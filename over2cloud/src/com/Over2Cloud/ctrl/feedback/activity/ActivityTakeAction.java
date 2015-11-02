package com.Over2Cloud.ctrl.feedback.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;
import com.Over2Cloud.ctrl.feedback.LodgeFeedbackHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.opensymphony.xwork2.ActionContext;

public class ActivityTakeAction
{
	public String feedId;
	public String feedToDept;
	public String subCatId;
	public String clientId;
	private final CommonOperInterface cbt = new CommonConFactory().createInterface();
	private final MsgMailCommunication communication = new MsgMailCommunication();
	private String recvSMS;
	private String recvEmail;
	private com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo fbp = null;
	private String action;
	private String escalation_date = "NA", escalation_time = "NA", non_working_timing = "00:00";

	private String mode;
	private String feed_brief;
	private String snoozeDate;
	private String snoozeTime;
	private String snoozecomment;
	private String activityFlag;
	private String feedStatId;
	private String comments;
	private String ticket_no;
	private String patientId;
	private String feedbackDataId;
	private String visitType;

	public String takeActionForActivity()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				// String deptLevel = (String) session.get("userDeptLevel");
				String deptLevel = "2";
				String userName = (String) session.get("uName");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				// Updating Feedback Table Status
				InsertDataTable ob = new InsertDataTable();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				// System.out.println("register id=>>>>>>>>>>>>"+getFeedId());
				condtnBlock.put("id", getFeedId());

