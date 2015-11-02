package com.Over2Cloud.ctrl.wfpm.report;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Sessionfactor;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;

public class KPIReportHelper
{
	public final String	DES_ENCRYPTION_KEY	= "ankitsin";

	private List getTargetDataByEmpId(String empId, String targetMonth, SessionFactory connectionSpace)
	{
		List data = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String query = " select id, empID, KPIId, targetvalue, targetMonth, userName, date, time from target where empID='" + empId + "' and targetMonth='"
					+ targetMonth + "' ";
			// System.out.println("query1:" + query);
			data = coi.executeAllSelectQuery(query, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	public KPIReportTotalPojo getKPIReportTotalPojoData(String userName, SessionFactory connectionSpace, String targetMonth)
	{
		KPIReportTotalPojo object = new KPIReportTotalPojo();
		try
		{
			// System.out.println("getKPIReportTotalPojoData()");
			String uName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			// get table emp_basic id and name of an employee by employee userName
			String empId[] = new CommonWork().getEmpIdAndNameByUserName(userName, connectionSpace);

			object.setId(Integer.parseInt(empId[0]));
			object.setEmpName(empId[1]);

			// String targetMonth = DateUtil.getCurrentDateMonthYear();

			// get table target data by empID and current month date
			List data = getTargetDataByEmpId(empId[0], targetMonth, connectionSpace);
			// System.out.println("data.size()1:" + data.size());
			int totalTarget = 0;
			// KPI ids having target alloted for
			String targetKPIIds = null;
			Map<String, Integer> KPIandTargetAllotedMAP = new HashMap<String, Integer>();

			if (data != null && data.size() > 0)
			{
				Object[] ob = (Object[]) data.get(0);
				String[] targetValue = ob[3].toString().split("#"); // target alloted
				targetKPIIds = ob[2].toString().replaceAll("#", ","); // KPI ids having
				// target alloted
				// for

				targetKPIIds = targetKPIIds.substring(0, targetKPIIds.lastIndexOf(","));

				for (String s : targetValue)
				{
					if (s.trim() != null && !s.trim().equals(""))
					{
						totalTarget += Integer.parseInt(s.trim());
					}
				}

				String[] KPIIds = ob[2].toString().split("#");
				for (int j = 0; j < KPIIds.length; j++)
				{
					// Map containing KPI ids and target alloted
					KPIandTargetAllotedMAP.put(KPIIds[j], Integer.parseInt((targetValue.length > j && !targetValue[j].trim().equals("")) ? targetValue[j] : "0"));
				}
			}

			object.setTotalTarget(String.valueOf(totalTarget));

			// Map containing kpi id and its total target achieved
			Map<String, Integer> KPIandtargetAchievedMAP = new HashMap<String, Integer>();
			int achieved = getTotalTargetAchievedByEmpId(userName, targetMonth, connectionSpace, targetKPIIds, KPIandtargetAchievedMAP);

			object.setAchieved(String.valueOf(achieved));
			double achievedPercentage = (achieved > 0 && totalTarget > 0) ? (((double) achieved / (double) totalTarget) * 100) : 0;

			object.setAchievedPercentage(String.valueOf(new DecimalFormat("0.00").format(achievedPercentage)));
			int remaining = totalTarget - achieved;

			object.setRemaining(String.valueOf(remaining));
			double remainingPercentage = (remaining > 0 && totalTarget > 0) ? (((double) remaining / (double) totalTarget) * 100) : 0;

			object.setRemainingPercentage(String.valueOf(new DecimalFormat("0.00").format(remainingPercentage)));

			object.setIncentive(String.valueOf(getTotalIncentive(targetKPIIds, targetMonth, connectionSpace, KPIandtargetAchievedMAP, KPIandTargetAllotedMAP, null)));

			object.setMonth(targetMonth);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * Get total incenive calculated
	 */
	public double getTotalIncentive(String kpiIds, String targetMonth, SessionFactory connectionSpace, Map<String, Integer> KPIandtargetAchievedMAP,
			Map<String, Integer> KPIandTargetAllotedMAP, Map<String, Integer> KPIandIncentiveMAP)
	{
		double totalIncentive = 0;

		CommonOperInterface coi = new CommonConFactory().createInterface();
		String query = "select kpiId, slabFrom, slabTo, incentive from incentivedata where kpiId in (" + kpiIds + ") and month = '" + targetMonth + "'";
		// System.out.println("query:" + query);
		List data = coi.executeAllSelectQuery(query, connectionSpace);
		if (data != null && data.size() > 0)
		{
			double target = 0, achieved = 0, percent = 0;
			// tempList is to make sure that same slab incentive does not gets added 2
			// times
			ArrayList<String> tempList = new ArrayList<String>();
			for (Object objData : data)
			{
				Object[] obj = (Object[]) objData;
				if (KPIandtargetAchievedMAP.containsKey(obj[0].toString()))
				{
					target = KPIandTargetAllotedMAP.get(obj[0].toString());
					achieved = KPIandtargetAchievedMAP.get(obj[0].toString());
					if (target > 0 && achieved > 0)
					{
						percent = (achieved / target) * 100;

						// temp = KPIandtargetAchievedMAP.get(obj[0].toString());
						if (Integer.parseInt(obj[1].toString()) <= percent && Integer.parseInt(obj[2].toString()) >= percent)
						{
							if (!tempList.contains(obj[0]))
							{
								if (KPIandIncentiveMAP != null)
								{
									KPIandIncentiveMAP.put(obj[0].toString(), Integer.parseInt(obj[3].toString()));
								}
								totalIncentive += Double.parseDouble(obj[3].toString());
								// System.out.println("totalIncentive:" + totalIncentive);
								tempList.add(obj[0].toString());
							}
						}
					}
				}

			}
		}

		// System.out.println("totalIncentive:" + totalIncentive);
		return totalIncentive;
	}

	/*
	 * Get tatal target achieved of specified month
	 */
	private int getTotalTargetAchievedByEmpId(String userName, String targetMonth, SessionFactory connectionSpace, String targetKPIIds,
			Map<String, Integer> KPIandtargetAchieved)
	{
		// System.out.println("userName:" + userName);
		int targetAchieved = 0;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List<String> totalFourWeekValue = null;
			// System.out.println("targetKPIIds:" + targetKPIIds);

			String query = " select kpi_id, firstWeekReport, secondWeekReport, thirdWeekReport, fourthWeekReport from dailyreport where user = '" + userName
					+ "' and currentMonth = '" + targetMonth + "' " + " and kpi_id in (" + targetKPIIds + ") ";
			// System.out.println("query:" + query);

			List data = coi.executeAllSelectQuery(query, connectionSpace);

			if (data != null && data.size() > 0)
			{
				int m = 0;
				for (Object objData : data)
				{
					// 4 5 8 9
					int count = 0;
					Object[] obj = (Object[]) objData;
					if (obj[1] != null)
					{
						totalFourWeekValue = new ArrayList<String>(Arrays.asList(obj[1].toString().split("#")));
					}
					if (obj[2] != null) totalFourWeekValue.addAll(Arrays.asList(obj[2].toString().split("#")));
					if (obj[3] != null) totalFourWeekValue.addAll(Arrays.asList(obj[3].toString().split("#")));
					if (obj[4] != null) totalFourWeekValue.addAll(Arrays.asList(obj[4].toString().split("#")));

					for (String s : totalFourWeekValue)
					{
						count += (s != null && !s.equals("")) ? Integer.parseInt(s) : 0;
					}

					targetAchieved += count;
					// System.out.println("Count:" + count);
					KPIandtargetAchieved.put(obj[0].toString(), count);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// System.out.println("targetAchieved:" + targetAchieved);
		return targetAchieved;
	}

	public List<KPIReportKPIPojo> getKPIReportKPIPojoData(String userName, String empId, SessionFactory connectionSpace, String targetMonth) throws Exception
	{
		List<KPIReportKPIPojo> KPIReportKPIPojoLIST = new ArrayList<KPIReportKPIPojo>();

		// get table target data by empID and current month date
		List data = getTargetDataByEmpId(empId, targetMonth, connectionSpace);
		// System.out.println("data.size()1:" + data.size());
		int totalTarget = 0;
		// KPI ids having target alloted for
		String[] KPIIds = null;
		String[] targetValue = null;
		String targetKPIIdsCommaSeparated = null;
		Map<String, Integer> KPIandTargetAllotedMAP = new HashMap<String, Integer>();
		Map<String, Integer> KPIandtargetAchievedMAP = new HashMap<String, Integer>();
		Map<String, Integer> KPIandIncentiveMAP = new HashMap<String, Integer>();

		if (data != null && data.size() > 0)
		{
			Object[] ob = (Object[]) data.get(0);
			KPIIds = ob[2].toString().split("#");
			targetValue = ob[3].toString().split("#");
			targetKPIIdsCommaSeparated = ob[2].toString().replaceAll("#", ",");
			targetKPIIdsCommaSeparated = targetKPIIdsCommaSeparated.substring(0, targetKPIIdsCommaSeparated.lastIndexOf(","));

			for (int j = 0; j < KPIIds.length; j++)
			{
				// Map containing KPI ids and target alloted
				KPIandTargetAllotedMAP.put(KPIIds[j], Integer.parseInt((targetValue.length > j && !targetValue[j].trim().equals("")) ? targetValue[j] : "0"));
			}
		}

		int achieved = getTotalTargetAchievedByEmpId(userName, targetMonth, connectionSpace, targetKPIIdsCommaSeparated, KPIandtargetAchievedMAP);

		double totalIncentive = getTotalIncentive(targetKPIIdsCommaSeparated, targetMonth, connectionSpace, KPIandtargetAchievedMAP, KPIandTargetAllotedMAP,
				KPIandIncentiveMAP);

		Map<String, String> KPIIdandNameMAP = getKPIIdandNameByIds(targetKPIIdsCommaSeparated, connectionSpace);

		if (KPIandTargetAllotedMAP != null && KPIandTargetAllotedMAP.size() > 0)
		{
			String kpiId = null;
			int target;
			KPIReportKPIPojo object = null;

			for (Map.Entry<String, Integer> entry : KPIandTargetAllotedMAP.entrySet())
			{
				object = new KPIReportKPIPojo();

				kpiId = entry.getKey();
				target = entry.getValue();
				if (kpiId != null && !kpiId.equals("") && target == 0) continue;

				object.setId(Integer.parseInt(kpiId));
				object.setKpi(KPIIdandNameMAP.get(kpiId));
				object.setTargetAlloted(String.valueOf(target));

				int achieve = KPIandtargetAchievedMAP.get(kpiId) != null ? KPIandtargetAchievedMAP.get(kpiId) : 0;
				object.setAchieved(String.valueOf(achieve));

				double achievedPercentage = ((double) achieve / (double) target) * 100;
				object.setAchievedPercentage(String.valueOf(achievedPercentage));

				int remaining = target - achieve;
				object.setRemaining(String.valueOf(remaining));

				double remainingPercentage = (remaining / (double) target) * 100;
				object.setRemainingPercentage(String.valueOf(remainingPercentage));

				int incentive = KPIandIncentiveMAP.get(kpiId) != null ? KPIandIncentiveMAP.get(kpiId) : 0;
				object.setIncentive(String.valueOf(incentive));

				object.setMonth(targetMonth);

				KPIReportKPIPojoLIST.add(object);
			}
		}

		return KPIReportKPIPojoLIST;
	}

	public Map<String, String> getKPIIdandNameByIds(String targetKPIIdsCommaSeparated, SessionFactory connectionSpace) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		String query = "select id,kpi from krakpicollection where id in(" + targetKPIIdsCommaSeparated + ")";
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Object objData : data)
			{
				Object[] obj = (Object[]) objData;
				if (obj[1] != null)
				{
					map.put(obj[0].toString(), obj[1].toString());
				}
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> fetchAchievedKPIForMonth(String empId, String date, SessionFactory connectionSpace)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT kpi.kpiId,kpiM.kpi FROM kpi_autofill AS kpi INNER JOIN krakpicollection AS kpiM ON kpi.kpiId=kpiM.id  WHERE empId='");
			query.append(empId);
			query.append("' AND kpi.date LIKE '");
			query.append(date);
			query.append("%' GROUP BY kpiM.kpi ORDER BY kpiM.kpi ");
			List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			map = CommonHelper.convertListToMap(list,false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}

	public String getNumericalDate(String value)
	{
		try
		{
			if (value == null || value.equals("")) return DateUtil.getCurrentDateUSFormat();

			String[] temp = value.trim().split("-"); // May-2014
			if (temp.length > 2) return value;

			SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-yyyy");
			Date date = sdf1.parse(value);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
		catch (ParseException e)
		{
			return DateUtil.getCurrentDateUSFormat();
		}
	}

	
	public String getStringDate(String value)//
	{
		try
		{
			SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-yyyy");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// return sdf.format(date);

			Date date = null;
			if (value == null || value.equals(""))
			{
				return sdf.format(new Date());
			}
			return sdf.format(date);
		}
		catch (Exception e)
		{
			return DateUtil.getCurrentDateUSFormat();
		}
	}

	@SuppressWarnings("unchecked")
	public String fetchLastTargetMonthByEmpId(String empId, String month, SessionFactory connectionSpace, TargetType targetType)
	{
		String targetMonth = month;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (targetType == TargetType.KPI)
			{
				query.append("SELECT applicableFrom FROM target_for_kpi WHERE empId='");
				query.append(empId);
				query.append("' AND applicableFrom <= '");
				query.append(month);
				query.append("' order by applicableFrom desc limit 0,1");
			}
			else if (targetType == TargetType.OFFERING)
			{
				query.append("SELECT applicableFrom FROM target_for_offering WHERE empId='");
				query.append(empId);
				query.append("' AND applicableFrom <= '");
				query.append(month);
				query.append("' order by applicableFrom desc limit 0,1");
			}

			List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			targetMonth = data.get(0).toString().trim();
		}
		catch (Exception e)
		{
			targetMonth = month;
			e.printStackTrace();
		}
		return targetMonth;
	}

}
