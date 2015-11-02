package com.Over2Cloud.ctrl.helpdesk.lodgefeedback;

/**
 * @author Khushal Singh
 * Date (08-07-2013)
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.activityboard.ActivityBoardHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.ActionOnFeedback;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;

@SuppressWarnings("serial")
public class LodgeFeedbackAction extends GridPropertyBean
{
	/*
	 * @SuppressWarnings("unchecked") Map session =
	 * ActionContext.getContext().getSession();
	 */

	// Class Variable Declaration
	private String abc;
	private String uid;
	private String subdeptId = "";
	private String headerEscalation;
	private String bydept_subdept;
	private JSONArray commonJSONArray = new JSONArray();
	private String conditionVar_Value = "";

	private String selectedId;
	private String feedbackBy;
	private String mobileno;
	private String emailId;

	private String byDept;
	private String bydept;
	private String todept;
	private String tosubdept;
	private String feedType;
	private String subCategory;
	private String feed_brief;
	private String viaFrom;
	private String location;
	private String recvSMS;
	private String recvEmail;
	private String adrrTime;
	private String escTime;

	private String ticket_no;
	private String catg;
	private String open_date;
	private String open_time;
	private String allotto;
	private String subCatg;
	private String addrTime;
	private String addrDate;
	private String feedStatus;

	private String allot_to_mobno;

	private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";
	private String floor = "";

	private String Addressing_Time = "";

	private Map<Integer, String> deptList = null;
	private Map<Integer, String> serviceDeptList = null;

	// List and Pojo Bean Declaration
	private EmpBasicPojoBean empObj = null;
	private List<Object> empData4Escalation = null;

	private String dataFor;
	private String pageFor;
	String new_escalation_datetime = "0000-00-00#00:00", non_working_timing = "00:00";
	private Map<String, String> fromDept = null;
	private String columnName;
	private FeedbackPojo fstatus;
	private String orgImgPath;

	@SuppressWarnings("unchecked")
	public String firstAction4Complaint()
	{

		List dataList = null;
		ActivityBoardHelper ABH = new ActivityBoardHelper();
		fromDept = new LinkedHashMap<String, String>();
		dataList = ABH.getAllDepartment(connectionSpace, null);
		if (dataList != null && dataList.size() > 0)
		{
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

		System.out.println("Inside  for firstAction4Complaint");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// Method for getting fields for complaint page
				setComplaintPageFields();

				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();

				if (feedStatus != null && !feedStatus.equals("") && feedStatus.equalsIgnoreCase("Call"))
				{
					// getting Normal department mapped for HDM
					departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, "", connectionSpace);
					if (departmentlist != null && departmentlist.size() > 0)
					{
						for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							deptList.put((Integer) object[0], object[1].toString());
						}
						departmentlist.clear();
					}
				}

				// Start Code for getting Service Department list
				serviceDeptList = new LinkedHashMap<Integer, String>();
				departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, dataFor, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put((Integer) object1[0], object1[1].toString());
					}
				}
				// End Code for getting Service Department list
				returnResult = SUCCESS;
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

	@SuppressWarnings("unchecked")
	public String firstAction4ReAllot()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List departmentlist = new ArrayList();

				// Start Code for getting Service Department list
				serviceDeptList = new LinkedHashMap<Integer, String>();
				departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, dataFor, connectionSpace);
				if (departmentlist != null && departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object1 = (Object[]) iterator.next();
						serviceDeptList.put((Integer) object1[0], object1[1].toString());
					}
				}
				// End Code for getting Service Department list
				returnResult = SUCCESS;
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

	public void setComplaintPageFields()
	{
		ConfigurationUtilBean obj;
		pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();

		// List for Department Data
		List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID, connectionSpace, "", 0, "table_name", "dept_config_param");
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("deptName"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					obj.setMandatory(true);
					pageFieldsColumns.add(obj);
				}
			}
		}
		// List for Sub Department Data
		List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");
		if (subdept_deptList != null && subdept_deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : subdept_deptList)
			{
				obj = new ConfigurationUtilBean();
				if (gdv.getColomnName().equalsIgnoreCase("subdeptname"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType("D");
					obj.setMandatory(true);
					pageFieldsColumns.add(obj);
				}

			}
		}

		// List for Complaint to Data
		List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
		if (feedLodgeTags != null && feedLodgeTags.size() > 0)
		{
			for (GridDataPropertyView gdv : feedLodgeTags)
			{
				obj = new ConfigurationUtilBean();
				if (!gdv.getColomnName().equals("ticket_no") && !gdv.getColomnName().equals("deptHierarchy") && !gdv.getColomnName().equals("by_dept_subdept") && !gdv.getColomnName().equals("to_dept_subdept")
						&& !gdv.getColomnName().equals("allot_to") && !gdv.getColomnName().equals("escalation_date") && !gdv.getColomnName().equals("escalation_time") && !gdv.getColomnName().equals("open_date")
						&& !gdv.getColomnName().equals("open_time") && !gdv.getColomnName().equals("level") && !gdv.getColomnName().equals("status") && !gdv.getColomnName().equals("via_from")
						&& !gdv.getColomnName().equals("feed_registerby") && !gdv.getColomnName().equals("action_by") && !gdv.getColomnName().equals("resolve_date") && !gdv.getColomnName().equals("resolve_time")
						&& !gdv.getColomnName().equals("resolve_duration") && !gdv.getColomnName().equals("resolve_remark") && !gdv.getColomnName().equals("resolve_by") && !gdv.getColomnName().equals("spare_used")
						&& !gdv.getColomnName().equals("hp_date") && !gdv.getColomnName().equals("hp_time") && !gdv.getColomnName().equals("hp_reason") && !gdv.getColomnName().equals("sn_reason")
						&& !gdv.getColomnName().equals("sn_on_date") && !gdv.getColomnName().equals("sn_on_time") && !gdv.getColomnName().equals("sn_upto_date") && !gdv.getColomnName().equals("sn_upto_time")
						&& !gdv.getColomnName().equals("sn_duration") && !gdv.getColomnName().equals("ig_date") && !gdv.getColomnName().equals("ig_time") && !gdv.getColomnName().equals("ig_reason")
						&& !gdv.getColomnName().equals("transfer_date") && !gdv.getColomnName().equals("transfer_time") && !gdv.getColomnName().equals("transfer_reason") && !gdv.getColomnName().equals("moduleName")
						&& !gdv.getColomnName().equals("feedback_remarks") && !gdv.getColomnName().equals("transferId") && !gdv.getColomnName().equals("non_working_time"))
				{
					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					pageFieldsColumns.add(obj);
				}
			}
		}
		// System.out.println("Finally The List Size is  "+pageFieldsColumns.size());
	}

	@SuppressWarnings("unchecked")
	public String userListByDept()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			StringBuilder qry = new StringBuilder();
			try
			{
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				List resultList = new ArrayList<String>();
				qry.append(" select emp.id,emp.empName from employee_basic as emp");
				qry.append(" where emp.deptName=" + conditionVar_Value + " order by emp.empName");
				System.out.println(" ********Query  " + qry.toString());
				resultList = new HelpdeskUniversalHelper().getData(qry.toString(), connectionSpace);

				System.out.println("****List Size : " + resultList.size());
				if (resultList.size() > 0)
				{
					for (Object c : resultList)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				returnResult = SUCCESS;
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

	// Method for getting Contact detail from emp_basic table
	@SuppressWarnings("unchecked")
	public String getContactDetail()
	{
		String returnResult = ERROR;
		try
		{
			empObj = new EmpBasicPojoBean();
			List empList = new HelpdeskUniversalAction().getEmpData(uid, pageFor, columnName);
			if (empList != null && empList.size() > 0)
			{
				for (Iterator iterator = empList.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();

					// Set the value of Employee Name
					if (objectCol[0] == null || objectCol[0].toString().equals(""))
					{
						empObj.setEmpName("NA");
					}
					else
					{
						empObj.setEmpName(objectCol[0].toString());
					}

					// Set the value of Employee Mobile No
					if (objectCol[1] == null || objectCol[1].toString().equals(""))
					{
						empObj.setMobOne("NA");
					}
					else
					{
						empObj.setMobOne(objectCol[1].toString());
					}

					// Set The value of Employee Email ID
					if (objectCol[2] == null || objectCol[2].toString().equals(""))
					{
						empObj.setEmailIdOne("helpdesk@blkhospital.com");
					}
					else
					{
						empObj.setEmailIdOne(objectCol[2].toString());
					}

					if (objectCol[3] == null || objectCol[3].toString().equals(""))
					{
						empObj.setOther2("-1");
					}
					else
					{
						empObj.setOther2(objectCol[3].toString());
					}

					if (objectCol[4] == null || objectCol[4].toString().equals(""))
					{
						empObj.setDeptName("NA");
					}
					else
					{
						empObj.setDeptName(objectCol[4].toString());
					}

					if (objectCol[5] == null || objectCol[5].toString().equals(""))
					{
						empObj.setCity("NA");
					}
					else
					{
						empObj.setCity(objectCol[5].toString());
					}
					if (objectCol[6] == null || objectCol[6].toString().equals(""))
					{
						empObj.setEmpId("NA");
					}
					else
					{
						empObj.setEmpId(objectCol[6].toString());
					}

					if (pageFor.equalsIgnoreCase("SD"))
					{/*
					 * if (objectCol[5] == null ||
					 * objectCol[5].toString().equals("")) { empObj.setId(-1); }
					 * else {
					 * empObj.setId(Integer.parseInt(objectCol[5].toString()));
					 * }
					 * 
					 * if (objectCol[6] == null ||
					 * objectCol[6].toString().equals("")) {
					 * empObj.setSubdept("NA"); } else {
					 * empObj.setSubdept(objectCol[6].toString()); }
					 */
					}

					else if (pageFor.equalsIgnoreCase("SD"))
					{/*
					 * if (objectCol[5] == null ||
					 * objectCol[5].toString().equals("")) {
					 * empObj.setCity("NA"); } else {
					 * empObj.setCity(objectCol[5].toString()); }
					 */
					}
				}
			}

			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	// Method for getting Employee data for ticket allocation
	@SuppressWarnings("unchecked")
	public String getEmp4Escalation()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				empData4Escalation = new ArrayList<Object>();
				String tolevel = "";
				// Code for getting the Escalation Date and Escalation Time
				List subCategoryList = HUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connectionSpace);
				if (subCategoryList != null && subCategoryList.size() > 0)
				{
					for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
					{
						Object[] objectCol = (Object[]) iterator.next();

						if (objectCol[8] == null)
						{
							tolevel = "1";
						}
						else
						{
							tolevel = objectCol[8].toString().substring(5);
						}
					}
				}

				String dept_id = HUH.getDeptId(getTosubdept(), connectionSpace);
				String floor_status = HUH.getFloorStatus(dept_id, connectionSpace);

				List emp4Escalation = new ArrayList();
				String newToLevel = "";
				emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, tolevel, floor_status, floor, connectionSpace);
				if (emp4Escalation == null || emp4Escalation.size() == 0)
				{
					newToLevel = "" + (Integer.parseInt(tolevel) + 1);
					emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, newToLevel, floor_status, floor, connectionSpace);
					if (emp4Escalation == null || emp4Escalation.size() == 0)
					{
						newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
						emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, newToLevel, floor_status, floor, connectionSpace);
						if (emp4Escalation == null || emp4Escalation.size() == 0)
						{
							newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
							emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, newToLevel, floor_status, floor, connectionSpace);
						}
					}
				}

				if (emp4Escalation != null && emp4Escalation.size() > 0)
				{
					setRecords(emp4Escalation.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					List<Object> Listhb = new ArrayList<Object>();

					List fieldNames = new ArrayList();
					fieldNames.add("id");
					fieldNames.add("empName");
					fieldNames.add("mobOne");
					fieldNames.add("emailIdOne");

					for (Iterator it = emp4Escalation.iterator(); it.hasNext();)
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
								}
								else
								{
									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
						}
						Listhb.add(one);
					}
					setEmpData4Escalation(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult = SUCCESS;
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

	@SuppressWarnings(
	{ "unchecked" })
	public synchronized String registerFeedbackViaCall()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			Lock lock = new ReentrantLock();
			lock.lock();
			try
			{
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				MsgMailCommunication MMC = new MsgMailCommunication();
				CommonOperInterface cot = new CommonConFactory().createInterface();
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");

				if (colName != null && colName.size() > 0)
				{
					@SuppressWarnings("unused")
					boolean status = false;
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName)
					{
						if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category") && !tableColumes.getColomnName().equals("resolutionTime"))
						{
							TableColumes ob1 = new TableColumes();
							ob1.setColumnname(tableColumes.getColomnName());
							ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
							ob1.setConstraint("default NULL");
							TableColumnName.add(ob1);
						}
					}

					// Method calling for creating table on the basis of above
					cot.createTable22("feedback_status", TableColumnName, connectionSpace);

					// Object creation for Feedback Pojo Bean
					FeedbackPojo fbp = null;
					// Local variables declaration
					String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", allottoId = "", to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", mailText = "", tolevel = "", needesc = "", esc_time="";
					String resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA";
					// Code for getting the Escalation Date and Escalation Time
					List subCategoryList = HUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connectionSpace);
					if (subCategoryList != null && subCategoryList.size() > 0)
					{
						for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
						{
							Object[] objectCol = (Object[]) iterator.next();
							if (objectCol[4] == null)
							{
								adrr_time = "06:00";
							}
							else
							{
								adrr_time = objectCol[4].toString();
							}
							if (objectCol[5] == null)
							{
								res_time = "10:00";
							}
							else
							{
								res_time = objectCol[5].toString();
							}
							if (objectCol[6] == null)
							{
								esc_time = "10:00";
							}
							else
							{
								esc_time = objectCol[6].toString();
							}

							if (objectCol[8] == null)
							{
								tolevel = "Level1";
							}
							else
							{
								tolevel = objectCol[8].toString();
							}
							if (objectCol[9] == null)
							{
								needesc = "Y";
							}
							else
							{
								needesc = objectCol[9].toString();
							}
						}
					}

					// To Sub department on both case caal/ Online
					to_dept_subdept = tosubdept;

					String[] date_time_arr = null;
					// Method for get Escalation Date & Time after Check Holiday
					String[] adddate_time = null;
					String Address_Date_Time = "NA";
					String deptid = new HelpdeskUniversalHelper().getDeptId(to_dept_subdept, connectionSpace);
					
					String severityLevel = getSeverityLevel(deptid, adrr_time, esc_time, res_time);
					
					
					if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
					{
						getNextEscalationDateTime(adrr_time, res_time, deptid, "HDM", connectionSpace);
						String[] newdate_time = null;
						if (new_escalation_datetime != null && new_escalation_datetime != "")
						{
							newdate_time = new_escalation_datetime.split("#");
							if (newdate_time.length > 0)
							{
								escalation_date = newdate_time[0];
								escalation_time = newdate_time[1];
							}
						}
						Address_Date_Time = DateUtil.newdate_BeforeTime(escalation_date, escalation_time, res_time);

						if (Address_Date_Time != null && !Address_Date_Time.equals("") && !Address_Date_Time.equals("NA"))
						{
							String esc_start_time = "00:00";
							String esc_end_time = "24:00";
							String esc_working_day = "";
							esc_working_day = DateUtil.getDayofDate(escalation_date);
							// List of working timing data
							List workingTimingData = null;
							workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
							if (workingTimingData != null && workingTimingData.size() > 0)
							{
								String time_status = "", time_diff = "", working_hrs = "";
								adddate_time = Address_Date_Time.split("#");
								esc_start_time = workingTimingData.get(1).toString();
								esc_end_time = workingTimingData.get(2).toString();
								time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
								if (time_status != null && !time_status.equals("") && time_status.equals("before"))
								{
									time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], escalation_date, esc_start_time);
									String backdate_working_Hrs = "24:00";
									String previous_working_date = "";
									previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(escalation_date), -1), connectionSpace);
									esc_working_day = DateUtil.getDayofDate(previous_working_date);
									workingTimingData.clear();
									workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
									if (workingTimingData != null && workingTimingData.size() > 0)
									{
										esc_start_time = workingTimingData.get(1).toString();
										esc_end_time = workingTimingData.get(2).toString();
										working_hrs = workingTimingData.get(3).toString();
										if (DateUtil.checkTwoTime(working_hrs, time_diff))
										{
											Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
										}
										else
										{
											time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
											previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNewDate("day", -1, previous_working_date), connectionSpace);

											esc_working_day = DateUtil.getDayofDate(previous_working_date);
											workingTimingData.clear();
											workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
											if (workingTimingData != null && workingTimingData.size() > 0)
											{
												esc_start_time = workingTimingData.get(1).toString();
												esc_end_time = workingTimingData.get(2).toString();
												working_hrs = workingTimingData.get(3).toString();
												if (DateUtil.checkTwoTime(working_hrs, time_diff))
												{
													Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
												}
												else
												{
													time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
												}
											}
										}
									}
								}
							}
							// Address_Date_Time =
							// DateUtil.convertDateToIndianFormat(Address_Date_Time.substring(0,
							// 10))+" "+Address_Date_Time.substring(11, 16);
						}
					}
					else
					{
						escalation_date = "0000-00-00";
						escalation_time = "00:00";
						Address_Date_Time = DateUtil.newdate_AfterTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time);
						date_time_arr = Address_Date_Time.split("#");
						String esc_start_time = "00:00";
						String esc_end_time = "24:00";
						String esc_working_day = "";
						esc_working_day = DateUtil.getDayofDate(date_time_arr[0]);
						// List of working timing data
						List workingTimingData = null;
						workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							String time_status = "", time_diff = "", working_hrs = "";
							adddate_time = Address_Date_Time.split("#");
							esc_start_time = workingTimingData.get(2).toString();
							esc_end_time = workingTimingData.get(3).toString();
							time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
							if (time_status != null && !time_status.equals("") && (time_status.equals("before") || time_status.equals("after")))
							{
								time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], adddate_time[0], esc_start_time);
								String backdate_working_Hrs = "24:00";
								String previous_working_date = "";
								String ddd1 = DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]), -1);
								previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]), -1), connectionSpace);
								esc_working_day = DateUtil.getDayofDate(previous_working_date);
								workingTimingData.clear();
								workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
								if (workingTimingData != null && workingTimingData.size() > 0)
								{
									esc_start_time = workingTimingData.get(2).toString();
									esc_end_time = workingTimingData.get(3).toString();
									working_hrs = workingTimingData.get(4).toString();
									if (DateUtil.checkTwoTime(working_hrs, time_diff))
									{
										Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
									}
									else
									{
										time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
										previous_working_date = new EscalationHelper().getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(previous_working_date, -1), connectionSpace);
										esc_working_day = DateUtil.getDayofDate(previous_working_date);
										workingTimingData.clear();
										workingTimingData = new EscalationHelper().getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
										if (workingTimingData != null && workingTimingData.size() > 0)
										{

											esc_start_time = workingTimingData.get(2).toString();
											esc_end_time = workingTimingData.get(3).toString();
											working_hrs = workingTimingData.get(4).toString();
											if (DateUtil.checkTwoTime(working_hrs, time_diff))
											{
												Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
											}
											else
											{
												time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
											}
										}
									}
								}
							}
						}
						// Address_Date_Time =
						// DateUtil.convertDateToIndianFormat(Address_Date_Time.substring(0,
						// 10))+" "+Address_Date_Time.substring(11, 16);
					}

					// Block for setting the Allotment Id/Feedback By
					// Name/Feedback By Mobile No/ Feedback By email Id and
					// ToDept/ByDept OR To Sub Dept/By Sub dept in the case of
					// Via Call feedback Lodging
					if (viaFrom != null && viaFrom.equals("call"))
					{
						String[] selectedIdArr;
						feedby = feedbackBy;
						feedbymob = mobileno;
						feedbyemailid = emailId;

						if (selectedId.contains(","))
						{
							selectedIdArr = selectedId.split(",");
							for (int i = 0; i < selectedIdArr.length; i++)
							{
								allottoId = selectedIdArr[i];
								break;
							}
						}
						else
						{
							allottoId = selectedId;
						}
						if (getBydept_subdept() != null && getBydept_subdept().equals("NA"))
						{
							by_dept_subdept = bydept;
						}
						else
						{
							by_dept_subdept = getBydept_subdept();
						}
					}

					// Block for setting the Allotment Id/Feedback By
					// Name/Feedback By Mobile No/ Feedback By email Id and
					// ToDept/ByDept OR To Sub Dept/By Sub dept in the case of
					// Online feedback Lodging
					if (viaFrom != null && viaFrom.equals("online"))
					{
						List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
						if (empData != null && empData.size() > 0)
						{
							for (Iterator iterator = empData.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && !object[0].toString().equals(""))
								{
									feedby = object[0].toString();
								}
								else
								{
									feedby = "NA";
								}

								if (object[1] != null && !object[1].toString().equals(""))
								{
									feedbymob = object[1].toString();
								}
								else
								{
									feedbymob = "NA";
								}

								if (object[2] != null && !object[2].toString().equals(""))
								{
									feedbyemailid = object[2].toString();
								}
								else
								{
									feedbyemailid = "NA";
								}

								if (object[3] != null && !object[3].toString().equals(""))
								{
									by_dept_subdept = object[3].toString();
								}
								else
								{
									by_dept_subdept = "-1";
								}
							}
						}

						List allotto = null;
						List allotto1 = null;
						List<String> one = new ArrayList<String>();
						List<String> two = new ArrayList<String>();
						List<String> two_new = new ArrayList<String>();
						String floor_status = HUH.getFloorStatus(getTosubdept(), connectionSpace);
						try
						{
							allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, tolevel.substring(5), floor_status, floor, connectionSpace);
							if (allotto == null || allotto.size() == 0)
							{
								String newToLevel = "";
								newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
								allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, newToLevel, floor_status, floor, connectionSpace);
								if (allotto == null || allotto.size() == 0)
								{
									newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
									allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, newToLevel, floor_status, floor, connectionSpace);
									if (allotto == null || allotto.size() == 0)
									{
										newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
										allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, newToLevel, floor_status, floor, connectionSpace);
									}
								}
							}

							allotto1 = HUA.getRandomEmployee(tosubdept, deptLevel, tolevel.substring(5), allotto, connectionSpace);

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
							}
							else
							{
								List pending_alloted = HUA.getPendingAllotto(tosubdept, deptLevel);
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
									allottoId = HUA.getRandomEmployee("", one);
								}
								else
								{
									allottoId = HUA.getRandomEmployee("", allotto);
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					String ticketLevel = "";
					if (allottoId != null && !allottoId.equals(""))
					{
						ticketLevel = HUH.getTicketLevel(allottoId, connectionSpace);
					}
					// Block for getting Ticket No
					ticketno = HelpdeskUniversalHelper.getTicketNo(deptid, "HDM", connectionSpace);

					if (ticketno != null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId != null && !allottoId.equals(""))
					{
						String exist_ticket = "";
						// exist_ticket = new
						// HelpdeskUniversalHelper().isExist("feedback_status",
						// "feed_by", feedby, "subCatg", subCategory,
						// "feed_brief", feed_brief, "location",
						// floor+"-"+location, "via_from", viaFrom, "open_date",
						// DateUtil.getCurrentDateUSFormat(), "open_time",
						// DateUtil.getCurrentTime(), "feed_registerby",
						// userName, connectionSpace);
						if (exist_ticket.equals("") || exist_ticket.equals("NA"))
						{
							// Setting the column values after getting from the
							// page
							InsertDataTable ob = new InsertDataTable();
							List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
							ob.setColName("ticket_no");
							ob.setDataName(ticketno);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_by");
							ob.setDataName(feedby);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_by_mobno");
							ob.setDataName(feedbymob);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_by_emailid");
							ob.setDataName(feedbyemailid);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("deptHierarchy");
							ob.setDataName(deptLevel);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("by_dept_subdept");
							ob.setDataName(by_dept_subdept);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("subcatg");
							ob.setDataName(subCategory);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_brief");
							ob.setDataName(feed_brief);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("to_dept_subdept");
							ob.setDataName(to_dept_subdept);
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
							ob.setColName("open_date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("open_time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("location");
							ob.setDataName(floor + "-" + location);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("level");
							ob.setDataName(ticketLevel);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("status");
							ob.setDataName("Pending");
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("via_from");
							ob.setDataName(DateUtil.makeTitle(viaFrom));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feed_registerby");
							ob.setDataName(userName);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("moduleName");
							ob.setDataName("HDM");
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("non_working_time");
							ob.setDataName(non_working_timing);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("Addr_date_time");
							ob.setDataName(Address_Date_Time);
							insertData.add(ob);
							
							if(severityLevel!=null)
							{
								ob = new InsertDataTable();
								ob.setColName("severity_level");
								ob.setDataName(severityLevel);
								insertData.add(ob);
							}

							exist_ticket = new HelpdeskUniversalHelper().isExist("feedback_status", "feed_by", feedby, "subCatg", subCategory, "feed_brief", feed_brief, "location", floor + "-" + location, "via_from", viaFrom, "open_date",
									DateUtil.getCurrentDateUSFormat(), "open_time", DateUtil.getCurrentTimeHourMin(), "feed_registerby", userName, connectionSpace);

							// Method calling for inserting the values in the
							// feedback_status table
							boolean status1 = false;
							if (exist_ticket != null && exist_ticket.equalsIgnoreCase("NA"))
							{
								status1 = cot.insertIntoTable("feedback_status", insertData, connectionSpace);
							}
							insertData.clear();

							// Block for sending SMS and Mail to the Engineer
							// and
							// status table successfully
							if (status1)
							{
								// List for holding the data of currently lodged
								List data = HUH.getFeedbackDetail(ticketno, "SD", "Pending", "", connectionSpace);
								fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);
								
								feedbackBy = DateUtil.makeTitle(fbp.getFeed_registerby());
								ticket_no = fbp.getTicket_no();
								allotto = fbp.getFeedback_allot_to();
								allot_to_mobno= fbp.getMobOne();
								escTime = DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time().substring(0, 5);
								
								printTicket(ticketno,"TicketNo");
								if (fbp != null)
								{
									mapEscalation("" + fbp.getId(), fbp.getLevel(), fbp.getEscalation_date(), fbp.getEscalation_time(), connectionSpace);
									// Block for sending sms for Level1 Engineer
									if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && !fbp.getMobOne().startsWith("NA")
											&& (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
									{
										String levelMsg = "Open Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", To Be Closed By: " + fbp.getAddr_date_time() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby())
												+ ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ".";
										MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "HDM");
									}
									// Block for sending sms for Complainant
									if (getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10
											&& (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7")))
									{
										String complainMsg = "Dear " + DateUtil.makeTitle(fbp.getFeed_registerby()) + "," + " Ticket No: " + fbp.getTicket_no() + ", Resolution Time: "
												+ DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time().substring(0, 5) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief()
												+ " is open.";
										MMC.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), complainMsg, ticketno, "Pending", "0", "HDM");
									}
									// Block for creating mail for Level1
									// Engineer
									if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
									{
										mailText = HUH.getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, true);
										MMC.addMail(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getEmailIdOne(), "Open Feedback Alert", mailText, ticketno, "Pending", "0", "", "HDM");
									}
									// Block for creating mail for complainant
									if (getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("") && !fbp.getFeedback_by_emailid().equals("NA"))
									{
										mailText = HUH.getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, false);
										MMC.addMail(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_emailid(), "Open Feedback Alert", mailText, ticketno, "Pending", "0", "", "HDM");
									}
								}
							}
							return SUCCESS;
						}
						else
						{
							List data = HUH.getFeedbackDetail(exist_ticket, "SD", "Pending", "", connectionSpace);
							fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);

							printTicket(exist_ticket,"TicketNo");
							return SUCCESS;
						}
					}
					else
					{
						if (ticketno == null || ticketno.equalsIgnoreCase("") || ticketno.equalsIgnoreCase("NA"))
						{
							addActionMessage("Please Check Your Ticket Series!!!");
						}
						else if (allottoId == null || allottoId.equalsIgnoreCase("") || allottoId.equalsIgnoreCase("-1"))
						{
							addActionMessage("May be Someone is not mapped for taking the complaint. Please contact to concern service department !!!");
						}
						returnResult = "TicketError";
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				lock.unlock();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
	{
		String startTime = "", endTime = "", callflag = "";
		String dept_id = todept;

		String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
		String working_day = DateUtil.getDayofDate(working_date);
		// List of working timing data
		List workingTimingData = new EscalationHelper().getWorkingTimeData(module, working_day, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			startTime = workingTimingData.get(1).toString();
			endTime = workingTimingData.get(2).toString();
			// Here we know the complaint lodging time is inside the the start
			// and end time or not
			callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
			if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
			{
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
				}
				else
				{
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			}
			else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
			{
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
				String newdatetime[] = new_escalation_datetime.split("#");
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = "00:00";
				}
				else
				{
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			}
			else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
			{
				non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, "24:00");

				workingTimingData.clear();
				String main_difference = DateUtil.addTwoTime(adrr_time, res_time);
				workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					startTime = workingTimingData.get(1).toString();
					String left_time = workingTimingData.get(4).toString();
					String final_date = workingTimingData.get(5).toString();
					new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
					non_working_timing = workingTimingData.get(3).toString();
				}
			}
		}
	}

	boolean mapEscalation(String id, String level, String date, String time, SessionFactory connection)
	{
		boolean flag = false;

		try
		{
			CommonOperInterface cot = new CommonConFactory().createInterface();
			List<TableColumes> TableColumnName = new ArrayList<TableColumes>();

			TableColumes tc = new TableColumes();
			tc.setColumnname("feed_status_id");
			tc.setDatatype("varchar(10)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("allot_to_level");
			tc.setDatatype("varchar(10)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("nextEscDateTime");
			tc.setDatatype("varchar(20)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("nextEscLevel_2");
			tc.setDatatype("varchar(10)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("EscLevel_2_DateTime");
			tc.setDatatype("varchar(20)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("nextEscLevel_3");
			tc.setDatatype("varchar(10)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("EscLevel_3_DateTime");
			tc.setDatatype("varchar(20)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("nextEscLevel_4");
			tc.setDatatype("varchar(10)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("EscLevel_4_DateTime");
			tc.setDatatype("varchar(20)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("nextEscLevel_5");
			tc.setDatatype("varchar(10)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			tc = new TableColumes();
			tc.setColumnname("EscLevel_5_DateTime");
			tc.setDatatype("varchar(20)");
			tc.setConstraint("default NULL");
			TableColumnName.add(tc);

			// Method calling for creating table on the basis of above
			cot.createTable22("escalation_mapping", TableColumnName, connection);

			InsertDataTable ob = new InsertDataTable();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			ob.setColName("feed_status_id");
			ob.setDataName(id);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("allot_to_level");
			ob.setDataName(level);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("nextEscDateTime");
			ob.setDataName(DateUtil.convertDateToIndianFormat(date) + "(" + time + ")");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("nextEscLevel_2");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("EscLevel_2_DateTime");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("nextEscLevel_3");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("EscLevel_3_DateTime");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("nextEscLevel_4");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("EscLevel_4_DateTime");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("nextEscLevel_5");
			ob.setDataName("NA");
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("EscLevel_5_DateTime");
			ob.setDataName("NA");
			insertData.add(ob);

			// Method calling for inserting the values in the
			// feedback_status table
			@SuppressWarnings("unused")
			boolean status1 = cot.insertIntoTable("escalation_mapping", insertData, connection);
			insertData.clear();

		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		return flag;
	}

	public void printTicket(String ticketNo,String flag)
	{
		fstatus= new FeedbackPojo();
		String orgDetail = (String) session.get("orgDetail");
		String[] orgData = null;
		if (orgDetail != null && !orgDetail.equals(""))
		{
			orgData = orgDetail.split("#");
			orgName = orgData[0];
			address = orgData[1];
			city = orgData[2];
			pincode = orgData[3];
			orgImgPath=new CommonWork().getOrganizationImage((SessionFactory)session.get("connectionSpace"));
			fstatus  = new ActionOnFeedback().getTicketDetail(ticketNo,flag);
		}
	}

	public String getSeverityLevel(String deptid, String adrr_time, String esc_time, String res_time)
	{
		String severityLevel= null;
		try
		{
			String query ="SELECT severityCheckOn,fromTime,toTime,severityLevel FROM severity_detail WHERE deptName="+deptid;
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null)
					{
						if(object[0].toString().equalsIgnoreCase("AT"))
						{
							if(object[1]!=null && object[2]!=null)
							{
								boolean checkStatus = DateUtil.checkTimeBetween2Times(object[1].toString(), object[2].toString(), adrr_time);
								if(checkStatus)
								{
									severityLevel = object[3].toString();
									break;
								}
							}
						}
						else if(object[0].toString().equalsIgnoreCase("RT"))
						{
							if(object[1]!=null && object[2]!=null)
							{
								boolean checkStatus = DateUtil.checkTimeBetween2Times(object[1].toString(), object[2].toString(), res_time);
								if(checkStatus)
								{
									severityLevel = object[3].toString();
									break;
								}
							}
						}
						else if(object[0].toString().equalsIgnoreCase("ET"))
						{
							if(object[1]!=null && object[2]!=null)
							{
								boolean checkStatus = DateUtil.checkTimeBetween2Times(object[1].toString(), object[2].toString(), esc_time);
								if(checkStatus)
								{
									severityLevel = object[3].toString();
									break;
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return severityLevel;
	}
	
	
	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public EmpBasicPojoBean getEmpObj()
	{
		return empObj;
	}

	public void setEmpObj(EmpBasicPojoBean empObj)
	{
		this.empObj = empObj;
	}

	public String getSubdeptId()
	{
		return subdeptId;
	}

	public void setSubdeptId(String subdeptId)
	{
		this.subdeptId = subdeptId;
	}

	public String getHeaderEscalation()
	{
		return headerEscalation;
	}

	public void setHeaderEscalation(String headerEscalation)
	{
		this.headerEscalation = headerEscalation;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public List<Object> getEmpData4Escalation()
	{
		return empData4Escalation;
	}

	public void setEmpData4Escalation(List<Object> empData4Escalation)
	{
		this.empData4Escalation = empData4Escalation;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
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

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getByDept()
	{
		return byDept;
	}

	public void setByDept(String byDept)
	{
		this.byDept = byDept;
	}

	public String getTodept()
	{
		return todept;
	}

	public void setTodept(String todept)
	{
		this.todept = todept;
	}

	public String getSubCategory()
	{
		return subCategory;
	}

	public void setSubCategory(String subCategory)
	{
		this.subCategory = subCategory;
	}

	public String getFeed_brief()
	{
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
	}

	public String getViaFrom()
	{
		return viaFrom;
	}

	public void setViaFrom(String viaFrom)
	{
		this.viaFrom = viaFrom;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getRecvSMS()
	{
		return recvSMS;
	}

	public void setRecvSMS(String recvSMS)
	{
		this.recvSMS = recvSMS;
	}

	public String getBydept()
	{
		return bydept;
	}

	public void setBydept(String bydept)
	{
		this.bydept = bydept;
	}

	public String getTosubdept()
	{
		return tosubdept;
	}

	public void setTosubdept(String tosubdept)
	{
		this.tosubdept = tosubdept;
	}

	public String getAdrrTime()
	{
		return adrrTime;
	}

	public void setAdrrTime(String adrrTime)
	{
		this.adrrTime = adrrTime;
	}

	public String getEscTime()
	{
		return escTime;
	}

	public void setEscTime(String escTime)
	{
		this.escTime = escTime;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
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

	public String getAllotto()
	{
		return allotto;
	}

	public void setAllotto(String allotto)
	{
		this.allotto = allotto;
	}

	public String getSubCatg()
	{
		return subCatg;
	}

	public void setSubCatg(String subCatg)
	{
		this.subCatg = subCatg;
	}

	public String getAddrTime()
	{
		return addrTime;
	}

	public void setAddrTime(String addrTime)
	{
		this.addrTime = addrTime;
	}

	public String getAddrDate()
	{
		return addrDate;
	}

	public void setAddrDate(String addrDate)
	{
		this.addrDate = addrDate;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public String getBydept_subdept()
	{
		return bydept_subdept;
	}

	public void setBydept_subdept(String bydept_subdept)
	{
		this.bydept_subdept = bydept_subdept;
	}

	public String getAllot_to_mobno()
	{
		return allot_to_mobno;
	}

	public void setAllot_to_mobno(String allot_to_mobno)
	{
		this.allot_to_mobno = allot_to_mobno;
	}

	public String getAbc()
	{
		return abc;
	}

	public void setAbc(String abc)
	{
		this.abc = abc;
	}

	public String getRecvEmail()
	{
		return recvEmail;
	}

	public void setRecvEmail(String recvEmail)
	{
		this.recvEmail = recvEmail;
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

	public String getFeedType()
	{
		return feedType;
	}

	public void setFeedType(String feedType)
	{
		this.feedType = feedType;
	}

	public String getFloor()
	{
		return floor;
	}

	public void setFloor(String floor)
	{
		this.floor = floor;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}

	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public String getPageFor()
	{
		return pageFor;
	}

	public void setPageFor(String pageFor)
	{
		this.pageFor = pageFor;
	}

	public String getAddressing_Time()
	{
		return Addressing_Time;
	}

	public void setAddressing_Time(String addressing_Time)
	{
		Addressing_Time = addressing_Time;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public String getConditionVar_Value()
	{
		return conditionVar_Value;
	}

	public void setConditionVar_Value(String conditionVar_Value)
	{
		this.conditionVar_Value = conditionVar_Value;
	}

	public Map<String, String> getFromDept()
	{
		return fromDept;
	}

	public void setFromDept(Map<String, String> fromDept)
	{
		this.fromDept = fromDept;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}

	public FeedbackPojo getFstatus()
	{
		return fstatus;
	}

	public void setFstatus(FeedbackPojo fstatus)
	{
		this.fstatus = fstatus;
	}

	public String getOrgImgPath()
	{
		return orgImgPath;
	}

	public void setOrgImgPath(String orgImgPath)
	{
		this.orgImgPath = orgImgPath;
	}

}