package com.Over2Cloud.ctrl.VAM.VisitorReports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.ctrl.VAM.BeanUtil.SummaryReportPojo;
import com.Over2Cloud.ctrl.VAM.common.SummaryReportHelperUniversal;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class VisitorStayAlertHelperUniversal {
	private String totaltimestay = null;
	private String visitorname = null;
	private String visitormob = null;
	private String alertafter = null;
	String timein = null;
	@SuppressWarnings("rawtypes")
	  public List getStayAlertToData(SessionFactory connection)
		{
			List  stayalertdatalist = new ArrayList();
			StringBuffer query= new StringBuffer();
			query.append("select srd.id, loc.name, gld.gate, dpt.deptName, srd.report_for, empb.empName, empb.mobOne, empb.emailIdOne, srd.report_at from summary_report_details as srd");
			query.append(" left join location as loc on srd.location = loc.id ");
			query.append(" left join department as dpt on srd.deptName = dpt.id ");
			query.append(" left join employee_basic as empb on srd.empName = empb.id ");
			query.append(" left join gate_location_details as gld on srd.gate = gld.id ");
			//System.out.println("QQQQ :::: "+query.toString());
			Session session = null;  
		  Transaction transaction = null;  
			try 
			  {  
				 session=connection.openSession(); 
				 transaction = session.beginTransaction(); 
				 stayalertdatalist=session.createSQLQuery(query.toString()).list();  
				 transaction.commit(); 
			  }
		    catch (Exception ex)
			  {
				 transaction.rollback();
			  } 
			finally{
				try {
					session.flush();
					session.close();
				} catch (Exception e) {
					
				}
			}
			return stayalertdatalist;
		}
	 public List<SummaryReportPojo> getVisitorDataList(SessionFactory connection, String todaydate)
		{
	  	 	 List  visitordatalist = new ArrayList();
			 List<SummaryReportPojo> report = new ArrayList<SummaryReportPojo>();
			 StringBuilder query = new StringBuilder("");
			 query.append("select vid.sr_number, vid.visitor_name,vid.visitor_mobile, vid.visitor_company,vid.address,pa.purpose, vid.visited_person,vid.visited_mobile,dept.deptName, vid.time_in,vid.time_out,loc.name, vid.status, vid.alert_after from visitor_entry_details as vid");
			 query.append(" left join department as dept on vid.deptName = dept.id");
			 query.append(" left join purpose_admin as pa on vid.purpose = pa.id");
			 query.append(" left join location as loc on vid.location = loc.id");
			 query.append(" where visit_date = '"+todaydate+"' and vid.sr_number is not null and vid.time_out is null");
			 query.append(" order by vid.location");
			 Session session = null;  
			 Transaction transaction = null;  
			 try 
			  {  
				 session=connection.openSession(); 
				 transaction = session.beginTransaction(); 
				 visitordatalist=session.createSQLQuery(query.toString()).list();  
				 transaction.commit(); 
			  }
		    catch (Exception ex)
			  {
				  ex.printStackTrace();
		    	transaction.rollback();
			  } 
			 if (visitordatalist!=null && visitordatalist.size()>0) {
					report = new VisitorStayAlertHelperUniversal().setvisitorDataforReport(visitordatalist);
				}
			 return report;
		}
	 public List<SummaryReportPojo> setvisitorDataforReport(List visitordatalist)
	  {
	  	 
			 String timeout = null;
			 String totalstay = null;
			List<SummaryReportPojo> srpList = new ArrayList<SummaryReportPojo>();
			SummaryReportPojo srp =null;
			if (visitordatalist!=null && visitordatalist.size()>0) {
				for (Iterator iterator = visitordatalist.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					srp = new SummaryReportPojo();
					
					if (object[0]!=null) {
						srp.setSr_number(object[0].toString());
					}
					else {
						srp.setSr_number("NA");
					}
					
					if (object[1]!=null) {
						srp.setVisitor_name(object[1].toString());
						this.visitorname = object[1].toString();
					}
					else {
						srp.setVisitor_name("NA");
					}
					
					if (object[2]!=null) {
						srp.setVisitor_mobile(object[2].toString());
						this.visitormob = object[2].toString();
					}
					else {
						srp.setVisitor_mobile("NA");
					}
					
					if (object[3]!=null) {
						srp.setVisitor_company(object[3].toString());
					}
					else {
						srp.setVisitor_company("NA");
					}
					
					if (object[4]!=null) {
						srp.setAddress(object[4].toString());
					}
					else {
						srp.setAddress("NA");
					}
					
					if (object[5]!=null) {
						srp.setPurpose(object[5].toString());
					}
					else {
						srp.setPurpose("NA");
					}
					
					if (object[6]!=null) {
						srp.setVisited_person(object[6].toString());
					}
					else {
						srp.setVisited_person("NA");
					}
					
					if (object[7]!=null) {
						srp.setVisited_mobile(object[7].toString());
					}
					else {
						srp.setVisited_mobile("NA");
					}
					
					if (object[8]!=null) {
						srp.setDeptName(object[8].toString());
					}
					else {
						srp.setDeptName("NA");
					}
					
					if (object[9]!=null) {
						srp.setTime_in((object[9].toString()).substring(0, 5));
						this.timein = object[9].toString();
					}
					else {
						srp.setTime_in("NA");
					}
					
					if (object[10]!=null) {
						srp.setTime_out(object[10].toString());
						timeout = object[10].toString();
					}
					else {
						srp.setTime_out("NA");
					}
					
					if (object[11]!=null) {
						srp.setLocation(object[11].toString());
					}
					else {
						srp.setLocation("NA");
					}
					if (object[12]!=null) {
						srp.setStatus(object[12].toString());
					}
					else {
						srp.setStatus("NA");
					}
					if (object[13]!=null) {
						srp.setAlert_after(object[13].toString());
						this.alertafter = object[13].toString();
					}
					else {
						srp.setAlert_after("NA");
					}
					// srp.setTotaltimestay(CommonMethod.subtractTime(timein,timeout));
					System.out.println("DateUtil.getCurrentTime()>>"+DateUtil.getCurrentTime()+"timein>>"+timein);
					String totaltm = CommonMethod.subtractTime(timein,DateUtil.getCurrentTime());
					System.out.println("totaltm>>"+totaltm);
					if(totaltm != null)
					{
						//srp.setTotaltimestay(totaltm.substring(0, 4));
						srp.setTotaltimestay(totaltm);
					}
					
				   srpList.add(srp);	
				
				}
			}
			return srpList;
	  }
	 public String getConfigMailForStayAlert(String empname, List<SummaryReportPojo> visitordatalist, String location)
	 {
		 long j=0,l=0;
		 List orgData= new LoginImp().getUserInfomation("8", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
			 for(SummaryReportPojo SRP:visitordatalist)
			 {
				 if(!(SRP.getStatus().equals("0")))
					{
					 j++;
					}else
					{
						l++;
					}
			 }
				StringBuilder mailtext=new StringBuilder("");
				mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr></tbody></table>");
				mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Visitor Alert Manager</b></td></tr></tbody></table>");
        mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Visitor Stay Alert For "+location+" as On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTimeHourMin()+" For All Department</b></td></tr></tbody></table>");
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Total Visitor</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Visitor Out</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Visitor Still In Campus</b></td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+visitordatalist.size()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+j+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+l+"</td></tr></tbody></table>");
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sr.&nbsp;No.</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visitor&nbsp;Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visitor&nbsp;Mobile</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visitor&nbsp;Company</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Address</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Purpose</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visited&nbsp;Person</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Visited&nbsp;Mobile</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;IN</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alert&nbsp;Time</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total&nbsp;Stay</strong></td></tr>");
        if (visitordatalist!=null && visitordatalist.size()>0)
        {
       	 int i=0;
     	 for(SummaryReportPojo SRP:visitordatalist)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					if(!(SRP.getStatus().equals("0")) && SRP.getTotaltimestay() != null)
					{
						mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getSr_number()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_name()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_mobile()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_company()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAddress()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getPurpose()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_person()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_mobile()+"</td><td align='left' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getDeptName()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getLocation()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTime_in()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAlert_after()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTotaltimestay()+"</td></tr>");
					}else
					{
						mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getSr_number()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_name()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_mobile()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_company()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAddress()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getPurpose()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_person()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_mobile()+"</td><td align='left' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getDeptName()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getLocation()+"</td><td align='left' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTime_in()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAlert_after()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTotaltimestay()+"</td></tr>");
					}
					
				}
				else
				{
					if(!(SRP.getStatus().equals("0")) && SRP.getTotaltimestay() != null)
					{
						mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getSr_number()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_name()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_mobile()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_company()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAddress()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getPurpose()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_person()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_mobile()+"</td><td align='left' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getDeptName()+"</td><td align='left' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getLocation()+"</td><td align='left' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTime_in()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAlert_after()+"</td><td align='left' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTotaltimestay()+"</td></tr>");
					}else
					{
						mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getSr_number()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_name()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_mobile()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisitor_company()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAddress()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getPurpose()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_person()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getVisited_mobile()+"</td><td align='left' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getDeptName()+"</td><td align='left' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getLocation()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTime_in()+"</td><td align='left' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getAlert_after()+"</td><td align='left' width='12%'    style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+SRP.getTotaltimestay()+"</td></tr>");
					}
					
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
       	System.out.println("mailtext>>Visitor>"+mailtext.toString()); 
       	return mailtext.toString() ; 
	 }
	 
	 public boolean getAlertSms(String empname, String emobile, String todaydate, SessionFactory connection)
	 {
		 List  visitoralertdatalist = new ArrayList();
		 
		 List<SummaryReportPojo> report = new ArrayList<SummaryReportPojo>();
		 StringBuilder query = new StringBuilder("");
		 query.append("select vid.sr_number, vid.visitor_name,vid.visitor_mobile, vid.visitor_company,vid.address,pa.purpose, vid.visited_person,vid.visited_mobile,dept.deptName, vid.time_in,vid.time_out,loc.name, vid.status, vid.alert_after from visitor_entry_details as vid");
		 query.append(" left join department as dept on vid.deptName = dept.id");
		 query.append(" left join purpose_admin as pa on vid.purpose = pa.id");
		 query.append(" left join location as loc on vid.location = loc.id");
		 query.append(" where visit_date = '"+todaydate+"' and vid.sr_number is not null and vid.time_out is null");
		 query.append(" order by vid.location");
		 Session session = null;  
		 Transaction transaction = null;  
		 try 
		  {  
			 session=connection.openSession(); 
			 transaction = session.beginTransaction(); 
			 visitoralertdatalist=session.createSQLQuery(query.toString()).list();  
			 transaction.commit(); 
		  }
	    catch (Exception ex)
		  {
			  ex.printStackTrace();
	    	transaction.rollback();
		  } 
	    String timeout = null;
		 String totalstay = null;
		List<SummaryReportPojo> srpList = new ArrayList<SummaryReportPojo>();
		SummaryReportPojo srp =null;
		if (visitoralertdatalist!=null && visitoralertdatalist.size()>0) {
			for (Iterator iterator = visitoralertdatalist.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				srp = new SummaryReportPojo();
				
				if (object[0]!=null) {
					srp.setSr_number(object[0].toString());
				}
				else {
					srp.setSr_number("NA");
				}
				
				if (object[1]!=null) {
					srp.setVisitor_name(object[1].toString());
					visitorname = object[1].toString();
				}
				else {
					srp.setVisitor_name("NA");
				}
				
				if (object[2]!=null) {
					srp.setVisitor_mobile(object[2].toString());
					visitormob = object[2].toString();
				}
				else {
					srp.setVisitor_mobile("NA");
				}
				
				if (object[3]!=null) {
					srp.setVisitor_company(object[3].toString());
				}
				else {
					srp.setVisitor_company("NA");
				}
				
				if (object[4]!=null) {
					srp.setAddress(object[4].toString());
				}
				else {
					srp.setAddress("NA");
				}
				
				if (object[5]!=null) {
					srp.setPurpose(object[5].toString());
				}
				else {
					srp.setPurpose("NA");
				}
				
				if (object[6]!=null) {
					srp.setVisited_person(object[6].toString());
				}
				else {
					srp.setVisited_person("NA");
				}
				
				if (object[7]!=null) {
					srp.setVisited_mobile(object[7].toString());
				}
				else {
					srp.setVisited_mobile("NA");
				}
				
				if (object[8]!=null) {
					srp.setDeptName(object[8].toString());
				}
				else {
					srp.setDeptName("NA");
				}
				
				if (object[9]!=null) {
					srp.setTime_in(object[9].toString());
					this.timein = object[9].toString();
				}
				else {
					srp.setTime_in("NA");
				}
				
				if (object[10]!=null) {
					srp.setTime_out(object[10].toString());
					timeout = object[10].toString();
				}
				else {
					srp.setTime_out("NA");
				}
				
				if (object[11]!=null) {
					srp.setLocation(object[11].toString());
				}
				else {
					srp.setLocation("NA");
				}
				if (object[12]!=null) {
					srp.setStatus(object[12].toString());
				}
				else {
					srp.setStatus("NA");
				}
				if (object[13]!=null) {
					srp.setAlert_after(object[13].toString());
					alertafter = object[13].toString();
				}
				else {
					srp.setAlert_after("NA");
				}
			   //srp.setTotaltimestay(CommonMethod.subtractTime(timein,timeout));
			   //srpList.add(srp);	
				boolean alertstatus = false;
				String alerttime = CommonMethod.subtractTime(timein,DateUtil.getCurrentTime());
				int alertTime=Integer.parseInt((alertafter.substring(0, 2)));
				if(alerttime != null)
				{
					alertstatus = CommonMethod.getAlertStatus(alerttime,alertTime);
				}
				
			   if(alertstatus)
			   {
				   boolean servrStatus = true;
				   String alsms = "Stay Alert For Visitor: "+visitorname+", "+visitormob+", Time In: "+timein+" allowed time "+alertafter+" came to meet "+empname+", "+emobile+" is still in campus.";
				   new MsgMailCommunication().addMessage(emobile, alsms, "", "Pending", "0", "VAM", connection);
				  /* servrStatus=  new MsgMailCommunication().addMessageClientServer(emobile, alsms, "","Pending", "0", "VAM", connection);
				   if(!servrStatus)
				   {
					   servrStatus = true;
				   }*/
			   }
				
			   
			}
		}
		return true; 
	 }
	 
	public String getTotaltimestay() {
		return totaltimestay;
	}
	public void setTotaltimestay(String totaltimestay) {
		this.totaltimestay = totaltimestay;
	}
	public String getVisitorname() {
		return visitorname;
	}
	public void setVisitorname(String visitorname) {
		this.visitorname = visitorname;
	}
	public String getVisitormob() {
		return visitormob;
	}
	public void setVisitormob(String visitormob) {
		this.visitormob = visitormob;
	}
	public String getAlertafter() {
		return alertafter;
	}
	public void setAlertafter(String alertafter) {
		this.alertafter = alertafter;
	}
	public String getTimein() {
		return timein;
	}
	public void setTimein(String timein) {
		this.timein = timein;
	}
	 
}
