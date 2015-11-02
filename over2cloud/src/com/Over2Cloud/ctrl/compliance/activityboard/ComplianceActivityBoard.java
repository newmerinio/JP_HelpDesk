package com.Over2Cloud.ctrl.compliance.activityboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.compliance.serviceFiles.ComplianceServiceHelper;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.compliance.ComplianceTransferAction;
import com.Over2Cloud.ctrl.compliance.ConfigureComplianceAction;
import com.Over2Cloud.ctrl.compliance.complTaskMaster.ComplianceTaskAction;

@SuppressWarnings("serial")
public class ComplianceActivityBoard extends GridPropertyBean implements ServletRequestAware
{
	private List<Object> masterViewList;
	private String deptId;
	private String searchPeriodOn;
	private String fromDate;
	private String toDate;
	private String frequency;
	private String actionStatus;
	private String dataFrom;
	private Map<String, String> headermap;
	private Map<String, String> deptName;
	private Map<String, String> taskTypeMap;
	private String complID;
	private Map<String, String> complDetails;
	private Map<String, String> checkListDetails;
	private Map<String, String> checkedCheckListDetails;
	private List<ComplianceDashboardBean> doneDetails = new ArrayList<ComplianceDashboardBean>();
	private List<ComplianceDashboardBean> allotDetails = new ArrayList<ComplianceDashboardBean>();
	private List<ComplianceDashboardBean> allotByDetails = new ArrayList<ComplianceDashboardBean>();
	private List<ComplianceDashboardBean> budgetDetails = new ArrayList<ComplianceDashboardBean>();
	private Map<String, String> statusDetails = null;
	private Map<String, String> escalationDetails = null;
	private Map<String, String> reminderDetails = null;
	private String taskId;
	private String budget;
	private String timmings;
	private String shareStatus;
	private String searching;
	private String type;
	private String taskName;
	private String fileName;
	private InputStream inputStream;
	private String doneDoc;
	private String dataFor;
	private String docName;
	private Map<String, String> workingmap;
	String userType = (String) session.get("userTpe");
	private ArrayList<ArrayList<String>> checkList = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> checkedCheckList = new ArrayList<ArrayList<String>>();
	private Map<String, String> deptMap;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private String departId;
	private String empName;
	private String status;
	private HttpServletRequest request;
	
