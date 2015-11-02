package com.Over2Cloud.ctrl.compliance.complContacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.opensymphony.xwork2.ActionContext;

public class PsmOutcomeAction extends GridPropertyBean
{

	/*
	 * author : Manab this class is about PMS Outcome Fields From HR
	 */

	private List<GridDataPropertyView> requestGridColomns = new ArrayList<GridDataPropertyView>();

	private String toDate;
	private String fromDate;
	private List<Object> viewResponse;

	public String beforeViewpsmOutCome()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				/*
				 * employeeType = new LinkedHashMap<String, String>();
				 * LeaveHelper LH = new LeaveHelper(); String data[] =
				 * LH.getEmpDetailsByUserName("HR", userName, connectionSpace);
				 * deptName = new LinkedHashMap<String, String>(); if (data !=
				 * null && !data.toString().equalsIgnoreCase("")) {
				 * deptName.put("All", "All Department"); List dataCount =
				 * LH.getDeptName("HR", data[4], connectionSpace); if (dataCount
				 * != null && dataCount.size() > 0) { Object[] object = null;
				 * for (Iterator iterator = dataCount.iterator();
				 * iterator.hasNext();) { object = (Object[]) iterator.next();
				 * if (object != null) { deptName.put(object[0].toString(),
				 * object[1].toString()); } } } List list =
				 * cbt.executeAllSelectQuery
				 * ("Select id,etype from employee_type", connectionSpace);
				 * 
				 * if (list != null && list.size() > 0) { for (Iterator iterator
				 * = list.iterator(); iterator.hasNext();) { Object object[] =
				 * (Object[]) iterator.next(); if (object != null) {
				 * employeeType.put(object[0].toString(), object[1].toString());
				 * } } }
				 * 
				 * toDate = DateUtil.getCurrentDateUSFormat(); // fromDate =
				 * DateUtil.getCurrentDateYearMonth()+"-01"; fromDate =
				 * DateUtil.getLastDateFromLastDate(-1, toDate); //
				 * System.out.println
				 * ("B4 start>>>>>fromDate>>>"+fromDate+">>>toDate"+toDate); }
				 */
				toDate = DateUtil.getCurrentDateUSFormat();
				// fromDate = DateUtil.getCurrentDateYearMonth()+"-01";
				fromDate = DateUtil.getLastDateFromLastDate(-1, toDate);
				// System.out.println("B4 start>>>>>fromDate>>>"+fromDate+">>>toDate"+toDate);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}

	}

	public String pmsOutcomeViewHeader()
	{

		// System.out.println("In beforeAttendanceGridView>>>>>>>>>>>>>>>>....");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setAttendanceGridColomnNames();
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}

	}

	public void setAttendanceGridColomnNames()
	{
		try
		{
			Map session = ActionContext.getContext().getSession();
			String accountID = (String) session.get("accountid");
			GridDataPropertyView gpv;
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("empId");
			gpv.setHeadingName("Employee Code");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("name");
			gpv.setHeadingName("Employee Name");
			gpv.setHideOrShow("false");
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("designation");
			gpv.setHeadingName("Designation");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("onTimeView");
			gpv.setWidth(80);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("department");
			gpv.setHeadingName("Department");
			// gpv.setFormatter("absentView");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("qualification");
			gpv.setHeadingName("Qualification");
			// gpv.setFormatter("earlyView");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("headOffice");
			gpv.setHeadingName("Company Name");
			// gpv.setFormatter("late15View");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("branchName");
			gpv.setHeadingName("Plant Name");
			// gpv.setFormatter("late30View");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("dob");
			gpv.setHeadingName("Date of Birth");
			// gpv.setFormatter("late60View");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("doj");
			gpv.setHeadingName("Date of Joining");
			// gpv.setFormatter("lateGtrHourView");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ageTillToday");
			gpv.setHeadingName("Age As on");
			// gpv.setFormatter("early15View");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("joinedAs");
			gpv.setHeadingName("Joined As");
			// gpv.setFormatter("early30View");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("preCompanyName");
			gpv.setHeadingName("Previous Employer Name");
			// gpv.setFormatter("earlyGtr30View");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("preCompanyAdd");
			gpv.setHeadingName("Previous Employer Address");
			// gpv.setFormatter("lateGoingView");
			gpv.setHideOrShow("false");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("preCompanyDesig");
			gpv.setHeadingName("Previous Employer Desig.");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("complWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("preCompanyDept");
			gpv.setHeadingName("Previous Employer Dept.");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("complWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("preCompanyExp");
			gpv.setHeadingName("Previous Exp.in Year");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("currentCompanyExp");
			gpv.setHeadingName("JBM Exp.");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalExp");
			gpv.setHeadingName("Total Exp.");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("lastPromotion");
			gpv.setHeadingName("Last Promotion date");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("currentCTC");
			gpv.setHeadingName("Current CTC Rs. PA");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("incrementPercn");
			gpv.setHeadingName("Increment %");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("incrementAmt");
			gpv.setHeadingName("Increment Amtount");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("addtionAmmount");
			gpv.setHeadingName("Additional Ammount");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("newCTC");
			gpv.setHeadingName("New CTC Rs. PA");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("newCTCEffDate");
			gpv.setHeadingName("New CTC Effective Date");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("isPromotion");
			gpv.setHeadingName("Promotion)");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("promotionEffDate");
			gpv.setHeadingName("Promotion Effective date");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("shortWorkHours");
			gpv.setWidth(100);
			requestGridColomns.add(gpv);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// data in grid

	public String pmsoutcomeGridDataView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder builder = new StringBuilder("select id, empId, name, designation, department, qualification, headOffice, branchName,");
				builder.append(" dob, doj, ageTillToday, joinedAs, preCompanyName, preCompanyAdd, preCompanyDesig,");
				builder.append(" preCompanyDept, preCompanyExp, currentCompanyExp, totalExp, lastPromotion, ");
				builder.append(" currentCTC, incrementAmt,incrementPercn,  newCTC, newCTCEffDate, isPromotion, ");
				builder.append(" promotionEffDate,addtionAmmount FROM pms_outcome_data;");
				System.out.println("query PMS OutCome LINK "+builder);
				List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
				PmsOutcomePOJO pojo = null;
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						pojo = new PmsOutcomePOJO();
						Object[] object = (Object[]) iterator.next();
						pojo.setId(checkValueWithNull(object[0].toString()));
						pojo.setEmpId(checkValueWithNull(object[1].toString()));
						pojo.setName(checkValueWithNull(object[2].toString()));
						pojo.setDesignation(checkValueWithNull(object[3].toString()));
						pojo.setDepartment(checkValueWithNull(object[4].toString()));
						pojo.setQualification(checkValueWithNull(object[5].toString()));
						pojo.setHeadOffice(checkValueWithNull(object[6].toString()));
						pojo.setBranchName(checkValueWithNull(object[7].toString()));
						pojo.setDob(DateUtil.convertDateToIndianFormat(checkValueWithNull(object[8].toString())));
						pojo.setDoj(DateUtil.convertDateToIndianFormat(checkValueWithNull(object[9].toString())));

						System.out.println(checkValueWithNull(object[8].toString()));
						String[] dateOfBirth = checkValueWithNull(object[8].toString()).split("-");

						System.out.println("yy " + dateOfBirth[0]);
						System.out.println("MM " + dateOfBirth[1]);
						System.out.println("DD " + dateOfBirth[2]);
						String jj = dateOfBirth[2] + "/" + dateOfBirth[1] + "/" + dateOfBirth[0];
						/*
						 * System.out.println("jj "+jj);
						 * System.out.println("kjhkjhkhj "
						 * +ageTillToday(dateOfBirth[0], dateOfBirth[1],
						 * dateOfBirth[2]));
						 */

						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date birthDate = sdf.parse(jj); // Yeh !! It's my date
														// of birth <img
														// src="http://howtodoinjava.com/wp-includes/images/smilies/icon_smile.gif"
														// alt=":-)"
														// class="wp-smiley">
						Age age = calculateAge(birthDate);
						// My age is
						System.out.println(age);

						pojo.setAgeTillToday(age.toString());

						pojo.setJoinedAs(checkValueWithNull(object[11].toString()));
						pojo.setPreCompanyName(checkValueWithNull(object[12].toString()));
						pojo.setPreCompanyAdd(checkValueWithNull(object[13].toString()));
						pojo.setPreCompanyDesig(checkValueWithNull(object[14].toString()));
						pojo.setPreCompanyDept(checkValueWithNull(object[15].toString()));
						pojo.setPreCompanyExp(checkValueWithNull(object[16].toString()));
						pojo.setCurrentCompanyExp(checkValueWithNull(object[17].toString()));

						if (!object[16].toString().equalsIgnoreCase("NA"))
						{
							double total = Double.parseDouble(object[16].toString()) + Double.parseDouble(object[17].toString());
							pojo.setTotalExp(checkValueWithNull(String.valueOf(total)));

						} else
						{

							pojo.setTotalExp(checkValueWithNull(object[17].toString()));
						}
						pojo.setLastPromotion(checkValueWithNull(object[19].toString()));
						pojo.setCurrentCTC(checkValueWithNull(object[20].toString()));
						pojo.setIncrementAmt(checkValueWithNull(object[21].toString()));
						pojo.setIncrementPercn(checkValueWithNull(object[22].toString()));
						pojo.setAddtionAmmount(checkValueWithNull(object[27].toString()));
						pojo.setNewCTC(checkValueWithNull(object[23].toString()));
						

						if(!object[24].toString().equalsIgnoreCase("NA")){
							pojo.setNewCTCEffDate(DateUtil.convertDateToIndianFormat(checkValueWithNull(object[24].toString())));
						}
						else {
							pojo.setNewCTCEffDate(checkValueWithNull(object[24].toString()));
						}
						pojo.setIsPromotion(checkValueWithNull(object[25].toString()));
						
						if(!object[26].toString().equalsIgnoreCase("NA")){
							pojo.setPromotionEffDate(DateUtil.convertDateToIndianFormat(checkValueWithNull(object[26].toString())));
						}
						else {
							pojo.setPromotionEffDate(checkValueWithNull(object[26].toString()));
						}
					
						Listhb.add(pojo);
					}
				}
				setViewResponse(Listhb);
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}

	}

	public String checkValueWithNull(Object value)
	{
		return (value == null ? null : value.toString());
	}

	/*
	 * public static Object ageTillToday(String yy, String MM, String DD){
	 * String age ="";
	 * 
	 * int daysInMon[] = {31, 28, 31, 30, 31, 30,31, 31, 30, 31, 30, 31}; //Days
	 * in month int days, month, year; char[] dob = new char[110];
	 * days=Integer.parseInt(DD); month=Integer.parseInt(MM);
	 * year=Integer.parseInt(yy); System.out.println("Date of Birth: "+days+ "/"
	 * +month+ "/" +year); Date d = new Date();
	 * System.out.println("Current Date: " +d.getDate()+ "/" +(d.getMonth()+1)+
	 * "/" +(d.getYear()+1900));
	 * 
	 * days = daysInMon[month - 1] - days + 1;
	 * 
	 * Calculating the num of year, month and date days = days + d.getDate();
	 * month = (12 - month) + d.getMonth(); year = (d.getYear() + 1900) - year -
	 * 1;
	 * 
	 * if (month >= 12) { year = year + 1; month = month - 12; }
	 * 
	 * Result portion
	 * 
	 * System.out.println("Age:" +year+ "years" +month+ "months" +days+ "days");
	 * age = "Age:" +year+ "years" +month+ "months" +days+ "days"; return age; }
	 */

	public static Age calculateAge(Date birthDate)
	{
		int years = 0;
		int months = 0;
		int days = 0;
		// create calendar object for birth day
		Calendar birthDay = Calendar.getInstance();
		birthDay.setTimeInMillis(birthDate.getTime());
		// create calendar object for current day
		long currentTime = System.currentTimeMillis();
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(currentTime);
		// Get difference between years
		years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
		int currMonth = now.get(Calendar.MONTH) + 1;
		int birthMonth = birthDay.get(Calendar.MONTH) + 1;
		// Get difference between months
		months = currMonth - birthMonth;
		// if month difference is in negative then reduce years by one and
		// calculate the number of months.
		if (months < 0)
		{
			years--;
			months = 12 - birthMonth + currMonth;
			if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
				months--;
		} else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
		{
			years--;
			months = 11;
		}
		// Calculate the days
		if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
			days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
		else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
		{
			int today = now.get(Calendar.DAY_OF_MONTH);
			now.add(Calendar.MONTH, -1);
			days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
		} else
		{
			days = 0;
			if (months == 12)
			{
				years++;
				months = 0;
			}
		}
		// Create new Age object
		return new Age(days, months, years);
	}

	public List<GridDataPropertyView> getRequestGridColomns()
	{
		return requestGridColomns;
	}

	public void setRequestGridColomns(List<GridDataPropertyView> requestGridColomns)
	{
		this.requestGridColomns = requestGridColomns;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public List<Object> getViewResponse()
	{
		return viewResponse;
	}

	public void setViewResponse(List<Object> viewResponse)
	{
		this.viewResponse = viewResponse;
	}

}
