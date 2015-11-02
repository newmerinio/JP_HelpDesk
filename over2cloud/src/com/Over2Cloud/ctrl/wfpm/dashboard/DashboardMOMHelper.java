package com.Over2Cloud.ctrl.wfpm.dashboard;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class DashboardMOMHelper
{

	public JSONObject fetchEntityNameAndAddress(SessionFactory factory, ActivityType activityType, int id)
	{
		JSONObject jsonObject = null;
		try
		{
	//Systemem.out.println("4444444444444444444444444 activityType:" + activityType);
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (activityType == ActivityType.client)
			{
				query.append("select cbd.clientName, cbd.address ");
				query.append("from client_basic_data as cbd, client_contact_data as ccd, client_takeaction as cta ");
				query.append("where cta.contacId = ccd.id ");
				query.append("and ccd.clientName = cbd.id ");
				query.append("and cta.id = ");
				query.append(id);
			}
			else if (activityType == ActivityType.associate)
			{
				query.append("select cbd.associateName, cbd.address ");
				query.append("from associate_basic_data as cbd, associate_contact_data as ccd, associate_takeaction as cta ");
				query.append("where cta.contacId = ccd.id ");
				query.append("and ccd.associateName = cbd.id ");
				query.append("and cta.id = ");
				query.append(id);
			}

			//System.out.println("fetchEntityNameAndAddress#Query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				Object[] object = (Object[]) list.get(0);
				jsonObject = new JSONObject();
				jsonObject.put("NAME", (object[0] == null || object[0].toString().equals("")) ? "NA" : object[0].toString());
				jsonObject.put("ADDRESS", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return jsonObject;
	}

	public JSONArray fetchEntityContactPersonByCTAId(SessionFactory factory, ActivityType activityType, int id)
	{
		JSONArray jsonArray = null;
		try
		{
			//System.out.println("4444444444444444444444444 activityType:" + activityType);
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (activityType == ActivityType.client)
			{
				query.append("select ccd.id, ccd.personName ");
				query.append("from client_contact_data as ccd, client_contact_data as ccd1, client_takeaction as cta ");
				query.append("where ccd.clientName = ccd1.clientName ");
				query.append("and ccd1.id = cta.contacId ");
				query.append("and cta.id = ");
				query.append(id);
			}
			else if (activityType == ActivityType.associate)
			{
				query.append("select ccd.id, ccd.name ");
				query.append("from associate_contact_data as ccd, associate_contact_data as ccd1, associate_takeaction as cta ");
				query.append("where ccd.associateName = ccd1.associateName ");
				query.append("and ccd1.id = cta.contacId ");
				query.append("and cta.id =  ");
				query.append(id);
			}

			//System.out.println("fetchContactPersonByCTAId#Query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), factory);
			if (list != null && list.size() > 0)
			{
				jsonArray = new JSONArray();
				JSONObject jsonObject = null;
				for (Object obj : list)
				{
					Object[] object = (Object[]) obj;
					jsonObject = new JSONObject();
					jsonObject.put("ID", object[0].toString());
					jsonObject.put("NAME", (object[1] == null || object[1].toString().equals("")) ? "NA" : object[1].toString());

					jsonArray.add(jsonObject);
					//System.out.println("jsonArray.size():" + jsonArray.size());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return jsonArray;
	}

}
