package com.Over2Cloud.ctrl.wfpm.salary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Attendance extends ActionSupport
{
	static final Log				log							= LogFactory.getLog(SalaryAction.class);
	private Map							session					= ActionContext.getContext().getSession();
	private String					userName				= (String) session.get("uName");
	private String					accountID				= (String) session.get("accountid");
	private SessionFactory	connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private List<Object>		empDetail;
	private String					oper;

	private String					empName;
	private String					emailId;
	private String					mobileNo;
	private String					attandance;
	private String					month;
	private String					mainHeaderName;

	public String execute()
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

	public String getAttendanceInfo()
	{
		//System.out.println("1111111111111111111111");
		try
		{
			empDetail = new ArrayList<Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SalaryDao salaryDao = new SalaryDao();

			List<TableColumes> tableColumn = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();

			ob1 = new TableColumes();
			ob1.setColumnname("empName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("mobOne");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("attendance");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("month");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			cbt.createTable22("attendanceInfo", tableColumn, connectionSpace);

			List empList = salaryDao.getEmployeeInfoAnd(cbt, DateUtil.getCurrentDateYearMonth());
			if (empList != null)
			{
				for (Iterator iterator = empList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					SalaryBean salaryBean = new SalaryBean();
					if (object[0] != null)
					{
						salaryBean.setEmpId(Integer.parseInt(object[0].toString()));
					}
					else
					{
						salaryBean.setEmpId(Integer.parseInt("0"));
					}

					if (object[1] != null)
					{
						salaryBean.setEmpName(object[1].toString());
					}
					else
					{
						salaryBean.setEmpName("NA");
					}

					if (object[2] != null)
					{
						salaryBean.setMobileNo(object[2].toString());
					}
					else
					{
						salaryBean.setMobileNo("NA");
					}

					if (object[3] != null)
					{
						salaryBean.setEmailId(object[3].toString());
					}
					else
					{
						salaryBean.setEmailId("NA");
					}

					if (object[4] != null)
					{
						salaryBean.setAttandance(Integer.parseInt(object[4].toString()));

					}
					else
					{
						salaryBean.setAttandance(0);
					}

					if (object[5] != null)
					{
						salaryBean.setMonth(DateUtil.convertDateYearMonthToMonthYear(object[5].toString()));

					}
					else
					{
						// else set current year and month as default
						salaryBean.setMonth(DateUtil.convertDateYearMonthToMonthYear(DateUtil.getCurrentDateYearMonth()));
					}

					empDetail.add(salaryBean);
				}
			}
			// Collections.sort(empDetail);
			setEmpDetail(empDetail);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String editAttendanceInfo()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			if (getOper() != null && getOper().equalsIgnoreCase("edit"))
			{
				List attendance = cbt.executeAllSelectQuery("select * from attendanceinfo where mobOne='" + getMobileNo() + "' and month = '"
						+ DateUtil.convertDateMonthYearToYearMonth(getMonth()) + "'", connectionSpace);

				if (attendance != null && attendance.size() > 0)
				{
					String query = "UPDATE attendanceinfo " + "SET attendance='" + getAttandance() + "'" + " WHERE mobOne='" + getMobileNo() + "'" + " and month = '"
							+ DateUtil.convertDateMonthYearToYearMonth(getMonth()) + "'";

					cbt.executeAllUpdateQuery(query, connectionSpace);
				}
				else
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = new InsertDataTable();
					ob.setColName("empName");
					ob.setDataName(getEmpName());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("mobOne");
					ob.setDataName(getMobileNo());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("attendance");
					ob.setDataName(getAttandance());
					insertData.add(ob);

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

					cbt.insertIntoTable("attendanceinfo", insertData, connectionSpace);
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<Object> getEmpDetail()
	{
		return empDetail;
	}

	public void setEmpDetail(List<Object> empDetail)
	{
		this.empDetail = empDetail;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getAttandance()
	{
		return attandance;
	}

	public void setAttandance(String attandance)
	{
		this.attandance = attandance;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
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
