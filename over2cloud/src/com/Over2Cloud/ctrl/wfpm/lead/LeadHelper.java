package com.Over2Cloud.ctrl.wfpm.lead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.dashboard.ActivityType;
import com.sun.org.apache.bcel.internal.generic.RET;

public class LeadHelper
{
	public JSONArray getAllLead(SessionFactory factory, String userName)
	{
		JSONArray jsonArray = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			//query.append("select id, leadName from leadgeneration where id not in (select leadName from lead_contact_data) and userName = '");
			query.append("select id, leadName from leadgeneration where id not in (select id from lead_contact_data) and userName = '");
			query.append(userName);
			query.append("' order by leadName ");
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				jsonArray = new JSONArray();
				JSONObject jsonObject = null;
				for (Object obj : list)
				{
					Object[] object = (Object[]) obj;
					jsonObject = new JSONObject();
					jsonObject.put("ID", object[0].toString());
					jsonObject.put("NAME", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

					jsonArray.add(jsonObject);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// //System.out.println("jsonArray.size():" + jsonArray.size());
		return jsonArray;
	}

	public boolean createTableForLeadContact(SessionFactory factory, String accountID)
	{
		boolean flag = false;

		List<GridDataPropertyView> list = Configuration.getConfigurationData("mapped_lead_generation", accountID, factory, "", 0, "table_name",
				"lead_contact_configuration");

		if (list != null)
		{
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();

			for (GridDataPropertyView gdp : list)
			{
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn.add(ob1);
			}

			CommonOperInterface coi = new CommonConFactory().createInterface();
			flag = coi.createTable22("lead_contact_data", tableColumn, factory);

		}
		return flag;
	}

	public int addDataToLeadContact(SessionFactory factory, String accountID, HttpServletRequest request, String userName, String id)
	{
		//System.out.println("2222222222222222222");
		int dataCount = 0;
		// create table 'lead_contact_data' for lead contact dynamically
		//new LeadHelper().createTableForLeadContact(factory, accountID);

		//System.out.println("33333333333333333333333333");
		// store data to table
		
		
		
		
		
		
		
		
		
		
		
		
		boolean status = false;
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
		Collections.sort(requestParameterNames);
		Iterator it = requestParameterNames.iterator();
		LinkedHashMap<String, String[]> listValues = new LinkedHashMap<String, String[]>();
		String leadName = null;
		int dataSize = 0;
		boolean oneTime = true;

		while (it.hasNext())
		{
			String Parmname = it.next().toString();
			String[] paramValue = request.getParameterValues(Parmname);
			if (paramValue != null && Parmname != null)
			{
				if (Parmname.equalsIgnoreCase("leadName")) leadName = paramValue[0];
				else
				{
					//System.out.println("param name======"+Parmname+"param value************"+paramValue.toString());
					listValues.put(Parmname, paramValue);
					if (oneTime)
					{
						dataSize = paramValue.length;
						oneTime = false;
					}
				}
			}
		}
		
		//System.out.println("444444444444444444444444444444");

		if (listValues != null && listValues.size() > 0)
		{
			for (int i = 0; i < dataSize; i++)
			{
				//boolean forFlag = false;
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				for (Map.Entry<String, String[]> entry : listValues.entrySet())
				{
					/*if (entry.getKey().trim().equalsIgnoreCase("contactNo") && (entry.getValue()[i] == null || entry.getValue()[i].trim().equals("")))
					{
						forFlag = true;
						break;
					}*/

					ob = new InsertDataTable();
					ob.setColName(entry.getKey().trim());
					ob.setDataName((entry.getValue()[i] == null || entry.getValue()[i].trim().equals("")) ? "NA" : entry.getValue()[i].trim());
					insertData.add(ob);
				}

				//if (forFlag) continue;

				ob = new InsertDataTable();
				ob.setColName("leadName");
				ob.setDataName(leadName);
				insertData.add(ob);

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
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				status = checkForInsertOrUpdate(insertData, factory, id);
				if (status) dataCount++;
			}
		}

		return dataCount;
	}

	private boolean checkForInsertOrUpdate(List<InsertDataTable> insertData, SessionFactory factory, String id)
	{
		boolean flag = false;
		if (insertData == null || insertData.size() < 1) return false;

		CommonOperInterface coi = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("select id from lead_contact_data where leadName = '");
		query.append(id);
		query.append("'");
		int count = 0;

		List list = coi.executeAllSelectQuery(query.toString(), factory);
		if (list != null && list.size() > 0)
		{
			// String contactId = list.get(0).toString();
			Map<String, Object> wherClause = new HashMap<String, Object>();
			for (InsertDataTable idt : insertData)
			{
				wherClause.put(idt.getColName(), idt.getDataName());
			}
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			condtnBlock.put("leadName", id);
			flag = coi.updateTableColomn("lead_contact_data", wherClause, condtnBlock, factory);
		}
		else
		{
			flag = coi.insertIntoTable("lead_contact_data", insertData, factory);
		}

		return flag;
	}

	public List<String> getLeadBasicToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("leadName");
		listDataToShow.add("starRating");
		listDataToShow.add("industry");
		listDataToShow.add("subIndustry");
		listDataToShow.add("name");
		listDataToShow.add("sourceName");
		return listDataToShow;
	}

	public List<String> getLeadContactToShow()
	{
		List<String> listContactDataToShow = new ArrayList<String>();
		listContactDataToShow.add("personName");
		listContactDataToShow.add("contactNo");
		listContactDataToShow.add("emailOfficialContact");
		return listContactDataToShow;
	}

	public JSONArray fetchSubIndustry(SessionFactory factory, String id, LeadData leadData)
	{
		JSONArray jsonArray = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, subIndustry from subindustrydatalevel2 ");
			if (leadData == LeadData.ById)
			{
				query.append("where industry = '");
				query.append(id);
				query.append("'");
			}

			// //System.out.println("Query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				jsonArray = new JSONArray();
				JSONObject jsonObject = null;
				for (Object obj : list)
				{
					Object[] object = (Object[]) obj;
					jsonObject = new JSONObject();
					jsonObject.put("ID", object[0].toString());
					jsonObject.put("NAME", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

					jsonArray.add(jsonObject);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jsonArray;
	}

	public void fetchConfigurationForLead(String mappedTable, String accountID, SessionFactory connectionSpace, String configurationTable,
			List<GridDataPropertyView> listData, boolean isWithId)
	{

		GridDataPropertyView gpv = null;
		if (isWithId)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			listData.add(gpv);
		}

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name",
				configurationTable);
		for (GridDataPropertyView gdp : returnResult)
		{
			if (gdp.getColomnName().equalsIgnoreCase("userName") || gdp.getColomnName().equalsIgnoreCase("date") || gdp.getColomnName().equalsIgnoreCase("time")) continue;

			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setWidth(gdp.getWidth());
			gpv.setAlign(gdp.getAlign());
			if (gdp.getFormatter() != null && !gdp.getFormatter().equalsIgnoreCase(""))
			{
				gpv.setFormatter(gdp.getFormatter());
			}

			listData.add(gpv);
		}
	}

	public Map<String, String> leadBasicFullView(SessionFactory connectionSpace, String id, String accountID)
	{
		LinkedHashMap<String, String> leadMap = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> leadBasicList = new ArrayList<GridDataPropertyView>();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			StringBuilder query2 = new StringBuilder("");
			StringBuilder query3 = new StringBuilder("");

			// Lead view page
			new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, connectionSpace, "lead_generation", leadBasicList, false);
			if (leadBasicList != null && leadBasicList.size() > 0)
			{
				for (Iterator<GridDataPropertyView> itr = leadBasicList.iterator(); itr.hasNext();)
				{
					GridDataPropertyView gdp = itr.next();
					if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
					{
						query.append("sm.");
						query.append(gdp.getColomnName());
						query.append(", ");
						query2.append("left join sourcemaster as sm on sm.id = ld.sourceName ");
					}
					else if (gdp.getColomnName().equalsIgnoreCase("industry"))
					{
						query.append("ind.");
						query.append(gdp.getColomnName());
						query.append(", ");
						query2.append("left join industrydatalevel1 as ind on ind.id = ld.industry ");
					}
					else if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
					{
						query.append("sind.");
						query.append(gdp.getColomnName());
						query.append(", ");
						query2.append("left join subindustrydatalevel2 as sind on sind.id = ld.subIndustry ");
					}
					else if (gdp.getColomnName().equalsIgnoreCase("name"))
					{
						query.append("loc.");
						query.append(gdp.getColomnName());
						query.append(", ");
						query2.append("left join location as loc on loc.id = ld.name ");
					}
					else
					{
						if (gdp.getColomnName().equalsIgnoreCase("leadName"))
						{
							itr.remove();
							continue;
						}
						query.append("ld.");
						query.append(gdp.getColomnName());
						query.append(", ");
					}
				}

				query3.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
				query3.append(" ");
				query3.append("from leadgeneration as ld ");
				query3.append(query2.toString());

				query3.append("where ld.id=");
				query3.append(id);

				// //System.out.println("query3:" + query3);
				List data = coi.executeAllSelectQuery(query3.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					Object[] obdata = (Object[]) data.get(0);
					for (int k = 0; k < obdata.length; k++)
					{
						if (obdata[k] != null)
						{
							leadMap.put(leadBasicList.get(k).getHeadingName(), obdata[k].toString());
						}
					}

					/*
					 * for (Entry<String, String> entry : leadMap.entrySet())
					 * //System.out.println(entry.getKey() + "  :  " + entry.getValue());
					 */
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return leadMap;
	}

	public Map<String, String> leadContactFullView(SessionFactory connectionSpace, String id, String accountID)
	{

		LinkedHashMap<String, String> leadMap = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> leadContactList = new ArrayList<GridDataPropertyView>();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			StringBuilder query3 = new StringBuilder("");

			new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, connectionSpace, "lead_contact_configuration", leadContactList, false);

			for (Iterator itr = leadContactList.iterator(); itr.hasNext();)
			{
				GridDataPropertyView gdp = (GridDataPropertyView) itr.next();
				if (gdp.getColomnName().equalsIgnoreCase("leadName"))
				{
					itr.remove();
					continue;
				}

				if (gdp.getColomnName().equalsIgnoreCase("department"))
				{
					query.append("dept.deptName 'dName',");
				}
				else
				{
					query.append("lc.");
					query.append(gdp.getColomnName());
					query.append(", ");
				}
			}

			query3.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
			query3.append(" ");
			query3.append("from lead_contact_data as lc inner join leadgeneration as ld on ld.id = lc.leadName ");
			query3.append(" left join department as dept on dept.id = lc.department ");
			query3.append("where lc.leadName =");
			query3.append(id);
			// //System.out.println("query3:::" + query3);

			List data = coi.executeAllSelectQuery(query3.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				Object[] obdata = (Object[]) data.get(0);
				for (int k = 0; k < obdata.length; k++)
				{
					if (obdata[k] != null)
					{
						leadMap.put(leadContactList.get(k).getHeadingName(), obdata[k].toString());
					}
				}

				/*
				 * for (Entry<String, String> entry : leadMap.entrySet())
				 * //System.out.println(entry.getKey() + "  :  " + entry.getValue());
				 */
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return leadMap;
	}

	public Map<String, String> fetchIndustryList(SessionFactory factory)
	{
		Map<String, String> industryList = new LinkedHashMap<String, String>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List offeringData = coi.executeAllSelectQuery("select id, industry from industrydatalevel1 order by industry", factory);
		if (offeringData != null && offeringData.size() > 0)
		{
			for (Object c : offeringData)
			{
				Object[] dataC = (Object[]) c;
				industryList.put(dataC[0].toString(), dataC[1].toString());
			}
		}
		return industryList;
	}
	public Map<String, String> fetchAllweightageTargetSegment(SessionFactory connectionSpace)
	{
		Map<String, String> weightage = new LinkedHashMap<String, String>();
		try
		{   CommonOperInterface coi = new CommonConFactory().createInterface();
			List weightageMasterData = coi.executeAllSelectQuery("select id, weightageName from weightagemaster order by weightageName", connectionSpace);
			if (weightageMasterData != null && !weightageMasterData.isEmpty())
			{
				for (Object c : weightageMasterData)
				{
					Object[] dataC = (Object[]) c;
					weightage.put(dataC[0].toString(), dataC[1].toString());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return weightage;
	}
	
	

	public Map<String, String> fetchSourceNameList(SessionFactory factory)
	{
		Map<String, String> sourceList = new LinkedHashMap<String, String>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List offeringData = coi.executeAllSelectQuery("select id, sourceName from sourcemaster order by sourceName", factory);
		if (offeringData != null && offeringData.size() > 0)
		{
			for (Object c : offeringData)
			{
				Object[] dataC = (Object[]) c;
				sourceList.put(dataC[0].toString(), dataC[1].toString());
			}
		}
		return sourceList;
	}

	public Map<String, String> fetchLocationList(SessionFactory factory)
	{
		Map<String, String> locationList = new LinkedHashMap<String, String>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List offeringData = coi.executeAllSelectQuery("select id, name from location order by name", factory);
		if (offeringData != null && offeringData.size() > 0)
		{
			for (Object c : offeringData)
			{
				Object[] dataC = (Object[]) c;
				locationList.put(dataC[0].toString(), dataC[1].toString());
			}
		}
		return locationList;
	}
	public Map<String, String> fetchWeightageList(SessionFactory factory)
	{
		Map<String, String> weightageList = new LinkedHashMap<String, String>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List offeringData = coi.executeAllSelectQuery("select id, weightageName from weightagemaster order by weightageName", factory);
		if (offeringData != null && offeringData.size() > 0)
		{
			for (Object c : offeringData)
			{
				Object[] dataC = (Object[]) c;
				weightageList.put(dataC[0].toString(), dataC[1].toString());
			}
		}
		return weightageList;
	}

	public ArrayList<ArrayList<String>> fetchLeadBasicForTakeAction(SessionFactory factory, String accountID, String id, StringBuilder subIndId)
	{
		ArrayList<ArrayList<String>> leadList = new ArrayList<ArrayList<String>>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("select leadName, leadAddress, phoneNo, webAddress, industry, subIndustry from leadgeneration where id = ");
		query.append(id);
		List list = coi.executeAllSelectQuery(query.toString(), factory);
		if (list != null && list.size() > 0)
		{
			Map<String, String> tempMap = new HashMap<String, String>();
			List<GridDataPropertyView> leadBasicList = new ArrayList<GridDataPropertyView>();
			// fetch lead configuration
			new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, factory, "lead_generation", leadBasicList, false);
			if (leadBasicList != null && leadBasicList.size() > 0)
			{
				for (GridDataPropertyView gdp : leadBasicList)
				{
					if (gdp.getColomnName().equalsIgnoreCase("leadName") || gdp.getColomnName().equalsIgnoreCase("leadAddress")
							|| gdp.getColomnName().equalsIgnoreCase("phoneNo") || gdp.getColomnName().equalsIgnoreCase("webAddress")
							|| gdp.getColomnName().equalsIgnoreCase("industry") || gdp.getColomnName().equalsIgnoreCase("subIndustry"))
					{
						tempMap.put(gdp.getColomnName(), gdp.getHeadingName());
					}
				}
			}

			Object[] dataC = (Object[]) list.get(0);
			ArrayList<String> aList = null;
			if (dataC[0] != null && tempMap.containsKey("leadName"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("leadName"));
				aList.add("leadName");
				aList.add(dataC[0].toString());
				leadList.add(aList);
			}
			if (dataC[1] != null && tempMap.containsKey("leadAddress"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("leadAddress"));
				aList.add("leadAddress");
				aList.add(dataC[1].toString());
				leadList.add(aList);
			}
			if (dataC[2] != null && tempMap.containsKey("phoneNo"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("phoneNo"));
				aList.add("phoneNo");
				aList.add(dataC[2].toString());
				leadList.add(aList);
			}
			if (dataC[3] != null && tempMap.containsKey("webAddress"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("webAddress"));
				aList.add("webAddress");
				aList.add(dataC[3].toString());
				leadList.add(aList);
			}
			if (dataC[4] != null && tempMap.containsKey("industry"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("industry"));
				aList.add("industry");
				aList.add(dataC[4].toString());
				leadList.add(aList);
			}
			if (dataC[5] != null && tempMap.containsKey("subIndustry"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("subIndustry"));
				aList.add("subIndustry");
				aList.add(dataC[5].toString());
				leadList.add(aList);
				subIndId.append(dataC[5].toString());
			}
		}
		return leadList;
	}

	public ArrayList<ArrayList<String>> fetchLeadContactForTakeAction(SessionFactory factory, String accountID, String id, StringBuilder subIndId)
	{
		ArrayList<ArrayList<String>> leadList = new ArrayList<ArrayList<String>>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("select lcd.personName, dept.deptName, lcd.contactNo, lcd.anniversary, lcd.emailOfficialContact, lcd.degreeOfInfluence, lcd.communicationName, lcd.birthday,lcd.designation ");
		query.append("from lead_contact_data as lcd left join department as dept on lcd.department = dept.id where lcd.leadName = '");
		query.append(id);
		query.append("' ");

		List list = coi.executeAllSelectQuery(query.toString(), factory);
		if (list != null && list.size() > 0)
		{
			Map<String, String> tempMap = new HashMap<String, String>();
			List<GridDataPropertyView> leadContactList = new ArrayList<GridDataPropertyView>();
			// fetch lead contact configuration
			new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, factory, "lead_contact_configuration", leadContactList, false);
			if (leadContactList != null && leadContactList.size() > 0)
			{
				for (GridDataPropertyView gdp : leadContactList)
				{
					if (gdp.getColomnName().equalsIgnoreCase("personName") || gdp.getColomnName().equalsIgnoreCase("department")
							|| gdp.getColomnName().equalsIgnoreCase("contactNo") || gdp.getColomnName().equalsIgnoreCase("anniversary")
							|| gdp.getColomnName().equalsIgnoreCase("emailOfficialContact") || gdp.getColomnName().equalsIgnoreCase("degreeOfInfluence")
							|| gdp.getColomnName().equalsIgnoreCase("communicationName") || gdp.getColomnName().equalsIgnoreCase("birthday")
							|| gdp.getColomnName().equalsIgnoreCase("designation"))
					{
						tempMap.put(gdp.getColomnName(), gdp.getHeadingName());
					}
				}
			}

			Object[] dataC = (Object[]) list.get(0);
			ArrayList<String> aList = null;
			if (dataC[0] != null && tempMap.containsKey("personName"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("personName"));
				aList.add("personName");
				aList.add(dataC[0].toString());
				leadList.add(aList);
			}
			if (dataC[1] != null && tempMap.containsKey("department"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("department"));
				aList.add("department");
				aList.add(dataC[1].toString());
				leadList.add(aList);
			}
			if (dataC[2] != null && tempMap.containsKey("contactNo"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("contactNo"));
				aList.add("contactNo");
				aList.add(dataC[2].toString());
				leadList.add(aList);
			}
			if (dataC[3] != null && tempMap.containsKey("anniversary"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("anniversary"));
				aList.add("anniversary");
				aList.add(dataC[3].toString());
				leadList.add(aList);
			}
			if (dataC[4] != null && tempMap.containsKey("emailOfficialContact"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("emailOfficialContact"));
				aList.add("emailOfficialContact");
				aList.add(dataC[4].toString());
				leadList.add(aList);
			}
			if (dataC[5] != null && tempMap.containsKey("degreeOfInfluence"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("degreeOfInfluence"));
				aList.add("degreeOfInfluence");
				aList.add(dataC[5].toString());
				leadList.add(aList);
				subIndId.append(dataC[5].toString());
			}
			if (dataC[6] != null && tempMap.containsKey("communicationName"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("communicationName"));
				aList.add("communicationName");
				aList.add(dataC[6].toString());
				leadList.add(aList);
			}
			if (dataC[7] != null && tempMap.containsKey("birthday"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("birthday"));
				aList.add("birthday");
				aList.add(dataC[7].toString());
				leadList.add(aList);
			}
			if (dataC[8] != null && tempMap.containsKey("designation"))
			{
				aList = new ArrayList<String>();
				aList.add(tempMap.get("designation"));
				aList.add("designation");
				aList.add(dataC[8].toString());
				leadList.add(aList);
			}

		}
		return leadList;
	}

	public Map<String, String> fetchLeadBasicColumnAndValueMap(SessionFactory factory, String accountID, String id)
	{

		LinkedHashMap<String, String> leadMap = new LinkedHashMap<String, String>();
		try
		{
			List<GridDataPropertyView> leadBasicList = new ArrayList<GridDataPropertyView>();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select ");
			StringBuilder query2 = new StringBuilder("");
			StringBuilder query3 = new StringBuilder("");

			// Lead view page
			new LeadHelper().fetchConfigurationForLead("mapped_lead_generation", accountID, factory, "lead_generation", leadBasicList, false);
			if (leadBasicList != null && leadBasicList.size() > 0)
			{
				for (Iterator<GridDataPropertyView> itr = leadBasicList.iterator(); itr.hasNext();)
				{
					GridDataPropertyView gdp = itr.next();
					if (gdp.getColomnName().equalsIgnoreCase("userName") || gdp.getColomnName().equalsIgnoreCase("date") || gdp.getColomnName().equalsIgnoreCase("time"))
					{
						itr.remove();
						continue;
					}
					else
					{
						query.append("ld.");
						query.append(gdp.getColomnName());
						query.append(", ");
					}
				}

				query3.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
				query3.append(" ");
				query3.append("from leadgeneration as ld ");
				query3.append(query2.toString());

				query3.append("where ld.id=");
				query3.append(id);

				// //System.out.println("query3:" + query3);
				List data = coi.executeAllSelectQuery(query3.toString(), factory);
				if (data != null && data.size() > 0)
				{
					Object[] obdata = (Object[]) data.get(0);
					for (int k = 0; k < obdata.length; k++)
					{
						if (obdata[k] != null)
						{
							leadMap.put(leadBasicList.get(k).getColomnName(), obdata[k].toString());
						}
					}

					/*
					 * for (Entry<String, String> entry : leadMap.entrySet())
					 * //System.out.println(entry.getKey() + "  :  " + entry.getValue());
					 */
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return leadMap;
	}
	
	public String fetchDeptIdForName(String deptname, SessionFactory factory)
	{
		String depId=null;
		CommonOperInterface coi = new CommonConFactory().createInterface();
		String query = "select id from department where deptName = '"+deptname+"'";
		List dataList = coi.executeAllSelectQuery(query, factory);
		if(dataList!=null && dataList.size()>0)
		{ 
			depId = String.valueOf(dataList.get(0).toString());
			
		}
		
		return depId;
	}
}
