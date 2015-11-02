package com.Over2Cloud.ctrl.wfpm.crm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.wfpm.client.ClientCtrlAction;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

@SuppressWarnings("serial")
public class UserActionHistory extends GridPropertyBean {
	
	String cId = new CommonHelper().getEmpDetailsByUserName(
			CommonHelper.moduleName, userName, connectionSpace)[0];

	static final Log log = LogFactory.getLog(ClientCtrlAction.class);
	private String moduleName = "";
	private List<Object> userHistoryViewList;
	private List<GridDataPropertyView> userHistoryViewProp = new ArrayList<GridDataPropertyView>();
	private boolean loadonce;
	private String fromDate;
	private String toDate;
	private String userID;
	private Map<String, String> fieldMap;
	private Map<String, String> moduleMap;
	private Map<String, String> subModuleMap;
	private Map<String, String> userMap;
	
	public String beforeUserActionHistoryView()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				fromDate=DateUtil.getNextDateAfter(-7);
				UserHistoryHelper<String> UH=new UserHistoryHelper<String>();
				moduleMap= new LinkedHashMap<String, String>();
				subModuleMap= new LinkedHashMap<String, String>();
				userMap = new LinkedHashMap<String, String>();
				moduleMap=UH.fetchModuleInHistory(connectionSpace);
				subModuleMap=UH.fetchSubModuleInHistory(connectionSpace);
				userMap=UH.fetchUserInHistory(connectionSpace);
				
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
	
