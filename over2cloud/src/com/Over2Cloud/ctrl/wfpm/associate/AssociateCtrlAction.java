package com.Over2Cloud.ctrl.wfpm.associate;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper2;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper3;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.lead.LeadActionControlDao;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper2;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssociateCtrlAction extends ActionSupport implements ServletRequestAware
{

	Map																									session												= ActionContext.getContext().getSession();
	String																							userName											= (String) session.get("uName");
	private String																			empId													= session.get("empIdofuser").toString().split("-")[1];
	private String timestatus;
	private String scheduletime;
	String																							accountID											= (String) session.get("accountid");
	SessionFactory																			connectionSpace								= (SessionFactory) session.get("connectionSpace");
	String																							cId														= new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName,
																																												userName, connectionSpace)[0];
	static final Log																		log														= LogFactory.getLog(AssociateCtrlAction.class);
	private HttpServletRequest													request;
	public static final String													DES_ENCRYPTION_KEY						= "ankitsin";
	private int																					associateForBasicDetails			= 0;
	private int																					associateForOfferingDetails		= 0;
	private int																					associateForContacDetails			= 0;
	private String																			associateStatus;
	private int																					associateForAttachmentDetails	= 0;
	private List<ConfigurationUtilBean>									associateTextBox							= null;
	private List<ConfigurationUtilBean>									associateDropDown							= null;
	private ArrayList<ArrayList<String>>								associateStatusAllList				= null;
	private String																			fromDashboard;
	private int																					nextSevenDaysActivity;
	private String																			activityType;
	private String																			stateExist										= null;
	private int																					tomorrowActivity;
	private String																			stateValue										= null;
	private int																					existingAssociate;
	private int																					todayActivity;
	private String																			locationExist									= null;
	private String																			locationValue									= null;
	private String																			associateTypeExist						= null;
	private String																			associateTypeValue						= null;
	private String																			associateSubTypeExist					= null;
	private String																			associateSubTypeValue					= null;
	private String																			associateCategoryExist				= null;
	private String																			associateCategoryValue				= null;
	private String																			associateRatingExist					= null;
	private String																			associateRatingValue					= null;
	private String																			sourceNameExist								= null;
	private String																			sourceNameValue								= null;
	private String																			targetAchievedExist						= null;
	private String																			targetAchievedValue						= null;
	private Map<Integer, String>												targetKpiLiist								= new java.util.HashMap<Integer, String>();
	private Map<String, String>													stateDataMap									= null;
	private Map<String, String>													associateTypeMap							= null;
	private Map<String, String>													associateCategoryMap					= null;
	private Map<String, String>													sourceMap											= null;

	private String																			offeringLevel									= session.get("offeringLevel").toString();
	private boolean																			OLevel1												= false;
	private String																			OLevel1LevelName							= null;

	private boolean																			OLevel2												= false;
	private String																			OLevel2LevelName							= null;

	private boolean																			OLevel3												= false;
	private String																			OLevel3LevelName							= null;

	private boolean																			OLevel4												= false;
	private String																			OLevel4LevelName							= null;

	private boolean																			OLevel5												= false;
	private String																			OLevel5LevelName							= null;
	private Map<String, String>													verticalMap										= new LinkedHashMap<String, String>();
	private List<ConfigurationUtilBean>									associateTextBoxForContact		= null;
	// private Map<String, String> associateDropDownForContact = null;
	private Map<String, String>													associateDateControls					= null;

	private boolean																			degreeOfInfluenceExist				= false;
	private String																			degreeOfInfluence							= null;
	private String																			tempVariable									= null;																								// use
	// it
	// for
	// holding
	// any
	// value
	private JSONArray																		locationJsonArray							= null;

	private String																			modifyFlag										= null;
	private String																			deleteFlag										= null;
	private String																			mainHeaderName								= null;
	private String																			isExistingAssociate						= null;
	private List<GridDataPropertyView>									masterViewProp								= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>									masterViewPropForContact			= new ArrayList<GridDataPropertyView>();
	private String																			lostFlag											= null;

	private Integer																			rows													= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer																			page													= 0;
	// sorting order - asc or desc
	private String																			sord													= "";
	// get index row - i.e. user click to sort.
	private String																			sidx													= "";
	// Search Field
	private String																			searchField										= "";
	// The Search String
	private String																			searchString									= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String																			searchOper										= "";
	// Your Total Pages
	private Integer																			total													= 0;
	// All Record
	private Integer																			records												= 0;
	private boolean																			loadonce											= false;
	// Grid colomn view
	private String																			oper;
	private String																			id;
	private List<Object>																masterViewList								= null;
	private List<Object>																masterViewListForContact			= null;
	private Map<String, String>													basicParams										= null;
	List<String>																				namesContact									= null;
	List<Parent>																				parentContact									= new ArrayList<Parent>();
	List<String>																				namesOffering									= null;
	List<Parent>																				parentOffering								= new ArrayList<Parent>();
	List<String>																				namesTakeaction								= null;
	List<Parent>																				parentTakeaction							= new ArrayList<Parent>();
	private Map<String, String>													offeringList									= null;
	private String																			associateName;
	private Map<String, String>													associateStatusList						= null;
	private String																			statusName										= null;
	private String																			targetAchieved								= null;
	private String																			convertToAssociate						= null;
	private String																			acManagerExist								= null;
	private String																			acManager											= null;
	private Map<String, String>													employee_basicMap;
	private String																			statusExist										= null;
	private String																			status												= null;
	private Map<String, String>													associate_statusMap						= null;
	private int																					pendingAssociate;
	private int																					lostAssociate;
	private int																					nextAssociateAction;
	private Map<String, Integer>												activity											= null;
	private String																			legendShow										= "false";
	private String																			width													= "250";
	private String																			height												= "250";
	private int																					missedActivity								= 0;
	private Map<String, Integer>												statusMap											= null;
	private Map<String, Integer>												offWrtAssoMap									= null;
	String																							tableName											= "", colName = "";
	private ArrayList<ArrayList<String>>								birthdayList									= null;
	private ArrayList<ArrayList<String>>								anniversaryList								= null;
	private String																			currentDate										= DateUtil.getCurrentDateIndianFormat();
	private String																			ExistAndLostAssociate;
	private String																			industry;
	private String																			industryExist;
	private String																			subIndustry;
	private String																			subIndustryExist;
	private Map<String, String>													industryMap;
	private Map<String, String>													subIndustryMap;
	private Map<String, String>													industryList;
	private Map<String, String>													sourceList;
	private Map<String, String>													locationList;
	private String																			starRating;
	private String																			sourceName;
	private String																			associateCategory;
	private String																			associateType;
	private String																			associate_Name;
	private String																			location;
	private boolean																			departmentExist;
	private String																			department;
	private Map<String, String>													deptMap;
	private Map<String, String>													location_master_dataMap;
	private int																					selectedStateId;
	private Map<String, String>													associateSubTypeMap;
	private String																			contactId;
	private String																			offeringId;
	private String																			date;
	private String																			comments;
	private Map<String, String>													contactPersonMap;
	private ArrayList<ArrayList<GridDataPropertyView>>	associateContactEditList;
	private Map<String, String>													deptlist;
	private List<ConfigurationUtilBean>									associateDropDownForContact;

	/*
	 * Anoop, 18-7-2013 Build Associate add page
	 */
	public String beforeAssociateAdd()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			setAddPageDataForAssociate();
			ClientHelper3 ch3 = new ClientHelper3();
			contactPersonMap = new LinkedHashMap<String, String>();
			contactPersonMap = ch3.fetchallInactiveContact();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in WFPM  method: beforeAssociateAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 6-5-2014
	 */
	public String beforeAssociateEdit()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			AssociateHelper1 ah = new AssociateHelper1();
			ClientHelper3 ch3 = new ClientHelper3();

			/*
			 * For Associate basic details
			 */
			masterViewProp = ah.fetchAssociateBasicDataForEdition(id);
			// Source
			sourceMap = new LinkedHashMap<String, String>();
			sourceMap = ch3.fetchAllSource();
			// Location
			location_master_dataMap = new LinkedHashMap<String, String>();
			location_master_dataMap = ch3.fetchAllLocation();
			// Employee
			employee_basicMap = new LinkedHashMap<String, String>();
			employee_basicMap = ch3.fetchAllEmployee();
			// Industry
			industryMap = new LinkedHashMap<String, String>();
			industryMap = ch3.fetchAllIndustry();

			// subIndustry
			subIndustryMap = ch3.fetchSubIndustryByIndustryId(AssociateHelper1.industryId);

			// selected state
			selectedStateId = ch3.fetchStateIdByLocationId(AssociateHelper1.locationId);

			// Get all source list
			sourceMap = new LinkedHashMap<String, String>();
			sourceMap = ch3.fetchAllSource();

			// Get all state list
			stateDataMap = new LinkedHashMap<String, String>();
			stateDataMap = ch3.fetchAllState();

			// associateTypeMap
			associateTypeMap = new LinkedHashMap<String, String>();
			associateTypeMap = ch3.fetchAssociateType();

			associateSubTypeMap = ch3.fetchAssociateSubTypeByTypeId(AssociateHelper1.associateTypeId);

			// Get all associate category list
			associateCategoryMap = new LinkedHashMap<String, String>();
			associateCategoryMap = ch3.fetchAssociateCategory();

			/*
			 * For Associate contact details
			 */

			associateContactEditList = ah.fetchAssociateContactDataForEdition(id);
			// Department

			deptlist = new LinkedHashMap<String, String>();
			deptlist = ch3.fetchAllDepartment();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in WFPM  method: beforeAssociateAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Sanjiv 12-05-2014. For editing Associate contacts.
	 */
	public String editAssociateContact()
	{
		String idval = null;
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> associateColName = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_contact_configuration");

			if (associateColName != null)
			{
				// List <InsertDataTable> insertData=new
				// ArrayList<InsertDataTable>();
				InsertDataTable ob = null;

				boolean status = false;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();

				for (GridDataPropertyView gdp : associateColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);

				}

				cbt.createTable22("associate_contact_data", tableColumn, connectionSpace);

				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				List paramList = new ArrayList<String>();
				int paramValueSize = 0;
				boolean statusTemp = true;
				String associateName = null;
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && Parmname != null)
					{
						paramList.add(Parmname);
						/*
						 * if (Parmname.equalsIgnoreCase("clientName")) { associateName =
						 * paramValue; continue; }
						 */
						// adding the parameters list.
						if (statusTemp)
						{
							String tempParamValueSize[] = request.getParameterValues(Parmname);
							for (String H : tempParamValueSize)
							{
								// counting one time size of the parameter value
								if (H != null) paramValueSize++;
							}
							statusTemp = false;
						}
					}
				}
				String parmValuew[][] = new String[paramList.size()][paramValueSize];
				int m = 0;
				for (Object c : paramList)
				{
					Object Parmname = (Object) c;
					String paramValue[] = request.getParameterValues(Parmname.toString());

					for (int j = 0; j < paramValue.length; j++)
					{

						if (!paramValue[j].equalsIgnoreCase("") && paramValue[j] != null && !paramValue[j].equalsIgnoreCase("-1"))
						{
							// System.out.println("paramValue[j]" + paramValue[j]);
							parmValuew[m][j] = paramValue[j];
						}
					}
					m++;
				}
				boolean valueSelect = false;
				for (int i = 0; i < paramValueSize; i++)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					for (int k = 0; k < paramList.size(); k++)
					{

						if (parmValuew[k][i] != null)
						{
							// System.out.println("parmValuew[k][i]>>" + parmValuew[k][i]);
							valueSelect = true;

						}
						else
						{
							valueSelect = false;
						}

						if (valueSelect)
						{
							if (!(paramList.get(k).toString().equalsIgnoreCase("associateName")))
							{
								ob = new InsertDataTable();
								// System.out.println("Column name " +
								// paramList.get(k).toString());
								if (paramList.get(k).toString().equals("id"))
								{
									idval = parmValuew[k][i];
								}
								// System.out.println("Data name " + parmValuew[k][i]);

								if (paramList.get(k).toString().equalsIgnoreCase("birthday") || paramList.get(k).toString().equalsIgnoreCase("anniversary"))
								{
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(DateUtil.convertDateToUSFormat(parmValuew[k][i]));
								}
								else
								{
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(parmValuew[k][i]);
								}
								insertData.add(ob);
							}
						}
					}

					if (valueSelect)
					{
						/*
						 * ob = new InsertDataTable(); ob.setColName("clientName"); //
						 * System.out.println("associateName  " + associateName);
						 * ob.setDataName(associateName); insertData.add(ob);
						 */

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(cId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						status = cbt.updateIntoTable("associate_contact_data", insertData, idval, connectionSpace);
					}
				}

				if (status)
				{
					addActionMessage("Associate contact edited successfully.");
				}
				else
				{
					addActionMessage("Oops There is some error in data.");
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: editAssociateContact of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 18-7-2013
	 */
	@SuppressWarnings("unchecked")
	public void setAddPageDataForAssociate()
	{

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Create accordion for associate details, begins
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_associate_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("associate_basic_configuration"))
					{
						associateForBasicDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("associate_offering_configuration"))
					{
						associateForOfferingDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("associate_contact_configuration"))
					{
						associateForContacDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("<fill attachment table>"))
					{
						associateForAttachmentDetails = 1;
					}
				}
			}

			/*
			 * Accordion: Associate Basic Information
			 */
			// Build map for text box and drop down for Associate basic
			// information
			if (associateForBasicDetails == 1)
			{
				List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
						"table_name", "associate_basic_configuration");
				associateTextBox = new ArrayList<ConfigurationUtilBean>();
				associateDropDown = new ArrayList<ConfigurationUtilBean>();
				if (offeringLevel1 != null)
				{
					for (GridDataPropertyView gdp : offeringLevel1)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("weightage"))
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
							associateTextBox.add(obj);
						}

						if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
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

							// //////////////////////////////
							if (gdp.getColomnName().equalsIgnoreCase("industry"))
							{
								industry = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) industryExist = "true";
								else industryExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
							{
								subIndustry = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) subIndustryExist = "true";
								else subIndustryExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("state"))
							{
								stateValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) stateExist = "true";
								else stateExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("location"))
							{
								locationValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) locationExist = "true";
								else locationExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("associateType"))
							{
								associateTypeValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) associateTypeExist = "true";
								else associateTypeExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("associateSubType"))
							{
								associateSubTypeValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) associateSubTypeExist = "true";
								else associateSubTypeExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("associateCategory"))
							{
								associateCategoryValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) associateCategoryExist = "true";
								else associateCategoryExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("associateRating"))
							{
								associateRatingValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) associateRatingExist = "true";
								else associateRatingExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
							{
								sourceNameValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) sourceNameExist = "true";
								else sourceNameExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("acManager"))
							{
								acManager = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) acManagerExist = "true";
								else acManagerExist = "false";
							}
							if (gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
							{
								targetAchievedValue = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) targetAchievedExist = "true";
								else targetAchievedExist = "false";

								/**
								 * getting mapped target of employee
								 */
								// targetKpiLiist = CommonWork.getTargetOfEmployee(userName,
								// connectionSpace);
							}
							if (gdp.getColomnName().equalsIgnoreCase(// ///////
									"status"))
							{
								status = gdp.getHeadingName();
								if (gdp.getMandatroy().equalsIgnoreCase("1")) statusExist = "true";
								else statusExist = "false";
							}

							associateDropDown.add(obj);
						}
					}

					/*
					 * Fill values in dropdown
					 */
					ClientHelper3 ch3 = new ClientHelper3();
					industryMap = new LinkedHashMap<String, String>();
					if (industryExist != null)
					{
						List industryData = cbt.executeAllSelectQuery("select id,industry from industrydatalevel1 order by industry", connectionSpace);
						if (industryData != null && industryData.size() > 0)
						{
							for (Object i : industryData)
							{
								Object[] dataI = (Object[]) i;
								industryMap.put(dataI[0].toString(), dataI[1].toString());
							}
						}
					}

					subIndustryMap = new LinkedHashMap<String, String>();
					if (subIndustryExist != null)
					{
						List subIndustryData = cbt.executeAllSelectQuery("select id,subIndustry from subindustrydatalevel2 order by subIndustry", connectionSpace);// subIndustryMap
						if (subIndustryData != null && subIndustryData.size() > 0)
						{
							for (Object s : subIndustryData)
							{
								Object[] dataS = (Object[]) s;
								subIndustryMap.put(dataS[0].toString(), dataS[1].toString());
							}

						}
					}
					// Get all state list
					stateDataMap = new LinkedHashMap<String, String>();
					if (stateExist != null && !stateExist.equalsIgnoreCase(""))
					{
						stateDataMap = ch3.fetchAllState();
					}

					// Get all associate type list
					associateTypeMap = new LinkedHashMap<String, String>();
					if (associateTypeExist != null && !associateTypeExist.equalsIgnoreCase(""))
					{
						associateTypeMap = ch3.fetchAssociateType();
					}

					// Get all associate category list
					associateCategoryMap = new LinkedHashMap<String, String>();
					if (associateCategoryExist != null && !associateCategoryExist.equalsIgnoreCase(""))
					{
						associateCategoryMap = ch3.fetchAssociateCategory();
					}

					// Get all source list
					sourceMap = new LinkedHashMap<String, String>();
					if (sourceNameExist != null && !sourceNameExist.equalsIgnoreCase(""))
					{
						sourceMap = ch3.fetchAllSource();
					}

					// employee
					EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
					employee_basicMap = new LinkedHashMap<String, String>();
					if (acManagerExist != null && !acManagerExist.equalsIgnoreCase(""))
					{
						employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
					}

					// status
					associate_statusMap = new LinkedHashMap<String, String>();
					if (statusExist != null && !statusExist.equalsIgnoreCase(""))
					{
						// Get all client status list
						List<String> client_statusColname = new ArrayList<String>();
						client_statusColname.add("id");
						client_statusColname.add("statusname");
						List client_statusData = cbt.viewAllDataEitherSelectOrAll("associatestatus", client_statusColname, connectionSpace);
						if (client_statusData != null)
						{

							for (Object c : client_statusData)
							{
								Object[] dataC = (Object[]) c;
								associate_statusMap.put(dataC[0].toString(), dataC[1].toString());
							}
						}
					}
				}

				/*
				 * Accordion: Associate offering Details
				 */

				if (associateForOfferingDetails == 1)
				{
					// oferring taken by client in configuration
					offeringLevel = session.get("offeringLevel").toString();
					String[] oLevels = null;

					if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
					{
						oLevels = offeringLevel.split("#");
						int level = Integer.parseInt(oLevels[0]);
						if (level > 0)
						{
							OLevel1 = true;
							OLevel1LevelName = oLevels[1];
						}
						if (level > 1)
						{
							OLevel2 = true;
							OLevel2LevelName = oLevels[2];
						}
						if (level > 2)
						{
							OLevel3 = true;
							OLevel3LevelName = oLevels[3];
						}
						if (level > 3)
						{
							OLevel4 = true;
							OLevel4LevelName = oLevels[4];
						}
						if (level > 4)
						{
							OLevel5 = true;
							OLevel5LevelName = oLevels[5];
						}

						// Get verticalname list
						List<String> verticalColname = new ArrayList<String>();
						verticalColname.add("id");
						verticalColname.add("verticalname");

						List verticalData = cbt.viewAllDataEitherSelectOrAll("offeringlevel1", verticalColname, connectionSpace);
						if (verticalData != null)
						{

							for (Object c : verticalData)
							{
								Object[] dataC = (Object[]) c;
								verticalMap.put(dataC[0].toString(), dataC[1].toString());
							}
						}
					}
				}

				if (associateForContacDetails == 1)
				{

					/*
					 * Accordion: Associate contact information, if contact is taken
					 */

					List<GridDataPropertyView> list = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
							"table_name", "associate_contact_configuration");
					associateTextBoxForContact = new ArrayList<ConfigurationUtilBean>();
					associateDropDownForContact = new ArrayList<ConfigurationUtilBean>();
					associateDateControls = new LinkedHashMap<String, String>();

					for (GridDataPropertyView gdp : list)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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

							associateTextBoxForContact.add(obj);
						}

						if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
							associateDropDownForContact.add(obj);
						}
						// associateDropDownForContact.put(gdp.getColomnName(),
						// gdp.getHeadingName());
						if (gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{

							associateDateControls.put(gdp.getColomnName(), gdp.getHeadingName());
						}
					}

					ClientHelper3 ch3 = new ClientHelper3();
					deptMap = new LinkedHashMap<String, String>();
					// Code for drop down
					for (ConfigurationUtilBean entry : associateDropDownForContact)
					{
						// String tmpString = entry.getKey().trim();
						if (entry.getKey().equalsIgnoreCase("degreeOfInfluence"))
						{
							degreeOfInfluence = entry.getValue();
							if (entry.isMandatory()) degreeOfInfluenceExist = true;
						}
						if (entry.getKey().equalsIgnoreCase("department"))
						{
							department = entry.getValue();
							if (entry.isMandatory()) departmentExist = true;
						}

						if (entry.getKey().equalsIgnoreCase("department"))
						{
							setDepartment(entry.getValue());
							deptMap = ch3.fetchAllDepartment();
							if (entry.isMandatory()) setDepartmentExist(true);
						}
					}
					// System.out.println("deptMap:"+deptMap.size());
				}
			}

		}
		catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
		// Create accordian for client details, begins

	}

	/*
	 * Anoop, 19-7-2013 Add prospective associate data
	 */
	public String addAssociate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_basic_configuration");
			if (statusColName != null)
			{
				// Start: create table 'associate_basic_data'
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				/*
				 * boolean userTrue = false; boolean dateTrue = false; boolean timeTrue
				 * = false;
				 */
				for (GridDataPropertyView gdp : statusColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					/*
					 * if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue =
					 * true; else if (gdp.getColomnName().equalsIgnoreCase("date"))
					 * dateTrue = true; else if
					 * (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
					 */
				}
				cbt.createTable22("associate_basic_data", Tablecolumesaaa, connectionSpace);
				// End: create table 'associate_basic_data'

				// Start: Fetch jsp page parameters and their values and store
				// data in table 'associate_basic_data'
				String targetAchieved = null;
				String acManager = "";
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					// //System.out.println(Parmname+"  ======= "+paramValue);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);

						if (Parmname.equalsIgnoreCase("targetAchieved"))
						{
							targetAchieved = paramValue;
						}
						if (Parmname.equalsIgnoreCase("acManager"))
						{
							acManager = paramValue;
						}
					}
				}
				if (!acManager.equalsIgnoreCase(""))
				{
					try
					{
						ob = new InsertDataTable();
						ob.setColName("userName");

						LeadHelper2 lh2 = new LeadHelper2();
						acManager = lh2.getContactIdOfEmpByEmpId(acManager);
						if (acManager != null)
						{
							ob.setDataName(acManager);
						}
						else
						{
							acManager = cId;
							ob.setDataName(acManager);
						}
					}
					catch (Exception e)
					{
						ob.setDataName(cId);
						e.printStackTrace();
					}
					insertData.add(ob);
				}
				else
				{
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(cId);
					insertData.add(ob);
				}

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				status = cbt.insertIntoTable("associate_basic_data", insertData, connectionSpace);
				if (status)
				{
					int idMAx = cbt.getMaxId("associate_basic_data", connectionSpace);
					LeadActionControlDao lacd = new LeadActionControlDao();
					// for KPI auto achievement for 'Data Collection'
					lacd.insertInToKPIAutofillTable(empId, "1", userName, connectionSpace, String.valueOf(idMAx), "Prospective Associate");
					// for KPI auto achievement for 'Prospective Associate'
					lacd.insertInToKPIAutofillTable(empId, "7", userName, connectionSpace, String.valueOf(idMAx), "Prospective Associate");

					DSRgerneration ta = new DSRgerneration();
					int empID = Integer.parseInt((String)session .get("empIdofuser").toString().split("-")[1]);

					if (targetAchieved != null && !targetAchieved.equalsIgnoreCase("") && !targetAchieved.equalsIgnoreCase("-1"))
					{
						ta.setDSRRecords(targetAchieved, empID, DSRMode.KPI, DSRType.ONLINE);
					}
					addActionMessage("Associate Added Successfully");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
					return SUCCESS;
				}
				// End: Fetch jsp page parameters and their values and store
				// data in table 'associate_basic_data'

			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in WFPM  method: addAssociate of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 6-5-2014
	 */
	public String editAssociate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			String targetAchieved = null;
			String acManager = "";
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = request.getParameter(Parmname);
				// //System.out.println(Parmname+"  ======= "+paramValue);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
				{
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					ob.setDataName(paramValue);
					insertData.add(ob);

					if (Parmname.equalsIgnoreCase("targetAchieved"))
					{
						targetAchieved = paramValue;
					}
					if (Parmname.equalsIgnoreCase("acManager"))
					{
						acManager = paramValue;
					}
				}
			}
			if (!acManager.equalsIgnoreCase(""))
			{
				try
				{
					ob = new InsertDataTable();
					ob.setColName("userName");

					LeadHelper2 lh2 = new LeadHelper2();
					acManager = lh2.getContactIdOfEmpByEmpId(acManager);
					if (acManager != null)
					{
						ob.setDataName(acManager);
					}
					else
					{
						acManager = cId;
						ob.setDataName(acManager);
					}
				}
				catch (Exception e)
				{
					ob.setDataName(cId);
					e.printStackTrace();
				}
				insertData.add(ob);
			}
			else
			{
				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(cId);
				insertData.add(ob);
			}

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData.add(ob);

			CommonOperInterface coi = new CommonConFactory().createInterface();
			boolean status = coi.updateIntoTable("associate_basic_data", insertData, id, connectionSpace);
			if (status)
			{
				DSRgerneration ta = new DSRgerneration();
				int empID =Integer.parseInt((String)session .get("empIdofuser").toString().split("-")[1]);

				if (targetAchieved != null && !targetAchieved.equalsIgnoreCase("") && !targetAchieved.equalsIgnoreCase("-1"))
				{
					ta.setDSRRecords(targetAchieved, empID, DSRMode.KPI, DSRType.ONLINE);
				}
				addActionMessage("Associate Edited Successfully.");
			}
			else
			{
				addActionMessage("Oops There is some error in data!");
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 19-7-2013 fetch location on the basis of state
	 */
	public String fetchLocation()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			// tempVariable -> used for keeping id of state
			locationJsonArray = new JSONArray();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, name from location where stateName = ");
			query.append(tempVariable);
			query.append(" order by name asc");

			List locationList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (locationList != null)
			{
				for (Object object : locationList)
				{
					Object[] obj = (Object[]) object;
					if (obj != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", obj[0].toString());
						formDetailsJson.put("NAME", obj[1].toString());
						locationJsonArray.add(formDetailsJson);
					}
				}
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in WFPM  method: fetchLocation of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 20-7-2013 fetch associate for ddn
	 */
	public String fetchAssociate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			// tempVariable -> used for keeping id of state
			locationJsonArray = new JSONArray();
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			query.append("select id, associateName from associate_basic_data");

			List locationList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (locationList != null)
			{
				for (Object object : locationList)
				{
					Object[] obj = (Object[]) object;

					if (obj != null)
					{
						// System.out.println("Name >>>>>>>>  " + obj[1].toString());
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", obj[0].toString());
						formDetailsJson.put("NAME", obj[1].toString());
						locationJsonArray.add(formDetailsJson);
					}
				}
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in WFPM  method: fetchAssociate of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 20-7-2013 store offering mapped with associate
	 */
	public String addOffering()
	{
		boolean flag = false;
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			InsertDataTable ob = null;
			String clientId = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			int level = 0;

			offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Iterator it = requestParameterNames.iterator();

			String associateName = null;
			List<String> paramList = new ArrayList<String>();
			while (it.hasNext())
			{
				String ParmName = it.next().toString();
				if (ParmName.equalsIgnoreCase("associateName"))
				{
					associateName = request.getParameter(ParmName);
				}
				else
				{
					// get all offering level in a list
					paramList.add(ParmName);
				}
			}

			// get values of last level of offering
			String tempParamValues[] = null;
			if (paramList.contains("subvariantname"))
			{
				tempParamValues = request.getParameterValues("subvariantname");
			}
			else if (paramList.contains("variantname"))
			{
				tempParamValues = request.getParameterValues("variantname");
			}
			else if (paramList.contains("subofferingname"))
			{
				tempParamValues = request.getParameterValues("subofferingname");
			}
			else if (paramList.contains("offeringname"))
			{
				tempParamValues = request.getParameterValues("offeringname");
			}
			else if (paramList.contains("verticalname"))
			{
				tempParamValues = request.getParameterValues("verticalname");
			}

			// count: it is to check no. of offering mapped with associate
			// successfully
			int count = 0;
			if (tempParamValues.length > 0)
			{
				// create table 'associate_offering_mapping'
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("associateName");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				// overwriting same tableColumes reference again and again
				ob1 = new TableColumes();
				ob1.setColumnname("offeringId");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("offeringLevelId");
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

				ob1 = new TableColumes();
				ob1.setColumnname("isConverted");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("convertDate");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("convertTime");
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);

				cbt.createTable22("associate_offering_mapping", Tablecolumesaaa, connectionSpace);
				// end creating table 'associate_offering_mapping'

				/*
				 * set one record value for associate
				 */
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				ob = new InsertDataTable();
				ob.setColName("associateName");
				ob.setDataName(associateName);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("offeringLevelId");
				ob.setDataName(String.valueOf(level));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(cId);
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
				ob.setColName("isConverted");
				ob.setDataName("0");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("offeringId");
				insertData.add(ob);

				/*
				 * Each record value will be same for associate except offeringID for
				 * multiple mapped offerings save record one by one
				 */
				for (String H : tempParamValues)
				{
					if (!H.equalsIgnoreCase("") && H != null && !H.equalsIgnoreCase("-1"))
					{
						ob.setDataName(H);
						flag = cbt.insertIntoTable("associate_offering_mapping", insertData, connectionSpace);
						if (flag) count++;
					}
				}
			}
			if (count > 0)
			{
				addActionMessage(count + " Associate offering saved successfully");
			}
			else
			{
				addActionMessage("Oops There is some error in data!");
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in WFPM  method: addOffering of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 20-7-2013 store contacts mapped with associate
	 */
	public String addContacts()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> associateColName = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_contact_configuration");

			if (associateColName != null)
			{
				// List <InsertDataTable> insertData=new
				// ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				boolean associate_status = false;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();
				List<InsertDataTable> insertAssociateEmpBasic = new ArrayList<InsertDataTable>();
				InsertDataTable associateEmpBasicObj = null;

				for (GridDataPropertyView gdp : associateColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);

				}

				cbt.createTable22("associate_contact_data", tableColumn, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				List paramList = new ArrayList<String>();
				int paramValueSize = 0;
				boolean statusTemp = true;
				String associateName = null;
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);

					if (paramValue != null && Parmname != null)
					{
						paramList.add(Parmname);
						if (Parmname.equalsIgnoreCase("associateName"))
						{
							associateName = paramValue;
							continue;
						}
						// adding the parameters list.
						if (statusTemp)
						{
							String tempParamValueSize[] = request.getParameterValues(Parmname);
							for (String H : tempParamValueSize)
							{
								// counting one time size of the parameter value
								if (H != null) paramValueSize++;
							}
							statusTemp = false;
						}
					}
				}
				String parmValuew[][] = new String[paramList.size()][paramValueSize];
				int m = 0;
				for (Object c : paramList)
				{
					Object Parmname = (Object) c;
					String paramValue[] = request.getParameterValues(Parmname.toString());

					for (int j = 0; j < paramValue.length; j++)
					{

						if (!paramValue[j].equalsIgnoreCase("") && paramValue[j] != null && !paramValue[j].equalsIgnoreCase("-1"))
						{
							parmValuew[m][j] = paramValue[j];
						}
					}
					m++;
				}
				for (int i = 0; i < paramValueSize; i++)
				{
					boolean valueSelect = false;
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					for (int k = 0; k < paramList.size(); k++)
					{

						if (parmValuew[k][i] != null)
						{
							valueSelect = true;

						}
						else
						{
							valueSelect = false;
						}

						if (valueSelect)
						{
							if (!paramList.get(k).toString().equalsIgnoreCase("associateName"))
							{
								ob = new InsertDataTable();
								ob.setColName(paramList.get(k).toString());
								ob.setDataName(parmValuew[k][i]);
								insertData.add(ob);
								
								if(paramList.get(k).toString().equalsIgnoreCase("contactNum")){
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("mobOne");
									associateEmpBasicObj.setDataName(parmValuew[k][i]);
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								else if(paramList.get(k).toString().equalsIgnoreCase("communicationName"))
								{
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("comName");
									associateEmpBasicObj.setDataName(parmValuew[k][i]);
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								else if(paramList.get(k).toString().equalsIgnoreCase("name"))
								{
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("empName");
									associateEmpBasicObj.setDataName(parmValuew[k][i]);
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								else if(paramList.get(k).toString().equalsIgnoreCase("birthday"))
								{
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("dob");
									associateEmpBasicObj.setDataName(parmValuew[k][i]);
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								else if(paramList.get(k).toString().equalsIgnoreCase("emailOfficial"))
								{
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("emailIdOne");
									associateEmpBasicObj.setDataName(parmValuew[k][i]);
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								else if(paramList.get(k).toString().equalsIgnoreCase("department"))
								{
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("deptname");
									associateEmpBasicObj.setDataName(new AssociateHelper1().fetchAsociateDept());
									System.out.println("parmValuew[k][i]>>>>>deptname 18>>>"+parmValuew[k][i]);
									System.out.println("new ClientHelper3().fetchClientDept()>>>"+new ClientHelper3().fetchClientDept());
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								else if(paramList.get(k).toString().equalsIgnoreCase("designation"))
								{
									associateEmpBasicObj = new InsertDataTable();
									associateEmpBasicObj.setColName("designation");
									associateEmpBasicObj.setDataName(parmValuew[k][i]);
									insertAssociateEmpBasic.add(associateEmpBasicObj);
								}
								
							}
						}
					}
					if (valueSelect)
					{
						ob = new InsertDataTable();
						ob.setColName("associateName");
						// System.out.println("associateName  " + associateName);
						ob.setDataName(associateName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(cId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						
						//For emp_basic
						associateEmpBasicObj = new InsertDataTable();
						associateEmpBasicObj.setColName("associateName");
						associateEmpBasicObj.setDataName(associateName);
						insertAssociateEmpBasic.add(associateEmpBasicObj);

						associateEmpBasicObj = new InsertDataTable();
						associateEmpBasicObj.setColName("groupId");
						associateEmpBasicObj.setDataName(new CommonHelper().fetchEntityGroupId(EntityType.ASSOCIATE));
						insertAssociateEmpBasic.add(associateEmpBasicObj);
						
						associateEmpBasicObj = new InsertDataTable();
						associateEmpBasicObj.setColName("userName");
						associateEmpBasicObj.setDataName(cId);
						insertAssociateEmpBasic.add(associateEmpBasicObj);

						associateEmpBasicObj = new InsertDataTable();
						associateEmpBasicObj.setColName("date");
						associateEmpBasicObj.setDataName(DateUtil.getCurrentDateUSFormat());
						insertAssociateEmpBasic.add(associateEmpBasicObj);

						associateEmpBasicObj = new InsertDataTable();
						associateEmpBasicObj.setColName("time");
						associateEmpBasicObj.setDataName(DateUtil.getCurrentTime());
						insertAssociateEmpBasic.add(associateEmpBasicObj);
						
						status = cbt.insertIntoTable("associate_contact_data", insertData, connectionSpace);
						associate_status = cbt.insertIntoTable("employee_basic", insertAssociateEmpBasic, connectionSpace);
					}

				}
				if (status)
				{
					addActionMessage("Associate contact saved successfully");
					returnString = SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
					returnString = SUCCESS;
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: addContacts of class " + getClass(), e);
			e.printStackTrace();
		}
		return returnString;
	}

	/*
	 * Anoop, 22-7-2013 Create associate grid page
	 */
	public String beforeAssociateView()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			setAssociateViewProp();
			setAssociateContactViewProp();

			/*
			 * if (isExistingAssociate.equalsIgnoreCase("0")) {
			 * session.put("isExistingAssociate", "0"); } else if
			 * (isExistingAssociate.equalsIgnoreCase("1")) {
			 * session.put("isExistingAssociate", "1"); } else if
			 * (isExistingAssociate.equalsIgnoreCase("2")) {
			 * session.put("isExistingAssociate", "2"); }
			 */
			ClientHelper3 ch3 = new ClientHelper3();
			industryList = new LeadHelper().fetchIndustryList(connectionSpace);
			sourceList = new LeadHelper().fetchSourceNameList(connectionSpace);
			locationList = new LeadHelper().fetchLocationList(connectionSpace);
			associateCategoryMap = new LinkedHashMap<String, String>();
			associateCategoryMap = new ClientHelper3().fetchAssociateCategory();
			associateTypeMap = new LinkedHashMap<String, String>();
			associateTypeMap = new ClientHelper3().fetchAssociateType();
			/*
			 * if (convertToClient.equalsIgnoreCase("1")) {
			 * session.put("convertToClient", "1"); } else {
			 * session.put("convertToClient", "0"); }
			 */

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: beforeAssociateView of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setAssociateViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			/*
			 * gpv = new GridDataPropertyView(); gpv.setColomnName("associateName");
			 * gpv.setHeadingName("History"); gpv.setEditable("false");
			 * gpv.setSearch("false"); gpv.setHideOrShow("false");
			 * gpv.setFormatter("historyLink"); gpv.setAlign("left");
			 * masterViewProp.add(gpv);
			 */
			List<String> listDataToShow = new AssociateHelper().getAssociateBasicDataToShow();
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_basic_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("associateName"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("contactNo"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("webAddress"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("location"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("associateRating"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("weightage"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("associateType"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("associateCategory"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("industry"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					masterViewProp.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: setAssociateViewProp of class " + getClass(), e);
			e.printStackTrace();
		}
	}

	private void setAssociateContactViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewPropForContact.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_contact_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setAlign(gdp.getAlign());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				if (gdp.getFormatter() != null && !gdp.getFormatter().equalsIgnoreCase(""))
				{
					gpv.setFormatter(gdp.getFormatter());
				}
				masterViewPropForContact.add(gpv);
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: setAssociateContactViewProp of class " + getClass(), e);
			e.printStackTrace();
		}
	}

	/*
	 * Sanjiv, 22-7-2013 Fetch associate data
	 */

	public String viewAssociateGrid()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonHelper ch = new CommonHelper();
			String cIdAll = ch.getHierarchyContactIdByEmpId(empId);

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			if (associateStatus != null && !associateStatus.equalsIgnoreCase("0") && !associateStatus.equalsIgnoreCase(""))
			{
				query1.append(" select count(*) from associate_basic_data where id in (select distinct(ccd.associateName) from associate_contact_data as ccd, "
						+ "associate_takeaction as cta ,associate_offering_mapping as com where ccd.id = cta.contacId "
						+ "and ccd.associateName = com.associateName and cta.statusId = '" + associateStatus + "' " + "and com.isConverted = 0) ");
			}
			else
			{

				if (isExistingAssociate.equalsIgnoreCase("1"))// for Existing Associate
				// associate
				{
					query1.append("select count(*) from associate_basic_data where id in " + "(select distinct(associateName) from associate_offering_mapping "
							+ "where isConverted = '1') and userName IN(" + cIdAll + ") ");
				}
				else if (isExistingAssociate.equalsIgnoreCase("2")) // for lost
				// Associate
				{
					query1.append("select count(*) from associate_basic_data where id in " + "(select distinct(associateName) from associate_offering_mapping "
							+ "where isConverted = '2') and userName IN(" + cIdAll + ") ");
				}
				else if (isExistingAssociate.equalsIgnoreCase("0")) // for prospective
				// Associate
				{
					query1.append("select count(*) from associate_basic_data where id in " + "(select distinct(associateName) from associate_offering_mapping "
							+ "where isConverted = '0') and userName IN(" + cIdAll + ") ");
				}
			}
			// System.out.println("QUERY: " + query1.toString());
			//System.out.println("isExistingAssociate????????????"+isExistingAssociate
			// );
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			// //System.out.println("dataCount:"+dataCount);

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
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from associate_basic_data as cbd ");

				List fieldNames = Configuration.getColomnList("mapped_associate_configuration", accountID, connectionSpace, "associate_basic_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				List<String> listDataToShow = new AssociateHelper().getAssociateBasicDataToShow();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (!listDataToShow.contains(obdata.toString()))
						{
							it.remove();
							continue;
						}
						if (obdata.toString().equalsIgnoreCase("sourceName"))
						{
							queryTemp.append(" sm.sourceName 'sourceName', ");
							queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceName ");
						}
						else if (obdata.toString().equalsIgnoreCase("industry"))
						{
							queryTemp.append(" ind.industry , ");
							queryEnd.append(" left join industrydatalevel1 as ind on ind.id = cbd.industry ");
						}
						else if (obdata.toString().equalsIgnoreCase("subIndustry"))
						{
							queryTemp.append(" subi.subIndustry , ");
							queryEnd.append(" left join subindustrydatalevel2 as subi on subi.id = cbd.subIndustry ");
						}
						else if (obdata.toString().equalsIgnoreCase("status"))
						{
							queryTemp.append(" cs.statusname 'status', ");
							queryEnd.append(" left join associatestatus as cs on cs.id = cbd.status ");
						}
						else if (obdata.toString().equalsIgnoreCase("location"))
						{
							queryTemp.append(" loc.name 'location', ");
							queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						}
						else if (obdata.toString().equalsIgnoreCase("acManager"))
						{
							queryTemp.append(" cbd1.empName 'acManager', ");
							queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						}
						else if (obdata.toString().equalsIgnoreCase("state"))
						{
							queryTemp.append(" stt.stateName 'state', ");
							queryEnd.append(" left join state_data as stt on stt.id = cbd.state ");
						}
						else if (obdata.toString().equalsIgnoreCase("associateType"))
						{
							queryTemp.append(" at.associateType 'associateType', ");
							queryEnd.append(" left join associatetype as at on at.id = cbd.associateType ");
						}
						else if (obdata.toString().equalsIgnoreCase("associateSubType"))
						{
							queryTemp.append(" ast.associateSubType 'associateSubType', ");
							queryEnd.append(" left join associatesubtype as ast on ast.id = cbd.associateSubType ");
						}
						else if (obdata.toString().equalsIgnoreCase("associateCategory"))
						{
							queryTemp.append(" ac.associate_category 'associateCategory', ");
							queryEnd.append(" left join associatecategory as ac on ac.id = cbd.associateCategory ");
						}
						else
						{
							queryTemp.append("cbd." + obdata.toString() + ",");
						}
					}

				}

				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" cbd.userName IN(");
				query.append(cIdAll);
				query.append(") ");

				if (industry != null && !industry.equals("-1"))
				{
					query.append("and cbd.industry = '");
					query.append(industry);
					query.append("' ");
				}
				if (subIndustry != null && !subIndustry.equals("-1"))
				{
					query.append("and subi.subIndustry = '");
					query.append(subIndustry);
					query.append("' ");
				}
				if (starRating != null && !starRating.equals("-1"))
				{
					query.append("and cbd.associateRating = '");
					query.append(starRating);
					query.append("' ");
				}
				if (sourceName != null && !sourceName.equals("-1"))
				{
					query.append("and cbd.sourceName = '");
					query.append(sourceName);
					query.append("' ");
				}
				if (location != null && !location.equals("-1"))
				{
					query.append("and cbd.location = '");
					query.append(location);
					query.append("' ");
				}
				if (associateCategory != null && !associateCategory.equals("-1"))
				{
					query.append("and cbd.associateCategory = '");
					query.append(associateCategory);
					query.append("' ");
				}
				if (associateType != null && !associateType.equals("-1"))
				{
					query.append("and cbd.associateType = '");
					query.append(associateType);
					query.append("' ");
				}
				if (associate_Name != null && !associate_Name.equals(""))
				{
					query.append("and cbd.associateName like '");
					query.append(associate_Name);
					query.append("%' ");
				}
				query.append(" and ");

				if (associateStatus != null && !associateStatus.equalsIgnoreCase("0") && !associateStatus.equalsIgnoreCase(""))
				{
					query.append(" cbd.id in (select distinct(ccd.associateName) from associate_contact_data as ccd, "
							+ "associate_takeaction as cta ,associate_offering_mapping as com where ccd.id = cta.contacId "
							+ "and ccd.associateName = com.associateName and cta.statusId = '" + associateStatus + "' " + "and com.isConverted = 0) ");
				}
				else
				{
					if (isExistingAssociate.equalsIgnoreCase("1"))
					{

						query.append(" cbd.id in (select distinct(associateName) from associate_offering_mapping where isConverted = '1')");
					}
					else if (isExistingAssociate.equalsIgnoreCase("2"))
					{
						query.append(" cbd.id in (select distinct(associateName) from associate_offering_mapping where isConverted = '2')");
					}
					else if (isExistingAssociate.equalsIgnoreCase("0"))
					{
						query.append(" cbd.id in (select distinct(associateName) from associate_offering_mapping where isConverted = '0')");
					}
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceName"))
					{
						setSearchField(" sm.sourceName ");
					}
					else if (getSearchField().toString().equalsIgnoreCase("status"))
					{
						setSearchField(" cs.statusname ");
					}
					else if (getSearchField().toString().equalsIgnoreCase("location"))
					{
						setSearchField(" loc.name ");
					}
					else if (getSearchField().toString().equalsIgnoreCase("acManager"))
					{
						setSearchField(" cbd1.empName ");
					}
					else
					{
						setSearchField(" cbd." + getSearchField());
					}

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

				// uncomment sorting order of grid and remove order by cbd.associateName
				
				/*if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by " + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by " + getSidx() + " DESC");
					}
				}*/

				query.append(" order by cbd.associateName limit " + from + "," + to);
				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "associate_basic_data", connectionSpace);

				System.out.println("query:::" + query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						String associateName = "";
						String associateId = "";
						double weightage = 0;
						ClientHelper2 ch2 = new ClientHelper2();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							
							if (obdata[k] == null || obdata[k].toString().equalsIgnoreCase("")) {
								obdata[k] = fieldNames.get(k).toString().equalsIgnoreCase("weightage") ? "0.0": "NA";
							}
							if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase(""))
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									associateId = obdata[k].toString();
									//System.out.println(">>>>>>associateId>>"+associateId);
								}
								else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("associateName"))
									{
										associateName = obdata[k] == null ? "NA" : obdata[k].toString();
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("weightage")) {
										try {
											// calculate weightage
											weightage = ch2.calculateOverallWeightage(cbt, associateId,EntityType.ASSOCIATE);
											obdata[k] = weightage;
											//System.out.println("in calculate Wtg>>>>>>>>>>>.");
											/* type = obdata[k].toString(); */
										} catch (Exception e) {

										}
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("date")) one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k]
											.toString()));
									else one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
							else one.put(fieldNames.get(k).toString(), "NA");

						}

						one.put("history", associateName);
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: setAssociateContactViewProp of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String viewAssociateContactGrid()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from associate_contact_data where associateName = " + id);
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
				List fieldNames = Configuration.getColomnList("mapped_associate_configuration", accountID, connectionSpace, "associate_contact_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata.toString().equalsIgnoreCase("associateName"))
					{
						if (i < fieldNames.size() - 1) query.append("abd.associateName,");
						else query.append("abd.associateName");
					}
					else if (obdata.toString().equalsIgnoreCase("department"))
					{
						if (i < fieldNames.size() - 1) query.append("dept.deptName 'dName',");
						else query.append("dept.deptName 'dName'");
					}
					else
					{
						if (i < fieldNames.size() - 1) query.append("acd." + obdata.toString() + ",");
						else query.append("acd." + obdata.toString());
					}
					i++;

				}

				query.append(" from associate_contact_data as acd inner join associate_basic_data as abd on acd.associateName = abd.id");
				query.append(" left join department as dept on dept.id = acd.department");
				query.append(" where acd.associateName = " + id);
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and ");
						query.append(" acd." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and ");
						query.append(" acd." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and ");
						query.append(" acd." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and ");
						query.append(" acd." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and ");
						query.append(" acd." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by acd." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by acd." + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "associate_contact_data", connectionSpace);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] == null || obdata[k].toString().equalsIgnoreCase(""))
							{
								obdata[k] = "NA";
							}
							if (k == 0)
							{
								one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
							}
							else
							{
								if (fieldNames.get(k).toString().equalsIgnoreCase("date") || fieldNames.get(k).toString().equalsIgnoreCase("birthday")
										|| fieldNames.get(k).toString().equalsIgnoreCase("anniversary")) one.put(fieldNames.get(k).toString(), DateUtil
										.convertDateToIndianFormat(obdata[k].toString()));
								else one.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
						}
						Listhb.add(one);
					}

					setMasterViewListForContact(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: viewAssociateContactGrid of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String viewModifyAssociate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			if (getOper().equalsIgnoreCase("edit"))
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
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("associate_basic_data", wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("associate_basic_data", "id", condtIds.toString(), connectionSpace);
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: viewAssociateContactGrid of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 23-7-2013 Modify associate contact
	 */
	public String viewModifyAssociateContact()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			if (getOper().equalsIgnoreCase("edit"))
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
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("associate_contact_data", wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("associate_contact_data", "id", condtIds.toString(), connectionSpace);
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: viewModifyAssociateContact of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String viewAssociateFullHistory()
	{
		//System.out.println("viewAssociateFullHistory");
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			/* isExistingAssociate = session.get("isExistingAssociate").toString(); */
			if (isExistingAssociate != null && isExistingAssociate.equalsIgnoreCase("2")) convertToAssociate = "1";
			/*
			 * show associate basic info on full view
			 */
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_basic_configuration");
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from associate_basic_data as cbd ");
			List<String> names = new ArrayList<String>();

			int ii = 0;
			for (GridDataPropertyView gdp : returnResult)
			{
				// System.out.println(">>>>>>>>>>>>>>>>>> "+gdp.getColomnName());
				if (ii < returnResult.size() - 1)
				{
					if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
					{
						query.append(" sm.sourceName 'sourceName', ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceName ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("industry"))
					{
						query.append(" ind.industry 'industry', ");
						queryEnd.append(" left join industrydatalevel1 as ind on ind.id = cbd.industry ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
					{
						query.append(" subi.subIndustry 'subIndustry', ");
						queryEnd.append(" left join subindustrydatalevel2 as subi on subi.id = cbd.subIndustry ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusname 'status', ");
						queryEnd.append(" left join associatestatus as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location', ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager', ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("state"))
					{
						query.append(" stt.stateName 'state', ");
						queryEnd.append(" left join state_data as stt on stt.id = cbd.state ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateType"))
					{
						query.append(" at.associateType 'associateType', ");
						queryEnd.append(" left join associatetype as at on at.id = cbd.associateType ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateCategory"))
					{
						query.append(" at1.associate_category 'associateCategory', ");
						queryEnd.append(" left join associatecategory as at1 on at1.id = cbd.associateCategory ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
					{
						query.append(" sst1.statusname 'targetAchieved', ");
						queryEnd.append(" left join associatestatus as sst1 on sst1.id = cbd.targetAchieved ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateSubType"))
					{
						query.append(" sst2.associateSubType 'associateSubType', ");
						queryEnd.append(" left join associatesubtype as sst2 on sst2.id = cbd.associateSubType ");
						names.add(gdp.getHeadingName());
					}
					else
					{
						query.append("cbd." + gdp.getColomnName() + ",");
						names.add(gdp.getHeadingName());
					}

				}
				else
				{
					if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
					{
						query.append(" sm.sourceName 'sourceName' ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceName ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("industry"))
					{
						query.append(" ind.industry 'industry' ");
						queryEnd.append(" left join industrydatalevel1 as ind on ind.id = cbd.industry ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
					{
						query.append(" subi.subIndustry 'subIndustry' ");
						queryEnd.append(" left join subindustrydatalevel2 as subi on subi.id = cbd.subIndustry ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusname 'status' ");
						queryEnd.append(" left join associatestatus as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location' ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager' ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("state"))
					{
						query.append(" stt.stateName 'state' ");
						queryEnd.append(" left join state_data as stt on stt.id = cbd.state ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateType"))
					{
						query.append(" at.associateType 'associateType' ");
						queryEnd.append(" left join associatetype as at on at.id = cbd.associateType ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateCategory"))
					{
						query.append(" at1.associate_category 'associateCategory' ");
						queryEnd.append(" left join associatecategory as at1 on at1.id = cbd.associateCategory ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
					{
						query.append(" sst1.statusname 'targetAchieved' ");
						queryEnd.append(" left join associatestatus as sst1 on sst1.id = cbd.targetAchieved ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateSubType"))
					{
						query.append(" sst2.associateSubType 'associateSubType' ");
						queryEnd.append(" left join associatesubtype as sst2 on sst2.id = cbd.associateSubType ");
						names.add(gdp.getHeadingName());
					}
					else
					{
						query.append("cbd." + gdp.getColomnName());
						names.add(gdp.getHeadingName());
					}

				}
				ii++;
			}

			query.append(" " + queryEnd.toString() + " where cbd.id = " + id);
			System.out.println("query>"+query);

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			Map<String, String> one = null;

			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					one = new HashMap<String, String>();

					if (obdata != null)
					{
						CommonHelper ch = new CommonHelper();
						for (int j = 0; j < returnResult.size(); j++)
						{
							if (obdata[j] != null)
							{
								if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("date"))
								{
									obdata[j] = DateUtil.convertDateToIndianFormat(obdata[j].toString());
								}
								else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("userName"))
								{
									obdata[j] = ch.fetchEmpDetailsByContactId(obdata[j].toString())[0];
								}
								else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("time"))
								{
									obdata[j] = CommonHelper.getFieldValue(obdata[j]).substring(0, CommonHelper.getFieldValue(obdata[j]).lastIndexOf(":"));
								}
								else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("associateName"))
								{
									associateName = CommonHelper.getFieldValue(obdata[j]);
								}
								one.put(names.get(j), obdata[j].toString());
							}
						}
					}
				}
				basicParams = one;
			}

			/*
			 * show associate contact info on full view
			 */
			List<GridDataPropertyView> returnResult1 = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_contact_configuration");
			StringBuilder query1 = new StringBuilder("");
			query1.append("select ");
			namesContact = new ArrayList<String>();
			for (Iterator<GridDataPropertyView> itr = returnResult1.iterator(); itr.hasNext();)
			{
				GridDataPropertyView gdp = itr.next();
				if (gdp.getColomnName().equalsIgnoreCase("userName") || gdp.getColomnName().equalsIgnoreCase("date") || gdp.getColomnName().equalsIgnoreCase("time")
						|| gdp.getColomnName().equalsIgnoreCase("associateName"))
				{
					itr.remove();
				}
				else
				{
					if (gdp.getColomnName().equalsIgnoreCase("department"))
					{
						query1.append("dept.deptName 'dName',");
					}
					else
					{
						query1.append(gdp.getColomnName() + ",");
					}
					namesContact.add(gdp.getHeadingName());
				}
			}
			String query1New = query1.toString().substring(0, query1.toString().lastIndexOf(","));
			query1New += " from associate_contact_data as acd left join department as dept on dept.id = acd.department where associateName=" + id;

			List data1 = cbt.executeAllSelectQuery(query1New.toString(), connectionSpace);
			// System.out.println("query for full history >>>  " + query1New.toString());
			List<String> cList = null;
			Map<String, String> one1 = null;

			if (data1 != null)
			{
				int i = 0;
				for (Iterator it = data1.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								if (returnResult1.get(j).getColomnName().equalsIgnoreCase("date") || returnResult1.get(j).getColomnName().equalsIgnoreCase("birthday")
										|| returnResult1.get(j).getColomnName().equalsIgnoreCase("anniversary")) c1.setName(DateUtil
										.convertDateToIndianFormat(obdata[j].toString()));
								else c1.setName(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentContact.add(p1);
				}
			}

			/*
			 * Show client offering data
			 */
			int level = 0;
			offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			namesOffering = new ArrayList<String>();
			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				namesOffering.add("ID");
				namesOffering.add("Is Converted");

				level = Integer.parseInt(oLevels[0]);
				for (int i = 1; i < oLevels.length; i++)
				{
					if (oLevels[i] != null && !oLevels[i].equalsIgnoreCase("")) namesOffering.add(oLevels[i]);
				}
			}

			// System.out.println("namesOffering:"+namesOffering);

			String tableName = "", colName = "";
			StringBuffer queryOffering = new StringBuffer();

			if (level == 1)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname from offeringlevel1 as t1, associate_offering_mapping as com where t1.id = com.offeringId and com.associateName = ");
			}
			if (level == 2)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname from offeringlevel1 as t1, offeringlevel2 as t2, associate_offering_mapping as com where t2.verticalname=t1.id and t2.id = com.offeringId and com.associateName = ");
			}
			if (level == 3)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, associate_offering_mapping as com where t3.offeringname=t2.id and t2.verticalname=t1.id and t3.id = com.offeringId and com.associateName = ");
			}
			if (level == 4)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, associate_offering_mapping as com where t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t4.id = com.offeringId and com.associateName = ");
			}
			if (level == 5)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname, t5.subvariantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, offeringlevel5 as t5, associate_offering_mapping as com where t5.variantname=t4.id and t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t5.id = com.offeringId and com.associateName = ");
			}

			queryOffering.append(id);

			if (isExistingAssociate.equalsIgnoreCase("0"))
			{
				queryOffering.append(" and com.isConverted = 0 ");
			}
			else if (isExistingAssociate.equalsIgnoreCase("1"))
			{
				queryOffering.append(" and com.isConverted = 1 ");
			}
			else if (isExistingAssociate.equalsIgnoreCase("2"))
			{
				queryOffering.append(" and com.isConverted = 2 ");
			}

			// System.out.println("queryOffering:"+queryOffering);

			List data2 = cbt.executeAllSelectQuery(queryOffering.toString(), connectionSpace);

			if (data2 != null)
			{
				int i = 0;
				for (Iterator it = data2.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
								// list.add(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
								// list.add("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentOffering.add(p1);
				}
			}

			/*
			 * Associate Action taken
			 */

			/*
			 * int level = 0; CommonOperInterface cbt=new
			 * CommonConFactory().createInterface(); offeringLevel =
			 * "5#Vertical#Offering#Sub-Offering#Variant#Sub-variant#"
			 * ;//session.get("offeringLevel").toString(); String[] oLevels = null;
			 */
			namesTakeaction = new ArrayList<String>();
			namesTakeaction.add("MOM");
			namesTakeaction.add("Contact No");
			namesTakeaction.add("Name");
			namesTakeaction.add("Offering");
			namesTakeaction.add("Status");
			namesTakeaction.add("Max Date Time");
			namesTakeaction.add("Comment");
			namesTakeaction.add("Is Finished");
			namesTakeaction.add("Finished Date");
			namesTakeaction.add("Finished Time");

			level = 0;
			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			// String tableName = "", colName = "";
			tableName = "";
			colName = "";

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

			StringBuffer queryTA = new StringBuffer();
			queryTA.append(" select cta.id,ccd.contactNum,ccd.name, co.");
			queryTA.append(colName);
			queryTA.append(" , cs.statusName, cta.maxDateTime, cta.comment, ");
			queryTA.append(" cta.isFinished, cta.finishedDate, cta.finishedTime");
			queryTA.append(" from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, ");
			queryTA.append(tableName);
			queryTA.append(" as co ");
			queryTA.append(" where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id ");
			queryTA.append(" and ccd.associateName = ");
			queryTA.append(id);

			// System.out.println("queryTA: " + queryTA.toString());

			List data3 = cbt.executeAllSelectQuery(queryTA.toString(), connectionSpace);

			if (data3 != null)
			{
				String[] dateVal = null;
				int i = 0;
				for (Iterator it = data3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								// System.out.println("obdata["+j+"]:"+obdata[j]);
								if (j == 5)
								{
									dateVal = obdata[j].toString().split(" "); // yyyy
									// -
									// mm
									// -
									// dd
									// hh
									// :
									// mm
									c1.setName(DateUtil.convertDateToIndianFormat(dateVal[0].trim()) + " " + dateVal[1]);
								}
								else if (j == 8)
								{
									c1.setName(DateUtil.convertDateToIndianFormat(obdata[j].toString()));
								}
								else
								{
									c1.setName(obdata[j].toString());
								}
							}
							else
							{
								c1.setName("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}
			}

			/*
			 * Accordion to show
			 */
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_associate_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("associate_basic_configuration"))
					{
						associateForBasicDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("associate_offering_configuration"))
					{
						associateForOfferingDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("associate_contact_configuration"))
					{
						associateForContacDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("client_attachment_configuration"))
					{
						associateForAttachmentDetails = 1;
					}
				}
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: viewAssociateFullHistory of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String viewASooActionDash()
	{

		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			// isExistingAssociate =
			// session.get("isExistingAssociate").toString();
			if (isExistingAssociate != null && isExistingAssociate.equalsIgnoreCase("2")) convertToAssociate = "1";
			/*
			 * show associate basic info on full view
			 */
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_basic_configuration");
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from associate_basic_data as cbd ");
			List<String> names = new ArrayList<String>();

			int ii = 0;
			for (GridDataPropertyView gdp : returnResult)
			{
				if (ii < returnResult.size() - 1)
				{
					if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
					{
						query.append(" sm.sourceName 'sourceName', ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceName ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusname 'status', ");
						queryEnd.append(" left join associatestatus as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location', ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager', ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("state"))
					{
						query.append(" stt.stateName 'state', ");
						queryEnd.append(" left join state_data as stt on stt.id = cbd.state ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateType"))
					{
						query.append(" at.associateType 'associateType', ");
						queryEnd.append(" left join associatetype as at on at.id = cbd.associateType ");
						names.add(gdp.getHeadingName());
					}
					else
					{
						query.append("cbd." + gdp.getColomnName() + ",");
						names.add(gdp.getHeadingName());
					}

				}
				else
				{
					if (gdp.getColomnName().equalsIgnoreCase("sourceName"))
					{
						query.append(" sm.sourceName 'sourceName' ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceName ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusname 'status' ");
						queryEnd.append(" left join associatestatus as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location' ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager' ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("state"))
					{
						query.append(" stt.stateName 'state' ");
						queryEnd.append(" left join state_data as stt on stt.id = cbd.state ");
						names.add(gdp.getHeadingName());
					}
					else if (gdp.getColomnName().equalsIgnoreCase("associateType"))
					{
						query.append(" at.associateType 'associateType' ");
						queryEnd.append(" left join associatetype as at on at.id = cbd.associateType ");
						names.add(gdp.getHeadingName());
					}
					else
					{
						query.append("cbd." + gdp.getColomnName());
						names.add(gdp.getHeadingName());
					}

				}
				ii++;
			}

			query.append(" " + queryEnd.toString() + " where cbd.id = " + id);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			Map<String, String> one = null;

			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					one = new HashMap<String, String>();
					String type = "";
					boolean flag = false;

					if (obdata != null)
					{
						for (int j = 0; j < returnResult.size(); j++)
						{
							if (obdata[j] != null)
							{
								one.put(names.get(j), obdata[j].toString());
							}
						}
					}
				}
				basicParams = one;
			}

			/*
			 * show associate contact info on full view
			 */
			List<GridDataPropertyView> returnResult1 = Configuration.getConfigurationData("mapped_associate_configuration", accountID, connectionSpace, "", 0,
					"table_name", "associate_contact_configuration");
			StringBuilder query1 = new StringBuilder("");
			query1.append("select ");
			namesContact = new ArrayList<String>();
			for (GridDataPropertyView gdp : returnResult1)
			{
				query1.append(gdp.getColomnName() + ",");
				namesContact.add(gdp.getHeadingName());
			}

			String query1New = query1.toString().substring(0, query1.toString().lastIndexOf(","));
			query1New += " from associate_contact_data where associateName=" + id;

			List data1 = cbt.executeAllSelectQuery(query1New.toString(), connectionSpace);
			if (data1 != null)
			{
				int i = 0;
				for (Iterator it = data1.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentContact.add(p1);
				}
			}

			/*
			 * Show client offering data
			 */
			int level = 0;
			offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			namesOffering = new ArrayList<String>();
			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				namesOffering.add("ID");
				namesOffering.add("Is Converted");

				level = Integer.parseInt(oLevels[0]);
				for (int i = 1; i < oLevels.length; i++)
				{
					if (oLevels[i] != null && !oLevels[i].equalsIgnoreCase("")) namesOffering.add(oLevels[i]);
				}
			}

			String tableName = "", colName = "";
			StringBuffer queryOffering = new StringBuffer();

			if (level == 1)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname from offeringlevel1 as t1, associate_offering_mapping as com where t1.id = com.offeringId and com.associateName = ");
			}
			if (level == 2)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname from offeringlevel1 as t1, offeringlevel2 as t2, associate_offering_mapping as com where t2.verticalname=t1.id and t2.id = com.offeringId and com.associateName = ");
			}
			if (level == 3)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, associate_offering_mapping as com where t3.offeringname=t2.id and t2.verticalname=t1.id and t3.id = com.offeringId and com.associateName = ");
			}
			if (level == 4)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, associate_offering_mapping as com where t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t4.id = com.offeringId and com.associateName = ");
			}
			if (level == 5)
			{
				queryOffering
						.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname, t5.subvariantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, offeringlevel5 as t5, associate_offering_mapping as com where t5.variantname=t4.id and t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t5.id = com.offeringId and com.associateName = ");
			}

			queryOffering.append(id);

			List data2 = cbt.executeAllSelectQuery(queryOffering.toString(), connectionSpace);
			if (data2 != null)
			{
				int i = 0;
				for (Iterator it = data2.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentOffering.add(p1);
				}
			}

			/*
			 * Associate Action taken
			 */

			namesTakeaction = new ArrayList<String>();
			namesTakeaction.add("ID");
			namesTakeaction.add("Contact No");
			namesTakeaction.add("Name");
			namesTakeaction.add("Offering");
			namesTakeaction.add("Status");
			namesTakeaction.add("Max Date Time");
			namesTakeaction.add("Comment");
			namesTakeaction.add("Is Finished");
			namesTakeaction.add("Finished Date");
			namesTakeaction.add("Finished Time");

			level = 0;
			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			tableName = "";
			colName = "";

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

			StringBuffer queryTA = new StringBuffer();
			queryTA.append(" select cta.id,ccd.contactNo,ccd.name, co.");
			queryTA.append(colName);
			queryTA.append(" , cs.statusName, cta.maxDateTime, cta.comment, ");
			queryTA.append(" cta.isFinished, cta.finishedDate, cta.finishedTime");
			queryTA.append(" from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, ");
			queryTA.append(tableName);
			queryTA.append(" as co ");
			queryTA.append(" where ccd.id = cta.contacId and cta.isFinished='0'  and cta.statusId = cs.id and cta.offeringId = co.id ");
			queryTA.append(" and ccd.associateName in (select cbd.id from associate_contact_data as cbd)");

			List data3 = cbt.executeAllSelectQuery(queryTA.toString(), connectionSpace);

			if (data3 != null)
			{
				int i = 0;
				for (Iterator it = data3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}
			}

			/*
			 * Accordion to show
			 */
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_associate_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("associate_basic_configuration"))
					{
						associateForBasicDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("associate_offering_configuration"))
					{
						associateForOfferingDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("associate_contact_configuration"))
					{
						associateForContacDetails = 1;
					}
					else if (obdata.toString().equalsIgnoreCase("client_attachment_configuration"))
					{
						associateForAttachmentDetails = 1;
					}
				}
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: viewAssociateFullHistory of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	/*
	 * Anoop, 24-7-2013 Take actin on associate
	 */

	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public String beforeContactTakeAction()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			CommonHelper ch = new CommonHelper();
			String[] tempOffering = ch.getOfferingName();

			StringBuilder query = new StringBuilder();
			query.append("select co.id, co.");
			query.append(tempOffering[0]);
			query.append(" from ");
			query.append(tempOffering[1]);
			query.append(" as co, associate_offering_mapping as com where co.id = com.offeringId and com.offeringLevelId = '");
			query.append(tempOffering[2]);
			query.append("' and com.associateName = '");
			query.append(id);
			query.append("' and com.isConverted = '0' order by co.subofferingname ");

			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			// Build offering map
			offeringList = CommonHelper.convertListToMap(data, false);

			StringBuilder queryForContact = new StringBuilder();
			queryForContact.append("select id, name from associate_contact_data where associateName = '");
			queryForContact.append(id);
			queryForContact.append("' order by name ");
			List dataForContact = cbt.executeAllSelectQuery(queryForContact.toString(), connectionSpace);
			// Build contact person map
			contactPersonMap = CommonHelper.convertListToMap(dataForContact, false);

			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			// Build employee map
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);

			StringBuffer query1 = new StringBuffer();
			query1.append("select id,statusname from associatestatus order by statusname");
			data = null;
			data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			associateStatusList = CommonHelper.convertListToMap(data, false);
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: viewAssociateFullHistory of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 25-7-2013 Take action on client
	 */
	public String takeActionOnAssociate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			CommonOperInterface coi = new CommonConFactory().createInterface();
			// create client_takeaction table
			AssociateHelper ch = new AssociateHelper();
			ch.createTableAssociateTakeAction(connectionSpace);
			ch.createTableAssociateTakeActionMappedEmployee(connectionSpace);
			//System.out.println("contactId:" + contactId);
			//System.out.println("empId:" + empId);
			//System.out.println("offeringId:" + offeringId);

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			List<InsertDataTable> insertDataME = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			String[] contactIdArray = CommonHelper.getCommaSeparatedToArray(contactId);
			String[] empIdArray = CommonHelper.getCommaSeparatedToArray(empId);
			String[] dateVal = date.split(" ");// dd-mm-yyyy hh:mm
			date = DateUtil.convertDateToUSFormat(dateVal[0].trim()) + " " + dateVal[1];
			//System.out.println("date:" + date);
			int count = 0;

			for (String contact : contactIdArray)
			{
				ob = new InsertDataTable();
				ob.setColName("contacId");
				ob.setDataName(contact);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("offeringId");
				ob.setDataName(offeringId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("statusId");
				ob.setDataName(status);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("maxDateTime");
				ob.setDataName(date);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("comment");
				ob.setDataName(comments);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("isFinished");
				ob.setDataName("0");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(cId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				boolean statusFlag = coi.insertIntoTable("associate_takeaction", insertData, connectionSpace);
				insertData.clear();

				if (statusFlag)
				{
					// Fill auto KPI for 'Total Calls'
					LeadActionControlDao lacd = new LeadActionControlDao();
					lacd.insertInToKPIAutofillTable(empId, "2", userName, connectionSpace, id, "Prospective Associate");
					if (associateStatus.contains("Intro Mail"))
					{
						// Fill auto KPI for 'Intro Mail'
						lacd.insertInToKPIAutofillTable(empId, "4", userName, connectionSpace, id, "Prospective Associate");
					}
					else if (associateStatus.contains("Meeting"))
					{
						// Fill auto KPI for 'Meeting Generation'
						lacd.insertInToKPIAutofillTable(empId, "5", userName, connectionSpace, id, "Prospective Associate");
					}

					count++;
					StringBuilder querySelect = new StringBuilder();
					querySelect.append("select id from associate_takeaction where contacId = '");
					querySelect.append(contact);
					querySelect.append("' and offeringId = '");
					querySelect.append(offeringId);
					querySelect.append("' and maxDateTime = '");
					querySelect.append(date);
					querySelect.append("' ");
					List list = coi.executeAllSelectQuery(querySelect.toString(), connectionSpace);
					if (list != null && !list.isEmpty())
					{
						String actionId = list.get(0).toString();
						//System.out.println("actionId:" + actionId);
						if (actionId != null && empIdArray != null && empIdArray.length > 0)
						{
							for (String emp : empIdArray)
							{
								InsertDataTable ob1 = null;
								ob1 = new InsertDataTable();
								ob1.setColName("associate_takeaction_id");
								ob1.setDataName(actionId);
								insertDataME.add(ob1);

								ob1 = new InsertDataTable();
								ob1.setColName("empId");
								ob1.setDataName(emp);
								insertDataME.add(ob1);

								boolean statusFlagME = coi.insertIntoTable("associate_takeaction_mapped_emp", insertDataME, connectionSpace);
								insertDataME.clear();
							}
						}
					}
				}
			}

			if (count > 0) addActionMessage("Action Taken Successfully.");
			else addActionMessage("Error: Action Incomplete !");
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: takeActionOnAssociate of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 25-7-2013 Convert an associate to existing for an offering
	 */

	public String convertAssociateForOffering()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("isConverted", 1);
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("associate_offering_mapping", wherClause, condtnBlock, connectionSpace);

			// For Dynamically creating Table associate_history
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			List<String> columnName = new ArrayList<String>();

			columnName.add("associateName");
			columnName.add("Offering");
			columnName.add("isConverted");
			columnName.add("convertDate");
			columnName.add("convertTime");
			columnName.add("offeringLevel");

			for (String cloumn : columnName)
			{
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname(cloumn);
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn.add(ob1);
			}

			boolean status = cbt.createTable22("associate_history", tableColumn, connectionSpace);

			// for entry in History table
			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("associateName");
			ob.setDataName(tempVariable);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offering");
			StringBuilder query1 = new StringBuilder("");
			query1.append("Select offeringId from associate_offering_mapping where associateName='" + tempVariable + "'");
			List offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ob.setDataName(offeringId.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offeringLevel");
			StringBuilder query2 = new StringBuilder("");
			query2.append("Select offeringLevelId from associate_offering_mapping where associateName='" + tempVariable + "'");
			List offeringLevelId1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			ob.setDataName(offeringLevelId1.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("isConverted");
			ob.setDataName(1);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertDate");
			ob.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertTime");
			ob.setDataName(DateUtil.getCurrentTime());
			insertHistory.add(ob);
			cbt.insertIntoTable("client_history", insertHistory, connectionSpace);

			if (b)
			{
				return SUCCESS;
			}
			else
			{
				return ERROR;
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: convertAssociateForOffering of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}

	}

	/*
	 * Anoop, 25-7-2013 Close associate activity
	 */

	public String finishAssociateActivity()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("isFinished", 1);
			wherClause.put("finishedDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("finishedTime", DateUtil.getCurrentTime());

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("associate_takeaction", wherClause, condtnBlock, connectionSpace);
			if (b)
			{
				return SUCCESS;
			}
			else
			{
				return ERROR;
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: finishAssociateActivity of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
	}

	/*
	 * Anoop, 25-7-2013 Convert associate to lost
	 */

	public String convertToLost()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("isConverted", 2);
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("associate_offering_mapping", wherClause, condtnBlock, connectionSpace);

			// For Dynamically creating Table associate_history
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			List<String> columnName = new ArrayList<String>();

			columnName.add("associateName");
			columnName.add("Offering");
			columnName.add("isConverted");
			columnName.add("convertDate");
			columnName.add("convertTime");
			columnName.add("offeringLevel");

			for (String cloumn : columnName)
			{
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname(cloumn);
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn.add(ob1);
			}

			boolean status = cbt.createTable22("associate_history", tableColumn, connectionSpace);

			// for entry in History table

			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("associateName");
			ob.setDataName(tempVariable);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offering");
			StringBuilder query1 = new StringBuilder("");
			query1.append("Select offeringId from associate_offering_mapping where associateName='" + tempVariable + "'");
			List offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ob.setDataName(offeringId.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offeringLevel");
			StringBuilder query2 = new StringBuilder("");
			query2.append("Select offeringLevelId from associate_offering_mapping where associateName='" + tempVariable + "'");
			List offeringLevelId1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			ob.setDataName(offeringLevelId1.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("isConverted");
			ob.setDataName(1);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertDate");
			ob.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertTime");
			ob.setDataName(DateUtil.getCurrentTime());
			insertHistory.add(ob);
			cbt.insertIntoTable("client_history", insertHistory, connectionSpace);

			if (b)
			{
				return SUCCESS;
			}
			else
			{
				return ERROR;
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: finishAssociateActivity of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}

	}

	/*
	 * Anoop, 25-7-2013 Convert to prospective associate
	 */
	public String convertToAssociate()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("isConverted", 0);
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("associate_offering_mapping", wherClause, condtnBlock, connectionSpace);

			// For Dynamically creating Table associate_history
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			List<String> columnName = new ArrayList<String>();

			columnName.add("associateName");
			columnName.add("Offering");
			columnName.add("isConverted");
			columnName.add("convertDate");
			columnName.add("convertTime");
			columnName.add("offeringLevel");

			for (String cloumn : columnName)
			{
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname(cloumn);
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				tableColumn.add(ob1);
			}

			boolean status = cbt.createTable22("associate_history", tableColumn, connectionSpace);

			// for entry in History table

			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("associateName");
			ob.setDataName(tempVariable);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offering");
			StringBuilder query1 = new StringBuilder("");
			query1.append("Select offeringId from associate_offering_mapping where associateName='" + tempVariable + "'");
			List offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ob.setDataName(offeringId.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offeringLevel");
			StringBuilder query2 = new StringBuilder("");
			query2.append("Select offeringLevelId from associate_offering_mapping where associateName='" + tempVariable + "'");
			List offeringLevelId1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			ob.setDataName(offeringLevelId1.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("isConverted");
			ob.setDataName(1);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertDate");
			ob.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertTime");
			ob.setDataName(DateUtil.getCurrentTime());
			insertHistory.add(ob);
			cbt.insertIntoTable("client_history", insertHistory, connectionSpace);

			if (b)
			{
				return SUCCESS;
			}
			else
			{
				return ERROR;
			}

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method: finishAssociateActivity of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
	}

	public String beforeDashboardView()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				AssociateHelper ah = new AssociateHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				getOfferingTableAndColumnNames();

				// Prospective associate
				pendingAssociate = ah.getProspectiveAssociateCount(cId, connectionSpace);

				// Existing associate
				existingAssociate = ah.getExistingAssociateCount(cId, connectionSpace);

				// Lost associate
				lostAssociate = ah.getLostAssociateCount(cId, connectionSpace);

				// Today's activities
				todayActivity = ah.getTodaysActivityCount(cId, connectionSpace, tableName);

				// Tomorros's activities
				tomorrowActivity = ah.getTomorrowsActivityCount(cId, connectionSpace, tableName);

				// Up coming seven days activities
				nextSevenDaysActivity = ah.getNextSevenDaysActivityCount(cId, connectionSpace, tableName);

				// Associate status
				associateStatusAllList = ah.getAssociateCountByStatus(cId, connectionSpace);

				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	/*
	 * Anoop 12-10-2013
	 */
	public String beforeShowOffWrtAssoPie()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getOfferingTableAndColumnNames();
				AssociateHelper ah = new AssociateHelper();
				// Offering wrt Associate
				offWrtAssoMap = ah.getOfferingWrtAssociateCount(cId, connectionSpace, tableName, colName);

				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;

	}

	/*
	 * Anoop 12-10-2013 Get table name and column name based on configuration
	 */
	public void getOfferingTableAndColumnNames()
	{
		try
		{
			int level = 0;
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

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
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Anoop 11-10-2013
	 */
	public String beforeStatusPie()
	{

		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				AssociateHelper ah = new AssociateHelper();
				// Associate status
				associateStatusAllList = ah.getAssociateCountByStatus(cId, connectionSpace);
				statusMap = new HashMap<String, Integer>();
				for (ArrayList<String> al : associateStatusAllList)
				{
					statusMap.put(al.get(1), Integer.parseInt(al.get(2)));
				}
				returnString = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;

	}

	/*
	 * Anoop 14-10-2013 Show up coming 7 days anniversary
	 */
	public String beforeShowAssociateAnniversaryTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				AssociateHelper ah = new AssociateHelper();
				// Up coming 7 days birthday
				anniversaryList = ah.getUpComingSevenDaysAnniversary(cId, connectionSpace, DateUtil.getCurrentDateUSFormat(), DateUtil.getDateAfterDays(7));
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	/*
	 * Anoop 14-10-2013 Show up coming 7 days birthday
	 */
	public String beforeShowAssociateBirthdayTable()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				AssociateHelper ah = new AssociateHelper();
				// Up coming 7 days birthday
				birthdayList = ah.getUpComingSevenDaysBirthday(cId, connectionSpace, DateUtil.getCurrentDateUSFormat(), DateUtil.getDateAfterDays(7));
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	/*
	 * Anoop 11-10-2013 show activity pie
	 */
	public String beforeActivityPie()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getOfferingTableAndColumnNames();
				// System.out.println(legendShow + "===" + width + "===" + height);
				activity = new HashMap<String, Integer>();
				AssociateHelper ah = new AssociateHelper();
				// Missed activities
				missedActivity = ah.getMissedActivityCount(cId, connectionSpace);
				activity.put("Missed", missedActivity);

				// Today's activities
				todayActivity = ah.getTodaysActivityCount(cId, connectionSpace, tableName);
				activity.put("Today", todayActivity);

				// Tomorros's activities
				tomorrowActivity = ah.getTomorrowsActivityCount(cId, connectionSpace, tableName);
				activity.put("Tomorrow", tomorrowActivity);

				// Up coming seven days activities
				nextSevenDaysActivity = ah.getNextSevenDaysActivityCount(cId, connectionSpace, tableName);
				activity.put("Seven Days", nextSevenDaysActivity);

				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	/**
	 * Anoop 1-10-2013
	 * 
	 * @return Fetch list of upcoming activities
	 */
	public String associateActivities()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			namesTakeaction = new ArrayList<String>();
			namesTakeaction.add("ID");
			namesTakeaction.add("Contact No");
			namesTakeaction.add("Name");
			namesTakeaction.add("Offering");
			namesTakeaction.add("Status");
			namesTakeaction.add("Max Date Time");
			namesTakeaction.add("Comment");
			namesTakeaction.add("Is Finished");
			namesTakeaction.add("Finished Date");
			namesTakeaction.add("Finished Time");

			int level = 0;
			offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;
			String tableName = "", colName = "";
			CommonOperInterface coi = new CommonConFactory().createInterface();

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

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

			String todayDate = DateUtil.currentdatetime(); // yyyy-mm-dd
			// 11:11:11
			todayDate = todayDate.substring(0, todayDate.lastIndexOf(":")); // yyyy
			// -
			// mm
			// -
			// dd
			// 11
			// :
			// 11

			StringBuffer queryTA = new StringBuffer();
			if (activityType.equalsIgnoreCase("todayActivity"))
			{
				queryTA.append("select cta.id,ccd.contactNo,ccd.name, co." + colName + " , cs.statusname, "
						+ "cta.maxDateTime, cta.comment,  cta.isFinished, cta.finishedDate, cta.finishedTime "
						+ "from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, " + tableName + " as co, "
						+ "associate_basic_data as cbd  where ccd.id = cta.contacId and cta.statusId = cs.id "
						+ "and cta.offeringId = co.id  and cbd.id = ccd.associateName and cbd.userName = '" + cId + "' " + "and isFinished = '0' and maxDateTime like '"
						+ DateUtil.getCurrentDateUSFormat() + "%' " + "order by maxDateTime ");
			}
			else if (activityType.equalsIgnoreCase("nextSevenDaysActivities"))
			{
				queryTA.append("select cta.id,ccd.contactNo,ccd.name, co." + colName + " , cs.statusname, "
						+ "cta.maxDateTime, cta.comment,  cta.isFinished, cta.finishedDate, cta.finishedTime "
						+ "from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, " + tableName + " as co, "
						+ "associate_basic_data as cbd  where ccd.id = cta.contacId and cta.statusId = cs.id "
						+ "and cta.offeringId = co.id  and cbd.id = ccd.associateName and cbd.userName = '" + cId + "' " + "and isFinished = '0' and maxDateTime >= '"
						+ todayDate + "' " + "and maxDateTime <= '" + DateUtil.getNextDateAfter(7) + " 23:59' " + "order by maxDateTime ");
			}
			else if (activityType.equalsIgnoreCase("tomorrowActivity"))
			{
				queryTA.append("select cta.id,ccd.contactNo,ccd.name, co." + colName + " , cs.statusname, "
						+ "cta.maxDateTime, cta.comment,  cta.isFinished, cta.finishedDate, cta.finishedTime "
						+ "from associate_contact_data as ccd, associate_takeaction as cta, associatestatus as cs, " + tableName + " as co, "
						+ "associate_basic_data as cbd  where ccd.id = cta.contacId and cta.statusId = cs.id "
						+ "and cta.offeringId = co.id  and cbd.id = ccd.associateName and cbd.userName = '" + cId + "' " + "and isFinished = '0' and maxDateTime like '"
						+ DateUtil.getNextDateAfter(1) + "%' " + "order by maxDateTime ");
			}

			List data3 = coi.executeAllSelectQuery(queryTA.toString(), connectionSpace);

			if (data3 != null)
			{
				String[] dateVal = null;
				int i = 0;
				for (Iterator it = data3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								if (j == 5)
								{
									dateVal = obdata[j].toString().split(" "); // yyyy
									// -
									// mm
									// -
									// dd
									// hh
									// :
									// mm
									c1.setName(DateUtil.convertDateToIndianFormat(dateVal[0].trim()) + " " + dateVal[1]);
								}
								else
								{
									c1.setName(obdata[j].toString());
								}
								// list.add(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
								// list.add("NA");
							}
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}
			}

			returnValue = SUCCESS;
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String fetchAssoCategory()
	{
		String returnString = "ERROR";
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				query.append("select id, associate_category from associatecategory order by associate_category asc");
				List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
				if (list != null)
				{
					locationJsonArray = new JSONArray();
					for (Object object : list)
					{
						Object[] obj = (Object[]) object;
						if (obj != null)
						{
							JSONObject formDetailsJson = new JSONObject();
							formDetailsJson.put("ID", obj[0].toString());
							formDetailsJson.put("NAME", obj[1].toString());
							locationJsonArray.add(formDetailsJson);
						}
					}
				}
				returnString = SUCCESS;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	public String beforeAssociateExcelUpload()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	public String validateTimeForOffering()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			String dateUS = scheduletime.substring(0, 10);
			String timeval = scheduletime.substring(10, 16);
			// System.out.println("dateUS>"+dateUS+"   timeval >"+timeval);
			scheduletime = DateUtil.convertDateToUSFormat(dateUS) + timeval;
			//System.out.println("scheduletime.substring(beginIndex, endIndex)>>>"+scheduletime);
			timestatus = new ClientHelper2().fetchTimeStatusForOffering(EntityType.ASSOCIATE, cId, id, scheduletime);
			/*System.out.println(timestatus);
			String ar[]=timestatus.split("#");
			timestatus=ar[1];
			String arr[]=ar[0].split(" ");
			time=arr[1];*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/*
	 * Anoop 15-4-2014 Fetch client last status
	 */
	public String fetchLastStatusForOffering()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			status = new ClientHelper2().fetchLastStatusForOffering(EntityType.ASSOCIATE, offeringId, id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public int getAssociateForBasicDetails()
	{
		return associateForBasicDetails;
	}

	public void setAssociateForBasicDetails(int associateForBasicDetails)
	{
		this.associateForBasicDetails = associateForBasicDetails;
	}

	public int getAssociateForOfferingDetails()
	{
		return associateForOfferingDetails;
	}

	public void setAssociateForOfferingDetails(int associateForOfferingDetails)
	{
		this.associateForOfferingDetails = associateForOfferingDetails;
	}

	public int getAssociateForContacDetails()
	{
		return associateForContacDetails;
	}

	public void setAssociateForContacDetails(int associateForContacDetails)
	{
		this.associateForContacDetails = associateForContacDetails;
	}

	public int getAssociateForAttachmentDetails()
	{
		return associateForAttachmentDetails;
	}

	public void setAssociateForAttachmentDetails(int associateForAttachmentDetails)
	{
		this.associateForAttachmentDetails = associateForAttachmentDetails;
	}

	public List<ConfigurationUtilBean> getAssociateTextBox()
	{
		return associateTextBox;
	}

	public void setAssociateTextBox(List<ConfigurationUtilBean> associateTextBox)
	{
		this.associateTextBox = associateTextBox;
	}

	public List<ConfigurationUtilBean> getAssociateDropDown()
	{
		return associateDropDown;
	}

	public void setAssociateDropDown(List<ConfigurationUtilBean> associateDropDown)
	{
		this.associateDropDown = associateDropDown;
	}

	public String getAssociateTypeValue()
	{
		return associateTypeValue;
	}

	public void setAssociateTypeValue(String associateTypeValue)
	{
		this.associateTypeValue = associateTypeValue;
	}

	public String getAssociateSubTypeValue()
	{
		return associateSubTypeValue;
	}

	public void setAssociateSubTypeValue(String associateSubTypeValue)
	{
		this.associateSubTypeValue = associateSubTypeValue;
	}

	public String getAssociateCategoryValue()
	{
		return associateCategoryValue;
	}

	public void setAssociateCategoryValue(String associateCategoryValue)
	{
		this.associateCategoryValue = associateCategoryValue;
	}

	public String getAssociateRatingValue()
	{
		return associateRatingValue;
	}

	public void setAssociateRatingValue(String associateRatingValue)
	{
		this.associateRatingValue = associateRatingValue;
	}

	public String getLocationValue()
	{
		return locationValue;
	}

	public void setLocationValue(String locationValue)
	{
		this.locationValue = locationValue;
	}

	public String getSourceNameValue()
	{
		return sourceNameValue;
	}

	public void setSourceNameValue(String sourceNameValue)
	{
		this.sourceNameValue = sourceNameValue;
	}

	public String getTargetAchievedValue()
	{
		return targetAchievedValue;
	}

	public void setTargetAchievedValue(String targetAchievedValue)
	{
		this.targetAchievedValue = targetAchievedValue;
	}

	public Map<Integer, String> getTargetKpiLiist()
	{
		return targetKpiLiist;
	}

	public void setTargetKpiLiist(Map<Integer, String> targetKpiLiist)
	{
		this.targetKpiLiist = targetKpiLiist;
	}

	public Map<String, String> getStateDataMap()
	{
		return stateDataMap;
	}

	public void setStateDataMap(Map<String, String> stateDataMap)
	{
		this.stateDataMap = stateDataMap;
	}

	public Map<String, String> getAssociateTypeMap()
	{
		return associateTypeMap;
	}

	public void setAssociateTypeMap(Map<String, String> associateTypeMap)
	{
		this.associateTypeMap = associateTypeMap;
	}

	public Map<String, String> getAssociateCategoryMap()
	{
		return associateCategoryMap;
	}

	public void setAssociateCategoryMap(Map<String, String> associateCategoryMap)
	{
		this.associateCategoryMap = associateCategoryMap;
	}

	public Map<String, String> getSourceMap()
	{
		return sourceMap;
	}

	public void setSourceMap(Map<String, String> sourceMap)
	{
		this.sourceMap = sourceMap;
	}

	public String getStateValue()
	{
		return stateValue;
	}

	public void setStateValue(String stateValue)
	{
		this.stateValue = stateValue;
	}

	public String getOfferingLevel()
	{
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel)
	{
		this.offeringLevel = offeringLevel;
	}

	public boolean isOLevel1()
	{
		return OLevel1;
	}

	public void setOLevel1(boolean level1)
	{
		OLevel1 = level1;
	}

	public String getOLevel1LevelName()
	{
		return OLevel1LevelName;
	}

	public void setOLevel1LevelName(String level1LevelName)
	{
		OLevel1LevelName = level1LevelName;
	}

	public boolean isOLevel2()
	{
		return OLevel2;
	}

	public void setOLevel2(boolean level2)
	{
		OLevel2 = level2;
	}

	public String getOLevel2LevelName()
	{
		return OLevel2LevelName;
	}

	public void setOLevel2LevelName(String level2LevelName)
	{
		OLevel2LevelName = level2LevelName;
	}

	public boolean isOLevel3()
	{
		return OLevel3;
	}

	public void setOLevel3(boolean level3)
	{
		OLevel3 = level3;
	}

	public String getOLevel3LevelName()
	{
		return OLevel3LevelName;
	}

	public void setOLevel3LevelName(String level3LevelName)
	{
		OLevel3LevelName = level3LevelName;
	}

	public boolean isOLevel4()
	{
		return OLevel4;
	}

	public void setOLevel4(boolean level4)
	{
		OLevel4 = level4;
	}

	public String getOLevel4LevelName()
	{
		return OLevel4LevelName;
	}

	public void setOLevel4LevelName(String level4LevelName)
	{
		OLevel4LevelName = level4LevelName;
	}

	public boolean isOLevel5()
	{
		return OLevel5;
	}

	public void setOLevel5(boolean level5)
	{
		OLevel5 = level5;
	}

	public String getOLevel5LevelName()
	{
		return OLevel5LevelName;
	}

	public void setOLevel5LevelName(String level5LevelName)
	{
		OLevel5LevelName = level5LevelName;
	}

	public List<ConfigurationUtilBean> getAssociateTextBoxForContact()
	{
		return associateTextBoxForContact;
	}

	public void setAssociateTextBoxForContact(List<ConfigurationUtilBean> associateTextBoxForContact)
	{
		this.associateTextBoxForContact = associateTextBoxForContact;
	}

	public Map<String, String> getAssociateDateControls()
	{
		return associateDateControls;
	}

	public void setAssociateDateControls(Map<String, String> associateDateControls)
	{
		this.associateDateControls = associateDateControls;
	}

	public boolean isDegreeOfInfluenceExist()
	{
		return degreeOfInfluenceExist;
	}

	public void setDegreeOfInfluenceExist(boolean degreeOfInfluenceExist)
	{
		this.degreeOfInfluenceExist = degreeOfInfluenceExist;
	}

	public String getDegreeOfInfluence()
	{
		return degreeOfInfluence;
	}

	public void setDegreeOfInfluence(String degreeOfInfluence)
	{
		this.degreeOfInfluence = degreeOfInfluence;
	}

	public Map<String, String> getVerticalMap()
	{
		return verticalMap;
	}

	public void setVerticalMap(Map<String, String> verticalMap)
	{
		this.verticalMap = verticalMap;
	}

	public String getTempVariable()
	{
		return tempVariable;
	}

	public void setTempVariable(String tempVariable)
	{
		this.tempVariable = tempVariable;
	}

	public JSONArray getLocationJsonArray()
	{
		return locationJsonArray;
	}

	public void setLocationJsonArray(JSONArray locationJsonArray)
	{
		this.locationJsonArray = locationJsonArray;
	}

	public String getModifyFlag()
	{
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag)
	{
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public String getIsExistingAssociate()
	{
		return isExistingAssociate;
	}

	public void setIsExistingAssociate(String isExistingAssociate)
	{
		this.isExistingAssociate = isExistingAssociate;
	}

	public List<GridDataPropertyView> getMasterViewPropForContact()
	{
		return masterViewPropForContact;
	}

	public void setMasterViewPropForContact(List<GridDataPropertyView> masterViewPropForContact)
	{
		this.masterViewPropForContact = masterViewPropForContact;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getLostFlag()
	{
		return lostFlag;
	}

	public void setLostFlag(String lostFlag)
	{
		this.lostFlag = lostFlag;
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

	public List<Object> getMasterViewListForContact()
	{
		return masterViewListForContact;
	}

	public void setMasterViewListForContact(List<Object> masterViewListForContact)
	{
		this.masterViewListForContact = masterViewListForContact;
	}

	public Map<String, String> getBasicParams()
	{
		return basicParams;
	}

	public void setBasicParams(Map<String, String> basicParams)
	{
		this.basicParams = basicParams;
	}

	public List<String> getNamesContact()
	{
		return namesContact;
	}

	public void setNamesContact(List<String> namesContact)
	{
		this.namesContact = namesContact;
	}

	public List<Parent> getParentContact()
	{
		return parentContact;
	}

	public void setParentContact(List<Parent> parentContact)
	{
		this.parentContact = parentContact;
	}

	public List<String> getNamesOffering()
	{
		return namesOffering;
	}

	public void setNamesOffering(List<String> namesOffering)
	{
		this.namesOffering = namesOffering;
	}

	public List<Parent> getParentOffering()
	{
		return parentOffering;
	}

	public void setParentOffering(List<Parent> parentOffering)
	{
		this.parentOffering = parentOffering;
	}

	public List<String> getNamesTakeaction()
	{
		return namesTakeaction;
	}

	public void setNamesTakeaction(List<String> namesTakeaction)
	{
		this.namesTakeaction = namesTakeaction;
	}

	public List<Parent> getParentTakeaction()
	{
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction)
	{
		this.parentTakeaction = parentTakeaction;
	}

	public String getAssociateName()
	{
		return associateName;
	}

	public void setAssociateName(String associateName)
	{
		this.associateName = associateName;
	}

	public String getStatusName()
	{
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public String getTargetAchieved()
	{
		return targetAchieved;
	}

	public void setTargetAchieved(String targetAchieved)
	{
		this.targetAchieved = targetAchieved;
	}

	public String getConvertToAssociate()
	{
		return convertToAssociate;
	}

	public void setConvertToAssociate(String convertToAssociate)
	{
		this.convertToAssociate = convertToAssociate;
	}

	public String getAcManager()
	{
		return acManager;
	}

	public void setAcManager(String acManager)
	{
		this.acManager = acManager;
	}

	public Map<String, String> getEmployee_basicMap()
	{
		return employee_basicMap;
	}

	public void setEmployee_basicMap(Map<String, String> employee_basicMap)
	{
		this.employee_basicMap = employee_basicMap;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Map<String, String> getAssociate_statusMap()
	{
		return associate_statusMap;
	}

	public void setAssociate_statusMap(Map<String, String> associate_statusMap)
	{
		this.associate_statusMap = associate_statusMap;
	}

	public int getPendingAssociate()
	{
		return pendingAssociate;
	}

	public void setPendingAssociate(int pendingAssociate)
	{
		this.pendingAssociate = pendingAssociate;
	}

	public int getLostAssociate()
	{
		return lostAssociate;
	}

	public void setLostAssociate(int lostAssociate)
	{
		this.lostAssociate = lostAssociate;
	}

	public int getNextAssociateAction()
	{
		return nextAssociateAction;
	}

	public void setNextAssociateAction(int nextAssociateAction)
	{
		this.nextAssociateAction = nextAssociateAction;
	}

	public String getFromDashboard()
	{
		return fromDashboard;
	}

	public void setFromDashboard(String fromDashboard)
	{
		this.fromDashboard = fromDashboard;
	}

	public int getExistingAssociate()
	{
		return existingAssociate;
	}

	public void setExistingAssociate(int existingAssociate)
	{
		this.existingAssociate = existingAssociate;
	}

	public int getTodayActivity()
	{
		return todayActivity;
	}

	public void setTodayActivity(int todayActivity)
	{
		this.todayActivity = todayActivity;
	}

	public String getActivityType()
	{
		return activityType;
	}

	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}

	public int getTomorrowActivity()
	{
		return tomorrowActivity;
	}

	public void setTomorrowActivity(int tomorrowActivity)
	{
		this.tomorrowActivity = tomorrowActivity;
	}

	public int getNextSevenDaysActivity()
	{
		return nextSevenDaysActivity;
	}

	public void setNextSevenDaysActivity(int nextSevenDaysActivity)
	{
		this.nextSevenDaysActivity = nextSevenDaysActivity;
	}

	public ArrayList<ArrayList<String>> getAssociateStatusAllList()
	{
		return associateStatusAllList;
	}

	public void setAssociateStatusAllList(ArrayList<ArrayList<String>> associateStatusAllList)
	{
		this.associateStatusAllList = associateStatusAllList;
	}

	public String getAssociateStatus()
	{
		return associateStatus;
	}

	public void setAssociateStatus(String associateStatus)
	{
		this.associateStatus = associateStatus;
	}

	public String getStateExist()
	{
		return stateExist;
	}

	public void setStateExist(String stateExist)
	{
		this.stateExist = stateExist;
	}

	public String getLocationExist()
	{
		return locationExist;
	}

	public void setLocationExist(String locationExist)
	{
		this.locationExist = locationExist;
	}

	public String getAssociateTypeExist()
	{
		return associateTypeExist;
	}

	public void setAssociateTypeExist(String associateTypeExist)
	{
		this.associateTypeExist = associateTypeExist;
	}

	public String getAssociateSubTypeExist()
	{
		return associateSubTypeExist;
	}

	public void setAssociateSubTypeExist(String associateSubTypeExist)
	{
		this.associateSubTypeExist = associateSubTypeExist;
	}

	public String getAssociateCategoryExist()
	{
		return associateCategoryExist;
	}

	public void setAssociateCategoryExist(String associateCategoryExist)
	{
		this.associateCategoryExist = associateCategoryExist;
	}

	public String getAssociateRatingExist()
	{
		return associateRatingExist;
	}

	public void setAssociateRatingExist(String associateRatingExist)
	{
		this.associateRatingExist = associateRatingExist;
	}

	public String getSourceNameExist()
	{
		return sourceNameExist;
	}

	public void setSourceNameExist(String sourceNameExist)
	{
		this.sourceNameExist = sourceNameExist;
	}

	public String getTargetAchievedExist()
	{
		return targetAchievedExist;
	}

	public void setTargetAchievedExist(String targetAchievedExist)
	{
		this.targetAchievedExist = targetAchievedExist;
	}

	public String getAcManagerExist()
	{
		return acManagerExist;
	}

	public void setAcManagerExist(String acManagerExist)
	{
		this.acManagerExist = acManagerExist;
	}

	public String getStatusExist()
	{
		return statusExist;
	}

	public void setStatusExist(String statusExist)
	{
		this.statusExist = statusExist;
	}

	public Map<String, Integer> getActivity()
	{
		return activity;
	}

	public void setActivity(Map<String, Integer> activity)
	{
		this.activity = activity;
	}

	public String getLegendShow()
	{
		return legendShow;
	}

	public void setLegendShow(String legendShow)
	{
		this.legendShow = legendShow;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public int getMissedActivity()
	{
		return missedActivity;
	}

	public void setMissedActivity(int missedActivity)
	{
		this.missedActivity = missedActivity;
	}

	public Map<String, Integer> getStatusMap()
	{
		return statusMap;
	}

	public void setStatusMap(Map<String, Integer> statusMap)
	{
		this.statusMap = statusMap;
	}

	public Map<String, Integer> getOffWrtAssoMap()
	{
		return offWrtAssoMap;
	}

	public void setOffWrtAssoMap(Map<String, Integer> offWrtAssoMap)
	{
		this.offWrtAssoMap = offWrtAssoMap;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getColName()
	{
		return colName;
	}

	public void setColName(String colName)
	{
		this.colName = colName;
	}

	public ArrayList<ArrayList<String>> getBirthdayList()
	{
		return birthdayList;
	}

	public void setBirthdayList(ArrayList<ArrayList<String>> birthdayList)
	{
		this.birthdayList = birthdayList;
	}

	public String getCurrentDate()
	{
		return currentDate;
	}

	public void setCurrentDate(String currentDate)
	{
		this.currentDate = currentDate;
	}

	public ArrayList<ArrayList<String>> getAnniversaryList()
	{
		return anniversaryList;
	}

	public void setAnniversaryList(ArrayList<ArrayList<String>> anniversaryList)
	{
		this.anniversaryList = anniversaryList;
	}

	public String getExistAndLostAssociate()
	{
		return ExistAndLostAssociate;
	}

	public void setExistAndLostAssociate(String existAndLostAssociate)
	{
		ExistAndLostAssociate = existAndLostAssociate;
	}

	public String getIndustry()
	{
		return industry;
	}

	public void setIndustry(String industry)
	{
		this.industry = industry;
	}

	public String getIndustryExist()
	{
		return industryExist;
	}

	public void setIndustryExist(String industryExist)
	{
		this.industryExist = industryExist;
	}

	public String getSubIndustry()
	{
		return subIndustry;
	}

	public void setSubIndustry(String subIndustry)
	{
		this.subIndustry = subIndustry;
	}

	public String getSubIndustryExist()
	{
		return subIndustryExist;
	}

	public void setSubIndustryExist(String subIndustryExist)
	{
		this.subIndustryExist = subIndustryExist;
	}

	public void setIndustryMap(LinkedHashMap<String, String> industryMap)
	{
		this.industryMap = industryMap;
	}

	public void setSubIndustryMap(LinkedHashMap<String, String> subIndustryMap)
	{
		this.subIndustryMap = subIndustryMap;
	}

	public Map<String, String> getIndustryList()
	{
		return industryList;
	}

	public void setIndustryList(Map<String, String> industryList)
	{
		this.industryList = industryList;
	}

	public Map<String, String> getSourceList()
	{
		return sourceList;
	}

	public void setSourceList(Map<String, String> sourceList)
	{
		this.sourceList = sourceList;
	}

	public Map<String, String> getLocationList()
	{
		return locationList;
	}

	public void setLocationList(Map<String, String> locationList)
	{
		this.locationList = locationList;
	}

	public String getStarRating()
	{
		return starRating;
	}

	public void setStarRating(String starRating)
	{
		this.starRating = starRating;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public boolean isDepartmentExist()
	{
		return departmentExist;
	}

	public void setDepartmentExist(boolean departmentExist)
	{
		this.departmentExist = departmentExist;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public void setLocation_master_dataMap(Map<String, String> location_master_dataMap)
	{
		this.location_master_dataMap = location_master_dataMap;
	}

	public Map<String, String> getLocation_master_dataMap()
	{
		return location_master_dataMap;
	}

	public void setSubIndustryMap(Map<String, String> subIndustryMap)
	{
		this.subIndustryMap = subIndustryMap;
	}

	public Map<String, String> getSubIndustryMap()
	{
		return subIndustryMap;
	}

	public void setIndustryMap(Map<String, String> industryMap)
	{
		this.industryMap = industryMap;
	}

	public Map<String, String> getIndustryMap()
	{
		return industryMap;
	}

	public void setSelectedStateId(int selectedStateId)
	{
		this.selectedStateId = selectedStateId;
	}

	public int getSelectedStateId()
	{
		return selectedStateId;
	}

	public void setAssociateSubTypeMap(Map<String, String> associateSubTypeMap)
	{
		this.associateSubTypeMap = associateSubTypeMap;
	}

	public Map<String, String> getAssociateSubTypeMap()
	{
		return associateSubTypeMap;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDate()
	{
		return date;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getComments()
	{
		return comments;
	}

	public void setOfferingList(Map<String, String> offeringList)
	{
		this.offeringList = offeringList;
	}

	public Map<String, String> getOfferingList()
	{
		return offeringList;
	}

	public void setContactPersonMap(Map<String, String> contactPersonMap)
	{
		this.contactPersonMap = contactPersonMap;
	}

	public Map<String, String> getContactPersonMap()
	{
		return contactPersonMap;
	}

	public void setAssociateStatusList(Map<String, String> associateStatusList)
	{
		this.associateStatusList = associateStatusList;
	}

	public Map<String, String> getAssociateStatusList()
	{
		return associateStatusList;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setAssociateContactEditList(ArrayList<ArrayList<GridDataPropertyView>> associateContactEditList)
	{
		this.associateContactEditList = associateContactEditList;
	}

	public ArrayList<ArrayList<GridDataPropertyView>> getAssociateContactEditList()
	{
		return associateContactEditList;
	}

	public void setDeptlist(Map<String, String> deptlist)
	{
		this.deptlist = deptlist;
	}

	public Map<String, String> getDeptlist()
	{
		return deptlist;
	}

	public void setDeptMap(Map<String, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<String, String> getDeptMap()
	{
		return deptMap;
	}

	public String getAssociateCategory() {
		return associateCategory;
	}

	public void setAssociateCategory(String associateCategory) {
		this.associateCategory = associateCategory;
	}

	public String getAssociateType() {
		return associateType;
	}

	public void setAssociateType(String associateType) {
		this.associateType = associateType;
	}

	public String getAssociate_Name() {
		return associate_Name;
	}

	public void setAssociate_Name(String associate_Name) {
		this.associate_Name = associate_Name;
	}

	public void setTimestatus(String timestatus) {
		this.timestatus = timestatus;
	}

	public String getTimestatus() {
		return timestatus;
	}

	public void setScheduletime(String scheduletime) {
		this.scheduletime = scheduletime;
	}

	public String getScheduletime() {
		return scheduletime;
	}
	
}