package com.Over2Cloud.ctrl.wfpm.referral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SessionFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper1;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper3;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.lead.LeadActionControlDao;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper2;

public class ReferralActivityBoard extends GridPropertyBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewPropForContact = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> referralColumnMap, referralDropDown,referralCalender,referralContactColumnMap, referralContactDropDown,referralContactCalender ;
	private String referralSubType,CommonAddr,referralStatus,from_date,from_date1,todate,todate1,
	from_source,from_source1,searchParameter,searchParameter1,action_status,comments,tabel,country,data_status;
	private Map<String, String> statusmap;
	private JSONArray dataArray = new JSONArray();
	private JSONArray commonJsonArr;
	private List<?> commonList;
	private int indexVal;
	private Map countryMap=null,sourceMap=null,accMgrMap=null,deptMap=null;
	public Map getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map deptMap) {
		this.deptMap = deptMap;
	}

	public String beforeReferralView(){
		boolean CheckSession = ValidateSession.checkSession();
		if(CheckSession){
			try{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				from_date=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(),-7);
				from_date=DateUtil.convertDateToIndianFormat(from_date);
				from_date1=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(),-7);
				from_date1=DateUtil.convertDateToIndianFormat(from_date1);
				sourceMap = new TreeMap<String, String>();
				List list= cbt.executeAllSelectQuery("select id,src_subtype from pcrm_source where src_type='1' group by src_subtype", connectionSpace);
				if(list!=null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							sourceMap.put( object[0].toString(),object[1].toString());	
						}
					}
				}
				
				
				//System.out.println("referralSubType::::::::::"+referralSubType);
				//System.out.println("referralStatus:::::::::::"+referralStatus);
				return SUCCESS;
			}catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
			
		}else{
			return LOGIN;
		}
		
	}
	
	public String beforeAdhocReferralView(){
		boolean CheckSession = ValidateSession.checkSession();
		if(CheckSession){
			try{
			/*	CommonOperInterface cbt = new CommonConFactory().createInterface();
				sourceMap = new TreeMap<String, String>();
				List list= cbt.executeAllSelectQuery("select id,sourceName from sourcemaster group by sourceName", connectionSpace);
				if(list!=null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							sourceMap.put( object[0].toString(),object[1].toString());	
						}
					}
				}
				*/
				
				//System.out.println("referralSubType::::::::::"+referralSubType);
				//System.out.println("referralStatus:::::::::::"+referralStatus);
				return SUCCESS;
			}catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
			
		}else{
			return LOGIN;
		}
		
	}
	
	
	public String beforeReferralGridVieW(){
		boolean CheckSession = ValidateSession.checkSession();
		if(CheckSession){
			String result=null;
			try{
				
				if(referralSubType!=null && !referralSubType.equalsIgnoreCase("") && !referralSubType.equalsIgnoreCase("undefined"))
				{	
				//	System.out.println(referralSubType);	
					setGridProperties(referralSubType);
					if(referralSubType.equalsIgnoreCase("Individual"))
					{
						result="indisuccess";
					}
					else{
						result="corpsuccess";	
					}
				}
				
			}catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
		//	System.out.println(result);
		return result;	
		}else{
			return LOGIN;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public String createIndivisualRef(){
		String result=null;
		boolean CheckSession = ValidateSession.checkSession();
		if(CheckSession){
			try
			{
				country="4179";
				if(referralSubType!=null && !referralSubType.equalsIgnoreCase("") && !referralSubType.equalsIgnoreCase("undefined"))
				{
				countryMap = new TreeMap<String, String>();
				sourceMap = new TreeMap<String, String>();
				accMgrMap = new TreeMap<String, String>();
				deptMap = new TreeMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List list = cbt.executeAllSelectQuery("select id,country_name from country group by country_name", connectionSpace);
				if(list!=null && !list.isEmpty()){
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							countryMap.put( object[0].toString(),object[1].toString());	
						}
					}
				}
				list.clear();
				
                list= cbt.executeAllSelectQuery("select id,src_subtype from pcrm_source where src_type='1' group by src_subtype ", connectionSpace);
				if(list!=null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							sourceMap.put( object[0].toString(),object[1].toString());	
						}
					}
				}
				
				list.clear();
				list= cbt.executeAllSelectQuery("select rmd.id,emp.emp_name from rel_mgr_data as rmd INNER JOIN manage_contact as cc on cc.id=rmd.name INNER JOIN primary_contact as emp on emp.id=cc.emp_id", connectionSpace);
				if(list!=null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							accMgrMap.put( object[0].toString(),object[1].toString());	
						}
					}
				}
				list.clear();
				list= cbt.executeAllSelectQuery("select id,contact_subtype_name from contact_sub_type order by contact_subtype_name asc ", connectionSpace);
				if(list!=null && !list.isEmpty())
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							deptMap.put( object[0].toString(),object[1].toString());	
						}
					}
				}
				System.out.println(referralSubType);
				if(referralSubType.equalsIgnoreCase("Institutional"))
				{
					addReferralPageFeilds();
					addReferralContactPageFeilds();
						result="instsuccess";
				}else{
					addPageFeilds();
					result="indisuccess";
				}
					
				}
				
			}catch (Exception e) 
			{
				e.printStackTrace();
				result=ERROR;
			}
			
		}else{
			result=LOGIN;
		}
	//	System.out.println("result:::::::::::::"+result);
		return result;
	}
	
	//getting state info
	
	public void addPageFeilds(){
		List<GridDataPropertyView> columnList = Configuration.getConfigurationData("mapped_referral_individual_data", accountID, connectionSpace, "", 0, "table_name", "referral_individual_data_configuration");

		referralColumnMap = new ArrayList<ConfigurationUtilBean>();
		referralDropDown = new ArrayList<ConfigurationUtilBean>();
		referralCalender = new ArrayList<ConfigurationUtilBean>();        
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (columnList != null && columnList.size() > 0)
		{//&& !gdp.getColomnName().equalsIgnoreCase("quantity")
			for (GridDataPropertyView gdp : columnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("status")
						&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("deptHierarchy ")
						&& !gdp.getColomnName().equalsIgnoreCase("dept_subdept") && !gdp.getColomnName().equalsIgnoreCase("flag") && !gdp.getColomnName().equalsIgnoreCase("status")
				        && !gdp.getColomnName().equalsIgnoreCase("data_status") && !gdp.getColomnName().equalsIgnoreCase("transfer_status") && !gdp.getColomnName().equalsIgnoreCase("trn_comment") && !gdp.getColomnName().equalsIgnoreCase("ack_comment")		
				     )
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					referralColumnMap.add(obj);
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setField_name(gdp.getHeadingName());
					obj.setField_value(gdp.getColomnName());
					obj.setColType(gdp.getColType());
					obj.setValidationType(gdp.getValidationType());
					if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					//System.out.println("Drop Down ::::::::::"+gdp.getColomnName());
					referralDropDown.add(obj);
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					//System.out.println("Date :::  "+gdp.getColomnName());
					obj.setKey(gdp.getHeadingName());
					obj.setValue(gdp.getColomnName());
					obj.setColType(gdp.getColType());
					obj.setValidationType(gdp.getValidationType());
					if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					referralCalender.add(obj);
				}
			}

		}
		else
		{
			addActionMessage("Table Is Not properly Configured !");
		}
	}
	
	public void addReferralPageFeilds(){
		List<GridDataPropertyView> columnList = Configuration.getConfigurationData("mapped_referral_institutional_data", accountID, connectionSpace, "", 0, "table_name", "referral_institutional_data_configuration");

		referralColumnMap = new ArrayList<ConfigurationUtilBean>();
		referralDropDown = new ArrayList<ConfigurationUtilBean>();
		referralCalender = new ArrayList<ConfigurationUtilBean>();        
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (columnList != null && columnList.size() > 0)
		{//&& !gdp.getColomnName().equalsIgnoreCase("quantity")
			for (GridDataPropertyView gdp : columnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("status")
						&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("action_by")
						&& !gdp.getColomnName().equalsIgnoreCase("action_on") && !gdp.getColomnName().equalsIgnoreCase("comments") && !gdp.getColomnName().equalsIgnoreCase("status")
				        && !gdp.getColomnName().equalsIgnoreCase("data_status") && !gdp.getColomnName().equalsIgnoreCase("transfer_status") && !gdp.getColomnName().equalsIgnoreCase("trn_comment") && !gdp.getColomnName().equalsIgnoreCase("ack_comment")		
				     )
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					referralColumnMap.add(obj);
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setField_name(gdp.getHeadingName());
					obj.setField_value(gdp.getColomnName());
					obj.setColType(gdp.getColType());
					obj.setValidationType(gdp.getValidationType());
					if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					//System.out.println("Drop Down ::::::::::"+gdp.getColomnName());
					referralDropDown.add(obj);
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					//System.out.println("Date :::  "+gdp.getColomnName());
					obj.setKey(gdp.getHeadingName());
					obj.setValue(gdp.getColomnName());
					obj.setColType(gdp.getColType());
					obj.setValidationType(gdp.getValidationType());
					if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					referralCalender.add(obj);
				}
			}

		}
		else
		{
			addActionMessage("Asset Detail Table Is Not properly Configured !");
		}
	}
	
	
	public void addReferralContactPageFeilds(){
		List<GridDataPropertyView> columnList = Configuration.getConfigurationData("mapped_referral_institutional_mapcontact", accountID, connectionSpace, "", 0, "table_name", "referral_institutional_mapcontact_configuration");

		referralContactColumnMap = new ArrayList<ConfigurationUtilBean>();
		referralContactDropDown = new ArrayList<ConfigurationUtilBean>();
		referralContactCalender = new ArrayList<ConfigurationUtilBean>();        
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (columnList != null && columnList.size() > 0)
		{//&& !gdp.getColomnName().equalsIgnoreCase("quantity")
			for (GridDataPropertyView gdp : columnList)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("status")
						&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("deptHierarchy ")
						&& !gdp.getColomnName().equalsIgnoreCase("dept_subdept") && !gdp.getColomnName().equalsIgnoreCase("flag") && !gdp.getColomnName().equalsIgnoreCase("status")
				        && !gdp.getColomnName().equalsIgnoreCase("transfer") && !gdp.getColomnName().equalsIgnoreCase("transfer_status") && !gdp.getColomnName().equalsIgnoreCase("trn_comment") && !gdp.getColomnName().equalsIgnoreCase("ack_comment")		
				     )
				{
					obj.setKey(gdp.getColomnName());
					obj.setValue(gdp.getHeadingName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					referralContactColumnMap.add(obj);
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setField_name(gdp.getHeadingName());
					obj.setField_value(gdp.getColomnName());
					obj.setColType(gdp.getColType());
					obj.setValidationType(gdp.getValidationType());
					if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					//System.out.println("Drop Down ::::::::::"+gdp.getColomnName());
					referralContactDropDown.add(obj);
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					//System.out.println("Date :::  "+gdp.getColomnName());
					obj.setKey(gdp.getHeadingName());
					obj.setValue(gdp.getColomnName());
					obj.setColType(gdp.getColType());
					obj.setValidationType(gdp.getValidationType());
					if (gdp.getMandatroy() != null && gdp.getMandatroy().equalsIgnoreCase("1"))
					{
						obj.setMandatory(true);
					}
					else
					{
						obj.setMandatory(false);
					}
					referralContactCalender.add(obj);
				}
			}

		}
		else
		{
			addActionMessage("Asset Detail Table Is Not properly Configured !");
		}
	}
	
	
	
	public String fetchStateInfo(){
		boolean CheckSession = ValidateSession.checkSession();
		if(CheckSession){
			try{
				List list = new createTable().executeAllSelectQuery("select id,state_name from state where country_code='"+CommonAddr+"' group by state_name order by state_name  ", connectionSpace);
				commonJsonArr = new JSONArray();
				if(list!=null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						
						Object object[] = (Object[]) iterator.next();
						if(object!=null){
							obj = new JSONObject();
							obj.put("id", object[0].toString());
							obj.put("state", object[1].toString());
							commonJsonArr.add(obj);
						}
					
					}
				}
					return SUCCESS;
				
			}catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
			
		}else{
			return LOGIN;
		}
		
	}

	public String fetchCityInfo(){
		boolean CheckSession = ValidateSession.checkSession();
		if(CheckSession){
			try{
				List list = new createTable().executeAllSelectQuery("select id,city_name from city where name_state='"+CommonAddr+"' group by city_name", connectionSpace);
				commonJsonArr = new JSONArray();
				if(list!=null && !list.isEmpty())
				{
					JSONObject obj = null;
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null){
							obj = new JSONObject();
							obj.put("id", object[0].toString());
							obj.put("city", object[1].toString());
							commonJsonArr.add(obj);
						}
					
					}
				}
					return SUCCESS;
				
			}catch (Exception e) 
			{
				e.printStackTrace();
				return ERROR;
			}
			
		}else{
			return LOGIN;
		}
		
	}
	
	
	
	public String addindivisualRef(){
		boolean CheckSession = ValidateSession.checkSession();
		String result=null;
		if(CheckSession){
			try{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				CommunicationHelper COH=new CommunicationHelper();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				List<com.Over2Cloud.Rnd.TableColumes> tableColumn = new ArrayList<com.Over2Cloud.Rnd.TableColumes>();
				List<InsertDataTable> insertReferralEmpBasic = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertReferralData= new ArrayList<InsertDataTable>();
				InsertDataTable ReferralEmpBasicObj = null;
				com.Over2Cloud.Rnd.TableColumes obj=null;
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("map_emp_id");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				for (Iterator iterator = requestParameterNames.iterator(); iterator.hasNext();)
				{
					String column = (String) iterator.next();
					
					if(column.equalsIgnoreCase("emp_name") || column.equalsIgnoreCase("comm_name") || column.equalsIgnoreCase("email_id") 
							|| column.equalsIgnoreCase("mobile_no")|| column.equalsIgnoreCase("dob")|| column.equalsIgnoreCase("doa")
							|| column.equalsIgnoreCase("address") || column.equalsIgnoreCase("degreeOfInfluence"))
					{
						
					}
					else if(!column.equalsIgnoreCase("country") && !column.equalsIgnoreCase("state"))
					{
						obj= new com.Over2Cloud.Rnd.TableColumes();
						obj.setColumnname(column);
						obj.setDatatype("varchar(255)");
						obj.setConstraint("default NULL");
						tableColumn.add(obj);
					}
				}
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("user_name");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("date");
				obj.setDatatype("varchar(15)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("time");
				obj.setDatatype("varchar(15)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				cbt.createTable22("referral_contact_data", tableColumn, connectionSpace);
			
				//inserting data into employee_basic
				/*ReferralEmpBasicObj = new InsertDataTable();
				ReferralEmpBasicObj.setColName("regLevel");
				ReferralEmpBasicObj.setDataName("1");
				insertReferralEmpBasic.add(ReferralEmpBasicObj);
				
				
				ReferralEmpBasicObj = new InsertDataTable();
				ReferralEmpBasicObj.setColName("groupId");
				ReferralEmpBasicObj.setDataName(fetchGroupName());
				insertReferralEmpBasic.add(ReferralEmpBasicObj);

				ReferralEmpBasicObj = new InsertDataTable();
				ReferralEmpBasicObj.setColName("deptname");
				ReferralEmpBasicObj.setDataName(fetchAsociateDept());
				insertReferralEmpBasic.add(ReferralEmpBasicObj);*/
				
				
				for (Iterator iterator = requestParameterNames.iterator(); iterator.hasNext();)
				{
					String column = (String) iterator.next();
					
					if(column.equalsIgnoreCase("emp_name") || column.equalsIgnoreCase("comm_name") || column.equalsIgnoreCase("email_id") 
							|| column.equalsIgnoreCase("mobile_no")|| column.equalsIgnoreCase("dob")|| column.equalsIgnoreCase("doa")
							|| column.equalsIgnoreCase("address") || column.equalsIgnoreCase("degreeOfInfluence"))
					{
						ReferralEmpBasicObj = new InsertDataTable();
						ReferralEmpBasicObj.setColName(column);
						ReferralEmpBasicObj.setDataName(request.getParameter(column).toString());
						insertReferralEmpBasic.add(ReferralEmpBasicObj);
					}
					
				}
				
				ReferralEmpBasicObj = new InsertDataTable();
				ReferralEmpBasicObj.setColName("sub_type_id");
				ReferralEmpBasicObj.setDataName("3");
				insertReferralEmpBasic.add(ReferralEmpBasicObj);
				
				int id=cbt.insertDataReturnId("primary_contact", insertReferralEmpBasic, connectionSpace);
				
				if(id>0)
				{
					InsertDataTable insertData= null;
					insertData = new InsertDataTable();
					insertData.setColName("map_emp_id");
					insertData.setDataName(id);
					insertReferralData.add(insertData);	
					for(Iterator iterator = requestParameterNames.iterator(); iterator.hasNext();)
					{
						String column = (String) iterator.next();
						if(column.equalsIgnoreCase("emp_name") || column.equalsIgnoreCase("comm_name") || column.equalsIgnoreCase("email_id") 
								|| column.equalsIgnoreCase("mobile_no")|| column.equalsIgnoreCase("dob")|| column.equalsIgnoreCase("doa")
								|| column.equalsIgnoreCase("address"))
						{
							
						}
						else if(!column.equalsIgnoreCase("country") && !column.equalsIgnoreCase("state"))
						{
							insertData = new InsertDataTable();
							insertData.setColName(column);
							insertData.setDataName(request.getParameter(column).toString());
							insertReferralData.add(insertData);		
						}
					}
						
					insertData = new InsertDataTable();
					insertData.setColName("status");
					insertData.setDataName("Unapproved");
					insertReferralData.add(insertData);
					
					insertData = new InsertDataTable();
					insertData.setColName("data_status");
					insertData.setDataName("0");
					insertReferralData.add(insertData);
					
					insertData = new InsertDataTable();
					insertData.setColName("user_name");
					insertData.setDataName(userName);
					insertReferralData.add(insertData);	
					
					insertData = new InsertDataTable();
					insertData.setColName("date");
					insertData.setDataName(DateUtil.getCurrentDateUSFormat());
					insertReferralData.add(insertData);	

					insertData = new InsertDataTable();
					insertData.setColName("time");
					insertData.setDataName(DateUtil.getCurrentTime());
					insertReferralData.add(insertData);	
					
					int max=cbt.insertDataReturnId("referral_contact_data", insertReferralData, connectionSpace);
						if(max>0)
						{
							String name=null,mobile=null,email=null;
							boolean putsmsstatus=false,putmailstatus=false;
							String acc_mgr=request.getParameter("acc_mgr");
							if(acc_mgr!=null)
							{
							String arr[]=CUA.getEmpDetailsByEmpId("WFPM", acc_mgr);
							for(int i=0;i<arr.length;i++)
							{
							if(arr[1]!=null)
							{
								name=arr[1].toString();
							}
							else if(arr[2]!=null)
							{
								mobile=arr[2].toString();
							}
							else if(arr[3]!=null)
							{
								email=arr[3].toString();
							}
							
							//System.out.println("Details:::::::::::"+arr[i]);
							}
							 addActionMessage("Data Added Successfuly!!!!");
							 result= SUCCESS;
							if(name!=null && !name.equals("") && !name.equals("NA"))
							{
							   if (mobile!=null && !mobile.equals("") && !mobile.equals("NA") && mobile != "" && mobile != "NA" && mobile.length() == 10 && (mobile.startsWith("9") || mobile.startsWith("8") || mobile.startsWith("7")))
					           {
								 putsmsstatus = COH.addMessage(name, " ", mobile,"report_sms" , "Referral Alert", "Pending", "0", "COM", connectionSpace);
								
					           }
							   else
					           {
					        	   if(mobile!=null && !mobile.equals("") && !mobile.equals("NA") && mobile != "" && mobile != "NA" && mobile.startsWith("0") && mobile.length()>10)
					        	   {
					        		   if(mobile.length()==11)
					        		   {
					        			   mobile=mobile.substring(1); 
					        			   putsmsstatus = COH.addMessage(name, " ", mobile," report_sms", "Referral Alert", "Pending", "0", "COM", connectionSpace);
					        		   }
					        	   }
					           }
							   
							   if (email!=null && !email.equals("") && !email.equals("NA"))
					           {
							    putmailstatus = COH.addMail(name,"",email,"Referral Alert", "mailtext", "Report", "Pending", "0","", "WFPM", connectionSpace);
					           }
							   if(putmailstatus || putsmsstatus)
							   {
							   addActionMessage("Data Added Successfuly!!!!");
							   result= SUCCESS;
							   }
							}
							}
							else{
								 addActionMessage("Data Added Successfuly!!!!");
								   result= SUCCESS;
							}
						}
				    }
			
					
				
			}catch (Exception e) 
			{
				e.printStackTrace();
				result= ERROR;
			}
			
		}else{
			result= LOGIN;
		}
		return result;
	}
	
	
	
	
	public String addinstitutionalRef(){
		boolean CheckSession = ValidateSession.checkSession();
		String result=null;
		if(CheckSession){
			try{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				CommunicationHelper COH=new CommunicationHelper();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				List<com.Over2Cloud.Rnd.TableColumes> tableColumn = new ArrayList<com.Over2Cloud.Rnd.TableColumes>();
				List<InsertDataTable> insertReferralEmpBasic = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertReferralData= new ArrayList<InsertDataTable>();
				//InsertDataTable ReferralEmpBasicObj = null;
				com.Over2Cloud.Rnd.TableColumes obj=null;
				InsertDataTable ob = null;
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("emp_name");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				for (Iterator iterator = requestParameterNames.iterator(); iterator.hasNext();)
				{
					String column = (String) iterator.next();
					
					if(column.equalsIgnoreCase("empName") || column.equalsIgnoreCase("comName") || column.equalsIgnoreCase("emailIdOne") 
							|| column.equalsIgnoreCase("mobOne")|| column.equalsIgnoreCase("dob")|| column.equalsIgnoreCase("doa")
							|| column.equalsIgnoreCase("designation"))
					{
						
					}
					else if(!column.equalsIgnoreCase("country") && !column.equalsIgnoreCase("state"))
					{
						obj= new com.Over2Cloud.Rnd.TableColumes();
						obj.setColumnname(column);
						obj.setDatatype("varchar(255)");
						obj.setConstraint("default NULL");
						tableColumn.add(obj);
					}
				}
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("status");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("user_name");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("date");
				obj.setDatatype("varchar(15)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("time");
				obj.setDatatype("varchar(15)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				cbt.createTable22("referral_institutional_data", tableColumn, connectionSpace);
			//System.out.println(":::::::::::::::::referral_institutional_data");
				
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					// //System.out.println(Parmname+"  ======= "+paramValue);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
					{
						if(!Parmname.equalsIgnoreCase("country") && !Parmname.equalsIgnoreCase("state"))
						{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertReferralData.add(ob);
					    }
					}
				}
						
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Unapproved");
					insertReferralData.add(ob);		
					
					ob = new InsertDataTable();
					ob.setColName("data_status");
					ob.setDataName("0");
					insertReferralData.add(ob);		
					
				    ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertReferralData.add(ob);	
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertReferralData.add(ob);	

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertReferralData.add(ob);	
					
					 int max=cbt.insertDataReturnId("referral_institutional_data", insertReferralData, connectionSpace);
					if(max>0)
					{
						   
						String name=null,mobile=null,email=null;
						boolean putsmsstatus=false,putmailstatus=false;
						String acc_mgr=request.getParameter("acc_mgr");
						if(acc_mgr!=null)
						{
						String arr[]=CUA.getEmpDetailsByEmpId("WFPM", acc_mgr);
						for(int i=0;i<arr.length;i++)
						{
						if(arr[1]!=null)
						{
							name=arr[1].toString();
						}
						else if(arr[2]!=null)
						{
							mobile=arr[2].toString();
						}
						else if(arr[3]!=null)
						{
							email=arr[3].toString();
						}
						
						System.out.println("Details:::::::::::"+arr[i]);
						}
						 addActionMessage("Data Added Successfuly!!!!");
						 result= SUCCESS;
						
						if(name!=null && !name.equals("") && !name.equals("NA"))
						{
							String report_sms = "Dear "+name+", a new referral is added for this approval please visit at http://192.168.1.53:8080/over2cloud .";		
						   if (mobile!=null && !mobile.equals("") && !mobile.equals("NA") && mobile != "" && mobile != "NA" && mobile.length() == 10 && (mobile.startsWith("9") || mobile.startsWith("8") || mobile.startsWith("7")))
				           {
							 putsmsstatus = COH.addMessage(name, " ", mobile,report_sms , "Referral Alert", "Pending", "0", "COM", connectionSpace);
					       }
						   else
				           {
				        	   if(mobile!=null && !mobile.equals("") && !mobile.equals("NA") && mobile != "" && mobile != "NA" && mobile.startsWith("0") && mobile.length()>10)
				        	   {
				        		   if(mobile.length()==11)
				        		   {
				        			   mobile=mobile.substring(1); 
				        			   putsmsstatus = COH.addMessage(name, " ", mobile,report_sms, "Referral Alert", "Pending", "0", "COM", connectionSpace);
				        		   }
				        	   }
				           }
						   
						   if (email!=null && !email.equals("") && !email.equals("NA"))
				           {
						    putmailstatus = COH.addMail(name,"",email,"Referral Alert", "mailtext", "Report", "Pending", "0","", "WFPM", connectionSpace);
				           }
						   if(putmailstatus || putsmsstatus)
						   {
						   addActionMessage("Data Added Successfuly!!!!");
						   result= SUCCESS;
						   }
						}
						}
						else{
							  addActionMessage("Data Added Successfuly!!!!");
							   result= SUCCESS;
						}
						
					}
			}catch (Exception e) 
			{
				e.printStackTrace();
				result= ERROR;
			}
			
		}else{
			result= LOGIN;
		}
		return result;
	}
	
	
	
	public String addInstituationalContact()
	{
		String returnString = null;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
				// List <InsertDataTable> insertData=new
				// ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				List<InsertDataTable> insertAssociateEmpBasic = new ArrayList<InsertDataTable>();
				com.Over2Cloud.Rnd.TableColumes obj=null;
				ArrayList<ArrayList> mainList = new ArrayList<ArrayList>();
				ArrayList<String> fieldNameList = new ArrayList<String>();
				
				/*for (Iterator iterator = requestParameterNames.iterator(); iterator.hasNext();)
				{
					
			     		String column = (String) iterator.next();
			     		if(!column.equalsIgnoreCase("indexVal"))
			     		{
			     		obj= new com.Over2Cloud.Rnd.TableColumes();
						obj.setColumnname(column);
						obj.setDatatype("varchar(255)");
						obj.setConstraint("default NULL");
						tableColumn.add(obj);
				        }
				}*/
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("org_name");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("designation");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("state");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("map_emp_id");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("gender");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
			
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("user_name");
				obj.setDatatype("varchar(255)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("date");
				obj.setDatatype("varchar(15)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				obj= new com.Over2Cloud.Rnd.TableColumes();
				obj.setColumnname("time");
				obj.setDatatype("varchar(15)");
				obj.setConstraint("default NULL");
				tableColumn.add(obj);
				
				cbt.createTable22("referral_institutional_mapcontact", tableColumn, connectionSpace);
				
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				List paramList = new ArrayList<String>();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);

					if (paramValue != null && Parmname != null)
					{

						if (!Parmname.equalsIgnoreCase("status") && !Parmname.equalsIgnoreCase("date") && !Parmname.equalsIgnoreCase("time") && !Parmname.equalsIgnoreCase("indexVal") && !Parmname.equalsIgnoreCase("org_name"))
						{ 
				    		paramList.add(Parmname);
								fieldNameList.add(Parmname);
								ArrayList<String> list = new ArrayList<String>();
								String tempParamValueSize[] = request.getParameterValues(Parmname);
								for (int i = 0; i < indexVal; i++)
								{
									
								//	System.out.println(tempParamValueSize[i].trim());
									list.add(tempParamValueSize[i].trim());
								}
								mainList.add(list);
						 }
					}
				}
			
				for (int i = 0; i < mainList.get(0).size(); i++)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					for (int j = 0; j < fieldNameList.size(); j++)
					{
						String s = mainList.get(j).get(i).toString();
						if (!fieldNameList.get(j).toString().equalsIgnoreCase("indexVal"))
						{
							if (!fieldNameList.get(j).toString().equalsIgnoreCase("gender") && !fieldNameList.get(j).toString().equalsIgnoreCase("org_name") && !fieldNameList.get(j).toString().equalsIgnoreCase("designation") && !fieldNameList.get(j).toString().equalsIgnoreCase("state"))
							{
								if(fieldNameList.get(j).toString().equalsIgnoreCase("department"))
								{
									String deptname=this.getDepartment(request.getParameter("department"));
									System.out.println(deptname);
									if(deptname==null)
									deptname=this.getDepartment("Referral");
									ob = new InsertDataTable();
									ob.setColName("sub_type_id");
									ob.setDataName(deptname);
									insertAssociateEmpBasic.add(ob);
								}	
								else{
									ob = new InsertDataTable();
									ob.setColName(fieldNameList.get(j).toString());
									ob.setDataName(s);
									insertAssociateEmpBasic.add(ob);	
								}
								
						//		insertData.add(ob);
						//		System.out.println(fieldNameList.get(j).toString());
							} 
							else{
								ob = new InsertDataTable();
								ob.setColName(fieldNameList.get(j).toString());
								ob.setDataName(s);
								insertData.add(ob);
	 						}
    					}
					}
					
					ob = new InsertDataTable();
					ob.setColName("org_name");
					ob.setDataName(request.getParameter("org_name"));
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("designation");
					ob.setDataName(request.getParameter("designation"));
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					
					int maxid = cbt.insertDataReturnId("primary_contact", insertAssociateEmpBasic, connectionSpace);
					
					if(maxid!=0)
					{
						ob = new InsertDataTable();
						ob.setColName("map_emp_id");
						ob.setDataName(Integer.toString(maxid));
						insertData.add(ob);
						
						status = cbt.insertIntoTable("referral_institutional_mapcontact",insertData, connectionSpace);
					}
					
					
					insertAssociateEmpBasic.clear(); 
					//insertData.clear();
				}
				if (status)
				{
					addActionMessage("Referral Contact saved successfully");
					returnString = SUCCESS;
				} else
				{
					addActionMessage("Oops There is some error in data!");
					returnString = SUCCESS;
				}
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return returnString;
	}
	
	public String getDepartment(String deptname)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String str="Select id from contact_sub_type where contact_subtype_name='"+deptname+"'";
		System.out.println(str);
		List datalist=cbt.executeAllSelectQuery(str, connectionSpace);	
		String department=null;
		if(datalist!=null && datalist.size()>0)
		{
			department=datalist.get(0).toString();
		}
		return department;
	}
	
	
	private int fetchAsociateDept()
	{
		int clientDeptId = 0;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select id from contact_sub_type where contact_subtype_name = '");
			query.append("Referral");
			query.append("'");

			List subIndustryData = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
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
	
	private int fetchGroupName()
	{
		int clientDeptId = 0;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select id from contact_type where contact_name ='Referral'");
		
			

			List subIndustryData = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
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
	
	private void setGridProperties(String referralSubTpe)
	{
		GridDataPropertyView gpv=null;
		if(referralSubTpe.equalsIgnoreCase("Individual"))
		{
			String accountID = (String) session.get("accountid");
		    gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_referral_individual_data", accountID, connectionSpace, "", 0, "table_name", "referral_individual_data_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					gpv = new GridDataPropertyView();
					if (!gdp.getColomnName().equalsIgnoreCase("email_id") && !gdp.getColomnName().equalsIgnoreCase("dob") 
							&& !gdp.getColomnName().equalsIgnoreCase("doa") && !gdp.getColomnName().equalsIgnoreCase("gender") 
							&& !gdp.getColomnName().equalsIgnoreCase("dob") && !gdp.getColomnName().equalsIgnoreCase("country") 
							&& !gdp.getColomnName().equalsIgnoreCase("state") && !gdp.getColomnName().equalsIgnoreCase("address") 
							&& !gdp.getColomnName().equalsIgnoreCase("designation") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("action_by") 
							&& !gdp.getColomnName().equalsIgnoreCase("comments"))
					{
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						if(gdp.getColomnName().equalsIgnoreCase("emp_name"))
						{
							gpv.setFormatter("getIndi");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("status"))
						{
							gpv.setFormatter("getStatus1");
						}
						gpv.setSearch(gdp.getSearch());
						gpv.setSearch(gdp.getSearch());
						if(gdp.getColomnName().equalsIgnoreCase("action_on"))
						{
							gpv.setHideOrShow("true");	
						}
						else{
							gpv.setHideOrShow(gdp.getHideOrShow());
						}
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
			
			}
		else{
			
			String accountID = (String) session.get("accountid");
		    gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_referral_institutional_data", accountID, connectionSpace, "", 0, "table_name", "referral_institutional_data_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					gpv = new GridDataPropertyView();
					if (!gdp.getColomnName().equalsIgnoreCase("contact") && !gdp.getColomnName().equalsIgnoreCase("website") 
							&& !gdp.getColomnName().equalsIgnoreCase("address") && !gdp.getColomnName().equalsIgnoreCase("pin") 
							&& !gdp.getColomnName().equalsIgnoreCase("landmark") && !gdp.getColomnName().equalsIgnoreCase("country") 
							&& !gdp.getColomnName().equalsIgnoreCase("state") && !gdp.getColomnName().equalsIgnoreCase("fax") 
							&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("action_by") 
							&& !gdp.getColomnName().equalsIgnoreCase("comments"))
					{
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						if(gdp.getColomnName().equalsIgnoreCase("org_name"))
						{
							gpv.setFormatter("getOrg");
						}
						else if(gdp.getColomnName().equalsIgnoreCase("status"))
						{
							gpv.setFormatter("getStatus");
						}
						gpv.setSearch(gdp.getSearch());
						if(gdp.getColomnName().equalsIgnoreCase("action_on"))
						{
							gpv.setHideOrShow("true");	
						}
						else{
							gpv.setHideOrShow(gdp.getHideOrShow());
						}
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
			
			
			 gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewPropForContact.add(gpv);
				statusColName=null;
				statusColName = Configuration.getConfigurationData("mapped_referral_institutional_mapcontact", accountID, connectionSpace, "", 0, "table_name", "referral_institutional_mapcontact_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						gpv = new GridDataPropertyView();
						if (!gdp.getColomnName().equalsIgnoreCase("org_name") && !gdp.getColomnName().equalsIgnoreCase("category") 
								&& !gdp.getColomnName().equalsIgnoreCase("doa") && !gdp.getColomnName().equalsIgnoreCase("state") 
								&& !gdp.getColomnName().equalsIgnoreCase("department") && !gdp.getColomnName().equalsIgnoreCase("gender") 
								&& !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") 
								&& !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							gpv.setColomnName(gdp.getColomnName());
							gpv.setHeadingName(gdp.getHeadingName());
							gpv.setEditable(gdp.getEditable());
							if(gdp.getColomnName().equalsIgnoreCase("emp_name"))
							{
								gpv.setFormatter("getMapContact");
							}
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							gpv.setWidth(gdp.getWidth());
							masterViewPropForContact.add(gpv);
						}
					}
				}
     		}
		
     	}
	
	public String beforeStatusAction()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			statusmap = new LinkedHashMap<String, String>();

			sb.append(" select user_name,date from "+tabel+"");
			sb.append(" where id= '" + id + "'");
			
			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			//System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				statusmap.put("Requested By", getValueWithNullCheck(data[0]));
				statusmap.put("Requested On", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[1].toString())));
		    }
		} catch (Exception ex)
		{
		  ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String statusAction()
	{
		String result=null;
		if(id!=null && !id.equals("-1") && !id.equalsIgnoreCase("undefined"))
		{ 
		try
		{
			if(getAction_status()!=null && !getAction_status().equals("-1") && !getAction_status().equalsIgnoreCase("undefined"))
			{  
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				System.out.println("UPDATE "+tabel+" SET action_by='"+userName+"',status='"+this.getAction_status()+"',comments='"+this.getComments()+"',action_on='"+DateUtil.getCurrentDateUSFormat()+"' WHERE id='"+id+"'");
				int updatedPass=cbt.executeAllUpdateQuery("UPDATE "+tabel+" SET action_by='"+userName+"',status='"+this.getAction_status()+"',comments='"+this.getComments()+"',action_on='"+DateUtil.getCurrentDateUSFormat()+"' WHERE id='"+id+"'",connectionSpace);
				System.out.println(updatedPass);
				if(updatedPass!=0 && updatedPass>0)
				{
					addActionMessage("Action Taken successfully !!!");
					result= SUCCESS;
				}
				else
				{
					addActionMessage("Oops there is some database error occurs !!!!!");
					result= ERROR;
				}
			}
			else
			{
				addActionMessage("There is some database error occurs !!!!!");
				result= ERROR;
			}
		} catch (Exception ex)
		{
		  ex.printStackTrace();
		}
		
		}
		else
		{
			addActionMessage("There is some database error occurs !!!!!");
			result= ERROR;
		}
		return result;
		
	}
	
	
	
    @SuppressWarnings(
		{ "unchecked", "rawtypes" })
		public String viewModifyReferral()
		{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					if (getOper().equalsIgnoreCase("edit"))
					{
						CommonOperInterface cbt = new CommonConFactory()
								.createInterface();
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						List<String> requestParameterNames = Collections
								.list((Enumeration<String>) request
										.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String Parmname = it.next().toString();
							String paramValue = request.getParameter(Parmname);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& Parmname != null
									&& !Parmname.equalsIgnoreCase("")
									&& !Parmname.equalsIgnoreCase("id")
									&& !Parmname.equalsIgnoreCase("oper")
									&& !Parmname.equalsIgnoreCase("rowid"))
								if (Parmname.equalsIgnoreCase("date"))
								{
									wherClause.put(Parmname, DateUtil
											.convertDateToUSFormat(paramValue));
								}
								else
								{
									wherClause.put(Parmname, paramValue);
								}
						}
						condtnBlock.put("id", getId());
						cbt.updateTableColomn("referral_contact_data", wherClause,
								condtnBlock, connectionSpace);
					}
					else if (getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt = new CommonConFactory()
								.createInterface();
						String tempIds[] = getId().split(",");
						StringBuilder condtIds = new StringBuilder();
						int i = 1;
						for (String H : tempIds)
						{
							if (i < tempIds.length)
								condtIds.append(H + " ,");
							else
								condtIds.append(H);
							i++;
						}
						StringBuilder query = new StringBuilder();
						query.append("UPDATE referral_contact_data SET data_status='1' WHERE id IN("
								+ condtIds + ")");
						cbt.updateTableColomn(connectionSpace, query);
						query.setLength(0);

					}
					returnResult = SUCCESS;
				}
				catch (Exception exp)
				{
					exp.printStackTrace();
				}
			}
			else
			{
				returnResult = LOGIN;
			}
			return returnResult;
		}

    @SuppressWarnings(
			{ "unchecked", "rawtypes" })
			public String viewModify()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
				{
					try
					{
						if (getOper().equalsIgnoreCase("edit"))
						{
							CommonOperInterface cbt = new CommonConFactory()
									.createInterface();
							Map<String, Object> wherClause = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							List<String> requestParameterNames = Collections
									.list((Enumeration<String>) request
											.getParameterNames());
							Collections.sort(requestParameterNames);
							Iterator it = requestParameterNames.iterator();
							while (it.hasNext())
							{
								String Parmname = it.next().toString();
								String paramValue = request.getParameter(Parmname);
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")
										&& Parmname != null
										&& !Parmname.equalsIgnoreCase("")
										&& !Parmname.equalsIgnoreCase("id")
										&& !Parmname.equalsIgnoreCase("oper")
										&& !Parmname.equalsIgnoreCase("rowid"))
									if (Parmname.equalsIgnoreCase("date"))
									{
										wherClause.put(Parmname, DateUtil
												.convertDateToUSFormat(paramValue));
									}
									else
									{
										wherClause.put(Parmname, paramValue);
									}
							}
							condtnBlock.put("id", getId());
							cbt.updateTableColomn("referral_institutional_data", wherClause,
									condtnBlock, connectionSpace);
						}
						else if (getOper().equalsIgnoreCase("del"))
						{
							CommonOperInterface cbt = new CommonConFactory()
									.createInterface();
							String tempIds[] = getId().split(",");
							StringBuilder condtIds = new StringBuilder();
							int i = 1;
							for (String H : tempIds)
							{
								if (i < tempIds.length)
									condtIds.append(H + " ,");
								else
									condtIds.append(H);
								i++;
							}
							StringBuilder query = new StringBuilder();
							query.append("UPDATE referral_institutional_data SET data_status='1' WHERE id IN("
									+ condtIds + ")");
							cbt.updateTableColomn(connectionSpace, query);
							query.setLength(0);

						}
						returnResult = SUCCESS;
					}
					catch (Exception exp)
					{
						exp.printStackTrace();
					}
				}
				else
				{
					returnResult = LOGIN;
				}
				return returnResult;
			}

    
	
	private String getValueWithNullCheck(Object obj)
	{
		return( obj!=null && !obj.toString().equalsIgnoreCase(""))?obj.toString():"NA";
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	

	public void setCommonJsonArr(JSONArray commonJsonArr)
	{
		this.commonJsonArr = commonJsonArr;
	}

	public JSONArray getCommonJsonArr()
	{
		return commonJsonArr;
	}

	public void setCommonAddr(String commonAddr)
	{
		CommonAddr = commonAddr;
	}

	public String getCommonAddr()
	{
		return CommonAddr;
	}

	public void setCommonList(List<?> commonList)
	{
		this.commonList = commonList;
	}

	public List<?> getCommonList()
	{
		return commonList;
	}

	

	public Map getCountryMap()
	{
		return countryMap;
	}

	public void setCountryMap(Map countryMap)
	{
		this.countryMap = countryMap;
	}

	public Map getSourceMap()
	{
		return sourceMap;
	}

	public void setSourceMap(Map sourceMap)
	{
		this.sourceMap = sourceMap;
	}

	public Map getAccMgrMap()
	{
		return accMgrMap;
	}

	public void setAccMgrMap(Map accMgrMap)
	{
		this.accMgrMap = accMgrMap;
	}

	public void setMasterViewPropForContact(List<GridDataPropertyView> masterViewPropForContact) {
		this.masterViewPropForContact = masterViewPropForContact;
	}

	public List<GridDataPropertyView> getMasterViewPropForContact() {
		return masterViewPropForContact;
	}

	public void setIndexVal(int indexVal) {
		this.indexVal = indexVal;
	}

	public int getIndexVal() {
		return indexVal;
	}

	public void setReferralSubType(String referralSubType) {
		this.referralSubType = referralSubType;
	}

	public String getReferralSubType() {
		return referralSubType;
	}

	public void setReferralStatus(String referralStatus) {
		this.referralStatus = referralStatus;
	}

	public String getReferralStatus() {
		return referralStatus;
	}

	public void setReferralDropDown(List<ConfigurationUtilBean> referralDropDown) {
		this.referralDropDown = referralDropDown;
	}

	public List<ConfigurationUtilBean> getReferralDropDown() {
		return referralDropDown;
	}

	public void setReferralColumnMap(List<ConfigurationUtilBean> referralColumnMap) {
		this.referralColumnMap = referralColumnMap;
	}

	public List<ConfigurationUtilBean> getReferralColumnMap() {
		return referralColumnMap;
	}

	public void setReferralCalender(List<ConfigurationUtilBean> referralCalender) {
		this.referralCalender = referralCalender;
	}

	public List<ConfigurationUtilBean> getReferralCalender() {
		return referralCalender;
	}

	public void setReferralContactColumnMap(List<ConfigurationUtilBean> referralContactColumnMap) {
		this.referralContactColumnMap = referralContactColumnMap;
	}

	public List<ConfigurationUtilBean> getReferralContactColumnMap() {
		return referralContactColumnMap;
	}

	public void setReferralContactDropDown(List<ConfigurationUtilBean> referralContactDropDown) {
		this.referralContactDropDown = referralContactDropDown;
	}

	public List<ConfigurationUtilBean> getReferralContactDropDown() {
		return referralContactDropDown;
	}

	public void setReferralContactCalender(List<ConfigurationUtilBean> referralContactCalender) {
		this.referralContactCalender = referralContactCalender;
	}

	public List<ConfigurationUtilBean> getReferralContactCalender() {
		return referralContactCalender;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getFrom_date1() {
		return from_date1;
	}

	public void setFrom_date1(String from_date1) {
		this.from_date1 = from_date1;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getTodate1() {
		return todate1;
	}

	public void setTodate1(String todate1) {
		this.todate1 = todate1;
	}

	public String getFrom_source() {
		return from_source;
	}

	public void setFrom_source(String from_source) {
		this.from_source = from_source;
	}

	public String getFrom_source1() {
		return from_source1;
	}

	public void setFrom_source1(String from_source1) {
		this.from_source1 = from_source1;
	}

	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}

	public String getSearchParameter() {
		return searchParameter;
	}

	public void setSearchParameter1(String searchParameter1) {
		this.searchParameter1 = searchParameter1;
	}

	public String getSearchParameter1() {
		return searchParameter1;
	}

	public void setStatusmap(Map<String, String> statusmap) {
		this.statusmap = statusmap;
	}

	public Map<String, String> getStatusmap() {
		return statusmap;
	}

	public void setAction_status(String action_status) {
		this.action_status = action_status;
	}

	public String getAction_status() {
		return action_status;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getComments() {
		return comments;
	}

	public void setTabel(String tabel) {
		this.tabel = tabel;
	}

	public String getTabel() {
		return tabel;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setData_status(String data_status) {
		this.data_status = data_status;
	}

	public String getData_status() {
		return data_status;
	}
	
	

}