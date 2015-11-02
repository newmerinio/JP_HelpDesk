package com.Over2Cloud.ctrl.krakpi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;

public class KraKpiHelper
{
	Map							session					= ActionContext.getContext().getSession();
	String					userName				= (String) session.get("uName");
	SessionFactory	connectionSpace	= (SessionFactory) session.get("connectionSpace");

	@SuppressWarnings("unchecked")
	public List<List<String>> fetchKraKpiOfEmpDept(CommonOperInterface coi, String empId)
	{
		List<List<String>> dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			// query.append(
			// "select kp.id, kp.kra, kp.kpi from krakpicollection as kp inner join employee_basic as eb on eb.deptname = kp.mappeddeptid where eb.id = '"
			// );
			// query.append(empId);
			// query.append("'");

			query
					.append("select kp.id, kp.kra, kp.kpi from krakpicollection as kp inner join department as dept on dept.id = kp.mappeddeptid where dept.deptName = 'Sales'");

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				List<Object> checkList = fetchKpiMappedWithEmp(coi, empId);
				dataList = new ArrayList<List<String>>();
				List<String> tempList = null;
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					tempList = new ArrayList<String>();
					tempList.add((data[0] == null || data[0].equals("")) ? "NA" : data[0].toString());
					tempList.add((data[1] == null || data[1].equals("")) ? "NA" : data[1].toString());
					tempList.add((data[2] == null || data[2].equals("")) ? "NA" : data[2].toString());
					if (checkList != null && checkList.size() > 0 && checkList.contains(data[0].toString().trim()))
					{
						tempList.add("true");
					}
					else
					{
						tempList.add("false");
					}
					dataList.add(tempList);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public List<Object> fetchKpiMappedWithEmp(CommonOperInterface coi, String empId)
	{
		List<Object> dataList = null;
		try
		{
			createTableKraKpiMap(coi);
			StringBuilder query = new StringBuilder();
			query.append("select KPIId from krakpimap where empID = '");
			query.append(empId);
			query.append("'");
			dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public void createTableKraKpiMap(CommonOperInterface coi)
	{
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
		TableColumes ob1 = null;
		ob1 = new TableColumes();
		ob1.setColumnname("empID");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL ");
		Tablecolumesaaa.add(ob1);
		ob1 = new TableColumes();
		ob1.setColumnname("KPIId");
		ob1.setDatatype("varchar(255)");
		ob1.setConstraint("default NULL ");
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
		coi.createTable23("krakpimap", Tablecolumesaaa, connectionSpace, "empID", "KPIId");
	}

}
