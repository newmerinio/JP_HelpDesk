package com.Over2Cloud.ctrl.VAM.VisitorReports;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.ctrl.VAM.master.VAMMasterActionControl;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AllVisitorReport extends ActionSupport implements ServletRequestAware{
	/**
	 * For Visitor Reports.
	 * Sanjiv
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String modifyFlag;
	private String deleteFlag;
	private String mainHeaderName;
	private String purpose;
	private String visit_mode;
	private String rowid;
	private String gatename;
	private String empName;
	private String datebeforenday;
	private String visitedDeptExist;
	private String visiteddept;
	private String empNameExist;
	private String gateNameExist;
	private String reportforExist;
	private String reportfor; 
	private String usertype;
	private String visitorNameVal;
	static final Log log = LogFactory.getLog(VAMMasterActionControl.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private List<GridDataPropertyView> viewVisitorDetail=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVisitorReportDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewScheduledrequestDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVisitorMovementDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVisitorInDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVisitorOutDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewInstantVisitorDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVisitorDetailNUser=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewpostacknowledgeDetail=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVehicleDetails = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewsummaryrepDetails = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewproductivityDetail = new ArrayList<GridDataPropertyView>();
	
	Map<Integer, String> gatenamelist = new HashMap<Integer, String>();
	Map<Integer, String> purposeListPreReqest = new HashMap<Integer, String>();
	private LinkedList<ConfigurationUtilBean> summaryrepfields = null;
	Map<Integer, String> locationlistsumrep = new HashMap<Integer, String>();
	private LinkedList<ConfigurationUtilBean> vehiclefields = null;
	private List<ConfigurationUtilBean> instantvisitfields = null;
	private List<Object> instantfieldandvallist = null;
	private List<ConfigurationUtilBean> fieldNames = null;
	private List<Object> visitorData;
	private List<Object> productivityData;
	private List<Object> postacknowledgedata;
	private List<Object> summaryrepdata;
	private List<Object> scheduledvisitorData;
	private List<Object> visitorMovementData;
	private List<Object> visitorInData;
	private List<Object> visitorOutData;
	private List<Object> vehiclesdatalist;
	private List<ConfigurationUtilBean>	deptDropDown	= null;
	private LinkedList<ConfigurationUtilBean>visitorsearchfields = null;
	Map<Integer,String> departmentlist=new HashMap<Integer, String>();
	Map<Integer,String> vehiclelocationlist=new HashMap<Integer, String>();
	Map<Integer, String> locationlist = new HashMap<Integer, String>();
	Map<Integer, String> purposeList = new HashMap<Integer, String>();
	private String from_date;
	private String to_date;
	private String deptname;
	private String location;
	private String srnumber;
	private String visitorname;
	private String visitedperson;
	private String visitedmobile;
	private String visitormobile;
	private String visitorcompany;
	private String visitor_status; 
	private String visitorMIS;
	private String visitorstatus;
	private String visitmode;
	private String visitorStatuscol;
	private String visitormodecol;
	private String totalstaytime;
	private String visitorstatusAction;
	private String comments;
	private String deptName;
	private String deptNameExist;
	private String locationNameExit;
	private JSONArray jsonArray;
	// Get the requested page. By default grid sets this to 1.
	private Integer rows = 0;
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
	//Grid colomn view
	private String oper;
	private String id;
	private List<Object> instantvisitorData;
	private JSONObject jsonObject;
	private JSONArray array;
	Map<String, Object> oneList = new HashMap<String, Object>();
	
	public String visitorReportMenuView(){
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			setMainHeaderName("Visitor Report");
			setVisitorViewProp("mapped_visitor_configuration", "visitor_configuration");
			
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <visitorReportMenuView()> of class "+getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	
	}
	public void setVisitorViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorDetail.add(gpv);
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			viewVisitorDetail.add(gpv);
		}
	}
	public String visitorDetailsForReport(){
		String strResult = ERROR;
		if(userName == null || userName.equalsIgnoreCase("")){
			return  LOGIN;
		}
		try {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from visitor_entry_details");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if (dataCount != null) 
			{
				/*BigInteger count=new BigInteger("3");
				for(Iterator it=dataCount.iterator(); it.hasNext();)
				{
					 Object obdata=(Object)it.next();
					 count=(BigInteger)obdata;
						
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();*/
				
				//getting the list of colmuns
				StringBuilder query=new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from visitor_entry_details as vid ");
				List fieldNames=Configuration.getColomnList("mapped_visitor_configuration", accountID, connectionSpace, "visitor_configuration");
				int i=0;
				if(fieldNames != null && !fieldNames.isEmpty())
				{	
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
						
						String object = iterator.next().toString();
						if(object!=null)
						{	
							if(i<fieldNames.size()-1){
								if (object.toString().equalsIgnoreCase("purpose"))
								{
									query.append(" pradmin.purpose , ");
									queryEnd.append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
								}
								if (object.toString().equalsIgnoreCase("deptName"))
								{
									query.append(" dept.deptName , ");
									queryEnd.append(" left join department as dept on dept.id = vid.deptName ");
								}else
								{
									if(!object.toString().equalsIgnoreCase("purpose"))
									{
										query.append("vid." + object.toString() + ",");
									}
								}
								
							}
						    	//query.append(object.toString()+",");
						    else
						    	query.append(object.toString());
						}
						i++;
					}
					
					query.append(" " + queryEnd.toString());
					System.out.println("Querry>VReport1>>"+query.toString());
					if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						//add search  query based on the search operation
						if(getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" vid."+getSearchField()+" = '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" vid."+getSearchField()+" like '"+getSearchString()+"%'");
						}
						else if(getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" vid."+getSearchField()+" <> '"+getSearchString()+"'");
						}
						else if(getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"'");
						}
						
					}
					
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
				    {
						if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
						{
							query.append(" order by "+getSidx());
						}
			    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
			    	    {
			    	    	query.append(" order by "+getSidx()+" DESC");
			    	    }
				    }
					
				//	query.append(" limit "+from+","+to);
					
					/**
					 * **************************checking for colomn change due to configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames,"visitor_entry_details",connectionSpace);
					
					System.out.println("Querry VReport2>>"+query.toString());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					List<Object> listhb=new ArrayList<Object>();
					if(data!=null && !data.isEmpty())
					{
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							Object[] obdata = (Object[]) iterator.next();
							
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata[k] != null)
								{
									

									if(fieldNames.get(k).toString().equalsIgnoreCase("orgnztnlevel"))
									{
										//need to work here for viewing the organizaion name instead the id
										String orgName=null;
										if(obdata[k].toString().equalsIgnoreCase("2"))
										{}
										else if(obdata[k].toString().equalsIgnoreCase("3"))
										{}
										else if(obdata[k].toString().equalsIgnoreCase("4"))
										{}
										else if(obdata[k].toString().equalsIgnoreCase("5"))
										{}
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									else
									{
										if(k==0)
										{
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
										}
										else
										{
											if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
											{
												one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
											}
											else
											{
												one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
											}
											
										}
									}
								
								}
							}
							listhb.add(one);
						}
						setVisitorData(listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				
			}
			strResult = SUCCESS;
			
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <getVisitorRecords> of class "+getClass(), e);
			e.printStackTrace();
			strResult = ERROR;
		}
		return strResult;
		
	}
	public String editVisitorReportGrid(){
			String resVisitorReport = ERROR;
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				Map<String, Object>wherClause=new HashMap<String, Object>();
				Map<String, Object>condtnBlock=new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
							&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id",getId());
				cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);
				resVisitorReport = SUCCESS;
			}else if (getOper().equalsIgnoreCase("del")) {
				CommonOperInterface cbt = new CommonConFactory()
				.createInterface();
		String tempIds[] = getId().split(",");
		StringBuilder condtIds = new StringBuilder();
		int i = 1;
		for (String H : tempIds) {
			if (i < tempIds.length)
				condtIds.append(H + " ,");
			else
				condtIds.append(H);
			i++;
		}
		cbt.deleteAllRecordForId("visitor_entry_details", "id", condtIds
				.toString(), connectionSpace);
		resVisitorReport = SUCCESS;
	}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method editVisitorReportGrid of class "+getClass(), e);
			 e.printStackTrace();
		}
		return resVisitorReport;
	}
	public String beforeVisitorReportDetailsView(){
		String resvisview = ERROR;
		try {
			if(userName == null || !userName.equalsIgnoreCase("")){
				resvisview = LOGIN;
			}
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			deptDropDown = new ArrayList<ConfigurationUtilBean>();
			visitorsearchfields = new LinkedList<ConfigurationUtilBean>();
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			List<GridDataPropertyView> groupdata = Configuration
			.getConfigurationData("mapped_visitor_configuration",
			accountID, connectionSpace, "", 0, "table_name",
			"visitor_configuration");
			if(groupdata != null){

				for (GridDataPropertyView gdp : groupdata) {
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("D")
						&& !gdp.getColomnName().equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("gate")
						&& !gdp.getColomnName().equalsIgnoreCase("purpose")
						&& !gdp.getColomnName().equalsIgnoreCase("empName")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
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
					deptDropDown.add(obj);
				}
				
				if (gdp.getColType().trim().equalsIgnoreCase("T")
				&& !gdp.getColomnName().equalsIgnoreCase("date")
				&& !gdp.getColomnName().equalsIgnoreCase("time")
				&& !gdp.getColomnName().equalsIgnoreCase("ip")
				&& !gdp.getColomnName().equalsIgnoreCase("alert_time")
				&& !gdp.getColomnName().equalsIgnoreCase("accept")
				&& !gdp.getColomnName().equalsIgnoreCase("reject")
				&& !gdp.getColomnName().equalsIgnoreCase("status")
				&& !gdp.getColomnName().equalsIgnoreCase("db_transfer")
				&& !gdp.getColomnName().equalsIgnoreCase("update_date")
				&& !gdp.getColomnName().equalsIgnoreCase("update_time")
				&& !gdp.getColomnName().equalsIgnoreCase("time_out")
				&& !gdp.getColomnName().equalsIgnoreCase("update_time")
				&& !gdp.getColomnName().equalsIgnoreCase("userName")
				&& !gdp.getColomnName().equalsIgnoreCase("one_time_password")
				&& !gdp.getColomnName().equalsIgnoreCase("possession")
				&& !gdp.getColomnName().equalsIgnoreCase("image")
				&& !gdp.getColomnName().equalsIgnoreCase("address")
				&& !gdp.getColomnName().equalsIgnoreCase("barcode")
				&& !gdp.getColomnName().equalsIgnoreCase("alert_after")){
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
				if (gdp.getMandatroy().toString().equals("1")) {
				obj.setMandatory(true);
				} else {
				obj.setMandatory(false);
				}
				visitorsearchfields.add(obj);
				}
			}
				resvisview = SUCCESS;
			}
		} catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeVisitorReportDetailsView of class "+getClass(), ex);
			ex.printStackTrace();
		}
		return resvisview;
	}
	/* 
	 * Method to load grid.
	 * **/
	public String VisitorReportDetailsData(){
		String resVisitor = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			setMainHeaderName("Visitor");
			setVisitorReportViewProp("mapped_visitor_configuration", "visitor_configuration");
			resVisitor = SUCCESS;
		} catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method VisitorReportDetailsData() of class "+getClass(), ex);
			ex.printStackTrace();
		}
		
		return resVisitor;
	}
	/* 
	 * Method to show colums names of grid.
	 * **/
	public void setVisitorReportViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorReportDetail.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			if(gdp.getColomnName().equalsIgnoreCase("visit_date"))
			{
				gpv.setHeadingName("Visit Date & Time");
			}else if(gdp.getColomnName().equalsIgnoreCase("location"))
			{
				gpv.setHeadingName("Location & Gate");
			}else
			{
				gpv.setHeadingName(gdp.getHeadingName());
			}
			
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if(gdp.getColomnName().equalsIgnoreCase("time_in"))
			{
				gpv.setHideOrShow("true");
			}else if (gdp.getColomnName().equalsIgnoreCase("gate"))
			{
				gpv.setHideOrShow("true");
			}else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setFormatter(gdp.getFormatter());
			viewVisitorReportDetail.add(gpv);
		}
	}
	/* 
	 * Method to search visitor data for given search parameters.
	 * **/
