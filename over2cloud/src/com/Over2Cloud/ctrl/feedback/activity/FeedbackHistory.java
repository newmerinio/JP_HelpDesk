package com.Over2Cloud.ctrl.feedback.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackHistory
{
	private String id;
	private String clientId;
	private ActivityPojo pojo;
	private List<ConfigurationUtilBean> paperRatings = null;
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<String, String> dataMap;
	private Map<String, Map<String, String>> dataMapTAT;
	private String feedStatId;

	private String dataFor;
	private String ticketNo;
	public static final boolean DESC = false;
	private String activityFlag;
	private String patientId;
	private String date;
	private Map<String, Map<String, String>> finalStatusMap;
	private Map<String, Map<String, Map<String, String>>> statusMap = new LinkedHashMap<String, Map<String, Map<String, String>>>();;
	private List<ActivityPojo> statusList;
	private String feedbackDataId;
	private List<Object> masterViewList;
	public String oper;
	private String compType, centerCode, serviceBy, companytype, discharge_datetime, admission_time;
	private String patientName, dept, cat, subCat, brief;
	private String status;
	public List dataList;

	public String takeAction()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				StringBuffer query = new StringBuffer();
				if (discharge_datetime != null && !"".equalsIgnoreCase(discharge_datetime))
				{
					String str[] = discharge_datetime.split(", ");
					discharge_datetime = DateUtil.convertDateToUSFormat(str[0]) + " " + str[1];
				}
				String str1[] = admission_time.split(", ");
				admission_time = DateUtil.convertDateToUSFormat(str1[0]) + " " + str1[1];
				query.append(" UPDATE feedbackdata SET compType='" + compType + "',centerCode='" + centerCode + "',serviceBy='" + serviceBy + "',companytype='" + companytype + "',patientId='" + patientId + "',discharge_datetime='" + discharge_datetime + "',admission_time='" + admission_time + "' WHERE id='" + id + "'");
				cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
				query.setLength(0);
				query.append("UPDATE feedback_status SET location='" + centerCode + "',patientId='" + patientId + "' WHERE feedDataId='" + id + "'");
				cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
				return "success";
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

	public String getStatusFullDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{

				return "success";
			} catch (Exception e)
			{
				return "error";
			}
		} else
		{
			return "error";
		}
	}

	@SuppressWarnings("rawtypes")
	public String viewPatientInfo()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List list = new createTable().executeAllSelectQuery("SELECT id,patientId,patient_type,admission_time,discharge_datetime,station,admit_consultant,companytype FROM patientinfo WHERE cr_number='" + clientId + "' ORDER BY id DESC ", connectionSpace);
				List<Object> Listhb = new ArrayList<Object>();
				if (list != null && list.size() > 0)
				{
					setMasterViewList(new ArrayList<Object>());
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", (object[0]));
						one.put("clientId", clientId);
						if (object[1] != null)
						{
							one.put("patientId", (object[1]));
						}

						if (object[2] != null && !object[2].equals(""))
						{
							one.put("patient_type", (object[2]));
						}

						if (object[3] != null && !object[3].equals(""))
						{
							String dateTime[] = object[3].toString().split(" ");
							one.put("admission_time", DateUtil.convertDateToIndianFormat(dateTime[0]) + ", " + dateTime[1]);
						}

						if (object[4] != null && !object[4].equals("NA"))
						{
							String dateTime[] = object[4].toString().split(" ");
							one.put("discharge_datetime", DateUtil.convertDateToIndianFormat(dateTime[0]) + ", " + dateTime[1]);
						}
						if (object[5] != null && !object[5].equals(""))
						{
							one.put("station", (object[5]));
						} else
						{
							one.put("station", "NA");
						}
						if (object[6] != null && !object[6].equals(""))
						{
							one.put("serviceBy", (object[6]));
						}
						if (object[7] != null && !object[7].equals(""))
						{
							one.put("companytype", (object[7]));
						}
						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				return "success";
			} catch (Exception e)
			{
				e.printStackTrace();
				return "error";
			}
		} else
		{
			return "error";
		}
	}

	public String getFeedbackHistory()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if ("SMS".equalsIgnoreCase(dataFor))
				{
					return "smssuccess";
				} else
				{
					Map session = ActionContext.getContext().getSession();
					String accountID = (String) session.get("accountid");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					ActivityBoardHelper helper = new ActivityBoardHelper();
					pojo = helper.getPatientFullDetails(connectionSpace, getClientId(), getPatientId(), getFeedbackDataId());
					if (pojo.getFeedDataId().equalsIgnoreCase("NA"))
					{
						return "nofeed";
					} else
					{
						List<GridDataPropertyView> ipdForm = null;
						List<GridDataPropertyView> opdForm = null;
						paperRatings = new ArrayList<ConfigurationUtilBean>();
						if (pojo.getCompType().equalsIgnoreCase("IPD") && pojo.getMode().equalsIgnoreCase("Paper"))
						{
							pojo = helper.getPatientIPDRatings(connectionSpace, pojo);
							return "ipdsuccess";
						} else if (pojo.getCompType().equalsIgnoreCase("OPD") && pojo.getMode().equalsIgnoreCase("Paper"))
						{
							pojo = helper.getPatientOPDRatings(connectionSpace, pojo);
							return "opdsuccess";
						} else
						{
							return "normal";
						}
					}
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

	public String getPatHistory()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getPatientFullDetails(connectionSpace, getClientId(), getPatientId(), getFeedbackDataId());
				return "success";
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

	public String viewEmpDetail()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getEmpFullDetails(connectionSpace, getId(), getActivityFlag());
				return "success";
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

	public String viewOpenByDetail()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getOpenByDetails(connectionSpace, getId(), getActivityFlag());
				return "success";
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewOpenedTATDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				dataMap = new HashMap<String, String>();
				WorkingHourAction WHA = new WorkingHourAction();
				ActivityBoardHelper helperObject = new ActivityBoardHelper();
				int count = 0;
				ActivityPojo activityPojo = null;
				pojo = new ActivityPojo();
				List list = null;
				statusList = new ArrayList<ActivityPojo>();
				if (getDataFor() != null && getDataFor().equalsIgnoreCase("status") && getFeedStatId() != null && !getFeedStatId().equalsIgnoreCase("NA"))
				{
					Map<String, String> dataHashMap = new HashMap<String, String>();
					List tempList = null;
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					List dataList1 = helperObject.getTicketHistory(connectionSpace, cbt, getFeedStatId());
					if (dataList1 != null && dataList1.size() > 0)
					{
						dataList = new ArrayList();
						for (Iterator iterator1 = dataList1.iterator(); iterator1.hasNext();)
						{
							Object[] object = (Object[]) iterator1.next();
							tempList = new ArrayList<String>();
							if (object[0] != null)
							{
								if (object[7] != null && object[8] != null && object[0].equals("Snooze"))
								{
									tempList.add(object[0] + " Till Date:" + DateUtil.convertDateToIndianFormat(object[7].toString()) + ", " + object[8].toString());
								} else
								{
									tempList.add(object[0].toString());
								}
							} else
							{
								tempList.add("NA");
							}
							tempList.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[1])) + ", " + CUA.getValueWithNullCheck(object[2]).substring(0, 5));
							if (object[3] != null)
							{
								tempList.add(CUA.getValueWithNullCheck(object[3]));

							} else
							{
								tempList.add("Auto");
							}
							tempList.add(CUA.getValueWithNullCheck(object[4]));
							tempList.add(CUA.getValueWithNullCheck(object[5]));
							tempList.add(CUA.getValueWithNullCheck(object[6]));
							tempList.add(CUA.getValueWithNullCheck(object[9]));
							dataList.add(tempList);
						}
					} else
					{
						dataMap.put("Name", "NA");
						dataMap.put("Mobile", "NA");
						dataMap.put("Email", "NA");
						dataMap.put("Department", "NA");
					}
				} else
				{
					List dataList = null;
					List addressList = null;
					int countRec = 0;
					String addrTime = null, resTime = null;
					ActivityBoardHelper helper = new ActivityBoardHelper();
					dataMapTAT = new LinkedHashMap<String, Map<String, String>>();
					if (getId() != null && !getId().equalsIgnoreCase("") && !getId().equalsIgnoreCase("0"))
					{
						dataList = helperObject.getStatusLevelOfCompliant(connectionSpace, cbt, getId(), false, getDataFor(), getTicketNo(), getActivityFlag());
					}
					String addressingDate = null, addressingTime = "00:00", resolutionDate = null, resolutionTime = "00:00";
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
						{
							list = (List) iterator1.next();
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dataMap.put("ticket_no", (object[7].toString()));
								dataMap.put("Status", (object[0].toString()));
								dataMap.put("Level", (object[1].toString()));
								dataMap.put("Open Date & Time", DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[3].toString().substring(0, object[3].toString().length() - 3));
								// To check escalation via mode or sub category
								// wise
								boolean type = helper.getEscalationType(connectionSpace);
								if (type)
								{
									addressList = helper.getAddressResolutionTime(object[11].toString(), "", connectionSpace);
									if (addressList != null && addressList.size() > 0)
									{
										for (Iterator iterator2 = addressList.iterator(); iterator2.hasNext();)
										{
											Object[] object2 = (Object[]) iterator2.next();
											if (object2[0] == null)
											{
												addrTime = "06:00";
											} else
											{
												addrTime = object2[0].toString();
											}
											if (object2[1] == null)
											{
												resTime = "10:00";
											} else
											{
												resTime = object2[1].toString();
											}
										}
									}
									addressList.clear();
								} else
								{
									addrTime = object[4].toString();
									resTime = object[5].toString();
								}
								System.out.println(addrTime + ">>>>>>" + resTime);

								List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, addrTime, resTime, "Y", object[2].toString(), object[3].toString(), "FM");
								addressingDate = dateTime.get(0);
								addressingTime = dateTime.get(1);
								resolutionDate = dateTime.get(2);
								resolutionTime = dateTime.get(3);
								dateTime.clear();
								dataMap.put("Addressing Date & Time", DateUtil.convertDateToIndianFormat(addressingDate) + ", " + addressingTime);
								dataMap.put("Resolution Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
								List empList = null;
								if (getActivityFlag().equalsIgnoreCase("1") && countRec == 0)
								{
									empList = helperObject.getEmp4Escalation(String.valueOf(helperObject.getAdminDeptId(connectionSpace)), "FM", "2", connectionSpace, cbt);
									countRec++;
								} else
								{
									empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "2", connectionSpace, cbt);
								}

								StringBuilder empName = new StringBuilder();
								if (empList != null)
								{
									for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
									{
										Object[] object2 = (Object[]) iterator2.next();
										if (object2[1] != null)
										{
											empName.append(object2[1].toString() + ", ");
										}
									}
									if (empName != null && empName.length() > 0)
									{
										dataMap.put("L-2 Escalation Date & Time", DateUtil.convertDateToIndianFormat(resolutionDate) + ", " + resolutionTime);
										dataMap.put("L-2 Escaltion To", empName.toString().substring(0, empName.toString().length() - 2));
									}
									empList.clear();
									empName.setLength(0);

									dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "FM");
									resolutionDate = dateTime.get(0);
									resolutionTime = dateTime.get(1);
									empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "3", connectionSpace, cbt);
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

										dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, object[5].toString(), "00:00", "Y", resolutionDate, resolutionTime, "FM");
										resolutionDate = dateTime.get(0);
										resolutionTime = dateTime.get(1);
										empList = helperObject.getEmp4Escalation(object[6].toString(), "FM", "4", connectionSpace, cbt);

										if (empList != null && empList.size() > 0)
										{
											for (Iterator iterator2 = empList.iterator(); iterator2.hasNext();)
											{
												Object[] object2 = (Object[]) iterator2.next();
												if (object2[1] != null)
												{
													empName.append(object2[1].toString() + ", ");
												}
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

								dataMapTAT.put(dataMap.get("Open Date & Time"), dataMap);
								dataMap = new HashMap<String, String>();
							}
						}
					}
				}

				return "success";
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

	public void getCycleByOrder(List dataList, Map<String, String> dataMap, String actionBy, List tempList, int count)
	{
		finalStatusMap = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> pendingMap = new LinkedHashMap<String, String>();
		Map<String, String> snoozeMap = new LinkedHashMap<String, String>();
		Map<String, String> hpMap = new LinkedHashMap<String, String>();
		Map<String, String> seekMap = new LinkedHashMap<String, String>();
		Map<String, String> reassignMap = new LinkedHashMap<String, String>();
		Map<String, String> ignoreMap = new LinkedHashMap<String, String>();
		Map<String, String> resolvedMap = new LinkedHashMap<String, String>();
		for (Entry<String, String> entry : dataMap.entrySet())
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (entry.getKey().equalsIgnoreCase("Pending"))
				{
					pendingMap.put("Opened By", "User Name");

					if (object[31] != null)
					{

						pendingMap.put("Opened For", object[31].toString());
					}

					if (object[26] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[26].toString());
						String time = object[27].toString();
						pendingMap.put("Opened At", date + ", " + time.substring(0, time.length() - 3));
					} else
					{
						pendingMap.put("Opened At", "NA");
					}
					if (object[28] != null)
					{

						pendingMap.put("Category", object[28].toString());
					}

					if (object[29] != null)
					{

						pendingMap.put("Sub Category", object[29].toString());
					}

					if (object[30] != null)
					{

						pendingMap.put("Brief", object[30].toString());
					}

					finalStatusMap.put("Pending", pendingMap);
				} else if (entry.getKey().equalsIgnoreCase("Snooze"))
				{
					if (object[1] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[1].toString());
						String time = object[2].toString();

						snoozeMap.put("Snooze At", date + ", " + time.substring(0, time.length() - 3));
					} else
						snoozeMap.put("Snooze At", "NA");

					if (object[3] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[3].toString());
						String time = object[4].toString();

						snoozeMap.put("Snooze Upto", date + ", " + time);
					} else
						snoozeMap.put("Snooze Upto", "NA");

					snoozeMap.put("Durartion", (object[5].toString()));
					snoozeMap.put("Snooze By", actionBy);
					snoozeMap.put("Reasons", (object[6].toString()));
					finalStatusMap.put("Snooze", snoozeMap);
				} else if (entry.getKey().equalsIgnoreCase("High Priority"))
				{
					if (object[7] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[7].toString());
						String time = object[8].toString();

						hpMap.put("High Priority At", date + ", " + time.substring(0, time.length() - 3));
					} else
						hpMap.put("High Priority At", "NA");

					hpMap.put("High Priority By", actionBy);
					hpMap.put("Reasons", (object[9].toString()));
					hpMap.put("Action Mode", (object[25].toString()));
					finalStatusMap.put("High Priority", hpMap);
				} else if (entry.getKey().equalsIgnoreCase("Ignore"))
				{
					if (object[10] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[10].toString());
						String time = object[11].toString();

						ignoreMap.put("Ignore At", date + ", " + time.substring(0, time.length() - 3));
					} else
						ignoreMap.put("Ignore At", "NA");

					ignoreMap.put("Ignore By", actionBy);
					ignoreMap.put("Reasons", (object[12].toString()));
					ignoreMap.put("Action Mode", (object[24].toString()));
					finalStatusMap.put("Ignore", ignoreMap);
				} else if (entry.getKey().equalsIgnoreCase("Resolved"))
				{
					if (object[13] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[13].toString());
						String time = object[14].toString().substring(0, object[14].toString().length() - 3);

						resolvedMap.put("Resolve At", date + ", " + time);
					} else
						resolvedMap.put("Resolve At", "NA");

					if (object[16] != null)
					{
						resolvedMap.put("Resolved By", (object[16].toString()));
					} else
					{
						resolvedMap.put("Resolved By", ("NA"));
					}
					if (object[17] != null)
					{
						resolvedMap.put("CAPA", (object[17].toString()));
					} else
					{
						resolvedMap.put("CAPA", ("NA"));
					}
					if (object[18] != null)
					{
						resolvedMap.put("RCA", (object[18].toString()));
					} else
					{
						resolvedMap.put("RCA", ("NA"));
					}

					// resolvedMap.put("Action Mode", (object[23].toString()));
					finalStatusMap.put("Resolved", resolvedMap);
				} else if (entry.getKey().equalsIgnoreCase("Re-assign"))
				{
					if (object[19] != null)
					{
						String date = DateUtil.convertDateToIndianFormat(object[19].toString());
						String time = object[20].toString();

						reassignMap.put("Re-assign At", date + ", " + time.substring(0, time.length() - 3));
					} else
						reassignMap.put("Re-assign At", "NA");

					reassignMap.put("Re-assign By", actionBy);
					reassignMap.put("Reason", (object[21].toString()));
					finalStatusMap.put("Re-assign", reassignMap);
				} else if (entry.getKey().equalsIgnoreCase("SeekApproval"))
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

							seekMap.put("Requested By", (object1[3].toString()));
							seekMap.put("Reasons", (object1[4].toString()));
							seekMap.put("Requested To", (object1[5].toString()));
							seekMap.put("Current Status", (object1[6].toString()));
							if (object1[7] != null)
							{
								String date = DateUtil.convertDateToIndianFormat(object1[7].toString());
								String time = object1[8].toString();
								seekMap.put("Approved At", date + ", " + time);
							} else
								seekMap.put("Approved At", "NA");

							seekMap.put("Comments", (object1[9].toString()));
						}
						finalStatusMap.put("Seek Approval", seekMap);
					}
				}
			}
		}
		// statusMap.put(String.valueOf(count), finalStatusMap);
		// System.out.println("size status Map>>>>>>>>>>>>"+count);

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
				} else
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
			// System.out.println("Key : " + entry.getKey() + " Value : " +
			// entry.getValue());
		}

		return sortedMap;
	}

	public String viewReopenedTATDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				ActivityBoardHelper helper = new ActivityBoardHelper();
				pojo = helper.getReopenTATDetails(connectionSpace, getId());
				return "success";
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public ActivityPojo getPojo()
	{
		return pojo;
	}

	public void setPojo(ActivityPojo pojo)
	{
		this.pojo = pojo;
	}

	public List<ConfigurationUtilBean> getPaperRatings()
	{
		return paperRatings;
	}

	public void setPaperRatings(List<ConfigurationUtilBean> paperRatings)
	{
		this.paperRatings = paperRatings;
	}

	public Map<String, String> getDataMap()
	{
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap)
	{
		this.dataMap = dataMap;
	}

	public String getFeedStatId()
	{
		return feedStatId;
	}

	public void setFeedStatId(String feedStatId)
	{
		this.feedStatId = feedStatId;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public static boolean isDesc()
	{
		return DESC;
	}

	public Map<String, Map<String, String>> getFinalStatusMap()
	{
		return finalStatusMap;
	}

	public void setFinalStatusMap(Map<String, Map<String, String>> finalStatusMap)
	{
		this.finalStatusMap = finalStatusMap;
	}

	public String getTicketNo()
	{
		return ticketNo;
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public Map<String, Map<String, String>> getDataMapTAT()
	{
		return dataMapTAT;
	}

	public void setDataMapTAT(Map<String, Map<String, String>> dataMapTAT)
	{
		this.dataMapTAT = dataMapTAT;
	}

	public Map<String, Map<String, Map<String, String>>> getStatusMap()
	{
		return statusMap;
	}

	public void setStatusMap(Map<String, Map<String, Map<String, String>>> statusMap)
	{
		this.statusMap = statusMap;
	}

	public List<ActivityPojo> getStatusList()
	{
		return statusList;
	}

	public void setStatusList(List<ActivityPojo> statusList)
	{
		this.statusList = statusList;
	}

	public String getActivityFlag()
	{
		return activityFlag;
	}

	public void setActivityFlag(String activityFlag)
	{
		this.activityFlag = activityFlag;
	}

	public String getPatientId()
	{
		return patientId;
	}

	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getFeedbackDataId()
	{
		return feedbackDataId;
	}

	public void setFeedbackDataId(String feedbackDataId)
	{
		this.feedbackDataId = feedbackDataId;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getCompType()
	{
		return compType;
	}

	public void setCompType(String compType)
	{
		this.compType = compType;
	}

	public String getCenterCode()
	{
		return centerCode;
	}

	public void setCenterCode(String centerCode)
	{
		this.centerCode = centerCode;
	}

	public String getServiceBy()
	{
		return serviceBy;
	}

	public void setServiceBy(String serviceBy)
	{
		this.serviceBy = serviceBy;
	}

	public String getCompanytype()
	{
		return companytype;
	}

	public void setCompanytype(String companytype)
	{
		this.companytype = companytype;
	}

	public String getDischarge_datetime()
	{
		return discharge_datetime;
	}

	public void setDischarge_datetime(String discharge_datetime)
	{
		this.discharge_datetime = discharge_datetime;
	}

	public String getAdmission_time()
	{
		return admission_time;
	}

	public void setAdmission_time(String admission_time)
	{
		this.admission_time = admission_time;
	}

	public List getDataList()
	{
		return dataList;
	}

	public void setDataList(List dataList)
	{
		this.dataList = dataList;
	}

	public String getPatientName()
	{
		return patientName;
	}

	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getCat()
	{
		return cat;
	}

	public void setCat(String cat)
	{
		this.cat = cat;
	}

	public String getSubCat()
	{
		return subCat;
	}

	public void setSubCat(String subCat)
	{
		this.subCat = subCat;
	}

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}
