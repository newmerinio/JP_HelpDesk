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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EventConfigureMaster extends ActionSupport implements
		ServletRequestAware {

	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	CommonOperInterface					coi							= new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewProp2 =new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	
	static final Log log = LogFactory.getLog(EventConfigureMaster.class);
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
	private Map<String,String> activityMap;
	private Map<String,String> eventMap;
	
	List<ConfigurationUtilBean> packageFields;
	List<ConfigurationUtilBean> packageFields2;
	
	public String viewEventConfigureHeader() {
		System.out.println("kkkkkkkkkkkkkkkkkkkkkk");
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
	
	//get Event type  
	void createEventpMap(){
		activityMap= new LinkedHashMap<String, String>();
		
		String query=" select id, act_name from activity_type where act_type='Event' order by act_name ";
		if (query!=null)
		{
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						activityMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		
		
	}
	
	public String beforeAddEventConfigureMaster(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAddPageDataFields();
				createEventpMap();
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

		// Activity Type	
		List<GridDataPropertyView> commonList = Configuration.getConfigurationData("mapped_event_cost_parameter_configuration", accountID, connectionSpace, "", 0, "table_name", "event_cost_parameter_configuration");
		packageFields = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : commonList)
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
		
		// Activity Type Reasons
		commonList.clear();
		commonList = Configuration.getConfigurationData("mapped_event_map_cost_parameter_configuration", accountID, connectionSpace, "", 0, "table_name", "event_map_cost_parameter_configuration");
		packageFields2 = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : commonList)
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
					packageFields2.add(obj);
					
			}
		}
		
		
		}catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
			}
	}

	public String addEventCostParameters()
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
								"mapped_event_cost_parameter_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"event_cost_parameter_configuration");
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
					
						status=	cbt.createTable22("event_cost_parameter", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					
					Object [] event_cost=new String[0];
					Object [] event_base=new String[0];
					Object [] event_unit=new String[0];
					Object [] event_max_value=new String[0];
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
						{
							if(Parmname.equalsIgnoreCase("event_cost_name")){
								event_cost=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("event_base")){
								event_base=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("event_unit")){
								event_unit=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("event_max_value")){
								event_max_value=request.getParameterValues(Parmname);
							}
							else{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
							}
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
				
					System.out.println(Arrays.asList(event_cost));
					
					System.out.println(Arrays.asList(event_base));
					
					
					
					int count=0;
					
					for(int i=0;i<event_cost.length;i++){
						
						if(event_cost[i]!=null && !event_cost[i].toString().trim().equalsIgnoreCase("")){
					InsertDataTable	idt1 = new InsertDataTable();
					idt1.setColName("event_cost_name");
					idt1.setDataName(checkNull(event_cost[i]));
						insertData.add(idt1);
							
						InsertDataTable	idt2 = new InsertDataTable();
							idt2.setColName("event_base");
							idt2.setDataName(checkNull(event_base[i]));
							insertData.add(idt2);	
							
							InsertDataTable	idt3 = new InsertDataTable();
							idt3.setColName("event_unit");
							idt3.setDataName(checkNull(event_unit[i]));
							insertData.add(idt3);
								InsertDataTable	idt4 = new InsertDataTable();
							idt4.setColName("event_max_value");
							idt4.setDataName(checkNull(event_max_value[i]));
							insertData.add(idt4);
							
							status = cbt.insertIntoTable("event_cost_parameter",insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
							insertData.remove(idt2);
							insertData.remove(idt3);
							insertData.remove(idt4);
						}
					}
					
					insertData.clear();
				
					if (status)
					{
						addActionMessage("Data Added successfully: "+count);
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
	
	
	public String addEventRoi()
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
								"mapped_event_map_cost_parameter_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"event_map_cost_parameter_configuration");
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
					
						status=	cbt.createTable22("event_map_cost_parameter", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] activity=new String[0];
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
						{
							if(Parmname.equalsIgnoreCase("roi_parameter")){
								activity=request.getParameterValues(Parmname);
							}
							else{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
							}
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
				
					System.out.println(Arrays.asList(activity));
					
					int count=0;
					String codeExist="";
					for(int i=0;i<activity.length;i++){
						
						if(activity[i]!=null && !activity[i].toString().trim().equalsIgnoreCase("")){
										InsertDataTable	idt1 = new InsertDataTable();
										idt1.setColName("roi_parameter");
										idt1.setDataName(checkNull(activity[i]));
											insertData.add(idt1);
											
		status = cbt.insertIntoTable("event_map_cost_parameter",insertData, connectionSpace);
		count++;
		insertData.remove(idt1);
						}
					}
						insertData.clear();
					if (status)
					{
						addActionMessage("Data Added successfully: "+count
								+codeExist );
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
	
	
	public String viewEventCostDetails(){
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
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_event_cost_parameter_configuration",accountID,connectionSpace,"",0,"table_name","event_cost_parameter_configuration");
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
				
				statusColName.clear();
				statusColName=Configuration.getConfigurationData("mapped_event_map_cost_parameter_configuration",accountID,connectionSpace,"",0,"table_name","event_map_cost_parameter_configuration");
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
										masterViewProp2.add(gpv);
								}
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	 
	 public String viewEventCostData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from event_cost_parameter");
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
				queryEnd.append(" from event_cost_parameter as cp " +
						" left join activity_type as at on at.id=cp.event_type" +
						" ");

				List fieldNames = Configuration.getColomnList("mapped_event_cost_parameter_configuration", accountID, connectionSpace, "event_cost_parameter_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										if(obdata.toString().equalsIgnoreCase("event_type")){
											queryTemp.append(" at.act_name ,");	
										}else{
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
				
				query.append(" order by at.act_name ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				System.out.println("query.toString()>>"+query.toString()); 
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
	 
	 public String editEventTypeMasterDataGrid()
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
						boolean status = cbt.updateTableColomn("event_cost_parameter", wherClause, condtnBlock, connectionSpace);
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
							cbt.updateTableColomn("event_cost_parameter", wherClause, condtnBlock, connectionSpace);
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
	 
	 public String viewModifyEventRoiCost()
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
						boolean status = cbt.updateTableColomn("event_map_cost_parameter", wherClause, condtnBlock, connectionSpace);
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
							cbt.updateTableColomn("event_map_cost_parameter", wherClause, condtnBlock, connectionSpace);
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
	 
	 
	 
		
	 
	 
	 
	 public String viewEventRoiCostData(){
		  
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from event_map_cost_parameter");
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
				queryEnd.append(" from event_map_cost_parameter as atr " +
						"left join activity_type as at on at.id=atr.roi_event_type");

				List fieldNames = Configuration.getColomnList("mapped_event_map_cost_parameter_configuration", accountID, connectionSpace, "event_map_cost_parameter_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										if(obdata.toString().equalsIgnoreCase("roi_event_type")){
											queryTemp.append(" at.act_name ,");	
										}
										else
										queryTemp.append("atr." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" atr.status='Active' ");
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
				// add search query based on the search operation
				query.append(" and atr.");

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
				
				query.append(" order by at.act_name ");
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
								one.put(fieldNames.get(k).toString(),  obdata[k].toString());
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

	public Map<String, String> getActivityMap() {
		return activityMap;
	}

	public void setActivityMap(Map<String, String> activityMap) {
		this.activityMap = activityMap;
	}

	public List<GridDataPropertyView> getMasterViewProp2() {
		return masterViewProp2;
	}

	public void setMasterViewProp2(List<GridDataPropertyView> masterViewProp2) {
		this.masterViewProp2 = masterViewProp2;
	}

	public List<ConfigurationUtilBean> getPackageFields2() {
		return packageFields2;
	}

	public void setPackageFields2(List<ConfigurationUtilBean> packageFields2) {
		this.packageFields2 = packageFields2;
	}
	
	
}
