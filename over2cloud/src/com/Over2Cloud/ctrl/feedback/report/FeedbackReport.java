package com.Over2Cloud.ctrl.feedback.report;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.DailyReportExcelWrite4Attach;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.FeedbackReportHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackReportPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackReport implements Action
{
	static final Log log = LogFactory.getLog(FeedbackReport.class);
	private List<ConfigurationUtilBean> reportSearchDD;
	private List<ConfigurationUtilBean> reportSearchTestField;
	private List<String> modesList = null;
	private List<String> feedType = null;
	private String mode, type;
	private List<GridDataPropertyView> masterViewProp;
	private List<Object> masterViewList;
	private Integer rows = 0;
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
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	private String endDate;
	private String startDate;
	private String mailReportData;
	private FeedbackReportPojo FRP = null;
	private FeedbackReportPojo FRP4Counter = null;
	int cfp_Total = 0, cd_Total = 0, cd_Pending = 0, total_snooze = 0, CDR_Total = 0;
	List<FeedbackPojo> currentDayResolvedData = null;
	List<FeedbackPojo> currentDayPendingData = null;
	List<FeedbackPojo> currentDaySnoozeData = null;
	List<FeedbackPojo> currentDayHPData = null;
	List<FeedbackPojo> currentDayIgData = null;
	List<FeedbackPojo> CFData = null;
	List<FeedbackReportPojo> FRPList = null;
	private int dateType=0;
	private String currentDate;

	public String beforeMailReport()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				//System.out.println("Inside Try Block");
				Map session = ActionContext.getContext().getSession();
				SessionFactory connection = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				//System.out.println(">>>>>>>>>>"+startDate);
				if(startDate==null || startDate.equalsIgnoreCase("") || startDate.equalsIgnoreCase("undefined"))
				{
					startDate=DateUtil.getCurrentDateIndianFormat();
				}
				
				startDate=DateUtil.convertDateToUSFormat(startDate);
				
				//startDate=DateUtil.getCurrentDateUSFormat();
				@SuppressWarnings("unused")
				String empname = null, mobno = null, emailid = null, subject = null, report_level = "2", subdept_dept = null, deptLevel = "2", sms_status = null, mail_status = null, type_report = "D", id = null, deptname = null, module = null, department = null;
				// Counts for Current Day
				int pc = 0, hc = 0, sc = 0, ic = 0, rc = 0, total = 0;
				int rAc = 0, cfrAc = 0;
				// Counts for Carry Forward
				@SuppressWarnings("unused")
				int cfpc = 0, cfhc = 0, cfsc = 0, cfic = 0, cfrc = 0, cftotal = 0;
				int totalSnooze = 0;
				FeedbackHelper FRH = new FeedbackHelper();

				List currentdayCounter = new ArrayList();
				List CFCounter = new ArrayList();
				deptLevel = report_level;
				
				StringBuilder sb = new StringBuilder();
				sb.append("select distinct dept.id,dept.deptName from feedback_type as feed");
				sb.append(" inner join department as dept on feed.dept_subdept=dept.id");
				sb.append(" where feed.moduleName='FM'");
				//System.out.println("deprt query>>>>>>>>" + sb);
				List deptList = new HelpdeskUniversalHelper().getData(sb.toString(), connection);
				if (deptList != null && deptList.size() > 0)
				{
					FRPList = new ArrayList<FeedbackReportPojo>();
					for (Iterator iterator2 = deptList.iterator(); iterator2.hasNext();)
					{
						Object[] object2 = (Object[]) iterator2.next();
						FRP = new FeedbackReportPojo();
						// System.out.println("O index value is   "+object2[0].toString());
						CFCounter.clear();
						currentdayCounter.clear();

						FRP.setCFP(0);
						FRP.setCDT(0);
						FRP.setCDP(0);
						FRP.setTS(0);
						FRP.setCDR(0);
						pc = 0;
						hc = 0;
						sc = 0;
						ic = 0;
						rc = 0;
						total = 0;
						rAc = 0;
						cfrAc = 0;
						cfpc = 0;
						cfhc = 0;
						cfsc = 0;
						cfic = 0;
						cfrc = 0;
						cftotal = 0;
						totalSnooze = 0;

						currentdayCounter = getTicketCounters(report_level, type_report, true, object2[0].toString(), deptLevel, connection,cbt);
						CFCounter = getTicketCounters(report_level, type_report, false, object2[0].toString(), deptLevel, connection,cbt);
						if (currentdayCounter != null && currentdayCounter.size() > 0)
						{
							// System.out.println("Current day Counter for "+object2[1].toString());
							for (Iterator iterator3 = currentdayCounter.iterator(); iterator3.hasNext();)
							{
								Object[] object3 = (Object[]) iterator3.next();
								if (object3[1].toString().equalsIgnoreCase("Pending"))
								{
									pc = Integer.parseInt(object3[0].toString());
								} else if (object3[1].toString().equalsIgnoreCase("Snooze"))
								{
									sc = Integer.parseInt(object3[0].toString());
								} else if (object3[1].toString().equalsIgnoreCase("High Priority"))
								{
									hc = Integer.parseInt(object3[0].toString());
								} else if (object3[1].toString().equalsIgnoreCase("Ignore"))
								{
									ic = Integer.parseInt(object3[0].toString());
								} else if (object3[1].toString().equalsIgnoreCase("Resolved"))
								{
									rc = Integer.parseInt(object3[0].toString());
								}
							}
							total = pc + sc + hc + ic + rc;
						}
						// Check Counter for Carry Forward
						if (CFCounter != null && CFCounter.size() > 0)
						{
							// System.out.println("Carry Forward  Counter for "+object2[1].toString());
							for (Iterator iterator4 = CFCounter.iterator(); iterator4.hasNext();)
							{
								Object[] object4 = (Object[]) iterator4.next();
								if (object4[1].toString().equalsIgnoreCase("Pending"))
								{
									cfpc = Integer.parseInt(object4[0].toString());
								} else if (object4[1].toString().equalsIgnoreCase("Snooze"))
								{
									cfsc = Integer.parseInt(object4[0].toString());
								} else if (object4[1].toString().equalsIgnoreCase("High Priority"))
								{
									cfhc = Integer.parseInt(object4[0].toString());
								}
							}
							cftotal = cfpc + cfhc;
						}
						totalSnooze = sc + cfsc;
						// int
						// cfp_Total=0,cd_Total=0,cd_Pending,total_snooze=0,CDR_Total=0;
						if ((CFCounter != null && CFCounter.size() > 0) || (currentdayCounter != null && currentdayCounter.size() > 0))
						{
							// System.out.println("Final Block "+object2[1].toString());
							FRP.setDeptName(object2[1].toString());
							FRP.setCFP(cftotal);
							FRP.setCDT(total);
							FRP.setCDP(pc);
							FRP.setTS(totalSnooze);
							FRP.setCDR(rc);
							FRPList.add(FRP);
							cfp_Total = cfp_Total + cftotal;
							cd_Total = cd_Total + total;
							cd_Pending = cd_Pending + pc;
							total_snooze = total_snooze + totalSnooze;
							CDR_Total = CDR_Total + rc;
						}
					}
				}

				currentDayResolvedData = getTicketDataforReport(report_level, type_report, true, "Resolved", subdept_dept, deptLevel, connection,cbt);
				currentDayPendingData = getTicketDataforReport(report_level, type_report, true, "Pending", subdept_dept, deptLevel, connection,cbt);
				currentDaySnoozeData = getTicketDataforReport(report_level, type_report, true, "Snooze", subdept_dept, deptLevel, connection,cbt);
				currentDayHPData = getTicketDataforReport(report_level, type_report, true, "High Priority", subdept_dept, deptLevel, connection,cbt);
				currentDayIgData = getTicketDataforReport(report_level, type_report, true, "Ignore", subdept_dept, deptLevel, connection,cbt);
				CFData = getTicketDataforReport(report_level, type_report, false, "", subdept_dept, deptLevel, connection,cbt);

				int pp = 0, pn = 0, All = 0, NR = 0, Neg = 0;

				// Code For First Block(Paper Mode)
				List paperData =getPaperData(connection,cbt);

				if (paperData != null && paperData.size() > 0)
				{
					for (Iterator iterator2 = paperData.iterator(); iterator2.hasNext();)
					{
						Object[] object3 = (Object[]) iterator2.next();
						if (object3[0] != null && !object3[0].toString().equals("") && object3[0].toString().equalsIgnoreCase("No"))
						{
							pn = Integer.parseInt(object3[1].toString());
						} else if (object3[0] != null && !object3[0].toString().equals("") && object3[0].toString().equalsIgnoreCase("Yes"))
						{
							pp = Integer.parseInt(object3[1].toString());
						}
					}
				}

				FRP4Counter = new FeedbackReportPojo();
				getSMSDataForTotalSeek(connection,cbt,FRP4Counter);
				
				//FRP4Counter.setSt(All);
				//FRP4Counter.setSnr(NR);
				//FRP4Counter.setSn(Neg);
				List smsList =getRevertedSMSDetailsCurrent(connection,cbt);
				if (smsList != null && smsList.size() > 0)
				{
					for (Iterator iterator3 = smsList.iterator(); iterator3.hasNext();)
					{
						Object[] object3 = (Object[]) iterator3.next();
						if (object3[0] != null && !object3[0].toString().equals("") && object3[0].toString().equalsIgnoreCase("No"))
						{
							if(object3[2]!=null && object3[2].toString().equalsIgnoreCase("IPD"))
							{
								FRP4Counter.setSn(Integer.parseInt(object3[1].toString()));
							}
							else
							{
								FRP4Counter.setSneg(Integer.parseInt(object3[1].toString()));
							}
							
						} else if (object3[0] != null && !object3[0].toString().equals("") && object3[0].toString().equalsIgnoreCase("Yes"))
						{
							if(object3[2]!=null && object3[2].toString().equalsIgnoreCase("IPD"))
							{
								FRP4Counter.setSnr(Integer.parseInt(object3[1].toString()));
							}
							else
							{
								FRP4Counter.setSpos(Integer.parseInt(object3[1].toString()));
							}
						}
					}
				}
				/*int current = 0;
				current =getRevertedSMSDetailsCurrent( connection,cbt);
				NR = pre + current;
				pre = 0;
				current = 0;
				pre = getRevertedSMSDetailsCurrent(false, true, true, connection,cbt);
				current = getRevertedSMSDetailsCurrent(false, true, false, connection,cbt);*/
			
				//Neg = pre + current;

				
				FRP4Counter.setPt(pp + pn);
				FRP4Counter.setPp(pp);
				FRP4Counter.setPn(pn);
				
				// Check Counter for Current day
				if (currentdayCounter != null && currentdayCounter.size() > 0)
				{
					for (Iterator iterator2 = currentdayCounter.iterator(); iterator2.hasNext();)
					{
						Object[] object2 = (Object[]) iterator2.next();
						if (object2[1].toString().equalsIgnoreCase("Pending"))
						{
							pc = Integer.parseInt(object2[0].toString());
						} else if (object2[1].toString().equalsIgnoreCase("Snooze"))
						{
							sc = Integer.parseInt(object2[0].toString());
						} else if (object2[1].toString().equalsIgnoreCase("High Priority"))
						{
							hc = Integer.parseInt(object2[0].toString());
						} else if (object2[1].toString().equalsIgnoreCase("Ignore"))
						{
							ic = Integer.parseInt(object2[0].toString());
						} else if (object2[1].toString().equalsIgnoreCase("Resolved"))
						{
							rc = Integer.parseInt(object2[0].toString());
						}
					}
					total = pc + sc + hc + ic + rc;
				}
				// Check Counter for Carry Forward
				if (CFCounter != null && CFCounter.size() > 0)
				{
					for (Iterator iterator2 = CFCounter.iterator(); iterator2.hasNext();)
					{
						Object[] object3 = (Object[]) iterator2.next();
						if (object3[1].toString().equalsIgnoreCase("Pending"))
						{
							cfpc = Integer.parseInt(object3[0].toString());
						} else if (object3[1].toString().equalsIgnoreCase("Snooze"))
						{
							cfsc = Integer.parseInt(object3[0].toString());
						} else if (object3[1].toString().equalsIgnoreCase("High Priority"))
						{
							cfhc = Integer.parseInt(object3[0].toString());
						}
					}
					cftotal = cfpc + cfhc;
				}
				totalSnooze = sc + cfsc;
				//startDate=DateUtil.convertDateToIndianFormat(startDate);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}
	
	public List getPaperData(SessionFactory connection, CommonOperInterface cbt)
	{
		List counterList = new ArrayList();
		StringBuilder qry = new StringBuilder();
		qry.append("select targetOn,count(*) from feedbackdata");
		qry.append(" where openDate='" + startDate + "'");
		qry.append(" and mode='Paper' group by targetOn");
		//System.out.println("paper query>>>>"+qry);
		counterList = cbt.executeAllSelectQuery(qry.toString(), connection);
		return counterList;
	}
	
	public void getSMSDataForTotalSeek( SessionFactory connection, CommonOperInterface cbt, FeedbackReportPojo fRP4Counter)
	{
		String counterDetails ="";
		//StringBuilder qry = new StringBuilder("select count(*) from instant_msg where msg_text like '%Thanks for availing services at %'");
		//qry.append(" and update_date='" + startDate + "'");
		List counterList = cbt.executeAllSelectQuery("select patient_type,count(*) from patientinfo where sms_date='"+startDate+"' group by patient_type ", connection);
		if (counterList != null && counterList.size() > 0)
		{
			for (Iterator iterator = counterList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					//counterDetails=counterDetails+object[0].toString()+","+Integer.parseInt(object[1].toString())+"#";
					if(object[0].toString().equalsIgnoreCase("IPD"))
					{
						FRP4Counter.setSt(Integer.parseInt(object[1].toString()));
					}
					else if(object[0].toString().equalsIgnoreCase("OPD"))
					{
						FRP4Counter.setSto(Integer.parseInt(object[1].toString()));
					}
				}
			}
		}
	}

	public List getRevertedSMSDetailsCurrent( SessionFactory connection, CommonOperInterface cbt)
	{
		StringBuilder qry = new StringBuilder();
		qry.append("select targetOn,count(*),compType from feedbackdata");
		qry.append(" where openDate='" + startDate + "'");
		qry.append(" and mode='SMS' group by targetOn,compType");
		//System.out.println("SMS query>>>>"+qry);
		List counterList = cbt.executeAllSelectQuery(qry.toString(), connection);
		return counterList;
	}

	public List<FeedbackPojo> getTicketDataforReport(String reportLevel, String reportType, boolean cd_pd, String status, String subdept_dept, String deptLevel, SessionFactory connection, CommonOperInterface cbt)
	{
		List dataList = new ArrayList();
		List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
		
		StringBuilder query = new StringBuilder("");
		query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.patMobNo,feedback.clientId,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");

		// Block for get resolved data
		if (status.equalsIgnoreCase("Resolved"))
		{
			query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
		}
		// Block for get Snooze data
		else if (status.equalsIgnoreCase("Snooze"))
		{
			query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
		}
		// Block for get High Priority data
		else if (status.equalsIgnoreCase("High Priority"))
		{
			query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
		}
		// Block for get ignore data
		else if (status.equalsIgnoreCase("Ignore"))
		{
			query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason");
		}
		
		if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending"))
		{
			query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
		}
		query.append(",subcatg.addressingTime,feedback.escalation_date,feedback.escalation_time");
		query.append(" from feedback_status as feedback");

		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		// Applying inner join at sub department level
		if (deptLevel.equals("2"))
		{
			query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
			query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
			query.append(" inner join department dept2 on feedtype.dept_subdept= dept2.id  ");
			query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id  ");
		}

		// Apply inner join for getting the data for Resolved Ticket
		if (status.equalsIgnoreCase("Resolved"))
		{
			query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
		}

		// getting data of current day
		if (cd_pd)
		{
			query.append(" where feedback.status='" + status + "' ");
			if (reportType != null && !reportType.equals("") && reportType.equals("D"))
			{
				query.append(" and feedback.open_date='" + startDate + "'");
			} 
		}
		// End of If Block for getting the data for only the current date in
		// both case for Resolved OR All

		// Else Block for getting the data for only the previous date
		else
		{
			if (reportLevel.equals("2"))
			{
				query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + startDate + "'");
			} else if (reportLevel.equals("1"))
			{
				query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" +startDate + "' and feedback.to_dept_subdept=" + subdept_dept + " ");
			}
		}
		query.append(" and feedback.moduleName='FM' and feedtype.fbType='Complaint' and feedback.stage='2' ORDER BY feedback.open_date DESC ");
		// //System.out.println("DAta Query  " + query.toString());
		dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			report = new FeedbackUniversalHelper().setFeedbackDataforReport(dataList, status, deptLevel, connection);
		}
		return report;
	}

	public List getTicketCounters(String reportLevel, String reportType, boolean cd_pd, String subdept_dept, String deptLevel, SessionFactory connection, CommonOperInterface cbt)
	{
		List counterList = new ArrayList();
		StringBuilder qry = new StringBuilder();
		if (deptLevel.equals("2"))
		{
			qry.append("select count(*),status from feedback_status as feedback");
			qry.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
			// Applying inner join at sub department level
			qry.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
			qry.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
			qry.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
			qry.append(" inner join department dept2 on feedtype.dept_subdept= dept2.id  ");
			qry.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id  ");

			if (reportLevel.equals("2"))
			{
				if (cd_pd)
				{
					if (reportType != null && !reportType.equals("") && reportType.equals("D"))
					{
						qry.append(" where feedback.open_date='" + startDate + "' ");
					} 
				} else
				{
					qry.append(" where feedback.open_date<'" +startDate + "' ");
				}
			}
			qry.append(" and feedback.moduleName='FM' and feedtype.fbType='Complaint' and feedback.stage='2' and to_dept_subdept ='" + subdept_dept + "' group by status");
		}
		// //System.out.println("Counter Query ::::::::   " + qry.toString());
		counterList = cbt.executeAllSelectQuery(qry.toString(), connection);
		return counterList;
	}

	public void setFeedbackViewProp()
	{
		try
		{

			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String accountID = (String) session.get("accountid");

			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			//System.out.println("Mode is as " + getMode());

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_feedback_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_data_configuration");

			// System.out.println("returnResult is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>."+returnResult.size());
			for (GridDataPropertyView gdp : returnResult)
			{
				if (getMode().equalsIgnoreCase("-1") || getMode().equalsIgnoreCase("SMS"))
				{
					if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("comments") && !gdp.getColomnName().equalsIgnoreCase("tabId") && !gdp.getColomnName().equalsIgnoreCase("refNo") && !gdp.getColomnName().equalsIgnoreCase("blkId") && !gdp.getColomnName().equalsIgnoreCase("nameEmp") && !gdp.getColomnName().equalsIgnoreCase("appFor") && !gdp.getColomnName().equalsIgnoreCase("recommend")
							&& !gdp.getColomnName().equalsIgnoreCase("overAll") && !gdp.getColomnName().equalsIgnoreCase("choseHospital") && !gdp.getColomnName().equalsIgnoreCase("companytype") && !gdp.getColomnName().equalsIgnoreCase("companytype"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (getMode().equalsIgnoreCase("Paper"))
				{
					if (!gdp.getColomnName().equalsIgnoreCase("tabId"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}

			if (getMode().equalsIgnoreCase("SMS"))
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName("revertedOn");
				gpv.setHeadingName("Reverted On");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(50);
				masterViewProp.add(gpv);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setFeedbackNegViewProp()
	{
		try
		{
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String accountID = (String) session.get("accountid");

			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_feedback_negative_data_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_negative_data_configuration");

			for (GridDataPropertyView gdp : returnResult)
			{

				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setFormatter(gdp.getFormatter());
				gpv.setWidth(gdp.getWidth());
				masterViewProp.add(gpv);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String beforeFeedbackReportSearchDataView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getType() != null && getType().equalsIgnoreCase("-1") || getType().equalsIgnoreCase("Positive"))
				{
					setFeedbackViewProp();
				} else if (getType().equalsIgnoreCase("Negative"))
				{
					setFeedbackNegViewProp();
				} else
				{
					setFeedbackViewProp();
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String showFeedbackReportSearchDataGrid()
	{

		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();

				StringBuilder query1 = new StringBuilder(" select count(*) from feedbackdata where id!=0");

				/*
				 * if(getType()!=null && getType().equalsIgnoreCase("Positive"))
				 * { query1.append(" &&  targetOn='Yes'"); } else
				 * if(getType()!=null && getType().equalsIgnoreCase("Negative"))
				 * { query1.append(" &&  targetOn='No'"); }
				 */

				/*
				 * if(getMode()!=null && !getMode().equalsIgnoreCase("-1")) {
				 * query1.append(" && mode='"+getMode()+"'"); }
				 */

				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					StringBuilder query = new StringBuilder("");

					List fieldNames = null;
					if (getType() != null && getType().equalsIgnoreCase("Negative"))
					{
						fieldNames = Configuration.getColomnList("mapped_feedback_negative_data_configuration", accountID, connectionSpace, "feedback_negative_data_configuration");

						query.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," + " feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," + " feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem," + " feedData.compType,feedData.handledBy,feedData.remarks,"
								+ " feedData.kword,feedData.ip,feedData.status,feedData.mode,feedbck.resolve_remark,feedbck.spare_used,feedbck.resolve_date,feedbck.resolve_time from feedback_ticket as feedbt" + " inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " + " inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where feedData.targetOn='No' and  feedbck.status!='Resolved' ");
						if (getStartDate() != null && getEndDate() != null)
						{
							query.append(" && feedData.openDate between '" + DateUtil.convertDateToUSFormat(getStartDate()) + "' and '" + DateUtil.convertDateToUSFormat(getEndDate()) + "' ");
						}

						query.append(" order by feedData.openDate DESC");
					} else
					{
						query.append("select ");
						fieldNames = Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
						int i = 0;
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dyanamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
									query.append(obdata.toString() + ",");
								else
									query.append(obdata.toString());
							}
							i++;

						}

						query.append(" from feedbackdata where id!=0");
						if (getMode() != null && !getMode().equalsIgnoreCase("-1"))
						{
							query.append(" && mode ='" + getMode() + "'");
						}

						if (getType() != null && !getType().equalsIgnoreCase("-1"))
						{
							query.append(" &&  compType='" + getType() + "'");
						}

						if (getStartDate() != null && getEndDate() != null)
						{
							String str[] = getStartDate().split("-");
							if (str[0] != null && str[0].length() > 3)
							{
								query.append(" and openDate between '" + ((getStartDate())) + "' and '" + ((getEndDate())) + "'");
							} else
							{
								query.append(" and openDate between '" + (DateUtil.convertDateToUSFormat(getStartDate())) + "' and '" + (DateUtil.convertDateToUSFormat(getEndDate())) + "'");
							}
						}
					}
					//System.out.println("Query is as >>>" + query);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					// System.out.println("Data ka size is as >>> "+data.size());
					List<Object> listhb = new ArrayList<Object>();
					if (data != null)
					{
						FeedbackReportHelper FRH = new FeedbackReportHelper();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else
									{
										if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("openDate"))
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										} else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("mobNo"))
										{
											one.put(fieldNames.get(k).toString(), (obdata[k].toString()));
											one.put("revertedOn", (DateUtil.convertDateToIndianFormat(FRH.getPatientRevertDate(obdata[k].toString(), connectionSpace))));

										} else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("discharge_datetime"))
										{
											/*
											 * System.out.println(
											 * "Hello i am Avinash "+obdata[k]);
											 * if(!obdata[k].toString().
											 * equalsIgnoreCase("NA") &&
											 * obdata[k]!=null &&
											 * obdata[k].toString().length()>9)
											 * {
											 * one.put(fieldNames.get(k).toString
											 * (),
											 * obdata[k].toString().substring(8,
											 * 10
											 * )+"-"+obdata[k].toString().substring
											 * (5,
											 * 7)+"-"+obdata[k].toString().substring
											 * (0,
											 * 4)+" "+obdata[k].toString().substring
											 * (
											 * obdata[k].toString().indexOf(" ")
											 * ,
											 * obdata[k].toString().length()));
											 * } else
											 */
											{
												one.put(fieldNames.get(k).toString(), (obdata[k].toString()));
											}
										} else
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
									}
									// listhb.add(one);
								}
							}
							listhb.add(one);
						}

						setMasterViewList(listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public void setReportsPageViewProp()
	{
		try
		{
			Map session = ActionContext.getContext().getSession();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String userName = (String) session.get("uName");
			String accountID = (String) session.get("accountid");

			List<GridDataPropertyView> configTableDataList = Configuration.getConfigurationData("mapped_feedback_report_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_report_configuration");
			if (configTableDataList != null && configTableDataList.size() > 0)
			{
				reportSearchDD = new ArrayList<ConfigurationUtilBean>();
				reportSearchTestField = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : configTableDataList)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("smsStatus") && !gdp.getColomnName().equalsIgnoreCase("patientType"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
						{
							objdata.setMandatory(false);
						} else if (gdp.getMandatroy().equalsIgnoreCase("0"))
						{
							objdata.setMandatory(false);
						} else if (gdp.getMandatroy().equalsIgnoreCase("1"))
						{
							objdata.setMandatory(true);
						}

						reportSearchDD.add(objdata);
					} else if (gdp.getColType().trim().equalsIgnoreCase("T"))
					{

						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
						{
							objdata.setMandatory(false);
						} else if (gdp.getMandatroy().equalsIgnoreCase("0"))
						{
							objdata.setMandatory(false);
						} else if (gdp.getMandatroy().equalsIgnoreCase("1"))
						{
							objdata.setMandatory(true);
						}
						reportSearchTestField.add(objdata);
					}

					if (gdp.getColomnName().equalsIgnoreCase("mode"))
					{
						modesList = new ArrayList<String>();
						modesList.add("Paper");
						modesList.add("SMS");
					} else if (gdp.getColomnName().equalsIgnoreCase("type"))
					{
						feedType = new ArrayList<String>();
						feedType.add("Positive");
						feedType.add("Negative");
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String beforeFeedbackReportSearchView()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				setReportsPageViewProp();
				setStartDate(DateUtil.getNextDateAfter(-6));
				setEndDate(DateUtil.getNextDateAfter(0));
				return SUCCESS;
			} catch (Exception e)
			{
				log.error("Exception in class " + getClass() + " on " + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTimeHourMin(), e);
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@Override
	public String execute() throws Exception
	{
		return SUCCESS;
	}

	public List<ConfigurationUtilBean> getReportSearchDD()
	{
		return reportSearchDD;
	}

	public void setReportSearchDD(List<ConfigurationUtilBean> reportSearchDD)
	{
		this.reportSearchDD = reportSearchDD;
	}

	public List<String> getModesList()
	{
		return modesList;
	}

	public void setModesList(List<String> modesList)
	{
		this.modesList = modesList;
	}

	public List<String> getFeedType()
	{
		return feedType;
	}

	public void setFeedType(List<String> feedType)
	{
		this.feedType = feedType;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
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

	public List<ConfigurationUtilBean> getReportSearchTestField()
	{
		return reportSearchTestField;
	}

	public void setReportSearchTestField(List<ConfigurationUtilBean> reportSearchTestField)
	{
		this.reportSearchTestField = reportSearchTestField;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getMailReportData()
	{
		return mailReportData;
	}

	public void setMailReportData(String mailReportData)
	{
		this.mailReportData = mailReportData;
	}

	public FeedbackReportPojo getFRP()
	{
		return FRP;
	}

	public void setFRP(FeedbackReportPojo fRP)
	{
		FRP = fRP;
	}

	public FeedbackReportPojo getFRP4Counter()
	{
		return FRP4Counter;
	}

	public void setFRP4Counter(FeedbackReportPojo fRP4Counter)
	{
		FRP4Counter = fRP4Counter;
	}

	public int getCfp_Total()
	{
		return cfp_Total;
	}

	public void setCfp_Total(int cfp_Total)
	{
		this.cfp_Total = cfp_Total;
	}

	public int getCd_Total()
	{
		return cd_Total;
	}

	public void setCd_Total(int cd_Total)
	{
		this.cd_Total = cd_Total;
	}

	public int getCd_Pending()
	{
		return cd_Pending;
	}

	public void setCd_Pending(int cd_Pending)
	{
		this.cd_Pending = cd_Pending;
	}

	public int getTotal_snooze()
	{
		return total_snooze;
	}

	public void setTotal_snooze(int total_snooze)
	{
		this.total_snooze = total_snooze;
	}

	public int getCDR_Total()
	{
		return CDR_Total;
	}

	public void setCDR_Total(int cDR_Total)
	{
		CDR_Total = cDR_Total;
	}

	public List<FeedbackPojo> getCurrentDayResolvedData()
	{
		return currentDayResolvedData;
	}

	public void setCurrentDayResolvedData(List<FeedbackPojo> currentDayResolvedData)
	{
		this.currentDayResolvedData = currentDayResolvedData;
	}

	public List<FeedbackPojo> getCurrentDayPendingData()
	{
		return currentDayPendingData;
	}

	public void setCurrentDayPendingData(List<FeedbackPojo> currentDayPendingData)
	{
		this.currentDayPendingData = currentDayPendingData;
	}

	public List<FeedbackPojo> getCurrentDaySnoozeData()
	{
		return currentDaySnoozeData;
	}

	public void setCurrentDaySnoozeData(List<FeedbackPojo> currentDaySnoozeData)
	{
		this.currentDaySnoozeData = currentDaySnoozeData;
	}

	public List<FeedbackPojo> getCurrentDayHPData()
	{
		return currentDayHPData;
	}

	public void setCurrentDayHPData(List<FeedbackPojo> currentDayHPData)
	{
		this.currentDayHPData = currentDayHPData;
	}

	public List<FeedbackPojo> getCurrentDayIgData()
	{
		return currentDayIgData;
	}

	public void setCurrentDayIgData(List<FeedbackPojo> currentDayIgData)
	{
		this.currentDayIgData = currentDayIgData;
	}

	public List<FeedbackPojo> getCFData()
	{
		return CFData;
	}

	public void setCFData(List<FeedbackPojo> cFData)
	{
		CFData = cFData;
	}

	public List<FeedbackReportPojo> getFRPList()
	{
		return FRPList;
	}

	public void setFRPList(List<FeedbackReportPojo> fRPList)
	{
		FRPList = fRPList;
	}

	public int getDateType()
	{
		return dateType;
	}

	public void setDateType(int dateType)
	{
		this.dateType = dateType;
	}

	public String getCurrentDate()
	{
		return currentDate;
	}

	public void setCurrentDate(String currentDate)
	{
		this.currentDate = currentDate;
	}

}
