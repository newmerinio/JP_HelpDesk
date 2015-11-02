package com.Over2Cloud.ctrl.wfpm.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper1;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper2;

public class ClientHelper3 extends SessionProviderClass
{

	@SuppressWarnings("unchecked")
	public Map<String, String> fetchAllSource()
	{
	Map<String, String> source = new LinkedHashMap<String, String>();
	try
	{
	List sourceMasterData = coi.executeAllSelectQuery("select id, sourceName from sourcemaster order by sourceName", connectionSpace);
	if (sourceMasterData != null && !sourceMasterData.isEmpty())
	{
	for (Object c : sourceMasterData)
	{
	Object[] dataC = (Object[]) c;
	source.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return source;
	}
	public Map<String, String> fetchAllweightageTargetSegment()
	{
	Map<String, String> weightage = new LinkedHashMap<String, String>();
	try
	{
	List weightageMasterData = coi.executeAllSelectQuery("select id, weightageName from weightagemaster order by weightageName", connectionSpace);
	if (weightageMasterData != null && !weightageMasterData.isEmpty())
	{
	for (Object c : weightageMasterData)
	{
	Object[] dataC = (Object[]) c;
	weightage.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return weightage;
	}
	public Map<String, String> fetchForcastcategory()
	{
	Map<String, String> forcast = new LinkedHashMap<String, String>();
	 try
	 {
	List forcastMasterData = coi.executeAllSelectQuery("select id, forcastName from forcastcategarymaster order by forcastName", connectionSpace);
	      if (forcastMasterData != null && !forcastMasterData.isEmpty())
	         {
	       for (Object c : forcastMasterData)
	            {
	         Object[] dataC = (Object[]) c;
	         forcast.put(dataC[0].toString(), dataC[1].toString());
	            }
	         }
	}
	 catch (Exception e)
	      {
	       e.printStackTrace();
	      }
	       return forcast;
	}

	public Map<String, String> fetchsalesStage()
	{    Map<String, String> sales = new LinkedHashMap<String, String>();
	     try
	     {
	    List salesMasterData = coi.executeAllSelectQuery("select id, salesstageName from salesstagemaster order by salesstageName", connectionSpace);
	       if (salesMasterData != null && !salesMasterData.isEmpty())
	          {
	        for (Object c : salesMasterData)
	            {
	         Object[] dataC = (Object[]) c;
	         sales.put(dataC[0].toString(), dataC[1].toString());
	            }
	         }
	    }
	    catch (Exception e)
	    {
	    e.printStackTrace();
	    }
	    return sales;
	
	}

	public Map<String, String> fetchOffring()
	{
	Map<String, String> offering = new LinkedHashMap<String, String>();
	     try
	     {
	    List offeringdata = coi.executeAllSelectQuery("SELECT opd.offeringId, off.subofferingname FROM opportunity_details as opd left join offeringlevel3 as off on off.id =opd.offeringId group by off.subofferingname", connectionSpace);
	       if (offeringdata != null && !offeringdata.isEmpty())
	          {
	        for (Object c : offeringdata)
	            {
	         Object[] dataC = (Object[]) c;
	         offering.put(dataC[0].toString(), dataC[1].toString());
	            }
	         }
	    }
	    catch (Exception e)
	    {
	    e.printStackTrace();
	    }
	    return offering;
	}
	public Map<String, String> fetchOpportunityName()
	{
	Map<String, String> opportunityName = new LinkedHashMap<String, String>();
	     try
	     {
	    List opportunitynamedata = coi.executeAllSelectQuery("SELECT cbd.id, cbd.clientName FROM opportunity_details as opd left join client_basic_data as cbd on cbd.id = opd.clientName group by opd.clientName order by cbd.clientName", connectionSpace);
	       if (opportunitynamedata != null && !opportunitynamedata.isEmpty())
	          {
	        for (Object c : opportunitynamedata)
	            {
	         Object[] dataC = (Object[]) c;
	         if(dataC[0] != null && dataC[1] != null)
	         {
	        	 opportunityName.put(dataC[0].toString(), dataC[1].toString());
	         }
	         
	            }
	         }
	    }
	    catch (Exception e)
	    {
	    e.printStackTrace();
	    }
	    return opportunityName;
	
	}
	public Map<String, String> fetchAllLocation()
	{
	Map<String, String> location = new LinkedHashMap<String, String>();
	try
	{
	List location_master_dataData = coi.executeAllSelectQuery("select id, name from location order by name", connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	location.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return location;
	}
	
	public Map<String, String> fetchAllAllergic(EntityType entity)
	{
	Map<String, String> allergic = new LinkedHashMap<String, String>();
	try
	{
	String query=null;
	if (entity == EntityType.LEAD)
	{
	query="select id, allergic_to from lead_contact_data order by allergic_to";
	}
	else if (entity == EntityType.CLIENT)
	{
	query="select id, allergic_to from client_contact_data order by allergic_to";
	}
	List location_master_dataData = coi.executeAllSelectQuery(query, connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	if(dataC[0]!=null && dataC[1]!=null)
	{
	allergic.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return allergic;
	}
	
	public Map<String, String> fetchAllOfferig()
	{
	Map<String, String> allergic = new LinkedHashMap<String, String>();
	try
	{
	List location_master_dataData = coi.executeAllSelectQuery("select id, allergic_to from client_contact_data order by allergic_to", connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	if(dataC[0]!=null && dataC[1]!=null)
	{
	allergic.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return allergic;
	}
	
	
	public Map<String, String> fetchClientStatus()
	{
	Map<String, String> status = new LinkedHashMap<String, String>();
	try
	{
	List location_master_dataData = coi.executeAllSelectQuery("select id, statusName from client_status where id IN(select distinct statusId from client_takeaction where isFinished ='0')", connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	status.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return status;
	}
	public Map<String, String> fetchStateByLocation()
	{
	Map<String, String> location = new LinkedHashMap<String, String>();
	try
	{
	List location_master_dataData = coi.executeAllSelectQuery("select id, name from location order by name", connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	location.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return location;
	}

	public Map<String, String> fetchAllLocation(String stateId)
	{
	Map<String, String> location = new LinkedHashMap<String, String>();
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select id, name from location where stateName = '");
	query.append(stateId);
	query.append("' order by name ");

	List location_master_dataData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	location.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return location;
	}

	public Map<String, String> fetchAllEmployee()
	{
	Map<String, String> employee = new LinkedHashMap<String, String>();
	try
	{
	List employee_basicData = coi.executeAllSelectQuery("select id, empName from employee_basic order by empName", connectionSpace);
	if (employee_basicData != null)
	{
	for (Object c : employee_basicData)
	{
	Object[] dataC = (Object[]) c;
	employee.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return employee;
	}

	public Map<String, String> fetchAllContactOfClient(String clientId)
	{
	Map<String, String> contactClient = new LinkedHashMap<String, String>();
	try
	{
	List employee_basicData = coi.executeAllSelectQuery("select emailOfficial, concat(personName,'-', emailOfficial) from client_contact_data where clientName= '"+clientId+"' and emailOfficial <> 'NA' and emailOfficial is not null and emailOfficial <> '' order by personName", connectionSpace);
	if (employee_basicData != null)
	{
	for (Object c : employee_basicData)
	{
	Object[] dataC = (Object[]) c;
	contactClient.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return contactClient;
	}
	public Map<Integer, String> fetchCCContactOfClient(String clientId, String emailone)
	{
	Map<Integer, String> contactClient = new LinkedHashMap<Integer, String>();
	try
	{
	List employee_basicData = coi.executeAllSelectQuery("select id, concat(personName,'-', emailOfficial) from client_contact_data where clientName= '"+clientId+"' and emailOfficial <> 'NA' and emailOfficial is not null and emailOfficial NOT IN('"+emailone+"') and emailOfficial <> '' order by personName", connectionSpace);
	if (employee_basicData != null)
	{
	for (Object c : employee_basicData)
	{
	Object[] dataC = (Object[]) c;
	contactClient.put(Integer.parseInt(dataC[0].toString()), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return contactClient;
	}
	public Map<Integer, String> fetchBCCContactOfClient(String clientId, String emailone, String emailonetwo)
	{
	Map<Integer, String> contactClient = new LinkedHashMap<Integer, String>();
	try
	{
	List employee_basicData = coi.executeAllSelectQuery("select id, concat(personName,'-', emailOfficial) from client_contact_data where clientName= '"+clientId+"' and emailOfficial <> 'NA' and emailOfficial is not null and emailOfficial NOT IN('"+emailone+"', '"+emailonetwo+"') and emailOfficial <> '' order by personName", connectionSpace);
	if (employee_basicData != null)
	{
	for (Object c : employee_basicData)
	{
	Object[] dataC = (Object[]) c;
	contactClient.put(Integer.parseInt(dataC[0].toString()), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return contactClient;
	}
	// to get mail template name.
	public Map<String, String> fetchMailTemplateName()
	{
	Map<String, String> mailteplateName = new LinkedHashMap<String, String>();
	try
	{
	List employee_basicData = coi.executeAllSelectQuery("select id, template_name from mail_template", connectionSpace);
	if (employee_basicData != null)
	{
	for (Object temp : employee_basicData)
	{
	Object[] dataC = (Object[]) temp;
	mailteplateName.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return mailteplateName;
	}
	
	
	public Map<String, String> fetchAllIndustry()
	{
	Map<String, String> industry = new LinkedHashMap<String, String>();
	try
	{
	List industryData = coi.executeAllSelectQuery("select id,industry from industrydatalevel1 order by industry", connectionSpace);
	if (industryData != null && industryData.size() > 0)
	{
	for (Object i : industryData)
	{
	Object[] dataI = (Object[]) i;
	industry.put(dataI[0].toString(), dataI[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return industry;
	}

	public Map<String, String> fetchAllState()
	{
	Map<String, String> state = new LinkedHashMap<String, String>();
	try
	{
	List industryData = coi.executeAllSelectQuery("select id, stateName from state_data order by stateName", connectionSpace);
	if (industryData != null && industryData.size() > 0)
	{
	for (Object i : industryData)
	{
	Object[] dataI = (Object[]) i;
	state.put(dataI[0].toString(), dataI[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return state;
	}

	public Map<String, String> fetchSubIndustryByIndustryId(String industryId)
	{
	Map<String, String> subIndustryMap = new LinkedHashMap<String, String>();
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select id,subIndustry from subindustrydatalevel2 where industry = '");
	query.append(industryId);
	query.append("' order by subIndustry");

	List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (subIndustryData != null && subIndustryData.size() > 0)
	{
	for (Object s : subIndustryData)
	{
	Object[] dataS = (Object[]) s;
	subIndustryMap.put(dataS[0].toString(), dataS[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return subIndustryMap;
	}

	public int fetchStateIdByLocationId(String locationId)
	{
	int stateId = 0;
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select stateName from location where id = '");
	query.append(locationId);
	query.append("'");

	List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (subIndustryData != null && !subIndustryData.isEmpty())
	{
	stateId = Integer.parseInt(subIndustryData.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return stateId;
	}
	
	public String fetchEmailDataForTemplate(String templateid)
	{
	String emailfortemplate = null;
	try {

	StringBuilder query = new StringBuilder();
	query.append("select template_body from mail_template where id='");
	query.append(templateid);
	query.append("'");

	List emailData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (emailData != null && !emailData.isEmpty())
	{
	emailfortemplate = emailData.get(0).toString();
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return emailfortemplate;
	}
	public Map<String, String> fetchAssociateType()
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select id,associateType from associatetype order by associateType");

	List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (subIndustryData != null && subIndustryData.size() > 0)
	{
	for (Object s : subIndustryData)
	{
	Object[] dataS = (Object[]) s;
	map.put(dataS[0].toString(), dataS[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return map;
	}

	public Map<String, String> fetchAssociateSubTypeByTypeId(String associateTypeId)
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select id, associateSubType from associatesubtype where associateType = '");
	query.append(associateTypeId);
	query.append("'");

	List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (subIndustryData != null && subIndustryData.size() > 0)
	{
	for (Object s : subIndustryData)
	{
	Object[] dataS = (Object[]) s;
	map.put(dataS[0].toString(), dataS[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return map;
	}

	public Map<String, String> fetchAssociateCategory()
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select id, associate_category from associatecategory order by associate_category ");

	List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (subIndustryData != null && subIndustryData.size() > 0)
	{
	for (Object s : subIndustryData)
	{
	Object[] dataS = (Object[]) s;
	map.put(dataS[0].toString(), dataS[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return map;
	}

	public Map<String, String> fetchAllDepartment()
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	List tempList = coi.executeAllSelectQuery("select id,deptName from department order by deptName", connectionSpace);
	if (tempList != null && tempList.size() > 0)
	{
	for (Object dept : tempList)
	{
	Object[] dataI = (Object[]) dept;
	map.put(dataI[0].toString(), dataI[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return map;
	}

	public Map<String, String> fetchallInactiveContact()
	{  Map<String, String> map = new LinkedHashMap<String, String>();
	   try{
	  List listContact=coi.executeAllSelectQuery("select id, personName from client_contact_data  where currentStatus=0 order by personName", connectionSpace);
	  if(listContact!= null && listContact.size() > 0 )
	  {
	   for(Object person: listContact)
	      {
	   Object[] object= (Object[]) person;
	   map.put(object[0].toString(), object[1].toString());
	      }
	  }
	  
	      }
	      catch (Exception e)
	      {
	    	  e.printStackTrace();
	      }
	
	return map;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, String> fetchAllDesignationOfClientContact()
	{
	Map<String, String> map = null;
	try
	{
	List tempList = coi.executeAllSelectQuery("select id, designation from client_contact_data group by designation order by designation", connectionSpace);
	if (tempList != null && tempList.size() > 0)
	{
	map = CommonHelper.convertListToMap(tempList, false);
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return map;
	}
	public int fetchClientDept()
	{
	int clientDeptId = 0;
	try
	{
	StringBuilder query = new StringBuilder();
	query.append("select id from department where deptName = '");
	query.append("Prospective Client");
	query.append("'");

	List subIndustryData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (subIndustryData != null && !subIndustryData.isEmpty())
	{
	clientDeptId = Integer.parseInt(subIndustryData.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return clientDeptId;
	}
	public String fetchOpportunityDetailsToShow(String clientName, String userName)
	{
	String opportunitydataList = null;
	try {
	String query = "SELECT opportunity_name, opportunity_value, closure_date  FROM opportunity_details where clientName='"+clientName+"' and userName = '"+userName+"'";
	List oppdataList = coi.executeAllSelectQuery(query, connectionSpace);
	System.out.println("query>"+query);
	//System.out.println("oppdataList>"+oppdataList.size());
	if(oppdataList!=null && oppdataList.size()>0)
	{
	for(Iterator it=oppdataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 opportunitydataList = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
	}
	}
	
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return opportunitydataList;
	}
	
	public String fetchOpportunityValue(String clientName, String offeringId)
	{
	String opportunitydataList = null;
	try {
	String query = "select opd.opportunity_name, opd.opportunity_value, opd.closure_date, ssm.salesstageName 'sales_stages', fcm.forcastName 'forecast_category'  FROM opportunity_details as opd left join salesstagemaster as ssm on ssm.id = opd.sales_stages left join forcastcategarymaster fcm on fcm.id = opd.forecast_category where opd.clientName='"+clientName+"' and opd.offeringId = '"+offeringId+"'";
	List oppdataList = coi.executeAllSelectQuery(query, connectionSpace);
	System.out.println("query>"+query);
	//System.out.println("oppdataList>"+oppdataList.size());
	if(oppdataList!=null && oppdataList.size()>0)
	{
	for(Iterator it=oppdataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[3] == null){obdata[3] = "NA";}
	 if(obdata[4] == null){obdata[4] = "NA";}
	 opportunitydataList = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString();
	}
	}
	
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return opportunitydataList;
	}
	
	public String fetchSalesStageCompReason(String offeringId, String clientName)
	{
	String clientActiondataList = null;
	try {
	StringBuilder query = new StringBuilder("");
	query.append(" select");
	query.append(" sales_stages, compelling_reason FROM client_takeaction");
	query.append(" where offeringId = (");
	query.append("select  id from offeringlevel3 where subofferingname='");
	query.append(offeringId);
	query.append("')");
	query.append(" and id = (");
	query.append(" select max(cta.id) from client_takeaction as cta");
	query.append(" inner join client_contact_data ccd on ccd.id = cta.contacId ");
	query.append(" where");
	query.append(" cta.offeringId=(");
	query.append("select  id from offeringlevel3 where subofferingname='");
	query.append(offeringId);
	query.append("')");
	query.append(" and cta.contacId IN(");
	query.append(" select id from client_contact_data");
	query.append(" where clientName=(");
	query.append("select  id from client_basic_data where clientName='");
	query.append(clientName);
	query.append("')))");
	
	List actiondataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	//System.out.println("query sales stages>"+query);
	
	if(actiondataList!=null && actiondataList.size()>0)
	{
	for(Iterator it=actiondataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null || obdata[0].toString().equalsIgnoreCase("")){obdata[0] = "NA";}
	 if(obdata[1] == null || obdata[1].toString().equalsIgnoreCase("")){obdata[1] = "NA";}
	 clientActiondataList = obdata[0].toString()+"#"+obdata[1].toString();
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return clientActiondataList;
	}
	
	public String historyCount(String offeringId, String clientName)
	{
	String countAction = "0";
	try {
	StringBuilder query = new StringBuilder("");
	query.append(" select");
	query.append(" id FROM client_takeaction");
	query.append(" where offeringId IN(");
	query.append("select  id from offeringlevel3 where subofferingname='");
	query.append(offeringId);
	query.append("')");
	query.append(" and id IN(");
	query.append(" select cta.id from client_takeaction as cta");
	query.append(" inner join client_contact_data ccd on ccd.id = cta.contacId ");
	query.append(" where");
	query.append(" cta.offeringId=(");
	query.append("select  id from offeringlevel3 where subofferingname='");
	query.append(offeringId);
	query.append("')");
	query.append(" and cta.contacId IN(");
	query.append(" select id from client_contact_data");
	query.append(" where clientName=(");
	query.append("select  id from client_basic_data where clientName='");
	query.append(clientName);
	query.append("')))");
	
	
	List actiondataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(actiondataList!=null && actiondataList.size()>0)
	{
	countAction = String.valueOf(actiondataList.size());
	//System.out.println("client   "+clientName+"   offering  "+offeringId);
	}
	
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	return countAction;
	}
	
	public String fetchClientAndOffId(String rowId)
	{
	String nameAndIdVal = null;
	try
	{
	String query = "select opd.clientName, opd.offeringId, opd.offeringLevelId, fcm.forcastName 'forecast_category', ssm.salesstageName  'sales_stages' from opportunity_details as opd left join forcastcategarymaster fcm on fcm.id = opd.forecast_category left join salesstagemaster ssm on ssm.id = opd.sales_stages where opd.id ='"+rowId+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[3] == null){obdata[3] = "NA";}
	 if(obdata[4] == null){obdata[4] = "NA";}
	 nameAndIdVal = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return nameAndIdVal;
	}
	
	public String fetchClientAndOffIdSupport(String rowId)
	{
	String nameAndIdVal = null;
	try
	{
	String query = "select opd.clientName, opd.offeringId, opd.offeringLevelId, fcm.forcastName 'forecast_category', ssm.salesstageName  'sales_stages', csd.close_flag from client_support_details as csd left join opportunity_details as opd on opd.id = csd.opportunity_detail_id left join forcastcategarymaster fcm on fcm.id = opd.forecast_category left join salesstagemaster ssm on ssm.id = opd.sales_stages where csd.id ='"+rowId+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[3] == null){obdata[3] = "NA";}
	 if(obdata[4] == null){obdata[4] = "NA";}
	 if(obdata[5] == null){obdata[5] = "NA";}
	 nameAndIdVal = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString()+"#"+obdata[5].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return nameAndIdVal;
	}
	public String fetchExtendedSupportData(String rowId)
	{
	String extendsupport = "NA";
	try
	{
	//String query = "select opd.clientName, opd.offeringId, opd.offeringLevelId, fcm.forcastName 'forecast_category', ssm.salesstageName  'sales_stages' from client_support_details as csd left join opportunity_details as opd on opd.id = csd.opportunity_detail_id left join forcastcategarymaster fcm on fcm.id = opd.forecast_category left join salesstagemaster ssm on ssm.id = opd.sales_stages where csd.id ='"+rowId+"'";
	StringBuilder query = new StringBuilder("");
	query.append("select ctd.support_type, ces.comments, ces.support_from, ces.support_till,ces.po_attach,ces.agreement_attach ,ces.amount from client_extended_support as ces");
	query.append(" left join client_support_details as csd on ces.client_support_id = csd.id");
	query.append(" left join clientsupport_type_data as ctd on ctd.id = ces.support_type");
	query.append(" where ces.client_support_id = '");
	query.append(rowId);
	query.append("' order by ces.id desc limit 1");
	File file=null;
	boolean sta=false;
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[3] == null){obdata[3] = "NA";}
	 if(obdata[4] == null){obdata[4] = "NA";}
	 if(obdata[5] == null){obdata[5] = "NA";}
	 if(obdata[6] == null){obdata[6] = "NA";}
	 if(obdata[4].toString()!=null)
	 {
	 file=new File(obdata[4].toString());
	 sta=file.exists();
	 if(sta==false)
	 {
	 obdata[4] = "NA";
	 }
	 file=null;
	 sta=false;
	 }
	 if(obdata[5].toString()!=null  )
	 {
	 file=new File(obdata[5].toString());
	 sta=file.exists();
	 if(sta==false)
	 {
	 obdata[5] = "NA";
	 }
	 file=null;
	 }
	 extendsupport = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString()+"#"+obdata[5].toString()+"#"+obdata[6].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return extendsupport;
	}
	
	public String fetchSupportStatus(String rowId)
	{
	String nameAndIdVal = null;
	try
	{
	String query = "select cst.support_type,csd.support_from,csd.support_till,csd.po_attach,csd.agreement_attach,csd.comments from client_support_details as csd left join clientsupport_type_data as cst on cst.id = csd.support_type where csd.id ='"+rowId+"'";
	System.out.println("support status query>>"+query);
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	boolean sta=false;
	File file=null;
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[3] == null){obdata[3] = "NA";}
	 if(obdata[4] == null){obdata[4] = "NA";}
	 if(obdata[5] == null){obdata[5] = "NA";}
	 if(obdata[3].toString()!=null  )
	 {
	 file=new File(obdata[3].toString());
	 sta=file.exists();
	 if(sta==false)
	 {
	 obdata[3] = "NA";
	 }
	 file=null;
	 }
	 if(obdata[4].toString()!=null  )
	 {
	 file=new File(obdata[4].toString());
	 sta=file.exists();
	 if(sta==false)
	 {
	 obdata[4] = "NA";
	 }
	 file=null;
	 }
	 nameAndIdVal = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString()+"#"+obdata[3].toString()+"#"+obdata[4].toString()+"#"+obdata[5].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return nameAndIdVal;
	}

	public String fetchContactName(String clientName)
	{
	String name = null;
	try
	{
	String query = "select personName from client_contact_data where clientName = '"+clientName+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	 name = dataList.get(0).toString();
	/*for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 name = obdata[0].toString();
	}*/
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return name;
	}
	public String fetchClientName(String clientName)
	{
	String name = null;
	try
	{
	String query = "select clientName from client_basic_data where id = '"+clientName+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	
	 
	 name = dataList.get(0).toString();
	
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return name;
	
	}
	public String fetchLastStatus(String clientName, String offeringId)
	{
	String status = null;
	try
	{
	String query = "select statusName from client_status where id=(select max(statusId) from client_takeaction where offeringId='"+offeringId+"' and contacId IN(select id from client_contact_data where clientName = '"+clientName+"'))";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	status = dataList.get(0).toString();
	/*for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 status = obdata[0].toString();
	}*/
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return status;
	
	}
	public String fetchOfferingName(String offeringId)
	{
	String offname = null;
	try
	{
	String query = "select subofferingname from offeringlevel3 where id = '"+offeringId+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	
	 
	offname = dataList.get(0).toString();
	
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return offname;
	
	}
	
	public String fetchOfferingTree(String offeringId)
	{
	String offtreedata = null;
	String query = "select offlone.verticalname, offltwo.offeringname, offthree.subofferingname from offeringlevel3 as offthree inner join offeringlevel2 as offltwo on offthree.offeringname = offltwo.id inner join offeringlevel1 as offlone on offltwo.verticalname = offlone.id where offthree.id='"+offeringId+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
	}
	}
	return offtreedata;
	}
	public List fetchClientDetails(String clientName)
	{
	StringBuilder query = new StringBuilder("");
	query.append("select cbd.clientName,cbd.starRating,cbd.address,lcn.name 'location',emb.empName 'acManager',indone.industry,sbinOne.subIndustry,");
	query.append(" fcm.forcastName, ssm.salesstageName, cct.personName ");
	query.append(", cct.contactNo,cct.birthday,cct.anniversary,cct.degreeOfInfluence,cct.designation ");
	query.append(", cct.emailOfficial, cbd.webAddress, wtm.weightageName, cbd.companyEmail, cbd.mobileNo ");
	query.append(" from client_basic_data as cbd left join location as lcn on cbd.location = lcn.id");
	query.append(" left join employee_basic as emb on cbd.acManager = emb.id");
	query.append(" left join industrydatalevel1 as indone on cbd.industry = indone.id");
	query.append(" left join subindustrydatalevel2 sbinOne on cbd.subindustry = sbinOne.id");
	query.append(" left join forcastcategarymaster fcm on cbd.forecast_category = fcm.id");
	query.append(" left join salesstagemaster ssm on cbd.sales_stages = ssm.id");
	query.append(" left join client_contact_data as cct on cct.clientName = cbd.id");
	query.append(" left join weightagemaster wtm on wtm.id = cbd.weightage_targetsegment");
	query.append(" where cbd.id = '");
	query.append(clientName);
	query.append("'");
	System.out.println("client data>>"+query.toString());
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	return dataList;
	}
	public List fetchAcManagerDetails(String clientName,String offeringId)
	{
	StringBuilder query = new StringBuilder("");
	query.append(" select grp.groupName 'groupId',dept.deptName 'deptname',emp.empName,emp.mobOne,emp.emailIdOne,emp.address,emp.dob,emp.doa from employee_basic as emp");
	query.append(" left join department as dept on emp.deptname = dept.id");
	query.append(" left join groupinfo as grp on emp.groupId = grp.id");
	query.append(" where emp.id = (");
	query.append(" select distinct userName from opportunity_details where clientName ='");
	query.append(clientName);
	query.append("' and offeringId = '");
	query.append(offeringId);
	query.append("')");
	//System.out.println("query.toString() "+query.toString());
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	return dataList;
	}
	
	public String fetchExpectedValue(String clientId, String offeringId)
	{
	String expectedval=null;
	String query = "select opportunity_value, date from client_offering_mapping where clientName = '"+clientId+"' and offeringId = '"+offeringId+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 expectedval = obdata[0].toString()+"#"+obdata[1].toString();
	}
	}
	return expectedval;
	}
	 
	public String fetchOppDetailsId(String clientId, String offeringId)
	{
	String id=null;
	String query = "select id from opportunity_details where clientName = '"+clientId+"' and offeringId ='"+offeringId+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{ 
	id = String.valueOf(dataList.get(0).toString());
	
	}
	return id;
	}
	public Map<String, String> fetchAllSalesStagesOnId(String clientId, String offeringId)
	{
	Map<String, String> salesstages = new LinkedHashMap<String, String>();
	try
	{
	List location_master_dataData = coi.executeAllSelectQuery("select id, sales_stages from client_takeaction where contacId IN(select id from client_contact_data where clientName='"+clientId+"')  and offeringId= '"+offeringId+"' order by id ", connectionSpace);
	if (location_master_dataData != null)
	{
	for (Object c : location_master_dataData)
	{
	Object[] dataC = (Object[]) c;
	 if(dataC[0] == null){dataC[0] = "NA";}
	 if(dataC[1] == null){dataC[1] = "NA";}
	salesstages.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return salesstages;
	}
	
	public List fetchActionHistoryData(String clientId, String offeringId)
	{
	List historyData = null;
	try
	{
	StringBuilder query = new StringBuilder("");
	query.append(" select cta.id, cst.statusName,cta.maxDateTime,ccd.personName,emp.empName,cta.comment from client_takeaction as cta");
	query.append(" left join client_status as cst on cst.id = cta.statusId");
	query.append(" left join client_contact_data as ccd on ccd.id = cta.contacId");
	query.append(" left join compliance_contacts as cmc on cmc.id = cta.userName");
	query.append(" left join employee_basic as emp on emp.id = cmc.emp_id");
	query.append(" where offeringId ='");
	query.append(offeringId);
	query.append("' and");
	query.append(" contacId IN (select id from client_contact_data where clientName='");
	query.append(clientId);
	query.append("')");
	//System.out.println("history "+query.toString());
	historyData = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return historyData;
	}
	
	public String fetchOffIdFromOfferingMap(String rowId)
	{
	String nameAndIdVal = null;
	try
	{
	String query = "select offeringId, offeringLevelId from client_offering_mapping where clientName = '"+rowId+"' and opportunity_value is null";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 nameAndIdVal = obdata[0].toString()+"#"+obdata[1].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return nameAndIdVal;
	}
	public String fetchTakeActionId(String clientName, String offeringId)
	{
	String idAndNameVal = null;
	try
	{
	String query = "select max(id) from client_takeaction where offeringId = '"+offeringId+"' and contacId IN( select id from client_contact_data where clientName = '"+clientName+" ')";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	idAndNameVal = (dataList.get(0) == null || dataList.get(0).toString().equals("")) ? "NA" :String.valueOf(dataList.get(0).toString());
	// idAndNameVal = String.valueOf(dataList.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return idAndNameVal;
	}
	
	public String fetchContactId(String clientName, String offeringId)
	{

	String contactIdVal = null;
	try
	{
	String query = "select contacId from client_takeaction where offeringId = '"+offeringId+"' and contacId IN( select id from client_contact_data where clientName = '"+clientName+" ')";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	contactIdVal = (dataList.get(0) == null || dataList.get(0).toString().equals("")) ? "NA" :String.valueOf(dataList.get(0).toString());
	// idAndNameVal = String.valueOf(dataList.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return contactIdVal;
	
	}
	public String fetchOfferingIdForName(String offeringName)
	{
	String offeringIdVal = null;
	try
	{
	String query = "select id from offeringlevel3 where subofferingname = '"+offeringName+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	offeringIdVal = (dataList.get(0) == null || dataList.get(0).toString().equals("")) ? "NA" :String.valueOf(dataList.get(0).toString());
	// idAndNameVal = String.valueOf(dataList.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return offeringIdVal;
	}
	public String fetchClientIdForName(String clientnameval)
	{
	String clientIdVal = null;
	try
	{
	String query = "select id from client_basic_data where clientName = '"+clientnameval+"'";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	clientIdVal = (dataList.get(0) == null || dataList.get(0).toString().equals("")) ? "NA" :String.valueOf(dataList.get(0).toString());
	// idAndNameVal = String.valueOf(dataList.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return clientIdVal;
	}
	public Map<String, String> fetchSupportType(SessionFactory factory)
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	CommonOperInterface coi = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	query.append("select id, support_type from clientsupport_type_data");
	query.append(" order by support_type");
	
	List list = coi.executeAllSelectQuery(query.toString(), factory);
	if (list != null && list.size() > 0)
	{
	for (Object obj : list)
	{
	Object[] data = (Object[]) obj;
	if (data[1] != null) map.put(data[0].toString(), data[1].toString());
	}
	// System.out.println("map.size():" + map.size());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	e.printStackTrace();
	}
	return map;
	}
	
	public Map<String, String> supportClientNameMAP(SessionFactory factory)
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	CommonOperInterface coi = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	query.append("select cbd.id, cbd.clientName from client_support_details as csd ");
	query.append("left join opportunity_details as opd on opd.id = csd.opportunity_detail_id");
	query.append(" left join client_basic_data as cbd on cbd.id = opd.clientName");
	query.append(" order by cbd.clientName");
	List list = coi.executeAllSelectQuery(query.toString(), factory);
	if (list != null && list.size() > 0)
	{
	for (Object obj : list)
	{
	Object[] data = (Object[]) obj;
	if (data[1] != null) map.put(data[0].toString(), data[1].toString());
	}
	// System.out.println("map.size():" + map.size());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	e.printStackTrace();
	}
	return map;
	}
	
	public Map<String, String> supportOfferingNameMAP(SessionFactory factory)
	{
	Map<String, String> map = new LinkedHashMap<String, String>();
	try
	{
	CommonOperInterface coi = new CommonConFactory().createInterface();
	StringBuilder query = new StringBuilder();
	query.append("select ofl.id, ofl.subofferingname from client_support_details as csd ");
	query.append(" left join opportunity_details as opd on opd.id = csd.opportunity_detail_id");
	query.append(" left join offeringlevel3 as ofl on ofl.id = opd.offeringId");
	query.append(" order by ofl.subofferingname");
	List list = coi.executeAllSelectQuery(query.toString(), factory);
	if (list != null && list.size() > 0)
	{
	for (Object obj : list)
	{
	Object[] data = (Object[]) obj;
	if (data[1] != null) map.put(data[0].toString(), data[1].toString());
	}
	// System.out.println("map.size():" + map.size());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	e.printStackTrace();
	}
	return map;
	}
	
	public static Map<Integer,String> alleEmployeeList(SessionFactory connectionSpace, String DeptID)
	{
	Map<Integer,String>emplist=new HashMap<Integer, String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder("");
	query.append("select id, empName from employee_basic where flag = '0' and deptname = '"+DeptID+"'");
	List  list=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(list!=null && list.size()>0)
	{
	for (Object obj : list)
	{
	Object[] data = (Object[]) obj;
	if (data[1] != null) emplist.put((Integer)data[0], data[1].toString());
	}
	// System.out.println("map.size():" + map.size());
	}
	return emplist;
	}
	
	public String fetchOpportunityDetailId(String clientName,String offeringId)
	{
	String opdDetailIdVal = null;
	try
	{

	StringBuilder query = new StringBuilder("");
	query.append("select csd.opportunity_detail_id from client_support_details as csd");
	query.append(" left join opportunity_details as opd on opd.id = csd.opportunity_detail_id");
	query.append(" where opd.clientName = '");
	query.append(clientName);
	query.append("' and opd.offeringId = '");
	query.append(offeringId);
	query.append("'");
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	opdDetailIdVal = (dataList.get(0) == null || dataList.get(0).toString().equals("")) ? "NA" :dataList.get(0).toString();
	// idAndNameVal = String.valueOf(dataList.get(0).toString());
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return opdDetailIdVal;
	}
	
	public String fetchsupportDetailsId(String clientName,String offeringId)
	{
	String opdDetailIdVal = null;
	try
	{
	StringBuilder query = new StringBuilder("");
	query.append("select csd.id, csd.opportunity_detail_id from client_support_details as csd");
	query.append(" left join opportunity_details as opd on opd.id = csd.opportunity_detail_id");
	query.append(" where opd.clientName = '");
	query.append(clientName);
	query.append("' and opd.offeringId = '");
	query.append(offeringId);
	query.append("'");
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 opdDetailIdVal = obdata[0].toString()+"#"+obdata[1].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return opdDetailIdVal;
	}
	public int fetchStarOfClient(String cNameOffId)
	{
	int star = 0;
	try {
	StringBuilder query = new StringBuilder("");
	query.append("select starRating from client_basic_data where id='");
	query.append(cNameOffId);
	query.append("'");
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	String starRating = dataList.get(0).toString();
	star = Integer.parseInt(starRating);
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	return star;
	}
	public int fetchDegreeOfInfluence(String contactName)
	{
	int degree = 0;
	try {
	StringBuilder query = new StringBuilder("");
	query.append("select degreeOfInfluence from client_contact_data where personName='");
	query.append(contactName);
	query.append("'");
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	String starRating = dataList.get(0).toString();
	degree = Integer.parseInt(starRating);
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	return degree;
	}
	public String fetchClMapData(String clientName, String offeringId)
	{
	String clientMapData = null;
	try {
	StringBuilder query = new StringBuilder("");
	query.append("select convertDate, po_attachment, comment from  client_offering_mapping ");
	query.append("where clientName = '");
	query.append(clientName);
	query.append("' and offeringId = '");
	query.append(offeringId);
	query.append("'");
	boolean sta=false;
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[1].toString()!=null)
	 {
	 File file=new File(obdata[1].toString());
	 sta=file.exists();
	 if(sta==false)
	 {
	 obdata[1] = "NA";
	 }
	 file=null;
	 }
	 clientMapData = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return clientMapData;
	}
	
	public String fetchContactCommName(String clientname, String contact_person)
	{
	String commName = null;
	try {
	StringBuilder query = new StringBuilder("");
	query.append("select communicationName from client_contact_data where clientName = ("); 
	query.append("select id from client_basic_data where clientName = '"); 
	query.append(clientname); 
	query.append("')"); 
	query.append(" and personName = '"); 
	query.append(contact_person); 
	query.append("'"); 
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	commName = dataList.get(0).toString();
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return commName;
	}
	public List<String> fetchallInactiveContactList()
    {  List<String> map = new ArrayList<String>();
       try{
          List listContact=coi.executeAllSelectQuery("select personName from client_contact_data  where currentStatus=0 order by personName", connectionSpace);
          if(listContact!= null && listContact.size() > 0 )
          {
               for(Object person: listContact)
                  {
                   Object object=  (String)person;
                   map.add(object.toString());
                  }
          }
          
          }
          catch (Exception e)
          {
              e.printStackTrace();
          }
        
        return map;
    }
	public List<ClientSupportBean> fetckReferanceDetails(String clientId)
	{ 
	
	ClientSupportBean clsupportBean = null;
	clsupportBean = new ClientSupportBean();
	List<ClientSupportBean> refdatalist =  new LinkedList<ClientSupportBean>();
    try{
    	StringBuilder query = new StringBuilder("");
    	query.append(" select sm.sourceName,cbd.referedBy,cbd.refName,cbd.clientName from client_basic_data as cbd");
    	query.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster");
    	query.append(" where cbd.id ='");
    	query.append(clientId);
    	query.append("'");
      // System.out.println(">>>>>>>>>>>>>>>query"+query);
    	List listContact=coi.executeAllSelectQuery(query.toString(), connectionSpace);
    	if(listContact!= null && listContact.size() > 0 )
        {
	for(Iterator it=listContact.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";}
	 if(obdata[2] == null){obdata[2] = "NA";}
	 if(obdata[3] == null){obdata[3] = "NA";}
	 clsupportBean.setSourceMaster(obdata[0].toString());
	 clsupportBean.setReferedBy(obdata[1].toString());
	 clsupportBean.setRefName(obdata[2].toString());
	 clsupportBean.setClientname(obdata[3].toString());
	 if(obdata[1].toString().equals("Existing Client") ||obdata[1].toString().equals("Client") )
	 {
	    StringBuilder query1 = new StringBuilder("");
	    	query1.append(" select cbd.mobileNo,cbd.clientName,cbd.companyEmail from client_basic_data as cbd");
	    	query1.append(" where cbd.id ='");
	    	query1.append(obdata[2].toString());
	    	query1.append("'");
	   //System.out.println("QUERY1"+query1);
	   List listContact1=coi.executeAllSelectQuery(query1.toString(), connectionSpace);
	        if(listContact1!= null && listContact1.size() > 0 )
	        {
	for(Iterator it1=listContact1.iterator(); it1.hasNext();)
	{
	 Object[] obdata1=(Object[])it1.next();
	 if(obdata1[0] == null){obdata1[0] = "NA";}
	 else{ clsupportBean.setMobileNo(obdata1[0].toString());}
	 if(obdata1[1] == null){obdata1[1] = "NA";}
	 else{ clsupportBean.setMapName(obdata1[1].toString());}
	 if(obdata1[2] == null){obdata1[2] = "NA";}
	 else{ clsupportBean.setEmailId(obdata1[2].toString());}
	 clsupportBean.setFullDetailShow("yes"); 
	 	}	
	}    
	 }else if(obdata[1].toString().equals("Associate") || obdata[1].toString().equals("Existing Associate") )
	 {
	    StringBuilder query1 = new StringBuilder("");
                        query1.append(" select abd.contactNo,abd.associateName,abd.associateEmail from associate_basic_data as abd");
	    	query1.append(" where abd.id='");
	    	query1.append(obdata[2].toString());
	    	query1.append("'");
	    	System.out.println("QUERY1"+query1);
	    	 List listContact1=coi.executeAllSelectQuery(query1.toString(), connectionSpace);
	        if(listContact1!= null && listContact1.size() > 0 )
	        {
	for(Iterator it1=listContact1.iterator(); it1.hasNext();)
	{
	 Object[] obdata1=(Object[])it1.next();
	 if(obdata1[0] == null){obdata1[0] = "NA";}
	 else{ clsupportBean.setMobileNo(obdata1[0].toString());}
	 if(obdata1[1] == null){obdata1[1] = "NA";}
	 else{ clsupportBean.setMapName(obdata1[1].toString());}
	 if(obdata1[2] == null){obdata1[2] = "NA";}
	 else{ clsupportBean.setEmailId(obdata1[2].toString());}
	 clsupportBean.setFullDetailShow("yes");; 
	
	}	
	}   
	   
	 }
	 refdatalist.add(clsupportBean);
	}
	}
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      
      return refdatalist;
  }
	public Map<String, String> fetchExistingRecord(String existingfreeId)
	{   Map<String, String> map = new LinkedHashMap<String, String>();
	    String query = "select ccd.personName,ccd.designation,ccd.department,ccd.communicationName,ccd.degreeOfInfluence ,ccd.contactNo,ccd.emailOfficial,ccd.birthday,ccd.anniversary, ccd.alternateMob,ccd.alternateEmail from client_contact_data as ccd where id='"+existingfreeId+"'";
	     List listdata = coi.executeAllSelectQuery(query, connectionSpace);
	     System.out.println("free query>>>>>>>>>>>"+query);
	 if(listdata.size()>0 && listdata != null ) 
	 {
	 for(Object ob:listdata)
	 {
	 
	 Object[] data = (Object[]) ob;
	 
	 if(data[0] != null)
	 {
	 map.put("personName", data[0].toString());
	 }else
	 map.put("personName","NA");
	 if(data[1] != null)
	 {
	 map.put("designation", data[1].toString());
	 }else
	 map.put("designation","NA");
	 
	 if(data[2] != null)
	 {
	 map.put("department", data[2].toString());
	 }else
	 map.put("designation","NA");
	 
	 if(data[3] != null)
	 {
	 map.put("communicationName", data[3].toString());
	 }else
	 map.put("communicationName","NA");
	 
	 if(data[4] != null)
	 {
	 map.put("degreeOfInfluence", data[4].toString());
	 }else
	 map.put("degreeOfInfluence","NA");
	 if(data[5] != null)
	 {
	 map.put("contactNo", data[5].toString());
	 }else
	 map.put("contactNo","NA");
	 if(data[6] != null)
	 {
	 map.put("emailOfficial", data[6].toString());
	 }else
	 map.put("emailOfficial","NA");
	 if(data[7] != null)
	 {
	 map.put("birthday", data[7].toString());
	 }else
	 map.put("birthday","NA");
	  if(data[8] != null)
	    {                                                    
	  map.put("anniversary", data[8].toString());
	    }else
	  map.put("anniversary","NA");
	  if(data[9] != null)
	 {
	 map.put("alternateMob", data[9].toString());
	 }else
	 map.put("alternateMob","NA");
	  if(data[10] != null)
	 {
	 map.put("alternateEmail", data[10].toString());
	 }else
	 map.put("alternateEmail","NA");
	 }
	 }
	return map;
	}
	
	public List<ClientSupportBean> fetchContactNameToConfigure(String clientName)
	{
	ClientSupportBean clsupportBean = null;
	List<ClientSupportBean> name =  new LinkedList<ClientSupportBean>();
	try
	{
	String query = "select personName from client_contact_data where clientName = '"+clientName+"' order by personName";
	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
	System.out.println("query "+query);
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object obdata=(Object)it.next();
	 clsupportBean = new ClientSupportBean();
	 if(obdata == null){clsupportBean.setContactName("NA");}
	 clsupportBean.setContactName(obdata.toString());
	 name.add(clsupportBean);
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return name;
	}
	
	public List<ClientSupportBean> fetchContactDetails(String clientName)
	{
	ClientSupportBean clsupportBean = null;
	List<ClientSupportBean> contactData =  new LinkedList<ClientSupportBean>();
	try
	{
	StringBuilder query = new StringBuilder("");
	query.append("select id, clientName, department,contactNo, anniversary, emailOfficial, degreeOfInfluence, communicationName, birthday, designation ");
	query.append(" from client_contact_data where clientName = '");
	query.append(clientName);
	query.append("'  and currentStatus = '1'");
	List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	System.out.println("query "+query.toString());
	if(dataList!=null && dataList.size()>0)
	{
	for(Iterator it=dataList.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 clsupportBean = new ClientSupportBean();
	 if(obdata[0] == null){obdata[0] = "NA";}
	 if(obdata[1] == null){obdata[1] = "NA";;}
	 if(obdata[2] == null){obdata[2] = "NA";;}
	 if(obdata[3] == null){obdata[3] = "NA";;}
	 if(obdata[4] == null){obdata[4] = "NA";;}
	 if(obdata[5] == null){obdata[5] = "NA";;}
	 if(obdata[6] == null){obdata[6] = "NA";;}
	 if(obdata[7] == null){obdata[7] = "NA";;}
	 if(obdata[8] == null){obdata[8] = "NA";;}
	 if(obdata[9] == null){obdata[9] = "NA";;}
	 clsupportBean.setContactId(obdata[0].toString());
	 clsupportBean.setClientname(obdata[1].toString());
	 clsupportBean.setContactDept(obdata[2].toString());
	 clsupportBean.setContactNumber(obdata[3].toString());
	 clsupportBean.setAnniversary(obdata[4].toString());
	 clsupportBean.setEmail(obdata[5].toString());
	 clsupportBean.setDegreeOfInfluence(obdata[6].toString());
	 clsupportBean.setContCommName(obdata[7].toString());
	 clsupportBean.setBirthday(obdata[8].toString());
	 clsupportBean.setDesignation(obdata[9].toString());
	 contactData.add(clsupportBean);
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return contactData;
	}

}