package com.Over2Cloud.ctrl.wfpm.salary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;

public class GetSalary
{
	static final Log				log							= LogFactory.getLog(SalaryAction.class);
	private Map							session					= ActionContext.getContext().getSession();
	private String					userName				= (String) session.get("uName");
	private String					accountID				= (String) session.get("accountid");
	private SessionFactory	connectionSpace	= (SessionFactory) session.get("connectionSpace");

	public List<Object> getSalaryByEmpId(String empId, int from, int to)
	{
		List<Object> salaryGridModelList = new ArrayList<Object>();
		try
		{
			SalaryDao salaryDao = new SalaryDao();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			int basicSalary = salaryDao.getBasicSalary(empId, cbt);
			int totalIncentive = salaryDao.getTotalIncentive(empId, cbt);
			String s[] = session.get("empIdofuser").toString().split("-");
			List list = salaryDao.getSalaryConfigInfo(cbt);

			List empList = salaryDao.getAllEmployeeDetail(cbt, from, to);
			int holidays = 0; /* Total Holidays in Month */
			int allowedLeave = 0; /* Total Leave in Month */
			int deduction = 0; /* Per Day Deduction in % */
			int extraDayAmount = 0; /* Extra day amount in % */

			StringBuffer year = new StringBuffer(String.valueOf(DateUtil.getCurretnYear()));// example
																																											// ,
																																											// year
																																											// :
																																											// 2012
			StringBuffer month = new StringBuffer(String.valueOf(DateUtil.getCurrentMonth()));// example
																																												// ,
																																												// month
																																												// :
																																												// 05
			getPreviousMonthYearAndMonth(year, month);
			int monthDays = getMaximumDaysOfMonth(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()) - 1, 1);// example
																																																													// ,
																																																													// 2012
																																																													// ,
																																																													// 05
																																																													// ,
																																																													// 1
			int attandance = 0;

			if (list != null)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					holidays = Integer.parseInt(object[0].toString());
					allowedLeave = Integer.parseInt(object[1].toString());
					deduction = Integer.parseInt(object[2].toString());
					extraDayAmount = Integer.parseInt(object[3].toString());

				}
			}

			int workingDays = monthDays - holidays;

			if (empList != null)
			{
				int leave = 0;
				int extraDays = 0;
				int totalWorkingDays = 0;
				int totalLeave = 0;
				float salary = 0.0f;
				int deductionDays = 0;
				float salaryDeduct = 0.0f;
				float extraDaysSalary = 0.0f;
				int payDays = 0;
				float totalSalary = 0;
				int incentive = 0;
				float netSalary = 0.0f;

				for (Iterator iterator = empList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					incentive = salaryDao.getTotalIncentive(object[0].toString(), cbt);

					attandance = salaryDao.getEmployeeAttandance(object[2].toString(), cbt);

					leave = attandance - workingDays;
					salary = salaryDao.getBasicSalary(object[0].toString(), cbt);
					if (leave > 0)
					{
						extraDays = leave;
						leave = 0;
						if (extraDays > 0)
						{
							extraDaysSalary = ((salary * extraDayAmount) / 100) * extraDays;
						}
						else
						{
							extraDaysSalary = extraDays;
						}

						payDays = workingDays + extraDays;
						if (salary > 0)
						{
							totalSalary = salary + extraDaysSalary;
						}
						else
						{
							totalSalary = salary;
						}
					}
					else
					{

						totalLeave = allowedLeave - Math.abs(leave);
						if (totalLeave < 0)
						{
							deductionDays = Math.abs(totalLeave);
							payDays = workingDays - Math.abs(totalLeave);
							salaryDeduct = ((salary * deduction) / 100) * deductionDays;
							incentive = 0; /*
															 * If total leave less then 0 then set incentive
															 * set 0
															 */
						}
						else
						{
							deductionDays = 0;
							salaryDeduct = 0.0f;
							payDays = workingDays;
						}

						if (salary > 0)
						{
							totalSalary = salary - salaryDeduct;
						}

					}

					if (object[4] != null)
					{
						salary = Float.parseFloat(object[4].toString());

					}
					else
					{
						salary = 0.0f;
					}

					if (incentive > 0)
					{
						netSalary = totalSalary + ((salary * incentive) / 100);
					}
					else
					{
						netSalary = totalSalary;
					}
					SalaryBean salaryBean = new SalaryBean();
					salaryBean.setEmpId(Integer.parseInt(object[0].toString()));
					salaryBean.setEmpName(object[1].toString());
					salaryBean.setMobileNo(object[2].toString());
					salaryBean.setEmailId(object[3].toString());
					salaryBean.setMonthDays(monthDays);
					salaryBean.setHolidays(holidays);
					salaryBean.setWorkingDays(workingDays);
					salaryBean.setAttandance(attandance);
					salaryBean.setOnLeave(Math.abs(leave));
					salaryBean.setAllowLeave(allowedLeave);
					salaryBean.setDeduction(salaryDeduct);
					salaryBean.setDudctionDays(deductionDays);
					salaryBean.setExtraSalary(extraDaysSalary);
					salaryBean.setExtraDaysSalary(extraDays);
					salaryBean.setPayDay(payDays);
					salaryBean.setIncentive(incentive);
					salaryBean.setBasicSalary(salary);
					salaryBean.setSalaryPayble(totalSalary);
					salaryBean.setNetSalary(netSalary);
					salaryGridModelList.add(salaryBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return salaryGridModelList;
	}

	private int getMaximumDaysOfMonth(int year, int month, int date)
	{
		int days = 0;
		try
		{
			// System.out.println(year+"/"+month);
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, date);
			days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			// System.out.println("Number of Days: " + days);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime()
					+ " @Over2Cloud::>> Problem in SalaryAction at getMaximumDaysOfMonth()", e);
			e.printStackTrace();
		}

		return days;
	}

	private void getPreviousMonthYearAndMonth(StringBuffer year, StringBuffer month)// example
																																									// ,
																																									// year
																																									// :
																																									// 2012
																																									// month
																																									// :
																																									// 11
	{
		try
		{
			int currentMonth = Integer.valueOf(month.toString().trim());
			int currentyear = Integer.valueOf(year.toString().trim());

			if (currentMonth - 1 > 0)
			{
				month.replace(0, month.length(), String.valueOf(currentMonth - 1));
			}
			else
			{
				year.replace(0, year.length(), String.valueOf(currentyear - 1));
				month.replace(0, month.length(), "12");
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime()
					+ " @Over2Cloud::>> Problem in SalaryAction at getPreviousMonthYearAndMonth()", e);
			e.printStackTrace();
		}
	}

}
