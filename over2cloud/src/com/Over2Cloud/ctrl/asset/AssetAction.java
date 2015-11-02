package com.Over2Cloud.ctrl.asset;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.asset.common.CreateLogTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(AssetAction.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String userName = (String) session.get("uName");
	String deptLevel = (String) session.get("userDeptLevel");
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
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
	private Map<Integer, String> serialNoList = null;
	private List<ConfigurationUtilBean> barcodeColumnMap;
	private List<ConfigurationUtilBean> commonDDMap;
	private boolean serialNoActive = false;
	private String headingSerialNo;
	private String assetId = null;
	private List<ConfigurationUtilBean> assetColumnMap, dateColumnMap, spareIssueColumnMap, assetDropMap;
	private Map<Integer, String>  vendorList, supportTypeList,assetTypeList;

	@SuppressWarnings("unchecked")
	public String addAsset()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");

				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertDataForDepreciation = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					boolean workStatus = false;
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
							workStatus = true;
					}
					cbt.createTable22("asset_detail", tableColume, connectionSpace);
					
					tableColume.clear();
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname("assetid");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("today_amount");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("depreciation_rate");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("amount_deducted");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("next_update_on");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("next_update_at");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					cbt.createTable22("asset_depreciation_detail", tableColume, connectionSpace);
					
					tableColume.clear();
					ob1 = new TableColumes();
					ob1.setColumnname("asset_depreciation_id");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("witten_down_value");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("amount_deducted");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("date");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColume.add(ob1);
					
					cbt.createTable22("asset_depreciation_yearly_log", tableColume, connectionSpace);
					
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					String deptLevel = (String) session.get("userDeptLevel");
					String subdept_dept = null;
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);

							if (parmName.equalsIgnoreCase("deptname"))
							{
								subdept_dept = paramValue;
							}
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("deptHierarchy") && !parmName.equalsIgnoreCase("deptname") && !parmName.equalsIgnoreCase("subdeptname") && !parmName.equalsIgnoreCase("quantity") && !parmName.equalsIgnoreCase("totalamount"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								if (parmName.equals("receivedOn") || parmName.equals("expectedlife") || parmName.equals("installon") || parmName.equals("commssioningon"))
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
								else
									ob.setDataName(paramValue);

								insertData.add(ob);
							}
						}
					}
					if (subdept_dept != null)
					{
						ob = new InsertDataTable();
						ob.setColName("dept_subdept");
						ob.setDataName(subdept_dept);
						insertData.add(ob);
					}

					if (deptLevel != null)
					{
						ob = new InsertDataTable();
						ob.setColName("deptHierarchy");
						ob.setDataName(deptLevel);
						insertData.add(ob);
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
					if (workStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("flag");
						ob.setDataName("1");
						insertData.add(ob);

					}
					ob = new InsertDataTable();
					ob.setColName("quantity");
					ob.setDataName("1");
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);

					
					
					
					int qty = Integer.valueOf(request.getParameter("quantity"));
					double totalamount = Double.valueOf(request.getParameter("totalamount"));
					ob = new InsertDataTable();
					ob.setColName("totalamount");
					ob.setDataName(String.valueOf(totalamount / qty));
					insertData.add(ob);
					
					
					
					// data insert for asset Depreciation
					ob = new InsertDataTable();
					ob.setColName("today_amount");
					ob.setDataName(String.valueOf(totalamount / qty));
					insertDataForDepreciation.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("depreciation_rate");
					ob.setDataName("0.0707");
					insertDataForDepreciation.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("next_update_on");
					ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("receivedOn")));
					insertDataForDepreciation.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("next_update_at");
					ob.setDataName("23:30");
					insertDataForDepreciation.add(ob);
					
					
					String deptId = request.getParameter("deptName");
					HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
					String serialNo =HUH.getTicketNo(deptId, "ASTM", connectionSpace);
					
					String ticket_type=null,prefix=null;
					int sufix=0;
					if(serialNo!=null && !serialNo.equalsIgnoreCase(""))
					{
						List ticketSeries = new HelpdeskUniversalHelper().getAllData("ticket_series_conf", "moduleName", "ASTM", "", "", connectionSpace);
						if (ticketSeries != null && ticketSeries.size() == 1)
						{
							for (Iterator iterator = ticketSeries.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								ticket_type = object[1].toString();
							}
						}
					}
					if(ticket_type!=null && !ticket_type.equalsIgnoreCase("") && ticket_type.equalsIgnoreCase("D"))
					{
						prefix = serialNo.substring(0, 11);
						sufix = Integer.valueOf(serialNo.substring(11, serialNo.length()));
					}
					
					for (int i = 0; i < qty; i++)
					{
						ob = new InsertDataTable();
						ob.setColName("serialno");
						if(ticket_type!=null && !ticket_type.equalsIgnoreCase("") && ticket_type.equalsIgnoreCase("D"))
						{
							ob.setDataName(prefix+(sufix+i));
						}
						else if(ticket_type!=null && !ticket_type.equalsIgnoreCase("") && ticket_type.equalsIgnoreCase("N"))
						{
							ob.setDataName(Integer.valueOf(serialNo)+i);
						}
						insertData.add(ob);
						int maxId = cbt.insertDataReturnId("asset_detail", insertData, connectionSpace);
						insertData.remove(insertData.size() - 1);
						
						ob = new InsertDataTable();
						ob.setColName("assetid");
						ob.setDataName(maxId);
						insertDataForDepreciation.add(ob);
						status=cbt.insertIntoTable("asset_depreciation_detail", insertDataForDepreciation, connectionSpace);
						insertDataForDepreciation.remove(insertDataForDepreciation.size() - 1);
						
					}
					if (status)
					{
						addActionMessage("Asset Registered Successfully!!!");
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addAsset of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeAssetView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getAssetColumn4View();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAssetView of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getAssetColumn4View()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				if (gdp.getColomnName().equals("deptHierarchy"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("deptName");
					gpv.setHeadingName(new AssetCommonAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName"));
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewProp.add(gpv);
				}
				else if(gdp.getColomnName().equals("assetname"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter("viewAssetHistory");
					masterViewProp.add(gpv);
				}
				else if (!gdp.getColomnName().equals("dept_subdept"))
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
		else
		{
			addActionMessage("Asset Detail Table Is Not properly Configured !");
		}
	}

	@SuppressWarnings("unchecked")
	public String viewAsset()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String dept_subdept_id = null;
				
				dept_subdept_id=new ComplianceUniversalAction().getEmpDataByUserName(userName)[3];
				System.out.println("dept_subdept_id ======="+dept_subdept_id);
				String query1="select count(*) from asset_detail where status='Active' and dept_subdept="+dept_subdept_id;
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
									if(obdata.toString().equalsIgnoreCase("vendorname"))
									{
										columnName.append("vendor."+obdata.toString()+ ",");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("dept_subdept"))
									{
										columnName.append("dept.deptName,");
										finalFieldName.add("deptName");
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("assettype"))
									{
										columnName.append("at.assetSubCat,");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else if(obdata.toString().equalsIgnoreCase("supportfor"))
									{
										columnName.append("concat(asd.supportfor,' Months'),");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else if(!obdata.toString().equalsIgnoreCase("deptHierarchy") && !obdata.toString().equalsIgnoreCase("date") && !obdata.toString().equalsIgnoreCase("time") && !obdata.toString().equalsIgnoreCase("userName") && !obdata.toString().equalsIgnoreCase("flag") && !obdata.toString().equalsIgnoreCase("status"))
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
					query.append(" from asset_detail as asd");
					query.append(" inner join createvendor_master as vendor on vendor.id=asd.vendorname");
					query.append(" inner join createasset_type_master as at on at.id=asd.assettype");
					query.append(" inner join department as dept on dept.id=asd.dept_subdept where asd.status='Active'");
					
					
					
					String app = "asd.";
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
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
					query.append(" order by asd.assetname");
					System.out.println("query2 :"+query.toString());
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
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAsset of class " + getClass(), e);
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
	public String modifyAsset()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					StringBuffer columnBuffer = new StringBuffer("");
					StringBuffer dataBuffer = new StringBuffer("");
					Map<String, String> logData = new LinkedHashMap<String, String>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String newAssetName = null;
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
								if(parmName.equalsIgnoreCase("assetname"))
									newAssetName = paramValue;
									
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}

					}
					
					String oldAssetName = new HelpdeskUniversalAction().getData("asset_detail", "assetname", "id", getId());
					boolean flag = new HelpdeskUniversalHelper().isExist("feedback_category", "categoryName", oldAssetName, connectionSpace);
					if(flag)
					{
						String updateQry="update feedback_category set categoryName='"+newAssetName+"' where categoryName='"+oldAssetName+"'";
						new HelpdeskUniversalHelper().updateData(updateQry, connectionSpace);
					}
					
					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "asset_detail");
					logData.put("update_type", "Modify");
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);

					condtnBlock.put("id", getId());
					boolean status = cbt.updateTableColomn("asset_detail", wherClause, condtnBlock, connectionSpace);
					System.out.println(status);
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
						cbt.updateTableColomn("asset_detail", wherClause, condtnBlock, connectionSpace);
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyAsset of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createBarcodePage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				barcodeColumnMap = new ArrayList<ConfigurationUtilBean>();
				commonDDMap = new ArrayList<ConfigurationUtilBean>();
				assetDropMap=new ArrayList<ConfigurationUtilBean>();
				assetTypeList=new LinkedHashMap<Integer, String>();
				AssetUniversalAction AUA=new AssetUniversalAction();
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				if (assetColumnList != null && assetColumnList.size() > 0)
				{
					ConfigurationUtilBean obj;
					for (GridDataPropertyView gdp1 : assetColumnList)
					{
						obj = new ConfigurationUtilBean();
						if (gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand") || gdp1.getColomnName().equalsIgnoreCase("assetmodel"))
						{
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							obj.setValidationType(gdp1.getValidationType());
							obj.setColType(gdp1.getColType());
							if (gdp1.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							barcodeColumnMap.add(obj);
						}
						if (gdp1.getColomnName().equalsIgnoreCase("assetname"))
						{
							serialNoActive = true;
							headingSerialNo = gdp1.getHeadingName().toString();
							serialNoList = new LinkedHashMap<Integer, String>();
							try
							{
								String query1 = "SELECT id,assetname FROM asset_detail WHERE status='Active'";
								List slnoList = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
								if (slnoList != null && slnoList.size() > 0)
								{
									for (Object obj1 : slnoList)
									{
										Object[] object = (Object[]) obj1;
										serialNoList.put((Integer) object[0], object[1].toString());
									}
								}
								obj.setKey(gdp1.getColomnName());
								obj.setValue(headingSerialNo);
								obj.setValidationType(gdp1.getValidationType());
								obj.setColType(gdp1.getColType());
								if (gdp1.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								commonDDMap.add(obj);
								assetDropMap=AUA.getAssetConfig();
								assetTypeList=AUA.assetTypeList();
								returnResult = SUCCESS;
							}
							catch (Exception e)
							{
								log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createBarcodePage of class " + getClass(), e);
								e.printStackTrace();
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createBarcodePage of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		System.out.println("Return result is "+returnResult);
		return returnResult;
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public String generateBarcode()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("assetId ids"+assetId);
				if(assetId!=null)
				{
					String serialNo=null;
					String dateOfPurchase=null;
					String mfgSerialNo=null;
					
					String query = "SELECT serialno,mfgserialno,receivedOn FROM asset_detail WHERE id="+assetId;
					System.out.println("query  :"+query );
					List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null)
								serialNo=object[0].toString();
							else
								serialNo="NA";
							
							if(object[1]!=null)
								mfgSerialNo=object[1].toString();
							else
								mfgSerialNo="NA";
							
							if(object[2]!=null)
								dateOfPurchase=DateUtil.convertDateToIndianFormat(object[2].toString());
							else
								dateOfPurchase="NA";
						}
					}
					String filePath = request.getSession().getServletContext().getRealPath("/");
					File TTFfilePath = new File(filePath + "/images/", "3of9.TTF");
					System.out.println(TTFfilePath.toString());
					if(TTFfilePath!=null)
					{
						String saveImgPath = request.getSession().getServletContext().getRealPath("/images/barCodeImage/");
						System.out.println("saveImgPath "+saveImgPath);
						if(saveImgPath!=null)
						{
							StringBuffer buffer = new StringBuffer();
							for (int i = 0; i < 10 - getAssetId().length(); i++)
							{
								buffer.append("0");
							}
							buffer.append(getAssetId());
							int width, height;
							String format = new String("gif");
							width = 250;
							height = 102;
							BufferedImage bufimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
							Graphics graphicsobj = bufimg.createGraphics();
							
							InputStream fin = new FileInputStream(TTFfilePath);
							Font font = Font.createFont(Font.TRUETYPE_FONT, fin);
							Font font1 = font.deriveFont(30f);
							graphicsobj.setFont(font1);
							graphicsobj.setFont(font.getFont("3 of 9 Barcode"));
							graphicsobj.setColor(Color.WHITE);

							graphicsobj.fillRect(1, 1, 248, 100);
							graphicsobj.setColor(Color.BLACK);
							((Graphics2D) graphicsobj).drawString(buffer.toString(), 30, 50);
							Font font2 = new Font("serif", Font.PLAIN, 12);
							graphicsobj.setFont(font2);
							graphicsobj.drawString("Org S/N : " + serialNo, 30, 60);
							graphicsobj.drawString("DOP : "+dateOfPurchase, 30, 70);
							graphicsobj.drawString("Mfg S/N : " + mfgSerialNo, 30, 80);
							File saveFile = new File(saveImgPath, getAssetId() + ".gif");
							
							ImageIO.write(bufimg, format, saveFile);
							//assetDetailMap.put("path", "/images/BarcodeImg/" + getAssetId() + ".gif");
						}
						
					}
				}
				//= request.getParameter("assetid");
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

	public String barcodeConfiguration()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						if (gdp.getColomnName().equals("deptHierarchy"))
						{
							
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
		else
		{
			return LOGIN;
		}
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

	public Map<Integer, String> getSerialNoList()
	{
		return serialNoList;
	}

	public void setSerialNoList(Map<Integer, String> serialNoList)
	{
		this.serialNoList = serialNoList;
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

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}

	public List<ConfigurationUtilBean> getBarcodeColumnMap()
	{
		return barcodeColumnMap;
	}

	public List<ConfigurationUtilBean> getCommonDDMap()
	{
		return commonDDMap;
	}

	public void setBarcodeColumnMap(List<ConfigurationUtilBean> barcodeColumnMap)
	{
		this.barcodeColumnMap = barcodeColumnMap;
	}

	public void setCommonDDMap(List<ConfigurationUtilBean> commonDDMap)
	{
		this.commonDDMap = commonDDMap;
	}

	
	
	public List<ConfigurationUtilBean> getAssetColumnMap()
	{
		return assetColumnMap;
	}

	public void setAssetColumnMap(List<ConfigurationUtilBean> assetColumnMap)
	{
		this.assetColumnMap = assetColumnMap;
	}

	public List<ConfigurationUtilBean> getDateColumnMap()
	{
		return dateColumnMap;
	}

	public void setDateColumnMap(List<ConfigurationUtilBean> dateColumnMap)
	{
		this.dateColumnMap = dateColumnMap;
	}

	public List<ConfigurationUtilBean> getSpareIssueColumnMap()
	{
		return spareIssueColumnMap;
	}

	public void setSpareIssueColumnMap(List<ConfigurationUtilBean> spareIssueColumnMap)
	{
		this.spareIssueColumnMap = spareIssueColumnMap;
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

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}
}
