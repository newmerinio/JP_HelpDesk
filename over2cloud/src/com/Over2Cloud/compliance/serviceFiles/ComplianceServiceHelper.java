package com.Over2Cloud.compliance.serviceFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class ComplianceServiceHelper
{
	@SuppressWarnings("rawtypes")
	List dataList = new ArrayList();

	@SuppressWarnings("rawtypes")
	public List getReminderData(SessionFactory connectionSpace)
	{
		try
		{
			String query;
			query = "SELECT ct.taskName,cd.frequency,cd.taskBrief,cd.msg,cd.remindMode,cd.reminder_for,cr.id AS reminderId,cd.id AS " + "compId,cd.dueDate,cd.dueTime,cd.uploadDoc,cr.reminder_code,cr.remind_date,cty.taskType,cr.reminder_name,cd.escalation,cd.apply_working_day,cd.remind_days FROM compliance_details AS cd INNER JOIN " + "compliance_task ct on ct.id=cd.taskName INNER JOIN compl_task_type cty on cty.id=ct.taskType INNER JOIN compliance_reminder cr ON "
					+ "cr.compliance_id=cd.id WHERE cr.remind_date <='" + DateUtil.getNewDate("day", -1, DateUtil.getCurrentDateUSFormat()) + "' AND cd.status=0 AND cr.reminder_status=0 AND cr.reminder_code<>'S'";
			System.out.println("first remind query>>>"+query);
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);

			if (dataList.size() == 0)
			{
				query = "SELECT ct.taskName,cd.frequency,cd.taskBrief,cd.msg,cd.remindMode,cd.reminder_for,cr.id AS reminderId,cd.id AS " + "compId,cd.dueDate,cd.dueTime,cd.uploadDoc,cr.reminder_code,cr.remind_date,cty.taskType,cr.reminder_name,cd.escalation,cd.apply_working_day,cd.remind_days FROM compliance_details AS cd INNER JOIN " + "compliance_task ct on ct.id=cd.taskName INNER JOIN compl_task_type cty on cty.id=ct.taskType INNER JOIN compliance_reminder cr ON "
						+ "cr.compliance_id=cd.id WHERE cr.remind_date <='" + DateUtil.getCurrentDateUSFormat() + "' AND cr.remind_time <='" + DateUtil.getCurrentTimeHourMin() + "' AND cd.status=0 AND cr.reminder_status=0 AND cr.reminder_code<>'S'";
				System.out.println("second remind query>>>"+query);
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getEscalationData(SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT ct.taskName,cd.frequency,cd.taskBrief,cd.msg,cd.remindMode,cd.reminder_for,cd.dueDate,cd.dueTime,cd.escalation_level_1," + "cd.l1EscDuration,cd.escalation_level_2,cd.l2EscDuration,cd.escalation_level_3,cd.l3EscDuration,cd.escalation_level_4," + "cd.l4EscDuration,cd.current_esc_level,cd.id AS compId,cty.taskType FROM compliance_details AS cd INNER JOIN compliance_task ct on ct.id=cd.taskName "
					+ "INNER JOIN compl_task_type AS cty ON cty.id=ct.taskType WHERE cd.escalation='Y' " + "AND cd.actionTaken='Pending' AND cd.status=0 AND cd.current_esc_level<5 AND cd.escalate_date='" + DateUtil.getCurrentDateUSFormat() + "' AND cd.escalate_time<='" + DateUtil.getCurrentTimeHourMin() + "'";
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getRecurringData(SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT cd.id,frequency,escalation,dueDate,dueTime,escalation_level_1,l1EscDuration,escalation_level_2,l2EscDuration," + "escalation_level_3,l3EscDuration,escalation_level_4,l4EscDuration,uploadDoc,uploadDoc1,cd.userName,ct.departName,cd.apply_working_day,cd.remind_days,cd.noofdaybefore FROM compliance_details AS cd INNER JOIN compliance_task AS ct ON cd.taskName=ct.id WHERE " + "dueDate<'" + DateUtil.getCurrentDateUSFormat()
					+ "' AND actionTaken='Recurring'  AND frequency!='OT'";
			//System.out.println(query);
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public List getPendingData(SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT cd.id,frequency,dueDate,dueTime,ct.departName,start_date,start_time,remind_days,apply_working_day,prevStartDate FROM compliance_details AS cd INNER JOIN compliance_task AS ct ON cd.taskName=ct.id WHERE " + "dueDate<'" + DateUtil.getCurrentDateUSFormat() + "'  AND frequency!='OT' AND cd.status=0 AND taskStatus IS NULL";
			//System.out.println("pending query>>>>>>"+query);
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getReminderDate(String compId, SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT reminder_code,remind_date,remind_interval,id FROM compliance_reminder WHERE compliance_id='" + compId + "'";
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public List getAllReminderData(String compId, String mode, SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT * FROM compliance_reminder WHERE compliance_id='" + compId + "' and reminder_code IN(" + mode + ")";
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public List getComplianceDetailData(String compId, SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT * FROM compliance_details WHERE id='" + compId + "'";
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public int getMaximumComplId(SessionFactory connection)
	{

		List complList = null;
		int maxComplId = 0;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		qryString.append("select max(id) from compliance_details");
		complList = cbt.executeAllSelectQuery(qryString.toString(), connection);
		for (int i = 0; i < complList.size(); i++)
		{
			maxComplId = (Integer) complList.get(0);
		}
		return maxComplId;
	}

	/*
	 * @SuppressWarnings("unchecked") public List getSnoozeData(SessionFactory
	 * connectionSpace) { try { String query=
	 * "SELECT cd.id,cd.frequency,cd.escalation,cd.dueDate,cd.dueTime,cd.escalation_level_1,cd.l1EscDuration,cd.escalation_level_2,"
	 * +
	 * "cd.l2EscDuration,cd.escalation_level_3,cd.l3EscDuration,cd.escalation_level_4,cd.l4EscDuration,cd.snooze_date,cd.snooze_time,"
	 * +
	 * "cd.current_esc_level,cd.reminder_for,cd.msg,ctn.taskName,cty.taskType,cd.taskBrief FROM compliance_details AS cd INNER JOIN compliance_task AS ctn ON "
	 * +
	 * "ctn.id=cd.taskName  INNER JOIN compl_task_type AS cty ON cty.id=ctn.taskType WHERE cd.snooze_date<='"
	 * +DateUtil.getCurrentDateUSFormat()+"' AND cd.snooze_time<='"+DateUtil.
	 * getCurrentTimeHourMin()+"' AND cd.actionTaken='Snooze'"; dataList=new
	 * createTable().executeAllSelectQuery(query,connectionSpace); } catch
	 * (Exception e) { e.printStackTrace(); } return dataList; }
	 */
	@SuppressWarnings("rawtypes")
	public List getSnoozeData(SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT cd.id,cd.frequency,cd.escalation,cd.dueDate,cd.dueTime,cd.escalation_level_1,cd.l1EscDuration,cd.escalation_level_2," + "cd.l2EscDuration,cd.escalation_level_3,cd.l3EscDuration,cd.escalation_level_4,cd.l4EscDuration,cd.snooze_date,cd.snooze_time," + "cd.current_esc_level,cd.reminder_for,cd.msg,ctn.taskName,cty.taskType,cd.taskBrief,cd.comments FROM compliance_details AS cd INNER JOIN compliance_task AS ctn ON "
					+ "ctn.id=cd.taskName  INNER JOIN compl_task_type AS cty ON cty.id=ctn.taskType WHERE cd.snooze_date<='" + DateUtil.getCurrentDateUSFormat() + "' AND cd.snooze_time<='" + DateUtil.getCurrentTimeHourMin() + "' AND cd.actionTaken='Snooze'";
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getTodayReminderFor(SessionFactory connectionSpace, String workingDate, String conditionType)
	{
		try
		{
			String query = null;
			if (conditionType != null && conditionType.equalsIgnoreCase("equalCondition"))
			{
				query = "SELECT id,reminder_for FROM compliance_details WHERE dueDate='" + DateUtil.getCurrentDateUSFormat() + "' AND actionTaken='Pending'";
			} else if (conditionType.equalsIgnoreCase("betweenCondition"))
			{
				query = "SELECT id,reminder_for FROM compliance_details WHERE dueDate BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + workingDate + "' AND actionTaken='Pending'";
			}
			if (query != null)
			{
				dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	List getAllMissedPendingData(SessionFactory connectionSpace,String workingDate, String conditionType, String todayDate)
	{
		 try 
		 {
			 String query = null;
			if(conditionType!=null && conditionType.equalsIgnoreCase("equalCondition"))
			{
				query="SELECT id,reminder_for FROM compliance_details WHERE (dueDate<='"+DateUtil.getCurrentDateUSFormat()+"' )AND actionTaken='Pending'";
			}
			else if(conditionType.equalsIgnoreCase("betweenCondition"))
			{
				query="SELECT id,reminder_for FROM compliance_details WHERE ((dueDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+workingDate+"') OR dueDate<'"+DateUtil.getCurrentDateUSFormat()+"') AND actionTaken='Pending'";
			}
			//System.out.println("reminder for>>>>>"+query);
			if(query!=null)
			{
				dataList=new createTable().executeAllSelectQuery(query,connectionSpace);
			}
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 } 
		 return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getTodayPendingData(SessionFactory connectionSpace)
	{
		try
		{
			String query = "SELECT ct.taskName,cd.frequency,cd.taskBrief,cd.msg,cd.id,cd.dueDate,cd.dueTime,cd.reminder_for,cd.actionStatus " + "FROM compliance_details AS cd INNER JOIN compliance_task ct ON ct.id=cd.taskName WHERE cd.dueDate='" + DateUtil.getCurrentDateUSFormat() + "' AND cd.actionTaken='Pending'";
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public boolean updateCompliaceAccordingToFrequency(String id, String frequency, String remindDate, SessionFactory connectionSpace, String remindDays)
	{
		boolean returnFlag = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (frequency != null && id != null && frequency != "" && id != "")
		{
			Map<String, Object> setVariables = new HashMap<String, Object>();
			Map<String, Object> whereCondition = new HashMap<String, Object>();
			String nextUpdateDate = "";
			whereCondition.put("id", id);
			setVariables.put("reminder_status", "311");
			if (frequency.equals("OT"))
			{
				returnFlag = cbt.updateTableColomn("compliance_reminder", setVariables, whereCondition, connectionSpace);
			}
			else if (frequency.equals("D"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
				{
					nextUpdateDate = DateUtil.getNewDate("day", 1, remindDate);
				}
				if (nextUpdateDate != null)
					setVariables.put("remind_date", nextUpdateDate);
			}
			else if (frequency.equals("W"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
					nextUpdateDate = DateUtil.getNewDate("week", 1, remindDate);

				setVariables.put("remind_date", nextUpdateDate);
			}
			else if (frequency.equals("BM"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
					nextUpdateDate = DateUtil.getNewDate("day", 15, remindDate);

				setVariables.put("remind_date", nextUpdateDate);
			} 
			else if (frequency.equals("M"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
					nextUpdateDate = DateUtil.getNewDate("month", 1, remindDate);

				setVariables.put("remind_date", nextUpdateDate);
			}
			else if (frequency.equals("Q"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
					nextUpdateDate = DateUtil.getNewDate("month", 3, remindDate);

				setVariables.put("remind_date", nextUpdateDate);
			} 
			else if (frequency.equals("HY"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
					nextUpdateDate = DateUtil.getNewDate("month", 6, remindDate);

				setVariables.put("remind_date", nextUpdateDate);
			}
			else if (frequency.equals("Y"))
			{
				if (remindDate != null && !remindDate.equalsIgnoreCase("NA"))
					nextUpdateDate = DateUtil.getNewDate("year", 1, remindDate);

				setVariables.put("remind_date", nextUpdateDate);

			}
			else if (frequency.equalsIgnoreCase("O") && remindDays != null && !remindDays.equalsIgnoreCase("NA"))
			{
				String customFrqOn = remindDays.split("#")[0];
				String days = remindDays.split("#")[1];
				if (days != null && customFrqOn != null && !customFrqOn.equalsIgnoreCase("-1"))
				{
					if (customFrqOn.equalsIgnoreCase("D"))
						setVariables.put("remind_date", DateUtil.getNewDate("day", Integer.parseInt(days), remindDate));
					else if (customFrqOn.equalsIgnoreCase("M"))
						setVariables.put("remind_date", DateUtil.getNewDate("month", Integer.parseInt(days), remindDate));
					else if (customFrqOn.equalsIgnoreCase("Y"))
						setVariables.put("remind_date", DateUtil.getNewDate("year", Integer.parseInt(days), remindDate));
				}
			}
			returnFlag = cbt.updateTableColomn("compliance_reminder", setVariables, whereCondition, connectionSpace);
		}
		return returnFlag;
	}

	public boolean updateNextEscDate(String id, SessionFactory connectionSpace, int nextEscLevel, String nextEscDateTime)
	{
		boolean returnFlag = false;
		if (id != null && id != "")
		{
			String arr[] = nextEscDateTime.split("#");
			Session session = null;
			Transaction transaction = null;
			try
			{
				session = connectionSpace.openSession();
				transaction = session.beginTransaction();
				Query query = session.createSQLQuery("UPDATE compliance_details SET current_esc_level='" + nextEscLevel + "',escalate_date='" + arr[0] + "',escalate_time='" + arr[1] + "' WHERE id=" + id);
				int count = query.executeUpdate();
				if (count > 0)
					returnFlag = true;
				transaction.commit();

			} catch (Exception ex)
			{
				ex.printStackTrace();
				transaction.rollback();
			}
		}
		return returnFlag;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List getReportTime(SessionFactory connectionSpace)
	{
		List dataList = new ArrayList();
		try
		{

			dataList = new createTable().executeAllSelectQuery("SHOW TABLES LIKE 'compl_report_time'", connectionSpace);
			if (dataList == null)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				InsertDataTable ob = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<TableColumes> tableColume = new ArrayList<TableColumes>();

				TableColumes ob1 = new TableColumes();

				ob1 = new TableColumes();
				ob1.setColumnname("reportDate");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("reportTime");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("msg");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("frequency");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("dueDate");
				ob1.setDatatype("varchar(15)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("dueTime");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("configStatus");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				boolean status = cbt.createTable22("comp_excelupload_data", tableColume, connectionSpace);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public List getReportData(SessionFactory connectionSpace, String reportLevel, String reportType, String deptId, String workingDate, String conditionType)
	{
		List reportDataList = new ArrayList();
		try
		{
			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("SELECT comp.id,comp.frequency,comp.taskBrief,comp.reminder_for,comp.dueDate,comp.actionTaken,compTask.taskName,compType.taskType,dept.deptName ");
			stringBuilder.append("FROM compliance_details AS comp ");
			stringBuilder.append("INNER JOIN compliance_task AS compTask ON compTask.id=comp.taskName ");
			stringBuilder.append("INNER JOIN department AS dept ON dept.id=compTask.departName ");
			stringBuilder.append("INNER JOIN compl_task_type AS compType ON compType.id=compTask.taskType ");
			stringBuilder.append("WHERE");
			if (reportLevel != null && reportLevel.equalsIgnoreCase("1") && deptId != null)
			{
				stringBuilder.append(" compTask.departName=" + deptId + " AND");
			}

			if (reportType != null)
			{

				if (reportType.equalsIgnoreCase("D"))
				{
					if (conditionType != null && conditionType.equalsIgnoreCase("equalCondition"))
					{
						// query="SELECT id,reminder_for FROM compliance_details WHERE dueDate='"+DateUtil.getCurrentDateUSFormat()+"' AND actionTaken='Pending'";
						stringBuilder.append(" dueDate='" + DateUtil.getCurrentDateUSFormat() + "'");
					} else if (conditionType.equalsIgnoreCase("betweenCondition"))
					{
						// query="SELECT id,reminder_for FROM compliance_details WHERE dueDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+workingDate+"' AND actionTaken='Pending'";
						stringBuilder.append(" dueDate BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + workingDate + "'");
					}
				}
				/*
				 * else if(reportType.equalsIgnoreCase("W")) {
				 * stringBuilder.append
				 * (" dueDate BETWEEN '"+DateUtil.getNewDate("week",-7,
				 * DateUtil.getCurrentDateUSFormat())+"' AND '"+DateUtil.
				 * getCurrentDateUSFormat()+"'"); } else
				 * if(reportType.equalsIgnoreCase("M")) {
				 * stringBuilder.append(" dueDate BETWEEN '"
				 * +DateUtil.getNewDate("month",-30,
				 * DateUtil.getCurrentDateUSFormat
				 * ())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'"); }
				 */
			}
			reportDataList = new createTable().executeAllSelectQuery(stringBuilder.toString(), connectionSpace);
			//System.out.println("stringBuilder.toString() :::::::::" + stringBuilder.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return reportDataList;
	}

	@SuppressWarnings("rawtypes")
	public List getTodayActionData(SessionFactory connectionSpace, String reportLevel, String reportType, String deptId, String workingDate, String conditionType)
	{
		List actionDataList = new ArrayList();
		try
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT comp.id,comp.frequency,comp.taskBrief,comp.reminder_for,comp.dueDate,compReport.actionTaken,compTask.taskName,compType.taskType,dept.deptName,usac.name,compReport.comments ");
			stringBuilder.append("FROM compliance_report AS compReport ");
			stringBuilder.append("INNER JOIN compliance_details AS comp ON comp.id=compReport.complID ");
			stringBuilder.append("INNER JOIN compliance_task AS compTask ON compTask.id=comp.taskName ");
			stringBuilder.append("INNER JOIN department AS dept ON dept.id=compTask.departName ");
			stringBuilder.append("INNER JOIN compl_task_type AS compType ON compType.id=compTask.taskType ");
			stringBuilder.append("INNER JOIN useraccount AS usac ON usac.userID=compReport.userName ");
			stringBuilder.append("WHERE compReport.actionTaken<>'Pending'");
			/*
			 * if(reportLevel!=null && reportLevel.equalsIgnoreCase("1") &&
			 * deptId!=null) {
			 * stringBuilder.append(" AND compTask.departName="+deptId); }
			 */

			if (reportType != null)
			{
				if (reportType.equalsIgnoreCase("D"))
				{
					// stringBuilder.append(" AND compReport.actionTakenDate='"+DateUtil.getCurrentDateUSFormat()+"'");
					if (conditionType != null && conditionType.equalsIgnoreCase("equalCondition"))
					{
						stringBuilder.append(" AND compReport.actionTakenDate='" + DateUtil.getCurrentDateUSFormat() + "'");
					} else if (conditionType.equalsIgnoreCase("betweenCondition"))
					{
						stringBuilder.append(" AND compReport.actionTakenDate BETWEEN '" + DateUtil.getCurrentDateUSFormat() + "' AND '" + workingDate + "'");
					}
				}
				/*
				 * else if(reportType.equalsIgnoreCase("W")) {
				 * stringBuilder.append
				 * (" AND compReport.actionTakenDate BETWEEN '"
				 * +DateUtil.getNewDate("week",-7,
				 * DateUtil.getCurrentDateUSFormat
				 * ())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'"); } else
				 * if(reportType.equalsIgnoreCase("M")) {
				 * stringBuilder.append(" AND compReport.actionTakenDate BETWEEN '"
				 * +DateUtil.getNewDate("month",-30,
				 * DateUtil.getCurrentDateUSFormat
				 * ())+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'"); }
				 */
			}
			actionDataList = new createTable().executeAllSelectQuery(stringBuilder.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return actionDataList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCompIdPrefix_Suffix(String deptId, String frq, SessionFactory connectionSpace)
	{
		List dataList = new ArrayList();
		String complSeries = new HelpdeskUniversalHelper().getTicketNo(deptId, "COMPL", connectionSpace);
		if (!frq.equalsIgnoreCase("-1"))
		{
			if (frq.equalsIgnoreCase("OT") || frq.equalsIgnoreCase("BM") || frq.equalsIgnoreCase("HY"))
			{
				frq = String.valueOf(frq.charAt(0));
			}
		}
		if (complSeries != null)
		{
			String prefix = complSeries.substring(0, 3);
			prefix = prefix.concat(frq);
			dataList.add(prefix);
			String suffix = complSeries.substring(4, complSeries.length());
			dataList.add(suffix);
		}
		return dataList;
	}

	public List getLastCompActionData(String compId, SessionFactory connectionSpace)
	{
		List lastActionDataList = new ArrayList();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT actionTakenDate,usac.name,comments FROM compliance_report AS compReport");
			query.append(" INNER JOIN useraccount AS usac ON usac.userID=compReport.userName WHERE complID='" + compId + "'");
			query.append(" AND (actionTaken='Recurring' OR actionTaken='Done' OR actionTaken='Snooze') ORDER BY actionTakenDate DESC LIMIT 1");
			lastActionDataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return lastActionDataList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getEmployeeInfo(String contactId,SessionFactory connectionSpace)
	{

		List data = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query2 = "SELECT emp.id AS empId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON " +
			"emp.id=cc.emp_id " +
			"INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.work_status!=1 " +
			"AND cc.id IN(" + contactId + ") GROUP BY emp.id";
			//System.out.println("Contacts List query ::: "+query2);
			data = cbt.executeAllSelectQuery(query2, connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public List getEmployeeName(String contactId,SessionFactory connectionSpace)
	{

		List data = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query2 = "SELECT emp.empName AS empId FROM compliance_contacts AS cc INNER JOIN employee_basic AS emp ON " +
			"emp.id=cc.emp_id " +
			"INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cc.work_status!=1 " +
			"AND cc.id IN(" + contactId + ") GROUP BY emp.empName";
			//System.out.println("Contacts List query ::: "+query2);
			data = cbt.executeAllSelectQuery(query2, connectionSpace);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
}
