package com.Over2Cloud.ctrl.wfpm.report.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

public class WFPMMailingReportHelper
{
	CommonOperInterface	coi	= new CommonConFactory().createInterface();

	@SuppressWarnings("unchecked")
	public List fetchActivitiesForLead(SessionFactory factory, String month, String userName)
	{
		List list = null;
		try
		{
			// For Client
			StringBuilder query1 = new StringBuilder();
			query1
					.append("select lbd.leadName, cs.statusName, lh.comment, 'Prospective Client' from lead_history as lh inner join leadgeneration as lbd on lbd.id = lh.leadId inner join client_status as cs on cs.id = lh.status where lh.convertedTo = '1' and lh.date = '");
			query1.append(month);
			query1.append("' and lh.actionBy IN (");
			query1.append(userName);
			query1.append(") ");
			list = coi.executeAllSelectQuery(query1.toString(), factory);

			// For Associate
			StringBuilder query2 = new StringBuilder();
			query2
					.append("select lbd.leadName, cs.statusName, lh.comment, 'Prospective Associate' from lead_history as lh inner join leadgeneration as lbd on lbd.id = lh.leadId inner join associatestatus as cs on cs.id = lh.status where lh.convertedTo = '2'  and lh.date = '");
			query2.append(month);
			query2.append("' and lh.actionBy IN (");
			query2.append(userName);
			query2.append(") ");
			list.addAll(coi.executeAllSelectQuery(query2.toString(), factory));

			// For Snooze
			StringBuilder query3 = new StringBuilder();
			query3
					.append("select lbd.leadName, 'NA', lh.comment, 'Lost' from lead_history as lh inner join leadgeneration as lbd on lbd.id = lh.leadId where lh.convertedTo = '3' and lh.date = '");
			query3.append(month);
			query3.append("' and lh.actionBy IN (");
			query3.append(userName);
			query3.append(") ");
			list.addAll(coi.executeAllSelectQuery(query3.toString(), factory));

			// For Lost
			StringBuilder query4 = new StringBuilder();
			query4
					.append("select lbd.leadName, cs.lostReason, lh.comment, 'Lost' from lead_history as lh inner join leadgeneration as lbd on lbd.id = lh.leadId inner join lostoportunity as cs on cs.id = lh.status where lh.convertedTo = '4' and lh.date = '");
			query4.append(month);
			query4.append("' and lh.actionBy IN (");
			query4.append(userName);
			query4.append(") ");
			list.addAll(coi.executeAllSelectQuery(query4.toString(), factory));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public List fetchActivitiesForClient(SessionFactory factory, String month, String userName)
	{
		List list = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select cbd.clientName, off.subofferingname,  cs.statusName, cta.comment, cta.maxDateTime ");
			query.append("from client_takeaction as cta, client_contact_data as ccd, ");
			query.append("offeringlevel3 as off, client_status as cs, client_basic_data as cbd, client_offering_mapping as com ");
			query.append("where ");
			query.append("com.clientName = cbd.id ");
			query.append("and ccd.clientName = cbd.id ");
			query.append("and cta.contacId = ccd.id ");
			query.append("and off.id = cta.offeringId ");
			query.append("and cs.id = cta.statusId ");
			query.append("and cta.offeringId = com.offeringId ");
			query.append("and com.isConverted = '0' ");
			query.append("and cta.isFinished = '0' ");
			query.append("and cta.isFinished = '0' ");
			query.append("and cbd.userName IN(");
			query.append(userName);
			query.append(") and cta.date like '");
			query.append(month);
			query.append("%' order by maxDateTime");
			// System.out.println("query:" + query);

			list = coi.executeAllSelectQuery(query.toString(), factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List fetchActivitiesForAssociate(SessionFactory factory, String month, String userName)
	{
		List list = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select cbd.associateName, off.subofferingname, cs.statusname, cta.comment, cta.maxDateTime ");
			query.append("from associate_takeaction as cta, associate_contact_data as ccd, ");
			query.append("offeringlevel3 as off, associatestatus as cs, associate_basic_data as cbd, associate_offering_mapping as com ");
			query.append("where ");
			query.append("com.associateName = cbd.id ");
			query.append("and ccd.associateName = cbd.id ");
			query.append("and cta.contacId = ccd.id ");
			query.append("and off.id = cta.offeringId ");
			query.append("and cs.id = cta.statusId ");
			query.append("and cta.offeringId = com.offeringId ");
			query.append("and com.isConverted = '0' ");
			query.append("and cta.isFinished = '0' ");
			query.append("and cbd.userName IN(");
			query.append(userName);
			query.append(") and cta.date like '");
			query.append(month);
			query.append("%' order by maxDateTime");
			list = coi.executeAllSelectQuery(query.toString(), factory);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> fetchKPIReport(String empId, SessionFactory factory)
	{
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		try
		{
			StringBuilder query2 = new StringBuilder();
			query2
					.append("select kauto.kpiId, count(kauto.kpiId) from kpi_autofill as kauto inner join target_for_kpi as tfk on tfk.kpiId = kauto.kpiId where kauto.date like '");
			query2.append(DateUtil.getCurrentDateYearMonth());
			query2.append("%' and kauto.empId = '");
			query2.append(empId);
			query2.append("' and tfk.applicableFrom IN (select max(applicableFrom) from target_for_kpi where tfk.empID = '");
			query2.append(empId);
			query2.append("') group by kauto.KPIId");
			List list2 = coi.executeAllSelectQuery(query2.toString(), factory);
			Map<String, String> map = CommonHelper.convertListToMap(list2,false);

			StringBuilder query1 = new StringBuilder();
			query1
					.append("select kpi.id, kpi.kpi, tfk.targetValue, tfk.applicableFrom from krakpicollection as kpi inner join target_for_kpi as tfk on tfk.kpiId = kpi.id where tfk.empID = '");
			query1.append(empId);
			query1.append("' and tfk.applicableFrom IN (select max(applicableFrom) from target_for_kpi where tfk.empID = '");
			query1.append(empId);
			query1.append("')");
			List list1 = coi.executeAllSelectQuery(query1.toString(), factory);
			if (list1 != null && !list1.isEmpty())
			{
				StringBuilder query3 = null;
				String todayTarget = null;
				ArrayList<String> childList = null;
				for (Object obj : list1)
				{
					childList = new ArrayList<String>();
					Object[] data = (Object[]) obj;
					query3 = new StringBuilder();
					query3.append("select count(*) from kpi_autofill where empId = '");
					query3.append(empId);
					query3.append("' and kpiId = '");
					query3.append(CommonHelper.getFieldValue(data[0]));
					query3.append("' and date = '");
					query3.append(DateUtil.getCurrentDateUSFormat());
					query3.append("'");
					List list3 = coi.executeAllSelectQuery(query3.toString(), factory);
					if (list3 != null && !list3.isEmpty()) todayTarget = CommonHelper.getFieldValue(list3.get(0));
					childList.add(CommonHelper.getFieldValue(data[1]));
					childList.add(CommonHelper.getFieldValue(data[2]));
					childList.add(map.get(CommonHelper.getFieldValue(data[0])));
					childList.add(todayTarget);
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
}
