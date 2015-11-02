package com.Over2Cloud.ctrl.dar.productivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.dar.task.TaskRegistrationHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProjectActivityBoard extends ActionSupport
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	private static final long serialVersionUID = 1L;
	private String fromDate;
	DarSubmissionPojoBean dashboardPojo,dashboardPojo1;
	private String contactId;
	private String size;
	
	public String beforeActivityBoard() 
	{
		if(ValidateSession.checkSession())
		{
			try 
			{
				    String userName=(String)session.get("uName");
				    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				    List<DarSubmissionPojoBean> todayDueList=null; 
	                TaskRegistrationHelper TRH=new TaskRegistrationHelper();
					String empIdofuser = (String) session.get("empIdofuser");
					if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;

					String empId = empIdofuser.split("-")[1].trim();
					setFromDate(DateUtil.convertDateToIndianFormat( DateUtil.getNewDate("day", -30, DateUtil.getCurrentDateUSFormat())));
					String userContID = null;
					userContID=TRH.getEmpDetailsByUserName("DAR",userName,connectionSpace)[0];
				    contactId=TRH.getContactListForReports(empId, userContID,connectionSpace);
					System.out.println("contactId     :::   "+contactId);
					dashboardPojo = new DarSubmissionPojoBean();
					todayDueList = new ArrayList<DarSubmissionPojoBean>();
					todayDueList = TRH.getProjectDueStatus(contactId, DateUtil.getCurrentDateUSFormat(),connectionSpace);
					dashboardPojo.setDetailList(todayDueList);
					System.out.println("SIZE IS AS    :::   "+todayDueList.size());
					if (todayDueList.size()<=4) 
					{
						size=String.valueOf(todayDueList.size()-1);
					}
					else {
						size="4";
					}
				
				
					setFromDate(DateUtil.getCurrentDateIndianFormat());
				return SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else 
		{
			return ERROR;
		}
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public DarSubmissionPojoBean getDashboardPojo() {
		return dashboardPojo;
	}
	public void setDashboardPojo(DarSubmissionPojoBean dashboardPojo) {
		this.dashboardPojo = dashboardPojo;
	}
	public DarSubmissionPojoBean getDashboardPojo1() {
		return dashboardPojo1;
	}
	public void setDashboardPojo1(DarSubmissionPojoBean dashboardPojo1) {
		this.dashboardPojo1 = dashboardPojo1;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

}
