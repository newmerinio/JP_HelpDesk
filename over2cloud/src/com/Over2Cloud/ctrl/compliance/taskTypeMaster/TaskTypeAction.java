package com.Over2Cloud.ctrl.compliance.taskTypeMaster;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TaskTypeAction extends ActionSupport implements ServletRequestAware
{

	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String userType = (String) session.get("userTpe");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(TaskTypeAction.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> taskTypeList = null;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private String deptName;
	private String deptId;
	private Map<String, String> allDeptName = null;
	private String status;
	private String searchParameter;

	@Override
	public String execute()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method execute of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeTaskTypeAdd()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setAddPageDataFields();
				return SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method execute of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public void setAddPageDataFields()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> complianceFieldsName = Configuration.getConfigurationData("mapped_otm_task_type_configuration", accountID, connectionSpace, "", 0, "table_name", "otm_task_type_configuration");
				allDeptName = new LinkedHashMap<String, String>();
				taskTypeList = new ArrayList<ConfigurationUtilBean>();
				if (complianceFieldsName != null && complianceFieldsName.size()>0)
				{
					for (GridDataPropertyView gdp : complianceFieldsName)
					{
						ConfigurationUtilBean conUtilBean = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
						{
							conUtilBean.setField_name(gdp.getHeadingName());
							conUtilBean.setField_value(gdp.getColomnName());
							conUtilBean.setValidationType(gdp.getValidationType());
							conUtilBean.setColType(gdp.getColType());
							if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
							{
								conUtilBean.setMandatory(true);
							} else
							{
								conUtilBean.setMandatory(false);
							}
							conUtilBean.setValidationType(gdp.getValidationType());
							conUtilBean.setField_length(gdp.getLength());
							taskTypeList.add(conUtilBean);
						}
					}
					if (userType != null && userType.equalsIgnoreCase("M"))
					{
						String query = "SELECT id,contact_subtype_name FROM contact_sub_type WHERE status='Active' ORDER BY contact_subtype_name";
						List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									allDeptName.put(object[0].toString(), object[1].toString());
								}
							}
						}

					} else
					{
						String str[] = new ComplianceUniversalAction().getEmpDataByUserName(userName);
						deptName = str[4];
						deptId = str[3];
					}

				}
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method setAddPageDataFields of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addTaskType()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query = "SELECT * FROM otm_task_type WHERE task_type='" + request.getParameter("task_type") + "' AND status='Active'";
				List data = cbt.executeAllSelectQuery(query, connectionSpace);
				String actionMsg = null;
				if (data == null || data.size() == 0)
				{
					List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_otm_task_type_configuration", accountID, connectionSpace, "", 0, "table_name", "otm_task_type_configuration");
					if (statusColName != null)
					{
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						InsertDataTable ob = null;
						boolean status = false;
						List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
						TableColumes ob1 = null;
						for (GridDataPropertyView gdp : statusColName)
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype(gdp.getData_type());

							if (gdp.getColomnName().equalsIgnoreCase("status"))
							{
								ob1.setConstraint("default 'Active'");
							} else
							{
								ob1.setConstraint("default NULL");
							}
							Tablecolumesaaa.add(ob1);
						}
						cbt.createTable22("otm_task_type", Tablecolumesaaa, connectionSpace);
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String Parmname = it.next().toString();
							if (!Parmname.equalsIgnoreCase("multiDept") && !Parmname.equalsIgnoreCase("deptName"))
							{
								String paramValue = request.getParameter(Parmname);
								if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
								{
									ob = new InsertDataTable();
									ob.setColName(Parmname);
									ob.setDataName(DateUtil.makeTitle(paramValue));
									insertData.add(ob);
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

						if (userType != null && userType.equalsIgnoreCase("M"))
						{
							String str[] = request.getParameterValues("multiDept");
							for (int i = 0; i < str.length; i++)
							{
								ob = new InsertDataTable();
								ob.setColName("sub_type_id");
								ob.setDataName(str[i]);
								insertData.add(ob);
								if (!existStatus(str[i], request.getParameter("task_type"), cbt))
								{
									status = cbt.insertIntoTable("otm_task_type", insertData, connectionSpace);
									actionMsg = "Task Type added successfully!!!";
								} else
								{
									actionMsg = "Task Type Already Exist In Mapped Dept !!!";
								}
								insertData.remove(insertData.size() - 1);

							}
						} else
						{
							String str = request.getParameter("deptName");
							ob = new InsertDataTable();
							ob.setColName("departName");
							ob.setDataName(str);
							insertData.add(ob);
							if (!existStatus(str, request.getParameter("taskType"), cbt))
							{
								status = cbt.insertIntoTable("otm_task_type", insertData, connectionSpace);
								actionMsg = "Task Type added successfully!!!";
							} else
							{
								actionMsg = "Task Type Already Exist In Mapped Dept !!!";
							}
						}
						if (status)
						{
							addActionMessage(actionMsg);
							int empID =Integer.parseInt((String) session.get("empIdofuser").toString().split("-")[1]);
							int maxId=cbt.getMaxId("otm_task_type", connectionSpace);
							new UserHistoryAction().userHistoryAdd(String.valueOf(empID), "Add", "COMPL", "Task Type", "Task Type Added", "Task Type Added", maxId, connectionSpace);
							return SUCCESS;
						} else
						{
							addActionMessage(actionMsg);
							return SUCCESS;
						}
					} else
					{
						return ERROR;
					}
				} else
				{
					addActionMessage(request.getParameter("taskType") + " Task Type Already Exist!!!");
					return SUCCESS;
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method addTaskType of class " + getClass(), exp);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}
	
	

	public boolean existStatus(String deptId, String checkedValue, CommonOperInterface cbt)
	{
		boolean status = false;
		String query = "SELECT id FROM otm_task_type WHERE status='Active' AND task_type='" + checkedValue + "' AND sub_type_id=" + deptId;
		List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
		if (dataList != null && dataList.size() > 0)
			status = true;

		return status;
	}

	public String beforeViewTaskType()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeViewTaskType of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public void setViewProp()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_otm_task_type_configuration", accountID, connectionSpace, "", 0, "table_name", "otm_task_type_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setWidth(gdp.getWidth());
			gpv.setHideOrShow(gdp.getHideOrShow());
			masterViewProp.add(gpv);
		}
	}

	@SuppressWarnings("rawtypes")
	public String viewTaskType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from otm_task_type");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					Object obdata = null;
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query1.setLength(0);
					query.append("SELECT ");
					List fieldNames = Configuration.getColomnList("mapped_otm_task_type_configuration", accountID, connectionSpace, "otm_task_type_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (obdata.equals("sub_type_id"))
								{
									query.append("dept.contact_subtype_name,");
								}
								else
								{
									query.append("cty." + obdata.toString() + ",");
								}
							}
							else
							{
								if (obdata.equals("sub_type_id"))
								{
									query.append("dept.contact_subtype_name");
								}
								else
								{
									query.append("cty." + obdata.toString() + "");
								}
							}
						}
						i++;
					}
					query.append(" FROM otm_task_type AS cty ");
					query.append(" LEFT JOIN contact_sub_type AS dept ON dept.id=cty.sub_type_id");
					query.append(" WHERE cty.id!=0 ");
					if (userType != null && !userType.equalsIgnoreCase("M"))
					{
						String str[] = new ComplianceUniversalAction().getEmpDataByUserName(userName);
						deptId = str[3];
						query.append("AND cty.sub_type_ide=" + deptId);
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						// add search query based on the search operation
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
					if (status != null && !status.equalsIgnoreCase("All"))
					{
						query.append(" and status='" + getStatus() + "'");
					}
				//	System.out.println("jkgjhb>>>>>>>>>>"+searchParameter);
					
					query.append(" ORDER BY cty.task_type ASC");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null)
					{
						Object[] obdata11 = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata11 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata11[k] != null && !obdata11[k].toString().equalsIgnoreCase(""))
								{
									if (fieldNames.get(k).toString().equals("created_date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata11[k].toString()));
									} else if (fieldNames.get(k).toString().equals("user_name"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata11[k].toString()));
									} else
									{
										one.put(fieldNames.get(k).toString(), obdata11[k].toString());
									}
								} else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewTaskType of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewModifyTaskType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				int empID =Integer.parseInt((String) session.get("empIdOfuser").toString().split("-")[1]);
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
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid") && !Parmname.equalsIgnoreCase("departName"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("compl_task_type", wherClause, condtnBlock, connectionSpace);
					new UserHistoryAction().userHistoryAdd(String.valueOf(empID), "Edit", "COMPL", "Task Type", wherClause.toString(), "Task Type Edited", Integer.parseInt(getId()), connectionSpace);
				} else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
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
					query.append("UPDATE compl_task_type SET status='Inactive' WHERE id IN(" + condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
					new UserHistoryAction().userHistoryAdd(String.valueOf(empID), "Inactive", "COMPL", "Task Type", "status", "Inactive", Integer.parseInt(getId()), connectionSpace);
					query.setLength(0);

					query.append("UPDATE compliance_task SET status='Inactive' WHERE taskType IN(" + condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
				}
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewModifyTaskType of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<ConfigurationUtilBean> getTaskTypeList()
	{
		return taskTypeList;
	}

	public void setTaskTypeList(List<ConfigurationUtilBean> taskTypeList)
	{
		this.taskTypeList = taskTypeList;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public Map<String, String> getAllDeptName()
	{
		return allDeptName;
	}

	public void setAllDeptName(Map<String, String> allDeptName)
	{
		this.allDeptName = allDeptName;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getSearchParameter()
	{
		return searchParameter;
	}

	public void setSearchParameter(String searchParameter)
	{
		this.searchParameter = searchParameter;
	}

}
