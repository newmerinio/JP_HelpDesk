package com.Over2Cloud.ctrl.wfpm.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import KPIReportHelper.OfferingReportHelper;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class OfferingAutoFillReportAction extends ActionSupport
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	private String empId = session.get("empIdofuser").toString().split("-")[1];
	private SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private List<DailyReportBean> offeringGridModelList;
	private Map<String, Object> userdata;
	private Map<String, String> employee_basicMap;
	private String acManagerName;
	private Integer rows = 0;
	private String acManager;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	private String searchOper;
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	private String currentMonthValue;
	private Map<String, String> mapOffering;
	private JSONArray jsonArrayOffering;
	private String month;
	private String offeringId;
	private String dateValue;
	private List<DailyReportBean> gridDataModelList;
	private String clientId;
	private Map<String, String> clientMap;
	private JSONArray arr = new JSONArray();

	public String getOfferingRecords()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			userdata = new HashMap<String, Object>();
			// System.out.println("111111111111:" + currentMonthValue);
			List<DailyReportBean> offeringList = getDataOffering(
					empId,
					currentMonthValue.split("-")[0] + "-"
							+ currentMonthValue.split("-")[1]);
			if (offeringList != null && offeringList.size() > 0)
			{
				setOfferingGridModelList(offeringList);
			}
			setRecords(offeringList.size());
			setTotal((int) Math
					.ceil((double) getRecords() / (double) getRows()));
			if (offeringList != null && offeringList.size() > 0)
			{
				DecimalFormat df2 = new DecimalFormat("###.##");
				int tTarget = 0, weightage = 0;
				double remain = 0;
				double tSales = 0;
				double perAcheive = 0;
				for (DailyReportBean dpb : offeringList)
				{
					tTarget = tTarget + Integer.parseInt(dpb.getTargetvalue());
					tSales = tSales + Double.parseDouble(dpb.getTotalsale());
					remain = remain
							+ Double.parseDouble(dpb.getRemainng_target()
									.replace("(+)", "-"));
					perAcheive = perAcheive + dpb.getPerAcheive();
					weightage = weightage
							+ Integer.parseInt(dpb.getWeightage());
				}
				userdata.put("offeringname", "Total : ");
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

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	private List<DailyReportBean> getDataOffering(String empId, String month)
	{
		List<DailyReportBean> offeringList = null;
		try
		{
			// System.out.println("month:" + month);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			KPIReportHelper krh = new KPIReportHelper();
			String targetMonth = krh.fetchLastTargetMonthByEmpId(empId, month,
					connectionSpace, TargetType.OFFERING);

			CommonHelper CH = new CommonHelper();
			StringBuilder query = new StringBuilder();
			String offeringDetail[] = CH.getOfferingName();
			query.append("SELECT offeringId,weightage,targetValue,offM."
					+ offeringDetail[0]
					+ " FROM target_for_offering as tfo INNER JOIN "
					+ offeringDetail[1]
					+ " AS offM ON tfo.offeringId=offM.id  WHERE empId='");
			query.append(empId);
			query.append("' AND applicableFrom IN ('");
			query.append(targetMonth);
			query.append("') ");

			List offeringIds = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			DailyReportBean dailyReportBean = null;
			DecimalFormat df2 = null;
			offeringList = new ArrayList<DailyReportBean>();
			Object[] object = null;
			for (Iterator iterator = offeringIds.iterator(); iterator.hasNext();)
			{
				dailyReportBean = new DailyReportBean();
				object = (Object[]) iterator.next();
				if (object[0] != null)
				{
					dailyReportBean
							.setId(CommonHelper.getFieldValue(object[0]));
					dailyReportBean.setWeightage(object[1].toString());
					dailyReportBean.setTargetvalue(object[2].toString());
					dailyReportBean.setOfferingname(CommonHelper
							.getFieldValue(object[3]));

					query = new StringBuilder();
					query.append("SELECT SUM(achievementValue) FROM achievement_for_offering AS afo   WHERE empId='");
					query.append(empId);
					query.append("' AND afo.achievementDate LIKE '");
					query.append(month);
					query.append("%' AND afo.offeringId='");
					query.append(object[0].toString());
					query.append("' ");

					// //System.out.println("TARGET QUERY IS AS :::  "+query.toString());
					List targetList = cbt.executeAllSelectQuery(
							query.toString(), connectionSpace);
					if (targetList != null && targetList.size() > 0)
					{
						for (Iterator iterator3 = targetList.iterator(); iterator3
								.hasNext();)
						{
							Object object1 = (Object) iterator3.next();

							if (object1 != null)
							{
								dailyReportBean.setTotalsale(CommonHelper
										.getFieldValue(object1));
								dailyReportBean
										.setRemainng_target(String
												.valueOf(
														Double.parseDouble(object[2]
																.toString())
																- Double.parseDouble(object1
																		.toString()))
												.replace("-", "(+)"));
								df2 = new DecimalFormat("###.##");
								dailyReportBean
										.setPerAcheive(Double.parseDouble(df2.format((Double
												.parseDouble(object1.toString()) / Double
												.parseDouble(object[2]
														.toString()))
												* (Double.parseDouble(object[1]
														.toString()) / 100)
												* 100)));

							}
							else
							{
								dailyReportBean.setTotalsale("0");
								dailyReportBean.setRemainng_target("0");
								dailyReportBean.setPerAcheive(0);
							}

							query = new StringBuilder();
							query.append("SELECT SUBSTRING(achievementDate,-2),SUM(achievementValue) FROM achievement_for_offering WHERE empId='");
							query.append(empId);
							query.append("' AND offeringId = '");
							query.append(object[0].toString());
							query.append("' AND achievementDate LIKE '");
							query.append(month);
							query.append("%' GROUP BY achievementDate");

							// //System.out.println("QUERY 222222222   "+query);
							List data = cbt.executeAllSelectQuery(
									query.toString(), connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator2 = data.iterator(); iterator2
										.hasNext();)
								{
									object = (Object[]) iterator2.next();
									switch (Integer.parseInt(object[0]
											.toString()))
									{
										case 1:
											dailyReportBean.setOne(object[1]
													.toString());
											break;
										case 2:
											dailyReportBean.setTwo(object[1]
													.toString());
											break;
										case 3:
											dailyReportBean.setThree(object[1]
													.toString());
											break;
										case 4:
											dailyReportBean.setFourth(object[1]
													.toString());
											break;
										case 5:
											dailyReportBean.setFifth(object[1]
													.toString());
											break;
										case 6:
											dailyReportBean.setSix(object[1]
													.toString());
											break;
										case 7:
											dailyReportBean.setSeven(object[1]
													.toString());
											break;
										case 8:
											dailyReportBean.setEight(object[1]
													.toString());
											break;
										case 9:
											dailyReportBean.setNine(object[1]
													.toString());
											break;
										case 10:
											dailyReportBean.setTen(object[1]
													.toString());
											break;
										case 11:
											dailyReportBean.setEleven(object[1]
													.toString());
											break;
										case 12:
											dailyReportBean.setTwelve(object[1]
													.toString());
											break;
										case 13:
											dailyReportBean
													.setThirteen(object[1]
															.toString());
											break;
										case 14:
											dailyReportBean
													.setFourteen(object[1]
															.toString());
											break;
										case 15:
											dailyReportBean
													.setFifteen(object[1]
															.toString());
											break;
										case 16:
											dailyReportBean
													.setSixteen(object[1]
															.toString());
											break;
										case 17:
											dailyReportBean
													.setSeventeen(object[1]
															.toString());
											break;
										case 18:
											dailyReportBean
													.setEighteen(object[1]
															.toString());
											break;
										case 19:
											dailyReportBean
													.setNineteen(object[1]
															.toString());
											break;
										case 20:
											dailyReportBean.setTwenty(object[1]
													.toString());
											break;
										case 21:
											dailyReportBean
													.setTwentyone(object[1]
															.toString());
											break;
										case 22:
											dailyReportBean
													.setTwentytwo(object[1]
															.toString());
											break;
										case 23:
											dailyReportBean
													.setTwentythree(object[1]
															.toString());
											break;
										case 24:
											dailyReportBean
													.setTwentyfourth(object[1]
															.toString());
											break;
										case 25:
											dailyReportBean
													.setTwentyfifth(object[1]
															.toString());
											break;
										case 26:
											dailyReportBean
													.setTwentysix(object[1]
															.toString());
											break;
										case 27:
											dailyReportBean
													.setTwentyseven(object[1]
															.toString());
											break;
										case 28:
											dailyReportBean
													.setTwentyeight(object[1]
															.toString());
											break;
										case 29:
											dailyReportBean
													.setTwentynine(object[1]
															.toString());
											break;
										case 30:
											dailyReportBean.setThirty(object[1]
													.toString());
											break;
										case 31:
											dailyReportBean
													.setThirtyone(object[1]
															.toString());
											break;
										default:
											break;
									}
								}
							}
							dailyReportBean.setCurrentmonth(month.split("-")[1]
									+ "-" + month.split("-")[0]);
							offeringList.add(dailyReportBean);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return offeringList;
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	public String beforeOfferingGraph()
	{
		boolean validateSession = ValidateSession.checkSession();
		if (validateSession)
		{
			try
			{
				mapOffering = new LinkedHashMap<String, String>();
				currentMonthValue = DateUtil.getCurrentDateUSFormat();
				mapOffering = new OfferingReportHelper()
						.fetchAchievedOfferingForMonth(empId,
								currentMonthValue, connectionSpace);

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

	public String showBarChartOfferingFill()
	{
		try
		{
			List<DailyReportBean> finalDataList3 = new ArrayList<DailyReportBean>();

			finalDataList3 = getDataOffering(
					empId,
					currentMonthValue.split("-")[0] + "-"
							+ currentMonthValue.split("-")[1]);
			JSONObject jsonObjectKPI;
			jsonArrayOffering = new JSONArray();
			if (finalDataList3 != null && finalDataList3.size() > 0)
			{
				jsonObjectKPI = new JSONObject();
				for (DailyReportBean pojo : finalDataList3)
				{
					jsonObjectKPI.put("Offering", pojo.getOfferingname());
					jsonObjectKPI.put("totalTarget", pojo.getTargetvalue());
					jsonObjectKPI.put("acheivement", pojo.getTotalsale());
					jsonArrayOffering.add(jsonObjectKPI);
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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String offeringAutofillGraphPerOfferingShow()
	{
		try
		{
			List<DailyReportBean> finalDataList3 = new ArrayList<DailyReportBean>();

			finalDataList3 = getDataPerOffering(
					empId,
					currentMonthValue.split("-")[0] + "-"
							+ currentMonthValue.split("-")[1], offeringId);
			JSONObject jsonObjectKPI;
			jsonArrayOffering = new JSONArray();
			if (finalDataList3 != null && finalDataList3.size() > 0)
			{
				List t = null;
				double one = 0;
				double two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0, eight = 0, nine = 0, ten = 0, eleven = 0, tweleve = 0, thirteen = 0, fourteen = 0, fifteen = 0, sixteen = 0, seventeen = 0, eighteen = 0, nineteen = 0, twenty = 0, twentyone = 0, twentytwo = 0, twentythree = 0, twentyfour = 0, twnetyfive = 0, twentysix = 0, twentyseven = 0, twentyeight = 0, twentynine = 0, thirty = 0, thirtyone = 0;
				t = new ArrayList<String>();
				for (DailyReportBean pojo : finalDataList3)
				{
					if (pojo.getOne().equalsIgnoreCase("0"))
					{
						one = 0;
					}
					else
					{
						one = Double.parseDouble(pojo.getOne());
					}
					if (pojo.getTwo().equalsIgnoreCase("0"))
					{
						two = 0;
					}
					else
					{
						two = Double.parseDouble(pojo.getTwo());
					}
					if (pojo.getThree().equalsIgnoreCase("0"))
					{

					}
					else
					{
						three = Double.parseDouble(pojo.getThree());
					}
					if (pojo.getFourth().equalsIgnoreCase("0"))
					{

					}
					else
					{
						four = Double.parseDouble(pojo.getFourth());
					}
					if (pojo.getFifth().equalsIgnoreCase("0"))
					{

					}
					else
					{
						five = Double.parseDouble(pojo.getFifth());
					}
					if (pojo.getSix().equalsIgnoreCase("0"))
					{

					}
					else
					{
						six = Double.parseDouble(pojo.getSix());
					}
					if (pojo.getSeven().equalsIgnoreCase("0"))
					{

					}
					else
					{
						seven = Double.parseDouble(pojo.getSeven());
					}
					if (pojo.getEight().equalsIgnoreCase("0"))
					{

					}
					else
					{
						eight = Double.parseDouble(pojo.getEight());
					}
					if (pojo.getNine().equalsIgnoreCase("0"))
					{

					}
					else
					{
						nine = Double.parseDouble(pojo.getNine());
					}
					if (pojo.getTen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						ten = Double.parseDouble(pojo.getTen());
					}
					if (pojo.getEleven().equalsIgnoreCase("0"))
					{

					}
					else
					{
						eleven = Double.parseDouble(pojo.getEleven());
					}
					if (pojo.getTwelve().equalsIgnoreCase("0"))
					{

					}
					else
					{
						tweleve = Double.parseDouble(pojo.getTwelve());
					}
					if (pojo.getThirteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						thirteen = Double.parseDouble(pojo.getThirteen());
					}
					if (pojo.getFourteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						fourteen = Double.parseDouble(pojo.getFourteen());
					}
					if (pojo.getFifteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						fifteen = Double.parseDouble(pojo.getFifteen());
					}
					if (pojo.getSixteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						sixteen = Double.parseDouble(pojo.getSixteen());
					}
					if (pojo.getSeventeen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						seventeen = Double.parseDouble(pojo.getSeventeen());
					}
					if (pojo.getEighteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						eighteen = Double.parseDouble(pojo.getEighteen());
					}
					if (pojo.getNineteen().equalsIgnoreCase("0"))
					{

					}
					else
					{
						nineteen = Double.parseDouble(pojo.getNineteen());
					}
					if (pojo.getTwenty().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twenty = Double.parseDouble(pojo.getTwenty());
					}
					if (pojo.getTwentyone().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyone = Double.parseDouble(pojo.getTwentyone());
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
						twnetyfive = Double.parseDouble(pojo.getTwentyfifth());
					}
					if (pojo.getTwentysix().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentysix = Double.parseDouble(pojo.getTwentysix());
					}
					if (pojo.getTwentyseven().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyseven = Double.parseDouble(pojo.getTwentyseven());
					}
					if (pojo.getTwentyeight().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentyeight = Double.parseDouble(pojo.getTwentyeight());
					}
					if (pojo.getTwentynine().equalsIgnoreCase("0"))
					{

					}
					else
					{
						twentynine = Double.parseDouble(pojo.getTwentynine());
					}
					if (pojo.getThirty().equalsIgnoreCase("0"))
					{

					}
					else
					{
						thirty = Double.parseDouble(pojo.getThirty());
					}
					if (pojo.getThirtyone().equalsIgnoreCase("0"))
					{

					}
					else
					{
						thirtyone = Double.parseDouble(pojo.getThirtyone());
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
					jsonArrayOffering.add(jsonObjectKPI);
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
	private List<DailyReportBean> getDataPerOffering(String empId,
			String month, String offerId)
	{
		List<DailyReportBean> offeringList = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			DailyReportBean dailyReportBean = null;
			offeringList = new ArrayList<DailyReportBean>();
			Object[] object = null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT SUBSTRING(achievementDate,-2),SUM(achievementValue) FROM achievement_for_offering WHERE empId='");
			query.append(empId);
			query.append("' AND offeringId = '");
			query.append(offerId);
			query.append("' AND achievementDate LIKE '");
			query.append(month);
			query.append("%' GROUP BY achievementDate");
			// //System.out.println("QUERY 222222222   "+query.toString());
			List data = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
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
							dailyReportBean
									.setTwentythree(object[1].toString());
							break;
						case 24:
							dailyReportBean.setTwentyfourth(object[1]
									.toString());
							break;
						case 25:
							dailyReportBean
									.setTwentyfifth(object[1].toString());
							break;
						case 26:
							dailyReportBean.setTwentysix(object[1].toString());
							break;
						case 27:
							dailyReportBean
									.setTwentyseven(object[1].toString());
							break;
						case 28:
							dailyReportBean
									.setTwentyeight(object[1].toString());
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
					offeringList.add(dailyReportBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return offeringList;
	}

	@SuppressWarnings("rawtypes")
	public String beforeOfferingDataView()
	{
		boolean validateSession = ValidateSession.checkSession();
		if (validateSession)
		{
			try
			{
				clientMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				String currentDate = DateUtil.getCurrentDateUSFormat();
				if (Integer.parseInt(dateValue) < 10)
				{
					dateValue = "0" + dateValue;
				}
				if (dateValue.length() == 3)
				{
					dateValue = dateValue.substring(1, dateValue.length());
				}
				String searchDate = null;
				if (dateValue.equalsIgnoreCase("00"))
				{
					searchDate = currentDate.split("-")[0] + "-"
							+ currentDate.split("-")[1];
				}
				else
				{
					searchDate = currentDate.split("-")[0] + "-"
							+ currentDate.split("-")[1] + "-" + dateValue;
				}
				StringBuilder query = new StringBuilder();
				query.append("SELECT cbd.id,cbd.clientName  ");
				query.append(" FROM client_contact_data as cca  ");
				query.append(" INNER JOIN client_offering_mapping as com ON com.clientName=cca.clientName  ");
				query.append(" INNER JOIN client_basic_data as cbd ON cca.clientName=cbd.id  ");
				query.append(" INNER JOIN achievement_for_offering as aft on aft.client_offering_mapping_id=com.id  ");
				query.append(" INNER JOIN client_takeaction as cta ON cta.contacId=cca.id  ");
				query.append(" INNER JOIN client_status as cs on cs.id= cta.statusId  ");
				query.append(" WHERE aft.offeringId ='"
						+ offeringId
						+ "' AND aft.achievementDate LIKE '"
						+ searchDate
						+ "%' AND aft.empId='"
						+ empId
						+ "'  group by cta.contacId ORDER BY cta.maxDateTime DESC");
				// //System.out.println(" FIRST QUERY IS AS  ===  "+query.toString());

				List data1 = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (data1 != null && data1.size() > 0)
				{
					clientMap.put("-1", "All");
					CommonHelper CH = new CommonHelper();
					for (Iterator iterator = data1.iterator(); iterator
							.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null)
						{
							clientMap.put(object[0].toString(),
									CH.getFieldValue(object[1]));
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

	public String offeringRecordViewData()
	{
		boolean validateSession = ValidateSession.checkSession();
		if (validateSession)
		{
			try
			{
				String userName = (String) session.get("uName");

				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				String empId = (String) session.get("empIdofuser");// o-100000
				if (empId == null)
					return ERROR;
				String currentDate = DateUtil.getCurrentDateUSFormat();
				/*
				 * if (Integer.parseInt(dateValue)<10) {
				 * dateValue="0"+dateValue; }
				 */
				// //System.out.println("datevalue :::   "+dateValue);
				if (dateValue.length() == 3)
				{
					dateValue = dateValue.substring(1, dateValue.length());
				}
				String searchDate = currentDate.split("-")[0] + "-"
						+ currentDate.split("-")[1] + "-" + dateValue;
				List<DailyReportBean> dataList = getDataAccordingOffering(
						empId.split("-")[1], offeringId, searchDate, clientId);
				setGridDataModelList(dataList);
				setRecords(dataList.size());
				setTotal((int) Math.ceil((double) getRecords()
						/ (double) getRows()));
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

	@SuppressWarnings("rawtypes")
	private List<DailyReportBean> getDataAccordingOffering(String empId,
			String offId, String searchDate, String clientId)
	{
		List<DailyReportBean> data = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			CommonHelper CH = new CommonHelper();
			DailyReportBean dailyReportBean = null;
			data = new ArrayList<DailyReportBean>();
			StringBuilder query = new StringBuilder();
			if (searchDate.split("-")[2].equalsIgnoreCase("00"))
			{
				searchDate = searchDate.split("-")[0] + "-"
						+ searchDate.split("-")[1];
			}

			query.append("SELECT DISTINCT (cca.id),cbd.clientName,cca.personName,cca.contactNo,cca.designation,aft.achievementDate,cs.statusName  ");
			query.append(" FROM client_contact_data as cca  ");
			query.append(" INNER JOIN client_offering_mapping as com ON com.clientName=cca.clientName  ");
			query.append(" INNER JOIN client_basic_data as cbd ON cca.clientName=cbd.id  ");
			query.append(" INNER JOIN achievement_for_offering as aft on aft.client_offering_mapping_id=com.id  ");
			query.append(" INNER JOIN client_takeaction as cta ON cta.contacId=cca.id  ");
			query.append(" INNER JOIN client_status as cs on cs.id= cta.statusId  ");
			query.append(" WHERE aft.offeringId ='" + offId
					+ "' AND aft.achievementDate LIKE '" + searchDate
					+ "%' AND aft.empId='" + empId + "'  ");
			if (clientId != null && !clientId.equalsIgnoreCase("-1"))
			{
				query.append("AND cbd.id= " + clientId);
			}
			query.append(" group by cta.contacId ORDER BY cta.maxDateTime DESC ");

			// //System.out.println("QUERY IS AS  ===  "+query.toString());
			List data1 = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			if (data1 != null && data1.size() > 0)
			{
				for (Iterator iterator = data1.iterator(); iterator.hasNext();)
				{
					dailyReportBean = new DailyReportBean();
					Object[] object = (Object[]) iterator.next();

					dailyReportBean.setId(object[0].toString());
					dailyReportBean.setClientName(CH.getFieldValue(object[1]));
					dailyReportBean.setContactName(CH.getFieldValue(object[2]));
					dailyReportBean.setMobileNo(CH.getFieldValue(object[3]));
					dailyReportBean.setDesignation(CH.getFieldValue(object[4]));
					dailyReportBean.setOffering_map("NA");
					dailyReportBean.setLast_action("Client");
					dailyReportBean.setCurrentmonth(DateUtil
							.convertDateToIndianFormat(CH
									.getFieldValue(object[5])));
					dailyReportBean.setCurrent_status(CH
							.getFieldValue(object[6]));
					data.add(dailyReportBean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public String fetchAchievedOfferingForMonth()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			mapOffering = new OfferingReportHelper()
					.fetchAchievedOfferingForMonth(empId,
							currentMonthValue.split("-")[0] + "-"
									+ currentMonthValue.split("-")[1],
							connectionSpace);
			jsonArrayOffering = new JSONArray();
			for (Map.Entry<String, String> map : mapOffering.entrySet())
			{
				JSONObject formDetailsJson = new JSONObject();
				formDetailsJson.put("ID", map.getKey().toString());
				formDetailsJson.put("NAME", map.getValue().toString());
				jsonArrayOffering.add(formDetailsJson);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
		public String beforeOpportunityReportView()
		{
			try {
				employee_basicMap = new LinkedHashMap<String, String>();
				//employee_basicMap = ch3.fetchAllEmployee();
				EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
				employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			long sumaug =0;
			long sumsept =0;
			long sumOct =0;
			long sumnov =0;
			long sumDec =0;
			long sumJan =0;
			long sumfeb =0;
			long sumMarch =0;
			long sumapril =0;
			long sumMay =0;
			long sumJune =0;
			long sumJuly =0;
			String month = null;
			String month2 = null;
			String month3 = null;
			String month4 = null;
			String month5 = null;
			String month6 = null;
			String month7 = null;
			String month8 = null;
			String month9 = null;
			String month10 = null;
			String month11 = null;
			String month12 = null;
			
			if(acManager != null)
			{
				query.append("SELECT opportunity_value, closure_date  ");
			}else
			{
				query.append("SELECT opportunity_value, closure_date  from opportunity_details where (opportunity_value is not null and opportunity_value > 0)");
			}
			
			if(acManager != null &&  (acManager.equalsIgnoreCase("") ||  acManager.equalsIgnoreCase("-1") || acManager.equalsIgnoreCase("undefined")))
			{
				query.append(" from opportunity_details where (opportunity_value is not null and opportunity_value > 0)");
			}
			else{
				if(acManager != null){
										query.append(" from opportunity_details where (opportunity_value is not null and opportunity_value > 0 and userName = '"+acManager+"') ");
			
									}
			}
			System.out.println("query  "+query.toString());
			List data1 = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			
			/*JSONObject ob =null;
			
			ob = new JSONObject();
			ob.put("label", "xxxx");
			ob.put("value", 5);
			arr.add(ob);
			
			ob = new JSONObject();
			ob.put("label", "yyyy");
			ob.put("value", 10);
			arr.add(ob);
			
			ob = new JSONObject();
			ob.put("label", "zzzzz");
			ob.put("value", 4);
			arr.add(ob);
			
			ob = new JSONObject();
			ob.put("label", "xxxx");
			ob.put("value", 6);
			arr.add(ob);*/
			
			if (data1 != null && data1.size() > 0)
			{
				JSONObject ob =null;
				JSONObject ob2 =null;
				JSONObject ob3 =null;
				JSONObject ob4 =null;
				JSONObject ob5 =null;
				JSONObject ob6 =null;
				JSONObject ob7 =null;
				JSONObject ob8 =null;
				JSONObject ob9 =null;
				JSONObject ob10 =null;
				JSONObject ob11 =null;
				JSONObject ob12 =null;
				for (Iterator iterator = data1.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("08"))
						{
							month = "August";
							sumaug = sumaug + Long.parseLong(object[0].toString());
							
						}
						
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("09"))
						{
							month2 = "September";
							sumsept = sumsept + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("10"))
						{
							month3 = "October";
							sumOct = sumOct + Long.parseLong(object[0].toString());
							
						}
					}
					
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("11"))
						{
							month4 = "November";
							sumnov = sumnov + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("12"))
						{
							month5 = "December";
							sumDec = sumDec + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("01"))
						{
							month6 = "Janunry";
							sumJan = sumJan + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("02"))
						{
							month7 = "February";
							sumfeb = sumfeb + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("03"))
						{
							month8 = "March";
							sumMarch = sumMarch + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("04"))
						{
							month9 = "April";
							sumapril = sumapril + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("05"))
						{
							month10 = "May";
							sumMay = sumMay + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("06"))
						{
							month11 = "June";
							sumJune = sumJune + Long.parseLong(object[0].toString());
							
						}
					}
					if(object[1]!=null && !object[1].toString().equalsIgnoreCase("NA"))
					{
						if(object[1].toString().split("-")[1].equalsIgnoreCase("07"))
						{
							month12 = "July";
							sumJuly = sumJuly + Long.parseLong(object[0].toString());
							
						}
					}
					
				}
				System.out.println("sumaug "+sumaug);
				ob = new JSONObject();
				ob.put("label", month);
				ob.put("value", sumaug);
				arr.add(ob);
				System.out.println("sumsept "+sumsept);
				ob2 = new JSONObject();
				ob2.put("label", month2);
				ob2.put("value", sumsept);
				arr.add(ob2);
				
				ob3 = new JSONObject();
				ob3.put("label", month3);
				ob3.put("value", sumOct);
				arr.add(ob3);
				
				ob4 = new JSONObject();
				ob4.put("label", month4);
				ob4.put("value", sumnov);
				arr.add(ob4);
				ob5 = new JSONObject();
				ob5.put("label", month5);
				ob5.put("value", sumDec);
				arr.add(ob5);
				ob6 = new JSONObject();
				ob6.put("label", month6);
				ob6.put("value", sumJan);
				arr.add(ob6);
				ob7 = new JSONObject();
				ob7.put("label", month7);
				ob7.put("value", sumfeb);
				arr.add(ob7);
				ob8 = new JSONObject();
				ob8.put("label", month8);
				ob8.put("value", sumMarch);
				arr.add(ob8);
				ob9 = new JSONObject();
				ob9.put("label", month9);
				ob9.put("value", sumapril);
				arr.add(ob9);
				ob10 = new JSONObject();
				ob10.put("label", month10);
				ob10.put("value", sumMay);
				arr.add(ob10);
				ob11 = new JSONObject();
				ob11.put("label", month11);
				ob11.put("value", sumJune);
				arr.add(ob11);
				ob12 = new JSONObject();
				ob12.put("label", month12);
				ob12.put("value", sumJuly);
				arr.add(ob12);
				
			}
			System.out.println(arr.size());
			return SUCCESS;
		}

	public List<DailyReportBean> getOfferingGridModelList()
	{
		return offeringGridModelList;
	}

	public void setOfferingGridModelList(
			List<DailyReportBean> offeringGridModelList)
	{
		this.offeringGridModelList = offeringGridModelList;
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

	public String getCurrentMonthValue()
	{
		return currentMonthValue;
	}

	public void setCurrentMonthValue(String currentMonthValue)
	{
		this.currentMonthValue = new KPIReportHelper()
				.getNumericalDate(currentMonthValue);
	}

	public Map<String, String> getMapOffering()
	{
		return mapOffering;
	}

	public void setMapOffering(Map<String, String> mapOffering)
	{
		this.mapOffering = mapOffering;
	}

	public JSONArray getJsonArrayOffering()
	{
		return jsonArrayOffering;
	}

	public void setJsonArrayOffering(JSONArray jsonArrayOffering)
	{
		this.jsonArrayOffering = jsonArrayOffering;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
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

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public Map<String, String> getClientMap()
	{
		return clientMap;
	}

	public void setClientMap(Map<String, String> clientMap)
	{
		this.clientMap = clientMap;
	}

	public JSONArray getArr() {
		return arr;
	}

	public void setArr(JSONArray arr) {
		this.arr = arr;
	}

	public Map<String, String> getEmployee_basicMap() {
		return employee_basicMap;
	}

	public void setEmployee_basicMap(Map<String, String> employee_basicMap) {
		this.employee_basicMap = employee_basicMap;
	}

	public String getAcManager() {
		return acManager;
	}

	public void setAcManager(String acManager) {
		this.acManager = acManager;
	}

	public String getAcManagerName() {
		return acManagerName;
	}

	public void setAcManagerName(String acManagerName) {
		this.acManagerName = acManagerName;
	}
	
}