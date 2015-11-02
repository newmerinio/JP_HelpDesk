package com.Over2Cloud.ctrl.departmen;

import java.util.ArrayList;
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
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.CommonWork;
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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class DepartmentAction extends GridPropertyBean {

	static final Log log = LogFactory.getLog(DepartmentAction.class);
	private String level1=new String();
	private Map<Integer, String>deptList=new LinkedHashMap<Integer, String>();
	private String contact_subtype_name,contact_subtype_brief,deptName;
	private String selectedorgId;
	private List<GridDataPropertyView>level1ColmnNames=new ArrayList<GridDataPropertyView>();
	private HttpServletRequest request;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String searchFields;
	private String SearchValue;
	private Map<Integer, String> deptMap;
	private Map<String,String> totalCount;
	private List<ConfigurationUtilBean> departmentDropDown;
	private List<ConfigurationUtilBean> departmentTextBox;
	private Map<Integer,String> officeMap;
	private Map<Integer,String> groupMap;
	private boolean hodStatus;
	private boolean mgtStatus;
	
	public void setDeptAddPageFileds()
	{
		try
		{
			List<GridDataPropertyView> gridFields=Configuration.getConfigurationData("mapped_contact_sub_type_configuration",accountID,connectionSpace,"",0,"table_name","contact_sub_type_configuration");
			departmentDropDown=new ArrayList<ConfigurationUtilBean>();
			departmentTextBox=new ArrayList<ConfigurationUtilBean>();
			groupMap=new HashMap<Integer, String>();
			if(gridFields!=null)
			{
				for(GridDataPropertyView  gdp:gridFields)
				{
					ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("D") )
                    {
                    	objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        departmentDropDown.add(objdata);
                    
                        
                        if(gdp.getColomnName().equalsIgnoreCase("contact_type_id"))
                        {
                        	String userType = (String)session.get("userTpe");
                        	officeMap=new LinkedHashMap<Integer, String>();
                        	String query =null;
                        
                        	if (userType!=null && userType.equalsIgnoreCase("H")) 
                        	{
                        		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                        		hodStatus=true;
                        		query = "SELECT id,branch_name FROM branch_office WHERE head_id = '"+s[2]+"' ORDER BY branch_name";
							} 
                        	else if(userType!=null && userType.equalsIgnoreCase("M") || userType.equalsIgnoreCase("o"))
                        	{
                        	    mgtStatus = true;
                        	    hodStatus = true;
                        		query = "SELECT id,country_name FROM country_office ORDER BY country_name";
                        	}
                        	else 
                        	{
                        		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                        		query = "SELECT id,contact_name FROM contact_type WHERE mapped_level = '"+s[1]+"' ORDER BY contact_name";
							}
            				if (query!=null)
            				{
            					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
                				if (dataList != null && dataList.size() > 0)
                				{
                					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
                					{
                						Object[] object = (Object[]) iterator.next();
                						if (object[0] != null && object[1] != null)
                						{
                							officeMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
                						}
                					}
                				}
							}
                        }
                       
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time") && !gdp.getColomnName().equalsIgnoreCase("status"))
                    {
                        objdata.setKey(gdp.getColomnName());
                        objdata.setValue(gdp.getHeadingName());
                        objdata.setColType(gdp.getColType());
                        objdata.setValidationType(gdp.getValidationType());
                        if(gdp.getMandatroy()==null)
                        objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("0"))
                            objdata.setMandatory(false);
                        else if(gdp.getMandatroy().equalsIgnoreCase("1"))
                            objdata.setMandatory(true);
                        objdata.setData_type(gdp.getData_type());
						objdata.setField_length(gdp.getLength());
                        departmentTextBox.add(objdata);
                    }
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method setDepartmentAndSubDeptNames(int deptFalgOrSubDept) of class "+getClass(), e);
			e.printStackTrace();
		}
	
	}
	
	@SuppressWarnings("unused")
	public String checkDeptAvailability()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				String searchQry="select status from contact_sub_type where  contact_subtype_name like '"+getDeptName()+"%' ";
				List data=new createTable().executeAllSelectQuery(searchQry, connectionSpace);;
				if(data!=null && data.size()>0)
				{
					if(data.get(0).equals("Inactive")){
						addActionMessage("Contact Sub Type Already Exists InActive Mode plz Make It Active !!!");
					}else{
					addActionMessage("Contact Sub Type Already Exists !!!");
						}	
				}
				else
				{
					addActionMessage("Contact Sub Type Doesnot Exists !!!");
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
	
	public String beforeDeptAdd()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				setDeptAddPageFileds();
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
	
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public String createDepartment()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_contact_sub_type_configuration",accountID,connectionSpace,"",0,"table_name","contact_sub_type_configuration");
	    	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	    boolean userTrue=false;
                boolean dateTrue=false;
                boolean timeTrue=false;
                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
                for(GridDataPropertyView gdp:statusColName)
                {
                     if(gdp.getColomnName()!=null)
                     {
                    	 TableColumes ob1=new TableColumes();
                         ob1=new TableColumes();
                         ob1.setColumnname(gdp.getColomnName());
                         ob1.setDatatype(gdp.getData_type());
                         if(gdp.getColomnName().equalsIgnoreCase("status"))
                         {
                        	 ob1.setConstraint("default 'Active'");
                         }
                         else
                         {
                        	 ob1.setConstraint("default NULL");
                         }
                         Tablecolumesaaa.add(ob1);
                         if(gdp.getColomnName().equalsIgnoreCase("user_name"))
                             userTrue=true;
                         else if(gdp.getColomnName().equalsIgnoreCase("created_date"))
                             dateTrue=true;
                         else if(gdp.getColomnName().equalsIgnoreCase("created_time"))
                             timeTrue=true;
                     }
                }
                boolean exists=false;
                cbt.createTable22("contact_sub_type",Tablecolumesaaa,connectionSpace);
             
                List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
                InsertDataTable ob=null;
                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();
               
                String contact_type=null;
                while(it.hasNext())
                {
                	String Parmname = it.next().toString();
                    String paramValue=request.getParameter(Parmname);
                    if(Parmname.equalsIgnoreCase("contact_type_id"))
                    {
                    	contact_type=paramValue;
                    }
                }
                if(contact_type!=null)
                {
                	String arr[]=getContact_subtype_name().trim().split(",");
                	if(arr!=null )
                	{
                		for (int i = 0; i < arr.length; i++) 
                		{
							if(arr[i]!=null && !arr[i].equalsIgnoreCase(" "))
							{
								insertData=new ArrayList<InsertDataTable>();
								exists=new HelpdeskUniversalHelper().isExist("contact_sub_type", "contact_subtype_name", arr[i].trim(), connectionSpace);
								
								if(!exists)
								{
									String brief[]=getContact_subtype_brief().trim().split(",");
									
									ob=new InsertDataTable();
					                ob.setColName("contact_subtype_name");
					                ob.setDataName(arr[i].trim());
					                insertData.add(ob);
					                
					                if(brief!=null && brief[i]!=null && !brief[i].equalsIgnoreCase(""))
					                {
					                	ob=new InsertDataTable();
							            ob.setColName("contact_subtype_brief");
							            ob.setDataName(brief[i]);
							            insertData.add(ob);
					                }
						            
						            ob=new InsertDataTable();
						            ob.setColName("contact_type_id");
						            ob.setDataName(contact_type);
						            insertData.add(ob);
						                
						            if(userTrue)
						            {
						            	ob=new InsertDataTable();
						            	ob.setColName("user_name");
						            	ob.setDataName(userName);
						            	insertData.add(ob);
						            }
						            if(dateTrue)
						            {
						                    ob=new InsertDataTable();
						                    ob.setColName("created_date");
						                    ob.setDataName(DateUtil.getCurrentDateUSFormat());
						                    insertData.add(ob);
						            }
						            if(timeTrue)
						            {
						                    ob=new InsertDataTable();
						                    ob.setColName("created_time");
						                    ob.setDataName(DateUtil.getCurrentTimeHourMin());
						                    insertData.add(ob);
						            }
						            int maxId=cbt.insertDataReturnId("contact_sub_type",insertData,connectionSpace);
				                	if (maxId>0)
									{
				                		StringBuilder fieldsNames=new StringBuilder();
				                		StringBuilder fieldsValue=new StringBuilder();
				                		if (insertData!=null && !insertData.isEmpty())
										{
											int j=1;
											for (InsertDataTable h : insertData)
											{
												for(GridDataPropertyView gdp:statusColName)
												{
													if(h.getColName().equalsIgnoreCase(gdp.getColomnName()))
													{
														if (j < insertData.size())
														{
															fieldsNames.append(gdp.getHeadingName()+", ");
															if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
					                                    	{
																fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString())+", ");
					                                    	}
															else
															{
																fieldsValue.append(h.getDataName()+", ");
															}
														}
														else
														{
															fieldsNames.append(gdp.getHeadingName());
															if(h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
					                                    	{
																fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
					                                    	}
															else
															{
																fieldsValue.append(h.getDataName());
															}
														}
													}
												}
												j++;
											}
										}
				                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
				                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Contact Sub Type",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
									}
								}
								else
								{
									addActionMessage("Contact Sub Type "+ arr[i].trim()+" Already Exists !!!");
								}
							}
						}
                	}
                }
                if (!exists)
				{
                	 addActionMessage("Contact Sub Type  Added Successfully !!!");
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
		
		
		
		
		
		/*
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getLevel2org()!=null)
			{
				 String mappedorgId=new String();
				 try
				 {
						 if(getLevelOforganization()==2 && getLevel2org()!=null && !getLevel2org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel2org();
						 }
						 else if(getLevelOforganization()==3 && getLevel3org()!=null && !getLevel3org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel3org();
						 }
						 else if(getLevelOforganization()==4 && getLevel4org()!=null && !getLevel4org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel4org();
						 }
						 else if(getLevelOforganization()==5 && getLevel5org()!=null && !getLevel5org().equalsIgnoreCase("-1"))
						 {
							 mappedorgId=getLevel5org();
						 }
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				 boolean status=false;
				 if(mappedorgId!=null && !mappedorgId.equalsIgnoreCase(""))
				 {
					 CommonOperInterface cbt=new CommonConFactory().createInterface();
					 List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_dept_config_param",accountID,connectionSpace,"",0,"table_name","dept_config_param");
					    boolean userTrue=false;
						boolean dateTrue=false;
						boolean timeTrue=false;	
					        if(org2!=null)
						{
							//create table query based on configuration
							List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
							for(GridDataPropertyView gdp:org2)
							{
								 TableColumes ob1=new TableColumes();
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
							cbt.createTable22("department",Tablecolumesaaa,connectionSpace);
						}
						
						//getting the parameters nd setting their value using loop
						 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 InsertDataTable ob=null;
						 List paramList=new ArrayList<String>();
						 int paramValueSize=0;
						 boolean statusTemp=true;
						while (it.hasNext()) {
							String Parmname = it.next().toString();
							String paramValue[]=request.getParameterValues(Parmname);
						   if(paramValue!=null && Parmname!=null 
								   && Parmname.equalsIgnoreCase("orgnztnlevel"))
							{
							   setLevelOforganization(Integer.parseInt(paramValue[0]));
							   
							}
						   else if(paramValue!=null && Parmname!=null 
									 && !Parmname.equalsIgnoreCase("orgLevel")
									&& !Parmname.equalsIgnoreCase("levelOforganization")&& !Parmname.equalsIgnoreCase("level1")&& !Parmname.equalsIgnoreCase("level")&& !Parmname.equalsIgnoreCase("level2org")
									&& !Parmname.equalsIgnoreCase("level3org")&& !Parmname.equalsIgnoreCase("level4org")&& !Parmname.equalsIgnoreCase("level5org"))
								{
							   		//adding the parameters list.
							   		paramList.add(Parmname);
							   		if(statusTemp)
							   		{
								   		String tempParamValueSize[]=request.getParameterValues(Parmname);
								   		for(String H:tempParamValueSize)
								   		{
								   			//counting one time size of the parameter value
								   			if(!H.equalsIgnoreCase("") && H!=null)
								   				paramValueSize++;	
								   		}
								   		statusTemp=false;
							   		}
								}
						}
						String parmValuew[][]=new String[paramList.size()][paramValueSize];
						int m=0;
							for (Object c : paramList) {
								Object Parmname = (Object) c;
								String paramValue[]=request.getParameterValues(Parmname.toString());
								for(int j=0;j<paramValueSize;j++)
								{
									if(!paramValue[j].equalsIgnoreCase("") && paramValue[j]!=null)
									{
										parmValuew[m][j]=paramValue[j];
									}
								}
								m++;
							}
						
						for(int i=0;i<paramValueSize;i++)
						{
							boolean deptblank=true;
							boolean depExists=false;
							List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
							for(int k=0;k<paramList.size();k++)
							{
								if(paramList.get(k).toString().equalsIgnoreCase("deptName"))
								{
									if(parmValuew[k][i].toString()==null || !parmValuew[k][i].toString().equalsIgnoreCase(""))
									{
										deptblank=false;
										depExists=new HelpdeskUniversalHelper().isExist("department", "deptName", parmValuew[k][i], "", "", "","","","", connectionSpace);
									}
								}
								 ob=new InsertDataTable();
								 ob.setColName(paramList.get(k).toString());
								 ob.setDataName(DateUtil.makeTitle(parmValuew[k][i]));
								 insertData.add(ob);
							}
							 ob=new InsertDataTable();
							 ob.setColName("mappedOrgnztnId");
							 ob.setDataName(mappedorgId);
							 insertData.add(ob);
							 ob=new InsertDataTable();
							 ob.setColName("orgnztnlevel");
							 ob.setDataName(getLevelOforganization());
							 insertData.add(ob);
							 
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
							 if(!deptblank)
							 {
								 if(!depExists)
								 {
									 status=cbt.insertIntoTable("department",insertData,connectionSpace);
									 addActionMessage("Department Registered Successfully!!!");
									 return SUCCESS;
								 }
								 else
								 {
									 addActionMessage("Department Already Exists!!!");
									 return SUCCESS;
								 }
								 
							 }
						}
				 }
				 if(status)
				 {
					 addActionMessage("Department Registered Successfully!!!");
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
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createDepartment of class "+getClass(), e);
			e.printStackTrace();
			 addActionError("Ooops There Is Some Problem In Department Creation!");
			 return ERROR;
		}
		return SUCCESS;
	*/
		
	}
	
	/**
	 * Ajax calling for getting the list of all dept based on the selected level of organization and the organization ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDeptData()
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List lowerLevel3=new ArrayList<String>();
			lowerLevel3.add("id");
			lowerLevel3.add("contact_subtype_name");
			Map<String, Object> temp=new HashMap<String, Object>();
			Map<String, Object> order=new HashMap<String, Object>();
			
			order.put("contact_subtype_name", "ASC");
			lowerLevel3=cbt.viewAllDataEitherSelectOrAllByOrder("contact_sub_type",lowerLevel3,temp,order,connectionSpace);
			if(temp!=null && temp.size()>0)
			{
				for (Object c : lowerLevel3) {
					Object[] dataC = (Object[]) c;
					deptList.put((Integer)dataC[0], dataC[1].toString());
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getDeptData of class "+getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String beforeDepartmentView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setGridColomnNames();
			setDepartmentAndSubDeptNames();
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeDepartmentView of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeDepartmentModify()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setGridColomnNames();
			setDepartmentAndSubDeptNames();
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeDepartmentModify of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public void setDepartmentAndSubDeptNames()
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
				@SuppressWarnings("unused")
				String namesofDepts[]=new String[3];
				StringBuilder query=new StringBuilder("");
						query.append("select levelName from "+"mapped_dept_level_config");
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(data!=null)
						{
							String names=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object obdata=(Object)it.next();
								if(obdata!=null)
									names=obdata.toString();
									
								
							}
							namesofDepts=names.split("#");
						}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setGridColomnNames()
	{
		
		//id,deptname,orgnztnlevel,mappedOrgnztnId,userName,date,time
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Registration Id");
		gpv.setHideOrShow("true");
		level1ColmnNames.add(gpv);
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_contact_sub_type_configuration",accountID,connectionSpace,"",0,"table_name","contact_sub_type_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setWidth(gdp.getWidth());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			level1ColmnNames.add(gpv);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String beforeDepartmentViewHeader() 
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				groupMap=new LinkedHashMap<Integer, String>();
				deptMap=new LinkedHashMap<Integer, String>();
				String userType = (String)session.get("userTpe");
            	String query =null;
            	
            	if (userType!=null && userType.equalsIgnoreCase("H")) 
            	{
            		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
            		hodStatus=true;
            		query = "SELECT id,branch_name FROM branch_office WHERE head_id = '"+s[2]+"' ORDER BY branch_name";
				} 
            	else if(userType!=null && (userType.equalsIgnoreCase("M")|| userType.equalsIgnoreCase("o")))
            	{
            	    mgtStatus = true;
            	    hodStatus = true;
            		query = "SELECT id,country_name FROM country_office ORDER BY country_name";
            	}
            	else 
            	{
            		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
            		List data=cbt.executeAllSelectQuery("SELECT id ,contact_name FROM contact_type WHERE status='Active' AND mapped_level="+s[1]+" ORDER BY contact_name ASC", connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}
						}
					}
				}
				if (query!=null)
				{
					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
    				if (dataList != null && dataList.size() > 0)
    				{
    					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
    					{
    						Object[] object = (Object[]) iterator.next();
    						if (object[0] != null && object[1] != null)
    						{
    							deptMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
    						}
    					}
    				}
				}
				String userName = (String) session.get("empName");
				totalCount=new LinkedHashMap<String, String>();
				totalCount=getToatalContactsTypeCounters(userType,userName, connectionSpace);
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class "
								+ getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public Map<String,String> getToatalContactsTypeCounters(String userType,String username,SessionFactory connectionSpace)
	{
		Map<String,String> counter=new LinkedHashMap<String, String>();
		
		StringBuilder builder=new StringBuilder("select count(*),status from contact_sub_type where id!='0'");
		int total =0;
		if(userType!=null && userType.equalsIgnoreCase("N"))
		{
			builder.append( " AND contct.userName='"+username+"'  ");
		}
		if(userType!=null && userType.equalsIgnoreCase("H"))
		{
			String EmpData=new DeptViewAction().getEmpDataByUserName(userName);
			 if (EmpData!=null && !EmpData.equalsIgnoreCase("")) 
			 {
				 builder.append(" and contact_sub_type.userName IN "+EmpData.replace("[", "(").replace("]", ")")+"");
			 }
		}
		builder.append(" GROUP BY status");
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0]!=null && object[1]!=null)
				{
					total=total+Integer.parseInt(object[0].toString());
					counter.put( object[1].toString(),object[0].toString());
				}
			}
			counter.put("Total Records", String.valueOf(total));
		}
		return counter;
	}
	
	/*public String getContactsTypeCounters(SessionFactory connectionSpace)
	{
		String counter=null;
		
		StringBuilder builder=new StringBuilder(" select count(*),deptName from department where flag=0 group by deptName ");
		System.out.println(builder);
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			counter=dataList.get(0).toString();
		}
		return counter;
	}*/
	
	public String getTotalCounters(String userType,String username,SessionFactory connectionSpace)
	{
		String counter=null;
		
		StringBuilder builder=new StringBuilder(" Select count(*) from employee_basic  AS contct where flag=0  ");
		if(userType!=null && userType.equalsIgnoreCase("M"))
		{
			builder.append(" AND contct.regLevel=1");
		}
		if(userType!=null && userType.equalsIgnoreCase("N"))
		{
			builder.append( " AND contct.userName='"+username+"'  ");
		}
		
		if(userType!=null && userType.equalsIgnoreCase("H"))
		{
			String deptId[]=new ComplianceUniversalAction().getEmpDataByUserName(userName);
		 if (deptId!=null && !deptId.toString().equalsIgnoreCase("")) 
		 {
			 builder.append(" AND contct.deptname='"+deptId[3]+"'  ");
			 builder.append(" AND contct.regLevel='1' ");
		 }
		}
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			counter=dataList.get(0).toString();
		}
		return counter;
	}
	
	
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	
	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public String getSelectedorgId() {
		return selectedorgId;
	}

	public void setSelectedorgId(String selectedorgId) {
		this.selectedorgId = selectedorgId;
	}

	public List<GridDataPropertyView> getLevel1ColmnNames() {
		return level1ColmnNames;
	}


	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames) {
		this.level1ColmnNames = level1ColmnNames;
	}

	public List<ConfigurationUtilBean> getDepartmentDropDown() {
		return departmentDropDown;
	}
	public void setDepartmentDropDown(List<ConfigurationUtilBean> departmentDropDown) {
		this.departmentDropDown = departmentDropDown;
	}

	public List<ConfigurationUtilBean> getDepartmentTextBox() {
		return departmentTextBox;
	}

	public void setDepartmentTextBox(List<ConfigurationUtilBean> departmentTextBox) {
		this.departmentTextBox = departmentTextBox;
	}
	public Map<Integer, String> getOfficeMap() {
		return officeMap;
	}

	public void setOfficeMap(Map<Integer, String> officeMap) {
		this.officeMap = officeMap;
	}

	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

	public String getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String searchFields) {
		this.searchFields = searchFields;
	}

	public String getSearchValue() {
		return SearchValue;
	}

	public void setSearchValue(String searchValue) {
		SearchValue = searchValue;
	}

	public Map<Integer, String> getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap) {
		this.deptMap = deptMap;
	}



	public Map<String, String> getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Map<String, String> totalCount)
	{
		this.totalCount = totalCount;
	}

	

	public String getContact_subtype_name() {
		return contact_subtype_name;
	}

	public void setContact_subtype_name(String contactSubtypeName) {
		contact_subtype_name = contactSubtypeName;
	}

	public String getContact_subtype_brief() {
		return contact_subtype_brief;
	}

	public void setContact_subtype_brief(String contactSubtypeBrief) {
		contact_subtype_brief = contactSubtypeBrief;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public boolean isHodStatus() {
		return hodStatus;
	}

	public void setHodStatus(boolean hodStatus) {
		this.hodStatus = hodStatus;
	}

	public boolean isMgtStatus() {
		return mgtStatus;
	}

	public void setMgtStatus(boolean mgtStatus) {
		this.mgtStatus = mgtStatus;
	}
	
	
}
