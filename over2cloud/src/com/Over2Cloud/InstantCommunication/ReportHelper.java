package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.compliance.serviceFiles.ComplianceServiceHelper;
import com.Over2Cloud.compliance.serviceFiles.DailyCompReportExcelWrite4Attach;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.feedback.FeedbackReportHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class ReportHelper {
	
	@SuppressWarnings("unchecked")
	public void ReportGeneration(SessionFactory connection)
	 {
    	//Local variable defined
    	@SuppressWarnings("unused")
		String empname=null,mobno=null,emailid=null,subject=null,report_level=null,subdept_dept=null, deptLevel=null,sms_status=null,mail_status=null,type_report=null,id=null,deptname=null,module=null,department=null;
    	// Counts for Current Day
		int pc=0,hc=0,sc=0,ic=0,rc=0,total=0;
		int rAc=0,cfrAc=0;
		// Counts for Carry Forward
		@SuppressWarnings("unused")
		int cfpc=0,cfhc=0,cfsc=0,cfic=0,cfrc=0,cftotal=0;
    	List subDeptList=null;
    	
    	try {
			  List data4report = new HelpdeskUniversalHelper().getReportToData(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), connection);
			  if (data4report!=null && data4report.size()>0) {
				  for (Iterator iterator = data4report.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					
					if (object[0]!=null) {
						empname = object[0].toString();
					}
					else {
						empname = "NA";
					}
					
					if (object[1]!=null) {
						mobno = object[1].toString();
					}
					else {
						mobno = "NA";
					}
					
					if (object[2]!=null) {
						emailid = object[2].toString();
					}
					else {
						emailid = "NA";
					}
					
					if (object[3]!=null) {
						subject = object[3].toString();
					}
					else {
						subject = "NA";
					}
					
					if (object[4]!=null) {
						report_level = object[4].toString();
					}
					
					
					if (object[5]!=null) {
						subdept_dept = object[5].toString();
					}
					
					
					if (object[6]!=null) {
						deptLevel = object[6].toString();
					}
					
					
					if (object[7]!=null) {
						sms_status = object[7].toString();
					}
					
					
					if (object[8]!=null) {
						mail_status = object[8].toString();
					}
					
					if (object[9]!=null) {
						type_report = object[9].toString();
					}
					
					if (object[10]!=null) {
						id = object[10].toString();
					}
					
					if (object[11]!=null) {
						module = object[11].toString();
					}
					
					if (object[12]!=null && !object[12].toString().equals("")) {
						department = object[12].toString();
					}
					else {
						department = "NA";
					}
					
					String newDate = null;
					if (!type_report.equals("") && !type_report.equals("NA")) {
						newDate = new HelpdeskUniversalHelper().getNewDate(type_report);
					}
					String updateQry="update report_configuration set report_date='"+newDate+"' where id='"+id+"'";
					boolean updateReport = new HelpdeskUniversalHelper().updateData(updateQry, connection);
					if (module!=null && !module.equals("") && module.equalsIgnoreCase("HDM") && updateReport) {
						List currentdayCounter = new ArrayList();
						List CFCounter = new ArrayList();
						List<FeedbackPojo> currentDayResolvedData = null;
						List<FeedbackPojo> currentDayPendingData = null;
						List<FeedbackPojo> currentDaySnoozeData = null;
						List<FeedbackPojo> currentDayHPData =null;
						List<FeedbackPojo> currentDayIgData = null;
						List<FeedbackPojo> CFData =null;
						
						
						//get Data when Dept Level is 2 in Organization
						if (deptLevel.equals("2")) {
						    // getting the data for All the departments
						    if (report_level.equals("2")) {
							    currentdayCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, true, subdept_dept, deptLevel, connection);
							    CFCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, false, subdept_dept, deptLevel, connection);
							    currentDayResolvedData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Resolved", subdept_dept, deptLevel, connection);
							    currentDayPendingData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Pending", subdept_dept, deptLevel, connection);
							    currentDaySnoozeData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Snooze", subdept_dept, deptLevel, connection);
							    currentDayHPData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "High Priority", subdept_dept, deptLevel, connection);
							    currentDayIgData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Ignore", subdept_dept, deptLevel, connection);
							    CFData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, false, "", subdept_dept, deptLevel, connection);
						    }
						    // getting the data for specific department
						    else if (report_level.equals("1")) {
							    subDeptList = new HelpdeskUniversalHelper().getSubDeptList(subdept_dept, connection);
							    if (subDeptList!=null) {
							    	currentdayCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, true, subdept_dept, deptLevel, connection);
								    CFCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, false, subdept_dept, deptLevel, connection);
								    currentDayResolvedData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Resolved", subdept_dept, deptLevel, connection);
								    currentDayPendingData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Pending", subdept_dept, deptLevel, connection);
								    currentDaySnoozeData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Snooze", subdept_dept, deptLevel, connection);
								    currentDayHPData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "High Priority", subdept_dept, deptLevel, connection);
								    currentDayIgData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Ignore", subdept_dept, deptLevel, connection);
									CFData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, false, "", subdept_dept, deptLevel, connection);
							    //}
						    }
						  }
						}
						//get Data when Dept Level is 1 in Organization
						else {
							// getting the data for All the departments
							if (report_level.equals("2")) {
								currentdayCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, true, subdept_dept, deptLevel, connection);
								CFCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, false, subdept_dept, deptLevel, connection);
								currentDayResolvedData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Resolved", subdept_dept, deptLevel, connection);
								currentDayPendingData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Pending", subdept_dept, deptLevel, connection);
								currentDaySnoozeData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Snooze", subdept_dept, deptLevel, connection);
								currentDayHPData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "High Priority", subdept_dept, deptLevel, connection);
								currentDayIgData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Ignore", subdept_dept, deptLevel, connection);
							    CFData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, false, "", subdept_dept, deptLevel, connection);
							}
							// getting the data for specific department
							else if (report_level.equals("1")) {
								subDeptList.add(subdept_dept);
								if (subDeptList!=null) {
									currentdayCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, true, subdept_dept, deptLevel, connection);
								    CFCounter = new HelpdeskUniversalHelper().getTicketCounters(report_level,type_report, false, subdept_dept, deptLevel, connection);
								    currentDayResolvedData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Resolved", subdept_dept, deptLevel, connection);
								    currentDayPendingData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Pending", subdept_dept, deptLevel, connection);
								    currentDaySnoozeData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Snooze", subdept_dept, deptLevel, connection);
								    currentDayHPData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "High Priority", subdept_dept, deptLevel, connection);
								    currentDayIgData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, true, "Ignore", subdept_dept, deptLevel, connection);
									CFData = new HelpdeskUniversalHelper().getTicketData(report_level,type_report, false, "", subdept_dept, deptLevel, connection);
							   // }
							}
						  }
						}
						
						// Check Counter for Current day
						if (currentdayCounter!=null && currentdayCounter.size()>0) {
							for (Iterator iterator2 = currentdayCounter.iterator(); iterator2
									.hasNext();) {
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[1].toString().equalsIgnoreCase("Pending")) {
									pc=Integer.parseInt(object2[0].toString());
								}
								else if (object2[1].toString().equalsIgnoreCase("Snooze")) {
									sc=Integer.parseInt(object2[0].toString());
								}
								else if (object2[1].toString().equalsIgnoreCase("High Priority")) {
									hc=Integer.parseInt(object2[0].toString());
								}
								else if (object2[1].toString().equalsIgnoreCase("Ignore")) {
									ic=Integer.parseInt(object2[0].toString());
								}
								else if (object2[1].toString().equalsIgnoreCase("Resolved")) {
									rc=Integer.parseInt(object2[0].toString());
								}
							}
							total=pc+sc+hc+ic+rc;
						}
						// Check Counter for Carry Forward
						if (CFCounter!=null && CFCounter.size()>0) {
							for (Iterator iterator2 = CFCounter.iterator(); iterator2
									.hasNext();) {
								Object[] object3 = (Object[]) iterator2.next();
								if (object3[1].toString().equalsIgnoreCase("Pending")) {
									cfpc=Integer.parseInt(object3[0].toString());
								}
								else if (object3[1].toString().equalsIgnoreCase("Snooze")) {
									cfsc=Integer.parseInt(object3[0].toString());
								}
								else if (object3[1].toString().equalsIgnoreCase("High Priority")) {
									cfhc=Integer.parseInt(object3[0].toString());
								}
							}
							cftotal=cfpc+cfsc+cfhc;
						}
						 String report_sms = "Dear "+empname+", For All Ticket Status as on "+DateUtil.getCurrentDateIndianFormat()+": Pending: "+pc+", C/F Pending: "+cftotal+", Snooze: "+sc+", Ignore: "+ic+", Resolved: "+rc+".";
						 if (mobno!=null && mobno!="" && mobno!="NA" && mobno.length()==10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7"))) {
							@SuppressWarnings("unused")
							boolean putsmsstatus = new CommunicationHelper().addMessage(empname,department,mobno, report_sms, subject,"Pending", "0","HDM",connection);
						 }
						 
						 String filepath=new DailyReportExcelWrite4Attach().writeDataInExcel(currentdayCounter,CFCounter,currentDayResolvedData,currentDayPendingData,currentDaySnoozeData,currentDayHPData,currentDayIgData,CFData,deptLevel,type_report);
						 String mailtext = new HelpdeskUniversalHelper().getConfigMailForReport(pc, hc, sc, ic, rc,total,cfpc,cfsc,cfhc,cftotal,empname, currentDayResolvedData,currentDayPendingData,currentDaySnoozeData,currentDayHPData,currentDayIgData,CFData);
						 new CommunicationHelper().addMail(empname,department,emailid, subject, mailtext, "Report","Pending", "0",filepath,"HDM",connection);
				   }
					else if (module!=null && !module.equals("") && module.equalsIgnoreCase("FM") && updateReport)
					{
						List currentdayCounter = new ArrayList();
						List CFCounter = new ArrayList();
						List<FeedbackPojo> currentDayResolvedData = new ArrayList<FeedbackPojo>();
						List<FeedbackPojo> currentDayPendingData = new ArrayList<FeedbackPojo>();
						List<FeedbackPojo> currentDayRAsgnData  = new ArrayList<FeedbackPojo>() ;
						List<FeedbackPojo> CFData =new ArrayList<FeedbackPojo>();
						if (deptLevel.equals("2")) 
						{
							    if (report_level.equals("2")) 
							    {
								    currentdayCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, true, subdept_dept, deptLevel, connection);
								    CFCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, false, subdept_dept, deptLevel, connection);
								    currentDayResolvedData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Resolved", subdept_dept, deptLevel, connection);
								    currentDayPendingData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Pending", subdept_dept, deptLevel, connection);
								    currentDayRAsgnData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Re-Assign", subdept_dept, deptLevel, connection);
								    CFData = new FeedbackUniversalHelper().getTicketData(report_level, false, "", subdept_dept, deptLevel, connection);
							    }
							    // getting the data for specific department
							    else if (report_level.equals("1")) 
							    {
								    String deptId=new FeedbackUniversalHelper().getDeptId(subdept_dept,connection);
								    if (deptId!=null)
								    {
								    	currentdayCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, true, deptId, deptLevel, connection);
									    CFCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, false, deptId, deptLevel, connection);
									    currentDayResolvedData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Resolved", deptId, deptLevel, connection);
									    currentDayPendingData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Pending", deptId, deptLevel, connection);
									    currentDayRAsgnData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Re-Assign", deptId, deptLevel, connection);
									    CFData = new FeedbackUniversalHelper().getTicketData(report_level, false, "", deptId, deptLevel, connection);
							      }
							   }
						 }
						//get Data when Dept Level is 1 in Organization
						else 
						  {
								// getting the data for All the departments
								if (report_level.equals("2")) 
								{
										currentdayCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, true, subdept_dept, deptLevel, connection);
										CFCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, false, subdept_dept, deptLevel, connection);
										currentDayResolvedData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Resolved", subdept_dept, deptLevel, connection);
										currentDayPendingData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Pending", subdept_dept, deptLevel, connection);
										currentDayRAsgnData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Re-Assign", subdept_dept, deptLevel, connection);
										CFData = new FeedbackUniversalHelper().getTicketData(report_level, false, "", subdept_dept, deptLevel, connection);
								}
								// getting the data for specific department
								else if (report_level.equals("1"))
								{
										 String deptId=new FeedbackUniversalHelper().getDeptId(subdept_dept,connection);
										if (deptId!=null)
										{
											currentdayCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, true, deptId, deptLevel, connection);
										    CFCounter = new FeedbackUniversalHelper().getTicketCounters(report_level, false, deptId, deptLevel, connection);
										    currentDayResolvedData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Resolved", deptId, deptLevel, connection);
										    currentDayPendingData = new FeedbackUniversalHelper().getTicketData(report_level, true, "Pending", deptId, deptLevel, connection);
											CFData = new FeedbackUniversalHelper().getTicketData(report_level, false, "", deptId, deptLevel, connection);
										}
							}
						}
						// Check Counter for Current day
						if (currentdayCounter!=null && currentdayCounter.size()>0)
						{
							for (Iterator iterator2 = currentdayCounter.iterator(); iterator2.hasNext();)
							{
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[1].toString().equalsIgnoreCase("Pending"))
								{
									pc=Integer.parseInt(object2[0].toString());
								}
								else if (object2[1].toString().equalsIgnoreCase("Re-Assign"))
								{
									rAc=Integer.parseInt(object2[0].toString());
								}
								else if (object2[1].toString().equalsIgnoreCase("Resolved")) {
									rc=Integer.parseInt(object2[0].toString());
								}
							}
							total=pc+rAc+rc;
						}
						// Check Counter for Carry Forward
						if (CFCounter!=null && CFCounter.size()>0)
						{
							for (Iterator iterator2 = CFCounter.iterator(); iterator2.hasNext();)
							{
								Object[] object3 = (Object[]) iterator2.next();
								if (object3[1].toString().equalsIgnoreCase("Pending"))
								{
									cfpc=Integer.parseInt(object3[0].toString());
								}
								else if (object3[1].toString().equalsIgnoreCase("Re-Assign"))
								{
									cfrAc=Integer.parseInt(object3[0].toString());
								}
							}
							cftotal=cfpc+cfrAc;
						}
						
						if(report_level!=null && report_level.equalsIgnoreCase("2"))
						{
							department="All Departments";
						}
						
						 String report_sms = "Dear "+empname+", For "+department+" Ticket Status as on "+DateUtil.getCurrentDateIndianFormat()+": Pending: "+pc+", C/F Pending: "+cftotal+", Re-Assigned: "+rAc+".";
						 if (mobno!=null && mobno!="" && mobno!="NA" && mobno.length()==10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
						 {
							@SuppressWarnings("unused")
							boolean putsmsstatus = new CommunicationHelper().addMessage(empname,department,mobno, report_sms, subject,"Pending", "0","FM",connection);
						 }
						 String mailtext=null;
						 String filepath=null;
						 
						 if ((currentDayResolvedData!=null && currentDayResolvedData.size()>0) || (currentDayPendingData!=null && currentDayPendingData.size()>0) || (currentDayRAsgnData!=null && currentDayRAsgnData.size()>0)  || (CFData!=null && CFData.size()>0))
						 {
							 filepath=new DailyReportExcelWrite4Attach().writeDataInExcelForFeedback(currentdayCounter,CFCounter,currentDayResolvedData,currentDayPendingData,currentDayRAsgnData,CFData,deptLevel,type_report);
						 }
						
						 mailtext = new FeedbackUniversalHelper().getConfigMailForReportFeedback(pc,rc,rAc,total,cfpc,cfrAc,cftotal,empname, currentDayResolvedData,currentDayPendingData,currentDayRAsgnData,CFData,department);
						 new CommunicationHelper().addMail(empname,department,emailid, subject, mailtext, "Report","Pending", "0",filepath,"FM",connection);
					}
					else if (module!=null && !module.equals("") && module.equalsIgnoreCase("Feedback") && updateReport)
					{
						FeedbackReportHelper reportHelper=new FeedbackReportHelper();
						int totalFeed=0;
						int posFeed=0;
						int negFeed=0;
						
						posFeed=new FeedbackReportHelper().getPositiveDataCount(connection);
						negFeed=new FeedbackReportHelper().getNegativeDataCount(connection);
						totalFeed=posFeed+negFeed;
						
						List<CustomerPojo> feedList=new FeedbackReportHelper().getFeedbackData(connection,empname);
						
						//String report_sms = "Dear "+empname+", Total Feedback Received till "+DateUtil.getCurrentDateIndianFormat()+": Positive: "+posFeed+", Negative: "+negFeed+", Total: "+totalFeed+".";
						String report_sms = "Dear "+empname+", Total Feedback Received till: "+DateUtil.getCurrentDateIndianFormat()+", Positive: "+posFeed+", Negative: "+negFeed+", Total: "+totalFeed+".";
						if (mobno!=null && mobno!="" && mobno!="NA" && mobno.length()==10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
						 {
							@SuppressWarnings("unused")
							boolean putsmsstatus = new CommunicationHelper().addMessage(empname,department,mobno, report_sms, subject,"Pending", "0","FM",connection);
						 }
						String mailtext=reportHelper.getMailBodyForAdminFeedback(connection,empname,feedList,posFeed,negFeed,totalFeed);
						String filepath=null;
						new CommunicationHelper().addMail(empname,department,emailid, subject, mailtext, "Report","Pending", "0",filepath,"FM",connection);
					}
					else if (module!=null && !module.equals("") && module.equalsIgnoreCase("compliance") && updateReport)
					 {
						ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
						CommunicationHelper cmnHelper = new CommunicationHelper();
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						List validData = null;
						WorkingHourHelper WHH = new WorkingHourHelper();
						List<String> workingDayList = WHH.getDayDate(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), connection, cbt, "COMPL");
						String workingDay = workingDayList.get(1).toString();
						if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.getNewDate("day", -1, workingDay)))
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// DateUtil.getCurrentDateUSFormat(),
							// "equalCondition");
							validData = srvcHelper.getReportData(connection, report_level, type_report, null, DateUtil.getCurrentDateUSFormat(), "equalCondition");
						}
						else
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// workingDayList.get(1).toString(),
							// "betweenCondition");
							validData = srvcHelper.getReportData(connection, report_level, type_report, null, workingDayList.get(1).toString(), "betweenCondition");
						}
						// List
						// validData=srvcHelper.getReportData(connection,report_level,type_report,null);
						List<ComplianceDashboardBean> listForActionDataMail = new ArrayList<ComplianceDashboardBean>();
						List<ComplianceDashboardBean> listForPendingDataMail = new ArrayList<ComplianceDashboardBean>();

						if (validData != null && validData.size() > 0)
						{
							ComplianceDashboardBean obj = null;
							String reminderFor = null;
							for (Iterator iterator2 = validData.iterator(); iterator2.hasNext();)
							{
								obj = new ComplianceDashboardBean();
								Object[] objects = (Object[]) iterator2.next();

								if (objects[0] != null)
								{
									List lastCompDeatil = srvcHelper.getLastCompActionData(objects[0].toString(), connection);
									if (lastCompDeatil != null && lastCompDeatil.size() > 0)
									{
										for (Iterator iterator1 = lastCompDeatil.iterator(); iterator1.hasNext();)
										{
											Object[] object1 = (Object[]) iterator1.next();
											if (object1[0] != null)
											{
												obj.setActionTakenOn(DateUtil.convertDateToIndianFormat(object1[0].toString()));
											}
											else
											{
												obj.setActionTakenOn("NA");
											}

											if (object1[1] != null)
											{
												obj.setActionBy(object1[1].toString());
											}
											else
											{
												obj.setActionBy("NA");
											}

											if (object1[2] != null)
											{
												obj.setLastActionComment(object1[2].toString());
											}
											else
											{
												obj.setLastActionComment("NA");
											}
										}
									}
								}
								if (objects[1] != null)
									obj.setFrequency(new ComplianceReminderHelper().getFrequencyName(objects[1].toString()));
								else
									obj.setFrequency("NA");

								if (objects[2] != null)
									obj.setTaskBrief(objects[2].toString());
								else
									obj.setTaskBrief("NA");

								if (objects[4] != null)
									obj.setDueDate(DateUtil.convertDateToIndianFormat(objects[4].toString()));
								else
									obj.setDueDate("NA");

								if (objects[5] != null)
									obj.setActionStatus(objects[5].toString());
								else
									obj.setActionStatus("NA");

								if (objects[6] != null)
									obj.setTaskName(objects[6].toString());
								else
									obj.setTaskName("NA");

								if (objects[7] != null)
									obj.setTaskType(objects[7].toString());
								else
									obj.setTaskType("NA");

								if (objects[8] != null)
									obj.setDepartName(objects[8].toString());
								else
									obj.setDepartName("NA");

								if (objects[3] != null)
									reminderFor = objects[3].toString();
								else
									reminderFor = "NA";

								String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));

								StringBuilder builder = new StringBuilder();
								builder.append("SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName FROM employee_basic AS emp ");
								builder.append("INNER JOIN compliance_contacts AS cont ON cont.emp_id=emp.id ");
								builder.append("INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cont.id IN(" + contactId + ")");
								List data1 = new createTable().executeAllSelectQuery(builder.toString(), connection);

								if (data1 != null && data1.size() > 0)
								{
									String mobileNo = null, emailId = null, empName = null;
									String mappedTeam = null;
									StringBuilder str = new StringBuilder();
									for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
									{
										Object object2[] = (Object[]) iterator3.next();
										if (object2[2] != null)
										{
											str.append(object2[2].toString() + ", ");
										}
									}
									if (str != null && str.toString().length() > 0)
									{
										mappedTeam = str.toString().substring(0, str.toString().length() - 2);
									}
									obj.setMappedTeam(mappedTeam);
								}
								else
									obj.setMappedTeam("NA");

								listForPendingDataMail.add(obj);
							}
						}

						List todayActionData = null;
						if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.getNewDate("day", -1, workingDay)))
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// DateUtil.getCurrentDateUSFormat(),
							// "equalCondition");
							todayActionData = srvcHelper.getTodayActionData(connection, report_level, type_report, null, DateUtil.getCurrentDateUSFormat(), "equalCondition");
						}
						else
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// workingDayList.get(1).toString(),
							// "betweenCondition");
							todayActionData = srvcHelper.getTodayActionData(connection, report_level, type_report, null, workingDayList.get(1).toString(), "betweenCondition");
						}
						if (todayActionData != null && todayActionData.size() > 0)
						{
							ComplianceDashboardBean obj = null;
							String reminderFor = null;
							for (Iterator iterator5 = todayActionData.iterator(); iterator5.hasNext();)
							{
								obj = new ComplianceDashboardBean();
								Object[] object4 = (Object[]) iterator5.next();

								if (object4[1] != null)
									obj.setFrequency(new ComplianceReminderHelper().getFrequencyName(object4[1].toString()));
								else
									obj.setFrequency("NA");

								if (object4[2] != null)
									obj.setTaskBrief(object4[2].toString());
								else
									obj.setTaskBrief("NA");

								if (object4[3] != null)
									reminderFor = object4[3].toString();
								else
									reminderFor = "NA";

								if (object4[4] != null)
									obj.setDueDate(DateUtil.convertDateToIndianFormat(object4[4].toString()));
								else
									obj.setDueDate("NA");

								if (object4[5] != null)
								{
									if (object4[5].toString().equalsIgnoreCase("Recurring"))
									{
										obj.setActionStatus("Done & " + object4[5].toString());
									}
									else
									{
										obj.setActionStatus(object4[5].toString());
									}
								}
								else
								{
									obj.setActionStatus("NA");
								}

								if (object4[6] != null)
									obj.setTaskName(object4[6].toString());
								else
									obj.setTaskName("NA");

								if (object4[7] != null)
									obj.setTaskType(object4[7].toString());
								else
									obj.setTaskType("NA");

								if (object4[8] != null)
									obj.setDepartName(object4[8].toString());
								else
									obj.setDepartName("NA");

								if (object4[9] != null)
									obj.setEmpId(object4[9].toString());
								else
									obj.setEmpId("NA");

								if (object4[10] != null)
									obj.setComment(object4[10].toString());
								else
									obj.setComment("NA");

								String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));

								StringBuilder builder = new StringBuilder();
								builder.append("SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName FROM employee_basic AS emp ");
								builder.append("INNER JOIN compliance_contacts AS cont ON cont.emp_id=emp.id ");
								builder.append("INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cont.id IN(" + contactId + ")");
								List data1 = new createTable().executeAllSelectQuery(builder.toString(), connection);

								if (data1 != null && data1.size() > 0)
								{

									String mobileNo = null, emailId = null, empName = null;
									String mappedTeam = null;
									StringBuilder str = new StringBuilder();
									for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
									{
										Object object2[] = (Object[]) iterator3.next();
										if (object2[2] != null)
										{
											str.append(object2[2].toString() + ", ");
										}
									}
									if (str != null && str.toString().length() > 0)
									{
										mappedTeam = str.toString().substring(0, str.toString().length() - 2);
									}
									obj.setMappedTeam(mappedTeam);

								}
								else
									obj.setMappedTeam("NA");

								listForActionDataMail.add(obj);
							}
						}
						if ((listForPendingDataMail != null || listForActionDataMail != null) && (listForActionDataMail.size() > 0 || listForPendingDataMail.size() > 0))
						{
							String filePath = new DailyCompReportExcelWrite4Attach().writeDataInExcel(listForActionDataMail, listForPendingDataMail, null, type_report);
							String mailSubject = "Today Operational Task Status";
							String mailText = new ComplianceReminderHelper().configReportComp(listForPendingDataMail, listForActionDataMail, empname, mailSubject);
							cmnHelper.addMail(empname, department, emailid, mailSubject, mailText, "", "Pending", "0", filePath, "Compliance", connection);
						}
						else
						{
							String mailSubject = "Today There Are No Operational Task";
							String mailText = new ComplianceReminderHelper().configReportComp(listForPendingDataMail, listForActionDataMail, empname, mailSubject);
							cmnHelper.addMail(empname, department, emailid, mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);
						}
					}
			    }
			  }
			     Runtime rt = Runtime.getRuntime();
				 rt.gc();
				 System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				 Thread.sleep(1000*5);
				 System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}
