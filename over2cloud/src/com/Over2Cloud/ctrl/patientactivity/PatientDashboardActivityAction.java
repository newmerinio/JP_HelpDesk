package com.Over2Cloud.ctrl.patientactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PatientDashboardActivityAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	private SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");	
	HttpServletRequest request;
	private Map<String, String> employee_basicMap;
	private String acManager;
	private JSONArray arr = new JSONArray();
	private String fromDate;
	private String toDate,filterEmpId,filterBloodId,filterAgeId;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	PatientDashboardPojo dashObj;
	private List<PatientDashboardPojo> mgr_counterList;
	private List<PatientDashboardPojo> blood_counterList;
	private List<PatientDashboardPojo> age_counterList;
	private String empId,maximizeDivBlock;
	private String empName;
	private Map<String, String> totalComplStatusMap=null;
	
public String beforePaientReportView()
{
	try {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		employee_basicMap = new LinkedHashMap<String, String>();
		List relManagerData = cbt.executeAllSelectQuery("select Distinct rmd.relationship_mgr,eb.empName from patient_visit_action as rmd  " +
				" inner join compliance_contacts as cc on cc.id=rmd.relationship_mgr" +
				" inner join employee_basic as eb on  eb.id=cc.emp_id ", connectionSpace);
		if (relManagerData != null)
		{
		for (Object c : relManagerData)
		{
		Object[] dataC = (Object[]) c;
		employee_basicMap.put(dataC[0].toString(), dataC[1].toString());
		}
		}

		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return SUCCESS;
}

//For Bar Graph
public String getBarChart4DeptCounters()
{
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
		try
		{
			//this.generalMethod();
			if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
				fromDate=DateUtil.getCurrentDateIndianFormat();
				toDate=DateUtil.getCurrentDateIndianFormat();
			}
			getCounterData(fromDate,toDate);
			/*if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
				getServiceDept();
			}else{
				
			}*/
			jsonObject = new JSONObject();
			jsonArray = new JSONArray();
			
			for (PatientDashboardPojo pojo : mgr_counterList)
			 {
				jsonObject.put("id",pojo.getId());
				jsonObject.put("emp",pojo.getMgr());
				jsonObject.put("Missed",Integer.parseInt(pojo.getMissed()));
				jsonObject.put("Pending", Integer.parseInt(pojo.getPending()));
				jsonObject.put("Done", Integer.parseInt(pojo.getDone()));
				jsonArray.add(jsonObject);
			 }
			return SUCCESS;
		}
		catch (Exception e) {
			return ERROR;
		}
	}
	else
	{
		return LOGIN;
	}
}

//For Bar Graph
public String getBarChart4AgeCounters()
{
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
		try
		{
			//this.generalMethod();
			if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
				fromDate=DateUtil.getCurrentDateIndianFormat();
				toDate=DateUtil.getCurrentDateIndianFormat();
			}
			getCounterAgeData(fromDate,toDate);
			/*if(filterFlag!=null && filterFlag.equalsIgnoreCase("H")){
				getServiceDept();
			}else{
				
			}*/
			jsonObject = new JSONObject();
			jsonArray = new JSONArray();
			
			for (PatientDashboardPojo pojo : age_counterList)
			 {
				jsonObject.put("Age",pojo.getAge());
				jsonObject.put("A20-40",Integer.parseInt(getValueWithNullCheck(pojo.getA2t4())));
				jsonObject.put("A40-60", Integer.parseInt(getValueWithNullCheck(pojo.getA4t6())));
				jsonObject.put("A60-80", Integer.parseInt(getValueWithNullCheck(pojo.getA6t8())));
				jsonObject.put("A80-100", Integer.parseInt(getValueWithNullCheck(pojo.getA8t10())));
				jsonObject.put("A100-120", Integer.parseInt(getValueWithNullCheck(pojo.getA10t12())));
				jsonArray.add(jsonObject);
			 }
			return SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	else
	{
		return LOGIN;
	}
}


