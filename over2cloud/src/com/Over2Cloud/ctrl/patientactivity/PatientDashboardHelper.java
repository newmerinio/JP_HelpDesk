package com.Over2Cloud.ctrl.patientactivity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.opensymphony.xwork2.ActionSupport;

public class PatientDashboardHelper extends ActionSupport {
	
	@SuppressWarnings("unchecked")
	public List getSubDeptListByUID(String loginType,String dept_id,String empName,SessionFactory connection)
	 {
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
			if (loginType.equalsIgnoreCase("H")) {
				query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept ");
				query.append(" inner join feedback_type fbtype on subdept.id= fbtype.dept_subdept ");
				query.append(" inner join department dept on subdept.deptid= dept.id  ");
				query.append(" where dept.id="+dept_id+" order by subdept.subdeptname ");
			}
			else if (loginType.equalsIgnoreCase("M")) 
			{
				query.append("select distinct dept.id,dept.deptName from department as dept");
				query.append(" inner join subdepartment as subdept on dept.id=subdept.deptid");
				query.append(" inner join feedback_type as feedtype on subdept.id=feedtype.dept_subdept");
				query.append(" where feedtype.hide_show='Active' and feedtype.moduleName='HDM' order by dept.deptName ");
			}
			else if (loginType.equalsIgnoreCase("N")) {
				query.append("select distinct subdept.id,subdept.subdeptname from feedback_status as feedstatus");
				query.append(" inner join subdepartment subdept on feedstatus.to_dept_subdept=subdept.id ");
				query.append(" where feedstatus.feed_by='"+empName+"'");
			}
			//System.out.println("User wise Query Dept Subdept::"+query);
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

	public List getEmpData(String empId,SessionFactory connection)
	 {
		
	//	System.out.println("Invoked....");
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
				query.append(" select distinct pva.relationship_mgr,emp.empName from patient_visit_action as pva " +
						" left join offeringlevel3 as off on off.id = pva.offeringlevel3 " +
						" left join patient_basic_data as pbd on pbd.id = pva.patient_name  " +
						" left join patientcrm_status_data as pcs on pcs.id = pva.current_activity " +
						" left join patientcrm_status_data as pcs2 on pcs2.id = pva.next_activity " +
						" left join compliance_contacts as cc on cc.id = pva.relationship_mgr " +
						" left join employee_basic as emp on emp.id = cc.emp_id");
	if(empId.trim().length()>0)
	{
		query.append(" where pva.relationship_mgr='"+empId+"'");
	}
	//System.out.println(query.toString());			
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
	
	public List getDashboardCounter0(String status,String empId,SessionFactory connection,String fromDate,String toDate)
	{
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
	//	System.out.println(fromDate+" "+toDate);
		if(status.equalsIgnoreCase("0"))
		{
			query.append("Select count(*)  from patient_visit_action where maxDateTime between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"' and relationship_mgr='"+empId+"'");
		}
		if(status.equalsIgnoreCase("1"))
		{
			query.append(" Select count(*)  from patient_visit_action where SUBSTRING_INDEX(maxDateTime,' ',1) < '"+DateUtil.convertDateToUSFormat(toDate)+"' and relationship_mgr='"+empId+"'");
		}
		if(status.equalsIgnoreCase("2"))
		{
			query.append(" Select count(*) from patient_visit_action as pva inner join patientcrm_status_data as psd on psd.id=pva.next_activity where psd.status='done' and pva.maxDateTime between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"' and pva.relationship_mgr='"+empId+"'");
		}
		Session session = null;  
			Transaction transaction = null;  
			try 
			 {  
			//	System.out.println(query.toString());
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				subdeptList=session.createSQLQuery(query.toString()).list();  
				transaction.commit(); 
			 }
			catch (Exception ex)
				{transaction.rollback();} 
				
			return subdeptList;
   }
	
	public List getDashboardBloodCounter(String bloodId,SessionFactory connection,String fromDate,String toDate)
	{
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		System.out.println(fromDate+" "+toDate);
		query.append(" select blood_group,count(*) from patient_basic_data where blood_group='"+bloodId+"' and date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'");
		Session session = null;  
			Transaction transaction = null;  
			try 
			 {  
				System.out.println(query.toString());
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				subdeptList=session.createSQLQuery(query.toString()).list();  
				transaction.commit(); 
			 }
			catch (Exception ex)
				{transaction.rollback();} 
				
			return subdeptList;
   }
	
	public List getAgeCounter(String age1,String age2,SessionFactory connection,String fromDate,String toDate)
	{
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		System.out.println(fromDate+" "+toDate);
		query.append(" select count(*) from patient_basic_data where age between '"+age1+"' and '"+age2+"' and date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"'");
		Session session = null;  
			Transaction transaction = null;  
			try 
			 {  
				System.out.println(query.toString());
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				subdeptList=session.createSQLQuery(query.toString()).list();  
				transaction.commit(); 
			 }
			catch (Exception ex)
				{transaction.rollback();} 
				
			return subdeptList;
   }	
	
	public List getEmpName(String empId,SessionFactory connection)
	{
		List  subdeptList = new ArrayList();
		StringBuffer query=new StringBuffer();
		query.append(" select distinct pva.relationship_mgr,emp.empName from patient_visit_action as pva " +
						" left join offeringlevel3 as off on off.id = pva.offeringlevel3 " +
						" left join patient_basic_data as pbd on pbd.id = pva.patient_name  " +
						" left join patientcrm_status_data as pcs on pcs.id = pva.current_activity " +
						" left join patientcrm_status_data as pcs2 on pcs2.id = pva.next_activity " +
						" left join compliance_contacts as cc on cc.id = pva.relationship_mgr " +
						" left join employee_basic as emp on emp.id = cc.emp_id where pva.relationship_mgr='"+empId+"'");
		Session session = null;  
			Transaction transaction = null;  
			try 
			 {  
				System.out.println(query.toString());
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				subdeptList=session.createSQLQuery(query.toString()).list();  
				transaction.commit(); 
			 }
			catch (Exception ex)
				{transaction.rollback();} 
				
			return subdeptList;
   }
	
}