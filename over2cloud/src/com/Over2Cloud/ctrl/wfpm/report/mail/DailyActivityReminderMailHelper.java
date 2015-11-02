package com.Over2Cloud.ctrl.wfpm.report.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class DailyActivityReminderMailHelper {
	CommonOperInterface	coi	= new CommonConFactory().createInterface();

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> dailyActivityReminder(String cId, SessionFactory factory)
	{
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		try
		{
			StringBuilder query2 = new StringBuilder();
			query2
					.append("select id, userName from client_takeaction group by userName");
			
			List list2 = coi.executeAllSelectQuery(query2.toString(), factory);
			Map<String, String> map = CommonHelper.convertListToMap(list2,false);
			StringBuilder query3 = null;
			query3 = new StringBuilder();
			query3.append("select cbd.clientName, ccd.personName, offl3.subofferingname, cst.statusName, (");
			query3.append("select cstt.statusName from client_takeaction as ctaa ");
			query3.append("left join client_status as cstt on cstt.id = ctaa.statusId ");
			query3.append("where ctaa.maxDateTime like '");
			query3.append(DateUtil.getNextDateAfter(-1));
			query3.append("%'");
			query3.append("and ctaa.userName IN(");
			query3.append(cId);
			query3.append(")) 'Last Activity'");
			query3.append(" from client_takeaction as cta");
					
			query3.append(" left join client_contact_data as ccd on ccd.id = cta.contacId");
			query3.append(" left join client_basic_data as cbd on cbd.id = ccd.clientName");
			query3.append(" left join offeringlevel3 as offl3 on offl3.id = cta.offeringId");
			query3.append(" left join client_status as cst on cst.id = cta.statusId");
			query3.append(" where cta.userName IN(");
			
			query3.append(cId);
			query3.append(") and cta.maxDateTime like '");
			query3.append(DateUtil.getCurrentDateUSFormat());
			query3.append("%' ");
			//System.out.println("query3.toString()>"+query3.toString());
			
			List list3 = coi.executeAllSelectQuery(query3.toString(), factory);
			if (list3 != null && !list3.isEmpty() && list3.size()>0)
			{
				ArrayList<String> childList = null;
				for (Object obj : list3)
				{
					childList = new ArrayList<String>();
					Object[] data = (Object[]) obj;
					childList.add(CommonHelper.getFieldValue(data[0]));
					childList.add(CommonHelper.getFieldValue(data[1]));
					childList.add(CommonHelper.getFieldValue(data[2]));
					childList.add(CommonHelper.getFieldValue(data[3]));
					childList.add(CommonHelper.getFieldValue(data[4]));
					
					mainList.add(childList);
				}
			}
			else
			{
				mainList.add(new ArrayList<String>());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mainList;
	}

	
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> activityMailDataForRefContact(String cId, String maxDateTime, String contactId,String offeringId, SessionFactory factory)
	{
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		try
		{
			StringBuilder query2 = new StringBuilder();
			query2.append("select id, userName from client_takeaction group by userName");
			
			List list2 = coi.executeAllSelectQuery(query2.toString(), factory);
			Map<String, String> map = CommonHelper.convertListToMap(list2,false);
			StringBuilder query3 = null;
			query3 = new StringBuilder();
			query3.append("select ctaa.maxDateTime ,cstt.statusName,ctaa.comment from client_takeaction as ctaa");
			query3.append(" left join client_status as cstt on cstt.id = ctaa.statusId");
			query3.append(" where ctaa.maxDateTime <='");
			query3.append(maxDateTime);
			query3.append("' and ctaa.userName IN(");
			query3.append(cId);
			query3.append(") and ctaa.contacId = '");
			query3.append(contactId);
			query3.append("' and  ctaa.offeringId = '");
			query3.append(offeringId);
			query3.append("' order by ctaa.id desc");
			
			System.out.println("query3.toString()>"+query3.toString());
			
			List list3 = coi.executeAllSelectQuery(query3.toString(), factory);
			if (list3 != null && !list3.isEmpty())
			{
				ArrayList<String> childList = null;
				for (Object obj : list3)
				{
					childList = new ArrayList<String>();
					Object[] data = (Object[]) obj;
					childList.add(CommonHelper.getFieldValue(DateUtil.convertDateToIndianFormat(data[0].toString().split(" ")[0])+" "+data[0].toString().split(" ")[1]));
					childList.add(CommonHelper.getFieldValue(data[1]));
					childList.add(CommonHelper.getFieldValue(data[2]));
					
					mainList.add(childList);
				}
			}
			else
			{
				mainList.add(new ArrayList<String>());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mainList;
	}
	
	public String getContactIdByEmpIdForMail(String empId, String deptId, SessionFactory connection)
	{
		String allIds = null;
		try
		{
			/*CommonHelper ch = new CommonHelper();
			String empIdAll = ch.getLevelHierarchy(CommonHelper.moduleName, empId);
			empIdAll += "," + empId;*/

			StringBuilder query = new StringBuilder();
			query.append("select id from compliance_contacts where emp_id IN(");
			query.append(empId);
			query.append(") and forDept_id ='");
			query.append(deptId);
			query.append("'");
			List list = coi.executeAllSelectQuery(query.toString(), connection);
			if (list != null && !list.isEmpty())
			{
				if(list.get(0) != null && !list.get(0).toString().equalsIgnoreCase("") && !list.get(0).toString().equalsIgnoreCase("NA"))
				{
					allIds = list.get(0).toString();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return allIds;
	}

}
