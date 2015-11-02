package com.Over2Cloud.ctrl.wfpm.lead;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;

public class LeadActionControlDao
{

	public int dashboardCounter(SessionFactory session, String tableName, String colName1, String colVal1, String userName)
	{

		int counter = 0;
		List data = null;
		Session hSession = null;

		try
		{
			hSession = session.openSession();
			String qru = "select count(*) from " + tableName + " where " + colName1 + "='" + colVal1 + "' and userName='" + userName + "'";
			// System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
			// qru);
			data = hSession.createSQLQuery(qru).list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}

		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					counter = Integer.parseInt(object.toString());
				}
			}
		}
		return counter;

	}

	public int dashboardCounter1(SessionFactory session, String tableName, String colName1, String userName)
	{

		int counter = 0;
		List data = null;
		Session hSession = null;

		try
		{
			hSession = session.openSession();
			String qru = "select count(*) from " + tableName + " where (" + colName1 + "='1' or " + colName1 + "='2') and userName= '" + userName + "' ";
			// System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
			// qru);
			data = hSession.createSQLQuery(qru).list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}

		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					counter = Integer.parseInt(object.toString());
				}
			}
		}
		return counter;
	}

	public void createLeadGenerationTable(String accountID, SessionFactory connectionSpace) throws Exception
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name",
				"lead_generation");
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

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("status");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default '0'");
			Tablecolumesaaa.add(ob1);

			// create table 'leadgeneration' to store lead data
			cbt.createTable22("leadgeneration", Tablecolumesaaa, connectionSpace);
		}
	}

	public void createKPIAutofillTable(String accountID, SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

		TableColumes ob1 = new TableColumes();
		ob1.setColumnname("empId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		ob1 = new TableColumes();
		ob1.setColumnname("kpiId");
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
		ob1.setColumnname("leadId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);
		
		ob1 = new TableColumes();
		ob1.setColumnname("type");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL");
		Tablecolumesaaa.add(ob1);

		// create table 'kpi_autofill' to store lead data
		cbt.createTable22("kpi_autofill", Tablecolumesaaa, connectionSpace);
	}

	public void insertInToKPIAutofillTable(String empId, String kpiId, String userName, SessionFactory connectionSpace,String leadId,String type)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		InsertDataTable ob = null;
		ob = new InsertDataTable();
		ob.setColName("empId");
		ob.setDataName(empId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("kpiId");
		ob.setDataName(kpiId);
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
		
		ob = new InsertDataTable();
		ob.setColName("leadId");
		ob.setDataName(leadId);
		insertData.add(ob);
        
		ob = new InsertDataTable();
		ob.setColName("type");
		ob.setDataName(type);
		insertData.add(ob);
		cbt.insertIntoTable("kpi_autofill", insertData, connectionSpace);
	}
	
	@SuppressWarnings("rawtypes")
	public List statusName(String colName,String tableName,String idValue,SessionFactory connection)
	{
		List data=null;
		try
		{
			String query = "SELECT "+colName+" FROM "+tableName+" WHERE id= "+idValue;
			data=new createTable().executeAllSelectQuery(query, connection);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
}