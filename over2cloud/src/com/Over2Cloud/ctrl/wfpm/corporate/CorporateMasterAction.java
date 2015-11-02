package com.Over2Cloud.ctrl.wfpm.corporate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CorporateMasterAction extends ActionSupport implements ServletRequestAware
{

	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommonOperInterface coi = new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private Map<String, String> corpmap;
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	private JSONArray dataArray = new JSONArray();
	static final Log log = LogFactory.getLog(CorporateMasterAction.class);
	private Map corpTypeMap = null;
	// Get the requested page. By default grid sets this to 1.
	private Integer rows = 0;
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

	private String id, from_date, to_date, searchParameter, data_status, packages, corp_type, action_status;
	private Map<String, String> stateMap, countryMap;

	// setting Map for state
	void setStateMapData()
	{
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query2 = "select distinct id,stateName from state_data ";
		List data = cbt.executeAllSelectQuery(query2, connectionSpace);
		stateMap = new LinkedHashMap<String, String>();
		if (data != null && data.size() > 0)
		{
			for (Iterator it = data.iterator(); it.hasNext();)
			{
				Object[] obdata = (Object[]) it.next();
				stateMap.put(checkNull(obdata[0]), checkNull(obdata[1]));
			}
		}

		corpTypeMap = new TreeMap<String, String>();
		List list = cbt.executeAllSelectQuery("select id,rel_subtype from relationship_sub_type where rel_type='2' group by rel_subtype", connectionSpace);
		if (list != null && !list.isEmpty())
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null && object[1] != null)
				{
					corpTypeMap.put(object[0].toString(), object[1].toString());
				}
			}
		}
		list.clear();
		countryMap = new TreeMap<String, String>();
		list = cbt.executeAllSelectQuery("select id,country_name from country group by country_name", connectionSpace);
		if (list != null && !list.isEmpty())
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null && object[1] != null)
				{
					countryMap.put(object[0].toString(), object[1].toString());
				}
			}
		}
	}

	public String checkNull(Object ob)
	{
		if (ob != null)
			return ob.toString();
		return "NA";
	}

	@SuppressWarnings("unchecked")
	public String viewCorporateHeader()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println(data_status);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				corpTypeMap = new TreeMap<String, String>();
				List list = cbt.executeAllSelectQuery("select id,rel_subtype from relationship_sub_type where rel_type='2' group by rel_subtype", connectionSpace);
				if (list != null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							corpTypeMap.put(object[0].toString(), object[1].toString());
						}
					}
				}

				/*
				 * String userName = (String) session.get("uName"); if (userName
				 * == null || userName.equalsIgnoreCase(""))
				 */
				from_date = DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -7);
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

	public String beforeAddCorporate()
	{
		System.out.println("beforeAddCorporate starts");
		setAddPageDataFields();
		setStateMapData();
		return SUCCESS;
	}

	List<ConfigurationUtilBean> corporateTextBox;
	List<ConfigurationUtilBean> corporateDropDown;

	public void setAddPageDataFields()
	{
		try
		{

			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_corporate_master_configuration", accountID, connectionSpace, "", 0, "table_name", "corporate_master_configuration");
			corporateTextBox = new ArrayList<ConfigurationUtilBean>();
			corporateDropDown = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("data_status") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("action_by") && !gdp.getColomnName().equalsIgnoreCase("action_on") && !gdp.getColomnName().equalsIgnoreCase("comments"))
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
					corporateTextBox.add(obj);
				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("data_status") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
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
					corporateDropDown.add(obj);
				}
			}
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String addCorporateDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_corporate_master_configuration", accountID, connectionSpace, "", 0, "table_name", "corporate_master_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}

					status = cbt.createTable22("corporate_master", Tablecolumesaaa, connectionSpace);
					System.out.println("Status >>>>>>>>>>>>  " + status);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();

						String paramValue = request.getParameter(Parmname);
						System.out.println(Parmname + "    " + paramValue);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
					ob = new InsertDataTable();
					ob.setColName("user_name");
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
					ob.setColName("data_status");
					ob.setDataName("active");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Unapproved");
					insertData.add(ob);

					status = cbt.insertIntoTable("corporate_master", insertData, connectionSpace);
					insertData.clear();

					if (status)
					{
						StringBuilder qry = new StringBuilder("");
						qry.append("update temp_counter_notification set count=count+1 WHERE module_name='CORP' AND id!=0 ");
						cbt.executeAllUpdateQuery(qry.toString(), connectionSpace);
						addActionMessage("Corporate Added successfully!!!");
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

	public String viewCorporateDetails()
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
				setMasterViewProps();
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

	void setMasterViewProps()
	{

		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_corporate_master_configuration", accountID, connectionSpace, "", 0, "table_name", "corporate_master_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("address") && !gdp.getColomnName().equalsIgnoreCase("state") && !gdp.getColomnName().equalsIgnoreCase("city") && !gdp.getColomnName().equalsIgnoreCase("email") && !gdp.getColomnName().equalsIgnoreCase("mob_no") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("action_by") && !gdp.getColomnName().equalsIgnoreCase("comments"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						if (gdp.getColomnName().equalsIgnoreCase("action_on"))
						{
							gpv.setHideOrShow("true");
						}
						else
						{
							gpv.setHideOrShow(gdp.getHideOrShow());
						}

						if (gdp.getColomnName().equalsIgnoreCase("corp_name"))
						{
							gpv.setFormatter("getCorp");
						}
						else if (gdp.getColomnName().equalsIgnoreCase("status"))
						{
							gpv.setFormatter("getStatus");
						}
						else
						{
							gpv.setFormatter(gdp.getFormatter());
						}
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewCorporateData()
	{

		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from corporate_master");
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
				if (to > getRecords())
					to = getRecords();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from corporate_master as mc ");
				queryEnd.append(" inner join country as con on con.id=mc.country inner join state as st on st.id=mc.state " + " inner join city as ct on ct.id=mc.city " + "inner join relationship_sub_type as rst on mc.corp_type=rst.id");

				List fieldNames = Configuration.getColomnList("mapped_corporate_master_configuration", accountID, connectionSpace, "corporate_master_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("country"))
							queryTemp.append("con.country_name ,");
						else if (obdata.toString().equalsIgnoreCase("state"))
							queryTemp.append("st.state_name ,");
						else if (obdata.toString().equalsIgnoreCase("city"))
							queryTemp.append("ct.city_name ,");
						else if (obdata.toString().equalsIgnoreCase("corp_type"))
							queryTemp.append("rst.rel_subtype ,");
						else
							queryTemp.append("mc." + obdata.toString() + ",");
					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(queryEnd.toString());
				query.append(" where ");

				if (this.getData_status() != null && !this.getData_status().equals("-1") && !this.getData_status().equalsIgnoreCase("undefined"))
				{
					if (this.getData_status().equalsIgnoreCase("All Status"))
					{
						query.append(" mc.data_status IS Not Null ");
					}
					else
					{
						query.append(" mc.data_status='" + this.getData_status() + "'");
					}
				}

				if (this.getAction_status() != null && !this.getAction_status().equals("-1") && !this.getAction_status().equalsIgnoreCase("undefined"))
				{
					if (this.getAction_status().equalsIgnoreCase("All Status"))
					{
						query.append(" status IS Not Null ");
					}
					else
					{
						query.append(" and mc.status='" + this.getAction_status() + "'");
					}
				}

				if (this.getPackages() != null && !this.getPackages().equals("-1") && !this.getPackages().equalsIgnoreCase("undefined"))
				{
					query.append(" and mc.packages='" + this.getPackages() + "'");
				}

				if (this.getCorp_type() != null && !this.getCorp_type().equals("-1") && !this.getCorp_type().equalsIgnoreCase("undefined"))
				{
					query.append(" and mc.corp_type='" + this.getCorp_type() + "'");
				}

				if (getFrom_date() != null && !getFrom_date().equals("") && getTo_date() != null && !getTo_date().equals(""))
				{
					setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
					setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
					query.append("and mc.date >= '" + DateUtil.convertDateToUSFormat(getFrom_date()) + "' and mc.date <= '" + DateUtil.convertDateToUSFormat(getTo_date()) + "'");
				}

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

				if (searchParameter != null && !searchParameter.equalsIgnoreCase(""))
				{
					query.append(" and ");
					if (fieldNames != null && !fieldNames.isEmpty())
					{
						int k = 0;
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
						{
							String object = iterator.next().toString();
							if (object != null)
							{
								if (object.toString().equalsIgnoreCase("state"))
								{
									query.append(" st.stateName LIKE " + "'%" + searchParameter + "%" + "'");
								}
								else if (object.toString().equalsIgnoreCase("city"))
								{
									query.append(" ct.name LIKE " + "'%" + searchParameter + "%" + "'");
								}
								else if (object.toString().equalsIgnoreCase("corp_type"))
								{
									query.append(" rst.rel_subtype LIKE " + "'%" + searchParameter + "%" + "'");
								}
								else
								{
									query.append(" mc." + object.toString() + " LIKE " + "'%" + searchParameter + "%" + "'");
								}
							}
							if (k < fieldNames.size() - 1)
							{
								/*
								 * if
								 * (!object.toString().equalsIgnoreCase("clientName"
								 * )) {
								 */
								query.append(" OR ");
								/* } */
							}
							else
								query.append(" ");
							k++;
						}
					}
				}

				query.append(" order by mc.corp_name ");
				System.out.println(query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				System.out.println("query.toString()>>" + query.toString());
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
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
									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
						}
						Listhb.add(one);
					}
					setViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					
					StringBuilder qry = new StringBuilder("");
					qry.append("update temp_counter_notification set count=0 WHERE module_name='CORP' AND id!=0 ");
					cbt.executeAllUpdateQuery(qry.toString(), connectionSpace);
					
					
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

	public String editCorporateDataGrid()
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
							if (parmName.equalsIgnoreCase("user_name"))
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
					boolean status = cbt.updateTableColomn("corporate_master", wherClause, condtnBlock, connectionSpace);
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
						wherClause.put("data_status", "inactive");
						condtnBlock.put("id", id);
						cbt.updateTableColomn("corporate_master", wherClause, condtnBlock, connectionSpace);
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

	public String corpBasicDetails()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			corpmap = new LinkedHashMap<String, String>();

			sb.append("select rst.rel_subtype,mc.address,con.country_name,st.state_name,ct.city_name,mc.poc_name,mc.mob_no,mc.email,mc.user_name,mc.date " + " from corporate_master as mc ");
			sb.append(" inner join country as con on con.id=mc.country ");
			sb.append(" inner join state as st on st.id=mc.state ");
			sb.append(" inner join city as ct on ct.id=mc.city ");
			sb.append(" inner join relationship_sub_type as rst on mc.corp_type=rst.id ");
			sb.append(" where mc.id= '" + id + "'");

			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				corpmap.put("Corporate Type", getValueWithNullCheck(data[0]));
				corpmap.put("Address", getValueWithNullCheck(data[1]));
				corpmap.put("Country", getValueWithNullCheck(data[2]));
				corpmap.put("State", getValueWithNullCheck(data[3]));
				corpmap.put("City", getValueWithNullCheck(data[4]));
				corpmap.put("POC Name", getValueWithNullCheck(data[5]));
				corpmap.put("Mobile No", getValueWithNullCheck(data[6]));
				corpmap.put("Email Id", getValueWithNullCheck(data[7]));
				corpmap.put("Created By", getValueWithNullCheck(data[8]));
				corpmap.put("Created On", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[9].toString())));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	private String getValueWithNullCheck(Object obj)
	{
		return (obj != null && !obj.toString().equalsIgnoreCase("")) ? obj.toString() : "NA";
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
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

	public List<Object> getViewList()
	{
		return viewList;
	}

	public void setViewList(List<Object> viewList)
	{
		this.viewList = viewList;
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

	public List<ConfigurationUtilBean> getCorporateTextBox()
	{
		return corporateTextBox;
	}

	public void setCorporateTextBox(List<ConfigurationUtilBean> corporateTextBox)
	{
		this.corporateTextBox = corporateTextBox;
	}

	public List<ConfigurationUtilBean> getCorporateDropDown()
	{
		return corporateDropDown;
	}

	public void setCorporateDropDown(List<ConfigurationUtilBean> corporateDropDown)
	{
		this.corporateDropDown = corporateDropDown;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Map<String, String> getStateMap()
	{
		return stateMap;
	}

	public void setStateMap(Map<String, String> stateMap)
	{
		this.stateMap = stateMap;
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

	public String getData_status()
	{
		return data_status;
	}

	public void setData_status(String data_status)
	{
		this.data_status = data_status;
	}

	public String getPackages()
	{
		return packages;
	}

	public void setPackages(String packages)
	{
		this.packages = packages;
	}

	public String getCorp_type()
	{
		return corp_type;
	}

	public void setCorp_type(String corp_type)
	{
		this.corp_type = corp_type;
	}

	public void setSearchParameter(String searchParameter)
	{
		this.searchParameter = searchParameter;
	}

	public String getSearchParameter()
	{
		return searchParameter;
	}

	public void setCorpTypeMap(Map corpTypeMap)
	{
		this.corpTypeMap = corpTypeMap;
	}

	public Map getCorpTypeMap()
	{
		return corpTypeMap;
	}

	public void setCorpmap(Map<String, String> corpmap)
	{
		this.corpmap = corpmap;
	}

	public Map<String, String> getCorpmap()
	{
		return corpmap;
	}

	public void setAction_status(String action_status)
	{
		this.action_status = action_status;
	}

	public String getAction_status()
	{
		return action_status;
	}

	public void setCountryMap(Map<String, String> countryMap)
	{
		this.countryMap = countryMap;
	}

	public Map<String, String> getCountryMap()
	{
		return countryMap;
	}

}