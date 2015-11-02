package com.Over2Cloud.ctrl.wfpm.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;

public class MOMHelper
{
	public boolean creteTableEntityTakeActionAttachedDoc(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("docPath");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("entity_takeaction_attacheddoc", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean creteTableMOM(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("momDate");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("fromTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("toTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("agenda");
			ob1.setDatatype("text");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("entityType");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("entity_takeaction_id");
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

			flag = cbt.createTable22("entity_takeaction_mom", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean creteTableMOMEntityContact(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("clientContactId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_mom_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("entity_takeaction_mom_clientcontact", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean creteTableMOMEmployee(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("empId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_mom_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("entity_takeaction_mom_employee", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean creteTableMOMDiscussion(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("discussion");
			ob1.setDatatype("text");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_mom_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("entity_takeaction_mom_discussion", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean creteTableMOMFutureAction(SessionFactory factory)
	{
		boolean flag = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("futureAction");
			ob1.setDatatype("text");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("client_takeaction_mom_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = cbt.createTable22("entity_takeaction_mom_futureaction", Tablecolumesaaa, factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
}
