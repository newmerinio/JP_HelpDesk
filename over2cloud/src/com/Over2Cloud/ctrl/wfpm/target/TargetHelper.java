package com.Over2Cloud.ctrl.wfpm.target;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;

public class TargetHelper extends SessionProviderClass
{
	CommonOperInterface	coi	= new CommonConFactory().createInterface();

	public Map<String, String> fetchContactIdAndEmpNameByDept(String empId, String moduleName)
	{
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			StringBuilder query = new StringBuilder();
			query
					.append("select cc.id, emp1.empName from employee_basic as emp, employee_basic as emp1, compliance_contacts as cc where emp.deptname = cc.forDept_id  and emp1.id = cc.emp_id and emp.id = '");
			query.append(empId);
			query.append("'  and cc.moduleName = '");
			query.append(moduleName);
			query.append("' ");

			//System.out.println("query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				CommonHelper ch = new CommonHelper();
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					empMap.put(data[0].toString(), ch.getFieldValue(data[1]));
				}
				//System.out.println("empMap.size():" + empMap.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empMap;
	}

	public ArrayList<ArrayList<String>> fetchKpiIdAndNameByEmpId(String empId)
	{
		ArrayList<ArrayList<String>> kpiList = new ArrayList<ArrayList<String>>();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select kc.id, kc.kra, kc.kpi from krakpicollection as kc inner join krakpimap as km on km.KPIId = kc.id where empID = '");
			query.append(empId);
			query.append("'");
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				CommonHelper ch = new CommonHelper();
				ArrayList<String> tempList = null;
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					tempList = new ArrayList<String>();
					tempList.add(data[0].toString());
					tempList.add(ch.getFieldValue(data[1]));
					tempList.add(ch.getFieldValue(data[2]));
					kpiList.add(tempList);
				}
				//System.out.println("kpiList.size():" + kpiList.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return kpiList;
	}

	public ArrayList<ArrayList<String>> fetchAllOffering()
	{
		ArrayList<ArrayList<String>> offeringList = new ArrayList<ArrayList<String>>();
		try
		{
			String[] colAndTable = new CommonHelper().getOfferingName();
			StringBuilder query = new StringBuilder();
			query.append("select off.id, off.");
			query.append(colAndTable[0]);
			query.append(" from ");
			query.append(colAndTable[1]);
			query.append(" as off order by off.");
			query.append(colAndTable[0]);

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				ArrayList<String> tempList = null;
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					tempList = new ArrayList<String>();
					tempList.add(data[0].toString());
					tempList.add(CommonHelper.getFieldValue(data[1]));
					offeringList.add(tempList);
				}
				//System.out.println("kpiList.size():" + offeringList.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return offeringList;
	}

	/*
	 * public String[] fetchEmpDetailsById(String empId) { String[] details =
	 * null; try { StringBuilder query = new StringBuilder();query.append(
	 * "select emp.id, emp.empName, emp.mobOne from employee_basic as emp where emp.id = "
	 * ); query.append(empId); List list =
	 * coi.executeAllSelectQuery(query.toString(), connectionSpace); if (list !=
	 * null && list.size() > 0) { CommonHelper ch = new CommonHelper(); Object[]
	 * data = (Object[]) list.get(0); details = new String[data.length];
	 * details[0] = data[0].toString().trim(); details[1] =
	 * data[1].toString().trim(); details[2] = data[2].toString().trim(); } }
	 * catch (Exception e) { e.printStackTrace(); } return details; }
	 */

	public boolean createTableTargetForKpi()
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
			ob1.setColumnname("kpiId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("weightage");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("targetValue");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("applicableFrom");
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

			flag = coi.createTable22("target_for_kpi", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createTableTargetForOffering()
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
			ob1.setColumnname("offeringLevelId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("weightage");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("targetValue");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("applicableFrom");
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

			flag = coi.createTable22("target_for_offering", Tablecolumesaaa, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public int addTargetForKpi(String empId, String applicableFrom, String kpiId, String weightage, String targetValue, String uName)
	{
		int count = 0;
		try
		{
			TargetHelper th = new TargetHelper();
			boolean flag = th.createTableTargetForKpi();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ob = new InsertDataTable();
			ob.setColName("empId");
			ob.setDataName(empId);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("applicableFrom");
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

			InsertDataTable ob1 = new InsertDataTable();
			ob1.setColName("kpiId");
			insertData.add(ob1);
			InsertDataTable ob2 = new InsertDataTable();
			ob2.setColName("weightage");
			insertData.add(ob2);
			InsertDataTable ob3 = new InsertDataTable();
			ob3.setColName("targetValue");
			insertData.add(ob3);

			if (kpiId != null && weightage != null && targetValue != null)
			{
				String[] kpiIdArray = CommonHelper.getCommaSeparatedToArray(kpiId);
				String[] weightageArray = CommonHelper.getCommaSeparatedToArray(weightage);
				String[] targetValueArray = CommonHelper.getCommaSeparatedToArray(targetValue);
				for (int i = 0; i < kpiIdArray.length; i++)
				{
					if (kpiIdArray[i] == null || kpiIdArray[i].trim().equals("") || weightageArray[i] == null || weightageArray[i].trim().equals("")
							|| targetValueArray[i] == null || targetValueArray[i].trim().equals("")) continue;
					ob1.setDataName(CommonHelper.getFieldValue(kpiIdArray[i]));
					ob2.setDataName(CommonHelper.getFieldValue(weightageArray[i]));
					ob3.setDataName(CommonHelper.getFieldValue(targetValueArray[i]));
					boolean status = coi.insertIntoTable("target_for_kpi", insertData, connectionSpace);
					if (status) count++;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

	public int editTargetForKpi(String empId, String applicableFrom, String kpiId, String weightage, String targetValue, String uName)
	{
		int count = 0;
		String[] offering = CommonHelper.getInstance().getOfferingName();
		StringBuilder query = new StringBuilder();
		query.append("delete from target_for_kpi where empId = '");
		query.append(empId);
		query.append("' and applicableFrom = '");
		query.append(applicableFrom);
		query.append("' and offeringLevelId = '");
		query.append(offering[2]);
		query.append("' ");

		boolean flag = coi.updateTableColomn(connectionSpace, query);
		//System.out.println("flag:" + flag);
		if (flag)
		{
			TargetHelper th = new TargetHelper();
			count = th.addTargetForKpi(empId, applicableFrom, kpiId, weightage, targetValue, cId);
		}

		return count;
	}
	
	public int editTargetForOffering(String empId, String applicableFrom, String offeringId, String weightage, String targetValue, String uName)
	{
		int count = 0;
		String[] offering = CommonHelper.getInstance().getOfferingName();
		StringBuilder query = new StringBuilder();
		query.append("delete from target_for_offering where empId = '");
		query.append(empId);
		query.append("' and applicableFrom = '");
		query.append(applicableFrom);
		query.append("' and offeringLevelId = '");
		query.append(offering[2]);
		query.append("' ");
		
		boolean flag = coi.updateTableColomn(connectionSpace, query);
		//System.out.println("flag:" + flag);
		if (flag)
		{
			TargetHelper th = new TargetHelper();
			count = th.addTargetForOffering(empId, applicableFrom, offeringId, weightage, targetValue, cId);
		}
		
		return count;
	}

	public int addTargetForOffering(String empId, String applicableFrom, String offeringId, String weightage, String targetValue, String uName)
	{
		int count = 0;
		try
		{
			TargetHelper th = new TargetHelper();
			boolean flag = th.createTableTargetForOffering();
			String offeringLevelId = session.get("offeringLevel").toString();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ob = new InsertDataTable();
			ob.setColName("empId");
			ob.setDataName(empId);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("applicableFrom");
			ob.setDataName(applicableFrom);
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offeringLevelId");
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

			InsertDataTable ob1 = new InsertDataTable();
			ob1.setColName("offeringId");
			insertData.add(ob1);
			InsertDataTable ob2 = new InsertDataTable();
			ob2.setColName("weightage");
			insertData.add(ob2);
			InsertDataTable ob3 = new InsertDataTable();
			ob3.setColName("targetValue");
			insertData.add(ob3);

			if (offeringId != null && weightage != null && targetValue != null)
			{
				String[] offeringIdArray = CommonHelper.getCommaSeparatedToArray(offeringId);
				String[] weightageArray = CommonHelper.getCommaSeparatedToArray(weightage);
				String[] targetValueArray = CommonHelper.getCommaSeparatedToArray(targetValue);
				for (int i = 0; i < offeringIdArray.length; i++)
				{
					if (offeringIdArray[i] == null || offeringIdArray[i].trim().equals("") || weightageArray[i] == null || weightageArray[i].trim().equals("")
							|| targetValueArray[i] == null || targetValueArray[i].trim().equals("")) continue;
					ob1.setDataName(CommonHelper.getFieldValue(offeringIdArray[i]));
					ob2.setDataName(CommonHelper.getFieldValue(weightageArray[i]));
					ob3.setDataName(CommonHelper.getFieldValue(targetValueArray[i]));
					boolean status = coi.insertIntoTable("target_for_offering", insertData, connectionSpace);
					if (status) count++;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> fetchEmpAndMobile(String empId, String contactId, TargetType targetType)
	{
		ArrayList<ArrayList<String>> targetList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			/*query
					.append("select eb.id,  eb.empName, eb.mobOne  from employee_basic as eb inner join groupinfo as gi on gi.id = eb.groupId inner join department as dept on dept.id = eb.deptname and gi.groupName = 'Employee'  and dept.deptName = 'Sales' order by eb.empName asc ");*/
			
			if(targetType == TargetType.KPI)
			query
					.append("select eb.id,  eb.empName, eb.mobOne  from employee_basic as eb inner join groupinfo as gi on gi.id = eb.groupId inner join department as dept on dept.id = eb.deptname inner join target_for_kpi as tfk on tfk.empId = eb.id where gi.groupName = 'Employee' group by eb.id order by eb.empName asc ");
			else if(targetType == TargetType.OFFERING)
				query
					.append("select eb.id,  eb.empName, eb.mobOne  from employee_basic as eb inner join groupinfo as gi on gi.id = eb.groupId inner join department as dept on dept.id = eb.deptname inner join target_for_offering as tfk on tfk.empId = eb.id where gi.groupName = 'Employee' group by eb.id order by eb.empName asc ");
			
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				targetList = new ArrayList<ArrayList<String>>();
				ArrayList<String> tempList = null;
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					tempList = new ArrayList<String>();
					tempList.add(CommonHelper.getFieldValue(data[0]));
					tempList.add(CommonHelper.getFieldValue(data[1]));
					tempList.add(CommonHelper.getFieldValue(data[2]));
					targetList.add(tempList);
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return targetList;
	}

	public ArrayList<ArrayList<String>> fetchTargetForKpiByEmpId(String empId)
	{
		ArrayList<ArrayList<String>> targetList = null;
		StringBuilder query = new StringBuilder();
		query
				.append("select tfk.id, kp.kra, kp.kpi, tfk.weightage, tfk.targetValue, tfk.applicableFrom from target_for_kpi as tfk inner join krakpicollection as kp on kp.id = tfk.kpiId where tfk.empId = '");
		query.append(empId);
		query.append("' order by tfk.applicableFrom");
		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			targetList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = null;
			CommonHelper ch = new CommonHelper();
			for (Object obj : list)
			{
				tempList = new ArrayList<String>();
				Object[] data = (Object[]) obj;
				tempList.add(ch.getFieldValue(data[0]));
				tempList.add(ch.getFieldValue(data[1]));
				tempList.add(ch.getFieldValue(data[2]));
				tempList.add(ch.getFieldValue(data[3]));
				tempList.add(ch.getFieldValue(data[4]));
				tempList.add(ch.getFieldValue(data[5]));
				targetList.add(tempList);
			}
		}
		return targetList;
	}

	public ArrayList<ArrayList<String>> fetchTargetForOfferingByEmpId(String empId)
	{
		ArrayList<ArrayList<String>> targetList = null;
		CommonHelper ch = new CommonHelper();
		String[] colAndTable = ch.getOfferingName();
		StringBuilder query = new StringBuilder();
		query.append("select tfo.id, off.");
		query.append(colAndTable[0]);
		query.append(", tfo.weightage, tfo.targetValue, tfo.applicableFrom from target_for_offering as tfo inner join ");
		query.append(colAndTable[1]);
		query.append(" as off on off.id = tfo.offeringId where tfo.empId = '");
		query.append(empId);
		query.append("' order by tfo.applicableFrom ");

		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			targetList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = null;
			for (Object obj : list)
			{
				tempList = new ArrayList<String>();
				Object[] data = (Object[]) obj;
				for (Object dt : data)
				{
					tempList.add(ch.getFieldValue(dt.toString()));
				}
				targetList.add(tempList);
			}
		}
		return targetList;
	}

	public ArrayList<ArrayList<String>> fetchTargetByTableId(String tableId, String applicableFrom, TargetType targetType)
	{
		ArrayList<ArrayList<String>> targetList = null;
		StringBuilder query = new StringBuilder();
		String[] offering = new CommonHelper().getOfferingName();
		if (targetType == TargetType.KPI)
		{
			query
					.append("select tfk.id, kc.id 'kpiId', kc.kra, kc.kpi, tfk.weightage, tfk.targetValue, tfk.applicableFrom, tfk.userName, tfk.date, tfk.time, tfk.empId  from target_for_kpi as tfk, target_for_kpi as tfk1, krakpicollection as kc where tfk1.empId = tfk.empId and tfk1.applicableFrom = tfk.applicableFrom and kc.id = tfk.kpiId and tfk1.id = ");
			query.append(tableId);
			query.append(" and tfk1.applicableFrom = '");
			query.append(applicableFrom);
			query.append("'");
		}
		else if (targetType == TargetType.OFFERING)
		{
			query.append("select tfo.id, off.id 'offId', off.");
			query.append(offering[0]);
			query.append(", tfo.weightage, tfo.targetValue, tfo.applicableFrom, tfo.userName, tfo.date, tfo.time, tfo.empId from target_for_offering as tfo,  target_for_offering as tfo1, ");
			query.append(offering[1]);
			query.append(" as off where tfo1.empId = tfo.empId and tfo1.applicableFrom = tfo.applicableFrom and off.id = tfo.offeringId and tfo1.id = '");
			query.append(tableId);
			query.append("' and tfo1.applicableFrom = '");
			query.append(applicableFrom);
			query.append("'");
			query.append("  and tfo1.offeringLevelId = '");
			query.append(offering[2]);
			query.append("' ");
		}

		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			targetList = new ArrayList<ArrayList<String>>();
			ArrayList<String> tempList = null;
			for (Object obj : list)
			{
				tempList = new ArrayList<String>();
				Object[] data = (Object[]) obj;
				for (Object dt : data)
				{
					tempList.add(CommonHelper.getFieldValue(dt.toString()));
				}
				targetList.add(tempList);
			}
		}
		return targetList;
	}

}
