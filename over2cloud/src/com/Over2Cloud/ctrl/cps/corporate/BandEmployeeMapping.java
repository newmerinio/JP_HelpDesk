package com.Over2Cloud.ctrl.cps.corporate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BandEmployeeMapping extends ActionSupport implements
		ServletRequestAware {

	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	CommonOperInterface					coi							= new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	
	static final Log log = LogFactory.getLog(BandEmployeeMapping.class);
	// Get the requested page. By default grid sets this to 1.
	private Integer rows = 0;
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
	private String employee;
	
	List<ConfigurationUtilBean> packageFields;
	private Map<String,String>deptList;
	
	
	
	public String viewBandEmployeeMappingHeader() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
void createParameterList(){
	
	System.out.println(session);

	deptList=new LinkedHashMap<String, String>();
	ComplianceUniversalAction CUA= new ComplianceUniversalAction();
	String loggedDetail[]= CUA.getEmpDetailsByUserName("WFPM", userName);
	StringBuilder qryString = new StringBuilder();
	qryString.append("SELECT dept.id,dept.contact_subtype_name FROM primary_contact AS emp");
	qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
	qryString.append(" INNER JOIN contact_sub_type AS dept ON cc.from_contact_sub_type_id=dept.id");
	qryString.append(" WHERE for_contact_sub_type_id=" + loggedDetail[4] + " AND module_name='WFPM' AND work_status!=1 AND emp.status='0' order by dept.contact_subtype_name asc");

	if (qryString.toString()!=null)
	{
		List<?> dataList = new createTable().executeAllSelectQuery(qryString.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null && object[1] != null)
				{
					deptList.put(object[0].toString(), object[1].toString());
				}
			}
		}
	}
	

	
}	
	
	public String beforeAddBandEmployeeMapping(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				createParameterList();
				setAddPageDataFields();
				returnResult = SUCCESS;
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	public void setAddPageDataFields() {
		try
		{

		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_map_band_employee_configuration", accountID, connectionSpace, "", 0, "table_name", "map_band_employee_configuration");
		packageFields = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName"))
			{
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
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
					packageFields.add(obj);
					
			}

			
		}
		}catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
			}
	}

	public String addBandEmployeeMappingDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> statusColName = Configuration
						.getConfigurationData(
								"mapped_map_band_employee_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"map_band_employee_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							Tablecolumesaaa.add(ob1);
					}
					
						status=	cbt.createTable22("map_band_employee", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] rel_subtype=employee.split(",");
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
							System.out.println((!(Parmname.equalsIgnoreCase("employee"))));
						if (paramValue != null    
								&& Parmname != null 
								&& !(Parmname.equalsIgnoreCase("employee")) 
								&& !(Parmname.contains("employee")) )
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
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

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
				
				//	System.out.println(Arrays.asList(rel_subtype));
					
					int count=0;
					
					for(int i=0;i<rel_subtype.length;i++){
						
						if(rel_subtype[i]!=null && !rel_subtype[i].toString().trim().equalsIgnoreCase("")){
					InsertDataTable	idt1 = new InsertDataTable();
					idt1.setColName("employee");
					idt1.setDataName(checkNull(rel_subtype[i]));
						insertData.add(idt1);
							
							status = cbt.insertIntoTable("map_band_employee",insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
						}
					}
					insertData.clear();
				
					if (status)
					{
						addActionMessage("Employee Mapped Successfully: "+count);
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
		
	}
	
	
	String checkNull(Object obj){
		String returnResult="NA";
		if(obj!=null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1") ){
			returnResult=obj.toString();
		}
		return returnResult;
	}
	
	
	public String viewBandEmployeeMappingDetails(){
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewProps();
				returnResult=SUCCESS;		
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				 e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	 void setMasterViewProps(){

		String accountID=(String)session.get("accountid");
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_map_band_employee_configuration",accountID,connectionSpace,"",0,"table_name","map_band_employee_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
							for(GridDataPropertyView gdp:statusColName)
							{
									gpv = new GridDataPropertyView();
									gpv.setColomnName(gdp.getColomnName());
									gpv.setHeadingName(gdp.getHeadingName());
									gpv.setEditable(gdp.getEditable());
									gpv.setSearch(gdp.getSearch());
									gpv.setHideOrShow(gdp.getHideOrShow());
									gpv.setFormatter(gdp.getFormatter());
									gpv.setWidth(gdp.getWidth());
									masterViewProp.add(gpv);
							}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	 
	 public String viewBandEmployeeMappingData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from map_band_employee");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
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
				if (to > getRecords()) to = getRecords();
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				
				queryEnd.append(" from map_band_employee as mbe " +
						" inner JOIN contact_sub_type AS dept ON dept.id=mbe.emp_department " +
						 " inner JOIN manage_contact AS cc ON cc.id=mbe.employee " +
						 "inner join primary_contact AS emp on cc.emp_id=emp.id ");

				List fieldNames = Configuration.getColomnList("mapped_map_band_employee_configuration", accountID, connectionSpace, "map_band_employee_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
								
										if(obdata.toString().equalsIgnoreCase("emp_department")){
											queryTemp.append(" dept.contact_subtype_name , ");
										}else 
											if(obdata.toString().equalsIgnoreCase("employee")){
											queryTemp.append(" emp.emp_name , ");
										}
										else{
												queryTemp.append("mbe." + obdata.toString() + ",");	
										}
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" mbe.status='Active' ");
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
				// add search query based on the search operation
				query.append(" and ");

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
				
				query.append(" order by mbe.emp_band ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				//System.out.println("query.toString()>>"+query.toString()); 
				if (data != null)
				{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
				Object[] obdata = (Object[]) it.next();
				Map<String, Object> one = new HashMap<String, Object>();
					for (int k = 0; k < fieldNames.size(); k++)
					{
						if (obdata[k] != null)
						{
							if (k == 0)
							{
								one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
							}
							else
							{
								if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
								{
									obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
								}
								one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
					}
					Listhb.add(one);
				}
					setViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					return "success";
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
	 }
	 
	 public String editBandEmployeeMappingDataGrid()
	 {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					if (getOper().equalsIgnoreCase("edit"))
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
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
								}
								else if (parmName.equalsIgnoreCase("date"))
								{
									wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
								}
								else if (parmName.equalsIgnoreCase("time"))
								{
									wherClause.put(parmName, DateUtil.getCurrentTimeHourMin());
								}
								else
								{
									wherClause.put(parmName, paramValue);
								}
							}
						}
						condtnBlock.put("id", getId());
						boolean status = cbt.updateTableColomn("map_band_employee", wherClause, condtnBlock, connectionSpace);
						if (status)
						{
							returnResult = SUCCESS;
						}
					}
					else if (getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						String tempIds[] = getId().split(",");
						for (String H : tempIds)
						{
							Map<String, Object> wherClause = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							wherClause.put("status", "Inactive");
							condtnBlock.put("id", id);
							cbt.updateTableColomn("map_band_employee", wherClause, condtnBlock, connectionSpace);
							returnResult = SUCCESS;
						}
					}
				}
				catch (Exception e)
				{
					log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method corporate master of class " + getClass(), e);
					e.printStackTrace();
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		
		 
	 }

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getViewList() {
		return viewList;
	}

	public void setViewList(List<Object> viewList) {
		this.viewList = viewList;
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

	public List<ConfigurationUtilBean> getPackageFields() {
		return packageFields;
	}

	public void setPackageFields(List<ConfigurationUtilBean> packageFields) {
		this.packageFields = packageFields;
	}

	
	public Map<String, String> getDeptList() {
		return deptList;
	}


	public void setDeptList(Map<String, String> deptList) {
		this.deptList = deptList;
	}


	public String getEmployee() {
		return employee;
	}


	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
}