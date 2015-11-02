package com.Over2Cloud.ctrl.wfpm.salary;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;

public class SalaryDao {
	static final Log log = LogFactory.getLog(SalaryAction.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName = (String) session.get("uName");
	private String accountID = (String) session.get("accountid");
	private SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	/**
	 * Get all information of all employees. *
	 */
	public List getAllEmployeeDetail(CommonOperInterface cbt, int from, int to) {
		String query = "select id,empName,mobOne,emailIdOne,salary,useraccountid from employee_basic "
				+ "where id in (select id from employee_basic) order by empName;";
		return cbt.executeAllSelectQuery(query, connectionSpace);
	}

	/**
	 * Get Empinfo and attendance info of employee
	 * */

	public List getEmployeeInfoAnd(CommonOperInterface cbt, String month) {
		String query = "select emp.id,emp.empName,emp.mobOne,emp.emailIdOne,attend.attendance,attend.month"
				+ " from employee_basic as emp left join attendanceinfo as attend"
				+ " on emp.mobOne=attend.mobOne and attend.month ='"+month+"' order by emp.empName asc";
		return cbt.executeAllSelectQuery(query, connectionSpace);
	}

	/**
	 * Get Slab detail of incentive by kpi
	 * */
	public List getIncentiveSlabByKPI(CommonOperInterface cbt, String kpiId) {
		return cbt.executeAllSelectQuery(
				"select slabFrom, slabTo, incentive from incentivedata where kpiId='"
						+ kpiId + "'", connectionSpace);
	}

	/**
	 * Get Slab detail of Incentive by offering
	 * */
	public List getOfferingIncentiveSlabByKPI(CommonOperInterface cbt,
			String kpiId) {
		return cbt.executeAllSelectQuery(
				"select slabFrom, slabTo, incentive from offeringincentive where kpiId='"
						+ kpiId + "'", connectionSpace);

	}

	/**
	 * Get employee attendance
	 * 
	 * @param cbt
	 * @param empId
	 * */
	public int getEmployeeAttandance(String mobileNo, CommonOperInterface cbt) {
		String query = "select attendance from attendanceinfo where mobOne='"
				+ mobileNo + "'";
		List attendanceList = cbt.executeAllSelectQuery(query, connectionSpace);
		String attendance = "";
		int attend = 0;
		try {
			if (attendanceList != null && attendanceList.size() > 0) {
				attendance = attendanceList.get(0).toString();
				// attendance =
				// Cryptography.decrypt(attendanceList.get(0).toString
				// (),DES_ENCRYPTION_KEY);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attend = (!attendance.equalsIgnoreCase("")) ? Integer
				.parseInt(attendance) : 0;

		return attend;
	}

	/**
	 * Get Salary of employee
	 * */
	public int getBasicSalary(String empId, CommonOperInterface cbt) {
		List list = cbt.executeAllSelectQuery(
				"select salary from employee_basic where id='" + empId + "'",
				connectionSpace);
		int salary = 0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (object != null) {
				salary = Integer.parseInt(object.toString());
			} else {
				salary = 0;
			}
		}
		return salary;
	}

	/**
	 * Get Salary configuration information
	 * */

	public List getSalaryConfigInfo(CommonOperInterface cbt) {
		return cbt
				.executeAllSelectQuery(
						"select holidays,allowedLeave,deduction,extraDayAmount from salaryconfiginfo where (SELECT MONTH('"
								+ DateUtil.getCurrentDateUSFormat() + "'));",
						connectionSpace);
	}

	/**
	 * Get total incentive
	 * 
	 * */
	public int getTotalIncentive(String empId, CommonOperInterface cbt) {
		List dsrData = null;
		boolean isOfferingTarget = false;
		boolean isKPITarget = false;
		Map<String, String> targetMap = new LinkedHashMap<String, String>();
		Map<String, String> offeringTargetMap = new LinkedHashMap<String, String>();
		int totalIncentive = 0;
		int offeringIncentive = 0;
		int kpiIncentive = 0;
		SalaryDao salaryDao = new SalaryDao();
		String query = "select offeringId from offeringtarget where empID='"
				+ empId + "'";
		List offeringTarget = cbt.executeAllSelectQuery(query.toString(),
				connectionSpace);

		query = "select KPIId from target where empID='" + empId + "'";
		List kpiTarget = cbt.executeAllSelectQuery(query.toString(),
				connectionSpace);

		if (offeringTarget.size() > 0) {
			isOfferingTarget = true;
		}

		if (kpiTarget.size() > 0) {
			isKPITarget = true;
		}

		if (isOfferingTarget) {
			offeringTargetMap = getTotalTargetOnOffering(empId, cbt);
			String offering = offeringTargetMap.get("offering").replaceAll("#",
					",");
			String target = offeringTargetMap.get("target")
					.replaceAll("#", ",");

			offering = offering.substring(0, offering.length() - 1);
			target = target.substring(0, target.length() - 1);

			List dsrReport = cbt
					.executeAllSelectQuery(
							"select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport, kpi_id from offeringdailyreport where kpi_id in("
									+ offering + ") ", connectionSpace);
			String firstWeekReport[] = null;
			String secondWeekReport[] = null;
			String thirtWeekReport[] = null;
			String fourthWeekReport[] = null;

			Map<String, Integer> achiveTargetMap = new LinkedHashMap<String, Integer>();
			Map<String, String> totalTargetMap = new LinkedHashMap<String, String>();
			int targetAhived = 0;
			List incentiveSlabList = new ArrayList();
			int slabFrom = 0;
			int slabTo = 0;
			int incentive = 0;
			int achiveTarget = 0;
			for (Iterator iterator = dsrReport.iterator(); iterator.hasNext();) {
				targetAhived = 0;
				Object object[] = (Object[]) iterator.next();

				firstWeekReport = object[0].toString().split("#");
				secondWeekReport = object[1].toString().split("#");
				thirtWeekReport = object[2].toString().split("#");
				fourthWeekReport = object[3].toString().split("#");

				for (int i = 0; i < firstWeekReport.length; i++) {
					targetAhived += Integer.parseInt(firstWeekReport[i].trim());
				}

				for (int i = 0; i < secondWeekReport.length; i++) {
					targetAhived += Integer
							.parseInt(secondWeekReport[i].trim());
				}
				for (int i = 0; i < thirtWeekReport.length; i++) {
					targetAhived += Integer.parseInt(thirtWeekReport[i].trim());
				}
				for (int i = 0; i < fourthWeekReport.length; i++) {
					targetAhived += Integer
							.parseInt(fourthWeekReport[i].trim());
				}
				achiveTargetMap.put(object[4].toString(), targetAhived);
			}
			totalTargetMap = getTargetArray(offeringTargetMap.get("offering"),
					offeringTargetMap.get("target"));

			for (Map.Entry<String, Integer> entry : achiveTargetMap.entrySet()) {
				achiveTarget = achiveTargetMap.get(entry.getKey());
				incentiveSlabList = salaryDao.getOfferingIncentiveSlabByKPI(
						cbt, entry.getKey());
				for (Iterator iterator = incentiveSlabList.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					slabFrom = Integer.parseInt(object[0].toString().trim());
					slabTo = Integer.parseInt(object[1].toString().trim());
					incentive = Integer.parseInt(object[2].toString().trim());

					if (achiveTarget >= slabFrom && achiveTarget <= slabTo) {
						offeringIncentive += incentive;
					}

				}
			}
		}

		if (isKPITarget) {
			targetMap = getTotalTarget(empId, cbt);
			dsrData = new ArrayList();
			String kpi = targetMap.get("kpi").replaceAll("#", ",");
			String target = targetMap.get("target").replaceAll("#", ",");

			kpi = kpi.substring(0, kpi.length() - 1);
			target = target.substring(0, target.length() - 1);

			List dsrReport = cbt
					.executeAllSelectQuery(
							"select firstWeekReport,secondWeekReport,thirdWeekReport,fourthWeekReport, kpi_id from dailyreport where kpi_id in("
									+ kpi + ") ", connectionSpace);
			String firstWeekReport[] = null;
			String secondWeekReport[] = null;
			String thirtWeekReport[] = null;
			String fourthWeekReport[] = null;

			Map<String, Integer> achiveTargetMap = new LinkedHashMap<String, Integer>();
			Map<String, String> totalTargetMap = new LinkedHashMap<String, String>();
			int targetAhived = 0;
			List incentiveSlabList = new ArrayList();
			int slabFrom = 0;
			int slabTo = 0;
			int incentive = 0;
			int achiveTarget = 0;
			for (Iterator iterator = dsrReport.iterator(); iterator.hasNext();) {
				targetAhived = 0;
				Object object[] = (Object[]) iterator.next();

				firstWeekReport = object[0].toString().split("#");
				secondWeekReport = object[1].toString().split("#");
				thirtWeekReport = object[2].toString().split("#");
				fourthWeekReport = object[3].toString().split("#");

				for (int i = 0; i < firstWeekReport.length; i++) {
					targetAhived += Integer.parseInt(firstWeekReport[i].trim());
				}

				for (int i = 0; i < secondWeekReport.length; i++) {
					targetAhived += Integer
							.parseInt(secondWeekReport[i].trim());
				}
				for (int i = 0; i < thirtWeekReport.length; i++) {
					targetAhived += Integer.parseInt(thirtWeekReport[i].trim());
				}
				for (int i = 0; i < fourthWeekReport.length; i++) {
					targetAhived += Integer
							.parseInt(fourthWeekReport[i].trim());
				}
				achiveTargetMap.put(object[4].toString(), targetAhived);
			}
			totalTargetMap = getTargetArray(targetMap.get("kpi"), targetMap
					.get("target"));

			for (Map.Entry<String, Integer> entry : achiveTargetMap.entrySet()) {
				achiveTarget = achiveTargetMap.get(entry.getKey());
				incentiveSlabList = salaryDao.getIncentiveSlabByKPI(cbt, entry
						.getKey());
				for (Iterator iterator = incentiveSlabList.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					slabFrom = Integer.parseInt(object[0].toString().trim());
					slabTo = Integer.parseInt(object[1].toString().trim());
					incentive = Integer.parseInt(object[2].toString().trim());

					if (achiveTarget >= slabFrom && achiveTarget <= slabTo) {
						kpiIncentive += incentive;
					}

				}
			}
		}
		totalIncentive = offeringIncentive + kpiIncentive;
		return totalIncentive;
	}

	/**
	 * Get total KPI target of employee
	 * */
	private Map<String, String> getTotalTarget(String empID,
			CommonOperInterface cbt) {
		List data = cbt.executeAllSelectQuery(
				"select KPIId,targetvalue from target where empID='" + empID
						+ "'", connectionSpace);
		String kpi = "";
		String target = "";

		Map<String, String> targetMap = new LinkedHashMap<String, String>();
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			kpi = object[0].toString();
			target = object[1].toString();
		}

		targetMap.put("kpi", kpi);
		targetMap.put("target", target);

		return targetMap;
	}

	/**
	 * Get target on offering
	 * */
	private Map<String, String> getTotalTargetOnOffering(String empID,
			CommonOperInterface cbt) {
		List data = cbt.executeAllSelectQuery(
				"select offeringId, targetvalue from offeringtarget where empID='"
						+ empID + "'", connectionSpace);
		String kpi = "";
		String target = "";

		Map<String, String> targetMap = new LinkedHashMap<String, String>();
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			kpi = object[0].toString();
			target = object[1].toString();
		}

		targetMap.put("offering", kpi);
		targetMap.put("target", target);

		return targetMap;
	}

	/**
	 * Get target in array on the basis of kpi & target
	 * */
	private Map<String, String> getTargetArray(String kpi, String target) {
		String kpiArray[] = null;
		String targetArray[] = null;
		Map<String, String> targetMap = new LinkedHashMap<String, String>();

		if (kpi != null && !kpi.equalsIgnoreCase("")) {
			kpiArray = kpi.split("#");
			targetArray = target.split("#");
		}
		for (int i = 0; i < targetArray.length; i++) {
			targetMap.put(kpiArray[i], targetArray[i]);
		}

		return targetMap;
	}

	/**
	 * Get get employee detail by given empId.
	 * */
	public List getEmployeeDetail(CommonOperInterface cbt, int empId) {

		String query = "select id,empName,mobOne,emailIdOne,salary,useraccountid from employee_basic "
				+ "where id='" + empId + "' order by empName;";
		return cbt.executeAllSelectQuery(query, connectionSpace);
	}

}
