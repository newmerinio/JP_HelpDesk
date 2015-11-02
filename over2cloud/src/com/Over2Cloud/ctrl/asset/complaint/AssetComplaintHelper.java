package com.Over2Cloud.ctrl.asset.complaint;

import hibernate.common.HibernateSessionFactory;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class AssetComplaintHelper
{
	
	
	@SuppressWarnings("unchecked")
	public List getAssetDetail(String dataFor, String userName, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append("select distinct asset.serialno,IFNULL(asset.serialno,'NA') as serialno from createasset_type_master as asset_type");
				query.append(" inner join asset_detail as asset on asset_type.id=asset.assettype");
				query.append(" inner join asset_allotment_detail as asset_allot on asset.id=asset_allot.assetid");
				query.append(" inner join compliance_contacts as contact on asset_allot.employeeid=contact.id");
				query.append(" inner join employee_basic as employee on contact.emp_id=employee.id");
				query.append(" inner join useraccount as useracc on employee.useraccountid=useracc.id");
			    query.append(" where useracc.userID='"+userName+"'");
			    query.append(" ORDER BY asset_type.assetSubCat ASC");
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getAssetNewDetail(String userName, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append("select asset.id,asset.assetname,IFNULL(asset.serialno,'NA') as serialno from asset_detail as asset");
				query.append(" inner join asset_allotment_detail as asset_allot on asset.id=asset_allot.assetid");
				query.append(" inner join associate_contact_data as associate on asset_allot.outletid=associate.associateName");
				query.append(" inner join department as dept on associate.department=dept.id");
				query.append(" inner join employee_basic as employee on dept.id=employee.deptname");
				query.append(" inner join useraccount as useracc on employee.useraccountid=useracc.id");
			    query.append(" where useracc.userID='"+userName+"'");
			    query.append(" ORDER BY asset.assetname ASC");
			    // System.out.println("Asset List   "+query.toString());
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getEmp4TicketAllocation(String dept,String module, String level, SessionFactory connectionSpace) {
		List empList = new ArrayList();
		//String qry = null;
		StringBuilder query = new StringBuilder();
		try {
			query.append("select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append(" inner join department dept on contacts.forDept_id = dept.id ");
			query.append(" where contacts.level='"+level+"' and contacts.moduleName='"+module+"'");
			query.append(" and dept.id='"+ dept+ "'");
			empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
	
	@SuppressWarnings("unchecked")
	public String getLatestTicket(String seriesType,String deptid,String moduleName, SessionFactory connection)
	 {
		 String ticketno="",query="";
		 List list =null;
		 if (seriesType.equals("N")) {
			 query = "select feed_status.ticket_no from asset_complaint_status as feed_status"
                 +" where feed_status.id=(select max(id) from asset_complaint_status where moduleName='"+moduleName+"')";
		}
		 else if (seriesType.equals("D")) {
			 query = "select feed_status.ticket_no from asset_complaint_status as feed_status"
                 +" where feed_status.id=(select max(id) from asset_complaint_status where moduleName='"+moduleName+"' and to_dept_subdept in (select id from subdepartment where deptid="+deptid+"))";
		}
		  Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(query).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{transaction.rollback();} 
			
			
			 if(list!=null && list.size()>0)
	            {
				  ticketno=list.get(0).toString();
	            }
			return ticketno;
	 }
	
	
	@SuppressWarnings("unchecked")
	public List getEmpData(String empid, SessionFactory connectionSpace) {
		List empList = new ArrayList();
		String empall = null;
		try {
				empall = "select emp.empName,emp.mobOne,emp.emailIdOne,dept.id as deptid,dept.deptName,sdept.id as sdeptid,sdept.subdeptname,emp.city from employee_basic as emp"
						+ " inner join subdepartment as sdept on emp.subdept=sdept.id"
						+ " inner join department as dept on sdept.deptid=dept.id"
						+ " where emp.empId='"+ empid + "'";
			empList = new createTable().executeAllSelectQuery(empall,connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getAssetDetailById(String id,String dataFor, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append("select distinct asset.id as assetid,asset.assetname,asset.serialno,employee.empName,employee.mobOne,employee.emailIdOne,employee.empId as empid,contact.forDept_id as deptid from createasset_type_master as asset_type");
				query.append(" inner join asset_detail as asset on asset_type.id=asset.assettype");
				query.append(" inner join asset_allotment_detail as asset_allot on asset.id=asset_allot.assetid");
				query.append(" inner join compliance_contacts as contact on asset_allot.employeeid=contact.id");
				query.append(" inner join employee_basic as employee on contact.emp_id=employee.id");
				if (dataFor!=null && !dataFor.equals("") && dataFor.equalsIgnoreCase("emp"))
				{
					query.append(" where employee.empId='"+id+"'");
				}
				else if (dataFor!=null && !dataFor.equals("") && dataFor.equalsIgnoreCase("asset")) {
					query.append(" where asset.serialno='"+id+"'");
				}
			    query.append(" ORDER BY asset.assetname ASC");
			    //System.out.println("QQQQQQQ :::;   "+query.toString());
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
			    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getTicketLevel(String empid,String deptid,String module,SessionFactory connection)
	 {
		 String ticketlevel="";
		 StringBuilder sb =new StringBuilder();
		 List list =null;
		 sb.append("select contact.level from employee_basic as emp");
		 sb.append(" inner join compliance_contacts as contact on emp.id=contact.emp_id");
		 sb.append(" where contact.forDept_id='"+deptid+"' and contact.moduleName='"+module+"' and emp.id='"+empid+"'");
		 System.out.println("Ticket Level    "+sb.toString());
		  Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(sb.toString()).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{transaction.rollback();} 
			 if(list!=null && list.size()>0)
	            {
				 ticketlevel="Level"+list.get(0).toString();
	            }
			return ticketlevel;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getTicketLevelOutletWise(String empid,String outletid,String module,SessionFactory connection)
	 {
		 String ticketlevel="";
		 StringBuilder sb =new StringBuilder();
		 List list =null;
		 sb.append("select contact.level from employee_basic as emp");
		 sb.append(" inner join compliance_contacts as contact on emp.id=contact.emp_id");
		 sb.append(" Inner join outlet_to_contact_mapping as outlet on contact.id=outlet.contactid");
		 sb.append(" where contact.moduleName='"+module+"' and emp.id='"+empid+"' and outlet.outletid='"+outletid+"'");
		 System.out.println("Ticket Level    "+sb.toString());
		  Session session = null;  
			Transaction transaction = null;  
			 try 
				{
				session=connection.getCurrentSession(); 
				transaction = session.beginTransaction(); 
				list=session.createSQLQuery(sb.toString()).list();  
				transaction.commit(); 
				}
			catch (Exception ex)
				{transaction.rollback();} 
			 if(list!=null && list.size()>0)
	            {
				 ticketlevel="Level"+list.get(0).toString();
	            }
			return ticketlevel;
	 }
	
	/*@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept, String level,SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		//String qry = null;
		StringBuilder query =new StringBuilder();
		try {
				query.append("select distinct emp.id from employee_basic as emp");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query.append(" where  contacts.level='"+level+"' and contacts.forDept_Id='"+dept+"' and contacts.moduleName='ASTM'");
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String outletid, String level,SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		//String qry = null;
		StringBuilder query =new StringBuilder();
		try {
				query.append(" select distinct emp.id from employee_basic as emp");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query.append(" inner join outlet_to_contact_mapping outlet on contacts.id = outlet.contactid ");
				query.append(" where  contacts.level='"+level+"' and outlet.outletid='"+outletid+"' and outlet.sendflag='NC' and contacts.work_status!='1' and contacts.moduleName='ASTM'");
			    //System.out.println("QQQQQ 11111   "+query.toString());
				list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4EscalationFromFile(String outletid, String level,SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		//String qry = null;
		StringBuilder query =new StringBuilder();
		try {
				query.append(" select distinct emp.id,emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName from employee_basic as emp");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query.append(" inner join department dept on contacts.forDept_id = dept.id");
				query.append(" inner join outlet_to_contact_mapping outlet on contacts.id = outlet.contactid ");
				query.append(" where  contacts.level='"+level+"' and outlet.outletid='"+outletid+"' and outlet.sendflag='NC' and contacts.work_status!='1' and contacts.moduleName='ASTM'");
			    System.out.println("QQQQQ 11111   "+query.toString());
				list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String deptid, String toLevel,List empId, SessionFactory connectionSpace) {
		List<Integer> list = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		String arr =empId.toString().replace("[", "(").replace("]", ")");
		try {
			query.append("select distinct allot_to from asset_complaint_status as feed_status");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
			query.append(" where feed_status.to_dept='"+deptid+"' and contacts.level='"+toLevel+"' and feed_status.allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
			//System.out.println("QQQQQ 22222   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getPendingAllotto(String deptid, SessionFactory connectionSpace) {
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String qry = null;
		try {
			session = HibernateSessionFactory.getSession();
			qry = "select distinct allot_to from asset_complaint_status where open_date='"
					+ DateUtil.getCurrentDateUSFormat()
					+ "' and to_dept="+deptid+" and status='Pending'";
			//System.out.println("QQQQQ 33333   "+qry);
			list = new createTable()
					.executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String getRandomEmployee(List empId, SessionFactory connectionSpace) {
		/**
		 * need to work on this problem
		 */
		Session session = null;
		List list = new ArrayList();
		String allotmentId = "";
		String qry = null;
		try {
			session = HibernateSessionFactory.getSession();
			qry = "select allot_to from asset_complaint_status where open_date='"
					+ DateUtil.getCurrentDateUSFormat()
					+ "' group by allot_to having allot_to in "
					+ empId.toString().replace("[", "(").replace("]", ")")
					+ " order by count(allot_to) limit 1 ";
			//System.out.println("QQQQQ 22222   "+qry);
			list = new createTable().executeAllSelectQuery(qry, connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				allotmentId = object.toString();
			}
		}
		return allotmentId;
	}
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String ticketno,String status,String id, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            		query.append("select distinct feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            		query.append("feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
            		query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
            		query.append("associate.address,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            		query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
            		query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
            		query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
            		query.append("emp.mobOne as allotto_mobno,emp.emailIdOne  as allotto_emailid,dept1.deptName as bydept,dept2.deptName as todeptt,feedback.id as feedid,asset.assetname,asset.serialno");
            		if (status.equalsIgnoreCase("Resolved")) {
            		query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark,feedback.spare_used, emp1.empName as resolve_by, emp1.mobOne as resolve_by_mobno, emp1.emailIdOne as resolve_by_emailid");
					}
            		query.append(" from asset_complaint_status as feedback");
            		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            		query.append(" inner join compliance_contacts contact on emp.id=contact.emp_id ");
            		query.append(" inner join department dept1 on feedback.by_dept= dept1.id");
            		query.append(" inner join department dept2 on feedback.to_dept= dept2.id");
            		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id"); 	
            		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            		query.append(" inner join asset_detail asset on feedback.asset_id = asset.id"); 
            		query.append(" inner join asset_allotment_detail asset_allot on asset.id = asset_allot.assetid ");
            		query.append(" inner join associate_basic_data associate on asset_allot.outletid = associate.id ");
            		
            		if (status.equalsIgnoreCase("Resolved")) {
            		query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
					}
            		if (id!=null && !id.equals("") && status!=null && !status.equals("") && !status.equals("Re-Assign")) {
            			query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
					}
            		else if (id!=null && !id.equals("") && status!=null && !status.equals("") && status.equals("Re-Assign")) {
            			query.append(" where feedback.id='"+id+"' ");	
					}
            		else {
            		query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from asset_complaint_status)");
					}
            		//System.out.println("QQQQ    "+query.toString());
                    list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	
	@SuppressWarnings("unchecked")
	public FeedbackPojo setFeedbackDataValues(List data, String status,String deptLevel)
	{
		FeedbackPojo fbp =null;
		if (data!=null && data.size()>0) {
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				fbp = new FeedbackPojo();
				fbp.setTicket_no(object[0].toString());
				if (object[1]!=null && !object[1].toString().equals("")) {
					fbp.setFeed_registerby(DateUtil.makeTitle(object[1].toString()));
				} else {
					fbp.setFeed_registerby("NA");
				}
				if (object[2]!=null && !object[2].toString().equals("")) {
					fbp.setFeedback_by_mobno(object[2].toString());
				} else {
					fbp.setFeedback_by_mobno("NA");
				}
				if (object[3]!=null && !object[3].toString().equals("")) {
					fbp.setFeedback_by_emailid(object[3].toString());
				} else {
					fbp.setFeedback_by_emailid("NA");
				}
				
				fbp.setFeedback_allot_to(object[4].toString());
				fbp.setFeedtype(object[5].toString());
				fbp.setFeedback_catg(object[6].toString());
				fbp.setFeedback_subcatg(object[7].toString());
				fbp.setFeed_brief(object[8].toString());
				fbp.setFeedaddressing_time(object[9].toString());
				fbp.setLocation(object[11].toString());
				fbp.setOpen_date(object[12].toString());
				fbp.setOpen_time(object[13].toString());
				fbp.setEscalation_date(object[14].toString());
				fbp.setEscalation_time(object[15].toString());
				fbp.setLevel(object[16].toString());
				fbp.setStatus(object[17].toString());
				fbp.setVia_from(object[18].toString());
				
				
				
				/*if (status.equals("High Priority")) {
				fbp.setHp_date(object[19].toString());	
				fbp.setHp_time(object[20].toString());	
				fbp.setHp_reason(object[21].toString());	
				}
				else if (status.equals("Snooze")) {
			    fbp.setSn_reason(object[22].toString());
			    fbp.setSn_on_date(object[23].toString());
			    fbp.setSn_on_time(object[24].toString());
			    fbp.setSn_date(object[25].toString());
			    fbp.setSn_time(object[26].toString());
			    fbp.setSn_duration(object[27].toString());
				}
                else if (status.equals("Ignore")) {
                fbp.setIg_date(object[28].toString());
     			fbp.setIg_time(object[29].toString());
     			fbp.setIg_reason(object[30].toString());
				}
                else if (object[31]!=null && !object[31].toString().equals("") && object[32]!=null && !object[32].toString().equals("")) {
                fbp.setTransfer_date(object[31].toString());
         		fbp.setTransfer_time(object[32].toString());
         		fbp.setTransfer_reason(object[33].toString());	
				}
                else if (deptLevel.equals("2") && status.equals("Resolved")) {
                fbp.setResolve_date(object[42].toString());
                fbp.setResolve_time(object[43].toString());
                fbp.setResolve_duration(object[44].toString());
                fbp.setResolve_remark(object[45].toString());
                fbp.setSpare_used(object[46].toString());
                fbp.setResolve_by(object[47].toString());
                if (object[48]!=null && !object[48].toString().equals("")) {
					fbp.setResolve_by_mobno(object[48].toString());
				} else {
					fbp.setResolve_by_mobno("NA");
				}
                if (object[49]!=null && !object[49].toString().equals("")) {
					fbp.setResolve_by_emailid(object[49].toString());
				} else {
					fbp.setResolve_by_emailid("NA");
				}
				}
                else if (deptLevel.equals("1") && status.equals("Resolved")) {
                fbp.setResolve_date(object[40].toString());
                fbp.setResolve_time(object[41].toString());
                fbp.setResolve_duration(object[42].toString());
                fbp.setResolve_remark(object[43].toString());
                fbp.setResolve_by(object[44].toString());
                fbp.setSpare_used(object[45].toString());
    		    }*/
				
				if (object[34]!=null && !object[34].toString().equals("")) {
					fbp.setAction_by(object[34].toString());	
				}
				else {
					fbp.setAction_by("NA");
				}
				
				if (object[35]!=null && !object[35].toString().equals("")) {
					fbp.setMobOne(object[35].toString());	
				}
				else {
					fbp.setMobOne("NA");
				}
				if (object[36]!=null && !object[36].toString().equals("")) {
					fbp.setEmailIdOne(object[36].toString());
				} else {
					fbp.setEmailIdOne("NA");
				}
			
				if (object[37]!=null && !object[37].toString().equals("")) {
					fbp.setFeedback_by_dept(object[37].toString());
				}
				else {
					fbp.setFeedback_by_dept("NA");
				}
				if (object[38]!=null && !object[38].toString().equals("")) {
					 fbp.setFeedback_to_dept(object[38].toString());	
				}
				else {
					fbp.setFeedback_to_dept("NA");
				}
				if (object[39]!=null && !object[39].toString().equals("")) {
					 fbp.setId(Integer.parseInt(object[39].toString()));
				}
				
				if (object[40]!=null && !object[40].toString().equals("")) {
					fbp.setAsset(object[40].toString());
				}
				else {
					fbp.setAsset(object[40].toString());
				}
				if (object[41]!=null && !object[41].toString().equals("")) {
					fbp.setSerialno(object[41].toString());
				}
				else {
					 fbp.setSerialno("NA");
				}
			}
		}
		return fbp;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getOutletId(String empid, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append("select outlet.outletid from outlet_to_contact_mapping as outlet");
				query.append(" inner join compliance_contacts contact on outlet.contactid=contact.id ");
				query.append(" inner join employee_basic emp on contact.emp_id= emp.id ");
			    query.append(" where emp.id='"+empid+"'");
			    System.out.println(""+query.toString());
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail4View(String status,String fromDate,String toDate,List outletid,String newDeptId, String searchField, String searchString,String searchOper, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			    System.out.println("OutletId List  "+outletid.toString());
				query.append("select distinct feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
				query.append("feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,");
				query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query.append("associate.address,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,asset.serialno");
				if (status.equalsIgnoreCase("Resolved")) {
				query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				}
				query.append(" from asset_complaint_status as feedback");
        		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
        		query.append(" inner join compliance_contacts contact on emp.id=contact.emp_id ");
        		query.append(" inner join department dept1 on feedback.by_dept= dept1.id");
        		query.append(" inner join department dept2 on feedback.to_dept= dept2.id");
        		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id"); 	
        		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
        		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
        		query.append(" inner join asset_detail asset on feedback.asset_id = asset.id");
        		query.append(" inner join asset_allotment_detail asset_allot on asset.id = asset_allot.assetid ");
        		query.append(" inner join associate_basic_data associate on asset_allot.outletid = associate.id ");
        		
				if (status.equalsIgnoreCase("Resolved")) {
					query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
				}

				query.append(" where feedback.status='"+status+ "' and feedback.location in "+outletid.toString().replace("[", "(").replace("]", ")")+"");
				if (newDeptId!=null && !newDeptId.equals("") && !newDeptId.equals("-1")) {
					query.append(" and dept1.id='"+newDeptId+"'");
				}
				if (status.equals("Resolved")) {
					query.append(" and feedback.open_date between '"+fromDate+"' and '"+toDate+"'");
				}
			
				if (searchField != null && searchString != null
						&& !searchField.equalsIgnoreCase("")
						&& !searchString.equalsIgnoreCase("")) {
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
				
			
			query.append(" and feedback.moduleName='ASTM'");
			query.append(" ORDER BY feedback.id DESC ");
			
			System.out.println("Data Query   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String status,String fromDate,String toDate,String deptid,String newDeptId, String searchField, String searchString,String searchOper, SessionFactory connectionSpace) {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
			    //System.out.println("NNNNNN   "+newDeptId);
				query.append("select distinct feedback.id,feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,");
				query.append("feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,");
				query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
				query.append("associate.address,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
				query.append("feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby,feedback.hp_date,feedback.hp_time,");
				query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
				query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,asset.serialno");
				if (status.equalsIgnoreCase("Resolved")) {
				query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
				}
				query.append(" from asset_complaint_status as feedback");
        		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
        		query.append(" inner join compliance_contacts contact on emp.id=contact.emp_id ");
        		query.append(" inner join department dept1 on feedback.by_dept= dept1.id");
        		query.append(" inner join department dept2 on feedback.to_dept= dept2.id");
        		query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id"); 	
        		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
        		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
        		query.append(" inner join asset_detail asset on feedback.asset_id = asset.id");
        		query.append(" inner join asset_allotment_detail asset_allot on asset.id = asset_allot.assetid ");
        		query.append(" inner join associate_basic_data associate on asset_allot.outletid = associate.id ");
        		
				if (status.equalsIgnoreCase("Resolved")) {
					query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id");
				}

				query.append(" where feedback.status='"+status+ "' and dept2.id='"+deptid+"'");
				if (newDeptId!=null && !newDeptId.equals("") && !newDeptId.equals("-1")) {
					query.append(" and dept1.id='"+newDeptId+"'");
				}
				if (status.equals("Resolved")) {
					query.append(" and feedback.open_date between '"+fromDate+"' and '"+toDate+"'");
				}
			
				if (searchField != null && searchString != null
						&& !searchField.equalsIgnoreCase("")
						&& !searchString.equalsIgnoreCase("")) {
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
				
			
			query.append(" and feedback.moduleName='ASTM'");
			query.append(" ORDER BY feedback.id DESC ");
			
			System.out.println("Data Query   "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(),
					connectionSpace);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AssetComplaintPojo> setFeedbackValues(List feedValue, String deptLevel, String feedStatus)
	 {
		List<AssetComplaintPojo> feedList = new ArrayList<AssetComplaintPojo>();
		if (feedValue!=null && feedValue.size()>0) {
			if (deptLevel.equals("2")) {
				for (Iterator iterator = feedValue.iterator(); iterator
						.hasNext();) {
					Object[] obdata = (Object[]) iterator.next();
					AssetComplaintPojo  acp = new AssetComplaintPojo();
					acp.setId((Integer)obdata[0]);
					acp.setTicket_no(obdata[1].toString());
					acp.setBy_dept(obdata[2].toString());
					 if (obdata[3]!=null && !obdata[3].toString().equals("")) {
						 acp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
					}
					 else {
						 acp.setFeed_by("NA");
					}
					 
					if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
						acp.setFeed_by_mobno(obdata[4].toString());
				    }
					else {
						acp.setFeed_by_mobno("NA");
					}
					
					if (obdata[5]!=null && !obdata[5].toString().equals("")) {
						acp.setFeed_by_emailid(obdata[5].toString());
			        }
				    else {
				    	acp.setFeed_by_emailid("NA");
				    }
				 
					acp.setTo_dept(obdata[6].toString());
					acp.setAllot_to(obdata[7].toString());
					acp.setFeedType(obdata[8].toString());
					acp.setCategory(obdata[9].toString());
					acp.setSubCatg(obdata[10].toString());
					if (obdata[11]!=null && !obdata[11].toString().equals("")) {
						acp.setFeed_brief(obdata[11].toString());
			        }
				    else {
				    	acp.setFeed_brief("NA");
				    }
					
					/*acp.setFeedaddressing_time(obdata[14].toString());
					
					if (obdata[14]!=null && !obdata[14].toString().equals("") && obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("")) {
						String addr_date_time = DateUtil.newdate_AfterTime(obdata[17].toString(), obdata[18].toString(), obdata[14].toString());
						String[] add_date_time = addr_date_time.split("#");
						for (int i = 0; i < add_date_time.length; i++) {
							fbp.setFeedaddressing_date(DateUtil.convertDateToIndianFormat(add_date_time[0]));
							fbp.setFeedaddressing_time(add_date_time[1].substring(0, 5));
						}
					}
				    else {
				    	acp.setFeedaddressing_date("NA");
				    	acp.setFeedaddressing_time("NA");
				    }*/
					
					if (obdata[14]!=null && !obdata[14].toString().equals("")) {
						acp.setLocation(obdata[14].toString());
			        }
				    else {
				    	acp.setLocation("NA");
				    }
					acp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[15].toString()));
					acp.setOpen_time(obdata[16].toString().substring(0, 5));
					if (feedStatus.equalsIgnoreCase("Pending")) {
						acp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[17].toString()));
						if (obdata[18].toString()!=null && !obdata[18].toString().equals("") && !obdata[18].toString().equals("NA")) {
							acp.setEscalation_time(obdata[18].toString().substring(0, 5));
						}
						else {
							acp.setEscalation_time("NA");
						}
					}
				  
					acp.setLevel(obdata[19].toString());
					acp.setStatus(obdata[20].toString());
					if (obdata[21]!=null && !obdata[21].toString().equals("")) {
						acp.setVia_from(DateUtil.makeTitle(obdata[21].toString()));
					}
					else {
						acp.setVia_from("NA");
					}
					
					acp.setFeed_registerby(DateUtil.makeTitle(obdata[22].toString()));
					
					 if (feedStatus.equalsIgnoreCase("High Priority")) {
						 acp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[23].toString()));
						 acp.setHp_time(obdata[24].toString().substring(0, 5));
						 acp.setHp_reason(obdata[25].toString());
						}
					
					 if (feedStatus.equalsIgnoreCase("Snooze")) {
						 if (obdata[26]!=null && !obdata[26].toString().equals("")) {
							 acp.setSn_reason(obdata[26].toString());
						}
						 else {
							 acp.setSn_reason("NA");
						}
						 if (obdata[27]!=null && !obdata[27].toString().equals("")) {
							 acp.setSn_on_date(obdata[27].toString());
						}
						 else {
							 acp.setSn_on_date("NA");
						}
						 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
							 acp.setSn_on_time(obdata[28].toString());
						}
						 else {
							 acp.setSn_on_time("NA");
						}
						 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
							 acp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[29].toString()));
						}
						 else {
							 acp.setSn_upto_date("NA");
						}
						 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
							 acp.setSn_upto_time(obdata[30].toString().substring(0, 5));
						}
						 else {
							 acp.setSn_upto_time("NA");
						} 
						 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
							 acp.setSn_duration(obdata[31].toString());
						}
						 else {
							 acp.setSn_duration("NA");
						}  
						}
					
					 if (feedStatus.equalsIgnoreCase("Resolved")) {
						 if (obdata[40]!=null && !obdata[40].toString().equals("")) {
							 acp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[40].toString()));
						 }
						 else {
							 acp.setResolve_date("NA");
						}
						if (obdata[41]!=null && !obdata[41].toString().equals("")) {
							 acp.setResolve_time(obdata[41].toString().substring(0, 5));
							}
						else {
							acp.setResolve_time("NA");
							 }
						
						 if (obdata[42]!=null && !obdata[42].equals("")) {
							 acp.setResolve_duration(obdata[42].toString());
							  }
						 else {
								   if (obdata[15]!=null && !obdata[15].toString().equals("") && obdata[16]!=null && !obdata[16].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("") && obdata[42]!=null && !obdata[42].toString().equals("")) {
									   String dura1=DateUtil.time_difference(obdata[15].toString(), obdata[16].toString(), obdata[41].toString(), obdata[42].toString());
									   acp.setResolve_duration(dura1);
								   }
								   else {
									   acp.setResolve_duration("NA");
								   }
							 }
						 
						 if (obdata[43]!=null && !obdata[43].toString().equals("")) {
							 acp.setResolve_remark(obdata[43].toString());
						}
						 else {
							 acp.setResolve_remark("NA");
						}
						 acp.setResolve_by(obdata[44].toString());
						 acp.setSpare_used(obdata[45].toString());
						}
					 if (!feedStatus.equalsIgnoreCase("") && !feedStatus.equalsIgnoreCase("Pending")) {
						 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
							 acp.setAction_by(obdata[38].toString());
						}
						 else {
							 acp.setAction_by("NA");
						}
						}
					 if (obdata[39]!=null && !obdata[39].toString().equals("")) {
						 acp.setSerialno(obdata[39].toString());
					}
					 else {
						 acp.setSerialno("NA");
					}
					
					feedList.add(acp);
				}
			}
		}
		return feedList;
	 }
	
}