public String getVisitorReportData(){
	String strResult = ERROR;
	if(userName == null || userName.equalsIgnoreCase("")){
		return  LOGIN;
	}
	try {
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query1=new StringBuilder("");
		query1.append("select count(*) from visitor_entry_details");
		List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		if (dataCount != null) {
			BigInteger count=new BigInteger("3");
			for(Iterator it=dataCount.iterator(); it.hasNext();)
			{
				 Object obdata=(Object)it.next();
				 count=(BigInteger)obdata;
					
			}
			setRecords(count.intValue());
			int to = (getRows() * getPage());
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
			
			//getting the list of colmuns
			StringBuilder query=new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from visitor_entry_details as vid ");
			List fieldNames=Configuration.getColomnList("mapped_visitor_configuration", accountID, connectionSpace, "visitor_configuration");
			int i=0;
			if(fieldNames != null && !fieldNames.isEmpty())
			{	
				for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
					
					String object = iterator.next().toString();
					if(object!=null)
					{	
						if(i<fieldNames.size()-1){
							if (object.toString().equalsIgnoreCase("purpose"))
							{
								query.append(" pradmin.purpose , ");
								queryEnd.append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
							}
							else if (object.toString().equalsIgnoreCase("location"))
							{
								query.append(" loc.name , ");
								queryEnd
								    .append(" left join location as loc on loc.id = vid.location ");
							}
							else if (object.toString().equalsIgnoreCase("deptName"))
							{
								query.append(" dept.deptName , ");
								queryEnd.append(" left join department as dept on dept.id = vid.deptName ");
							}else
							{
								if(!object.toString().equalsIgnoreCase("purpose"))
								{
									query.append("vid." + object.toString() + ",");
								}
								
							}
							
						}
					    else
					    	query.append("vid."+object.toString());
					}
					i++;
				}
				
				query.append(" " + queryEnd.toString());
				if(getDeptname()!=null && !getDeptname().equalsIgnoreCase("") && !getDeptname().equalsIgnoreCase("-1")){
					
					query.append(" where vid.deptName = '"+getDeptname()+"'");
					
				}
				else if(getLocation()!=null && !getLocation().equalsIgnoreCase("") && !getLocation().equalsIgnoreCase("-1")){
					query.append(" where vid.location = '"+getLocation()+"'");
				}
				else if(getVisitorname()!=null && !getVisitorname().equalsIgnoreCase("")){
					query.append(" where vid.visitor_name = '"+getVisitorname()+"'");
				}
				else if(getVisitorcompany()!=null && !getVisitorcompany().equalsIgnoreCase("")){
					query.append(" where vid.visitor_company = '"+getVisitorcompany()+"'");
				}
				else if(getSrnumber()!=null && !getSrnumber().equalsIgnoreCase("")){
					query.append(" where vid.sr_number = '"+getSrnumber()+"'");
				}
				else if(getVisitormobile()!=null && !getVisitormobile().equalsIgnoreCase("")){
					query.append(" where vid.visitor_mobile = '"+getVisitormobile()+"'");
				}
				else if(getFrom_date()!=null && !getFrom_date().equalsIgnoreCase("")
						&& getTo_date()!=null && !getTo_date().equalsIgnoreCase("")){
					query.append(" where vid.visit_date >= '" + getFrom_date()
						    + "' and vid.visit_date <= '" + getTo_date() + "'");
					//query.append(" where vid.visit_date between  = '"+getFrom_date()+"' and '"+getTo_date()+"'");
				}
				
				else if(getVisitedperson()!=null && !getVisitedperson().equalsIgnoreCase("")){
					query.append(" where vid.visited_person = '"+getVisitedperson()+"'");
				}
				else if(getVisitedmobile()!=null && !getVisitedmobile().equalsIgnoreCase("")){
					query.append(" where vid.visited_mobile = '"+getVisitedmobile()+"'");
				}
				System.out.println("Querry>getVisitorReportData>>"+query.toString());
				if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" where ");
					//add search  query based on the search operation
					if(getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" vid."+getSearchField()+" = '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" vid."+getSearchField()+" like '"+getSearchString()+"%'");
					}
					else if(getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" vid."+getSearchField()+" <> '"+getSearchString()+"'");
					}
					else if(getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"'");
					}
					
				}
				System.out.println("222222"+getDeptname());
			
				if (getSord() != null && !getSord().equalsIgnoreCase(""))
			    {
					if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
					{
						query.append(" order by "+getSidx());
					}
		    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
		    	    {
		    	    	query.append(" order by "+getSidx()+" DESC");
		    	    }
			    }
				
				query.append(" limit "+from+","+to);
				
				/**
				 * **************************checking for colomn change due to configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames,"visitor_entry_details",connectionSpace);
				
				System.out.println("Querry getVisitorReportData>>"+query.toString());
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				List<Object> listhb=new ArrayList<Object>();
				if(data!=null && !data.isEmpty())
				{
					for (Iterator iterator = data.iterator(); iterator
							.hasNext();) {
						Object[] obdata = (Object[]) iterator.next();
						
						Map<String, Object> one=new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++) {
							if(obdata[k] != null)
							{
								

								if(fieldNames.get(k).toString().equalsIgnoreCase("orgnztnlevel"))
								{
									//need to work here for viewing the organizaion name instead the id
									String orgName=null;
									if(obdata[k].toString().equalsIgnoreCase("2"))
									{}
									else if(obdata[k].toString().equalsIgnoreCase("3"))
									{}
									else if(obdata[k].toString().equalsIgnoreCase("4"))
									{}
									else if(obdata[k].toString().equalsIgnoreCase("5"))
									{}
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
								else
								{
									if(k==0)
									{
										one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									}
									else
									{
										if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
										{
											one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
										}else if (fieldNames.get(k) != null
										    && fieldNames.get(k).toString()
								        .equalsIgnoreCase("visit_date"))
											{
												one.put(fieldNames.get(k).toString(),
												    (obdata[k].toString()+"  "+obdata[k+1].toString()));
											}
										else if (fieldNames.get(k) != null
										    && fieldNames.get(k).toString()
								        .equalsIgnoreCase("location"))
											{
												if(obdata[k+4] != null &&  obdata[k] !=null)
												{
													one.put(fieldNames.get(k).toString(),
													    obdata[k].toString()+"  "+obdata[k+4].toString());
												}
											}
										else
										{
											one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
										}
										
									}
								}
							
							}
						}
						listhb.add(one);
					}
					setVisitorData(listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
			}
			
		}
		strResult = SUCCESS;
		
	} catch (Exception e) {
		log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
				"Problem in Over2Cloud  method <getVisitorRecords> of class "+getClass(), e);
		e.printStackTrace();
		strResult = ERROR;
	}
	return strResult;
}
 public String visitorPhotoReportWithDetails(){
	 String resString = ERROR;
	 resString = SUCCESS;
	 return resString;
 }
 public String scheduledVisitorGrid(){
	System.out.println("in scheduledVisitorGrid()");
	String resScheduledData = ERROR;
	
	try {
		if(userName == null || userName.equalsIgnoreCase("")){
			return  LOGIN;
		}
		setMainHeaderName("Scheduled Visitor");
		setScheduledrequestViewProp("mapped_prerequest_configuration", "prerequest_configuration");
		resScheduledData = SUCCESS;
	} catch (Exception e) {
		log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
				"Problem in Over2Cloud  method <scheduledVisitorGrid()> of class "+getClass(), e);
		e.printStackTrace();
	}
	return resScheduledData;

 }
	 public void setScheduledrequestViewProp(String table1, String table2)
		{
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewScheduledrequestDetail.add(gpv);
			
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
			for(GridDataPropertyView gdp:returnResult)
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setFormatter(gdp.getFormatter());
				viewScheduledrequestDetail.add(gpv);
			}
		}
	 public String getScheduledPrerequestview(){
			String resString = ERROR;
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			
			try {
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from prerequest_details");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if (dataCount != null) {
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
							
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from prerequest_details as prereq ");
					List fieldNames=Configuration.getColomnList("mapped_prerequest_configuration", accountID, connectionSpace, "prerequest_configuration");
					int i=0;
					if(fieldNames != null && !fieldNames.isEmpty())
					{	
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
							
							String object = iterator.next().toString();
							if(object!=null)
							{	
								if(i<fieldNames.size()-1)
								{
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd.append(" left join location as loc on loc.id = prereq.location ");
									}
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" purposeadmin.purpose, ");
										queryEnd.append(" left join purpose_admin as purposeadmin on purposeadmin.id = prereq.purpose ");
									}
									else
									{
										if(!object.toString().equalsIgnoreCase("location"))
										{
											query.append("prereq." + object.toString() + ",");
										}
									}
								}
								else{
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name ");
										queryEnd.append(" left join location as loc on loc.id = prereq.location ");
									}
									else
									{
										query.append("prereq." + object.toString());
									}
								   }	
							}
							i++;
						}
						query.append(" " + queryEnd.toString());
						System.out.println("Querry>getScheduledPrerequestview>>"+query.toString());
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");
							//add search  query based on the search operation
							if(getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
							}
							
						}
						
						if (getSord() != null && !getSord().equalsIgnoreCase(""))
					    {
							if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
							{
								query.append(" order by "+getSidx());
							}
				    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
				    	    {
				    	    	query.append(" order by "+getSidx()+" DESC");
				    	    }
					    }
						
						query.append(" limit "+from+","+to);
						
						/**
						 * **************************checking for colomn change due to configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,"prerequest_details",connectionSpace);
						
						System.out.println("Querry getScheduledPrerequestview>>"+query.toString());
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						List<Object> listhb=new ArrayList<Object>();
						if(data!=null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator
									.hasNext();) {
								Object[] obdata = (Object[]) iterator.next();
								
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k] != null)
									{
										

										if(fieldNames.get(k).toString().equalsIgnoreCase("orgnztnlevel"))
										{
											//need to work here for viewing the organizaion name instead the id
											String orgName=null;
											if(obdata[k].toString().equalsIgnoreCase("2"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("3"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("4"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("5"))
											{}
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
										else
										{
											if(k==0)
											{
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											}
											else
											{
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
												}
												else
												{
													one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
												}
											}
										}
									
									}
								}
								listhb.add(one);
							}
							setScheduledvisitorData(listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
							resString = SUCCESS;
						}
					}
				}
				
				
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <getScheduledPrerequestview> of class "+getClass(), e);
				e.printStackTrace();
			}
			return resString;
	 }
	 public String modifyScheduledVisitorGrid(){
		 String resModify = ERROR;
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("prerequest_details", wherClause, condtnBlock,connectionSpace);
					resModify = SUCCESS;
				}else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
					.createInterface();
			String tempIds[] = getId().split(",");
			StringBuilder condtIds = new StringBuilder();
			int i = 1;
			for (String H : tempIds) {
				if (i < tempIds.length)
					condtIds.append(H + " ,");
				else
					condtIds.append(H);
				i++;
			}
			cbt.deleteAllRecordForId("prerequest_details", "id", condtIds
					.toString(), connectionSpace);
			resModify = SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method modifyScheduledVisitorGrid of class "+getClass(), e);
				 e.printStackTrace();
			}
			return resModify;
		
	 }
	 public String visitorMovementsGrid(){
			String resMovementData = ERROR;
			
			try {
				if(userName == null || userName.equalsIgnoreCase("")){
					return  LOGIN;
				}
				setMainHeaderName("Visitor Movement");
				setVisitorMovementViewProp("mapped_visitor_configuration", "visitor_configuration");
				resMovementData = SUCCESS;
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <visitorMovementsGrid()> of class "+getClass(), e);
				e.printStackTrace();
			}
			return resMovementData;

		 
	 }
	 public void setVisitorMovementViewProp(String table1, String table2)
		{
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewVisitorMovementDetail.add(gpv);
			
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
			for(GridDataPropertyView gdp:returnResult)
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setFormatter(gdp.getFormatter());
				viewVisitorMovementDetail.add(gpv);
			}
		}
	 public String visitorMovementDataView(){
			String strResult = ERROR;
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			try {
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if (dataCount != null) 
				{
					/*BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
							
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();*/
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames=Configuration.getColomnList("mapped_visitor_configuration", accountID, connectionSpace, "visitor_configuration");
					int i=0;
					if(fieldNames != null && !fieldNames.isEmpty())
					{	
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
							
							String object = iterator.next().toString();
							if(object!=null)
							{	
								if(i<fieldNames.size()-1){
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" pradmin.purpose , ");
										queryEnd
										    .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
									}
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd
										    .append(" left join location as loc on loc.id = vid.location ");
									} else
									{
										if (!object.toString().equalsIgnoreCase("purpose")
										    && !object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
							    	//query.append(object.toString()+",");
							    else
							    	query.append(object.toString());
							}
							i++;
						}
						
						query.append(" " + queryEnd.toString());
						System.out.println("Querry>>>"+query.toString());
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");
							//add search  query based on the search operation
							if(getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" vid."+getSearchField()+" = '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid."+getSearchField()+" like '"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid."+getSearchField()+" <> '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"'");
							}
							
						}
						
						if (getSord() != null && !getSord().equalsIgnoreCase(""))
					    {
							if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
							{
								query.append(" order by "+getSidx());
							}
				    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
				    	    {
				    	    	query.append(" order by "+getSidx()+" DESC");
				    	    }
					    }
						
					//	query.append(" limit "+from+","+to);
						
						/**
						 * **************************checking for colomn change due to configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,"visitor_entry_details",connectionSpace);
						
						System.out.println("Querry >>"+query.toString());
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						List<Object> listhb=new ArrayList<Object>();
						if(data!=null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator
									.hasNext();) {
								Object[] obdata = (Object[]) iterator.next();
								
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k] != null)
									{
										

										if(fieldNames.get(k).toString().equalsIgnoreCase("orgnztnlevel"))
										{
											//need to work here for viewing the organizaion name instead the id
											String orgName=null;
											if(obdata[k].toString().equalsIgnoreCase("2"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("3"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("4"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("5"))
											{}
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
										else
										{
											if(k==0)
											{
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											}
											else
											{
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
												}
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("visit_date"))
												{
													one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else
												{
													one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
												}
												
											}
										}
									
									}
								}
								listhb.add(one);
							}
							setVisitorMovementData(listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
					
				}
				strResult = SUCCESS;
				
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <getVisitorRecords> of class "+getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
			return strResult;
			
		}
	 public String editVisitorMovementGrid(){

			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);
				}else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
					.createInterface();
			String tempIds[] = getId().split(",");
			StringBuilder condtIds = new StringBuilder();
			int i = 1;
			for (String H : tempIds) {
				if (i < tempIds.length)
					condtIds.append(H + " ,");
				else
					condtIds.append(H);
				i++;
			}
			cbt.deleteAllRecordForId("visitor_entry_details", "id", condtIds
					.toString(), connectionSpace);
		}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method editVisitorEntryGrid of class "+getClass(), e);
				 e.printStackTrace();
			}
			return SUCCESS;
		
	 }
	 
	 public String visitorINGrid(){
			String resMovementData = ERROR;
			try {
				if(userName == null || userName.equalsIgnoreCase("")){
					return  LOGIN;
				}
				setMainHeaderName("Visitor In");
				setVisitorInViewProp("mapped_visitor_configuration", "visitor_configuration");
				resMovementData = SUCCESS;
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <visitorINGrid()> of class "+getClass(), e);
				e.printStackTrace();
			}
			return resMovementData;
	 }
	 public void setVisitorInViewProp(String table1, String table2)
		{
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewVisitorInDetail.add(gpv);
			
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
			for(GridDataPropertyView gdp:returnResult)
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setFormatter(gdp.getFormatter());
				viewVisitorInDetail.add(gpv);
			}
		}
	 public String visitorInView(){

			String strResult = ERROR;
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			try {
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if (dataCount != null) {
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
							
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames=Configuration.getColomnList("mapped_visitor_configuration", accountID, connectionSpace, "visitor_configuration");
					int i=0;
					if(fieldNames != null && !fieldNames.isEmpty())
					{	
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
							
							String object = iterator.next().toString();
							if(object!=null)
							{	
								if(i<fieldNames.size()-1){
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" pradmin.purpose , ");
										queryEnd
										    .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
									}
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd
										    .append(" left join location as loc on loc.id = vid.location ");
									} else
									{
										if (!object.toString().equalsIgnoreCase("purpose")
										    && !object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
							    	//query.append(object.toString()+",");
							    else
							    	query.append(object.toString());
							}
							i++;
						}
						
						query.append(" " + queryEnd.toString());
						System.out.println("Querry>setVisitorInData>>"+query.toString());
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");
							//add search  query based on the search operation
							if(getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" vid."+getSearchField()+" = '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid."+getSearchField()+" like '"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid."+getSearchField()+" <> '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"'");
							}
							
						}
						query.append(" where status = 0");
						if (getSord() != null && !getSord().equalsIgnoreCase(""))
					    {
							if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
							{
								query.append(" order by "+getSidx());
							}
				    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
				    	    {
				    	    	query.append(" order by "+getSidx()+" DESC");
				    	    }
					    }
						
						query.append(" limit "+from+","+to);
						
						/**
						 * **************************checking for colomn change due to configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,"visitor_entry_details",connectionSpace);
						
						System.out.println("QuerryresVisitorIn >>"+query.toString());
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						List<Object> listhb=new ArrayList<Object>();
						if(data!=null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator
									.hasNext();) {
								Object[] obdata = (Object[]) iterator.next();
								
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k] != null)
									{
										

										if(fieldNames.get(k).toString().equalsIgnoreCase("orgnztnlevel"))
										{
											//need to work here for viewing the organizaion name instead the id
											String orgName=null;
											if(obdata[k].toString().equalsIgnoreCase("2"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("3"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("4"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("5"))
											{}
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
										else
										{
											if(k==0)
											{
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											}
											else
											{
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
												}
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("visit_date"))
												{
													one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else
												{
													one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
												}
												
											}
										}
									
									}
								}
								listhb.add(one);
							}
							setVisitorInData(listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
					
				}
				strResult = SUCCESS;
				
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <visitorInView> of class "+getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
			return strResult;
			
		
	 }
	 public String modifyVisitorIN(){
		 String resVisitorIn = ERROR;
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);
					resVisitorIn = SUCCESS;
				}else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
					.createInterface();
			String tempIds[] = getId().split(",");
			StringBuilder condtIds = new StringBuilder();
			int i = 1;
			for (String H : tempIds) {
				if (i < tempIds.length)
					condtIds.append(H + " ,");
				else
					condtIds.append(H);
				i++;
			}
			cbt.deleteAllRecordForId("visitor_entry_details", "id", condtIds
					.toString(), connectionSpace);
			resVisitorIn = SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method modifyVisitorIN of class "+getClass(), e);
				 e.printStackTrace();
			}
			return resVisitorIn;
		
	 
	 }
	 public String visitorOutGrid(){
			String resvisitorOutData = ERROR;
			try {
				if(userName == null || userName.equalsIgnoreCase("")){
					return  LOGIN;
				}
				setMainHeaderName("Visitor Out");
				setVisitorOutViewProp("mapped_visitor_configuration", "visitor_configuration");
				resvisitorOutData = SUCCESS;
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <visitorOutGrid()> of class "+getClass(), e);
				e.printStackTrace();
			}
			return resvisitorOutData;
	 }
	 public void setVisitorOutViewProp(String table1, String table2)
		{
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewVisitorOutDetail.add(gpv);
			
			List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
			for(GridDataPropertyView gdp:returnResult)
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setFormatter(gdp.getFormatter());
				viewVisitorOutDetail.add(gpv);
			}
		}
	 public String visitorOutView(){

			String strResult = ERROR;
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			try {
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query1=new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if (dataCount != null) {
					BigInteger count=new BigInteger("3");
					for(Iterator it=dataCount.iterator(); it.hasNext();)
					{
						 Object obdata=(Object)it.next();
						 count=(BigInteger)obdata;
							
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames=Configuration.getColomnList("mapped_visitor_configuration", accountID, connectionSpace, "visitor_configuration");
					int i=0;
					if(fieldNames != null && !fieldNames.isEmpty())
					{	
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();) {
							
							String object = iterator.next().toString();
							if(object!=null)
							{	
								if(i<fieldNames.size()-1){
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" pradmin.purpose , ");
										queryEnd
										    .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
									}
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd
										    .append(" left join location as loc on loc.id = vid.location ");
									} else
									{
										if (!object.toString().equalsIgnoreCase("purpose")
										    && !object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
							    	//query.append(object.toString()+",");
							    else
							    	query.append(object.toString());
							}
							i++;
						}
						
						query.append(" " + queryEnd.toString());
						System.out.println("Querry>visitorOutView>>"+query.toString());
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");
							//add search  query based on the search operation
							if(getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" vid."+getSearchField()+" = '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid."+getSearchField()+" like '"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid."+getSearchField()+" <> '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid."+getSearchField()+" like '%"+getSearchString()+"'");
							}
							
						}
						query.append(" where status = 1");
						if (getSord() != null && !getSord().equalsIgnoreCase(""))
					    {
							if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
							{
								query.append(" order by "+getSidx());
							}
				    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
				    	    {
				    	    	query.append(" order by "+getSidx()+" DESC");
				    	    }
					    }
						
						query.append(" limit "+from+","+to);
						
						/**
						 * **************************checking for colomn change due to configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,"visitor_entry_details",connectionSpace);
						
						System.out.println("QuerryvisitorOutView >>"+query.toString());
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						List<Object> listhb=new ArrayList<Object>();
						if(data!=null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator
									.hasNext();) {
								Object[] obdata = (Object[]) iterator.next();
								
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k] != null)
									{
										

										if(fieldNames.get(k).toString().equalsIgnoreCase("orgnztnlevel"))
										{
											//need to work here for viewing the organizaion name instead the id
											String orgName=null;
											if(obdata[k].toString().equalsIgnoreCase("2"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("3"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("4"))
											{}
											else if(obdata[k].toString().equalsIgnoreCase("5"))
											{}
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
										else
										{
											if(k==0)
											{
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											}
											else
											{
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
												}
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("visit_date"))
												{
													one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else
												{
													one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
												}
												
											}
										}
									
									}
								}
								listhb.add(one);
							}
							setVisitorOutData(listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
					
				}
				strResult = SUCCESS;
				
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <visitorOutView> of class "+getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
			return strResult;
			
		
	 }
	 public String modifyVisitorOut(){
		 String resVisitorOut = ERROR;
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);
					resVisitorOut = SUCCESS;
				}else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
					.createInterface();
			String tempIds[] = getId().split(",");
			StringBuilder condtIds = new StringBuilder();
			int i = 1;
			for (String H : tempIds) {
				if (i < tempIds.length)
					condtIds.append(H + " ,");
				else
					condtIds.append(H);
				i++;
			}
			cbt.deleteAllRecordForId("visitor_entry_details", "id", condtIds
					.toString(), connectionSpace);
			resVisitorOut = SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method modifyVisitorOut of class "+getClass(), e);
				 e.printStackTrace();
			}
			return resVisitorOut;
	 }
	public String getVisitorRecord()
	{
		String strResult = ERROR;
		if(userName == null || userName.equalsIgnoreCase("")){
			return  strResult = LOGIN;
		}
		try{
			jsonObject = new JSONObject();
			array = new JSONArray();
			List<Object> visitordata = new ArrayList<Object>();
			visitordata = CommonMethod.getAllVisitorData(connectionSpace);
			for (Iterator iterator = visitordata.iterator(); iterator.hasNext();) {
				Object[] visdata = (Object[]) iterator.next();
				if(visdata != null && visdata[3] != null){
				System.out.println("visdata>>>"+visdata[0].toString());
				jsonObject.put("firstname", visdata[0].toString());
				if(visdata[1] != null)
				{
					jsonObject.put("lastname", visdata[1].toString());
				}
				
				jsonObject.put("title", visdata[2].toString());
				jsonObject.put("titleofcourtesy", visdata[3].toString());
				jsonObject.put("birthdate", visdata[4].toString());
				jsonObject.put("hiredate", visdata[5].toString());
				jsonObject.put("address", visdata[3].toString());
				jsonObject.put("city", visdata[5].toString());
				jsonObject.put("postalcode", visdata[5].toString());
				if(visdata[6] != null){
				System.out.println("photo>>>>>>>>>>>"+visdata[6].toString());
				jsonObject.put("country", visdata[6].toString());
				}
				jsonObject.put("homephone", visdata[5].toString());
				jsonObject.put("notes", visdata[2].toString());
				array.add(jsonObject);
				}
			}
			/*String firstNames[] = {"", "Andrew", "Janet", "Margaret", "Steven", "Michael", "Robert", "Laura", "Anne"};
			String lastNames[] = {"Davolio", "Fuller", "Leverling", "Peacock", "Buchanan", "Suyama", "King", "Callahan", "Dodsworth"};
			String titles[] = {"Sales Representative", "Vice President, Sales", "Sales Representative", "Sales Representative", "Sales Manager", "Sales Representative", "Sales Representative", "Inside Sales Coordinator", "Sales Representative"};
			String titleofcourtesy[] = {"Ms.", "Dr.", "Ms.", "Mrs.", "Mr.", "Mr.", "Mr.", "Ms.", "Ms."};
			String birthdate[] = {"08-Dec-48", "19-Feb-52", "30-Aug-63", "19-Sep-37", "04-Mar-55", "02-Jul-63", "29-May-60", "09-Jan-58", "27-Jan-66"};
			String hiredate[] = {"01-May-92", "14-Aug-92", "01-Apr-92", "03-May-93", "17-Oct-93", "17-Oct-93", "02-Jan-94", "05-Mar-94", "15-Nov-94"};
			String address[] = {"507 - 20th Ave. E. Apt. 2A", "908 W. Capital Way", "722 Moss Bay Blvd.", "4110 Old Redmond Rd.", "14 Garrett Hill", "Coventry House", "Miner Rd.", "Edgeham Hollow", "Winchester Way", "4726 - 11th Ave. N.E.", "7 Houndstooth Rd."};
			String city[] = {"Seattle", "Tacoma", "Kirkland", "Redmond", "London", "London", "London", "Seattle", "London"};
			String postalcode[] = {"98122", "98401", "98033", "98052", "SW1 8JR", "EC2 7JR", "RG1 9SP", "98105", "WG2 7LT"};
			String country[] = {"USA", "USA", "USA", "USA", "UK", "UK", "UK", "USA", "UK"};
			String homephone[] = {"(206) 555-9857", "(206) 555-9482", "(206) 555-3412", "(206) 555-8122", "(71) 555-4848", "(71) 555-7773", "(71) 555-5598", "(206) 555-1189", "(71) 555-4444"};
			String notes[] = {"Education includes a BA in psychology from Colorado State University in 1970.  She also completed 'The Art of the Cold Call.'  Nancy is a member of Toastmasters International.",
			                "Andrew received his BTS commercial in 1974 and a Ph.D. in international marketing from the University of Dallas in 1981.  He is fluent in French and Italian and reads German.  He joined the company as a sales representative, was promoted to sales manager in January 1992 and to vice president of sales in March 1993.  Andrew is a member of the Sales Management Roundtable, the Seattle Chamber of Commerce, and the Pacific Rim Importers Association.",
			                "Janet has a BS degree in chemistry from Boston College (1984).  She has also completed a certificate program in food retailing management.  Janet was hired as a sales associate in 1991 and promoted to sales representative in February 1992.",
			                "Margaret holds a BA in English literature from Concordia College (1958) and an MA from the American Institute of Culinary Arts (1966).  She was assigned to the London office temporarily from July through November 1992.",
			                "Steven Buchanan graduated from St. Andrews University, Scotland, with a BSC degree in 1976.  Upon joining the company as a sales representative in 1992, he spent 6 months in an orientation program at the Seattle office and then returned to his permanent post in London.  He was promoted to sales manager in March 1993.  Mr. Buchanan has completed the courses 'Successful Telemarketing' and 'International Sales Management.'  He is fluent in French.",
			                "Michael is Nancya graduate of Sussex University (MA, economics, 1983) and the University of California at Los Angeles (MBA, marketing, 1986).  He has also taken the courses 'Multi-Cultural Selling' and 'Time Management for the Sales Professional.'  He is fluent in Japanese and can read and write French, Portuguese, and Spanish.",
			                "Robert King served in the Peace Corps and traveled extensively before completing his degree in English at the University of Michigan in 1992, the year he joined the company.  After completing a course entitled 'Selling in Europe,' he was transferred to the London office in March 1993.",
			                "Laura received a BA in psychology from the University of Washington.  She has also completed a course in business French.  She reads and writes French.",
			                "Anne has a BA degree in English from St. Lawrence College.  She is fluent in French and German."};
			
		
			array = new JSONArray();
			for (int i = 0; i < firstNames.length; i++) {
				jsonObject = new JSONObject();
				jsonObject.put("firstname", firstNames[i]);
				jsonObject.put("lastname", lastNames[i]);
				jsonObject.put("title", titles[i]);
				jsonObject.put("titleofcourtesy", titleofcourtesy[i]);
				jsonObject.put("birthdate", birthdate[i]);
				jsonObject.put("hiredate", hiredate[i]);
				jsonObject.put("address", address[i]);
				jsonObject.put("city", city[i]);
				jsonObject.put("postalcode", postalcode[i]);
				jsonObject.put("country", country[i]);
				jsonObject.put("homephone", homephone[i]);
				jsonObject.put("notes", notes[i]);
				array.add(jsonObject);
			}*/
			strResult = SUCCESS;
			}catch(Exception ex){
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getVisitorRecord of class "+getClass(), ex);
				 ex.printStackTrace();
			}	
			return strResult;
		}
	
	public String beforeInstantVisitorView()
	{

		String resVisitor = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				return  LOGIN;
			}
			datebeforenday = CommonMethod.dateBeforeNdays(7);
			purposeList = new HashMap<Integer, String>();
			purposeList = CommonMethod.allPurpose(connectionSpace);
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			setMainHeaderName("Instant Visitor");
			setInstantVisitorViewProp("mapped_visitor_configuration", "visitor_configuration");
			resVisitor = SUCCESS;
		} catch (Exception ex) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeInstantVisitorView() of class "+getClass(), ex);
			ex.printStackTrace();
		}
		return resVisitor;
	}
	public void setInstantVisitorViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewInstantVisitorDetail.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(table1,accountID,connectionSpace,"",0,"table_name",table2);
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("comments"))
      {
	      gpv.setHideOrShow("false");
      }
			else if (gdp.getColomnName().equals("deptName"))
      {
	      gpv.setHideOrShow("true");
      }
			else if(gdp.getColomnName().equalsIgnoreCase("sr_number"))
      {
      	gpv.setHideOrShow("true");
      }
			else if(gdp.getColomnName().equalsIgnoreCase("visit_date"))
      {
      	gpv.setHeadingName("Visit Date & Time");
				gpv.setHideOrShow("false");
      }
			else if(gdp.getColomnName().equalsIgnoreCase("location"))
      {
      	gpv.setHeadingName("Location & Gate");
				gpv.setHideOrShow("false");
      }
			else if(gdp.getColomnName().equalsIgnoreCase("time_in"))
      {
				gpv.setHideOrShow("true");
      }
			else if(gdp.getColomnName().equalsIgnoreCase("gate"))
      {
				gpv.setHideOrShow("true");
      }
			else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setFormatter(gdp.getFormatter());
			viewInstantVisitorDetail.add(gpv);
		}
	}
	public String instantVisitorView()
	{
		String resString = ERROR;
		String sts=null;;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
		{
			//String statuscol = CommonMethod.getInstantVisitorStatus(connectionSpace);
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from visitor_entry_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			    connectionSpace);
			if (dataCount != null)
			{
				/*BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;

				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();*/

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from visitor_entry_details as ved ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_visitor_configuration", accountID, connectionSpace,
				    "visitor_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{
						
						String object = iterator.next().toString();
						if (object != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (object.toString().equalsIgnoreCase("location"))
								{
									query.append(" loc.name , ");
									queryEnd
									    .append(" left join location as loc on loc.id = ved.location ");
								}
								if (object.toString().equalsIgnoreCase("purpose"))
								{
									query.append(" purposeadmin.purpose, ");
									queryEnd
									    .append(" left join purpose_admin as purposeadmin on purposeadmin.id = ved.purpose ");
								} else
								{
									if (!object.toString().equalsIgnoreCase("location"))
									{
										query.append("ved." + object.toString() + ",");
									}
								}
							} else
							{
								if (object.toString().equalsIgnoreCase("location"))
								{
									query.append(" loc.name ");
									queryEnd
									    .append(" left join location as loc on loc.id = ved.location ");
								} else
								{
									query.append("ved." + object.toString());
								}
							}
						}
						i++;
					}
					query.append(" " + queryEnd.toString()+" where one_time_password IS NULL ");
					if(getVisitorstatus() != null && !getVisitorstatus().equals("-1") && getVisitorstatus().equalsIgnoreCase("Pending"))
					{
						query.append(" " + "and ved.reject is null and ved.accept is null and status = 0");
					}else if(getVisitorstatus() != null && !getVisitorstatus().equals("-1") && getVisitorstatus().equalsIgnoreCase("Rejected"))
					{
						query.append(" " + "and ved.reject is not null");
					}else if(getVisitorstatus() != null && !getVisitorstatus().equals("-1") && getVisitorstatus().equalsIgnoreCase("Accepted"))
					{
						query.append(" " + "and ved.accept is not null");
					}else if(getVisitorstatus() != null && getVisitorstatus().equals("-1"))
					{
					}
					if (getPurpose() != null && !getPurpose().equals("-1"))
					{
						query.append(" " + "and ved.purpose=" + getPurpose());
					}
					else if(getPurpose() != null && getPurpose().equals("-1"))
					{
					}
					if (getLocation() != null && !getLocation().equals("-1"))
					{
						query.append(" " + "and ved.location=" + getLocation());
					}
					else if(getLocation() != null && getLocation().equals("-1"))
					{
					}
					if (getFrom_date() != null && !getFrom_date().equals("")
					    && getTo_date() != null && !getTo_date().equals(""))
					{
						query.append(" and ved.visit_date between '" + getFrom_date()
						    + "' and '" + getTo_date() + "'");
					}
					System.out.println("Querry>instantVisitor>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
					    && !getSearchField().equalsIgnoreCase("")
					    && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "'");
						}

					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					//query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "visitor_entry_details",
					    connectionSpace);

					System.out.println("Querry instantVisitor>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
					    connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					List<Object> listhbpending = new ArrayList<Object>();
					List<Object> listhbaccept = new ArrayList<Object>();
					List<Object> listhbreject = new ArrayList<Object>();
					
					
					if (data != null && !data.isEmpty())
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] obdata = (Object[]) iterator.next();
							sts = CommonMethod.getInstantVisitorStatus(connectionSpace, obdata[0].toString());
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{

									if (fieldNames.get(k).toString()
									    .equalsIgnoreCase("orgnztnlevel"))
									{
										// need to work here for viewing the organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString().equalsIgnoreCase("2"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("3"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("4"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("5"))
										{
										}
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									} else
									{
										if (k == 0)
										{
											one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										} else
										{
											if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("date"))
											{
												one.put(fieldNames.get(k).toString(), DateUtil
												    .convertDateToIndianFormat(obdata[k].toString()));
											} else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),
												    (obdata[k].toString().substring(0, 5)));
											}else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
									        .equalsIgnoreCase("visit_date"))
												{
													one.put(fieldNames.get(k).toString(),
													    (DateUtil.convertDateToIndianFormat(obdata[k].toString())+"  "+obdata[k+1].toString()));
												}
											else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
									        .equalsIgnoreCase("location"))
												{
													if(obdata[k+4] != null &&  obdata[k] !=null)
													{
														one.put(fieldNames.get(k).toString(),
														    obdata[k].toString()+"  "+obdata[k+4].toString());
													}
												}
											else
											{
												one.put(fieldNames.get(k).toString(),
												    DateUtil.makeTitle(obdata[k].toString()));
											}
										}
									}

								}
							}
							
							one.put("visitorStatuscol", sts);
							if(sts.equals("Pending"))
							{
								listhbpending.add(one);
							}else if(sts.equals("Accepted"))
							{
								listhbaccept.add(one);
							}else {
								listhbreject.add(one);
							}
							//listhb.addAll(arg0)
						}
						listhb.addAll(listhbpending);
						listhb.addAll(listhbaccept);
						listhb.addAll(listhbreject);
						setInstantvisitorData(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getPrerequestRecords> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	
	}
	public String modifyInstantVisitor()
	{
		 String InstantVisitor = ERROR;
			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);
					InstantVisitor = SUCCESS;
				}else if (getOper().equalsIgnoreCase("del")) {
					CommonOperInterface cbt = new CommonConFactory()
					.createInterface();
			String tempIds[] = getId().split(",");
			StringBuilder condtIds = new StringBuilder();
			int i = 1;
			for (String H : tempIds) {
				if (i < tempIds.length)
					condtIds.append(H + " ,");
				else
					condtIds.append(H);
				i++;
			}
			cbt.deleteAllRecordForId("visitor_entry_details", "id", condtIds
					.toString(), connectionSpace);
			InstantVisitor = SUCCESS;
				}
			}
			catch(Exception e)
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method modifyInstantVisitor of class "+getClass(), e);
				 e.printStackTrace();
			}
			return InstantVisitor;
	 
	}
	public String instantVisitActionAdd()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			instantvisitfields = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_visitor_configuration", accountID, connectionSpace, "", 0,
			    "table_name", "visitor_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("sr_number")
					    && !gdp.getColomnName().equalsIgnoreCase("address")
					    && !gdp.getColomnName().equalsIgnoreCase("visited_person")
					    && !gdp.getColomnName().equalsIgnoreCase("visited_mobile")
					    && !gdp.getColomnName().equalsIgnoreCase("time_out")
					    && !gdp.getColomnName().equalsIgnoreCase("image")
					    && !gdp.getColomnName().equalsIgnoreCase("alert_time")
					    && !gdp.getColomnName().equalsIgnoreCase("accept")
					    && !gdp.getColomnName().equalsIgnoreCase("reject")
					    && !gdp.getColomnName().equalsIgnoreCase("status")
					    && !gdp.getColomnName().equalsIgnoreCase("db_transfer")
					    && !gdp.getColomnName().equalsIgnoreCase("ip")
					    && !gdp.getColomnName().equalsIgnoreCase("update_date")
					    && !gdp.getColomnName().equalsIgnoreCase("update_time")
					    && !gdp.getColomnName().equalsIgnoreCase("one_time_password")
					    && !gdp.getColomnName().equalsIgnoreCase("alert_after")
					    && !gdp.getColomnName().equalsIgnoreCase("barcode")
					    && !gdp.getColomnName().equalsIgnoreCase("car_number")
					    && !gdp.getColomnName().equalsIgnoreCase("userName"))
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
						instantvisitfields.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
					    && !gdp.getColomnName().equalsIgnoreCase("userName")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("empName")
					    && !gdp.getColomnName().equalsIgnoreCase("deptName")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
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
						instantvisitfields.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("Date")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
						  && !gdp.getColomnName().equalsIgnoreCase("time")
					    || gdp.getColType().trim().equalsIgnoreCase("Time")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
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
						instantvisitfields.add(obj);
					}
					
				}
			}
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from visitor_entry_details as ved ");
			int i = 0;
			for (ConfigurationUtilBean fields : instantvisitfields)
      {
				if (fields != null)
				{
					if (i < instantvisitfields.size() - 1)
					{
						if (fields.getKey().equalsIgnoreCase("purpose"))
						{
							query.append(" pur.purpose , ");
							queryEnd
							    .append(" left join purpose_admin as pur on pur.id = ved.purpose ");
						}else
						{
						query.append(fields.getKey() + ",");
						}
					}
					else
					{
						query.append(fields.getKey());
					}
				}
				i++;
      }
			query.append(" " + queryEnd.toString());
			query.append(" where ved.id = '" +getRowid()+"'");
			List data = cbt.executeAllSelectQuery(query.toString(),
			    connectionSpace);
			if (data != null && !data.isEmpty())
			{
				Object[] obj = (Object[]) data.get(0);
				for(int j=0; j<obj.length; j++)
				{
					if(obj[j]!= null && instantvisitfields.get(j).getValue()!=null){
					oneList.put(instantvisitfields.get(j).getValue(), obj[j].toString());
					}
				}
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to add instantVisitActionAdd!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <instantVisitActionAdd> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/** 
	 * For updating table on accept or reject visitor request.
	 * */
	public String instantVisitActionSubmit()
	{
		String resstring = ERROR;
		boolean status = false;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		Map<String, Object>wherClause=new HashMap<String, Object>();
		Map<String, Object>condtnBlock=new HashMap<String, Object>();
		try
    {
			if(getVisitorstatusAction() != null && !getVisitorstatusAction().equals("") && getVisitorstatusAction().equalsIgnoreCase("Approved"))
			{
				/*wherClause.put("accept", "yes");
				wherClause.put("comments", getComments());
				condtnBlock.put("id",getRowid());
				status = cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);*/
				String query = "update visitor_entry_details set accept = '"+"yes"+"', comments = '"+getComments()+"' where id = '"+getRowid()+"'";
				//update on local.
				int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
				PreRequestserviceStub updateTable=new PreRequestserviceStub();
				UpdateTable obj=new UpdateTable();
				obj.setTableQuery(query);
				//update on Server.
				
				 
				if(localstatus>0)
				{
					addActionMessage("Approved Done Successfully.");
					resstring = SUCCESS;
				}
				else
				{
					addActionMessage("There is some error in Accept.");
					resstring = ERROR;
				}
				 status = updateTable.updateTable(obj).get_return();
			}
			else if(getVisitorstatusAction() != null && !getVisitorstatusAction().equals("") && getVisitorstatusAction().equalsIgnoreCase("Rejected"))
			{
				/*wherClause.put("reject", "no");
				wherClause.put("comments", getComments());
				wherClause.put("status", "1");
				wherClause.put("time_out", DateUtil.getCurrentTime());
				condtnBlock.put("id",getRowid());
				status = cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);*/
				String query = "update visitor_entry_details set reject = '"+"no"+"', comments = '"+getComments()+"',"+" status = 1, time_out ='"+DateUtil.getCurrentTime()+"' where id = '"+getRowid()+"'";
				//update on local.
				int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
				PreRequestserviceStub updateTable=new PreRequestserviceStub();
				UpdateTable obj=new UpdateTable();
				obj.setTableQuery(query);
				//update on Server.
				 //status = updateTable.updateTable(obj).get_return();
				
				if(localstatus>0){
					addActionMessage("Rejected Successfully.");
					resstring = SUCCESS;
				}
				else
				{
					addActionMessage("There is some error in Reject.");
					resstring = ERROR;
				}
				 status = updateTable.updateTable(obj).get_return();
			}else if(getVisitorstatusAction() != null && !getVisitorstatusAction().equals(""))
			{
				addActionMessage("There is some error in Action.");
				resstring = ERROR;}
			
    } catch (Exception e)
    {
    	addActionError("There Is Problem To Accept and Reject Visitor Request.");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <instantVisitActionSubmit> of class "
			    + getClass(), e);
			e.printStackTrace();
    }
		System.out.println("resstring"+resstring);
		return resstring;
	}
	
	/** 
	 * Method To View Visitor Report For Normal Employee.
	 * */
	public String beforeVisitorReportView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			String usrtype = CommonMethod.getUserType(connectionSpace, userName);
			purposeList = new HashMap<Integer, String>();
			purposeList = CommonMethod.allPurpose(connectionSpace);
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			setMainHeaderName("Visitor Report");
			if(usrtype.equals("N"))
			{
				setVisitorViewNormalUserProp("mapped_visitor_configuration",
			    "visitor_configuration");	
			}
			if(usrtype.equals("H"))
			{
				setVisitorHViewNormalUserProp("mapped_visitor_configuration",
			    "visitor_configuration");	
			}
			if(usrtype.equals("M"))
			{
				setUsertype("admin");
				setVisitorMViewNormalUserProp("mapped_visitor_configuration",
			    "visitor_configuration");	
			}
			

		} catch (Exception e)
		{
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <beforeVisitorReportView()> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setVisitorViewNormalUserProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorDetailNUser.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
      {
	      gpv.setHeadingName("Location & Gate");
      }
			else
			{
				gpv.setHeadingName(gdp.getHeadingName());
			}
			if (gdp.getColomnName().equalsIgnoreCase("sr_number"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visited_person"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visited_mobile"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("deptName"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visit_date"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("time_in"))
      {
	      gpv.setHeadingName("In");
      }
      else if (gdp.getColomnName().equals("time_out"))
      {
	      gpv.setHeadingName("Out");
	      gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("possession"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("gate"))
      {
	      gpv.setHideOrShow("true");
      }
      else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewVisitorDetailNUser.add(gpv);
		}
	}
	// for HOD...
	public void setVisitorHViewNormalUserProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorDetailNUser.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
      {
	      gpv.setHeadingName("Location & Gate");
      }
			else
			{
				gpv.setHeadingName(gdp.getHeadingName());
			}
			if (gdp.getColomnName().equalsIgnoreCase("sr_number"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visited_person"))
      {
    	  gpv.setHeadingName("Visited To");
    	  gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("visited_mobile"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("deptName"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visit_date"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("time_in"))
      {
	      gpv.setHeadingName("In");
      }
      else if (gdp.getColomnName().equals("time_out"))
      {
	      gpv.setHeadingName("Out");
	      gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("possession"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("gate"))
      {
	      gpv.setHideOrShow("true");
      }
      else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewVisitorDetailNUser.add(gpv);
		}
	}
	// for Management......
	public void setVisitorMViewNormalUserProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorDetailNUser.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
      {
	      gpv.setHeadingName("Location & Gate");
      }
		else
		{
			gpv.setHeadingName(gdp.getHeadingName());
		}
			if (gdp.getColomnName().equalsIgnoreCase("sr_number"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visited_person"))
      {
    	  gpv.setHeadingName("Visited To");
    	  gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("visited_mobile"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("deptName"))
      {
    	  gpv.setHeadingName("Department");
    	  gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("visit_date"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("time_in"))
      {
	      gpv.setHeadingName("In");
      }
      else if (gdp.getColomnName().equals("time_out"))
      {
	      gpv.setHeadingName("Out");
	      gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("possession"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("gate"))
      {
	      gpv.setHideOrShow("true");
      }
      else
		{
			gpv.setHideOrShow(gdp.getHideOrShow());
		}
			gpv.setFormatter(gdp.getFormatter());
		
			gpv.setWidth(gdp.getWidth());
			viewVisitorDetailNUser.add(gpv);
		}
	}
	
	public String visitorReportView()
	{
		String mode = null;
		String timein = null;
		String timeout = null;
		String strResult = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
				    connectionSpace);
				if (dataCount != null)
				{
					/*BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;

					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();*/

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames = Configuration.getColomnList(
					    "mapped_visitor_configuration", accountID, connectionSpace,
					    "visitor_configuration");
					int i = 0;
					if (fieldNames != null && !fieldNames.isEmpty())
					{
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
						{

							String object = iterator.next().toString();
							if (object != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" pradmin.purpose , ");
										queryEnd
										    .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
									}
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd
										    .append(" left join location as loc on loc.id = vid.location ");
									} else
									{
										if (!object.toString().equalsIgnoreCase("purpose")
										    && !object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
								// query.append(object.toString()+",");
								else
									query.append("vid." + object.toString());
							}
							i++;
						}

						query.append(" " + queryEnd.toString());

						if (getPurpose() != null && !getPurpose().equals("-1"))
						{
							query.append(" " + "where vid.purpose=" + getPurpose());
						}
						else if(getPurpose() != null && getPurpose().equals("-1"))
						{
						}
						if (getDeptName() != null && !getDeptName().equals("-1") && (getEmpName() != null && !getEmpName().equals("-1")))
						{
							query.append(" " + "where vid.deptName=" + getDeptName()+" and vid.visited_person = '"+getEmpName()+"'");
						}
						else if(getDeptName() != null && getDeptName().equals("-1") && (getEmpName() != null && getEmpName().equals("-1")))
						{
						} 
						if (getLocation() != null && !getLocation().equals("-1"))
						{
							query.append(" " + "where vid.location=" + getLocation());
						}
						else if(getLocation() != null && getLocation().equals("-1"))
						{
						} 
						if (getVisitmode() != null && !getVisitmode().equals("-1") && getVisitmode().equalsIgnoreCase("Instant"))
						{
							query.append(" " + "where vid.one_time_password is null");
						}
						else if(getVisitmode() != null && !getVisitmode().equals("-1") && getVisitmode().equalsIgnoreCase("Pre-Request"))
						{
							query.append(" " + "where vid.one_time_password is not null");
						}
						else if(getVisitmode() != null && getVisitmode().equals("-1"))
						{
						} 
						if (getFrom_date() != null && !getFrom_date().equals("")
						    && getTo_date() != null && !getTo_date().equals(""))
						{
							setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
							setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
							query.append(" where vid.visit_date >= '" + getFrom_date()
							    + "' and vid.visit_date <= '" + getTo_date() + "'");
						}
						System.out.println("Querry>VreportNUser>>" + query.toString());
						if (getSearchField() != null && getSearchString() != null
						    && !getSearchField().equalsIgnoreCase("")
						    && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");

							// add search query based on the search operation
							if (getSearchOper().equalsIgnoreCase("eq"))
                            {
                                if(getSearchField().equals("purpose"))
                                {
                                    query.append("and pradmin." + getSearchField() + " = '"
                                            + getSearchString() + "'");
                                }
                                else if(getSearchField().equals("deptName"))
                                {
                                    query.append("and dept." + getSearchField() + " = '"
                                            + getSearchString() + "'");
                                }
                                else if(getSearchField().equals("location"))
                                {
                                    query.append("and loc.name = '"
                                            + getSearchString() + "'");
                                }
                                else{
                                query.append("and vid." + getSearchField() + " = '"
                                    + getSearchString() + "'");
                                }
                            }
							else if (getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid." + getSearchField() + " like '"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid." + getSearchField() + " <> '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "'");
							}

						}

						if (getSord() != null && !getSord().equalsIgnoreCase(""))
						{
							if (getSord().equals("asc") && getSidx() != null
							    && !getSidx().equals(""))
							{
								query.append(" order by " + getSidx());
							}
							if (getSord().equals("desc") && getSidx() != null
							    && !getSidx().equals(""))
							{
								query.append(" order by " + getSidx() + " DESC");
							}
						}

					//	query.append(" limit " + from + "," + to);

						/**
						 * **************************checking for colomn change due to
						 * configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,
						    "visitor_entry_details", connectionSpace);

						System.out.println("Querry visitor2>>" + query.toString());
						List data = cbt.executeAllSelectQuery(query.toString(),
						    connectionSpace);
						List<Object> listhb = new ArrayList<Object>();
						if (data != null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] obdata = (Object[]) iterator.next();
								mode = CommonMethod.getVisitorMode(connectionSpace, obdata[0].toString());
								Map<String, Object> one = new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++)
								{
									if (obdata[k] != null)
									{

										if (fieldNames.get(k).toString()
										    .equalsIgnoreCase("orgnztnlevel"))
										{
											// need to work here for viewing the organizaion name
											// instead the id
											String orgName = null;
											if (obdata[k].toString().equalsIgnoreCase("2"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("3"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("4"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("5"))
											{
											}
											one.put(fieldNames.get(k).toString(),
											    obdata[k].toString());
										} else
										{
											if (k == 0)
											{
												one.put(fieldNames.get(k).toString(),
												    (Integer) obdata[k]);
											} else
											{
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil
													    .convertDateToIndianFormat(obdata[k].toString()));
												}
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),
													    (obdata[k].toString().substring(0, 5)));
												} 
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
										        .equalsIgnoreCase("location"))
													{
														if(obdata[k+4] != null &&  obdata[k] !=null)
														{
															one.put(fieldNames.get(k).toString(),
															    obdata[k].toString()+"  "+obdata[k+4].toString());
														}
													}
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
										        .equalsIgnoreCase("time_in"))
													{
														if(obdata[k] !=null)
														{
															one.put(fieldNames.get(k).toString(),obdata[k].toString());
															timein = obdata[k].toString();
														}
													}
													if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
									        .equalsIgnoreCase("time_out"))
												{
													if(obdata[k] !=null)
													{
														one.put(fieldNames.get(k).toString(),obdata[k].toString());
														timeout = obdata[k].toString();
													}
												}
												else
												{
													one.put(fieldNames.get(k).toString(),
													    DateUtil.makeTitle(obdata[k].toString()));
												}

											}
										}

									}
								}
								one.put("visitormodecol", mode);
								if(CommonMethod.subtractTime(timein,timeout) != null && !CommonMethod.subtractTime(timein,timeout).equalsIgnoreCase("NA"))
								{
									one.put("totalstaytime", CommonMethod.subtractTime(timein,timeout));
								}  
								listhb.add(one);
							}
							setVisitorData(listhb);
							setTotal((int) Math.ceil((double) getRecords()
							    / (double) getRows()));
						}
					}

				}
				strResult = SUCCESS;

			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <visitorReportView> of class "
				    + getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
			// return strResult;
		return strResult;
	
	}
	
	public String modifyVisitorReport()
	{
		String resVisitorDataNuser = ERROR;
	try
	{
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		if(getOper().equalsIgnoreCase("edit"))
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			Map<String, Object>wherClause=new HashMap<String, Object>();
			Map<String, Object>condtnBlock=new HashMap<String, Object>();
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			while (it.hasNext()) {
				String Parmname = it.next().toString();
				String paramValue=request.getParameter(Parmname);
				if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
						&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
					if(Parmname.equals("deptName"))
	                {
	                    String deptid=CommonMethod.getDeptId(connectionSpace, paramValue);
	                    wherClause.put(Parmname, deptid);
	                }
	                else if(Parmname.equals("location") || Parmname.equals("gate"))
	                {
	                    String loc=request.getParameter("location");
	                    String st[]=loc.split(" ");
	                    if(st.length > 1)
	                    {
	                    String location=st[0].toString();
	                    String gate=st[1].toString();
	                    System.out.println("Gate Is>>>>>>>>>>"+gate);
	                    String locid=CommonMethod.getLocationId(connectionSpace, location);
	                    if(Parmname.equals("gate"))
	                    {
	                        wherClause.put(Parmname,gate);   
	                    }
	                    else{                           
	                    wherClause.put(Parmname, locid);
	                    }
	                    }
	                    else{
	                        if(Parmname.equals("location"))
	                        {
	                        String loc1=request.getParameter("location");
	                        System.out.println(loc1);
	                        String locid=CommonMethod.getLocationId(connectionSpace, loc1);
	                        System.out.println(locid);
	                        wherClause.put(Parmname, locid);
	                        }
	                        else if(Parmname.equals("gate"))
	                        {
	                            String gate1=request.getParameter("gate");
	                            wherClause.put(Parmname, gate1);
	                        }
	                    }   
	                }
	               else if(Parmname.equals("purpose"))
	                {
	                    String pur=request.getParameter("purpose");
	                    String locid=CommonMethod.getPurposeId(connectionSpace, pur);
	                    wherClause.put(Parmname, locid);
	                }
	                else{
	                wherClause.put(Parmname, paramValue);
	                }
	        }
	        if(wherClause.containsKey("oper"))
	        {
	            wherClause.remove("oper");
	        }
	        condtnBlock.put("id",getId());
			cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,connectionSpace);
			resVisitorDataNuser = SUCCESS;
		}else if (getOper().equalsIgnoreCase("del")) {
			CommonOperInterface cbt = new CommonConFactory()
			.createInterface();
	String tempIds[] = getId().split(",");
	StringBuilder condtIds = new StringBuilder();
	int i = 1;
	for (String H : tempIds) {
		if (i < tempIds.length)
			condtIds.append(H + " ,");
		else
			condtIds.append(H);
		i++;
	}
	cbt.deleteAllRecordForId("visitor_entry_details", "id", condtIds
			.toString(), connectionSpace);
	resVisitorDataNuser = SUCCESS;
}
	}
	catch(Exception e)
	{
		log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
				"Problem in Over2Cloud  method modifyVisitorReport of class "+getClass(), e);
		 e.printStackTrace();
	}
	return resVisitorDataNuser;
	}
	
	/** 
	 * Method To View Post Acknowledge Report For Normal Employee.
	 * */
	public String beforePostAckReportView()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
			{
				String usrtype = CommonMethod.getUserType(connectionSpace, userName);
				locationlist = new HashMap<Integer, String>();
				locationlist = CommonMethod.allLocationList(connectionSpace);
				departmentlist = new HashMap<Integer, String>();
				departmentlist = CommonMethod.allDepartmentList(connectionSpace);
				setMainHeaderName("Post Acknowledge Report");
				if(usrtype.equals("N"))
				{
					setPostAckowledgeRepProp("mapped_acknowledge_post_configuration", "acknowledge_post_configuration");
				}
				if(usrtype.equals("H"))
				{
					setPostAckowledgeHRepProp("mapped_acknowledge_post_configuration", "acknowledge_post_configuration");
				}
				if(usrtype.equals("M"))
				{
					setPostAckowledgeMRepProp("mapped_acknowledge_post_configuration", "acknowledge_post_configuration");
				}
				//setPostAckowledgeRepProp("mapped_acknowledge_post_configuration", "acknowledge_post_configuration");
			} catch (Exception e)
			{
						log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <beforeacknowledgePostAdd()> of class "
				    + getClass(), e);
				e.printStackTrace();
			}
			return SUCCESS;
	
	}
	public void setPostAckowledgeRepProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewpostacknowledgeDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
	if (gdp.getColomnName().equalsIgnoreCase("location"))
      {
	      gpv.setHeadingName("Location & Gate");
		  gpv.setHideOrShow("false");
      }else if(gdp.getColomnName().equalsIgnoreCase("date"))
      {
      	 gpv.setHeadingName("Date & Time");
      	 gpv.setHideOrShow("false");
      }
      else if(gdp.getColomnName().equalsIgnoreCase("empName"))
      {
      	 gpv.setHideOrShow("true");
      }
      else if(gdp.getColomnName().equalsIgnoreCase("dept"))
      {
     	 gpv.setHideOrShow("true");
      }
	else
      {
      	gpv.setHideOrShow(gdp.getHideOrShow());
      }
			
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewpostacknowledgeDetail.add(gpv);
		}
	}
	// for HOD...
	public void setPostAckowledgeHRepProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewpostacknowledgeDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
      {
	      gpv.setHeadingName("Location & Gate");
				gpv.setHideOrShow("false");
      }else if(gdp.getColomnName().equalsIgnoreCase("date"))
      {
      	 gpv.setHeadingName("Date & Time");
      	gpv.setHideOrShow("false");
      }else if(gdp.getColomnName().equalsIgnoreCase("dept"))
      {
     	 gpv.setHideOrShow("true");
     }
			else
      {
      	gpv.setHideOrShow(gdp.getHideOrShow());
      }
			
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewpostacknowledgeDetail.add(gpv);
		}
	}
	// for Admin.....
	public void setPostAckowledgeMRepProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewpostacknowledgeDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
      {
	      gpv.setHeadingName("Location & Gate");
				gpv.setHideOrShow("false");
      }else if(gdp.getColomnName().equalsIgnoreCase("date"))
      {
      	 gpv.setHeadingName("Date & Time");
      	gpv.setHideOrShow("false");
      }else if(gdp.getColomnName().equalsIgnoreCase("dept"))
      {
    	  gpv.setHeadingName("Department");
          gpv.setHideOrShow("false");
     }
			else
      {
      	gpv.setHideOrShow(gdp.getHideOrShow());
      }
			
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewpostacknowledgeDetail.add(gpv);
		}
	}
	
	public String postAckReportNUserView()
	{
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from acknowledge_post_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			    connectionSpace);
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
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from acknowledge_post_details as apd ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_acknowledge_post_configuration", accountID, connectionSpace,
				    "acknowledge_post_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{

						String object = iterator.next().toString();
						if (object != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (object.toString().equalsIgnoreCase("vender_type"))
								{
									query.append(" vad.vender_type, ");
									queryEnd
									    .append(" left join vendor_admin_details as vad on apd.vender_type = vad.id ");
								}else
								if (object.toString().equalsIgnoreCase("dept"))
								{
									query.append(" dpt.deptName, ");
									queryEnd
									    .append(" left join department as dpt on apd.dept = dpt.id ");
								}else
								if (object.toString().equalsIgnoreCase("empName"))
								{
									query.append(" empb.empName, ");
									queryEnd
									    .append(" left join employee_basic as empb on apd.empName = empb.id ");
								}
								else
								{
									query.append("apd." + object.toString() + ",");
								}
							}
							else
								query.append("apd."+object.toString());
						}
						i++;
					}
					
					query.append(" " + queryEnd.toString());
					System.out.println("TestgetDeptName()tttgetEmpName()tttttt"+getDeptName()+">>>"+getEmpName());
					if (getDeptName() != null && !getDeptName().equals("-1") && (getEmpName() != null && !getEmpName().equals("-1")))
					{
						query.append(" " + "where apd.dept =" + getDeptName()+" and apd.empName = '"+getEmpName()+"'");
					}
					else if(getDeptName() != null && getDeptName().equals("-1") && (getEmpName() != null && getEmpName().equals("-1")))
					{
					} 
					if (getLocation() != null && !getLocation().equals("-1"))
					{
						query.append(" " + "where apd.location='" + getLocation()+"'");
					}
					else if(getLocation() != null && getLocation().equals("-1"))
					{
					} 
					if (getFrom_date() != null && !getFrom_date().equals("")
					    && getTo_date() != null && !getTo_date().equals(""))
					{
						setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
						setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
						query.append(" where apd.date >= '" + getFrom_date()
						    + "' and apd.date <= '" + getTo_date() + "'");
					}
					System.out.println("Querry>Postacknowledge>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
					    && !getSearchField().equalsIgnoreCase("")
					    && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "'");
						}

					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "acknowledge_post_details",
					    connectionSpace);

					System.out.println("Querry Acknowledgepost>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
					    connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty())
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{

									if (fieldNames.get(k).toString()
									    .equalsIgnoreCase("orgnztnlevel"))
									{
										// need to work here for viewing the organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString().equalsIgnoreCase("2"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("3"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("4"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("5"))
										{
										}
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									} else
									{
										if (k == 0)
										{
											one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										} else
										{
											if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("date"))
											{
												one.put(fieldNames.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+"  "+(obdata[k+1].toString()).substring(0, 5));
											} /*else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),
												    (obdata[k].toString().substring(0, 5)));
											}*/ else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
									        .equalsIgnoreCase("location"))
											{
												one.put(fieldNames.get(k).toString(),
										    (obdata[k].toString()+"  "+obdata[k+1].toString())); // for appeding location and gate.
											} 
											
											else
											{
												one.put(fieldNames.get(k).toString(),
												    DateUtil.makeTitle(obdata[k].toString()));
											}
										}
									}
								}
							}
							listhb.add(one);
						}
						setPostacknowledgedata(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
						resString = SUCCESS;
					}
				}
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <postAckReportNUserView> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	
	public String modifyPostAckReportView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
				    .list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
					    && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
						 if(Parmname.equalsIgnoreCase("dept"))
		                    {
		                        String dep=request.getParameter("dept");
		                        String dept=CommonMethod.getDeptId(connectionSpace, dep);
		                        wherClause.put(Parmname,dept);    
		                    }
		                    else if(Parmname.equalsIgnoreCase("empName"))
		                    {
		                        String emp=request.getParameter("empName");
		                        String empid=null;
		                        String str="Select id from employee_basic where empName='"+emp+"'";
		                        CommonMethod.getEmpGateName(connectionSpace, emp);
		                        System.out.println(">>>>>>>>"+str.toString());
		                        List empData =new createTable().executeAllSelectQuery(str.toString(), connectionSpace);
		                        if(empData!=null && empData.size()>0)
		                        {
		                            empid = empData.get(0).toString();
		                        }
		                        wherClause.put(Parmname,empid);
		                    }
		                    else if(Parmname.equalsIgnoreCase("vender_type"))
		                    {
		                        String vt=request.getParameter("vender_type");
		                        String vti=null;
		                        String str="Select id from vendor_admin_details where vender_type='"+vt+"'";
		                        System.out.println(">>>>>>>>"+str.toString());
		                        List viData =new createTable().executeAllSelectQuery(str.toString(), connectionSpace);
		                        if(viData!=null && viData.size()>0)
		                        {
		                            vti = viData.get(0).toString();
		                        }
		                        wherClause.put(Parmname,vti);
		                    }
		                    else if(Parmname.equalsIgnoreCase("date"))
		                    {    
		                    String str=request.getParameter("date");
		                    String st[]=str.split("  ");
		                    String date=st[0].toString();
		                    wherClause.put(Parmname,DateUtil.convertDateToUSFormat(date));
		                    }        
		                    else if(Parmname.equals("location") || Parmname.equals("gate"))
		                    {
		                        String loc=request.getParameter("location");
		                        String st[]=loc.split(" ");
		                        if(st.length > 1)
		                        {
		                        String location=st[0].toString();
		                        String gate=st[1].toString();
		                        System.out.println("Gate Is>>>>>>>>>>"+gate);
		                        String locid=CommonMethod.getLocationId(connectionSpace, location);
		                        if(Parmname.equals("gate"))
		                        {
		                            wherClause.put(Parmname,gate);    
		                        }
		                        else{                            
		                        wherClause.put(Parmname, location);
		                        }
		                        }
		                        else{
		                            if(Parmname.equals("location"))
		                            {
		                            String loc1=request.getParameter("location");
		                            System.out.println(loc1);
		                            String locid=CommonMethod.getLocationId(connectionSpace, loc1);
		                            System.out.println(locid);
		                            wherClause.put(Parmname, loc1);
		                            }
		                            else if(Parmname.equals("gate"))
		                            {
		                                String gate1=request.getParameter("gate");
		                                wherClause.put(Parmname, gate1);
		                            }
		                        }    
		                    }
		                    else{    
		                        wherClause.put(Parmname, paramValue);
		                }
				}
		                    if(wherClause.containsKey("oper"))
		                {
		                    wherClause.remove("oper");
		                }	condtnBlock.put("id", getId());
				cbt.updateTableColomn("acknowledge_post_details", wherClause,
				    condtnBlock, connectionSpace);
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
				cbt.deleteAllRecordForId("acknowledge_post_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method modifyPostAckReportView of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String beforeSummaryReportView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			setMainHeaderName("Summary Report");
			setSummaryRepViewProp("mapped_summary_report_configuration",
			    "summary_report_configuration");
		} catch (Exception e)
		{
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <beforeSummaryReportView()> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setSummaryRepViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewsummaryrepDetails.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			if(gpv.getColomnName().equalsIgnoreCase("location"))
			{
				gpv.setHeadingName("Location & Gate");
			}else
			{
				gpv.setHeadingName(gdp.getHeadingName());
			}
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewsummaryrepDetails.add(gpv);
		}
	}
	
	public String summaryReportView()
	{
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from summary_report_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			    connectionSpace);
			if (dataCount != null)
			{
				/*BigInteger count = new BigInteger("3");
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
*/
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from summary_report_details as srd ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_summary_report_configuration", accountID, connectionSpace,
				    "summary_report_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{

						String object = iterator.next().toString();
						if (object != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (object.toString().equalsIgnoreCase("location"))
								{
									query.append(" loc.name, ");
									queryEnd
									    .append(" left join location as loc on srd.location = loc.id ");
								}
								else
									if (object.toString().equalsIgnoreCase("gate"))
									{
										query.append(" gld.gate, ");
										queryEnd
										    .append(" left join gate_location_details as gld on srd.gate = gld.id ");
									}
									else
										if (object.toString().equalsIgnoreCase("deptName"))
										{
											query.append(" dep.deptName, ");
											queryEnd
											    .append(" left join department as dep on srd.deptName = dep.id ");
										}
								else
								if (object.toString().equalsIgnoreCase("empName"))
								{
									query.append(" empb.empName, ");
									queryEnd
									    .append(" left join employee_basic as empb on srd.empName = empb.id ");
								}
								else
								{
									query.append("srd." + object.toString() + ",");
								}
							}
							else
								query.append("srd."+object.toString());
						}
						i++;
					}
					
					query.append(" " + queryEnd.toString());
					System.out.println("Querry233>Summary Rep>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
					    && !getSearchField().equalsIgnoreCase("")
					    && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation

					    if (getSearchOper().equalsIgnoreCase("eq"))
					                        {
					                            if(getSearchField().equals("location"))
					                            {
					                                String loc,gate;
					                                String sp[];
					                                sp=getSearchString().split(" ");
					                                loc=sp[0].toString();
					                                gate=sp[1].toString();
					                                query.append(" loc.name = '" + loc + "' and  gld.gate = '" +gate+"'");
					                            }
					                            else if(getSearchField().equals("deptName"))
					                            {
					                                query.append("dep."+getSearchField() + " = '" + getSearchString()
					                                        + "'");
					                            }
					                            else if(getSearchField().equals("empName"))
					                            {
					                                query.append("empb."+getSearchField() + " = '" + getSearchString()
					                                        + "'");
					                            }
					                            else{
					                            query.append(" " + getSearchField() + " = '" + getSearchString()
					                                + "'");
					                            }
					                        }else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "'");
						}

					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

		//			query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "summary_report_details",
					    connectionSpace);

					System.out.println("Querry Summary Rep>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
					    connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty())
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{

									if (fieldNames.get(k).toString()
									    .equalsIgnoreCase("orgnztnlevel"))
									{
										// need to work here for viewing the organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString().equalsIgnoreCase("2"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("3"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("4"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("5"))
										{
										}
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									} else
									{
										if (k == 0)
										{
											one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										} else
										{
											if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("date"))
											{
												one.put(fieldNames.get(k).toString(), obdata[k].toString()+"  "+obdata[k+1].toString());
											} else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),
												    (obdata[k].toString().substring(0, 5)));
											}else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
									        .equalsIgnoreCase("location"))
											{
												one.put(fieldNames.get(k).toString(),
										    (obdata[k].toString()+"  "+obdata[k+1].toString())); // for appeding location and gate.
											} 
											
											else
											{
												one.put(fieldNames.get(k).toString(),
												    DateUtil.makeTitle(obdata[k].toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setSummaryrepdata(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <summaryReportView> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	
	public String modifySummaryReport()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
				    .list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
					    && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("summary_report_details", wherClause,
				    condtnBlock, connectionSpace);
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
				cbt.deleteAllRecordForId("summary_report_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method modifySummaryReport of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String addSummaryRepDetail()
	{
		String resString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			//SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
			deptDropDown = new ArrayList<ConfigurationUtilBean>();
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			locationlistsumrep = new HashMap<Integer, String>();
			locationlistsumrep = CommonMethod.allLocationList(connectionSpace);
			summaryrepfields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_summary_report_configuration", accountID,
			    connectionSpace, "", 0, "table_name",
			    "summary_report_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
					    && !gdp.getColomnName().equalsIgnoreCase("userName")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
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
						deptDropDown.add(obj);
					}
					if (!gdp.getColType().trim().equalsIgnoreCase("Date")
					    && !gdp.getColType().trim().equalsIgnoreCase("Time")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
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
						// visitordatetimelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("deptName"))
					{
						deptName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExist = "true";
						else
							deptNameExist = "false";
					} 
					else if (gdp.getColomnName().equalsIgnoreCase("dept"))
					{
						visiteddept = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							visitedDeptExist = "true";
						else
							visitedDeptExist = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("report_for"))
					{
						reportfor = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							reportforExist = "true";
						else
							reportforExist = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("empName"))
					{
						empName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							empNameExist = "true";
						else
							empNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							locationNameExit = "true";
						else
							locationNameExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("gate"))
					{
						gatename = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							gateNameExist = "true";
						else
							gateNameExist = "false";
					}
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("userName"))
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
						summaryrepfields.add(obj);
					}
				}
				resString = SUCCESS;
			}

		} catch (Exception e)
		{
			addActionError("There Is Problem to Add EmpLocationGateMappingAdd!");
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <empLocationGateMappingAdd> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	public String getGateDetail()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			gatenamelist = new HashMap<Integer, String>();
			if(getId() != null && !getId().equals("-1") && getId().equals("")){
			gatenamelist = CommonMethod.allMappedGateList(connectionSpace, id);
			}
			if (gatenamelist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : gatenamelist.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());
					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to Load Gate Name!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getGateDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}
	public String submitSummaryData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> summaryrepData = Configuration
				    .getConfigurationData("mapped_summary_report_configuration", accountID,
				        connectionSpace, "", 0, "table_name", "summary_report_configuration");

				if (summaryrepData != null && summaryrepData.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : summaryrepData)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("summary_report_details", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections
					    .list((Enumeration<String>) request.getParameterNames());
					System.out.println("requestParameterNames:====="
					    + requestParameterNames.size());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("")
							    && parmName != null)
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
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

					System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("summary_report_details", insertData,
					    connectionSpace);
					if (status)
					{
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				} else
				{
					addActionMessage("There is some error in data insertion.");
				}
			} catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
	public String beforeProductivityReport()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			String usrtype = CommonMethod.getUserType(connectionSpace, userName);
			purposeList = new HashMap<Integer, String>();
			purposeList = CommonMethod.allPurpose(connectionSpace);
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			setMainHeaderName("Productivity Report");
			if(usrtype.equals("M"))
			{
				setProductivityProp("mapped_visitor_configuration",
			    "visitor_configuration");	
			}
			

		} catch (Exception e)
		{
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <beforeProductivityReport()> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setProductivityProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewproductivityDetail.add(gpv);
		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			
				gpv.setHeadingName(gdp.getHeadingName());
	 if (gdp.getColomnName().equalsIgnoreCase("sr_number"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visited_person"))
      {
    	  gpv.setHeadingName("Visited To");
    	  gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("visited_mobile"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("deptName"))
      {
    	  gpv.setHeadingName("Department");
    	  gpv.setHideOrShow("false");
      }
      else if (gdp.getColomnName().equals("visit_date"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("possession"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("gate"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visitor_name"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visitor_mobile"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("visitor_company"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("purpose"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("time_in"))
      {
	      gpv.setHideOrShow("true");
      }
      else if (gdp.getColomnName().equals("location"))
      {
	      gpv.setHideOrShow("true");
      }
      else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewproductivityDetail.add(gpv);
		}
	}
	
	public String productivityReportView()
	{
		System.out.println(">>productivityReportView>>>");
		String mode = null;
		String timein = null;
		String timeout = null;
		String insvisitor = null;
		String preqvisitor = null;
		String totalvisitor = null;
		String preqvisitorw = null;
		String deptVal = null;
		String visitedpersn = null;
		
		String strResult = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
				    connectionSpace);
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
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames = Configuration.getColomnList(
					    "mapped_visitor_configuration", accountID, connectionSpace,
					    "visitor_configuration");
					int i = 0;
					if (fieldNames != null && !fieldNames.isEmpty())
					{
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
						{

							String object = iterator.next().toString();
							if (object != null)
							{
								if (i < fieldNames.size() - 1)
								{
									
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									 else
									{
										if (!object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
								// query.append(object.toString()+",");
								else
									query.append("vid." + object.toString());
							}
							i++;
						}

						query.append(" " + queryEnd.toString()+" group by vid.visited_person");

						System.out.println("Querry>productivity2>>" + query.toString());
						if (getSearchField() != null && getSearchString() != null
						    && !getSearchField().equalsIgnoreCase("")
						    && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");

							// add search query based on the search operation
							if (getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" vid." + getSearchField() + " = '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid." + getSearchField() + " like '"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid." + getSearchField() + " <> '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "'");
							}

						}

						if (getSord() != null && !getSord().equalsIgnoreCase(""))
						{
							if (getSord().equals("asc") && getSidx() != null
							    && !getSidx().equals(""))
							{
								query.append(" order by " + getSidx());
							}
							if (getSord().equals("desc") && getSidx() != null
							    && !getSidx().equals(""))
							{
								query.append(" order by " + getSidx() + " DESC");
							}
						}

						query.append(" limit " + from + "," + to);

						/**
						 * **************************checking for colomn change due to
						 * configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,
						    "visitor_entry_details", connectionSpace);

						System.out.println("Querry productivity2>>" + query.toString());
						List data = cbt.executeAllSelectQuery(query.toString(),
						    connectionSpace);
						List<Object> listhb = new ArrayList<Object>();
						if (data != null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] obdata = (Object[]) iterator.next();
								mode = CommonMethod.getVisitorMode(connectionSpace, obdata[0].toString());
								Map<String, Object> one = new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++)
								{
									if (obdata[k] != null)
									{

										if (fieldNames.get(k).toString()
										    .equalsIgnoreCase("orgnztnlevel"))
										{
											// need to work here for viewing the organizaion name
											// instead the id
											String orgName = null;
											if (obdata[k].toString().equalsIgnoreCase("2"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("3"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("4"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("5"))
											{
											}
											one.put(fieldNames.get(k).toString(),
											    obdata[k].toString());
										} else
										{
											if (k == 0)
											{
												one.put(fieldNames.get(k).toString(),
												    (Integer) obdata[k]);
											} else
											{
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil
													    .convertDateToIndianFormat(obdata[k].toString()));
												}
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),
													    (obdata[k].toString().substring(0, 5)));
												} 
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
										        .equalsIgnoreCase("location"))
													{
														if(obdata[k+4] != null &&  obdata[k] !=null)
														{
															one.put(fieldNames.get(k).toString(),
															    obdata[k].toString()+"  "+obdata[k+4].toString());
														}
													}
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
										        .equalsIgnoreCase("time_in"))
													{
														if(obdata[k] !=null)
														{
															one.put(fieldNames.get(k).toString(),obdata[k].toString());
															timein = obdata[k].toString();
														}
													}
													if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
									        .equalsIgnoreCase("time_out"))
												{
													if(obdata[k] !=null)
													{
														one.put(fieldNames.get(k).toString(),obdata[k].toString());
														timeout = obdata[k].toString();
													}
												}
													if (fieldNames.get(k) != null
														    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("deptName"))
															{
																if(obdata[k] !=null)
																{
																	one.put(fieldNames.get(k).toString(),obdata[k].toString());
																	deptVal = obdata[k].toString();
																}
															}
													if (fieldNames.get(k) != null
														    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("visited_person"))
															{
																if(obdata[k] !=null)
																{
																	one.put(fieldNames.get(k).toString(),obdata[k].toString());
																	visitedpersn = obdata[k].toString();
																}
															}
												else
												{
														one.put(fieldNames.get(k).toString(),
													    DateUtil.makeTitle(obdata[k].toString()));
												}

											}
										}

									}
								}
								one.put("visitormodecol", mode);
								
								if(CommonMethod.subtractTime(timein,timeout) != null && !CommonMethod.subtractTime(timein,timeout).equalsIgnoreCase("NA"))
								{
									one.put("totalstaytime", CommonMethod.subtractTime(timein,timeout));
								}  
								listhb.add(one);
							}
							setProductivityData(listhb);
							setTotal((int) Math.ceil((double) getRecords()
							    / (double) getRows()));
						}
					}

				}
				strResult = SUCCESS;

			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <productivityReportView> of class "
				    + getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
			// return strResult;
		return strResult;
	}
	/** 
	 * for Visitor Mis view.
	 * */
	public String beforeVisitorMISView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			purposeListPreReqest = new HashMap<Integer, String>();
			purposeListPreReqest = CommonMethod.allPurpose(connectionSpace);
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			setMainHeaderName("Visitor MIS");
			setVisitorMISViewProp("mapped_visitor_configuration",
			    "visitor_configuration");

		} catch (Exception e)
		{
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <beforeVisitorMISView()> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public void setVisitorMISViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
		      {
			      gpv.setHeadingName("Location & Gate");
				  gpv.setHideOrShow("false");
		      }
			else if(gdp.getColomnName().equalsIgnoreCase("gate"))
			{
				gpv.setHideOrShow("true");
			}
			else if (gdp.getColomnName().equalsIgnoreCase("visit_date"))
		      {
			      gpv.setHeadingName("Visit Date & Time");
				  gpv.setHideOrShow("false");
		      }
/*			else if(gdp.getColomnName().equalsIgnoreCase("time_in"))
			{
				gpv.setHideOrShow("true");
			}
*/			else if (gdp.getColomnName().equals("time_in"))
            {
                gpv.setHeadingName("In");
            }
            else if (gdp.getColomnName().equals("time_out"))
            {
                gpv.setHeadingName("Out");
                gpv.setHideOrShow("false");
            }

			else
		      {
		    	  gpv.setHeadingName(gdp.getHeadingName());
		    	  gpv.setHideOrShow(gdp.getHideOrShow());
		      }
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewVisitorDetail.add(gpv);
		}
	}
	public String editVisitorMISGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
				    .list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
					    && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,
				    connectionSpace);
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
				cbt.deleteAllRecordForId("visitor_entry_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method editVisitorEntryGrid of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String visitorMisView()
	{
	    String mode = null;
        String timein = null;
        String timeout = null;
		String strResult = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
				    connectionSpace);
				if (dataCount != null)
				{ //27 oct by Azad
					/*BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;

					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();*/

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames = Configuration.getColomnList(
					    "mapped_visitor_configuration", accountID, connectionSpace,
					    "visitor_configuration");
					int i = 0;
					if (fieldNames != null && !fieldNames.isEmpty())
					{
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
						{

							String object = iterator.next().toString();
							if (object != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" pradmin.purpose , ");
										queryEnd
										    .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
									}
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd
										    .append(" left join location as loc on loc.id = vid.location ");
									} else
									{
										if (!object.toString().equalsIgnoreCase("purpose")
										    && !object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
								// query.append(object.toString()+",");
								else
									query.append("vid." + object.toString());
							}
							i++;
						}

						query.append(" " + queryEnd.toString());

						/*if (getDeptName() != null && !getDeptName().equals("-1"))
						{
							query.append(" " + "where vid.deptName=" + getDeptName());
						}
						else if(getDeptName() != null && getDeptName().equals("-1"))
						{
						} */
						if (getDeptName() != null && !getDeptName().equals("-1") && (getEmpName() != null && !getEmpName().equals("-1")))
						{
							query.append(" " + "where vid.deptName=" + getDeptName()+" and vid.visited_person = '"+getEmpName()+"'");
						}
						else if(getDeptName() != null && getDeptName().equals("-1") && (getEmpName() != null && getEmpName().equals("-1")))
						{
						} 
						if (getLocation() != null && !getLocation().equals("-1") && (getGatename() != null && !getGatename().equals("-1")))
						{
							query.append(" " + "where vid.location=" + getLocation()+" and vid.gate = '"+getGatename()+"'");
						}
						else if(getLocation() != null && getLocation().equals("-1") && (getGatename() != null && getGatename().equals("-1")))
						{
						} 
						if (getPurpose() != null && !getPurpose().equals("-1"))
						{
							query.append(" " + "where vid.purpose=" + getPurpose());
						}
						else if(getPurpose() != null && getPurpose().equals("-1"))
						{
						} 
						if (getVisit_mode() != null && !getVisit_mode().equals("-1") && getVisit_mode().equals("Instant Visitor"))
						{
							query.append(" " + "where vid.one_time_password is null" );
						}
						else if(getVisit_mode() != null && !getVisit_mode().equals("-1") && getVisit_mode().equals("Pre-Request"))
						{
							query.append(" " + "where vid.one_time_password is not null" );
						} 
						else if(getVisit_mode() != null && getVisit_mode().equals("All"))
						{
						} 
						else if(getVisitorNameVal() != null && !getVisitorNameVal().equals(""))
						{
							query.append(" " + "where vid.visitor_name like '" );
							query.append(getVisitorNameVal());
							query.append("%'" );
						} 
						else if(getVisitorNameVal() != null && getVisitorNameVal().equals(""))
						{
						} 
						if (getFrom_date() != null && !getFrom_date().equals("")
						    && getTo_date() != null && !getTo_date().equals(""))
						{
							setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
							setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
							query.append(" where vid.visit_date >= '" + getFrom_date()
							    + "' and vid.visit_date <= '" + getTo_date() + "'");
						}
						//System.out.println("Querry>vJOIN>>" + query.toString());
						if (getSearchField() != null && getSearchString() != null
						    && !getSearchField().equalsIgnoreCase("")
						    && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");

							// add search query based on the search operation
							if (getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" vid." + getSearchField() + " = '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid." + getSearchField() + " like '"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid." + getSearchField() + " <> '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "'");
							}

						}

						if (getSord() != null && !getSord().equalsIgnoreCase(""))
						{
							if (getSord().equals("asc") && getSidx() != null
							    && !getSidx().equals(""))
							{
								query.append(" order by " + getSidx());
							}
							if (getSord().equals("desc") && getSidx() != null
							    && !getSidx().equals(""))
							{
								query.append(" order by " + getSidx() + " DESC");
							}
						}

						//query.append(" limit " + from + "," + to);

						/**
						 * **************************checking for colomn change due to
						 * configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,
						    "visitor_entry_details", connectionSpace);

						System.out.println("Querry visitor2>>" + query.toString());
						List data = cbt.executeAllSelectQuery(query.toString(),
						    connectionSpace);
						List<Object> listhb = new ArrayList<Object>();
						if (data != null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] obdata = (Object[]) iterator.next();
							    mode = CommonMethod.getVisitorMode(connectionSpace, obdata[0].toString());
								Map<String, Object> one = new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++)
								{
									if (obdata[k] != null)
									{

										if (fieldNames.get(k).toString()
										    .equalsIgnoreCase("orgnztnlevel"))
										{
											// need to work here for viewing the organizaion name
											// instead the id
											String orgName = null;
											if (obdata[k].toString().equalsIgnoreCase("2"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("3"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("4"))
											{
											} else if (obdata[k].toString().equalsIgnoreCase("5"))
											{
											}
											one.put(fieldNames.get(k).toString(),
											    obdata[k].toString());
										} else
										{
											if (k == 0)
											{
												one.put(fieldNames.get(k).toString(),
												    (Integer) obdata[k]);
											} else
											{
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil
													    .convertDateToIndianFormat(obdata[k].toString()));
												}
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),
													    (obdata[k].toString().substring(0, 5)));
												}
												else if (fieldNames.get(k) != null
													    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("location"))
														{
															if(obdata[k+9] != null &&  obdata[k] !=null)
															{
																one.put(fieldNames.get(k).toString(),
																    obdata[k].toString()+"   "+obdata[k+4].toString());
															}
														}
													else if (fieldNames.get(k) != null
													    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("visit_date"))
														{
															if(obdata[k+1] != null &&  obdata[k] !=null)
															{
																one.put(fieldNames.get(k).toString(),
																   DateUtil.convertDateToIndianFormat(obdata[k].toString())+"   "+obdata[k+1].toString());
															}
														}
												if (fieldNames.get(k) != null
                                                        && fieldNames.get(k).toString()
                                                    .equalsIgnoreCase("time_in"))
                                                        {
                                                            if(obdata[k] !=null)
                                                            {
                                                                one.put(fieldNames.get(k).toString(),obdata[k].toString());
                                                                timein = obdata[k].toString();
                                                            }
                                                        }
                                                        if (fieldNames.get(k) != null
                                                    && fieldNames.get(k).toString()
                                                .equalsIgnoreCase("time_out"))
                                                    {
                                                        if(obdata[k] !=null)
                                                        {
                                                            one.put(fieldNames.get(k).toString(),obdata[k].toString());
                                                            timeout = obdata[k].toString();
                                                        }
                                                    }
												else
												{
													one.put(fieldNames.get(k).toString(),
													    DateUtil.makeTitle(obdata[k].toString()));
												}
											}
										}

									}
								}
								one.put("visitormodecol", mode);
                                if(CommonMethod.subtractTime(timein,timeout) != null && !CommonMethod.subtractTime(timein,timeout).equalsIgnoreCase("NA"))
                                {
                                    one.put("totalstaytime", CommonMethod.subtractTime(timein,timeout));
                                } 
								listhb.add(one);
							}
							setVisitorData(listhb);
							setTotal((int) Math.ceil((double) getRecords()
							    / (double) getRows()));
						}
					}
				}
				strResult = SUCCESS;

			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <visitorMisView> of class "
				    + getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
		return strResult;
	}
	
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public JSONArray getArray() {
		return array;
	}
	public void setArray(JSONArray array) {
		this.array = array;
	}
	
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchOper() {
		return searchOper;
	}
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public boolean isLoadonce() {
		return loadonce;
	}
	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Object> getVisitorData() {
		return visitorData;
	}
	public void setVisitorData(List<Object> visitorData) {
		this.visitorData = visitorData;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	public List<GridDataPropertyView> getViewVisitorDetail() {
		return viewVisitorDetail;
	}
	public void setViewVisitorDetail(List<GridDataPropertyView> viewVisitorDetail) {
		this.viewVisitorDetail = viewVisitorDetail;
	}
	
	public List<ConfigurationUtilBean> getDeptDropDown() {
		return deptDropDown;
	}
	public void setDeptDropDown(List<ConfigurationUtilBean> deptDropDown) {
		this.deptDropDown = deptDropDown;
	}
	public LinkedList<ConfigurationUtilBean> getVisitorsearchfields() {
		return visitorsearchfields;
	}
	public void setVisitorsearchfields(
			LinkedList<ConfigurationUtilBean> visitorsearchfields) {
		this.visitorsearchfields = visitorsearchfields;
	}
	public Map<Integer, String> getDepartmentlist() {
		return departmentlist;
	}
	public void setDepartmentlist(Map<Integer, String> departmentlist) {
		this.departmentlist = departmentlist;
	}
	
	public Map<Integer, String> getLocationlist() {
		return locationlist;
	}
	public void setLocationlist(Map<Integer, String> locationlist) {
		this.locationlist = locationlist;
	}
	
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSrnumber() {
		return srnumber;
	}
	public void setSrnumber(String srnumber) {
		this.srnumber = srnumber;
	}
	public String getVisitorname() {
		return visitorname;
	}
	public void setVisitorname(String visitorname) {
		this.visitorname = visitorname;
	}
	public String getVisitormobile() {
		return visitormobile;
	}
	public void setVisitormobile(String visitormobile) {
		this.visitormobile = visitormobile;
	}
	public String getVisitorcompany() {
		return visitorcompany;
	}
	public void setVisitorcompany(String visitorcompany) {
		this.visitorcompany = visitorcompany;
	}
	
	public List<GridDataPropertyView> getViewVisitorReportDetail() {
		return viewVisitorReportDetail;
	}
	public void setViewVisitorReportDetail(
			List<GridDataPropertyView> viewVisitorReportDetail) {
		this.viewVisitorReportDetail = viewVisitorReportDetail;
	}
	
	public List<GridDataPropertyView> getViewScheduledrequestDetail() {
		return viewScheduledrequestDetail;
	}
	public void setViewScheduledrequestDetail(
			List<GridDataPropertyView> viewScheduledrequestDetail) {
		this.viewScheduledrequestDetail = viewScheduledrequestDetail;
	}
	
	public List<Object> getScheduledvisitorData() {
		return scheduledvisitorData;
	}
	public void setScheduledvisitorData(List<Object> scheduledvisitorData) {
		this.scheduledvisitorData = scheduledvisitorData;
	}
	
	public List<GridDataPropertyView> getViewVisitorMovementDetail() {
		return viewVisitorMovementDetail;
	}
	public void setViewVisitorMovementDetail(
			List<GridDataPropertyView> viewVisitorMovementDetail) {
		this.viewVisitorMovementDetail = viewVisitorMovementDetail;
	}
	
	public List<Object> getVisitorMovementData() {
		return visitorMovementData;
	}
	public void setVisitorMovementData(List<Object> visitorMovementData) {
		this.visitorMovementData = visitorMovementData;
	}
	
	public List<GridDataPropertyView> getViewVisitorInDetail() {
		return viewVisitorInDetail;
	}
	public void setViewVisitorInDetail(
			List<GridDataPropertyView> viewVisitorInDetail) {
		this.viewVisitorInDetail = viewVisitorInDetail;
	}
	
	public List<Object> getVisitorInData() {
		return visitorInData;
	}
	public void setVisitorInData(List<Object> visitorInData) {
		this.visitorInData = visitorInData;
	}
	
	public List<GridDataPropertyView> getViewVisitorOutDetail() {
		return viewVisitorOutDetail;
	}
	public void setViewVisitorOutDetail(
			List<GridDataPropertyView> viewVisitorOutDetail) {
		this.viewVisitorOutDetail = viewVisitorOutDetail;
	}
	
	public List<Object> getVisitorOutData() {
		return visitorOutData;
	}
	public void setVisitorOutData(List<Object> visitorOutData) {
		this.visitorOutData = visitorOutData;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getVisitedperson()
	{
		return visitedperson;
	}
	public void setVisitedperson(String visitedperson)
	{
		this.visitedperson = visitedperson;
	}
	public String getVisitedmobile()
	{
		return visitedmobile;
	}
	public void setVisitedmobile(String visitedmobile)
	{
		this.visitedmobile = visitedmobile;
	}
	public String getVisitor_status()
	{
		return visitor_status;
	}
	public void setVisitor_status(String visitor_status)
	{
		this.visitor_status = visitor_status;
	}
	public String getVisitorMIS()
	{
		return visitorMIS;
	}
	public void setVisitorMIS(String visitorMIS)
	{
		this.visitorMIS = visitorMIS;
	}
	public List<GridDataPropertyView> getViewInstantVisitorDetail()
	{
		return viewInstantVisitorDetail;
	}
	public void setViewInstantVisitorDetail(
	    List<GridDataPropertyView> viewInstantVisitorDetail)
	{
		this.viewInstantVisitorDetail = viewInstantVisitorDetail;
	}
	public Map<Integer, String> getPurposeList()
	{
		return purposeList;
	}
	public void setPurposeList(Map<Integer, String> purposeList)
	{
		this.purposeList = purposeList;
	}
	public List<Object> getInstantvisitorData()
	{
		return instantvisitorData;
	}
	public void setInstantvisitorData(List<Object> instantvisitorData)
	{
		this.instantvisitorData = instantvisitorData;
	}
	public String getPurpose()
	{
		return purpose;
	}
	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}
	public String getVisitorstatus()
	{
		return visitorstatus;
	}
	public void setVisitorstatus(String visitorstatus)
	{
		this.visitorstatus = visitorstatus;
	}
	public String getVisitorStatuscol()
	{
		return visitorStatuscol;
	}
	public void setVisitorStatuscol(String visitorStatuscol)
	{
		this.visitorStatuscol = visitorStatuscol;
	}
	public String getRowid()
	{
		return rowid;
	}
	public void setRowid(String rowid)
	{
		this.rowid = rowid;
	}
	public List<ConfigurationUtilBean> getInstantvisitfields()
	{
		return instantvisitfields;
	}
	public void setInstantvisitfields(List<ConfigurationUtilBean> instantvisitfields)
	{
		this.instantvisitfields = instantvisitfields;
	}
	public List<ConfigurationUtilBean> getFieldNames()
	{
		return fieldNames;
	}
	public void setFieldNames(List<ConfigurationUtilBean> fieldNames)
	{
		this.fieldNames = fieldNames;
	}
	public List<Object> getInstantfieldandvallist()
	{
		return instantfieldandvallist;
	}
	public void setInstantfieldandvallist(List<Object> instantfieldandvallist)
	{
		this.instantfieldandvallist = instantfieldandvallist;
	}
	public Map<String, Object> getOneList()
	{
		return oneList;
	}
	public void setOneList(Map<String, Object> oneList)
	{
		this.oneList = oneList;
	}
	public String getVisitorstatusAction()
	{
		return visitorstatusAction;
	}
	public void setVisitorstatusAction(String visitorstatusAction)
	{
		this.visitorstatusAction = visitorstatusAction;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public List<GridDataPropertyView> getViewVisitorDetailNUser()
	{
		return viewVisitorDetailNUser;
	}
	public void setViewVisitorDetailNUser(
	    List<GridDataPropertyView> viewVisitorDetailNUser)
	{
		this.viewVisitorDetailNUser = viewVisitorDetailNUser;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public List<GridDataPropertyView> getViewpostacknowledgeDetail()
	{
		return viewpostacknowledgeDetail;
	}
	public void setViewpostacknowledgeDetail(
	    List<GridDataPropertyView> viewpostacknowledgeDetail)
	{
		this.viewpostacknowledgeDetail = viewpostacknowledgeDetail;
	}
	public List<Object> getPostacknowledgedata()
	{
		return postacknowledgedata;
	}
	public void setPostacknowledgedata(List<Object> postacknowledgedata)
	{
		this.postacknowledgedata = postacknowledgedata;
	}
	public Map<Integer, String> getVehiclelocationlist() {
		return vehiclelocationlist;
	}
	public void setVehiclelocationlist(Map<Integer, String> vehiclelocationlist) {
		this.vehiclelocationlist = vehiclelocationlist;
	}
	public LinkedList<ConfigurationUtilBean> getVehiclefields() {
		return vehiclefields;
	}
	public void setVehiclefields(LinkedList<ConfigurationUtilBean> vehiclefields) {
		this.vehiclefields = vehiclefields;
	}
	public List<GridDataPropertyView> getViewVehicleDetails()
	{
		return viewVehicleDetails;
	}
	public void setViewVehicleDetails(List<GridDataPropertyView> viewVehicleDetails)
	{
		this.viewVehicleDetails = viewVehicleDetails;
	}
	public List<Object> getVehiclesdatalist()
	{
		return vehiclesdatalist;
	}
	public void setVehiclesdatalist(List<Object> vehiclesdatalist)
	{
		this.vehiclesdatalist = vehiclesdatalist;
	}
	public String getDeptNameExist()
	{
		return deptNameExist;
	}
	public void setDeptNameExist(String deptNameExist)
	{
		this.deptNameExist = deptNameExist;
	}
	public String getLocationNameExit()
	{
		return locationNameExit;
	}
	public void setLocationNameExit(String locationNameExit)
	{
		this.locationNameExit = locationNameExit;
	}
	public String getVisitormodecol()
	{
		return visitormodecol;
	}
	public void setVisitormodecol(String visitormodecol)
	{
		this.visitormodecol = visitormodecol;
	}
	public String getTotalstaytime()
	{
		return totalstaytime;
	}
	public void setTotalstaytime(String totalstaytime)
	{
		this.totalstaytime = totalstaytime;
	}
	public String getVisitmode()
	{
		return visitmode;
	}
	public void setVisitmode(String visitmode)
	{
		this.visitmode = visitmode;
	}
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getEmpNameExist()
	{
		return empNameExist;
	}
	public void setEmpNameExist(String empNameExist)
	{
		this.empNameExist = empNameExist;
	}
	public String getGateNameExist()
	{
		return gateNameExist;
	}
	public void setGateNameExist(String gateNameExist)
	{
		this.gateNameExist = gateNameExist;
	}
	public List<GridDataPropertyView> getViewsummaryrepDetails()
	{
		return viewsummaryrepDetails;
	}
	public void setViewsummaryrepDetails(
	    List<GridDataPropertyView> viewsummaryrepDetails)
	{
		this.viewsummaryrepDetails = viewsummaryrepDetails;
	}
	public Map<Integer, String> getLocationlistsumrep()
	{
		return locationlistsumrep;
	}
	public void setLocationlistsumrep(Map<Integer, String> locationlistsumrep)
	{
		this.locationlistsumrep = locationlistsumrep;
	}
	public LinkedList<ConfigurationUtilBean> getSummaryrepfields()
	{
		return summaryrepfields;
	}
	public void setSummaryrepfields(
	    LinkedList<ConfigurationUtilBean> summaryrepfields)
	{
		this.summaryrepfields = summaryrepfields;
	}
	public Map<Integer, String> getGatenamelist()
	{
		return gatenamelist;
	}
	public void setGatenamelist(Map<Integer, String> gatenamelist)
	{
		this.gatenamelist = gatenamelist;
	}
	public JSONArray getJsonArray()
	{
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}
	public String getVisitedDeptExist()
	{
		return visitedDeptExist;
	}
	public void setVisitedDeptExist(String visitedDeptExist)
	{
		this.visitedDeptExist = visitedDeptExist;
	}
	public String getVisiteddept()
	{
		return visiteddept;
	}
	public void setVisiteddept(String visiteddept)
	{
		this.visiteddept = visiteddept;
	}
	public String getReportforExist()
	{
		return reportforExist;
	}
	public void setReportforExist(String reportforExist)
	{
		this.reportforExist = reportforExist;
	}
	public String getReportfor()
	{
		return reportfor;
	}
	public void setReportfor(String reportfor)
	{
		this.reportfor = reportfor;
	}
	public String getGatename()
	{
		return gatename;
	}
	public void setGatename(String gatename)
	{
		this.gatename = gatename;
	}
	public List<Object> getSummaryrepdata()
	{
		return summaryrepdata;
	}
	public void setSummaryrepdata(List<Object> summaryrepdata)
	{
		this.summaryrepdata = summaryrepdata;
	}
	public List<GridDataPropertyView> getViewproductivityDetail() {
		return viewproductivityDetail;
	}
	public void setViewproductivityDetail(
			List<GridDataPropertyView> viewproductivityDetail) {
		this.viewproductivityDetail = viewproductivityDetail;
	}
	public List<Object> getProductivityData() {
		return productivityData;
	}
	public void setProductivityData(List<Object> productivityData) {
		this.productivityData = productivityData;
	}
	public String getDatebeforenday() {
		return datebeforenday;
	}
	public void setDatebeforenday(String datebeforenday) {
		this.datebeforenday = datebeforenday;
	}
	public Map<Integer, String> getPurposeListPreReqest() {
		return purposeListPreReqest;
	}
	public void setPurposeListPreReqest(Map<Integer, String> purposeListPreReqest) {
		this.purposeListPreReqest = purposeListPreReqest;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getVisit_mode() {
		return visit_mode;
	}
	public void setVisit_mode(String visit_mode) {
		this.visit_mode = visit_mode;
	}
	public String getVisitorNameVal() {
		return visitorNameVal;
	}
	public void setVisitorNameVal(String visitorNameVal) {
		this.visitorNameVal = visitorNameVal;
	}
	

}