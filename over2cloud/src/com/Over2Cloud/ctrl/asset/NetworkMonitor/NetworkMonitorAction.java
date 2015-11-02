package com.Over2Cloud.ctrl.asset.NetworkMonitor;

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
import org.hibernate.SessionFactory;

import sun.util.logging.resources.logging;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.asset.AssetCommonAction;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class NetworkMonitorAction extends ActionSupport implements ServletRequestAware
{
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String accountID = (String) session.get("accountid");
	String userName = (String) session.get("uName");
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> netMonitorColumnMap = null;
	private List<ConfigurationUtilBean> assetColumnMap = null;
	private List<ConfigurationUtilBean> empDetailColumnMap = null;
	private Map<String,String> contMappedDeptList;
	private List<ConfigurationUtilBean> commonDropDownList;
	private Map<Integer, String> serialNoList=null;
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
	private List tableHeading=null;
	private List tableSubHeading=null;
	private List partitaionData=null;
	private String machineId;
	List<AssetDashboardBean> softwareList=null;
	private String restrictedexe;
	private boolean restrictedFlag;
	List<ConfigurationUtilBean> assetDropMap=null;
	private Map<Integer, String>  vendorList, supportTypeList,assetTypeList;
	
	public String createNetMontorPage()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				netMonitorColumnMap=new ArrayList<ConfigurationUtilBean>();
				assetColumnMap=new ArrayList<ConfigurationUtilBean>();
				empDetailColumnMap=new ArrayList<ConfigurationUtilBean>();
				contMappedDeptList=new LinkedHashMap<String, String>();
				commonDropDownList=new ArrayList<ConfigurationUtilBean>();
				serialNoList=new LinkedHashMap<Integer, String>();
				AssetUniversalAction AUA=new AssetUniversalAction();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				List<GridDataPropertyView> networkColumnList = Configuration.getConfigurationData("mapped_network_monitor_config", accountID, connectionSpace, "", 0, "table_name", "network_monitor_config");
				ConfigurationUtilBean obj;
				AssetCommonAction ACA=new AssetCommonAction();
				assetDropMap=AUA.getAssetConfig();
				assetTypeList=AUA.assetTypeList();
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : networkColumnList)
					{
						obj=new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("workstatus") && !gdp.getColomnName().equalsIgnoreCase("alertstatus"))
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
							netMonitorColumnMap.add(obj);
						}
						else if(gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							if(gdp.getColomnName().equalsIgnoreCase("assetid"))
							{
								List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
								if(assetColumnList!=null && assetColumnList.size()>0)
								{
									for (GridDataPropertyView gdp1 : assetColumnList)
									{
										obj = new ConfigurationUtilBean();
										if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
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
											assetColumnMap.add(obj);
										}
									}
									String query = "SELECT id,assetname FROM asset_detail WHERE status='Active' ORDER BY serialno ASC";
									List slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
									if (slnoList != null && slnoList.size() > 0)
									{
										for (Object obj1 : slnoList)
										{
											Object[] object = (Object[]) obj1;
											serialNoList.put((Integer) object[0], object[1].toString());
										}
									}
								}
							}
							/*else if(gdp.getColomnName().equalsIgnoreCase("contactid"))
							{
								contMappedDeptList=new AssetUniversalAction().getContactMappedDept(connectionSpace);
								obj = new ConfigurationUtilBean();
								obj.setKey("deptName");
								obj.setValue(ACA.getMasterFieldName("dept_config_param", "deptName"));
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
								empDetailColumnMap.add(obj);
								
								
								obj = new ConfigurationUtilBean();
								obj.setKey("contactid");
								obj.setValue(ACA.getMasterFieldName("employee_basic_configuration", "empName"));
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
								empDetailColumnMap.add(obj);
							}*/
							else if(gdp.getColomnName().equalsIgnoreCase("scanfor"))
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
								commonDropDownList.add(obj);
							}
						}
					}
				}
				System.out.println(netMonitorColumnMap.size());
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
	public String AddIpPort()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> networkColumnList = Configuration.getConfigurationData("mapped_network_monitor_config", accountID, connectionSpace, "", 0, "table_name", "network_monitor_config");
				if (networkColumnList != null && networkColumnList.size() > 0)
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
					for (GridDataPropertyView gdp : networkColumnList)
					{
						if(!gdp.getColomnName().equalsIgnoreCase("contactid"))
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
					}
					if(tableColume!=null && tableColume.size()>0)
					{
						cbt.createTable22("network_monitor_details", tableColume, connectionSpace);
						tableColume.clear();
					}
					/*ob1 = new TableColumes();
					ob1.setColumnname("net_monitor_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("contact_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					if(tableColume!=null && tableColume.size()>0)
					{
						cbt.createTable22("network_monitor_contact", tableColume, connectionSpace);
						tableColume.clear();
					}*/
					
					ob1 = new TableColumes();
					ob1.setColumnname("net_monitor_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("service_start_date");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("service_start_time");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("service_stop_date");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("service_stop_time");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					if(tableColume!=null && tableColume.size()>0)
					{
						cbt.createTable22("network_monitor_log", tableColume, connectionSpace);
					}
					
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("contactid"))
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
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
					}
					int maxId = cbt.insertDataReturnId("network_monitor_details", insertData, connectionSpace);
					/*if(maxId!=0)
					{
						String contactId[]=request.getParameterValues("contactid");
						if(contactId.length>0)
						{
							for(int count=0;count<contactId.length;count++)
							{
								insertData.clear();
								
								ob = new InsertDataTable();
								ob.setColName("net_monitor_id");
								ob.setDataName(maxId);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("contact_id");
								ob.setDataName(contactId[count]);
								insertData.add(ob);
								
								cbt.insertDataReturnId("network_monitor_contact", insertData, connectionSpace);
							}
						}
					}*/
					
				}
				addActionMessage("Data Registered Successfully!!!");
				return SUCCESS;
			}
			catch(Exception e)
			{
				addActionMessage("Oops There is some error in data!");
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
	public String beforeViewNetworkMonitor()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				AssetUniversalAction AUA= new AssetUniversalAction();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				
				assetDropMap=AUA.getAssetConfig();
				assetTypeList=AUA.assetTypeList();
				
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("serviceStatus");
				gpv.setHeadingName("Status");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				gpv.setFormatter("serviceStatus");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("contactid");
				gpv.setHeadingName("Remind To");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);

				List<GridDataPropertyView> networkColumnList = Configuration.getConfigurationData("mapped_network_monitor_config", accountID, connectionSpace, "", 0, "table_name", "network_monitor_config");
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : networkColumnList)
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
				for(GridDataPropertyView gpv1:masterViewProp)
				{
					System.out.println(gpv1.getColomnName());
				}
				session.put("masterViewProp", masterViewProp);
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
	public String ViewNetworkMonitor()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> networkColumnList = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				List finalFieldName=new ArrayList();
				query.append("SELECT ");
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					int i = 0;
					for (GridDataPropertyView gpv : networkColumnList)
					{
						if (i < (networkColumnList.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("asd.id,");
								finalFieldName.add(gpv.getColomnName());
							}
							else if(!gpv.getColomnName().equalsIgnoreCase("serviceStatus") && !gpv.getColomnName().equalsIgnoreCase("contactid") && !gpv.getColomnName().equalsIgnoreCase("assetid"))
							{
								query.append("nmd."+gpv.getColomnName().toString() + ",");
								finalFieldName.add(gpv.getColomnName());
							}
							else if(gpv.getColomnName().equalsIgnoreCase("assetid"))
							{
								query.append("asd.assetname,");
								finalFieldName.add(gpv.getColomnName());
							}
							/*else if(gpv.getColomnName().equalsIgnoreCase("contactid"))
							{
								finalFieldName.add(gpv.getColomnName());
								System.out.println("contactid");
							}*/
						}
						else
						{
							query.append("nmd."+gpv.getColomnName().toString());
							finalFieldName.add(gpv.getColomnName());
						}
						i++;
					}
					query.append(" FROM network_monitor_details AS nmd");
					query.append(" INNER JOIN asset_detail AS asd ON asd.id=nmd.assetid");
					
					String alis="asd";
					System.out.println(" SEARCH FIELD ==== "+getSearchField());
					System.out.println("SEARCH  STRING     "+getSearchString());
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("") && getSearchField().equalsIgnoreCase("Search")) 
					{
						query.append(" where asd.assetname='"+getSearchString()+"' OR asd.serialno='"+getSearchString()+"' OR asd.mfgserialno='"+getSearchString()+"'");
					} 
					else if(getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(alis+"." + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(alis+"." + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(alis+"." + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(alis+"." + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(alis+"." + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
				/*	if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{ 
						query.append(" where ");
						
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(alis+"." + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(alis+"." + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(alis+"." + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(alis+"." + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(alis+"." + getSearchField() + " like '%" + getSearchString() + "'");
						}

					}*/
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(alis+"." + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(alis+"." + getSidx() + " DESC");
						}
					}
					System.out.println("query :"+query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					
					if (data != null && data.size()>0)
					{
						setRecords(data.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						Object[] obdata =null;
						Map<String, Object> tempMap=null;
						List<Object> tempList = new ArrayList<Object>();
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
										System.out.println("K value is "+k);
										query=new StringBuilder();
										tempMap.put(finalFieldName.get(k).toString(), (Integer) obdata[k]);
										tempMap.put("serviceStatus", "Status");
										
										query.append(" SELECT emp.empName FROM network_monitor_contact AS nmc ");
										query.append(" INNER JOIN compliance_contacts AS cc ON cc.id=nmc.contactid ");
										query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE nmc.assetid="+obdata[k].toString());
										
										
										List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
										System.out.println(" >>>> "+query.toString());
										query=new StringBuilder();
										if(dataList!=null && dataList.size()>0)
										{
											for(int count=0;count<dataList.size();count++)
											{
												query.append(dataList.get(count).toString()+", ");
											}
										}
										if(query!=null && !query.toString().equalsIgnoreCase(""))
										{
											tempMap.put("contactid",query.substring(0, query.length()-2));
										}
										else
										{
											tempMap.put("contactid","NA");
										}
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
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	public String beforeViewNetworkMonitorLog()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				List<GridDataPropertyView> networkColumnList = Configuration.getConfigurationData("mapped_network_monitor_config", accountID, connectionSpace, "", 0, "table_name", "network_monitor_config");
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : networkColumnList)
					{
						if(gdp.getColomnName().equalsIgnoreCase("assetid") || gdp.getColomnName().equalsIgnoreCase("ipaddress") || gdp.getColomnName().equalsIgnoreCase("port"))
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
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("service_start_date");
				gpv.setHeadingName("Service Start On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("service_start_time");
				gpv.setHeadingName("Service Start At");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("service_stop_date");
				gpv.setHeadingName("Service Stop On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("service_stop_time");
				gpv.setHeadingName("Service Stop At");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				session.put("masterViewProp", masterViewProp);
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
	public String ViewNetworkMonitorLog()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> networkColumnList = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				List finalFieldName=new ArrayList();
				query.append("SELECT ");
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					int i = 0;
					for (GridDataPropertyView gpv : networkColumnList)
					{
						if (i < (networkColumnList.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("nml.id AS nmlid,");
								finalFieldName.add(gpv.getColomnName());
							}
							else if(gpv.getColomnName().equalsIgnoreCase("ipaddress") || gpv.getColomnName().equalsIgnoreCase("port"))
							{
								query.append("nmd."+gpv.getColomnName()+",");
								finalFieldName.add(gpv.getColomnName());
							}
							else if(gpv.getColomnName().equalsIgnoreCase("assetid"))
							{
								query.append("asd.assetname,");
								finalFieldName.add(gpv.getColomnName());
								
							}
							else if(!gpv.getColomnName().equalsIgnoreCase("ipaddress") && !gpv.getColomnName().equalsIgnoreCase("port") && !gpv.getColomnName().equalsIgnoreCase("assetid"))
							{
								query.append("nml."+gpv.getColomnName()+",");
								finalFieldName.add(gpv.getColomnName());
							}
						}
						else
						{
							query.append("nml."+gpv.getColomnName());
							finalFieldName.add(gpv.getColomnName());
						}
						i++;
					}
					query.append(" FROM network_monitor_log AS nml");
					query.append(" INNER JOIN network_monitor_details AS nmd ON nmd.id=nml.net_monitor_id ");
					query.append(" INNER JOIN asset_detail AS asd ON asd.id=nmd.assetid");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size()>0)
					{
						setRecords(data.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						Object[] obdata =null;
						Map<String, Object> tempMap=null;
						List<Object> tempList = new ArrayList<Object>();
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
										query=new StringBuilder();
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
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	public String beforeViewMachineDetails()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				AssetUniversalAction AUA= new AssetUniversalAction();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				
				assetDropMap=AUA.getAssetConfig();
				assetTypeList=AUA.assetTypeList();
				
				
				
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("machine_id");
				gpv.setHeadingName("Machine_id");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("ip");
				gpv.setHeadingName("IP");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("os_name");
				gpv.setHeadingName("OS Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("systemManufacturer");
				gpv.setHeadingName("Manufacturer");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("systemModel");
				gpv.setHeadingName("System Model");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("BIOSVersion");
				gpv.setHeadingName("BIOS Version");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("windowsDirectory");
				gpv.setHeadingName("Directory");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("timeZone");
				gpv.setHeadingName("Time Zone");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("OSInstallDate");
				gpv.setHeadingName("OS Install Date");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("OSVersion");
				gpv.setHeadingName("OS Version");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("domain");
				gpv.setHeadingName("Domain");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("hd_serialno");
				gpv.setHeadingName("HD Serial");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("hd_partitaion");
				gpv.setHeadingName("HD Partitaion");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("viewPartitaion");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("ram_details");
				gpv.setHeadingName("RAM Details");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("process_list");
				gpv.setHeadingName("Process List");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("temp_file_size");
				gpv.setHeadingName("Temp Size");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("software_inventory");
				gpv.setHeadingName("Software Inventory");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("viewSoftwareInventory");
				masterViewProp.add(gpv);
				
				
				
				
				
				session.put("masterViewProp", masterViewProp);
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
	public String ViewMachineDetails()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> networkColumnList = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				List finalFieldName=new ArrayList();
				query.append("SELECT ");
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					int i = 0;
					for (GridDataPropertyView gpv : networkColumnList)
					{
						if (i < (networkColumnList.size() - 1))
						{
							query.append(gpv.getColomnName()+",");
							finalFieldName.add(gpv.getColomnName());
							
						}
						else
						{
							query.append(gpv.getColomnName());
							finalFieldName.add(gpv.getColomnName());
						}
						i++;
					}
					query.append(" FROM machine_details");
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{ 
						query.append(" where ");
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
					
					System.out.println(query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					System.out.println("Data size "+data.size());
					if (data != null && data.size()>0)
					{
						setRecords(data.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						Object[] obdata =null;
						Map<String, Object> tempMap=null;
						List<Object> tempList = new ArrayList<Object>();
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
										query=new StringBuilder();
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
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	public String viewPartitaionDetails()
	{
		try
		{
			tableHeading=new ArrayList();
			tableSubHeading=new ArrayList();
			partitaionData=new ArrayList();
			String query="SELECT hd_partitaion FROM machine_details WHERE id="+machineId;
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(data!=null && data.size()>0)
			{
				if(data.get(0)!=null)
				{
					String driveArray[]=data.get(0).toString().split(",");
					if(driveArray.length>0)
					{
						for(int i=0; i < driveArray.length; i++)
						{
							String driveDetailArray[]=driveArray[i].split("#");
							if(driveDetailArray.length>0)
							{
								tableHeading.add(driveDetailArray[0]+" Drive");
								for (int j = 1; j < driveDetailArray.length; j++)
								{
									partitaionData.add(driveDetailArray[j]);
								}
							}
						}
					}
				}
			}
			if(tableHeading!=null && tableHeading.size()>0)
			{
				for(int i=1;i<=tableHeading.size()*2;i++)
				{
					if(i%2==0)
					{
						tableSubHeading.add("Free");
					}
					else
					{
						tableSubHeading.add("Total");
					}
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
	
	@SuppressWarnings("unchecked")
	public String viewSoftwareInventory()
	{
		try
		{
			tableHeading=new ArrayList();
			softwareList=new ArrayList<AssetDashboardBean>();
			tableHeading.add("Node");
			tableHeading.add("Name");
			tableHeading.add("Version");
			
			String query="SELECT software_inventory FROM machine_details WHERE id="+machineId;
			
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			AssetDashboardBean ADB=null;
			if(data!=null && data.size()>0)
			{
				if(data.get(0)!=null)
				{
					String str=data.get(0).toString().substring(1,data.get(0).toString().length());
					String driveArray[]=str.split("#");
					if(driveArray.length>0)
					{
						for(int i=0; i < driveArray.length; i++)
						{
							ADB=new AssetDashboardBean();
							
							if(!driveArray[i].equalsIgnoreCase(" "))
							{
								String driveDetailArray[]=driveArray[i].split(",");
								
								if(driveDetailArray.length>0)
								{
									ADB.setNodeName(driveDetailArray[0]);
									ADB.setSoftwareName(driveDetailArray[1]);
									ADB.setVersion(driveDetailArray[2]);
								}
								softwareList.add(ADB);
							}
						}
					}
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
	
	
	public String createProcessRestrictionPage()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				netMonitorColumnMap=new ArrayList<ConfigurationUtilBean>();
				assetColumnMap=new ArrayList<ConfigurationUtilBean>();
				serialNoList=new LinkedHashMap<Integer, String>();
				AssetUniversalAction AUA=new AssetUniversalAction();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				ConfigurationUtilBean obj;
				assetDropMap=AUA.getAssetConfig();
				assetTypeList=AUA.assetTypeList();
				
				obj=new ConfigurationUtilBean();
				obj.setKey("assetid");
				obj.setValue("Asset Code");
				obj.setColType("D");
				obj.setEditable(true);
				obj.setMandatory(true);
				netMonitorColumnMap.add(obj);
				
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				if(assetColumnList!=null && assetColumnList.size()>0)
				{
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						obj = new ConfigurationUtilBean();
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
							assetColumnMap.add(obj);
						}
					}
				}
				
				obj=new ConfigurationUtilBean();
				obj.setKey("restrictedexe");
				obj.setValue("Exe Name");
				obj.setColType("T");
				obj.setEditable(true);
				obj.setMandatory(true);
				netMonitorColumnMap.add(obj);
				
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				TableColumes ob1=null;
				
				ob1 = new TableColumes();
				ob1.setColumnname("assetid");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("restrictedexe");
				ob1.setDatatype("text");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				if(tableColume!=null && tableColume.size()>0)
				{
					cbt.createTable22("restricted_exe_details", tableColume, connectionSpace);
					tableColume.clear();
				}
				
				String query = "SELECT id,serialno FROM asset_detail WHERE status='Active' AND id NOT IN(SELECT assetid FROM restricted_exe_details) ORDER BY serialno ASC";
				List slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (slnoList != null && slnoList.size() > 0)
				{
					for (Object obj1 : slnoList)
					{
						Object[] object = (Object[]) obj1;
						serialNoList.put((Integer) object[0], object[1].toString());
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
	
	public String AddRestrictedExe()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				String assetId = request.getParameter("assetid");
				
				//String restrictedExe=request.getParameter("restrictedexe");
				
				System.out.println("restrictedExe "+restrictedexe);
				StringBuilder builder=new StringBuilder();
				if(restrictedexe!=null && restrictedexe.length()>0)
				{
					restrictedexe=restrictedexe+",";
					String restrictedExe[]=restrictedexe.split(",");
					for (int i = 0; i < restrictedExe.length; i++)
					{
						if(!restrictedExe[i].equals(" "))
						{
							builder.append(restrictedExe[i]+",");
						}
					}
				}
				
				
				ob = new InsertDataTable();
				ob.setColName("assetid");
				ob.setDataName(assetId);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("restrictedexe");
				ob.setDataName(builder.toString());
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
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				status=cbt.insertIntoTable("restricted_exe_details", insertData, connectionSpace);
				if(status)
				{
					addActionMessage("Data Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
			}
			catch(Exception e)
			{
				addActionMessage("Oops There is some error in data!");
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
	public String beforeViewRestrictedExe()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("assetname");
				gpv.setHeadingName("Asset Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("serialno");
				gpv.setHeadingName("Asset Code");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("empName");
				gpv.setHeadingName("Alloted User");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("restrictedExe");
				gpv.setHeadingName("Restricted Exe");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setFormatter("restrictedExe");
				masterViewProp.add(gpv);
				
				session.put("masterViewProp", masterViewProp);
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
	public String ViewRestrictedExe()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> networkColumnList = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				List finalFieldName=new ArrayList();
				query.append("SELECT ");
				if (networkColumnList != null && networkColumnList.size() > 0)
				{
					int i = 0;
					for (GridDataPropertyView gpv : networkColumnList)
					{
						if (i < (networkColumnList.size() - 1))
						{
							if(gpv.getColomnName().equalsIgnoreCase("assetname") || gpv.getColomnName().equalsIgnoreCase("serialno"))
							{
								query.append("ast."+gpv.getColomnName()+",");
								finalFieldName.add(gpv.getColomnName());
							}
							else if(gpv.getColomnName().equalsIgnoreCase("empName"))
							{
								query.append("emp."+gpv.getColomnName()+",");
								finalFieldName.add(gpv.getColomnName());
							}
							else if(gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("resexe."+gpv.getColomnName()+",");
								finalFieldName.add(gpv.getColomnName());
							}
						}
						else
						{
							query.append("resexe."+gpv.getColomnName());
							finalFieldName.add(gpv.getColomnName());
						}
						i++;
					}
					query.append(" FROM restricted_exe_details AS resexe ");
					query.append(" LEFT JOIN asset_detail AS ast ON ast.id=resexe.assetid ");
					query.append(" LEFT JOIN asset_allotment_detail AS aad ON aad.assetid=ast.id ");
					query.append(" LEFT JOIN compliance_contacts AS contact ON contact.id=aad.employeeid ");
					query.append(" LEFT JOIN employee_basic AS emp ON emp.id=contact.emp_id ");
					System.out.println(query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					System.out.println("Data size "+data.size());
					if (data != null && data.size()>0)
					{
						setRecords(data.size());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						Object[] obdata =null;
						Map<String, Object> tempMap=null;
						List<Object> tempList = new ArrayList<Object>();
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
										query=new StringBuilder();
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
	
	@SuppressWarnings("unchecked")
	public String getRestrictedExe()
	{
		try
		{
			tableHeading=new ArrayList();
			softwareList=new ArrayList<AssetDashboardBean>();
			tableHeading.add("Restricted Exe Name");
			restrictedFlag=true;
			String query="SELECT restrictedexe FROM restricted_exe_details WHERE id="+id;
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			AssetDashboardBean ADB=null;
			if(data!=null && data.size()>0)
			{
				if(data.get(0)!=null)
				{
					String detailArray[]=data.get(0).toString().split(",");
					
					if(detailArray.length>0)
					{
						for (int i = 0; i < detailArray.length; i++)
						{
							ADB=new AssetDashboardBean();
							ADB.setSoftwareName(detailArray[0]);
							softwareList.add(ADB);
						}
					}
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
	
	public String CreateConfigServiceAlert()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				assetColumnMap=new ArrayList<ConfigurationUtilBean>();
				empDetailColumnMap=new ArrayList<ConfigurationUtilBean>();
				contMappedDeptList=new LinkedHashMap<String, String>();
				serialNoList=new LinkedHashMap<Integer, String>();
				AssetCommonAction ACA=new AssetCommonAction();
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				ConfigurationUtilBean obj;
				if(assetColumnList!=null && assetColumnList.size()>0)
				{
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						obj = new ConfigurationUtilBean();
						if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
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
							assetColumnMap.add(obj);
						}
					}
					String query = "SELECT id,assetname FROM asset_detail WHERE status='Active' ORDER BY serialno ASC";
					List slnoList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (slnoList != null && slnoList.size() > 0)
					{
						for (Object obj1 : slnoList)
						{
							Object[] object = (Object[]) obj1;
							serialNoList.put((Integer) object[0], object[1].toString());
						}
					}
				}
				
				contMappedDeptList=new AssetUniversalAction().getContactMappedDept(connectionSpace);
				obj = new ConfigurationUtilBean();
				obj.setKey("deptName");
				obj.setValue(ACA.getMasterFieldName("dept_config_param", "deptName"));
				obj.setColType("D");
				obj.setEditable(true);
				obj.setMandatory(true);
				empDetailColumnMap.add(obj);
				
				
				obj = new ConfigurationUtilBean();
				obj.setKey("contactid");
				obj.setValue(ACA.getMasterFieldName("employee_basic_configuration", "empName"));
				obj.setColType("D");
				obj.setEditable(true);
				obj.setMandatory(true);
				empDetailColumnMap.add(obj);
				
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
	
	public String configServiceAlert()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				TableColumes ob1=null;
				
				ob1 = new TableColumes();
				ob1.setColumnname("assetid");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("contactid");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				if(tableColume!=null && tableColume.size()>0)
				{
					cbt.createTable22("network_monitor_contact", tableColume, connectionSpace);
					tableColume.clear();
				}
				String contactId[]=request.getParameterValues("contactid");
				if(contactId.length>0)
				{
					for(int count=0;count<contactId.length;count++)
					{
						insertData.clear();
						
						ob = new InsertDataTable();
						ob.setColName("assetid");
						ob.setDataName(request.getParameter("assetid"));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("contactid");
						ob.setDataName(contactId[count]);
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
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
						
						cbt.insertIntoTable("network_monitor_contact", insertData, connectionSpace);
					}
				}
				addActionMessage("Data Registered Successfully!!!");
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
	
	public String beforeViewServiceAlert()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("assetname");
				gpv.setHeadingName("Asset Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("serialno");
				gpv.setHeadingName("Asset Code");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("contactid");
				gpv.setHeadingName("Remind To");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
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
	
	public String viewServiceAlert()
	{
		System.out.println("inisde method");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query=new StringBuilder();
				query.append("SELECT asd.id,asd.assetname,asd.serialno");
				query.append(" FROM network_monitor_contact AS nmc");
				query.append(" INNER JOIN asset_detail AS asd ON asd.id=nmc.assetid GROUP BY asd.id");
				System.out.println(query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				System.out.println("Data size "+data.size());
				if (data != null && data.size()>0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata =null;
					
					Map<String, Object> tempMap=null;
					List<Object> tempList = new ArrayList<Object>();
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						obdata = (Object[]) it.next();
						tempMap = new HashMap<String, Object>();
						
						query=new StringBuilder();
						query.append(" SELECT emp.empName FROM network_monitor_contact AS nmc ");
						query.append(" INNER JOIN compliance_contacts AS cc ON cc.id=nmc.contactid ");
						query.append(" INNER JOIN employee_basic AS emp ON emp.id=cc.emp_id WHERE nmc.assetid="+obdata[0].toString());
						List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						System.out.println(dataList.size()+" >>>> "+query.toString());
						query=new StringBuilder();
						if(dataList!=null && dataList.size()>0)
						{
							for(int count=0;count<dataList.size();count++)
							{
								query.append(dataList.get(count).toString()+", ");
							}
						}
						if(query!=null && !query.toString().equalsIgnoreCase(""))
						{
							tempMap.put("contactid",query.substring(0, query.length()-2));
						}
						else
						{
							tempMap.put("contactid","NA");
						}
						
						if(obdata[1]!=null)
							tempMap.put("assetname",obdata[1].toString());
						else
							tempMap.put("assetname","NA");
						
						if(obdata[2]!=null)
							tempMap.put("serialno",obdata[2].toString());
						else
							tempMap.put("serialno","NA");
						
						tempList.add(tempMap);
					}
					setMasterViewList(tempList);
				}
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
		this.request=request;
		
	}

	public List<ConfigurationUtilBean> getNetMonitorColumnMap()
	{
		return netMonitorColumnMap;
	}

	public void setNetMonitorColumnMap(List<ConfigurationUtilBean> netMonitorColumnMap)
	{
		this.netMonitorColumnMap = netMonitorColumnMap;
	}

	public List<ConfigurationUtilBean> getAssetColumnMap()
	{
		return assetColumnMap;
	}

	public void setAssetColumnMap(List<ConfigurationUtilBean> assetColumnMap)
	{
		this.assetColumnMap = assetColumnMap;
	}

	public List<ConfigurationUtilBean> getEmpDetailColumnMap()
	{
		return empDetailColumnMap;
	}

	public void setEmpDetailColumnMap(List<ConfigurationUtilBean> empDetailColumnMap)
	{
		this.empDetailColumnMap = empDetailColumnMap;
	}

	public Map<String, String> getContMappedDeptList()
	{
		return contMappedDeptList;
	}

	public void setContMappedDeptList(Map<String, String> contMappedDeptList)
	{
		this.contMappedDeptList = contMappedDeptList;
	}

	public Map<Integer, String> getSerialNoList()
	{
		return serialNoList;
	}

	public void setSerialNoList(Map<Integer, String> serialNoList)
	{
		this.serialNoList = serialNoList;
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
	public List<ConfigurationUtilBean> getCommonDropDownList()
	{
		return commonDropDownList;
	}
	public void setCommonDropDownList(List<ConfigurationUtilBean> commonDropDownList)
	{
		this.commonDropDownList = commonDropDownList;
	}
	public List getTableHeading()
	{
		return tableHeading;
	}
	public void setTableHeading(List tableHeading)
	{
		this.tableHeading = tableHeading;
	}
	public List getTableSubHeading()
	{
		return tableSubHeading;
	}
	public void setTableSubHeading(List tableSubHeading)
	{
		this.tableSubHeading = tableSubHeading;
	}
	public String getMachineId()
	{
		return machineId;
	}
	public void setMachineId(String machineId)
	{
		this.machineId = machineId;
	}
	public List getPartitaionData()
	{
		return partitaionData;
	}
	public void setPartitaionData(List partitaionData)
	{
		this.partitaionData = partitaionData;
	}
	public List<AssetDashboardBean> getSoftwareList()
	{
		return softwareList;
	}
	public void setSoftwareList(List<AssetDashboardBean> softwareList)
	{
		this.softwareList = softwareList;
	}
	public String getRestrictedexe()
	{
		return restrictedexe;
	}
	public void setRestrictedexe(String restrictedexe)
	{
		this.restrictedexe = restrictedexe;
	}
	public boolean isRestrictedFlag()
	{
		return restrictedFlag;
	}
	public void setRestrictedFlag(boolean restrictedFlag)
	{
		this.restrictedFlag = restrictedFlag;
	}
	public List<ConfigurationUtilBean> getAssetDropMap()
	{
		return assetDropMap;
	}
	public void setAssetDropMap(List<ConfigurationUtilBean> assetDropMap)
	{
		this.assetDropMap = assetDropMap;
	}
	public Map<Integer, String> getVendorList()
	{
		return vendorList;
	}
	public void setVendorList(Map<Integer, String> vendorList)
	{
		this.vendorList = vendorList;
	}
	public Map<Integer, String> getSupportTypeList()
	{
		return supportTypeList;
	}
	public void setSupportTypeList(Map<Integer, String> supportTypeList)
	{
		this.supportTypeList = supportTypeList;
	}
	public Map<Integer, String> getAssetTypeList()
	{
		return assetTypeList;
	}
	public void setAssetTypeList(Map<Integer, String> assetTypeList)
	{
		this.assetTypeList = assetTypeList;
	}
	
}
