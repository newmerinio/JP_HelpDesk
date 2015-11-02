package com.Over2Cloud.ctrl.asset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.compliance.complTaskMaster.TaskTypePojoBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetTakeAction extends ActionSupport implements ServletRequestAware
{
	@SuppressWarnings("rawtypes")
    Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private HttpServletRequest request;
	private Map<String, String> dataMap;
	private Map<String, String> takeActionStatus;
	private String assetReminderID;
	AssetDashboardBean assetDetails;
	private String nextDueDate;
	private String actionTaken;
	public File takeActionDoc;
	private String takeActionDocContentType;
	private String takeActionDocFileName;
	private String dbName;
	private String empId;
	private String assetName;
	private String assetCode;
	private String assetMfgSl;
	private String doc1;
	private String doc2;
	private String moduleName;
	
	
	
	@SuppressWarnings(
	{ "rawtypes", "unused" })
	public String beforeTakeActionOnSupport()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		
		if (sessionFlag)
		{
			if (getAssetReminderID() != null)
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				AssetUniversalAction AUA=new AssetUniversalAction();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List complDetailList = AUA.getExistAssetDetails(getAssetReminderID(), connectionSpace);
				String frequency = null, currentStatus = null, dueDate = null, dueTime = null;
				if (complDetailList != null && complDetailList.size() > 0)
				{
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					Object[] objectCol=null;
					dataMap = new LinkedHashMap<String, String>();
					takeActionStatus = new LinkedHashMap<String, String>();
					for (Iterator iterator = complDetailList.iterator(); iterator.hasNext();)
					{
						assetDetails = new AssetDashboardBean();
						objectCol = (Object[]) iterator.next();
						
						assetName=CUA.getValueWithNullCheck(objectCol[0]);
						assetCode=CUA.getValueWithNullCheck(objectCol[1]);
						assetMfgSl=CUA.getValueWithNullCheck(objectCol[12]);

						dataMap.put("Brand", CUA.getValueWithNullCheck(objectCol[2]));
						dataMap.put("Model", CUA.getValueWithNullCheck(objectCol[2]));
						
						if(objectCol[16].equals("Support"))
						{
							moduleName="Support";
						}
						else
						{
							moduleName="Preventive";
						}
						
						
						if (objectCol[4] != null && objectCol[4].toString() != "")
						{
							dataMap.put("Due Date", DateUtil.convertDateToIndianFormat(objectCol[4].toString()));
							dueDate = DateUtil.convertDateToIndianFormat(objectCol[4].toString());
						}
						else
							dataMap.put("Due Date", "Not Available");

						if (objectCol[5] != null && objectCol[5].toString() != "")
						{
							StringBuilder empName = new StringBuilder();
							String contactId = objectCol[5].toString().replace("#", ",").substring(0, (objectCol[5].toString().length() - 1));
							String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
							List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
							Object object=null;
							for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
							{
								 object = (Object) iterator1.next();
								empName.append(object.toString() + ", ");
							}
							dataMap.put("Remind To", empName.toString().substring(0, empName.toString().length() - 2));
						}
						else
						{
							dataMap.put("Remind To", "Not Available");
						}

						dataMap.put("Due Time", CUA.getValueWithNullCheck(objectCol[6]));
						
						if (objectCol[7] != null && objectCol[7].toString() != "")
							frequency = objectCol[7].toString();

						if (objectCol[8] != null && objectCol[8].toString() != "")
							currentStatus = objectCol[8].toString();

					}
					if (frequency != null && currentStatus != null && frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
					{
						takeActionStatus.put("Done", "Done");
						takeActionStatus.put("Snooze", "Snooze");
						takeActionStatus.put("Reschedule", "Reschedule");
					}
					else if (frequency != null && currentStatus != null && !frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
					{
						takeActionStatus.put("Done", "Done");
						takeActionStatus.put("Snooze", "Snooze");
						takeActionStatus.put("Recurring", "Recurring & Done");
						takeActionStatus.put("Reschedule", "Reschedule");
					}
					if (frequency.equalsIgnoreCase("OT"))
					{
						dataMap.put("Frequency", "One-Time");
					}
					else if (frequency.equalsIgnoreCase("D"))
					{
						dataMap.put("Frequency", "Daily");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("W"))
					{
						dataMap.put("Frequency", "Weekly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("M"))
					{
						dataMap.put("Frequency", "Monthly");
						dataMap.put("Next Due Date",DateUtil.convertDateToIndianFormat( DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("BM"))
					{
						dataMap.put("Frequency", "Bi-Monthly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("Q"))
					{
						dataMap.put("Frequency", "Quaterly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("HY"))
					{
						dataMap.put("Frequency", "Half Yearly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("Y"))
					{
						dataMap.put("Frequency", "Yearly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate)));
					}
					
					dataMap.put("Action Detail",CUA.getValueWithNullCheck(objectCol[13]));
					
					if(objectCol[14]!=null)
					{
						String str=objectCol[14].toString().substring(objectCol[14].toString().lastIndexOf("//")+2, objectCol[14].toString().length());
						String documentName=str.substring(14,str.length());
						dataMap.put("Document 1", documentName);
						doc1=objectCol[14].toString();
					}
					else
					{
						dataMap.put("Document 1", "Not Available");
					}
					
					if(objectCol[15]!=null)
					{
						String str=objectCol[15].toString().substring(objectCol[15].toString().lastIndexOf("//")+2, objectCol[15].toString().length());
						String documentName=str.substring(14,str.length());
						dataMap.put("Document 2", documentName);
						doc2=objectCol[15].toString();
					}
					else
					{
						dataMap.put("Document 2", "Not Available");
					}
				}
				returnResult = SUCCESS;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings(
			{ "static-access", "unchecked", "rawtypes", "unused" })
   public String takeActionOnAssetData()
   {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				boolean doneRemindFlag = false;
				String renameFilePath = null;
				List remindDateList = null;
				//String dueDate = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_report_config", accountID, connectionSpace, "", 0, "table_name",
				        "asset_report_config");
				if (statusColName != null)
				{
					TableColumes ob1;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						if (gdp.getColomnName().equalsIgnoreCase("costForCompMiss"))
						{
							ob1.setConstraint("default '0'");
						}
						else
						{
							ob1.setConstraint("default NULL");
						}
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("asset_report", Tablecolumesaaa, connectionSpace);
				}
				if (actionTaken != null && !actionTaken.equals("-1"))
				{
					/*String compSeries = null;
					List prefix_suffixData = cbt.executeAllSelectQuery("SELECT compid_prefix,compid_suffix,dueDate FROM compliance_details WHERE id=" + complID, connectionSpace);

					if (prefix_suffixData != null && prefix_suffixData.size() > 0)
					{
						for (Iterator iterator = prefix_suffixData.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							compSeries = object[0].toString() + object[1].toString();
							dueDate = object[2].toString();
						}

					}*/
					List dateList = cbt.executeAllSelectQuery("SELECT id,reminder_code,remind_date FROM asset_reminder_data WHERE reminder_code='D' OR reminder_code='R' AND asseReminder_id=" + assetReminderID,
					        connectionSpace);
					if (dateList != null && dateList.size() > 0)
					{
						remindDateList = new ArrayList();
						for (Iterator iterator2 = dateList.iterator(); iterator2.hasNext();)
						{
							Object object2[] = (Object[]) iterator2.next();
							if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R") && object2[2] != null)
							{
								remindDateList.add(object2[2].toString());

							}
						}
					}
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					String comments = request.getParameter("comments");
					HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
					Map<String, Object> setVariables;
					Map<String, Object> whereCondition;
					if (actionTaken.equalsIgnoreCase("Done"))
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", assetReminderID);
						setVariables.put("actionTaken", actionTaken);
						setVariables.put("comments", comments);
						setVariables.put("actionStatus", "Done");
						boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connectionSpace);
						if (doneFlag)
						{
							setVariables = new HashMap<String, Object>();
							whereCondition = new HashMap<String, Object>();
							whereCondition.put("asseReminder_id", assetReminderID);
							setVariables.put("reminder_status", "311");
							setVariables.put("status", "Done");
							doneRemindFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connectionSpace);
						}
					}
					else if (actionTaken.equalsIgnoreCase("Recurring"))
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", assetReminderID);
						setVariables.put("actionTaken", actionTaken);
						setVariables.put("comments", comments);
						setVariables.put("actionStatus", "Done");
						boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connectionSpace);
						if (doneFlag)
						{
							setVariables = new HashMap<String, Object>();
							whereCondition = new HashMap<String, Object>();
							whereCondition.put("asseReminder_id", assetReminderID);
							setVariables.put("reminder_status", "311");
							setVariables.put("status", "Done");
							doneRemindFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connectionSpace);
						}
					}
					else if (actionTaken.equalsIgnoreCase("Snooze"))
					{
						List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", assetReminderID);
						setVariables.put("actionTaken", actionTaken);
						setVariables.put("comments", comments);
						setVariables.put("actionStatus", "Snooze");
						setVariables.put("snooze_date", DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
						setVariables.put("snooze_time", request.getParameter("snoozeTime"));
						boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connectionSpace);

						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", assetReminderID);
						whereCondition.put("escalation", "Y");
						setVariables.put("escalate_date", DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
						setVariables.put("escalate_time", request.getParameter("snoozeTime"));
						helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connectionSpace);

						if (doneFlag)
						{
							setVariables = new HashMap<String, Object>();
							whereCondition = new HashMap<String, Object>();
							whereCondition.put("asseReminder_id", assetReminderID);
							setVariables.put("reminder_status", "311");
							setVariables.put("status", "Snooze");
							doneRemindFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connectionSpace);
						}
						ob = new InsertDataTable();
						ob.setColName("reminder_name");
						ob.setDataName("Snooze");
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("reminder_code");
						ob.setDataName("S");
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("asseReminder_id");
						ob.setDataName(assetReminderID);
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("reminder_status");
						ob.setDataName("0");
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("due_date");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("due_time");
						ob.setDataName(request.getParameter("dueTime"));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("remind_date");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("remind_time");
						ob.setDataName(request.getParameter("snoozeTime"));
						insertData1.add(ob);
						cbt.insertIntoTable("asset_reminder_data", insertData1, connectionSpace);
					}
					else if (actionTaken.equalsIgnoreCase("Reschedule"))
					{
						remindDateList = new ArrayList();
						List data = cbt.executeAllSelectQuery("SELECT escalation,l1EscDuration,l2EscDuration,l3EscDuration,l4EscDuration FROM asset_reminder_details WHERE id=" + assetReminderID, connectionSpace);
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", assetReminderID);
						setVariables.put("dueDate", DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
						setVariables.put("dueTime", request.getParameter("rescheduleTime"));
						if (data != null && data.size() > 0)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object object[] = (Object[]) iterator.next();
								if (object[0] != null && !object[0].toString().equalsIgnoreCase("N"))
								{
									if (object[1] != null && !object[1].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")),
										        request.getParameter("rescheduleTime"), object[1].toString(), "Y").split("#");
										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "1");
									}
									else if (object[2] != null && !object[2].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")),
										        request.getParameter("rescheduleTime"), object[2].toString(), "Y").split("#");
										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "2");
									}
									else if (object[3] != null && !object[3].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")),
										        request.getParameter("rescheduleTime"), object[3].toString(), "Y").split("#");
										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "3");
									}
									else if (object[4] != null && !object[4].toString().equalsIgnoreCase("00:00"))
									{
										String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")),
										        request.getParameter("rescheduleTime"), object[4].toString(), "Y").split("#");
										setVariables.put("escalate_date", escDateTime[0]);
										setVariables.put("escalate_time", escDateTime[1]);
										setVariables.put("current_esc_level", "4");
									}
									setVariables.put("actionStatus", "Pending");
									setVariables.put("actionTaken", "Pending");
									setVariables.put("comments", " ");
								}
								boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connectionSpace);
								if (doneFlag)
								{
									List data1 = cbt.executeAllSelectQuery(
									        "SELECT id,reminder_code,remind_interval FROM asset_reminder_data WHERE reminder_code='D' OR reminder_code='R' AND asseReminder_id="+assetReminderID,
									        connectionSpace);
									if (data1 != null && data1.size() > 0)
									{
										for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
										{
											Object object2[] = (Object[]) iterator2.next();
											setVariables = new HashMap<String, Object>();
											whereCondition = new HashMap<String, Object>();
											whereCondition.put("id", object2[0].toString());
											setVariables.put("due_date", DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
											setVariables.put("due_time", request.getParameter("rescheduleTime"));
											setVariables.put("remind_time", request.getParameter("rescheduleTime"));
											if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R"))
											{
												String newRemindDate = DateUtil.getNewDate("day", -Integer.valueOf(object2[2].toString()),
												        DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
												setVariables.put("remind_date", newRemindDate);
												remindDateList.add(newRemindDate);

											}
											else
											{
												setVariables.put("remind_date", DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
											}
											setVariables.put("reminder_status", "0");
											setVariables.put("status", "Pending");
											doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connectionSpace);
										}
									}
								}
							}
						}
					}
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equals("dueDate") && !parmName.equals("snoozeDate")
						        && !parmName.equals("reschuedule_date"))
						{
							ob = new InsertDataTable();
							ob.setColName(parmName);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
					if (takeActionDocFileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + DateUtil.mergeDateTime() + takeActionDocFileName;
						String storeFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + takeActionDocFileName;
						String str = renameFilePath.replace("//", "/");
						// renameFilePath=DateUtil.removeSpace(renameFilePath);
						// storeFilePath=DateUtil.removeSpace(storeFilePath);
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(takeActionDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									ob = new InsertDataTable();
									ob.setColName("document_takeaction");
									ob.setDataName(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					if (remindDateList != null && remindDateList.size() > 0)
					{
						for (int i = 0; i < remindDateList.size(); i++)
						{
							ob = new InsertDataTable();
							ob.setColName("reminder" + (i + 1));
							ob.setDataName(remindDateList.get(i));
							insertData.add(ob);
						}
					}
					ob = new InsertDataTable();
					ob.setColName("dueDate");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
					insertData.add(ob);
					
					/*ob = new InsertDataTable();
					ob.setColName("dueTime");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueTime")));
					insertData.add(ob);
*/
					if (actionTaken != null && actionTaken.equalsIgnoreCase("snooze"))
					{
						ob = new InsertDataTable();
						ob.setColName("snoozeDate");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
						insertData.add(ob);
					}
					if (actionTaken != null && actionTaken.equalsIgnoreCase("Reschedule"))
					{
						ob = new InsertDataTable();
						ob.setColName("rescheduleDate");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("reschuedule_date")));
						insertData.add(ob);
					}
					ob = new InsertDataTable();
					ob.setColName("actionTakenDate");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("actionTakenTime");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					doneRemindFlag = cbt.insertIntoTable("asset_report", insertData, connectionSpace);
					boolean status = beforeMailConfiguration(getAssetReminderID(), renameFilePath, actionTaken, connectionSpace);
					if (doneRemindFlag)
					{
						addActionMessage("Successfully done !!!!");
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
}
	@SuppressWarnings("rawtypes")
	public boolean beforeMailConfiguration(String assetReminderId, String docPath, String actionStatus, SessionFactory connection)
	{
		boolean status = false;
		try
		{
			AssetUniversalAction AUA = new AssetUniversalAction();
			CommunicationHelper cmnHelper = new CommunicationHelper();
			List data1 =AUA.getExistAssetDetails(assetReminderId, connection);
			if (data1 != null && data1.size() > 0)
			{
				Object[] object=null;
				String assetName = null, serial = null, dueDate = null, dueTime = null, frequency = null, reminderFor = null, comments = null, snoozeDate = null, snoozeTime = null;
				for (Iterator iterator = data1.iterator(); iterator.hasNext();)
				{
					 object = (Object[]) iterator.next();
					if (object[0] != null)
						assetName = object[0].toString();

					if (object[1] != null)
						serial = object[1].toString();

					if (object[4] != null)
						dueDate = object[4].toString();

					if (object[6] != null)
						dueTime = object[6].toString();

					if (object[7] != null)
						frequency = new AssetUniversalHelper().getFrequencyName(object[7].toString());

					if (object[5] != null)
						reminderFor = object[5].toString();

					if (object[9] != null)
						comments = object[9].toString();
					else
						comments = "NA";

					if (object[10] != null)
						snoozeDate = object[10].toString();

					if (object[11] != null)
						snoozeTime = object[11].toString();

					if (reminderFor != null)
					{
						if (actionStatus != null && actionStatus.equalsIgnoreCase("Snooze"))
						{
							actionStatus = actionStatus + " (Till " + DateUtil.convertDateToIndianFormat(snoozeDate) + " " + snoozeTime + ")";
						}

						String mailSubject = null, mailTitle = null;
						mailSubject = "Asset Action Alert:";
						mailTitle = mailSubject;
						String msgText = "Asset Action Alert: For Asset Name: " + assetName + ", Action Status " + actionStatus + ", Comments: " + comments + ".";
						dueDate = DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;

						String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));
						String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName,cc.id " + "AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON "
						        + "emp.id=cc.emp_id INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.work_status=0 "
						        + "AND cc.id IN(" + contactId + ")";

						List data2 = new createTable().executeAllSelectQuery(query2, connection);
						if (data2 != null && data2.size() > 0)
						{
							String mobileNo = null, emailId = null, empName = null;
							for (Iterator iterator2 = data2.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								StringBuilder str = new StringBuilder();
								String mappedTeam = null;

								if (object2[0] != null)
									mobileNo = object2[0].toString();

								if (object2[1] != null)
									emailId = object2[1].toString();

								if (object2[2] != null)
									empName = object2[2].toString();

								if (object2[3] != null)
									contactId = object2[3].toString();

								for (Iterator iterator3 = data2.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[5].toString().equalsIgnoreCase(object3[5].toString()))
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

								if (mobileNo != null && mobileNo.toString() != "")
									cmnHelper.addMessage(empName, object2[4].toString(), mobileNo, msgText, "", "Pending", "0", "Compliance", connection);

								String mailText = getConfigMail(assetName, serial, dueDate, frequency, mappedTeam, actionStatus, comments, empName, contactId, mailTitle);
								if (emailId != null && emailId.toString() != "")
									cmnHelper.addMail(empName, object2[4].toString(), emailId, mailSubject, mailText, "", "Pending", "0", "", "Asset", connection);
							}
						}
					}
				}
			}
			status = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	public String getConfigMail(String assetName, String serial, String dueDate, String frequency, String mappedTeam, String actionStatus, String comments, String empName, String empId,
	        String mailTitle)
	{
		StringBuilder mailtext = new StringBuilder("");

		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		mailtext.append(mailTitle);
		mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Asset&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + assetName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Serial&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + serial + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + dueDate + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + frequency + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + mappedTeam + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + actionStatus + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + comments + "</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}
	@SuppressWarnings(
	{ "static-access", "unused", "rawtypes" })
	public String beforeTakeActionByMail()
	{
		try
		{
			String dbId = new Cryptography().decrypt(dbName, DES_ENCRYPTION_KEY);
			SessionFactory connection = new ConnectionHelper().getSessionFactory(dbId);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List assetDetailList = new AssetUniversalAction().getExistAssetDetails(assetReminderID, connection);
			String frequency = null, currentStatus = null, dueDate = null, dueTime = null;
			takeActionStatus = new LinkedHashMap<String, String>();
			if (assetDetailList != null && assetDetailList.size() > 0)
			{
				dataMap = new LinkedHashMap<String, String>();
				Object[] objectCol=null;
				for (Iterator iterator = assetDetailList.iterator(); iterator.hasNext();)
				{
					assetDetails = new AssetDashboardBean();
					 objectCol = (Object[]) iterator.next();
					 if (objectCol[0] != null && objectCol[0].toString() != "")
						dataMap.put("Asset Name", objectCol[0].toString());
					else
						dataMap.put("Asset Name", "NA");

					if (objectCol[1] != null && objectCol[1].toString() != "")
						dataMap.put("Serial No", objectCol[1].toString());
					else
						dataMap.put("Serial No", "NA");

					if (objectCol[2] != null && objectCol[2].toString() != "")
						dataMap.put("Brand", objectCol[2].toString());
					else
						dataMap.put("Brand", "NA");
					
					if (objectCol[3] != null && objectCol[3].toString() != "")
						dataMap.put("Model", objectCol[3].toString());
					else
						dataMap.put("Model", "NA");

					if (objectCol[4] != null && objectCol[4].toString() != "")
					{
						dataMap.put("Due Date", DateUtil.convertDateToIndianFormat(objectCol[4].toString()));
						dueDate = DateUtil.convertDateToIndianFormat(objectCol[4].toString());
					}
					else
						dataMap.put("Due Date", "NA");

					if (objectCol[6] != null && objectCol[6].toString() != "")
					{
						dataMap.put("Due Time", objectCol[6].toString());
						dueTime = objectCol[6].toString();
					}
					else
						dataMap.put("Due Time", "NA");

					if (objectCol[7] != null && objectCol[7].toString() != "")
						frequency = objectCol[7].toString();

					if (objectCol[8] != null && objectCol[8].toString() != "")
						currentStatus = objectCol[8].toString();

					if (objectCol[5] != null && objectCol[5].toString() != "")
					{
						StringBuilder empName = new StringBuilder();
						String contactId = objectCol[5].toString().replace("#", ",").substring(0, (objectCol[5].toString().length() - 1));
						String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + contactId + ")";
						List data1 = cbt.executeAllSelectQuery(query2, connection);
						Object object=null;
						for (Iterator iterator1 = data1.iterator(); iterator1.hasNext();)
						{
							object = (Object) iterator1.next();
							empName.append(object.toString() + " ,");
						}
						dataMap.put("Remind To", empName.toString().substring(0, empName.toString().length() - 1));
					}
					else
					{
						dataMap.put("Remind To", "NA");
					}

					if (frequency != null && currentStatus != null && frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
					{
						takeActionStatus.put("Done", "Done");
						takeActionStatus.put("Snooze", "Snooze");
						takeActionStatus.put("Reschedule", "Reschedule");
					}
					else if (frequency != null && currentStatus != null && !frequency.equalsIgnoreCase("OT") && (currentStatus.equalsIgnoreCase("Pending") || currentStatus.equalsIgnoreCase("Snooze")))
					{
						takeActionStatus.put("Done", "Done");
						takeActionStatus.put("Snooze", "Snooze");
						takeActionStatus.put("Recurring", "Recurring");
						takeActionStatus.put("Reschedule", "Reschedule");
					}
					if (frequency.equalsIgnoreCase("OT"))
					{
						dataMap.put("Frequency", "One-Time");
					}
					else if (frequency.equalsIgnoreCase("D"))
					{
						dataMap.put("Frequency", "Daily");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("W"))
					{
						dataMap.put("Frequency", "Weekly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 7, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("M"))
					{
						dataMap.put("Frequency", "Monthly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 1, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("BM"))
					{
						dataMap.put("Frequency", "Bi-Monthly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", 15, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("Q"))
					{
						dataMap.put("Frequency", "Quaterly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 3, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("HY"))
					{
						dataMap.put("Frequency", "Half Yearly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("month", 6, DateUtil.convertDateToUSFormat(dueDate)));
					}
					else if (frequency.equalsIgnoreCase("Y"))
					{
						dataMap.put("Frequency", "Yearly");
						dataMap.put("Next Due Date", DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate))));
						nextDueDate = DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("year", 1, DateUtil.convertDateToUSFormat(dueDate)));
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@SuppressWarnings(
	{ "static-access", "unchecked", "rawtypes", "unused" })
	public String takeActionOnAssetByMail()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String dbId = new Cryptography().decrypt(dbName, DES_ENCRYPTION_KEY);
			String emp_id = new Cryptography().decrypt(empId, DES_ENCRYPTION_KEY);
			SessionFactory connection = new ConnectionHelper().getSessionFactory(dbId);
			String actionTakenBy = null;
			String renameFilePath = null;
			List uidList = cbt.executeAllSelectQuery("SELECT userAC.userID FROM employee_basic AS emp INNER JOIN useraccount AS userAC ON userAC.id=emp.useraccountid WHERE emp.id=" + emp_id,
			        connection);
			if (uidList != null && uidList.size() > 0)
			{
				actionTakenBy = uidList.get(0).toString();
			}
			boolean doneRemindFlag = false;

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_report_config", "", connection, "", 0, "table_name", "asset_report_config");
			if (statusColName != null)
			{
				TableColumes ob1;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : statusColName)
				{
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					if (gdp.getColomnName().equalsIgnoreCase("costForCompMiss"))
					{
						ob1.setConstraint("default '0'");
					}
					else
					{
						ob1.setConstraint("default NULL");
					}
					Tablecolumesaaa.add(ob1);
				}
				cbt.createTable22("asset_report", Tablecolumesaaa, connection);
			}
			if (actionTaken != null && !actionTaken.equals("-1"))
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				String comments = request.getParameter("comments");
				HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
				Map<String, Object> setVariables;
				Map<String, Object> whereCondition;
				if (actionTaken.equalsIgnoreCase("Done"))
				{
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", assetReminderID);
					setVariables.put("actionTaken", actionTaken);
					setVariables.put("comments", comments);
					setVariables.put("actionStatus", "Done");
					boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);
					if (doneFlag)
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("asseReminder_id", assetReminderID);
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Done");
						doneRemindFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connection);
					}
				}
				else if (actionTaken.equalsIgnoreCase("Recurring"))
				{
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", assetReminderID);
					setVariables.put("actionTaken", actionTaken);
					setVariables.put("comments", comments);
					setVariables.put("actionStatus", "Done");
					boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);
					if (doneFlag)
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("asseReminder_id", assetReminderID);
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Done");
						doneRemindFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connection);
					}
				}
				else if (actionTaken.equalsIgnoreCase("Snooze"))
				{
					List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", assetReminderID);
					setVariables.put("actionTaken", actionTaken);
					setVariables.put("comments", comments);
					setVariables.put("actionStatus", "Snooze");
					setVariables.put("snooze_date", DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
					setVariables.put("snooze_time", request.getParameter("snoozeTime"));
					boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);

					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", assetReminderID);
					whereCondition.put("escalation", "Y");
					setVariables.put("escalate_date", DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
					setVariables.put("escalate_time", request.getParameter("snoozeTime"));
					helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);

					if (doneFlag)
					{
						setVariables = new HashMap<String, Object>();
						whereCondition = new HashMap<String, Object>();
						whereCondition.put("asseReminder_id", assetReminderID);
						setVariables.put("reminder_status", "311");
						setVariables.put("status", "Snooze");
						doneRemindFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connection);
					}
					ob = new InsertDataTable();
					ob.setColName("reminder_name");
					ob.setDataName("Snooze");
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("reminder_code");
					ob.setDataName("S");
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("asseReminder_id");
					ob.setDataName(assetReminderID);
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("reminder_status");
					ob.setDataName("0");
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("due_date");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("due_time");
					ob.setDataName(request.getParameter("dueTime"));
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("remind_date");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("remind_time");
					ob.setDataName(request.getParameter("snoozeTime"));
					insertData1.add(ob);
					cbt.insertIntoTable("asset_reminder_data", insertData1, connection);
				}
				else if (actionTaken.equalsIgnoreCase("Reschedule"))
				{
					List data = cbt.executeAllSelectQuery("SELECT escalation,l1EscDuration,l2EscDuration,l3EscDuration,l4EscDuration FROM asset_reminder_details WHERE id=" + assetReminderID, connection);
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", assetReminderID);
					setVariables.put("dueDate", DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
					setVariables.put("dueTime", request.getParameter("rescheduleTime"));
					if (data != null && data.size() > 0)
					{
						Object object[]=null;
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object[0] != null && !object[0].toString().equalsIgnoreCase("N"))
							{
								if (object[1] != null && !object[1].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")),
									        request.getParameter("rescheduleTime"), object[1].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "1");
								}
								else if (object[2] != null && !object[2].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")),
									        request.getParameter("rescheduleTime"), object[2].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "2");
								}
								else if (object[3] != null && !object[3].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")),
									        request.getParameter("rescheduleTime"), object[3].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "3");
								}
								else if (object[4] != null && !object[4].toString().equalsIgnoreCase("00:00"))
								{
									String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")),
									        request.getParameter("rescheduleTime"), object[4].toString(), "Y").split("#");
									setVariables.put("escalate_date", escDateTime[0]);
									setVariables.put("escalate_time", escDateTime[1]);
									setVariables.put("current_esc_level", "4");
								}
								setVariables.put("actionStatus", "Pending");
								setVariables.put("actionTaken", "Pending");
								setVariables.put("comments", " ");
							}
							boolean doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);
							if (doneFlag)
							{
								List data1 = cbt.executeAllSelectQuery(
								        "SELECT id,reminder_code,remind_interval FROM asset_reminder_data WHERE reminder_code='D' OR reminder_code='R' AND asseReminder_id=" + assetReminderID, connection);
								if (data1 != null && data1.size() > 0)
								{
									Object object2[] =null;
									for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
									{
										 object2 = (Object[]) iterator2.next();
										setVariables = new HashMap<String, Object>();
										whereCondition = new HashMap<String, Object>();
										whereCondition.put("id", object2[0].toString());
										setVariables.put("due_date", DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
										setVariables.put("due_time", request.getParameter("rescheduleTime"));
										setVariables.put("remind_time", request.getParameter("rescheduleTime"));
										if (object2[1] != null && object2[1].toString().equalsIgnoreCase("R"))
										{
											setVariables.put("remind_date",
											        DateUtil.getNewDate("day", -Integer.valueOf(object2[2].toString()), DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate"))));
										}
										else
										{
											setVariables.put("remind_date", DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
										}
										setVariables.put("reminder_status", "0");
										setVariables.put("status", "Pending");
										doneFlag = helpdeskHelper.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connection);
									}
								}
							}
						}
					}
				}
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue = request.getParameter(parmName);
					if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equals("dueDate") && !parmName.equals("snoozeDate")
					        && !parmName.equals("rescheduleDate") )
					{
						if (!parmName.equalsIgnoreCase("dbName") && !parmName.equalsIgnoreCase("empId"))
						{
							ob = new InsertDataTable();
							ob.setColName(parmName);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
				}

				if (takeActionDocFileName != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + DateUtil.mergeDateTime() + takeActionDocFileName;
					String storeFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + takeActionDocFileName;
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath != null)
					{
						File theFile = new File(storeFilePath);
						File newFileName = new File(str);
						if (theFile != null)
						{
							try
							{
								FileUtils.copyFile(takeActionDoc, theFile);
								if (theFile.exists())
									theFile.renameTo(newFileName);
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							if (theFile != null)
							{
								ob = new InsertDataTable();
								ob.setColName("document_takeaction");
								ob.setDataName(renameFilePath);
								insertData.add(ob);
							}
						}
					}
				}

				ob = new InsertDataTable();
				ob.setColName("dueDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("snoozeDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("snoozeDate")));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("rescheduleDate");
				ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("rescheduleDate")));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("actionTakenDate");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(actionTakenBy);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("actionTakenTime");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);

				doneRemindFlag = cbt.insertIntoTable("asset_report", insertData, connection);
				boolean status = beforeMailConfiguration(assetReminderID, renameFilePath, actionTaken, connection);
				if (doneRemindFlag)
				{
					addActionMessage("Successfully done !!!!");
					return SUCCESS;
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return ERROR;
	}
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	public Map<String, String> getDataMap()
	{
		return dataMap;
	}
	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}
	public Map<String, String> getTakeActionStatus()
	{
		return takeActionStatus;
	}
	public void setTakeActionStatus(Map<String, String> takeActionStatus)
	{
		this.takeActionStatus = takeActionStatus;
	}
	public AssetDashboardBean getAssetDetails()
	{
		return assetDetails;
	}
	public void setAssetDetails(AssetDashboardBean assetDetails)
	{
		this.assetDetails = assetDetails;
	}
	public String getNextDueDate()
	{
		return nextDueDate;
	}
	public void setNextDueDate(String nextDueDate)
	{
		this.nextDueDate = nextDueDate;
	}

	public String getActionTaken()
	{
		return actionTaken;
	}

	public void setActionTaken(String actionTaken)
	{
		this.actionTaken = actionTaken;
	}

	public File getTakeActionDoc()
	{
		return takeActionDoc;
	}

	public void setTakeActionDoc(File takeActionDoc)
	{
		this.takeActionDoc = takeActionDoc;
	}

	public String getTakeActionDocContentType()
	{
		return takeActionDocContentType;
	}

	public void setTakeActionDocContentType(String takeActionDocContentType)
	{
		this.takeActionDocContentType = takeActionDocContentType;
	}

	public String getTakeActionDocFileName()
	{
		return takeActionDocFileName;
	}

	public void setTakeActionDocFileName(String takeActionDocFileName)
	{
		this.takeActionDocFileName = takeActionDocFileName;
	}

	public String getAssetReminderID()
	{
		return assetReminderID;
	}

	public void setAssetReminderID(String assetReminderID)
	{
		this.assetReminderID = assetReminderID;
	}

	public String getDbName()
	{
		return dbName;
	}

	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetMfgSl() {
		return assetMfgSl;
	}

	public void setAssetMfgSl(String assetMfgSl) {
		this.assetMfgSl = assetMfgSl;
	}

	public String getDoc1() {
		return doc1;
	}

	public void setDoc1(String doc1) {
		this.doc1 = doc1;
	}

	public String getDoc2() {
		return doc2;
	}

	public void setDoc2(String doc2) {
		this.doc2 = doc2;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

}
