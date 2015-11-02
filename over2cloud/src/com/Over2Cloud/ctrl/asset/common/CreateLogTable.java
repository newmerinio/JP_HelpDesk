package com.Over2Cloud.ctrl.asset.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;

public class CreateLogTable
{
	static final Log log = LogFactory.getLog(CreateLogTable.class);
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommonOperInterface cbt = new CommonConFactory().createInterface();

	public boolean createLogTable()
	{
		boolean status = false;
		List<TableColumes> tableColume = new ArrayList<TableColumes>();
		InsertDataTable ob = null;
		try
		{
			TableColumes ob1 = null;
			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("updateon");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("updateat");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("table_name");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("column_name");
			ob1.setDatatype("text");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("data");
			ob1.setDatatype("text");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("update_row_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			ob1 = new TableColumes();
			ob1.setColumnname("update_type");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			status = cbt.createTable22("all_table_update_log", tableColume, connectionSpace);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method createLogTable of class " + getClass(), e);
			e.printStackTrace();
		}
		return status;
	}

	public boolean insertDataInLogTable(Map<String, String> logData)
	{
		Iterator iterator = logData.entrySet().iterator();
		InsertDataTable ob = null;
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		boolean insertFlag = false;
		try
		{
			while (iterator.hasNext())
			{
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				ob = new InsertDataTable();
				ob.setColName(mapEntry.getKey().toString());
				ob.setDataName(mapEntry.getValue().toString());
				insertData.add(ob);
			}
			insertFlag = cbt.insertIntoTable("all_table_update_log", insertData, connectionSpace);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method insertDataInLogTable of class " + getClass(), e);
			e.printStackTrace();
		}
		return insertFlag;
	}
}
