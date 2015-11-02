package com.Over2Cloud.ctrl.feedback.feedbackaction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.catalina.startup.SetAllPropertiesRule;
import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.feedback.report.FeedbackHelper;
import com.Over2Cloud.ctrl.feedback.service.FeedbackViaTab;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.activity.ActivityPojo;
import com.Over2Cloud.ctrl.feedback.activity.ActivityTakeAction;
import com.Over2Cloud.ctrl.feedback.beanUtil.PatientReportPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ActionOnFeedback extends ActionSupport
{
	private final MsgMailCommunication communication = new MsgMailCommunication();
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	private final CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String feedid;
	private String ticket_no;
	private String resolver;
	private String remark;
	private String status;
	private String addrDate;
	private String addrTime;
	private String open_date;
	private String open_time;
	private String feedbackBy;
	private String mobileno;
	private String catg;
	private String subCatg;
	private String feed_brief;
	// private String allotto;
	private String location;

	private String resolveon;
	private String resolveat;
	private String spareused;
	private String actiontaken;

	private String hpcomment;
	private String ignorecomment;

	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;

	private String todept;
	private String tosubdept;
	private String reAssignRemark;

	private String feedStatus;
	private String headingTitle;

	private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";

	private String fromDate = "";
	private String toDate = "";
	private String dataFlag = "N";

	private String dashFor = "";
	private String d_subdept_id = "";

	// /////////////////////////
	private String ticketNo = "";
	private String openDate = "";
	private String openTime = "";
	private String emailId = "";
	private String feedBreif = "";
	private String feedId = "";
	private String dischargeDateTime = "NA";
	private String allotto;
	private String deptId;
	// ////////////////////////

	private String subCategory;

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
	private boolean loadonce = true;
	// Grid colomn view
	private String oper;

	private String mode;
	private int id;

	private String crNo;
	private String patTyp;
	private String patObsrvtn;
	private String docName;
	private String roomNo;
	private String patName;
	private String brief;
	private String feedType;
	private String feedCat;
	private String feedBy;
	private String feedDataId;
	private String totalPending;
	private String totalResolved;
	private String totalTickets;
	private Map<String, String> feedTypeMap;
	private Map<String, String> feedCatMap;
	private Map<String, String> ticketNoMap;
	private Map<String, String> crNoMap;
	private Map<String, String> feedByMap;

	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	List<GridDataPropertyView> feedbackColumnNames = new ArrayList<GridDataPropertyView>();

	private Map<Integer, String> resolverList = new HashMap<Integer, String>();
	private List<String> statusList = new ArrayList<String>();

	// Added on 10-June For resolve doc upload
	public File resolveDoc;
	private String resolveDocContentType;
	private String resolveDocFileName;
	public File resolveDoc1;
	private String resolveDoc1ContentType;
	private String resolveDoc1FileName;

	// on 21 June For Noted Option
	private String actionComments;

	public String beforeActionRedirect()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ActivityBoardHelper actHelper = new ActivityBoardHelper();
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				int adminDeptId = actHelper.getAdminDeptId(connectionSpace);
				EmpBasicPojoBean empPojo = actHelper.getLoggedUserDetails(connectionSpace, userName, deptLevel);
				if (adminDeptId != 0)
				{
					if (empPojo.getDeptName().toString().equalsIgnoreCase(empPojo.getDeptName()))
					{
						return "adminView";
					} else
					{
						return "deptView";
					}
				} else
				{
					return "ERROR";
				}
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String redirectToJSPActivity()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				//String userName = (String) session.get("uName");
				//String deptLevel = "2";
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				// System.out.println("Feed ticket is as "+getTicketNo());

				int feedStatId = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, "select id from feedback_status_pdm where ticket_no='" + getTicketNo() + "'");

				PatientReportPojo pojo = new FeedbackHelper().getPatientDetails(String.valueOf(feedStatId), connectionSpace);
				if (pojo != null)
				{
					if (pojo.getPatientId() != null)
					{
						setCrNo(pojo.getPatientId());
					}

					if (pojo.getPatientName() != null)
					{
						setPatName(pojo.getPatientName());
					}

					if (pojo.getPatType() != null)
					{
						setPatTyp(pojo.getPatType());
					}
					if (pojo.getDocName() != null)
					{
						setDocName(pojo.getDocName());
					}
					if (pojo.getRoomNo() != null)
					{
						setRoomNo(pojo.getRoomNo());
					}
					if (pojo.getMobNo() != null)
					{
						setMobileno(pojo.getMobNo());
					}

					if (pojo.getEmailId() != null)
					{
						setEmailId(pojo.getEmailId());
					}
					if (pojo.getDischargeDateTime() != null)
					{
						setDischargeDateTime(pojo.getDischargeDateTime());
					}
				}
				StringBuilder query = new StringBuilder("select subCat.id, subCat.sub_category_name,feedStat.feed_brief,feedStat.feed_register_by,feedStat.status,feedStat.id as feedStatId,emp.emp_name");
				query.append(" from feedback_subcategory as subCat ");
				query.append(" inner join feedback_status_pdm as feedStat on subCat.id=feedStat.sub_catg ");
				query.append(" inner join primary_contact as emp on feedStat.allot_to=emp.id");
				query.append(" where feedStat.ticket_no='" + getTicketNo() + "'");
				//System.out.println(">>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<in sub cat"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[1] != null)
							{
								setSubCategory(object[1].toString());
							}
							if (object[2] != null)
							{
								setBrief(object[2].toString());
							}
							if (object[3] != null)
							{
								setFeedBy(object[3].toString());
							}
							if (object[4] != null)
							{
								setStatus(object[4].toString());
							}
							if (object[5] != null)
							{
								setFeedId(object[5].toString());
							}
							if (object[6] != null)
							{
								setAllotto(object[6].toString());
							}
						}
					}
				}

				boolean isATicket = new FeedbackUniversalHelper().getTicketIsComplaintOrNot(getFeedId(), connectionSpace);
				// System.out.println("It is a ticket or not >> "+isATicket);
				// System.out.println("feed status>>>>>>>>>>"+status);
				if (isATicket)
				{
					statusList.add("Resolved");
					if (status.equalsIgnoreCase("Pending"))
					{
						statusList.add("Snooze");
						statusList.add("Re-Assign");
						statusList.add("High Priority");
					} else if (status.equalsIgnoreCase("Snooze"))
					{
						statusList.add("High Priority");
					} else if (status.equalsIgnoreCase("High Priority"))
					{
						statusList.add("Snooze");
					}
					statusList.add("Ignore");
				} else
				{
					statusList.add("Acknowledged");
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String redirectToJSP()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				//System.out.println("Feed Id is as " + getTicketNo());
				// Setting Patients Data for Action Pageif(getFeedId()!=null &&
				// !getFeedId().equalsIgnoreCase(""))
				String userName = (String) session.get("uName");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				PatientReportPojo pojo = new FeedbackHelper().getPatientDetails(getFeedId(), connectionSpace);
				if (pojo != null)
				{
					if (pojo.getPatientId() != null)
					{
						setCrNo(pojo.getPatientId());
					}
					if (pojo.getPatType() != null)
					{
						setPatTyp(pojo.getPatType());
					}
					if (pojo.getDocName() != null)
					{
						setDocName(pojo.getDocName());
					}
					if (pojo.getRoomNo() != null)
					{
						setRoomNo(pojo.getRoomNo());
					}
					if (pojo.getMobNo() != null)
					{
						setMobileno(pojo.getMobNo());
					}

					if (pojo.getEmailId() != null)
					{
						setEmailId(pojo.getEmailId());
					}
					if (pojo.getDischargeDateTime() != null)
					{
						setDischargeDateTime(pojo.getDischargeDateTime());
					}
				}

				boolean isATicket = new FeedbackUniversalHelper().getTicketIsComplaintOrNot(getFeedId(), connectionSpace);
				// System.out.println("It is a ticket or not >> "+isATicket);
				if (isATicket)
				{
					statusList.add("Resolved");
					if (feedStatus.equalsIgnoreCase("Pending"))
					{
						statusList.add("Snooze");
						statusList.add("Re-Assign");
						statusList.add("High Priority");
					} else if (feedStatus.equalsIgnoreCase("Snooze"))
					{
						statusList.add("High Priority");
					} else if (feedStatus.equalsIgnoreCase("High Priority"))
					{
						statusList.add("Snooze");
					}
					statusList.add("Ignore");
				} else
				{
					statusList.add("Noted");
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String dashRedirect()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				statusList.add("Resolved");
				if (feedStatus.equalsIgnoreCase("Pending"))
				{
					statusList.add("Snooze");
					statusList.add("High Priority");
				} else if (feedStatus.equalsIgnoreCase("Snooze"))
				{
					statusList.add("High Priority");
				} else if (feedStatus.equalsIgnoreCase("High Priority"))
				{
					statusList.add("Snooze");
				}
				statusList.add("Ignore");
				// statusList.add("Re-Assign");
				returnResult = SUCCESS;

				// Setting Patients Data for Action Page

				if (getFeedId() != null)
				{
					String userName = (String) session.get("uName");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					PatientReportPojo pojo = new FeedbackHelper().getPatientDetails(getFeedId(), connectionSpace);
					if (pojo != null)
					{
						if (pojo.getPatientId() != null)
						{
							setCrNo(pojo.getPatientId());
						}
						if (pojo.getPatType() != null)
						{
							setPatTyp(pojo.getPatType());
						}
						if (pojo.getDocName() != null)
						{
							setDocName(pojo.getDocName());
						}
						if (pojo.getRoomNo() != null)
						{
							setRoomNo(pojo.getRoomNo());
						}
						if (pojo.getMobNo() != null)
						{
							setMobileno(pojo.getMobNo());
						}

						if (pojo.getEmailId() != null)
						{
							setEmailId(pojo.getEmailId());
						}
						if (pojo.getDischargeDateTime() != null)
						{
							setDischargeDateTime(pojo.getDischargeDateTime());
						}
					}

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String getReAllotPage()
	{
		return SUCCESS;
	}

	public String beforeActionSearchPage()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// System.out.println("dashFor value is as <>>>>>>>>>>>>>>>>..."+dashFor);
				if (dashFor != null && getDeptId() == null && feedStatus.equals("Pending"))
				{
					headingTitle = "Pending Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Snooze"))
				{
					headingTitle = "Snooze Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Ignore"))
				{
					headingTitle = "Ignore Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Re-Assign"))
				{
					headingTitle = "Re-Assign Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("High Priority"))
				{
					headingTitle = "High Priority Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Resolved"))
				{
					fromDate = DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					headingTitle = "Resolved Feedback >> View";
					returnResult = SUCCESS;
				} else if (getDeptId() != null && getFeedStatus() != null)
				{
					// System.out.println("Department ID is as ><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+getDeptId());
					// System.out.println("Status is as ><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<><><><"+getFeedStatus());
					// System.out.println("Search Filed is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getSearchField());
					// System.out.println("Search String is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getSearchString());
					returnResult = "dashsuccess";
				}
				setMode(getMode());
				setGridColomnNames();

				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				String empId = null;
				String dept_subdept_id = null;
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_subdept_id = object[3].toString();
						empId = object[5].toString();
					}
					FeedbackHelper helper = new FeedbackHelper();

					if (dept_subdept_id != null)
					{
						totalTickets = helper.getDeptTicketsCounters("", dept_subdept_id, empId, connectionSpace);
					} else
					{
						totalTickets = helper.getDeptTicketsCounters("", "", "", connectionSpace);
					}

				}

				ticketNoMap = new HashMap<String, String>();
				List<String> toDeptList = new FeedbackUniversalAction().getLoggedInEmpForDept(empId, dept_subdept_id, "FM", connectionSpace);
				ticketNoMap = getTicketNoMap(toDeptList, connectionSpace);

				crNoMap = new HashMap<String, String>();
				crNoMap = getCRNoMap(toDeptList, connectionSpace);

				feedByMap = new HashMap<String, String>();
				feedByMap = getDeptFeedBy(toDeptList, connectionSpace);

				feedTypeMap = new HashMap<String, String>();
				feedTypeMap = getDeptFeedType(toDeptList, connectionSpace);

				feedCatMap = new HashMap<String, String>();
				feedCatMap = getDeptFeedCat(toDeptList, connectionSpace);

				fromDate = DateUtil.getNextDateAfter(-6);
				toDate = DateUtil.getCurrentDateUSFormat();

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}

		return returnResult;

	}

	public String beforeActionOnFeedback()
	{
		// System.out.println("CR NO is as "+getCrNo());

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// System.out.println("dashFor value is as <>>>>>>>>>>>>>>>>..."+dashFor);
				if (dashFor != null && getDeptId() == null && feedStatus.equals("Pending"))
				{
					headingTitle = "Pending Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Snooze"))
				{
					headingTitle = "Snooze Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Ignore"))
				{
					headingTitle = "Ignore Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Re-Assign"))
				{
					headingTitle = "Re-Assign Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("High Priority"))
				{
					headingTitle = "High Priority Feedback >> View";
					returnResult = SUCCESS;
				} else if ((dashFor != null && getDeptId() == null) && feedStatus.equals("Resolved"))
				{
					fromDate = DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
					toDate = DateUtil.getCurrentDateUSFormat();
					headingTitle = "Resolved Feedback >> View";
					returnResult = SUCCESS;
				} else if (getDeptId() != null && getFeedStatus() != null)
				{
					// System.out.println("Department ID is as ><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+getDeptId());
					// System.out.println("Status is as ><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<><><><"+getFeedStatus());
					// System.out.println("Search Filed is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getSearchField());
					// System.out.println("Search String is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getSearchString());
					returnResult = "dashsuccess";
				}
				setMode(getMode());
				setGridColomnNames();

				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				String empId = null;
				String dept_subdept_id = null;
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_subdept_id = object[3].toString();
						empId = object[5].toString();
					}
					FeedbackHelper helper = new FeedbackHelper();

					if (dept_subdept_id != null)
					{
						totalTickets = helper.getDeptTicketsCounters("", dept_subdept_id, empId, connectionSpace);
					} else
					{
						totalTickets = helper.getDeptTicketsCounters("", "", "", connectionSpace);
					}

				}
				ticketNoMap = new HashMap<String, String>();
				List<String> toDeptList = new FeedbackUniversalAction().getLoggedInEmpForDept(empId, dept_subdept_id, "FM", connectionSpace);
				ticketNoMap = getTicketNoMap(toDeptList, connectionSpace);
				feedByMap = new HashMap<String, String>();
				feedByMap = getDeptFeedBy(toDeptList, connectionSpace);

				feedTypeMap = new HashMap<String, String>();
				feedTypeMap = getDeptFeedType(toDeptList, connectionSpace);

				feedCatMap = new HashMap<String, String>();
				feedCatMap = getDeptFeedCat(toDeptList, connectionSpace);
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public Map<String, String> getTicketNoMap(List<String> deptList, SessionFactory connectionSpace)
	{
		ticketNoMap = new HashMap<String, String>();
		List ticketNoList = null;
		if (deptList != null && deptList.size() > 0)
		{
			ticketNoList = cbt.executeAllSelectQuery("select distinct ticket_no from feedback_status_pdm where to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")"), connectionSpace);

		} else
		{
			ticketNoList = cbt.executeAllSelectQuery("select distinct ticket_no from feedback_status_pdm where id!=0 ", connectionSpace);
		}

		if (ticketNoList != null && ticketNoList.size() > 0)
		{
			for (Iterator iterator = ticketNoList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					ticketNoMap.put(object.toString(), object.toString());
				}
			}
		}
		return ticketNoMap;
	}

	public Map<String, String> getCRNoMap(List<String> deptList, SessionFactory connectionSpace)
	{
		ticketNoMap = new HashMap<String, String>();
		List ticketNoList = null;
		if (deptList != null && deptList.size() > 0)
		{
			ticketNoList = cbt.executeAllSelectQuery("select distinct clientid from feedback_status_pdm where to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")"), connectionSpace);

		} else
		{
			ticketNoList = cbt.executeAllSelectQuery("select distinct clientId from feedback_status where id!=0 ", connectionSpace);
		}

		if (ticketNoList != null && ticketNoList.size() > 0)
		{
			for (Iterator iterator = ticketNoList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					ticketNoMap.put(object.toString(), object.toString());
				}
			}
		}
		return ticketNoMap;
	}

	public Map<String, String> getDeptFeedBy(List<String> deptList, SessionFactory connectionSpace)
	{
		feedByMap = new HashMap<String, String>();
		List feedByList = null;
		if (deptList != null && deptList.size() > 0)
		{
			feedByList = cbt.executeAllSelectQuery("select distinct feed_by from feedback_status_pdm where to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ") and moduleName='FM' order by feed_by"), connectionSpace);
		} else
		{
			feedByList = cbt.executeAllSelectQuery("select distinct feed_by from feedback_status_pdm order by feed_by ", connectionSpace);
		}

		if (feedByList != null && feedByList.size() > 0)
		{
			for (Iterator iterator = feedByList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					feedByMap.put(object.toString(), object.toString());
				}
			}
		}
		return feedByMap;
	}

	public Map<String, String> getDeptFeedType(List<String> deptList, SessionFactory connectionSpace)
	{
		feedTypeMap = new HashMap<String, String>();
		StringBuilder builder = new StringBuilder(" select distinct feedtype.fb_type from feedback_status_pdm" + " as feedback inner join primary_contact emp on feedback.allot_to= emp.id " + " inner join contact_sub_type dept1 on feedback.by_dept_subdept= dept1.id " + " inner join contact_sub_type dept2 on feedback.to_dept_subdept= dept2.id " + " inner join feedback_subcategory subcatg on feedback.sub_catg = subcatg.id " + " inner join feedback_category catg on subcatg.category_name = catg.id "
				+ " inner join feedback_type feedtype on catg.fb_type = feedtype.id ");

		if (deptList != null && deptList.size() > 0)
		{
			builder.append(" and feedback.to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")") + "");
		} else
		{
			builder.append(" and feedback.id!=0");
		}

		List feedCatList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);

		if (feedCatList != null && feedCatList.size() > 0)
		{
			for (Iterator iterator = feedCatList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null && object != null)
				{
					feedTypeMap.put(object.toString(), object.toString());
				}
			}
		}
		return feedTypeMap;
	}

	public Map<String, String> getDeptFeedCat(List<String> deptList, SessionFactory connectionSpace)
	{
		feedCatMap = new HashMap<String, String>();
		StringBuilder builder = new StringBuilder(" select catg.category_name from feedback_status_pdm" + " as feedback inner join primary_contact emp on feedback.allot_to= emp.id " + " inner join contact_sub_type dept1 on feedback.by_dept_subdept= dept1.id " + " inner join contact_sub_type dept2 on feedback.to_dept_subdept= dept2.id " + " inner join feedback_subcategory subcatg on feedback.sub_catg = subcatg.id " + " inner join feedback_category catg on subcatg.category_name = catg.id "
				+ " inner join feedback_type feedtype on catg.fb_type = feedtype.id ");

		if (deptList != null && deptList.size() > 0)
		{
			builder.append(" and feedback.to_dept_subdept in " + deptList.toString().replace("[", "(").replace("]", ")") + "");
		} else
		{
			builder.append(" and feedback.id!=0");
		}

		List feedCatList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);

		if (feedCatList != null && feedCatList.size() > 0)
		{
			for (Iterator iterator = feedCatList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					feedCatMap.put(object.toString(), object.toString());
				}
			}
		}
		return feedCatMap;
	}

	public void setGridColomnNames()
	{
		feedbackColumnNames = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("cr_no");
		gpv.setHeadingName("MRD No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_date");
		gpv.setHeadingName("Addr Date");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedaddressing_time");
		gpv.setHeadingName("Addr Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Pending") || getFeedStatus().equals("HP"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_date");
			gpv.setHeadingName("Esc Date");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("escalation_time");
			gpv.setHeadingName("Esc Time");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);
		}

		/*
		 * gpv = new GridDataPropertyView();
		 * gpv.setColomnName("feedback_by_dept"); gpv.setHeadingName("By Dept");
		 * gpv.setAlign("center"); gpv.setWidth(100);
		 * feedbackColumnNames.add(gpv);
		 */
		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Patient Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("patMobNo");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("patEmailId");
		gpv.setHeadingName("Email Id");
		gpv.setHideOrShow("false");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Room No");
		gpv.setWidth(100);
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_to_dept");
		gpv.setHeadingName("To Department");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_catg");
		gpv.setHeadingName("Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_subcatg");
		gpv.setHeadingName("Sub Category");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_brief");
		gpv.setAlign("center");
		gpv.setHeadingName("Brief");
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Snooze"))
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_reason");
			gpv.setHeadingName("Snooze Reason");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_date");
			gpv.setHeadingName("Snooze On");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_on_time");
			gpv.setHeadingName("Snooze At");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_date");
			gpv.setHeadingName("Snooze Up To");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_upto_time");
			gpv.setHeadingName("Snooze Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sn_duration");
			gpv.setHeadingName("Snooze Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}

		if (getFeedStatus().equals("High Priority"))
		{

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_date");
			gpv.setHeadingName("HP Date");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_time");
			gpv.setHeadingName("HP Time");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("hp_reason");
			gpv.setHeadingName("HP Reason");
			gpv.setWidth(100);
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}

		if (getFeedStatus().equals("Noted"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Noted On");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Noted At");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Total Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_by");
			gpv.setHeadingName("Noted By");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("Noted Comment");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
		}
		if (getFeedStatus().equals("Resolved"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_date");
			gpv.setHeadingName("Resolved On");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_time");
			gpv.setHeadingName("Resolved At");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_duration");
			gpv.setHeadingName("Res. Duration");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_by");
			gpv.setHeadingName("Resolve By");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("resolve_remark");
			gpv.setHeadingName("CAPA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("spare_used");
			gpv.setHeadingName("RCA");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("actionComments");
			gpv.setHeadingName("Comments");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);

		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setAlign("center");
		gpv.setWidth(100);
		gpv.setHideOrShow("true");
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("via_from");
		gpv.setHeadingName("Mode");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_mobno");
		gpv.setHeadingName("Open By Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedback_by_emailid");
		gpv.setHeadingName("Open By Email Id");
		gpv.setHideOrShow("false");
		gpv.setAlign("center");
		gpv.setWidth(100);
		feedbackColumnNames.add(gpv);

		if (getFeedStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("feed_registerby");
			gpv.setHeadingName("Register By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}
		if (!getFeedStatus().equals("Pending"))
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("action_by");
			gpv.setHeadingName("Action By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("feedtype");
		gpv.setHeadingName("Feedback Type");
		gpv.setAlign("center");
		feedbackColumnNames.add(gpv);
	}

	@SuppressWarnings("unchecked")
	public String getFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				String userType = (String) session.get("userTpe");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				Map<String, List> whereClauseList = new HashMap<String, List>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				int count = 0;
				List data = null;
				String dept_subdept_id = "";
				String loggedEmpId = "";
				String loggedEmpName = "";
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpName = object[0].toString();
						dept_subdept_id = object[3].toString();
						loggedEmpId = object[5].toString();
					}
				}
				// System.out.println("CR No Is as "+getCrNo());
				if (dept_subdept_id != null && !dept_subdept_id.equals(""))
				{
					data = new FeedbackUniversalAction().getFeedbackDetail("feedback_status", getFeedStatus(), fromDate, toDate, dept_subdept_id, loggedEmpId, "ticket_no", "DESC", searchField, searchString, searchOper, connectionSpace, getMode(), getFeedType(), getFeedCat(), getTicket_no(), getFeedBy(), getCrNo(), userType, loggedEmpName);
				}

				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();

				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					feedbackList = new FeedbackUniversalAction().setFeedbackValues(data, deptLevel, getFeedStatus());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String geTicketDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				String orgName = "", address = "", city = "", pincode = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
					address = orgData[1];
					city = orgData[2];
					pincode = orgData[3];
				}
				setTicket_no(ticket_no);
				setFeedbackBy(feedbackBy);
				setMobileno(mobileno);
				setOpen_date(open_date);
				setOpen_time(open_time);
				setCatg(catg);
				setFeed_brief(feed_brief);
				setLocation(location);
				// setAllotto(allotto);
				setTodept(todept);
				setTosubdept(tosubdept);
				setAddrDate(addrDate);
				setAddrTime(addrTime);

				setOrgName(orgName);
				setAddress(address);
				setCity(city);
				setPincode(pincode);

				if (feedStatus.equalsIgnoreCase("Resolved"))
				{
					setResolveon(resolveon);
					setResolveat(resolveat);
					setActiontaken(actiontaken);
					setSpareused(spareused);
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getTicketDetails Method");
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		// System.out.println(returnResult+"sdvc nsd vdn");
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getResolverData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String deptLevel = (String) session.get("userDeptLevel");

				String dept_subdept = "";
				String sub_catg = "";

				@SuppressWarnings("unused")
				createTable cbt = new createTable();
				List empdata = new ArrayList<String>();
				if (feedid != null && !feedid.equals(""))
				{
					dept_subdept = new FeedbackUniversalAction().getField("feedback_status", "to_dept_subdept", "id", feedid, connectionSpace);
					sub_catg = new FeedbackUniversalAction().getField("feedback_status", "sub_Catg", "id", feedid, connectionSpace);
				}
				String tolevel = "";
				// Code for getting the Escalation Date and Escalation Time
				List subCategoryList = new FeedbackUniversalAction().getAllData("feedback_subcategory", "id", sub_catg, "subCategoryName", "ASC");
				if (subCategoryList != null && subCategoryList.size() > 0)
				{
					for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
					{
						Object[] objectCol = (Object[]) iterator.next();

						if (objectCol[8] == null)
						{
							tolevel = "Level1";
						} else
						{
							tolevel = objectCol[8].toString();
						}
					}
				}

				// empdata = new
				// FeedbackUniversalAction().getEmp4Escalation(dept_subdept,
				// deptLevel,"",tolevel, connectionSpace);
				// if (empdata==null || empdata.size()==0) {
				String deptid = new FeedbackUniversalAction().getData("subdepartment", "deptid", "id", dept_subdept);
				// empdata = new
				// FeedbackUniversalAction().getEmp4EscInAllDept(deptid,
				// deptLevel,"",tolevel, connectionSpace);

				// }
				if (empdata != null && empdata.size() > 0)
				{
					for (Object c : empdata)
					{
						Object[] dataC = (Object[]) c;
						resolverList.put((Integer) dataC[0], dataC[1].toString());
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public boolean getResTicketIsForAdminOrNot(String feedId, SessionFactory connectionSpace)
	{
		boolean flag = false;
		Properties configProp = new Properties();
		String adminDeptName = null;
		// Note Change Selected ID Value
		try
		{
			InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
			configProp.load(in);
			adminDeptName = configProp.getProperty("admin");
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		int adminDeptId = 0;
		if (adminDeptName != null)
		{
			adminDeptId = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, "select id from contact_sub_type where contact_subtype_name='" + adminDeptName + "'");
			if (adminDeptId != 0 && adminDeptId > 0)
			{
				int toDeptId = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, "select to_dept_subdept from feedback_status_pdm where id='" + feedId + "'");
				if (toDeptId != 0 && toDeptId == adminDeptId)
				{
					flag = true;
				} else
				{
					flag = false;
				}
			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public String addCommentForResolvedFeedback()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getFeedid() != null)
				{
					String userName = (String) session.get("uName");
					String deptLevel = (String) session.get("userDeptLevel");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					wherClause.put("actionComments", getActionComments());
					wherClause.put("action_by", userName);
					condtnBlock.put("id", getFeedid());
					boolean updateFeed = new FeedbackUniversalHelper().updateTableColomn("feedback_status", wherClause, condtnBlock, connectionSpace);
					if (updateFeed)
					{
						addActionMessage("Ticket Updated Successfully !!! ");
						return SUCCESS;
					} else
					{
						addActionMessage("Problem in taking Ticket Action !!! ");
						return SUCCESS;
					}
				} else
				{
					addActionMessage("Problem in taking Ticket Action !!! ");
					return SUCCESS;
				}
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String updateFeedbackStatus()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				//System.out.println("" + getFeedDataId());

				String userName = (String) session.get("uName");
				String deptLevel = "2";
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				FeedbackPojo fbp = new FeedbackPojo();
				String duration = "";
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				// GenericMailSender mailSender = new GenericMailSender();
				// boolean mailFlag = false;

				String snDate = "", snTime = "", snUpToDate = "", snUpToTime = "", snDuration = "", escDate = null, escTime = null, moduleName = null;
				StringBuilder query = new StringBuilder();
				query.append("SELECT action_date,action_time,snooze_upto_date,snooze_upto_time,action_duration,escalation_date,escalation_time FROM feedback_status_pdm AS fsp ");
				query.append("LEFT JOIN feedback_status_pdm_history AS his ON fsp.id=his.feed_id ");
				query.append(" WHERE fsp.id =" + getFeedId());
				List ticketData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (ticketData != null && ticketData.size() > 0)
				{
					for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && !object[0].toString().equals(""))
						{
							snDate = object[0].toString();
						} else
						{
							snDate = "NA";
						}
						if (object[1] != null && !object[1].toString().equals(""))
						{
							snTime = object[1].toString();
						} else
						{
							snTime = "NA";
						}
						if (object[2] != null && !object[2].toString().equals(""))
						{
							snUpToDate = object[2].toString();
						} else
						{
							snUpToDate = "NA";
						}
						if (object[3] != null && !object[3].toString().equals(""))
						{
							snUpToTime = object[3].toString();
						} else
						{
							snUpToTime = "NA";
						}
						if (object[4] != null && !object[4].toString().equals(""))
						{
							snDuration = object[4].toString();
						} else
						{
							snDuration = "NA";
						}

						if (object[5] != null && !object[5].toString().equals(""))
						{
							escDate = object[5].toString();
						}

						if (object[6] != null && !object[6].toString().equals(""))
						{
							escTime = object[6].toString();
						}

						/*if (object[7] != null && !object[7].toString().equals(""))
						{
							moduleName = object[7].toString();
						}*/
					}
				}
				InsertDataTable ob = null;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

				if (getStatus().equalsIgnoreCase("Acknowledged"))
				{
					// Get resolver Id which is already a alloted person
					/*String resolver = null;
					if (feedId != null && !feedId.equalsIgnoreCase(""))
					{
						resolver = new FeedbackUniversalAction().getField("feedback_status_pdm", "allot_to", "id", feedId, connectionSpace);
					}*/ /*else
					{
						resolver = new FeedbackUniversalAction().getField("feedback_status_pdm", "allot_to", "id", feedId, connectionSpace);
					}*/

					String cal_duration = "";
					if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
					{
						duration = DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					}
					if (!snDuration.equals("") && !snDuration.equals("NA"))
					{
						boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
						if (flag)
						{
							// System.out.println("Inside If Block");
							cal_duration = DateUtil.getTimeDifference(duration, snDuration);
							if (cal_duration != null && !cal_duration.equals("") && !cal_duration.contains("-"))
							{
								duration = cal_duration;
							}
						} else
						{
							// System.out.println("Inside Else Block");
							String newduration = DateUtil.time_difference(snDate, snTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
							if (newduration != null && !newduration.equals("") && !newduration.equals("NA"))
							{
								String new_cal_duration = DateUtil.getTimeDifference(duration, newduration);
								if (new_cal_duration != null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
								{
									duration = new_cal_duration;
								}
							}
						}
					}

					wherClause.put("status", getStatus());
					insertData.add(setValues("status",getStatus()));
					insertData.add(setValues("feed_id", getFeedId()));
					insertData.add(setValues("action_by",session.get("empIdofuser").toString().split("-")[1]));
					insertData.add(setValues("action_date",DateUtil.getCurrentDateUSFormat()));
					insertData.add(setValues("action_time",DateUtil.getCurrentTime()));
					insertData.add(setValues("action_duration",duration));
					insertData.add(setValues("action_remark",DateUtil.makeTitle(getActionComments())));
					if (resolveDocFileName != null)
					{
						String renameFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + DateUtil.mergeDateTime() + getResolveDocFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + getResolveDocFileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(resolveDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								} catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									insertData.add(setValues("resolve_doc",renameFilePath));
								}
							}
						}
					}
					if (resolveDoc1FileName != null)
					{
						String renameFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + DateUtil.mergeDateTime() + getResolveDoc1FileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + getResolveDoc1FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(resolveDoc1, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								} catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									insertData.add(setValues("resolve_doc1",renameFilePath));
								}
							}
						}
					}
					condtnBlock.put("id", getFeedId());
				}
				if (getStatus().equalsIgnoreCase("Resolved"))
				{
					// Get resolver Id which is already a alloted person
					//String resolver = new FeedbackUniversalAction().getField("feedback_status", "allot_to", "id", feedId, connectionSpace);
					String cal_duration = "";
					if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
					{
						duration = DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					}
					if (!snDuration.equals("") && !snDuration.equals("NA"))
					{
						boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
						if (flag)
						{
							// System.out.println("Inside If Block");
							cal_duration = DateUtil.getTimeDifference(duration, snDuration);
							if (cal_duration != null && !cal_duration.equals("") && !cal_duration.contains("-"))
							{
								duration = cal_duration;
							}
						} else
						{
							// System.out.println("Inside Else Block");
							String newduration = DateUtil.time_difference(snDate, snTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
							if (newduration != null && !newduration.equals("") && !newduration.equals("NA"))
							{
								String new_cal_duration = DateUtil.getTimeDifference(duration, newduration);
								if (new_cal_duration != null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
								{
									duration = new_cal_duration;
								}
							}
						}
					}
					
					wherClause.put("status", getStatus());
					insertData.add(setValues("status",getStatus()));
					insertData.add(setValues("feed_id", getFeedId()));
					insertData.add(setValues("action_by",session.get("empIdofuser").toString().split("-")[1]));
					insertData.add(setValues("action_date",DateUtil.getCurrentDateUSFormat()));
					insertData.add(setValues("action_time",DateUtil.getCurrentTime()));
					insertData.add(setValues("action_duration",duration));
					insertData.add(setValues("action_remark",getSpareused()));
					insertData.add(setValues("capa",getRemark()));
					if (resolveDocFileName != null)
					{
						String renameFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + DateUtil.mergeDateTime() + getResolveDocFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + getResolveDocFileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(resolveDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								} catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									insertData.add(setValues("resolve_doc",renameFilePath));
								}
							}
						}
					}
					if (resolveDoc1FileName != null)
					{
						String renameFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + DateUtil.mergeDateTime() + getResolveDoc1FileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Feedback_data") + "//" + getResolveDoc1FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(resolveDoc1, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								} catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									insertData.add(setValues("resolve_doc1",renameFilePath));
								}
							}
						}
					}
					
					condtnBlock.put("id", getFeedId());
				} else if (getStatus().equalsIgnoreCase("Snooze"))
				{
					
						wherClause.put("status", getStatus());
						insertData.add(setValues("status", getStatus()));
						insertData.add(setValues("feed_id", getFeedId()));
						insertData.add(setValues("action_date",DateUtil.getCurrentDateUSFormat()));
						insertData.add(setValues("action_time",DateUtil.getCurrentTime()));
						insertData.add(setValues("action_remark", DateUtil.makeTitle(getSnoozecomment())));
						insertData.add(setValues("snooze_upto_date",DateUtil.convertDateToUSFormat(getSnoozeDate())));
						insertData.add(setValues("snooze_upto_time", getSnoozeTime()));
					//	insertData.add(setValues("action_duration", duration));
						insertData.add(setValues("action_by",session.get("empIdofuser").toString().split("-")[1]));
					//	wherClause.put("escalation_date", dateTime.get(2));
					//	wherClause.put("escalation_time", dateTime.get(3));
						condtnBlock.put("id", getFeedId());
					//}
					
				} else if (getStatus().equalsIgnoreCase("High Priority"))
				{
					wherClause.put("status", getStatus());
					insertData.add(setValues("status", getStatus()));
					insertData.add(setValues("feed_id", getFeedId()));
					insertData.add(setValues("action_date",DateUtil.getCurrentDateUSFormat()));
					insertData.add(setValues("action_time",DateUtil.getCurrentTime()));
					insertData.add(setValues("action_remark", DateUtil.makeTitle(getHpcomment())));
					insertData.add(setValues("action_by",session.get("empIdofuser").toString().split("-")[1]));
					condtnBlock.put("id", getFeedId());
				} else if (getStatus().equalsIgnoreCase("Ignore"))
				{
					wherClause.put("status", getStatus());
					insertData.add(setValues("status", getStatus()));
					insertData.add(setValues("feed_id", getFeedId()));
					insertData.add(setValues("action_date",DateUtil.getCurrentDateUSFormat()));
					insertData.add(setValues("action_time",DateUtil.getCurrentTime()));
					insertData.add(setValues("action_remark", DateUtil.makeTitle(getIgnorecomment())));
					insertData.add(setValues("action_by",session.get("empIdofuser").toString().split("-")[1]));
					condtnBlock.put("id", getFeedId());
					
				} else if (getStatus().equalsIgnoreCase("Re-Assign"))
				{
					String feedBy = "NA", mobNo = "NA", emailId = "NA", bysubdept = "NA", feedBrief = "NA", location = "NA", toDept = "NA",clientId="",patientId="",via_from="",feedDataId="",patMobNo="NA",patEmailId="NA";
					List existTicketData = getTransferTicketData(getFeedId(), connectionSpace);
					if (existTicketData != null && existTicketData.size() > 0)
					{
						for (Iterator iterator = existTicketData.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && !object[0].toString().equals(""))
							{
								feedBy = object[0].toString();
							}
							
							if (object[1] != null && !object[1].toString().equals(""))
							{
								bysubdept = object[1].toString();
							}
							if (object[2] != null && !object[2].toString().equals(""))
							{
								feedBrief = object[2].toString();
							}
							if (object[3] != null && !object[3].toString().equals(""))
							{
								location = object[3].toString();
							}
							if (object[4] != null && !object[4].toString().equals(""))
							{
								ticket_no = object[4].toString();
							}

							if (object[5] != null && !object[5].toString().equals(""))
							{
								toDept = object[5].toString();
							}
							if (object[6] != null && !object[6].toString().equals(""))
							{
								clientId = object[6].toString();
							}
							if (object[7] != null && !object[7].toString().equals(""))
							{
								patientId = object[7].toString();
							}
							if (object[8] != null && !object[8].toString().equals(""))
							{
								feedDataId = object[8].toString();
							}
							if (object[9] != null && !object[9].toString().equals(""))
							{
								via_from = object[9].toString();
							}
						}
						boolean flag = transferComplaint(feedBy, bysubdept, feedBrief, location, getFeedId(), toDept,clientId,patientId,feedDataId,via_from, connectionSpace);
						if (flag)
						{
							wherClause.put("status", "Re-Assign");
							insertData.add(setValues("status", "Re-Assign"));
							insertData.add(setValues("feed_id", getFeedId()));
							insertData.add(setValues("action_date",DateUtil.getCurrentDateUSFormat()));
							insertData.add(setValues("action_time",DateUtil.getCurrentTime()));
							if (getReAssignRemark() != null && !getReAssignRemark().equals(""))
							{
								insertData.add(setValues("action_remark", DateUtil.makeTitle(getReAssignRemark())));
							} else
							{
								insertData.add(setValues("action_remark", "NA"));
							}
							
							insertData.add(setValues("action_by",session.get("empIdofuser").toString().split("-")[1]));
							condtnBlock.put("id", getFeedId());
						}
					}
				}
				boolean isInserted=cbt.insertIntoTable("feedback_status_pdm_history", insertData, connectionSpace);
				if(isInserted)
				{
					boolean updateFeed = new FeedbackUniversalHelper().updateTableColomn("feedback_status_pdm", wherClause, condtnBlock, connectionSpace);
					boolean smsFlag = new FeedbackUniversalHelper().getTicketIsComplaintOrNot(getFeedId(), connectionSpace);
					if (smsFlag && updateFeed)
					{
						{
							String levelMsg = "", mailText = "", mailSubject = "", mailheading = "";
							List data = new FeedbackUniversalHelper().getFeedbackDetail("", deptLevel, getStatus(), getFeedId(), connectionSpace);
							if (data != null && data.size() > 0)
							{
								fbp = new FeedbackUniversalHelper().setFeedbackDataValues(data, getStatus(), deptLevel);
							}
							boolean adminFlag = getResTicketIsForAdminOrNot(getFeedId(), connectionSpace);

							levelMsg = "Close Alert: Ticket No: " + fbp.getTicket_no() + ", Open By: " + DateUtil.makeTitle(fbp.getFeed_registerby().trim()) + " for " + fbp.getFeed_by() + ",Location: " + fbp.getLocation() + ", Feedback:" + fbp.getFeedback_subcatg() + ", Status is : Closed.";

							mailSubject = "Close Alert: " + fbp.getTicket_no();
							mailheading = "Close Alert: " + fbp.getTicket_no();
							// SMS Stop for Tickets other than complaints
							if (getStatus().equalsIgnoreCase("Resolved") || getStatus().equalsIgnoreCase("Re-Assign"))
							{
								String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);
								if (adminFlag)
								{
									if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
									{
										 System.out.println("fbp.getMobOne()>"+fbp.getMobOne()+"levelMsg>"+levelMsg);
										 communication.addMessage(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getMobOne(),levelMsg, ticket_no,getStatus(), "0", "FM");
									}
									if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true, orgName);
										communication.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), mailSubject, mailText, ticket_no, "Pending", "0", "", "FM");
									}
								}
								else
								{
									if (fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
									{
										communication.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), levelMsg, ticket_no, getStatus(), "0", "FM");
									}

									if (fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals(""))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, false, orgName);
										communication.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), mailSubject, mailText, ticket_no, getStatus(), "0", "", "FM");
									}

									if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
									{
										communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "FM");
									}

									if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
									{
										mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, mailheading, getStatus(), deptLevel, true, orgName);
										communication.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), mailSubject, mailText, ticket_no, "Pending", "0", "", "FM");
									}
								}
							}
						}
						addActionMessage("Feedback Updated in " + getStatus() + " Successfully !!!");
						returnResult = SUCCESS;
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	

	private InsertDataTable setValues(String column, String value)
	{
		InsertDataTable ob = new InsertDataTable();
		ob.setColName(column);
		ob.setDataName(value);
		return ob;
	}

	private List getTransferTicketData(String id,SessionFactory connectionSpace) {
		String str = "select feed_by ,by_dept_subdept ,feed_brief,location,ticket_no,to_dept_subdept,client_id,patient_id,feed_data_id,via_from from feedback_status_pdm where id='"+id+"'";
		List list=cbt.executeAllSelectQuery(str, connectionSpace); 
		return list;
	}

	public boolean transferComplaint(String feedBy,  String bysubdept, String feedBrief, String location, String previousId, String toDeptId, String clientId, String patientId, String feedDataId2, String via_from,  SessionFactory connectionSpace)
	{
		boolean flag = false;
		try
		{
			FeedbackUniversalAction FUA=new FeedbackUniversalAction();
			FeedbackUniversalHelper FUH=new FeedbackUniversalHelper();
			ActivityBoardHelper helper=new ActivityBoardHelper();
			String deptLevel = "2";
			String userName = (String) session.get("uName");
			String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", allottoId = "",  ticketno = "", tolevel = "", needesc = "";
			@SuppressWarnings("unused")
			String resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA",address_Date_Time="NA";
			boolean type=helper.getEscalationType(connectionSpace);
			List subCategoryList = FUH.getAllData("feedback_subcategory", "id", subCategory, "sub_category_name", "ASC", connectionSpace);
			if (subCategoryList != null && subCategoryList.size() > 0)
			{
				for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[3]!=null)
					{
						feedBrief = objectCol[3].toString();
					}
					if(!type)
					{
						if (objectCol[4] == null)
						{
							adrr_time = "16:00";
						} else
						{
							adrr_time = objectCol[4].toString();
						}
						if (objectCol[5] == null)
						{
							res_time = "08:00";
						} else
						{
							res_time = objectCol[5].toString();
						}
					}

					if (objectCol[8] == null)
					{
						tolevel = "Level1";
					} else
					{
						tolevel = objectCol[8].toString();
					}
					if (objectCol[9] == null)
					{
						needesc = "Y";
					} else
					{
						needesc = objectCol[9].toString();
					}
				}
			}
			if(type)
			{
				//Getting addressing and resolution time from settings
				List addressList=helper.getAddressResolutionTime(via_from,"",connectionSpace);
				if(addressList!=null && addressList.size()>0)
				{
					for (Iterator iterator = addressList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] == null)
						{
							adrr_time = "06:00";
						}
						else
						{
							adrr_time = object[0].toString();
						}
						if (object[1] == null)
						{
							res_time = "10:00";
						}
						else
						{
							res_time = object[1].toString();
						}
					}
				}
			}
			
		//	System.out.println(adrr_time+"Addressing time>>>res_time>>>"+res_time);
			WorkingHourAction WHA=new WorkingHourAction();
			if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
			{
				List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), "FM");
				address_Date_Time = dateTime.get(0)+" "+dateTime.get(1);
				escalation_date = dateTime.get(2);
				escalation_time = dateTime.get(3);
			}
			
			List allotto = null;
			List allotto1 = null;
			List<String> one = new ArrayList<String>();
			List<String> two = new ArrayList<String>();
			List<String> two_new = new ArrayList<String>();

			try
			{
				if (deptId != null)
				{
					try
					{
						allotto = FUA.getRandomEmp4Escalation(deptId, "FM", tolevel.substring(5), "", "", connectionSpace);
						if (allotto == null || allotto.size() == 0)
						{
							String newToLevel = "";
							newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
							allotto = FUA.getRandomEmp4Escalation(deptId, "FM", newToLevel, "", "", connectionSpace);
							if (allotto == null || allotto.size() == 0)
							{
								newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
								allotto = FUA.getRandomEmp4Escalation(deptId, "FM", newToLevel, "", "", connectionSpace);
								if (allotto == null || allotto.size() == 0)
								{
									newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
									allotto = FUA.getRandomEmp4Escalation(deptId, "FM", newToLevel, "", "", connectionSpace);
								}
							}
						}
						allotto1 = FUA.getRandomEmployee(deptId, deptLevel, tolevel.substring(5), allotto, "FM", connectionSpace);
						if (allotto != null && allotto.size() > 0)
						{
							for (Object object : allotto)
							{
								one.add(object.toString());
							}
						}
						if (allotto1 != null && allotto1.size() > 0)
						{
							for (Object object : allotto1)
							{
								two.add(object.toString());
							}
						}

						if (one != null && one.size() > two.size())
						{
							one.removeAll(two);
							if (one.size() > 0)
							{
								for (Iterator iterator = one.iterator(); iterator.hasNext();)
								{
									Object object = (Object) iterator.next();
									allottoId = object.toString();
									break;
								}
							}
						} else
						{
							List pending_alloted = FUA.getPendingAllotto(deptId, "FM");
							if (pending_alloted != null && pending_alloted.size() > 0)
							{
								for (Object object : pending_alloted)
								{
									two_new.add(object.toString());
								}
							}

							if (two_new != null && two_new.size() > 0)
							{
								one.removeAll(two_new);
							}
							if (one != null && one.size() > 0)
							{
								allottoId = FUA.getRandomEmployee("FM", one);
							} else
							{
								allottoId = FUA.getRandomEmployee("FM", allotto);
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			ticketno = FUH.getReAssignedTicketNo(ticket_no, deptId, toDeptId, "FM", connectionSpace);
			 System.out.println("New Ticket is as >>>>>>>>>>>>>>"+ticketno);
			
			if (ticketno != null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId != null && !allottoId.equals(""))
			{
				// Setting the column values after getting from the page
				InsertDataTable ob = new InsertDataTable();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				ob.setColName("ticket_no");
				ob.setDataName(ticketno);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by");
				ob.setDataName(feedBy);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("by_dept_subdept");
				ob.setDataName(bysubdept);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("sub_catg");
				ob.setDataName(subCategory);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_brief");
				ob.setDataName(feedBrief);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("to_dept_subdept");
				ob.setDataName(deptId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("allot_to");
				ob.setDataName(allottoId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_date");
				ob.setDataName(escalation_date);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_time");
				ob.setDataName(escalation_time);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("addr_date_time");
				ob.setDataName(address_Date_Time);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("location");
				ob.setDataName(location);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName("Level1");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Pending");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("via_from");
				ob.setDataName(via_from);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_register_by");
				ob.setDataName(userName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("client_id");
				ob.setDataName(clientId);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("patient_id");
				ob.setDataName(patientId);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("feed_data_id");
				ob.setDataName(feedDataId2);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("stage");
				ob.setDataName("2");
				insertData.add(ob);

				// Method calling for inserting the values in the
				// feedback_status table
				//boolean status1 = cbt.insertIntoTable("feedback_status_pdm", insertData, connectionSpace);
				int mid=cbt.insertDataReturnId("feedback_status_pdm", insertData, connectionSpace);
				//System.out.println(deptLevel+"Status value is   "+status1);
				if (mid>0)
				{
					new ActivityTakeAction().insertIntoHistory(String.valueOf(mid), "Pending", null, null, null, null, insertData, connectionSpace, session);
					flag = true;
					String levelMsg = "";
					List data = FUH.getFeedbackDetail(ticketno, deptLevel, "Pending", "", connectionSpace);
					FeedbackPojo fbp = FUH.setFeedbackDataValues(data, "Pending", deptLevel);

					if (fbp != null)
					{
						String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);
						MsgMailCommunication MMC = new MsgMailCommunication();
						levelMsg = "Open Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time().subSequence(0, 5) + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + " , Brief: " + fbp.getFeed_brief() + ".";
						if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
						{
							MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticket_no, getStatus(), "0", "FM");
						}
						if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals(""))
						{
							String mailText = FUH.getConfigMessage(fbp, "Pending Ticket Alert : " + fbp.getTicket_no() + "", getStatus(), deptLevel, true,orgName);
							MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Pending Ticket Alert : " + fbp.getTicket_no() + "", mailText, ticket_no, "Pending", "0", "", "FM");
						}
					}
				}
				insertData.clear();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
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

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public Map<Integer, String> getResolverList()
	{
		return resolverList;
	}

	public void setResolverList(Map<Integer, String> resolverList)
	{
		this.resolverList = resolverList;
	}

	public String getResolver()
	{
		return resolver;
	}

	public void setResolver(String resolver)
	{
		this.resolver = resolver;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getSpareused()
	{
		return spareused;
	}

	public void setSpareused(String spareused)
	{
		this.spareused = spareused;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getOpen_date()
	{
		return open_date;
	}

	public void setOpen_date(String open_date)
	{
		this.open_date = open_date;
	}

	public String getOpen_time()
	{
		return open_time;
	}

	public void setOpen_time(String open_time)
	{
		this.open_time = open_time;
	}

	public String getHpcomment()
	{
		return hpcomment;
	}

	public void setHpcomment(String hpcomment)
	{
		this.hpcomment = hpcomment;
	}

	public String getIgnorecomment()
	{
		return ignorecomment;
	}

	public void setIgnorecomment(String ignorecomment)
	{
		this.ignorecomment = ignorecomment;
	}

	public String getSnoozeDate()
	{
		return snoozeDate;
	}

	public void setSnoozeDate(String snoozeDate)
	{
		this.snoozeDate = snoozeDate;
	}

	public String getSnoozeTime()
	{
		return snoozeTime;
	}

	public void setSnoozeTime(String snoozeTime)
	{
		this.snoozeTime = snoozeTime;
	}

	public String getSnoozecomment()
	{
		return snoozecomment;
	}

	public void setSnoozecomment(String snoozecomment)
	{
		this.snoozecomment = snoozecomment;
	}

	public String getTosubdept()
	{
		return tosubdept;
	}

	public void setTosubdept(String tosubdept)
	{
		this.tosubdept = tosubdept;
	}

	public String getReAssignRemark()
	{
		return reAssignRemark;
	}

	public void setReAssignRemark(String reAssignRemark)
	{
		this.reAssignRemark = reAssignRemark;
	}

	public String getFeedid()
	{
		return feedid;
	}

	public void setFeedid(String feedid)
	{
		this.feedid = feedid;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames)
	{
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public String getHeadingTitle()
	{
		return headingTitle;
	}

	public void setHeadingTitle(String headingTitle)
	{
		this.headingTitle = headingTitle;
	}

	public String getFeedbackBy()
	{
		return feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy)
	{
		this.feedbackBy = feedbackBy;
	}

	public String getMobileno()
	{
		return mobileno;
	}

	public void setMobileno(String mobileno)
	{
		this.mobileno = mobileno;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public String getSubCatg()
	{
		return subCatg;
	}

	public void setSubCatg(String subCatg)
	{
		this.subCatg = subCatg;
	}

	public String getFeed_brief()
	{
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
	}

	/*
	 * public String getAllotto() { return allotto; }
	 * 
	 * public void setAllotto(String allotto) { this.allotto = allotto; }
	 */

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getAddrDate()
	{
		return addrDate;
	}

	public void setAddrDate(String addrDate)
	{
		this.addrDate = addrDate;
	}

	public String getAddrTime()
	{
		return addrTime;
	}

	public void setAddrTime(String addrTime)
	{
		this.addrTime = addrTime;
	}

	public String getResolveon()
	{
		return resolveon;
	}

	public void setResolveon(String resolveon)
	{
		this.resolveon = resolveon;
	}

	public String getResolveat()
	{
		return resolveat;
	}

	public void setResolveat(String resolveat)
	{
		this.resolveat = resolveat;
	}

	public String getActiontaken()
	{
		return actiontaken;
	}

	public void setActiontaken(String actiontaken)
	{
		this.actiontaken = actiontaken;
	}

	public String getTodept()
	{
		return todept;
	}

	public void setTodept(String todept)
	{
		this.todept = todept;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPincode()
	{
		return pincode;
	}

	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getDataFlag()
	{
		return dataFlag;
	}

	public void setDataFlag(String dataFlag)
	{
		this.dataFlag = dataFlag;
	}

	public String getDashFor()
	{
		return dashFor;
	}

	public void setDashFor(String dashFor)
	{
		this.dashFor = dashFor;
	}

	public String getD_subdept_id()
	{
		return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id)
	{
		this.d_subdept_id = d_subdept_id;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(String openDate)
	{
		this.openDate = openDate;
	}

	public String getOpenTime()
	{
		return openTime;
	}

	public void setOpenTime(String openTime)
	{
		this.openTime = openTime;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getFeedBreif()
	{
		return feedBreif;
	}

	public void setFeedBreif(String feedBreif)
	{
		this.feedBreif = feedBreif;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public List<String> getStatusList()
	{
		return statusList;
	}

	public void setStatusList(List<String> statusList)
	{
		this.statusList = statusList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getSubCategory()
	{
		return subCategory;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public void setSubCategory(String subCategory)
	{
		this.subCategory = subCategory;
	}

	public String getCrNo()
	{
		return crNo;
	}

	public void setCrNo(String crNo)
	{
		this.crNo = crNo;
	}

	public String getPatTyp()
	{
		return patTyp;
	}

	public void setPatTyp(String patTyp)
	{
		this.patTyp = patTyp;
	}

	public String getPatObsrvtn()
	{
		return patObsrvtn;
	}

	public void setPatObsrvtn(String patObsrvtn)
	{
		this.patObsrvtn = patObsrvtn;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public String getRoomNo()
	{
		return roomNo;
	}

	public void setRoomNo(String roomNo)
	{
		this.roomNo = roomNo;
	}

	public String getTotalPending()
	{
		return totalPending;
	}

	public void setTotalPending(String totalPending)
	{
		this.totalPending = totalPending;
	}

	public String getTotalResolved()
	{
		return totalResolved;
	}

	public void setTotalResolved(String totalResolved)
	{
		this.totalResolved = totalResolved;
	}

	public String getTotalTickets()
	{
		return totalTickets;
	}

	public void setTotalTickets(String totalTickets)
	{
		this.totalTickets = totalTickets;
	}

	public Map<String, String> getFeedTypeMap()
	{
		return feedTypeMap;
	}

	public void setFeedTypeMap(Map<String, String> feedTypeMap)
	{
		this.feedTypeMap = feedTypeMap;
	}

	public String getFeedType()
	{
		return feedType;
	}

	public void setFeedType(String feedType)
	{
		this.feedType = feedType;
	}

	public Map<String, String> getFeedCatMap()
	{
		return feedCatMap;
	}

	public void setFeedCatMap(Map<String, String> feedCatMap)
	{
		this.feedCatMap = feedCatMap;
	}

	public String getFeedCat()
	{
		return feedCat;
	}

	public void setFeedCat(String feedCat)
	{
		this.feedCat = feedCat;
	}

	public Map<String, String> getTicketNoMap()
	{
		return ticketNoMap;
	}

	public void setTicketNoMap(Map<String, String> ticketNoMap)
	{
		this.ticketNoMap = ticketNoMap;
	}

	public Map<String, String> getFeedByMap()
	{
		return feedByMap;
	}

	public void setFeedByMap(Map<String, String> feedByMap)
	{
		this.feedByMap = feedByMap;
	}

	public String getFeedBy()
	{
		return feedBy;
	}

	public void setFeedBy(String feedBy)
	{
		this.feedBy = feedBy;
	}

	public File getResolveDoc()
	{
		return resolveDoc;
	}

	public void setResolveDoc(File resolveDoc)
	{
		this.resolveDoc = resolveDoc;
	}

	public String getResolveDocContentType()
	{
		return resolveDocContentType;
	}

	public void setResolveDocContentType(String resolveDocContentType)
	{
		this.resolveDocContentType = resolveDocContentType;
	}

	public String getResolveDocFileName()
	{
		return resolveDocFileName;
	}

	public void setResolveDocFileName(String resolveDocFileName)
	{
		this.resolveDocFileName = resolveDocFileName;
	}

	public File getResolveDoc1()
	{
		return resolveDoc1;
	}

	public void setResolveDoc1(File resolveDoc1)
	{
		this.resolveDoc1 = resolveDoc1;
	}

	public String getResolveDoc1ContentType()
	{
		return resolveDoc1ContentType;
	}

	public void setResolveDoc1ContentType(String resolveDoc1ContentType)
	{
		this.resolveDoc1ContentType = resolveDoc1ContentType;
	}

	public String getResolveDoc1FileName()
	{
		return resolveDoc1FileName;
	}

	public void setResolveDoc1FileName(String resolveDoc1FileName)
	{
		this.resolveDoc1FileName = resolveDoc1FileName;
	}

	public String getDischargeDateTime()
	{
		return dischargeDateTime;
	}

	public void setDischargeDateTime(String dischargeDateTime)
	{
		this.dischargeDateTime = dischargeDateTime;
	}

	public String getActionComments()
	{
		return actionComments;
	}

	public void setActionComments(String actionComments)
	{
		this.actionComments = actionComments;
	}

	public Map<String, String> getCrNoMap()
	{
		return crNoMap;
	}

	public void setCrNoMap(Map<String, String> crNoMap)
	{
		this.crNoMap = crNoMap;
	}

	public String getPatName()
	{
		return patName;
	}

	public void setPatName(String patName)
	{
		this.patName = patName;
	}

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public String getAllotto()
	{
		return allotto;
	}

	public void setAllotto(String allotto)
	{
		this.allotto = allotto;
	}

	public String getFeedDataId()
	{
		return feedDataId;
	}

	public void setFeedDataId(String feedDataId)
	{
		this.feedDataId = feedDataId;
	}
}