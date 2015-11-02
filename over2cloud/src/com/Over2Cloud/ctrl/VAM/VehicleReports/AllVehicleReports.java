package com.Over2Cloud.ctrl.VAM.VehicleReports;

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

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.ctrl.VAM.master.VAMMasterActionControl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AllVehicleReports extends ActionSupport implements ServletRequestAware{

	private HttpServletRequest request;
	static final Log log = LogFactory.getLog(VAMMasterActionControl.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String mainHeaderName;
	private List<GridDataPropertyView> viewVehicleDetail = new ArrayList<GridDataPropertyView>();
	private List<Object> vehicledatalist;
	Map<Integer, String> departmentlist = new HashMap<Integer, String>();
	Map<Integer, String> locationlist = new HashMap<Integer, String>();
	Map<Integer, String> purposelist = new HashMap<Integer, String>(); 
	private String from_date;
	private String to_date;
	private String deptName;
	private String purpose;
	private String location;
	private String veh_status;
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
	// Grid colomn view
	private String oper;
	private String id;
	
	public String beforeVehicleMISView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allVhDepartmentList(connectionSpace);
			purposelist = new HashMap<Integer, String>();
			purposelist = CommonMethod.allVehiclePurpose(connectionSpace);
			setMainHeaderName("Vehicle MIS");
			setVehicleMisViewProp("mapped_vehicle_entry_configuration",
			    "vehicle_entry_configuration");

		} catch (Exception e)
		{
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <beforeVehicleMISView()> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	
	}
	public void setVehicleMisViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVehicleDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewVehicleDetail.add(gpv);
		}
	}
	public String vehicleMisView()
	{
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from vehicle_entry_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			    connectionSpace);
			if (dataCount != null)
			{
				/*BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;

				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();*/

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from vehicle_entry_details as ved ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_vehicle_entry_configuration", accountID, connectionSpace,
				    "vehicle_entry_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{
						String object = iterator.next().toString();
						if (object != null)
						{
							if (object.equalsIgnoreCase("deptName")) {
								queryTemp.append(",dpt.jlocation_name");
								queryEnd
										.append(" left join vh_location_jbm as dpt on ved.deptName = dpt.id ");
							} else if (object.equalsIgnoreCase(
									"location")) {
								queryTemp.append(",loc.name");
								queryEnd
										.append(" left join location as loc on ved.location = loc.id ");
							} else if (object.equalsIgnoreCase(
									"purpose")) {
								queryTemp.append(",pd.purpose");
								queryEnd
										.append(" left join purpose_admin as pd on ved.purpose = pd.id ");
							} else {
								queryTemp.append("," + "ved." + object.toString());
							}
						}
						i++;
					}
					query.append(queryTemp.toString().substring(1));
					query.append(" " + queryEnd.toString());
					if (getDeptName() != null && !getDeptName().equals("-1"))
					{
						query.append(" " + "where ved.deptName=" + getDeptName());
					}
					else if(getDeptName() != null && getDeptName().equals("-1"))
					{
					} 
					if (getLocation() != null && !getLocation().equals("-1"))
					{
						query.append(" " + "where ved.location=" + getLocation());
					}
					else if(getLocation() != null && getLocation().equals("-1"))
					{
					} 
					if (getPurpose() != null && !getPurpose().equals("-1")) {
						query.append(" " + "where ved.purpose=" + getPurpose());
					} else if (getLocation() != null
							&& getLocation().equals("-1")) {
					}
					System.out.println("veh_status><><"+getVeh_status());
					if (getVeh_status() != null && !getVeh_status().equals("-1") && !getVeh_status().equals("With Material")) {
						query.append(" " + "where ved.invoce_no = '"+"NA'");
					} else if (getLocation() != null
							&& getLocation().equals("-1")) {
					}
					if (getVeh_status() != null && !getVeh_status().equals("-1") && !getVeh_status().equals("Without Material")) {
						query.append(" " + "where ved.invoce_no !='"+"NA'");
					} else if (getLocation() != null
							&& getLocation().equals("-1")) {
					}
					if (getFrom_date() != null && !getFrom_date().equals("")
					    && getTo_date() != null && !getTo_date().equals(""))
					{
						setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
						setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
						query.append(" where ved.entry_date >= '" + getFrom_date()
						    + "' and ved.entry_date <= '" + getTo_date() + "'");
					}
					
					
					System.out.println("Querry>getVehicleDetails>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
					    && !getSearchField().equalsIgnoreCase("")
					    && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "'");
						}

					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					//query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "vehicle_entry_details",
					    connectionSpace);

					System.out.println("Querry getVehiclesmis2>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
					    connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty())
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{

									if (fieldNames.get(k).toString()
									    .equalsIgnoreCase("orgnztnlevel"))
									{
										// need to work here for viewing the organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString().equalsIgnoreCase("2"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("3"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("4"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("5"))
										{
										}
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									} else
									{
										if (k == 0)
										{
											one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										} else
										{
											if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("date"))
											{
												one.put(fieldNames.get(k).toString(), DateUtil
												    .convertDateToIndianFormat(obdata[k].toString()));
											} else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),
												    (obdata[k].toString().substring(0, 5)));
											}
											else if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("entry_date"))
												{
													one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
											else
											{
												one.put(fieldNames.get(k).toString(),
												    DateUtil.makeTitle(obdata[k].toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setVehicledatalist(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <vehicleMisView> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	
	public String editVehicleMISGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
				    .list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
					    && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("vehicle_entry_details", wherClause,
				    condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del"))
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
				cbt.deleteAllRecordForId("vehicle_entry_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method editVehicleMISGrid of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public List<GridDataPropertyView> getViewVehicleDetail() {
		return viewVehicleDetail;
	}
	public void setViewVehicleDetail(List<GridDataPropertyView> viewVehicleDetail) {
		this.viewVehicleDetail = viewVehicleDetail;
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
	public List<Object> getVehicledatalist() {
		return vehicledatalist;
	}
	public void setVehicledatalist(List<Object> vehicledatalist) {
		this.vehicledatalist = vehicledatalist;
	}
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public Map<Integer, String> getDepartmentlist() {
		return departmentlist;
	}
	public void setDepartmentlist(Map<Integer, String> departmentlist) {
		this.departmentlist = departmentlist;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Map<Integer, String> getLocationlist() {
		return locationlist;
	}
	public void setLocationlist(Map<Integer, String> locationlist) {
		this.locationlist = locationlist;
	}
	public Map<Integer, String> getPurposelist() {
		return purposelist;
	}
	public void setPurposelist(Map<Integer, String> purposelist) {
		this.purposelist = purposelist;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getVeh_status() {
		return veh_status;
	}
	public void setVeh_status(String veh_status) {
		this.veh_status = veh_status;
	}
	
}
