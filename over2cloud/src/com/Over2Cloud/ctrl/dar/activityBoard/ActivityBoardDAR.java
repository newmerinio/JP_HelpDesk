package com.Over2Cloud.ctrl.dar.activityBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.dar.task.TaskRegistrationHelper;
import com.aspose.slides.p883e881b.all;

@SuppressWarnings("serial")
public class ActivityBoardDAR extends GridPropertyBean 
{
	String fromdate;
	String todate;
	private String taskType;
	private String taskPriority;
	private String allot;
	private Map<Integer, String> listtask;
	private List<Object> masterViewList;
	private String search;
	private Map<String, String> registerBY;
	private Map<String, String> allotmentList;
	private Map<String, String> searchByname;
	private String taskStatus;
	
	@SuppressWarnings("rawtypes")
	public String beforeMainPage() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				fromdate=DateUtil.getNextDateAfter(-3);
				todate=DateUtil.getNextDateAfter(3);
				listtask=new LinkedHashMap<Integer, String>();
				registerBY=new LinkedHashMap<String, String>();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String query1 = "SELECT id,tasktype FROM task_type ORDER BY tasktype";
				List task=cbt.executeAllSelectQuery(query1, connectionSpace);
				
				if (task!=null && task.size()>0) 
				{
					Object[] object=null;
					for (Iterator iterator = task.iterator(); iterator.hasNext();) 
					{
						object = (Object[]) iterator.next();
						listtask.put(Integer.parseInt(object[0].toString()), object[1].toString());
					}
					task.clear();
				}
				query1="SELECT userName FROM task_registration GROUP BY userName ORDER BY userName";
				task=cbt.executeAllSelectQuery(query1, connectionSpace);
				if (task!=null && task.size()>0)
				{
					Object object=null;
					for (Iterator iterator = task.iterator(); iterator.hasNext();) 
					{
						object = (Object) iterator.next();
						registerBY.put(object.toString(), object.toString());
					}
				}
				allotmentList=new LinkedHashMap<String, String>();
				allotmentList.put("allotBy", "Allotment By");
				allotmentList.put("allotTo", "Alloted To");
				allotmentList.put("coowner", "Co-Owner");
				allotmentList.put("techRev", "Technical Review By");
				allotmentList.put("funcRev", "Functional Review By");
				
				searchByname=new LinkedHashMap<String, String>();
				
				TaskRegistrationHelper TRH=new TaskRegistrationHelper();
				List allotedTo=TRH.getAllotedToName("DAR", userName,connectionSpace);
				if (allotedTo!=null && allotedTo.size()>0) 
				{
					Object[] object=null;
					for (Iterator iterator = allotedTo.iterator(); iterator.hasNext();) 
					 {
						object = (Object[]) iterator.next();
						searchByname.put(object[0].toString(), object[1].toString());
					}
				}
				
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
			return LOGIN;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String activityData() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				viewPageColumns=new ArrayList<GridDataPropertyView>();
				
				GridDataPropertyView gpv=new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("task.taskname");
				gpv.setHeadingName("Project Name");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(80);
				viewPageColumns.add(gpv);
				
				if (allot!=null && allot.equalsIgnoreCase("Unalloted"))
				{
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.attachment");
					gpv.setHeadingName("Project Work flow");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(50);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.specifictask");
					gpv.setHeadingName("Specific Task");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("tt.tasktype");
					gpv.setHeadingName("Task Type");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.priority");
					gpv.setHeadingName("Task Priority");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(80);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.clientfor");
					gpv.setHeadingName("For");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(100);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.cName");
					gpv.setHeadingName("Name");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.offering");
					gpv.setHeadingName("Offering");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.userName");
					gpv.setHeadingName("Register By");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(80);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.date");
					gpv.setHeadingName("Register On");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(90);
					viewPageColumns.add(gpv);
					
					session.put("masterViewProp", viewPageColumns);
				} 
				else
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.status");
					gpv.setHeadingName("Current Status");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(50);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("max(dr.compercentage)");
					gpv.setHeadingName("% Achieved");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(100);
					//viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.specifictask");
					gpv.setHeadingName("Specific Task");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(80);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.priority");
					gpv.setHeadingName("Task Priority");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(50);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.allotedto");
					gpv.setHeadingName("Alloted To");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.guidance");
					gpv.setHeadingName("Co Owner");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(150);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.allotedby");
					gpv.setHeadingName("Alloted By");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.intiation");
					gpv.setHeadingName("Initiation Date");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(70);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.completion");
					gpv.setHeadingName("Completion Date");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(70);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.validate_By_2");
					gpv.setHeadingName("Technical Review By");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.technical_Date");
					gpv.setHeadingName("Technical Review Date");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.validate_By_1");
					gpv.setHeadingName("Functional Review By");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.functional_Date");
					gpv.setHeadingName("Functional Review Date");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					
					gpv=new GridDataPropertyView();
					gpv.setColomnName("task.date");
					gpv.setHeadingName("Alloted On");
					gpv.setEditable("false");
					gpv.setSearch("true");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					viewPageColumns.add(gpv);
					
