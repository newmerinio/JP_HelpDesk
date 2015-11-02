package com.Over2Cloud.ctrl.wfpm.crm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonInterface.commanOperation;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;

public class CRMHelper
{
	public List<String> getLeadBasicToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("personName");
		listDataToShow.add("communicationName");
		listDataToShow.add("degreeOfInfluence");
		listDataToShow.add("contactNo");
		listDataToShow.add("emailOfficialContact");
		listDataToShow.add("department");
		listDataToShow.add("designation");
		listDataToShow.add("leadName");
		listDataToShow.add("starRating");
		listDataToShow.add("industry");
		return listDataToShow;
	}

	public HashMap<Integer, HashMap<String, String>> fetchCorrectMailDataList1(EntityType entityType, Map<String, String> correctMailData, String entityContacts, SessionFactory connectionSpace, String cId, Map<String, String> organizationnameMap ,String emailContant)
	{
       //    System.out.println("clllllllllllllllllllllllllllllllllllllll");
   		   HashMap<String, String> diaplayList ;
   		   HashMap<Integer, HashMap<String, String>> diaplayList1 = new HashMap<Integer, HashMap<String, String>>();

           try
		{
			
			StringBuilder query = null;
			query = new StringBuilder();
			CommonOperInterface coi = new CommonConFactory().createInterface();

			if (entityType == EntityType.ALL)
			{
		         //  System.out.println("entityType"+EntityType.ALL);

				// Lead
				List list;
				try
				{
					query = new StringBuilder();
					query
							.append("select lcd.emailOfficialContact, lcd.personName, sind.subIndustry  from lead_contact_data as lcd inner join leadgeneration as lg on lg.id = lcd.leadName left join industrydatalevel1 as ind on ind.id = lg.industry left join department as dept on dept.id = lcd.department left join subindustrydatalevel2 as sind on sind.id = lg.subIndustry where lg.status IN('0','4') and lg.userName IN(");
					query.append(cId);
					query.append(") and lcd.emailOfficialContact is not null and lcd.emailOfficialContact <> 'NA' ");
				//	System.out.println("query"+query);
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						
						Iterator it = list.iterator();
						while (it.hasNext())
						{
							Object[] obdata = (Object[]) it.next();
							if (obdata != null)
							{
								  for (int i = 0; i < obdata.length; i++) 
								{
									
									  
								}    
								}
						
						}
						/*correctMailData.putAll(CommonHelper.convertListToMap(list, false));
						for (Object obj : list)
						{
							Object[] data = (Object[]) obj;
							if(data[0].toString() != null && data[2].toString() != null)
							{
							 organizationnameMap.put(data[0].toString(), data[2].toString()) ;
							}
						}*/
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// Client
				try
				{
					query = new StringBuilder();
					query
							.append("select ccd.emailOfficial, ccd.personName, sind.subIndustry from client_contact_data as ccd inner join client_basic_data as cbd on cbd.id = ccd.clientName left join industrydatalevel1 as ind on ind.id = cbd.industry left join department as dept on dept.id = ccd.department left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry left join client_offering_mapping as com on com.clientName = cbd.id where com.isConverted IN('0','1','2') and cbd.userName IN(");
					query.append(cId);
					query.append(") and ccd.emailOfficial is not null and ccd.emailOfficial <> 'NA'");
					
				//	System.out.println("query>>>>>>>>>>>>>>>>>>>>>>"+query);
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						Iterator it = list.iterator();
						while (it.hasNext())
						{
							Object[] obdata = (Object[]) it.next();
							if (obdata != null)
							{
								for (Object obj : obdata)
								{
																	}	  
								}    
								}
						}
					}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// Associate
				try
				{
					query = new StringBuilder();
					query
							.append("select ccd.emailOfficial, ccd.name, sind.subIndustry from associate_contact_data as ccd inner join associate_basic_data as cbd on cbd.id = ccd.associateName left join industrydatalevel1 as ind on ind.id = cbd.industry left join department as dept on dept.id = ccd.department left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry left join associate_offering_mapping as com on com.associateName = cbd.id where com.isConverted IN('0','1','2') and cbd.userName IN(");
					query.append(cId);
					query.append(") and ccd.emailOfficial is not null and ccd.emailOfficial <> 'NA' ");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						Iterator it = list.iterator();
						while (it.hasNext())
						{
							Object[] obdata = (Object[]) it.next();
							if (obdata != null)
							{
								diaplayList = new HashMap<String, String>();
								
								for (Object obj : obdata)
								{
									diaplayList.put("Person Name", obdata[1].toString());
								    diaplayList.put("subIndustry", obdata[2].toString());
								    diaplayList.put("emailContact", obdata[3].toString());

								}	  
								
							}    
								}
						}
			}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (entityContacts != null && !entityContacts.trim().isEmpty())
			{
				if (entityType == EntityType.LEAD)
				{
					query = new StringBuilder();
					query.append("select lcd.personName ,lcd.emailOfficialContact,  sind.subIndustry from lead_contact_data as lcd left join leadgeneration as lg on lg.id = lcd.leadName left join subindustrydatalevel2 as sind on lg.subIndustry = sind.id where lcd.emailOfficialContact is not null and lcd.emailOfficialContact <> 'NA' and lcd.id in (");
					query.append(entityContacts);
					query.append(") order by personName ");
					
				}
				if (entityType == EntityType.CLIENT)
				{
					query = new StringBuilder();
					query.append("select ccd.personName, ccd.emailOfficial,  sind.subIndustry from client_contact_data as ccd left join client_basic_data as cbd on cbd.id = ccd.clientName left join subindustrydatalevel2 as sind on cbd.subIndustry = sind.id where ccd.emailOfficial is not null and ccd.emailOfficial <> 'NA' and ccd.id in (");
					query.append(entityContacts);
					query.append(") order by personName ");
				}
				if (entityType == EntityType.ASSOCIATE)
				{
					query = new StringBuilder();
					query.append("select  acd.name, acd.emailOfficial, sind.subIndustry from associate_contact_data as acd left join associate_basic_data as abd on abd.id = acd.name left join subindustrydatalevel2 as sind on abd.subIndustry = sind.id where acd.emailOfficial is not null and acd.emailOfficial <> 'NA' and acd.id in (");
					query.append(entityContacts);
					query.append(") order by name ");
				}
				System.out.println(" get org name qry>>"+query.toString());
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				/*correctMailData.putAll(CommonHelper.convertListToMap(list, false));
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					if(data[0] != null && data[2] != null)
					{
						organizationnameMap.put(data[0].toString(), data[2].toString()) ;
					}
					
				}*/
			{
					int count=0;
					Iterator it = list.iterator();
					while (it.hasNext())
					{
						Object[] obdata = (Object[]) it.next();
						if (obdata != null)
						{
							
							diaplayList = new HashMap<String, String>();
							System.out.println(">>>>>>>>>>>"+obdata.length);

							  for (int i = 0; i < obdata.length; i++) 
							{
								if(i==0)
								{
								diaplayList.put("emailContact", obdata[0].toString());
								System.out.println(obdata[0].toString());
								
								}
								
								else if(i==1)
							    {
							
								diaplayList.put("subIndustry", obdata[1].toString());
							    System.out.println(obdata[1].toString());
							    }
								else if(i==2)
								{
											  diaplayList.put("Person_Name", emailContant);
									    System.out.println(obdata[2].toString());

								}
								
							    
							}	  
							count=count+1;
							diaplayList1.put(count, diaplayList);	
							
						}    
							}
					}

			}

			/*String tempMobile = null;
			for (Iterator<Map.Entry<String, String>> entry = correctMailData.entrySet().iterator(); entry.hasNext();)
			{
				tempMobile = CommonHelper.getFieldValue(entry.next().getKey());
				if (!Pattern.matches("[0-9]\\d{9}", tempMobile) || (tempMobile.charAt(0) != '9' && tempMobile.charAt(0) != '8' && tempMobile.charAt(0) != '7'))
				{
					entry.remove();
				}
			}*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
			System.out.println(" diaplayList"+diaplayList1.size());

           return diaplayList1;
	
	}

	
	
	public Map<String, String> getMappedColumnForLead()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("personName", "name");
		map.put("communicationName", "comName");
		map.put("degreeOfInfluence", "influence");
		map.put("contactNo", "mobile");
		map.put("emailOfficialContact", "email");
		map.put("department", "department");
		map.put("designation", "designation");
		map.put("leadName", "organization");
		map.put("starRating", "star");
		map.put("industry", "industry");
		return map;
	}

	public Map<String, String> getMappedColumnForClient()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("personName", "name");
		map.put("communicationName", "comName");
		map.put("degreeOfInfluence", "influence");
		map.put("contactNo", "mobile");
		map.put("emailOfficial", "email");
		map.put("department", "department");
		map.put("designation", "designation");
		map.put("clientName", "organization");
		map.put("starRating", "star");
		map.put("industry", "industry");
		return map;
	}

	public List<String> getClientBasicDataToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("personName");
		listDataToShow.add("communicationName");
		listDataToShow.add("degreeOfInfluence");
		listDataToShow.add("contactNo");
		listDataToShow.add("emailOfficial");
		listDataToShow.add("department");
		listDataToShow.add("designation");
		listDataToShow.add("clientName");
		listDataToShow.add("starRating");
		listDataToShow.add("industry");
		return listDataToShow;
	}

	public List<String> getAssociateBasicDataToShow()
	{
		List<String> listDataToShow = new ArrayList<String>();
		listDataToShow.add("id");
		listDataToShow.add("name");
		listDataToShow.add("communicationName");
		listDataToShow.add("degreeOfInfluence");
		listDataToShow.add("contactNum");
		listDataToShow.add("emailOfficial");
		listDataToShow.add("department");
		listDataToShow.add("designation");
		listDataToShow.add("associateName");
		listDataToShow.add("associateRating");
		listDataToShow.add("industry");
		return listDataToShow;
	}

	public Map<String, String> getMappedColumnForAssociate()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "name");
		map.put("communicationName", "comName");
		map.put("degreeOfInfluence", "influence");
		map.put("contactNum", "mobile");
		map.put("emailOfficial", "email");
		map.put("department", "department");
		map.put("designation", "designation");
		map.put("associateName", "organization");
		map.put("associateRating", "star");
		map.put("industry", "industry");
		return map;
	}

	@SuppressWarnings("unchecked")
	public void fetchCorrectDataList(EntityType entityType, Map<String, String> correctData, String entityContacts, SessionFactory connectionSpace, String cId)
	{
		try
		{
			StringBuilder query = null;
			query = new StringBuilder();
			CommonOperInterface coi = new CommonConFactory().createInterface();

			if (entityType == EntityType.ALL)
			{
				// Lead
				List list;
				try
				{
					query = new StringBuilder();
					query
							.append("select lcd.contactNo, lcd.personName from lead_contact_data as lcd inner join leadgeneration as lg on lg.id = lcd.leadName left join industrydatalevel1 as ind on ind.id = lg.industry left join department as dept on dept.id = lcd.department left join subindustrydatalevel2 as sind on sind.id = lg.subIndustry where lg.status IN('0','4') and lg.userName IN(");
					query.append(cId);
					query.append(") and lcd.contactNo is not null and lcd.contactNo <> 'NA' ");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						correctData.putAll(CommonHelper.convertListToMap(list, false));
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// Client
				try
				{
					query = new StringBuilder();
					query
							.append("select ccd.contactNo, ccd.personName from client_contact_data as ccd inner join client_basic_data as cbd on cbd.id = ccd.clientName left join industrydatalevel1 as ind on ind.id = cbd.industry left join department as dept on dept.id = ccd.department left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry left join client_offering_mapping as com on com.clientName = cbd.id where com.isConverted IN('0','1','2') and cbd.userName IN(");
					query.append(cId);
					query.append(") and ccd.contactNo is not null and ccd.contactNo <> 'NA'");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						correctData.putAll(CommonHelper.convertListToMap(list, false));
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// Associate
				try
				{
					query = new StringBuilder();
					query
							.append("select ccd.contactNum, ccd.name from associate_contact_data as ccd inner join associate_basic_data as cbd on cbd.id = ccd.associateName left join industrydatalevel1 as ind on ind.id = cbd.industry left join department as dept on dept.id = ccd.department left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry left join associate_offering_mapping as com on com.associateName = cbd.id where com.isConverted IN('0','1','2') and cbd.userName IN(");
					query.append(cId);
					query.append(") and ccd.contactNum is not null and ccd.contactNum <> 'NA' ");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						correctData.putAll(CommonHelper.convertListToMap(list, false));
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (entityContacts != null && !entityContacts.trim().isEmpty())
			{
				if (entityType == EntityType.LEAD)
				{
					query = new StringBuilder();
					query.append("select contactNo, personName from lead_contact_data where contactNo is not null and contactNo <> 'NA' and id in (");
					query.append(entityContacts);
					query.append(") order by personName ");
				}
				if (entityType == EntityType.CLIENT)
				{
					query = new StringBuilder();
					query.append("select contactNo, personName from client_contact_data where contactNo is not null and contactNo <> 'NA' and id in (");
					query.append(entityContacts);
					query.append(") order by personName ");
				}
				if (entityType == EntityType.ASSOCIATE)
				{
					query = new StringBuilder();
					query.append("select contactNum, name from associate_contact_data where contactNum is not null and contactNum <> 'NA' and id in (");
					query.append(entityContacts);
					query.append(") order by name ");
				}
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				correctData.putAll(CommonHelper.convertListToMap(list, false));
			}

			String tempMobile = null;
			for (Iterator<Map.Entry<String, String>> entry = correctData.entrySet().iterator(); entry.hasNext();)
			{
				tempMobile = CommonHelper.getFieldValue(entry.next().getKey());
				if (!Pattern.matches("[0-9]\\d{9}", tempMobile) || (tempMobile.charAt(0) != '9' && tempMobile.charAt(0) != '8' && tempMobile.charAt(0) != '7'))
				{
					entry.remove();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// fetch correct mail data list
	public void fetchCorrectMailDataList(EntityType entityType, Map<String, String> correctMailData, String entityContacts, SessionFactory connectionSpace, String cId, Map<String, String> organizationnameMap)
	{

		try
		{
			StringBuilder query = null;
			query = new StringBuilder();
			CommonOperInterface coi = new CommonConFactory().createInterface();

			if (entityType == EntityType.ALL)
			{
				// Lead
				List list;
				try
				{
					query = new StringBuilder();
					query
							.append("select lcd.emailOfficialContact, lcd.personName, sind.subIndustry  from lead_contact_data as lcd inner join leadgeneration as lg on lg.id = lcd.leadName left join industrydatalevel1 as ind on ind.id = lg.industry left join department as dept on dept.id = lcd.department left join subindustrydatalevel2 as sind on sind.id = lg.subIndustry where lg.status IN('0','4') and lg.userName IN(");
					query.append(cId);
					query.append(") and lcd.emailOfficialContact is not null and lcd.emailOfficialContact <> 'NA' ");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						correctMailData.putAll(CommonHelper.convertListToMap(list, false));
						for (Object obj : list)
						{
							Object[] data = (Object[]) obj;
							if(data[0].toString() != null && data[2].toString() != null)
							{
							 organizationnameMap.put(data[0].toString(), data[2].toString()) ;
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// Client
				try
				{
					query = new StringBuilder();
					query
							.append("select ccd.emailOfficial, ccd.personName, sind.subIndustry from client_contact_data as ccd inner join client_basic_data as cbd on cbd.id = ccd.clientName left join industrydatalevel1 as ind on ind.id = cbd.industry left join department as dept on dept.id = ccd.department left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry left join client_offering_mapping as com on com.clientName = cbd.id where com.isConverted IN('0','1','2') and cbd.userName IN(");
					query.append(cId);
					query.append(") and ccd.emailOfficial is not null and ccd.emailOfficial <> 'NA'");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						correctMailData.putAll(CommonHelper.convertListToMap(list, false));
						for (Object obj : list)
						{
							Object[] data = (Object[]) obj;
							if(data[0].toString() != null && data[2].toString() != null)
							{
							 organizationnameMap.put(data[0].toString(), data[2].toString()) ;
							} 
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				// Associate
				try
				{
					query = new StringBuilder();
					query
							.append("select ccd.emailOfficial, ccd.name, sind.subIndustry from associate_contact_data as ccd inner join associate_basic_data as cbd on cbd.id = ccd.associateName left join industrydatalevel1 as ind on ind.id = cbd.industry left join department as dept on dept.id = ccd.department left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry left join associate_offering_mapping as com on com.associateName = cbd.id where com.isConverted IN('0','1','2') and cbd.userName IN(");
					query.append(cId);
					query.append(") and ccd.emailOfficial is not null and ccd.emailOfficial <> 'NA' ");
					list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						correctMailData.putAll(CommonHelper.convertListToMap(list, false));
						for (Object obj : list)
						{
							Object[] data = (Object[]) obj;
							if(data[0].toString() != null && data[2].toString() != null)
							{
							 organizationnameMap.put(data[0].toString(), data[2].toString()) ;
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if (entityContacts != null && !entityContacts.trim().isEmpty())
			{
				if (entityType == EntityType.LEAD)
				{
					query = new StringBuilder();
					query.append("select lcd.emailOfficialContact, lcd.personName, sind.subIndustry from lead_contact_data as lcd left join leadgeneration as lg on lg.id = lcd.leadName left join subindustrydatalevel2 as sind on lg.subIndustry = sind.id where lcd.emailOfficialContact is not null and lcd.emailOfficialContact <> 'NA' and lcd.id in (");
					query.append(entityContacts);
					query.append(") order by personName ");
				}
				if (entityType == EntityType.CLIENT)
				{
					query = new StringBuilder();
					query.append("select ccd.emailOfficial, ccd.personName, sind.subIndustry from client_contact_data as ccd left join client_basic_data as cbd on cbd.id = ccd.clientName left join subindustrydatalevel2 as sind on cbd.subIndustry = sind.id where ccd.emailOfficial is not null and ccd.emailOfficial <> 'NA' and ccd.id in (");
					query.append(entityContacts);
					query.append(") order by personName ");
				}
				if (entityType == EntityType.ASSOCIATE)
				{
					query = new StringBuilder();
					query.append("select acd.emailOfficial, acd.name, sind.subIndustry from associate_contact_data as acd left join associate_basic_data as abd on abd.id = acd.name left join subindustrydatalevel2 as sind on abd.subIndustry = sind.id where acd.emailOfficial is not null and acd.emailOfficial <> 'NA' and acd.id in (");
					query.append(entityContacts);
					query.append(") order by name ");
				}
				System.out.println(" get org name qry>>"+query.toString());
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				correctMailData.putAll(CommonHelper.convertListToMap(list, false));
				for (Object obj : list)
				{
					Object[] data = (Object[]) obj;
					if(data[0] != null && data[2] != null)
					{
						organizationnameMap.put(data[0].toString(), data[2].toString()) ;
					}
					
				}
			}

			/*String tempMobile = null;
			for (Iterator<Map.Entry<String, String>> entry = correctMailData.entrySet().iterator(); entry.hasNext();)
			{
				tempMobile = CommonHelper.getFieldValue(entry.next().getKey());
				if (!Pattern.matches("[0-9]\\d{9}", tempMobile) || (tempMobile.charAt(0) != '9' && tempMobile.charAt(0) != '8' && tempMobile.charAt(0) != '7'))
				{
					entry.remove();
				}
			}*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public void fetchCorrectDataListForEmployee(Map<String, String> correctData, String employee)
	{
		try
		{
			String[] tempEmployee = CommonHelper.getCommaSeparatedToArray(employee);
			for (String num : tempEmployee)
			{
				if (Pattern.matches("[0-9]+", num.split("-")[1].trim()) && num.split("-")[1].trim().length() == 10
						&& (num.split("-")[1].trim().charAt(0) == '9' || num.split("-")[1].trim().charAt(0) == '8' || num.split("-")[1].trim().charAt(0) == '7'))
				{
					correctData.put(num.split("-")[1].trim(), num.split("-")[0]);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void fetchCorrectDataListFormobileNo(Map<String, String> correctData, String mobileNo)
	{
		try
		{
			String[] tempMobileNo = CommonHelper.getCommaSeparatedToArray(mobileNo);
			for (String num : tempMobileNo)
			{
				if (Pattern.matches("[0-9]+", num.trim()) && num.trim().length() == 10
						&& (num.trim().charAt(0) == '9' || num.trim().charAt(0) == '8' || num.trim().charAt(0) == '7'))
				{
					correctData.put(num.trim(), "CSV");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// fetch correct data for email id.
	public void fetchCorrectDataListForEmailId(Map<String, String> correctMailData, String emailid)
	{
		try
		{
			String[] tempEmailId = CommonHelper.getCommaSeparatedToArray(emailid);
			for (String mail : tempEmailId)
			{
				
				correctMailData.put(mail.trim(), "CSV");
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	
	public Map<String, String> getGroupNameList(SessionFactory factory)
	{
		Map<String, String> groupNameList = new LinkedHashMap<String, String>();
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List groupnameData = coi.executeAllSelectQuery("select id, name from group_name where moduleName = 'WFPM' order by name", factory);
		if (groupnameData != null && groupnameData.size() > 0)
		{
			for (Object c : groupnameData)
			{
				Object[] dataC = (Object[]) c;
				groupNameList.put(dataC[0].toString(), dataC[1].toString());
			}
		}
		return groupNameList;
	}
	
	public String getTotalDataInQuery(SessionFactory factory, String query)
	{
		int count = 0;
		CommonOperInterface coi = new CommonConFactory().createInterface();
		List countdata = coi.executeAllSelectQuery(query, factory);
		if (countdata != null && countdata.size() > 0)
		{
			for (Object c : countdata)
			{
				count +=1;
			}
		}
		System.out.println("count>"+count);
		return String.valueOf(count);
	}
}
