package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType; //import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.Over2Cloud.ctrl.user.UserMappingHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DSRActionControl extends ActionSupport implements ServletRequestAware
{
	static final Log										log							= LogFactory.getLog(DSRActionControl.class);
	private Map													session					= ActionContext.getContext().getSession();
	private String											userName				= (String) session.get("uName");
	private String											accountID				= (String) session.get("accountid");
	private SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private List<String>								curremonth;
	private String											mainHeader;
	private String											month;
	private List<DailyReportBean>				kpiList;
	private List<DailyReportBean>				kpiGridModelList;
	private List<DailyReportBean>				gridModelList;
	private List<DailyReportBean>				listfistweekdays;
	private List<DailyReportBean>				listSecondweekdays;
	private List<DailyReportBean>				listThirdweekdays;
	private List<DailyReportBean>				listFourthweekdays;
	private String											kpiId;
	private String											searchOper;
	private String											filters;
	private Map<String, Object>					userdata;
	List<FullAchievmentReport>					fullAchievmentList;
	String[]														months					= { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
			"November", "December"													};
	Calendar														cal							= Calendar.getInstance();
	private Map<String, String>					empInfo;
	private String											currentmonth;
	List<Temp>													kpiGridModel;
	List<Temp>													empDetail;
	private Integer											rows						= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer											page						= 0;
	// sorting order - asc or desc
	private String											sord						= "";
	// get index row - i.e. user click to sort.
	private String											sidx						= "";
	// Search Field
	private String											searchField			= "";
	// The Search String
	private String											searchString		= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	// Your Total Pages
	private Integer											total						= 0;
	// All Record
	private Integer											records					= 0;
	private boolean											loadonce				= false;
	// Grid colomn view
	private String											oper;
	private long												id;
	private List												list;
	private HttpServletRequest					request;
	private List<IVRSDataBean>					ivrsData;
	private List<IVRSDataBean>					ivrsDataDetails;
	private HashMap<Double, Double>			doubleMap;
	private HashMap<Double, Double>			achiveMap;
	private List<DailyReportBean>				kpiHistoryGridModel;
	private String											userID;
	private List<GridDataPropertyView>	viewIVRS;
	private String											mode;
	private String											mobileNo;
	private String											userN;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			setMainHeader("Daily Sales Report >> View");
			setGridHeader();
			getDaysByWeek();
			// getWeekList();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <execute> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setGridHeader()
	{
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		Calendar cal = Calendar.getInstance();
		Integer date = new Integer(cal.get(Calendar.DATE));
		Integer Year = new Integer(cal.get(Calendar.YEAR));
		month = date + "-" + months[cal.get(Calendar.MONTH)] + "-" + Year + "-" + "Daily Sales Report";
	}

	public void getDaysByWeek()
	{
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Calendar calendar = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		listfistweekdays = new LinkedList<DailyReportBean>();
		listSecondweekdays = new LinkedList<DailyReportBean>();
		listThirdweekdays = new LinkedList<DailyReportBean>();
		listFourthweekdays = new LinkedList<DailyReportBean>();

		String[] arryaValue = { "one", "two", "three", "fourth", "fifth", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
				"fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty", "twentyone", "twentytwo", "twentythree", "twentyfourth", "twentyfifth",
				"twentysix", "twentyseven", "twentyeight", "twentynine", "thirty", "thirtyone" };

		for (int i = 0; i < days; i++)
		{
			DailyReportBean drb = new DailyReportBean();

			if (i < 7)
			{
				drb.setFistweekdays(new Integer(i + 1).toString() + "th");
				drb.setFistweekdatename(arryaValue[i].toString());
				listfistweekdays.add(drb);
			}
			else if (i > 6 && i < 14)
			{
				drb.setSecondweekdays(new Integer(i + 1).toString() + "th");
				drb.setSecondweekdatename(arryaValue[i].toString());
				listSecondweekdays.add(drb);
			}
			else if (i > 13 && i < 21)
			{

				drb.setThirdtweekdays(new Integer(i + 1).toString() + "th");
				drb.setThirdweekdatename(arryaValue[i].toString());
				listThirdweekdays.add(drb);
			}
			else
			{
				drb.setFourthweekdays(new Integer(i + 1).toString() + "th");
				drb.setFourthweekdatename(arryaValue[i].toString());
				listFourthweekdays.add(drb);
			}
		}
	}

	public String getDSRRecords()
	{
		try
		{
			// //System.out.println(
			// "***********************getDSRRecords()***********************");
			// //System.out.println("mode:"+mode);
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			userdata = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// String empId = (String)session.get("empIdofuser");//o-100000
			//System.out.println("getUserID():" + getUserID());
			if (getUserID() == null || getUserID().equalsIgnoreCase("")) return ERROR;

			//System.out.println("getUserID():" + getUserID());
			String empId = (String)session.get("empIdofuser").toString().split("-")[1];
			if (empId == null) return ERROR;

			// Gudiya code for month searching
			// //System.out.println("getSearchField()>>>>>>>>"+getSearchField());
			// //System.out.println("getSearchOper()>>>>>>>>"+getSearchOper());
			// //System.out.println("getSearchString()>>>>>>>>"+getSearchString());
			// String currentMonth = DateUtil.getCurrentDateMonthYear();
			String currentMonth = "";
			int month = DateUtil.getCurrentMonth();
			currentMonth = month + "-" + DateUtil.getCurretnYear();
			if (month < 10)
			{
				currentMonth = "0" + month + "-" + DateUtil.getCurretnYear();
			}
			if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
			{
				if (getSearchOper().equalsIgnoreCase("eq"))
				{
					currentMonth = getSearchString().trim();
				}
			}

			// String tempempIdofuser[]=empId.split("-");
			// empId=tempempIdofuser[1];
			// String currentMonth = "";
			String query = "select KPIId, targetvalue from target where empID=" + empId + " and targetMonth='" + currentMonth + "'";
			//System.out.println("query??????????????????1111" + query);
			List kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
			//System.out.println("kpiIds.size():" + kpiIds.size());
			DailyReportBean dailyReportBean = null;
			kpiList = new ArrayList<DailyReportBean>();
			String kpi = "";
			String targetValue = "";
			JSONObject jsonFilter = null;
			String searchString = "";
			String searchString2 = "";
			boolean searchRecord = false;

			if (filters != null && !filters.equalsIgnoreCase(""))
			{
				//System.out.println("jsonFilter>>>>>>>>>>" + filters);
				jsonFilter = (JSONObject) JSONSerializer.toJSON(filters);

			}
			if (jsonFilter != null)
			{
				JSONArray rules = jsonFilter.getJSONArray("rules");
				int rulesCount = JSONArray.getDimensions(rules)[0];

				if (rulesCount == 1)
				{
					searchRecord = true;
					JSONObject rule = rules.getJSONObject(0);
					searchString = rule.getString("data");

					if (!searchString.equalsIgnoreCase(""))
					{
						// query =
						// "select kpi_id from dailyreport where currentMonth='"+
						// searchString +"'";
						query = "select kpi_id, kpi_target from dailyreport where currentMonth ='" + searchString + "'";
						kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
						//System.out.println("kpiIds.size(1):" + kpiIds.size());
					}
				}
				if (rulesCount > 1)
				{
					JSONObject rule = rules.getJSONObject(1);
					searchString2 = rule.getString("data");

					if (!searchString.equalsIgnoreCase(""))
					{
						query = "select kpi_id, kpi_target from dailyreport where currentMonth between '" + searchString + "' and '" + searchString2 + "'";
						kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
						//System.out.println("kpiIds.size(2):" + kpiIds.size());
					}
				}

			}
			if (kpiIds != null)
			{
				if (kpiIds.size() > 0)
				{
					if (!searchRecord)
					{
						for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							kpi = object[0].toString();
							targetValue = object[1].toString();
						}
					}
					else
					{
						for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							kpi = kpi + object[0].toString() + "#";
							targetValue = targetValue + object[1].toString() + "#";
						}
					}
					kpi = kpi.replaceAll("#", ",");
					kpi = kpi.substring(0, kpi.length() - 1);

					List kpiLists = cbt.executeAllSelectQuery("select  kpi from krakpicollection where id in (" + kpi + ")", connectionSpace);
					String target[] = targetValue.split("#");

					if (kpiLists != null)
					{
						List targetInfo = new ArrayList();
						int countFirst = 0;
						int countSecond = 0;
						int countThird = 0;
						int countFourth = 0;
						int total = 0;
						int totalTarget = 0;
						int totalAchived = 0;
						int remTarget = 0;
						String firstWeek[] = null;
						String secondWeek[] = null;
						String thirdWeek[] = null;
						String fourthWeek[] = null;
						String currMonth = "";
						String kpiId[] = kpi.split(",");
						/* for (int i = 0; i < kpiLists.size(); i++) { */
						for (int i = 0; i < target.length; i++)
						{
							dailyReportBean = new DailyReportBean();
							dailyReportBean.setId(kpiId[i]);
							dailyReportBean.setKpiname(kpiLists.get(i).toString());
							dailyReportBean.setTargetvalue(target[i]);
							totalTarget += Integer.parseInt(target[i].equalsIgnoreCase("") ? "0" : target[i]);
							if (getMode() != null && !getMode().equalsIgnoreCase("BOTH"))
							{
								query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
										+ " from dailyreport where kpi_id IN (select id from krakpicollection where kpi='" + kpiLists.get(i) + "') and currentMonth='"
										+ currentMonth + "' and user='" + userName + "' and mode='" + getMode() + "'";
								//System.out.println("if>>>>>>>>>>>+++++++++++++++++:" + query);
							}
							else
							{
								query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
										+ " from dailyreport where kpi_id IN (select id from krakpicollection where kpi='" + kpiLists.get(i) + "') and currentMonth='"
										+ currentMonth + "' and user='" + userName + "'";
								//System.out.println("else +++++++++++++++++:" + query);
							}

							targetInfo = cbt.executeAllSelectQuery(query, connectionSpace);
							if (targetInfo != null && targetInfo.size() > 0)
							{
								for (Iterator iterator = targetInfo.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									firstWeek = object[0].toString().split("#");
									/*
									 * //System.out.println("firstWeek:"+firstWeek.length);
									 * //System.out.println("secondWeek:"+secondWeek.length);
									 * //System.out.println("thirdWeek:"+thirdWeek.length);
									 * //System.out.println("fourthWeek:"+fourthWeek.length);
									 */

									secondWeek = object[1].toString().split("#");
									thirdWeek = object[2].toString().split("#");
									fourthWeek = object[3].toString().split("#");
									currMonth = object[4].toString();

									for (int j = 0; j < firstWeek.length; j++)
									{
										countFirst += Integer.parseInt(firstWeek[j]);
									}

									for (int j = 0; j < secondWeek.length; j++)
									{
										countSecond += Integer.parseInt(secondWeek[j]);
									}

									for (int j = 0; j < thirdWeek.length; j++)
									{
										countThird += Integer.parseInt(thirdWeek[j]);
									}

									for (int j = 0; j < fourthWeek.length; j++)
									{
										countFourth += Integer.parseInt(fourthWeek[j]);
									}
									for (int j = 0; j < firstWeek.length; j++)
									{
										dailyReportBean.setOne(firstWeek[0]);
										dailyReportBean.setTwo(firstWeek[1]);
										dailyReportBean.setThree(firstWeek[2]);
										dailyReportBean.setFourth(firstWeek[3]);
										dailyReportBean.setFifth(firstWeek[4]);
										dailyReportBean.setSix(firstWeek[5]);
										dailyReportBean.setSeven(firstWeek[6]);
									}

									for (int j = 0; j < secondWeek.length; j++)
									{
										dailyReportBean.setEight(secondWeek[0]);
										dailyReportBean.setNine(secondWeek[1]);
										dailyReportBean.setTen(secondWeek[2]);
										dailyReportBean.setEleven(secondWeek[3]);
										dailyReportBean.setTwelve(secondWeek[4]);
										dailyReportBean.setThirteen(secondWeek[5]);
										dailyReportBean.setFourteen(secondWeek[6]);

									}

									for (int j = 0; j < thirdWeek.length; j++)
									{
										dailyReportBean.setFifteen(thirdWeek[0]);
										dailyReportBean.setSixteen(thirdWeek[1]);
										dailyReportBean.setSeventeen(thirdWeek[2]);
										dailyReportBean.setEighteen(thirdWeek[3]);
										dailyReportBean.setNineteen(thirdWeek[4]);
										dailyReportBean.setTwenty(thirdWeek[5]);
										dailyReportBean.setTwentyone(thirdWeek[6]);
									}

									for (int j = 0; j < fourthWeek.length; j++)
									{
										dailyReportBean.setTwentytwo(fourthWeek[0]);
										dailyReportBean.setTwentythree(fourthWeek[1]);
										dailyReportBean.setTwentyfourth(fourthWeek[2]);
										dailyReportBean.setTwentyfifth(fourthWeek[3]);
										dailyReportBean.setTwentysix(fourthWeek[4]);
										dailyReportBean.setTwentyseven(fourthWeek[5]);
										dailyReportBean.setTwentyeight(fourthWeek[6]);

										try
										{
											if (fourthWeek.length > 7 && fourthWeek[7] != null)
											{
												dailyReportBean.setTwentynine(fourthWeek[7]);
											}
										}
										catch (Exception e)
										{
											e.printStackTrace();
										}

										try
										{
											if (fourthWeek.length > 8 && fourthWeek[8] != null)
											{
												dailyReportBean.setThirty(fourthWeek[8]);
											}
										}
										catch (Exception e)
										{
											e.printStackTrace();
										}

										try
										{
											if (fourthWeek.length > 9 && fourthWeek[9] != null)
											{
												dailyReportBean.setThirtyone(fourthWeek[9]);
											}
										}
										catch (Exception e)
										{
											e.printStackTrace();
										}
									}
									dailyReportBean.setCurrentmonth(currMonth);
								}

								//System.out.println("firstWeek:" + firstWeek.length);
								//System.out.println("secondWeek:" + secondWeek.length);
								//System.out.println("thirdWeek:" + thirdWeek.length);
								//System.out.println("fourthWeek:" + fourthWeek.length);
							}
							total = countFirst + countSecond + countThird + countFourth;
							dailyReportBean.setTotalsale(String.valueOf(total));
							dailyReportBean.setRemainng_target(String.valueOf(Integer.parseInt(target[i].equals("") ? "0" : target[i]) - total));
							remTarget += (Integer.parseInt(target[i].equals("") ? "0" : target[i]) - total);
							kpiList.add(dailyReportBean);

							totalAchived += total;
							countFirst = 0;
							countSecond = 0;
							countThird = 0;
							countFourth = 0;
						}
						userdata.put("remainng_target", remTarget);
						userdata.put("targetvalue", totalTarget);
						userdata.put("totalsale", totalAchived);
						userdata.put("kpiname", "Total :");
						setKpiGridModelList(kpiList);
					}

				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <getDSRRecords> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getFullAchievmentReport()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			fullAchievmentList = new ArrayList<FullAchievmentReport>();
			FullAchievmentReport achievmentReport = new FullAchievmentReport();
			if (getKpiId() != null)
			{
				String query = "select historyIds from dailyreport where kpi_id=" + getKpiId();
				List historyList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (historyList != null)
				{
					String ids = historyList.toString();
					ids = ids.substring(1, ids.length() - 1);

					String actionIdList[] = ids.split("#");
					String query1 = "";
					if (actionIdList != null)
					{
						for (int i = 0; i < actionIdList.length; i++)
						{
							if (!actionIdList[i].equalsIgnoreCase(""))
							{
								achievmentReport = new FullAchievmentReport();
								achievmentReport.setHistoryId(Integer.parseInt(actionIdList[i]));
								query1 = "select user, actionDate, description from takeaction where id=" + actionIdList[i];
								historyList = cbt.executeAllSelectQuery(query1, connectionSpace);

								if (historyList != null && historyList.size() > 0)
								{
									for (Iterator iterator = historyList.iterator(); iterator.hasNext();)
									{
										Object object[] = (Object[]) iterator.next();
										achievmentReport.setActionBy(object[0].toString());
										achievmentReport.setActionDate(object[1].toString());
										achievmentReport.setActionTaken(object[2].toString());
									}
								}
								fullAchievmentList.add(achievmentReport);
							}
						}
					}

				}
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <getFullAchievmentReport> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getCurrentMonthDropdown()
	{
		ArrayList<String> cmonth = new ArrayList<String>();
		cmonth.add(months[0] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[1] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[2] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[3] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[4] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[5] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[6] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[7] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[8] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[9] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[10] + "-" + cal.get(Calendar.YEAR));
		cmonth.add(months[11] + "-" + cal.get(Calendar.YEAR));
		setCurremonth(cmonth);
		return SUCCESS;
	}

	public String beforeHierarchyView()
	{

		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			setMainHeader("Hierarchy wise Daily Sales Report >> View");
			setGridHeader();
			// getDaysByWeek();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String editDailySalesReport()
	{
		if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

		try
		{
			if (getOper().equalsIgnoreCase("edit"))
			{
				DSRgerneration rgerneration = new DSRgerneration();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				String empId[] = session.get("empIdofuser").toString().split("-");
				rgerneration.setManualDSRRecords(getId(), empId[1], DSRMode.KPI, requestParameterNames, request, DSRType.ONLINE);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String hierarchyDSRRecords()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String empId = (String) session.get("empIdofuser");// o-100000
			String tempempIdofuser[] = empId.split("-");
			empId = tempempIdofuser[1];
			String query = "select id, empName, useraccountid from employee_basic " + "where mapwith=(select useraccountid from employee_basic where id=" + empId
					+ ") and id != " + empId;
			List<Temp> empIds = cbt.executeAllSelectQuery(query, connectionSpace);
			empDetail = new ArrayList<Temp>();
			for (Iterator iterator = empIds.iterator(); iterator.hasNext();)
			{
				Object object[] = (Object[]) iterator.next();
				Temp dailyReportBean = new Temp();
				dailyReportBean.setId(Integer.parseInt(object[0].toString()));
				dailyReportBean.setEmpDetail(object[1].toString());
				empDetail.add(dailyReportBean);
			}
			setKpiGridModel(empDetail);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getSubHierarchyDSRRecords()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			kpiGridModel = new ArrayList<Temp>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// String empId =
			// ActionContext.getContext().getSession().get("empIdofuser").toString();
			String empId = (String) session.get("empIdofuser");// o-100000
			String tempempIdofuser[] = empId.split("-");
			empId = tempempIdofuser[1];
			String query = "select id, empName from employee_basic " + "where mapwith=(select useraccountid from employee_basic where id=" + getId() + ") and id != "
					+ getId();
			empInfo = new LinkedHashMap<String, String>();
			List<Temp> empIds = cbt.executeAllSelectQuery(query, connectionSpace);
			empDetail = new ArrayList<Temp>();
			for (Iterator iterator = empIds.iterator(); iterator.hasNext();)
			{
				Object object[] = (Object[]) iterator.next();
				Temp dailyReportBean = new Temp();
				dailyReportBean.setId(Integer.parseInt(object[0].toString()));
				dailyReportBean.setEmpDetail(object[1].toString());
				empDetail.add(dailyReportBean);
			}
			setKpiGridModel(empDetail);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String showDSRGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			setGridHeader();
			getDaysByWeek();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <showDSRGrid> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeIVRSReportView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			//System.out.println("user888888888888:" + userN);
			setMainHeader("IVRS Report");
			setIVRSViewProp("mapped_obd_report_configuration", "obd_report_configuration");
			setGridHeader();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setIVRSViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		viewIVRS = new ArrayList<GridDataPropertyView>();

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(table1, accountID, connectionSpace, "", 0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setAlign(gdp.getAlign());
			// gpv.setFormatter(gdp.getFormatter());
			viewIVRS.add(gpv);
		}
	}

	public String getIVRSReport()
	{
		//System.out.println("111111111111111111111111111111111111111111");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			//System.out.println("2222222222222222222222222222222222222222");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//System.out.println("33333333333333333333333333333333333333");
			ReportDao rd = new ReportDao();
			// get IVRS data of current month from table 'ivrsreport'
			// String user = uName;
			//System.out.println("000000000========================:" + userN);
			// ************this block is due to value of userN is with extra single
			// quotes that we don't want but it should be fixed later
			// permanently*********
			// char sTemp = userN.charAt(0)+"";
			if (userN != null && String.valueOf(userN.charAt(0)).equals("'")) userN = userN.substring(1);
			if (userN != null && userN.lastIndexOf("'") >= 0) userN = userN.substring(0, userN.length() - 1);
			//**************end*******************************************************
			// *
			if (userN != null && userN.equalsIgnoreCase("ALL_USER"))
			{
				userN = session.get("uName").toString();
				List<String> childUserList = new UserMappingHelper().getAllUsers(userN, true, connectionSpace);
				StringBuilder sb = new StringBuilder();
				if (childUserList != null && childUserList.size() > 0)
				{
					for (String u : childUserList)
					{
						sb.append("'" + u + "',");
					}
				}
				userN = sb.substring(0, sb.lastIndexOf(","));
				//System.out.println("user:>>>>//////////>>>>>> " + userN);
			}
			else
			{
				userN = "'" + userN + "'";
			}

			List report = rd.getIVRSCumulativeData(DateUtil.getCurrentDateYearMonth(), userN, connectionSpace);
			List list = null;

			int total = 0;
			int achive = 0;
			ivrsData = new ArrayList<IVRSDataBean>();
			if (report != null && report.size() > 0)
			{
				for (Iterator iterator = report.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					IVRSDataBean dataBean = new IVRSDataBean();
					//System.out.println("object[3]*************" + object[3]);
					dataBean.setName(object[0].toString());
					dataBean.setMobileNo(object[1].toString());
					dataBean.setId((long) Double.parseDouble(object[1].toString()));

					//System.out.println("object[1]:" + object[1]);
					//System.out.println("dataBean.getId():" + dataBean.getId());

					list = cbt.executeAllSelectQuery("SELECT totalCall,productiveCall,totalSale,newOutlet FROM obdtargetdata WHERE mobile='" + object[1]
							+ "' and currentMonth = '" + DateUtil.getCurrentDateMonthYear() + "' ", connectionSpace);

					for (Iterator iterator2 = list.iterator(); iterator2.hasNext();)
					{
						Object[] obj = (Object[]) iterator2.next();
						total += (Integer.parseInt(obj[0].toString()) + Integer.parseInt(obj[1].toString()) + Integer.parseInt(obj[2].toString()) + Integer.parseInt(obj[3]
								.toString()));
					}
					if (object[2].toString().equalsIgnoreCase("0"))
					{
						dataBean.setAvailability("Not Working");
					}
					else if (object[2].toString().equalsIgnoreCase("1"))
					{
						dataBean.setAvailability("Working");
					}
					else
					{
						dataBean.setAvailability("Not Reachable");
					}

					dataBean.setTotalCall(String.valueOf((int) Double.parseDouble(object[3].toString())));
					dataBean.setProductiveCall(String.valueOf((int) Double.parseDouble(object[4].toString())));
					dataBean.setTotalSale(String.valueOf((int) Double.parseDouble(object[5].toString())));
					dataBean.setNewOutlet(String.valueOf((int) Double.parseDouble(object[6].toString())));
					dataBean.setTalkTime(String.valueOf((int) Double.parseDouble(object[7].toString())));
					dataBean.setPulse(String.valueOf((int) Double.parseDouble(object[8].toString())));
					dataBean.setDate(DateUtil.convertDateToIndianFormat(object[9].toString()));
					dataBean.setTime(object[10].toString());
					dataBean.setTarget(String.valueOf(total));

					achive += ((int) Double.parseDouble((!object[3].toString().equalsIgnoreCase("") ? object[3].toString() : "0"))
							+ (int) Double.parseDouble((!object[4].toString().equalsIgnoreCase("") ? object[4].toString() : "0"))
							+ (int) Double.parseDouble((!object[5].toString().equalsIgnoreCase("") ? object[5].toString() : "0")) + (int) Double.parseDouble((!object[6]
							.toString().equalsIgnoreCase("") ? object[6].toString() : "0")));

					dataBean.setAchive(String.valueOf(achive));
					if (total != 0)
					{
						dataBean.setAchivePerc(String.valueOf(((achive * 100) / total)));
					}
					else
					{
						dataBean.setAchivePerc(String.valueOf(("0")));
					}
					ivrsData.add(dataBean);
					total = 0;
					achive = 0;
				}
			}
			setIvrsData(ivrsData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("555555555555555555555555555555555555555555555555555555555");
		return SUCCESS;
	}

	public String beforeDashboardView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			/*
			 * pointsFromMap = new HashMap<Integer, Integer>(); targetMap = new
			 * HashMap<Integer, Integer>(); achiveMap = new HashMap<Integer,
			 * Integer>(); targetMap.put(0, 0); targetMap.put(100, 100);
			 * achiveMap.put(0, 0); achiveMap.put(80, 80); //pointsFromMap.put(0, 0);
			 * //pointsFromMap.put(100, 100); pointsFromMap.put(0, 0);
			 * pointsFromMap.put(50, 60); setPointsFromMap(pointsFromMap);
			 */
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getJsonChartData()
	{
		try
		{

			/*
			 * Random generator = new Random(); doubleMap = new HashMap<Double,
			 * Double>(); achiveMap = new HashMap<Double, Double>();
			 * doubleMap.put(Double.valueOf("" + 1), generator.nextDouble() 10);
			 * achiveMap.put(Double.valueOf("" + 4), generator.nextDouble() 5);
			 */
			/*
			 * for (int i = 1; i <= 2; i++) { doubleMap.put(Double.valueOf("" + 1),
			 * generator.nextDouble() 10.0); }
			 */

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getDSRHistoryRecords()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			userdata = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String empId = (String) session.get("empIdofuser");// o-100000
			String tempempIdofuser[] = empId.split("-");
			empId = tempempIdofuser[1];
			String currentMonth = "";
			int month = DateUtil.getCurrentMonth();
			currentMonth = month + "-" + DateUtil.getCurretnYear();
			if (month < 10)
			{
				currentMonth = "0" + month + "-" + DateUtil.getCurretnYear();
			}
			String query = "select KPIId, targetvalue from target where empID=" + empId + " and targetMonth='" + currentMonth + "'";
			//System.out.println("query???????????????????????" + query);
			List kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
			DailyReportBean dailyReportBean = null;
			kpiList = new ArrayList<DailyReportBean>();
			String kpi = "";
			String targetValue = "";
			JSONObject jsonFilter = null;
			String searchString = "";
			String searchString2 = "";
			boolean searchRecord = false;
			kpiHistoryGridModel = new ArrayList<DailyReportBean>();
			//System.out.println(">>>>>>>>>>>Filter : " + filters);
			if (filters != null && !filters.equalsIgnoreCase(""))
			{
				jsonFilter = (JSONObject) JSONSerializer.toJSON(filters);
			}
			if (jsonFilter != null)
			{
				JSONArray rules = jsonFilter.getJSONArray("rules");
				int rulesCount = JSONArray.getDimensions(rules)[0];

				if (rulesCount == 1)
				{
					searchRecord = true;
					JSONObject rule = rules.getJSONObject(0);
					searchString = rule.getString("data");

					if (!searchString.equalsIgnoreCase(""))
					{
						query = "select kpi_id, kpi_target from dsrhistory where currentMonth ='" + searchString + "'";
						kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
					}
				}
				if (rulesCount > 1)
				{
					JSONObject rule = rules.getJSONObject(1);
					searchString2 = rule.getString("data");

					if (!searchString.equalsIgnoreCase(""))
					{
						query = "select kpi_id, kpi_target from dsrhistory where currentMonth between '" + searchString + "' and '" + searchString2 + "'";
						kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
					}
				}

			}
			if (kpiIds != null)
			{
				if (kpiIds.size() > 0)
				{
					if (!searchRecord)
					{
						for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							kpi = object[0].toString();
							targetValue = object[1].toString();
						}
					}
					else
					{
						for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							kpi = kpi + object[0].toString() + "#";
							targetValue = targetValue + object[1].toString() + "#";
						}
					}
					kpi = kpi.replaceAll("#", ",");
					kpi = kpi.substring(0, kpi.length() - 1);

					List kpiLists = cbt.executeAllSelectQuery("select  kpi from krakpicollection where id in (" + kpi + ")", connectionSpace);
					String target[] = targetValue.split("#");

					if (kpiLists != null)
					{
						List targetInfo = new ArrayList();
						int countFirst = 0;
						int countSecond = 0;
						int countThird = 0;
						int countFourth = 0;
						int total = 0;
						int totalTarget = 0;
						int totalAchived = 0;
						int remTarget = 0;
						String firstWeek[] = null;
						String secondWeek[] = null;
						String thirdWeek[] = null;
						String fourthWeek[] = null;
						String currMonth = "";
						String updatedDate = "";
						String kpiId[] = kpi.split(",");
						for (int i = 0; i < kpiLists.size(); i++)
						{

							query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
									+ " from dsrhistory where kpi_id=(select id from krakpicollection where kpi='" + kpiLists.get(i) + "') and currentMonth='" + currentMonth
									+ "' and user='" + userName + "'";
							//System.out.println(">>>>>>>>>>>>>>>>>test@@@@" + query);
							targetInfo = cbt.executeAllSelectQuery(query, connectionSpace);
							//System.out.println("targetInfo@@@@@@>>>>>>>>>>>>>>>>>>>>>" + targetInfo);
							if (targetInfo != null && targetInfo.size() > 0)
							{
								for (Iterator iterator = targetInfo.iterator(); iterator.hasNext();)
								{
									dailyReportBean = new DailyReportBean();
									dailyReportBean.setId(kpiId[i]);
									dailyReportBean.setKpiname(kpiLists.get(i).toString());
									dailyReportBean.setTargetvalue(target[i]);
									totalTarget += Integer.parseInt(target[i]);

									Object[] object = (Object[]) iterator.next();
									firstWeek = object[0].toString().split("#");
									secondWeek = object[1].toString().split("#");
									thirdWeek = object[2].toString().split("#");
									fourthWeek = object[3].toString().split("#");
									currMonth = object[4].toString();
									//System.out.println("currMonth@@@@@@>>>>>>>>>>>>>>>" + object[4].toString());
									for (int j = 0; j < firstWeek.length; j++)
									{
										countFirst += Integer.parseInt(firstWeek[j]);
									}

									for (int j = 0; j < firstWeek.length; j++)
									{
										countSecond += Integer.parseInt(secondWeek[j]);
									}

									for (int j = 0; j < firstWeek.length; j++)
									{
										countThird += Integer.parseInt(thirdWeek[j]);
									}

									for (int j = 0; j < firstWeek.length; j++)
									{
										countFourth += Integer.parseInt(fourthWeek[j]);
									}
									for (int j = 0; j < firstWeek.length; j++)
									{
										dailyReportBean.setOne(firstWeek[0]);
										dailyReportBean.setTwo(firstWeek[1]);
										dailyReportBean.setThree(firstWeek[2]);
										dailyReportBean.setFourth(firstWeek[3]);
										dailyReportBean.setFifth(firstWeek[4]);
										dailyReportBean.setSix(firstWeek[5]);
										dailyReportBean.setSeven(firstWeek[6]);
									}

									for (int j = 0; j < secondWeek.length; j++)
									{
										dailyReportBean.setEight(secondWeek[0]);
										dailyReportBean.setNine(secondWeek[1]);
										dailyReportBean.setTen(secondWeek[2]);
										dailyReportBean.setEleven(secondWeek[3]);
										dailyReportBean.setTwelve(secondWeek[4]);
										dailyReportBean.setThirteen(secondWeek[5]);
										dailyReportBean.setFourteen(secondWeek[6]);

									}

									for (int j = 0; j < thirdWeek.length; j++)
									{
										dailyReportBean.setFifteen(thirdWeek[0]);
										dailyReportBean.setSixteen(thirdWeek[1]);
										dailyReportBean.setSeventeen(thirdWeek[2]);
										dailyReportBean.setEighteen(thirdWeek[3]);
										dailyReportBean.setNineteen(thirdWeek[4]);
										dailyReportBean.setTwenty(thirdWeek[5]);
										dailyReportBean.setTwentyone(thirdWeek[6]);
									}

									for (int j = 0; j < fourthWeek.length; j++)
									{
										dailyReportBean.setTwentytwo(fourthWeek[0]);
										dailyReportBean.setTwentythree(fourthWeek[1]);
										dailyReportBean.setTwentyfourth(fourthWeek[2]);
										dailyReportBean.setTwentyfifth(fourthWeek[3]);
										dailyReportBean.setTwentysix(fourthWeek[4]);
										dailyReportBean.setTwentyseven(fourthWeek[5]);
										dailyReportBean.setTwentyeight(fourthWeek[6]);

										try
										{
											if (fourthWeek[7] != null)
											{
												dailyReportBean.setTwentynine(fourthWeek[7]);
											}
										}
										catch (Exception e)
										{
											e.printStackTrace();
										}

										try
										{
											if (fourthWeek[8] != null)
											{
												dailyReportBean.setThirty(fourthWeek[8]);
											}
										}
										catch (Exception e)
										{
											e.printStackTrace();
										}

										/*
										 * try { if (fourthWeek[9] != null) { dailyReportBean
										 * .setThirtyone(fourthWeek[9]); } } catch(Exception e) {
										 * e.printStackTrace(); }
										 */
									}
									dailyReportBean.setCurrentmonth(currMonth);
									total = countFirst + countSecond + countThird + countFourth;
									dailyReportBean.setTotalsale(String.valueOf(total));

									dailyReportBean.setRemainng_target(String.valueOf((Integer.parseInt(target[i]) - total) > 0 ? (Integer.parseInt(target[i]) - total) : 0));
									remTarget += (Integer.parseInt(target[i]) - total);
									kpiList.add(dailyReportBean);
								}
							}

							totalAchived += total;
							countFirst = 0;
							countSecond = 0;
							countThird = 0;
							countFourth = 0;
						}

						//System.out.println(">>>>>>>>>>>remTarget : " + remTarget);
						userdata.put("remainng_target", remTarget);
						userdata.put("targetvalue", totalTarget);
						userdata.put("totalsale", totalAchived);
						userdata.put("kpiname", "Total :");
						// setKpiGridModelList(kpiList);
						setKpiHistoryGridModel(kpiList);
					}
				}
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <getDSRRecords> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeIVRSHistoryView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			setMainHeader("DSR History");
			setGridHeader();
			getDaysByWeek();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Get details of IVRS data for an mobile number of fa month
	 */
	public String ivrsRecordDetailsData()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{

				//System.out.println("Mobile No:id:" + id);
				List report = new ReportDao().getIVRSDetailData(DateUtil.getCurrentDateYearMonth(), id, connectionSpace);

				ivrsDataDetails = new ArrayList<IVRSDataBean>();
				if (report != null && report.size() > 0)
				{
					for (Iterator iterator = report.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();

						IVRSDataBean dataBean = new IVRSDataBean();

						if (object[0].toString().equalsIgnoreCase("0"))
						{
							dataBean.setAvailability("Not Working");
						}
						else if (object[0].toString().equalsIgnoreCase("1"))
						{
							dataBean.setAvailability("Working");
						}
						else
						{
							dataBean.setAvailability("Not Reachable");
						}

						dataBean.setTotalCall(String.valueOf((int) Double.parseDouble(object[1].toString())));
						dataBean.setProductiveCall(String.valueOf((int) Double.parseDouble(object[2].toString())));
						dataBean.setTotalSale(String.valueOf((int) Double.parseDouble(object[3].toString())));
						dataBean.setNewOutlet(String.valueOf((int) Double.parseDouble(object[4].toString())));
						dataBean.setTalkTime(String.valueOf((int) Double.parseDouble(object[5].toString())));
						dataBean.setPulse(String.valueOf((int) Double.parseDouble(object[6].toString())));
						dataBean.setDate(DateUtil.convertDateToIndianFormat(object[7].toString()));
						dataBean.setTime(object[8].toString());

						ivrsDataDetails.add(dataBean);
					}
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			returnValue = SUCCESS;
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;

	}

	public String getMainHeader()
	{
		return mainHeader;
	}

	public void setMainHeader(String mainHeader)
	{
		this.mainHeader = mainHeader;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public List<DailyReportBean> getKpiList()
	{
		return kpiList;
	}

	public void setKpiList(List<DailyReportBean> kpiList)
	{
		this.kpiList = kpiList;
	}

	public List<DailyReportBean> getKpiGridModelList()
	{
		return kpiGridModelList;
	}

	public void setKpiGridModelList(List<DailyReportBean> kpiGridModelList)
	{
		this.kpiGridModelList = kpiGridModelList;
	}

	public List<DailyReportBean> getListfistweekdays()
	{
		return listfistweekdays;
	}

	public void setListfistweekdays(List<DailyReportBean> listfistweekdays)
	{
		this.listfistweekdays = listfistweekdays;
	}

	public List<DailyReportBean> getListSecondweekdays()
	{
		return listSecondweekdays;
	}

	public void setListSecondweekdays(List<DailyReportBean> listSecondweekdays)
	{
		this.listSecondweekdays = listSecondweekdays;
	}

	public List<DailyReportBean> getListThirdweekdays()
	{
		return listThirdweekdays;
	}

	public void setListThirdweekdays(List<DailyReportBean> listThirdweekdays)
	{
		this.listThirdweekdays = listThirdweekdays;
	}

	public List<DailyReportBean> getListFourthweekdays()
	{
		return listFourthweekdays;
	}

	public void setListFourthweekdays(List<DailyReportBean> listFourthweekdays)
	{
		this.listFourthweekdays = listFourthweekdays;
	}

	public Map<String, Object> getUserdata()
	{
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata)
	{
		this.userdata = userdata;
	}

	public String getKpiId()
	{
		return kpiId;
	}

	public void setKpiId(String kpiId)
	{
		this.kpiId = kpiId;
	}

	public List<FullAchievmentReport> getFullAchievmentList()
	{
		return fullAchievmentList;
	}

	public void setFullAchievmentList(List<FullAchievmentReport> fullAchievmentList)
	{
		this.fullAchievmentList = fullAchievmentList;
	}

	public List<String> getCurremonth()
	{
		return curremonth;
	}

	public void setCurremonth(List<String> curremonth)
	{
		this.curremonth = curremonth;
	}

	public String getCurrentmonth()
	{
		return currentmonth;
	}

	public void setCurrentmonth(String currentmonth)
	{
		this.currentmonth = currentmonth;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public String getFilters()
	{
		return filters;
	}

	public void setFilters(String filters)
	{
		this.filters = filters;
	}

	public Map<String, String> getEmpInfo()
	{
		return empInfo;
	}

	public void setEmpInfo(Map<String, String> empInfo)
	{
		this.empInfo = empInfo;
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

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public List<Temp> getKpiGridModel()
	{
		return kpiGridModel;
	}

	public void setKpiGridModel(List<Temp> kpiGridModel)
	{
		this.kpiGridModel = kpiGridModel;
	}

	public List<Temp> getEmpDetail()
	{
		return empDetail;
	}

	public void setEmpDetail(List<Temp> empDetail)
	{
		this.empDetail = empDetail;
	}

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		this.list = list;
	}

	public List<DailyReportBean> getGridModelList()
	{
		return gridModelList;
	}

	public void setGridModelList(List<DailyReportBean> gridModelList)
	{
		this.gridModelList = gridModelList;
	}

	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<IVRSDataBean> getIvrsData()
	{
		return ivrsData;
	}

	public void setIvrsData(List<IVRSDataBean> ivrsData)
	{
		this.ivrsData = ivrsData;
	}

	public HashMap<Double, Double> getDoubleMap()
	{
		return doubleMap;
	}

	public void setDoubleMap(HashMap<Double, Double> doubleMap)
	{
		this.doubleMap = doubleMap;
	}

	public HashMap<Double, Double> getAchiveMap()
	{
		return achiveMap;
	}

	public void setAchiveMap(HashMap<Double, Double> achiveMap)
	{
		this.achiveMap = achiveMap;
	}

	public List<DailyReportBean> getKpiHistoryGridModel()
	{
		return kpiHistoryGridModel;
	}

	public void setKpiHistoryGridModel(List<DailyReportBean> kpiHistoryGridModel)
	{
		this.kpiHistoryGridModel = kpiHistoryGridModel;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public List<GridDataPropertyView> getViewIVRS()
	{
		return viewIVRS;
	}

	public void setViewIVRS(List<GridDataPropertyView> viewIVRS)
	{
		this.viewIVRS = viewIVRS;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public List<IVRSDataBean> getIvrsDataDetails()
	{
		return ivrsDataDetails;
	}

	public void setIvrsDataDetails(List<IVRSDataBean> ivrsDataDetails)
	{
		this.ivrsDataDetails = ivrsDataDetails;
	}

	public String getUserN()
	{
		return userN;
	}

	public void setUserN(String userN)
	{
		this.userN = userN;
	}

}
