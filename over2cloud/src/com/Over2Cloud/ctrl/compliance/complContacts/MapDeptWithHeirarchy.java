package com.Over2Cloud.ctrl.compliance.complContacts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

@SuppressWarnings("serial")
public class MapDeptWithHeirarchy extends GridPropertyBean
{
	private Map<String, String> deptNameMap;
	private Map<String, String> empList;
	private Map<String, String> deptList;
	private String country,headOfficeId,regLevel,dept_id,deptname,status;
	private List<ConfigurationUtilBean> dropDown;
	private String mappedOffice,mappedFor,map_dept_id,contact_id;
	private JSONArray jsonArray;
	private List<GridDataPropertyView> level1ColmnNames=null;
	private List<Object> deptDataViewShow;
	private String PlantId;

	public String beforemapDept() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				deptList = new LinkedHashMap<String, String>();
				deptNameMap = new LinkedHashMap<String, String>();
				String query = "SELECT id,name FROM country_data ORDER BY name";
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							deptNameMap.put(object[0].toString(), object[1].toString());
						}
					}
					dataList.clear();
				}
				query = "SELECT id,dept_name FROM department_master ORDER BY dept_name";
			    dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							deptList.put(object[0].toString(), object[1].toString());
						}
					}
				}
				setDeptAddPageFileds("mapped_dept_hierarchy_configuration","dept_hierarchy_configuration");
				return SUCCESS;
				
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} 
		else 
		{
			return LOGIN;
		}
	}
	public void setDeptAddPageFileds(String mappedTable,String configTable)
	{
		try
		{
			dropDown=new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> gridFields=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",configTable);
			if(gridFields!=null && gridFields.size()>0)
			{
				for(GridDataPropertyView  gdp:gridFields)
				{
					ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("flag"))
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
                    }
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String mapDeptHeirarchy() 
	{if (ValidateSession.checkSession()) 
	{
		try 
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_dept_hierarchy_configuration",accountID,connectionSpace,"",0,"table_name","dept_hierarchy_configuration");
            for(GridDataPropertyView gdp:statusColName)
            {
                 if(gdp.getColomnName()!=null)
                 {
                	 TableColumes ob1=new TableColumes();
                     ob1=new TableColumes();
                     ob1.setColumnname(gdp.getColomnName());
                     if(gdp.getColomnName().equalsIgnoreCase("id"))
						{
							ob1.setDatatype("int(10)");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("mapped_id"))
						{
							ob1.setDatatype("int(10)");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("mapped_office"))
						{
							ob1.setDatatype("varchar(20)");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("dept_id"))
						{
							ob1.setDatatype("int(10)");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("user_name"))
						{
							ob1.setDatatype("varchar(50)");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("date"))
						{
							ob1.setDatatype("varchar(10)");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("time"))
						{
							ob1.setDatatype("varchar(10)");
						}
						else
						{
							ob1.setDatatype("varchar(50)");
						}
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
			cbt.createTable22("map_dept_heirarchy", Tablecolumesaaa, connectionSpace);
		
			String mappedId=null;
			String dataFor =null;
			if(PlantId!=null && !PlantId.equalsIgnoreCase("-1") && !PlantId.equalsIgnoreCase(""))
			{
				mappedId = PlantId;
				dataFor ="PL";
			}
			String deptIds[] = dept_id.split(",");
			String mappedData[] = mappedId.split(",");
			if(mappedData!=null && !mappedData.equals("-1") && !mappedData.toString().equalsIgnoreCase(""))
			{
				for (int j = 0; j < mappedData.length; j++) 
				{
					if(deptIds!=null && !deptIds.equals("-1"))
					{
						for (int i = 0; i < deptIds.length; i++) 
						{
							ob = new InsertDataTable();
							ob.setColName("mapped_id");
							ob.setDataName(mappedData[j]);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("mapped_office");
							ob.setDataName(dataFor);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("dept_id");
							ob.setDataName(deptIds[i].trim());
							insertData.add(ob);
							
							ob=new InsertDataTable();
			            	ob.setColName("user_name");
			            	ob.setDataName(userName);
			            	insertData.add(ob);
			            	
			            	ob=new InsertDataTable();
		                    ob.setColName("date");
		                    ob.setDataName(DateUtil.getCurrentDateUSFormat());
		                    insertData.add(ob);
		                    
		                    ob=new InsertDataTable();
		                    ob.setColName("time");
		                    ob.setDataName(DateUtil.getCurrentTimeHourMin());
		                    insertData.add(ob);
							
		                    ob=new InsertDataTable();
		                    ob.setColName("status");
		                    ob.setDataName("Active");
		                    insertData.add(ob);
		                    
						    cbt.insertIntoTable("map_dept_heirarchy", insertData, connectionSpace);
						    insertData.clear();
						}
					}
				}
				addActionMessage("Data Saved Successfully!");
			}
			
			return SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	} 
	else 
	{
		return LOGIN;
	}
}
	
	public String deptWithEmployee()
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				deptNameMap = new LinkedHashMap<String, String>();
				empList = new LinkedHashMap<String, String>();
				String query = "SELECT id,name FROM country_data ORDER BY name";
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							deptNameMap.put(object[0].toString(), object[1].toString());
						}
					}
					dataList.clear();
				}
				query = "SELECT id,emp_name FROM employee_master where id not in(select contact_id from contact_dept_mapping) ORDER BY emp_name";
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							empList.put(object[0].toString(), object[1].toString());
						}
					}
				}
				setDeptAddPageFileds("mapped_contact_dept_configuration","contact_dept_configuration");
				return SUCCESS;
				
			} catch (Exception e) {
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
	public String fetchMappedDept()
	{
		String returnresult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String query = "SELECT map.id,dept.dept_name  FROM map_dept_heirarchy AS map LEFT JOIN department_master as dept on dept.id=map.dept_id WHERE map.mapped_office='"+mappedFor+"' AND map.mapped_id='"+mappedOffice+"'";
				List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject obj = new JSONObject();
							obj.put("ID", object[0].toString());
							obj.put("NAME", object[1].toString());
							jsonArray.add(obj);
						}
					}
				}

				returnresult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnresult = ERROR;
			}
		} else
		{
			return LOGIN;
		}
		return returnresult;
	}
	
	public String employeeMapDept() 
	{
		if (ValidateSession.checkSession()) 
		{
			try 
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_contact_dept_configuration",accountID,connectionSpace,"",0,"table_name","contact_dept_configuration");
                for(GridDataPropertyView gdp:statusColName)
                {
                     if(gdp.getColomnName()!=null)
                     {
                    	TableColumes ob1=new TableColumes();
                        ob1=new TableColumes();
                        ob1.setColumnname(gdp.getColomnName());
                        if(gdp.getColomnName().equalsIgnoreCase("id"))
 						{
 							ob1.setDatatype("int(10)");
 						}
 						else if(gdp.getColomnName().equalsIgnoreCase("map_dept_id"))
 						{
 							ob1.setDatatype("int(10)");
 						}
 						else if(gdp.getColomnName().equalsIgnoreCase("contact_id"))
 						{
 							ob1.setDatatype("int(10)");
 						}
 						else if(gdp.getColomnName().equalsIgnoreCase("user_name"))
 						{
 							ob1.setDatatype("varchar(50)");
 						}
 						else if(gdp.getColomnName().equalsIgnoreCase("date"))
 						{
 							ob1.setDatatype("varchar(10)");
 						}
 						else if(gdp.getColomnName().equalsIgnoreCase("time"))
 						{
 							ob1.setDatatype("varchar(10)");
 						}
 						else
 						{
 							ob1.setDatatype("varchar(50)");
 						}
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
				cbt.createTable22("contact_dept_mapping", Tablecolumesaaa, connectionSpace);
				
				String empIds[] = contact_id.split(",");
				if(empIds!=null && !empIds.equals("-1"))
				{
					for (int i = 0; i < empIds.length; i++) 
					{
						ob = new InsertDataTable();
						ob.setColName("map_dept_id");
						ob.setDataName(map_dept_id);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("contact_id");
						ob.setDataName(empIds[i].trim());
						insertData.add(ob);

						ob=new InsertDataTable();
		            	ob.setColName("user_name");
		            	ob.setDataName(userName);
		            	insertData.add(ob);
		            	
		            	ob=new InsertDataTable();
	                    ob.setColName("date");
	                    ob.setDataName(DateUtil.getCurrentDateUSFormat());
	                    insertData.add(ob);
	                    
	                    ob=new InsertDataTable();
	                    ob.setColName("time");
	                    ob.setDataName(DateUtil.getCurrentTimeHourMin());
	                    insertData.add(ob);
						
	                    ob=new InsertDataTable();
	                    ob.setColName("status");
	                    ob.setDataName("Active");
	                    insertData.add(ob);
	                    
					    cbt.insertIntoTable("contact_dept_mapping", insertData, connectionSpace);
					    insertData.clear();
					}
					addActionMessage("Data Saved Successfully!");
				}
				return SUCCESS;
				
			} catch (Exception e) {
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
	public String beforeMapDeptView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setGridColomnNames("mapped_dept_hierarchy_configuration","dept_hierarchy_configuration");
			
			String query = "SELECT map.id,dept.dept_name  FROM map_dept_heirarchy AS map LEFT JOIN department_master as dept on dept.id=map.dept_id ";
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			deptList = new LinkedHashMap<String, String>();
			if (dataList != null && dataList.size() > 0)
			{
				
				for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						deptList.put(object[0].toString(), object[1].toString());
					}
				}
			}
			System.out.println(this.getStatus());
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setGridColomnNames(String mappedTable,String mainTable)
	{
		level1ColmnNames=new ArrayList<GridDataPropertyView>();
		
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		level1ColmnNames.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",mainTable);
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
	@SuppressWarnings("rawtypes")
	public String mapDeptHierarchyView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("SELECT COUNT(*) FROM map_dept_heirarchy ");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null)
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
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				query.append("SELECT ");
				List fieldNames=Configuration.getColomnList("mapped_dept_hierarchy_configuration", accountID, connectionSpace, "dept_hierarchy_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
				    Object obdata=(Object)it.next();
				    if(obdata!=null)
				    {
					    if(i<fieldNames.size()-1)
					    {
					    	if(obdata.toString().equalsIgnoreCase("dept_id"))
					    	{
					    		query.append(" dept.dept_name, ");
					    	}
					    	else if(obdata.toString().equalsIgnoreCase("mapped_id"))
					    	{
					    		query.append(" dept1.deptName, ");
					    	}
					    	else
					    	{
					    		query.append(" map."+obdata.toString()+", ");
					    	}
					    }
					    else
					    {
					    	query.append(" map."+obdata.toString());
					    }
				    }
				    i++;
				} 
				query.append(" FROM map_dept_heirarchy AS map ");
				query.append(" LEFT JOIN  department_master as dept on dept.id=map.dept_id ");
				query.append(" LEFT JOIN  department as dept1 on dept1.id=map.mapped_id ");
				
				if (this.getStatus() != null && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("undefined"))
				{
					query.append(" where map.status='" + this.getStatus()+ "'");
				}
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" AND ");
					//add search  query based on the search operation
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
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by "+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by "+getSidx()+" DESC");
		    	    }
			    }
				System.out.println("DATA QUERY ::: "+query.toString());
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null && data.size()>0)
				{
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
	                            	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	                            	{
	                            		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat( obdata[j].toString()));
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
	                    Listhb.add(one);
	                }
					setDeptDataViewShow(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
}
	public String deptWithEmployeeView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setGridColomnNames("mapped_contact_dept_configuration","contact_dept_configuration");
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	@SuppressWarnings("rawtypes")
	public String deptWithEmployeeViewData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("SELECT COUNT(*) FROM contact_dept_mapping ");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount!=null)
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
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				query.append("SELECT ");
				List fieldNames=Configuration.getColomnList("mapped_contact_dept_configuration", accountID, connectionSpace, "contact_dept_configuration");
				List<Object> Listhb=new ArrayList<Object>();
				int i=0;
				for(Iterator it=fieldNames.iterator(); it.hasNext();)
				{
					//generating the dyanamic query based on selected fields
				    Object obdata=(Object)it.next();
				    if(obdata!=null)
				    {
					    if(i<fieldNames.size()-1)
					    {
					    	if(obdata.toString().equalsIgnoreCase("map_dept_id"))
					    	{
					    		query.append(" dept.dept_name, ");
					    	}
					    	else if(obdata.toString().equalsIgnoreCase("contact_id"))
					    	{
					    		query.append(" emp.emp_name, ");
					    	}
					    	else
					    	{
					    		query.append(" contact."+obdata.toString()+", ");
					    	}
					    }
					    else
					    {
					    	query.append(" contact."+obdata.toString());
					    }
				    }
				    i++;
				} 
				query.append(" FROM contact_dept_mapping AS contact ");
				query.append(" LEFT JOIN  map_dept_heirarchy as map on map.id=contact.map_dept_id ");
				query.append(" LEFT JOIN  department_master as dept on dept.id=map.dept_id ");
				query.append(" LEFT JOIN  employee_master as emp on emp.id=contact.contact_id ");
				
				if (this.getStatus() != null && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("undefined"))
				{
					query.append(" where contact.status='"+this.getStatus()+ "'");
				}
				
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" AND ");
					//add search  query based on the search operation
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
				
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by "+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by "+getSidx()+" DESC");
		    	    }
			    }
				System.out.println("DATA QUERY ::: "+query.toString());
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null && data.size()>0)
				{
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
                                	if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
                                	{
                                		one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat( obdata[j].toString()));
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
                        Listhb.add(one);
                    }
					setDeptDataViewShow(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String taskGridDataViewModify()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>)request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& Parmname != null
								&& !Parmname.equalsIgnoreCase("")
								&& !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper")
								&& !Parmname.equalsIgnoreCase("rowid"))
							if (Parmname.equalsIgnoreCase("date"))
							{
								wherClause.put(Parmname, DateUtil
										.convertDateToUSFormat(paramValue));
							}
							else
							{
								wherClause.put(Parmname, paramValue);
							}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("map_dept_heirarchy", wherClause,
							condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					StringBuilder query = new StringBuilder();
					query.append("UPDATE map_dept_heirarchy SET status='Inactive' WHERE id IN("
							+ condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
					query.setLength(0);

				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String taskGridDataViewModify1()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& Parmname != null
								&& !Parmname.equalsIgnoreCase("")
								&& !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper")
								&& !Parmname.equalsIgnoreCase("rowid"))
							if (Parmname.equalsIgnoreCase("date"))
							{
								wherClause.put(Parmname, DateUtil
										.convertDateToUSFormat(paramValue));
							}
							else
							{
								wherClause.put(Parmname, paramValue);
							}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("contact_dept_mapping", wherClause,
							condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					StringBuilder query = new StringBuilder();
					query.append("UPDATE contact_dept_mapping SET status='Inactive' WHERE id IN("
							+ condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
					query.setLength(0);

				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public Map<String, String> getDeptNameMap() {
		return deptNameMap;
	}

	public void setDeptNameMap(Map<String, String> deptNameMap) {
		this.deptNameMap = deptNameMap;
	}

	public Map<String, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<String, String> deptList) {
		this.deptList = deptList;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadOfficeId() {
		return headOfficeId;
	}
	public void setHeadOfficeId(String headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	public String getRegLevel() {
		return regLevel;
	}
	public void setRegLevel(String regLevel) {
		this.regLevel = regLevel;
	}
	
	public List<ConfigurationUtilBean> getDropDown() {
		return dropDown;
	}
	public void setDropDown(List<ConfigurationUtilBean> dropDown) {
		this.dropDown = dropDown;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public Map<String, String> getEmpList() {
		return empList;
	}
	public void setEmpList(Map<String, String> empList) {
		this.empList = empList;
	}
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public String getMappedOffice() {
		return mappedOffice;
	}
	public void setMappedOffice(String mappedOffice) {
		this.mappedOffice = mappedOffice;
	}
	public String getMappedFor() {
		return mappedFor;
	}
	public void setMappedFor(String mappedFor) {
		this.mappedFor = mappedFor;
	}
	public String getMap_dept_id() {
		return map_dept_id;
	}
	public void setMap_dept_id(String map_dept_id) {
		this.map_dept_id = map_dept_id;
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public List<GridDataPropertyView> getLevel1ColmnNames()
	{
		return level1ColmnNames;
	}
	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames)
	{
		this.level1ColmnNames = level1ColmnNames;
	}
	public List<Object> getDeptDataViewShow()
	{
		return deptDataViewShow;
	}
	public void setDeptDataViewShow(List<Object> deptDataViewShow)
	{
		this.deptDataViewShow = deptDataViewShow;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public String getPlantId() {
		return PlantId;
	}
	public void setPlantId(String plantId) {
		PlantId = plantId;
	}
	
}
