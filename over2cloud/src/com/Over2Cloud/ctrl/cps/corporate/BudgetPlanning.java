package com.Over2Cloud.ctrl.cps.corporate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BudgetPlanning extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	static final Log log = LogFactory.getLog(ScheduleConfigureMaster.class);
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewProp2 =new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> dataTextBox = null;
	private List<ConfigurationUtilBean> dataDropDown = null;
	
	private List<ConfigurationUtilBean> dataTextBox1 = null;
	private List<ConfigurationUtilBean> dataDropDown1 = null;
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	
	private List<Object> viewList;
	private Map<String, String> departmentData = null;
	private Map<String, String> financialYear = null;
	private Map<String, String> monthList = null;
	private JSONArray jsonArray;
	
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
	private String count;
	
	private String deptid;
	private String forMonth;
	private String removeMonth;
	private String empId;
	CommonOperInterface	coi	= new CommonConFactory().createInterface();
	
	public String beforeBudgetPlanning() {
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
	
	
	public String viewBudgetPlanningDetails(){
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setConfigureSchduleViewProp();
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
	
	
	public String viewAllocateBudgetDetails(){
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAllocateViewProp();
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
	
	
	
	public void setAllocateViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp2.add(gpv);
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_allocate_budget_configuration",accountID,connectionSpace,"",0,"table_name","allocate_budget_configuration");
			
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
			System.out.println("masterViewProp2   "+masterViewProp2.size());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void setConfigureSchduleViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_budget_planning_configuration", accountID, connectionSpace, "", 0, "table_name", "budget_planning_configuration");
			if(statusColName!=null&&statusColName.size()>0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{

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
					masterViewProp.add(gpv);
				}
			}
			System.out.println("masterViewProp   "+masterViewProp.size());
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
public String viewBudgetPlanningMasterData(){
	    
		try {
			if (!ValidateSession.checkSession()) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from budget_planning");
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
			queryEnd.append(" from budget_planning as cs ");
			//queryEnd.append(" inner join primary_contact as eb on cs.emp=eb.id");
			//queryEnd.append(" inner join contact_sub_type as dept on cs.department=dept.id");

			List fieldNames = Configuration.getColomnList("mapped_budget_planning_configuration", accountID, connectionSpace, "budget_planning_configuration");
			List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
						if (obdata != null)
							{
							
									if(obdata.toString().equalsIgnoreCase("department")){
										queryTemp.append("dept.contact_subtype_name" + ",");
									}
									else if(obdata.toString().equalsIgnoreCase("emp"))
									{
										queryTemp.append("eb.emp_name" +  ",");
									}
									else{
										queryTemp.append("cs." + obdata.toString() + ",");	
									}
									
							}	
				}
					query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
					query.append(" ");
					query.append(queryEnd.toString());
					query.append(" where ");
					query.append(" cs.status='Active' ");
			
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
			
			//query.append(" order by cp.src_subtype ");
			System.out.println("query.toString()>>"+query.toString()); 
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			
			if (data != null)
			{
			for (Iterator it = data.iterator(); it.hasNext();)
			{
			Object[] obdata = (Object[]) it.next();
			Map<String, Object> one = new HashMap<String, Object>();
				for (int k = 0; k < fieldNames.size(); k++)
				{
					if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("NA") && obdata[k].toString().length() > 0 )
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
							else if(fieldNames.get(k).toString().equalsIgnoreCase("deadline_date"))
							{
								obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
							}
							else if(fieldNames.get(k).toString().equalsIgnoreCase("reminder1"))
							{
								obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
							}
							else if(fieldNames.get(k).toString().equalsIgnoreCase("reminder2"))
							{
								obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
							}
							else if(fieldNames.get(k).toString().equalsIgnoreCase("reminder3"))
							{
								obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
							}
							one.put(fieldNames.get(k).toString(), obdata[k].toString());
							
						}
					}
					else
					{
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
	



public String beforeBudgetPlanningAdd()
{
	try
	{
		if (!ValidateSession.checkSession())
			return LOGIN;
		setAddPageDataForBudgetPlanning();
		setAddPageDataForAllocateBudget();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		System.out.println("year  "+year);
		financialYear=new LinkedHashMap<String, String>();
		
		/*String finyear=String.valueOf(year)+"-"+String.valueOf(year+1);
		System.out.println("finyear  "+finyear);*/
		
		for(int i=0; i<1; i++)
		{
			financialYear.put(String.valueOf(year+i)+"-"+String.valueOf(year+i+1), String.valueOf(year+i)+"-"+String.valueOf(year+i+1));
		}
		
		System.out.println("financialYear  "+financialYear);
		
		departmentData=new LinkedHashMap<String, String>();
		departmentData=getDepartment();
		monthList=new LinkedHashMap<String, String>();
		monthList=monthListData();

		

	} catch (Exception e)
	{
		log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
		e.printStackTrace();
	}
	return SUCCESS;
}

public Map<String,String> monthListData(){
	
	Map<String,String> deptlist=new LinkedHashMap<String, String>();
	
	try {
		String query=null;
		query="select id,month_wise_breakup from  budget_planning where plan_budget_for='2015-2016'  group by month_wise_breakup";
		System.out.println("query.toString()     actid "+query.toString());
		List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator it = dataList.iterator(); it.hasNext();)
			{
			Object[] obdata =  (Object[]) it.next();
			
				if(obdata[0] !=null && obdata[1] !=null)
				{
					deptlist.put(obdata[1].toString(), obdata[1].toString());
				}
			
			}

		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("deptlist  "+deptlist.size());
	return deptlist;
	
}


public void setAddPageDataForBudgetPlanning()
{
	try
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();

		// Build map for text box and drop down for employee basic
		// information
		
		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_budget_planning_configuration", accountID, connectionSpace, "", 0, "table_name", "budget_planning_configuration");
		dataTextBox = new ArrayList<ConfigurationUtilBean>();
		dataDropDown = new ArrayList<ConfigurationUtilBean>();
		
		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("status")  )
			{
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
				if (gdp.getMandatroy().toString().equals("1"))
				{
					obj.setMandatory(true);
				} else
				{
					obj.setMandatory(false);
				}
				dataTextBox.add(obj);
				System.out.println("dataTextBox  "+dataTextBox.size());
			}

			

			if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
			{

				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
				if (gdp.getMandatroy().toString().equals("1"))
				{
					obj.setMandatory(true);
				} else
				{
					obj.setMandatory(false);
				}
				
				dataDropDown.add(obj);
				System.out.println("dataDropDown  "+dataDropDown.size());
			}
			
			
			
			
			
			

		}

		

		

	} catch (Exception ex)
	{
		log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
		ex.printStackTrace();
	}
}







public void setAddPageDataForAllocateBudget()
{
	try
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();

		// Build map for text box and drop down for employee basic
		// information
		
		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_allocate_budget_configuration", accountID, connectionSpace, "", 0, "table_name", "allocate_budget_configuration");
		dataTextBox1 = new ArrayList<ConfigurationUtilBean>();
		dataDropDown1 = new ArrayList<ConfigurationUtilBean>();
		
		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("status")  )
			{
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
				if (gdp.getMandatroy().toString().equals("1"))
				{
					obj.setMandatory(true);
				} else
				{
					obj.setMandatory(false);
				}
				dataTextBox1.add(obj);
				System.out.println("dataTextBox1  "+dataTextBox1.size());
			}

			

			if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
			{

				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
				if (gdp.getMandatroy().toString().equals("1"))
				{
					obj.setMandatory(true);
				} else
				{
					obj.setMandatory(false);
				}
				
				dataDropDown1.add(obj);
				System.out.println("dataDropDown1  "+dataDropDown1.size());
			}
			
			
			
			
			
			

		}

		

		

	} catch (Exception ex)
	{
		log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
		ex.printStackTrace();
	}
}












