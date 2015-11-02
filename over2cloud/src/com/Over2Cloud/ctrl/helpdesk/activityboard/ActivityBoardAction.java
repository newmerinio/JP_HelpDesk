package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.ActionOnFeedback;

@SuppressWarnings("serial")
public class ActivityBoardAction extends GridPropertyBean
{
	private String taskType;
	private String fromDepart;
	private String toDepart;
	private String lodgeMode;
	private String feedStatus;
	private String closeMode;
	private String escLevel;
	private String escTAT;

	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String tableAlis;
	private String divName;
	private Map<String, String> columnMap;
	private String[] columnNames;

	private String excelFileName;
	private String selectedId;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;

	private Map<String, String> fromDept;
	private Map<String, String> toDept;
	private List<Object> masterViewProp = new ArrayList<Object>();
	private Map<String, String> dataCountMap;
	private String readingConditn;
	private String complianId;
	private String formaterOn;
	private String mainTable;
	private Map<String, String> dataMap;
	private Map<String, String> editableDataMap;
	/*
	 * private Map<String, String> snoozeMap; private Map<String, String> hpMap;
	 * private Map<String, String> seekMap; private Map<String, String>
	 * reassignMap; private Map<String, String> ignoreMap; private Map<String,
	 * String> resolvedMap;
	 */
	private String maxDateValue;
	private String minDateValue;
	private String[] resolutionDetail;
	public static boolean ASC = true;
	public static boolean DESC = false;
	private Map<String, Map<String, String>> finalStatusMap;
	private String severityLevel;

	@SuppressWarnings("unused")
	public String viewActivityBoardHeader()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ActivityBoardHelper ABH = new ActivityBoardHelper();
				fromDept = new LinkedHashMap<String, String>();
				toDept = new LinkedHashMap<String, String>();
				String empId = null;
				String userType = null;
				List dataList = null;
				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();

