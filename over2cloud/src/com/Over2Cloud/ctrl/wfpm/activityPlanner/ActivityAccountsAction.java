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
public class ActivityAccountsAction extends GridPropertyBean
{
	private List<ConfigurationUtilBean> textBox = null;
	private List<ConfigurationUtilBean> dropDown = null;
	private List<ConfigurationUtilBean> dateField = null;
	private List<ConfigurationUtilBean> file = null;
	private List<Map<String,String>> test;
	private Map<String,String> commonMap;
	private List<Object> masterViewList;
	public File document;
	private String documentContentType;
	private String documentFileName;
	private String emp_id;
	private String month;
	private String fdate;
	private String tdate;
	
	
	public String beforeAccountsView()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				commonMap = new ActivityPlannerHelper().mappedEmployee(loggedDetails[4], coi);
				fdate  =DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
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
	public String beforeAccountsPage()
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
			List<GridDataPropertyView> fieldList = Configuration.getConfigurationData("mapped_activity_accounts_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_accounts_configuration");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
			commonMap = new LinkedHashMap<String,String>();
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
						if (gdp.getColomnName().equalsIgnoreCase("given_by"))
						{
							if(loggedDetails[4]!=null)
							{
								Map<String ,String> test  =new LinkedHashMap<String ,String>();
								test = new ActivityPlannerHelper().mappedEmployee(loggedDetails[4], coi);
								commonMap.put(loggedDetails[0], loggedDetails[1]);
								for(Map.Entry<String,String> entry :test.entrySet())
								{
									if(!entry.getKey().equalsIgnoreCase(loggedDetails[0]))
									{
										commonMap.put(entry.getKey(), entry.getValue());
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
	public String addAcountDetail()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_activity_accounts_configuration", accountID, connectionSpace, "", 0, "table_name", "activity_accounts_configuration");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
	
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : statusColName)
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
				
				ob1 = new TableColumes();
				ob1.setColumnname("given_to_contact_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
	
				cbt.createTable22("activity_accounts_plan", Tablecolumesaaa, connectionSpace);
	
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null )
					{
						if(Parmname.equalsIgnoreCase("date"))
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
				}
				String renameFilePath = null;
				if (documentFileName != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("DSR Doc") + "//" + DateUtil.mergeDateTime() + getDocumentFileName();
					String storeFilePath = new CreateFolderOs().createUserDir("DSR Doc") + "//" + getDocumentFileName();
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
								ob.setDataName(getDocumentFileName());
								insertData.add(ob);
							}
						}
					}
				}
				
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
				
				status = cbt.insertIntoTable("activity_accounts_plan", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Reimbursement Action Successfully Done !!!");
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
	@SuppressWarnings("unchecked")
	public String accountViewData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from activity_accounts_plan");
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
					query1.append("SELECT mc.id,pc.emp_name as name1,sp.for_month,sum(dscrex.value_text) as val,pc1.emp_name as name2,dsr.manager_approve_date FROM activity_schedule_dsr_fill_expenses as dscrex  ");
					query1.append(" LEFT JOIN activity_schedule_dsr_fill as dsr on dsr.id = dscrex.dsr_id ");
					query1.append(" LEFT JOIN activity_schedule_plan as sp on sp.id = dsr.activityId ");
					query1.append(" LEFT JOIN manage_contact as mc on mc.id = sp.schedule_contact_id ");
					query1.append(" LEFT JOIN primary_contact as pc on pc.id = mc.emp_id ");
					query1.append(" LEFT JOIN manage_contact as mc1 on mc1.id = dsr.manager_id ");
					query1.append(" LEFT JOIN primary_contact as pc1 on pc1.id = mc1.emp_id ");
					query1.append(" WHERE dsr.manager_status IS NOT NULL ");
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
						query1.append(" AND sp.for_month BETWEEN '"+fdate+"' AND '"+tdate+"'");
					}
					if(emp_id!=null && !emp_id.equalsIgnoreCase("-1"))
					{
						query1.append(" AND mc.id = '"+emp_id+"' ");
					}
					query1.append(" GROUP BY  substring(sp.for_month,1,6),pc.id ");
					
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
							double amount1=0;
							double amount2=0;
							object = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							
							if (object[0] != null)
								one.put("id", object[0].toString());
							else
								one.put("id", "NA");

