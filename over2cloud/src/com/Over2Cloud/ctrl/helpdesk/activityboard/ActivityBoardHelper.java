package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class ActivityBoardHelper 
{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public List getServiceDeptByEmpId(SessionFactory connection, String empId, String userType)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT DISTINCT dept.id,dept.deptName FROM employee_basic AS emp");
			query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname");
			query.append(" INNER JOIN subdepartment AS subdept ON subdept.deptid = dept.id");
			query.append(" INNER JOIN feedback_type AS feedtype On feedtype.dept_subdept = subdept.id");
			query.append(" WHERE feedtype.moduleName = 'HDM'");
			if(userType!=null && userType.equalsIgnoreCase("N") && empId!=null)
				query.append("  AND emp.id = "+empId);
			
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	public List getAllDepartment(SessionFactory connection, String empId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT DISTINCT dept.id,dept.deptName FROM employee_basic AS emp");
			query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname");
			if(empId!=null && !empId.equals(""))
				query.append("WHERE emp.id = "+empId);
			
			query.append(" ORDER BY dept.deptName");
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	public String getComplaintCountByFeedByWithCatg(SessionFactory connection, String feedby, String feedbyMobno, String catgId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(id) FROM feedback_status ");
			query.append(" WHERE feed_by_mobno = '"+feedbyMobno+"' AND feed_by = '"+feedby+"' AND moduleName = 'HDM'");
			query.append(" AND subCatg IN (SELECT id FROM feedback_subcategory WHERE categoryName ="+catgId+")");
			
			System.out.println("Count "+query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(dataList!=null && dataList.size()>0)
			return dataList.get(0).toString();
		else
			return "0";
	}
	public List getFeedByEmployeeDetail(SessionFactory connection, CommonOperInterface cbt,String complianId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp ");
			query.append(" INNER JOIN feedback_status As complaint ON complaint.feed_by_mobno = emp.mobOne ");
			query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname ");
			query.append(" WHERE complaint.id ="+complianId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	public List getAllotToEmployeeDetail(SessionFactory connection, CommonOperInterface cbt,String complianId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp ");
			query.append(" INNER JOIN feedback_status As complaint ON complaint.allot_to = emp.id ");
			query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname ");
			query.append(" WHERE complaint.id ="+complianId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dataList;
	}
	public List getLodgerEmployeeDetail(SessionFactory connection, CommonOperInterface cbt,String complianId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT feed_registerby FROM feedback_status WHERE id ="+complianId);
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
			query.setLength(0);
			if(dataList!=null && dataList.size()>0)
			{
				dataList = getLodgerEmployeeDetailByUserName(connection,cbt,dataList.get(0).toString());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public List getLodgerEmployeeDetailByUserName(SessionFactory connection, CommonOperInterface cbt,String userName)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			String encryUserId = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			query.append("SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp ");
			query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname ");
			query.append(" INNER JOIN useraccount As useracc ON useracc.id = emp.useraccountid");
			query.append(" WHERE useracc.userID='"+encryUserId+"'");
			System.out.println("@###@### "+query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public List getStatusLevelOfCompliant(SessionFactory connection, CommonOperInterface cbt,String compliantId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT feedback.status,feedback.level,feedback.open_date,feedback.open_time,subcatg.addressingTime,subcatg.resolutionTime,feedback.to_dept_subdept");
			query.append(",feedback.resolve_remark,feedback.spare_used");
			query.append(" FROM feedback_status AS feedback");
			query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
			query.append(" WHERE feedback.id ="+compliantId);
			System.out.println("query String "+query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmp4Escalation(String dept_subDept, String module, String level, SessionFactory connectionSpace, CommonOperInterface cbt)
	{
		List empList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try {
			query.append("select distinct emp.id, emp.empName from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
			query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
			query.append(" inner join department dept on sub_dept.deptid = dept.id ");
			query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
			query.append(" where roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
			query.append(" and sub_dept.id='"+ dept_subDept+ "'");
			
			System.out.println("*******Query: "+query.toString());
			empList = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
	
	@SuppressWarnings("unchecked")
	public List getTicketCycle(SessionFactory connection, CommonOperInterface cbt,String compliantId)
	{
		List tempList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{
			query.append("SELECT feedback.status,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
			query.append("feedback.sn_reason,feedback.hp_date,feedback.hp_time,feedback.hp_reason,");
			query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,");
			query.append("feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,emp.empName,feedback.resolve_remark,feedback.spare_used,");
			query.append("feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,");
			query.append("feedback.action_by,feedback.close_mode,feedback.ignore_mode,feedback.hp_mode,feedback.id,feedback.open_date,feedback.open_time");
			query.append(" FROM feedback_status AS feedback ");
			query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.resolve_by where  feedback.id ="+compliantId);
			System.out.println("*******Query: "+query.toString());
			tempList = cbt.executeAllSelectQuery(query.toString(),connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempList;
	}
	public List getSeekApprovalData(SessionFactory connection, CommonOperInterface cbt,String compliantId)
	{
		List tempList = new ArrayList();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT seek.id,seek.request_on,seek.request_at,emp1.empname AS allotTo,seek.reason,");
			query.append("emp2.empname AS approvedBy,seek.status,seek.approved_on, seek.approved_at,seek.approved_comment");
			query.append(" FROM seek_approval_detail AS seek");
			query.append(" INNER JOIN feedback_status AS feedback On feedback.id = seek.complaint_id");
			query.append(" INNER JOIN employee_basic AS emp1 ON emp1.id = feedback.allot_to");
			query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = seek.approved_by");
			query.append(" INNER JOIN employee_basic AS emp2 ON emp2.id = contact.emp_id");
			query.append(" WHERE seek.module_name = 'HDM' AND seek.complaint_id='"+compliantId+"'");
			System.out.println(" ### "+query.toString());
			tempList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return tempList;
	}
	
}