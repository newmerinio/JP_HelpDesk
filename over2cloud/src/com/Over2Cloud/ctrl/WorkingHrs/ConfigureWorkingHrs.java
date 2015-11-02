package com.Over2Cloud.ctrl.WorkingHrs;

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
import org.apache.struts2.interceptor.ServletRequestAware;
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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class ConfigureWorkingHrs extends GridPropertyBean implements ServletRequestAware
{

	private HttpServletRequest request;
	private String dataFor;
	private String actionType;
	private String dept;
	private String working_type;
	private String holiday;
	private String from_day;
	private String to_day;
	private String from_time = "00:00";
	private String to_time = "24:00";
	private List<GridDataPropertyView> viewColumnList = null;
	private List<ConfigurationUtilBean> createColumnList = null;
	private Map<String, String> deptList = null;
	private List<Object> masterViewList;
	Map<String, String> appDetails = null;
	private JSONArray arrObj = new JSONArray();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	@SuppressWarnings("unchecked")
	public String firstAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				deptList = new LinkedHashMap<String, String>();
				if (dataFor != null && !dataFor.equals("") && actionType != null && !actionType.equals("") && actionType.equals("Add"))
				{
					if (dataFor != null && !dataFor.equals(""))
					{
						List data = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, dataFor, connectionSpace);
						if (data != null && data.size() > 0)
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] object1 = (Object[]) iterator.next();
								deptList.put(object1[0].toString(), object1[1].toString());
							}
						}
					}
					returnResult = "addsuccess";
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getHeaderBeforeView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				appDetails = new LinkedHashMap<String, String>();
				appDetails = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				dataFor = "all";
				getApplyedWorkingHrsDept();
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
	
	public String beforeViewWorkingHrs()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				workingTimingColumns();
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
	public String getApplyedWorkingHrsDept()
	{
		deptList = new LinkedHashMap<String, String>();
		deptList.put("all", "All Contact Sub Type");
		WorkingHourHelper WHH = new WorkingHourHelper();
		List dataList = WHH.applyedWorkingHrsDept(connectionSpace, cbt, dataFor);
		if(dataList!=null && dataList.size()>0)
		{
			JSONObject obj =null;
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					deptList.put(object[0].toString(), object[1].toString());
					obj = new JSONObject();
					obj.put("id", object[0].toString());
					obj.put("name", object[1].toString());
					arrObj.add(obj);
				}
			}
		}
		return SUCCESS;
	}
	
	
	public void workingTimingColumns()
	{
		viewColumnList = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewColumnList.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("day");
		gpv.setHeadingName("Day");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setWidth(200);
		viewColumnList.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_working_timming_configuration", accountID, connectionSpace, "", 0, "table_name",
				"working_timming_configuration");
		for (GridDataPropertyView gdp : returnResult)
		{
			if (gdp.getColomnName() != null && !gdp.getColomnName().equals("") && !gdp.getColomnName().equals("from_day") && !gdp.getColomnName().equals("to_day"))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				viewColumnList.add(gpv);
			}
		}

	}

	@SuppressWarnings(
	{ "unchecked"})
	public String viewWorkingHrs()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				StringBuilder query = new StringBuilder("");
				List fieldNames = new ArrayList();
				query.append("select ");
				List columnList = Configuration.getColomnList("mapped_working_timming_configuration", accountID, connectionSpace, "working_timming_configuration");
				columnList.add("day");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = columnList.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (!obdata.toString().equalsIgnoreCase("from_day") && !obdata.toString().equalsIgnoreCase("to_day"))
						{
							if (i < columnList.size() - 1)
							{
								if(obdata.toString().equals("id"))
									query.append("wh." + obdata.toString() + " as workid,");
								else if(obdata.toString().equals("sub_type_id"))
									query.append("dept.contact_subtype_name,");
								else
									query.append("wh." + obdata.toString() + ",");
								
								fieldNames.add(obdata.toString());
							}
							else
							{
								query.append("wh." + obdata.toString());
								fieldNames.add(obdata.toString());
							}
						}
					}
					i++;
				}
				query.append(" from working_timming as wh");
				query.append(" left join contact_sub_type as dept ON dept.id = wh.sub_type_id");
				query.append(" where wh.id!=0");
				if(dataFor!=null && !dataFor.equalsIgnoreCase("all") && dept!=null && dept.equalsIgnoreCase("all"))
				{
					query.append(" and wh.module_name='" + dataFor + "' AND sub_type_id='A'");
					
				}
				else if(dataFor!=null && !dataFor.equalsIgnoreCase("all") && dept!=null && !dept.equalsIgnoreCase("all"))
				{
					query.append(" and wh.module_name='" + dataFor + "' AND sub_type_id="+dept);
					
				}
				else if(dataFor!=null && dataFor.equalsIgnoreCase("all") && dept!=null && !dept.equalsIgnoreCase("all"))
				{
					query.append(" and sub_type_id="+dept);
					
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}
				}
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null && data.size()>0)
				{
					Object[] obdata1 = null;
					for (Iterator it1 = data.iterator(); it1.hasNext();)
					{
						obdata1 = (Object[]) it1.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k <= fieldNames.size() - 1; k++)
						{
							if (obdata1[k] != null && !obdata1[k].toString().equalsIgnoreCase(""))
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata1[k]);
								}
								else
								{
									if (fieldNames.get(k).toString().equals("created_date"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
									}
									else if (fieldNames.get(k).toString().equals("created_time"))
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString().substring(0, obdata1[k].toString().length() - 3));
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}
								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "All Contact Sub Type");
							}
						}
						Listhb.add(one);
					}
					setRecords(Listhb.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
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
	public String editWorkingHrs()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equals("from_time"))
							{
								if (paramValue == null || paramValue.equals("") || paramValue.equals("00:00"))
									from_time = "00:01";
								else
									from_time = getTime(from_time);
								
								wherClause.put(parmName, from_time);
							}
							else if (parmName.equals("to_time"))
							{
								if (paramValue == null || paramValue.equals("") || paramValue.equals("24:00"))
									to_time = "23:59";
								else
									to_time = getTime(to_time);
								
								wherClause.put(parmName, to_time);
							}
						}
					}
					wherClause.put("working_hrs", DateUtil.findDifferenceTwoTime(from_time, to_time));
					wherClause.put("non_working_hrs", DateUtil.findDifferenceTwoTime(DateUtil.findDifferenceTwoTime(from_time, to_time),"24:00"));
					
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("working_timming", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					cbt.deleteAllRecordForId("working_timming", "id", getId(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public String beforeAdd()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				deptList = new LinkedHashMap<String, String>();
				appDetails = new LinkedHashMap<String, String>();
				appDetails = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
				setWorkingTimeTags();
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
	public String getConcernDept()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String deptLevel = "1";
				if (dataFor != null && dataFor.equals("HDM"))
				{
					deptLevel = "2";
				}
				List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept(deptLevel, orgLevel, orgId, dataFor, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					JSONObject obj = null;
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						obj = new JSONObject();
						Object[] object1 = (Object[]) iterator.next();
						obj.put("id", object1[0].toString());
						obj.put("name", object1[1].toString());
						arrObj.add(obj);
					}
				}
				System.out.println(arrObj.size());
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

	public void setWorkingTimeTags()
	{
		ConfigurationUtilBean obj;
		createColumnList = new ArrayList<ConfigurationUtilBean>();
		List<String> data = new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");

		List<GridDataPropertyView> reportConfigList = Configuration.getConfigurationData("mapped_working_timming_configuration", accountID, connectionSpace, "", 0, "table_name","working_timming_configuration");
		if (reportConfigList != null && reportConfigList.size() > 0)
		{
			for (GridDataPropertyView gdv : reportConfigList)
			{
				obj = new ConfigurationUtilBean();
				if (!gdv.getColomnName().trim().equalsIgnoreCase("user_name") && !gdv.getColomnName().trim().equalsIgnoreCase("created_date") && !gdv.getColomnName().trim().equalsIgnoreCase("created_time") && !gdv.getColomnName().trim().equalsIgnoreCase("non_working_hrs"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					createColumnList.add(obj);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String addWorkingTimings()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cot = new CommonConFactory().createInterface();
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_working_timming_configuration", "", connectionSpace, "", 0, "table_name",
						"working_timming_configuration");
				if (colName != null && colName.size() > 0)
				{
					@SuppressWarnings("unused")
					boolean status = false;
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName)
					{
						if (!tableColumes.getColomnName().equals("to_day"))
						{
							TableColumes ob1 = new TableColumes();
							if (tableColumes.getColomnName().equalsIgnoreCase("from_day"))
							{
								ob1.setColumnname("day");
							}
							else
							{
								ob1.setColumnname(tableColumes.getColomnName());
							}
							ob1.setDatatype(tableColumes.getData_type());
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
						}
					}
					cot.createTable22("working_timming", TableColumnName, connectionSpace);
				}

				InsertDataTable ob = null;
				String deptValue = "";
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("from_day") && !Parmname.equalsIgnoreCase("to_day")
							&& !Parmname.equalsIgnoreCase("dataFor"))
					{
						if (Parmname.equals("sub_type_id"))
						{
							deptValue=paramValue;
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
						else if (Parmname.equals("from_time"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							if (paramValue == null || paramValue.equals("") || paramValue.equals("00:00"))
								from_time = "00:01";
							else
								from_time = getTime(paramValue);
							
							ob.setDataName(from_time);
							insertData.add(ob);
						}
						else if (Parmname.equals("to_time"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							if (paramValue == null || paramValue.equals("") || paramValue.equals("24:00"))
								to_time = "23:59";
							else
								to_time = getTime(paramValue);
							
							ob.setDataName(to_time);
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

				ob = new InsertDataTable();
				ob.setColName("working_hrs");
				ob.setDataName(DateUtil.findDifferenceTwoTime(from_time, to_time));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("non_working_hrs");
				ob.setDataName(DateUtil.findDifferenceTwoTime(DateUtil.findDifferenceTwoTime(from_time, to_time),"24:00"));
				insertData.add(ob);
				String arr[]=getDataFor().split(",");
				if(dataFor!=null && !dataFor.equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName("module_name");
					ob.setDataName(arr[1].trim());
					insertData.add(ob);
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
				int startPoint = Integer.parseInt(from_day);
				int endPoint = Integer.parseInt(to_day);
				String dayValue = "";
				boolean existFlag = false;
				for (int i = startPoint; i <= endPoint; i++)
				{
					ob = new InsertDataTable();
					ob.setColName("day");
					if (i == 1)
					{
						dayValue = "Monday";
					}
					else if (i == 2)
					{
						dayValue = "Tuesday";
					}
					else if (i == 3)
					{
						dayValue = "Wednesday";
					}
					else if (i == 4)
					{
						dayValue = "Thursday";
					}
					else if (i == 5)
					{
						dayValue = "Friday";
					}
					else if (i == 6)
					{
						dayValue = "Saturday";
					}
					else if (i == 7)
					{
						dayValue = "Sunday";

					}
					ob.setDataName(dayValue);
					insertData.add(ob);
					existFlag = new HelpdeskUniversalHelper().isExist("working_timming", "sub_type_id", deptValue, "day", dayValue, "module_name", arr[1].trim(), connectionSpace);
					if (!existFlag)
					{
						status = cbt.insertIntoTable("working_timming", insertData, connectionSpace);
					}
					insertData.remove(insertData.size() - 1);
				}
				if(status)
				{
					addActionMessage("Working Time Added Successfully !!");
				}
				else
				{
					addActionMessage("Working Time Already Exists !!");
				}
				returnResult= SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getTime(String time)
	{
		String str[] = time.split(":");
		if(str[1].equals("00"))
			return DateUtil.findDifferenceTwoTime("00:01",time);
		else
			return time;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getActionType()
	{
		return actionType;
	}

	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getWorking_type()
	{
		return working_type;
	}

	public void setWorking_type(String working_type)
	{
		this.working_type = working_type;
	}

	public String getHoliday()
	{
		return holiday;
	}

	public void setHoliday(String holiday)
	{
		this.holiday = holiday;
	}

	public List<GridDataPropertyView> getViewColumnList()
	{
		return viewColumnList;
	}

	public void setViewColumnList(List<GridDataPropertyView> viewColumnList)
	{
		this.viewColumnList = viewColumnList;
	}

	public List<ConfigurationUtilBean> getCreateColumnList()
	{
		return createColumnList;
	}

	public void setCreateColumnList(List<ConfigurationUtilBean> createColumnList)
	{
		this.createColumnList = createColumnList;
	}

	public Map<String, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<String, String> deptList)
	{
		this.deptList = deptList;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public Map<String, String> getAppDetails()
	{
		return appDetails;
	}

	public void setAppDetails(Map<String, String> appDetails)
	{
		this.appDetails = appDetails;
	}

	public JSONArray getArrObj()
	{
		return arrObj;
	}

	public void setArrObj(JSONArray arrObj)
	{
		this.arrObj = arrObj;
	}

	public String getFrom_day()
	{
		return from_day;
	}

	public void setFrom_day(String fromDay)
	{
		from_day = fromDay;
	}

	public String getTo_day()
	{
		return to_day;
	}

	public void setTo_day(String toDay)
	{
		to_day = toDay;
	}

	public String getFrom_time()
	{
		return from_time;
	}

	public void setFrom_time(String fromTime)
	{
		from_time = fromTime;
	}

	public String getTo_time()
	{
		return to_time;
	}

	public void setTo_time(String toTime)
	{
		to_time = toTime;
	}

}
