package com.Over2Cloud.ctrl.wfpm.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;

public class ReportDao {

	public List getAllRecordByKeyword(String keyword) {
		List templateData = new ArrayList();
		try {
			SessionFactory connectionSpace = new ConnectionHelper()
					.getSessionFactory("IN-8");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select id,length,paramName,shortCode,validation, revertMessage from smstemplate where keyword='"
					+ keyword + "'";
			templateData = cbt.executeAllSelectQuery(query, connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateData;
	}

	public List getAllOfferingRecordByKeyword(String keyword) {
		List templateData = new ArrayList();
		try {
			SessionFactory connectionSpace = new ConnectionHelper()
			.getSessionFactory("IN-8");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select id,length,paramName,shortCode,validation, revertMessage from smstemplateoffering where keyword='"
					+ keyword + "'";
			templateData = cbt.executeAllSelectQuery(query, connectionSpace);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateData;
	}

	public boolean filDSRByEmp(String date, int empId, DSRMode mode, String id,
			int val, String userName, String comment, DSRType dsrType) {
		SessionFactory connectionSpace = new ConnectionHelper()
		.getSessionFactory("IN-8");
		
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		int day = 0;

		if (date != null && !date.equalsIgnoreCase("")) {
			day = Integer.parseInt(DateUtil.getDate(date, "DD"));
		} else {
			day = cal.get(Calendar.DAY_OF_MONTH);
		}

		boolean firstWeek = false;
		boolean secondWeek = false;
		boolean thirdWeek = false;
		boolean fourthWeek = false;

		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;

		String firstWeekReport = "0#0#0#0#0#0#0#";
		String secondWeekReport = "0#0#0#0#0#0#0#";
		String thirdWeekReport = "0#0#0#0#0#0#0#";
		String fourthWeekReport = "0#0#0#0#0#0#0#0#0#0#";

		Calendar calendar = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		List dsrHistoryList = new ArrayList();
		List<TableColumes> tableColumn = new ArrayList<TableColumes>();
		List<String> columnName = new ArrayList<String>();
		boolean recordSaved = false;
		// columnName.add("id");
		columnName.add("user");
		columnName.add("currentDate");
		columnName.add("currentMonth");
		columnName.add("firstWeekReport");
		columnName.add("fourthWeekReport");

		columnName.add("kpi_id");
		columnName.add("kpi_target");

		columnName.add("secondWeekReport");
		columnName.add("thirdWeekReport");
		columnName.add("targetPeriodFlag");
		columnName.add("comment");
		columnName.add("mode");
		// columnName.add("historyIds");

		for (String cloumn : columnName) {
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname(cloumn);
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);
		}

		boolean status = false;
		String tableName = "";
		if (mode == DSRMode.OFFERING) {
			tableName = "offeringDailyReport";
			status = cbt.createTable22("offeringDailyReport", tableColumn,
					connectionSpace);
		} else if (mode == DSRMode.KPI) {
			tableName = "dailyreport";
			status = cbt.createTable22("dailyreport", tableColumn,
					connectionSpace);
		}

		if (days == 28) {
			fourthWeekReport = "0#0#0#0#0#0#0#";
		} else if (days == 29) {
			fourthWeekReport = "0#0#0#0#0#0#0#0#";
		} else if (days == 30) {
			fourthWeekReport = "0#0#0#0#0#0#0#0#0#";
		} else if (days == 31) {
			fourthWeekReport = "0#0#0#0#0#0#0#0#0#0#";
		}

		if (day >= 1 && day <= 7) {
			firstWeek = true;
		} else if (day >= 8 && day <= 14) {
			secondWeek = true;
		} else if (day >= 15 && day <= 21) {
			thirdWeek = true;
		} else {
			fourthWeek = true;
		}
		List report = null;
		String week = "";
		if (firstWeek) {
			week = "firstWeekReport";
			String query = "select firstWeekReport from " + tableName
					+ " where kpi_id=" + id + " and user = '" + userName
					+ "' and currentMonth = '" + getCurrentMonthYear() + "'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		} else if (secondWeek) {
			week = "secondWeekReport";
			String query = "select secondweekreport from " + tableName
					+ " where kpi_id=" + id + " and user = '" + userName
					+ "' and currentMonth = '" + getCurrentMonthYear() + "'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		} else if (thirdWeek) {
			week = "thirdWeekReport";
			String query = "select thirdWeekReport from " + tableName
					+ " where kpi_id=" + id + " and user = '" + userName
					+ "' and currentMonth = '" + getCurrentMonthYear() + "'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		} else {
			week = "fourthWeekReport";
			String query = "select fourthWeekReport from " + tableName
					+ " where kpi_id=" + id + " and user = '" + userName
					+ "' and currentMonth = '" + getCurrentMonthYear() + "'";
			report = cbt.executeAllSelectQuery(query, connectionSpace);
		}
		insertData = new ArrayList<InsertDataTable>();
		ob = new InsertDataTable();
		ob.setColName("user");
		ob.setDataName(userName);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("currentDate");
		ob.setDataName(DateUtil.getCurrentDateUSFormat());
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("currentMonth");
		// ob.setDataName(months[DateUtil.getCurrentMonth()-1]+"-"+
		// DateUtil.getCurretnYear());
		ob.setDataName(getCurrentMonthYear());
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("targetPeriodFlag");
		ob.setDataName("M");
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("kpi_id");
		ob.setDataName(id);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("mode");
		ob.setDataName((dsrType == DSRType.SMS) ? "SMS" : "ONLINE");
		insertData.add(ob);

		/**
		 * for set target value in kpi_target column in dailyreport table.
		 * */
		Map<String, Object> wherClause = new HashMap<String, Object>();
		Map<String, Object> condtnBlock = new HashMap<String, Object>();

		String query12 = "";
		if (mode == DSRMode.OFFERING) {
			query12 = "select offeringId,targetvalue from offeringtarget where empID='"
					+ empId
					+ "' and targetMonth = '"
					+ getCurrentMonthYear()
					+ "'";
		} else if (mode == DSRMode.KPI) {
			query12 = "select KPIId,targetvalue from target where empID='"
					+ empId + "' and targetMonth = '" + getCurrentMonthYear()
					+ "'";

		}
		List list = cbt.executeAllSelectQuery(query12, connectionSpace);
		String kpi = "";
		String targetValue = "";
		boolean productAlloted = false;
		if (list != null) {
			for (Object c : list) {
				Object[] data = (Object[]) c;
				kpi = data[0].toString();
				targetValue = data[1].toString();
			}
			String s[] = kpi.split("#");
			String x[] = targetValue.split("#");

			for (int i = 0; i < s.length; i++) {
				if (s[i].equalsIgnoreCase(id)) {
					targetValue = x[i];
					ob = new InsertDataTable();
					ob.setColName("kpi_target");
					ob.setDataName(targetValue);
					insertData.add(ob);
					productAlloted = true;
					break;
				}
			}
		}

		if (productAlloted) {
			if (report != null && report.size() > 0) {
				String weekDays = report.toString();
				if (weekDays != null) {
					weekDays = weekDays.substring(1, weekDays.length() - 1);
					String s[] = weekDays.split("#");
					int index = 0;
					if (firstWeek) {
						index = day - 1;
					} else if (secondWeek) {
						index = day - 8;
					} else if (thirdWeek) {
						index = day - 15;
					} else {
						index = day - 22;
					}
					// increment in target achieved in week day
					s[index] = String.valueOf(Integer.parseInt(s[index]) + val);

					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < s.length; j++) {
						stringBuffer.append(s[j] + "#");
					}
					wherClause.put(week, stringBuffer.toString());
					wherClause.put("currentDate",
							DateUtil.getCurrentDateUSFormat());
					wherClause.put("kpi_target", targetValue);
					wherClause.put("comment", comment);
					condtnBlock.put("kpi_id", "'" + id + "'");
					condtnBlock.put("user", "'" + userName + "'");
					condtnBlock.put("currentMonth", "'" + getCurrentMonthYear()
							+ "'");

					String query = "select * from dailyreport where kpi_id='"
							+ id + "'";
					dsrHistoryList = cbt.executeAllSelectQuery(query,
							connectionSpace);

					cbt.createTable22("dsrHistory", tableColumn,
							connectionSpace);
					for (Iterator iterator = dsrHistoryList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();

						insertData = new ArrayList<InsertDataTable>();
						ob = new InsertDataTable();
						ob.setColName("user");
						ob.setDataName(object[1]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("currentDate");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("currentMonth");
						ob.setDataName(getCurrentMonthYear());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("targetPeriodFlag");
						ob.setDataName("M");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("kpi_id");
						ob.setDataName(id);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("firstWeekReport");
						ob.setDataName(object[4]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("secondWeekReport");
						ob.setDataName(object[8]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("thirdWeekReport");
						ob.setDataName(object[9]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("fourthWeekReport");
						ob.setDataName(object[5]);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("comment");
						ob.setDataName(object[11]);
						insertData.add(ob);

						cbt.insertIntoTable("dsrHistory", insertData,
								connectionSpace);
					}
					recordSaved = cbt.updateTableColomn(tableName, wherClause,
							condtnBlock, connectionSpace);
				}
			} else {
				int index = 0;
				if (firstWeek) {
					String s[] = firstWeekReport.split("#");
					index = Math.abs(day - 1);
					s[index] = String.valueOf(Integer.parseInt(s[index]) + val);

					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < s.length; j++) {
						stringBuffer.append(s[j] + "#");
					}

					firstWeekReport = stringBuffer.toString();
				} else if (secondWeek) {
					String s[] = secondWeekReport.split("#");
					index = Math.abs(day - 8);

					s[index] = String.valueOf(Integer.parseInt(s[index]) + val);

					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < s.length; j++) {
						stringBuffer.append(s[j] + "#");
					}

					secondWeekReport = stringBuffer.toString();
				} else if (thirdWeek) {
					String s[] = thirdWeekReport.split("#");
					index = Math.abs(day - 15);
					s[index] = String.valueOf(Integer.parseInt(s[index]) + val);

					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < s.length; j++) {
						stringBuffer.append(s[j] + "#");
					}

					thirdWeekReport = stringBuffer.toString();
				} else {
					String s[] = fourthWeekReport.split("#");
					index = Math.abs(day - 22);
					s[index] = String.valueOf(Integer.parseInt(s[index]) + val);

					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < s.length; j++) {
						stringBuffer.append(s[j] + "#");
					}

					fourthWeekReport = stringBuffer.toString();
				}

				ob = new InsertDataTable();
				ob.setColName("firstWeekReport");
				ob.setDataName(firstWeekReport);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("secondWeekReport");
				ob.setDataName(secondWeekReport);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("thirdWeekReport");
				ob.setDataName(thirdWeekReport);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("fourthWeekReport");
				ob.setDataName(fourthWeekReport);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("comment");
				ob.setDataName(comment);
				insertData.add(ob);
				recordSaved = cbt.insertIntoTable(tableName, insertData,
						connectionSpace);
			}
		} else {
		}
		return recordSaved;
	}

	public List getEmpInfoByMobNo(String mobile) {
		SessionFactory connectionSpace = new ConnectionHelper()
		.getSessionFactory("IN-8");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query = "select id,useraccountid from employee_basic where mobOne='"
				+ mobile.trim() + "'";

		List list = cbt.executeAllSelectQuery(query, connectionSpace);

		return list;
	}

	public String getCurrentMonthYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		month = month + 1;
		String Month = String.valueOf(month);
		if (month < 10)
			Month = "0" + month;
		String date = Month + "-" + year;
		return date;
	}

