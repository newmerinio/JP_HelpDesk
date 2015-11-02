package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DSRGraphActionControl extends ActionSupport
{
	static final Log		log				= LogFactory.getLog(DSRGraphActionControl.class);
	private Map					session		= ActionContext.getContext().getSession();
	private String			userName	= (String) session.get("uName");
	private String			userID;
	private JSONObject	jsonObject;
	private JSONArray		jsonArray;

	public String beforeDashboardView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

			if (getUserID() == null || getUserID().equalsIgnoreCase("")) setUserID(userName);
			String empId = (String)session.get("empIdofuser").toString().split("-")[1];
			if (empId == null) return ERROR;

			String currentMonth = "";
			int month = DateUtil.getCurrentMonth();
			currentMonth = month + "-" + DateUtil.getCurretnYear();
			if (month < 10)
			{
				currentMonth = "0" + month + "-" + DateUtil.getCurretnYear();
			}
			String query = "select KPIId, targetvalue from target where empID=" + empId + " and targetMonth='" + currentMonth + "'";
			List kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);
			String kpi = "";
			String targetValue = "";
			jsonArray = new JSONArray();
			if (kpiIds != null)
			{
				if (kpiIds.size() > 0)
				{
					for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						kpi = object[0].toString();
						targetValue = object[1].toString();
					}
					kpi = kpi.replaceAll("#", ",");
					kpi = kpi.substring(0, kpi.length() - 1);
					List kpiLists = cbt.executeAllSelectQuery("select  kpi from krakpicollection where id in (" + kpi + ")", connectionSpace);
					String target[] = targetValue.split("#");
					if (kpiLists != null)
					{
						List targetInfo = new ArrayList();
						int total = 0;
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
							query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
									+ " from dailyreport where kpi_id IN (select id from krakpicollection where kpi='" + kpiLists.get(i) + "') and currentMonth='" + currentMonth
									+ "' and user='" + userName + "'";

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
								}
							}
							total = countFirst + countSecond + countThird + countFourth;
							jsonObject = new JSONObject();
							jsonObject.put("name", kpiLists.get(i).toString());
							jsonObject.put("target", target[i]);
							jsonObject.put("achivement", total);
							jsonArray.add(jsonObject);
							countFirst = 0;
							countSecond = 0;
							countThird = 0;
							countFourth = 0;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeOfferingDashboardView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getOfferingJsonChartData()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;
			String tableName = "", colName = "";
			int level = 0;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

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

			if (getUserID() == null || getUserID().equalsIgnoreCase("")) setUserID(userName);
			String empId =(String)session.get("empIdofuser").toString().split("-")[1];
			if (empId == null) return ERROR;
			String currentMonth = "";
			int month = DateUtil.getCurrentMonth();
			currentMonth = month + "-" + DateUtil.getCurretnYear();
			if (month < 10)
			{
				currentMonth = "0" + month + "-" + DateUtil.getCurretnYear();
			}

			String query = "select offeringId, targetvalue from offeringtarget where empID=" + empId + " and targetMonth='" + currentMonth + "'";
			List kpiIds = cbt.executeAllSelectQuery(query, connectionSpace);

			String kpi = "";
			String targetValue = "";
			jsonArray = new JSONArray();
			if (kpiIds != null)
			{
				if (kpiIds.size() > 0)
				{
					for (Iterator iterator = kpiIds.iterator(); iterator.hasNext();)
					{
						Object object[] = (Object[]) iterator.next();
						kpi = object[0].toString();
						targetValue = object[1].toString();
					}
					kpi = kpi.replaceAll("#", ",");
					kpi = kpi.substring(0, kpi.length() - 1);
					List kpiLists = cbt.executeAllSelectQuery("select " + colName + " from " + tableName + " where id in (" + kpi + ")", connectionSpace);
					String target[] = targetValue.split("#");
					if (kpiLists != null)
					{
						List targetInfo = new ArrayList();
						int total = 0;
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
							query = "select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport,currentMonth"
									+ " from offeringdailyreport where kpi_id in (select id from " + tableName + " where id='" + kpiId[i] + "') and currentMonth='"
									+ currentMonth + "' and user='" + userName + "'";

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
								}
							}
							total = countFirst + countSecond + countThird + countFourth;
							jsonObject = new JSONObject();
							jsonObject.put("name", kpiLists.get(i).toString());
							jsonObject.put("target", target[i]);
							jsonObject.put("achivement", total);
							jsonObject.put("interval", "5");
							jsonArray.add(jsonObject);
							countFirst = 0;
							countSecond = 0;
							countThird = 0;
							countFourth = 0;
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}
}
