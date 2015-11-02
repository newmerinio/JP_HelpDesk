package com.Over2Cloud.ctrl.helpdesk.reportconfiguration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.hr.group.GroupActionCtrl;
import com.Over2Cloud.emailSetting.EmailSetting;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.helpdesk.BeanUtil.ReportPojo;

@SuppressWarnings("serial")
public class ReportConfiguration extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;

	private String serviceDept;
	// private String subdept;
	private String emp_id;
	private String reportlevel;
	private String reportType;
	private String email_subject;
	private String report_date;
	private String report_time;
	private String sms;
	private String mail;
	private String module;
	// Variables for Report Download
	private String by_dept;
	private String to_dept;
	private String to_sdept;
	private String feed_type;
	private String category;
	private String sub_catg;
	private String from_date;
	private String to_date;
	private String from_time;
	private String to_time;
	private String status;
	private String emailid;
	private String subject;
	private String excelFileName;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> serviceDeptList = null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	private List<ConfigurationUtilBean> pageFieldsColumnsTT = null;
	private List<GridDataPropertyView> subDeptColumnNames = null;
	private List<GridDataPropertyView> reportConfigColumnNames = null;
	private List<FeedbackPojo> downloadReportData = null;
	private List<Object> subDeptData;
	private Map<String, String> graphDataMap = null;
	private String pageType;
	private Map<String, String> moduleMap;
	private Map<String, String> count;
	private String emailBody;
	private List<GridDataPropertyView> masterViewProp = null;
	private List<Object> masterViewList;
	
	public String beforeDailyReportConfigViewHeader()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			count = new LinkedHashMap<String, String>();
			count = new GroupActionCtrl().fetchContactTypeCounters(connectionSpace, "status", "report_configuration");
			moduleMap = new LinkedHashMap<String, String>();
			moduleMap = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String viewReportConfig()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewGridColumns();
				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				return ERROR;
			}
		} 
		else
		{
			return LOGIN;
		}
	}
	public void setViewGridColumns()
	{
		masterViewProp = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_daily_report_configuration", accountID, connectionSpace, "", 0, "table_name", "daily_report_configuration");
		if(returnResult!=null && returnResult.size()>0)
		{
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setWidth(gdp.getWidth());
				gpv.setHideOrShow(gdp.getHideOrShow());
				masterViewProp.add(gpv);
			}
		}
	}
	@SuppressWarnings({"unchecked" })
	public String firstAction4ReportConfig()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List departmentlist = null;
				deptList = new LinkedHashMap<Integer, String>();
				serviceDeptList = new LinkedHashMap<Integer, String>();
				moduleMap = new LinkedHashMap<String, String>();
				moduleMap = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				departmentlist = new HelpdeskUniversalAction().getParticularDepartment("", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
				}

				departmentlist.clear();

				departmentlist = new HelpdeskUniversalAction().getParticularDepartment("", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						serviceDeptList.put((Integer) object[0], object[1].toString());
					}
				}

				if (pageType != null && !pageType.equals("") && pageType.equalsIgnoreCase("SC"))
				{
					setReportConfigTags();
					returnResult = SUCCESS;
				}
				else if (pageType != null && !pageType.equals("") && pageType.equalsIgnoreCase("D"))
				{
					setReportDownloadTags();
					returnResult = SUCCESS;
				}
				else if (pageType != null && !pageType.equals("") && pageType.equalsIgnoreCase("H"))
				{
					setReportDownloadTags();
					returnResult = "History_SUCCESS";
				}

			}
			catch (Exception e)
			{
				addActionError("Ooops There Is Some Problem In firstActionMethod in HelpdeskUniversalAction !!!!");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeReportEmailData()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			StringBuilder emailContent = new StringBuilder();
			if (module != null && module.equalsIgnoreCase("COMPL"))
			{
				emailContent
						.append("<br><b>Dear Mr. Prabhat,</b><br><br><br><b><center>Compliance Task Due Today i.e. 21-08-2014</center></b><br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> S.No</b><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief&nbsp;&nbsp;</b></td><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency</b></td><td align='center' rowspan='2' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date&nbsp;&nbsp;</b></td><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td><td align='center' rowspan='2' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td><td align='center' colspan='3' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Last&nbsp;Action</b></td></tr><tr><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Date&nbsp;&nbsp;&nbsp;</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> By</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>1</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Tally Back Up</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Mailing Periodic Back Up of Tally</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Weekly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Accounts Excecutive</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Recurring</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Share with Sir and Mam</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>2</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Invoice Payment: Follow Up</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Reconciliation of all the Received & Due Payments</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Weekly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Himanshi Malik, Ms. Jagpreet Kaur</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Do it later or tomorrow</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>3</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Electricity Bill Payment</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pay Electricity Bill for C-52, Sec-02, Noida</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Monthly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Himanshi Malik, Ms. Jagpreet Kaur</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Discuss with Sir</b></td></tr></tbody></table><br><br><b><center>Compliances Task Action Taken Today i.e. 21-08-2014</center></b><br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> S.No</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Name</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Task&nbsp;Brief&nbsp;&nbsp;</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Frequency</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Due&nbsp;Date&nbsp;&nbsp;</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mapped&nbsp;Team</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Action&nbsp;By</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Status</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Comments&nbsp;&nbsp;&nbsp;</b></td><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>1</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Electricity Bill Generation</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Generating & Sharing Break Up of Electricity Bill for R-7, Sec-12, Noida</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Monthly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>20-09-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Himanshi Malik, Ms. Jagpreet Kaur</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Done & Recurring</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Paid July Bill</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>2</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Electricity Bill Payment</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>On Time Payment for Electricity Bill for R-7, Sec-12, Noida</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Monthly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>20-09-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Himanshi Malik, Ms. Jagpreet Kaur</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Done & Recurring</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Paid July Bill</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>3</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Tally Back Up</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Mailing Periodic Back Up of Tally</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Weekly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Accounts Excecutive</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Done & Recurring</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Share with Sir and Mam</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>4</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Electricity Bill Payment</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Pay Electricity Bill for C-52, Sec-02, Noida</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Monthly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Himanshi Malik, Ms. Jagpreet Kaur</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Discuss with Sir</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>5</b><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Invoice Payment: Follow Up</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Reconciliation of all the Received & Due Payments</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Weekly</b></td><td align='center' width='45%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>21-08-2014</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Himanshi Malik, Ms. Jagpreet Kaur</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ms. Himanshi Malik</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Do it later or tomorrow</b></td></tr></tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT><BR><BR><br><br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software ");
				emailContent.append(" provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required by the client. ");
				emailContent.append(" In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service ");
				emailContent.append(" provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div> ");
				emailBody = emailContent.toString();
			}
			else if (module != null && module.equalsIgnoreCase("HR"))
			{
				emailContent
						.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'></td></tr></tbody></table><b>Hello!!!</b><table width='100%' align='left' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Please find the following summary snapshot mapped for your analysis. You may find more details in the attached excel or for dynamic graphical analysis we request, you to please <a href=http://over2cloud.co.in/>Click Here</a> and login with your respective credentials</td></tr></tbody></table><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Attendance Summary of  21-08-2014 </b></td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Date</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;In</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;Out</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total&nbsp;Time</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong> Comment</strong></td> </tr></tbody></table><center>There are no records for the day .</center><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Attendance Summary of  20-08-2014 </b></td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Date</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;In</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Time&nbsp;Out</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total&nbsp;Time</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong> Comment</strong></td> </tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2228</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Abhay Kumar Verma</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:31</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:45</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:14</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2193</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Avinash Prakash</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:46</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:25</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:39</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2207</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Azad Ahmad</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:40</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:50</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:10</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2227</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Damanpreet Singh</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:45</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:46</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:1</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2231</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Kamlesh Kumar Yadav</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:30</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:45</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:15</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2206</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Karnika Gupta</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:46</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:15</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:29</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2229</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Kuldeep Kumar</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:30</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:47</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:17</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2230</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Manab Sarmah</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:56</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:45</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:49</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2197</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Padmalochan Behera</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:42</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:20</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:38</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2233</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sanjay Kumar Soni</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:29</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:48</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:19</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2210</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sanjiv Singh</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:39</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:42</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:3</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2154</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Shailendra Mishra</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:32</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>18:35</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:3</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2222</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sumiti Bajpai</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:48</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:30</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>8:42</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2226</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Tareshwar Chaudhary</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:23</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:45</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:22</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2202</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Vivekanand Mall</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:28</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>18:30</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:2</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2232</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sunil Kumar</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Technical</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Absent</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>00:00</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>00:00</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0:0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2195</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Himanshi Malik</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Operations</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td><td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>10:43</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:37</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>8:54</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2225</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Varun Kalra</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sales</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Present</td> <td align='center' width='12%' style=' color:red; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09:41</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19:05</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>9:24</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td></tr></tbody></table><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Leave Request Approval Status of 21-08-2014 </b></td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Reason</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>From</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>To</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Authorized By</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Total Days</strong></td> </tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>2195</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Himanshi Malik</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Operations</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>My Sister Marriage</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>26-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>30-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>NO Action</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>1</td></tr></tbody></table><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>New Joiners Status of 21-08-2014 </b></td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emp Id</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Department</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Mobile no</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Address</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>DOB</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>DOA</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Emergency&nbsp;No</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong> Email_id</strong></td> </tr></tbody></table><center>There are no New Joiners for Today.</center><br><b>Thanks & Regards,</b><br><b>Human Resource & Administration Team</b><table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software ");
				emailContent.append(" provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required ");
				emailContent.append(" by the client. ");
				emailContent.append(" In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service ");
				emailContent.append(" provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT> ");
				emailBody = emailContent.toString();
			}
			else if (module != null && module.equalsIgnoreCase("DREAM_HDM"))
			{
				emailContent
						.append(" <b>Dear Mr. Prabhat,</b><br><br><b>Hello!!!</b><br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>DreamSol Ticket Summary Status as on 21-08-2014, At 20:00:03</b></td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Pending</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F Snooze</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/F High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>C/D Total</b></td></tr><tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>1</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>1</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>0</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>1</td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr  bgcolor='#8db7d6'><td align='center' width='5%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge By & CC</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Organization Name</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Relationship Type</strong></td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Offering</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Category</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Sub-Category</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted To</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened At & On</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Resolution Time</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td></tr></tbody></table><br><strong>Pending</strong><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr  bgcolor='#ffffff'><td align='center' width='5%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>SP1067</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Deepak Gupta & NA</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>J K Tyre Ltd</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Prospect Client</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Trans SMS-D-Com</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>SMS Count</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Zero SMS Counter</td>  <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Vivekanand Mall</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>21-08-2014 & 13:44</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>00:30</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td></tr></tbody></table></tbody></table></tbody></table></tbody></table><br><strong>Carry Forward Data</strong><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr  bgcolor='#ffffff'><td align='center' width='5%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>SP1052</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Azad Ahmad & Sanjiv Singh,Shailendra Mishra,Vivekanand Mall</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Business Productivity Apps</td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Internal</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>VAM-Local</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Image</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Image Upload Issue</td>  <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Vivekanand Mall</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>04-08-2014 & 12:29</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>00:30</td><td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td></tr></tbody></table><table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software ");
				emailContent.append(" provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required ");
				emailContent.append(" by the client. ");
				emailContent.append(" In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service ");
				emailContent.append(" provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT> ");
				emailBody = emailContent.toString();
			}
			else if (module != null && module.equalsIgnoreCase("DAR"))
			{
				emailContent
						.append("<br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>DreamSol Telesolutions Pvt. Ltd.</b></td></tr></tbody></table><b>Hello!!!</b><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Consolidated Project DAR Submission Report As On 21-08-2014 At 20:00</b></td></tr></tbody></table><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody><tr bgcolor='#8db7d6'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Task&nbsp;Name</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Specific&nbsp;Task</strong></td><td align='center' width='14%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Alloted&nbsp;By</strong></td> <td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Initiation&nbsp;Date</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Completion&nbsp;Date</strong></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Status</strong></td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Completion (%)</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Today Task</strong></td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Tommorow Task</strong></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sumiti Bajpai</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Leave & Records</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Bug fixing Sheet</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Ms. Jagpreet Kaur</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>16-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>70</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>work on Personal Records and work Experience records Document upload and download  make view for record and employee (work experience)</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Leave Calculation for leave () check whole module Hr</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Kamlesh Kumar Yadav</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>100</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>view in 3rd and 4th board with search</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>apply data chart on all board</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Kuldeep Kumar</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>100</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>show data in utility of 1 week , top 10 Breaches,  view data click on outlet name and try for pai chart</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Show Data using  Data Icon,Pie Icon,Histrogram Icon</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Abhay Kumar Verma</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Done</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>100</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>top 10 breaches,pie chart with static data</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>new task</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Manab Sarmah</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>90</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>view data in four form with search by date</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>pop up for fourth form and next project</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Tareshwar Chaudhary</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>85</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>perform dashboard task</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>perform next task</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sanjay Kumar Soni</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>80</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>view data, perform searching by date</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>NA</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Damanpreet Singh</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Dashboard View With Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>20-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>75</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>work on dashboard</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>complete previous and start new task</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Azad Ahmad</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>WFPM</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Count and Search</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>06-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>07-08-2014</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>80</td><td align='center' width='12%'  bgcolor='#9AFE2E'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>view sequence,edit of client(partially),problem resolve(validation on client,and tranee))</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>edit and new full view(action) and offering should not add double</td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Vivekanand Mall</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>PunchSMS.com Reivew</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Testing & suggesting any Upgradtions</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Shailendra Mishra</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>15-05-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>22-05-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Karnika Gupta</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>CR in Projects for Allotment</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Project Allotment & Registration Concept</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>17-05-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>28-05-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Snooze</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Sumiti Bajpai</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>HR</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Change Request for HR</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>12-07-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>14-07-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Damanpreet Singh</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending Points Rectification</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending Points based on Review on 26-Jul-14</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>26-07-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>26-07-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Avinash Prakash</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>PDM Changes</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>PDM Changes as per Review Meet</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>08-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>09-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Damanpreet Singh</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Utility Master</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Daily utility in Outlet table</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>07-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>08-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Damanpreet Singh</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>User Management</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>User Management	Management, HOD, Normal, Super Admin etc.</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>11-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>11-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Tareshwar Chaudhary</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>User Management</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Management, HOD, Normal, Super Admin etc.</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>11-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>11-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Partially Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Azad Ahmad</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>WFPM</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Miscellaneous Work, Master, Changing in Client</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>11-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>12-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Padmalochan Behera</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Asset with HDM</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Seek Approval </td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>16-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>19-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr><tr  bgcolor='#ffffff'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Avinash Prakash</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>PDM</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>For Smooth running working on bug</td><td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Mr. Prabhat</td><td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>16-08-2014</td> <td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>16-08-2014</td><td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>Pending</td><td align='center' width='12%' colspan='4' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><font color='#FF0000'>DAR Awaited</font></td></tr></tbody></table><table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software ");
				emailContent.append(" provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required ");
				emailContent.append(" by the client. ");
				emailContent.append(" In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service ");
				emailContent.append(" provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT> ");
				emailBody = emailContent.toString();
			}
			else if (module != null && module.equalsIgnoreCase("WFPM"))
			{
				emailContent.append("<FONT face='sans-serif'><TABLE border='0' cellpadding='0' cellspacing='0' width='100%'><TR><TD><b>Dear Mr. Prabhat,</b></TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD><b>Hello!!!</b></TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD>Please find the following summary snapshot mapped for your analysis. You may find more details in the attached excel or for dynamic graphical analysis we request, you to please <a href='http://192.168.1.17:8080/over2cloud' target='blank'>click here</a> and login with your respective credentials.</TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD align='Center' bgcolor='DB7F07'><b>WFPM Activity Report of Mr. Varun as on 21-08-2014</b></TD></TR></TABLE><BR><BR><TABLE border='1' cellpadding='0' cellspacing='0' width='100%'><TR><TD colspan='4' align='Center'  bgcolor='DB7F07'><b>KPI</b></TD></TR><tr bgcolor='FAB55A' align='center'><td><b>KPI</b></td><td><b>Monthly Target</b></td><td><b>Total Achievement</b></td><td><b>Today's Achievement</b></td></tr><TR bgcolor='FAEDDC'><TD align='Center'>Data Collection</TD><TD align='Center'>500</TD><TD align='Center'>87</TD><TD align='Center'>0</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Total Calls</TD><TD align='Center'>750</TD><TD align='Center'>47</TD><TD align='Center'>5</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Productive Calls</TD><TD align='Center'>625</TD><TD align='Center'>47</TD><TD align='Center'>5</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Intro Mail</TD><TD align='Center'>375</TD><TD align='Center'>NA</TD><TD align='Center'>0</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Meeting Generation</TD><TD align='Center'>40</TD><TD align='Center'>3</TD><TD align='Center'>0</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Prospective Clients</TD><TD align='Center'>300</TD><TD align='Center'>1</TD><TD align='Center'>0</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Prospective Associate</TD><TD align='Center'>20</TD><TD align='Center'>NA</TD><TD align='Center'>0</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Offering-1</TD><TD align='Center'>5</TD><TD align='Center'>NA</TD><TD align='Center'>0</TD></TR></TABLE><BR><BR><TABLE border='1' cellpadding='0' cellspacing='0' width='100%'><TR><TD colspan='4' align='Center'  bgcolor='DB7F07'><b>Lead</b></TD></TR><tr bgcolor='FAB55A' align='center'><td><b>Name</b></td><td><b>Status</b></td><td><b>Activity</b></td><td><b>Moved To</b></td></tr><TR><TD colspan='4' align='Center'  bgcolor='FAEDDC'>NA</TD></TR></TABLE><BR><BR><TABLE border='1' cellpadding='0' cellspacing='0' width='100%'><TR><TD colspan='5' align='Center'  bgcolor='DB7F07'><b>Client</b></TD></TR><tr bgcolor='FAB55A' align='center'><td><b>Name</b></td><td><b>Offering</b></td><td><b>Status</b></td><td><b>Activity</b></td><td><b>Date</b></td></tr><TR bgcolor='FAEDDC'><TD align='Center'>Delhi Heart And Lung Institute</TD><TD align='Center'>Helpdesk-Local</TD><TD align='Center'>Follow Up</TD><TD align='Center'>Spoke to Prabhat sir in the morning for the time slots for visit to BLK. Will interact with sir tomorrow to share the same with DHLI.</TD><TD align='Center'>22-08-2014 10:00</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>National Heart Institute</TD><TD align='Center'>Helpdesk-Local</TD><TD align='Center'>Follow Up</TD><TD align='Center'>Call on 22.08.14 as Monika madam has not spoken to Mr Jena</TD><TD align='Center'>22-08-2014 10:30</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>The Cradle</TD><TD align='Center'>Helpdesk-Local</TD><TD align='Center'>Follow Up</TD><TD align='Center'>Busy in a board meeting. Call tomorrow.</TD><TD align='Center'>22-08-2014 11:00</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Sant Parmand Hospital</TD><TD align='Center'>Asset-Local</TD><TD align='Center'>Follow Up</TD><TD align='Center'>Asset and Spares solution flow shared today. Call on 25.08.14</TD><TD align='Center'>25-08-2014 10:30</TD></TR><TR bgcolor='FAEDDC'><TD align='Center'>Sumitra Hospital</TD><TD align='Center'>Operation Task-Local</TD><TD align='Center'>Follow Up</TD><TD align='Center'>Sir is out of station. Has asked to call for the intro meeting on 28.08.14</TD><TD align='Center'>28-08-2014 16:00</TD></TR></TABLE><BR><BR><TABLE border='1' cellpadding='0' cellspacing='0' width='100%'><TR><TD colspan='5' align='Center' bgcolor='DB7F07'><b>Associate</b></TD></TR><tr bgcolor='FAB55A' align='center'><td><b>Name</b></td><td><b>Offering</b></td><td><b>Status</b></td><td><b>Activity</b></td><td><b>Date</b></td></tr><TR><TD colspan='5' align='Center' bgcolor='FAEDDC'>NA</TD></TR></TABLE><br><br><div class='gmail_default'><b style='color:rgb(0,0,0);font-family:arial,sans-serif'>Thanks &amp; Regards,<br>WFPM Application Team<br></b><span style='color:rgb(0,0,0);font-family:arial,sans-serif'>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>------------------------------</span><span style='color:rgb(0,0,0);font-family:arial,sans-serif'><wbr>---------------------------</span><br style='color:rgb(0,0,0);font-family:arial,sans-serif'><font face='TIMESROMAN' size='1' style='color:rgb(0,0,0)'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required by the client. In case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then&nbsp;<br>please do not reply to this mail instead contact to your administrator or for any support related to the software service provided, visit contact details over '<a href='http://www.dreamsol.biz/contact_us.html' style='text-decoration:none' target='_blank'>http://www.dreamsol.biz/<wbr>contact_us.html</a>' or you may kindly mail your feedback to&nbsp;<br><a href='mailto:support@dreamsol.biz' style='text-decoration:none' target='_blank'>support@dreamsol.biz</a></font><font face='arial, sans-serif'><span style='font-size:14px'><br></span></font></div></FONT> ");
				emailBody = emailContent.toString();
			}
			else
			{
				emailBody = "No Report Configured";
			}
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeReportSMSData()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			StringBuilder emailContent = new StringBuilder();
			if (module != null && module.equalsIgnoreCase("DREAM_HDM"))
			{
				emailContent.append(" Dear Karnika, For All Ticket Status as on 21-07-2014: Pending: 0, C/F Pending: 2, Snooze: 0, Ignore: 0, Resolved: 0. ");
				emailBody = emailContent.toString();
			}
			else
			{
				emailBody = "No Report Configured";
			}
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setReportConfigTags()
	{
		ConfigurationUtilBean obj;
		pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
		pageFieldsColumnsTT = new ArrayList<ConfigurationUtilBean>();
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_contact_sub_type_configuration", accountID, connectionSpace, "", 0, "table_name", "contact_sub_type_configuration");
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("contact_subtype_name"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					obj.setMandatory(true);
					pageFieldsColumns.add(obj);
				}
			}
		}

		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_daily_report_configuration", accountID, connectionSpace, "", 0, "table_name", "daily_report_configuration");

		if (reportConfigList != null && reportConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : reportConfigList)
			{
				obj = new ConfigurationUtilBean();
				if (!gdv.getColomnName().trim().equalsIgnoreCase("sms") && !gdv.getColomnName().trim().equalsIgnoreCase("mail") && gdv.getColType().equalsIgnoreCase("D"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					pageFieldsColumns.add(obj);
				}
				else if (!gdv.getColomnName().trim().equalsIgnoreCase("sms") && !gdv.getColomnName().trim().equalsIgnoreCase("mail") && !gdv.getColType().equalsIgnoreCase("TA"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					obj.setData_type(gdv.getData_type());
					obj.setField_length(gdv.getLength());
					pageFieldsColumnsTT.add(obj);
				}
			}
		}
	}

	public void setReportDownloadTags()
	{
		ConfigurationUtilBean obj;
		pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_report_panel_configuration", accountID, connectionSpace, "", 0, "table_name", "report_panel_configuration");
		if (reportConfigList != null && reportConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : reportConfigList)
			{
				if (!gdv.getColomnName().equalsIgnoreCase("from_time") && !gdv.getColomnName().equalsIgnoreCase("to_time"))
				{
					obj = new ConfigurationUtilBean();

					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					pageFieldsColumns.add(obj);
				}
			}
		}
	}

	public String addReportConfiguration()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_daily_report_configuration", "", connectionSpace, "", 0, "table_name", "daily_report_configuration");
				if (colName != null && colName.size() > 0)
				{
					// Create Table Query Based On Configuration
					List<TableColumes> tableColumn = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName)
					{
						TableColumes tc = new TableColumes();
						tc.setColumnname(tableColumes.getColomnName());
						tc.setDatatype(tableColumes.getData_type());
						if (tableColumes.getColomnName().equalsIgnoreCase("status"))
						{
							tc.setConstraint("default 'Active'");
						}
						else
						{
							tc.setConstraint("default NULL");
						}
						tableColumn.add(tc);
					}
					TableColumes tc = new TableColumes();
					tc.setColumnname("report_send_time");
					tc.setDatatype("varchar(10)");
					tc.setConstraint("default NULL");
					tableColumn.add(tc);

					tc = new TableColumes();
					tc.setColumnname("sub_type_id");
					tc.setDatatype("varchar(5)");
					tc.setConstraint("default NULL");
					tableColumn.add(tc);

				

					cot.createTable22("report_configuration", tableColumn, connectionSpace);

					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					boolean flag = false;
					flag = new HelpdeskUniversalHelper().isExist("report_configuration", "emp_id", emp_id, "report_type", reportType, "module", module, connectionSpace);
					if (!flag)
					{
						ob = new InsertDataTable();
						ob.setColName("emp_id");
						ob.setDataName(emp_id);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("report_level");
						if (serviceDept != null && !serviceDept.equals("") && serviceDept.equalsIgnoreCase("All"))
						{
							ob.setDataName("2");
						}
						else
						{
							ob.setDataName("1");
						}
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("report_type");
						ob.setDataName(reportType);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("email_subject");
						ob.setDataName(email_subject);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("report_date");
						ob.setDataName(DateUtil.convertDateToIndianFormat(report_date));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("report_time");
						ob.setDataName(report_time);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("sms");
						if (sms.equals("true"))
						{
							ob.setDataName("Y");
						}
						else if (sms.equals("false"))
						{
							ob.setDataName("N");
						}
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("mail");
						if (mail.equals("true"))
						{
							ob.setDataName("Y");
						}
						else if (mail.equals("false"))
						{
							ob.setDataName("N");
						}
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("report_send_time");
						ob.setDataName("00:00:00");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("sub_type_id");
						ob.setDataName(serviceDept);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("user_name");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("created_date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("created_time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("module");
						ob.setDataName(module);
						insertData.add(ob);
						// status=cot.insertIntoTable("report_configuration",insertData,connectionSpace);
						int maxId = cot.insertDataReturnId("report_configuration", insertData, connectionSpace);

						if (maxId > 0)
						{
							addActionMessage("Report Configuration Detail Added Succesfully !!!");
							StringBuilder fieldsNames = new StringBuilder();
							StringBuilder fieldsValue = new StringBuilder();
							if (insertData != null && !insertData.isEmpty())
							{
								int i = 1;
								for (InsertDataTable h : insertData)
								{
									for (GridDataPropertyView gdp : colName)
									{
										if (h.getColName().equalsIgnoreCase(gdp.getColomnName()))
										{
											if (i < insertData.size())
											{
												fieldsNames.append(gdp.getHeadingName() + ", ");
												if (h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
												{
													fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()) + ", ");
												}
												else
												{
													fieldsValue.append(h.getDataName() + ", ");
												}
											}
											else
											{
												fieldsNames.append(gdp.getHeadingName());
												if (h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
												{
													fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
												}
												else
												{
													fieldsValue.append(h.getDataName());
												}
											}
										}
									}
									i++;
								}
							}
							String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
							new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Configured Report", fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
						}
					}
					else
					{
						addActionMessage("Details Already Added !!!");
					}
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
	public String viewReportConfigData()
	{
		try
		{
			
			
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from report_configuration");
			List<?> dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			if (dataCount != null)
			{
				BigInteger count = new BigInteger("3");
				Object obdata = null;
				for (Iterator<?> it = dataCount.iterator(); it.hasNext();)
				{
					obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				if (to > getRecords())
					to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query1.setLength(0);
				query.append("SELECT ");
				List<?> fieldNames = Configuration.getColomnList("mapped_daily_report_configuration", accountID, connectionSpace, "daily_report_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator<?> it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected
					// fields

					obdata = (Object) it.next();
					if (obdata != null )
					{
						if(obdata.toString() .equalsIgnoreCase("emp_id"))
						{
							query1.append(" emp.emp_name, ");
						}
						else if(obdata.toString() .equalsIgnoreCase("report_level"))
						{
							query1.append(" dept1.contact_subtype_name AS level, ");
						}
						else
						{
							query1.append( "report_conf."+obdata.toString() + ",");
						}
					}
					i++;
				}
				query.append(query1.substring(0, query1.toString().length() - 1));
				query.append(" FROM report_configuration as report_conf");
				query.append(" inner join primary_contact as emp on report_conf.emp_id=emp.id");
				query.append(" LEFT join contact_sub_type as dept1 on dept1.id=report_conf.report_level");
				query.append(" WHERE report_conf.id!=0 ");
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and ");
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" report_conf. " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" report_conf. " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" report_conf. " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" report_conf.  " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" report_conf. " + getSearchField() + " like '%" + getSearchString() + "'");
					}
				}
				List<?> data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					Object[] obdata11 = null;
					for (Iterator<?> it = data.iterator(); it.hasNext();)
					{
						obdata11 = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata11[k] != null && !obdata11[k].toString().equalsIgnoreCase(""))
							{
								if (fieldNames.get(k).toString().equals("created_date") || fieldNames.get(k).toString().equals("report_date"))
								{
									one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata11[k].toString()));
								}
								else if (fieldNames.get(k).toString().equals("report_type"))
								{
									if(obdata11[k].toString().equalsIgnoreCase("D"))
									{
										one.put(fieldNames.get(k).toString(),"Daily");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("W"))
									{
										one.put(fieldNames.get(k).toString(),"Weekly");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("M"))
									{
										one.put(fieldNames.get(k).toString(),"Monthly");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("Q"))
									{
										one.put(fieldNames.get(k).toString(),"Quarterly");
									}
									else if(obdata11[k].toString().equalsIgnoreCase("H"))
									{
										one.put(fieldNames.get(k).toString(),"Half Yearly");
									}
								}
								else if (fieldNames.get(k).toString().equals("user_name"))
								{
									one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata11[k].toString()));
								} else
								{
									one.put(fieldNames.get(k).toString(), obdata11[k].toString());
								}
							} else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
			}
		}
		catch (Exception e)
		{
			addActionMessage("Ooops!!! There is some problem in getting feedtype data");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// Method for Update Feedback Category Name (In Use)
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String modifyReportConfigData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
							wherClause.put(parmName, paramValue);
						if (parmName.equalsIgnoreCase("status"))
						{
							wherClause.put("deactiveOn", DateUtil.getCurrentDateUSFormat());
						}
						else if(parmName.equalsIgnoreCase("report_date"))
						{
							if(paramValue.split("-")[0].length()<3)
							{
								wherClause.put("report_date", DateUtil.convertDateToUSFormat(paramValue));
							}
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("report_configuration", wherClause, condtnBlock, connectionSpace);
					StringBuilder fieldsNames = new StringBuilder();
					StringBuilder dataStore = new StringBuilder();
					if (wherClause != null && !wherClause.isEmpty())
					{
						int i = 1;
						for (Map.Entry<String, Object> entry : wherClause.entrySet())
						{
							if (i < wherClause.size())
								fieldsNames.append("'" + entry.getKey() + "', ");
							else
								fieldsNames.append("'" + entry.getKey() + "' ");
							i++;
						}
					}
					UserHistoryAction UA = new UserHistoryAction();
					List fieldValue = UA.fetchFields(fieldsNames.toString(), cbt, connectionSpace, "daily_report_configuration");
					if (fieldValue != null && fieldValue.size() > 0)
					{
						StringBuilder dataField = new StringBuilder();
						for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (wherClause != null && !wherClause.isEmpty())
							{
								int i = 1;
								for (Map.Entry<String, Object> entry : wherClause.entrySet())
								{
									if (object[1].toString().equalsIgnoreCase(entry.getKey()))
									{
										if (i < wherClause.size())
										{
											dataStore.append(entry.getValue() + ", ");
											dataField.append(object[0].toString() + ", ");
										}
										else
										{
											dataStore.append(entry.getValue());
											dataField.append(object[0].toString());
										}
									}
									i++;
								}
							}
						}
						String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
						UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Configured Report", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}

				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					wherClause.put("status", "Inactive");
					wherClause.put("deactiveOn", DateUtil.getCurrentDateUSFormat());
					condtnBlock.put("id", condtIds.toString());
					cbt.updateTableColomn("report_configuration", wherClause, condtnBlock, connectionSpace);
					StringBuilder fieldsNames = new StringBuilder();
					StringBuilder dataStore = new StringBuilder();
					if (wherClause != null && !wherClause.isEmpty())
					{
						i = 1;
						for (Map.Entry<String, Object> entry : wherClause.entrySet())
						{
							if (i < wherClause.size())
								fieldsNames.append("'" + entry.getKey() + "', ");
							else
								fieldsNames.append("'" + entry.getKey() + "' ");
							i++;
						}
					}
					UserHistoryAction UA = new UserHistoryAction();
					List fieldValue = UA.fetchFields(fieldsNames.toString(), cbt, connectionSpace, "daily_report_configuration");
					if (fieldValue != null && fieldValue.size() > 0)
					{
						StringBuilder dataField = new StringBuilder();
						for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (wherClause != null && !wherClause.isEmpty())
							{
								i = 1;
								for (Map.Entry<String, Object> entry : wherClause.entrySet())
								{
									if (object[1].toString().equalsIgnoreCase(entry.getKey()))
									{
										if (i < wherClause.size())
										{
											dataStore.append(entry.getValue() + ", ");
											dataField.append(object[0].toString() + ", ");
										}
										else
										{
											dataStore.append(entry.getValue());
											dataField.append(object[0].toString());
										}
									}
									i++;
								}
							}
						}
						String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
						new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "Configured Report", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
					}
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

	public String beforeDownloadReportView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				setReportGridColomnNames(deptLevel);
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	// Method for set Column Names for download Report
	public void setReportGridColomnNames(String deptLevel)
	{
		feedbackColumnNames = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setFrozenValue("false");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_dept");
		gpv.setHeadingName("By Dept");
		gpv.setAlign("left");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		/*
		 * gpv=new GridDataPropertyView();
		 * gpv.setColomnName("feedback_by_subdept");
		 * gpv.setHeadingName("By Sub Dept"); gpv.setAlign("left");
		 * gpv.setWidth(100); feedbackColumnNames.add(gpv);
		 */

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Open Date");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Open Time");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Feedback By");
		gpv.setAlign("left");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_mobno");
		gpv.setHeadingName("Mobile No");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_emailid");
		gpv.setHeadingName("Email Id");
		gpv.setAlign("left");
		gpv.setHideOrShow("true");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_catg");
		gpv.setHeadingName("Category");
		gpv.setAlign("left");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_subcatg");
		gpv.setHeadingName("Sub Category");
		gpv.setAlign("left");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("left");
		gpv.setHeadingName("Brief");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setAlign("left");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Location");
		gpv.setWidth(100);
		gpv.setAlign("left");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("left");
		gpv.setWidth(100);
		gpv.setHideOrShow("true");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("via_from");
		gpv.setHeadingName("Via From");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedtype");
		gpv.setHeadingName("Feedback Type");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		if (getStatus().equals("Snooze"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_reason");
			gpv.setHeadingName("Snooze Reason");
			gpv.setAlign("left");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_date");
			gpv.setHeadingName("Snooze On");
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_time");
			gpv.setHeadingName("Snooze At");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_date");
			gpv.setHeadingName("Snooze Up To");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_time");
			gpv.setHeadingName("Snooze Time");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_duration");
			gpv.setHeadingName("Snooze Duration");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}
		else if (getStatus().equals("High Priority"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_date");
			gpv.setHeadingName("HP Date");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_time");
			gpv.setHeadingName("HP Time");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_reason");
			gpv.setHeadingName("HP Reason");
			gpv.setWidth(100);
			gpv.setAlign("left");
			feedbackColumnNames.add(gpv);
		}
		else if (getStatus().equals("Resolved"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Resolved On");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Resolved At");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Res. Duration");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_by");
			gpv.setHeadingName("Resolve By");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("Res. Remark");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("Spare Used");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}

		if (getStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_registerby");
			gpv.setHeadingName("Register By");
			feedbackColumnNames.add(gpv);
		}
		if (!getStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("action_by");
			gpv.setHeadingName("Action By");
			feedbackColumnNames.add(gpv);
		}
	}

	@SuppressWarnings("unchecked")
	public String viewDownloadReportData()
	{
		try
		{
			System.out.println("Method Call ");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();

			List<FeedbackPojo> data = HUH.getFeedbackDetail4ReportDownload(by_dept, to_dept, to_sdept, feed_type, category, sub_catg, from_date, to_date, from_time, to_time, status, deptLevel, pageType, connectionSpace);
			System.out.println("Data List Size is  " + data.size());
			System.out.println("After getting data   By dept  " + by_dept);
			System.out.println("After getting data   To dept  " + to_dept);
			System.out.println("After getting data   To Sub dept  " + to_sdept);
			System.out.println("After getting data   Feedback type  " + feed_type);
			System.out.println("After getting data   Category " + category);
			System.out.println("After getting data   Sub Category  " + sub_catg);
			System.out.println("After getting data   From Date " + from_date);
			System.out.println("After getting data   To Date  " + to_date);
			System.out.println("After getting data   Status " + status);
			System.out.println("After getting data   page type  " + pageType);

			List statuscount = HUH.getFeedbackStatusCount(by_dept, to_dept, to_sdept, feed_type, category, sub_catg, from_date, to_date, from_time, to_time, status, deptLevel, pageType, connectionSpace);

			if (data != null && data.size() > 0 && statuscount != null && statuscount.size() > 0)
			{

				int count = data.size();
				setRecords(count);
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();

				setDownloadReportData(data);
				session.put("reportDataList", data);
				session.put("statuscountList", statuscount);

			}
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
		}
		catch (Exception e)
		{
			addActionMessage("Ooops!!! There is some problem in getting feedtype data");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String downloadReportExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				downloadReportData = new ArrayList<FeedbackPojo>();
				if (userName == null || userName.equals(""))
				{
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				}
				else
				{
					downloadReportData = (List<FeedbackPojo>) session.get("reportDataList");
					session.remove("reportDataList");

					List statusList = (List) session.get("statuscountList");
					session.remove("statuscountList");
					String excelpath = "";
					excelpath = new ReportDownload().writeDataInExcel(downloadReportData, statusList, status);

					if (excelpath != null && !excelpath.equals("") && emailid != null && !emailid.equals("") && subject != null && !subject.equals(""))
					{
						String[] emailarr = null;
						if (emailid.contains(","))
						{
							GenericMailSender GM = new GenericMailSender();
							emailarr = emailid.split(",");
							for (int i = 0; i < emailarr.length; i++)
							{
								// new
								// MsgMailCommunication().addMail(emailarr[i].trim(),subject,
								// "","NA", "Pending", "0",excelpath, "HDM");
							}
						}
						else
						{
							// new
							// MsgMailCommunication().addMail(emailid.trim(),subject,
							// "","NA", "Pending", "0",excelpath, "HDM");
						}
						// Hide By padam 13 Nov
						// excelStream=new FileInputStream(excelpath);
					}
					excelStream = new FileInputStream(excelpath);
					excelFileName = excelpath.substring(4, excelpath.length());
				}
				addActionMessage("Report Excel Download Successfully !!!!");
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

	/*
	 * @SuppressWarnings("unchecked") public String getGraphData1() { try {
	 * System.out.println("2nd mthod call"); SessionFactory
	 * connectionSpace=(SessionFactory)session.get("connectionSpace"); List
	 * dataList=new
	 * HelpdeskUniversalHelper().getFeedbackStatusCount("","","","",
	 * "","","","","","",null,"2",connectionSpace); graphDataMap=new
	 * LinkedHashMap<String, String>(); if(dataList!=null && dataList.size()>0)
	 * { for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
	 * Object[] object = (Object[]) iterator.next();
	 * graphDataMap.put(object[0].toString(), object[1].toString()); }
	 * 
	 * } } catch(Exception e) {
	 * 
	 * } return SUCCESS; }
	 */

	public String getServiceDept()
	{
		return serviceDept;
	}

	public void setServiceDept(String serviceDept)
	{
		this.serviceDept = serviceDept;
	}

	public String getReportlevel()
	{
		return reportlevel;
	}

	public void setReportlevel(String reportlevel)
	{
		this.reportlevel = reportlevel;
	}

	public String getReportType()
	{
		return reportType;
	}

	public void setReportType(String reportType)
	{
		this.reportType = reportType;
	}

	public String getEmail_subject()
	{
		return email_subject;
	}

	public void setEmail_subject(String email_subject)
	{
		this.email_subject = email_subject;
	}

	public String getReport_date()
	{
		return report_date;
	}

	public void setReport_date(String report_date)
	{
		this.report_date = report_date;
	}

	public String getReport_time()
	{
		return report_time;
	}

	public void setReport_time(String report_time)
	{
		this.report_time = report_time;
	}

	public String getSms()
	{
		return sms;
	}

	public void setSms(String sms)
	{
		this.sms = sms;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public List<GridDataPropertyView> getSubDeptColumnNames()
	{
		return subDeptColumnNames;
	}

	public void setSubDeptColumnNames(List<GridDataPropertyView> subDeptColumnNames)
	{
		this.subDeptColumnNames = subDeptColumnNames;
	}

	public List<GridDataPropertyView> getReportConfigColumnNames()
	{
		return reportConfigColumnNames;
	}

	public void setReportConfigColumnNames(List<GridDataPropertyView> reportConfigColumnNames)
	{
		this.reportConfigColumnNames = reportConfigColumnNames;
	}

	public List<Object> getSubDeptData()
	{
		return subDeptData;
	}

	public void setSubDeptData(List<Object> subDeptData)
	{
		this.subDeptData = subDeptData;
	}

	
	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}
	public List<FeedbackPojo> getDownloadReportData()
	{
		return downloadReportData;
	}

	public void setDownloadReportData(List<FeedbackPojo> downloadReportData)
	{
		this.downloadReportData = downloadReportData;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames)
	{
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getBy_dept()
	{
		return by_dept;
	}

	public void setBy_dept(String by_dept)
	{
		this.by_dept = by_dept;
	}

	public String getTo_dept()
	{
		return to_dept;
	}

	public void setTo_dept(String to_dept)
	{
		this.to_dept = to_dept;
	}

	public String getTo_sdept()
	{
		return to_sdept;
	}

	public void setTo_sdept(String to_sdept)
	{
		this.to_sdept = to_sdept;
	}

	public String getFeed_type()
	{
		return feed_type;
	}

	public void setFeed_type(String feed_type)
	{
		this.feed_type = feed_type;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getSub_catg()
	{
		return sub_catg;
	}

	public void setSub_catg(String sub_catg)
	{
		this.sub_catg = sub_catg;
	}

	public String getFrom_date()
	{
		return from_date;
	}

	public void setFrom_date(String from_date)
	{
		this.from_date = from_date;
	}

	public String getTo_date()
	{
		return to_date;
	}

	public void setTo_date(String to_date)
	{
		this.to_date = to_date;
	}

	public String getFrom_time()
	{
		return from_time;
	}

	public void setFrom_time(String from_time)
	{
		this.from_time = from_time;
	}

	public String getTo_time()
	{
		return to_time;
	}

	public void setTo_time(String to_time)
	{
		this.to_time = to_time;
	}

	public Map<String, String> getGraphDataMap()
	{
		return graphDataMap;
	}

	public void setGraphDataMap(Map<String, String> graphDataMap)
	{
		this.graphDataMap = graphDataMap;
	}

	public String getEmailid()
	{
		return emailid;
	}

	public void setEmailid(String emailid)
	{
		this.emailid = emailid;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}

	public String getPageType()
	{
		return pageType;
	}

	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public List<ConfigurationUtilBean> getPageFieldsColumnsTT()
	{
		return pageFieldsColumnsTT;
	}

	public void setPageFieldsColumnsTT(List<ConfigurationUtilBean> pageFieldsColumnsTT)
	{
		this.pageFieldsColumnsTT = pageFieldsColumnsTT;
	}

	public Map<String, String> getModuleMap()
	{
		return moduleMap;
	}

	public void setModuleMap(Map<String, String> moduleMap)
	{
		this.moduleMap = moduleMap;
	}

	public Map<String, String> getCount()
	{
		return count;
	}

	public void setCount(Map<String, String> count)
	{
		this.count = count;
	}

	public String getEmailBody()
	{
		return emailBody;
	}

	public void setEmailBody(String emailBody)
	{
		this.emailBody = emailBody;
	}

	public String getEmp_id()
	{
		return emp_id;
	}

	public void setEmp_id(String empId)
	{
		emp_id = empId;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}
	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

}