public Map<String, String> getDepartment()
{
	Map<String,String> deptlist=new LinkedHashMap<String, String>();
    ComplianceUniversalAction CUA= new ComplianceUniversalAction();
    String loggedDetail[]= CUA.getEmpDetailsByUserName("WFPM", userName);
    StringBuilder qryString = new StringBuilder();
    qryString.append("SELECT dept.id,dept.contact_subtype_name FROM primary_contact AS emp");
    qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
    qryString.append(" INNER JOIN contact_sub_type AS dept ON cc.from_contact_sub_type_id=dept.id");
    qryString.append(" WHERE for_contact_sub_type_id=" + loggedDetail[4] + " AND module_name='WFPM' AND work_status!=1 AND emp.status='0' order by dept.contact_subtype_name asc");
    
    System.out.println("qryString.toString()   "+qryString.toString());
    try {
        
        
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
                        deptlist.put(object[0].toString(), object[1].toString());
                    }
                }
            }
        }
        
    
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("deptlist  "+deptlist.size());
    return deptlist;
}




public String employeeNameData(){
	
	
	
	
	String returnresult=ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			Map<String,String> deptlist=new LinkedHashMap<String, String>();
		    ComplianceUniversalAction CUA= new ComplianceUniversalAction();
		    String loggedDetail[]= CUA.getEmpDetailsByUserName("WFPM", userName);
		    CommonOperInterface cbt = new CommonConFactory().createInterface();
		    StringBuilder qryString = new StringBuilder();
		    List dataList = null;
			
		    if(!deptid.equalsIgnoreCase("-1"))
		    {
		    	qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
		        qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
		        qryString.append(" WHERE for_contact_sub_type_id=" + loggedDetail[4] + " AND from_contact_sub_type_id="+deptid+"' AND module_name='WFPM' AND work_status!=1 AND emp.status='0' order by emp_name asc");
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




public String monthsForAllocate(){
	String returnresult=ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			
		String query="select id,month_wise_breakup from  budget_planning where plan_budget_for='2015-2016'  group by month_wise_breakup";
		
		List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		
		 if (dataList != null && dataList.size() > 0)
			{
			 jsonArray = new JSONArray();
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						JSONObject obj= new JSONObject();
						obj.put("ID",object[1].toString() );
						obj.put("NAME", object[1].toString());
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



public String removemonthsForAllocate(){
	String returnresult=ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
		System.out.println("removeMonth   "+removeMonth);
		String[] monthName=removeMonth.split("-");
		String query=null;
		StringBuilder removeMonthlist=new StringBuilder();
		for(int i=0; i<monthName.length; i++)
		{
			System.out.println(monthName[i]);
			removeMonthlist=removeMonthlist.append("'"+monthName[i]+"',");
			
			
			
		}
		System.out.println(removeMonthlist.substring(0, removeMonthlist.toString().lastIndexOf(",")));
		
		
			
		query="select id,month_wise_breakup from  budget_planning where month_wise_breakup not in("+removeMonthlist.substring(0, removeMonthlist.toString().lastIndexOf(","))+") and plan_budget_for='2015-2016'  group by month_wise_breakup";
		
		List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		
		 if (dataList != null && dataList.size() > 0)
			{
			 jsonArray = new JSONArray();
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						JSONObject obj= new JSONObject();
						obj.put("ID",object[1].toString() );
						obj.put("NAME", object[1].toString());
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







public String monthSumData(){
	String returnresult=ERROR;
	String s=null;
	int total=0;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
		Integer valueInAllocate=valuefromallocateBudget(forMonth,empId);
		Integer valueInBudgetPlanning=valuefromBudgetPlanning(forMonth);
		System.out.println("valueInAllocate  "+valueInAllocate);
		System.out.println("valueInBudgetPlanning  "+valueInBudgetPlanning);
		
		if(valueInBudgetPlanning !=null )
		{
			total=valueInBudgetPlanning-valueInAllocate;
		}
		System.out.println(" total  "+total);
			 jsonArray = new JSONArray();
						JSONObject obj= new JSONObject();
						obj.put("ID",String.valueOf(total) );
						jsonArray.add(obj);
					
				
				
				System.out.println("jsonArray   "+jsonArray);
				
			
			
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

public Integer valuefromallocateBudget(String forMonth,String empId){
	int i=0;
		try
		{
		String query="select sum(amount) from allocate_budget where for_month="+forMonth+"'";
		
		List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		
		 if (dataList != null && dataList.size() > 0)
			{
			
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Double object =  (Double) iterator.next();
					if(object !=null)
					{
						i = object.intValue();
					}
						
					
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			}
	
	return i;
}

public Integer valuefromBudgetPlanning(String forMonth){
	Integer returnresult=0;
		try
		{
		String query="select sum(month_value) from budget_planning where month_wise_breakup="+forMonth+"'";
		
		List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		
		 if (dataList != null && dataList.size() > 0)
			{
			
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Double object =  (Double) iterator.next();
					if(object !=null)
					{
						returnresult= object.intValue();
					}
						
					
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			}
	
	return returnresult;
}





public String viewAllocatedBudgetGridData(){
	  
	try {
		if (!ValidateSession.checkSession()) return LOGIN;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append(" select count(*) from allocate_budget");
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
		queryEnd.append(" from allocate_budget as ab ");
		queryEnd.append(" inner join contact_sub_type AS dept ON dept.id = ab.dept ");
		queryEnd.append(" inner JOIN manage_contact AS cc ON cc.id = ab.emp ");
		queryEnd.append(" inner join primary_contact AS emp ON cc.emp_id = emp.id ");
		
		
		


		List fieldNames = Configuration.getColomnList("mapped_allocate_budget_configuration", accountID, connectionSpace, "allocate_budget_configuration");
		List<Object> Listhb = new ArrayList<Object>();
			for (Iterator it = fieldNames.iterator(); it.hasNext();)
			{
				// generating the dyanamic query based on selected fields
				Object obdata = (Object) it.next();
					if (obdata != null)
						{
								if(obdata.toString().equalsIgnoreCase("emp")){
									queryTemp.append(" emp.emp_name ,");	
								}
								else if(obdata.toString().equalsIgnoreCase("dept"))
								{
									queryTemp.append(" dept.contact_subtype_name ,");
								}
								else
								queryTemp.append("ab." + obdata.toString() + ",");	
						}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" ab.status='Active'");
		
		if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
		{
		// add search query based on the search operation
		query.append(" and ab.");

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
		
	//	query.append(" order by atr.ract_resaon ");
		System.out.println("query.toString()>> sub "+query.toString()); 
		List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		
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

public String editBudgetPlanningDataGrid()
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
					boolean status = cbt.updateTableColomn("budget_planning", wherClause, condtnBlock, connectionSpace);
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
						cbt.updateTableColomn("budget_planning", wherClause, condtnBlock, connectionSpace);
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






public String editAllocateBudgetDataGrid()
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
					boolean status = cbt.updateTableColomn("allocate_budget", wherClause, condtnBlock, connectionSpace);
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
						cbt.updateTableColomn("allocate_budget", wherClause, condtnBlock, connectionSpace);
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











public String addBudgetata()
{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_budget_planning_configuration", accountID, connectionSpace, "", 0, "table_name", "budget_planning_configuration");
			if (statusColName != null)
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	             Collections.sort(requestParameterNames);
	             Iterator it = requestParameterNames.iterator();
	             Object [] month_wise_breakup=new String[0];
					Object [] month_value=new String[0];
	             while (it.hasNext()) 
	          {
		       String Parmname = it.next().toString();
		       String paramValue = request.getParameter(Parmname);
		       if (paramValue != null && !paramValue.equalsIgnoreCase("") 
		    	  && !paramValue.equalsIgnoreCase("-1") && Parmname != null )
				 {
			        System.out.println("Parmname  "+Parmname+"  paramValue "+paramValue);
			        
			        if(Parmname.equalsIgnoreCase("month_wise_breakup")){
			        	month_wise_breakup=request.getParameterValues(Parmname);
					}else if(Parmname.equalsIgnoreCase("month_value")){
						month_value=request.getParameterValues(Parmname);
					}
					else{
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					ob.setDataName(paramValue);
					insertData.add(ob);
					}
			       
			     }
			  }
				
	             
	          System.out.println(Arrays.asList(month_wise_breakup));
					
					System.out.println(Arrays.asList(month_value));
	             
	             	ob = new InsertDataTable();
					ob.setColName("user");
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
				
				
int count=0;
					
					for(int i=0;i<month_wise_breakup.length;i++){
						
						if(month_wise_breakup[i]!=null && !month_wise_breakup[i].toString().trim().equalsIgnoreCase("") && !month_wise_breakup[i].toString().trim().equalsIgnoreCase("-1")){
							InsertDataTable	idt1 = new InsertDataTable();
							idt1.setColName("month_wise_breakup");
							idt1.setDataName(checkNull(month_wise_breakup[i]));
							insertData.add(idt1);
							
							InsertDataTable	idt6 = new InsertDataTable();
							idt6.setColName("month_value");
							idt6.setDataName(checkNull(month_value[i]));
							insertData.add(idt6);	
							
							status = cbt.insertIntoTable("budget_planning",insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
							insertData.remove(idt6);
						}
					}
					
					
					insertData.clear();
				
			
				

				if (status)
				{
					addActionMessage("Budget Planning Added successfully!!!"+count);
					return SUCCESS;
				} else
				{
					addActionMessage("Oops There is some error in data!");
					return SUCCESS;
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}
	} else
	{
		returnResult = LOGIN;
	}
	return returnResult;
}



public String addAllocateBudgetata()
{
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_allocate_budget_configuration", accountID, connectionSpace, "", 0, "table_name", "allocate_budget_configuration");
			System.out.println("statusColName  "+statusColName.size());
			if (statusColName != null)
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	             Collections.sort(requestParameterNames);
	             Iterator it = requestParameterNames.iterator();
	             Object [] for_month=new String[0];
					Object [] amount=new String[0];
	             while (it.hasNext()) 
	          {
		       String Parmname = it.next().toString();
		       String paramValue = request.getParameter(Parmname);
		       if (paramValue != null && !paramValue.equalsIgnoreCase("") 
		    	  && !paramValue.equalsIgnoreCase("-1") && Parmname != null )
				 {
			        System.out.println("Parmname  "+Parmname+"  paramValue "+paramValue);
			        
			        if(Parmname.equalsIgnoreCase("for_month")){
			        	for_month=request.getParameterValues(Parmname);
					}else if(Parmname.equalsIgnoreCase("amount")){
						amount=request.getParameterValues(Parmname);
					}
					else{
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					ob.setDataName(paramValue);
					insertData.add(ob);
					}
			       
			     }
			  }
				
	             
	          System.out.println(Arrays.asList(for_month));
					
					System.out.println(Arrays.asList(amount));
	             
	             	ob = new InsertDataTable();
					ob.setColName("user");
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
				
				
int count=0;
					
					for(int i=0;i<for_month.length;i++){
						
						if(for_month[i]!=null && !for_month[i].toString().trim().equalsIgnoreCase("") && !for_month[i].toString().trim().equalsIgnoreCase("-1")){
							InsertDataTable	idt1 = new InsertDataTable();
							idt1.setColName("for_month");
							idt1.setDataName(checkNull(for_month[i]));
							insertData.add(idt1);
							
							InsertDataTable	idt6 = new InsertDataTable();
							idt6.setColName("amount");
							idt6.setDataName(checkNull(amount[i]));
							insertData.add(idt6);	
							
							status = cbt.insertIntoTable("allocate_budget",insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
							insertData.remove(idt6);
						}
					}
					
					
					insertData.clear();
				
			
				

				if (status)
				{
					addActionMessage("Budget Allocated Added successfully!!!"+count);
					return SUCCESS;
				} else
				{
					addActionMessage("Oops There is some error in data!");
					return SUCCESS;
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}
	} else
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









	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}


	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}


	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}


	


	public List<ConfigurationUtilBean> getDataTextBox() {
		return dataTextBox;
	}


	public void setDataTextBox(List<ConfigurationUtilBean> dataTextBox) {
		this.dataTextBox = dataTextBox;
	}


	public List<ConfigurationUtilBean> getDataDropDown() {
		return dataDropDown;
	}


	public void setDataDropDown(List<ConfigurationUtilBean> dataDropDown) {
		this.dataDropDown = dataDropDown;
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


	public String getCount() {
		return count;
	}


	public void setCount(String count) {
		this.count = count;
	}


	public Map<String, String> getDepartmentData() {
		return departmentData;
	}


	public void setDepartmentData(Map<String, String> departmentData) {
		this.departmentData = departmentData;
	}


	public JSONArray getJsonArray() {
		return jsonArray;
	}


	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}


	public String getDeptid() {
		return deptid;
	}


	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}


	public Map<String, String> getFinancialYear() {
		return financialYear;
	}


	public void setFinancialYear(Map<String, String> financialYear) {
		this.financialYear = financialYear;
	}


	public List<GridDataPropertyView> getMasterViewProp2() {
		return masterViewProp2;
	}


	public void setMasterViewProp2(List<GridDataPropertyView> masterViewProp2) {
		this.masterViewProp2 = masterViewProp2;
	}


	public List<ConfigurationUtilBean> getDataTextBox1() {
		return dataTextBox1;
	}


	public void setDataTextBox1(List<ConfigurationUtilBean> dataTextBox1) {
		this.dataTextBox1 = dataTextBox1;
	}


	public List<ConfigurationUtilBean> getDataDropDown1() {
		return dataDropDown1;
	}


	public void setDataDropDown1(List<ConfigurationUtilBean> dataDropDown1) {
		this.dataDropDown1 = dataDropDown1;
	}


	public Map<String, String> getMonthList() {
		return monthList;
	}


	public void setMonthList(Map<String, String> monthList) {
		this.monthList = monthList;
	}


	public String getForMonth() {
		return forMonth;
	}


	public void setForMonth(String forMonth) {
		this.forMonth = forMonth;
	}


	public String getRemoveMonth() {
		return removeMonth;
	}


	public void setRemoveMonth(String removeMonth) {
		this.removeMonth = removeMonth;
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	
	
	
	
	
	
	

}

