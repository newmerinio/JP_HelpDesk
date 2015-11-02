package com.Over2Cloud.ctrl.wfpm.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.report.mail.WFPMMailingReportHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DashboardAction extends ActionSupport
{
	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	String											empId						= session.get("empIdofuser").toString().split("-")[1];
	// Anoop, 7-10-2013
	CommonOperInterface					coi							= new CommonConFactory().createInterface();
	HashMap<String, Integer>		leadMap					= null;
	HashMap<String, Integer>		clientMap				= null;
	HashMap<String, Integer>		associateMap		= null;

	HashMap<String, Integer>		offeringMap			= null;
	HashMap<String, Integer>		employeeMap			= null;
	HashMap<String, Integer>		eventMap				= null;
	private String							legendShow			= "false";
	private String							width						= "225";
	private String							height					= "225";
	private String							currDate				= null;
	private JSONArray						jsonArray				= null;
	private int									id;
	private String							temp;
	private JSONObject					jsonObject			= null;
	private ActivityType				activityType		= null;
	private int									offeringId;
	private String searchondate;
	private Map<String, String>	lostStatusMAP		= null;
	private Map<String, String>	empMap;

	public String beforeClientPie()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getClientPieChartData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String beforeLeadPie()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getLeadPieChartData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String beforeAssociatePie()
	{

		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println(legendShow + "===" + width + "===" + height);
				getAssociatePieChartData();
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	private void getActivityPieChartData() throws Exception
	{
		ClientHelper ch = new ClientHelper();
		AssociateHelper ah = new AssociateHelper();
		String offeringLevel = session.get("offeringLevel").toString();

		eventMap = new HashMap<String, Integer>();
		eventMap.put("Missed", 0);
		eventMap.put("Today", 0);
		eventMap.put("Tomorrow", 0);
		eventMap.put("Seven Days", 0);

		// client
		int missedActivityCount = ch.getMissedActivityCount(userName, connectionSpace);
		// associate
		int missedActivityCountAssociate = ah.getMissedActivityCount(userName, connectionSpace);
		eventMap.put("Missed", missedActivityCount + missedActivityCountAssociate);

		// client
		int todayActivity = ch.getTodaysActivityCount(userName, connectionSpace, offeringLevel);
		// associate
		int todayActivityAssociate = ah.getTodaysActivityCount(userName, connectionSpace, offeringLevel);
		eventMap.put("Today", todayActivity + todayActivityAssociate);

		// client
		int tomorrowActivity = ch.getTomorrowsActivityCount(userName, connectionSpace, offeringLevel);
		// associate
		int tomorrowActivityAssociate = ah.getTomorrowsActivityCount(userName, connectionSpace, offeringLevel);
		eventMap.put("Tomorrow", tomorrowActivity + tomorrowActivityAssociate);

		// client
		int nextSevenDaysActivity = ch.getNextSevenDaysActivityCount(userName, connectionSpace, offeringLevel);
		// associate
		int nextSevenDaysActivityAssociate = ah.getNextSevenDaysActivityCount(userName, connectionSpace, offeringLevel);
		eventMap.put("Seven Days", nextSevenDaysActivity + nextSevenDaysActivityAssociate);

	}

	private void getAssociatePieChartData() throws Exception
	{
		String q3 = "select com.isConverted, count(cbd.id) from associate_basic_data as cbd, "
				+ "associate_offering_mapping as com where com.associateName = cbd.id " + "and cbd.userName = '" + userName + "' group by com.isConverted";
		List associateList = coi.executeAllSelectQuery(q3, connectionSpace);
		if (associateList != null)
		{
			associateMap = new HashMap<String, Integer>();
			associateMap.put("Prospective", 0);
			associateMap.put("Existing", 0);
			associateMap.put("Lost", 0);

			for (Object ob : associateList)
			{
				Object[] data = (Object[]) ob;
				if (data[0].toString().equalsIgnoreCase("0"))
				{
					associateMap.put("Prospective", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("1"))
				{
					associateMap.put("Existing", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("2"))
				{
					associateMap.put("Lost", Integer.parseInt(data[1].toString()));
				}
			}
		}

	}

	private void getClientPieChartData() throws Exception
	{
		String q2 = "select com.isConverted, count(cbd.id) from client_basic_data as cbd, " + "client_offering_mapping as com where com.clientName = cbd.id "
				+ "and cbd.userName = '" + userName + "' group by com.isConverted";
		List clientList = coi.executeAllSelectQuery(q2, connectionSpace);
		if (clientList != null)
		{
			clientMap = new HashMap<String, Integer>();
			clientMap.put("Prospective", 0);
			clientMap.put("Existing", 0);
			clientMap.put("Lost", 0);

			for (Object ob : clientList)
			{
				Object[] data = (Object[]) ob;
				if (data[0].toString().equalsIgnoreCase("0"))
				{
					clientMap.put("Prospective", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("1"))
				{
					clientMap.put("Existing", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("2"))
				{
					clientMap.put("Lost", Integer.parseInt(data[1].toString()));
				}
			}
		}
	}

	private void getLeadPieChartData() throws Exception
	{
		String q1 = "select status, count(*)  from leadgeneration " + "where userName = '" + userName + "' group by status";
		List leadList = coi.executeAllSelectQuery(q1, connectionSpace);
		if (leadList != null)
		{
			leadMap = new HashMap<String, Integer>();
			leadMap.put("New", 0);
			leadMap.put("Pros. Client", 0);
			leadMap.put("Pros. Associate", 0);
			leadMap.put("Snoozed", 0);
			leadMap.put("Lost", 0);

			for (Object ob : leadList)
			{
				Object[] data = (Object[]) ob;
				if (data[0].toString().equalsIgnoreCase("0"))
				{
					leadMap.put("New", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("1"))
				{
					leadMap.put("Pros. Client", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("2"))
				{
					leadMap.put("Pros. Associate", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("3"))
				{
					leadMap.put("Snoozed", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("4"))
				{
					leadMap.put("Lost", Integer.parseInt(data[1].toString()));
				}
			}
		}
	}

	public String beforeCommonDashboard()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{

			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String fetchDashboardFullHistory()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{

			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String beforeDashboardTakeActon()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{

			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String fetchDashboardActivityList()
	{
		String returnValue = LOGIN;
		String month;
		String cIdUserType = new CommonHelper().getEmpTypeByUserName(CommonHelper.moduleName, userName, connectionSpace);
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("11111111111111111111111111111111111111111111");
				if("ActivityByDate".equalsIgnoreCase(searchondate))
				{
					month = DateUtil.convertDateToUSFormat(currDate);
				}else{
					month = DateUtil.getDateAfterDays(Integer.parseInt(currDate.trim()));
				}
				//String month = DateUtil.getDateAfterDays(Integer.parseInt(currDate.trim()));
				DashboardHelper dh = new DashboardHelper();
				jsonArray = new JSONArray();
				CommonHelper ch = new CommonHelper();
				String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
				
				// Actvity list for client
				List dashboardActivityListClient = dh.fetchCommonDashboardActivitiesForClient(connectionSpace, month, cIdAll, cIdUserType);
				if (dashboardActivityListClient != null && dashboardActivityListClient.size() > 0)
				{
					// System.out.println("dashboardActivityList.size():" +
					// dashboardActivityListClient.size());
					dh.buildJSONArrayForActivity(jsonArray, dashboardActivityListClient, ActivityType.client);
				}

				// Activity list for associate
				 List dashboardActivityListAssociate = dh.fetchCommonDashboardActivitiesForAssociate(connectionSpace, month, cIdAll,cIdUserType);
				if (dashboardActivityListAssociate != null && dashboardActivityListAssociate.size() > 0)
				{
					// System.out.println("dashboardActivityList.size():" +
					// dashboardActivityListAssociate.size());
					dh.buildJSONArrayForActivity(jsonArray, dashboardActivityListAssociate, ActivityType.associate);
				}

				// System.out.println("jsonArray:" + jsonArray.size());
				currDate = DateUtil.convertDateToIndianFormat(month);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchActivityHistory()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("3333333333333333333333");
				DashboardHelper dh = new DashboardHelper();
				jsonArray = new JSONArray();
				// For client
				List list = dh.fetchRecentActivitiesForClient(connectionSpace, id);
				if (list != null && list.size() > 0)
				{
					// System.out.println("list.size():" + list.size());
					dh.buildJSONArrayForActivity(jsonArray, list, ActivityType.activityHistory);
				}

				// For associate
				list = dh.fetchRecentActivitiesForAssociate(connectionSpace, id);
				if (list != null && list.size() > 0)
				{
					// System.out.println("list.size():" + list.size());
					dh.buildJSONArrayForActivity(jsonArray, list, ActivityType.activityHistory);
				}

				// System.out.println("jsonArray:" + jsonArray.size());

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchContactDetails()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				DashboardHelper dh = new DashboardHelper();
				jsonObject = dh.fetchContantDetailsByType(connectionSpace, id, activityType);
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchOrgnizationDetails()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				DashboardHelper dh = new DashboardHelper();
				jsonObject = dh.fetchOrgnizationDetailsByType(connectionSpace, id, activityType);
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforeFullHistory()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("5555555555555555555");
				DashboardHelper dh = new DashboardHelper();
				jsonArray = dh.fetchOfferingMappedWithOrganization(connectionSpace, id, activityType);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchAllMappedOffering()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("7777777777777777777777777");

				DashboardHelper dh = new DashboardHelper();
				jsonArray = dh.fetchOfferingMappedWithOrganization(connectionSpace, id, activityType);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchContactPerson()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("66666666666666666666666666");

				DashboardHelper dh = new DashboardHelper();
				jsonArray = dh.fetchContactPersonByOfferingAndOrganization(connectionSpace, id, activityType, offeringId);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String fetchActivityByOfferingAndContact()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("7777777777777777777777777777");

				DashboardHelper dh = new DashboardHelper();
				jsonArray = dh.fetchActivityDetailsByOfferingAndContact(connectionSpace, id, offeringId, activityType);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforeLostTakeActon()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				// System.out.println("7777777777777777777777777777");

				ClientHelper ch = new ClientHelper();
				lostStatusMAP = ch.fetchLostStatus(connectionSpace);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String beforeReassignTakeActon()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				EmployeeHelper<Map<String, String>> ch = new EmployeeHelper<Map<String, String>>();
				empMap = ch.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public HashMap<String, Integer> getLeadMap()
	{
		return leadMap;
	}

	public void setLeadMap(HashMap<String, Integer> leadMap)
	{
		this.leadMap = leadMap;
	}

	public HashMap<String, Integer> getClientMap()
	{
		return clientMap;
	}

	public void setClientMap(HashMap<String, Integer> clientMap)
	{
		this.clientMap = clientMap;
	}

	public HashMap<String, Integer> getOfferingMap()
	{
		return offeringMap;
	}

	public void setOfferingMap(HashMap<String, Integer> offeringMap)
	{
		this.offeringMap = offeringMap;
	}

	public HashMap<String, Integer> getEmployeeMap()
	{
		return employeeMap;
	}

	public void setEmployeeMap(HashMap<String, Integer> employeeMap)
	{
		this.employeeMap = employeeMap;
	}

	public HashMap<String, Integer> getEventMap()
	{
		return eventMap;
	}

	public void setEventMap(HashMap<String, Integer> eventMap)
	{
		this.eventMap = eventMap;
	}

	public String getLegendShow()
	{
		return legendShow;
	}

	public void setLegendShow(String legendShow)
	{
		this.legendShow = legendShow;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getCurrDate()
	{
		return currDate;
	}

	public void setCurrDate(String currDate)
	{
		this.currDate = currDate;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTemp()
	{
		return temp;
	}

	public void setTemp(String temp)
	{
		this.temp = temp;
		if (temp.trim().equals("0")) this.activityType = ActivityType.client;
		else if (temp.trim().equals("1")) this.activityType = ActivityType.associate;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public ActivityType getActivityType()
	{
		return activityType;
	}

	public void setActivityType(ActivityType activityType)
	{
		this.activityType = activityType;
	}

	public int getOfferingId()
	{
		return offeringId;
	}

	public void setOfferingId(int offeringId)
	{
		this.offeringId = offeringId;
	}

	public Map<String, String> getLostStatusMAP()
	{
		return lostStatusMAP;
	}

	public void setLostStatusMAP(Map<String, String> lostStatusMAP)
	{
		this.lostStatusMAP = lostStatusMAP;
	}

	public HashMap<String, Integer> getAssociateMap()
	{
		return associateMap;
	}

	public void setAssociateMap(HashMap<String, Integer> associateMap)
	{
		this.associateMap = associateMap;
	}

	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}

	public String getSearchondate() {
		return searchondate;
	}

	public void setSearchondate(String searchondate) {
		this.searchondate = searchondate;
	}

}
