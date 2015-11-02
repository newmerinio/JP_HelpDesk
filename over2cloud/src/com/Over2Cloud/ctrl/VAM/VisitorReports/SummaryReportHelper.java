package com.Over2Cloud.ctrl.VAM.VisitorReports;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.DailyReportExcelWrite4Attach;
import com.Over2Cloud.ctrl.VAM.BeanUtil.SummaryReportPojo;
import com.Over2Cloud.ctrl.VAM.common.SummaryReportHelperUniversal;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class SummaryReportHelper
{
	public void reportGeneration(SessionFactory connection,SummaryReportHelperUniversal SRHU,CommunicationHelper CH)
	{
		try
    {
			String todaydate = DateUtil.getCurrentDateUSFormat();
			String currenttime = DateUtil.getCurrentTime();
			currenttime = currenttime.substring(0, 5);
			List dataforreporttodetail =  (List) SRHU.getSummaryReportToData(connection);
			if(dataforreporttodetail != null && dataforreporttodetail.size()>0)
			{
				String location = null, gate = null, deptname = null, reportfor = null, empname = null, mobno = null, mailid = null, reportat = null;
				for (Iterator iterator = dataforreporttodetail.iterator(); iterator
                                                                                   .hasNext();)
			        {
				        Object[] object = (Object[]) iterator.next();
				        
				        if (object[1] != null) {
				        	location = object[1].toString();
								} else {
									location = "NA";
								}
				        if (object[2] != null) {
				        	gate = object[2].toString();
								} else {
									gate = "NA";
								}
				        if (object[3] != null) {
				        	deptname = object[3].toString();
								} else {
									deptname = "NA";
								}
				        if (object[4] != null) {
				        	reportfor = object[4].toString();
								} else {
									reportfor = "NA";
								}
				        if (object[5] != null) {
				        	empname = object[5].toString();
								} else {
									empname = "NA";
								}
				        if (object[6] != null) {
				        	mobno = object[6].toString();
								} else {
									mobno = "NA";
								}
				        if (object[7] != null) {
				        	mailid = object[7].toString();
								} else {
									mobno = "NA";
								}
				        if (object[8] != null) {
				        	reportat = object[8].toString();
								} else {
									reportat = "NA";
								}
				        
						        //data to send in report.
				        		//System.out.println("deptname>>>>>>>"+deptname);
						        boolean status = currenttime.equalsIgnoreCase(reportat);
				        		System.out.println("status>>"+status);
				        		if(currenttime.equalsIgnoreCase(reportat)){
				        			
				      			//List<SummaryReportPojo> visitordatalist = null;
								List<SummaryReportPojo> vehicledatalist = null;
								//List<SummaryReportPojo> postacknowledgelist = null;
								String deptnameId = CommonMethod.getDeptId(connection, deptname);
								String locationId = CommonMethod.getLocationId(connection, location);
								//visitordatalist = SRHU.getVisitorDataList(connection, todaydate, deptnameId, locationId);
								vehicledatalist = SRHU.getVehicleDataList(connection, todaydate);
								// postacknowledgelist = SRHU.getPostAckDataList(connection, todaydate);
								
								//String filepath = new SummaryReportExcelToAttache().writeDataToExcel(visitordatalist,vehicledatalist,postacknowledgelist);
								//String filepath = new SummaryReportExcelToAttache().writeDataToExcel(visitordatalist, location);
								//System.out.println("filepath>>"+filepath);
								//String mailtextvisitor = SRHU.getConfigMailForSummaryReport(connection, empname, visitordatalist, mobno, location);
								  String mailtextvehicle = SRHU.getConfigVehicleSumarryRep(empname, vehicledatalist, location);
								// String mailtextpostack = SRHU.getConfigPostSumarryRep(empname, postacknowledgelist, location);
								//String subjectVis = "Daily Visitor Summary Report For "+location+" as on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimeHourMin();
								String subjectVeh = "Daily Vehicle Summary Report For "+location+" as on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimeHourMin();
								// String subjectPost = "Daily Post Summary Report For "+location+" as on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimeHourMin();
								//CH.addMail(empname, deptname, mailid, subjectVis,	mailtextvisitor, "Report", "Pending", "0", "","VAM", connection);
								CH.addMail(empname, deptname, mailid, subjectVeh, mailtextvehicle, "Report", "Pending", "0", "", "VAM", connection);
								// CH.addMail(empname, deptname, mailid, subjectPost, mailtextpostack, "Report", "Pending", "0", "", "VAM", connection);
				        		}
			        }
			}
    } catch (Exception e)
    {
	    e.printStackTrace();
    }
	}
}
