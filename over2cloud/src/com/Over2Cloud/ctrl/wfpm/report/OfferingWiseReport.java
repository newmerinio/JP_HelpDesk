package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OfferingWiseReport extends ActionSupport implements ServletRequestAware
{
	static final Log							log							= LogFactory.getLog(OfferingWiseReport.class);
	private Map										session					= ActionContext.getContext().getSession();
	private String								userName				= (String) session.get("uName");
	private String								accountID				= (String) session.get("accountid");
	private SessionFactory				connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private String								mainHeader;
	private String								month;
	private List<DailyReportBean>	listfistweekdays;
	private List<DailyReportBean>	listSecondweekdays;
	private List<DailyReportBean>	listThirdweekdays;
	private List<DailyReportBean>	listFourthweekdays;
	private Map<String, Object>		userdata;
	private List<DailyReportBean>	offeringList;
	private List<DailyReportBean>	offeringGridModelList;
	private String								filters;
	private int										id;
	private String								oper;
	private HttpServletRequest		request;
	private String								offeringLevel		= session.get("offeringLevel").toString();
	private String								userID;
	private String								mode;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			setMainHeader("Offering wise DAR >> View");
			setGridHeader();
			getDaysByWeek();
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

	public String getOfferingDSRRecords()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			int level = 0;
			String[] oLevels = null;
			String tableName = "", colName = "";
			CommonOperInterface coi = new CommonConFactory().createInterface();

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			// String tableName = "", colName = "";
			tableName = "";
			colName = "";

			if (level == 1)
			{
				tableName = "offeringlevel1";
				colName = "verticalname";
			}
			if (level == 2)
			{
				tableName = "offeringlevel2";
				colName = "offeringname";
			}
			if (level == 3)
			{
				tableName = "offeringlevel3";
				colName = "subofferingname";
			}
			if (level == 4)
			{
				tableName = "offeringlevel4";
				colName = "variantname";
			}
			if (level == 5)
			{
				tableName = "offeringlevel5";
				colName = "subvariantname";
			}

			userdata = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// String empId = (String)session.get("empIdofuser");//o-100000
			//System.out.println("getUserID():" + getUserID());
			//System.out.println("getMode():" + getMode());

			if (getUserID() == null || getUserID().equalsIgnoreCase("")) return ERROR;

			String empId = (String)session.get("empIdofuser").toString().split("-")[1];
			//System.out.println("empId:" + empId);

			if (empId == null) return ERROR;

			// String tempempIdofuser[]=empId.split("-");
			// empId=tempempIdofuser[1];

			int month = DateUtil.getCurrentMonth();
			String currentMonth = month + "-" + DateUtil.getCurretnYear();
			Map<String, Object> session = ActionContext.getContext().getSession();
			// String s[] = session.get("offeringLevel").toString().split("#");
			// int offeringLavel = Integer.parseInt(s[0]);
			if (month < 10)
			{
				currentMonth = "0" + month + "-" + DateUtil.getCurretnYear();
			}
			String query = "select offeringId, targetvalue from offeringtarget where empID=" + empId + " and targetMonth='" + currentMonth + "'";

			List kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
			DailyReportBean dailyReportBean = null;
			offeringList = new ArrayList<DailyReportBean>();
			String kpi = "";
			String targetValue = "";
			JSONObject jsonFilter = null;
			String searchString = "";
			String searchString2 = "";
			boolean searchRecord = false;
			if (filters != null)
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
						// query = "select kpi_id from dailyreport where currentMonth='"+
						// searchString +"'";
						query = "select kpi_id, kpi_target from offeringdailyreport where currentMonth ='" + searchString + "'";
						kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
					}
				}
				if (rulesCount > 1)
				{
					JSONObject rule = rules.getJSONObject(1);
					searchString2 = rule.getString("data");

					if (!searchString.equalsIgnoreCase(""))
					{
						query = "select kpi_id, kpi_target from offeringdailyreport where currentMonth between '" + searchString + "' and '" + searchString2 + "'";
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
					query = "select " + colName + " from " + tableName + " where id in (" + kpi + ")";
					List kpiLists = cbt.executeAllSelectQuery(query, connectionSpace);
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
						for (int i = 0; i < kpiLists.size(); i++)
						{
							dailyReportBean = new DailyReportBean();
							dailyReportBean.setId(kpiId[i]);
							dailyReportBean.setKpiname(kpiLists.get(i).toString());
							dailyReportBean.setTargetvalue(target[i]);
							totalTarget += Integer.parseInt(target[i]);

							if (!getMode().equalsIgnoreCase("BOTH"))
							{
								query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
										+ " from offeringdailyreport where kpi_id in (select id from " + tableName + " where id='" + kpiId[i] + "') and currentMonth='"
										+ currentMonth + "' and user='" + userName + "' and mode='" + getMode() + "'";
							}
							else
							{
								query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
										+ " from offeringdailyreport where kpi_id in (select id from " + tableName + " where id='" + kpiId[i] + "') and currentMonth='"
										+ currentMonth + "' and user='" + userName + "'";
							}

							targetInfo = cbt.executeAllSelectQuery(query, connectionSpace);

							if (targetInfo != null && targetInfo.size() > 0)
							{
								for (Iterator iterator = targetInfo.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									firstWeek = object[0].toString().split("#");
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
											if (fourthWeek[7] != null)
											{
												dailyReportBean.setTwentynine(fourthWeek[7]);
											}
										}
										catch (Exception e)
										{

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

										}

										try
										{
											if (fourthWeek[9] != null)
											{
												dailyReportBean.setThirtyone(fourthWeek[9]);
											}
										}
										catch (Exception e)
										{

										}
									}
									dailyReportBean.setCurrentmonth(currMonth);
								}
							}
							total = countFirst + countSecond + countThird + countFourth;
							dailyReportBean.setTotalsale(String.valueOf(total));
							dailyReportBean.setRemainng_target(String.valueOf(Integer.parseInt(target[i]) - total));
							remTarget += (Integer.parseInt(target[i]) - total);
							offeringList.add(dailyReportBean);

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
						setOfferingGridModelList(offeringList);
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

	public String updateOfferingDSRRecords()
	{
		if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

		try
		{
			if (getOper().equalsIgnoreCase("edit"))
			{
				//System.out.println("updateOfferingDSRRecords>>>>>>>>>>>");
				DSRgerneration rgerneration = new DSRgerneration();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				String empId[] = session.get("empIdofuser").toString().split("-");
				rgerneration.setManualDSRRecords(getId(), empId[1], DSRMode.OFFERING, requestParameterNames, request, DSRType.ONLINE);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
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

	public List<DailyReportBean> getOfferingList()
	{
		return offeringList;
	}

	public void setOfferingList(List<DailyReportBean> offeringList)
	{
		this.offeringList = offeringList;
	}

	public List<DailyReportBean> getOfferingGridModelList()
	{
		return offeringGridModelList;
	}

	public void setOfferingGridModelList(List<DailyReportBean> offeringGridModelList)
	{
		this.offeringGridModelList = offeringGridModelList;
	}

	public String getFilters()
	{
		return filters;
	}

	public void setFilters(String filters)
	{
		this.filters = filters;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
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

}
