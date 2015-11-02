package com.Over2Cloud.ctrl.cps.corporate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

public class BudgetPlan extends ActionSupport implements
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
	
	static final Log log = LogFactory.getLog(BudgetPlan.class);
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
	
	Map<String, String>  financialYear;
	Map<Object, Object>  yearMap;
	Map<Object, Object> monthMap;
	Map<String, String> deptMap;
	Map<String, String> existMonthMap;
	
	private String yearName;
	private String defaultValue;
	private String remainBalance;
	private String totalBalance;
	private JSONArray jsonArray;
	
	private List<Map<Object,Object>> gridModel;
	private List<Map<Object,Object>> monthList;
	private Map<String,String> currencyMap;
	
	public String fetchMontlyAllotedBudget() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
			    CommonOperInterface cbt = new CommonConFactory().createInterface();
			    StringBuilder qryString = new StringBuilder();
			    List dataList = null;
			 // Set Current Year
				int year = Calendar.getInstance().get(Calendar.YEAR);
				
			    	qryString.append("SELECT mon.month_for,mon.month_amount FROM allocate_budget_month AS mon ");
			    
			    	if(searchField !=null && !searchField.equalsIgnoreCase("-1")){
			    		qryString.append("inner join allocate_budget_year as yr on yr.id=mon.month_year " +
				" where yr.id = '"+searchField+"' " );
			    		
			    	}else{
			    		qryString.append("inner join allocate_budget_year as yr on yr.id=mon.month_year " +
			    				" where yr.id = '"+String.valueOf(year)+"-"+String.valueOf(year+1)+"' " );
			    			    		
			    	}
			    	
			    	
			    	jsonArray = new JSONArray();
			    	
			    	gridModel=new LinkedList<Map<Object,Object>>();
			        dataList = coi.executeAllSelectQuery(qryString.toString(), connectionSpace);
			        if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								yearMap = new LinkedHashMap<Object, Object>();
								yearMap.put("months",object[0].toString() );
								yearMap.put("values", object[1].toString());
								gridModel.add(yearMap);
								
							}
						}
					}
			        
			        qryString.setLength(0);
			        qryString.append("select yr.year_for, yr.year_amount from allocate_budget_year as yr where yr.id='"+searchField+"' ");
			        dataList = coi.executeAllSelectQuery(qryString.toString(), connectionSpace);
			        if (dataList != null && dataList.size() > 0)
					{
			        	Object[] obj= (Object[])  dataList.get(0); 
			        	yearName=checkNull(obj[0] );
			        	totalBalance=checkNull(obj[1] );
					}
			        
					
					
					
					//Remain Balance of year
					StringBuilder query=new StringBuilder("");
						query.append("select (yr.year_amount - sum(mon.month_amount)  ");
							query.append(" - 0 ");
							
						query.append(" ) , yr.year_amount from  allocate_budget_month as mon right join allocate_budget_year as yr on yr.id=mon.month_year " +
								" where yr.id = '"+searchField+"' ");
						query.append(" group by mon.month_amount ");
						 if (query.toString()!=null)
					        {
					            dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					            if (dataList != null && dataList.size() > 0)
					            {
					                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					                {
					                    Object object[] = (Object[]) iterator.next();
					                    if (object != null)
					                    {
					                    	if(object[0] != null){
					                    		remainBalance = object[0].toString();
					                    	}else{
					                    		remainBalance = object[1].toString();
					                    	}
					                    }
					                }
					            }
					        }
						 
						 monthList=new LinkedList<Map<Object,Object>>();
							// get Month for the year
						Map monthMap2 = new LinkedHashMap<Object, Object>(); 
								monthMap2.put("April","April");
								monthMap2.put("May","May");
								monthMap2.put("June","June");
								monthMap2.put("July","July");
								monthMap2.put("August","August");
								monthMap2.put("September","September");
								monthMap2.put("October","October");
								monthMap2.put("November","November");
								monthMap2.put("December","December");
								monthMap2.put("January","January");
								monthMap2.put("February","February");
								monthMap2.put("March","March");
								
								query.setLength(0);
								 query.append("select mon.month_for from allocate_budget_month as mon " +
								 		" inner join allocate_budget_year as yr on yr.id=mon.month_year " +
								 		" where mon.id <> 0 and  yr.id = '"+searchField+"'" );
								 if (query.toString()!=null)
							        {
							            dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
							            if (dataList != null && dataList.size() > 0)
							            {
							                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
							                {
							                    Object object = (Object) iterator.next();
							                    if (object != null)
							                    {
							                    	monthMap2.remove(object.toString());
							                    }
							                }
							            }
							        }

								 Set Obj= monthMap2.entrySet();
								 
								 Iterator itr= Obj.iterator();

								 while (itr.hasNext()) {
									    Map.Entry entry = (Map.Entry) itr.next();
									    Map obj=new LinkedHashMap<Object,Object>();
									    	obj.put("id", entry.getKey());
									    	obj.put("value", entry.getValue());
									    	monthList.add(obj);
									}
								 
			        returnResult=SUCCESS;
			 
			} catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	
	
	public String fetchBudgetMonthByGraph() {
		String returnresult=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			returnresult=SUCCESS;
			 
		}else {
			return LOGIN; 
		}
		return returnresult;
}
	
	public String fetchMonthByYear() {
		String returnresult=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
			    ComplianceUniversalAction CUA= new ComplianceUniversalAction();
			    String loggedDetail[]= CUA.getEmpDetailsByUserName("WFPM", userName);
			    CommonOperInterface cbt = new CommonConFactory().createInterface();
			    StringBuilder qryString = new StringBuilder();
			    List dataList = null;
				
			    if(!id.equalsIgnoreCase("-1"))
			    {
			    	qryString.setLength(0);
			        
		        	qryString.append("select mon.id , mon.month_for as value" +
					" from  allocate_budget_month as mon inner join allocate_budget_year as yr on yr.id=mon.month_year " +
					" where yr.id = '"+id+"' ");
		        	
			    	dataList = coi.executeAllSelectQuery(qryString.toString(), connectionSpace);
			        if (dataList != null && dataList.size() > 0)
					{
					 jsonArray = new JSONArray();
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								JSONObject obj= new JSONObject();
								obj.put("ID",object[0].toString() );
								obj.put("NAME", object[1].toString());
								jsonArray.add(obj);
							}
						}
					}
					returnresult=SUCCESS;
			    }
			
			 
			}catch(Exception e){
				e.printStackTrace();
				returnresult = ERROR;
				}
		}else {
			return LOGIN; 
		}
		return returnresult;
}
	
	
	public String fetchEmployeeName() {
		String returnresult=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
			    ComplianceUniversalAction CUA= new ComplianceUniversalAction();
			    String loggedDetail[]= CUA.getEmpDetailsByUserName("WFPM", userName);
			    CommonOperInterface cbt = new CommonConFactory().createInterface();
			    StringBuilder qryString = new StringBuilder();
			    List dataList = null;
				
			    if(!id.equalsIgnoreCase("-1"))
			    {
			    	qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
			        qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
			        qryString.append(" WHERE for_contact_sub_type_id=" + loggedDetail[4] + " " +
			        		" AND from_contact_sub_type_id="+id+"' AND module_name='WFPM' AND work_status!=1 AND emp.status='0' " +
			        		" AND cc.id not in ( select empb.emp from allocate_budget_employee as empb " +
			        		" inner join allocate_budget_month as mon ON empb.for_month = empb.for_month where empb.for_month ='"+searchField+"' )  order by emp_name asc");
			        dataList = coi.executeAllSelectQuery(qryString.toString(), connectionSpace);
			        if (dataList != null && dataList.size() > 0)
					{
					 jsonArray = new JSONArray();
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								JSONObject obj= new JSONObject();
								obj.put("ID",object[0].toString() );
								obj.put("NAME", object[1].toString());
								jsonArray.add(obj);
							}
						}
					}
					returnresult=SUCCESS;
			    }
			
			 
			}catch(Exception e){
				e.printStackTrace();
				returnresult = ERROR;
				}
		}else {
			return LOGIN; 
		}
		return returnresult;
}
	
	

	public String viewBudgetPlanHeader() {
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

		financialYear=new LinkedHashMap<String, String>();
		yearMap=new LinkedHashMap<Object, Object>();
		deptMap=new LinkedHashMap<String, String>();
		existMonthMap=new LinkedHashMap<String, String>();
		
		// Set Current Year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=0; i<3; i++)
		{
			financialYear.put(String.valueOf(year+i)+"-"+String.valueOf(year+i+1), String.valueOf(year+i)+"-"+String.valueOf(year+i+1));
		}
		
		//System.out.println("financialYear  "+financialYear);
		
		
		//get Year for month
		String query=" select id, year_for from allocate_budget_year order by id ";
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
						yearMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}

		
		// get Departments
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
                    	deptMap.put(object[0].toString(), object[1].toString());
                    }
                }
            }
        }
	    
        qryString.setLength(0);
        
        qryString.append(" select id from allocate_budget_year where year_for ='"+String.valueOf(year)+"-"+String.valueOf(year+1)+"'");
        if (qryString.toString()!=null)
        {
            List dataList = new createTable().executeAllSelectQuery(qryString.toString(), connectionSpace);
            if (dataList != null && dataList.size() > 0)
            {
                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
                {
                    Object object = (Object) iterator.next();
                    if (object != null)
                    {
                    	defaultValue=object.toString();
                    }
                }
            }
            
        }
        
        yearName=String.valueOf(year)+"-"+String.valueOf(year+1);
        
        // get Remain balance and total budget default
        fetchRemainningBalance();
        
        // get Existing Map for month
        	qryString.setLength(0);
        
        	qryString.append("select mon.id , mon.month_for as value" +
			" from  allocate_budget_month as mon inner join allocate_budget_year as yr on yr.id=mon.month_year " +
			" where yr.year_for = '"+String.valueOf(year)+"-"+String.valueOf(year+1)+"' ");
        	
        	if (qryString.toString()!=null)
		        {
		            List dataList = new createTable().executeAllSelectQuery(qryString.toString(), connectionSpace);
		            if (dataList != null && dataList.size() > 0)
		            {
		                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
		                {
		                	 Object[] object = (Object[]) iterator.next();
		                     if (object[0] != null && object[1] != null)
		                     {
		                     	existMonthMap.put(object[0].toString(), object[1].toString());
		                     }
		                }
		            }
		        }
        	currencyMap = new LinkedHashMap<String, String>();
        	query = " select id, curr_name from currency_type order by curr_name ";
    		if (query != null)
    		{
    			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
    			if (dataList != null && dataList.size() > 0)
    			{
    				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
    				{
    					Object[] object = (Object[]) iterator.next();
    					if (object[0] != null && object[1] != null)
    					{
    						currencyMap.put(object[0].toString(), object[1].toString());
    					}
    				}
    			}
    		}
        
	}
	
	// default
	public String fetchRemainningBalance(){
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				
				monthMap=new LinkedHashMap<Object, Object>(); 
				
				int year = Calendar.getInstance().get(Calendar.YEAR);
				StringBuilder query=new StringBuilder("");
				
					query.append("select ( yr.year_amount - if(sum(mon.month_amount) is null ,0, sum(mon.month_amount)) ");

					if(searchField != null && !searchField.equals("undefined") && !searchField.trim().isEmpty()){
						query.append(" - "+ searchField);
					}else{
						query.append(" - 0 ");
					}
						
					query.append(" ) from  allocate_budget_month as mon right join allocate_budget_year as yr on yr.id=mon.month_year " +
							" where ");
							if(id !=null && id.length()>0){
								query.append(" yr.id = '"+ id +"'");	
							}else{
								query.append(" yr.year_for = '"+String.valueOf(year)+"-"+String.valueOf(year+1)+"' ");
							}
							
							query.append(" group by mon.month_amount ");
				
					 if (query.toString()!=null)
				        {
				            List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				            if (dataList != null && dataList.size() > 0)
				            {
				                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				                {
				                    Object object = (Object) iterator.next();
				                    if (object != null)
				                    {
				                    	remainBalance = object.toString();
				                    }
				                }
				            }
				        }
					 System.out.println("REmain "+remainBalance);
					 
					 if(id == null){
						 
					 
					 query.setLength(0);
					 query.append("select yr.year_amount from allocate_budget_year as yr " +
					 		" where id <>0 and  yr.year_for = '"+String.valueOf(year)+"-"+String.valueOf(year+1)+"'" );
					 if (query.toString()!=null)
				        {
				            List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				            if (dataList != null && dataList.size() > 0)
				            {
				                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				                {
				                    Object object = (Object) iterator.next();
				                    if (object != null)
				                    {
				                    	totalBalance = object.toString();
				                    }
				                }
				            }
				            System.out.println("totalBalance ::  "+ totalBalance);
				        }
					 
					 
					// get Month
						monthMap.put("April","April");
						monthMap.put("May","May");
						monthMap.put("June","June");
						monthMap.put("July","July");
						monthMap.put("August","August");
						monthMap.put("September","September");
						monthMap.put("October","October");
						monthMap.put("November","November");
						monthMap.put("December","December");
						monthMap.put("January","January");
						monthMap.put("February","February");
						monthMap.put("March","March");
						
						query.setLength(0);
						 query.append("select mon.month_for from allocate_budget_month as mon " +
						 		" inner join allocate_budget_year as yr on yr.id=mon.month_year " +
						 		" where mon.id <> 0 and  yr.year_for = '"+String.valueOf(year)+"-"+String.valueOf(year+1)+"'" );
						 if (query.toString()!=null)
					        {
					            List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					            if (dataList != null && dataList.size() > 0)
					            {
					                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					                {
					                    Object object = (Object) iterator.next();
					                    if (object != null)
					                    {
					                    	monthMap.remove(object.toString());
					                    }
					                }
					            }
					        }
						 
					 }
						 returnResult=SUCCESS;
					 
			}
			catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
		
	}
	
	
	public String fetchMonthRemainingBalance(){

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				
				int year = Calendar.getInstance().get(Calendar.YEAR);
				StringBuilder query=new StringBuilder("");
				
					query.append("select mon.month_amount - sum(empb.amount),  mon.month_amount ");
					query.append(" from allocate_budget_employee as empb ");
					query.append("right join allocate_budget_month as mon on mon.id=empb.for_month ");
					query.append(" right join allocate_budget_year as yr on yr.id=mon.month_year ");
					query.append(" where yr.id = '"+yearName+"' ");
					query.append(" group by mon.month_amount ");
					
					if(id != null && !id.equals("undefined") && !id.trim().isEmpty()){
						query.append("and mon.id='"+ id +"' ");
					}	
					
					 if (query.toString()!=null)
				        {
				            List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				            if (dataList != null && dataList.size() > 0)
				            {
				                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				                {
				                    Object[] object = (Object[]) iterator.next();
				                    if (object != null)
				                    {
				                    	if(object[0] != null){
				                    		remainBalance = object[0].toString();
				                    	}else{
				                    		remainBalance = object[1].toString();
				                    	}
				                    }
				                }
				            }
				        }
					 
					 System.out.println("REmain "+remainBalance);
					 
						 returnResult=SUCCESS;
			}
			catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
		
	
		
		
		
	}
	
	
	
	public String beforeAddBudgetPlan(){
		
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
			
		List<GridDataPropertyView> commonList = Configuration.getConfigurationData("mapped_allocate_budget_configuration", accountID, connectionSpace, "", 0, "table_name", "allocate_budget_year_configuration");
		
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
		commonList = Configuration.getConfigurationData("mapped_allocate_budget_configuration", accountID, connectionSpace, "", 0, "table_name", "allocate_budget_month_configuration");
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
		commonList = Configuration.getConfigurationData("mapped_allocate_budget_configuration", accountID, connectionSpace, "", 0, "table_name", "allocate_budget_employee_configuration");
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

	@SuppressWarnings("unchecked")
	public String addFinancialYear()
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
								"mapped_allocate_budget_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"allocate_budget_year_configuration");
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
					
						status=	cbt.createTable22("allocate_budget_year", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
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
				
					status = cbt.insertIntoTable("allocate_budget_year",insertData, connectionSpace);
						
					if (status)
					{
						addActionMessage("Allocate Budget Year Added successfully");
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
	
	
	
	public String addBudgetPlanMonth()
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
								"mapped_allocate_budget_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"allocate_budget_month_configuration");
				boolean status = false;
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							Tablecolumesaaa.add(ob1);
					}
					ob1 = new TableColumes();
					ob1.setColumnname("currency");
					ob1.setDatatype("varchar(100)");
					Tablecolumesaaa.add(ob1);
					
						status=	cbt.createTable22("allocate_budget_month", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
							{
								if(Parmname.equalsIgnoreCase("month_amount")){
										ob = new InsertDataTable();
										ob.setColName(Parmname);
										ob.setDataName(paramValue);
										insertData.add(ob);
										searchField=paramValue.trim();
								}else if(Parmname.equalsIgnoreCase("month_year")){
									ob = new InsertDataTable();
									ob.setColName(Parmname);
									ob.setDataName(paramValue);
									insertData.add(ob);
									yearName=paramValue.trim();
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
				
					// Set Current Year
					int year = Calendar.getInstance().get(Calendar.YEAR);
					
					StringBuilder query=new StringBuilder(" ");
					query.append("select  ("+ searchField
							+" > ( yr.year_amount - if(sum(mon.month_amount) is null ,0, sum(mon.month_amount)) ) ) " +
					 		" from  allocate_budget_month as mon " +
					 		" right join allocate_budget_year as yr on yr.id=mon.month_year " +
					 		" where yr.id='"+yearName+"'" );
					query.append(" group by mon.month_amount ");
					
					 if (query.toString()!=null)
				        {
				            List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				            if (dataList != null && dataList.size() > 0)
				            {
				                for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				                {
				                    Object object = (Object) iterator.next();
				                    if (object != null)
				                    {
				                    	searchField = object.toString();
				                    }
				                }
				            }
				        }
					 	
					 System.out.println("searchField >>>>>>>>>>>>>>>>>>>>>>>>> "+searchField);
					 if(searchField.equalsIgnoreCase("0")){
						 status = cbt.insertIntoTable("allocate_budget_month",insertData, connectionSpace);
						 
					 }else{
						 status=false;
					 }
						
					}
					if (status)
					{
						addActionMessage("Allocate Budget Month Added successfully");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops Error: Entered Amount Should Not Be Greater Than Total Yearly Budget ");
						return SUCCESS;
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
	
	public String addBudgetPlanForEmployee()
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
								"mapped_allocate_budget_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"allocate_budget_employee_configuration");
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
					
						status=	cbt.createTable22("allocate_budget_employee", Tablecolumesaaa,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& Parmname != null
								)
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
					
					status = cbt.insertIntoTable("allocate_budget_employee",insertData, connectionSpace);

					if (status)
					{
						addActionMessage("Allocate Budget Employee successfully: ");
						returnResult= SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						returnResult= SUCCESS;
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
	
	
	
	
	public String viewConfiguredBudgetPlan(){
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
						
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_allocate_budget_configuration",accountID,connectionSpace,"",0,"table_name","allocate_budget_year_configuration");
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
			statusColName=Configuration.getConfigurationData("mapped_allocate_budget_configuration",accountID,connectionSpace,"",0,"table_name","allocate_budget_month_configuration");
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
		  statusColName=Configuration.getConfigurationData("mapped_allocate_budget_configuration",accountID,connectionSpace,"",0,"table_name","allocate_budget_employee_configuration");
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
	 
	 public String viewBudgetYearData(){
		    
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from allocate_budget_year order by id");
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
				queryEnd.append(" from allocate_budget_year as yr");

				List fieldNames = Configuration.getColomnList("mapped_allocate_budget_configuration", accountID, connectionSpace, "allocate_budget_year_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										queryTemp.append("yr." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" yr.status='Active' ");
				
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
				
				query.append(" order by yr.id ");
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
	 
	 public String viewBudgetPlanMonthData(){
		  
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from allocate_budget_month");
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
				queryEnd.append(" from allocate_budget_month as mon inner join allocate_budget_year as yr on yr.id=mon.month_year");

				List fieldNames = Configuration.getColomnList("mapped_allocate_budget_configuration", accountID, connectionSpace, "allocate_budget_month_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										if(obdata.toString().equalsIgnoreCase("month_year")){
											queryTemp.append(" yr.year_for ,");	
										}
										else
										queryTemp.append("mon." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" mon.status='Active' and mon.month_year='"+id+"'");
				
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
				
				query.append(" order by mon.month_amount ");
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
	 
	 
	 public String viewBudgetPlanEmployeeData(){
		  
			try {
				if (!ValidateSession.checkSession()) return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from allocate_budget_employee ");
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
				
				queryEnd.append(" from allocate_budget_employee as empb " +
						" inner join allocate_budget_month as mon ON mon.id = empb.for_month " +
						" inner join allocate_budget_year as yr ON yr.id = mon.month_year "+
						" inner JOIN contact_sub_type AS dept ON dept.id = empb.dept " +
						 " inner JOIN manage_contact AS cc ON cc.id = empb.emp " +
						 "inner join primary_contact AS emp on cc.emp_id = emp.id ");
				
				List fieldNames = Configuration.getColomnList("mapped_allocate_budget_configuration", accountID, connectionSpace, "allocate_budget_employee_configuration");
				List<Object> Listhb = new ArrayList<Object>();
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected fields
						Object obdata = (Object) it.next();
							if (obdata != null)
								{
										if(obdata.toString().equalsIgnoreCase("for_month")){
											queryTemp.append(" mon.month_for ,");	
										}
										else if(obdata.toString().equalsIgnoreCase("for_year")){
											queryTemp.append(" yr.year_for ,");	
										}else if(obdata.toString().equalsIgnoreCase("dept")){
											queryTemp.append(" dept.contact_subtype_name , ");
										}else if(obdata.toString().equalsIgnoreCase("emp")){
											queryTemp.append(" emp.emp_name , ");
										}
										else
										queryTemp.append("empb." + obdata.toString() + ",");	
								}
						}
						query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
						query.append(" ");
						query.append(queryEnd.toString());
						query.append(" where ");
						query.append(" empb.status='Active' and empb.for_month='"+id+"'");
				
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
				
				query.append(" order by empb.amount ");
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


	public Map<String, String> getFinancialYear() {
		return financialYear;
	}


	public void setFinancialYear(Map<String, String> financialYear) {
		this.financialYear = financialYear;
	}

	public Map<String, String> getDeptMap() {
		return deptMap;
	}


	public void setDeptMap(Map<String, String> deptMap) {
		this.deptMap = deptMap;
	}


	public String getYearName() {
		return yearName;
	}


	public void setYearName(String yearName) {
		this.yearName = yearName;
	}


	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	public String getRemainBalance() {
		return remainBalance;
	}


	public void setRemainBalance(String remainBalance) {
		this.remainBalance = remainBalance;
	}


	public String getTotalBalance() {
		return totalBalance;
	}


	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}


	public Map<String, String> getExistMonthMap() {
		return existMonthMap;
	}


	public void setExistMonthMap(Map<String, String> existMonthMap) {
		this.existMonthMap = existMonthMap;
	}



	public JSONArray getJsonArray() {
		return jsonArray;
	}



	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}




	public Map<Object, Object> getYearMap() {
		return yearMap;
	}




	public void setYearMap(Map<Object, Object> yearMap) {
		this.yearMap = yearMap;
	}




	public List<Map<Object, Object>> getGridModel() {
		return gridModel;
	}




	public void setGridModel(List<Map<Object, Object>> gridModel) {
		this.gridModel = gridModel;
	}




	public Map<Object, Object> getMonthMap() {
		return monthMap;
	}




	public void setMonthMap(Map<Object, Object> monthMap) {
		this.monthMap = monthMap;
	}




	public List<Map<Object, Object>> getMonthList() {
		return monthList;
	}




	public void setMonthList(List<Map<Object, Object>> monthList) {
		this.monthList = monthList;
	}




	public void setCurrencyMap(Map<String,String> currencyMap)
	{
		this.currencyMap = currencyMap;
	}




	public Map<String,String> getCurrencyMap()
	{
		return currencyMap;
	}
	
	
	
	
}
