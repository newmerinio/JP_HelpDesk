package com.Over2Cloud.ctrl.compliance.complContacts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
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
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ComplianceContactsAction extends ActionSupport implements ServletRequestAware
{

	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String deptLevel = (String) session.get("userDeptLevel");
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(ComplianceContactsAction.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> complDDBox = null;
	private List<ConfigurationUtilBean> complTxtBox = null;

	private Map<Integer, String> subDeptMap = null;
	private Map<Integer, String> empMap = null;
	private Map<String, String> columnMap;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private List<Object> masterViewList1;
	private Map<String, String> level = null;
	private Map<Integer, String> employeeList;
	private String mainHeaderName;
	private String fromDept;
	private String forDept;
	private String empName;
	private String selectedId;
	private String userDeptId;
	private String userDeptName;
	private String forDeptId;
	private String moduleName;
	private String mappedLevel;
	private JSONArray jsonObjArray;
	private String contactId;
	private String levelName;
	private String groupField;
	private String deptName;
	private String leveldata;
	private JSONArray deptMap = null;
	private JSONArray levelValue = null;
	private String totalCount;
	private Map<String, String> groupMap = null;
	private Map<String, String> subTypeMap;
	private Map<String, String> levelMap;
	private Map<String, String> moduleList;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String subType;
	private String forModule;
	private boolean hodStatus;
	private boolean mgtStatus;

	@SuppressWarnings("unchecked")
	@Override
	public String execute()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				level = new LinkedHashMap<String, String>();
				int lastCount = 0;
				if (moduleName != null)
				{

					if (moduleName.equalsIgnoreCase("HDM"))
					{
						lastCount = 5;
					}
					else if (moduleName.equalsIgnoreCase("WFPM"))
					{
						lastCount = 5;
					}
					else if (moduleName.equalsIgnoreCase("COMPL"))
					{
						lastCount = 2;
					}
					else if (moduleName.equalsIgnoreCase("FM"))
					{
						lastCount = 5;
					}

					lastCount = 5;
					for (int i = 2; i <= lastCount; i++)
					{
						level.put(String.valueOf(i), "Level " + i);
					}
				}

				subTypeMap = new HashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List subTypeList = cbt.executeAllSelectQuery("select distinct dept.id,dept.contact_subtype_name from contact_sub_type as dept inner join manage_contact as compl on compl.from_contact_sub_type_id=dept.id order by dept.contact_subtype_name", connectionSpace);
				if (subTypeList != null && subTypeList.size() > 0)
				{
					for (Iterator iterator = subTypeList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								subTypeMap.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				/*
				 * totalCount=getCounters(connectionSpace);
				 * System.out.println("totalCount" +totalCount);
				 */
				moduleList = new LinkedHashMap<String, String>();
				moduleList = CommonWork.fetchAppAssignedUser(connectionSpace, userName);

				return SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), exp);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	/*
	 * @SuppressWarnings("rawtypes") public String beforeContactAdd() { boolean
	 * sessionFlag = ValidateSession.checkSession(); if (sessionFlag) { try {
	 * ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
	 * 
	 * subDeptMap = new LinkedHashMap<Integer, String>(); //deptMap =
	 * cmnOper.getAllDepartment(); List loggedUserData = new
	 * ComplianceUniversalAction
	 * ().getEmpDataByUserName(Cryptography.encrypt(userName,
	 * DES_ENCRYPTION_KEY), "1"); if (loggedUserData != null &&
	 * loggedUserData.size() > 0) { for (Iterator iterator =
	 * loggedUserData.iterator(); iterator.hasNext();) { Object[] object =
	 * (Object[]) iterator.next(); userDeptId = object[3].toString();
	 * userDeptName = object[4].toString(); } } setAddPageDataFields(); return
	 * SUCCESS; } catch (Exception exp) { log.error("Date : " +
	 * DateUtil.getCurrentDateIndianFormat() + " Time: " +
	 * DateUtil.getCurrentTime() + " " +
	 * "Problem in Compliance  method beforeContactAdd of class " + getClass(),
	 * exp); exp.printStackTrace(); return ERROR; } } else { return LOGIN; } }
	 */

	@SuppressWarnings("unchecked")
	public String beforeContactAdd()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ComplianceCommonOperation cmnOper = new ComplianceCommonOperation();
				moduleList = new LinkedHashMap<String, String>();
				moduleList = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				subDeptMap = new LinkedHashMap<Integer, String>();
				// deptMap = cmnOper.getAllDepartment();
				List loggedUserData = new ComplianceUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");
				if (loggedUserData != null && loggedUserData.size() > 0)
				{
					for (Iterator iterator = loggedUserData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userDeptId = object[3].toString();
						userDeptName = object[4].toString();
						subDeptMap = cmnOper.getSubDeptByDeptId(userDeptId);
					}
				}
				setAddPageDataFields();
				return SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setAddPageDataFields()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		String accountID = (String) session.get("accountid");
		if (sessionFlag)
		{
			try
			{
				groupMap = new LinkedHashMap<String, String>();
				complDDBox = new ArrayList<ConfigurationUtilBean>();
				complTxtBox = new ArrayList<ConfigurationUtilBean>();
				List<GridDataPropertyView> complianceFieldsName = Configuration.getConfigurationData("mapped_manage_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "manage_contact_configuration");
				if (complianceFieldsName != null)
				{
					ConfigurationUtilBean conUtilBean = null;
					for (GridDataPropertyView gdp : complianceFieldsName)
					{
						conUtilBean = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("from_contact_sub_type_id"))
							{
								String userType = (String) session.get("userTpe");
								String query = null;
								if (userType != null && userType.equalsIgnoreCase("H"))
								{
									String s[] = CommonWork.fetchCommonDetails(userName, connectionSpace);
									hodStatus = true;
									query = "SELECT id,branch_name FROM branch_office WHERE head_id = '" + s[2] + "' ORDER BY branch_name";
								}
								else if (userType != null && userType.equalsIgnoreCase("M"))
								{
									mgtStatus = true;
									hodStatus = true;
									query = "SELECT id,country_name FROM country_office ORDER BY country_name";
								}
								else
								{
									String s[] = CommonWork.fetchCommonDetails(userName, connectionSpace);
									query = "SELECT id,contact_name FROM contact_type WHERE mapped_level = '" + s[1] + "' ORDER BY contact_name";
								}
								if (query != null)
								{
									List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
									if (dataList != null && dataList.size() > 0)
									{
										for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
										{
											Object[] object = (Object[]) iterator.next();
											if (object[0] != null && object[1] != null)
											{
												groupMap.put(object[0].toString(), object[1].toString());
											}
										}
									}
								}
								conUtilBean.setField_name("Group Name");
								conUtilBean.setField_value("contact_type");
								complDDBox.add(conUtilBean);

								conUtilBean = new ConfigurationUtilBean();
								conUtilBean.setField_name("Sub-Group Name");
								conUtilBean.setField_value("contact_sub_type");
								complDDBox.add(conUtilBean);

								conUtilBean = new ConfigurationUtilBean();
								conUtilBean.setField_name(gdp.getHeadingName());
								conUtilBean.setField_value(gdp.getColomnName());
								conUtilBean.setColType(gdp.getColType());
								conUtilBean.setValidationType(gdp.getValidationType());
								if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
								{
									conUtilBean.setMandatory(true);
								}
								else
								{
									conUtilBean.setMandatory(false);
								}
								complDDBox.add(conUtilBean);
							}

						}
						else if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setColType(gdp.getColType());
							conUtilBean.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							}
							else
							{
								conUtilBean.setMandatory(false);
							}
							complTxtBox.add(conUtilBean);
						}
					}
					/*
					 * if(moduleName!=null && moduleName.equalsIgnoreCase("HDM")
					 * && deptLevel.equalsIgnoreCase("2")) { conUtilBean=new
					 * ConfigurationUtilBean();
					 * conUtilBean.setField_name("Sub-Dept Name");
					 * conUtilBean.setField_value("subdeptname");
					 * conUtilBean.setColType("D");
					 * conUtilBean.setMandatory(true);
					 * complDDBox.add(conUtilBean); }
					 */
				}
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
	}

	public String complianceContactsAdd()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_manage_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "manage_contact_configuration");
				if (statusColName != null)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false, flag = false;
					TableColumes ob1 = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("manage_contact", Tablecolumesaaa, connectionSpace);

					String module = request.getParameter("module_name");
					// String module =getForModule();

					if (empName != null)
						empName.concat(",");

					ob = new InsertDataTable();
					ob.setColName("from_contact_sub_type_id");
					ob.setDataName(fromDept);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("for_contact_sub_type_id");
					ob.setDataName(forDept);
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
					ob.setColName("work_status");
					ob.setDataName("0");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("level");
					ob.setDataName("1");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("module_name");
					ob.setDataName(moduleName);
					insertData.add(ob);

					if (module != null && module.equalsIgnoreCase("HDM"))
					{
						ob = new InsertDataTable();
						ob.setColName("dept_level");
						ob.setDataName("2");
						insertData.add(ob);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("dept_level");
						ob.setDataName("1");
						insertData.add(ob);
					}

					if (empName != null)
					{
						String empArr[] = empName.split(",");
						for (int i = 0; i < empArr.length; i++)
						{
							ob = new InsertDataTable();
							ob.setColName("emp_id");
							ob.setDataName(empArr[i]);
							insertData.add(ob);
							flag = getUpdateContact(forDept, empArr[i], module);
							if (!flag)
							{
								status = cbt.insertIntoTable("manage_contact", insertData, connectionSpace);
							}
							insertData.remove(insertData.size() - 1);
						}
					}
					if (status)
					{
						addActionMessage("Contact details added successfully!!!");
						returnResult = SUCCESS;
					}
					else if (flag)
					{
						addActionMessage("Contact details added successfully!!!");
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("There is some error in data!");
						returnResult = ERROR;
					}
				}

			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method complianceTaskAdd of class " + getClass(), exp);
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
	public boolean getUpdateContact(String forDept2, String empId, String ModuleName)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "SELECT * FROM manage_contact WHERE work_status = '1' AND for_contact_sub_type_id='" + forDept2 + "' AND emp_id ='" + empId + "' AND module_name='" + moduleName + "'";
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				flag = true;
				StringBuilder query2 = new StringBuilder();
				query2.append("UPDATE manage_contact SET work_status = '0' WHERE for_contact_sub_type_id='" + forDept2 + "' AND emp_id ='" + empId + "' AND module_name='" + moduleName + "'");
				cbt.updateTableColomn(connectionSpace, query2);
			}
			else
			{
				flag = false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public String beforeViewComplContacts()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String arr[] = new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName) ;
				if (arr != null && !arr[4].equalsIgnoreCase(""))
				{
					mainHeaderName =  arr[4];
				}
				else
				{
					mainHeaderName = "NA";
				}
				StringBuilder str = new StringBuilder();
				str.append("{value:'");
				if (moduleName != null)
				{
					for (int i = 1; i <= 5; i++)
					{
						str.append(i + ":Level " + i + ";");
					}
				}
				String test = str.substring(0, (str.toString().length() - 1));
				test = test + "'}";
				levelName = test;
				setViewProp();

				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
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
	public void setViewProp()
	{
		GridDataPropertyView gpv = null;

		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("subdept.subdeptname");
			gpv.setHeadingName("Sub Department");
			gpv.setEditable("true");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			gpv.setFormatter("false");
			gpv.setWidth(250);
			masterViewProp.add(gpv);
			groupField = "subdept.subdeptname";
		}
		else
		{
			groupField = "level";
		}
		gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("true");
		gpv.setFormatter("false");
		gpv.setWidth(50);
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.emp_name");
		gpv.setHeadingName("Name");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(100);
		}
		else
		{
			gpv.setWidth(150);
		}
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("grp.contact_name");
		gpv.setHeadingName("From Contact Type");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(250);
		}
		else
		{
			gpv.setWidth(150);
		}
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("dept.contact_subtype_name");
		gpv.setHeadingName("From Contact Sub-Type");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(250);
		}
		else
		{
			gpv.setWidth(150);
		}
		masterViewProp.add(gpv);

		/*gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.designation");
		gpv.setHeadingName("Designation");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(325);
		}
		else
		{
			gpv.setWidth(150);
		}
		masterViewProp.add(gpv);*/

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.mobile_no");
		gpv.setHeadingName("Mobile No");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(100);
		}
		else
		{
			gpv.setWidth(100);
		}
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("emp.email_id");
		gpv.setHeadingName("E-Mail");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(325);
		}
		else
		{
			gpv.setWidth(250);
		}
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("work_status");
		gpv.setHeadingName("Status");
		gpv.setEditable("true");
		gpv.setSearch("true");
		gpv.setHideOrShow("true");
		gpv.setFormatter("false");
		if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
		{
			gpv.setWidth(325);
		}
		else
		{
			gpv.setWidth(250);
		}
		masterViewProp.add(gpv);
		session.put("masterViewProp", masterViewProp);
	}

	@SuppressWarnings("unchecked")
	public String getCounters(SessionFactory connectionSpace)
	{
		String dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
		String counter = null;
		StringBuilder builder = new StringBuilder();
		if (dept_subDeptId != null && !dept_subDeptId.equalsIgnoreCase(" "))
		{
			builder.append(" SELECT count(*) FROM manage_contact AS ctm ");
			builder.append(" INNER JOIN primary_contact AS emp ON emp.id=ctm.emp_id ");
			builder.append(" INNER JOIN contact_sub_type AS dept ON ctm.from_contact_sub_type_id=dept.id ");
			builder.append(" INNER JOIN contact_type AS grp ON grp.id=dept.contact_type_id ");
			builder.append("  where ctm.work_status!=1 and ctm.for_contact_sub_type_id=" + dept_subDeptId + " and module_name='" + moduleName + "'");
		}
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
		{
			counter = dataList.get(0).toString();
		}
		return counter;
	}

	@SuppressWarnings("unchecked")
	public List getDeptName(String moduleName, String deptID, SessionFactory connectionSpace)
	{
		List data = new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{

			query.append(" SELECT  dt.id,dt.contact_subtype_name FROM contact_sub_type as dt");
			query.append(" INNER JOIN manage_contact as cc ON dt.id=cc.from_contact_sub_type_id");
			query.append(" WHERE cc.module_name='" + moduleName + "' AND cc.for_contact_sub_type_id='" + deptID + "' GROUP BY dt.contact_subtype_name");

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings({ "unchecked" })
	public String viewComplContacts()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String dept_subDeptId = null;
				dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
				List<Object> Listhb = new ArrayList<Object>();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("ctm.id,");
							}
							else
							{
								query.append(gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							query.append(gpv.getColomnName().toString());
						}

						i++;
					}
				}
				query.append(" FROM manage_contact AS ctm");
				query.append(" INNER JOIN primary_contact AS emp ON emp.id=ctm.emp_id ");
				if (dept_subDeptId != null && !dept_subDeptId.equalsIgnoreCase(" "))
				{
					if (moduleName != null && moduleName.equalsIgnoreCase("HDM"))
					{
						query.append(" INNER JOIN subdepartment AS subdept ON ctm.for_contact_sub_type_id=subdept.id  INNER JOIN contact_sub_type AS dept ON subdept.deptid=dept.id INNER JOIN contact_type AS grp ON grp.id=dept.contact_type_id ");
						query.append("  where ctm.work_status!=1 and ctm.for_contact_sub_type_id IN (select id from subdepartment where deptid='" + dept_subDeptId + "') and ctm.module_name='" + moduleName + "'");
					}
					else
					{
						query.append(" INNER JOIN contact_sub_type AS dept ON ctm.from_contact_sub_type_id=dept.id INNER JOIN contact_type AS grp ON grp.id=dept.contact_type_id ");
						query.append("  where ctm.work_status!=1 and ctm.for_contact_sub_type_id=" + dept_subDeptId + " and module_name='" + moduleName + "'");
					}
					if (getSubType() != null && !getSubType().equalsIgnoreCase("-1"))
					{
						query.append(" and ctm.from_contact_sub_type_id='" + getSubType() + "' ");
					}

					if (getLeveldata() != null && !getLeveldata().equals("-1"))
					{
						query.append(" and level='" + getLeveldata() + "' ");
					}
				}
				else
				{
					query.append(" INNER JOIN contact_sub_type AS dept ON ctm.from_contact_sub_type_id=dept.id INNER JOIN contact_type AS grp ON grp.id=dept.contact_type_id ");
					query.append("  where ctm.work_status!=1 ");
				}
				query.append(" ORDER BY emp.emp_name");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if (gpv.getColomnName().equalsIgnoreCase("level"))
								{
									one.put(gpv.getColomnName(), "Level-" + obdata[k].toString());
								}
								else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
								}
							}
							else
							{
								one.put(gpv.getColomnName().toString(), "NA");
							}
							k++;
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method viewComplContacts of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getDept_SubdeptId(String userName, String deptLevel, String moduleName)
	{
		String dept_subDeptId = null;
		try
		{
			String arr[]=new com.Over2Cloud.common.compliance.ComplianceUniversalAction().getEmpDataByUserName(userName);
			if(arr!=null && arr[3]!=null)
			{
				dept_subDeptId = arr[3];
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dept_subDeptId;
	}

	public String beforeFindEmpDetail()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();

				gpv.setColomnName("id");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);
				if (deptLevel != null && deptLevel.equalsIgnoreCase("2"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("subdeptname");
					gpv.setHeadingName("Sub Department");
					gpv.setEditable("true");
					gpv.setSearch("true");
					gpv.setHideOrShow("true");
					gpv.setFormatter("false");
					masterViewProp.add(gpv);
				}
				gpv = new GridDataPropertyView();
				gpv.setColomnName("emp_name");
				gpv.setHeadingName("Name");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				/*
				 * gpv = new GridDataPropertyView();
				 * gpv.setColomnName("designation");
				 * gpv.setHeadingName("Designation"); gpv.setEditable("true");
				 * gpv.setSearch("true"); gpv.setHideOrShow("false");
				 * gpv.setFormatter("false"); masterViewProp.add(gpv);
				 */

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mobile_no");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("email_id");
				gpv.setHeadingName("E-Mail");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

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

	@SuppressWarnings("unchecked")
	public String findEmpDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<Object> Listhb = new ArrayList<Object>();
				masterViewList = new ArrayList<Object>();
				List empList = new ArrayList();
				StringBuilder queryIS = new StringBuilder();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				empList = cbt.executeAllSelectQuery("SELECT emp_id FROM manage_contact WHERE for_contact_sub_type_id='" + getForDeptId() + "' AND work_status in ('0','3')  AND module_name='" + moduleName + "'", connectionSpace);

				if (empList != null && empList.size() > 0)
				{
					queryIS.append("SELECT emp.id, emp.emp_name,emp.mobile_no,emp.email_id");
					queryIS.append(" FROM primary_contact AS emp ");
					queryIS.append(" WHERE emp.sub_type_id='" + getFromDept() + "'");
					queryIS.append(" AND emp.id NOT IN (SELECT emp_id FROM manage_contact WHERE for_contact_sub_type_id='" + getForDeptId() + "' AND work_status in ('0','3') AND emp.status=0 AND module_name='" + moduleName + "')");
					queryIS.append(" AND emp.status=0  order by emp_name asc");
				}
				else
				{
					queryIS.append("SELECT emp.id, emp.emp_name,emp.mobile_no,emp.email_id ");
					queryIS.append(" FROM primary_contact AS emp ");
					queryIS.append(" WHERE emp.sub_type_id='" + getFromDept() + "' AND emp.status=0  ");
					queryIS.append(" order by emp.emp_name asc");
				}
				empList = cbt.executeAllSelectQuery(queryIS.toString(), connectionSpace);
				if (empList != null && empList.size() > 0)
				{
					for (Iterator iterator = empList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						if (object[0] != null)
							one.put("id", object[0].toString());
						else
							one.put("id", "NA");

						if (object[1] != null)
							one.put("emp_name", object[1].toString());
						else
							one.put("emp_name", "NA");

						if (object[2] != null)
							one.put("mobile_no", object[2].toString());
						else
							one.put("mobile_no", "NA");

						if (object[3] != null)
							one.put("email_id", object[3].toString());
						else
							one.put("email_id", "NA");

						/*
						 * if (object[4] != null) one.put("subdeptname",
						 * object[4].toString()); else one.put("subdeptname",
						 * "NA");
						 */

						/*if (object[4] != null)
							one.put("designation", object[4].toString());
						else
							one.put("designation", "NA");*/

						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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

	public String beforeComplContactEdits()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				level = new LinkedHashMap<String, String>();
				level.put("1", "Level 1");
				level.put("2", "Level 2");
				setAddPageDataFields();
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeContactAdd of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeMappedContact()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				level = new LinkedHashMap<String, String>();
				int lastCount = 0;
				if (moduleName != null)
				{
					lastCount = 5;
					for (int i = 2; i <= lastCount; i++)
					{
						level.put(String.valueOf(i), "Level " + i);
					}
				}

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1;

				ob1 = new TableColumes();
				ob1.setColumnname("mapped_with");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("contact_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("contact_mapping_detail", Tablecolumesaaa, connectionSpace);

				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeMappedContact of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings(
	{ "unchecked" })
	public String getMappedEmp()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (moduleName != null && mappedLevel != null)
				{
					String dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
					if (dept_subDeptId != null)
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						jsonObjArray = new JSONArray();
						StringBuilder qString = new StringBuilder();
						qString.append("SELECT  eb.id,eb.emp_name FROM manage_contact AS cc ");
						qString.append(" INNER JOIN primary_contact AS eb ON eb.id=cc.emp_id WHERE cc.for_contact_sub_type_id=" + dept_subDeptId);
						qString.append(" AND cc.module_name='" + moduleName + "' AND level='" + mappedLevel + "' ORDER BY eb.emp_name");
						List data = cbt.executeAllSelectQuery(qString.toString(), connectionSpace);
						if (data != null && data.size() > 0)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null || object[1] != null)
								{
									JSONObject jsonObj = new JSONObject();
									jsonObj.put("id", object[0].toString());
									jsonObj.put("name", object[1].toString());
									jsonObjArray.add(jsonObj);
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getMappedEmp of class " + getClass(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeGetUnMappedEmp()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();

				gpv.setColomnName("id");
				gpv.setHeadingName("id");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("level");
				gpv.setHeadingName("Level");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("otherMapped");
				gpv.setHeadingName("Other Mapped");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				gpv.setAlign("left");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("emp_name");
				gpv.setHeadingName("Employee");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				gpv.setAlign("left");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("mobile_no");
				gpv.setHeadingName("Mobile No");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				masterViewProp.add(gpv);

				gpv = new GridDataPropertyView();
				gpv.setColomnName("email_id");
				gpv.setHeadingName("E-Mail");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("false");
				gpv.setAlign("left");
				masterViewProp.add(gpv);

				returnResult = SUCCESS;

			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeGetUnMappedEmp of class " + getClass(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getUnMappedEmp()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String dept_subDeptId = getDept_SubdeptId(userName, deptLevel, moduleName);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				List dataCount =null;
				if (dept_subDeptId != null && !dept_subDeptId.equalsIgnoreCase(" "))
				{
					query1.append("SELECT  COUNT(eb.id) FROM manage_contact AS cc");
					query1.append(" INNER JOIN primary_contact AS eb ON eb.id=cc.emp_id WHERE cc.for_contact_sub_type_id=" + dept_subDeptId);
					query1.append(" AND cc.module_name='" + moduleName + "' AND level<" + mappedLevel + " AND cc.id");
					query1.append(" NOT IN(SELECT contact_id FROM contact_mapping_detail WHERE mapped_with=" + empName + ") ORDER BY eb.emp_name");
					dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				}
				if (dataCount != null && dataCount.size()>0)
				{
					Object obdata1 = null;
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						obdata1 = (Object) it.next();
						count = (BigInteger) obdata1;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("SELECT  cc.id,eb.emp_name,eb.mobile_no,eb.email_id,cc.level FROM manage_contact AS cc");
					query.append(" INNER JOIN primary_contact AS eb ON eb.id=cc.emp_id WHERE cc.for_contact_sub_type_id=" + dept_subDeptId);
					query.append(" AND cc.module_name='" + moduleName + "' AND level<" + mappedLevel + " AND cc.id");
					query.append(" NOT IN(SELECT contact_id FROM contact_mapping_detail WHERE mapped_with=" + empName + ")");
					query.append(" ORDER BY eb.emp_name limit " + from + "," + to);
					// //System.out.println(query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							Map<String, Object> one = new LinkedHashMap<String, Object>();
							if (obdata[0] != null)
							{
								one.put("id", obdata[0].toString());
								StringBuilder query2 = new StringBuilder("");
								query2.append("SELECT eb.emp_name FROM primary_contact AS eb ");
								query2.append(" INNER JOIN contact_mapping_detail AS cmd ON cmd.mapped_with=eb.id");
								query2.append(" WHERE cmd.contact_id=" + obdata[0].toString());
								// //System.out.println("Query String dsvnsdvds "+query2.toString());
								List dataList = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
								StringBuilder empBuilder = new StringBuilder();
								if (dataList != null && dataList.size() > 0)
								{
									for (int i = 0; i < dataList.size(); i++)
									{
										empBuilder.append(dataList.get(i) + ", ");
									}
									if (dataList != null)
									{
										String complianceId = empBuilder.substring(0, (empBuilder.toString().length() - 2));
										one.put("otherMapped", complianceId);
									}
									else
									{
										one.put("otherMapped", "NA");
									}
								}
								else
								{
									one.put("otherMapped", "NA");
								}
							}
							else
							{
								one.put("id", "NA");
							}
							if (obdata[1] != null)
								one.put("emp_name", obdata[1].toString());
							else
								one.put("emp_name", "NA");

							if (obdata[2] != null)
								one.put("mobile_no", obdata[2].toString());
							else
								one.put("mobile_no", "NA");

							if (obdata[3] != null)
								one.put("email_id", obdata[3].toString());
							else
								one.put("email_id", "NA");

							if (obdata[4] != null)
								one.put("level", "Level " + obdata[4].toString());
							else
								one.put("level", "NA");

							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;

			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getUnMappedEmp of class " + getClass(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String mapUnMappedContact()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false, flag = false;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (contactId != null)
				{
					ob = new InsertDataTable();
					ob.setColName("mapped_with");
					ob.setDataName(empName);
					insertData.add(ob);

					String contactArr[] = contactId.split(",");
					for (int i = 0; i < contactArr.length; i++)
					{
						ob = new InsertDataTable();
						ob.setColName("contact_id");
						ob.setDataName(contactArr[i]);
						insertData.add(ob);
						status = cbt.insertIntoTable("contact_mapping_detail", insertData, connectionSpace);
						insertData.remove(insertData.size() - 1);
					}
				}
				if (status)
				{
					addActionMessage("Contact details added successfully!!!");
					returnResult = SUCCESS;
				}
				else if (flag)
				{
					addActionMessage("Contact details added successfully!!!");
					returnResult = SUCCESS;
				}
				else
				{
					addActionMessage("There is some error in data!");
					returnResult = ERROR;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method mapUnMappedContact of class " + getClass(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getContShareEmpDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("SELECT  COUNT(cc.id) FROM manage_contact AS cc INNER JOIN primary_contact AS eb ON eb.id=cc.emp_id INNER JOIN contact_sharing_detail AS csd ON csd.contact_id=cc.id where cc.work_status=0 AND csd.sharing_with=" + id + " ORDER BY eb.emp_name");
				// System.out.println("Query String "+query1.toString());
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					StringBuilder query = new StringBuilder("");
					query.append("SELECT  eb.id,eb.emp_name,cc.level,eb.mobile_no,eb.email_id,grp.contact_subtype_name  FROM manage_contact AS cc INNER JOIN primary_contact AS eb ON eb.id=cc.emp_id INNER JOIN contact_sub_type AS grp ON grp.id=eb.sub_type_id INNER JOIN contact_sharing_detail AS csd ON csd.contact_id=cc.id where cc.work_status=0 AND csd.sharing_with=" + id + " ORDER BY eb.emp_name");
					// System.out.println(" Query String  "+query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] object = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							object = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();

							if (object[0] != null)
								one.put("id", object[0].toString());
							else
								one.put("id", "NA");

							if (object[1] != null)
								one.put("shareWithEmpName", object[1].toString());
							else
								one.put("shareWithEmpName", "NA");

							if (object[2] != null)
								one.put("shareEmpLevel", "Level " + object[2].toString());
							else
								one.put("shareEmpLevel", "NA");

							if (object[3] != null)
								one.put("shareWithEmpMob", object[3].toString());
							else
								one.put("shareWithEmpMob", "NA");

							if (object[4] != null)
								one.put("shareWithEmpEmail", object[4].toString());
							else
								one.put("shareWithEmpEmail", "NA");

						/*	if (object[5] != null)
								one.put("shareWithDesignation", object[5].toString());
							else
								one.put("shareWithDesignation", "NA");*/

							if (object[5] != null)
								one.put("shareGroupName", object[5].toString());
							else
								one.put("shareGroupName", "NA");

							Listhb.add(one);
						}
						setMasterViewList1(Listhb);
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getContactLevel of class " + getClass(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getDeptBySubGroup()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				deptMap = new JSONArray();
				JSONObject ob;
				StringBuilder query = new StringBuilder();
				query.append("SELECT dept.id,dept.contact_subtype_name FROM contact_sub_type AS dept ");
				query.append(" INNER JOIN primary_contact AS emp ON emp.sub_type_id=dept.id ");
				query.append(" WHERE dept.contact_type_id=" + selectedId + " GROUP BY dept.contact_subtype_name ");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob = new JSONObject();
						Object[] object = (Object[]) iterator.next();
						ob.put("id", object[0].toString());
						ob.put("name", object[1].toString());
						deptMap.add(ob);
					}
				}

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

	@SuppressWarnings("unchecked")
	public String fetchLevelValue()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				levelValue = new JSONArray();
				JSONObject ob;
				StringBuilder query = new StringBuilder();
				query.append("SELECT level  FROM compliance_contacts AS ctm ");
				query.append(" INNER JOIN department AS dept ON ctm.forDept_id=dept.id ");
				query.append(" WHERE dept.id= ");
				query.append(selectedId);
				query.append(" GROUP BY level");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob = new JSONObject();
						Object object = (Object) iterator.next();
						ob.put("id", object.toString());
						ob.put("name", object.toString());
						levelValue.add(ob);
					}
				}
				StringBuilder xyz = new StringBuilder();
				xyz.append("WHERE dept.id= ");
				xyz.append(selectedId);
				xyz.append(" GROUP BY level");

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

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
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

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public JSONArray getDeptMap()
	{
		return deptMap;
	}

	public void setDeptMap(JSONArray deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<Integer, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<Integer, String> empMap)
	{
		this.empMap = empMap;
	}

	public Map<Integer, String> getEmployeeList()
	{
		return employeeList;
	}

	public void setEmployeeList(Map<Integer, String> employeeList)
	{
		this.employeeList = employeeList;
	}

	public String getFromDept()
	{
		return fromDept;
	}

	public void setFromDept(String fromDept)
	{
		this.fromDept = fromDept;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public String getUserDeptId()
	{
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId)
	{
		this.userDeptId = userDeptId;
	}

	public String getUserDeptName()
	{
		return userDeptName;
	}

	public void setUserDeptName(String userDeptName)
	{
		this.userDeptName = userDeptName;
	}

	public String getForDeptId()
	{
		return forDeptId;
	}

	public void setForDeptId(String forDeptId)
	{
		this.forDeptId = forDeptId;
	}

	public String getForDept()
	{
		return forDept;
	}

	public void setForDept(String forDept)
	{
		this.forDept = forDept;
	}

	public Map<String, String> getLevel()
	{
		return level;
	}

	public void setLevel(Map<String, String> level)
	{
		this.level = level;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getMappedLevel()
	{
		return mappedLevel;
	}

	public void setMappedLevel(String mappedLevel)
	{
		this.mappedLevel = mappedLevel;
	}

	public JSONArray getJsonObjArray()
	{
		return jsonObjArray;
	}

	public void setJsonObjArray(JSONArray jsonObjArray)
	{
		this.jsonObjArray = jsonObjArray;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getLevelName()
	{
		return levelName;
	}

	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}

	public List<Object> getMasterViewList1()
	{
		return masterViewList1;
	}

	public void setMasterViewList1(List<Object> masterViewList1)
	{
		this.masterViewList1 = masterViewList1;
	}

	public List<ConfigurationUtilBean> getComplDDBox()
	{
		return complDDBox;
	}

	public void setComplDDBox(List<ConfigurationUtilBean> complDDBox)
	{
		this.complDDBox = complDDBox;
	}

	public List<ConfigurationUtilBean> getComplTxtBox()
	{
		return complTxtBox;
	}

	public void setComplTxtBox(List<ConfigurationUtilBean> complTxtBox)
	{
		this.complTxtBox = complTxtBox;
	}

	public Map<Integer, String> getSubDeptMap()
	{
		return subDeptMap;
	}

	public void setSubDeptMap(Map<Integer, String> subDeptMap)
	{
		this.subDeptMap = subDeptMap;
	}

	public String getGroupField()
	{
		return groupField;
	}

	public void setGroupField(String groupField)
	{
		this.groupField = groupField;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public Map<String, String> getGroupMap()
	{
		return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap)
	{
		this.groupMap = groupMap;
	}

	public Map<String, String> getSubTypeMap()
	{
		return subTypeMap;
	}

	public void setSubTypeMap(Map<String, String> subTypeMap)
	{
		this.subTypeMap = subTypeMap;
	}

	public String getSubType()
	{
		return subType;
	}

	public void setSubType(String subType)
	{
		this.subType = subType;
	}

	public Map<String, String> getLevelMap()
	{
		return levelMap;
	}

	public void setLevelMap(Map<String, String> levelMap)
	{
		this.levelMap = levelMap;
	}

	public String getLeveldata()
	{
		return leveldata;
	}

	public void setLeveldata(String leveldata)
	{
		this.leveldata = leveldata;
	}

	public void setLevelValue(JSONArray levelValue)
	{
		this.levelValue = levelValue;
	}

	public JSONArray getLevelValue()
	{
		return levelValue;
	}

	public String getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(String totalCount)
	{
		this.totalCount = totalCount;
	}

	public Map<String, String> getModuleList()
	{
		return moduleList;
	}

	public void setModuleList(Map<String, String> moduleList)
	{
		this.moduleList = moduleList;
	}

	public String getForModule()
	{
		return forModule;
	}

	public void setForModule(String forModule)
	{
		this.forModule = forModule;
	}

	public boolean isHodStatus()
	{
		return hodStatus;
	}

	public void setHodStatus(boolean hodStatus)
	{
		this.hodStatus = hodStatus;
	}

	public boolean isMgtStatus()
	{
		return mgtStatus;
	}

	public void setMgtStatus(boolean mgtStatus)
	{
		this.mgtStatus = mgtStatus;
	}

}