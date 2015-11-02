package com.Over2Cloud.ctrl.asset.productivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetProductivity extends ActionSupport 
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	// Grid Variables Declaration
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
	private boolean loadonce = true;
	// Grid column view
	private String oper;
	private int id;
	private String deptId;
	private String subdeptId;
	private String fromDate;
	private String toDate;
	private String dataFor;
	private String byDeptId;
	private String moduleName=null;
	private Map<String, String> serviceDeptList = null;
	private Map<Integer, String> deptList = null;
	List<AssetDashboardBean> feedbackList = null;
	Map<String, Integer> graphMap = null;
	private Map<String, Object>	userdata;
	private List<GridDataPropertyView> masterViewProp=null;
	private 	List<Object> 	masterViewList;
	private String graphData=null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getAnalytics()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String deptHierarchy = (String) session.get("userDeptLevel");
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				HelpdeskUniversalAction HUA= new HelpdeskUniversalAction();
				serviceDeptList=new LinkedHashMap<String, String>();
				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = HUA.getServiceDept_SubDept(deptHierarchy,orgLevel, orgId,"ASTM", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0) 
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();) 
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put( object1[0].toString(), object1[1].toString());
					}
				}
				departmentlist.clear();
				departmentlist=HUA.getDataFromTable("department", colmName,wherClause, order, connectionSpace);
				if (departmentlist!=null && departmentlist.size()>0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							deptList.put ( Integer.parseInt(object[0].toString()),object[1].toString());
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
			return  LOGIN;
		}
	}
	public String getAnalyticsForGrid()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
				{
					return "employeeSucess";
				}
				else 
				{
					return "categorySucess";
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
			return  LOGIN;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String viewAnalysisDeatil()
	{
		String returnResult = SUCCESS;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List totalData = null;
				List onTimedata=null;
				List offTimedata=null;
				List snooze=null;
				List missed=null;
				List ignore=null;
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				AssetUniversalHelper AUH = new AssetUniversalHelper();
				feedbackList = new ArrayList<AssetDashboardBean>();
				if (fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase(""))
				{
					totalData = new AssetUniversalAction().getAnalyticalGridReport(
							dataFor, DateUtil.convertDateToUSFormat(fromDate),
							DateUtil.convertDateToUSFormat(toDate), deptId,
							subdeptId, searchField, searchString, searchOper,connectionSpace,"Total","ASTM",byDeptId);
				    
					if (totalData!=null && totalData.size()>0)
					{
						
						onTimedata = new AssetUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Ontime","ASTM",byDeptId);
					
						offTimedata= new AssetUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Offtime","ASTM",byDeptId);
						
						snooze= new AssetUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Snooze","ASTM",byDeptId);
					
						missed= new AssetUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Missed","ASTM",byDeptId);
					    
						ignore= new AssetUniversalAction().getAnalyticalGridReport(
								dataFor, DateUtil.convertDateToUSFormat(fromDate),
								DateUtil.convertDateToUSFormat(toDate), deptId,
								subdeptId, searchField, searchString, searchOper,connectionSpace,"Ignore","ASTM",byDeptId);
					}
				}
				if (totalData != null && totalData.size() > 0)
				{
					setRecords(totalData.size());
					int to = (getRows() * getPage());
					
					if (to > getRecords()) to = getRecords();
					
					if (dataFor.equals("Employee"))
					{
					   feedbackList = AUH.setAnalyticalReportValues(totalData,onTimedata,offTimedata,snooze,missed,ignore,dataFor);
					}
					else if (dataFor.equals("Category"))
					{
						feedbackList = AUH.setAnalyticalReportValues(totalData,onTimedata,offTimedata,snooze,missed,ignore,dataFor);
					}
				    userdata= new LinkedHashMap<String, Object>();
				    int counter=0,ontime=0,offtime=0,missedT=0,snoozeT=0,perontime=0,perofftime=0,permissed=0,ignoreT=0;
				    for (AssetDashboardBean fbp : feedbackList)
				    {
					   counter=counter+Integer.valueOf(fbp.getCounter());
					   ontime=ontime+Integer.valueOf(fbp.getOnTime());
					   offtime=offtime+Integer.valueOf(fbp.getOffTime());
					   missedT=missedT+Integer.valueOf(fbp.getMissed());
					   snoozeT=snoozeT+Integer.valueOf(fbp.getSnooze());
					   perontime=perontime+Integer.valueOf(fbp.getPerOnTime());
					   perofftime=perofftime+Integer.valueOf(fbp.getPerOffTime());
					   permissed=permissed+Integer.valueOf(fbp.getPerMissed());
					   ignoreT=ignoreT+Integer.valueOf(fbp.getIgnore());
				    }
				    if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
				    {
					   userdata.put("feedback_by_subdept", "Total : ");
				    }
				    else if(dataFor!=null && dataFor.equalsIgnoreCase("Category"))
				    {
					   userdata.put("feedback_subcatg", "Total : ");
				    }
				    userdata.put("counter", counter);
				    userdata.put("onTime", ontime);
				    userdata.put("offTime", offtime);
				    userdata.put("missed", missedT);
				    userdata.put("snooze", snoozeT);
				    userdata.put("ignore", ignoreT);
				    userdata.put("perOnTime", perontime);
				    userdata.put("perOffTime", perofftime);
				    userdata.put("perMissed", permissed);
				    userdata.put("graph", counter+","+ontime+","+offtime+","+missedT+","+snoozeT+",");
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
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public Map<String, String> getServiceDeptList() {
		return serviceDeptList;
	}
	public void setServiceDeptList(Map<String, String> serviceDeptList) {
		this.serviceDeptList = serviceDeptList;
	}
	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}
	public String getDataFor() {
		return dataFor;
	}
	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public boolean isLoadonce() {
		return loadonce;
	}
	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getSubdeptId() {
		return subdeptId;
	}
	public void setSubdeptId(String subdeptId) {
		this.subdeptId = subdeptId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getByDeptId() {
		return byDeptId;
	}
	public void setByDeptId(String byDeptId) {
		this.byDeptId = byDeptId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public List<AssetDashboardBean> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<AssetDashboardBean> feedbackList) {
		this.feedbackList = feedbackList;
	}
	public Map<String, Integer> getGraphMap() {
		return graphMap;
	}
	public void setGraphMap(Map<String, Integer> graphMap) {
		this.graphMap = graphMap;
	}
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public String getGraphData() {
		return graphData;
	}
	public void setGraphData(String graphData) {
		this.graphData = graphData;
	}
	
}
