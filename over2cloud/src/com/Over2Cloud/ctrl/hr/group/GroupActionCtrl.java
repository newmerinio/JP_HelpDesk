package com.Over2Cloud.ctrl.hr.group;

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

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
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


@SuppressWarnings("serial")
public class GroupActionCtrl extends GridPropertyBean implements ServletRequestAware
{
	private List<GridDataPropertyView>masterViewProp=new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> groupTextBox=null;
	private List<ConfigurationUtilBean> contactDD=null;
	private HttpServletRequest request;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<Integer, String> groupMap;
	private String fieldName;
	private String fieldvalue;
	private Map<String, String> totalCount;
	private Map<String,String> officeMap=null;
    private boolean loadonce = false;
    //Grid colomn view
    private String oper;
    private String id;
    private List<Object> masterViewList;
	private String selectedorgId;
	private String contact;
	private boolean  mgtStatus=false;
	private boolean  hodStatus=false;
	private String regLevel;
	private String headOffice;
	private String countryOff;
	private String mapped_level;
	    
	@SuppressWarnings({  "unchecked" })
	public String addGroup()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
	    		String accountID=(String)session.get("accountid");
	    	    List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_contact_type_configuration",accountID,connectionSpace,"",0,"table_name","contact_type_configuration");
	    	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	    
	    	    boolean userTrue=false;
                boolean dateTrue=false;
                boolean timeTrue=false;
                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
                for(GridDataPropertyView gdp:statusColName)
                {
                     TableColumes ob1=new TableColumes();
                     ob1=new TableColumes();
                     ob1.setColumnname(gdp.getColomnName());
                     ob1.setDatatype(gdp.getData_type());
                     if (gdp.getColomnName().equalsIgnoreCase("status")) 
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
	    	    
                cbt.createTable22("contact_type",Tablecolumesaaa,connectionSpace);
                String mappedId=null,dataFor=null;
                if(countryOff!=null && !countryOff.equalsIgnoreCase("-1") && !countryOff.equalsIgnoreCase(""))
				{
					mappedId = countryOff;
					dataFor ="CO";
				}
				if(headOffice!=null && !headOffice.equalsIgnoreCase("-1")&& !headOffice.equalsIgnoreCase(""))
				{
					mappedId = headOffice;
					dataFor ="HO";
				}
				if(mapped_level!=null && !mapped_level.equalsIgnoreCase("-1") && !mapped_level.equalsIgnoreCase(""))
				{
					mappedId = mapped_level;
					dataFor ="BO";
				}
				
                List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
                InsertDataTable ob=null;
                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();
                while(it.hasNext())
                {
                	String Parmname = it.next().toString();
                    String paramValue=request.getParameter(Parmname);
                    if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null && !Parmname.equalsIgnoreCase("mapped_level") && !Parmname.equalsIgnoreCase("countryOff") && !Parmname.equalsIgnoreCase("headOffice"))
                    {
                    	ob=new InsertDataTable();
                    	ob.setColName(Parmname);
                        ob.setDataName(paramValue);
                        insertData.add(ob);
                    }
                }
                ob=new InsertDataTable();
                ob.setColName("mapped_level");
                ob.setDataName(mappedId);
                insertData.add(ob);
                
