package com.Over2Cloud.ctrl.dar.helper;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.dar.task.TaskRegistrationHelper;

public class DarHelper
{
	public String getMailDetail(HttpServletRequest request,String Nameallotedby ,String Nameallotedto,String Nameguidance,String str,String taskType, String task, String specificTask, String clientFor, String clientData, String offering, String priority) throws UnknownHostException 
	{
		 StringBuffer mailText = new StringBuffer("");
		// HttpServletRequest req = ServletActionContext.getRequest();
	    // String ip=InetAddress.getLocalHost().getHostAddress();
	     //int portNo=req.getServerPort();
	    // String URL="<a href=http://"+ip+":"+portNo+"/over2cloud>Click Here!!</a>";
	     String URL="<a href=http://over2cloud.co.in>Click Here!!</a>";
		
		 if(str.equalsIgnoreCase("allotto"))
		 {
		     mailText.append("<table><tr><b>Dear "+ Nameallotedto+",</b></tr>");
	    	 mailText.append("<tr>Hello!</tr>");
			 mailText.append("<tr>Following Project has been alloted to you.</tr>");
		     mailText.append("</table>");
		 }
		 else if(str.equalsIgnoreCase("allotby"))
		 {
			 mailText.append("<table><tr><b>Dear "+ Nameallotedby+",</b></tr>");
	    	 mailText.append("<tr>Hello!</tr>");
			 mailText.append("<tr>Following Project has been registed by you succesfully.</tr>");
		     mailText.append("</table>");
		 }
		 else if(str.equalsIgnoreCase("under"))
		 {
			 mailText.append("<table><tr><b>Dear "+ Nameguidance+",</b></tr>");
	    	 mailText.append("<tr>Hello!</tr>");
			 mailText.append("<tr>Following Project has been alloted to you <b>As co-owner</b>.</tr>");
		     mailText.append("</table>");
		 }
		mailText.append("<br>");
 		mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Project Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ task+ "</td></tr>");
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ taskType+ "</td></tr>");    	
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Specific Task:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ specificTask+ "</td></tr>");
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Priority:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ priority+ "</td></tr>");
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> For:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ clientFor+ "</td></tr>");   
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ clientData+ "</td></tr>");   
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Offering:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ offering+ "</td></tr>");   
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Alloted To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ Nameallotedto+ "</td></tr>");
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Co-Owner:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ Nameguidance+ "</td></tr>"); 
 		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Alloted By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ Nameallotedby+ "</td></tr>");    	
		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Initiation Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ request.getParameter("intiation")+ "</td></tr>");  
		if (request.getParameter("functional_Date")!=null) 
		{
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next Functional Review:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ request.getParameter("functional_Date")+ "</td></tr>");
		}
		else 
		{
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next Functional Review:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>No Functional Review Required</td></tr>");
		}
		if (request.getParameter("technical_Date")!=null) 
		{
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next Technical Review:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ request.getParameter("technical_Date")+ "</td></tr>");
		} 
		else 
		{
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next Technical Review:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>No Technical Review Required</td></tr>");
		}
		mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Completion Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ request.getParameter("completion")+ "</td></tr>");
		mailText.append("</table>");
	    mailText.append("<br>");
	    mailText.append("<table>");
	    mailText.append("<tr> Project work flow (If any) is attached for your reference.</tr>");
	    if (str.equalsIgnoreCase("allotto")) 
	    {
	    	mailText.append("<tr> Kindly "+URL+" & Login to submit your daily activity of the project.</tr> ");
	 	    mailText.append("<tr> Please remember to complete your project before due date to avoid escalation & maintain your productivity score .</tr> ");
	 	    mailText.append("<tr> Wish you All The Best !!!!! </tr> ");
	    }
	    else if(str.equalsIgnoreCase("allotby"))
	    {
	    	mailText.append("<tr> Kindly "+URL+" & Login to check the daily progress of the project.</tr> ");
	    }
	    mailText.append("</table>");
	    mailText.append("<br>");
	    mailText.append("<table>");
	    mailText.append("<tr><b>Thanks & Regards,</b> </tr>");
	    mailText.append("<tr><b>Projects Team</b> </tr>");
	    mailText.append("<tr><b>DreamSol TeleSolutions Pvt. Ltd.</b> </tr>");
	    mailText.append("</table>");
        mailText.append("<br>");
	    mailText.append("<br><div align='justify'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</div>");
	
		  return mailText.toString();
	 }

	public String validationDAR(String name,String taskName,String status ,String reason1,String reason2 ,String status1,String status2)
	{
		 StringBuffer mailText = new StringBuffer("");	
		 mailText.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><h3>Dear ,</h3></FONT></div>"); 
		 mailText.append("<div align='justify'><font face ='TIMESROMAN' size='2'><h3>"+name+" </h3></FONT></div>"); 
		 mailText.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'><h3>This mail is sent you because your work status is changed by Under Guidance/Project Management </h3></FONT></div>"); 
	     mailText.append("<br>");
		 mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
	     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Application Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+taskName + "</td></tr>");    	
	     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Your Work Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+status+ "</td></tr>");    	
	     mailText.append("</table>");
	     mailText.append("<br>");
	     mailText.append("<br>");
		 mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
	     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Changed Status By Project Management:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+status1 + "</td></tr>");    	
	     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Reason By Project Management :</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+reason1 + "</td></tr>");    	
	     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Changed Status By Under Guidance:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+status2 + "</td></tr>");    	
	     mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Reason By Under Guidance</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+reason2 + "</td></tr>");    	
		 mailText.append("</table>");
		
		 return mailText.toString();
	}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public  Map sortByComparator(Map unsortMap) 
		{
			 
			List list = new LinkedList(unsortMap.entrySet());
			// sort list based on comparator
			Collections.sort(list, new Comparator() 
			{
				public int compare(Object o1, Object o2) 
				{
					return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
				}
			}
			);
	 
			// put sorted list into map again
	                //LinkedHashMap make sure order in which keys were inserted
			Map sortedMap = new LinkedHashMap();
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				sortedMap.put(entry.getKey(), entry.getValue());
			}
			return sortedMap;
		}
		
		@SuppressWarnings("rawtypes")
		public List<DarSubmissionPojoBean> getAllAgeingDetails(SessionFactory connectionSpace,String contactId,String fromDate) 
		{
			String query=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<DarSubmissionPojoBean> list=new ArrayList<DarSubmissionPojoBean>();
			DarSubmissionPojoBean dsp=null;
			try 
			{
				 String[] activity ={"Due For Completion","Due For Validation","Due For Functional Review","Due For Technical Review","New Projects "};
				 for (String a : activity) 
				 {
					 dsp=new DarSubmissionPojoBean();
					 dsp.setStatuss(a);
					 if (a.equalsIgnoreCase("Due For Completion") || a.equalsIgnoreCase("Due For Validation")) 
					 {
						 if (a.equalsIgnoreCase("Due For Validation")) 
						 {
							fromDate=DateUtil.getNewDate("day", 1, fromDate);
						 }
						 query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND completion='"+fromDate+"'";
						// System.out.println("SECOND 1111111111  "+query);
						 List todayList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (todayList!=null && todayList.size()>0) 
					     {
					    	 dsp.setToday(todayList.get(0).toString());
						 }
					     query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND completion='"+DateUtil.getNewDate("day", 1,fromDate)+"'";
					    // System.out.println("SECOND 2222222222222  "+query);
					     List tommorowList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (tommorowList!=null && tommorowList.size()>0) 
					     {
					    	 dsp.setTommorow(tommorowList.get(0).toString());
						 }
					     query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND completion BETWEEN  '"+fromDate+"'  AND '"+DateUtil.getNewDate("day", 7,fromDate)+"'";
					    // System.out.println("SECOND 44444444444  "+query);
					     List thisweekList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (thisweekList!=null && thisweekList.size()>0) 
					     {
					    	 dsp.setThisWeek(thisweekList.get(0).toString());
						 }
					     query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND completion BETWEEN  '"+fromDate+"'  AND '"+DateUtil.getNewDate("day", 30,fromDate)+"'";
					    // System.out.println("SECOND 5555555555  "+query);
					     List thismonthList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (thismonthList!=null && thismonthList.size()>0) 
					     {
					    	 dsp.setThisMonth(thismonthList.get(0).toString());
						 }
					 }
					 else if(a.equalsIgnoreCase("Due For Functional Review") || a.equalsIgnoreCase("Due For Technical Review"))
					 {
						 fromDate=DateUtil.getCurrentDateUSFormat();
						 String colName=null;
						 if (a.equalsIgnoreCase("Due For Functional Review") ) 
						 {
							 colName="functional_Date";
						 }
						 else 
						 {
							 colName="technical_Date";
						 }
						 query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND "+colName+"='"+fromDate+"'";
						// System.out.println("SECOND "+colName+" ::::111111111:::: "+query);
						 List todayList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (todayList!=null && todayList.size()>0) 
					     {
					    	 dsp.setToday(todayList.get(0).toString());
						 }
					     query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND "+colName+"='"+DateUtil.getNewDate("day", 1,fromDate)+"'";
					    // System.out.println("SECOND "+colName+" ::::222222222:::: "+query);
					     List tommorowList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (tommorowList!=null && tommorowList.size()>0) 
					     {
					    	 dsp.setTommorow(tommorowList.get(0).toString());
						 }
					     query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND "+colName+" BETWEEN  '"+fromDate+"'  AND '"+DateUtil.getNewDate("day", 7,fromDate)+"'";
					   //  System.out.println("SECOND "+colName+" ::::333333333:::: "+query);
					     List thisweekList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (thisweekList!=null && thisweekList.size()>0) 
					     {
					    	 dsp.setThisWeek(thisweekList.get(0).toString());
						 }
					     query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND "+colName+" BETWEEN  '"+fromDate+"'  AND '"+DateUtil.getNewDate("day", 30,fromDate)+"'";
					   //  System.out.println("SECOND "+colName+" ::::4444444444:::: "+query);
					     List thismonthList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (thismonthList!=null && thismonthList.size()>0) 
					     {
					    	 dsp.setThisMonth(thismonthList.get(0).toString());
						 } 
					 }
					 else if (a.equalsIgnoreCase("New Projects ")) 
					 {
						 fromDate=DateUtil.getCurrentDateUSFormat();
						 query="SELECT count(*) FROM task_registration WHERE allotedto IN("+contactId+") AND date='"+fromDate+"'";
						// System.out.println("SECOND New Projects  "+query);
						 List todayList=cbt.executeAllSelectQuery(query, connectionSpace);
					     if (todayList!=null && todayList.size()>0) 
					     {
					    	 dsp.setToday(todayList.get(0).toString());
						 }
					 }
					 list.add(dsp);
				 }
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}		
			return list;
	    }
		@SuppressWarnings("rawtypes")
		public List<DarSubmissionPojoBean> getRunningProjectDetails(SessionFactory connectionSpace,String contactId) 
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<DarSubmissionPojoBean> list=new ArrayList<DarSubmissionPojoBean>();
			DarSubmissionPojoBean dsp;
			 try 
			 {
			    
				 StringBuilder query= new StringBuilder();
				 query.append("SELECT tr.taskname,ty.tasktype,tr.priority,emp1.empName as allotedto,emp2.empName as allotedby,tr.clientFor,tr.cName,tr.offering, ");
				 query.append(" tr.intiation,tr.completion,dr.tactionstatus,dr.tommorow,tr.status FROM task_registration AS tr ");
				 query.append(" LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id  ");
				 query.append(" LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id  ");
				 query.append(" LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
				 query.append(" LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id   ");
				 query.append(" LEFT JOIN dar_submission as dr ON dr.taskname=tr.id  ");
				 query.append(" LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
				 query.append(" WHERE tr.completion='"+DateUtil.getCurrentDateUSFormat()+"' AND allotedto IN ("+contactId+") ");
				// System.out.println("THIRD ::::111111111:::: "+query.toString());
				 
				 List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				 if (data!=null && data.size()>0) 
				 {
					 DARReportHelper DRH=new DARReportHelper();
					 TaskRegistrationHelper TRH=new TaskRegistrationHelper();
					 String clientVal=null;
					 String clientData=null;
					 for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					 {
						dsp=new DarSubmissionPojoBean();
						Object[] object = (Object[]) iterator.next();
						dsp.setTasknameee(TRH.getValueWithNullCheck(object[0]));
						dsp.setTaskType(TRH.getValueWithNullCheck(object[1]));
						dsp.setPriority(TRH.getValueWithNullCheck(object[2]));
						dsp.setAllotedtoo(TRH.getValueWithNullCheck(object[3]));
						dsp.setAllotedbyy(TRH.getValueWithNullCheck(object[4]));
						clientVal=object[5].toString();
						if (object[5].toString().equalsIgnoreCase("PA")) 
						{
							dsp.setClientFor("Prospect Associate");
						}
						else if (object[5].toString().equalsIgnoreCase("PC")) 
						{
							dsp.setClientFor("Prospect Client");
						}
						else if (object[5].toString().equalsIgnoreCase("EC")) 
						{
							dsp.setClientFor("Existing Client");
						}
						else if (object[5].toString().equalsIgnoreCase("EA")) 
						{
							dsp.setClientFor("Existing Associate");
						}
						else if (object[5].toString().equalsIgnoreCase("N")) 
						{
							dsp.setClientFor("New");
						}
						else if (object[5].toString().equalsIgnoreCase("IN")) 
						{
							dsp.setClientFor("Internal");
						}
						clientData=object[6].toString();
						dsp.setClientName(TRH.getValueWithNullCheck(object[6]));
						dsp.setOfferingName(DRH.offeringName(clientVal,clientData ,object[7].toString(),connectionSpace));
						dsp.setInitiondate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[8])));
						dsp.setComlpetiondate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[9])));
						dsp.setToday(TRH.getValueWithNullCheck(object[10]));
						dsp.setTommorow(TRH.getValueWithNullCheck(object[11]));
						if (!TRH.getValueWithNullCheck(object[12]).equalsIgnoreCase("Done")) 
						{
							dsp.setStatuss("Running");
						}
						else if(TRH.getValueWithNullCheck(object[12]).equalsIgnoreCase("Snooze"))
						{
							dsp.setStatuss("Snooze");
						}
						else if(TRH.getValueWithNullCheck(object[12]).equalsIgnoreCase("Done"))
						{
							dsp.setStatuss("Completed");
						}
						list.add(dsp);
					 }
				 }
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}		
			return list;
	     }
		
		public String getMailContentForDar(String subject, HttpServletRequest request,String initiation,String completion, String taskType, String specificTask, String allotedto, String allotedby, String str, String underguidance) 
		{
			 StringBuffer mailText = new StringBuffer("");
			try 
			{
				 if (str!=null && str.equalsIgnoreCase("allotedTo")) 
				 {
					mailText.append("<table><tr><b>Dear "+allotedto+"</b></tr>");
					mailText.append("<tr>Hello !</tr>");
					mailText.append("<tr>Kindly find the project progress report by you.</tr>");
					mailText.append("</table>");
				 }
				 else if(str!=null && str.equalsIgnoreCase("allotedBy"))
				 {
				    mailText.append("<table><b><tr>Dear "+allotedby+"</b></tr>");
					mailText.append("<tr>Hello !</tr>");
					mailText.append("<tr>Kindly find the project progress report filled by "+allotedto+".</tr>");
					mailText.append("</table>");
				 }
				 else if(str!=null && str.equalsIgnoreCase("guidance"))
				 {
				    mailText.append("<table><tr><b>Dear "+underguidance+"</b></tr>");
					mailText.append("<tr>Hello !</tr>");
					mailText.append("<tr>Kindly find the project progress report filled by "+allotedto+" And You are co-owner.</tr>");
					mailText.append("</table>");
				 }
				 else if(str!=null && str.equalsIgnoreCase("dar"))
				 {
				    mailText.append("<table>");
					mailText.append("<tr>Hello !</tr>");
					mailText.append("<tr>Kindly find the project progress report filled by "+allotedto+".</tr>");
					mailText.append("</table>");
				 }
				 String splitarr[]=completion.split(" ");
				 
				 mailText.append("<br>");
				 mailText.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");		
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Project Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+subject+ "</td></tr>");
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Task Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+taskType+ "</td></tr>");
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Specific Task:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+specificTask+ "</td></tr>");
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Initiation Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(initiation)+ "</td></tr>");
				 if (splitarr.length>1) 
				 {
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Completion Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(splitarr[0])+" "+splitarr[1]+ "</td></tr>");
				} else {
					mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Completion Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(completion)+ "</td></tr>");
				}
				 
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Status:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("pstatus")+ "</td></tr>");
				 
				 if(request.getParameter("pstatus").equalsIgnoreCase("Snooze"))
				 {
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("snoozeDate")+ "</td></tr>");
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("snoozeDate")+ "</td></tr>");
				 }
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Today Task:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("tactionStatus")+ "</td></tr>");
				 if (request.getParameter("challenges")!=null && !request.getParameter("challenges").equalsIgnoreCase("")) 
				 {
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Challenges Phase:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("challenges")+ "</td></tr>");
				 }
				 else 
				 {
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Challenges Phase:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>NA</td></tr>");
				 }
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Completion (%):</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("compercentage")+ "</td></tr>");
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Tommorow Plan:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("tommorow")+ "</td></tr>");
				 if (request.getParameter("otherWork")!=null && !request.getParameter("otherWork").equalsIgnoreCase("")) 
				 {
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Other Work:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("otherWork")+ "</td></tr>");
				 }
				 else 
				 {
					 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Other Work:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>NA</td></tr>");
				 }
				 
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Man Hour:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("manhour")+ "</td></tr>");
				 mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Cost:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+request.getParameter("cost")+ "</td></tr>");
				 mailText.append("</table>");
				 mailText.append("<br>");
				 mailText.append("<table>");
				 mailText.append("<tr><b> Thanks & Regards,</b> </tr>");
			     mailText.append("<tr><b> Projects Team</b> </tr>");
			     mailText.append("<tr><b> DreamSol TeleSolutions Pvt. Ltd.</b> </tr>");
			     mailText.append("</table>");
			     mailText.append("<br>");
				 mailText.append("<br><div align='justify'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</div>");
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			 return mailText.toString();
		}

		@SuppressWarnings("rawtypes")
		public List<DarSubmissionPojoBean> getProjectStatusBetweenMonth(SessionFactory connectionSpace, String contactId,String fromDate,String toDate) 
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<DarSubmissionPojoBean> list=new ArrayList<DarSubmissionPojoBean>();
			DarSubmissionPojoBean CDB;
			try 
			{
				 CDB=new DarSubmissionPojoBean();
		    	 String query="SELECT completion,status FROM task_registration WHERE allotedto IN("+contactId+") AND date BETWEEN '"+fromDate+"' AND '"+toDate+"'";
		    	// System.out.println("FIRST QUERY IS AS :::;   "+query);
		    	 
		    	 List data=cbt.executeAllSelectQuery(query, connectionSpace);
		    	 if (data!=null && data.size()>0) 
		    	 {
		    		 int onGoing=0,complete=0,snooze=0,missed=0;
		    		 for (Iterator iterator = data.iterator(); iterator.hasNext();) 
		    		 {
		    			
						Object[] object = (Object[]) iterator.next();
						String strDue = object[0].toString() + " 00:00";
						String strCurrent = DateUtil.getCurrentDateUSFormat() + " 00:00";
						System.out.println(">>>>>>>>>  strDue "+strDue);
						System.out.println("strCurrent   "+strCurrent);
						boolean status = DateUtil.compareDateTime(strDue, strCurrent);
						if (status && !object[1].toString().equalsIgnoreCase("Done")) 
						{
							missed=1;
						}
						else if (!status && !object[1].toString().equalsIgnoreCase("Done") && !object[1].toString().equalsIgnoreCase("Snooze")) 
						{
							onGoing=1;
						}
						else if (!status  && !object[1].toString().equalsIgnoreCase("Snooze")) 
						{
							snooze=1;
						}
						else if (!status  && object[1].toString().equalsIgnoreCase("Done")) 
						{
							complete=1;
						}
						if (missed > 0)
						{
							CDB.setMissed(String.valueOf(Integer.parseInt(CDB.getMissed()) + missed));
						}
						if (onGoing > 0)
						{
							CDB.setPending(String.valueOf(Integer.parseInt(CDB.getPending()) + onGoing));
						}
						if (complete > 0)
						{
							CDB.setTdone(String.valueOf(Integer.parseInt(CDB.getTdone()) + complete));
						}
						if (snooze > 0)
						{
							CDB.setSnooze(String.valueOf(Integer.parseInt(CDB.getSnooze()) + snooze));
						}
						onGoing=0;
						complete=0;
						snooze=0;
						missed=0;
					}
				 }
		    	 list.add(CDB);
			 }
		     catch (Exception e) 
		     {
		    	 e.printStackTrace();
		     }		
			return list;
	}
	
}


