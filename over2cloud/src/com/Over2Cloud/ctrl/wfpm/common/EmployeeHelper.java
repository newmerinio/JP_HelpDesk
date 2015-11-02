package com.Over2Cloud.ctrl.wfpm.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;

public class EmployeeHelper<T>
{
	private Map							session							= ActionContext.getContext().getSession();
	private String					userName						= (String) session.get("uName");
	private SessionFactory	connectionSpace			= (SessionFactory) session.get("connectionSpace");
	private final String		DES_ENCRYPTION_KEY	= "ankitsin";
	CommonOperInterface			coi									= new CommonConFactory().createInterface();

	public String[] getEmpDepartmentIdAndName(CommonOperInterface coi)
	{
		String[] dept = null;
		if (userName != null || !userName.equals(""))
		{
			try
			{
				dept = new String[2];
				userName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
				StringBuilder query = new StringBuilder();
				query
						.append("select dept.id, dept.deptName from department as dept inner join employee_basic as emp on emp.dept = dept.id inner join useraccount as ua on ua.id = emp.useraccountid where ua.userID = '");
				query.append(userName);
				query.append("' ");

				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null && list.size() > 0)
				{
					Object[] obj = (Object[]) list.get(0);
					dept[0] = obj[0].toString();
					dept[1] = obj[1].toString();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return dept;
	}

	@SuppressWarnings("unchecked")
	public T fetchEmployee(EmployeeReturnType type, TargetType targetType)
	{

		T objectContainer = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (targetType == TargetType.KPI) query
					.append("select emp.id, emp.empName from employee_basic as emp inner join compliance_contacts as cc inner join department as dept on dept.id = cc.forDept_id inner join krakpimap as krak on krak.empId = emp.id where emp.id = cc.emp_id and dept.deptName = 'Sales' and cc.moduleName = 'WFPM' group by emp.id ");
			else if (targetType == TargetType.OFFERING) query
					.append("select emp.id, emp.empName from employee_basic as emp inner join compliance_contacts as cc inner join department as dept on dept.id = cc.forDept_id where emp.id = cc.emp_id and dept.deptName = 'Sales' and cc.moduleName = 'WFPM'  ");

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			Map<String, String> map = null;
			JSONArray jsonArray = null;
			if (type == EmployeeReturnType.MAP)
			{
				map = new LinkedHashMap<String, String>();
				objectContainer = (T) map;
			}
			else if (type == EmployeeReturnType.JSONARRAY)
			{
				jsonArray = new JSONArray();
				objectContainer = (T) jsonArray;
			}

			if (list != null && list.size() > 0)
			{
				if (type == EmployeeReturnType.MAP)
				{
					for (Object obj : list)
					{
						Object[] data = (Object[]) obj;
						if (data[1] != null) map.put(data[0].toString(), data[1].toString());
					}
					objectContainer = (T) map;
				}
				else if (type == EmployeeReturnType.JSONARRAY)
				{
					for (Object obj : list)
					{
						Object[] object = (Object[]) obj;
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("ID", object[0].toString());
						jsonObject.put("NAME", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

						jsonArray.add(jsonObject);
					}
					objectContainer = (T) jsonArray;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();
		}
		return objectContainer;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> fetchEmpWithMobile()
	{
		Map<String, String> map = new LinkedHashMap<String, String>();
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query
					.append("select concat(empName,'-',mobOne) as 'one' , concat(empName,'-',mobOne) as 'two' from employee_basic as emp inner join compliance_contacts as cc inner join department as dept on dept.id = cc.forDept_id where emp.id = cc.emp_id and dept.deptName = 'Sales' and cc.moduleName = 'WFPM'  ");

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					map.put(data[0].toString(), CommonHelper.getFieldValue(data[1].toString()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public String[] fetchEmpDetailsById(String empId)
	{
		String[] details = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select emp.id, emp.empName, emp.mobOne from employee_basic as emp where emp.id = ");
			query.append(empId);

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				Object[] data = (Object[]) list.get(0);
				details = new String[data.length];
				details[0] = data[0].toString().trim();
				details[1] = data[1].toString().trim();
				details[2] = data[2].toString().trim();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return details;
	}

	public Map<String, String> fetchEmpIdAndEmpName()
	{
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			StringBuilder query = new StringBuilder();
			/* query.append("select id, empName from employee_basic "); */
			query
					.append("select eb.id, eb.empName from employee_basic as eb inner join groupinfo as gi on gi.id = eb.groupId and gi.groupName = 'Employee' order by eb.empName ");

			// System.out.println("query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				CommonHelper ch = new CommonHelper();
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					empMap.put(data[0].toString(), ch.getFieldValue(data[1]));
				}
				// System.out.println("empMap.size():" + empMap.size());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empMap;
	}

}
