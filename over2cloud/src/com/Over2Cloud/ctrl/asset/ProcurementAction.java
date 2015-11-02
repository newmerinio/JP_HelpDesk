package com.Over2Cloud.ctrl.asset;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.asset.common.CreateLogTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProcurementAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(ProcurementAction.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	Map session = ActionContext.getContext().getSession();
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<ConfigurationUtilBean> procurementColumnMap = null;
	private List<ConfigurationUtilBean> procurementDDMap = null;
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
	private boolean serialNoActive = false;
	private String headingSerialNo;
	private boolean vendorActive = false;
	private String headingVendorName;
	private boolean supportFromActive = false;
	private String headingSupportFrom;
	private boolean supportToActive = false;
	private String headingSupportTo;
	private Map<Integer, String> serialNoList = null;
	private Map<Integer, String> vendorList = null;
	private String vendorValue = null;
	private Map<String, String> support4List = null;
	private List<ConfigurationUtilBean> dateColumnMap = null;

	private HttpServletRequest request;

	// set data for create vendor add page
	@SuppressWarnings("unchecked")
	public String createProcurementPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				String userName = (String) session.get("uName");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> procurementColumnList = Configuration.getConfigurationData("mapped_procurement_master", accountID, connectionSpace, "", 0, "table_name", "procurement_master");
				procurementColumnMap = new ArrayList<ConfigurationUtilBean>();
				// support4List=new LinkedHashMap<String, String>();
				dateColumnMap = new ArrayList<ConfigurationUtilBean>();
				procurementDDMap = new ArrayList<ConfigurationUtilBean>();
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				String dept_subdept_id = null;
				try
				{
					List empData = new AssetUniversalHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), (String) session.get("userDeptLevel"), connectionSpace);
					if (empData != null && empData.size() > 0)
					{
						for (Iterator iterator = empData.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							dept_subdept_id = object[3].toString();
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if (procurementColumnList != null && procurementColumnList.size() > 0)
				{
					ConfigurationUtilBean obj;
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
						{
							obj = new ConfigurationUtilBean();
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
							procurementColumnMap.add(obj);
						}
						if (gdp1.getColomnName().equalsIgnoreCase("serialno"))
						{
							obj = new ConfigurationUtilBean();
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());

							headingSerialNo = gdp1.getHeadingName().toString();
						}
					}
				}
				if (procurementColumnList != null && procurementColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : procurementColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getColomnName().equalsIgnoreCase("totalamount"))
							{
								obj.setEditable(true);
							}
							else
							{
								obj.setEditable(false);
							}
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							procurementColumnMap.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("assetid"))
							{
								serialNoActive = true;
								serialNoList = new LinkedHashMap<Integer, String>();
								try
								{
									String tabCheckQuery = "SHOW TABLES LIKE 'procurement_detail'";
									List temp = cbt.executeAllSelectQuery(tabCheckQuery.toString(), connectionSpace);
									List slnoList = null;
									if (temp != null && temp.size() > 0)
									{
										// String query=
										// "SELECT id,serialno FROM asset_detail where id NOT IN(SELECT assetid FROM procurement_detail) AND dept_subdept="
										// +dept_subdept_id+
										// " ORDER BY serialno ASC";
										String query = "SELECT id,assetname FROM asset_detail where id NOT IN(SELECT assetid FROM procurement_detail) ORDER BY assetname ASC";
										slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
									}
									else
									{
										// String query1=
										// "SELECT id,serialno FROM asset_detail WHERE ast.dept_subdept="
										// +dept_subdept_id+
										// " ORDER BY serialno ASC";
										String query1 = "SELECT id,assetname FROM asset_detail ORDER BY assetname ASC";
										slnoList = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
									procurementDDMap.add(obj);
								}
								catch (Exception e)
								{
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
									temp.add("id");
									temp.add("vendorname");
									temp = cbt.viewAllDataEitherSelectOrAll("createvendor_master", temp, connectionSpace);
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
									procurementDDMap.add(obj);
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
							/*
							 * if(gdp.getColomnName().equalsIgnoreCase("supportfrom"
							 * )) { supportFromActive=true;
							 * headingSupportFrom=gdp
							 * .getHeadingName().toString();
							 * obj.setKey(gdp.getColomnName());
							 * obj.setValue(headingSupportFrom);
							 * obj.setValidationType(gdp.getValidationType());
							 * obj.setColType(gdp.getColType());
							 * if(gdp.getMandatroy().toString().equals("1")) {
							 * obj.setMandatory(true); } else {
							 * obj.setMandatory(false); }
							 * procurementDDMap.add(obj); }
							 * if(gdp.getColomnName(
							 * ).equalsIgnoreCase("supportfor")) {
							 * supportToActive=true;
							 * headingSupportTo=gdp.getHeadingName().toString();
							 * obj.setKey(gdp.getColomnName());
							 * obj.setValue(headingSupportTo);
							 * obj.setValidationType(gdp.getValidationType());
							 * obj.setColType(gdp.getColType());
							 * if(gdp.getMandatroy().toString().equals("1")) {
							 * obj.setMandatory(true); } else {
							 * obj.setMandatory(false); }
							 * procurementDDMap.add(obj); }
							 */

						}
						if (gdp.getColType().trim().equalsIgnoreCase("Date"))
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
						/*
						 * if(gdp.getColomnName().equalsIgnoreCase("commssioningon"
						 * )
						 * ||gdp.getColomnName().equalsIgnoreCase("installon")||
						 * gdp.getColomnName().equalsIgnoreCase("receivedon")) {
						 * support4List.put(gdp.getColomnName().toString(),
						 * gdp.getHeadingName().toString()); }
						 */
					}
					System.out.println("Date size >>> " + dateColumnMap.size() + " Dropdown Map size>>>> " + procurementDDMap.size() + " Text File size>>>> " + procurementColumnList.size());
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createProcurementPage of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// Add Asset Procurement Details
	@SuppressWarnings("unchecked")
	public String addProcurement()
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
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_procurement_master", accountID, connectionSpace, "", 0, "table_name", "procurement_master");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> depInsertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					List<TableColumes> depTableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
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
					}
					cbt.createTable22("procurement_detail", tableColume, connectionSpace);

					TableColumes ob1 = new TableColumes();
					ob1.setColumnname("assetid");
					ob1.setDatatype("varchar(30)");
					ob1.setConstraint("default NULL");
					depTableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("opening_cost");
					ob1.setDatatype("varchar(250)");
					ob1.setConstraint("default NULL");
					depTableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("addition_cost_btw_finance_period");
					ob1.setDatatype("varchar(250)");
					ob1.setConstraint("default '0'");
					depTableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					depTableColume.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("time");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default NULL");
					depTableColume.add(ob1);

					cbt.createTable22("asset_depression_detail", depTableColume, connectionSpace);

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
								if (parmName.equalsIgnoreCase("expectedlife") || parmName.equalsIgnoreCase("challandate") || parmName.equalsIgnoreCase("receivedon") || parmName.equalsIgnoreCase("installon") || parmName.equalsIgnoreCase("commssioningon") || parmName.equalsIgnoreCase("paidon"))
								{
									System.out.println(parmName + " >>>>> " + paramValue);
									ob.setColName(parmName);
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
								}
								else if (!parmName.equalsIgnoreCase("assetname") && !parmName.equalsIgnoreCase("assetbrand") && !parmName.equalsIgnoreCase("assetmodel") && !parmName.equalsIgnoreCase("mobileno"))
								{
									System.out.println(parmName + " >>>> " + paramValue);
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
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
					System.out.println("Insert data size " + insertData.size());
					status = cbt.insertIntoTable("procurement_detail", insertData, connectionSpace);
					if (status)
					{
						ob = new InsertDataTable();
						ob.setColName("assetid");
						ob.setDataName(request.getParameter("assetid"));
						depInsertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("opening_cost");
						ob.setDataName(request.getParameter("totalamount"));
						depInsertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						depInsertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						depInsertData.add(ob);

						cbt.insertIntoTable("asset_depression_detail", depInsertData, connectionSpace);

						addActionMessage("Procurement Add Successfully!!!");
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addProcurement of class " + getClass(), e);
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
	public String beforeProcumntView()
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
					setMainHeaderName("Procurement >> View");
				else if (getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false"))
					setMainHeaderName("Procurement>> Modify");
				else if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true"))
					setMainHeaderName("Procurement>> Delete");

				getProcumntColumn4View();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeProcumntView of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getProcumntColumn4View()
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_procurement_master", accountID, connectionSpace, "", 0, "table_name", "procurement_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				if (!gdp.getColomnName().equalsIgnoreCase("assetid") && !gdp.getColomnName().equalsIgnoreCase("vendorname"))
				{
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
	}

	public String viewProcurement()
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
				query1.append("select count(*) from procurement_detail");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					/*
					 * BigInteger count=new BigInteger("3"); for(Iterator
					 * it=dataCount.iterator(); it.hasNext();) { Object
					 * obdata=(Object)it.next(); count=(BigInteger)obdata; }
					 * setRecords(count.intValue()); int to = (getRows()
					 * getPage()); int from = to - getRows(); if (to >
					 * getRecords()) to = getRecords();
					 */

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_procurement_master", accountID, connectionSpace, "procurement_master");

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
					query.append(" from procurement_detail where assetid=" + getId());
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
					cbt.checkTableColmnAndAltertable(fieldNames, "procurement_detail", connectionSpace);
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
									{
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									}
									else
									{
										if (fieldNames.get(k).toString().equalsIgnoreCase("expectedlife") || fieldNames.get(k).toString().equalsIgnoreCase("challandate") || fieldNames.get(k).toString().equalsIgnoreCase("receivedon") || fieldNames.get(k).toString().equalsIgnoreCase("installon") || fieldNames.get(k).toString().equalsIgnoreCase("commssioningon")
												|| fieldNames.get(k).toString().equalsIgnoreCase("paidon"))
										{
											tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else
										{
											tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyProcurement of class " + getClass(), e);
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
	public String modifyProcurement()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		boolean updateStatus = false;
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (getOper().equalsIgnoreCase("edit"))
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					StringBuffer columnBuffer = new StringBuffer("");
					StringBuffer dataBuffer = new StringBuffer("");
					Map<String, String> logData = new LinkedHashMap<String, String>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					double unitCost = 0;
					int quantity = 0;
					double taxes = 0;
					String totalAmount = null;
					boolean totalFlag = false;
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						System.out.println("param name====" + parmName);
						System.out.println("param value====" + paramValue);
						if (parmName.equalsIgnoreCase("unitcost"))
						{
							unitCost = Double.valueOf(paramValue);
						}
						if (parmName.equalsIgnoreCase("quantity"))
						{
							quantity = Integer.valueOf(paramValue);
						}
						if (parmName.equalsIgnoreCase("tax"))
						{
							taxes = Double.valueOf(paramValue);
						}

						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equalsIgnoreCase("userName"))
							{
								wherClause.put(parmName, userName);
								logData.put("userName", userName);
							}
							else if (parmName.equalsIgnoreCase("date"))
							{
								wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
								logData.put("updateon", DateUtil.getCurrentDateUSFormat());
							}
							else if (parmName.equalsIgnoreCase("time"))
							{
								wherClause.put(parmName, DateUtil.getCurrentTime());
								logData.put("updateat", DateUtil.getCurrentTime());
							}
							else if (parmName.equalsIgnoreCase("totalamount"))
							{
								totalFlag = true;
							}
							else if (parmName.equalsIgnoreCase("expectedlife") || parmName.equalsIgnoreCase("challandate") || parmName.equalsIgnoreCase("receivedon") || parmName.equalsIgnoreCase("installon") || parmName.equalsIgnoreCase("commssioningon") || parmName.equalsIgnoreCase("paidon"))
							{
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
							}
							else
							{
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}

					}
					if (totalFlag)
					{
						double total = 0.00;
						double baseValue;
						try
						{
							baseValue = (unitCost * quantity);
							total = baseValue + (baseValue * taxes) / 100;
							DecimalFormat df = new DecimalFormat("#.##");
							totalAmount = df.format(total);
							returnResult = SUCCESS;
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						wherClause.put("totalamount", totalAmount);
						columnBuffer.append("totalamount#");
						dataBuffer.append(totalAmount + "#");
					}
					condtnBlock.put("id", getId());
					for (Entry<String, Object> entry : wherClause.entrySet())
					{
						System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					}

					ProcurementAction pd = new ProcurementAction();
					StringBuilder updateQuery = pd.updateQuery("procurement_detail", wherClause, condtnBlock);
					updateStatus = cbt.updateTableColomn(connectionSpace, updateQuery);
					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "procurement_detail");
					logData.put("update_type", "Modify");
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("procurement_detail", "id", condtIds.toString(), connectionSpace);
				}
				if (updateStatus)
				{
					returnResult = SUCCESS;
				}

			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyProcurement of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public StringBuilder updateQuery(String tablename, Map<String, Object> wherClause, Map<String, Object> condtnBlock)
	{
		StringBuilder selectTableData = new StringBuilder("");
		try
		{

			selectTableData.append("update " + tablename + " set ");
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			if (wherClause != null && wherClause.size() > 0)
			{
				while (i.hasNext())
				{
					Map.Entry me = (Map.Entry) i.next();
					if (size < wherClause.size())
						selectTableData.append(me.getKey() + " = '" + me.getValue() + "' , ");
					else
						selectTableData.append(me.getKey() + " = '" + me.getValue() + "'");
					size++;
				}
			}
			if (condtnBlock != null && condtnBlock.size() > 0)
			{
				selectTableData.append(" where ");
				size = 1;
				set = condtnBlock.entrySet();
				i = set.iterator();
				while (i.hasNext())
				{
					Map.Entry me = (Map.Entry) i.next();
					if (size < condtnBlock.size())
						selectTableData.append(me.getKey() + " = " + me.getValue() + " and ");
					else
						selectTableData.append(me.getKey() + " = " + me.getValue() + "");
					size++;
				}
			}
			System.out.println("querty of update is as.,.,.,.,.,.,.,.,.,.,==" + selectTableData.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return selectTableData;

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

	public boolean isSerialNoActive()
	{
		return serialNoActive;
	}

	public void setSerialNoActive(boolean serialNoActive)
	{
		this.serialNoActive = serialNoActive;
	}

	public boolean isVendorActive()
	{
		return vendorActive;
	}

	public void setVendorActive(boolean vendorActive)
	{
		this.vendorActive = vendorActive;
	}

	public boolean isSupportFromActive()
	{
		return supportFromActive;
	}

	public void setSupportFromActive(boolean supportFromActive)
	{
		this.supportFromActive = supportFromActive;
	}

	public String getHeadingSerialNo()
	{
		return headingSerialNo;
	}

	public void setHeadingSerialNo(String headingSerialNo)
	{
		this.headingSerialNo = headingSerialNo;
	}

	public String getHeadingVendorName()
	{
		return headingVendorName;
	}

	public void setHeadingVendorName(String headingVendorName)
	{
		this.headingVendorName = headingVendorName;
	}

	public String getHeadingSupportFrom()
	{
		return headingSupportFrom;
	}

	public void setHeadingSupportFrom(String headingSupportFrom)
	{
		this.headingSupportFrom = headingSupportFrom;
	}

	public String getHeadingSupportTo()
	{
		return headingSupportTo;
	}

	public void setHeadingSupportTo(String headingSupportTo)
	{
		this.headingSupportTo = headingSupportTo;
	}

	public boolean isSupportToActive()
	{
		return supportToActive;
	}

	public void setSupportToActive(boolean supportToActive)
	{
		this.supportToActive = supportToActive;
	}

	public String getVendorValue()
	{
		return vendorValue;
	}

	public void setVendorValue(String vendorValue)
	{
		this.vendorValue = vendorValue;
	}

	public Map<Integer, String> getVendorList()
	{
		return vendorList;
	}

	public void setVendorList(Map<Integer, String> vendorList)
	{
		this.vendorList = vendorList;
	}

	public Map<Integer, String> getSerialNoList()
	{
		return serialNoList;
	}

	public void setSerialNoList(Map<Integer, String> serialNoList)
	{
		this.serialNoList = serialNoList;
	}

	public Map<String, String> getSupport4List()
	{
		return support4List;
	}

	public void setSupport4List(Map<String, String> support4List)
	{
		this.support4List = support4List;
	}

	public List<ConfigurationUtilBean> getProcurementColumnMap()
	{
		return procurementColumnMap;
	}

	public void setProcurementColumnMap(List<ConfigurationUtilBean> procurementColumnMap)
	{
		this.procurementColumnMap = procurementColumnMap;
	}

	public List<ConfigurationUtilBean> getDateColumnMap()
	{
		return dateColumnMap;
	}

	public void setDateColumnMap(List<ConfigurationUtilBean> dateColumnMap)
	{
		this.dateColumnMap = dateColumnMap;
	}

	public List<ConfigurationUtilBean> getProcurementDDMap()
	{
		return procurementDDMap;
	}

	public void setProcurementDDMap(List<ConfigurationUtilBean> procurementDDMap)
	{
		this.procurementDDMap = procurementDDMap;
	}

}