				if (getAction().equalsIgnoreCase("No Further Action Required") || getAction().equalsIgnoreCase("2"))
				{

					boolean isInserted = insertIntoHistory(getFeedStatId(), "No Further Action Required", getComments(), null, null, null, insertData, connectionSpace, session);
					if (isInserted)
					{
						StringBuilder query = new StringBuilder("update feedback_status_pdm set status='No Further Action Required',stage='2' where id=" + getFeedStatId());
						cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
						wherClause.put("status", "No Further Action Required");
						new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);
					}
					return "noFurtherAction";
				} else if (getAction().equalsIgnoreCase("Open Ticket") || getAction().equalsIgnoreCase("1"))
				{
					String adrr_time = null, res_time = null;
					String arr[] = helper.getSubCatDetails(connectionSpace, getSubCatId());
					boolean type = helper.getEscalationType(connectionSpace);
					if (type)
					{
						// Getting addressing and resolution time from settings
						List addressList = new ActivityBoardHelper().getAddressResolutionTime(getMode(), "", connectionSpace);
						if (addressList != null && addressList.size() > 0)
						{
							for (Iterator iterator = addressList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] == null)
								{
									adrr_time = "06:00";
								} else
								{
									adrr_time = object[0].toString();
								}
								if (object[1] == null)
								{
									res_time = "10:00";
								} else
								{
									res_time = object[1].toString();
								}
							}
						}
						addressList.clear();
					} else
					{
						adrr_time = arr[0];
						res_time = arr[1];
					}
					String needesc = arr[3];
					String[] adddate_time = null;
					String Address_Date_Time = "NA";

					FeedbackPojo pojo = new LodgeFeedbackHelper().getPatientDetails(getFeedbackDataId(), connectionSpace);
					boolean bedMapping = FUH.checkTable("bed_mapping", connectionSpace);
					String allottoId = helper.getTicketAllotToId(connectionSpace, getFeedToDept(), bedMapping, pojo, arr[2], deptLevel);
					String ticketNo = helper.getTicketNoForDept(connectionSpace, getTicket_no(), getFeedToDept());
					EmpBasicPojoBean empPojo = helper.getLoggedUserDetails(connectionSpace, userName, deptLevel);
					WorkingHourAction WHA = new WorkingHourAction();
					String date = DateUtil.getCurrentDateUSFormat();
					String time = DateUtil.getCurrentTimeHourMin();
					if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
					{
						List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
						Address_Date_Time = dateTime.get(0) + " " + dateTime.get(1);
						escalation_date = dateTime.get(2);
						escalation_time = dateTime.get(3);
					}
					// System.out.println("allot to>>>>"+allottoId);
					boolean ticketAdded = false;
					if (allottoId != null && !(allottoId.equalsIgnoreCase("")) && pojo.getName() != null && !"NA".equalsIgnoreCase(pojo.getName()))
					{
						// Setting the column values after getting from the page
						ob.setColName("ticket_no");
						ob.setDataName(ticketNo);
						insertData.add(ob);

						/*
						 * ob = new InsertDataTable();
						 * ob.setColName("feed_by_mobno");
						 * ob.setDataName(empPojo.getMobOne());
						 * insertData.add(ob);
						 * 
						 * ob = new InsertDataTable();
						 * ob.setColName("feed_by_emailid");
						 * ob.setDataName(empPojo.getEmailIdOne());
						 * insertData.add(ob);
						 */

						if (pojo != null)
						{
							ob = new InsertDataTable();
							ob.setColName("feed_by");
							ob.setDataName(pojo.getName());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("location");
							ob.setDataName(pojo.getCentreCode());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("client_id");
							ob.setDataName(pojo.getCrNo());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("patient_id");
							ob.setDataName(pojo.getPatId());
							insertData.add(ob);

						}

						ob = new InsertDataTable();
						ob.setColName("by_dept_subdept");
						ob.setDataName(empPojo.getDeptName());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("sub_catg");
						ob.setDataName(getSubCatId());
						insertData.add(ob);

						if (getFeed_brief() != null && !getFeed_brief().equalsIgnoreCase(""))
						{
							ob = new InsertDataTable();
							ob.setColName("feed_brief");
							ob.setDataName(getFeed_brief());
							insertData.add(ob);
						} else
						{
							ob = new InsertDataTable();
							ob.setColName("feed_brief");
							ob.setDataName(arr[4]);
							insertData.add(ob);
						}

						ob = new InsertDataTable();
						ob.setColName("to_dept_subdept");
						ob.setDataName(getFeedToDept());
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
						ob.setDataName(Address_Date_Time);
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
						ob.setColName("level");
						ob.setDataName("Level1");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						if (!escalation_date.equalsIgnoreCase("NA"))
						{
							ob.setDataName("Pending");
						} else
						{
							ob.setDataName("Unacknowledged");
						}
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("via_from");
						ob.setDataName(getMode());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("feed_register_by");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("non_working_hour");
						ob.setDataName(non_working_timing);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("stage");
						ob.setDataName("2");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("feed_data_id");
						ob.setDataName(feedbackDataId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("visit_type");
						ob.setDataName(visitType);
						insertData.add(ob);

						int mid = cbt.insertDataReturnId("feedback_status_pdm", insertData, connectionSpace);
						insertData.clear();

						if (mid > 0)
						{
							String hStatus = null;
							if (!escalation_date.equalsIgnoreCase("NA"))
							{
								hStatus = "Pending";
							} else
							{
								hStatus = "Unacknowledged";
							}
							ticketAdded = insertIntoHistory(String.valueOf(mid), hStatus, getComments(), null, null, null, insertData, connectionSpace, session);

						}
						if (ticketAdded)
						{
							List data = new FeedbackUniversalHelper().getFeedbackDetail(ticketNo, deptLevel, "Pending", "", connectionSpace);
							fbp = new FeedbackUniversalHelper().setFeedbackDataValues(data, "Pending", deptLevel);
							String mailText = "";
							// setTodept(fbp.getFeedback_to_dept());
							if (fbp != null)
							{
								String orgName = new ReportsConfigurationDao().getOrganizationName(connectionSpace);
								boolean mailFlag = false;

								if (needesc.equalsIgnoreCase("Y") && fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
								{
									String levelMsg = "Open Alert: Ticket No: " + fbp.getTicket_no() + ", Open By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + " for " + DateUtil.makeTitle(fbp.getFeed_by()) + "," + fbp.getCr_no() + ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg() + ", To be closed by :" + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time();
									communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketNo, "Pending", "0", "FM");
								}

								if (needesc.equalsIgnoreCase("Y") && getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
								{
									String complainMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + "," + " Ticket No: " + fbp.getTicket_no() + ", for " + fbp.getFeed_by() + ", Location: " + fbp.getLocation() + ", Feedback: " + fbp.getFeedback_subcatg() + ", Resolution Time: " + DateUtil.getCurrentDateIndianFormat() + "," + DateUtil.newTime(fbp.getFeedaddressing_time()).substring(0, 5) + " ,Status is : Open";
									communication.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainMsg, ticketNo, "Pending", "0", "FM");
								}

								if (needesc.equalsIgnoreCase("Y") && fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
								{
									mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, true, orgName);
									communication.addMail(fbp.getFeed_by(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketNo, "Pending", "0", null, "FM");
								}
								if (needesc.equalsIgnoreCase("Y") && getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("") && !fbp.getFeedback_by_emailid().equals("NA"))
								{
									mailText = new FeedbackUniversalHelper().getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, false, orgName);
									communication.addMail(fbp.getFeed_by(), fbp.getFeedback_by_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText.toString(), ticketNo, "Pending", "0", null, "FM");
								}
							}
						}
					}
					System.out.println("ticket ADDed" + ticketAdded);
					if (ticketAdded)
					{
						boolean isInserted = insertIntoHistory(getFeedStatId(), "Ticket Opened", getComments(), null, null, null, insertData, connectionSpace, session);
						if (isInserted)
						{
							wherClause.put("status", "Ticket Opened");
							boolean updateFeed = new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);
							StringBuilder query = new StringBuilder("update feedback_status_pdm set status='Ticket Opened',stage='2' where id=" + getFeedStatId());
							System.out.println("query::" + query);
							int i = cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
						}
						return "success";
					} else
					{

						return "unsuccess";
					}
				} else if (getAction().equalsIgnoreCase("Ignore") || getAction().equalsIgnoreCase("4"))
				{
					boolean isInserted = insertIntoHistory(getFeedStatId(), "Ignore", getComments(), null, null, null, insertData, connectionSpace, session);
					if (isInserted)
					{
						StringBuilder query = new StringBuilder();
						query.append("update feedback_status_pdm set status='Ignore',stage='2' where id=" + getFeedStatId() + "");
						cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
						wherClause.put("status", "Ignore");
						new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);
					}
					return "noFurtherAction";
				} else if (getAction().equalsIgnoreCase("Snooze") || getAction().equalsIgnoreCase("3"))
				{
					wherClause.put("status", "Snooze");
					condtnBlock.put("id", getFeedStatId());
					boolean isInserted = insertIntoHistory(getFeedStatId(), "Snooze", getSnoozecomment(), DateUtil.convertDateToUSFormat(getSnoozeDate()), getSnoozeTime(), null, insertData, connectionSpace, session);
					if (isInserted)
					{
						HUH.updateTableColomn("feedback_status_pdm", wherClause, condtnBlock, connectionSpace);
						StringBuilder qry = new StringBuilder("UPDATE feedbackdata SET status='Snooze' WHERE id=" + getFeedId());
						cbt.updateTableColomn(connectionSpace, qry);
						new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock, connectionSpace);
					}
					return "noFurtherAction";
				} else
				{
					return "error";
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		} else
		{
			return "login";
		}
	}

	public boolean insertIntoHistory(String mid, String hStatus, String comments, String date, String time, String duration, List<InsertDataTable> insertData, SessionFactory connectionSpace, Map session)
	{
		insertData.clear();
		boolean isInserted = false;
		InsertDataTable ob = new InsertDataTable();
		ob.setColName("feed_id");
		ob.setDataName(mid);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("status");
		ob.setDataName(hStatus);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_date");
		ob.setDataName(DateUtil.getCurrentDateUSFormat());
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_time");
		ob.setDataName(DateUtil.getCurrentTime());
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_remark");
		ob.setDataName(comments);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("snooze_upto_date");
		ob.setDataName(date);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("snooze_upto_time");
		ob.setDataName(time);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_duration");
		ob.setDataName(duration);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("action_by");
		ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
		insertData.add(ob);

		// Method calling for inserting the values in the feedback_status table
		isInserted = cbt.insertIntoTable("feedback_status_pdm_history", insertData, connectionSpace);
		return isInserted;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public String getFeedToDept()
	{
		return feedToDept;
	}

	public String getSubCatId()
	{
		return subCatId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public void setFeedToDept(String feedToDept)
	{
		this.feedToDept = feedToDept;
	}

	public void setSubCatId(String subCatId)
	{
		this.subCatId = subCatId;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getRecvSMS()
	{
		return recvSMS;
	}

	public void setRecvSMS(String recvSMS)
	{
		this.recvSMS = recvSMS;
	}

	public String getRecvEmail()
	{
		return recvEmail;
	}

	public void setRecvEmail(String recvEmail)
	{
		this.recvEmail = recvEmail;
	}

	public com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo getFbp()
	{
		return fbp;
	}

	public void setFbp(com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo fbp)
	{
		this.fbp = fbp;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getFeed_brief()
	{
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
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

	public String getActivityFlag()
	{
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag)
	{
		this.activityFlag = activityFlag;
	}

	public String getFeedStatId()
	{
		return feedStatId;
	}

	public void setFeedStatId(String feedStatId)
	{
		this.feedStatId = feedStatId;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public String getPatientId()
	{
		return patientId;
	}

	public String getFeedbackDataId()
	{
		return feedbackDataId;
	}

	public void setFeedbackDataId(String feedbackDataId)
	{
		this.feedbackDataId = feedbackDataId;
	}

	public String getVisitType()
	{
		return visitType;
	}

	public void setVisitType(String visitType)
	{
		this.visitType = visitType;
	}

}
