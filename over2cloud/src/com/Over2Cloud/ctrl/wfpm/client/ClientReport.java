package com.Over2Cloud.ctrl.wfpm.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.achievement.AchievementHelper;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.dashboard.ActivityType;
import com.Over2Cloud.ctrl.wfpm.dashboard.DashboardTakeActionHelper;
import com.Over2Cloud.ctrl.wfpm.dashboard.EntityTakeActionIsFinishedType;
import com.Over2Cloud.ctrl.wfpm.template.SMSTemplateBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class ClientReport extends ActionSupport implements
	ServletRequestAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -100000L;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	
	private String empId = session.get("empIdofuser").toString().split("-")[1];
	
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
	.get("connectionSpace");
	String cId = new CommonHelper().getEmpDetailsByUserName(
	CommonHelper.moduleName, userName, connectionSpace)[0];
	static final Log log = LogFactory.getLog(ClientCtrlAction.class);
	private HttpServletRequest request;
	
	private String id;
	private String clientName;
	private String offeringId;
	private String contactName;
	private String organization;
	private String lastActivity;
	private String offeringname;
	private String opportunityType;
	private Map<String, String> lostStatusMAP;
	private String verticalname;
	private String subofferingname;
	private String clientname;
	private String starRating;
	private String address;
	private String location;
	private String acManager;
	private String industry;
	private String subIndustry;
	private String forecastCategory;
	private String salesStages;
	
	//fields for acc Manager data
	private  String groupname;
	private  String deptname;
	private  String empname;
	private  String mobileno;
	private  String email;
	private  String dob;
	private  String doa;
	
	private String firstExpVal;
	private String firstExpDate;
	
	private String lostId;
	private String RCA;
	private String CAPA;
	private String otherlostreason;
	private String opportunityActionType;
	
	// fields for closing client.
	private String comments;
	private String po_attachFileName;
	private String po_attachContentType;
	private File po_attach;
	private String ammount;
	private String poNumber;
	private String po_date;
	private String offeringLevelId;
	
	private String opportunityValue;
	private String closureDate;
	private String opportunity_value;
	private String closure_date;
	private String opportunity_name;
	private String takeActionId;
	
	private String maxDateTime;
	private String status;
	private String clientContactId;
	
	private Map<String, String> clientStatusList = null;
	Map<String, String> allsalesstages;
	private List<HistoryClientOfferingBean> historyGridModelList;
	
	private Map<String, String> forecastcategoryMap;
	private Map<String, String> salesStageMap;
	private String presalesStage;
	private String preforecastcategory;
	private String forecast_category;
	private String sales_stages;
	List<ClientSupportBean> referancedatalist;
	int numberOfStar;
	
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
	
	public String execute()
	{
	try {
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	
	if(cNameOffId != null)
	{
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	offeringname = new ClientHelper3().fetchOfferingName(offeringId);
	organization = new ClientHelper3().fetchClientName(clientName);
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewClientDetails()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	List clientdata = new ClientHelper3().fetchClientDetails(clientName);
	if(clientdata!=null && clientdata.size()>0)
	{
	for(Iterator it=clientdata.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 //offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
	 if(obdata[0] == null){obdata[0]="NA";}
	 if(obdata[1] == null){obdata[1]="NA";}
	 if(obdata[2] == null){obdata[2]="NA";}
	 if(obdata[3] == null){obdata[3]="NA";}
	 if(obdata[4] == null){obdata[4]="NA";}
	 if(obdata[5] == null){obdata[5]="NA";}
	 if(obdata[6] == null){obdata[6]="NA";}
	 if(obdata[7] == null){obdata[7]="NA";}
	 clientname = obdata[0].toString();
	 starRating = obdata[1].toString();
	 address = obdata[2].toString();
	 location = obdata[3].toString();
	 acManager = obdata[4].toString();
	 industry = obdata[5].toString();
	 subIndustry = obdata[6].toString();
	 contactName = obdata[9].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	public String viewOfferingDetails()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	//clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	String offeringtree = new ClientHelper3().fetchOfferingTree(offeringId);
	verticalname = offeringtree.split("#")[0];
	offeringname = offeringtree.split("#")[1];
	subofferingname = offeringtree.split("#")[2];
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewAccMgrDetails()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	List acManagerData=null;
	if(id!=null && id!="")
	{
	    String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
                acManagerData = new ClientHelper3().fetchAcManagerDetails(clientName,offeringId);
	}
	else{	
	System.out.println(this.getClientName());
	System.out.println(this.getOfferingId());
	    acManagerData = new ClientHelper3().fetchAcManagerDetails(clientName,offeringId);
	}
	if(acManagerData!=null && acManagerData.size()>0)
	{
	for(Iterator it=acManagerData.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 //offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
	 if(obdata[0] == null){obdata[0]="NA";}
	 if(obdata[1] == null){obdata[1]="NA";}
	 if(obdata[2] == null){obdata[2]="NA";}
	 if(obdata[3] == null){obdata[3]="NA";}
	 if(obdata[4] == null){obdata[4]="NA";}
	 if(obdata[5] == null){obdata[5]="NA";}
	 if(obdata[6] == null){obdata[6]="NA";}
	 if(obdata[7] == null){obdata[7]="NA";}
	 groupname = obdata[0].toString();
	 deptname = obdata[1].toString();
	 empname = obdata[2].toString();
	 mobileno = obdata[3].toString();
	 email = obdata[4].toString();
	 address = obdata[5].toString();
	 dob = obdata[6].toString();
	 doa = obdata[7].toString();
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	public String beforeTakeActionOpportunity()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	offeringname = new ClientHelper3().fetchOfferingName(offeringId);
	contactName = new ClientHelper3().fetchContactName(clientName);
	organization = new ClientHelper3().fetchClientName(clientName);
	lastActivity = new ClientHelper3().fetchLastStatus(clientName, offeringId);
	takeActionId = new ClientHelper3().fetchTakeActionId(clientName, offeringId);
	
	forecastCategory = cNameOffId.split("#")[3];
	salesStages = cNameOffId.split("#")[4];
	List clientdata = new ClientHelper3().fetchClientDetails(clientName);
	//List salesstageForcast = new ClientHelper3().fetch
	if(clientdata!=null && clientdata.size()>0)
	{
	for(Iterator it=clientdata.iterator(); it.hasNext();)
	{
	 Object[] obdata=(Object[])it.next();
	 //offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
	 if(obdata[0] == null){obdata[0]="NA";}
	 if(obdata[1] == null){obdata[1]="NA";}
	 if(obdata[2] == null){obdata[2]="NA";}
	 if(obdata[3] == null){obdata[3]="NA";}
	 if(obdata[4] == null){obdata[4]="NA";}
	 if(obdata[5] == null){obdata[5]="NA";}
	 if(obdata[6] == null){obdata[6]="NA";}
	 if(obdata[7] == null){obdata[7]="NA";}
	 if(obdata[8] == null){obdata[8]="NA";}
	 clientname = obdata[0].toString();
	 starRating = obdata[1].toString();
	 address = obdata[2].toString();
	 location = obdata[3].toString();
	 acManager = obdata[4].toString();
	 industry = obdata[5].toString();
	 subIndustry = obdata[6].toString();
	 //forecastCategory = obdata[7].toString();
	// salesStages = obdata[8].toString();
	 
	}
	}
	//System.out.println("contactName>"+contactName+"   organization>"+organization+"  lastActivity>>"+lastActivity);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewHistoryRecord()
	{
	HistoryClientOfferingBean historyBean = null;
	historyGridModelList = new ArrayList<HistoryClientOfferingBean>();
	if (!ValidateSession.checkSession()) return LOGIN;
	try {
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	
	if(cNameOffId != null)
	{
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	offeringname = new ClientHelper3().fetchOfferingName(offeringId);
	organization = new ClientHelper3().fetchClientName(clientName);
	}
	
	List template = new ClientHelper3().fetchActionHistoryData(clientName, offeringId);
	if(template != null && template.size()>0)
	{
	for (Iterator iterator = template.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	
	historyBean = new HistoryClientOfferingBean();
	if(object[0] == null){object[0] = Integer.parseInt("0");}
	if(object[1] == null){object[1] = "NA";}
	if(object[2] == null){object[2] = "NA";}
	if(object[3] == null){object[3] = "NA";}
	if(object[4] == null){object[4] = "NA";}
	if(object[5] == null){object[5] = "NA";}
	
	
	historyBean.setId(Integer.parseInt(object[0].toString()));
	historyBean.setActivity(object[1].toString());
	//historyBean.setDate(object[2].toString());
	String string = object[2].toString();
	String[] datetime = string.split(" ");
	String date = datetime[0]; 
	String time = datetime[1];
	String dateTime = new StringBuilder().append(DateUtil.convertDateToIndianFormat(date)).append(" ").append(time).toString();
	historyBean.setDate(dateTime);
	historyBean.setContactperson(object[3].toString());
	historyBean.setAccountmanager(object[4].toString());
	historyBean.setComment(object[5].toString());
	historyGridModelList.add(historyBean);
	}
	setHistoryGridModelList(historyGridModelList);
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewCurrentStagesRecord()
	{
	try
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	allsalesstages = new LinkedHashMap<String, String>();
	allsalesstages = new ClientHelper3().fetchAllSalesStagesOnId(clientName,offeringId);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewExpectedOppValRecord()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	
	String expValDetails = new ClientHelper3().fetchExpectedValue(clientName, offeringId);
	firstExpVal = expValDetails.split("#")[0];
	firstExpDate = DateUtil.convertDateToIndianFormat(expValDetails.split("#")[1]);
	
	//get from edit history on opportunity_details table id basis.
	//String previousExpValHistory = new ClientHelper3().previousExpValHistory(id);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	public String beforeTakeActiononOpportunity()
	{
	String responce = "ERROR";
	if (!ValidateSession.checkSession()) return LOGIN;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	try
	{
	if(opportunityType.equalsIgnoreCase("Close"))
	{
	responce = "CloseOpp";
	}
	else if(opportunityType.equalsIgnoreCase("Edit"))
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	String oppdetails = new ClientHelper3().fetchOpportunityValue(clientName, offeringId);
	opportunityValue = oppdetails.split("#")[1];
	closureDate = DateUtil.convertDateToIndianFormat(oppdetails.split("#")[2]);
	presalesStage = oppdetails.split("#")[3];
	preforecastcategory = oppdetails.split("#")[4];
	salesStageMap = new LinkedHashMap<String, String>();
	salesStageMap = new ClientHelper3().fetchsalesStage();
	forecastcategoryMap = new LinkedHashMap<String, String>();
	forecastcategoryMap = new ClientHelper3().fetchForcastcategory();
	responce = "EditOpp";
	}
	else if(opportunityType.equalsIgnoreCase("Lost"))
	{
	ClientHelper ch = new ClientHelper();
	// Build lost status map
	setLostStatusMAP(ch.fetchLostStatus(connectionSpace));
	responce = "LostOpp";
	}
	else if(opportunityType.equalsIgnoreCase("Next Activity"))
	{
	StringBuffer query1 = new StringBuffer();
	query1.append("select id,statusName from client_status order by statusName");

	List data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
	// Build client status map
	setClientStatusList(CommonHelper.convertListToMap(data, false));
	
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	clientContactId = new ClientHelper3().fetchContactId(clientName,offeringId);
	//System.out.println("clientContactId  "+clientContactId);
	responce = "NextActivity";
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return responce;
	}
	
	public String takeActionOnOpportunity ()
	{
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	offeringId = cNameOffId.split("#")[1];
	offeringLevelId = cNameOffId.split("#")[2];
	DashboardTakeActionHelper dtah = new DashboardTakeActionHelper();
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	if(opportunityActionType.equalsIgnoreCase("Lost"))
	{
	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	wherClause.put("isConverted", 2);
	wherClause.put("convertDate", DateUtil.getCurrentDateUSFormat());
	wherClause.put("convertTime", DateUtil.getCurrentTime());
	wherClause.put("lostId", lostId);
	wherClause.put("RCA", RCA);
	wherClause.put("CAPA", CAPA);
	wherClause.put("otherlostreason", otherlostreason);
	
	condtnBlock.put("clientName", clientName);
	condtnBlock.put("offeringId", offeringId);
	// for getting client and offering id from opportunity_details
	
	boolean b = cbt.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, connectionSpace);
	
	StringBuilder query = new StringBuilder("");
	query.append("update opportunity_details set lost_offering_flag = '1', lost_reason = '" + lostId + "', convertDate = '" + DateUtil.getCurrentDateUSFormat() + "'");
	query.append(" where id = '");
	query.append(getId());
	query.append("'");

	System.out.println(" query>"+query.toString());
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
	ob.setDataName(clientName);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("offering");
	//StringBuilder query1 = new StringBuilder("");
	//query1.append("Select offeringId from client_offering_mapping where clientName='" + clientName + "'");
	// offeringId = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
	ob.setDataName(offeringId);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("offeringLevel");
	//StringBuilder query2 = new StringBuilder("");
	//query2.append("Select offeringLevelId from client_offering_mapping where clientName='" + clientName + "'");
	//List offeringLevelId1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
	ob.setDataName(offeringLevelId);
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
	}
	else
	{
	return ERROR;
	}
	}
	else if(opportunityActionType.equalsIgnoreCase("Close"))
	{
	try
	{
	if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
	if (theFile.exists()) theFile.renameTo(newFileName);
	}
	catch (IOException e)
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

	condtnBlock.put("ClientName", clientName);
	condtnBlock.put("offeringId", offeringId);
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
	ob.setDataName(clientName);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("offering");
	ob.setDataName(offeringId);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("offeringLevel");
	ob.setDataName(offeringLevelId);
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
	
	StringBuilder query = new StringBuilder("");
	query.append("select id from client_offering_mapping where clientName = '"+clientName+"' and offeringId ='"+offeringId+"'");
	List cltmapoffID = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
	AH.addAchievementForOffering(empId, offeringId, getAmmount(), DateUtil.getCurrentDateUSFormat(), offeringLevelId, userName, cltmapoffID.get(0).toString());

	addActionMessage("Data Added Successfully.");
	return SUCCESS;
	}
	else
	{
	return ERROR;
	}
	}
	catch (Exception e)
	{
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: takeActionOnOpportunity of class " + getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	
	}
	else if(opportunityActionType.equalsIgnoreCase("Edit"))
	{
	String oppDetailId = null;
	try
	{
	if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();
	Map<String, Object> wherClause2 = new HashMap<String, Object>();
	Map<String, Object> condtnBlock2 = new HashMap<String, Object>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	wherClause.put("opportunity_value", opportunity_value);
	wherClause.put("sales_stages", sales_stages);
	wherClause.put("forecast_category", forecast_category);
	wherClause.put("closure_date", DateUtil.convertDateToUSFormat(closure_date));
	
	condtnBlock.put("ClientName", clientName);
	condtnBlock.put("offeringId", offeringId);
	boolean b = cbt.updateTableColomn("opportunity_details", wherClause, condtnBlock, connectionSpace);
	if(b)
	{
	oppDetailId = new ClientHelper3().fetchOppDetailsId(clientName,offeringId);
	//System.out.println("oppDetailId  "+oppDetailId);
	}
	
	// update client_offering_mapping.
	wherClause2.put("opportunity_value", opportunity_value);
	wherClause2.put("closure_date", closure_date);
	wherClause2.put("sales_stages", sales_stages);
	wherClause2.put("forecast_category", forecast_category);
	
	condtnBlock2.put("ClientName", clientName);
	condtnBlock2.put("offeringId", offeringId);
	boolean b2 = cbt.updateTableColomn("client_offering_mapping", wherClause, condtnBlock, connectionSpace);
	
	// For Dynamically creating Table opportunity_history
	List<TableColumes> tableColumn = new ArrayList<TableColumes>();
	List<String> columnName = new ArrayList<String>();

	columnName.add("clientName");
	columnName.add("Offering");
	columnName.add("old_opportunity_value");
	columnName.add("old_closure_date");
	columnName.add("old_salesStages_value");
	columnName.add("old_forecast_category");
	columnName.add("edit_date");
	columnName.add("edit_time");
	columnName.add("offeringLevel");
	columnName.add("opportunity_details_id");
	columnName.add("comments");

	for (String cloumn : columnName)
	{
	TableColumes ob1 = new TableColumes();
	ob1.setColumnname(cloumn);
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColumn.add(ob1);
	}

	boolean status = cbt.createTable22("opportunity_history", tableColumn, connectionSpace);
	// for entry in History table

	List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	ob = new InsertDataTable();
	ob.setColName("clientName");
	ob.setDataName(clientName);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("offering");
	ob.setDataName(offeringId);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("old_opportunity_value");
	ob.setDataName(opportunityValue);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("old_salesStages_value");
	ob.setDataName(presalesStage);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("old_forecast_category");
	ob.setDataName(preforecastcategory);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("old_closure_date");
	ob.setDataName(DateUtil.convertDateToUSFormat(closureDate));
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("offeringLevel");
	ob.setDataName(offeringLevelId);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("edit_date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("opportunity_details_id");
	ob.setDataName(oppDetailId);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("comments");
	ob.setDataName(comments);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("edit_time");
	ob.setDataName(DateUtil.getCurrentTimeHourMin());
	insertHistory.add(ob);
	boolean res = cbt.insertIntoTable("opportunity_history", insertHistory, connectionSpace);
	if(res)
	{
	addActionMessage("Data Updated Successfully.");
	}

	}
	catch (Exception e)
	{
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: takeActionOnOpportunity of class " + getClass(), e);
	e.printStackTrace();
	}
	}
	else if(opportunityActionType.equalsIgnoreCase("Next Activity"))
	{
	try
	{
	ActivityType activityType;
	activityType = ActivityType.client;
	if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
	boolean flag = false;
	
	flag = new ClientHelper().createTableClientTakeAction(connectionSpace);

	if (flag)
	{
	// //System.out.println("CloseType.nextActivity");
	// //System.out.println("statusId:" + statusId);
	
	boolean statusFlag = dtah.saveTakeActionData(false, clientName, connectionSpace, activityType, clientContactId, offeringId, status,maxDateTime, comments, cId);
	if (statusFlag)
	{
	boolean status = dtah.closeActivity(connectionSpace, activityType, Integer.parseInt(takeActionId), "Activity Closed", EntityTakeActionIsFinishedType.CLOSE, "id");
	// //System.out.println("status::::::::::::" + status);
	addActionMessage("Activity created successfully.");
	}
	else
	{
	addActionMessage("ERROR: There is some error in data !");
	}
	}
	
	}
	catch (Exception e)
	{
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: takeActionOnOpportunity of class " + getClass(), e);
	e.printStackTrace();
	}
	}
	}
	catch (Exception e)
	{
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: takeActionOnOpportunity of class " + getClass(), e);
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	public String beforeMapOppvalToClient()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try {
	String cNameOffId = new ClientHelper3().fetchOffIdFromOfferingMap(id);
	clientName = getId();
	offeringId = cNameOffId.split("#")[0];
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	
	public String addOpportintyDetails()
	{

	String cNameOffId = new ClientHelper3().fetchOffIdFromOfferingMap(id);
	clientName = getId();
	offeringId = cNameOffId.split("#")[0];
	offeringLevelId = cNameOffId.split("#")[1];
	if (!ValidateSession.checkSession()) return LOGIN;
	try
	{
	String oppDetailId = null;
	try
	{
	if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
	Map<String, Object> wherClause = new HashMap<String, Object>();
	Map<String, Object> condtnBlock = new HashMap<String, Object>();
	Map<String, Object> wherClause2 = new HashMap<String, Object>();
	Map<String, Object> condtnBlock2 = new HashMap<String, Object>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	List<InsertDataTable> insertoppval = new ArrayList<InsertDataTable>();
	InsertDataTable oboppval = null;
	
	oboppval = new InsertDataTable();
	oboppval.setColName("opportunity_name");
	oboppval.setDataName(opportunity_name);
	insertoppval.add(oboppval);
	
	oboppval = new InsertDataTable();
	oboppval.setColName("opportunity_value");
	oboppval.setDataName(opportunity_value);
	insertoppval.add(oboppval);
	
	oboppval = new InsertDataTable();
	oboppval.setColName("closure_date");
	oboppval.setDataName(DateUtil.convertDateToUSFormat(closure_date));
	insertoppval.add(oboppval);
	
	oboppval = new InsertDataTable();
	oboppval.setColName("clientName");
	oboppval.setDataName(clientName);
	insertoppval.add(oboppval);
	
	oboppval = new InsertDataTable();
	oboppval.setColName("offeringId");
	oboppval.setDataName(offeringId);
	insertoppval.add(oboppval);
	
	oboppval = new InsertDataTable();
	oboppval.setColName("offeringLevelId");
	oboppval.setDataName(offeringLevelId);
	insertoppval.add(oboppval);
	
	oboppval = new InsertDataTable();
	oboppval.setColName("lost_offering_flag");
	oboppval.setDataName("0");
	insertoppval.add(oboppval);
	
	// change for clientName Id
	oboppval = new InsertDataTable();
	oboppval.setColName("userName");
	String queryC = "select acManager, id from client_basic_data where id='" + clientName + "'";
	List acManagerList = cbt.executeAllSelectQuery(queryC.toString(), connectionSpace);
	if (acManagerList != null && acManagerList.size() > 0)
	{
	for (Iterator it1 = acManagerList.iterator(); it1.hasNext();)
	{
	Object[] obdata11 = (Object[]) it1.next();
	oboppval.setDataName(obdata11[0].toString());
	insertoppval.add(oboppval);
	}

	}
	
	
	boolean b = cbt.insertIntoTable("opportunity_details", insertoppval, connectionSpace);
	//boolean b = cbt.updateTableColomn("opportunity_details", wherClause, condtnBlock, connectionSpace);
	if(b)
	{
	oppDetailId = new ClientHelper3().fetchOppDetailsId(clientName,offeringId);
	System.out.println("oppDetailId  "+oppDetailId);
	}
	
	// update client_offering_mapping.
	wherClause2.put("opportunity_name", opportunity_name);
	wherClause2.put("opportunity_value", opportunity_value);
	wherClause2.put("closure_date", DateUtil.convertDateToUSFormat(closure_date));
	
	condtnBlock2.put("clientName", clientName);
	condtnBlock2.put("offeringId", offeringId);
	System.out.println("  opportunity_name  "+opportunity_name+" opportunity_value>"+opportunity_value+" closure_date >"+closure_date+" clientName>"+clientName+" offeringId>"+offeringId);
	boolean b2 = cbt.updateTableColomn("client_offering_mapping", wherClause2, condtnBlock2, connectionSpace);
	System.out.println(" 2 b2   c off maping  "+b2);
	// For Dynamically creating Table opportunity_history
	List<TableColumes> tableColumn = new ArrayList<TableColumes>();
	List<String> columnName = new ArrayList<String>();

	columnName.add("clientName");
	columnName.add("Offering");
	columnName.add("old_opportunity_value");
	columnName.add("old_closure_date");
	columnName.add("edit_date");
	columnName.add("edit_time");
	columnName.add("offeringLevel");
	columnName.add("opportunity_details_id");
	columnName.add("comments");

	for (String cloumn : columnName)
	{
	TableColumes ob1 = new TableColumes();
	ob1.setColumnname(cloumn);
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColumn.add(ob1);
	}

	boolean status = cbt.createTable22("opportunity_history", tableColumn, connectionSpace);
	// for entry in History table

	List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	ob = new InsertDataTable();
	ob.setColName("clientName");
	ob.setDataName(clientName);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("offering");
	ob.setDataName(offeringId);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("old_opportunity_value");
	ob.setDataName(opportunityValue);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("old_closure_date");
	ob.setDataName(DateUtil.convertDateToUSFormat(closureDate));
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("offeringLevel");
	ob.setDataName(offeringLevelId);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("edit_date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("opportunity_details_id");
	ob.setDataName(oppDetailId);
	insertHistory.add(ob);

	ob = new InsertDataTable();
	ob.setColName("comments");
	ob.setDataName(comments);
	insertHistory.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("edit_time");
	ob.setDataName(DateUtil.getCurrentTimeHourMin());
	insertHistory.add(ob);
	boolean res = cbt.insertIntoTable("opportunity_history", insertHistory, connectionSpace);
	if(res)
	{
	addActionMessage("Data Updated Successfully.");
	}

	}
	catch (Exception e)
	{
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addOpportunity of class " + getClass(), e);
	e.printStackTrace();
	}
	
	}
	catch (Exception e)
	{
	log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addOpportunity of class " + getClass(), e);
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	
	public String showReferanceData()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try {
	
	referancedatalist = new ClientHelper3().fetckReferanceDetails(id);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String clientStar()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try {
	String cNameOffId = new ClientHelper3().fetchClientAndOffId(id);
	clientName = cNameOffId.split("#")[0];
	clientName = cNameOffId.split("#")[0];
	numberOfStar = new ClientHelper3().fetchStarOfClient(clientName);
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
	this.request = request;
	}
	public void setId(String id)
	{
	this.id = id;
	}
	public String getId()
	{
	return id;
	}
	public void setClientName(String clientName)
	{
	this.clientName = clientName;
	}
	public String getClientName()
	{
	return clientName;
	}
	public void setOfferingId(String offeringId)
	{
	this.offeringId = offeringId;
	}
	public String getOfferingId()
	{
	return offeringId;
	}
	public String getContactName()
	{
	return contactName;
	}
	public void setContactName(String contactName)
	{
	this.contactName = contactName;
	}
	public String getOrganization()
	{
	return organization;
	}
	public void setOrganization(String organization)
	{
	this.organization = organization;
	}
	public String getLastActivity()
	{
	return lastActivity;
	}
	public void setLastActivity(String lastActivity)
	{
	this.lastActivity = lastActivity;
	}
	public void setOfferingname(String offeringname)
	{
	this.offeringname = offeringname;
	}
	public String getOfferingname()
	{
	return offeringname;
	}
	public void setOpportunityType(String opportunityType)
	{
	this.opportunityType = opportunityType;
	}
	public String getOpportunityType()
	{
	return opportunityType;
	}
	public void setLostStatusMAP(Map<String, String> lostStatusMAP)
	{
	this.lostStatusMAP = lostStatusMAP;
	}
	public Map<String, String> getLostStatusMAP()
	{
	return lostStatusMAP;
	}
	public void setClientStatusList(Map<String, String> clientStatusList)
	{
	this.clientStatusList = clientStatusList;
	}
	public Map<String, String> getClientStatusList()
	{
	return clientStatusList;
	}
	public void setVerticalname(String verticalname)
	{
	this.verticalname = verticalname;
	}
	public String getVerticalname()
	{
	return verticalname;
	}
	public void setSubofferingname(String subofferingname)
	{
	this.subofferingname = subofferingname;
	}
	public String getSubofferingname()
	{
	return subofferingname;
	}
	public void setClientname(String clientname)
	{
	this.clientname = clientname;
	}
	public String getClientname()
	{
	return clientname;
	}
	public void setStarRating(String starRating)
	{
	this.starRating = starRating;
	}
	public String getStarRating()
	{
	return starRating;
	}
	public void setAddress(String address)
	{
	this.address = address;
	}
	public String getAddress()
	{
	return address;
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
	public String getIndustry()
	{
	return industry;
	}
	public void setIndustry(String industry)
	{
	this.industry = industry;
	}
	public String getSubIndustry()
	{
	return subIndustry;
	}
	public void setSubIndustry(String subIndustry)
	{
	this.subIndustry = subIndustry;
	}
	public void setGroupname(String groupname)
	{
	this.groupname = groupname;
	}
	public String getGroupname()
	{
	return groupname;
	}
	public void setDeptname(String deptname)
	{
	this.deptname = deptname;
	}
	public String getDeptname()
	{
	return deptname;
	}
	public void setEmpname(String empname)
	{
	this.empname = empname;
	}
	public String getEmpname()
	{
	return empname;
	}
	public void setEmail(String email)
	{
	this.email = email;
	}
	public String getEmail()
	{
	return email;
	}
	public void setMobileno(String mobileno)
	{
	this.mobileno = mobileno;
	}
	public String getMobileno()
	{
	return mobileno;
	}
	public String getDob()
	{
	return dob;
	}
	public void setDob(String dob)
	{
	this.dob = dob;
	}
	public String getDoa()
	{
	return doa;
	}
	public void setDoa(String doa)
	{
	this.doa = doa;
	}
	public String getFirstExpVal()
	{
	return firstExpVal;
	}
	public void setFirstExpVal(String firstExpVal)
	{
	this.firstExpVal = firstExpVal;
	}
	public String getFirstExpDate()
	{
	return firstExpDate;
	}
	public void setFirstExpDate(String firstExpDate)
	{
	this.firstExpDate = firstExpDate;
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
	public void setRCA(String rca)
	{
	RCA = rca;
	}
	public String getCAPA()
	{
	return CAPA;
	}
	public void setCAPA(String capa)
	{
	CAPA = capa;
	}
	public String getOtherlostreason()
	{
	return otherlostreason;
	}
	public void setOtherlostreason(String otherlostreason)
	{
	this.otherlostreason = otherlostreason;
	}
	public String getOpportunityActionType()
	{
	return opportunityActionType;
	}
	public void setOpportunityActionType(String opportunityActionType)
	{
	this.opportunityActionType = opportunityActionType;
	}
	public String getComments()
	{
	return comments;
	}
	public void setComments(String comments)
	{
	this.comments = comments;
	}
	public String getPo_attachFileName()
	{
	return po_attachFileName;
	}
	public void setPo_attachFileName(String po_attachFileName)
	{
	this.po_attachFileName = po_attachFileName;
	}
	public String getPo_attachContentType()
	{
	return po_attachContentType;
	}
	public void setPo_attachContentType(String po_attachContentType)
	{
	this.po_attachContentType = po_attachContentType;
	}
	public File getPo_attach()
	{
	return po_attach;
	}
	public void setPo_attach(File po_attach)
	{
	this.po_attach = po_attach;
	}
	public String getAmmount()
	{
	return ammount;
	}
	public void setAmmount(String ammount)
	{
	this.ammount = ammount;
	}
	public String getPoNumber()
	{
	return poNumber;
	}
	public void setPoNumber(String poNumber)
	{
	this.poNumber = poNumber;
	}
	public String getPo_date()
	{
	return po_date;
	}
	public void setPo_date(String po_date)
	{
	this.po_date = po_date;
	}
	public String getOfferingLevelId()
	{
	return offeringLevelId;
	}
	public void setOfferingLevelId(String offeringLevelId)
	{
	this.offeringLevelId = offeringLevelId;
	}
	public String getOpportunityValue()
	{
	return opportunityValue;
	}
	public void setOpportunityValue(String opportunityValue)
	{
	this.opportunityValue = opportunityValue;
	}
	public String getClosureDate()
	{
	return closureDate;
	}
	public void setClosureDate(String closureDate)
	{
	this.closureDate = closureDate;
	}
	public String getOpportunity_value()
	{
	return opportunity_value;
	}
	public void setOpportunity_value(String opportunity_value)
	{
	this.opportunity_value = opportunity_value;
	}
	public String getClosure_date()
	{
	return closure_date;
	}
	public void setClosure_date(String closure_date)
	{
	this.closure_date = closure_date;
	}
	public String getForecastCategory()
	{
	return forecastCategory;
	}
	public void setForecastCategory(String forecastCategory)
	{
	this.forecastCategory = forecastCategory;
	}
	public String getSalesStages()
	{
	return salesStages;
	}
	public void setSalesStages(String salesStages)
	{
	this.salesStages = salesStages;
	}
	public Map<String, String> getAllsalesstages()
	{
	return allsalesstages;
	}
	public void setAllsalesstages(Map<String, String> allsalesstages)
	{
	this.allsalesstages = allsalesstages;
	}
	public List<HistoryClientOfferingBean> getHistoryGridModelList() {
	return historyGridModelList;
	}
	public void setHistoryGridModelList(
	List<HistoryClientOfferingBean> historyGridModelList) {
	this.historyGridModelList = historyGridModelList;
	}

	public String getOpportunity_name() {
	return opportunity_name;
	}
	public void setOpportunity_name(String opportunity_name) {
	this.opportunity_name = opportunity_name;
	}
	public String getTakeActionId() {
	return takeActionId;
	}
	public void setTakeActionId(String takeActionId) {
	this.takeActionId = takeActionId;
	}

	public String getMaxDateTime() {
	return maxDateTime;
	}

	public void setMaxDateTime(String maxDateTime) {
	this.maxDateTime = maxDateTime;
	}

	public String getStatus() {
	return status;
	}

	public void setStatus(String status) {
	this.status = status;
	}

	public String getClientContactId() {
	return clientContactId;
	}

	public void setClientContactId(String clientContactId) {
	this.clientContactId = clientContactId;
	}

	public void setForecastcategoryMap(Map<String, String> forecastcategoryMap) {
	this.forecastcategoryMap = forecastcategoryMap;
	}

	public Map<String, String> getForecastcategoryMap() {
	return forecastcategoryMap;
	}

	public void setSalesStageMap(Map<String, String> salesStageMap) {
	this.salesStageMap = salesStageMap;
	}

	public Map<String, String> getSalesStageMap() {
	return salesStageMap;
	}

	public String getPresalesStage() {
	return presalesStage;
	}

	public void setPresalesStage(String presalesStage) {
	this.presalesStage = presalesStage;
	}

	public String getPreforecastcategory() {
	return preforecastcategory;
	}

	public void setPreforecastcategory(String preforecastcategory) {
	this.preforecastcategory = preforecastcategory;
	}

	public String getForecast_category() {
	return forecast_category;
	}

	public void setForecast_category(String forecast_category) {
	this.forecast_category = forecast_category;
	}

	public String getSales_stages() {
	return sales_stages;
	}

	public void setSales_stages(String sales_stages) {
	this.sales_stages = sales_stages;
	}

	public List<ClientSupportBean> getReferancedatalist() {
	return referancedatalist;
	}

	public void setReferancedatalist(List<ClientSupportBean> referancedatalist) {
	this.referancedatalist = referancedatalist;
	}

	public int getNumberOfStar() {
	return numberOfStar;
	}

	public void setNumberOfStar(int numberOfStar) {
	this.numberOfStar = numberOfStar;
	}
	
	
	
	}
