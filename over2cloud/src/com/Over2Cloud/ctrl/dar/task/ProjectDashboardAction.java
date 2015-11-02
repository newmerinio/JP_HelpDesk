package com.Over2Cloud.ctrl.dar.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.dar.helper.DarHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ProjectDashboardAction extends ActionSupport
{
	  @SuppressWarnings("rawtypes")
	  Map session = ActionContext.getContext().getSession();
	  DarSubmissionPojoBean dashboardPojo,dashboardPojo1,dashboardPojo2,dashboardPojo3;
	  private List<NewsAlertsPojo> newsList;
	  private String fromDate;
	  private String toDate;
	  private String totalStatus="0";

	public String getData4ProjectDashboard()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
			    DarHelper darhelper = new DarHelper();
			    String userName=(String)session.get("uName");
				SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
				List<DarSubmissionPojoBean> finalDataList=null; 
                TaskRegistrationHelper TRH=new TaskRegistrationHelper();
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				String empId = empIdofuser.split("-")[1].trim();
				setFromDate(DateUtil.convertDateToIndianFormat( DateUtil.getNewDate("day", -30, DateUtil.getCurrentDateUSFormat())));
				setToDate(DateUtil.getCurrentDateIndianFormat());
				String userContID = null;
				userContID=TRH.getEmpDetailsByUserName("DAR",userName,connectionSpace)[0];
				String contactId=TRH.getContactListForReports(empId, userContID,connectionSpace);
				System.out.println("CONTACT ID :::  "+contactId);
				dashboardPojo = new DarSubmissionPojoBean();
				finalDataList = new ArrayList<DarSubmissionPojoBean>();
				finalDataList = darhelper.getProjectStatusBetweenMonth(connectionSpace,contactId, DateUtil.getNewDate("day", -30, DateUtil.getCurrentDateUSFormat()),DateUtil.getCurrentDateUSFormat());
				dashboardPojo.setDetailList(finalDataList);
				if (finalDataList!=null && finalDataList.size()>0) 
				{
					int temp=0,temp1=0,temp2=0,temp3=0;
					for (DarSubmissionPojoBean dsp : finalDataList) 
					{
						temp=temp+Integer.parseInt(dsp.getPending());
						temp1=temp1+Integer.parseInt(dsp.getMissed());
						temp2=temp2+Integer.parseInt(dsp.getTdone());
						temp3=temp3+Integer.parseInt(dsp.getSnooze());
					}
					totalStatus=String.valueOf(temp+temp1+temp2+temp3);
					
				}
				dashboardPojo1 = new DarSubmissionPojoBean();
				finalDataList = new ArrayList<DarSubmissionPojoBean>();
				finalDataList = darhelper.getAllAgeingDetails(connectionSpace,contactId,DateUtil.getCurrentDateUSFormat());
				dashboardPojo1.setDetailList(finalDataList);
				
				dashboardPojo2 = new DarSubmissionPojoBean();
				finalDataList = new ArrayList<DarSubmissionPojoBean>();
				finalDataList = darhelper.getRunningProjectDetails(connectionSpace,contactId);
				dashboardPojo2.setDetailList(finalDataList);
			
				returnResult=SUCCESS;
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else 
		{
			returnResult=LOGIN;
		}
	   return returnResult;
	}
	
	public DarSubmissionPojoBean getDashboardPojo() {
		return dashboardPojo;
	}
	public void setDashboardPojo(DarSubmissionPojoBean dashboardPojo) {
		this.dashboardPojo = dashboardPojo;
	}
	public List<NewsAlertsPojo> getNewsList() {
		return newsList;
	}
	public void setNewsList(List<NewsAlertsPojo> newsList) {
		this.newsList = newsList;
	}
	public DarSubmissionPojoBean getDashboardPojo1() {
		return dashboardPojo1;
	}
	public void setDashboardPojo1(DarSubmissionPojoBean dashboardPojo1) {
		this.dashboardPojo1 = dashboardPojo1;
	}
    public DarSubmissionPojoBean getDashboardPojo2() {
		return dashboardPojo2;
	}
	public void setDashboardPojo2(DarSubmissionPojoBean dashboardPojo2) {
		this.dashboardPojo2 = dashboardPojo2;
	}

	public DarSubmissionPojoBean getDashboardPojo3() {
		return dashboardPojo3;
	}

	public void setDashboardPojo3(DarSubmissionPojoBean dashboardPojo3) {
		this.dashboardPojo3 = dashboardPojo3;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	  public String getTotalStatus() {
		return totalStatus;
	}

	public void setTotalStatus(String totalStatus) {
		this.totalStatus = totalStatus;
	}
}
