package com.Over2Cloud.ctrl.asset.complaint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author admin
 *
 */
public class ActionOnAssetComplaint extends ActionSupport
{
	
	private static final long serialVersionUID = 1L;
    @SuppressWarnings("unchecked")
    Map session = ActionContext.getContext().getSession();
    public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	String deptLevel = (String) session.get("userDeptLevel");
	SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
	
	private Map<Integer, String> resolverList = null;
	private Map<Integer, String> RCAList = null;
	Map<Integer, String> deptList=null;
	private String complaintid;
	private String open_date;
	private String open_time;
	private String remark;
	private String resolver;
	private String spareused;
	
	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	
	private String hpcomment;
	private String ignorecomment;
	
	
	private String moduleName;
	private String dataFlag;
	private String newDept_id;
	
	
	
	private String selectedId;
	private String feedbackBy;
	private String mobileno;
	private String emailId;
    private String viaFrom;
    private String feed_brief;
    private String asset_id;
    private String asset;
    private String serialno;
    
    private String ticketno;
    private String complainantResTime;
    private String allotto_name;
    private String allotto_mobno;
    
    private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";
	
	
	private String feedStatus;
	private String fromDate;
	private String toDate;
    
    
 // Grid Variables Declaration
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = true;
	// Grid column view
	private String oper;
	private int id;
	private Map<String,String> spareCatgMap;
	private String spareName;
	private String no_spareused;
	

	private String status;
	private List statusList=null;
	List<AssetComplaintPojo> feedbackList = null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	public String redirectToJSP()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				statusList = new ArrayList<String>();
				spareCatgMap = new LinkedHashMap<String, String>();
				
				statusList.add("Resolved");
				if (status!=null && status.equalsIgnoreCase("Pending"))
				{
					statusList.add("Snooze");
					statusList.add("High Priority");
				}
				else if (status!=null && status.equalsIgnoreCase("Snooze"))
				{
					statusList.add("High Priority");
				}
				else if (status!=null && status.equalsIgnoreCase("High Priority"))
				{
					statusList.add("Snooze");
				}
				statusList.add("Ignore");
				// statusList.add("Re-Assign");
				