				dataList = ABH.getServiceDeptByEmpId(connectionSpace, empId, userType);
				if (dataList != null && dataList.size() > 0)
				{
					dataList.clear();
					dataList = ABH.getAllDepartment(connectionSpace, null);
				}
				else
				{
					dataList = ABH.getAllDepartment(connectionSpace, empId);
				}
				if (dataList != null && dataList.size() > 0)
				{
					fromDept.put("all", "From Dept");
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							fromDept.put(object[0].toString(), object[1].toString());
						}
					}
					dataList.clear();
				}
				dataList = ABH.getServiceDeptByEmpId(connectionSpace, null, null);
				if (dataList != null && dataList.size() > 0)
				{
					toDept.put("all", "To Dept");
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							toDept.put(object[0].toString(), object[1].toString());
						}
					}
					dataList.clear();
				}

				maxDateValue = DateUtil.getCurrentDateUSFormat();
				minDateValue = DateUtil.getNewDate("day", -30, DateUtil.getCurrentDateUSFormat());
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

	public String viewActivityBoardColumn()
	{
		try
		{
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("history");
			gpv.setHeadingName("History");
			gpv.setFormatter("viewHistory");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticketno");
			gpv.setHeadingName("Ticket No.");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("todept");
			gpv.setHeadingName("To Department");
			gpv.setHideOrShow("false");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("category");
			gpv.setHeadingName("Category");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("subcatg");
			gpv.setHeadingName("Sub-Category");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("brief");
			gpv.setHeadingName("Feedback Brief");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("openat");
			gpv.setHeadingName("Open On");
			gpv.setHideOrShow("false");
			gpv.setWidth(110);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedby");
			gpv.setHeadingName("Feedback By");
			gpv.setFormatter("feedByDetail");
			gpv.setHideOrShow("false");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("bydept");
			gpv.setHeadingName("By Department");
			gpv.setHideOrShow("false");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Current Status");
			gpv.setFormatter("statusDetail");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("TAT");
			gpv.setHeadingName("TAT Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			gpv.setFormatter("tatDetail");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("allotedto");
			gpv.setHeadingName("Alloted To");
			gpv.setFormatter("allotToDetail");
			gpv.setWidth(120);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("location");
			gpv.setHeadingName("Location");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("feedbacktype");
			gpv.setHeadingName("Feedback Type");
			gpv.setHideOrShow("false");
			gpv.setWidth(70);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("lodgemode");
			gpv.setHeadingName("Lodge Mode");
			gpv.setFormatter("lodgeUserDetail");
			gpv.setHideOrShow("false");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("closemode");
			gpv.setHeadingName("Close Mode");
			gpv.setHideOrShow("false");
			gpv.setFormatter("editCloseMode");
			gpv.setWidth(40);
			viewPageColumns.add(gpv);

			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	public String beforeViewActivityHistoryData()
	{
		try
		{
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("ticketno");
			gpv.setHeadingName("Ticket No.");
			gpv.setFormatter("statusDetail11");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("subcatg");
			gpv.setHeadingName("Sub-Category");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("brief");
			gpv.setHeadingName("Feedback-Brief");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("openat");
			gpv.setHeadingName("Open At");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("closeat");
			gpv.setHeadingName("Close At");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalbreakdown");
			gpv.setHeadingName("Total Breakdown");
			gpv.setWidth(50);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("totaltime");
			gpv.setHeadingName("Total Time");
			gpv.setWidth(50);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalworkingtime");
			gpv.setHeadingName("Total Working Time");
			gpv.setWidth(50);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolveby");
			gpv.setHeadingName("Resolved By");
			gpv.setWidth(120);
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("rca");
			gpv.setHeadingName("Action Taken");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare");
			gpv.setHeadingName("Resource Used");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation");
			gpv.setHeadingName("Escalation");
			gpv.setHideOrShow("false");
			gpv.setWidth(50);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setHideOrShow("false");
			gpv.setWidth(60);
			viewPageColumns.add(gpv);

			/*
			 * gpv = new GridDataPropertyView(); gpv.setColomnName("cost");
			 * gpv.setHeadingName("Cost"); gpv.setHideOrShow("false");
			 * viewPageColumns.add(gpv);
			 */

			gpv = new GridDataPropertyView();
			gpv.setColomnName("closemode");
			gpv.setHeadingName("Close Mode");
			gpv.setHideOrShow("false");
			gpv.setWidth(55);
			viewPageColumns.add(gpv);

			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}

	}

	public String viewActivityBoardData()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ActivityBoardHelper ABH = new ActivityBoardHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> tempList = new ArrayList<Object>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				String userType = null, empId = null, userMobno = null;

				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();
				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userMobno = CUA.getValueWithNullCheck(object[1]);
						toDepart = object[3].toString();
					}
				}

				query.append("SELECT feedback.id, feedback.feed_by, feedback.feed_by_mobno, catg.id AS catgId, feedback.ticket_no, ");
				query.append("catg.categoryName, subcatg.subCategoryName, ");
				query.append("feedback.feed_brief, feedback.open_date, feedback.open_time, ");
				query.append("dept.deptName, feedback.location, feedback.status, feedback.level,");
				query.append("emp.empName, feedtype.fbType, feedback.via_from, feedback.close_mode, dept1.deptName AS todepartment,feedback.status AS feedstatus");
				query.append(" FROM feedback_status AS feedback ");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
				query.append(" INNER JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
				query.append(" INNER JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");
				query.append(" INNER JOIN department AS dept ON dept.id = feedback.by_dept_subdept");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = feedback.to_dept_subdept");
				query.append(" INNER JOIN department AS dept1 ON dept1.id = subdept.deptid");
				query.append(" WHERE feedback.moduleName = 'HDM'");
				if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")) && (searchString == null || searchString.equals("")))
				{
					query.append(" AND feedback.by_dept_subdept = " + fromDepart);
				}
				else
				{
					String depId = getFromDeptId();
					if (!depId.equals("0"))
					{
						query.append(" AND feedback.by_dept_subdept IN(" + depId + ")");
					}
				}
				/*
				 * if(toDepart!=null && !toDepart.equalsIgnoreCase("All"))
				 * query.append(
				 * " AND feedback.to_dept_subdept IN(SELECT id FROM subdepartment WHERE deptid="
				 * +toDepart+")");
				 */
				if (taskType != null && taskType.equalsIgnoreCase("All") && !userType.equalsIgnoreCase("M") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.to_dept_subdept IN(SELECT id FROM subdepartment WHERE deptid=" + toDepart + ")");
				else if (taskType != null && taskType.equalsIgnoreCase("byMe") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.feed_by_mobno =" + userMobno);
				else if (taskType != null && taskType.equalsIgnoreCase("toMe") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.allot_to =" + empId);
				else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.by_dept_subdept =" + toDepart);

				if (lodgeMode != null && !lodgeMode.equalsIgnoreCase("All") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.via_from = '" + lodgeMode + "'");
				if (feedStatus != null && !feedStatus.equalsIgnoreCase("All") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.status = '" + feedStatus + "'");
				if (closeMode != null && !closeMode.equalsIgnoreCase("All") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.close_mode = '" + closeMode + "'");
				if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.level = '" + escLevel + "'");
				if (escTAT != null && escTAT.equalsIgnoreCase("onTime") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.level = 'Level1'");
				else if (escTAT != null && escTAT.equalsIgnoreCase("offTime") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.level != 'Level1'");

				if (severityLevel != null && !severityLevel.equalsIgnoreCase("All"))
					query.append(" AND feedback.severity_level=" + severityLevel);

				if ((minDateValue != null && maxDateValue != null) && !searchField.equalsIgnoreCase("ticket_no"))
				{
					String str[] = maxDateValue.split("-");
					if (str[0].length() < 4)
						query.append(" AND feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
					else
						query.append(" AND feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
				}
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation

					query.append("AND feedback." + getSearchField() + " like '" + getSearchString() + "%'");

				}

				query.append(" ORDER BY feedback.ticket_no DESC");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("id", CUA.getValueWithNullCheck(object[0]));
						tempMap.put("history", ABH.getComplaintCountByFeedByWithCatg(connectionSpace, CUA.getValueWithNullCheck(object[1]), CUA.getValueWithNullCheck(object[2]), CUA.getValueWithNullCheck(object[3])));
						tempMap.put("ticketno", CUA.getValueWithNullCheck(object[4]));
						tempMap.put("todept", CUA.getValueWithNullCheck(object[18]));
						tempMap.put("category", CUA.getValueWithNullCheck(object[5]));
						tempMap.put("subcatg", CUA.getValueWithNullCheck(object[6]));
						tempMap.put("brief", CUA.getValueWithNullCheck(object[7]));
						tempMap.put("openat", DateUtil.convertDateToIndianFormat(object[8].toString()) + ", " + object[9].toString().substring(0, object[9].toString().length() - 3));
						tempMap.put("feedby", CUA.getValueWithNullCheck(object[1]));
						tempMap.put("bydept", CUA.getValueWithNullCheck(object[10]));
						tempMap.put("location", CUA.getValueWithNullCheck(object[11]));
						System.out.println("Status is "+CUA.getValueWithNullCheck(object[12]));
						
						if (CUA.getValueWithNullCheck(object[12]).equalsIgnoreCase("transfer"))
							tempMap.put("status", "Re-Assign");
						else if (CUA.getValueWithNullCheck(object[12]).equalsIgnoreCase("hold"))
							tempMap.put("status", "Seek Approval");
						else
							tempMap.put("status", CUA.getValueWithNullCheck(object[12]));

						tempMap.put("TAT", CUA.getValueWithNullCheck(object[13]));
						tempMap.put("allotedto", CUA.getValueWithNullCheck(object[14]));
						tempMap.put("feedbacktype", CUA.getValueWithNullCheck(object[15]));
						tempMap.put("lodgemode", CUA.getValueWithNullCheck(object[16]));
						tempMap.put("closemode", CUA.getValueWithNullCheck(object[17]));
						tempList.add(tempMap);

					}
				}
				setMasterViewProp(tempList);
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();
				columnMap.put("fstatus.ticket_no", "Ticket No");
				columnMap.put("deptTo.deptName AS todepart ", "To Department");
				columnMap.put("fCat.categoryName", "Category");
				columnMap.put("fsubCat.subCategoryName", "Sub-category");
				columnMap.put("fstatus.feed_brief", "Feddback-Brief");
				columnMap.put("fstatus.open_date", "Open Date");
				columnMap.put("fstatus.open_time", " Open Time");
				columnMap.put("fstatus.feed_by", "Feedback By");
				columnMap.put("dept.deptName AS bydepart", "By Department");
				columnMap.put("fstatus.location", "Location");
				columnMap.put("fstatus.status", "Current Status");
				columnMap.put("fstatus.level", "TAT Status");
				columnMap.put("emp.empName AS emp1", "Alloted To");
				columnMap.put("fbtype.fbType", "Feedback Type");
				columnMap.put("fstatus.via_from", "Lodge Mode");
				columnMap.put("fstatus.close_mode", "Close Mode");
				columnMap.put("fstatus.feed_by_mobno", " Mobile No");
				columnMap.put("fstatus.feed_by_emailid", "Email Id");
				columnMap.put("fstatus.escalation_date", "Escalation Date");
				columnMap.put("fstatus.feed_registerby", "Feed Register By");
				columnMap.put("fstatus.action_by", "Action By");
				columnMap.put("fstatus.resolve_date", " Resolve Date");
				columnMap.put("fstatus.resolve_time", "Resolve Time");
				columnMap.put("fstatus.resolve_duration", "Resolve Duration");
				columnMap.put("fstatus.resolve_remark", " Resolve Remarks");
				columnMap.put("empresolve.empName As resolve", "Resolve By");
				columnMap.put("fstatus.spare_used", "Spare Used");
				columnMap.put("fstatus.hp_date", "High Priority Date");
				columnMap.put("fstatus.hp_time", "High Priority Time");
				columnMap.put("fstatus.hp_reason", "High Priority Reason");
				columnMap.put("fstatus.sn_reason", " Snooze Reason");
				columnMap.put("fstatus.sn_on_date", "Snooze Date");
				columnMap.put("fstatus.sn_on_time", "Snooze Time");
				columnMap.put("fstatus.sn_upto_date", "Snooze Upto Date");
				columnMap.put("fstatus.sn_upto_time", "Snooze Upto Time");
				columnMap.put("fstatus.sn_duration", "Snooze Duration");
				columnMap.put("fstatus.ig_date", "Ignore Date");
				columnMap.put("fstatus.ig_time", "Ignore Time");
				columnMap.put("fstatus.ig_reason", "Ignore Reason");
				columnMap.put("fstatus.transfer_date", " Transfer Date");
				columnMap.put("fstatus.transfer_time", "Transfer Time");
				columnMap.put("fstatus.moduleName", "Module Name");
				columnMap.put("fstatus.feedback_remarks", "Remarks");
				columnMap.put("fstatus.transferId", "Transfer Id");
				columnMap.put("fstatus.non_working_time", "Non Working time");
				columnMap.put("fstatus.resolveDoc", "Resolve Document First");
				columnMap.put("fstatus.resolveDoc1", "Resolve Document Second");
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
				return SUCCESS;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String downloadExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Feedback Status Detail";

		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommunicationHelper cmnHelper = new CommunicationHelper();
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String userType = null, empId = null, userMobno = null;
				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();
				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");

				StringBuilder query = new StringBuilder();
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userMobno = CUA.getValueWithNullCheck(object[1]);
						toDepart = object[3].toString();
					}
				}

				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					List dataList = null;
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{

						query.append("select distinct ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								}
								else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" FROM feedback_status AS fstatus");
						query.append(" LEFT JOIN  department  AS dept  ON  dept.id =fstatus.by_dept_subdept");
						query.append(" LEFT JOIN  employee_basic  AS emp  ON  emp.id =fstatus.allot_to");
						query.append(" LEFT JOIN  employee_basic  AS empresolve  ON  empresolve.id =fstatus.resolve_by");
						query.append(" LEFT JOIN  department  AS deptTo  ON  deptTo.id =emp.deptname");
						query.append(" LEFT JOIN  subdepartment  AS subdept  ON  subdept.id =fstatus.to_dept_subdept");
						query.append(" LEFT JOIN  subdepartment  AS subdeptby  ON  subdeptby.id =fstatus.by_dept_subdept");
						query.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");
						query.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
						query.append(" LEFT JOIN  feedback_type AS fbtype ON fbtype.id = fCat.fbType");
						query.append(" WHERE fstatus.moduleName = 'HDM'");

						if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")))
						{
							query.append(" AND fstatus.by_dept_subdept = " + fromDepart);
						}
						else
						{
							String depId = getFromDeptId();
							if (!depId.equals("0"))
							{
								query.append(" AND fstatus.by_dept_subdept IN(" + depId + ")");
							}
						}

						if (taskType != null && taskType.equalsIgnoreCase("All"))
							query.append(" AND fstatus.to_dept_subdept IN(SELECT id FROM subdepartment WHERE deptid=" + toDepart + ")");
						else if (taskType != null && taskType.equalsIgnoreCase("byMe"))
							query.append(" AND fststus.feed_by_mobno =" + userMobno);
						else if (taskType != null && taskType.equalsIgnoreCase("toMe"))
							query.append(" AND fstatus.allot_to =" + empId);
						else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("")))
							query.append(" AND feedback.by_dept_subdept =" + toDepart);

						if (lodgeMode != null && !lodgeMode.equalsIgnoreCase("All"))
							query.append(" AND fstatus.via_from = '" + lodgeMode + "'");
						if (feedStatus != null && !feedStatus.equalsIgnoreCase("All"))
							query.append(" AND fstatus.status = '" + feedStatus + "'");
						if (closeMode != null && !closeMode.equalsIgnoreCase("All"))
							query.append(" AND fstatus.close_mode = '" + closeMode + "'");
						if (escLevel != null && !escLevel.equalsIgnoreCase("all"))
							query.append(" AND fstatus.level = '" + escLevel + "'");
						if (escTAT != null && escTAT.equalsIgnoreCase("onTime"))
							query.append(" AND fststus.level = 'Level1'");
						else if (escTAT != null && escTAT.equalsIgnoreCase("offTime"))
							query.append(" AND fststus.level != 'Level1'");

						if (severityLevel != null && !severityLevel.equalsIgnoreCase("All"))
							query.append(" AND feedback.severity_level=" + severityLevel);

						if (minDateValue != null && maxDateValue != null)
						{
							String str[] = maxDateValue.split("-");
							if (str[0].length() < 4)
								query.append(" AND fstatus.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
							else
								query.append(" AND fstatus.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
						}

						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
						{

							excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);

						}
						if (excelFileName != null)
						{
							excelStream = new FileInputStream(excelFileName);
						}
						/*
						 * if (excelFileName!=null) {
						 * excelFileName=excelFileName
						 * .substring(excelFileName.lastIndexOf("//")+2,
						 * excelFileName.length()); }
						 */

					}
					returnResult = SUCCESS;
				}
				else
				{
					addActionMessage("There are some error in data!!!!");
					returnResult = ERROR;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String activityBoardCount()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				dataCountMap = new LinkedHashMap<String, String>();
				ActivityBoardHelper ABH = new ActivityBoardHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> tempList = new ArrayList<Object>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				String userType = null, empId = null, userMobno = null;

				String empIdofuser = (String) session.get("empIdofuser");
				userType = (String) session.get("userTpe");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				empId = empIdofuser.split("-")[1].trim();
				List empDataList = CUACtrl.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), "1");
				if (empDataList != null && empDataList.size() > 0)
				{
					for (Iterator iterator = empDataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						userMobno = CUA.getValueWithNullCheck(object[1]);
						toDepart = object[3].toString();
					}
				}

				query.append("SELECT count(distinct feedback.id),feedback.status");
				query.append(" FROM feedback_status AS feedback ");
				query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
				query.append(" INNER JOIN feedback_category AS catg ON catg.id = subcatg.categoryName");
				query.append(" INNER JOIN feedback_type AS feedtype ON feedtype.id = catg.fbType");
				query.append(" INNER JOIN department AS dept ON dept.id = feedback.by_dept_subdept");
				query.append(" INNER JOIN employee_basic AS emp ON emp.id = feedback.allot_to");
				query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = feedback.to_dept_subdept");
				query.append(" INNER JOIN department AS dept1 ON dept1.id = subdept.deptid");
				query.append(" WHERE feedback.moduleName = 'HDM'");
				if (fromDepart != null && !(fromDepart.trim().equalsIgnoreCase("all")) && (searchString == null || searchString.equals("")))
				{
					query.append(" AND feedback.by_dept_subdept = " + fromDepart);
				}
				else
				{
					String depId = getFromDeptId();
					if (!depId.equals("0"))
					{
						query.append(" AND feedback.by_dept_subdept IN(" + depId + ")");
					}
				}
				/*
				 * if(toDepart!=null && !toDepart.equalsIgnoreCase("All"))
				 * query.append(
				 * " AND feedback.to_dept_subdept IN(SELECT id FROM subdepartment WHERE deptid="
				 * +toDepart+")");
				 */
				if (taskType != null && taskType.equalsIgnoreCase("All") && !userType.equalsIgnoreCase("M") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.to_dept_subdept IN(SELECT id FROM subdepartment WHERE deptid=" + toDepart + ")");
				else if (taskType != null && taskType.equalsIgnoreCase("byMe") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.feed_by_mobno =" + userMobno);
				else if (taskType != null && taskType.equalsIgnoreCase("toMe") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.allot_to =" + empId);
				else if (taskType != null && taskType.equalsIgnoreCase("fromMyDept") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.by_dept_subdept =" + toDepart);

				if (lodgeMode != null && !lodgeMode.equalsIgnoreCase("All") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.via_from = '" + lodgeMode + "'");
				if (feedStatus != null && !feedStatus.equalsIgnoreCase("All") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.status = '" + feedStatus + "'");
				if (closeMode != null && !closeMode.equalsIgnoreCase("All") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.close_mode = '" + closeMode + "'");
				if (escLevel != null && !escLevel.equalsIgnoreCase("all") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.level = '" + escLevel + "'");
				if (escTAT != null && escTAT.equalsIgnoreCase("onTime") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.level = 'Level1'");
				else if (escTAT != null && escTAT.equalsIgnoreCase("offTime") && (searchString == null || searchString.equals("")))
					query.append(" AND feedback.level != 'Level1'");

				if (severityLevel != null && !severityLevel.equalsIgnoreCase("All"))
					query.append(" AND feedback.severity_level=" + severityLevel);

				if (minDateValue != null && maxDateValue != null && !searchField.equalsIgnoreCase("ticket_no"))
				{
					String str[] = maxDateValue.split("-");
					if (str[0].length() < 4)
						query.append(" AND feedback.open_date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
					else
						query.append(" AND feedback.open_date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
				}
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation

					query.append("AND feedback." + getSearchField() + " like '" + getSearchString() + "%'");

				}

				query.append(" GROUP BY feedback.status ORDER BY feedback.id DESC");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				int totalValue = 0;
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator3 = dataList.iterator(); iterator3.hasNext();)
					{
						Object[] object2 = (Object[]) iterator3.next();
						dataCountMap.put("Pending", "0");
						dataCountMap.put("Snooze", "0");
						dataCountMap.put("HighPriority", "0");
						dataCountMap.put("Ignore", "0");
						dataCountMap.put("Resolved", "0");
						dataCountMap.put("hold", "0");
						dataCountMap.put("transfer", "0");

						if (object2[1].toString().equalsIgnoreCase("Pending"))
						{
							dataCountMap.put("Pending", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
						else if (object2[1].toString().equalsIgnoreCase("Snooze"))
						{
							dataCountMap.put("Snooze", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
						else if (object2[1].toString().equalsIgnoreCase("High Priority"))
						{
							dataCountMap.put("HighPriority", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
						else if (object2[1].toString().equalsIgnoreCase("Ignore"))
						{
							dataCountMap.put("Ignore", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
						else if (object2[1].toString().equalsIgnoreCase("Resolved"))
						{
							dataCountMap.put("Resolved", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
						else if (object2[1].toString().equalsIgnoreCase("Hold"))
						{
							dataCountMap.put("Hold", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
						else if (object2[1].toString().equalsIgnoreCase("transfer"))
						{
							dataCountMap.put("transfer", object2[0].toString());
							totalValue = totalValue + Integer.parseInt(object2[0].toString());
						}
					}
				}
				dataCountMap.put("total", String.valueOf(totalValue));

				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getFromDeptId()
	{
		ActivityBoardHelper ABH = new ActivityBoardHelper();
		String empId = null;
		String userType = null;
		List dataList = null;
		StringBuilder fromDepId = new StringBuilder();
		String empIdofuser = (String) session.get("empIdofuser");
		userType = (String) session.get("userTpe");
		if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
			return ERROR;
		empId = empIdofuser.split("-")[1].trim();

		dataList = ABH.getServiceDeptByEmpId(connectionSpace, empId, userType);
		if (dataList == null && dataList.size() == 0)
		{
			dataList = ABH.getAllDepartment(connectionSpace, empId);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						fromDepId.append(object[0].toString() + ",");
					}
				}
			}
		}
		fromDepId.append("0");
		return fromDepId.toString();
	}

	public String viewActivityHistoryData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> tempList = new ArrayList<Object>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String feedby = null, mobno = null, catgId = null;
				StringBuilder query = new StringBuilder();
				if (id != null)
				{
					query.append("SELECT feedback.feed_by,feedback.feed_by_mobno,subcatg.categoryName");
					query.append(" FROM feedback_status AS feedback");
					query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id= feedback.subCatg");
					query.append(" WHERE feedback.id=" + id);
					List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					query.setLength(0);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							feedby = CUA.getValueWithNullCheck(object[0]);
							mobno = CUA.getValueWithNullCheck(object[1]);
							catgId = CUA.getValueWithNullCheck(object[2]);
						}
					}
					dataList.clear();

					query.append("SELECT feedback.id, feedback.ticket_no, ");
					query.append("subcatg.subCategoryName, ");
					query.append("feedback.feed_brief, feedback.open_date, feedback.open_time,feedback.resolve_date, feedback.resolve_time,");
					query.append("feedback.resolve_duration, emp.empName, feedback.resolve_remark, feedback.spare_used,");
					query.append("feedback.level, feedback.close_mode, feedback.status,");
					query.append("feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
					query.append("feedback.ig_date,feedback.ig_time,feedback.transfer_date,feedback.transfer_time");
					query.append(" FROM feedback_status AS feedback ");
					query.append(" LEFT JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
					query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.resolve_by");
					query.append(" WHERE feedback.feed_by_mobno = '" + mobno + "' AND feedback.feed_by = '" + feedby + "' AND feedback.moduleName = 'HDM'");
					query.append(" AND feedback.subCatg IN (SELECT id FROM feedback_subcategory WHERE categoryName =" + catgId + ")");
					query.append(" ORDER BY feedback.id DESC");
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					query.setLength(0);
					if (dataList != null && dataList.size() > 0)
					{
						String totalHrs = "00:00", totalWorkingHrs = "00:00";
						List timeList = new ArrayList();
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();

							Map<String, Object> tempMap = new HashMap<String, Object>();
							tempMap.put("id", CUA.getValueWithNullCheck(object[0]));
							tempMap.put("ticketno", CUA.getValueWithNullCheck(object[1]));
							tempMap.put("subcatg", CUA.getValueWithNullCheck(object[2]));
							tempMap.put("brief", CUA.getValueWithNullCheck(object[3]));
							tempMap.put("openat", DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString().substring(0, object[5].toString().length() - 3));
							if (object[6] != null && object[7] != null)
								tempMap.put("closeat", DateUtil.convertDateToIndianFormat(object[6].toString()) + ", " + object[7].toString().substring(0, object[7].toString().length() - 3));
							else
								tempMap.put("closeat", "NA");

							tempMap.put("totalbreakdown", CUA.getValueWithNullCheck(object[8]));
							tempMap.put("resolveby", CUA.getValueWithNullCheck(object[9]));
							tempMap.put("rca", CUA.getValueWithNullCheck(object[10]));
							tempMap.put("spare", CUA.getValueWithNullCheck(object[11]));
							tempMap.put("escalation", "Yes");
							tempMap.put("level", CUA.getValueWithNullCheck(object[12]));
							tempMap.put("closemode", CUA.getValueWithNullCheck(object[13]));
							tempMap.put("status", CUA.getValueWithNullCheck(object[14]));
							if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("Resolved"))
							{

								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), object[6].toString(), object[7].toString(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("Pending"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("Snooze"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("High Priority"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("hold"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("Ignore"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[20]), CUA.getValueWithNullCheck(object[21]), cbt);
							}
							else if (CUA.getValueWithNullCheck(object[14]).equalsIgnoreCase("transfer"))
							{
								timeList = getTotalAndWorkingTime(object[0].toString(), object[4].toString(), object[5].toString(), CUA.getValueWithNullCheck(object[15]), CUA.getValueWithNullCheck(object[16]), CUA.getValueWithNullCheck(object[17]),
										CUA.getValueWithNullCheck(object[18]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[22]), CUA.getValueWithNullCheck(object[23]), cbt);
							}
							if (timeList != null && timeList.size() > 0)
							{
								tempMap.put("totaltime", timeList.get(0).toString());
								tempMap.put("totalworkingtime", timeList.get(1).toString());
							}
							tempList.add(tempMap);

						}
					}
					setMasterViewProp(tempList);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public List getTotalAndWorkingTime(String complainid, String openDate, String openTime, String snoozeDate, String snoozeTime, String snoozeUp2Date, String snoozeUp2Time, String snoozeDur, String calculateDate, String calculateTime,
			CommonOperInterface cbt)
	{
		List dataList = new ArrayList();
		try
		{

			String totalHrs = DateUtil.time_difference(openDate, openTime, calculateDate, calculateTime);
			String totalWorkingHrs = totalHrs;
			if (snoozeDate != null && !snoozeDate.equalsIgnoreCase("NA"))
			{
				if (DateUtil.compareDateTime(snoozeUp2Date + " " + snoozeUp2Time, calculateDate + " " + calculateTime))
				{
					totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, snoozeDur);
				}
				else
				{

					String tempTime = DateUtil.time_difference(calculateDate, calculateTime, snoozeDate, snoozeTime);
					totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
				}
			}
			String query = "SELECT request_on,request_at,approved_on,approved_at,status FROM seek_approval_detail WHERE complaint_id =" + complainid;
			List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();)
				{
					Object[] object2 = (Object[]) iterator2.next();
					if (object2[4] != null && !object2[4].toString().equalsIgnoreCase("Pending"))
					{
						String tempTime = DateUtil.time_difference(object2[0].toString(), object2[1].toString(), object2[2].toString(), object2[3].toString());
						totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
					}
					else if (object2[4] != null && object2[4].toString().equalsIgnoreCase("Pending"))
					{
						String tempTime = DateUtil.time_difference(object2[0].toString(), object2[1].toString(), calculateDate, calculateTime);
						totalWorkingHrs = DateUtil.getTimeDifference(totalWorkingHrs, tempTime);
					}
				}
			}

			dataList.add(totalHrs);
			dataList.add(totalWorkingHrs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public String getComplaintActivityDeatil()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ActivityBoardHelper helperObject = new ActivityBoardHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataList = null;
				dataMap = new LinkedHashMap<String, String>();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				if (formaterOn != null)
				{
					if (formaterOn.equalsIgnoreCase("smsCode"))
					{
						dataMap.put("Ticket Status", "Code");
						dataMap.put("Close Ticket", "BLK CT <Ticket No.>*<Comment>");
						dataMap.put("Ignore Ticket", "BLK IT <Ticket No.>*<Comment>");
						dataMap.put("High-Priority Ticket", "BLK HT <Ticket No.>*<Comment>");
						dataMap.put("Noted Ticket", "BLK NT <Ticket No.>*<Comment>");
					}
				}
				if (complianId != null && formaterOn != null && mainTable != null)
				{
					if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("feed_by"))
					{
						dataList = helperObject.getFeedByEmployeeDetail(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
								dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
							}
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("allot_to"))
					{
						dataList = helperObject.getAllotToEmployeeDetail(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
								dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
							}
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("employee_basic") && formaterOn.equalsIgnoreCase("lodgeMode"))
					{
						dataList = helperObject.getLodgerEmployeeDetail(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Name", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Mobile", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Email", CUA.getValueWithNullCheck(object[2]));
								dataMap.put("Department", CUA.getValueWithNullCheck(object[3]));
							}
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("status"))
					{
						Map<String, String> dataHashMap = new HashMap<String, String>();
						List tempList = null;
						dataList = helperObject.getTicketCycle(connectionSpace, cbt, complianId);
						String actionBy = "NA";
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[22] != null)
								{
									tempList = helperObject.getLodgerEmployeeDetailByUserName(connectionSpace, cbt, object[22].toString());
									if (tempList != null && tempList.size() > 0)
									{
										Object[] obj = (Object[]) tempList.get(0);
										actionBy = obj[0].toString();
									}
									tempList.clear();
								}

								if (object[1] != null)
								{
									dataHashMap.put("Snooze", object[1].toString()+" "+object[2].toString());
								}
								if (object[7] != null)
								{
									dataHashMap.put("High Priority", object[7].toString()+" "+object[8].toString());

								}
								if (object[10] != null)
								{
									dataHashMap.put("Ignore", object[10].toString()+" "+object[11].toString());

								}
								if (object[13] != null)
								{
									dataHashMap.put("Resolved", object[13].toString()+" "+object[14].toString());

								}
								if (object[19] != null)
								{
									dataHashMap.put("Re-assign", object[19].toString());

								}
								tempList = helperObject.getSeekApprovalData(connectionSpace, cbt, complianId);
								if (tempList != null && tempList.size() > 0)
								{

									String requestedDate = null, requestedTime = null;
									for (Iterator iterator2 = tempList.iterator(); iterator2.hasNext();)
									{
										Object[] object1 = (Object[]) iterator2.next();
										if (object1[1] != null)
										{
											requestedDate = object1[1].toString();
											requestedTime = object1[2].toString();
										}
											
									}
									dataHashMap.put("SeekApproval", requestedDate+" "+requestedTime);
								}

							}
							dataHashMap = sortByComparator(dataHashMap, DESC);
							getCycleByOrder(dataList, dataHashMap, actionBy, tempList);
						}
						else
						{
							dataMap.put("Name", "NA");
							dataMap.put("Mobile", "NA");
							dataMap.put("Email", "NA");
							dataMap.put("Department", "NA");
						}
					}
					else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("TAT"))
					{
						WorkingHourAction WHA = new WorkingHourAction();
						dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, complianId);
						String addressingDate = null, addressingTime = "00:00", resolutionDate = null, resolutionTime = "00:00";
						if (dataList != null && dataList.size() > 0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("Status", CUA.getValueWithNullCheck(object[0]));
								dataMap.put("Level", CUA.getValueWithNullCheck(object[1]));
								dataMap.put("Open Date & Time", DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[3].toString().substring(0, object[3].toString().length() - 3));
								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[4].toString(), object[5].toString(), "Y", object[2].toString(), object[3].toString(), "HDM");
								addressingDate = dateTime.get(0);
								addressingTime = dateTime.get(1);
								resolutionDate = dateTime.get(2);
								resolutionTime = dateTime.get(3);
								dateTime.clear();
								dataMap.put("Addressing Date & Time", DateUtil.convertDateToIndianFormat(addressingDate) + ", " + addressingTime);
								dataMap.put("Resolution Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
								List empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "2", connectionSpace, cbt);
								StringBuilder empName = new StringBuilder();
								if (empList != null)
								{
									for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
									{
										Object[] object2 = (Object[]) iterator2.next();
										if (object2[1] != null)
											empName.append(object2[1].toString() + ", ");
									}
									if (empName != null && empName.length() > 0)
									{
										dataMap.put("L-2 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
										dataMap.put("L-2 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
									}
									empList.clear();
									empName.setLength(0);

									dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "HDM");
									resolutionDate = dateTime.get(0);
									resolutionTime = dateTime.get(1);

									empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "3", connectionSpace, cbt);

									if (empList != null && empList.size() > 0)
									{
										for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
										{
											Object[] object2 = (Object[]) iterator2.next();
											if (object2[1] != null)
												empName.append(object2[1].toString() + ", ");
										}
										if (empName != null && empName.length() > 0)
										{
											dataMap.put("L-3 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
											dataMap.put("L-3 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
										}
										empList.clear();
										empName.setLength(0);

										dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "HDM");
										resolutionDate = dateTime.get(0);
										resolutionTime = dateTime.get(1);
										empList = helperObject.getEmp4Escalation(object[6].toString(), "HDM", "4", connectionSpace, cbt);

										if (empList != null && empList.size() > 0)
										{
											for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
											{
												Object[] object2 = (Object[]) iterator2.next();
												if (object2[1] != null)
													empName.append(object2[1].toString() + ", ");
											}
											if (empName != null && empName.length() > 0)
											{
												dataMap.put("L-4 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
												dataMap.put("L-4 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
											}
											empList.clear();
											empName.setLength(0);
										}
									}
								}
							}
						}
					}
					else if (mainTable.equalsIgnoreCase("feedback_status") && formaterOn.equalsIgnoreCase("closeMode"))
					{
						dataMap = new LinkedHashMap<String, String>();
						editableDataMap = new LinkedHashMap<String, String>();
						dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, complianId);
						if (dataList != null && dataList.size() > 0)
						{
							dataMap = new ActionOnFeedback().getTicketDetails(complianId);
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								editableDataMap.put("Action Taken", CUA.getValueWithNullCheck(object[7]));
								editableDataMap.put("Resources Used", CUA.getValueWithNullCheck(object[8]));
							}
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

	public void getCycleByOrder(List dataList, Map<String, String> dataMap, String actionBy, List tempList)
	{
		ComplianceUniversalAction CUA = new ComplianceUniversalAction();
		finalStatusMap = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> snoozeMap = new LinkedHashMap<String, String>();
		Map<String, String> hpMap = new LinkedHashMap<String, String>();
		Map<String, String> seekMap = new LinkedHashMap<String, String>();
		Map<String, String> reassignMap = new LinkedHashMap<String, String>();
		Map<String, String> ignoreMap = new LinkedHashMap<String, String>();
		Map<String, String> resolvedMap = new LinkedHashMap<String, String>();
		List timeList = new ArrayList();
		for (Entry<String, String> entry : dataMap.entrySet())
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (entry.getKey().equalsIgnoreCase("Snooze"))
				{
					if (object[1] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[1].toString());
						String time = object[2].toString();

						snoozeMap.put("Snooze At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						snoozeMap.put("Snooze At", "NA");

					if (object[3] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[3].toString());
						String time = object[4].toString();

						snoozeMap.put("Snooze Upto", date + ", " + time);
					}
					else
						snoozeMap.put("Snooze Upto", "NA");

					snoozeMap.put("Durartion", CUA.getValueWithNullCheck(object[5]));
					snoozeMap.put("Snooze By", actionBy);
					snoozeMap.put("Reasons", CUA.getValueWithNullCheck(object[6]));
					snoozeMap.put("Total Time", CUA.getValueWithNullCheck(object[5]));
					snoozeMap.put("Working Time", CUA.getValueWithNullCheck(object[5]));

					finalStatusMap.put("Snooze", snoozeMap);
				}
				else if (entry.getKey().equalsIgnoreCase("High Priority"))
				{
					if (object[7] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[7].toString());
						String time = object[8].toString();

						hpMap.put("High Priority At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						hpMap.put("High Priority At", "NA");

					hpMap.put("High Priority By", actionBy);
					hpMap.put("Reasons", CUA.getValueWithNullCheck(object[9]));
					hpMap.put("Action Mode", CUA.getValueWithNullCheck(object[25]));
					String totalTime = DateUtil.time_difference(object[27].toString(), object[28].toString(), object[7].toString(), object[8].toString());
					hpMap.put("Total Time", totalTime);
					hpMap.put("Working Time", totalTime);

					finalStatusMap.put("High Priority", hpMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Ignore"))
				{
					if (object[10] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[10].toString());
						String time = object[11].toString();

						ignoreMap.put("Ignore At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						ignoreMap.put("Ignore At", "NA");

					ignoreMap.put("Ignore By", actionBy);
					ignoreMap.put("Reasons", CUA.getValueWithNullCheck(object[12]));
					ignoreMap.put("Action Mode", CUA.getValueWithNullCheck(object[24]));

					timeList = getTotalAndWorkingTime(object[26].toString(), object[27].toString(), object[28].toString(), CUA.getValueWithNullCheck(object[1]), CUA.getValueWithNullCheck(object[2]), CUA.getValueWithNullCheck(object[3]),
							CUA.getValueWithNullCheck(object[4]), CUA.getValueWithNullCheck(object[5]), CUA.getValueWithNullCheck(object[10]), CUA.getValueWithNullCheck(object[11]), cbt);

					if (timeList != null && timeList.size() > 0)
					{
						hpMap.put("Total Time", timeList.get(0).toString());
						hpMap.put("Working Time", timeList.get(1).toString());
					}
					timeList.clear();
					finalStatusMap.put("Ignore", ignoreMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Resolved"))
				{
					if (object[13] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[13].toString());
						String time = object[14].toString().substring(0, object[14].toString().length() - 3);

						resolvedMap.put("Resolve At", date + ", " + time);
					}
					else
						resolvedMap.put("Resolve At", "NA");

					resolvedMap.put("Total Breakdown", CUA.getValueWithNullCheck(object[15]));
					resolvedMap.put("Resolved By", CUA.getValueWithNullCheck(object[16]));
					resolvedMap.put("RCA", CUA.getValueWithNullCheck(object[17]));
					resolvedMap.put("Resources Used", CUA.getValueWithNullCheck(object[18]));
					resolvedMap.put("Action Mode", CUA.getValueWithNullCheck(object[23]));

					System.out.println(object[26].toString());
					System.out.println(object[27].toString());
					System.out.println(object[28].toString());
					
					timeList = getTotalAndWorkingTime(object[26].toString(), object[27].toString(), object[28].toString(), CUA.getValueWithNullCheck(object[1]), CUA.getValueWithNullCheck(object[2]), CUA.getValueWithNullCheck(object[3]),
							CUA.getValueWithNullCheck(object[4]), CUA.getValueWithNullCheck(object[5]), CUA.getValueWithNullCheck(object[13]), CUA.getValueWithNullCheck(object[14]), cbt);

					if (timeList != null && timeList.size() > 0)
					{
						resolvedMap.put("Total Time", timeList.get(0).toString());
						resolvedMap.put("Working Time", timeList.get(1).toString());
					}
					timeList.clear();
					finalStatusMap.put("Resolved", resolvedMap);
				}
				else if (entry.getKey().equalsIgnoreCase("Re-assign"))
				{
					if (object[19] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[19].toString());
						String time = object[20].toString();

						reassignMap.put("Re-assign At", date + ", " + time.substring(0, time.length() - 3));
					}
					else
						reassignMap.put("Re-assign At", "NA");

					reassignMap.put("Re-assign By", actionBy);
					reassignMap.put("Reason", CUA.getValueWithNullCheck(object[21]));
					timeList = getTotalAndWorkingTime(object[26].toString(), object[27].toString(), object[28].toString(), CUA.getValueWithNullCheck(object[1]), CUA.getValueWithNullCheck(object[2]), CUA.getValueWithNullCheck(object[3]),
							CUA.getValueWithNullCheck(object[4]), CUA.getValueWithNullCheck(object[5]), CUA.getValueWithNullCheck(object[19]), CUA.getValueWithNullCheck(object[20]), cbt);

					if (timeList != null && timeList.size() > 0)
					{
						reassignMap.put("Total Time", timeList.get(0).toString());
						reassignMap.put("Working Time", timeList.get(1).toString());
					}
					timeList.clear();

					finalStatusMap.put("Re-assign", reassignMap);
				}
				else if (entry.getKey().equalsIgnoreCase("SeekApproval"))
				{
					if (tempList != null && tempList.size() > 0)
					{
						String requestedDate = null;
						for (Iterator iterator2 = tempList.iterator(); iterator2.hasNext();)
						{
							Object[] object1 = (Object[]) iterator2.next();
							if (object1[1] != null)
							{

								String date = DateUtil.convertDateToIndianFormat(object1[1].toString());
								String time = object1[2].toString();

								seekMap.put("Request At", date + ", " + time);
							}

							seekMap.put("Requested By", CUA.getValueWithNullCheck(object1[3]));
							seekMap.put("Reasons", CUA.getValueWithNullCheck(object1[4]));
							seekMap.put("Requested To", CUA.getValueWithNullCheck(object1[5]));
							seekMap.put("Current Status", CUA.getValueWithNullCheck(object1[6]));
							if (object1[7] != null)
							{
								String date = DateUtil.convertDateToIndianFormat(object1[7].toString());
								String time = object1[8].toString();
								seekMap.put("Approved At", date + ", " + time);
							}
							else
								seekMap.put("Approved At", "NA");

							seekMap.put("Comments", CUA.getValueWithNullCheck(object1[9]));
						}
						finalStatusMap.put("Seek Approval", seekMap);
					}
				}
			}

		}
	}

	public String editSMSModeData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();

				if (resolutionDetail[0] != null || resolutionDetail[0].equals(""))
					wherClause.put("resolve_remark", resolutionDetail[0]);

				if (resolutionDetail[1] != null || resolutionDetail[1].equals(""))
					wherClause.put("spare_used", resolutionDetail[1]);

				condtnBlock.put("id", complianId);
				boolean updateFeed = new HelpdeskUniversalHelper().updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
				if (updateFeed)
				{
					addActionMessage("Data Update Sucessfully.");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Opps, There are some problem.");
					return ERROR;
				}
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

	public Map<String, String> sortByComparator(Map<String, String> unsortMap, final boolean order)
	{

		List<Entry<String, String>> list = new LinkedList<Entry<String, String>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, String>>()
		{
			public int compare(Entry<String, String> o1, Entry<String, String> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		for (Entry<String, String> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		for (Entry<String, String> entry : sortedMap.entrySet())
		{
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}

		return sortedMap;
	}

	public String getMaxDateValue()
	{
		return maxDateValue;
	}

	public void setMaxDateValue(String maxDateValue)
	{
		this.maxDateValue = maxDateValue;
	}

	public String getMinDateValue()
	{
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
		this.minDateValue = minDateValue;
	}

	public Map<String, String> getFromDept()
	{
		return fromDept;
	}

	public void setFromDept(Map<String, String> fromDept)
	{
		this.fromDept = fromDept;
	}

	public Map<String, String> getToDept()
	{
		return toDept;
	}

	public void setToDept(Map<String, String> toDept)
	{
		this.toDept = toDept;
	}

	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getFromDepart()
	{
		return fromDepart;
	}

	public void setFromDepart(String fromDepart)
	{
		this.fromDepart = fromDepart;
	}

	public String getToDepart()
	{
		return toDepart;
	}

	public void setToDepart(String toDepart)
	{
		this.toDepart = toDepart;
	}

	public String getLodgeMode()
	{
		return lodgeMode;
	}

	public void setLodgeMode(String lodgeMode)
	{
		this.lodgeMode = lodgeMode;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public String getCloseMode()
	{
		return closeMode;
	}

	public void setCloseMode(String closeMode)
	{
		this.closeMode = closeMode;
	}

	public String getEscLevel()
	{
		return escLevel;
	}

	public void setEscLevel(String escLevel)
	{
		this.escLevel = escLevel;
	}

	public String getEscTAT()
	{
		return escTAT;
	}

	public void setEscTAT(String escTAT)
	{
		this.escTAT = escTAT;
	}

	public Map<String, String> getDataCountMap()
	{
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
		this.dataCountMap = dataCountMap;
	}

	public String getReadingConditn()
	{
		return readingConditn;
	}

	public void setReadingConditn(String readingConditn)
	{
		this.readingConditn = readingConditn;
	}

	public String getComplianId()
	{
		return complianId;
	}

	public void setComplianId(String complianId)
	{
		this.complianId = complianId;
	}

	public String getFormaterOn()
	{
		return formaterOn;
	}

	public void setFormaterOn(String formaterOn)
	{
		this.formaterOn = formaterOn;
	}

	public String getMainTable()
	{
		return mainTable;
	}

	public void setMainTable(String mainTable)
	{
		this.mainTable = mainTable;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public Map<String, Map<String, String>> getFinalStatusMap()
	{
		return finalStatusMap;
	}

	public void setFinalStatusMap(Map<String, Map<String, String>> finalStatusMap)
	{
		this.finalStatusMap = finalStatusMap;
	}

	public String getTableAlis()
	{
		return tableAlis;
	}

	public void setTableAlis(String tableAlis)
	{
		this.tableAlis = tableAlis;
	}

	public String getDivName()
	{
		return divName;
	}

	public void setDivName(String divName)
	{
		this.divName = divName;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public String[] getResolutionDetail()
	{
		return resolutionDetail;
	}

	public void setResolutionDetail(String[] resolutionDetail)
	{
		this.resolutionDetail = resolutionDetail;
	}

	public Map<String, String> getEditableDataMap()
	{
		return editableDataMap;
	}

	public void setEditableDataMap(Map<String, String> editableDataMap)
	{
		this.editableDataMap = editableDataMap;
	}

	public String getSeverityLevel()
	{
		return severityLevel;
	}

	public void setSeverityLevel(String severityLevel)
	{
		this.severityLevel = severityLevel;
	}

}