							if (object[1] != null)
								one.put("mappedEmpName", object[1].toString());
							else
								one.put("mappedEmpName", "NA");

							if (object[2] != null)
								one.put("month", DateUtil.convertDateToIndianFormat(object[2].toString()));
							else
								one.put("month", "NA");

							if (object[3] != null)
							{
								one.put("total_due", object[3].toString());
								amount1 = Double.parseDouble(object[3].toString());
								
								query1.setLength(0);
								query1.append(" SELECT sum(imprest_amount) FROM activity_accounts_plan WHERE given_to_contact_id = '"+ object[0].toString()+"' AND date LIKE '%"+object[2].toString().substring(0,7)+"%'  ");
								List data1=cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
								if(data1!=null && data1.size()>0)
								{
									if(data1.get(0)!=null)
									{
										amount2=Double.parseDouble(data1.get(0).toString());
										one.put("advance",data1.get(0).toString());
									}
									else
									{
										one.put("advance", "0");
									}
								}
								else
								{
									one.put("advance", "0");
								}
								if (object[3] != null)
									one.put("balance", amount1 -amount2);
								else
									one.put("balance", "NA");
							}
							else
								one.put("total_due", "NA");
							
							if (object[4] != null)
								one.put("autoby", object[4].toString());
							else
								one.put("autoby", "NA");
							
							if (object[5] != null)
								one.put("autodate", DateUtil.convertDateToIndianFormat(object[5].toString()));
							else
								one.put("autodate", "NA");
						
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
	@SuppressWarnings("unchecked")
	public String breaktotalReimbursement()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				test  =new ArrayList<Map<String,String>>();
				Map<String,String> a  =null;
				StringBuilder query = new StringBuilder();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append(" SELECT sp.for_month,exp.exp_parameter,exp1.value_text FROM activity_schedule_plan as sp ");
				query.append(" LEFT JOIN activity_schedule_dsr_fill as dsr on dsr.activityId=sp.id ");
				query.append(" LEFT JOIN activity_schedule_dsr_fill_expenses as exp1 on exp1.dsr_id=dsr.id ");
				query.append(" LEFT JOIN expense_parameter  as exp on exp.id=exp1.expenses ");
				query.append(" WHERE sp.for_month LIKE '%"+month.split("-")[2]+"-"+month.split("-")[1]+"%' AND sp.schedule_contact_id='"+emp_id+"' ");
				query.append(" AND dsr.manager_status IS NOT NULL ");
				List  data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						a  =new LinkedHashMap<String,String>();
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null)
						{
							a.put("Month", DateUtil.convertDateToIndianFormat(object[0].toString()));
						}
						else
						{
							a.put("Month","NA");
						}
						if(object[1]!=null)
						{
							a.put("exp", object[1].toString());
						}
						else
						{
							a.put("exp","NA");
						}
						if(object[2]!=null)
						{
							a.put("val",object[2].toString());
						}
						else
						{
							a.put("val","NA");
						}
						test.add(a);
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
	public void setCommonMap(Map<String,String> commonMap)
	{
		this.commonMap = commonMap;
	}
	public Map<String,String> getCommonMap()
	{
		return commonMap;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setFile(List<ConfigurationUtilBean> file)
	{
		this.file = file;
	}

	public List<ConfigurationUtilBean> getFile()
	{
		return file;
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

	public void setEmp_id(String emp_id)
	{
		this.emp_id = emp_id;
	}

	public String getEmp_id()
	{
		return emp_id;
	}
	public void setMonth(String month)
	{
		this.month = month;
	}
	public String getMonth()
	{
		return month;
	}
	public void setTest(List<Map<String,String>> test)
	{
		this.test = test;
	}
	public List<Map<String,String>> getTest()
	{
		return test;
	}
	public String getFdate()
	{
		return fdate;
	}
	public void setFdate(String fdate)
	{
		this.fdate = fdate;
	}
	public String getTdate()
	{
		return tdate;
	}
	public void setTdate(String tdate)
	{
		this.tdate = tdate;
	}
	
}
