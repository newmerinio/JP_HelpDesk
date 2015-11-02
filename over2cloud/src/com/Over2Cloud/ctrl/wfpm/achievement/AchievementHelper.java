package com.Over2Cloud.ctrl.wfpm.achievement;

import java.util.ArrayList;
import java.util.List;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

public class AchievementHelper extends SessionProviderClass
{
	CommonOperInterface	coi	= new CommonConFactory().createInterface();

	public boolean createTableAchievementForOffering()
	{
		boolean flag = false;
		try
		{
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("empId");
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
			ob1.setColumnname("achievementValue");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("achievementDate");
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
			ob1.setColumnname("client_offering_mapping_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			
			flag = coi.createTable22("achievement_for_offering", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public int addAchievementForOffering(String... values)
	{
		int count = 0;
		try
		{
			boolean flag = createTableAchievementForOffering();

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
           
			ob = new InsertDataTable();
			ob.setColName("empId");
			ob.setDataName(values[0]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("offeringId");
			ob.setDataName(values[1]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("achievementValue");
			ob.setDataName(values[2]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("achievementDate");
			ob.setDataName(values[3]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("offeringLevelId");
			ob.setDataName(values[4]);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(values[5]);
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
			ob.setColName("client_offering_mapping_id");
			ob.setDataName(values[6]);
			insertData.add(ob);

			boolean status = coi.insertIntoTable("achievement_for_offering", insertData, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

}
