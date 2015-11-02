package com.Over2Cloud.ctrl.helpdesk.feedbackaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class ActionOnFeedback extends GridPropertyBean implements ServletRequestAware
{
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();

	private String feedid;
	private String ticket_no;
	private String resolver;
	private String remark;
	private String status;
	private String addrDate;
	private String addrTime;
	private String open_date;
	private String open_time;
	private String feedbackBy;
	private String mobileno;
	private String catg;
	private String subCatg;
	private String feed_brief;
	private String allotto;
	private String location;

	private String resolveon;
	private String resolveat;
	private String spareused;
	private String actiontaken;

	private String hpcomment;
	private String ignorecomment;

	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	private String sn_reason;

	private String todept;
	private String tosubdept;
	private String reAssignRemark;

	private String feedStatus;
	private String headingTitle;

	private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";

	private String fromDate = "";
	private String toDate = "";
	private String dataFlag = "";

	private String dashFor = "";
	private String d_subdept_id = "";

	// /////////////////////////
	private String ticketNo = "";
	private String openDate = "";
	private String openTime = "";
	private String emailId = "";
	private String feedBreif = "";
	private String feedId = "";
	// ////////////////////////

	private String moduleName;

	private String backData;

	// ReAssign variable
	private String feedbackSubCatgory;

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	List<FeedbackPojo> feedbackList = null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	private Map<Integer, String> resolverList = null;
	private Map<String, String> statusList = null;
	private Map<Integer, String> deptList = null;
	private Map<String,String> dataMap;
	private File approvalDoc;
	private String approvalDocContentType;
	private String approvalDocFileName;
	private String storedDocPath;
	private String approvedBy;
	private String reason;
	private String complaintid;
	private FeedbackPojo fstatus;
	private HttpServletRequest request;
	Map<String, String> checkListMap = new LinkedHashMap<String, String>();
	private String orgImgPath;

/*	public String redirectToJSP()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	statusList = new LinkedHashMap<String, String>();
	statusList.put("Resolved", "Resolved");
	if (feedStatus.equalsIgnoreCase("Pending"))
	{
	statusList.put("Snooze", "Snooze");
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	}
	else if (feedStatus.equalsIgnoreCase("Snooze"))
	{
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	}
	else if (feedStatus.equalsIgnoreCase("High Priority"))
	{
	statusList.put("Snooze", "Snooze");
	statusList.put("hold", "Seek Approval");
	}
	statusList.put("Ignore", "Ignore");
	statusList.put("Re-Assign", "Re-Assign");
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
	
	}   */
	
 
	
	
public String redirectToJSP()
	{
	String returnResult = ERROR;
	StringBuilder NextEscalationTo = new StringBuilder();
	
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	statusList = new LinkedHashMap<String, String>();
	statusList.put("Resolved", "Resolved");
	getTicketDetails(feedId);
	
	
	if (feedStatus.equalsIgnoreCase("Pending"))
	{
	statusList.put("Snooze", "Snooze");
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	}
	else if (feedStatus.equalsIgnoreCase("Snooze"))
	{
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	}
	else if (feedStatus.equalsIgnoreCase("High Priority"))
	{
	statusList.put("Snooze", "Snooze");
	statusList.put("hold", "Seek Approval");
	}
	statusList.put("Ignore", "Ignore");
	statusList.put("Re-Assign", "Re-Assign");
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
 




@SuppressWarnings("unchecked")
	public List getEmp4Escalation(String dept_subDept, String module, String level, SessionFactory connectionSpace) {
	List empList = new ArrayList();
	StringBuilder query = new StringBuilder();
	try {
	query.append("select distinct emp.id, emp.empName from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
	query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
	query.append(" where roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	
	System.out.println("*******Query: "+query.toString());
	empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return empList;
	}
	

	
	

	public String getReAllotPage()
	{
	return SUCCESS;
	}

	public String beforeActionOnFeedback()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("HDM") && feedStatus != null && !feedStatus.equals("")
	&& (feedStatus.equals("Pending") || feedStatus.equals("Snooze") || feedStatus.equals("High Priority")))
	{
	// System.out.println("Inside If");
	fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
	toDate = DateUtil.getCurrentDateUSFormat();
	statusList();
	deptList();
	setGridColomnNames();
	returnResult = SUCCESS;
	}
	else if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("HDM") && feedStatus != null && !feedStatus.equals("") && feedStatus.equals("Resolved"))
	{
	// System.out.println("Inside First else If");
	fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
	toDate = DateUtil.getCurrentDateUSFormat();
	statusList();
	deptList();
	setGridColomnNames();
	returnResult = SUCCESS;
	}
	else if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("FM") && feedStatus != null && !feedStatus.equals("")
	&& (feedStatus.equals("Pending") || feedStatus.equals("Snooze") || feedStatus.equals("High Priority")))
	{
	// System.out.println("Inside Second else If");
	fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
	toDate = DateUtil.getCurrentDateUSFormat();
	statusList();
	deptList();
	returnResult = "FMSUCCESS";
	}
	else if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("FM") && feedStatus != null && !feedStatus.equals("") && feedStatus.equals("Resolved"))
	{
	// System.out.println("Inside Third else If");
	fromDate = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
	toDate = DateUtil.getCurrentDateUSFormat();
	statusList();
	deptList();
	returnResult = "FMSUCCESS";
	}
	else if (dashFor.equals("H") || dashFor.equals("M") || dashFor.equals("N"))
	{
	// System.out.println("Inside Fourth else If");
	fromDate = DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat());
	toDate = DateUtil.getCurrentDateIndianFormat();
	setGridColomnNames();
	returnResult = "dashsuccess";
	}

	if (moduleName != null && !moduleName.equals("") && moduleName.equalsIgnoreCase("FM"))
	{
	// System.out.println("For FM");
	getColumnNamesFM();
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
	// System.out.println("Return result   "+returnResult);
	return returnResult;
	}

	public void statusList()
	{
	try
	{
	statusList = new LinkedHashMap<String, String>();
	if (feedStatus.equalsIgnoreCase("Pending"))
	{
	statusList.put("Snooze", "Snooze");
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	statusList.put("Resolved", "Resolved");
	statusList.put("Ignore", "Ignore");
	}
	else if (feedStatus.equalsIgnoreCase("Snooze"))
	{
	statusList.put("Pending", "Pending");
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	statusList.put("Resolved", "Resolved");
	statusList.put("Ignore", "Ignore");
	}
	else if (feedStatus.equalsIgnoreCase("High Priority"))
	{
	statusList.put("Pending", "Pending");
	statusList.put("Snooze", "Snooze");
	statusList.put("hold", "Seek Approval");
	statusList.put("Resolved", "Resolved");
	statusList.put("Ignore", "Ignore");
	}
	else if (feedStatus.equalsIgnoreCase("Ignore"))
	{
	statusList.put("Pending", "Pending");
	statusList.put("Snooze", "Snooze");
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	statusList.put("Resolved", "Resolved");
	}
	else if (feedStatus.equalsIgnoreCase("Resolved"))
	{
	statusList.put("Pending", "Pending");
	statusList.put("Snooze", "Snooze");
	statusList.put("High Priority", "High Priority");
	statusList.put("hold", "Seek Approval");
	statusList.put("Ignore", "Ignore");
	}
	else if(feedStatus.equalsIgnoreCase("Hold"))
	{
	statusList.put("Pending", "Pending");
	statusList.put("Snooze", "Snooze");
	statusList.put("High Priority", "High Priority");
	statusList.put("Ignore", "Ignore");
	statusList.put("Resolved", "Resolved");
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
	gpv.setColomnName("addressingTime");
	gpv.setHeadingName("Addr Date / Time");
	gpv.setAlign("center");
	gpv.setWidth(150);
	feedbackColumnNames.add(gpv);

	if (getFeedStatus().equals("Pending") || getFeedStatus().equals("HP"))
	{
	gpv = new GridDataPropertyView();
	gpv.setColomnName("escalation_date");
	gpv.setHeadingName("Esc Date / Time");
	gpv.setAlign("center");
	gpv.setWidth(150);
	feedbackColumnNames.add(gpv);
	}

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_by_dept");
	gpv.setHeadingName("By Dept");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feed_by");
	gpv.setHeadingName("Feedback By");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_by_mobno");
	gpv.setHeadingName("Mobile No");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_by_emailid");
	gpv.setHeadingName("Email Id");
	gpv.setHideOrShow("true");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_to_dept");
	gpv.setHeadingName("To Department");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_to_subdept");
	gpv.setHeadingName("To Sub Dept");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_allot_to");
	gpv.setHeadingName("Allot To");
	gpv.setAlign("center");
	gpv.setWidth(100);
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
	gpv.setEditable("true");
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

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedtype");
	gpv.setHeadingName("Feedback Type");
	gpv.setAlign("center");
	feedbackColumnNames.add(gpv);
	}

	public void getColumnNamesFM()
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
	gpv.setHeadingName("Response Date");
	gpv.setAlign("center");
	gpv.setWidth(70);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedaddressing_time");
	gpv.setHeadingName("Response Time");
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
	gpv.setColomnName("feed_by");
	gpv.setHeadingName("Patient Name");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("location");
	gpv.setHeadingName("Location");
	gpv.setWidth(100);
	gpv.setAlign("center");
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feed_brief");
	gpv.setAlign("center");
	gpv.setHeadingName("Feedback/Complaint");
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_remarks");
	gpv.setAlign("center");
	gpv.setHeadingName("Feedback Remarks");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feed_registerby");
	gpv.setHeadingName("Register By");
	gpv.setAlign("center");
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_by_dept");
	gpv.setHeadingName("By Department");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_by_mobno");
	gpv.setHeadingName("Mobile No");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_by_emailid");
	gpv.setHeadingName("Email Id");
	gpv.setHideOrShow("true");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_to_dept");
	gpv.setHeadingName("To Department");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("feedback_allot_to");
	gpv.setHeadingName("Allot To");
	gpv.setAlign("center");
	gpv.setWidth(100);
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
	gpv.setHeadingName("CAPA");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("spare_used");
	gpv.setHeadingName("RCA");
	gpv.setAlign("center");
	gpv.setWidth(100);
	feedbackColumnNames.add(gpv);
	}

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

	if (!getFeedStatus().equals("Pending"))
	{
	gpv = new GridDataPropertyView();
	gpv.setColomnName("action_by");
	gpv.setHeadingName("Action By");
	gpv.setAlign("center");
	feedbackColumnNames.add(gpv);
	}
	}

	@SuppressWarnings("unchecked")
	public String getFeedbackDetail()
	{
	// System.out.println("Inside getFeedback Data");
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
	HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();

	feedbackList = new ArrayList<FeedbackPojo>();
	// int count = 0 ;
	List data = new ArrayList();
	String dept_subdept_id = "", userType = "", emp_Name = "";

	List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
	if (empData != null && empData.size() > 0)
	{
	for (Iterator iterator = empData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	emp_Name = object[0].toString();
	dept_subdept_id = object[3].toString();
	userType = object[7].toString();
	}
	}

	if (dept_subdept_id != null && !dept_subdept_id.equals(""))
	{
	// System.out.println("Inisde if  when dataflag M ");
	if (dataFlag.equals("M"))
	{
	// System.out.println("Inside If");
	toDate = DateUtil.convertDateToUSFormat(toDate);
	fromDate = DateUtil.convertDateToUSFormat(fromDate);
	}
	else
	{
	// System.out.println("Inside Else When there is no flag");
	toDate = toDate;
	fromDate = fromDate;
	}
	if (backData != null && !backData.equals("") && backData.equalsIgnoreCase("Y"))
	{
	data = HUA.getFeedbackDetail("feedback_status_15072014", getFeedStatus(), fromDate, toDate, dept_subdept_id, "", deptLevel, "feedback.id", "DESC", "HDM", emp_Name, userType,
	searchField, searchString, searchOper, connectionSpace);
	}
	else
	{
	data = HUA.getFeedbackDetail("feedback_status", getFeedStatus(), fromDate, toDate, dept_subdept_id, "", deptLevel, "feedback.id", "DESC", "HDM", emp_Name, userType,
	searchField, searchString, searchOper, connectionSpace);
	}

	// System.out.println("After geting data");
	}
	// System.out.println("Data Size is "+data.size());
	if (data != null && data.size() > 0)
	{
	setRecords(data.size());
	int to = (getRows() * getPage());
	@SuppressWarnings("unused")
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();

	feedbackList = HUH.setFeedbackValues(data, deptLevel, getFeedStatus());
	// System.out.println("@@@@@ "+feedbackList.size());
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

	public String getTicketDetails()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	String orgDetail = (String) session.get("orgDetail");
	String[] orgData = null;
	if(orgDetail!=null)
	{
	orgData = orgDetail.split("#");
	orgName = orgData[0];
	address = orgData[1];
	city = orgData[2];
	pincode = orgData[3];
	}
	
	System.out.println(orgData+" >>>> "+orgName);
	orgImgPath=new CommonWork().getOrganizationImage((SessionFactory)session.get("connectionSpace"));
	System.out.println("sdvnsdknvksdvb "+orgImgPath);
	fstatus  = new ActionOnFeedback().getTicketDetail(feedId,"id");
	
	/*String orgDetail = (String) session.get("orgDetail");
	String[] orgData = null;
	if (orgDetail != null && !orgDetail.equals(""))
	{
	orgData = orgDetail.split("#");
	orgName = orgData[0];
	address = orgData[1];
	city = orgData[2];
	pincode = orgData[3];
	
	
	}*/
	
	/*List dataList = null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	StringBuilder query = new StringBuilder();
	query.append("SELECT fstatus.ticket_no,fstatus.open_date,fstatus.open_time,dept.deptName AS bydepart,fstatus.feed_by,fCat.categoryName,fbtype.fbType,fstatus.status ,fstatus.feed_by_mobno,");
	query.append("deptTo.deptName AS todepart,emp.empName,emp.mobone,fsubCat.subCategoryName,fstatus.feed_brief,");
	query.append("fstatus.escalation_date,fstatus.escalation_time,subdept.subdeptname As tosubdept,fstatus.location,fstatus.Addr_date_time,subdeptby.subdeptname As bysubdept,fstatus.resolve_date,fstatus.resolve_time,fstatus.hp_reason,fstatus.spare_used,fstatus.feedback_remarks,");
	query.append("fstatus.sn_reason,fstatus.sn_on_date,fstatus.sn_on_time,fstatus.sn_upto_date,fstatus.sn_upto_time,fstatus.sn_duration,fstatus.ig_date,fstatus.ig_time,fstatus.ig_reason,fstatus.hp_date,fstatus.hp_time");
	query.append(" FROM feedback_status AS fstatus");
	query.append(" LEFT JOIN  department  AS dept  ON  dept.id =fstatus.by_dept_subdept");  
	 	query.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.id =fstatus.allot_to");
	query.append(" LEFT JOIN  department  AS deptTo  ON  deptTo.id =emp.deptname");
	query.append(" LEFT JOIN  subdepartment  AS subdept  ON  subdept.id =fstatus.to_dept_subdept");  
	query.append(" LEFT JOIN  subdepartment  AS subdeptby  ON  subdeptby.id =fstatus.by_dept_subdept");  

	query.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");  
	query.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
	query.append(" LEFT JOIN  feedback_type AS fbtype ON fbtype.id = fCat.fbType");
	query.append(" WHERE fstatus.id="+feedId);
	System.out.println("Query : "+query.toString()+   "    FEED ID:  "+feedId);
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	
	 
	 	 fstatus=new FeedbackPojo();
	if (dataList != null && dataList.size() > 0)
	{
	 
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fstatus.setTicket_no(CUA.getValueWithNullCheck(object[0]));
	if (object[1] != null)
	fstatus.setOpen_date( DateUtil.convertDateToIndianFormat(object[1].toString()));  
	fstatus.setOpen_time(CUA.getValueWithNullCheck(object[2]));
	 fstatus.setOpen_time(CUA.getValueWithNullCheck(object[2]).substring(0, CUA.getValueWithNullCheck(object[2]).length() - 3));
	fstatus.setFeedback_by_dept(CUA.getValueWithNullCheck(object[3]));
	fstatus.setFeed_by(CUA.getValueWithNullCheck(object[4]));
	fstatus.setFeedback_catg(CUA.getValueWithNullCheck(object[5]));
	fstatus.setFeedtype(CUA.getValueWithNullCheck(object[6]));
	fstatus.setStatus(CUA.getValueWithNullCheck(object[7]));
	fstatus.setFeedback_by_mobno(CUA.getValueWithNullCheck(object[8]));
	fstatus.setFeedback_to_dept(CUA.getValueWithNullCheck(object[9]));
	fstatus.setEmpName(CUA.getValueWithNullCheck(object[10]));
	
	fstatus.setMobOne(CUA.getValueWithNullCheck(object[11]));
	 fstatus.setFeedback_subcatg(CUA.getValueWithNullCheck(object[12]));
	fstatus.setFeed_brief(CUA.getValueWithNullCheck(object[13]));
	if (object[14] != null)
	fstatus.setEscalation_date( DateUtil.convertDateToIndianFormat(object[14].toString()));  
	fstatus.setEscalation_time(CUA.getValueWithNullCheck(object[15]));
	fstatus.setFeedback_to_subdept (CUA.getValueWithNullCheck(object[16]));
	fstatus.setLocation (CUA.getValueWithNullCheck(object[17]));
	 
	fstatus.setAddr_date_time(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[18]).split("#")[0].trim())+"  "+CUA.getValueWithNullCheck(object[18]).split("#")[1].trim());
	fstatus.setAddr_date_time(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[18]).split("#")[0].trim())+"  "+CUA.getValueWithNullCheck(object[18]).split("#")[1].trim().substring(0, CUA.getValueWithNullCheck(object[18]).split("#")[1].trim().length() - 3));
	fstatus.setFeedback_by_subdept(CUA.getValueWithNullCheck(object[19]));
	  	if (object[20] != null)
	fstatus.setResolve_date( DateUtil.convertDateToIndianFormat(object[20].toString()));  
	else
	fstatus.setResolve_date("NA");  
	
	fstatus.setResolve_time(CUA.getValueWithNullCheck(object[21]));
	  	if (object[21] != null)
	fstatus.setResolve_time((object[21].toString()).substring(0, (object[21].toString()).length() - 3));
	else
	fstatus.setResolve_time("NA");  
	fstatus.setHp_reason(CUA.getValueWithNullCheck(object[22]));
	  
	fstatus.setSpare_used(CUA.getValueWithNullCheck(object[23]));
	fstatus.setResolve_remark(CUA.getValueWithNullCheck(object[24]));

	
	
	fstatus.setSn_reason(CUA.getValueWithNullCheck(object[25]));
	if (object[26] != null)
	fstatus.setSn_on_date(DateUtil.convertDateToIndianFormat(object[26].toString()));
	else
	fstatus.setSn_on_date("NA");
	
	fstatus.setSn_on_time(CUA.getValueWithNullCheck(object[27]));
	if (object[28] != null)
	fstatus.setSn_upto_date(DateUtil.convertDateToIndianFormat(object[28].toString()));
	else
	fstatus.setSn_upto_date("NA");
	
	fstatus.setSn_upto_time(CUA.getValueWithNullCheck(object[29]));
	fstatus.setSn_duration(CUA.getValueWithNullCheck(object[30]));
	
	
	
	
	if (object[31] != null)
	fstatus.setIg_date(DateUtil.convertDateToIndianFormat(object[31].toString()));
	else
	fstatus.setIg_date("NA");
	fstatus.setIg_time(CUA.getValueWithNullCheck(object[32]));
	fstatus.setIg_reason(CUA.getValueWithNullCheck(object[33]));
	 
	
	

	if (object[34] != null)
	fstatus.setHp_date(DateUtil.convertDateToIndianFormat(object[34].toString()));
	else
	fstatus.setHp_date("NA");
	fstatus.setHp_time(CUA.getValueWithNullCheck(object[35]));
	 	
	setOrgName(orgName);
	setAddress(address);
	setCity(city);
	setPincode(pincode);
	
	 	session.put("status", CUA.getValueWithNullCheck(object[7]));
	}
	}*/
	
	/*	String Addressing_date_time = new HelpdeskUniversalAction().getData("feedback_status", "Addr_date_time", "ticket_no", ticket_no);
	// System.out.println("Addressing Date time  "+Addressing_date_time);
	setTicket_no(ticket_no);
	setFeedbackBy(feedbackBy);
	setMobileno(mobileno);
	setOpen_date(open_date);
	setOpen_time(open_time);
	setCatg(catg);
	setFeed_brief(feed_brief);
	setLocation(location);
	setAllotto(allotto);
	setTodept(todept);
	setTosubdept(tosubdept);
	if (Addressing_date_time != null && !Addressing_date_time.equals("") && !Addressing_date_time.equals("NA"))
	{
	setAddrDate(DateUtil.convertDateToUSFormat(Addressing_date_time.substring(0, 10)) + " / " + Addressing_date_time.substring(11, 16));
	}
	else
	{
	setAddrDate(open_date + " / " + open_time);
	}

	// setAddrTime(addrTime);

	setOrgName(orgName);
	setAddress(address);
	setCity(city);
	setPincode(pincode); */

	/*	if (feedStatus.equalsIgnoreCase("Resolved"))
	{
	setResolveon(resolveon);
	setResolveat(resolveat);
	setActiontaken(actiontaken);
	setSpareused(spareused);
	}   */
	returnResult = SUCCESS;
	}
	catch (Exception e)
	{
	addActionError("Ooops!!! There is some problem in getTicketDetails Method");
	e.printStackTrace();
	}
	}
	else
	{
	returnResult = LOGIN;
	}
	// System.out.println(returnResult+"sdvc nsd vdn");
	return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getResolverData()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
	resolverList = new LinkedHashMap<Integer, String>();

	String dept_subdept = "";
	String allot_to_id = "";
	@SuppressWarnings("unused")
	String sub_catg = "";

	@SuppressWarnings("unused")
	createTable cbt = new createTable();
	List empdata = new ArrayList<String>();
	List allotto_data = new ArrayList<String>();

	String tolevel = "";
	List feedbackList = HUA.getMultipleColumns("feedback_status", "to_dept_subdept", "subCatg", "allot_to", "level", "", "id", feedid, "", "", connectionSpace);
	if (feedbackList != null && feedbackList.size() > 0)
	{
	for (Iterator iterator = feedbackList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	dept_subdept = object[0].toString();
	sub_catg = object[1].toString();
	allot_to_id = object[2].toString();
	tolevel = object[3].toString().substring(5);
	}
	if (allot_to_id != null && !allot_to_id.equals(""))
	{
	allotto_data = HUA.getEmployeeData(allot_to_id, deptLevel);
	}

	String deptid = HUA.getData("subdepartment", "deptid", "id", dept_subdept);
	empdata = HUA.getEmp4EscInAllDept(deptid, deptLevel, "", tolevel, connectionSpace);
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
	resolverList.put((Integer) dataC_new[4], dataC_new[0].toString());
	}
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
	else
	{
	returnResult = LOGIN;
	}
	return returnResult;

	}

	@SuppressWarnings("unchecked")
	public String updateFeedbackStatus()
	{

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	if (status != null && !status.equals("-1") && !status.equalsIgnoreCase("Hold"))
	{
	HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	MsgMailCommunication MMC = new MsgMailCommunication();
	FeedbackPojo fbp = new FeedbackPojo();
	String duration = "NA";
	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();
	String snDate = "", snTime = "", snUpToDate = "", snUpToTime = "", snDuration = "", escDate = null, escTime = null, moduleName = null;
	
	
	
	
	
	InsertDataTable ob = null;
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	 
	 if(getFeedid()!=null && getStatus().equalsIgnoreCase("Resolved"))
	{
	 
	ob = new InsertDataTable();
	ob.setColName("feedbackStatusId");
	ob.setDataName(getFeedid());
	insertData.add(ob);
	
	// insert task id into compliance_task_KR table
	String compId[] = request.getParameterValues("checkid");
	if(compId!=null && compId.length>0)
	{
	for (int i = 0; i < compId.length; i++)
	{
	ob = new InsertDataTable();
	ob.setColName("completionTipId");
	ob.setDataName(compId[i]);
	insertData.add(ob);
	cbt.insertIntoTable("feedback_completionTip_details", insertData, connectionSpace);
	 
	insertData.remove(insertData.size()-1);
	}
	}
	
	}
	StringBuilder query = new StringBuilder();
	query.append("SELECT sn_on_date,sn_on_time,sn_upto_date,sn_upto_time,sn_duration,escalation_date,escalation_time,moduleName,open_date,open_time FROM feedback_status");
	query.append(" WHERE id =" + getFeedid());
	List ticketData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if (ticketData != null && ticketData.size() > 0)
	{
	for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && !object[0].toString().equals(""))
	{
	snDate = object[0].toString();
	}
	else
	{
	snDate = "NA";
	}
	if (object[1] != null && !object[1].toString().equals(""))
	{
	snTime = object[1].toString();
	}
	else
	{
	snTime = "NA";
	}
	if (object[2] != null && !object[2].toString().equals(""))
	{
	snUpToDate = object[2].toString();
	}
	else
	{
	snUpToDate = "NA";
	}
	if (object[3] != null && !object[3].toString().equals(""))
	{
	snUpToTime = object[3].toString();
	}
	else
	{
	snUpToTime = "NA";
	}
	if (object[4] != null && !object[4].toString().equals(""))
	{
	snDuration = object[4].toString();
	}
	else
	{
	snDuration = "NA";
	}

	if (object[5] != null && !object[5].toString().equals(""))
	{
	escDate = object[5].toString();
	}

	if (object[6] != null && !object[6].toString().equals(""))
	{
	escTime = object[6].toString();
	}

	if (object[7] != null && !object[7].toString().equals(""))
	{
	moduleName = object[7].toString();
	}
	if (object[8] != null && !object[8].toString().equals(""))
	{
	open_date = object[8].toString();
	open_time = object[9].toString();
	}
	}
	}

	if (getStatus().equalsIgnoreCase("Resolved"))
	{
	String cal_duration = "";
	if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
	{
	duration = DateUtil.time_difference(open_date, open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
	}
	if (snDuration != null && !snDuration.equals("") && !snDuration.equals("NA"))
	{
	boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
	if (flag)
	{
	cal_duration = DateUtil.getTimeDifference(duration, snDuration);
	if (cal_duration != null && !cal_duration.equals("") && !cal_duration.contains("-"))
	{
	duration = cal_duration;
	}
	}
	else
	{
	String newduration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), snUpToDate, snUpToTime);
	if (newduration != null && !newduration.equals("") && !newduration.equals("NA"))
	{
	newduration = DateUtil.getTimeDifference(snDuration, newduration);
	String new_cal_duration = DateUtil.getTimeDifference(duration, newduration);
	if (new_cal_duration != null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
	{
	duration = new_cal_duration;
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
	wherClause.put("close_mode", "Online");
	wherClause.put("spare_used", getSpareused());
	condtnBlock.put("id", getFeedid());
	}
	else if (getStatus().equalsIgnoreCase("Snooze"))
	{
	WorkingHourAction WHA = new WorkingHourAction();
	WorkingHourHelper WHH = new WorkingHourHelper();
	String newVartualSnoozeTime = "00:00";
	if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.convertDateToUSFormat(snoozeDate)))
	{
	newVartualSnoozeTime = snoozeTime;
	}
	else
	{
	newVartualSnoozeTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
	}
	String workingTime = "00:00";
	if (DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(), escDate))
	{
	workingTime = WHH.getWorkingHrs(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), connectionSpace, cbt, moduleName, escDate);
	}
	// System.out.println("Total Working Hrs "+workingTime);
	List<String> dataList = WHH.getDayDate(DateUtil.getCurrentDateUSFormat(), connectionSpace, cbt, moduleName);
	String date = dataList.get(0).toString();
	String day = dataList.get(1).toString();
	// System.out.println("workingTime before add" +
	// workingTime);

	if (date.equals(DateUtil.getCurrentDateUSFormat()))
	{
	List tempList = WHH.getWorkingTimeDetails(day, connectionSpace, cbt, moduleName);
	if (tempList != null && tempList.size() > 0)
	{
	String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
	for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fromTime = object[0].toString();
	toTime = object[1].toString();
	workingHrs = object[2].toString();
	}
	if (DateUtil.checkTwoTimeWithMilSec(fromTime, DateUtil.getCurrentTimeHourMin()) && DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), toTime))
	{
	String todayRemainingWorkingTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), toTime);
	workingTime = DateUtil.addTwoTime(workingTime, todayRemainingWorkingTime);
	}
	else if (DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), fromTime))
	{
	workingTime = DateUtil.addTwoTime(workingTime, workingHrs);
	}
	// System.out.println("Working Time "+workingTime);
	}
	}
	if (DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(), escDate))
	{
	String escDay = DateUtil.getDayofDate(escDate);
	List tempList = WHH.getWorkingTimeDetails(escDay, connectionSpace, cbt, moduleName);
	if (tempList != null && tempList.size() > 0)
	{
	String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
	for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fromTime = object[0].toString();
	toTime = object[1].toString();
	workingHrs = object[2].toString();
	}
	// System.out.println(escDate+" >>>> "+
	// fromTime+" >>>> "+ escDate+" >>>> "+
	// escTime);
	String remainingWorkingTimeOnEscDate = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), escDate, escTime);
	// System.out.println(remainingWorkingTimeOnEscDate
	// + " >>> remainingWorkingTimeOnEscDate");
	workingTime = DateUtil.addTwoTime(workingTime, remainingWorkingTimeOnEscDate);

	}
	}
	// System.out.println(" newVartualSnoozeTime " +
	// newVartualSnoozeTime + " >>>>>" + workingTime);
	List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, newVartualSnoozeTime, workingTime, "Y", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), moduleName);
	if (dateTime != null && dateTime.size() > 0)
	{
	if (dateTime.get(0) != null && dateTime.get(1) != null)
	duration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), dateTime.get(0), dateTime.get(1));

	// System.out.println("Snooze Date " +
	// dateTime.get(0) + " >>> " + dateTime.get(1));
	// System.out.println("Escalation Date " +
	// dateTime.get(2) + " >>> " + dateTime.get(3));
	/*
	 * if(dateTime.get(2)!=null &&
	 * dateTime.get(3)!=null) { escDate =
	 * dateTime.get(2); escTime = dateTime.get(3); }
	 */

	wherClause.put("status", getStatus());
	wherClause.put("sn_reason", DateUtil.makeTitle(getSnoozecomment()));
	if (snDate.equals("NA") || snTime.equals("NA") || snDate.equals("") || snTime.equals(""))
	{
	wherClause.put("sn_on_date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("sn_on_time", DateUtil.getCurrentTime());
	}
	wherClause.put("sn_upto_date", dateTime.get(0));
	wherClause.put("sn_upto_time", dateTime.get(1));
	wherClause.put("sn_duration", duration);
	wherClause.put("escalation_date", dateTime.get(2));
	wherClause.put("escalation_time", dateTime.get(3));
	wherClause.put("action_by", userName);
	condtnBlock.put("id", getFeedid());
	}

	/*
	 * if (snDate.equals("NA") || snTime.equals("NA")) { if
	 * (snoozeDate != null && !snoozeDate.equals("") &&
	 * snoozeTime != null && !snoozeTime.equals("")) {
	 * duration =
	 * DateUtil.time_difference(DateUtil.getCurrentDateUSFormat
	 * (), DateUtil.getCurrentTime(),
	 * DateUtil.convertDateToUSFormat(snoozeDate),
	 * snoozeTime); } } else { if (snDuration!=null &&
	 * !snDuration.equals("") && !snDuration.equals("NA")) {
	 * duration = DateUtil.time_difference
	 * (DateUtil.getCurrentDateUSFormat(),
	 * DateUtil.getCurrentTime(),
	 * DateUtil.convertDateToUSFormat(snoozeDate),
	 * snoozeTime); duration =
	 * DateUtil.addTwoTime(snDuration, duration); } else {
	 * if (snoozeDate != null && !snoozeDate.equals("") &&
	 * snoozeTime != null && !snoozeTime.equals("")) {
	 * duration = DateUtil.time_difference(snDate,snTime,
	 * DateUtil.convertDateToUSFormat(snoozeDate),
	 * snoozeTime); } } }
	 * 
	 * wherClause.put("status", getStatus());
	 * wherClause.put("sn_reason",
	 * DateUtil.makeTitle(getSnoozecomment())); if
	 * (snDate.equals("NA") || snTime.equals("NA")) {
	 * wherClause.put("sn_on_date",
	 * DateUtil.getCurrentDateUSFormat());
	 * wherClause.put("sn_on_time",
	 * DateUtil.getCurrentTime()); }
	 * wherClause.put("sn_upto_date",
	 * DateUtil.convertDateToUSFormat(snoozeDate));
	 * wherClause.put("sn_upto_time", snoozeTime);
	 * wherClause.put("sn_duration", duration);
	 * wherClause.put("action_by", userName);
	 * condtnBlock.put("id", getFeedid());
	 */
	}
	else if (getStatus().equalsIgnoreCase("High Priority"))
	{
	wherClause.put("status", getStatus());
	wherClause.put("hp_date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("hp_time", DateUtil.getCurrentTime());
	wherClause.put("hp_reason", DateUtil.makeTitle(getHpcomment()));
	wherClause.put("action_by", userName);
	wherClause.put("hp_mode", "Online");
	condtnBlock.put("id", getFeedid());
	}
	else if (getStatus().equalsIgnoreCase("Ignore"))
	{
	wherClause.put("status", getStatus());
	wherClause.put("ig_date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("ig_time", DateUtil.getCurrentTime());
	wherClause.put("ig_reason", DateUtil.makeTitle(getIgnorecomment()));
	wherClause.put("action_by", userName);
	wherClause.put("ignore_mode", "Online");
	condtnBlock.put("id", getFeedid());
	}
	else if (getStatus().equalsIgnoreCase("Re-Assign"))
	{
	String feedBy = "NA", mobNo = "NA", emailId = "NA", bysubdept = "NA", feedBrief = "NA", location = "NA";
	List existTicketData = new HelpdeskUniversalHelper().getTransferTicketData(getFeedid(), connectionSpace);
	if (existTicketData != null && existTicketData.size() > 0)
	{
	for (Iterator iterator = existTicketData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && !object[0].toString().equals(""))
	{
	feedBy = object[0].toString();
	}
	if (object[1] != null && !object[1].toString().equals(""))
	{
	mobNo = object[1].toString();
	}
	if (object[2] != null && !object[2].toString().equals(""))
	{
	emailId = object[2].toString();
	}
	if (object[3] != null && !object[3].toString().equals(""))
	{
	bysubdept = object[3].toString();
	}
	if (object[4] != null && !object[4].toString().equals(""))
	{
	feedBrief = object[4].toString();
	}
	if (object[5] != null && !object[5].toString().equals(""))
	{
	location = object[5].toString();
	}
	}
	boolean flag = transferComplaint(feedBy, mobNo, emailId, bysubdept, feedBrief, location, getFeedid());
	if (flag)
	{
	wherClause.put("status", "Transfer");
	wherClause.put("transfer_date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("transfer_time", DateUtil.getCurrentTime());
	wherClause.put("transfer_reason", DateUtil.makeTitle(getReAssignRemark()));
	wherClause.put("action_by", userName);
	condtnBlock.put("id", getFeedid());
	}
	}
	}
	boolean updateFeed = HUH.updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
	if (updateFeed)
	{
	{
	String levelMsg = "", complainatMsg = "", mailText = "", mailSubject = "", mailheading = "";
	List data = HUH.getFeedbackDetail("", "SD", getStatus(), getFeedid(), connectionSpace);
	if (data != null && data.size() > 0)
	{
	fbp = HUH.setFeedbackDataValues(data, getStatus(), deptLevel);
	}

	if (getStatus().equalsIgnoreCase("Resolved"))
	{
	levelMsg = "Close Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + remark
	+ " is Closed.";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + remark + " is Closed.";
	mailSubject = "Close Ticket Alert: " + fbp.getTicket_no();
	mailheading = "Close Ticket Alert: " + fbp.getTicket_no();
	}
	else if (getStatus().equalsIgnoreCase("High Priority"))
	{
	levelMsg = "High Priority Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By : " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: "
	+ fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + ".";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: "
	+ fbp.getHp_reason() + " is on High Priority.";
	mailSubject = "High Priority Feedback Alert: " + fbp.getTicket_no();
	mailheading = "High Priority Case Ticket Alert";
	}
	else if (getStatus().equalsIgnoreCase("Snooze"))
	{
	levelMsg = "Snooze Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief()
	+ " will be Snoozed till " + fbp.getSn_duration() + ",Reason: " + fbp.getSn_reason() + ".";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief()
	+ ",Status: Snoozed to " + DateUtil.convertDateToIndianFormat(fbp.getSn_on_date()) + " " + fbp.getSn_on_time() + " Hrs, Reason:" + fbp.getSn_reason() + ".";
	mailSubject = "Snooze Feedback Alert: " + fbp.getTicket_no();
	mailheading = "Snooze Case Ticket Alert";
	}
	else if (getStatus().equalsIgnoreCase("Ignore"))
	{
	levelMsg = "Ignore Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief()
	+ ",Reason: " + fbp.getIg_reason() + " should be Ignored.";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: "
	+ fbp.getIg_reason() + " is Ignored.";
	mailSubject = "Ignore Feedback Alert: " + fbp.getTicket_no();
	mailheading = "Ignore Case Ticket Alert";
	}

	if (getStatus().equalsIgnoreCase("Resolved"))
	{
	if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10
	&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
	{
	MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticket_no, getStatus(), "0", "HDM");
	}

	if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
	{
	mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false);
	MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "HDM");
	}

	if (fbp.getResolve_by_mobno() != null && fbp.getResolve_by_mobno() != "" && fbp.getResolve_by_mobno().trim().length() == 10
	&& (fbp.getResolve_by_mobno().startsWith("9") || fbp.getResolve_by_mobno().startsWith("8") || fbp.getResolve_by_mobno().startsWith("7")))
	{
	MMC.addMessage(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_mobno(), levelMsg, ticket_no, getStatus(), "0", "HDM");
	}

	if (fbp.getResolve_by_emailid() != null && !fbp.getResolve_by_emailid().equals(""))
	{
	mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true);
	MMC.addMail(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_emailid(), mailSubject, mailText, ticket_no, "Pending", "0", "", "HDM");
	}

	}
	else if (getStatus().equalsIgnoreCase("High Priority") || getStatus().equalsIgnoreCase("Snooze") || getStatus().equalsIgnoreCase("Ignore"))
	{

	if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10
	&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
	{
	MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticket_no, getStatus(), "0", "HDM");
	}

	if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
	{
	mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false);
	MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "HDM");
	}

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
	}
	}
	else
	{
	complaintid = feedid;
	moduleName = "HDM";
	if (approvalDocFileName != null)
	{
	String renameFilePath = new CreateFolderOs().createUserDir("SeekApprove_Data") + "//" + DateUtil.mergeDateTime() + approvalDocFileName;
	String storeFilePath = new CreateFolderOs().createUserDir("SeekApprove_Data") + "//" + approvalDocFileName;
	String str = renameFilePath.replace("//", "/");
	// renameFilePath=DateUtil.removeSpace(renameFilePath);
	// storeFilePath=DateUtil.removeSpace(storeFilePath);
	if (storeFilePath != null)
	{
	File theFile = new File(storeFilePath);
	File newFileName = new File(str);
	if (theFile != null)
	{
	try
	{
	FileUtils.copyFile(approvalDoc, theFile);
	if (theFile.exists())
	theFile.renameTo(newFileName);
	}
	catch (IOException e)
	{
	e.printStackTrace();
	}
	storedDocPath = renameFilePath;
	}
	}
	}
	returnResult = "ForwoardForApproval";

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
	
	
	
	/*
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	if (status != null && !status.equals("-1") && !status.equalsIgnoreCase("Hold"))
	{	
	HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	// HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
	MsgMailCommunication MMC = new MsgMailCommunication();
	FeedbackPojo fbp = new FeedbackPojo();
	String duration = "NA";
	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();
	
	String snDate = "", snTime = "", snUpToDate = "", snUpToTime = "", snDuration = "", escDate = null, escTime = null,moduleName=null;
	// List ticketData = new
	// HelpdeskUniversalAction().getMultipleColumns("feedback_status",
	// "sn_on_date", "sn_on_time",
	// "sn_upto_date","sn_upto_time","sn_duration", "id",
	// getFeedid(), "", "", connectionSpace);
	StringBuilder query = new StringBuilder();
	query.append("SELECT sn_on_date,sn_on_time,sn_upto_date,sn_upto_time,sn_duration,escalation_date,escalation_time,moduleName FROM feedback_status");
	query.append(" WHERE id =" + getFeedid());
	List ticketData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	if (ticketData != null && ticketData.size() > 0)
	{
	for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && !object[0].toString().equals(""))
	{
	snDate = object[0].toString();
	}
	else
	{
	snDate = "NA";
	}
	if (object[1] != null && !object[1].toString().equals(""))
	{
	snTime = object[1].toString();
	}
	else
	{
	snTime = "NA";
	}
	if (object[2] != null && !object[2].toString().equals(""))
	{
	snUpToDate = object[2].toString();
	}
	else
	{
	snUpToDate = "NA";
	}
	if (object[3] != null && !object[3].toString().equals(""))
	{
	snUpToTime = object[3].toString();
	}
	else
	{
	snUpToTime = "NA";
	}
	if (object[4] != null && !object[4].toString().equals(""))
	{
	snDuration = object[4].toString();
	}
	else
	{
	snDuration = "NA";
	}
	
	if (object[5] != null && !object[5].toString().equals(""))
	{
	escDate = object[5].toString();
	}
	
	if (object[6] != null && !object[6].toString().equals(""))
	{
	escTime = object[6].toString();
	}
	
	if (object[7] != null && !object[7].toString().equals(""))
	{
	moduleName = object[7].toString();
	}
	}
	}
	
	if (getStatus().equalsIgnoreCase("Resolved"))
	{
	String cal_duration = "";
	if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
	{
	duration = DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
	}
	if (snDuration != null && !snDuration.equals("") && !snDuration.equals("NA"))
	{
	boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
	if (flag)
	{
	// System.out.println("Inside Second If Block");
	cal_duration = DateUtil.getTimeDifference(duration, snDuration);
	if (cal_duration != null && !cal_duration.equals("") && !cal_duration.contains("-"))
	{
	duration = cal_duration;
	}
	}
	else
	{
	// System.out.println("Inside Else Block");
	String newduration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), snUpToDate, snUpToTime);
	// String newduration =
	// DateUtil.time_difference(snDate, snTime,
	// DateUtil.getCurrentDateUSFormat(),
	// DateUtil.getCurrentTime());
	if (newduration != null && !newduration.equals("") && !newduration.equals("NA"))
	{
	newduration = DateUtil.getTimeDifference(snDuration, newduration);
	String new_cal_duration = DateUtil.getTimeDifference(duration, newduration);
	if (new_cal_duration != null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
	{
	duration = new_cal_duration;
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
	condtnBlock.put("id", getFeedid());
	}
	else if (getStatus().equalsIgnoreCase("Snooze"))
	{
	WorkingHourAction WHA = new WorkingHourAction();
	WorkingHourHelper WHH = new WorkingHourHelper();
	String newVartualSnoozeTime = "00:00";
	if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.convertDateToUSFormat(snoozeDate)))
	{
	newVartualSnoozeTime = snoozeTime;
	}
	else
	{
	newVartualSnoozeTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
	}
	//    System.out.println("newVartualSnoozeTime " + newVartualSnoozeTime+" >>> "+escDate);
	// String grossEscTime =
	// DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(),
	// DateUtil.getCurrentTimeHourMin(), escDate, escTime);
	String workingTime = "00:00";
	if(!DateUtil.getCurrentDateUSFormat().equals(escDate))
	{
	workingTime=WHH.getWorkingHrs(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), connectionSpace, cbt, moduleName, escDate);
	}
	//	System.out.println("Total Working Hrs "+workingTime);
	List<String> dataList = WHH.getDayDate(DateUtil.getCurrentDateUSFormat(), connectionSpace, cbt, moduleName);
	String date = dataList.get(0).toString();
	String day = dataList.get(1).toString();
	//	System.out.println("workingTime before add" + workingTime);

	if (date.equals(DateUtil.getCurrentDateUSFormat()))
	{
	List tempList = WHH.getWorkingTimeDetails(day, connectionSpace, cbt, moduleName);
	if (tempList != null && tempList.size() > 0)
	{
	String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
	for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fromTime = object[0].toString();
	toTime = object[1].toString();
	workingHrs = object[2].toString();
	}
	if (DateUtil.checkTwoTimeWithMilSec(fromTime, DateUtil.getCurrentTimeHourMin()) && DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), toTime))
	{
	String todayRemainingWorkingTime = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(),
	toTime);
	workingTime = DateUtil.addTwoTime(workingTime, todayRemainingWorkingTime);
	}
	else if (DateUtil.checkTwoTimeWithMilSec(DateUtil.getCurrentTimeHourMin(), fromTime))
	{
	workingTime = DateUtil.addTwoTime(workingTime, workingHrs);
	}
	//	System.out.println("Working Time "+workingTime);
	}
	}
	String escDay = DateUtil.getDayofDate(escDate);
	List tempList = WHH.getWorkingTimeDetails(escDay, connectionSpace, cbt, moduleName);
	if (tempList != null && tempList.size() > 0)
	{
	String fromTime = "00:00", toTime = "00:00", workingHrs = "00:00";
	for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fromTime = object[0].toString();
	toTime = object[1].toString();
	workingHrs = object[2].toString();
	}
	//	System.out.println(escDate+" >>>> "+ fromTime+" >>>> "+ escDate+" >>>> "+ escTime);
	String remainingWorkingTimeOnEscDate = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), escDate, escTime);
	//	System.out.println(remainingWorkingTimeOnEscDate + " >>> remainingWorkingTimeOnEscDate");
	workingTime = DateUtil.addTwoTime(workingTime, remainingWorkingTimeOnEscDate);

	}
	//	System.out.println(" newVartualSnoozeTime " + newVartualSnoozeTime + " >>>>>" + workingTime);
	List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, newVartualSnoozeTime, workingTime, "Y", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(),
	moduleName);
	if (dateTime != null && dateTime.size() > 0)
	{
	if (dateTime.get(0) != null && dateTime.get(1) != null)
	duration = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), dateTime.get(0), dateTime.get(1));

	//	System.out.println("Snooze Date " + dateTime.get(0) + " >>> " + dateTime.get(1));
	//	System.out.println("Escalation Date " + dateTime.get(2) + " >>> " + dateTime.get(3));
	
	 * if(dateTime.get(2)!=null && dateTime.get(3)!=null) {
	 * escDate = dateTime.get(2); escTime = dateTime.get(3);
	 * }
	 

	wherClause.put("status", getStatus());
	wherClause.put("sn_reason", DateUtil.makeTitle(getSnoozecomment()));
	if (snDate.equals("NA") || snTime.equals("NA") || snDate.equals("") || snTime.equals(""))
	{
	wherClause.put("sn_on_date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("sn_on_time", DateUtil.getCurrentTime());
	}
	wherClause.put("sn_upto_date", dateTime.get(0));
	wherClause.put("sn_upto_time", dateTime.get(1));
	wherClause.put("sn_duration", duration);
	wherClause.put("escalation_date", dateTime.get(2));
	wherClause.put("escalation_time", dateTime.get(3));
	wherClause.put("action_by", userName);
	condtnBlock.put("id", getFeedid());
	}

	
	 * if (snDate.equals("NA") || snTime.equals("NA")) { if
	 * (snoozeDate != null && !snoozeDate.equals("") &&
	 * snoozeTime != null && !snoozeTime.equals("")) { duration
	 * =
	 * DateUtil.time_difference(DateUtil.getCurrentDateUSFormat
	 * (), DateUtil.getCurrentTime(),
	 * DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
	 * } } else { if (snDuration!=null && !snDuration.equals("")
	 * && !snDuration.equals("NA")) { duration =
	 * DateUtil.time_difference
	 * (DateUtil.getCurrentDateUSFormat(),
	 * DateUtil.getCurrentTime(),
	 * DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
	 * duration = DateUtil.addTwoTime(snDuration, duration); }
	 * else { if (snoozeDate != null && !snoozeDate.equals("")
	 * && snoozeTime != null && !snoozeTime.equals("")) {
	 * duration = DateUtil.time_difference(snDate,snTime,
	 * DateUtil.convertDateToUSFormat(snoozeDate), snoozeTime);
	 * } } }
	 * 
	 * wherClause.put("status", getStatus());
	 * wherClause.put("sn_reason",
	 * DateUtil.makeTitle(getSnoozecomment())); if
	 * (snDate.equals("NA") || snTime.equals("NA")) {
	 * wherClause.put("sn_on_date",
	 * DateUtil.getCurrentDateUSFormat());
	 * wherClause.put("sn_on_time", DateUtil.getCurrentTime());
	 * } wherClause.put("sn_upto_date",
	 * DateUtil.convertDateToUSFormat(snoozeDate));
	 * wherClause.put("sn_upto_time", snoozeTime);
	 * wherClause.put("sn_duration", duration);
	 * wherClause.put("action_by", userName);
	 * condtnBlock.put("id", getFeedid());
	 
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
	else if (getStatus().equalsIgnoreCase("Re-Assign"))
	{
	String feedBy = "NA", mobNo = "NA", emailId = "NA", bysubdept = "NA", feedBrief = "NA", location = "NA";
	List existTicketData = new HelpdeskUniversalHelper().getTransferTicketData(getFeedid(), connectionSpace);
	if (existTicketData != null && existTicketData.size() > 0)
	{
	for (Iterator iterator = existTicketData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	if (object[0] != null && !object[0].toString().equals(""))
	{
	feedBy = object[0].toString();
	}
	if (object[1] != null && !object[1].toString().equals(""))
	{
	mobNo = object[1].toString();
	}
	if (object[2] != null && !object[2].toString().equals(""))
	{
	emailId = object[2].toString();
	}
	if (object[3] != null && !object[3].toString().equals(""))
	{
	bysubdept = object[3].toString();
	}
	if (object[4] != null && !object[4].toString().equals(""))
	{
	feedBrief = object[4].toString();
	}
	if (object[5] != null && !object[5].toString().equals(""))
	{
	location = object[5].toString();
	}
	}
	boolean flag = transferComplaint(feedBy, mobNo, emailId, bysubdept, feedBrief, location, getFeedid());
	if (flag)
	{
	wherClause.put("status", "Transfer");
	wherClause.put("transfer_date", DateUtil.getCurrentDateUSFormat());
	wherClause.put("transfer_time", DateUtil.getCurrentTime());
	wherClause.put("transfer_reason", DateUtil.makeTitle(getReAssignRemark()));
	wherClause.put("action_by", userName);
	condtnBlock.put("id", getFeedid());
	}
	}
	}
	boolean updateFeed = HUH.updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
	if (updateFeed)
	{
	{
	String levelMsg = "", complainatMsg = "", mailText = "", mailSubject = "", mailheading = "";
	List data = HUH.getFeedbackDetail("", "SD", getStatus(), getFeedid(), connectionSpace);
	if (data != null && data.size() > 0)
	{
	fbp = HUH.setFeedbackDataValues(data, getStatus(), deptLevel);
	}
	
	if (getStatus().equalsIgnoreCase("Resolved"))
	{
	levelMsg = "Close Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: "
	+ fbp.getLocation() + ", Brief: " + remark + " is Closed.";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: "
	+ remark + " is Closed.";
	mailSubject = "Close Ticket Alert: " + fbp.getTicket_no();
	mailheading = "Close Ticket Alert: " + fbp.getTicket_no();
	}
	else if (getStatus().equalsIgnoreCase("High Priority"))
	{
	levelMsg = "High Priority Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By : " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: "
	+ fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + ".";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: "
	+ fbp.getFeed_brief() + ",Reason: " + fbp.getHp_reason() + " is on High Priority.";
	mailSubject = "High Priority Feedback Alert: " + fbp.getTicket_no();
	mailheading = "High Priority Case Ticket Alert";
	}
	else if (getStatus().equalsIgnoreCase("Snooze"))
	{
	levelMsg = "Snooze Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Location: " + fbp.getLocation()
	+ ", Brief: " + fbp.getFeed_brief() + " will be Snoozed till " + fbp.getSn_duration() + ",Reason: " + fbp.getSn_reason() + ".";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: "
	+ fbp.getFeed_brief() + ",Status: Snoozed to " + DateUtil.convertDateToIndianFormat(fbp.getSn_on_date()) + " " + fbp.getSn_on_time() + " Hrs, Reason:"
	+ fbp.getSn_reason() + ".";
	mailSubject = "Snooze Feedback Alert: " + fbp.getTicket_no();
	mailheading = "Snooze Case Ticket Alert";
	}
	else if (getStatus().equalsIgnoreCase("Ignore"))
	{
	levelMsg = "Ignore Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation()
	+ ", Brief: " + fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " should be Ignored.";
	complainatMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Ticket No: " + fbp.getTicket_no() + ", Location: " + fbp.getLocation() + ", Brief: "
	+ fbp.getFeed_brief() + ",Reason: " + fbp.getIg_reason() + " is Ignored.";
	mailSubject = "Ignore Feedback Alert: " + fbp.getTicket_no();
	mailheading = "Ignore Case Ticket Alert";
	}
	
	if (getStatus().equalsIgnoreCase("Resolved"))
	{
	if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10
	&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
	{
	MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticket_no, getStatus(), "0", "HDM");
	}
	
	if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
	{
	mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false);
	MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "HDM");
	}
	
	if (fbp.getResolve_by_mobno() != null && fbp.getResolve_by_mobno() != "" && fbp.getResolve_by_mobno().trim().length() == 10
	&& (fbp.getResolve_by_mobno().startsWith("9") || fbp.getResolve_by_mobno().startsWith("8") || fbp.getResolve_by_mobno().startsWith("7")))
	{
	MMC.addMessage(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_mobno(), levelMsg, ticket_no, getStatus(), "0", "HDM");
	}
	
	if (fbp.getResolve_by_emailid() != null && !fbp.getResolve_by_emailid().equals(""))
	{
	mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true);
	MMC.addMail(fbp.getResolve_by(), fbp.getFeedback_to_dept(), fbp.getResolve_by_emailid(), mailSubject, mailText, ticket_no, "Pending", "0", "", "HDM");
	}
	
	}
	else if (getStatus().equalsIgnoreCase("High Priority") || getStatus().equalsIgnoreCase("Snooze") || getStatus().equalsIgnoreCase("Ignore"))
	{
	
	if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10
	&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
	{
	MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainatMsg, ticket_no, getStatus(), "0", "HDM");
	}
	
	if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
	{
	mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false);
	MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "HDM");
	}
	
	if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10
	&& (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
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
	}
	}
	else
	{
	complaintid = feedid;
	moduleName = "HDM";
	if (approvalDocFileName != null)
	{
	String renameFilePath = new CreateFolderOs().createUserDir("SeekApprove_Data") + "//" + DateUtil.mergeDateTime()
	+ approvalDocFileName;
	String storeFilePath = new CreateFolderOs().createUserDir("SeekApprove_Data") + "//" + approvalDocFileName;
	String str = renameFilePath.replace("//", "/");
	// renameFilePath=DateUtil.removeSpace(renameFilePath);
	// storeFilePath=DateUtil.removeSpace(storeFilePath);
	if (storeFilePath != null)
	{
	File theFile = new File(storeFilePath);
	File newFileName = new File(str);
	if (theFile != null)
	{
	try
	{
	FileUtils.copyFile(approvalDoc, theFile);
	if (theFile.exists())
	theFile.renameTo(newFileName);
	}
	catch (IOException e)
	{
	e.printStackTrace();
	}
	storedDocPath = renameFilePath;
	}
	}
	}
	returnResult = "ForwoardForApproval";
	
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
	*/

	
	}

	@SuppressWarnings("unchecked")
	public boolean transferComplaint(String feedBy, String mobNo, String emailId, String bysubdept, String feedBrief, String location, String previousId)
	{
	boolean flag = false;
	try
	{
	CommonOperInterface cot = new CommonConFactory().createInterface();
	HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
	HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
	String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", allottoId = "", to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", tolevel = "", needesc = "";
	@SuppressWarnings("unused")
	String resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA";

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

	allotto1 = HUA.getRandomEmployee(tosubdept, deptLevel, tolevel.substring(5), allotto, connectionSpace);

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

	String deptid = HUA.getData("subdepartment", "deptid", "id", tosubdept);
	// Block for getting Ticket No
	ticketno = HelpdeskUniversalHelper.getTicketNo(deptid, "HDM", connectionSpace);
	// System.out.println("At last  Ticket No  "+ticketno);
	if (ticketno != null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId != null && !allottoId.equals(""))
	{
	// Setting the column values after getting from the page
	InsertDataTable ob = new InsertDataTable();
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	ob.setColName("ticket_no");
	ob.setDataName(ticketno);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("feed_by");
	ob.setDataName(feedBy);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("feed_by_mobno");
	ob.setDataName(mobNo);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("feed_by_emailid");
	ob.setDataName(emailId);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("deptHierarchy");
	ob.setDataName(deptLevel);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("by_dept_subdept");
	ob.setDataName(bysubdept);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("subcatg");
	ob.setDataName(feedbackSubCatgory);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("feed_brief");
	ob.setDataName(feedBrief);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("to_dept_subdept");
	ob.setDataName(tosubdept);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("allot_to");
	ob.setDataName(allottoId);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("escalation_date");
	ob.setDataName(escalation_date);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("escalation_time");
	ob.setDataName(escalation_time);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("open_date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("open_time");
	ob.setDataName(DateUtil.getCurrentTime());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("location");
	ob.setDataName(floor + "-" + location);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("level");
	ob.setDataName("Level1");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("status");
	ob.setDataName("Pending");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("via_from");
	ob.setDataName(DateUtil.makeTitle("Online"));
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("feed_registerby");
	ob.setDataName(userName);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("moduleName");
	ob.setDataName("HDM");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("transferId");
	ob.setDataName(previousId);
	insertData.add(ob);

	// Method calling for inserting the values in the
	// feedback_status table
	boolean status1 = cot.insertIntoTable("feedback_status", insertData, connectionSpace);
	if (status1)
	{
	flag = true;
	String levelMsg = "";
	List data = HUH.getFeedbackDetail(ticketno, deptLevel, "Pending", "", connectionSpace);
	FeedbackPojo fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);

	if (fbp != null)
	{
	MsgMailCommunication MMC = new MsgMailCommunication();
	levelMsg = "Open Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + ","
	+ fbp.getEscalation_time().subSequence(0, 5) + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + " , Brief: "
	+ fbp.getFeed_brief() + ".";
	if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10
	&& (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
	{
	MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "HDM");
	}
	if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
	{
	String mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, "Pending Ticket Alert : " + fbp.getTicket_no() + "", getStatus(), deptLevel, true);
	MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Pending Ticket Alert : " + fbp.getTicket_no() + "", mailText, ticket_no,
	"Pending", "0", "", "HDM");
	}
	}
	}
	insertData.clear();
	}

	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return flag;
	}

	// Method for Update Feedback Type (In Use)
	@SuppressWarnings("unchecked")
	public String modifyFeedSnooze()
	{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
	try
	{
	if (getOper().equalsIgnoreCase("edit"))
	{
	String updateQry = "update feedback_status set sn_reason='" + sn_reason + "',action_by='" + userName + "' where id='" + id + "'";
	new HelpdeskUniversalHelper().updateData(updateQry, connectionSpace);
	returnResult = SUCCESS;
	}
	}
	catch (Exception e)
	{
	returnResult = ERROR;
	e.printStackTrace();
	}
	}
	else
	{
	returnResult = LOGIN;
	}
	return returnResult;
	}

	public Map<String, String> getTicketDetails(String feedId)
	{
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	List dataList = null;
	StringBuilder NextEscalationTo = new StringBuilder();
	dataMap = new LinkedHashMap<String, String>();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	StringBuilder query = new StringBuilder();
	query.append("SELECT fstatus.ticket_no,fstatus.open_date,fstatus.open_time,dept.deptName AS bydepart,fstatus.feed_by,fCat.categoryName,fbtype.fbType,fstatus.status,fstatus.level,fstatus.feed_by_mobno,");
	query.append("deptTo.deptName AS todepart,emp.empName,emp.mobone,fsubCat.subCategoryName,fstatus.feed_brief,");
	query.append("fstatus.escalation_date,fstatus.escalation_time,fstatus.to_dept_subdept,fstatus.allot_to,emp1.empName AS resolveby,fstatus.resolve_date,fstatus.resolve_time,fstatus.resolve_duration,fstatus.subCatg");
	query.append(" FROM feedback_status AS fstatus");
	query.append(" LEFT JOIN  department  AS dept  ON  dept.id =fstatus.by_dept_subdept");
	query.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.id =fstatus.allot_to");
	query.append(" LEFT JOIN  employee_basic  AS emp1  ON  emp1.id =fstatus.resolve_by");
	query.append(" LEFT JOIN  department  AS deptTo  ON  deptTo.id =emp.deptname");
	query.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");
	query.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
	query.append(" LEFT JOIN  feedback_type AS fbtype ON fbtype.id = fCat.fbType");
	query.append(" WHERE fstatus.id=" + feedId);
	System.out.println("sdgjkvsdvsdv @@@@ " + query.toString());
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

	if (dataList != null && dataList.size() > 0)
	{

	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	ticket_no = CUA.getValueWithNullCheck(object[0]);
	if (object[1] != null)
	open_date = DateUtil.convertDateToIndianFormat(object[1].toString());
	else
	dataMap.put("Open Date", "NA");
	open_time = CUA.getValueWithNullCheck(object[2]).substring(0, CUA.getValueWithNullCheck(object[2]).length() - 3);

	dataMap.put("From Department", CUA.getValueWithNullCheck(object[3]));
	dataMap.put("Feedback By", CUA.getValueWithNullCheck(object[4]));

	dataMap.put("Category", CUA.getValueWithNullCheck(object[5]));
	dataMap.put("Feedback Type", CUA.getValueWithNullCheck(object[6]));

	dataMap.put("Current Status", CUA.getValueWithNullCheck(object[7]));
	dataMap.put("Current Level", CUA.getValueWithNullCheck(object[8]));
	dataMap.put("Mobile No.", CUA.getValueWithNullCheck(object[9]));
	dataMap.put("To Department", CUA.getValueWithNullCheck(object[10]));
	dataMap.put("Allotted To", CUA.getValueWithNullCheck(object[11]));
	dataMap.put("Requested By", CUA.getValueWithNullCheck(object[11]));
	allotto = CUA.getValueWithNullCheck(object[18]);
	dataMap.put("Mobile No", CUA.getValueWithNullCheck(object[12]));
	dataMap.put("Sub-Category", CUA.getValueWithNullCheck(object[13]));
	dataMap.put("Brief", CUA.getValueWithNullCheck(object[14]));
	dataMap.put("Lapse Time", DateUtil.time_difference(object[1].toString(), object[2].toString(), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin()));
	if (object[15] != null)
	dataMap.put("Next Escalation On", DateUtil.convertDateToIndianFormat(object[15].toString()) + ", " + CUA.getValueWithNullCheck(object[16]));
	else
	dataMap.put("Next Escalation On", "NA");

	String level = String.valueOf(Integer.parseInt(object[8].toString().substring(5, 6)) + 1);
	System.out.println("Main String : " + (object[8].toString()) + " level: " + level + "Sub String " + (object[8].toString().substring(5, 6)));
	List tempList = getEmp4Escalation(object[17].toString(), "HDM", level, connectionSpace);
	System.out.println("***tempList Size: " + tempList.size());
	if (tempList != null && tempList.size() > 0)
	{
	for (Iterator iterator1 = tempList.iterator(); iterator1.hasNext();)
	{
	Object[] object1 = (Object[]) iterator1.next();
	if (object[1] != null)
	NextEscalationTo.append(object1[1].toString() + ", ");
	else
	NextEscalationTo.append("NA");

	}
	}

	if (NextEscalationTo.toString().length() != 0)
	dataMap.put("Next Escalation To", NextEscalationTo.toString().substring(0, NextEscalationTo.toString().length() - 2));
	else
	dataMap.put("Next Escalation To", "NA");

	dataMap.put("Resolved By", CUA.getValueWithNullCheck(object[19]));
	if (object[20] != null)
	dataMap.put("Resolved On", DateUtil.convertDateToIndianFormat(object[20].toString()) + ", " + object[21].toString().substring(0, object[21].toString().length() - 3));

	dataMap.put("Resolve Duration", CUA.getValueWithNullCheck(object[22]));
	
	
	if((object[23])!=null)
	{
	subCatg=object[23].toString();
	String query1="SELECT id,completion_tip FROM  feedback_completion_tip WHERE subCatId="+object[23].toString()+" ORDER BY completion_tip";
	List dataList1 = new createTable().executeAllSelectQuery(query1, connectionSpace);
	if(dataList1!=null && dataList1.size()>0)
	{
	for (Iterator iterator2 = dataList1.iterator(); iterator2.hasNext();)
	{
	Object[] obj = (Object[]) iterator2.next();
	if(object[0]!=null && obj[1]!=null)
	checkListMap.put(obj[0].toString(), obj[1].toString());
	}
	}
	}

	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return dataMap;
	}
	
	
	public FeedbackPojo getTicketDetail(String feedId, String flag)
	{
	try
	{
	List dataList = null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	StringBuilder query = new StringBuilder();
	query.append("SELECT fstatus.ticket_no,fstatus.open_date,fstatus.open_time,dept.deptName AS bydepart,fstatus.feed_by,fCat.categoryName,fbtype.fbType,fstatus.status ,fstatus.feed_by_mobno,");
	query.append("deptTo.deptName AS todepart,emp.empName,emp.mobone,fsubCat.subCategoryName,fstatus.feed_brief,");
	query.append("fstatus.escalation_date,fstatus.escalation_time,subdept.subdeptname As tosubdept,fstatus.location,fstatus.Addr_date_time,subdeptby.subdeptname As bysubdept,fstatus.resolve_date,fstatus.resolve_time,fstatus.hp_reason,fstatus.spare_used,fstatus.feedback_remarks,");
	query.append("fstatus.sn_reason,fstatus.sn_on_date,fstatus.sn_on_time,fstatus.sn_upto_date,fstatus.sn_upto_time,fstatus.sn_duration,fstatus.ig_date,fstatus.ig_time,fstatus.ig_reason,fstatus.hp_date,fstatus.hp_time");
	query.append(" FROM feedback_status AS fstatus");
	query.append(" LEFT JOIN  department  AS dept  ON  dept.id =fstatus.by_dept_subdept");
	query.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.id =fstatus.allot_to");
	query.append(" LEFT JOIN  department  AS deptTo  ON  deptTo.id =emp.deptname");
	query.append(" LEFT JOIN  subdepartment  AS subdept  ON  subdept.id =fstatus.to_dept_subdept");
	query.append(" LEFT JOIN  subdepartment  AS subdeptby  ON  subdeptby.id =fstatus.by_dept_subdept");

	query.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");
	query.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
	query.append(" LEFT JOIN  feedback_type AS fbtype ON fbtype.id = fCat.fbType");
	if (flag != null && flag.equalsIgnoreCase("id"))
	query.append(" WHERE fstatus.id=" + feedId);
	else if (flag != null && flag.equalsIgnoreCase("TicketNo"))
	query.append(" WHERE fstatus.ticket_no='" + feedId + "'");

	System.out.println("Query : " + query.toString() + "    FEED ID:  " + feedId);
	dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

	fstatus = new FeedbackPojo();
	if (dataList != null && dataList.size() > 0)
	{

	for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	fstatus.setTicket_no(CUA.getValueWithNullCheck(object[0]));
	if (object[1] != null)
	fstatus.setOpen_date(DateUtil.convertDateToIndianFormat(object[1].toString()));
	fstatus.setOpen_time(CUA.getValueWithNullCheck(object[2]).substring(0, object[2].toString().length() - 3));
	fstatus.setFeedback_by_dept(CUA.getValueWithNullCheck(object[3]));
	fstatus.setFeed_by(CUA.getValueWithNullCheck(object[4]));
	fstatus.setFeedback_catg(CUA.getValueWithNullCheck(object[5]));
	fstatus.setFeedtype(CUA.getValueWithNullCheck(object[6]));
	fstatus.setStatus(CUA.getValueWithNullCheck(object[7]));
	fstatus.setFeedback_by_mobno(CUA.getValueWithNullCheck(object[8]));
	fstatus.setFeedback_to_dept(CUA.getValueWithNullCheck(object[9]));
	fstatus.setEmpName(CUA.getValueWithNullCheck(object[10]));

	fstatus.setMobOne(CUA.getValueWithNullCheck(object[11]));
	fstatus.setFeedback_subcatg(CUA.getValueWithNullCheck(object[12]));
	fstatus.setFeed_brief(CUA.getValueWithNullCheck(object[13]));
	if (object[14] != null)
	fstatus.setEscalation_date(DateUtil.convertDateToIndianFormat(object[14].toString()));
	fstatus.setEscalation_time(CUA.getValueWithNullCheck(object[15]));
	fstatus.setFeedback_to_subdept(CUA.getValueWithNullCheck(object[16]));
	fstatus.setLocation(CUA.getValueWithNullCheck(object[17]));

	fstatus.setAddr_date_time(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[18]).split("#")[0].trim()) + ", "
	+ CUA.getValueWithNullCheck(object[18]).split("#")[1].trim().substring(0, object[18].toString().split("#")[1].length() - 3));

	fstatus.setFeedback_by_subdept(CUA.getValueWithNullCheck(object[19]));
	if (object[20] != null)
	fstatus.setResolve_date(DateUtil.convertDateToIndianFormat(object[20].toString()));
	else
	fstatus.setResolve_date("");
	if (object[21] != null)
	fstatus.setResolve_time(CUA.getValueWithNullCheck(object[21]).substring(0, object[21].toString().length() - 3));
	else
	fstatus.setResolve_time("");

	fstatus.setHp_reason(CUA.getValueWithNullCheck(object[22]));

	if (object[23] != null)
	fstatus.setSpare_used(CUA.getValueWithNullCheck(object[23]));
	else
	fstatus.setSpare_used("");

	if (object[24] != null)
	fstatus.setResolve_remark(CUA.getValueWithNullCheck(object[24]));
	else
	fstatus.setResolve_remark("");

	fstatus.setSn_reason(CUA.getValueWithNullCheck(object[25]));
	if (object[26] != null)
	fstatus.setSn_on_date(DateUtil.convertDateToIndianFormat(object[26].toString()));
	else
	fstatus.setSn_on_date("NA");

	fstatus.setSn_on_time(CUA.getValueWithNullCheck(object[27]));
	if (object[28] != null)
	fstatus.setSn_upto_date(DateUtil.convertDateToIndianFormat(object[28].toString()));
	else
	fstatus.setSn_upto_date("NA");

	fstatus.setSn_upto_time(CUA.getValueWithNullCheck(object[29]));
	fstatus.setSn_duration(CUA.getValueWithNullCheck(object[30]));

	if (object[31] != null)
	fstatus.setIg_date(DateUtil.convertDateToIndianFormat(object[31].toString()));
	else
	fstatus.setIg_date("NA");
	fstatus.setIg_time(CUA.getValueWithNullCheck(object[32]));
	fstatus.setIg_reason(CUA.getValueWithNullCheck(object[33]));

	if (object[34] != null)
	fstatus.setHp_date(DateUtil.convertDateToIndianFormat(object[34].toString()));
	else
	fstatus.setHp_date("NA");
	fstatus.setHp_time(CUA.getValueWithNullCheck(object[35]));
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return fstatus;
	}
	
	
	public List<FeedbackPojo> getFeedbackList()
	{
	return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
	this.feedbackList = feedbackList;
	}

	public String getTicket_no()
	{
	return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
	this.ticket_no = ticket_no;
	}

	public Map<Integer, String> getResolverList()
	{
	return resolverList;
	}

	public void setResolverList(Map<Integer, String> resolverList)
	{
	this.resolverList = resolverList;
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

	public String getSpareused()
	{
	return spareused;
	}

	public void setSpareused(String spareused)
	{
	this.spareused = spareused;
	}

	public String getStatus()
	{
	return status;
	}

	public void setStatus(String status)
	{
	this.status = status;
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

	public String getTosubdept()
	{
	return tosubdept;
	}

	public void setTosubdept(String tosubdept)
	{
	this.tosubdept = tosubdept;
	}

	public String getReAssignRemark()
	{
	return reAssignRemark;
	}

	public void setReAssignRemark(String reAssignRemark)
	{
	this.reAssignRemark = reAssignRemark;
	}

	public String getFeedid()
	{
	return feedid;
	}

	public void setFeedid(String feedid)
	{
	this.feedid = feedid;
	}

	public String getFeedStatus()
	{
	return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
	this.feedStatus = feedStatus;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
	return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames)
	{
	this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getHeadingTitle()
	{
	return headingTitle;
	}

	public void setHeadingTitle(String headingTitle)
	{
	this.headingTitle = headingTitle;
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

	public String getCatg()
	{
	return catg;
	}

	public void setCatg(String catg)
	{
	this.catg = catg;
	}

	public String getSubCatg()
	{
	return subCatg;
	}

	public void setSubCatg(String subCatg)
	{
	this.subCatg = subCatg;
	}

	public String getFeed_brief()
	{
	return feed_brief;
	}

	public void setFeed_brief(String feed_brief)
	{
	this.feed_brief = feed_brief;
	}

	public String getAllotto()
	{
	return allotto;
	}

	public void setAllotto(String allotto)
	{
	this.allotto = allotto;
	}

	public String getLocation()
	{
	return location;
	}

	public void setLocation(String location)
	{
	this.location = location;
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

	public String getActiontaken()
	{
	return actiontaken;
	}

	public void setActiontaken(String actiontaken)
	{
	this.actiontaken = actiontaken;
	}

	public String getTodept()
	{
	return todept;
	}

	public void setTodept(String todept)
	{
	this.todept = todept;
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

	public String getDataFlag()
	{
	return dataFlag;
	}

	public void setDataFlag(String dataFlag)
	{
	this.dataFlag = dataFlag;
	}

	public String getDashFor()
	{
	return dashFor;
	}

	public void setDashFor(String dashFor)
	{
	this.dashFor = dashFor;
	}

	public String getD_subdept_id()
	{
	return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id)
	{
	this.d_subdept_id = d_subdept_id;
	}

	public String getTicketNo()
	{
	return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
	this.ticketNo = ticketNo;
	}

	public String getOpenDate()
	{
	return openDate;
	}

	public void setOpenDate(String openDate)
	{
	this.openDate = openDate;
	}

	public String getOpenTime()
	{
	return openTime;
	}

	public void setOpenTime(String openTime)
	{
	this.openTime = openTime;
	}

	public String getEmailId()
	{
	return emailId;
	}

	public void setEmailId(String emailId)
	{
	this.emailId = emailId;
	}

	public String getFeedBreif()
	{
	return feedBreif;
	}

	public void setFeedBreif(String feedBreif)
	{
	this.feedBreif = feedBreif;
	}

	public String getFeedId()
	{
	return feedId;
	}

	public void setFeedId(String feedId)
	{
	this.feedId = feedId;
	}

	public Map<String, String> getStatusList() {
	return statusList;
	}

	public void setStatusList(Map<String, String> statusList) {
	this.statusList = statusList;
	}

	public String getModuleName()
	{
	return moduleName;
	}

	public void setModuleName(String moduleName)
	{
	this.moduleName = moduleName;
	}

	public String getFeedbackSubCatgory()
	{
	return feedbackSubCatgory;
	}

	public void setFeedbackSubCatgory(String feedbackSubCatgory)
	{
	this.feedbackSubCatgory = feedbackSubCatgory;
	}

	public Map<Integer, String> getDeptList()
	{
	return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
	this.deptList = deptList;
	}

	public String getBackData()
	{
	return backData;
	}

	public void setBackData(String backData)
	{
	this.backData = backData;
	}

	public String getSn_reason()
	{
	return sn_reason;
	}

	public void setSn_reason(String sn_reason)
	{
	this.sn_reason = sn_reason;
	}

	public File getApprovalDoc() {
	return approvalDoc;
	}

	public void setApprovalDoc(File approvalDoc) {
	this.approvalDoc = approvalDoc;
	}

	public String getApprovalDocContentType() {
	return approvalDocContentType;
	}

	public void setApprovalDocContentType(String approvalDocContentType) {
	this.approvalDocContentType = approvalDocContentType;
	}

	public String getApprovalDocFileName() {
	return approvalDocFileName;
	}

	public void setApprovalDocFileName(String approvalDocFileName) {
	this.approvalDocFileName = approvalDocFileName;
	}

	public String getStoredDocPath() {
	return storedDocPath;
	}

	public void setStoredDocPath(String storedDocPath) {
	this.storedDocPath = storedDocPath;
	}

	public String getApprovedBy() {
	return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
	this.approvedBy = approvedBy;
	}

	public String getReason() {
	return reason;
	}

	public void setReason(String reason) {
	this.reason = reason;
	}

	public String getComplaintid() {
	return complaintid;
	}

	public void setComplaintid(String complaintid) {
	this.complaintid = complaintid;
	}

	public Map<String, String> getDataMap() {
	return dataMap;
	}
	public void setDataMap(Map<String, String> dataMap) {
	this.dataMap = dataMap;
	}

	public FeedbackPojo getFstatus() {
	return fstatus;
	}

	public void setFstatus(FeedbackPojo fstatus) {
	this.fstatus = fstatus;
	}

	public Map<String, String> getCheckListMap()
	{
	return checkListMap;
	}

	public void setCheckListMap(Map<String, String> checkListMap)
	{
	this.checkListMap = checkListMap;
	}


	public String getOrgImgPath()
	{
	return orgImgPath;
	}





	public void setOrgImgPath(String orgImgPath)
	{
	this.orgImgPath = orgImgPath;
	}





	@Override
	public void setServletRequest(HttpServletRequest request)
	{

	this.request=request;
	}
	
	

}
