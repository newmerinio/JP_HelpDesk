package KPIReportHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.report.KPIReportHelper;
import com.Over2Cloud.ctrl.wfpm.report.KPIReportKPIPojo;
import com.Over2Cloud.ctrl.wfpm.report.KPIReportTotalPojo;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;

public class OfferingReportHelper
{
	public final String DES_ENCRYPTION_KEY = "ankitsin";

	public KPIReportTotalPojo getOfferingReportTotalPojoData(String userName,
	    SessionFactory connectionSpace, String targetMonth)
	{
		KPIReportTotalPojo object = new KPIReportTotalPojo();
		try
		{
			System.out.println("getOfferingReportTotalPojoData()");
			String uName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			// get table emp_basic id and name of an employee by employee userName
			String empId[] = new CommonWork().getEmpIdAndNameByUserName(userName,
			    connectionSpace);

			object.setId(Integer.parseInt(empId[0]));
			object.setEmpName(empId[1]);

			// String targetMonth = DateUtil.getCurrentDateMonthYear();

			// get table target data by empID and current month date
			List data = getTargetDataByEmpId(empId[0], targetMonth, connectionSpace);
			System.out.println("data.size()1:" + data.size());
			int totalTarget = 0;
			// Offering ids having target alloted for
			String targetOfferingIds = null;
			Map<String, Integer> offeringaAndTargetAllotedMAP = new HashMap<String, Integer>();

			if (data != null && data.size() > 0)
			{
				Object[] ob = (Object[]) data.get(0);
				String[] targetValue = ob[3].toString().split("#"); // target alloted
				targetOfferingIds = ob[2].toString().replaceAll("#", ","); // offering
				                                                           // ids having
				// target alloted
				// for

				targetOfferingIds = targetOfferingIds.substring(0,
				    targetOfferingIds.lastIndexOf(","));

				for (String s : targetValue)
				{
					if (s.trim() != null && !s.trim().equals(""))
					{
						totalTarget += Integer.parseInt(s.trim());
					}
				}

				String[] offeringIds = ob[2].toString().split("#");
				for (int j = 0; j < offeringIds.length; j++)
				{
					// Map containing KPI ids and target alloted
					offeringaAndTargetAllotedMAP.put(offeringIds[j], Integer
					    .parseInt((targetValue.length > j && !targetValue[j].trim()
					        .equals("")) ? targetValue[j] : "0"));
				}
			}

			object.setTotalTarget(String.valueOf(totalTarget));

			// Map containing kpi id and its total target achieved
			Map<String, Integer> offeringandtargetAchievedMAP = new HashMap<String, Integer>();
			int achieved = getTotalTargetAchievedByEmpId(userName, targetMonth,
			    connectionSpace, targetOfferingIds, offeringandtargetAchievedMAP);

			System.out.println("offeringandtargetAchievedMAP.size()"
			    + offeringandtargetAchievedMAP.size());

			object.setAchieved(String.valueOf(achieved));
			double achievedPercentage = (achieved > 0 && totalTarget > 0) ? (((double) achieved / (double) totalTarget) * 100)
			    : 0;

			object.setAchievedPercentage(String.valueOf(new DecimalFormat("0.00")
			    .format(achievedPercentage)));
			int remaining = totalTarget - achieved;

			object.setRemaining(String.valueOf(remaining));
			double remainingPercentage = (remaining > 0 && totalTarget > 0) ? (((double) remaining / (double) totalTarget) * 100)
			    : 0;

			object.setRemainingPercentage(String.valueOf(new DecimalFormat("0.00")
			    .format(remainingPercentage)));

			object.setIncentive(String.valueOf(getTotalIncentive(targetOfferingIds,
			    targetMonth, connectionSpace, offeringandtargetAchievedMAP,
			    offeringaAndTargetAllotedMAP, null)));

			object.setMonth(targetMonth);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return object;
	}

	private List getTargetDataByEmpId(String empId, String targetMonth,
	    SessionFactory connectionSpace)
	{
		List data = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			String query = " select id, empID, offeringId, targetvalue, targetMonth, userName, date, time from offeringtarget where empID='"
			    + empId + "' and targetMonth='" + targetMonth + "' ";
			System.out.println("query1:" + query);
			data = coi.executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}

	/*
	 * Get tatal target achieved of specified month
	 */
	private int getTotalTargetAchievedByEmpId(String userName,
	    String targetMonth, SessionFactory connectionSpace, String targetKPIIds,
	    Map<String, Integer> KPIandtargetAchieved)
	{
		System.out.println("userName:" + userName);
		int targetAchieved = 0;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List<String> totalFourWeekValue = null;
			System.out.println("targetKPIIds:" + targetKPIIds);

			String query = " select kpi_id, firstWeekReport, secondWeekReport, thirdWeekReport, fourthWeekReport "
			    + "from offeringdailyreport where user = '"
			    + userName
			    + "' and currentMonth = '"
			    + targetMonth
			    + "' "
			    + " and kpi_id in ("
			    + targetKPIIds + ") ";
			System.out.println("query:" + query);

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
						totalFourWeekValue = new ArrayList<String>(Arrays.asList(obj[1]
						    .toString().split("#")));
					}
					if (obj[2] != null)
						totalFourWeekValue.addAll(Arrays.asList(obj[2].toString()
						    .split("#")));
					if (obj[3] != null)
						totalFourWeekValue.addAll(Arrays.asList(obj[3].toString()
						    .split("#")));
					if (obj[4] != null)
						totalFourWeekValue.addAll(Arrays.asList(obj[4].toString()
						    .split("#")));

					for (String s : totalFourWeekValue)
					{
						count += (s != null && !s.equals("")) ? Integer.parseInt(s) : 0;
					}

					targetAchieved += count;
					System.out.println("Count:" + count);
					KPIandtargetAchieved.put(obj[0].toString(), count);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("targetAchieved:" + targetAchieved);
		return targetAchieved;
	}