				getAllSpareCategory();
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
	
	
	public String beforeActionOnFeedback()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try {
				if (feedStatus!=null && !feedStatus.equals("")  && (feedStatus.equals("Pending") || feedStatus.equals("Snooze") || feedStatus.equals("High Priority"))) 
				{
					statusList();
					deptList();
				}
				else if (feedStatus!=null && !feedStatus.equals("") && feedStatus.equals("Resolved"))
				{
					 fromDate=DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
					 toDate=DateUtil.getCurrentDateUSFormat();
				}
				setGridColomnNames();
				returnResult = SUCCESS;
			} catch (Exception e) {
			e.printStackTrace();
		  }
		}
		else {
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
			}
			else if (feedStatus.equalsIgnoreCase("Snooze"))
			{
				statusList.add("Pending");
				statusList.add("High Priority");
				statusList.add("Resolved");
				statusList.add("Ignore");
			}
			else if (feedStatus.equalsIgnoreCase("High Priority"))
			{
				statusList.add("Pending");
				statusList.add("Snooze");
				statusList.add("Resolved");
				statusList.add("Ignore");
			}
			else if (feedStatus.equalsIgnoreCase("Ignore"))
			{
				statusList.add("Pending");
				statusList.add("Snooze");
				statusList.add("Resolved");
				statusList.add("High Priority");
			}
			else if (feedStatus.equalsIgnoreCase("Resolved"))
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
	
	@SuppressWarnings("unchecked")
	public void deptList()
	{
		try
		{
			String orgLevel = (String) session.get("orgnztnlevel");
			String orgId = (String) session.get("mappedOrgnztnId");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
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
			departmentlist = new HelpdeskUniversalAction().getDataFromTable("department", colmName, wherClause, order, connectionSpace);
			if (departmentlist != null && departmentlist.size() > 0)
			{
				for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
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
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		feedbackColumnNames.add(gpv);

		List<GridDataPropertyView> ColName = Configuration.getConfigurationData("mapped_asset_complaint_configuration", accountID, connectionSpace, "", 0, "table_name", "asset_complaint_configuration");
		if (ColName != null && ColName.size() > 0)
		 {
			for (GridDataPropertyView gdp : ColName)
			 {
				if (feedStatus!=null && !feedStatus.equals("") && feedStatus.equalsIgnoreCase("pending"))
					 {
						if( !gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("asset_id")
						&& !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("resolve_time")
						&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_remark")
						&& !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("spare_used")
						&& !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("hp_time")
						&& !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_on_date")
						&& !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")
						&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("sn_duration")
						&& !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")
						&& !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("ig_reason")
						&& !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_time")
						&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("vendorid") && !gdp.getColomnName().equals("sub_location")
						&& !gdp.getColomnName().equals("moduleName"))
				             {	
								gpv = new GridDataPropertyView();
								gpv.setColomnName(gdp.getColomnName());
								gpv.setHeadingName(gdp.getHeadingName());
								gpv.setEditable(gdp.getEditable());
								gpv.setSearch(gdp.getSearch());
								gpv.setHideOrShow(gdp.getHideOrShow());
								feedbackColumnNames.add(gpv);
							 }
			       }
				else if (feedStatus!=null && !feedStatus.equals("") && feedStatus.equalsIgnoreCase("Snooze"))
				 {
					if( !gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("asset_id")
					&& !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("resolve_time")
					&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_remark")
					&& !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("spare_used")
					&& !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("hp_time")
					&& !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("ig_date")
					&& !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("ig_reason")
					&& !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_time")
					&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("vendorid") && !gdp.getColomnName().equals("sub_location")
					&& !gdp.getColomnName().equals("moduleName"))
			             {	
							gpv = new GridDataPropertyView();
							gpv.setColomnName(gdp.getColomnName());
							gpv.setHeadingName(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							feedbackColumnNames.add(gpv);
						 }
		       }
				else if (feedStatus!=null && !feedStatus.equals("") && feedStatus.equalsIgnoreCase("High Priority"))
				 {
					if(!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("asset_id")
							&& !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("resolve_time")
							&& !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_remark")
							&& !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("spare_used")
							&& !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")
							&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")
							&& !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("ig_reason")
							&& !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_time")
							&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("vendorid") && !gdp.getColomnName().equals("sub_location")
							&& !gdp.getColomnName().equals("moduleName"))
			             {	
							gpv = new GridDataPropertyView();
							gpv.setColomnName(gdp.getColomnName());
							gpv.setHeadingName(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							feedbackColumnNames.add(gpv);
						 }
		       }
				else if (feedStatus!=null && !feedStatus.equals("") && feedStatus.equalsIgnoreCase("Resolved"))
				 {
					if( !gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("asset_id")
					&& !gdp.getColomnName().equals("escalation_date") && !gdp.getColomnName().equals("escalation_time")
					&& !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("hp_time")
					&& !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_on_date")
					&& !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date")
					&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("sn_duration")
					&& !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")
					&& !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("ig_reason")
					&& !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_time")
					&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("vendorid") && !gdp.getColomnName().equals("sub_location")
					&& !gdp.getColomnName().equals("moduleName"))
			             {	
							gpv = new GridDataPropertyView();
							gpv.setColomnName(gdp.getColomnName());
							gpv.setHeadingName(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							feedbackColumnNames.add(gpv);
						 }
		             }
			     }
		    }
	  }
	
	@SuppressWarnings("unchecked")
	public String getFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				AssetComplaintHelper ACH = new AssetComplaintHelper();
				
				feedbackList = new ArrayList<AssetComplaintPojo>();
				// int count = 0 ;
				List data = null;
				String dept_id = "";
				String emp_id = "";

				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if(empData != null && empData.size() > 0)
				 {
				  for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_id = object[3].toString();
						emp_id = object[5].toString();
					}
				 }
				List outletId = new AssetComplaintHelper().getOutletId(emp_id,connectionSpace);
				if (outletId!=null && outletId.size()>0) {
				data = ACH.getFeedbackDetail4View(feedStatus, fromDate, toDate, outletId,"", searchField, searchString, searchOper, connectionSpace);
				if (data != null && data.size() > 0)
				 {
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					feedbackList = ACH.setFeedbackValues(data, deptLevel, getFeedStatus());
					
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				 }
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
	
	
	public String updateFeedbackStatus()
	{
	//	System.out.println("Inside Method ");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				MsgMailCommunication MMC = new MsgMailCommunication();
				FeedbackPojo fbp = new FeedbackPojo();
				String duration = "";
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				System.out.println("Status value is    "+status);
				String snDate="",snTime="",snUpToDate="",snUpToTime="",snDuration="";
				List ticketData = new HelpdeskUniversalAction().getMultipleColumns("feedback_status", "sn_on_date", "sn_on_time", "sn_upto_date","sn_upto_time","sn_duration", "id", complaintid, "", "", connectionSpace);
				if (ticketData!=null && ticketData.size()>0)
				{
					for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && !object[0].toString().equals(""))
						{
							snDate=object[0].toString();
						}
						else 
						{
					      snDate="NA";
						}
						if (object[1]!=null && !object[1].toString().equals(""))
						{
							snTime=object[1].toString();
						}
						else
						{
							snTime="NA";
						}
						if (object[2]!=null && !object[2].toString().equals(""))
						{
							snUpToDate=object[2].toString();
						}
						else
						{
							snUpToDate="NA";
						}
						if (object[3]!=null && !object[3].toString().equals(""))
						{
							snUpToTime=object[3].toString();
						}
						else 
						{
							snUpToTime="NA";
						}
						if (object[4]!=null && !object[4].toString().equals(""))
						{
							snDuration=object[4].toString();
						}
						else 
						{
							snDuration="NA";
						}
					}
				}
				
				if (status!=null && status.equalsIgnoreCase("Resolved"))
				{
					 //Get resolver Id which is already a alloted person
					   //String resolver=new FeedbackUniversalAction().getField("feedback_status", "allot_to", "id", complaintid,connectionSpace);
					   String cal_duration="";
					   if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
					   {
						   duration = DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					   }
					   if (!snDuration.equals("") && !snDuration.equals("NA"))
					   {
						   boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
						   if (flag)
						   {
							   //System.out.println("Inside If Block");
							   cal_duration= DateUtil.getTimeDifference(duration, snDuration);
							   if (cal_duration!=null && !cal_duration.equals("") && !cal_duration.contains("-"))
							   {
								   duration=cal_duration;
							   }
						   }
						   else
						   {
							   //System.out.println("Inside Else Block");
							   String newduration = DateUtil.time_difference(snDate, snTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
							   if (newduration!=null && !newduration.equals("") && !newduration.equals("NA"))
							   {
								   String new_cal_duration= DateUtil.getTimeDifference(duration, newduration);
								   if (new_cal_duration!=null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
								   {
									   duration=new_cal_duration;
								   }
							   }
						   }
					   }
					wherClause.put("status", status);
					wherClause.put("action_by", userName);
					wherClause.put("resolve_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("resolve_time", DateUtil.getCurrentTime());
					wherClause.put("resolve_duration", duration);
					wherClause.put("resolve_remark", DateUtil.makeTitle(remark));
					wherClause.put("resolve_by", resolver);
					wherClause.put("spare_used", spareused);
					System.out.println("spareName "+spareName+" >>no_spareused>> "+no_spareused+" >>>>");
					if(spareName!=null && no_spareused!=null && !spareName.equals("-1"))
					{
						System.out.println("If True ");
						wherClause.put("spareName", spareName);
						wherClause.put("no_spareused", no_spareused);
						updateAndGetSpareCoutById(spareName,no_spareused);
					}
					condtnBlock.put("id", complaintid);
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
					else
					{
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
					condtnBlock.put("id", complaintid);
				}
				else if (getStatus().equalsIgnoreCase("High Priority"))
				{
					wherClause.put("status", status);
					wherClause.put("hp_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("hp_time", DateUtil.getCurrentTime());
					wherClause.put("hp_reason", DateUtil.makeTitle(hpcomment));
					wherClause.put("action_by", userName);
					condtnBlock.put("id", complaintid);
				}
				else if (getStatus().equalsIgnoreCase("Ignore"))
				{
					wherClause.put("status", getStatus());
					wherClause.put("ig_date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("ig_time", DateUtil.getCurrentTime());
					wherClause.put("ig_reason", DateUtil.makeTitle(ignorecomment));
					wherClause.put("action_by", userName);
					condtnBlock.put("id", complaintid);
				}
				else if (getStatus().equalsIgnoreCase("Re-Assign"))
				{/*
					@SuppressWarnings("unused")
					String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", allottoId = "", to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", tolevel = "", needesc = "";
					@SuppressWarnings("unused")
					String resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA";
					// Code for getting the Escalation Date and Escalation Time
					List subCategoryList = HUH.getAllData("feedback_subcategory", "id", feedbackSubCatgory, "subCategoryName", "ASC", connectionSpace);
					if (subCategoryList != null && subCategoryList.size() > 0)
					{
						for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
						{
							Object[] objectCol = (Object[]) iterator.next();
							if (objectCol[4] == null)
							{
								adrr_time = "06:00";
							}
							else
							{
								adrr_time = objectCol[4].toString();
							}
							if (objectCol[5] == null)
							{
								res_time = "10:00";
							}
							else
							{
								res_time = objectCol[5].toString();
							}

							if (objectCol[8] == null)
							{
								tolevel = "Level1";
							}
							else
							{
								tolevel = objectCol[8].toString();
							}
							if (objectCol[9] == null)
							{
								needesc = "Y";
							}
							else
							{
								needesc = objectCol[9].toString();
							}
						}
					}

					String date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, res_time);
					String[] date_time_arr = date_time.split("#");
					if (date_time_arr.length > 0)
					{
						if (needesc != null && !needesc.equals("") && needesc.equalsIgnoreCase("Y"))
						{
							escalation_date = date_time_arr[0];
							escalation_time = date_time_arr[1];
							resolution_date = date_time_arr[0];
							resolution_time = date_time_arr[1];
						}
						else
						{
							resolution_date = date_time_arr[0];
							resolution_time = date_time_arr[1];
						}
					}

					String level_date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, "00:00");
					String[] level_date_time_arr = level_date_time.split("#");
					if (level_date_time_arr.length > 0)
					{
						level_resolution_date = level_date_time_arr[0];
						level_resolution_time = level_date_time_arr[1];
					}

					String floor = "1st Floor";

					List allotto = null;
					List allotto1 = null;
					List<String> one = new ArrayList<String>();
					List<String> two = new ArrayList<String>();
					List<String> two_new = new ArrayList<String>();
					String floor_status = HUH.getFloorStatus(getTosubdept(), connectionSpace);
					try
					{
						if (deptLevel.equals("2"))
						{
							allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, tolevel.substring(5), floor_status, floor, connectionSpace);
							if (allotto == null || allotto.size() == 0)
							{
								String newToLevel = "";
								newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
								allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, newToLevel, floor_status, floor, connectionSpace);
								if (allotto == null || allotto.size() == 0)
								{
									newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
									allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, newToLevel, floor_status, floor, connectionSpace);
									if (allotto == null || allotto.size() == 0)
									{
										newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
										allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, newToLevel, floor_status, floor, connectionSpace);
									}
								}
							}
						}
						else
						{
							// allotto = new
							// HelpdeskUniversalAction().getRandomEmp4Escalation
							// (todept,deptLevel,tolevel,floor_status,floor,
							// connectionSpace);
						}

						if (deptLevel.equals("2"))
						{
							allotto1 = HUA.getRandomEmployee(tosubdept, deptLevel, tolevel.substring(5), allotto, connectionSpace);
						}
						else
						{
							allotto1 = HUA.getRandomEmployee(todept, deptLevel, tolevel.substring(5), allotto, connectionSpace);
						}

						if (allotto != null && allotto.size() > 0)
						{
							for (Object object : allotto)
							{
								one.add(object.toString());
							}
						}
						if (allotto1 != null && allotto1.size() > 0)
						{
							for (Object object : allotto1)
							{
								two.add(object.toString());
							}
						}

						if (one != null && one.size() > two.size())
						{
							one.removeAll(two);
							if (one.size() > 0)
							{
								for (Iterator iterator = one.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									allottoId = object.toString();
									break;
								}
							}
						}
						else
						{
							List pending_alloted = HUA.getPendingAllotto(tosubdept, deptLevel);
							if (pending_alloted != null && pending_alloted.size() > 0)
							{
								for (Object object : pending_alloted)
								{
									two_new.add(object.toString());
								}
							}

							if (two_new != null && two_new.size() > 0)
							{
								one.removeAll(two_new);
							}
							if (one != null && one.size() > 0)
							{
								allottoId = HUA.getRandomEmployee("", one);
							}
							else
							{
								allottoId = HUA.getRandomEmployee("", allotto);
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				*/}
				boolean updateFeed = HUH.updateTableColomn("asset_complaint_status", wherClause, condtnBlock, connectionSpace);
				if (updateFeed)
				{/*
					{
						String levelMsg = "", complainatMsg = "", mailText = "", mailSubject = "", mailheading = "";
						List data = HUH.getFeedbackDetail("", deptLevel, getStatus(), complaintid, connectionSpace);
						if (data != null && data.size() > 0)
						{
							fbp = HUH.setFeedbackDataValues(data, getStatus(), deptLevel);
						}

						if (getStatus().equalsIgnoreCase("Resolved"))
						{
							levelMsg = "Close Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
							complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
							mailSubject = "Close Ticket Alert: " + fbp.getTicket_no();
							mailheading = "Close Ticket Alert: " + fbp.getTicket_no();
						}
						else if (getStatus().equalsIgnoreCase("High Priority"))
						{
							levelMsg = "High Priority Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By : " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + ".";
							complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + " is on High Priority.";
							mailSubject = "High Priority Feedback Alert: " + fbp.getTicket_no();
							mailheading = "High Priority Case Ticket Alert";
						}
						else if (getStatus().equalsIgnoreCase("Snooze"))
						{
							levelMsg = "Snooze Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + " will be Snoozed till " + fbp.getSn_duration() + ",Reason: " + fbp.getSn_reason() + ".";
							complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Status: Snoozed to " + fbp.getSn_on_date() + " " + fbp.getSn_on_time() + " Hrs, Reason:" + fbp.getSn_reason() + ".";
							mailSubject = "Snooze Feedback Alert: " + fbp.getTicket_no();
							mailheading = "Snooze Case Ticket Alert";
						}
						else if (getStatus().equalsIgnoreCase("Ignore"))
						{
							levelMsg = "Ignore Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " should be Ignored.";
							complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " is Ignored.";
							mailSubject = "Ignore Feedback Alert: " + fbp.getTicket_no();
							mailheading = "Ignore Case Ticket Alert";
						}
						else if (getStatus().equalsIgnoreCase("Re-Assign"))
						{
							levelMsg = "Open Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time().subSequence(0, 5) + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + " , Brief: " + fbp.getFeed_brief() + ".";
						}

						if (getStatus().equalsIgnoreCase("Resolved"))
						{
							if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
							{
								System.out.println("Ticket No Inside resolved Block   "+fbp.getTicket_no());
								MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, fbp.getTicket_no(), getStatus(), "0", "HDM");
							}

							if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
							{
								mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false);
								MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, fbp.getTicket_no(), getStatus(), "0", "", "HDM");
							}

							if (fbp.getResolve_by_mobno() != null && fbp.getResolve_by_mobno() != "" && fbp.getResolve_by_mobno().trim().length() == 10 && (fbp.getResolve_by_mobno().startsWith("9") || fbp.getResolve_by_mobno().startsWith("8") || fbp.getResolve_by_mobno().startsWith("7")))
							{
								MMC.addMessage(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_mobno(), levelMsg, fbp.getTicket_no(), getStatus(), "0", "HDM");
							}

							if (fbp.getResolve_by_emailid() != null && !fbp.getResolve_by_emailid().equals(""))
							{
								mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true);
								MMC.addMail(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_emailid(), mailSubject, mailText, fbp.getTicket_no(), "Pending", "0", "", "HDM");
							}

						}
						else if (getStatus().equalsIgnoreCase("High Priority") || getStatus().equalsIgnoreCase("Snooze") || getStatus().equalsIgnoreCase("Ignore"))
						{

							if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
							{
								MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, fbp.getTicket_no(), getStatus(), "0", "HDM");
							}

							if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
							{
								mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false);
								MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, fbp.getTicket_no(), getStatus(), "0", "", "HDM");
							}

							if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
							{
								MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, fbp.getTicket_no(), getStatus(), "0", "HDM");
							}

							if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
							{
								mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true);
								MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), mailSubject, mailText, fbp.getTicket_no(), "Pending", "0", "", "HDM");
							}

						}
						else if (getStatus().equalsIgnoreCase("Re-Assign"))
						{
							if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
							{
								MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "HDM");
							}
							if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
							{
								mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true);
								MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), mailSubject, mailText, ticket_no, "Pending", "0", "", "HDM");
							}
						}
					}
					addActionMessage("Feedback Updated in " + getStatus() + " Successfully !!!");
					returnResult = SUCCESS;
				*/}
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
	
	
	@SuppressWarnings("unchecked")
	public String getResolverData()
	 {
		System.out.println("Inside  ResolverDataMethod"+complaintid);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
	   resolverList = new LinkedHashMap<Integer, String>();
		try
		  {
			HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();

				String location = "";
				String allot_to_id = "";
				String sub_catg = "";

				@SuppressWarnings("unused")
				createTable cbt = new createTable();
				List empdata = new ArrayList<String>();
				List allotto_data = new ArrayList<String>();

				String tolevel = "";
				List feedbackList = HUA.getMultipleColumns("asset_complaint_status", "location", "subCatg", "allot_to", "level", "", "id", complaintid, "", "", connectionSpace);
				if (feedbackList != null && feedbackList.size() > 0)
				 {
					for (Iterator iterator = feedbackList.iterator(); iterator.hasNext();)
					 {
						Object[] object = (Object[]) iterator.next();
						location = object[0].toString();
						sub_catg = object[1].toString();
						allot_to_id = object[2].toString();
						tolevel = object[3].toString().substring(5);
					 }
					if (allot_to_id != null && !allot_to_id.equals(""))
					 {
						allotto_data = HUA.getEmployeeData4Asset(allot_to_id);
					 }

					//String deptid = HUA.getData("subdepartment", "deptid", "id", dept_subdept);
					empdata = HUA.getEmp4EscInAsset(location, tolevel, connectionSpace);
					if (empdata != null && empdata.size() > 0)
					{
						for (Object c : empdata)
						{
							Object[] dataC = (Object[]) c;
							resolverList.put((Integer) dataC[0], dataC[1].toString());
						}
						boolean flag = resolverList.containsKey(Integer.parseInt(allot_to_id));
						if (!flag)
						{
							if (resolverList != null && resolverList.size() > 0 && allotto_data != null && allotto_data.size() > 0)
							{
								for (Object c : allotto_data)
								{
									Object[] dataC_new = (Object[]) c;
									resolverList.put((Integer) dataC_new[1], dataC_new[0].toString());
								}
							}
						}
					}
				}
				
				if (sub_catg!=null && !sub_catg.equals("")) {
					List rcaList = HUA.getMultipleColumns("rca_master", "id", "rcaBrief", "", "", "", "subCategory", sub_catg, "", "", connectionSpace);
				if (rcaList!=null && rcaList.size()>0) {
					RCAList = new LinkedHashMap<Integer, String>();
					for (Object rcaData : rcaList)
					 {
						Object[] rcadataC = (Object[]) rcaData;
						RCAList.put((Integer) rcadataC[0], rcadataC[1].toString());
					 }
				}
				
				}
				
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getRCA()
	 {
		System.out.println("Inside  RCA List  "+complaintid);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
	   resolverList = new LinkedHashMap<Integer, String>();
		try
		  {
			RCAList = new LinkedHashMap<Integer, String>();
			HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				String sub_catg = "";
				List feedbackList = HUA.getMultipleColumns("asset_complaint_status", "subCatg", "", "", "", "", "id", complaintid, "", "", connectionSpace);
				if (feedbackList != null && feedbackList.size() > 0)
				 {
					for (Iterator iterator = feedbackList.iterator(); iterator.hasNext();)
					 {
						Object object = (Object) iterator.next();
						sub_catg = object.toString();
					 }
					if (sub_catg!=null && !sub_catg.equals("")) {
						List rcaList = HUA.getMultipleColumns("rca_master", "id", "rcaBrief", "", "", "", "subCategory", sub_catg, "", "", connectionSpace);
					if (rcaList!=null && rcaList.size()>0) {
						for (Object rcaData : rcaList)
						 {
							Object[] rcadataC = (Object[]) rcaData;
							RCAList.put((Integer) rcadataC[0], rcadataC[1].toString());
						 }
					  }
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	
	/*@SuppressWarnings("unchecked")
	public String getMoreFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				AssetComplaintHelper ACH = new AssetComplaintHelper();
				feedbackList = new ArrayList<AssetComplaintPojo>();

				// int count = 0 ;
				List data = null;
				String dept_id = "";

				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_id = object[4].toString();
					}
				}
				if (dept_id != null && !dept_id.equals(""))
				{
					
					// System.out.println("Inside Else");
					fromDate=DateUtil.convertDateToUSFormat(fromDate);
					toDate=DateUtil.convertDateToUSFormat(toDate);

							if (fromDate!=null && !fromDate.equals("") && !fromDate.equals("undefined")) {
							String []frmDateArr = fromDate.split("-");
							if (frmDateArr[0].length()<3) {
								fromDate=DateUtil.convertDateToUSFormat(fromDate);
								toDate=DateUtil.convertDateToUSFormat(toDate);
							}
						}
					System.out.println("New Dept Id is  "+newDept_id);
						data = ACH.getFeedbackDetail(feedStatus, fromDate, toDate, dept_id, newDept_id,searchField, searchString, searchOper, connectionSpace);
					}

					
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					feedbackList = ACH.setFeedbackValues(data, deptLevel, getFeedStatus());
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
	}*/

	public void getAllSpareCategory()
	{
		String query="SELECT id,spare_category FROM createspare_catg_master WHERE status='Active' ORDER BY spare_category";
		List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
					spareCatgMap.put(object[0].toString(), object[1].toString());
			}
		}
	}
	
	public void updateAndGetSpareCoutById(String spareId,String usedSpare)
	{
		String query="SELECT total_nonconsume_spare FROM nonconsume_spare_detail WHERE id="+spareId;
		System.out.println("Spare Query is "+query);
		List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			int noConsumeSpare=Integer.parseInt(dataList.get(0).toString());
			if(usedSpare!=null)
			{
				int totalRemainingSpare=noConsumeSpare-Integer.valueOf(usedSpare);
				System.out.println("totalRemainingSpare "+totalRemainingSpare);
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				wherClause.put("total_nonconsume_spare", totalRemainingSpare);
				condtnBlock.put("spare_name", spareId);
				boolean updateFeed = new HelpdeskUniversalHelper().updateTableColomn("nonconsume_spare_detail", wherClause, condtnBlock, connectionSpace);
				if(updateFeed)
					System.out.println("Data Update");
			}
		}
	}
	
	
	
	
	public String getStatus()
	{
		return status;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}


	public List getStatusList()
	{
		return statusList;
	}
	public void setStatusList(List statusList)
	{
		this.statusList = statusList;
	}


	public String getComplaintid()
	{
		return complaintid;
	}


	public void setComplaintid(String complaintid)
	{
		this.complaintid = complaintid;
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


	public String getRemark()
	{
		return remark;
	}


	public void setRemark(String remark)
	{
		this.remark = remark;
	}


	public String getResolver()
	{
		return resolver;
	}


	public void setResolver(String resolver)
	{
		this.resolver = resolver;
	}


	public String getSpareused()
	{
		return spareused;
	}


	public void setSpareused(String spareused)
	{
		this.spareused = spareused;
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


	public Map<Integer, String> getResolverList()
	{
		return resolverList;
	}


	public void setResolverList(Map<Integer, String> resolverList)
	{
		this.resolverList = resolverList;
	}



	public String getSelectedId()
	{
		return selectedId;
	}



	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}



	public String getFeedbackBy()
	{
		return feedbackBy;
	}



	public void setFeedbackBy(String feedbackBy)
	{
		this.feedbackBy = feedbackBy;
	}



	public String getMobileno()
	{
		return mobileno;
	}



	public void setMobileno(String mobileno)
	{
		this.mobileno = mobileno;
	}



	public String getEmailId()
	{
		return emailId;
	}



	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}



	public String getViaFrom()
	{
		return viaFrom;
	}



	public void setViaFrom(String viaFrom)
	{
		this.viaFrom = viaFrom;
	}



	public String getFeed_brief()
	{
		return feed_brief;
	}



	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
	}



	public String getAsset_id()
	{
		return asset_id;
	}



	public void setAsset_id(String asset_id)
	{
		this.asset_id = asset_id;
	}



	public String getAsset()
	{
		return asset;
	}



	public void setAsset(String asset)
	{
		this.asset = asset;
	}



	public String getSerialno()
	{
		return serialno;
	}



	public void setSerialno(String serialno)
	{
		this.serialno = serialno;
	}



	public String getTicketno()
	{
		return ticketno;
	}



	public void setTicketno(String ticketno)
	{
		this.ticketno = ticketno;
	}



	public String getComplainantResTime()
	{
		return complainantResTime;
	}



	public void setComplainantResTime(String complainantResTime)
	{
		this.complainantResTime = complainantResTime;
	}



	public String getAllotto_name()
	{
		return allotto_name;
	}



	public void setAllotto_name(String allotto_name)
	{
		this.allotto_name = allotto_name;
	}



	public String getAllotto_mobno()
	{
		return allotto_mobno;
	}



	public void setAllotto_mobno(String allotto_mobno)
	{
		this.allotto_mobno = allotto_mobno;
	}



	public String getOrgName()
	{
		return orgName;
	}



	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}



	public String getAddress()
	{
		return address;
	}



	public void setAddress(String address)
	{
		this.address = address;
	}



	public String getCity()
	{
		return city;
	}



	public void setCity(String city)
	{
		this.city = city;
	}



	public String getPincode()
	{
		return pincode;
	}



	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}



	public String getFeedStatus()
	{
		return feedStatus;
	}



	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}



	public String getFromDate()
	{
		return fromDate;
	}



	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}



	public String getToDate()
	{
		return toDate;
	}



	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}



	public Integer getRows()
	{
		return rows;
	}



	public void setRows(Integer rows)
	{
		this.rows = rows;
	}



	public Integer getPage()
	{
		return page;
	}



	public void setPage(Integer page)
	{
		this.page = page;
	}



	public String getSord()
	{
		return sord;
	}



	public void setSord(String sord)
	{
		this.sord = sord;
	}



	public String getSidx()
	{
		return sidx;
	}



	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}



	public String getSearchField()
	{
		return searchField;
	}



	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}



	public String getSearchString()
	{
		return searchString;
	}



	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}



	public String getSearchOper()
	{
		return searchOper;
	}



	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}



	public Integer getTotal()
	{
		return total;
	}



	public void setTotal(Integer total)
	{
		this.total = total;
	}



	public Integer getRecords()
	{
		return records;
	}



	public void setRecords(Integer records)
	{
		this.records = records;
	}



	public boolean isLoadonce()
	{
		return loadonce;
	}



	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}



	public String getOper()
	{
		return oper;
	}



	public void setOper(String oper)
	{
		this.oper = oper;
	}



	public int getId()
	{
		return id;
	}



	public void setId(int id)
	{
		this.id = id;
	}



	public List<AssetComplaintPojo> getFeedbackList()
	{
		return feedbackList;
	}



	public void setFeedbackList(List<AssetComplaintPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}



	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
		return feedbackColumnNames;
	}



	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames)
	{
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getModuleName()
	{
		return moduleName;
	}


	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getDataFlag()
	{
		return dataFlag;
	}
	public void setDataFlag(String dataFlag)
	{
		this.dataFlag = dataFlag;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}
	public String getNewDept_id()
	{
		return newDept_id;
	}
	public void setNewDept_id(String newDept_id)
	{
		this.newDept_id = newDept_id;
	}

	public Map<Integer, String> getRCAList() {
		return RCAList;
	}
	public void setRCAList(Map<Integer, String> list) {
		RCAList = list;
	}

	public Map<String, String> getSpareCatgMap() {
		return spareCatgMap;
	}

	public void setSpareCatgMap(Map<String, String> spareCatgMap) {
		this.spareCatgMap = spareCatgMap;
	}
	public String getSpareName() {
		return spareName;
	}
	public void setSpareName(String spareName) {
		this.spareName = spareName;
	}
	public String getNo_spareused() {
		return no_spareused;
	}
	public void setNo_spareused(String no_spareused) {
		this.no_spareused = no_spareused;
	}
	
}
