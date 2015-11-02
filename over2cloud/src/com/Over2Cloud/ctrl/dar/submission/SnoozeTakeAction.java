package com.Over2Cloud.ctrl.dar.submission;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SnoozeTakeAction extends ActionSupport implements ServletRequestAware{
	static final Log log = LogFactory.getLog(SnoozeTakeAction.class);
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
    @SuppressWarnings("unused")
	private HttpServletRequest request;
	private String reason;
	private String newDate;
	private String comment;
	private int feedid;
	private String status1;
	
	  public String snoozeAction()
	  {
		  boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				String userName=(String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
			}
			else {
				return LOGIN;
			}
		return SUCCESS;
	  }
	  public String updateTaskDetail()
	  {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
		try
		{
	    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
        Map<String, Object> setVariables=new HashMap<String, Object>();
        Map<String, Object> whereCondition=new HashMap<String, Object>();
        whereCondition.put("id", getFeedid());
        setVariables.put("comlpetiondate",DateUtil.convertDateToUSFormat(getNewDate()));
        setVariables.put("snooze",getReason());
        setVariables.put("statuss",getStatus1());
        CommonOperInterface cbt=new CommonConFactory().createInterface();
        cbt.updateTableColomn("dar_submission", setVariables, whereCondition, connectionSpace);
		}
		catch (Exception e)
		{
				returnResult=ERROR;
				e.printStackTrace();
		}
				
		addActionMessage("Task Updated  Successfully!!!");
		returnResult=SUCCESS;
	    }
		else
		{
			 addActionMessage("Oops There is some error in data!");
			returnResult=LOGIN;
		}
		
		return returnResult;
	}
     
	public String updateProductivity()
	{
		String returnResult=ERROR;

	    boolean sessionFlag=ValidateSession.checkSession();
				if(sessionFlag)
				{
				try
				{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				Map<String, Object> setVariables=new HashMap<String, Object>();
				Map<String, Object> whereCondition=new HashMap<String, Object>();
				whereCondition.put("id", getFeedid());
				setVariables.put("comment",getComment());
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				cbt.updateTableColomn("dar_submission", setVariables, whereCondition, connectionSpace);
	             }
			    catch (Exception e)
			     {
				 returnResult=ERROR;
				 e.printStackTrace();
			      }
			      addActionMessage(" Updated  Successfully!!!");
	              returnResult=SUCCESS;
		}
	else
	{
		 addActionMessage("Oops There is some error in data!");
		 returnResult=LOGIN;
	}
	
	return returnResult;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
	public int getFeedid() {
		return feedid;
	}
	public void setFeedid(int feedid) {
		this.feedid = feedid;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
