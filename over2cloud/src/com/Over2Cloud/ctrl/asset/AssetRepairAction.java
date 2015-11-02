package com.Over2Cloud.ctrl.asset;

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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssetRepairAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(AssetRepairAction.class);
	Map session = ActionContext.getContext().getSession();
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
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
	// private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> repairColumnMap;
	private boolean serialNoActive = false;
	private String headingSerialNo;
	private boolean vendorActive = false;
	private String headingVendorName;
	private Map<Integer, String> serialNoList = null;
	private Map<Integer, String> vendorList = null;
	private String vendorValue = null;
	private List<ConfigurationUtilBean> dateColumnMap = null;
	Map<String, Object> data4Update = null;
	Map<String, Object> condtnBlock = null;
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<ConfigurationUtilBean> commonDDMap = null;
	private String assetId;

	public String createRepairPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				dateColumnMap = new ArrayList<ConfigurationUtilBean>();
				commonDDMap = new ArrayList<ConfigurationUtilBean>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> repairColumnList = Configuration.getConfigurationData("mapped_asset_repair_master", accountID, connectionSpace, "", 0, "table_name", "asset_repair_master");
				repairColumnMap = new ArrayList<ConfigurationUtilBean>();
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				if (repairColumnList != null && repairColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
						{
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							obj.setValidationType(gdp1.getValidationType());
							obj.setColType(gdp1.getColType());
							obj.setEditable(true);
							if (gdp1.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							repairColumnMap.add(obj);
						}
						if (gdp1.getColomnName().equalsIgnoreCase("serialno"))
						{
							headingSerialNo = gdp1.getHeadingName().toString();
						}
					}
				}
				if (repairColumnList != null && repairColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : repairColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("flag") && !gdp.getColomnName().equalsIgnoreCase("receiveby"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							obj.setEditable(false);
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							repairColumnMap.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("Time"))
						{
							if (!gdp.getColomnName().equalsIgnoreCase("receivedon"))
							{
								ConfigurationUtilBean timeObj = new ConfigurationUtilBean();
								timeObj.setKey(gdp.getColomnName());
								timeObj.setValue(gdp.getHeadingName());
								timeObj.setValidationType(gdp.getValidationType());
								timeObj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1"))
								{
									timeObj.setMandatory(true);
								}
								else
								{
									timeObj.setMandatory(false);
								}
								dateColumnMap.add(timeObj);
							}
						}
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("assetid"))
							{
								serialNoActive = true;
								serialNoList = new LinkedHashMap<Integer, String>();
								try
								{
									String tabCheckQuery = "SHOW TABLES LIKE 'allotment_master'";
									List temp = cbt.executeAllSelectQuery(tabCheckQuery.toString(), connectionSpace);
									List slnoList = null;
									String query;
									if (temp != null && temp.size() > 0)
									{
										query = "SELECT id,serialno FROM asset_detail where flag=1 and id IN(select allot.assetid from asset_allotment_detail as allot where allot.flag=1) order by serialno asc";

										slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
									}

									if (slnoList != null && slnoList.size() > 0)
									{
										for (Object obj1 : slnoList)
										{
											Object[] object = (Object[]) obj1;
											serialNoList.put((Integer) object[0], object[1].toString());
										}
									}
									obj.setKey(gdp.getColomnName());
									obj.setValue(headingSerialNo);
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
									commonDDMap.add(obj);
								}
								catch (Exception e)
								{
									log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addAssetAllotment of class " + getClass(), e);
									e.printStackTrace();
								}

							}
							if (gdp.getColomnName().equalsIgnoreCase("vendorname"))
							{
								vendorActive = true;
								headingVendorName = new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname");
								vendorValue = gdp.getColomnName();
								try
								{
									vendorList = new LinkedHashMap<Integer, String>();
									List temp = new ArrayList<String>();
									String query = "SELECT id,vendorname FROM createvendor_master ORDER BY vendorname ASC";
									temp = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
									if (temp != null && temp.size() > 0)
									{
										for (Object obj1 : temp)
										{
											Object[] object = (Object[]) obj1;
											vendorList.put((Integer) object[0], object[1].toString());
										}
									}
									obj.setKey(gdp.getColomnName());
									obj.setValue(headingVendorName);
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
									commonDDMap.add(obj);
								}
								catch (Exception e)
								{
									log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createRepairPage of class " + getClass(), e);
									e.printStackTrace();
								}
							}
							returnResult = SUCCESS;
						}
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createRepairPage of class " + getClass(), e);
				e.printStackTrace();

			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String addAssetRepair()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String actionMsg = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_repair_master", accountID, connectionSpace, "", 0, "table_name", "asset_repair_master");
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
					boolean repairStatus = false;
					String assetId = null;
					data4Update = new LinkedHashMap<String, Object>();
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
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
						else if (gdp.getColomnName().equalsIgnoreCase("flag"))
							repairStatus = true;

					}
					cbt.createTable22("asset_repair_detail", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					// String deptLevel = (String)session.get("userDeptLevel");
					// String subdept_dept = null;
					boolean repairedStatus = false;
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (parmName.equalsIgnoreCase("assetid"))
							{
								assetId = paramValue;
							}
							if (parmName.equalsIgnoreCase("assetRepairedStatus"))
							{
								repairedStatus = true;
							}
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("assetname") && !parmName.equalsIgnoreCase("assetmodel") && !parmName.equalsIgnoreCase("assetbrand") && !parmName.equalsIgnoreCase("assetRepairedStatus"))
							{
								if (parmName.equalsIgnoreCase("resolveon") || parmName.equalsIgnoreCase("receivedon"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
									data4Update.put(parmName, paramValue);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
									data4Update.put(parmName, paramValue);
								}
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
					if (repairStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("flag");
						ob.setDataName("0");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("On Service");
						insertData.add(ob);

					}
					if (repairedStatus)
					{
						condtnBlock = new LinkedHashMap<String, Object>();
						condtnBlock.put("assetid", assetId);
						data4Update.put("flag", "1");
						status = new HelpdeskUniversalHelper().updateTableColomn("asset_repair_detail", data4Update, condtnBlock, connectionSpace);
					}
					else
					{
						status = cbt.insertIntoTable("asset_repair_detail", insertData, connectionSpace);
					}
					if (status)
					{
						if (assetId != null && assetId != "")
						{
							condtnBlock = new LinkedHashMap<String, Object>();
							condtnBlock.put("assetid", assetId);
							data4Update = new LinkedHashMap<String, Object>();

							if (repairedStatus)
							{
								data4Update.put("flag", "1");
								actionMsg = "Re-assign Successfully!!!";
							}
							else
							{
								data4Update.put("flag", "0");
								actionMsg = "Repair Registered Successfully!!!";
							}
							status = new HelpdeskUniversalHelper().updateTableColomn("asset_allotment_detail", data4Update, condtnBlock, connectionSpace);

							condtnBlock = new LinkedHashMap<String, Object>();
							condtnBlock.put("id", assetId);
							status = new HelpdeskUniversalHelper().updateTableColomn("asset_detail", data4Update, condtnBlock, connectionSpace);
						}
					}
					if (status)
					{
						addActionMessage("Asset " + actionMsg);
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addAssetRepair of class " + getClass(), e);
				e.printStackTrace();

			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createRepairedPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				dateColumnMap = new ArrayList<ConfigurationUtilBean>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> repairColumnList = Configuration.getConfigurationData("mapped_asset_repair_master", accountID, connectionSpace, "", 0, "table_name", "asset_repair_master");
				repairColumnMap = new ArrayList<ConfigurationUtilBean>();
				commonDDMap = new ArrayList<ConfigurationUtilBean>();
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				if (repairColumnList != null && repairColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
						{
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							obj.setValidationType(gdp1.getValidationType());
							obj.setColType(gdp1.getColType());
							obj.setEditable(true);
							if (gdp1.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							repairColumnMap.add(obj);
						}
						if (gdp1.getColomnName().equalsIgnoreCase("serialno"))
						{
							headingSerialNo = gdp1.getHeadingName().toString();
						}
					}
				}
				if (repairColumnList != null && repairColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : repairColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("flag") && !gdp.getColomnName().equalsIgnoreCase("purpose"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							obj.setEditable(false);
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							repairColumnMap.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("Time"))
						{
							if (!gdp.getColomnName().equalsIgnoreCase("resolveon"))
							{
								ConfigurationUtilBean timeObj = new ConfigurationUtilBean();
								timeObj.setKey(gdp.getColomnName());
								timeObj.setValue(gdp.getHeadingName());
								timeObj.setValidationType(gdp.getValidationType());
								timeObj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1"))
								{
									timeObj.setMandatory(true);
								}
								else
								{
									timeObj.setMandatory(false);
								}
								dateColumnMap.add(timeObj);
							}
						}
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("assetid"))
							{
								serialNoActive = true;
								serialNoList = new LinkedHashMap<Integer, String>();
								try
								{
									String tabCheckQuery = "SHOW TABLES LIKE 'asset_repair_detail'";
									List temp = cbt.executeAllSelectQuery(tabCheckQuery.toString(), connectionSpace);
									List slnoList = null;
									String query;
									if (temp != null && temp.size() > 0)
									{
										query = "SELECT id,serialno FROM asset_detail where flag=0 and id IN(select repair.assetid from asset_repair_detail as repair where repair.flag=0) order by serialno asc";

										slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
									}

									if (slnoList != null && slnoList.size() > 0)
									{
										for (Object obj1 : slnoList)
										{
											Object[] object = (Object[]) obj1;
											serialNoList.put((Integer) object[0], object[1].toString());
										}
									}
									obj.setKey(gdp.getColomnName());
									obj.setValue(headingSerialNo);
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
									commonDDMap.add(obj);
								}
								catch (Exception e)
								{
									log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createRepairedPage of class " + getClass(), e);
									e.printStackTrace();
								}

							}
							returnResult = SUCCESS;
						}
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createRepairedPage of class " + getClass(), e);
				e.printStackTrace();

			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeRepairView()
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
				if (getModifyFlag().equalsIgnoreCase("0"))
					setModifyFlag("false");
				else
					setModifyFlag("true");
				if (getDeleteFlag().equalsIgnoreCase("0"))
					setDeleteFlag("false");
				else
					setDeleteFlag("true");

				if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false"))
					setMainHeaderName("Total Repair  >> View");
				else if (getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false"))
					setMainHeaderName("Total Repair >> Modify");
				else if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true"))
					setMainHeaderName("Total Repair  >> Delete");

				getColumn4View("mapped_asset_repair_master", "asset_repair_master");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeRepairView of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getColumn4View(String mappingTable, String masterTable)
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappingTable, accountID, connectionSpace, "", 0, "table_name", masterTable);
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

	public String viewTotalRepair()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_repair_detail");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					// BigInteger count=new BigInteger("3");
					/*
					 * for(Iterator it=dataCount.iterator(); it.hasNext();) {
					 * Object obdata=(Object)it.next();
					 * count=(BigInteger)obdata; } setRecords(count.intValue());
					 * int to = (getRows() getPage()); int from = to -
					 * getRows(); if (to > getRecords()) to = getRecords();
					 */

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_asset_repair_master", accountID, connectionSpace, "asset_repair_master");

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
								{
									query.append(obdata.toString() + ",");
								}
								else
								{
									query.append(obdata.toString());
								}
							}
							i++;

						}
					}
					query.append(" from asset_repair_detail where assetid=" + getId());
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
					cbt.checkTableColmnAndAltertable(fieldNames, "asset_repair_detail", connectionSpace);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					List<String> temp = new ArrayList<String>();
					List list = new ArrayList();
					Map<String, Object> wherClause = new LinkedHashMap<String, Object>();
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
									{
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("vendorname"))
									{
										wherClause.clear();
										temp.clear();
										wherClause.put("id", obdata[k].toString());
										temp.add("vendorname");
										list = cbt.viewAllDataEitherSelectOrAll("createvendor_master", temp, wherClause, connectionSpace);
										tempMap.put(fieldNames.get(k).toString(), list.get(0).toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("resolveon") || fieldNames.get(k).toString().equalsIgnoreCase("receivedon"))
									{
										tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToUSFormat(obdata[k].toString()));
									}
									else
									{
										tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
						setRecords(masterViewList.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}

				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewTotalRepair of class " + getClass(), e);
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
	public String beforeGetBreakdownDetails()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_complaint_configuration", accountID, connectionSpace, "", 0, "table_name", "asset_complaint_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						if(gdp.getColomnName().equalsIgnoreCase("ticket_no") || gdp.getColomnName().equalsIgnoreCase("feed_by") || gdp.getColomnName().equalsIgnoreCase("feed_by_mobno")
								|| gdp.getColomnName().equalsIgnoreCase("by_dept") || gdp.getColomnName().equalsIgnoreCase("to_dept") || gdp.getColomnName().equalsIgnoreCase("feed_brief")
								|| gdp.getColomnName().equalsIgnoreCase("allot_to") || gdp.getColomnName().equalsIgnoreCase("open_date") || gdp.getColomnName().equalsIgnoreCase("level")
								|| gdp.getColomnName().equalsIgnoreCase("status") || gdp.getColomnName().equalsIgnoreCase("resolve_date") || gdp.getColomnName().equalsIgnoreCase("resolve_remark")
								|| gdp.getColomnName().equalsIgnoreCase("resolve_by")
						)
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
					if (masterViewProp != null && masterViewProp.size() > 0)
					{
						session.put("repairColumnMap", masterViewProp);
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
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
	public String viewBreakdownDetails()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder("");
				StringBuilder query1 = new StringBuilder("");
				query.append("select ");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("repairColumnMap");
				if (session.containsKey("repairColumnMap"))
				{
					session.remove("repairColumnMap");
				}
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView GPV : fieldNames)
					{
						if(GPV.getColomnName().toString().equals("by_dept"))
						{
							query1.append("DEPT.deptName AS By_dept,");
						}
						else if(GPV.getColomnName().equalsIgnoreCase("to_dept"))
						{
							query1.append("DEPT1.deptName AS To_dept,");
						}
						else if(GPV.getColomnName().equalsIgnoreCase("allot_to"))
						{
							query1.append("EMP.empName AS allotto,");
						}
						else if(GPV.getColomnName().equalsIgnoreCase("resolve_by"))
						{
							query1.append("EMP1.empName as resolveby,");
						}
						else
						{
							query1.append("ACS."+GPV.getColomnName().toString()+",");
						}
					}
					query.append(query1.substring(0, query1.length()-1));
					query.append(" FROM asset_complaint_status AS ACS ");
					query.append(" LEFT JOIN department AS DEPT ON ACS.by_dept=DEPT.id");
					query.append(" LEFT JOIN department AS DEPT1 ON ACS.to_dept=DEPT1.id");
					query.append(" LEFT JOIN employee_basic AS EMP ON ACS.allot_to=EMP.id");
					query.append(" LEFT JOIN employee_basic AS EMP1 ON EMP1.id=ACS.resolve_by");
					query.append(" WHERE asset_id="+assetId);
					
					List data1 = new ArrayList();
					data1=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data1 != null && data1.size() > 0)
					{
						setRecords(data1.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = data1.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							int k = 0;
							for (GridDataPropertyView GPV : fieldNames)
							{
								if (obdata[k] != null)
								{
									if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										tempMap.put(GPV.getColomnName().toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else
									{
										tempMap.put(GPV.getColomnName().toString(), obdata[k].toString());
									}
								}
								else
								{
									tempMap.put(GPV.getColomnName().toString(), "NA");
								}
								k++;
							}
							tempList.add(tempMap);
							
						}
						setMasterViewList(tempList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
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

	public boolean isSerialNoActive()
	{
		return serialNoActive;
	}

	public void setSerialNoActive(boolean serialNoActive)
	{
		this.serialNoActive = serialNoActive;
	}

	public String getHeadingSerialNo()
	{
		return headingSerialNo;
	}

	public void setHeadingSerialNo(String headingSerialNo)
	{
		this.headingSerialNo = headingSerialNo;
	}

	public boolean isVendorActive()
	{
		return vendorActive;
	}

	public void setVendorActive(boolean vendorActive)
	{
		this.vendorActive = vendorActive;
	}

	public String getHeadingVendorName()
	{
		return headingVendorName;
	}

	public void setHeadingVendorName(String headingVendorName)
	{
		this.headingVendorName = headingVendorName;
	}

	public Map<Integer, String> getSerialNoList()
	{
		return serialNoList;
	}

	public void setSerialNoList(Map<Integer, String> serialNoList)
	{
		this.serialNoList = serialNoList;
	}

	public Map<Integer, String> getVendorList()
	{
		return vendorList;
	}

	public void setVendorList(Map<Integer, String> vendorList)
	{
		this.vendorList = vendorList;
	}

	public String getVendorValue()
	{
		return vendorValue;
	}

	public void setVendorValue(String vendorValue)
	{
		this.vendorValue = vendorValue;
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

	public Map<String, Object> getCondtnBlock()
	{
		return condtnBlock;
	}

	public void setCondtnBlock(Map<String, Object> condtnBlock)
	{
		this.condtnBlock = condtnBlock;
	}

	public Map<String, Object> getData4Update()
	{
		return data4Update;
	}

	public void setData4Update(Map<String, Object> data4Update)
	{
		this.data4Update = data4Update;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public String getModifyFlag()
	{
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag)
	{
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public List<ConfigurationUtilBean> getRepairColumnMap()
	{
		return repairColumnMap;
	}

	public void setRepairColumnMap(List<ConfigurationUtilBean> repairColumnMap)
	{
		this.repairColumnMap = repairColumnMap;
	}

	public List<ConfigurationUtilBean> getDateColumnMap()
	{
		return dateColumnMap;
	}

	public void setDateColumnMap(List<ConfigurationUtilBean> dateColumnMap)
	{
		this.dateColumnMap = dateColumnMap;
	}

	public List<ConfigurationUtilBean> getCommonDDMap()
	{
		return commonDDMap;
	}

	public void setCommonDDMap(List<ConfigurationUtilBean> commonDDMap)
	{
		this.commonDDMap = commonDDMap;
	}

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}

}