	public String getUserId(String accId) {
		SessionFactory connectionSpace = new ConnectionHelper()
		.getSessionFactory("IN-8");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query = "select userID from useraccount where id='"
				+ accId.trim() + "'";

		List list = cbt.executeAllSelectQuery(query, connectionSpace);

		return list.get(0).toString();
	}

	/*
	 * Get cumulative IVRS data of specified month
	 */
	public List getIVRSCumulativeData(String yearMonth, String userName, SessionFactory connectionSpace)
			throws Exception {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query = "SELECT emp.empName, ivrs.mobileNo, ivrs.availability, sum(ivrs.totalCall), "
				+ "sum(ivrs.productiveCall), sum(ivrs.totalSale), sum(ivrs.newOutlet), sum(ivrs.talkTime), sum(ivrs.pulse), "
				+ "ivrs.msgDate, ivrs.msgTime "
				+ "FROM employee_basic as emp INNER JOIN ivrsreport as ivrs "
				+ "ON emp.mobOne = ivrs.mobileNo WHERE ";

		if (userName != null && !userName.equals("")) {
			query += " ivrs.createdBy IN (" + userName + ") and ";
		}

		query += " ivrs.msgDate like '" + yearMonth
				+ "%' group by ivrs.mobileNo";

		//System.out.println("query:>>>>>>:" + query);
		List report = cbt.executeAllSelectQuery(query, connectionSpace);

		return report;
	}

	public List getIVRSDetailData(String yearMonth, long mobileNO, SessionFactory connectionSpace)
			throws Exception {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query = "SELECT ivrs.availability, ivrs.totalCall, "
				+ "ivrs.productiveCall, ivrs.totalSale, ivrs.newOutlet, ivrs.talkTime, ivrs.pulse, "
				+ "ivrs.msgDate, ivrs.msgTime FROM ivrsreport as ivrs WHERE ivrs.mobileNo = '"
				+ mobileNO + "' and  ivrs.msgDate like '" + yearMonth + "%' ";

		//System.out.println("getIVRSDetailData:>>>>>>:" + query);
		List report = cbt.executeAllSelectQuery(query, connectionSpace);

		return report;
	}

}
