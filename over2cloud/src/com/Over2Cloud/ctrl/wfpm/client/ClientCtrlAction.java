package com.Over2Cloud.ctrl.wfpm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.criteria.predicate.LikePredicate;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Constant;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.report.DSRMode;
import com.Over2Cloud.ctrl.report.DSRType;
import com.Over2Cloud.ctrl.report.DSRgerneration;
import com.Over2Cloud.ctrl.wfpm.achievement.AchievementHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.lead.LeadActionControlDao;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper2;
import com.Over2Cloud.ctrl.wfpm.report.KPIReportHelper;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class ClientCtrlAction extends ActionSupport implements ServletRequestAware
{

	/**
	 * {@link LikePredicate}
	 */
	private static final long serialVersionUID = -1L;

	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");

	private String empId = session.get("empIdofuser").toString().split("-")[1];

	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String cId = new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, userName, connectionSpace)[0];
	static final Log log = LogFactory.getLog(ClientCtrlAction.class);
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> clientTextBox = null;
	private List<ConfigurationUtilBean> clientDropDown = null;
	private ArrayList<ArrayList<String>> clientStatusAllList = null;
	private int clientForBasicDetails;
	private int clientForOfferingDetails;
	private int clientForContacDetails;
	private int clientForAttachmentDetails;
	private String sourceMasterExist, uid;
	private String weightageMasterExist;
	private String sourceMaster;
	private String weightageMaster;
	private String clientStatus;
	private String clientstatus, time;
	private String activityType;
	private int todayActivity;
	private int nextSevenDaysActivity;
	private int tomorrowActivity;
	private String referedByExist;
	private String referedBy;
	private String fromDashboard;
	private String refNameExist;
	private String refName;
	private String starRatingExist;
	private String starRating;
	private String statusExist;
	private String status;
	private String locationExist;
	private String location;
	private String client_Name;
	private String acManagerExist;
	private String acManager;
	private String forecastCategary;
	private String fromDateSearch;
	private String targetAchievedExist;
	private String targetAchieved;
	private boolean degreeOfInfluenceExist;
	private boolean clientNameExist;
	private String degreeOfInfluence;
	private boolean anniversaryExist;
	private String anniversary;
	private boolean birthdayExist;
	private String birthday;
	private String forecast_category;
	private String forecastcategoryExist;
	private String closuredateExist;
	private String closure_date;
	private String clientNameWithStar;
	private List<String> numberOfStar;
	private String lostId, searchParameter;
	private String RCA;
	private String CAPA;
	private String verticalname;
	private String offeringname;
	private String subofferingname;
	private String returntype;
	private String opportunity_value;
	private String opportunity_name;
	private String offeringOpportunity;
	private String sales_stages;
	private String salesstagesExist;
	private String compelling_reason;
	private String salesstages;
	private Long totalopp = 0L;
	private Long grandtotal = 0L;
	private Long grandtotalsum = 0L;
	private Map<String, String> offeringNameMap;
	private Map<String, String> sourceMasterMap;
	private Map<String, String> weightageMasterMap;
	private Map<String, String> forecastcategoryMap;
	private Map<String, String> salesStageMap;
	private Map<String, String> employee_basicMap;
	private Map<String, String> lostStatusMAP;
	private Map<String, String> offeringforOpportunityMap;
	private Map<String, String> opportunityNameMap;
	private Map<String, String> salesStagesMap;
	private LinkedHashMap<String, String> client_statusMap;
	private Map<String, String> location_master_dataMap;
	private List<ConfigurationUtilBean> clientTextBoxForContact;
	private List<ConfigurationUtilBean> clientDropDownForContact;
	private LinkedHashMap<String, String> clientDateControls;
	Map<Integer, String> reqccemailList = new HashMap<Integer, String>();

	private boolean OLevel1;
	private boolean OLevel2;
	private boolean OLevel3;
	private boolean OLevel4;
	private boolean OLevel5;

	private String OLevel1LevelName;
	private String OLevel2LevelName;
	private String OLevel3LevelName;
	private String OLevel4LevelName;
	private String OLevel5LevelName;
	private LinkedHashMap<String, String> verticalMap;

	private String offeringId;
	private String Offeringlevel;
	private LinkedHashMap<String, Object> offeringMap;
	private LinkedHashMap<String, String> pClientMap;

	private JSONObject responseDetailsJson = null;
	private JSONArray jsonArray = null;
	private JSONObject jsonObject;
	private String emailtofilter;
	private String emailtofiltertwo;
	private String message;
	private String messageValue;
	private List<ConfigurationUtilBean> frequency = null;

	private String mainHeaderName;
	private String lostFlag;
	private String pAction;
	private String selectRows;
	private String clientId;
	private String ExistAndLostClient;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterViewPropForContact = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> masterClientViewProp = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> opportunityViewProp = new ArrayList<GridDataPropertyView>();
	private HashMap<String, HashMap<String, String>> offeringWeightageDetailsVal = new LinkedHashMap<String, HashMap<String, String>>();
	private HashMap<String, HashMap<String, String>> offeringWeightageDetailsZeroval = new LinkedHashMap<String, HashMap<String, String>>();
	HashMap<String, HashMap<String, HashMap<String, String>>> offeringWeightageDetails2 = null;

	private Map<String, String> clientContactandEmailMap;
	private Map<String, String> templateNameList;
	Map<String, Object> userdata = new HashMap<String, Object>();

	private String timestatus;
	private String scheduletime;

	private String fromDate;
	private String opportunityType;
	private String expectency;
	private Map<String, String> existingFreeContact;
	private String salesstageName;
	private InputStream inputstream;
	private String client;
	private Map<String, String> checkEmp;

	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private List<Object> masterViewListForContact;

	private List<Object> opportunityViewList;
	private LinkedHashMap<String, String> offeringList = null;
	private Map<String, String> clientStatusList = null;
	private String clientName;
	private String statusName;
	private LinkedHashMap<String, String> basicParams = null;
	private List<List<String>> contactList = null;
	private LinkedHashMap<Integer, List<String>> dataList = null;
	private List<Parent> parentContact = new ArrayList<Parent>();
	private List<String> namesContact = null;

	private List<Parent> parentOffering = new ArrayList<Parent>();
	private List<String> namesOffering = null;

	private List<Parent> parentTakeaction = new ArrayList<Parent>();
	private List<String> namesTakeaction = null;

	private List<String> cList = null;
	private LinkedHashMap<String, String> one1 = null;
	private String isExistingClient = "";
	private String param = "";
	private String convertToClient;
	private Map<Integer, String> targetKpiLiist = null;
	private int pendingClient;
	private int lostClient;
	private int nextAction;
	private int existingClient;
	String tableName = "", colName = "";
	private String offeringLevel = session.get("offeringLevel").toString();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String industry;
	private String industryExist;
	private String subIndustry;
	private String subIndustryExist;
	private Map<String, String> industryMap;
	private Map<String, String> subIndustryMap;
	private Map<String, String> weightageMap;
	private Map<String, String> industryList;
	private Map<String, String> sourceList;
	private Map<String, String> locationList;
	private Map<String, String> weightageList;
	private Map<String, String> clientstatuslist;
	private String sourceName;
	private String momType;
	private Map<String, String> momFullViewMap;
	private String fileName;
	private InputStream fileInputStream;
	HashMap<String, HashMap<String, String>> offeringWeightageDetails = null;
	private boolean departmentExist;
	private String department;
	private Map<String, String> deptMap;
	private String comments;
	private String po_attachFileName;
	private String po_attachContentType;
	private File po_attach;
	private String ammount;
	private String poNumber;
	private String po_date;
	private ArrayList<ArrayList<GridDataPropertyView>> clientContactEditList;
	private Map<String, String> contactPersonMap;
	private String existingfreeId;
	private List<String> contactPersonList;
	private String contactId;
	private String date;
	private String to_maxDateTime;
	private String currDate;
	private boolean showMomDetails;
	private Integer totalRecord;
	private String otherlostreason;
	private int counter;
	private String indr;
	private String subindr;
	private String rate;
	private String sourceN, locatn, clintSts, clintName, acmng, salesstag, forcstcat, frmdateSear;

	public String execute()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: execute of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop 15-4-20132 Build client page
	 */
	public String beforeClientAdd()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageDataForClient();

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		if (getReturntype().equalsIgnoreCase("Editoffering") && getReturntype() != null)
		{
			// System.out.println(getReturntype());
			return "EditOffering";
		} else
		{
			return SUCCESS;
		}

	}

	@SuppressWarnings("unchecked")
	public String beforeClientEdit()
	{
		String resulttype = ERROR;
		String editfor = getReturntype();
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			ClientHelper2 ch2 = new ClientHelper2();
			ClientHelper3 ch3 = new ClientHelper3();

			/*
			 * For client basic details
			 */
			masterViewProp = ch2.fetchClientBasicDataForEdition(id);
			// Source
			sourceMasterMap = new LinkedHashMap<String, String>();
			sourceMasterMap = ch3.fetchAllSource();
			// Location
			location_master_dataMap = new LinkedHashMap<String, String>();
			location_master_dataMap = ch3.fetchAllLocation();

			// Target Segmentmap.
			weightageMasterMap = new LinkedHashMap<String, String>();
			weightageMasterMap = ch3.fetchAllweightageTargetSegment();

			// Employee
			employee_basicMap = new LinkedHashMap<String, String>();
			// employee_basicMap = ch3.fetchAllEmployee();
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
			// Industry
			industryMap = new LinkedHashMap<String, String>();
			industryMap = ch3.fetchAllIndustry();
			// Sub Industry
			subIndustryMap = ch3.fetchSubIndustryByIndustryId(ClientHelper2.industryId);
			salesStageMap = new LinkedHashMap<String, String>();
			salesStageMap = ch3.fetchsalesStage();
			forecastcategoryMap = new LinkedHashMap<String, String>();
			forecastcategoryMap = ch3.fetchForcastcategory();

			/*
			 * For client contact
			 */
			clientContactEditList = ch2.fetchClientContactDataForEdition(id);
			// Department
			deptMap = new LinkedHashMap<String, String>();
			deptMap = ch3.fetchAllDepartment();

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		// System.out.println("masterViewProp:" + masterViewProp.size());
		if (editfor.equalsIgnoreCase("Client"))
		{
			resulttype = "CLIENT";
		}
		if (editfor.equalsIgnoreCase("Contact"))
		{
			resulttype = "CONTACT";
		}
		if (editfor.equalsIgnoreCase("ClientandContact"))
		{
			resulttype = SUCCESS;
		}
		return resulttype;
	}

	public String editClient()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = false;
			String acManager = "";
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = request.getParameter(Parmname);

				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
				{
					if (Parmname.equalsIgnoreCase("closure_date"))
					{
						paramValue = DateUtil.convertDateToUSFormat(paramValue);
					}
					ob = new InsertDataTable();
					ob.setColName(Parmname);
					ob.setDataName(paramValue);
					insertData.add(ob);
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
					} else
					{
						acManager = cId;
						ob.setDataName(acManager);
					}
				} catch (Exception e)
				{
					ob.setDataName(cId);
					e.printStackTrace();
				}
				insertData.add(ob);
			} else
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

			status = cbt.updateIntoTable("client_basic_data", insertData, id, connectionSpace);
			if (status)
			{
				DSRgerneration ta = new DSRgerneration();
				int empID = Integer.parseInt((String) session.get("empIdofuser").toString().split("-")[1]);

				if (targetAchieved != null && !targetAchieved.equalsIgnoreCase("") && !targetAchieved.equalsIgnoreCase("-1"))
				{
					ta.setDSRRecords(targetAchieved, empID, DSRMode.KPI, DSRType.ONLINE);
				}
				addActionMessage("Client Edited Successfully");
				return SUCCESS;
			} else
			{
				addActionMessage("Oops There is some error in data!");
				return SUCCESS;
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Sanjiv, Edit client
	 */
	public String editClientContact()
	{
		String idval = null;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> clientColName = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");

			if (clientColName != null)
			{
				// List <InsertDataTable> insertData=new
				// ArrayList<InsertDataTable>();
				InsertDataTable ob = null;

				boolean status = false;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();

				for (GridDataPropertyView gdp : clientColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);

				}

				cbt.createTable22("client_contact_data", tableColumn, connectionSpace);

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
						 * if (Parmname.equalsIgnoreCase("clientName")) {
						 * associateName = paramValue; continue; }
						 */
						// adding the parameters list.
						if (statusTemp)
						{
							String tempParamValueSize[] = request.getParameterValues(Parmname);
							for (String H : tempParamValueSize)
							{
								// counting one time size of the parameter value
								if (H != null)
									paramValueSize++;
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
							// //System.out.println("paramValue[j]" +
							// paramValue[j]);
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
							// System.out.println("parmValuew[k][i]>>" +
							// parmValuew[k][i]);
							valueSelect = true;
						} else
						{
							valueSelect = false;
						}

						if (valueSelect)
						{

							// change the client of a contact person (by azad)
							/*
							 * if(paramList.get(k).toString().equalsIgnoreCase(
							 * "opportunityName" )) { if(parmValuew[k][i] !=
							 * null && !parmValuew[k][i].equalsIgnoreCase("-1")
							 * && !parmValuew[k][i].equalsIgnoreCase("")); {
							 * 
							 * ob.setColName("clientName");
							 * ob.setDataName(parmValuew[k][i]);
							 * insertData.add(ob); }
							 * 
							 * }
							 */

							if (!(paramList.get(k).toString().equalsIgnoreCase("clientName")))
							{
								ob = new InsertDataTable();
								// System.out.println("Column name " +
								// paramList.get(k).toString());
								if (paramList.get(k).toString().equals("id"))
								{
									idval = parmValuew[k][i];
								}
								// System.out.println("Data name " +
								// parmValuew[k][i]);

								if (paramList.get(k).toString().equalsIgnoreCase("birthday") || paramList.get(k).toString().equalsIgnoreCase("anniversary"))
								{
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(DateUtil.convertDateToUSFormat(parmValuew[k][i]));
								} else
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
						 * ob = new InsertDataTable();
						 * ob.setColName("clientName"); //
						 * //System.out.println("associateName " +
						 * associateName); ob.setDataName(associateName);
						 * insertData.add(ob);
						 */

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(cId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("currentStatusDate");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						status = cbt.updateIntoTable("client_contact_data", insertData, idval, connectionSpace);
					}
				}

				if (status)
				{
					addActionMessage("Client contact Edited successfully.");
				} else
				{
					addActionMessage("Oops There is some error in data.");
				}
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addContacts of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForClient()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Create accordion for client details, begins
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_client_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("client_basic_configuration"))
					{
						clientForBasicDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_offering_configuration"))
					{
						clientForOfferingDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_contact_configuration"))
					{
						clientForContacDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_attachment_configuration"))
					{
						clientForAttachmentDetails = 1;
					}
				}
			}

			/*
			 * Accordion: Client Basic Information
			 */
			// Build map for text box and drop down for employee basic
			// information
			ClientHelper3 ch3 = new ClientHelper3();
			if (clientForBasicDetails == 1)
			{
				List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_basic_configuration");
				clientTextBox = new ArrayList<ConfigurationUtilBean>();
				clientDropDown = new ArrayList<ConfigurationUtilBean>();

				for (GridDataPropertyView gdp : offeringLevel1)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("opportunity_brief") && !gdp.getColomnName().equalsIgnoreCase("opportunity_value") && !gdp.getColomnName().equalsIgnoreCase("weightage") && !gdp.getColomnName().equalsIgnoreCase("input") && !gdp.getColomnName().equalsIgnoreCase("phoneNo") && !gdp.getColomnName().equalsIgnoreCase("faxNo")
							&& !gdp.getColomnName().equalsIgnoreCase("webAddress") && !gdp.getColomnName().equalsIgnoreCase("companyEmail"))
					{

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}

						clientTextBox.add(obj);

					}

					if (gdp.getColType().trim().equalsIgnoreCase("Date") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("weightage"))
					{

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}

						if (gdp.getColomnName().equalsIgnoreCase("closure_date"))
						{
							closure_date = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								closuredateExist = "true";
							else
								closuredateExist = "false";
						}
						// clientTextBox.add(obj);

					}

					if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
					{

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}

						// ////////////////////////////////////
						if (gdp.getColomnName().equalsIgnoreCase("industry"))
						{
							industry = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								industryExist = "true";
							else
								industryExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
						{
							subIndustry = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								subIndustryExist = "true";
							else
								subIndustryExist = "false";
						}
						if (gdp.getColomnName().equalsIgnoreCase("sourceMaster"))
						{
							sourceMaster = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								sourceMasterExist = "true";
							else
								sourceMasterExist = "false";
						}
						if (gdp.getColomnName().equalsIgnoreCase("weightage_targetsegment"))
						{
							weightageMaster = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								weightageMasterExist = "true";
							else
								weightageMasterExist = "false";
						}

						else if (gdp.getColomnName().equalsIgnoreCase("referedBy"))
						{
							referedBy = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								referedByExist = "true";
							else
								referedByExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("refName"))
						{
							refName = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								refNameExist = "true";
							else
								refNameExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("starRating"))
						{
							starRating = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								starRatingExist = "true";
							else
								starRatingExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("status"))
						{
							status = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								statusExist = "true";
							else
								statusExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("location"))
						{
							location = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								locationExist = "true";
							else
								locationExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("forecast_category"))
						{

							forecast_category = gdp.getHeadingName();

							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								forecastcategoryExist = "true";
							else
								forecastcategoryExist = "false";
						}

						else if (gdp.getColomnName().equalsIgnoreCase("sales_stages"))
						{

							sales_stages = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								salesstagesExist = "true";
							else
								salesstagesExist = "false";
						}

						else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
						{
							acManager = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								acManagerExist = "true";
							else
								acManagerExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
						{
							targetAchieved = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								targetAchievedExist = "true";
							else
								targetAchievedExist = "false";
							/**
							 * getting mapped target of employee
							 */
							targetKpiLiist = new HashMap<Integer, String>();
							try
							{
								targetKpiLiist = CommonWork.getTargetOfEmployee(userName, connectionSpace);
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						clientDropDown.add(obj);
					}
				}

				// Fill values in dropdown
				// Get all source list
				industryMap = new LinkedHashMap<String, String>();
				if (industryExist != null)
				{
					industryMap = ch3.fetchAllIndustry();
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

				sourceMasterMap = new LinkedHashMap<String, String>();
				if (sourceMasterExist != null)
				{
					sourceMasterMap = ch3.fetchAllSource();
				}

				weightageMasterMap = new LinkedHashMap<String, String>();
				if (weightageMasterExist != null)
				{
					weightageMasterMap = ch3.fetchAllweightageTargetSegment();
				}
				forecastcategoryMap = new LinkedHashMap<String, String>();
				if (forecastcategoryExist != null)
				{
					forecastcategoryMap = ch3.fetchForcastcategory();
				}
				salesStageMap = new LinkedHashMap<String, String>();
				if (salesstagesExist != null)
				{
					salesStageMap = ch3.fetchsalesStage();
				}

				EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
				employee_basicMap = new LinkedHashMap<String, String>();
				if (acManagerExist != null)
				{
					employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
				}

				client_statusMap = new LinkedHashMap<String, String>();
				if (statusExist != null)
				{
					List client_statusData = cbt.executeAllSelectQuery("select id, statusName from " + "client_status order by statusName", connectionSpace);
					if (client_statusData != null)
					{

						for (Object c : client_statusData)
						{
							Object[] dataC = (Object[]) c;
							client_statusMap.put(dataC[0].toString(), dataC[1].toString());
						}
					}
				}
				location_master_dataMap = new LinkedHashMap<String, String>();
				if (locationExist != null)
				{
					location_master_dataMap = ch3.fetchAllLocation();
				}
			}

			/*
			 * Accordion: Client offering Information
			 */
			if (clientForOfferingDetails == 1)
			{
				// oferring taken by client in configuration
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
					verticalMap = new LinkedHashMap<String, String>();
					List verticalData = cbt.executeAllSelectQuery("select id, verticalname from " + "offeringlevel1 order by verticalname", connectionSpace);

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

			if (clientForContacDetails == 1)
			{
				/*
				 * Accordion: Client contact information, if contact is taken
				 */
				List<GridDataPropertyView> list = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
				clientTextBoxForContact = new ArrayList<ConfigurationUtilBean>();
				clientDropDownForContact = new ArrayList<ConfigurationUtilBean>();
				clientDateControls = new LinkedHashMap<String, String>();

				for (GridDataPropertyView gdp : list)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("designation") && !gdp.getColomnName().equalsIgnoreCase("alternateMob") && !gdp.getColomnName().equalsIgnoreCase("alternateEmail"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}

						clientTextBoxForContact.add(obj);
					}

					if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}

						clientDropDownForContact.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("Time") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
					{

						clientDateControls.put(gdp.getColomnName(), gdp.getHeadingName());
					}

				}

				// Code for drop down
				deptMap = new LinkedHashMap<String, String>();

				for (ConfigurationUtilBean entry : clientDropDownForContact)
				{
					if (entry.getKey().equalsIgnoreCase("degreeOfInfluence"))
					{
						degreeOfInfluence = entry.getValue();
						if (entry.isMandatory())
							degreeOfInfluenceExist = true;
					}

					if (entry.getKey().equalsIgnoreCase("clientName"))
					{
						clientName = entry.getValue();
						if (entry.isMandatory())
							clientNameExist = true;
					}

					if (entry.getKey().equalsIgnoreCase("department"))
					{
						setDepartment(entry.getValue());
						// deptMap = ch3.fetchAllDepartment();
						setDeptMap(ch3.fetchAllDepartment());
						if (entry.isMandatory())
							setDepartmentExist(true);
					}

				}
				contactPersonMap = new LinkedHashMap<String, String>();
				contactPersonMap = ch3.fetchallInactiveContact();
				// contactPersonList = ch3.fetchallInactiveContactList();

			}

		} catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
		// Create accordian for client details, begins
	}

	/*
	 * Anoop 18-4-2013 Add client
	 */
	public String addClient()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_basic_configuration");
			if (statusColName != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<InsertDataTable> insertEmpBasicData = new ArrayList<InsertDataTable>();
				InsertDataTable empbasicObj = null;
				boolean status = false;
				boolean empbasicstatus = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

				for (GridDataPropertyView gdp : statusColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

				}
				cbt.createTable22("client_basic_data", Tablecolumesaaa, connectionSpace);

				String acManager = "";
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
					{
						if ("closure_date".equalsIgnoreCase(Parmname))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							insertData.add(ob);
						} else
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}

						if (Parmname.equalsIgnoreCase("acManager"))
						{
							acManager = paramValue;
						}
					}
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && !Parmname.equalsIgnoreCase("acManager") && !Parmname.equalsIgnoreCase("comment") && !Parmname.equalsIgnoreCase("faxNo") && !Parmname.equalsIgnoreCase("companyEmail") && !Parmname.equalsIgnoreCase("mobileNo") && !Parmname.equalsIgnoreCase("referedBy") && !Parmname.equalsIgnoreCase("sourceMaster") && !Parmname.equalsIgnoreCase("starRating") && !Parmname.equalsIgnoreCase("webAddress"))
					{
						empbasicObj = new InsertDataTable();
						empbasicObj.setColName(Parmname);
						empbasicObj.setDataName(paramValue);
						insertEmpBasicData.add(empbasicObj);
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
						} else
						{
							acManager = cId;
							ob.setDataName(acManager);
						}
					} catch (Exception e)
					{
						ob.setDataName(cId);
						e.printStackTrace();
					}
					insertData.add(ob);
				} else
				{
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(cId);
					insertData.add(ob);

					empbasicObj = new InsertDataTable();
					empbasicObj.setColName("userName");
					empbasicObj.setDataName(cId);
					insertEmpBasicData.add(empbasicObj);

				}

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				empbasicObj = new InsertDataTable();
				empbasicObj.setColName("date");
				empbasicObj.setDataName(DateUtil.getCurrentDateUSFormat());
				insertEmpBasicData.add(empbasicObj);

				empbasicObj = new InsertDataTable();
				empbasicObj.setColName("time");
				empbasicObj.setDataName(DateUtil.getCurrentTime());
				insertEmpBasicData.add(empbasicObj);

				// status = cbt.insertIntoTable("client_basic_data",
				// insertData,connectionSpace);
				int maxId = cbt.insertDataReturnId("client_basic_data", insertData, connectionSpace);
				// To get Field names and field values.
				if (maxId > 0)
				{
					StringBuilder fieldsNames = new StringBuilder();
					StringBuilder fieldsValue = new StringBuilder();
					if (insertData != null && !insertData.isEmpty())
					{
						int j = 1;
						for (InsertDataTable h : insertData)
						{
							for (GridDataPropertyView gdp : statusColName)
							{
								if (h.getColName().equalsIgnoreCase(gdp.getColomnName()))
								{
									if (j < insertData.size())
									{
										fieldsNames.append(gdp.getHeadingName() + ", ");
										if (h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()) + ", ");
										} else
										{
											fieldsValue.append(h.getDataName() + ", ");
										}
									} else
									{
										fieldsNames.append(gdp.getHeadingName());
										if (h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
										} else
										{
											fieldsValue.append(h.getDataName());
										}
									}
								}
							}
							j++;
						}
					}
					new UserHistoryAction().userHistoryAdd(empId, "Add", "Sales", "Opportunity", fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
				}

				// cbt.insertIntoTable("user_action_history",
				// insertUserHistoryData, connectionSpace);

				// empbasicstatus = cbt.insertIntoTable("employee_basic",
				// insertEmpBasicData, connectionSpace);
				if (maxId > 0)
				{
					// int idMAx =
					// cbt.getMaxId("client_basic_data",connectionSpace);
					// System.out.println("idMAx>>"+maxId+" maxId>"+maxId);
					LeadActionControlDao lacd = new LeadActionControlDao();
					// for KPI auto achievement for 'Data Collection'
					lacd.insertInToKPIAutofillTable(empId, "1", userName, connectionSpace, String.valueOf(maxId), "Prospective Client");
					// for KPI auto achievement for 'Prospective Clients'
					lacd.insertInToKPIAutofillTable(empId, "6", userName, connectionSpace, String.valueOf(maxId), "Prospective Client");

					DSRgerneration ta = new DSRgerneration();
					int empID = Integer.parseInt((String) session.get("empIdofuser").toString().split("-")[1]);

					if (targetAchieved != null && !targetAchieved.equalsIgnoreCase("") && !targetAchieved.equalsIgnoreCase("-1"))
					{
						ta.setDSRRecords(targetAchieved, empID, DSRMode.KPI, DSRType.ONLINE);
					}
					addActionMessage("Client Added Successfully");
					return SUCCESS;
				} else
				{
					addActionMessage("There is some error in data!");
					return SUCCESS;
				}
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addClient of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// for counter

	public String counter()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				// String query
				// ="select feedback, id from seek_assistance_report_data where id='"+idF+"'";
				query.append("select  count(*)  from client_basic_data as cbd ");
				query.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster   ");
				query.append(" left join location as loc on loc.id = cbd.location ");
				query.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager   ");
				query.append(" left join industrydatalevel1 as i on i.id = cbd.industry  ");
				query.append(" left join subindustrydatalevel2 as si on si.id = cbd.subIndustry   ");
				query.append(" left join forcastcategarymaster as fccm on fccm.id = cbd.forecast_category   ");
				query.append(" left join salesstagemaster as ssm on ssm.id = cbd.sales_stages   ");
				query.append(" left join weightagemaster as w on w.id = cbd.weightage_targetsegment  ");
				query.append(" where  cbd.userName <> '0' and ");

				if (indr != null && !indr.equalsIgnoreCase("-1"))
				{
					query.append(" cbd.industry = '" + indr + "' and  ");
				}
				if (subindr != null && !subindr.equalsIgnoreCase("-1"))
				{
					query.append(" cbd.subIndustry = '" + subindr + "' and ");
				}
				if (!rate.equalsIgnoreCase("-1") && rate != null)
				{
					query.append(" cbd.starRating = '" + rate + "' and ");
				}
				if (!sourceN.equalsIgnoreCase("-1") && sourceN != null)
				{
					query.append(" cbd.sourceMaster = '" + sourceN + "' and ");
				}
				if (!locatn.equalsIgnoreCase("-1") && locatn != null)
				{
					query.append(" cbd.location = '" + locatn + "' and ");
				}

				if (!acmng.equalsIgnoreCase("-1") && acmng != null)
				{
					query.append(" cbd.acManager = '" + acmng + "' and ");
				}
				if (!salesstag.equalsIgnoreCase("-1") && salesstag != null)
				{
					query.append(" cbd.sales_stages like '" + salesstag + "%' and ");
				}
				if (!forcstcat.equalsIgnoreCase("-1") && forcstcat != null)
				{
					query.append(" cbd.forecast_category = '" + forcstcat + "' and ");
				}
				if (!frmdateSear.equalsIgnoreCase("") && frmdateSear != null)
				{
					query.append(" cbd.closure_date = '" + frmdateSear + "' and ");
				}

				query.append("  cbd.id in");

				if (clintSts != null && !clintSts.equalsIgnoreCase("-1"))
				{
					query.append(" (select distinct(ccd.clientName) from client_contact_data as ccd, client_takeaction as cta ,");
					query.append(" client_offering_mapping as com where ccd.id = cta.contacId and ccd.clientName = com.clientName and cta.statusId = '" + clintSts + "' and com.isConverted = 0) ");
				} else
				{
					query.append(" (select distinct(clientName) from client_offering_mapping where isConverted = '0') ");
				}
				query.append(" order by cbd.clientName");
				List dataCount = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setCounter(count.intValue());
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnResult;

		/*
		 * System.out.println(" indr "+indr); counter = "7";
		 * 
		 * return SUCCESS;
		 */
	}

	/*
	 * Anoop 19-4-2013 Fetch data of one level based on other level
	 */
	public String fetchOfferingLevelData()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			if (Offeringlevel.equalsIgnoreCase("1"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("offeringname");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("offeringname", "ASC");
				temp.put("verticalname", offeringId);
				// empSelectParam =
				// cbt.viewAllDataEitherSelectOrAll("offeringlevel2",
				// empSelectParam, temp, connectionSpace);
				empSelectParam = cbt.viewAllDataEitherSelectOrAllByOrder("offeringlevel2", empSelectParam, temp, order, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}

				}

			} else if (Offeringlevel.equalsIgnoreCase("2"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("subofferingname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("offeringname", offeringId);
				empSelectParam = cbt.viewAllDataEitherSelectOrAll("offeringlevel3", empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}

				}
			} else if (Offeringlevel.equalsIgnoreCase("3"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("variantname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("subofferingname", offeringId);
				empSelectParam = cbt.viewAllDataEitherSelectOrAll("offeringlevel4", empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}
			} else if (Offeringlevel.equalsIgnoreCase("4"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("subvariantname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("variantname", offeringId);
				empSelectParam = cbt.viewAllDataEitherSelectOrAll("offeringlevel5", empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: fetchOfferingLevelData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop 22-4-2013 Store client offerings
	 */
	public String addOffering()
	{
		String returnString = "SUCCESS";
		CommonOperInterface coi = new CommonConFactory().createInterface();
		boolean flag = false;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			InsertDataTable ob = null;
			InsertDataTable objOpportunitydetail = null;
			InsertDataTable objClientSupportId = null;
			String clientId = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			int level = 0;

			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Iterator it = requestParameterNames.iterator();

			String clientName = null;
			List<String> paramList = new ArrayList<String>();
			while (it.hasNext())
			{
				String ParmName = it.next().toString();

				if (ParmName.equalsIgnoreCase("clientName"))
				{
					clientName = request.getParameter(ParmName);
				} else
				{
					// get all offering level in a list
					paramList.add(ParmName);
				}

			}

			String tempParamValues[] = null;
			String tempParamValues2[] = null;
			String tempParamValues3[] = null;
			String tempParamValues4[] = null;
			String tempParamValues5[] = null;
			String tempParamValues6[] = null;
			String tempParamValues7[] = null;
			if (paramList.contains("subvariantsize"))
			{
				tempParamValues = request.getParameterValues("subvariantsize");
			} else if (paramList.contains("variantname"))
			{
				tempParamValues = request.getParameterValues("variantname");
			} else if (paramList.contains("subofferingname"))
			{
				tempParamValues = request.getParameterValues("subofferingname");
			} else if (paramList.contains("offeringname"))
			{
				tempParamValues = request.getParameterValues("offeringname");
			} else if (paramList.contains("verticalname"))
			{
				tempParamValues = request.getParameterValues("verticalname");
			}
			if (paramList.contains("opportunity_name"))
			{
				tempParamValues2 = request.getParameterValues("opportunity_name");
			}
			if (paramList.contains("opportunity_value"))
			{
				tempParamValues3 = request.getParameterValues("opportunity_value");
			}
			if (paramList.contains("closure_date"))
			{
				tempParamValues4 = request.getParameterValues("closure_date");
			}
			if (paramList.contains("expectency"))
			{
				tempParamValues5 = request.getParameterValues("expectency");
			}
			if (paramList.contains("forecast_category"))
			{
				tempParamValues6 = request.getParameterValues("forecast_category");
			}
			if (paramList.contains("sales_stages"))
			{
				tempParamValues7 = request.getParameterValues("sales_stages");
			}

			// count: it is to check no. of offering mapped with client
			// successfully
			int count = 0;
			if (tempParamValues.length > 0)
			{
				new ClientHelper().createTableClientOfferingMapping(connectionSpace);

				/*
				 * set one record value for client
				 */
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				ob = new InsertDataTable();
				ob.setColName("clientName");
				ob.setDataName(clientName);
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

				// for date wise closure date records
				List<InsertDataTable> insertDataOpportunityDetail = new ArrayList<InsertDataTable>();
				List<InsertDataTable> insertDataClientSupport = new ArrayList<InsertDataTable>();

				objOpportunitydetail = new InsertDataTable();
				objOpportunitydetail.setColName("clientName");
				objOpportunitydetail.setDataName(clientName);
				insertDataOpportunityDetail.add(objOpportunitydetail);

				/*
				 * objOpportunitydetail = new InsertDataTable();
				 * objOpportunitydetail.setColName("userName");
				 * objOpportunitydetail.setDataName(empId);
				 * insertDataOpportunityDetail.add(objOpportunitydetail);
				 */

				// change for clientName Id
				objOpportunitydetail = new InsertDataTable();
				objOpportunitydetail.setColName("userName");
				String queryC = "select acManager, id from client_basic_data where id='" + clientName + "'";
				List acManagerList = cbt.executeAllSelectQuery(queryC, connectionSpace);
				if (acManagerList != null && acManagerList.size() > 0)
				{
					for (Iterator it1 = acManagerList.iterator(); it1.hasNext();)
					{
						Object[] obdata11 = (Object[]) it1.next();
						if (obdata11[0] != null)
						{
							objOpportunitydetail.setDataName(obdata11[0].toString());
						}
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}

				}

				objOpportunitydetail = new InsertDataTable();
				objOpportunitydetail.setColName("offeringLevelId");
				objOpportunitydetail.setDataName(String.valueOf(level));
				insertDataOpportunityDetail.add(objOpportunitydetail);

				// cbt.insertIntoTable("opportunity_details",insertDataOpportunityDetail,
				// connectionSpace);

				for (int i = 0; i < tempParamValues.length; i++)
				{
					objOpportunitydetail = new InsertDataTable();
					objOpportunitydetail.setColName("offeringId");
					objOpportunitydetail.setDataName(tempParamValues[i]);
					insertDataOpportunityDetail.add(objOpportunitydetail);

					if (null != tempParamValues2)
					{
						objOpportunitydetail = new InsertDataTable();
						objOpportunitydetail.setColName("opportunity_name");
						objOpportunitydetail.setDataName(tempParamValues2[i]);
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}

					if (null != tempParamValues3)
					{
						objOpportunitydetail = new InsertDataTable();
						objOpportunitydetail.setColName("opportunity_value");
						objOpportunitydetail.setDataName(tempParamValues3[i]);
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}

					if (null != tempParamValues4)
					{
						objOpportunitydetail = new InsertDataTable();
						objOpportunitydetail.setColName("closure_date");
						objOpportunitydetail.setDataName(DateUtil.convertDateToUSFormat(tempParamValues4[i]));
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}

					if (null != tempParamValues5)
					{
						objOpportunitydetail = new InsertDataTable();
						objOpportunitydetail.setColName("expectency");
						if (tempParamValues5[i].toString() != null && !tempParamValues5[i].toString().equals("-1") && !tempParamValues5[i].toString().equalsIgnoreCase("undefined"))
						{
							objOpportunitydetail.setDataName(tempParamValues5[i]);
						} else
						{
							objOpportunitydetail.setDataName("Low");
						}
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}
					if (null != tempParamValues6)
					{
						objOpportunitydetail = new InsertDataTable();
						objOpportunitydetail.setColName("forecast_category");
						objOpportunitydetail.setDataName(tempParamValues6[i]);
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}
					if (null != tempParamValues7)
					{
						objOpportunitydetail = new InsertDataTable();
						objOpportunitydetail.setColName("sales_stages");
						objOpportunitydetail.setDataName(tempParamValues7[i]);
						insertDataOpportunityDetail.add(objOpportunitydetail);
					}

					// flag =cbt.insertIntoTable("opportunity_details",
					// insertDataOpportunityDetail, connectionSpace);
					int maxOPDID = coi.insertDataReturnId("opportunity_details", insertDataOpportunityDetail, connectionSpace);
					// System.out.println(" maxOPDID  "+maxOPDID);
					if (maxOPDID > 0)
					{
						objClientSupportId = new InsertDataTable();
						objClientSupportId.setColName("support_type");
						objClientSupportId.setDataName("1");
						insertDataClientSupport.add(objClientSupportId);

						objClientSupportId = new InsertDataTable();
						objClientSupportId.setColName("delivary_emp");
						objClientSupportId.setDataName(empId);
						insertDataClientSupport.add(objClientSupportId);

						objClientSupportId = new InsertDataTable();
						objClientSupportId.setColName("delivary_dept");
						String queryDeptId = "select deptname, id from employee_basic where id='" + empId + "'";
						// System.out.println(" queryDeptId "+queryDeptId);
						List deptIdList = cbt.executeAllSelectQuery(queryDeptId, connectionSpace);
						if (deptIdList != null && deptIdList.size() > 0)
						{
							for (Iterator it1 = deptIdList.iterator(); it1.hasNext();)
							{
								Object[] obdata11 = (Object[]) it1.next();
								objClientSupportId.setDataName(obdata11[0].toString());
								insertDataClientSupport.add(objClientSupportId);
								// System.out.println("obdata11[0].toString() "+obdata11[0].toString());
							}

						}

						objClientSupportId = new InsertDataTable();
						objClientSupportId.setColName("opportunity_detail_id");
						objClientSupportId.setDataName(maxOPDID);
						insertDataClientSupport.add(objClientSupportId);

						boolean status = cbt.insertIntoTable("client_support_details", insertDataClientSupport, connectionSpace);
						if (status)
						{
							insertDataClientSupport.remove(objClientSupportId);
						}

						insertDataOpportunityDetail.remove(insertData);
					}

					objOpportunitydetail = new InsertDataTable();
					objOpportunitydetail.setColName("clientName");
					// System.out.println(">>>>>> " + clientName);
					objOpportunitydetail.setDataName(clientName);
					insertDataOpportunityDetail.add(objOpportunitydetail);

					objOpportunitydetail = new InsertDataTable();
					objOpportunitydetail.setColName("userName");
					objOpportunitydetail.setDataName(empId); // added empId (a/c
					// manager)
					insertDataOpportunityDetail.add(objOpportunitydetail);

					objOpportunitydetail = new InsertDataTable();
					objOpportunitydetail.setColName("offeringLevelId");
					objOpportunitydetail.setDataName(String.valueOf(level));
					insertDataOpportunityDetail.add(objOpportunitydetail);

				}

				for (int i = 0; i < tempParamValues.length; i++)
				{
					ob = new InsertDataTable();
					ob.setColName("offeringId");
					ob.setDataName(tempParamValues[i]);
					insertData.add(ob);
					if (null != tempParamValues2)
					{
						ob = new InsertDataTable();
						ob.setColName("opportunity_name");
						ob.setDataName(tempParamValues2[i]);
						insertData.add(ob);
					}

					if (null != tempParamValues3)
					{
						ob = new InsertDataTable();
						ob.setColName("opportunity_value");
						ob.setDataName(tempParamValues3[i]);
						insertData.add(ob);
					}

					if (null != tempParamValues4)
					{
						ob = new InsertDataTable();
						ob.setColName("closure_date");
						ob.setDataName(tempParamValues4[i]);
						insertData.add(ob);
					}
					if (null != tempParamValues5)
					{
						ob = new InsertDataTable();
						ob.setColName("expectency");
						if (tempParamValues5[i].toString() != null && !tempParamValues5[i].toString().equals("-1") && !tempParamValues5[i].toString().equalsIgnoreCase("undefined"))
						{
							objOpportunitydetail.setDataName(tempParamValues5[i]);
						} else
						{
							objOpportunitydetail.setDataName("Low");
						}
						insertData.add(ob);
					}
					if (null != tempParamValues6)
					{
						ob = new InsertDataTable();
						ob.setColName("forecast_category");
						ob.setDataName(tempParamValues6[i]);
						insertData.add(ob);
					}
					if (null != tempParamValues7)
					{
						ob = new InsertDataTable();
						ob.setColName("sales_stages");
						ob.setDataName(tempParamValues7[i]);
						insertData.add(ob);
					}

					flag = cbt.insertIntoTable("client_offering_mapping", insertData, connectionSpace);
					if (flag)
					{
						insertData.remove(insertData);
						count++;
					}

					ob = new InsertDataTable();
					ob.setColName("clientName");
					ob.setDataName(clientName);
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

				}
			}
			if (count > 0)
			{
				addActionMessage(count + " Client offering saved successfully");
				returnString = SUCCESS;
			} else
			{
				addActionMessage("Oops There is some error in data!");
				returnString = SUCCESS;
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addOffering of class " + getClass(), e);
			e.printStackTrace();
		}
		return returnString;
	}

	public String addContacts()
	{
		String returnString = ERROR;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			CommunicationHelper COH = new CommunicationHelper();
			List<GridDataPropertyView> clientColName = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");

			if (clientColName != null)
			{
				// List <InsertDataTable> insertData=new
				// ArrayList<InsertDataTable>();
				boolean status = false;
				boolean empbasic_status = false;
				InsertDataTable ob = null;
				List<TableColumes> tableColumn = new ArrayList<TableColumes>();
				List<InsertDataTable> insertInEmpBasic = new ArrayList<InsertDataTable>();
				InsertDataTable empBasicObj = null;
				for (GridDataPropertyView gdp : clientColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					tableColumn.add(ob1);

				}

				cbt.createTable22("client_contact_data", tableColumn, connectionSpace);

				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();

				List paramList = new ArrayList<String>();
				int paramValueSize = 0;
				boolean statusTemp = true;
				String associateName = null, mobile = null, email = null, name = null;
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);

					if (paramValue != null && Parmname != null && !Parmname.equalsIgnoreCase("__checkbox_sms") && !Parmname.equalsIgnoreCase("__checkbox_mail") && !Parmname.equalsIgnoreCase("mail") && !Parmname.equalsIgnoreCase("sms"))
					{

						paramList.add(Parmname);

						// assign client name into variable
						if (Parmname.equalsIgnoreCase("clientName"))
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
								if (H != null)
									paramValueSize++;
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
							// //System.out.println("paramValue[j]" +
							// paramValue[j]);
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
							// //System.out.println("parmValuew[k][i]>>" +
							// parmValuew[k][i]);
							valueSelect = true;

						} else
						{
							valueSelect = false;
						}

						if (valueSelect)
						{
							if (!paramList.get(k).toString().equalsIgnoreCase("clientName") && !paramList.get(k).toString().equalsIgnoreCase("contactType"))
							{
								ob = new InsertDataTable();
								empBasicObj = new InsertDataTable();
								// //System.out.println("Column name " +
								// paramList.get(k).toString());
								// //System.out.println("Data name " +
								// parmValuew[k][i]);

								if (paramList.get(k).toString().equalsIgnoreCase("birthday") || paramList.get(k).toString().equalsIgnoreCase("anniversary"))
								{
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(DateUtil.convertDateToUSFormat(parmValuew[k][i]));
									empBasicObj.setColName(paramList.get(k).toString());
									empBasicObj.setDataName(DateUtil.convertDateToUSFormat(parmValuew[k][i]));

								}

								else
								{

									// set all contact detail into object

									/*
									 * if(paramList.get(k).toString().
									 * equalsIgnoreCase ("personName") ||
									 * paramList.get(k).toString
									 * ().equalsIgnoreCase("existing_free") ||
									 * paramList
									 * .get(k).toString().equalsIgnoreCase
									 * ("contactName") ) { //
									 * paramList.get(k).toString();
									 * ob.setColName
									 * (paramList.get(k).toString());
									 * ob.setDataName(parmValuew[k][i]);
									 */
									// insertData.add(ob);
									// System.out.println("column in name**********"+paramList.get(k).toString());
									// .out.println("value in name***********"+parmValuew[k][i]);
									ob = new InsertDataTable();
									ob.setColName(paramList.get(k).toString());
									ob.setDataName(parmValuew[k][i]);

									// System.out.println("column name**********"+paramList.get(k).toString());
									// System.out.println("value name***********"+parmValuew[k][i]);

									if (paramList.get(k).toString().equalsIgnoreCase("communicationName"))
									{
										empBasicObj.setColName("comName");
										empBasicObj.setDataName(parmValuew[k][i]);
										insertInEmpBasic.add(empBasicObj);
									} else if (paramList.get(k).toString().equalsIgnoreCase("emailOfficial"))
									{
										empBasicObj.setColName("emailIdOne");
										empBasicObj.setDataName(parmValuew[k][i]);
										email = parmValuew[k][i];
										insertInEmpBasic.add(empBasicObj);
									} else if (paramList.get(k).toString().equalsIgnoreCase("contactNo"))
									{
										empBasicObj.setColName("mobOne");
										empBasicObj.setDataName(parmValuew[k][i]);
										mobile = parmValuew[k][i];
										insertInEmpBasic.add(empBasicObj);
									}

									else if (paramList.get(k).toString().equalsIgnoreCase("department"))
									{
										empBasicObj.setColName("deptname");
										empBasicObj.setDataName(new ClientHelper3().fetchClientDept());

										insertInEmpBasic.add(empBasicObj);
									}

									else if (paramList.get(k).toString().equalsIgnoreCase("personName"))
									{
										empBasicObj.setColName("empName");
										empBasicObj.setDataName(parmValuew[k][i]);
										name = parmValuew[k][i];
										insertInEmpBasic.add(empBasicObj);
									} else
									{
										empBasicObj.setColName(paramList.get(k).toString());
										empBasicObj.setDataName(parmValuew[k][i]);
										insertInEmpBasic.add(empBasicObj);
									}

								}
								insertData.add(ob);

							}

						}
					}
					// add client into contact object
					if (valueSelect)
					{
						ob = new InsertDataTable();
						ob.setColName("clientName");
						// //System.out.println("associateName " +
						// associateName);
						ob.setDataName(associateName);
						insertData.add(ob);
						// for show the status of contact(active inactive)
						ob = new InsertDataTable();
						ob.setColName("currentStatus");
						ob.setDataName("1");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("currentStatusDate");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
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

						// for empBasic
						empBasicObj = new InsertDataTable();
						empBasicObj.setColName("clientName");
						empBasicObj.setDataName(associateName);
						insertInEmpBasic.add(empBasicObj);

						empBasicObj = new InsertDataTable();
						empBasicObj.setColName("groupId");
						empBasicObj.setDataName(new CommonHelper().fetchEntityGroupId(EntityType.CLIENT));
						insertInEmpBasic.add(empBasicObj);

						empBasicObj = new InsertDataTable();
						empBasicObj.setColName("userName");
						empBasicObj.setDataName(cId);
						insertInEmpBasic.add(empBasicObj);

						empBasicObj = new InsertDataTable();
						empBasicObj.setColName("date");
						empBasicObj.setDataName(DateUtil.getCurrentDateUSFormat());
						insertInEmpBasic.add(empBasicObj);

						empBasicObj = new InsertDataTable();
						empBasicObj.setColName("time");
						empBasicObj.setDataName(DateUtil.getCurrentTime());
						insertInEmpBasic.add(empBasicObj);
						String mm = request.getParameter("mail");
						String sm = request.getParameter("sms");

						ob = new InsertDataTable();
						ob.setColName("mail");
						ob.setDataName(mm);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("sms");
						ob.setDataName(sm);
						insertData.add(ob);

						status = cbt.insertIntoTable("client_contact_data", insertData, connectionSpace);
						if (status)
						{

							String query = "select id FROM client_contact_data order by id desc limit 0,1";
							List data4escalate = cbt.executeAllSelectQuery(query, connectionSpace);
							if (data4escalate.size() > 0 && data4escalate.get(0) != null)
							{
								String trace = data4escalate.get(0).toString();
								boolean putsmsstatus = false, putmailstatus = false;
								if (mm.equalsIgnoreCase("true"))
								{
									if (name != null && !name.equals("") && !name.equals("NA") && !email.equals("") && !email.equals("NA"))
									{
										String report_sms = null;
										report_sms = "Dear " + name + ", please visit 192.168.1.51:8080/over2cloud/patientFeedback.action?traceid=" + trace + " for giving your valuable feedback";
										String mailtext = new ClientHelper().mail_text(name, trace);
										putmailstatus = COH.addMail(name, "", email, "Online Feedback", mailtext, "", "Pending", "0", "", "WFPM", connectionSpace);
									}
								}
								if (sm.equalsIgnoreCase("True"))
								{
									if (name != null && !name.equals("") && !name.equals("NA") && !mobile.equals("") && !mobile.equals("NA"))
									{
										String report_sms = null;
										report_sms = "Dear " + name + ", please visit 192.168.1.51:8080/over2cloud/patientFeedback.action?traceid=" + trace + " for giving your valuable feedback";
										if (mobile != null && mobile != "" && mobile != "NA" && mobile.length() == 10 && (mobile.startsWith("9") || mobile.startsWith("8") || mobile.startsWith("7")))
										{
											putsmsstatus = COH.addMessage(name, " ", mobile, report_sms, "Online Feedback", "Pending", "0", "WFPM", connectionSpace);
										}
									}
								}
								// delete free contact after active with new
								// opportunity //by Azad
								String freeContactId = request.getParameter("existing_free");
								if (freeContactId != null && !freeContactId.equalsIgnoreCase("") && !freeContactId.equalsIgnoreCase("-1"))
								{
									cbt.deleteAllRecordForId("client_contact_data", "id", freeContactId, connectionSpace);
								}
								String contactType = request.getParameter("contactType");
								if (contactType.equalsIgnoreCase("new_contact"))
								{
									empbasic_status = cbt.insertIntoTable("employee_basic", insertInEmpBasic, connectionSpace);
								}

							}
						}

						if (status)
						{
							addActionMessage("Client contact saved successfully");
							returnString = SUCCESS;
						} else
						{
							addActionMessage("There is some error in data");
							returnString = SUCCESS;
						}
					}
				}
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addContacts of class " + getClass(), e);
			e.printStackTrace();
		}
		return returnString;
	}

	public String beforeClientView()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			ClientHelper3 ch3 = new ClientHelper3();
			setClientViewProp();
			setClientContactViewProp();
			industryList = new LeadHelper().fetchIndustryList(connectionSpace);
			sourceList = new LeadHelper().fetchSourceNameList(connectionSpace);
			locationList = new LeadHelper().fetchLocationList(connectionSpace);
			// weightageList= new
			// LeadHelper().fetchWeightageList(connectionSpace);
			// System.out.println("weightage list========"+weightageList.size());
			clientstatuslist = new ClientHelper3().fetchClientStatus();
			employee_basicMap = new LinkedHashMap<String, String>();
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
			salesStageMap = new LinkedHashMap<String, String>();
			salesStageMap = ch3.fetchsalesStage();
			forecastcategoryMap = new LinkedHashMap<String, String>();
			forecastcategoryMap = ch3.fetchForcastcategory();

			// 9 Aug by Azad
			StringBuilder query2 = new StringBuilder("");
			String cIdUserType = new CommonHelper().getEmpTypeByUserName(CommonHelper.moduleName, userName, connectionSpace);
			CommonHelper ch = new CommonHelper();
			String cIdAll = ch.getHierarchyContactIdByEmpId(empId);
			if (isExistingClient.equalsIgnoreCase("0")) // Prospective client
			{
				if (cIdUserType.equalsIgnoreCase("M"))
				{
					query2.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '0') ");
				} else
				{
					query2.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '0') and userName in(" + cIdAll + ") ");
				}
				totalRecord = countRecord(query2.toString());
			} else if (isExistingClient.equalsIgnoreCase("1"))
			{// Existing
				if (Constant.M.equalsIgnoreCase(cIdUserType))
				{
					query2.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '1') ");
				} else
				{
					query2.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '1') and userName in(" + cIdAll + ") ");
				}
				totalRecord = countRecord(query2.toString());
			} else if (isExistingClient.equalsIgnoreCase("2"))
			{// Lost
				// client
				if (Constant.M.equalsIgnoreCase(cIdUserType))
				{
					query2.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '2')");
				} else
				{
					query2.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '2') and userName in(" + cIdAll + ") ");
				}
				totalRecord = countRecord(query2.toString());

			}
			// System.out.println("query count================="+query2);
			// totalRecord=countRecord(query2.toString());

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeClientView of class " + getClass(), e);
			e.printStackTrace();
		}
		// //System.out.println(
		// "beforeClientView()========================================================================="
		// );
		return SUCCESS;
	}

	// function for count
	public Integer countRecord(String query)
	{
		BigInteger totalRecord = new BigInteger("3");
		List listData = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		listData = cbt.executeAllSelectQuery(query, connectionSpace);
		if (listData != null)
		{
			for (Iterator iterator = listData.iterator(); iterator.hasNext();)
			{

				totalRecord = (BigInteger) iterator.next();
			}
		}

		return totalRecord.intValue();
	}

	public void setClientViewProp() throws Exception
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			List<String> listDataToShow = new ClientHelper().getClientBasicDataToShow();
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_basic_configuration");

			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("clientName"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("opportunity_brief"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("starRating"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
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
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("sales_stages"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("forecast_category"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
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
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{

				if (gdp.getColomnName().equalsIgnoreCase("weightage_targetsegment"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);

				}

			}
			for (GridDataPropertyView gdp : returnResult)
			{

				if (gdp.getColomnName().equalsIgnoreCase("sourceMaster"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
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
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
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
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("acManager"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			// this all hide on 21aug2014 as new view sequence
			/*
			 * for (GridDataPropertyView gdp : returnResult) {
			 * if(gdp.getColomnName().equalsIgnoreCase("phoneNo")) { gpv = new
			 * GridDataPropertyView(); gpv.setColomnName(gdp.getColomnName());
			 * gpv.setHeadingName(gdp.getHeadingName());
			 * gpv.setEditable(gdp.getEditable());
			 * gpv.setSearch(gdp.getSearch());
			 * gpv.setHideOrShow(gdp.getHideOrShow());
			 * gpv.setAlign(gdp.getAlign()); gpv.setWidth(gdp.getWidth()); if
			 * (gdp.getFormatter() != null)
			 * gpv.setFormatter(gdp.getFormatter()); masterViewProp.add(gpv); }
			 * }
			 */

			/*
			 * for (GridDataPropertyView gdp : returnResult) {
			 * if(gdp.getColomnName().equalsIgnoreCase("webAddress")) { gpv =
			 * new GridDataPropertyView();
			 * gpv.setColomnName(gdp.getColomnName());
			 * gpv.setHeadingName(gdp.getHeadingName());
			 * gpv.setEditable(gdp.getEditable());
			 * gpv.setSearch(gdp.getSearch());
			 * gpv.setHideOrShow(gdp.getHideOrShow());
			 * gpv.setAlign(gdp.getAlign()); gpv.setWidth(gdp.getWidth()); if
			 * (gdp.getFormatter() != null)
			 * gpv.setFormatter(gdp.getFormatter()); masterViewProp.add(gpv); }
			 * }
			 */

			/*
			 * for (GridDataPropertyView gdp : returnResult) {
			 * if(gdp.getColomnName().equalsIgnoreCase("sourceMaster")) { gpv =
			 * new GridDataPropertyView();
			 * gpv.setColomnName(gdp.getColomnName());
			 * gpv.setHeadingName(gdp.getHeadingName());
			 * gpv.setEditable(gdp.getEditable());
			 * gpv.setSearch(gdp.getSearch());
			 * gpv.setHideOrShow(gdp.getHideOrShow());
			 * gpv.setAlign(gdp.getAlign()); gpv.setWidth(gdp.getWidth()); if
			 * (gdp.getFormatter() != null)
			 * gpv.setFormatter(gdp.getFormatter()); masterViewProp.add(gpv); }
			 * }
			 */

			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("opportunity_value"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setAlign(gdp.getAlign());
					gpv.setWidth(gdp.getWidth());
					if (gdp.getFormatter() != null)
						gpv.setFormatter(gdp.getFormatter());
					masterViewProp.add(gpv);
				}
			}
			/*
			 * for (GridDataPropertyView gdp : returnResult) {
			 * if(gdp.getColomnName().equalsIgnoreCase("closure_date")) { gpv =
			 * new GridDataPropertyView();
			 * gpv.setColomnName(gdp.getColomnName());
			 * gpv.setHeadingName(gdp.getHeadingName());
			 * gpv.setEditable(gdp.getEditable());
			 * gpv.setSearch(gdp.getSearch());
			 * gpv.setHideOrShow(gdp.getHideOrShow());
			 * gpv.setAlign(gdp.getAlign()); gpv.setWidth(gdp.getWidth()); if
			 * (gdp.getFormatter() != null)
			 * gpv.setFormatter(gdp.getFormatter()); masterViewProp.add(gpv); }
			 * }
			 */

		} catch (Exception e)
		{

			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setClientViewProp of class " + getClass(), e);
			e.printStackTrace();
		}
	}

	public void setClientContactViewProp() throws Exception
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewPropForContact.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
			/*
			 * List listToShow= new ClientHelper().getClientContactDataToShow();
			 * for (Iterator it = returnResult.iterator(); it.hasNext();) { //
			 * generating the dyanamic query based on selected fields Object
			 * obdata = (Object) it.next(); if (obdata != null) { if
			 * (!listToShow.contains(obdata.toString())) { it.remove();
			 * continue; } } }
			 */
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				if (gdp.getColomnName().equalsIgnoreCase("personName") || gdp.getColomnName().equalsIgnoreCase("designation") || gdp.getColomnName().equalsIgnoreCase("department") || gdp.getColomnName().equalsIgnoreCase("communicationName") || gdp.getColomnName().equalsIgnoreCase("degreeOfInfluence ") || gdp.getColomnName().equalsIgnoreCase("contactNo") || gdp.getColomnName().equalsIgnoreCase("emailOfficial") || gdp.getColomnName().equalsIgnoreCase("currentStatus") || gdp.getColomnName().equalsIgnoreCase("currentStatusDate"))
				{
					gpv.setColomnName(gdp.getColomnName());

					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					if (gdp.getFormatter() != null && !gdp.getFormatter().equalsIgnoreCase(""))
					{
						gpv.setFormatter(gdp.getFormatter());
					}
					masterViewPropForContact.add(gpv);
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setClientContactViewProp of class " + getClass(), e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewClientGrid()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonHelper ch = new CommonHelper();
			String cIdAll = ch.getHierarchyContactIdByEmpId(empId);

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			String cIdUserType = new CommonHelper().getEmpTypeByUserName(CommonHelper.moduleName, userName, connectionSpace);
			// //System.out.println(isExistingClient + "\n" + lostFlag + "\n" +
			// clientStatus);
			if (clientStatus != null && !clientStatus.equalsIgnoreCase("0") && !clientStatus.equalsIgnoreCase(""))
			{
				query1.append(" select count(*) from client_basic_data where id in (select distinct(ccd.clientName) from " + "client_contact_data as ccd, " + "client_takeaction as cta ,client_offering_mapping as com where ccd.id = cta.contacId " + "and ccd.clientName = com.clientName and cta.statusId = '" + clientStatus + "' " + "and com.isConverted = 0) ");
			} else
			{
				if (isExistingClient.equalsIgnoreCase("0"))
				{// Prospective
					// client
					if (cIdUserType.equalsIgnoreCase("M"))
					{
						query1.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '0') ");
					} else
					{
						query1.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '0') and userName in(" + cIdAll + ") ");
					}

				} else if (isExistingClient.equalsIgnoreCase("1"))
				{// Existing
					// client

					if (Constant.M.equalsIgnoreCase(cIdUserType))
					{
						query1.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '1') ");
					} else
					{
						query1.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '1') and userName in(" + cIdAll + ") ");
					}

				} else if (isExistingClient.equalsIgnoreCase("2"))
				{// Lost
					// client
					if (Constant.M.equalsIgnoreCase(cIdUserType))
					{
						query1.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '2')");
					} else
					{
						query1.append("select count(*) from client_basic_data where id in (select distinct(clientName) from client_offering_mapping where isConverted = '2') and userName in(" + cIdAll + ") ");
					}

				}
			}
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			// ////System.out.println("dataCount:"+dataCount);
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
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from client_basic_data as cbd ");

				List fieldNames = Configuration.getColomnList("mapped_client_configuration", accountID, connectionSpace, "client_basic_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				List<String> listDataToShow = new ClientHelper().getClientBasicDataToShow();
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

						if (obdata.toString().equalsIgnoreCase("sourceMaster"))
						{
							queryTemp.append(" sm.sourceName 'sourceMaster', ");
							queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster ");
						} else if (obdata.toString().equalsIgnoreCase("industry"))
						{
							queryTemp.append(" i.industry 'industry', ");
							queryEnd.append(" left join industrydatalevel1 as i on i.id = cbd.industry ");
						} else if (obdata.toString().equalsIgnoreCase("weightage_targetsegment"))
						{
							queryTemp.append(" w.weightageName 'weightage_targetsegment', ");
							queryEnd.append(" left join weightagemaster as w on w.id = cbd.weightage_targetsegment ");
						}

						else if (obdata.toString().equalsIgnoreCase("subIndustry"))
						{
							queryTemp.append(" si.subIndustry 'subIndustry', ");
							queryEnd.append(" left join subindustrydatalevel2 as si on si.id = cbd.subIndustry ");
						} else if (obdata.toString().equalsIgnoreCase("status"))
						{
							queryTemp.append(" cs.statusName 'status', ");
							queryEnd.append(" left join client_status as cs on cs.id = cbd.status ");
						}

						else if (obdata.toString().equalsIgnoreCase("sales_stages"))
						{
							queryTemp.append(" ssm.salesstageName 'sales_stages', ");
							queryEnd.append(" left join salesstagemaster as ssm on ssm.id = cbd.sales_stages ");
						} else if (obdata.toString().equalsIgnoreCase("forecast_category"))
						{
							queryTemp.append(" fccm.forcastName 'forecast_category', ");
							queryEnd.append(" left join forcastcategarymaster as fccm on fccm.id = cbd.forecast_category ");
						}

						else if (obdata.toString().equalsIgnoreCase("location"))
						{
							queryTemp.append(" loc.name 'location', ");
							queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						} else if (obdata.toString().equalsIgnoreCase("acManager"))
						{
							queryTemp.append(" cbd1.empName 'acManager', ");
							queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						} else
						{
							queryTemp.append("cbd." + obdata.toString() + ",");
						}
					}
				}

				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				if (Constant.M.equalsIgnoreCase(cIdUserType))
				{
					query.append(" cbd.userName <> '0' ");
				} else
				{
					query.append(" cbd.userName IN(");
					query.append(cIdAll);
					query.append(") ");
				}

				if (industry != null && !industry.equals("-1"))
				{
					query.append("and cbd.industry = '");
					query.append(industry);
					query.append("' ");
				}
				if (subIndustry != null && !subIndustry.equals("-1"))
				{
					query.append("and cbd.subIndustry = '");
					query.append(subIndustry);
					query.append("' ");
				}
				if (starRating != null && !starRating.equals("-1"))
				{
					query.append("and cbd.starRating = '");
					query.append(starRating);
					query.append("' ");
				}
				if (sourceName != null && !sourceName.equals("-1"))
				{
					query.append("and cbd.sourceMaster = '");
					query.append(sourceName);
					query.append("' ");
				}
				/*
				 * if (sourceName != null && !sourceName.equals("-1")) {
				 * query.append("and cbd.sourceMaster = '");
				 * query.append(sourceName); query.append("' "); }
				 */
				if (location != null && !location.equals("-1"))
				{
					query.append("and cbd.location = '");
					query.append(location);
					query.append("' ");
				}
				if (acManager != null && !acManager.equals("-1") && !acManager.equals("undefined"))
				{
					query.append("and cbd.acManager ='");
					query.append(acManager);
					query.append("' ");
				}
				if (client_Name != null && !client_Name.equals(""))
				{
					query.append("and cbd.clientName like '");
					query.append(client_Name);
					query.append("%' ");
				}
				if (salesstages != null && !salesstages.equals("-1"))
				{
					query.append("and cbd.sales_stages like '");
					query.append(salesstages);
					query.append("%' ");
				}
				if (forecastCategary != null && !forecastCategary.equals("-1"))
				{
					query.append("and cbd.forecast_category = '");
					query.append(forecastCategary);
					query.append("' ");

				}
				if (fromDateSearch != null && !fromDateSearch.equals(""))
				{
					query.append("and cbd.closure_date = '");
					query.append(fromDateSearch);
					query.append("' ");

				}
				query.append(" and ");
				if (clientstatus != null && !clientstatus.equalsIgnoreCase("-1") && !clientstatus.equalsIgnoreCase(""))
				{
					query.append(" cbd.id in (select distinct(ccd.clientName) from client_contact_data as ccd, " + "client_takeaction as cta ,client_offering_mapping as com where ccd.id = cta.contacId " + "and ccd.clientName = com.clientName and cta.statusId = '" + clientstatus + "' " + "and com.isConverted = 0) ");
				} else
				{
					if (isExistingClient.equalsIgnoreCase("0"))
					{// Prospective
						// client
						query.append(" cbd.id in (select distinct(clientName) from client_offering_mapping where isConverted = '0')");
					} else if (isExistingClient.equalsIgnoreCase("1"))
					{// Existing
						// client
						query.append(" cbd.id in (select distinct(clientName) from client_offering_mapping where isConverted = '1')");
					} else if (isExistingClient.equalsIgnoreCase("2"))
					{// Lost
						// client
						query.append(" cbd.id in (select distinct(clientName) from client_offering_mapping where isConverted = '2')");
					}
				}
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceMaster"))
					{
						setSearchField(" sm.sourceName ");
					} else if (getSearchField().toString().equalsIgnoreCase("status"))
					{
						setSearchField(" cs.statusName ");
					} else if (getSearchField().toString().equalsIgnoreCase("location"))
					{
						setSearchField(" loc.name ");
					} else if (getSearchField().toString().equalsIgnoreCase("acManager"))
					{
						setSearchField(" cbd1.empName ");
					} else
					{
						setSearchField(" cbd." + getSearchField());
					}

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

				/*
				 * if (getSord() != null && !getSord().equalsIgnoreCase("")) {
				 * if (getSord().equals("asc") && getSidx() != null &&
				 * !getSidx().equals("")) { query.append(" order by " +
				 * getSidx()); } if (getSord().equals("desc") && getSidx() !=
				 * null && !getSidx().equals("")) { query.append(" order by " +
				 * getSidx() + " DESC"); } }
				 */

				query.append(" order by cbd.clientName limit " + from + "," + to);
				// System.out.println("query>>>fffffffff>" + query.toString());
				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "client_basic_data", connectionSpace);

				// System.out.println("query:::" + query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				// int total =
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						String clientname = "";
						String type = "";

						String clientId = "";
						double weightage = 0;
						ClientHelper2 ch2 = new ClientHelper2();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] == null || obdata[k].toString().equalsIgnoreCase(""))
							{
								obdata[k] = fieldNames.get(k).toString().equalsIgnoreCase("weightage") ? "0.0" : "NA";
							}
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									clientId = obdata[k].toString();
								} else
								{
									if ("clientName".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										clientname = obdata[k].toString();
										// ////System.out.println("clientname:"+
										// clientname);
									} else if (fieldNames.get(k).toString().equalsIgnoreCase("weightage"))
									{
										try
										{
											// calculate weightage
											weightage = ch2.calculateOverallWeightage(cbt, clientId, EntityType.CLIENT);
											obdata[k] = weightage;
											/* type = obdata[k].toString(); */
										} catch (Exception e)
										{

										}
									} else if ("closure_date".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									} else if ("opportunity_value".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										grandtotal = Long.parseLong(obdata[k].toString());
										grandtotalsum += grandtotal;
									}

									else if (fieldNames.get(k).toString().equalsIgnoreCase("referedBy"))
									{
										type = obdata[k].toString();
									} else if (fieldNames.get(k).toString().equalsIgnoreCase("refName"))
									{
										String queryName = "";
										String _id = obdata[k].toString().trim();
										if (type.trim().equalsIgnoreCase("Client"))
										{
											queryName = " select id,clientName from client_basic_data where id = " + _id;
										} else if (type.trim().equalsIgnoreCase("Associate"))
										{
											queryName = " select id,associateName from associate_basic_data where id = " + _id;
										}

										if (!queryName.equalsIgnoreCase(""))
										{
											List nameList = cbt.executeAllSelectQuery(queryName, connectionSpace);
											if (nameList != null)
											{
												for (Object obData : nameList)
												{
													Object[] ob = (Object[]) obData;
													if (ob != null && ob.length > 0)
													{
														obdata[k] = ob[1];
														break;
													}
												}
											}
										}
									} else if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
						}

						one.put("history", clientname);
						Listhb.add(one);
					}
					userdata.put("clientName", "Total :");
					userdata.put("opportunity_value", grandtotalsum);
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: viewClientGrid of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewModifyClient()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

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
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("client_basic_data", wherClause, condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
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
				cbt.deleteAllRecordForId("client_basic_data", "id", condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: viewModifyClient of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewModifyClientContact()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

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
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("client_contact_data", wherClause, condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
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
				cbt.deleteAllRecordForId("client_contact_data", "id", condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: viewModifyClientContact of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewClientContactGrid()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from client_contact_data where clientName = " + id);
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
				query.append("select ");
				List fieldNames = Configuration.getColomnList("mapped_client_configuration", accountID, connectionSpace, "client_contact_configuration");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata.toString().equalsIgnoreCase("clientName"))
					{
						if (i < fieldNames.size() - 1)
							query.append("cleintbasic.clientName,");
						else
							query.append("cleintbasic.clientName");
					} else if (obdata.toString().equalsIgnoreCase("department"))
					{
						if (i < fieldNames.size() - 1)
							query.append("dept.deptName 'dName',");
						else
							query.append("dept.deptName 'dName'");
					} else
					{
						if (i < fieldNames.size() - 1)
							query.append("cmaster." + obdata.toString() + ",");
						else
							query.append("cmaster." + obdata.toString());
					}
					i++;

				}

				query.append(" from client_contact_data as cmaster inner join client_basic_data as cleintbasic on cmaster.clientName=cleintbasic.id");
				query.append(" left join department as dept on dept.id = cmaster.department ");
				query.append(" where cmaster.clientName = " + id);
				// query by Azad
				// query.append(" and cmaster.currentStatus != '0'");
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and ");
						query.append(" cmaster." + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and ");
						query.append(" cmaster." + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and ");
						query.append(" cmaster." + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and ");
						query.append(" cmaster." + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and ");
						query.append(" cmaster." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by cmaster." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by cmaster." + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "client_contact_data", connectionSpace);

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
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								} else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date") || fieldNames.get(k).toString().equalsIgnoreCase("birthday") || fieldNames.get(k).toString().equalsIgnoreCase("anniversary") || fieldNames.get(k).toString().equalsIgnoreCase("currentStatusDate"))
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									else if (fieldNames.get(k).toString().equalsIgnoreCase("currentStatus")) // show
									// current
									// status

									{
										if (obdata[k].toString().equalsIgnoreCase("1"))
										{
											status = "Active";
										} else if (obdata[k].toString().equalsIgnoreCase("0"))
										{
											status = "Inactive";
										} else
										{
											status = "NA";
										}

										one.put(fieldNames.get(k).toString(), status);

									} else
										one.put(fieldNames.get(k).toString(), // this
												// show
												// all
												// selected
												// fields
												obdata[k].toString());
								}
							}
						}
						Listhb.add(one);
					}

					setMasterViewListForContact(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: viewClientContactGrid of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeContactTakeAction()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			if (session.get("isExistingClient") != null)
			{
				isExistingClient = session.get("isExistingClient").toString();
			}

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			CommonHelper ch = new CommonHelper();
			ClientHelper3 clh = new ClientHelper3();
			String[] tempOffering = ch.getOfferingName();

			StringBuilder query = new StringBuilder();
			query.append("select co.id, co.");
			query.append(tempOffering[0]);
			query.append(" from ");
			query.append(tempOffering[1]);
			query.append(" as co, client_offering_mapping as com where co.id = com.offeringId and com.offeringLevelId = '");
			query.append(tempOffering[2]);
			query.append("' and com.clientName = '");
			query.append(id);
			query.append("' and com.isConverted = '0' order by co.subofferingname ");

			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			// Build offering map
			offeringList = CommonHelper.convertListToMap(data, false);

			StringBuilder queryForContact = new StringBuilder();
			queryForContact.append("select id, personName from client_contact_data where clientName = '");
			queryForContact.append(id);
			queryForContact.append("' order by personName");
			List dataForContact = cbt.executeAllSelectQuery(queryForContact.toString(), connectionSpace);
			// Build contact person map
			contactPersonMap = CommonHelper.convertListToMap(dataForContact, false);

			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			// Build employee map
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
			salesStageMap = new LinkedHashMap<String, String>();
			salesStageMap = clh.fetchsalesStage();

			StringBuffer query1 = new StringBuffer();
			query1.append("select id,statusName from client_status order by statusCode");

			data = null;
			data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			// Build client status map
			clientStatusList = CommonHelper.convertListToMap(data, false);

			// opportunity details..
			String opportunitydetailstoshow = clh.fetchOpportunityDetailsToShow(id, empId);
			// System.out.println("opportunitydetailstoshow>" +
			// opportunitydetailstoshow);

			if (opportunitydetailstoshow != null)
			{

				String[] object = opportunitydetailstoshow.split("#");
				opportunity_name = object[0];
				opportunity_value = object[1];
				closure_date = object[2];
				// System.out.println("opportunity_name>" + opportunity_name);
				// System.out.println("opportunity_value>" + opportunity_value);
				// System.out.println("closure_date>" + closure_date);
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeContactTakeAction of class " + getClass(), e);
			e.printStackTrace();
		}
		if (getReturntype() != null && getReturntype().equalsIgnoreCase("createActivityFullView"))
		{
			return "crActivityFullView";
		} else
		{
			return SUCCESS;
		}

	}

	@SuppressWarnings("unchecked")
	public String takeActionOnClient()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			// System.out.println("id:"+id);
			String contId = null;
			CommonOperInterface coi = new CommonConFactory().createInterface();
			// create client_takeaction table
			ClientHelper ch = new ClientHelper();
			ch.createTableClientTakeAction(connectionSpace);
			ch.createTableClientTakeActionMappedEmployee(connectionSpace);
			// end creating client_takeaction table
			// System.out.println("contactId:" + contactId);
			// System.out.println("empId:" + empId);
			// System.out.println("offeringId:" + offeringId);

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			List<InsertDataTable> insertDataME = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			String[] contactIdArray = CommonHelper.getCommaSeparatedToArray(contactId);
			String[] empIdArray = CommonHelper.getCommaSeparatedToArray(empId);
			/*
			 * for(int i=0;i<empIdArray.length; i++) {
			 * 
			 * 
			 * String userid = new CommonHelper().fetchUserID(empIdArray[i],
			 * connectionSpace); contId = new
			 * CommonHelper().getEmpDetailsByUserId( CommonHelper.moduleName,
			 * userid, connectionSpace)[0];
			 * 
			 * System.out.println("empIdArray>"+empIdArray[i]+" contId>"+contId+
			 * "  userid   "+userid); }
			 */
			// System.out.println(date);
			if (date != null && date != " " && !date.equalsIgnoreCase("undifined") && !date.equals("-1"))
			{
				String[] dateVal = date.split(" ");// dd-mm-yyyy hh:mm
				date = DateUtil.convertDateToUSFormat(dateVal[0].trim()) + " " + dateVal[1];
			}
			// System.out.println("date:" + date);
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
				ob.setColName("to_maxDateTime");
				ob.setDataName(to_maxDateTime);
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
				ob.setColName("sales_stages");
				ob.setDataName(sales_stages);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("compelling_reason");
				ob.setDataName(compelling_reason);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				// boolean statusFlag =
				// coi.insertIntoTable("client_takeaction",insertData,
				// connectionSpace);
				int maxId = coi.insertDataReturnId("client_takeaction", insertData, connectionSpace);
				// coi.insertIntoTable("user_action_history",
				// insertUserHistoryData, connectionSpace);
				new UserHistoryAction().userHistoryAdd(empId, "Take action on client", "Sales", "Opportunity", "Contact, Offering Name, Status, Due Date, Comment, User Name,Sales Stages, Compelling Reason", "contactId, offeringId, statusId, maxDateTime, comment, isFinished, userName, sales_stages, compellingreason, date, time", maxId, connectionSpace);

				insertData.clear();

				if (maxId > 0)
				{
					// Fill auto KPI for 'Total Calls'
					LeadActionControlDao lacd = new LeadActionControlDao();
					lacd.insertInToKPIAutofillTable(empId, "2", userName, connectionSpace, id, "Prospective Client");
					if (clientStatus.contains("Intro Mail"))
					{
						// Fill auto KPI for 'Intro Mail'
						lacd.insertInToKPIAutofillTable(empId, "4", userName, connectionSpace, id, "Prospective Client");
					} else if (clientStatus.contains("Meeting"))
					{
						// Fill auto KPI for 'Meeting Generation'
						lacd.insertInToKPIAutofillTable(empId, "5", userName, connectionSpace, id, "Prospective Client");
					}

					if (!clientStatus.contains("Snooze") && !clientStatus.contains("Reschedule"))
					{
						// Fill auto KPI for 'Productive Calls'
						lacd.insertInToKPIAutofillTable(empId, "3", userName, connectionSpace, id, "Prospective Client");
					}

					count++;
					StringBuilder querySelect = new StringBuilder();
					querySelect.append("select id from client_takeaction where contacId = '");
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
						// System.out.println("actionId:" + actionId);
						if (actionId != null && empIdArray != null && empIdArray.length > 0)
						{
							for (String emp : empIdArray)
							{
								InsertDataTable ob1 = null;
								ob1 = new InsertDataTable();
								ob1.setColName("client_takeaction_id");
								ob1.setDataName(actionId);
								insertDataME.add(ob1);

								ob1 = new InsertDataTable();
								ob1.setColName("empId");
								ob1.setDataName(emp);
								insertDataME.add(ob1);

								boolean statusFlagME = coi.insertIntoTable("client_takeaction_mapped_emp", insertDataME, connectionSpace);
								insertDataME.clear();
							}
						}
					}
				}
			}

			if (count > 0)
				addActionMessage("Action Taken Successfully.");
			else
				addActionMessage("Error: Action Incomplete !");
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: takeActionOnClient of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop 25-4-2013 client full history
	 */
	public String viewClientFullHistory()
	{
		clientName = id;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			/*
			 * show client basic info on full view
			 */
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_basic_configuration");
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from client_basic_data as cbd ");
			List<String> names = new ArrayList<String>();

			// this variable is used to view client's referred name
			String refByHeadingName = "";

			int ii = 0;
			for (GridDataPropertyView gdp : returnResult)
			{
				if (ii < returnResult.size() - 1)
				{
					if (gdp.getColomnName().equalsIgnoreCase("industry"))
					{
						query.append(" i.industry 'industry', ");
						queryEnd.append(" left join industrydatalevel1 as i on i.id = cbd.industry ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
					{
						query.append(" si.subIndustry 'subIndustry', ");
						queryEnd.append(" left join subindustrydatalevel2 as si on si.id = cbd.subIndustry ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("sourceMaster"))
					{
						query.append(" sm.sourceName 'sourceMaster', ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("weightage_targetsegment"))
					{
						query.append(" wm.weightageName 'weightage_targetsegment', ");
						queryEnd.append(" left join weightagemaster as wm on wm.id = cbd.weightage_targetsegment ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("sales_stages"))
					{
						query.append(" ssm.salesstageName 'sales_stages', ");
						queryEnd.append(" left join salesstagemaster as ssm on ssm.id = cbd.sales_stages ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("forecast_category"))
					{
						query.append(" fccm.forcastName 'forecast_category', ");
						queryEnd.append(" left join forcastcategarymaster as fccm on fccm.id = cbd.forecast_category ");
						names.add(gdp.getHeadingName());
					}

					else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusName 'status', ");
						queryEnd.append(" left join client_status as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location', ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager', ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
					{
						query.append(" cs1.statusName 'targetAchieved', ");
						queryEnd.append(" left join client_status as cs1 on cs1.id = cbd.targetAchieved ");
						names.add(gdp.getHeadingName());
					} else
					{
						// //System.out.println("gdp.getColomnName() " +
						// gdp.getColomnName());
						query.append("cbd." + gdp.getColomnName() + ",");
						names.add(gdp.getHeadingName());
					}

					if (gdp.getColomnName().equalsIgnoreCase("referedBy"))
					{
						refByHeadingName = gdp.getHeadingName();
					}
				} else
				{
					if (gdp.getColomnName().equalsIgnoreCase("industry"))
					{
						query.append(" i.industry 'industry' ");
						queryEnd.append(" left join industrydatalevel1 as i on i.id = cbd.industry ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("weightage_targetsegment"))
					{
						query.append(" wm.weightageName 'weightage_targetsegment' ");
						queryEnd.append(" left join weightagemaster as wm on wm.id = cbd.weightage_targetsegment ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("subIndustry"))
					{
						query.append(" si.subIndustry 'subIndustry' ");
						queryEnd.append(" left join subindustrydatalevel2 as si on si.id = cbd.subIndustry ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("sourceMaster"))
					{
						query.append(" sm.sourceName 'sourceMaster' ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusName 'status' ");
						queryEnd.append(" left join client_status as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location' ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager' ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
					{
						query.append(" cs1.statusName 'targetAchieved' ");
						queryEnd.append(" left join client_status as cs1 on cs1.id = cbd.targetAchieved ");
						names.add(gdp.getHeadingName());
					} else
					{
						if (!gdp.getColomnName().equalsIgnoreCase("closure_date"))
						{
							query.append("cbd." + gdp.getColomnName());
							names.add(gdp.getHeadingName());
						}

					}

					if (gdp.getColomnName().equalsIgnoreCase("referedBy"))
					{
						refByHeadingName = gdp.getHeadingName();
					}
				}
				ii++;
			}

			query.append(" " + queryEnd.toString() + " where cbd.id = " + id);

			// System.out.println("query>>" + query.toString());
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			LinkedHashMap<String, String> one = null;
			numberOfStar = new ArrayList<String>();
			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					one = new LinkedHashMap<String, String>();
					String type = "";
					boolean flag = false;

					if (obdata != null)
					{
						CommonHelper ch = new CommonHelper();
						for (int j = 0; j < returnResult.size(); j++)
						{
							if (obdata[j] != null)
							{
								if (names.get(j).toString().equalsIgnoreCase(refByHeadingName))
								{
									type = obdata[j].toString();
									flag = true;
								} else if (flag)
								{
									String queryName = "";
									String _id = obdata[j].toString().trim();
									if (type.trim().equalsIgnoreCase("Client"))
									{
										queryName = " select id,clientName from client_basic_data where id = " + _id;
									} else if (type.trim().equalsIgnoreCase("Associate"))
									{
										queryName = " select id,associateName from prospectiveAssociate where id = " + _id;
									}

									if (!queryName.equalsIgnoreCase(""))
									{
										List nameList = cbt.executeAllSelectQuery(queryName, connectionSpace);
										// //System.out.println("queryName
										// >>>>>>>>>>> " +
										// queryName.toString());
										if (nameList != null)
										{
											for (Object obData : nameList)
											{
												Object[] ob = (Object[]) obData;
												if (ob != null && ob.length > 0)
												{
													obdata[j] = ob[1];
													break;
												}
											}
										}
									}
									flag = false;
								}

								if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("date"))
								{
									obdata[j] = DateUtil.convertDateToIndianFormat(obdata[j].toString());
								} else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("userName"))
								{
									obdata[j] = ch.fetchEmpDetailsByContactId(obdata[j].toString())[0];
								} else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("time"))
								{
									obdata[j] = CommonHelper.getFieldValue(obdata[j]).substring(0, CommonHelper.getFieldValue(obdata[j]).lastIndexOf(":"));
								}

								else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("clientName"))
								{
									clientName = CommonHelper.getFieldValue(obdata[j]);
									// clientNameWithStar = clientName;
								} else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("starRating"))
								{
									clientNameWithStar = obdata[j].toString();
									if (obdata[j].toString().equalsIgnoreCase("NA"))
									{
										obdata[j] = "0";
									}
									for (int n = 0; n < Integer.parseInt(obdata[j].toString()); n++)
									{

										numberOfStar.add(clientNameWithStar);
									}

								}

								else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("location"))
								{
									location = ((obdata[j] == null) ? "NA" : obdata[j].toString());
								} else if (returnResult.get(j).getColomnName().toString().trim().equalsIgnoreCase("closure_date"))
								{
									one.put(names.get(j), DateUtil.convertDateToIndianFormat(obdata[j].toString()));
								} else
								{
									one.put(names.get(j), obdata[j].toString());
								}

							}
						}
					}
				}
				basicParams = one;
			}

			/*
			 * show client contact info on full view
			 */
			String qpersonname = null;
			String qpersonHeading = null;
			String qcommname = null;
			String qcomnameHeading = null;
			String qinfdegree = null;
			String qinfHeading = null;
			String qdept = null;
			String qdeptheading = null;
			String qdesig = null;
			String qdesigHeading = null;
			String qmob = null;
			String qmobHeading = null;
			String qmail = null;
			String qmailHeading = null;
			String qbday = null;
			String qbdayHeading = null;
			String qanniv = null;
			String qannivHeading = null;
			String qamail = null;
			String qamailHeading = null;
			String qalmob = null;
			String qbalmob = null;
			String qinput = null;
			String qinputHeading = null;
			String qactivInactive = null;
			String qactivInactiveHeading = null;
			String qactivDateInactive = null;
			String qactivInactiveDateHeading = null;

			List<GridDataPropertyView> returnResult1 = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
			// System.out.println(" returnResult1 >>>>>>>>"+returnResult1.size());
			StringBuilder query1 = new StringBuilder("");
			query1.append("select ");
			namesContact = new LinkedList<String>();
			List<GridDataPropertyView> test = new ArrayList<GridDataPropertyView>();
			for (Iterator<GridDataPropertyView> itr = returnResult1.iterator(); itr.hasNext();)
			{
				GridDataPropertyView gdp = itr.next();
				if (gdp.getColomnName().equalsIgnoreCase("personName") || gdp.getColomnName().equalsIgnoreCase("communicationName") || gdp.getColomnName().equalsIgnoreCase("degreeOfInfluence") || gdp.getColomnName().equalsIgnoreCase("department") || gdp.getColomnName().equalsIgnoreCase("designation") || gdp.getColomnName().equalsIgnoreCase("contactNo") || gdp.getColomnName().equalsIgnoreCase("emailOfficial") || gdp.getColomnName().equalsIgnoreCase("birthday") || gdp.getColomnName().equalsIgnoreCase("anniversary") || gdp.getColomnName().equalsIgnoreCase("alternateEmail")
						|| gdp.getColomnName().equalsIgnoreCase("currentStatus"))
				{

					// System.out.println("nnnn   "+gdp.getColomnName());
					if (gdp.getColomnName().equalsIgnoreCase("personName"))
					{
						qpersonname = gdp.getColomnName() + ",";
						qpersonHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("communicationName"))
					{
						qcommname = gdp.getColomnName() + ",";
						qcomnameHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("degreeOfInfluence"))
					{
						qinfdegree = gdp.getColomnName() + ",";
						qinfHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("department"))
					{
						qdept = "dept.deptName 'dName',";
						qdeptheading = gdp.getHeadingName();
						// query1.append("dept.deptName 'dName',");
					}
					if (gdp.getColomnName().equalsIgnoreCase("designation"))
					{
						qdesig = gdp.getColomnName() + ",";
						qdesigHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("contactNo"))
					{
						qmob = gdp.getColomnName() + ",";
						qmobHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("emailOfficial"))
					{
						qmail = gdp.getColomnName() + ",";
						qmailHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("birthday"))
					{
						qbday = gdp.getColomnName() + ",";
						qbdayHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("anniversary"))
					{
						qanniv = gdp.getColomnName() + ",";
						qannivHeading = gdp.getHeadingName();
					}
					if (gdp.getColomnName().equalsIgnoreCase("alternateEmail"))
					{
						qamail = gdp.getColomnName() + ",";
						qamailHeading = gdp.getHeadingName();
					}
					/*
					 * if
					 * (gdp.getColomnName().equalsIgnoreCase("alternateContNo"))
					 * { qalmob = gdp.getColomnName() + ","; qbalmob =
					 * gdp.getHeadingName(); }
					 */
					/*
					 * if (gdp.getColomnName().equalsIgnoreCase("input")) {
					 * qinput = gdp.getColomnName() + ","; qinputHeading =
					 * gdp.getHeadingName(); }
					 */
					if (gdp.getColomnName().equalsIgnoreCase("currentStatus"))
					{
						qactivInactive = gdp.getColomnName() + ",";
						qactivInactiveHeading = gdp.getHeadingName();
					}
					/*
					 * if
					 * (gdp.getColomnName().equalsIgnoreCase("currentStatusDate"
					 * )) { qactivDateInactive = gdp.getColomnName() + ",";
					 * qactivInactiveDateHeading = gdp.getHeadingName(); }
					 */
					else
					{
						// query1.append(gdp.getColomnName() + ",");
					}
					// namesContact.add(gdp.getHeadingName());

				} else
				{

					test.add(gdp);
					// itr.remove();
				}
			}
			query1.append(qpersonname);
			query1.append(qcommname);
			query1.append(qinfdegree);
			query1.append(qdept);
			query1.append(qdesig);
			query1.append(qmob);
			query1.append(qmail);
			query1.append(qbday);
			query1.append(qanniv);
			query1.append(qamail);
			/*
			 * if (qalmob != null) { query1.append(qalmob); }
			 */

			// query1.append(qinput);
			query1.append(qactivInactive);
			// query1.append(qactivDateInactive);
			namesContact.add(qpersonHeading);
			namesContact.add(qcomnameHeading);
			namesContact.add(qinfHeading);
			namesContact.add(qdeptheading);
			namesContact.add(qdesigHeading);
			namesContact.add(qmobHeading);
			namesContact.add(qmailHeading);
			namesContact.add(qbdayHeading);
			namesContact.add(qannivHeading);
			namesContact.add(qamailHeading);
			// namesContact.add(qbalmob);
			// namesContact.add(qinputHeading);
			namesContact.add(qactivInactiveHeading);
			// namesContact.add(qactivInactiveDateHeading);

			String query1New = query1.toString().substring(0, query1.toString().lastIndexOf(","));
			query1New += " from client_contact_data as ccd left join department as dept on dept.id = ccd.department where clientName=" + id;
			// System.out.println("client contact>>" + query1New.toString());
			List data1 = cbt.executeAllSelectQuery(query1New.toString(), connectionSpace);

			List<String> cList = null;
			Map<String, String> one1 = null;

			returnResult1.removeAll(test);
			// System.out.println("Old Size >>>>>>>>"+returnResult1.size());

			returnResult1.remove("userName");
			returnResult1.remove("date");
			returnResult1.remove("time");
			returnResult1.remove("location");
			returnResult1.remove("source");
			returnResult1.remove("star");
			returnResult1.remove("existing_free");
			returnResult1.remove("dateOfBirth");
			returnResult1.remove("dateOfAnnvrsry");
			returnResult1.remove("potential");
			returnResult1.remove("dateOfJoining");
			returnResult1.remove("opportunityName");
			returnResult1.remove("officeEmailId");
			returnResult1.remove("contactName");
			returnResult1.remove("clientName");
			returnResult1.remove("alternateMob");
			returnResult1.remove("input");
			returnResult1.remove("currentStatusDate");

			// System.out.println("New "
			// +" Size >>>>>>>>"+returnResult1.size());
			if (data1 != null)
			{
				int i = 0;
				for (Iterator it = data1.iterator(); it.hasNext();)
				{
					CommonHelper ch = new CommonHelper();
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < returnResult1.size(); j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								if (returnResult1.get(j).getColomnName().equalsIgnoreCase("currentStatus"))
								{

									if (obdata[j].toString().equalsIgnoreCase("1"))
									{
										obdata[j] = "Active";
									} else
									{
										obdata[j] = "Inactive";
									}
								}
								if (returnResult1.get(j).getColomnName().equalsIgnoreCase("currentStatusDate"))
								{
									obdata[j] = DateUtil.convertDateToIndianFormat(obdata[j].toString());
								}
								// //System.out.println("obdata[" + j + "]:" +
								// obdata[j]);
								// //System.out.println(" returnResult1.get(" +
								// j + "):" +
								// returnResult1.get(j).getColomnName());
								if (returnResult1.get(j).getColomnName().equalsIgnoreCase("date") || returnResult1.get(j).getColomnName().equalsIgnoreCase("birthday") || returnResult1.get(j).getColomnName().equalsIgnoreCase("anniversary"))
									c1.setName(DateUtil.convertDateToIndianFormat(obdata[j].toString()));
								else
								{
									c1.setName(obdata[j].toString());
								}
							} else
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
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				namesOffering = new ArrayList<String>();
				namesOffering.add("ID");
				namesOffering.add("Is Converted");

				level = Integer.parseInt(oLevels[0]);
				for (int i = 1; i < oLevels.length; i++)
				{
					if (oLevels[i] != null && !oLevels[i].equalsIgnoreCase(""))
						namesOffering.add(oLevels[i]);
				}
				if (namesOffering.contains("Vertical") && namesOffering.contains("Offering"))
				{
					namesOffering.remove("Vertical");
					namesOffering.remove("Offering");
				}
				namesOffering.add("Brief");
				namesOffering.add("Expectency");
				namesOffering.add("Value");
				namesOffering.add("Closure Date");
			}

			String tableName = "", colName = "";
			StringBuffer queryOffering = new StringBuffer();

			if (level == 1)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname from offeringlevel1 as t1, client_offering_mapping as com where t1.id = com.offeringId and com.clientName = ");
			}
			if (level == 2)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname from offeringlevel1 as t1, offeringlevel2 as t2, client_offering_mapping as com where t2.verticalname=t1.id and t2.id = com.offeringId and com.clientName = ");
			}
			if (level == 3)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname,com.opportunity_name,com.expectency,com.opportunity_value,com.closure_date from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, client_offering_mapping as com where t3.offeringname=t2.id and t2.verticalname=t1.id and t3.id = com.offeringId and com.clientName = ");
			}
			if (level == 4)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, client_offering_mapping as com where t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t4.id = com.offeringId and com.clientName = ");
			}
			if (level == 5)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname, t5.subvariantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, offeringlevel5 as t5, client_offering_mapping as com where t5.variantname=t4.id and t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t5.id = com.offeringId and com.clientName = ");
			}

			queryOffering.append(id);

			if (isExistingClient.equalsIgnoreCase("1"))
			{
				queryOffering.append(" and com.isConverted = 1 ");
			} else if (isExistingClient.equalsIgnoreCase("0"))
			{
				queryOffering.append(" and com.isConverted = 0 ");
			} else if (isExistingClient.equalsIgnoreCase("2"))
			{
				queryOffering.append(" and com.isConverted = 2 ");
			}
			// System.out.println(">>>>>>>>>>>>>>>>>>"+queryOffering.toString());
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
								if (obdata[j].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
								{
									c1.setName(DateUtil.convertDateToIndianFormat(obdata[j].toString()));
								} else
								{
									c1.setName(obdata[j].toString());
									if (obdata[2] != null)
									{
										this.setVerticalname(obdata[2].toString());
									}
									if (obdata[3] != null)
									{
										this.setOfferingname(obdata[3].toString());
									}
									if (obdata[4] != null)
									{
										this.setSubofferingname(obdata[4].toString());
									}
								}
								// list.add(obdata[j].toString());
							} else
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
			 * Client Action taken
			 */

			/*
			 * int level = 0; CommonOperInterface cbt=new
			 * CommonConFactory().createInterface(); offeringLevel =
			 * "5#Vertical#Offering#Sub-Offering#Variant#Sub-variant#"
			 * ;//session.get("offeringLevel").toString(); String[] oLevels =
			 * null;
			 */
			namesTakeaction = new LinkedList<String>();
			namesTakeaction.add("MOM");
			namesTakeaction.add("Offering");
			namesTakeaction.add("Contact Person");
			namesTakeaction.add("Contact No");
			namesTakeaction.add("Sales Activity Status");
			namesTakeaction.add("Comment");
			namesTakeaction.add("Due Date & Time");

			namesTakeaction.add("Is Finished");
			namesTakeaction.add("Activity Date");
			namesTakeaction.add("Activity Time");

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
			queryTA.append(" select cta.id, co.");
			queryTA.append(colName);
			queryTA.append(", ccd.personName, ccd.contactNo");

			queryTA.append(" , cs.statusName, cta.comment, cta.maxDateTime, ");
			queryTA.append(" cta.isFinished, cta.finishedDate, cta.finishedTime");
			queryTA.append(" from client_contact_data as ccd, client_takeaction as cta, client_status as cs, ");
			queryTA.append(tableName);
			queryTA.append(" as co ");
			queryTA.append(" where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id and cta.isFinished=0 ");
			queryTA.append(" and ccd.clientName = ");
			queryTA.append(id);
			// System.out.println("queryTA.toString() >>>>>>>>>>> " +
			// queryTA.toString());
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
						List<Child> child = new LinkedList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								if (j == 6)
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
								} else if (j == 8)
								{
									c1.setName(DateUtil.convertDateToIndianFormat(obdata[j].toString()));
								} else
								{
									c1.setName(obdata[j].toString());
								}
								/*
								 * c1.setName(obdata[j].toString()); //
								 * list.add(obdata[j].toString());
								 */} else
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

			/*
			 * Accordion to show
			 */
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_client_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("client_basic_configuration"))
					{
						clientForBasicDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_offering_configuration"))
					{
						clientForOfferingDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_contact_configuration"))
					{
						clientForContacDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_attachment_configuration"))
					{
						clientForAttachmentDetails = 1;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: viewClientFullHistory of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * before Edit Offering.
	 * */
	public String beforeEditOffering()
	{
		if (!ValidateSession.checkSession())
		{
			return LOGIN;
		}
		return SUCCESS;
	}

	public String editOfferingToFullview()
	{
		String returnValue = ERROR;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		try
		{
			query1.append("update client_offering_mapping as com ");
			query1.append("left join offeringlevel3 as ofl on com.offeringId = ofl.id ");
			query1.append("left join offeringlevel2 as lft  on ofl.offeringname = lft.id ");
			query1.append("left join offeringlevel1 as olone on lft.verticalname = olone.id ");
			query1.append("set olone.verticalname = '");
			query1.append(verticalname);
			query1.append("', ");

			query1.append("lft.offeringname = '");
			query1.append(offeringname);
			query1.append("', ");

			query1.append("ofl.subofferingname = '");
			query1.append(subofferingname);
			query1.append("' ");
			query1.append("where com.id = '");
			query1.append(offeringId);
			query1.append("'");

			int b = cbt.executeAllUpdateQuery(query1.toString(), connectionSpace);
			if (b > 0)
			{
				addActionMessage("Offering details updated successfully.");
				returnValue = SUCCESS;
			} else
			{
				addActionMessage("There is some problem in updation.");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return returnValue;
	}

	/**
	 * Anoop 26-9-2013
	 * 
	 * @return Fetch list of upcoming 10 activities
	 */
	public String clientActivities()
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
			String[] oLevels = null;
			String tableName = "", colName = "";
			CommonOperInterface coi = new CommonConFactory().createInterface();

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
				queryTA.append("select cta.id,ccd.contactNo,ccd.personName, co." + colName + " , cs.statusName, " + "cta.maxDateTime, cta.comment,  cta.isFinished, cta.finishedDate, cta.finishedTime " + "from client_contact_data as ccd, client_takeaction as cta, client_status as cs, " + tableName + " as co, " + "client_basic_data as cbd  where ccd.id = cta.contacId and cta.statusId = cs.id " + "and cta.offeringId = co.id  and cbd.id = ccd.clientName and cbd.userName = '" + cId + "' " + "and isFinished = '0' and maxDateTime like '" + DateUtil.getCurrentDateUSFormat()
						+ "%' " + "order by maxDateTime ");
			} else if (activityType.equalsIgnoreCase("nextSevenDaysActivities"))
			{
				queryTA.append("select cta.id,ccd.contactNo,ccd.personName, co.subofferingname , cs.statusName, cta.maxDateTime, " + "cta.comment,  cta.isFinished, cta.finishedDate, cta.finishedTime from client_contact_data as ccd, " + "client_takeaction as cta, client_status as cs, offeringlevel3 as co, client_basic_data as cbd  " + "where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id  " + "and cbd.id = ccd.clientName and cbd.userName = '" + cId + "' " + "and isFinished = '0' and maxDateTime >= '" + todayDate + "' " + "and maxDateTime <= '"
						+ DateUtil.getNextDateAfter(7) + " 23:59' " + "order by maxDateTime ");
			} else if (activityType.equalsIgnoreCase("tomorrowActivity"))
			{
				queryTA.append("select cta.id,ccd.contactNo,ccd.personName, co.subofferingname , cs.statusName, cta.maxDateTime, " + "cta.comment,  cta.isFinished, cta.finishedDate, cta.finishedTime from client_contact_data as ccd, " + "client_takeaction as cta, client_status as cs, offeringlevel3 as co, client_basic_data as cbd  " + "where ccd.id = cta.contacId and cta.statusId = cs.id and cta.offeringId = co.id  " + "and cbd.id = ccd.clientName and cbd.userName = '" + cId + "' " + "and isFinished = '0' and maxDateTime like '" + DateUtil.getNextDateAfter(1) + "%' "
						+ "order by maxDateTime ");
			}

			// //System.out.println("queryTA.toString() >>>>>>>>>>> " +
			// queryTA.toString());
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
								} else
								{
									c1.setName(obdata[j].toString());
								}
								// list.add(obdata[j].toString());
							} else
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
		} else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}

	public String viewCLientActionDash()
	{
		clientName = id;
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			// isExistingClient = session.get("isExistingClient").toString();
			// lostFlag = session.get("lostFlag").toString();
			// convertToClient = session.get("convertToClient").toString();

			/*
			 * show client basic info on full view
			 */
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_basic_configuration");
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from client_basic_data as cbd ");
			List<String> names = new ArrayList<String>();

			// this variable is used to view client's referred name
			String refByHeadingName = "";

			int ii = 0;
			for (GridDataPropertyView gdp : returnResult)
			{
				if (ii < returnResult.size() - 1)
				{
					if (gdp.getColomnName().equalsIgnoreCase("sourceMaster"))
					{
						query.append(" sm.sourceName 'sourceMaster', ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusName 'status', ");
						queryEnd.append(" left join client_status as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location', ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager', ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					} else
					{
						query.append("cbd." + gdp.getColomnName() + ",");
						names.add(gdp.getHeadingName());
					}

					if (gdp.getColomnName().equalsIgnoreCase("referedBy"))
					{
						refByHeadingName = gdp.getHeadingName();
					}
				} else
				{
					if (gdp.getColomnName().equalsIgnoreCase("sourceMaster"))
					{
						query.append(" sm.sourceName 'sourceMaster' ");
						queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						query.append(" cs.statusName 'status' ");
						queryEnd.append(" left join client_status as cs on cs.id = cbd.status ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						query.append(" loc.name 'location' ");
						queryEnd.append(" left join location as loc on loc.id = cbd.location ");
						names.add(gdp.getHeadingName());
					} else if (gdp.getColomnName().equalsIgnoreCase("acManager"))
					{
						query.append(" cbd1.empName 'acManager' ");
						queryEnd.append(" left join employee_basic as cbd1 on cbd1.id = cbd.acManager ");
						names.add(gdp.getHeadingName());
					} else
					{
						query.append("cbd." + gdp.getColomnName());
						names.add(gdp.getHeadingName());
					}

					if (gdp.getColomnName().equalsIgnoreCase("referedBy"))
					{
						refByHeadingName = gdp.getHeadingName();
					}
				}
				ii++;
			}

			query.append(" " + queryEnd.toString() + " where cbd.id = " + id);

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			LinkedHashMap<String, String> one = null;

			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					one = new LinkedHashMap<String, String>();
					String type = "";
					boolean flag = false;

					if (obdata != null)
					{
						for (int j = 0; j < returnResult.size(); j++)
						{
							if (obdata[j] != null)
							{
								if (names.get(j).toString().equalsIgnoreCase(refByHeadingName))
								{
									type = obdata[j].toString();
									flag = true;
								} else if (flag)
								{
									String queryName = "";
									String _id = obdata[j].toString().trim();
									if (type.trim().equalsIgnoreCase("Prospective Client"))
									{
										queryName = " select id,clientName from client_basic_data where id = " + _id;
									} else if (type.trim().equalsIgnoreCase("Prospective Associate") || type.trim().equalsIgnoreCase("Existing Associate"))
									{
										queryName = " select id,associateName from prospectiveAssociate where id = " + _id;
									}

									if (!queryName.equalsIgnoreCase(""))
									{
										List nameList = cbt.executeAllSelectQuery(queryName, connectionSpace);
										if (nameList != null)
										{
											for (Object obData : nameList)
											{
												Object[] ob = (Object[]) obData;
												if (ob != null && ob.length > 0)
												{
													obdata[j] = ob[1];
													break;
												}
											}
										}
									}
									flag = false;
								}

								one.put(names.get(j), obdata[j].toString());
							}
						}
					}
				}
				basicParams = one;
			}

			/*
			 * show client contact info on full view
			 */
			List<GridDataPropertyView> returnResult1 = Configuration.getConfigurationData("mapped_client_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
			StringBuilder query1 = new StringBuilder("");
			query1.append("select ");
			namesContact = new ArrayList<String>();
			for (GridDataPropertyView gdp : returnResult1)
			{
				query1.append(gdp.getColomnName() + ",");
				namesContact.add(gdp.getHeadingName());
			}

			String query1New = query1.toString().substring(0, query1.toString().lastIndexOf(","));
			query1New += " from client_contact_data where clientName=" + id;
			// //System.out.println("query1New " + query1New.toString());

			List data1 = cbt.executeAllSelectQuery(query1New.toString(), connectionSpace);

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
								c1.setName(obdata[j].toString());
								// list.add(obdata[j].toString());
							} else
							{
								c1.setName("NA");
								// list.add("NA");
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
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				namesOffering = new ArrayList<String>();
				namesOffering.add("ID");
				namesOffering.add("Is Converted");

				level = Integer.parseInt(oLevels[0]);
				for (int i = 1; i < oLevels.length; i++)
				{
					if (oLevels[i] != null && !oLevels[i].equalsIgnoreCase(""))
						namesOffering.add(oLevels[i]);
				}
			}

			String tableName = "", colName = "";
			StringBuffer queryOffering = new StringBuffer();

			if (level == 1)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname from offeringlevel1 as t1, client_offering_mapping as com where t1.id = com.offeringId and com.clientName = ");
			}
			if (level == 2)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname from offeringLevel1 as t1, offeringlevel2 as t2, client_offering_mapping as com where t2.verticalname=t1.id and t2.id = com.offeringId and com.clientName = ");
			}
			if (level == 3)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, client_offering_mapping as com where t3.offeringname=t2.id and t2.verticalname=t1.id and t3.id = com.offeringId and com.clientName = ");
			}
			if (level == 4)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, client_offering_mapping as com where t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t4.id = com.offeringId and com.clientName = ");
			}
			if (level == 5)
			{
				queryOffering.append("select com.id,com.isConverted,t1.verticalname, t2.offeringname, t3.subofferingname, t4.variantname, t5.subvariantsize from offeringlevel1 as t1, offeringlevel2 as t2, offeringlevel3 as t3, offeringlevel4 as t4, offeringlevel5 as t5, client_offering_mapping as com where t5.variantname=t4.id and t4.subofferingname=t3.id and t3.offeringname=t2.id and t2.verticalname=t1.id and t5.id = com.offeringId and com.clientName = ");
			}

			queryOffering.append(id);

			if (isExistingClient.equalsIgnoreCase("1") && lostFlag.equalsIgnoreCase("0"))
			{
				queryOffering.append(" and com.isConverted = 1 ");
			}

			else if (isExistingClient.equalsIgnoreCase("0") && lostFlag.equalsIgnoreCase("0"))
			{
				queryOffering.append(" and com.isConverted = 0 ");
			} else if (isExistingClient.equalsIgnoreCase("0") && lostFlag.equalsIgnoreCase("1"))
			{
				queryOffering.append(" and com.isConverted = 2 ");
			}

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
							} else
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
			 * Client Action taken
			 */

			/*
			 * int level = 0; CommonOperInterface cbt=new
			 * CommonConFactory().createInterface(); offeringLevel =
			 * "5#Vertical#Offering#Sub-Offering#Variant#Sub-variant#"
			 * ;//session.get("offeringLevel").toString(); String[] oLevels =
			 * null;
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
			queryTA.append(" select cta.id,ccd.contactNo,ccd.personName, co.");
			queryTA.append(colName);
			queryTA.append(" , cs.statusName, cta.maxDateTime, cta.comment, ");
			queryTA.append(" cta.isFinished, cta.finishedDate, cta.finishedTime");
			queryTA.append(" from client_contact_data as ccd, client_takeaction as cta, client_status as cs, ");
			queryTA.append(tableName);
			queryTA.append(" as co ");
			queryTA.append(" where ccd.id = cta.contacId and cta.isFinished='0' and cta.statusId = cs.id and cta.offeringId = co.id ");
			queryTA.append(" and ccd.clientName in (select cbd.id from client_contact_data as cbd where userName ='" + cId + "') ");
			// queryTA.append(id);
			// //System.out.println("queryTA.toString() >>>>>>>>>>> " +
			// queryTA.toString());
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
								// //System.out.println("obdata[j].toString() 6
								// Sept " +
								// obdata[j].toString());
								// list.add(obdata[j].toString());
							} else
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

			/*
			 * Accordion to show
			 */
			StringBuilder empuery = new StringBuilder("");
			empuery.append("select table_name from mapped_client_configuration");
			List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);
			for (Iterator it = empData.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();

				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("client_basic_configuration"))
					{
						clientForBasicDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_offering_configuration"))
					{
						clientForOfferingDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_contact_configuration"))
					{
						clientForContacDetails = 1;
					} else if (obdata.toString().equalsIgnoreCase("client_attachment_configuration"))
					{
						clientForAttachmentDetails = 1;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: viewClientFullHistory of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeFinishClientActivity()
	{
		if (!ValidateSession.checkSession())
			return LOGIN;
		return SUCCESS;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String fetchOfferingData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				offeringNameMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query = "SELECT com.isConverted,emp.empName,com.convertDate,lo.lostReason,com.RCA,com.CAPA FROM client_offering_mapping as com left join lostoportunity as lo on lo.id=com.lostId left join employee_basic as emp on emp.id=(Select cc.emp_id from compliance_contacts as cc where cc.id=com.userName)  WHERE com.id=" + id;
				List data = cbt.executeAllSelectQuery(query, connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (!object[0].toString().equalsIgnoreCase("0") && !object[0].toString().equalsIgnoreCase("1"))
						{
							offeringNameMap.put("Lost By", getValueWithNullCheck(object[1]));
							offeringNameMap.put("Lost Date", DateUtil.convertDateToIndianFormat(getValueWithNullCheck(object[2])));
							offeringNameMap.put("Lost Reason", getValueWithNullCheck(object[3]));
							offeringNameMap.put("RCA", getValueWithNullCheck(object[4]));
							offeringNameMap.put("CAPA", getValueWithNullCheck(object[5]));
						}
					}
				}
				return SUCCESS;
			} catch (Exception exp)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method beforeViewTaskType of class " + getClass(), exp);
				exp.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String finishClientActivity()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("isFinished", 1);
			wherClause.put("finishedDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("finishedTime", DateUtil.getCurrentTime());
			wherClause.put("comment", comments);

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("client_takeaction", wherClause, condtnBlock, connectionSpace);
			if (b)
			{
				addActionMessage("Client activity finished successfully.");
				return SUCCESS;
			} else
			{
				return ERROR;
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: finishClientActivity of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String convertClientForOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			String renameFilePath = null;
			File theFile = null;
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// //System.out.println("CLIENMT ID "+clientId);
			if (getPo_attachFileName() != null)
			{
				renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Activity") + "//" + DateUtil.mergeDateTime() + getPo_attachFileName();
				String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Activity") + "//" + getPo_attachFileName();
				String str = renameFilePath.replace("//", "/");
				if (storeFilePath != null)
				{
					theFile = new File(storeFilePath);
					File newFileName = new File(str);
					if (theFile != null)
					{
						try
						{
							FileUtils.copyFile(getPo_attach(), theFile);
							if (theFile.exists())
								theFile.renameTo(newFileName);
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			wherClause.put("isConverted", 1);
			wherClause.put("offering_price", getAmmount());
			wherClause.put("poNumber", getPoNumber());
			wherClause.put("poDate", DateUtil.convertDateToUSFormat(getPo_date()));
			// System.out.println("COMMENTS :::: " + comments);
			if (theFile != null)
			{
				wherClause.put("po_attachment", renameFilePath);
			}
			if (comments != null && !comments.equalsIgnoreCase(""))
			{
				wherClause.put("comment", comments);
			}
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, connectionSpace);

			// For Dynamically creating Table client_history
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			List<String> columnName = new ArrayList<String>();
			columnName.add("clientName");
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

			boolean status = cbt.createTable22("client_history", tableColumn, connectionSpace);

			// for entry in History table

			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("clientName");
			ob.setDataName(clientId);
			insertHistory.add(ob);

			ob = new InsertDataTable();
			ob.setColName("offering");
			StringBuilder query1 = new StringBuilder("");
			query1.append("Select offeringId from client_offering_mapping where clientName='" + clientId + "'");
			List offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ob.setDataName(offeringId.get(0));
			insertHistory.add(ob);

			ob = new InsertDataTable();
			ob.setColName("offeringLevel");
			StringBuilder query2 = new StringBuilder("");
			query2.append("Select offeringLevelId from client_offering_mapping where clientName='" + clientId + "'");
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
				AchievementHelper AH = new AchievementHelper();
				AH.addAchievementForOffering(empId, offeringId.get(0).toString(), getAmmount(), DateUtil.getCurrentDateUSFormat(), offeringLevelId1.get(0).toString(), userName, getId());

				addActionMessage("Data Added Successfully.");
				return SUCCESS;
			} else
			{
				return ERROR;
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: convertClientForOffering of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String fetchReferredName()
	{
		try
		{
			String cIdUserType = new CommonHelper().getEmpTypeByUserName(CommonHelper.moduleName, userName, connectionSpace);

			if (!ValidateSession.checkSession())
				return LOGIN;
			// System.out.println("client");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");

			if (id.equalsIgnoreCase("Client"))
			{
				// it will fetch all data from client_basic_data irrespective of
				// prospective or existing client
				if (cIdUserType.equalsIgnoreCase("M"))
				{
					query1.append("select id,clientName from client_basic_data");

				} else
				{
					query1.append("select id,clientName from client_basic_data where userName='");
					query1.append(cId);
					query1.append("'");
				}

				query1.append(" order by clientName ");
			} else if (id.equalsIgnoreCase("Existing_Client"))
			{

				query1.append("select DISTINCT cbd.id,cbd.clientName from client_basic_data as cbd left join client_offering_mapping as com on com.clientName=cbd.id where isConverted='1'");
				// query1.append(cId);
				// query1.append("'");
				query1.append("order by clientName");
			} else if (id.equalsIgnoreCase("Associate"))
			{
				// it will fetch all data from associate_basic_data irrespective
				// of
				// prospective or existing client
				query1.append("select id,associateName from associate_basic_data where userName='");
				query1.append(cId);
				query1.append("' order by associateName ");
				// System.out.println("associate  "+query1.toString()+" cId "+cId);
			} else if (id.equalsIgnoreCase("Existing_Associate"))
			{
				query1.append("select DISTINCT abd.id, abd.associateName from associate_basic_data as abd left join associate_offering_mapping as aom on aom.associateName=abd.id where isConverted='1'");
				query1.append("order by associateName");
			}

			else
			{
				query1.append("select id,subIndustry from subindustrydatalevel2 where industry='");
				query1.append(id);
				query1.append("' order by subIndustry ");
			}
			// System.out.println("query for referace name***********************"+query1);
			List data2 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

			if (data2 != null)
			{
				jsonArray = new JSONArray();
				for (Iterator it = data2.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					if (obdata != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						/*
						 * param is either 1 or 0 1: for fetching name on both
						 * key and value place 0: for fetching id on key and
						 * name on value place
						 */
						if (param.equalsIgnoreCase("1"))
							formDetailsJson.put("ID", obdata[1].toString());
						else if (param.equalsIgnoreCase("0"))
							formDetailsJson.put("ID", obdata[0].toString());

						formDetailsJson.put("NAME", obdata[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: fetchReferredName of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeLostTakeActonOnFullview()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			try
			{
				ClientHelper ch = new ClientHelper();
				setLostStatusMAP(ch.fetchLostStatus(connectionSpace));

				returnValue = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public String convertToLost()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
			String str = employee_basicMap.get(empId);
			if (str != null)
			{
				acManager = str;
				// System.out.println(acManager);
				wherClause.put("userName", empId);
			}
			wherClause.put("isConverted", 2);
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());
			wherClause.put("lostId", lostId);
			wherClause.put("RCA", RCA);
			wherClause.put("CAPA", CAPA);
			if (otherlostreason != null && !otherlostreason.equalsIgnoreCase(""))
			{
				wherClause.put("otherlostreason", otherlostreason);
			}

			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, connectionSpace);

			// for updating opportunity_detail table.

			String queryC = "select offeringId from client_offering_mapping where id='" + getId() + "' and clientName='" + getClientId() + "'";
			List offringidList = cbt.executeAllSelectQuery(queryC.toString(), connectionSpace);

			if (offringidList != null && offringidList.size() > 0)
			{
				for (Iterator it1 = offringidList.iterator(); it1.hasNext();)
				{
					String obdata = (String) it1.next();
					setOfferingId(obdata);
					// System.out.println(" offeringId = " + getOfferingId());
				}
			}

			StringBuilder query = new StringBuilder("");
			query.append("update opportunity_details set lost_offering_flag = '1', lost_reason = '" + lostId + "', convertDate = '" + DateUtil.getCurrentDateUSFormat() + "'");
			query.append(" where clientName = '");
			query.append(getClientId());
			query.append("' and offeringId ='");
			query.append(offeringId);
			query.append("'");

			// System.out.println("To check cltID  "+getClientId()+" offeringId   "+offeringId+" query>"+query.toString());
			boolean res = cbt.updateTableColomn(connectionSpace, query);

			// For creating Table client_history
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			List<String> columnName = new ArrayList<String>();

			columnName.add("clientName");
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

			boolean status = cbt.createTable22("client_history", tableColumn, connectionSpace);

			// for entry in History table

			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("clientName");
			ob.setDataName(clientId);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offering");
			StringBuilder query1 = new StringBuilder("");
			query1.append("Select offeringId from client_offering_mapping where clientName='" + clientId + "'");
			List offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ob.setDataName(offeringId.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offeringLevel");
			StringBuilder query2 = new StringBuilder("");
			query2.append("Select offeringLevelId from client_offering_mapping where clientName='" + clientId + "'");
			List offeringLevelId1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			ob.setDataName(offeringLevelId1.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("isConverted");
			ob.setDataName(2);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertDate");
			ob.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("convertTime");
			ob.setDataName(DateUtil.getCurrentTime());
			insertHistory.add(ob);
			ob = new InsertDataTable();
			cbt.insertIntoTable("client_history", insertHistory, connectionSpace);

			if (b)
			{
				addActionMessage("Converted to lost successfully.");
				return SUCCESS;
			} else
			{
				return ERROR;
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: convertToLost of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String convertToClient()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			wherClause.put("isConverted", 0);
			wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
			wherClause.put("convertTime", DateUtil.getCurrentTime());
			condtnBlock.put("id", getId());
			boolean b = cbt.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, connectionSpace);

			// For creating Table client_history
			List<TableColumes> tableColumn = new ArrayList<TableColumes>();
			List<String> columnName = new ArrayList<String>();

			columnName.add("clientName");
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

			boolean status = cbt.createTable22("client_history", tableColumn, connectionSpace);

			// for entry in History table
			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("clientName");
			ob.setDataName(clientId);
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offering");
			StringBuilder query1 = new StringBuilder("");
			query1.append("Select offeringId from client_offering_mapping where clientName='" + clientId + "'");
			List offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			ob.setDataName(offeringId.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("offeringLevel");
			StringBuilder query2 = new StringBuilder("");
			query2.append("Select offeringLevelId from client_offering_mapping where clientName='" + clientId + "'");
			List offeringLevelId1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
			ob.setDataName(offeringLevelId1.get(0));
			insertHistory.add(ob);
			ob = new InsertDataTable();
			ob.setColName("isConverted");
			ob.setDataName(0);
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
			} else
			{
				return ERROR;
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: convertClientForOffering of class " + getClass(), e);
			e.printStackTrace();
		}

		try
		{
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String beforeDashboardView()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				getOfferingTableAndColumnNames();
				ClientHelper ch = new ClientHelper();
				int counter = 0, level = 0;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				// Prospective client
				pendingClient = ch.getProspectiveClientCount(cId, connectionSpace);
				// Lost client
				lostClient = ch.getLostClientCount(cId, connectionSpace);
				// Existing client
				existingClient = ch.getExistingClient(cId, connectionSpace);
				// Today's activities
				todayActivity = ch.getTodaysActivityCount(cId, connectionSpace, tableName);
				// Up coming seven days activities
				nextSevenDaysActivity = ch.getNextSevenDaysActivityCount(cId, connectionSpace, tableName);
				// Tomorrow's activities
				tomorrowActivity = ch.getTomorrowsActivityCount(cId, connectionSpace, tableName);

				StringBuilder query7 = new StringBuilder("");
				// Client status
				query7.append("select cs.id, cs.statusName, count(distinct(cbd.id)) from client_status as cs, " + "client_takeaction as cta , " + "client_contact_data as ccd, client_basic_data as cbd, client_offering_mapping as com " + "where cbd.id = ccd.clientName " + "and ccd.id = cta.contacId and cbd.userName = '" + cId + "' and cta.statusId = cs.id and com.clientName = cbd.id and com.isConverted = 0 " + "and cta.isFinished = '0' " + "group by cta.statusId order by cs.statusName ");

				List dataCount7 = cbt.executeAllSelectQuery(query7.toString(), connectionSpace);

				if (dataCount7 != null && dataCount7.size() > 0)
				{
					clientStatusAllList = new ArrayList<ArrayList<String>>();
					ArrayList<String> al = null;
					for (Iterator iterator = dataCount7.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						al = new ArrayList<String>();
						al.add(String.valueOf(object[0].toString()));
						al.add((object[1] == null || object[1].toString().equalsIgnoreCase("")) ? "NA" : object[1].toString());
						al.add(object[2].toString());

						clientStatusAllList.add(al);
					}
				}

				returnString = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
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
			CommonOperInterface cbt = new CommonConFactory().createInterface();
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
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String beforeClientExcelUpload()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		} else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	/*
	 * public String viewWeightageDetails() { String returnString = LOGIN; if
	 * (ValidateSession.checkSession()) { CommonOperInterface coi = new
	 * CommonConFactory().createInterface(); offeringWeightageDetails = new
	 * ClientHelper2().calculateIndividualWeightageForOffering(coi, id,
	 * EntityType.CLIENT); // System.out.println("offeringWeightageDetails:" +
	 * // offeringWeightageDetails.size());
	 * 
	 * returnString = SUCCESS; } return returnString; }
	 */
	public String viewWeightageDetails()
	{
		String returnString = LOGIN;
		if (ValidateSession.checkSession())
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			offeringWeightageDetails2 = new ClientHelper2().calculateIndividualWeightageForOffering(coi, id, EntityType.CLIENT);
			Set setOfKeys = offeringWeightageDetails2.keySet();
			Iterator iterator = setOfKeys.iterator();
			while (iterator.hasNext())
			{
				String key = (String) iterator.next();
				HashMap<String, HashMap<String, String>> value = offeringWeightageDetails2.get(key);

				// offeringWeightageDetails5.put(key, (HashMap<String,
				// HashMap<String, String>>) value);
				// System.out.println("Key: "+ key+", Value: "+ value);
				if (key == "1")
				{
					Set setOfKeys1 = value.keySet();
					Iterator iterator1 = setOfKeys1.iterator();
					while (iterator1.hasNext())
					{
						String key1 = (String) iterator1.next();
						HashMap<String, String> value1 = (HashMap<String, String>) value.get(key1);
						offeringWeightageDetailsVal.put(key1, value1);
						// System.out.println("Key1: "+ key1+", Value1: "+
						// value1);
					}

				}

				if (key == "2")
				{
					Set setOfKeys2 = value.keySet();
					Iterator iterator2 = setOfKeys2.iterator();
					while (iterator2.hasNext())
					{
						String key2 = (String) iterator2.next();
						HashMap<String, String> value2 = (HashMap<String, String>) value.get(key2);
						offeringWeightageDetailsZeroval.put(key2, value2);
						// System.out.println("Key2: "+ key2+", Value2: "+
						// value2);
					}

				}

			}
			returnString = SUCCESS;

		}
		return returnString;
	}

	// for mom view
	@SuppressWarnings("unchecked")
	public String beforviewMom()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{
			CommonHelper mom = new CommonHelper();
			momFullViewMap = mom.FetchMomDetails(connectionSpace, id, momType);
			showMomDetails = momFullViewMap.get("Client Contact") == "NA" ? false : true;
			// System.out.println("size::::::" + momFullViewMap.size());
			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String downloadattachedment()
	{
		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				File file = new File(fileName);
				// System.out.println("file>>>>>>>>>>>>>>>>>>>" + file);
				if (file.exists())
				{
					fileInputStream = new FileInputStream(file);
					return "download";
				} else
				{
					addActionError("File does not exist");
					return ERROR;
				}
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			returnString = SUCCESS;
		}
		return returnString;
	}

	public String beforeClientTakeAction()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	/*
	 * Anoop 15-4-2014 Fetch client last status
	 */
	public String fetchLastStatusForOffering()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			status = new ClientHelper2().fetchLastStatusForOffering(EntityType.CLIENT, offeringId, id);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Sanjiv 20-10-2014 Fetch match time
	 */
	public String validateTimeForOffering()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			String dateUS = scheduletime.substring(0, 10);
			String timeval = scheduletime.substring(10, 16);
			// System.out.println("dateUS>"+dateUS+"   timeval >"+timeval);
			scheduletime = DateUtil.convertDateToUSFormat(dateUS) + timeval;
			// System.out.println("scheduletime.substring(beginIndex, endIndex)>>>"+scheduletime);
			timestatus = new ClientHelper2().fetchTimeStatusForOffering(EntityType.CLIENT, cId, id, scheduletime);
			/*
			 * System.out.println(timestatus); String
			 * ar[]=timestatus.split("#"); timestatus=ar[1]; String
			 * arr[]=ar[0].split(" "); time=arr[1];
			 */
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// for client contact and email ids.
	public String fetchContacAndEmail()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			clientContactandEmailMap = new LinkedHashMap<String, String>();
			clientContactandEmailMap = new ClientHelper3().fetchAllContactOfClient(id);
			templateNameList = new LinkedHashMap<String, String>();
			templateNameList = new ClientHelper3().fetchMailTemplateName();
			frequency = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> instantmailfields = Configuration.getConfigurationData("mapped_instant_mail_configuration", accountID, connectionSpace, "", 0, "table_name", "instant_mail_configuration");
			if (instantmailfields != null && instantmailfields.size() > 0)
			{
				for (GridDataPropertyView gdp : instantmailfields)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();

					if (gdp.getColType().trim().equalsIgnoreCase("R") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setColType(gdp.getColType());
						obj.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						frequency.add(obj);
					}

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	// for client contact and sales person's cc email ids.
	public String fetchMailIdForCC()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			if (id != null)
			{
				reqccemailList = new HashMap<Integer, String>();
				reqccemailList = new ClientHelper3().fetchCCContactOfClient(id, emailtofilter);
			}
			if (reqccemailList != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Map.Entry<Integer, String> entry : reqccemailList.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String fetchMailIdForBCC()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			if (id != null)
			{
				reqccemailList = new HashMap<Integer, String>();
				reqccemailList = new ClientHelper3().fetchBCCContactOfClient(id, emailtofilter, emailtofiltertwo);
			}
			if (reqccemailList != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Map.Entry<Integer, String> entry : reqccemailList.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String mailDataForTemplate()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			if (message != null)
			{
				setMessageValue(new ClientHelper3().fetchEmailDataForTemplate(message));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String beforeopportunityDetails()
	{

		if (!ValidateSession.checkSession())
			return LOGIN;
		// System.out.println("opportunityType> in beforeopportunityDetails>>>"
		// + opportunityType);
		if (opportunityType != null && opportunityType.equalsIgnoreCase("Lost"))
		{
			setLostOpportunityViewProperty();
		}

		else if (opportunityType != null && opportunityType.equalsIgnoreCase("Closed"))
		{
			setClosedOpportunityViewProperty();
		} else
		{
			setOpportunityViewProperty();
		}

		employee_basicMap = new LinkedHashMap<String, String>();
		// employee_basicMap = ch3.fetchAllEmployee();
		EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
		employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
		ClientHelper3 ch = new ClientHelper3();
		offeringforOpportunityMap = new TreeMap<String, String>();
		offeringforOpportunityMap = ch.fetchOffring();

		opportunityNameMap = new TreeMap<String, String>();
		opportunityNameMap = ch.fetchOpportunityName();

		salesStagesMap = new TreeMap<String, String>();
		salesStagesMap = ch.fetchsalesStage();

		return SUCCESS;
	}

	public void setLostOpportunityViewProperty()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		opportunityViewProp.add(gpv);
		List<String> listDataToShow = new ClientHelper().getClientBasicDataToShow();
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_opportunity_details_configuration", accountID, connectionSpace, "", 0, "table_name", "opportunity_details_configuration");

		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("po_number"))
			{
				gpv.setHideOrShow("true");
			} else if (gdp.getColomnName().equalsIgnoreCase("po_date"))
			{
				gpv.setHideOrShow("true");
			} else if (gdp.getColomnName().equalsIgnoreCase("offering_price"))
			{
				gpv.setHideOrShow("true");
			} else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setAlign(gdp.getAlign());
			gpv.setWidth(gdp.getWidth());
			if (gdp.getFormatter() != null)
				gpv.setFormatter(gdp.getFormatter());
			opportunityViewProp.add(gpv);
		}
	}

	public void setClosedOpportunityViewProperty()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		opportunityViewProp.add(gpv);
		List<String> listDataToShow = new ClientHelper().getClientBasicDataToShow();
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_opportunity_details_configuration", accountID, connectionSpace, "", 0, "table_name", "opportunity_details_configuration");

		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());

			if (gdp.getColomnName().equalsIgnoreCase("lost_reason"))
			{
				gpv.setHideOrShow("true");
			} else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setAlign(gdp.getAlign());
			gpv.setWidth(gdp.getWidth());
			if (gdp.getFormatter() != null)
				gpv.setFormatter(gdp.getFormatter());
			opportunityViewProp.add(gpv);
		}

		gpv = new GridDataPropertyView();
		gpv.setColomnName("po_attach");
		gpv.setHeadingName("PO. Attachment");
		gpv.setEditable("false");
		gpv.setFormatter("getDoc");
		gpv.setColType("F");
		gpv.setSearch("false");
		opportunityViewProp.add(gpv);
	}

	public void setOpportunityViewProperty()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		opportunityViewProp.add(gpv);
		List<String> listDataToShow = new ClientHelper().getClientBasicDataToShow();
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_opportunity_details_configuration", accountID, connectionSpace, "", 0, "table_name", "opportunity_details_configuration");

		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("convertDate") || gdp.getColomnName().equalsIgnoreCase("lost_reason") || gdp.getColomnName().equalsIgnoreCase("po_number") || gdp.getColomnName().equalsIgnoreCase("po_date") || gdp.getColomnName().equalsIgnoreCase("offering_price"))
			{
				gpv.setHideOrShow("true");
			} else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setAlign(gdp.getAlign());
			gpv.setWidth(gdp.getWidth());
			if (gdp.getFormatter() != null)
				gpv.setFormatter(gdp.getFormatter());
			opportunityViewProp.add(gpv);
		}
	}

	public String viewOpportunityGrid()
	{
		String salesStages;
		String compellingReason;

		String queryres = null;
		String historycount;
		if (!ValidateSession.checkSession())
			return LOGIN;

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append("select count(*) from opportunity_details");
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
			StringBuilder queryTemp = new StringBuilder("");
			queryTemp.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from opportunity_details as opd ");

			List fieldNames = Configuration.getColomnList("mapped_opportunity_details_configuration", accountID, connectionSpace, "opportunity_details_configuration");
			List<Object> Listhb = new ArrayList<Object>();
			for (Iterator it = fieldNames.iterator(); it.hasNext();)
			{
				// generating the dyanamic query based on selected fields
				Object obdata = (Object) it.next();
				if (obdata != null)
				{
					if (obdata.toString().equalsIgnoreCase("clientName"))
					{
						queryTemp.append(" cbd.clientName , ");
						queryEnd.append(" inner join  client_basic_data as cbd on cbd.id= opd.clientName ");
						queryEnd.append("inner join client_offering_mapping as com on com.clientName = opd.clientName");
					} else if (obdata.toString().equalsIgnoreCase("offeringId"))
					{
						queryTemp.append(" off.subofferingname 'offeringId', ");
						queryEnd.append(" inner join offeringlevel3 as off on off.id = opd.offeringId ");
					} else if (obdata.toString().equalsIgnoreCase("userName"))
					{
						queryTemp.append(" emp.empName 'userName', ");
						queryEnd.append(" inner join employee_basic as emp on emp.id = opd.userName ");
					} else if (obdata.toString().equalsIgnoreCase("lost_reason"))
					{
						queryTemp.append(" lop.lostReason 'lost_reason', ");
						queryEnd.append(" left join lostoportunity as lop on lop.id = opd.lost_reason ");
					} else if (obdata.toString().equalsIgnoreCase("forecast_category"))
					{
						queryTemp.append(" fcm.forcastName, ");
						queryEnd.append(" left join forcastcategarymaster as fcm on fcm.id = opd.forecast_category ");
					}

					else if (obdata.toString().equalsIgnoreCase("sales_stages"))
					{
						queryTemp.append(" ssm.salesstageName, ");
						queryEnd.append(" left join salesstagemaster as ssm on ssm.id = opd.sales_stages ");
					} else if (obdata.toString().equalsIgnoreCase("convertDate"))
					{
						queryTemp.append(" com.convertDate, ");
					} else if (obdata.toString().equalsIgnoreCase("po_number"))
					{
						queryTemp.append(" com.poNumber 'po_number', ");
					} else if (obdata.toString().equalsIgnoreCase("po_date"))
					{
						queryTemp.append(" com.poDate 'po_date', ");
					} else if (obdata.toString().equalsIgnoreCase("offering_price"))
					{
						queryTemp.append(" com.offering_price, ");
					} else
					{
						queryTemp.append("opd." + obdata.toString() + ",");
					}
				}
			}

			if (opportunityType != null && opportunityType.equalsIgnoreCase("Closed"))
			{
				queryTemp.append(" com.po_attachment ,");
			}

			query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
			query.append(" ");
			query.append(queryEnd.toString());
			query.append(" where ");
			if (opportunityType != null && opportunityType.equalsIgnoreCase("Lost"))
			{
				query.append(" opd.opportunity_value > '0' ");
				query.append(" and opd.clientName <> 'NA' ");
				query.append(" and com.isConverted NOT IN('1','0')  and com.offeringId = opd.offeringId");
			} else if (opportunityType != null && opportunityType.equalsIgnoreCase("Closed"))
			{
				query.append(" opd.opportunity_value > '0' ");
				query.append(" and opd.clientName <> 'NA' ");
				query.append(" and com.isConverted NOT IN('0','2')  and com.offeringId = opd.offeringId");
			} else
			{
				query.append(" opd.opportunity_value > '0' ");
				query.append(" and opd.clientName <> 'NA' ");
				query.append(" and com.isConverted NOT IN('1','2')  and com.offeringId = opd.offeringId");
				query.append(" and opd.lost_offering_flag = '0'");
			}

			if (this.getAcManager() != null && !this.getAcManager().equals("-1") && !this.getAcManager().equalsIgnoreCase("undefined"))
			{
				query.append(" and opd.userName = '");
				query.append(acManager);
				query.append("' ");
			}
			if (fromDate != null && !fromDate.equals("") && fromDateSearch != null && !fromDateSearch.equals(""))
			{
				query.append(" and opd.closure_date between '");
				query.append(DateUtil.convertDateToUSFormat(fromDate));
				query.append("' and '");
				query.append(DateUtil.convertDateToUSFormat(fromDateSearch));
				query.append("'");
			}
			/*
			 * System.out.println(
			 * "   fromDate.split()[0] +  fromDate.split()[1])   "
			 * +fromDate.split("-")[0] + "-" + fromDate.split("-")[1]); if (){
			 * query.append(" and opd.closure_date like '");
			 * query.append(fromDate.split("-")[0] + "-" +
			 * fromDate.split("-")[1]); query.append("%' ");
			 * 
			 * }
			 */

			if (client_Name != null && !client_Name.equals("-1"))
			{
				query.append(" and opd.clientName = '");
				query.append(client_Name);
				query.append("' ");
			}
			/*
			 * if (salesstageName != null &&
			 * !salesstageName.equals("Current Stage")) {
			 * query.append(" and cbd.sales_stages ='");
			 * query.append(salesstageName); query.append("' "); }
			 */

			if (expectency != null && !expectency.equals("Expectancy"))
			{
				query.append(" and opd.expectency = '");
				query.append(expectency);
				query.append("' ");
			}

			if (offeringOpportunity != null && !offeringOpportunity.equals("-1"))
			{
				query.append(" and opd.offeringId = '");
				query.append(offeringOpportunity);
				query.append("' ");
			}

			if (getSearchOper().equalsIgnoreCase("eq"))
			{
				query.append(" and cbd." + getSearchField() + " = '" + getSearchString() + "'");
			} else if (getSearchOper().equalsIgnoreCase("cn"))
			{
				query.append(" and cbd." + getSearchField() + " like '%" + getSearchString() + "%'");
			} else if (getSearchOper().equalsIgnoreCase("bw"))
			{
				query.append(" and cbd." + getSearchField() + " like '" + getSearchString() + "%'");
			} else if (getSearchOper().equalsIgnoreCase("ne"))
			{
				query.append(" and cbd." + getSearchField() + " <> '" + getSearchString() + "'");
			} else if (getSearchOper().equalsIgnoreCase("ew"))
			{
				query.append(" and cbd." + getSearchField() + " like '%" + getSearchString() + "'");
			}
			if (searchParameter != null && !searchParameter.equalsIgnoreCase(""))
			{
				query.append(" and ");
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					int k = 0;
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{
						String object = iterator.next().toString();
						if (object != null)
						{
							// System.out.println("EEE>>>>"+object.toString());
							if (object.toString().equalsIgnoreCase("clientName"))
							{
								query.append(" cbd.clientName LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("offeringId"))
							{
								query.append(" off.subofferingname LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("userName"))
							{
								query.append(" emp.empName LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("lost_reason"))
							{
								query.append(" lop.lostReason LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("forecast_category"))
							{
								query.append(" fcm.forcastName LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("sales_stages"))
							{
								query.append(" ssm.salesstageName LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("convertDate"))
							{
								query.append(" com.convertDate LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("po_number"))
							{
								query.append(" com.poNumber LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("po_date"))
							{
								query.append(" com.poDate LIKE " + "'%" + searchParameter + "%" + "'");
							} else if (object.toString().equalsIgnoreCase("offering_price"))
							{
								query.append(" com.offering_price LIKE " + "'%" + searchParameter + "%" + "'");
							} else
							{
								query.append(" opd." + object.toString() + " LIKE " + "'%" + searchParameter + "%" + "'");
							}
						}
						if (k < fieldNames.size() - 1)
						{
							/*
							 * if
							 * (!object.toString().equalsIgnoreCase("clientName"
							 * )) {
							 */
							query.append(" OR ");
							/* } */
						} else
							query.append(" ");
						k++;
					}
				}
			}
			//
			query.append(" order by cbd.clientName ASC limit " + from + "," + to);
			// System.out.println("query>>>opportunity>" + query.toString());
			/**
			 * **************************checking for colomon change due to
			 * configuration if then alter table
			 */
			cbt.checkTableColmnAndAltertable(fieldNames, "opportunity_details", connectionSpace);

			// System.out.println("query:::" + query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			// int total =

			try
			{
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{

						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						String clientname = "";
						String type = "";

						String clientId = "";
						double weightage = 0;
						ClientHelper2 ch2 = new ClientHelper2();
						String clientnameval = null;
						String offeringval = null;
						String offeringId = null;
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] == null || obdata[k].toString().equalsIgnoreCase(""))
							{
								obdata[k] = fieldNames.get(k).toString().equalsIgnoreCase("weightage") ? "0.0" : "NA";
							}
							if (obdata[k] != null)
							{
								// getting sales stages and compelling reason.
								if ("clientName".equalsIgnoreCase(fieldNames.get(k).toString()))
								{
									clientnameval = obdata[k].toString();
									if (clientnameval != null && clientnameval.equals("-1") && clientnameval.equalsIgnoreCase("undefined"))
									{
										clientId = new ClientHelper3().fetchClientIdForName(clientnameval);
									}
								}
								if ("offeringId".equalsIgnoreCase(fieldNames.get(k).toString()))
								{
									offeringval = obdata[k].toString();
									if (offeringval != null && offeringval.equals("-1") && offeringval.equalsIgnoreCase("undefined"))
									{
										offeringId = new ClientHelper3().fetchOfferingIdForName(offeringval);
									}
									// System.out.println("  in offeringval "+offeringval+
									// " offeringId "+offeringId);
								}
								if (offeringval != null && offeringval.equals("-1") && offeringval.equalsIgnoreCase("undefined") && clientnameval != null && clientnameval.equals("-1") && clientnameval.equalsIgnoreCase("undefined"))
								{
									queryres = new ClientHelper3().fetchSalesStageCompReason(offeringval, clientnameval);
								}
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									// clientId = obdata[k].toString();
								} else
								{

									if ("opportunity_value".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										grandtotal = Long.parseLong(obdata[k].toString());
										grandtotalsum += grandtotal;
									}
									if ("closure_date".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									if ("convertDate".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									if ("po_date".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}

									if ("sales_stages".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										if (queryres != null)
										{
											obdata[k] = queryres.split("#")[0];
										}
									}
									if ("compelling_reason".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										if (queryres != null)
										{
											obdata[k] = queryres.split("#")[1];
											queryres = null;
										}
									}
									if ("weightage".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										try
										{
											// calculate weightage
											weightage = ch2.calculateOfferingWiseWeightage(cbt, clientId, EntityType.CLIENT, offeringId);
											obdata[k] = weightage;
											// System.out.println("offeringId "+offeringId+" weightage  "+weightage+" clientId  "+clientId);
										} catch (Exception e)
										{
											e.printStackTrace();
										}
									}
									if ("opp_history".equalsIgnoreCase(fieldNames.get(k).toString()))
									{
										historycount = new ClientHelper3().historyCount(offeringval, clientnameval);
										obdata[k] = historycount;
									}
									if (opportunityType != null && opportunityType.equalsIgnoreCase("Closed"))
									{
										if (obdata[20] != null)
										{
											File file = new File(obdata[20].toString());
											boolean res = file.exists();
											if (res)
											{
												one.put("po_attach", obdata[20].toString());
											} else
											{
												file = null;
												one.put("po_attach", "NA");
											}
										} else
										{
											one.put("po_attach", "NA");
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

								}
							}
						}
						Listhb.add(one);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			userdata.put("clientName", "Total :");
			userdata.put("opportunity_value", grandtotalsum);
			setOpportunityViewList(Listhb);
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
		}
		return SUCCESS;
	}

	// for modify data
	public String viewModifyOpportunityAction()
	{
		String returnResult = ERROR;
		String paramValue;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					// System.out.println("call for edit");
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						if (Parmname.equalsIgnoreCase("closure_date"))
						{
							paramValue = DateUtil.convertDateToUSFormat(request.getParameter(Parmname));
						} else
						{
							paramValue = request.getParameter(Parmname);
						}
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("opportunity_details", wherClause, condtnBlock, connectionSpace);
				}
				/*
				 * else if (getOper().equalsIgnoreCase("del")) {
				 * CommonOperInterface cbt = new
				 * CommonConFactory().createInterface(); String tempIds[] =
				 * getId().split(","); StringBuilder condtIds = new
				 * StringBuilder(); int i = 1; for (String H : tempIds) { if (i
				 * < tempIds.length) condtIds.append(H + " ,"); else
				 * condtIds.append(H); i++; } StringBuilder query = new
				 * StringBuilder();query.append(
				 * "UPDATE opportunity_details SET status='1' WHERE id IN(" +
				 * condtIds + ")"); cbt.updateTableColomn(connectionSpace,
				 * query); //query.setLength(0);
				 * 
				 * }
				 */
				returnResult = SUCCESS;
			} catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewModifyTaskType of class " + getClass(), exp);
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// For PO download.
	public String docDownload() throws IOException
	{
		if (!ValidateSession.checkSession())
			return LOGIN;
		String result = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List docName = null;
		try
		{
			// System.out.println( this.getPo_attachFileName());
			if (getId() != null && getId() != "")
			{
				docName = cbt.executeAllSelectQuery("SELECT po_attachment FROM client_offering_mapping WHERE id=((Select id from  client_offering_mapping where clientName IN ((Select clientName from opportunity_details where id='" + id + "')) and offeringId IN ((Select offeringId from opportunity_details where id='" + id + "'))))", connectionSpace);
				if (docName.get(0) != null && !docName.get(0).equals(""))
				{
					fileName = (String) docName.get(0);
					File file = new File(fileName);
					boolean res = file.exists();
					if (res)
					{
						inputstream = new FileInputStream(file);
						fileName = file.getName();
						result = SUCCESS;
					} else
					{
						addActionError("Attachment Not Found !!!!!");
						// System.out.println("in Error");
						result = ERROR;
					}

				} else
				{
					addActionError("Attachment Not Found !!!!!");
					result = ERROR;
				}
			}
		} catch (FileNotFoundException file)
		{
			addActionError("Attachment Not Found !!!!!");
			result = ERROR;
			file.printStackTrace();
		} catch (Exception e)
		{

			inputstream.close();
			e.printStackTrace();
			addActionError("Attachment Not Found !!!!!");
			result = ERROR;
		}
		return result;
	}

	// check client and offering mapping ....
	public String checkSuboffering()
	{
		if (!ValidateSession.checkSession())
			return LOGIN;
		String result = null;
		try
		{
			if (this.client != null && this.subofferingname != null)
			{
				checkEmp = new HashMap<String, String>();
				boolean status = false;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String str = "Select clientName,offeringId from client_offering_mapping where clientName='" + client + "' and offeringId='" + subofferingname + "'";
				List clientData = cbt.executeAllSelectQuery(str, connectionSpace);
				if (clientData != null && clientData.size() > 0)
				{
					status = true;
				}
				if (!status)
				{
					checkEmp.put("msg", "You Can Create.");
					checkEmp.put("status", "false");
				} else
				{
					StringBuilder sb = new StringBuilder("");
					String emp = null;
					if (this.client != null && this.subofferingname != null)
					{
						if (this.client != null)
						{
							String str1 = "Select cbd.clientName,emp.empName from client_basic_data as cbd Left Join employee_basic as emp on emp.id=cbd.acManager where cbd.id='" + client + "'";
							List clientData1 = cbt.executeAllSelectQuery(str1, connectionSpace);
							Iterator itr = clientData1.iterator();
							while (itr.hasNext())
							{
								Object ob[] = (Object[]) itr.next();
								if (ob[0].toString() != null)
								{
									sb.append(ob[0].toString() + " is already mapped ");
								}
								if (ob[1].toString() != null)
								{
									emp = ob[1].toString();
								}
							}
						}
					}

					if (this.subofferingname != null)
					{
						String str1 = "Select subofferingname from offeringlevel3 where id='" + subofferingname + "'";
						List clientData1 = cbt.executeAllSelectQuery(str1, connectionSpace);
						if (clientData1.size() > 0)
						{
							sb.append(" for " + clientData1.get(0).toString() + " with " + emp + " .");
						}
					}
					checkEmp.put("msg", sb.toString());
					checkEmp.put("status", "true");
				}
				result = SUCCESS;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method checkSuboffering of class " + getClass(), e);
			e.printStackTrace();
			result = ERROR;
		}
		return result;
	}

	public String fetchExistingFreeRecord()
	{
		ClientHelper3 ch3 = new ClientHelper3();
		existingFreeContact = new HashMap<String, String>();
		existingFreeContact = ch3.fetchExistingRecord(existingfreeId);
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public int getClientForBasicDetails()
	{
		return clientForBasicDetails;
	}

	public void setClientForBasicDetails(int clientForBasicDetails)
	{
		this.clientForBasicDetails = clientForBasicDetails;
	}

	public int getClientForOfferingDetails()
	{
		return clientForOfferingDetails;
	}

	public void setClientForOfferingDetails(int clientForOfferingDetails)
	{
		this.clientForOfferingDetails = clientForOfferingDetails;
	}

	public int getClientForContacDetails()
	{
		return clientForContacDetails;
	}

	public void setClientForContacDetails(int clientForContacDetails)
	{
		this.clientForContacDetails = clientForContacDetails;
	}

	public int getClientForAttachmentDetails()
	{
		return clientForAttachmentDetails;
	}

	public void setClientForAttachmentDetails(int clientForAttachmentDetails)
	{
		this.clientForAttachmentDetails = clientForAttachmentDetails;
	}

	public String getSourceMaster()
	{
		return sourceMaster;
	}

	public void setSourceMaster(String sourceMaster)
	{
		this.sourceMaster = sourceMaster;
	}

	public String getReferedBy()
	{
		return referedBy;
	}

	public void setReferedBy(String referedBy)
	{
		this.referedBy = referedBy;
	}

	public String getRefName()
	{
		return refName;
	}

	public void setRefName(String refName)
	{
		this.refName = refName;
	}

	public String getStarRating()
	{
		return starRating;
	}

	public void setStarRating(String starRating)
	{
		this.starRating = starRating;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getAcManager()
	{
		return acManager;
	}

	public void setAcManager(String acManager)
	{
		this.acManager = acManager;
	}

	public String getTargetAchieved()
	{
		return targetAchieved;
	}

	public void setTargetAchieved(String targetAchieved)
	{
		this.targetAchieved = targetAchieved;
	}

	public Map<String, String> getSourceMasterMap()
	{
		return sourceMasterMap;
	}

	public Map<String, String> getEmployee_basicMap()
	{
		return employee_basicMap;
	}

	public Map<String, String> getClient_statusMap()
	{
		return client_statusMap;
	}

	public Map<String, String> getLocation_master_dataMap()
	{
		return location_master_dataMap;
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

	public boolean isOLevel2()
	{
		return OLevel2;
	}

	public void setOLevel2(boolean level2)
	{
		OLevel2 = level2;
	}

	public boolean isOLevel3()
	{
		return OLevel3;
	}

	public void setOLevel3(boolean level3)
	{
		OLevel3 = level3;
	}

	public boolean isOLevel4()
	{
		return OLevel4;
	}

	public void setOLevel4(boolean level4)
	{
		OLevel4 = level4;
	}

	public boolean isOLevel5()
	{
		return OLevel5;
	}

	public void setOLevel5(boolean level5)
	{
		OLevel5 = level5;
	}

	public Map<String, String> getVerticalMap()
	{
		return verticalMap;
	}

	public String getOLevel1LevelName()
	{
		return OLevel1LevelName;
	}

	public void setOLevel1LevelName(String level1LevelName)
	{
		OLevel1LevelName = level1LevelName;
	}

	public String getOLevel2LevelName()
	{
		return OLevel2LevelName;
	}

	public void setOLevel2LevelName(String level2LevelName)
	{
		OLevel2LevelName = level2LevelName;
	}

	public String getOLevel3LevelName()
	{
		return OLevel3LevelName;
	}

	public void setOLevel3LevelName(String level3LevelName)
	{
		OLevel3LevelName = level3LevelName;
	}

	public String getOLevel4LevelName()
	{
		return OLevel4LevelName;
	}

	public void setOLevel4LevelName(String level4LevelName)
	{
		OLevel4LevelName = level4LevelName;
	}

	public String getOLevel5LevelName()
	{
		return OLevel5LevelName;
	}

	public void setOLevel5LevelName(String level5LevelName)
	{
		OLevel5LevelName = level5LevelName;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getOfferinglevel()
	{
		return Offeringlevel;
	}

	public void setOfferinglevel(String offeringlevel)
	{
		Offeringlevel = offeringlevel;
	}

	public Map<String, Object> getOfferingMap()
	{
		return offeringMap;
	}

	public JSONObject getResponseDetailsJson()
	{
		return responseDetailsJson;
	}

	public void setResponseDetailsJson(JSONObject responseDetailsJson)
	{
		this.responseDetailsJson = responseDetailsJson;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public Map<String, String> getPClientMap()
	{
		return pClientMap;
	}

	public List<ConfigurationUtilBean> getClientTextBox()
	{
		return clientTextBox;
	}

	public void setClientTextBox(List<ConfigurationUtilBean> clientTextBox)
	{
		this.clientTextBox = clientTextBox;
	}

	public List<ConfigurationUtilBean> getClientDropDown()
	{
		return clientDropDown;
	}

	public void setClientDropDown(List<ConfigurationUtilBean> clientDropDown)
	{
		this.clientDropDown = clientDropDown;
	}

	public List<ConfigurationUtilBean> getClientTextBoxForContact()
	{
		return clientTextBoxForContact;
	}

	public void setClientTextBoxForContact(List<ConfigurationUtilBean> clientTextBoxForContact)
	{
		this.clientTextBoxForContact = clientTextBoxForContact;
	}

	public List<ConfigurationUtilBean> getClientDropDownForContact()
	{
		return clientDropDownForContact;
	}

	public void setClientDropDownForContact(List<ConfigurationUtilBean> clientDropDownForContact)
	{
		this.clientDropDownForContact = clientDropDownForContact;
	}

	public Map<String, String> getClientDateControls()
	{
		return clientDateControls;
	}

	public boolean isAnniversaryExist()
	{
		return anniversaryExist;
	}

	public void setAnniversaryExist(boolean anniversaryExist)
	{
		this.anniversaryExist = anniversaryExist;
	}

	public String getAnniversary()
	{
		return anniversary;
	}

	public void setAnniversary(String anniversary)
	{
		this.anniversary = anniversary;
	}

	public boolean isBirthdayExist()
	{
		return birthdayExist;
	}

	public void setBirthdayExist(boolean birthdayExist)
	{
		this.birthdayExist = birthdayExist;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
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

	public List<GridDataPropertyView> getMasterViewPropForContact()
	{
		return masterViewPropForContact;
	}

	public void setMasterViewPropForContact(List<GridDataPropertyView> masterViewPropForContact)
	{
		this.masterViewPropForContact = masterViewPropForContact;
	}

	public List<Object> getMasterViewListForContact()
	{
		return masterViewListForContact;
	}

	public void setMasterViewListForContact(List<Object> masterViewListForContact)
	{
		this.masterViewListForContact = masterViewListForContact;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public String getStatusName()
	{
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public Map<String, String> getBasicParams()
	{
		return basicParams;
	}

	public List<List<String>> getContactList()
	{
		return contactList;
	}

	public void setContactList(List<List<String>> contactList)
	{
		this.contactList = contactList;
	}

	public Map<Integer, List<String>> getDataList()
	{
		return dataList;
	}

	public List<Parent> getParentContact()
	{
		return parentContact;
	}

	public void setParentContact(List<Parent> parentContact)
	{
		this.parentContact = parentContact;
	}

	public List<String> getNamesContact()
	{
		return namesContact;
	}

	public void setNamesContact(List<String> namesContact)
	{
		this.namesContact = namesContact;
	}

	public List<Parent> getParentOffering()
	{
		return parentOffering;
	}

	public void setParentOffering(List<Parent> parentOffering)
	{
		this.parentOffering = parentOffering;
	}

	public List<String> getNamesOffering()
	{
		return namesOffering;
	}

	public void setNamesOffering(List<String> namesOffering)
	{
		this.namesOffering = namesOffering;
	}

	public List<Parent> getParentTakeaction()
	{
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction)
	{
		this.parentTakeaction = parentTakeaction;
	}

	public List<String> getNamesTakeaction()
	{
		return namesTakeaction;
	}

	public void setNamesTakeaction(List<String> namesTakeaction)
	{
		this.namesTakeaction = namesTakeaction;
	}

	public String getIsExistingClient()
	{
		return isExistingClient;
	}

	public void setIsExistingClient(String isExistingClient)
	{
		this.isExistingClient = isExistingClient;
	}

	public String getParam()
	{
		return param;
	}

	public void setParam(String param)
	{
		this.param = param;
	}

	public String getLostFlag()
	{
		return lostFlag;
	}

	public void setLostFlag(String lostFlag)
	{
		this.lostFlag = lostFlag;
	}

	public String getPAction()
	{
		return pAction;
	}

	public void setPAction(String action)
	{
		pAction = action;
	}

	public String getSelectRows()
	{
		return selectRows;
	}

	public void setSelectRows(String selectRows)
	{
		this.selectRows = selectRows;
	}

	public String getConvertToClient()
	{
		return convertToClient;
	}

	public void setConvertToClient(String convertToClient)
	{
		this.convertToClient = convertToClient;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public Map<Integer, String> getTargetKpiLiist()
	{
		return targetKpiLiist;
	}

	public void setTargetKpiLiist(Map<Integer, String> targetKpiLiist)
	{
		this.targetKpiLiist = targetKpiLiist;
	}

	public boolean isClientNameExist()
	{
		return clientNameExist;
	}

	public void setClientNameExist(boolean clientNameExist)
	{
		this.clientNameExist = clientNameExist;
	}

	public int getPendingClient()
	{
		return pendingClient;
	}

	public void setPendingClient(int pendingClient)
	{
		this.pendingClient = pendingClient;
	}

	public int getLostClient()
	{
		return lostClient;
	}

	public void setLostClient(int lostClient)
	{
		this.lostClient = lostClient;
	}

	public int getNextAction()
	{
		return nextAction;
	}

	public void setNextAction(int nextAction)
	{
		this.nextAction = nextAction;
	}

	public String getFromDashboard()
	{
		return fromDashboard;
	}

	public void setFromDashboard(String fromDashboard)
	{
		this.fromDashboard = fromDashboard;
	}

	public int getExistingClient()
	{
		return existingClient;
	}

	public void setExistingClient(int existingClient)
	{
		this.existingClient = existingClient;
	}

	public int getNextSevenDaysActivity()
	{
		return nextSevenDaysActivity;
	}

	public void setNextSevenDaysActivity(int nextSevenDaysActivity)
	{
		this.nextSevenDaysActivity = nextSevenDaysActivity;
	}

	public String getActivityType()
	{
		return activityType;
	}

	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}

	public int getTodayActivity()
	{
		return todayActivity;
	}

	public void setTodayActivity(int todayActivity)
	{
		this.todayActivity = todayActivity;
	}

	public int getTomorrowActivity()
	{
		return tomorrowActivity;
	}

	public void setTomorrowActivity(int tomorrowActivity)
	{
		this.tomorrowActivity = tomorrowActivity;
	}

	public ArrayList<ArrayList<String>> getClientStatusAllList()
	{
		return clientStatusAllList;
	}

	public void setClientStatusAllList(ArrayList<ArrayList<String>> clientStatusAllList)
	{
		this.clientStatusAllList = clientStatusAllList;
	}

	public String getClientStatus()
	{
		return clientStatus;
	}

	public void setClientStatus(String clientStatus)
	{
		this.clientStatus = clientStatus;
	}

	public String getSourceMasterExist()
	{
		return sourceMasterExist;
	}

	public void setSourceMasterExist(String sourceMasterExist)
	{
		this.sourceMasterExist = sourceMasterExist;
	}

	public String getReferedByExist()
	{
		return referedByExist;
	}

	public void setReferedByExist(String referedByExist)
	{
		this.referedByExist = referedByExist;
	}

	public String getRefNameExist()
	{
		return refNameExist;
	}

	public void setRefNameExist(String refNameExist)
	{
		this.refNameExist = refNameExist;
	}

	public String getStarRatingExist()
	{
		return starRatingExist;
	}

	public void setStarRatingExist(String starRatingExist)
	{
		this.starRatingExist = starRatingExist;
	}

	public String getStatusExist()
	{
		return statusExist;
	}

	public void setStatusExist(String statusExist)
	{
		this.statusExist = statusExist;
	}

	public String getLocationExist()
	{
		return locationExist;
	}

	public void setLocationExist(String locationExist)
	{
		this.locationExist = locationExist;
	}

	public String getAcManagerExist()
	{
		return acManagerExist;
	}

	public void setAcManagerExist(String acManagerExist)
	{
		this.acManagerExist = acManagerExist;
	}

	public String getTargetAchievedExist()
	{
		return targetAchievedExist;
	}

	public void setTargetAchievedExist(String targetAchievedExist)
	{
		this.targetAchievedExist = targetAchievedExist;
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

	public List<String> getCList()
	{
		return cList;
	}

	public void setCList(List<String> list)
	{
		cList = list;
	}

	public LinkedHashMap<String, String> getOne1()
	{
		return one1;
	}

	public void setOne1(LinkedHashMap<String, String> one1)
	{
		this.one1 = one1;
	}

	public void setSourceMasterMap(LinkedHashMap<String, String> sourceMasterMap)
	{
		this.sourceMasterMap = sourceMasterMap;
	}

	public void setEmployee_basicMap(LinkedHashMap<String, String> employee_basicMap)
	{
		this.employee_basicMap = employee_basicMap;
	}

	public void setClient_statusMap(LinkedHashMap<String, String> client_statusMap)
	{
		this.client_statusMap = client_statusMap;
	}

	public void setClientDateControls(LinkedHashMap<String, String> clientDateControls)
	{
		this.clientDateControls = clientDateControls;
	}

	public void setVerticalMap(LinkedHashMap<String, String> verticalMap)
	{
		this.verticalMap = verticalMap;
	}

	public void setOfferingMap(LinkedHashMap<String, Object> offeringMap)
	{
		this.offeringMap = offeringMap;
	}

	public void setPClientMap(LinkedHashMap<String, String> clientMap)
	{
		pClientMap = clientMap;
	}

	public void setBasicParams(LinkedHashMap<String, String> basicParams)
	{
		this.basicParams = basicParams;
	}

	public void setDataList(LinkedHashMap<Integer, List<String>> dataList)
	{
		this.dataList = dataList;
	}

	public String getExistAndLostClient()
	{
		return ExistAndLostClient;
	}

	public void setExistAndLostClient(String existAndLostClient)
	{
		ExistAndLostClient = existAndLostClient;
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

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}

	public String getMomType()
	{
		return momType;
	}

	public void setMomType(String momType)
	{
		this.momType = momType;
	}

	public Map<String, String> getMomFullViewMap()
	{
		return momFullViewMap;
	}

	public void setMomFullViewMap(Map<String, String> momFullViewMap)
	{
		this.momFullViewMap = momFullViewMap;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public HashMap<String, HashMap<String, String>> getOfferingWeightageDetails()
	{
		return offeringWeightageDetails;
	}

	public void setOfferingWeightageDetails(HashMap<String, HashMap<String, String>> offeringWeightageDetails)
	{
		this.offeringWeightageDetails = offeringWeightageDetails;
	}

	public boolean setDepartmentExist(boolean departmentExist)
	{
		this.departmentExist = departmentExist;
		return departmentExist;
	}

	public boolean isDepartmentExist()
	{
		return departmentExist;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getDepartment()
	{
		return department;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public void setPo_attachFileName(String po_attachFileName)
	{
		this.po_attachFileName = po_attachFileName;
	}

	public String getPo_attachFileName()
	{
		return po_attachFileName;
	}

	public void setPo_attach(File po_attach)
	{
		this.po_attach = po_attach;
	}

	public File getPo_attach()
	{
		return po_attach;
	}

	public void setAmmount(String ammount)
	{
		this.ammount = ammount;
	}

	public String getAmmount()
	{
		return ammount;
	}

	public void setPoNumber(String poNumber)
	{
		this.poNumber = poNumber;
	}

	public String getPoNumber()
	{
		return poNumber;
	}

	public void setPo_date(String po_date)
	{
		this.po_date = po_date;
	}

	public String getPo_date()
	{
		return po_date;
	}

	public void setPo_attachContentType(String po_attachContentType)
	{
		this.po_attachContentType = po_attachContentType;
	}

	public String getPo_attachContentType()
	{
		return po_attachContentType;
	}

	public void setIndustryMap(Map<String, String> industryMap)
	{
		this.industryMap = industryMap;
	}

	public Map<String, String> getIndustryMap()
	{
		return industryMap;
	}

	public void setSubIndustryMap(Map<String, String> subIndustryMap)
	{
		this.subIndustryMap = subIndustryMap;
	}

	public Map<String, String> getSubIndustryMap()
	{
		return subIndustryMap;
	}

	public void setLocation_master_dataMap(Map<String, String> location_master_dataMap)
	{
		this.location_master_dataMap = location_master_dataMap;
	}

	public void setEmployee_basicMap(Map<String, String> employee_basicMap)
	{
		this.employee_basicMap = employee_basicMap;
	}

	public void setSourceMasterMap(Map<String, String> sourceMasterMap)
	{
		this.sourceMasterMap = sourceMasterMap;
	}

	public void setClientContactEditList(ArrayList<ArrayList<GridDataPropertyView>> clientContactEditList)
	{
		this.clientContactEditList = clientContactEditList;
	}

	public ArrayList<ArrayList<GridDataPropertyView>> getClientContactEditList()
	{
		return clientContactEditList;
	}

	public void setDeptMap(Map<String, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<String, String> getDeptMap()
	{
		return deptMap;
	}

	public void setOfferingList(LinkedHashMap<String, String> offeringList)
	{
		this.offeringList = offeringList;
	}

	public LinkedHashMap<String, String> getOfferingList()
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

	public void setClientStatusList(Map<String, String> clientStatusList)
	{
		this.clientStatusList = clientStatusList;
	}

	public Map<String, String> getClientStatusList()
	{
		return clientStatusList;
	}

	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	public String getContactId()
	{
		return contactId;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDate()
	{
		return date;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setShowMomDetails(boolean showMomDetails)
	{
		this.showMomDetails = showMomDetails;
	}

	public boolean isShowMomDetails()
	{
		return showMomDetails;
	}

	public void setCurrDate(String currDate)
	{
		this.currDate = currDate;
	}

	public String getCurrDate()
	{
		return currDate;
	}

	public List<GridDataPropertyView> getMasterClientViewProp()
	{
		return masterClientViewProp;
	}

	public void setMasterClientViewProp(List<GridDataPropertyView> masterClientViewProp)
	{
		this.masterClientViewProp = masterClientViewProp;
	}

	public Map<String, String> getClientstatuslist()
	{
		return clientstatuslist;
	}

	public void setClientstatuslist(Map<String, String> clientstatuslist)
	{
		this.clientstatuslist = clientstatuslist;
	}

	public String getClientstatus()
	{
		return clientstatus;
	}

	public void setClientstatus(String clientstatus)
	{
		this.clientstatus = clientstatus;
	}

	public String getClient_Name()
	{
		return client_Name;
	}

	public void setClient_Name(String client_Name)
	{
		this.client_Name = client_Name;
	}

	public Map<String, String> getClientContactandEmailMap()
	{
		return clientContactandEmailMap;
	}

	public void setClientContactandEmailMap(Map<String, String> clientContactandEmailMap)
	{
		this.clientContactandEmailMap = clientContactandEmailMap;
	}

	public String getForecast_category()
	{
		return forecast_category;
	}

	public void setForecast_category(String forecast_category)
	{
		this.forecast_category = forecast_category;
	}

	public String getForecastcategoryExist()
	{
		return forecastcategoryExist;
	}

	public void setForecastcategoryExist(String forecastcategoryExist)
	{
		this.forecastcategoryExist = forecastcategoryExist;
	}

	public String getClosuredateExist()
	{
		return closuredateExist;
	}

	public void setClosuredateExist(String closuredateExist)
	{
		this.closuredateExist = closuredateExist;
	}

	public String getClosure_date()
	{
		return closure_date;
	}

	public void setClosure_date(String closure_date)
	{
		this.closure_date = closure_date;
	}

	/*
	 * public String getOpportunity_name() { return opportunity_name; } public
	 * void setOpportunity_name(String opportunity_name) { this.opportunity_name
	 * = opportunity_name; }
	 */
	public String getSales_stages()
	{
		return sales_stages;
	}

	public void setSales_stages(String sales_stages)
	{
		this.sales_stages = sales_stages;
	}

	public String getSalesstagesExist()
	{
		return salesstagesExist;
	}

	public void setSalesstagesExist(String salesstagesExist)
	{
		this.salesstagesExist = salesstagesExist;
	}

	public String getCompelling_reason()
	{
		return compelling_reason;
	}

	public void setCompelling_reason(String compelling_reason)
	{
		this.compelling_reason = compelling_reason;
	}

	public String getSalesstages()
	{
		return salesstages;
	}

	public void setSalesstages(String salesstages)
	{
		this.salesstages = salesstages;
	}

	public String getForecastCategary()
	{
		return forecastCategary;
	}

	public void setForecastCategary(String forecastCategary)
	{
		this.forecastCategary = forecastCategary;
	}

	public String getFromDateSearch()
	{
		return fromDateSearch;
	}

	public void setFromDateSearch(String fromDateSearch)
	{
		this.fromDateSearch = fromDateSearch;
	}

	public Map<String, Object> getUserdata()
	{
		return userdata;
	}

	public void setUserdata(Map<String, Object> userdata)
	{
		this.userdata = userdata;
	}

	public Integer getTotalRecord()
	{
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord)
	{
		this.totalRecord = totalRecord;
	}

	public Map<String, String> getWeightageList()
	{
		return weightageList;
	}

	public void setWeightageList(Map<String, String> weightageList)
	{
		this.weightageList = weightageList;
	}

	public String getWeightageMasterExist()
	{
		return weightageMasterExist;
	}

	public void setWeightageMasterExist(String weightageMasterExist)
	{
		this.weightageMasterExist = weightageMasterExist;
	}

	public String getWeightageMaster()
	{
		return weightageMaster;
	}

	public void setWeightageMaster(String weightageMaster)
	{
		this.weightageMaster = weightageMaster;
	}

	public Map<String, String> getWeightageMasterMap()
	{
		return weightageMasterMap;
	}

	public void setWeightageMasterMap(Map<String, String> weightageMasterMap)
	{
		this.weightageMasterMap = weightageMasterMap;
	}

	public Map<String, String> getWeightageMap()
	{
		return weightageMap;
	}

	public void setWeightageMap(Map<String, String> weightageMap)
	{
		this.weightageMap = weightageMap;
	}

	public Map<String, String> getOfferingNameMap()
	{
		return offeringNameMap;
	}

	public void setOfferingNameMap(Map<String, String> offeringNameMap)
	{
		this.offeringNameMap = offeringNameMap;
	}

	public Map<String, String> getForecastcategoryMap()
	{
		return forecastcategoryMap;
	}

	public void setForecastcategoryMap(Map<String, String> forecastcategoryMap)
	{
		this.forecastcategoryMap = forecastcategoryMap;
	}

	public Map<String, String> getSalesStageMap()
	{
		return salesStageMap;
	}

	public void setSalesStageMap(Map<String, String> salesStageMap)
	{
		this.salesStageMap = salesStageMap;
	}

	public String getClientNameWithStar()
	{
		return clientNameWithStar;
	}

	public void setClientNameWithStar(String clientNameWithStar)
	{
		this.clientNameWithStar = clientNameWithStar;
	}

	public List<String> getNumberOfStar()
	{
		return numberOfStar;
	}

	public void setNumberOfStar(List<String> numberOfStar)
	{
		this.numberOfStar = numberOfStar;
	}

	public String getLostId()
	{
		return lostId;
	}

	public void setLostId(String lostId)
	{
		this.lostId = lostId;
	}

	public String getRCA()
	{
		return RCA;
	}

	public void setRCA(String rCA)
	{
		RCA = rCA;
	}

	public String getCAPA()
	{
		return CAPA;
	}

	public void setCAPA(String cAPA)
	{
		CAPA = cAPA;
	}

	public String getVerticalname()
	{
		return verticalname;
	}

	public void setVerticalname(String verticalname)
	{
		this.verticalname = verticalname;
	}

	public String getOfferingname()
	{
		return offeringname;
	}

	public void setOfferingname(String offeringname)
	{
		this.offeringname = offeringname;
	}

	public String getSubofferingname()
	{
		return subofferingname;
	}

	public void setSubofferingname(String subofferingname)
	{
		this.subofferingname = subofferingname;
	}

	public String getReturntype()
	{
		return returntype;
	}

	public void setReturntype(String returntype)
	{
		this.returntype = returntype;
	}

	public Map<String, String> getLostStatusMAP()
	{
		return lostStatusMAP;
	}

	public void setLostStatusMAP(Map<String, String> lostStatusMAP)
	{
		this.lostStatusMAP = lostStatusMAP;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public String getEmailtofilter()
	{
		return emailtofilter;
	}

	public void setEmailtofilter(String emailtofilter)
	{
		this.emailtofilter = emailtofilter;
	}

	public String getEmailtofiltertwo()
	{
		return emailtofiltertwo;
	}

	public void setEmailtofiltertwo(String emailtofiltertwo)
	{
		this.emailtofiltertwo = emailtofiltertwo;
	}

	public Map<String, String> getTemplateNameList()
	{
		return templateNameList;
	}

	public void setTemplateNameList(Map<String, String> templateNameList)
	{
		this.templateNameList = templateNameList;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessageValue()
	{
		return messageValue;
	}

	public void setMessageValue(String messageValue)
	{
		this.messageValue = messageValue;
	}

	public List<ConfigurationUtilBean> getFrequency()
	{
		return frequency;
	}

	public void setFrequency(List<ConfigurationUtilBean> frequency)
	{
		this.frequency = frequency;
	}

	public String getOtherlostreason()
	{
		return otherlostreason;
	}

	public void setOtherlostreason(String otherlostreason)
	{
		this.otherlostreason = otherlostreason;
	}

	public String getOpportunity_value()
	{
		return opportunity_value;
	}

	public void setOpportunity_value(String opportunity_value)
	{
		this.opportunity_value = opportunity_value;
	}

	public String getOpportunity_name()
	{
		return opportunity_name;
	}

	public void setOpportunity_name(String opportunity_name)
	{
		this.opportunity_name = opportunity_name;
	}

	public List<GridDataPropertyView> getOpportunityViewProp()
	{
		return opportunityViewProp;
	}

	public void setOpportunityViewProp(List<GridDataPropertyView> opportunityViewProp)
	{
		this.opportunityViewProp = opportunityViewProp;
	}

	public List<Object> getOpportunityViewList()
	{
		return opportunityViewList;
	}

	public void setOpportunityViewList(List<Object> opportunityViewList)
	{
		this.opportunityViewList = opportunityViewList;
	}

	public Map<String, String> getOfferingforOpportunityMap()
	{
		return offeringforOpportunityMap;
	}

	public void setOfferingforOpportunityMap(Map<String, String> offeringforOpportunityMap)
	{
		this.offeringforOpportunityMap = offeringforOpportunityMap;
	}

	public String getOfferingOpportunity()
	{
		return offeringOpportunity;
	}

	public void setOfferingOpportunity(String offeringOpportunity)
	{
		this.offeringOpportunity = offeringOpportunity;
	}

	public Map<String, String> getOpportunityNameMap()
	{
		return opportunityNameMap;
	}

	public void setOpportunityNameMap(Map<String, String> opportunityNameMap)
	{
		this.opportunityNameMap = opportunityNameMap;
	}

	public String getTimestatus()
	{
		return timestatus;
	}

	public void setTimestatus(String timestatus)
	{
		this.timestatus = timestatus;
	}

	public String getScheduletime()
	{
		return scheduletime;
	}

	public void setScheduletime(String scheduletime)
	{
		this.scheduletime = scheduletime;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;

	}

	public void setOpportunityType(String opportunityType)
	{
		this.opportunityType = opportunityType;
	}

	public String getOpportunityType()
	{
		return opportunityType;
	}

	public String getExpectency()
	{
		return expectency;
	}

	public void setExpectency(String expectency)
	{
		this.expectency = expectency;
	}

	public Map<String, String> getSalesStagesMap()
	{
		return salesStagesMap;
	}

	public void setSalesStagesMap(Map<String, String> salesStagesMap)
	{
		this.salesStagesMap = salesStagesMap;
	}

	public String getSalesstageName()
	{
		return salesstageName;
	}

	public void setSalesstageName(String salesstageName)
	{
		this.salesstageName = salesstageName;
	}

	public int getCounter()
	{
		return counter;
	}

	public void setCounter(int counter)
	{
		this.counter = counter;
	}

	public String getIndr()
	{
		return indr;
	}

	public void setIndr(String indr)
	{
		this.indr = indr;
	}

	public String getSubindr()
	{
		return subindr;
	}

	public void setSubindr(String subindr)
	{
		this.subindr = subindr;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public String getSourceN()
	{
		return sourceN;
	}

	public void setSourceN(String sourceN)
	{
		this.sourceN = sourceN;
	}

	public String getLocatn()
	{
		return locatn;
	}

	public void setLocatn(String locatn)
	{
		this.locatn = locatn;
	}

	public String getClintSts()
	{
		return clintSts;
	}

	public void setClintSts(String clintSts)
	{
		this.clintSts = clintSts;
	}

	public String getClintName()
	{
		return clintName;
	}

	public void setClintName(String clintName)
	{
		this.clintName = clintName;
	}

	public String getAcmng()
	{
		return acmng;
	}

	public void setAcmng(String acmng)
	{
		this.acmng = acmng;
	}

	public String getSalesstag()
	{
		return salesstag;
	}

	public void setSalesstag(String salesstag)
	{
		this.salesstag = salesstag;
	}

	public String getForcstcat()
	{
		return forcstcat;
	}

	public void setForcstcat(String forcstcat)
	{
		this.forcstcat = forcstcat;
	}

	public String getFrmdateSear()
	{
		return frmdateSear;
	}

	public void setFrmdateSear(String frmdateSear)
	{
		this.frmdateSear = frmdateSear;
	}

	public InputStream getInputstream()
	{
		return inputstream;
	}

	public void setInputstream(InputStream inputstream)
	{
		this.inputstream = inputstream;
	}

	public String getClient()
	{
		return client;
	}

	public void setClient(String client)
	{
		this.client = client;
	}

	public Map<String, String> getCheckEmp()
	{
		return checkEmp;
	}

	public void setCheckEmp(Map<String, String> checkEmp)
	{
		this.checkEmp = checkEmp;
	}

	public Long getTotalopp()
	{
		return totalopp;
	}

	public void setTotalopp(Long totalopp)
	{
		this.totalopp = totalopp;
	}

	public Long getGrandtotal()
	{
		return grandtotal;
	}

	public void setGrandtotal(Long grandtotal)
	{
		this.grandtotal = grandtotal;
	}

	public Long getGrandtotalsum()
	{
		return grandtotalsum;
	}

	public void setGrandtotalsum(Long grandtotalsum)
	{
		this.grandtotalsum = grandtotalsum;
	}

	public void setTo_maxDateTime(String to_maxDateTime)
	{
		this.to_maxDateTime = to_maxDateTime;
	}

	public String getTo_maxDateTime()
	{
		return to_maxDateTime;
	}

	public List<String> getContactPersonList()
	{
		return contactPersonList;
	}

	public void setContactPersonList(List<String> contactPersonList)
	{
		this.contactPersonList = contactPersonList;
	}

	public Map<String, String> getExistingFreeContact()
	{
		return existingFreeContact;
	}

	public void setExistingFreeContact(Map<String, String> existingFreeContact)
	{
		this.existingFreeContact = existingFreeContact;
	}

	public String getExistingfreeId()
	{
		return existingfreeId;
	}

	public void setExistingfreeId(String existingfreeId)
	{
		this.existingfreeId = existingfreeId;
	}

	public HashMap<String, HashMap<String, String>> getOfferingWeightageDetailsVal()
	{
		return offeringWeightageDetailsVal;
	}

	public void setOfferingWeightageDetailsVal(HashMap<String, HashMap<String, String>> offeringWeightageDetailsVal)
	{
		this.offeringWeightageDetailsVal = offeringWeightageDetailsVal;
	}

	public HashMap<String, HashMap<String, String>> getOfferingWeightageDetailsZeroval()
	{
		return offeringWeightageDetailsZeroval;
	}

	public void setOfferingWeightageDetailsZeroval(HashMap<String, HashMap<String, String>> offeringWeightageDetailsZeroval)
	{
		this.offeringWeightageDetailsZeroval = offeringWeightageDetailsZeroval;
	}

	public void setSearchParameter(String searchParameter)
	{
		this.searchParameter = searchParameter;
	}

	public String getSearchParameter()
	{
		return searchParameter;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getTime()
	{
		return time;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getUid()
	{
		return uid;
	}

}