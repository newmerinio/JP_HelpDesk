package com.Over2Cloud.ctrl.wfpm.salary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SalaryAction extends ActionSupport implements ServletRequestAware
{
	static final Log						log							= LogFactory.getLog(SalaryAction.class);
	private Map									session					= ActionContext.getContext().getSession();
	private String							userName				= (String) session.get("uName");
	private String							accountID				= (String) session.get("accountid");
	private SessionFactory			connectionSpace	= (SessionFactory) session.get("connectionSpace");

	private boolean							holidaysExist;
	private boolean							allowedLeaveExist;
	private boolean							deductionExist;
	private boolean							extraDayAmountExist;

	private String							holidaysLable;
	private String							allowedLeaveLable;
	private String							deductionLable;
	private String							extraDayAmountLable;

	private List<Integer>				holidayList;
	private List<Integer>				deductionList;
	private List<Integer>				allowLeaveList;
	private List<Integer>				extraDayPayList;
	private List<Object>				salaryGridModelList;
	private HttpServletRequest	request;

	private String							hvalue					= "0";
	private String							alValue					= "0";
	private String							dValue					= "0";
	private String							edaValue				= "0";

	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer							rows						= 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer							page						= 0;

	// sorting order - asc or desc
	private String							sord;

	// get index row - i.e. user click to sort.
	private String							sidx;

	// Search Field
	private String							searchField;

	// The Search String
	private String							searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer							totalrows				= 0;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String							searchOper;

	// Your Total Pages
	private Integer							total						= 3;

	// All Records
	private Integer							records					= 0;
	private String							mainHeaderName;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			Calendar calendar = Calendar.getInstance();

			int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			setAddPageDataFields("mapped_salary_configuration", "salary_configuration");

			holidayList = new ArrayList<Integer>();
			deductionList = new ArrayList<Integer>();
			allowLeaveList = new ArrayList<Integer>();
			extraDayPayList = new ArrayList<Integer>();

			CommonOperInterface coi = new CommonConFactory().createInterface();
			String query = "select * from salaryconfiginfo where month = '" + DateUtil.getCurrentDateYearMonth() + "'";
			List list = coi.executeAllSelectQuery(query, connectionSpace);
			if (list != null && list.size() > 0)
			{
				Object[] ob = (Object[]) list.get(0);
				hvalue = ob[1].toString();
				alValue = ob[2].toString();
				dValue = ob[3].toString();
				edaValue = ob[4].toString();
			}

			for (int i = 1; i <= days; i++)
			{
				holidayList.add(i);
			}

			for (int i = 1; i <= 10; i++)
			{
				deductionList.add(i);
			}

			for (int i = 1; i <= 10; i++)
			{
				allowLeaveList.add(i);
			}

			for (int i = 1; i <= 10; i++)
			{
				extraDayPayList.add(i);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataFields(String table1, String table2)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> incentive = Configuration.getConfigurationData(table1, accountID, connectionSpace, "", 0, "table_name", table2);
		for (GridDataPropertyView gdp : incentive)
		{
			if (gdp.getColType().equalsIgnoreCase("D"))
			{
				String tmpString = gdp.getColomnName().trim();
				if (tmpString.equalsIgnoreCase("holidays"))
				{
					holidaysExist = true;
					holidaysLable = gdp.getHeadingName();
				}
				else if (tmpString.equalsIgnoreCase("allowedLeave"))
				{
					allowedLeaveExist = true;
					allowedLeaveLable = gdp.getHeadingName();
				}
				else if (tmpString.equalsIgnoreCase("deduction"))
				{
					deductionExist = true;
					deductionLable = gdp.getHeadingName();
				}
				else if (tmpString.equalsIgnoreCase("extraDayAmount"))
				{
					extraDayAmountExist = true;
					extraDayAmountLable = gdp.getHeadingName();
				}
			}

		}
	}

	public String saveSalaryConfigInfo()
	{

		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> salaryConfigInfo = Configuration.getConfigurationData("mapped_salary_configuration", accountID, connectionSpace, "", 0,
					"table_name", "salary_configuration");
			StringBuffer wherClause = new StringBuffer();

			if (salaryConfigInfo != null)
			{
				InsertDataTable ob = null;

				boolean status = false;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();

				for (GridDataPropertyView gdp : salaryConfigInfo)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);

				}

				cbt.createTable22("salaryConfigInfo", tableColumn, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				List paramList = new ArrayList<String>();
				int paramValueSize = 0;
				boolean statusTemp = true;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				while (it.hasNext())
				{
					String parmName = it.next().toString();
					String paramValue = request.getParameter(parmName);

					if (paramValue != null && parmName != null)
					{
						// adding the parameters list.
						ob = new InsertDataTable();
						ob.setColName(parmName);
						ob.setDataName(paramValue);
						insertData.add(ob);

						wherClause.append(" " + parmName);
						wherClause.append(" = '" + paramValue + "', ");
					}
				}

				ob = new InsertDataTable();
				ob.setColName("month");
				ob.setDataName(DateUtil.getCurrentDateYearMonth());
				insertData.add(ob);

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
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);
				String month = DateUtil.getCurrentMonth() < 10 ? 0 + "" + DateUtil.getCurrentMonth() : String.valueOf(DateUtil.getCurrentMonth());

				/*
				 * Check Record already exist.
				 */

				/*
				 * StringBuffer wherClause = new StringBuffer(); it =
				 * requestParameterNames.iterator(); while (it.hasNext()) { String
				 * parmName = it.next().toString(); String
				 * paramValue=request.getParameter(parmName); if(paramValue!=null &&
				 * parmName != null ) { wherClause.append(" "+ parmName);
				 * wherClause.append(" = '"+ paramValue +"', "); } }
				 */
				wherClause.append("userName = '" + userName + "', ");
				wherClause.append("date = '" + DateUtil.getCurrentDateUSFormat() + "', ");
				wherClause.append("time = '" + DateUtil.getCurrentTime() + "'");

				String query = "select id from salaryConfigInfo where month ='" + DateUtil.getCurrentDateYearMonth() + "' ";
				List report = cbt.executeAllSelectQuery(query, connectionSpace);

				String msg = "";
				if (report != null && report.size() > 0)
				{
					String query2 = "update salaryConfigInfo " + "set " + wherClause + " where month ='" + DateUtil.getCurrentDateYearMonth() + "' ";
					int i = cbt.executeAllUpdateQuery(query2, connectionSpace);
					if (i > 0)
					{
						status = true;
					}
					msg = "Salary config update successfully!!!";
				}
				else
				{
					status = cbt.insertIntoTable("salaryConfigInfo", insertData, connectionSpace);
					msg = "Salary Config Info Added Successfully!!!";
				}

				if (status)
				{
					addActionMessage(msg);
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
					return SUCCESS;
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * Check session
	 */
	public String beforeSalaryCalculation()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * Calculate total salary
	 */
	public String calculateSalaryInfo()
	{
		try
		{
			if (userName != null && userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}

			GetSalary getSalary = new GetSalary();
			String empId[] = session.get("empIdofuser").toString().split("-");
			int to = (getRows() * getPage());
			int from = to - getRows();

			salaryGridModelList = new ArrayList<Object>();
			//System.out.println("empId[1]:" + empId[1]);
			salaryGridModelList = getSalary.getSalaryByEmpId(empId[1], from, to);
			setRows(salaryGridModelList.size());
			setRecords(salaryGridModelList.size());
			setSalaryGridModelList(salaryGridModelList);
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public boolean isHolidaysExist()
	{
		return holidaysExist;
	}

	public void setHolidaysExist(boolean holidaysExist)
	{
		this.holidaysExist = holidaysExist;
	}

	public boolean isAllowedLeaveExist()
	{
		return allowedLeaveExist;
	}

	public void setAllowedLeaveExist(boolean allowedLeaveExist)
	{
		this.allowedLeaveExist = allowedLeaveExist;
	}

	public boolean isDeductionExist()
	{
		return deductionExist;
	}

	public void setDeductionExist(boolean deductionExist)
	{
		this.deductionExist = deductionExist;
	}

	public boolean isExtraDayAmountExist()
	{
		return extraDayAmountExist;
	}

	public void setExtraDayAmountExist(boolean extraDayAmountExist)
	{
		this.extraDayAmountExist = extraDayAmountExist;
	}

	public String getHolidaysLable()
	{
		return holidaysLable;
	}

	public void setHolidaysLable(String holidaysLable)
	{
		this.holidaysLable = holidaysLable;
	}

	public String getAllowedLeaveLable()
	{
		return allowedLeaveLable;
	}

	public void setAllowedLeaveLable(String allowedLeaveLable)
	{
		this.allowedLeaveLable = allowedLeaveLable;
	}

	public String getDeductionLable()
	{
		return deductionLable;
	}

	public void setDeductionLable(String deductionLable)
	{
		this.deductionLable = deductionLable;
	}

	public String getExtraDayAmountLable()
	{
		return extraDayAmountLable;
	}

	public void setExtraDayAmountLable(String extraDayAmountLable)
	{
		this.extraDayAmountLable = extraDayAmountLable;
	}

	public List<Integer> getHolidayList()
	{
		return holidayList;
	}

	public void setHolidayList(List<Integer> holidayList)
	{
		this.holidayList = holidayList;
	}

	public List<Integer> getDeductionList()
	{
		return deductionList;
	}

	public void setDeductionList(List<Integer> deductionList)
	{
		this.deductionList = deductionList;
	}

	public List<Integer> getAllowLeaveList()
	{
		return allowLeaveList;
	}

	public void setAllowLeaveList(List<Integer> allowLeaveList)
	{
		this.allowLeaveList = allowLeaveList;
	}

	public List<Integer> getExtraDayPayList()
	{
		return extraDayPayList;
	}

	public void setExtraDayPayList(List<Integer> extraDayPayList)
	{
		this.extraDayPayList = extraDayPayList;
	}

	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<Object> getSalaryGridModelList()
	{
		return salaryGridModelList;
	}

	public void setSalaryGridModelList(List<Object> salaryGridModelList2)
	{
		this.salaryGridModelList = salaryGridModelList2;
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

	public Integer getTotalrows()
	{
		return totalrows;
	}

	public void setTotalrows(Integer totalrows)
	{
		this.totalrows = totalrows;
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
		if (this.records > 0 && this.rows > 0)
		{
			this.total = (int) Math.ceil((double) this.records / (double) this.rows);
		}
		else
		{
			this.total = 0;
		}
	}

	public String getHvalue()
	{
		return hvalue;
	}

	public void setHvalue(String hvalue)
	{
		this.hvalue = hvalue;
	}

	public String getAlValue()
	{
		return alValue;
	}

	public void setAlValue(String alValue)
	{
		this.alValue = alValue;
	}

	public String getDValue()
	{
		return dValue;
	}

	public void setDValue(String value)
	{
		dValue = value;
	}

	public String getEdaValue()
	{
		return edaValue;
	}

	public void setEdaValue(String edaValue)
	{
		this.edaValue = edaValue;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

}
