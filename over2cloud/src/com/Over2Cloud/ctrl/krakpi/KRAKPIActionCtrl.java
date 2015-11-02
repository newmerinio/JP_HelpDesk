package com.Over2Cloud.ctrl.krakpi;

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

import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class KRAKPIActionCtrl extends ActionSupport implements ServletRequestAware
{

	static final Log										log							= LogFactory.getLog(KRAKPIActionCtrl.class);
	Map																	session					= ActionContext.getContext().getSession();
	String															userName				= (String) session.get("uName");
	String															accountID				= (String) session.get("accountid");
	SessionFactory											connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest					request;
	private String											deptHierarchy;
	private String											deptname;
	private String											subdept;
	private String											mainHeaderName;
	private List<GridDataPropertyView>	masterViewProp	= new ArrayList<GridDataPropertyView>();
	private Integer											rows						= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer											page						= 0;
	// sorting order - asc or desc
	private String											sord						= "";
	// get index row - i.e. user click to sort.
	private String											sidx						= "";
	// Search Field
	private String											searchField			= "";
	// The Search String
	private String											searchString		= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String											searchOper			= "";
	// Your Total Pages
	private Integer											total						= 0;
	// All Record
	private Integer											records					= 0;
	private boolean											loadonce				= false;
	// Grid colomn view
	private String											oper;
	private String											id;
	private List<Object>								masterViewList;
	private Map<String, String>					empDataList			= null;
	private List<List<String>>					empKRAKPIList		= null;
	private String											empID;
	private String											pid;
	private String											kpiHeaderName		= null;
	private String											moduleName			= null;
	private Map<Integer, String>				officeMap				= null;
	private String											regLevel;
	private JSONArray										deptJSONArray;
	//private Map<String, String>					kraKPIParam;
	private List<ConfigurationUtilBean>	kraKPIParam			= null;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " Problem in Over2Cloud  method execute of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String createKRAKPI()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			boolean status = false;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_kra_kpi_configuration", accountID, connectionSpace, "", 0, "table_name",
					"kra_kpi_configuration");
			boolean userTrue = false;
			boolean dateTrue = false;
			boolean timeTrue = false;
			if (org2 != null)
			{
				// create table query based on configuration
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname("deptHierarchy");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname("mappeddeptid");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				cbt.createTable22("krakpicollection", Tablecolumesaaa, connectionSpace);
			}

			// getting the parameters and setting their value using loop
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			InsertDataTable ob = null;
			String kraName = null;
			List paramList = new ArrayList<String>();
			int paramValueSize = 0;
			boolean statusTemp = true;
			String[] tempParamValueSize = null;
			String mappeddeptid = null;
			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue[] = request.getParameterValues(Parmname);
				if (paramValue != null && Parmname != null && !Parmname.equalsIgnoreCase("regLevel"))
				{
					// Anoop, updated 06-06-2014
					if (Parmname.equalsIgnoreCase("kra"))
					{
						kraName = request.getParameter(Parmname);
					}
					else if (Parmname.equalsIgnoreCase("kpi"))
					{
						tempParamValueSize = request.getParameterValues(Parmname);
					}
					else if (Parmname.equalsIgnoreCase("mappeddeptid"))
					{
						mappeddeptid = request.getParameter(Parmname);
					}
				}
			}

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			if (tempParamValueSize != null && tempParamValueSize.length > 0)
			{
				ob = new InsertDataTable();
				ob.setColName("kra");
				ob.setDataName(kraName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("mappeddeptid");
				ob.setDataName(mappeddeptid);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("kpi");
				// put dynamic value, in loop below
				insertData.add(ob);

				for (String val : tempParamValueSize)
				{
					if (!val.trim().equals("") && val != null)
					{
						ob.setDataName(val);
						status = cbt.insertIntoTable("krakpicollection", insertData, connectionSpace);
					}
				}
			}

			if (status)
			{
				addActionMessage("KRA KPI Registered Successfully.");
				return SUCCESS;
			}
			else
			{
				addActionMessage("ERROR: Data did not save successfully !");
				return SUCCESS;
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime()
					+ " Problem in Over2Cloud  method createKRAKPI of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Ooops There Is Some Problem In Department Creation!");
			return ERROR;
		}
	}

	public String beforeKRAKPIView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			setMainHeaderName(getModuleName());
			setViewPageProp();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method beforeKRAKPIView of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setViewPageProp()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		// for name of dept or sub dept mapped with KRA or KPI
		gpv = new GridDataPropertyView();
		gpv.setColomnName("mappeddeptid");
		gpv.setHeadingName("Department");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		masterViewProp.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_kra_kpi_configuration", accountID, connectionSpace, "", 0,
				"table_name", "kra_kpi_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setAlign(gdp.getAlign());
			masterViewProp.add(gpv);
		}

	}

	public String viewKRAKPI()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from krakpicollection");
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
				int from = to - getRows();
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				List fieldNames = Configuration.getColomnList("mapped_kra_kpi_configuration", accountID, connectionSpace, "kra_kpi_configuration");
				// fieldNames.add("deptHierarchy");
				fieldNames.add("mappeddeptid");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1)
						{
							if (obdata.toString().equalsIgnoreCase("mappeddeptid"))
							{
								query.append("dept.deptName, ");
								queryEnd.append(" inner join department as dept on dept.id = kpi.mappeddeptid, ");
							}
							else
							{
								query.append("kpi." + obdata.toString() + ", ");
							}
						}
						else
						{
							if (obdata.toString().equalsIgnoreCase("mappeddeptid"))
							{
								query.append("dept.deptName ");
								queryEnd.append(" inner join department as dept on dept.id = kpi.mappeddeptid ");
							}
							else
							{
								query.append("kpi." + obdata.toString() + " ");
							}
						}
					}
					i++;

				}

				query.append(" from krakpicollection as kpi ");
				query.append(queryEnd);
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" where ");
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append("kpi." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append("kpi." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append("kpi." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append("kpi." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append("kpi." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase("") && getSidx() != null && !getSidx().equals(""))
				{
					query.append(" order by kpi." + getSidx() + " " + getSord());
				}
				else
				{
					query.append(" order by kpi.kra ASC");
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				// System.out.println("Query: " + query);
				cbt.checkTableColmnAndAltertable(fieldNames, "krakpicollection", connectionSpace);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					StringBuilder tempQ = new StringBuilder();

					for (Iterator it = data.iterator(); it.hasNext();)
					{
						String level = null;
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (fieldNames.get(k).toString().equalsIgnoreCase("userName"))
								{
									one.put(fieldNames.get(k).toString(), WordUtils.capitalize(obdata[k].toString()));
								}
								else if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
								{
									one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								else if (fieldNames.get(k).toString().equalsIgnoreCase("time"))
								{
									one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
								}
								else one.put(fieldNames.get(k).toString(), obdata[k].toString());
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
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method viewKRAKPI of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewKRAKPIOperation()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				wherClause.put("userName", userName);
				wherClause.put("date", DateUtil.getCurrentDateUSFormat());
				wherClause.put("time", DateUtil.getCurrentTime());

				condtnBlock.put("id", getId());
				cbt.updateTableColomn("krakpicollection", wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("krakpicollection", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method viewKRAKPIOperation of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforekra_kpiMapping()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			empDataList = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
			setMainHeaderName(getModuleName());
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method beforekra_kpiMapping of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String mappedKRAKPIWithSubDept()
	{
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			empKRAKPIList = new KraKpiHelper().fetchKraKpiOfEmpDept(coi, empID);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method mappedKRAKPIWithSubDept of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String mapKRAKPIWithEmp()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			cbt.deleteAllRecordForId("krakpimap", "empID", empID, connectionSpace);

			boolean status = false;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("empID");
			ob.setDataName(getEmpID());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(userName);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("KPIId");
			insertData.add(ob);

			int count = 0;
			if (pid != null && !pid.equals(""))
			{
				String[] ids = pid.split(",");
				for (String s : ids)
				{
					if (s != null && !s.trim().equals(""))
					{
						ob.setDataName(s.trim());
						// System.out.println("s:" + s);
						status = cbt.insertIntoTable("krakpimap", insertData, connectionSpace);
						if (status) count++;
					}
				}
			}
			addActionMessage(count + " KPIs Mapped successfully.");
			return SUCCESS;
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method mapKRAKPIWithEmp of class " + getClass(), e);
			e.printStackTrace();
			addActionError("Ooops There Is Some Problem In Department Creation!");
			return ERROR;
		}
	}

	public String beforeKpiMapping()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{

			returnString = SUCCESS;
		}
		return returnString;
	}

	public String viewKraKpiMappingAction()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder();
				query1
						.append("select km.id, emp.empName, kc.kra, kc.kpi from krakpimap as km inner join employee_basic as emp on km.empID = emp.id inner join krakpicollection as kc on kc.id = km.KPIId");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataCount != null && dataCount.size() > 0)
				{
					CommonHelper ch = new CommonHelper();

					List<Object> Listhb = new ArrayList<Object>();
					for (Object obj : dataCount)
					{
						Map<String, Object> one = new HashMap<String, Object>();
						Object[] data = (Object[]) obj;
						one.put("id", ch.getFieldValue(data[0]));
						one.put("empName", ch.getFieldValue(data[1]));
						one.put("kra", ch.getFieldValue(data[2]));
						one.put("kpi", ch.getFieldValue(data[3]));

						Listhb.add(one);
					}
					// list to be shown in grid
					masterViewList = Listhb;
					// System.out.println("masterViewList.size():" +
					// masterViewList.size());
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
						+ "Problem in Over2Cloud  method viewKRAKPI of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return SUCCESS;

	}

	public String editKraKpiMappingAction()
	{
		String retVal = LOGIN;
		if (ValidateSession.checkSession())
		{
			// System.out.println("id:" + id);
			CommonOperInterface coi = new CommonConFactory().createInterface();
			boolean flag = coi.deleteAllRecordForId("krakpimap", "id", id, connectionSpace);

			retVal = SUCCESS;
		}
		return retVal;
	}

	@SuppressWarnings("unchecked")
	public String beforeKRAKPICreate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			// Fetch organization level names
			officeMap = new HashMap<Integer, String>();
			List data = new createTable().executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);

			if (data != null && data.size() > 0 && data.get(0) != null)
			{
				String arr[] = data.get(0).toString().split("#");
				for (int i = 0; i < arr.length; i++)
				{
					officeMap.put(i + 1, arr[i].toString());
				}
			}

			// KRA-KPI
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_kra_kpi_configuration", accountID, connectionSpace, "", 0,
					"table_name", "kra_kpi_configuration");
			kraKPIParam = new ArrayList<ConfigurationUtilBean>();
			if (offeringLevel1 != null)
			{
				

				kraKPIParam = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : offeringLevel1)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
							kraKPIParam.add(obj);
					}
						
				}
			
				
				
				
				
				
				
				
				
				
				
				
				
				/*for (GridDataPropertyView gdp : offeringLevel1)
				{
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						kraKPIParam.put(gdp.getColomnName(), gdp.getHeadingName());s
					}
				}*/
				
				
				
				
				
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method beforeKraKpi of class " + getClass(), e);
			addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String fetchDepartment()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, deptName from department where orgnztnlevel = '");
			query.append(regLevel);
			query.append("' order by deptName ");
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			Map<String, String> deptMap = CommonHelper.convertListToMap(list,false);
			deptJSONArray = new JSONArray();
			JSONObject jsonObject = null;
			for (Map.Entry<String, String> entry : deptMap.entrySet())
			{
				jsonObject = new JSONObject();
				jsonObject.put("ID", CommonHelper.getFieldValue(entry.getKey()));
				jsonObject.put("NAME", CommonHelper.getFieldValue(entry.getValue()));

				deptJSONArray.add(jsonObject);
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method beforeKraKpi of class " + getClass(), e);
			addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getEmpID()
	{
		return empID;
	}

	public void setEmpID(String empID)
	{
		this.empID = empID;
	}

	public String getDeptHierarchy()
	{
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy)
	{
		this.deptHierarchy = deptHierarchy;
	}

	public String getDeptname()
	{
		return deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getSubdept()
	{
		return subdept;
	}

	public void setSubdept(String subdept)
	{
		this.subdept = subdept;
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

	public Map<String, String> getEmpDataList()
	{
		return empDataList;
	}

	public void setEmpDataList(Map<String, String> empDataList)
	{
		this.empDataList = empDataList;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getKpiHeaderName()
	{
		return kpiHeaderName;
	}

	public void setKpiHeaderName(String kpiHeaderName)
	{
		this.kpiHeaderName = kpiHeaderName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public void setEmpKRAKPIList(List<List<String>> empKRAKPIList)
	{
		this.empKRAKPIList = empKRAKPIList;
	}

	public List<List<String>> getEmpKRAKPIList()
	{
		return empKRAKPIList;
	}

	public void setOfficeMap(Map<Integer, String> officeMap)
	{
		this.officeMap = officeMap;
	}

	public Map<Integer, String> getOfficeMap()
	{
		return officeMap;
	}

	public void setRegLevel(String regLevel)
	{
		this.regLevel = regLevel;
	}

	public String getRegLevel()
	{
		return regLevel;
	}

	public void setDeptJSONArray(JSONArray deptJSONArray)
	{
		this.deptJSONArray = deptJSONArray;
	}

	public JSONArray getDeptJSONArray()
	{
		return deptJSONArray;
	}

	public List<ConfigurationUtilBean> getKraKPIParam() {
		return kraKPIParam;
	}

	public void setKraKPIParam(List<ConfigurationUtilBean> kraKPIParam) {
		this.kraKPIParam = kraKPIParam;
	}

	

}