	public String showUserHistoryData()
	{
		try {
			if (!ValidateSession.checkSession())
				return LOGIN;
			setUserHistoryViewProp();
			
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method: showUserHistoryData of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setUserHistoryViewProp()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		userHistoryViewProp.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData("mapped_user_action_history_configuration",
						accountID, connectionSpace, "", 0, "table_name",
						"user_action_history_configuration");
		
			for (GridDataPropertyView gdp : returnResult) {
				
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
					gpv.setFormatter(gdp.getFormatter());
					userHistoryViewProp.add(gpv);
			}
			
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String viewUserHistoryGrid()
	{
		try {
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from user_action_history ");
				
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if (dataCount.size()>=0) {
				BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();) {
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;
				}
				setRecords(count.intValue());
				setRows(count.intValue());
				int to = (getRows() * getPage());
				if (to > getRecords())
					to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from user_action_history as uah ");

				List fieldNames = Configuration.getColomnList(
						"mapped_user_action_history_configuration", accountID,
						connectionSpace, "user_action_history_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				// List of column names to show in grid.
				List<String> listDataToShow = new UserHistoryHelper().getUserHistoryDataToShow();
				for (Iterator it = fieldNames.iterator(); it.hasNext();) {
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null) {
						if (!listDataToShow.contains(obdata.toString())) {
							it.remove();
							continue;
						}
								if (obdata.toString().equalsIgnoreCase("userName")) {
									queryTemp.append(" empb.empName as userName, ");
									queryEnd.append(" left join employee_basic as empb on uah.userName = empb.id ");
								}
							else{
								
									queryTemp.append("uah." + obdata.toString() + ",");
							}
					}
				}
				query.append(queryTemp.toString().substring(0,queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				/*query.append(" where ");
				query.append(" uah.userName IN(");
				query.append(cIdAll);
				query.append(") ");*/
				if (fromDate!=null && toDate!=null)
				{
					if (fromDate.split("-")[0].length()<4)
					{
						fromDate=DateUtil.convertDateToUSFormat(fromDate);
						toDate=DateUtil.convertDateToUSFormat(toDate);
					}
					query.append(" WHERE action_date  BETWEEN '"+fromDate+"' AND '"+toDate+"'");
				}
				if (getSearchField() != null && getSearchString() != null&& !getSearchField().equalsIgnoreCase("")&& !getSearchString().equalsIgnoreCase("")&& !getSearchField().equalsIgnoreCase("undefined")) {
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchOper().equalsIgnoreCase("eq")) {
						query.append(" " + getSearchField() + " = '"
								+ getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn")) {
						query.append(" " + getSearchField() + " like '%"
								+ getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw")) {
						query.append(" " + getSearchField() + " like '"
								+ getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne")) {
						query.append(" " + getSearchField() + " <> '"
								+ getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew")) {
						query.append(" " + getSearchField() + " like '%"
								+ getSearchString() + "'");
					}
				}
				if (getSord() != null && !getSord().equalsIgnoreCase("")) {
					if (getSord().equals("asc") && getSidx() != null
							&& !getSidx().equals("")) {
						query.append(" order by " + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null
							&& !getSidx().equals("")) {
						query.append(" order by " + getSidx() + " DESC");
					}
				}
				cbt.checkTableColmnAndAltertable(fieldNames,
						"user_action_history", connectionSpace);

				 System.out.println("query:::" + query);
				List data = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (data.size()>=0 ) {
					for (Iterator it = data.iterator(); it.hasNext();) {
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						
						for (int k = 0; k < fieldNames.size(); k++) {
							if (obdata[k] != null) {
								if (k == 0) {
									one.put(fieldNames.get(k).toString(),
											(Integer) obdata[k]);
								} else {
									if(fieldNames.get(k).toString().equalsIgnoreCase("action_date"))
									{
										one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+" "+obdata[k+1].toString());
									}
									else if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                	{
                                		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()) );
                                	}
									else{
										one.put(fieldNames.get(k).toString(),obdata[k].toString());
									}
									
								}
							}
						}
						Listhb.add(one);
					}
					setUserHistoryViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords()
							/ (double) getRows()));
				}

			}
		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: showUserHistoryData of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String userActionHistoryViewData()
	{
		try 
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			List data=new UserHistoryHelper<String>().fetchDataFields(connectionSpace,userID);
			fieldMap=new LinkedHashMap<String, String>();
			if (data!=null && data.size()>0)
			{
				Object[] object=(Object[])data.get(0);
				if (object!=null)
				{
					String field[]=object[0].toString().split(", ");
					String datas[]=object[1].toString().split(", ");
					for (int i = 0; i < field.length; i++)
					{
						if (datas[i].matches("\\d{4}-[01]\\d-[0-3]\\d"))
						{
							fieldMap.put(field[i],DateUtil.convertDateToIndianFormat(datas[i]));
						}
						else
						{
							fieldMap.put(field[i], datas[i]);
						}
						
					}
				}
			}
		} 
		catch (Exception e) 
		{
			log.error("Date : "+ DateUtil.getCurrentDateIndianFormat()+ " Time: "+ DateUtil.getCurrentTime()
									+ " "+ "Problem in Over2Cloud  method: userActionHistoryViewData of class "+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<Object> getUserHistoryViewList() {
		return userHistoryViewList;
	}

	public void setUserHistoryViewList(List<Object> userHistoryViewList) {
		this.userHistoryViewList = userHistoryViewList;
	}

	public List<GridDataPropertyView> getUserHistoryViewProp() {
		return userHistoryViewProp;
	}

	public void setUserHistoryViewProp(
			List<GridDataPropertyView> userHistoryViewProp) {
		this.userHistoryViewProp = userHistoryViewProp;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public Map<String, String> getModuleMap()
	{
		return moduleMap;
	}

	public void setModuleMap(Map<String, String> moduleMap)
	{
		this.moduleMap = moduleMap;
	}

	public Map<String, String> getSubModuleMap()
	{
		return subModuleMap;
	}

	public void setSubModuleMap(Map<String, String> subModuleMap)
	{
		this.subModuleMap = subModuleMap;
	}

	public Map<String, String> getUserMap()
	{
		return userMap;
	}

	public void setUserMap(Map<String, String> userMap)
	{
		this.userMap = userMap;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public Map<String, String> getFieldMap()
	{
		return fieldMap;
	}

	public void setFieldMap(Map<String, String> fieldMap)
	{
		this.fieldMap = fieldMap;
	}

}
