package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

@SuppressWarnings("serial")
public class ActivityPlannerAction extends GridPropertyBean
{
	private List<GridDataPropertyView> masterViewProp = null;
	private List<ConfigurationUtilBean> textBox = null;
	private List<ConfigurationUtilBean> dropDown = null;
	private List<ConfigurationUtilBean> dateField = null;
	private List<ConfigurationUtilBean> timeField = null;
	private Map<String, String> commonMap;
	private Map<String, String> relationMap;
	private Map<String, String> activityMap;
	private String date;
	private String territory;
	private boolean state = false;
	private boolean city = false;
	private boolean terri = false;
	private JSONArray commonJsonArr;
	private String contactId;
	private String status;
	private String dataFor;
	private String dsrStatus;
	private String eplan_from_buddy;
	private String eplan_from_offering;
	private String eplan_team;
	private List<ActivityPojo> fullDetails;

	@SuppressWarnings("unchecked")
	public String beforeActivityPlanner()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				System.out.println("Date::: "+date);
				if(date!=null && date.split("-")[0].length()<3)
				{
					date = DateUtil.convertDateToUSFormat(date);
				}
				String query = "SELECT scheduleFor,startFrom,dueTill,deadline_date,deadline_time FROM configure_schedule WHERE configure_for='Activity Plan'";
				List dataSchedule = coi.executeAllSelectQuery(query, connectionSpace);
				String dateCompare=DateUtil.getCurrentDateUSFormat();
				String dateCompare2=DateUtil.getCurrentDateUSFormat();
				if(dataSchedule!=null && dataSchedule.size()>0)
				{
					Object[] obj= (Object[])dataSchedule.get(0);
					if(obj[0]!=null && obj[0].toString().equalsIgnoreCase("Date"))
					{
						dateCompare = obj[3].toString();
					}
					else
					{
						dateCompare = DateUtil.getNewDate("day", -(Integer.parseInt( obj[1].toString())), obj[3].toString());
						dateCompare2 =  DateUtil.getNewDate("day", Integer.parseInt( obj[2].toString()),  obj[3].toString());
					}
				}
				String empIdOfuser= (String)session.get("empIdofuser");
				System.out.println("dateCompare :::::::::: "+dateCompare);
				System.out.println("dateCompare2 :::::::::: "+dateCompare2);
				boolean dateStatus = DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(),dateCompare);
				boolean dateStatus2 = DateUtil.comparetwoDates(dateCompare2,DateUtil.getCurrentDateUSFormat());
				System.out.println("dateStatus :::::::::::::: "+dateStatus);
				System.out.println("dateStatus22 :::::::::::::: "+dateStatus2);
				String qry="SELECT for_month,extend_by FROM activity_plan WHERE status='Approved' AND request_id IN (SELECT id FROM manage_contact WHERE emp_id IN (" + empIdOfuser.split("-")[1] + ") AND module_name='WFPM') AND for_month LIKE '%"+DateUtil.getCurrentDateUSFormat().split("-")[0]+"-"+DateUtil.getCurrentDateUSFormat().split("-")[1]+"%' ORDER BY id DESC LIMIT 1";
				List extend = coi.executeAllSelectQuery(qry, connectionSpace);
				if(extend!=null && extend.size()>0)
				{
					Object[] obj= (Object[])extend.get(0);
					
					boolean status = DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(),obj[1].toString());
					if(status)
					{
						createActivity();
						return SUCCESS;
					}
				}
				if(dateStatus && !dateStatus2)
				{
					setAddPageDataFieldsrequest();
					return "request";
				}
				else if(!dateStatus && !dateStatus2)
				{
					
					System.out.println(">>>>>>...  "+String.valueOf(Integer.parseInt((dateCompare2.split("-")[1])+1)));
					if(date.split("-")[1].equalsIgnoreCase(String.valueOf(Integer.parseInt((dateCompare2.split("-")[1]))+1)))
					{
						createActivity();
						return SUCCESS;
					}
					else
					{
						setAddPageDataFieldsrequest();
						return "request";
					}
				}
				else if(!dateStatus && !dateStatus2)
				{
					setAddPageDataFieldsrequest();
					StringBuilder update =new StringBuilder("UPDATE configure_schedule SET deadline_date = '"+DateUtil.getNewDate("day", 30, dateCompare)+"' WHERE configure_for='Activity Plan'");
					coi.updateTableColomn(connectionSpace, update);
					return "request";
				}
				else
				{
					setAddPageDataFieldsrequest();
					return "request";
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
	
	private void createActivity()
	{
		try
		{
			ActivityPojo aps;
			CommonOperInterface coi = new CommonConFactory().createInterface();
			fullDetails=new ArrayList<ActivityPojo>();
			StringBuilder qry=new StringBuilder();
			qry.append("SELECT sc.id,sc.for_month,sc.rel_sub_type,sc.activity_for,at.act_name,sc.sch_from,sc.sch_to, ");
			qry.append(" sc.comments FROM activity_schedule_plan as sc  ");
			qry.append(" LEFT JOIN patientcrm_status_data as escData on escData.id=sc.activity_type  ");
			qry.append(" LEFT JOIN activity_type as at on at.id=escData.status  ");
			qry.append(" WHERE user_name='"+userName+"' AND for_month='"+date+"' ");
			List data = coi.executeAllSelectQuery(qry.toString(), connectionSpace);
			if(data!=null && !data.isEmpty())
			{
				ActivityPlannerAction AA=new ActivityPlannerAction();
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					aps = new ActivityPojo();
					if(object[0]!=null)
					{
						aps.setId(id);
					}
					if(object[1]!=null)
					{
						aps.setFor_month(DateUtil.convertDateToIndianFormat(object[1].toString()));
					}
					if(object[2]!=null && object[3]!=null)
					{
						aps.setActivity_for(AA.fetchActivityFor(object[2].toString(), object[3].toString()));
					}
					if(object[4]!=null)
					{
						aps.setActivity_type(object[4].toString());
					}
					if(object[5]!=null)
					{
						aps.setSchedule_time(object[5].toString()+" - "+object[6].toString());
					}
					if(object[7]!=null)
					{
						aps.setComment(object[7].toString());
					}
					fullDetails.add(aps);
				}
			}
			setAddPageDataFields();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private void setAddPageDataFieldsrequest()
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
	private void setAddPageDataFields()
	{
		try
		{
			List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_activity_planner_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_planner_configuration");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
			String query = "SELECT mapped_for,territory FROM employee_location WHERE employee=" + loggedDetails[0];
			List mappedData = coi.executeAllSelectQuery(query, connectionSpace);
			String mappedfor = null, mappedId = null;
			StringBuilder mapedId = new StringBuilder();
			if (mappedData != null && mappedData.size() > 0)
			{
				for (Iterator iterator = mappedData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						mappedfor = object[0].toString();
					}
					if (object[1] != null)
					{
						mapedId.append(object[1].toString() + ",");
					}
				}
				mappedId = mapedId.toString().substring(0, mapedId.toString().length() - 1);
			}
			textBox = new ArrayList<ConfigurationUtilBean>();
			dropDown = new ArrayList<ConfigurationUtilBean>();
			dateField = new ArrayList<ConfigurationUtilBean>();
			timeField = new ArrayList<ConfigurationUtilBean>();
			if (fieldList != null && fieldList.size() > 0)
			{
				for (GridDataPropertyView gdp : fieldList)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_time") && !gdp.getColomnName().equalsIgnoreCase("created_date"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						textBox.add(objdata);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("D"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						dropDown.add(objdata);
						if (gdp.getColomnName().equalsIgnoreCase("country"))
						{
							commonMap = new LinkedHashMap<String, String>();
							if (mappedfor != null && mappedfor.equalsIgnoreCase("CO"))
							{
								state = true;
								city = true;
								terri = true;
								query = "SELECT id,state_name FROM state  WHERE country_code IN(" + mappedId + ") ORDER BY state_name";
							}
							else if (mappedfor != null && mappedfor.equalsIgnoreCase("ST"))
							{
								city = true;
								terri = true;
								query = "SELECT id,city_name FROM city  WHERE name_state IN(" + mappedId + ") ORDER BY city_name";
							}
							else if (mappedfor != null && mappedfor.equalsIgnoreCase("CI"))
							{
								terri = true;
								query = "SELECT id,trr_name FROM territory  WHERE trr_city IN(" + mappedId + ") ORDER BY trr_name";
							}
							else if (mappedfor != null && mappedfor.equalsIgnoreCase("TR"))
							{
								territory = mappedfor;
							}
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
						}
						if (gdp.getColomnName().equalsIgnoreCase("rel_type"))
						{
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
						}
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("date"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						dateField.add(objdata);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("time"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						timeField.add(objdata);
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
	public String addActivity()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (request.getParameter("schedule_plan").equalsIgnoreCase("Field Activity"))
				{
					List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_activity_planner_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_planner_configuration");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					TableColumes ob1 = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("country") && !gdp.getColomnName().equalsIgnoreCase("state") && !gdp.getColomnName().equalsIgnoreCase("city") )
						{
							ob1 = new TableColumes();
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
						}
					}
					ob1 = new TableColumes();
					ob1.setColumnname("schedule_plan");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("activity_status");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default 'Approved'");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("schedule_contact_id");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("activity_flag");
					ob1.setDatatype("varchar(50)");
					ob1.setConstraint("default '0'");
					Tablecolumesaaa.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("advance_required");
					ob1.setDatatype("varchar(10)");
					ob1.setConstraint("default 'No'");
					Tablecolumesaaa.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("amount");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default '0'");
					Tablecolumesaaa.add(ob1);
					
					ob1 = new TableColumes();
					ob1.setColumnname("ticket_no");
					ob1.setDatatype("varchar(100)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					cbt.createTable22("activity_schedule_plan", Tablecolumesaaa, connectionSpace);
					
					String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String activity_for[] = request.getParameterValues("activity_for");
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("SubRel"))
						{
							if (Parmname.equalsIgnoreCase("rel_sub_type"))
							{
								if (paramValue.equalsIgnoreCase("Individual"))
								{
									ob = new InsertDataTable();
									ob.setColName(Parmname);
									ob.setDataName("referral_contact_data");
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName(Parmname);
									ob.setDataName("referral_institutional_data");
									insertData.add(ob);
								}
							}
							else if (!Parmname.equalsIgnoreCase("activity_for") && !Parmname.equalsIgnoreCase("status"))
							{
								if(Parmname.equalsIgnoreCase("for_month"))
								{
									if(paramValue.split("-")[0].length()<3)
									{
										ob = new InsertDataTable();
										ob.setColName(Parmname);
										ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
										insertData.add(ob);
									}
									else
									{
										ob = new InsertDataTable();
										ob.setColName(Parmname);
										ob.setDataName(paramValue);
										insertData.add(ob);
									}
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName(Parmname);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
							}
						}
					}
					ob = new InsertDataTable();
					ob.setColName("schedule_contact_id");
					ob.setDataName(loggedDetails[0]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("created_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("created_time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					if(status!=null && status.equalsIgnoreCase("changePlan"))
					{
						ob = new InsertDataTable();
						ob.setColName("activity_flag");
						ob.setDataName("2");
						insertData.add(ob);
					}
					boolean status = false;
					if (activity_for != null)
					{
						for (int i = 0; i < activity_for.length; i++)
						{
							if (activity_for[i] != null && !activity_for[i].equalsIgnoreCase(""))
							{
								String query = "SELECT ticket_no FROM activity_schedule_plan ORDER BY id DESC LIMIT 1";
								List ticket_no = cbt.executeAllSelectQuery(query, connectionSpace);
								if(ticket_no!=null &&  !ticket_no .isEmpty() && ticket_no.get(0)!=null)
								{
									String s= ticket_no.get(0).toString();
									String finals = s.substring(4,s.length());
									int a = Integer.parseInt(finals)+1;
									ob = new InsertDataTable();
									ob.setColName("ticket_no");
									ob.setDataName(s.substring(0, 4)+a);
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName("ticket_no");
									ob.setDataName("ACVT1000");
									insertData.add(ob);
								}
								ob = new InsertDataTable();
								ob.setColName("activity_for");
								ob.setDataName(activity_for[i].trim());
								insertData.add(ob);

								status = cbt.insertIntoTable("activity_schedule_plan", insertData, connectionSpace);
								insertData.remove(insertData.size() - 1);
							}
						}
					}
					else
					{
						status = cbt.insertIntoTable("activity_schedule_plan", insertData, connectionSpace);
					}
					if (status)
					{
						addActionMessage("Activity Schedule Successfully !!!");
					}
					else
					{
						addActionMessage("Oop's there is some problem !!!");
					}

				}
				else if (request.getParameter("schedule_plan").equalsIgnoreCase("Event"))
				{
					// Create Event Plan Data Table
					List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_event_plan_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_configuration");
					CommonOperInterface cbt = new CommonConFactory().createInterface();

					TableColumes ob1 = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("eplan_parameter"))
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							if (gdp.getColomnName().equalsIgnoreCase("status"))
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
				    ob1=new TableColumes();
                    ob1.setColumnname("event_status");
                    ob1.setDatatype("varchar(255)");
                    ob1.setConstraint("default 'Approved'");
                    Tablecolumesaaa.add(ob1);

	                ob1=new TableColumes();
                    ob1.setColumnname("eplan_flag");
                    ob1.setDatatype("varchar(255)");
                    ob1.setConstraint("default '0'");
                    Tablecolumesaaa.add(ob1);
                    
	                ob1=new TableColumes();
                    ob1.setColumnname("eplan_dcr");
                    ob1.setDatatype("varchar(255)");
                    ob1.setConstraint("default 'No'");
                    Tablecolumesaaa.add(ob1);
                    
                    ob1=new TableColumes();
                    ob1.setColumnname("event_status_comments");
                    ob1.setDatatype("varchar(255)");
                    Tablecolumesaaa.add(ob1);
                    
                    ob1=new TableColumes();
                    ob1.setColumnname("event_status_date");
                    ob1.setDatatype("varchar(255)");
                    Tablecolumesaaa.add(ob1);

                    ob1=new TableColumes();
                    ob1.setColumnname("dsr_flag");
                    ob1.setDatatype("varchar(255)");
                    ob1.setConstraint("default '0'");
                    Tablecolumesaaa.add(ob1);
                    
                    ob1=new TableColumes();
                    ob1.setColumnname("dsr_status");
                    ob1.setDatatype("varchar(255)");
                    ob1.setConstraint("default 'No'");
                    Tablecolumesaaa.add(ob1);
                    
                    ob1=new TableColumes();
                    ob1.setColumnname("dsr_status_date");
                    ob1.setDatatype("varchar(255)");
                    Tablecolumesaaa.add(ob1);
                    
                    ob1=new TableColumes();
                    ob1.setColumnname("dsr_status_comments");
                    ob1.setDatatype("varchar(255)");
                    Tablecolumesaaa.add(ob1);

                    ob1=new TableColumes();
                    ob1.setColumnname("manager_id");
                    ob1.setDatatype("varchar(255)");
                    Tablecolumesaaa.add(ob1);
                    
                    ob1=new TableColumes();
                    ob1.setColumnname("ticket_no");
                    ob1.setDatatype("varchar(100)");
                    Tablecolumesaaa.add(ob1);

					cbt.createTable22("event_plan", Tablecolumesaaa, connectionSpace);

					// Event Parameters Data table
					statusColName = Configuration.getConfigurationData("mapped_event_plan_parameters_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_parameters_configuration");
					cbt = new CommonConFactory().createInterface();
					ob1 = null;
					Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (true)
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							if (gdp.getColomnName().equalsIgnoreCase("status"))
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
					cbt.createTable22("event_plan_parameters", Tablecolumesaaa, connectionSpace);

					// Event ROI Parameters Data table
					statusColName = Configuration.getConfigurationData("mapped_event_plan_parameters_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_roi_parameters_configuration");
					cbt = new CommonConFactory().createInterface();
					ob1 = null;
					Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (true)
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							if (gdp.getColomnName().equalsIgnoreCase("status"))
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
					cbt.createTable22("event_plan_roi_parameters", Tablecolumesaaa, connectionSpace);

					// Insert Data Lists
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertEParamData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertERoiData = new ArrayList<InsertDataTable>();

					InsertDataTable ob = null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();

					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
						{
							if (!(Parmname.equalsIgnoreCase("rel_type")) && !(Parmname.equalsIgnoreCase("activity_for")) && !(Parmname.equalsIgnoreCase("activity_type")) && !(Parmname.contains("eroi")) && !(Parmname.contains("eparam")) && !(Parmname.equalsIgnoreCase("eplan_from_buddy")) && !(Parmname.equalsIgnoreCase("eplan_from_offering")) && !(Parmname.equalsIgnoreCase("eplan_team")) && !(Parmname.equalsIgnoreCase("SubRel")) && !(Parmname.equalsIgnoreCase("rel_sub_type")&& !Parmname.equalsIgnoreCase("status")))
							{
								if(Parmname.equalsIgnoreCase("for_month")){
	                    			 ob=new InsertDataTable();
		                             ob.setColName(Parmname);
		                             ob.setDataName(DateUtil.convertDateToUSFormat( paramValue) );
		                             insertData.add(ob);
	                    		}else{
	                    		 ob=new InsertDataTable();
	                             ob.setColName(Parmname);
	                             ob.setDataName(paramValue);
	                             insertData.add(ob);
	                    		}
							}
							else if (Parmname.contains("eparam"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
								insertEParamData.add(ob);
							}
							else if (Parmname.contains("eroi"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
								insertERoiData.add(ob);
							}
							else if (Parmname.equalsIgnoreCase("eplan_from_buddy"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(eplan_from_buddy);
								insertData.add(ob);
							}
							else if (Parmname.equalsIgnoreCase("eplan_from_offering"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(eplan_from_offering);
								insertData.add(ob);
							}
							else if (Parmname.equalsIgnoreCase("eplan_team"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(eplan_team);
								insertData.add(ob);
							}
							else if (Parmname.equalsIgnoreCase("rel_sub_type"))
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(request.getParameter("SubRel"));
								insertData.add(ob);
							}
						}
					}

					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					String loggedDetail[] = CUA.getEmpDetailsByUserName("WFPM", userName);

					ob = new InsertDataTable();
					ob.setColName("eplan_owner");
					ob.setDataName(loggedDetail[0]);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
					
					String query = "SELECT ticket_no FROM event_plan ORDER BY id DESC LIMIT 1";
					List ticket_no = cbt.executeAllSelectQuery(query, connectionSpace);
					if(ticket_no!=null &&  !ticket_no .isEmpty() && ticket_no.get(0)!=null)
					{
						String s= ticket_no.get(0).toString();
						String finals = s.substring(3,s.length());
						int a = Integer.parseInt(finals)+1;
						ob = new InsertDataTable();
						ob.setColName("ticket_no");
						ob.setDataName(s.substring(0, 3)+a);
						insertData.add(ob);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("ticket_no");
						ob.setDataName("EVT1000");
						insertData.add(ob);
					}

					int maxid = -1;
					if (insertData != null && insertData.size() > 0)
					{
						maxid = cbt.insertDataReturnId("event_plan", insertData, connectionSpace);
						// insert event parameters
						if (maxid != -1)
						{
							int count = 0;
							if (insertEParamData.size() > 0)
							{
								insertData.clear();

								ob = new InsertDataTable();
								ob.setColName("userName");
								ob.setDataName(userName);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("date");
								ob.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("time");
								ob.setDataName(DateUtil.getCurrentTimeHourMin());
								insertData.add(ob);

								InsertDataTable obt = new InsertDataTable();
								obt.setColName("eplan_id");
								obt.setDataName(maxid);
								insertData.add(obt);

								for (Iterator iter = insertEParamData.iterator(); iter.hasNext();)
								{
									InsertDataTable idt = (InsertDataTable) iter.next();
									if (idt != null && idt.getDataName().toString().trim().length() > 0)
									{

										InsertDataTable obt1 = new InsertDataTable();
										obt1.setColName("eplan_pname");
										obt1.setDataName(idt.getColName().split("eparam")[1]);
										insertData.add(obt1);

										InsertDataTable obt2 = new InsertDataTable();
										obt2.setColName("eplan_pvalue");
										obt2.setDataName(idt.getDataName());
										insertData.add(obt2);
										boolean status = cbt.insertIntoTable("event_plan_parameters", insertData, connectionSpace);
										insertData.remove(obt1);
										insertData.remove(obt2);
										if (status)
											count++;
									}
								}
							}
							// //insert ROI Parameters
							if (insertERoiData.size() > 0)
							{
								count = 0;
								insertData.clear();

								ob = new InsertDataTable();
								ob.setColName("userName");
								ob.setDataName(userName);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("date");
								ob.setDataName(DateUtil.getCurrentDateUSFormat());
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("time");
								ob.setDataName(DateUtil.getCurrentTimeHourMin());
								insertData.add(ob);

								InsertDataTable obt = new InsertDataTable();
								obt.setColName("eplan_id");
								obt.setDataName(maxid);
								insertData.add(obt);

								for (Iterator iter = insertERoiData.iterator(); iter.hasNext();)
								{
									InsertDataTable idt = (InsertDataTable) iter.next();
									if (idt != null && idt.getDataName().toString().trim().length() > 0)
									{

										InsertDataTable obt1 = new InsertDataTable();
										obt1.setColName("eplan_roi_parameter");
										obt1.setDataName(idt.getColName().split("eroi")[1]);
										insertData.add(obt1);

										InsertDataTable obt2 = new InsertDataTable();
										obt2.setColName("eplan_roi_value");
										obt2.setDataName(idt.getDataName());
										insertData.add(obt2);

										boolean status = cbt.insertIntoTable("event_plan_roi_parameters", insertData, connectionSpace);
										insertData.remove(obt1);
										insertData.remove(obt2);
										if (status)
											count++;
									}
								}
							}

						}
						else
						{
							addActionMessage("Max id Not !!!");
						}
						addActionMessage("Event Planned Successfully !!!");
					}
				}
				return "success";
			}
			catch (Exception e)
			{
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

	public String beforeActivityPlannerView()
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
			masterViewProp = new ArrayList<GridDataPropertyView>();

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_activity_planner_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_planner_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
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
	public String getHolidayes()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List list = cbt.executeAllSelectQuery("select id,holiday_date,holidayname from holiday_list", connectionSpace);
				commonJsonArr = new JSONArray();
				if (list != null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[2] != null)
						{
							obj = new JSONObject();
							obj.put("holiday", object[2].toString());
							obj.put("id", object[0].toString());
							obj.put("date", DateUtil.convertDateToIndianFormat(object[1].toString()));
							commonJsonArr.add(obj);
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

	@SuppressWarnings("unchecked")
	public String fetchActivityEvent()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				commonJsonArr = new JSONArray();
				JSONArray j1 = new JSONArray();
				JSONArray j2 = new JSONArray();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				
				String empId = (String)session.get("empIdofuser").toString().split("-")[1];
				ActivityPlannerHelper AH=new ActivityPlannerHelper();
				String contact = AH.searchAllContactId(empId, cbt);
				query.append("SELECT asp.id,at.act_name,asp.schedule_plan,asp.for_month,asp.activity_status,rel_sub_type,activity_for,max(dsr.id) as dsrId,dsr.manager_status, dsr.dsr_flag,activity_flag  FROM activity_schedule_plan as asp ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=asp.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status ");
				query.append("LEFT JOIN activity_schedule_dsr_fill as dsr on dsr.activityId=asp.id ");
				query.append("WHERE asp.schedule_plan='Field Activity' ");
				if (contactId != null && !contactId.equalsIgnoreCase("-1") && !contactId.equalsIgnoreCase(""))
				{
					query.append(" AND asp.schedule_contact_id IN (SELECT id FROM manage_contact WHERE emp_id IN (SELECT emp_id FROM manage_contact WHERE id =" + contactId + ") AND module_name='WFPM') ");
				}
				if (status != null && !status.equalsIgnoreCase("-1") && !status.equalsIgnoreCase(""))
				{
					query.append(" AND asp.activity_status = '" + status + "'");
				}
				if (dataFor != null && !dataFor.equalsIgnoreCase("-1") && !dataFor.equalsIgnoreCase(""))
				{
					query.append(" AND asp.activity_flag IN (" + dataFor + ") ");
				}
				else
				{
					query.append(" AND asp.schedule_contact_id IN ( '" + contact + "')");
				}
				query.append(" GROUP BY asp.id ");
				List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[2] != null)
						{
							obj = new JSONObject();
							obj.put("Activity", object[1].toString());
							obj.put("id", object[0].toString());
							obj.put("schedule", object[2].toString());
							obj.put("month", DateUtil.convertDateToIndianFormat(object[3].toString()));
							obj.put("status", object[4].toString());
							obj.put("activity_for", fetchActivityFor(object[5].toString(), object[6].toString()));
							if (object[7] != null)
							{
								obj.put("dsr", object[7].toString());
							}
							else
							{
								obj.put("dsr", "No");
							}
							if (object[8] != null)
							{
								obj.put("dsr_status", object[8].toString());
							}
							else
							{
								obj.put("dsr_status", "No");
							}
							if (object[9] != null)
							{
								obj.put("dsr_flag", object[9].toString());
							}
							else
							{
								obj.put("dsr_flag", "No");
							}
							if (object[10] != null && !object[10].toString().equalsIgnoreCase(""))
							{
								obj.put("flag", object[10].toString());
							}
							else
							{
								obj.put("flag", "No");
							}
							j1.add(obj);
						}
					}
					list.clear();
				}
				query.setLength(0);
				query.append(" SELECT asp.id,asp.eplan_title,asp.schedule_plan,asp.for_month,at.act_name,asp.event_status,asp.dsr_status," +
						"asp.eplan_flag  ,asp.dsr_flag " +
						"FROM event_plan as asp ");
				query.append(" LEFT JOIN activity_type as at on at.id=asp.eplan_type ");
				query.append("WHERE asp.schedule_plan = 'Event'  AND at.act_type='Event' ");
				if (dataFor != null && !dataFor.equalsIgnoreCase("-1") && !dataFor.equalsIgnoreCase(""))
				{
					query.append(" AND asp.eplan_flag IN (" + dataFor + ")");
				}
				else
				{
					query.append(" AND asp.eplan_owner IN ( '" + contact + "')");
				}
				if (status != null && !status.equalsIgnoreCase("-1") && !status.equalsIgnoreCase(""))
				{
					query.append(" AND asp.event_status = '" + status + "'");
				}
				if (contactId != null && !contactId.equalsIgnoreCase("-1") && !contactId.equalsIgnoreCase(""))
				{
					query.append(" AND asp.eplan_owner IN (SELECT id FROM manage_contact WHERE emp_id IN (SELECT emp_id FROM manage_contact WHERE id =" + contactId + ") AND module_name='WFPM') ");
				}
				list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[2] != null)
						{
							obj = new JSONObject();
							obj.put("Title", object[1].toString());
							obj.put("id", object[0].toString());
							obj.put("schedule", object[2].toString());
							obj.put("month", DateUtil.convertDateToIndianFormat(object[3].toString()));
							obj.put("Activity", object[4].toString());
							obj.put("status", object[5].toString());
							if( object[6]!=null)
							{
								obj.put("dsr_status",  object[6].toString());
							}
							else
							{
								obj.put("dsr_status", "No");
							}
							if( object[7]!=null)
							{
								obj.put("flag",  object[7].toString());
							}
							else
							{
								obj.put("flag", "No");
							}
							if( object[8]==null)
							{
								obj.put("dsr_flag",  "0");
							}
							else if( object[8]!=null)
							{
								obj.put("dsr_flag", object[8].toString());
							}
							j2.add(obj);
						}
					}
				}
				commonJsonArr.add(j1);
				commonJsonArr.add(j2);
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
	public String fetchActivityFor(String tablename, String id)
	{
		String activityFor = null;
		try
		{
			String query = null;
			if (tablename != null && tablename.equalsIgnoreCase("referral_institutional_data"))
			{
				query = "SELECT org_name FROM referral_institutional_data WHERE id=" + id;
			}
			else
			{
				query = "SELECT emp.emp_name FROM referral_contact_data as con LEFT JOIN primary_contact as emp on emp.id=con.map_emp_id WHERE con.id=" + id;
			}
			if (query != null)
			{
				List list = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (list != null && !list.isEmpty())
				{
					activityFor = list.get(0).toString();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return activityFor;
	}

	public String approveRequest()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				StringBuilder query = new StringBuilder();
				if (status != null && status.equalsIgnoreCase("activity_show"))
				{
					query.append(" UPDATE  activity_schedule_plan SET activity_flag = '1' WHERE for_month LIKE '%" + date + "%' AND schedule_contact_id = '" + loggedDetails[0] + "' AND activity_flag=0");
				}
				else
				{
					query.append(" UPDATE  event_plan SET event_flag = '1' WHERE for_month LIKE '%" + date + "%' AND eplan_owner = '" + loggedDetails[0] + "' AND event_flag=0");
				}

				boolean status = cbt.updateTableColomn(connectionSpace, query);
				if (status)
				{
					addActionMessage("Request Sent for Approval !!!");
				}
				else
				{
					addActionMessage("OOPS there is some error!!");
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

	public String approvedsrRequest()
	{
		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				StringBuilder query = new StringBuilder();
				if (status != null && status.equalsIgnoreCase("act"))
				{
					query.append(" UPDATE  activity_schedule_dsr_fill SET dsr_flag = '1' WHERE for_month LIKE '%" + date + "%' AND dsr_user_contact_id = '" + loggedDetails[0] + "' AND dsr_flag=0");
				}
				else
				{
					query.append(" UPDATE  event_plan SET event_flag = '1' WHERE for_month LIKE '%" + date + "%' AND eplan_owner = '" + loggedDetails[0] + "' AND event_flag=0");
				}

				boolean status = cbt.updateTableColomn(connectionSpace, query);
				if (status)
				{
					addActionMessage("DSR Sent for Approval !!!");
				}
				else
				{
					addActionMessage("OOPS there is some error!!");
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

	public JSONArray getCommonJsonArr()
	{
		return commonJsonArr;
	}

	public void setCommonJsonArr(JSONArray commonJsonArr)
	{
		this.commonJsonArr = commonJsonArr;
	}

	public List<ConfigurationUtilBean> getTextBox()
	{
		return textBox;
	}

	public void setTextBox(List<ConfigurationUtilBean> textBox)
	{
		this.textBox = textBox;
	}

	public List<ConfigurationUtilBean> getDropDown()
	{
		return dropDown;
	}

	public void setDropDown(List<ConfigurationUtilBean> dropDown)
	{
		this.dropDown = dropDown;
	}

	public List<ConfigurationUtilBean> getDateField()
	{
		return dateField;
	}

	public void setDateField(List<ConfigurationUtilBean> dateField)
	{
		this.dateField = dateField;
	}

	public List<ConfigurationUtilBean> getTimeField()
	{
		return timeField;
	}

	public void setTimeField(List<ConfigurationUtilBean> timeField)
	{
		this.timeField = timeField;
	}

	public Map<String, String> getCommonMap()
	{
		return commonMap;
	}

	public void setCommonMap(Map<String, String> commonMap)
	{
		this.commonMap = commonMap;
	}

	public void setRelationMap(Map<String, String> relationMap)
	{
		this.relationMap = relationMap;
	}

	public Map<String, String> getRelationMap()
	{
		return relationMap;
	}

	public void setActivityMap(Map<String, String> activityMap)
	{
		this.activityMap = activityMap;
	}

	public Map<String, String> getActivityMap()
	{
		return activityMap;
	}

	public void setTerritory(String territory)
	{
		this.territory = territory;
	}

	public String getTerritory()
	{
		return territory;
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public boolean isCity()
	{
		return city;
	}

	public void setCity(boolean city)
	{
		this.city = city;
	}

	public boolean isTerri()
	{
		return terri;
	}

	public void setTerri(boolean terri)
	{
		this.terri = terri;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDate()
	{
		return date;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus()
	{
		return status;
	}

	public String getEplan_from_buddy()
	{
		return eplan_from_buddy;
	}

	public void setEplan_from_buddy(String eplanFromBuddy)
	{
		eplan_from_buddy = eplanFromBuddy;
	}

	public String getEplan_from_offering()
	{
		return eplan_from_offering;
	}

	public void setEplan_from_offering(String eplanFromOffering)
	{
		eplan_from_offering = eplanFromOffering;
	}

	public String getEplan_team()
	{
		return eplan_team;
	}

	public void setEplan_team(String eplanTeam)
	{
		eplan_team = eplanTeam;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDsrStatus(String dsrStatus)
	{
		this.dsrStatus = dsrStatus;
	}

	public String getDsrStatus()
	{
		return dsrStatus;
	}

	public void setFullDetails(List<ActivityPojo> fullDetails)
	{
		this.fullDetails = fullDetails;
	}

	public List<ActivityPojo> getFullDetails()
	{
		return fullDetails;
	}

}