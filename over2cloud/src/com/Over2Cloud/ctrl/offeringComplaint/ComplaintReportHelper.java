package com.Over2Cloud.ctrl.offeringComplaint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class ComplaintReportHelper
{
	@SuppressWarnings("rawtypes")
    public List getTicketCounters(String reportLevel,String reportType, boolean cd_pd,String for_dept,SessionFactory connection)
	 {
		List counterList = new ArrayList();
		StringBuilder qry = new StringBuilder();
		qry.append("select count(*),status from complaint_status");
		if (reportLevel.equals("2")) 
		{
			if (cd_pd) 
			{
				if (reportType!=null && !reportType.equals("") && reportType.equals("D")) {
					qry.append(" where open_date='"+DateUtil.getCurrentDateUSFormat()+"' and moduleName='DREAM_HDM' group by status");
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("W")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and moduleName='DREAM_HDM' group by status");
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("M")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and moduleName='DREAM_HDM' group by status");				
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("Q")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and moduleName='DREAM_HDM' group by status");
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("H")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and moduleName='DREAM_HDM' group by status");
				}
			}
			else 
			{
				qry.append(" where open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and moduleName='DREAM_HDM' group by status");
			}
		}
		else if (reportLevel.equals("1")) 
		{
			if (cd_pd) 
			{
				if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("D")) {
					qry.append(" where open_date='"+DateUtil.getCurrentDateUSFormat()+"' and forDept = '"+for_dept+"'  and moduleName='DREAM_HDM' group by status");
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("W")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and forDept = '"+for_dept+"' and moduleName='DREAM_HDM' group by status");
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("M")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and forDept = '"+for_dept+"' and moduleName='DREAM_HDM' group by status");				
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("Q")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and forDept = '"+for_dept+"' and moduleName='DREAM_HDM' group by status");
				}
				else if (reportType!=null && !reportType.equals("") && reportType.equalsIgnoreCase("H")) {
					qry.append(" where open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"' and forDept = '"+for_dept+"' and moduleName='DREAM_HDM' group by status");
				}
			}
			else 
			{
				qry.append(" where open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and forDept = '"+for_dept+"' and moduleName='DREAM_HDM' group by status");
			}
		}
		System.out.println("Counter Query  "+qry.toString());
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			counterList=session.createSQLQuery(qry.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{
			   transaction.rollback();
			} 
		
		return counterList;
	 }
	@SuppressWarnings("rawtypes")
    public List<FeedbackPojo> getTicketData( String reportLevel,String reportType, boolean cd_pd, String  status,String for_dept,SessionFactory connection)
	 {
		  List  dataList = new ArrayList();
		  List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
		  StringBuilder query = new StringBuilder("");
		  query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.clientfor,feedback.clientName,feedback.offering,emp.empName AS allot_to,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.feedbackTo,feedback.feed_registerby,feedback.feedbackCC");
       		
	        // Block for get resolved data
	        if (status.equalsIgnoreCase("Resolved"))
	        {
	            query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used,rca.rcaBrief,feedback.resolve_capa ");
		        
	        }
	        // Block for get Snooze data
	        else if (status.equalsIgnoreCase("Snooze")) 
	        {
	        	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
			}
	        // Block for get High Priority data
	        else if (status.equalsIgnoreCase("High Priority")) 
	        {
	        	query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
			}
	        // Block for get ignore data
	        else if (status.equalsIgnoreCase("Ignore")) 
	        {
	        	query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason");
			}
	      
			if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending")) 
			{
	        	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
			}
			query.append(",subcatg.addressingTime,feedback.escalation_date,feedback.escalation_time");
	        query.append(" FROM complaint_status as feedback");
	        
	        query.append(" INNER JOIN compliance_contacts AS cc ON cc.id=feedback.allot_to ");
	        query.append(" INNER JOIN employee_basic emp on cc.emp_id= emp.id");
	       
    		query.append(" INNER JOIN feedback_subcategory subcatg on feedback.subCategory = subcatg.id");
    		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
    		query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
    		query.append(" inner join department dept1 on feedback.forDept= dept1.id  "); 
	
	        // Apply inner join for getting the data for Resolved Ticket
			if (status.equalsIgnoreCase("Resolved")) 
			{
				query.append(" INNER JOIN compliance_contacts AS cc1 ON cc1.id=feedback.resolve_by ");
				query.append(" INNER JOIN employee_basic emp1 on cc1.emp_id= emp1.id"); 
				query.append(" INNER JOIN rca_master as rca ON rca.id=feedback.resolve_rca ");
			}
		
	    		// getting data of current day
				if (cd_pd) 
				{
					query.append(" where feedback.status='"+status+"' ");
					if (reportType!=null && !reportType.equals("") && reportType.equals("D")) 
					{
						query.append(" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"'");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equals("W")) 
					{
						query.append(" and feedback.open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equals("M")) 
					{
						query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");				
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equals("Q")) 
					{
						query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
					}
					else if (reportType!=null && !reportType.equals("") && reportType.equals("H")) 
					{
						query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
					}
					
					if (reportLevel.equals("1")) 
					{
						 query.append(" and feedback.forDept = '"+for_dept+"' "); 
					}
				}
		        // End of If Block for getting the data for only the current date in both case for Resolved OR All
		
				// Else Block for getting the data for only the previous date
				else 
				{
						if (reportLevel.equals("2")) 
						{
							  query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"'");
						}
						else if (reportLevel.equals("1")) 
						{
							 query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and feedback.forDept = '"+for_dept+"' "); 
						}
				}
				query.append(" and feedback.moduleName='DREAM_HDM'");
				System.out.println("DAta Query "+status+" "+query.toString());
					Session session = null;  
					Transaction transaction = null;  
					try 
					 {  
						session=connection.getCurrentSession(); 
						transaction = session.beginTransaction(); 
						dataList=session.createSQLQuery(query.toString()).list();  
						transaction.commit(); 
					 }
					catch (Exception ex)
						{
						   transaction.rollback();
						} 
										
					if (dataList!=null && dataList.size()>0) 
					{
						report = new ComplaintLodgeHelper().setFeedbackDataforReport(dataList, status, "",connection);
					}
					return report;
	 }
	
	@SuppressWarnings("rawtypes")
    public String getConfigMailForReport(int pc,int hc,int sc,int ic,int rc,int total,int cfpc,int cfsc,int cfhc,int cftotal,String empname, List<FeedbackPojo> currentDayResolvedData,List<FeedbackPojo> currentDayPendingData,List<FeedbackPojo> currentDaySnoozeData,List<FeedbackPojo> currentDayHPData,List<FeedbackPojo> currentDayIgData,List<FeedbackPojo> CFData)
	  {
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr></tbody></table>");
        mailtext.append("<b>Dear "+DateUtil.makeTitle(empname)+",</b>");
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Summary Status For All Department On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime()+"</b></td></tr></tbody></table>");
		 
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Pending</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/D Total</b></td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+pc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+hc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+sc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ic+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+rc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfpc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfsc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+cfhc+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+total+"</td></tr></tbody></table>");
	     
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;CC</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;On</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To&nbsp;Sub&nbsp;Dept.</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Client&nbsp;For</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Client&nbsp;Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Offering</strong></td></tr>");
        if (currentDayResolvedData!=null && currentDayResolvedData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayResolvedData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDayPendingData!=null && currentDayPendingData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayPendingData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDaySnoozeData!=null && currentDaySnoozeData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDaySnoozeData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDayHPData!=null && currentDayHPData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayHPData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (currentDayIgData!=null && currentDayIgData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:currentDayIgData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        if (CFData!=null && CFData.size()>0)
        {
       	 int i=0;
     	 for(FeedbackPojo FBP:CFData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td> <td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td>  <td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getTicket_no()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_by()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeed_cc()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_date()+"</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOpen_time()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_to_dept()+"</td><td align='left' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getFeedback_allot_to()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getLocation()+"</td><td align='left' width='12%' bgcolor='#53c156' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getStatus()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientFor()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getClientName()+"</td><td align='left' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getOfferingName()+"</td></tr>");
				}
            }
       	 }  
        mailtext.append("</tbody></table>");
        
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
        return mailtext.toString() ; 
	  }
}
