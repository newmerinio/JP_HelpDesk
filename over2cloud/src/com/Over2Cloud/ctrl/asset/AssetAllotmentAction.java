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
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.asset.common.CreateLogTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssetAllotmentAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(AssetAllotmentAction.class);
	Map session = ActionContext.getContext().getSession();
	String accountID = (String) session.get("accountid");
	String userName = (String) session.get("uName");
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
	// private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private boolean serialNoActive = false;
	private String headingSerialNo;
	private Map<Integer, String> serialNoList = null;
	private List<ConfigurationUtilBean> reallotColumnMap;
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<Object> masterViewList;
	private List<ConfigurationUtilBean> commonDDMap1 = null;

	private HttpServletRequest request;
	private String assetId;
	private Map<Integer, String>assetTypeList;
	List<ConfigurationUtilBean> assetDropMap=null;
	private String outletId;
	private Map<String, String>outletTypeList;
	private List<ConfigurationUtilBean> allotmentRadio = null;
	private List<ConfigurationUtilBean> allotmentDropDown = null;
	private List<ConfigurationUtilBean> allotmentColumnMap;
	private List<ConfigurationUtilBean> commonDDMap = null;
	private List<ConfigurationUtilBean> dateColumnMap;
	private Map<String,String> allottoFlagValue;
	private Map<String,String> outletMappedDept;
	private String O;
	private String allottoflag;

	@SuppressWarnings("unchecked")
	public String addAssetAllotment()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Map<String, Object> data4Update = new LinkedHashMap<String, Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String assetId = null,actionMsg=" Alloted Successfully!!!";
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					List<TableColumes> allotLogTableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					boolean avilStatus = false;
					boolean returnOnStatus = false;
					for (GridDataPropertyView gdp : statusColName)
					{
						// set column name for create asset_allotment_detail
						// table
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						// end

						// set column name for create asset_allotment_log table
						TableColumes ob2 = new TableColumes();
						ob2.setColumnname(gdp.getColomnName());
						ob2.setDatatype("varchar(255)");
						ob2.setConstraint("default NULL");
						allotLogTableColume.add(ob2);
						// end

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("flag"))
							avilStatus = true;
					}
					
					TableColumes ob2 = new TableColumes();
					ob2.setColumnname("asset_freedate");
					ob2.setDatatype("varchar(255)");
					ob2.setConstraint("default NULL");
					allotLogTableColume.add(ob2);
					// create asset_allotment_detail table
					cbt.createTable22("asset_allotment_detail", tableColume, connectionSpace);
					// end

					// create asset_allotment_detail table
					cbt.createTable22("asset_allotment_log", allotLogTableColume, connectionSpace);
					// end

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					String deptLevel = (String) session.get("userDeptLevel");
					String subdept_dept = null;
					// String usagesType=null;
					String returnDate = null;
					String freeORReAllot = null;
					String assetType = null;
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						String parmName=null;
						String paramValue=null;
						while (it.hasNext())
						{
							parmName = it.next().toString();
							paramValue = request.getParameter(parmName);
							if (parmName.equalsIgnoreCase("deptname"))
							{
								subdept_dept = paramValue;
							}
							if (parmName.equalsIgnoreCase("assettype"))
							{
								assetType = paramValue;
							}
							if (parmName.equalsIgnoreCase("returnon"))
							{
								returnDate = paramValue;
								returnOnStatus = true;
							}

							if (parmName.equalsIgnoreCase("freeORReAllot"))
							{
								freeORReAllot = paramValue;
							}

							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("empMobileNo") && !parmName.equalsIgnoreCase("deptHierarchy") && !parmName.equalsIgnoreCase("deptname") && !parmName.equalsIgnoreCase("subdeptname") && !parmName.equalsIgnoreCase("assetname") && !parmName.equalsIgnoreCase("intecom")
									&& !parmName.equalsIgnoreCase("assetbrand") && !parmName.equalsIgnoreCase("assetmodel") && !parmName.equalsIgnoreCase("assetType") && !parmName.equalsIgnoreCase("returnon") && !parmName.equalsIgnoreCase("freeORReAllot"))
							{
								if (parmName.equalsIgnoreCase("issueon"))
								{
									ob = new InsertDataTable();
									ob.setColName("issueon");
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
									data4Update.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
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
							if (parmName.equalsIgnoreCase("assetid"))
							{
								assetId = paramValue;
							}
						}
					}
					if (subdept_dept != null)
					{
						ob = new InsertDataTable();
						ob.setColName("dept_subdept");
						ob.setDataName(subdept_dept);
						insertData.add(ob);
						data4Update.put("dept_subdept", subdept_dept);
					}

					if (deptLevel != null)
					{
						ob = new InsertDataTable();
						ob.setColName("deptHierarchy");
						ob.setDataName(deptLevel);
						insertData.add(ob);
						data4Update.put("deptHierarchy", deptLevel);
					}

					if (userTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);
						data4Update.put("userName", userName);
					}
					if (dateTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
						data4Update.put("date", DateUtil.getCurrentDateUSFormat());
					}
					if (timeTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						data4Update.put("time", DateUtil.getCurrentTimeHourMin());
					}
					if (avilStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("flag");
						ob.setDataName("1");
						insertData.add(ob);
						data4Update.put("flag", "1");
					}
					if (returnOnStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("returnon");
						ob.setDataName(DateUtil.convertDateToUSFormat(returnDate));
						insertData.add(ob);
						data4Update.put("returnon", DateUtil.convertDateToUSFormat(returnDate));
					}
					String query = "SELECT id FROM asset_allotment_detail where assetid=" + assetId;
					List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
						condtnBlock.put("assetid", assetId);
						if (freeORReAllot != null && freeORReAllot.equals("free"))
						{
							data4Update = new LinkedHashMap<String, Object>();
							data4Update.put("flag", "0");
							status = new HelpdeskUniversalHelper().updateTableColomn("asset_allotment_detail", data4Update, condtnBlock, connectionSpace);
						}
						else
						{
							System.out.println("Inside Reallot");
							status = new HelpdeskUniversalHelper().updateTableColomn("asset_allotment_detail", data4Update, condtnBlock, connectionSpace);
						}
					}
					else
					{
						status = cbt.insertIntoTable("asset_allotment_detail", insertData, connectionSpace);
					}

					if (assetType != null && assetType != "")
					{
						Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
						Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
						condtnBlock.put("id", assetId);
						dataMap.put("assettype", assetType);
						status = new HelpdeskUniversalHelper().updateTableColomn("asset_detail", dataMap, condtnBlock, connectionSpace);
					}

					if (status)
					{
						if (freeORReAllot != null && freeORReAllot.equals("free"))
						{
							actionMsg = "Free Successfully!!!";
							data4Update = new LinkedHashMap<String, Object>();
							Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
							List<String> nullCondtnBlock = new ArrayList<String>();
							data4Update.put("asset_freedate", DateUtil.getCurrentDateUSFormat());
							condtnBlock.put("assetid", assetId);
							nullCondtnBlock.add("asset_freedate");
							status = new AssetUniversalHelper().updateTableColNull("asset_allotment_log", data4Update, condtnBlock, nullCondtnBlock, connectionSpace);
						}
						else if (freeORReAllot != null && freeORReAllot.equals("reAlloted"))
						{
							actionMsg = "Re-allot Successfully!!!";
							data4Update = new LinkedHashMap<String, Object>();
							Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
							List<String> nullCondtnBlock = new ArrayList<String>();
							data4Update.put("asset_freedate", DateUtil.getCurrentDateUSFormat());
							condtnBlock.put("assetid", assetId);
							nullCondtnBlock.add("asset_freedate");
							status = new AssetUniversalHelper().updateTableColNull("asset_allotment_log", data4Update, condtnBlock, nullCondtnBlock, connectionSpace);
							if (status)
							{
								status = cbt.insertIntoTable("asset_allotment_log", insertData, connectionSpace);
							}
						}
						else
						{
							status = cbt.insertIntoTable("asset_allotment_log", insertData, connectionSpace);
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
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addAssetAllotment of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createFreeReallotPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String deptHierarchy = (String) session.get("userDeptLevel");
				List<GridDataPropertyView> allotmentColumnList = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				reallotColumnMap = new ArrayList<ConfigurationUtilBean>();
				commonDDMap1 = new ArrayList<ConfigurationUtilBean>();
				ConfigurationUtilBean obj;
				if (assetColumnList != null && assetColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						if (gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
						{
							obj = new ConfigurationUtilBean();
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							obj.setEditable(true);
							reallotColumnMap.add(obj);
						}
					}
				}
				
				obj = new ConfigurationUtilBean();
				obj.setKey("dept");
				obj.setValue("Department");
				obj.setEditable(true);
				reallotColumnMap.add(obj);

				obj = new ConfigurationUtilBean();
				obj.setKey("empid");
				obj.setValue("Employee Name");
				obj.setEditable(true);
				reallotColumnMap.add(obj);

				obj = new ConfigurationUtilBean();
				obj.setKey("mobile");
				obj.setValue("Mobile No");
				obj.setEditable(true);
				reallotColumnMap.add(obj);

				for (GridDataPropertyView gdp : allotmentColumnList)
				{
					if (gdp.getColomnName().equalsIgnoreCase("assetid"))
					{
						serialNoActive = true;
						headingSerialNo = gdp.getHeadingName().toString();
						serialNoList = new LinkedHashMap<Integer, String>();
						try
						{
							ConfigurationUtilBean obj1 = new ConfigurationUtilBean();
							List slnoList = null;
							String query = "SELECT id,assetname FROM asset_detail where flag=1 and id IN(select assetid from asset_allotment_detail  where flag=1)";
							slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (slnoList != null && slnoList.size() > 0)
							{
								for (Object obj2 : slnoList)
								{
									Object[] object = (Object[]) obj2;
									serialNoList.put((Integer) object[0], object[1].toString());
								}
							}
							obj1.setKey(gdp.getColomnName());
							obj1.setValue(headingSerialNo);
							obj1.setValidationType(gdp.getValidationType());
							obj1.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj1.setMandatory(true);
							}
							else
							{
								obj1.setMandatory(false);
							}
							commonDDMap1.add(obj1);
						}
						catch (Exception e)
						{
							log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createFreeReallotPage of class " + getClass(), e);
							e.printStackTrace();
						}
					}
				}
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

	public String beforeAllotView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getColumn4View("mapped_allotment_master", "allotment_master");

				//getColumn4View("mapped_asset_reminder", "asset_reminder");
				AssetUniversalAction AUA= new AssetUniversalAction();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				
				assetDropMap=AUA.getAssetConfig();
				assetTypeList=AUA.assetTypeList();
				
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAllotView of class " + getClass(), e);
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
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("assetname");
		gpv.setHeadingName("Asset Name");
		gpv.setHideOrShow("false");
		masterViewProp.add(gpv);
		
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("outletid");
		gpv.setHeadingName("Outlet Name");
		gpv.setHideOrShow("false");
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("deptid");
		gpv.setHeadingName("Dept Name");
		gpv.setHideOrShow("false");
		masterViewProp.add(gpv);

		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("empname");
		gpv.setHeadingName("Employee Name");
		gpv.setHideOrShow("false");
		masterViewProp.add(gpv);

		System.out.println("masterViewProp >> "+masterViewProp.size());

	}

	@SuppressWarnings("unchecked")
	public String viewAllotment()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_allotment_detail");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					
					query.append(" SELECT allot.id,asd.assetname,associate.associateName,dept.deptName,allot.contactid ");
					query.append(" FROM asset_allotment_detail AS allot INNER JOIN asset_detail AS asd ON asd.id=allot.assetid ");
					query.append(" INNER JOIN associate_basic_data AS associate ON associate.id=allot.outletid ");
					query.append(" LEFT JOIN department AS dept ON dept.id=allot.deptid");
					String app = "asd.";
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("") && getSearchField().equalsIgnoreCase("Search")) 
					{
						query.append(" where asd.assetname='"+getSearchString()+"' OR asd.serialno='"+getSearchString()+"' OR asd.mfgserialno='"+getSearchString()+"'");
					} 
					else if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(app + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(app + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(app + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(app + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(app + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + app + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + app + getSidx() + " DESC");
						}
					}
					else
					{
						query.append(" order by asd.assetname");
					}
					System.out.println("Query string "+query.toString());
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Map<String, Object> tempMap=null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							tempMap.put("id",CUA.getValueWithNullCheck(obdata[0]));
							tempMap.put("assetname",CUA.getValueWithNullCheck(obdata[1]));
							tempMap.put("outletid",CUA.getValueWithNullCheck(obdata[2]));
							tempMap.put("deptid",CUA.getValueWithNullCheck(obdata[3]));
							tempMap.put("empname",CUA.getValueWithNullCheck(obdata[4]));
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAllotment of class " + getClass(), e);
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
	public String viewTotalAllotment()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_allotment_detail");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					StringBuilder columnName = new StringBuilder("");
					List fieldNames = Configuration.getColomnList("mapped_allotment_master", accountID, connectionSpace, "allotment_master");
					List finalFieldName=new ArrayList();
					int i = 0;
					boolean floorFlag=false;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						query.append("select ");
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if(obdata.toString().equalsIgnoreCase("dept_subdept"))
									{
										columnName.append("dept.deptName,");
										finalFieldName.add("deptName");
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("assetid"))
									{
										columnName.append("asd.assetname,");
										finalFieldName.add("assetname");
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("employeeid"))
									{
										columnName.append("emp.empName,");
										finalFieldName.add("empName");
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("supporttype"))
									{
										columnName.append("suprtcatg.category,");
										finalFieldName.add("supporttype");
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("sublocation"))
									{
										columnName.append("floor.floorname,");
										finalFieldName.add("floorname");
										i++;
										
										columnName.append("subfloor.subfloorname,");
										finalFieldName.add("subfloorname");
										i++;
										floorFlag=true;
									}
									else if(!obdata.toString().equalsIgnoreCase("deptHierarchy") && !obdata.toString().equalsIgnoreCase("date") && !obdata.toString().equalsIgnoreCase("time") && !obdata.toString().equalsIgnoreCase("userName") && !obdata.toString().equalsIgnoreCase("flag") && !obdata.toString().equalsIgnoreCase("status"))
									{
										if(obdata.toString().equalsIgnoreCase("returnon"))
										{
											columnName.append("allot.asset_freedate,");
											finalFieldName.add(obdata.toString());
											i++;
										}
										else
										{
											columnName.append("allot."+obdata.toString() + ",");
											finalFieldName.add(obdata.toString());
											i++;
										}
										
									}
								}
								else 
								{
									if(obdata.toString().equalsIgnoreCase("returnon"))
									{
										columnName.append("allot.asset_freedate,");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else
									{
										columnName.append("allot."+obdata.toString() + ",");
										finalFieldName.add(obdata.toString());
										i++;
									}
								}
							}
						}
					}
					
					query.append(columnName.substring(0, columnName.toString().length()-1));
					query.append(" from asset_allotment_log as allot");
					query.append(" inner join department as dept on dept.id=allot.dept_subdept");
					query.append(" inner join compliance_contacts as cc on cc.id=allot.employeeid");
					query.append(" inner join employee_basic as emp on emp.id=cc.emp_id ");
					if(floorFlag)
					{
						query.append(" inner join buddy_sub_floor_info as subfloor on subfloor.id=allot.sublocation ");
						query.append(" inner join buddy_floor_info as floor on floor.id=subfloor.floorname1 ");
					}
					query.append(" inner join asset_detail as asd on asd.id=allot.assetid where assetid="+assetId);
					query.append(" order by asd.assetname");
					System.out.println("Query string "+query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Object[] obdata=null;
						Map<String, Object> tempMap=null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							for (int k = 0; k < finalFieldName.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
									{
										tempMap.put(finalFieldName.get(k).toString(), (Integer) obdata[k]);
									}
									else if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										tempMap.put(finalFieldName.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else
									{
										tempMap.put(finalFieldName.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									System.out.println(fieldNames.get(k).toString());
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAllotment of class " + getClass(), e);
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
	public String modifyAllotment()
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
					StringBuffer columnBuffer = new StringBuffer("");
					StringBuffer dataBuffer = new StringBuffer("");
					Map<String, String> logData = new LinkedHashMap<String, String>();
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
							else
							{
								if (parmName.equalsIgnoreCase("issueon") || parmName.equalsIgnoreCase("returnon"))
								{
									wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
								}
								else
								{
									wherClause.put(parmName, paramValue);
								}

								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}

					}
					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "asset_allotment_detail");
					logData.put("update_type", "Modify");
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);

					condtnBlock.put("id", getId());
					ProcurementAction pa = new ProcurementAction();
					StringBuilder updateQuery = pa.updateQuery("asset_allotment_detail", wherClause, condtnBlock);
					cbt.updateTableColomn(connectionSpace, updateQuery);
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
					cbt.deleteAllRecordForId("asset_allotment_detail", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyAllotment of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	//New Code For Asset Allotment
	
	@SuppressWarnings("unchecked")
	public String getOutletDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				outletTypeList = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query="SELECT id,associateType FROM associatetype ORDER BY associateType";
				List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						outletTypeList.put(object[0].toString(), object[1].toString());
					}
				}
				returnResult=SUCCESS;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				returnResult=ERROR;
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	
	
	public String getAssetColumn4View()
	{
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			for (GridDataPropertyView gdp : statusColName)
			{
				if (!gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("dept_subdept") 
						&& !gdp.getColomnName().equals("astcategory") && !gdp.getColomnName().equals("assettype") 
						&& !gdp.getColomnName().equals("pono") && !gdp.getColomnName().equals("quantity") 
						&& !gdp.getColomnName().equals("totalamount") && !gdp.getColomnName().equals("vendorname") 
						&& !gdp.getColomnName().equals("receivedOn") && !gdp.getColomnName().equals("installon") 
						&& !gdp.getColomnName().equals("commssioningon") && !gdp.getColomnName().equals("expectedlife") 
						&& !gdp.getColomnName().equals("supportfrom") && !gdp.getColomnName().equals("supportfor") 
						&& !gdp.getColomnName().equals("mfgserialno"))
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
			return SUCCESS;
		}
		else
		{
			addActionMessage("Asset Detail Table Is Not properly Configured !");
			return ERROR;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String viewRemainAsset()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList=new ArrayList<Object>();
				if(outletId!=null && !outletId.equalsIgnoreCase("-1"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String query1="select count(*) from asset_detail where status='Active'";
					List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					
					if (dataCount != null && dataCount.size() > 0)
					{
						StringBuilder query = new StringBuilder("");
						StringBuilder columnName = new StringBuilder("");
						query.append("select ");
						List fieldNames = Configuration.getColomnList("mapped_asset_master", accountID, connectionSpace, "asset_master");
						List finalFieldName=new ArrayList();
						
						int i = 0;
						if (fieldNames != null && fieldNames.size() > 0)
						{
							for (Iterator it = fieldNames.iterator(); it.hasNext();)
							{
								Object obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < fieldNames.size() - 1)
									{
										if (!obdata.toString().equals("deptHierarchy") && !obdata.toString().equals("dept_subdept") 
												&& !obdata.toString().equals("astcategory") && !obdata.toString().equals("assettype") 
												&& !obdata.toString().equals("pono") && !obdata.toString().equals("quantity") 
												&& !obdata.toString().equals("totalamount") && !obdata.toString().equals("vendorname") 
												&& !obdata.toString().equals("receivedOn") && !obdata.toString().equals("installon") 
												&& !obdata.toString().equals("commssioningon") && !obdata.toString().equals("expectedlife") 
												&& !obdata.toString().equals("supportfrom") && !obdata.toString().equals("supportfor") 
												&& !obdata.toString().equals("mfgserialno"))
										{
											columnName.append("asd."+obdata.toString() + ",");
											finalFieldName.add(obdata.toString());
											i++;
										}
									}
									else 
									{
										columnName.append("asd."+obdata.toString());
										finalFieldName.add(obdata.toString());
										i++;
									}
								}
							}
						}
						query.append(columnName.substring(0, columnName.toString().length()-1));
						query.append(" FROM asset_detail AS asd WHERE asd.flag=1");
						query.append(" AND asd.id NOT IN(SELECT allot.assetid FROM asset_allotment_detail AS allot WHERE allot.flag=1)");
						query.append(" ORDER BY asd.serialno ASC");
						
						System.out.println("query2 :"+query);
						
						List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						
						if (data != null && data.size() > 0)
						{
							List<Object> tempList = new ArrayList<Object>();
							Object[] obdata=null;
							Map<String, Object> tempMap=null;
							for (Iterator it = data.iterator(); it.hasNext();)
							{
								obdata = (Object[]) it.next();
								tempMap = new HashMap<String, Object>();
								for (int k = 0; k<finalFieldName.size(); k++)
								{
									if (obdata[k] != null)
									{
										if (k == 0)
										{
											tempMap.put(finalFieldName.get(k).toString(), (Integer) obdata[k]);
										}
										else if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											tempMap.put(finalFieldName.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else
										{
											tempMap.put(finalFieldName.get(k).toString(), obdata[k].toString());
										}
									}
									else
									{
										tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
								}
								tempList.add(tempMap);
							}
							setMasterViewList(tempList);
						}
	
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
	public String createAllotmentPage()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> allotColumnList = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
				allotmentColumnMap = new ArrayList<ConfigurationUtilBean>();
				dateColumnMap = new ArrayList<ConfigurationUtilBean>();
				commonDDMap = new ArrayList<ConfigurationUtilBean>();
				allotmentRadio = new ArrayList<ConfigurationUtilBean>();
				allottoFlagValue = new LinkedHashMap<String, String>();
				outletMappedDept=new LinkedHashMap<String, String>();
				if (allotColumnList != null && allotColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : allotColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date")
								&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("maplevel") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept")
								&& !gdp.getColomnName().equalsIgnoreCase("flag"))
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
							allotmentColumnMap.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("deptid"))
							{
								obj.setKey(gdp.getColomnName());
								obj.setValue(gdp.getHeadingName());
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
							else if (gdp.getColomnName().equalsIgnoreCase("contactid"))
							{
								String empHeading = new AssetCommonAction().getMasterFieldName("employee_basic_configuration", "empName");
								try
								{
									obj.setKey(gdp.getColomnName());
									obj.setValue(empHeading);
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
									e.printStackTrace();
								}
							}
						}
						if (gdp.getColType().trim().equalsIgnoreCase("Time"))
						{
							if (gdp.getColomnName().equalsIgnoreCase("issueon") || gdp.getColomnName().equalsIgnoreCase("issueat"))
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
						if(gdp.getColType().trim().equalsIgnoreCase("R"))
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
							allotmentRadio.add(obj);
						}
					}
					System.out.println("Outlet Id is "+outletId);
					if(outletId!=null)
					{
						List dataList = getMappedDeptToOutlet(outletId, cbt);
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null && object[1]!=null)
								{
									outletMappedDept.put(object[0].toString(), object[1].toString());
								}
							}
						}
					}
				}
				if(allotmentRadio!=null && allotmentRadio.size()>0)
				{
					allottoFlagValue.put("O", "Outlet  ");
					allottoFlagValue.put("OD", "Department  ");
					allottoFlagValue.put("ODE", "Employee");
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
	
	public String assetGroupAllotment()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				boolean saveStatus=false;
				Map<String, Object> data4Update = new LinkedHashMap<String, Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String assetId = null,actionMsg=" Alloted Successfully!!!";
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
				if (statusColName != null && statusColName.size() > 0)
				{

					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					List<TableColumes> allotLogTableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					boolean avilStatus = false;
					boolean returnOnStatus = false;
					for (GridDataPropertyView gdp : statusColName)
					{
						// set column name for create asset_allotment_detail
						// table
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						// end

						// set column name for create asset_allotment_log table
						TableColumes ob2 = new TableColumes();
						ob2.setColumnname(gdp.getColomnName());
						ob2.setDatatype("varchar(255)");
						ob2.setConstraint("default NULL");
						allotLogTableColume.add(ob2);
						// end

						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("flag"))
							avilStatus = true;
					}
					
					TableColumes ob2 = new TableColumes();
					ob2.setColumnname("asset_freedate");
					ob2.setDatatype("varchar(255)");
					ob2.setConstraint("default NULL");
					allotLogTableColume.add(ob2);
					// create asset_allotment_detail table
					cbt.createTable22("asset_allotment_detail", tableColume, connectionSpace);
					// end

					// create asset_allotment_detail table
					cbt.createTable22("asset_allotment_log", allotLogTableColume, connectionSpace);
					// end

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					String deptLevel = (String) session.get("userDeptLevel");
					String subdept_dept = null;
					// String usagesType=null;
					String returnDate = null;
					String freeORReAllot = null;
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						String parmName=null;
						String paramValue=null;
						while (it.hasNext())
						{
							parmName = it.next().toString();
							paramValue = request.getParameter(parmName);
							if (parmName.equalsIgnoreCase("returnon"))
							{
								returnDate = paramValue;
								returnOnStatus = true;
							}

							if (parmName.equalsIgnoreCase("freeORReAllot"))
							{
								freeORReAllot = paramValue;
							}

							if (!parmName.equalsIgnoreCase("deptid") && !parmName.equalsIgnoreCase("contactid") && !parmName.equalsIgnoreCase("__multiselect_contactid") && !parmName.equalsIgnoreCase("assetid") && !parmName.equalsIgnoreCase("returnon") && !parmName.equalsIgnoreCase("freeORReAllot"))
							{
								if (parmName.equalsIgnoreCase("issueon"))
								{
									ob = new InsertDataTable();
									ob.setColName("issueon");
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
									data4Update.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
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
							if (parmName.equalsIgnoreCase("assetid"))
							{
								assetId = paramValue;
								assetId=assetId+",";
							}
						}
					}

					if (userTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);
						data4Update.put("userName", userName);
					}
					if (dateTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
						data4Update.put("date", DateUtil.getCurrentDateUSFormat());
					}
					if (timeTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						data4Update.put("time", DateUtil.getCurrentTimeHourMin());
					}
					if (avilStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("flag");
						ob.setDataName("1");
						insertData.add(ob);
						data4Update.put("flag", "1");
					}
					if (returnOnStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("returnon");
						ob.setDataName(DateUtil.convertDateToUSFormat(returnDate));
						insertData.add(ob);
						data4Update.put("returnon", DateUtil.convertDateToUSFormat(returnDate));
					}
					String str[]=request.getParameterValues("contactid");
					StringBuilder contBuilder=new StringBuilder();
					if(str!=null && str.length>0)
					{
						for (int i = 0; i < str.length; i++)
						{
							contBuilder.append(str[i]+",");
						}
						
					}
					
					
					if(allottoflag!=null && allottoflag.equalsIgnoreCase("ODE"))
					{
						ob = new InsertDataTable();
						ob.setColName("deptid");
						ob.setDataName(request.getParameter("deptid"));
						insertData.add(ob);
						data4Update.put("deptid", request.getParameter("deptid"));
						
						ob = new InsertDataTable();
						ob.setColName("contactid");
						ob.setDataName(contBuilder.toString());
						insertData.add(ob);
						data4Update.put("contactid", contBuilder.toString());
					}
					else if(allottoflag!=null && allottoflag.equalsIgnoreCase("OD"))
					{
						ob = new InsertDataTable();
						ob.setColName("deptid");
						ob.setDataName(request.getParameter("deptid"));
						insertData.add(ob);
						data4Update.put("deptid", request.getParameter("deptid"));
					}
					
					
					String assetIdArr[]=assetId.split(",");
					if(assetIdArr!=null && assetIdArr.length>0)
					{
						for (int i = 0; i < assetIdArr.length; i++)
						{
							ob = new InsertDataTable();
							ob.setColName("assetid");
							ob.setDataName(assetIdArr[i]);
							insertData.add(ob);
							data4Update.put("userName", userName);
							
							String query = "SELECT id FROM asset_allotment_detail where assetid=" + assetIdArr[i];
							List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
								condtnBlock.put("assetid", assetIdArr[i]);
								if (freeORReAllot != null && freeORReAllot.equals("free"))
								{
									data4Update = new LinkedHashMap<String, Object>();
									data4Update.put("flag", "0");
									saveStatus = new HelpdeskUniversalHelper().updateTableColomn("asset_allotment_detail", data4Update, condtnBlock, connectionSpace);
								}
								else
								{
									System.out.println("Inside Reallot");
									saveStatus = new HelpdeskUniversalHelper().updateTableColomn("asset_allotment_detail", data4Update, condtnBlock, connectionSpace);
								}
							}
							else
							{
								saveStatus = cbt.insertIntoTable("asset_allotment_detail", insertData, connectionSpace);
							}

							if (saveStatus)
							{
								if (freeORReAllot != null && freeORReAllot.equals("free"))
								{
									actionMsg = "Free Successfully!!!";
									data4Update = new LinkedHashMap<String, Object>();
									Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
									List<String> nullCondtnBlock = new ArrayList<String>();
									data4Update.put("asset_freedate", DateUtil.getCurrentDateUSFormat());
									condtnBlock.put("assetid", assetIdArr[i]);
									nullCondtnBlock.add("asset_freedate");
									saveStatus = new AssetUniversalHelper().updateTableColNull("asset_allotment_log", data4Update, condtnBlock, nullCondtnBlock, connectionSpace);
								}
								else if (freeORReAllot != null && freeORReAllot.equals("reAlloted"))
								{
									actionMsg = "Re-allot Successfully!!!";
									data4Update = new LinkedHashMap<String, Object>();
									Map<String, Object> condtnBlock = new LinkedHashMap<String, Object>();
									List<String> nullCondtnBlock = new ArrayList<String>();
									data4Update.put("asset_freedate", DateUtil.getCurrentDateUSFormat());
									condtnBlock.put("assetid", assetIdArr[i]);
									nullCondtnBlock.add("asset_freedate");
									saveStatus = new AssetUniversalHelper().updateTableColNull("asset_allotment_log", data4Update, condtnBlock, nullCondtnBlock, connectionSpace);
									if (saveStatus)
									{
										saveStatus = cbt.insertIntoTable("asset_allotment_log", insertData, connectionSpace);
									}
								}
								else
								{
									saveStatus = cbt.insertIntoTable("asset_allotment_log", insertData, connectionSpace);
								}
							}
							insertData.remove(insertData.size()-1);
						}
					}
					
				}
				if (saveStatus)
				{
					addActionMessage("Asset " + actionMsg);
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
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
	
	
	public List getMappedDeptToOutlet(String outletId, CommonOperInterface cbt)
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT distinct dept.id,dept.deptName FROM department AS dept ");
		query.append(" INNER JOIN associate_contact_data AS ACD ON ACD.department=dept.id ");
		query.append(" WHERE ACD.associateName="+outletId+" ORDER BY dept.deptName");
		System.out.println("Get dept Query is "+query.toString());
		List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return dataList;
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

	public Map<Integer, String> getSerialNoList()
	{
		return serialNoList;
	}

	public void setSerialNoList(Map<Integer, String> serialNoList)
	{
		this.serialNoList = serialNoList;
	}

	public List<ConfigurationUtilBean> getReallotColumnMap()
	{
		return reallotColumnMap;
	}

	public void setReallotColumnMap(List<ConfigurationUtilBean> reallotColumnMap)
	{
		this.reallotColumnMap = reallotColumnMap;
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

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<ConfigurationUtilBean> getCommonDDMap1()
	{
		return commonDDMap1;
	}

	public void setCommonDDMap1(List<ConfigurationUtilBean> commonDDMap1)
	{
		this.commonDDMap1 = commonDDMap1;
	}

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}
	public Map<Integer, String> getAssetTypeList() {
		return assetTypeList;
	}

	public void setAssetTypeList(Map<Integer, String> assetTypeList) {
		this.assetTypeList = assetTypeList;
	}

	public List<ConfigurationUtilBean> getAssetDropMap() {
		return assetDropMap;
	}

	public void setAssetDropMap(List<ConfigurationUtilBean> assetDropMap) {
		this.assetDropMap = assetDropMap;
	}
	public String getOutletId()
	{
		return outletId;
	}
	public void setOutletId(String outletId)
	{
		this.outletId = outletId;
	}

	public Map<String, String> getOutletTypeList()
	{
		return outletTypeList;
	}

	public void setOutletTypeList(Map<String, String> outletTypeList)
	{
		this.outletTypeList = outletTypeList;
	}

	public List<ConfigurationUtilBean> getAllotmentRadio()
	{
		return allotmentRadio;
	}

	public void setAllotmentRadio(List<ConfigurationUtilBean> allotmentRadio)
	{
		this.allotmentRadio = allotmentRadio;
	}

	public List<ConfigurationUtilBean> getAllotmentDropDown()
	{
		return allotmentDropDown;
	}

	public void setAllotmentDropDown(List<ConfigurationUtilBean> allotmentDropDown)
	{
		this.allotmentDropDown = allotmentDropDown;
	}

	public List<ConfigurationUtilBean> getAllotmentColumnMap()
	{
		return allotmentColumnMap;
	}

	public void setAllotmentColumnMap(List<ConfigurationUtilBean> allotmentColumnMap)
	{
		this.allotmentColumnMap = allotmentColumnMap;
	}

	public List<ConfigurationUtilBean> getCommonDDMap()
	{
		return commonDDMap;
	}

	public void setCommonDDMap(List<ConfigurationUtilBean> commonDDMap)
	{
		this.commonDDMap = commonDDMap;
	}

	public List<ConfigurationUtilBean> getDateColumnMap()
	{
		return dateColumnMap;
	}

	public void setDateColumnMap(List<ConfigurationUtilBean> dateColumnMap)
	{
		this.dateColumnMap = dateColumnMap;
	}

	public Map<String, String> getAllottoFlagValue()
	{
		return allottoFlagValue;
	}

	public void setAllottoFlagValue(Map<String, String> allottoFlagValue)
	{
		this.allottoFlagValue = allottoFlagValue;
	}

	public String getO()
	{
		return O;
	}

	public void setO(String o)
	{
		O = o;
	}

	public Map<String, String> getOutletMappedDept()
	{
		return outletMappedDept;
	}

	public void setOutletMappedDept(Map<String, String> outletMappedDept)
	{
		this.outletMappedDept = outletMappedDept;
	}

	public String getAllottoflag()
	{
		return allottoflag;
	}

	public void setAllottoflag(String allottoflag)
	{
		this.allottoflag = allottoflag;
	}
	
}
