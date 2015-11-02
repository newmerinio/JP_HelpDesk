package com.Over2Cloud.ctrl.offeringComplaint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class ComplaintLodgeHelper 
{
	@SuppressWarnings("rawtypes")
	public List getEmp4Escalation(String dept_subDept,String module, String level, SessionFactory connectionSpace) 
	{
		List empList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			query.append("SELECT distinct contacts.id, emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName from employee_basic as emp ");
			query.append(" INNER JOIN compliance_contacts contacts on contacts.emp_id = emp.id ");
			query.append(" INNER JOIN department dept on contacts.forDept_id = dept.id ");
			query.append(" WHERE contacts.moduleName='"+module+"'");
			query.append(" AND dept.id='"+ dept_subDept+ "' ");
			if (level!=null && !level.equalsIgnoreCase(""))
            {
	            query.append("AND contacts.level='"+level+"'  ");
            }
			//System.out.println("QUERY ISA SAS   "+query);
			empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
	
	@SuppressWarnings("rawtypes")
    public List getFeedbackDetail(String feedID,String status, SessionFactory connectionSpace)
    {
           List list=new ArrayList();
           StringBuilder query = new StringBuilder("");
           try
           {
           	
           		query.append("SELECT feedback.ticket_no,emp.empName as allotto,feedtype.fbType,catg.categoryName, ");
           		query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time, ");
           		query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time, ");
           		query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time, ");
           		query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration, ");
           		query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason, ");
           		query.append("feedback.action_by,dept1.deptName as bydept,feedback.id as feedid, ");
           		query.append("emp.mobOne as allotto_mobno,emp.emailIdOne  as allotto_emailid,feedback.feedbackTo,feedback.feedbackCC  ");
           		query.append(" ,feedback.clientfor,feedback.clientName,feedback.offering ");
           		if (status.equalsIgnoreCase("Resolved")) 
           		{
           			query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark,feedback.spare_used, emp1.empName as resolve_by, emp1.mobOne as resolve_by_mobno, emp1.emailIdOne as resolve_by_emailid");
				}
           		
           		query.append(" FROM complaint_status as feedback ");
           		query.append(" LEFT  JOIN compliance_contacts as cc on feedback.allot_to= cc.id ");
           		query.append(" LEFT  JOIN employee_basic emp on cc.emp_id=emp.id ");
           		query.append(" LEFT  JOIN compliance_contacts as cc1 on feedback.resolve_by= cc1.id ");
           		query.append(" LEFT  JOIN employee_basic emp1 on cc1.emp_id=emp1.id ");
           		query.append(" LEFT  JOIN department dept1 on feedback.forDept= dept1.id ");
           		query.append(" LEFT  JOIN feedback_subcategory subcatg on feedback.subCategory = subcatg.id"); 	
           		query.append(" LEFT  JOIN feedback_category catg on subcatg.categoryName = catg.id"); 	
           		query.append(" LEFT  JOIN feedback_type feedtype on catg.fbType = feedtype.id"); 
           	
           		query.append(" WHERE feedback.status='"+status+"' "); 
           		if (feedID!=null && !feedID.equalsIgnoreCase(""))
                {
           			query.append(" AND feedback.id="+feedID);
                }
           		else
           		{
           			query.append(" AND feedback.id=(SELECT max(id) FROM complaint_status)");
           		}
				System.out.println("QUERY FOR COMPLIANT DETAILS  ::  "+query.toString());
                   list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
           }
           catch (Exception e)
           {
                   e.printStackTrace();
           }
           return list;
   }
	@SuppressWarnings("rawtypes")
	public FeedbackPojo setFeedbackDataValues(List data, String status,SessionFactory connectionSpace)
	{
		FeedbackPojo fbp =null;
		if (data!=null && data.size()>0) 
		{
			DARReportHelper DRH=new DARReportHelper();
			for (Iterator iterator = data.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				fbp.setTicket_no(object[0].toString());
				fbp.setFeedback_allot_to(object[1].toString());
				fbp.setFeedtype(object[2].toString());
				fbp.setFeedback_catg(object[3].toString());
				fbp.setFeedback_subcatg(object[4].toString());
				fbp.setFeed_brief(object[5].toString());
				fbp.setFeedaddressing_time(object[6].toString());
				fbp.setLocation(object[8].toString());
				fbp.setOpen_date(object[9].toString());
				fbp.setOpen_time(object[10].toString());
				fbp.setEscalation_date(object[11].toString());
				fbp.setEscalation_time(object[12].toString());
				fbp.setLevel(object[13].toString());
				fbp.setStatus(object[14].toString());
				fbp.setVia_from(object[15].toString());
				
				if (status.equals("High Priority")) {
				fbp.setHp_date(object[16].toString());	
				fbp.setHp_time(object[17].toString());	
				fbp.setHp_reason(object[18].toString());	
				}
				else if (status.equals("Snooze")) {
			    fbp.setSn_reason(object[19].toString());
			    fbp.setSn_on_date(object[20].toString());
			    fbp.setSn_on_time(object[21].toString());
			    fbp.setSn_date(object[22].toString());
			    fbp.setSn_time(object[23].toString());
			    fbp.setSn_duration(object[24].toString());
				}
                else if (status.equals("Ignore")) {
                fbp.setIg_date(object[25].toString());
     			fbp.setIg_time(object[26].toString());
     			fbp.setIg_reason(object[27].toString());
				}
                else if (object[28]!=null && !object[28].toString().equals("") && object[29]!=null && !object[29].toString().equals("")) 
                {
                fbp.setTransfer_date(object[28].toString());
         		fbp.setTransfer_time(object[29].toString());
         		fbp.setTransfer_reason(object[30].toString());	
				}
				
                else if (status.equals("Resolved")) 
                {
	                fbp.setResolve_date(object[41].toString());
	                fbp.setResolve_time(object[42].toString());
	                fbp.setResolve_duration(object[43].toString());
	                fbp.setResolve_remark(object[44].toString());
	                fbp.setSpare_used(object[45].toString());
	                fbp.setResolve_by(object[46].toString());
	                if (object[47]!=null && !object[47].toString().equals("")) {
						fbp.setResolve_by_mobno(object[47].toString());
					} else {
						fbp.setResolve_by_mobno("NA");
					}
	                if (object[48]!=null && !object[48].toString().equals("")) {
						fbp.setResolve_by_emailid(object[48].toString());
					} else {
						fbp.setResolve_by_emailid("NA");
					}
				}
				if (object[31]!=null && !object[31].toString().equals("")) {
					fbp.setAction_by(object[31].toString());	
				}
				else {
					fbp.setAction_by("NA");
				}
				
				if (object[32]!=null && !object[32].toString().equals("")) {
					fbp.setFeedback_to_dept(object[32].toString());	
				}
				else {
					fbp.setFeedback_to_dept("NA");
				}
				
				if (object[33]!=null && !object[33].toString().equals("")) {
					fbp.setFeedId(object[33].toString());	
				}
				else {
					fbp.setFeedId("NA");
				}
				
				if (object[34]!=null && !object[34].toString().equals("")) {
					fbp.setMobOne(object[34].toString());	
				}
				else {
					fbp.setMobOne("NA");
				}
				if (object[35]!=null && !object[35].toString().equals("")) {
					fbp.setEmailIdOne(object[35].toString());
				} else {
					fbp.setEmailIdOne("NA");
				}
				   String clientfor=null;
				   
					if (object[38].toString()!=null && !object[38].toString().equalsIgnoreCase("") && object[38].toString().equalsIgnoreCase("PC")) 
					{
						clientfor="Prospect Client";
					}
					else if (object[38].toString()!=null && !object[38].toString().equalsIgnoreCase("") && object[38].toString().equalsIgnoreCase("EC")) 
					{
						clientfor="Existing Client";
					}
					else if (object[38].toString()!=null && !object[38].toString().equalsIgnoreCase("") && object[38].toString().equalsIgnoreCase("PA")) 
					{
						clientfor="Prospect Associate";
					}
					else if (object[38].toString()!=null && !object[38].toString().equalsIgnoreCase("") && object[38].toString().equalsIgnoreCase("EA")) 
					{
						clientfor="Existing Associate";
					}
					else if (object[38].toString()!=null && !object[38].toString().equalsIgnoreCase("") && object[38].toString().equalsIgnoreCase("IN")) 
					{
						clientfor="Internal";
					}
				 fbp.setClientFor(clientfor);
				 String cName = DRH.clientName(object[38].toString(), object[39].toString(), connectionSpace);
				 String offeringName = DRH.offeringName(object[38].toString(), object[39].toString(), object[40].toString(), connectionSpace);
				 fbp.setClientName(cName);
				 fbp.setOfferingName(offeringName);
				 String[] feed_to=getFeedbackToData(object[38].toString(),object[36].toString(),connectionSpace);
				 if (object[36]!=null && !object[36].toString().equals("")) 
				 {
					fbp.setFeed_by(feed_to[0]);
				 }
				 else {
					 fbp.setFeed_by("NA");
				}
				if (feed_to[1]!=null)
                {
	                fbp.setFeedback_by_mobno(feed_to[1]);
                }
				if (feed_to[2]!=null)
                {
	                fbp.setFeedback_by_emailid(feed_to[2]);
                }
				String[] feedCC=object[37].toString().split("#");
				StringBuilder feed_cc =new StringBuilder();
				for (String s : feedCC)
				{
					feed_to=getFeedbackToData(object[38].toString(),s,connectionSpace);
					feed_cc.append(feed_to[0]+", ");
				}
				if (object[37]!=null && !object[37].toString().equals("")) 
				{
					fbp.setFeed_cc(feed_cc.toString().substring(0,feed_cc.toString().length()-1));
			    }
				else 
				{
					 fbp.setFeed_cc("NA");
				}
			}
		}
		return fbp;
	}
	
	@SuppressWarnings("rawtypes")
	public String getConfigMessage(FeedbackPojo feedbackObj,String title,String status,boolean flag,String clientFor,String clientName,String offeringName) 
	{
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String orgName="";
			if (orgData!=null && orgData.size()>0) 
			{
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:20px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) 
	    {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) 
	    {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_by())+ ",</b>");	
		}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) 
	    {
	    	mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag)
	    {
	    	mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
		}
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    if (flag)
        {
	    	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Client&nbsp;For:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ clientFor+ "</td></tr>");
        }
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Client&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ clientName+"</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Offering&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ offeringName+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getOpen_date())+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_by() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;CC:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_cc() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
        if (!flag)
        {
        	 mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Allot&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_allot_to()+ "</td></tr>"); 
        }
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedtype()+ "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
		if (flag) 
		{
		   if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) 
		   {
		      mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat( feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		   }
		}
		else
		{
		   if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) 
		   {
		      mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve&nbsp;Up&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		   }
		}
		if (status.equalsIgnoreCase("High Priority")) 
		{
			mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> High Priority Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getHp_reason() + " </td></tr>");
		}
		else if (status.equalsIgnoreCase("Snooze")) 
		{
			mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getSn_date()) + "</td></tr>");
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_time() + " Hrs</td></tr>");
			mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Ignore")) 
        {
        	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ignore Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getIg_reason() + "</td></tr>");
		}
        else if (status.equalsIgnoreCase("Resolved")) 
        {
	        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> TAT:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_duration() + "</td></tr>");
	        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Spare Used:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve Remark:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
        }
			mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
			mailtext.append("<table><tr><font face ='verdana' size='2'><br>Support Team</FONT></tr></table>");
			mailtext.append("<table><tr><font face ='verdana' size='2'><br>DreamSol TeleSolutions Pvt.Ltd.</FONT></tr></table>");
			mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
			return mailtext.toString();
	}
	@SuppressWarnings("rawtypes")
    public String[] getEmpDetailsByUserName(String moduleName,String userName, SessionFactory connectionSpace)
	{
		String[] values =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"+moduleName+"' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id");
			//System.out.println("Common Helper Class "+query.toString());
			List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				values=new String[7];
				Object[] object=(Object[])dataList.get(0);
				values[0]=getValueWithNullCheck(object[0]);
				values[1]=getValueWithNullCheck(object[1]);
				values[2]=getValueWithNullCheck(object[2]);
				values[3]=getValueWithNullCheck(object[3]);
				values[4]=getValueWithNullCheck(object[4]);
				values[5]=getValueWithNullCheck(object[5]);
				values[6]=getValueWithNullCheck(object[6]);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List getFeedbackDetail(String table, String status,String fromDate,String toDate,String deptid, String feedID, String orderField,String order,String module, String searchField, String searchString,String searchOper, SessionFactory connectionSpace) 
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
		
				query.append("SELECT feedback.id,feedback.ticket_no,dept1.deptName as deptFor,feedback.clientfor,feedback.clientName,");
				query.append(" feedback.offering,feedback.feedbackTo,feedback.feedbackCC,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,emp.empName, ");
				query.append(" subcatg.addressingTime,subcatg.escalateTime as esc_time,  ");
				query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by ");
				if (status.equalsIgnoreCase("Resolved")) 
				{
					query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used,rca.rcaBrief,feedback.resolve_capa ");
				}
				query.append(" from "+table+" as feedback");
				query.append(" LEFT JOIN compliance_contacts as cc ON cc.id=feedback.allot_to");
				query.append(" LEFT join employee_basic emp on cc.emp_id= emp.id");
				query.append(" LEFT join department dept1 on feedback.forDept= dept1.id");
				query.append(" LEFT JOIN feedback_subcategory subcatg on feedback.subCategory = subcatg.id");
				query.append(" LEFT JOIN feedback_category catg on subcatg.categoryName = catg.id");
				query.append(" LEFT JOIN feedback_type feedtype on catg.fbType = feedtype.id");
				if (status.equalsIgnoreCase("Resolved")) 
				{
					query.append(" LEFT JOIN compliance_contacts as cc1 ON cc1.id=feedback.resolve_by LEFT join employee_basic emp1 on cc1.emp_id= emp1.id ");
					query.append(" LEFT JOIN rca_master as rca ON rca.id=feedback.resolve_rca  ");
				}
				query.append(" where feedback.status='"+status+ "'");
				if (deptid!=null && !deptid.equalsIgnoreCase(""))
                {
					query.append(" and dept1.id='"+deptid+"'");
                }
			  
				if (searchField != null && searchString != null
						&& !searchField.equalsIgnoreCase("")
						&& !searchString.equalsIgnoreCase("")) {
					query.append(" and");

					if (searchOper.equalsIgnoreCase("eq")) {
						query.append(" " + searchField + " = '" + searchString
								+ "'");
					} else if (searchOper.equalsIgnoreCase("cn")) {
						query.append(" " + searchField + " like '%"
								+ searchString + "%'");
					} else if (searchOper.equalsIgnoreCase("bw")) {
						query.append(" " + searchField + " like '"
								+ searchString + "%'");
					} else if (searchOper.equalsIgnoreCase("ne")) {
						query.append(" " + searchField + " <> '" + searchString
								+ "'");
					} else if (searchOper.equalsIgnoreCase("ew")) {
						query.append(" " + searchField + " like '%"
								+ searchString + "'");
					}
				}
			query.append(" and feedback.moduleName='"+module+"'");
			query.append(" ORDER BY "+orderField+" " + order + "");
			
			System.out.println("QQQQQQ   :::::   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
    public List<FeedbackPojo> setFeedbackValues(List feedValue,  String feedStatus,SessionFactory connectionSpace)
	 {
		List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
		if (feedValue!=null && feedValue.size()>0)
		{
				DARReportHelper DRH=new DARReportHelper();
				for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) 
				{
					Object[] obdata = (Object[]) iterator.next();
					FeedbackPojo  fbp = new FeedbackPojo();
					 fbp.setId((Integer)obdata[0]);
					 fbp.setTicket_no(obdata[1].toString());
					 fbp.setFeedback_to_dept(obdata[2].toString());
					 
					    String clientfor=null;
					   
						if (obdata[3].toString()!=null && !obdata[3].toString().equalsIgnoreCase("") && obdata[3].toString().equalsIgnoreCase("PC")) 
						{
							clientfor="Prospect Client";
						}
						else if (obdata[3].toString()!=null && !obdata[3].toString().equalsIgnoreCase("") && obdata[3].toString().equalsIgnoreCase("EC")) 
						{
							clientfor="Existing Client";
						}
						else if (obdata[3].toString()!=null && !obdata[3].toString().equalsIgnoreCase("") && obdata[3].toString().equalsIgnoreCase("PA")) 
						{
							clientfor="Prospect Associate";
						}
						else if (obdata[3].toString()!=null && !obdata[3].toString().equalsIgnoreCase("") && obdata[3].toString().equalsIgnoreCase("EA")) 
						{
							clientfor="Existing Associate";
						}
						else if (obdata[3].toString()!=null && !obdata[3].toString().equalsIgnoreCase("") && obdata[3].toString().equalsIgnoreCase("IN")) 
						{
							clientfor="Internal";
						}
					 fbp.setClientFor(clientfor);
					 String cName = DRH.clientName(obdata[3].toString(), obdata[4].toString(), connectionSpace);
					 String offeringName=DRH.offeringName(obdata[3].toString(), obdata[4].toString(), obdata[5].toString(), connectionSpace);
					 fbp.setClientName(cName);
					 fbp.setOfferingName(offeringName);
					 String[] feed_to=getFeedbackToData(obdata[3].toString(),obdata[6].toString(),connectionSpace);
					 if (obdata[6]!=null && !obdata[6].toString().equals("")) {
						fbp.setFeed_by(feed_to[0]);
					}
					 else {
						 fbp.setFeed_by("NA");
					}
					String[] feedCC=obdata[7].toString().split("#");
					StringBuilder feed_cc =new StringBuilder();
					for (String s : feedCC)
                    {
						feed_to=getFeedbackToData(obdata[3].toString(),s,connectionSpace);
						feed_cc.append(feed_to[0]+",");
                    }
					if (obdata[7]!=null && !obdata[7].toString().equals("")) 
					{
							fbp.setFeed_cc(feed_cc.toString().substring(0,feed_cc.toString().length()-1));
				    }
					else {
							 fbp.setFeed_cc("NA");
					}
					
					fbp.setFeedtype(obdata[8].toString());
					fbp.setFeedback_catg(obdata[9].toString());
					fbp.setFeedback_subcatg(obdata[10].toString());
					if (obdata[11]!=null && !obdata[11].toString().equals("")) 
					{
						fbp.setFeed_brief(obdata[11].toString());
			        }
				    else 
				    {
				    	fbp.setFeed_brief("NA");
				    }
					fbp.setFeedback_allot_to(obdata[12].toString());
					fbp.setFeedaddressing_time(obdata[13].toString());
					
					if (obdata[13]!=null && !obdata[13].toString().equals("") && obdata[16]!=null && !obdata[16].toString().equals("") && obdata[17]!=null && !obdata[17].toString().equals("")) 
					{
						String addr_date_time = DateUtil.newdate_AfterTime(obdata[16].toString(), obdata[17].toString(), obdata[13].toString());
						String[] add_date_time = addr_date_time.split("#");
						for (int i = 0; i < add_date_time.length; i++) 
						{
							fbp.setFeedaddressing_date(DateUtil.convertDateToIndianFormat(add_date_time[0]));
							fbp.setFeedaddressing_time(add_date_time[1].substring(0, 5));
						}
					}
				    else {
				    	fbp.setFeedaddressing_date("NA");
				    	fbp.setFeedaddressing_time("NA");
				    }
					
					if (obdata[15]!=null && !obdata[15].toString().equals("")) {
						fbp.setLocation(obdata[15].toString());
			        }
				    else {
				    	fbp.setLocation("NA");
				    }
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[16].toString()));
					fbp.setOpen_time(obdata[17].toString().substring(0, 5));
					if (feedStatus.equalsIgnoreCase("Pending")) 
					{
						fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[18].toString()));
						if (obdata[19].toString()!=null && !obdata[19].toString().equals("") && !obdata[19].toString().equals("NA")) {
							fbp.setEscalation_time(obdata[19].toString().substring(0, 5));
						}
						else {
							fbp.setEscalation_time("NA");
						}
					}
				  
					fbp.setLevel(obdata[20].toString());
					fbp.setStatus(obdata[21].toString());
					if (obdata[22]!=null && !obdata[22].toString().equals("")) {
						fbp.setVia_from(DateUtil.makeTitle(obdata[22].toString()));
					}
					else {
						fbp.setVia_from("NA");
					}
					
					fbp.setFeed_registerby(DateUtil.makeTitle(obdata[23].toString()));
					
					 if (feedStatus.equalsIgnoreCase("High Priority")) 
					 {
						 fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[24].toString()));
						 fbp.setHp_time(obdata[25].toString().substring(0, 5));
						 fbp.setHp_reason(obdata[26].toString());
					}
					
					 if (feedStatus.equalsIgnoreCase("Snooze")) {
						 if (obdata[27]!=null && !obdata[27].toString().equals("")) {
							 fbp.setSn_reason(obdata[27].toString());
						}
						 else {
							 fbp.setSn_reason("NA");
						}
						 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
							 fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(obdata[28].toString()));
						}
						 else {
							 fbp.setSn_on_date("NA");
						}
						 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
							 fbp.setSn_on_time(obdata[29].toString());
						}
						 else {
							 fbp.setSn_on_time("NA");
						}
						 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
							 fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[30].toString()));
						}
						 else {
							 fbp.setSn_upto_date("NA");
						}
						 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
							 fbp.setSn_upto_time(obdata[31].toString().substring(0, 5));
						}
						 else {
							 fbp.setSn_upto_time("NA");
						} 
						 if (obdata[32]!=null && !obdata[32].toString().equals("")) {
							 fbp.setSn_duration(obdata[32].toString());
						}
						 else {
							 fbp.setSn_duration("NA");
						}  
						}
					
					 if (feedStatus.equalsIgnoreCase("Resolved")) 
					 {
						 if (obdata[40]!=null && !obdata[40].toString().equals("")) {
							 fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[40].toString()));
						 }
						 else {
							 fbp.setResolve_date("NA");
						}
						if (obdata[41]!=null && !obdata[41].toString().equals("")) {
							 fbp.setResolve_time(obdata[41].toString().substring(0, 5));
							}
						else {
								 fbp.setResolve_time("NA");
							 }
						
						 if (obdata[42]!=null && !obdata[42].equals("")) {
							 fbp.setResolve_duration(obdata[42].toString());
							  }
						 else {
								   if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("") && obdata[42]!=null && !obdata[42].toString().equals("")) {
									   String dura1=DateUtil.time_difference(obdata[16].toString(), obdata[17].toString(), obdata[40].toString(), obdata[41].toString());
									   fbp.setResolve_duration(dura1);
								   }
								   else {
									   fbp.setResolve_duration("NA");
								   }
							 }
						 
						 if (obdata[43]!=null && !obdata[43].toString().equals("")) {
							 fbp.setResolve_remark(obdata[43].toString());
						}
						 else {
							 fbp.setResolve_remark("NA");
						}
						 fbp.setResolve_by(obdata[44].toString());
						 fbp.setSpare_used(obdata[45].toString());
						 fbp.setResolve_rca(obdata[46].toString());
						 fbp.setResolve_capa(obdata[47].toString());
						}
					 if (!feedStatus.equalsIgnoreCase("") && !feedStatus.equalsIgnoreCase("Pending")) {
						 if (obdata[39]!=null && !obdata[39].toString().equals("")) {
							 fbp.setAction_by(obdata[39].toString());
						}
						 else {
							 fbp.setAction_by("NA");
						}
						}
					feedList.add(fbp);
				}
		}
		return feedList;
	 }

	@SuppressWarnings("rawtypes")
    public String[] getFeedbackToData(String clientFor, String feedbackTo,SessionFactory connectionSpace)
    {
	   String[] feedback=new String[3];
	   try
	    {
		   String query=null;
		   if (clientFor!=null && clientFor.equalsIgnoreCase("PC") || clientFor.equalsIgnoreCase("EC"))
		   {
	         query="SELECT personName,contactNo,emailOfficial FROM client_contact_data WHERE id ="+feedbackTo;
		   } 
		   else if (clientFor!=null && clientFor.equalsIgnoreCase("PA") || clientFor.equalsIgnoreCase("EA")) 
		   {
			   query="SELECT name,contactNum,emailOfficial FROM associate_contact_data WHERE id ="+feedbackTo;
		   }
		   else if (clientFor!=null && clientFor.equalsIgnoreCase("IN") ) 
		   {
			   query="SELECT emp.empName,emp.mobOne,emp.emailIdOne FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id= "+feedbackTo;
		   }
		   List data=new createTable().executeAllSelectQuery(query, connectionSpace);
		   System.out.println(">>>>>>>>>>   "+query);
		   if (data!=null && data.size()>0)
		   {
	          for (Iterator iterator = data.iterator(); iterator.hasNext();)
	          {
	            Object[] object = (Object[]) iterator.next();
	             feedback[0]=getValueWithNullCheck(object[0]);
	             feedback[1]=getValueWithNullCheck(object[1]);
	             feedback[2]=getValueWithNullCheck(object[2]);
	          }
		   }
	    }
	    catch (Exception e)
	    {
		    e.printStackTrace();
	    }
	    return feedback;
    }

	@SuppressWarnings("rawtypes")
    public String getTicketLevel(String allottoId,SessionFactory connectionSpace)
    {
		String level =null;
	   try
	   {
	      StringBuilder qry=new StringBuilder();
	      qry.append("SELECT cc.level FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ");
	      qry.append(" ON emp.id=cc.emp_id WHERE cc.id="+allottoId);
	      List list = new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
	      if (list!=null && list.size()>0)
          {
	    	  level="Level"+list.get(0).toString();
          }
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	    return level;
    }
	@SuppressWarnings("rawtypes")
    public String configMessage4Escalation(FeedbackPojo feedbackObj,String title,String escalateEmp,boolean flag) 
	{
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
		String orgName="";
			if (orgData!=null && orgData.size()>0) 
			{
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					orgName=object[0].toString();
				}
			}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<b>Dear " +DateUtil.makeTitle(escalateEmp)+ ",</b>");
	   
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
		mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	   
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_date()+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_by() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;CC:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_cc() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
		
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;Allot&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.makeTitle(feedbackObj.getFeedback_allot_to()) + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+feedbackObj.getEscalation_date()+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}

	@SuppressWarnings("rawtypes")
    public List getEmployeeData(String allot_to, String to_dept,String moduleName,SessionFactory connection)
    {
	    List data=new ArrayList<String>();
	    try
        {
	        StringBuilder query=new StringBuilder();
	        query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" where contact.moduleName='"+moduleName+"' and contact.id='");
			query.append(allot_to + "' and contact.forDept_id='"+to_dept+"'");
			System.out.println("QUERY ISA SS        "+query.toString());
			data=new createTable().executeAllSelectQuery(query.toString(), connection);
        }
        catch (Exception e)
        {
	       e.printStackTrace();
        }
	    return data;
    }
	
	@SuppressWarnings("rawtypes")
    public List<FeedbackPojo> setFeedbackDataforReport(List data,String status,String deptLevel, SessionFactory connectionSpace)
	{
		List<FeedbackPojo> fbpList = new ArrayList<FeedbackPojo>();
		FeedbackPojo fbp =null;
		if (data!=null && data.size()>0) {
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				
				if (object[0]!=null) {
					fbp.setTicket_no(object[0].toString());
				}
				else {
					fbp.setTicket_no("NA");
				}
				
				if (object[1]!=null) {
					fbp.setFeedback_to_dept(object[1].toString());
				}
				else {
					fbp.setFeedback_to_dept("NA");
				}
				
				if (object[2]!=null) 
				{
				    String clientfor=null;
					if (object[2].toString()!=null && !object[2].toString().equalsIgnoreCase("") && object[2].toString().equalsIgnoreCase("PC")) 
					{
						clientfor="Prospect Client";
					}
					else if (object[2].toString()!=null && !object[2].toString().equalsIgnoreCase("") && object[2].toString().equalsIgnoreCase("EC")) 
					{
						clientfor="Existing Client";
					}
					else if (object[2].toString()!=null && !object[2].toString().equalsIgnoreCase("") && object[2].toString().equalsIgnoreCase("PA")) 
					{
						clientfor="Prospect Associate";
					}
					else if (object[2].toString()!=null && !object[2].toString().equalsIgnoreCase("") && object[2].toString().equalsIgnoreCase("EA")) 
					{
						clientfor="Existing Associate";
					}
					else if (object[2].toString()!=null && !object[2].toString().equalsIgnoreCase("") && object[2].toString().equalsIgnoreCase("IN")) 
					{
						clientfor="Internal";
					}
					 fbp.setClientFor(clientfor);
					 
				}
				else {
					fbp.setClientFor("NA");
				}
				
				if (object[3]!=null) 
				{
					DARReportHelper DRH=new DARReportHelper();
					String cName = DRH.clientName(object[2].toString(), object[3].toString(), connectionSpace);
					fbp.setClientName(cName);
				}
				else {
					fbp.setClientName("NA");
				}
				
				if (object[4]!=null) 
				{
					DARReportHelper DRH=new DARReportHelper();
					String offeringName = DRH.offeringName(object[2].toString(), object[3].toString(), object[4].toString(), connectionSpace);
					fbp.setOfferingName(offeringName);
				}
				else {
					fbp.setOfferingName("NA");
				}
				
				if (object[5]!=null) {
					fbp.setFeedback_allot_to(object[5].toString());
				}
				else {
					fbp.setFeedback_allot_to("NA");
				}
				
				if (object[6]!=null) {
					fbp.setFeedtype(object[6].toString());
				}
				else {
					fbp.setFeedtype("NA");
				}
				
				if (object[7]!=null) {
					fbp.setFeedback_catg(object[7].toString());
				}
				else {
					fbp.setFeedback_catg("NA");
				}
				
				if (object[8]!=null) {
					fbp.setFeedback_subcatg(object[8].toString());
				}
				else {
					fbp.setFeedback_subcatg("NA");
				}
				
				if (object[9]!=null) {
					fbp.setFeed_brief(object[9].toString());
				}
				else {
					fbp.setFeed_brief("NA");
				}
				
				if (object[10]!=null) {
					fbp.setLocation(object[10].toString());
				}
				else {
					fbp.setLocation("NA");
				}
				
				if (object[11]!=null) {
					fbp.setOpen_date(DateUtil.convertDateToIndianFormat(object[11].toString()));
				}
				else {
					fbp.setOpen_date("NA");
				}
				
				if (object[12]!=null) {
					fbp.setOpen_time(object[12].toString().substring(0, 5));
				}
				else {
					fbp.setOpen_time("NA");
				}
				
				if (object[13]!=null) {
					fbp.setLevel(object[13].toString());
				}
				else {
					fbp.setLevel("NA");
				}
				
				if (object[14]!=null) {
					fbp.setStatus(object[14].toString());
				}
				else {
					fbp.setStatus("NA");
				}
				
				if (object[15]!=null) {
					 String[] feed_to=getFeedbackToData(object[2].toString(),object[15].toString(),connectionSpace);
					 if (object[15]!=null && !object[15].toString().equals("")) 
					 {
						fbp.setFeed_by(feed_to[0]);
					 }
					 else {
						 fbp.setFeed_by("NA");
					}
				}
				else {
					fbp.setFeed_by("NA");
				}
				
				if (object[16]!=null) {
					fbp.setFeed_registerby(object[16].toString());
				}
				else {
					fbp.setFeed_registerby("NA");
				}
				if (object[17]!=null) 
				{
					String[] feedCC=object[17].toString().split("#");
					StringBuilder feed_cc =new StringBuilder();
					for (String s : feedCC)
                    {
						String[] feed_to=getFeedbackToData(object[2].toString(),s,connectionSpace);
						feed_cc.append(feed_to[0]+",");
                    }
					if (object[17]!=null && !object[17].toString().equals("")) 
					{
						fbp.setFeed_cc(feed_cc.toString().substring(0,feed_cc.toString().length()-1));
				    }
					else 
					{
					    fbp.setFeed_cc("NA");
					}
				}
				else {
					fbp.setFeed_cc("NA");
				}
				if (status.equalsIgnoreCase("Resolved")) 
				{
					if (object[18]!=null) {
						fbp.setResolve_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
					}
					else {
						fbp.setResolve_date("NA");
					}
					
					if (object[19]!=null) {
						fbp.setResolve_time(object[19].toString().substring(0, 5));
					}
					else {
						fbp.setResolve_time("NA");
					}
					
					if (object[20]!=null) {
						fbp.setResolve_duration(object[20].toString());
					}
					else {
						fbp.setResolve_duration("NA");
					}
					
					if (object[21]!=null) {
						fbp.setResolve_remark(object[21].toString());
					}
					else {
						fbp.setResolve_remark("NA");
					}
					
					if (object[22]!=null) {
						fbp.setResolve_by(object[22].toString());
					}
					else {
						fbp.setResolve_by("NA");
					}
					
					if (object[23]!=null) {
						fbp.setSpare_used(object[23].toString());
					}
					else {
						fbp.setSpare_used("NA");
					}
					
					if (object[24]!=null) {
						fbp.setResolve_rca(object[24].toString());
					}
					else {
						fbp.setResolve_rca("NA");
					}
					if (object[25]!=null) {
						fbp.setResolve_capa(object[25].toString());
					}
					else {
						fbp.setResolve_capa("NA");
					}
				}
				if (status.equalsIgnoreCase("Snooze")) {
					if (object[18]!=null) {
						fbp.setSn_reason(object[18].toString());
					}
					else {
						fbp.setSn_reason("NA");
					}
					
					if (object[19]!=null && !object[19].toString().equals("") && !object[19].toString().equals("NA")) {
						fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));
					}
					else {
						fbp.setSn_on_date("NA");
					}
					
					if (object[20]!=null && !object[20].toString().equals("") && !object[20].toString().equals("NA")) {
						fbp.setSn_on_time(object[20].toString().substring(0, 5));
					}
					else {
						fbp.setSn_on_time("NA");
					}
					
					if (object[21]!=null && !object[21].toString().equals("") && !object[21].toString().equals("NA")) {
						fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
					}
					else {
						fbp.setSn_date("NA");
					}
					
					if (object[22]!=null && !object[22].toString().equals("") && !object[22].toString().equals("NA")) {
						fbp.setSn_time(object[22].toString().substring(0, 5));
					}
					else {
						fbp.setSn_time("NA");
					}
					
					if (object[23]!=null) {
						fbp.setSn_duration(object[23].toString());
					}
					else {
						fbp.setSn_duration("NA");
					}
				}
				if (status.equalsIgnoreCase("Ignore")) {
					if (object[18]!=null) {
						fbp.setIg_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
					}
					else {
						fbp.setIg_date("NA");
					}
					
					if (object[19]!=null) {
						fbp.setIg_time(object[19].toString().substring(0, 5));
					}
					else {
						fbp.setIg_time("NA");
					}
					
					if (object[20]!=null) {
						fbp.setIg_reason(object[20].toString());
					}
					else {
						fbp.setIg_reason("NA");
					}
				}
				if (status.equalsIgnoreCase("High Priority")) {
					if (object[18]!=null) {
						fbp.setHp_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
					}
					else {
						fbp.setHp_date("NA");
					}
					
					if (object[19]!=null) {
						fbp.setHp_time(object[19].toString().substring(0, 5));
					}
					else {
						fbp.setHp_time("NA");
					}
					
					if (object[20]!=null) {
						fbp.setHp_reason(object[20].toString());
					}
					else {
						fbp.setHp_reason("NA");
					}
				}
				
				if (deptLevel.equals("2") && (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Snooze"))) {
					if (object[24]!=null) {
						fbp.setFeedback_by_subdept(object[24].toString());
					}
					else {
						fbp.setFeedback_by_subdept("NA");
					}
					
					if (object[25]!=null) {
						fbp.setFeedback_to_subdept(object[25].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				}
				
				if (deptLevel.equals("2") && (status.equalsIgnoreCase("Resolved"))) {
					if (object[26]!=null) {
						fbp.setSn_reason(object[26].toString());
					}
					else {
						fbp.setSn_reason("NA");
					}
					
					if (object[27]!=null && !object[27].toString().equals("") && !object[27].toString().equals("NA")) {
						fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[27].toString()));
					}
					else {
						fbp.setSn_on_date("NA");
					}
					
					if (object[28]!=null && !object[28].toString().equals("") && !object[28].toString().equals("NA")) {
						fbp.setSn_on_time(object[28].toString().substring(0, 5));
					}
					else {
						fbp.setSn_on_time("NA");
					}
					
					if (object[29]!=null && !object[29].toString().equals("") && !object[29].toString().equals("NA")) {
						fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[29].toString()));
					}
					else {
						fbp.setSn_date("NA");
					}
					
					if (object[30]!=null && !object[30].toString().equals("") && !object[30].toString().equals("NA")) {
						fbp.setSn_time(object[30].toString().substring(0, 5));
					}
					else {
						fbp.setSn_time("NA");
					}
					
					if (object[31]!=null) {
						fbp.setSn_duration(object[31].toString());
					}
					else {
						fbp.setSn_duration("NA");
					}
					

    				if (object[32]!=null && !object[32].toString().equals("") && object[12]!=null && !object[12].toString().equals("") && object[13]!=null && !object[13].toString().equals("")) {
    					
    					fbp.setAddressingTime(DateUtil.newdate_AfterEscTimeOld(object[12].toString(), object[13].toString(), "00:00", object[32].toString()).substring(11));
    				}
    				else {
    					fbp.setAddressingTime("NA");
    				}
					
					if (object[33]!=null) {
						fbp.setEscalation_date(object[33].toString());
					}
					else {
						fbp.setEscalation_date("NA");
					}
					
					if (object[34]!=null) {
						fbp.setEscalation_time(object[34].toString());
					}
					else {
						fbp.setEscalation_time("NA");
					}
					
				}
				
				if (deptLevel.equals("2") && (status.equalsIgnoreCase("Ignore") || status.equalsIgnoreCase("High Priority"))) {
					if (object[21]!=null) {
						fbp.setFeedback_by_subdept(object[21].toString());
					}
					else {
						fbp.setFeedback_by_subdept("NA");
					}
					
					if (object[22]!=null) {
						fbp.setFeedback_to_subdept(object[22].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				}
				
				if (deptLevel.equals("2") && status.equalsIgnoreCase("Pending")) {
					if (object[18]!=null) {
						fbp.setFeedback_by_subdept(object[18].toString());
					}
					else {
						fbp.setFeedback_by_subdept("NA");
					}
					
					if (object[19]!=null) {
						fbp.setFeedback_to_subdept(object[19].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				
					if (object[20]!=null) {
						fbp.setSn_reason(object[20].toString());
					}
					else {
						fbp.setSn_reason("NA");
					}
					
					if (object[21]!=null && !object[21].toString().equals("") && !object[21].toString().equals("NA")) {
						fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[21].toString()));
					}
					else {
						fbp.setSn_on_date("NA");
					}
					
					if (object[22]!=null && !object[22].toString().equals("") && !object[22].toString().equals("NA")) {
						fbp.setSn_on_time(object[22].toString().substring(0, 5));
					}
					else {
						fbp.setSn_on_time("NA");
					}
					
					if (object[23]!=null && !object[23].toString().equals("") && !object[23].toString().equals("NA")) {
						fbp.setSn_date(DateUtil.convertDateToIndianFormat(object[23].toString()));
					}
					else {
						fbp.setSn_date("NA");
					}
					
					if (object[24]!=null && !object[24].toString().equals("") && !object[24].toString().equals("NA")) {
						fbp.setSn_time(object[24].toString().substring(0, 5));
					}
					else {
						fbp.setSn_time("NA");
					}
					
					if (object[25]!=null) {
						fbp.setSn_duration(object[25].toString());
					}
					else {
						fbp.setSn_duration("NA");
					}
					
                    if (object[26]!=null && !object[26].toString().equals("") && object[12]!=null && !object[12].toString().equals("") && object[13]!=null && !object[13].toString().equals("")) {
    					
    					fbp.setAddressingTime(DateUtil.newdate_AfterEscTimeOld(object[12].toString(), object[13].toString(), "00:00", object[26].toString()).substring(11));
    				}
    				else {
    					fbp.setAddressingTime("NA");
    				}
					
					if (object[27]!=null) {
						fbp.setEscalation_date(object[27].toString());
					}
					else {
						fbp.setEscalation_date("NA");
					}
					
					if (object[28]!=null) {
						fbp.setEscalation_time(object[28].toString());
					}
					else {
						fbp.setEscalation_time("NA");
					}
				}
				
				if (deptLevel.equals("2") && status.equalsIgnoreCase("")) {
					if (object[18]!=null) {
						fbp.setFeedback_by_subdept(object[18].toString());
					}
					else {
						fbp.setFeedback_by_subdept("NA");
					}
					
					if (object[19]!=null) {
						fbp.setFeedback_to_subdept(object[19].toString());
					}
					else {
						fbp.setFeedback_to_subdept("NA");
					}
				}
				fbpList.add(fbp);	
			}
		}
		return fbpList;
	}
	
	
}
