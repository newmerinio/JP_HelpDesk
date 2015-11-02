package com.Over2Cloud.ctrl.dar.submission;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.offeringComplaint.ComplaintReportHelper;


public class TestEveningReport  extends Thread
{
	
private static SessionFactory connection=null;
DARReportHelper HUH=null;
CommunicationHelper CH = null;

@SuppressWarnings("static-access")
public TestEveningReport(SessionFactory connection,DARReportHelper HUHObj,CommunicationHelper CHObj)
  {
	this.connection=connection;
	this.HUH=HUHObj;
	this.CH=CHObj;
  }
	//HelpdeskServiceFileHelper HSFH = new HelpdeskServiceFileHelper();
DARReportHelper DARR=new DARReportHelper();
ComplaintReportHelper CRH=new ComplaintReportHelper();
    public void run()
	{
		try {
			 while (true) {
				try {
					List<DarSubmissionPojoBean> reportData = DARR.getAllData(connection);
					if (reportData!=null && reportData.size()>0) 
					{
						String mailtext = DARR.getConfigMailForReport(reportData);
						//String filepath=DARR.writeDataInExcel(reportData);
						if (mailtext!=null && !mailtext.equals("")) 
						{
							new CommunicationHelper().addMail("Mr. Prabhat Kumar","Management","karnikag@dreamsol.biz","Evening Report", mailtext,"","Pending", "0","","DAR",connection);
						}
					}
				/*	List<DarSubmissionPojoBean> todayReview = DARR.getAllTodayReviewData(connection);
					List<DarSubmissionPojoBean> tommorowReview = DARR.getAllTommorowReviewData(connection);
					List<DarSubmissionPojoBean> dayAfterReview = DARR.getAllDayAfterReviewData(connection);
					if (todayReview!=null && todayReview.size()>0) 
					{
						String mailtext = DARR.getConfigMailForReviewReport(todayReview,tommorowReview,dayAfterReview);
						if (mailtext!=null && !mailtext.equals("")) 
						{
							new CommunicationHelper().addMail("Mr. Prabhat Kumar","Management","karnikag@dreamsol.biz","Evening Report", mailtext,"","Pending", "0","","DAR",connection);
						}
					}
					List<DarSubmissionPojoBean> alreadyFree = DARR.getAllAlreadyFreeData(connection);
					List<DarSubmissionPojoBean> todayFree = DARR.getAllTodayFreeData(connection);
					List<DarSubmissionPojoBean> tommorowFree = DARR.getAllTommorowFreeData(connection);
					if (alreadyFree!=null && alreadyFree.size()>0) 
					{
						String mailtext = DARR.getConfigMailForFreeResourceReport(alreadyFree,todayFree,tommorowFree);
						if (mailtext!=null && !mailtext.equals("")) 
						{
							new CommunicationHelper().addMail("Mr. Prabhat Kumar","Management","karnikag@dreamsol.biz","Evening Report", mailtext,"","Pending", "0","","DAR",connection);
						}
					}*/
					
					/*List currentdayCounter = new ArrayList();
					List CFCounter = new ArrayList();
					List<FeedbackPojo> currentDayResolvedData = null;
					List<FeedbackPojo> currentDayPendingData = null;
					List<FeedbackPojo> currentDaySnoozeData = null;
					List<FeedbackPojo> currentDayHPData = null;
					List<FeedbackPojo> currentDayIgData = null;
					List<FeedbackPojo> CFData = null;
					String report_level="2";
					String type_report="D";
					int pc = 0, hc = 0, sc = 0, ic = 0, rc = 0, total = 0;
					int rAc = 0, cfrAc = 0;
					int cfpc = 0, cfhc = 0, cfsc = 0, cfic = 0, cfrc = 0, cftotal = 0;
					
					if (report_level.equals("2"))
					{
						currentdayCounter = CRH.getTicketCounters(report_level, type_report, true, "4", connection);
						CFCounter = CRH.getTicketCounters(report_level, type_report, false, "4", connection);
						currentDayResolvedData = CRH.getTicketData(report_level, type_report, true, "Resolved", "4", connection);
						currentDayPendingData = CRH.getTicketData(report_level, type_report, true, "Pending", "4",connection);
						currentDaySnoozeData = CRH.getTicketData(report_level, type_report, true, "Snooze", "4",connection);
						currentDayHPData = CRH.getTicketData(report_level, type_report, true, "High Priority", "4",connection);
						currentDayIgData = CRH.getTicketData(report_level, type_report, true, "Ignore", "4", connection);
						CFData = CRH.getTicketData(report_level, type_report, false, "", "4", connection);
					}
					else if (report_level.equals("1"))
					{
						currentdayCounter = CRH.getTicketCounters(report_level, type_report, true, "4", connection);
						CFCounter = CRH.getTicketCounters(report_level, type_report, false, "4", connection);
						currentDayResolvedData = CRH.getTicketData(report_level, type_report, true, "Resolved", "4",connection);
						currentDayPendingData = CRH.getTicketData(report_level, type_report, true, "Pending", "4",connection);
						currentDaySnoozeData = CRH.getTicketData(report_level, type_report, true, "Snooze", "4",connection);
						currentDayHPData = CRH.getTicketData(report_level, type_report, true, "High Priority", "4", connection);
						currentDayIgData = CRH.getTicketData(report_level, type_report, true, "Ignore", "4", connection);
						CFData = CRH.getTicketData(report_level, type_report, false, "", "4",connection);
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
							}
							else if (object2[1].toString().equalsIgnoreCase("Snooze"))
							{
								sc = Integer.parseInt(object2[0].toString());
							}
							else if (object2[1].toString().equalsIgnoreCase("High Priority"))
							{
								hc = Integer.parseInt(object2[0].toString());
							}
							else if (object2[1].toString().equalsIgnoreCase("Ignore"))
							{
								ic = Integer.parseInt(object2[0].toString());
							}
							else if (object2[1].toString().equalsIgnoreCase("Resolved"))
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
							}
							else if (object3[1].toString().equalsIgnoreCase("Snooze"))
							{
								cfsc = Integer.parseInt(object3[0].toString());
							}
							else if (object3[1].toString().equalsIgnoreCase("High Priority"))
							{
								cfhc = Integer.parseInt(object3[0].toString());
							}
						}
						cftotal = cfpc + cfsc + cfhc;
					}
					String report_sms = "Dear " + "Karnika" + ", For All Ticket Status as on " + DateUtil.getCurrentDateIndianFormat()
							+ ": Pending: " + pc + ", C/F Pending: " + cftotal + ", Snooze: " + sc + ", Ignore: " + ic + ", Resolved: " + rc
							+ ".";
					String mobno="7503771131";
					if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10
							&& (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
					{
						@SuppressWarnings("unused")
						boolean putsmsstatus = CH.addMessage("Karnika", "IT", "7503771131", report_sms, "Testing", "Pending", "0", "HDM", connection);
					}
					String filepath = new DailyReportExcelWrite4Attach().writeDataInExcelForDream_HDM(currentdayCounter, CFCounter, currentDayResolvedData,
							currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, CFData, "", type_report);
					String mailtext = CRH.getConfigMailForReport(pc, hc, sc, ic, rc, total, cfpc, cfsc, cfhc, cftotal, "karnika",currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, CFData);
					CH.addMail("Karnika", "IT", "Karnikag@dreamsol.biz", "Test mail", mailtext, "Report", "Pending", "0", filepath, "DREAM_HDM", connection);
					*/
					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(1000*60);
					System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	 {
		try {
			 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		     CommunicationHelper CH = new CommunicationHelper();
		     DARReportHelper DARR=new DARReportHelper();
		     Thread esc = new Thread(new TestEveningReport(connection,DARR,CH));
		     esc.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	 }
 }
