package com.Over2Cloud.ctrl.asset.NetworkMonitor;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class NetworkScannerHelper
{
	@SuppressWarnings("unchecked")
	public void checkIP(SessionFactory connection,CommonOperInterface cbt)
	{
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("SELECT nmd.id,nmd.ipaddress,nmd.workstatus,nmd.alertstatus,asd.assetname,IFNULL(asd.serialno,'NA') ");
			query.append(" FROM network_monitor_details AS nmd ");
			query.append(" INNER JOIN asset_detail AS asd ON asd.id=nmd.assetid WHERE asd.status='Active' AND nmd.scanfor='IP'");
			List dataList=cbt.executeAllSelectQuery(query.toString(), connection);
			InetAddress addr=null;
			Map<String, Object> setVariables = new HashMap<String, Object>();
			Map<String, Object> whereCondition = new HashMap<String, Object>();
			InsertDataTable ob=null;
			HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
			List<InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			if(dataList!=null && dataList.size()>0)
			{
				CommunicationHelper cmnHelper = new CommunicationHelper();
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object!=null)
					{
						if(object[1]!=null && !object[1].toString().equalsIgnoreCase(""))
						{
							addr=InetAddress.getByName(object[1].toString());
							if(!addr.isReachable(3000))
							{
								if(object[2]==null || object[2].toString().equalsIgnoreCase("Active"))
								{
									whereCondition.clear();
									setVariables.clear();
									whereCondition.put("id", object[0].toString());
									setVariables.put("workstatus", "DActive");
									setVariables.put("alertstatus", "Send");
									boolean status=cbt.updateTableColomn("network_monitor_details", setVariables, whereCondition, connection);
									if(status)
									{
										String updateQuery="UPDATE network_monitor_log SET service_stop_date='"+DateUtil.getCurrentDateUSFormat()+"',service_stop_time='"+DateUtil.getCurrentTimeHourMin()+"' WHERE net_monitor_id="+object[0].toString()+" AND service_stop_date IS NULL";
										//HUH.updateByIsNull(updateQuery, connection);
										
										List empDetailsList=getEmpDetailsByNetMonitorId(object[0].toString(), connection, cbt);
										sendMail(empDetailsList,object[1].toString(),null,object[4].toString(),object[5].toString(),cmnHelper,connection,"DActive");
									}
								}
							}
							else
							{
								if(object[2]==null || object[2].toString().equalsIgnoreCase("DActive"))
								{
									whereCondition.clear();
									setVariables.clear();
									whereCondition.put("id", object[0].toString());
									
									setVariables.put("workstatus", "Active");
									setVariables.put("alertstatus", "Send");
									boolean status=cbt.updateTableColomn("network_monitor_details", setVariables, whereCondition, connection);
									if(status)
									{
										insertDataIntoLog(object[0].toString(),ob,insertData,connection, cbt);
										
										List empDetailsList=getEmpDetailsByNetMonitorId(object[0].toString(), connection, cbt);
										sendMail(empDetailsList,object[1].toString(),null,object[4].toString(),object[5].toString(),cmnHelper,connection,"Active");
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void checkPort(SessionFactory connection,CommonOperInterface cbt)
	{
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("SELECT nmd.id,nmd.ipaddress,nmd.port,nmd.workstatus,nmd.alertstatus,asd.assetname,IFNULL(asd.serialno,'NA') ");
			query.append(" FROM network_monitor_details AS nmd ");
			query.append(" INNER JOIN asset_detail AS asd ON asd.id=nmd.assetid WHERE asd.status='Active' AND nmd.scanfor='Port'");
			List dataList=cbt.executeAllSelectQuery(query.toString(), connection);
			InetAddress addr=null;
			Map<String, Object> setVariables = new HashMap<String, Object>();
			Map<String, Object> whereCondition = new HashMap<String, Object>();
			InsertDataTable ob=null;
			HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
			List<InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			if(dataList!=null && dataList.size()>0)
			{
				CommunicationHelper cmnHelper = new CommunicationHelper();
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object!=null)
					{
						boolean reachableStatus=false;
						if(object[1]!=null && !object[1].toString().equalsIgnoreCase(""))
						{
							try
							{
								addr=InetAddress.getByName(object[1].toString());
								Socket socket = new Socket(addr, Integer.valueOf(object[2].toString()));
								reachableStatus=true;
							}
							catch(Exception e)
							{
								//e.printStackTrace();
							}
							
							if(!reachableStatus)
							{
								if(object[3]==null || object[3].toString().equalsIgnoreCase("Active"))
								{
									whereCondition.clear();
									setVariables.clear();
									whereCondition.put("id", object[0].toString());
									setVariables.put("workstatus", "DActive");
									setVariables.put("alertstatus", "Send");
									boolean status=cbt.updateTableColomn("network_monitor_details", setVariables, whereCondition, connection);
									if(status)
									{
										String updateQuery="UPDATE network_monitor_log SET service_stop_date='"+DateUtil.getCurrentDateUSFormat()+"',service_stop_time='"+DateUtil.getCurrentTimeHourMin()+"' WHERE net_monitor_id="+object[0].toString()+" AND service_stop_date IS NULL";
										//HUH.updateByIsNull(updateQuery, connection);
										
										List empDetailsList=getEmpDetailsByNetMonitorId(object[0].toString(), connection, cbt);
										sendMail(empDetailsList,object[1].toString(),object[2].toString(),object[5].toString(),object[6].toString(),cmnHelper,connection,"DActive");
									}
								}
							}
							else
							{
								if(object[3]==null || object[3].toString().equalsIgnoreCase("DActive"))
								{
									whereCondition.clear();
									setVariables.clear();
									whereCondition.put("id", object[0].toString());
									
									setVariables.put("workstatus", "Active");
									setVariables.put("alertstatus", "Send");
									boolean status=cbt.updateTableColomn("network_monitor_details", setVariables, whereCondition, connection);
									if(status)
									{
										insertDataIntoLog(object[0].toString(),ob,insertData,connection, cbt);
										
										List empDetailsList=getEmpDetailsByNetMonitorId(object[0].toString(), connection, cbt);
										sendMail(empDetailsList,object[1].toString(),object[2].toString(),object[5].toString(),object[6].toString(),cmnHelper,connection,"Active");
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List getEmpDetailsByNetMonitorId(String netMonitorId, SessionFactory connection, CommonOperInterface cbt)
	{
		List dataList=new ArrayList();
		try
		{
			StringBuilder str=new StringBuilder();
			str.append("SELECT IFNULL(emp.empName,'NA'),IFNULL(emp.mobOne,'NA'),IFNULL(emp.emailIdOne,'NA'),IFNULL(dept.deptName,'NA') FROM network_monitor_contact AS nmc ");
			str.append(" INNER JOIN compliance_contacts AS cc ON cc.id=nmc.contact_id ");
			str.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
			str.append(" INNER JOIN department AS dept ON dept.id=cc.forDept_id ");
			str.append(" WHERE cc.work_status!=1 AND nmc.net_monitor_id="+netMonitorId);
			dataList=cbt.executeAllSelectQuery(str.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
			
	}
	
	public void insertDataIntoLog(String netMonitorId,InsertDataTable ob,List<InsertDataTable> insertData,SessionFactory connection,CommonOperInterface cbt)
	{
		insertData.clear();
		
		ob = new InsertDataTable();
		ob.setColName("net_monitor_id");
		ob.setDataName(netMonitorId);
		insertData.add(ob);
		
		ob = new InsertDataTable();
		ob.setColName("service_start_date");
		ob.setDataName(DateUtil.getCurrentDateUSFormat());
		insertData.add(ob);
		
		ob = new InsertDataTable();
		ob.setColName("service_start_time");
		ob.setDataName(DateUtil.getCurrentTimeHourMin());
		insertData.add(ob);
		
		cbt.insertIntoTable("network_monitor_log", insertData, connection);
	}
	
	
	@SuppressWarnings("unchecked")
	public void sendMail(List empDataList,String ip,String port,String assetName,String serialNo,CommunicationHelper cmnHelper,SessionFactory connection,String status)
	{
		try
		{
			if(empDataList!=null && empDataList.size()>0)
			{
				String msgText=null;
				String mailText=null;
				String mailSubject=null;
				
				
				for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(port==null)
					{
						if(status!=null && status.equalsIgnoreCase("Active"))
						{
							msgText="This IP "+ip+" is reachable";
							mailSubject=ip+" Service Status";
						}
						else
						{
							msgText="Dear "+DateUtil.makeTitle(object[0].toString())+", Kindly take action for "+ip+" as it is observed that it is not reachable.";
							mailSubject=ip+" Service Status";
						}
					}
					else
					{
						if(status!=null && status.equalsIgnoreCase("Active"))
						{
							msgText="Dear "+DateUtil.makeTitle(object[0].toString())+", Kindly take action for "+ip+","+port+" as it is observed that it is not reachable.";
							mailSubject=ip+" Port "+port+" Service Status";
						}
						else
						{
							msgText="Dear "+DateUtil.makeTitle(object[0].toString())+", Kindly take action for "+ip+","+port+" as it is observed that it is not reachable.";
							mailSubject=ip+" Port "+port+" Service Status";
						}
					}
					cmnHelper.addMessage(object[0].toString(), object[3].toString(), object[1].toString(), msgText, "", "Pending", "0", "ASTM", connection);
					mailText = getConfigMail(assetName, serialNo, ip,null,object[0].toString(),mailSubject,status);
					cmnHelper.addMail(object[0].toString(), object[3].toString(), object[2].toString(), mailSubject, mailText, "", "Pending", "0", null, "ASTM", connection);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String getConfigMail(String assetName, String serialNo, String ip, String port, String empName,String mailTitle,String status)
	{
		StringBuilder mailtext = new StringBuilder("");

		mailtext.append("<br><b>Dear " + empName + ", </b><br>");
		mailtext.append(mailTitle);
		mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Asset&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + assetName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Serial&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + serialNo + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> IP:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + ip + "</td></tr>");
		if(port!=null)
		{
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Port:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
			        + port + "</td></tr>");
		}
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + status + "</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append("<br>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}
	
}
