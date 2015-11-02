package com.Over2Cloud.ctrl.dar.report;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.dar.task.TaskRegistrationHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReportActionDAR extends ActionSupport
{
	private static final long serialVersionUID = 4879544001959441915L;
	@SuppressWarnings("rawtypes")
	Map session=ActionContext.getContext().getSession();
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private String oper;
	private String fdate;
	private String tdate;
	private String allotTo;
	private String clientFor;
	private String taskTyp;
	private String taskPriority;
	private String statusWork;
	private String fromDate;
	private List<GridDataPropertyView> reportViewProp = null;
	private Map<Integer, String> allotedTo;
	private Map<Integer, String> taskType;
	private List<Object> viewList;
	
	@SuppressWarnings("rawtypes")
	public String beforeReportView() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				String userName=(String)session.get("uName");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				TaskRegistrationHelper TRH=new TaskRegistrationHelper();
				StringBuilder query=new StringBuilder();
				String userEmpID=null;
				String alltedTo=null;
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				userEmpID = empIdofuser.split("-")[1].trim();
				String userContID = null;
				userContID=TRH.getEmpDetailsByUserName("DAR",userName,connectionSpace)[0];
				alltedTo=TRH.getContactListForReports(userEmpID, userContID,connectionSpace);
				
				query.append("SELECT DISTINCT (contact.id),emp.empName FROM task_registration AS tr ");
				query.append("INNER JOIN compliance_contacts as contact ON contact.id=tr.allotedto ");
				query.append("INNER JOIN employee_basic as emp ON contact.emp_id=emp.id WHERE contact.id IN ("+alltedTo+")" );
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				allotedTo = new LinkedHashMap<Integer, String>();
				
				if (data!=null && data.size()>0) 
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1]!=null) 
						{
							allotedTo.put((Integer) object[0], object[1].toString());
						}
					}
				}
				allotedTo.put(-1, "All Employee");
				query.setLength(0);
				if (data!=null && data.size()>0) 
				{
					data.clear();
				}
				query.append("SELECT id,tasktype FROM task_type");
				taskType=new LinkedHashMap<Integer, String>();
				data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data!=null && data.size()>0) 
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if (object[1]!=null) 
						{
							taskType.put((Integer) object[0], object[1].toString());
						}
					}
				}
				fromDate=DateUtil.getNewDate("day", -7, DateUtil.getCurrentDateUSFormat());
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
			return ERROR;
		}
	}
	public String getColumn4DarReport()
	{
		boolean validateSession =ValidateSession.checkSession();
		if (validateSession) 
		{
			setReportViewProp();
			return SUCCESS;
		} 
		else 
		{
			return ERROR;
		}
	}
	public void setReportViewProp()
	{
		try
		{
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			reportViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv=new GridDataPropertyView();
			
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			reportViewProp.add(gpv);
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
			if(statusColName!=null&&statusColName.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")) 
					{
						gpv=new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						/*if(gdp.getColomnName().equalsIgnoreCase("attachment"))
						{
							gpv.setFormatter(gdp.getFormatter());
						}*/
						reportViewProp.add(gpv);
					}
					
				}
			}
			List<GridDataPropertyView> darSubmitColName=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_submission_configuration");
			if(darSubmitColName!=null&&darSubmitColName.size()>0)
			{
				for(GridDataPropertyView gdp:darSubmitColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("taskname") && !gdp.getColomnName().equalsIgnoreCase("userName") ) 
					{
						gpv=new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						/*if(gdp.getColomnName().equalsIgnoreCase("attachmentt"))
						{
							gpv.setFormatter(gdp.getFormatter());
						}*/
						reportViewProp.add(gpv);
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String  viewDARReportData() 
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String accountID=(String)session.get("accountid");
				String userName=(String)session.get("uName");
				DARReportHelper DRH=new DARReportHelper();
				TaskRegistrationHelper TRH=new TaskRegistrationHelper();
				StringBuilder query=new StringBuilder("");
				query.append("select count(*) from task_registration");
				List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(dataCount!=null)
				{
					BigInteger count=new BigInteger("3");
					Object obdata=null;
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 obdata=(Object)it.next();
						 count=(BigInteger)obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					String userEmpID=null;
					String alltedTo=null;
					String empIdofuser = (String) session.get("empIdofuser");
					if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
						return ERROR;
					userEmpID = empIdofuser.split("-")[1].trim();
					String userContID = null;
					userContID=TRH.getEmpDetailsByUserName("DAR",userName,connectionSpace)[0];
					alltedTo=TRH.getContactListForReports(userEmpID, userContID,connectionSpace);
					
					//System.out.println(">>>>>> FINAL CONTACT LIST IS AS ::::  "+alltedTo);
					query.setLength(0);
					query.append("SELECT  ");
					List fieldNames=Configuration.getColomnList("mapped_dar_configuration", accountID, connectionSpace, "dar_configuration");
					List<Object> Listhb=new ArrayList<Object>();
					int i=0;
					for(Iterator it=fieldNames.iterator(); it.hasNext();)
					{
					    obdata=(Object)it.next();
					    if(obdata!=null)
					    {
					    	if (!obdata.toString().equalsIgnoreCase("userName") && !obdata.toString().equalsIgnoreCase("date") && !obdata.toString().equalsIgnoreCase("time")) 
					    	{
					    		if (obdata.toString().equalsIgnoreCase("tasktype")) 
					    		{
					    			query.append("ty.tasktype"+",");
								}
					    		else if (obdata.toString().equalsIgnoreCase("allotedto")) 
					    		{
					    			query.append("emp1.empName as allotedto,");
								}
					    		else if (obdata.toString().equalsIgnoreCase("allotedby")) 
					    		{
					    			query.append("emp2.empName as allotedby,");
								}
					    		else if (obdata.toString().equalsIgnoreCase("validate_By_2")) 
					    		{
					    			query.append("emp3.empName as val2,");
								}
					    		else if (obdata.toString().equalsIgnoreCase("validate_By_1")) 
					    		{
					    			query.append("emp4.empName as val1,");
								}
					    		else if (obdata.toString().equalsIgnoreCase("guidance")) 
					    		{
					    			query.append("emp5.empName as guidance,");
								}
					    		else 
					    		{
					    			query.append("tr."+obdata.toString()+",");
								}
							}
					    }
					    i++;
					}
					fieldNames.remove("userName");
					fieldNames.remove("date");
					fieldNames.remove("time");
					
					i=0;
					List gridFields=new ArrayList();
					List fieldNames1=Configuration.getColomnList("mapped_dar_configuration", accountID, connectionSpace, "dar_submission_configuration");
					for(Iterator it=fieldNames1.iterator(); it.hasNext();)
					{
					    obdata=(Object)it.next();
					    if(obdata!=null)
					    {
					    	if (!obdata.toString().equalsIgnoreCase("taskname") && !obdata.toString().equalsIgnoreCase("id") && !obdata.toString().equalsIgnoreCase("userName") ) 
					    	{
					    		gridFields.add(obdata.toString());
					    		if(i<fieldNames1.size()-1)
					    			
							    	query.append("ds."+obdata.toString()+",");
							    else
							    	query.append("ds."+obdata.toString());
							}
					    }
					    i++;
					}
					query.append(" FROM task_registration AS tr ");
					query.append(" LEFT JOIN dar_submission AS ds ON ds.taskname=tr.id ");
					query.append("LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id ");
					query.append("LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id ");
					query.append("LEFT JOIN compliance_contacts AS cc3 ON tr.validate_By_2=cc3.id ");
					query.append("LEFT JOIN compliance_contacts AS cc4 ON tr.validate_By_1=cc4.id ");
					query.append("LEFT JOIN compliance_contacts AS cc5 ON tr.guidance=cc5.id  ");
					query.append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
					query.append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
					query.append("LEFT JOIN employee_basic emp3 ON cc3.emp_id= emp3.id ");
					query.append("LEFT JOIN employee_basic emp4 ON cc4.emp_id= emp4.id ");
					query.append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
					query.append("LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
					if (fdate.split("-")[0].length()<3) 
					{
						fdate=DateUtil.convertDateToUSFormat(fdate);
						tdate=DateUtil.convertDateToUSFormat(tdate);
					}
					query.append(" WHERE  ds.date BETWEEN '"+fdate+"' AND '"+tdate+"'  ");
					if (allotTo!=null && !allotTo.equalsIgnoreCase("-1")) 
					{
						query.append(" AND  tr.allotedTo='"+allotTo+"' ");
					}
					if (clientFor!=null && !clientFor.equalsIgnoreCase("-1")) 
					{
						query.append(" AND  tr.clientfor='"+clientFor+"' ");
					}
					if (taskTyp!=null && !taskTyp.equalsIgnoreCase("-1")) 
					{
						query.append(" AND  tr.tasktype='"+taskTyp+"' ");
					}
					if (taskPriority!=null && !taskPriority.equalsIgnoreCase("-1")) 
					{
						query.append(" AND  tr.priority='"+taskPriority+"' ");
					}
					if (statusWork!=null && !statusWork.equalsIgnoreCase("-1")) 
					{
						query.append(" AND  tr.status='"+statusWork+"' ");
					}
					if (alltedTo!=null && alltedTo.length()>0) 
					{
						query.append(" AND  tr.allotedTo IN ("+alltedTo+") ");
					}
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
						}
					}
					System.out.println("QUERY OF REPORT IS AS ::::  "+query.toString());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					for (int k = 0; k < gridFields.size(); k++)
					{
						fieldNames.add(gridFields.get(k).toString());
					}
					if(data!=null && data.size()>0)
					{
						
						String clientData=null;
						String clientVal=null;
						Object[] obdata11=null;
						for(Iterator it=data.iterator(); it.hasNext();)
						{
						    obdata11=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
							   if(fieldNames.get(k)!=null && obdata11[k]!=null && !obdata11[k].toString().equalsIgnoreCase(""))
								{
									if(obdata11[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata11[k].toString()));
									}
									/*else if(fieldNames.get(k).toString().equalsIgnoreCase("attachment")||  fieldNames.get(k).toString().equalsIgnoreCase("attachmentt")||fieldNames.get(k).toString().equalsIgnoreCase("reviewDoc")  )
									{
										String str=obdata11[k].toString().substring(obdata11[k].toString().indexOf("//"), obdata11[k].toString().length());
										String docName=str.substring(14,str.length());
										one.put(fieldNames.get(k).toString(),docName);
									}*/
									else if(fieldNames.get(k).toString().equalsIgnoreCase("clientfor"))
									{
										clientVal= obdata11[k].toString();
										if (obdata11[k].toString().equalsIgnoreCase("PA")) 
										{
											one.put(fieldNames.get(k).toString(),"Prospect Associate");
										}
										else if (obdata11[k].toString().equalsIgnoreCase("PC")) 
										{
											one.put(fieldNames.get(k).toString(),"Prospect Client");
										}
										else if(obdata11[k].toString().equalsIgnoreCase("N"))
										{
											one.put(fieldNames.get(k).toString(),"Other");
										}
										else if(obdata11[k].toString().equalsIgnoreCase("EC"))
										{
											one.put(fieldNames.get(k).toString(),"Existing Client");
										}
										else if(obdata11[k].toString().equalsIgnoreCase("EA"))
										{
											one.put(fieldNames.get(k).toString(),"Existing Associate");
										}
										else if(obdata11[k].toString().equalsIgnoreCase("IN"))
										{
											one.put(fieldNames.get(k).toString(),"Internal");
										}
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("cName"))
									{
										clientData=obdata11[k].toString();
										one.put(fieldNames.get(k).toString(), DRH.clientName(clientVal, obdata11[k].toString(),connectionSpace));
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("offering"))
									{
										one.put(fieldNames.get(k).toString(), DRH.offeringName(clientVal, clientData,obdata11[k].toString(),connectionSpace));
									}
									else
									{
										one.put(fieldNames.get(k).toString(),obdata11[k].toString());
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
					}
				}
			  return SUCCESS;
			}
			catch (Exception exp) 
			{
				exp.printStackTrace();
				return ERROR;
			}
	   } 
		else 
		{
			return ERROR;
		}
	}
	public List<GridDataPropertyView> getReportViewProp() {
		return reportViewProp;
	}
	public void setReportViewProp(List<GridDataPropertyView> reportViewProp) {
		this.reportViewProp = reportViewProp;
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
	public Map<Integer, String> getAllotedTo() {
		return allotedTo;
	}
	public void setAllotedTo(Map<Integer, String> allotedTo) {
		this.allotedTo = allotedTo;
	}
	public Map<Integer, String> getTaskType() {
		return taskType;
	}
	public void setTaskType(Map<Integer, String> taskType) {
		this.taskType = taskType;
	}
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getAllotTo() {
		return allotTo;
	}
	public void setAllotTo(String allotTo) {
		this.allotTo = allotTo;
	}
	public String getClientFor() {
		return clientFor;
	}
	public void setClientFor(String clientFor) {
		this.clientFor = clientFor;
	}
	
	public String getTaskTyp() {
		return taskTyp;
	}
	public void setTaskTyp(String taskTyp) {
		this.taskTyp = taskTyp;
	}
	public String getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}
	public String getStatusWork() {
		return statusWork;
	}
	public void setStatusWork(String statusWork) {
		this.statusWork = statusWork;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
}
