package com.Over2Cloud.ctrl.wfpm.associate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientSupportBean;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssociateDashboardAction extends SessionProviderClass
{
	// Anoop, 7-10-2013
	HashMap<String, Integer>							leadMap									= null;
	HashMap<String, Integer>							clientMap								= null;
	HashMap<String, Integer>							associateMap						= null;
	HashMap<String, Integer>							offeringMap							= null;
	HashMap<String, Integer>							employeeMap							= null;
	HashMap<String, Integer>							eventMap								= null;
	String																tableName								= "", colName = "";
	private String												offeringLevel						= session.get("offeringLevel").toString();
	private String												legendShow							= "false";
	private String												width										= "200";
	private String												height									= "200",starRating,clientType;
	private HashMap<String, Integer>			activity								= null;
	private int														missedActivity;
	private HashMap<String, Integer>			statusMap								= null;
	private HashMap<String, Integer>			associatestatusMap								= null;
	private HashMap<String, Integer>			associateActivityMap								= null;
	
	
	private HashMap<String, Integer>			offWrtClientMap					= null;
	private ArrayList<ArrayList<String>>	birthdayList						= null;
	private ArrayList<ArrayList<String>>	anniversaryList					= null;
	private String												currentDate							= DateUtil.getCurrentDateIndianFormat();
	private int														todayActivity;
	private int														nextSevenDaysActivity;
	private int														tomorrowActivity;
	private ArrayList<ArrayList<String>>	associateStatusAllList	= null;
	private Map<String, Integer>					offWrtAssoMap						= null;
	CommonHelper ch = new CommonHelper();
	private List<AssociateSupportBean> finalDataList;
	private List<AssociateSupportBean> ratingfDataList;
	String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
	private JSONArray jsonArray;
	private JSONArray jsonArray1;
	private JSONArray jsonArray2;
	private JSONArray jsonArray3;
	private ArrayList<ArrayList<String>> associateSourceAllList = null;
	

	public String beforeAssociatePie()
	{

		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				////System.out.println(legendShow + "===" + width + "===" + height);
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

	private void getAssociatePieChartData() throws Exception
	{
		ArrayList<ArrayList<String>> associatepiedata=new ArrayList<ArrayList<String>>();
		JSONObject jsonObject2 = new JSONObject();
		jsonArray2 = new JSONArray();
		String q3 = "select  com.isConverted, count(cbd.id) from associate_basic_data as cbd, "
				+ "associate_offering_mapping as com where com.associateName = cbd.id " + "and cbd.userName IN(" + cIdAll + ") group by com.isConverted";
		System.out.println("q3  associatepiedata     "+q3);
		List associateList = coi.executeAllSelectQuery(q3, connectionSpace);
		
		int temp1 = 0;
		Iterator iterator = associateList.iterator();
		while(iterator.hasNext()) {
			temp1++;
		    iterator.next();
		}
		//System.out.println("temp Associate status  "+temp1);
		
		
		if (associateList != null)
		{
			associateMap = new HashMap<String, Integer>();
			associateMap.put("Prospective", 0);
			associateMap.put("Existing", 0);
			associateMap.put("Lost", 0);

			for (Object ob : associateList)
			{
				Object[] data = (Object[]) ob;
				String sts = "";
				if (data[0].toString().equalsIgnoreCase("0"))
				{
					sts="Prospective";
					associateMap.put("Prospective", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("1"))
				{
					sts="Existing";
					associateMap.put("Existing", Integer.parseInt(data[1].toString()));
				}
				else if (data[0].toString().equalsIgnoreCase("2"))
				{
					sts="Lost";
					associateMap.put("Lost", Integer.parseInt(data[1].toString()));
				}
				jsonObject2.put("source", sts);
				jsonObject2.put("percent", Integer.parseInt(data[1].toString()));
				jsonObject2.put("issue", temp1);
				jsonArray2.add(jsonObject2);
			}
		}
		
		//System.out.println("jsonArray  dlkjdlkjdj     "+jsonArray2.size());
		
		/*if (associateList != null && associateList.size() > 0)
		{
			ArrayList<String> al = null;
			for (Iterator iterator = associateList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				al = new ArrayList<String>();
				al.add(String.valueOf(object[0].toString()));
				al.add((object[1] == null || object[1].toString().equalsIgnoreCase("")) ? "NA" : object[1].toString());
				al.add(object[2].toString());

				associatepiedata.add(al);
			}
		}*/
		
		
		
		
		
		//sourceMap = new HashMap<String, Integer>();
	/*	for (ArrayList<String> al : associatepiedata)
		{
			System.out.println(al.get(1)+"              asss        "+Integer.parseInt(al.get(2)));
			//sourceMap.put(al.get(1), Integer.parseInt(al.get(2)));
			jsonObject2.put("source", al.get(1));
			jsonObject2.put("percent", Integer.parseInt(al.get(2)));
			jsonObject2.put("issue", temp1);
			jsonArray2.add(jsonObject2);
		}
		associatestatusMap = new HashMap<String, Integer>();
		
		for (ArrayList<String> al : associatepiedata)
		{
			associatestatusMap.put(al.get(1), Integer.parseInt(al.get(2)));
		}*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

	public String beforeAssociateTable()
	{

		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				////System.out.println(legendShow + "===" + width + "===" + height);
				getAssociateTableData();
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
	
	private void getAssociateTableData() throws Exception
	{
		finalDataList = new ArrayList<AssociateSupportBean>();
		int rating=0;
		for(int i=0;i<3;i++)
		{
		String q3 = "select cbd.associateRating from  associate_offering_mapping as com"
				+ ",associate_basic_data as cbd where com.associateName = cbd.id " + "and cbd.userName IN(" + cIdAll + ") and  com.isConverted='"+i+"'";
		System.out.println(q3.toString());
		List associateList = coi.executeAllSelectQuery(q3, connectionSpace);
		int rat1=0,rat2=0,rat3=0,rat4=0,rat5=0;
		//System.out.println("Value Of I    >>>"+i);
		AssociateSupportBean pojo=new AssociateSupportBean();
		if(i==0){
			pojo.setClientname("Prospective");
		}
		if(i==1){
			pojo.setClientname("Existing");
		}
		if(i==2){
			pojo.setClientname("Lost");
		}
		if (associateList != null && associateList.size()>0)
		{
			
			for (Iterator iterator = associateList.iterator(); iterator.hasNext();)
			{	
				Object data = (Object) iterator.next();
				if(data!=null && !data.toString().equalsIgnoreCase("NA"))
				{
					try
					{
						rating=Integer.parseInt(data.toString());
						//System.out.println(rating);
					}
					catch (Exception e)
					{
						rating=0;
						e.printStackTrace();
					}
					if(rating==1)
					{
						rat1+=1;
					}
					else if(rating==2)
					{
						rat2+=1;
					}
					else if(rating==3)
					{
						rat3+=1;
					}
					else if(rating==4)
					{
						rat4+=1;
					}
					else if(rating==5)
					{
						rat5+=1;
					}
					pojo.setRat1(String.valueOf(rat1+Integer.parseInt(pojo.getRat1())));
					pojo.setRat2(String.valueOf(rat2+Integer.parseInt(pojo.getRat2())));
					pojo.setRat3(String.valueOf(rat3+Integer.parseInt(pojo.getRat3())));
					pojo.setRat4(String.valueOf(rat4+Integer.parseInt(pojo.getRat4())));
					pojo.setRat5(String.valueOf(rat5+Integer.parseInt(pojo.getRat5())));
					
				}
				
			   }
			finalDataList.add((pojo));
		    }
		
		 }
    }

	
	/*
	 * Anoop 11-10-2013 show activity pie
	 */
	public String beforeActivityPie()
	{
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
				AssociateHelper ah = new AssociateHelper();
				// Missed activities
				missedActivity = ah.getMissedActivityCount(cIdAll, connectionSpace);
				activity.put("Missed", missedActivity);

				// Today's activities
				todayActivity = ah.getTodaysActivityCount(cIdAll, connectionSpace, tableName);
				activity.put("Today", todayActivity);

				// Tomorros's activities
				tomorrowActivity = ah.getTomorrowsActivityCount(cIdAll, connectionSpace, tableName);
				activity.put("Tomorrow", tomorrowActivity);

				// Up coming seven days activities
				nextSevenDaysActivity = ah.getNextSevenDaysActivityCount(cIdAll, connectionSpace, tableName);
				activity.put("Seven Days", nextSevenDaysActivity);
				
				List activityList=new ArrayList();
				activityList.add(missedActivity);
				activityList.add(todayActivity);
				activityList.add(tomorrowActivity);
				activityList.add(nextSevenDaysActivity);
				//System.out.println("activityList   "+activityList);
				List<String> statuslist = new ArrayList<String>();
				finalDataList = new ArrayList<AssociateSupportBean>();
				AssociateSupportBean pojo = null;
				statuslist.add("Missed");
				statuslist.add("Today");
				statuslist.add("Tomorrow");
				statuslist.add("Seven Days");
				
				
				associateActivityMap=new HashMap<String, Integer>();
				associateActivityMap.put("Missed", 0);
				associateActivityMap.put("Today", 0);
				associateActivityMap.put("Tomorrow", 0);
				associateActivityMap.put("Seven Days", 0);
				
				for (Iterator iterator = statuslist.iterator(); iterator.hasNext();) {
					pojo = new AssociateSupportBean();
					String status = (String) iterator.next(); 
					
					
					String sts1="";
					Object [] obc= activityList.toArray();
					//System.out.println("size of arry  "+obc.length);
					
					
					if(status.equalsIgnoreCase("Missed"))
					{
						sts1="Missed";
						//associateActivityMap.put("Missed", Integer.parseInt(obc[0].toString()));
						//System.out.println("missed   "+Integer.parseInt(obc[0].toString()));
						jsonObject3.put("percent", Integer.parseInt(obc[0].toString()));
						
						
						pojo.setMissedActivity(String.valueOf(activity.get("Missed")));
						pojo.setStatus("Missed");
						finalDataList.add(pojo);
					}
					else if(status.equalsIgnoreCase("Today"))
					{
						sts1="Today";
						//associateActivityMap.put("Today", Integer.parseInt(obc[1].toString()));
						//System.out.println("Today   "+Integer.parseInt(obc[1].toString()));
						jsonObject3.put("percent", Integer.parseInt(obc[1].toString()));
						
						pojo.setTodayAct(String.valueOf(activity.get("Today")));
						pojo.setStatus("Today");
						finalDataList.add(pojo);
					}
					else if(status.equalsIgnoreCase("Tomorrow"))
					{
						
						sts1="Tomorrow";
						//associateActivityMap.put("Tomorrow", Integer.parseInt(obc[2].toString()));
						//System.out.println("Tomorrow   "+Integer.parseInt(obc[2].toString()));
						jsonObject3.put("percent", Integer.parseInt(obc[2].toString()));
						
						
						pojo.setTomorrowAct(String.valueOf(activity.get("Tomorrow")));
						pojo.setStatus("Tomorrow");
						finalDataList.add(pojo);
					}
					else if(status.equalsIgnoreCase("Seven Days"))
					{
						
						sts1="Seven Days";
						//associateActivityMap.put("Seven Days", Integer.parseInt(obc[3].toString()));
						//System.out.println("Seven Days   "+Integer.parseInt(obc[3].toString()));
						jsonObject3.put("percent", Integer.parseInt(obc[3].toString()));
						
						
						pojo.setSevenDaysAct(String.valueOf(activity.get("Seven Days")));
						pojo.setStatus("Seven Days");
						finalDataList.add(pojo);
					}
					
					
					
					jsonObject3.put("source", sts1);
					
					//jsonObject3.put("issue", temp1);
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

	/*
	 * Anoop 12-10-2013 Get table name and column name based on configuration
	 */
	public void getOfferingTableAndColumnNames()
	{

		try
		{
			int level = 0;
			String[] oLevels = null;
			

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
				//System.out.println("level  "+level);
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
	 * Anoop 11-10-2013
	 */
	public String beforeStatusPie()
	{

		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				JSONObject jsonObject1 = new JSONObject();
				jsonArray1 = new JSONArray();
				AssociateHelper ah = new AssociateHelper();
				// Associate status
				associateStatusAllList = ah.getAssociateCountByStatus(cIdAll, connectionSpace);
				
				
				int temp1 = 0;
				Iterator iterator = associateStatusAllList.iterator();
				while(iterator.hasNext()) {
					temp1++;
				    iterator.next();
				}
				//System.out.println("temp Associate status  "+temp1);
				//sourceMap = new HashMap<String, Integer>();
				for (ArrayList<String> al : associateStatusAllList)
				{
					//System.out.println(al.get(1)+"              asss        "+Integer.parseInt(al.get(2)));
					//sourceMap.put(al.get(1), Integer.parseInt(al.get(2)));
					jsonObject1.put("source", al.get(1));
					jsonObject1.put("percent", Integer.parseInt(al.get(2)));
					jsonObject1.put("issue", temp1);
					jsonArray1.add(jsonObject1);
				}
				statusMap = new HashMap<String, Integer>();
				
				for (ArrayList<String> al : associateStatusAllList)
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
	 * Anoop 12-10-2013
	 */
	public String beforeShowOffWrtAssoPie()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getOfferingTableAndColumnNames();
				AssociateHelper ah = new AssociateHelper();
				// Offering wrt Associate
				offWrtAssoMap = ah.getOfferingWrtAssociateCount(cIdAll, connectionSpace, tableName, colName);

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
	 * Anoop 14-10-2013 Show up coming 7 days birthday
	 */
	public String beforeShowAssociateBirthdayTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("Birthday::::" + legendShow + "===" + width + "===" + height);
				AssociateHelper ah = new AssociateHelper();
				// Up coming 7 days birthday
				birthdayList = ah.getUpComingSevenDaysBirthday(cIdAll, connectionSpace, DateUtil.getCurrentDateUSFormat(), DateUtil.getDateAfterDays(7));
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
	 * Anoop 14-10-2013 Show up coming 7 days anniversary
	 */
	public String beforeShowAssociateAnniversaryTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				//System.out.println("anniversary::::" + legendShow + "===" + width + "===" + height);
				AssociateHelper ah = new AssociateHelper();
				// Up coming 7 days birthday
				anniversaryList = ah.getUpComingSevenDaysAnniversary(cIdAll, connectionSpace, DateUtil.getCurrentDateUSFormat(), DateUtil.getDateAfterDays(7));
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
		//System.out.println("In>>>>");
		return SUCCESS;
	}
	
	public String viewRatingDataGrid()
	{
		//System.out.println("iii2    ");
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		AssociateSupportBean clientBean;
		finalDataList = new ArrayList<AssociateSupportBean>();
		
		try {System.out.println("iii3    ");
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
			query.append("select cbd.associateName, cbd.address,cbd.webAddress,loc.name,off.subofferingname, com.id ");
			query.append("from associate_offering_mapping as com ");
			query.append("left join associate_basic_data as cbd on com.associateName= cbd.id ");
			query.append("left join location as loc on cbd.location=loc.id ");
			query.append("left join offeringlevel3 as off on com.offeringId = off.id ");
			query.append("where com.isConverted = '");
			query.append(clientType);
			query.append("' and cbd.associateRating='");
			query.append(starRating);
			query.append("'");
			
			System.out.println("query:::::"+query.toString());
			List template = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			for (Iterator iterator = template.iterator(); iterator.hasNext();) {
				clientBean = new AssociateSupportBean();
			Object[] object = (Object[]) iterator.next();
				
			
			if(object[0] != null)
			{
				clientBean.setClientname(object[0].toString());
				//System.out.println("object[0].toString() "+object[0].toString());
			}
			if(object[1] != null)
			{
				clientBean.setAddress(object[1].toString());
				//System.out.println("object[1].toString() "+object[1].toString());
			}
			if(object[2] != null)
			{
				clientBean.setWebAddress(object[2].toString());
				//System.out.println("object[2].toString() "+object[2].toString());
			}
			if(object[3] != null)
			{
				clientBean.setLocation(object[3].toString());
				//System.out.println("object[3].toString() "+object[3].toString());
			}
			if(object[4] != null)
			{
				clientBean.setOfferingName(object[4].toString());
				//System.out.println("object[4].toString() "+object[4].toString());
			}
			if(object[5] != null)
			{
				clientBean.setId(object[5].toString());
				//System.out.println("object[5].toString() "+object[5].toString());
			}
			/*clientBean.setAddress(object[1].toString());
			clientBean.setWebAddress(object[2].toString());
			clientBean.setLocation(object[3].toString());
			clientBean.setOfferingName(object[4].toString());
			clientBean.setId(object[5].toString());*/
			
			finalDataList.add(clientBean);
				//System.out.println("ratingfDataList  "+finalDataList.size()); 
			
			//System.out.println("iii5    ");
		}
		setRatingfDataList(finalDataList);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	public String getJsonData4AssociateSource()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				
				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				//ClientHelper ch = new ClientHelper();
				AssociateHelper ch=new AssociateHelper();
				// client status
				associateSourceAllList=ch.getAssociateCountBySource(cIdAll, connectionSpace);
				
				
				
				int temp1 = 0;
				Iterator iterator = associateSourceAllList.iterator();
				while(iterator.hasNext()) {
					temp1++;
				    iterator.next();
				}
				//System.out.println("temp1 "+temp1);
				//sourceMap = new HashMap<String, Integer>();
				for (ArrayList<String> al : associateSourceAllList)
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

	public ArrayList<ArrayList<String>> getAssociateStatusAllList()
	{
		return associateStatusAllList;
	}

	public void setAssociateStatusAllList(ArrayList<ArrayList<String>> associateStatusAllList)
	{
		this.associateStatusAllList = associateStatusAllList;
	}

	public Map<String, Integer> getOffWrtAssoMap()
	{
		return offWrtAssoMap;
	}

	public void setOffWrtAssoMap(Map<String, Integer> offWrtAssoMap)
	{
		this.offWrtAssoMap = offWrtAssoMap;
	}

	public void setFinalDataList(List<AssociateSupportBean> finalDataList) {
		this.finalDataList = finalDataList;
	}

	public List<AssociateSupportBean> getFinalDataList() {
		return finalDataList;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientType() {
		return clientType;
	}

	public void setRatingfDataList(List<AssociateSupportBean> ratingfDataList) {
		this.ratingfDataList = ratingfDataList;
	}

	public List<AssociateSupportBean> getRatingfDataList() {
		return ratingfDataList;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public ArrayList<ArrayList<String>> getAssociateSourceAllList() {
		return associateSourceAllList;
	}

	public void setAssociateSourceAllList(
			ArrayList<ArrayList<String>> associateSourceAllList) {
		this.associateSourceAllList = associateSourceAllList;
	}

	public JSONArray getJsonArray1() {
		return jsonArray1;
	}

	public void setJsonArray1(JSONArray jsonArray1) {
		this.jsonArray1 = jsonArray1;
	}

	public JSONArray getJsonArray2() {
		return jsonArray2;
	}

	public void setJsonArray2(JSONArray jsonArray2) {
		this.jsonArray2 = jsonArray2;
	}

	public HashMap<String, Integer> getAssociatestatusMap() {
		return associatestatusMap;
	}

	public void setAssociatestatusMap(HashMap<String, Integer> associatestatusMap) {
		this.associatestatusMap = associatestatusMap;
	}

	public JSONArray getJsonArray3() {
		return jsonArray3;
	}

	public void setJsonArray3(JSONArray jsonArray3) {
		this.jsonArray3 = jsonArray3;
	}
	
	
	
	

}