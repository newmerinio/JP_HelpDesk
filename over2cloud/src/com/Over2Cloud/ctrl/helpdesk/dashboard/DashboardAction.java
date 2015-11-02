package com.Over2Cloud.ctrl.helpdesk.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.helpdesk.BeanUtil.Point;

@SuppressWarnings("serial")
public class DashboardAction extends GridPropertyBean {

	private String ticket_id;
	private String status_for;
	
	
    private String dataFlag="";
    private String feedStatus;
	private String dashFor="";
	private String level="";
	private String d_subdept_id="";
	
	private String dashboard;
	private String feedFor;
	private String fromDate=DateUtil.getCurrentDateUSFormat();
	private String toDate=DateUtil.getCurrentDateUSFormat();
	
	private String l_pending="0";
	private String l_snooze="0";
	private String l_hp="0";
	private String l_ignore="0";
	private String l_resolved="0";
	
	private String r_pending="0";
	private String r_snooze="0";
	private String r_hp="0";
	private String r_ignore="0";
	private String r_resolved="0";
	
	
	private String flag;
	private String feedFor1;
	private String feedFor2;
	private String deptHierarchy;
	private String caption;
	private String headerValue;
	
	Map<String, Integer> graphCatgMap = null;
	Map<String, Integer> graphLevelMap = null;
	List<FeedbackPojo> ticketsList=null;
	List<FeedbackPojo> mgmtTicketsList=null;
	List<DashboardPojo> levelTicketsList=null;
	List<FeedbackPojo> empCountList=null;
	List<FeedbackPojo> catgCountList=null;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	
	String dept="", dept_id="", empName="";
	DashboardPojo dashObj;
	DashboardPojo leveldashObj;
	DashboardPojo deptdashObj;
	private List<DashboardPojo> deptdashList=null;
	private List<DashboardPojo> subdeptdashList=null;
	List<DashboardPojo> dept_subdeptcounterList  = null;
	List<FeedbackPojo> feedbackList = null;
	FeedbackPojo FP = null;
	private String loginType;
	private List<Point>    points;
	private List<String>   objList;
	
	private boolean hodFlag;
	private boolean mgmtFlag;
	private String chartWidth;
	private boolean normalFlag;
	private String chartHeight;
	private String maximizeDivBlock;
	private Map<Integer, Integer> doubleMap;
	
