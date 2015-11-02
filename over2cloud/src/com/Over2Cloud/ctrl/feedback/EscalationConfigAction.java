package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackDraftPojo;

public class EscalationConfigAction extends GridPropertyBean implements ServletRequestAware
{
	private Map<String, String> serviceDeptMap;
	private Map<String, String> floorMap;
	private HttpServletRequest request;
	private String deptId;
	private JSONArray jsonList;
	private List<Object> masterViewList;
	private String status;
	private String wingName;
	private String floorName;
	private String trashEmpId;
	private String addressTime;
	private String resTime;
	FeedbackDraftPojo feedDraft = null;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	public String beforeConfigEsc()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				serviceDeptMap = new LinkedHashMap<String, String>();
				List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, "FM", connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptMap.put(object1[0].toString(), object1[1].toString());
					}
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String addEscConfig()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				
				
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("esc_dept");
				ob1.setDatatype("int(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("esc_for");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("consider_roaster");
				ob1.setDatatype("varchar(3)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("addresing_time");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("resolution_time");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("escalation_time");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				 
				
				ob1 = new TableColumes();
				ob1.setColumnname("user_name");
				ob1.setDatatype("varchar(30)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				cbt.createTable22("escalation_config_detail", tableColume, connectionSpace);
				
				 
				
				ob = new InsertDataTable();
				ob.setColName("consider_roaster");
				ob.setDataName(request.getParameter("considerRoaster"));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("addresing_time");
				ob.setDataName(request.getParameter("addrtime"));
				insertData.add(ob);
				 
				ob = new InsertDataTable();
				ob.setColName("resolution_time");
				ob.setDataName(request.getParameter("restime"));
				insertData.add(ob);
				
				 
				
				ob = new InsertDataTable();
				ob.setColName("escalation_time");
				ob.setDataName(request.getParameter("esctime"));
				insertData.add(ob);
				 
				
				ob = new InsertDataTable();
				ob.setColName("user_name");
				ob.setDataName(userName);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				String[] deptID = request.getParameterValues("escDept");
				//System.out.println("Length********"+deptID.length);
				if(deptID!=null && deptID.length>0)
				{
					for (int i = 0; i < deptID.length; i++)
					{
						//System.out.println("For Loop i********"+i);
						
						if(deptID[i].equalsIgnoreCase("All"))
						{
							System.out.println("In dept All");
							List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, "FM", connectionSpace);
							if (departmentlist != null && departmentlist.size() > 0)
							{
								for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
								{
									Object[] object1 = (Object[]) iterator.next();
									if(object1[0]!=null)
									{
										ob = new InsertDataTable();
										ob.setColName("esc_dept");
										ob.setDataName(object1[0].toString());
										insertData.add(ob);
										
										
										
										String[] escFor = request.getParameterValues("escFor");
										if(escFor!=null && escFor.length>0)
										{
											for (int k = 0; k < escFor.length; k++)
											{
												//System.out.println("EscFor : "+escFor[k]);
												
												if(escFor[k].equalsIgnoreCase("All"))
												{
													String escFor1[]={"Admin Round","Paper","SMS","Visitor","Ward Rounds"};
													 	 for (int j = 0; j < escFor1.length; j++)
														{
													
														 	if(escFor1[j]!=null )
															{
														 		//System.out.println("In ESC All");
																
																ob = new InsertDataTable();
																ob.setColName("esc_for");
																ob.setDataName( escFor1[j]);
																insertData.add(ob);
																cbt.deleteAllRecordForId("escalation_config_detail","esc_for",escFor1[j],"esc_dept",object1[0].toString(),connectionSpace);
																cbt.insertIntoTable("escalation_config_detail", insertData, connectionSpace);
																insertData.remove(insertData.size()-1);
															}
														}
													  
													break;
												}
												else
												{
												//	System.out.println("In ESC Selected");
													ob = new InsertDataTable();
													ob.setColName("esc_for");
													ob.setDataName(escFor[k]);
													insertData.add(ob);
													cbt.deleteAllRecordForId("escalation_config_detail","esc_for",escFor[k],"esc_dept",object1[0].toString(),connectionSpace);
													cbt.insertIntoTable("escalation_config_detail", insertData, connectionSpace);
													insertData.remove(insertData.size()-1);
												}
											}
										}
										
										insertData.remove(insertData.size()-1); 
									}
								}
								 
							}
							break;
						}
						else
						{

							//System.out.println("In dept Selected");
							ob = new InsertDataTable();
							ob.setColName("esc_dept");
							ob.setDataName(deptID[i]);
							insertData.add(ob);
							

							String[] escFor = request.getParameterValues("escFor");
							if(escFor!=null && escFor.length>0)
							{
								for (int k = 0; k < escFor.length; k++)
								{
									if(escFor[k].equalsIgnoreCase("All"))
									{

										//System.out.println("In Esc All");
										String escFor1[]={"Admin Round","Paper","SMS","Visitor","Ward Rounds"};
										
											for (int j = 0; j < escFor1.length; j++)
											{
										
											 	if(escFor1[j]!=null)
												{
													ob = new InsertDataTable();
													ob.setColName("esc_for");
													ob.setDataName( escFor1[j]);
													insertData.add(ob);
													cbt.deleteAllRecordForId("escalation_config_detail","esc_for",escFor1[j],"esc_dept",deptID[i],connectionSpace);
													cbt.insertIntoTable("escalation_config_detail", insertData, connectionSpace);
													insertData.remove(insertData.size()-1);
												}
											}
											
										  
										break;
									}
									else
									{

										//System.out.println("In ESC Selected");
										ob = new InsertDataTable();
										ob.setColName("esc_for");
										ob.setDataName(escFor[k]);
										insertData.add(ob);
										cbt.deleteAllRecordForId("escalation_config_detail","esc_for",escFor[k],"esc_dept",deptID[i],connectionSpace);
										cbt.insertIntoTable("escalation_config_detail", insertData, connectionSpace);
										insertData.remove(insertData.size()-1);
									}
								}
								insertData.remove(insertData.size()-1);
							}
							
					 
						}
					}
				}
				
				
				 
				
				
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				addActionMessage("Opps. There are some problem in data save.");
				return ERROR;
				
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	 
	public String getEscalationTime()
	{
		String returnResult = ERROR;
		try
		{
			feedDraft = new FeedbackDraftPojo();
			String resolution_time = null;
			String[] arr1 = null;
			String[] arr2 = null;
			if (addressTime!=null && !addressTime.equals("")  && resTime!=null && !resTime.equals(""))
			{
					arr1 = addressTime.split(":");
				arr2 = resTime.split(":");
				int a = 0, b = 0, c = 0, d = 0;
				a = Integer.parseInt((arr1[1])) + Integer.parseInt((arr2[1]));
				b = Integer.parseInt((arr1[0])) + Integer.parseInt((arr2[0]));
				if (a != 0 && a < 60)
				{
					c = a;
					if(a>0&&a<10)
					{
						resolution_time = "" + b + ":0" + a;
					}
					else
					{
						resolution_time = "" + b + ":" + a;
					}
					
				}
				else
				{
					d = a / 60;
					c = a % 60;
					d = b + d;
					if(c==0)
					{
						resolution_time = "" + d + ":" + c+"0";
					}
					else
					{	
						resolution_time = "" + d + ":" + c;
					}	
				}
				if (resolution_time != null && !resolution_time.equals(""))
				{
					feedDraft.setResolution_time(resolution_time);
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}
	
	 
	
	public String viewEscDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb=new ArrayList<Object>();
				List dataList = null;
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				query.append("SELECT esc.id,dept.contact_subtype_name,esc.consider_roaster,esc.addresing_time,esc.resolution_time,");
				query.append("esc.escalation_time,esc.esc_for");
				query.append(" FROM escalation_config_detail AS esc");
				query.append(" INNER JOIN contact_sub_type AS dept ON dept.id = esc.esc_dept");
				 
				
				//System.out.println("####### "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", CUA.getValueWithNullCheck(object[0]));
						one.put("deptName", CUA.getValueWithNullCheck(object[1]));
					 	one.put("considerRoaster", CUA.getValueWithNullCheck(object[2]));
						one.put("addresingTime", CUA.getValueWithNullCheck(object[3]));
						one.put("resolutionTime", CUA.getValueWithNullCheck(object[4]));
						one.put("escalationTime", CUA.getValueWithNullCheck(object[5]));
						
						one.put("escFor", CUA.getValueWithNullCheck(object[6]));
						 
						
						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	 
	/*public String getEscTime(String fieldName,String conField,String conValue)
	{
		String time="00:00";
		try
		{
			String query ="SELECT "+fieldName+" FROM escalation_config_detail WHERE "+conField+" ="+conValue;
			List dataList  = cbt.executeAllSelectQuery(query, connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				time = dataList.get(0).toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return time;
	}*/
	
	 
	public Map<String, String> getServiceDeptMap() {
		return serviceDeptMap;
	}

	public void setServiceDeptMap(Map<String, String> serviceDeptMap) {
		this.serviceDeptMap = serviceDeptMap;
	}
	
	public Map<String, String> getFloorMap() {
		return floorMap;
	}

	public void setFloorMap(Map<String, String> floorMap) {
		this.floorMap = floorMap;
	}
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public JSONArray getJsonList() {
		return jsonList;
	}

	public void setJsonList(JSONArray jsonList) {
		this.jsonList = jsonList;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getWingName() {
		return wingName;
	}

	public void setWingName(String wingName) {
		this.wingName = wingName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getTrashEmpId() {
		return trashEmpId;
	}

	public void setTrashEmpId(String trashEmpId) {
		this.trashEmpId = trashEmpId;
	}

	public String getAddressTime() {
		return addressTime;
	}

	public void setAddressTime(String addressTime) {
		this.addressTime = addressTime;
	}

	public String getResTime() {
		return resTime;
	}

	public void setResTime(String resTime) {
		this.resTime = resTime;
	}
	public FeedbackDraftPojo getFeedDraft() {
		return feedDraft;
	}

	public void setFeedDraft(FeedbackDraftPojo feedDraft) {
		this.feedDraft = feedDraft;
	}

}
