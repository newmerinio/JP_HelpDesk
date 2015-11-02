package com.Over2Cloud.ctrl.asset;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

@SuppressWarnings("serial")
public class SupportCatgAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(SupportCatgAction.class);
	@SuppressWarnings("rawtypes")
    Map session = ActionContext.getContext().getSession();
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
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
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private HttpServletRequest request;
	List<ConfigurationUtilBean> supportCatgColumnMap = null;
	List<ConfigurationUtilBean> assetTypeColumnMap = null;
	

	public String createSupportCatgPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				List<GridDataPropertyView> vendorColumnList = Configuration.getConfigurationData("mapped_asset_support_catg", accountID, connectionSpace, "", 0, "table_name", "asset_support_catg");
				supportCatgColumnMap = new ArrayList<ConfigurationUtilBean>();
				ConfigurationUtilBean obj;
				if (vendorColumnList != null && vendorColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : vendorColumnList)
					{
						obj=new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							obj.setEditable(true);
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							supportCatgColumnMap.add(obj);
						}

					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSupportCatgPage of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addSupportCatg()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_support_catg", accountID, connectionSpace, "", 0, "table_name", "asset_support_catg");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					TableColumes ob1=null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
					}
					cbt.createTable22("createasset_support_catg_master", tableColume, connectionSpace);
					String query="SELECT id FROM createasset_support_catg_master WHERE status='Active' AND category='"+request.getParameter("category")+"'";
					List datalist=cbt.executeAllSelectQuery(query, connectionSpace);
					if(datalist!=null && datalist.size()>0)
					{
						addActionMessage("Entered Support Type Already Exist!!!");
						returnResult = SUCCESS;
					}
					else
					{
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						if (requestParameterNames != null && requestParameterNames.size() > 0)
						{
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext())
							{
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null)
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
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
						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);
						
						status = cbt.insertIntoTable("createasset_support_catg_master", insertData, connectionSpace);
						if (status)
						{
							addActionMessage("Status Registered Successfully!!!");
							returnResult = SUCCESS;
						}
						else
						{
							addActionMessage("Oops There is some error in data!");
						}
					}
					
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addSupportCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// create support category view page

	public String createCatgViewPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getColumn4View();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createCatgViewPage of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getColumn4View()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_support_catg", accountID, connectionSpace, "", 0, "table_name", "asset_support_catg");
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
				masterViewProp.add(gpv);
			}
		}
	}

	// view support category
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
    public String viewSupportCatg()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from createasset_support_catg_master where status='Active'");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
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
					List fieldNames = Configuration.getColomnList("mapped_asset_support_catg", accountID, connectionSpace, "asset_support_catg");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dyanamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
									query.append(obdata.toString() + ",");
								else
									query.append(obdata.toString());
							}
							i++;

						}
					}
					query.append(" from createasset_support_catg_master where status='Active'");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
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
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" " + getSidx() + " DESC");
						}
					}
					query.append(" ORDER BY category");
					cbt.checkTableColmnAndAltertable(fieldNames, "createasset_support_catg_master", connectionSpace);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									else
										tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
								else
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}

				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSupportCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// Modify Support Category
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifySupportCatg()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
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
								wherClause.put(parmName, DateUtil.getCurrentTime());
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}

					}

					condtnBlock.put("id", getId());
					boolean status = cbt.updateTableColomn("createasset_support_catg_master", wherClause, condtnBlock, connectionSpace);
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
						wherClause.put("status", "DActive");
						condtnBlock.put("id", H);
						cbt.updateTableColomn("createasset_support_catg_master", wherClause, condtnBlock, connectionSpace);
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifySupportCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createAssetTypeViewPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getColumn4ViewAssetType();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createCatgViewPage of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getColumn4ViewAssetType()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_type_master", accountID, connectionSpace, "", 0, "table_name", "asset_type_master");
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
				masterViewProp.add(gpv);
			}
		}
	}
	
	public String createAssetTypePage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				List<GridDataPropertyView> assetTypeColumnList = Configuration.getConfigurationData("mapped_asset_type_master", accountID, connectionSpace, "", 0, "table_name", "asset_type_master");
				assetTypeColumnMap = new ArrayList<ConfigurationUtilBean>();
				ConfigurationUtilBean obj=null;
				if (assetTypeColumnList != null && assetTypeColumnList.size() > 0)
				{
					
					for (GridDataPropertyView gdp : assetTypeColumnList)
					{
						obj=new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							obj.setEditable(true);
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							assetTypeColumnMap.add(obj);
						}
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSupportCatgPage of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addAssetType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_type_master", accountID, connectionSpace, "", 0, "table_name", "asset_type_master");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					TableColumes ob1=null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
					}
					cbt.createTable22("createasset_type_master", tableColume, connectionSpace);
					String query="SELECT id FROM createasset_type_master WHERE status='Active' AND assetCategory='"+request.getParameter("assetCategory")+"'";
					List datalist=cbt.executeAllSelectQuery(query, connectionSpace);
					if(datalist!=null && datalist.size()>0)
					{
						addActionMessage("Entered Asset Type Already Exist!!!");
						returnResult = SUCCESS;
					}
					else
					{
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						if (requestParameterNames != null && requestParameterNames.size() > 0)
						{
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext())
							{
								String parmName = it.next().toString();
								String paramValue = request.getParameter(parmName);
								if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null)
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
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
						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);
						
						status = cbt.insertIntoTable("createasset_type_master", insertData, connectionSpace);
						if (status)
						{
							addActionMessage("Status Registered Successfully!!!");
							returnResult = SUCCESS;
						}
						else
						{
							addActionMessage("Oops There is some error in data!");
						}
					}
					
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addSupportCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public String viewAssetType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("SELECT count(*) FROM createasset_type_master WHERE status='Active'");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
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

					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_asset_type_master", accountID, connectionSpace, "asset_type_master");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
									query.append(obdata.toString() + ",");
								else
									query.append(obdata.toString());
							}
							i++;

						}
					}
					query.append(" from createasset_type_master where status='Active'");
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
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
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" " + getSidx() + " DESC");
						}
					}
					query.append(" ORDER BY assetCategory");
					cbt.checkTableColmnAndAltertable(fieldNames, "createasset_type_master", connectionSpace);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									else
										tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
								else
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}

				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSupportCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifyAssetType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
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
								wherClause.put(parmName, DateUtil.getCurrentTime());
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("id", getId());
					boolean status = cbt.updateTableColomn("createasset_type_master", wherClause, condtnBlock, connectionSpace);
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
						wherClause.put("status", "DActive");
						condtnBlock.put("id", H);
						cbt.updateTableColomn("createasset_type_master", wherClause, condtnBlock, connectionSpace);
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifySupportCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	public List<ConfigurationUtilBean> getSupportCatgColumnMap()
	{
		return supportCatgColumnMap;
	}

	public void setSupportCatgColumnMap(List<ConfigurationUtilBean> supportCatgColumnMap)
	{
		this.supportCatgColumnMap = supportCatgColumnMap;
	}

	public List<ConfigurationUtilBean> getAssetTypeColumnMap()
	{
		return assetTypeColumnMap;
	}

	public void setAssetTypeColumnMap(List<ConfigurationUtilBean> assetTypeColumnMap)
	{
		this.assetTypeColumnMap = assetTypeColumnMap;
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

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}



	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

}
