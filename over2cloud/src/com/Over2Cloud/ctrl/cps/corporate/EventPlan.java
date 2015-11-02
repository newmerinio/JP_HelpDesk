package com.Over2Cloud.ctrl.cps.corporate;

import java.math.BigInteger;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.emory.mathcs.backport.java.util.LinkedList;

public class EventPlan extends ActionSupport implements ServletRequestAware
{

	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommonOperInterface coi = new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	private HttpServletRequest request;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> viewList;

	static final Log log = LogFactory.getLog(EventPlan.class);
	// Get the requested page. By default grid sets this to 1.
	private Integer rows = 0;
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
	// Grid colomn view
	private String oper;

	private String id;

	List<ConfigurationUtilBean> packageFields;
	private Map<String, String> eventMap;
	private Map<String, String> buddyMap;
	private Map<String, String> offeringMap;
	private Map<String, String> currencyMap;
	private Map<String, String> teamMap;
	private Map<String, String> mapParamRoi;

	private String eplan_from_buddy;
	private String eplan_from_offering;
	private String eplan_team;
	private String eventid;
	private String unitType;
	private String totalBudget;

	private String date;
	private String status;
	private String status_comments;
	private JSONArray dataArray = new JSONArray();

	private EventPlanPojo epp;

	// before approve by event form manager
	public String beforeApproveByManagerDCRAdd()
	{

		// System.out.println("beforeApproveByManagerDCRAdd                id   >>>"+id);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				fetchDCRValues();

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

	// dCr approving by manager
	public String approveByManagerDCRAdd()
	{

		// System.out.println(status_comments
		// +"approveByManagerDCRAdd id   >>>"+id + "\t  "+status+ " \t  "+
		// date);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				fetchDCRValues();

				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String loggedDetail[] = CUA.getEmpDetailsByUserName("WFPM", userName);

				StringBuilder query = new StringBuilder("");
				query.append(" update event_plan set dsr_status='" + status + "'" + " , dsr_status_comments='" + status_comments + "' , " + " dsr_status_date='" + DateUtil.getCurrentDateUSFormat() + "' " + ", manager_id='" + loggedDetail[0] + "'" + " where " + " id='" + id + "'" + " and for_month = '" + date + "' ");

				boolean result = coi.updateTableColomn(connectionSpace, query);

				if (result)
					addActionMessage("Request For DCR " + status + " Successfully");
				else
					addActionMessage("Oops Error: Not Request For Event Approval this time ");

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

	// before approve by event form manager
	public String beforeApproveByManagerAdd()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				viewEventDetailsById();

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

	// event approving by manager
	public String approveByManagerAdd()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String loggedDetail[] = CUA.getEmpDetailsByUserName("WFPM", userName);

				StringBuilder query = new StringBuilder("");
				query.append(" update event_plan set event_status='" + status + "' , eplan_flag='2'" + " , event_status_comments='" + status_comments + "' , " + " event_status_date='" + DateUtil.getCurrentDateUSFormat() + "' " + " ,manager_id='" + loggedDetail[0] + "' " + " where" + " id='" + id + "'" + " and for_month = '" + date + "' ");

				boolean result = coi.updateTableColomn(connectionSpace, query);

				if (result)
					addActionMessage("Request For Event " + status + " Successfully");
				else
					addActionMessage("Oops Error: Not Request For Event Approval this time ");

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

	// Request Approval for DCR to see by manager
	public String approveDCRRequest()
	{

		// System.out.println(status
		// +"   approveEventRequest                id   >>>"+id);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				StringBuilder query = new StringBuilder("");
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				query.append(" update event_plan set dsr_flag='1' where id <> 0 and for_month LIKE '%" + date + "%' " + " and eplan_owner ='" + loggedDetails[0] + "'");
				boolean result = coi.updateTableColomn(connectionSpace, query);

				if (result)
					addActionMessage("Request For Event DCR Approval Successfully");
				else
					addActionMessage("Oops Error: Not Request For DCR Approval this time ");

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

	// Request Approval for Event Plan to see by manager
	public String approveEventRequest()
	{

		// System.out.println(status
		// +"   approveEventRequest                id   >>>"+id);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				StringBuilder query = new StringBuilder("");
				String loggedDetails[] = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName);
				query.append(" update event_plan set eplan_flag='1' where id <> 0 and for_month LIKE '%" + date + "%' " + " and eplan_owner ='" + loggedDetails[0] + "'");
				boolean result = coi.updateTableColomn(connectionSpace, query);

				if (result)
					addActionMessage("Request For Event Approval Successfully");
				else
					addActionMessage("Oops Error: Not Request For Event Approval this time ");

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

	public String fetchDataParamRoi()
	{
		// System.out.println("id"+id);
		// System.out.println("oper"+oper);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				mapParamRoi = new LinkedHashMap<String, String>();
				if (oper.equalsIgnoreCase("param"))
				{
					String query = "  select ecp.event_cost_name, epp.eplan_pvalue from event_plan_parameters as  epp " + " inner join event_plan as ep on ep.id=epp.eplan_id " + " inner join event_cost_parameter as ecp on ecp.id=epp.eplan_pname " + " where   epp.eplan_id= '" + id + "'";
					// System.out.println(">>> "+ query);
					List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								mapParamRoi.put(object[0].toString(), object[1].toString());
							}
						}
					}

				}
				else if (oper.equalsIgnoreCase("roi"))
				{

					String query = "  select emcp.roi_parameter, eprp.eplan_roi_value from event_plan_roi_parameters as  eprp " + " inner join event_plan as ep on ep.id=eprp.eplan_id " + " inner join event_map_cost_parameter as emcp on emcp.id=eprp.eplan_roi_parameter " + " where   eprp.eplan_id= '" + id + "'";
					// System.out.println(">>> "+ query);
					List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								mapParamRoi.put(object[0].toString(), object[1].toString());
							}
						}
					}
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

	public String viewEventPlanHeader()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
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

	public static String getCurrentMonthByName(String date)
	{
		String month = "NA";
		String months[] =
		{ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		month = months[Integer.parseInt(DateUtil.convertDateToUSFormat(date).split("-")[1]) - 1];
		return month;
	}

	// Date as DD-MM-YYYY
	public String getCurrentYearByName(String date)
	{
		// System.out.println("Date >>>>>>> "+ date);
		String year = "NA";
		String[] myCurrDate = date.split("-");

		int yr1 = Integer.parseInt(myCurrDate[2]);

		if (Integer.parseInt(myCurrDate[1]) >= 4 && Integer.parseInt(myCurrDate[1]) <= 12)
		{
			year = String.valueOf(Integer.parseInt(myCurrDate[2]) + "-" + String.valueOf(Integer.parseInt(myCurrDate[2]) + 1));
		}
		else
		{
			year = String.valueOf(Integer.parseInt(myCurrDate[2]) - 1) + "-" + String.valueOf(Integer.parseInt(myCurrDate[2]));
		}

		return year;
	}

	void createParameterList()
	{
		eventMap = new LinkedHashMap<String, String>();
		currencyMap = new LinkedHashMap<String, String>();
		buddyMap = new LinkedHashMap<String, String>();
		offeringMap = new LinkedHashMap<String, String>();

		// get Event type
		String query = " select id, act_name from activity_type where act_type='Event' order by act_name ";
		if (query != null)
		{
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						eventMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		String[] values = null;
		try
		{

			coi = new CommonConFactory().createInterface();
			String userName2 = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));
			// System.out.println("userName2  "+ userName2);
			// System.out.println(" userName  "+ userName);
			StringBuilder query2 = new StringBuilder();
			query2.append("select contact.id,emp.emp_name,emp.mobile_no,emp.email_id,emp.sub_type_id as deptid," + " dept.contact_subtype_name,emp.id as empid from primary_contact as emp ");
			query2.append(" inner join manage_contact as contact on contact.emp_id=emp.id");
			query2.append(" inner join contact_sub_type as dept on emp.sub_type_id=dept.id ");
			query2.append(" inner join contact_type as gi on dept.contact_type_id=gi.id ");
			query2.append(" inner join user_account as uac on emp.user_account_id=uac.id " + " where contact.module_name='" + "WFPM" + "' and uac.user_name='");
			query2.append(userName2 + "' and contact.for_contact_sub_type_id=dept.id");
			List dataList = coi.executeAllSelectQuery(query2.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				values = new String[7];
				Object[] object = (Object[]) dataList.get(0);
				values[0] = getValueWithNullCheck(object[0]);
				values[1] = getValueWithNullCheck(object[1]);
				/*
				 * values[2] = getValueWithNullCheck(object[2]); values[3] =
				 * getValueWithNullCheck(object[3]); values[4] =
				 * getValueWithNullCheck(object[4]); values[5] =
				 * getValueWithNullCheck(object[5]); values[6] =
				 * getValueWithNullCheck(object[6]);
				 */
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Date event ::: "+date);
		// get Available Budget
		query = " select empb.amount, (empb.amount - ( select sum(eplan_budget) from event_plan where eplan_owner='" + values[0] + "' " + " and for_month like '%" + DateUtil.convertDateToUSFormat(date).substring(0, DateUtil.convertDateToUSFormat(date).lastIndexOf("-")) + "%' ) ) " + " from allocate_budget_employee as empb " + "inner join allocate_budget_month as mon ON mon.id = empb.for_month " + " inner join allocate_budget_year as yr ON yr.id = mon.month_year " + " where emp='" + values[0] + "' and mon.month_for='" + getCurrentMonthByName(date) + "'" + " and yr.year_for='" + getCurrentYearByName(date) + "' ";
		if (query != null)
		{
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						totalBudget = object[1].toString();
					}
					else
					{
						totalBudget = "0000";
					}
				}
			}
		}

		// get Buddy Map
		query = " SELECT cc.id,emp.emp_name FROM rel_mgr_data as rel " + " INNER JOIN manage_contact AS cc ON cc.id=rel.name " + " INNER JOIN primary_contact AS emp ON cc.emp_id=emp.id " + " WHERE  module_name='WFPM' AND work_status!=1 AND emp.status='0'" + " order by emp_name asc";

		if (query != null)
		{
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						buddyMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		query = " select id,subofferingname from offeringlevel3 ";

		if (query != null)
		{
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						offeringMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
	}

	public static String getCurrentMonthByName()
	{
		String month = "NA";
		String months[] =
		{ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		month = months[Integer.parseInt(DateUtil.getCurrentDateUSFormat().split("-")[1]) - 1];

		return month;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String fetchMappedTeam()
	{
		// System.out.println("id "+ id);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				teamMap = new LinkedHashMap<String, String>();
				StringBuilder sb = new StringBuilder("");
				sb.append(" SELECT distinct cc.id, emp.emp_name FROM primary_contact AS emp" + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id " + " Inner join doctor_off_map_data as doc on doc.doctor_name=cc.id " + " WHERE module_name = 'WFPM' AND work_status != 1 AND emp.status = '0' " + "AND doc.offlevel3 in (" + id + ") " + "  order by emp.emp_name ");

				// System.out.println(">>> "+sb.toString());
				if (sb != null)
				{
					List dataList = new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								teamMap.put(object[0].toString(), object[1].toString());
							}
						}
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
		// System.out.println(returnResult);
		return returnResult;
	}

	public String fetchEventParameters()
	{
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		JSONObject jsonObject = new JSONObject();
		StringBuilder sb = new StringBuilder("");

		sb.append(" select event_cost_name,id from event_cost_parameter where event_type='" + eventid + "' and event_base='" + unitType + "'  " + " order by event_cost_name ");

		// System.out.println(">>> "+sb.toString());
		List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				jsonObject = new JSONObject();
				StringBuilder sb2 = new StringBuilder();

				for (int i = 0; i < data.length; i++)
				{
					if (data[i] != null && !data[i].toString().equals(""))
					{
						// System.out.println(i+"  "+ data[i].toString());

						sb2.append(data[i].toString());
						sb2.append(",");

					}
					else
					{
						sb2.append("NA,");
					}
				}

				jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
			}

			/*
			 * jsonObject = new JSONObject(); String appendOthers
			 * ="Other,eplan_other"; jsonObject.put("VALUE",appendOthers);
			 * dataArray.add(jsonObject);
			 */

			jsonObject = new JSONObject();
			String appendOthers = "Total,eplan_ptotal";
			jsonObject.put("VALUE", appendOthers);
			dataArray.add(jsonObject);

			jsonObject = new JSONObject();
			appendOthers = "Comments,eplan_pcomments";
			jsonObject.put("VALUE", appendOthers);
			dataArray.add(jsonObject);

		}

		return SUCCESS;
	}

	public String fetchEventROIParameters()
	{
		// System.out.println(eventid);
		// System.out.println(unitType);
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		JSONObject jsonObject = new JSONObject();
		StringBuilder sb = new StringBuilder("");

		sb.append(" select roi_parameter,id from event_map_cost_parameter where roi_event_type='" + eventid + "' " + " order by roi_parameter ");

		// System.out.println(">>> "+sb.toString());
		List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				jsonObject = new JSONObject();
				StringBuilder sb2 = new StringBuilder();

				for (int i = 0; i < data.length; i++)
				{
					if (data[i] != null && !data[i].toString().equals(""))
					{
						// System.out.println(i+"  "+ data[i].toString());

						sb2.append(data[i].toString());
						sb2.append(",");

					}
					else
					{
						sb2.append("NA,");
					}
				}

				jsonObject.put("VALUE", sb2.substring(0, sb2.lastIndexOf(",")));
			}

			/*
			 * jsonObject = new JSONObject(); String appendOthers
			 * ="Other,eroi_other"; jsonObject.put("VALUE",appendOthers);
			 * dataArray.add(jsonObject);
			 */

			jsonObject = new JSONObject();
			String appendOthers = "Total,eplan_rtotal";
			jsonObject.put("VALUE", appendOthers);
			dataArray.add(jsonObject);

			jsonObject = new JSONObject();
			appendOthers = "Comments,eplan_rcomments";
			jsonObject.put("VALUE", appendOthers);
			dataArray.add(jsonObject);
		}

		return SUCCESS;
	}

	public String beforeAddEventPlan()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				createParameterList();
				setAddPageDataFields();
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

	public void setAddPageDataFields()
	{
		try
		{

			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_event_plan_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_configuration");
			packageFields = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (!gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					packageFields.add(obj);

				}

			}
		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String addEventPlanDetails()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				coi = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_event_plan_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}

					status = coi.createTable22("event_plan", Tablecolumesaaa, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Object[] rel_subtype = eplan_from_buddy.split(",");

					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();

						String paramValue = request.getParameter(Parmname);
						// System.out.println((!(Parmname.equalsIgnoreCase("employee"))));
						if (paramValue != null && Parmname != null && !(Parmname.equalsIgnoreCase("employee")) && !(Parmname.contains("employee")))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);

					// //System.out.println(Arrays.asList(rel_subtype));

					int count = 0;

					for (int i = 0; i < rel_subtype.length; i++)
					{

						if (rel_subtype[i] != null && !rel_subtype[i].toString().trim().equalsIgnoreCase(""))
						{
							InsertDataTable idt1 = new InsertDataTable();
							idt1.setColName("employee");
							idt1.setDataName(checkNull(rel_subtype[i]));
							insertData.add(idt1);

							status = coi.insertIntoTable("event_plan", insertData, connectionSpace);
							count++;
							insertData.remove(idt1);
						}
					}
					insertData.clear();

					if (status)
					{
						addActionMessage("Event is Planned Successfully: " + count);
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			}
			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	String checkNull(Object obj)
	{
		String returnResult = "NA";
		if (obj != null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1"))
		{
			returnResult = obj.toString();
		}
		return returnResult;
	}

	public String viewEventPlanDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				setMasterViewProps();
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

	void setMasterViewProps()
	{

		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		try
		{
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_event_plan_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!(gdp.getColomnName().equalsIgnoreCase("eplan_parameter")))
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String viewEventPlanData()
	{

		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			coi = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from event_plan");
			List dataCount = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
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
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");

				queryEnd.append(" from event_plan as ep ");
				/*
				 * +
				 * " inner JOIN contact_sub_type AS dept ON dept.id=mbe.emp_department "
				 * + " inner JOIN manage_contact AS cc ON cc.id=mbe.employee " +
				 * "inner join primary_contact AS emp on cc.emp_id=emp.id ");
				 */
				List fieldNames = Configuration.getColomnList("mapped_event_plan_configuration", accountID, connectionSpace, "event_plan_configuration");
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("eplan_parameter"))
						{
							it.remove();
						}
					}
				}

				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{

						if (obdata.toString().equalsIgnoreCase("eplan_type"))
						{
							queryTemp.append(" actType.act_name ,");
							queryEnd.append("left join activity_type as actType on actType.id=ep.eplan_type ");
						}
						else if (obdata.toString().equalsIgnoreCase("territory"))
						{
							queryTemp.append(" terr.trr_name ,");
							queryEnd.append("left join territory as terr on terr.id=ep.territory ");
						}
						else if (obdata.toString().equalsIgnoreCase("rel_sub_type"))
						{
							queryTemp.append(" relSub.rel_subtype ,");
							queryEnd.append("left join relationship_sub_type as relSub on relSub.id=ep.rel_sub_type ");
						}
						else if (obdata.toString().equalsIgnoreCase("eplan_owner"))
						{
							queryTemp.append("emp.emp_name as eplan_owner , ");
							queryEnd.append(" INNER JOIN manage_contact AS cc ON cc.emp_id = ep.eplan_owner ");
							queryEnd.append(" INNER JOIN primary_contact AS emp ON cc.emp_id = emp.id ");
						}
						else if (obdata.toString().equalsIgnoreCase("eplan_currency"))
						{
							queryTemp.append(" currType.curr_name ,");
							queryEnd.append("left join currency_type  as currType on currType.id=ep.eplan_currency ");
						}

						/*
						 * hkhk
						 * 
						 * 
						 * select group_concat(emp.emp_name ORDER BY
						 * emp.emp_name SEPARATOR ',') as names FROM
						 * primary_contact AS emp INNER JOIN manage_contact AS
						 * cc ON cc.emp_id = emp.id where cc.id in (12,13,14,15)
						 * ;
						 */
						/*
						 * else
						 * if(obdata.toString().equalsIgnoreCase("eplan_from_buddy"
						 * )){ List buddyData=coi.executeAllSelectQuery("",
						 * connectionSpace);
						 * queryTemp.append(" currType.curr_name ,");
						 * queryEnd.append(
						 * "left join currency_type  as currType on currType.id=ep.eplan_currency "
						 * ); }
						 */
						else
						{
							queryTemp.append("ep." + obdata.toString() + ",");
						}
					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" ep.status='Active' ");

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (date != null && date.length() > 0)
				{
					query.append(" and ep.for_month='" + date + "' ");
				}

				query.append(" order by ep.eplan_title ");
				List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				// System.out.println("query.toString()>>"+query.toString());
				if (data != null)
				{
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
								}
								else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("eplan_from_buddy"))
									{
										StringBuilder bquery = new StringBuilder(" ");
										bquery.append(" select group_concat(emp.emp_name ORDER BY emp.emp_name SEPARATOR ',') as names " + " FROM primary_contact AS emp " + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id where cc.id in (" + obdata[k].toString() + ") ");

										String buddyNames = checkNull(coi.executeAllSelectQuery(bquery.toString(), connectionSpace).get(0));
										// System.out.println(buddyNames
										// +"  *******   "+
										// obdata[k].toString());
										one.put(fieldNames.get(k).toString(), buddyNames);
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("for_month"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("eplan_from_offering"))
									{
										StringBuilder bquery = new StringBuilder(" ");
										bquery.append(" select group_concat(off3.subofferingname ORDER BY off3.subofferingname SEPARATOR ',') from offeringlevel3 as off3 " + " where off3.id in (" + obdata[k].toString() + ") ");
										String buddyNames = checkNull((coi.executeAllSelectQuery(bquery.toString(), connectionSpace).get(0)));
										// System.out.println(buddyNames
										// +" off3 *******   "+
										// obdata[k].toString());
										one.put(fieldNames.get(k).toString(), buddyNames);
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("eplan_team"))
									{
										StringBuilder bquery = new StringBuilder(" ");
										bquery.append(" select group_concat(emp.emp_name ORDER BY emp.emp_name SEPARATOR ',') as names " + " FROM primary_contact AS emp " + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id where cc.id in (" + obdata[k].toString() + ") ");
										String buddyNames = checkNull(coi.executeAllSelectQuery(bquery.toString(), connectionSpace).get(0));
										// System.out.println(buddyNames
										// +" eplan_team  *******   "+
										// obdata[k].toString());
										one.put(fieldNames.get(k).toString(), buddyNames);
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}

								}
							}
						}
						Listhb.add(one);
					}
					setViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					return "success";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String editEventPlanDataGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					coi = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equalsIgnoreCase("userName"))
							{
								wherClause.put(parmName, userName);
							}
							else if (parmName.equalsIgnoreCase("date"))
							{
								wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
							}
							else if (parmName.equalsIgnoreCase("time"))
							{
								wherClause.put(parmName, DateUtil.getCurrentTimeHourMin());
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("id", getId());
					boolean status = coi.updateTableColomn("event_plan", wherClause, condtnBlock, connectionSpace);
					if (status)
					{
						returnResult = SUCCESS;
					}
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					coi = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					for (String H : tempIds)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						wherClause.put("status", "Inactive");
						condtnBlock.put("id", id);
						coi.updateTableColomn("event_plan", wherClause, condtnBlock, connectionSpace);
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method corporate master of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String fillDCRForEvent()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				viewEventDetailsById();
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

	public void viewEventDetailsById()
	{

		StringBuilder query = new StringBuilder("");
		query.append(" select ep.id, actType.act_name ,ep.eplan_title,ep.eplan_address,ep.eplan_from_buddy,ep.eplan_from_offering," + "ep.eplan_team,ep.eplan_current_month,ep.eplan_budget,currType.curr_name,ep.eplan_unit_type,ep.eplan_ptotal," + "ep.eplan_pcomments,emp.emp_name as eplan_owner , relSub.rel_subtype ,ep.schedule_plan,terr.trr_name,ep.for_month,ep.sch_from," + "ep.sch_to,ep.comments,ep.eplan_rtotal,ep.eplan_rcomments,ep.userName,ep.date,ep.time,ep.status  " + "from event_plan as ep " + "left join activity_type as actType on actType.id=ep.eplan_type " + "left join currency_type  as currType on currType.id=ep.eplan_currency " + "left join relationship_sub_type as relSub on relSub.id=ep.rel_sub_type " + "left join territory as terr on terr.id=ep.territory  " + " INNER JOIN manage_contact AS cc ON cc.emp_id = ep.eplan_owner " + "  INNER JOIN primary_contact AS emp ON cc.emp_id = emp.id " + "where  ep.status='Active' and ep.id='" + id + "'  order by ep.eplan_title ");

		// System.out.println("1 >>>>>> "+query.toString());
		List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);

		epp = new EventPlanPojo();

		if (data != null && data.size() > 0)
		{
			Object[] ob = (Object[]) data.get(0);

			epp.setId(checkNull(ob[0]));
			epp.setType(checkNull(ob[1]));
			epp.setTitle(checkNull(ob[2]));
			epp.setAddress(checkNull(ob[3]));
			epp.setFrom_buddy(checkNull(ob[4]));
			epp.setFrom_offering(checkNull(ob[5]));
			epp.setTeam(checkNull(ob[6]));
			epp.setCurrent_month(checkNull(ob[7]));
			epp.setBudget(checkNull(ob[8]));
			epp.setCurrency(checkNull(ob[9]));
			epp.setUnit_type(checkNull(ob[10]));
			epp.setPtotal(checkNull(ob[11]));
			epp.setPcomments(checkNull(ob[12]));
			epp.setOwner(checkNull(ob[13]));
			epp.setRel_sub_type(checkNull(14));
			// epp.set 15:sch .plan
			epp.setTerritory(checkNull(16));
			epp.setFor_month(checkNull(ob[17]));
			epp.setSch_from(checkNull(ob[18]));
			epp.setSch_to(checkNull(ob[19]));
			epp.setComments(checkNull(ob[20]));
			epp.setRtotal(checkNull(ob[21]));
			epp.setRcomments(checkNull(ob[22]));

			// System.out.println(">>>CHKKKKKK>>> "+ epp.getTitle());

			// from buddy find
			if (!(epp.getFrom_buddy().equalsIgnoreCase("NA")))
			{
				query = new StringBuilder(" ");
				query.append(" select group_concat(emp.emp_name ORDER BY emp.emp_name SEPARATOR ',') as names " + " FROM primary_contact AS emp " + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id where cc.id in (" + epp.getFrom_buddy() + ") ");
				epp.setFrom_buddy(checkNull((coi.executeAllSelectQuery(query.toString(), connectionSpace).get(0))));

			}
			else
			{
				epp.setFrom_buddy("NA");
			}

			// from offering find
			if (!(epp.getFrom_offering().equalsIgnoreCase("NA")))
			{
				query = new StringBuilder(" ");
				query.append(" select group_concat(off3.subofferingname ORDER BY off3.subofferingname SEPARATOR ',') from offeringlevel3 as off3 " + " where off3.id in (" + epp.getFrom_offering() + ") ");
				epp.setFrom_offering(checkNull((coi.executeAllSelectQuery(query.toString(), connectionSpace).get(0))));
			}
			else
			{
				epp.setFrom_offering("NA");
			}

			// plan team find
			if (!(epp.getTeam().equalsIgnoreCase("NA")))
			{

				query = new StringBuilder(" ");
				query.append("  select group_concat(emp.emp_name ORDER BY emp.emp_name SEPARATOR ',') as names " + " FROM primary_contact AS emp " + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id where cc.id in (" + epp.getTeam() + ")");
				epp.setTeam(checkNull((coi.executeAllSelectQuery(query.toString(), connectionSpace).get(0))));

			}
			else
			{
				epp.setTeam("NA");
			}

			// Parameter and ROI List preparing
			List<Object> epList = new ArrayList<Object>();

			query = new StringBuilder(" ");

			query.append("  select epp.id,ecp.event_cost_name, epp.eplan_pvalue from event_plan_parameters as  epp " + " inner join event_plan as ep on ep.id=epp.eplan_id " + " inner join event_cost_parameter as ecp on ecp.id=epp.eplan_pname " + " where   epp.eplan_id= '" + id + "'");

			// System.out.println("2 >>> "+ query);
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);

			if (data != null && data.size() > 0)
			{
				// System.out.println("?????ss "+ data.size());
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Map mapParam = new LinkedHashMap<String, String>();
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						mapParam.put("id", object[0].toString());
						mapParam.put("name", object[1].toString());
						mapParam.put("value", object[2].toString());
						epList.add(mapParam);
					}
				}
				epp.setEparamList(epList);
				// System.out.println("Size epp.1  "+
				// epp.getEparamList().size());
			}

			query = new StringBuilder(" ");
			epList = new ArrayList<Object>();
			query.append("  select eprp.id,emcp.roi_parameter, eprp.eplan_roi_value from event_plan_roi_parameters as  eprp " + " inner join event_plan as ep on ep.id=eprp.eplan_id " + " inner join event_map_cost_parameter as emcp on emcp.id=eprp.eplan_roi_parameter " + " where   eprp.eplan_id= '" + id + "' ");
			// System.out.println( " 3 >>> "+ query);

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Map mapRoi = new LinkedHashMap<String, String>();
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						mapRoi.put("id", object[0].toString());
						mapRoi.put("name", object[1].toString());
						mapRoi.put("value", object[2].toString());
						epList.add(mapRoi);
					}
				}
				epp.setEroiList(epList);
			}

			// System.out.println("epar :::::   "+epp.getEparamList().size() );
			// System.out.println( "e roi ::    "+ epp.getEroiList() );
		}

	}

	public String addEventDCR()
	{

		// System.out.println("addEventDCR  idddddd"+ id);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// Insert Data Lists
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertEParamData = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertERoiData = new ArrayList<InsertDataTable>();

				InsertDataTable ob = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				boolean eparameterBool = false;
				boolean eroiBool = false;
				boolean status = false;

				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						// System.out.println("CHKKKK   Parmname:"+ Parmname+
						// "\t" + "paramValue:  "+ paramValue);
						if (!(Parmname.equalsIgnoreCase("rel_type")) && !(Parmname.equalsIgnoreCase("activity_for")) && !(Parmname.equalsIgnoreCase("activity_type")) && !(Parmname.contains("eroi")) && !(Parmname.contains("eparam")) && !(Parmname.equalsIgnoreCase("eplan_from_buddy")) && !(Parmname.equalsIgnoreCase("eplan_from_offering")) && !(Parmname.equalsIgnoreCase("eplan_team")) && !(Parmname.equalsIgnoreCase("SubRel")) && !(Parmname.equalsIgnoreCase("rel_sub_type")))
						{
							if (Parmname.equalsIgnoreCase("eplan_id"))
							{
								// id=paramValue;
								/*
								 * ob=new InsertDataTable();
								 * ob.setColName(Parmname);
								 * ob.setDataName(paramValue);
								 * insertData.add(ob);
								 */
							}

						}
						else if (Parmname.contains("eparam"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertEParamData.add(ob);
						}
						else if (Parmname.contains("eroi"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertERoiData.add(ob);
						}
					}
				}

				// System.out.println("insertData:  "+insertData.size());
				// System.out.println("insertEParamData:  "+insertEParamData.size());
				// System.out.println("insertERoiData:  "+insertERoiData.size());

				TableColumes ob1 = null;

				// create Parameters
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_event_plan_parameters_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_dcr_parameters_configuration");

				if (statusColName != null)
				{

					ob1 = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (true)
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							if (gdp.getColomnName().equalsIgnoreCase("status"))
							{
								ob1.setConstraint("default 'Active'");
							}
							else
							{
								ob1.setConstraint("default NULL");
							}
							Tablecolumesaaa.add(ob1);
						}
					}
					status = coi.createTable22("event_plan_dcr_parameters", Tablecolumesaaa, connectionSpace);
					if (status || true)
					{

					}

					// create ROI table
					statusColName = Configuration.getConfigurationData("mapped_event_plan_parameters_configuration", accountID, connectionSpace, "", 0, "table_name", "event_plan_dcr_roi_configuration");

					if (statusColName != null)
					{
						status = false;
						ob1 = null;
						Tablecolumesaaa = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : statusColName)
						{
							if (true)
							{
								ob1 = new TableColumes();
								ob1.setColumnname(gdp.getColomnName());
								ob1.setDatatype("varchar(255)");
								if (gdp.getColomnName().equalsIgnoreCase("status"))
								{
									ob1.setConstraint("default 'Active'");
								}
								else
								{
									ob1.setConstraint("default NULL");
								}
								Tablecolumesaaa.add(ob1);
							}
						}
						status = coi.createTable22("event_plan_dcr_roi", Tablecolumesaaa, connectionSpace);
					}
					if (status || true)
					{

					}

					// inserting DCR parameters
					if (insertEParamData.size() > 0)
					{
						insertData.clear();

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);

						InsertDataTable obt = new InsertDataTable();
						obt.setColName("eplan_id");
						obt.setDataName(id);
						insertData.add(obt);

						for (Iterator iter = insertEParamData.iterator(); iter.hasNext();)
						{
							InsertDataTable idt = (InsertDataTable) iter.next();
							if (idt != null && idt.getDataName().toString().trim().length() > 0)
							{

								InsertDataTable obt1 = new InsertDataTable();
								obt1.setColName("eplan_pname");
								obt1.setDataName(idt.getColName().split("eparam")[1]);
								insertData.add(obt1);

								InsertDataTable obt2 = new InsertDataTable();
								obt2.setColName("eplan_pvalue");
								obt2.setDataName(idt.getDataName());
								insertData.add(obt2);
								eparameterBool = coi.insertIntoTable("event_plan_dcr_parameters", insertData, connectionSpace);
								insertData.remove(obt1);
								insertData.remove(obt2);
								if (eparameterBool)
								{
									// System.out
									// .println("eplan parameter added!!");
								}
								else
								{
									// System.out
									// .println("eplan parameter added!!");
								}
							}
						}
					}

					// inserting Event ROI parameters
					if (insertERoiData.size() > 0)
					{
						insertData.clear();

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);

						InsertDataTable obt = new InsertDataTable();
						obt.setColName("eplan_id");
						obt.setDataName(id);
						insertData.add(obt);

						for (Iterator iter = insertERoiData.iterator(); iter.hasNext();)
						{
							InsertDataTable idt = (InsertDataTable) iter.next();
							if (idt != null && idt.getDataName().toString().trim().length() > 0)
							{

								InsertDataTable obt1 = new InsertDataTable();
								obt1.setColName("eplan_roi_parameter");
								obt1.setDataName(idt.getColName().split("eroi")[1]);
								insertData.add(obt1);

								InsertDataTable obt2 = new InsertDataTable();
								obt2.setColName("eplan_roi_value");
								obt2.setDataName(idt.getDataName());
								insertData.add(obt2);

								eroiBool = coi.insertIntoTable("event_plan_dcr_roi", insertData, connectionSpace);
								insertData.remove(obt1);
								insertData.remove(obt2);
								if (eroiBool)
								{
									// System.out
									// .println("eplan roi added!!");
								}
								else
								{
									// System.out
									// .println("eplan parameter added!!");
								}
							}
						}
					}

				}
				// System.out.println(eroiBool + " ----- "+ eparameterBool);
				if (eroiBool || eparameterBool)
				{
					coi.updateTableColomn(connectionSpace, new StringBuilder("update event_plan set dsr_status='Yes' ,dsr_flag='0' where id='" + id + "' "));
					addActionMessage("DCR Submitted Successfully");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
					return SUCCESS;
				}
			}

			catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), exp);
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String filledDCRValues()
	{

		// System.out.println("GHJGGGJGKGGGGGGGGGKGKKKGKGGJ                id   >>>"+id);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;

				fetchDCRValues();
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

	public void fetchDCRValues()
	{

		StringBuilder query = new StringBuilder("");
		query.append(" select ep.id, actType.act_name ,ep.eplan_title,ep.eplan_address,ep.eplan_from_buddy,ep.eplan_from_offering," + "ep.eplan_team,ep.eplan_current_month,ep.eplan_budget,currType.curr_name,ep.eplan_unit_type,ep.eplan_ptotal," + "ep.eplan_pcomments,emp.emp_name as eplan_owner , relSub.rel_subtype ,ep.schedule_plan,terr.trr_name,ep.for_month,ep.sch_from," + "ep.sch_to,ep.comments,ep.eplan_rtotal,ep.eplan_rcomments,ep.userName,ep.date,ep.time,ep.status  " + "from event_plan as ep " + "left join activity_type as actType on actType.id=ep.eplan_type " + "left join currency_type  as currType on currType.id=ep.eplan_currency " + "left join relationship_sub_type as relSub on relSub.id=ep.rel_sub_type " + "left join territory as terr on terr.id=ep.territory  " + " INNER JOIN manage_contact AS cc ON cc.emp_id = ep.eplan_owner " + "  INNER JOIN primary_contact AS emp ON cc.emp_id = emp.id " + "where  ep.status='Active' and ep.id='" + id + "'  order by ep.eplan_title ");

		// System.out.println("1 >>>>>> "+query.toString());
		List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);

		epp = new EventPlanPojo();

		if (data != null && data.size() > 0)
		{
			Object[] ob = (Object[]) data.get(0);

			epp.setId(checkNull(ob[0]));
			epp.setType(checkNull(ob[1]));
			epp.setTitle(checkNull(ob[2]));
			epp.setAddress(checkNull(ob[3]));
			epp.setFrom_buddy(checkNull(ob[4]));
			epp.setFrom_offering(checkNull(ob[5]));
			epp.setTeam(checkNull(ob[6]));
			epp.setCurrent_month(checkNull(ob[7]));
			epp.setBudget(checkNull(ob[8]));
			epp.setCurrency(checkNull(ob[9]));
			epp.setUnit_type(checkNull(ob[10]));
			epp.setPtotal(checkNull(ob[11]));
			epp.setPcomments(checkNull(ob[12]));
			epp.setOwner(checkNull(ob[13]));
			epp.setRel_sub_type(checkNull(14));
			// epp.set 15:sch .plan
			epp.setTerritory(checkNull(16));
			epp.setFor_month(checkNull(ob[17]));
			epp.setSch_from(checkNull(ob[18]));
			epp.setSch_to(checkNull(ob[19]));
			epp.setComments(checkNull(ob[20]));
			epp.setRtotal(checkNull(ob[21]));
			epp.setRcomments(checkNull(ob[22]));

			// System.out.println(">>>CHKKKKKK>>> "+ epp.getTitle());

			// from buddy find
			if (!(epp.getFrom_buddy().equalsIgnoreCase("NA")))
			{
				query = new StringBuilder(" ");
				query.append(" select group_concat(emp.emp_name ORDER BY emp.emp_name SEPARATOR ',') as names " + " FROM primary_contact AS emp " + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id where cc.id in (" + epp.getFrom_buddy() + ") ");
				epp.setFrom_buddy(checkNull((coi.executeAllSelectQuery(query.toString(), connectionSpace).get(0))));

			}
			else
			{
				epp.setFrom_buddy("NA");
			}

			// from offering find
			if (!(epp.getFrom_offering().equalsIgnoreCase("NA")))
			{
				query = new StringBuilder(" ");
				query.append(" select group_concat(off3.subofferingname ORDER BY off3.subofferingname SEPARATOR ',') from offeringlevel3 as off3 " + " where off3.id in (" + epp.getFrom_offering() + ") ");
				epp.setFrom_offering(checkNull((coi.executeAllSelectQuery(query.toString(), connectionSpace).get(0))));
			}
			else
			{
				epp.setFrom_offering("NA");
			}

			// plan team find
			if (!(epp.getTeam().equalsIgnoreCase("NA")))
			{

				query = new StringBuilder(" ");
				query.append("  select group_concat(emp.emp_name ORDER BY emp.emp_name SEPARATOR ',') as names " + " FROM primary_contact AS emp " + " INNER JOIN manage_contact AS cc ON cc.emp_id = emp.id where cc.id in (" + epp.getTeam() + ")");
				epp.setTeam(checkNull((coi.executeAllSelectQuery(query.toString(), connectionSpace).get(0))));

			}
			else
			{
				epp.setTeam("NA");
			}

			// Parameter and ROI List preparing
			List<Object> epList = new ArrayList<Object>();

			query = new StringBuilder(" ");

			query.append("  select ecp.id,ecp.event_cost_name, epp.eplan_pvalue,epdp.eplan_pvalue as final from event_plan_parameters as  epp " + " inner join event_plan as ep on ep.id=epp.eplan_id " + " inner join event_cost_parameter as ecp on ecp.id=epp.eplan_pname " + " inner join event_plan_dcr_parameters as epdp on epdp.eplan_pname=epp.id  " + " where   epp.eplan_id= '" + id + "'");

			// System.out.println("2 >>> "+ query);
			data = coi.executeAllSelectQuery(query.toString(), connectionSpace);

			if (data != null && data.size() > 0)
			{
				// System.out.println("?????ss "+ data.size());
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Map mapParam = new LinkedHashMap<String, String>();
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						mapParam.put("id", object[0].toString());
						mapParam.put("name", object[1].toString());
						mapParam.put("value", object[2].toString());
						mapParam.put("final", object[3].toString());
						epList.add(mapParam);
					}
				}
				epp.setEparamList(epList);
				// System.out.println("Size epp.1  "+
				// epp.getEparamList().size());
			}

			query = new StringBuilder(" ");
			epList = new ArrayList<Object>();
			query.append("  select emcp.id,emcp.roi_parameter, eprp.eplan_roi_value,epdr.eplan_roi_value as final" + " from event_plan_roi_parameters as  eprp " + " inner join event_plan as ep on ep.id=eprp.eplan_id " + " inner join event_map_cost_parameter as emcp on emcp.id=eprp.eplan_roi_parameter " + "  inner join event_plan_dcr_roi as epdr on epdr.eplan_roi_parameter=eprp.id " + "  " + " where   eprp.eplan_id= '" + id + "' ");
			// System.out.println( " 3 >>> "+ query);

			data = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Map mapRoi = new LinkedHashMap<String, String>();
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						mapRoi.put("id", object[0].toString());
						mapRoi.put("name", object[1].toString());
						mapRoi.put("value", object[2].toString());
						mapRoi.put("final", object[3].toString());
						epList.add(mapRoi);
					}
				}
				epp.setEroiList(epList);
			}

			// System.out.println("epar :::::   "+epp.getEparamList().size() );
			// System.out.println( "e roi ::    "+ epp.getEroiList() );
		}

	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getViewList()
	{
		return viewList;
	}

	public void setViewList(List<Object> viewList)
	{
		this.viewList = viewList;
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<ConfigurationUtilBean> getPackageFields()
	{
		return packageFields;
	}

	public void setPackageFields(List<ConfigurationUtilBean> packageFields)
	{
		this.packageFields = packageFields;
	}

	public Map<String, String> getEventMap()
	{
		return eventMap;
	}

	public void setEventMap(Map<String, String> eventMap)
	{
		this.eventMap = eventMap;
	}

	public Map<String, String> getBuddyMap()
	{
		return buddyMap;
	}

	public void setBuddyMap(Map<String, String> buddyMap)
	{
		this.buddyMap = buddyMap;
	}

	public Map<String, String> getOfferingMap()
	{
		return offeringMap;
	}

	public void setOfferingMap(Map<String, String> offeringMap)
	{
		this.offeringMap = offeringMap;
	}

	public Map<String, String> getCurrencyMap()
	{
		return currencyMap;
	}

	public void setCurrencyMap(Map<String, String> currencyMap)
	{
		this.currencyMap = currencyMap;
	}

	public String getEplan_from_buddy()
	{
		return eplan_from_buddy;
	}

	public void setEplan_from_buddy(String eplan_from_buddy)
	{
		this.eplan_from_buddy = eplan_from_buddy;
	}

	public String getEplan_from_offering()
	{
		return eplan_from_offering;
	}

	public void setEplan_from_offering(String eplan_from_offering)
	{
		this.eplan_from_offering = eplan_from_offering;
	}

	public String getEplan_team()
	{
		return eplan_team;
	}

	public void setEplan_team(String eplan_team)
	{
		this.eplan_team = eplan_team;
	}

	public String getEventid()
	{
		return eventid;
	}

	public void setEventid(String eventid)
	{
		this.eventid = eventid;
	}

	public String getUnitType()
	{
		return unitType;
	}

	public void setUnitType(String unitType)
	{
		this.unitType = unitType;
	}

	public JSONArray getDataArray()
	{
		return dataArray;
	}

	public void setDataArray(JSONArray dataArray)
	{
		this.dataArray = dataArray;
	}

	public String getTotalBudget()
	{
		return totalBudget;
	}

	public void setTotalBudget(String totalBudget)
	{
		this.totalBudget = totalBudget;
	}

	public Map<String, String> getTeamMap()
	{
		return teamMap;
	}

	public void setTeamMap(Map<String, String> teamMap)
	{
		this.teamMap = teamMap;
	}

	public Map<String, String> getMapParamRoi()
	{
		return mapParamRoi;
	}

	public void setMapParamRoi(Map<String, String> mapParamRoi)
	{
		this.mapParamRoi = mapParamRoi;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public EventPlanPojo getEpp()
	{
		return epp;
	}

	public void setEpp(EventPlanPojo epp)
	{
		this.epp = epp;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStatus_comments()
	{
		return status_comments;
	}

	public void setStatus_comments(String status_comments)
	{
		this.status_comments = status_comments;
	}

}