	/*
	 * Get total incenive calculated
	 */
	public double getTotalIncentive(String kpiIds, String targetMonth,
	    SessionFactory connectionSpace,
	    Map<String, Integer> offeringandtargetAchievedMAP,
	    Map<String, Integer> offeringaAndTargetAllotedMAP,
	    Map<String, Integer> offeringAndIncentiveMAP)
	{
		double totalIncentive = 0;

		CommonOperInterface coi = new CommonConFactory().createInterface();
		String query = "select kpiId, slabFrom, slabTo, incentive from offeringincentive where kpiId in ("
		    + kpiIds + ") and month = '" + targetMonth + "'";
		System.out.println("query:" + query);
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
				if (offeringandtargetAchievedMAP.containsKey(obj[0].toString()))
				{
					target = offeringaAndTargetAllotedMAP.get(obj[0].toString());
					achieved = offeringandtargetAchievedMAP.get(obj[0].toString());
					if (target > 0 && achieved > 0)
					{
						percent = (achieved / target) * 100;

						// temp = KPIandtargetAchievedMAP.get(obj[0].toString());
						if (Integer.parseInt(obj[1].toString()) <= percent
						    && Integer.parseInt(obj[2].toString()) >= percent)
						{
							if (!tempList.contains(obj[0]))
							{
								if (offeringAndIncentiveMAP != null)
								{
									offeringAndIncentiveMAP.put(obj[0].toString(),
									    Integer.parseInt(obj[3].toString()));
								}
								totalIncentive += Double.parseDouble(obj[3].toString());
								System.out.println("totalIncentive:" + totalIncentive);
								tempList.add(obj[0].toString());
							}
						}
					}
				}

			}
		}

		System.out.println("totalIncentive:" + totalIncentive);
		return totalIncentive;
	}

	public List<KPIReportKPIPojo> getOfferingReportOfferingPojoData(String userName,
	    String empId, SessionFactory connectionSpace, String targetMonth, String offeringLevel)
	    throws Exception
	{
		List<KPIReportKPIPojo> offeringReportOfferingPojoLIST = new ArrayList<KPIReportKPIPojo>();

		// get table target data by empID and current month date
		//select id, empID, offeringId, targetvalue, targetMonth, userName, date, time from offeringtarget
		List data = getTargetDataByEmpId(empId, targetMonth, connectionSpace);
		System.out.println("data.size()1:" + data.size());
		int totalTarget = 0;
		// KPI ids having target alloted for
		String[] offeringIds = null;
		String[] targetValue = null;
		String targetOfferingIdsCommaSeparated = null;
		Map<String, Integer> offeringAndTargetAllotedMAP = new HashMap<String, Integer>();
		Map<String, Integer> offeringAndtargetAchievedMAP = new HashMap<String, Integer>();
		Map<String, Integer> offeringAndIncentiveMAP = new HashMap<String, Integer>();

		if (data != null && data.size() > 0)
		{
			Object[] ob = (Object[]) data.get(0);
			offeringIds = ob[2].toString().split("#");
			targetValue = ob[3].toString().split("#");
			targetOfferingIdsCommaSeparated = ob[2].toString().replaceAll("#", ",");
			targetOfferingIdsCommaSeparated = targetOfferingIdsCommaSeparated.substring(0,
					targetOfferingIdsCommaSeparated.lastIndexOf(","));

			for (int j = 0; j < offeringIds.length; j++)
			{
				// Map containing KPI ids and target alloted
				offeringAndTargetAllotedMAP.put(offeringIds[j], Integer
				    .parseInt((targetValue.length > j && !targetValue[j].trim().equals(
				        "")) ? targetValue[j] : "0"));
			}
		}

		int achieved = getTotalTargetAchievedByEmpId(userName, targetMonth,
		    connectionSpace, targetOfferingIdsCommaSeparated, offeringAndtargetAchievedMAP);

		double totalIncentive = getTotalIncentive(targetOfferingIdsCommaSeparated,
		    targetMonth, connectionSpace, offeringAndtargetAchievedMAP,
		    offeringAndTargetAllotedMAP, offeringAndIncentiveMAP);

		Map<String, String> offeringIdandNameMAP = getOfferingIdandNameByIds(
				targetOfferingIdsCommaSeparated, connectionSpace, offeringLevel);

		if (offeringAndTargetAllotedMAP != null && offeringAndTargetAllotedMAP.size() > 0)
		{
			String offeringId = null;
			int target;
			KPIReportKPIPojo object = null;

			for (Map.Entry<String, Integer> entry : offeringAndTargetAllotedMAP.entrySet())
			{
				object = new KPIReportKPIPojo();

				offeringId = entry.getKey();
				target = entry.getValue();
				if (offeringId != null && !offeringId.equals("") && target == 0)
					continue;

				object.setId(Integer.parseInt(offeringId));
				//object.setKpi(offeringIdandNameMAP.get(offeringId));
				object.setOffering(offeringIdandNameMAP.get(offeringId));
				object.setTargetAlloted(String.valueOf(target));

				int achieve = offeringAndtargetAchievedMAP.get(offeringId) != null ? offeringAndtargetAchievedMAP
				    .get(offeringId) : 0;
				object.setAchieved(String.valueOf(achieve));

				double achievedPercentage = ((double) achieve / (double) target) * 100;
				object.setAchievedPercentage(String.valueOf(achievedPercentage));

				int remaining = target - achieve;
				object.setRemaining(String.valueOf(remaining));

				double remainingPercentage = (remaining / (double) target) * 100;
				object.setRemainingPercentage(String.valueOf(remainingPercentage));

				int incentive = offeringAndIncentiveMAP.get(offeringId) != null ? offeringAndIncentiveMAP
				    .get(offeringId) : 0;
				object.setIncentive(String.valueOf(incentive));

				object.setMonth(targetMonth);

				offeringReportOfferingPojoLIST.add(object);
			}
		}

		return offeringReportOfferingPojoLIST;
	}

	public Map<String, String> getOfferingIdandNameByIds(
	    String targetOfferingIdsCommaSeparated, SessionFactory connectionSpace, String offeringLevel)
	    throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		
		int level = 0;
		String[] oLevels = null;
		if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) {
			oLevels = offeringLevel.split("#");
			level = Integer.parseInt(oLevels[0]);
		}

		String tableName = "", colName = "";

		if (level == 1) {
			tableName = "offeringLevel1";
			colName = "verticalname";
		}
		if (level == 2) {
			tableName = "offeringLevel2";
			colName = "offeringname";
		}
		if (level == 3) {
			tableName = "offeringLevel3";
			colName = "subofferingname";
		}
		if (level == 4) {
			tableName = "offeringLevel4";
			colName = "variantname";
		}
		if (level == 5) {
			tableName = "offeringlevel5";
			colName = "subvariantname";
		}
		
		String query = "select id, "+colName+" from "+tableName+" where id in("
		    + targetOfferingIdsCommaSeparated + ")";
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
	public Map<String, String> fetchAchievedOfferingForMonth(String empId, String date, SessionFactory connectionSpace)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			CommonHelper CH = new CommonHelper();
			String offeringDetail[] = CH.getOfferingName();
			StringBuilder query = new StringBuilder();
			query.append("SELECT offM.id, offM." + offeringDetail[0] + " FROM target_for_offering AS tfo INNER JOIN " + offeringDetail[1]
					+ " AS offM ON tfo.offeringId=offM.id WHERE empId='");
			query.append(empId);
			query.append("' AND tfo.applicableFrom LIKE '");
			query.append(date);
			query.append("%' GROUP BY offM.");
			query.append(offeringDetail[0]);
			query.append(" ORDER BY offM.");
			query.append(offeringDetail[0]);
			
			List list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			map = CommonHelper.convertListToMap(list,false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}

}
