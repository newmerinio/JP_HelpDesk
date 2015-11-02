package com.Over2Cloud.ctrl.asset;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetDashboardAction extends ActionSupport
{
	static final Log log = LogFactory.getLog(AssetDashboardAction.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String username = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private Map<String, String> columnMap = null;
	private List<Object> masterViewList;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	AssetDashboardBean assetForDashboard;
	AssetDashboardBean spareForDashboard;
	AssetDashboardBean assetRankForDashboard;
	AssetDashboardBean userRankForDashboard;
	AssetDashboardBean supportForDashboard;
	AssetDashboardBean expireForDashboard;
	Map<String, Integer> supportData;
	Map<String, Integer> preventiveData;
	private String tableName = null;
	private String column4 = null;
	private String tableAllis = null;
	private String dataFor;
	private String type;
	private String deptId;
	private String assetid;
	int size = 0;
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private String oper;
	private String id;
	private String mainHeaderName = null;
	private String value = null;
	private String mode;
	private int gridWidth;
	private int gridHeight;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private double randomNum;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private JSONArray jsonArrayAssetTypeRank;
	private JSONObject jsonObjectAssetTypeRank;
	private JSONArray jsonArrayAssetUserRank;
	private JSONObject jsonObjectAssetUserRank;
	private List<AssetDashboardBean> ticketsList = null;
	ComplianceUniversalAction CUA = new ComplianceUniversalAction();
	List<AssetDashboardBean> allAssetReminderList=null;
	List<AssetDashboardBean> allOutLetStatus=null;
	List<AssetDashboardBean> allOutLetPendingStatus=null;
	private String moduleName;
	private String parameterName;
	private String reminderId;
	private String totalPending;
	private String totalLevel1;
	private String totalLevel2;
	private String totalLevel3;
	private String totalLevel4;
	private String totalLevel5;
	private String totalSnooze;
	private String totalHighPriority;
	private String totalIgnore;
	private String totalResolve;
	private String totalCounter;

	@SuppressWarnings(
	{ "rawtypes" })
	public String getData4AssetDashboard()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String userEmpID=null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				String userContID = null;
				userContID=CUA.getEmpDetailsByUserName("ASTM",username)[0];
				if (logedDeptId != null)
				{
					allOutLetStatus=new ArrayList<AssetDashboardBean>();
					allOutLetStatus=AUH.getDataForTicketStatus(userEmpID,userContID,connectionSpace);
					if (allOutLetStatus!=null && allOutLetStatus.size()>0) 
					{
						int pending=0,level1=0,level2=0,level3=0,level4=0,level5=0,snooze=0,ignore=0,highPriority=0,resolve=0,total=0;
						for (AssetDashboardBean adb : allOutLetStatus) 
						{
							pending=pending+Integer.parseInt(adb.getPending());
							level1=level1+Integer.parseInt(adb.getLevel1());
							level2=level2+Integer.parseInt(adb.getLevel2());
							level3=level3+Integer.parseInt(adb.getLevel3());
							level4=level4+Integer.parseInt(adb.getLevel4());
							level5=level5+Integer.parseInt(adb.getLevel5());
							snooze=snooze+Integer.parseInt(adb.getSnooze());
							ignore=ignore+Integer.parseInt(adb.getIgnore());
							highPriority=highPriority+Integer.parseInt(adb.getHighpriority());
							resolve=resolve+Integer.parseInt(adb.getResolve());
							total=total+Integer.parseInt(adb.getCounter());
						}
						totalPending=String.valueOf(pending);
						totalLevel1=String.valueOf(level1);
						totalLevel2=String.valueOf(level2);
					    totalLevel3=String.valueOf(level3);
					    totalLevel4=String.valueOf(level4);
						totalLevel5=String.valueOf(level5);
						totalSnooze=String.valueOf(snooze);
						totalHighPriority=String.valueOf(ignore);
						totalIgnore=String.valueOf(highPriority);
						totalResolve=String.valueOf(resolve);
						totalCounter=String.valueOf(total);
					}
					
					//finalDataList = AUH.getTotalAsset(logedDeptId, connectionSpace, "asset");
					//assetForDashboard.setAssetList(finalDataList);

					/*
					 * List<AssetDashboardBean> finalDataList1 = new
					 * ArrayList<AssetDashboardBean>(); spareForDashboard = new
					 * AssetDashboardBean(); finalDataList1 =
					 * AUH.getTotalAsset(logedDeptId, connectionSpace, "Spare",
					 * deptHierarchy);
					 * spareForDashboard.setSpareList(finalDataList1);
					 */
				}
				
				if (logedDeptId != null)
				{
					allOutLetPendingStatus=new ArrayList<AssetDashboardBean>();
					allOutLetPendingStatus=AUH.getDataForPendingTicketStatus(userEmpID,connectionSpace);
				}
				
				
				
				
				
				
/*
				// Code for Asset Rank********
				if (logedDeptId != null)
				{
					List<AssetDashboardBean> finalDataList2 = new ArrayList<AssetDashboardBean>();
					assetRankForDashboard = new AssetDashboardBean();
					//finalDataList2 = AUH.getRankForAsset(logedDeptId, connectionSpace, "AssetRank");
					//Collections.sort(finalDataList2);
					System.out.println("dsnvsvbjbsjvd " + finalDataList2.size());
					//assetRankForDashboard.setAssetList(finalDataList2);
				}
				// Code for user wise Rank *********
				if (logedDeptId != null)
				{
					List<AssetDashboardBean> finalDataList3 = new ArrayList<AssetDashboardBean>();
					userRankForDashboard = new AssetDashboardBean();
					//finalDataList3 = AUH.getRankForAsset(logedDeptId, connectionSpace, "UserRank");
					//Collections.sort(finalDataList3);
					//userRankForDashboard.setAssetList(finalDataList3);
				}*/
			/*	// Code for Asset Due Action Matrix *********
				if (logedDeptId != null)
				{
					supportData = new LinkedHashMap<String, Integer>();
					preventiveData = new LinkedHashMap<String, Integer>();
					List<AssetDashboardBean> finalDataList4 = new ArrayList<AssetDashboardBean>();
					supportForDashboard = new AssetDashboardBean();
					finalDataList4 = AUH.supportDetails(logedDeptId, connectionSpace, "Support");
					supportForDashboard.setAssetList(finalDataList4);

					expireForDashboard = new AssetDashboardBean();
					List<AssetDashboardBean> finalDataList5 = new ArrayList<AssetDashboardBean>();
					finalDataList5 = AUH.supportDetails(logedDeptId, connectionSpace, "Preventive");
					expireForDashboard.setAssetList(finalDataList5);
					if (finalDataList4 != null && finalDataList4.size() > 0)
					{
						for (AssetDashboardBean adb : finalDataList4)
						{
							supportData.put("7 Days", Integer.parseInt(adb.getNext7Days()));
							supportData.put("30 Days", Integer.parseInt(adb.getNext30Days()));
							supportData.put("90 Days", Integer.parseInt(adb.getNext90Days()));
							supportData.put("180 Days", Integer.parseInt(adb.getNext180Days()));
						}
					}
					if (finalDataList5 != null && finalDataList5.size() > 0)
					{
						for (AssetDashboardBean adb : finalDataList5)
						{
							preventiveData.put("7 Days", Integer.parseInt(adb.getNext7Days()));
							preventiveData.put("30 Days", Integer.parseInt(adb.getNext30Days()));
							preventiveData.put("90 Days", Integer.parseInt(adb.getNext90Days()));
							preventiveData.put("180 Days", Integer.parseInt(adb.getNext180Days()));
						}
					}
					getTicketDetail("H", logedDeptId, "", connectionSpace);
				}*/
			/*	
				//Code For Asset Reminder
				if (logedDeptId != null )
				{
					
					allAssetReminderList = new ArrayList<AssetDashboardBean>();
					String userEmpID=null;
					String empIdofuser = (String) session.get("empIdofuser");
					if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;
					userEmpID = empIdofuser.split("-")[1].trim();
					String userContID = null;
					userContID=CUA.getEmpDetailsByUserName("ASTM",username)[0];
					allAssetReminderList = AUH.getAllReminderOfAsset(logedDeptId,userEmpID,userContID,connectionSpace);
				}*/
				
				
				
				
				// Code for Spare************
				// totalSpareGraph=assetHelper.getSpareGraph(connectionSpace);

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getData4AssetDashboard of class "
						+ getClass(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getTicketDetail(String status_for, String deptid, String empName, SessionFactory connectionSpace)
	{
		try
		{
			List ticketData = new AssetUniversalAction().getLodgedTickets(deptid, status_for, empName, connectionSpace);
			ticketsList = new ArrayList<AssetDashboardBean>();
			if(ticketData != null && ticketData.size() > 0)
			{
				for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					AssetDashboardBean ADB = new AssetDashboardBean();
					ADB.setId(Integer.parseInt(object[0].toString()));
					ADB.setTicket_no(object[1].toString());
					ADB.setFeed_by(DateUtil.makeTitle(object[2].toString()));
					ADB.setOpen_date(DateUtil.convertDateToIndianFormat(object[3].toString()));
					ADB.setOpen_time(object[4].toString().substring(0, 5));
					ADB.setStatus(object[5].toString());
					ticketsList.add(ADB);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String showBarChart()
	{
		try
		{
			String logedDeptId = null;
			logedDeptId = CUA.getEmpDataByUserName(username)[3];
			AssetUniversalHelper AUH = new AssetUniversalHelper();
			List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
			if (logedDeptId != null)
			{
				assetForDashboard = new AssetDashboardBean();
				//finalDataList = AUH.getTotalAsset(logedDeptId, connectionSpace, "asset");
			}
			jsonObject = new JSONObject();
			jsonArray = new JSONArray();

			for (AssetDashboardBean pojo : finalDataList)
			{
				jsonObject.put("AssetType", pojo.getAssetName());
				jsonObject.put("Total Asset", pojo.getTotalAsset());
				jsonObject.put("Total Allot", pojo.getTotalAllot());
				jsonObject.put("Total Free", pojo.getTotalFree());
				jsonArray.add(jsonObject);
			}
			return SUCCESS;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String showBarChartAssetTypeRank()
	{
		try
		{
			String logedDeptId = null;
			logedDeptId = CUA.getEmpDataByUserName(username)[3];
			AssetUniversalHelper AUH = new AssetUniversalHelper();
			List<AssetDashboardBean> finalDataList2 = new ArrayList<AssetDashboardBean>();
			if (logedDeptId != null)
			{
				assetForDashboard = new AssetDashboardBean();
				finalDataList2 = AUH.getRankForAsset(logedDeptId, connectionSpace, "AssetRank");
			}
			jsonObjectAssetTypeRank = new JSONObject();
			jsonArrayAssetTypeRank = new JSONArray();
			for (AssetDashboardBean pojo : finalDataList2)
			{
				jsonObjectAssetTypeRank.put("AssetType", pojo.getAssetType());
				jsonObjectAssetTypeRank.put("TotalBreakScore", pojo.getAssetScore());
				jsonArrayAssetTypeRank.add(jsonObjectAssetTypeRank);
			}
			return SUCCESS;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String showBarChartAssetUserRank()
	{
		try
		{/*
			String logedDeptId = null;
			logedDeptId = CUA.getEmpDataByUserName(username)[3];
			AssetUniversalHelper AUH = new AssetUniversalHelper();
			List<AssetDashboardBean> finalDataList3 = new ArrayList<AssetDashboardBean>();
			if (logedDeptId != null)
			{
				assetForDashboard = new AssetDashboardBean();
				finalDataList3 = AUH.getRankForAsset(logedDeptId, connectionSpace, "UserRank");
			}
			jsonObjectAssetUserRank = new JSONObject();
			jsonArrayAssetUserRank = new JSONArray();

			for (AssetDashboardBean pojo : finalDataList3)
			{
				jsonObjectAssetUserRank.put("EmpName", pojo.getEmpName());
				jsonObjectAssetUserRank.put("TotalBreakScore", pojo.getAssetScore());
				jsonArrayAssetUserRank.add(jsonObjectAssetUserRank);
			}
		*/
			return SUCCESS;	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String getDataAssetDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					List<AssetDashboardBean> finalDataList = new ArrayList<AssetDashboardBean>();
					assetForDashboard = new AssetDashboardBean();
					//finalDataList = AUH.getTotalAsset(logedDeptId, connectionSpace, "asset");
					assetForDashboard.setAssetList(finalDataList);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;
	}

	public String getDtaAssetDataGraph()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				randomNum = Math.random();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;

	}

	@SuppressWarnings("rawtypes")
	public String getDataPerformanceRank()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					List<AssetDashboardBean> finalDataList2 = new ArrayList<AssetDashboardBean>();
					assetRankForDashboard = new AssetDashboardBean();
					finalDataList2 = AUH.getRankForAsset(logedDeptId, connectionSpace, "AssetRank");
					Collections.sort(finalDataList2);
					assetRankForDashboard.setAssetList(finalDataList2);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String getDataUserRank()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					List<AssetDashboardBean> finalDataList3 = new ArrayList<AssetDashboardBean>();
					userRankForDashboard = new AssetDashboardBean();
					finalDataList3 = AUH.getRankForAsset(logedDeptId, connectionSpace, "UserRank");
					Collections.sort(finalDataList3);
					userRankForDashboard.setAssetList(finalDataList3);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String getSupportGraphDueMatrix()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					List<AssetDashboardBean> finalDataList4 = new ArrayList<AssetDashboardBean>();
					supportForDashboard = new AssetDashboardBean();
					finalDataList4 = AUH.supportDetails(logedDeptId, connectionSpace, "Support");
					supportForDashboard.setAssetList(finalDataList4);

					expireForDashboard = new AssetDashboardBean();
					List<AssetDashboardBean> finalDataList5 = new ArrayList<AssetDashboardBean>();
					finalDataList5 = AUH.supportDetails(logedDeptId, connectionSpace, "Preventive");
					expireForDashboard.setAssetList(finalDataList5);

				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;

	}

	@SuppressWarnings("rawtypes")
	public String getSupportGraph()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				randomNum = Math.random();
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					supportData = new LinkedHashMap<String, Integer>();
					List<AssetDashboardBean> finalDataList4 = new ArrayList<AssetDashboardBean>();
					finalDataList4 = AUH.supportDetails(logedDeptId, connectionSpace, "Support");
					if (finalDataList4 != null && finalDataList4.size() > 0)
					{
						for (AssetDashboardBean adb : finalDataList4)
						{
							supportData.put("7 Days", Integer.parseInt(adb.getNext7Days()));
							supportData.put("30 Days", Integer.parseInt(adb.getNext30Days()));
							supportData.put("90 Days", Integer.parseInt(adb.getNext90Days()));
							supportData.put("180 Days", Integer.parseInt(adb.getNext180Days()));
						}
					}
				}
				if (mode != null && mode.equalsIgnoreCase("refresh"))
				{
					setGridHeight(300);
					setGridWidth(200);
				}
				else if (mode != null && mode.equalsIgnoreCase("maximize"))
				{
					setGridHeight(400);
					setGridWidth(300);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;

	}

	@SuppressWarnings("rawtypes")
	public String getAssetExpireGraph()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				randomNum = Math.random();
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					preventiveData = new LinkedHashMap<String, Integer>();
					List<AssetDashboardBean> finalDataList4 = new ArrayList<AssetDashboardBean>();
					finalDataList4 = AUH.supportDetails(logedDeptId, connectionSpace, "Preventive");
					if (finalDataList4 != null && finalDataList4.size() > 0)
					{
						for (AssetDashboardBean adb : finalDataList4)
						{
							preventiveData.put("7 Days", Integer.parseInt(adb.getNext7Days()));
							preventiveData.put("30 Days", Integer.parseInt(adb.getNext30Days()));
							preventiveData.put("90 Days", Integer.parseInt(adb.getNext90Days()));
							preventiveData.put("180 Days", Integer.parseInt(adb.getNext180Days()));
						}
					}
				}
				if (mode != null && mode.equalsIgnoreCase("refresh"))
				{
					setGridHeight(300);
					setGridWidth(200);
				}
				else if (mode != null && mode.equalsIgnoreCase("maximize"))
				{
					setGridHeight(400);
					setGridWidth(300);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public String getAssetTicketData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String logedDeptId = null;
				logedDeptId = CUA.getEmpDataByUserName(username)[3];
				if (logedDeptId != null)
				{
					getTicketDetail("H", logedDeptId, "", connectionSpace);
				}

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = ERROR;
		}
		return returnResult;
	}

	public String getAllAssetData()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getColumn4View("mapped_asset_master", "asset_master", dataFor, type);
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createCatgViewPage of class " + getClass(),
						e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	private void getColumn4View(String mappedTable, String table, String dataFor, String type)
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name", table);
		if (statusColName != null && statusColName.size() > 0)
		{
			if (dataFor.equalsIgnoreCase("Allot"))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("employeeid");
				gpv.setHeadingName("Employee Name");
				gpv.setEditable("false");
				gpv.setSearch("false");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);
			}
			for (GridDataPropertyView gdp : statusColName)
			{
				if (!gdp.getColomnName().equalsIgnoreCase("deptHierarchy") && !gdp.getColomnName().equalsIgnoreCase("dept_subdept") && !gdp.getColomnName().equalsIgnoreCase("astcategory")
						&& !gdp.getColomnName().equalsIgnoreCase("glCode") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("assettype"))
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
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public String viewAssetCategory()
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
				StringBuilder query = new StringBuilder("");
				query.append("select count(*) from asset_detail");
				List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
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
					if (dataFor != null && type != null)
					{
						List fieldNames = Configuration.getColomnList("mapped_asset_master", accountID, connectionSpace, "asset_master");
						query.setLength(0);

						if (dataFor.equalsIgnoreCase("Total"))
						{
							query.append("select ");
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
											if (obdata.toString().equalsIgnoreCase("supportfor"))
											{
												query.append(" concat(supportfor,' Months') , ");
											}
											else if (obdata.toString().equalsIgnoreCase("vendorname"))
											{
												query.append(" vendor.vendorname, ");
											}
											else
											{
												query.append("suprt." + obdata.toString() + ",");
											}
										}
										else
										{
											query.append(obdata.toString());
										}
									}
									i++;
								}
							}
							query.append(" from asset_detail as suprt");
							query.append(" INNER JOIN createvendor_master AS vendor ON vendor.id=suprt.vendorname");
							query.append(" where suprt.dept_subdept ='" + deptId + "' AND suprt.assettype='" + type + "' AND suprt.status='Active'");
							query.append(" limit " + from + "," + to);
							System.out.println("TOTAL ===  " + query.toString());
						}
						if (dataFor.equalsIgnoreCase("Allot"))
						{
							query.append("select ");
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
											if (obdata.toString().equalsIgnoreCase("supportfor"))
											{
												query.append(" concat(supportfor,' Months') , ");
											}
											else if (obdata.toString().equalsIgnoreCase("vendorname"))
											{
												query.append(" vendor.vendorname, ");
											}
											else
											{
												query.append("ad." + obdata.toString() + ",");
											}
										}
										else
											query.append("ad." + obdata.toString());
									}
									i++;
								}
							}
							query.append(" ,emp.empName from asset_detail as ad ");
							query.append("INNER JOIN asset_allotment_detail as aal on ad.id=aal.assetid  ");
							query.append(" INNER JOIN createvendor_master AS vendor ON vendor.id=ad.vendorname ");
							query.append("INNER JOIN compliance_contacts as cc on cc.id=aal.employeeid INNER JOIN employee_basic as emp on emp.id=cc.emp_id  WHERE ad.status='Active' AND aal.flag=1 ");
							query.append("AND aal.dept_subdept='" + deptId + "' AND ad.assettype='" + type + "'");
							System.out.println("Allted ===  " + query.toString());
							fieldNames.add("employeeid");
						}
						if (dataFor.equalsIgnoreCase("Free"))
						{
							query.append("SELECT ");
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
											if (obdata.toString().equalsIgnoreCase("supportfor"))
											{
												query.append(" concat(supportfor,' Months') , ");
											}
											else if (obdata.toString().equalsIgnoreCase("vendorname"))
											{
												query.append(" vendor.vendorname, ");
											}
											else
											{
												query.append("ad." + obdata.toString() + ",");
											}
										}
										else
											query.append("ad." + obdata.toString());
									}
									i++;
								}
							}
							query.append("  from asset_detail as ad ");
							query.append(" INNER JOIN createvendor_master AS vendor ON vendor.id=ad.vendorname ");
							query.append(" WHERE ad.id NOT IN(SELECT assetid FROM asset_allotment_detail WHERE flag=1) AND ad.status='Active' AND  ");
							query.append(" ad.dept_subdept='" + deptId + "' AND ad.assettype='" + type + "'");
							System.out.println("Free Asset ===  " + query.toString());
						}
						if (dataFor.equalsIgnoreCase("performanceRank"))
						{
							query.append("SELECT ");
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
											if (obdata.toString().equalsIgnoreCase("supportfor"))
											{
												query.append(" concat(supportfor,' Months') , ");
											}
											else if (obdata.toString().equalsIgnoreCase("vendorname"))
											{
												query.append(" vendor.vendorname, ");
											}
											else
											{
												query.append("ad." + obdata.toString() + ",");
											}
										}
										else
											query.append("ad." + obdata.toString());
									}
									i++;
								}
							}
							query.append("  from asset_detail as ad ");
							query.append(" INNER JOIN asset_complaint_status AS acs ON acs.asset_id=ad.id  ");
							query.append(" INNER JOIN createvendor_master AS vendor ON vendor.id=ad.vendorname ");
							query.append(" WHERE ad.dept_subdept='" + deptId + "' AND ad.assettype='" + type + "' AND ad.status='Active' ");
							System.out.println("PERFORMANCE RANK ===  " + query.toString());
						}
						if (dataFor.equalsIgnoreCase("User"))
						{
							query.append("SELECT ");
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
											if (obdata.toString().equalsIgnoreCase("supportfor"))
											{
												query.append(" concat(supportfor,' Months') , ");
											}
											else if (obdata.toString().equalsIgnoreCase("vendorname"))
											{
												query.append(" vendor.vendorname, ");
											}
											else
											{
												query.append("asset." + obdata.toString() + ",");
											}
										}
										else
											query.append("asset." + obdata.toString());
									}
									i++;
								}
							}
							query.append("  FROM asset_complaint_status AS asset_complaint  ");
							query.append(" INNER JOIN asset_allotment_log AS asset_allot ON asset_complaint.asset_id=asset_allot.assetid");
							query.append(" INNER JOIN compliance_contacts AS contacts ON asset_allot.employeeid=contacts.id");
							query.append(" INNER JOIN employee_basic AS employee ON contacts.emp_id=employee.id");
							query.append(" INNER JOIN asset_detail AS asset ON asset.id=asset_allot.assetid");
							query.append(" INNER JOIN createvendor_master AS vendor ON vendor.id=asset.vendorname ");
							query.append(" WHERE asset.dept_subdept=" + deptId + " AND employee.id='" + type + "'");
							System.out.println("USER RANK ===  " + query.toString());

						}
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
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else if (fieldNames.get(k).toString().equalsIgnoreCase("supportfrom"))
											{
												tempMap.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
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
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
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

	public String getAllSupportData()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println(">>>>>>>>>>>>>>>");
				getColumn4Support("mapped_asset_reminder", "asset_reminder");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createCatgViewPage of class " + getClass(),
						e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	private void getColumn4Support(String mappedTable, String table)
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("actionStatus");
		gpv.setHeadingName("Status");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("true");
		gpv.setWidth(150);
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name",table);
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				if (gdp.getColomnName().equals("assetid"))
				{
					List<GridDataPropertyView> statusColName1 = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
					if (statusColName1 != null && statusColName1.size() > 0)
					{
						for (GridDataPropertyView gdp1 : statusColName1)
						{
							if (gdp1.getColomnName().equalsIgnoreCase("assetname") )
							{
								gpv = new GridDataPropertyView();
								gpv.setColomnName(gdp1.getColomnName());
								gpv.setHeadingName(gdp1.getHeadingName());
								gpv.setEditable(gdp1.getEditable());
								gpv.setSearch(gdp1.getSearch());

								if ((moduleName != null && moduleName.equalsIgnoreCase("Preventive") && gdp1.getColomnName().toString().equalsIgnoreCase("supportfrom"))
										|| gdp1.getColomnName().equalsIgnoreCase("receivedOn") || gdp1.getColomnName().equalsIgnoreCase("commssioningon")
										|| gdp1.getColomnName().equalsIgnoreCase("installon"))
								{
									gpv.setHideOrShow("true");
								}
								else
								{
									gpv.setHideOrShow(gdp1.getHideOrShow());
								}
								gpv.setWidth(gdp1.getWidth());
								masterViewProp.add(gpv);
							}
						}
					}
				}
				else if (!gdp.getColomnName().equals("supporttype") && !gdp.getColomnName().equals("vendorid") && !gdp.getColomnName().equals("assetid") && !gdp.getColomnName().equals("actionStatus") && !gdp.getColomnName().equals("escalation_level_1") && !gdp.getColomnName().equals("escalation_level_2") && !gdp.getColomnName().equals("escalation_level_3") && !gdp.getColomnName().equals("escalation_level_4"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					if (gdp.getColomnName().equals("dueDate") || gdp.getColomnName().equals("snooze_date"))
					{
						gpv.setHeadingName(gdp.getHeadingName() + " & Time");
					}
					else
					{
						gpv.setHeadingName(gdp.getHeadingName());
					}
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
				else if((gdp.getColomnName().equals("supporttype") || gdp.getColomnName().equals("vendorid")) && moduleName!=null && moduleName.equalsIgnoreCase("Support") )
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
		
		/*gpv=new GridDataPropertyView();
		gpv.setColomnName("escMatix");
		gpv.setHeadingName("Escalation Matrix");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setFormatter("testAbc");
		gpv.setWidth(2000);
		masterViewProp.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("download");
		gpv.setHeadingName("Document");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setFormatter("downloadDoc");
		gpv.setWidth(2000);
		masterViewProp.add(gpv);*/
	}

	public String viewTotalSupportData()
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
				query1.append("select count(*) from asset_reminder_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					StringBuilder columnName = new StringBuilder("");
					List fieldNames = Configuration.getColomnList("mapped_asset_reminder", accountID, connectionSpace, "asset_reminder");
					List finalFieldName = new ArrayList();
					int i = 0;
					int j = 0;
					String finalDate = null;
					if (dataFor!= null && dataFor.equalsIgnoreCase("next90Days"))
					{
						finalDate = DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
					}
					else if (dataFor != null && dataFor.equalsIgnoreCase("next30Days"))
					{
						finalDate = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
					}
					else if (dataFor != null && dataFor.equalsIgnoreCase("next7Days"))
					{
						finalDate = DateUtil.getNewDate("day", 90, DateUtil.getCurrentDateUSFormat());
					}
					
					if (fieldNames != null && fieldNames.size() > 0)
					{
						Object obdata = null;
						query.append("SELECT ");
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if (obdata.toString().equalsIgnoreCase("vendorid") && moduleName!=null && moduleName.equalsIgnoreCase("Support"))
									{
										columnName.append("vendor.vendorname,");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else if (obdata.toString().equalsIgnoreCase("assetid"))
									{
										List fieldNames1 = Configuration.getColomnList("mapped_asset_master", accountID, connectionSpace, "asset_master");
										if (fieldNames1 != null && fieldNames1.size() > 0)
										{
											Object obj = null;
											for (Iterator iterator = fieldNames1.iterator(); iterator.hasNext();)
											{
												obj = (Object) iterator.next();
												if (obj != null)
												{
													if (j < fieldNames1.size() - 1)
													{
														if (obj.toString().equalsIgnoreCase("assetname") || obj.toString().equalsIgnoreCase("serialno")
																|| obj.toString().equalsIgnoreCase("assetbrand") || obj.toString().equalsIgnoreCase("assetmodel")
																|| obj.toString().equalsIgnoreCase("supportfrom") || obj.toString().equalsIgnoreCase("commssioningon")
																|| obj.toString().equalsIgnoreCase("receivedOn") || obj.toString().equalsIgnoreCase("installon"))
														{
															columnName.append("asd." + obj.toString() + ",");
															finalFieldName.add(obj.toString());
															i++;
														}
													}
												}
											}
										}
									}
									else if (obdata.toString().equalsIgnoreCase("supporttype") && moduleName!=null && moduleName.equalsIgnoreCase("Support"))
									{
										columnName.append("suprtcatg.category,");
										finalFieldName.add("supporttype");
										i++;
									}
									else if (!obdata.toString().equalsIgnoreCase("assetid") && !obdata.toString().equalsIgnoreCase("reminder1") && !obdata.toString().equalsIgnoreCase("reminder2")
											&& !obdata.toString().equalsIgnoreCase("reminder3") && !obdata.toString().equals("escalation_level_1") && !obdata.toString().equals("escalation_level_2") && !obdata.toString().equals("escalation_level_3") && !obdata.toString().equals("escalation_level_4"))
									{
										columnName.append("suprt." + obdata.toString() + ",");
										finalFieldName.add(obdata.toString());
										i++;
									}
								}
							}
						}
					}
					query.append(columnName.substring(0, columnName.toString().length() - 1));
					query.append(" FROM asset_reminder_details AS suprt");
					query.append(" LEFT JOIN createvendor_master AS vendor ON vendor.id=suprt.vendorid");
					query.append(" LEFT JOIN createasset_support_catg_master AS suprtcatg ON suprt.supporttype=suprtcatg.id ");
					query.append(" INNER JOIN asset_detail AS asd ON asd.id=suprt.assetid ");
					query.append(" WHERE suprt.id IN(" + reminderId + ") AND asd.status='Active' ");
					
					if(finalDate!=null)
					{
						query.append(" AND suprt.dueDate BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + finalDate + "' AND suprt.actionStatus!='Done' AND suprt.actionStatus!='Recurring'");
					}
					else if(dataFor!=null && dataFor.equalsIgnoreCase("missed"))
					{
						query.append(" AND suprt.dueDate <= '" + DateUtil.getCurrentDateUSFormat() + "' AND suprt.dueTime<= '"+DateUtil.getCurrentTimeHourMin()+"' AND suprt.actionStatus!='Done' AND suprt.actionStatus!='Recurring'");
					}
					
String qry="";
					query.append(" ORDER BY asd.assetname");

					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Object[] obdata = null;
						Map<String, Object> tempMap = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							for (int k = 0; k < finalFieldName.size(); k++)
							{
								if (obdata[k] != null && !obdata[k].equals(""))
								{
									if (finalFieldName.get(k).toString().equalsIgnoreCase("id"))
									{
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										String reminQry = "SELECT reminder_name,remind_date from asset_reminder_data WHERE asseReminder_id IN(" + obdata[k].toString() + ")";
										System.out.println("REMINDER QUERY ::; "+reminQry);
										List remData = cbt.executeAllSelectQuery(reminQry, connectionSpace);
										if (remData != null && remData.size() > 0)
										{
											boolean rem1 = false, rem2 = false, rem3 = false;
											for (Iterator iterator = remData.iterator(); iterator.hasNext();)
											{
												Object[] object = (Object[]) iterator.next();
												if (object[1] != null && object[0] != null)
												{
													if (object[0].toString().equalsIgnoreCase("Reminder 1"))
													{
														tempMap.put("reminder1", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem1 = true;
													}
													else if (object[0].toString().equalsIgnoreCase("Reminder 2"))
													{
														tempMap.put("reminder2", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem2 = true;
													}
													else if (object[0].toString().equalsIgnoreCase("Reminder 3"))
													{
														tempMap.put("reminder3", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem3 = true;
													}
													if (!rem1)
													{
														tempMap.put("reminder1", "NA");
													}
													if (!rem2)
													{
														tempMap.put("reminder2", "NA");
													}
													if (!rem3)
													{
														tempMap.put("reminder3", "NA");
													}
												}
											}
										}
										 if (remData!=null && remData.size()==1) 
										   {
											 tempMap.put("reminder1","NA");
											 tempMap.put("reminder2","NA");
											 tempMap.put("reminder3","NA");
										   }
										   if (remData!=null && remData.size()==2) 
										   {
											   tempMap.put("reminder2","NA");
											   tempMap.put("reminder3","NA");
										   }
										   if (remData!=null && remData.size()==3) 
										   {
											   tempMap.put("reminder3","NA");
										   }
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("dueDate") || finalFieldName.get(k).toString().equalsIgnoreCase("snooze_date"))
									{
										tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + " " + obdata[k + 1].toString());
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("supportfrom"))
									{
										if (obdata[k].toString().equalsIgnoreCase("receivedOn"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 3].toString()));
										}
										else if (obdata[k].toString().equalsIgnoreCase("installOn"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 2].toString()));
										}
										else if (obdata[k].toString().equalsIgnoreCase("commssioningon"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 1].toString()));
										}
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("reminder_for") || finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_1")
											|| finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_2") || finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_3")
											|| finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_4"))
									{
										StringBuilder empName = new StringBuilder();
										String empId = obdata[k].toString().replace("#", ",").substring(0, (obdata[k].toString().length() - 1));
										String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
										List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
										for (Iterator iterator = data1.iterator(); iterator.hasNext();)
										{
											Object object = (Object) iterator.next();
											empName.append(object.toString() + ", ");
										}
										if (empName != null && !empName.toString().equalsIgnoreCase(""))
											tempMap.put(finalFieldName.get(k).toString(), empName.toString().substring(0, empName.toString().length() - 2));
										else
											tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("action_type"))
									{
										tempMap.put(finalFieldName.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("frequency"))
									{
										tempMap.put(finalFieldName.get(k).toString(), new AssetUniversalHelper().getFrequencyName(obdata[k].toString()));
									}
									else if (obdata[k] == null)
									{
										tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
									else
									{
										tempMap.put(finalFieldName.get(k).toString(), obdata[k].toString());
									}
								}
								/*else if(!finalFieldName.get(k).toString().equalsIgnoreCase("reminder1") && !finalFieldName.get(k).toString().equalsIgnoreCase("reminder2") && !finalFieldName.get(k).toString().equalsIgnoreCase("reminder3"))
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}*/
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
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSupport of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String getAllRankData()
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

				if (type != null)
				{
					if (type.equalsIgnoreCase("Asset"))
					{
						setMainHeaderName("Asset Rank View");
						getColumnAssetRank("mapped_asset_master", "asset_master", type);
					}
					if (type.equalsIgnoreCase("User"))
					{
						setMainHeaderName("User Rank View");
						getColumnAssetRank("mapped_asset_master", "asset_master", type);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getAllRankData of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	private void getColumnAssetRank(String mappedTable, String table, String type)
	{
		try
		{

			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name", table);
			if (type.equalsIgnoreCase("Asset") || type.equalsIgnoreCase("User"))
			{
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equals("dept_subdept") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("astcategory")
								&& !gdp.getColomnName().equals("userName") && !gdp.getColomnName().equals("date") && !gdp.getColomnName().equals("time") && !gdp.getColomnName().equals("flag")
								&& !gdp.getColomnName().equals("assettype"))
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
					gpv = new GridDataPropertyView();
					gpv.setColomnName("date");
					gpv.setHeadingName("Entered Date");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("resolveon");
					gpv.setHeadingName("Resolve On");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					masterViewProp.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public String viewTotalAssetRank()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");

				if (type != null)
				{
					if (type.equalsIgnoreCase("Asset"))
					{
						query1.append("select count(*) from asset_detail");
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

							// getting the list of colmuns
							String query223 = "select field_value from asset_master where id IN ('1','2','3','4','5')";
							List fieldNames = cbt.executeAllSelectQuery(query223, connectionSpace);
							fieldNames.add("date");
							fieldNames.add("resolveon");
							StringBuilder query = new StringBuilder("");
							query.append("SELECT ");
							query.append("ad.assetname,ad.serialno,ad.assetbrand,ad.assetmodel,ad.specification,ar.date,ar.resolveon  ");
							query.append("FROM asset_repair_detail  as ar ");
							query.append("INNER JOIN asset_detail as ad on ad.id=ar.assetid ");
							query.append("WHERE assetid IN(SELECT assetid FROM asset_allotment_log WHERE dept_subdept='" + deptId + "') AND assetid='" + assetid + "' ");
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
											if (fieldNames.get(k).toString().equalsIgnoreCase("date") || fieldNames.get(k).toString().equalsIgnoreCase("resolveon"))
											{
												tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else
											{

												tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}

									}
									tempList.add(tempMap);
								}
								setMasterViewList(tempList);
								setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							}
						}
					}
					if (type.equalsIgnoreCase("User"))
					{

						query1.append("select count(*) from asset_detail");
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

							// getting the list of colmuns
							String query223 = "select field_value from asset_master where id IN ('1','2','3','4','5')";
							StringBuilder query = new StringBuilder("");
							query.append("SELECT ");
							List fieldNames = cbt.executeAllSelectQuery(query223, connectionSpace);
							fieldNames.add("date");
							fieldNames.add("resolveon");

							query.append("ad.assetname,ad.serialno,ad.assetbrand,ad.assetmodel,ad.specification,ar.date,ar.resolveon ");
							query.append("from asset_repair_detail as ar ");
							query.append("INNER JOIN asset_detail as ad on ad.id=ar.assetid ");
							query.append("WHERE assetid IN(Select assetid from asset_allotment_log ");
							query.append("where dept_subdept='" + deptId + "' AND employeeid='" + assetid + "')");
							List data1 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

							if (data1 != null && data1.size() > 0)
							{
								List<Object> tempList = new ArrayList<Object>();
								for (Iterator it = data1.iterator(); it.hasNext();)
								{
									Object[] obdata = (Object[]) it.next();
									Map<String, Object> tempMap = new HashMap<String, Object>();
									for (int k = 0; k < fieldNames.size(); k++)
									{
										if (obdata[k] != null)
										{
											if (fieldNames.get(k).toString().equalsIgnoreCase("date") && fieldNames.get(k).toString().equalsIgnoreCase("resolveon"))
											{
												tempMap.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else
											{
												tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}
									}
									tempList.add(tempMap);
								}
								setMasterViewList(tempList);
								setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							}

						}
					}
					returnResult = SUCCESS;
				}
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

	public String getColumn4Download()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (column4 != null && column4.equals("allotmentDetail"))
				{
					tableAllis = "allot";
					returnResult = getColumnName("mapped_allotment_master", "allotment_master", tableAllis);
					tableName = "asset_allotment_log";
					mainHeaderName = "Deptment Data";
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getColumn4Download of class " + getClass(),
						e);
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
	public String getColumnName(String mappedTableName, String basicTableName, String allias)
	{
		String returnResult = ERROR;
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name", basicTableName);
			columnMap = new LinkedHashMap<String, String>();
			if (columnList != null && columnList.size() > 0)
			{
				for (GridDataPropertyView gdp1 : columnList)
				{
					if (gdp1.getColomnName().equals("dept_subdept") && session.get("userDeptLevel").toString().equals("2"))
					{
						columnMap.put("dept.deptName", new AssetCommonAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName"));
						columnMap.put("sdept.subdeptname", new AssetCommonAction().getFieldName("mapped_subdeprtmentconf", "subdeprtmentconf", "subdeptname"));
					}
					else if (gdp1.getColomnName().equals("dept_subdept") && session.get("userDeptLevel").toString().equals("1"))
					{
						columnMap.put("dept.deptName", new AssetCommonAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName"));
					}
					else if (gdp1.getColomnName().equals("employeeid"))
					{
						columnMap.put("emp.empName", new AssetCommonAction().getFieldName("mapped_employee_basic_configuration", "employee_basic_configuration", "empName"));
					}
					else if (gdp1.getColomnName().equals("assetid"))
					{
						columnMap.put("asset.assetname", new AssetCommonAction().getFieldName("mapped_asset_master", "asset_master", "assetname"));
					}
					/*
					 * else if(gdp1.getColomnName().equals("vendorname") ||
					 * gdp1.getColomnName().equals("vendorid")) {
					 * columnMap.put("vendor.vendorname", new
					 * AssetCommonAction().getMasterFieldName("vendor_master",
					 * "vendorname")); } else
					 * if(gdp1.getColomnName().equals("supporttype")) {
					 * columnMap.put("suprtcatg.support_type", new
					 * AssetCommonAction
					 * ().getMasterFieldName("asset_category_master",
					 * "support_type")); } else
					 * if(gdp1.getColomnName().equals("spare_category")) {
					 * columnMap.put("spc.spare_category", new
					 * AssetCommonAction(
					 * ).getMasterFieldName("spare_catg_master",
					 * "spare_category")); } else
					 * if(gdp1.getColomnName().equals("spare_name")) {
					 * columnMap.put("spr.spare_name", new
					 * AssetCommonAction().getFieldName
					 * ("mapped_spare_master","spare_master","spare_name")); }
					 */
					else if (!gdp1.getColomnName().equals("deptHierarchy"))
					{
						columnMap.put(allias + "." + gdp1.getColomnName(), gdp1.getHeadingName());

					}
				}
				if (value != null && value.equalsIgnoreCase("free"))
				{
					columnMap.put(allias + ".asset_freedate", "Free Date");
				}
			}
			if (columnMap != null && columnMap.size() > 0)
			{
				session.put("columnMap", columnMap);
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getColumnName of class " + getClass(), e);
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, String> tempMap = new LinkedHashMap<String, String>();
				List keyList = new ArrayList();
				tempMap = (Map<String, String>) session.get("columnMap");
				if (session.containsKey("columnMap"))
				{
					session.remove("columnMap");
				}
				List dataList = null;
				StringBuilder query = new StringBuilder("");
				if (tempMap != null && tempMap.size() > 0)
				{

					query.append("select ");
					int i = 0;
					Iterator<Entry<String, String>> hiterator = tempMap.entrySet().iterator();
					while (hiterator.hasNext())
					{
						Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
						if (i < tempMap.size() - 1)
						{
							query.append(paramPair.getKey().toString() + ",");
							keyList.add(paramPair.getKey().toString());
						}
						else
						{
							query.append(paramPair.getKey().toString());
							keyList.add(paramPair.getKey().toString());
						}
						i++;
					}
					query.append(" from " + tableName + " as " + tableAllis);
					if (tempMap.containsKey("sdept.subdeptname") && !tempMap.containsKey("dept.deptName"))
					{
						query.append(" left join subdepartment sdept on sdept.id=" + tableAllis + ".dept_subdept");
					}
					if (tempMap.containsKey("dept.deptName") && session.get("userDeptLevel").toString().equals("2"))
					{
						query.append(" left join subdepartment sdept on sdept.id=" + tableAllis + ".dept_subdept left join department dept on dept.id=sdept.deptid");
					}
					if (tempMap.containsKey("dept.deptName") && session.get("userDeptLevel").toString().equals("1"))
					{
						query.append(" left join department dept on dept.id=" + tableAllis + ".dept_subdept");
					}
					if (tempMap.containsKey("emp.empName"))
					{
						query.append(" left join employee_basic emp on emp.id=" + tableAllis + ".employeeid");
					}
					if (keyList.contains("asset.assetname"))
					{
						query.append(" left join asset_detail asset on asset.id=" + tableAllis + ".assetid");
					}
					if (value != null && value.equalsIgnoreCase("Alloted"))
					{
						query.append(" WHERE asset_freedate IS NULL");
					}
					if (value != null && value.equalsIgnoreCase("free"))
					{
						query.append(" WHERE asset_freedate IS NOT NULL");
					}
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = dataList.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> temp = new HashMap<String, Object>();
							for (int k = 0; k < keyList.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										temp.put(keyList.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else
									{
										temp.put(keyList.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									temp.put(keyList.get(k).toString(), "NA");
								}
							}
							tempList.add(temp);
						}
						setMasterViewList(tempList);
					}
				}

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewData of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String getColumn4()
	{
		return column4;
	}

	public void setColumn4(String column4)
	{
		this.column4 = column4;
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

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableAllis()
	{
		return tableAllis;
	}

	public void setTableAllis(String tableAllis)
	{
		this.tableAllis = tableAllis;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public AssetDashboardBean getAssetForDashboard()
	{
		return assetForDashboard;
	}

	public void setAssetForDashboard(AssetDashboardBean assetForDashboard)
	{
		this.assetForDashboard = assetForDashboard;
	}

	public AssetDashboardBean getSpareForDashboard()
	{
		return spareForDashboard;
	}

	public void setSpareForDashboard(AssetDashboardBean spareForDashboard)
	{
		this.spareForDashboard = spareForDashboard;
	}

	public AssetDashboardBean getAssetRankForDashboard()
	{
		return assetRankForDashboard;
	}

	public void setAssetRankForDashboard(AssetDashboardBean assetRankForDashboard)
	{
		this.assetRankForDashboard = assetRankForDashboard;
	}

	public AssetDashboardBean getUserRankForDashboard()
	{
		return userRankForDashboard;
	}

	public void setUserRankForDashboard(AssetDashboardBean userRankForDashboard)
	{
		this.userRankForDashboard = userRankForDashboard;
	}

	public AssetDashboardBean getSupportForDashboard()
	{
		return supportForDashboard;
	}

	public void setSupportForDashboard(AssetDashboardBean supportForDashboard)
	{
		this.supportForDashboard = supportForDashboard;
	}

	public Map<String, Integer> getSupportData()
	{
		return supportData;
	}

	public void setSupportData(Map<String, Integer> supportData)
	{
		this.supportData = supportData;
	}

	public AssetDashboardBean getExpireForDashboard()
	{
		return expireForDashboard;
	}

	public void setExpireForDashboard(AssetDashboardBean expireForDashboard)
	{
		this.expireForDashboard = expireForDashboard;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public double getRandomNum()
	{
		return randomNum;
	}

	public void setRandomNum(double randomNum)
	{
		this.randomNum = randomNum;
	}

	public String getAssetid()
	{
		return assetid;
	}

	public void setAssetid(String assetid)
	{
		this.assetid = assetid;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public int getGridWidth()
	{
		return gridWidth;
	}

	public void setGridWidth(int gridWidth)
	{
		this.gridWidth = gridWidth;
	}

	public int getGridHeight()
	{
		return gridHeight;
	}

	public void setGridHeight(int gridHeight)
	{
		this.gridHeight = gridHeight;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public JSONArray getJsonArrayAssetTypeRank()
	{
		return jsonArrayAssetTypeRank;
	}

	public void setJsonArrayAssetTypeRank(JSONArray jsonArrayAssetTypeRank)
	{
		this.jsonArrayAssetTypeRank = jsonArrayAssetTypeRank;
	}

	public JSONObject getJsonObjectAssetTypeRank()
	{
		return jsonObjectAssetTypeRank;
	}

	public void setJsonObjectAssetTypeRank(JSONObject jsonObjectAssetTypeRank)
	{
		this.jsonObjectAssetTypeRank = jsonObjectAssetTypeRank;
	}

	public List<AssetDashboardBean> getTicketsList()
	{
		return ticketsList;
	}

	public void setTicketsList(List<AssetDashboardBean> ticketsList)
	{
		this.ticketsList = ticketsList;
	}

	public JSONArray getJsonArrayAssetUserRank()
	{
		return jsonArrayAssetUserRank;
	}

	public void setJsonArrayAssetUserRank(JSONArray jsonArrayAssetUserRank)
	{
		this.jsonArrayAssetUserRank = jsonArrayAssetUserRank;
	}

	public JSONObject getJsonObjectAssetUserRank()
	{
		return jsonObjectAssetUserRank;
	}

	public void setJsonObjectAssetUserRank(JSONObject jsonObjectAssetUserRank)
	{
		this.jsonObjectAssetUserRank = jsonObjectAssetUserRank;
	}

	public Map<String, Integer> getPreventiveData()
	{
		return preventiveData;
	}

	public void setPreventiveData(Map<String, Integer> preventiveData)
	{
		this.preventiveData = preventiveData;
	}

	public List<AssetDashboardBean> getAllAssetReminderList()
	{
		return allAssetReminderList;
	}

	public void setAllAssetReminderList(List<AssetDashboardBean> allAssetReminderList)
	{
		this.allAssetReminderList = allAssetReminderList;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public void setParameterName(String parameterName)
	{
		this.parameterName = parameterName;
	}

	public String getReminderId()
	{
		return reminderId;
	}

	public void setReminderId(String reminderId)
	{
		this.reminderId = reminderId;
	}

	public List<AssetDashboardBean> getAllOutLetStatus() {
		return allOutLetStatus;
	}

	public void setAllOutLetStatus(List<AssetDashboardBean> allOutLetStatus) {
		this.allOutLetStatus = allOutLetStatus;
	}

	public String getTotalPending() {
		return totalPending;
	}

	public void setTotalPending(String totalPending) {
		this.totalPending = totalPending;
	}

	public String getTotalLevel1() {
		return totalLevel1;
	}

	public void setTotalLevel1(String totalLevel1) {
		this.totalLevel1 = totalLevel1;
	}

	public String getTotalLevel2() {
		return totalLevel2;
	}

	public void setTotalLevel2(String totalLevel2) {
		this.totalLevel2 = totalLevel2;
	}

	public String getTotalLevel3() {
		return totalLevel3;
	}

	public void setTotalLevel3(String totalLevel3) {
		this.totalLevel3 = totalLevel3;
	}

	public String getTotalLevel4() {
		return totalLevel4;
	}

	public void setTotalLevel4(String totalLevel4) {
		this.totalLevel4 = totalLevel4;
	}

	public String getTotalLevel5() {
		return totalLevel5;
	}

	public void setTotalLevel5(String totalLevel5) {
		this.totalLevel5 = totalLevel5;
	}

	public String getTotalSnooze() {
		return totalSnooze;
	}

	public void setTotalSnooze(String totalSnooze) {
		this.totalSnooze = totalSnooze;
	}

	public String getTotalHighPriority() {
		return totalHighPriority;
	}

	public void setTotalHighPriority(String totalHighPriority) {
		this.totalHighPriority = totalHighPriority;
	}

	public String getTotalIgnore() {
		return totalIgnore;
	}

	public void setTotalIgnore(String totalIgnore) {
		this.totalIgnore = totalIgnore;
	}

	public String getTotalResolve() {
		return totalResolve;
	}

	public void setTotalResolve(String totalResolve) {
		this.totalResolve = totalResolve;
	}

	public String getTotalCounter() {
		return totalCounter;
	}

	public void setTotalCounter(String totalCounter) {
		this.totalCounter = totalCounter;
	}

	public List<AssetDashboardBean> getAllOutLetPendingStatus() {
		return allOutLetPendingStatus;
	}

	public void setAllOutLetPendingStatus(
			List<AssetDashboardBean> allOutLetPendingStatus) {
		this.allOutLetPendingStatus = allOutLetPendingStatus;
	}

}