					setViewPageColumns(viewPageColumns);
					session.put("masterViewProp", viewPageColumns);
				}
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
			return LOGIN;
		}
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String activityBoardGridView() 
	{
		String retunString=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List  data=null;
				List<Object> Listhb=new ArrayList<Object>();
				
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from task_registration");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if(dataCount!=null && dataCount.size()>0)
				{
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if(i<(fieldNames.size()-1))
							{
								if(gpv.getColomnName()!=null)
								{
									if(gpv.getColomnName().equalsIgnoreCase("id"))
									{
										query.append("task.id,");
									}
									else if (gpv.getColomnName().equalsIgnoreCase("task.allotedto")) 
						    		{
						    			query.append("emp1.empName as allotedto,");
									}
						    		else if (gpv.getColomnName().equalsIgnoreCase("task.allotedby")) 
						    		{
						    			query.append("emp2.empName as allotedby,");
									}
						    		else if (gpv.getColomnName().equalsIgnoreCase("task.validate_By_2")) 
						    		{
						    			query.append("emp3.empName as val2,");
									}
						    		else if (gpv.getColomnName().equalsIgnoreCase("task.validate_By_1")) 
						    		{
						    			query.append("emp4.empName as val1,");
									}
						    		else if (gpv.getColomnName().equalsIgnoreCase("task.guidance")) 
						    		{
						    			query.append("emp5.empName as guidance,");
									}
									else
									{
										query.append(gpv.getColomnName().toString()+",");
									}
								}
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" FROM task_registration AS task " +
								"INNER JOIN task_type AS tt ON tt.id=task.tasktype   ");
						
						if (allot!=null && allot.equalsIgnoreCase("Alloted"))
						{
							query .append("LEFT JOIN compliance_contacts AS cc1 ON task.allotedto=cc1.id ");
							query .append("LEFT JOIN compliance_contacts AS cc2 ON task.allotedby=cc2.id ");
							query .append("LEFT JOIN compliance_contacts AS cc3 ON task.validate_By_2=cc3.id ");
							query .append("LEFT JOIN compliance_contacts AS cc4 ON task.validate_By_1=cc4.id ");
							query .append("LEFT JOIN compliance_contacts AS cc5 ON task.guidance=cc5.id  ");
							query .append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
							query .append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
							query .append("LEFT JOIN employee_basic emp3 ON cc3.emp_id= emp3.id ");
							query .append("LEFT JOIN employee_basic emp4 ON cc4.emp_id= emp4.id ");
							query .append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
							//query .append("LEFT JOIN dar_submission dr ON dr.id= task.taskname ");
						}
						
						query.append(" WHERE ");
						if (allot!=null && allot.equalsIgnoreCase("Unalloted"))
						{
							query.append(" allot_status=0 ");
						}
						else if (allot!=null && allot.equalsIgnoreCase("Alloted"))
						{
							query.append(" allot_status=1 ");
						}
						
						if (fromdate!=null && todate!=null)
						{
							String str[]=fromdate.split("-");
							if(str[0].length()<3)
							{
								fromdate=DateUtil.convertDateToUSFormat(fromdate);
								todate=DateUtil.convertDateToUSFormat(todate);
							}
							query.append(" AND task.date BETWEEN '"+fromdate+"' AND '"+todate+"' ");
						}
						if (taskType!=null && !taskType.equalsIgnoreCase("-1"))
						{
							query.append(" AND tt.id = '"+taskType+"' ");
						}
						if (taskPriority!=null && !taskPriority.equalsIgnoreCase("-1"))
						{
							query.append(" AND task.priority = '"+taskPriority+"' ");
						}
						if (search!=null && !search.equalsIgnoreCase("-1") && !search.equalsIgnoreCase(""))
						{
							query.append(" AND task.taskname LIKE '%"+search+"%' ");
						}
						if (taskStatus!=null && !taskStatus.equalsIgnoreCase("All")&& !taskStatus.equalsIgnoreCase("-1"))
						{
							if (taskStatus.equalsIgnoreCase("Running"))
							{
								query.append(" AND task.completion <'"+DateUtil.getCurrentDateUSFormat()+"' ");
							}
							else if(taskStatus.equalsIgnoreCase("Pending"))
							{
								query.append(" AND task.completion >'"+DateUtil.getCurrentDateUSFormat()+"' ");
							}
							else
							{
								query.append(" AND task.status = '"+taskStatus+"' ");
							}
						}
						System.out.println("query.toString()   "+query.toString());
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
						
						if(data!=null && !data.isEmpty())
						{
							String clientVal=null;
							Object[] obdata=null;
							String clientData=null;
							DARReportHelper DRH=new DARReportHelper();
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								int k=0;
								obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for(GridDataPropertyView gpv:fieldNames)
								{
									if(obdata[k]!=null && !obdata[k].toString().equalsIgnoreCase(""))
									{
										if(gpv.getColomnName()!=null)
										{
											if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("task.completion") || gpv.getColomnName().equalsIgnoreCase("task.functional_Date") || gpv.getColomnName().equalsIgnoreCase("task.technical_Date"))
											{
												String splitarr[]=obdata[k].toString().split(" ");
												if (splitarr.length>1) 
												{
													one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(splitarr[0])+" "+splitarr[1]);
												}
												else
												{
													one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
											}
											else if(gpv.getColomnName().equalsIgnoreCase("task.clientfor"))
											{
											    clientVal = obdata[k].toString();
												if (obdata[k].toString().equalsIgnoreCase("PA")) 
												{
													one.put(gpv.getColomnName().toString(),"Prospect Associate");
												}
												else if (obdata[k].toString().equalsIgnoreCase("PC")) 
												{
													one.put(gpv.getColomnName().toString(),"Prospect Client");
												}
												else if(obdata[k].toString().equalsIgnoreCase("N"))
												{
													one.put(gpv.getColomnName(),"Other");
												}
												else if(obdata[k].toString().equalsIgnoreCase("EC"))
												{
													one.put(gpv.getColomnName(),"Existing Client");
												}
												else if(obdata[k].toString().equalsIgnoreCase("EA"))
												{
													one.put(gpv.getColomnName(),"Existing Associate");
												}
												else if(obdata[k].toString().equalsIgnoreCase("IN"))
												{
													one.put(gpv.getColomnName(),"Internal");
												}
											}
											else if(gpv.getColomnName().equalsIgnoreCase("task.cName"))
											{
												clientData=obdata[k].toString();
												one.put(gpv.getColomnName(), DRH.clientName(clientVal, obdata[k].toString(),connectionSpace));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("task.offering"))
											{
												one.put(gpv.getColomnName(), DRH.offeringName(clientVal, clientData,obdata[k].toString(),connectionSpace));
											}
											else if(gpv.getColomnName().equalsIgnoreCase("task.status"))
											{
												if (obdata[k].toString().equalsIgnoreCase("Pending"))
												{
													
													boolean status=DateUtil.comparetwoDatesForDAR(DateUtil.getCurrentDateUSFormat(), obdata[k+7].toString());
													
													if (status)
													{
														one.put(gpv.getColomnName(),"On Time");
													}
													else
													{
														one.put(gpv.getColomnName(),"Off Time");
													}
												} 
												else if(obdata[k].toString().equalsIgnoreCase("Done"))
												{
													one.put(gpv.getColomnName(),"Completed");
												}
												else
												{
													one.put(gpv.getColomnName(),obdata[k].toString());
												}
											}
											else
											{
												one.put(gpv.getColomnName(),obdata[k].toString());
											}
										}
									}
									else
									{
										one.put(gpv.getColomnName().toString(), "NA");
									}
									k++;
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							@SuppressWarnings("unused")
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							return SUCCESS;
						}
					}
				}
				retunString=SUCCESS;
				session.remove("masterViewProp");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			retunString=LOGIN;
		}
		return retunString;
	}
	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public Map<Integer, String> getListtask() {
		return listtask;
	}

	public void setListtask(Map<Integer, String> listtask) {
		this.listtask = listtask;
	}

	public String getAllot() {
		return allot;
	}

	public void setAllot(String allot) {
		this.allot = allot;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getTaskPriority()
	{
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority)
	{
		this.taskPriority = taskPriority;
	}

	public String getSearch()
	{
		return search;
	}

	public void setSearch(String search)
	{
		this.search = search;
	}

	public Map<String, String> getRegisterBY()
	{
		return registerBY;
	}

	public void setRegisterBY(Map<String, String> registerBY)
	{
		this.registerBY = registerBY;
	}

	public Map<String, String> getAllotmentList()
	{
		return allotmentList;
	}

	public void setAllotmentList(Map<String, String> allotmentList)
	{
		this.allotmentList = allotmentList;
	}

	public String getTaskStatus()
	{
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus)
	{
		this.taskStatus = taskStatus;
	}

	public Map<String, String> getSearchByname()
	{
		return searchByname;
	}

	public void setSearchByname(Map<String, String> searchByname)
	{
		this.searchByname = searchByname;
	}

	
	
}
