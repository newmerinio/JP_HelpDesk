package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
//import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.cps.corporate.EventPlanPojo;

public class ActivityServiceFileHelper
{

	@SuppressWarnings("unchecked")
	public void checkDeadline(SessionFactory connection)
	{
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			CommunicationHelper cmnHelper = new CommunicationHelper();
			String id=null,schedule_for=null,configure_for=null,deadlinedate=null,deadlinetime,rem1=null,rem2=null,rem3=null,startfrom=null,duetill=null,emp=null;
			//String escL1 = null, escL2 = null, escL3 = null, escL4 = null, escL1Duration = null, escL2Duration = null, escL3Duration = null, escL4Duration = null, esc = null,currentEscLevel=null;
			String query="select scheduleFor,configure_for,deadline_date,deadline_time,startFrom,dueTill,emp,reminder1,reminder2,reminder3,id,escalation,l2,tat2,l3,tat3,l4,tat4,l5,tat5,esc_level from configure_schedule ";
			List data =coi.executeAllSelectQuery(query, connection);
			if(data!=null && data.size()>0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null)
					{
						schedule_for = object[0].toString();
					}
					else
					{
						schedule_for ="NA";
					}
					if(object[1]!=null)
					{
						configure_for = object[1].toString();
					}
					else
					{
						configure_for ="NA";
					}
					if(object[2]!=null)
					{
						deadlinedate = object[2].toString();
					}
					else
					{
						deadlinedate ="NA";
					}
					if(object[3]!=null)
					{
						deadlinetime = object[3].toString();
					}
					else
					{
						deadlinetime ="NA";
					}
					if(object[7]!=null)
					{
						rem1 = object[7].toString();
					}
					else
					{
						rem1 ="NA";
					}
					if(object[8]!=null)
					{
						rem2 = object[8].toString();
					}
					else
					{
						rem2 ="NA";
					}
					if(object[9]!=null)
					{
						rem3 = object[9].toString();
					}
					else
					{
						rem3 ="NA";
					}
					if(object[4]!=null)
					{
						startfrom = object[4].toString();
					}
					else
					{
						startfrom ="NA";
					}
					if(object[5]!=null)
					{
						duetill = object[5].toString();
					}
					else
					{
						duetill ="NA";
					}
					if(object[6]!=null)
					{
						emp = object[6].toString();
					}
					else
					{
						emp ="NA";
					}
					if(object[10]!=null)
					{
						id = object[10].toString();
					}
					else
					{
						id ="NA";
					}
					/*if(object[11]!=null)
					{
						esc = object[11].toString();
					}
					else
					{
						esc ="NA";
					}
					if(object[12]!=null)
					{
						escL1 = object[12].toString();
					}
					else
					{
						escL1 ="NA";
					}
					if(object[13]!=null)
					{
						escL1Duration = object[13].toString();
					}
					else
					{
						escL1Duration ="NA";
					}
					if(object[14]!=null)
					{
						escL2 = object[14].toString();
					}
					else
					{
						escL2 ="NA";
					}
					if(object[15]!=null)
					{
						escL2Duration = object[15].toString();
					}
					else
					{
						escL2Duration ="NA";
					}
					if(object[16]!=null)
					{
						escL3 = object[16].toString();
					}
					else
					{
						escL3 ="NA";
					}
					if(object[17]!=null)
					{
						escL3Duration = object[17].toString();
					}
					else
					{
						escL3Duration ="NA";
					}
					if(object[18]!=null)
					{
						escL4 = object[18].toString();
					}
					else
					{
						escL4 ="NA";
					}
					if(object[19]!=null)
					{
						escL4Duration = object[19].toString();
					}
					else
					{
						escL4Duration ="NA";
					}
					if(object[20]!=null)
					{
						currentEscLevel = object[20].toString();
					}
					else
					{
						currentEscLevel ="NA";
					}*/
					query= " SELECT ac.for_month,ac.rel_sub_type,ac.activity_for,at.act_name,ac.sch_from,ac.sch_to,ac.comments,ac.activity_status " +
							" ,ac.activity_flag,dr.dsr_flag FROM activity_schedule_plan as ac LEFT JOIN activity_schedule_dsr_fill " +
							" as dr on dr.activityId=ac.id  LEFT JOIN patientcrm_status_data as escData on escData.id=ac.activity_type " +
							"LEFT JOIN activity_type as at on at.id=escData.status WHERE ac.schedule_contact_id IN ( SELECT id FROM manage_contact "+
							" WHERE emp_id IN (select emp_id from manage_contact where id='"+emp+"') AND module_name='WFPM') " +
							" AND ac.for_month LIKE '%"+ DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"%' ORDER BY ac.for_month ";
					List dataList = coi.executeAllSelectQuery(query, connection);
					List<ActivityPojo> ab= setFieldsIntoPojo(dataList,connection);
					
					query=" SELECT ac.for_month,ac.eplan_title,ac.eplan_address,st.rel_subtype,ac.sch_from,ac.sch_to, " +
							" ac.comments,ac.event_status,ac.eplan_flag,ac.dsr_flag FROM event_plan as ac  " +
							" LEFT JOIN relationship_sub_type as st on st.id=ac.rel_sub_type  WHERE ac.eplan_owner IN ( SELECT id FROM manage_contact " +
							"  WHERE emp_id IN (select emp_id from manage_contact where id='"+emp+"') AND module_name='WFPM') "+
					        " AND ac.for_month LIKE '%"+ DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"%' ORDER BY ac.for_month ";
					List dataList1 = coi.executeAllSelectQuery(query, connection);
					List<EventPlanPojo> event= setFieldsIntoPojoEvent(dataList1);
					
					String query2 = "SELECT emp.mobile_no,emp.email_id,emp.emp_name FROM manage_contact AS cc INNER JOIN primary_contact AS emp ON emp.id=cc.emp_id WHERE cc.work_status=0 AND cc.id IN(" + emp + ")";
					List data1 = new createTable().executeAllSelectQuery(query2, connection);
					String emp_name=null,emp_no=null,emp_mail=null;
					if (data1 != null && data1.size() > 0)
					{
						for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
						{
							Object object2[] = (Object[]) iterator2.next();
							emp_name = object2[2].toString();
							emp_no = object2[0].toString();
							emp_mail = object2[1].toString();
						}
					}
					String smsText=null,mailText = null;
					if(rem1.equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
					{
						if(configure_for.equalsIgnoreCase("Activity Plan"))
						{
							if(dataList.size()==0 && dataList==null)
							{
								smsText = "Reminder 1: Dear "+emp_name+", Kindly plan your activity ";
							}
						}
						else if(configure_for.equalsIgnoreCase("Event Plan"))
						{
							if(dataList1.size()==0 && dataList1==null)
							{
								smsText = "Reminder 1: Dear "+emp_name+", Kindly plan your event ";
							}
						}
						else if(configure_for.equalsIgnoreCase("Activity Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 1: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for Approval  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						else if(configure_for.equalsIgnoreCase("Event Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 1: Dear "+emp_name+", Kindly Send your "+dataList.size() +" event for Approval  ";
								mailText = generateMailEvent(emp_name,event);
							}
						}
						else if(configure_for.equalsIgnoreCase("DCR Submission"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 1: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Submission  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						else if(configure_for.equalsIgnoreCase("DCR Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 1: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Approval  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						if(smsText!=null)
						{
							cmnHelper.addMessage("", "", emp_no, smsText, "", "Pending", "0", "WFPM", connection);
						}
						if(mailText!=null)
						{
							cmnHelper.addMail("", "", emp_mail, "Reminder 1 Alert: ", mailText, "", "Pending", "0", "", "WFPM", connection);
						}
					}
					if(rem2.equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
					{
						if(configure_for.equalsIgnoreCase("Activity Plan"))
						{
							if(dataList.size()==0 && dataList==null)
							{
								smsText = "Reminder 2: Dear "+emp_name+", Kindly plan your activity ";
							}
						}
						else if(configure_for.equalsIgnoreCase("Event Plan"))
						{
							if(dataList1.size()==0 && dataList1==null)
							{
								smsText = "Reminder 2: Dear "+emp_name+", Kindly plan your event ";
							}
						}
						else if(configure_for.equalsIgnoreCase("Activity Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 2: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for Approval  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						else if(configure_for.equalsIgnoreCase("Event Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 2: Dear "+emp_name+", Kindly Send your "+dataList.size() +" event for Approval  ";
								mailText = generateMailEvent(emp_name,event);
							}
						}
						else if(configure_for.equalsIgnoreCase("DCR Submission"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 2: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Submission  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						else if(configure_for.equalsIgnoreCase("DCR Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 2: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Approval  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						if(smsText!=null)
						{
							cmnHelper.addMessage("", "", emp_no, smsText, "", "Pending", "0", "WFPM", connection);
						}
						if(mailText!=null)
						{
							cmnHelper.addMail("", "", emp_mail, "Reminder 2 Alert:", mailText, "", "Pending", "0", "", "WFPM", connection);
						}
					}
					if(rem3.equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()))
					{
						if(configure_for.equalsIgnoreCase("Activity Plan"))
						{
							if(dataList.size()==0 && dataList==null)
							{
								smsText = "Reminder 3: Dear "+emp_name+", Kindly plan your activity ";
							}
						}
						else if(configure_for.equalsIgnoreCase("Event Plan"))
						{
							if(dataList1.size()==0 && dataList1==null)
							{
								smsText = "Reminder 3: Dear "+emp_name+", Kindly plan your event ";
							}
						}
						else if(configure_for.equalsIgnoreCase("Activity Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 3: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for Approval  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						else if(configure_for.equalsIgnoreCase("Event Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 3: Dear "+emp_name+", Kindly Send your "+dataList.size() +" event for Approval  ";
								mailText = generateMailEvent(emp_name,event);
							}
						}
						else if(configure_for.equalsIgnoreCase("DCR Submission"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 3: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Submission  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						else if(configure_for.equalsIgnoreCase("DCR Approval"))
						{
							if( dataList!=null && dataList.size()>0 )
							{
								smsText = "Reminder 3: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Approval  ";
								mailText = generateMail(emp_name,ab);
							}
						}
						if(smsText!=null)
						{
							cmnHelper.addMessage("", "", emp_no, smsText, "", "Pending", "0", "WFPM", connection);
						}
						if(mailText!=null)
						{
							cmnHelper.addMail("", "", emp_mail, "Reminder 3 Alert:", mailText, "", "Pending", "0", "", "WFPM", connection);
						}
					}
					if(schedule_for.equalsIgnoreCase("Date"))
					{
						if(deadlinedate.equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()) && deadlinetime.equalsIgnoreCase(DateUtil.getCurrentTimeHourMin()))
						{
							if(configure_for.equalsIgnoreCase("Activity Plan"))
							{
								if(dataList.size()==0 && dataList==null)
								{
									smsText = "Deadline Alert: Dear "+emp_name+", Kindly plan your activity ";
								}
							}
							else if(configure_for.equalsIgnoreCase("Event Plan"))
							{
								if(dataList1.size()==0 && dataList1==null)
								{
									smsText = "Deadline Alert: Dear "+emp_name+", Kindly plan your event ";
								}
							}
							else if(configure_for.equalsIgnoreCase("Activity Approval"))
							{
								if( dataList!=null && dataList.size()>0 )
								{
									smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for Approval  ";
									mailText = generateMail(emp_name,ab);
								}
							}
							else if(configure_for.equalsIgnoreCase("Event Approval"))
							{
								if( dataList!=null && dataList.size()>0 )
								{
									smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" event for Approval  ";
									mailText = generateMailEvent(emp_name,event);
								}
							}
							else if(configure_for.equalsIgnoreCase("DCR Submission"))
							{
								if( dataList!=null && dataList.size()>0 )
								{
									smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Submission  ";
									mailText = generateMail(emp_name,ab);
								}
							}
							else if(configure_for.equalsIgnoreCase("DCR Approval"))
							{
								if( dataList!=null && dataList.size()>0 )
								{
									smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Approval  ";
									mailText = generateMail(emp_name,ab);
								}
							}
							StringBuilder updatequery = new StringBuilder();
							updatequery.append(" update configure_schedule set deadline_date='"+DateUtil.getNewDate("month", 1,deadlinedate)+"' , reminder1 ='"+DateUtil.getNewDate("month", 1,rem1)+"'  ");
							updatequery.append(" , reminder2 ='"+DateUtil.getNewDate("month", 1,rem2)+"' , reminder3 ='"+DateUtil.getNewDate("month", 1,rem3)+"'  ");
							updatequery.append(" WHERE  id="+id+" ");
							boolean status = coi.updateTableColomn(connection, updatequery);
							if(status)
							{
								if(smsText!=null)
								{
									cmnHelper.addMessage("", "", emp_no, smsText, "", "Pending", "0", "WFPM", connection);
								}
								if(mailText!=null)
								{
									cmnHelper.addMail("", "", emp_mail, "Deadline Alert: ", mailText, "", "Pending", "0", "", "WFPM", connection);
								}
							}
						}
					}
					else if(schedule_for.equalsIgnoreCase("Frequency"))
					{
						if(startfrom!="NA" && duetill!="NA")
						{
							String date1 = DateUtil.getNewDate("day", Integer.parseInt(startfrom), DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"30");
							String date2 = DateUtil.getNewDate("day", Integer.parseInt(duetill), DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"30");
							if(DateUtil.comparetwoDates(date1, date2))
							{
								if(configure_for.equalsIgnoreCase("Activity Plan"))
								{
									if(dataList.size()==0 && dataList==null)
									{
										smsText = "Deadline Alert: Dear "+emp_name+", Kindly plan your activity ";
									}
								}
								else if(configure_for.equalsIgnoreCase("Event Plan"))
								{
									if(dataList1.size()==0 && dataList1==null)
									{
										smsText = "Deadline Alert: Dear "+emp_name+", Kindly plan your event ";
									}
								}
								else if(configure_for.equalsIgnoreCase("Activity Approval"))
								{
									if( dataList!=null && dataList.size()>0 )
									{
										smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for Approval  ";
										mailText = generateMail(emp_name,ab);
									}
								}
								else if(configure_for.equalsIgnoreCase("Event Approval"))
								{
									if( dataList!=null && dataList.size()>0 )
									{
										smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" event for Approval  ";
										mailText = generateMailEvent(emp_name,event);
									}
								}
								else if(configure_for.equalsIgnoreCase("DCR Submission"))
								{
									if( dataList!=null && dataList.size()>0 )
									{
										smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Submission  ";
										mailText = generateMail(emp_name,ab);
									}
								}
								else if(configure_for.equalsIgnoreCase("DCR Approval"))
								{
									if( dataList!=null && dataList.size()>0 )
									{
										smsText = "Deadline Alert: Dear "+emp_name+", Kindly Send your "+dataList.size() +" activities for DCR Approval  ";
										mailText = generateMail(emp_name,ab);
									}
								}
								if(smsText!=null)
								{
									cmnHelper.addMessage("", "", emp_no, smsText, "", "Pending", "0", "WFPM", connection);
								}
								if(mailText!=null)
								{
									cmnHelper.addMail("", "", emp_mail, "Deadline Alert;", mailText, "", "Pending", "0", "", "WFPM", connection);
								}
							}
						}
					}
					/*if(esc.equalsIgnoreCase("Yes"))
					{
						if(configure_for.equalsIgnoreCase("Activity Plan"))
						{
							if(dataList.size()==0 && dataList==null)
							{
								int nextEscLevel = Integer.valueOf(currentEscLevel);
								String nextEscDuration = "00:00";
								String nextEscDateTime = null;
								boolean isNextEsc = false;
								if (nextEscLevel == 1 && !escL1Duration.equalsIgnoreCase("00:00"))
								{
									nextEscDuration = escL2Duration;
									isNextEsc = true;
								} else if (nextEscLevel == 2 && !escL2Duration.equalsIgnoreCase("00:00"))
								{
									nextEscDuration = escL3Duration;
									isNextEsc = true;
								} else if (nextEscLevel == 3 && !escL3Duration.equalsIgnoreCase("00:00"))
								{
									nextEscDuration = escL4Duration;
									isNextEsc = true;
								} else if (nextEscLevel == 4)
								{
									nextEscDuration = "00:00";
									isNextEsc = true;
								}
								nextEscLevel++;

								if (nextEscLevel < 6)
								{
									WorkingHourAction WHA = new WorkingHourAction();
									String date = DateUtil.getCurrentDateUSFormat();
									String time = DateUtil.getCurrentTimeHourMin();
									List<String> dateTime = WHA.getAddressingEscTime(connection, coi, nextEscDuration, "00:00", "Y", date, time, "WFPM");
									if (dateTime != null && dateTime.size() > 0)
									{
										nextEscDateTime = dateTime.get(0) + "#" + dateTime.get(1);
									}
								}
								
								
								if(schedule_for.equalsIgnoreCase("Date"))
								{
									if(deadlinedate.equalsIgnoreCase(DateUtil.getCurrentDateUSFormat()) && deadlinetime.equalsIgnoreCase(DateUtil.getCurrentTimeHourMin()))
									{
										
									}
								}
								else if(schedule_for.equalsIgnoreCase("Frequency"))
								{
									if(startfrom!="NA" && duetill!="NA")
									{
										String date1 = DateUtil.getNewDate("day", Integer.parseInt(startfrom), DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"30");
										String date2 = DateUtil.getNewDate("day", Integer.parseInt(duetill), DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"30");
										if(DateUtil.comparetwoDates(date1, date2))
										{
											
										}
									}
								}
								//smsText = "Deadline Alert: Dear "+emp_name+", Kindly plan your activity ";
							}
						}
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator2 = data1.iterator(); iterator2.hasNext();)
							{
								Object[] object2 = (Object[]) iterator2.next();
								
							}
						}
					}*/
				}
			}
		}
		catch (Exception e)
		{
		     e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private List<EventPlanPojo> setFieldsIntoPojoEvent(List dataList1)
	{
		List<EventPlanPojo> data=new ArrayList<EventPlanPojo>();
		EventPlanPojo ap=null;
		try
		{
			if(dataList1!=null && dataList1.size()>0)
			{
				for (Iterator iterator = dataList1.iterator(); iterator.hasNext();)
				{
					ap = new EventPlanPojo();
					Object[] object = (Object[]) iterator.next();
					if(object[1]!=null)
					{
						ap.setTitle(object[1].toString());
					}
					if(object[0]!=null )
					{
						ap.setFor_month(DateUtil.convertDateToIndianFormat(object[0].toString()));
					}
					if(object[2]!=null )
					{
						ap.setAddress(object[2].toString());
					}
					
					if(object[4]!=null && object[5]!=null)
					{
						ap.setSch_from(object[4].toString()+" to "+object[5].toString());
					}
					if(object[3]!=null )
					{
						ap.setRel_sub_type(object[3].toString());
					}
					if(object[6]!=null )
					{
						ap.setComments(object[6].toString());
					}
					data.add(ap);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	
	}

	private String generateMailEvent(String empName, List<EventPlanPojo> ab)
	{
		StringBuilder mailtext=new StringBuilder();
		try
		{
			mailtext.append("<br><b>Dear " + empName + ",</b><br>");
			mailtext.append(" Following are the Activities: ");
			mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Day After Tomorrow's Review</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For Date</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Event Title</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Address</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sub Type</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Schedule Timmings</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Comment</strong></td> </tr>");
        	for(EventPlanPojo FBP:ab)
         	{
     		  mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFor_month()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTitle()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getAddress()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRel_sub_type()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSch_from()+" </td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComments()+" </td></tr>");
         	}
     		mailtext.append("</tbody></table>");
			mailtext.append("<BR>");
			mailtext.append("<BR>");
			mailtext.append("<br>");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailtext.toString();
	}
	@SuppressWarnings("unchecked")
	public String fetchActivityFor(String tablename, String id,SessionFactory connection)
	{
		String activityFor = null;
		try
		{
			String query = null;
			if (tablename != null && tablename.equalsIgnoreCase("referral_institutional_data"))
			{
				query = "SELECT org_name FROM referral_institutional_data WHERE id=" + id;
			}
			else
			{
				query = "SELECT emp.emp_name FROM referral_contact_data as con LEFT JOIN primary_contact as emp on emp.id=con.map_emp_id WHERE con.id=" + id;
			}
			if (query != null)
			{
				List list = new createTable().executeAllSelectQuery(query, connection);
				if (list != null && !list.isEmpty())
				{
					activityFor = list.get(0).toString();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return activityFor;
	}
	@SuppressWarnings("unchecked")
	private List<ActivityPojo> setFieldsIntoPojo(List dataList, SessionFactory connection)
	{
		List<ActivityPojo> data=new ArrayList<ActivityPojo>();
		ActivityPojo ap=null;
		try
		{
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					ap = new ActivityPojo();
					Object[] object = (Object[]) iterator.next();
					if(object[1]!=null && object[2]!=null)
					{
						ap.setActivity_for(fetchActivityFor(object[1].toString(), object[2].toString(),connection));
					}
					else
					{
						ap.setActivity_for("NA");
					}
					if(object[0]!=null )
					{
						ap.setFor_month(DateUtil.convertDateToIndianFormat(object[0].toString()));
					}
					else
					{
						ap.setFor_month("NA");
					}
					if(object[3]!=null )
					{
						ap.setActivity_type(object[3].toString());
					}
					else
					{
						ap.setActivity_type("NA");
					}
					if(object[4]!=null && object[5]!=null)
					{
						ap.setSchedule_time(object[4].toString()+" to "+object[5].toString());
					}
					else
					{
						ap.setSchedule_time("NA");
					}
					if(object[6]!=null )
					{
						ap.setComment(object[6].toString());
					}
					else
					{
						ap.setComment("NA");
					}
					data.add(ap);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	private String generateMail(String empName, List<ActivityPojo> dataList)
	{
		StringBuilder mailtext=new StringBuilder();
		try
		{
			mailtext.append("<br><b>Dear " + empName + ",</b><br>");
			mailtext.append("<BR>");
			mailtext.append(" Following are the Activities: ");
			mailtext.append("<BR>");
			//mailtext.append("<table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Day After Tomorrow's Review</b></td></tr></tbody></table>");
        	mailtext.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        	mailtext.append("<tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For Date</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Activity For</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Activity Type</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Schedule Timmings</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Comment</strong></td> </tr>");
        	
        	for(ActivityPojo FBP:dataList)
         	{
     		  mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFor_month()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getActivity_for()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getActivity_type()+"</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSchedule_time()+"</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getComment()+" </td></tr>");
         	}
     		mailtext.append("</tbody></table>");
			mailtext.append("<BR>");
			mailtext.append("<BR>");
			mailtext.append("<br>");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailtext.toString();
	}
}
