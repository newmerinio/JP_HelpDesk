package com.Over2Cloud.ctrl.asset.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AssetComplaintDashAction extends ActionSupport{

	public Map session = ActionContext.getContext().getSession();
	
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	
	String dept="", dept_id="", empName="",loginType;
	List<FeedbackPojo> ticketsList=null;
	List<DashboardPojo> dept_counterList =null;
	List<DashboardPojo> dept_subdeptcounterList  = null;
	DashboardPojo dashObj;
	Map<String, Integer> graphCatgMap = null;
	
	private String fromDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(),-5);
	private String toDate=DateUtil.getCurrentDateUSFormat();
	
	List<FeedbackPojo> catgCountList = null;
	
	
	public Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	public Integer page = 0;
	// sorting order - asc or desc
	public String sord = "";
	// get index row - i.e. user click to sort.
	public String sidx = "";
	// Search Field
	public String searchField = "";
	// The Search String
	public String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	public String searchOper = "";
	// Your Total Pages
	public Integer total = 0;
	// All Record
	public Integer records = 0;
	private boolean loadonce = true;
	// Grid colomn view
	public String oper;
	public String id;
	List<AssetDashboardBean> allAssetReminderList=null;
	List<AssetDashboardBean> allPendingPreventiveSupportList=null;
	
	// First method for Asset Complaint Dashboard
	public String beforeDashboardAction() 
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				  String userName = (String) session.get("uName");
				  ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				  AssetUniversalHelper AUH = new AssetUniversalHelper();
				  String logedDeptId=null;
				  logedDeptId = CUA.getEmpDataByUserName(userName)[3];
				  String userEmpID=null;
				  String empIdofuser = (String) session.get("empIdofuser");
				  if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;
				  userEmpID = empIdofuser.split("-")[1].trim();
				  String userContID = null;
				  userContID=CUA.getEmpDetailsByUserName("ASTM",userName)[0];
				 
					if (logedDeptId != null)
					{
						allAssetReminderList = new ArrayList<AssetDashboardBean>();
						allAssetReminderList = AUH.getAllReminderOfAsset(logedDeptId,userEmpID,userContID,connectionSpace);
					}
					if (logedDeptId!=null) 
					{
						allPendingPreventiveSupportList = new ArrayList<AssetDashboardBean>();
						allPendingPreventiveSupportList = AUH.getPendingPreventiveSupportData(userEmpID,connectionSpace);
					}
				  //********************************************//
				  //Scrolling Data(First Block)
				 // getTicketDetail(loginType,dept_id,empName, connectionSpace);
				  //********************************************//
				
				  //*******************************************/
				  //Ticket Counters on the basis of status(Second Block)
				  //getCounterData();
				  //********************************************//
				
				  //********************************************//				
				  //Level wise Ticket Detail in Table (Fifth Block)
				  //getTicketDetailByLevel("T");
				
				  //Level wise Ticket Detail in Graph (Fifth Block)
				  //getTicketDetailByLevel("G");
				  //********************************************//
				
				  //********************************************//
				  //Analytics for category in table (Third Block)
				 // getAnalytics4SubCategory("T");
				
				  //Analytics for category in graph (Third Block)
				  //getAnalytics4SubCategory("G");
				  //********************************************//
				
				  
				//getDataInBars(connectionSpace);
				return SUCCESS;
			} 
			catch (Exception e) 
			{
				addActionError("Ooops There Is Some Problem In beforeDashboardAction in AssetComplaintDashAction !!!");
				e.printStackTrace();
				return ERROR;
			}
		} 
		else 
		{
			return LOGIN;
		}
	}
	
	
	public void generalMethod()
	{
		try
		{
			String userName = (String) session.get("uName");
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "2");
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					empName=object[0].toString();
					dept_id=object[3].toString();
					dept=object[4].toString();
					loginType=object[7].toString();
					
				/*	if(loginType.equalsIgnoreCase("H"))
					{
						hodFlag=true;
						headerValue=dept;
					}
					else if (loginType.equalsIgnoreCase("M")) {
						mgmtFlag=true;
						headerValue="All Department";
					}
					else if (loginType.equalsIgnoreCase("N")) {
						headerValue=DateUtil.makeTitle(empName);
						normalFlag=true;
					}*/
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getTicketDetail(String status_for,String deptid,String empName, SessionFactory connectionSpace)
	 {
		try {
		//	System.out.println("ddddddd  "+deptid);
			List ticketData= new AssetDashboardHelper().getLodgedTickets(deptid, status_for, empName, connectionSpace);
			ticketsList = new ArrayList<FeedbackPojo>();
			System.out.println(">>>>>>> "+ticketData.size());
			if (ticketData!=null && ticketData.size()>0) {
				for (Iterator iterator = ticketData.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					FeedbackPojo fp=new FeedbackPojo();
					fp.setId(Integer.parseInt( object[0].toString()));
					fp.setTicket_no(object[1].toString());
					fp.setFeed_by(DateUtil.makeTitle(object[2].toString()));
					fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[3].toString()));
					fp.setOpen_time(object[4].toString().substring(0, 5));
					fp.setStatus(object[5].toString());
					ticketsList.add(fp);
				}
			}
			System.out.println(" >>>>>> "+ticketsList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	
	
	//Ticket Counters on the basis of status At HOD Level
	@SuppressWarnings("unchecked")
	public void getCounterData()
	 {
		dashObj=new DashboardPojo();
		//subdeptdashList=new ArrayList<DashboardPojo>();
		DashboardPojo DP=null;
		try {
			this.generalMethod();
			//dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
		   //String uid = new HelpdeskUniversalAction().getData("useraccount", "id", "userID", Cryptography.encrypt(userName,DES_ENCRYPTION_KEY));
		List dept_subdeptNameList = new AssetDashboardHelper().getDeptList(connectionSpace);
		if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
			for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
				Object[] object1 = (Object[]) iterator.next();
				DP=new DashboardPojo();
				DP.setId(object1[0].toString());
				DP.setDeptName(object1[1].toString());
				// List for counters for Pending/ Snooze/ High Priority/ Ignore Status
				List dept_subdeptCounterList = new ArrayList();
					dept_subdeptCounterList = new AssetDashboardHelper().getDashboardCounter("All",empName,object1[0].toString(),connectionSpace);
				
				if (dept_subdeptCounterList!=null && dept_subdeptCounterList.size()>0) {
					for (Iterator iterator2 = dept_subdeptCounterList.iterator(); iterator2.hasNext();) {
						Object[] object2 = (Object[]) iterator2.next();
						if (object2[0].toString().equals("High Priority")) {
							DP.setHpc(object2[1].toString());
						}
						else if (object2[0].toString().equals("Ignore")) {
							DP.setIgc(object2[1].toString());
						}
						else if (object2[0].toString().equals("Pending")) {
							DP.setPc(object2[1].toString());
						}
						else if (object2[0].toString().equals("Snooze")) {
							DP.setSc(object2[1].toString());
					    }
				     }
			     }
				// List for counters for Resolved Status
				List dept_subdeptRSCounterList = new ArrayList();
					dept_subdeptRSCounterList = new AssetDashboardHelper().getDashboardCounter("Resolved",empName,object1[0].toString(),connectionSpace);
				
				if (dept_subdeptRSCounterList!=null && dept_subdeptRSCounterList.size()>0) {
					for (Iterator iterator3 = dept_subdeptRSCounterList.iterator(); iterator3.hasNext();) {
						Object[] object3 = (Object[]) iterator3.next();
						 if (object3[0].toString().equals("Resolved")) {
							DP.setRc(object3[1].toString());
						}
				     }
			     }
				dept_subdeptcounterList.add(DP);
				dashObj.setDashList(dept_subdeptcounterList);
		     }
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void getAnalytics4SubCategory(String status_for)
	 {
		try {
			 this.generalMethod();
			 List catgData= new AssetDashboardHelper().getAnalyticalData("C",fromDate,toDate,dept_id,"-1","",empName,searchField,searchString,searchOper, connectionSpace);
			 if (catgData!=null && catgData.size()>0) {
	    		 if (status_for.equalsIgnoreCase("T")) {
	    			 catgCountList = new ArrayList<FeedbackPojo>();
	    			 for (Iterator iterator = catgData.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (status_for.equalsIgnoreCase("T")) {
								FeedbackPojo fp=new FeedbackPojo();
								fp.setId(Integer.parseInt(object[0].toString()));
								fp.setFeedback_catg(object[1].toString());
								fp.setCounter(object[2].toString());
								catgCountList.add(fp);
							}
	    			 }
	    		 }
	    		 else if (status_for.equalsIgnoreCase("G")) {
	    			 System.out.println("Inside G Block");
	    			 graphCatgMap=new LinkedHashMap<String, Integer>();
	    			 for (Iterator iterator = catgData.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							graphCatgMap.put(object[1].toString(),Integer.parseInt(object[2].toString()));
	    			 }
	    			 System.out.println("Map Size for graph  is:::::::::::::::::::::::: "+graphCatgMap.size());
			}
	    	 }} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	


	public List<FeedbackPojo> getCatgCountList() {
		return catgCountList;
	}


	public void setCatgCountList(List<FeedbackPojo> catgCountList) {
		this.catgCountList = catgCountList;
	}


	public List<FeedbackPojo> getTicketsList() {
		return ticketsList;
	}


	public void setTicketsList(List<FeedbackPojo> ticketsList) {
		this.ticketsList = ticketsList;
	}


	public List<DashboardPojo> getDept_counterList() {
		return dept_counterList;
	}


	public void setDept_counterList(List<DashboardPojo> dept_counterList) {
		this.dept_counterList = dept_counterList;
	}


	public Integer getRows() {
		return rows;
	}


	public void setRows(Integer rows) {
		this.rows = rows;
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


	public Map<String, Integer> getGraphCatgMap() {
		return graphCatgMap;
	}


	public void setGraphCatgMap(Map<String, Integer> graphCatgMap) {
		this.graphCatgMap = graphCatgMap;
	}


	public List<AssetDashboardBean> getAllAssetReminderList()
	{
		return allAssetReminderList;
	}


	public void setAllAssetReminderList(List<AssetDashboardBean> allAssetReminderList)
	{
		this.allAssetReminderList = allAssetReminderList;
	}


	public List<AssetDashboardBean> getAllPendingPreventiveSupportList() {
		return allPendingPreventiveSupportList;
	}


	public void setAllPendingPreventiveSupportList(
			List<AssetDashboardBean> allPendingPreventiveSupportList) {
		this.allPendingPreventiveSupportList = allPendingPreventiveSupportList;
	}
	
	
	
}
