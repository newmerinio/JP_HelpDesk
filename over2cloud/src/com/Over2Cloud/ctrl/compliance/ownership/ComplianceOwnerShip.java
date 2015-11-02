package com.Over2Cloud.ctrl.compliance.ownership;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;

public class ComplianceOwnerShip extends GridPropertyBean implements ServletRequestAware
{
	HttpServletRequest request;
	private Map<String, String> dataMap;
	private Map<String, String> deptMap;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String deptId;
	private List<Object> masterViewList;
	private List<Object> masterViewList0;
	
	public String beforeOwnershipConfig()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				dataMap = new LinkedHashMap<String, String>();
				deptMap = new LinkedHashMap<String, String>();
				StringBuilder query = new StringBuilder();
				List dataList = getTaskMappedDept();
				query.setLength(0);
				if(dataList!=null && dataList.size()>0)
				{
					dataMap.put("All", "All Department");
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							dataMap.put(object[0].toString(), object[1].toString());
						}
						
					}
					dataList.clear();
				}
				query.append("SELECT DISTINCT dept.id,dept.deptName FROM department AS dept");
				query.append(" INNER JOIN employee_basic AS emp ON emp.deptname = dept.id"); 
				query.append(" INNER JOIN groupinfo AS grp ON grp.id = emp.groupid");
				query.append(" WHERE emp.flag='0' AND dept.flag='Active' AND grp.status='Active' AND grp.groupName='Employee'");
				query.append(" ORDER BY dept.deptName");
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				query.setLength(0);
				if(dataList!=null && dataList.size()>0)
				{
					deptMap.put("All", "All Department");
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							deptMap.put(object[0].toString(), object[1].toString());
						}
						
					}
					dataList.clear();
				}
				return SUCCESS;
			}
			catch(Exception e)
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
	
	public List getTaskMappedDept()
	{
		StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT dept.id,dept.deptName FROM department AS dept");
		query.append(" INNER JOIN compliance_task AS task ON task.departName = dept.id");
		query.append(" WHERE task.status='Active' AND dept.flag='Active' AND task.application='COMPL'");
		query.append(" ORDER BY dept.deptName");
		List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return dataList;
	}
	
	
	public String getEmployee()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				dataMap = new LinkedHashMap<String, String>();
				System.out.println("deptId  "+deptId);
				if(deptId!=null && !deptId.equals(""))
				{
					StringBuilder query = new StringBuilder();
					if(deptId.contains("All"))
					{
						query.append("SELECT DISTINCT emp.id,emp.empName FROM employee_basic AS emp");
						query.append(" INNER JOIN groupinfo AS grp ON grp.id = emp.groupId");
						query.append(" WHERE emp.flag=0 ORDER BY emp.empName");
					}
					else
					{
						query.append("SELECT DISTINCT emp.id,emp.empName FROM employee_basic AS emp");
						query.append(" INNER JOIN groupinfo AS grp ON grp.id = emp.groupId");
						query.append(" WHERE emp.flag=0 AND emp.deptname IN("+deptId+") ORDER BY emp.empName");
					}
					if(query!=null && query.length()>0)
					{
						List dataList  = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if(object[0]!=null && object[1]!=null) 
								{
									dataMap.put(object[0].toString(), object[1].toString());
								}
							}
						}
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
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
	
	public String ownerShipAction()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				
				
				ob1 = new TableColumes();
				ob1.setColumnname("for_dept");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("employee");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date_time");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("compliance_ownership", Tablecolumesaaa, connectionSpace);
				
				
				
				String[] deptID = request.getParameterValues("forDept");
				if(deptID!=null && deptID.length>0)
				{
					for (int i = 0; i < deptID.length; i++)
					{
						if(deptID[i].equalsIgnoreCase("All"))
						{
							List departmentlist = getTaskMappedDept();
							if (departmentlist != null && departmentlist.size() > 0)
							{
								for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
								{
									Object[] object1 = (Object[]) iterator.next();
									if(object1[0]!=null)
									{
										String[] empId = request.getParameterValues("empName");
										if(empId!=null && empId.length>0)
										{
											for (int j = 0; j < empId.length; j++)
											{
												ob = new InsertDataTable();
												ob.setColName("userName");
												ob.setDataName(userName);
												insertData.add(ob);

												ob = new InsertDataTable();
												ob.setColName("date_time");
												ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
												insertData.add(ob);
												
												ob = new InsertDataTable();
												ob.setColName("for_dept");
												ob.setDataName(object1[0].toString());
												insertData.add(ob);
												
												ob = new InsertDataTable();
												ob.setColName("employee");
												ob.setDataName(empId[j].toString());
												insertData.add(ob);
												
												cbt.executeAllUpdateQuery("DELETE FROM compliance_ownership WHERE for_dept="+object1[0].toString()+" AND employee="+empId[j].toString(), connectionSpace);
												cbt.insertIntoTable("compliance_ownership", insertData, connectionSpace);
												
												insertData.clear();
											}
										}
									}
								}
							}
							break;
						}
						else
						{

							String[] empId = request.getParameterValues("empName");
							if(empId!=null && empId.length>0)
							{
								for (int j = 0; j < empId.length; j++)
								{
									ob = new InsertDataTable();
									ob.setColName("userName");
									ob.setDataName(userName);
									insertData.add(ob);

									ob = new InsertDataTable();
									ob.setColName("date_time");
									ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("for_dept");
									ob.setDataName(deptID[i].toString());
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("employee");
									ob.setDataName(empId[j].toString());
									insertData.add(ob);
									
									cbt.executeAllUpdateQuery("DELETE FROM compliance_ownership WHERE for_dept="+deptID[i].toString()+" AND employee="+empId[j].toString(), connectionSpace);
									cbt.insertIntoTable("compliance_ownership", insertData, connectionSpace);
									
									insertData.clear();
								}
							}
						}
					}
				}
				addActionMessage("Data saved sucessfully.");
				int empID =Integer.parseInt((String) session.get("empIdOfuser").toString().split("-")[1]);
				int maxId=cbt.getMaxId("compliance_ownership", connectionSpace);
				new UserHistoryAction().userHistoryAdd(String.valueOf(empID), "Add", "COMPL", "Ownership", "Ownership Added", "Ownership Added", maxId, connectionSpace);
				return SUCCESS;
			}
			catch(Exception e)
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
	
	public String alertMailOwnerOnLeave()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				
				
				ob1 = new TableColumes();
				ob1.setColumnname("for_dept");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("ownership_level");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("userName");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date_time");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("compliance_ownership_level", Tablecolumesaaa, connectionSpace);
				
				
				
				String[] deptID = request.getParameterValues("forDept");
				if(deptID!=null && deptID.length>0)
				{
					for (int i = 0; i < deptID.length; i++)
					{
						if(deptID[i].equalsIgnoreCase("All"))
						{
							List departmentlist = getTaskMappedDept();
							if (departmentlist != null && departmentlist.size() > 0)
							{
								for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
								{
									Object[] object1 = (Object[]) iterator.next();
									if(object1[0]!=null)
									{
										ob = new InsertDataTable();
										ob.setColName("userName");
										ob.setDataName(userName);
										insertData.add(ob);

										ob = new InsertDataTable();
										ob.setColName("date_time");
										ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
										insertData.add(ob);
										
										ob = new InsertDataTable();
										ob.setColName("for_dept");
										ob.setDataName(object1[0].toString());
										insertData.add(ob);
										
										ob = new InsertDataTable();
										ob.setColName("ownership_level");
										ob.setDataName(request.getParameter("toLevel"));
										insertData.add(ob);
										
										cbt.executeAllUpdateQuery("DELETE FROM compliance_ownership_level WHERE for_dept="+object1[0].toString()+" AND ownership_level="+request.getParameter("toLevel"), connectionSpace);
										cbt.insertIntoTable("compliance_ownership_level", insertData, connectionSpace);
										
										insertData.clear();
									}
								}
							}
							break;
						}
						else
						{
							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(userName);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("date_time");
							ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("for_dept");
							ob.setDataName(deptID[i].toString());
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("ownership_level");
							ob.setDataName(request.getParameter("toLevel"));
							insertData.add(ob);
							
							cbt.executeAllUpdateQuery("DELETE FROM compliance_ownership_level WHERE for_dept="+deptID[i].toString()+" AND ownership_level="+request.getParameter("toLevel"), connectionSpace);
							cbt.insertIntoTable("compliance_ownership_level", insertData, connectionSpace);
							
							insertData.clear();
						}
					}
				}
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			}
			catch(Exception e)
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
	
	public String gridMappedLevel()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT col.id,dept.deptName,col.ownership_level FROM compliance_ownership_level AS col INNER JOIN department AS dept ON dept.id = col.for_dept ORDER BY dept.deptName");
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object = null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("department",object[1].toString());
						else 
							one.put("department","NA");
						
						if(object[2]!=null)
						{
							String level = "NA";
							if(object[2].toString().equalsIgnoreCase("L2"))
								level = "L2 Only";
							else if(object[2].toString().equalsIgnoreCase("L2OW"))
								level = "L2 & Owner";
							else if(object[2].toString().equalsIgnoreCase("OW"))
								level = "Owner Only";
							
							one.put("level",level);
						}
						else 
							one.put("level","NA");
						
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public String gridMappedEmpl()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag) 
		{
			try 
			{
				masterViewList0 = new ArrayList<Object>();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT col.id,dept.deptName,emp.empName FROM compliance_ownership AS co");
				query.append(" INNER JOIN compliance_ownership_level  AS col ON co.for_dept = col.for_dept");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = co.employee");
				query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname WHERE col.id="+id+" ORDER BY emp.empName ASC");
				System.out.println(query.toString());
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object = null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("fromdept",object[1].toString());
						else 
							one.put("fromdept","NA");
						
						if(object[1]!=null)
							one.put("emp",object[2].toString());
						else 
							one.put("emp","NA");
						
						Listhb.add(one);
					}
					setMasterViewList0(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult=SUCCESS;
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) 
	{
		this.request = request;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public Map<String, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<String, String> deptMap) {
		this.deptMap = deptMap;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public List<Object> getMasterViewList0() {
		return masterViewList0;
	}

	public void setMasterViewList0(List<Object> masterViewList0) {
		this.masterViewList0 = masterViewList0;
	}
	
}