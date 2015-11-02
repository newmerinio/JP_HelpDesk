package com.Over2Cloud.ctrl.asset.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;

public class AssetDashboardHelper {

	@SuppressWarnings("unchecked")
	public List getLodgedTickets(String dept,String status_for, String empName,SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (status_for.equals("H")) {
				query.append("select id, ticket_no, feed_by, open_date, open_time, status from asset_complaint_status where  status not in ('Resolved','Ignore','Transfer') and  to_dept="+dept+" ");
			}
			else if (status_for.equals("M")) {
				query.append("select id, ticket_no, feed_by, open_date, open_time, status from asset_complaint_status where  status not in ('Resolved','Ignore','Transfer')");
			}
			else if (status_for.equals("N")) {
				query.append("select id, ticket_no, feed_by, open_date, open_time, status from asset_complaint_status where  status not in ('Resolved','Ignore','Transfer') and feed_by='"+empName+"'");
			}
		    System.out.println("Marquee Ticket Detail Query  is   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getDeptList(SessionFactory connection)
	 {
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		query.append("select distinct dept.id,dept.deptName from department as dept ");
		query.append(" inner join feedback_type fbtype on dept.id= fbtype.dept_subdept ");
		query.append(" where fbtype.hide_show='1' and fbtype.moduleName='CASTM' order by dept.deptName ");
		System.out.println("*********Dept Name List Query  "+query.toString());
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			subdeptList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return subdeptList;
	 }
	
	
	@SuppressWarnings("unchecked")
	public List getDashboardCounter(String status,String empName,String dept_subdeptid,SessionFactory connection)
	 {
		List  dashDeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		// Query for getting SubDept List
	    	 query.append("select  feedback.status,count(*) from asset_complaint_status as feedback ");
	    	 query.append(" inner join department dept on feedback.to_dept_subdept= dept.id");
	    	 if (status.equalsIgnoreCase("All")) {
	    		 query.append(" where dept.id="+dept_subdeptid+" and feedback.moduleName='CASTM' group by feedback.status order by feedback.status");
			}
	    	 else if (status.equalsIgnoreCase("Resolved")) {
	    		 query.append(" where dept.id="+dept_subdeptid+" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' and feedback.moduleName='CASTM' group by feedback.status order by feedback.status");
			}
	     System.out.println("Counter Counterdfgrt   "+query.toString());
		Session session = null;  
		Transaction transaction = null;  
		try 
		 {  
			session=connection.getCurrentSession(); 
			transaction = session.beginTransaction(); 
			dashDeptList=session.createSQLQuery(query.toString()).list();  
			transaction.commit(); 
		 }
		catch (Exception ex)
			{transaction.rollback();} 
	
		return dashDeptList;
	 }
	
	
	@SuppressWarnings("unchecked")
	public List getAnalyticalData(String reportFor,String fromDate,String toDate,String dept,String tosubdept,String loginType,String empName, String searchField, String searchString,String searchOper, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			if (reportFor.equalsIgnoreCase("C")) {
				query.append("select catg.id,catg.categoryName,count(*) as counter from asset_complaint_status as feedback");
			}
			else if (reportFor.equalsIgnoreCase("E")) {
				query.append("select emp.empName,count(*) as counter  from asset_complaint_status as feedback");
			}
			
			query.append(" inner join department dept1 on feedback.by_dept= dept1.id");
			query.append(" inner join department dept2 on feedback.to_dept= dept2.id");
			query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
			query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
			if (reportFor.equalsIgnoreCase("C")) {
				query.append(" where feedback.moduleName='ASTM' and open_date between '"+fromDate+"' and '"+toDate+"' and  dept2.id="+dept+"");
				/*if (loginType!=null && loginType.equalsIgnoreCase("H")) {
					if (dept!=null && !dept.equals("-1") && tosubdept!=null && tosubdept.equals("-1")) {
						query.append(" and  dept2.id="+dept+" ");
					}
					else if (dept!=null && !dept.equals("-1") && tosubdept!=null && !tosubdept.equals("-1")) {
						query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="+dept+")");
					}
				}
				else if (loginType!=null && loginType.equalsIgnoreCase("N")) {
						query.append(" and feedback.feed_by='"+empName+"'");
				}*/
			}
			else if (reportFor.equalsIgnoreCase("E")) {
				query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
				query.append(" where feedback.moduleName='HDM' and open_date between '"+fromDate+"' and '"+toDate+"'");
				if (dept!=null && !dept.equals("-1") && tosubdept!=null && tosubdept.equals("-1")) {
					query.append(" and  dept2.id="+dept+" ");
				}
				else if (dept!=null && !dept.equals("-1") && tosubdept!=null && !tosubdept.equals("-1")) {
					query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid="+dept+")");
				}
			}
			
			if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase("")) {
				query.append(" and");

				if (searchOper.equalsIgnoreCase("eq")) {
					query.append(" " + searchField + " = '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("cn")) {
					query.append(" " + searchField + " like '%"
							+ searchString + "%'");
				} else if (searchOper.equalsIgnoreCase("bw")) {
					query.append(" " + searchField + " like '"
							+ searchString + "%'");
				} else if (searchOper.equalsIgnoreCase("ne")) {
					query.append(" " + searchField + " <> '" + searchString
							+ "'");
				} else if (searchOper.equalsIgnoreCase("ew")) {
					query.append(" " + searchField + " like '%"
							+ searchString + "'");
				}
				}
				if (reportFor.equalsIgnoreCase("C")) {
					query.append(" group by catg.categoryName order by counter desc  limit 10");
				}
				else if (reportFor.equalsIgnoreCase("E")) {
					query.append(" group by emp.empName order by counter desc  limit 10");
				}
				System.out.println("Category Query  "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