	private String dataCheck="";
	
	
	public void generalMethod()
	{
		try
		{
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "2");
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					empName=object[0].toString();
					dept_id=object[3].toString();
					dept=object[4].toString();
					loginType=object[7].toString();
					
					/*System.out.println("Emp Name "+empName);
					System.out.println("Dept Id "+dept_id);
					System.out.println("Dept Name "+dept);
					System.out.println("Login Type "+loginType);
					*/
					
					if(loginType.equalsIgnoreCase("H"))
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
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// First method for Helpdesk Dashboard
	public String beforeDashboardAction() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				this.generalMethod();
			
				
				//********************************************//
				//Scrolling Data(First Block)
				  getTicketDetail(loginType,dept_id,empName, connectionSpace);
				//********************************************//
				
				//********************************************//
				//Ticket Counters on the basis of status(Second Block)
				  getCounterData();
				//********************************************//
				
				//********************************************//				
				//Level wise Ticket Detail in Table (Fifth Block)
				  getTicketDetailByLevel("T");
				
				//Level wise Ticket Detail in Graph (Fifth Block)
				  getTicketDetailByLevel("G");
				//********************************************//
				
				//********************************************//
				//Analytics for category in table (Third Block)
				  getAnalytics4SubCategory("T");
				
				//Analytics for category in graph (Third Block)
				  getAnalytics4SubCategory("G");
				//********************************************//
				
				//getDataInBars(connectionSpace);
				returnResult=SUCCESS;
			  } catch (Exception e) {
				addActionError("Ooops There Is Some Problem In beforeDashboardAction in DashboardAction !!!");
				e.printStackTrace();
				returnResult= ERROR;
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	public String maximizeDiv()
	{
		if (maximizeDivBlock!=null && !maximizeDivBlock.equals("") && (maximizeDivBlock.equals("catg_graph") || maximizeDivBlock.equals("catg_table") || maximizeDivBlock.equals("categ_doughnutChart")))
		{
			getAnalytics4SubCategory(status_for);
			return "SUCCESSCATEG";
		}
		else if (maximizeDivBlock!=null && !maximizeDivBlock.equals("") && (maximizeDivBlock.equals("level_graph") || maximizeDivBlock.equals("level_table")))
		{
			getBarChart4LevelCounters();
		}
		else if (maximizeDivBlock!=null && !maximizeDivBlock.equals("") && (maximizeDivBlock.equals("ticket_graph") || maximizeDivBlock.equals("ticket_table")||
				maximizeDivBlock.equals("ticket_columgraph")|| maximizeDivBlock.equals("ticket_Linegraph")|| maximizeDivBlock.equals("ticket_Bubblegraph")
				|| maximizeDivBlock.equals("ticket_PendingPiegraph")))
		{
			getBarChart4DeptCounters();
		}
		else if (maximizeDivBlock!=null && !maximizeDivBlock.equals("") && (maximizeDivBlock.equals("level_StackedBar") || maximizeDivBlock.equals("level_Column2Axes")
				|| maximizeDivBlock.equals("level_table")|| maximizeDivBlock.equals("level_LineChart")
				|| maximizeDivBlock.equals("level_BubbleChart")|| maximizeDivBlock.equals("level_PiePendingChart")))
		{
			getBarChart4LevelCounters();
			return "SUCCESSLEVEL";
		}
		return SUCCESS;
	}
	
	
	// For Bar Graph
	public String getBarChart4DeptCounters()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				this.generalMethod();
				getCounterData();
				jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				
				for (DashboardPojo pojo : dept_subdeptcounterList)
				 {
					jsonObject.put("dept",pojo.getDeptName());
					jsonObject.put("Pending", pojo.getPc());
					jsonObject.put("HighPriority", pojo.getHpc());
					jsonObject.put("Snooze", pojo.getSc());
					jsonObject.put("Ignore", pojo.getIgc());
					jsonObject.put("Resolved", pojo.getRc());
					
					jsonArray.add(jsonObject);
				 }
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	// For Bar Graph
	public String getBarChart4LevelCounters()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				this.generalMethod();
				getTicketDetailByLevel("T");
				
				jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				
				for (DashboardPojo pojo : levelTicketsList)
				 {
					jsonObject.put("Level",pojo.getLevel());
					jsonObject.put("Pending", pojo.getPc());
					jsonObject.put("HighPriority", pojo.getHpc());
					jsonObject.put("Snooze", pojo.getSc());
					jsonObject.put("Ignore", pojo.getIgc());
					jsonObject.put("Resolved", pojo.getRc());
					jsonArray.add(jsonObject);
				 }
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	//for pie chart
	public String getBarChart4CategCounters()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				this.generalMethod();
				 getAnalytics4SubCategory("T");
				
				jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				
				for (FeedbackPojo pojo : catgCountList)
				 {
					jsonObject.put("Category",pojo.getFeedback_catg());
					jsonObject.put("Counter", pojo.getCounter());
					
					jsonArray.add(jsonObject);
				 }
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	//Ticket Counters on the basis of status At HOD Level
	@SuppressWarnings("unchecked")
	public void getCounterData()
	 {
		dashObj=new DashboardPojo();
		DashboardPojo DP=null;
		try {
			this.generalMethod();
			flag =DateUtil.getCurrentDateIndianFormat().split("-")[0];
			dept_subdeptcounterList  = new ArrayList<DashboardPojo>();
		List dept_subdeptNameList = new HelpdeskUniversalHelper().getSubDeptListByUID(loginType,dept_id,empName,connectionSpace);
		if (dept_subdeptNameList!=null && dept_subdeptNameList.size()>0) {
			for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();) {
				Object[] object1 = (Object[]) iterator.next();
				DP=new DashboardPojo();
				DP.setId(object1[0].toString());
				DP.setDeptName(object1[1].toString());
				// List for counters for Pending/ Snooze/ High Priority/ Ignore Status
				List dept_subdeptCounterList = new ArrayList();
				
				if (loginType.equalsIgnoreCase("M")) {
					dept_subdeptCounterList = new HelpdeskUniversalHelper().getDashboardCounter("dept","All",empName,object1[0].toString(),connectionSpace);
				}
				else if (loginType.equalsIgnoreCase("H")) {
					dept_subdeptCounterList = new HelpdeskUniversalHelper().getDashboardCounter("subdept","All",empName,object1[0].toString(),connectionSpace);
				}
				else if (loginType.equalsIgnoreCase("N")) {
					dept_subdeptCounterList = new HelpdeskUniversalHelper().getDashboardCounter("subdept","Normal",empName,object1[0].toString(),connectionSpace);
				}
				
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
				if (loginType.equalsIgnoreCase("M")) {
					dept_subdeptRSCounterList = new HelpdeskUniversalHelper().getDashboardCounter("dept","Resolved",empName,object1[0].toString(),connectionSpace);
				}
				else if (loginType.equalsIgnoreCase("H")) {
					dept_subdeptRSCounterList = new HelpdeskUniversalHelper().getDashboardCounter("subdept","Resolved",empName,object1[0].toString(),connectionSpace);
				}
				else if (loginType.equalsIgnoreCase("N")) {
					dept_subdeptRSCounterList = new HelpdeskUniversalHelper().getDashboardCounter("subdept","empResolved",empName,object1[0].toString(),connectionSpace);
				}
				
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
	public void getTicketDetail(String status_for,String deptid,String empName, SessionFactory connectionSpace)
	 {
		try {
			List ticketData= new HelpdeskUniversalAction().getLodgedTickets(deptid, status_for, empName, connectionSpace);
			ticketsList = new ArrayList<FeedbackPojo>();
			if (ticketData!=null && ticketData.size()>0) {
				for (Iterator iterator = ticketData.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					FeedbackPojo fp=new FeedbackPojo();
					fp.setId(Integer.parseInt( object[0].toString()));
					fp.setTicket_no(object[1].toString());
					fp.setFeed_by(DateUtil.makeTitle(object[2].toString())+", "+object[7].toString());
					fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[3].toString())+", "+object[4].toString().substring(0, 5));
					fp.setFeedback_subcatg(object[10].toString());
					fp.setFeedback_catg(object[11].toString());
					fp.setAllot_to_mobno(object[8].toString()+", "+object[9].toString());
					//fp.setOpen_time(object[4].toString().substring(0, 5));
					if(object[5].toString().equalsIgnoreCase("Hold"))
						fp.setStatus("Seek Approval");
					else 
						fp.setStatus(object[5].toString());
					
					fp.setLocation(object[6].toString());
					ticketsList.add(fp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	@SuppressWarnings("unchecked")
	public void getTicketDetailByLevel(String status_for)
	 {
		try {
			this.generalMethod();
			if (status_for!=null && !status_for.equals("") && status_for.equals("T"))
			 {
				List levelList = new ArrayList();
				levelList.add("Level1");
				levelList.add("Level2");
				levelList.add("Level3");
				levelList.add("Level4");
				levelList.add("Level5");
				
				levelTicketsList = new ArrayList<DashboardPojo>();
				DashboardPojo dp=null;
				leveldashObj= new DashboardPojo();
				for (Iterator iterator = levelList.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					dp=new DashboardPojo();
					dp.setLevel(object.toString());
					List ticketData= new HelpdeskUniversalAction().getLevelTickets(dept_id, loginType,object.toString(),status_for,empName,"All", connectionSpace);
					if (ticketData!=null && ticketData.size()>0) {
						for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();) {
							Object[] object1 = (Object[]) iterator1.next();
							
							if (object1[0].toString().equals("High Priority")) {
								dp.setHpc(object1[1].toString());
							}
							else if (object1[0].toString().equals("Pending")) {
								dp.setPc(object1[1].toString());
							}
							else if (object1[0].toString().equals("Snooze")) {
								dp.setSc(object1[1].toString());
						    }
					    }
						ticketData.clear();
					}
					ticketData= new HelpdeskUniversalAction().getLevelTickets(dept_id, loginType,object.toString(),status_for,empName,"Resolved", connectionSpace);
					if (ticketData!=null && ticketData.size()>0) {
						for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();) {
							Object[] object1 = (Object[]) iterator1.next();
							
							 if (object1[0].toString().equals("Resolved")) {
								dp.setRc(object1[1].toString());
						    }
					    }
						ticketData.clear();
					}
					levelTicketsList.add(dp);
					leveldashObj.setDashList(levelTicketsList);
				}
			}
		else if (status_for!=null && !status_for.equals("") && status_for.equals("G")) 
		 {/*
			List ticketData= new HelpdeskUniversalAction().getLevelTickets(dept_id, loginType,"",status_for,empName, connectionSpace);
			if (ticketData!=null && ticketData.size()>0)
			 {
				for (Iterator iterator1 = ticketData.iterator(); iterator1.hasNext();) {
					Object[] object1 = (Object[]) iterator1.next();
					graphLevelMap = new LinkedHashMap<String, Integer>();
					graphLevelMap.put(object1[0].toString(), Integer.parseInt(object1[1].toString()));
				    }
			    }
		     */}	
		 } catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	@SuppressWarnings("unchecked")
	public void getAnalytics4SubCategory(String status_for)
	 {
		try {
			 this.generalMethod();
			 List catgData= new HelpdeskUniversalAction().getAnalyticalData("C",fromDate,toDate,dept_id,"-1",loginType,empName,searchField,searchString,searchOper, connectionSpace);
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
	    			 graphCatgMap=new LinkedHashMap<String, Integer>();
	    			 for (Iterator iterator = catgData.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							graphCatgMap.put(object[1].toString(),Integer.parseInt(object[2].toString()));
	    			 }
			}
	    	 }} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	@SuppressWarnings("unchecked")
	public String geTicketDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
				List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
				
				try {
					List dataList = new HelpdeskUniversalHelper().getTicketData(ticket_id, connectionSpace);
					if (dataList!=null && dataList.size()>0) {
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							FP = new FeedbackPojo();
							if (object[0]!=null && !object[0].toString().equals("")) {
								FP.setTicket_no(object[0].toString());
							}
							else {
								FP.setTicket_no("NA");
							}
							if (object[1]!=null && !object[1].toString().equals("")) {
								FP.setFeedback_by_dept(DateUtil.makeTitle(object[1].toString()));						
							}
							else {
								FP.setFeedback_by_dept("NA");
							}
							if (object[2]!=null && !object[2].toString().equals("")) {
								FP.setFeed_by(DateUtil.makeTitle(object[2].toString()));	
							}
							else {
								FP.setFeed_by("NA");
							}
							if (object[3]!=null && !object[3].toString().equals("")) {
								FP.setFeedback_by_mobno(object[3].toString());	
							}
							else {
								FP.setFeedback_by_mobno("NA");
							}
							if (object[4]!=null && !object[4].toString().equals("")) {
								FP.setFeedback_by_emailid(object[4].toString());	
							}
							else {
								FP.setFeedback_by_emailid("NA");
							}
							if (object[5]!=null && !object[5].toString().equals("")) {
								FP.setFeedback_to_dept(DateUtil.makeTitle(object[5].toString()));	
							}
							else {
								FP.setFeedback_to_dept("NA");
							}
							if (object[6]!=null && !object[6].toString().equals("")) {
								FP.setFeedback_allot_to(DateUtil.makeTitle(object[6].toString()));	
							}
							else {
								FP.setFeedback_allot_to("NA");
							}
							if (object[7]!=null && !object[7].toString().equals("")) {
								FP.setFeedtype(DateUtil.makeTitle(object[7].toString()));	
							}
							else {
								FP.setFeedtype("NA");
							}
							if (object[8]!=null && !object[8].toString().equals("")) {
								FP.setFeedback_catg(DateUtil.makeTitle(object[8].toString()));	
							}
							else {
								FP.setFeedback_catg("NA");
							}
							if (object[9]!=null && !object[9].toString().equals("")) {
								FP.setFeedback_subcatg(DateUtil.makeTitle(object[9].toString()));	
							}
							else {
								FP.setFeedback_subcatg("NA");
							}
							if (object[10]!=null && !object[10].toString().equals("")) {
								FP.setFeed_brief(DateUtil.makeTitle(object[10].toString()));	
							}
							else {
								FP.setFeed_brief("NA");
							}
							if (object[11]!=null && !object[11].toString().equals("")) {
								FP.setLocation(object[11].toString());	
							}
							else {
								FP.setLocation("NA");
							}
							if (object[12]!=null && !object[12].toString().equals("")) {
								FP.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));	
							}
							else {
								FP.setOpen_date("NA");
							}
							if (object[13]!=null && !object[13].toString().equals("")) {
								FP.setOpen_time(object[13].toString().substring(0, 5));	
							}
							else {
								FP.setOpen_time("NA");
							}
							if (object[14]!=null && !object[14].toString().equals("")) {
								FP.setLevel(object[14].toString());	
							}
							else {
								FP.setLevel("NA");
							}
							if (object[15]!=null && !object[15].toString().equals("")) {
								FP.setStatus(object[15].toString());	
							}
							else {
								FP.setStatus("NA");
							}
							if (object[16]!=null && !object[16].toString().equals("")) {
								FP.setVia_from(object[16].toString());	
							}
							else {
								FP.setVia_from("NA");
							}
							if (object[17]!=null && !object[17].toString().equals("")) {
								FP.setFeed_registerby(DateUtil.makeTitle(object[17].toString()));	
							}
							else {
								FP.setFeed_registerby("NA");
							}
							if (object[18]!=null && !object[18].toString().equals("")) {
								FP.setSn_reason(object[18].toString());	
							}
							else {
								FP.setSn_reason("NA");
							}
							if (object[19]!=null && !object[19].toString().equals("")) {
								FP.setSn_on_date(DateUtil.convertDateToIndianFormat(object[19].toString()));	
							}
							else {
								FP.setSn_on_date("NA");
							}
							if (object[20]!=null && !object[20].toString().equals("")) {
								FP.setSn_on_time(object[20].toString().substring(0, 5));	
							}
							else {
								FP.setSn_on_time("NA");
							}
							if (object[21]!=null && !object[21].toString().equals("")) {
								FP.setSn_date(DateUtil.convertDateToIndianFormat(object[21].toString()));	
							}
							else {
								FP.setSn_date("NA");
							}
							if (object[22]!=null && !object[22].toString().equals("")) {
								FP.setSn_time(object[22].toString().substring(0, 5));	
							}
							else {
								FP.setSn_time("NA");
							}
							if (object[23]!=null && !object[23].toString().equals("")) {
								FP.setSn_duration(object[22].toString());	
							}
							else {
								FP.setSn_duration("NA");
							}
							if (object[24]!=null && !object[24].toString().equals("")) {
								FP.setHp_date(DateUtil.convertDateToIndianFormat(object[24].toString()));	
							}
							else {
								FP.setHp_date("NA");
							}
							if (object[25]!=null && !object[25].toString().equals("")) {
								FP.setHp_time(object[25].toString().substring(0, 5));	
							}
							else {
								FP.setHp_time("NA");
							}
							if (object[26]!=null && !object[26].toString().equals("")) {
								FP.setHp_reason(object[26].toString());	
							}
							else {
								FP.setHp_reason("NA");
							}
							
							if (object[27]!=null && !object[27].toString().equals("")) {
								FP.setFeedback_to_subdept(DateUtil.makeTitle(object[27].toString()));	
							}
							else {
								FP.setFeedback_to_subdept("NA");
							}
							if (object[28]!=null && !object[28].toString().equals("")) {
								FP.setEscalation_date(DateUtil.convertDateToIndianFormat(object[28].toString()));	
							}
							else {
								FP.setEscalation_date("NA");
							}
							if (object[29]!=null && !object[29].toString().equals("")) {
								FP.setEscalation_time(object[29].toString().substring(0, 5));	
							}
							else {
								FP.setEscalation_time("NA");
							}
							if (object[30]!=null && !object[30].toString().equals("")) {
								FP.setFeedaddressing_time(DateUtil.convertDateToIndianFormat(object[30].toString().substring(0, 10))+"  ("+object[30].toString().substring(11, 16)+")");	
							}
							else {
								FP.setFeedaddressing_time("NA");
							}
							
							feedList.add(FP);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (status_for.equalsIgnoreCase("T")) {
					caption = "Ticket Detail";
					returnResult = "ticket_success";
				}
				else if (status_for.equalsIgnoreCase("FB")) {
					caption = "Lodged By Detail";
					returnResult = "feedby_success" ;
				}
		 }
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	public String getDashboard() {
		return dashboard;
	}
	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	
	@SuppressWarnings("unchecked")
	public String getFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			
			/*String userName=(String)session.get("uName");
			String deptLevel = (String)session.get("userDeptLevel");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");*/
			feedbackList = new ArrayList<FeedbackPojo>();
			
		//	int count = 0 ;
			List data=null;
			
			
			
			
			if (dashFor!=null && !dashFor.equals("") && dashFor.equalsIgnoreCase("M")) {
				
			}
			else if (dashFor!=null && !dashFor.equals("") && dashFor.equalsIgnoreCase("H")) {
				
			}
            else if (dashFor!=null && !dashFor.equals("") && dashFor.equalsIgnoreCase("N")) {
				
			}
			
			String dept_id="",empname="";
			List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), "2");
			if (empData!=null && empData.size()>0) {
				for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					empname=object[0].toString();
					dept_id=object[3].toString();
				}
			}
			
			if (dataFlag!=null && !dataFlag.equals("") && dataFlag.equals("L")) {
				d_subdept_id=dept_id;
			}
			data=new HelpdeskUniversalAction().getDataForDashboard(feedStatus,fromDate,toDate,dashFor, d_subdept_id,dataFlag,"HDM",empname,level,searchField,searchString,searchOper, connectionSpace);
		
			if(data!=null && data.size()>0){ 
			setRecords(data.size());
			int to = (getRows() * getPage());
			@SuppressWarnings("unused")
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
				if (deptLevel.equals("2")) {
					feedbackList=new HelpdeskUniversalHelper().setFeedbackValues(data,deptLevel,getFeedStatus());
					dataCheck="Confirm";
				}
				
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			}
			returnResult = SUCCESS;
		}
		catch(Exception e)
		{
			 addActionError("Ooops!!! There is some problem in getting Feedback Data");
			 e.printStackTrace();
		}
		}
		else {
			returnResult=LOGIN;
		}
		return returnResult;
	}
	

    
	

	public String getFeedFor() {
		return feedFor;
	}
	public void setFeedFor(String feedFor) {
		this.feedFor = feedFor;
	}


	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<FeedbackPojo> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<FeedbackPojo> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public String getL_pending() {
		return l_pending;
	}
	public void setL_pending(String l_pending) {
		this.l_pending = l_pending;
	}


	public String getL_snooze() {
		return l_snooze;
	}
	public void setL_snooze(String l_snooze) {
		this.l_snooze = l_snooze;
	}


	public String getL_hp() {
		return l_hp;
	}
	public void setL_hp(String l_hp) {
		this.l_hp = l_hp;
	}


	public String getL_ignore() {
		return l_ignore;
	}
	public void setL_ignore(String l_ignore) {
		this.l_ignore = l_ignore;
	}


	public String getL_resolved() {
		return l_resolved;
	}
	public void setL_resolved(String l_resolved) {
		this.l_resolved = l_resolved;
	}


	public String getR_pending() {
		return r_pending;
	}
	public void setR_pending(String r_pending) {
		this.r_pending = r_pending;
	}


	public String getR_snooze() {
		return r_snooze;
	}
	public void setR_snooze(String r_snooze) {
		this.r_snooze = r_snooze;
	}


	public String getR_hp() {
		return r_hp;
	}
	public void setR_hp(String r_hp) {
		this.r_hp = r_hp;
	}

	
	public String getR_ignore() {
		return r_ignore;
	}
	public void setR_ignore(String r_ignore) {
		this.r_ignore = r_ignore;
	}


	public String getR_resolved() {
		return r_resolved;
	}
	public void setR_resolved(String r_resolved) {
		this.r_resolved = r_resolved;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFeedFor1() {
		return feedFor1;
	}

	public void setFeedFor1(String feedFor1) {
		this.feedFor1 = feedFor1;
	}

	public String getFeedFor2() {
		return feedFor2;
	}

	public void setFeedFor2(String feedFor2) {
		this.feedFor2 = feedFor2;
	}

	public String getDeptHierarchy() {
		return deptHierarchy;
	}

	public void setDeptHierarchy(String deptHierarchy) {
		this.deptHierarchy = deptHierarchy;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDashFor() {
		return dashFor;
	}

	public void setDashFor(String dashFor) {
		this.dashFor = dashFor;
	}

	public String getD_subdept_id() {
		return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id) {
		this.d_subdept_id = d_subdept_id;
	}
	public String getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
	}
	

	public List<DashboardPojo> getDept_subdeptcounterList() {
		return dept_subdeptcounterList;
	}


	public void setDept_subdeptcounterList(
			List<DashboardPojo> dept_subdeptcounterList) {
		this.dept_subdeptcounterList = dept_subdeptcounterList;
	}



	public DashboardPojo getDashObj() {
		return dashObj;
	}


	public void setDashObj(DashboardPojo dashObj) {
		this.dashObj = dashObj;
	}


	public List<DashboardPojo> getSubdeptdashList() {
		return subdeptdashList;
	}



	public void setSubdeptdashList(List<DashboardPojo> subdeptdashList) {
		this.subdeptdashList = subdeptdashList;
	}



	public DashboardPojo getDeptdashObj() {
		return deptdashObj;
	}



	public void setDeptdashObj(DashboardPojo deptdashObj) {
		this.deptdashObj = deptdashObj;
	}



	public List<DashboardPojo> getDeptdashList() {
		return deptdashList;
	}



	public void setDeptdashList(List<DashboardPojo> deptdashList) {
		this.deptdashList = deptdashList;
	}



/*	public Map<String, Integer> getGraphEmpMap() {
		return graphEmpMap;
	}
	public void setGraphEmpMap(Map<String, Integer> graphEmpMap) {
		this.graphEmpMap = graphEmpMap;
	}*/



	public Map<String, Integer> getGraphCatgMap() {
		return graphCatgMap;
	}
	public void setGraphCatgMap(Map<String, Integer> graphCatgMap) {
		this.graphCatgMap = graphCatgMap;
	}


	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Map<Integer, Integer> getDoubleMap() {
		return doubleMap;
	}
	public void setDoubleMap(Map<Integer, Integer> doubleMap) {
		this.doubleMap = doubleMap;
	}



	public List<String> getObjList() {
		return objList;
	}
	public void setObjList(List<String> objList) {
		this.objList = objList;
	}


	public List<FeedbackPojo> getTicketsList() {
		return ticketsList;
	}
	public void setTicketsList(List<FeedbackPojo> ticketsList) {
		this.ticketsList = ticketsList;
	}


	public String getTicket_id() {
		return ticket_id;
	}



	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}



	public String getStatus_for() {
		return status_for;
	}



	public void setStatus_for(String status_for) {
		this.status_for = status_for;
	}



	public FeedbackPojo getFP() {
		return FP;
	}
	public void setFP(FeedbackPojo fp) {
		FP = fp;
	}



	public String getCaption() {
		return caption;
	}



	public void setCaption(String caption) {
		this.caption = caption;
	}



	public List<FeedbackPojo> getMgmtTicketsList() {
		return mgmtTicketsList;
	}



	public void setMgmtTicketsList(List<FeedbackPojo> mgmtTicketsList) {
		this.mgmtTicketsList = mgmtTicketsList;
	}



	public DashboardPojo getLeveldashObj() {
		return leveldashObj;
	}



	public void setLeveldashObj(DashboardPojo leveldashObj) {
		this.leveldashObj = leveldashObj;
	}



	public List<DashboardPojo> getLevelTicketsList() {
		return levelTicketsList;
	}



	public void setLevelTicketsList(List<DashboardPojo> levelTicketsList) {
		this.levelTicketsList = levelTicketsList;
	}



	public List<FeedbackPojo> getEmpCountList() {
		return empCountList;
	}



	public void setEmpCountList(List<FeedbackPojo> empCountList) {
		this.empCountList = empCountList;
	}



	public List<FeedbackPojo> getCatgCountList() {
		return catgCountList;
	}



	public void setCatgCountList(List<FeedbackPojo> catgCountList) {
		this.catgCountList = catgCountList;
	}

	


	public String getHeaderValue() {
		return headerValue;
	}


	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}

	public String getMaximizeDivBlock() {
		return maximizeDivBlock;
	}


	public void setMaximizeDivBlock(String maximizeDivBlock) {
		this.maximizeDivBlock = maximizeDivBlock;
	}


	public String getChartWidth() {
		return chartWidth;
	}


	public void setChartWidth(String chartWidth) {
		this.chartWidth = chartWidth;
	}


	public String getChartHeight() {
		return chartHeight;
	}


	public void setChartHeight(String chartHeight) {
		this.chartHeight = chartHeight;
	}


	public Map<String, Integer> getGraphLevelMap() {
		return graphLevelMap;
	}


	public void setGraphLevelMap(Map<String, Integer> graphLevelMap) {
		this.graphLevelMap = graphLevelMap;
	}


	


	public String getLoginType() {
		return loginType;
	}


	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}


	public boolean isHodFlag() {
		return hodFlag;
	}


	public void setHodFlag(boolean hodFlag) {
		this.hodFlag = hodFlag;
	}


	public boolean isMgmtFlag() {
		return mgmtFlag;
	}


	public void setMgmtFlag(boolean mgmtFlag) {
		this.mgmtFlag = mgmtFlag;
	}


	public boolean isNormalFlag() {
		return normalFlag;
	}


	public void setNormalFlag(boolean normalFlag) {
		this.normalFlag = normalFlag;
	}

	public String getDataCheck() {
		return dataCheck;
	}

	public void setDataCheck(String dataCheck) {
		this.dataCheck = dataCheck;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}



	public JSONArray getJsonArray()
	{
		return jsonArray;
	}



	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}



	public JSONObject getJsonObject()
	{
		return jsonObject;
	}



	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}
	
}