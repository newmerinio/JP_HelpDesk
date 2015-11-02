package com.Over2Cloud.ctrl.VAM.VisitorReports;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.ctrl.VAM.BeanUtil.SummaryReportPojo;

public class VisitorStayAlertHelper {
	public void alertGeneration(SessionFactory connection,VisitorStayAlertHelperUniversal VSAHU,CommunicationHelper CH)
	{

		try
    {
			String todaydate = DateUtil.getCurrentDateUSFormat();
			List dataforalerttodetail =  (List) VSAHU.getStayAlertToData(connection);
			if(dataforalerttodetail != null && dataforalerttodetail.size()>0)
			{
				String location = null, gate = null, deptname = null, reportfor = null, empname = null, mobno = null, mailid = null, reportat = null;
				for (Iterator iterator = dataforalerttodetail.iterator(); iterator
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
	      	List<SummaryReportPojo> visitordatalist = null;
					//List<SummaryReportPojo> vehicledatalist = null;
					//List<SummaryReportPojo> postacknowledgelist = null;
					
					visitordatalist = VSAHU.getVisitorDataList(connection, todaydate);
					String mailtextstayalert = VSAHU.getConfigMailForStayAlert(empname, visitordatalist, location);
					boolean statusmail = CH.addMail(empname, deptname, mailid, "Visitor Stay Alert",	mailtextstayalert, "Alert", "Pending", "0", "","VAM", connection);
					if(statusmail)
					{
						boolean alertsms = VSAHU.getAlertSms(empname, mobno, todaydate, connection);
						//new MsgMailCommunication().addMessage(mobno, alertsms, "", "Pending", "0", "VAM", connection);
					}
					
					/*vehicledatalist = VSAHU.getVehicleDataList(connection, todaydate);
					postacknowledgelist = VSAHU.getPostAckDataList(connection, todaydate);
					
					String filepath = new SummaryReportExcelToAttache().writeDataToExcel(visitordatalist,vehicledatalist,postacknowledgelist);
					String mailtextvisitor = VSAHU.getConfigMailForSummaryReport(empname, visitordatalist);
					String mailtextvehicle = VSAHU.getConfigVehicleSumarryRep(empname, vehicledatalist);
					String mailtextpostack = VSAHU.getConfigPostSumarryRep(empname, postacknowledgelist);
					CH.addMail(empname, deptname, mailid, "Daily Visitor Summary Report",	mailtextvisitor, "Report", "Pending", "0", filepath,"VAM", connection);
					CH.addMail(empname, deptname, mailid, "Daily Vehicle Summary Report", mailtextvehicle, "Report", "Pending", "0", filepath, "VAM", connection);
					CH.addMail(empname, deptname, mailid, "Daily Post Summary Report", mailtextpostack, "Report", "Pending", "0", filepath, "VAM", connection);*/
        }
			}
    } catch (Exception e)
    {
	    e.printStackTrace();
    }
	
	}
}