//For Bar Graph
public String getBarChart4BloodGroupCounters()
{
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
		try
		{
			//this.generalMethod();
			if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase("")){
				fromDate=DateUtil.getCurrentDateIndianFormat();
				toDate=DateUtil.getCurrentDateIndianFormat();
			}
			getBloodGroupCounterData(fromDate,toDate);
			
			jsonObject = new JSONObject();
			jsonArray = new JSONArray();
			
			for (PatientDashboardPojo pojo : blood_counterList)
			 {
				jsonObject.put("Group",pojo.getGroup());
				jsonObject.put("A Positive",Integer.parseInt(getValueWithNullCheck(pojo.getAp())));
				jsonObject.put("B Positive", Integer.parseInt(getValueWithNullCheck(pojo.getBp())));
				jsonObject.put("AB Positive", Integer.parseInt(getValueWithNullCheck(pojo.getAbp())));
				jsonObject.put("O Positive", Integer.parseInt(getValueWithNullCheck(pojo.getOp())));
				jsonObject.put("A Negative", Integer.parseInt(getValueWithNullCheck(pojo.getAn())));
				jsonObject.put("B Negative", Integer.parseInt(getValueWithNullCheck(pojo.getBn())));
				jsonObject.put("AB Negative", Integer.parseInt(getValueWithNullCheck(pojo.getAbn())));
				jsonObject.put("O Negative", Integer.parseInt(getValueWithNullCheck(pojo.getOn())));
				jsonArray.add(jsonObject);
			 }
			return SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	else
	{
		return LOGIN;
	}
}

public String getValueWithNullCheck(Object value)
{
	return (value==null || value.toString().equals(""))?"0" : value.toString();
}

//For Table Data
public String getTableDataCounters()
{
	boolean validSession=ValidateSession.checkSession();
	if(validSession)
	{
		try
		{
			if(fromDate==null || fromDate.equalsIgnoreCase("") && toDate==null || toDate.equalsIgnoreCase(""))
			{
				fromDate=DateUtil.getCurrentDateIndianFormat();
				toDate=DateUtil.getCurrentDateIndianFormat();
			}
			getCounterData(fromDate,toDate);
			jsonArray = new JSONArray();
			totalComplStatusMap=new HashMap<String,String>();
			for (PatientDashboardPojo pojo : mgr_counterList)
			 {
				totalComplStatusMap.put("id",pojo.getId());
				totalComplStatusMap.put("emp",pojo.getMgr());
				totalComplStatusMap.put("Missed",pojo.getMissed());
				totalComplStatusMap.put("Pending",pojo.getPending());
				totalComplStatusMap.put("Done", pojo.getDone());
				jsonArray.add(totalComplStatusMap);
			 }
			return SUCCESS;
		}
		catch (Exception e) {
			return ERROR;
		}
	}
	else
	{
		return LOGIN;
	}
}



//Ticket Counters on the basis of status At HOD Level

@SuppressWarnings("rawtypes")
public void getCounterData(String fromDate, String toDate)
 {
	dashObj=new PatientDashboardPojo();
	PatientDashboardPojo DP=null;
	try {
		mgr_counterList  = new ArrayList<PatientDashboardPojo>();
		List dept_subdeptNameList = null;
		if(this.getFilterEmpId() != null && !this.getFilterEmpId().equals("-1") && !this.getFilterEmpId().equalsIgnoreCase("undefined"))
		{
			dept_subdeptNameList= new PatientDashboardHelper().getEmpData(getFilterEmpId(),connectionSpace);
		}
		else{
		     dept_subdeptNameList= new PatientDashboardHelper().getEmpData("",connectionSpace);
		}
//	System.out.println(dept_subdeptNameList.size());	
		     
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
		for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
			Object[] object1 = (Object[]) iterator.next();
			DP=new PatientDashboardPojo();
			DP.setId(object1[0].toString());
			DP.setMgr(object1[1].toString());
			// List for counters for Pending/ Snooze/ High Priority/ Ignore Status
			List dept_subdeptCounterList = new ArrayList();
			List dept_subdeptCounterList1 = new ArrayList();
			List dept_subdeptCounterList2 = new ArrayList();
			dept_subdeptCounterList = new PatientDashboardHelper().getDashboardCounter0("0",object1[0].toString(),connectionSpace,DateUtil.convertDateToUSFormat(fromDate),DateUtil.convertDateToUSFormat(toDate));
			dept_subdeptCounterList1 = new PatientDashboardHelper().getDashboardCounter0("1",object1[0].toString(),connectionSpace,DateUtil.convertDateToUSFormat(fromDate),DateUtil.convertDateToUSFormat(toDate));
			dept_subdeptCounterList2 = new PatientDashboardHelper().getDashboardCounter0("2",object1[0].toString(),connectionSpace,DateUtil.convertDateToUSFormat(fromDate),DateUtil.convertDateToUSFormat(toDate));
			
			if (dept_subdeptCounterList.get(0)!=null ) {
	//			System.out.println(dept_subdeptCounterList.get(0).toString());
				DP.setPending(dept_subdeptCounterList.get(0).toString());
		     }
			if (dept_subdeptCounterList1.get(0)!=null) {
		//		System.out.println(dept_subdeptCounterList1.get(0).toString());
				DP.setMissed(dept_subdeptCounterList1.get(0).toString());
		     }
			if (dept_subdeptCounterList2.get(0)!=null) {
			//	System.out.println(dept_subdeptCounterList2.get(0).toString());
				DP.setDone(dept_subdeptCounterList2.get(0).toString());
		     }
			mgr_counterList.add(DP);
			dashObj.setDashList(mgr_counterList);
	     }
	  }} catch (Exception e) {
		e.printStackTrace();
	}
}


