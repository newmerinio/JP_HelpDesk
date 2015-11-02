package com.Over2Cloud.ctrl.wfpm.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Sessionfactor;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.dashboard.ActivityType;

public class ClientHelper<T>
{
	CommonOperInterface	cbt		= new CommonConFactory().createInterface();
	int									level	= 0;

	public int getProspectiveClientCount(String userName, SessionFactory connectionSpace) throws Exception
	{
		int pendingClient = 0;
		StringBuilder query1 = new StringBuilder("");
		query1.append("select count(*) from client_basic_data where userName = '" + userName
				+ "' and id in (select distinct(clientName) from client_offering_mapping " + "where isConverted = '0')");
		List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
		if (dataCount != null && dataCount.size() > 0)
		{
			for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					pendingClient = Integer.parseInt(object.toString());
				}
			}
		}
		return pendingClient;
	}

	public int getLostClientCount(String userName, SessionFactory connectionSpace) throws Exception
	{

		int lostClient = 0;
		StringBuilder query2 = new StringBuilder("");
		query2.append("select count(*) from client_basic_data where userName = '" + userName
				+ "' and id in (select distinct(clientName) from client_offering_mapping " + "where isConverted = '2')");
		List dataCount2 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
		if (dataCount2 != null && dataCount2.size() > 0)
		{
			for (Iterator iterator = dataCount2.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					lostClient = Integer.parseInt(object.toString());
				}
			}
		}
		return lostClient;

	}

	public int getExistingClient(String userName, SessionFactory connectionSpace) throws Exception
	{
		int existingClient = 0;
		StringBuilder query3 = new StringBuilder("");
		query3.append("select count(*) from client_basic_data where userName = '" + userName
				+ "' and id in (select distinct(clientName) from client_offering_mapping " + "where isConverted = '1')");
		List dataCount3 = cbt.executeAllSelectQuery(query3.toString(), connectionSpace);
		if (dataCount3 != null && dataCount3.size() > 0)
		{
			for (Iterator iterator = dataCount3.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					existingClient = Integer.parseInt(object.toString());
				}
			}
		}
		return existingClient;
	}

	public int getTodaysActivityCount(String userName, SessionFactory connectionSpace, String tableName) throws Exception
	{
		int todayActivity = 0;
		StringBuilder query4 = new StringBuilder("");
		query4.append("select count(*) from client_contact_data as ccd, client_takeaction as cta, " + "client_status as cs, " + tableName
				+ " as co, client_basic_data as cbd  " + "where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id  "
				+ "and cbd.id = ccd.clientName and cbd.userName IN(" + userName + ") and cta.isFinished = '0'  and cta.maxDateTime like '"
				+ DateUtil.getCurrentDateUSFormat() + "%' ");
		List dataCount4 = cbt.executeAllSelectQuery(query4.toString(), connectionSpace);
		if (dataCount4 != null && dataCount4.size() > 0)
		{
			for (Iterator iterator = dataCount4.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					todayActivity = Integer.parseInt(object.toString());
				}
			}
		}
		return todayActivity;
	}

	public int getNextSevenDaysActivityCount(String userName, SessionFactory connectionSpace, String tableName) throws Exception
	{
		int nextSevenDaysActivity = 0;
		String todayDate = DateUtil.currentdatetime(); // yyyy-mm-dd
		// 11:11:11
		todayDate = todayDate.substring(0, todayDate.lastIndexOf(":")); // yyyy
		// -
		// mm
		// -dd
		// 11:11
		//System.out.println("::::::::::::::::" + todayDate);
		StringBuilder query5 = new StringBuilder("");
		query5.append("select count(*) from client_contact_data as ccd, client_takeaction as cta, " + "client_status as cs, " + tableName
				+ " as co, client_basic_data as cbd  " + "where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id  "
				+ "and cbd.id = ccd.clientName and cbd.userName IN(" + userName + ") and isFinished = '0'  " + "and maxDateTime >= '" + todayDate
				+ "' and maxDateTime <= '" + DateUtil.getNextDateAfter(7) + " 23:59' " + "order by maxDateTime ");
		List dataCount5 = cbt.executeAllSelectQuery(query5.toString(), connectionSpace);
		if (dataCount5 != null && dataCount5.size() > 0)
		{
			for (Iterator iterator = dataCount5.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					nextSevenDaysActivity = Integer.parseInt(object.toString());
				}
			}
		}
		return nextSevenDaysActivity;
	}

	public int getTomorrowsActivityCount(String userName, SessionFactory connectionSpace, String tableName) throws Exception
	{
		int tomorrowActivity = 0;
		StringBuilder query6 = new StringBuilder("");
		query6.append("select count(*) from client_contact_data as ccd, client_takeaction as cta, " + "client_status as cs, " + tableName
				+ " as co, client_basic_data as cbd  " + "where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id  "
				+ "and cbd.id = ccd.clientName and cbd.userName IN(" + userName + ") and isFinished = '0'  " + "and maxDateTime like '" + DateUtil.getNextDateAfter(1)
				+ "%'" + "order by maxDateTime ");
		List dataCount6 = cbt.executeAllSelectQuery(query6.toString(), connectionSpace);
		if (dataCount6 != null && dataCount6.size() > 0)
		{
			for (Iterator iterator = dataCount6.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					tomorrowActivity = Integer.parseInt(object.toString());
				}
			}
		}
		return tomorrowActivity;
	}

	public int getMissedActivityCount(String userName, SessionFactory connectionSpace) throws Exception
	{
		int missedActivity = 0;
		StringBuilder query6 = new StringBuilder("");
		query6.append("select count(cbd.id) from client_basic_data as cbd, client_contact_data as ccd ,"
				+ "client_takeaction as cta where cbd.id = ccd.clientName and ccd.id = cta.contacId " + "and cta.maxDateTime <= '" + DateUtil.getCurrentDateUSFormat()
				+ " 00:00' and isFinished = '0' " + "and cbd.userName IN(" + userName + ") ");
		List dataCount6 = cbt.executeAllSelectQuery(query6.toString(), connectionSpace);
		if (dataCount6 != null && dataCount6.size() > 0)
		{
			for (Iterator iterator = dataCount6.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					missedActivity = Integer.parseInt(object.toString());
				}
			}
		}
		return missedActivity;
	}

	/*
	 * Anoop 21-10-2013
	 */
	public ArrayList<ArrayList<String>> getClientCountByStatus(String userName, SessionFactory connectionSpace) throws Exception
	{
		ArrayList<ArrayList<String>> clientStatusAllList = new ArrayList<ArrayList<String>>();
		StringBuilder query7 = new StringBuilder("");
		query7.append("select cs.id, cs.statusName, count(distinct(cbd.id)) from client_status as cs, " + "client_takeaction as cta , "
				+ "client_contact_data as ccd, client_basic_data as cbd, " + "client_offering_mapping as com where cbd.id = ccd.clientName "
				+ "and ccd.id = cta.contacId and cbd.userName IN(" + userName + ") and cta.statusId = cs.id and com.clientName = cbd.id "
				+ "and com.isConverted = 0 and cta.isFinished = '0' " + "group by cta.statusId order by cs.statusName ");
		System.out.println(">>>>>>>>>>>>>>>>>>>> 11111"+query7);
		List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

		if (dataCount7 != null && dataCount7.size() > 0)
		{
			ArrayList<String> al = null;
			for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				al = new ArrayList<String>();
				al.add(String.valueOf(object[0].toString()));
				al.add((object[1] == null || object[1].toString().equalsIgnoreCase("")) ? "NA" : object[1].toString());
				al.add(object[2].toString());

				clientStatusAllList.add(al);
			}
		}
		return clientStatusAllList;
	}
	
	/*
	 * Written by :Manab
	 * 
	 */
	
	public ArrayList<ArrayList<String>> getClientCountBySource(String userName, SessionFactory connectionSpace) throws Exception
	{
		ArrayList<ArrayList<String>> clientSourceAllList = new ArrayList<ArrayList<String>>();
		StringBuilder query7 = new StringBuilder("");
		query7.append("select cs.id, cs.sourceName, count(distinct(cbd.id)) from sourcemaster as cs, " + "client_takeaction as cta , "
				+ "client_contact_data as ccd, client_basic_data as cbd, " + "client_offering_mapping as com where cbd.id = ccd.clientName "
				+ "and ccd.id = cta.contacId and cbd.userName IN(" + userName + ") and cbd.sourceMaster = cs.id and com.clientName = cbd.id "
				+ "and com.isConverted = 0 and cta.isFinished = '0' " + "group by cta.statusId order by cbd.sourceMaster ");
System.out.println(">>>>>>>>>>>>>>>>>>>> "+query7);

 
		List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

		if (dataCount7 != null && dataCount7.size() > 0)
		{
			ArrayList<String> al = null;
			for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				al = new ArrayList<String>();
				al.add(String.valueOf(object[0].toString()));
				al.add((object[1] == null || object[1].toString().equalsIgnoreCase("")) ? "NA" : object[1].toString());
				al.add(object[2].toString());

				clientSourceAllList.add(al);
			}
		}
		return clientSourceAllList;
	}

	/*
	 * Anoop 12-10-2013 Get offering wrt client count
	 */
	public HashMap<String, Integer> getOfferingWrtClientCount(String userName, SessionFactory connectionSpace, String tableName, String colName) throws Exception
	{
		HashMap<String, Integer> offWrtClientMap = new HashMap<String, Integer>();
		StringBuilder query7 = new StringBuilder("");
		query7.append("select off." + colName + ", count(distinct(cbd.clientName)) " + "from " + tableName + " as off, client_basic_data as cbd, "
				+ "client_offering_mapping as com where cbd.id = com.clientName " + "and com.offeringId = off.id and com.isConverted = '1' " + "and cbd.userName IN("
				+ userName + ") group by com.offeringId ");

		List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);
		System.out.println("query7.toString() "+query7.toString());
		if (dataCount7 != null && dataCount7.size() > 0)
		{
			for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] == null || object[1] == null) continue;

				offWrtClientMap.put(object[0].toString(), Integer.parseInt(object[1].toString()));
			}
		}
		return offWrtClientMap;
	}

	/*
	 * Anoop 12-10-2013 Get up coming 7 days birthday list
	 */
	public ArrayList<ArrayList<String>> getUpComingSevenDaysBirthday(String userName, SessionFactory connectionSpace, String firstDate, String secondDate)
			throws Exception
	{
		ArrayList<ArrayList<String>> list = null;
		StringBuilder query7 = new StringBuilder("");
		query7.append("select cbd.clientName, ccd.personName, ccd.birthday from client_basic_data as cbd, "
				+ "client_contact_data as ccd where cbd.id = ccd.clientName  " + "and (ccd.birthday >= '" + firstDate + "' and ccd.birthday <= '" + secondDate
				+ "') order by ccd.birthday ");
		// //System.out.println("Query: " + query7);

		List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

		if (dataCount7 != null && dataCount7.size() > 0)
		{
			list = new ArrayList<ArrayList<String>>();
			for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null && object[2] != null && !object[2].toString().equalsIgnoreCase(""))
				{
					ArrayList<String> data = new ArrayList<String>();
					data.add(object[0].toString());
					data.add(object[1].toString());
					data.add(DateUtil.convertDateToIndianFormat(object[2].toString()));

					list.add(data);
				}
			}
		}
		return list;
	}

	/*
	 * Anoop 12-10-2013 Get offering wrt associate count
	 */
	public ArrayList<ArrayList<String>> getUpComingSevenDaysAnniversary(String userName, SessionFactory connectionSpace, String firstDate, String secondDate)
			throws Exception
	{
		ArrayList<ArrayList<String>> list = null;
		StringBuilder query7 = new StringBuilder("");
		query7.append("select cbd.clientName, ccd.personName, ccd.anniversary from client_basic_data as cbd, "
				+ "client_contact_data as ccd where cbd.id = ccd.clientName  " + "and (ccd.anniversary >= '" + firstDate + "' and ccd.anniversary <= '" + secondDate
				+ "') order by ccd.anniversary ");
		// //System.out.println("Query: " + query7);

		List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

		if (dataCount7 != null && dataCount7.size() > 0)
		{
			list = new ArrayList<ArrayList<String>>();
			for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null && object[2] != null && !object[2].toString().equalsIgnoreCase(""))
				{
					ArrayList<String> data = new ArrayList<String>();
					data.add(object[0].toString());
					data.add(object[1].toString());
					data.add(DateUtil.convertDateToIndianFormat(object[2].toString()));

					list.add(data);
				}
			}
		}
		return list;
	}

	public Map<String, String> fetchClientStatus(SessionFactory factory, ActivityType type)
	{
		Map<String, String> map = null;

		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (type == ActivityType.client) query.append("select id, statusName from client_status order by statusName");
			else if (type == ActivityType.associate) query.append("select id, statusname from associatestatus order by statusname");

			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				map = new LinkedHashMap<String, String>();
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					if (data[1] != null) map.put(data[0].toString(), data[1].toString());
				}
				// //System.out.println("map.size():" + map.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();
		}
		return map;
	}

	public boolean createTableClientTakeActionMappedEmployee(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("empId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("client_takeaction_mapped_emp", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableClientTakeAction(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// create client_takeaction table
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("contacId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("statusId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("maxDateTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("to_maxDateTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			
			ob1 = new TableColumes();
			ob1.setColumnname("comment");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("isFinished");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("finishedDate");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("finishedTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("readFlag");
			ob1.setDatatype("int(10)");
			ob1.setConstraint("default 0");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("sales_stages");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("closingComment");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("compelling_reason");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			
			flag = cbt.createTable22("client_takeaction", Tablecolumesaaa, factory);
			// end creating client_takeaction table
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public Map<String, String> fetchLostStatus(SessionFactory factory)
	{
		Map<String, String> map = new HashMap<String, String>();

		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, lostReason from lostoportunity where lostOpportunityFor = '0'");

			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					if (data[1] != null) map.put(data[0].toString(), data[1].toString());
				}
				// //System.out.println("map.size():" + map.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	/*public T fetchEmployee(SessionFactory factory, EmployeeReturnType type)
	{

		T objectContainer = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, empName from employee_basic ");
			query
			.append("select eb.id, eb.empName from employee_basic as eb inner join groupinfo as gi on gi.id = eb.groupId and gi.groupName = 'Employee' order by eb.empName ");

			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				if (type == EmployeeReturnType.map)
				{
					objectContainer = (T) new EmployeeHelper().fetchEmpIdAndEmpName();
					// //System.out.println("map.size():" + map.size());
				}
				else if (type == EmployeeReturnType.jsonArray)
				{
					JSONArray jsonArray = new JSONArray();
					for (Object obj : list)
					{
						Object[] object = (Object[]) obj;
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("ID", object[0].toString());
						jsonObject.put("NAME", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

						jsonArray.add(jsonObject);
					}
					objectContainer = (T) jsonArray;
					// //System.out.println("jsonArray.size():" + jsonArray.size());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();
		}
		return objectContainer;
	}*/

	public boolean createTableClientBasicData(SessionFactory factory, String accountID)
	{
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_client_configuration", accountID, factory, "", 0, "table_name",
				"client_basic_configuration");
		boolean flag = false;
		if (statusColName != null)
		{
			// create table query based on configuration
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
			}
			flag = cbt.createTable22("client_basic_data", Tablecolumesaaa, factory);
		}
		return flag;
	}

	public boolean createTableClientOfferingMapping(SessionFactory factory)
	{
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

		TableColumes ob1 = new TableColumes();
		ob1.setColumnname("clientName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("offeringId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("offeringLevelId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("userName");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("date");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("time");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("isConverted");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("convertDate");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("convertTime");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("poNumber");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("poDate");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("offering_price");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("po_attachment");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("comment");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("RCA");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("CAPA");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("lostId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("opportunity_name");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("opportunity_value");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("closure_date");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		return cbt.createTable22("client_offering_mapping", Tablecolumesaaa, factory);
	}

	public boolean createTableClientContactData(SessionFactory factory, String accountID)
	{
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_client_configuration", accountID, factory, "", 0, "table_name",
				"client_contact_configuration");
		boolean flag = false;
		if (statusColName != null)
		{
			// create table query based on configuration
			List<TableColumes> Tablecolumesaaa1 = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa1.add(ob1);
			}
			flag = cbt.createTable22("client_contact_data", Tablecolumesaaa1, factory);
		}
		return flag;
	}

	public int calculateWeightage(Integer id, SessionFactory connectionSpace)
	{
		int sum = 0;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			query.append("cbd.id,cbd.industry,cbd.subIndustry,com.offeringId,ccd.personName,ois.deptName,ois.weightage ");
			query.append("from ");
			query.append("client_basic_data as cbd ");
			query.append("inner join ");
			query.append("client_offering_mapping as com on ");
			query.append("com.clientName=cbd.id ");
			query.append("inner join ");
			query.append("client_contact_data as ccd ");
			query.append("on ccd.clientName=com.clientName ");
			query.append("inner join ");
			query.append("off_indust_subindust_dept_mapping as ois ");
			query.append("on ois.offeringId=com.offeringId ");
			query.append("where cbd.id='" + id + "' ");
			query.append("and ");
			query.append("cbd.industry !='null' ");
			query.append("and ");
			query.append("cbd.subIndustry != 'null'");
			String resultSet = query.toString();
			// //System.out.println("resultSet:"+resultSet);
			List data = cbt.executeAllSelectQuery(resultSet, connectionSpace);
			StringBuilder qcal = new StringBuilder();

			if (data != null && data.size() > 0)
			{
				for (Object obj : data)
				{
					Object[] dt = (Object[]) obj;
					String[] sumweightage = dt[6].toString().split(",");
					for (int i = 0; i < sumweightage.length; i++)
					{
						sum += Integer.parseInt(sumweightage[i]);
					}
					// qcal.append("select sum('"+dt[6].toString()+"') from dt");
					// ////System.out.println("qcal :"+qcal);
				}
			}
			//System.out.println("sum   : " + sum);
			//System.out.println("resultSet :" + resultSet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sum;
	}

	public List<String> getClientBasicDataToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("clientName");
		listDataToShow.add("starRating");
		listDataToShow.add("industry");
		listDataToShow.add("subIndustry");
		listDataToShow.add("location");
		listDataToShow.add("sourceMaster");
		listDataToShow.add("mobileNo");
		listDataToShow.add("webAddress");
		listDataToShow.add("companyEmail");
		listDataToShow.add("weightage");
		listDataToShow.add("opportunity_brief");
		listDataToShow.add("closure_date");
		listDataToShow.add("forecast_category");
		listDataToShow.add("opportunity_value");
		listDataToShow.add("sales_stages");
		listDataToShow.add("acManager");
		listDataToShow.add("weightage_targetsegment");
		return listDataToShow;
	}
	public List<String> getClientContactDataToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("personName");
		listDataToShow.add("designation");
		listDataToShow.add("department");
		listDataToShow.add("communicationName");
		listDataToShow.add("userName");
		listDataToShow.add("date");
		listDataToShow.add("time");
		listDataToShow.add("degreeOfInfluence ");
		listDataToShow.add("contactNo");
		listDataToShow.add("emailOfficial");
		listDataToShow.add("alternateMob");
		listDataToShow.add("alternateEmail");
		listDataToShow.add("location");
		listDataToShow.add("birthday");
		listDataToShow.add("anniversary");
		listDataToShow.add("currentStatus");
		return listDataToShow;
	}
	
	public String fetchRefrenceData(String clientId, SessionFactory connectionSpace)
	{
		String clientRefData = null;
		try {
			StringBuilder query = new StringBuilder("");
			query.append("select cbd2.clientName, cbd2.companyEmail  from client_basic_data as cbd ");
			query.append(" inner join client_basic_data as cbd2 on cbd2.id = cbd.refName ");
			query.append("where cbd.id = '");
			query.append(clientId);
			query.append("'");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for(Iterator it=dataList.iterator(); it.hasNext();)
				{
					 Object[] obdata=(Object[])it.next();
					 if(obdata[0] == null){obdata[0] = "NA";}
					 if(obdata[1] == null){obdata[1] = "NA";}
					 clientRefData = obdata[0].toString()+"#"+obdata[1].toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clientRefData;
	}
	public String fetchClientOfferingName(String clientId,String offeringId, SessionFactory connectionSpace)
	{
		String clientOfferingName = null;
		String offeringName = null;
		String clientName = null;
		try {
			StringBuilder query = new StringBuilder("");
			query.append("select subofferingname from offeringlevel3 ");
			query.append("where id = '");
			query.append(offeringId);
			query.append("'");
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				 if(dataList.get(0) != null)
				 {
					 offeringName  = dataList.get(0).toString();
				}
				
			}
			StringBuilder query2 = new StringBuilder("");
			query2.append("select clientName from client_basic_data ");
			query2.append("where id = '");
			query2.append(clientId);
			query2.append("'");
			List dataList2 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			if(dataList2!=null && dataList2.size()>0)
			{
				 if(dataList.get(0) != null)
				 {
					 clientName = dataList.get(0).toString();
				 }
			}
			if(offeringName == null){offeringName="NA";}
			if(offeringName == null){offeringName="NA";}
			clientOfferingName = offeringName+"#"+clientName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clientOfferingName;
	}
	
	public boolean createTableClientPreventiveTakeAction(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// create client_takeaction table
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("contacId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("statusId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("maxDateTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("to_maxDateTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			
			ob1 = new TableColumes();
			ob1.setColumnname("comment");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("isFinished");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("finishedDate");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("finishedTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("client_contact_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("readFlag");
			ob1.setDatatype("int(10)");
			ob1.setConstraint("default 0");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("sales_stages");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("closingComment");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("compelling_reason");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			
			flag = cbt.createTable22("patient_takeaction", Tablecolumesaaa, factory);
			// end creating client_takeaction table
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public String mail_text(String name,String trace)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(name) + ",</b>");
		mailtext.append("<br><br> please visit http://115.112.184.21:8080/over2cloud/view/Over2Cloud/questionairOver2Cloud/feedbackForm.action?setNo=A&traceid="+trace+" for giving your valuable feedback ");
		mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><br Thanks !!!<br><br></FONT>");
		return mailtext.toString();
	}

	
	
}
