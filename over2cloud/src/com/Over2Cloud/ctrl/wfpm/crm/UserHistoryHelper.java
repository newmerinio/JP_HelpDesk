package com.Over2Cloud.ctrl.wfpm.crm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class UserHistoryHelper<T> {

	CommonOperInterface	cbt		= new CommonConFactory().createInterface();
	int									level	= 0;
	
	public List<String> getUserHistoryDataToShow()
	{

		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("action_date");
		listDataToShow.add("action_time");
		listDataToShow.add("userName");
		listDataToShow.add("sub_module");
		listDataToShow.add("moduleName");
		listDataToShow.add("field");
		listDataToShow.add("curd_operation");
		listDataToShow.add("action_description");
		return listDataToShow;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, String> fetchModuleInHistory(SessionFactory connectionSpace)
	{
		Map<String, String> data=new LinkedHashMap<String, String>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query="SELECT moduleName FROM user_action_history GROUP BY moduleName ORDER BY moduleName ASC ";
			List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						data.put(object.toString(), object.toString());
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
	public Map<String, String> fetchSubModuleInHistory(SessionFactory connectionSpace)
	{
		Map<String, String> data=new LinkedHashMap<String, String>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query="SELECT sub_module FROM user_action_history GROUP BY sub_module ORDER BY sub_module ASC ";
			List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					if (object!=null)
					{
						data.put(object.toString(), object.toString());
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
	public Map<String, String> fetchUserInHistory(SessionFactory connectionSpace)
	{
		Map<String, String> data=new LinkedHashMap<String, String>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query="SELECT emp.id,emp.empName FROM user_action_history AS history LEFT JOIN employee_basic AS emp ON history.userName=emp.id GROUP BY emp.id ORDER BY emp.empName ASC ";
			List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
			if (dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0]!=null && object[1]!=null)
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
	public List fetchDataFields(SessionFactory connectionSpace, String userID)
	{
		List data=new ArrayList<String>();
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query="SELECT field,action_description FROM user_action_history WHERE id= "+userID;
			System.out.println("QUERY ::  "+query);
			data=cbt.executeAllSelectQuery(query, connectionSpace);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
}
