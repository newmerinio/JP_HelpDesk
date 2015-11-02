package com.Over2Cloud.ctrl.wfpm.incentive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.target.TargetHelper;

public class IncentiveHelper extends SessionProviderClass
{

	public Map<String, String> fetchKpiIdAndName()
	{
		Map<String, String> kpiList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select kc.id, kc.kpi from krakpicollection as kc inner join krakpimap as km on km.KPIId = kc.id order by kc.kpi");
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			kpiList = CommonHelper.convertListToMap(list,false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return kpiList;
	}

	public Map<String, String> fetchOfferingIdAndName()
	{
		Map<String, String> offeringList = null;
		try
		{
			CommonHelper ch = new CommonHelper();
			String[] colAndTable = ch.getOfferingName();

			StringBuilder query = new StringBuilder();
			query.append("select off.id, off.");
			query.append(colAndTable[0]);
			query.append(" from ");
			query.append(colAndTable[1]);
			query.append(" as off order by off.");
			query.append(colAndTable[0]);
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			offeringList = CommonHelper.convertListToMap(list,false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return offeringList;
	}

	public int addIncentiveForKpi(String empId, String applicableFrom, String kpiId, IncentiveValueType incentive_in, String[] incentive, String uName,
			String[] slabFrom, String[] slabTo)
	{
		int count = 0;
		try
		{
			boolean flag = createTableIncentiveForKpi();
			boolean flag1 = createTableIncentiveSlab(IncentiveType.KPI);
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();

			InsertDataTable ob = new InsertDataTable();
			ob.setColName("kpiId");
			ob.setDataName(kpiId);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("empId");
			ob.setDataName(empId);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("incentive_in");
			ob.setDataName(incentive_in.ordinal());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("applicable_from");
			ob.setDataName(applicableFrom);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(uName);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			List<InsertDataTable> insertDataSlab = new ArrayList<InsertDataTable>();
			InsertDataTable ob1 = new InsertDataTable();
			ob1.setColName("slab_from");
			insertDataSlab.add(ob1);
			InsertDataTable ob2 = new InsertDataTable();
			ob2.setColName("slab_to");
			insertDataSlab.add(ob2);
			InsertDataTable ob3 = new InsertDataTable();
			ob3.setColName("incentive_value");
			insertDataSlab.add(ob3);
			InsertDataTable ob4 = new InsertDataTable();
			ob4.setColName("incentiv_for_kpiId");
			insertDataSlab.add(ob4);

			if (slabFrom != null && slabTo != null && incentive != null)
			{
				boolean kpiStatus = coi.insertIntoTable("incentive_for_kpi", insertData, connectionSpace);
				if (kpiStatus)
				{
					int idMAx = coi.getMaxId("incentive_for_kpi", connectionSpace);
					for (int i = 0; i < incentive.length; i++)
					{
						ob1.setDataName(CommonHelper.getFieldValue(slabFrom[i]));
						ob2.setDataName(CommonHelper.getFieldValue(slabTo[i]));
						ob3.setDataName(CommonHelper.getFieldValue(incentive[i]));
						ob4.setDataName(idMAx);
						boolean kpislabstatus = coi.insertIntoTable("incentive_slab_for_kpi", insertDataSlab, connectionSpace);
						if (kpislabstatus) count++;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

	public boolean createTableIncentiveForKpi()
	{
		boolean flag = false;
		try
		{
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();
			ob1 = new TableColumes();
			ob1.setColumnname("kpiId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("empId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("incentive_in");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("applicable_from");
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

			flag = coi.createTable22("incentive_for_kpi", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableIncentiveSlab(IncentiveType incentiveType)
	{
		boolean slabflag = false;
		try
		{
			String field = "", table = "";
			if (incentiveType == IncentiveType.KPI)
			{
				field = "incentiv_for_kpiId";
				table = "incentive_slab_for_kpi";
			}
			else if (incentiveType == IncentiveType.OFFERING)
			{
				field = "incentiv_for_offeringId";
				table = "incentive_slab_for_offering";
			}

			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname(field);
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("slab_from");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("slab_to");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("incentive_value");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			slabflag = coi.createTable22(table, Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return slabflag;

	}

	public int addIncentiveForOffering(String empId, String applicableFrom, String incofferingid, IncentiveValueType incentive_in, String[] incentive,
			String uName, String[] slabFrom, String[] slabTo)
	{
		int count = 0;
		try
		{
			IncentiveHelper ih = new IncentiveHelper();
			boolean flag = ih.createTableIncentiveForOffering();
			boolean slab = ih.createTableIncentiveSlab(IncentiveType.OFFERING);
			String offeringLevelId = session.get("offeringLevel").toString();

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ob.setColName("offering_id");
			ob.setDataName(incofferingid);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("empId");
			ob.setDataName(empId);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("incentive_in");
			ob.setDataName(incentive_in.ordinal());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("applicable_from");
			ob.setDataName(applicableFrom);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offering_level_id");
			ob.setDataName(offeringLevelId.split("#")[0]);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(uName);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			List<InsertDataTable> insertDataSlab = new ArrayList<InsertDataTable>();
			InsertDataTable ob1 = new InsertDataTable();
			ob1.setColName("slab_from");
			insertDataSlab.add(ob1);
			InsertDataTable ob2 = new InsertDataTable();
			ob2.setColName("slab_to");
			insertDataSlab.add(ob2);
			InsertDataTable ob3 = new InsertDataTable();
			ob3.setColName("incentive_value");
			insertDataSlab.add(ob3);
			InsertDataTable ob4 = new InsertDataTable();
			ob4.setColName("incentiv_for_offeringId");
			insertDataSlab.add(ob4);

			if (slabFrom != null && slabTo != null && incentive != null)
			{
				boolean kpiStatus = coi.insertIntoTable("incentive_for_offering", insertData, connectionSpace);
				if (kpiStatus)
				{
					int idMAx = coi.getMaxId("incentive_for_offering", connectionSpace);
					for (int i = 0; i < incentive.length; i++)
					{
						ob1.setDataName(CommonHelper.getFieldValue(slabFrom[i]));
						ob2.setDataName(CommonHelper.getFieldValue(slabTo[i]));
						ob3.setDataName(CommonHelper.getFieldValue(incentive[i]));
						ob4.setDataName(idMAx);
						boolean kpislabstatus = coi.insertIntoTable("incentive_slab_for_offering", insertDataSlab, connectionSpace);
						if (kpislabstatus) count++;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;

	}

	public boolean createTableIncentiveForOffering()
	{
		boolean flag = false;
		try
		{
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = null;
			ob1 = new TableColumes();
			ob1.setColumnname("offering_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("empId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("incentive_in");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("applicable_from");
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
			ob1.setColumnname("offering_level_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			flag = coi.createTable22("incentive_for_offering", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableSlabForOffering()
	{
		boolean slabflag = false;
		try
		{
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("incentiv_for_offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("slab_from");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("slab_to");
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

			slabflag = coi.createTable22("incentive_slab_from_offering", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return slabflag;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> fetchIncentiveForKpiByEmpId(String empId)
	{
		ArrayList<ArrayList<String>> incentiveList = null;
		StringBuilder query = new StringBuilder();
		query
				.append("select isfk.id, kc.kra, kc.kpi, isfk.slab_from, isfk.slab_to, isfk.incentive_value, ifk.incentive_in, ifk.applicable_from from incentive_for_kpi as ifk inner join krakpicollection as kc on kc.id = ifk.kpiId inner join incentive_slab_for_kpi as isfk on isfk.incentiv_for_kpiId = ifk.id where ifk.empId = '");
		query.append(empId);
		query.append("' ");

		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			incentiveList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = null;
			for (Object obj : list)
			{
				tempList = new ArrayList<String>();
				Object[] data = (Object[]) obj;
				tempList.add(CommonHelper.getFieldValue(data[0]));
				tempList.add(CommonHelper.getFieldValue(data[1]));
				tempList.add(CommonHelper.getFieldValue(data[2]));
				tempList.add(CommonHelper.getFieldValue(data[3]));
				tempList.add(CommonHelper.getFieldValue(data[4]));
				tempList.add(CommonHelper.getFieldValue(data[5]));
				tempList.add(CommonHelper.getFieldValue(data[6]));
				tempList.add(CommonHelper.getFieldValue(data[7]));
				incentiveList.add(tempList);
			}
		}
		return incentiveList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> fetchIncentiveForOfferingByEmpId(String empId)
	{
		ArrayList<ArrayList<String>> incentiveList = null;
		CommonHelper ch = new CommonHelper();
		String[] colAndTable = ch.getOfferingName();
		StringBuilder query = new StringBuilder();
		query.append("select isfo.id, off.");
		query.append(colAndTable[0]);
		query.append(", isfo.slab_from, isfo.slab_to, isfo.incentive_value, ifo.incentive_in, ifo.applicable_from from incentive_for_offering as ifo inner join ");
		query.append(colAndTable[1]);
		query
				.append(" as off on off.id = ifo.offering_id inner join incentive_slab_for_offering as isfo on isfo.incentiv_for_offeringId = ifo.id where ifo.empId = '");
		query.append(empId);
		query.append("' ");

		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			incentiveList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = null;
			for (Object obj : list)
			{
				tempList = new ArrayList<String>();
				Object[] data = (Object[]) obj;
				for (Object dt : data)
				{
					tempList.add(CommonHelper.getFieldValue(dt.toString()));
				}
				incentiveList.add(tempList);
			}
		}
		return incentiveList;
	}
}
