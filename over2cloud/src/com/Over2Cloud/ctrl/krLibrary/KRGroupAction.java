package com.Over2Cloud.ctrl.krLibrary;

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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;

public class KRGroupAction extends GridPropertyBean implements ServletRequestAware
{
	private static final long serialVersionUID = 7404224590016628909L;
	int groupLevel;
	String groupLevelName = null;
	String subGroupLevelName = null;
	private List<ConfigurationUtilBean> KRGroupTextBox = null;
	private List<ConfigurationUtilBean> KRGroupDropDown = null;
	private List<ConfigurationUtilBean> KRGroupTextBox1 = null;
	private List<ConfigurationUtilBean> KRGroupDropDown1 = null;
	private List<GridDataPropertyView> masterViewPropForSubGroup = new ArrayList<GridDataPropertyView>();
	private List<Object> masterViewListForSubGroup = null;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private HttpServletRequest request;
	private List<Object> masterViewList;
	private String groupId;
	Map<String, String> abbreName;
	private Map<String, String> deptName = null;
	private Map<String, String> countMap = null;
	private String searchingData;
	private String searchStr;
	private String searchFields;
	private String fieldName;
	private String fieldvalue;

	@SuppressWarnings("rawtypes")
	public String beforeGroupViewHeader()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				countMap = new LinkedHashMap<String, String>();
				deptName = new LinkedHashMap<String, String>();
				KRActionHelper KRH = new KRActionHelper();
				List data = null;
				String dept[] = KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
				if (dept != null && !dept[4].equalsIgnoreCase(""))
				{
					data = KRH.getGroupAssignedDepartment(connectionSpace, dept[4]);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								deptName.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				KRActionHelper KH = new KRActionHelper();
				String countValue = KH.fetchCountValue("kr_group_data", connectionSpace);
				if (countValue != null)
				{
					countMap.put("Group", countValue);
				}
				countValue = KH.fetchCountValue("kr_sub_group_data", connectionSpace);
				if (countValue != null)
				{
					countMap.put("Sub Group", countValue);
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

	@SuppressWarnings("rawtypes")
	public String brforeKRGroupView()
	{
		System.out.println(getFieldName() + "and " + getFieldvalue());
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				createGridViewForGroup(); // For KR Group
				createGridViewForSubGroup(); // For KR Sub Group

				CommonOperInterface coi = new CommonConFactory().createInterface();
				StringBuilder empuery = new StringBuilder("");
				empuery.append("select orgLevel, levelName from mapped_kr_group_level");
				List groupLevelList = coi.executeAllSelectQuery(empuery.toString(), connectionSpace);

				if (groupLevelList != null && groupLevelList.size() > 0)
				{
					Object[] data = (Object[]) groupLevelList.get(0);
					groupLevel = Integer.parseInt(data[0].toString());
					groupLevelName = groupLevel > 0 ? data[1].toString().split("#")[0] : "";
					subGroupLevelName = groupLevel > 0 ? data[1].toString().split("#")[1] : "";
				}

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	@SuppressWarnings("rawtypes")
	public String brforeKRGroupAdd()
	{
		String returnType = ERROR;
		if (ValidateSession.checkSession())
		{
			KRGroupTextBox = new ArrayList<ConfigurationUtilBean>();
			KRGroupDropDown = new ArrayList<ConfigurationUtilBean>();
			KRGroupTextBox1 = new ArrayList<ConfigurationUtilBean>();
			KRGroupDropDown1 = new ArrayList<ConfigurationUtilBean>();

			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select orgLevel, levelName from mapped_kr_group_level");
			List groupLevelList = coi.executeAllSelectQuery(empuery.toString(), connectionSpace);
			ConfigurationUtilBean obj = null;
			if (groupLevelList != null && groupLevelList.size() > 0)
			{
				Object[] data = (Object[]) groupLevelList.get(0);
				groupLevel = Integer.parseInt(data[0].toString());
				groupLevelName = groupLevel > 0 ? data[1].toString().split("#")[0] : "";
				subGroupLevelName = groupLevel > 0 ? data[1].toString().split("#")[1] : "";

				if (groupLevel > 0)
				{
					List<GridDataPropertyView> control = Configuration.getConfigurationData("mapped_kr_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_group_configuration");

					deptName = new LinkedHashMap<String, String>();
					KRActionHelper KRH = new KRActionHelper();
					List data1 = null;
					String dept[] = KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
					if (dept != null && !dept[4].equalsIgnoreCase(""))
					{
						data1 = KRH.getAssignedDepartment(connectionSpace, dept[4]);
						if (data1 != null && data1.size() > 0)
						{
							for (Iterator iterator = data1.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									deptName.put(object[0].toString(), object[1].toString());
								}
							}
						}
					}

					for (GridDataPropertyView gdp : control)
					{
						obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
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
							obj.setField_length(gdp.getLength());
							KRGroupTextBox.add(obj);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D"))
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
							obj.setField_length(gdp.getLength());
							KRGroupDropDown.add(obj);
						}
					}
				}
			}
			if (groupLevel > 1) // for KR sub group only
			{
				List<GridDataPropertyView> control = Configuration.getConfigurationData("mapped_kr_sub_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_sub_group_configuration");
				for (GridDataPropertyView gdp : control)
				{
					obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
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
						obj.setField_length(gdp.getLength());
						KRGroupTextBox1.add(obj);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("D"))
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
						obj.setField_length(gdp.getLength());
						KRGroupDropDown1.add(obj);
					}
				}
			}
			returnType = SUCCESS;
		}
		else
		{
			returnType = LOGIN;
		}
		return returnType;
	}

	private void createGridViewForGroup() throws Exception
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_kr_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_group_configuration");

		returnResult.add(0, gpv);
		masterViewProp = returnResult;
	}

	private void createGridViewForSubGroup() throws Exception
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewPropForSubGroup.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_kr_sub_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_sub_group_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			if (!gdp.getColomnName().equalsIgnoreCase("group_name"))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setWidth(gdp.getWidth());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				masterViewPropForSubGroup.add(gpv);
			}
		}
	}

	/*
	 * Rahul 11-9-2013 Get data for Sub Group
	 */
	@SuppressWarnings("rawtypes")
	public String viewSubGroupGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_sub_group_data");
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					List fieldNames = Configuration.getColomnList("mapped_kr_sub_group_configuration", accountID, connectionSpace, "kr_sub_group_configuration");
					fieldNames.remove("group_name");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
							{
								query.append(obdata + ", ");
							}
							else
							{
								query.append(obdata);
							}
						}
						i++;
					}

					query.append(" from kr_sub_group_data where group_name = '" + id + "' AND status='Active' ");

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");
						if (getSearchOper().equalsIgnoreCase("cn"))
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
						else
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" limit " + from + "," + to);

					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

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
									else if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("kr_starting_id"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString() + "" + obdata[k + 1].toString());
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("created_time"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
									}
								}
							}
							Listhb.add(one);
						}
						setMasterViewListForSubGroup(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	/*
	 * Anoop 31-8-2013, 9-9-2013 Add KR group
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addKrGroup()
	{
		String returnType = ERROR;
		if (ValidateSession.checkSession())
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_kr_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_group_configuration");

			if (statusColName != null)
			{
				// create table query based on configuration
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1 = null;
				for (GridDataPropertyView gdp : statusColName)
				{
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype(gdp.getData_type());
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
				}
				coi.createTable22("kr_group_data", Tablecolumesaaa, connectionSpace);

				// Add values to table 'kr_group_data'
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				String groupName = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
					{
						if (Parmname.equalsIgnoreCase("group_name"))
							groupName = paramValue;

						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
				}

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

				// Check for duplicate group
				List dataList = coi.executeAllSelectQuery("select id from kr_group_data where group_name = '" + groupName + "'", connectionSpace);
				if (dataList.size() > 0)
				{
					addActionMessage("Duplicate: Group already exists !");
					returnType = ERROR;
				}
				else
				{
					boolean status = coi.insertIntoTable("kr_group_data", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("KR Group added Successfully");
						returnType = SUCCESS;
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
						returnType = ERROR;
					}
				}
			}
		}
		else
		{
			returnType = LOGIN;
		}
		return returnType;
	}

	/*
	 * Rahul 31-8-2013 Add KR sub group
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String addKrSubGroup()
	{
		String returnType = ERROR;
		if (ValidateSession.checkSession())
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();

			// Read configurationn form table 'kr_sub_group_configuration'
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_kr_sub_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_sub_group_configuration");

			// Create table 'kr_sub_group_data'
			if (statusColName != null)
			{
				// create table query based on configuration
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1 = null;
				for (GridDataPropertyView gdp : statusColName)
				{
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype(gdp.getData_type());
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
				}
				coi.createTable22("kr_sub_group_data", Tablecolumesaaa, connectionSpace);

				// Add values to table 'kr_group_data'
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				String groupName = null, subGroupName = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
					{
						if (Parmname.equalsIgnoreCase("group_name"))
						{
							groupName = paramValue;
						}
						if (Parmname.equalsIgnoreCase("sub_group_name"))
							subGroupName = paramValue;

						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
				}
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
				// Check for duplicate group
				String query = "select id from kr_sub_group_data where group_name = '" + groupName + "' and  sub_group_name = '" + subGroupName + "' ";
				List dataList = coi.executeAllSelectQuery(query, connectionSpace);
				if (dataList.size() > 0)
				{
					addActionMessage("Duplicate: Group Or Sub Group already exists !");
					returnType = ERROR;
				}
				else
				{
					boolean status = coi.insertIntoTable("kr_sub_group_data", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("KR Sub Group added Successfully");
						returnType = SUCCESS;
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
						returnType = ERROR;
					}
				}
			}
		}
		else
		{
			returnType = LOGIN;
		}
		return returnType;
	}

	@SuppressWarnings({ "unchecked" })
	public String viewGroupGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_group_data");
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					List fieldNames = Configuration.getColomnList("mapped_kr_group_configuration", accountID, connectionSpace, "kr_group_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (obdata.toString().equalsIgnoreCase("sub_type_id"))
								{
									query.append("dept.contact_subtype_name , ");
								}
								else
								{
									query.append("kr." + obdata + ", ");
								}
							}
							else
							{
								if (obdata.toString().equalsIgnoreCase("sub_type_id"))
								{
									query.append("dept.contact_subtype_name ");
								}
								else
								{
									query.append("kr." + obdata);
								}
							}
						}
						i++;
					}
					// change by manab
					query.append(" FROM kr_group_data AS kr LEFT JOIN contact_sub_type AS dept ON kr.sub_type_id=dept.id WHERE ");
					if (getFieldName() != null && getFieldvalue() != null &&  !getFieldvalue().equalsIgnoreCase("-1"))
					{
						query.append("  kr." + fieldName + "='" + fieldvalue + "'");
					}
					if (searchingData != null && !searchingData.equalsIgnoreCase("") && !searchingData.equalsIgnoreCase("-1"))
					{
						query.append("  dept.contact_subtype_name LIKE '%" + searchingData + "%' OR kr.group_name LIKE '%" + searchingData + "%' OR kr.group_abbr LIKE '%" + searchingData + "%' OR kr.group_description LIKE '%" + searchingData + "%'");
					}
					else if (getSearchFields() != null && getSearchStr() != null && !getSearchFields().equalsIgnoreCase("") && !getSearchStr().equalsIgnoreCase("") && !getSearchStr().equalsIgnoreCase("-1"))
					{
						query.append(" " + getSearchFields() + " = '" + getSearchStr() + "'");
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("-1"))
					{
						// add search query based on the search operation
						query.append(" AND ");
						if (!getSearchField().equalsIgnoreCase("kr_sub_group_data.id"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						}
						if (getSearchOper().equalsIgnoreCase("cn"))
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

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" limit " + from + "," + to);

					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
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
									else if (fieldNames.get(k).toString().equalsIgnoreCase("created_date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("created_time"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									
								}
								else
								{
									one.put(fieldNames.get(k).toString(), "All");
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String getGroupAbbr()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				abbreName = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				query.append("SELECT  UCASE(SUBSTR(dept.contact_subtype_name, 1, 3)),group_abbr FROM kr_group_data AS kr LEFT JOIN contact_sub_type AS dept ON kr.sub_type_id=dept.id WHERE kr.id='" + groupId + "'");
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null)
						{
							abbreName.put("abbreviation", object[0].toString() + "/" + object[1].toString());
						}
						else
						{
							abbreName.put("abbreviation", "All/" + object[1].toString());
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String modifyGroupGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
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
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && Parmname != null && !Parmname.equalsIgnoreCase("tableName") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("id"))
						{
							wherClause.put(Parmname, paramValue);
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("kr_group_data", wherClause, condtnBlock, connectionSpace);
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
					wherClause.put("status", "In active");
					condtnBlock.put("id", condtIds.toString());
					cbt.updateTableColomn("kr_group_data", wherClause, condtnBlock, connectionSpace);
				}

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String modifySubGroupGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
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
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && Parmname != null && !Parmname.equalsIgnoreCase("tableName") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
						{
							wherClause.put(Parmname, paramValue);
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("kr_sub_group_data", wherClause, condtnBlock, connectionSpace);
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
					wherClause.put("status", "In active");
					condtnBlock.put("id", condtIds.toString());
					cbt.updateTableColomn("kr_sub_group_data", wherClause, condtnBlock, connectionSpace);
				}
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public Map<String, String> getAbbreName()
	{
		return abbreName;
	}

	public void setAbbreName(Map<String, String> abbreName)
	{
		this.abbreName = abbreName;
	}

	public List<ConfigurationUtilBean> getKRGroupTextBox()
	{
		return KRGroupTextBox;
	}

	public void setKRGroupTextBox(List<ConfigurationUtilBean> kRGroupTextBox)
	{
		KRGroupTextBox = kRGroupTextBox;
	}

	public List<ConfigurationUtilBean> getKRGroupDropDown()
	{
		return KRGroupDropDown;
	}

	public void setKRGroupDropDown(List<ConfigurationUtilBean> kRGroupDropDown)
	{
		KRGroupDropDown = kRGroupDropDown;
	}

	public List<ConfigurationUtilBean> getKRGroupTextBox1()
	{
		return KRGroupTextBox1;
	}

	public void setKRGroupTextBox1(List<ConfigurationUtilBean> kRGroupTextBox1)
	{
		KRGroupTextBox1 = kRGroupTextBox1;
	}

	public List<ConfigurationUtilBean> getKRGroupDropDown1()
	{
		return KRGroupDropDown1;
	}

	public void setKRGroupDropDown1(List<ConfigurationUtilBean> kRGroupDropDown1)
	{
		KRGroupDropDown1 = kRGroupDropDown1;
	}

	public List<GridDataPropertyView> getMasterViewPropForSubGroup()
	{
		return masterViewPropForSubGroup;
	}

	public void setMasterViewPropForSubGroup(List<GridDataPropertyView> masterViewPropForSubGroup)
	{
		this.masterViewPropForSubGroup = masterViewPropForSubGroup;
	}

	public List<Object> getMasterViewListForSubGroup()
	{
		return masterViewListForSubGroup;
	}

	public void setMasterViewListForSubGroup(List<Object> masterViewListForSubGroup)
	{
		this.masterViewListForSubGroup = masterViewListForSubGroup;
	}

	public int getGroupLevel()
	{
		return groupLevel;
	}

	public void setGroupLevel(int groupLevel)
	{
		this.groupLevel = groupLevel;
	}

	public String getGroupLevelName()
	{
		return groupLevelName;
	}

	public void setGroupLevelName(String groupLevelName)
	{
		this.groupLevelName = groupLevelName;
	}

	public String getSubGroupLevelName()
	{
		return subGroupLevelName;
	}

	public void setSubGroupLevelName(String subGroupLevelName)
	{
		this.subGroupLevelName = subGroupLevelName;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public Map<String, String> getDeptName()
	{
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName)
	{
		this.deptName = deptName;
	}

	public String getSearchingData()
	{
		return searchingData;
	}

	public void setSearchingData(String searchingData)
	{
		this.searchingData = searchingData;
	}

	public String getSearchStr()
	{
		return searchStr;
	}

	public void setSearchStr(String searchStr)
	{
		this.searchStr = searchStr;
	}

	public String getSearchFields()
	{
		return searchFields;
	}

	public void setSearchFields(String searchFields)
	{
		this.searchFields = searchFields;
	}

	public Map<String, String> getCountMap()
	{
		return countMap;
	}

	public void setCountMap(Map<String, String> countMap)
	{
		this.countMap = countMap;
	}

	public String getFieldvalue()
	{
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue)
	{
		this.fieldvalue = fieldvalue;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

}