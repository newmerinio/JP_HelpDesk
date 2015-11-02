package com.Over2Cloud.ctrl.cps.corporate;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ScheduleConfigureMaster extends ActionSupport implements ServletRequestAware
{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	static final Log log = LogFactory.getLog(ScheduleConfigureMaster.class);
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> configureSchduleTextBox = null;
	private List<ConfigurationUtilBean> configureSchduleDropDown = null;
	private List<ConfigurationUtilBean> configureSchduleDate = null;
	private List<ConfigurationUtilBean> configureSchduleTime = null;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	private Map<String, String> escL2Emp = null;
	private Map<String, String> escL3Emp = null;
	private Map<String, String> escL4Emp = null;
	private Map<String, String> escL5Emp = null;
	private String l2, tat2, l3, tat3, l4, tat4, l5, tat5, configure_for, emp, department, deadline_date, deadline_time, reminder1, reminder2, reminder3, escalation;

	private Map<String, String> departmentData = null;
	private Map<String, String> empList = null;

	private List<Object> viewList;

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
	private String count;

	CommonOperInterface coi = new CommonConFactory().createInterface();

	private String deptId;

	public String beforeConfigureSchdule()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				returnResult = SUCCESS;
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

	public String viewConfigurationScheduleDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setConfigureSchduleViewProp();
				returnResult = SUCCESS;
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

	public void setConfigureSchduleViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_configure_schedule_configuration", accountID, connectionSpace, "", 0, "table_name", "configure_schedule_configuration");

			for (GridDataPropertyView gdp : returnResult)
			{

				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setAlign(gdp.getAlign());
				gpv.setWidth(gdp.getWidth());
				if (gdp.getFormatter() != null)
					gpv.setFormatter(gdp.getFormatter());
				masterViewProp.add(gpv);
			}
			System.out.println("masterViewProp   " + masterViewProp.size());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String beforeConfigureScheduleAdd()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageDataForConfigureSchdule();
			String[] loggedUserData = new String[7];
			loggedUserData = getEmpDetailsByUserName("WFPM", userName);
			String deptId=null;
			if (loggedUserData != null && loggedUserData.length > 0)
			{
				deptId = loggedUserData[4];
			}
			departmentData = new LinkedHashMap<String, String>();
			departmentData = getDepartment(deptId);
			escL2Emp = new LinkedHashMap<String, String>();
			escL3Emp = new LinkedHashMap<String, String>();
			escL4Emp = new LinkedHashMap<String, String>();
			escL5Emp = new LinkedHashMap<String, String>();

			escL2Emp = getComplianceAllContacts("2", "WFPM",deptId);
			escL3Emp =  escL2Emp;
			escL4Emp =  escL2Emp;
			escL5Emp =  escL2Emp;

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String empData()
	{
		try
		{
			empList = new LinkedHashMap<String, String>();
			empList = employeeList();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForConfigureSchdule()
	{
		try
		{
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_configure_schedule_configuration", accountID, connectionSpace, "", 0, "table_name", "configure_schedule_configuration");
			configureSchduleTextBox = new ArrayList<ConfigurationUtilBean>();
			configureSchduleDropDown = new ArrayList<ConfigurationUtilBean>();
			configureSchduleDate = new ArrayList<ConfigurationUtilBean>();
			configureSchduleTime = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("tat2") && !gdp.getColomnName().equalsIgnoreCase("tat3") && !gdp.getColomnName().equalsIgnoreCase("tat4") && !gdp.getColomnName().equalsIgnoreCase("tat5"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					configureSchduleTextBox.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					configureSchduleDropDown.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("Date") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					configureSchduleDate.add(obj);
				}
				if (gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					configureSchduleTime.add(obj);
				}
			}
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getComplianceAllContacts(String level, String module,String deptId)
	{

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
			qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE for_contact_sub_type_id=" + deptId + " and module_name='" + module + "' AND work_status!=1 AND emp.status='0' order by emp_name asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return empMap;
	}

	@SuppressWarnings("unchecked")
	public String[] getEmpDetailsByUserName(String moduleName, String userName)
	{
		String[] values = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

			StringBuilder query = new StringBuilder();
			query.append("select contact.id,emp.emp_name,emp.mobile_no,emp.email_id,emp.sub_type_id as deptid, dept.contact_subtype_name,emp.id as empid from primary_contact as emp ");
			query.append(" inner join manage_contact as contact on contact.emp_id=emp.id");
			query.append(" inner join contact_sub_type as dept on emp.sub_type_id=dept.id ");
			query.append(" inner join contact_type as gi on dept.contact_type_id=gi.id ");
			query.append(" inner join user_account as uac on emp.user_account_id=uac.id where contact.module_name='" + moduleName + "' and uac.user_name='");
			query.append(userName + "' and contact.for_contact_sub_type_id=dept.id");

			List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				values = new String[7];
				Object[] object = (Object[]) dataList.get(0);
				values[0] = getValueWithNullCheck(object[0]);
				values[1] = getValueWithNullCheck(object[1]);
				values[2] = getValueWithNullCheck(object[2]);
				values[3] = getValueWithNullCheck(object[3]);
				values[4] = getValueWithNullCheck(object[4]);
				values[5] = getValueWithNullCheck(object[5]);
				values[6] = getValueWithNullCheck(object[6]);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public Map<String, String> getDepartment(String deptID)
	{
		Map<String, String> deptlist = new LinkedHashMap<String, String>();
		try
		{
			if(deptID!=null)
			{
				StringBuilder qryString = new StringBuilder();
				qryString.append("SELECT dept.id,dept.contact_subtype_name FROM primary_contact AS emp");
				qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
				qryString.append(" INNER JOIN contact_sub_type AS dept ON cc.from_contact_sub_type_id=dept.id");
				qryString.append(" WHERE for_contact_sub_type_id=" + deptID + " AND module_name='WFPM' AND work_status!=1 AND emp.status='0' order by dept.contact_subtype_name asc");
				if (qryString.toString() != null)
				{
					List<?> dataList = new createTable().executeAllSelectQuery(qryString.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								deptlist.put(object[0].toString(), object[1].toString());
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
		return deptlist;

	}

	public String addConfigureData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_configure_schedule_configuration", accountID, connectionSpace, "", 0, "table_name", "configure_schedule_configuration");
				if (statusColName != null && statusColName.size()>0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						if (gdp.getColomnName().equalsIgnoreCase("status"))
						{
							ob1.setConstraint("default 'Active'");
						}
						else
						{
							ob1.setConstraint("default NULL");
						}
						Tablecolumesaaa.add(ob1);
					}
					
					ob1 = new TableColumes();
					ob1.setColumnname("scheduleFor");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("startFrom");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default 'NULL'");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("dueTill");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("mappedWith");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default 'NULL'");
					Tablecolumesaaa.add(ob1);

					cbt.createTable22("configure_schedule", Tablecolumesaaa, connectionSpace);
					
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;

					if (emp != null)
					{
						String[] arr = emp.split(",");
						for (int i = 0; i < arr.length; i++)
						{
							ob = new InsertDataTable();
							ob.setColName("emp");
							ob.setDataName(getValueWithNullCheck(arr[i].trim()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("l2");
							ob.setDataName(getValueWithNullCheck(l2));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("l3");
							ob.setDataName(getValueWithNullCheck(l3));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("l4");
							ob.setDataName(getValueWithNullCheck(l4));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("l5");
							ob.setDataName(getValueWithNullCheck(l5));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("tat2");
							ob.setDataName(getValueWithNullCheck(tat2));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("tat3");
							ob.setDataName(getValueWithNullCheck(tat3));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("tat4");
							ob.setDataName(getValueWithNullCheck(tat4));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("tat5");
							ob.setDataName(getValueWithNullCheck(tat5));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("configure_for");
							ob.setDataName(getValueWithNullCheck(configure_for));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("deadline_date");
							ob.setDataName(getValueWithNullCheck(DateUtil.convertDateToUSFormat(deadline_date)));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("deadline_time");
							ob.setDataName(getValueWithNullCheck(deadline_time));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("reminder1");
							ob.setDataName(getValueWithNullCheck(DateUtil.convertDateToUSFormat(reminder1)));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("reminder2");
							ob.setDataName(getValueWithNullCheck(DateUtil.convertDateToUSFormat(reminder2)));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("reminder3");
							ob.setDataName(getValueWithNullCheck(DateUtil.convertDateToUSFormat(reminder3)));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("escalation");
							ob.setDataName(getValueWithNullCheck(escalation));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("department");
							ob.setDataName(getValueWithNullCheck(department));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("user");
							ob.setDataName(userName);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("scheduleFor");
							ob.setDataName(getValueWithNullCheck(request.getParameter("scheduleFor")));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("startFrom");
							ob.setDataName(getValueWithNullCheck(request.getParameter("startFrom")));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("dueTill");
							ob.setDataName(getValueWithNullCheck(request.getParameter("dueTill")));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("mappedWith");
							ob.setDataName(getValueWithNullCheck(request.getParameter("mappedWith")));
							insertData.add(ob);

							
							status = cbt.insertIntoTable("configure_schedule", insertData, connectionSpace);
							insertData.clear();
						}
					}
					if (status)
					{
						addActionMessage("Configure Schedule Added successfully!!!");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> employeeList()
	{
		Map<String, String> deptlist = new LinkedHashMap<String, String>();
		try
		{
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			String loggedDetail[] = CUA.getEmpDetailsByUserName("WFPM", userName);
			StringBuilder qryString = new StringBuilder();
			List dataList = null;
			if (!deptId.equalsIgnoreCase("-1"))
			{
				qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
				qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
				qryString.append(" WHERE for_contact_sub_type_id=" + loggedDetail[4] + " AND from_contact_sub_type_id='" + deptId + "' AND module_name='WFPM' AND work_status!=1 AND emp.status='0' order by emp_name asc");
				dataList = coi.executeAllSelectQuery(qryString.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator it = dataList.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();

						if (obdata[0] != null && obdata[1] != null)
						{
							deptlist.put(obdata[0].toString(), obdata[1].toString());
						}

					}

				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("deptlist  " + deptlist.size());
		return deptlist;

	}

	@SuppressWarnings("unchecked")
	public String viewConfigureScheduleMasterData()
	{

		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from configure_schedule");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			if (dataCount != null)
			{
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from configure_schedule as cs ");
				queryEnd.append(" inner join primary_contact as eb on cs.emp=eb.id");
				queryEnd.append(" inner join contact_sub_type as dept on cs.department=dept.id");

				List fieldNames = Configuration.getColomnList("mapped_configure_schedule_configuration", accountID, connectionSpace, "configure_schedule_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{

						if (obdata.toString().equalsIgnoreCase("department"))
						{
							queryTemp.append("dept.contact_subtype_name" + ",");
						}
						else if (obdata.toString().equalsIgnoreCase("emp"))
						{
							queryTemp.append("eb.emp_name" + ",");
						}
						else
						{
							queryTemp.append("cs." + obdata.toString() + ",");
						}

					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" cs.status='Active' ");

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				// query.append(" order by cp.src_subtype ");
				System.out.println("query.toString()>>" + query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("NA") && obdata[k].toString().length() > 0)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								}
								else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("deadline_date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("reminder1"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("reminder2"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("reminder3"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l2"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										}
										else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}

									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l3"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										}
										else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l4"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										}
										else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l5"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										}
										else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					return "success";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private String getNameByccid(String string)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		String emp = null;

		try
		{
			qryString.append("select emp.emp_name, cc.id from primary_contact as emp inner join manage_contact as cc on cc.emp_id=emp.id");
			qryString.append(" where cc.id=" + string + "");
			// System.out.println("qryString " + qryString);
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						emp = objectCol[0].toString();
					}
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return emp;
	}

	@SuppressWarnings("unchecked")
	public String editConfigureScheduleMasterDataGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
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
						{
							if (parmName.equalsIgnoreCase("userName"))
							{
								wherClause.put(parmName, userName);
							}
							else if (parmName.equalsIgnoreCase("date"))
							{
								wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
							}
							else if (parmName.equalsIgnoreCase("time"))
							{
								wherClause.put(parmName, DateUtil.getCurrentTimeHourMin());
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("id", getId());
					boolean status = cbt.updateTableColomn("configure_schedule", wherClause, condtnBlock, connectionSpace);
					if (status)
					{
						returnResult = SUCCESS;
					}
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					for (String H : tempIds)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						wherClause.put("status", "Inactive");
						condtnBlock.put("id",H);
						cbt.updateTableColomn("configure_schedule", wherClause, condtnBlock, connectionSpace);
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method corporate master of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	public Map<String, String> getEscL2Emp()
	{
		return escL2Emp;
	}

	public void setEscL2Emp(Map<String, String> escL2Emp)
	{
		this.escL2Emp = escL2Emp;
	}

	public Map<String, String> getEscL3Emp()
	{
		return escL3Emp;
	}

	public void setEscL3Emp(Map<String, String> escL3Emp)
	{
		this.escL3Emp = escL3Emp;
	}

	public Map<String, String> getEscL4Emp()
	{
		return escL4Emp;
	}

	public void setEscL4Emp(Map<String, String> escL4Emp)
	{
		this.escL4Emp = escL4Emp;
	}

	public Map<String, String> getEscL5Emp()
	{
		return escL5Emp;
	}

	public void setEscL5Emp(Map<String, String> escL5Emp)
	{
		this.escL5Emp = escL5Emp;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public Map<String, String> getEmpList()
	{
		return empList;
	}

	public void setEmpList(Map<String, String> empList)
	{
		this.empList = empList;
	}

	public String getTat2()
	{
		return tat2;
	}

	public void setTat2(String tat2)
	{
		this.tat2 = tat2;
	}

	public String getTat3()
	{
		return tat3;
	}

	public void setTat3(String tat3)
	{
		this.tat3 = tat3;
	}

	public String getTat4()
	{
		return tat4;
	}

	public void setTat4(String tat4)
	{
		this.tat4 = tat4;
	}

	public String getTat5()
	{
		return tat5;
	}

	public void setTat5(String tat5)
	{
		this.tat5 = tat5;
	}

	public String getDeadline_date()
	{
		return deadline_date;
	}

	public void setDeadline_date(String deadlineDate)
	{
		deadline_date = deadlineDate;
	}

	public String getDeadline_time()
	{
		return deadline_time;
	}

	public void setDeadline_time(String deadlineTime)
	{
		deadline_time = deadlineTime;
	}

	public String getReminder1()
	{
		return reminder1;
	}

	public void setReminder1(String reminder1)
	{
		this.reminder1 = reminder1;
	}

	public String getReminder2()
	{
		return reminder2;
	}

	public void setReminder2(String reminder2)
	{
		this.reminder2 = reminder2;
	}

	public String getReminder3()
	{
		return reminder3;
	}

	public void setReminder3(String reminder3)
	{
		this.reminder3 = reminder3;
	}

	public String getEscalation()
	{
		return escalation;
	}

	public void setEscalation(String escalation)
	{
		this.escalation = escalation;
	}

	public String getL2()
	{
		return l2;
	}

	public void setL2(String l2)
	{
		this.l2 = l2;
	}

	public String getL3()
	{
		return l3;
	}

	public void setL3(String l3)
	{
		this.l3 = l3;
	}

	public String getL4()
	{
		return l4;
	}

	public void setL4(String l4)
	{
		this.l4 = l4;
	}

	public String getL5()
	{
		return l5;
	}

	public void setL5(String l5)
	{
		this.l5 = l5;
	}

	public String getConfigure_for()
	{
		return configure_for;
	}

	public void setConfigure_for(String configureFor)
	{
		configure_for = configureFor;
	}

	public List<Object> getViewList()
	{
		return viewList;
	}

	public void setViewList(List<Object> viewList)
	{
		this.viewList = viewList;
	}

	public List<ConfigurationUtilBean> getConfigureSchduleTextBox()
	{
		return configureSchduleTextBox;
	}

	public void setConfigureSchduleTextBox(List<ConfigurationUtilBean> configureSchduleTextBox)
	{
		this.configureSchduleTextBox = configureSchduleTextBox;
	}

	public List<ConfigurationUtilBean> getConfigureSchduleDropDown()
	{
		return configureSchduleDropDown;
	}

	public void setConfigureSchduleDropDown(List<ConfigurationUtilBean> configureSchduleDropDown)
	{
		this.configureSchduleDropDown = configureSchduleDropDown;
	}

	public List<ConfigurationUtilBean> getConfigureSchduleDate()
	{
		return configureSchduleDate;
	}

	public void setConfigureSchduleDate(List<ConfigurationUtilBean> configureSchduleDate)
	{
		this.configureSchduleDate = configureSchduleDate;
	}

	public List<ConfigurationUtilBean> getConfigureSchduleTime()
	{
		return configureSchduleTime;
	}

	public void setConfigureSchduleTime(List<ConfigurationUtilBean> configureSchduleTime)
	{
		this.configureSchduleTime = configureSchduleTime;
	}

	public String getEmp()
	{
		return emp;
	}

	public void setEmp(String emp)
	{
		this.emp = emp;
	}

	public Map<String, String> getDepartmentData()
	{
		return departmentData;
	}

	public void setDepartmentData(Map<String, String> departmentData)
	{
		this.departmentData = departmentData;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

}
