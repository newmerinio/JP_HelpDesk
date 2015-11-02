package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
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
public class DSRFillAction extends GridPropertyBean
{
	private List<ConfigurationUtilBean> textBox = null;
	private List<ConfigurationUtilBean> dropDown = null;
	private List<ConfigurationUtilBean> dateField = null;
	private List<ConfigurationUtilBean> file = null;
	private String date;
	private String activityId;
	private String dsrId;
	private Map<String, String> reasonMap;
	private Map<String, String> expenseMap;
	private Map<String, String> dataMap;
	private String activityType;
	private String activity_for;
	public File document;
	private String documentContentType;
	private String documentFileName;
	private List<GridDataPropertyView> masterViewProp = null;
	private List<Object> masterViewList;
	private String emp;
	private String month;
	private String amount;
	private String sch_date;
	private String comm;
	
	@SuppressWarnings("unchecked")
	public String beforeDSRFillAdd()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				reasonMap = new LinkedHashMap<String, String>();
				expenseMap = new LinkedHashMap<String, String>();
				CommonOperInterface coi = new CommonConFactory().createInterface();
				String query= " SELECT id,exp_parameter FROM expense_parameter WHERE status='Active' ORDER BY exp_parameter ";
				List xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
				if (xepenseList != null && xepenseList.size() > 0)
				{
					for (Iterator<?> iterator = xepenseList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							expenseMap.put(object[0].toString(), object[1].toString());
						}
					}
					xepenseList.clear();
				}
				String tableMapped=null;
				String activityTypeId=null;
				query= "SELECT rel_sub_type  FROM activity_schedule_plan WHERE id= "+activityId;
				xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
				if (xepenseList != null && xepenseList.size() > 0)
				{
					tableMapped =xepenseList.get(0).toString();
				}
				if(tableMapped!=null)
				{
					if(tableMapped.equalsIgnoreCase("referral_contact_data"))
					{
						query= "SELECT pc.emp_name,at.act_name,at.id,sp.amount,sp.sch_from,sp.sch_to,sp.comments  FROM activity_schedule_plan as sp LEFT JOIN referral_contact_data as ca on ca.id=sp.activity_for " +
								"LEFT JOIN primary_contact as pc on pc.id=ca.map_emp_id LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type " +
								"LEFT JOIN activity_type as at on ps.status = at.id WHERE sp.id= "+activityId;
					}
					else
					{
						query="SELECT ca.org_name,at.act_name,at.id,sp.amount,sp.sch_from,sp.sch_to,sp.comments  FROM activity_schedule_plan as sp LEFT JOIN referral_institutional_data as ca on ca.id=sp.activity_for " +
								"LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type LEFT JOIN activity_type as at on ps.status = at.id " +
								"WHERE sp.id= "+activityId;
					}
					xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
					if (xepenseList != null && xepenseList.size() > 0)
					{
						for (Iterator<?> iterator = xepenseList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								activity_for = object[0].toString();
								activityType = object[1].toString();
								activityTypeId = object[2].toString();
								if(object[3]!=null)
								{
									amount = object[3].toString();
								}
								else
								{
									amount = "No";
								}
								if(object[4]!=null && object[5]!=null)
								{
									sch_date = object[4].toString()+" - "+object[5].toString();
								}
								if(object[6]!=null)
								{
									comm = object[6].toString();
								}
							}
						}
						xepenseList.clear();
					}
				}
				query= " SELECT id,ract_resaon FROM activity_type_reason WHERE ract_name='"+activityTypeId+"' AND status='Active' ORDER BY ract_resaon ";
				xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
				if (xepenseList != null && xepenseList.size() > 0)
				{
					for (Iterator<?> iterator = xepenseList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							reasonMap.put(object[0].toString(), object[1].toString());
						}
					}
				}
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
			List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_activity_dsr_fill_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_dsr_fill_configuration");
		
			textBox = new ArrayList<ConfigurationUtilBean>();
			dropDown = new ArrayList<ConfigurationUtilBean>();
			dateField = new ArrayList<ConfigurationUtilBean>();
			file = new ArrayList<ConfigurationUtilBean>();
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
					else if (gdp.getColType().trim().equalsIgnoreCase("F"))
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
						file.add(objdata);
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
	public String addDsr()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_activity_dsr_fill_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_dsr_fill_configuration");
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : statusColName)
				{
					if(!gdp.getColomnName().equalsIgnoreCase("activity_for") && !gdp.getColomnName().equalsIgnoreCase("activity_type")&& !gdp.getColomnName().equalsIgnoreCase("expenses")&& !gdp.getColomnName().equalsIgnoreCase("value_text")&& !gdp.getColomnName().equalsIgnoreCase("for_date"))
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
				ob1.setColumnname("activityId");
				ob1.setDatatype("varchar(50)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("dsr_user_contact_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("activity_schedule_dsr_fill", Tablecolumesaaa, connectionSpace);
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
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
						if(Parmname.equalsIgnoreCase("for_month"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							insertData.add(ob);
						}
						else if(!Parmname.equalsIgnoreCase("expenses") && !Parmname.equalsIgnoreCase("value_text"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
				}
				
				ob = new InsertDataTable();
				ob.setColName("dsr_user_contact_id");
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

				boolean status = false;
			
				int maxid = cbt.insertDataReturnId("activity_schedule_dsr_fill", insertData, connectionSpace);
				if (maxid>0)
				{
					insertData.clear();
					String expense[]=request .getParameterValues("expenses");
					String value[]=request .getParameterValues("value_text");
					
					createTableExpense();
					if(expense!=null && value!=null)
					{
						ob = new InsertDataTable();
						ob.setColName("dsr_id");
						ob.setDataName(maxid);
						insertData.add(ob);
						
						for(int i=0;i<expense.length;i++)
						{
							if(expense[i]!=null && !expense[i].toString().equalsIgnoreCase("-1"))
							{
								ob = new InsertDataTable();
								ob.setColName("expenses");
								ob.setDataName(expense[i]);
								insertData.add(ob);
								if(value[i]!=null && !value[i].toString().equalsIgnoreCase(""))
								{
									ob = new InsertDataTable();
									ob.setColName("value_text");
									ob.setDataName(value[i]);
									insertData.add(ob);
								}
								String renameFilePath = null;
								if (documentFileName != null)
								{
									String document1[]=documentFileName.split(",");
									renameFilePath = new CreateFolderOs().createUserDir("DSR Doc") + "//" + DateUtil.mergeDateTime() + getDocumentFileName().split(",")[i];
									String storeFilePath = new CreateFolderOs().createUserDir("DSR Doc") + "//" + getDocumentFileName().split(",")[i];
									String str = renameFilePath.replace("//", "/");
									if (storeFilePath != null)
									{
										File theFile = new File(storeFilePath);
										File newFileName = new File(str);
										if (theFile != null)
										{
											try
											{
												FileUtils.copyFile(document, theFile);
												if (theFile.exists())
													theFile.renameTo(newFileName);
											}
											catch (IOException e)
											{
												e.printStackTrace();
											}
											if (theFile != null)
											{
												ob = new InsertDataTable();
												ob.setColName("document");
												ob.setDataName(document1[i]);
												insertData.add(ob);
											}
										}
									}
								}
								status = cbt.insertIntoTable("activity_schedule_dsr_fill_expenses", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
								if(value[i]!=null && !value[i].toString().equalsIgnoreCase(""))
								{
									insertData.remove(insertData.size()-1);
									if (documentFileName != null)
									{
										insertData.remove(insertData.size()-1);
									}
								}
							}
						}
					}
					if(status)
					{
						addActionMessage("DSR Fill Successfully !!!");
					}
					else
					{
						addActionMessage("Oop's there is some problem !!!");
					}
				}
				else
				{
					addActionMessage("Oop's there is some problem !!!");
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
	private void createTableExpense()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			TableColumes ob1 = null;
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
	
			ob1 = new TableColumes();
			ob1.setColumnname("dsr_id");
			ob1.setDatatype("varchar(50)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("expenses");
			ob1.setDatatype("varchar(20)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("value_text");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("document");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("value_text2");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("value_text2_reason");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("manager_id");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("manager_date_time");
			ob1.setDatatype("varchar(100)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			cbt.createTable22("activity_schedule_dsr_fill_expenses", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unchecked")
	public String beforeDCRDisapprove()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				String query= null;
				List xepenseList=null;
				dataMap = new LinkedHashMap<String, String>();
				String tableMapped=null;
				query= "SELECT rel_sub_type  FROM activity_schedule_plan WHERE id= "+activityId;
				xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
				if (xepenseList != null && xepenseList.size() > 0)
				{
					tableMapped =xepenseList.get(0).toString();
				}
				if(tableMapped!=null)
				{
					if(tableMapped.equalsIgnoreCase("referral_contact_data"))
					{
						query= "SELECT pc.emp_name,at.act_name,at.id ,sp.sch_from,sp.sch_to,dsr.activity_status,dsr.id as dsrId FROM activity_schedule_plan as sp LEFT JOIN activity_schedule_dsr_fill as dsr on dsr.activityId=sp.id LEFT JOIN referral_contact_data as ca on ca.id=sp.activity_for " +
								"LEFT JOIN primary_contact as pc on pc.id=ca.map_emp_id LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type " +
								"LEFT JOIN activity_type as at on ps.status = at.id WHERE sp.id= "+activityId;
					}
					else
					{
						query="SELECT ca.org_name,at.act_name,at.id ,sp.sch_from,sp.sch_to,dsr.activity_status,dsr.id as dsrId FROM activity_schedule_plan as sp LEFT JOIN activity_schedule_dsr_fill as dsr on dsr.activityId=sp.id LEFT JOIN referral_institutional_data as ca on ca.id=sp.activity_for " +
								"LEFT JOIN patientcrm_status_data as ps on ps.id = sp.activity_type LEFT JOIN activity_type as at on ps.status = at.id " +
								"WHERE sp.id= "+activityId;
					}
					xepenseList=coi.executeAllSelectQuery(query, connectionSpace);
					if (xepenseList != null && xepenseList.size() > 0)
					{
						for (Iterator<?> iterator = xepenseList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								dataMap . put("For Month", DateUtil.convertDateToIndianFormat(date));
								if(object[0]!=null)
								{
									dataMap . put("Activity For", object[0].toString());
								}
								else
								{
									dataMap . put("Activity For", "NA");
								}
								if(object[1]!=null)
								{
									dataMap . put("Activity Type", object[1].toString());
								}
								else
								{
									dataMap . put("Activity Type", "NA");
								}
								if(object[3]!=null && object[4]!=null)
								{
									dataMap . put("Schedule", object[3].toString()+" - "+object[4].toString());
								}
								else
								{
									dataMap . put("Schedule", "NA");
								}
								if(object[5]!=null)
								{
									dataMap . put("DCR Status", object[5].toString());
								}
								else
								{
									dataMap . put("DCR Status", "NA");
								}
								if(object[6]!=null)
								{
									dataMap . put("DCR Id", object[6].toString());
								}
								else
								{
									dataMap . put("DCR Id", "NA");
								}
							}
						}
						xepenseList.clear();
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
	public String addDsrApproval()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
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
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
							&& !parmName.equalsIgnoreCase("activityId")&& !parmName.equalsIgnoreCase("dsrId"))
					{
						 wherClause.put(parmName, paramValue);
					}
				}
				wherClause.put("manager_id", loggedDetails[0]);
				wherClause.put("manager_approve_date", DateUtil.getCurrentDateUSFormat());
				
				condtnBlock.put("activityId",activityId);
				wherClause.put("dsr_flag","1");
				
				condtnBlock.put("id",dsrId);
				boolean status = cbt.updateTableColomn("activity_schedule_dsr_fill", wherClause, condtnBlock,connectionSpace);
                if(status)
                {
                	 addActionMessage("Action Taken Successfully !!!");
                }
                else
                {
                	 addActionMessage("OOPS there is some error !!!");
                }
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				addActionError("Oop's there is some problem!!");
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String beforeDSRView()
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
	@SuppressWarnings("unchecked")
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

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_activity_dsr_fill_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_dsr_fill_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if( !gdp.getColomnName().equalsIgnoreCase("for_date")&&!gdp.getColomnName().equalsIgnoreCase("expenses")
						&&	!gdp.getColomnName().equalsIgnoreCase("value_text"))
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
			gpv = new GridDataPropertyView();
			gpv.setColomnName("activity_id");
			gpv.setHeadingName("activity_id");
			gpv.setHideOrShow("true");
			gpv.setWidth(40);
			masterViewProp.add(gpv);
			session.put("masterViewProp",masterViewProp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public String dsrViewGrid()
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
								query.append("dr.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_for"))
							{
								query.append("sp.activity_for,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("for_month"))
							{
								query.append("sp.for_month,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("reason"))
							{
								query.append("res.ract_resaon,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_id"))
							{
								query.append("sp.id as actId,");
							}
							else
							{
								query.append("dr."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							if (gpv.getColomnName().equalsIgnoreCase("activity_id"))
							{
								query.append("sp.id as actId");
							}
							else
							{
								query.append(gpv.getColomnName().toString());
							}
							
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_dsr_fill as dr ");
				query.append("LEFT JOIN activity_schedule_plan as sp on sp.id = dr.activityId ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=sp.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status ");
				query.append("LEFT JOIN activity_type_reason as res on at.id=res.ract_name ");
				query.append(" WHERE  dr.for_month='"+date+"' AND dr.activityId='"+activityId+"'");
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
								if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("created_date"))
								{
									one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat( obdata[k].toString())+", "+ obdata[k+1].toString());
								}
								else if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat( obdata[k].toString()));
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
	public String dsrViewGridFull()
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
								query.append("dr.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_for"))
							{
								query.append("sp.activity_for,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("reason"))
							{
								query.append("res.ract_resaon,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("for_month"))
							{
								query.append("sp.for_month,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_id"))
							{
								query.append("sp.id as actId,");
							}
							else
							{
								query.append("dr."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							 if (gpv.getColomnName().equalsIgnoreCase("activity_id"))
							 {
								query.append("sp.id as actId");
							 }
							 else
							 {
								 query.append(gpv.getColomnName().toString());
							 }
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_dsr_fill as dr ");
				query.append("LEFT JOIN activity_schedule_plan as sp on sp.id = dr.activityId ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=sp.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status ");
				query.append("LEFT JOIN activity_type_reason as res on at.id=res.ract_name ");
				query.append(" WHERE  dr.for_month LIKE '%"+month+"%' AND dr.dsr_flag=0 ");
				if(emp!=null && !emp.equalsIgnoreCase("-1")&& !emp.equalsIgnoreCase("undefined"))
				{
					query.append(" AND sp.schedule_contact_id IN (SELECT id FROM manage_contact WHERE emp_id IN (SELECT emp_id FROM manage_contact WHERE id =" + emp + ") AND module_name='WFPM') ");
				}
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
								if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("created_date"))
								{
									one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+", "+ obdata[k+1].toString());
								}
								else if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat( obdata[k].toString()));
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
	public String dsrExpenseView()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from activity_schedule_dsr_fill_expenses");
				List<?> dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator<?> it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					query1.setLength(0);
					query1.append("SELECT ex.id,par.exp_parameter,ex.value_text FROM activity_schedule_dsr_fill_expenses as ex LEFT JOIN expense_parameter as par on par.id=ex.expenses  WHERE ex.dsr_id= "+activityId);
					List<Object> Listhb = new ArrayList<Object>();
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query1.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query1.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query1.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query1.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query1.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query1.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					List<?> data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					if (data != null)
					{
						Object[] object = null;
						for (Iterator<?> it = data.iterator(); it.hasNext();)
						{
							object = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							
							if (object[0] != null)
								one.put("id", object[0].toString());
							else
								one.put("id", "NA");

							if (object[1] != null)
								one.put("expense", object[1].toString());
							else
								one.put("expense", "NA");

							if (object[2] != null)
								one.put("value", object[2].toString());
							else
								one.put("value", "NA");
						
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} 
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
	public String dsrExpenseViewEdit()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from activity_schedule_dsr_fill_expenses");
				List<?> dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator<?> it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					query1.setLength(0);
					query1.append("SELECT ex.id,par.exp_parameter,ex.value_text,ex.value_text2,ex.value_text2_reason FROM activity_schedule_dsr_fill_expenses as ex LEFT JOIN expense_parameter as par on par.id=ex.expenses  WHERE ex.dsr_id= "+activityId);
					List<Object> Listhb = new ArrayList<Object>();
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query1.append(" and ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query1.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query1.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query1.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query1.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query1.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					List<?> data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					if (data != null)
					{
						Object[] object = null;
						for (Iterator<?> it = data.iterator(); it.hasNext();)
						{
							object = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							
							if (object[0] != null)
								one.put("id", object[0].toString());
							else
								one.put("id", "NA");

							if (object[1] != null)
								one.put("expense", object[1].toString());
							else
								one.put("expense", "NA");

							if (object[2] != null)
								one.put("value", object[2].toString());
							else
								one.put("value", "NA");
							
							if (object[3] != null)
								one.put("value1", object[3].toString());
							else
								if (object[2] != null)
									one.put("value1", object[2].toString());
								else
									one.put("value1", "NA");
							
							if (object[4] != null)
								one.put("reason", object[4].toString());
							else
								one.put("reason", "NA");
						
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} 
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	public String beforeDSRDisapprove()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				viewPropertiesDisapprove();
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
	private void viewPropertiesDisapprove()
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();

			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_activity_dsr_fill_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_dsr_fill_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if(!gdp.getColomnName().equalsIgnoreCase("for_month") && !gdp.getColomnName().equalsIgnoreCase("for_date")&&!gdp.getColomnName().equalsIgnoreCase("expenses")
						&&	!gdp.getColomnName().equalsIgnoreCase("value_text"))
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
			gpv = new GridDataPropertyView();
			gpv.setColomnName("manager_status");
			gpv.setHeadingName("Manager Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("manager_reason");
			gpv.setHeadingName("Manager Reason");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("manager_id");
			gpv.setHeadingName("Manager Name");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			session.put("masterViewProp",masterViewProp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public String dsrViewDisapprve()
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
								query.append("dr.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_for"))
							{
								query.append("sp.activity_for,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("reason"))
							{
								query.append("res.ract_resaon,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("manager_id"))
							{
								query.append("pc.emp_name,");
							}
							else
							{
								query.append("dr."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							if(gpv.getColomnName().equalsIgnoreCase("manager_id"))
							{
								query.append("pc.emp_name");
							}
							else
							{
								query.append("dr."+gpv.getColomnName().toString());
							}
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_dsr_fill as dr ");
				query.append("LEFT JOIN activity_schedule_plan as sp on sp.id = dr.activityId ");
				query.append("LEFT JOIN patientcrm_status_data as escData on escData.id=sp.activity_type ");
				query.append("LEFT JOIN activity_type as at on at.id=escData.status ");
				query.append("LEFT JOIN activity_type_reason as res on at.id=res.ract_name ");
				query.append("LEFT JOIN manage_contact as mc on mc.id=dr.manager_id ");
				query.append("LEFT JOIN primary_contact as pc on pc.id=mc.emp_id ");
				query.append(" WHERE  sp.for_month='"+date+"' AND dr.activityId='"+activityId+"'");
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
								if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
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
	public String editExpenseDSR()
	{

		boolean CheckSession = ValidateSession.checkSession();
		if (CheckSession)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				StringBuilder query = new StringBuilder();
				query.append(" UPDATE  activity_schedule_dsr_fill_expenses SET value_text2 = '"+request.getParameter("value1")+"',value_text2_reason = '"+request.getParameter("reason")+"',manager_id='"+loggedDetails[0]+"',manager_date_time='"+DateUtil.getCurrentDateUSFormat()+","+DateUtil.getCurrentTimeHourMin()+"' WHERE id = '" + getId() + "' && id!=0");
				boolean status = cbt.updateTableColomn(connectionSpace, query);
				if (status)
				{
					addActionMessage("Action taken successfully!!!");
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
	public List<ConfigurationUtilBean> getFile()
	{
		return file;
	}
	public void setFile(List<ConfigurationUtilBean> file)
	{
		this.file = file;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDate()
	{
		return date;
	}

	public void setReasonMap(Map<String, String> reasonMap)
	{
		this.reasonMap = reasonMap;
	}

	public Map<String, String> getReasonMap()
	{
		return reasonMap;
	}

	public void setExpenseMap(Map<String, String> expenseMap)
	{
		this.expenseMap = expenseMap;
	}

	public Map<String, String> getExpenseMap()
	{
		return expenseMap;
	}

	public void setActivityId(String activityId)
	{
		this.activityId = activityId;
	}

	public String getActivityId()
	{
		return activityId;
	}

	public String getActivityType()
	{
		return activityType;
	}

	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}

	public String getActivity_for()
	{
		return activity_for;
	}

	public void setActivity_for(String activityFor)
	{
		activity_for = activityFor;
	}

	public File getDocument()
	{
		return document;
	}

	public void setDocument(File document)
	{
		this.document = document;
	}

	public String getDocumentContentType()
	{
		return documentContentType;
	}

	public void setDocumentContentType(String documentContentType)
	{
		this.documentContentType = documentContentType;
	}

	public String getDocumentFileName()
	{
		return documentFileName;
	}

	public void setDocumentFileName(String documentFileName)
	{
		this.documentFileName = documentFileName;
	}

	public void setDsrId(String dsrId)
	{
		this.dsrId = dsrId;
	}

	public String getDsrId()
	{
		return dsrId;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setEmp(String emp)
	{
		this.emp = emp;
	}

	public String getEmp()
	{
		return emp;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getMonth()
	{
		return month;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setSch_date(String sch_date)
	{
		this.sch_date = sch_date;
	}

	public String getSch_date()
	{
		return sch_date;
	}

	public void setComm(String comm)
	{
		this.comm = comm;
	}

	public String getComm()
	{
		return comm;
	}


}
