package com.Over2Cloud.ctrl.dar.submission;

import java.io.File;
import java.io.IOException;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.dar.helper.DarHelper;
import com.Over2Cloud.ctrl.dar.task.TaskRegistrationHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DarSubmission extends ActionSupport implements ServletRequestAware {
	static final Log log = LogFactory.getLog(DarSubmission.class);
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	private HttpServletRequest request;
	// for DAR modules
	private List<Object> viewList;
	private List<Object> viewListForPending;
	private List<Object> viewListForDashboard;
	private String idtask;
	private InputStream fileInputStream;
	private List<GridDataPropertyView> taskTypeViewProp = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> darColumnMap = null;
	private List<ConfigurationUtilBean> darCalenderMap = null;
	private List<ConfigurationUtilBean> darFileMap = null;
	private List<ConfigurationUtilBean> darDropMap = null;
	Map<Integer, String> listTaskName =null;
	Map<Integer, String> listTaskNameFovalidation = null;
	private String clientOf;
	private String allotedTo;
	private String dashboardId;
	private File attachmentt;
	private String attachmenttContentType;
	private String attachmenttFileName;
	private File attachmentt1;
	private String attachmentt1ContentType;
	private String attachmentt1FileName;
	private File attachmentt2;
	private String attachmentt2ContentType;
	private String attachmentt2FileName;
	private File attachmentt3;
	private String attachmentt3ContentType;
	private String attachmentt3FileName;
	private File attachmentt4;
	private String attachmentt4ContentType;
	private String attachmentt4FileName;
	private File reviewDoc;
	private String reviewDocContentType;
	private String reviewDocFileName;
	Map<String, String> attachFileMap = new LinkedHashMap<String, String>();
	private String fileName;
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
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private DarSubmissionPojoBean taskObj = null;
	private DarSubmissionPojoBean darObj = new DarSubmissionPojoBean();
	private int indexVal;
	private String currentDate;
	private JSONArray jsonArr = null;
	private String monthCounter;
	private String attachment;
	private String projectId;

	public String execute() {
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	@SuppressWarnings("rawtypes")
	public String getClientDataFrom() 
	{
		//System.out.println(">>>>Return Date is "+currentDate);
		String newDate=null;
		newDate=currentDate;
		try 
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			taskObj = new DarSubmissionPojoBean();
			if (getClientOf() != null) 
			{
				Object[] object=null;
				StringBuilder query=new StringBuilder();
				query.append("SELECT tr.technical_Date,tr.functional_Date,tr.intiation,tr.completion,tr.specifictask,tt.tasktype,tr.priority,tr.clientfor,tr.cName,tr.offering, ");
				query.append(" emp.empName,tr.attachment ");
				query.append(" FROM task_registration AS tr ");
				query.append(" INNER JOIN task_type AS tt ON tt.id=tr.tasktype ");
				query.append(" INNER JOIN compliance_contacts AS cc ON cc.id=tr.guidance ");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id ");
				query .append( "WHERE tr.id='"+getClientOf()+"'");
				DARReportHelper DRH=new DARReportHelper();
				TaskRegistrationHelper TRH=new TaskRegistrationHelper();
				List getlist2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (getlist2!=null && getlist2.size()>0) 
				{
					String clientVal=null;
					String clientData=null;
					for (Iterator iterator = getlist2.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						
						taskObj.setTechnicalDate(DateUtil.convertDateToIndianFormat( TRH.getValueWithNullCheck(object[0])));
						taskObj.setFunctonalDate(DateUtil.convertDateToIndianFormat( TRH.getValueWithNullCheck(object[1])));
						taskObj.setInitiondate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[2])));
						taskObj.setComlpetiondate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[3])));
						taskObj.setSpecificTask(TRH.getValueWithNullCheck(object[4]));
						taskObj.setTaskType(TRH.getValueWithNullCheck(object[5]));
						taskObj.setPriority(TRH.getValueWithNullCheck(object[6]));
						if (object[7]!=null) 
						{
							clientVal=object[7].toString();
							if (object[7].toString().equalsIgnoreCase("PA")) 
							{
								taskObj.setClientFor("Prospect Associate");
							}
							else if (object[7].toString().equalsIgnoreCase("PC")) 
							{
								taskObj.setClientFor("Prospect Client");
							}
							else if (object[7].toString().equalsIgnoreCase("EC")) 
							{
								taskObj.setClientFor("Existing Client");
							}
							else if (object[7].toString().equalsIgnoreCase("EA")) 
							{
								taskObj.setClientFor("Existing Associate");
							}
							else if (object[7].toString().equalsIgnoreCase("N")) 
							{
								taskObj.setClientFor("New");
							}
							else if (object[7].toString().equalsIgnoreCase("IN")) 
							{
								taskObj.setClientFor("Internal");
							}
							
						} else 
						{
							taskObj.setClientFor("NA");
						}
						if (object[8]!=null) 
						{
							clientData=object[8].toString();
							taskObj.setClientName(DRH.clientName(clientVal, object[8].toString(),connectionSpace));
						} else 
						{
							taskObj.setClientName("NA");
						}
						if (object[9]!=null) 
						{
							taskObj.setOfferingName(DRH.offeringName(clientVal,clientData ,object[9].toString(),connectionSpace));
						} else 
						{
							taskObj.setOfferingName("NA");
						}
						taskObj.setGuidancee(TRH.getValueWithNullCheck(object[10]));
						taskObj.setTaskAttach(TRH.getValueWithNullCheck(object[11]));
					}
				}
				query.setLength(0);
				if (getlist2!=null && getlist2.size()>0) 
				{
					getlist2.clear();
				}
				query.append(" SELECT dr.tactionStatus,dr.tommorow,dr.compercentage,dr.attachmentt,dr.manhour,dr.cost,dr.otherWork,dr.challenges ");
				query.append(" FROM dar_submission AS dr ");
				query.append(" INNER JOIN  task_registration AS tr ON tr.id=dr.taskname  ");
				query .append( "WHERE tr.id='"+getClientOf()+"' ");
				
				if (id!=null && id.equalsIgnoreCase("1") && !id.equalsIgnoreCase("")) 
				{
					if (monthCounter!=null && !monthCounter.equalsIgnoreCase("")&& !monthCounter.equalsIgnoreCase("0")) 
					{
						newDate=DateUtil.getNewDate("day",Integer.parseInt(monthCounter),DateUtil.convertDateToUSFormat(currentDate));
						query.append(" AND dr.date='"+newDate+"'");
						newDate=DateUtil.convertDateToIndianFormat(newDate);
					} 
					else 
					{
						query.append(" AND dr.date='"+DateUtil.convertDateToUSFormat(currentDate)+"'");
					}
				}
				//System.out.println(">>>> QUERY IS AS >>> "+query.toString());
				getlist2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (getlist2!=null && getlist2.size()>0) 
				{
					for (Iterator iterator = getlist2.iterator(); iterator.hasNext();) 
					{
						object = (Object[]) iterator.next();
						
						taskObj.setToday(TRH.getValueWithNullCheck(object[0]));
						taskObj.setTommorow(TRH.getValueWithNullCheck(object[1]));
						taskObj.setCompercentage(TRH.getValueWithNullCheck(object[2]));
						taskObj.setDarAttach(TRH.getValueWithNullCheck(object[3]));
						taskObj.setManHour(TRH.getValueWithNullCheck(object[4]));
						taskObj.setCost(TRH.getValueWithNullCheck(object[5]));
						taskObj.setOtherworkk(TRH.getValueWithNullCheck(object[6]));
						taskObj.setChallenges(TRH.getValueWithNullCheck(object[7]));
					}
				}
				taskObj.setCurrentDate(newDate);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String beforeAddDar() 
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try 
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName=(String)session.get("uName");
				listTaskName = new LinkedHashMap<Integer, String>();
				String contactId=new TaskRegistrationHelper().getContactIdForTaskName("DAR", userName,connectionSpace);
			   // System.out.println("contactId     "+contactId);
				if (contactId!=null) 
			    {
			    	String query = "SELECT id,taskname FROM task_registration WHERE status!='Done' AND  allotedto IN ( "+contactId.substring(0, contactId.length()-1)+")";
			    	List task = cbt.executeAllSelectQuery(query,connectionSpace);
					Object[] object=null;
					if (task!=null && task.size()>0) 
					{
						for (Iterator iterator = task.iterator(); iterator.hasNext();) 
						{
							object = (Object[]) iterator.next();
							listTaskName.put((Integer) object[0], object[1].toString());
						}
					}
				}
				List<GridDataPropertyView> taskColumnList = Configuration.getConfigurationData("mapped_dar_configuration",accountID, connectionSpace, "", 0,
								"table_name", "dar_submission_configuration");
				darColumnMap = new ArrayList<ConfigurationUtilBean>();
				darFileMap = new ArrayList<ConfigurationUtilBean>();
				darDropMap = new ArrayList<ConfigurationUtilBean>();
				if (taskColumnList != null && taskColumnList.size() > 0) 
				{
					ConfigurationUtilBean obj=null;
					for (GridDataPropertyView gdp : taskColumnList) 
					{
						obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") &&  !gdp.getColomnName().equalsIgnoreCase("userName")&&  !gdp.getColomnName().equalsIgnoreCase("date")&&  !gdp.getColomnName().equalsIgnoreCase("time")&&  !gdp.getColomnName().equalsIgnoreCase("wStatus") &&  !gdp.getColomnName().equalsIgnoreCase("reviewComments")&&  !gdp.getColomnName().equalsIgnoreCase("rating")) 
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							darColumnMap.add(obj);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("reviewDoc")) 
						{
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							darFileMap.add(obj);
						} 
						else if (gdp.getColType().trim().equalsIgnoreCase("D")) 
						{
							if (gdp.getColomnName().equalsIgnoreCase("taskname")) 
							{
								List<GridDataPropertyView> taskColumnList1 = Configuration.getConfigurationData("mapped_dar_configuration",accountID, connectionSpace, "", 0,"table_name", "dar_configuration");
								if (taskColumnList1!=null && taskColumnList1.size()>0) 
								{
									ConfigurationUtilBean obj1=null;
									for (GridDataPropertyView gdp1 : taskColumnList1) 
									{
										obj1 = new ConfigurationUtilBean();
										if (gdp1.getColomnName().equalsIgnoreCase("intiation") || gdp1.getColomnName().equalsIgnoreCase("completion") || gdp1.getColomnName().equalsIgnoreCase("technical_Date") || gdp1.getColomnName().equalsIgnoreCase("functional_Date")) 
										{
											obj1.setValue(gdp1.getHeadingName());
											obj1.setKey(gdp1.getColomnName());
											obj1.setValidationType(gdp1.getValidationType());
											obj1.setColType(gdp1.getColType());
											obj1.setEditable(true);
											if (gdp1.getMandatroy().toString().equals("1")) {
												obj1.setMandatory(true);
											} else {
												obj1.setMandatory(false);
											}
											darColumnMap.add(obj1);
										}
									}
								}
							}
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							darDropMap.add(obj);
						}
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat()+ " Time: " + DateUtil.getCurrentTime()+ " Problem in Over2Cloud method beforeAddDar  of class " + getClass(), e);
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
public String beforeDarValidate()
{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) 
	{
		try 
		  {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			StringBuilder query=new StringBuilder();
			query.append("SELECT DISTINCT (contact.id),emp.empName FROM task_registration AS tr ");
			query.append("INNER JOIN compliance_contacts as contact ON contact.id=tr.allotedto ");
			query.append("INNER JOIN employee_basic as emp ON contact.emp_id=emp.id" );
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			listTaskNameFovalidation = new LinkedHashMap<Integer, String>();
			if (data!=null && data.size()>0) 
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1]!=null) 
					{
						listTaskNameFovalidation.put((Integer) object[0], object[1].toString());
					}
				}
			}
			
			currentDate = DateUtil.getCurrentDateIndianFormat();
			List<GridDataPropertyView> taskColumnList1=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
			List<GridDataPropertyView> taskColumnList = Configuration.getConfigurationData("mapped_dar_configuration",accountID, connectionSpace, "", 0,
							"table_name", "dar_submission_configuration");
			darColumnMap = new ArrayList<ConfigurationUtilBean>();
			darDropMap = new ArrayList<ConfigurationUtilBean>();
			ConfigurationUtilBean obj =null;
			if (taskColumnList != null && taskColumnList.size() > 0) 
			{
				for (GridDataPropertyView gdp : taskColumnList) 
				{
					obj = new ConfigurationUtilBean();
					if ( !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("snoozeDate") && !gdp.getColomnName().equalsIgnoreCase("snoozeReason")&& !gdp.getColomnName().equalsIgnoreCase("taskname") && !gdp.getColomnName().equalsIgnoreCase("pstatus") ) 
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						darColumnMap.add(obj);
					}
				}
			}
			if (taskColumnList1 != null && taskColumnList1.size() > 0) 
			{
				for (GridDataPropertyView gdp : taskColumnList1) 
				{
					obj = new ConfigurationUtilBean();
					if ( !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("allotedto") && !gdp.getColomnName().equalsIgnoreCase("allotedby") && !gdp.getColomnName().equalsIgnoreCase("validate_By_2") && !gdp.getColomnName().equalsIgnoreCase("validate_By_1") && !gdp.getColomnName().equalsIgnoreCase("technical_Date")&& !gdp.getColomnName().equalsIgnoreCase("functional_Date")) 
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						darDropMap.add(obj);
					}
				}
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()+ " Time: " + DateUtil.getCurrentTime()+ " Problem in Over2Cloud  of class " + getClass(), e);
			e.printStackTrace();
		}
	} else {
		returnResult = LOGIN;
	}
	return returnResult;
}
public String beforeDashboardAction(){
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) 
	{
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List<GridDataPropertyView> taskColumnList = Configuration.getConfigurationData("mapped_dar_configuration",accountID, connectionSpace, "", 0,
							"table_name", "dar_submission_configuration");
			darColumnMap = new ArrayList<ConfigurationUtilBean>();
			darFileMap = new ArrayList<ConfigurationUtilBean>();
			darDropMap = new ArrayList<ConfigurationUtilBean>();
			if (taskColumnList != null && taskColumnList.size() > 0) 
			{
				ConfigurationUtilBean obj=null;
				for (GridDataPropertyView gdp : taskColumnList)
				{
					obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")) 
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						darColumnMap.add(obj);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("F")) 
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						darFileMap.add(obj);
					} 
					else if (gdp.getColType().trim().equalsIgnoreCase("D")) 
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						darDropMap.add(obj);
					 }
				}
				returnResult = SUCCESS;
			}
		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()+ " Time: " + DateUtil.getCurrentTime()+ " Problem in Over2Cloud  of class " + getClass(), e);
			e.printStackTrace();
		}
	} else {
		returnResult = LOGIN;
	}
	return returnResult;
}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String darSubmissionAdd()
	{
		try 
		{
			String userName = (String) session.get("uName");
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_dar_configuration",accountID, connectionSpace, "", 0, "table_name","dar_submission_configuration");
			if (org2 != null && org2.size()>0)
              {
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				TableColumes ob1=null;
				for (GridDataPropertyView gdp : org2) 
				{
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName"))
						userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date"))
						dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time"))
						timeTrue = true;
				}
				cbt.createTable22("dar_submission", Tablecolumesaaa,connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				
				
					if (dashboardId!=null && dashboardId.equalsIgnoreCase("1")) 
					{
						while (it.hasNext()) 
						{
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (paramValue != null && !paramValue.equalsIgnoreCase("")&& Parmname != null && !Parmname.equalsIgnoreCase("dashboardId") && !Parmname.equalsIgnoreCase("taskid") && !Parmname.equalsIgnoreCase("tasknameee") && !Parmname.equalsIgnoreCase("initiondate")
									&& !Parmname.equalsIgnoreCase("comlpetiondate"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(DateUtil.makeTitle(paramValue));
								insertData.add(ob);
							}
							else if (paramValue == null) 
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName("NA");
								insertData.add(ob);
							} else if (Parmname.equalsIgnoreCase("initiondate")) 
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
								insertData.add(ob);
							} else if (Parmname.equalsIgnoreCase("comlpetiondate")) 
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
								insertData.add(ob);
							}
							else if (Parmname.equalsIgnoreCase("tasknameee")) 
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								//ob.setDataName(taskid);
								insertData.add(ob);
							}
						}
						if (userTrue) 
						{
							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(userName);
							insertData.add(ob);
						}
						if (dateTrue) 
						{
							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);
						}
						if (timeTrue) 
						{
							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertData.add(ob);
						}
						 if (attachmenttFileName != null)
						 {
							String renameFilePath = new CreateFolderOs().createUserDir("DAR") + "//" + DateUtil.mergeDateTime() + getAttachmenttFileName();
							String storeFilePath = new CreateFolderOs().createUserDir("DAR") + "//" + getAttachmenttFileName();
							String str = renameFilePath.replace("//", "/");
							if (storeFilePath != null)
							{
								File theFile = new File(storeFilePath);
								File newFileName = new File(str);
								if (theFile != null)
								{
									try
									{
										FileUtils.copyFile(attachmentt, theFile);
										if (theFile.exists())
											theFile.renameTo(newFileName);
									}
									catch (IOException e)
									{
										e.printStackTrace();
									}
									if (theFile != null)
									{
										 ob=new InsertDataTable();
				                         ob.setColName("attachmentt");
				                         ob.setDataName(renameFilePath);
				                         insertData.add(ob);
									}
								}
							}
						 }
					
					  status = cbt.insertIntoTable("dar_submission", insertData,connectionSpace);
					  if (status) 
					  {
						//  mailSendStatus(taskid, request.getParameter("statuss"),insertData);
						  addActionMessage("Data Added Successfully !!!!!");
					  }
					} 
					else 
					{
						String otherWork = null;
						String chalenge = null;
						ArrayList<ArrayList> mainList = new ArrayList<ArrayList>();
						ArrayList<String> fieldNameList = new ArrayList<String>();
						ArrayList<String> filePath = new ArrayList<String>();
						StringBuilder query =new StringBuilder();
						
						while (it.hasNext()) 
						{
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);

							if (paramValue != null && Parmname != null) 
							{
								if (Parmname.equalsIgnoreCase("otherWork")) 
								{
									otherWork = paramValue;
								}
								else if (Parmname.equalsIgnoreCase("challenges")) 
								{
									chalenge = paramValue;
								}
								else if (Parmname.equalsIgnoreCase("attachmentt")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("attachmentt1")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("attachmentt2")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("attachmentt3")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("attachmentt4")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("attachmentt5")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("attachmentt6")) 
								{
								}
								else if (Parmname.equalsIgnoreCase("indexVal")) 
								{
								}
								else
								{
									fieldNameList.add(Parmname);
									ArrayList<String> list = new ArrayList<String>();
									String tempParamValueSize[] = request.getParameterValues(Parmname);
									for(int i=0; i<indexVal; i++)
									{
										list.add(tempParamValueSize[i].trim());
									}
									mainList.add(list);
								}
							}
						}
						if (getAttachmenttFileName() != null) 
						{
							String renameFilePath = new CreateFolderOs().createUserDir("DAR") + "//" + DateUtil.mergeDateTime() + getAttachmenttFileName();
							String storeFilePath = new CreateFolderOs().createUserDir("DAR") + "//" + getAttachmenttFileName();
							String str = renameFilePath.replace("//", "/");
							if (storeFilePath != null)
							{
								File theFile = new File(storeFilePath);
								File newFileName = new File(str);
								if (theFile != null)
								{
									try
									{
										FileUtils.copyFile(attachmentt, theFile);
										if (theFile.exists())
											theFile.renameTo(newFileName);
									}
									catch (IOException e)
									{
										e.printStackTrace();
									}
								}
								filePath.add(renameFilePath);
							}
						}
						else
						{
							filePath.add("");
						}
						if (getAttachmentt1FileName() != null) 
						{
							String storeFilePath = new CreateFolderOs().createUserDir("DAR")+ "//" + getAttachmentt1FileName();
							filePath.add(storeFilePath);
						}
						else
						{
							filePath.add("");
						}
						if (getAttachmentt2FileName() != null) 
						{
							String storeFilePath = new CreateFolderOs().createUserDir("DAR")+ "//" + getAttachmentt2FileName();
							filePath.add(storeFilePath);
						}
						else
						{
							filePath.add("");
						}
						if (getAttachmentt3FileName() != null) 
						{
							String storeFilePath = new CreateFolderOs().createUserDir("DAR")+ "//" + getAttachmentt3FileName();
							filePath.add(storeFilePath);
						}
						else
						{
							filePath.add("");
						}
						if (getAttachmentt4FileName() != null) 
						{
							String storeFilePath = new CreateFolderOs().createUserDir("DAR")+ "//" + getAttachmentt4FileName();
							filePath.add(storeFilePath);
						}
						else
						{
							filePath.add("");
						}
						for (int i = 0; i < mainList.get(0).size(); i++)
						{
							List<InsertDataTable> insert =new ArrayList<InsertDataTable>();
							String taskName=null;
							String actionTaken=null;
							String snoozeDate=null;
							for (int j = 0; j < fieldNameList.size(); j++) 
							{
								//System.out.print(fieldNameList.get(j).toString());
								String s= mainList.get(j).get(i).toString();
								//System.out.println(":"+s);
								if (!fieldNameList.get(j).toString().equalsIgnoreCase("completion") && !fieldNameList.get(j).toString().equalsIgnoreCase("intiation") && !fieldNameList.get(j).toString().equalsIgnoreCase("technical_Date") && !fieldNameList.get(j).toString().equalsIgnoreCase("functional_Date") && !s.equalsIgnoreCase("")) 
								{
									if (fieldNameList.get(j).toString().equalsIgnoreCase("snoozeDate")) 
									{
										snoozeDate=s;
										ob = new InsertDataTable();
										ob.setColName(fieldNameList.get(j).toString());
										ob.setDataName(DateUtil.convertDateToUSFormat(s));
										insert.add(ob);
									} 
									else 
									{
										ob = new InsertDataTable();
										ob.setColName(fieldNameList.get(j).toString());
										ob.setDataName(s);
										insert.add(ob);
									}
								}
								if (fieldNameList.get(j).toString().equalsIgnoreCase("taskname"))
								{
									taskName=s;
								} 
								else if(fieldNameList.get(j).toString().equalsIgnoreCase("pstatus")) 
								{
									actionTaken=s;
								}
								if (snoozeDate!=null && !snoozeDate.equalsIgnoreCase(""))
                                {
									query.append("UPDATE task_registration SET completion='"+DateUtil.convertDateToUSFormat(snoozeDate)+"' WHERE id='"+taskName+"'");
									cbt.updateTableColomn(connectionSpace, query);
								    query.setLength(0);
                                }
							}
							try 
							{
								ob = new InsertDataTable();
								ob.setColName("attachmentt");
								ob.setDataName(filePath.get(i));
								insert.add(ob);
							  // System.out.println("File:        "+filePath.get(i) +">>>>>>>>>>"+file);
							} 
							catch (Exception e) 
							{
							}
							
							ob = new InsertDataTable();
							ob.setColName("otherWork");
							ob.setDataName(otherWork);
							insert.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("challenges");
							ob.setDataName(chalenge);
							insert.add(ob);

							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(userName);
							insert.add(ob);

							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insert.add(ob);

							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTime());
							insert.add(ob);
							
							status = cbt.insertIntoTable("dar_submission",insert, connectionSpace);
							if (status) 
							{
								query.append("UPDATE task_registration SET status='"+actionTaken+"' WHERE id='"+taskName+"'");
								cbt.updateTableColomn(connectionSpace, query);
								mailSendStatus(taskName, actionTaken);
							}
							//System.out.println("status :::::  "+status);
							//System.out.println("-----------------------------------------------------------------");
						}
						if (status) 
						{
							addActionMessage("Data Added Successfully !!!!!");
						}
						else
						 {
							addActionMessage("OOPS There is some error in data !!!!!!!!"); 
						 }
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings({  "rawtypes" })
	private void mailSendStatus(String taskName, String actionTaken) 
	{
	 try 
	  {
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
	   try 
	   {
		 String allotedby = null;
		 String allotedbyEmail = null;
		 String allotedto = null;
		 String allotedEmail = null;
		 String underguidance = null;
		 String tskname = null;
		 String fileName = null;
		 String underguidanceEmail=null;
		 String initiation=null;
		 String completion=null;
		 String taskType=null;
		 String specificTask=null;
		if (attachmenttFileName != null) 
		{
			File saveFile = null;
			String tempPath = System.getProperty("java.io.tmpdir");
			saveFile = new File(tempPath + File.separator+ attachmenttFileName);
			FileUtils.copyFile(attachmentt, saveFile);
			fileName = saveFile.toString();
		}
		if (attachmentt1FileName != null) 
		{
			File saveFile = null;
			String tempPath = System.getProperty("java.io.tmpdir");
			saveFile = new File(tempPath + File.separator+ attachmentt1FileName);
			FileUtils.copyFile(attachmentt1, saveFile);
			fileName = saveFile.toString();
		}
		if (attachmentt2FileName != null) 
		{
			File saveFile = null;
			String tempPath = System.getProperty("java.io.tmpdir");
			saveFile = new File(tempPath + File.separator+ attachmentt2FileName);
			FileUtils.copyFile(attachmentt2, saveFile);
			fileName = saveFile.toString();

		}
		if (attachmentt3FileName != null) 
		{
			File saveFile = null;
			String tempPath = System.getProperty("java.io.tmpdir");
			saveFile = new File(tempPath + File.separator+ attachmentt3FileName);
			FileUtils.copyFile(attachmentt3, saveFile);
			fileName = saveFile.toString();

		}
		if (attachmentt4FileName != null) 
		{
			File saveFile = null;
			String tempPath = System.getProperty("java.io.tmpdir");
			saveFile = new File(tempPath + File.separator+ attachmentt4FileName);
			FileUtils.copyFile(attachmentt4, saveFile);
			fileName = saveFile.toString();
		}
		StringBuilder query= new StringBuilder();
		query .append("SELECT tr.taskname,emp1.empName as allotedto,emp1.emailIdOne as allotedtoEmail, ");
		query .append("emp2.empName as allotedby,emp2.emailIdOne as allotedbyEmail, ");
		query .append("emp5.empName as guidance ,emp5.emailIdOne as guidanceEmail ,tr.intiation,tr.completion, ");
		query .append("ty.tasktype,tr.specifictask  ");
		query .append("FROM task_registration AS tr ");
		query .append("LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id ");
		query .append("LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id ");
		query .append("LEFT JOIN compliance_contacts AS cc5 ON tr.guidance=cc5.id  ");
		query .append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
		query .append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
		query .append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
		query .append("LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
		query.append("WHERE tr.id='"+taskName+"'");
		
		List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list!=null && list.size()>0) 
		{
			Object[] object=null;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) 
			{
				object = (Object[]) iterator.next();
				if (object!=null) 
				{
					tskname=object[0].toString();
					allotedby=object[3].toString();
					allotedto=object[1].toString();
					allotedEmail=object[2].toString();
					allotedbyEmail=object[4].toString();
					underguidance=object[5].toString();
					underguidanceEmail=object[6].toString();
					initiation=object[7].toString();
					completion=object[8].toString();
					taskType=object[9].toString();
					specificTask=object[10].toString();
				}
			}
		}
		String Subject = " DAR For "+tskname+ " On "+ DateUtil.getCurrentDateIndianFormat()+ " By " + allotedto + "";
		String text=null;
		String str=null;
		str="dar";
		text = new DarHelper().getMailContentForDar(tskname,request,initiation,completion,taskType,specificTask,allotedto,allotedby,str,underguidance);
		new MsgMailCommunication().addMail("","","dar@dreamsol.biz",Subject, text,"", "Pending", "0",fileName,"DAR");
		if (allotedto!=null) 
		{
			str="allotedTo";
			text = new DarHelper().getMailContentForDar(tskname,request,initiation,completion,taskType,specificTask,allotedto,allotedby,str,underguidance);
			new MsgMailCommunication().addMail("","",allotedEmail,Subject, text,"", "Pending", "0",fileName,"DAR");
		}
		if (allotedby!=null) 
		{
			str="allotedBy";
			text = new DarHelper().getMailContentForDar(tskname,request,initiation,completion,taskType,specificTask,allotedto,allotedby,str,underguidance);
			new MsgMailCommunication().addMail("","",allotedbyEmail,Subject, text,"", "Pending", "0",fileName,"DAR");
		}
		if (underguidance!=null) 
		{
			str="guidance";
			text = new DarHelper().getMailContentForDar(tskname,request,initiation,completion,taskType,specificTask,allotedto,allotedby,str,underguidance);
			new MsgMailCommunication().addMail("","",underguidanceEmail,Subject, text,"", "Pending", "0",fileName,"DAR");
		}
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	  } 
	  catch (Exception e) 
	 {
		  e.printStackTrace();
	 }
 }

	public String beforeDarView() 
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try 
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setTaskViewProp();
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
			return LOGIN;
		}
	}
	public void setTaskViewProp() 
	{
		try 
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			taskTypeViewProp.add(gpv);
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_dar_configuration",accountID, connectionSpace, "", 0, "table_name","dar_submission_configuration");
			if (statusColName != null && statusColName.size() > 0) 
			{
				for (GridDataPropertyView gdp : statusColName) 
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getColomnName().equalsIgnoreCase("attachmentt")) 
					{
						gpv.setFormatter(gdp.getFormatter());
					}
					taskTypeViewProp.add(gpv);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public String beforeDarViewtask() 
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try 
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				setTaskViewProp();
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
			return LOGIN;
		}
	}


	@SuppressWarnings("rawtypes")
	public String viewDarSubmission() 
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder("");
				query.append("select count(*) from dar_submission");
				List dataCount1 = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if (dataCount1.size() > 0) 
				{
					BigInteger count = new BigInteger("1");
					Object obdata=null;
					for (Iterator it = dataCount1.iterator(); it.hasNext();) 
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					StringBuilder query1 = new StringBuilder("");
					query1.append("SELECT  a.id,b.taskname,a.pstatus,a.tactionStatus,a.tommorow,a.compercentage,a.attachmentt,a.manhour,a.cost, "+
							" a.otherWork,a.challenges,a.userName,a.date,a.time,a.wStatus ,  "+
							" a.reviewComments ,a.rating,a.reviewDoc,a.snoozeDate,a.snoozeReason  "+
							" FROM dar_submission a RIGHT JOIN task_registration b on a.taskname=b.id  "+
							" WHERE a.userName='"+userName + "'");

					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) 
					{
						query1.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq")) {
							query1.append(" a." + getSearchField() + " = '"+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query1.append(" a." + getSearchField() + " like '%"+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query1.append(" a." + getSearchField() + " like '"+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query1.append(" a." + getSearchField() + " <> '"+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query1.append(" a." + getSearchField() + " like '%"+ getSearchString() + "'");
						}

					} 
					else if (getSord() != null && getSidx() != null&& !getSord().equalsIgnoreCase("")&& !getSidx().equalsIgnoreCase("")) 
					{
						query1.append(" ORDER BY a." + getSidx() + " "+ getSord() + "");
					}
					List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					List fieldNames = Configuration.getColomnList("mapped_dar_configuration", accountID,connectionSpace, "dar_submission_configuration");
					if (dataCount!=null && dataCount.size() > 0) 
					{
						viewList = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						Object[] obdata1=null;
						for (Iterator it = dataCount.iterator(); it.hasNext();) 
						{
							obdata1 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if (obdata1[k] != null) 
								{
									if (k == 0)
										one.put(fieldNames.get(k).toString(),(Integer) obdata1[k]);

									else if (fieldNames.get(k).toString().equalsIgnoreCase("snoozeDate")) 
									{
										one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata1[k]
																		.toString()));
									} 
									else 
									{
										one.put(fieldNames.get(k).toString(),obdata1[k].toString());
									}
								}
								else
								{
										one.put(fieldNames.get(k).toString(),"NA");
								}
							}
							Listhb.add(one);
						}
						setViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords()/ (double) getRows()));
					}
				}
				return SUCCESS;
			} catch (Exception e) 
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
	public String getAttachmentdownload() 
	{
		try 
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String query = "select id,attachmentt from dar_submission where id='"+ getId() + "'";
			List attach = cbt.executeAllSelectQuery(query, connectionSpace);
			if (attach != null && attach.size() > 0) 
			{
				Object[] object=null;
				for (Iterator iterator = attach.iterator(); iterator.hasNext();) 
				{
					object = (Object[]) iterator.next();
					if (object != null) 
					{
						if (object[0] != null & object[1] != null) 
						{
							fileName = object[1].toString();
							attachFileMap.put("id", object[0].toString());
							attachFileMap.put("File Path", object[1].toString());
						} 
						else 
						{
							addActionMessage("FILE NOT FOUND");
							return "notfound";
						}
					}
				}
			} 
			else 
			{
				addActionMessage("FILE NOT FOUND");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String getDetailOfPendingTask() 
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try 
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String contactId=new TaskRegistrationHelper().getContactIdForTaskName("DAR", userName,connectionSpace);

				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder("");
				query.append("select count(*) from task_registration where status!='Done'");
				List dataCount1 = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if (dataCount1!=null && dataCount1.size() > 0) 
				{
					BigInteger count = new BigInteger("1");
					Object obdata=null;
					for (Iterator it = dataCount1.iterator(); it.hasNext();) 
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					query.setLength(0);
					query .append("SELECT tr.id,tr.taskname,tr.specifictask,ty.tasktype,tr.priority,emp1.empName as allotedto, ");
					query .append("emp2.empName as allotedby,tr.clientFor,tr.cName,tr.offering, ");
					query .append("emp3.empName as validateby2,emp4.empName as validateby1 ,tr.functional_Date, ");
					query .append("tr.technical_Date,tr.intiation,tr.completion, ");
					query .append("emp3.empName as guidance,tr.attachment,tr.userName,tr.date,tr.time ");
					query .append("FROM task_registration AS tr ");
					query .append("LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id ");
					query .append("LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id ");
					query .append("LEFT JOIN compliance_contacts AS cc3 ON tr.validate_By_2=cc3.id ");
					query .append("LEFT JOIN compliance_contacts AS cc4 ON tr.validate_By_1=cc4.id ");
					query .append("LEFT JOIN compliance_contacts AS cc5 ON tr.guidance=cc5.id  ");
					query .append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
					query .append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
					query .append("LEFT JOIN employee_basic emp3 ON cc3.emp_id= emp3.id ");
					query .append("LEFT JOIN employee_basic emp4 ON cc4.emp_id= emp4.id ");
					query .append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
					query .append("LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
					query .append("LEFT JOIN client_type ct ON tr.clientfor= ct.id  ");
					query.append("WHERE tr.status!='Done' ");
					if (contactId!=null ) 
					{
						query.append(" AND tr.allotedto IN ("+contactId.substring(0, contactId.length()-1)+") ");
					}
								
					if (getSearchField() != null && getSearchString() != null&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq")) {
							query.append("tr. " + getSearchField() + " = '"+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query.append(" tr. " + getSearchField()+ " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query.append(" tr. " + getSearchField() + " like '"+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query.append(" tr. " + getSearchField() + " <> '"+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query.append(" tr. " + getSearchField()+ " like '%" + getSearchString() + "'");
						}
					} 
					else if (getSord() != null && getSidx() != null&& !getSord().equalsIgnoreCase("")&& !getSidx().equalsIgnoreCase("")) 
					{
						query.append(" ORDER BY tr." + getSidx() + " "+ getSord() + "");
					}
					query.append(" limit " + from + "," + to);
					List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (dataCount != null && dataCount.size() > 0) 
					{
					    String clientVal=null;
					    String clientData=null;
						List fieldNames = Configuration.getColomnList("mapped_dar_configuration",accountID, connectionSpace,"dar_configuration");
						viewListForPending = new ArrayList<Object>();
						List<Object> listPending = new ArrayList<Object>();
						Object[] obdata1=null;
						DARReportHelper DRH=new DARReportHelper();
						for (Iterator it = dataCount.iterator(); it.hasNext();) 
						{
							obdata1 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if (obdata1[k] != null) 
								{
									if(k==0)
									{
										one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("intiation") || fieldNames.get(k).toString().equalsIgnoreCase("completion") || fieldNames.get(k).toString().equalsIgnoreCase("functional_Date") || fieldNames.get(k).toString().equalsIgnoreCase("technical_Date")|| fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("clientfor"))
									{
										clientVal= obdata1[k].toString();
										if (obdata1[k].toString().equalsIgnoreCase("PA")) 
										{
											one.put(fieldNames.get(k).toString(),"Prospect Associate");
										}
										else if (obdata1[k].toString().equalsIgnoreCase("PC")) 
										{
											one.put(fieldNames.get(k).toString(),"Prospect Client");
										}
										else if(obdata1[k].toString().equalsIgnoreCase("N"))
										{
											one.put(fieldNames.get(k).toString(),"Other");
										}
										else if(obdata1[k].toString().equalsIgnoreCase("EC"))
										{
											one.put(fieldNames.get(k).toString(),"Existing Client");
										}
										else if(obdata1[k].toString().equalsIgnoreCase("EA"))
										{
											one.put(fieldNames.get(k).toString(),"Existing Associate");
										}
										else if(obdata1[k].toString().equalsIgnoreCase("IN"))
										{
											one.put(fieldNames.get(k).toString(),"Internal");
										}
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("cName"))
									{
										clientData=obdata1[k].toString();
										one.put(fieldNames.get(k).toString(), DRH.clientName(clientVal, obdata1[k].toString(),connectionSpace));
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("offering"))
									{
										one.put(fieldNames.get(k).toString(), DRH.offeringName(clientVal,clientData,obdata1[k].toString(),connectionSpace));
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}
								}
								else
								{
									  one.put(fieldNames.get(k).toString(),"NA");
								}
							}
							listPending.add(one);
						}
						setViewListForPending(listPending);
						setTotal((int) Math.ceil((double) getRecords()/ (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String updateDarValidation() 
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try 
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				Map<String, Object> setVariables = new HashMap<String, Object>();
				Map<String, Object> whereCondition = new HashMap<String, Object>();
				while (it.hasNext()) 
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					//System.out.println("PRAM NAME :::: "+Parmname +">>>> PARAM VALUE :::   "+paramValue);
					if (!Parmname.equalsIgnoreCase("taskname")) 
					{
						setVariables.put(Parmname, paramValue);
					}
				}
				if (getReviewDocFileName()!=null) 
				{
                     String storeFilePath = new CreateFolderOs().createUserDir("DAR")+ "//"+getReviewDocFileName();
                     setVariables.put("reviewDoc", storeFilePath);
				}
				whereCondition.put("taskname", request.getParameter("taskname"));
				boolean updateFlag = cbt.updateTableColomn("dar_submission", setVariables, whereCondition, connectionSpace);
				if (updateFlag) 
				{
					addActionMessage("Data Validated Successfully!!!!!!!!!!!!");
					return SUCCESS;
				}
				else
				{
					addActionMessage("There is some error in data !!!!!!");
					return ERROR;
				}
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
	@SuppressWarnings("rawtypes")
	public String getProjectNames()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (getAllotedTo() != null && !getAllotedTo().equalsIgnoreCase(""))
				{
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					StringBuilder query = new StringBuilder();
					query.append("SELECT id,taskname FROM task_registration WHERE allotedto ='"+getAllotedTo()+"'");
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && data2.size() > 0)
					{
						jsonArr=new JSONArray();
						JSONObject formDetailsJson = new JSONObject();
						Object[] object = null;
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
								formDetailsJson = new JSONObject();
								formDetailsJson.put("ID", object[0].toString());
								formDetailsJson.put("NAME", object[1].toString());
								jsonArr.add(formDetailsJson);
							}
						}
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
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
	@SuppressWarnings("rawtypes")
	public String getProjectData() 
	{
		try 
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			taskObj = new DarSubmissionPojoBean();
			if (getProjectId() != null) 
			{
				StringBuilder query=new StringBuilder();
				query.append("SELECT technical_Date,functional_Date,intiation,completion FROM task_registration");
				query .append( " WHERE id='"+getProjectId()+"'");
			
				List getlist2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (getlist2!=null && getlist2.size()>0) 
				{
					TaskRegistrationHelper TRH=new TaskRegistrationHelper();
					Object[] object=null;
					for (Iterator iterator = getlist2.iterator(); iterator.hasNext();) 
					{
						object = (Object[]) iterator.next();
						if (object[0]!=null) 
						{
							taskObj.setTechnicalDate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[0]).split(" ")[0])+" "+TRH.getValueWithNullCheck(object[0]).split(" ")[1]);
						}
						else
						{
							taskObj.setTechnicalDate("No Technical Review Required");
						}
						if (object[1]!=null) 
						{
							taskObj.setFunctonalDate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[1]).split(" ")[0])+" "+TRH.getValueWithNullCheck(object[1]).split(" ")[1]);
						}
						else
						{
							taskObj.setFunctonalDate("No Functional Review Required");
						}
						taskObj.setInitiondate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[2])));
						taskObj.setComlpetiondate(DateUtil.convertDateToIndianFormat(TRH.getValueWithNullCheck(object[3]).split(" ")[0])+" "+TRH.getValueWithNullCheck(object[3]).split(" ")[1]);
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public List<ConfigurationUtilBean> getDarColumnMap() {
		return darColumnMap;
	}

	public void setDarColumnMap(List<ConfigurationUtilBean> darColumnMap) {
		this.darColumnMap = darColumnMap;
	}

	public List<ConfigurationUtilBean> getDarCalenderMap() {
		return darCalenderMap;
	}

	public void setDarCalenderMap(List<ConfigurationUtilBean> darCalenderMap) {
		this.darCalenderMap = darCalenderMap;
	}

	public List<ConfigurationUtilBean> getDarFileMap() {
		return darFileMap;
	}

	public void setDarFileMap(List<ConfigurationUtilBean> darFileMap) {
		this.darFileMap = darFileMap;
	}

	public List<ConfigurationUtilBean> getDarDropMap() {
		return darDropMap;
	}

	public void setDarDropMap(List<ConfigurationUtilBean> darDropMap) {
		this.darDropMap = darDropMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<Integer, String> getListTaskName() {
		return listTaskName;
	}

	public void setListTaskName(Map<Integer, String> listTaskName) {
		this.listTaskName = listTaskName;
	}

	public String getClientOf() {
		return clientOf;
	}

	public void setClientOf(String clientOf) {
		this.clientOf = clientOf;
	}

	public File getAttachmentt() {
		return attachmentt;
	}

	public void setAttachmentt(File attachmentt) {
		this.attachmentt = attachmentt;
	}

	public String getAttachmenttContentType() {
		return attachmenttContentType;
	}

	public void setAttachmenttContentType(String attachmenttContentType) {
		this.attachmenttContentType = attachmenttContentType;
	}

	public String getAttachmenttFileName() {
		return attachmenttFileName;
	}

	public void setAttachmenttFileName(String attachmenttFileName) {
		this.attachmenttFileName = attachmenttFileName;
	}

	public List<Object> getViewList() {
		return viewList;
	}

	public void setViewList(List<Object> viewList) {
		this.viewList = viewList;
	}

	

	public List<GridDataPropertyView> getTaskTypeViewProp() {
		return taskTypeViewProp;
	}

	public void setTaskTypeViewProp(List<GridDataPropertyView> taskTypeViewProp) {
		this.taskTypeViewProp = taskTypeViewProp;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DarSubmissionPojoBean getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(DarSubmissionPojoBean taskObj) {
		this.taskObj = taskObj;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getAttachFileMap() {
		return attachFileMap;
	}

	public void setAttachFileMap(Map<String, String> attachFileMap) {
		this.attachFileMap = attachFileMap;
	}

	public List<Object> getViewListForPending() {
		return viewListForPending;
	}

	public void setViewListForPending(List<Object> viewListForPending) {
		this.viewListForPending = viewListForPending;
	}

	public Map<Integer, String> getListTaskNameFovalidation() {
		return listTaskNameFovalidation;
	}

	public void setListTaskNameFovalidation(
			Map<Integer, String> listTaskNameFovalidation) {
		this.listTaskNameFovalidation = listTaskNameFovalidation;
	}

	public List<Object> getViewListForDashboard() {
		return viewListForDashboard;
	}

	public void setViewListForDashboard(List<Object> viewListForDashboard) {
		this.viewListForDashboard = viewListForDashboard;
	}
	public DarSubmissionPojoBean getDarObj() {
		return darObj;
	}

	public void setDarObj(DarSubmissionPojoBean darObj) {
		this.darObj = darObj;
	}
	

	public String getAllotedTo() {
		return allotedTo;
	}

	public void setAllotedTo(String allotedTo) {
		this.allotedTo = allotedTo;
	}

	public String getIdtask() {
		return idtask;
	}

	public void setIdtask(String idtask) {
		this.idtask = idtask;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public int getIndexVal() {
		return indexVal;
	}

	public void setIndexVal(int indexVal) {
		this.indexVal = indexVal;
	}

	public File getAttachmentt1() {
		return attachmentt1;
	}

	public void setAttachmentt1(File attachmentt1) {
		this.attachmentt1 = attachmentt1;
	}

	public String getAttachmentt1ContentType() {
		return attachmentt1ContentType;
	}

	public void setAttachmentt1ContentType(String attachmentt1ContentType) {
		this.attachmentt1ContentType = attachmentt1ContentType;
	}

	public String getAttachmentt1FileName() {
		return attachmentt1FileName;
	}

	public void setAttachmentt1FileName(String attachmentt1FileName) {
		this.attachmentt1FileName = attachmentt1FileName;
	}

	public File getAttachmentt2() {
		return attachmentt2;
	}

	public void setAttachmentt2(File attachmentt2) {
		this.attachmentt2 = attachmentt2;
	}

	public String getAttachmentt2ContentType() {
		return attachmentt2ContentType;
	}

	public void setAttachmentt2ContentType(String attachmentt2ContentType) {
		this.attachmentt2ContentType = attachmentt2ContentType;
	}

	public String getAttachmentt2FileName() {
		return attachmentt2FileName;
	}

	public void setAttachmentt2FileName(String attachmentt2FileName) {
		this.attachmentt2FileName = attachmentt2FileName;
	}

	public File getAttachmentt3() {
		return attachmentt3;
	}

	public void setAttachmentt3(File attachmentt3) {
		this.attachmentt3 = attachmentt3;
	}

	public String getAttachmentt3ContentType() {
		return attachmentt3ContentType;
	}

	public void setAttachmentt3ContentType(String attachmentt3ContentType) {
		this.attachmentt3ContentType = attachmentt3ContentType;
	}

	public String getAttachmentt3FileName() {
		return attachmentt3FileName;
	}

	public void setAttachmentt3FileName(String attachmentt3FileName) {
		this.attachmentt3FileName = attachmentt3FileName;
	}

	public File getAttachmentt4() {
		return attachmentt4;
	}

	public void setAttachmentt4(File attachmentt4) {
		this.attachmentt4 = attachmentt4;
	}

	public String getAttachmentt4ContentType() {
		return attachmentt4ContentType;
	}

	public void setAttachmentt4ContentType(String attachmentt4ContentType) {
		this.attachmentt4ContentType = attachmentt4ContentType;
	}

	public String getAttachmentt4FileName() {
		return attachmentt4FileName;
	}

	public void setAttachmentt4FileName(String attachmentt4FileName) {
		this.attachmentt4FileName = attachmentt4FileName;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public JSONArray getJsonArr() {
		return jsonArr;
	}

	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	public String getMonthCounter() {
		return monthCounter;
	}

	public void setMonthCounter(String monthCounter) {
		this.monthCounter = monthCounter;
	}

	public File getReviewDoc() {
		return reviewDoc;
	}

	public void setReviewDoc(File reviewDoc) {
		this.reviewDoc = reviewDoc;
	}

	public String getReviewDocContentType() {
		return reviewDocContentType;
	}

	public void setReviewDocContentType(String reviewDocContentType) {
		this.reviewDocContentType = reviewDocContentType;
	}

	public String getReviewDocFileName() {
		return reviewDocFileName;
	}

	public void setReviewDocFileName(String reviewDocFileName) {
		this.reviewDocFileName = reviewDocFileName;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	
	
}
