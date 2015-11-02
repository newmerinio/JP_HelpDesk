package com.Over2Cloud.ctrl.productivityEvaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class ProductivityEvaluationKaizenHelper 
{
	@SuppressWarnings("rawtypes")
	public Map<String, String> getOGList(SessionFactory connectionSpace) 
	{
		Map<String,String> data = null;
		try 
		{
			data=new LinkedHashMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT id,groupName FROM groupinfo WHERE groupName NOT IN('Corporate Functions','CMO') ");
			List value=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (value!=null && value.size()>0) 
			{
				data.put("All", "All OG");
				for (Iterator iterator = value.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1]!=null) 
					{
						data.put(object[0].toString(), object[1].toString());
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> getReviewBY(SessionFactory connectionSpace) 
	{
		Map<String,String> data = null;
		try 
		{
			data=new LinkedHashMap<String, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			query.append("SELECT id,deptName FROM department WHERE groupId='9' ");
			List value=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (value!=null && value.size()>0) 
			{
				for (Iterator iterator = value.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					if (object[1]!=null) 
					{
						data.put(object[0].toString(), object[1].toString());
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return data;
	}
	@SuppressWarnings("rawtypes")
	public int getMaximumComSpecialId(String moduleName,SessionFactory connectionSpace)
	{

		List comList = null;
		int maxComId = 0;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		qryString.append("select max(id) from cmo_special_add_details WHERE moduleName= '"+moduleName+"'");
		comList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		for (int i = 0; i < comList.size(); i++)
		{
			maxComId = (Integer) comList.get(0);
		}
		return maxComId;
	}
	// saveCmo Special Reminder
		public boolean saveCmoSpecialReminder(Map<String, String> dataWithColumnName, SessionFactory connectionSpace)
		{
			// getting user from session

			boolean status = false;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// Code for creating table dynamically
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			// add some extra fields
			TableColumes ob2 = new TableColumes();
			// employee id
			ob2 = new TableColumes();
			ob2.setColumnname("reminder_name");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("reminder_code");
			ob2.setDatatype("varchar(10)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			// openDate
			ob2 = new TableColumes();
			ob2.setColumnname("due_date");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);
			// openDate
			ob2 = new TableColumes();
			ob2.setColumnname("due_time");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			// openTime
			ob2 = new TableColumes();
			ob2.setColumnname("remind_date");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);
			// openTime
			ob2 = new TableColumes();
			ob2.setColumnname("remind_time");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			ob2 = new TableColumes();
			ob2.setColumnname("remind_interval");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			// openTime
			ob2 = new TableColumes();
			ob2.setColumnname("cmo_special_id");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			// set status
			ob2 = new TableColumes();
			ob2.setColumnname("reminder_status");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			// set status
			ob2 = new TableColumes();
			ob2.setColumnname("status");
			ob2.setDatatype("varchar(255)");
			ob2.setConstraint("default NULL");
			Tablecolumesaaa.add(ob2);

			status = cbt.createTable22("cmo_special_reminder", Tablecolumesaaa, connectionSpace);
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			Iterator<Entry<String, String>> hiterator = dataWithColumnName.entrySet().iterator();
			while (hiterator.hasNext())
			{
				Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				ob = new InsertDataTable();
				ob.setColName(paramPair.getKey().toString());
				ob.setDataName(paramPair.getValue().toString());
				insertData.add(ob);
			}
			status = cbt.insertIntoTable("cmo_special_reminder", insertData, connectionSpace);
			return status;
		}
		
		@SuppressWarnings("rawtypes")
		public String getOtherMultipleOGDetails(SessionFactory connection,String plant)
		{
			String data=null;
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder();
				query.append("SELECT groupName FROM groupinfo AS gi  ");
				query.append("INNER JOIN department AS dept ON dept.groupId=gi.id  ");
				query.append(" WHERE dept.id IN("+plant+") GROUP BY groupName");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				query.setLength(0);
				if (dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							query.append(object.toString()+", ");
						}
					}
					data=query.toString().substring(0, query.toString().length()-2);
				}
				else
				{
					data="NA";
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}
		@SuppressWarnings("rawtypes")
		public String getotherMultiplePlantNames(SessionFactory connection,String plant)
		{
			String data=null;
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder();
				query.append("SELECT deptName FROM department WHERE id IN("+plant+")");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connection);
				query.setLength(0);
				if (dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object!=null)
						{
							query.append(object.toString()+", ");
						}
					}
					data=query.toString().substring(0, query.toString().length()-2);
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}

		@SuppressWarnings("rawtypes")
		public List getSendPersonNames(SessionFactory connectionSpace,
				String otherPlant)
		{
			List data=null;
			try
			{
				data=new ArrayList();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder();
				query.append("SELECT empName,mobOne,emailIdOne FROM employee_basic WHERE deptname IN("+otherPlant+")");
				data=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}

		@SuppressWarnings("rawtypes")
		public String getReviewName(SessionFactory connectionSpace,
				String empId)
		{
			String data=null;
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder();
				query.append("SELECT empName FROM employee_basic WHERE id IN("+empId+")");
				List data1=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			    if (data1!=null && data1.size()>0)
				{
			    	data=data1.get(0).toString();
				}
			    else
			    {
			    	data="NA";
			    }
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}
		
}
