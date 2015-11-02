package com.Over2Cloud.ctrl.dar.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.dar.helper.DarHelper;
import com.Over2Cloud.ctrl.krLibrary.KRActionHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
      
	public class TaskRegistrationAction extends GridPropertyBean implements ServletRequestAware
	{
		static final Log log = LogFactory.getLog(TaskRegistrationAction.class);
		private static final long serialVersionUID = 1L;
	    private HttpServletRequest request;
		private List <ConfigurationUtilBean> taskColumnMap=null;
		private List <ConfigurationUtilBean> taskCalenderMap=null;
		private List <ConfigurationUtilBean> taskFileMap=null;
		private List <ConfigurationUtilBean> taskDropMap=null;
		Map<Integer, String> listtask = null;
		Map<Integer, String>  listemployee = new LinkedHashMap<Integer, String>();
		Map<Integer, String>  listAllotedemployee = null;
		private List<GridDataPropertyView> taskTypeViewProp = new ArrayList<GridDataPropertyView>();
		private File attachment;
		private String attachmentContentType;
		private String attachmentFileName;
		
		private List<Object> viewList;
		Map<String,String> attachFileMap=new LinkedHashMap<String,String>();
		private String fileName;
	    private InputStream fileInputStream;
	    private JSONArray jsonArr = null;
	    private String allotedById;
	    private String clientValue;
	    private String contactName;
	    private Map<String, String> ccPerson=null;
		private Map<String, String> taskDetails;
		private String taskId;
		private long contentLength;
		private Map<String, String> deptName;
		private String dataFor;
		public String execute()
		{
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			return SUCCESS;
		}
		
		@SuppressWarnings("rawtypes")
		public String beforeAddTask()
		{ 
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					listAllotedemployee = new LinkedHashMap<Integer, String>();
					listtask=new LinkedHashMap<Integer, String>();
					TaskRegistrationHelper TRH=new TaskRegistrationHelper();
					List allotedTo=TRH.getAllotedToName("DAR", userName,connectionSpace);
					if (allotedTo!=null && allotedTo.size()>0) 
					{
						Object[] object=null;
						for (Iterator iterator = allotedTo.iterator(); iterator.hasNext();) 
						 {
							object = (Object[]) iterator.next();
							listAllotedemployee.put((Integer)object[0], object[1].toString());
						}
					}
					 String query1 = "SELECT id,tasktype FROM task_type ORDER BY tasktype ASC";
					 List task=cbt.executeAllSelectQuery(query1, connectionSpace);
					 if (task!=null && task.size()>0) 
					 {
						 Object[] object=null;
						 for (Iterator iterator = task.iterator(); iterator.hasNext();) 
						 {
							object = (Object[]) iterator.next();
							listtask.put(Integer.parseInt(object[0].toString()), object[1].toString());
						 }
					 }
					List<GridDataPropertyView> taskColumnList=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
					taskColumnMap=new ArrayList<ConfigurationUtilBean>();
					taskCalenderMap=new ArrayList<ConfigurationUtilBean>();
					taskFileMap=new ArrayList<ConfigurationUtilBean>();
					taskDropMap=new ArrayList<ConfigurationUtilBean>();
					if(taskColumnList!=null && taskColumnList.size()>0)
					{
						ConfigurationUtilBean obj=null;
						for(GridDataPropertyView  gdp : taskColumnList)
						{
							obj=new ConfigurationUtilBean();
							if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskColumnMap.add(obj);
							}
							else if(gdp.getColType().trim().equalsIgnoreCase("Date"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskCalenderMap.add(obj);
							}
							else if(gdp.getColType().trim().equalsIgnoreCase("F"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskFileMap.add(obj);
							}
							else if(gdp.getColType().trim().equalsIgnoreCase("D"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskDropMap.add(obj);
							}
						}
						returnResult=SUCCESS;
					}
				}
				catch(Exception e)
				{
					 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in Over2Cloud  method beforeAddTask of class "+getClass(), e);
					e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		}
		
		@SuppressWarnings("rawtypes")
		public String  beforeAddAllotTask()
		{ 
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					listAllotedemployee = new LinkedHashMap<Integer, String>();
					TaskRegistrationHelper TRH=new TaskRegistrationHelper();
					List allotedTo=TRH.getAllotedToName("DAR", userName,connectionSpace);
					if (allotedTo!=null && allotedTo.size()>0) 
					{
						Object[] object=null;
						for (Iterator iterator = allotedTo.iterator(); iterator.hasNext();) 
						 {
							object = (Object[]) iterator.next();
							listAllotedemployee.put((Integer)object[0], object[1].toString());
						}
					}
					listtask=new LinkedHashMap<Integer, String>();
					listtask.put(-1, "Create New");
					List unAllotProject=TRH.fetchUnAllotedProjects(connectionSpace);
					if (unAllotProject!=null && !unAllotProject.isEmpty())
					{
						Object[] object=null;
						for (Iterator iterator = unAllotProject.iterator(); iterator.hasNext();) 
						 {
							object = (Object[]) iterator.next();
							listtask.put((Integer)object[0], object[1].toString());
						}
					}
					List<GridDataPropertyView> taskColumnList=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
					taskColumnMap=new ArrayList<ConfigurationUtilBean>();
					taskCalenderMap=new ArrayList<ConfigurationUtilBean>();
					taskFileMap=new ArrayList<ConfigurationUtilBean>();
					taskDropMap=new ArrayList<ConfigurationUtilBean>();
					if(taskColumnList!=null && taskColumnList.size()>0)
					{
						ConfigurationUtilBean obj=null;
						for(GridDataPropertyView  gdp : taskColumnList)
						{
							obj=new ConfigurationUtilBean();
							if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskColumnMap.add(obj);
							}
							else if(gdp.getColType().trim().equalsIgnoreCase("Date"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskCalenderMap.add(obj);
							}
							else if(gdp.getColType().trim().equalsIgnoreCase("F"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskFileMap.add(obj);
							}
							else if(gdp.getColType().trim().equalsIgnoreCase("D"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
                                taskDropMap.add(obj);
							}
						}
						returnResult=SUCCESS;
					}
				}
				catch(Exception e)
				{
					 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in Over2Cloud  method beforeAddTask of class "+getClass(), e);
					e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		}
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String taskRegistrationAdd()
		{
		   try
		    {
			   System.out.println("IMNSIE TASK REGISTARTion method");
			    if(userName==null || userName.equalsIgnoreCase(""))
				 return LOGIN;
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
				 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				 if(org2!=null && org2.size()>0)
				 {
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					TableColumes ob1=null;
					for(GridDataPropertyView gdp:org2)
					{
						 ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 Tablecolumesaaa.add(ob1);
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
					}
					 ob1=new TableColumes();
					 ob1.setColumnname("status");
					 ob1.setDatatype("varchar(20)");
					 ob1.setConstraint("default NULL");
					 Tablecolumesaaa.add(ob1);
					 
					 ob1=new TableColumes();
					 ob1.setColumnname("active_status");
					 ob1.setDatatype("varchar(10)");
					 ob1.setConstraint("default NULL");
					 Tablecolumesaaa.add(ob1);
					 
					 ob1=new TableColumes();
					 ob1.setColumnname("allot_status");
					 ob1.setDatatype("varchar(10)");
					 ob1.setConstraint("default NULL");
					 Tablecolumesaaa.add(ob1);
					 
					 cbt.createTable22("task_registration",Tablecolumesaaa,connectionSpace);
					 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 Collections.sort(requestParameterNames);
					 Iterator it = requestParameterNames.iterator();
					 while (it.hasNext()) 
					 {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						
						if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
						{
							 ob=new InsertDataTable();
							 ob.setColName(Parmname);
							 ob.setDataName(paramValue);
							 insertData.add(ob);
			            }
						else if(Parmname.equalsIgnoreCase("attachment1"))
						{
							String renameFilePath = new CreateFolderOs().createUserDir("DAR") + "//" +paramValue;
							String str = renameFilePath.replace("//", "/");
							if (renameFilePath != null)
							{
								File theFile = new File(renameFilePath);
								File newFileName = new File(str);
								if (theFile != null)
								{
									try
									{
										File abc=new File("attachment1");
										FileUtils.copyFile(abc, theFile);
										if (theFile.exists())
											theFile.renameTo(newFileName);
									}
									catch (IOException e)
									{
										e.printStackTrace();
									}
									if (theFile != null)
									{
										 ob=new InsertDataTable();
				                         ob.setColName("attachment1");
				                         ob.setDataName(paramValue);
				                         insertData.add(ob);
									}
								}
							}
						}
					 }			 
					 if(userTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("userName");
						 ob.setDataName(userName);
						 insertData.add(ob);
					 }
					 if(dateTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("date");
						 ob.setDataName(DateUtil.getCurrentDateUSFormat());
						 insertData.add(ob);
					 }
					 if(timeTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("time");
						 ob.setDataName(DateUtil.getCurrentTime());
						 insertData.add(ob);
					 }
					 ob=new InsertDataTable();
					 ob.setColName("status");
					 ob.setDataName("Pending");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("active_status");
					 ob.setDataName("1");
					 insertData.add(ob);
					 
					 ob=new InsertDataTable();
					 ob.setColName("allot_status");
					 ob.setDataName("0");
					 insertData.add(ob);
					 
					 if (attachmentFileName != null)
					 {
						String renameFilePath = new CreateFolderOs().createUserDir("DAR") + "//" +getAttachmentFileName();
						String str = renameFilePath.replace("//", "/");
						if (renameFilePath != null)
						{
							File theFile = new File(renameFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(attachment, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									 ob=new InsertDataTable();
			                         ob.setColName("attachment");
			                         ob.setDataName(getAttachmentFileName());
			                         insertData.add(ob);
								}
							}
						}
					 }
					
					 if(insertData.size()>0)
					 status=cbt.insertIntoTable("task_registration",insertData,connectionSpace);
					 if(status)
					 {
						 addActionMessage("Project Registered Successfully!!!");
						 return SUCCESS;
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!");
						 return SUCCESS;
					 }
				}
		}
		catch(Exception e)
		{
			 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in Over2Cloud  method addTask of class "+getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
		
	public void setTaskViewProp()
	{
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			taskTypeViewProp.add(gpv);
			try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","dar_configuration");
				if(statusColName!=null&&statusColName.size()>0)
				{
					for(GridDataPropertyView gdp:statusColName)
					{
						gpv=new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						if(gdp.getColomnName().equalsIgnoreCase("attachment"))
						{
							gpv.setFormatter(gdp.getFormatter());
						}
						taskTypeViewProp.add(gpv);
					}
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
	}
		
  public String beforeTaskView()
  {
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setTaskViewProp();
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
		
	@SuppressWarnings({  "rawtypes" })
	public String viewtaskRegisOperation()
	{
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					StringBuilder query=new StringBuilder("");
					query.append("SELECT COUNT(*) FROM task_registration");
					List  dataCount1=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(dataCount1.size()>0)
					{
						BigInteger count=new BigInteger("1");
						Object obdata=null;
						for(Iterator it=dataCount1.iterator(); it.hasNext();)
						{
							 obdata=(Object)it.next();
							 count=(BigInteger)obdata;
						}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					TaskRegistrationHelper TH=new TaskRegistrationHelper();
					String loggedContactId=TH.getContactIdForTaskName("DAR", userName, connectionSpace);
					StringBuilder contactId=new StringBuilder();
					if (loggedContactId!=null)
					{
						for (String s : loggedContactId.split(","))
						{
							contactId.append("'"+s+"',");
						}
					}
					query.setLength(0);
					query.append("SELECT  ");
					List fieldNames=Configuration.getColomnList("mapped_dar_configuration", accountID, connectionSpace, "dar_configuration");
					int i=0;
					for(Iterator it=fieldNames.iterator(); it.hasNext();)
					{
					    obdata=(Object)it.next();
					    if(obdata!=null)
					    {
				    		if(i<fieldNames.size()-1)
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
						    else
						    {
						    	query.append("tr."+obdata.toString());
						    }
							
					    }
					    i++;
					}
					query .append(" FROM task_registration AS tr ");
					query .append("LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id ");
					query .append("LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id ");
					query .append("LEFT JOIN compliance_contacts AS cc3 ON tr.validate_By_2=cc3.id ");
					query .append("LEFT JOIN compliance_contacts AS cc4 ON tr.validate_By_1=cc4.id ");
					query .append("LEFT JOIN compliance_contacts AS cc5 ON tr.guidance=cc5.id  ");
					query .append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
					query .append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
					query .append("LEFT JOIN employee_basic emp3 ON cc3.emp_id= emp3.id ");
					query .append("LEFT JOIN employee_basic emp4 ON cc4.emp_id= emp4.id ");
					query .append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
					query .append("LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
					query .append("LEFT JOIN client_type ct ON tr.clientfor= ct.id  ");
					query.append("WHERE tr.username='"+userName+"' ");
					if (loggedContactId!=null && contactId!=null && !contactId.toString().equalsIgnoreCase(""))
					{
						query.append(" OR tr.allotedby IN ("+contactId.toString().substring(0, contactId.toString().length()-1)+")");
					}
					query.append(" AND tr.active_status='1' ");
					
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" tr."+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" tr."+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" tr."+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"'");
						}
					}
					else if(getSord()!=null && getSidx()!=null && !getSord().equalsIgnoreCase("") && !getSidx().equalsIgnoreCase(""))
					{
						query.append(" ORDER BY tr."+getSidx()+" "+getSord()+"");
					}
					//query1.append(" limit "+from+","+to);
					System.out.println(">>>>>>>    "+query.toString());
					List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					
						if(dataCount!=null && dataCount.size()>0)
						{
							DARReportHelper DRH=new DARReportHelper();
							viewList=new ArrayList<Object>();
							List<Object> Listhb=new ArrayList<Object>();
							Object[] obdata1=null;
							String clientVal=null;
							String clientData=null;
							for(Iterator it=dataCount.iterator(); it.hasNext();)
							{
								 obdata1=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) 
								{
									if(obdata1[k]!=null)
									{
										if(k==0)
										{
											one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
										}
										else if(fieldNames.get(k).toString().equalsIgnoreCase("intiation") || fieldNames.get(k).toString().equalsIgnoreCase("date"))
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else if(fieldNames.get(k).toString().equalsIgnoreCase("completion") || fieldNames.get(k).toString().equalsIgnoreCase("functional_Date") || fieldNames.get(k).toString().equalsIgnoreCase("technical_Date"))
										{
											String splitarr[]=obdata1[k].toString().split(" ");
											if (splitarr.length>1) 
											{
												one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(splitarr[0])+" "+splitarr[1]);
											}
											else
											{
												one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
											}
										}
										else if(fieldNames.get(k).toString().equalsIgnoreCase("clientfor"))
										{
											clientVal= obdata1[k].toString();
											if (obdata1[k].toString().equalsIgnoreCase("PA")) 
											{
												one.put(fieldNames.get(k).toString(),"Prospect Associate");
											}
											else if (obdata1[k].toString().equalsIgnoreCase("PC")) 
											{
												one.put(fieldNames.get(k).toString(),"Prospect Client");
											}
											else if(obdata1[k].toString().equalsIgnoreCase("N"))
											{
												one.put(fieldNames.get(k).toString(),"Other");
											}
											else if(obdata1[k].toString().equalsIgnoreCase("EC"))
											{
												one.put(fieldNames.get(k).toString(),"Existing Client");
											}
											else if(obdata1[k].toString().equalsIgnoreCase("EA"))
											{
												one.put(fieldNames.get(k).toString(),"Existing Associate");
											}
											else if(obdata1[k].toString().equalsIgnoreCase("IN"))
											{
												one.put(fieldNames.get(k).toString(),"Internal");
											}
										}
										else if(fieldNames.get(k).toString().equalsIgnoreCase("cName"))
										{
											clientData=obdata1[k].toString();
											one.put(fieldNames.get(k).toString(), DRH.clientName(clientVal, obdata1[k].toString(),connectionSpace));
										}
										else if(fieldNames.get(k).toString().equalsIgnoreCase("offering"))
										{
											one.put(fieldNames.get(k).toString(), DRH.offeringName(clientVal, clientData,obdata1[k].toString(),connectionSpace));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), obdata1[k].toString());
										}
									}
									else 
									{
										if(fieldNames.get(k).toString().equalsIgnoreCase("validate_By_2")  || fieldNames.get(k).toString().equalsIgnoreCase("validate_By_1"))
										{
											one.put(fieldNames.get(k).toString(),"No Review Required" );
										}
										else
										{
											 one.put(fieldNames.get(k).toString(), "NA");
										}
										    
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
	
		@SuppressWarnings("rawtypes")
		public String download() 
		{

			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				List listData=null;
				try
				{
					
					    CommonOperInterface cbt = new CommonConFactory().createInterface();
				        String sql_query = "SELECT attachment FROM task_registration WHERE id='"+getFileName()+"'";
				        listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
						String files = listData.get(0).toString();
						fileName = new CreateFolderOs().createUserDir("DAR")+ "//"+files;
						File file = new File(fileName);

						if (file.exists())
						{
							fileInputStream = new FileInputStream(file);
							contentLength = file.length();
							fileName = file.getName();
							return "download";
						}
						else
						{
							addActionError("File dose not exist");
							return ERROR;
						}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return SUCCESS;
			}
			else
			{
				return LOGIN;
			}
		}	
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String modifyTaskRegistration()
		{
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					if(getOper().equalsIgnoreCase("edit"))
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						Map<String, Object>wherClause=new HashMap<String, Object>();
						Map<String, Object>condtnBlock=new HashMap<String, Object>();
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue=request.getParameter(parmName);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("completion") &&!parmName.equalsIgnoreCase("technical_Date")
									&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid")&& !parmName.equalsIgnoreCase("functional_Date"))
							{
								wherClause.put(parmName, paramValue);
							}
							if(parmName.equalsIgnoreCase("completion"))
							{
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));	
							}
						    if(parmName.equalsIgnoreCase("functional_Date"))
						    {
							    wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));	
						    }  
						    if(parmName.equalsIgnoreCase("technical_Date"))
						    {
							    wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));	
						    }  
						}
						condtnBlock.put("id",getId());
						cbt.updateTableColomn("task_registration", wherClause, condtnBlock,connectionSpace);
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
		@SuppressWarnings("rawtypes")
		public String getAllotedByData()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (getAllotedById() != null && !getAllotedById().equalsIgnoreCase(""))
					{
						SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						StringBuilder query = new StringBuilder();
						query.append("SELECT  contact.id,emp.empName FROM employee_basic AS emp  ");
						query.append(" INNER JOIN compliance_contacts AS contact ON contact.emp_id=emp.id  ");
						query.append(" WHERE  moduleName='DAR' AND level >= (SELECT level FROM compliance_contacts WHERE id='" + getAllotedById() + "' )   ");
						query.append(" AND contact.forDept_id IN ( SELECT emp.deptname AS deptid  ");
						query.append(" FROM  employee_basic AS emp  INNER JOIN compliance_contacts AS contact ON contact.emp_id=emp.id INNER JOIN department AS dept ON " );
						query.append(" emp.deptname=dept.id INNER JOIN useraccount AS uac ON emp.useraccountid=uac.id ");
						query.append(" WHERE  contact.moduleName='DAR' AND uac.userID='"+(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY))+"'  ");
						query.append(" AND contact.forDept_id=dept.id )  AND emp.flag=0 AND contact.work_status=0 AND contact.id NOT IN(" + getAllotedById() + ") ORDER BY emp.empName ASC ");
						System.out.println("QUERY IS AS ==== "+query.toString());
						List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data2 != null && data2.size() > 0)
						{
							jsonArr=new JSONArray();
							JSONObject formDetailsJson = new JSONObject();
							Object[] object = null;
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									formDetailsJson = new JSONObject();
									formDetailsJson.put("ID", object[0].toString());
									formDetailsJson.put("NAME", object[1].toString());
									jsonArr.add(formDetailsJson);
								}
							}
						}
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
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
		
		@SuppressWarnings("rawtypes")
		public String getClientValueData()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (getClientValue() != null && !getClientValue().equalsIgnoreCase(""))
					{
						SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						StringBuilder query = new StringBuilder();
						query.append("SELECT ");
						if (getClientValue().equalsIgnoreCase("PC")) 
						{
							query.append(" cb.id,cb.clientName FROM client_basic_data as cb");
							query.append(" INNER JOIN client_offering_mapping AS com ON com.clientName=cb.id ");
							query.append(" WHERE com.isConverted ='0' GROUP BY cb.id ORDER BY cb.clientName ASC");
						}
						else if (getClientValue().equalsIgnoreCase("EC")) 
						{
							query.append(" cb.id,cb.clientName FROM client_basic_data as cb");
							query.append(" INNER JOIN client_offering_mapping AS com ON com.clientName=cb.id ");
							query.append(" WHERE com.isConverted ='1' GROUP BY cb.id ORDER BY cb.clientName ASC");
						}
						else if (getClientValue().equalsIgnoreCase("PA")) 
						{
							query.append(" ab.id,ab.associateName FROM associate_basic_data as ab ");
							query.append(" INNER JOIN associate_offering_mapping AS aom ON aom.associateName=ab.id ");
							query.append(" WHERE aom.isConverted ='0' GROUP BY ab.id ORDER BY ab.associateName ASC");
						}
						else if (getClientValue().equalsIgnoreCase("EA")) 
						{
							query.append(" ab.id,ab.associateName FROM associate_basic_data as ab ");
							query.append(" INNER JOIN associate_offering_mapping AS aom ON aom.associateName=ab.id ");
							query.append(" WHERE aom.isConverted ='1' GROUP BY ab.id ORDER BY ab.associateName ASC");
						}
						else if (getClientValue().equalsIgnoreCase("IN"))
						{
							query.append(" id,verticalname FROM offeringlevel1 ORDER BY verticalname ASC");
						}
						else if (getClientValue().equalsIgnoreCase("N")) 
						{
							query.append(" id,cPerson FROM client_type  ORDER BY cPerson ASC");
						}
						
						//System.out.println("QUERY IS AS ==== "+query.toString());
						List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data2 != null && data2.size() > 0)
						{
							jsonArr=new JSONArray();
							JSONObject formDetailsJson = new JSONObject();
							Object[] object = null;
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									formDetailsJson = new JSONObject();
									formDetailsJson.put("ID", object[0].toString());
									formDetailsJson.put("NAME", object[1].toString());
									jsonArr.add(formDetailsJson);
								}
							}
						}
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
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
		@SuppressWarnings("rawtypes")
		public String getOfferingData() 
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (getClientValue() != null && !getClientValue().equalsIgnoreCase(""))
					{
						SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						CommonHelper CH=new CommonHelper();
						String offeringDetails[]=CH.getOfferingName();
						StringBuilder query = new StringBuilder();
						query.append("SELECT ");
						if (getClientValue().equalsIgnoreCase("PC")) 
						{
							query.append(" id,"+offeringDetails[0]+" FROM "+offeringDetails[1]+" WHERE id IN (");
							query.append(" SELECT offeringId FROM client_offering_mapping WHERE clientName ='"+contactName+"'  ");
							query.append(" AND isConverted='0' AND offeringLevelId='"+offeringDetails[2]+"' )");
						}
						else if (getClientValue().equalsIgnoreCase("EC")) 
						{
							query.append(" id,"+offeringDetails[0]+" FROM "+offeringDetails[1]+" WHERE id IN (");
							query.append(" SELECT offeringId FROM client_offering_mapping WHERE clientName ='"+contactName+"'  ");
							query.append(" AND isConverted='1' AND offeringLevelId='"+offeringDetails[2]+"' )");
						}
						else if (getClientValue().equalsIgnoreCase("PA")) 
						{
							query.append(" id,"+offeringDetails[0]+" FROM "+offeringDetails[1]+" WHERE id IN (");
							query.append(" SELECT offeringId FROM associate_offering_mapping WHERE associateName ='"+contactName+"'  ");
							query.append(" AND isConverted='0' AND offeringLevelId='"+offeringDetails[2]+"' )");
						}
						else if (getClientValue().equalsIgnoreCase("EA")) 
						{
							query.append(" id,"+offeringDetails[0]+" FROM "+offeringDetails[1]+" WHERE id IN (");
							query.append(" SELECT offeringId FROM associate_offering_mapping WHERE associateName ='"+contactName+"'  ");
							query.append(" AND isConverted='1' AND offeringLevelId='"+offeringDetails[2]+"' )");
						}
						else if (getClientValue().equalsIgnoreCase("IN"))
						{
							if (offeringDetails[2].equalsIgnoreCase("2")) 
							{
								query.append(" id,offeringname FROM offeringlevel2 WHERE verticalname='"+contactName+"'");
							}
							else if (offeringDetails[2].equalsIgnoreCase("3")) 
							{
								query.append(" o3.id,o3.subofferingname FROM offeringlevel3 as o3 INNER JOIN offeringlevel2 AS o2 On o3.offeringname= o2.id WHERE  o2.verticalname='"+contactName+"'");
							}
							else if (offeringDetails[2].equalsIgnoreCase("4")) 
							{
								query.append(" o4.id,o4.variantname FROM offeringlevel4 as o4 INNER JOIN offeringlevel3 AS o3 ON o4.subofferingname=o3.id WHERE  offeringlevel1 AS o1 ON o1.id='"+contactName+"'");
							}
							else if (offeringDetails[2].equalsIgnoreCase("5")) 
							{
								query.append(" o5.id,o5.subvariantname FROM offeringlevel5 as o5 INNER JOIN offeringlevel4 AS o4 ON o5.variantname=o4.id WHERE  offeringlevel1 AS o1 ON o1.id='"+contactName+"'");
							}
						}
						else if (getClientValue().equalsIgnoreCase("N")) 
						{
							query.append(" id,"+offeringDetails[0]+" FROM "+offeringDetails[1]+" ORDER BY  "+offeringDetails[0]+" ASC ");
						}
						
						//System.out.println("QUERY IS AS ==== "+query.toString());
						List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data2 != null && data2.size() > 0)
						{
							jsonArr=new JSONArray();
							JSONObject formDetailsJson = new JSONObject();
							Object[] object = null;
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									formDetailsJson = new JSONObject();
									formDetailsJson.put("ID", object[0].toString());
									formDetailsJson.put("NAME", object[1].toString());
									jsonArr.add(formDetailsJson);
								}
							}
						}
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
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
		@SuppressWarnings("rawtypes")
		public String getClientContactPersonNameOnChange()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (getClientValue() != null && !getClientValue().equalsIgnoreCase(""))
					{
						ccPerson=new LinkedHashMap<String, String>();
						SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						StringBuilder query = new StringBuilder();
						List data2 =null;
						
						if (getClientValue().equalsIgnoreCase("PC") || getClientValue().equalsIgnoreCase("EC") || getClientValue().equalsIgnoreCase("PA") || getClientValue().equalsIgnoreCase("EA")) 
						{
							query.append("SELECT ");
							if (getClientValue().equalsIgnoreCase("PC") || getClientValue().equalsIgnoreCase("EC")) 
							{
								query.append(" id,personName FROM client_contact_data WHERE clientName ='"+getContactName()+"' ORDER BY personName ASC");
							}
							else if (getClientValue().equalsIgnoreCase("PA") || getClientValue().equalsIgnoreCase("EA")) 
							{
								query.append(" id,name FROM associate_contact_data WHERE associateName ='"+getContactName()+"' ORDER BY name ASC");
							}
							data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						}
						else if (getClientValue().equalsIgnoreCase("IN"))
						{
							data2=new TaskRegistrationHelper().getAllotedToName("DREAM_HDM", userName,connectionSpace);
						}
						//System.out.println("QUERY IS AS getClientContactPersonName ==== "+query.toString());
						
						if (data2 != null && data2.size() > 0)
						{
							Object[] object = null;
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									ccPerson.put(object[0].toString(), object[1].toString());
								}
							}
						}
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
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
		@SuppressWarnings("rawtypes")
		public String getClientContactPersonName()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (getClientValue() != null && !getClientValue().equalsIgnoreCase(""))
					{
						SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						StringBuilder query = new StringBuilder();
						List data2 =null;
						
						if (getClientValue().equalsIgnoreCase("PC") || getClientValue().equalsIgnoreCase("EC") || getClientValue().equalsIgnoreCase("PA") || getClientValue().equalsIgnoreCase("EA")) 
						{
							query.append("SELECT ");
							if (getClientValue().equalsIgnoreCase("PC") || getClientValue().equalsIgnoreCase("EC")) 
							{
								query.append(" id,personName FROM client_contact_data WHERE clientName ='"+getContactName()+"' ORDER BY personName ASC");
							}
							else if (getClientValue().equalsIgnoreCase("PA") || getClientValue().equalsIgnoreCase("EA")) 
							{
								query.append(" id,name FROM associate_contact_data WHERE associateName ='"+getContactName()+"' ORDER BY name ASC");
							}
							data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						}
						else if (getClientValue().equalsIgnoreCase("IN"))
						{
							data2=new TaskRegistrationHelper().getAllotedToName("DREAM_HDM", userName,connectionSpace);
						}
						//System.out.println("QUERY IS AS getClientContactPersonName ==== "+query.toString());
						
						if (data2 != null && data2.size() > 0)
						{
							jsonArr=new JSONArray();
							JSONObject formDetailsJson = new JSONObject();
							Object[] object = null;
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									formDetailsJson = new JSONObject();
									formDetailsJson.put("ID", object[0].toString());
									formDetailsJson.put("NAME", object[1].toString());
									jsonArr.add(formDetailsJson);
								}
							}
						}
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
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
		
		@SuppressWarnings("rawtypes")
		public String getClientContactPersonNameCC()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (getClientValue() != null && !getClientValue().equalsIgnoreCase(""))
					{
						SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
						StringBuilder query = new StringBuilder();
						query.append("SELECT ");
						if (getClientValue().equalsIgnoreCase("PC") || getClientValue().equalsIgnoreCase("EC")) 
						{
							query.append(" id,personName FROM client_contact_data WHERE clientName ='"+getContactName()+"' AND id NOT IN("+getId()+") ORDER BY personName ASC");
						}
						else if (getClientValue().equalsIgnoreCase("PA") || getClientValue().equalsIgnoreCase("EA")) 
						{
							query.append(" id,name FROM associate_contact_data WHERE associateName ='"+getContactName()+"' AND id NOT IN("+getId()+") ORDER BY name ASC");
						}
						else if (getClientValue().equalsIgnoreCase("IN"))
						{
							String user = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
							query.append(" contact.id,emp.empName from employee_basic as emp ");
							query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
							query.append(" WHERE contact.forDept_id IN ( ");
							query.append("select emp.deptname as deptid from employee_basic as emp ");
							query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
							query.append(" inner join department as dept on emp.deptname=dept.id");
							query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='DREAM_HDM' and uac.userID='");
							query.append(user + "' and contact.forDept_id=dept.id");
							query.append(" ) AND moduleName= 'DREAM_HDM' AND contact.id NOT IN ("+getId()+")") ;
						}
						List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						ccPerson=new LinkedHashMap<String, String>();
						if (data2 != null && data2.size() > 0)
						{
							
							Object[] object = null;
							for (Iterator iterator = data2.iterator(); iterator.hasNext();)
							{
								object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									ccPerson.put(object[0].toString(), object[1].toString());
								}
							}
						}
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
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
		@SuppressWarnings("rawtypes")
		public String fetchTaskCompleteDetails()
		{
			if (ValidateSession.checkSession())
			{
				try
				{
					taskDetails=new LinkedHashMap<String, String>();
					StringBuilder query = new StringBuilder();
					List data2 =null;
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					query.append("SELECT task.taskname,task.specifictask,type.tasktype,task.priority,task.clientfor,task.cName,task.offering,emp.empName  ");
					query.append(" FROM task_registration AS task LEFT JOIN task_type AS type ON type.id=task.tasktype ");	
					query.append("  LEFT JOIN compliance_contacts AS cc ON cc.id=task.allotedby LEFT join employee_basic as emp on emp.id=cc.emp_id ");
					query.append(" where task.id='"+taskId+"' ");
					System.out.println(">>>> "+query.toString());
					data2=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2!=null && data2.size()>0)
					{
						DARReportHelper DRH=new DARReportHelper();
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0]!=null)
							{
								taskDetails.put("Task Name", object[0].toString());
							}
							if (object[1]!=null)
							{
								taskDetails.put("Specific Task", object[1].toString());
							}
							if (object[2]!=null)
							{
								taskDetails.put("Task Type", object[2].toString());
							}
							if (object[3]!=null)
							{
								taskDetails.put("Task Priority", object[3].toString());
							}
							if (object[4]!=null)
							{
								if (object[4].toString().equalsIgnoreCase("PC"))
								{
									taskDetails.put("For", "Prospect Client");
								}
								else if(object[4].toString().equalsIgnoreCase("EC"))
								{
									taskDetails.put("For", "Existing Client");
								}
								else if(object[4].toString().equalsIgnoreCase("PA"))
								{
									taskDetails.put("For", "Prospect Associate");
								}
								else if(object[4].toString().equalsIgnoreCase("EA"))
								{
									taskDetails.put("For", "Existing Associate");
								}
								else if(object[4].toString().equalsIgnoreCase("IN"))
								{
									taskDetails.put("For", "Internal");
								}
								else if(object[4].toString().equalsIgnoreCase("N"))
								{
									taskDetails.put("For", "Other");
								}
								else
								{
									taskDetails.put("For", "NA");
								}
							}
							if (object[5]!=null)
							{
								taskDetails.put("Name", DRH.clientName(object[4].toString(), object[5].toString(),connectionSpace));
							}
							if (object[6]!=null)
							{
								taskDetails.put("Offering", DRH.offeringName(object[4].toString(), object[5].toString(),object[6].toString(),connectionSpace));
							}
							if (object[7]!=null)
							{
								taskDetails.put("Alloted By", object[7].toString());
							}
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
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String allotTask()
		{
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					boolean mailStatus=false;
					boolean smsStatus=false;
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue=request.getParameter(parmName);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("taskId") && !parmName.equalsIgnoreCase("__checkbox_sms")&& !parmName.equalsIgnoreCase("__checkbox_mail"))
						{
							if (parmName.equalsIgnoreCase("intiation"))
							{
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
							}
							else if(parmName.equalsIgnoreCase("completion") || parmName.equalsIgnoreCase("functional_Date") || parmName.equalsIgnoreCase("technical_Date"))
							{
								String value[]=paramValue.split(" ");
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(value[0]) +" "+value[1]);
							}
							else if(parmName.equalsIgnoreCase("mail"))
							{
								mailStatus=true;
							}
							else if(parmName.equalsIgnoreCase("sms"))
							{
								smsStatus=true;
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					wherClause.put("allot_status", "1");
					condtnBlock.put("id",getTaskId());
					
					cbt.updateTableColomn("task_registration", wherClause, condtnBlock,connectionSpace);
					addActionMessage("Task Alloted Successfully !!!!!!!");
					StringBuilder fileName=new StringBuilder();
					if (mailStatus) 
					{
					  try
						{
						    String task=null;
						    String taskType=null;
							String nameAllotedby=null;
							String nameAllotedto=null;
							String nameGuidance=null;
							String nameAllotedbyemail=null;
							String nameAllotedtoemail=null;
							String nameGuidanceemail=null;
							String specificTask=null;
							String clientFor=null;
							String clientData=null;
							String offering=null;
							String priority=null;
							StringBuilder query= new StringBuilder();
							DARReportHelper DRH=new DARReportHelper();
							query .append("SELECT tr.taskname,ty.tasktype,emp1.empName as allotedto,emp1.emailIdOne as allotedtoEmail, ");
							query .append("emp2.empName as allotedby,emp2.emailIdOne as allotedbyEmail, ");
							query .append("emp5.empName as guidance ,emp5.emailIdOne as guidanceEmail,tr.specifictask,tr.clientfor,tr.cName,tr.offering,tr.attachment,tr.attachment1,tr.priority ");
							query .append("FROM task_registration AS tr ");
							query .append("LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id ");
							query .append("LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id ");
							query .append("LEFT JOIN compliance_contacts AS cc5 ON tr.guidance=cc5.id  ");
							query .append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
							query .append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
							query .append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
							query .append("LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
							query.append("WHERE tr.id='"+getTaskId()+"'");
							List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if (data!=null && data.size()>0) 
							{
								Object[] object=null;
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									object = (Object[]) iterator.next();
									task=object[0].toString();
									taskType=object[1].toString();
									nameAllotedby=object[4].toString();
									nameAllotedbyemail=object[5].toString();
									nameAllotedto=object[2].toString();
									nameAllotedtoemail=object[3].toString();
									nameGuidance=object[6].toString();
									nameGuidanceemail=object[7].toString();
									specificTask=object[8].toString();
									priority=object[14].toString();
									if (object[12]!=null)
									{
										fileName.append(object[12].toString()+",");
									}
									if (object[13]!=null)
									{
										fileName.append(object[12].toString()+",");
									}
									if (object[9].toString().equalsIgnoreCase("PA")) 
									{
										clientFor="Prospect Associate";
									}
									else if (object[9].toString().equalsIgnoreCase("PC")) 
									{
										clientFor="Prospect Client";
									}
									else if(object[9].toString().equalsIgnoreCase("N"))
									{
										clientFor="Other";
									}
									else if(object[9].toString().equalsIgnoreCase("EC"))
									{
										clientFor="Existing Client";
									}
									else if(object[9].toString().equalsIgnoreCase("EA"))
									{
										clientFor="Existing Associate";
									}
									else if(object[9].toString().equalsIgnoreCase("IN"))
									{
										clientFor="Internal";
									}
									clientData= DRH.clientName(object[9].toString(), object[10].toString(),connectionSpace);
								    offering= DRH.offeringName(object[9].toString(), object[10].toString(),object[11].toString(),connectionSpace);
								}
							 }
					         String text=null;
					         DarHelper DH=new DarHelper();
					         String Subject=null;
					         MsgMailCommunication MMC=new MsgMailCommunication();
						     String str="allotby";
						     Subject=" Project Acknowledgement Alert: "+task;
					         text=DH.getMailDetail(request,nameAllotedby,nameAllotedto,nameGuidance,str,taskType,task,specificTask,clientFor,clientData,offering,priority);
					         MMC.addMail("","",nameAllotedbyemail,Subject, text,"","Pending", "0",fileName.substring(0, fileName.length()-1).toString(),"DAR");
						
						     str="allotto";
						     Subject=" Project Allotment Alert: "+task;
						     text=DH.getMailDetail(request,nameAllotedby,nameAllotedto,nameGuidance,str,taskType,task,specificTask,clientFor,clientData,offering,priority);
						     MMC.addMail("","",nameAllotedtoemail,Subject, text,"","Pending", "0",fileName.substring(0, fileName.length()-1).toString(),"DAR");
					
						     str="under";
						     Subject=" Co-Owner: "+task;
						     text=DH.getMailDetail(request,nameAllotedby,nameAllotedto,nameGuidance,str,taskType,task,specificTask,clientFor,clientData,offering,priority);
						     MMC.addMail("","",nameGuidanceemail,Subject, text,"","Pending", "0",fileName.substring(0, fileName.length()-1).toString(),"DAR");
						}
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
					if(smsStatus)
					 {
						 try 
						 {
							    String task=null;
								String nameAllotedby=null;
								String nameAllotedto=null;
								String nameGuidance=null;
								String nameAllotedbyMob=null;
								String nameAllotedtoMob=null;
								String nameGuidanceMob=null;
								StringBuilder query= new StringBuilder();
								query .append("SELECT tr.taskname,emp1.empName as allotedto,emp1.mobOne as allotedtoMob, ");
								query .append("emp2.empName as allotedby,emp2.mobOne as allotedbyMob, ");
								query .append("emp5.empName as guidance ,emp5.mobOne as guidanceMob ");
								query .append("FROM task_registration AS tr ");
								query .append("LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id ");
								query .append("LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id ");
								query .append("LEFT JOIN compliance_contacts AS cc5 ON tr.guidance=cc5.id  ");
								query .append("LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
								query .append("LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id  ");
								query .append("LEFT JOIN employee_basic emp5 ON cc5.emp_id= emp5.id ");
								query.append("WHERE tr.id='"+getTaskId()+"'");
								List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
								if (data!=null && data.size()>0) 
								{
									Object[] object=null;
									for (Iterator iterator = data.iterator(); iterator.hasNext();)
									{
										object = (Object[]) iterator.next();
										task=object[0].toString();
										nameAllotedby=object[3].toString();
										nameAllotedbyMob=object[4].toString();
										nameAllotedto=object[1].toString();
										nameAllotedtoMob=object[2].toString();
										nameGuidance=object[5].toString();
										nameGuidanceMob=object[6].toString();
									}
								}
								String msgText=null;
								MsgMailCommunication MMC=new MsgMailCommunication();
								
								msgText = "Registration of Task :" + task + ", Alloted By : " + nameAllotedby + ", Co-Owner: " + nameGuidance + ".";
								MMC.addMessage("", "",nameAllotedtoMob, msgText, "", "Pending", "0", "DAR");
								msgText = "Registration of Task :" + task + ", Alloted To : " + nameAllotedto + ", Co-Owner: " + nameGuidance + ".";
								MMC.addMessage("", "",nameAllotedbyMob, msgText, "", "Pending", "0", "DAR");
								msgText = "Registration of Task :" + task + ", Alloted By : " + nameAllotedby + ", Alloted To: " + nameAllotedto + ".";
								MMC.addMessage("", "",nameGuidanceMob, msgText, "", "Pending", "0", "DAR");
						 } 
						 catch (Exception e) 
						 {
							e.printStackTrace();
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
		
		@SuppressWarnings("rawtypes")
		public String fromKR()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					deptName = new LinkedHashMap<String, String>();
					KRActionHelper KRH=new KRActionHelper();
					List data=null;
					String dept[]=KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
					if (dept!=null && !dept[4].equalsIgnoreCase(""))
					{
						data=KRH.getAssignedDepartment(connectionSpace,dept[4]);
						if (data!=null && data.size()>0) 
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();) 
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0]!=null && object[1]!=null) 
								{
									deptName.put(object[0].toString(), object[1].toString());
								}
							}
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
		public List<ConfigurationUtilBean> getTaskColumnMap() {
			return taskColumnMap;
		}
		public void setTaskColumnMap(List<ConfigurationUtilBean> taskColumnMap) {
			this.taskColumnMap = taskColumnMap;
		}
		public List<ConfigurationUtilBean> getTaskCalenderMap() {
			return taskCalenderMap;
		}
		public void setTaskCalenderMap(List<ConfigurationUtilBean> taskCalenderMap) {
			this.taskCalenderMap = taskCalenderMap;
		}
		public List<ConfigurationUtilBean> getTaskFileMap() {
			return taskFileMap;
		}
		public void setTaskFileMap(List<ConfigurationUtilBean> taskFileMap) {
			this.taskFileMap = taskFileMap;
		}
		public List<ConfigurationUtilBean> getTaskDropMap() {
			return taskDropMap;
		}
		public void setTaskDropMap(List<ConfigurationUtilBean> taskDropMap) {
			this.taskDropMap = taskDropMap;
		}
		@Override
		public void setServletRequest(HttpServletRequest request) {
					
			this.request=request;
		}
		public File getAttachment() {
			return attachment;
		}
		public void setAttachment(File attachment) {
			this.attachment = attachment;
		}
		public String getAttachmentContentType() {
			return attachmentContentType;
		}
		public void setAttachmentContentType(String attachmentContentType) {
			this.attachmentContentType = attachmentContentType;
		}
		public String getAttachmentFileName() {
			return attachmentFileName;
		}
		public void setAttachmentFileName(String attachmentFileName) {
			this.attachmentFileName = attachmentFileName;
		}
		public void setListtask(Map<Integer, String> listtask) {
			this.listtask = listtask;
		}

		public List<GridDataPropertyView> getTaskTypeViewProp() {
			return taskTypeViewProp;
		}
		
		public Map<Integer, String> getListemployee() {
			return listemployee;
		}
		public void setListemployee(Map<Integer, String> listemployee) {
			this.listemployee = listemployee;
		}
		public void setTaskTypeViewProp(List<GridDataPropertyView> taskTypeViewProp) {
			this.taskTypeViewProp = taskTypeViewProp;
		}
		public List<Object> getViewList() {
			return viewList;
		}
		public void setViewList(List<Object> viewList) {
			this.viewList = viewList;
		}
		public Map<String, String> getAttachFileMap() {
			return attachFileMap;
		}
		public void setAttachFileMap(Map<String, String> attachFileMap) {
			this.attachFileMap = attachFileMap;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public InputStream getFileInputStream() {
			return fileInputStream;
		}
		public void setFileInputStream(InputStream fileInputStream) {
			this.fileInputStream = fileInputStream;
		}

		public Map<Integer, String> getListtask() {
			return listtask;
		}

		public Map<Integer, String> getListAllotedemployee() {
			return listAllotedemployee;
		}

		public void setListAllotedemployee(Map<Integer, String> listAllotedemployee) {
			this.listAllotedemployee = listAllotedemployee;
		}

		public String getAllotedById() {
			return allotedById;
		}
		public void setAllotedById(String allotedById) {
			this.allotedById = allotedById;
		}
		public JSONArray getJsonArr() {
			return jsonArr;
		}
		public void setJsonArr(JSONArray jsonArr) {
			this.jsonArr = jsonArr;
		}
		public String getClientValue() {
			return clientValue;
		}
		public void setClientValue(String clientValue) {
			this.clientValue = clientValue;
		}
		public String getContactName() {
			return contactName;
		}
		public void setContactName(String contactName) {
			this.contactName = contactName;
		}
		public Map<String, String> getCcPerson()
		{
			return ccPerson;
		}
		public void setCcPerson(Map<String, String> ccPerson)
		{
			this.ccPerson = ccPerson;
		}

		public Map<String, String> getTaskDetails()
		{
			return taskDetails;
		}

		public void setTaskDetails(Map<String, String> taskDetails)
		{
			this.taskDetails = taskDetails;
		}

		public String getTaskId()
		{
			return taskId;
		}

		public void setTaskId(String taskId)
		{
			this.taskId = taskId;
		}

		

		

		

		


		

		

		public long getContentLength()
		{
			return contentLength;
		}

		public void setContentLength(long contentLength)
		{
			this.contentLength = contentLength;
		}

		public Map<String, String> getDeptName() {
			return deptName;
		}

		public void setDeptName(Map<String, String> deptName) {
			this.deptName = deptName;
		}

		public String getDataFor()
		{
			return dataFor;
		}

		public void setDataFor(String dataFor)
		{
			this.dataFor = dataFor;
		}
		
	}
