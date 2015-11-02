package com.Over2Cloud.ctrl.wfpm.patient;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonInterFace;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;

public class PatientActivityDashboardAction extends GridPropertyBean  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,String> dashboardConfigMap;
	private Map<String, String > offeringDetail;
	private String from_date,to_date;
	private String duration="Today";
	private JSONArray jsonArr;
	private String headerValue;
	private List<PatientDashSourcePojo> sourceList;
	private List<PatientDashNextActPojo> nextActList;
	private List<GridDataPropertyView> masterViewProps;
	private String stage;
	private String dataFor;
	
	public String beforeDashboardAction() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
			
				//getting view configuration
				getDashboardViewCofiguration();
				
				
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String upcomingActivities() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
			
				getGridProperties();
				
				
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	//for upcoming activities
	private void getGridProperties()
	{
		masterViewProps = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProps.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patName");
		gpv.setHeadingName("Patient Name");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		masterViewProps.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patId");
		gpv.setHeadingName("CRMID");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProps.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("activity");
		gpv.setHeadingName("Activity");
		gpv.setHideOrShow("false");
		gpv.setWidth(150);
		masterViewProps.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("date");
		gpv.setHeadingName("Date");
		gpv.setWidth(80);
		gpv.setHideOrShow("false");
		masterViewProps.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("doc");
		gpv.setHeadingName("Doctor/Buddy");
		gpv.setWidth(150);
		gpv.setHideOrShow("false");
		masterViewProps.add(gpv);
		
		
	}
	

	@SuppressWarnings("unchecked")
	public String upcomingActivitiesView() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				if (!ValidateSession.checkSession())
					return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append(" select count(*) from patient_visit_action");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append(" select pva.id,pbd.first_name,pbd.crm_id,pbd.uh_id,act.act_name,pva.maxDateTime,pc.emp_name from patient_visit_action as pva ");
					query.append("  INNER JOIN patient_basic_data as pbd on pbd.id=pva.patient_name");
					query.append(" INNER JOIN patientcrm_status_data as pcsd on pcsd.id=pva.next_activity");
					query.append(" LEFT JOIN activity_type as act on act.id=pcsd.status ");
					query.append(" INNER JOIN relationship_sub_type as rst on rst.id=pbd.type");
					query.append(" LEFT JOIN manage_contact as cc on cc.id = pva.relationship_mgr");
					query.append(" LEFT JOIN primary_contact as pc on pc.id = cc.emp_id");
					if(duration!=null && !duration.equalsIgnoreCase(""))
					{
						query.append("  where pva.date between '"+from_date+" 00:00:00' and '"+to_date+" 23:59:59'");
						if(stage!=null && !stage.equalsIgnoreCase(""))
						{
							query.append(" and pva.stage='"+stage+"'");
							
						}
						query.append(" and pva.status='Open'");
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}

					}

					query.append(" group by pva.next_activity");
					System.out.println("query.toString()>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					
					masterViewProps = new ArrayList<GridDataPropertyView>();
						List tempList = new ArrayList();
					if (data != null)
					{ 
						for (Iterator iterator2 = data.iterator(); iterator2.hasNext();)
						{
							Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
							Object[] object = (Object[]) iterator2.next();
							tempMap.put("id", getValueWithNullCheck(object[0]));
						 	tempMap.put("patName", getValueWithNullCheck(object[1]));
						 	if(object[3]!=null)
						 	{
						 		tempMap.put("patId", getValueWithNullCheck(object[3]));
						 	}else
						 	{
						 		if(object[2]!=null)
						 		{
						 			tempMap.put("patId", getValueWithNullCheck(object[2]));
						 		}
						 	}
							tempMap.put("activity", getValueWithNullCheck(object[4]));
							tempMap.put("date", DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[5])));
							tempMap.put("doc", getValueWithNullCheck(object[6]));
				 		  	tempList.add(tempMap);
						}
					}
					setMasterViewProps(tempList);
					 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));}
				
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			
			  }}else{
				  returnResult=LOGIN;
			  }
		return returnResult;
	}
	
	public String getDashboardLead() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
			
				headerValue=DateUtil.getDayFromMonth(String.valueOf(DateUtil.getCurrentMonth()));
				
				
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	//lead stage method
	@SuppressWarnings("unchecked")
	public String getJsonDataForLeadStage() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//stage Count For lead  method

				jsonArr= new JSONArray();
				StringBuilder qry = new StringBuilder();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				qry.append("select id,act_name from activity_type where act_stage='1' group by act_name");
				List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if(list!=null && !list.isEmpty()){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						JSONObject  obj = new JSONObject();
						obj.put("Type", object[1].toString());
						obj.put("TypeId", object[0].toString());
						 qry.append("select count(*),rst.rel_subtype from patient_visit_action as pva ");
						 qry.append(" INNER JOIN patient_basic_data as pbd on pbd.id=pva.patient_name");
						 qry.append(" INNER JOIN patientcrm_status_data as pcsd on pcsd.id=pva.next_activity");
						 qry.append(" INNER JOIN relationship_sub_type as rst on rst.id=pbd.type");
						 qry.append(" where pcsd.status='"+object[0].toString()+"' and pva.date between '"+from_date+" 00:00:00' and '"+to_date+" 23:59:59' ");
							if(stage!=null && !stage.equalsIgnoreCase("")&& !stage.equalsIgnoreCase("undefined"))
							{
								qry.append(" and pva.stage='"+stage+"'");
							}
							qry.append(" group by pva.next_activity");
						List list2 = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
							if(list2!=null && ! list2.isEmpty())
							{ 
								for (Iterator iterator1 = list2.iterator(); iterator1.hasNext();) 
								{
									Object[] object1 = (Object[]) iterator1.next();
									if(object1[0]!=null && object1[1]!=null)
									{
										if(object1[1].toString().equalsIgnoreCase("Domestic"))
										{
											obj.put("Domestic", object1[0].toString());
										}
										else if(object1[1].toString().equalsIgnoreCase("International"))
										{
											obj.put("International", object1[0].toString());
										}
									}
								}
							}
							qry.setLength(0);
							jsonArr.add(obj);
							
					}
				}
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	//lead Source method
	@SuppressWarnings("unchecked")
	public String getJsonDataForLeadSource() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//stage Count For lead  method

				jsonArr= new JSONArray();
				StringBuilder qry = new StringBuilder();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				qry.append("select ps.id,ps.src_subtype from pcrm_source as ps ");
				qry.append(" INNER JOIN relationship as rel on rel.id=ps.src_type ");
				qry.append(" where rel.name='Patient' group by src_subtype ");
				List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if(list!=null && !list.isEmpty()){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						JSONObject  obj = new JSONObject();
						obj.put("Source", object[1].toString());
						obj.put("SourceId", object[0].toString());
						 qry.append("select count(*),rst.rel_subtype from patient_basic_data as pbd ");
						 qry.append(" INNER JOIN relationship_sub_type as rst on rst.id=pbd.type");
						 qry.append(" where pbd.source='"+object[0].toString()+"' and pbd.date between '"+from_date+"' and '"+to_date+"'  ");
						
							qry.append("  group by pbd.source");
						List list2 = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
							if(list2!=null && ! list2.isEmpty())
							{ 
								for (Iterator iterator1 = list2.iterator(); iterator1.hasNext();) 
								{
									Object[] object1 = (Object[]) iterator1.next();
									if(object1[0]!=null && object1[1]!=null)
									{
										if(object1[1].toString().equalsIgnoreCase("Domestic"))
										{
											obj.put("Domestic", object1[0].toString());
										}
										else if(object1[1].toString().equalsIgnoreCase("International"))
										{
											obj.put("International", object1[0].toString());
										}
									}
								}
							}
							qry.setLength(0);
							jsonArr.add(obj);
							
					}
				}
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	//lead Source method
	@SuppressWarnings("unchecked")
	public String getJsonDataForLeadOffering() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//stage Count For lead  method

				jsonArr= new JSONArray();
				StringBuilder qry = new StringBuilder();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				qry.append("select id,offeringname from offeringlevel2 group by offeringname");
				List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if(list!=null && !list.isEmpty()){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						JSONObject  obj = new JSONObject();
						obj.put("offer", object[1].toString());
						obj.put("offerId", object[0].toString());
						 qry.append("select count(*),rst.rel_subtype from patient_visit_action as pva ");
						 qry.append(" INNER JOIN patient_basic_data as pbd on pbd.id=pva.patient_name");
						 qry.append(" INNER JOIN patientcrm_status_data as pcsd on pcsd.id=pva.next_activity");
						 qry.append(" INNER JOIN relationship_sub_type as rst on rst.id=pbd.type");
						 qry.append(" INNER JOIN offeringlevel3 as ofr3 on ofr3.id=pva.offeringlevel3");
						 qry.append(" INNER JOIN offeringlevel2 as ofr2 on ofr2.id=ofr3.offeringname");
						 qry.append(" where ofr2.id='"+object[0].toString()+"' and pva.date between '"+from_date+" 00:00:00' and '"+to_date+" 23:59:59' ");
						 if(stage!=null && !stage.equalsIgnoreCase("") && !stage.equalsIgnoreCase("undefined"))
							{
								qry.append(" and pva.stage='"+stage+"'");
							}
							qry.append("  group by rst.rel_subtype");
						List list2 = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
							if(list2!=null && ! list2.isEmpty())
							{ 
								for (Iterator iterator1 = list2.iterator(); iterator1.hasNext();) 
								{
									Object[] object1 = (Object[]) iterator1.next();
									if(object1[0]!=null && object1[1]!=null)
									{
										if(object1[1].toString().equalsIgnoreCase("Domestic"))
										{
											obj.put("Domestic", object1[0].toString());
										}
										else if(object1[1].toString().equalsIgnoreCase("International"))
										{
											obj.put("International", object1[0].toString());
										}
									}
								}
							}
							qry.setLength(0);
							jsonArr.add(obj);
							
					}
				}
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String getJsonDataForDueActivity() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//stage Count For lead  method

				jsonArr= new JSONArray();
				HashSet hashSet=new HashSet<String>();
				JSONArray jsonArr1= new JSONArray();
				StringBuilder qry = new StringBuilder();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				qry.append("select id,offeringname from offeringlevel2 group by offeringname");
				List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if(list!=null && !list.isEmpty()){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						JSONObject  obj = new JSONObject();
						obj.put("offer", object[1].toString());
						//obj.put("offerId", object[0].toString());
						 qry.append("SELECT COUNT(*),act.act_name FROM patient_visit_action AS pva ");
						 qry.append(" INNER JOIN patientcrm_status_data AS pcsd ON pcsd.id=pva.next_activity ");
						 qry.append(" INNER JOIN activity_type AS act ON act.id=pcsd.status ");
						 qry.append(" INNER JOIN offeringlevel3 AS ofr3 ON ofr3.id=pva.offeringlevel3 ");
						 qry.append(" INNER JOIN offeringlevel2 AS ofr2 ON ofr2.id=ofr3.offeringname ");
						 qry.append(" WHERE ofr2.id="+object[0].toString()+" AND pva.maxDateTime between '"+from_date+"' and '"+to_date+"' ");
						 if(stage!=null && !stage.equalsIgnoreCase("") && !stage.equalsIgnoreCase("undefined"))
							{
								qry.append(" AND pva.stage='"+stage+"'");
							}
						 if(dataFor!=null && !dataFor.equalsIgnoreCase("undefined")&& dataFor.equalsIgnoreCase("All"))
						 {
							 qry.append(" GROUP BY act.id");
						 }
						 else
						 {
							 qry.append(" AND pva.status NOT IN ('Completed','Closed') GROUP BY pva.id");
						 }
							
						System.out.println("Due query:::::"+qry);	
						List list2 = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
							if(list2!=null && ! list2.isEmpty())
							{ 
								for (Iterator iterator1 = list2.iterator(); iterator1.hasNext();) 
								{
									Object[] object1 = (Object[]) iterator1.next();
									if(object1[0]!=null && object1[1]!=null)
									{
										obj.put(object1[1].toString(), object1[0].toString());
										hashSet.add(object1[1].toString());
									}
								}
							}
							qry.setLength(0);
							jsonArr1.add(obj);
							
					}
				}
				jsonArr.add(hashSet);
				jsonArr.add(jsonArr1);
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String getJsonDataForDueOwner() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//stage Count For lead  method

				jsonArr= new JSONArray();
				HashSet hashSet=new HashSet<String>();
				JSONArray jsonArr1= new JSONArray();
				StringBuilder qry = new StringBuilder();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				qry.append("select id,offeringname from offeringlevel2 group by offeringname");
				List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				qry.setLength(0);
				
				if(list!=null && !list.isEmpty()){
					for (Iterator iterator = list.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						JSONObject  obj = new JSONObject();
						obj.put("offer", object[1].toString());
						//obj.put("offerId", object[0].toString());
						 qry.append("SELECT COUNT(*),pc.emp_name FROM patient_visit_action AS pva ");
						 qry.append(" INNER JOIN manage_contact AS mgc ON mgc.id=pva.relationship_mgr ");
						 qry.append(" INNER JOIN primary_contact AS pc ON pc.id=mgc.emp_id ");
						 qry.append(" INNER JOIN offeringlevel3 AS ofr3 ON ofr3.id=pva.offeringlevel3 ");
						 qry.append(" INNER JOIN offeringlevel2 AS ofr2 ON ofr2.id=ofr3.offeringname ");
						 qry.append(" WHERE ofr2.id="+object[0].toString()+" AND pva.maxDateTime between '"+from_date+"' and '"+to_date+"' AND pva.status NOT IN ('Completed','Closed') ");
						 if(stage!=null && !stage.equalsIgnoreCase("") && !stage.equalsIgnoreCase("undefined"))
							{
								qry.append(" AND pva.stage='"+stage+"'");
							}
							qry.append("  GROUP BY pva.id");
						System.out.println("owner query:::::"+qry);	
						List list2 = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
							if(list2!=null && ! list2.isEmpty())
							{ 
								for (Iterator iterator1 = list2.iterator(); iterator1.hasNext();) 
								{
									Object[] object1 = (Object[]) iterator1.next();
									if(object1[0]!=null && object1[1]!=null)
									{
										obj.put(object1[1].toString(), object1[0].toString());
										hashSet.add(object1[1].toString());
									}
								}
							}
							qry.setLength(0);
							jsonArr1.add(obj);
							
					}
				}
				jsonArr.add(hashSet);
				jsonArr.add(jsonArr1);
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	//lead Source method
	@SuppressWarnings("unchecked")
	public String getJsonDataForDomesticQry() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//stage Count For lead  method

				jsonArr= new JSONArray();
				StringBuilder qry = new StringBuilder();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				List temp = new ArrayList();
				temp.add("Domestic");
				temp.add("International");
				for (Iterator iterator = temp.iterator(); iterator.hasNext();) 
				{
					Object object = (Object) iterator.next();
					qry.append("select count(*),date from (");
					qry.append(" select pva.date as date from patient_visit_action as pva ");
					qry.append(" INNER JOIN patient_basic_data as pbd on pbd.id = pva.patient_name ");
					qry.append(" INNER JOIN relationship_sub_type as rel on rel.id =pbd.type");
					qry.append(" where pbd.type=(select id as relid from relationship_sub_type where rel_subtype='"+object.toString()+"')");
					 if(stage!=null && !stage.equalsIgnoreCase("") && !stage.equalsIgnoreCase("undefined"))
						{
							qry.append(" and pva.stage='"+stage+"'");
						}
					qry.append(" group by  pva.patient_name) as count group by date");
					List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
					qry.setLength(0);
					
					if(list!=null && !list.isEmpty()){
						for (Iterator iterator1 = list.iterator(); iterator1.hasNext();) 
						{	
							JSONObject obj = new JSONObject();
							Object[] object1 = (Object[]) iterator1.next();
								obj.put("date", object1[1].toString().split(" ")[0]);
								obj.put(object.toString(), object1[0].toString());
								qry.setLength(0);
								jsonArr.add(obj);
								
						}
					}
					qry.setLength(0);
				}
				
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
									 
	public String getDashboardBlockData() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				
				//gettingg list of count by suboffering
				getSubofferingWiseCount();
				
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	private void getSubofferingWiseCount()
	{
		List list = new createTable().executeAllSelectQuery("select count(*),ofr2.offeringname from patient_visit_action as pva INNER JOIN offeringlevel3 as ofr on ofr.id=pva.offeringlevel3 INNER JOIN offeringlevel2 as ofr2 on ofr2.id=ofr.offeringname where pva.date between '"+from_date+" 00:00:00' and '"+to_date+" 23:59:59'group by ofr2.offeringname order by count(*) limit 0,10".toString(), connectionSpace);
		offeringDetail= new HashMap<String, String>();
		if(list!=null && ! list.isEmpty()){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null){
					offeringDetail.put(object[1].toString(), object[0].toString());
					
				}
			}
		}
	}
	
	private void getVerticalWiseCount()
	{
		StringBuilder qry = new StringBuilder();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		PatientDashSourcePojo pojo=null;
		sourceList = new ArrayList<PatientDashSourcePojo>();
		qry.append("select id,verticalname from offeringlevel1 group by verticalname ");
		List list =cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		qry.setLength(0);
		
		if(list!=null && !list.isEmpty()){
			int corp=0,ref=0,walk=0;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				 pojo= new PatientDashSourcePojo();
				 pojo.setxAxisId(object[0].toString());
				 pojo.setxAxisName(object[1].toString());
				 qry.append("select count(*),pat.corporate from patient_basic_data as pat");
				 qry.append(" INNER JOIN patient_visit_action as pva on pva.patient_name=pat.id");
				 qry.append(" INNER JOIN offeringlevel3 as ofr on ofr.id=pva.offeringlevel3");
				 qry.append(" INNER JOIN offeringlevel2 as ofr2 on ofr2.id=ofr.offeringname");
				 qry.append(" INNER JOIN offeringlevel1 as ofr1 on ofr1.id=ofr2.verticalname");
				 qry.append(" where ofr1.id='"+object[0]+"' and pva.date between '"+from_date+" 00:00:00' and '"+to_date+" 23:59:59' ");
				 if(stage!=null && !stage.equalsIgnoreCase("")&& !stage.equalsIgnoreCase("undefined"))
					{
						qry.append(" and pva.stage='"+stage+"'");
					}
					qry.append("  group by pat.corporate");
				List list2 = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
					if(list2!=null && ! list2.isEmpty())
					{
						for (Iterator iterator1 = list2.iterator(); iterator1.hasNext();) 
						{
							Object[] object1 = (Object[]) iterator1.next();
							if(object1[0]!=null && object1[1]!=null){
								if(object1[1].toString().equalsIgnoreCase("1"))
								{
								corp+=Integer.parseInt(object1[0].toString());	
								}
								else if(object1[1].toString().equalsIgnoreCase("2"))
								{
									ref+=Integer.parseInt(object1[0].toString());	
								}
								else if(object1[1].toString().equalsIgnoreCase("3"))
								{
									walk+=Integer.parseInt(object1[0].toString());	
								}
								
							}
						}
						pojo.setRef(String.valueOf(ref));
						pojo.setCorp(String.valueOf(corp));
						pojo.setWalk(String.valueOf(walk));
					}
					qry.setLength(0);
					sourceList.add(pojo);
					
			}
		}
		
	}

	
	//defines date range from view type duration 
	private void dateInitializer(){
		if(duration.equalsIgnoreCase("Today")){
			from_date= DateUtil.getCurrentDateUSFormat();
			to_date=from_date;
		}
		else if(duration.equalsIgnoreCase("Weekly"))
		{
			from_date= DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
			to_date=DateUtil.getCurrentDateUSFormat();
		}
		else if(duration.equalsIgnoreCase("Monthly"))
		{
			from_date= DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());
			to_date=DateUtil.getCurrentDateUSFormat();	
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String getNextActivityDetails() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				nextActList= new ArrayList<PatientDashNextActPojo>();
				StringBuilder qry = new StringBuilder();
				PatientDashNextActPojo pojo=null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				qry.append("select pd.uh_id,pd.crm_id,pd.first_name,ofr.subofferingname,pca.status,pva.maxDateTime,emp.empName  from patient_visit_action as pva");
				qry.append(" LEFT JOIN patient_basic_data as pd on pd.id=pva.patient_name");
				qry.append(" LEFT JOIN offeringlevel3 as ofr on ofr.id=pva.offeringlevel3");
				qry.append(" LEFT JOIN patientcrm_status_data as pca on pca.id=pva.next_activity");
				qry.append(" LEFT JOIN compliance_contacts as cc on cc.id=pva.relationship_mgr");
				qry.append(" LEFT JOIN employee_basic as emp on emp.id=cc.emp_id");
				qry.append(" where pva.maxDateTime>'"+from_date+"'");
				List list = cbt.executeAllSelectQueryLogin(qry.toString(), connectionSpace);
				if(list!=null && !list.isEmpty()){
					
					for (Iterator iterator = list.iterator(); iterator.hasNext();) 
					{
						pojo = new PatientDashNextActPojo();
						Object[] object = (Object[]) iterator.next();
						if(object[0]==null)
						{
							if(object[1]!=null)
							{
								object[0]=object[1];
							}
							else
							{
								object[0]="NA";
							}
							
						}
						pojo.setUhid(object[0].toString());
						pojo.setPatName(getValueWithNullCheck(object[2]));
						pojo.setSuboffer(getValueWithNullCheck(object[3]));
						pojo.setStatus(getValueWithNullCheck(object[4]));
						pojo.setAccMgr(getValueWithNullCheck(object[6]));
						if(object[5]!=null && !object[5].toString().equalsIgnoreCase(""))
						{
							pojo.setMaxDate(DateUtil.convertDateToIndianFormat(object[5].toString()));
						}else
						{
							pojo.setMaxDate("NA");
						}
						nextActList.add(pojo);
					}
						
					
				}
			
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	private String getValueWithNullCheck(Object value)
	{
	return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	@SuppressWarnings("unchecked")
	public String getVerticalDetails() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//gettingg list of count by suboffering
				getVerticalWiseCount();
				jsonArr= new JSONArray();
				if(sourceList!=null && !sourceList.isEmpty())
				{
					JSONObject obj;
						for(PatientDashSourcePojo pojo:sourceList)
						{ 
						 obj=new JSONObject();
						 obj.put("Vertical",pojo.getxAxisName());
						 obj.put("Corp", pojo.getCorp());
						 obj.put("Ref", pojo.getRef());
						 obj.put("Walk", pojo.getWalk());
						 jsonArr.add(obj);
						}
					}
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	//Lead Board
	@SuppressWarnings("unchecked")
	public String getLeadDetails() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//gettingg list of count by suboffering
				getLeadEscalated();
				jsonArr= new JSONArray();
				if(sourceList!=null && !sourceList.isEmpty())
				{
					JSONObject obj;
						for(PatientDashSourcePojo pojo:sourceList)
						{ 
						 obj=new JSONObject();
						 obj.put("Vertical",pojo.getxAxisName());
						 obj.put("Corp", pojo.getCorp());
						 obj.put("Ref", pojo.getRef());
						 obj.put("Walk", pojo.getWalk());
						 jsonArr.add(obj);
						}
					}
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	private void getLeadEscalated(){
		StringBuilder qry = new StringBuilder();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		qry.append("select id,forcastName from forcastcategarymaster");
		sourceList = new ArrayList<PatientDashSourcePojo>();
		
		List list = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		if (list!=null && list.isEmpty()){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				PatientDashSourcePojo pojo = new PatientDashSourcePojo();
				pojo.setxAxisId(object[0].toString());
				pojo.setxAxisName(object[0].toString());
				
				
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public String getDashboardOfferingsChart() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				dateInitializer();
				//gettingg list of count by suboffering
				getSubofferingWiseCount();
				jsonArr= new JSONArray();
				if(offeringDetail!=null && !offeringDetail.isEmpty()){
					JSONObject obj;
					Set set = offeringDetail.entrySet();
					Iterator i = set.iterator();
					while(i.hasNext())
					{ 
					Map.Entry me = (Map.Entry)i.next();
					 obj=new JSONObject();
					 obj.put("Offering",(String)me.getKey() );
					 obj.put("Counter", (String)me.getValue());
					 jsonArr.add(obj);
					} 
				}
				returnResult=SUCCESS;
			  } catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	private void getDashboardViewCofiguration()
	{
		List list = new createTable().executeAllSelectQuery("select id,field_name from patient_dashboard_configuration where status='1'".toString(), connectionSpace);
		dashboardConfigMap= new TreeMap<String, String>();
		if(list!=null && !list.isEmpty())
		{
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				dashboardConfigMap.put(object[0].toString(), object[1].toString());
			}
		}
		
	}
	
	
	
	
	

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

	public void setOfferingDetail(Map<String, String > offeringDetail) {
		this.offeringDetail = offeringDetail;
	}

	public Map<String, String > getOfferingDetail() {
		return offeringDetail;
	}

	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}

	public JSONArray getJsonArr() {
		return jsonArr;
	}

	public void setNextActList(List<PatientDashNextActPojo> nextActList) {
		this.nextActList = nextActList;
	}

	public List<PatientDashNextActPojo> getNextActList() {
		return nextActList;
	}

	public void setDashboardConfigMap(Map<String,String> dashboardConfigMap) {
		this.dashboardConfigMap = dashboardConfigMap;
	}

	public Map<String,String> getDashboardConfigMap() {
		return dashboardConfigMap;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	public List<GridDataPropertyView> getMasterViewProps() {
		return masterViewProps;
	}

	public void setMasterViewProps(List<GridDataPropertyView> masterViewProps) {
		this.masterViewProps = masterViewProps;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStage() {
		return stage;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	
	
	

}
