package com.Over2Cloud.ctrl.wfpm.locationMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
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

@SuppressWarnings("serial")
public class EmployeeLocationAction extends GridPropertyBean
{
	private List<ConfigurationUtilBean> dropDown=null;
	private Map<String, String> commonMap;
	private Map<String, String> deptMap;
	private String employee;
	private String country;
	private String state;
	private String city;
	private String territory;
	
	
	public String beforeMapEmployee() 
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				setAddPageDataFields();
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
	private void setAddPageDataFields()
	{
    	try
    	{
    	    List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_employee_location_configuration",accountID,connectionSpace,"",0,"table_name","employee_location_configuration");
    	    dropDown = new ArrayList<ConfigurationUtilBean>();
    	    if(fieldList!=null && fieldList.size()>0)
            {
                for(GridDataPropertyView  gdp : fieldList)
                {
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("D"))
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
                        dropDown.add(objdata);
                        if(gdp.getColomnName().equalsIgnoreCase("country"))
                        {
                        	commonMap=new LinkedHashMap<String, String>();
                        	String query =null;
                        	query = "SELECT id,country_name FROM country  ORDER BY country_name";
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
                							commonMap.put(object[0].toString(), object[1].toString());
                						}
                					}
                				}
							}
                        }
                        if(gdp.getColomnName().equalsIgnoreCase("dept"))
                        {
                        	deptMap=new LinkedHashMap<String, String>();
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
                        }
                    }
                }
            }
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	
	@SuppressWarnings("unchecked")
	public String addMappingLocation()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_employee_location_configuration",accountID,connectionSpace,"",0,"table_name","employee_location_configuration");
	    	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	   
                TableColumes ob1=new TableColumes();
                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
                for(GridDataPropertyView gdp:statusColName)
                {
                     if(gdp.getColomnName()!=null)
                     {
                    	 if(!gdp.getColomnName().equalsIgnoreCase("dept") && !gdp.getColomnName().equalsIgnoreCase("country") && !gdp.getColomnName().equalsIgnoreCase("city")
                    			 && !gdp.getColomnName().equalsIgnoreCase("state"))
                    	 {
                    		 ob1=new TableColumes();
                             ob1.setColumnname(gdp.getColomnName());
                             ob1.setDatatype("varchar(255)");
                             if(gdp.getColomnName().equalsIgnoreCase("flag"))
                             {
                            	 ob1.setConstraint("default 'Active'");
                             }
                             else
                             {
                            	 ob1.setConstraint("default NULL");
                             }
                             Tablecolumesaaa.add(ob1);
                    	 }
                     }
                }
                cbt.createTable22("employee_location",Tablecolumesaaa,connectionSpace);
                List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
                InsertDataTable ob=null;
                String mapped_id=null,mapped_for=null;
                System.out.println("Country ::: "+country);
                System.out.println("State ::: "+state);
                System.out.println("City ::: "+city);
                System.out.println("Territory ::: "+territory);
                
                
                if(country!=null && !country.equalsIgnoreCase("-1")&& !country.equalsIgnoreCase(""))
                {
                	mapped_id = country;
                	mapped_for = "CO";
                }
                if(state!=null && !state.equalsIgnoreCase("-1")&& !state.equalsIgnoreCase(""))
                {
                	mapped_id = state;
                	mapped_for = "ST";
                }
                if(city!=null && !city.equalsIgnoreCase("-1")&& !city.equalsIgnoreCase(""))
                {
                	mapped_id = city;
                	mapped_for = "CI";
                }
                if(territory!=null && !territory.equalsIgnoreCase("-1")&& !territory.equalsIgnoreCase(""))
                {
                	mapped_id = territory;
                	mapped_for = "TE";
                }
                boolean status =false;
                System.out.println("Mapped id :::::::::: "+mapped_id);
                System.out.println("Mapped For :::::::::: "+mapped_for);
                if(employee!=null)
                {
                	String arr[]=employee.trim().split(",");
                	if(arr!=null )
                	{
                		for (int i = 0; i < arr.length; i++) 
                		{
							if(arr[i]!=null && !arr[i].equalsIgnoreCase(" "))
							{
								insertData=new ArrayList<InsertDataTable>();
								
								ob=new InsertDataTable();
				                ob.setColName("employee");
				                ob.setDataName(arr[i].trim());
				                insertData.add(ob);
				                
				                String map[] = mapped_id.trim().split(",");
				                if(map!=null)
				                {
				                	for (int j = 0; j < map.length; j++)
									{
										if(map[j]!=null && !map[j].equalsIgnoreCase(" "))
										{
											ob=new InsertDataTable();
							                ob.setColName("territory");
							                ob.setDataName(map[j].trim());
							                insertData.add(ob);
							                
							                ob=new InsertDataTable();
							                ob.setColName("mapped_for");
							                ob.setDataName(mapped_for);
							                insertData.add(ob);
							                
							            	ob=new InsertDataTable();
							            	ob.setColName("user_name");
							            	ob.setDataName(userName);
							            	insertData.add(ob);
							            
						                    ob=new InsertDataTable();
						                    ob.setColName("created_date");
						                    ob.setDataName(DateUtil.getCurrentDateUSFormat());
						                    insertData.add(ob);
						            
						                    ob=new InsertDataTable();
						                    ob.setColName("created_time");
						                    ob.setDataName(DateUtil.getCurrentTimeHourMin());
						                    insertData.add(ob);
						                    
						                    cbt.insertIntoTable("employee_location",insertData,connectionSpace);
						                    insertData.remove(insertData.size()-1);
						                    insertData.remove(insertData.size()-1);
						                    insertData.remove(insertData.size()-1);
						                    insertData.remove(insertData.size()-1);
						                    insertData.remove(insertData.size()-1);
						                    status =true;
										}
									}
				                }
							}
						}
                	}
                }
                if (status)
				{
                	 addActionMessage("Employee Map with Employee Successfully !!!");
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
	public void setDropDown(List<ConfigurationUtilBean> dropDown)
	{
		this.dropDown = dropDown;
	}
	public List<ConfigurationUtilBean> getDropDown()
	{
		return dropDown;
	}
	public void setCommonMap(Map<String, String> commonMap)
	{
		this.commonMap = commonMap;
	}
	public Map<String, String> getCommonMap()
	{
		return commonMap;
	}
	public void setDeptMap(Map<String, String> deptMap)
	{
		this.deptMap = deptMap;
	}
	public Map<String, String> getDeptMap()
	{
		return deptMap;
	}
	public String getEmployee()
	{
		return employee;
	}
	public void setEmployee(String employee)
	{
		this.employee = employee;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getTerritory()
	{
		return territory;
	}
	public void setTerritory(String territory)
	{
		this.territory = territory;
	}
	
}