@SuppressWarnings({ "rawtypes", "unchecked" })
public void getBloodGroupCounterData(String fromDate, String toDate)
 {
	dashObj=new PatientDashboardPojo();
	PatientDashboardPojo DP=null;
	try {
		blood_counterList  = new ArrayList<PatientDashboardPojo>();
		List dept_subdeptNameList = null;
		if(this.getFilterBloodId() != null && !this.getFilterBloodId().equals("-1") && !this.getFilterBloodId().equalsIgnoreCase("undefined"))
		{
			dept_subdeptNameList= new ArrayList();
			dept_subdeptNameList.add(this.getFilterBloodId());
		}
		else{
		     dept_subdeptNameList=new ArrayList();
		     dept_subdeptNameList.add("A Positive");
		     dept_subdeptNameList.add("O Positive");
		     dept_subdeptNameList.add("B Positive");
		     dept_subdeptNameList.add("AB Positive");
		     dept_subdeptNameList.add("A Negative");
		     dept_subdeptNameList.add("O Negative");
		     dept_subdeptNameList.add("B Negative");
		     dept_subdeptNameList.add("AB Negative");
		}
	
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
		for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
			Object object1 = (Object) iterator.next();
			DP=new PatientDashboardPojo();
			DP.setGroup(object1.toString());
			// List for counters for Pending/ Snooze/ High Priority/ Ignore Status
			List dept_subdeptCounterList = new ArrayList();
			dept_subdeptCounterList = new PatientDashboardHelper().getDashboardBloodCounter(object1.toString(),connectionSpace,DateUtil.convertDateToUSFormat(fromDate),DateUtil.convertDateToUSFormat(toDate));
		
			if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
				for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
					Object[] object2 = (Object[]) iterator2.next();
					if(object2[0]!=null){
					if(object2[0].toString().equalsIgnoreCase("A Positive"))
					{
					 DP.setAp(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("B Positive"))
					{
					DP.setBp(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("AB Positive"))
					{
					DP.setAbp(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("O Positive"))
					{
					DP.setOp(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("A Negative"))
					{
					DP.setAn(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("B Negative"))
					{
					DP.setBn(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("AB Negative"))
					{
					DP.setAbn(object2[1].toString());
					}
					else if(object2[0].toString().equalsIgnoreCase("O Negative"))
					 {
					  DP.setOn(object2[1].toString());
					 }
				   }
				}
		     }
			blood_counterList.add(DP);
			dashObj.setDashList(blood_counterList);
	     }
	  }} catch (Exception e) {
		e.printStackTrace();
	}
}


@SuppressWarnings({ "rawtypes", "unchecked" })
public void getCounterAgeData(String fromDate, String toDate)
 {
	dashObj=new PatientDashboardPojo();
	PatientDashboardPojo DP=null;
	try {
		age_counterList  = new ArrayList<PatientDashboardPojo>();
		List dept_subdeptNameList = null;
		if(this.getFilterAgeId() != null && !this.getFilterAgeId().equals("-1") && !this.getFilterAgeId().equalsIgnoreCase("undefined"))
		{
			dept_subdeptNameList= new ArrayList();
			dept_subdeptNameList.add(this.getFilterAgeId());
		}
		else{
		     dept_subdeptNameList=new ArrayList();
		     dept_subdeptNameList.add("A20-40");
		     dept_subdeptNameList.add("A40-60");
		     dept_subdeptNameList.add("A60-80");
		     dept_subdeptNameList.add("A80-100");
		     dept_subdeptNameList.add("A100-120");
		}
	
	if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
		for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
			Object object1 = (Object) iterator.next();
			DP=new PatientDashboardPojo();
			DP.setAge(object1.toString());
			// List for counters for Pending/ Snooze/ High Priority/ Ignore Status
			List dept_subdeptCounterList = new ArrayList();
			String arr[]=object1.toString().substring(1).split("-");
			dept_subdeptCounterList = new PatientDashboardHelper().getAgeCounter(arr[0],arr[1],connectionSpace,DateUtil.convertDateToUSFormat(fromDate),DateUtil.convertDateToUSFormat(toDate));
			
			 if (dept_subdeptCounterList.get(0)!=null)
			 {
				if(object1.toString().equalsIgnoreCase("A20-40"))
				{
					DP.setA2t4(dept_subdeptCounterList.get(0).toString());
				}
				else if(object1.toString().equalsIgnoreCase("A40-60"))
				{
					DP.setA4t6(dept_subdeptCounterList.get(0).toString());
				}
				else if(object1.toString().equalsIgnoreCase("A60-80"))
				{
					DP.setA6t8(dept_subdeptCounterList.get(0).toString());
				}
				else if(object1.toString().equalsIgnoreCase("A80-100"))
				{
					DP.setA8t10(dept_subdeptCounterList.get(0).toString());
				}
				else if(object1.toString().equalsIgnoreCase("A100-120"))
				{
					DP.setA10t12(dept_subdeptCounterList.get(0).toString());
				}
		     }
			 age_counterList.add(DP);
			dashObj.setDashList(age_counterList);
	     }
	  }} catch (Exception e) {
		e.printStackTrace();
	}
}



	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}


	public void setEmployee_basicMap(Map<String, String> employee_basicMap) {
		this.employee_basicMap = employee_basicMap;
	}


	public Map<String, String> getEmployee_basicMap() {
		return employee_basicMap;
	}


	public void setAcManager(String acManager) {
		this.acManager = acManager;
	}


	public String getAcManager() {
		return acManager;
	}


	public void setArr(JSONArray arr) {
		this.arr = arr;
	}


	public JSONArray getArr() {
		return arr;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setDashObj(PatientDashboardPojo dashObj) {
		this.dashObj = dashObj;
	}

	public PatientDashboardPojo getDashObj() {
		return dashObj;
	}

	public void setMgr_counterList(List<PatientDashboardPojo> mgr_counterList) {
		this.mgr_counterList = mgr_counterList;
	}

	public List<PatientDashboardPojo> getMgr_counterList() {
		return mgr_counterList;
	}




	public void setEmpId(String empId) {
		this.empId = empId;
	}




	public String getEmpId() {
		return empId;
	}




	public void setEmpName(String empName) {
		this.empName = empName;
	}




	public String getEmpName() {
		return empName;
	}




	public void setTotalComplStatusMap(Map<String, String> totalComplStatusMap) {
		this.totalComplStatusMap = totalComplStatusMap;
	}




	public Map<String, String> getTotalComplStatusMap() {
		return totalComplStatusMap;
	}




	public void setMaximizeDivBlock(String maximizeDivBlock) {
		this.maximizeDivBlock = maximizeDivBlock;
	}




	public String getMaximizeDivBlock() {
		return maximizeDivBlock;
	}




	public void setFilterEmpId(String filterEmpId) {
		this.filterEmpId = filterEmpId;
	}

	public String getFilterEmpId() {
		return filterEmpId;
	}

	public void setFilterBloodId(String filterBloodId) {
		this.filterBloodId = filterBloodId;
	}

	public String getFilterBloodId() {
		return filterBloodId;
	}

	public void setBlood_counterList(List<PatientDashboardPojo> blood_counterList) {
		this.blood_counterList = blood_counterList;
	}




	public List<PatientDashboardPojo> getBlood_counterList() {
		return blood_counterList;
	}




	public void setFilterAgeId(String filterAgeId) {
		this.filterAgeId = filterAgeId;
	}




	public String getFilterAgeId() {
		return filterAgeId;
	}




	public void setAge_counterList(List<PatientDashboardPojo> age_counterList) {
		this.age_counterList = age_counterList;
	}




	public List<PatientDashboardPojo> getAge_counterList() {
		return age_counterList;
	}

}