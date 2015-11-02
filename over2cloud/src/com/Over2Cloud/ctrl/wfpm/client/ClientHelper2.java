package com.Over2Cloud.ctrl.wfpm.client;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.opensymphony.xwork2.ActionContext;

public class ClientHelper2 extends SessionProviderClass
{
	CommonOperInterface		coi		= new CommonConFactory().createInterface();
	public static String	industryId;
	int										idval	= 1;
//hide by Azad 2Sep2014
	/*public String fetchEntityIndustryId(CommonOperInterface coi, String id, EntityType entityType)
	{
		String subIndustryId = null;
		try
		{
			StringBuilder query = new StringBuilder();
			if (entityType == EntityType.CLIENT)
			{
				query.append("select subIndustry from client_basic_data where id = '");
				query.append(id);
				query.append("'");
			}
			else if (entityType == EntityType.ASSOCIATE)
			{
				query.append("select subIndustry from associate_basic_data where id = '");
				query.append(id);
				query.append("'");
			}

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				subIndustryId = list.get(0).toString().trim();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return subIndustryId;
	}*/
//By Azad 2sep 2014 fetch target segment	
	public String fetchEntityTargetId(CommonOperInterface coi, String id, EntityType entityType)
	{
		String targetId = null;
		try
		{
			StringBuilder query = new StringBuilder();
			if (entityType == EntityType.CLIENT)
			{
				query.append("select weightage_targetsegment from client_basic_data where id = '");
				query.append(id);
				query.append("'");
			}
			else if (entityType == EntityType.ASSOCIATE)
			{
				query.append("select subIndustry from associate_basic_data where id = '");
				query.append(id);
				query.append("'");
			}

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			
			if (list != null && list.size() > 0)
			{
				  if(list.get(0) != null)
				  {
				   targetId = list.get(0).toString().trim();
				  }
				//System.out.println("targetId*********"+targetId);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return targetId;
	}

	public List getMeter(CommonOperInterface coi, String adminId, SessionFactory connectionSpace)
	{
		List dataList = null;
		try
		{
			String query = "SELECT METER FROM tblmstdata WHERE PARENTADMINID ='"+adminId+"'";
			dataList = coi.executeAllSelectQuery(query, connectionSpace);
		}
		catch(Exception  e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public List fetchEntityOfferingId(CommonOperInterface coi, String id, EntityType entityType)
	{
		List list = null;
		try
		{
			StringBuilder query = new StringBuilder();
			String[] offeringColTable = new CommonHelper().getOfferingName();

			if (entityType == EntityType.CLIENT)
			{
				query.append("select off.id, off.");
				query.append(offeringColTable[0]);
				query.append(" from client_offering_mapping as com left join ");
				query.append(offeringColTable[1]);
				query.append(" as off on off.id = com.offeringId where com.clientName = '");
				query.append(id);
				query.append("' ");
			}
			else if (entityType == EntityType.ASSOCIATE)
			{
				query.append("select off.id, off.");
				query.append(offeringColTable[0]);
				query.append(" from associate_offering_mapping as com left join ");
				query.append(offeringColTable[1]);
				query.append(" as off on off.id = com.offeringId where com.associateName = '");
				query.append(id);
				query.append("' ");
			}
			// System.out.println("query:" + query);
			list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public Map<String, String> fetchEntityContactDepartmentId(CommonOperInterface coi, String id, EntityType entityType)
	{
		Map<String, String> deptMap = new LinkedHashMap<String, String>();
		try
		{
			StringBuilder query = new StringBuilder();
			if (entityType == EntityType.CLIENT)
			{
				query
						.append("select distinct(dept.id), dept.deptName from client_contact_data as ccd left join department as dept on dept.id = ccd.department where ccd.clientName = '");
				query.append(id);
				query.append("' ");
			}
			else if (entityType == EntityType.ASSOCIATE)
			{
				query
						.append("select distinct(dept.id), dept.deptName from associate_contact_data as ccd left join department as dept on dept.id = ccd.department where ccd.associateName = '");
				query.append(id);
				query.append("' ");
			}
			// System.out.println("query:" + query);
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					deptMap.put(CommonHelper.getFieldValue(data[0]), CommonHelper.getFieldValue(data[1]));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return deptMap;
	}

	public double calculateWeightageForOffering(CommonOperInterface coi, String offeringId, String subIndustryId, Map<String, String> dept, String clientId,
			EntityType entityType)
	{
		double weightage = 0;

		List list = fetchWeightageForOffering(coi, offeringId, subIndustryId);
		if (list != null && list.size() > 0)
		{
			for (Object obj : list)
			{
				Object[] data = (Object[]) obj;
				if (data[0] != null && data[1] != null)
				{
					if (dept.containsKey((data[0].toString().trim())))
					{
						if (isInfulenceGreaterThanThree(coi, data[0].toString().trim(), clientId, entityType))
						{
							weightage += Double.parseDouble(data[1].toString().trim());
						}
						else
						{
							weightage += Double.parseDouble(data[1].toString().trim()) / 2;
						}
						// System.out.println("data[0]:" + data[0] + "         data[1]:" +
						// data[1] + "              weightage:" + weightage);
					}
				}
			}
		}
		return weightage;
	}

	private boolean isInfulenceGreaterThanThree(CommonOperInterface coi, String dept, String clientId, EntityType entityType)
	{
		boolean flag = false;
		StringBuilder query = new StringBuilder();
		try
		{
			if (entityType == EntityType.CLIENT)
			{
				query.append("select degreeOfInfluence from client_contact_data where clientName = '");
				query.append(clientId);
				query.append("' and department = '");
				query.append(dept);
				query.append("' ");
			}
			else if (entityType == EntityType.ASSOCIATE)
			{
				query.append("select degreeOfInfluence from associate_contact_data where associateName = '");
				query.append(clientId);
				query.append("' and department = '");
				query.append(dept);
				query.append("' ");
			}

			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && !list.isEmpty())
			{
				if (Integer.parseInt(list.get(0).toString()) > 2) flag = true;
			}
		}
		catch (Exception e)
		{
		}
		return flag;
	}

	public List fetchWeightageForOffering(CommonOperInterface coi, String offeringId, String subIndustryId)
	{
		StringBuilder query = new StringBuilder();
		query
				.append("select ois.deptName, ois.weightage, dept.deptName 'ois_dept' from off_indust_subindust_dept_mapping as ois inner join department as dept on dept.id = ois.deptName where ois.offeringId = '");
		query.append(offeringId);
		query.append("' and ois.targetSegment = '"); //change by Azad 2 sep 2014
		query.append(subIndustryId);
		query.append("' and ois.weightage <> '0'");

		// System.out.println("query:" + query);
		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		return list;
	}
//modify by Tareshwar 26 Dec 2014
	public double calculateOverallWeightage(CommonOperInterface coi, String id, EntityType entityType)
	{
		double weightage = 0;

		// Fetch target segment for  client
		String targetSegmentId = fetchEntityTargetId(coi, id, entityType);
		// Fetch all mapped offering with client
		List offeringList = fetchEntityOfferingId(coi, id, entityType);
		// Fetch all contact person department
		Map<String, String> deptList = fetchEntityContactDepartmentId(coi, id, entityType);
		if (offeringList != null && offeringList.size() > 0 && deptList != null && deptList.size() > 0)
		{
			for (Object offering : offeringList)
			{
				if (offering == null) continue;
				Object[] offData = (Object[]) offering;
				weightage += calculateWeightageForOffering(coi, offData[0].toString(), targetSegmentId, deptList, id, entityType);
				// System.out.println("offering:" + offData[1] +
				// "                       weightage:" + weightage);
			}
		}
/*		weightage = (weightage * 100) / (offeringList.size() * 100);
*/		// System.out.println("weightage:" + weightage);
		  String Weightage = (String)(new DecimalFormat("##.##").format((weightage * 100) / (offeringList.size() * 100)));
		  weightage = Double.parseDouble(Weightage);
		 
		 return weightage;
	}
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> calculateIndividualWeightageForOffering(CommonOperInterface coi, String id, EntityType entityType)
	{
		  HashMap<String, HashMap<String, String>> offeringWeightageDetails = new LinkedHashMap<String, HashMap<String, String>>();
	      HashMap<String, HashMap<String, String>> offeringWeightageDetails1 = new LinkedHashMap<String, HashMap<String, String>>();
	      HashMap<String, HashMap<String, HashMap<String, String>>> offeringWeightageDetails2 = new LinkedHashMap<String, HashMap<String, HashMap<String, String>>>();
	      
		try
		{   //comment by azad 2sep 2014
			//String subIndustryId = fetchEntityIndustryId(coi, id, entityType);
			String subIndustryId = fetchEntityTargetId(coi, id, entityType);
			//this is changed by Azad
			List offeringList = fetchEntityOfferingId(coi, id, entityType);
			Map<String, String> deptList = fetchEntityContactDepartmentId(coi, id, entityType);
			if (offeringList != null && offeringList.size() > 0 && deptList != null && deptList.size() > 0)
			{
				HashMap<String, String> tempMap = null;
				HashMap<String, String> tempMap1 = null;
				for (Object offering : offeringList)
				{
					if (offering == null) continue;
					Object[] offData = (Object[]) offering;
					double weightage, totalWeightage = 0;
					tempMap = new LinkedHashMap<String, String>();
					tempMap1 = new LinkedHashMap<String, String>();
					List list = fetchWeightageForOffering(coi, offData[0].toString(), subIndustryId);
					if (list != null && list.size() > 0)
					{
						for (Object obj : list)
						{
							Object[] data = (Object[]) obj;
							if (data[0] != null && data[1] != null)
							{
								if (deptList.containsKey((data[0].toString().trim())))
								{
									if (isInfulenceGreaterThanThree(coi, data[0].toString().trim(), id, entityType))
									{
										weightage = Double.parseDouble(data[1].toString().trim());
									}
									else
									{
										weightage = Double.parseDouble(data[1].toString().trim()) / 2;
									}
									/*tempMap.put(deptList.get(data[0].toString()), " Achieved: " + weightage + " Out Of: " + data[1].toString());
									*/
									tempMap1.put(deptList.get(data[0].toString()), " Achieved: " + weightage + " Out Of: " + data[1].toString());
									totalWeightage += weightage;
								}
								else
								{
									tempMap.put(CommonHelper.getFieldValue(data[2]), " Achieved: 0 Out Of: " + data[1].toString());
								}
								// System.out.println("data[2].toString():" + data[2].toString()
								// + "         data[1]:" + data[1]);
							}
						}
					}
					offeringWeightageDetails.put(offData[1].toString() + " [" + totalWeightage + " %]", tempMap);
					offeringWeightageDetails1.put(offData[1].toString() + " [" + totalWeightage + " %]", tempMap1);
					 System.out.println("offering:" + offData[1] +
					 "                       totalWeightage:" + totalWeightage);
					 
				}
			}
			offeringWeightageDetails2.put("1",offeringWeightageDetails);
			offeringWeightageDetails2.put("2",offeringWeightageDetails1);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
	
		return offeringWeightageDetails2;
	}
	
	// weightage for Review Board Show offering wise
	public double calculateOfferingWiseWeightage(CommonOperInterface coi, String id, EntityType entityType, String offeringId)
	{
		double weightage = 0;

		// Fetch target segment for  client
		String targetSegmentId = fetchEntityTargetId(coi, id, entityType);
		// Fetch all mapped offering with client
		//List offeringList = fetchEntityOfferingId(coi, id, entityType);
		// Fetch all contact person department
		Map<String, String> deptList = fetchEntityContactDepartmentId(coi, id, entityType);
		if (deptList != null && deptList.size() > 0)
		{
			
				weightage += calculateWeightageForOffering(coi, offeringId, targetSegmentId, deptList, id, entityType);
				 System.out.println("offering:" + offeringId +
				 "                       weightage:" + weightage);
		}
		weightage = (weightage * 100) /(1 * 100);
		 //System.out.println("weightage:" + weightage);

		return weightage;
	}
	

	/*
	 * Build List of List for client edit page
	 */
	@SuppressWarnings("unchecked")
	public List<GridDataPropertyView> fetchClientBasicDataForEdition(String clientId)
	{
		List<GridDataPropertyView> mainList = null;
		try
		{
			List<GridDataPropertyView> configData = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0,
					"table_name", "client_basic_configuration");
			if (configData == null || configData.isEmpty()) return null;
			// clientDropDown = new ArrayList<ConfigurationUtilBean>();
			StringBuilder queryStart = new StringBuilder();
			StringBuilder queryEnd = new StringBuilder();
			queryStart.append("SELECT ");
			String itrTemp = "";
			for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
			{
				itrTemp = itr.next().getColomnName();
				if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time") || itrTemp.equalsIgnoreCase("status")
						|| itrTemp.equalsIgnoreCase("targetAchieved") || itrTemp.equalsIgnoreCase("weightage") || itrTemp.equalsIgnoreCase("targetAchieved")
						|| itrTemp.equalsIgnoreCase("status") || itrTemp.equalsIgnoreCase("closure_date") || itrTemp.equalsIgnoreCase("opportunity_name") || itrTemp.equalsIgnoreCase("opportunity_value"))
				{
					itr.remove();
					continue;
				}
				queryStart.append(itrTemp);
				queryStart.append(", ");
			}

			queryEnd.append(queryStart.toString().substring(0, queryStart.toString().lastIndexOf(",")));
			queryEnd.append(" FROM client_basic_data WHERE id = ");
			queryEnd.append(clientId);

			List list = coi.executeAllSelectQuery(queryEnd.toString(), connectionSpace);
			//System.out.println("queryEnd.toString()>>"+queryEnd.toString());
			if (list != null && !list.isEmpty())
			{
				Object[] listArray = (Object[]) list.get(0);
				String listVal = "";
				for (int i = 0; i < configData.size(); i++)
				{
					// System.out.println("columnName:" +
					// configData.get(i).getColomnName());
					// System.out.println("getColType:" + configData.get(i).getColType());
					// System.out.println("value:" +
					// CommonHelper.getFieldValue(listArray[i]));
					listVal = CommonHelper.getFieldValue(listArray[i]);

					if (configData.get(i).getColomnName().equalsIgnoreCase("industry"))
					{
						industryId = listVal;
					}
					configData.get(i).setValue(listVal);
				}
			}

			mainList = configData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mainList;
	}
	
	//by Azad
	public HashMap fetchClientName(String clientId)
	{   HashMap clientMap=new LinkedHashMap();;
		StringBuilder query=new StringBuilder();
		query.append("select cbd.id,cbd.clientName from client_basic_data as cbd ");
		query.append("where id !='"+clientId+"' ");
		query.append("order by cbd.clientName");
		System.out.println("query fetch client>>>>>>>>>>>"+query);
		List list=coi.executeAllSelectQuery(query.toString(), connectionSpace);
		   if(list!= null && !list.isEmpty())
		   {    
			   for(int i=0; i<list.size(); i++)
			   {     if(i==0)
			         {
				      clientMap.put("0","Not Aware");
			         }
				    Object[] ob=(Object[]) list.get(i);
				    clientMap.put(ob[0].toString(),ob[1].toString());      
				              
				   
			   }
			   
		   }
		
		return clientMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<GridDataPropertyView>> fetchClientContactDataForEdition(String clientId)
	{
		ArrayList<ArrayList<GridDataPropertyView>> containerMainList = new ArrayList<ArrayList<GridDataPropertyView>>();
		try
		{
			ArrayList<GridDataPropertyView> configData = (ArrayList<GridDataPropertyView>) Configuration.getConfigurationData("mapped_client_configuration",
					accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
			if (configData == null || configData.isEmpty()) return null;
			// clientDropDown = new ArrayList<ConfigurationUtilBean>();
			StringBuilder queryStart = new StringBuilder();
			StringBuilder queryEnd = new StringBuilder();
			queryStart.append("SELECT ");
			String itrTemp = "";
			// add extra id field to list 'configData' to be used for contact
			// edition
			GridDataPropertyView gdp = new GridDataPropertyView();
			gdp.setColomnName("id");
			gdp.setColType("T");
			configData.add(0, gdp);

			for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
			{
				itrTemp = itr.next().getColomnName();
				if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time")
						|| itrTemp.equalsIgnoreCase("clientName"))
				{
					itr.remove();
					continue;
				}
				queryStart.append(itrTemp);
				queryStart.append(", ");
			}

			queryEnd.append(queryStart.toString().substring(0, queryStart.toString().lastIndexOf(",")));
			queryEnd.append(" FROM client_contact_data WHERE clientName = '");
			queryEnd.append(clientId);
			queryEnd.append("' ");

			List list = coi.executeAllSelectQuery(queryEnd.toString(), connectionSpace);
			if (list != null && !list.isEmpty())
			{
				for (Object obj : list)
				{
					Object[] listArray = (Object[]) obj;
					String listVal = "";
					/*
					 * ArrayList<GridDataPropertyView> configDataTemp =
					 * (ArrayList<GridDataPropertyView>) configData .clone();
					 */
					ArrayList<GridDataPropertyView> configDataTemp = getConfigDataForContactTemp();
					for (int i = 0; i < configDataTemp.size(); i++)
					{
						listVal = CommonHelper.getFieldValue(listArray[i]);
						configDataTemp.get(i).setValue(listVal);
					}
					containerMainList.add(configDataTemp);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return containerMainList;
	}

	@SuppressWarnings("unchecked")
	public String fetchLastStatusForOffering(EntityType entityType, String offeringId, String id)
	{
		String status = "No Last Status !";
		StringBuilder query = new StringBuilder();
		if (entityType == EntityType.CLIENT)
		{
			query
					.append("select cst.statusName from client_status as cst inner join client_takeaction as cta on cta.statusId = cst.id inner join client_contact_data as ccd on ccd.id = cta.contacId where cta.offeringId = '");
			query.append(offeringId);
			query.append("' and ccd.clientName = '");
			query.append(id);
			query.append("' order by cta.maxDateTime desc limit 1");
		}
		else if (entityType == EntityType.PATIENT)
		{
			query.append("select cst.statusName from client_status as cst inner join patient_takeaction as cta on cta.statusId = cst.id inner join client_contact_data as ccd on ccd.id = cta.contacId where cta.offeringId = '");
			query.append(offeringId);
			query.append("' and ccd.clientName = '");
			query.append(id);
			query.append("' order by cta.maxDateTime desc limit 1");
		}
		else if (entityType == EntityType.ASSOCIATE)
		{
			query.append("select cst.statusname from associatestatus as cst inner join associate_takeaction as cta on cta.statusId = cst.id inner join associate_contact_data as ccd on ccd.id = cta.contacId where cta.offeringId = '");
			query.append(offeringId);
			query.append("' and ccd.associateName = '");
			query.append(id);
			query.append("' order by cta.maxDateTime desc limit 1");
		}

		CommonOperInterface coi = new CommonConFactory().createInterface();
		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			status = CommonHelper.getFieldValue(list.get(0));
		}
		return status;
	}

	public String fetchTimeStatusForOffering(EntityType entityType, String userName, String id, String scheduletime)
	{
		String timestatus = "You can schedule";
		StringBuilder query = new StringBuilder();
		if (entityType == EntityType.CLIENT)
		{
			query
					.append("select maxDateTime from client_takeaction where userName = '");
			query.append(userName);
			query.append("' and id = ");
			query.append("(select max(id) from client_takeaction where userName = '");
			query.append(userName);
			query.append("')");
		}
		else if (entityType == EntityType.ASSOCIATE)
		{
				query
				.append("select maxDateTime from associate_takeaction where userName = '");
				query.append(userName);
				query.append("' and id = ");
				query.append("(select max(id) from associate_takeaction where userName = '");
				query.append(userName);
				query.append("')");
		}
	//	System.out.println("query.toString()>>"+query.toString());
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		if (list != null && !list.isEmpty())
		{
			timestatus = CommonHelper.getFieldValue(list.get(0));
			String a[]=null,b[]=null;
			a=timestatus.split(" ");
			b=scheduletime.split(" ");
			if(a[0].equalsIgnoreCase(b[0]))
			{
				a=a[1].split(":");
				b=b[1].split(":");
				if(Integer.parseInt(a[0]) >= Integer.parseInt(b[0]))
				{
					timestatus = "This time is already scheduled.";	
				}
				else
				{
					timestatus = "You can schedule.";
				}
			}
			else
			{
				timestatus = "You can schedule.";
			}
			a=null;
			b=null;
		}
		return timestatus;
	}
	
	public ArrayList<GridDataPropertyView> getConfigDataForContactTemp()
	{
		ArrayList<GridDataPropertyView> configData = (ArrayList<GridDataPropertyView>) Configuration.getConfigurationData("mapped_client_configuration", accountID,
				connectionSpace, "", 0, "table_name", "client_contact_configuration");
		if (configData == null || configData.isEmpty()) return null;
		// add extra id field to list 'configData' to be used for contact
		// edition
		GridDataPropertyView gdp = new GridDataPropertyView();
		gdp.setColomnName("id");
		gdp.setColType("T");
		configData.add(0, gdp);
		String itrTemp = "";

		for (Iterator<GridDataPropertyView> itr = configData.iterator(); itr.hasNext();)
		{
			itrTemp = itr.next().getColomnName();
			if (itrTemp.equalsIgnoreCase("anniversary"))
			{
				itr.next().setAnniversaryId("anniversary" + String.valueOf(idval));

			}
			if (itrTemp.equalsIgnoreCase("birthday"))
			{
				itr.next().setBirthdayId("birthday" + String.valueOf(idval));
			}
			if (itrTemp.equalsIgnoreCase("userName") || itrTemp.equalsIgnoreCase("date") || itrTemp.equalsIgnoreCase("time")
					|| itrTemp.equalsIgnoreCase("clientName"))
			{
				itr.remove();
			}
		}

		return configData;
	}
}
