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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.criteria.expression.function.TrimFunction;

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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

// Ankit 2015
public class DocScheduleMaster extends ActionSupport implements
		ServletRequestAware {

	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	CommonOperInterface					coi							= new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	
	static final Log log = LogFactory.getLog(CorporateMasterAction.class);
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
	private JSONArray jsonArray;
	
	
	
	private Map<String,String> serviceList;
	private Map<String,String> specList;
	private Map<String, String> locList;
	private Map<String, String> docList;
	
	
	List<ConfigurationUtilBean> packageFields;
	
	
	
	public String viewDocScheduleHeader() {
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
	
	public String beforeAddDocSchedule(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAddPageDataFields();
				createServiceList();
				createSpecialityList();
				createLocationMap();
				createDocMap();
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
	Map<String,String> createLocationMap()
	{
		locList=new LinkedHashMap<String,String>();
		List dataList=coi.executeAllSelectQuery("select distinct id, location_name from cps_medanta_location;", connectionSpace);
		if(dataList!=null && dataList.size() > 0){
			for(Iterator itr=dataList.iterator();itr.hasNext();){
				Object[] obj =(Object []) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					locList.put(obj[0].toString(),obj[1].toString());
			}
		}
		return locList;
	}
	
	void createDocMap()
	{
		docList=new LinkedHashMap<String,String>();
		List dataList=coi.executeAllSelectQuery("SELECT emp.id,emp.empName FROM 4_clouddb.employee_basic as emp inner join department as dept on dept.id=emp.deptname where dept.id=(select id from department where deptName like '%Doctor%')", connectionSpace);
		if(dataList!=null && dataList.size() > 0){
			for(Iterator itr=dataList.iterator();itr.hasNext();){
				Object[] obj =(Object []) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					docList.put(obj[0].toString(),obj[1].toString());
			}
		}
	}
	
	
	Map<String,String> createServiceList(){
		serviceList=new LinkedHashMap<String,String>();
		List dataList=coi.executeAllSelectQuery("select distinct id, service_name from cps_service order by service_name ", connectionSpace);
		if(dataList!=null && dataList.size() > 0){
			for(Iterator itr=dataList.iterator();itr.hasNext();){
				Object[] obj =(Object []) itr.next();
				if(obj[0]!=null && obj[1]!=null)
				serviceList.put(obj[0].toString(),obj[1].toString());
			}
		}
		return serviceList;
	}
	
	
	Map<String,String> createSpecialityList(){
		specList=new LinkedHashMap<String,String>();
		List dataList=coi.executeAllSelectQuery("select distinct id, spec_name from cps_speciality;", connectionSpace);
		if(dataList!=null && dataList.size() > 0){
			for(Iterator itr=dataList.iterator();itr.hasNext();){
				Object[] obj =(Object []) itr.next();
				if(obj[0]!=null && obj[1]!=null)
					specList.put(obj[0].toString(),obj[1].toString());
			}
		}
		return serviceList;
	}
	
	public void setAddPageDataFields() {
		try
		{

		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_cps_doc_schedule_configuration", accountID, connectionSpace, "", 0, "table_name", "cps_doc_schedule_configuration");
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

	public String addDocScheduleDetails()
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
								"mapped_cps_doc_schedule_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"cps_doc_schedule_configuration");
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
					
						status=	cbt.createTable22("cps_doc_schedule", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] ftime=new String[0];
					Object [] ttime=new String[0];
					Object [] days=new String[0];
					Object [] brief=new String[0];
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null && !Parmname.contains("__multiselect")
								)
						{
							if(Parmname.equalsIgnoreCase("sch_ftime")){
								ftime=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("sch_ttime")){
								ttime=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("sch_day")){
								days=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("sch_brief")){
								brief=request.getParameterValues(Parmname);
							}
							else{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
							}
						}
					}
					
					String selectDays="";
					if(days!=null && days.length>0){
						for(Object str:days){
							selectDays+=checkNull(str)+",";
						}
						selectDays=selectDays.substring(0, selectDays.lastIndexOf(","));
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
					
					//System.out.println(Arrays.asList(ftime));
					//System.out.println(Arrays.asList(ttime));
					//System.out.println(Arrays.asList(days));
					//System.out.println(Arrays.asList(brief));
					
					int count=0;
					
					for(int i=0;i<ftime.length;i++){
						
						if(ftime[i]!=null && !ftime[i].toString().trim().equalsIgnoreCase("")){
						InsertDataTable	idt1 = new InsertDataTable();
							idt1.setColName("sch_ftime");
							idt1.setDataName(checkNull(ftime[i]));
							insertData.add(idt1);
					
						InsertDataTable	idt2 = new InsertDataTable();
						idt2.setColName("sch_ttime");
						idt2.setDataName(checkNull(ttime[i]));
							insertData.add(idt2);
					
						InsertDataTable	idt3 = new InsertDataTable();
							idt3.setColName("sch_day");
							idt3.setDataName(selectDays);
								insertData.add(idt3);			
						
						InsertDataTable	idt6 = new InsertDataTable();
							idt6.setColName("sch_brief");
							idt6.setDataName(checkNull(brief[i]));
							insertData.add(idt6);	
							
							status = cbt.insertIntoTable("cps_doc_schedule",insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
							insertData.remove(idt2);
							insertData.remove(idt3);
							insertData.remove(idt6);
						}
					}
					insertData.clear();
					if (status)
					{
						addActionMessage("Doctor Schedule Added successfully: "+count);
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
	
	  public String fetchSerivceList(){
	    	String returnresult=ERROR;
	    	boolean sessionFlag = ValidateSession.checkSession();
	    	if (sessionFlag)
	    	{
	    		try
	    		{
	    		String query="select id,service_name from cps_service where serv_loc = '"+searchField+"'  ORDER BY service_name ";
	    		List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
	    		 if (dataList != null && dataList.size() > 0)
	    			{
	    			 jsonArray = new JSONArray();
	    				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
	    				{
	    					Object[] object = (Object[]) iterator.next();
	    					if (object[0] != null && object[1] != null)
	    					{
	    						JSONObject obj= new JSONObject();
	    						obj.put("id",object[0].toString() );
	    						obj.put("name", object[1].toString());
	    						jsonArray.add(obj);
	    					}
	    				}
	    			}
	    			returnresult=SUCCESS;
	    		}catch(Exception e){
	    			e.printStackTrace();
	    			returnresult = ERROR;
	    			}
	    	}else {
	    		return LOGIN; 
	    	}
	    	return returnresult;
	    }
	
	public String viewDocScheduleDetails(){

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
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_cps_doc_schedule_configuration",accountID,connectionSpace,"",0,"table_name","cps_doc_schedule_configuration");
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
	 
	 public String viewDocScheduleData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from cps_doc_schedule");
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
				queryEnd.append(" from cps_doc_schedule as cp left join cps_service as pack on pack.id=cp.sch_service " +
						"left join cps_speciality as cc on cc.id=cp.sch_spec " +
						"left join cps_medanta_location as mloc on mloc.id=cp.sch_loc " +
						" left join employee_basic as emp on emp.id=cp.sch_doc_name ");

				List fieldNames = Configuration.getColomnList("mapped_cps_doc_schedule_configuration", accountID, connectionSpace, "cps_doc_schedule_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
									if(obdata.toString().equalsIgnoreCase("sch_service")){
										queryTemp.append("pack.service_name , ");
									}else
									if(obdata.toString().equalsIgnoreCase("sch_spec")){
										queryTemp.append("cc.spec_name , ");
									}else
										if(obdata.toString().equalsIgnoreCase("sch_loc")){
											queryTemp.append("mloc.location_name , ");
										}
										else
											if(obdata.toString().equalsIgnoreCase("sch_doc_name")){
												queryTemp.append("emp.empName , ");
											}
									else{
										queryTemp.append("cp." + obdata.toString() + ",");
									}
								}
							
					}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" cp.status='Active' ");
				
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
				
				query.append(" order by cp.sch_doc_name ");
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
								one.put(fieldNames.get(k).toString(), checkNull(obdata[k]));
							}
						}else{
							one.put(fieldNames.get(k).toString(), "NA");
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
	 
	 
	 
	 public String editDocScheduleDataGrid()
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
						boolean status = cbt.updateTableColomn("cps_doc_schedule", wherClause, condtnBlock, connectionSpace);
						if (status)
						{
							returnResult = SUCCESS;
						}
					}
					else if (getOper().equalsIgnoreCase("del"))
					{
						System.out.println(getOper());
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						String tempIds[] = getId().split(",");
						for (String H : tempIds)
						{
							Map<String, Object> wherClause = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							wherClause.put("status", "Inactive");
							condtnBlock.put("id", id);
							System.out.println(wherClause);
							System.out.println(condtnBlock);
						boolean status=	cbt.updateTableColomn("cps_doc_schedule", wherClause, condtnBlock, connectionSpace);
						System.out.println(status);
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

	
	public Map<String, String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(Map<String, String> serviceList) {
		this.serviceList = serviceList;
	}

	public Map<String, String> getSpecList() {
		return specList;
	}

	public void setSpecList(Map<String, String> specList) {
		this.specList = specList;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public Map<String, String> getLocList() {
		return locList;
	}

	public void setLocList(Map<String, String> locList) {
		this.locList = locList;
	}

	public Map<String, String> getDocList() {
		return docList;
	}

	public void setDocList(Map<String, String> docList) {
		this.docList = docList;
	}
	
}
