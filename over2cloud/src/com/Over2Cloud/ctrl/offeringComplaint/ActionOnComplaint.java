package com.Over2Cloud.ctrl.offeringComplaint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class ActionOnComplaint extends GridPropertyBean
{

	private static final long serialVersionUID = 1L;
	private String moduleName;
	private String fromDate;
	private String toDate;
	private String feedStatus;
	private String dashFor = "";
	private String deptId;
	private String headingTitle;
	private List<String> statusList = null;
	private Map<Integer, String> deptList = null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	private String dataFlag;
	private String feedid;
	private String ticket_no;
    private String resolver;
	private String remark;
	private String status;
	private String addrDate;
	private String addrTime;
	private String open_date;
	private String open_time;
	private String resolveon;
	private String resolveat;
	private String spareused;
	private String actiontaken;
	private String hpcomment;
	private String ignorecomment;
	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	private String reAssignRemark;
	private JSONArray jsonArr = null;
	private String rca;
	private String capa;

	public String beforeActionOnFeedback()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (moduleName != null&& !moduleName.equals("") && moduleName.equalsIgnoreCase("DREAM_HDM")
				        && feedStatus != null&& !feedStatus.equals("")&& (feedStatus.equals("Pending")
				                || feedStatus.equals("Snooze") || feedStatus.equals("High Priority")))
				{
					setFromDate(DateUtil.getNewDate("week", -1,DateUtil.getCurrentDateUSFormat()));
					toDate = DateUtil.getCurrentDateUSFormat();
					statusList();
					deptList();
					returnResult = SUCCESS;
				} 
				else if (moduleName != null && !moduleName.equals("")&& moduleName.equalsIgnoreCase("DREAM_HDM")
				        && feedStatus != null && !feedStatus.equals("")&& feedStatus.equals("Resolved"))
				{
					setFromDate(DateUtil.getNewDate("week", -1,DateUtil.getCurrentDateUSFormat()));
					toDate = DateUtil.getCurrentDateUSFormat();
					statusList();
					deptList();
					returnResult = SUCCESS;
				}
				if (moduleName != null && !moduleName.equals("")&& moduleName.equalsIgnoreCase("DREAM_HDM"))
				{
					setGridColomnNames();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void statusList()
	{
		try
		{
			statusList = new ArrayList<String>();
			if (feedStatus.equalsIgnoreCase("Pending"))
			{
				statusList.add("Snooze");
				statusList.add("High Priority");
				statusList.add("Resolved");
				statusList.add("Ignore");
			} else if (feedStatus.equalsIgnoreCase("Snooze"))
			{
				statusList.add("Pending");
				statusList.add("High Priority");
				statusList.add("Resolved");
				statusList.add("Ignore");
			} else if (feedStatus.equalsIgnoreCase("High Priority"))
			{
				statusList.add("Pending");
				statusList.add("Snooze");
				statusList.add("Resolved");
				statusList.add("Ignore");
			} else if (feedStatus.equalsIgnoreCase("Ignore"))
			{
				statusList.add("Pending");
				statusList.add("Snooze");
				statusList.add("Resolved");
				statusList.add("High Priority");
			} else if (feedStatus.equalsIgnoreCase("Resolved"))
			{
				statusList.add("Pending");
				statusList.add("Snooze");
				statusList.add("High Priority");
				statusList.add("Ignore");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public void deptList()
	{
		try
		{
			String orgLevel = (String) session.get("orgnztnlevel");
			String orgId = (String) session.get("mappedOrgnztnId");
			SessionFactory connectionSpace = (SessionFactory) session
			        .get("connectionSpace");
			deptList = new LinkedHashMap<Integer, String>();
			List departmentlist = new ArrayList();
			List colmName = new ArrayList();
			Map<String, Object> order = new HashMap<String, Object>();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			colmName.add("id");
			colmName.add("deptName");
			wherClause.put("orgnztnlevel", orgLevel);
			wherClause.put("mappedOrgnztnId", orgId);
			order.put("deptName", "ASC");

			// Getting Id, Dept Name for Non Service Department
			departmentlist = new HelpdeskUniversalAction().getDataFromTable(
			        "department", colmName, wherClause, order, connectionSpace);
			if (departmentlist != null && departmentlist.size() > 0)
			{
				for (Iterator iterator = departmentlist.iterator(); iterator
				        .hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					deptList.put((Integer) object[0], object[1].toString());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setGridColomnNames()
	{
		feedbackColumnNames = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_date");
		gpv.setHeadingName("Addr Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_time");
		gpv.setHeadingName("Addr Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Pending") || getFeedStatus().equals("HP"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_date");
			gpv.setHeadingName("Esc Date");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_time");
			gpv.setHeadingName("Esc Time");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientFor");
		gpv.setHeadingName("Client For");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientName");
		gpv.setHeadingName("Client Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("offeringName");
		gpv.setHeadingName("Offering");
		gpv.setHideOrShow("false");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Feedback To");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_cc");
		gpv.setHeadingName("Feedback CC");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_to_dept");
		gpv.setHeadingName("To Department");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

	/*	gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_to_subdept");
		gpv.setHeadingName("To Sub Dept");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);*/

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedtype");
		gpv.setHeadingName("Feedback Type");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_catg");
		gpv.setHeadingName("Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_subcatg");
		gpv.setHeadingName("Sub Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("center");
		gpv.setHeadingName("Brief");
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Snooze"))
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_reason");
			gpv.setHeadingName("Snooze Reason");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_date");
			gpv.setHeadingName("Snooze On");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_time");
			gpv.setHeadingName("Snooze At");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_date");
			gpv.setHeadingName("Snooze Up To");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_time");
			gpv.setHeadingName("Snooze Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_duration");
			gpv.setHeadingName("Snooze Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}

		if (getFeedStatus().equals("High Priority"))
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_date");
			gpv.setHeadingName("HP Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_time");
			gpv.setHeadingName("HP Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_reason");
			gpv.setHeadingName("HP Reason");
			gpv.setWidth(100);
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}

		if (getFeedStatus().equals("Resolved"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Resolved On");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Resolved At");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Res. Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_by");
			gpv.setHeadingName("Resolve By");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("Res. Remark");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("Spare Used");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_rca");
			gpv.setHeadingName("Res. RCA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_capa");
			gpv.setHeadingName("Res. CAPA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Location");
		gpv.setWidth(100);
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(100);
		gpv.setHideOrShow("true");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("via_from");
		gpv.setHeadingName("Via From");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_registerby");
			gpv.setHeadingName("Register By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}
		if (!getFeedStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("action_by");
			gpv.setHeadingName("Action By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}

		
	}

	public String actionOnFeedback()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (dashFor != null && getDeptId() == null
				        && feedStatus.equals("Pending"))
				{
					headingTitle = "Pending Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null)
				        && feedStatus.equals("Snooze"))
				{
					headingTitle = "Snooze Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null)
				        && feedStatus.equals("High Priority"))
				{
					headingTitle = "High Priority Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null)
				        && feedStatus.equals("Resolved"))
				{
					fromDate = DateUtil.getNewDate("month", -1,
					        DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					headingTitle = "Resolved Feedback >> View";
					returnResult = SUCCESS;
				}
				setGridColomnNames();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
    public String getFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ComplaintLodgeHelper CLH=new ComplaintLodgeHelper();
				feedbackList = new ArrayList<FeedbackPojo>();
				List data = null;
				String dept_subdept_id = "";
				String contactLoggedId=null;

				String[] empData = CLH.getEmpDetailsByUserName("DREAM_HDM",Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), connectionSpace);
				if (empData != null && empData.length > 0)
				{
					dept_subdept_id=empData[4];
					contactLoggedId=empData[0];
				}

				if (dept_subdept_id != null && !dept_subdept_id.equals(""))
				{
					if (dataFlag!=null && dataFlag.equals("M"))
					{
						toDate = DateUtil.convertDateToUSFormat(toDate);
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
					}
					else
					{
						toDate = toDate;
						fromDate = fromDate;
					}
					data = CLH.getFeedbackDetail("complaint_status", getFeedStatus(), fromDate, toDate, dept_subdept_id, contactLoggedId, "feedback.id", "ASC", "DREAM_HDM", searchField, searchString, searchOper, connectionSpace);
				
				}
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

				
					feedbackList = CLH.setFeedbackValues(data, getFeedStatus(),connectionSpace);
					
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	public String redirectToJSP()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				statusList = new ArrayList<String>();
				statusList.add("Resolved");
				if (feedStatus.equalsIgnoreCase("Pending"))
				{
					statusList.add("Snooze");
					statusList.add("High Priority");
				}
				else if (feedStatus.equalsIgnoreCase("Snooze"))
				{
					statusList.add("High Priority");
				}
				else if (feedStatus.equalsIgnoreCase("High Priority"))
				{
					statusList.add("Snooze");
				}
				statusList.add("Ignore");
				//statusList.add("Re-Assign");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	@SuppressWarnings("rawtypes")
    public String updateFeedbackStatus()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				new HelpdeskUniversalAction();
				MsgMailCommunication MMC = new MsgMailCommunication();
				FeedbackPojo fbp = new FeedbackPojo();
				String duration = "NA";
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				
				String snDate="",snTime="",snUpToDate="",snUpToTime="",snDuration="";
				List ticketData = new HelpdeskUniversalAction().getMultipleColumns("complaint_status", "sn_on_date", "sn_on_time", "sn_upto_date","sn_upto_time","sn_duration", "id", getFeedid(), "", "", connectionSpace);
				if (ticketData!=null && ticketData.size()>0)
				{
					for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && !object[0].toString().equals(""))
						{
							snDate=object[0].toString();
						}
						else {
							snDate="NA";
						}
						if (object[1]!=null && !object[1].toString().equals(""))
						{
							snTime=object[1].toString();
						}
						else {
							snTime="NA";
						}
						if (object[2]!=null && !object[2].toString().equals(""))
						{
							snUpToDate=object[2].toString();
						}
						else {
							snUpToDate="NA";
						}
						if (object[3]!=null && !object[3].toString().equals(""))
						{
							snUpToTime=object[3].toString();
						}
						else {
							snUpToTime="NA";
						}
						if (object[4]!=null && !object[4].toString().equals(""))
						{
							snDuration=object[4].toString();
						}
						else {
							snDuration="NA";
						}
					}
				}
				
				if (getStatus().equalsIgnoreCase("Resolved"))
				{
					String cal_duration="";
					if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
					 {
						duration = DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					 }
					if (!snDuration.equals("") && !snDuration.equals("NA"))
					{
					  boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
						if (flag) {
							cal_duration= DateUtil.getTimeDifference(duration, snDuration);
							if (cal_duration!=null && !cal_duration.equals("") && !cal_duration.contains("-"))
							{
								duration=cal_duration;
							}
						}
						else {
							String newduration = DateUtil.time_difference(snDate, snTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
							if (newduration!=null && !newduration.equals("") && !newduration.equals("NA")) {
								String new_cal_duration= DateUtil.getTimeDifference(duration, newduration);
								if (new_cal_duration!=null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
								{
									duration=new_cal_duration;
								}
							}
						}
					}
					wherClause.put("status", getStatus());
					wherClause.put("action_by", userName);
					wherClause.put("resolve_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("resolve_time", DateUtil.getCurrentTime());
					wherClause.put("resolve_duration", duration);
					wherClause.put("resolve_remark", DateUtil.makeTitle(getRemark()));
					wherClause.put("resolve_by", getResolver());
					wherClause.put("spare_used", getSpareused());
					wherClause.put("resolve_rca", getRca());
					wherClause.put("resolve_capa", getCapa());
					condtnBlock.put("id", getFeedid());
				}
				else if (getStatus().equalsIgnoreCase("Snooze"))
				{
					if (snDate.equals("NA") || snTime.equals("NA"))
					{
						if (snoozeDate != null && !snoozeDate.equals("") && snoozeTime != null && !snoozeTime.equals(""))
						{
							duration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
						}
					}
					else {
						if (snoozeDate != null && !snoozeDate.equals("") && snoozeTime != null && !snoozeTime.equals(""))
						{
							duration = DateUtil.time_difference(snDate,snTime, DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
						}
					}

					wherClause.put("status", getStatus());
					wherClause.put("sn_reason", DateUtil.makeTitle(getSnoozecomment()));
					if (snDate.equals("NA") || snTime.equals("NA"))
					 {
						wherClause.put("sn_on_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("sn_on_time", DateUtil.getCurrentTime());
					 }
					wherClause.put("sn_upto_date", DateUtil.convertDateToUSFormat(snoozeDate));
					wherClause.put("sn_upto_time", snoozeTime);
					wherClause.put("sn_duration", duration);
					wherClause.put("action_by", userName);
					condtnBlock.put("id", getFeedid());
				}
				else if (getStatus().equalsIgnoreCase("High Priority"))
				{
					wherClause.put("status", getStatus());
					wherClause.put("hp_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("hp_time", DateUtil.getCurrentTime());
					wherClause.put("hp_reason", DateUtil.makeTitle(getHpcomment()));
					wherClause.put("action_by", userName);
					condtnBlock.put("id", getFeedid());
				}
				else if (getStatus().equalsIgnoreCase("Ignore"))
				{
					wherClause.put("status", getStatus());
					wherClause.put("ig_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("ig_time", DateUtil.getCurrentTime());
					wherClause.put("ig_reason", DateUtil.makeTitle(getIgnorecomment()));
					wherClause.put("action_by", userName);
					condtnBlock.put("id", getFeedid());
				}
				/*else if (getStatus().equalsIgnoreCase("Re-Assign"))
				{
					String feedBy="NA",mobNo="NA",emailId="NA",bysubdept="NA",feedBrief="NA",location="NA";
					List existTicketData = new HelpdeskUniversalHelper().getTransferTicketData(getFeedid(), connectionSpace);
					if (existTicketData!=null && existTicketData.size()>0) {
						for (Iterator iterator = existTicketData.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0]!=null && !object[0].toString().equals("")) {
								feedBy=object[0].toString();
							}
							if (object[1]!=null && !object[1].toString().equals("")) {
								mobNo=object[1].toString();
							}
							if (object[2]!=null && !object[2].toString().equals("")) {
								emailId=object[2].toString();
							}
							if (object[3]!=null && !object[3].toString().equals("")) {
								bysubdept=object[3].toString();
							}
							if (object[4]!=null && !object[4].toString().equals("")) {
								feedBrief=object[4].toString();
							}
							if (object[5]!=null && !object[5].toString().equals("")) {
								location=object[5].toString();
							}
						}
						boolean flag = transferComplaint(feedBy,mobNo,emailId,bysubdept,feedBrief,location,getFeedid());
						if (flag) {
							wherClause.put("status", "Transfer");
							wherClause.put("transfer_date", DateUtil.getCurrentDateUSFormat());
							wherClause.put("transfer_time", DateUtil.getCurrentTime());
							wherClause.put("transfer_reason", DateUtil.makeTitle(getReAssignRemark()));
							wherClause.put("action_by", userName);
							condtnBlock.put("id", getFeedid());
						}
					}
					
				}*/
				boolean updateFeed = HUH.updateTableColomn("complaint_status", wherClause, condtnBlock, connectionSpace);
				if (updateFeed)
				{
					ComplaintLodgeHelper CLH=new ComplaintLodgeHelper();
				
					String levelMsg = "", complainatMsg = "", mailText = "", mailSubject = "", mailheading = "";
					List data = CLH.getFeedbackDetail(getFeedid(), getStatus(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						fbp = CLH.setFeedbackDataValues(data, getStatus(),connectionSpace);
					}
					if (getStatus().equalsIgnoreCase("Resolved"))
					{
						//levelMsg = "Close Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
						//complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
						mailSubject = "Close Ticket Alert: " + fbp.getTicket_no();
						mailheading = "Close Ticket Alert: " + fbp.getTicket_no();
					}
					else if (getStatus().equalsIgnoreCase("High Priority"))
					{
						//levelMsg = "High Priority Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By : " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + ".";
						//complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + " is on High Priority.";
						mailSubject = "High Priority Feedback Alert: " + fbp.getTicket_no();
						mailheading = "High Priority Case Ticket Alert";
					}
					else if (getStatus().equalsIgnoreCase("Snooze"))
					{
						//levelMsg = "Snooze Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + " will be Snoozed till " + fbp.getSn_duration() + ",Reason: " + fbp.getSn_reason() + ".";
						//complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Status: Snoozed to " + fbp.getSn_on_date() + " " + fbp.getSn_on_time() + " Hrs, Reason:" + fbp.getSn_reason() + ".";
						mailSubject = "Snooze Feedback Alert: " + fbp.getTicket_no();
						mailheading = "Snooze Case Ticket Alert";
					}
					else if (getStatus().equalsIgnoreCase("Ignore"))
					{
						//levelMsg = "Ignore Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " should be Ignored.";
						//complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " is Ignored.";
						mailSubject = "Ignore Feedback Alert: " + fbp.getTicket_no();
						mailheading = "Ignore Case Ticket Alert";
					}

					if (getStatus().equalsIgnoreCase("Resolved"))
					{
						/*if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
						{
							MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticket_no, getStatus(), "0", "HDM");
						}*/
						if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
						{
							mailText = CLH.getConfigMessage(fbp, mailheading, getStatus(), false, fbp.getClientFor(), fbp.getClientName(), fbp.getOfferingName());
							MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "DREAM_HDM");
						}
						/*if (fbp.getResolve_by_mobno() != null && fbp.getResolve_by_mobno() != "" && fbp.getResolve_by_mobno().trim().length() == 10 && (fbp.getResolve_by_mobno().startsWith("9") || fbp.getResolve_by_mobno().startsWith("8") || fbp.getResolve_by_mobno().startsWith("7")))
						{
							MMC.addMessage(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_mobno(), levelMsg, ticket_no, getStatus(), "0", "HDM");
						}*/
						if (fbp.getResolve_by_emailid() != null && !fbp.getResolve_by_emailid().equals(""))
						{
							mailText = CLH.getConfigMessage(fbp, mailheading, getStatus(), true, fbp.getClientFor(), fbp.getClientName(), fbp.getOfferingName());
							MMC.addMail(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_emailid(), mailSubject, mailText, fbp.getTicket_no(), "Pending", "0", "", "DREAM_HDM");
						}
					}
					
					else if (getStatus().equalsIgnoreCase("High Priority") || getStatus().equalsIgnoreCase("Snooze") || getStatus().equalsIgnoreCase("Ignore"))
					{

					/*	if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
						{
							MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticket_no, getStatus(), "0", "HDM");
						}
					 	*/
						if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
						{
							mailText = CLH.getConfigMessage(fbp, mailheading, getStatus(), false, fbp.getClientFor(), fbp.getClientName(), fbp.getOfferingName());
							MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "DREAM_HDM");
						}
						/*if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
						{
							MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "HDM");
						}*/

						if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
						{
							mailText = CLH.getConfigMessage(fbp, mailheading, getStatus(), true, fbp.getClientFor(), fbp.getClientName(), fbp.getOfferingName());
							MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), mailSubject, mailText, fbp.getTicket_no(), "Pending", "0", "", "DREAM_HDM");
						}
					}
					addActionMessage("Feedback Updated in " + getStatus() + " Successfully !!!");
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	@SuppressWarnings("rawtypes")
    public String getResolverData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				ComplaintLodgeHelper CLH=new ComplaintLodgeHelper();
				String deptFor = "";
				List empdata = new ArrayList<String>();
				List feedbackList = HUA.getMultipleColumns("complaint_status", "forDept", "", "allot_to", "level", "", "id", feedid, "", "", connectionSpace);
				if (feedbackList != null && feedbackList.size() > 0)
				{
					for (Iterator iterator = feedbackList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptFor = object[0].toString();
					}
					empdata = CLH.getEmp4Escalation(deptFor, "DREAM_HDM", "", connectionSpace);
					jsonArr = new JSONArray();
					JSONObject formDetailsJson=null;
					for (Object c : empdata)
					{
						Object[] dataC_new = (Object[]) c;
						formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", dataC_new[0].toString());
						formDetailsJson.put("NAME", dataC_new[1].toString());
						jsonArr.add(formDetailsJson);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
    @SuppressWarnings("rawtypes")
    public String getRCAData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				    StringBuilder query=new StringBuilder();
				    query.append("SELECT rca.id,rca.rcaBrief FROM rca_master AS rca ");
				    query.append(" INNER JOIN complaint_status AS cs  ON cs.offering=rca.category ");
				    query.append(" INNER JOIN complaint_status AS cs1  ON cs1.forDept=rca.deptName ");
				    query.append(" INNER JOIN complaint_status AS cs2  ON cs2.subCategory=rca.subCategory ");
				    query.append(" WHERE cs.id='"+feedid+"' AND rca.moduleName='"+moduleName+"' GROUP BY rca.id  ");
					System.out.println("QUERY IS AS    ::;  "+query.toString());
				    List data =new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					jsonArr = new JSONArray();
					JSONObject formDetailsJson=null;
					if (data!=null && data.size()>0)
                    {
	                    for (Iterator iterator = data.iterator(); iterator.hasNext();)
                        {
	                        Object[] object = (Object[]) iterator.next();
	                        if (object[0]!=null && object[1]!=null)
                            {
	                        	formDetailsJson = new JSONObject();
	    						formDetailsJson.put("ID", object[0].toString());
	    						formDetailsJson.put("NAME", object[1].toString());
	    						jsonArr.add(formDetailsJson);
                            }
                        }
                    }
				return  SUCCESS;
			}
			catch (Exception e)
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
	public JSONArray getJsonArr()
	{
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public List<String> getStatusList()
	{
		return statusList;
	}

	public void setStatusList(List<String> statusList)
	{
		this.statusList = statusList;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(
	        List<GridDataPropertyView> feedbackColumnNames)
	{
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getDashFor()
	{
		return dashFor;
	}

	public void setDashFor(String dashFor)
	{
		this.dashFor = dashFor;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getHeadingTitle()
	{
		return headingTitle;
	}

	public void setHeadingTitle(String headingTitle)
	{
		this.headingTitle = headingTitle;
	}

	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public String getFeedid()
    {
	    return feedid;
    }

	public void setFeedid(String feedid)
    {
	    this.feedid = feedid;
    }

	public String getDataFlag()
	{
		return dataFlag;
	}

	public void setDataFlag(String dataFlag)
	{
		this.dataFlag = dataFlag;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public String getResolver()
	{
		return resolver;
	}

	public void setResolver(String resolver)
	{
		this.resolver = resolver;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getAddrDate()
	{
		return addrDate;
	}

	public void setAddrDate(String addrDate)
	{
		this.addrDate = addrDate;
	}

	public String getAddrTime()
	{
		return addrTime;
	}

	public void setAddrTime(String addrTime)
	{
		this.addrTime = addrTime;
	}

	public String getOpen_date()
	{
		return open_date;
	}

	public void setOpen_date(String open_date)
	{
		this.open_date = open_date;
	}

	public String getOpen_time()
	{
		return open_time;
	}

	public void setOpen_time(String open_time)
	{
		this.open_time = open_time;
	}

	public String getResolveon()
	{
		return resolveon;
	}

	public void setResolveon(String resolveon)
	{
		this.resolveon = resolveon;
	}

	public String getResolveat()
	{
		return resolveat;
	}

	public void setResolveat(String resolveat)
	{
		this.resolveat = resolveat;
	}

	public String getSpareused()
	{
		return spareused;
	}

	public void setSpareused(String spareused)
	{
		this.spareused = spareused;
	}

	public String getActiontaken()
	{
		return actiontaken;
	}

	public void setActiontaken(String actiontaken)
	{
		this.actiontaken = actiontaken;
	}

	public String getHpcomment()
	{
		return hpcomment;
	}

	public void setHpcomment(String hpcomment)
	{
		this.hpcomment = hpcomment;
	}

	public String getIgnorecomment()
	{
		return ignorecomment;
	}

	public void setIgnorecomment(String ignorecomment)
	{
		this.ignorecomment = ignorecomment;
	}

	public String getSnoozeDate()
	{
		return snoozeDate;
	}

	public void setSnoozeDate(String snoozeDate)
	{
		this.snoozeDate = snoozeDate;
	}

	public String getSnoozeTime()
	{
		return snoozeTime;
	}

	public void setSnoozeTime(String snoozeTime)
	{
		this.snoozeTime = snoozeTime;
	}

	public String getSnoozecomment()
	{
		return snoozecomment;
	}

	public void setSnoozecomment(String snoozecomment)
	{
		this.snoozecomment = snoozecomment;
	}

	public String getReAssignRemark()
    {
	    return reAssignRemark;
    }

	public void setReAssignRemark(String reAssignRemark)
    {
	    this.reAssignRemark = reAssignRemark;
    }
	public String getRca()
	{
		return rca;
	}

	public void setRca(String rca)
	{
		this.rca = rca;
	}

	public String getCapa()
	{
		return capa;
	}

	public void setCapa(String capa)
	{
		this.capa = capa;
	}

}
