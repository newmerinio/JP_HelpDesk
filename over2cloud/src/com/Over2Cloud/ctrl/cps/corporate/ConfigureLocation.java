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

public class ConfigureLocation extends ActionSupport implements
		ServletRequestAware {

	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	CommonOperInterface					coi							= new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewProp1 = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewProp2 = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewProp3 = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;
	
	static final Log log = LogFactory.getLog(ConfigureLocation.class);
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
	
	List<ConfigurationUtilBean> packageFields;
	
	List<ConfigurationUtilBean> packageFields1;
	List<ConfigurationUtilBean> packageFields2;
	List<ConfigurationUtilBean> packageFields3;
	
	Map<String, String>  countryMap;
	Map<String, String> stateMap;
	Map<String, String> cityMap;

	
	
	public String viewConfigureLocationHeader() {
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
	
	
	void createFormList(){
		countryMap=new LinkedHashMap<String, String>();
		stateMap=new LinkedHashMap<String, String>();
		cityMap=new LinkedHashMap<String, String>();
		
		//query="select code,country_name from country GROUP by country_name ORDER BY country_name ";
		//query="select city_code,state_name from state where city_code='IND' GROUP by state_name ";
		
		//get Country
		String query=" select distinct id, country_name from country order by country_name ";
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
						countryMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		System.out.println(countryMap);
		// get State
		query="select id,state_name from state order by state_name ";
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
						stateMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		
		// get city
		/*query="select distinct city_name as ct ,city_name from city order by city_name ";
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
						cityMap.put(object[0].toString(), object[0].toString());
					}
				}
			}
		}*/
		
	}
	
	
	
	public String beforeAddConfigureLocation(){
		
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAddPageDataFields();
				createFormList();
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
			
		List<GridDataPropertyView> commonList = Configuration.getConfigurationData("mapped_pcrm_location_configuration", accountID, connectionSpace, "", 0, "table_name", "pcrm_country_configuration");
		
		// country
		packageFields1 = new ArrayList<ConfigurationUtilBean>();

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
					packageFields1.add(obj);
					
			}
		}
		
		// State
		commonList.clear();
		commonList = Configuration.getConfigurationData("mapped_pcrm_location_configuration", accountID, connectionSpace, "", 0, "table_name", "pcrm_state_configuration");
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

		// City
		commonList.clear();
		commonList = Configuration.getConfigurationData("mapped_pcrm_location_configuration", accountID, connectionSpace, "", 0, "table_name", "pcrm_city_configuration");
		packageFields3 = new ArrayList<ConfigurationUtilBean>();

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
					packageFields3.add(obj);
			}
		}
		
		}catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
			}
	}

	public String addCountry()
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
								"mapped_pcrm_location_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"pcrm_country_configuration");
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
					
						status=	cbt.createTable22("country", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] country=new String[0];
					Object [] code=new String[0];
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
						{
							if(Parmname.equalsIgnoreCase("country_name")){
								country=request.getParameterValues(Parmname);
							}else if(Parmname.equalsIgnoreCase("code")){
								code=request.getParameterValues(Parmname);
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
				
					System.out.println(Arrays.asList(country));
					
					System.out.println(Arrays.asList(code));
					
					int count=0;
					String codeExist="";
					for(int i=0;i<country.length;i++){
						
						if(country[i]!=null && !country[i].toString().trim().equalsIgnoreCase("") && code[i]!=null && !code[i].toString().trim().equalsIgnoreCase("")){
					InsertDataTable	idt1 = new InsertDataTable();
					idt1.setColName("country_name");
					idt1.setDataName(checkNull(country[i]));
						insertData.add(idt1);
							
						InsertDataTable	idt6 = new InsertDataTable();
							idt6.setColName("code");
							idt6.setDataName(checkNull(code[i]));
							insertData.add(idt6);	
							

							if((cbt.executeAllSelectQuery("select * from country where code='"+checkNull(code[i])+"'", connectionSpace)).size()>0){
								codeExist="\n Code Already Exists: "+checkNull(code[i]);
							}else{
								status = cbt.insertIntoTable("country",insertData, connectionSpace);
								count++;
								insertData.remove(idt1);
								insertData.remove(idt6);
							}
						}
					}
						insertData.clear();
					if (status)
					{
						addActionMessage("Country Added successfully: "+count
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
			returnResult=obj.toString().trim();
		}
		return returnResult;
	}
	
	
	
	public String addState()
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
								"mapped_pcrm_location_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"pcrm_state_configuration");
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
					
						status=	cbt.createTable22("state", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] state=new String[0];
					String countryid="";
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
						{
							if(Parmname.equalsIgnoreCase("state_name")){
								state=request.getParameterValues(Parmname);
							}
							else if(Parmname.equalsIgnoreCase("country_code")){
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
								countryid=paramValue;
								insertData.add(ob);
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
				
					System.out.println(Arrays.asList(state));
					
					//System.out.println(Arrays.asList(code));
					
					int count=0;
					String codeExist="";
					for(int i=0;i<state.length;i++){
						
						if(state[i]!=null && !state[i].toString().trim().equalsIgnoreCase("")){
					InsertDataTable	idt1 = new InsertDataTable();
					idt1.setColName("state_name");
					idt1.setDataName(checkNull(state[i]));
						insertData.add(idt1);

							if((cbt.executeAllSelectQuery("select * from state where state_name='"+checkNull(state[i])+"' and country_code='"+countryid+"'", connectionSpace)).size()>0){
									codeExist="\n State Already Exists: "+checkNull(state[i]);
							}else{
								status = cbt.insertIntoTable("state",insertData, connectionSpace);
								count++;
							}
							insertData.remove(idt1);
						}
					}
						insertData.clear();
					if (status)
					{
						addActionMessage("State Added successfully: "+count
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
	
	public String addCity()
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
								"mapped_pcrm_location_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"pcrm_city_configuration");
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
					
						status=	cbt.createTable22("city", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Object [] city=new String[0];
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
						{
							if(Parmname.equalsIgnoreCase("city_name")){
								city=request.getParameterValues(Parmname);
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
				
					System.out.println(Arrays.asList(city));
					
					int count=0;
					String codeExist="";
					for(int i=0;i<city.length;i++){
						
						if(city[i]!=null && !city[i].toString().trim().equalsIgnoreCase("")){
										InsertDataTable	idt1 = new InsertDataTable();
										idt1.setColName("city_name");
										idt1.setDataName(checkNull(city[i]));
											insertData.add(idt1);
													status = cbt.insertIntoTable("city",insertData, connectionSpace);
													count++;
													insertData.remove(idt1);
						}
					}
						insertData.clear();
					if (status)
					{
						addActionMessage("City Added successfully: "+count
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
	
	
	
	
	public String viewConfigureLocation(){
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
		try
			{
					String accountID=(String)session.get("accountid");
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					GridDataPropertyView gpv=new GridDataPropertyView();
					gpv.setColomnName("id");
					gpv.setHeadingName("Id");
					gpv.setHideOrShow("true");
						masterViewProp1.add(gpv);
						masterViewProp2.add(gpv);
						masterViewProp3.add(gpv);
						
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_pcrm_location_configuration",accountID,connectionSpace,"",0,"table_name","pcrm_country_configuration");
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
									masterViewProp1.add(gpv);
							}
				}
				
			statusColName.clear();
			statusColName=Configuration.getConfigurationData("mapped_pcrm_location_configuration",accountID,connectionSpace,"",0,"table_name","pcrm_state_configuration");
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
				
		   statusColName.clear();	
		  statusColName=Configuration.getConfigurationData("mapped_pcrm_location_configuration",accountID,connectionSpace,"",0,"table_name","pcrm_city_configuration");
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
									masterViewProp3.add(gpv);
							}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	 
	 public String viewCountryData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(distinct code) from country");
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
				queryEnd.append(" from country as c");

				List fieldNames = Configuration.getColomnList("mapped_pcrm_location_configuration", accountID, connectionSpace, "pcrm_country_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										/*if(obdata.toString().equalsIgnoreCase("id")){
											queryTemp.append("distinct c.code as id,");	
										}
										else*/
										queryTemp.append("c." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" c.status='Active' ");
				
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
				
				query.append(" order by c.country_name ");
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
								one.put(fieldNames.get(k).toString(), obdata[k].toString());
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
	 
	 public String editCountryDataGrid()
	 {
		 System.out.println(getOper());
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
						boolean status = cbt.updateTableColomn("country", wherClause, condtnBlock, connectionSpace);
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
							cbt.updateTableColomn("country", wherClause, condtnBlock, connectionSpace);
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
	 
	 public String viewStateData(){
		  
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(distinct state_name) from state");
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
				queryEnd.append(" from state as c inner join country as st on st.id=c.country_code");

				List fieldNames = Configuration.getColomnList("mapped_pcrm_location_configuration", accountID, connectionSpace, "pcrm_state_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										if(obdata.toString().equalsIgnoreCase("country_code")){
											queryTemp.append(" st.country_name ,");	
										}
										else
										queryTemp.append("c." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" c.status='Active' and c.country_code='"+id+"'");
				
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
				
				query.append(" order by c.state_name ");
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
	 
	 
	 public String viewCityData(){
		  
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(distinct city_name) from city ");
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
				queryEnd.append(" from city as c inner join state as st on st.id=c.name_state left join country as ct on ct.id=c.code_country ");

				List fieldNames = Configuration.getColomnList("mapped_pcrm_location_configuration", accountID, connectionSpace, "pcrm_city_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										if(obdata.toString().equalsIgnoreCase("name_state")){
											queryTemp.append(" st.state_name ,");	
										}
										else if(obdata.toString().equalsIgnoreCase("code_country")){
											queryTemp.append(" ct.country_name ,");	
										}
										else
										queryTemp.append("c." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" c.status='Active' and c.name_state='"+id+"'");
				
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
				
				query.append(" order by c.city_name ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				// System.out.println("query.toString()>>"+query.toString()); 
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

	public List<GridDataPropertyView> getMasterViewProp1() {
		return masterViewProp1;
	}

	public void setMasterViewProp1(List<GridDataPropertyView> masterViewProp1) {
		this.masterViewProp1 = masterViewProp1;
	}

	public List<GridDataPropertyView> getMasterViewProp2() {
		return masterViewProp2;
	}

	public void setMasterViewProp2(List<GridDataPropertyView> masterViewProp2) {
		this.masterViewProp2 = masterViewProp2;
	}

	public List<GridDataPropertyView> getMasterViewProp3() {
		return masterViewProp3;
	}

	public void setMasterViewProp3(List<GridDataPropertyView> masterViewProp3) {
		this.masterViewProp3 = masterViewProp3;
	}

	public List<ConfigurationUtilBean> getPackageFields1() {
		return packageFields1;
	}

	public void setPackageFields1(List<ConfigurationUtilBean> packageFields1) {
		this.packageFields1 = packageFields1;
	}

	public List<ConfigurationUtilBean> getPackageFields2() {
		return packageFields2;
	}

	public void setPackageFields2(List<ConfigurationUtilBean> packageFields2) {
		this.packageFields2 = packageFields2;
	}

	public List<ConfigurationUtilBean> getPackageFields3() {
		return packageFields3;
	}

	public void setPackageFields3(List<ConfigurationUtilBean> packageFields3) {
		this.packageFields3 = packageFields3;
	}


	public Map<String, String> getCountryMap() {
		return countryMap;
	}


	public void setCountryMap(Map<String, String> countryMap) {
		this.countryMap = countryMap;
	}


	public Map<String, String> getStateMap() {
		return stateMap;
	}


	public void setStateMap(Map<String, String> stateMap) {
		this.stateMap = stateMap;
	}


	public Map<String, String> getCityMap() {
		return cityMap;
	}


	public void setCityMap(Map<String, String> cityMap) {
		this.cityMap = cityMap;
	}
	
}
