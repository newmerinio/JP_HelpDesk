package com.Over2Cloud.ctrl.wfpm.associate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;

public class AssociateHelper
{
	CommonOperInterface	cbt		= new CommonConFactory().createInterface();
	int									level	= 0;

	public int getProspectiveAssociateCount(String userName, SessionFactory connectionSpace) throws Exception
	{
		int pendingAssociate = 0;
		StringBuilder query1 = new StringBuilder("");
		query1.append("select count(*) from associate_basic_data where id in (" + "select distinct(associateName) from associate_offering_mapping "
				+ "where isConverted = '0') and userName = '" + userName + "'");
		List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
		if (dataCount != null && dataCount.size() > 0)
		{
			for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					pendingAssociate = Integer.parseInt(object.toString());
				}
			}
		}
		return pendingAssociate;
	}

	public int getExistingAssociateCount(String userName, SessionFactory connectionSpace) throws Exception
	{
		int existingClient = 0;
		StringBuilder query2 = new StringBuilder("");
		query2.append("select count(*) from associate_basic_data where id in " + "(select distinct(associateName) from associate_offering_mapping "
				+ "where isConverted = '1') and userName = '" + userName + "'");
		List dataCount2 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
		if (dataCount2 != null && dataCount2.size() > 0)
		{
			for (Iterator iterator = dataCount2.iterator(); iterator.hasNext();)
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

	public int getLostAssociateCount(String userName, SessionFactory connectionSpace) throws Exception
	{
		int lostAssociate = 0;
		StringBuilder query3 = new StringBuilder("");
		query3.append("select count(*) from associate_basic_data where id in " + "(select distinct(associateName) from associate_offering_mapping "
				+ "where isConverted = '2') and userName = '" + userName + "'");
		List dataCount3 = cbt.executeAllSelectQuery(query3.toString(), connectionSpace);
		if (dataCount3 != null && dataCount3.size() > 0)
		{
			for (Iterator iterator = dataCount3.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					lostAssociate = Integer.parseInt(object.toString());
				}
			}
		}
		return lostAssociate;
	}

	public int getTodaysActivityCount(String userName, SessionFactory connectionSpace, String tableName) throws Exception
	{
		int todayActivity = 0;
		StringBuilder query4 = new StringBuilder("");
		query4.append("select count(*) from associate_contact_data as ccd, associate_takeaction as cta, " + "associatestatus as cs, " + tableName
				+ " as co, associate_basic_data as cbd  " + "where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id  "
				+ "and cbd.id = ccd.associateName and cbd.userName IN(" + userName + ") and cta.isFinished = '0'  and cta.maxDateTime like '"
				+ DateUtil.getCurrentDateUSFormat() + "%' ");
		System.out.println("today  "+query4);
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

	public int getTomorrowsActivityCount(String userName, SessionFactory connectionSpace, String tableName) throws Exception
	{
		int tomorrowActivity = 0;
		StringBuilder query5 = new StringBuilder("");
		query5.append("select count(*) from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, " + tableName + " as co, "
				+ "associate_basic_data as cbd  where ccd.id = cta.contacId and cta.statusId = cs.id "
				+ "and cta.offeringId = co.id  and cbd.id = ccd.associateName and cbd.userName IN(" + userName + ") " + "and isFinished = '0' and maxDateTime like '"
				+ DateUtil.getNextDateAfter(1) + "%' ");
		System.out.println("tomorrow   "+query5);
		List dataCount5 = cbt.executeAllSelectQuery(query5.toString(), connectionSpace);
		if (dataCount5 != null && dataCount5.size() > 0)
		{
			for (Iterator iterator = dataCount5.iterator(); iterator.hasNext();)
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
		StringBuilder query6 = new StringBuilder("");
		query6.append("select count(*) from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, " + tableName + " as co, "
				+ "associate_basic_data as cbd  where ccd.id = cta.contacId and cta.statusId = cs.id "
				+ "and cta.offeringId = co.id  and cbd.id = ccd.associateName and cbd.userName IN(" + userName + ") " + "and isFinished = '0' and maxDateTime >= '"
				+ todayDate + "' " + "and maxDateTime <= '" + DateUtil.getNextDateAfter(7) + " 23:59' ");
		List dataCount6 = cbt.executeAllSelectQuery(query6.toString(), connectionSpace);
		if (dataCount6 != null && dataCount6.size() > 0)
		{
			for (Iterator iterator = dataCount6.iterator(); iterator.hasNext();)
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

	public int getMissedActivityCount(String userName, SessionFactory connectionSpace) throws Exception
	{
		int missedActivity = 0;
		StringBuilder query6 = new StringBuilder("");
		query6.append("select count(cbd.id) from associate_basic_data as cbd, associate_contact_data as ccd ,"
				+ "associate_takeaction as cta where cbd.id = ccd.associateName and ccd.id = cta.contacId " + "and cta.maxDateTime <= '"
				+ DateUtil.getCurrentDateUSFormat() + " 00:00' and isFinished = '0' " + "and cbd.userName IN(" + userName + ") ");
		System.out.println("missed activeity    "+query6);
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

	public ArrayList<ArrayList<String>> getAssociateCountByStatus(String userName, SessionFactory connectionSpace) throws Exception
	{
		ArrayList<ArrayList<String>> associateStatusAllList = new ArrayList<ArrayList<String>>();
		StringBuilder query7 = new StringBuilder("");
		query7.append("select cs.id, cs.statusname, count(distinct(cbd.id)) from associatestatus as cs, " + "associate_takeaction as cta , "
				+ "associate_contact_data as ccd, associate_basic_data as cbd, " + "associate_offering_mapping as com where cbd.id = ccd.associateName "
				+ "and ccd.id = cta.contacId and cbd.userName IN(" + userName + ") and cta.statusId = cs.id and com.associateName = cbd.id "
				+ "and com.isConverted = 0 and cta.isFinished = '0' " + "group by cta.statusId order by cs.statusname ");
		System.out.println("associatestatus   "+query7);
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

				associateStatusAllList.add(al);
			}
		}
		return associateStatusAllList;
	}

	/*
	 * Anoop 12-10-2013 Get offering wrt associate count
	 */
	public HashMap<String, Integer> getOfferingWrtAssociateCount(String userName, SessionFactory connectionSpace, String tableName, String colName)
			throws Exception
	{
		HashMap<String, Integer> offWrtAssoMap = new HashMap<String, Integer>();
		StringBuilder query7 = new StringBuilder("");
		query7.append("select off." + colName + ", count(distinct(cbd.associateName)) " + "from " + tableName + " as off, associate_basic_data as cbd, "
				+ "associate_offering_mapping as com where cbd.id = com.associateName " + "and com.offeringId = off.id and com.isConverted = '1' "
				+ "and cbd.userName IN("+userName+") group by com.offeringId ");
		System.out.println("query7  >   >   >  >   "+query7);
		List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

		if (dataCount7 != null && dataCount7.size() > 0)
		{
			for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] == null || object[1] == null) continue;

				offWrtAssoMap.put(object[0].toString(), Integer.parseInt(object[1].toString()));
			}
		}
		return offWrtAssoMap;
	}

	/*
	 * Anoop 12-10-2013 Get up coming 7 days birthday list
	 */
	public ArrayList<ArrayList<String>> getUpComingSevenDaysBirthday(String userName, SessionFactory connectionSpace, String firstDate, String secondDate)
			throws Exception
	{
		ArrayList<ArrayList<String>> list = null;
		StringBuilder query7 = new StringBuilder("");
		query7.append("select cbd.associateName, ccd.name, ccd.birthday from associate_basic_data as cbd, "
				+ "associate_contact_data as ccd where cbd.id = ccd.associateName  " + "and ccd.birthday >= '" + firstDate + "' and ccd.birthday <= '" + secondDate
				+ "' ");
		// System.out.println("Query: " + query7);

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
		query7.append("select cbd.associateName, ccd.name, ccd.anniversary from associate_basic_data as cbd, "
				+ "associate_contact_data as ccd where cbd.id = ccd.associateName  " + "and ccd.anniversary >= '" + firstDate + "' and ccd.anniversary <= '"
				+ secondDate + "' ");
		// System.out.println("Query: " + query7);

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
	
	
	
	
	
	public ArrayList<ArrayList<String>> getAssociateCountBySource(String userName, SessionFactory connectionSpace) throws Exception
	{
		ArrayList<ArrayList<String>> associateSourceAllList = new ArrayList<ArrayList<String>>();
		StringBuilder query7 = new StringBuilder("");
		query7.append("select asso.id, asso.sourceName, count(asso.id) from sourcemaster as asso, " + "associate_takeaction as ata , "
				+ "associate_contact_data as acd, associate_basic_data as abd, " + "associate_offering_mapping as aom where abd.id = acd.associateName "
				+ "and acd.id = ata.contacId and abd.userName IN(" + userName + ") and abd.sourceName = asso.id and aom.associateName = abd.id "
				+ "and aom.isConverted = 0 and ata.isFinished = '0' " + "group by asso.sourceName  ");
			System.out.println(">>>>>>>>>>>>>>>>>>>> "+query7);

			
			
			
			
			
			
			


		List dataCount8 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

		if (dataCount8 != null && dataCount8.size() > 0)
		{
			
			ArrayList<String> al = null;
			for (Iterator iterator = dataCount8.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				al = new ArrayList<String>();
				al.add(String.valueOf(object[0].toString()));
				al.add((object[1] == null || object[1].toString().equalsIgnoreCase("")) ? "NA" : object[1].toString());
				al.add(object[2].toString());
				System.out.println("al   "+al);
				
				associateSourceAllList.add(al);
				System.out.println("associateSourceAllList    "+associateSourceAllList);
			}
		}
		return associateSourceAllList;
	}
	
	
	
	
	

	public boolean createTableAssociateTakeAction(SessionFactory factory)
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
			ob1.setColumnname("associate_takeaction_id");
			ob1.setDatatype("int(10)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("closingComment");
			ob1.setDatatype("int(10)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("associate_takeaction", Tablecolumesaaa, factory);
			// end creating client_takeaction table
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean createTableAssociateTakeActionMappedEmployee(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("associate_takeaction_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("empId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("associate_takeaction_mapped_emp", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableAssociateBasicData(SessionFactory factory, String accountID)
	{
		List<GridDataPropertyView> pAssociateColName = Configuration.getConfigurationData("mapped_associate_configuration", accountID, factory, "", 0,
				"table_name", "associate_basic_configuration");
		boolean flag = false;
		if (pAssociateColName != null)
		{
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();

			for (GridDataPropertyView gdp : pAssociateColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn.add(ob1);
			}

			flag = cbt.createTable22("associate_basic_data", tableColumn, factory);
		}
		return flag;
	}

	public boolean createTableAssociateOfferingMapping(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();

			ob1 = new TableColumes();
			ob1.setColumnname("associateName");
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

			flag = cbt.createTable22("associate_offering_mapping", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableAssociateContactMaster(SessionFactory factory, String accountID)
	{
		boolean flag = false;
		List<GridDataPropertyView> pAssociateColName1 = Configuration.getConfigurationData("mapped_associate_configuration", accountID, factory, "", 0,
				"table_name", "associate_contact_configuration");
		if (pAssociateColName1 != null)
		{
			// List <InsertDataTable> insertData=new
			// ArrayList<InsertDataTable>();
			List<TableColumes> tableColumn1 = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : pAssociateColName1)
			{
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn1.add(ob1);
			}
			flag = cbt.createTable22("associate_contact_data", tableColumn1, factory);
		}
		return flag;
	}

	public List<String> getAssociateBasicDataToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("associateName");
		listDataToShow.add("associateRating");
		listDataToShow.add("weightage");
		listDataToShow.add("industry");
		listDataToShow.add("subIndustry");
		listDataToShow.add("associateType");
		listDataToShow.add("associateCategory");
		listDataToShow.add("location");
		listDataToShow.add("sourceName");
		listDataToShow.add("contactNo");
		listDataToShow.add("webAddress");
		listDataToShow.add("associateEmail");
		return listDataToShow;
	}

}