package com.Over2Cloud.ctrl.wfpm.report;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class KPIAutoFillReportAction extends ActionSupport
{
	@SuppressWarnings("rawtypes")
	Map														session					= ActionContext.getContext().getSession();
	String												empId						= session.get("empIdofuser").toString().split("-")[1];
	SessionFactory								connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private String								mainHeader;
	private String								month;
	private String								currentMonthValue;
	private List<DailyReportBean>	listfistweekdays;
	private List<DailyReportBean>	listSecondweekdays;
	private List<DailyReportBean>	listThirdweekdays;
	private List<DailyReportBean>	listFourthweekdays;
	private List<DailyReportBean>	kpiGridModelList;
	private List<DailyReportBean>	gridDataModelList;
	private Map<String, Object>		userdata;
	private Map<String, String>		clientMap;
	private Integer								rows						= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer								page						= 0;
	// sorting order - asc or desc
	private String								sord						= "";
	// get index row - i.e. user click to sort.
	private String								sidx						= "";
	// Search Field
	private String								searchField			= "";
	// The Search String
	private String								searchString		= "";
	private String								searchOper;
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	// Your Total Pages
	private Integer								total						= 0;
	// All Record
	private Integer								records					= 0;
	private boolean								loadonce				= false;
	// Grid colomn view
	private String								oper;
	private JSONArray							jsonArrayKPI;
	private String								kpiId;
	private Map<String, String>		mapKpi;
	private String								dateValue;
	private String								clientId;

	public String execute()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			if (currentMonthValue == null || currentMonthValue.equals(""))
			{
				currentMonthValue = DateUtil.getCurrentDateUSFormat();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf1.parse(currentMonthValue);
				SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
				currentMonthValue = sdf.format(date);
			}

			//System.out.println("execute():" + currentMonthValue);
			// setMainHeader("KPI Report >> View");
			// setGridHeader();
			getDaysByWeek();
			// getWeekList();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String setGridHeader()
	{
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		Calendar cal = Calendar.getInstance();
		Integer Year = new Integer(cal.get(Calendar.YEAR));
		month = months[cal.get(Calendar.MONTH)] + "-" + Year;
		if (currentMonthValue != null && !currentMonthValue.equalsIgnoreCase(""))
		{
			month = months[Integer.parseInt(currentMonthValue.split("-")[1]) - 1] + "-" + currentMonthValue.split("-")[2];
		}
		return month;
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
				drb.setFormatterName("gridData" + new Integer(i + 1).toString());
				listfistweekdays.add(drb);
			}
			else if (i > 6 && i < 14)
			{
				drb.setSecondweekdays(new Integer(i + 1).toString() + "th");
				drb.setSecondweekdatename(arryaValue[i].toString());
				drb.setFormatterName("gridData" + new Integer(i + 1).toString());
				listSecondweekdays.add(drb);
			}
			else if (i > 13 && i < 21)
			{
				drb.setThirdtweekdays(new Integer(i + 1).toString() + "th");
				drb.setThirdweekdatename(arryaValue[i].toString());
				drb.setFormatterName("gridData" + new Integer(i + 1).toString());
				listThirdweekdays.add(drb);
			}
			else
			{
				drb.setFourthweekdays(new Integer(i + 1).toString() + "th");
				drb.setFourthweekdatename(arryaValue[i].toString());
				drb.setFormatterName("gridData" + new Integer(i + 1).toString());
				listFourthweekdays.add(drb);
			}
		}
	}

	public String getKPIRecords()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			userdata = new HashMap<String, Object>();
			//System.out.println("111111111111:" + currentMonthValue);

			List<DailyReportBean> kpiList = getDataKpi(empId, currentMonthValue.split("-")[0] + "-" + currentMonthValue.split("-")[1]);
			setKpiGridModelList(kpiList);
			setRecords(kpiList.size());
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));

			if (kpiList != null && kpiList.size() > 0)
			{
				DecimalFormat df2 = new DecimalFormat("###.##");
				int tTarget = 0, tSales = 0, remain = 0, weightage = 0;
				double perAcheive = 0;
				for (DailyReportBean dpb : kpiList)
				{
					tTarget = tTarget + Integer.parseInt(dpb.getTargetvalue());
					tSales = tSales + Integer.parseInt(dpb.getTotalsale());
					remain = remain + Integer.parseInt(dpb.getRemainng_target());
					perAcheive = perAcheive + dpb.getPerAcheive();
					weightage = weightage + Integer.parseInt(dpb.getWeightage());
				}
				userdata.put("kpiname", "Total : ");
				userdata.put("targetvalue", tTarget);
				userdata.put("totalsale", tSales);
				userdata.put("remainng_target", remain);
				userdata.put("perAcheive", df2.format(perAcheive));
				userdata.put("weightage", weightage);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" })
	private List<DailyReportBean> getDataKpi(String empId, String month)
	{
		List<DailyReportBean> kpiList = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			KPIReportHelper krh = new KPIReportHelper();
			String targetMonth = krh.fetchLastTargetMonthByEmpId(empId, month, connectionSpace, TargetType.KPI);
			StringBuilder query = new StringBuilder();
			query
					.append("SELECT kpiId,weightage,targetValue,kpiM.kpi FROM target_for_kpi as tfk INNER JOIN krakpicollection AS kpiM ON tfk.kpiId=kpiM.id  WHERE empId='");
			query.append(empId);
			query.append("' AND applicableFrom IN ('");
			query.append(targetMonth);
			query.append("') ");

			List kpiIds = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			DailyReportBean dailyReportBean = null;
			DecimalFormat df2 = null;
			kpiList = new ArrayList<DailyReportBean>();
			Object[] object = null;
			if (kpiIds != null && kpiIds.size() > 0)
			{
				for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
				{
					dailyReportBean = new DailyReportBean();
					object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						dailyReportBean.setId(CommonHelper.getFieldValue(object[0]));
						dailyReportBean.setWeightage(CommonHelper.getFieldValue(object[1]));
						dailyReportBean.setTargetvalue(CommonHelper.getFieldValue(object[2]));
						dailyReportBean.setKpiname(CommonHelper.getFieldValue(object[3]));

						query = new StringBuilder();
						query.append("SELECT COUNT(*) FROM kpi_autofill AS kpi   WHERE empId='");
						query.append(empId);
						query.append("' AND kpi.date LIKE '");
						query.append(month);
						query.append("%' AND kpi.kpiId='");
						query.append(object[0].toString());
						query.append("' ");

						// //System.out.println("TARGET QUERY IS AS :::  "+query.toString());
						List targetList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (targetList != null && targetList.size() > 0)
						{
							for (Iterator iterator3 = targetList.iterator(); iterator3.hasNext();)
							{
								Object object1 = (Object) iterator3.next();
								dailyReportBean.setTotalsale(CommonHelper.getFieldValue(object1));
								dailyReportBean.setRemainng_target(String.valueOf(Integer.parseInt(object[2].toString()) - Integer.parseInt(object1.toString())));
								df2 = new DecimalFormat("###.##");
								dailyReportBean.setPerAcheive(Double.parseDouble(df2.format((Double.parseDouble(object1.toString()) / Double.parseDouble(object[2].toString()))
										* (Double.parseDouble(object[1].toString()) / 100) * 100)));

								query = new StringBuilder();
								query.append("SELECT SUBSTRING(date,-2),COUNT(kpiId) FROM kpi_autofill WHERE empId='");
								query.append(empId);
								query.append("' AND kpiId = '");
								query.append(object[0].toString());
								query.append("' AND date LIKE '");
								query.append(month);
								query.append("%' GROUP BY date");

								// //System.out.println("QUERY 222222222   "+query);
								List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if (data != null && data.size() > 0)
								{
									for (Iterator iterator2 = data.iterator(); iterator2.hasNext();)
									{
										object = (Object[]) iterator2.next();
										switch (Integer.parseInt(object[0].toString()))
										{
										case 1:
											dailyReportBean.setOne(CommonHelper.getFieldValue(object[1]));
											break;
										case 2:
											dailyReportBean.setTwo(CommonHelper.getFieldValue(object[1]));
											break;
										case 3:
											dailyReportBean.setThree(CommonHelper.getFieldValue(object[1]));
											break;
										case 4:
											dailyReportBean.setFourth(CommonHelper.getFieldValue(object[1]));
											break;
										case 5:
											dailyReportBean.setFifth(CommonHelper.getFieldValue(object[1]));
											break;
										case 6:
											dailyReportBean.setSix(CommonHelper.getFieldValue(object[1]));
											break;
										case 7:
											dailyReportBean.setSeven(CommonHelper.getFieldValue(object[1]));
											break;
										case 8:
											dailyReportBean.setEight(CommonHelper.getFieldValue(object[1]));
											break;
										case 9:
											dailyReportBean.setNine(CommonHelper.getFieldValue(object[1]));
											break;
										case 10:
											dailyReportBean.setTen(CommonHelper.getFieldValue(object[1]));
											break;
										case 11:
											dailyReportBean.setEleven(CommonHelper.getFieldValue(object[1]));
											break;
										case 12:
											dailyReportBean.setTwelve(CommonHelper.getFieldValue(object[1]));
											break;
										case 13:
											dailyReportBean.setThirteen(CommonHelper.getFieldValue(object[1]));
											break;
										case 14:
											dailyReportBean.setFourteen(CommonHelper.getFieldValue(object[1]));
											break;
										case 15:
											dailyReportBean.setFifteen(CommonHelper.getFieldValue(object[1]));
											break;
										case 16:
											dailyReportBean.setSixteen(CommonHelper.getFieldValue(object[1]));
											break;
										case 17:
											dailyReportBean.setSeventeen(CommonHelper.getFieldValue(object[1]));
											break;
										case 18:
											dailyReportBean.setEighteen(CommonHelper.getFieldValue(object[1]));
											break;
										case 19:
											dailyReportBean.setNineteen(CommonHelper.getFieldValue(object[1]));
											break;
										case 20:
											dailyReportBean.setTwenty(CommonHelper.getFieldValue(object[1]));
											break;
										case 21:
											dailyReportBean.setTwentyone(CommonHelper.getFieldValue(object[1]));
											break;
										case 22:
											dailyReportBean.setTwentytwo(CommonHelper.getFieldValue(object[1]));
											break;
										case 23:
											dailyReportBean.setTwentythree(CommonHelper.getFieldValue(object[1]));
											break;
										case 24:
											dailyReportBean.setTwentyfourth(CommonHelper.getFieldValue(object[1]));
											break;
										case 25:
											dailyReportBean.setTwentyfifth(CommonHelper.getFieldValue(object[1]));
											break;
										case 26:
											dailyReportBean.setTwentysix(CommonHelper.getFieldValue(object[1]));
											break;
										case 27:
											dailyReportBean.setTwentyseven(CommonHelper.getFieldValue(object[1]));
											break;
										case 28:
											dailyReportBean.setTwentyeight(CommonHelper.getFieldValue(object[1]));
											break;
										case 29:
											dailyReportBean.setTwentynine(CommonHelper.getFieldValue(object[1]));
											break;
										case 30:
											dailyReportBean.setThirty(CommonHelper.getFieldValue(object[1]));
											break;
										case 31:
											dailyReportBean.setThirtyone(CommonHelper.getFieldValue(object[1]));
											break;
										default:
											break;
										}
									}
								}
								dailyReportBean.setCurrentmonth(month.split("-")[1] + "-" + month.split("-")[0]);
								kpiList.add(dailyReportBean);
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return kpiList;
	}

	public String showBarChartKpiFill()
	{
		try
		{
			List<DailyReportBean> finalDataList3 = new ArrayList<DailyReportBean>();
			finalDataList3 = getDataKpi(empId, currentMonthValue.split("-")[0] + "-" + currentMonthValue.split("-")[1]);
			JSONObject jsonObjectKPI;
			jsonArrayKPI = new JSONArray();
			if (finalDataList3 != null && finalDataList3.size() > 0)
			{
				jsonObjectKPI = new JSONObject();
				for (DailyReportBean pojo : finalDataList3)
				{
					jsonObjectKPI.put("KPI", pojo.getKpiname());
					jsonObjectKPI.put("totalTarget", pojo.getTargetvalue());
					jsonObjectKPI.put("acheivement", pojo.getTotalsale());
					jsonArrayKPI.add(jsonObjectKPI);
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

	@SuppressWarnings("rawtypes")
	public String beforeKPIGraph()
	{
		boolean validateSession = ValidateSession.checkSession();
		if (validateSession)
		{
			try
			{
				String date = DateUtil.getCurrentDateUSFormat();
				mapKpi = new KPIReportHelper().fetchAchievedKPIForMonth(empId, date, connectionSpace);

				// setGridHeader();
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

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public String kpiAutofillGraphPerKpiShow()
	{
		try
		{
			List<DailyReportBean> finalDataList3 = new ArrayList<DailyReportBean>();

			finalDataList3 = getDataPerKpi(empId, currentMonthValue.split("-")[0] + "-" + currentMonthValue.split("-")[1], kpiId);
			JSONObject jsonObjectKPI;
			jsonArrayKPI = new JSONArray();
			if (finalDataList3 != null && finalDataList3.size() > 0)
			{
				List t = null;
				int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0, eight = 0, nine = 0, ten = 0, eleven = 0, tweleve = 0, thirteen = 0, fourteen = 0, fifteen = 0, sixteen = 0, seventeen = 0, eighteen = 0, nineteen = 0, twenty = 0, twentyone = 0, twentytwo = 0, twentythree = 0, twentyfour = 0, twnetyfive = 0, twentysix = 0, twentyseven = 0, twentyeight = 0, twentynine = 0, thirty = 0, thirtyone = 0;
				t = new ArrayList<String>();
				for (DailyReportBean pojo : finalDataList3)
				{
					if (pojo.getOne().equalsIgnoreCase("0"))
					{
						one = 0;
					}
					else
					{
						one = Integer.parseInt(pojo.getOne());
					}
					if (pojo.getTwo().equalsIgnoreCase("0"))
					{
						two = 0;
					}
					else
					{
						two = Integer.parseInt(pojo.getTwo());
					}
					if (pojo.getThree().equalsIgnoreCase("0"))
					{

					}
					else
					{
						three = Integer.parseInt(pojo.getThree());
					}
					if (pojo.getFourth().equalsIgnoreCase("0"))
					{

					}
					else
					{
						four = Integer.parseInt(pojo.getFourth());
					}
					if (pojo.getFifth().equalsIgnoreCase("0"))
					{

					}
					else
					{
						five = Integer.parseInt(pojo.getFifth());
					}
					if (pojo.getSix().equalsIgnoreCase("0"))
					{

					}
					else
					{
						six = Integer.parseInt(pojo.getSix());
					}
					if (pojo.getSeven().equalsIgnoreCase("0"))
					{

					}
					else
					{
						seven = Integer.parseInt(pojo.getSeven());
					}
					if (pojo.getEight().equalsIgnoreCase("0"))
					{

					}
					else
					{
						eight = Integer.parseInt(pojo.getEight());
					}
					if (pojo.getNine().equalsIgnoreCase("0"))
					{

					}
					else
					{
						nine = Integer.parseInt(pojo.getNine());
					}
					if (pojo.getTen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						ten = Integer.parseInt(pojo.getTen());
					}
					if (pojo.getEleven().equalsIgnoreCase("0"))
					{

					}
					else
					{
						eleven = Integer.parseInt(pojo.getEleven());
					}
					if (pojo.getTwelve().equalsIgnoreCase("0"))
					{

					}
					else
					{
						tweleve = Integer.parseInt(pojo.getTwelve());
					}
					if (pojo.getThirteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						thirteen = Integer.parseInt(pojo.getThirteen());
					}
					if (pojo.getFourteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						fourteen = Integer.parseInt(pojo.getFourteen());
					}
					if (pojo.getFifteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						fifteen = Integer.parseInt(pojo.getFifteen());
					}
					if (pojo.getSixteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						sixteen = Integer.parseInt(pojo.getSixteen());
					}
					if (pojo.getSeventeen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						seventeen = Integer.parseInt(pojo.getSeventeen());
					}
					if (pojo.getEighteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						eighteen = Integer.parseInt(pojo.getEighteen());
					}
					if (pojo.getNineteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						nineteen = Integer.parseInt(pojo.getNineteen());
					}
					if (pojo.getTwenty().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twenty = Integer.parseInt(pojo.getTwenty());
					}
					if (pojo.getTwentyone().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyone = Integer.parseInt(pojo.getTwentyone());
					}
					if (pojo.getTwentytwo().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentytwo = Integer.parseInt(pojo.getTwentytwo());
					}
					if (pojo.getTwentythree().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentythree = Integer.parseInt(pojo.getTwentythree());
					}
					if (pojo.getTwentyfourth().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyfour = Integer.parseInt(pojo.getTwentyfourth());
					}
					if (pojo.getTwentyfifth().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twnetyfive = Integer.parseInt(pojo.getTwentyfifth());
					}
					if (pojo.getTwentysix().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentysix = Integer.parseInt(pojo.getTwentysix());
					}
					if (pojo.getTwentyseven().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyseven = Integer.parseInt(pojo.getTwentyseven());
					}
					if (pojo.getTwentyeight().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyeight = Integer.parseInt(pojo.getTwentyeight());
					}
					if (pojo.getTwentynine().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentynine = Integer.parseInt(pojo.getTwentynine());
					}
					if (pojo.getThirty().equalsIgnoreCase("0"))
					{

					}
					else
					{
						thirty = Integer.parseInt(pojo.getThirty());
					}
					if (pojo.getThirtyone().equalsIgnoreCase("0"))
					{

					}
					else
					{
						thirtyone = Integer.parseInt(pojo.getThirtyone());
					}
				}
				t.add(one);
				t.add(two);
				t.add(three);
				t.add(four);
				t.add(five);
				t.add(six);
				t.add(seven);
				t.add(eight);
				t.add(nine);
				t.add(ten);
				t.add(eleven);
				t.add(tweleve);
				t.add(thirteen);
				t.add(fourteen);
				t.add(fifteen);
				t.add(sixteen);
				t.add(seventeen);
				t.add(eighteen);
				t.add(nineteen);
				t.add(twenty);
				t.add(twentyone);
				t.add(twentytwo);
				t.add(twentythree);
				t.add(twentyfour);
				t.add(twnetyfive);
				t.add(twentysix);
				t.add(twentyseven);
				t.add(twentyeight);
				t.add(twentynine);
				t.add(thirty);
				t.add(thirtyone);
				t.toArray();
				for (int i = 1; i <= 31; i++)
				{
					jsonObjectKPI = new JSONObject();
					jsonObjectKPI.put("day", i);
					jsonObjectKPI.put("count", t.get(i - 1));
					jsonArrayKPI.add(jsonObjectKPI);
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

	@SuppressWarnings("rawtypes")
	private List<DailyReportBean> getDataPerKpi(String empId, String month, String kpiId)
	{
		List<DailyReportBean> kpiList = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			DailyReportBean dailyReportBean = null;
			kpiList = new ArrayList<DailyReportBean>();
			Object[] object = null;
			String query = "SELECT SUBSTRING(date,-2),COUNT(kpiId) FROM kpi_autofill WHERE empId='" + empId + "' AND kpiId = '" + kpiId + "' AND date LIKE '" + month
					+ "%' GROUP BY date";
			// //System.out.println("QUERY 222222222   "+query);
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator2 = data.iterator(); iterator2.hasNext();)
				{
					dailyReportBean = new DailyReportBean();
					object = (Object[]) iterator2.next();
					switch (Integer.parseInt(object[0].toString()))
					{
					case 1:
						dailyReportBean.setOne(object[1].toString());
						break;
					case 2:
						dailyReportBean.setTwo(object[1].toString());
						break;
					case 3:
						dailyReportBean.setThree(object[1].toString());
						break;
					case 4:
						dailyReportBean.setFourth(object[1].toString());
						break;
					case 5:
						dailyReportBean.setFifth(object[1].toString());
						break;
					case 6:
						dailyReportBean.setSix(object[1].toString());
						break;
					case 7:
						dailyReportBean.setSeven(object[1].toString());
						break;
					case 8:
						dailyReportBean.setEight(object[1].toString());
						break;
					case 9:
						dailyReportBean.setNine(object[1].toString());
						break;
					case 10:
						dailyReportBean.setTen(object[1].toString());
						break;
					case 11:
						dailyReportBean.setEleven(object[1].toString());
						break;
					case 12:
						dailyReportBean.setTwelve(object[1].toString());
						break;
					case 13:
						dailyReportBean.setThirteen(object[1].toString());
						break;
					case 14:
						dailyReportBean.setFourteen(object[1].toString());
						break;
					case 15:
						dailyReportBean.setFifteen(object[1].toString());
						break;
					case 16:
						dailyReportBean.setSixteen(object[1].toString());
						break;
					case 17:
						dailyReportBean.setSeventeen(object[1].toString());
						break;
					case 18:
						dailyReportBean.setEighteen(object[1].toString());
						break;
					case 19:
						dailyReportBean.setNineteen(object[1].toString());
						break;
					case 20:
						dailyReportBean.setTwenty(object[1].toString());
						break;
					case 21:
						dailyReportBean.setTwentyone(object[1].toString());
						break;
					case 22:
						dailyReportBean.setTwentytwo(object[1].toString());
						break;
					case 23:
						dailyReportBean.setTwentythree(object[1].toString());
						break;
					case 24:
						dailyReportBean.setTwentyfourth(object[1].toString());
						break;
					case 25:
						dailyReportBean.setTwentyfifth(object[1].toString());
						break;
					case 26:
						dailyReportBean.setTwentysix(object[1].toString());
						break;
					case 27:
						dailyReportBean.setTwentyseven(object[1].toString());
						break;
					case 28:
						dailyReportBean.setTwentyeight(object[1].toString());
						break;
					case 29:
						dailyReportBean.setTwentynine(object[1].toString());
						break;
					case 30:
						dailyReportBean.setThirty(object[1].toString());
						break;
					case 31:
						dailyReportBean.setThirtyone(object[1].toString());
						break;
					default:
						break;
					}
					kpiList.add(dailyReportBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return kpiList;
	}

	@SuppressWarnings("rawtypes")
	public String beforeKPIDataView()
	{
		boolean validateSession = ValidateSession.checkSession();
		if (validateSession)
		{
			try
			{
				clientMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				String currentDate = currentMonthValue;// yyyy-mm-dd
				//System.out.println("First date value is as   " + dateValue);
				if (Integer.parseInt(dateValue) < 10)
				{
					dateValue = "0" + dateValue;
				}
				if (dateValue.length() == 3)
				{
					dateValue = dateValue.substring(1, dateValue.length());
				}

				String searchDate = currentDate.split("-")[0] + "-" + currentDate.split("-")[1] + "-" + dateValue;
				StringBuilder query = new StringBuilder();
				query.append("SELECT leadId,type FROM kpi_autofill WHERE empId='");
				query.append(empId);
				if (searchDate.split("-")[2].equalsIgnoreCase("00"))
				{
					query.append("' AND date LIKE '");
					query.append(searchDate.split("-")[0] + "-" + searchDate.split("-")[1]);
					query.append("%' ");
				}
				else
				{
					query.append("' AND date='");
					query.append(searchDate);
					query.append("' ");
				}
				query.append(" AND kpiId='");
				query.append(kpiId);
				query.append("' ");
				query.append(" ORDER BY date ");
				StringBuilder leadId = new StringBuilder();
				StringBuilder clientId = new StringBuilder();
				StringBuilder associateId = new StringBuilder();
				boolean lead = false;
				boolean client = false;
				boolean associate = false;
				List leaddata = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				Object[] object = null;
				if (leaddata != null && leaddata.size() > 0)
				{
					for (Iterator iterator = leaddata.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[0] != null)
						{
							if (object[1].toString().equalsIgnoreCase("Lead"))
							{
								lead = true;
								leadId.append(object[0] + ",");
							}
							if (object[1].toString().equalsIgnoreCase("Prospective Client"))
							{
								client = true;
								clientId.append(object[0] + ",");
							}
							if (object[1].toString().equalsIgnoreCase("Prospective Associate"))
							{
								associate = true;
								associateId.append(object[0] + ",");
							}
						}
					}
				}
				clientMap.put("-1", "All");
				if (lead)
				{
					query.setLength(0);
					query.append("SELECT id,leadName FROM leadgeneration WHERE id IN(" + leadId.substring(0, leadId.length() - 1) + ")");
					List leadListData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (leadListData != null && leadListData.size() > 0)
					{
						for (Iterator iterator = leadListData.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							clientMap.put("L-" + CommonHelper.getFieldValue(object[0]), CommonHelper.getFieldValue(object[1]));
						}
					}
				}
				if (client)
				{
					query.setLength(0);
					query.append("SELECT id,clientName FROM client_basic_data WHERE id IN(" + clientId.substring(0, clientId.length() - 1) + ")");
					List clientListData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (clientListData != null && clientListData.size() > 0)
					{
						for (Iterator iterator = clientListData.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							clientMap.put("C-" + object[0].toString(), object[1].toString());
						}
					}
				}
				if (associate)
				{
					query.setLength(0);
					query.append("SELECT id,associateName FROM associate_basic_data WHERE id IN(" + associateId.substring(0, associateId.length() - 1) + ")");
					List associateListData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (associateListData != null && associateListData.size() > 0)
					{
						for (Iterator iterator = associateListData.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							clientMap.put("A-" + object[0].toString(), object[1].toString());
						}
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

	public String kpiRecordViewData()
	{
		boolean validateSession = ValidateSession.checkSession();
		if (validateSession)
		{
			try
			{
				if (!ValidateSession.checkSession()) return LOGIN;

				String currentDate = currentMonthValue;
				if (dateValue.length() == 3)
				{
					dateValue = dateValue.substring(1, dateValue.length());
				}
				String searchDate = currentDate.split("-")[0] + "-" + currentDate.split("-")[1] + "-" + dateValue;
				List<DailyReportBean> dataList = getDataAccordingKpi(empId, kpiId, searchDate, clientId);
				setGridDataModelList(dataList);
				setRecords(dataList.size());
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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

	@SuppressWarnings( { "rawtypes", "unchecked" })
	private List<DailyReportBean> getDataAccordingKpi(String empId, String kpiId2, String searchDate, String clientId)
	{
		List<DailyReportBean> data = null;
		try
		{
			data = new ArrayList<DailyReportBean>();
			StringBuilder query = new StringBuilder();
			StringBuilder leadId = new StringBuilder();
			StringBuilder clientId1 = new StringBuilder();
			StringBuilder associateId = new StringBuilder();
			boolean lead = false;
			boolean client = false;
			boolean associate = false;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			DailyReportBean dailyReportBean = null;
			Object[] object = null;
			// For searching on the basis of client names they are from
			// Lead,Client,Associates *******
			if (clientId != null && !clientId.equalsIgnoreCase("") && !clientId.equalsIgnoreCase("-1"))
			{
				if (clientId.split("-")[0].equalsIgnoreCase("L"))
				{
					lead = true;
					leadId.append(clientId.split("-")[1] + ",");
				}
				if (clientId.split("-")[0].equalsIgnoreCase("C"))
				{
					client = true;
					clientId1.append(clientId.split("-")[1] + ",");
				}
				if (clientId.split("-")[0].equalsIgnoreCase("A"))
				{
					associate = true;
					associateId.append(clientId.split("-")[1] + ",");
				}
			}
			// ///////////////// For noraml Data ****
			else
			{
				query.append("SELECT leadId,type FROM kpi_autofill WHERE empId='");
				query.append(empId);
				if (searchDate.split("-")[2].equalsIgnoreCase("00"))
				{
					query.append("' AND date LIKE '");
					query.append(searchDate.split("-")[0] + "-" + searchDate.split("-")[1]);
					query.append("%' ");
				}
				else
				{
					query.append("' AND date='");
					query.append(searchDate);
					query.append("' ");
				}
				query.append(" AND kpiId='");
				query.append(kpiId2);
				query.append("' ");
				query.append(" ORDER BY date ");

				List leaddata = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (leaddata != null && leaddata.size() > 0)
				{
					for (Iterator iterator = leaddata.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object[0] != null)
						{
							if (object[1].toString().equalsIgnoreCase("Lead"))
							{
								lead = true;
								leadId.append(object[0] + ",");
							}
							if (object[1].toString().equalsIgnoreCase("Prospective Client"))
							{
								client = true;
								clientId1.append(object[0] + ",");
							}
							if (object[1].toString().equalsIgnoreCase("Prospective Associate"))
							{
								associate = true;
								associateId.append(object[0] + ",");
							}
						}
					}
				}
			}
			if (lead && (kpiId2.equalsIgnoreCase("1") || kpiId2.equalsIgnoreCase("2") || kpiId2.equalsIgnoreCase("3")))
			{
				query.setLength(0);
				query.append("SELECT lg.id,lg.leadName,lcd.personName,lcd.contactNo,lcd.designation, ka.date,ka.type FROM leadgeneration AS lg   ");
				query.append(" INNER JOIN lead_contact_data AS lcd ON  lcd.leadName=lg.id ");
				query.append(" INNER JOIN kpi_autofill AS ka ON ka.leadId=lg .id  ");
				query.append(" WHERE lg.id IN(" + leadId.substring(0, leadId.length() - 1) + ") AND ka.kpiId='" + kpiId2 + "' AND ka.empId='" + empId
						+ "' GROUP BY lg.id");
				//System.out.println("QUERY LEAD  ==  " + query.toString());
				List data1 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data1 != null && data1.size() > 0)
				{
					for (Iterator iterator = data1.iterator(); iterator.hasNext();)
					{
						dailyReportBean = new DailyReportBean();
						object = (Object[]) iterator.next();

						dailyReportBean.setId(object[0].toString());
						dailyReportBean.setClientName(CommonHelper.getFieldValue(object[1]));
						dailyReportBean.setContactName(CommonHelper.getFieldValue(object[2]));
						dailyReportBean.setMobileNo(CommonHelper.getFieldValue(object[3]));
						dailyReportBean.setDesignation(CommonHelper.getFieldValue(object[4]));
						dailyReportBean.setOffering_map("NA");
						dailyReportBean.setLast_action("Raw Lead");
						dailyReportBean.setCurrentmonth(DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(object[5])));
						dailyReportBean.setCurrent_status(CommonHelper.getFieldValue(object[6]));
						data.add(dailyReportBean);
					}
				}
			}
			if (client)
			{
				query.setLength(0);
				query.append("SELECT cbd.id,cbd.clientName,ccd.personName,ccd.contactNo,ccd.designation, ka.date,ka.type FROM client_basic_data AS cbd    ");
				query.append(" INNER JOIN client_contact_data AS ccd ON  cbd.id=ccd.clientName ");
				query.append(" INNER JOIN kpi_autofill AS ka ON ka.leadId=cbd .id   ");
				query.append(" WHERE cbd.id IN(" + clientId1.substring(0, clientId1.length() - 1) + ") AND ka.kpiId='" + kpiId2 + "' AND ka.empId='" + empId
						+ "' GROUP BY cbd.id");
				//System.out.println("QUERY CLIENT  ==  " + query.toString());
				List data1 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data1 != null && data1.size() > 0)
				{
					for (Iterator iterator = data1.iterator(); iterator.hasNext();)
					{
						dailyReportBean = new DailyReportBean();
						object = (Object[]) iterator.next();

						dailyReportBean.setId(object[0].toString());
						dailyReportBean.setClientName(CommonHelper.getFieldValue(object[1]));
						dailyReportBean.setContactName(CommonHelper.getFieldValue(object[2]));
						dailyReportBean.setMobileNo(CommonHelper.getFieldValue(object[3]));
						dailyReportBean.setDesignation(CommonHelper.getFieldValue(object[4]));
						dailyReportBean.setOffering_map("NA");
						dailyReportBean.setLast_action("Raw Lead");
						dailyReportBean.setCurrentmonth(DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(object[5])));
						dailyReportBean.setCurrent_status(CommonHelper.getFieldValue(object[6]));
						data.add(dailyReportBean);
					}
				}
			}
			if (associate)
			{
				query.setLength(0);
				query.append("SELECT abd.id,abd.associateName,acd.name,acd.contactNum,acd.designation, ka.date,ka.type FROM associate_basic_data AS abd    ");
				query.append(" INNER JOIN associate_contact_data AS acd ON  abd.id=acd.associateName ");
				query.append(" INNER JOIN kpi_autofill AS ka ON ka.leadId=abd .id   ");
				query.append(" WHERE abd.id IN(" + associateId.substring(0, associateId.length() - 1) + ") AND ka.kpiId='" + kpiId2 + "' AND ka.empId='" + empId
						+ "' GROUP BY abd.id");
				//System.out.println("QUERY ASSOCIATE  ==  " + query.toString());
				List data1 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data1 != null && data1.size() > 0)
				{
					for (Iterator iterator = data1.iterator(); iterator.hasNext();)
					{
						dailyReportBean = new DailyReportBean();
						object = (Object[]) iterator.next();

						dailyReportBean.setId(object[0].toString());
						dailyReportBean.setClientName(CommonHelper.getFieldValue(object[1]));
						dailyReportBean.setContactName(CommonHelper.getFieldValue(object[2]));
						dailyReportBean.setMobileNo(CommonHelper.getFieldValue(object[3]));
						dailyReportBean.setDesignation(CommonHelper.getFieldValue(object[4]));
						dailyReportBean.setOffering_map("NA");
						dailyReportBean.setLast_action("Raw Lead");
						dailyReportBean.setCurrentmonth(DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(object[5])));
						dailyReportBean.setCurrent_status(CommonHelper.getFieldValue(object[6]));
						data.add(dailyReportBean);
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public String fetchAchievedKPIForMonth()
	{
		try
		{
			if (!ValidateSession.checkSession()) return ERROR;
			mapKpi = new KPIReportHelper().fetchAchievedKPIForMonth(empId, currentMonthValue.split("-")[0] + "-" + currentMonthValue.split("-")[1], connectionSpace);

			jsonArrayKPI = new JSONArray();
			for (Map.Entry<String, String> map : mapKpi.entrySet())
			{
				JSONObject formDetailsJson = new JSONObject();
				formDetailsJson.put("ID", map.getKey().toString());
				formDetailsJson.put("NAME", map.getValue().toString());
				jsonArrayKPI.add(formDetailsJson);
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

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public List<DailyReportBean> getKpiGridModelList()
	{
		return kpiGridModelList;
	}

	public void setKpiGridModelList(List<DailyReportBean> kpiGridModelList)
	{
		this.kpiGridModelList = kpiGridModelList;
	}

	public String getCurrentMonthValue()
	{
		return currentMonthValue;
	}

	public void setCurrentMonthValue(String currentMonthValue)
	{
		this.currentMonthValue = new KPIReportHelper().getNumericalDate(currentMonthValue);
	}

	public Map<String, String> getMapKpi()
	{
		return mapKpi;
	}

	public void setMapKpi(Map<String, String> mapKpi)
	{
		this.mapKpi = mapKpi;
	}

	public JSONArray getJsonArrayKPI()
	{
		return jsonArrayKPI;
	}

	public void setJsonArrayKPI(JSONArray jsonArrayKPI)
	{
		this.jsonArrayKPI = jsonArrayKPI;
	}

	public String getKpiId()
	{
		return kpiId;
	}

	public void setKpiId(String kpiId)
	{
		this.kpiId = kpiId;
	}

	public String getDateValue()
	{
		return dateValue;
	}

	public void setDateValue(String dateValue)
	{
		this.dateValue = dateValue;
	}

	public List<DailyReportBean> getGridDataModelList()
	{
		return gridDataModelList;
	}

	public void setGridDataModelList(List<DailyReportBean> gridDataModelList)
	{
		this.gridDataModelList = gridDataModelList;
	}

	public Map<String, String> getClientMap()
	{
		return clientMap;
	}

	public void setClientMap(Map<String, String> clientMap)
	{
		this.clientMap = clientMap;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

}
