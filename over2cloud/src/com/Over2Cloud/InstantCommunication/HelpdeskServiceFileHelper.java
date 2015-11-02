package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackReportPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.feedback.report.WriteFeedbackData;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class HelpdeskServiceFileHelper
{
	String id = "", mobno = "", msg_text = "", module = "", date = "", time = "", updateQry = "", mappingUpdateQry = "";
	String new_escalation_datetime = "0000-00-00#00:00", non_working_timing = "00:00";
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	String deptComment = "NA";

	// Method body for Escalating Ticket @Khushal 24-01-2014
	@SuppressWarnings("unchecked")
	public void escalateToNextLevel(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			String query = "";
			query = "select id ,ticket_no ,feed_by ,to_dept_subdept,feed_brief,escalation_date ,escalation_time,open_date,open_time,location,level,sub_catg from feedback_status_pdm where status='Pending' and escalation_date<='" + DateUtil.getCurrentDateUSFormat() + "'";
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				@SuppressWarnings("unused")
				String id = null, ticket_no = null, feed_by = null, feed_by_mobno = null, feed_by_emailid = null, to_dept = null, feed_brief = null, to_dept_subdept = "", escalation_date = null, escalation_time = null, open_date = null, open_time = null, location = null, level = null, subCatg = null, catg_Dept = null, esc_duration = null, needesc = null, module = "FM";
				String adrr_time="",res_time = "", non_working_time = "00:00";
				String levelMsg = null, subject = null, ulevel = null, _2uLevel = null, _3uLevel = null;
				List esclevelData = null;

				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticket_no = object[1].toString();
					} else
					{
						ticket_no = "NA";
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						feed_by = object[2].toString();
					} else
					{
						feed_by = "NA";
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						to_dept_subdept = object[3].toString();
					} else
					{
						to_dept_subdept = "NA";
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						feed_brief = object[4].toString();
					} else
					{
						feed_brief = "NA";
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						escalation_date = object[5].toString();
					} else
					{
						escalation_date = "NA";
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						escalation_time = object[6].toString();
					} else
					{
						escalation_time = "NA";
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						open_date = object[7].toString();
					} else
					{
						open_date = "NA";
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						open_time = object[8].toString();
					} else
					{
						open_time = "NA";
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						location = object[11].toString();
					} else
					{
						location = "NA";
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						level = object[10].toString();
					} else
					{
						level = "NA";
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						subCatg = object[11].toString();
					} else
					{
						subCatg = "NA";
					}

					if (escalation_date != null && !escalation_date.equals("") && !escalation_date.equals("NA") && escalation_time != null && !escalation_time.equals("") && !escalation_time.equals("NA") && level != null && !level.equals("") && !level.equals("NA") && !level.equals("Level5"))
					{
						boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
						// If Escalating time passed away then go inside the If
						// block
						if (escnextlvl)
						{

							catg_Dept = to_dept_subdept;

							// Getting Detail from Sub Category Table
							if (subCatg != null && !subCatg.equals("") && !subCatg.equals("0"))
							{
								query = "select * from feedback_subcategory where id='" + subCatg + "'";
								List subCategoryList = HUH.getData(query, connection);
								if (subCategoryList != null && subCategoryList.size() > 0)
								{
									for (Iterator iterator5 = subCategoryList.iterator(); iterator5.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator5.next();
										if (objectCol[4] == null)
										{
											adrr_time = "06:00";
										} else
										{
											adrr_time = objectCol[4].toString();
										}
										if (objectCol[5] == null)
										{
											res_time = "10:00";
										} else
										{
											res_time = objectCol[5].toString();
										}
										if (objectCol[9] == null)
										{
											needesc = "Y";
										} else
										{
											needesc = objectCol[9].toString();
										}
									}
								}

								WorkingHourAction WHA = new WorkingHourAction();
								String new_date = null, new_time = null, datetime = null;
								if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
								{
									String[] newdate_time = null;
									List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, res_time, "05:00", needesc, escalation_date, escalation_time, module);
									if (dateTime != null && dateTime.size() > 0)
									{
										new_date = dateTime.get(0);
										new_time = dateTime.get(1);
									}
								}

								String updateLevel = "";
								ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
								subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
								levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " + feed_brief + ".";
								esclevelData = HUH.getEmp4Escalation(to_dept_subdept, "SD", module, ulevel, "N", "", connection);
								updateLevel = "Level" + ulevel;
								if (esclevelData == null || esclevelData.size() == 0)
								{
									_2uLevel = "" + (Integer.parseInt(ulevel) + 1);
									subject = "L" + level.substring(5) + " to L" + (_2uLevel) + " Escalate Alert: " + ticket_no;
									levelMsg = "L" + level.substring(5) + " to L" + (_2uLevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " + feed_brief + ".";
									esclevelData = HUH.getEmp4Escalation(to_dept_subdept, "SD", module, _2uLevel, "N", "", connection);
									updateLevel = "Level" + _2uLevel;
									if (esclevelData == null || esclevelData.size() == 0)
									{
										_3uLevel = "" + (Integer.parseInt(_2uLevel) + 1);
										subject = "L" + level.substring(5) + " to L" + (_3uLevel) + " Escalate Alert: " + ticket_no;
										levelMsg = "L" + level.substring(5) + " to L" + (_3uLevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " + feed_brief + ".";
										esclevelData = HUH.getEmp4Escalation(to_dept_subdept, "SD", module, _3uLevel, "N", "", connection);
										updateLevel = "Level" + _3uLevel;
									}
								}

								if (esclevelData != null && esclevelData.size() > 0)
								{
									boolean updatefeedback=false;
									String escDetails="";
									for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
									{
										Object[] object3 = (Object[]) iterator3.next();

										if (escalation_date != null && escalation_date != "" && escalation_time != null && escalation_time != "")
										{
											updateQry = "UPDATE feedback_status_pdm SET escalation_date='" + new_date + "',escalation_time='" + new_time + "',level='" + updateLevel + "' WHERE id=" + id;
											updatefeedback = HUH.updateData(updateQry, connection);
											
											List data = null;
											FeedbackPojo fbp = null;
											 if (module.equalsIgnoreCase("FM"))
											{
												data = new FeedbackUniversalHelper().getFeedbackDetail("", "", "Pending", id, connection);
												fbp = new FeedbackUniversalHelper().setFeedbackDataValues(data, "Pending", "");
											}

											if (updatefeedback)
											{
												escDetails+=object3[1]+", ";
												if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
												{
													CH.addMessage(object3[1].toString(), object3[4].toString(), object3[2].toString(), levelMsg, "", "Pending", "0", module, connection);
												}
												if (fbp != null)
												{
													String mailText = HUH.configMessage4Escalation(fbp, subject, object3[1].toString(), "SD", true);
													CH.addMail(object3[1].toString(), object3[4].toString(), object3[3].toString(), subject, mailText, "", "Pending", "0", null, module, connection);
												}
												if (fbp != null && fbp.getFeed_registerby() != null && !fbp.getFeed_registerby().equals("") && fbp.getMobOne() != null && !fbp.getMobOne().equals("") && !fbp.getMobOne().equals("NA"))
												{
													String msg = "Dear " + fbp.getFeed_by() + ", We apologies for delay of Ticket No: " + fbp.getTicket_no() + " that has now been escalated to " + DateUtil.makeTitle(object3[1].toString()) + ", Mob: " + object3[2].toString() + ". Thanks.";
													CH.addMessage(fbp.getFeed_by(), fbp.getFeedback_by_dept(), fbp.getMobOne(), msg, "", "Pending", "0", module, connection);
												}
											}
										}
									}
									if(updatefeedback)
									{
										insertIntoHistory(id, "Pending", updateLevel, escDetails.substring(0,escDetails.length()-2), connection);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean insertIntoHistory(String id, String hStatus, String level, String name, SessionFactory connectionSpace)
	{
		List<InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		boolean isInserted = false;
		InsertDataTable ob = new InsertDataTable();
		ob.setColName("feed_id");
		ob.setDataName(id);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("status");
		ob.setDataName(hStatus);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_date");
		ob.setDataName(DateUtil.getCurrentDateUSFormat());
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_time");
		ob.setDataName(DateUtil.getCurrentTime());
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("esc_level");
		ob.setDataName(level);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("escalate_to");
		ob.setDataName(name);
		insertData.add(ob);

		// Method calling for inserting the values in the feedback_status table
		isInserted = cbt.insertIntoTable("feedback_status_pdm_history", insertData, connectionSpace);
		return isInserted;
	}

	// Method body for Snooze to Active ticket status 24-01-2014
	@SuppressWarnings("unchecked")
	public void SnoozetoPending(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{

		try
		{
			String query = "select id ,ticket_no ,feed_by ,to_dept_subdept ,feed_brief ,allot_to ,location ,subCatg ,sn_upto_date ,sn_upto_time,moduleName,non_working_time from feedback_status where status='Snooze' and sn_upto_date<='" + DateUtil.getCurrentDateUSFormat() + "'";
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				String id = null, ticket_no = null, feed_by = null, to_dept = null, feed_brief = null, snooze_upto_date = null, snooze_upto_time = null, location = null, subCatg = null, allot_to = null, adrr_time = null, res_time = null, module = null, needesc = "", non_working_time = "00:00";
				List esclevelData = null;
				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}

					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticket_no = object[1].toString();
					} else
					{
						ticket_no = "NA";
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						feed_by = object[2].toString();
					} else
					{
						feed_by = "NA";
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						to_dept = object[3].toString();
					} else
					{
						to_dept = "NA";
					}

					if (object[4] != null && !object[4].toString().equals(""))
					{
						feed_brief = object[4].toString();
					} else
					{
						feed_brief = "NA";
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						allot_to = object[5].toString();
					} else
					{
						allot_to = "NA";
					}

					if (object[6] != null && !object[6].toString().equals(""))
					{
						location = object[6].toString();
					} else
					{
						location = "NA";
					}

					if (object[7] != null && !object[7].toString().equals(""))
					{
						subCatg = object[7].toString();
					} else
					{
						subCatg = "NA";
					}

					if (object[8] != null && !object[8].toString().equals(""))
					{
						snooze_upto_date = object[8].toString();
					} else
					{
						snooze_upto_date = "NA";
					}

					if (object[9] != null && !object[9].toString().equals(""))
					{
						snooze_upto_time = object[9].toString();
					} else
					{
						snooze_upto_time = "NA";
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						module = object[10].toString();
					} else
					{
						module = "NA";
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						non_working_time = object[11].toString();
					}

					if (snooze_upto_date != null && !snooze_upto_date.equals("") && snooze_upto_time != null && !snooze_upto_time.equals("") && module != null && !module.equals("") && !module.equals("NA"))
					{
						boolean snoozetopending = DateUtil.time_update(snooze_upto_date, snooze_upto_time);
						if (snoozetopending == true)
						{
							// Getting Detail from Sub Category Table
							if (!subCatg.equals("0"))
							{
								String query2 = "select * from feedback_subcategory where id='" + subCatg + "'";
								List subCategoryList = HUH.getData(query2, connection);
								if (subCategoryList != null && subCategoryList.size() > 0)
								{
									for (Iterator iterator2 = subCategoryList.iterator(); iterator2.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator2.next();
										if (objectCol[4] == null)
										{
											adrr_time = "06:00";
										} else
										{
											adrr_time = objectCol[4].toString();
										}
										if (objectCol[5] == null)
										{
											res_time = "10:00";
										} else
										{
											res_time = objectCol[5].toString();
										}
										if (objectCol[9] == null)
										{
											needesc = "Y";
										} else
										{
											needesc = objectCol[9].toString();
										}
									}
								}

								String new_date = null, new_time = null, datetime = null;
								if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
								{
									String[] newdate_time = null;

									getNextEscalationDateTime(adrr_time, res_time, to_dept, module, connection);
									if (new_escalation_datetime != null && new_escalation_datetime != "")
									{
										newdate_time = new_escalation_datetime.split("#");
										if (newdate_time.length > 0)
										{
											new_date = newdate_time[0];
											new_time = newdate_time[1];
											non_working_time = DateUtil.addTwoTime(non_working_time, non_working_timing);
										}
									}
								}
								esclevelData = HUH.getEmployeeData(allot_to.toString(), "2", connection);
								if (esclevelData != null && esclevelData.size() > 0)
								{
									for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
									{
										Object[] object3 = (Object[]) iterator3.next();
										if (snooze_upto_date != null && snooze_upto_date != "" && snooze_upto_time != null && snooze_upto_time != "")
										{
											// datetime =
											// DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(),
											// DateUtil.getCurrentTime(),
											// adrr_time, res_time);

											if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
											{
												updateQry = "update feedback_status set escalation_date='" + new_date + "',escalation_time='" + new_time + "',status='Pending',non_working_time='" + non_working_time + "' where id='" + id + "'";
											} else
											{
												updateQry = "update feedback_status set status='Pending' where id='" + id + "'";
											}

											boolean updatefeedback = HUH.updateData(updateQry, connection);
											List data = HUH.getFeedbackDetail("", "SD", "Pending", id, connection);
											FeedbackPojo fbp = null;
											fbp = HUH.setFeedbackDataValues(data, "Pending", "");
											if (updatefeedback)
											{
												@SuppressWarnings("unused")
												boolean putsmsstatus = false;
												// Code/block for
												// sending the message
												if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
												{
													String msg = "By:Snooze to Active Alert: Ticket No: " + ticket_no + ", Resolved By: " + new_time + ", Reg. By: " + feed_by + ", Location: " + location + ", Brief: " + feed_brief + ".";
													putsmsstatus = CH.addMessage(object3[0].toString(), object3[5].toString(), object3[1].toString(), msg, ticket_no, "Pending", "0", module, connection);
												}
												if (fbp != null)
												{
													String mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, "Snooze --> Pending Feedback Alert", "Pending", "SD", true);
													CH.addMail(object3[0].toString(), object3[5].toString(), object3[4].toString(), "Snooze --> Pending Feedback Alert", mailText, ticket_no, "Pending", "0", "", module, connection);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Evening Report generation witten by : Manab

	public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
	{

		// System.out.println("Inside Important method");
		String startTime = "", endTime = "", callflag = "";
		String dept_id = todept;

		String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
		String working_day = DateUtil.getDayofDate(working_date);
		// List of working timing data
		List workingTimingData = new EscalationHelper().getWorkingTimeData(module, working_day, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			startTime = workingTimingData.get(1).toString();
			endTime = workingTimingData.get(2).toString();

			// Here we know the complaint lodging time is inside the the start
			// and end time or not
			callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
			if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
			{
				// System.out.println("Inside before");

				// date_time = DateUtil.newdate_AfterEscInRoaster(date,
				// startTime, adrr_time, res_time);
				// new_escalation_datetime = DateUtil.newdate_AfterEscTime(date,
				// startTime, esc_duration, needesc);
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
				// System.out.println("After Calculation First escalation Date time is  "+new_escalation_datetime);
				// (Differrence between current date/time and date & starttime)
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					// System.out.println("Inside If when flag is true");
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					// System.out.println("Non Working time inside if  "+non_working_timing);
				} else
				{
					// System.out.println("Inside Else when flag is false");
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					// System.out.println("First Non Working time  "+non_working_timing);
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					// System.out.println("Final After calculation Second Non Working time  "+non_working_timing);
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, newdatetime[0], newdatetime[1]);
					// System.out.println("First diffrence "+timeDiff1);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, working_date, endTime);
					// System.out.println("Second diffrence "+timeDiff2);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					// System.out.println("Main diffrence "+main_difference);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						// System.out.println("Inside Second If   "+workingTimingData.toString());
						startTime = workingTimingData.get(1).toString();
						// System.out.println("Final Start Time :::::::::: "+startTime);
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						// System.out.println("Final Date ::::::::: "+final_date);
						// System.out.println("Final Left time :::::::: "+left_time);
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						// new_escalation_datetime =
						// DateUtil.newdate_AfterEscInRoaster(final_date,
						// startTime, adrr_time, res_time);
						non_working_timing = workingTimingData.get(3).toString();
						// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
			{

				// Escalation date time at the time of opening the ticket
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = "00:00";
				} else
				{
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						// new_escalation_datetime =
						// DateUtil.newdate_AfterEscInRoaster(final_date,
						// startTime, adrr_time, res_time);
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
			{
				// System.out.println("Inside After");

				// System.out.println("Inside After");

				non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, "24:00");

				workingTimingData.clear();
				String main_difference = DateUtil.addTwoTime(adrr_time, res_time);
				// System.out.println("Main Difference in After  "+main_difference);
				workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					// System.out.println("Inside Second If   "+workingTimingData.toString());
					startTime = workingTimingData.get(1).toString();
					// System.out.println("Final Start Time :::::::::: "+startTime);
					String left_time = workingTimingData.get(4).toString();
					String final_date = workingTimingData.get(5).toString();
					// System.out.println("Final Date ::::::::: "+final_date);
					// System.out.println("Final Left time :::::::: "+left_time);
					new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
					// new_escalation_datetime =
					// DateUtil.newdate_AfterEscInRoaster(final_date, startTime,
					// adrr_time, res_time);
					non_working_timing = workingTimingData.get(3).toString();
					// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
				}
			}
		}

		// System.out.println("Final   ::::   Escalation Date  "+new_escalation_datetime);
		// System.out.println("Final Non Working Timing  "+non_working_timing);
	}

	@SuppressWarnings({ "unchecked", "null" })
	public void ReportGeneration(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH, FeedbackUniversalHelper FUH, String IPAddress)
	{
		try
		{
			System.out.println("Inside Try Block");
			List data4report = HUH.getReportToData(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), connection);

			if (data4report != null && data4report.size() > 0)
			{
				// Local variable defined
				@SuppressWarnings("unused")
				String empname = null, mobno = null, emailid = null, subject = null, report_level = null, subdept_dept = null, deptLevel = null, sms_status = null, mail_status = null, type_report = null, id = null, deptname = null, module = null, department = null;
				// Counts for Current Day
				int pc = 0, hc = 0, sc = 0, ic = 0, rc = 0, total = 0;
				int rAc = 0, cfrAc = 0;
				// Counts for Carry Forward
				@SuppressWarnings("unused")
				int cfpc = 0, cfhc = 0, cfsc = 0, cfic = 0, cfrc = 0, cftotal = 0;
				List subDeptList = null;

				for (Iterator iterator = data4report.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null)
					{
						id = object[0].toString();
					}

					if (object[1] != null)
					{
						report_level = object[1].toString();
					}

					if (object[2] != null)
					{
						subdept_dept = object[2].toString();
					}

					if (object[3] != null)
					{
						sms_status = object[3].toString();
					}

					if (object[4] != null)
					{
						mail_status = object[4].toString();
					}

					if (object[5] != null)
					{
						type_report = object[5].toString();
					}

					if (object[6] != null)
					{
						module = object[6].toString();
					}

					if (object[7] != null)
					{
						empname = object[7].toString();
					} else
					{
						empname = "NA";
					}

					if (object[8] != null)
					{
						mobno = object[8].toString();
					} else
					{
						mobno = "NA";
					}

					if (object[9] != null)
					{
						emailid = object[9].toString();
					} else
					{
						emailid = "NA";
					}

					if (object[10] != null)
					{
						subject = object[10].toString();
					} else
					{
						subject = "NA";
					}

					if (object[11] != null && !object[11].toString().equals(""))
					{
						department = object[11].toString();
					} else
					{
						department = "NA";
					}

					String newDate = null;
					if (!type_report.equals("") && !type_report.equals("NA"))
					{
						newDate = HUH.getNewDate(type_report);
					}
					String updateQry = "update report_configuration set report_date='" + newDate + "' where id='" + id + "'";
					// System.out.println("updateQry    "+updateQry);
					boolean updateReport = HUH.updateData(updateQry, connection);
					if (module != null && !module.equals("") && module.equalsIgnoreCase("FM") && updateReport)
					{

						// System.out.println("For Fedback ");
						// For Feedback

						int totalSnooze = 0;
						FeedbackHelper FRH = new FeedbackHelper();

						List currentdayCounter = new ArrayList();
						List CFCounter = new ArrayList();
						List<FeedbackPojo> currentDayResolvedData = null;
						List<FeedbackPojo> currentDayPendingData = null;
						List<FeedbackPojo> currentDaySnoozeData = null;
						List<FeedbackPojo> currentDayHPData = null;
						List<FeedbackPojo> currentDayIgData = null;
						List<FeedbackPojo> CFData = null;
						List<FeedbackReportPojo> FRPList = null;
						FeedbackReportPojo FRP = null;

						FeedbackReportPojo FRP4Counter = null;
						// get Data when Dept Level is 2 in Organization
						int cfp_Total = 0, cd_Total = 0, cd_Pending = 0, total_snooze = 0, CDR_Total = 0;
						deptLevel = report_level;
						if (deptLevel.equals("2"))
						{
							// getting the data for All the departments
							if (report_level.equals("2"))
							{
								deptComment = "All";
								subject = "Patient Delight Score for All Dept. as on " + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime().substring(0, 5) + "";
								StringBuilder sb = new StringBuilder();
								sb.append("select distinct dept.id,dept.deptName from feedback_type as feed");
								sb.append(" inner join department as dept on feed.dept_subdept=dept.id");
								sb.append(" where feed.moduleName='FM'");

								List deptList = new HelpdeskUniversalHelper().getData(sb.toString(), connection);
								if (deptList != null && deptList.size() > 0)
								{
									FRPList = new ArrayList<FeedbackReportPojo>();
									for (Iterator iterator2 = deptList.iterator(); iterator2.hasNext();)
									{
										Object[] object2 = (Object[]) iterator2.next();
										FRP = new FeedbackReportPojo();
										// System.out.println("O index value is   "+object2[0].toString());
										CFCounter.clear();
										currentdayCounter.clear();

										FRP.setCFP(0);
										FRP.setCDT(0);
										FRP.setCDP(0);
										FRP.setTS(0);
										FRP.setCDR(0);
										pc = 0;
										hc = 0;
										sc = 0;
										ic = 0;
										rc = 0;
										total = 0;
										rAc = 0;
										cfrAc = 0;
										cfpc = 0;
										cfhc = 0;
										cfsc = 0;
										cfic = 0;
										cfrc = 0;
										cftotal = 0;
										totalSnooze = 0;

										currentdayCounter = FRH.getTicketCounters(report_level, type_report, true, object2[0].toString(), deptLevel, connection);
										CFCounter = FRH.getTicketCounters(report_level, type_report, false, object2[0].toString(), deptLevel, connection);
										if (currentdayCounter != null && currentdayCounter.size() > 0)
										{
											// System.out.println("Current day Counter for "+object2[1].toString());
											for (Iterator iterator3 = currentdayCounter.iterator(); iterator3.hasNext();)
											{
												Object[] object3 = (Object[]) iterator3.next();
												if (object3[1].toString().equalsIgnoreCase("Pending"))
												{
													pc = Integer.parseInt(object3[0].toString());
												} else if (object3[1].toString().equalsIgnoreCase("Snooze"))
												{
													sc = Integer.parseInt(object3[0].toString());
												} else if (object3[1].toString().equalsIgnoreCase("High Priority"))
												{
													hc = Integer.parseInt(object3[0].toString());
												} else if (object3[1].toString().equalsIgnoreCase("Ignore"))
												{
													ic = Integer.parseInt(object3[0].toString());
												} else if (object3[1].toString().equalsIgnoreCase("Resolved"))
												{
													rc = Integer.parseInt(object3[0].toString());
												}
											}
											total = pc + sc + hc + ic + rc;
										}
										// Check Counter for Carry Forward
										if (CFCounter != null && CFCounter.size() > 0)
										{
											// System.out.println("Carry Forward  Counter for "+object2[1].toString());
											for (Iterator iterator4 = CFCounter.iterator(); iterator4.hasNext();)
											{
												Object[] object4 = (Object[]) iterator4.next();
												if (object4[1].toString().equalsIgnoreCase("Pending"))
												{
													cfpc = Integer.parseInt(object4[0].toString());
												} else if (object4[1].toString().equalsIgnoreCase("Snooze"))
												{
													cfsc = Integer.parseInt(object4[0].toString());
												} else if (object4[1].toString().equalsIgnoreCase("High Priority"))
												{
													cfhc = Integer.parseInt(object4[0].toString());
												}
											}
											cftotal = cfpc + cfhc;
										}
										totalSnooze = sc + cfsc;
										// int
										// cfp_Total=0,cd_Total=0,cd_Pending,total_snooze=0,CDR_Total=0;
										if ((CFCounter != null && CFCounter.size() > 0) || (currentdayCounter != null && currentdayCounter.size() > 0))
										{
											// System.out.println("Final Block "+object2[1].toString());
											FRP.setDeptName(object2[1].toString());
											FRP.setCFP(cftotal);
											FRP.setCDT(total);
											FRP.setCDP(pc);
											FRP.setTS(totalSnooze);
											FRP.setCDR(rc);
											FRPList.add(FRP);
											cfp_Total = cfp_Total + cftotal;
											cd_Total = cd_Total + total;
											cd_Pending = cd_Pending + pc;
											total_snooze = total_snooze + totalSnooze;
											CDR_Total = CDR_Total + rc;
										}
									}
								}

								currentDayResolvedData = FRH.getTicketDataforReport(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, connection);
								currentDayPendingData = FRH.getTicketDataforReport(report_level, type_report, true, "Pending", subdept_dept, deptLevel, connection);
								currentDaySnoozeData = FRH.getTicketDataforReport(report_level, type_report, true, "Snooze", subdept_dept, deptLevel, connection);
								currentDayHPData = FRH.getTicketDataforReport(report_level, type_report, true, "High Priority", subdept_dept, deptLevel, connection);
								currentDayIgData = FRH.getTicketDataforReport(report_level, type_report, true, "Ignore", subdept_dept, deptLevel, connection);
								CFData = FRH.getTicketDataforReport(report_level, type_report, false, "", subdept_dept, deptLevel, connection);

								int pp = 0, pn = 0, All = 0, NR = 0, Neg = 0;

								// Code For First Block(Paper Mode)
								List paperData = new FeedbackHelper().getPaperData(type_report, connection);

								if (paperData != null && paperData.size() > 0)
								{
									for (Iterator iterator2 = paperData.iterator(); iterator2.hasNext();)
									{
										Object[] object3 = (Object[]) iterator2.next();
										if (object3[0] != null && !object3[0].toString().equals("") && object3[0].toString().equalsIgnoreCase("No"))
										{
											pn = Integer.parseInt(object3[1].toString());
										} else if (object3[0] != null && !object3[0].toString().equals("") && object3[0].toString().equalsIgnoreCase("Yes"))
										{
											pp = Integer.parseInt(object3[1].toString());
										}
									}
								}

								// SMS Mode
								// For Total Feedback Seek Counters
								// All = new
								// FeedbackHelper().getSMSData(type_report, "A",
								// connection);
								FeedbackHelper FH = new FeedbackHelper();
								FRP4Counter = new FeedbackReportPojo();
								FRP4Counter.setPt(pp + pn);
								FRP4Counter.setPp(pp);
								FRP4Counter.setPn(pn);

								FH.getSMSDataForTotalSeek(connection, FRP4Counter, type_report);

								int pre = 0;
								pre = FH.getRevertedSMSDetailsPrevious(true, false, type_report, connection, "IPD");
								int current = 0;
								current = FH.getRevertedSMSDetailsCurrent(true, false, type_report, connection, "IPD");
								NR = pre + current;
								pre = 0;
								current = 0;
								pre = FH.getRevertedSMSDetailsPrevious(false, true, type_report, connection, "IPD");
								current = FH.getRevertedSMSDetailsCurrent(false, true, type_report, connection, "IPD");
								// Neg=new
								// FeedbackHelper().getRevertedSMSDetailsPrevious(false,
								// true, type_report, connection);
								Neg = pre + current;
								FRP4Counter.setSnr(NR);
								FRP4Counter.setSn(Neg);
								NR = 0;
								Neg = 0;
								pre = 0;
								pre = FH.getRevertedSMSDetailsPrevious(true, false, type_report, connection, "OPD");
								current = 0;
								current = FH.getRevertedSMSDetailsCurrent(true, false, type_report, connection, "OPD");
								NR = pre + current;
								pre = 0;
								current = 0;
								pre = FH.getRevertedSMSDetailsPrevious(false, true, type_report, connection, "OPD");
								current = FH.getRevertedSMSDetailsCurrent(false, true, type_report, connection, "OPD");
								// Neg=new
								// FeedbackHelper().getRevertedSMSDetailsPrevious(false,
								// true, type_report, connection);
								Neg = pre + current;

								FRP4Counter.setSpos(Neg);
								FRP4Counter.setSneg(NR);

								/*
								 * FRP4Counter.setSt(All);
								 * FRP4Counter.setSnr(NR);
								 * FRP4Counter.setSn(Neg);
								 */
							}
							// getting the data for specific department
							else if (report_level.equals("1"))
							{
								deptComment = department;
								subject = "Patient Delight Score for " + deptComment + " as on " + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime().substring(0, 5) + "";
								currentdayCounter = FRH.getTicketCounters(report_level, type_report, true, subdept_dept, deptLevel, connection);
								CFCounter = FRH.getTicketCounters(report_level, type_report, false, subdept_dept, deptLevel, connection);
								currentDayResolvedData = FRH.getTicketDataforReport(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, connection);
								currentDayPendingData = FRH.getTicketDataforReport(report_level, type_report, true, "Pending", subdept_dept, deptLevel, connection);
								currentDaySnoozeData = FRH.getTicketDataforReport(report_level, type_report, true, "Snooze", subdept_dept, deptLevel, connection);
								currentDayHPData = FRH.getTicketDataforReport(report_level, type_report, true, "High Priority", subdept_dept, deptLevel, connection);
								currentDayIgData = FRH.getTicketDataforReport(report_level, type_report, true, "Ignore", subdept_dept, deptLevel, connection);
								CFData = FRH.getTicketDataforReport(report_level, type_report, false, "", subdept_dept, deptLevel, connection);
							}
							// Check Counter for Current day
							if (currentdayCounter != null && currentdayCounter.size() > 0)
							{
								for (Iterator iterator2 = currentdayCounter.iterator(); iterator2.hasNext();)
								{
									Object[] object2 = (Object[]) iterator2.next();
									if (object2[1].toString().equalsIgnoreCase("Pending"))
									{
										pc = Integer.parseInt(object2[0].toString());
									} else if (object2[1].toString().equalsIgnoreCase("Snooze"))
									{
										sc = Integer.parseInt(object2[0].toString());
									} else if (object2[1].toString().equalsIgnoreCase("High Priority"))
									{
										hc = Integer.parseInt(object2[0].toString());
									} else if (object2[1].toString().equalsIgnoreCase("Ignore"))
									{
										ic = Integer.parseInt(object2[0].toString());
									} else if (object2[1].toString().equalsIgnoreCase("Resolved"))
									{
										rc = Integer.parseInt(object2[0].toString());
									}
								}
								total = pc + sc + hc + ic + rc;
							}
							// Check Counter for Carry Forward
							if (CFCounter != null && CFCounter.size() > 0)
							{
								for (Iterator iterator2 = CFCounter.iterator(); iterator2.hasNext();)
								{
									Object[] object3 = (Object[]) iterator2.next();
									if (object3[1].toString().equalsIgnoreCase("Pending"))
									{
										cfpc = Integer.parseInt(object3[0].toString());
									} else if (object3[1].toString().equalsIgnoreCase("Snooze"))
									{
										cfsc = Integer.parseInt(object3[0].toString());
									} else if (object3[1].toString().equalsIgnoreCase("High Priority"))
									{
										cfhc = Integer.parseInt(object3[0].toString());
									}
								}
								cftotal = cfpc + cfhc;
							}
							totalSnooze = sc + cfsc;
						}

						String report_sms = "";
						if (report_level != null && !report_level.equals("") && report_level.equals("1"))
						{
							report_sms = "Dear " + empname + ", For " + deptComment + " Ticket Status as on " + DateUtil.getCurrentDateIndianFormat() + ": Pending: " + pc + ", C/F Pending: " + cftotal + ", Snooze: " + totalSnooze + ", Ignore: " + ic + ", Resolved: " + rc + ".";
						} else if (report_level != null && !report_level.equals("") && report_level.equals("2"))
						{
							report_sms = "Dear " + empname + ", For " + deptComment + " Ticket Status as on " + DateUtil.getCurrentDateIndianFormat() + ": Pending: " + cd_Pending + ", C/F Pending: " + cfp_Total + ", Snooze: " + total_snooze + ", Ignore: " + ic + ", Resolved: " + CDR_Total + ".";
						}

						if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
						{
							CH.addMessage(empname, department, mobno, report_sms, subject, "Pending", "0", "FM", connection);
						}
						String filepath = null;
						if ((currentDayResolvedData != null && currentDayResolvedData.size() > 0) || (currentDayPendingData != null && currentDayPendingData.size() > 0) || (currentDaySnoozeData != null && currentDaySnoozeData.size() > 0) || (currentDayHPData != null && currentDayHPData.size() > 0) || (currentDayIgData != null && currentDayIgData.size() > 0) || (CFData != null && CFData.size() > 0))
						{
							// System.out.println("Inside If for write Excel File   ");
							filepath = new WriteFeedbackData().writeDataInExcel(pc, hc, sc, ic, rc, total, cfpc, cfsc, cfhc, cftotal, totalSnooze, report_level, FRPList, deptComment, type_report, FRP4Counter, empname, currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, CFData);
							// System.out.println("Generated File path   >>>>>>>>>   "+filepath);
							String mailtext = FRH.getConfigMailForReport(pc, hc, sc, ic, rc, total, cfpc, cfsc, cfhc, cftotal, totalSnooze, report_level, FRPList, deptComment, type_report, FRP4Counter, empname, currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, CFData, IPAddress, cfp_Total, cd_Total, cd_Pending, total_snooze, CDR_Total);
							CH.addMail(empname, department, emailid, subject, mailtext, "Report", "Pending", "0", filepath, "FM", connection);
						}

					}

				}
			}
			Runtime rt = Runtime.getRuntime();
			rt.gc();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
