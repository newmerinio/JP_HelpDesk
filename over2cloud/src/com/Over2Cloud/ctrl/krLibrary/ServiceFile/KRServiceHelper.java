package com.Over2Cloud.ctrl.krLibrary.ServiceFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.krLibrary.KRActionHelper;
import com.Over2Cloud.ctrl.krLibrary.KRPojo;

public class KRServiceHelper
{
	 @SuppressWarnings("rawtypes")
	List dataList=new ArrayList();
	@SuppressWarnings("rawtypes")
    public List getRecurringData(SessionFactory connectionSpace)
	{
		 try 
		 {
		 	String query="SELECT share.id as shareId,contact.id as contact_id,share.userName,contact.share_on,contact.action_date,share.shareType,share.shareTill FROM kr_share_data as share " +
		 			" LEFT JOIN kr_share_contact_data as contact on share.id=contact.kr_share_id WHERE "+
		 			" contact.status='Done' AND share.status='Active' AND contact.share_on<='"+DateUtil.getCurrentDateUSFormat()+"'  AND  share.frequency!='OT'";
		 	System.out.println("QUERY ::: RECURING :::   "+query.toString());
		 	dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	@SuppressWarnings("rawtypes")
    public List getReminderData(SessionFactory connectionSpace)
	{
		 try 
		 {
			 StringBuilder query = new StringBuilder();
			    query.append("SELECT krShare.id,upload.kr_name,upload.kr_starting_id,upload.kr_brief,upload.tag_search,dept.deptName,krGroup.groupName,subGroup.subGroupName, ");
				query.append(" krShare.accessType,upload.upload1,krShare.frequency,con.action_date,con.share_on,emp.empName,con.status,emp.emailIdOne,emp.mobOne,krShare.repeatUpTo FROM kr_share_data AS krShare  ");
				query.append(" LEFT JOIN  kr_share_contact_data as con on con.kr_share_id=krShare.id ");
				query.append(" LEFT JOIN compliance_contacts as cc on con.contactId=cc.id ");
				query.append(" LEFT JOIN employee_basic as emp on emp.id=cc.emp_id ");
				query.append(" LEFT JOIN kr_upload_data AS upload ON krShare.doc_name=upload.id ");
				query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id ");
				query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
				query.append(" LEFT JOIN department AS dept ON krGroup.dept=dept.id ");
				query.append("WHERE krShare.dueDate<='"+DateUtil.getCurrentDateUSFormat()+"' AND con.status='Pending' AND krShare.actionReq='Yes' AND krShare.status='Active' ");
			   System.out.println("query :: "+query.toString());
				dataList=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
			
			 query.setLength(0);
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	@SuppressWarnings("rawtypes")
    public List getTodayReminderFor(SessionFactory connectionSpace,String workingDate,String conditionType)
	{
		 try 
		 {
			 String query = null;
			if(conditionType!=null && conditionType.equalsIgnoreCase("equalCondition"))
			{
				query="SELECT share.id,contact.contactId FROM kr_share_data as share LEFT JOIN kr_share_contact_data as contact on contact.kr_share_id = share.id WHERE contact.share_on<='"+DateUtil.getCurrentDateUSFormat()+"' AND contact.status='Pending' AND share.status = 'Active'";
				
			}
			else if(conditionType.equalsIgnoreCase("betweenCondition"))
			{
				query="SELECT share.id,contact.contactId FROM kr_share_data as share LEFT JOIN kr_share_contact_data as contact on contact.kr_share_id = share.id WHERE contact.share_on BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+workingDate+"' AND contact.status='Pending' AND share.status = 'Active'";
			}
			if(query!=null)
			{
				System.out.println("today reminder query :::  "+query);
				dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
			}
			
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}
	@SuppressWarnings("rawtypes")
	public List<KRPojo> getAllSharedSummary(SessionFactory connection)
	{
		List<KRPojo> sharedSummary=null;
		try
		{
			ComplianceReminderHelper CR=new ComplianceReminderHelper();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			sharedSummary=new ArrayList<KRPojo>();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.deptName,krGroup.groupName   ");
			query.append(",subGroup.subGroupName,emp.empName,krshare.accessType,krshare.actionReq, contact.share_on,contact.action_date,krshare.frequency ");
			query.append(" FROM kr_share_data AS krshare LEFT JOIN kr_share_contact_data as contact on contact.kr_share_id=krshare.id LEFT JOIN  kr_upload_data AS upload ON krshare.doc_name=upload.id  ");
			query.append(" LEFT JOIN compliance_contacts as cc on contact.contactId=cc.id ");
			query.append(" LEFT JOIN employee_basic as emp on emp.id=cc.emp_id ");
			query.append("LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id  LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
			query.append("LEFT JOIN department AS dept ON dept.id=krGroup.dept ");
			query.append("WHERE krshare.date='"+DateUtil.getCurrentDateUSFormat()+"' ORDER BY upload.kr_name ASC");
		    System.out.println(query.toString());
			List data=cbt.executeAllSelectQuery(query.toString(), connection);
		    if (data!=null && data.size()>0)
			{
		    	KRActionHelper KH=new KRActionHelper();
		    	for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
		    		KRPojo PD=new KRPojo();
					Object[] object = (Object[]) iterator.next();
					PD.setKrName(KH.getValueWithNullCheck(object[1]));
					PD.setKrId(KH.getValueWithNullCheck(object[0]));
					PD.setKrBrief(KH.getValueWithNullCheck(object[2]));
					PD.setKrTags(KH.getValueWithNullCheck(object[3]));
					PD.setKrAttach(KH.getValueWithNullCheck(object[4]));
					PD.setDepartment(KH.getValueWithNullCheck(object[5]));
					PD.setGroup(KH.getValueWithNullCheck(object[6]));
					PD.setSubGroup(KH.getValueWithNullCheck(object[7]));
					PD.setRemindTo(KH.getValueWithNullCheck(object[8]));
					PD.setKrAccessType(KH.getValueWithNullCheck(object[9]));
					PD.setKrActionReq(KH.getValueWithNullCheck(object[10]));
					if (object[11]!=null) 
					{
						PD.setKrDueDateReq(DateUtil.convertDateToIndianFormat(object[11].toString()));
					}
					else
					{
						PD.setKrDueDateReq("NA");
					}
					if (object[12]!=null) 
					{
						PD.setKrDueDate(DateUtil.convertDateToIndianFormat(object[12].toString()));
					}
					else
					{
						PD.setKrDueDate("NA");
					}
					if (object[13]!=null) 
					{
						PD.setKrFreq(CR.getFrequencyName(object[13].toString()));
					}
					else
					{
						PD.setKrFreq("NA");
					}
				    sharedSummary.add(PD); 
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sharedSummary;
	}
	@SuppressWarnings("rawtypes")
	public List<KRPojo> getAllActionTakenData(
			SessionFactory connection)
	{
		List<KRPojo> actionData=null;
		try
		{
			ComplianceReminderHelper CR=new ComplianceReminderHelper();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			actionData=new ArrayList<KRPojo>();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.deptName,krGroup.groupName   ");
			query.append(",subGroup.subGroupName,emp.empName,krshare.accessType,krshare.actionReq, contact.share_on,contact.action_date,krshare.frequency ");
			query.append(" FROM kr_share_data AS krshare LEFT JOIN kr_share_contact_data as contact on contact.kr_share_id=krshare.id LEFT JOIN  kr_upload_data AS upload ON krshare.doc_name=upload.id  ");
			query.append(" LEFT JOIN compliance_contacts as cc on contact.contactId=cc.id ");
			query.append(" LEFT JOIN employee_basic as emp on emp.id=cc.emp_id ");
			query.append("LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id  LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
			query.append("LEFT JOIN department AS dept ON dept.id=krGroup.dept ");
			query.append("LEFT JOIN kr_share_report_data AS report ON contact.id=report.krSharingId ");
			query.append("WHERE report.actionTakenDate='"+DateUtil.getCurrentDateUSFormat()+"' ORDER BY upload.kr_name ASC");
		    System.out.println(query.toString());
			List data=cbt.executeAllSelectQuery(query.toString(), connection);
		    if (data!=null && data.size()>0)
			{
		    	KRActionHelper KH=new KRActionHelper();
		    	for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
		    		KRPojo PD=new KRPojo();
					Object[] object = (Object[]) iterator.next();
					PD.setKrName(KH.getValueWithNullCheck(object[1]));
					PD.setKrId(KH.getValueWithNullCheck(object[0]));
					PD.setKrBrief(KH.getValueWithNullCheck(object[2]));
					PD.setKrTags(KH.getValueWithNullCheck(object[3]));
					PD.setKrAttach(KH.getValueWithNullCheck(object[4]));
					PD.setDepartment(KH.getValueWithNullCheck(object[5]));
					PD.setGroup(KH.getValueWithNullCheck(object[6]));
					PD.setSubGroup(KH.getValueWithNullCheck(object[7]));
					PD.setRemindTo(KH.getValueWithNullCheck(object[8]));
					PD.setKrAccessType(KH.getValueWithNullCheck(object[9]));
					PD.setKrActionReq(KH.getValueWithNullCheck(object[10]));
					if (object[11]!=null) 
					{
						PD.setKrDueDateReq(DateUtil.convertDateToIndianFormat(object[11].toString()));
					}
					else
					{
						PD.setKrDueDateReq("NA");
					}
					if (object[12]!=null) 
					{
						PD.setKrDueDate(DateUtil.convertDateToIndianFormat(object[12].toString()));
					}
					else
					{
						PD.setKrDueDate("NA");
					}
					if (object[13]!=null) 
					{
						PD.setKrFreq(CR.getFrequencyName(object[13].toString()));
					}
					else
					{
						PD.setKrFreq("NA");
					}
					actionData.add(PD); 
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return actionData;
	}
	public String getConfigMailForReport(String name,List<KRPojo> sharedSummaryData,List<KRPojo> actionTakenData, List<KRPojo> todayShareData)
	{
		String URL="<a href=http://www.over2cloud.co.in>Login</a>";
		StringBuilder mailtext=new StringBuilder("");
		mailtext.append("<div align='justify'><font face ='ARIAL' size='2'><h3>Dear "+name+", </h3></FONT></div>");
		//mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>JBM Industries Pvt. Ltd.</b></td></tr></tbody></table>");
        mailtext.append("<b>Hello!!!</b>");
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Your kind attention is required for following KR activities mapped for your analysis. We request, you to access complete information including the documents & take relevant actions (if any) by "+URL+" with your respective credentials.</td></tr></tbody></table>");
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>KR Activities as on "+DateUtil.getCurrentDateIndianFormat()+"</b></td></tr></tbody></table>");
       
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR Brief</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR ID</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tags</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Attachment</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Group</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sub Group</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remind To</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Access Type</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared On</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action Date</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Frequency</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action Req</strong></td>  </tr>");
       
        if (sharedSummaryData!=null && sharedSummaryData.size()>0)
        {
       	 int i=0;
     	 for(KRPojo FBP:sharedSummaryData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrBrief()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrId()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrTags()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAttach()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDepartment()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSubGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRemindTo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAccessType()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDateReq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrFreq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrActionReq()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrBrief()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrId()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrTags()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAttach()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDepartment()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSubGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRemindTo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAccessType()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDateReq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrFreq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrActionReq()+"</td></tr>");
				}
            }
       	 }
        mailtext.append("</tbody></table>");
        
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Today Due Summary Activities as on "+DateUtil.getCurrentDateIndianFormat()+"</b></td></tr></tbody></table>");
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR Brief</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR ID</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tags</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Attachment</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Group</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sub Group</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remind To</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Access Type</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared On</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action Date</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Frequency</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action Req</strong></td>  </tr>");
       
        if (todayShareData!=null && todayShareData.size()>0)
        {
       	 int i=0;
     	 for(KRPojo FBP:todayShareData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrBrief()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrId()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrTags()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAttach()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDepartment()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSubGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRemindTo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAccessType()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDateReq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrFreq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrActionReq()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrBrief()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrId()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrTags()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAttach()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDepartment()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSubGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRemindTo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAccessType()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDateReq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrFreq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrActionReq()+"</td></tr>");
				}
            }
       	 }
        mailtext.append("</tbody></table>");
        
        mailtext.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Action Taken Summary Activities as on "+DateUtil.getCurrentDateIndianFormat()+"</b></td></tr></tbody></table>");
       
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr  bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR Name</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR Brief</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>KR ID</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tags</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Attachment</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Group</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sub Group</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remind To</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Access Type</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Shared On</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action Date</strong></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Frequency</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action Req</strong></td>  </tr>");
       
        if (actionTakenData!=null && actionTakenData.size()>0)
        {
       	 int i=0;
     	 for(KRPojo FBP:actionTakenData)
       	  {
				int k=++i;
				if(k%2!=0)
				{
					mailtext.append("<tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrBrief()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrId()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrTags()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAttach()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDepartment()+"</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSubGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRemindTo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAccessType()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDateReq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrFreq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrActionReq()+"</td></tr>");
				}
				else
				{
					mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrBrief()+"</td><td align='left' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrId()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrTags()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAttach()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getDepartment()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getSubGroup()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getRemindTo()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrAccessType()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDateReq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrDueDate()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrFreq()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+FBP.getKrActionReq()+"</td></tr>");
				}
            }
       	 }
        mailtext.append("</tbody></table>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><b>Thanks !!!</b></FONT>");
        mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><b>CMO Team</b></FONT>");
        mailtext.append("<br>-----------------------------------------------------------------------------------<br>");
        mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
     
        return mailtext.toString() ; 
}
	public boolean updateKRAccordingToFrequency(String id,SessionFactory connectionSpace,String frequency, String remindDate) 
	{
		   boolean returnFlag=false;
		   CommonOperInterface cbt=new CommonConFactory().createInterface();
		   if(frequency!=null && id!=null && frequency!="" && id!="")
		   {
			   Map<String, Object> setVariables=new HashMap<String, Object>();
			   Map<String, Object> whereCondition=new HashMap<String, Object>();
			   String nextUpdateDate="";
			   whereCondition.put("id",id);
			   if(frequency.equals("OT"))
			   {
				  setVariables.put("reminderAlert", "0");
		       }
			   else if (frequency.equals("D")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   {
					   nextUpdateDate=DateUtil.getNewDate("day", 1, remindDate);
				   }
				   if(nextUpdateDate!=null)
					   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			   else if (frequency.equals("W")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   nextUpdateDate=DateUtil.getNewDate("week", 1, remindDate);
				   
				   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			   else if (frequency.equals("BM")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   nextUpdateDate=DateUtil.getNewDate("day", 15, remindDate);
				   
				   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			   else if (frequency.equals("M")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   nextUpdateDate=DateUtil.getNewDate("month", 1, remindDate);
				   
				   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			   else if (frequency.equals("Q")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   nextUpdateDate=DateUtil.getNewDate("month", 3, remindDate);
				   
				   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			   else if (frequency.equals("HY")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   nextUpdateDate=DateUtil.getNewDate("month", 6, remindDate);
				   
				   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			   else if (frequency.equals("Y")) 
			   {
				   if(remindDate!=null && !remindDate.equalsIgnoreCase("NA"))
				   nextUpdateDate=DateUtil.getNewDate("year", 1, remindDate);
				   
				   setVariables.put("reminderAlert", nextUpdateDate);
			   }
			
			   returnFlag=cbt.updateTableColomn("kr_share_data", setVariables, whereCondition, connectionSpace);
		   }
		   return returnFlag;
	   }
	@SuppressWarnings("rawtypes")
	public List<KRPojo> getAllTodaySharedSummary(SessionFactory connection) 
	{
		List<KRPojo> sharedSummary=null;
		try
		{
			ComplianceReminderHelper CR=new ComplianceReminderHelper();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			sharedSummary=new ArrayList<KRPojo>();
			StringBuilder query=new StringBuilder();
			query.append("SELECT upload.kr_starting_id,upload.kr_name,upload.kr_brief,upload.tag_search,upload.upload1,dept.deptName,krGroup.groupName   ");
			query.append(",subGroup.subGroupName,emp.empName,krshare.accessType,krshare.actionReq, contact.share_on,contact.action_date,krshare.frequency ");
			query.append(" FROM kr_share_data AS krshare LEFT JOIN kr_share_contact_data as contact on contact.kr_share_id=krshare.id LEFT JOIN  kr_upload_data AS upload ON krshare.doc_name=upload.id  ");
			query.append(" LEFT JOIN compliance_contacts as cc on contact.contactId=cc.id ");
			query.append(" LEFT JOIN employee_basic as emp on emp.id=cc.emp_id ");
			query.append("LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id  LEFT JOIN kr_group_data AS krGroup ON subGroup.groupName=krGroup.id ");
			query.append("LEFT JOIN department AS dept ON dept.id=krGroup.dept ");
			query.append("WHERE contact.share_on='"+DateUtil.getCurrentDateUSFormat()+"' ORDER BY upload.kr_name ASC");
		    System.out.println(query.toString());
			List data=cbt.executeAllSelectQuery(query.toString(), connection);
		    if (data!=null && data.size()>0)
			{
		    	KRActionHelper KH=new KRActionHelper();
		    	for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
		    		KRPojo PD=new KRPojo();
					Object[] object = (Object[]) iterator.next();
					PD.setKrName(KH.getValueWithNullCheck(object[1]));
					PD.setKrId(KH.getValueWithNullCheck(object[0]));
					PD.setKrBrief(KH.getValueWithNullCheck(object[2]));
					PD.setKrTags(KH.getValueWithNullCheck(object[3]));
					PD.setKrAttach(KH.getValueWithNullCheck(object[4]));
					PD.setDepartment(KH.getValueWithNullCheck(object[5]));
					PD.setGroup(KH.getValueWithNullCheck(object[6]));
					PD.setSubGroup(KH.getValueWithNullCheck(object[7]));
					PD.setRemindTo(KH.getValueWithNullCheck(object[8]));
					PD.setKrAccessType(KH.getValueWithNullCheck(object[9]));
					PD.setKrActionReq(KH.getValueWithNullCheck(object[10]));
					if (object[11]!=null) 
					{
						PD.setKrDueDateReq(DateUtil.convertDateToIndianFormat(object[11].toString()));
					}
					else
					{
						PD.setKrDueDateReq("NA");
					}
					if (object[12]!=null) 
					{
						PD.setKrDueDate(DateUtil.convertDateToIndianFormat(object[12].toString()));
					}
					else
					{
						PD.setKrDueDate("NA");
					}
					if (object[13]!=null) 
					{
						PD.setKrFreq(CR.getFrequencyName(object[13].toString()));
					}
					else
					{
						PD.setKrFreq("NA");
					}
				    sharedSummary.add(PD); 
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sharedSummary;
	}
}