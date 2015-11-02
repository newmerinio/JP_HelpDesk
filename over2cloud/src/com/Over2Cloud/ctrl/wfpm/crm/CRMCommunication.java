package com.Over2Cloud.ctrl.wfpm.crm;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.FilesUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Constant;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.ctrl.communication.instantMsg.InstantMessageBean;
import com.Over2Cloud.ctrl.wfpm.associate.AssociateHelper1;
import com.Over2Cloud.ctrl.wfpm.client.ClientCtrlAction;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper2;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper3;
import com.Over2Cloud.ctrl.wfpm.client.EmployeeReturnType;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.common.SessionProviderClass;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper;
import com.Over2Cloud.ctrl.wfpm.lead.LeadHelper2;
import com.Over2Cloud.ctrl.wfpm.masters.CommonMasters;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
public class CRMCommunication extends SessionProviderClass implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(CRMCommunication.class);
	private JSONArray	jsonArrayIndustry;
	private JSONArray	jsonArraySource;
	private JSONArray	jsonArrayAllergic;
	private JSONArray	jsonArrayOffering;
	private JSONArray	jsonArrayLocation;
	private JSONArray	jsonArrayAccMgr;
	private String	relationshipType;
	private EntityType	entityType;
	private String	relationshipSubType;
	private String	viewType,status;
	private String	createType;
	private StringBuilder parameter; 
	private String	industry,ent;
	private String	subIndustry;
	private String	rating;
	private String	source;
	private String	location;
	private String	groupname;
	private Integer	rows	= 0;
	private Integer	page	= 0;
	private String	sord	= "";
	private String	sidx	= "";
	private String	searchField	= "";
	private String	searchString	= "";
	private String	searchOper	= "";
	private Integer	total	= 0;
	private Integer	records	= 0;
	private Integer	activeGroups	= 0;
	private Integer	deactiveGroups	= 0;
	private boolean	loadonce	= false;
	private String	oper;
	private String	id;
	private List<Object>	crmDataList;
	private int	count;
	private JSONArray	jsonArraydepartment;
	private JSONArray	jsonArrayDesignation;
	private String	department;
	private String	designation;
	private String	influence,data;
	private boolean	chkBirthday;
	private String	birthdayFrom;
	private boolean	chkAnniversary;
	private String	anniversaryFrom;
	private List<ConfigurationUtilBean>	mobileTextBox	= null;
	private List<ConfigurationUtilBean>	writemessageTextBox	= null;
	private List<ConfigurationUtilBean>	messageNameDropdown	= null;
	private String	frequencys	= null;
	private List<ConfigurationUtilBean>	frequency	= null;
	private Map<String, String>	messagerootList	= null;
	private Map<String, String>	groupcontactNameListData	= new HashMap<String, String>();
	private Map<String, String>	groupNameListData	= new HashMap<String, String>();
	private Map<String, String>	employeeList;
	private List<GridDataPropertyView> crmgroupViewProp = new ArrayList<GridDataPropertyView>();
	private String	templateType;
	private JSONArray	templateJSONArray;
	private List<ConfigurationUtilBean>	tempList;
	private InstantMessageBean	msgObj;
	private String	message;
	private String	messageName;
	private String	employee;
	private String	mobileNo;
	private String	root;
	private String	templateid;
	private String	writeMessage;
	private String	date;
	private String	hours;
	private String	day;
	private String	entityContacts;
	private Map<String, String>	correctData;
	private boolean	showMultiselect;
	private boolean	chkGroup;
	static private String	autoGroup;
	private String	groupName;
	private String 	comment;
	private List<Object> groupdataViewList;
	private Map<String, String> groupNameList;
	private Map<String, String> templateNameList = new HashMap<String, String>();
	private Map<String, String> organizationnameMap;
	private HttpServletRequest request;
	private String contactId;
	private String groupId;
	private String subject;
	private String from_email;
	private String email_id;
	private String writemail;
	private String check_list,age,sex,allergic_to,blood_group;
	
	List<InstantMessageBean> dataList = null;
	List<InstantMessageBean> vdataList = null;
	List<InstantMessageBean> invdataList = null;
	List<InstantMessageBean> bdataList = null;
	List<InstantMessageBean> dudataList = null;
	List<InstantMessageBean> allDataList = null;
	Set<InstantMessageBean> set = null;
	private Map<String, String>correctMailData;
	
	private boolean isValid;
	
	private Pattern pattern;
	private Matcher matcher;
	private File[] docs = null;
	protected String[] docsFileName;
	private String docfilename;
	
	// for mail image CRM
	
	private File crmImage; 
    private String crmImageFileName; 
    private String crmImageContentType; 
    private String filePath;
    
	private List<Object> mailTagViewList;
	private List<GridDataPropertyView> mailTagViewProp = new ArrayList<GridDataPropertyView>();
	
	private String organization_name;
	private String name;
	private boolean organizationName;
	private boolean checkname;
	private String com_name;
	private String address;
	private String tag_type;
	
	
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public String beforeCRMPages()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
    public String beforeCommActivityPage()
	 {
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	groupNameList = new CRMHelper().getGroupNameList(connectionSpace);
	String query = "select * from group_name where moduleName = 'WFPM'";
	List dataCount = cbt.executeAllSelectQuery(query,connectionSpace);
	records = dataCount.size();
	
	String query2 = "select * from group_name where moduleName = 'WFPM' and active_deactive = 0 ";
	List dataCountactive = cbt.executeAllSelectQuery(query2,connectionSpace);
	activeGroups = dataCountactive.size();
	
	String query3 = "select * from group_name where moduleName = 'WFPM' and active_deactive = 1 ";
	List dataCountDeactive = cbt.executeAllSelectQuery(query3,connectionSpace);
	deactiveGroups = dataCountDeactive.size();
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	 }

    public String beforeCRMMailPage()
    {
    	try {
	if (!ValidateSession.checkSession())
	 return LOGIN;
	setCRMMailGroupViewProp();
	
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: beforeCRMGroupDetailsView of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
    public void setCRMMailGroupViewProp() throws Exception
	{
	try {
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	crmgroupViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("personName");
	gpv.setHeadingName("Person Name");
	gpv.setEditable("false");
	gpv.setWidth(100);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("communicationName");
	gpv.setHeadingName("Communication Name");
	gpv.setWidth(30);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("degreeOfInfulence");
	gpv.setHeadingName("Schedule Time");
	gpv.setWidth(30);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("contactNo");
	gpv.setHeadingName("Contact");
	gpv.setWidth(70);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("emailOfficial");
	gpv.setHeadingName("Email");
	gpv.setWidth(40);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("deptName");
	gpv.setHeadingName("Department");
	gpv.setWidth(40);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);

	gpv = new GridDataPropertyView();
	gpv.setColomnName("designation");
	gpv.setHeadingName("Designation");
	gpv.setWidth(40);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("clientName");
	gpv.setHeadingName("Client");
	gpv.setWidth(40);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("starRating");
	gpv.setHeadingName("Star Rating");
	gpv.setWidth(40);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);
	
	gpv = new GridDataPropertyView();
	gpv.setColomnName("industry");
	gpv.setHeadingName("Industry");
	gpv.setWidth(40);
	gpv.setHideOrShow("false");
	crmgroupViewProp.add(gpv);
	
	
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: beforeCRMGroupDetailsView of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	
	}
    
    
	public String beforeCRMGroupDetailsView()
	{
	try {
	if (!ValidateSession.checkSession())
	 return LOGIN;
	setCRMGroupViewProp();
	
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: beforeCRMGroupDetailsView of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	public void setCRMGroupViewProp() throws Exception
	{
	try {
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	crmgroupViewProp.add(gpv);
	List<String> listDataToShow = new ClientHelper().getClientBasicDataToShow();
	List<GridDataPropertyView> returnResult = Configuration
	.getConfigurationData("mapped_group_name_configuration",
	accountID, connectionSpace, "", 0, "table_name",
	"group_name_configuration");
	
	for (GridDataPropertyView gdp : returnResult) {
	gpv = new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	if(gdp.getColomnName().equalsIgnoreCase("count") || gdp.getColomnName().equalsIgnoreCase("user") )
	{
	gpv.setEditable("false");	
	}
	else{
	gpv.setEditable(gdp.getEditable());
	}
	gpv.setSearch(gdp.getSearch());
	if(gdp.getColomnName().equalsIgnoreCase("parameters"))
	{
	gpv.setHideOrShow("true");
	}
	else
	{
	gpv.setHideOrShow(gdp.getHideOrShow());	
	}
	
	gpv.setAlign(gdp.getAlign());
	gpv.setWidth(gdp.getWidth());
	if (gdp.getFormatter() != null)
	gpv.setFormatter(gdp.getFormatter());
	crmgroupViewProp.add(gpv);
	}
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: beforeCRMGroupDetailsView of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	
	}
	
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewModifyGroupDetails()
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
	else if (Parmname.equalsIgnoreCase("active_deactive"))
	{
	wherClause.put(Parmname, "0");
	}
	else
	{
	wherClause.put(Parmname, paramValue);
	}
	}
	condtnBlock.put("id", getId());
	cbt.updateTableColomn("group_name", wherClause,
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
	query.append("UPDATE group_name SET active_deactive='1' WHERE id IN("
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

	
	public String viewMailGroupDataGrid()
	{
	try {
	if (!ValidateSession.checkSession())
	return LOGIN;
	String querycount = null;
	//String querycountToassign = null;
	List<String> countlist = new LinkedList<String>();
	List<String> fieldNames=new ArrayList<String>();
	fieldNames.add("id");
	fieldNames.add("personName");
	fieldNames.add("communicationName");
	fieldNames.add("degreeOfInfulence");
	fieldNames.add("contactNo");
	fieldNames.add("emailOfficial");
	fieldNames.add("deptName");
	fieldNames.add("designation");
	fieldNames.add("clientName");
	fieldNames.add("starRating");
	fieldNames.add("industry");
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query1 = new StringBuilder("");
	query1.append("SELECT query FROM group_name where id in("+groupId+")");
	//System.out.println(query1.toString());
	List<Object> Listhb = new ArrayList<Object>();
	List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	for(int i=0;i<dataCount.size();i++)
	{
	String query=dataCount.get(i).toString();
	//	System.out.println(query.toString());
	List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data.size()>0)
	{
	/*BigInteger count = new BigInteger("3");
	for (Iterator it = dataCount.iterator(); it.hasNext();) {
	 Object obdata = (Object) it.next();
	 count = (BigInteger) obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();*/
	for (Iterator it = data.iterator(); it.hasNext();) {
	Object[] obdata = (Object[]) it.next();
	Map<String, Object> one = new HashMap<String, Object>();
	
	for (int k = 0; k < fieldNames.size(); k++) {
	
	if (obdata[k] != null) {
	if (k == 0) {
	one.put(fieldNames.get(k).toString(),
	(Integer) obdata[k]);
	}else{
	if (fieldNames.get(k).toString()
	.equalsIgnoreCase("date")) {
	obdata[k] = DateUtil
	.convertDateToIndianFormat(obdata[k]
	.toString());
	}
	//System.out.println("fieldNames.get(k).toString()>"+fieldNames.get(k).toString()+" "+ obdata[k].toString());
	if(fieldNames.get(k).toString().equalsIgnoreCase("query") && obdata[k].toString() != null)
	{
	querycount = new CRMHelper().getTotalDataInQuery(connectionSpace, obdata[k].toString());
	countlist.add(querycount);
	one.put(fieldNames.get(k).toString(),obdata[k].toString());
	one.put("count",querycount);
	}
	if(fieldNames.get(k).toString().equalsIgnoreCase("count") && querycount != null)
	{
	//System.out.println("querycountToassign>"+querycount);
	//one.put(fieldNames.get(k).toString(),querycount);
	}
	if(fieldNames.get(k).toString().equalsIgnoreCase("active_deactive"))
	{
	if(obdata[k].toString().equalsIgnoreCase("0"))
	{
	obdata[k] = "Active";
	}else
	{
	obdata[k] = "Inactive";
	}
	}
	one.put(fieldNames.get(k).toString(),getValueWithNullCheck(obdata[k].toString()));
             	} 
	
	}
	}
	//querycountToassign = querycount;
	Listhb.add(one);
	}
	}
	//System.out.println(Listhb.size());
	setGroupdataViewList(Listhb);
	setTotal((int) Math.ceil((double) getRecords()
	/ (double) getRows()));
	}
	Listhb=null;
	}
	catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: beforeCRMGroupDetailsView of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String getValueWithNullCheck(Object value)
	{
	return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	
	public String viewCRMGroupDataGrid()
	{
	try {
	if (!ValidateSession.checkSession())
	return LOGIN;
	String querycount = null;
	//String querycountToassign = null;
	List<String> countlist = new LinkedList<String>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query1 = new StringBuilder("");
	query1.append("select count(*) from group_name where moduleName = 'WFPM'");
	List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	if(dataCount.size()>0)
	{
	BigInteger count = new BigInteger("3");
	for (Iterator it = dataCount.iterator(); it.hasNext();) {
	 Object obdata = (Object) it.next();
	 count = (BigInteger) obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	// getting list of columns.
	StringBuilder query = new StringBuilder("");
	StringBuilder queryTemp = new StringBuilder("");
	queryTemp.append("select ");
	StringBuilder queryEnd = new StringBuilder("");
	queryEnd.append(" from group_name as grn ");

	List fieldNames = Configuration.getColomnList(
	"mapped_group_name_configuration", accountID,
	connectionSpace, "group_name_configuration");
	List<Object> Listhb = new ArrayList<Object>();
	for (Iterator it = fieldNames.iterator(); it.hasNext();) {

	// generating the dyanamic query based on selected fields
	Object obdata = (Object) it.next();
	if (obdata != null) {
	queryTemp.append("grn." + obdata.toString() + ",");
	    }
	}
	query.append(queryTemp.toString().substring(0,queryTemp.toString().lastIndexOf(",")));
	query.append(" ");
	query.append(queryEnd.toString());
	query.append(" where ");
	query.append(" grn.moduleName ='");
	query.append("WFPM");
	query.append("' ");
	if (groupname != null && !groupname.equalsIgnoreCase("Communication group")) {
	query.append("and grn.name = '");
	query.append(groupname);
	query.append("' ");
	}
	
	if(this.getStatus()!=null && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("undefined"))
	{
	if(this.getStatus().equalsIgnoreCase("Active"))
	{
	query.append(" and grn.active_deactive='0' ");	
	}
	else if(this.getStatus().equalsIgnoreCase("Inactive"))
	{
	query.append(" and grn.active_deactive='1' ");
	}
	else if(this.getStatus().equalsIgnoreCase("Inactive"))
	{
	query.append(" and grn.active_deactive Is Not Null ");
	}
	}
	if (getSearchField() != null && getSearchString() != null
	&& !getSearchField().equalsIgnoreCase("")
	&& !getSearchString().equalsIgnoreCase(""))
	{
	query.append(" and ");
	// add search query based on the search operation
	if (getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" " + getSearchField() + " = '"
	+ getSearchString() + "'");
	}
	else if (getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" " + getSearchField() + " like '%"
	+ getSearchString() + "%'");
	}
	else if (getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" " + getSearchField() + " like '"
	+ getSearchString() + "%'");
	}
	else if (getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" " + getSearchField() + " <> '"
	+ getSearchString() + "'");
	}
	else if (getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" " + getSearchField() + " like '%"
	+ getSearchString() + "'");
	}
	}
	
	query.append(" order by grn.name limit " + from + "," + to);
	//	System.out.println("query>>"+query.toString());
	List data = cbt.executeAllSelectQuery(query.toString(),
	connectionSpace);
	if(data.size()>0)
	{

	for (Iterator it = data.iterator(); it.hasNext();) {
	Object[] obdata = (Object[]) it.next();
	Map<String, Object> one = new HashMap<String, Object>();
	
	for (int k = 0; k < fieldNames.size(); k++) {
	
	if (obdata[k] != null) {
	if (k == 0) {
	one.put(fieldNames.get(k).toString(),
	(Integer) obdata[k]);
	}else{
	if (fieldNames.get(k).toString()
	.equalsIgnoreCase("date")) {
	obdata[k] = DateUtil
	.convertDateToIndianFormat(obdata[k]
	.toString());
	}
	//System.out.println("fieldNames.get(k).toString()>"+fieldNames.get(k).toString()+" "+ obdata[k].toString());
	if(fieldNames.get(k).toString().equalsIgnoreCase("query") && obdata[k].toString() != null)
	{
	querycount = new CRMHelper().getTotalDataInQuery(connectionSpace, obdata[k].toString());
	countlist.add(querycount);
	one.put(fieldNames.get(k).toString(),obdata[k].toString());
	one.put("count",querycount);
	}
	if(fieldNames.get(k).toString().equalsIgnoreCase("count") && querycount != null)
	{
	//System.out.println("querycountToassign>"+querycount);
	//one.put(fieldNames.get(k).toString(),querycount);
	}
	if(fieldNames.get(k).toString().equalsIgnoreCase("active_deactive"))
	{
	if(obdata[k].toString().equalsIgnoreCase("0"))
	{
	obdata[k] = "Active";
	}else
	{
	obdata[k] = "Inactive";
	}
	}
	
	one.put(fieldNames.get(k).toString(),obdata[k].toString());
	
	} 
	
	}
	}
	//querycountToassign = querycount;
	Listhb.add(one);
	}

	    }
	setGroupdataViewList(Listhb);
	setTotal((int) Math.ceil((double) getRecords()
	/ (double) getRows()));
	}
	
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: beforeCRMGroupDetailsView of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	public String fetchAllParameters()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	JSONObject jsonObject = null;
	ClientHelper3 ch3 = new ClientHelper3();

	// Industry
	jsonArrayIndustry = new JSONArray();
	Map<String, String> industry = ch3.fetchAllIndustry();
	if (!industry.isEmpty())
	{
	for (Map.Entry<String, String> entry : industry.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArrayIndustry.add(jsonObject);
	}
	}

	// Source
	jsonArraySource = new JSONArray();
	Map<String, String> source = ch3.fetchAllSource();
	if (!source.isEmpty())
	{
	for (Map.Entry<String, String> entry : source.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArraySource.add(jsonObject);
	}
	}
	
	jsonArrayAllergic = new JSONArray();
	Map<String, String> allergic = ch3.fetchAllAllergic(entityType);
	if (!allergic.isEmpty())
	{
	for (Map.Entry<String, String> entry : allergic.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArrayAllergic.add(jsonObject);
	}
	}
	
	/*jsonArrayOffering = new JSONArray();
	Map<String, String> offering = ch3.fetchAllAllergic();
	System.out.println("Allergic Siz>>>>>>>>>>>>>>>>>>>>>>>"+offering.size());
	if (!offering.isEmpty())
	{
	for (Map.Entry<String, String> entry : offering.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArrayOffering.add(jsonObject);
	}
	}*/
	
	// Location
	jsonArrayLocation = new JSONArray();
	Map<String, String> location = ch3.fetchAllLocation();
	if (!location.isEmpty())
	{
	for (Map.Entry<String, String> entry : location.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArrayLocation.add(jsonObject);
	}
	}
	
	// Account Manager
	CommonHelper ch = new CommonHelper();
	String contactIds = ch.getLevelHierarchyOfContId(CommonHelper.moduleName, empId);
	if (contactIds != null && contactIds.length() > 0)
	{
	StringBuilder query = new StringBuilder();
	query
	.append("select comp.id, emp.empName from compliance_contacts as comp inner join employee_basic as emp on emp.id = comp.emp_id where comp.id in (");
	query.append(contactIds);
	query.append(") ");
	List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (list != null && !list.isEmpty())
	{
	jsonArrayAccMgr = new JSONArray();
	Map<String, String> map = CommonHelper.convertListToMap(list, false);
	if (map != null && !map.isEmpty())
	{
	for (Map.Entry<String, String> entry : map.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArrayAccMgr.add(jsonObject);
	}
	}
	}
	}

	// Department
	Map<String, String> department = ch3.fetchAllDepartment();
	if (department != null && !department.isEmpty())
	{
	jsonArraydepartment = new JSONArray();
	for (Map.Entry<String, String> entry : department.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArraydepartment.add(jsonObject);
	}
	}

	// Designation
	Map<String, String> designation = null;
	if (entityType == EntityType.LEAD)
	{
	LeadHelper2 lh2 = new LeadHelper2();
	designation = lh2.fetchAllDesignationOfLeadContact();
	}
	else if (entityType == EntityType.CLIENT)
	{
	designation = ch3.fetchAllDesignationOfClientContact();
	}
	else if (entityType == EntityType.ASSOCIATE)
	{
	AssociateHelper1 ah1 = new AssociateHelper1();
	designation = ah1.fetchAllDesignationOfAssociateContact();
	}
	if (designation != null && !designation.isEmpty())
	{
	jsonArrayDesignation = new JSONArray();
	for (Map.Entry<String, String> entry : designation.entrySet())
	{
	jsonObject = new JSONObject();
	jsonObject.put("ID", entry.getKey());
	jsonObject.put("NAME", entry.getValue());
	jsonArrayDesignation.add(jsonObject);
	}
	}

	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String viewCRMData()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	/*
	 * System.out.println("entityType: " + entityType);
	 * System.out.println("industry: " + industry);
	 * System.out.println("subIndustry: " + subIndustry);
	 * System.out.println("source: " + source); System.out.println("rating: "
	 * + rating); System.out.println("location: " + location);
	 * System.out.println("department: " + department);
	 * System.out.println("designation: " + designation);
	 * System.out.println("influence: " + influence);
	 * System.out.println("chkBirthday: " + chkBirthday);
	 * System.out.println("birthdayFrom: " + birthdayFrom);
	 * System.out.println("chkAnniversary: " + chkAnniversary);
	 * System.out.println("anniversaryFrom: " + anniversaryFrom);
	 */
	parameter=new StringBuilder();
	String empIdofuser = session.get("empIdofuser").toString().split("-")[1];
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	crmDataList = new ArrayList<Object>();
	if(this.getEnt()!=null && !this.getEnt().equals("-1") &&  !this.getEnt().equalsIgnoreCase("undefined"))
	{
	parameter.append(getEnt()+"#");
	}
	
	int dataCount = 0;
	// Lead
	// *******************************************************************
	if (entityType == EntityType.LEAD || entityType == EntityType.ALL)
	{
	CRMHelper crm = new CRMHelper();
	// dataCount = leadDataCount();
	dataCount = count;
	if (dataCount != 0)
	{
	setRecords(dataCount);
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords()) to = getRecords();

	List<String> listDataToShow = crm.getLeadBasicToShow();
	Map<String, String> listColumn = crm.getMappedColumnForLead();

	if (listDataToShow != null && !listDataToShow.isEmpty())
	{
	StringBuilder query = new StringBuilder("");
	StringBuilder query2 = new StringBuilder("");

	query.append("select ");
	for (String data : listDataToShow)
	{
	if (data.equalsIgnoreCase("leadName"))
	{
	query.append("lg.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("starRating"))
	{
	query.append("lg.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("industry"))
	{
	query.append("ind.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("department"))
	{
	query.append("dept.deptName, ");
	}
	else
	{
	query.append("lcd.");
	query.append(data);
	query.append(", ");
	}
	}
	query2.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
	query2.append(" ");

	query2.append("from lead_contact_data as lcd ");
	if (listDataToShow.contains("leadName")) query2.append("inner join leadgeneration as lg on lg.id = lcd.leadName ");
	if (listDataToShow.contains("industry")) query2.append("left join industrydatalevel1 as ind on ind.id = lg.industry ");
	if (listDataToShow.contains("department")) query2.append("left join department as dept on dept.id = lcd.department ");
	query2.append("left join subindustrydatalevel2 as sind on sind.id = lg.subIndustry ");
	query2.append("where lg.status IN('");
	query2.append(relationshipSubType.equals("-1") ? "0','4" : relationshipSubType);
	query2.append("') and lg.userName IN(");
	query2.append(cIdAll);
	query2.append(") ");

	if (industry != null && !industry.equals("-1"))
	{
	query2.append("and lg.industry = '");
	query2.append(industry);
	query2.append("' ");
	}
	if (subIndustry != null && !subIndustry.equals("-1"))
	{
	query2.append("and lg.subIndustry = '");
	query2.append(subIndustry);
	query2.append("' ");
	}
	if (rating != null && !rating.equals("-1"))
	{
	query2.append("and lg.starRating = '");
	query2.append(rating);
	query2.append("' ");
	}
	if (source != null && !source.equals("-1"))
	{
	query2.append("and lg.sourceName = '");
	query2.append(source);
	query2.append("' ");
	}
	if (location != null && !location.equals("-1"))
	{
	query2.append("and lg.name = '");
	query2.append(location);
	query2.append("' ");
	}
	if (department != null && !department.equals("-1"))
	{
	query2.append("and lcd.department = '");
	query2.append(department);
	query2.append("' ");
	}
	if (designation != null && !designation.equals("-1"))
	{
	query2.append("and lcd.designation = '");
	query2.append(designation);
	query2.append("' ");
	}
	if (influence != null && !influence.equals("-1"))
	{
	query2.append("and lcd.degreeOfInfluence = '");
	query2.append(influence);
	query2.append("' ");
	}
	if (chkBirthday)
	{
	query2.append("and lcd.birthday LIKE '%-");
	query2.append(birthdayFrom);
	query2.append("-%' ");
	}
	if (chkAnniversary)
	{
	query2.append("and lcd.anniversary LIKE '%-");
	query2.append(anniversaryFrom);
	query2.append("-%' ");
	}
	if (age != null && !age.equals("-1"))
	{
	query2.append("and lcd.patient_age >='");
	if(age.equalsIgnoreCase("20 Plus"))
	{
	query2.append(20);	
	}
	if(age.equalsIgnoreCase("30 Plus"))
	{
	query2.append(30);
	}
	if(age.equalsIgnoreCase("40 Plus"))
	{
	query2.append(40);
	}
	if(age.equalsIgnoreCase("50 Plus"))
	{
	query2.append(50);
	}
	if(age.equalsIgnoreCase("60 Plus"))
	{
	query2.append(60);
	}
	
	query2.append(age);
	query2.append("' ");
	}
	if (sex != null && !sex.equals("-1"))
	{
	query2.append("and lcd.pateint_sex = '");
	query2.append(sex);
	query2.append("' ");
	}
	if (allergic_to != null && !allergic_to.equals("-1"))
	{
	query2.append("and lcd.allergic_to = '");
	query2.append(allergic_to);
	query2.append("' ");
	}
	if (blood_group != null && !blood_group.equals("-1"))
	{
	query2.append("and lcd.blood_group = '");
	query2.append(blood_group);
	query2.append("' ");
	}
	
	
	String app = "lg.";
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	{
	if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
	{
	query2.append(" order by " + app + getSidx());
	}
	if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
	{
	query2.append(" order by " + app + getSidx() + " DESC");
	}
	}
	query2.append(" limit " + from + "," + getRows());

	// Put query to static variable so that it
	// can be used for 'Auto Group'
	autoGroup = query2.toString();
	autoGroup = query2.toString();
	//System.out.println("autoGroup Lead>"+autoGroup);
	if (query2 == null || query2.equals("")) return SUCCESS;
	List data = coi.executeAllSelectQuery(query2.toString(), connectionSpace);
	if (data != null && !data.isEmpty())
	{
	for (Iterator it = data.iterator(); it.hasNext();)
	{
	Object[] obdata = (Object[]) it.next();
	Map<String, Object> one = new HashMap<String, Object>();
	for (int k = 0; k < listDataToShow.size(); k++)
	{
	if (k == 0) one.put(listDataToShow.get(k).toString(), (Integer) obdata[k]);
	else
	{
	one.put(listColumn.get(listDataToShow.get(k).toString()), CommonHelper.getFieldValue(obdata[k]));
	}
	}
	crmDataList.add(one);
	}
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	}
	}
	}
	//Client******************************************************************
	if (entityType == EntityType.CLIENT || entityType == EntityType.ALL)
	{
	CRMHelper crm = new CRMHelper();
	dataCount = count;
	if (dataCount != 0)
	{
	setRecords(dataCount);
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords()) to = getRecords();

	List<String> listDataToShow = crm.getClientBasicDataToShow();
	Map<String, String> listColumn = crm.getMappedColumnForClient();

	if (listDataToShow != null && !listDataToShow.isEmpty())
	{
	StringBuilder query = new StringBuilder("");
	StringBuilder query2 = new StringBuilder("");

	query.append("select ");
	for (String data : listDataToShow)
	{
	if (data.equalsIgnoreCase("clientName"))
	{
	query.append("cbd.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("starRating"))
	{
	query.append("cbd.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("industry"))
	{
	query.append("ind.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("department"))
	{
	query.append("dept.deptName, ");
	}
	else
	{
	query.append("ccd.");
	query.append(data);
	query.append(", ");
	}
	}
	query2.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
	query2.append(" ");

	query2.append("from client_contact_data as ccd ");
	query2.append("inner join client_basic_data as cbd on cbd.id = ccd.clientName ");
	query2.append("left join industrydatalevel1 as ind on ind.id = cbd.industry ");
	query2.append("left join department as dept on dept.id = ccd.department ");
	query2.append("left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry ");
	query2.append("left join client_offering_mapping as com on com.clientName = cbd.id ");
	query2.append("where com.isConverted IN('");
	query2.append(relationshipSubType.equals("-1") ? "0','1','2" : relationshipSubType);
	query2.append("') and cbd.userName IN(");
	query2.append(cIdAll);
	query2.append(") ");

	if (industry != null && !industry.equals("-1"))
	{
	query2.append("and cbd.industry = '");
	query2.append(industry);
	query2.append("' ");
	}
	if (subIndustry != null && !subIndustry.equals("-1"))
	{
	query2.append("and cbd.subIndustry = '");
	query2.append(subIndustry);
	query2.append("' ");
	}
	if (rating != null && !rating.equals("-1"))
	{
	query2.append("and cbd.starRating = '");
	query2.append(rating);
	query2.append("' ");
	}
	if (source != null && !source.equals("-1"))
	{
	query2.append("and cbd.sourceMaster = '");
	query2.append(source);
	query2.append("' ");
	}
	if (location != null && !location.equals("-1"))
	{
	query2.append("and cbd.location = '");
	query2.append(location);
	query2.append("' ");
	}
	if (department != null && !department.equals("-1"))
	{
	query2.append("and ccd.department = '");
	query2.append(department);
	query2.append("' ");
	}
	if (designation != null && !designation.equals("-1"))
	{
	query2.append("and ccd.designation = '");
	query2.append(designation);
	query2.append("' ");
	}
	if (influence != null && !influence.equals("-1"))
	{
	query2.append("and ccd.degreeOfInfluence = '");
	query2.append(influence);
	query2.append("' ");
	}
	if (chkBirthday)
	{
	query2.append("and ccd.birthday LIKE '%-");
	query2.append(birthdayFrom);
	query2.append("-%' ");
	}
	if (chkAnniversary)
	{
	query2.append("and ccd.anniversary LIKE '%-");
	query2.append(anniversaryFrom);
	query2.append("-%' ");
	}
	if (age != null && !age.equals("-1"))
	{
	query2.append("and ccd.patient_age >='");
	if(age.equalsIgnoreCase("20 Plus"))
	{
	query2.append(20);	
	}
	if(age.equalsIgnoreCase("30 Plus"))
	{
	query2.append(30);
	}
	if(age.equalsIgnoreCase("40 Plus"))
	{
	query2.append(40);
	}
	if(age.equalsIgnoreCase("50 Plus"))
	{
	query2.append(50);
	}
	if(age.equalsIgnoreCase("60 Plus"))
	{
	query2.append(60);
	}
	
	query2.append(age);
	query2.append("' ");
	}
	if (sex != null && !sex.equals("-1"))
	{
	query2.append("and ccd.pateint_sex = '");
	query2.append(sex);
	query2.append("' ");
	}
	if (allergic_to != null && !allergic_to.equals("-1"))
	{
	query2.append("and ccd.allergic_to = '");
	query2.append(allergic_to);
	query2.append("' ");
	}
	if (blood_group != null && !blood_group.equals("-1"))
	{
	query2.append("and ccd.blood_group = '");
	query2.append(blood_group);
	query2.append("' ");
	}
	
	
	String app = "cbd.";
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	{
	if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
	{
	query2.append(" order by " + app + getSidx());
	}
	if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
	{
	query2.append(" order by " + app + getSidx() + " DESC");
	}
	}
	query2.append(" limit " + from + "," + getRows());
	autoGroup = query2.toString();
	System.out.println("autoGroup Client>"+autoGroup);
	if (query2 == null || query2.equals("")) return SUCCESS;
	List data = coi.executeAllSelectQuery(query2.toString(), connectionSpace);
	if (data != null && !data.isEmpty())
	{
	for (Iterator it = data.iterator(); it.hasNext();)
	{
	Object[] obdata = (Object[]) it.next();
	Map<String, Object> one = new HashMap<String, Object>();

	for (int k = 0; k < listDataToShow.size(); k++)
	{
	if (k == 0) one.put(listDataToShow.get(k).toString(), (Integer) obdata[k]);
	else
	{
	one.put(listColumn.get(listDataToShow.get(k).toString()), CommonHelper.getFieldValue(obdata[k]));
	}
	}
	crmDataList.add(one);
	}
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	}
	}
	}
	if (entityType == EntityType.ASSOCIATE || entityType == EntityType.ALL)
	{
	CRMHelper crm = new CRMHelper();
	dataCount = count;
	if (dataCount != 0)
	{
	setRecords(dataCount);
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords()) to = getRecords();

	List<String> listDataToShow = crm.getAssociateBasicDataToShow();
	Map<String, String> listColumn = crm.getMappedColumnForAssociate();

	if (listDataToShow != null && !listDataToShow.isEmpty())
	{
	StringBuilder query = new StringBuilder("");
	StringBuilder query2 = new StringBuilder("");

	query.append("select ");
	for (String data : listDataToShow)
	{
	if (data.equalsIgnoreCase("associateName"))
	{
	query.append("cbd.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("associateRating"))
	{
	query.append("cbd.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("industry"))
	{
	query.append("ind.");
	query.append(data);
	query.append(", ");
	}
	else if (data.equalsIgnoreCase("department"))
	{
	query.append("dept.deptName, ");
	}
	else
	{
	query.append("ccd.");
	query.append(data);
	query.append(", ");
	}
	}
	query2.append(query.toString().substring(0, query.toString().lastIndexOf(",")));
	query2.append(" ");

	query2.append("from associate_contact_data as ccd ");
	query2.append("inner join associate_basic_data as cbd on cbd.id = ccd.associateName ");
	query2.append("left join industrydatalevel1 as ind on ind.id = cbd.industry ");
	query2.append("left join department as dept on dept.id = ccd.department ");
	query2.append("left join subindustrydatalevel2 as sind on sind.id = cbd.subIndustry ");
	query2.append("left join associate_offering_mapping as com on com.associateName = cbd.id ");
	query2.append("where com.isConverted IN('");
	query2.append(relationshipSubType.equals("-1") ? "0','1','2" : relationshipSubType);
	query2.append("') and cbd.userName IN(");
	query2.append(cIdAll);
	query2.append(") ");

	if (industry != null && !industry.equals("-1"))
	{
	query2.append("and cbd.industry = '");
	query2.append(industry);
	query2.append("' ");
	}
	if (subIndustry != null && !subIndustry.equals("-1"))
	{
	query2.append("and cbd.subIndustry = '");
	query2.append(subIndustry);
	query2.append("' ");
	}
	if (rating != null && !rating.equals("-1"))
	{
	query2.append("and cbd.associateRating = '");
	query2.append(rating);
	query2.append("' ");
	}
	if (source != null && !source.equals("-1"))
	{
	query2.append("and cbd.sourceName = '");
	query2.append(source);
	query2.append("' ");
	}
	if (location != null && !location.equals("-1"))
	{
	query2.append("and cbd.location = '");
	query2.append(location);
	query2.append("' ");
	}
	if (department != null && !department.equals("-1"))
	{
	query2.append("and ccd.department = '");
	query2.append(department);
	query2.append("' ");
	}
	if (designation != null && !designation.equals("-1"))
	{
	query2.append("and ccd.designation = '");
	query2.append(designation);
	query2.append("' ");
	}
	if (influence != null && !influence.equals("-1"))
	{
	query2.append("and ccd.degreeOfInfluence = '");
	query2.append(influence);
	query2.append("' ");
	}
	if (chkBirthday)
	{
	query2.append("and ccd.birthday LIKE '%-");
	query2.append(birthdayFrom);
	query2.append("-%' ");
	}
	if (chkAnniversary)
	{
	query2.append("and ccd.anniversary LIKE '%-");
	query2.append(anniversaryFrom);
	query2.append("-%' ");
	}

	String app = "cbd.";
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	{
	if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
	{
	query2.append(" order by " + app + getSidx());
	}
	if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
	{
	query2.append(" order by " + app + getSidx() + " DESC");
	}
	}
	query2.append(" limit " + from + "," + getRows());
	autoGroup = query2.toString();
	//	System.out.println("autoGroup Associate>"+autoGroup);
	if (query2 == null || query2.equals("")) return SUCCESS;
	List data = coi.executeAllSelectQuery(query2.toString(), connectionSpace);
	if (data != null && !data.isEmpty())
	{
	for (Iterator it = data.iterator(); it.hasNext();)
	{
	Object[] obdata = (Object[]) it.next();
	Map<String, Object> one = new HashMap<String, Object>();
	for (int k = 0; k < listDataToShow.size(); k++)
	{
	if (k == 0) one.put(listDataToShow.get(k).toString(), (Integer) obdata[k]);
	else
	{
	one.put(listColumn.get(listDataToShow.get(k).toString()), CommonHelper.getFieldValue(obdata[k]));
	}
	}
	crmDataList.add(one);
	}
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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

	// for group data count details view.
	public String viewGroupCountDetails()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try {
	StringBuilder query = new StringBuilder();
	query.append("select name, comment, type, view_type from group_name where moduleName = 'WFPM' and id = '");
	query.append(id);
	query.append("'");
	List dataCount = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	setCRMMailGroupViewProp();
	if(dataCount != null && dataCount.size()>0)
	{
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	groupName = object[0].toString();
	comment = object[1].toString();
	if(object[2]!=null)
	{
	createType = object[2].toString();	
	}
	if(object[3]!=null)
	{
	viewType = object[3].toString();	
	}
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	private int leadDataCount()
	{
	int count = 0;
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	StringBuilder query1 = new StringBuilder();
	query1
	.append("select count(lcd.id) from lead_contact_data as lcd inner join leadgeneration as lg on lg.id = lcd.leadName left join industrydatalevel1 as ind on ind.id = lg.industry left join subindustrydatalevel2 as sind on sind.id = lg.subIndustry left join department as dept on dept.id = lcd.department where lg.status IN ('");
	query1.append(relationshipSubType.equals("-1") ? "0','4" : relationshipSubType);
	query1.append("') and lg.userName IN (");
	query1.append(cIdAll);
	query1.append(")");

	if (industry != null && !industry.equals("-1"))
	{
	query1.append("and lg.industry = '");
	query1.append(industry);
	query1.append("' ");
	}
	if (subIndustry != null && !subIndustry.equals("-1"))
	{
	query1.append("and lg.subIndustry = '");
	query1.append(subIndustry);
	query1.append("' ");
	}
	if (rating != null && !rating.equals("-1"))
	{
	query1.append("and lg.starRating = '");
	query1.append(rating);
	query1.append("' ");
	}
	if (source != null && !source.equals("-1"))
	{
	query1.append("and lg.sourceName = '");
	query1.append(source);
	query1.append("' ");
	}
	if (location != null && !location.equals("-1"))
	{
	query1.append("and lg.name = '");
	query1.append(location);
	query1.append("' ");
	}
	if (department != null && !department.equals("-1"))
	{
	query1.append("and lcd.department = '");
	query1.append(department);
	query1.append("' ");
	}
	if (designation != null && !designation.equals("-1"))
	{
	query1.append("and lcd.designation = '");
	query1.append(designation);
	query1.append("' ");
	}
	if (influence != null && !influence.equals("-1"))
	{
	query1.append("and lcd.degreeOfInfluence = '");
	query1.append(influence);
	query1.append("' ");
	}
	if (chkBirthday)
	{
	query1.append("and lcd.birthday LIKE '%-");
	query1.append(birthdayFrom);
	query1.append("-%' ");
	}
	if (chkAnniversary)
	{
	query1.append("and lcd.anniversary LIKE '%-");
	query1.append(anniversaryFrom);
	query1.append("-%' ");
	}

	List dataCount = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
	if (dataCount != null && !dataCount.isEmpty()) count = Integer.parseInt(dataCount.get(0).toString());
	return count;
	}

	@SuppressWarnings("unchecked")
	private int associateDataCount()
	{
	int count = 0;
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	StringBuilder query1 = new StringBuilder();
	query1
	.append("select count(ccd.id) from associate_contact_data as ccd inner join associate_basic_data as cbd on cbd.id = ccd.associateName inner join associate_offering_mapping as com on com.associateName = cbd.id where com.isConverted IN('");
	query1.append(relationshipSubType.equals("-1") ? "0','1','2" : relationshipSubType);
	query1.append("') and cbd.userName IN(");
	query1.append(cIdAll);
	query1.append(")");

	if (industry != null && !industry.equals("-1"))
	{
	query1.append("and cbd.industry = '");
	query1.append(industry);
	query1.append("' ");
	}
	if (subIndustry != null && !subIndustry.equals("-1"))
	{
	query1.append("and cbd.subIndustry = '");
	query1.append(subIndustry);
	query1.append("' ");
	}
	if (rating != null && !rating.equals("-1"))
	{
	query1.append("and cbd.associateRating = '");
	query1.append(rating);
	query1.append("' ");
	}
	if (source != null && !source.equals("-1"))
	{
	query1.append("and cbd.sourceName = '");
	query1.append(source);
	query1.append("' ");
	}
	if (location != null && !location.equals("-1"))
	{
	query1.append("and cbd.location = '");
	query1.append(location);
	query1.append("' ");
	}
	if (department != null && !department.equals("-1"))
	{
	query1.append("and ccd.department = '");
	query1.append(department);
	query1.append("' ");
	}
	if (designation != null && !designation.equals("-1"))
	{
	query1.append("and ccd.designation = '");
	query1.append(designation);
	query1.append("' ");
	}
	if (influence != null && !influence.equals("-1"))
	{
	query1.append("and ccd.degreeOfInfluence = '");
	query1.append(influence);
	query1.append("' ");
	}
	if (chkBirthday)
	{
	query1.append("and ccd.birthday LIKE '%-");
	query1.append(birthdayFrom);
	query1.append("-%' ");
	}
	if (chkAnniversary)
	{
	query1.append("and ccd.anniversary LIKE '%-");
	query1.append(anniversaryFrom);
	query1.append("-%' ");
	}

	List dataCount = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
	if (dataCount != null && !dataCount.isEmpty()) count = Integer.parseInt(dataCount.get(0).toString());
	return count;
	}

	@SuppressWarnings("unchecked")
	private int clientDataCount()
	{
	int count = 0;
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	StringBuilder query1 = new StringBuilder();
	query1
	.append("select count(ccd.id) from client_contact_data as ccd inner join client_basic_data as cbd on cbd.id = ccd.clientName inner join client_offering_mapping as com on com.clientName = cbd.id where com.isConverted IN('");
	query1.append(relationshipSubType.equals("-1") ? "0','1','2" : relationshipSubType);
	query1.append("') and cbd.userName IN(");
	query1.append(cIdAll);
	query1.append(")");

	if (industry != null && !industry.equals("-1"))
	{
	query1.append("and cbd.industry = '");
	query1.append(industry);
	query1.append("' ");
	}
	if (subIndustry != null && !subIndustry.equals("-1"))
	{
	query1.append("and cbd.subIndustry = '");
	query1.append(subIndustry);
	query1.append("' ");
	}
	if (rating != null && !rating.equals("-1"))
	{
	query1.append("and cbd.starRating = '");
	query1.append(rating);
	query1.append("' ");
	}
	if (source != null && !source.equals("-1"))
	{
	query1.append("and cbd.sourceMaster = '");
	query1.append(source);
	query1.append("' ");
	}
	if (location != null && !location.equals("-1"))
	{
	query1.append("and cbd.location = '");
	query1.append(location);
	query1.append("' ");
	}
	if (department != null && !department.equals("-1"))
	{
	query1.append("and ccd.department = '");
	query1.append(department);
	query1.append("' ");
	}
	if (designation != null && !designation.equals("-1"))
	{
	query1.append("and ccd.designation = '");
	query1.append(designation);
	query1.append("' ");
	}
	if (influence != null && !influence.equals("-1"))
	{
	query1.append("and ccd.degreeOfInfluence = '");
	query1.append(influence);
	query1.append("' ");
	}
	if (chkBirthday)
	{
	query1.append("and ccd.birthday LIKE '%-");
	query1.append(birthdayFrom);
	query1.append("-%' ");
	}
	if (chkAnniversary)
	{
	query1.append("and ccd.anniversary LIKE '%-");
	query1.append(anniversaryFrom);
	query1.append("-%' ");
	}

	List dataCount = coi.executeAllSelectQuery(query1.toString(), connectionSpace);
	if (dataCount != null && !dataCount.isEmpty()) count = Integer.parseInt(dataCount.get(0).toString());
	return count;
	}

	@SuppressWarnings("unchecked")
	public String beforeSendPage()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	// Template root
	messagerootList = new HashMap<String, String>();
	messagerootList.put("Promo", "Promo");
	messagerootList.put("Trans", "Transactional");
	messagerootList.put("Open", "Open");

	// Sales dept Employee list
	employeeList = new EmployeeHelper().fetchEmpWithMobile();

	// Group
	// System.out.println("groupName:" + groupName);
	// System.out.println("chkGroup:" + chkGroup);
	
	if (groupName != null && !groupName.equalsIgnoreCase(""))
	{
	// System.out.println("autoGroup:" + autoGroup);
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	boolean status = false;
	ob = new InsertDataTable();
	ob.setColName("name");
	ob.setDataName(groupName);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("comment");
	ob.setDataName(comment);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("user");
	ob.setDataName(userName);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("type");
	ob.setDataName(createType);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("query");
	ob.setDataName(autoGroup);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("view_type");
	ob.setDataName(viewType);
	insertData.add(ob);

	status = coi.insertIntoTable("group_name", insertData, connectionSpace);
	
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	
	/**
	 * create CRM Mail Group for communication.
	 * 
	 * */
	public String beforeMailSendPage()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	// Code
	
	if (groupName != null && !groupName.equalsIgnoreCase(""))
	{
	/*if(this.getEnt()!=null && !this.getEnt().equals("-1") &&  !this.getEnt().equalsIgnoreCase("undefined"))
	{
	System.out.println(entityType);
	parameter.append(entityType+"__");
	}
	if(this.getRelationshipSubType()!=null && !this.getRelationshipSubType().equals("-1") &&  !this.getRelationshipSubType().equalsIgnoreCase("undefined"))
	{
	System.out.println(getRelationshipSubType());
	parameter.append(getRelationshipSubType()+"__");
	}
	if(this.getIndustry()!=null && !this.getIndustry().equals("-1") &&  !this.getIndustry().equalsIgnoreCase("undefined"))
	{
	System.out.println(getIndustry());
	parameter.append(getIndustry()+"__");
	}
	if(this.getSubIndustry()!=null && !this.getSubIndustry().equals("-1") &&  !this.getSubIndustry().equalsIgnoreCase("undefined"))
	{
	System.out.println(getSubIndustry());
	parameter.append(getSubIndustry()+"__");
	}
	if(this.getSource()!=null && !this.getSource().equals("-1") &&  !this.getSource().equalsIgnoreCase("undefined"))
	{
	System.out.println(getSource());
	parameter.append(getSource()+"__");
	}
	if(this.getRating()!=null && !this.getRating().equals("-1") &&  !this.getRating().equalsIgnoreCase("undefined"))
	{
	System.out.println(getRating());
	parameter.append(getRating()+"__");
	}
	if(this.getLocation()!=null && !this.getLocation().equals("-1") &&  !this.getLocation().equalsIgnoreCase("undefined"))
	{
	System.out.println(getLocation());
	parameter.append(getLocation()+"__");
	}
	if(this.getDepartment()!=null && !this.getDepartment().equals("-1") &&  !this.getDepartment().equalsIgnoreCase("undefined"))
	{
	System.out.println(getDepartment());
	parameter.append(getDepartment()+"__");
	}
	if(this.getDesignation()!=null && !this.getDesignation().equals("-1") &&  !this.getDesignation().equalsIgnoreCase("undefined"))
	{
	System.out.println(getDesignation());
	parameter.append(getDesignation()+"__");
	}
	if(this.getInfluence()!=null && !this.getInfluence().equals("-1") &&  !this.getInfluence().equalsIgnoreCase("undefined"))
	{
	System.out.println(getInfluence());
	parameter.append(getInfluence()+"__");
	}
	if(this.getBirthdayFrom()!=null && !this.getBirthdayFrom().equals("-1") &&  !this.getBirthdayFrom().equalsIgnoreCase("undefined"))
	{
	System.out.println(getBirthdayFrom());
	parameter.append(getBirthdayFrom()+"__");
	}
	if(this.getAnniversaryFrom()!=null && !this.getAnniversaryFrom().equals("-1") &&  !this.getAnniversaryFrom().equalsIgnoreCase("undefined"))
	{
	System.out.println(getInfluence());
	parameter.append(getInfluence()+"__");
	}
	if(this.getInfluence()!=null && !this.getInfluence().equals("-1") &&  !this.getInfluence().equalsIgnoreCase("undefined"))
	{
	System.out.println(getInfluence());
	parameter.append(getInfluence()+"__");
	}*/
	
	
	// System.out.println("autoGroup:" + autoGroup);
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	boolean status = false;
	ob = new InsertDataTable();
	ob.setColName("name");
	ob.setDataName(groupName);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("comment");
	ob.setDataName(comment);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("user");
	ob.setDataName(userName);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("type");
	ob.setDataName(createType);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("query");
	ob.setDataName(autoGroup);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("view_type");
	ob.setDataName(viewType);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("moduleName");
	ob.setDataName("WFPM");
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(DateUtil.getCurrentTimeHourMin());
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);
	status = coi.insertIntoTable("group_name", insertData, connectionSpace);
	}
	//	System.out.println("Group Id >>>>"+groupId);
	//	System.out.println("Id >>>>"+id);
	setInstantMessageDataFields();
	}
	catch (Exception e)
	{

	addActionError("There Is Some Problem !");
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	
	public String beforeMailSendPage1()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	//	System.out.println("Group Id >>>>"+groupId);
	//	System.out.println("Id >>>>"+id);
	//	System.out.println(data);
	setInstantMessageDataFields();
	}
	catch (Exception e)
	{

	addActionError("There Is Some Problem !");
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
    	
	private String setInstantMessageDataFields()
	{
	String returnResult = ERROR;
	try
	{
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
	.get("connectionSpace");
	mobileTextBox = new ArrayList<ConfigurationUtilBean>();
	writemessageTextBox = new ArrayList<ConfigurationUtilBean>();
	messageNameDropdown = new ArrayList<ConfigurationUtilBean>();
	frequency = new ArrayList<ConfigurationUtilBean>();
	String orgLevel = (String) session.get("orgnztnlevel");
	String orgId = (String) session.get("mappedOrgnztnId");
	List<GridDataPropertyView> instantMsg = Configuration
	.getConfigurationData("mapped_instant_mail_configuration",
	accountID, connectionSpace, "", 0, "table_name",
	"instant_mail_configuration");
	if (instantMsg != null)
	{
	for (GridDataPropertyView gdp : instantMsg)
	{
	ConfigurationUtilBean obj = new ConfigurationUtilBean();

	if (gdp.getColType().trim().equalsIgnoreCase("T")
	&& gdp.getColomnName().equalsIgnoreCase("email_id"))
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
	mobileTextBox.add(obj);

	}
	else if (gdp.getColType().trim().equalsIgnoreCase("T")
	&& gdp.getColomnName()
	.equalsIgnoreCase("writemail"))
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
	writemessageTextBox.add(obj);
	}

	else if (gdp.getColType().trim().equalsIgnoreCase("D")
	&& !gdp.getColomnName()
	.equalsIgnoreCase("userName")
	&& !gdp.getColomnName().equalsIgnoreCase("date")
	&& !gdp.getColomnName().equalsIgnoreCase("time")
	&& !gdp.getColomnName().equalsIgnoreCase("msgtype")
	&& !gdp.getColomnName().equalsIgnoreCase("subDept"))
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
	messageNameDropdown.add(obj);
	}
	else if (gdp.getColType().trim().equalsIgnoreCase("R")
	&& !gdp.getColomnName()
	.equalsIgnoreCase("userName")
	&& !gdp.getColomnName().equalsIgnoreCase("date")
	&& !gdp.getColomnName().equalsIgnoreCase("time"))
	{
	obj.setValue(gdp.getHeadingName());
	obj.setKey(gdp.getColomnName());
	obj.setColType(gdp.getColType());
	obj.setValidationType(gdp.getValidationType());
	if (gdp.getMandatroy().toString().equals("1"))
	{
	obj.setMandatory(true);
	}
	else
	{
	obj.setMandatory(false);
	}
	frequency.add(obj);
	}

	}
	}

	/* Get Contact group name */
	CommonforClassdata obhj = new CommonOperAtion();
	Map<String, Object> whereClose = new HashMap<String, Object>();
	List<String> listdata = new ArrayList<String>();
	listdata.add("id");
	listdata.add("groupName");
	List datatemp = obhj.getSelectdataFromSelectedFields("groupinfo",
	listdata, whereClose, connectionSpace);
	groupcontactNameListData.put("all_contactType", "All Contact Type");
	for (Iterator iterator = datatemp.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	groupcontactNameListData.put(object[0].toString(),
	object[1].toString());
	}
	Map<String, String> sortedMap = obhj
	.sortByComparator(groupcontactNameListData);
	setGroupcontactNameListData(sortedMap);

	/* Get group name */

	List<String> listdatas = new ArrayList<String>();
	listdatas.add("id");
	listdatas.add("name");

	List datatempz = obhj.getSelectdataFromSelectedFields("group_name",
	listdatas, whereClose, connectionSpace);
	for (Iterator iterator = datatempz.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	groupNameListData.put(object[0].toString(),
	object[1].toString());
	}
	
	Map<String, String> sortedgroup = obhj
	.sortByComparator(groupNameListData);
	setGroupNameList(sortedgroup);
	// * Get template name */
	List<String> listtemplate = new ArrayList<String>();
	listtemplate.add("id");
	listtemplate.add("template_name");

	List templdata = obhj.getSelectdataFromSelectedFields(
	"mail_template", listtemplate, whereClose, connectionSpace);
	for (Iterator iterator = templdata.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	templateNameList
	.put(object[0].toString(), object[1].toString());
	}

	Map<String, String> sortedMaps = obhj
	.sortByComparator(templateNameList);
	setTemplateNameList(sortedMaps);

	/* Get Department Data */

	// Getting Id, Dept Name for Non Service Department

	returnResult = SUCCESS;

	}
	catch (Exception e)
	{
	e.printStackTrace();
	returnResult = ERROR;
	}

	return returnResult;
	}
	
//***********************************************
	
	@SuppressWarnings("unchecked")
	public String urlBeforemailSend()
	{ 
	String returnResult = ERROR;
	String emailContant=null;
	if (!ValidateSession.checkSession()) return LOGIN;
	Set<InstantMessageBean> contactSet = null;
	Set<InstantMessageBean> groupset = null;
	try
	{
	String accountID = (String) session.get("accountid");
	List<GridDataPropertyView> instant = Configuration
	.getConfigurationData(
	"mapped_instant_mail_configuration", accountID,
	connectionSpace, "", 0, "table_name",
	"instant_mail_configuration");

	if (instant != null && instant.size() > 0)
	{
	// create table query based on configuration
	List<TableColumes> tableColume = new ArrayList<TableColumes>();
	for (GridDataPropertyView gdp : instant)
	{
	TableColumes ob1 = new TableColumes();
	ob1 = new TableColumes();
	ob1.setColumnname(gdp.getColomnName());
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	}
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	if (requestParameterNames != null
	&& requestParameterNames.size() > 0)
	{
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	while (it.hasNext())
	{
	 String parmName = it.next().toString();
	//	System.out.println("paramNAME"+parmName);
	 String paramValue = request.getParameter(parmName);
	 if(parmName!=""&& parmName.equals("writemail") )
	{
	 emailContant= request.getParameter("writemail");	
	}
	   	
	
	if (parmName
	.equalsIgnoreCase("__multiselect_check_list"))
	{
	}
	else if (parmName.equalsIgnoreCase("check_list"))
	{
	contactId = getCheck_list();
	}
	else if (parmName.equalsIgnoreCase("groupName"))
	{
	groupId = paramValue;
	}
	else if (parmName.equalsIgnoreCase("subject"))
	{
	subject = paramValue;

	}
	else if (parmName.equalsIgnoreCase("from_email"))
	{
	from_email = paramValue;
	}
	else if (parmName.equalsIgnoreCase("email_id"))
	{
	email_id = paramValue;

	}
	else if (parmName.equalsIgnoreCase("writemail"))
	{
	writemail = paramValue;

	}
	else if (parmName.equalsIgnoreCase("frequencys"))
	{
	frequencys = paramValue;
	}
	else if (parmName.equalsIgnoreCase("date"))
	{
	date = paramValue;

	}
	else if (parmName.equalsIgnoreCase("hours"))
	{
	hours = paramValue;

	}
	else if (parmName.equalsIgnoreCase("day"))
	{
	day = paramValue;

	}
	}
	}

	if (docs != null && docsFileName != null)
	{
	String filePath = new CreateFolderOs()
	.createUserDir("maildocument");
	StringBuffer str = new StringBuffer();
	int k = 1;
	for (int i = 0; i < docs.length; i++)
	{
	if (k < docs.length)
	{
	File fileToCreate = new File(filePath + "//"
	+ docsFileName[i]);
	FileUtils.copyFile(docs[i], fileToCreate);
	FilesUtil.saveFile(docs[i], docsFileName[i],
	filePath + File.separator);
	str.append(docsFileName[i] + ",");
	}
	else
	{
	File fileToCreate = new File(filePath + "//"
	+ docsFileName[i]);
	FileUtils.copyFile(docs[i], fileToCreate);
	FilesUtil.saveFile(docs[i], docsFileName[i],
	filePath + File.separator);
	str.append(docsFileName[i]);
	k++;
	}
	}
	setDocfilename(str.toString());
	}

	// for image storage in folder CRM_ImageData.
	if (crmImage != null && crmImageFileName != null)
	{
	filePath = new CreateFolderOs().createUserDir("CRM_ImageData");
	File fileToCreate = new File(filePath, crmImageFileName);
	FileUtils.copyFile(crmImage, fileToCreate);
	filePath = filePath+"//"+crmImageFileName;
	//	System.out.println("filePath   "+filePath);
	setFilePath(filePath);
	}
	
	if (frequencys != null && !frequencys.equalsIgnoreCase(""))
	{
	if (frequencys.equals("One-Time"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	setDay(day);

	}
	if (frequencys.equals("Daily"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	setDay(day);

	}
	if (frequencys.equals("Weekly"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	setDay(day);
	}
	if (frequencys.equals("Monthly"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	setDay(day);
	}
	if (frequencys.equals("Yearly"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	setDay(day);
	}
	}
	allDataList = new ArrayList<InstantMessageBean>();
	set = new HashSet<InstantMessageBean>();
	contactSet = new HashSet<InstantMessageBean>();
	groupset = new HashSet<InstantMessageBean>();
	InstantMessageBean bean = null;
	if (contactId != null && !contactId.equalsIgnoreCase("-1"))
	{
	List listcontact = getEmailidByContact(contactId);
	for (Iterator iterator = listcontact.iterator(); iterator
	.hasNext();)
	{
	bean = new InstantMessageBean();
	Object[] object = (Object[]) iterator.next();
	bean.setId(Integer.parseInt(object[0].toString()));
	bean.setEmail_id(object[1].toString());
	bean.setName(object[2].toString());
	bean.setWritemail(writemail);
	contactSet.add(bean);
	set.add(bean);
	}
	}
	if (groupId != null && !groupId.equalsIgnoreCase("-1"))
	{
	List listemailId = getEmailidBygroup(Integer
	.parseInt(groupId));
	for (Iterator iterator = listemailId.iterator(); iterator
	.hasNext();)
	{
	bean = new InstantMessageBean();
	Object[] object = (Object[]) iterator.next();
	bean.setId(Integer.parseInt(object[0].toString()));
	bean.setEmail_id(object[1].toString());
	bean.setName(object[2].toString());
	bean.setWritemail(writemail);
	groupset.add(bean);
	set.add(bean);
	}
	}
	// get contact from csv format.
	if (email_id != null && !email_id.equals(""))
	{
	String csvData[] = email_id.split(",");
	dataList = new ArrayList<InstantMessageBean>();
	vdataList = new ArrayList<InstantMessageBean>();
	invdataList = new ArrayList<InstantMessageBean>();
	bdataList = new ArrayList<InstantMessageBean>();
	dudataList = new ArrayList<InstantMessageBean>();
	ArrayList<String> tempDuplicateList = new ArrayList<String>();
	StringBuilder email_ids = new StringBuilder();
	for (int i = 0; i < csvData.length; i++)
	{
	isValid = false;
	if (csvData[i] != null)
	{
	//	System.out.println("csvData[] " + csvData[i]);
	csvData[i] = csvData[i].trim();
	pattern = Pattern.compile(EMAIL_PATTERN);
	matcher = pattern
	.matcher(csvData[i].toString());
	if (matcher.matches())
	{
	isValid = true;
	}
	else
	{
	InstantMessageBean ims = new InstantMessageBean();
	ims.setName("NA");
	ims.setEmail_id(csvData[i]);
	ims.setWritemail(writemail);
	invdataList.add(ims);
	}
	if (isValid)
	{
	if (isMobileBlacklisted(csvData[i]))
	{
	InstantMessageBean ims = new InstantMessageBean();
	ims.setName("NA");
	ims.setEmail_id(csvData[i]);
	ims.setWritemail(writemail);
	bdataList.add(ims);
	}
	else
	{
	if (tempDuplicateList
	.contains(csvData[i]))
	{
	InstantMessageBean ims = new InstantMessageBean();
	ims.setName("NA");
	ims.setEmail_id(csvData[i]);
	ims.setWritemail(writemail);
	dudataList.add(ims);

	}
	else
	{
	InstantMessageBean ims = new InstantMessageBean();
	ims.setName("NA");
	ims.setEmail_id(csvData[i]);
	ims.setWritemail(writemail);
	email_ids.append(csvData[i] + ",");
	vdataList.add(ims);
	set.add(ims);
	tempDuplicateList.add(csvData[i]);
	}
	}
	}

	}
	}
	if (email_id != null)
	{
	email_id = email_ids.toString();

	}
	}
	allDataList.addAll(contactSet);
	allDataList.addAll(groupset);
	detailInformation(emailContant);
	returnResult = SUCCESS;
	}
	else
	{
	addActionMessage("There is some problem");
	returnResult = ERROR;
	}
	}
	catch (Exception e)
	{
	returnResult = ERROR;
	e.printStackTrace();
	}
	//	System.out.println(">>>>>>>>>>" + returnResult);
	return returnResult;

	}
	
	   private HashMap<Integer, HashMap<String, String>> diaplayListOnPage  = new HashMap<Integer, HashMap<String, String>>();

	public void detailInformation(String emailContant)
	{
	correctMailData = new LinkedHashMap<String, String>();
	organizationnameMap = new LinkedHashMap<String, String>();
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	// For Lead/ Client/ Associate
	// System.out.println("entityType:" + entityType);
	 //System.out.println("entityContacts:" + entityContacts);
	 
	 diaplayListOnPage =(HashMap<Integer, HashMap<String, String>>) new CRMHelper().fetchCorrectMailDataList1(entityType, correctMailData, entityContacts, connectionSpace, cIdAll, organizationnameMap ,emailContant);
	 //System.out.println("diaplayListOnPage:" + diaplayListOnPage.size());

	// For Employee
	/*if (employee != null && !employee.trim().isEmpty())
	{
	new CRMHelper().fetchCorrectDataListForEmployee(correctMailData, employee);
	}*/

	if(contactId != null && contactId.equalsIgnoreCase(""))
	{
	List<String> listobj = new ArrayList<String>();
	List listcontact = getEmaildByContact(contactId);
	if(listcontact != null && listcontact.size()>0)
	{
	for (Iterator iterator = listcontact.iterator(); iterator
	.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	//listobj.add(object[1].toString());
	correctMailData.put(object[1].toString(), object[2].toString());
	organizationnameMap.put(object[1].toString(), object[3].toString());
	}
	}
	
	//csvData = listobj.toArray(new String[listobj.size()]);
	}
	
	// CSV email ids
	if (email_id != null && !email_id.trim().isEmpty())
	{
	new CRMHelper().fetchCorrectDataListForEmailId(correctMailData, email_id);
	}

	}
	public String insertMailData()
	{
	String returnResult = ERROR;
	// System.out.println("entityType:" + entityType);
	// System.out.println("entityContacts:" + entityContacts);
	 boolean status=false;
	// Build email list
	communicationEmailId();
	int count = 0;
	if (!ValidateSession.checkSession()) return LOGIN;
	
	InsertDataTable ob = null;
	try
	{
	List<GridDataPropertyView> instant = Configuration
	.getConfigurationData(
	"mapped_instant_mail_configuration", accountID,
	connectionSpace, "", 0, "table_name",
	"instant_mail_configuration");
	//	System.out.println(data);
	if (instant != null && instant.size() > 0)
	{
	// create table query based on configuration
	List<TableColumes> tableColume = new ArrayList<TableColumes>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	for (GridDataPropertyView gdp : instant)
	{
	TableColumes ob1 = new TableColumes();
	ob1 = new TableColumes();
	ob1.setColumnname(gdp.getColomnName());
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	}
	//	System.out.println("test 11");
	List paramList = new ArrayList<String>();
	int paramValueSize = 0;
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	if (requestParameterNames != null
	&& requestParameterNames.size() > 0)
	{
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	boolean statusTemp = true;
	//	System.out.println("test 22");
	while (it.hasNext())
	{
	String parmName = it.next().toString();
	String paramValue = request.getParameter(parmName);
	//	System.out.println("Param Name >>"+parmName+"   Param Value >>"+paramValue);
	if (paramValue != null && parmName != null)
	{
	if (!parmName.equalsIgnoreCase("department_id")
	&& !parmName.equalsIgnoreCase("__multiselect_department_id") && !parmName.equalsIgnoreCase("data") && !parmName.equalsIgnoreCase("frequencys") && !parmName.equalsIgnoreCase("hours") && !parmName.equalsIgnoreCase("date")
	&& !parmName.equalsIgnoreCase("__multiselect_designation_id"))
	{
	//	System.out.println(parmName);	
	paramList.add(parmName);
	if (statusTemp)
	{
	String tempParamValueSize[] = request.getParameterValues(parmName);
	for (String H : tempParamValueSize)
	{
	// counting one time size of the parameter
	// value
	if (H != null)
	paramValueSize++;
	}
	statusTemp = false;
	    }
	   }
	 }
	   	if (parmName
	.equalsIgnoreCase("__multiselect_check_list"))
	{
	}
	else if (parmName.equalsIgnoreCase("check_list"))
	{
	contactId = getCheck_list();
	   //     System.out.println(contactId);
	}
	else if (parmName.equalsIgnoreCase("groupName"))
	{
	groupId = paramValue;
	//	System.out.println(groupId);	
	}
	else if (parmName.equalsIgnoreCase("subject"))
	{
	subject = paramValue;
	//	System.out.println(subject);
	}
	else if (parmName.equalsIgnoreCase("from_email"))
	{
	from_email = paramValue;
	//	System.out.println(from_email);
	}
	else if (parmName.equalsIgnoreCase("email_id"))
	{
	email_id = paramValue;
	//	System.out.println(email_id);
	}
	else if (parmName.equalsIgnoreCase("writemail"))
	{
	writemail = paramValue;
	//	System.out.println(writemail);
	}
	else if (parmName.equalsIgnoreCase("frequencys"))
	{
	frequencys = paramValue;
	//	System.out.println(frequencys);
	}
	else if (parmName.equalsIgnoreCase("date"))
	{
	date = paramValue;
	System.out.println(date);
	}
	else if (parmName.equalsIgnoreCase("hours"))
	{
	hours = paramValue;
	System.out.println(hours);
	}
	else if (parmName.equalsIgnoreCase("day"))
	{
	day = paramValue;
	System.out.println(day);
	}
	}
	
	if (docs != null && docsFileName != null)
	{
	String filePath = new CreateFolderOs()
	.createUserDir("maildocument");
	StringBuffer str = new StringBuffer();
	int k = 1;
	for (int i = 0; i < docs.length; i++)
	{
	if (k < docs.length)
	{
	File fileToCreate = new File(filePath + "//"
	+ docsFileName[i]);
	FileUtils.copyFile(docs[i], fileToCreate);
	FilesUtil.saveFile(docs[i], docsFileName[i],
	filePath + File.separator);
	str.append(docsFileName[i] + ",");
	}
	else
	{
	File fileToCreate = new File(filePath + "//"
	+ docsFileName[i]);
	FileUtils.copyFile(docs[i], fileToCreate);
	FilesUtil.saveFile(docs[i], docsFileName[i],
	filePath + File.separator);
	str.append(docsFileName[i]);
	k++;
	}
	}
	setDocfilename(str.toString());
	}

	// for image storage in folder CRM_ImageData.
	if (crmImage != null && crmImageFileName != null)
	{
	filePath = new CreateFolderOs().createUserDir("CRM_ImageData");
	File fileToCreate = new File(filePath, crmImageFileName);
	FileUtils.copyFile(crmImage, fileToCreate);
	filePath = filePath+"//"+crmImageFileName;
	//System.out.println("filePath   "+filePath);
	setFilePath(filePath);
	}
	
	if (frequencys != null && !frequencys.equalsIgnoreCase(""))
	{
	if (frequencys.equals("One-Time"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	//	setDay(day);
	}
	if (frequencys.equals("Daily"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
//	setDay(day);
	}
	if (frequencys.equals("Weekly"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	setDay(day);
	}
	if (frequencys.equals("Monthly"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	//	setDay(day);
	}
	if (frequencys.equals("Yearly"))
	{
	setFrequencys(frequencys);
	setDate(date);
	setHours(hours);
	//	setDay(day);
	     	}
	    	}
	}
	System.out.println(date+" >>>>>>>  "+hours+"  >>>>>>>  "+day);
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
	
	boolean valueSelect = false;
	for (int i = 0; i < paramValueSize; i++)
	{
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
	ob = new InsertDataTable();
	if (!paramList.get(k).toString().equalsIgnoreCase("department_id")
	&& !paramList.get(k).toString().equalsIgnoreCase("__multiselect_department_id") && !paramList.get(k).toString().equalsIgnoreCase("writemail"))
	{
	// set all contact detail into object
	/*	if(paramList.get(k).toString().equalsIgnoreCase("writemail"))
	{
	if (data != null)
	{
	String ar[]=data.split("__");
	for(int j=0;j<ar.length;j++)
	{
	System.out.println(ar[j]);
	String arr[]=ar[j].toString().split(",");
	if(arr[0]!=null && arr[3]!=null)
	{
	String writemailtext=writemail.replaceAll("&lt;1&gt;", arr[0]);
	writemailtext=writemailtext.replaceAll("&lt;2&gt;", arr[3]);
	}
	ob = new InsertDataTable();
	ob.setColName("mail_text");
	ob.setDataName(parmValuew[k][i]);
	insertData.add(ob);
	}
	}
	}
	else{
	*/	ob = new InsertDataTable();
	ob.setColName(paramList.get(k).toString());
	ob.setDataName(parmValuew[k][i]);
	insertData.add(ob);
	/*	}*/
	}
	}
	}
	if (valueSelect)
	{

	    ob = new InsertDataTable();
	ob.setColName("groupName");
	ob.setDataName(groupId);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("status");
	ob.setDataName("Pending");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("update_date");
	ob.setDataName("0000:00:00");
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("update_time");
	ob.setDataName("0000:00:00");
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("flag");
	ob.setDataName("0");
	insertData.add(ob);
	
	System.out.println("***********************"+DateUtil.getdateofcurrentweekdays(day));
	
	ob = new InsertDataTable();
	ob.setColName("module");
	ob.setDataName("WFPM");
	insertData.add(ob);
	
	if (filePath != null && !filePath.equalsIgnoreCase(""))
	{
	ob = new InsertDataTable();
	ob.setColName("attachment");
	ob.setDataName(filePath);
	insertData.add(ob);
	}
	else{
	ob = new InsertDataTable();
	ob.setColName("attachment");
	ob.setDataName("NA");
	insertData.add(ob);
	}
	
	  if(frequencys!=null && !frequencys.equalsIgnoreCase(""))
	  {
	if(frequencys.equals("Weekly"))
	{
	String currentDay= DateUtil.getDayofDate(DateUtil.getCurrentDateUSFormat());
	int j=1;
	String a=null;
	while (!currentDay.equalsIgnoreCase(day))
	{
	a=DateUtil.getNewDate("day", j, DateUtil.getCurrentDateUSFormat());
	currentDay= DateUtil.getDayofDate(a);
	j++;
	}
	if(a!=null)
	{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(a);
	insertData.add(ob);
	}
	else{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);
	}
	
	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(getHours());
	insertData.add(ob);	
	}
	else
	 {	
	if(getHours()!=null && getHours()!="" && getDate()!=null && getDate()!="")
	{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(getDate());
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(getHours());
	insertData.add(ob);	
	}
	 }
	}
	else
	{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(DateUtil.getCurrentTimeHourMin());
	insertData.add(ob);	
	}
	}
	if (frequencys != null && !frequencys.equalsIgnoreCase(""))
	{
	String mailType = "Schedule";
	ob = new InsertDataTable();
	ob.setColName("frequency");
	ob.setDataName(frequencys);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("mail_type");
	ob.setDataName(mailType);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("emailid");
	
	}
	else
	{
	String mailType = "Instant";
	ob = new InsertDataTable();
	ob.setColName("mail_type");
	ob.setDataName(mailType);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("emailid");
	}

	InsertDataTable ob1;
	InsertDataTable ob2;
	InsertDataTable ob3;
	InsertDataTable ob4;
	System.out.println(data);	
	if (data != null)
	{
	String ar[]=data.split("__");
	for(int j=0;j<ar.length;j++)
	{
	System.out.println(ar[j]);
	String arr[]=ar[j].toString().split(",");
	ob1 = new InsertDataTable();             
	ob1.setColName("name");
	System.out.println(arr[0]+"  "+arr[1]+"   "+arr[2]);
	if(arr[0]!=null)
	{
	ob1.setDataName(arr[0]);	
	}
	else
	{
	ob1.setDataName("NA");
	}
	insertData.add(ob1);
	
	ob2 = new InsertDataTable();             
	ob2.setColName("dept");
	if(arr[1]!=null)
	{
	ob2.setDataName(arr[1]);	
	}
	else
	{
	ob2.setDataName("NA");
	}
	insertData.add(ob2);
	
	ob3 = new InsertDataTable();            
	ob3.setColName("emailid");
	if(arr[2]!=null)
	{
	ob3.setDataName(arr[2]);	
	}
	else
	{
	ob3.setDataName("NA");
	}
	insertData.add(ob3);
	
	ob4 = new InsertDataTable();  
	if(paramList.contains("writemail"))
	{	
	ob4 = new InsertDataTable();            
	ob4.setColName("mail_text");
	if(arr[0]!=null && arr[3]!=null)
	{
	String writemailtext=writemail.replaceAll("&lt;1&gt;", arr[0]);
	writemailtext=writemailtext.replaceAll("&lt;2&gt;", arr[3]);
	ob4.setDataName(writemailtext);
	}
	else
	{
	String writemailtext=writemail.replaceAll("&lt;1&gt;"," ");
	writemailtext=writemailtext.replaceAll("&lt;2&gt;"," ");
	ob4.setDataName(writemailtext);
	}
	}
	insertData.add(ob4);
	
	 status = cbt.insertIntoTable("instant_mail", insertData, connectionSpace);
	 insertData.remove(ob1);
	 insertData.remove(ob2);
	 insertData.remove(ob3);
	 insertData.remove(ob4);
	}
	    	  }
	     	    }
	      }
	if(status)
	 {
	 addActionMessage(" Mail Send Successfully.");
	 returnResult=SUCCESS;	 
	 }
	 }
	catch (Exception e)
	{
	returnResult = ERROR;
	e.printStackTrace();
	}
	//	System.out.println(">>>>>>>>>>>>>>. Result Type:  " + returnResult);
	return returnResult;
	
	}
	
	public String insertConfirmedMailData()
	{
	String returnResult = ERROR;
	communicationEmailId();
	int count = 0;
	if (!ValidateSession.checkSession()) return LOGIN;
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	try
	{
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommonforClassdata obhj = new CommonOperAtion();

	boolean status = false;
	
	//	System.out.println("writemail>"+writemail);
	
	
	for (Map.Entry<String, String> entry : correctMailData.entrySet())
	{
	//	System.out.println("##:" + entry.getKey());
	
	ob = new InsertDataTable();
	ob.setColName("groupName");
	ob.setDataName(groupId);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("status");
	ob.setDataName("Pending");
	insertData.add(ob);

	//	System.out.println("################3333"+frequencys.length());
	if(frequencys.length() == 0)
	{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(DateUtil.getCurrentDateIndianFormat());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(DateUtil.getCurrentTimeHourMin());
	insertData.add(ob);
	}
	else{
	if(frequencys.equals("Weekly"))
	{
	String currentDay= DateUtil.getDayofDate(DateUtil.getCurrentDateUSFormat());
	int j=1;
	String a=null;
	while (!currentDay.equalsIgnoreCase(day))
	{
	a=DateUtil.getNewDate("day", j, DateUtil.getCurrentDateUSFormat());
	currentDay= DateUtil.getDayofDate(a);
	j++;
	}
	if(a!=null)
	{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(a);
	insertData.add(ob);
	}
	else{
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);
	}
	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(getHours());
	insertData.add(ob);	
	}
	else
	   {	
	if(getHours()!=null && getHours()!="" && getDate()!=null && getDate()!="")
	{
	//	System.out.println("TEST 2");
	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(getDate());
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("time");
	ob.setDataName(getHours());
	insertData.add(ob);
	}
	  }
	}

	ob = new InsertDataTable();
	ob.setColName("userName");
	ob.setDataName(userName);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("flag");
	ob.setDataName("0");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("subject");
	ob.setDataName(subject);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("update_date");
	ob.setDataName("0000:00:00");
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("from_email");
	ob.setDataName(from_email);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("update_time");
	ob.setDataName("0000:00:00");
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("module");
	ob.setDataName("CRM");
	insertData.add(ob);
	
	if (filePath != null && !filePath.equalsIgnoreCase(""))
	{
	ob = new InsertDataTable();
	ob.setColName("attachment");
	ob.setDataName(filePath);
	insertData.add(ob);
	}
	else{
	ob = new InsertDataTable();
	ob.setColName("attachment");
	ob.setDataName("NA");
	insertData.add(ob);
	}


	if (frequencys != null && !frequencys.equalsIgnoreCase(""))
	{
	String mailType = "Schedule";
	ob = new InsertDataTable();
	ob.setColName("frequency");
	ob.setDataName(frequencys);
	insertData.add(ob);

	/*ob = new InsertDataTable();
	ob.setColName("hours");
	ob.setDataName(getHours());
	insertData.add(ob);*/

	/*ob = new InsertDataTable();
	ob.setColName("scheduled_date");
	ob.setDataName(getDate());
	insertData.add(ob);*/

	ob = new InsertDataTable();
	ob.setColName("day");
	ob.setDataName(getDay());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("mail_type");
	ob.setDataName(mailType);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("emailid");
	
	}
	else
	{
	String mailType = "Instant";
	ob = new InsertDataTable();
	ob.setColName("mail_type");
	ob.setDataName(mailType);
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("emailid");
	
	}
	ob.setDataName(entry.getKey());
	insertData.add(ob);
	
	ob = new InsertDataTable();
	ob.setColName("name");
	ob.setDataName(entry.getValue());
	insertData.add(ob);
	
	// put real value at place of tags.
	
	
	for (Map.Entry<String, String> orgentry : organizationnameMap.entrySet())
	{
	if(entry.getKey().equalsIgnoreCase(orgentry.getKey()))
	{
	//	System.out.println("entry.getKey()Mail Name:::::"+entry.getKey());
	//	System.out.println("entry.getKey()Person Name:::::"+entry.getValue());
	
	//	System.out.println("orgentry.getKey()Mail org Name:::::"+orgentry.getKey());
	//	System.out.println("orgentry.getKey()Org Name:::::"+orgentry.getValue());
	
	String writemailtext=writemail.replaceAll("&lt;1&gt;", entry.getValue());
	writemailtext=writemailtext.replaceAll("&lt;2&gt;", orgentry.getValue());
	
	ob = new InsertDataTable();
	ob.setColName("mail_text");
	ob.setDataName(writemailtext);
	insertData.add(ob);
	//	System.out.println("after replace  >"+writemailtext);
	}
	}
	
	
	/*ob = new InsertDataTable();
	ob.setColName("mail_text");
	ob.setDataName(writemail);
	insertData.add(ob);*/
	
	status = coi.insertIntoTable("instant_mail", insertData, connectionSpace);
	
	//	System.out.println("status>"+status);
	if (status) count++;
	insertData.clear();
	
	}
	if (status)
	{
	addActionMessage("Mail Send Successfully.");
	returnResult = SUCCESS;
	}

	}
	catch (Exception e)
	{
	returnResult = ERROR;
	e.printStackTrace();
	}
	//System.out.println(">>>>>>>>>>>>>>. Result Type:  " + returnResult);
	return returnResult;
	}
	
	private List getEmaildByContact(String contactId)
	{
	try
	{
	if(contactId != null && !contactId.equalsIgnoreCase(""))
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String query1 = "select id,emailIdOne,empName, orgName from employee_basic where id in ( "
	+ contactId + " )";
	return cbt.executeAllSelectQuery(query1, connectionSpace);
	}
	
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return null;
	}
	
	private List getEmailidByContact(String contactId)
	{
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String query1 = "select id,emailIdOne,empName from employee_basic where id in ( "
	+ contactId + " )";
	return cbt.executeAllSelectQuery(query1, connectionSpace);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return null;
	}
	
	private List getEmailidBygroup(int groupId)
	{
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String query1 = "select contbale.id,contbale.emailIdOne,contbale.empName from employee_basic as contbale "
	+ " where  contbale.groupId=" + groupId;
	return cbt.executeAllSelectQuery(query1, connectionSpace);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return null;
	}
	
	/*private List getDataBygroup(int groupId,int id)
	{
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query1=new StringBuilder();
	query1.append("SELECT query FROM group_name where id in("+groupId+")");
	System.out.println(query1.toString());
	List<Object> Listhb = new ArrayList<Object>();
	List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	for(int i=0;i<dataCount.size();i++)
	{
	String query=dataCount.get(i).toString();
	System.out.println(query.toString());
	List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	
	String query1 = "select "
	+ " where  contbale.groupId=" + groupId;
	
	
	
	String query1 = "select contbale.id,contbale.emailIdOne,contbale.empName from employee_basic as contbale "
	+ " where  contbale.groupId=" + groupId;
	return cbt.executeAllSelectQuery(query1, connectionSpace);
	  }
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return null;
	}
	*/
	
	@SuppressWarnings("unchecked")
	private boolean isMobileBlacklisted(String email_id)
	{
	boolean flag = false;
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	SessionFactory connectionSpace = (SessionFactory) session
	.get("connectionSpace");
	String query = "SELECT email_id FROM blacklist";
	List data = cbt.executeAllSelectQuery(query, connectionSpace);

	if (data != null && data.size() > 0)
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object object = (Object) iterator.next();
	if (object != null)
	{
	if (object.toString().equalsIgnoreCase(email_id))
	{
	flag = true;
	}
	}
	}
	}
	}
	catch (Exception ex)
	{
	ex.printStackTrace();
	}

	return flag;
	}
	/**
	 * create CRM Group for communication.
	 * 
	 * */
	public String createCRMGruop()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	// Template root
	/*messagerootList = new HashMap<String, String>();
	messagerootList.put("Promo", "Promo");
	messagerootList.put("Trans", "Transactional");
	messagerootList.put("Open", "Open");

	// Sales dept Employee list
	employeeList = new EmployeeHelper().fetchEmpWithMobile();   */

	// Group
	// System.out.println("groupName:" + groupName);
	// System.out.println("chkGroup:" + chkGroup);
	if (chkGroup)
	{
	// System.out.println("autoGroup:" + autoGroup);
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	boolean status = false;
	ob = new InsertDataTable();
	ob.setColName("name");
	ob.setDataName(groupName);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("comment");
	ob.setDataName("CRM Group");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("user");
	ob.setDataName(userName);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("type");
	ob.setDataName("AUTO");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("query");
	ob.setDataName(autoGroup);
	insertData.add(ob);

	status = coi.insertIntoTable("group_name", insertData, connectionSpace);
	if(status)
	{
	addActionMessage(" Group Created Successfully.");
	}else 
	{
	addActionMessage(" There is some problem in group creation.");
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}
	public String beforeFetchCRMDataView()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	if (entityType == EntityType.LEAD || entityType == EntityType.ALL)
	{
	count = leadDataCount();
	}
	if (entityType == EntityType.CLIENT || entityType == EntityType.ALL)
	{
	if (entityType == EntityType.ALL) count += clientDataCount();
	else count = clientDataCount();
	}
	if (entityType == EntityType.ASSOCIATE || entityType == EntityType.ALL)
	{
	if (entityType == EntityType.ALL) count += associateDataCount();
	else count = associateDataCount();
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String fetchTemplate()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	templateJSONArray = new JSONArray();
	JSONObject listObject;
	StringBuilder query = new StringBuilder();
	query.append("SELECT id,template_name FROM createtemplate where template_type='");
	query.append(templateType);
	query.append("' and  status='Approve'");

	List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (data != null && !data.isEmpty())
	{
	for (Iterator iterator = data.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	listObject = new JSONObject();
	listObject.put("ID", CommonHelper.getFieldValue(object[0]));
	listObject.put("NAME", CommonHelper.getFieldValue(object[1]));
	templateJSONArray.add(listObject);
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String templateSubmission()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	StringBuilder listForTemp = new StringBuilder();
	listForTemp.append("select template from createtemplate WHERE id= '");
	listForTemp.append(id);
	listForTemp.append("' ");

	String StoredTemp = null;
	List data = coi.executeAllSelectQuery(listForTemp.toString(), connectionSpace);
	if (data != null && data.size() > 0)
	{
	StoredTemp = data.get(0).toString();
	}
	// get pattern from valid String.
	Pattern pattern = Pattern.compile("<.+?>", Pattern.CASE_INSENSITIVE);
	// matching String from matcher
	Matcher matcher = pattern.matcher(StoredTemp);
	int temp = 0;
	int endIndex = 0;
	tempList = new ArrayList<ConfigurationUtilBean>();
	ConfigurationUtilBean ob = null;
	ConfigurationUtilBean ob1 = null;

	while (matcher.find())
	{
	ob = new ConfigurationUtilBean();
	ob1 = new ConfigurationUtilBean();

	String str = StoredTemp.substring(temp, matcher.start());
	String strTemp = StoredTemp.substring(matcher.start(), matcher.end());
	temp = matcher.end();
	endIndex = matcher.end();
	if (str != null)
	{
	ob.setKey(str);
	tempList.add(ob);
	}
	if (strTemp != null)
	{
	ob1.setDefault_value(strTemp.substring(strTemp.indexOf("(") + 1, strTemp.indexOf(")")));
	ob1.setKey("textfield");
	tempList.add(ob1);
	}
	}
	ob = new ConfigurationUtilBean();
	ob.setKey(StoredTemp.substring(endIndex, StoredTemp.length()));
	tempList.add(ob);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String fetchMessage()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	msgObj = new InstantMessageBean();
	if (message != null)
	{
	StringBuilder query = new StringBuilder();
	query.append("SELECT template FROM createtemplate where id='");
	query.append(message);
	query.append("'");

	List getlist2 = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if (getlist2 != null && getlist2.size() > 0)
	{
	messageName = CommonHelper.getFieldValue(getlist2.get(0));
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	public String insertDataInstantMsg()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	// System.out.println("entityType:" + entityType);
	// System.out.println("employee:" + employee);
	// System.out.println("mobileNo:" + mobileNo);
	// System.out.println("root:" + root);
	// System.out.println("templateid:" + templateid);
	// System.out.println("writeMessage:" + writeMessage);
	// System.out.println("frequencys:" + frequencys);
	// System.out.println("date:" + date);
	// System.out.println("hours:" + hours);
	// System.out.println("day:" + day);
	// System.out.println("entityContacts:" + entityContacts);

	try
	{
	date = CommonHelper.getCommaSeparatedToArray(date)[0];
	}
	catch (Exception e)
	{
	date = "NA";
	}
	// Build numbers list
	commonCall();

	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	public String sendMessage()
	{
	try
	{
	if (!ValidateSession.checkSession()) return LOGIN;

	// System.out.println("entityType:" + entityType);
	//	 System.out.println("employee:" + employee);
	//	 System.out.println("mobileNo:" + mobileNo);
	//	 System.out.println("root:" + root);
	//	 System.out.println("templateid:" + templateid);
	//	 System.out.println("writeMessage:" + writeMessage);
	//	 System.out.println("frequencys:" + frequencys);
	//	 System.out.println("date:" + date);
	//	 System.out.println("hours:" + hours);
	//	 System.out.println("day:" + day);
	//	 System.out.println("entityContacts:" + entityContacts);

	// Build numbers list
	commonCall();
	
	int count = 0;
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	boolean status = false;
	ob = new InsertDataTable();
	ob.setColName("msg");
	ob.setDataName(writeMessage);
	insertData.add(ob);

	//	 System.out.println("msg:" + writeMessage);
	
	ob = new InsertDataTable();
	ob.setColName("grp_name");
	ob.setDataName("NA");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("client_id");
	ob.setDataName("dredst22");
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("status");
	ob.setDataName(0);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("msg_date");
	ob.setDataName(DateUtil.getCurrentDateUSFormat());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("msg_time");
	ob.setDataName(DateUtil.getCurrentTime());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("smscount");
	ob.setDataName(writeMessage.length());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("user");
	ob.setDataName(userName);
	insertData.add(ob);
	//	System.out.println("11111111>>>>>>");
	if (frequencys != null && !frequencys.equalsIgnoreCase("") && !frequencys.equalsIgnoreCase("NONE") )
	{
	String msgType = "Schedule";
	ob = new InsertDataTable();
	ob.setColName("frequencys");
	ob.setDataName(frequencys);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("hours");
	ob.setDataName(getHours());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("date");
	ob.setDataName(getDate());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("day");
	ob.setDataName(getDay());
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("msgType");
	ob.setDataName(msgType);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("messageroot");
	ob.setDataName(root);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("msisdn");
	//insertData.add(ob);
	//	System.out.println("msgType>"+msgType);
	}
	else
	{
	String msgType = "Instant";
	ob = new InsertDataTable();
	ob.setColName("msgType");
	ob.setDataName(msgType);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("messageroot");
	ob.setDataName(root);
	insertData.add(ob);

	ob = new InsertDataTable();
	ob.setColName("msisdn");
	//insertData.add(ob);
	//	System.out.println("msgType>"+msgType);
	}

	for (Map.Entry<String, String> entry : correctData.entrySet())
	{
	//	System.out.println("##:" + entry.getKey());
	ob.setDataName(entry.getKey());
	insertData.add(ob);
	status = coi.insertIntoTable("msg_stats", insertData, connectionSpace);
	//	System.out.println("status>"+status);
	if (status) count++;
	}

	addActionMessage(count + " SMS Send Successfully.");
	// System.out.println("count:" + count);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return SUCCESS;
	}

	public void commonCall()
	{
	correctData = new LinkedHashMap<String, String>();
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	// For Lead/ Client/ Associate
	new CRMHelper().fetchCorrectDataList(entityType, correctData, entityContacts, connectionSpace, cIdAll);

	// For Employee
	if (employee != null && !employee.trim().isEmpty())
	{
	new CRMHelper().fetchCorrectDataListForEmployee(correctData, employee);
	}

	// CSV mobile numbers
	if (mobileNo != null && !mobileNo.trim().isEmpty())
	{
	new CRMHelper().fetchCorrectDataListFormobileNo(correctData, mobileNo);
	}
	}

	public void communicationEmailId()
	{
	correctMailData = new LinkedHashMap<String, String>();
	organizationnameMap = new LinkedHashMap<String, String>();
	String cIdAll = new CommonHelper().getHierarchyContactIdByEmpId(empIdofuser);
	// For Lead/ Client/ Associate
	new CRMHelper().fetchCorrectMailDataList(entityType, correctMailData, entityContacts, connectionSpace, cIdAll, organizationnameMap);

	// For Employee
	/*if (employee != null && !employee.trim().isEmpty())
	{
	new CRMHelper().fetchCorrectDataListForEmployee(correctMailData, employee);
	}*/

	if(contactId != null && contactId.equalsIgnoreCase(""))
	{
	List<String> listobj = new ArrayList<String>();
	List listcontact = getEmaildByContact(contactId);
	if(listcontact != null && listcontact.size()>0)
	{
	for (Iterator iterator = listcontact.iterator(); iterator
	.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	//listobj.add(object[1].toString());
	correctMailData.put(object[1].toString(), object[2].toString());
	organizationnameMap.put(object[1].toString(), object[3].toString());
	}
	}
	
	//csvData = listobj.toArray(new String[listobj.size()]);
	}
	
	// CSV email ids
	if (email_id != null && !email_id.trim().isEmpty())
	{
	new CRMHelper().fetchCorrectDataListForEmailId(correctMailData, email_id);
	}
	
	
	}
	
	public String beforeSummaryReport()
	{
	return SUCCESS;
	}
	
	public String beforeMailTagPage()
	{
	if (!ValidateSession.checkSession())
	return LOGIN;
	return SUCCESS;
	}
	
	public String showMailTagData()
	{
	if (!ValidateSession.checkSession())
	return LOGIN;
	try {
	
	setMailTagViewProp();
	
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: showMailTagData of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public void setMailTagViewProp()
	{
	GridDataPropertyView gpv = new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	mailTagViewProp.add(gpv);
	List<GridDataPropertyView> returnResult = Configuration
	.getConfigurationData("mapped_dynamic_emailtag_configuration",
	accountID, connectionSpace, "", 0, "table_name",
	"dynamic_emailtag_configuration");
	
	for (GridDataPropertyView gdp : returnResult) {
	
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
	mailTagViewProp.add(gpv);
	}
	
	}
	
	public String viewMailTagDataGrid()
	{
	
	try {
	if (!ValidateSession.checkSession())
	return LOGIN;

	CommonOperInterface cbt = new CommonConFactory().createInterface();
	StringBuilder query1 = new StringBuilder("");
	query1.append("select count(*) from dynamic_emailtag_data ");
	
	List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	if (dataCount.size()>=0) {
	BigInteger count = new BigInteger("3");
	for (Iterator it = dataCount.iterator(); it.hasNext();) {
	Object obdata = (Object) it.next();
	count = (BigInteger) obdata;
	}
	setRecords(count.intValue());
	setRows(count.intValue());
	int to = (getRows() * getPage());
	if (to > getRecords())
	to = getRecords();
	// getting the list of colmuns
	StringBuilder query = new StringBuilder("");
	StringBuilder queryTemp = new StringBuilder("");
	queryTemp.append("select ");
	StringBuilder queryEnd = new StringBuilder("");
	queryEnd.append(" from dynamic_emailtag_data as dmt ");

	List fieldNames = Configuration.getColomnList(
	"mapped_dynamic_emailtag_configuration", accountID,
	connectionSpace, "dynamic_emailtag_configuration");
	List<Object> Listhb = new ArrayList<Object>();
	for (Iterator it = fieldNames.iterator(); it.hasNext();) {
	// generating the dyanamic query based on selected fields
	Object obdata = (Object) it.next();
	if (obdata != null) {
	
	if (obdata.toString().equalsIgnoreCase("userName")) {
	          queryTemp.append(" empb.empName, ");
	          queryEnd.append(" left join compliance_contacts as cct on dmt.userName = cct.id ");
	          queryEnd.append(" left join employee_basic as empb on cct.emp_id = empb.id ");
	       }else
	       {
	    	   queryTemp.append("dmt." + obdata.toString() + ",");
	       }
	
	}
	}
	query.append(queryTemp.toString().substring(0,queryTemp.toString().lastIndexOf(",")));
	query.append(" ");
	query.append(queryEnd.toString());
	
	if (getSearchField() != null && getSearchString() != null&& !getSearchField().equalsIgnoreCase("")&& !getSearchString().equalsIgnoreCase("")&& !getSearchField().equalsIgnoreCase("undefined")) {
	// add search query based on the search operation
	query.append(" and ");

	if (getSearchOper().equalsIgnoreCase("eq")) {
	query.append(" " + getSearchField() + " = '"
	+ getSearchString() + "'");
	} else if (getSearchOper().equalsIgnoreCase("cn")) {
	query.append(" " + getSearchField() + " like '%"
	+ getSearchString() + "%'");
	} else if (getSearchOper().equalsIgnoreCase("bw")) {
	query.append(" " + getSearchField() + " like '"
	+ getSearchString() + "%'");
	} else if (getSearchOper().equalsIgnoreCase("ne")) {
	query.append(" " + getSearchField() + " <> '"
	+ getSearchString() + "'");
	} else if (getSearchOper().equalsIgnoreCase("ew")) {
	query.append(" " + getSearchField() + " like '%"
	+ getSearchString() + "'");
	}
	}
	if (getSord() != null && !getSord().equalsIgnoreCase("")) {
	if (getSord().equals("asc") && getSidx() != null
	&& !getSidx().equals("")) {
	query.append(" order by " + getSidx());
	}
	if (getSord().equals("desc") && getSidx() != null
	&& !getSidx().equals("")) {
	query.append(" order by " + getSidx() + " DESC");
	}
	}
	
	cbt.checkTableColmnAndAltertable(fieldNames,
	"dynamic_emailtag_data", connectionSpace);

	// System.out.println("query:::" + query);
	List data = cbt.executeAllSelectQuery(query.toString(),
	connectionSpace);
	
	if (data.size()>=0 ) {
	for (Iterator it = data.iterator(); it.hasNext();) {
	Object[] obdata = (Object[]) it.next();
	Map<String, Object> one = new HashMap<String, Object>();
	
	for (int k = 0; k < fieldNames.size(); k++) {
	if (obdata[k] != null) {
	if (k == 0) {
	one.put(fieldNames.get(k).toString(),
	(Integer) obdata[k]);
	} else{
	one.put(fieldNames.get(k).toString(),obdata[k].toString());
	}
	}
	}
	Listhb.add(one);
	}
	setMailTagViewList(Listhb);
	setTotal((int) Math.ceil((double) getRecords()
	/ (double) getRows()));
	}
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	public String beforeAddMailTagData()
	{
	if (!ValidateSession.checkSession())
	return LOGIN;
	return SUCCESS;
	}
	
	public String addMailTagData()
	{
	if (!ValidateSession.checkSession())
	return LOGIN;
	try {
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	
	boolean status = false;
	
	if(checkname)
	{
	ob = new InsertDataTable();
	ob.setColName("name");
	ob.setDataName(name);
	insertData.add(ob);
	}
	
	if(organizationName)
	{
	ob = new InsertDataTable();
	ob.setColName("organization_name");
	ob.setDataName(organization_name);
	insertData.add(ob);
	}
	
	ob = new InsertDataTable();
	ob.setColName("userName");
	ob.setDataName(cId);
	insertData.add(ob);
	
	status = coi.insertIntoTable("dynamic_emailtag_data", insertData, connectionSpace);
	//System.out.println("status>"+status+"  cId  >"+cId);
	
	if (status) 
	{
	addActionMessage(" Tag added successfully.");
	}
	} catch (Exception e) {
	log
	.error(
	"Date : "
	+ DateUtil.getCurrentDateIndianFormat()
	+ " Time: "
	+ DateUtil.getCurrentTime()
	+ " "
	+ "Problem in Over2Cloud  method: addMailTagData of class "
	+ getClass(), e);
	e.printStackTrace();
	}
	
	
	return SUCCESS;
	}
	
	public String loadMailTagData()
	{
	if (!ValidateSession.checkSession()) return LOGIN;
	try {
	StringBuilder query = new StringBuilder();
	
	query.append("select name, organization_name, com_name, address, mobile_number,tag_type from dynamic_emailtag_data where userName = '");
	query.append(cId);
	query.append("'");
	List dataCount = coi.executeAllSelectQuery(query.toString(), connectionSpace);
	if(dataCount != null && dataCount.size()>0)
	{
	for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
	{
	Object[] object 	= (Object[]) iterator.next();
	name	  = object[0].toString();
	organization_name = object[1].toString();
	com_name	  = object[2].toString();
	address 	  = object[3].toString();
	mobileNo 	  = object[4].toString();
	tag_type	      = object[5].toString();
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	
	return SUCCESS;
	}
	
	public String beforeMailEditor()
	{
	if (!ValidateSession.checkSession())
	return LOGIN;
	
	return SUCCESS;
	}
	
	public String addComposedMailtext()
	{
	if (!ValidateSession.checkSession())
	return LOGIN;
	try {
	
	List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
	InsertDataTable ob = null;
	boolean status = false;
	
	ob = new InsertDataTable();
	ob.setColName("mail_text");
	ob.setDataName(writemail);
	insertData.add(ob);
	
	status = coi.insertIntoTable("instant_mail", insertData, connectionSpace);
	if(status)
	{
	addActionMessage(" Mail added successfully.");
	}
	else
	{
	addActionMessage(" There is some problem in mail addition.");
	}
	
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public void setJsonArrayIndustry(JSONArray jsonArrayIndustry)
	{
	this.jsonArrayIndustry = jsonArrayIndustry;
	}

	public JSONArray getJsonArrayIndustry()
	{
	return jsonArrayIndustry;
	}

	public void setJsonArraySource(JSONArray jsonArraySource)
	{
	this.jsonArraySource = jsonArraySource;
	}

	public JSONArray getJsonArraySource()
	{
	return jsonArraySource;
	}

	public void setJsonArrayLocation(JSONArray jsonArrayLocation)
	{
	this.jsonArrayLocation = jsonArrayLocation;
	}

	public JSONArray getJsonArrayLocation()
	{
	return jsonArrayLocation;
	}

	public void setJsonArrayAccMgr(JSONArray jsonArrayAccMgr)
	{
	this.jsonArrayAccMgr = jsonArrayAccMgr;
	}

	public JSONArray getJsonArrayAccMgr()
	{
	return jsonArrayAccMgr;
	}

	public void setRelationshipType(String relationshipType)
	{
	this.relationshipType = relationshipType;
	}

	public String getRelationshipType()
	{
	return relationshipType;
	}

	public void setEntityType(int entityType)
	{
	this.entityType = entityType == 3 ? EntityType.ALL : entityType == 0 ? EntityType.LEAD : entityType == 1 ? EntityType.CLIENT : EntityType.ASSOCIATE;
	}

	public int getEntityType()
	{
	if (entityType == null || entityType.ordinal() == 3) showMultiselect = false;
	else showMultiselect = true;
	return entityType == null ? -1 : entityType.ordinal();
	}

	public void setIndustry(String industry)
	{
	this.industry = industry;
	}

	public String getIndustry()
	{
	return industry;
	}

	public void setSubIndustry(String subIndustry)
	{
	this.subIndustry = subIndustry;
	}

	public String getSubIndustry()
	{
	return subIndustry;
	}

	public void setRating(String rating)
	{
	this.rating = rating;
	}

	public String getRating()
	{
	return rating;
	}

	public void setSource(String source)
	{
	this.source = source;
	}

	public String getSource()
	{
	return source;
	}

	public void setLocation(String location)
	{
	this.location = location;
	}

	public String getLocation()
	{
	return location;
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

	public void setCrmDataList(List<Object> crmDataList)
	{
	this.crmDataList = crmDataList;
	}

	public List<Object> getCrmDataList()
	{
	return crmDataList;
	}

	public void setRelationshipSubType(String relationshipSubType)
	{
	this.relationshipSubType = relationshipSubType;
	}

	public String getRelationshipSubType()
	{
	return relationshipSubType;
	}

	public void setCount(int count)
	{
	this.count = count;
	}

	public int getCount()
	{
	return count;
	}

	public void setJsonArraydepartment(JSONArray jsonArraydepartment)
	{
	this.jsonArraydepartment = jsonArraydepartment;
	}

	public JSONArray getJsonArraydepartment()
	{
	return jsonArraydepartment;
	}

	public void setJsonArrayDesignation(JSONArray jsonArrayDesignation)
	{
	this.jsonArrayDesignation = jsonArrayDesignation;
	}

	public JSONArray getJsonArrayDesignation()
	{
	return jsonArrayDesignation;
	}

	public void setDepartment(String department)
	{
	this.department = department;
	}

	public String getDepartment()
	{
	return department;
	}

	public void setDesignation(String designation)
	{
	this.designation = designation;
	}

	public String getDesignation()
	{
	return designation;
	}

	public void setInfluence(String influence)
	{
	this.influence = influence;
	}

	public String getInfluence()
	{
	return influence;
	}

	public void setAnniversaryFrom(String anniversaryFrom)
	{
	try
	{
	// //System.out.println("before_setAnniversaryFrom:" + anniversaryFrom);
	SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
	Date date = sdf1.parse(anniversaryFrom);
	SimpleDateFormat sdf = new SimpleDateFormat("MM");
	this.anniversaryFrom = sdf.format(date);
	// //System.out.println("after_setAnniversaryFrom:" +
	// this.anniversaryFrom);
	}
	catch (ParseException e)
	{
	e.printStackTrace();
	}
	}

	public String getAnniversaryFrom()
	{
	try
	{
	// //System.out.println("1 before_getAnniversaryFrom:" +
	// this.anniversaryFrom);
	if (this.anniversaryFrom == null || this.anniversaryFrom.isEmpty()) this.anniversaryFrom = DateUtil.getCurrentMonth() < 10 ? "0"
	+ DateUtil.getCurrentMonth() : String.valueOf(DateUtil.getCurrentMonth());
	// //System.out.println("2 before_getAnniversaryFrom:" +
	// this.anniversaryFrom);
	SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
	Date date = sdf1.parse(this.anniversaryFrom);
	SimpleDateFormat sdf = new SimpleDateFormat("MMM");
	this.anniversaryFrom = sdf.format(date);
	// //System.out.println("after_getAnniversaryFrom:" +
	// this.anniversaryFrom);
	}
	catch (ParseException e)
	{
	e.printStackTrace();
	}
	return anniversaryFrom;
	}

	public void setBirthdayFrom(String birthdayFrom)
	{
	try
	{
	// //System.out.println("before_setBirthdayFrom:" + birthdayFrom);
	SimpleDateFormat sdf1 = new SimpleDateFormat("MMM");
	Date date = sdf1.parse(birthdayFrom);
	SimpleDateFormat sdf = new SimpleDateFormat("MM");
	this.birthdayFrom = sdf.format(date);
	// //System.out.println("after_setBirthdayFrom:" + this.birthdayFrom);
	}
	catch (ParseException e)
	{
	e.printStackTrace();
	}
	}

	public String getBirthdayFrom()
	{
	try
	{
	// //System.out.println("1 before_getBirthdayFrom:" + this.birthdayFrom);
	if (this.birthdayFrom == null || this.birthdayFrom.isEmpty()) this.birthdayFrom = DateUtil.getCurrentMonth() < 10 ? "0" + DateUtil.getCurrentMonth()
	: String.valueOf(DateUtil.getCurrentMonth());
	// //System.out.println("2 before_getBirthdayFrom:" + this.birthdayFrom);
	SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
	Date date = sdf1.parse(this.birthdayFrom);
	SimpleDateFormat sdf = new SimpleDateFormat("MMM");
	this.birthdayFrom = sdf.format(date);
	// //System.out.println("after_getBirthdayFrom:" + this.birthdayFrom);
	}
	catch (ParseException e)
	{
	e.printStackTrace();
	}
	return this.birthdayFrom;
	}

	public void setChkBirthday(boolean chkBirthday)
	{
	this.chkBirthday = chkBirthday;
	}

	public boolean isChkBirthday()
	{
	return chkBirthday;
	}

	public void setChkAnniversary(boolean chkAnniversary)
	{
	this.chkAnniversary = chkAnniversary;
	}

	public boolean isChkAnniversary()
	{
	return chkAnniversary;
	}

	public void setMobileTextBox(List<ConfigurationUtilBean> mobileTextBox)
	{
	this.mobileTextBox = mobileTextBox;
	}

	public List<ConfigurationUtilBean> getMobileTextBox()
	{
	return mobileTextBox;
	}

	public void setWritemessageTextBox(List<ConfigurationUtilBean> writemessageTextBox)
	{
	this.writemessageTextBox = writemessageTextBox;
	}

	public List<ConfigurationUtilBean> getWritemessageTextBox()
	{
	return writemessageTextBox;
	}

	public void setMessageNameDropdown(List<ConfigurationUtilBean> messageNameDropdown)
	{
	this.messageNameDropdown = messageNameDropdown;
	}

	public List<ConfigurationUtilBean> getMessageNameDropdown()
	{
	return messageNameDropdown;
	}

	public void setFrequencys(String frequencys)
	{
	this.frequencys = frequencys;
	}

	public String getFrequencys()
	{
	return frequencys;
	}

	public void setFrequency(List<ConfigurationUtilBean> frequency)
	{
	this.frequency = frequency;
	}

	public List<ConfigurationUtilBean> getFrequency()
	{
	return frequency;
	}

	public void setMessagerootList(Map<String, String> messagerootList)
	{
	this.messagerootList = messagerootList;
	}

	public Map<String, String> getMessagerootList()
	{
	return messagerootList;
	}

	public void setGroupcontactNameListData(Map<String, String> groupcontactNameListData)
	{
	this.groupcontactNameListData = groupcontactNameListData;
	}

	public Map<String, String> getGroupcontactNameListData()
	{
	return groupcontactNameListData;
	}

	public void setGroupNameListData(Map<String, String> groupNameListData)
	{
	this.groupNameListData = groupNameListData;
	}

	public Map<String, String> getGroupNameListData()
	{
	return groupNameListData;
	}

	public void setEmployeeList(Map<String, String> employeeList)
	{
	this.employeeList = employeeList;
	}

	public Map<String, String> getEmployeeList()
	{
	return employeeList;
	}

	public void setTemplateType(String templateType)
	{
	this.templateType = templateType;
	}

	public String getTemplateType()
	{
	return templateType;
	}

	public void setTemplateJSONArray(JSONArray templateJSONArray)
	{
	this.templateJSONArray = templateJSONArray;
	}

	public JSONArray getTemplateJSONArray()
	{
	return templateJSONArray;
	}

	public void setTempList(List<ConfigurationUtilBean> tempList)
	{
	this.tempList = tempList;
	}

	public List<ConfigurationUtilBean> getTempList()
	{
	return tempList;
	}

	public void setMsgObj(InstantMessageBean msgObj)
	{
	this.msgObj = msgObj;
	}

	public InstantMessageBean getMsgObj()
	{
	return msgObj;
	}

	public void setMessage(String message)
	{
	this.message = message;
	}

	public String getMessage()
	{
	return message;
	}

	public void setMessageName(String messageName)
	{
	this.messageName = messageName;
	}

	public String getMessageName()
	{
	return messageName;
	}

	public void setEmployee(String employee)
	{
	this.employee = employee;
	}

	public String getEmployee()
	{
	return employee;
	}

	public void setMobileNo(String mobileNo)
	{
	this.mobileNo = mobileNo;
	}

	public String getMobileNo()
	{
	return mobileNo;
	}

	public void setRoot(String root)
	{
	this.root = root;
	}

	public String getRoot()
	{
	return root;
	}

	public void setTemplateid(String templateid)
	{
	this.templateid = templateid;
	}

	public String getTemplateid()
	{
	return templateid;
	}

	public void setWriteMessage(String writeMessage)
	{
	this.writeMessage = writeMessage;
	}

	public String getWriteMessage()
	{
	return writeMessage;
	}

	public void setDate(String date)
	{
	this.date = date;
	}

	public String getDate()
	{
	return date;
	}

	public void setHours(String hours)
	{
	this.hours = hours;
	}

	public String getHours()
	{
	return hours;
	}

	public void setDay(String day)
	{
	this.day = day;
	}

	public String getDay()
	{
	return day;
	}

	public void setEntityContacts(String entityContacts)
	{
	this.entityContacts = entityContacts;
	}

	public String getEntityContacts()
	{
	return entityContacts;
	}

	public void setCorrectData(Map<String, String> correctData)
	{
	this.correctData = correctData;
	}

	public Map<String, String> getCorrectData()
	{
	return correctData;
	}

	public void setShowMultiselect(boolean showMultiselect)
	{
	this.showMultiselect = showMultiselect;
	}

	public boolean isShowMultiselect()
	{
	return showMultiselect;
	}

	public void setChkGroup(boolean chkGroup)
	{
	this.chkGroup = chkGroup;
	}

	public boolean isChkGroup()
	{
	return chkGroup;
	}

	public void setGroupName(String groupName)
	{
	this.groupName = groupName;
	}

	public String getGroupName()
	{
	return groupName;
	}
	public List<GridDataPropertyView> getCrmgroupViewProp() {
	return crmgroupViewProp;
	}
	public void setCrmgroupViewProp(List<GridDataPropertyView> crmgroupViewProp) {
	this.crmgroupViewProp = crmgroupViewProp;
	}
	public List<Object> getGroupdataViewList() {
	return groupdataViewList;
	}
	public void setGroupdataViewList(List<Object> groupdataViewList) {
	this.groupdataViewList = groupdataViewList;
	}
	public Map<String, String> getGroupNameList() {
	return groupNameList;
	}
	public void setGroupNameList(Map<String, String> groupNameList) {
	this.groupNameList = groupNameList;
	}
	public String getGroupname() {
	return groupname;
	}
	public void setGroupname(String groupname) {
	this.groupname = groupname;
	}
	public String getViewType() {
	return viewType;
	}
	public void setViewType(String viewType) {
	this.viewType = viewType;
	}
	public String getCreateType() {
	return createType;
	}
	public void setCreateType(String createType) {
	this.createType = createType;
	}
	
	public String getComment() {
	return comment;
	}
	public void setComment(String comment) {
	this.comment = comment;
	}
	public Map<String, String> getTemplateNameList() {
	return templateNameList;
	}
	public void setTemplateNameList(Map<String, String> templateNameList) {
	this.templateNameList = templateNameList;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
	this.request = request;
	}
	public String getContactId() {
	return contactId;
	}
	public void setContactId(String contactId) {
	this.contactId = contactId;
	}
	public String getGroupId() {
	return groupId;
	}
	public void setGroupId(String groupId) {
	this.groupId = groupId;
	}
	public String getSubject() {
	return subject;
	}
	public void setSubject(String subject) {
	this.subject = subject;
	}
	public String getFrom_email() {
	return from_email;
	}
	public void setFrom_email(String from_email) {
	this.from_email = from_email;
	}
	public String getEmail_id() {
	return email_id;
	}
	public void setEmail_id(String email_id) {
	this.email_id = email_id;
	}
	public String getWritemail() {
	return writemail;
	}
	public void setWritemail(String writemail) {
	this.writemail = writemail;
	}
	public String getCheck_list() {
	return check_list;
	}
	public void setCheck_list(String check_list) {
	this.check_list = check_list;
	}
	public File[] getDocs() {
	return docs;
	}
	public void setDocs(File[] docs) {
	this.docs = docs;
	}
	public String[] getDocsFileName() {
	return docsFileName;
	}
	public void setDocsFileName(String[] docsFileName) {
	this.docsFileName = docsFileName;
	}
	public String getDocfilename() {
	return docfilename;
	}
	public void setDocfilename(String docfilename) {
	this.docfilename = docfilename;
	}
	public List<InstantMessageBean> getDataList() {
	return dataList;
	}
	public void setDataList(List<InstantMessageBean> dataList) {
	this.dataList = dataList;
	}
	public List<InstantMessageBean> getVdataList() {
	return vdataList;
	}
	public void setVdataList(List<InstantMessageBean> vdataList) {
	this.vdataList = vdataList;
	}
	public List<InstantMessageBean> getInvdataList() {
	return invdataList;
	}
	public void setInvdataList(List<InstantMessageBean> invdataList) {
	this.invdataList = invdataList;
	}
	public List<InstantMessageBean> getBdataList() {
	return bdataList;
	}
	public void setBdataList(List<InstantMessageBean> bdataList) {
	this.bdataList = bdataList;
	}
	public List<InstantMessageBean> getDudataList() {
	return dudataList;
	}
	public void setDudataList(List<InstantMessageBean> dudataList) {
	this.dudataList = dudataList;
	}
	public List<InstantMessageBean> getAllDataList() {
	return allDataList;
	}
	public void setAllDataList(List<InstantMessageBean> allDataList) {
	this.allDataList = allDataList;
	}
	public Set<InstantMessageBean> getSet() {
	return set;
	}
	public void setSet(Set<InstantMessageBean> set) {
	this.set = set;
	}
	public Map<String, String> getCorrectMailData() {
	return correctMailData;
	}
	public void setCorrectMailData(Map<String, String> correctMailData) {
	this.correctMailData = correctMailData;
	}
	public Integer getActiveGroups() {
	return activeGroups;
	}
	public void setActiveGroups(Integer activeGroups) {
	this.activeGroups = activeGroups;
	}
	public Integer getDeactiveGroups() {
	return deactiveGroups;
	}
	public void setDeactiveGroups(Integer deactiveGroups) {
	this.deactiveGroups = deactiveGroups;
	}
	public List<Object> getMailTagViewList() {
	return mailTagViewList;
	}
	public void setMailTagViewList(List<Object> mailTagViewList) {
	this.mailTagViewList = mailTagViewList;
	}
	public List<GridDataPropertyView> getMailTagViewProp() {
	return mailTagViewProp;
	}
	public void setMailTagViewProp(List<GridDataPropertyView> mailTagViewProp) {
	this.mailTagViewProp = mailTagViewProp;
	}
	public String getOrganization_name() {
	return organization_name;
	}
	public void setOrganization_name(String organization_name) {
	this.organization_name = organization_name;
	}
	public String getName() {
	return name;
	}
	public void setName(String name) {
	this.name = name;
	}
	public boolean isOrganizationName() {
	return organizationName;
	}
	public void setOrganizationName(boolean organizationName) {
	this.organizationName = organizationName;
	}
	public boolean isCheckname() {
	return checkname;
	}
	public void setCheckname(boolean checkname) {
	this.checkname = checkname;
	}
	public String getCom_name() {
	return com_name;
	}
	public void setCom_name(String com_name) {
	this.com_name = com_name;
	}
	public String getAddress() {
	return address;
	}
	public void setAddress(String address) {
	this.address = address;
	}
	public String getTag_type() {
	return tag_type;
	}
	public void setTag_type(String tag_type) {
	this.tag_type = tag_type;
	}
	public Map<String, String> getOrganizationnameMap() {
	return organizationnameMap;
	}
	public void setOrganizationnameMap(Map<String, String> organizationnameMap) {
	this.organizationnameMap = organizationnameMap;
	}
	public void setCrmImage(File crmImage) {
	this.crmImage = crmImage;
	}
	public File getCrmImage() {
	return crmImage;
	}
	public void setCrmImageFileName(String crmImageFileName) {
	this.crmImageFileName = crmImageFileName;
	}
	public String getCrmImageFileName() {
	return crmImageFileName;
	}
	public void setCrmImageContentType(String crmImageContentType) {
	this.crmImageContentType = crmImageContentType;
	}
	public String getCrmImageContentType() {
	return crmImageContentType;
	}
	public String getFilePath() {
	return filePath;
	}
	public void setFilePath(String filePath) {
	this.filePath = filePath;
	}

	public String getData() {
	return data;
	}

	public void setData(String data) {
	this.data = data;
	}

	public void setEnt(String ent) {
	this.ent = ent;
	}

	public String getEnt() {
	return ent;
	}

	public void setParameter(StringBuilder parameter) {
	this.parameter = parameter;
	}

	public StringBuilder getParameter() {
	return parameter;
	}

	public void setDiaplayListOnPage(HashMap<Integer, HashMap<String, String>> diaplayListOnPage) {
	this.diaplayListOnPage = diaplayListOnPage;
	}

	public HashMap<Integer, HashMap<String, String>> getDiaplayListOnPage() {
	return diaplayListOnPage;
	}

	public void setStatus(String status) {
	this.status = status;
	}

	public String getStatus() {
	return status;
	}

	public void setAge(String age) {
	this.age = age;
	}

	public String getAge() {
	return age;
	}

	public void setSex(String sex) {
	this.sex = sex;
	}

	public String getSex() {
	return sex;
	}

	public void setAllergic_to(String allergic_to) {
	this.allergic_to = allergic_to;
	}

	public String getAllergic_to() {
	return allergic_to;
	}

	public void setBlood_group(String blood_group) {
	this.blood_group = blood_group;
	}

	public String getBlood_group() {
	return blood_group;
	}

	public void setJsonArrayAllergic(JSONArray jsonArrayAllergic) {
	this.jsonArrayAllergic = jsonArrayAllergic;
	}

	public JSONArray getJsonArrayAllergic() {
	return jsonArrayAllergic;
	}

	public void setJsonArrayOffering(JSONArray jsonArrayOffering) {
	this.jsonArrayOffering = jsonArrayOffering;
	}

	public JSONArray getJsonArrayOffering() {
	return jsonArrayOffering;
	}
	
	

}