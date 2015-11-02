package com.Over2Cloud.ctrl.wfpm.activityPlanner;

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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

@SuppressWarnings("serial")
public class ActivityPlanAction  extends GridPropertyBean
{
	private List<ConfigurationUtilBean> textBox=null;
	private List<ConfigurationUtilBean> dateField=null;
	private List<GridDataPropertyView> masterViewProp=null;
	private List<Object> masterViewList;
	private String month;
	private String dataFor;
	private JSONArray commonJsonArr;
	private Map<String, String> commonMap;
	private Map<String, String> relationMap;
	private String fdate;
	private String tdate;
	private String country;
	private String state;
	private String city;
	private String territory;
	private String type;
	private String subtype;
	private String emp;
	private String actId;
	
	public String beforeActivityPlan()
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
    	    List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_activity_plan_configuration",accountID,connectionSpace,"",0,"table_name","activity_plan_configuration");
    	 
    	    textBox = new ArrayList<ConfigurationUtilBean>();
    	    dateField = new ArrayList<ConfigurationUtilBean>();
    	    if(fieldList!=null && fieldList.size()>0)
            {
                for(GridDataPropertyView  gdp : fieldList)
                {
                    ConfigurationUtilBean objdata= new ConfigurationUtilBean();
                    if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_time") && !gdp.getColomnName().equalsIgnoreCase("created_date") )
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
                        textBox.add(objdata);
                    }
                    else if(gdp.getColType().trim().equalsIgnoreCase("date"))
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
                        dateField.add(objdata);
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
	public String addActivityPlan()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
	    	    List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_activity_plan_configuration",accountID,connectionSpace,"",0,"table_name","activity_plan_configuration");
	    	    CommonOperInterface cbt=new CommonConFactory().createInterface();
	    	   
                TableColumes ob1=null;
                List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
                for(GridDataPropertyView gdp:statusColName)
                {
                	 ob1=new TableColumes();
                     ob1.setColumnname(gdp.getColomnName());
                     ob1.setDatatype(gdp.getData_type());
                     if (gdp.getColomnName().equalsIgnoreCase("status")) 
                     {
                    	 ob1.setConstraint("default 'Pending'");
					 }
                     else
                     {
                    	 ob1.setConstraint("default NULL");
                     }
                     Tablecolumesaaa.add(ob1);
                }
                ob1=new TableColumes();
                ob1.setColumnname("request_id");
                ob1.setDatatype("varchar(100)");
               	ob1.setConstraint("default NULL");
                Tablecolumesaaa.add(ob1);
                
                ob1=new TableColumes();
                ob1.setColumnname("manager_id");
                ob1.setDatatype("varchar(100)");
               	ob1.setConstraint("default NULL");
                Tablecolumesaaa.add(ob1);
                
                ob1=new TableColumes();
                ob1.setColumnname("manager_reason");
                ob1.setDatatype("varchar(200)");
               	ob1.setConstraint("default NULL");
                Tablecolumesaaa.add(ob1);
                
                ob1=new TableColumes();
                ob1.setColumnname("manager_date_time");
                ob1.setDatatype("varchar(20)");
               	ob1.setConstraint("default NULL");
                Tablecolumesaaa.add(ob1);
                
                cbt.createTable22("activity_plan",Tablecolumesaaa,connectionSpace);
                
                List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
                InsertDataTable ob=null;
                List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();
                while(it.hasNext())
                {
                	String Parmname = it.next().toString();
                    String paramValue=request.getParameter(Parmname);
                    if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
                    {
                    	if(Parmname.equalsIgnoreCase("for_month") ||Parmname.equalsIgnoreCase("extend_by") )
                    	{
                    		ob=new InsertDataTable();
                        	ob.setColName(Parmname);
                            ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
                            insertData.add(ob);
                    	}
                    	else
                    	{
                    		ob=new InsertDataTable();
                        	ob.setColName(Parmname);
                            ob.setDataName(paramValue);
                            insertData.add(ob);
                    	}
                    }
                }
                String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
                
                ob=new InsertDataTable();
                ob.setColName("request_id");
                ob.setDataName(loggedDetails[0]);
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
         
                cbt.insertIntoTable("activity_plan",insertData,connectionSpace); 
                addActionMessage("Activity Plan Successfully !!!");
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
	public String beforeViewActivityPlan()
	{

		if (ValidateSession.checkSession()) 
		{
			try
			{
				viewProperties();
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
	private void viewProperties()
	{
		try
		{
			masterViewProp=new ArrayList<GridDataPropertyView>();

            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            gpv=new GridDataPropertyView();
            gpv.setColomnName("schedule_id");
            gpv.setHeadingName("Requester Name");
            gpv.setHideOrShow("false");
            gpv.setWidth(60);
            masterViewProp.add(gpv);
            
            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_activity_plan_configuration",accountID,connectionSpace,"",0,"table_name","activity_plan_configuration");
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
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public String viewActivityPlan()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from activity_plan where status='Active'");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_activity_plan_configuration", accountID, connectionSpace, "activity_plan_configuration");
					fieldNames.add("schedule_id");
					
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
								if(obdata.toString().equalsIgnoreCase("schedule_id"))
								{
									query.append(" emp.emp_name, ");
								}
								else
								{
									 query.append("av."+obdata.toString() + ",");
								}
							   
							else
							{
								if(obdata.toString().equalsIgnoreCase("schedule_id"))
								{
									query.append(" emp.emp_name ");
								}
								else
								{
									query.append("av."+obdata.toString() );
								}
							}
								
						}
						i++;
					}
					query.append(" from activity_plan  as av left join manage_contact as mc on mc.id=av.request_id LEFT JOIN primary_contact as emp on emp.id=mc.emp_id ");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and");
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase(""))
								{
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} 
									else if (fieldNames.get(k).toString().equalsIgnoreCase("for_month") || fieldNames.get(k).toString().equalsIgnoreCase("extend_by")|| fieldNames.get(k).toString().equalsIgnoreCase("created_date"))
									{
										one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat( obdata[k].toString()));
									}
									else
									{
										one.put(fieldNames.get(k).toString(),obdata[k].toString());
									}
								} 
								else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setRecords(Listhb.size());
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
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
	@SuppressWarnings("unchecked")
	public String addActivityPlanManagerStatus()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{

				CommonOperInterface cbt=new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue=request.getParameter(parmName);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("actId"))
					{
						 wherClause.put(parmName, paramValue);
					} 
				}
				 wherClause.put("manager_id", loggedDetails[0]);
				 wherClause.put("manager_date_time", DateUtil.getCurrentDateUSFormat()+", "+DateUtil.getCurrentTimeHourMin());
				 condtnBlock.put("id",actId);
				 boolean status=cbt.updateTableColomn("activity_plan", wherClause, condtnBlock,connectionSpace);
				 if(status)
				 {
					 addActionMessage("Action Taken Successfully !!!");
				 }else
				 {
					 addActionMessage("There is some error in data!!!");
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
	public String changeDate()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				commonJsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				if(dataFor!=null && dataFor.equalsIgnoreCase("previous"))
				{
					obj.put("date",DateUtil.convertDateToIndianFormat( DateUtil.getNewDate("day", -1, DateUtil.convertDateToUSFormat(month))));
					obj.put("day",DateUtil.getDayofDate(DateUtil.getNewDate("day", -1, DateUtil.convertDateToUSFormat(month))) );
				}
				else
				{
					obj.put("date", DateUtil.convertDateToIndianFormat( DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(month))));
					obj.put("day",DateUtil.getDayofDate(DateUtil.getNewDate("day", 1, DateUtil.convertDateToUSFormat(month))) );
				}
				commonJsonArr.add(obj);
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
	public String changeDateApproval()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				commonJsonArr = new JSONArray();
				JSONObject obj = new JSONObject();
				CommonOperInterface coi = new CommonConFactory().createInterface();
				
				String tableMapped=null,schId=null;
				String query= "SELECT rel_sub_type,schedule_contact_id  FROM activity_schedule_plan WHERE id= "+actId;
				List xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
				if (xepenseList != null && xepenseList.size() > 0)
				{
					Object[] object= (Object[])xepenseList.get(0);
					tableMapped =object[0].toString();
					schId =object[1].toString();
				}
				if(tableMapped!=null)
				{
					if(tableMapped.equalsIgnoreCase("referral_contact_data"))
					{
						query= "SELECT sp.for_month,pc.emp_name,at.act_name,at.id,sp.id as schID  FROM activity_schedule_plan as sp LEFT JOIN referral_contact_data as ca on ca.id=sp.activity_for " +
								"LEFT JOIN primary_contact as pc on pc.id=ca.map_emp_id LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type " +
								"LEFT JOIN activity_type as at on ps.status = at.id  ";
						
					}
					else
					{
						query="SELECT  sp.for_month,ca.org_name,at.act_name,at.id,sp.id as schID   FROM activity_schedule_plan as sp LEFT JOIN referral_institutional_data as ca on ca.id=sp.activity_for " +
								"LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type LEFT JOIN activity_type as at on ps.status = at.id ";
					}
					if(dataFor!=null && dataFor.equalsIgnoreCase("previous"))
					{
						query+= "WHERE sp.for_month< '"+DateUtil.convertDateToUSFormat(month)+"' ";
					}
					else
					{
						query+= "WHERE sp.for_month> '"+DateUtil.convertDateToUSFormat(month)+"' ";
					}
					
					query+= " AND sp.schedule_contact_id = '"+schId+"'  order by sp.for_month asc LIMIT  1 ";
					xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
					if (xepenseList != null && xepenseList.size() > 0)
					{
						for (Iterator<?> iterator = xepenseList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								obj.put("for_month",DateUtil.convertDateToIndianFormat( object[0].toString()))  ;
								obj.put("activity_for", object[1].toString())  ;
								obj.put("activity_type", object[2].toString())  ;
								obj.put("id", object[4].toString())  ;
							}
						}
						xepenseList.clear();
					}
				}
				commonJsonArr.add(obj);
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
	public String deadLineDisplay()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				commonJsonArr = new JSONArray();
				JSONObject obj = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				String query = "SELECT configure_for,scheduleFor,deadline_date,deadline_time,startFrom,dueTill FROM configure_schedule  WHERE emp IN("+loggedDetails[0]+") ORDER BY configure_for";
				List data = cbt.executeAllSelectQuery(query, connectionSpace);
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						obj = new JSONObject();
						if(object[0]!=null)
						{
							obj.put("configureFor", object[0].toString());
						}
						if(object[1]!=null)
						{
							if(object[1].toString().equalsIgnoreCase("Date"))
							{
								if(object[2]!=null && object[3]!=null)
								{
									obj.put("date", DateUtil.convertDateToIndianFormat(object[2].toString())+", "+object[3].toString());
								}
							}
							else
							{
								if(object[4]!=null && object[5]!=null)
								{
									obj.put("date",DateUtil.convertDateToIndianFormat( DateUtil.getNewDate("day", -(Integer.parseInt(object[4].toString())), object[2].toString()))+", "+DateUtil.convertDateToIndianFormat(DateUtil.getNewDate("day", (Integer.parseInt(object[5].toString())), object[2].toString())));
								}
							}
						}
						commonJsonArr.add(obj);
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
	public String beforeActivityViewHeader()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				fdate = DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
				commonMap = new LinkedHashMap<String, String>();
				CommonOperInterface coi = new CommonConFactory().createInterface();
				String query = " ";
				query = "SELECT id,country_name FROM country WHERE status='Active'  ORDER BY country_name";
				if (query != null)
				{
					List<?> dataList = coi.executeAllSelectQuery(query, connectionSpace);
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
				relationMap = new LinkedHashMap<String, String>();
				query = "SELECT id,name FROM relationship WHERE status='Active' ORDER BY name";
				if (query != null)
				{
					List<?> dataList = coi.executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								relationMap.put(object[0].toString(), object[1].toString());
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
	public String beforeActivityView()
	{
		if (ValidateSession.checkSession()) 
		{
			try
			{
				viewPropertiesActivity();
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
	private void viewPropertiesActivity()
	{
		try
		{
			masterViewProp=new ArrayList<GridDataPropertyView>();

            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            if(dataFor!=null && dataFor.equalsIgnoreCase("Accounts"))
            {
            	gpv=new GridDataPropertyView();
                gpv.setColomnName("schedule_contact_id");
                gpv.setHeadingName("Employee");
                gpv.setHideOrShow("false");
                gpv.setWidth(70);
                masterViewProp.add(gpv);
                
                gpv=new GridDataPropertyView();
                gpv.setColomnName("ticket_no");
                gpv.setHeadingName("Activity Id");
                gpv.setHideOrShow("false");
                gpv.setWidth(70);
                masterViewProp.add(gpv);
            }
            
            List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_activity_planner_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_planner_configuration");
            for(GridDataPropertyView gdp:returnResult)
            {
        	    gpv=new GridDataPropertyView();
                gpv.setColomnName(gdp.getColomnName());
                gpv.setHeadingName(gdp.getHeadingName());
                gpv.setEditable(gdp.getEditable());
                gpv.setSearch(gdp.getSearch());
                if(gdp.getColomnName().equalsIgnoreCase("sch_to") || gdp.getColomnName().equalsIgnoreCase("created_date") 
                		|| gdp.getColomnName().equalsIgnoreCase("user_name")||gdp.getColomnName().equalsIgnoreCase("created_time"))
                {
                	gpv.setHideOrShow("true");
                }
                else
                {
                	gpv.setHideOrShow("false");
                }
                gpv.setFormatter(gdp.getFormatter());
                gpv.setWidth(gdp.getWidth());
                masterViewProp.add(gpv);
            }
            if(dataFor!=null && dataFor.equalsIgnoreCase("Accounts"))
            {
            	gpv=new GridDataPropertyView();
                gpv.setColomnName("amount");
                gpv.setHeadingName("Amount");
                gpv.setHideOrShow("false");
                gpv.setWidth(70);
                masterViewProp.add(gpv);
            }
            session.put("masterViewProp", masterViewProp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public String viewActivity()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("sc.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("country"))
							{
								query.append("co.country_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("state"))
							{
								query.append("s.state_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("city"))
							{
								query.append("c.city_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("territory"))
							{
								query.append("tt.trr_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("rel_type"))
							{
								query.append("type.name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else
							{
								query.append("sc."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							query.append(gpv.getColomnName().toString());
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_plan as sc");
				query.append(" LEFT JOIN territory as tt on tt.id=sc.territory ");
				query.append(" LEFT JOIN city as c on c.id=tt.trr_city ");
				query.append(" LEFT JOIN state as s on s.id=c.name_state ");
				query.append(" LEFT JOIN country as co on co.id=s.country_code ");
				query.append(" LEFT JOIN relationship as type on type.id=sc.rel_type ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=sc.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status WHERE ");
				if(fdate!=null && tdate!=null && !fdate.equalsIgnoreCase("") && !tdate.equalsIgnoreCase(""))
				{
					if(fdate.split("-")[0].length()<3)
					{
						fdate= DateUtil.convertDateToUSFormat(fdate);
					}
					if(tdate.split("-")[0].length()<3)
					{
						tdate= DateUtil.convertDateToUSFormat(tdate);
					}
					query.append("  sc.for_month BETWEEN '"+fdate+"' AND '"+tdate+"' ");
				}
				else
				{
					if(month!=null && !month.equalsIgnoreCase("-1")&& !month.equalsIgnoreCase("undefined"))
					{
						query.append("  sc.for_month LIKE '%"+month+"%' ");
					}
				}
				if(country!=null && !country.equalsIgnoreCase("-1"))
				{
					query.append(" AND co.id = '"+country+"' ");
				}
				if(state!=null && !state.equalsIgnoreCase("-1"))
				{
					query.append(" AND s.id = '"+state+"' ");
				}
				if(city!=null && !city.equalsIgnoreCase("-1"))
				{
					query.append(" AND c.id = '"+city+"' ");
				}
				if(territory!=null && !territory.equalsIgnoreCase("-1"))
				{
					query.append(" AND tt.id = '"+territory+"' ");
				}
				if(type!=null && !type.equalsIgnoreCase("-1"))
				{
					query.append(" AND type.id = '"+type+"' ");
				}
				if(subtype!=null && !subtype.equalsIgnoreCase("-1")&& !subtype.equalsIgnoreCase(""))
				{
					if(subtype.equalsIgnoreCase("Individual"))
					{
						query.append(" AND  sc.rel_sub_type = 'referral_contact_data' ");
					}
					else
					{
						query.append(" AND sc.rel_sub_type = 'referral_institutional_data' ");
					}
				}
				if(emp!=null && !emp.equalsIgnoreCase("-1")&& !emp.equalsIgnoreCase("undefined"))
				{
					query.append(" AND sc.schedule_contact_id IN (SELECT id FROM manage_contact WHERE emp_id IN (SELECT emp_id FROM manage_contact WHERE id =" + emp + ") AND module_name='WFPM') ");
				}
				query.append(" ORDER BY sc.for_month ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					ActivityPlannerAction AA=new ActivityPlannerAction();
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("rel_sub_type"))
								{
									if( obdata[k].toString().equalsIgnoreCase("referral_contact_data"))
									{
										one.put(gpv.getColomnName(), "Individual");
									}
									else
									{
										one.put(gpv.getColomnName(), "Institutional");
									}
								}
								else if(gpv.getColomnName().equalsIgnoreCase("sch_from"))
								{
									one.put(gpv.getColomnName(),  obdata[k].toString()+" To "+ obdata[k+1].toString());
								}
								else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
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
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	@SuppressWarnings("unchecked")
	public String viewActivityFull()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("sc.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("country"))
							{
								query.append("co.country_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("state"))
							{
								query.append("s.state_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("city"))
							{
								query.append("c.city_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("territory"))
							{
								query.append("tt.trr_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("rel_type"))
							{
								query.append("type.name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else
							{
								query.append("sc."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							query.append(gpv.getColomnName().toString());
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_plan as sc");
				query.append(" LEFT JOIN territory as tt on tt.id=sc.territory ");
				query.append(" LEFT JOIN city as c on c.id=tt.trr_city ");
				query.append(" LEFT JOIN state as s on s.id=c.name_state ");
				query.append(" LEFT JOIN country as co on co.id=s.country_code ");
				query.append(" LEFT JOIN relationship as type on type.id=sc.rel_type ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=sc.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status WHERE ");
				if(fdate!=null && tdate!=null && !fdate.equalsIgnoreCase("") && !tdate.equalsIgnoreCase(""))
				{
					if(fdate.split("-")[0].length()<3)
					{
						fdate= DateUtil.convertDateToUSFormat(fdate);
					}
					if(tdate.split("-")[0].length()<3)
					{
						tdate= DateUtil.convertDateToUSFormat(tdate);
					}
					query.append("  sc.for_month BETWEEN '"+fdate+"' AND '"+tdate+"' ");
				}
				else
				{
					if(month!=null && !month.equalsIgnoreCase("-1")&& !month.equalsIgnoreCase("undefined"))
					{
						query.append("  sc.for_month LIKE '%"+month+"%' ");
					}
				}
				if(country!=null && !country.equalsIgnoreCase("-1"))
				{
					query.append(" AND co.id = '"+country+"' ");
				}
				if(state!=null && !state.equalsIgnoreCase("-1"))
				{
					query.append(" AND s.id = '"+state+"' ");
				}
				if(city!=null && !city.equalsIgnoreCase("-1"))
				{
					query.append(" AND c.id = '"+city+"' ");
				}
				if(territory!=null && !territory.equalsIgnoreCase("-1"))
				{
					query.append(" AND tt.id = '"+territory+"' ");
				}
				if(type!=null && !type.equalsIgnoreCase("-1"))
				{
					query.append(" AND type.id = '"+type+"' ");
				}
				if(subtype!=null && !subtype.equalsIgnoreCase("-1")&& !subtype.equalsIgnoreCase(""))
				{
					if(subtype.equalsIgnoreCase("Individual"))
					{
						query.append(" AND  sc.rel_sub_type = 'referral_contact_data' ");
					}
					else
					{
						query.append(" AND sc.rel_sub_type = 'referral_institutional_data' ");
					}
				}
				if(emp!=null && !emp.equalsIgnoreCase("-1")&& !emp.equalsIgnoreCase("undefined"))
				{
					query.append(" AND sc.schedule_contact_id IN (SELECT id FROM manage_contact WHERE emp_id IN (SELECT emp_id FROM manage_contact WHERE id =" + emp + ") AND module_name='WFPM') ");
				}
				query.append(" AND sc.activity_flag = '1' ");
				query.append(" ORDER BY sc.for_month ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					ActivityPlannerAction AA=new ActivityPlannerAction();
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("rel_sub_type"))
								{
									if( obdata[k].toString().equalsIgnoreCase("referral_contact_data"))
									{
										one.put(gpv.getColomnName(), "Individual");
									}
									else
									{
										one.put(gpv.getColomnName(), "Institutional");
									}
								}
								else if(gpv.getColomnName().equalsIgnoreCase("sch_from"))
								{
									one.put(gpv.getColomnName(),  obdata[k].toString()+" To "+ obdata[k+1].toString());
								}
								else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
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
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	public List<ConfigurationUtilBean> getTextBox() {
		return textBox;
	}
	public void setTextBox(List<ConfigurationUtilBean> textBox) {
		this.textBox = textBox;
	}
	public List<ConfigurationUtilBean> getDateField() {
		return dateField;
	}
	public void setDateField(List<ConfigurationUtilBean> dateField) {
		this.dateField = dateField;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public String getMonth()
	{
		return month;
	}
	public void setMonth(String month)
	{
		this.month = month;
	}
	public String getDataFor()
	{
		return dataFor;
	}
	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}
	public void setCommonJsonArr(JSONArray commonJsonArr)
	{
		this.commonJsonArr = commonJsonArr;
	}
	public JSONArray getCommonJsonArr()
	{
		return commonJsonArr;
	}
	public Map<String, String> getCommonMap()
	{
		return commonMap;
	}
	public void setCommonMap(Map<String, String> commonMap)
	{
		this.commonMap = commonMap;
	}
	public Map<String, String> getRelationMap()
	{
		return relationMap;
	}
	public void setRelationMap(Map<String, String> relationMap)
	{
		this.relationMap = relationMap;
	}
	public void setFdate(String fdate)
	{
		this.fdate = fdate;
	}
	public String getFdate()
	{
		return fdate;
	}
	public void setTdate(String tdate)
	{
		this.tdate = tdate;
	}
	public String getTdate()
	{
		return tdate;
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
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getSubtype()
	{
		return subtype;
	}
	public void setSubtype(String subtype)
	{
		this.subtype = subtype;
	}
	public void setEmp(String emp)
	{
		this.emp = emp;
	}
	public String getEmp()
	{
		return emp;
	}
	public void setActId(String actId)
	{
		this.actId = actId;
	}
	public String getActId()
	{
		return actId;
	}
	
}
