package com.Over2Cloud.ctrl.asset;

import java.math.BigInteger;
import java.text.DecimalFormat;
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
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.common.CreateLogTable;

@SuppressWarnings("serial")
public class SpareAction extends GridPropertyBean implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(SpareAction.class);
	private List<ConfigurationUtilBean> spareCatgColumnMap = null;
	private List<ConfigurationUtilBean> spareColumnMap = null;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> masterViewList;
	private HttpServletRequest request;
	private String spareCatgHeadName = null;
	boolean spareCatgActive = false;
	boolean spareNonMoveAltActive = false;
	boolean spareNonMovePeriodActive = false;
	Map<Integer, String> spareCatgMap = null;
	Map<Integer, String> nonMoveMap = null;
	Map<Integer, String> nonMovePeriodMap = null;
	private boolean vendorActive = false;
	private String headingVendorName;
	private Map<Integer, String> vendorMap = null;
	private List<ConfigurationUtilBean> dateMap = null;
	private Map<Integer, String> spareNameMap = null;
	private Map<String, String> consumeNonConsumeSpareMap = null;
	private String deptName;
	private List<ConfigurationUtilBean> commonDDMap = null;

	public String createSpareCatgPage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> spareCatgColumnList = Configuration.getConfigurationData("mapped_spare_catg_master", accountID, connectionSpace, "", 0, "table_name", "spare_catg_master");
				spareCatgColumnMap = new ArrayList<ConfigurationUtilBean>();
				if (spareCatgColumnList != null && spareCatgColumnList.size() > 0)
				{
					ConfigurationUtilBean obj =null;
					for (GridDataPropertyView gdp : spareCatgColumnList)
					{
						obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
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
							spareCatgColumnMap.add(obj);
						}
					}
					returnResult = SUCCESS;
				}
				else
				{
					addActionMessage("Spare Category Table Is Not properly Configured !");
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareCatgPage of class " + getClass(), e);
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
	public String addSpareCatg()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_spare_catg_master", accountID, connectionSpace, "", 0, "table_name", "spare_catg_master");
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
					cbt.createTable22("createspare_catg_master", tableColume, connectionSpace);
					String query="SELECT id FROM createspare_catg_master WHERE status='Active' AND spare_category='"+request.getParameter("spare_category")+"'";
					List datalist=cbt.executeAllSelectQuery(query, connectionSpace);
					if(datalist!=null && datalist.size()>0)
					{
						addActionMessage("Entered Spare Category Already Exist!!!");
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
						status = cbt.insertIntoTable("createspare_catg_master", insertData, connectionSpace);
						if (status)
						{
							addActionMessage("Spare Category Registered Successfully!!!");
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addSpareCatg of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createSpareCatgView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				getColumn4View("mapped_spare_catg_master", "spare_catg_master");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareCatgView of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getColumn4View(String mappedTable, String columnTable)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		masterViewProp = new ArrayList<GridDataPropertyView>();
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name", columnTable);
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				if (gdp.getColomnName().equalsIgnoreCase("non_move_period"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName() + " (In Months)");
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
				else if (gdp.getColomnName().equalsIgnoreCase("non_move_alert"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName() + " (0:No,1:Yes)");
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
				else
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
		else
		{
			addActionMessage("Spare Detail Table Is Not properly Configured !");
		}
		if (columnTable.equalsIgnoreCase("spare_master"))
		{
			/*
			 * boolean spareReceiveStatus=false; boolean spareIssueStatus=false;
			 * 
			 * spareReceiveStatus=new
			 * HelpdeskUniversalHelper().checkTable("receive_spare_detail"
			 * ,connectionSpace); spareIssueStatus=new
			 * HelpdeskUniversalHelper().
			 * checkTable("spare_issue_detail",connectionSpace);
			 * if(spareReceiveStatus) { gpv=new GridDataPropertyView();
			 * gpv.setColomnName("sparereceive");
			 * gpv.setHeadingName("Total Receive");
			 * gpv.setFormatter("spareReceiveFormatLink"); gpv.setWidth(70);
			 * masterViewProp.add(gpv); } if(spareIssueStatus) { gpv=new
			 * GridDataPropertyView(); gpv.setColomnName("spareissue");
			 * gpv.setHeadingName("Total Issue");
			 * gpv.setFormatter("spareIssueFormatLink"); gpv.setWidth(70);
			 * masterViewProp.add(gpv); }
			 */
		}
	}

	// view support category
	public String viewSpareCategory()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from createspare_catg_master where status='Active'");
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
					List fieldNames = Configuration.getColomnList("mapped_spare_catg_master", accountID, connectionSpace, "spare_catg_master");

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
					query.append(" from createspare_catg_master where status='Active'");
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
					query.append(" ORDER BY spare_category");
					cbt.checkTableColmnAndAltertable(fieldNames, "createspare_catg_master", connectionSpace);
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSpareCategory of class " + getClass(), e);
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
	@SuppressWarnings("unchecked")
	public String modifySpareCategory()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuffer columnBuffer = new StringBuffer("");
				StringBuffer dataBuffer = new StringBuffer("");
				Map<String, String> logData = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (getOper().equalsIgnoreCase("edit"))
				{
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
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}
					}

					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "createspare_catg_master");
					logData.put("update_type", "Modify");

					condtnBlock.put("id", getId());
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);
					boolean status = cbt.updateTableColomn("createspare_catg_master", wherClause, condtnBlock, connectionSpace);
					if (status)
					{
						returnResult = SUCCESS;
					}
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						wherClause.put("status", "DActive");
						condtnBlock.put("id", H);
						boolean status = cbt.updateTableColomn("createspare_catg_master", wherClause, condtnBlock, connectionSpace);
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifySpareCategory of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createSparePage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> spareCatgColumnList=Configuration.getConfigurationData("mapped_spare_master",accountID,connectionSpace,"",0,"table_name","spare_master");
				spareColumnMap=new ArrayList<ConfigurationUtilBean>();
				commonDDMap=new ArrayList<ConfigurationUtilBean>();
				if(spareCatgColumnList!=null&&spareCatgColumnList.size()>0)
				{
					for(GridDataPropertyView  gdp:spareCatgColumnList)
					{
						ConfigurationUtilBean obj=new ConfigurationUtilBean();
						if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if(gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							spareColumnMap.add(obj);
						}
						/*if(gdp.getColomnName().equalsIgnoreCase("spare_category"))*/
						if(gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							spareCatgMap=new AssetCommonAction().getSpareCatgById();
							if(spareCatgMap!=null && spareCatgMap.size()>0)
							{
								spareCatgActive=true;
								spareCatgHeadName=new AssetCommonAction().getMasterFieldName("spare_catg_master","spare_category");
								if(spareCatgHeadName!=null)
								{
									obj.setKey(gdp.getColomnName());
									obj.setValue(spareCatgHeadName);
									obj.setValidationType(gdp.getValidationType());
									obj.setColType(gdp.getColType());
									if(gdp.getMandatroy().toString().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									commonDDMap.add(obj);
								}
							}
						}
						if(gdp.getColomnName().equalsIgnoreCase("non_move_alert"))
						{
							spareNonMoveAltActive=true;
							nonMoveMap=new LinkedHashMap<Integer, String>();
							nonMoveMap.put(0,"No");
							nonMoveMap.put(1,"Yes");
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName().toString());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if(gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							
						}
						if(gdp.getColomnName().equalsIgnoreCase("non_move_period"))
						{
							spareNonMovePeriodActive=true;
							nonMovePeriodMap=new LinkedHashMap<Integer, String>();
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName().toString());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if(gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							commonDDMap.add(obj);
							for(int i=1;i<=8;i++)
							{
								nonMovePeriodMap.put(3*i, (3*i)+"Months");
							}
						}
					}
					returnResult=SUCCESS;
				}
				else
				{
					addActionMessage("Spare Category Table Is Not properly Configured !");
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSparePage of class " + getClass(), e);
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
	public String addSpareDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_spare_master", accountID, connectionSpace, "", 0, "table_name", "spare_master");
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
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						if (gdp.getColomnName().equalsIgnoreCase("threshhold_level") || gdp.getColomnName().equalsIgnoreCase("non_move_alert") || gdp.getColomnName().equalsIgnoreCase("non_move_level") || gdp.getColomnName().equalsIgnoreCase("non_move_period"))
						{
							ob1.setConstraint("default '0'");
						}
						else
						{
							ob1.setConstraint("default NULL");
						}
						tableColume.add(ob1);
						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
					}
					cbt.createTable22("spare_detail", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (!paramValue.equalsIgnoreCase("-1") && paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase(""))
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
					status = cbt.insertIntoTable("spare_detail", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("Spare Detail Registered Successfully!!!");
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addSpareDetail of class " + getClass(), e);
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
	public String createSpareReceivePage()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> spareCatgColumnList = Configuration.getConfigurationData("mapped_spare_receive_master", accountID, connectionSpace, "", 0, "table_name", "spare_receive_master");
				spareColumnMap = new ArrayList<ConfigurationUtilBean>();
				commonDDMap = new ArrayList<ConfigurationUtilBean>();
				dateMap = new ArrayList<ConfigurationUtilBean>();
				if (spareCatgColumnList != null && spareCatgColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : spareCatgColumnList)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")  && !gdp.getColomnName().equalsIgnoreCase("unitcost") && !gdp.getColomnName().equalsIgnoreCase("remaining"))
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
							spareColumnMap.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("Time"))
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
							dateMap.add(timeObj);
						}
						if (gdp.getColomnName().equalsIgnoreCase("spare_name"))
						{
							spareCatgMap = new AssetCommonAction().getSpareCatgById();
							if (spareCatgMap != null && spareCatgMap.size() > 0)
							{
								spareCatgActive = true;
								spareCatgHeadName = new AssetCommonAction().getMasterFieldName("spare_catg_master", "spare_category");
							}
							else
							{
								spareNameMap = new LinkedHashMap<Integer, String>();
								spareNameMap = new AssetCommonAction().getSpare();
							}
							spareNonMoveAltActive = true;
							if (spareCatgHeadName != null && spareCatgHeadName != "")
							{
								obj = new ConfigurationUtilBean();
								obj.setKey("spare_category");
								obj.setValue(spareCatgHeadName);
								obj.setValidationType("");
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
							obj = new ConfigurationUtilBean();
							obj.setKey(gdp.getColomnName());
							obj.setValue(new AssetCommonAction().getFieldName("mapped_spare_master", "spare_master", "spare_name"));
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
						if (gdp.getColomnName().equalsIgnoreCase("vendorname"))
						{
							vendorActive = true;
							try
							{
								vendorMap = new LinkedHashMap<Integer, String>();
								List temp = new ArrayList<String>();
								String query = "SELECT id,vendorname FROM createvendor_master ORDER BY vendorname ASC";
								temp = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if (temp != null && temp.size() > 0)
								{
									for (Object obj1 : temp)
									{
										Object[] object = (Object[]) obj1;
										vendorMap.put((Integer) object[0], object[1].toString());
									}
								}
								obj = new ConfigurationUtilBean();
								obj.setKey(gdp.getColomnName());
								obj.setValue(new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname"));
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
								log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareReceivePage of class " + getClass(), e);
								e.printStackTrace();
							}
						}
					}
					returnResult = SUCCESS;
				}
				else
				{
					addActionMessage("Spare Receiveing Table Is Not properly Configured !");
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareReceivePage of class " + getClass(), e);
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
	public String addReceiveSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_spare_receive_master", accountID, connectionSpace, "", 0, "table_name", "spare_receive_master");
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

					cbt.createTable22("receive_spare_detail", tableColume, connectionSpace);
					tableColume.clear();
					TableColumes ob2 = null;
					ob2 = new TableColumes();
					ob2.setColumnname("spare_name");
					ob2.setDatatype("varchar(50)");
					ob2.setConstraint("default NULL");
					tableColume.add(ob2);

					ob2 = new TableColumes();
					ob2.setColumnname("total_nonconsume_spare");
					ob2.setDatatype("varchar(50)");
					ob2.setConstraint("default NULL");
					tableColume.add(ob2);

					ob2 = new TableColumes();
					ob2.setColumnname("sms_flag");
					ob2.setDatatype("varchar(1)");
					ob2.setConstraint("default 0");
					tableColume.add(ob2);

					cbt.createTable22("nonconsume_spare_detail", tableColume, connectionSpace);

					String spareId = null;
					int qnty = 0;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (!paramValue.equalsIgnoreCase("-1") && paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase(""))
							{
								if (parmName.equalsIgnoreCase("challan_date") || parmName.equalsIgnoreCase("received_date"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
								}
								else
								{
									if (parmName.equalsIgnoreCase("spare_name"))
									{
										spareId = paramValue;
									}
									if (parmName.equalsIgnoreCase("quantity"))
									{
										qnty = Integer.valueOf(paramValue);
									}
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
							}

						}
					}
					ob = new InsertDataTable();
					ob.setColName("remaining");
					ob.setDataName(qnty);
					insertData.add(ob);
					
					float unitCost=Float.valueOf(request.getParameter("totalamount"))/qnty;
					
					ob = new InsertDataTable();
					ob.setColName("unitcost");
					ob.setDataName(unitCost);
					insertData.add(ob);
					
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

					status = cbt.insertIntoTable("receive_spare_detail", insertData, connectionSpace);

					String query = "Select total_nonconsume_spare from nonconsume_spare_detail where spare_name=" + spareId;
					List temp = new ArrayList();
					temp = cbt.executeAllSelectQuery(query, connectionSpace);
					if (temp != null && temp.size() > 0)
					{
						qnty += Integer.parseInt(temp.get(0).toString());
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						wherClause.put("total_nonconsume_spare", qnty);
						wherClause.put("sms_flag", 0);
						condtnBlock.put("spare_name", spareId);
						status = cbt.updateTableColomn("nonconsume_spare_detail", wherClause, condtnBlock, connectionSpace);

					}
					else
					{
						insertData.clear();
						ob = new InsertDataTable();
						ob.setColName("spare_name");
						ob.setDataName(spareId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("total_nonconsume_spare");
						ob.setDataName(qnty);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("sms_flag");
						ob.setDataName(0);
						insertData.add(ob);

						status = cbt.insertIntoTable("nonconsume_spare_detail", insertData, connectionSpace);
					}

					if (status)
					{
						addActionMessage("Receive Spare Registered Successfully!!!");
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addReceiveSpare of class " + getClass(), e);
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
	public String addIssueSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_spare_issue_master", accountID, connectionSpace, "", 0, "table_name", "spare_issue_master");
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
					cbt.createTable22("spare_issue_detail", tableColume, connectionSpace);
					String deptLevel = (String) session.get("userDeptLevel");
					String subdept_dept = null;
					String spareId = null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (!paramValue.equalsIgnoreCase("-1") && paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase(""))
							{
								if (parmName.equalsIgnoreCase("from_date") || parmName.equalsIgnoreCase("to_date"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
								}
								else if (parmName.equalsIgnoreCase("deptname") && deptLevel.equals("1"))
								{
									subdept_dept = paramValue;
								}
								else if (parmName.equalsIgnoreCase("subdeptname") && deptLevel.equals("2"))
								{
									subdept_dept = paramValue;
								}
								else if (!parmName.equalsIgnoreCase("deptHierarchy") && !parmName.equalsIgnoreCase("deptname") && !parmName.equalsIgnoreCase("subdeptname"))
								{
									if (parmName.equalsIgnoreCase("spare_name"))
									{
										spareId = paramValue;
									}
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
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
					status = cbt.insertIntoTable("spare_issue_detail", insertData, connectionSpace);
					if (status)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						int remain = Integer.parseInt(session.get("remainSpare").toString());
						session.remove("remainSpare");
						wherClause.put("total_nonconsume_spare", remain);
						condtnBlock.put("spare_name", spareId);
						status = cbt.updateTableColomn("nonconsume_spare_detail", wherClause, condtnBlock, connectionSpace);
					}

					if (status)
					{
						addActionMessage("Spare Issued Successfully!!!");
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addIssueSpare of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createSpareView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				getColumn4View("mapped_spare_master", "spare_master");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareView of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String viewSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from spare_detail");
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
					List fieldNames = Configuration.getColomnList("mapped_spare_master", accountID, connectionSpace, "spare_master");

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
					query.append(" from spare_detail");
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
					cbt.checkTableColmnAndAltertable(fieldNames, "spare_detail", connectionSpace);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						List<String> temp = new ArrayList<String>();
						Map<String, Object> wherClause = new LinkedHashMap<String, Object>();
						List<Object> tempList = new ArrayList<Object>();
						List list = new ArrayList();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (k == 0)
								{
									tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								}
								else if (fieldNames.get(k).toString().equalsIgnoreCase("spare_category"))
								{
									wherClause.clear();
									temp.clear();
									wherClause.put("id", obdata[k].toString());
									temp.add("spare_category");
									list = cbt.viewAllDataEitherSelectOrAll("createspare_catg_master", temp, wherClause, connectionSpace);
									tempMap.put(fieldNames.get(k).toString(), list.get(0).toString());
								}
								else
								{
									if (obdata[k] != null && !obdata[k].toString().equals(""))
									{
										tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									else
									{
										tempMap.put(fieldNames.get(k).toString(), "NA");
									}
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSpare of class " + getClass(), e);
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
	public String modifySpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuffer columnBuffer = new StringBuffer("");
				StringBuffer dataBuffer = new StringBuffer("");
				Map<String, String> logData = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (getOper().equalsIgnoreCase("edit"))
				{
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
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}
					}

					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "spare_detail");
					logData.put("update_type", "Modify");

					condtnBlock.put("id", getId());
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);
					ProcurementAction pa = new ProcurementAction();
					StringBuilder updateQuery = pa.updateQuery("spare_detail", wherClause, condtnBlock);
					boolean status = cbt.updateTableColomn(connectionSpace, updateQuery);
					if (status)
					{
						returnResult = SUCCESS;
					}
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
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
					cbt.deleteAllRecordForId("spare_detail", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifySpare of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createReceiveSpareView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				getColumn4View("mapped_spare_receive_master", "spare_receive_master");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createReceiveSpareView of class " + getClass(), e);
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
	public String viewReceiveSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from receive_spare_detail");
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
					List fieldNames = Configuration.getColomnList("mapped_spare_receive_master", accountID, connectionSpace, "spare_receive_master");

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
									if(obdata.toString().equalsIgnoreCase("spare_name"))
									{
										query.append("spare."+obdata.toString() + ",");
									}
									else if(obdata.toString().equalsIgnoreCase("vendorname"))
									{
										query.append("vendor."+obdata.toString() + ",");
									}
									else 
									{
										query.append("RSD."+obdata.toString() + ",");
									}
										
								}
								else
								{
									if(obdata.toString().equalsIgnoreCase("spare_name"))
									{
										query.append("spare."+obdata.toString());
									}
									else if(obdata.toString().equalsIgnoreCase("vendorname"))
									{
										query.append("vendor."+obdata.toString());
									}
									else 
									{
										query.append("RSD."+obdata.toString());
									}
								}
							}
							i++;

						}
					}
					query.append(" from receive_spare_detail AS RSD");
					query.append(" LEFT JOIN createvendor_master AS vendor ON vendor.id=RSD.vendorname ");
					query.append(" LEFT JOIN spare_detail AS spare ON spare.id=RSD.spare_name ");
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
					cbt.checkTableColmnAndAltertable(fieldNames, "receive_spare_detail", connectionSpace);
					System.out.println(">>>> "+query.toString());
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
									if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewReceiveSpare of class " + getClass(), e);
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
	public String modifyReceiveSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuffer columnBuffer = new StringBuffer("");
				StringBuffer dataBuffer = new StringBuffer("");
				Map<String, String> logData = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (getOper().equalsIgnoreCase("edit"))
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
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
							else if (parmName.equalsIgnoreCase("challan_date") || parmName.equalsIgnoreCase("received_date"))
							{
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
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

					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "receive_spare_detail");
					logData.put("update_type", "Modify");

					condtnBlock.put("id", getId());
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);
					ProcurementAction pa = new ProcurementAction();
					StringBuilder updateQuery = pa.updateQuery("receive_spare_detail", wherClause, condtnBlock);
					boolean status = cbt.updateTableColomn(connectionSpace, updateQuery);
					if (status)
					{
						returnResult = SUCCESS;
					}
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
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
					cbt.deleteAllRecordForId("receive_spare_detail", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyReceiveSpare of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createIssueSpareView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				getColumn4View("mapped_spare_issue_master", "spare_issue_master");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createIssueSpareView of class " + getClass(), e);
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
	public String viewIssueSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from spare_issue_detail");
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
					List fieldNames = Configuration.getColomnList("mapped_spare_issue_master", accountID, connectionSpace, "spare_issue_master");

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
					if (deptName != null)
					{
						if (session.get("userDeptLevel").toString().equals("1"))
						{
							query.append(" from spare_issue_detail where dept_subdept IN(Select id from department where deptName=" + deptName + ")");
						}
						else if (session.get("userDeptLevel").toString().equals("2"))
						{
							query.append(" from spare_issue_detail where dept_subdept IN(Select id from subdepartment where deptid IN(Select id from department where deptName='" + deptName + "'))");
						}
					}
					else
					{
						query.append(" from spare_issue_detail");
					}
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
					cbt.checkTableColmnAndAltertable(fieldNames, "spare_issue_detail", connectionSpace);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<String> temp = new ArrayList<String>();
						Map<String, Object> wherClause = new LinkedHashMap<String, Object>();
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
									else if (fieldNames.get(k).toString().equalsIgnoreCase("to_date") || fieldNames.get(k).toString().equalsIgnoreCase("from_date"))
									{
										tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									/*
									 * elseif(fieldNames.get(k).toString().
									 * equalsIgnoreCase("dept_subdept")) {
									 * if(session
									 * .get("userDeptLevel").toString()
									 * .equals("1")) { query1=new
									 * StringBuilder();query1.append(
									 * "select deptName from department where id="
									 * +obdata[k].toString()); List
									 * deptNameList=
									 * cbt.executeAllSelectQuery(query1
									 * .toString(),connectionSpace);
									 * tempMap.put("dept_subdept",
									 * deptNameList.get(0)); } else
									 * if(session.get
									 * ("userDeptLevel").toString().equals("2"))
									 * { query1=new StringBuilder();
									 * query1.append(
									 * "select deptName from department where id IN(Select deptid from subdepartment where id="
									 * +obdata[k].toString()+")"); List
									 * deptNameList
									 * =cbt.executeAllSelectQuery(query1
									 * .toString(),connectionSpace);
									 * tempMap.put("dept_subdept",
									 * deptNameList.get(0)); } }
									 */
									else if (fieldNames.get(k).toString().equalsIgnoreCase("spare_name"))
									{
										query1 = new StringBuilder();
										query1.append("select spare_name from spare_detail where id=" + obdata[k].toString());
										List spareNameList = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
										tempMap.put("spare_name", spareNameList.get(0));
									}
									else
									{

										tempMap.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
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
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewIssueSpare of class " + getClass(), e);

			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	@SuppressWarnings("unchecked")
	public String modifyIssueSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ProcurementAction pa = new ProcurementAction();
				StringBuffer columnBuffer = new StringBuffer("");
				StringBuffer dataBuffer = new StringBuffer("");
				Map<String, String> logData = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (getOper().equalsIgnoreCase("edit"))
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					int updateSpare = 0;
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
							else if (parmName.equalsIgnoreCase("from_date") || parmName.equalsIgnoreCase("to_date"))
							{
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
							else
							{
								if (parmName.equalsIgnoreCase("no_issue"))
								{
									updateSpare = Integer.valueOf(paramValue);
								}
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}
					}

					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "spare_issue_detail");
					logData.put("update_type", "Modify");

					condtnBlock.put("id", getId());
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);
					int totalIssueSpare = 0;
					int totalRecvSpare = 0;
					String spareId = null;
					String str = "select sum(no_issue) from spare_issue_detail where id NOT IN('" + getId() + "') and spare_name in(SELECT spare_name FROM spare_issue_detail where id='" + getId() + "')";
					List totalRemainIng = cbt.executeAllSelectQuery(str, connectionSpace);
					if (totalRemainIng != null && totalRemainIng.size() > 0)
					{
						if (totalRemainIng.get(0) != null)
						{
							totalIssueSpare = (int) Double.parseDouble(totalRemainIng.get(0).toString());
						}
					}
					String abc = "select spare_name,sum(quantity) as total from receive_spare_detail where spare_name=(SELECT spare_name FROM spare_issue_detail where id='" + getId() + "') Group By spare_name";
					totalRemainIng = new ArrayList();
					totalRemainIng = cbt.executeAllSelectQuery(abc, connectionSpace);
					if (totalRemainIng != null && totalRemainIng.size() > 0)
					{
						for (Iterator iterator = totalRemainIng.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							totalRecvSpare = Float.valueOf(object[1].toString()).intValue();
							spareId = object[0].toString();
						}
					}

					if (totalRecvSpare >= (totalIssueSpare + updateSpare))
					{

						StringBuilder updateQuery = pa.updateQuery("spare_issue_detail", wherClause, condtnBlock);
						boolean status = cbt.updateTableColomn(connectionSpace, updateQuery);
						if (status)
						{
							returnResult = SUCCESS;
						}
						wherClause = new HashMap<String, Object>();
						condtnBlock = new HashMap<String, Object>();
						int availSpare = totalRecvSpare - (totalIssueSpare + updateSpare);
						wherClause.put("total_nonconsume_spare", availSpare);
						condtnBlock.put("spare_name", spareId);
						StringBuilder updateQuery1 = pa.updateQuery("nonconsume_spare_detail", wherClause, condtnBlock);
						status = cbt.updateTableColomn(connectionSpace, updateQuery1);
					}
					// String str=
					// "select (select sum(quantity) from receive_spare_detail where spare_name='"
					// +getId()+
					// "')-(select sum(no_issue) from spare_issue_detail where spare_name='"
					// +getId()+"') AS Outstanding_Spare";
					// List totalRemainIng=cbt.executeAllSelectQuery(str,
					// connectionSpace);

				}
				else if (getOper().equalsIgnoreCase("del"))
				{
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
					cbt.deleteAllRecordForId("spare_issue_detail", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifyIssueSpare of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String createSpareGraph()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				consumeNonConsumeSpareMap = new LinkedHashMap<String, String>();
				query1.append("select SUM(total_nonconsume_spare) from nonconsume_spare_detail");
				List totalRemainIng = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				String deptHierarchy = (String) session.get("userDeptLevel");
				if (deptHierarchy.equals("2"))
				{
					query1 = new StringBuilder("");
					query1.append("SELECT SUM(sid.no_issue),dept.deptName FROM spare_issue_detail as sid " + "INNER JOIN subdepartment sdept on sdept.id=sid.dept_subdept " + "INNER JOIN department dept on dept.id=sdept.deptid GROUP BY dept.deptName");
				}
				else if (deptHierarchy.equals("1"))
				{
					query1 = new StringBuilder("");
					query1.append("SELECT SUM(sid.no_issue),dept.deptName FROM spare_issue_detail as sid " + "INNER JOIN department dept on dept.id=sid.dept_subdept GROUP BY dept.deptName");
				}
				if (query1 != null)
				{
					List totalConsume = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					if (totalConsume != null && totalConsume.size() > 0)
					{
						for (Iterator iterator = totalConsume.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							consumeNonConsumeSpareMap.put(object[1].toString(), object[0].toString());
						}
					}
					if (totalRemainIng != null && totalRemainIng.size() > 0)
						;
					{
						consumeNonConsumeSpareMap.put("Remaining Spare", totalRemainIng.get(0).toString());
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareGraph of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String createSpareInfoView()
	{
		System.out.println("Inisde createSpareInfoView Method");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				GridDataPropertyView gpv = new GridDataPropertyView();
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("spare_catg");
				gpv.setHeadingName("Spare Category");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("spare_name");
				gpv.setHeadingName("Spare Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("total_consume_spare");
				gpv.setHeadingName("Total Used");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
				
				gpv = new GridDataPropertyView();
				gpv.setColomnName("total_nonconsume_spare");
				gpv.setHeadingName("Total Remaining");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
					
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createSpareInfoView of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String viewRemainingSpare()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from nonconsume_spare_detail");
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
					query.append("SELECT spare.id,catg.spare_category,spare.spare_name,NSD.total_nonconsume_spare ");
					query.append(" FROM nonconsume_spare_detail AS NSD");
					query.append(" LEFT JOIN spare_detail AS spare ON spare.id=NSD.spare_name");
					query.append(" LEFT JOIN createspare_catg_master AS catg ON catg.id=spare.spare_category ");
					query.append(" WHERE NSD.total_nonconsume_spare>0 ORDER BY spare.spare_name");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						ComplianceUniversalAction CUA = new ComplianceUniversalAction();
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							tempMap.put("id", CUA.getValueWithNullCheck(obdata[0]));
							tempMap.put("spare_catg", CUA.getValueWithNullCheck(obdata[1]));
							tempMap.put("spare_name", CUA.getValueWithNullCheck(obdata[2]));
							tempMap.put("total_consume_spare", totalConsumeSpare(obdata[0].toString(),cbt));
							tempMap.put("total_nonconsume_spare", CUA.getValueWithNullCheck(obdata[3]));
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewRemainingSpare of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String totalConsumeSpare(String spareId,CommonOperInterface cbt)
	{
		String count="0";
		String query="SELECT SUM(no_spareused) FROM asset_complaint_status WHERE spareName="+spareId;
		List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			if(dataList.get(0)!=null)
				count=dataList.get(0).toString().substring(0, dataList.get(0).toString().length()-2);
		}
		return count;
	}
	
	public String createGraphViewData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				getColumn4View("mapped_spare_issue_master", "spare_issue_master");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createGraphViewData of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	public void updateSpare()
	{
		System.out.println(">>>>> ");
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public String getSpareCatgHeadName()
	{
		return spareCatgHeadName;
	}

	public void setSpareCatgHeadName(String spareCatgHeadName)
	{
		this.spareCatgHeadName = spareCatgHeadName;
	}

	public boolean isSpareCatgActive()
	{
		return spareCatgActive;
	}

	public void setSpareCatgActive(boolean spareCatgActive)
	{
		this.spareCatgActive = spareCatgActive;
	}

	public Map<Integer, String> getSpareCatgMap()
	{
		return spareCatgMap;
	}

	public void setSpareCatgMap(Map<Integer, String> spareCatgMap)
	{
		this.spareCatgMap = spareCatgMap;
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

	public boolean isSpareNonMoveAltActive()
	{
		return spareNonMoveAltActive;
	}

	public void setSpareNonMoveAltActive(boolean spareNonMoveAltActive)
	{
		this.spareNonMoveAltActive = spareNonMoveAltActive;
	}

	public boolean isSpareNonMovePeriodActive()
	{
		return spareNonMovePeriodActive;
	}

	public void setSpareNonMovePeriodActive(boolean spareNonMovePeriodActive)
	{
		this.spareNonMovePeriodActive = spareNonMovePeriodActive;
	}

	public Map<Integer, String> getNonMoveMap()
	{
		return nonMoveMap;
	}

	public void setNonMoveMap(Map<Integer, String> nonMoveMap)
	{
		this.nonMoveMap = nonMoveMap;
	}

	public Map<Integer, String> getNonMovePeriodMap()
	{
		return nonMovePeriodMap;
	}

	public void setNonMovePeriodMap(Map<Integer, String> nonMovePeriodMap)
	{
		this.nonMovePeriodMap = nonMovePeriodMap;
	}

	public boolean isVendorActive()
	{
		return vendorActive;
	}

	public void setVendorActive(boolean vendorActive)
	{
		this.vendorActive = vendorActive;
	}

	public Map<Integer, String> getVendorMap()
	{
		return vendorMap;
	}

	public void setVendorMap(Map<Integer, String> vendorMap)
	{
		this.vendorMap = vendorMap;
	}

	public String getHeadingVendorName()
	{
		return headingVendorName;
	}

	public void setHeadingVendorName(String headingVendorName)
	{
		this.headingVendorName = headingVendorName;
	}

	public Map<Integer, String> getSpareNameMap()
	{
		return spareNameMap;
	}

	public void setSpareNameMap(Map<Integer, String> spareNameMap)
	{
		this.spareNameMap = spareNameMap;
	}

	public Map<String, String> getConsumeNonConsumeSpareMap()
	{
		return consumeNonConsumeSpareMap;
	}

	public void setConsumeNonConsumeSpareMap(Map<String, String> consumeNonConsumeSpareMap)
	{
		this.consumeNonConsumeSpareMap = consumeNonConsumeSpareMap;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public List<ConfigurationUtilBean> getSpareCatgColumnMap()
	{
		return spareCatgColumnMap;
	}

	public void setSpareCatgColumnMap(List<ConfigurationUtilBean> spareCatgColumnMap)
	{
		this.spareCatgColumnMap = spareCatgColumnMap;
	}

	public List<ConfigurationUtilBean> getSpareColumnMap()
	{
		return spareColumnMap;
	}

	public void setSpareColumnMap(List<ConfigurationUtilBean> spareColumnMap)
	{
		this.spareColumnMap = spareColumnMap;
	}

	public List<ConfigurationUtilBean> getDateMap()
	{
		return dateMap;
	}

	public void setDateMap(List<ConfigurationUtilBean> dateMap)
	{
		this.dateMap = dateMap;
	}

	public List<ConfigurationUtilBean> getCommonDDMap()
	{
		return commonDDMap;
	}

	public void setCommonDDMap(List<ConfigurationUtilBean> commonDDMap)
	{
		this.commonDDMap = commonDDMap;
	}

}
