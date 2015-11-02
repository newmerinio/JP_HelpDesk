package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HierarchyViewReport extends ActionSupport
{
	static final Log							log							= LogFactory.getLog(HierarchyViewReport.class);
	private Map										session					= ActionContext.getContext().getSession();
	private String								userName				= (String) session.get("uName");
	private SessionFactory				connectionSpace	= (SessionFactory) session.get("connectionSpace");
	private String								id;
	List<Temp>										kpiGridModel;
	List<Temp>										empDetail;
	private Map<String, Object>		userdata;
	private List<DailyReportBean>	kpiList;
	private List<DailyReportBean>	gridModelList;
	private String								filters;

	public String getFilters()
	{
		return filters;
	}

	public void setFilters(String filters)
	{
		this.filters = filters;
	}

	public String getDSRByUser()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			int total = 0;
			int totalTarget = 0;
			int totalAchived = 0;
			int remTarget = 0;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			userdata = new HashMap<String, Object>();
			String currentMonth = "";
			int month = DateUtil.getCurrentMonth();
			if (month < 10)
			{
				currentMonth = "0" + month + "-" + DateUtil.getCurretnYear();
			}
			String query = "select KPIId, targetvalue from target where empID=" + getId() + " and targetMonth='" + currentMonth + "'";
			List kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
			DailyReportBean dailyReportBean = null;
			kpiList = new ArrayList<DailyReportBean>();
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
						query = "select kpi_id, kpi_target from dailyreport where currentMonth ='" + searchString + "'";
						kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
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
							query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
									+ " from dailyreport where kpi_id=(select id from krakpicollection where kpi='" + kpiLists.get(i) + "') and currentMonth='" + currentMonth
									+ "'";
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
										dailyReportBean.setSeven(secondWeek[6]);
									}

									for (int j = 0; j < secondWeek.length; j++)
									{
										dailyReportBean.setEight(secondWeek[0]);
										dailyReportBean.setNine(secondWeek[1]);
										dailyReportBean.setTen(secondWeek[2]);
										dailyReportBean.setEleven(secondWeek[3]);
										dailyReportBean.setTwelve(secondWeek[4]);
										dailyReportBean.setThirteen(thirdWeek[5]);
										dailyReportBean.setFourteen(thirdWeek[6]);

									}

									for (int j = 0; j < thirdWeek.length; j++)
									{
										dailyReportBean.setFifteen(thirdWeek[0]);
										dailyReportBean.setSixteen(thirdWeek[1]);
										dailyReportBean.setSeventeen(thirdWeek[2]);
										dailyReportBean.setEighteen(thirdWeek[3]);
										dailyReportBean.setNineteen(fourthWeek[4]);
										dailyReportBean.setTwenty(fourthWeek[5]);
										dailyReportBean.setTwentyone(fourthWeek[6]);
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
							kpiList.add(dailyReportBean);

							totalAchived += total;
							countFirst = 0;
							countSecond = 0;
							countThird = 0;
							countFourth = 0;
						}

					}

				}
			}

			userdata.put("remainng_target", remTarget);
			userdata.put("targetvalue", totalTarget);
			userdata.put("totalsale", totalAchived);
			userdata.put("kpiname", "Total :");
			setGridModelList(kpiList);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <getDSRRecords> of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Map<String, Object> getUserdata()
	{
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata)
	{
		this.userdata = userdata;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<DailyReportBean> getKpiList()
	{
		return kpiList;
	}

	public void setKpiList(List<DailyReportBean> kpiList)
	{
		this.kpiList = kpiList;
	}

	public List<DailyReportBean> getGridModelList()
	{
		return gridModelList;
	}

	public void setGridModelList(List<DailyReportBean> gridModelList)
	{
		this.gridModelList = gridModelList;
	}

}
