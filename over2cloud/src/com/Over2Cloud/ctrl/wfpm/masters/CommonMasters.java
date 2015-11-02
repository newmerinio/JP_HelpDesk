package com.Over2Cloud.ctrl.wfpm.masters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.components.ActionMessage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.LostOpportunityType;
import com.Over2Cloud.ctrl.wfpm.template.FeedbackConfigBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CommonMasters extends ActionSupport implements ServletRequestAware
{
	Map																	session														= ActionContext.getContext().getSession();
	String															userName													= (String) session.get("uName");
	String															accountID													= (String) session.get("accountid");
	SessionFactory											connectionSpace										= (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest					request;
	private List<ConfigurationUtilBean>	associateStatusMasterTextBox			= null;
	private String											mainHeaderName;
	private List<GridDataPropertyView>	masterViewProp										= null;
	private Integer											rows															= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer											page															= 0;
	// sorting order - asc or desc
	private String											sord															= "";
	// get index row - i.e. user click to sort.
	private String											sidx															= "";
	// Search Field
	private String											searchField												= "";
	// The Search String
	private String											searchString											= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String											searchOper												= "";
	// Your Total Pages
	private Integer											total															= 0;
	// All Record
	private Integer											records														= 0;
	private boolean											loadonce													= false;
	// Grid colomn view
	private String											oper;
	private String											id;
	private List<Object>								masterViewList;

	// common variable related to the module name, mapped table , map data
	// table, data table
	private String											mappingTable;
	private String											mapSubTable;
	private String											moduleName;
	private String											dataTable;
	private Map<String, String>					associateCatList									= new HashMap<String, String>();
	private String											offeringLevelId										= null;
	private int													subTypeExists											= 0;
	private String											moduleName1												= null;
	private Map<String, String>					associateTypeList									= null;
	private JSONArray										jsonArray													= null;
	private boolean											isAssociateSubTypeExists					= false;
	private List<GridDataPropertyView>	masterViewPropForAssociateSubType	= null;
	private String											mainHeaderNameForAssociateSubType	= null;
	private List<Object>								masterViewListForAssociateSubType	= null;
	private boolean											categoryBlock											= false;
	private Map<String, String>					stateNameList											= null;
	private boolean											stateNameExists										= false;
	private String											offeringDivId											= null;

	/**
	 * This variable is taken for feedback configuration
	 */
	private List<Integer>								daysAfter;
	private String											after;
	private String											time;
	private String											keyword;
	private String											length;
	private List<FeedbackConfigBean>		feedbackDetail										= null;

	private JSONObject									keywordJSONObject;
	public static final String					DES_ENCRYPTION_KEY								= "ankitsin";
	private String											searchType;
	private String											type,status;
	private boolean											lostOpportunityMandatory;
	private String											lostOpportunityTypeName;
	private String											lostOpportunityTypeHeading;
	private String	mainHeaderNameForAssociateType;
	private Integer countSource=0;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeAssociateStatusAdd()
	{
		//System.out.println("11111111111111111111111111111111");
		try
		{
		  if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			  setModuleName(getModuleName());
			  setMappingTable(getMappingTable());
			  setMapSubTable(getMapSubTable());
			  setDataTable(getDataTable());
			  setAddPageDataFields();
			// //System.out.println(getModuleName());
			  setMainHeaderName(getModuleName());
			//System.out.println("getMainHeaderName():" + getMainHeaderName());
			//System.out.println("getModuleName():" + getModuleName());
            //System.out.println("module name==============="+getModuleName());
            //System.out.println("mapping table=============="+getMappingTable());
            //System.out.println("mapping sub table==========="+getMapSubTable());
            //System.out.println("data table=================="+getDataTable());
             
			  
			 CommonOperInterface coi = new CommonConFactory().createInterface();

			// Get level of offering
			String offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				offeringLevelId = oLevels[0].trim();
			}

			// Lost opportunity
			/*
			 * if (getMappingTable() != null &&
			 * getMappingTable().equalsIgnoreCase("mapped_lost_master")) {
			 * lostOpportunityType = true; List<GridDataPropertyView> offeringLevel1 =
			 * Configuration.getConfigurationData("mapped_client_configuration",
			 * accountID, connectionSpace, "", 0, "table_name",
			 * "client_basic_configuration"); clientTextBox = new
			 * ArrayList<ConfigurationUtilBean>(); clientDropDown = new
			 * ArrayList<ConfigurationUtilBean>(); for (GridDataPropertyView gdp :
			 * offeringLevel1) { ConfigurationUtilBean obj = new
			 * ConfigurationUtilBean(); if
			 * (gdp.getColType().trim().equalsIgnoreCase("T") &&
			 * !gdp.getColomnName().equalsIgnoreCase("userName") &&
			 * !gdp.getColomnName().equalsIgnoreCase("date") &&
			 * !gdp.getColomnName().equalsIgnoreCase("time") &&
			 * !gdp.getColomnName().equalsIgnoreCase("weightage")) {
			 * obj.setValue(gdp.getHeadingName()); obj.setKey(gdp.getColomnName());
			 * obj.setValidationType(gdp.getValidationType());
			 * obj.setColType(gdp.getColType()); if
			 * (gdp.getMandatroy().toString().equals("1")) { obj.setMandatory(true); }
			 * else { obj.setMandatory(false); } clientTextBox.add(obj); } } }
			 */

			// Get associate categories
			if (getMappingTable() != null && getMappingTable().equalsIgnoreCase("mapped_associate_category_master"))
			{
				categoryBlock = true;

				// offering column name
				offeringDivId = "";
				if (offeringLevelId.equalsIgnoreCase("1"))
				{
					offeringDivId = "verticalname";
				}
				else if (offeringLevelId.equalsIgnoreCase("2"))
				{
					offeringDivId = "offeringname";
				}
				else if (offeringLevelId.equalsIgnoreCase("3"))
				{
					offeringDivId = "subofferingname";
				}
				else if (offeringLevelId.equalsIgnoreCase("4"))
				{
					offeringDivId = "variantname";
				}
				else if (offeringLevelId.equalsIgnoreCase("5"))
				{
					offeringDivId = "subvariantname";
				}

				String query = "select id, associate_category from associatecategory";
				List data = coi.executeAllSelectQuery(query, connectionSpace);

				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						for (int k = 0; k <= 1; k++)
						{
							if (obdata != null)
							{
								associateCatList.put(obdata[0].toString(), obdata[1].toString() == null ? "NA" : obdata[1].toString());
							}
						}
					}
				}
			}

			// Associate sub type
			if (getMappingTable() != null && getMappingTable().equalsIgnoreCase("mapped_associate_type_master"))
			{
				StringBuilder query1 = new StringBuilder();
				query1.append("select orgLevel, levelName from mapped_associatetype_level_config limit 1");
				List list1 = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (list1 != null && list1.size() > 0)
				{
					Object[] obj = (Object[]) list1.get(0);
					if (obj != null)
					{
						subTypeExists = Integer.parseInt(obj[0].toString());
						String[] str = obj[1].toString().split("#");
						setModuleName(str[0] == null ? "NA" : str[0]);
						setModuleName1(str[1] == null ? "NA" : str[1]);
					}
				}
			}

			// location
			stateNameList = new HashMap<String, String>();
			if (getMappingTable() != null && getMappingTable().equalsIgnoreCase("mapped_location_master"))
			{
				StringBuilder query1 = new StringBuilder();
				stateNameExists = true;
				query1.append("select id, stateName from state_data");
				List list1 = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (list1 != null && list1.size() > 0)
				{
					for (Object data : list1)
					{
						Object[] obj = (Object[]) data;
						if (obj != null)
						{
							stateNameList.put(obj[0].toString(), obj[1].toString() == null ? "NA" : obj[1].toString());
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataFields()
	{
		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData(getMappingTable(), accountID, connectionSpace, "", 0, "table_name",
				getMapSubTable());
		//System.out.println(getMappingTable() + "=========" + getMapSubTable() +
		  //               "=============" + offeringLevel1.size());
		associateStatusMasterTextBox = new ArrayList<ConfigurationUtilBean>();
		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
			{      //this is for textfield 
				if (gdp.getColType().trim().equalsIgnoreCase("T"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
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
					associateStatusMasterTextBox.add(obj);
					// associateStatusMasterTextBox.put(gdp.getColomnName(),
					// gdp.getHeadingName());
				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && gdp.getColomnName().equalsIgnoreCase("lostOpportunityFor"))
				{
					lostOpportunityTypeHeading = gdp.getHeadingName();
					lostOpportunityTypeName = gdp.getColomnName();
					if (gdp.getMandatroy().toString().equals("1"))
					{
						lostOpportunityMandatory = true;
					}
				}

			}
		}
		// //System.out.println("associateStatusMasterTextBox.size():" +
		// associateStatusMasterTextBox.size());
	}

	public String associateStatusAddMaster()
	{
		//System.out.println("7777777777777777777777777777777777");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String isEmail = "", module = "";
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(getMappingTable(), accountID, connectionSpace, "", 0, "table_name",
					getMapSubTable());
			if (statusColName != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : statusColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				cbt.createTable22(getDataTable(), Tablecolumesaaa, connectionSpace);

				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("mappingTable")
							&& !Parmname.equalsIgnoreCase("mapSubTable") && !Parmname.equalsIgnoreCase("moduleName") && !Parmname.equalsIgnoreCase("dataTable"))
					{
						if (Parmname.equalsIgnoreCase("lostOpportunityFor"))
						{
							paramValue = String.valueOf(paramValue.equals("0") ? LostOpportunityType.LEAD.ordinal() : paramValue.equals("1") ? LostOpportunityType.CLIENT
									.ordinal() : LostOpportunityType.ASSOCIATE.ordinal());
						}

						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);

						if (Parmname.equalsIgnoreCase("server")) isEmail = paramValue;
						if (Parmname.equalsIgnoreCase("module")) module = paramValue;
					}
				}
				if (userTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
				}
				if (dateTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
				}
				if (timeTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}
				
				if(getDataTable().equalsIgnoreCase("sourcemaster") || getDataTable().equalsIgnoreCase("associatetype") || getDataTable().equalsIgnoreCase("associatecategory") || getDataTable().equalsIgnoreCase("client_status") || getDataTable().equalsIgnoreCase("weightagemaster") || getDataTable().equalsIgnoreCase("forcastcategarymaster") || getDataTable().equalsIgnoreCase("salesstagemaster") || getDataTable().equalsIgnoreCase("associatestatus") || getDataTable().equalsIgnoreCase("lostoportunity") || getDataTable().equalsIgnoreCase("location"))
			    {
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
				}
				
				if (!isEmail.equalsIgnoreCase("") && !module.equalsIgnoreCase(""))
				{
					List list = cbt.executeAllSelectQuery("select id from email_configuration_data where module = '" + module + "' ", connectionSpace);
					boolean isUpdated = false;
					if (list != null && list.size() > 0)
					{
						String id = list.get(0).toString();
						cbt.deleteAllRecordForId("email_configuration_data", "id", id, connectionSpace);
						isUpdated = true;
					}
					if (insertData.size() > 3) // for case when at least one
					// data
					// will be in text box
					status = cbt.insertIntoTable(getDataTable(), insertData, connectionSpace);
					if (status && isUpdated)
					{
						addActionMessage("Configuration Updated Successfully.");
						return SUCCESS;
					}
					if (status && !isUpdated)
					{
						addActionMessage("Configuration Saved Successfully!!!");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
				else
				{
					if (insertData.size() > 3) // for case when atleast one data
					// will be in text box
					status = cbt.insertIntoTable(getDataTable(), insertData, connectionSpace);
					if (status)
					{
						addActionMessage("Data Registered Successfully!!!");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			}
			else
			{
				addActionMessage("Oops There is some error in data!");
				return SUCCESS;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeAssociateStatusView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			setModuleName(getModuleName());
			setMappingTable(getMappingTable());
			setMapSubTable(getMapSubTable());
			setDataTable(getDataTable());
			setMainHeaderName(getModuleName());
			//System.out.println("module name in beforeAssociateStatusView=========="+getModuleName());
			setAssociateMasterViewProp();
			
			//count records  (by Azad 8July 2014)
			 String query1=null;
			 if(getModuleName().equalsIgnoreCase("Source"))
			 {
			  query1="select count(*) from sourcemaster";
			  countSource=countRecord(query1);
			
			 }
			 if(getModuleName().equalsIgnoreCase("Associate Type"))
			 {
				 query1="select count(*) from associatetype";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Client Status"))
			 {
				 query1="select count(*) from client_status";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Associate Status"))
			 {
				 query1="select count(*) from associatestatus";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Lost Opportunity"))
			 {
				 query1="select count(*) from lostoportunity";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Associate Category"))
			 {
				 query1="select count(*) from associatecategory";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Weightage"))
			 {
				 query1="select count(*) from weightagemaster";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Location"))
			 {
				 query1="select count(*) from location";
				  countSource=countRecord(query1);
			 }
			 if(getModuleName().equalsIgnoreCase("Forcast Categary"))
			 {
				 query1="select count(*) from forcastcategarymaster";
				  countSource=countRecord(query1);
			 }if(getModuleName().equalsIgnoreCase("Sales Stage"))
			 {
				 query1="select count(*) from salesstagemaster";
				  countSource=countRecord(query1);
			 }

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	//function of count record (by Azad 7july 2014)
	public Integer countRecord(String query)
	{ 	  BigInteger totalRecord=new BigInteger("3");
	    List listData=null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		listData=cbt.executeAllSelectQuery(query,connectionSpace);
	          if(listData!=null)
	          {
	        	  for(Iterator iterator=listData.iterator(); iterator.hasNext();)
	        	  {
	        	
	        		  totalRecord=(BigInteger) iterator.next();
	        	  }
	          }
		
		
		return totalRecord.intValue();
	}
	public void setAssociateMasterViewProp()
	{

		masterViewProp = new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(getMappingTable(), accountID, connectionSpace, "", 0, "table_name",
				getMapSubTable());
		if (returnResult != null)
		{
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				/*
				 * if (gdp.getColomnName().equalsIgnoreCase("associate_category")) gpv
				 * .setFormatter("associateHyperlink");
				 */
				//hide the state column in grid show for location master
				if(!gdp.getColomnName().equalsIgnoreCase("stateName"))
				{
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setAlign(gdp.getAlign());
				gpv.setWidth(gdp.getWidth());
				masterViewProp.add(gpv);
				}
				
			}
		}

		// for associate sub type
		if (getMappingTable() != null && getMappingTable().equalsIgnoreCase("mapped_associate_type_master"))
		{
			isAssociateSubTypeExists = true;

			// Start: Fetch associate type & sub type level
			StringBuilder query1 = new StringBuilder();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			query1.append("select orgLevel, levelName from mapped_associatetype_level_config limit 1");
			List list1 = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
			if (list1 != null && list1.size() > 0)
			{
				Object[] obj = (Object[]) list1.get(0);
				if (obj != null)
				{
					subTypeExists = Integer.parseInt(obj[0].toString());
					String[] str = obj[1].toString().split("#");
					// setModuleName(str[0] == null ? "NA" : str[0]);
					setMainHeaderNameForAssociateSubType(str[1] == null ? "NA" : str[1]);
					mainHeaderNameForAssociateType = CommonHelper.getFieldValue(str[0]);
				}
			}
			// End: Fetch associate type & sub type level

			masterViewPropForAssociateSubType = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv1 = new GridDataPropertyView();
			gpv1.setColomnName("id");
			gpv1.setHeadingName("Id");
			gpv1.setHideOrShow("true");
			masterViewPropForAssociateSubType.add(gpv1);

			List<GridDataPropertyView> returnResult1 = Configuration.getConfigurationData("mapped_associate_subtype", accountID, connectionSpace, "", 0,
					"table_name", "associate_subtype_master");
			if (returnResult1 != null)
			{
				for (GridDataPropertyView gdp : returnResult1)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("associateType"))
					{
						gpv1 = new GridDataPropertyView();
						gpv1.setColomnName(gdp.getColomnName());
						gpv1.setHeadingName(gdp.getHeadingName());
						gpv1.setEditable(gdp.getEditable());
						gpv1.setSearch(gdp.getSearch());
						gpv1.setHideOrShow(gdp.getHideOrShow());
						gpv1.setWidth(gdp.getWidth());
						masterViewPropForAssociateSubType.add(gpv1);
					}
				}
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	public String viewAssociateGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from " + getDataTable());
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList(getMappingTable(), accountID, connectionSpace, getMapSubTable());
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;

				if (getMappingTable().equals("mapped_location_master"))
				{

					StringBuilder oneQuery = new StringBuilder();
					// oneQuery.append(" select ");
					StringBuilder twoQuery = new StringBuilder();
					twoQuery.append(" from ");
					twoQuery.append(dataTable);
					System.out.println("dataTable "+dataTable);
					twoQuery.append(" as ldm ");

					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (obdata.toString().equalsIgnoreCase("stateName"))
								{
									oneQuery.append(" sd.stateName, ");
									twoQuery.append(" left join state_data as sd on sd.id = ldm.stateName ");
								}
								else
								{
									oneQuery.append(" ldm." + obdata.toString() + ",");
								}
							}
							else
							{
								if (obdata.toString().equalsIgnoreCase("stateName"))
								{
									oneQuery.append(" sd.stateName ");
									twoQuery.append(" left join state_data as sd on sd.id = ldm.stateName ");
								}
								else
								{
									oneQuery.append(" ldm." + obdata.toString());
								}
							}
						}
						i++;

					}

					query.append(oneQuery + " " + twoQuery);

					if(this.getStatus()!=null && !this.getStatus().equals("-1") &&  !this.getStatus().equalsIgnoreCase("undefined"))
				    {
						  query.append(" where ldm.status='"+getStatus()+"'");
					}	
					
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");
						if (getSearchField().equalsIgnoreCase("stateName"))
						{
							query.append("sd.");
						}
						else
						{
							query.append("ldm.");
						}

						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(getSearchField() + " like '%" + getSearchString() + "'");
						}

					}

					if (getSord() != null && !getSord().equalsIgnoreCase("") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by ldm." + getSidx() + " " + getSord());
					}
					else
					{
						query.append(" order by ldm.name ASC");
					}

					query.append(" limit " + from + "," + to);

				}
				else
				// ////////////////////////////////////
				{
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					    {
						   // generating the dyanamic query based on selected
						                                                    // fields
						  Object obdata = (Object) it.next();
						  if (obdata != null)
						     {
							    if (i < fieldNames.size() - 1) query.append(obdata.toString() + ",");
							    else query.append(obdata.toString());
						     }
						     i++;

					    }

					    query.append(" from " + getDataTable());
					    if(getDataTable().equalsIgnoreCase("sourcemaster") || getDataTable().equalsIgnoreCase("associatetype") || getDataTable().equalsIgnoreCase("associatecategory") || getDataTable().equalsIgnoreCase("client_status") || getDataTable().equalsIgnoreCase("weightagemaster") || getDataTable().equalsIgnoreCase("forcastcategarymaster") || getDataTable().equalsIgnoreCase("salesstagemaster") || getDataTable().equalsIgnoreCase("associatestatus") || getDataTable().equalsIgnoreCase("lostoportunity") || getDataTable().equalsIgnoreCase("location"))
					    {
					    	if(this.getStatus()!=null && !this.getStatus().equals("-1") &&  !this.getStatus().equalsIgnoreCase("undefined"))
						    {
								  query.append(" where status='"+getStatus()+"'");
							}	
					    }
					  if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					   {
						   // add search query based on the search operation
						  query.append(" and ");
						  if (getSearchOper().equalsIgnoreCase("eq"))
						     {
							  query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						     }
						  else if (getSearchOper().equalsIgnoreCase("cn"))
						  {
							  query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						  }
						  else if (getSearchOper().equalsIgnoreCase("bw"))
						  {
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						  }
						  else if (getSearchOper().equalsIgnoreCase("ne"))
						  {
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						  }
						  else if (getSearchOper().equalsIgnoreCase("ew"))
						  {
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						  }

					}

					// //System.out.println(getSord() + "<<<<<<<<<<>>>>>>>>>>>>>" +
					// getSidx());
					if (getSord() != null && !getSord().equalsIgnoreCase("") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by " + getSidx() + " " + getSord());
					}
					else
					{
						// //System.out.println("elseeeeeeeeeeee:" + getDataTable());
						if (getDataTable().equalsIgnoreCase("lostoportunity"))
						{
							query.append(" order by lostReason ASC");
						}
						else if (getDataTable().equalsIgnoreCase("client_status"))
						{
							query.append(" order by statusCode ASC");
						}
						else if (getDataTable().equalsIgnoreCase("associatestatus"))
						{
							query.append(" order by statuscode ASC");
						}
						else if (getDataTable().equalsIgnoreCase("associatetype"))
						{
							query.append(" order by associateType ASC");
						}
						else if (getDataTable().equalsIgnoreCase("associatecategory"))
						{
							query.append(" order by associate_category ASC");
						}
						else if (getDataTable().equalsIgnoreCase("sourcemaster"))
						{
							query.append(" order by sourceCode ASC");
						}
					}

					   query.append(" limit " + from + "," + to);
					 System.out.println("QQQQQ:" + query);
				}

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, getDataTable(), connectionSpace);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0) 
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								else if (fieldNames.get(k).toString().equalsIgnoreCase("lostOpportunityFor"))
									     one.put(fieldNames.get(k).toString(), String.valueOf(obdata[k]
										                                 .toString().equals("0") ? LostOpportunityType.LEAD : obdata[k].toString().equals("1") ? LostOpportunityType.CLIENT
										                                                                                    : LostOpportunityType.ASSOCIATE));
							    else one.put(fieldNames.get(k).toString(), obdata[k].toString());
								
								if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
								{
									one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								if (fieldNames.get(k).toString().equalsIgnoreCase("time"))
								{
									one.put(fieldNames.get(k).toString(), obdata[k].toString().substring(0, 5));
								}
							}
						}
						Listhb.add(one);
					}

					// Collections.reverse(Listhb);

					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
}

	
	
	
	public String viewModifyAssociate()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid") && !Parmname.equalsIgnoreCase("mappingTable")
							&& !Parmname.equalsIgnoreCase("mapSubTable") && !Parmname.equalsIgnoreCase("moduleName") && !Parmname.equalsIgnoreCase("dataTable")) wherClause
							.put(Parmname, paramValue);
				}

				wherClause.put("userName", userName);
				wherClause.put("date", DateUtil.getCurrentDateUSFormat());
				wherClause.put("time", DateUtil.getCurrentTime());

				condtnBlock.put("id", getId());
				cbt.updateTableColomn(getDataTable(), wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				// //System.out.println("ID:::::" + getId());
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				if(getDataTable().equalsIgnoreCase("sourcemaster") || getDataTable().equalsIgnoreCase("associatetype") || getDataTable().equalsIgnoreCase("associatecategory") || getDataTable().equalsIgnoreCase("client_status") || getDataTable().equalsIgnoreCase("weightagemaster") || getDataTable().equalsIgnoreCase("forcastcategarymaster") || getDataTable().equalsIgnoreCase("salesstagemaster") || getDataTable().equalsIgnoreCase("associatestatus") || getDataTable().equalsIgnoreCase("lostoportunity") || getDataTable().equalsIgnoreCase("location"))
			    {
			    	wherClause.put("status","Inactive");
					condtnBlock.put("id", condtIds.toString());
					cbt.updateTableColomn(getDataTable(), wherClause, condtnBlock, connectionSpace);
			    }
				else
				{
     				cbt.deleteAllRecordForId(getDataTable(), "id", condtIds.toString(), connectionSpace);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 11-7-2013 Map associate category with cost
	 */

	public String associateCategoryCostMapping()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface coi = new CommonConFactory().createInterface();

			// Start: create table 'associate_category_cost_mapping'
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();

			ob1.setColumnname("offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("offeringLevelId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("associateCategoryId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("price");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			coi.createTable22("associate_category_cost_mapping", tableColumn, connectionSpace);
			// End: create table 'associate_category_cost_mapping'

			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			InsertDataTable ob = null;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			boolean status = false;
			String tempOfferingId = null;
			String tempAssociateCategoryId = null;

			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1"))
				{
					if (Parmname.equalsIgnoreCase("offeringId") || Parmname.equalsIgnoreCase("variantname") || Parmname.equalsIgnoreCase("subofferingname")
							|| Parmname.equalsIgnoreCase("offeringname") || Parmname.equalsIgnoreCase("verticalname"))
					{
						tempOfferingId = paramValue;
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
					else
					{
						if (Parmname.equalsIgnoreCase("associateCategoryId")) tempAssociateCategoryId = paramValue;

						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
				}
			}

			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(userName);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			// fetch record from table 'associate_category_cost_mapping' to
			// check for update
			Map<String, Object> condList = new HashMap<String, Object>();
			condList.put("offeringId", tempOfferingId);
			condList.put("offeringLevelId", offeringLevelId);
			condList.put("associateCategoryId", tempAssociateCategoryId);

			List<String> colList = new ArrayList<String>();
			colList.add("id");

			List data = coi.viewAllDataEitherSelectOrAll("associate_category_cost_mapping", colList, condList, connectionSpace);
			if (data != null && data.size() > 0)// offeringId & offeringLevelId
			// & associateCategoryId exists,
			// update record in table
			// 'associate_category_cost_mapping'
			{
				Object obj = data.get(0);
				Map<String, Object> wherClause = new HashMap<String, Object>();
				for (InsertDataTable idt : insertData)
				{
					wherClause.put(idt.getColName(), idt.getDataName());
				}

				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				condtnBlock.put("id", obj.toString());
				status = coi.updateTableColomn("associate_category_cost_mapping", wherClause, condtnBlock, connectionSpace);
				if (status) addActionMessage("Offering updated successfully.");
				else addActionMessage("Error in updating mapping !");
			}
			else
			// insert new record to table 'associate_category_cost_mapping'
			{
				status = coi.insertIntoTable("associate_category_cost_mapping", insertData, connectionSpace);
				if (status) addActionMessage("Offering mapped successfully.");
				else addActionMessage("Error in mapping !");
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 13-7-2013 Set grid page for associate category and cost mapping
	 */
	public String beforeFetchAssociateCatCost()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			setMainHeaderName("Associate Category Cost Mapping");

			// Get level of offering
			String offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;
			int level = 0;
			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0].trim());
			}

			// Set table name to fetch offering from
			String tableName = "", colName = "";
			if (level == 1)
			{
				tableName = "offeringlevel1";
				colName = "verticalname";
			}
			if (level == 2)
			{
				tableName = "offeringlevel2";
				colName = "offeringname";
			}
			if (level == 3)
			{
				tableName = "offeringlevel3";
				colName = "subofferingname";
			}
			if (level == 4)
			{
				tableName = "offeringlevel4";
				colName = "variantname";
			}
			if (level == 5)
			{
				tableName = "offeringlevel5";
				colName = "subvariantname";
			}

			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("associate_category");
			gpv.setHeadingName("Category");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName(colName);
			gpv.setHeadingName("Offering");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("price");
			gpv.setHeadingName("Price");
			gpv.setEditable("false");
			gpv.setSearch("true");
			gpv.setHideOrShow("false");
			masterViewProp.add(gpv);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 13-7-2013 Fetch ssociate category and cost mapping
	 */

	public String fetchAssociateCatCost()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			// Get level of offering
			String offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;
			int level = 0;
			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0].trim());
			}

			// Set table name to fetch offering from
			String tableName = "", colName = "";
			if (level == 1)
			{
				tableName = "offeringlevel1";
				colName = "verticalname";
			}
			if (level == 2)
			{
				tableName = "offeringlevel2";
				colName = "offeringname";
			}
			if (level == 3)
			{
				tableName = "offeringlevel3";
				colName = "subofferingname";
			}
			if (level == 4)
			{
				tableName = "offeringlevel4";
				colName = "variantname";
			}
			if (level == 5)
			{
				tableName = "offeringlevel5";
				colName = "subvariantname";
			}

			// Fetch data from table 'associate_category_cost_mapping',
			// 'associatecategory', <offeringTable>
			StringBuffer query = new StringBuffer();
			query.append("select accm.id, ac.associate_category, off.");
			query.append(colName);
			query.append(", accm.price from associate_category_cost_mapping as accm ");
			query.append("left join associatecategory as ac on ac.id = accm.associateCategoryId left join ");
			query.append(tableName);
			query.append(" as off on off.id = accm.offeringId ");
			query.append("where accm.associateCategoryId = ");
			query.append(id);
			query.append(" and accm.offeringLevelId = ");
			query.append(level);

			CommonOperInterface coi = new CommonConFactory().createInterface();
			List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			// //System.out.println("query.toString() >>>>>>>  " + query.toString());
			masterViewList = new ArrayList<Object>();
			if (data != null && data.size() > 0)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obj = (Object[]) it.next();
					Map<String, Object> one = new HashMap<String, Object>();
					if (obj != null)
					{
						for (int i = 0; i < obj.length; i++)
						{
							if (i == 0) one.put("id", Integer.parseInt(obj[0].toString()));
							else if (i == 1) one.put("associate_category", CommonHelper.getFieldValue(obj[1]));
							else if (i == 2) one.put(colName, CommonHelper.getFieldValue(obj[2]));
							else if (i == 3) one.put("price", CommonHelper.getFieldValue(obj[3]));
						}
					}
					masterViewList.add(one);// list to show in grid
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 13-7-2013 Edit associate category mapping with cost
	 */

	public String editAssociateCatCost()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			if (oper.equalsIgnoreCase("del"))
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				coi.deleteAllRecordForId("associate_category_cost_mapping", "id", id, connectionSpace);
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 26-7-2013 Fetch associate type
	 */
	public String fetchAssociateType()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			jsonArray = new JSONArray();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List list = coi.executeAllSelectQuery("select id, associateType  from associatetype", connectionSpace);
			if (list != null)
			{
				for (Object object : list)
				{
					Object[] obj = (Object[]) object;
					if (obj != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", obj[0].toString());
						formDetailsJson.put("NAME", obj[1].toString());
						jsonArray.add(formDetailsJson);
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 26-7-2013 Fetch associate sub type
	 */
	public String fetchAssociateSubType()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			jsonArray = new JSONArray();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			List list = coi.executeAllSelectQuery("select id, associateSubType  from associatesubtype where associateType = " + id, connectionSpace);
			if (list != null)
			{
				for (Object object : list)
				{
					Object[] obj = (Object[]) object;
					if (obj != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", obj[0].toString());
						formDetailsJson.put("NAME", obj[1].toString());
						jsonArray.add(formDetailsJson);
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 26-7-2013 Add associate sub type
	 */
	public String addAssociateSubType()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface coi = new CommonConFactory().createInterface();
			// create table 'associatetype'
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("associateType");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("associateSubType");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			coi.createTable22("associatesubtype", Tablecolumesaaa, connectionSpace);

			// end creating table 'associatetype'

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Iterator it = requestParameterNames.iterator();

			String associateName = null;
			List<String> paramList = new ArrayList<String>();
			while (it.hasNext())
			{
				String ParmName = it.next().toString();
				String paramValue = request.getParameter(ParmName);
				if (paramValue != null && !paramValue.equalsIgnoreCase(""))
				{
					ob = new InsertDataTable();
					ob.setColName(ParmName);
					ob.setDataName(paramValue);
					insertData.add(ob);
				}
			}

			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(userName);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			boolean status = coi.insertIntoTable("associatesubtype", insertData, connectionSpace);
			if (status)
			{
				addActionMessage("Data saved successfully !");
			}
			else
			{
				addActionMessage("Error saving data !");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 26-7-2013 Grid view of associate sub type
	 */
	public String viewAssociateSubTypeGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from associatesubtype where associateType = ");
			query1.append(id);

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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");

				List fieldNames = Configuration.getColomnList("mapped_associate_subtype", accountID, connectionSpace, "associate_subtype_master");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1)
						{
							if (!obdata.toString().equalsIgnoreCase("associateType"))
							{
								query.append(obdata.toString() + ",");
							}
						}
						else
						{
							if (!obdata.toString().equalsIgnoreCase("associateType"))
							{
								query.append(obdata.toString());
							}
						}
					}
					i++;
				}

				query.append(" from associatesubtype where associateType = " + id);

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{

					query.append(" and ");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by " + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by " + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				fieldNames.remove("associateType");// remove it from list
				// because we dont want to
				// show it in grid

				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < obdata.length; k++)
						{
							if (obdata[k] != null)
							{
								if (fieldNames.get(k).toString().equalsIgnoreCase("date")) one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k]
										.toString()));
								else one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
						Listhb.add(one);
					}

					setMasterViewListForAssociateSubType(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 26-7-2013 Grid view of associate sub type
	 */
	public String modifyAssociateSubTypeGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			if (oper.equalsIgnoreCase("del"))
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				coi.deleteAllRecordForId("associatesubtype", "id", id, connectionSpace);
			}
			else if (oper.equalsIgnoreCase("edit"))
			{

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")) if (Parmname
							.equalsIgnoreCase("userName"))
					{
						wherClause.put(Parmname, userName);
					}
					else if (Parmname.equalsIgnoreCase("date"))
					{
						wherClause.put(Parmname, DateUtil.getCurrentDateUSFormat());
					}
					else if (Parmname.equalsIgnoreCase("time"))
					{
						wherClause.put(Parmname, DateUtil.getCurrentTime());
					}
					else if (Parmname.equalsIgnoreCase("associateSubType"))
					{
						wherClause.put(Parmname, paramValue);
					}
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("associatesubtype", wherClause, condtnBlock, connectionSpace);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Feedback Configuration Code start here.
	 */
	public String beforeFeedbackConfgView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			// //System.out.println(">>>>>>>>>Inside beforeFeedbackConfgView : " +
			// mainHeaderName);
			setMainHeaderName(mainHeaderName);
			if (mainHeaderName.equalsIgnoreCase("Feedback Configuration"))
			{
				daysAfter = new ArrayList<Integer>();
				daysAfter.add(1);
				daysAfter.add(2);
				daysAfter.add(3);
				daysAfter.add(4);
				daysAfter.add(5);
				daysAfter.add(6);
				daysAfter.add(7);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeSearchFeedbackConfgView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			// //System.out.println(">>>>>>>>>Inside beforeFeedbackConfgView : " +
			// mainHeaderName + ", searchType: " + searchType);
			setMainHeaderName(mainHeaderName);
			if (mainHeaderName.equalsIgnoreCase("Feedback Configuration"))
			{
				daysAfter = new ArrayList<Integer>();
				daysAfter.add(1);
				daysAfter.add(2);
				daysAfter.add(3);
				daysAfter.add(4);
				daysAfter.add(5);
				daysAfter.add(6);
				daysAfter.add(7);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewFeedbackConfiguration()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			boolean status = false;

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("after");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("smsTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("keyword");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("length");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("status");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default 0");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("type");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default 0");
			tableColumn.add(ob1);

			status = cbt.createTable22("feedbackConfig", tableColumn, connectionSpace);
			StringBuffer query = new StringBuffer("select after, smsTime, keyword, length, type from feedbackconfig");

			if (searchType != null)
			{
				if (searchType.equalsIgnoreCase("0"))
				{
					query.append(" where type='0';");
				}
				else if (searchType.equalsIgnoreCase("1"))
				{
					query.append(" where type='1'");
				}

			}
			// //System.out.println(">>>>>>>>>Query : " + query);
			List template = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			// //System.out.println(">>>>>>>template : " + template.size());
			FeedbackConfigBean configBean = null;
			feedbackDetail = new ArrayList<FeedbackConfigBean>();
			if (template != null && !template.isEmpty())
			{
				for (Iterator iterator = template.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					configBean = new FeedbackConfigBean();
					configBean.setAfter(object[0].toString());
					configBean.setTime(object[1].toString());
					configBean.setKeyword(object[2].toString());
					configBean.setLength(object[3].toString());
					configBean.setType((object[4].toString().equalsIgnoreCase("0") ? "Positive" : "Negative"));
					feedbackDetail.add(configBean);
				}
			}
			// //System.out.println(">>>>>>>>>>>>>feedbackDetail: " +
			// feedbackDetail.size());
			setFeedbackDetail(feedbackDetail);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String addFeedbackConfig()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = false;
			String mob = "";
			List getClientMob = cbt.executeAllSelectQuery("select * from useraccount where userID='" + Cryptography.encrypt(userName, DES_ENCRYPTION_KEY) + "'",
					connectionSpace);

			for (Iterator iterator = getClientMob.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				mob = object[1].toString();
			}
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("after");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("smsTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("keyword");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("length");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("smsDate");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("mobileNo");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("status");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default 0");
			tableColumn.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("type");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default 0");
			tableColumn.add(ob1);

			status = cbt.createTable22("feedbackConfig", tableColumn, connectionSpace);

			// //System.out.println(">>>>>>>> Table Created : " + status);
			status = false;
			ob = new InsertDataTable();
			ob.setColName("after");
			ob.setDataName(getAfter());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("smsTime");
			ob.setDataName(getTime());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("keyword");
			ob.setDataName(getKeyword());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("length");
			ob.setDataName(getLength());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("smsDate");
			ob.setDataName(DateUtil.getNextDateAfter(Integer.parseInt(getAfter())));
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("mobileNo");
			ob.setDataName(mob);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("userName");
			ob.setDataName(userName);
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("type");
			ob.setDataName(getType());
			insertData.add(ob);

			status = cbt.insertIntoTable("feedbackConfig", insertData, connectionSpace);

			if (status)
			{
				addActionMessage("You request for Length & Keyword is under process & we will contect you in next " + after + " days.");
				return SUCCESS;
			}
			else
			{
				addActionMessage("Oops There is some error in feedback configuration !");
				return SUCCESS;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ooops There Is Some Problem In CommonMaster Creation!");
			return ERROR;
		}
	}

	public String keywordExist()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// //System.out.println(">>>>>>>>>keywordExist");
			List keywordStatus = cbt.executeAllSelectQuery("select keyword from feedbackconfig where keyword='" + getKeyword() + "'", connectionSpace);
			keywordJSONObject = new JSONObject();

			if (keywordStatus != null)
			{
				if (!keywordStatus.isEmpty())
				{
					addActionError("Keyword already exist!");
					keywordJSONObject.put("exist", true);
				}
				else
				{
					addActionMessage("Keyword Available!");
					keywordJSONObject.put("exist", false);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String editFeedbackConfiguration()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			if (getOper().equalsIgnoreCase("del"))
			{
				cbt.deleteAllRecordForId("feedbackconfig", "id", getId(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * Feedback code end here.
	 */

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public List<ConfigurationUtilBean> getAssociateStatusMasterTextBox()
	{
		return associateStatusMasterTextBox;
	}

	public void setAssociateStatusMasterTextBox(List<ConfigurationUtilBean> associateStatusMasterTextBox)
	{
		this.associateStatusMasterTextBox = associateStatusMasterTextBox;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getMappingTable()
	{
		return mappingTable;
	}

	public void setMappingTable(String mappingTable)
	{
		this.mappingTable = mappingTable;
	}

	public String getMapSubTable()
	{
		return mapSubTable;
	}

	public void setMapSubTable(String mapSubTable)
	{
		this.mapSubTable = mapSubTable;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getDataTable()
	{
		return dataTable;
	}

	public void setDataTable(String dataTable)
	{
		this.dataTable = dataTable;
	}

	public Map<String, String> getAssociateCatList()
	{
		return associateCatList;
	}

	public void setAssociateCatList(Map<String, String> associateCatList)
	{
		this.associateCatList = associateCatList;
	}

	public String getOfferingLevelId()
	{
		return offeringLevelId;
	}

	public void setOfferingLevelId(String offeringLevelId)
	{
		this.offeringLevelId = offeringLevelId;
	}

	public int getSubTypeExists()
	{
		return subTypeExists;
	}

	public void setSubTypeExists(int subTypeExists)
	{
		this.subTypeExists = subTypeExists;
	}

	public String getModuleName1()
	{
		return moduleName1;
	}

	public void setModuleName1(String moduleName1)
	{
		this.moduleName1 = moduleName1;
	}

	public Map<String, String> getAssociateTypeList()
	{
		return associateTypeList;
	}

	public void setAssociateTypeList(Map<String, String> associateTypeList)
	{
		this.associateTypeList = associateTypeList;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public boolean getIsAssociateSubTypeExists()
	{
		return isAssociateSubTypeExists;
	}

	public void setIsAssociateSubTypeExists(boolean isAssociateSubTypeExists)
	{
		this.isAssociateSubTypeExists = isAssociateSubTypeExists;
	}

	public List<GridDataPropertyView> getMasterViewPropForAssociateSubType()
	{
		return masterViewPropForAssociateSubType;
	}

	public void setMasterViewPropForAssociateSubType(List<GridDataPropertyView> masterViewPropForAssociateSubType)
	{
		this.masterViewPropForAssociateSubType = masterViewPropForAssociateSubType;
	}

	public String getMainHeaderNameForAssociateSubType()
	{
		return mainHeaderNameForAssociateSubType;
	}

	public void setMainHeaderNameForAssociateSubType(String mainHeaderNameForAssociateSubType)
	{
		this.mainHeaderNameForAssociateSubType = mainHeaderNameForAssociateSubType;
	}

	public List<Object> getMasterViewListForAssociateSubType()
	{
		return masterViewListForAssociateSubType;
	}

	public void setMasterViewListForAssociateSubType(List<Object> masterViewListForAssociateSubType)
	{
		this.masterViewListForAssociateSubType = masterViewListForAssociateSubType;
	}

	public boolean isCategoryBlock()
	{
		return categoryBlock;
	}

	public void setCategoryBlock(boolean categoryBlock)
	{
		this.categoryBlock = categoryBlock;
	}

	public Map<String, String> getStateNameList()
	{
		return stateNameList;
	}

	public void setStateNameList(Map<String, String> stateNameList)
	{
		this.stateNameList = stateNameList;
	}

	public boolean isStateNameExists()
	{
		return stateNameExists;
	}

	public void setStateNameExists(boolean stateNameExists)
	{
		this.stateNameExists = stateNameExists;
	}

	public String getOfferingDivId()
	{
		return offeringDivId;
	}

	public void setOfferingDivId(String offeringDivId)
	{
		this.offeringDivId = offeringDivId;
	}

	public List<Integer> getDaysAfter()
	{
		return daysAfter;
	}

	public void setDaysAfter(List<Integer> daysAfter)
	{
		this.daysAfter = daysAfter;
	}

	public String getAfter()
	{
		return after;
	}

	public void setAfter(String after)
	{
		this.after = after;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getLength()
	{
		return length;
	}

	public void setLength(String length)
	{
		this.length = length;
	}

	public List<FeedbackConfigBean> getFeedbackDetail()
	{
		return feedbackDetail;
	}

	public void setFeedbackDetail(List<FeedbackConfigBean> feedbackDetail)
	{
		this.feedbackDetail = feedbackDetail;
	}

	public JSONObject getKeywordJSONObject()
	{
		return keywordJSONObject;
	}

	public void setKeywordJSONObject(JSONObject keywordJSONObject)
	{
		this.keywordJSONObject = keywordJSONObject;
	}

	public String getSearchType()
	{
		return searchType;
	}

	public void setSearchType(String searchType)
	{
		this.searchType = searchType;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getLostOpportunityTypeName()
	{
		return lostOpportunityTypeName;
	}

	public void setLostOpportunityTypeName(String lostOpportunityTypeName)
	{
		this.lostOpportunityTypeName = lostOpportunityTypeName;
	}

	public String getLostOpportunityTypeHeading()
	{
		return lostOpportunityTypeHeading;
	}

	public void setLostOpportunityTypeHeading(String lostOpportunityTypeHeading)
	{
		this.lostOpportunityTypeHeading = lostOpportunityTypeHeading;
	}

	public boolean isLostOpportunityMandatory()
	{
		return lostOpportunityMandatory;
	}

	public void setLostOpportunityMandatory(boolean lostOpportunityMandatory)
	{
		this.lostOpportunityMandatory = lostOpportunityMandatory;
	}

	public void setMainHeaderNameForAssociateType(String mainHeaderNameForAssociateType)
	{
		this.mainHeaderNameForAssociateType = mainHeaderNameForAssociateType;
	}

	public String getMainHeaderNameForAssociateType()
	{
		return mainHeaderNameForAssociateType;
	}

	public Integer getCountSource() {
		return countSource;
	}

	public void setCountSource(Integer countSource) {
		this.countSource = countSource;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
