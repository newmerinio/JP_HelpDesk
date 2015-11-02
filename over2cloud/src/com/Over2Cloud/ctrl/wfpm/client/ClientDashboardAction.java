package com.Over2Cloud.ctrl.wfpm.client;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.template.SMSTemplateBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ClientDashboardAction extends SessionProviderClass
{
	// Anoop, 7-10-2013
	HashMap<String, Integer> leadMap = null;
	HashMap<String, Integer> clientMap = null;
	HashMap<String, Integer> associateMap = null;
	HashMap<String, Integer> offeringMap = null;
	HashMap<String, Integer> employeeMap = null;
	HashMap<String, Integer> eventMap = null;
	String tableName = "", colName = "";
	private String offeringLevel = session.get("offeringLevel").toString();
	private String legendShow = "false";
	private String width = "200";
	private String height = "200";
	private HashMap<String, Integer> activity = null;
	private int missedActivity;
	private HashMap<String, Integer> statusMap = null;
	private HashMap<String, Integer> sourceMap = null;
	private HashMap<String, Integer> offWrtClientMap = null;
	private HashMap<String, Integer>clientActivityMap= null;
	
	
	private ArrayList<ArrayList<String>> birthdayList = null;
	private ArrayList<ArrayList<String>> anniversaryList = null;
	private String currentDate = DateUtil.getCurrentDateIndianFormat();
	private int todayActivity;
	private int nextSevenDaysActivity;
	private int tomorrowActivity;
	private ArrayList<ArrayList<String>> clientStatusAllList = null;
	private ArrayList<ArrayList<String>> clientSourceAllList = null;
	private List<ClientSupportBean> clientRatingWiseData = null;
	private List<ClientSupportBean> clientRatingWiseDataSource = null;
	private List<ClientSupportBean> clientActivityWiseData = null;
	CommonHelper ch = new CommonHelper();
	String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
	private List<ClientSupportBean> ratingfDataList;
	private String clientType;
	private String starRating;
	private int totalRat1=0,totalRat2=0,totalRat3=0,totalRat4=0,totalRat5=0;
	private String activityType;
	private JSONArray jsonArray;
	private JSONArray jsonArray1;
	private JSONArray jsonArray3;
	
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
	// Grid colomn view
	private String oper;
	private String id;
	
	
	public String beforeClientPie()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			
			try
			{    //method for table
				 clientRatingWiseData = new ClientDashboardHelper().getClientTypeDataRatingWise(cIdAll,connectionSpace);
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
	//for Source Bar graph
	
	
	public String beforeClientPieSource()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			
			try
			{    //method for table
				 setClientRatingWiseDataSource(new ClientDashboardHelper().getClientTypeDataRatingWise(cIdAll,connectionSpace));
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
	// method for table view.
	public String beforeClientTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			
			try
			{    //method for table
				 clientRatingWiseData = new ClientDashboardHelper().getClientTypeDataRatingWise(cIdAll,connectionSpace);
				 for (ClientSupportBean listdata : clientRatingWiseData) {
					//System.out.println("listdata "+listdata.getTotalRat1());
					totalRat1 = totalRat1 + listdata.getTotalRat1();
					//System.out.println("listdata "+listdata.getTotalRat2());
					totalRat2 = totalRat2 + listdata.getTotalRat2();
					//System.out.println("listdata "+listdata.getTotalRat3());
					totalRat3 = totalRat3 + listdata.getTotalRat3();
					//System.out.println("listdata "+listdata.getTotalRat4());
					totalRat4 = totalRat4 + listdata.getTotalRat4();
					//System.out.println("listdata "+listdata.getTotalRat5());
					totalRat5 = totalRat5 + listdata.getTotalRat5();
				}
				//getClientPieChartData();
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

	private void getClientPieChartData() throws Exception
	{
		//System.out.println(legendShow+""+width+""+height);
		JSONObject jsonObject = new JSONObject();
		jsonArray = new JSONArray();
		String q2 = "select com.isConverted, count(distinct(cbd.id)) from client_basic_data as cbd, "
				+ "client_offering_mapping as com where com.clientName = cbd.id "
				+ "and cbd.userName IN ("
				+ cIdAll
				+ ") group by com.isConverted";
		List clientList = coi.executeAllSelectQuery(q2, connectionSpace);
		
		int temp1 = 0;
		Iterator iterator = clientList.iterator();
		while(iterator.hasNext()) {
			temp1++;
		    iterator.next();
		}
		//System.out.println("temp1 "+temp1);
		if (clientList != null)
		{
			clientMap = new HashMap<String, Integer>();
			clientMap.put("Prospective", 0);
			clientMap.put("Existing", 0);
			clientMap.put("Lost", 0);

			for (Object ob : clientList)
			{
				Object[] data = (Object[]) ob;
				String sts = "";
				if (data[0].toString().equalsIgnoreCase("0"))
				{
					sts = "Prospective";
					
				}
				
				else if (data[0].toString().equalsIgnoreCase("1"))
				{
					sts = "Existing";
					
				}
				
				else if (data[0].toString().equalsIgnoreCase("2"))
				{
					sts = "Lost";
					
				}
				jsonObject.put("source", sts);
				jsonObject.put("percent", Integer.parseInt(data[1].toString()));
				jsonObject.put("issue", temp1);
				jsonArray.add(jsonObject);
				
				
				
			}
		}
		
		
		
	}

	/*
	 * Anoop 15-10-2013 Dashboard for client activity
	 */
	public String beforeShowClientActivityPie()
	{
		//System.out.println("----Status");
		String returnValue = ERROR;
	if (ValidateSession.checkSession())
	{
		try
		{
			getOfferingTableAndColumnNames();
			//System.out.println(legendShow + "===" + width + "===" + height);
			
			
			JSONObject jsonObject3 = new JSONObject();
			jsonArray3 = new JSONArray();
			
			
			activity = new HashMap<String, Integer>();
			ClientHelper ch = new ClientHelper();

			// Missed activities
			missedActivity = ch.getMissedActivityCount(cIdAll,
					connectionSpace);
			activity.put("Missed", missedActivity);

			// Today's activities
			todayActivity = ch.getTodaysActivityCount(cIdAll,
					connectionSpace, tableName);
			activity.put("Today", todayActivity);

			// Tomorros's activities
			tomorrowActivity = ch.getTomorrowsActivityCount(cIdAll,
					connectionSpace, tableName);
			activity.put("Tomorrow", tomorrowActivity);

			// Up coming seven days activities
			nextSevenDaysActivity = ch.getNextSevenDaysActivityCount(
					cIdAll, connectionSpace, tableName);
			activity.put("Seven Days", nextSevenDaysActivity);
			//System.out.println("activity:"+activity.size());
			List activityList=new ArrayList();
			activityList.add(missedActivity);
			activityList.add(todayActivity);
			activityList.add(tomorrowActivity);
			activityList.add(nextSevenDaysActivity);
			List<String> statuslist = new ArrayList<String>();
			clientActivityWiseData = new ArrayList<ClientSupportBean>();
			ClientSupportBean pojo = null;
			statuslist.add("Missed");
			statuslist.add("Today");
			statuslist.add("Tomorrow");
			statuslist.add("Seven Days");
			
			
			clientActivityMap=new HashMap<String, Integer>();
			clientActivityMap.put("Missed", 0);
			clientActivityMap.put("Today", 0);
			clientActivityMap.put("Tomorrow", 0);
			clientActivityMap.put("Seven Days", 0);
			for (Iterator iterator = statuslist.iterator(); iterator
					.hasNext();) {
				pojo = new ClientSupportBean();
				String status = (String) iterator.next(); 
				
				
				String sts1="";
				Object [] obc= activityList.toArray();
				//System.out.println("size of arry  "+obc.length);
				
				
				if(status.equalsIgnoreCase("Missed"))
				{
					sts1="Missed";
					//pojo.setMissedActivity(String.valueOf(activity.get("Missed")));
					/*pojo.setTodayAct("0");
					pojo.setTomorrowAct("0");
					pojo.setSevenDaysAct("0");*/
					
					//System.out.println("missed   "+Integer.parseInt(obc[0].toString()));
					jsonObject3.put("percent", Integer.parseInt(obc[0].toString()));
					pojo.setMissedActivity(String.valueOf(activity.get("Missed")));
					pojo.setStatus("Missed");
					clientActivityWiseData.add(pojo);
				}
				
				
				
				
				
				
				if(status.equalsIgnoreCase("Today"))
				{
				sts1="Today";
					
					/*pojo.setMissedActivity("0");
					pojo.setTomorrowAct("0");
					pojo.setSevenDaysAct("0");*/
					
					//System.out.println("Today   "+Integer.parseInt(obc[1].toString()));
					jsonObject3.put("percent", Integer.parseInt(obc[1].toString()));
					
					pojo.setTodayAct(String.valueOf(activity.get("Today")));
					pojo.setStatus("Today");
					clientActivityWiseData.add(pojo);
				}
				
				
				
				
				
				
				
				
				if(status.equalsIgnoreCase("Tomorrow"))
				{
				sts1="Tomorrow";
					
					/*pojo.setMissedActivity("0");
					pojo.setTodayAct("0");
					pojo.setSevenDaysAct("0");*/
					pojo.setTomorrowAct(String.valueOf(activity.get("Tomorrow")));
					jsonObject3.put("percent", Integer.parseInt(obc[2].toString()));
					pojo.setStatus("Tomorrow");
					clientActivityWiseData.add(pojo);
				}
				
				if(status.equalsIgnoreCase("Seven Days"))
				{
					sts1="Seven Days";
					
					/*pojo.setMissedActivity("0");
					pojo.setTomorrowAct("0");
					pojo.setTodayAct("0");*/
					jsonObject3.put("percent", Integer.parseInt(obc[3].toString()));
					pojo.setSevenDaysAct(String.valueOf(activity.get("Seven Days")));
					pojo.setStatus("Seven Days");
					clientActivityWiseData.add(pojo);
				}
				jsonObject3.put("source", sts1);
				jsonArray3.add(jsonObject3);
				
			}
			
			//System.out.println("jsonArray3    "+jsonArray3.size());
					
			
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
	

	// method for Client Activity Table view.
	public String clientActivityTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			
			try
			{    //method for table
				 //clientRatingWiseData = new ClientDashboardHelper().getClientTypeDataRatingWise(cIdAll,connectionSpace);
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
	

	/*
	 * Anoop 12-10-2013 Get table name and column name based on configuration
	 */
	public void getOfferingTableAndColumnNames()
	{

		try
		{
			int level = 0;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			if (level == 1)
			{
				tableName = "offeringlevel1";
				colName = "verticalname";
			}
			if (level == 2)
			{
				tableName = "offeringlevel2";
				colName = "offeringname";
			}
			if (level == 3)
			{
				tableName = "offeringlevel3";
				colName = "subofferingname";
			}
			if (level == 4)
			{
				tableName = "offeringlevel4";
				colName = "variantname";
			}
			if (level == 5)
			{
				tableName = "offeringlevel5";
				colName = "subvariantname";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Anoop 21-10-2013 get clients at different status
	 */
	public String beforeStatusPie()
	{//
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				JSONObject jsonObject1 = new JSONObject();
				jsonArray1 = new JSONArray();
				ClientHelper ch = new ClientHelper();
				// client status
				clientStatusAllList = ch.getClientCountByStatus(cIdAll,connectionSpace);
				int temp1 = 0;
				Iterator iterator = clientStatusAllList.iterator();
				while(iterator.hasNext()) {
					temp1++;
				    iterator.next();
				}
				//System.out.println("temp Client status  "+temp1);
				for (ArrayList<String> al : clientStatusAllList)
				{
					//System.out.println(al.get(1)+"              asss        "+Integer.parseInt(al.get(2)));
					//sourceMap.put(al.get(1), Integer.parseInt(al.get(2)));
					jsonObject1.put("source", al.get(1));
					jsonObject1.put("percent", Integer.parseInt(al.get(2)));
					jsonObject1.put("issue", temp1);
					jsonArray1.add(jsonObject1);
				}
				
				statusMap = new HashMap<String, Integer>();
				for (ArrayList<String> al : clientStatusAllList)
				{
					statusMap.put(al.get(1), Integer.parseInt(al.get(2)));
				}
				//System.out.println("jsonArray1:         SSSSSSSS      "+jsonArray1.size());
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	
	/*
	 * Written by: manab for get client count VS Source
	 */
	public String beforeStatusSourcePie()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				ClientHelper ch = new ClientHelper();
				// client status
				clientSourceAllList=ch.getClientCountBySource(cIdAll,
						connectionSpace);
				sourceMap = new HashMap<String, Integer>();
				for (ArrayList<String> al : clientSourceAllList)
				{
					//System.out.println(al.get(1)+"     "+Integer.parseInt(al.get(2)));
					sourceMap.put(al.get(1), Integer.parseInt(al.get(2)));
				}

				//System.out.println("sourceMap:"+sourceMap.size());
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}
	
	/*
	 * for pie chat
	 */
	
	public String getJsonDataPieBlock4Source()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				
				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				ClientHelper ch = new ClientHelper();
				// client status
				clientSourceAllList=ch.getClientCountBySource(cIdAll,
						connectionSpace);
				
				
				
				int temp1 = 0;
				Iterator iterator = clientSourceAllList.iterator();
				while(iterator.hasNext()) {
					temp1++;
				    iterator.next();
				}
				//System.out.println("temp1 "+temp1);
				//sourceMap = new HashMap<String, Integer>();
				for (ArrayList<String> al : clientSourceAllList)
				{
					//System.out.println(al.get(1)+"     "+Integer.parseInt(al.get(2)));
					//sourceMap.put(al.get(1), Integer.parseInt(al.get(2)));
					jsonObject.put("source", al.get(1));
					jsonObject.put("percent", Integer.parseInt(al.get(2)));
					jsonObject.put("issue", temp1);
					jsonArray.add(jsonObject);
				}

				//System.out.println("jsonArray:"+jsonArray.size());
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}
	/*
	 * Anoop 21-10-2013 clients with respect to offering
	 */
	public String beforeShowOffWrtClientPie()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getOfferingTableAndColumnNames();
				ClientHelper ch = new ClientHelper();
				// Offering wrt client
				offWrtClientMap = ch.getOfferingWrtClientCount(cIdAll,
						connectionSpace, tableName, colName);
//System.out.println("beforeShowOffWrtClientPie>>>>  "+offWrtClientMap.size());
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}
	
	/*
	 * Anoop 21-10-2013
	 * Show up coming 7 days birthday
	 */
	public String beforeShowClientBirthdayTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("Birthday::::"+legendShow + "===" + width + "===" + height);
				ClientHelper ch = new ClientHelper();
				// Up coming 7 days birthday
				birthdayList = ch.getUpComingSevenDaysBirthday(cIdAll,
						connectionSpace,DateUtil.getCurrentDateUSFormat(), DateUtil.getDateAfterDays(7));
				
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
	
	/*
	 * Anoop 14-10-2013
	 * Show up coming 7 days anniversary
	 */
	public String beforeShowclientAnniversaryTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("anniversary::::"+legendShow + "===" + width + "===" + height);
				ClientHelper ch = new ClientHelper();
				// Up coming 7 days birthday
				anniversaryList = ch.getUpComingSevenDaysAnniversary(cIdAll,
						connectionSpace,DateUtil.getCurrentDateUSFormat(), DateUtil.getDateAfterDays(7));
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
	
	public String showRatingWiseDetails()
	{
		//setRatingWiseViewProperty();
		return SUCCESS;
	}
	
	public String viewRatingDataGrid()
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		ClientSupportBean clientBean;
		ratingfDataList = new ArrayList<ClientSupportBean>();
		
		try {
		if(starRating.equalsIgnoreCase("rat1"))
		{
			starRating = "1";
		}
		else if(starRating.equalsIgnoreCase("rat2"))
		{
			starRating = "2";
		}
		else if(starRating.equalsIgnoreCase("rat3"))
		{
			starRating = "3";
		}
		else if(starRating.equalsIgnoreCase("rat4"))
		{
			starRating = "4";
		}
		else if(starRating.equalsIgnoreCase("rat5"))
		{
			starRating = "5";
		}
		
		if(clientType.equalsIgnoreCase("Prospective"))
		{
			clientType = "0";
		}
		else if(clientType.equalsIgnoreCase("Existing"))
		{
			clientType = "1";
		}
		else if(clientType.equalsIgnoreCase("Lost"))
		{
			clientType = "2";
		}
			StringBuilder query = new StringBuilder("");
			query.append("select cbd.clientName, cbd.address,cbd.webAddress,loc.name,off.subofferingname, com.id ");
			query.append("from client_offering_mapping as com ");
			query.append("left join client_basic_data as cbd on com.clientName= cbd.id ");
			query.append("left join location as loc on cbd.location=loc.id ");
			query.append("left join offeringlevel3 as off on com.offeringId = off.id ");
			query.append("where com.isConverted = '");
			query.append(clientType);
			query.append("' and cbd.starRating='");
			query.append(starRating);
			query.append("'");
			
			System.out.println("query:::::"+query.toString());
			List template = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			
			for (Iterator iterator = template.iterator(); iterator.hasNext();) {
				clientBean = new ClientSupportBean();
			Object[] object = (Object[]) iterator.next();
			if(object[0] != null)
			{
				clientBean.setClientname(object[0].toString());
				
			}
			if(object[1] != null)
			{
				clientBean.setAddress(object[1].toString());
				
			}
			if(object[2] != null)
			{
				clientBean.setWebAddress(object[2].toString());
				
			}
			if(object[3] != null)
			{
				clientBean.setLocation(object[3].toString());
				
			}
			if(object[4] != null)
			{
				clientBean.setOfferingName(object[4].toString());
				
			}
			if(object[5] != null)
			{
				clientBean.setId(object[5].toString());
				
			}
			/*clientBean.setAddress(object[1].toString());
			clientBean.setWebAddress(object[2].toString());
			clientBean.setLocation(object[3].toString());
			clientBean.setOfferingName(object[4].toString());
			clientBean.setId(object[5].toString());*/
			
				ratingfDataList.add(clientBean);
			
		}
		setRatingfDataList(ratingfDataList);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String beforeClientActivityGrid()
	{
		return SUCCESS;
	}
	
	public String viewActivityDataGrid()
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		ClientSupportBean clientBean;
		ratingfDataList = new ArrayList<ClientSupportBean>();
		try {
			//System.out.println(" >"+activityType);
			StringBuilder query = new StringBuilder("");
			if(activityType.equalsIgnoreCase("missedAct"))
			{
				query.append("select cbd.id,cbd.clientName, cst.statusName,cta.maxDateTime from client_basic_data as cbd, ");
				query.append("client_contact_data as ccd, client_takeaction as cta, client_status as cst ");
				query.append("where cbd.id = ccd.clientName and ccd.id = cta.contacId and cst.id = cta.statusId ");
				query.append("and cta.maxDateTime <= '");
				query.append(DateUtil.getCurrentDateUSFormat());
				query.append(" 00:00'");
				query.append("and isFinished = '0'");
				query.append(" and cbd.userName IN(");
				query.append(cIdAll);
				query.append(") order by cta.maxDateTime");
			}
			else if(activityType.equalsIgnoreCase("tomarroAct"))
			{
				query.append("select cbd.id,cbd.clientName, cst.statusName,cta.maxDateTime from client_basic_data as cbd, ");
				query.append("client_contact_data as ccd, client_takeaction as cta, client_status as cst ");
				query.append("where cbd.id = ccd.clientName and ccd.id = cta.contacId and cst.id = cta.statusId ");
				query.append("and cta.maxDateTime like '");
				query.append(DateUtil.getNextDateAfter(1));
				query.append("%'");
				query.append("and isFinished = '0'");
				query.append(" and cbd.userName IN(");
				query.append(cIdAll);
				query.append(") order by cta.maxDateTime");
			}
			else if(activityType.equalsIgnoreCase("sevenDayAct"))
			{
				String todayDate = DateUtil.currentdatetime(); // yyyy-mm-dd
				// 11:11:11
				todayDate = todayDate.substring(0, todayDate.lastIndexOf(":")); // yyyy
				// -
				// mm
				// -dd
				// 11:11
				query.append("select cbd.id,cbd.clientName, cst.statusName,cta.maxDateTime from client_basic_data as cbd, ");
				query.append("client_contact_data as ccd, client_takeaction as cta, client_status as cst ");
				query.append("where cbd.id = ccd.clientName and ccd.id = cta.contacId and cst.id = cta.statusId ");
				query.append("and cta.maxDateTime >= '");
				query.append(todayDate);
				query.append("' and maxDateTime <= '");
				query.append(DateUtil.getNextDateAfter(7));
				query.append("  23:59' ");
				query.append(" and cbd.userName IN(");
				query.append(cIdAll);
				query.append(") order by cta.maxDateTime");
			}
			else if(activityType.equalsIgnoreCase("todayAct"))
			{
				query.append("select cbd.id,cbd.clientName, cst.statusName,cta.maxDateTime from client_basic_data as cbd, ");
				query.append("client_contact_data as ccd, client_takeaction as cta, client_status as cst ");
				query.append("where cbd.id = ccd.clientName and ccd.id = cta.contacId and cst.id = cta.statusId ");
				query.append("and cta.maxDateTime like '");
				query.append(DateUtil.getCurrentDateUSFormat());
				query.append("%'");
				query.append("and isFinished = '0'");
				query.append(" and cbd.userName IN(");
				query.append(cIdAll);
				query.append(") order by cta.maxDateTime");
			}
			
			System.out.println("query:::::"+query.toString());
			List template = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			
			for (Iterator iterator = template.iterator(); iterator.hasNext();) {
				clientBean = new ClientSupportBean();
			Object[] object = (Object[]) iterator.next();
			if(object[0] != null)
			{
				clientBean.setId(object[0].toString());
				//System.out.println("object[0].toString() "+object[0].toString());
			}
			if(object[1] != null)
			{
				clientBean.setClientname(object[1].toString());
				//System.out.println("object[1].toString() "+object[1].toString());
			}
			if(object[2] != null)
			{
				clientBean.setStatus(object[2].toString());
				//System.out.println("object[2].toString() "+object[2].toString());
			}
			if(object[3] != null)
			{
				clientBean.setDueDateTime(object[3].toString());
				//System.out.println("object[3].toString() "+object[3].toString());
			}
				ratingfDataList.add(clientBean);
				//System.out.println("ratingfDataList  "+ratingfDataList.size()); 
		}
		setRatingfDataList(ratingfDataList);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
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

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public HashMap<String, Integer> getAssociateMap()
	{
		return associateMap;
	}

	public void setAssociateMap(HashMap<String, Integer> associateMap)
	{
		this.associateMap = associateMap;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getOfferingLevel()
	{
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel)
	{
		this.offeringLevel = offeringLevel;
	}

	public int getMissedActivity()
	{
		return missedActivity;
	}

	public void setMissedActivity(int missedActivity)
	{
		this.missedActivity = missedActivity;
	}

	public HashMap<String, Integer> getStatusMap()
	{
		return statusMap;
	}

	public void setStatusMap(HashMap<String, Integer> statusMap)
	{
		this.statusMap = statusMap;
	}

	public HashMap<String, Integer> getOffWrtClientMap()
	{
		return offWrtClientMap;
	}

	public void setOffWrtClientMap(HashMap<String, Integer> offWrtClientMap)
	{
		this.offWrtClientMap = offWrtClientMap;
	}

	public ArrayList<ArrayList<String>> getBirthdayList()
	{
		return birthdayList;
	}

	public void setBirthdayList(ArrayList<ArrayList<String>> birthdayList)
	{
		this.birthdayList = birthdayList;
	}

	public ArrayList<ArrayList<String>> getAnniversaryList()
	{
		return anniversaryList;
	}

	public void setAnniversaryList(ArrayList<ArrayList<String>> anniversaryList)
	{
		this.anniversaryList = anniversaryList;
	}

	public String getCurrentDate()
	{
		return currentDate;
	}

	public void setCurrentDate(String currentDate)
	{
		this.currentDate = currentDate;
	}

	public int getTodayActivity()
	{
		return todayActivity;
	}

	public void setTodayActivity(int todayActivity)
	{
		this.todayActivity = todayActivity;
	}

	public int getNextSevenDaysActivity()
	{
		return nextSevenDaysActivity;
	}

	public void setNextSevenDaysActivity(int nextSevenDaysActivity)
	{
		this.nextSevenDaysActivity = nextSevenDaysActivity;
	}

	public int getTomorrowActivity()
	{
		return tomorrowActivity;
	}

	public void setTomorrowActivity(int tomorrowActivity)
	{
		this.tomorrowActivity = tomorrowActivity;
	}

	public HashMap<String, Integer> getActivity()
	{
		return activity;
	}

	public void setActivity(HashMap<String, Integer> activity)
	{
		this.activity = activity;
	}

	public ArrayList<ArrayList<String>> getClientStatusAllList()
	{
		return clientStatusAllList;
	}

	public void setClientStatusAllList(
			ArrayList<ArrayList<String>> clientStatusAllList)
	{
		this.clientStatusAllList = clientStatusAllList;
	}

	public List<ClientSupportBean> getClientRatingWiseData() {
		return clientRatingWiseData;
	}

	public void setClientRatingWiseData(List<ClientSupportBean> clientRatingWiseData) {
		this.clientRatingWiseData = clientRatingWiseData;
	}

	public List<ClientSupportBean> getClientActivityWiseData() {
		return clientActivityWiseData;
	}

	public void setClientActivityWiseData(
			List<ClientSupportBean> clientActivityWiseData) {
		this.clientActivityWiseData = clientActivityWiseData;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public List<ClientSupportBean> getRatingfDataList() {
		return ratingfDataList;
	}

	public void setRatingfDataList(List<ClientSupportBean> ratingfDataList) {
		this.ratingfDataList = ratingfDataList;
	}

	public int getTotalRat1() {
		return totalRat1;
	}

	public void setTotalRat1(int totalRat1) {
		this.totalRat1 = totalRat1;
	}

	public int getTotalRat2() {
		return totalRat2;
	}

	public void setTotalRat2(int totalRat2) {
		this.totalRat2 = totalRat2;
	}

	public int getTotalRat3() {
		return totalRat3;
	}

	public void setTotalRat3(int totalRat3) {
		this.totalRat3 = totalRat3;
	}

	public int getTotalRat4() {
		return totalRat4;
	}

	public void setTotalRat4(int totalRat4) {
		this.totalRat4 = totalRat4;
	}

	public int getTotalRat5() {
		return totalRat5;
	}

	public void setTotalRat5(int totalRat5) {
		this.totalRat5 = totalRat5;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public void setSourceMap(HashMap<String, Integer> sourceMap) {
		this.sourceMap = sourceMap;
	}

	public HashMap<String, Integer> getSourceMap() {
		return sourceMap;
	}

	public void setClientSourceAllList(ArrayList<ArrayList<String>> clientSourceAllList) {
		this.clientSourceAllList = clientSourceAllList;
	}

	public ArrayList<ArrayList<String>> getClientSourceAllList() {
		return clientSourceAllList;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}


	public void setClientRatingWiseDataSource(
			List<ClientSupportBean> clientRatingWiseDataSource) {
		this.clientRatingWiseDataSource = clientRatingWiseDataSource;
	}


	public List<ClientSupportBean> getClientRatingWiseDataSource() {
		return clientRatingWiseDataSource;
	}


	public JSONArray getJsonArray3() {
		return jsonArray3;
	}


	public void setJsonArray3(JSONArray jsonArray3) {
		this.jsonArray3 = jsonArray3;
	}


	public JSONArray getJsonArray1() {
		return jsonArray1;
	}


	public void setJsonArray1(JSONArray jsonArray1) {
		this.jsonArray1 = jsonArray1;
	}

	
	
}