                ob=new InsertDataTable();
                ob.setColName("mapped_heirarchy");
                ob.setDataName(dataFor);
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
                List data=cbt.executeAllSelectQuery("SELECT status FROM contact_type WHERE contact_name='"+request.getParameter("contact_name")+"' and mapped_level='"+mappedId+"'", connectionSpace);
                if (data!=null && data.size()>0) 
                {
                	if(data.get(0).equals("Inactive"))
                	{
                		addActionMessage("Group Already Exist in InActive Mode, Plz Make it Active !!!");
                	}else
                		addActionMessage("Group Already Exist !!!");
                }
                else 
                {
                	int maxId=cbt.insertDataReturnId("contact_type",insertData,connectionSpace);
                	if (maxId>0)
					{
                		StringBuilder fieldsNames=new StringBuilder();
                		StringBuilder fieldsValue=new StringBuilder();
                		if (insertData!=null && !insertData.isEmpty())
						{
							int i=1;
							for (InsertDataTable h : insertData)
							{
								for(GridDataPropertyView gdp:statusColName)
								{
									if(h.getColName().equalsIgnoreCase(gdp.getColomnName()))
									{
										if (i < insertData.size())
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
								i++;
							}
						}
                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Contact Type",fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
					}
                    addActionMessage("Contact Type Added Successfully !!!");
                }
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				addActionError("Oop's there is some problem in adding Group ");
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String beforeAdd()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				setGroupAddPageDataFields();
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	public void setGroupAddPageDataFields()
    {
    	try
    	{
    		String accountID=(String)session.get("accountid");
    	    List<GridDataPropertyView>offeringLevel1=Configuration.getConfigurationData("mapped_contact_type_configuration",accountID,connectionSpace,"",0,"table_name","contact_type_configuration");
            if(offeringLevel1!=null)
            {
            	groupTextBox=new ArrayList<ConfigurationUtilBean>();
            	contactDD=new ArrayList<ConfigurationUtilBean>();
                for(GridDataPropertyView  gdp:offeringLevel1)
                {
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
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
                        groupTextBox.add(objdata);
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("mapped_heirarchy") )
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
                        contactDD.add(objdata);
                        
                        officeMap=new LinkedHashMap<String, String>();
                        if(gdp.getColomnName().equalsIgnoreCase("mapped_level"))
                        {
                        	String userType = (String)session.get("userTpe");
                        	String query =null;
                        	
                        	if (userType!=null && userType.equalsIgnoreCase("H") ) 
                        	{
                        		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                        		hodStatus=true;
                        		query = "SELECT id,branch_name FROM branch_office WHERE head_id = '"+s[2]+"' ORDER BY branch_name";
							} 
                        	else if(userType!=null && (userType.equalsIgnoreCase("M") || userType.equalsIgnoreCase("o") ))
                        	{
                        	    mgtStatus = true;
                        	    hodStatus = true;
                        		query = "SELECT id,country_name FROM country_office ORDER BY country_name";
                        	}
                        	else 
                        	{
                        		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                        		regLevel = s[1];
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
                							officeMap.put(object[0].toString(), object[1].toString());
                						}
                					}
                				}
							}
                        }
                    }
                }
            }
    	}
    	catch (Exception e) {
    	e.printStackTrace();
	}
    }
	public String beforeGroupView()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				setGroupViewProp();
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	 public void setGroupViewProp()
	    {
	    	try
	    	{
	            GridDataPropertyView gpv=new GridDataPropertyView();
	            gpv.setColomnName("id");
	            gpv.setHeadingName("Id");
	            gpv.setHideOrShow("true");
	            masterViewProp.add(gpv);
	            
	           /* gpv=new GridDataPropertyView();
                gpv.setColomnName("country");
                gpv.setHeadingName("Country Office");
                gpv.setEditable("false");
                gpv.setSearch("false");
                gpv.setHideOrShow("false");
                gpv.setWidth(80);
                masterViewProp.add(gpv);
	            
                gpv=new GridDataPropertyView();
                gpv.setColomnName("head");
                gpv.setHeadingName("Head Office");
                gpv.setEditable("false");
                gpv.setSearch("false");
                gpv.setHideOrShow("false");
                gpv.setWidth(80);
                masterViewProp.add(gpv);
	            */
	            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_contact_type_configuration",accountID,connectionSpace,"",0,"table_name","contact_type_configuration");
	            for(GridDataPropertyView gdp:returnResult)
	            {
            	    gpv=new GridDataPropertyView();
                    gpv.setColomnName(gdp.getColomnName());
                    gpv.setHeadingName(gdp.getHeadingName());
                    gpv.setEditable(gdp.getEditable());
                    gpv.setSearch(gdp.getSearch());
                    gpv.setHideOrShow(gdp.getHideOrShow());
                    gpv.setFormatter(gdp.getFormatter());
                    gpv.setWidth(gdp.getWidth());
                    masterViewProp.add(gpv);
	            }
	    	}
	    	catch (Exception e) {
	    	e.printStackTrace();
		}
	 }
	 
	@SuppressWarnings("unchecked")
	public String groupViewInGrid()
	 {
		 boolean validFlag=ValidateSession.checkSession();
		 if(validFlag)
		 {
			 try
			 {
					 CommonOperInterface cbt=new CommonConFactory().createInterface();
					 String accountID=(String)session.get("accountid");
					 List  dataCount=cbt.executeAllSelectQuery("select count(*) from contact_type",connectionSpace);
	
					 if(dataCount!=null && dataCount.size()>0)
					 {
						 BigInteger count=new BigInteger("3");
			                for(Iterator it=dataCount.iterator(); it.hasNext();)
			                {
			                     Object obdata=(Object)it.next();
			                     count=(BigInteger)obdata;
			                }
			                setRecords(count.intValue());
			                int to = (getRows() * getPage());
			                if (to > getRecords())
			                    to = getRecords();
						 
					 }
				    StringBuilder query=new StringBuilder(" SELECT ");
				    List fieldNames=Configuration.getColomnList("mapped_contact_type_configuration", accountID, connectionSpace, "contact_type_configuration");
				    List<Object> listhb=new ArrayList<Object>();
	                int i=0;
	                for(Iterator it=fieldNames.iterator(); it.hasNext();)
	                {
	                    //generating the dyanamic query based on selected fields
	                        Object obdata=(Object)it.next();
	                        if(obdata!=null)
	                        {
	                            if(i<fieldNames.size()-1)
									query.append("gp."+obdata.toString()+",");
	                            else
	                                query.append("gp."+obdata.toString());
	                        }
	                        i++;
	                }
	                query.append(" from contact_type  as gp  ");
	                if(getFieldName()!=null && !getFieldName().equalsIgnoreCase("-1") && getFieldvalue()!=null && !getFieldvalue().equalsIgnoreCase("-1") )
					{
	                	query.append(" WHERE ");
	                	if (!getFieldName().equalsIgnoreCase("status"))
						{
	                		query.append(" gp. "+getFieldName()+" = '"+getFieldvalue()+"' AND  gp.status='Active'");
						}
						else 
						{
							query.append(" gp.  "+getFieldName()+" = '"+getFieldvalue()+"' ");
						}
					}
	                if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	                {
	                	if(getSearchField().matches("de_active_on")||getSearchField().matches("created_date"))
                    	{
                    		setSearchString(DateUtil.convertDateToIndianFormat(getSearchString().toString()) );
                    	}
	                    query.append(" AND  ");
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
	                query.append(" ORDER BY contact_name DESC ");
	                List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	                if(data!=null && data.size()>0)
	                {
	                	String mappedOffice=null;
	                	Collections.reverse(data);
	                    for(Iterator it=data.iterator(); it.hasNext();)
	                    {
	                        Object[] obdata=(Object[])it.next();
	                        Map<String, Object> one=new HashMap<String, Object>();
	                        int j=0;
	                        for (int k = 0; k < fieldNames.size(); k++) 
	                        {
	                            if(obdata[j]!=null)
	                            {
	                                if(k==0)
	                                {
	                                    one.put(fieldNames.get(k).toString(), (Integer)obdata[j]);
	                                }
	                                else
	                                {
	                                	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("mapped_heirarchy"))
	                                	{
	                                		mappedOffice = obdata[j].toString();
	                                		if(mappedOffice!=null && mappedOffice.equalsIgnoreCase("CO"))
                                    		{
                                    			one.put(fieldNames.get(k).toString(),"Country Office");
                                    		}
                                    		else if(mappedOffice!=null && mappedOffice.equalsIgnoreCase("HO"))
                                    		{
                                    			one.put(fieldNames.get(k).toString(),"Head Office");
                                    		}
                                    		else if(mappedOffice!=null && mappedOffice.equalsIgnoreCase("BO"))
                                    		{
                                    			one.put(fieldNames.get(k).toString(),"Branch Office");
                                    		}
	                                	}
	                                	else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("mapped_level"))
	                                	{
	                                		String query11 = null;
	                                		if(mappedOffice!=null && mappedOffice.equalsIgnoreCase("CO"))
	                                		{
	                                			 query11 = "SELECT country_name FROM country_office WHERE id = "+ obdata[j].toString();
	                                		}
	                                		else if(mappedOffice!=null && mappedOffice.equalsIgnoreCase("HO"))
	                                		{
	                               			 	query11 = "SELECT head_name FROM head_office WHERE id = "+ obdata[j].toString();
	                                		}
	                                		else if(mappedOffice!=null && mappedOffice.equalsIgnoreCase("BO"))
	                                		{
	                               			 	query11 = "SELECT  branch_name FROM branch_office WHERE id = "+ obdata[j].toString();
	                                		}
	                                		if(query11!=null)
	                                		{
	                                			List dataList = cbt.executeAllSelectQuery(query11, connectionSpace);
	                                    		if(dataList!=null && dataList.size()>0)
	                                    		{
	                                    			one.put(fieldNames.get(k).toString(),dataList.get(0).toString());
	                                    		}
	                                		}
	                                	}
	                                	else if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
                                    	{
                                    		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[j].toString()) );
                                    	}
	                                	else
	                                	{
	                                		one.put(fieldNames.get(k).toString(), obdata[j].toString());
	                                	}
	                                }
	                            }
	                            else
	                            {
	                            	one.put(fieldNames.get(k).toString(),"NA");
	                            }
	                            j++;
	                        }
	                        listhb.add(one);
	                    }
	                    setMasterViewList(listhb);
	                    setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	                
	                }
				 return "success";
			 }
			 catch (Exception e) {
				 e.printStackTrace();
				 return "error";
			}
		 }
		 else
		 {
			 return "login";
		 }
	 }

		@SuppressWarnings("unchecked")
		public String checkContactTypeView()
		{
			
			boolean validFlag=ValidateSession.checkSession();
			if(validFlag)
			{
				try
				{
					String searchQry="select status from contact_type where mapped_level='"+getSelectedorgId()+"' and contact_name like '"+contact+"%' ";
					List data=new createTable().executeAllSelectQuery(searchQry, connectionSpace);
					if(data!=null && data.size()>0)
					{
						if(data.get(0).equals("Inactive"))
						{
							addActionMessage("Contact Type Alredy Exists In In Active Mode, Plz Make it Active  !!!");
						}
						else
						addActionMessage("Contact Type Already Exists  !!!");
					}
					else
					{
						addActionMessage("Contact Type Not Exists !!!");
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

		@SuppressWarnings("rawtypes")
		public String beforeGroupViewheader() 
		{
			boolean validFlag = ValidateSession.checkSession();
			
			if (validFlag)
			{
				try
				{
					groupMap=new LinkedHashMap<Integer, String>();
					
					String userType = (String)session.get("userTpe");
                	officeMap=new LinkedHashMap<String, String>();
                	String query =null;
                	
                	if (userType!=null && userType.equalsIgnoreCase("H")) 
                	{
                		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
                		hodStatus=true;
                		query = "SELECT id,name FROM branchoffice_data WHERE headOfficeId = '"+s[2]+"' ORDER BY name";
					} 
                	else if(userType!=null && (userType.equalsIgnoreCase("M")|| userType.equalsIgnoreCase("o")))
                	{
                	    mgtStatus = true;
                	    hodStatus = true;
                		query = "SELECT id,country_name FROM country_office WHERE status='Active' ORDER BY country_name ";
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
        							officeMap.put(object[0].toString(), object[1].toString());
        						}
        					}
        				}
					}
					totalCount=new LinkedHashMap<String, String>();
					totalCount=fetchContactTypeCounters(connectionSpace,"status","contact_type");
					return SUCCESS;
				}
				catch (Exception e)
				{
					
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
		@SuppressWarnings("rawtypes")
		public Map<String,String> fetchContactTypeCounters(SessionFactory connectionSpace,String colName,String tableName)
		{
			Map<String,String> data=new LinkedHashMap<String, String>();
			try
			{
				int total =0;
				StringBuilder builder=new StringBuilder("select count(*),"+colName+" from "+tableName+" where id!='0' GROUP BY "+colName+" ORDER BY "+colName+"");
				List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && object[1]!=null)
						{
							total=total+Integer.parseInt(object[0].toString());
							data.put( object[1].toString(),object[0].toString());
						}
					}
					data.put("Total Records", String.valueOf(total));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String viewModifyGroup()
		{
			String returnValue = ERROR;
			if (ValidateSession.checkSession())
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
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (paramValue != null && Parmname != null&& !Parmname.equalsIgnoreCase("tableName")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
							{
								wherClause.put(Parmname, paramValue);
								if (Parmname.equalsIgnoreCase("status"))
								{
									wherClause.put("de_active_on",DateUtil.getCurrentDateUSFormat());
								}
							}
						}
						condtnBlock.put("id", getId());
						cbt.updateTableColomn("contact_type", wherClause,condtnBlock, connectionSpace);

                		StringBuilder fieldsNames=new StringBuilder();
                		StringBuilder dataStore=new StringBuilder();
                		StringBuilder dataField=new StringBuilder();
                		if (wherClause!=null && !wherClause.isEmpty())
						{
							int i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
							    if (i < wherClause.size())
									fieldsNames.append("'"+entry.getKey() + "', ");
								else
									fieldsNames.append("'"+entry.getKey() + "' ");
								i++;
							}
						}
                		UserHistoryAction UA=new UserHistoryAction();
                		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"contact_type_configuration");
                		if (fieldValue!=null && fieldValue.size()>0)
						{
                			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (wherClause!=null && !wherClause.isEmpty())
								{
									int i=1;
									for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
									{
										if (object[1].toString().equalsIgnoreCase(entry.getKey()))
										{
											  if (i < fieldValue.size())
											  {
												  dataStore.append(entry.getValue() + ", ");
											      dataField.append(object[0].toString() +", ");
											  }
											  else
											  {
												  dataStore.append(entry.getValue());
											      dataField.append(object[0].toString());
											  }
										}
										i++;
									}
								}
							}
                			
                			
                			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
                			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "Contact Type",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
						}
                		
					
					}
				    else if (getOper().equalsIgnoreCase("del"))
				   {
						 CommonOperInterface cbt = new CommonConFactory().createInterface(); 
						 String tempIds[] = getId().split(",");
						 StringBuilder condtIds = new StringBuilder(); 
						 Map<String, Object> wherClause = new HashMap<String, Object>();
						 Map<String, Object> condtnBlock = new HashMap<String, Object>();
						 int i = 1; 
						 for(String H : tempIds) 
						 { 
							 if (i < tempIds.length)
						          condtIds.append(H + " ,"); 
							 else 
								  condtIds.append(H); 
							 i++; 
						 }
						 wherClause.put("status","Inactive");
						 wherClause.put("de_active_on",DateUtil.getCurrentDateUSFormat());
						 condtnBlock.put("id", condtIds.toString());
						 cbt.updateTableColomn("contact_type", wherClause,condtnBlock, connectionSpace);
						 StringBuilder fieldsNames=new StringBuilder();
						 StringBuilder dataStore=new StringBuilder();
	                		StringBuilder dataField=new StringBuilder();
	                		if (wherClause!=null && !wherClause.isEmpty())
							{
								 i=1;
								for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
								{
								    if (i < wherClause.size())
										fieldsNames.append("'"+entry.getKey() + "', ");
									else
										fieldsNames.append("'"+entry.getKey() + "' ");
									i++;
								}
							}
	                		UserHistoryAction UA=new UserHistoryAction();
	                		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"contact_type_configuration");
	                		if (fieldValue!=null && fieldValue.size()>0)
							{
	                			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (wherClause!=null && !wherClause.isEmpty())
									{
										 i=1;
										for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
										{
											if (object[1].toString().equalsIgnoreCase(entry.getKey()))
											{
												  if (i < fieldValue.size())
												  {
													  dataStore.append(entry.getValue() + ", ");
												      dataField.append(object[0].toString() +", ");
												  }
												  else
												  {
													  dataStore.append(entry.getValue());
												      dataField.append(object[0].toString());
												  }
											}
											i++;
										}
									}
								}
	                		String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
	                		new UserHistoryAction().userHistoryAdd(empIdofuser, "Inactive", "Admin", "Contact Type", dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
						}
				   }
					returnValue = SUCCESS;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					returnValue = ERROR;
				}
			}
			else
			{
				returnValue = LOGIN;
			}
			return returnValue;
		}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public List<ConfigurationUtilBean> getGroupTextBox() {
		return groupTextBox;
	}
	public void setGroupTextBox(List<ConfigurationUtilBean> groupTextBox) {
		this.groupTextBox = groupTextBox;
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
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	public List<ConfigurationUtilBean> getContactDD() {
		return contactDD;
	}

	public void setContactDD(List<ConfigurationUtilBean> contactDD) {
		this.contactDD = contactDD;
	}
	public Map<String, String> getOfficeMap() {
		return officeMap;
	}

	public void setOfficeMap(Map<String, String> officeMap) {
		this.officeMap = officeMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}

	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public String getSelectedorgId()
	{
		return selectedorgId;
	}

	public void setSelectedorgId(String selectedorgId)
	{
		this.selectedorgId = selectedorgId;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

	public Map<String, String> getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Map<String, String> totalCount)
	{
		this.totalCount = totalCount;
	}


	public String getRegLevel() {
		return regLevel;
	}

	public void setRegLevel(String regLevel) {
		this.regLevel = regLevel;
	}

	public boolean isMgtStatus() {
		return mgtStatus;
	}

	public void setMgtStatus(boolean mgtStatus) {
		this.mgtStatus = mgtStatus;
	}

	public boolean isHodStatus() {
		return hodStatus;
	}

	public void setHodStatus(boolean hodStatus) {
		this.hodStatus = hodStatus;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

	public String getHeadOffice() {
		return headOffice;
	}

	public void setCountryOff(String countryOff) {
		this.countryOff = countryOff;
	}

	public String getCountryOff() {
		return countryOff;
	}

	public void setMapped_level(String mapped_level) {
		this.mapped_level = mapped_level;
	}

	public String getMapped_level() {
		return mapped_level;
	}
	 
}
