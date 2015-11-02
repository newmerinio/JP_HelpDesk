package com.Over2Cloud.assetReminder.serviceFiles;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.assetReminder.serviceFiles.AssetServiceHelper;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;

public class AssetReminderHelper
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	@SuppressWarnings("rawtypes")
    public void checkReminder(SessionFactory connection)
	{
		try
		{
			AssetServiceHelper srvcHelper = new AssetServiceHelper();

			List validData = srvcHelper.getReminderData(connection);
			if (validData != null && validData.size() > 0)
			{
				String assetName = null, serialNo = null,msgText=null, escalation = null, frequency = null, brand = null, remidMode = null, reminderName = null, remindFor = null, assetReminderId = null, reminderId = null, dueDate = null, dueTime = null, attachmentPath = null, remindDate = null;
				CommunicationHelper cmnHelper = new CommunicationHelper();
				Object[] object=null;
				for (Iterator iterator = validData.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
						assetName = object[0].toString();
					else
						assetName = "NA";

					if (object[1] != null && !object[1].toString().equals(""))
						frequency = object[1].toString();
					else
						frequency = "NA";

					if (object[2] != null && !object[2].toString().equals(""))
						serialNo = object[2].toString();
					else
						serialNo = "NA";

					if (object[3] != null && !object[3].toString().equals(""))
						brand = object[3].toString();
					else
						brand = "NA";

					if (object[4] != null && !object[4].toString().equals(""))
						remidMode = object[4].toString();
					else
						remidMode = "NA";

					if (object[5] != null && !object[5].toString().equals(""))
						remindFor = object[5].toString();
					else
						remindFor = "NA";

					if (object[6] != null && !object[6].toString().equals(""))
						assetReminderId = object[6].toString();

					if (object[7] != null && !object[7].toString().equals(""))
						reminderId = object[7].toString();

					if (object[8] != null && !object[8].toString().equals(""))
						dueDate = object[8].toString();

					if (object[9] != null && !object[9].toString().equals(""))
						dueTime = object[9].toString();

					if (object[10] != null && !object[10].toString().equals(""))
						attachmentPath = object[10].toString();

					if (object[12] != null && !object[12].toString().equals(""))
						remindDate = object[12].toString();
					else
						remindDate = "NA";

					if (object[13] != null && !object[13].toString().equals(""))
						reminderName = object[13].toString();

					if (object[14] != null && !object[14].toString().equals(""))
						escalation = object[14].toString();

					boolean status = srvcHelper.updateAssetAccordingToFrequency(assetReminderId, frequency, remindDate, connection);
					if (status)
					{
						if (remindFor != null && !remindFor.equals(""))
						{
							String mailSubject = null, mailTitle = null;
							if (reminderName != null && reminderName.equalsIgnoreCase("Due Reminder"))
							{
								mailSubject = "Urgent Attention for Asset Due Today!";
								mailTitle = mailSubject;
								dueDate = "Today i.e " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
								msgText = "Asset Alert: Task Name: " + assetName + ", is having Due Date Today.";

							}
							else if (reminderName != null && !reminderName.equalsIgnoreCase("Due Reminder"))
							{
								mailSubject = "Asset Reminder Alert-" + reminderName.charAt(reminderName.length() - 1);
								mailTitle = mailSubject + " requires your kind attention!";
								dueDate = DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
								msgText = "Asset Alert: Reminder-" + reminderName.charAt(reminderName.length() - 1) + ", Asset Name: " + assetName + ", Due Date: " + dueDate + ".";
							}

							if (escalation != null && escalation.equalsIgnoreCase("N"))
								escalation = "No";
							else if (escalation != null && escalation.equalsIgnoreCase("Y"))
								escalation = "Yes";

							String contactId = remindFor.replace("#", ",").substring(0, (remindFor.toString().length() - 1));
							String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
							List data1 = new createTable().executeAllSelectQuery(query2, connection);
							
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								if (frequency.equalsIgnoreCase("OT"))
									frequency = "One Time";
								else if (frequency.equalsIgnoreCase("D"))
									frequency = "Daily";
								else if (frequency.equalsIgnoreCase("W"))
									frequency = "Weekly";
								else if (frequency.equalsIgnoreCase("M"))
									frequency = "Monthly";
								else if (frequency.equalsIgnoreCase("BM"))
									frequency = "Bi-Monthly";
								else if (frequency.equalsIgnoreCase("Q"))
									frequency = "Quaterly";
								else if (frequency.equalsIgnoreCase("HY"))
									frequency = "Half Yearly";
								else if (frequency.equalsIgnoreCase("Y"))
									frequency = "Yearly";

								StringBuilder str = new StringBuilder();
								String mappedTeam = null;
								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[3].toString().equalsIgnoreCase(object3[3].toString()))
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

								if (remidMode.equalsIgnoreCase("SMS"))
								{
									if (object2[0] != null && object2[0].toString() != "")
										cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Asset", connection);

								}
								else if (remidMode.equalsIgnoreCase("Mail"))
								{
									String mailText = getConfigMail(assetName, serialNo, brand, frequency, escalation,reminderId , mappedTeam, dueDate, object2[2].toString(), object2[3].toString(),
									        mailTitle, "Pending");
									if (object2[1] != null && object2[1].toString() != "")
										cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", attachmentPath, "Asset", connection);

								}
								else if (remidMode.equalsIgnoreCase("Both"))
								{
									if (object2[0] != null && object2[0].toString() != "")
										cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Asset", connection);

									String mailText = getConfigMail(assetName, serialNo, brand, frequency, escalation,reminderId , mappedTeam, dueDate, object2[2].toString(), object2[3].toString(),
									        mailTitle, "Pending");
									if (object2[1] != null && object2[1].toString() != "")
										cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", attachmentPath, "Asset", connection);
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

	@SuppressWarnings(
	{ "static-access", "rawtypes" })
	public void checkEscalation(SessionFactory connection)
	{
		try
		{
			AssetServiceHelper srvcHelper = new AssetServiceHelper();
			List validData = srvcHelper.getEscalationData(connection);
			if (validData != null && validData.size() > 0)
			{
				String assetName = null, serialNo = null, frequency = null, brand = null, msgText = null, remidMode = null, remindFor = null, reminderId = null, dueDate = null, dueTime = null, attachmentPath = null;
				String escL1 = null, escL2 = null, escL3 = null, escL4 = null, escL1Duration = null, escL2Duration = null, escL3Duration = null, escL4Duration = null, currentEscLevel = null;
				CommunicationHelper cmnHelper = new CommunicationHelper();
				for (Iterator iterator = validData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
						assetName = object[0].toString();
					else
						assetName = "NA";

					if (object[1] != null && !object[1].toString().equals(""))
						frequency = object[1].toString();
					else
						frequency = "NA";

					if (object[2] != null && !object[2].toString().equals(""))
						serialNo = object[2].toString();
					else
						serialNo = "NA";

					if (object[3] != null && !object[3].toString().equals(""))
						brand = object[3].toString();
					else
						brand = "NA";

					if (object[4] != null && !object[4].toString().equals(""))
						remidMode = object[4].toString();
					else
						remidMode = "NA";

					if (object[5] != null && !object[5].toString().equals(""))
						remindFor = object[5].toString();
					else
						remindFor = "NA";

					if (object[6] != null && !object[6].toString().equals(""))
						dueDate = object[6].toString();

					if (object[7] != null && !object[7].toString().equals(""))
						dueTime = object[7].toString();

					if (object[8] != null && !object[8].toString().equals(""))
						escL1 = object[8].toString();

					if (object[9] != null && !object[9].toString().equals(""))
						escL1Duration = object[9].toString();

					if (object[10] != null && !object[10].toString().equals(""))
						escL2 = object[10].toString();

					if (object[11] != null && !object[11].toString().equals(""))
						escL2Duration = object[11].toString();

					if (object[12] != null && !object[12].toString().equals(""))
						escL3 = object[12].toString();

					if (object[13] != null && !object[13].toString().equals(""))
						escL3Duration = object[13].toString();

					if (object[14] != null && !object[14].toString().equals(""))
						escL4 = object[14].toString();

					if (object[15] != null && !object[15].toString().equals(""))
						escL4Duration = object[15].toString();

					if (object[16] != null && !object[16].toString().equals(""))
						currentEscLevel = object[16].toString();

					if (object[17] != null && !object[17].toString().equals(""))
						reminderId = object[17].toString();

					int nextEscLevel = Integer.valueOf(currentEscLevel);
					String nextEscDuration = "00:00";
					String nextEscDateTime = null;
					boolean isNextEsc = false;
					if (nextEscLevel == 1 && !escL1Duration.equalsIgnoreCase("00:00"))
					{
						nextEscDuration = escL2Duration;
						isNextEsc = true;
					}
					else if (nextEscLevel == 2 && !escL2Duration.equalsIgnoreCase("00:00"))
					{
						nextEscDuration = escL3Duration;
						isNextEsc = true;
					}
					else if (nextEscLevel == 3 && !escL3Duration.equalsIgnoreCase("00:00"))
					{
						nextEscDuration = escL4Duration;
						isNextEsc = true;
					}
					else if (nextEscLevel == 4)
					{
						nextEscDuration = "00:00";
						isNextEsc = true;
					}
					nextEscLevel++;

					if (nextEscLevel < 6)
					{
						nextEscDateTime = new DateUtil().newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), nextEscDuration, "Y");
					}
					boolean status = false;
					if (isNextEsc)
					{
						status = srvcHelper.updateNextEscDate(reminderId, connection, nextEscLevel, nextEscDateTime);
					}
					String escL = null;
					if (nextEscLevel == 2 && isNextEsc)
						escL = escL1;
					else if (nextEscLevel == 3 && isNextEsc)
						escL = escL2;
					else if (nextEscLevel == 4 && isNextEsc)
						escL = escL3;
					else if (nextEscLevel == 5 && isNextEsc)
						escL = escL4;

					if (escL != null && !escL.equals(""))
					{
						if (frequency.equalsIgnoreCase("OT"))
							frequency = "One Time";
						else if (frequency.equalsIgnoreCase("D"))
							frequency = "Daily";
						else if (frequency.equalsIgnoreCase("W"))
							frequency = "Weekly";
						else if (frequency.equalsIgnoreCase("M"))
							frequency = "Monthly";
						else if (frequency.equalsIgnoreCase("BM"))
							frequency = "Bi-Monthly";
						else if (frequency.equalsIgnoreCase("Q"))
							frequency = "Quaterly";
						else if (frequency.equalsIgnoreCase("HY"))
							frequency = "Half Yearly";
						else if (frequency.equalsIgnoreCase("Y"))
							frequency = "Yearly";

						String mailSubject = null, mailTitle = null;
						List data1 = null;
						if (escL != null && !escL.equalsIgnoreCase(" "))
						{
							String contactId = escL.replace("#", ",").substring(0, (escL.toString().length() - 1));
							String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
							data1 = new createTable().executeAllSelectQuery(query2, connection);
						}
						if (data1 != null && data1.size() > 0)
						{
							mailSubject = "Asset Escalation Alert: L" + currentEscLevel + " to L" + nextEscLevel + "!";
							mailTitle = mailSubject;
							msgText = "Asset Escalation Alert: L" + currentEscLevel + " to L" + nextEscLevel + " for Asset Name: " + assetName + ", had Due Date "
							        + DateUtil.convertDateToIndianFormat(dueDate) + ".";
							dueDate = "Passed & was on " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object object2[] = (Object[]) iterator2.next();
								StringBuilder str = new StringBuilder();
								String mappedTeam = null;
								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[3].toString().equalsIgnoreCase(object3[3].toString()))
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

								if (object2[0] != null && object2[0].toString() != "")
									cmnHelper.addMessage("", "", object2[0].toString(), msgText, "", "Pending", "0", "Asset", connection);

								String mailText = getConfigMail(assetName, serialNo, brand, frequency, "Yes", reminderId, mappedTeam, dueDate, object2[2].toString(), object2[3].toString(), mailTitle,
								        "Pending");
								if (object2[1] != null && object2[1].toString() != "")
									cmnHelper.addMail("", "", object2[1].toString(), mailSubject, mailText, "", "Pending", "0", attachmentPath, "Asset", connection);
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

	@SuppressWarnings(
	{ "unchecked", "static-access", "rawtypes" })
	public void checkRecurring(SessionFactory connection)
	{
		AssetServiceHelper srvcHelper = new AssetServiceHelper();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List validData = srvcHelper.getRecurringData(connection);
		if (validData != null && validData.size() > 0)
		{
			for (Iterator iterator = validData.iterator(); iterator.hasNext();)
			{
				String nextEscLevel = null, nextEscDate = null, nextEscTime = null, escalation = null, frequency = null;
				String remindDate = null, dueDate = null, dueTime = null, docPath1 = null, docPath2 = null, userName = null, reminderId = null, assetName = null,serialNo=null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List remindDateList = new ArrayList();
				InsertDataTable insertObj = null;
				Object object[] = (Object[]) iterator.next();
				List reminderDate = srvcHelper.getReminderDate(object[0].toString(), connection);

				// Code For Update Asset Reminder Table
				if (reminderDate != null && reminderDate.size() > 0)
				{
					for (Iterator iterator2 = reminderDate.iterator(); iterator2.hasNext();)
					{
						Object object1[] = (Object[]) iterator2.next();
						if (object[1] != null && object[1].toString().equalsIgnoreCase("W"))
						{
							dueDate = DateUtil.getNewDate("day", 7, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("M"))
						{
							dueDate = DateUtil.getNewDate("month", 1, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("BM"))
						{
							dueDate = DateUtil.getNewDate("day", 15, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}

						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("Q"))
						{
							dueDate = DateUtil.getNewDate("month", 3, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("HY"))
						{
							dueDate = DateUtil.getNewDate("month", 6, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("Y"))
						{
							dueDate = DateUtil.getNewDate("year", 1, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
								remindDateList.add(remindDate);
							}
						}
						else if (object[1] != null && object[1].toString().equalsIgnoreCase("D"))
						{
							dueDate = DateUtil.getNewDate("day", 1, object[3].toString());
							if (object1[0] != null && object1[0].toString().equalsIgnoreCase("D"))
							{
								remindDate = dueDate;
							}
							else if (object1[0] != null && object1[0].toString().equalsIgnoreCase("R"))
							{
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(object1[2].toString()), dueDate);
							}
						}

						Map<String, Object> setVariables = new HashMap<String, Object>();
						Map<String, Object> whereCondition = new HashMap<String, Object>();
						whereCondition.put("id", object1[3].toString());
						setVariables.put("due_date", dueDate);
						setVariables.put("remind_date", remindDate);
						setVariables.put("reminder_status", "0");
						setVariables.put("status", "Pending");
						cbt.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connection);
					}
				}

				if (object[0] != null)
					reminderId = object[0].toString();

				if (object[1] != null)
					frequency = object[1].toString();

				if (object[2] != null)
					escalation = object[2].toString();

				if (object[4] != null)
					dueTime = object[4].toString();

				if (object[13] != null)
					docPath1 = object[13].toString();

				if (object[14] != null)
					docPath2 = object[14].toString();

				if (object[15] != null)
					userName = object[15].toString();

				if (object[16] != null)
					assetName = object[16].toString();
				if (object[17]!=null)
                {
	                serialNo=object[17].toString();
                }

				//List complSeries = new AssetServiceHelper().getCompIdPrefix_Suffix(deptId, frequency, connection);

				/*String prefix = null;
				String suffix = null;
				if (complSeries != null && complSeries.size() > 0)
				{
					if (complSeries.get(0) != null)
						prefix = complSeries.get(0).toString();

					if (complSeries.get(1) != null)
						suffix = complSeries.get(1).toString();
				}*/

				// Code For Update asset reminder Detail Table
				if (object[2] != null && object[2].toString().equalsIgnoreCase("Y"))
				{
					if (object[5] != null && !object[6].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "1";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[6].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[7] != null && !object[8].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "2";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[8].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[9] != null && !object[10].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "3";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[10].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[11] != null && !object[12].toString().equalsIgnoreCase("00:00"))
					{
						nextEscLevel = "4";
						String str[] = DateUtil.newdate_AfterEscTime(dueDate, object[4].toString(), object[12].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}

					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", object[0].toString());
					setVariables.put("dueDate", dueDate);
					setVariables.put("actionStatus", "Pending");
					setVariables.put("actionTaken", "Pending");
					/*if (prefix != null && suffix != null)
					{
						setVariables.put("compid_prefix", prefix);
						setVariables.put("compid_suffix", suffix);
					}*/
					setVariables.put("escalate_date", nextEscDate);
					setVariables.put("escalate_time", nextEscTime);
					setVariables.put("current_esc_level", nextEscLevel);
					cbt.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);
				}
				else
				{
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", object[0].toString());
					setVariables.put("dueDate", dueDate);
					setVariables.put("actionStatus", "Pending");
					setVariables.put("actionTaken", "Pending");
					/*if (prefix != null && suffix != null)
					{
						setVariables.put("compid_prefix", prefix);
						setVariables.put("compid_suffix", suffix);
					}*/
					cbt.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);
				}
				try
				{
					// data insert into asset report table.
					insertData = new ArrayList<InsertDataTable>();

					/*if (prefix != null && suffix != null)
					{
						insertObj = new InsertDataTable();
						insertObj.setColName("complainceId");
						insertObj.setDataName(prefix + suffix);
						insertData.add(insertObj);
					}*/

					if (remindDateList != null && remindDateList.size() > 0)
					{
						for (int i = 0; i < remindDateList.size(); i++)
						{
							insertObj = new InsertDataTable();
							insertObj.setColName("reminder" + (i + 1));
							insertObj.setDataName(remindDateList.get(i));
							insertData.add(insertObj);
						}
					}
					insertObj = new InsertDataTable();
					insertObj.setColName("dueDate");
					insertObj.setDataName(dueDate);
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("dueTime");
					insertObj.setDataName(dueTime);
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("userName");
					insertObj.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("actionTaken");
					insertObj.setDataName("Pending");
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("assetReminderID");
					insertObj.setDataName(reminderId);
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("document_config_1");
					insertObj.setDataName(docPath1);
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("document_config_2");
					insertObj.setDataName(docPath2);
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("actionTakenDate");
					insertObj.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(insertObj);

					insertObj = new InsertDataTable();
					insertObj.setColName("actionTakenTime");
					insertObj.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(insertObj);

					if (escalation.equalsIgnoreCase("Y"))
					{
						insertObj = new InsertDataTable();
						insertObj.setColName("current_esc_level");
						insertObj.setDataName("1");
						insertData.add(insertObj);
					}

					cbt.insertIntoTable("asset_report", insertData, connection);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({  "rawtypes" })
	public void checkSnooze(SessionFactory connection)
	{

		AssetServiceHelper srvcHelper = new AssetServiceHelper();
		CommunicationHelper cmnHelper = new CommunicationHelper();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List validData = srvcHelper.getSnoozeData(connection);
		if (validData != null && validData.size() > 0)
		{
			String frequency = null, reminderId = null, escalation = null, dueDate = null, dueTime = null, snoozeDate = null, snoozeTime = null, currentEscLevel = null, reminderFor = null;
			String serialNo = null, assetName = null, brand = null, model = null, comment = null;
			for (Iterator iterator = validData.iterator(); iterator.hasNext();)
			{
				String nextEscDate = null, nextEscTime = null;
				String remindDate = null;
				Object object[] = (Object[]) iterator.next();

				reminderId = object[0].toString();

				if (object[1] != null)
					frequency = object[1].toString();

				if (object[3] != null)
					dueDate = object[3].toString();

				if (object[4] != null)
					dueTime = object[4].toString();

				if (object[13] != null)
					snoozeDate = object[13].toString();

				if (object[14] != null)
					snoozeTime = object[14].toString();

				if (object[16] != null)
					reminderFor = object[16].toString();

				if (object[17] != null)
					assetName = object[17].toString();

				if (object[18] != null)
					serialNo = object[18].toString();
				
				if (object[19] != null)
					brand = object[19].toString();

				if (object[20] != null)
					model = object[20].toString();

				if (object[21] != null)
					comment = object[21].toString();
				else
					comment = "NA";

				if (object[2].toString().equalsIgnoreCase("N"))
					escalation = "No";
				else
					escalation = "Yes";

				List reminderData = srvcHelper.getReminderDate(object[0].toString(), connection);
				if (reminderData != null && reminderData.size() > 0)
				{
					for (Iterator iterator2 = reminderData.iterator(); iterator2.hasNext();)
					{
						Object object1[] = (Object[]) iterator2.next();
						String newDueDate = null;
						String reminderCode = null, remindInterval = null;

						reminderCode = object1[0].toString();

						if (object1[2] != null)
							remindInterval = object1[2].toString();

						if (frequency != null && frequency.equalsIgnoreCase("OT"))
						{
							newDueDate = object[13].toString();
							dueTime = object[14].toString();
						}
						else if (frequency != null && frequency.equalsIgnoreCase("W"))
						{
							newDueDate = DateUtil.getNewDate("day", 7, dueDate);

							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -(Integer.valueOf(remindInterval)), newDueDate);

						}
						else if (frequency != null && frequency.equalsIgnoreCase("M"))
						{
							newDueDate = DateUtil.getNewDate("month", 1, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("BM"))
						{
							newDueDate = DateUtil.getNewDate("day", 15, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("Q"))
						{
							newDueDate = DateUtil.getNewDate("month", 3, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("HY"))
						{
							newDueDate = DateUtil.getNewDate("month", 6, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						else if (frequency != null && frequency.equalsIgnoreCase("Y"))
						{
							newDueDate = DateUtil.getNewDate("year", 1, dueDate);
							if (reminderCode != null && reminderCode.equalsIgnoreCase("D"))
								remindDate = newDueDate;
							else if (reminderCode != null && reminderCode.equalsIgnoreCase("R"))
								remindDate = DateUtil.getNewDate("day", -Integer.valueOf(remindInterval), newDueDate);
						}
						Map<String, Object> setVariables = new HashMap<String, Object>();
						Map<String, Object> whereCondition = new HashMap<String, Object>();
						new HashMap<String, Object>();
						whereCondition.put("id", object1[3].toString());

						if (frequency != null && frequency.equalsIgnoreCase("OT"))
						{
							setVariables.put("remind_date", newDueDate);
							setVariables.put("remind_time", dueTime);
							setVariables.put("reminder_status", "311");
						}
						else
						{
							if (reminderCode.equalsIgnoreCase("S"))
							{
								setVariables.put("reminder_status", "311");
								setVariables.put("status", "Snooze");

							}
							else
							{
								setVariables.put("due_date", newDueDate);
								setVariables.put("remind_date", remindDate);
								setVariables.put("reminder_status", "0");
								setVariables.put("status", "Pending");
							}
						}
						cbt.updateTableColomn("asset_reminder_data", setVariables, whereCondition, connection);
					}
				}

				// Code For Update Compliance Detail Table
				if (object[2] != null && object[2].toString().equalsIgnoreCase("Y"))
				{
					if (object[5] != null && !object[6].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("1"))
					{
						// nextEscLevel="1";
						String str[] = DateUtil.newdate_AfterEscTime(object[13].toString(), object[14].toString(), object[6].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[7] != null && !object[8].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("2"))
					{
						// nextEscLevel="2";
						String str[] = DateUtil.newdate_AfterEscTime(object[13].toString(), object[14].toString(), object[8].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[9] != null && !object[10].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("3"))
					{
						// nextEscLevel="3";
						String str[] = DateUtil.newdate_AfterEscTime(object[13].toString(), object[14].toString(), object[10].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
					else if (object[11] != null && !object[12].toString().equalsIgnoreCase("00:00") && object[15].toString().equalsIgnoreCase("4"))
					{
						// nextEscLevel="4";
						String str[] = DateUtil.newdate_AfterEscTime(object[13].toString(), object[14].toString(), object[12].toString(), "Y").split("#");
						nextEscDate = str[0];
						nextEscTime = str[1];
					}
				}
				else
				{
					nextEscDate = "NA";
					nextEscTime = "NA";
				}

				if (frequency.equalsIgnoreCase("OT"))
					frequency = "One Time";
				else if (frequency.equalsIgnoreCase("D"))
					frequency = "Daily";
				else if (frequency.equalsIgnoreCase("W"))
					frequency = "Weekly";
				else if (frequency.equalsIgnoreCase("M"))
					frequency = "Monthly";
				else if (frequency.equalsIgnoreCase("BM"))
					frequency = "Bi-Monthly";
				else if (frequency.equalsIgnoreCase("Q"))
					frequency = "Quaterly";
				else if (frequency.equalsIgnoreCase("HY"))
					frequency = "Half Yearly";
				else if (frequency.equalsIgnoreCase("Y"))
					frequency = "Yearly";

				Map<String, Object> setVariables = new HashMap<String, Object>();
				Map<String, Object> whereCondition = new HashMap<String, Object>();
				whereCondition.put("id", reminderId);
				setVariables.put("dueDate", dueDate);
				setVariables.put("actionStatus", "Pending");
				setVariables.put("actionTaken", "Pending");
				if (nextEscDate != null && !nextEscDate.equalsIgnoreCase(""))
					setVariables.put("escalate_date", nextEscDate);

				if (nextEscTime != null && !nextEscTime.equalsIgnoreCase(""))
					setVariables.put("escalate_time", nextEscTime);

				boolean flag = cbt.updateTableColomn("asset_reminder_details", setVariables, whereCondition, connection);
				if (flag)
				{
					if (reminderFor != null)
					{
						String mailSubject = null, mailTitle = null;
						mailSubject = "Asset Snooze To Active Alert:";
						mailTitle = mailSubject;
						String msgText = "Asset Snooze Alert: For Asset Name: " + assetName + ",  has been snoozed till " + DateUtil.convertDateToIndianFormat(snoozeDate) + " " + snoozeTime
						        + ", Comments: " + comment + ".";
						dueDate = "Passed & was on " + DateUtil.convertDateToIndianFormat(dueDate) + " " + dueTime;

						String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));
						// String
						// query2="SELECT mobOne,emailIdOne,empName,id FROM employee_basic WHERE id IN("+empId+")";
						String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
						List data1 = new createTable().executeAllSelectQuery(query2, connection);
						if (data1 != null && data1.size() > 0)
						{
							String mobileNo = null, emailId = null, empName = null,empId=null;
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
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
									empId = object2[3].toString();

								for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
								{
									Object object3[] = (Object[]) iterator3.next();
									if (!object2[3].toString().equalsIgnoreCase(object3[3].toString()))
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
									cmnHelper.addMessage(empName, object2[4].toString(), mobileNo, msgText, "", "Pending", "0", "Asset", connection);

								String mailText = getConfigMail(assetName, serialNo, brand, frequency, escalation, reminderId, mappedTeam, dueDate, empName, empId, mailTitle, "Pending");
								if (emailId != null && emailId.toString() != "")
									cmnHelper.addMail(empName, object2[4].toString(), emailId, mailSubject, mailText, "", "Pending", "0", "", "Asset", connection);
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings({  "rawtypes" })
	public void checkTodayPending(SessionFactory connection)
	{
		try
		{
			AssetServiceHelper srvcHelper = new AssetServiceHelper();
			CommunicationHelper cmnHelper = new CommunicationHelper();
			// List reportTimeList=srvcHelper.getReportTime(connection);
			List data = srvcHelper.getTodayReminderFor(connection);
			Map<String, String> idWithData = new LinkedHashMap<String, String>();
			StringBuilder strBuilder = new StringBuilder();
			StringBuilder empIdBuilder = new StringBuilder(",");
			String frequency = null, reminderId = null, dueDate = null, dueTime = null, actionType = null;
			String status = null, assetName = null, serialNo = null, brand = null, emailId = null, empName = null;
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					if (object[1] != null && !object[1].toString().equalsIgnoreCase(""))
					{
						strBuilder.append(object[1].toString());
						idWithData.put(object[0].toString(), "#" + object[1].toString());
					}
				}
				if (strBuilder != null)
				{
					String empId[] = strBuilder.toString().split("#");
					for (int j = 0; j < empId.length; j++)
					{
						if (!empIdBuilder.toString().contains("," + empId[j] + ","))
						{
							empIdBuilder.append(empId[j] + ",");
						}
					}
					empIdBuilder = empIdBuilder.replace(0, 1, "");
					empId = empIdBuilder.toString().split(",");

					for (int i = 0; i < empId.length; i++)
					{
						StringBuilder remIdList = new StringBuilder();
						Iterator<Entry<String, String>> hiterator = idWithData.entrySet().iterator();
						while (hiterator.hasNext())
						{
							Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
							if (paramPair.getValue().contains("#" + empId[i] + "#"))
							{
								remIdList.append(paramPair.getKey() + ",");
							}
						}
						if (remIdList != null)
						{
							AssetDashboardBean obj = null;
							String mappedTeam = null;
							String remID = remIdList.toString().substring(0, (remIdList.toString().length() - 1));
							String query = "SELECT ard.id,ad.assetname,ad.serialno,ard.frequency,ad.assetbrand,ard.dueDate,ard.dueTime,ard.reminder_for,ard.actionStatus, "
							        + "ard.action_type FROM asset_reminder_details AS ard INNER JOIN asset_detail AS ad ON ard.assetid=ad.id  "
							        + "WHERE ard.id IN(" + remID + ")";
							List data1 = new createTable().executeAllSelectQuery(query, connection);
							if (data1 != null && data1.size() > 0)
							{
								List<AssetDashboardBean> listForMail = new ArrayList<AssetDashboardBean>();
								for (Iterator iterator = data1.iterator(); iterator.hasNext();)
								{
									obj = new AssetDashboardBean();
									Object[] object = (Object[]) iterator.next();
									if (object != null)
									{
										if (object[0] != null)
										{
											reminderId = object[0].toString();
										}
										if (object[1] != null)
										{
											assetName = object[1].toString();
										}
										else
										{
											assetName = "NA";
										}
										if (object[2] != null)
										{
											serialNo = object[2].toString();
										}
										else
										{
											serialNo = "NA";
										}
										if (object[3] != null)
										{
											if (object[3].toString().equalsIgnoreCase("OT"))
												frequency = "One Time";
											else if (object[3].toString().equalsIgnoreCase("D"))
												frequency = "Daily";
											else if (object[3].toString().equalsIgnoreCase("W"))
												frequency = "Weekly";
											else if (object[3].toString().equalsIgnoreCase("M"))
												frequency = "Monthly";
											else if (object[3].toString().equalsIgnoreCase("BM"))
												frequency = "Bi-Monthly";
											else if (object[3].toString().equalsIgnoreCase("Q"))
												frequency = "Quaterly";
											else if (object[3].toString().equalsIgnoreCase("HY"))
												frequency = "Half Yearly";
											else if (object[3].toString().equalsIgnoreCase("Y"))
												frequency = "Yearly";
										}
										else
										{
											frequency = "NA";
										}
										if (object[4] != null)
										{
											brand = object[4].toString();
										}
										else
										{
											brand = "NA";
										}
										if (object[5] != null)
										{
											dueDate = DateUtil.convertDateToIndianFormat(object[5].toString());
										}
										else
										{
											dueDate = "NA";
										}
										if (object[6] != null)
										{
											dueTime = object[6].toString();
										}
										else
										{
											dueTime = "NA";
										}
										if (object[7] != null)
										{
											String contactId = object[7].toString().replace("#", ",").substring(0, (object[7].toString().length() - 1));
											String query2 = "SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,cc.id AS contId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + contactId + ")";
											List empDataList = new createTable().executeAllSelectQuery(query2, connection);
											if (empDataList != null && empDataList.size() > 0)
											{
												for (Iterator iterator2 = empDataList.iterator(); iterator2.hasNext();)
												{
													Object object2[] = (Object[]) iterator2.next();
													StringBuilder str = new StringBuilder();
													if (object2[3] != null)
													{
														if (empId[i].equals(object2[3].toString()))
														{
															emailId = object2[1].toString();
															empName = object2[2].toString();
														}
													}
													for (Iterator iterator3 = empDataList.iterator(); iterator3.hasNext();)
													{
														Object object3[] = (Object[]) iterator3.next();
														if (!object2[3].toString().equalsIgnoreCase(object3[3].toString()))
														{
															str.append(object3[2].toString() + ", ");
														}
													}
													if (str != null && str.toString().length() > 0)
													{
														mappedTeam = str.toString().substring(0, str.toString().length() - 1);
													}
													if (mappedTeam == null)
														mappedTeam = "You";
													else
														mappedTeam = mappedTeam + " and You";
												}
											}
										}
										if (object[8] != null)
										{
											status = object[8].toString();
										}
										else
										{
											status = "NA";
										}
										if (object[9] != null)
										{
											actionType = object[9].toString();
										}
										else
										{
											actionType = "NA";
										}
										obj.setAssetid(reminderId);;
										obj.setAssetName(assetName);
										obj.setAssetserial(serialNo);
										obj.setBrand(brand);
										obj.setFrequency(frequency);
										obj.setDueDate(dueDate);
										obj.setDueTime(dueTime);
										obj.setMappedTeam(mappedTeam);
										obj.setActionStatus(status);
										// obj.setActionType(actionType);
										obj.setEmpId(empId[i]);
										obj.setActionType(actionType);
									}
									listForMail.add(obj);
									String mailText = getConfigMultipleAssetMail(listForMail, empName, "Today All Pending Asset");
									if (emailId != null && emailId.toString() != "")
										cmnHelper.addMail("", "", emailId, "Today All Pending Asset", mailText, "", "Pending", "0", "", "Asset", connection);
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

	public String getConfigMail(String assetName, String serialNO, String brand, String frequency, String escalation, String reminderID, String mappedTeam, String dueDate, String empName,
	        String empId, String mailTitle, String status)
	{
		String acId = null;
		try
		{
			acId = Cryptography.encrypt("IN-1", DES_ENCRYPTION_KEY);
			empId = Cryptography.encrypt(empId, DES_ENCRYPTION_KEY);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String ip = null;
		try
		{
			ip = InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		String URL = "<a href=http://" + ip + ":8084//over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeTakeActionFromMail?assetReminderID=" + reminderID + "&dbName=" + acId + "&empId=" + empId
		        + ">Click Here!!</a>";

		// String urlIS
		// ="http://124.124.47.149:8080//over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeTakeActionFromMail?complID="+complianceID+"&dbName="+acId+"&empId="+empId;
		StringBuilder mailtext = new StringBuilder("");

		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		mailtext.append(mailTitle);
		mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Asset&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + assetName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Serial&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + serialNO + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Brand:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + brand + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + dueDate + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + frequency + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + mappedTeam + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Escalation:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + escalation + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
		        + status + "</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append("<tr><td>You are requested to take an action by clicking on below Link:</td></tr>");
		mailtext.append(URL);
		mailtext.append("<br>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public String getConfigMultipleAssetMail(List<AssetDashboardBean> mailDetail, String empName, String mailTitle)
	{
		StringBuilder mailtext = new StringBuilder("");
		if (mailDetail != null && mailDetail.size() > 0)
		{
			mailtext.append("<br><b>Dear " + empName + ",</b><br>");
			mailtext.append(mailTitle);
			mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Take&nbsp;Action</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Asset&nbsp;Name</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Serial&nbsp;No</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Brand</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Time</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;Type</b></td></tr>");
			for (AssetDashboardBean object : mailDetail)
			{
				// String[] object = (String[]) iterator.next();
				String acId = null, empId = null;
				try
				{
					acId = Cryptography.encrypt("IN-1", DES_ENCRYPTION_KEY);
					// empId=Cryptography.encrypt(object.getEmpId(),DES_ENCRYPTION_KEY);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				// HttpServletRequest req = ServletActionContext.getRequest();
				String ip = null;
				try
				{
					ip = InetAddress.getLocalHost().getHostAddress();
				}
				catch (UnknownHostException e)
				{
					e.printStackTrace();
				}
				// int portNo=req.getServerPort();
				String URL = "<a href=http://" + ip + ":8084//over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/beforeTakeActionFromMail?assetReminderID=" + object.getAssetid() + "&dbName=" + acId
				        + "&empId=" + empId + ">Click Here!!</a>";

				// String
				// urlIS="<a href=http://124.124.47.149:8080//over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeTakeActionFromMail?complID="+object.getCompId()+"&dbName="+acId+"&empId="+empId+">Click Here!!</a>";

				mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + URL + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getAssetName() + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getAssetserial() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getBrand() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getFrequency() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueDate() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueTime() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getMappedTeam() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionStatus() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionType() + "</b></td>");
			}
			mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
			mailtext.append("<BR>");
			mailtext.append("<BR>");
			mailtext.append("<br>");
			mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		}
		return mailtext.toString();
	}

	public String configReportComp(List<ComplianceDashboardBean> pendingDataMailDetail, List<ComplianceDashboardBean> actionDataMailDetail, String empName, String mailTitle)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><b>Dear " + empName + ",</b><br>");
		int i = 1;
		if (pendingDataMailDetail != null && pendingDataMailDetail.size() > 0)
		{
			mailtext.append("<br><br><b><center>Compliance Task Due Today (" + DateUtil.getCurrentDateIndianFormat() + ")</center></b>");
			mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> S.No</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Dept&nbsp;Name</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;</b></td>");
			mailtext.append("<td align='left' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");
			for (ComplianceDashboardBean object : pendingDataMailDetail)
			{

				mailtext.append("<tr  bgcolor='#e8e7e8'>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + i + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskType() + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDepartName() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskName() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskBrief() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getFrequency() + "</b></td>");
				mailtext.append("<td align='left' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueDate() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getMappedTeam() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionStatus()
				        + "</b></td></tr>");
				i++;
			}
			mailtext.append("</tbody></table>");

		}

		if (actionDataMailDetail != null && actionDataMailDetail.size() > 0)
		{
			i = 1;
			mailtext.append("<br><br><b><center>Compliance Task Action Today (" + DateUtil.getCurrentDateIndianFormat() + ")</center></b>");
			mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> S.No</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Type</b>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Dept&nbsp;Name</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency&nbsp;</b></td>");
			mailtext.append("<td align='left' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;By</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td>");
			mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments</b></td>");
			for (ComplianceDashboardBean object : actionDataMailDetail)
			{

				mailtext.append("<tr  bgcolor='#e8e7e8'>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + i + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskType() + "</b>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDepartName() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskName() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getTaskBrief() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getFrequency() + "</b></td>");
				mailtext.append("<td align='left' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getDueDate() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getMappedTeam() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getEmpId() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getActionStatus() + "</b></td>");
				mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + object.getComment() + "</b></td>");
				mailtext.append("</tr>");
				i++;
			}
			mailtext.append("</tbody></table>");

		}

		mailtext.append("<font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<BR>");
		mailtext.append("<BR>");
		mailtext.append("<br>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");

		return mailtext.toString();
	}
}
