package com.Over2Cloud.ctrl.feedback.report;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;

public class FeedbackAuditReport  extends GridPropertyBean implements ServletRequestAware
{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private List<Object> masterViewList;
	private String clientId;
	private String patientId;
	private String feedDataId;
	private String fromDate;
	private String toDate;
	
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	
	public String takeActionForAudit()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
					System.out.println(">>>>>edit>>>>>");
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					wherClause.put("action_type",request.getParameter("actionType"));
					wherClause.put("comments",request.getParameter("comments"));
					wherClause.put("action_by",userName);
					wherClause.put("action_date_time", DateUtil.getCurrentDateUSFormat()+", "+DateUtil.getCurrentTime());
					System.out.println("id>>>>>"+getId());
					condtnBlock.put("id",getId());
					boolean b=cbt.updateTableColomn("feedback_audit_report", wherClause, condtnBlock,connectionSpace);
					if(b)
					{
						addActionMessage("Action Taken Successfullly!!!");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops!!! There is some error.");
						return ERROR;
					}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeActionForAudit()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println(clientId+"inside b4 action::::"+id);
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	
	}
	
	public String beforeAuditHeader()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				fromDate=DateUtil.getNewDate("day", -6, DateUtil.getCurrentDateUSFormat());
				toDate=DateUtil.getCurrentDateUSFormat();
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeAuditData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String viewAuditReport()
	{
		System.out.println("viewAuditReport");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb=new ArrayList<Object>();
				List dataList = null;
				StringBuilder query = new StringBuilder();
				query.append("SELECT far.id,feed.clientId,feed.patientId,feed.clientName,far.feedDataId,action_type,far.comments,action_by FROM feedback_audit_report AS far ");
				query.append(" INNER JOIN feedbackdata AS feed ON feed.id=far.feedDataId ");
				query.append(" WHERE far.id!=0 ");
				if (getFromDate() != null && getToDate() != null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
				{
					String str[] = getFromDate().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						query.append(" AND far.insert_date BETWEEN '" + ((getFromDate())) + "' AND '" + ((getToDate())) + "'");
					}
					else
					{
						query.append(" AND far.insert_date BETWEEN '" + (DateUtil.convertDateToUSFormat(getFromDate())) + "' AND '" + (DateUtil.convertDateToUSFormat(getToDate())) + "'");
					}
				}
				query.append(" ORDER BY far.id DESC ");
				System.out.println("query data:::::"+query);
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", (object[0]));
						if(object[1]!=null)
						{
							one.put("clientId", (object[1]));
						}
						
						if(object[2]!=null && !object[2].equals("-1"))
						{
							one.put("patientId", (object[2]));
						}
						else
						{
							one.put("patientId", "NA");
						}
						if(object[3]!=null && !object[3].equals("-1"))
						{
							one.put("clientName", (object[3]));
						}
						else
						{
							one.put("clientName", "NA");
						}
						if(object[4]!=null && !object[4].equals("-1"))
						{
							one.put("feedDataId", (object[4]));
						}
						else
						{
							one.put("feedDataId", "NA");
						}
						if(object[5]!=null && !object[5].equals("-1"))
						{
							one.put("action_type", (object[5]));
						}
						else
						{
							one.put("action_type", "NA");
						}
						if(object[6]!=null && !object[6].equals("-1"))
						{
							one.put("comments", (object[6]));
						}
						else
						{
							one.put("comments", "NA");
						}
						if(object[7]!=null && !object[7].equals("-1"))
						{
							one.put("action_by", (object[7]));
						}
						else
						{
							one.put("action_by", "NA");
						}
						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getFeedDataId() {
		return feedDataId;
	}

	public void setFeedDataId(String feedDataId) {
		this.feedDataId = feedDataId;
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
	
}

