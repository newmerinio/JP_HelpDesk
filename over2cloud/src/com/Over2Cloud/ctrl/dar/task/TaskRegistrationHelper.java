package com.Over2Cloud.ctrl.dar.task;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.DarSubmissionPojoBean;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;

public class TaskRegistrationHelper 
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	@SuppressWarnings("rawtypes")
	public List getAllotedToName(String moduleName,String userName,SessionFactory connectionSpace)
	{
		List dataList =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			StringBuilder query=new StringBuilder();
			
			query.append("SELECT contact.id,emp.empName from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" WHERE contact.forDept_id IN ( ");
			query.append("select emp.deptname as deptid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where  contact.moduleName='"+moduleName+"' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id");
			query.append(" ) AND moduleName= '"+moduleName+"' AND contact.work_status='0' AND emp.flag='0' ") ;
			if (moduleName!=null && moduleName.equalsIgnoreCase("DAR")) 
			{
				query.append("  AND contact.level<5") ;
			}
			query.append(" ORDER BY emp.empName ASC ");
			//System.out.println("Common Helper Class "+query.toString());
			dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	@SuppressWarnings("rawtypes")
	public String getContactIdForTaskName(String moduleName,String userName,SessionFactory connectionSpace)
	{
		StringBuilder contactId=new StringBuilder();
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			
			StringBuilder query=new StringBuilder();
			query.append("select contact.id from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"+moduleName+"' and uac.userID='");
			query.append(userName + "' ");
			List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
				{
					Object object = (Object) iterator.next();
					contactId.append(getValueWithNullCheck(object)+",");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return contactId.toString();
	}
	@SuppressWarnings("rawtypes")
	public String[] getEmpDetailsByUserName(String moduleName,String userName,SessionFactory connectionSpace)
	{
		String[] values =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
			
			StringBuilder query=new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='"+moduleName+"' and uac.userID='");
			query.append(userName + "' ");
			//System.out.println("Common Helper Class "+query.toString());
			List dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				values=new String[7];
				Object[] object=(Object[])dataList.get(0);
				values[0]=getValueWithNullCheck(object[0]);
				values[1]=getValueWithNullCheck(object[1]);
				values[2]=getValueWithNullCheck(object[2]);
				values[3]=getValueWithNullCheck(object[3]);
				values[4]=getValueWithNullCheck(object[4]);
				values[5]=getValueWithNullCheck(object[5]);
				values[6]=getValueWithNullCheck(object[6]);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	

	@SuppressWarnings("rawtypes")
	public String getContactListForReports(String userEmpID,String userContID,SessionFactory connectionSpace)
	{
		StringBuilder contactIds=new StringBuilder();
		try 
		{
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			String strQuery = "SELECT cc.id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='DAR' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpID+")";
			System.out.println("SHAREING QUERY :::  "+strQuery);
			ComplianceCommonOperation CCO=new ComplianceCommonOperation();
			
			List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
			if (shareDataCount != null && shareDataCount.size() > 0)
			{
				for (int i = 0; i < shareDataCount.size(); i++)
				{
					contactIds.append(shareDataCount.get(i).toString() + ",");
					String contactId=CCO.getMappedAllContactId(shareDataCount.get(i).toString(),"DAR");
					contactIds.append(contactId);
				}
			}
			String contactId=CCO.getMappedAllContactId(userEmpID,"DAR");
			if (contactId!=null && !contactId.equalsIgnoreCase("")) 
			{
				contactIds.append(contactId);
			}
			if(userContID!=null)
			{
				contactIds.append(userContID);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return contactIds.toString();
	}
	@SuppressWarnings("rawtypes")
	public List<DarSubmissionPojoBean> getProjectDueStatus(String contactId,String date,SessionFactory connectionSpace) 
	{
		List<DarSubmissionPojoBean> list =null;
		try 
		{
			 DARReportHelper DAH=new DARReportHelper();
			 CommonOperInterface cbt=new CommonConFactory().createInterface();
			 DarSubmissionPojoBean dsp;
			 list =new LinkedList<DarSubmissionPojoBean>();
			 StringBuilder query= new StringBuilder();
			 query.append("SELECT tr.taskname,ty.tasktype,tr.priority,emp1.empName as allotedto,emp2.empName as allotedby,tr.clientFor,tr.cName,tr.offering, ");
			 query.append(" tr.intiation,dr.compercentage,MAX(dr.tactionstatus) AS TODAY_WORK ,MAX(tr.status) AS CURRENT_STATUS,tr.id AS TASK_ID,tr.completion,tr.technical_Date,tr.functional_Date FROM task_registration AS tr ");
			 query.append(" LEFT JOIN compliance_contacts AS cc1 ON tr.allotedto=cc1.id  ");
			 query.append(" LEFT JOIN compliance_contacts AS cc2 ON tr.allotedby=cc2.id  ");
			 query.append(" LEFT JOIN employee_basic emp1 ON cc1.emp_id= emp1.id  ");
			 query.append(" LEFT JOIN employee_basic emp2 ON cc2.emp_id= emp2.id   ");
			 query.append(" LEFT JOIN dar_submission as dr ON dr.taskname=tr.id  ");
			 query.append(" LEFT JOIN task_type ty ON tr.tasktype= ty.id   ");
			 query.append(" WHERE tr.completion='"+date+"' AND allotedto IN ("+contactId+") ");
			 query.append(" OR tr.completion='"+date+"' OR tr.technical_Date ='"+date+"' OR tr.functional_Date='"+date+"' ");
			 query.append(" GROUP BY tr.id");
			 System.out.println("QUERY IS AS  :::  "+query.toString());
			 List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			 if (data!=null && data.size()>0) 
			 {
				 String clientVal=null;
				 String clientData=null;
				 for (Iterator iterator = data.iterator(); iterator.hasNext();) 
				 {
					dsp=new DarSubmissionPojoBean();
					Object[] object = (Object[]) iterator.next();
					dsp.setTasknameee(getValueWithNullCheck(object[0]));
					dsp.setTaskType(getValueWithNullCheck(object[1]));
					dsp.setPriority(getValueWithNullCheck(object[2]));
					dsp.setAllotedtoo(getValueWithNullCheck(object[3]));
					dsp.setAllotedbyy(getValueWithNullCheck(object[4]));
					clientVal=object[5].toString();
					if (object[5].toString().equalsIgnoreCase("PA")) 
					{
						dsp.setClientFor("Prospect Associate");
					}
					else if (object[5].toString().equalsIgnoreCase("PC")) 
					{
						dsp.setClientFor("Prospect Client");
					}
					else if (object[5].toString().equalsIgnoreCase("EC")) 
					{
						dsp.setClientFor("Existing Client");
					}
					else if (object[5].toString().equalsIgnoreCase("EA")) 
					{
						dsp.setClientFor("Existing Associate");
					}
					else if (object[5].toString().equalsIgnoreCase("N")) 
					{
						dsp.setClientFor("New");
					}
					else if (object[5].toString().equalsIgnoreCase("IN")) 
					{
						dsp.setClientFor("Internal");
					}
					clientData=object[6].toString();
					dsp.setClientName(getValueWithNullCheck(object[6]));
					dsp.setOfferingName(DAH.offeringName(clientVal,clientData ,object[7].toString(),connectionSpace));
					dsp.setInitiondate(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[8])));
					dsp.setCompercentage(getValueWithNullCheck(object[9]));
					dsp.setToday(getValueWithNullCheck(object[10]));
					dsp.setId(Integer.parseInt(object[12].toString()));
					if (object[13]!=null && object[13].toString().equalsIgnoreCase(date)) 
					{
						dsp.setStatuss("Completion");
					}
					else if(object[13]!=null && object[13].toString().equalsIgnoreCase(DateUtil.getNewDate("day",1,date)))
					{
						dsp.setStatuss("Validate");
					}
					else if(object[14]!=null && object[14].toString().equalsIgnoreCase(date))
					{
						dsp.setStatuss("Functional Review");
					}
					else if(object[15]!=null && object[15].toString().equalsIgnoreCase(date))
					{
						dsp.setStatuss("Technical Review");
					}
					list.add(dsp);
				 }
			 
			 }
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
	public List fetchUnAllotedProjects(SessionFactory connectionSpace)
	{
		List dataList =null;
		try 
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder();
			
			query.append(" SELECT id,taskname FROM task_registration WHERE allot_status=0 ");
			//System.out.println("Common Helper Class "+query.toString());
			dataList=coi.executeAllSelectQuery(query.toString(), connectionSpace);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	
}
