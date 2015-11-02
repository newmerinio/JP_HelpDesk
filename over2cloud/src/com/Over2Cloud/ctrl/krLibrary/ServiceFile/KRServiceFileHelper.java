package com.Over2Cloud.ctrl.krLibrary.ServiceFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.krLibrary.KRPojo;

public class KRServiceFileHelper
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	@SuppressWarnings({  "rawtypes" })
	public void checkReminder(SessionFactory connection)
	{
		try
		{
			KRServiceHelper srvcHelper = new KRServiceHelper();

			List validData = srvcHelper.getReminderData(connection);
			if (validData != null && validData.size() > 0)
			{
				String krName = null, krBrief = null,shareId=null, tags = null, accessType = null, frequency = null, dept = null, group = null, remindFor = null, subgroup = null, krId = null, actionTaken = null, dueDate = null, shareDate = null, attachmentPath = null;
				CommunicationHelper cmnHelper = new CommunicationHelper();
				for (Iterator iterator = validData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					krName = getValueWithNullCheck(object[1]);
					frequency = getValueWithNullCheck(object[10]);
					krBrief = getValueWithNullCheck(object[3]);
					tags = getValueWithNullCheck(object[4]);
					accessType = getValueWithNullCheck(object[8]);
					dept = getValueWithNullCheck(object[5]);
					group = getValueWithNullCheck(object[6]);
					dueDate = getValueWithNullCheck(object[11]);
					subgroup = getValueWithNullCheck(object[7]);
					attachmentPath = getValueWithNullCheck(object[9]);
					krId = getValueWithNullCheck(object[2]);
					actionTaken = getValueWithNullCheck(object[14]);
					remindFor = getValueWithNullCheck(object[13]);
					shareDate = getValueWithNullCheck(object[12]);
					shareId=getValueWithNullCheck(object[0]);
					boolean status = srvcHelper.updateKRAccordingToFrequency(shareId, connection,frequency,dueDate);
					if (status)
					{
						if (remindFor != null && !remindFor.equals(""))
						{
							String mailSubject = null, mailTitle = null;
							
								mailSubject = "Urgent Attention for KR Action Today!";
								mailTitle = mailSubject;
								/*dueDate = "Today i.e " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
								msgText = "Operation Task Alert: Task Name: " + taskName + ", is having Due Date Today.";

							*/
							

							String contactId = remindFor.replace("#", ",").substring(0, (remindFor.toString().length() - 1));
							String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId
									+ ")";
							List data1 = new createTable().executeAllSelectQuery(query2, connection);
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								frequency = getFrequencyName(frequency);

								StringBuilder str = new StringBuilder();
								String mappedTeam = null;
								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[4].toString().equalsIgnoreCase(object3[4].toString()))
									{
										str.append(object3[2].toString() + ", ");
									}
								}
								if (str != null && str.toString().length() > 0)
								{
									mappedTeam = str.toString().substring(0, str.toString().length() - 2);
								}
								if (mappedTeam == null)
									mappedTeam = "You";
								else
									mappedTeam = mappedTeam + " and You";

								/*if (remidMode.equalsIgnoreCase("SMS"))
								{
									if (object2[0] != null && object2[0].toString() != "")
										cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Compliance", connection);

								}*/
								if (dueDate!=null && !dueDate.equalsIgnoreCase("NA")) 
								{
									dueDate=DateUtil.convertDateToIndianFormat(dueDate);
								}
								if (shareDate!=null && !shareDate.equalsIgnoreCase("NA")) 
								{
									shareDate=DateUtil.convertDateToIndianFormat(dueDate);
								}
									String mailText = getConfigMail(krName, krId, krBrief, dept, group, subgroup, mappedTeam, dueDate, object2[2].toString(), object2[3].toString(), mailTitle, actionTaken,shareDate,accessType,tags,frequency);
									if (object2[1] != null && object2[1].toString() != "")
										cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0",attachmentPath, "KR", connection);

								
							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}
	public String getConfigMail(String taskName, String taskType, String taskBrief, String dept, String group, String subgroup, String mappedTeam, String dueDate, String empName, String empId, String mailTitle, String status, String shareDate, String accessType, String tags,String frequency)
	{
		StringBuilder mailtext = new StringBuilder("");

		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		mailtext.append(mailTitle);
		mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> KR&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ taskName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> KR&nbsp;ID:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ taskType + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ taskBrief + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Share&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ shareDate + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Department:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ dept + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Group:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ group + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub Group:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ subgroup + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ frequency + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ mappedTeam + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ dueDate + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Access Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ accessType + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Tags:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ tags + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
				+ status + "</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public void checkRecurring(SessionFactory connection)
	{
		KRServiceHelper srvcHelper = new KRServiceHelper();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List validData = srvcHelper.getRecurringData(connection);
		if (validData != null && validData.size() > 0)
		{
			for (Iterator iterator = validData.iterator(); iterator.hasNext();)
			{
				//String frequency = null;
				
				String actionDate = null, shareDue = null, userName = null, shareId = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				Object object[] = (Object[]) iterator.next();

				if (object[0] != null)
					shareId = object[0].toString();

				/*if (object[1] != null)
					frequency = object[1].toString();*/

				if (object[2] != null)
					actionDate = object[2].toString();
				
				if (object[3] != null)
					shareDue = object[3].toString();

				if (object[4] != null)
					userName = object[4].toString();
		
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", object[0].toString());
					setVariables.put("dueDate", actionDate);
					setVariables.put("dueShareDate", shareDue);
					setVariables.put("actionStatus", "Pending");
					setVariables.put("actionTaken", "Pending");
				
					cbt.updateTableColomn("kr_share_data", setVariables, whereCondition, connection);
				
				try
				{
					// data insert into kr_share report table.
					insertData = new ArrayList<InsertDataTable>();
				
					insertData=setParameterInObj("actionBy",new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY),insertData);
					insertData=setParameterInObj("actionTaken","Pending",insertData);
					insertData=setParameterInObj("krSharingId",shareId,insertData);
					insertData=setParameterInObj("shareDate",shareDue,insertData);
					insertData=setParameterInObj("actionDate",actionDate,insertData);
					insertData=setParameterInObj("actionTakenDate",DateUtil.getCurrentDateUSFormat(),insertData);
					insertData=setParameterInObj("actionTakenTime",DateUtil.getCurrentTimeHourMin(),insertData);
				
					String query = "SELECT kr_starting_id FROM kr_upload_data AS upload INNER JOIN kr_share_data AS krShare ON krShare.doc_name=upload.id WHERE krShare.id="+shareId;
					List codeData=cbt.executeAllSelectQuery(query, connection);
					if (codeData!=null && !codeData.isEmpty())
					{
						query="SELECT krId,krUpload FROM kr_share_report_data WHERE krSharingId ="+shareId+" ORDER BY id DESC LIMIT 0,1";
						List value=cbt.executeAllSelectQuery(query, connection);
						if (value!=null && !value.isEmpty() && value.get(0)!=null )
						{
							String finalCode=null;
							Object[] obj=(Object[])value.get(0);
							if (obj[0].toString().equalsIgnoreCase(codeData.get(0).toString()))
							{
								finalCode=obj[0].toString()+".1";
							}
							else
							{
								   String a = obj[0].toString();
								   String b = codeData.get(0).toString();
								   String c = a.substring(0, b.length()+1);
								   String d = a.substring(b.length()+1, a.length());
								   finalCode=c+""+String.valueOf(Integer.parseInt(d)+1);
							}
							insertData = setParameterInObj("krId",finalCode,insertData);
							insertData = setParameterInObj("krUpload",obj[1].toString(),insertData);
						}
					}
					cbt.insertIntoTable("kr_share_report_data", insertData, connection);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	private List<InsertDataTable> setParameterInObj(String paramName,String paramValue,List<InsertDataTable> insertData)
	{
		InsertDataTable object=new InsertDataTable();
		object.setColName(paramName);
		object.setDataName(paramValue);
		insertData.add(object);
		return insertData;
	}
	@SuppressWarnings({ "rawtypes", "null" })
	public void checkTodayPending(SessionFactory connection)
	{
		try
		{
			List data = null;
			String working_day = null;
			boolean existFlag = false;
			KRServiceHelper srvcHelper = new KRServiceHelper();
			CommunicationHelper cmnHelper = new CommunicationHelper();
			String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()),connection);
			if(working_date.equals(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat())))
			{
				working_day= DateUtil.getDayofDate(working_date);
				existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
				if(existFlag)
				{
					working_date =DateUtil.getNewDate("day", -1, working_date);
					data = srvcHelper.getTodayReminderFor(connection,working_date,"equalCondition");
					existFlag = false;
				}
				else
				{
					working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 1,working_date),connection);
					working_day= DateUtil.getDayofDate(working_date);
					existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
					if(existFlag)
					{
						working_date =DateUtil.getNewDate("day", -1, working_date);
						data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
						existFlag = false;
					}
					else
					{
						
						working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 2,working_date),connection);
						working_day= DateUtil.getDayofDate(working_date);
						existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
						if(existFlag)
						{
							working_date =DateUtil.getNewDate("day", -2, working_date);
							data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
							existFlag = false;
						}
						else
						{
							working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 3,working_date),connection);
							working_day= DateUtil.getDayofDate(working_date);
							existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
							if(existFlag)
							{
								working_date =DateUtil.getNewDate("day", -3, working_date);
								data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
								existFlag = false;
							}
							else
							{
								working_date =DateUtil.getNewDate("day", -3, working_date);
								data = srvcHelper.getTodayReminderFor(connection,working_date,"equalCondition");
								existFlag = false;
							}
						}
					}
				}
			}
			else
			{
				//between & working date
				working_day= DateUtil.getDayofDate(working_date);
				existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
				if(existFlag)
				{
					working_date = DateUtil.getNewDate("day", -1, working_date);
					data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
					existFlag = false;
				}
				else
				{
					working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 1,working_date),connection);
					working_day= DateUtil.getDayofDate(working_date);
					existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
					if(existFlag)
					{
						working_date =DateUtil.getNewDate("day", -1, working_date);
						data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
						existFlag = false;
					}
					else
					{
						working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 2,working_date),connection);
						working_day= DateUtil.getDayofDate(working_date);
						existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
						if(existFlag)
						{
							working_date =DateUtil.getNewDate("day", -2, working_date);
							data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
							existFlag = false;
						}
						else
						{
							working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N",DateUtil.getNewDate("day", 3,working_date),connection);
							working_day= DateUtil.getDayofDate(working_date);
							existFlag=new HelpdeskUniversalHelper().isExist("working_timing", "day", working_day, "moduleName", "KR", "", "", "", "", connection);
							if(existFlag)
							{
								working_date =DateUtil.getNewDate("day", -3, working_date);
								data = srvcHelper.getTodayReminderFor(connection,working_date,"betweenCondition");
								existFlag = false;
							}
							else
							{
								working_date =DateUtil.getNewDate("day", -3, working_date);
								data = srvcHelper.getTodayReminderFor(connection,working_date,"equalCondition");
								existFlag = false;
							}
						}
					}
				
				}
			}
			String mailSendDate = null,mailSendTime=null,id=null;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = cbt.executeAllSelectQuery("SELECT date,time,id FROM compliance_todayTask_config WHERE moduleName='KR' AND date='"+DateUtil.getCurrentDateUSFormat()+"'", connection);
			if(dataList==null && dataList.size()==0)
			{
				InsertDataTable ob = null;
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("moduleName");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("compliance_todayTask_config", Tablecolumesaaa, connection);
				
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName("08:00");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("moduleName");
				ob.setDataName("KR");
				insertData.add(ob);
				
				cbt.insertIntoTable("compliance_todayTask_config", insertData, connection);
				
				//Map<String, Object> setVariables = new HashMap<String, Object>();
				//Map<String, Object> whereCondition = new HashMap<String, Object>();
				//setVariables.put("date", DateUtil.getCurrentDateUSFormat());
				//setVariables.put("time", "8:00");
				//setVariables.put("moduleName", "COMPL");
				//boolean flag = cbt.updateTableColomn("compliance_todayTask_config", setVariables, whereCondition, connection);
		
			}
			else if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						mailSendDate = object[0].toString();
						mailSendTime = object[1].toString();
						id = object[2].toString();
					}
				}
				if(mailSendDate!=null && mailSendTime!=null)
				{
					boolean status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), mailSendDate + " " + mailSendTime);
					if (!status)
					{
						Map<String, Object> setVariables = new HashMap<String, Object>();
						Map<String, Object> whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", id);
						setVariables.put("date", DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()));
						boolean flag = cbt.updateTableColomn("compliance_todayTask_config", setVariables, whereCondition, connection);
						if(flag==false)
						{
							mailSendDate = null;
							mailSendTime = null;
						}
					}
					else
					{
						mailSendDate = null;
						mailSendTime = null;
					}
				}
			}
			if(mailSendDate!=null && mailSendTime!=null)
			{
				Map<String, String> idWithData = new LinkedHashMap<String, String>();
				StringBuilder strBuilder = new StringBuilder();
				StringBuilder contactIdBuilder = new StringBuilder(",");
				String frequency = null, shareId = null, shareDueDate = null, dueDate = null, actionType = null, reminderFor = null;
				String status = null, krName = null, krTag = null, krBrief = null, emailId = null, contactName = null, comments=null;
				String krId=null;
				
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						if (object[1] != null && !object[1].toString().equalsIgnoreCase(""))
						{
							strBuilder.append(object[1].toString()+", ");
							idWithData.put(object[0].toString(), ", " + object[1].toString()+",");
						}
					}
					if (strBuilder != null)
					{
						String contactId[] = strBuilder.toString().split(",");
						for (int j = 0; j < contactId.length; j++)
						{
							if (!contactIdBuilder.toString().contains("," + contactId[j].trim() + ","))
							{
								contactIdBuilder.append(contactId[j].trim() + ",");
							}
						}
						contactIdBuilder = contactIdBuilder.replace(0, 1, "");
						contactId = contactIdBuilder.toString().split(",");

						for (int i = 0; i < contactId.length; i++)
						{
							System.out.println("contactId[i].trim()    "+contactId[i].trim());
							StringBuilder compIdList = new StringBuilder();
							Iterator<Entry<String, String>> hiterator = idWithData.entrySet().iterator();
							while (hiterator.hasNext())
							{
								Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
								System.out.println("paramPair.getValue() ==========="+paramPair.getValue());
								System.out.println("paramPair.getValue() ==========="+", " + contactId[i].trim() + ",");
								if (paramPair.getValue().contains(", " + contactId[i].trim() + ","))
								{
									compIdList.append(paramPair.getKey() + ",");
								}
							}
							System.out.println("compIdList  ::::::   "+compIdList);
							if (compIdList != null &&  !compIdList.toString().equalsIgnoreCase(""))
							{
								KRPojo obj = null;
								String mappedTeam = null;
								String comID = compIdList.toString().substring(0, (compIdList.toString().length() - 1));
								System.out.println("comID    "+comID);
								StringBuilder query = new StringBuilder();
								query.append("SELECT krShare.id,upload.kr_name,upload.kr_starting_id,upload.kr_brief,upload.tag_search,dept.deptName,krGroup.groupName,subGroup.subGroupName, ");
								query.append(" krShare.accessType,upload.upload1,krShare.frequency,krShare.dueDate,krShare.dueShareDate,krShare.empName,krShare.actionTaken FROM kr_share_data AS krShare  ");
								query.append(" LEFT JOIN kr_upload_data AS upload ON krShare.doc_name=upload.id ");
								query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id ");
								query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
								query.append(" LEFT JOIN department AS dept ON krGroup.dept=dept.id ");
								query.append("WHERE krShare.id IN("+comID+")");
								System.out.println("QUERY US AS :::  "+query.toString());
								List data1 = new createTable().executeAllSelectQuery(query.toString(), connection);
								if (data1 != null && data1.size() > 0)
								{
									List<KRPojo> listForMail = new ArrayList<KRPojo>();
									for (Iterator iterator = data1.iterator(); iterator.hasNext();)
									{
										obj = new KRPojo();
										Object[] object = (Object[]) iterator.next();
										if (object != null)
										{
											if(object[0] != null)
											{
												shareId = object[0].toString();
											}
											if(object[1] != null)
											{
												krName = object[1].toString();
											}
											else
											{
												krName = "NA";
											}
											if (object[2] != null)
											{
												krId = object[2].toString();
											}
											else
											{
												krId = "NA";
											}
											if(object[10] != null)
											{
												frequency=getFrequencyName(object[10].toString());
											}
											else
											{
												frequency = "NA";
											}
											if (object[3] != null)
											{
												krBrief = object[3].toString();
											}
											else
											{
												krBrief = "NA";
											}
											if (object[4] != null)
											{
												krTag = object[4].toString();
											}
											else
											{
												krTag = "NA";
											}
											if (object[11] != null)
											{
												if(object[11].toString().equals(DateUtil.getCurrentDateUSFormat()))
												{
													comments = "Today Action Date";
												}
												else
												{
													comments = "Pre-Remind due to holiday or week end";
												}
												dueDate = DateUtil.convertDateToIndianFormat(object[11].toString());
											}
											else
											{
												dueDate = "NA";
											}
											if (object[12] != null)
											{
												shareDueDate = DateUtil.convertDateToIndianFormat(object[12].toString());
											}
											else
											{
												shareDueDate = "NA";
											}
											if (object[13] != null)
											{
												
												String contId = object[13].toString();
												StringBuilder query2 = new StringBuilder();
												query2.append("SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId ");
												query2.append("FROM compliance_contacts AS cc ");
												query2.append("INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
												query2.append("WHERE cc.work_status=0 AND cc.id IN("+ contId + ")");
												List empDataList = new createTable().executeAllSelectQuery(query2.toString(), connection);
												if (empDataList != null && empDataList.size() > 0)
												{
													String mobileNo = null;
													for (Iterator iterator2 = empDataList.iterator(); iterator2.hasNext();)
													{
														Object object2[] = (Object[]) iterator2.next();
														StringBuilder str = new StringBuilder();
														if (object2[3] != null)
														{
															if (contactId[i].trim().equals(object2[4].toString()))
															{
																emailId = object2[1].toString();
																contactName = object2[2].toString();
																
															}
														}
														if (!contactId[i].trim().toString().equalsIgnoreCase(object2[4].toString()))
														{
															str.append(object2[2].toString() + ",");
														}
														System.out.println("-----------------------------------------"+str);
													
														if (str != null && str.toString().length() > 0)
														{
															mappedTeam = str.toString().substring(0, str.toString().length() - 1);
														}
														
													}
													if (mappedTeam == null)
														mappedTeam = "You";
													else
														mappedTeam = mappedTeam + " and You";
												}
											}
											if (object[14] != null)
											{
												status = object[14].toString();
											}
											else
											{
												status = "NA";
											}
											if (object[8] != null)
											{
												actionType = DateUtil.makeTitle(object[8].toString());
											}
											else
											{
												actionType = "NA";
											}
											obj.setShareId(shareId);
											obj.setKrName(krName);
											obj.setKrBrief(krBrief);
											obj.setKrTags(krTag);
											obj.setKrFreq(frequency);
											obj.setKrId(krId);
											obj.setKrDueDate(dueDate);
											obj.setKrDueDateReq(shareDueDate);
											obj.setKrShareTo(mappedTeam);
											obj.setActionStatus(status);
											obj.setKrAccessType(actionType);
											
											
											/*obj.setShareId(shareId);
											obj.setTaskType(taskType);
											obj.setTaskName(taskName);
											obj.setTaskBrief(taskBrief);
											obj.setFrequency(frequency);
											obj.setDueDate(dueDate);
											obj.setDueTime(dueTime);
											obj.setMappedTeam(mappedTeam);
											obj.setActionStatus(status);
											obj.setComment(comments);
											obj.setActionType(actionType);
											obj.setEmpId(contactId[i]);*/
										}
										listForMail.add(obj);
									}
									String mailText = getConfigMultipleCompMail(listForMail, contactName, "Today Action Required");
									if (emailId != null && emailId.toString() != "")
										cmnHelper.addMail(contactName, "", emailId, "Today Action taken For KR", mailText, "", "Pending", "0", "", "KR", connection);
								}
						    }
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	   public String getConfigMultipleCompMail(List<KRPojo> mailDetail,String empName,String mailTitle) 
	    {
	    	  StringBuilder mailtext = new StringBuilder("");
	    	  if(mailDetail!=null && mailDetail.size()>0)
	    	  {
	    		  mailtext.append("<br><b>Dear "+empName+",</b><br>");
	    		  mailtext.append(mailTitle);
	    		  mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
	    		  mailtext.append("<tr  bgcolor='#e8e7e8'>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> KR&nbsp;ID</b>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> KR&nbsp;Name</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> KR&nbsp;Brief</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Share&nbsp;Date</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Kr Tags</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");
	    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;Type</b></td></tr>");
	    		  for (KRPojo object:mailDetail)
	    		  {
					
			    	  mailtext.append("<tr  bgcolor='#e8e7e8'>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrId()+"</b>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrName()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrBrief()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrFreq()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrDueDate()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrDueDateReq()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrTags()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrShareTo()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getActionStatus()+"</b></td>");
		    		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+object.getKrAccessType()+"</b></td></tr>");
	    		  }
	    		  mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	    		  mailtext.append("<BR>");
	    		  mailtext.append("<BR>");
	    		  mailtext.append("<br>");
	    		  mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    	  }
	    	  return mailtext.toString();
	    }
	public String getFrequencyName(String freqAbrivation)
	{
		String frequency = null;

		if (freqAbrivation.equalsIgnoreCase("OT"))
			frequency = "One Time";
		else if (freqAbrivation.equalsIgnoreCase("D"))
			frequency = "Daily";
		else if (freqAbrivation.equalsIgnoreCase("W"))
			frequency = "Weekly";
		else if (freqAbrivation.equalsIgnoreCase("M"))
			frequency = "Monthly";
		else if (freqAbrivation.equalsIgnoreCase("BM"))
			frequency = "Bi-Monthly";
		else if (freqAbrivation.equalsIgnoreCase("Q"))
			frequency = "Quaterly";
		else if (freqAbrivation.equalsIgnoreCase("HY"))
			frequency = "Half Yearly";
		else if (freqAbrivation.equalsIgnoreCase("Y"))
			frequency = "Yearly";

		return frequency;
	}
}