	public String inactiveTask()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			//System.out.println("inside success"+getComplID());
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}	
	
	public String editComplBoardData()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
					String userName = (String) session.get("uName");
					id=request.getParameter("id");
					String reason=request.getParameter("reason");
					//System.out.println(id+"djskgvdbgdhf>>>>"+reason);
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String tempIds[]=getId().split(",");
					CommunicationHelper cmnHelper = new CommunicationHelper();
					String mailText=null;
					List dataList=null;
					List list=null;
					for(String id:tempIds)
					{
						StringBuilder query=new StringBuilder();
						query.append("SELECT ts.taskName,cd.taskBrief,cd.dueDate,cd.dueTime,cd.reminder_for,cd.frequency from compliance_details AS cd ");
						query.append("INNER JOIN compliance_task AS ts ON ts.id=cd.taskName ");
						query.append(" WHERE cd.id="+id);
					//	System.out.println("query>>>"+query);
						dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						//boolean status=cbt.deleteAllRecordForId("compliance_details", "id", id, connectionSpace);
						query.delete(0, query.length());
						query.append("UPDATE compliance_details set status='1', comments='"+reason+"', taskStatus='1' WHERE id="+id);
						boolean status=cbt.updateTableColomn(connectionSpace, query);
						if(status)
						{
							//cbt.deleteAllRecordForId("compliance_report", "complID", id, connectionSpace);
							//cbt.deleteAllRecordForId("compliance_reminder", "compliance_id", id, connectionSpace);
							if(dataList!=null && dataList.size()>0)
							{
								for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
								{
									Object object[]=(Object[]) iterator.next();
									if(object[4]!=null)
									{
										String empIds[]=object[4].toString().split("#");
										for (int i = 0; i < empIds.length; i++)
										{
											list=cbt.executeAllSelectQuery("SELECT eb.empName,eb.emailIdOne from compliance_contacts AS cc INNER JOIN employee_basic AS eb ON cc.emp_id=eb.id WHERE cc.id="+empIds[i], connectionSpace);
											if(list!=null && list.size()>0)
											{
												for (Iterator iterator2 = list.iterator(); iterator2.hasNext();)
												{
													Object[] object2 = (Object[]) iterator2.next();
													mailText=null;
													//System.out.println(object2[0].toString()+">>>>>>>"+object2[1].toString());
													mailText=makeDeletedTaskMail(dataList, "Operational Task Inactivated", object2[0].toString(),reason,userName, connectionSpace);
													cmnHelper.addMail(object2[0].toString(), "", object2[1].toString(), "Operational Task Inactivated", mailText, "", "Pending", "0", "","Compliance", connectionSpace);
												}
											}
											list.clear();
										}
									}
								}
							}
						}
						dataList.clear();
				}	
				addActionMessage("Operational Task Inactivated Successfully.");	
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String makeDeletedTaskMail( List dataList,String mailTitle,String empName, String reason, String userName, SessionFactory connection)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		if (dataList != null && dataList.size() > 0)
		{
			mailtext.append(mailTitle);
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
				mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object[0].toString() + "</td></tr>");
				mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object[1].toString() + "</td></tr>");
				mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.convertDateToIndianFormat(object[2].toString()) + ", "+object[3].toString()+"</td></tr>");
				mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object[5].toString() + "</td></tr>");
			}
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + userName + "</td></tr>");
			mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + reason + "</td></tr>");
			mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
			mailtext.append("<BR>");
			mailtext.append("<BR>");
			mailtext.append("<br>");
			mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		}
		return mailtext.toString();
	}
		
	public String getEmployeeDataList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
            	CommonOperInterface cbt = new CommonConFactory().createInterface();
            	jsonArray = new JSONArray();
            	//System.out.println(departId);
            	if (departId!=null && !departId.equalsIgnoreCase("")) 
				{
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					StringBuilder query=new StringBuilder();
					query.append(" SELECT emp.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN department AS dept ON dept.id=emp.deptname");
					query.append(" WHERE dept.id IN(" + departId + ") ORDER BY emp.empName ASC");
					List data2=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2!=null && data2.size()>0) 
					{
						for (Iterator iterator = data2.iterator(); iterator.hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							if (object!=null) 
							{
								jsonObject = new JSONObject();
								jsonObject.put("ID", object[0].toString());
								jsonObject.put("NAME", object[1].toString());
								jsonArray.add(jsonObject);
							 }
						 }
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return  LOGIN;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String viewReminderFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				reminderDetails = new LinkedHashMap<String, String>();
				String qry = "SELECT cc.remindMode,cr.due_time,cr.remind_date FROM compliance_details AS cc INNER JOIN compliance_reminder AS cr On cr.compliance_id=cc.id WHERE cc.id=" + complID + "  AND reminder_code!='S'";
				List data = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (data != null && data.size() > 0)
				{
					int i = 1;
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						reminderDetails.put("Mode", object[0].toString());
						reminderDetails.put("Reminder " + i, DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[1].toString());
						reminderDetails.put("Reminder " + i, DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[1].toString());
						reminderDetails.put("Reminder " + i, DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[1].toString());
						i++;
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String viewBudgetedFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT budgetary,actual_expenses,difference,actionTakenDate FROM compliance_report WHERE complID=" + complID + " AND actionTaken IN('Done','Recurring') ORDER BY id DESC LIMIT 3;";
				// / System.out.println(qry);
				List data = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						ComplianceDashboardBean CDB = new ComplianceDashboardBean();
						Object[] object = (Object[]) iterator.next();
						CDB.setBudgeted(getValueWithNullCheck(object[0]));
						CDB.setActual(getValueWithNullCheck(object[1]));
						CDB.setDifference(getValueWithNullCheck(object[2]));
						CDB.setActionTakenOn(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[3])));
						budgetDetails.add(CDB);
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String viewAllotedToFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT reminder_for FROM compliance_details WHERE id=" + complID;
				List reminderFor = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (reminderFor != null && reminderFor.size() > 0)
				{
					String emp = reminderFor.toString().replace("#", ",").substring(1, reminderFor.toString().length() - 2);
					qry = "SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id = emp.id INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.id IN(" + emp + ")";
					List data = cbt.executeAllSelectQuery(qry, connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							ComplianceDashboardBean CDB = new ComplianceDashboardBean();
							Object[] object = (Object[]) iterator.next();
							CDB.setRemindTo(object[0].toString());
							CDB.setMobNo(object[1].toString());
							CDB.setEmailId(object[2].toString());
							CDB.setDepartName(object[3].toString());
							allotDetails.add(CDB);
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String viewAllotedByFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT userName FROM compliance_details WHERE id=" + complID;
				List reminderFor = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (reminderFor != null && reminderFor.size() > 0)
				{
					String emp = Cryptography.encrypt(reminderFor.get(0).toString(), DES_ENCRYPTION_KEY);
					qry = "SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp INNER JOIN useraccount AS uc ON uc.id = emp.useraccountid INNER JOIN department AS dept ON dept.id=emp.deptname WHERE uc.userID ='" + emp + "'";
					List data = cbt.executeAllSelectQuery(qry, connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							ComplianceDashboardBean CDB = new ComplianceDashboardBean();
							Object[] object = (Object[]) iterator.next();
							CDB.setRemindTo(object[0].toString());
							CDB.setMobNo(object[1].toString());
							CDB.setEmailId(object[2].toString());
							CDB.setDepartName(object[3].toString());
							allotByDetails.add(CDB);
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String viewStatus()
	{
		try
		{
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT actionTaken,actionTakenDate,actionTakenTime,comments,snoozeDate,snoozeTime,rescheduleDate,");
			query.append("rescheduleTime,document_action_1,document_action_2,actual_expenses,userName,dueDate,dueTime,id,document_takeaction,document_config_1,document_config_2 ");
			query.append("FROM compliance_report WHERE complID='" + complID + "' AND complainceId='" + taskId + "' ORDER BY actionTakenDate DESC ");
			// System.out.println("query is as:::::::"+query.toString());
			List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						ComplianceDashboardBean CDB = new ComplianceDashboardBean();
						if (object[0] != null)
						{
							CDB.setActionStatus(CUA.getValueWithNullCheck(object[0]));
							String interval = DateUtil.time_difference(object[12].toString(), object[13].toString(), object[1].toString(), object[2].toString());
							CDB.setActionTakenOn(DateUtil.convertDateToIndianFormat(object[1].toString()) + ", " + object[2].toString());
							if (object[4] != null && object[5] != null)
							{
								CDB.setDueDate(DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString());
							}
							CDB.setComment(CUA.getValueWithNullCheck(object[3]));
							CDB.setName(DateUtil.makeTitle(Cryptography.decrypt(CUA.getValueWithNullCheck(object[11]), DES_ENCRYPTION_KEY)));
							CDB.setCost(CUA.getValueWithNullCheck(object[10]));
							CDB.setDifference(interval);
							if (object[8] != null)
							{
								String str = object[8].toString().substring(object[8].toString().lastIndexOf("//") + 2, object[8].toString().length());
								String docName = str.substring(14, str.length());
								CDB.setDocPath1(docName);
							} else
							{
								CDB.setDocPath1("NA");
							}
							if (object[15] != null)
							{
								String str = object[15].toString().substring(object[15].toString().lastIndexOf("//") + 2, object[15].toString().length());
								String docName = str.substring(14, str.length());
								CDB.setDocPath(docName);
							} else
							{
								CDB.setDocPath("NA");
							}
							if (object[9] != null && !object[9].toString().equalsIgnoreCase(""))
							{
								String str = object[9].toString().substring(object[9].toString().lastIndexOf("//") + 2, object[9].toString().length());
								String docName = str.substring(14, str.length());
								CDB.setDocPath2(docName);
							} else
							{
								CDB.setDocPath2("NA");
							}
							if (object[16] != null)
							{
								String str = object[16].toString().substring(object[16].toString().lastIndexOf("//") + 2, object[16].toString().length());
								String docName = str.substring(14, str.length());
								CDB.setConfigDocPath(docName);
							} else
							{
								CDB.setConfigDocPath(docName);
							}
							if (object[17] != null)
							{
								String str = object[17].toString().substring(object[17].toString().lastIndexOf("//") + 2, object[17].toString().length());
								String docName = str.substring(14, str.length());
								CDB.setConfigDocPath1(docName);
							} else
							{
								CDB.setConfigDocPath1(docName);
							}
							CDB.setId(object[14].toString());
							doneDetails.add(CDB);
							interval = "";
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;

	}

	@SuppressWarnings({ "rawtypes" })
	public String viewStatusFormatter()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			String qry = "SELECT actionTakenDate,actionTakenTime,document_config_1,document_config_2 FROM compliance_report WHERE complID ='" + complID + "' AND actionTaken='Pending' ORDER BY id DESC LIMIT 1";
			//System.out.println("QUERY :::>>>>>>>>>>>>>>>>>>>>>>>>> " + qry);
			String actionTakenDate = null;
			String configDoc1 = null;
			String configDoc2 = null;

			List tempList = cbt.executeAllSelectQuery(qry, connectionSpace);
			if (tempList != null && tempList.size() > 0)
			{
				Object[] object1 = (Object[]) tempList.get(0);
				actionTakenDate = object1[0].toString();
				if (object1[2] != null)
				{
					configDoc1 = object1[2].toString();
				}
				if (object1[3] != null)
				{
					configDoc2 = object1[3].toString();
				}
				StringBuilder query = new StringBuilder();
				query.append("SELECT actionTaken,actionTakenDate,actionTakenTime,comments,snoozeDate,snoozeTime,rescheduleDate,");
				query.append("rescheduleTime,document_takeaction,document_action_1,actual_expenses,userName,dueDate,dueTime,id,document_action_2 ");
				query.append("FROM compliance_report WHERE complID='" + complID + "' AND actionTaken !='Pending' AND actionTakenDate BETWEEN '" + actionTakenDate + "' AND '" + DateUtil.getCurrentDateUSFormat() + "' ORDER BY actionTakenDate,actionTakenTime DESC");

				//System.out.println("query is as:::::::" + query.toString());
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null)
						{
							ComplianceDashboardBean CDB = new ComplianceDashboardBean();
							if (object[0] != null)
							{
								CDB.setActionStatus(CUA.getValueWithNullCheck(object[0]));
								String interval = null;
								boolean status = DateUtil.comparetwoDates(object[12].toString(), object[1].toString());
								if (status)
								{
									interval = DateUtil.time_difference(object[12].toString(), object[13].toString(), object[1].toString(), object[2].toString());
								} else
								{
									interval = DateUtil.time_difference(object[1].toString(), object[2].toString(), object[12].toString(), object[13].toString());
								}
								CDB.setActionTakenOn(DateUtil.convertDateToIndianFormat(object[1].toString()) + ", " + object[2].toString());
								if (object[0].toString().equalsIgnoreCase("Reschedule"))
								{
									if (object[6] != null && object[7] != null)
									{
										CDB.setDueDate(DateUtil.convertDateToIndianFormat(object[6].toString()) + ", " + object[7].toString());
									}
								} 
								else
								{
									if (object[4] != null && object[5] != null)
									{
										CDB.setDueDate(DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString());
									}
								}

								CDB.setComment(CUA.getValueWithNullCheck(object[3]));
								CDB.setName(DateUtil.makeTitle(Cryptography.decrypt(CUA.getValueWithNullCheck(object[11]), DES_ENCRYPTION_KEY)));
								CDB.setCost(CUA.getValueWithNullCheck(object[10]));
								CDB.setDifference(interval);
								if (object[8] != null)
								{
									String str = object[8].toString().substring(object[8].toString().lastIndexOf("//") + 2, object[8].toString().length());
									String docName = str.substring(14, str.length());
									CDB.setDocPath(docName);
								} 
								else
								{
									CDB.setDocPath("NA");
								}
								if (object[9] != null)
								{
									String str = object[9].toString().substring(object[9].toString().lastIndexOf("//") + 2, object[9].toString().length());
									String docName = str.substring(14, str.length());
									CDB.setDocPath1(docName);
								}
								else
								{
									CDB.setDocPath1("NA");
								}
								if (object[15] != null && !object[15].toString().equalsIgnoreCase(""))
								{
									String str = object[15].toString().substring(object[15].toString().lastIndexOf("//") + 2, object[15].toString().length());
									String docName = str.substring(14, str.length());
									CDB.setDocPath2(docName);
								}
								else
								{
									CDB.setDocPath2("NA");
								}
								if (configDoc1 != null && !configDoc1.equalsIgnoreCase(""))
								{
									String str = configDoc1.substring(configDoc1.lastIndexOf("//") + 2, configDoc1.length());
									String docName = str.substring(14, str.length());
									CDB.setConfigDocPath(docName);
								}
								else
								{
									CDB.setConfigDocPath("NA");
								}
								if (configDoc2 != null && !configDoc2.equalsIgnoreCase(""))
								{
									String str = configDoc2.substring(configDoc2.lastIndexOf("//") + 2, configDoc2.length());
									String docName = str.substring(14, str.length());
									CDB.setConfigDocPath1(docName);
								} 
								else
								{
									CDB.setConfigDocPath1("NA");
								}

								CDB.setId(object[14].toString());
								CDB.setCompId(complID);
								doneDetails.add(CDB);
							}
						}
					}
				}
			}
			workingmap = new LinkedHashMap<String, String>();
			qry = "SELECT  holiday,fromTime,toTime FROM working_timing WHERE moduleName='COMPL' ORDER BY id DESC LIMIT 1";
			List data = cbt.executeAllSelectQuery(qry, connectionSpace);
			if (data != null && !data.isEmpty())
			{
				Object object[] = (Object[]) data.get(0);
				qry = "SELECT  day FROM working_timing WHERE moduleName='COMPL' ";
				List data2 = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (data2 != null && !data2.isEmpty())
				{
					Object a[] = data2.toArray();
					if (object[0].toString().equalsIgnoreCase("N"))
					{
						workingmap.put("Working Days", a[0].toString() + " to " + a[a.length - 1].toString() + " (No Holiday)");
					} else
					{
						workingmap.put("Working Days", a[0].toString() + " to " + a[a.length - 1].toString() + " (Consider Holiday)");
					}
					workingmap.put("Working Hours", object[1].toString() + " Hrs to " + object[2].toString() + " Hrs");

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String viewTATFormatter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				statusDetails = new LinkedHashMap<String, String>();
				escalationDetails = new LinkedHashMap<String, String>();
				String qry = "SELECT actionStatus,current_esc_level,dueDate,dueTime,escalation,escalation_level_1,l1EscDuration,escalation_level_2" + ",l2EscDuration,escalation_level_3,l3EscDuration,escalation_level_4,l4EscDuration FROM " + "compliance_details WHERE id=" + complID;
				//System.out.println(">>>>TAT Query::: " + qry);
				List level = cbt.executeAllSelectQuery(qry, connectionSpace);
				if (level != null && level.size() > 0)
				{
					Object[] obj = (Object[]) level.get(0);
					statusDetails.put("Current Status", obj[0].toString());
					if (obj[1] != null)
					{
						statusDetails.put("Level", obj[1].toString());
					} else
					{
						statusDetails.put("Level", "1");
					}
					if (obj[4].toString().equalsIgnoreCase("Y"))
					{
						String escDateTime[] = new String[2];
						WorkingHourAction WHA = new WorkingHourAction();
						if (obj[5] != null)
						{

							String date = obj[2].toString();
							String time = obj[3].toString();
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[6].toString(), "00:00", "Y", date, time, "COMPL");
							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);

							escalationDetails.put("L-2 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[5].toString().replace("#", ",").substring(0, (obj[5].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-2 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						if (obj[7] != null)
						{
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[8].toString(), "00:00", "Y", DateUtil.convertDateToUSFormat(escDateTime[0]), escDateTime[1], "COMPL");

							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);

							escalationDetails.put("L-3 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[7].toString().replace("#", ",").substring(0, (obj[7].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-3 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						if (obj[9] != null)
						{
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[10].toString(), "00:00", "Y", DateUtil.convertDateToUSFormat(escDateTime[0]), escDateTime[1], "COMPL");

							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);

							escalationDetails.put("L-4 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[9].toString().replace("#", ",").substring(0, (obj[9].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							// System.out.println("Query 2::: "+query2);
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-4 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						if (obj[11] != null)
						{
							List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, obj[12].toString(), "00:00", "Y", DateUtil.convertDateToUSFormat(escDateTime[0]), escDateTime[1], "COMPL");

							escDateTime[0] = DateUtil.convertDateToIndianFormat(dateTime.get(0));
							escDateTime[1] = dateTime.get(1);

							escalationDetails.put("L-5 Escalate Date & Time", escDateTime[0] + ", " + escDateTime[1]);
							StringBuilder empName = new StringBuilder();
							String empId = obj[11].toString().replace("#", ",").substring(0, (obj[11].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object object = (Object) iterator.next();
								empName.append(object.toString() + ", ");
							}
							escalationDetails.put("L-5 Escalate To", empName.toString().substring(0, empName.toString().length() - 2));
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String beforeActivityBoardView()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				fromDate = DateUtil.getNewDate("day", -7, DateUtil.getCurrentDateUSFormat());
				toDate = DateUtil.getCurrentDateUSFormat();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userEmpID = null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				String compId = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				String qry = "SELECT dept.id,dept.deptName From department AS dept INNER JOIN compliance_task AS ct ON ct.departName = dept.id INNER JOIN compliance_details AS cc ON cc.taskName=ct.id WHERE cc.id IN (" + compId + ") GROUP BY id";
				List deptList = cbt.executeAllSelectQuery(qry, connectionSpace);
				deptName = new LinkedHashMap<String, String>();
				taskTypeMap = new LinkedHashMap<String, String>();
				if (deptList != null && deptList.size() > 0)
				{
					for (Iterator iterator = deptList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1] != null)
						{
							deptName.put(object[0].toString(), object[1].toString());
						}
					}
				}
				ComplianceTaskAction ct = new ComplianceTaskAction();
				// System.out.println("user name::: "+userName);
				String[] userData = new ComplianceUniversalAction().getEmpDataByUserName(userName);
				Map<String, String> tempMap = ct.getAllTaskType(userData[3]);
				taskTypeMap.put("All", "All Task Type");
				if (tempMap != null && tempMap.size() > 0)
				{
					for (Map.Entry<String, String> entry : tempMap.entrySet())
					{
						taskTypeMap.put(entry.getKey(), entry.getValue());
					}
				}
				/*deptMap=new HashMap<String, String>();
				StringBuilder query=new StringBuilder("");
				query.append("SELECT dept.id,dept.deptName");
				query.append(" FROM compliance_details AS comp INNER JOIN compliance_report AS cr ON comp.id=cr.complID INNER JOIN compliance_task AS ts ON ts.id=comp.taskName INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType INNER JOIN department AS dept ON dept.id=ts.departName ORDER BY dept.deptName");
				List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(list!=null && list.size()>0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptMap.put(object[0].toString(), object[1].toString());
					}
				}*/
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String fetchCounters()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userEmpID = null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				String compId = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				StringBuilder complIds = new StringBuilder();
				if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
				{
					ComplianceCommonOperation CO = new ComplianceCommonOperation();
					String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + userEmpID + ")";
					List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
					List contactList = new ArrayList();
					if (shareDataCount != null && shareDataCount.size() > 0)
					{
						for (int i = 0; i < shareDataCount.size(); i++)
						{
							contactList.add("#" + shareDataCount.get(i).toString() + "#");
							String contactId = CO.getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
							if (contactId != null && !contactId.equalsIgnoreCase(""))
							{
								String str[] = contactId.split(",");
								for (int count = 0; count < str.length; count++)
								{
									contactList.add("#" + str[count] + "#");
								}
							}
						}
					}
					String contactId = CO.getMappedAllContactId(userEmpID, "COMPL");
					if (contactId != null && !contactId.equalsIgnoreCase(""))
					{
						String str[] = contactId.split(",");
						for (int i = 0; i < str.length; i++)
						{
							contactList.add("#" + str[i] + "#");
						}
					}
					List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details", connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							Object object1[] = (Object[]) iterator.next();
							String str = "#";
							str = str + object1[1].toString();
							for (int i = 0; i < contactList.size(); i++)
							{
								if (str.contains(contactList.get(i).toString()))
								{
									complIds.append(object1[0].toString() + ",");
								}
							}
						}
					}
					complIds.append(compId);
				}
				headermap = new LinkedHashMap<String, String>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT actionStatus FROM compliance_details AS  comp " + "INNER JOIN compliance_report AS cr ON comp.id=cr.complID  " + "INNER JOIN compliance_task AS ts ON ts.id=comp.taskName  " + "INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType  " + "INNER JOIN department AS dept ON dept.id=ts.departName  " + "WHERE comp.id!=0");
				if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareMe"))
				{
					query.append(" AND comp.id IN(" + compId + ") ");
				} else if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareBy"))
				{
					query.append(" AND comp.userName ='" + userName + "' ");
				} else if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
				{
					query.append(" AND comp.id IN(" + complIds.toString() + ") ");
				}
				if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("actionTakenDate"))
				{
					String str[] = fromDate.split("-");
					if (str[0].length() < 3)
					{
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
						toDate = DateUtil.convertDateToUSFormat(toDate);
						query.append(" AND cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					} else
					{
						query.append(" AND cr.actionTakenDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
					}
				} else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("dueDate"))
				{
					String str[] = fromDate.split("-");
					if (str[0].length() < 3)
					{
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
						toDate = DateUtil.convertDateToUSFormat(toDate);
						query.append(" AND cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
					} else
					{
						query.append(" AND cr.dueDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
					}
				} else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("All"))
				{
					if (fromDate != null & !fromDate.equalsIgnoreCase("All") && toDate != null && !toDate.equalsIgnoreCase("All"))
					{
						String str[] = fromDate.split("-");
						if (str[0].length() < 3)
						{
							fromDate = DateUtil.convertDateToUSFormat(fromDate);
							toDate = DateUtil.convertDateToUSFormat(toDate);
						}
						query.append(" AND (cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
						query.append(" OR cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "')");
					}
				}
				if (timmings != null && timmings.equalsIgnoreCase("onTime"))
				{
					query.append(" AND comp.dueDate < '" + DateUtil.getCurrentDateUSFormat() + "'");
				}
				if (timmings != null && timmings.equalsIgnoreCase("offTime"))
				{
					query.append(" AND comp.dueDate > '" + DateUtil.getCurrentDateUSFormat() + "'");
				}
				if (frequency != null && !frequency.equalsIgnoreCase("All"))
				{
					query.append(" AND comp.frequency = '" + frequency + "'");
				}
				if (deptId != null && !deptId.equalsIgnoreCase("All"))
				{
					query.append(" AND dept.id = '" + deptId + "'");
				}
				if (empName != null && !empName.equalsIgnoreCase("-1") && !empName.equalsIgnoreCase("undefined"))
				{
					String qry = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.moduleName='COMPL' AND eb.id="+empName;
					List dataList = cbt.executeAllSelectQuery(qry, connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						query.append("AND (");
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							query.append(" comp.reminder_for LIKE '"+iterator.next()+"#' OR ");
						}
						query.delete(query.length()-3, query.length());
						query.append(")");
					}
				}
				if (actionStatus != null && !actionStatus.equalsIgnoreCase("ALL"))
				{
					if (!actionStatus.equalsIgnoreCase("Upcoming") && !actionStatus.equalsIgnoreCase("Missed") && !actionStatus.equalsIgnoreCase("Pending"))
					{
						if (actionStatus.equalsIgnoreCase("Done"))
						{
							query.append(" AND cr.actionTaken IN('Done','Recurring')");
						} else
						{
							query.append(" AND cr.actionTaken='" + actionStatus + "'");
						}
					} else if (actionStatus.equalsIgnoreCase("Upcoming"))
					{
						/*
						 * String str[] = fromDate.split("-"); if
						 * (str[0].length() < 3) { fromDate =
						 * DateUtil.convertDateToUSFormat(fromDate); toDate =
						 * DateUtil.convertDateToUSFormat(toDate);
						 * query.append(" AND comp.dueDate BETWEEN '" +
						 * DateUtil.getCurrentDateUSFormat() + "' AND '" +
						 * toDate + "' AND comp.actionTaken='Pending'"); } else
						 * { query.append(" AND comp.dueDate BETWEEN '" +
						 * DateUtil.getCurrentDateUSFormat() + "' AND '" +
						 * DateUtil.convertDateToUSFormat(toDate) +
						 * "' AND comp.actionTaken='Pending'"); }
						 */
						StringBuilder complId = new StringBuilder();
						String qry = "SELECT start_date,start_time,id FROM compliance_details WHERE actionStatus ='Pending'";
						List dataList = cbt.executeAllSelectQuery(qry, connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							int j = 1;
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object2 = (Object[]) iterator.next();
								boolean statusDue = DateUtil.comparebetweenTwodateTime(object2[0].toString(), object2[1].toString(), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin());
								if (!statusDue)
								{
									if (j < dataList.size() - 1)
									{
										complId.append(object2[2].toString() + ",");
									} else
									{
										complId.append(object2[2].toString());
									}
									j++;
								}
							}
							query.append(" AND comp.id IN (" + complId.toString().substring(0, complId.toString().length() - 1) + ") ");
						}
					} else if (actionStatus.equalsIgnoreCase("Missed"))
					{
						String str[] = fromDate.split("-");
						if (str[0].length() < 3)
						{
							fromDate = DateUtil.convertDateToUSFormat(fromDate);
							toDate = DateUtil.convertDateToUSFormat(toDate);
							query.append(" AND comp.dueDate BETWEEN '" + fromDate + "' AND  '" + DateUtil.getCurrentDateUSFormat() + "' AND comp.actionTaken='Pending'");
						} else
						{
							query.append(" AND comp.dueDate BETWEEN '" + fromDate + "' AND '" + DateUtil.getCurrentDateUSFormat() + "' AND comp.actionTaken='Pending'");
						}
					} else if (actionStatus.equalsIgnoreCase("Pending"))
					{
						query.append(" AND  comp.start_date = '" + DateUtil.getCurrentDateUSFormat() + "'  AND comp.actionTaken='Pending'");
					}
				}
				if (budget != null && !budget.equalsIgnoreCase("All"))
				{
					if (budget.equalsIgnoreCase("NB"))
					{
						query.append(" AND comp.budgetary='NA' OR comp.budgetary=' '");
					} else
					{
						query.append(" AND comp.budgetary!='NA' AND comp.budgetary!=' '");
					}
				}
				if (searching != null && !searching.equalsIgnoreCase(""))
				{
					query.append(" AND ts.taskName LIKE '%" + searching + "%'");
				}
				if (type != null && !type.equalsIgnoreCase("All"))
				{
					query.append(" AND ctyp.id='" + type + "' ");
				}
				if (status != null && !status.equalsIgnoreCase("-1") && status.equalsIgnoreCase("Inactive"))
				{
					query.append(" AND comp.status='1' ");
				}
				else
				{
					query.append(" AND comp.status='0'");
				}
				query.append(" GROUP BY comp.id");
				//System.out.println("VVVVVVv   " + query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				int total = 0;
				int pendingc = 0, snoozec = 0, donec = 0, ignore = 0;
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object.toString().equalsIgnoreCase("Pending"))
						{
							pendingc = pendingc + 1;
						} else if (object.toString().equalsIgnoreCase("Snooze"))
						{
							snoozec = snoozec + 1;
						} else if (object.toString().equalsIgnoreCase("Ignore"))
						{
							ignore = ignore + 1;
						} else if (object.toString().equalsIgnoreCase("Done"))
						{
							donec = donec + 1;
						}
					}
					if (pendingc > 0)
					{
						headermap.put("Pending", String.valueOf(pendingc));
					} else
					{
						headermap.put("Pending", "0");
					}
					if (snoozec > 0)
					{
						headermap.put("Snooze", String.valueOf(snoozec));
					} else
					{
						headermap.put("Snooze", "0");
					}
					if (ignore > 0)
					{
						headermap.put("Ignore", String.valueOf(ignore));
					} else
					{
						headermap.put("Ignore", "0");
					}
					if (donec > 0)
					{
						headermap.put("Done", String.valueOf(donec));
					} else
					{
						headermap.put("Done", "0");
					}
					total = pendingc + snoozec + donec;
					headermap.put("Total", String.valueOf(total));
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public String activityBoardView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> sessionvalues = new ArrayList<GridDataPropertyView>();
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.history");
				gpv.setHeadingName("History");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(30);
				gpv.setFormatter("historyFullView");
				viewPageColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("dept.deptName");
				gpv.setHeadingName("Department");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("ctyp.taskType");
				gpv.setHeadingName("Task Type");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("ts.taskName");
				gpv.setHeadingName("Task Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.taskBrief");
				gpv.setHeadingName("Task Brief");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.compList");
				gpv.setHeadingName("Check List");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(40);
				gpv.setFormatter("checkList");
				viewPageColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.compid_prefix");
				gpv.setHeadingName("Task ID");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.compid_suffix");
				gpv.setHeadingName("Compliance Suffix");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(150);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.frequency");
				gpv.setHeadingName("Frequency");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.start_date");
				gpv.setHeadingName("Start From");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(70);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.start_time");
				gpv.setHeadingName("Start Time");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(70);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.dueDate");
				gpv.setHeadingName("Due On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(70);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.dueTime");
				gpv.setHeadingName("Due On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(70);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.actionStatus");
				gpv.setHeadingName("Current Status");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				gpv.setFormatter("viewStatus");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.userName");
				gpv.setHeadingName("Last Action By & On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(130);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenDate");
				gpv.setHeadingName("Achieved On & At");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(150);
				gpv.setFormatter("false");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenTime");
				gpv.setHeadingName("Achieved At");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(150);
				gpv.setFormatter("false");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_date");
				gpv.setHeadingName("Escalation");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				gpv.setFormatter("viewTAT");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_time");
				gpv.setHeadingName("TAT");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.budgetary");
				gpv.setHeadingName("Budgeted");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				gpv.setFormatter("budgetedView");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.reminder_for");
				gpv.setHeadingName("Allot To");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				gpv.setFormatter("allotedTo");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.userName");
				gpv.setHeadingName("Alloted By");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(70);
				gpv.setFormatter("allotedBy");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.reminder");
				gpv.setHeadingName("Reminder");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(120);
				gpv.setFormatter("reminder");
				viewPageColumns.add(gpv);

				session.put("masterViewProp", sessionvalues);

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewActivityBoardData()
	{
		String retunString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				WorkingHourAction WHA=new WorkingHourAction();
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				String compIdSeries = null;
				Object object = null;
				String userEmpID = null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				StringBuilder complIds = new StringBuilder();
				compIdSeries = new ComplianceTransferAction().getAllComplianceIdByEmpId(userEmpID);
				if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
				{
					ComplianceCommonOperation CO = new ComplianceCommonOperation();
					String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='COMPL' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id=" + userEmpID + ")";
					List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
					List contactList = new ArrayList();
					if (shareDataCount != null && shareDataCount.size() > 0)
					{
						for (int i = 0; i < shareDataCount.size(); i++)
						{
							contactList.add("#" + shareDataCount.get(i).toString() + "#");
							String contactId = CO.getMappedAllContactId(shareDataCount.get(i).toString(), "COMPL");
							if (contactId != null && !contactId.equalsIgnoreCase(""))
							{
								String str[] = contactId.split(",");
								for (int count = 0; count < str.length; count++)
								{
									contactList.add("#" + str[count] + "#");
								}
							}
						}
					}
					String contactId = CO.getMappedAllContactId(userEmpID, "COMPL");
					if (contactId != null && !contactId.equalsIgnoreCase(""))
					{
						String str[] = contactId.split(",");
						for (int i = 0; i < str.length; i++)
						{
							contactList.add("#" + str[i] + "#");
						}
					}
					List dataCount = cbt.executeAllSelectQuery("SELECT id,reminder_for FROM compliance_details", connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							Object object1[] = (Object[]) iterator.next();
							String str = "#";
							str = str + object1[1].toString();
							for (int i = 0; i < contactList.size(); i++)
							{
								if (str.contains(contactList.get(i).toString()))
								{
									complIds.append(object1[0].toString() + ",");
								}
							}
						}
					}
					complIds.append(compIdSeries);
				}

				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from compliance_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName() != null )
								{
									if (gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("comp.id,");
									} else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
									{
										query.append("cr.userName AS actionBy,");
									} else
									{
										query.append(gpv.getColomnName().toString() + ",");
									}
								}
							} else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM compliance_details AS comp " + "INNER JOIN compliance_report AS cr ON comp.id=cr.complID  " + "INNER JOIN compliance_task AS ts ON ts.id=comp.taskName  " + "INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType  " + "INNER JOIN department AS dept ON dept.id=ts.departName  " + "WHERE ");
						
						if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareMe"))
						{
							query.append(" comp.id IN(" + compIdSeries + ") ");
						} else if (shareStatus != null && shareStatus.equalsIgnoreCase("ShareBy"))
						{
							query.append(" comp.userName ='" + userName + "' ");
						} else if (shareStatus != null && shareStatus.equalsIgnoreCase("group"))
						{
							query.append(" comp.id IN(" + complIds.toString() + ") ");
						} else if (shareStatus != null && shareStatus.equalsIgnoreCase("all"))
						{
							query.append(" ( comp.id IN(" + compIdSeries + ") OR comp.userName ='" + userName + "') ");
						}
						if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("actionTakenDate"))
						{
							String str[] = fromDate.split("-");
							if (str[0].length() < 3)
							{
								fromDate = DateUtil.convertDateToUSFormat(fromDate);
								toDate = DateUtil.convertDateToUSFormat(toDate);
								query.append(" AND cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
							} else
							{
								query.append(" AND cr.actionTakenDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							}
						} else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("dueDate"))
						{
							String str[] = fromDate.split("-");
							if (str[0].length() < 3)
							{
								fromDate = DateUtil.convertDateToUSFormat(fromDate);
								toDate = DateUtil.convertDateToUSFormat(toDate);
								query.append(" AND cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
							} else
							{
								query.append(" AND cr.dueDate BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							}
						} else if (searchPeriodOn != null && searchPeriodOn.equalsIgnoreCase("All"))
						{
							if (fromDate != null & !fromDate.equalsIgnoreCase("All") && toDate != null && !toDate.equalsIgnoreCase("All"))
							{
								String str[] = fromDate.split("-");
								if (str[0].length() < 3)
								{
									fromDate = DateUtil.convertDateToUSFormat(fromDate);
									toDate = DateUtil.convertDateToUSFormat(toDate);
								}
								query.append(" AND (cr.actionTakenDate BETWEEN '" + fromDate + "' AND '" + toDate + "'");
								query.append(" OR cr.dueDate BETWEEN '" + fromDate + "' AND '" + toDate + "')");
							}
						}
						if (timmings != null && timmings.equalsIgnoreCase("onTime"))
						{
							query.append(" AND comp.dueDate < '" + DateUtil.getCurrentDateUSFormat() + "'");
						}
						if (timmings != null && timmings.equalsIgnoreCase("offTime"))
						{
							query.append(" AND comp.dueDate > '" + DateUtil.getCurrentDateUSFormat() + "'");
						}
						if (frequency != null && !frequency.equalsIgnoreCase("All") && !frequency.equalsIgnoreCase("undefined"))
						{
							query.append(" AND comp.frequency = '" + frequency + "'");
						}
						if (deptId != null && !deptId.equalsIgnoreCase("All") && !deptId.equalsIgnoreCase("undefined"))
						{
							query.append(" AND dept.id = '" + deptId + "'");
						}
						if (empName != null && !empName.equalsIgnoreCase("-1") && !empName.equalsIgnoreCase("undefined"))
						{
							String qry = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN employee_basic AS eb ON eb.id=cc.emp_id WHERE cc.moduleName='COMPL' AND eb.id="+empName;
							List dataList = cbt.executeAllSelectQuery(qry, connectionSpace);
							if(dataList!=null && dataList.size()>0)
							{
								query.append("AND (");
								for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
								{
									query.append(" comp.reminder_for LIKE '"+iterator.next()+"#' OR ");
								}
								query.delete(query.length()-3, query.length());
								query.append(")");
							}
						}
						if (actionStatus != null && !actionStatus.equalsIgnoreCase("ALL") && !actionStatus.equalsIgnoreCase("undefined"))
						{
							if (!actionStatus.equalsIgnoreCase("Upcoming") && !actionStatus.equalsIgnoreCase("Missed") && !actionStatus.equalsIgnoreCase("Pending"))
							{
								if (actionStatus.equalsIgnoreCase("Done"))
								{
									query.append(" AND cr.actionTaken IN('Done','Recurring')");
								} else
								{
									query.append(" AND cr.actionTaken='" + actionStatus + "'");
								}
							} else if (actionStatus.equalsIgnoreCase("Upcoming"))
							{
								StringBuilder complId = new StringBuilder();
								String qry = "SELECT start_date,start_time,id FROM compliance_details WHERE actionStatus ='Pending'";
								List dataList = cbt.executeAllSelectQuery(qry, connectionSpace);
								if (dataList != null && dataList.size() > 0)
								{
									int j = 1;
									for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
									{
										Object[] object2 = (Object[]) iterator.next();
										boolean statusDue = DateUtil.comparebetweenTwodateTime(object2[0].toString(), object2[1].toString(), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin());
										if (!statusDue)
										{
											if (j < dataList.size() - 1)
											{
												complId.append(object2[2].toString() + ",");
											} 
											else
											{
												complId.append(object2[2].toString());
											}
											j++;
										}
									}
									query.append(" AND comp.id IN (" + complId.toString().substring(0, complId.toString().length() - 1) + ") ");
								}
							} else if (actionStatus.equalsIgnoreCase("Missed"))
							{
								String str[] = fromDate.split("-");
								if (str[0].length() < 3)
								{
									fromDate = DateUtil.convertDateToUSFormat(fromDate);
									toDate = DateUtil.convertDateToUSFormat(toDate);
									query.append(" AND comp.dueDate BETWEEN '" + fromDate + "' AND  '" + DateUtil.getCurrentDateUSFormat() + "' AND comp.actionTaken='Pending'");
								} 
								else
								{
									query.append(" AND comp.dueDate BETWEEN '" + fromDate + "' AND '" + DateUtil.getCurrentDateUSFormat() + "' AND comp.actionTaken='Pending'");
								}
							} 
							else if (actionStatus.equalsIgnoreCase("Pending"))
							{
								query.append(" AND  comp.start_date = '" + DateUtil.getCurrentDateUSFormat() + "'  AND comp.actionTaken='Pending'");
							}

						}
						if (budget != null && !budget.equalsIgnoreCase("All") && !budget.equalsIgnoreCase("undefined") )
						{
							if (budget.equalsIgnoreCase("NB"))
							{
								query.append(" AND comp.budgetary='NA' OR comp.budgetary=' '");
							}
							else
							{
								query.append(" AND comp.budgetary!='NA' AND comp.budgetary!=' '");
							}
						}
						if (searching != null && !searching.equalsIgnoreCase(""))
						{
							query.append(" AND ts.taskName LIKE '%" + searching + "%'");
						}
						if (type != null && !type.equalsIgnoreCase("All") && !type.equalsIgnoreCase("-1"))
						{
							query.append(" AND ctyp.id='" + type + "' ");
						}
						if (taskName != null && !taskName.equalsIgnoreCase("All") && !taskName.equalsIgnoreCase("-1"))
						{
							query.append(" AND ts.id='" + taskName + "' ");
						}
						if (status != null && !status.equalsIgnoreCase("-1") && status.equalsIgnoreCase("Inactive"))
						{
							query.append(" AND comp.status='1' ");
						}
						else
						{
							query.append(" AND comp.status='0'");
						}
						query.append("  GROUP BY comp.id ORDER BY ts.taskName ASC ");
						//System.out.println("query.toString()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   " + query.toString());
						data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String time="",startDate="";
						String flag="";
						
						if (data != null && !data.isEmpty())
						{
							Object[] obdata = null;
							String complId = null;
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								int k = 0;
								time="00:00";
								flag="";
								obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("undefined") && !obdata[k].toString().equalsIgnoreCase(""))
									{
										if (gpv.getColomnName() != null)
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + ", " + obdata[k + 1].toString());
												if(gpv.getColomnName().equalsIgnoreCase("comp.start_date"))
												{
													startDate=obdata[k].toString().trim()+" "+obdata[k + 1].toString().trim();
												}
												else if(gpv.getColomnName().equalsIgnoreCase("comp.dueDate"))
												{
													boolean status=DateUtil.compareTwoDateAndTime(startDate,DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
													if(status)
													{
														//time=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(),obdata[k].toString(), obdata[k+1].toString());
														//To check Missed task///0 for missed and 1 for upcoming 
														if(DateUtil.compareTwoDateAndTime(obdata[k].toString().trim()+" "+obdata[k+1].toString().trim(),DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin()))
														{
															flag="0";
															time="-"+WHA.getTotalWorkingHours(obdata[k].toString().trim(),obdata[k+1].toString().trim(),DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimeHourMin(),"COMPL",cbt,connectionSpace);
														}
														//Time for pending task
														else
														{
															time=WHA.getTotalWorkingHours(DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimeHourMin(),obdata[k].toString().trim(),obdata[k+1].toString().trim(),"COMPL",cbt,connectionSpace);
														}
													}
													else
													{
														flag="1";
														String date[]=startDate.split(" ");
														time=WHA.getTotalWorkingHours(DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimeHourMin(),date[0].toString().trim(),date[1].toString().trim(),"COMPL",cbt,connectionSpace);
														//time=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(),date[0].toString(), date[1].toString());
													}
												}
											} 
											else if (gpv.getColomnName().equalsIgnoreCase("id"))
											{
												complId = obdata[k].toString();
												one.put(gpv.getColomnName(), obdata[k].toString());
												String query11 = "SELECT remind_date,remind_time FROM compliance_reminder WHERE compliance_id='" + complId + "'  AND status!='Done' AND reminder_code IN('D','R') ORDER BY remind_date ASC LIMIT 1";
												List remind = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (remind != null && !remind.isEmpty())
												{
													Object[] obj = (Object[]) remind.get(0);
													one.put("comp.reminder", DateUtil.convertDateToIndianFormat(obj[0].toString()) + ", " + obj[1].toString());
												} 
												else
												{
													one.put("comp.reminder", "NA");
												}
											}
											else if (gpv.getColomnName().equalsIgnoreCase("ts.taskName"))
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
												String query11 = "SELECT cd.id,cd.taskName FROM compliance_details AS cd INNER JOIN compliance_task AS ct ON ct.id=cd.taskName WHERE ct.taskName LIKE '%"+obdata[k].toString()+"%' AND cd.status=0 ";
												//System.out.println("history query>>>"+query11);
												List history = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (history != null)
												{
													one.put("comp.history", history.size());
												}
												else
												{
													one.put("comp.history", "NA");
												}
											}	
											else if (gpv.getColomnName().equalsIgnoreCase("comp.compid_prefix"))
											{
												one.put(gpv.getColomnName(), obdata[k].toString() + "" + obdata[k + 1].toString());
											} 
											else if (gpv.getColomnName().equalsIgnoreCase("comp.userName"))
											{
												one.put(gpv.getColomnName(), DateUtil.makeTitle(obdata[k].toString()));
											}
											else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
											{
												String query11 = "SELECT emp.empName,actionTakenDate,actionTakenTime FROM compliance_report AS cr INNER JOIN useraccount AS uc ON uc.userID=cr.userName  INNER JOIN employee_basic AS emp ON emp.useraccountid=uc.id  WHERE complID=" + complId + " ORDER BY cr.id DESC LIMIT 1";
												List actionBy = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (actionBy != null && !actionBy.isEmpty())
												{
													Object[] obj = (Object[]) actionBy.get(0);
													one.put(gpv.getColomnName(), obj[0].toString() + " On " + DateUtil.convertDateToIndianFormat(obj[1].toString()) + " At " + obj[2].toString());
													if(one.get("comp.actionStatus").equals("Done") || one.get("comp.actionStatus").equals("Ignore"))
													{
														String date[]=startDate.split(" ");
														//time=DateUtil.time_difference(date[0].toString(), date[1].toString(),obj[1].toString(),obj[2].toString());
														
														time=WHA.getTotalWorkingHours(date[0].toString(), date[1].toString(),obj[1].toString(),obj[2].toString(),"COMPL",cbt,connectionSpace);
													}
												}
												else
												{
													one.put(gpv.getColomnName(), "NA");
												}
											} else if (gpv.getColomnName().equalsIgnoreCase("comp.reminder_for"))
											{
												StringBuilder empName = new StringBuilder();
												String empId = obdata[k].toString().replace("#", ",").substring(0, (obdata[k].toString().length() - 1));
												String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
												List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
												for (Iterator iterator = data1.iterator(); iterator.hasNext();)
												{
													object = (Object) iterator.next();
													empName.append(object.toString() + ", ");
												}
												one.put(gpv.getColomnName(), empName.toString().substring(0, empName.toString().length() - 2));

											} else if (gpv.getColomnName().equalsIgnoreCase("comp.frequency"))
											{
												one.put(gpv.getColomnName(), new ComplianceReminderHelper().getFrequencyName(obdata[k].toString()));
											} 
											else
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
											}
										}
									} 
									else
									{
										if (gpv.getColomnName().equalsIgnoreCase("comp.escalation"))
										{
											one.put(gpv.getColomnName().toString(), "No");
										}
										else
										{
											one.put(gpv.getColomnName().toString(), "NA");
										}
									}
									k++;
								}
								
								one.put("comp.escalate_time", time);
								if(flag.equalsIgnoreCase("1")  && one.get("comp.actionStatus").equals("Pending"))
								{
									one.put("comp.actionStatus", "Upcoming Due");
								}
								else if(flag.equalsIgnoreCase("0") && one.get("comp.actionStatus").equals("Pending"))
								{
									one.put("comp.actionStatus", "Missed");
								}
								else if(one.get("comp.actionStatus").equals("Snooze"))
								{
									one.put("comp.escalate_time", "00:00");
								}
								
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							@SuppressWarnings("unused")
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							return SUCCESS;
						}
					}
				}
				retunString = SUCCESS;
				session.remove("masterViewProp");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			retunString = LOGIN;
		}
		return retunString;
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes" })
	public String viewCheckList()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				complDetails = new LinkedHashMap<String, String>();
				checkListDetails = new LinkedHashMap<String, String>();
				checkedCheckListDetails = new LinkedHashMap<String, String>();
				String taskName = null, remindDays = null, customFrqOn = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CT = new ComplianceUniversalAction();
				StringBuilder qry = new StringBuilder();
				qry.append(" SELECT dept.deptName,ctyp.tasktype,comp.uploadDoc,comp.uploadDoc1,comp.userName AS alloetdBy ");
				qry.append(",comp.date,comp.reminder_for,comp.budgetary,comp.frequency,comp.dueDate,comp.actionStatus ");
				qry.append(",comp.current_esc_level,comp.taskName,comp.dueTime,comp.remind_days ,comp.start_date,comp.start_time ");
				qry.append(" FROM compliance_details AS comp ");
				qry.append("INNER JOIN compliance_task AS ts ON ts.id=comp.taskName ");
				if (dataFrom != null && dataFrom.equalsIgnoreCase("History"))
				{
					qry.append("INNER JOIN compliance_report AS cr ON comp.id=cr.complID ");
				}
				qry.append("INNER JOIN compl_task_type AS ctyp ON ctyp.id=ts.taskType ");
				qry.append("INNER JOIN department AS dept ON dept.id=ts.departName WHERE ");
				if (dataFrom != null && dataFrom.equalsIgnoreCase("Task"))
				{
					qry.append("  comp.id= " + complID + "");
				} else
				{
					qry.append("  cr.id= " + complID + "");
				}

				//System.out.println(" >>>> " + qry.toString());

				List data = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						complDetails.put("Department", CT.getValueWithNullCheck(object[0]));
						complDetails.put("Task Type", CT.getValueWithNullCheck(object[1]));

						if (object[2] != null)
						{
							String str = object[2].toString().substring(object[2].toString().lastIndexOf("//") + 2, object[2].toString().length());
							String docName = str.substring(14, str.length());
							complDetails.put("Document 1", docName);
						} else
						{
							complDetails.put("Document 1", CT.getValueWithNullCheck(object[2]));
						}
						if (object[3] != null)
						{
							String str = object[3].toString().substring(object[3].toString().lastIndexOf("//") + 2, object[3].toString().length());
							String docName = str.substring(14, str.length());
							complDetails.put("Document 2", docName);
						}
						else
						{
							complDetails.put("Document 2", CT.getValueWithNullCheck(object[3]));
						}
						complDetails.put("Alloted By", DateUtil.makeTitle(CT.getValueWithNullCheck(object[4])));
						complDetails.put("Alloted On", DateUtil.convertDateToIndianFormat(CT.getValueWithNullCheck(object[5])));

						if (object[6] != null)
						{
							StringBuilder empName = new StringBuilder();
							String empId = object[6].toString().replace("#", ",").substring(0, (object[6].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							if (data1 != null && data1.size() > 0)
							{
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									Object object1 = (Object) iterator1.next();
									empName.append(object1.toString() + ", ");
								}
							}
							if (empName != null && !empName.toString().equalsIgnoreCase(""))
							{
								complDetails.put("Alloted To", empName.toString().substring(0, empName.toString().length() - 2));
							} 
							else
							{
								complDetails.put("Alloted To", "NA");
							}
						}
						else
						{
							complDetails.put("Alloted To", "NA");
						}

						remindDays = CT.getValueWithNullCheck(object[14]);
						if (!CT.getValueWithNullCheck(object[14]).equalsIgnoreCase("NA"))
						{
							customFrqOn = remindDays.split("#")[0];
							remindDays = remindDays.split("#")[1];
						}

						complDetails.put("Budgeted", CT.getValueWithNullCheck(object[7]));
						complDetails.put("Frequency", new ComplianceReminderHelper().getFrequencyName(CT.getValueWithNullCheck(object[8])));
						complDetails.put("Start From", DateUtil.convertDateToIndianFormat(CT.getValueWithNullCheck(object[15])) + " " + CT.getValueWithNullCheck(object[16]));
						complDetails.put("Due Date", DateUtil.convertDateToIndianFormat(CT.getValueWithNullCheck(object[9])) + " " + CT.getValueWithNullCheck(object[13]));
						complDetails.put("Next Due Date", DateUtil.convertDateToIndianFormat(new ConfigureComplianceAction().getUpdateDueDate(CT.getValueWithNullCheck(object[8]), CT.getValueWithNullCheck(object[9]), remindDays, customFrqOn)) + ", " + CT.getValueWithNullCheck(object[13]));
						complDetails.put("Current Status", CT.getValueWithNullCheck(object[10]));
						if (object[11] != null)
						{
							complDetails.put("Current Level", "Level " + object[11].toString());
						} else
						{
							complDetails.put("Current Level", "NA");
						}

						taskName = CT.getValueWithNullCheck(object[12]);
					}
				}
				qry.setLength(0);
				if (dataFrom != null && dataFrom.equalsIgnoreCase("Task"))
				{
					qry.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE taskId=" + taskName + " ");
					List tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if (tempList != null && tempList.size() > 0)
					{
						int i = 1;
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							checkListDetails.put(String.valueOf(i), object[1].toString());
							i++;
						}
					}
					if (checkListDetails.size() == 0)
					{
						checkListDetails.put(" ", "There is no check list");
					}
				} else if (dataFrom != null && dataFrom.equalsIgnoreCase("History"))
				{
					List tempList = new createTable().executeAllSelectQuery("SELECT checkid FROM compliance_report WHERE id =" + complID, connectionSpace);
					if (tempList != null && tempList.size() > 0)
					{
						qry.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE id IN(" + tempList.get(0).toString() + ") ");
						tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
						qry.setLength(0);
						if (tempList != null && tempList.size() > 0)
						{
							ArrayList<String> tempList1 = null;
							for (Object obj : tempList)
							{
								Object[] obdata = (Object[]) obj;
								tempList1 = new ArrayList<String>();
								tempList1.add(obdata[0].toString());
								tempList1.add(obdata[1].toString());
								checkedCheckList.add(tempList1);
							}
							tempList.clear();
						}
					}
					//System.out.println(checkedCheckList.size() + " checkedCheckList");
					qry.append("SELECT id,completion_tip FROM compl_task_completion_tip WHERE taskId=" + taskName + " ");
					//System.out.println(" ###### " + qry.toString());
					tempList = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if (tempList != null && tempList.size() > 0)
					{
						ArrayList<String> tempList1 = null;
						for (Object obj : tempList)
						{
							Object[] obdata = (Object[]) obj;
							tempList1 = new ArrayList<String>();
							tempList1.add(obdata[0].toString());
							tempList1.add(obdata[1].toString());
							checkList.add(tempList1);
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}

		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String DocDownload() throws IOException
	{
		//System.out.println(getDocName() + "in doc" + getComplID() + " download" + getDataFor());
		String result = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List docName = null;
		try
		{
			if (getDocName() != null && getDocName() != "" && getDataFor() != null && getDataFor() != "")
			{
				if (getDataFor().equalsIgnoreCase("configDoc1"))
				{
					docName = cbt.executeAllSelectQuery("SELECT document_config_1 FROM compliance_report WHERE actionTaken='Pending' AND complID=" + getComplID() + ";", connectionSpace);
				}
				if (getDataFor().equalsIgnoreCase("configDoc2"))
				{
					docName = cbt.executeAllSelectQuery("SELECT document_config_2 FROM compliance_report WHERE actionTaken='Pending' AND complID=" + getComplID() + ";", connectionSpace);
				} else if (getDataFor().equalsIgnoreCase("doc1"))
				{
					docName = cbt.executeAllSelectQuery("SELECT document_takeaction FROM compliance_report WHERE id=" + getDocName() + ";", connectionSpace);
				} else if (getDataFor().equalsIgnoreCase("doc2"))
				{
					docName = cbt.executeAllSelectQuery("SELECT document_action_1 FROM compliance_report WHERE id=" + getDocName() + ";", connectionSpace);
				} else if (getDataFor().equalsIgnoreCase("doc3"))
				{
					docName = cbt.executeAllSelectQuery("SELECT document_action_2 FROM compliance_report WHERE id=" + getDocName() + ";", connectionSpace);
				} else if (getDataFor().equalsIgnoreCase("Document 1"))
				{
					docName = cbt.executeAllSelectQuery("SELECT  uploadDoc FROM  compliance_details WHERE id=" + getDocName() + ";", connectionSpace);
				} else if (getDataFor().equalsIgnoreCase("Document 2"))
				{
					docName = cbt.executeAllSelectQuery("SELECT  uploadDoc1 FROM  compliance_details WHERE id=" + getDocName() + ";", connectionSpace);
				}
				if (docName.get(0) != null && !docName.get(0).equals(""))
				{
					fileName = (String) docName.get(0);
					//System.out.println(" file name " + fileName);
					File file = new File(fileName);
					inputStream = new FileInputStream(file);
					fileName = file.getName();
					//System.out.println("after doc download");
					result = SUCCESS;
				} else
				{
					//System.out.println("in Error");
					result = ERROR;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			inputStream.close();
		}
		return result;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewFullViewFormatter()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CT = new ComplianceUniversalAction();
				ComplianceReminderHelper RH = new ComplianceReminderHelper();
				complDetails = new LinkedHashMap<String, String>();
				StringBuilder qry = new StringBuilder();
				/*qry.append(" SELECT dept.deptName,comp.userName AS alloetdBy ");
				qry.append(",comp.reminder_for,comp.frequency,comp.dueDate ");
				qry.append(",comp.dueTime,comp.action_type FROM compliance_details AS comp ");
				qry.append("INNER JOIN compliance_task AS ts ON ts.id=comp.taskName ");
				qry.append("INNER JOIN department AS dept ON dept.id=ts.departName WHERE ts.taskName LIKE '%"+taskName+"%'");*/
				qry.append(" SELECT dept.deptName,comp.userName AS alloetdBy ");
				qry.append(",comp.reminder_for,comp.frequency,min(cr.dueDate) ");
				qry.append(",min(cr.dueTime),comp.action_type FROM compliance_details AS comp ");
				qry.append("INNER JOIN compliance_task AS ts ON ts.id=comp.taskName ");
				qry.append("INNER JOIN compliance_report AS cr ON comp.id=cr.complID ");
				qry.append("INNER JOIN department AS dept ON dept.id=ts.departName WHERE comp.id= " + complID + " GROUP By dept.deptName");
				//comp.id= " + complID + "
				//System.out.println("history query>>>>>>"+qry);
				List data = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				if (data != null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						complDetails.put("Department", CT.getValueWithNullCheck(object[0]));
						complDetails.put("Alloted By", DateUtil.makeTitle(CT.getValueWithNullCheck(object[1])));
						if (object[2] != null)
						{
							StringBuilder empName = new StringBuilder();
							String empId = object[2].toString().replace("#", ",").substring(0, (object[2].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							if (data1 != null && data1.size() > 0)
							{
								for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
								{
									Object object1 = (Object) iterator1.next();
									empName.append(object1.toString() + ", ");
								}
							}
							if (empName != null && !empName.toString().equalsIgnoreCase(""))
							{
								complDetails.put("Alloted To", empName.toString().substring(0, empName.toString().length() - 2));
							}
							else
							{
								complDetails.put("Alloted To", "NA");
							}
						}
						else
						{
							complDetails.put("Alloted To", "NA");
						}
						// complDetails.put("Frequency",new
						// ComplianceReminderHelper().getFrequencyName(
						// CT.getValueWithNullCheck(object[3])));
						complDetails.put("Started From", DateUtil.convertDateToIndianFormat(CT.getValueWithNullCheck(object[4])) + ", " + CT.getValueWithNullCheck(object[5]));
						complDetails.put("Frequency", RH.getFrequencyName(object[3].toString()));
						complDetails.put("Ownership", DateUtil.makeTitle(CT.getValueWithNullCheck(object[6])));
					}
				}
				List<GridDataPropertyView> sessionvalues = new ArrayList<GridDataPropertyView>();
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();

				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.complainceId");
				gpv.setHeadingName("Task ID");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.dueDate");
				gpv.setHeadingName("Due On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.dueTime");
				gpv.setHeadingName("Due Time");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(110);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenDate");
				gpv.setHeadingName("Action Taken On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(110);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTakenTime");
				gpv.setHeadingName("Action Taken On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(80);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.userName");
				gpv.setHeadingName("Action Taken By");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(90);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.actionTaken");
				gpv.setHeadingName("Status");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				gpv.setFormatter("viewStatus");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.checkList");
				gpv.setHeadingName("Check List");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(30);
				gpv.setFormatter("checkList");
				viewPageColumns.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_date");
				gpv.setHeadingName("TAT");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(100);
				gpv.setFormatter("viewTAT");
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("comp.escalate_time");
				gpv.setHeadingName("TATTime");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.budgetary");
				gpv.setHeadingName("Budgeted");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.difference");
				gpv.setHeadingName("Cost");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				viewPageColumns.add(gpv);
				sessionvalues.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("cr.reminder");
				gpv.setHeadingName("Reminder");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setWidth(60);
				gpv.setFormatter("reminder");
				viewPageColumns.add(gpv);

				session.put("masterViewProp", sessionvalues);

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String fullViewActivity()
	{
		String retunString = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder ids=new StringBuilder();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" SELECT  comp.id FROM compliance_details AS comp ");
				query1.append("INNER JOIN compliance_task AS ts ON ts.id=comp.taskName ");
				query1.append("WHERE ts.taskName LIKE '%"+taskName+"%' AND comp.status=0");
				//System.out.println(">>>>>>>>>"+query1);
				List list=cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if(list!=null && list.size()>0 )
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						ids.append(iterator.next()+",");
					}
					if(ids.length()>0)
					{
						ids.deleteCharAt(ids.length()-1);
					}
				}
				query1.delete(0, query1.length());
				query1.append("select count(*) from compliance_report");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName() != null)
								{
									if (gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("cr.id,");
									}
									else
									{
										query.append(gpv.getColomnName().toString() + ",");
									}
								}
							} 
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM compliance_report AS cr " + "INNER JOIN compliance_details AS comp ON comp.id=cr.complID  " + "WHERE comp.status=0 AND cr.id IN(SELECT max(id) from compliance_report WHERE  complID IN ("+ids+") GROUP BY complainceId) ");
						//query.append(" ORDER BY cr.actionTakenDate ASC");
						//System.out.println("query1.toString():::::::::::::;"+query.toString());
						data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String cIds[]=ids.toString().split(",");
						if (data != null)
						{
							Object[] obdata = null;
							String dueDate = null;
							int j=0;
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								int k = 0;
								obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase(""))
									{
										if (gpv.getColomnName() != null)
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												if (gpv.getColomnName().equalsIgnoreCase("cr.dueDate"))
												{
													dueDate = obdata[k].toString();
												}
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + ", " + obdata[k + 1].toString());
											} 
											else if (gpv.getColomnName().equalsIgnoreCase("id"))
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
												String query11 = "SELECT remind_date,remind_time FROM compliance_reminder WHERE compliance_id='" + cIds[k] + "' AND remind_date BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + dueDate + "' AND status!='Done' AND reminder_code IN('D','R') ORDER BY remind_date ASC LIMIT 1";
												//System.out.println("remind query>>>>>"+query11);
												List remind = cbt.executeAllSelectQuery(query11, connectionSpace);
												if (remind != null && !remind.isEmpty())
												{
													Object[] obj = (Object[]) remind.get(0);

													one.put("cr.reminder", DateUtil.convertDateToIndianFormat(obj[0].toString()) + ", " + obj[1].toString());
												} 
												else
												{
													one.put("cr.reminder", "NA");
												}
											} else if (gpv.getColomnName().equalsIgnoreCase("cr.userName"))
											{
												one.put(gpv.getColomnName(), DateUtil.makeTitle(Cryptography.decrypt(obdata[k].toString(), DES_ENCRYPTION_KEY)));
											} else
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
											}
										}
									} else
									{
										one.put(gpv.getColomnName().toString(), "NA");
									}
									k++;
								}
								Listhb.add(one);
								j++;
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							@SuppressWarnings("unused")
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							return SUCCESS;
						}
					}
				}
				retunString = SUCCESS;
				session.remove("masterViewProp");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			retunString = LOGIN;
		}
		return retunString;

	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getSearchPeriodOn()
	{
		return searchPeriodOn;
	}

	public void setSearchPeriodOn(String searchPeriodOn)
	{
		this.searchPeriodOn = searchPeriodOn;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}

	public Map<String, String> getHeadermap()
	{
		return headermap;
	}

	public void setHeadermap(Map<String, String> headermap)
	{
		this.headermap = headermap;
	}

	public String getComplID()
	{
		return complID;
	}

	public void setComplID(String complID)
	{
		this.complID = complID;
	}

	public Map<String, String> getComplDetails()
	{
		return complDetails;
	}

	public void setComplDetails(Map<String, String> complDetails)
	{
		this.complDetails = complDetails;
	}

	public Map<String, String> getCheckListDetails()
	{
		return checkListDetails;
	}

	public void setCheckListDetails(Map<String, String> checkListDetails)
	{
		this.checkListDetails = checkListDetails;
	}

	public List<ComplianceDashboardBean> getDoneDetails()
	{
		return doneDetails;
	}

	public void setDoneDetails(List<ComplianceDashboardBean> doneDetails)
	{
		this.doneDetails = doneDetails;
	}

	public Map<String, String> getStatusDetails()
	{
		return statusDetails;
	}

	public void setStatusDetails(Map<String, String> statusDetails)
	{
		this.statusDetails = statusDetails;
	}

	public Map<String, String> getEscalationDetails()
	{
		return escalationDetails;
	}

	public void setEscalationDetails(Map<String, String> escalationDetails)
	{
		this.escalationDetails = escalationDetails;
	}

	public List<ComplianceDashboardBean> getAllotDetails()
	{
		return allotDetails;
	}

	public void setAllotDetails(List<ComplianceDashboardBean> allotDetails)
	{
		this.allotDetails = allotDetails;
	}

	public List<ComplianceDashboardBean> getAllotByDetails()
	{
		return allotByDetails;
	}

	public void setAllotByDetails(List<ComplianceDashboardBean> allotByDetails)
	{
		this.allotByDetails = allotByDetails;
	}

	public Map<String, String> getReminderDetails()
	{
		return reminderDetails;
	}

	public void setReminderDetails(Map<String, String> reminderDetails)
	{
		this.reminderDetails = reminderDetails;
	}

	public List<ComplianceDashboardBean> getBudgetDetails()
	{
		return budgetDetails;
	}

	public void setBudgetDetails(List<ComplianceDashboardBean> budgetDetails)
	{
		this.budgetDetails = budgetDetails;
	}

	public String getDataFrom()
	{
		return dataFrom;
	}

	public void setDataFrom(String dataFrom)
	{
		this.dataFrom = dataFrom;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public Map<String, String> getDeptName()
	{
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName)
	{
		this.deptName = deptName;
	}

	public String getBudget()
	{
		return budget;
	}

	public void setBudget(String budget)
	{
		this.budget = budget;
	}

	public String getTimmings()
	{
		return timmings;
	}

	public void setTimmings(String timmings)
	{
		this.timmings = timmings;
	}

	public String getShareStatus()
	{
		return shareStatus;
	}

	public void setShareStatus(String shareStatus)
	{
		this.shareStatus = shareStatus;
	}

	public String getSearching()
	{
		return searching;
	}

	public void setSearching(String searching)
	{
		this.searching = searching;
	}

	public Map<String, String> getTaskTypeMap()
	{
		return taskTypeMap;
	}

	public void setTaskTypeMap(Map<String, String> taskTypeMap)
	{
		this.taskTypeMap = taskTypeMap;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getDoneDoc()
	{
		return doneDoc;
	}

	public void setDoneDoc(String doneDoc)
	{
		this.doneDoc = doneDoc;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public Map<String, String> getWorkingmap()
	{
		return workingmap;
	}

	public void setWorkingmap(Map<String, String> workingmap)
	{
		this.workingmap = workingmap;
	}

	public Map<String, String> getCheckedCheckListDetails()
	{
		return checkedCheckListDetails;
	}

	public void setCheckedCheckListDetails(Map<String, String> checkedCheckListDetails)
	{
		this.checkedCheckListDetails = checkedCheckListDetails;
	}

	public ArrayList<ArrayList<String>> getCheckList()
	{
		return checkList;
	}

	public void setCheckList(ArrayList<ArrayList<String>> checkList)
	{
		this.checkList = checkList;
	}

	public ArrayList<ArrayList<String>> getCheckedCheckList()
	{
		return checkedCheckList;
	}

	public void setCheckedCheckList(ArrayList<ArrayList<String>> checkedCheckList)
	{
		this.checkedCheckList = checkedCheckList;
	}

	public Map<String, String> getDeptMap()
	{
		return deptMap;
	}

	public void setDeptMap(Map<String, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public String getDepartId()
	{
		return departId;
	}

	public void setDepartId(String departId)
	{
		this.departId = departId;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request=arg0;
	}

}