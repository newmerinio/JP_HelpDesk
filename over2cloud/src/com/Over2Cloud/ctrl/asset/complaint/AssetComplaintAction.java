package com.Over2Cloud.ctrl.asset.complaint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.worktimeing.WorkingTimeHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssetComplaintAction extends ActionSupport implements ServletRequestAware
{
  private static final long serialVersionUID = 1L;
  @SuppressWarnings("unchecked")
  Map session = ActionContext.getContext().getSession();
  public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	String orgLevel = (String) session.get("orgnztnlevel");
	String orgId = (String) session.get("mappedOrgnztnId");
	String deptLevel = (String) session.get("userDeptLevel");
	SessionFactory connectionSpace = (SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	
	@SuppressWarnings("unchecked")
	List assetFieldList = null; 
	@SuppressWarnings("unchecked")
	List empData4Escalation =null;
	AssetPojo assetObj=null;
	private Map<Integer, String> serviceDeptList = null;
	private Map<String, String> assetDetailList = null;
	private JSONArray commonJSONArray=null;
	EmpBasicPojoBean empObj =null;
	List<GridDataPropertyView> feedbackColumnNames = null;
	List<AssetComplaintPojo> feedbackList = null;
	private String dataFor;
    private String moduleName;
    private String uniqueid;
    private String feedType;
    
    
    private String todept;
    private String bydept;
    private String catg;
    private String subCatg;
    private String open_date;
    private String open_time;
    private String addrDate;
    private String addrTime;
    
    private String selectedId;
	private String feedbackBy;
	private String mobileno;
	private String emailId;
    private String viaFrom;
    private String feed_brief;
    private String asset_id;
    private String asset;
    private String serialno;
    
    private String ticketno;
    private String complainantResTime;
    private String allotto_name;
    private String allotto_mobno;
    
    private String orgName = "";
	private String address = "";
	private String city = "";
	private String pincode = "";
	
	
	private String feedStatus;
	private String fromDate;
	private String toDate;
	
	
	private String resolveon;
	private String resolveat;
	private String spareused;
	private String actiontaken;
	private String recvSMS;
	private String recvEmail;
	
	String  escalation_date = "NA",escalation_time = "NA",resolution_date = "NA",
	resolution_time = "NA",level_resolution_date = "NA", level_resolution_time = "NA",non_working_timing="00:00";
	
    
 // Grid Variables Declaration
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
	private boolean loadonce = true;
	// Grid column view
	private String oper;
	private int id;
	
	
	public String justCheck()
	{
		try {
			assetDetailList  =new LinkedHashMap<String, String>();
			AssetComplaintHelper ACH = new AssetComplaintHelper();
			List assetDetail =	ACH.getAssetNewDetail(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY),connectionSpace);
			if (assetDetail != null && assetDetail.size() > 0) {
				
				for (Iterator iterator = assetDetail.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					assetDetailList.put(object[0].toString(), object[1].toString()+"-"+ object[2].toString());
				}
			}
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String firstActionMethod()
	 {
		String returnResult = ERROR;
	    boolean sessionFlag = ValidateSession.checkSession();
	    if (sessionFlag)
		{
			@SuppressWarnings("unused")
			CommonOperInterface cot = new CommonConFactory().createInterface();
			AssetComplaintHelper ACH = new AssetComplaintHelper();
			try
			{
				// Get Lodge Feedback Level and Fields Name
				if (dataFor != null && !dataFor.equals("") && (dataFor.equals("call") || dataFor.equals("online"))) {
					if (dataFor.equals("call")) {
						getAssetComplaintFields(dataFor);
						//getAssetDetail(dataFor);
						returnResult = "call_success";
					} 
					else if (dataFor.equals("online")) {
					getAssetComplaintFields(dataFor);
					assetDetailList = new LinkedHashMap<String, String>();
					List assetDetail =	ACH.getAssetDetail(dataFor,Cryptography.encrypt(userName,DES_ENCRYPTION_KEY),connectionSpace);
						if (assetDetail != null && assetDetail.size() > 0) {
							
							for (Iterator iterator = assetDetail.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								assetDetailList.put(object[0].toString(), object[1].toString());
							}
						}
						returnResult = "online_success";
					}
				}
				if (moduleName!=null && !moduleName.equals("")) {
					serviceDeptList = new LinkedHashMap<Integer, String>();
					List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept(deptLevel,orgLevel, orgId,moduleName, connectionSpace);
					if (departmentlist != null && departmentlist.size() > 0) {
						for (Iterator iterator = departmentlist.iterator(); iterator
								.hasNext();) {
							Object[] object1 = (Object[]) iterator.next();
							serviceDeptList.put((Integer) object1[0], object1[1]
									.toString());
						}
					  }
					}
			 }
			catch (Exception e)
			 {
			 	e.printStackTrace();
			 }
		}
	    else {
			returnResult = LOGIN;
		}
		return returnResult;
	 } 
	
	@SuppressWarnings({ "unchecked"})
	public synchronized String registerFeedback() {
	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
			AssetComplaintHelper ACH = new AssetComplaintHelper();
			HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
			MsgMailCommunication MMC = new MsgMailCommunication();
			CommonOperInterface cot = new CommonConFactory().createInterface();
			List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_asset_complaint_configuration", "",connectionSpace, "", 0, "table_name","asset_complaint_configuration");

			if (colName != null && colName.size() > 0) {
				@SuppressWarnings("unused")
				boolean status = false;
				List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
				for (GridDataPropertyView tableColumes : colName) {
					if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("serialno") 
							&& !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category"))
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(tableColumes.getColomnName());
						ob1.setDatatype("varchar("+ tableColumes.getWidth() + ")");
						ob1.setConstraint("default NULL");
						TableColumnName.add(ob1);
					}
				}

				// Method calling for creating table on the basis of above
				cot.createTable22("asset_complaint_status", TableColumnName,connectionSpace);

				//System.out.println("Inside Register Feedback method");
				
				// Object creation for Feedback Pojo Bean
				FeedbackPojo fbp = null;
				// Local variables declaration
				String outlet_id ="";
				String   adrr_time = "",res_time = "", allottoId = "",  by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", mailText = "", tolevel = "", needesc = "";
				String assetId="NA";
				// Code for getting the Escalation Date and Escalation Time
				
				//System.out.println("Sub category Id is   "+subCatg);
				
				List subCategoryList = HUH.getAllData("feedback_subcategory", "id",subCatg, "subCategoryName", "ASC",connectionSpace);
				if (subCategoryList != null && subCategoryList.size() > 0) {
					for (Iterator iterator = subCategoryList.iterator(); iterator
							.hasNext();) {
						Object[] objectCol = (Object[]) iterator.next();
						if (objectCol[4] == null) {
							adrr_time = "06:00";
						}
						else {
							adrr_time = objectCol[4].toString();
						}
						if (objectCol[5] == null) {
							res_time = "10:00";
						}
						else {
							res_time = objectCol[5].toString();
						}
						
						if (objectCol[8] == null) {
							tolevel = "Level1";
						}
						else {
						tolevel = objectCol[8].toString();
						}
						if (objectCol[9] == null) {
							needesc = "Y";
						} else {
							needesc = objectCol[9].toString();
						}
					  }
				    }
				    assetId = request.getParameter("assetid");
				    todept = new HelpdeskUniversalAction().getData("asset_detail", "dept_subdept", "id", assetId);
				
				    getEscalationDateTime(adrr_time,res_time,needesc,todept,"ASTM");
                    /*System.out.println("Escalation Date         "+escalation_date);
                    System.out.println("Escalation Time         "+escalation_time);
                    System.out.println("Resolution Date         "+resolution_date);
                    System.out.println("Resolution Time         "+resolution_time);
                    System.out.println("Level Resolution Date   "+level_resolution_date);
                    System.out.println("Level Resolution Time   "+level_resolution_time);*/
                    
                    
				// Block for setting the Allotment Id/Feedback By
				// Name/Feedback By Mobile No/ Feedback By email Id and
				// ToDept/ByDept OR To Sub Dept/By Sub dept in the case of
				// Via Call feedback Lodging
				if (viaFrom != null && viaFrom.equalsIgnoreCase("call")) {
					String[] selectedIdArr;
					feedby = feedbackBy;
					feedbymob = mobileno;
					feedbyemailid = emailId;
					assetId=asset_id;

					if (selectedId.contains(",")) {
						selectedIdArr = selectedId.split(",");
						for (int i = 0; i < selectedIdArr.length; i++) {
							allottoId = selectedIdArr[i];
							break;
						}
					} else {
						allottoId = selectedId;
					}
				}
				
				// Block for setting the Allotment Id/Feedback By
				// Name/Feedback By Mobile No/ Feedback By email Id and
				// ToDept/ByDept OR To Sub Dept/By Sub dept in the case of
				// Online feedback Lodging
				if (viaFrom != null && viaFrom.equalsIgnoreCase("Online")) {
					//System.out.println("Inside Via OnLine Mode");
					//todept = "1";
					
					//System.out.println("To dept Id is  "+todept);
					//System.out.println("Asset Id  "+assetId);
					outlet_id = new HelpdeskUniversalAction().getData("asset_allotment_detail", "outletid", "assetid", assetId);
					//System.out.println("Outlet id is    "+outlet_id);
					List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY),deptLevel);
					if (empData != null && empData.size() > 0) {
						for (Iterator iterator = empData.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null
									&& !object[0].toString().equals("")) {
								feedby = object[0].toString();
							} else {
								feedby = "NA";
							}

							if (object[1] != null
									&& !object[1].toString().equals("")) {
								feedbymob = object[1].toString();
							} else {
								feedbymob = "NA";
							}

							if (object[2] != null
									&& !object[2].toString().equals("")) {
								feedbyemailid = object[2].toString();
							} else {
								feedbyemailid = "NA";
							}

							if (object[3] != null && !object[3].toString().equals("")) {
								bydept = object[3].toString();
							} else {
								bydept = "-1";
							}
						}
					}

				

					List allotto = null;
					List allotto1 = null;
					List<String> one = new ArrayList<String>();
					List<String> two = new ArrayList<String>();
					List<String> two_new = new ArrayList<String>();
					//String floor_status= HUH.getFloorStatus(getTosubdept(),connectionSpace);
					try {
							allotto = ACH.getRandomEmp4Escalation(outlet_id,tolevel.substring(5),connectionSpace);
							if (allotto==null || allotto.size()==0) {
								String newToLevel= "";
									newToLevel = ""+(Integer.parseInt(tolevel.substring(5))+1);
									allotto = ACH.getRandomEmp4Escalation(outlet_id,newToLevel,connectionSpace);
									if (allotto==null || allotto.size()==0) {
										newToLevel = ""+(Integer.parseInt(newToLevel)+1);
										allotto = ACH.getRandomEmp4Escalation(outlet_id,newToLevel,connectionSpace);
										if (allotto==null || allotto.size()==0) {
											newToLevel = ""+(Integer.parseInt(newToLevel)+1);
											allotto = ACH.getRandomEmp4Escalation(outlet_id,newToLevel,connectionSpace);
										}
									}
							}
                        if (allotto!=null && allotto.size()>0) {
							
						
						if (deptLevel.equals("2")) {
							allotto1 = ACH.getRandomEmployee(todept,tolevel.substring(5),allotto, connectionSpace);
						} 
                       
							for (Object object : allotto) {
								one.add(object.toString());
							}
						
						if (allotto1!=null && allotto1.size()>0) {
						for (Object object : allotto1) {
							two.add(object.toString());
						}
						}
						
						if (one!=null && one.size()>two.size()) {
							one.removeAll(two);
							if (one.size() > 0) {
								for (Iterator iterator = one.iterator(); iterator
										.hasNext();) {
									Object object = (Object) iterator.next();
									allottoId = object.toString();
									break;
								}
						    }
					    } 
						else {
							List pending_alloted = ACH.getPendingAllotto(todept,connectionSpace);
							if (pending_alloted!=null && pending_alloted.size()>0) {
								for (Object object : pending_alloted) {
									two_new.add(object.toString());
								}
							}
							
							if (two_new!=null && two_new.size()>0) {
								one.removeAll(two_new);
							}
							if (one!=null && one.size()>0) {
								allottoId = ACH.getRandomEmployee(one,connectionSpace);
							}
							else {
								allottoId = ACH.getRandomEmployee(allotto,connectionSpace);
							}
						}
						}}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				String ticketLevel = "";
				if (allottoId!=null && !allottoId.equals("")) {
					 ticketLevel = ACH.getTicketLevelOutletWise(allottoId,outlet_id,"ASTM", connectionSpace);
				}
					System.out.println("Real Time level is  "+ticketLevel);
					/*
					allottoId="1";
					String ticketLevel="Level1";*/
				
				//String deptid = HUA.getData("subdepartment", "deptid", "id", todept);
				// Block for getting Ticket No
				//System.out.println("Department Id  "+todept);
				ticketno = HUH.getTicketNo(outlet_id,"CASTM", connectionSpace);
				//System.out.println(" Ticket Series  "+ticketno);
				if (ticketno!=null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId!=null && !allottoId.equals("")) {
				//System.out.println("Inside tttttttttttt");
				//System.out.println("Ticket no  "+ticketno);
				//System.out.println("Allot To Id   "+allottoId);
					// Setting the column values after getting from the page
				InsertDataTable ob = new InsertDataTable();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				ob.setColName("ticket_no");
				ob.setDataName(ticketno);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by");
				ob.setDataName(feedby);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by_mobno");
				ob.setDataName(feedbymob);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_by_emailid");
				ob.setDataName(feedbyemailid);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("asset_id");
				ob.setDataName(assetId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("by_dept");
				ob.setDataName(bydept);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("to_dept");
				ob.setDataName(todept);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("subCatg");
				ob.setDataName(subCatg);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_brief");
				ob.setDataName(feed_brief);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("allot_to");
				ob.setDataName(allottoId);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("escalation_date");
				ob.setDataName(escalation_date);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("escalation_time");
				ob.setDataName(escalation_time);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("location");
				ob.setDataName(outlet_id);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("level");
				ob.setDataName(ticketLevel);
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("status");
				ob.setDataName("Pending");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("via_from");
				ob.setDataName(DateUtil.makeTitle(viaFrom));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("feed_registerby");
				ob.setDataName(userName);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("moduleName");
				ob.setDataName("ASTM");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("non_working_time");
				ob.setDataName(non_working_timing);
				insertData.add(ob);

				// Method calling for inserting the values in the
				// feedback_status table
				boolean status1 = cot.insertIntoTable("asset_complaint_status",insertData, connectionSpace);
				insertData.clear();

				// Block for sending SMS and Mail to the Engineer and
				// status table successfully
				if (status1) {
					// List for holding the data of currently lodged
					List data = ACH.getFeedbackDetail(ticketno,"Pending", "", connectionSpace);
					fbp = ACH.setFeedbackDataValues(data, "Pending",deptLevel);
					printTicket(fbp);
					if (fbp != null) {
						// Block for sending sms for Level1 Engineer
						if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10  &&  !fbp.getMobOne().startsWith("NA") && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7"))) {
							String levelMsg = "Open Feedback Alert: Ticket No: "+ fbp.getTicket_no()+ ", To Be Closed By: "+ DateUtil.convertDateToIndianFormat(level_resolution_date)+ ","+ level_resolution_time.substring(0, 5)+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ", Location: "+ fbp.getLocation()+ ", Brief: "+ fbp.getFeed_brief()+ ".";
							MMC.addMessage(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getMobOne(), levelMsg, ticketno,"Pending", "0","HDM");
						}
						// Block for sending sms for Complainant
						if (getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7"))) {
							String complainMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ","+ " Ticket No: "+ fbp.getTicket_no()+ ", Resolution Time: "+ DateUtil.convertDateToIndianFormat(resolution_date)+ ","+ resolution_time.substring(0, 5)+ ", Location: " + fbp.getLocation()+ ", Brief: " + fbp.getFeed_brief()+ " is open.";
							MMC.addMessage(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_mobno(), complainMsg,ticketno,"Pending", "0", "HDM");
						}
						// Block for creating mail for Level1 Engineer
						if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA")) {
							mailText = HUH.getConfigMessage4Asset(fbp,"Open Feedback Alert","Pending", deptLevel, true);
							MMC.addMail(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getEmailIdOne(),"Open Feedback Alert", mailText,ticketno, "Pending", "0","","HDM");
						}
						// Block for creating mail for complainant
						if (getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("") && !fbp.getFeedback_by_emailid().equals("NA")) {
							mailText = HUH.getConfigMessage4Asset(fbp,"Open Feedback Alert","Pending", deptLevel, false);
							MMC.addMail(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_emailid(),"Open Feedback Alert", mailText,ticketno,"Pending", "0","","HDM");
						}
					}
					addActionMessage("Feedback Lodge Successfully!!!");
				}
				returnResult = SUCCESS;
				}
				else{
					//System.out.println("Inside final Else Block");
					if (ticketno==null || ticketno.equalsIgnoreCase("") || ticketno.equalsIgnoreCase("NA")) {
						//System.out.println("When Ticket No is not exist");
						addActionMessage("Please Check Your Ticket Series!!!");
					}
					if (allottoId==null || allottoId.equalsIgnoreCase("") || allottoId.equalsIgnoreCase("-1")) {
						//System.out.println("When Allot To Id is not exist");
						addActionMessage("May be Someone is not mapped for taking the complaint. Please contact to concern service department !!!");
					}
					returnResult = "TicketError";
				}
				//returnResult = SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			lock.unlock();
		}
	} else {
		returnResult = LOGIN;
	}
	//System.out.println("Return result Value is   "+returnResult);
	return returnResult;}
	
	
	
	public void getEscalationDateTime(String adrr_time, String res_time, String needesc,String toDept, String module)
	{
		String date_time="",level_date_time="";
		String holiday_status="",startTime="",endTime="",callflag="";
		String dept_id = toDept;
		String date = DateUtil.getCurrentDateUSFormat();
		String currentday= DateUtil.getCurrentDayofWeek();
		//List of working timing data
		List workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
		if (workingTimingData!=null && workingTimingData.size()>0) {
				holiday_status=workingTimingData.get(0).toString();
		}
		//System.out.println("Holiday Status  ");
		if (holiday_status.equals("Y") || holiday_status.equals("N") ) {
            // Name should be different for date & current day
			if (holiday_status.equals("Y")) {
				date = new WorkingTimeHelper().getWorkingDate(DateUtil.getCurrentDateUSFormat());
				//System.out.println("Inside yes Working date   "+date);
				currentday= DateUtil.getDayofDate(date);
				//System.out.println("Inside yes Working day   "+currentday);
			}
			workingTimingData.clear();
			workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
			if (workingTimingData!=null && workingTimingData.size()>0)
			  {
					startTime=workingTimingData.get(1).toString();
					endTime=workingTimingData.get(2).toString();
					// Here we know the complaint lodging time is inside the the start and end time or not
					callflag=DateUtil.timeInBetween(date,startTime, endTime, DateUtil.getCurrentTime());
					//System.out.println("Call Flag Value is   "+callflag);
					if (callflag!=null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
					 {
						//System.out.println("Inside before");
							date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
							
							//(Differrence between current date/time and date & starttime)
							String newdatetime[]= date_time.split("#");
							// Check the date time is lying inside the time block
							boolean flag= DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], date, endTime);
							if (flag) {
								level_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, "00:00");
								non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							}
							else {
								String timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
								String timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, endTime);
							    String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
								workingTimingData.clear();
								
								date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date),1));
								currentday= DateUtil.getDayofDate(date);
								
								workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
								if (workingTimingData!=null && workingTimingData.size()>0) {
										startTime=workingTimingData.get(1).toString();
											String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
											date_time=DateUtil.newdate_AfterTime(new_date_time,main_difference);
											String level_difference=DateUtil.getTimeDifference(main_difference, res_time);
											level_date_time = DateUtil.newdate_AfterTime(new_date_time,level_difference);
											non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
								}
							}
						}
						else if (callflag!=null && !callflag.equals("") && callflag.equalsIgnoreCase("middle")) {
							//System.out.println("Inside middle");
							//Escalation date time at the time of opening the ticket 
							date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, res_time);
							//System.out.println("New Date Time  "+date_time);
							String newdatetime[]= date_time.split("#");
							// Check the date time is lying inside the time block
							boolean flag= DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], DateUtil.getCurrentDateUSFormat(), endTime);
							//System.out.println("Flag Value is  "+flag);
							if (flag) {
								//System.out.println("Inside flag true");
								level_date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, "00:00");
								non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							}
							else {
								//System.out.println("Inside flag false");
								String timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
								//System.out.println("First difference "+timeDiff1);
								String timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), DateUtil.getCurrentDateUSFormat(), endTime);
								//System.out.println("Second difference "+timeDiff2);
								String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
							//	System.out.println("Main Differnce "+timeDiff2);
								workingTimingData.clear();
								
								date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date),1));
								//System.out.println("New Current date  "+date);
								currentday= DateUtil.getDayofDate(date);
								//System.out.println("New Current day  "+currentday);
								
								workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
								//System.out.println("New Working Time data  "+workingTimingData);
								if (workingTimingData!=null && workingTimingData.size()>0) {
										startTime=workingTimingData.get(1).toString();
									//	System.out.println("New Start time  "+startTime);
											String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
										//	System.out.println("New Date time  "+new_date_time);
										//	System.out.println("main Differnce   "+main_difference);
											date_time=DateUtil.newdate_AfterTime(new_date_time,main_difference);
										//	System.out.println("Updated date time Date time  "+date_time);
											String level_difference=DateUtil.getTimeDifference(main_difference, res_time);
										//	System.out.println("Difference at level   "+level_difference);
											level_date_time = DateUtil.newdate_AfterTime(new_date_time,level_difference);
										//	System.out.println("Level Date Time    "+level_date_time);
											non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
										//	System.out.println("Final non working time  "+non_working_timing);
								}
							}
						}
						else if (callflag!=null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
						  {
							workingTimingData.clear();
							if (holiday_status.equalsIgnoreCase("Y")) {
								date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateAfter(1));
								currentday= DateUtil.getDayofDate(date);
							}
							else if (holiday_status.equalsIgnoreCase("N")) {
								date = DateUtil.getNextDateAfter(1);
								currentday= DateUtil.getDayofDate(date);
							}
							
							workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
							if (workingTimingData!=null && workingTimingData.size()>0)
							 {
								startTime=workingTimingData.get(1).toString();
								endTime=workingTimingData.get(2).toString();
								date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
								String newdatetime[]= date_time.split("#");
								// Check the date time is lying inside the time block
								boolean flag= DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], newdatetime[0], endTime);
								if (flag) {
								//	System.out.println("Inside Flag true");
								//	System.out.println("Date Value  "+date);
								//	System.out.println("Start Time Value  "+startTime);
									level_date_time = DateUtil.newdate_AfterEscInRoaster(date,startTime, adrr_time, "00:00");
									non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
								}
								else 
								 {
									String timeDiff1=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
									String timeDiff2=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, endTime);
								    String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
									workingTimingData.clear();
									
									date = new WorkingTimeHelper().getWorkingDate(DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date),1));
									currentday= DateUtil.getDayofDate(date);
									
									//String nextday= DateUtil.getNextDayofWeek();
									workingTimingData= new WorkingTimeHelper().getWorkingTimeData(module,currentday,dept_id);
									if (workingTimingData!=null && workingTimingData.size()>0)
									  {
											startTime=workingTimingData.get(1).toString();
												String new_date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, "00:00", "00:00");
												date_time=DateUtil.newdate_AfterTime(new_date_time,main_difference);
												String level_difference=DateUtil.getTimeDifference(main_difference, res_time);
												level_date_time = DateUtil.newdate_AfterTime(new_date_time,level_difference);
												non_working_timing=DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
									  }
								  }
							    }
						    }
			   }
		   }
		
			String[] date_time_arr = date_time.split("#");
			if (date_time_arr.length > 0) {
				if (needesc!=null && !needesc.equals("") && needesc.equalsIgnoreCase("Y")) {
					escalation_date = date_time_arr[0];
					escalation_time = date_time_arr[1];	
					resolution_date = date_time_arr[0];
					resolution_time = date_time_arr[1];
				}
				else {
					resolution_date = date_time_arr[0];
					resolution_time = date_time_arr[1];
				}
			}
			
			String[] level_date_time_arr = level_date_time.split("#");
			if (level_date_time_arr.length > 0) {
				level_resolution_date = level_date_time_arr[0];
				level_resolution_time = level_date_time_arr[1];
			}
			//System.out.println("Non Working Time   "+non_working_timing);
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void getAssetComplaintFields(String dataForValue) {
		ConfigurationUtilBean obj;
		List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_complaint_configuration",accountID, connectionSpace, "", 0, "table_name","asset_complaint_configuration");
		assetFieldList = new ArrayList();
		if (assetColumnList != null && assetColumnList.size() > 0) {
			for (GridDataPropertyView gdv : assetColumnList) {
				obj = new ConfigurationUtilBean();
				if (dataForValue!=null && !dataForValue.equals("") && dataForValue.equalsIgnoreCase("call"))
				{
					if (!gdv.getColomnName().equals("deptHierarchy") && !gdv.getColomnName().equals("by_dept")
							&& !gdv.getColomnName().equals("ticket_no") && !gdv.getColomnName().equals("allot_to")
							&& !gdv.getColomnName().equals("escalation_date") && !gdv.getColomnName().equals("escalation_time")
							&& !gdv.getColomnName().equals("open_date") && !gdv.getColomnName().equals("open_time")
							&& !gdv.getColomnName().equals("level") && !gdv.getColomnName().equals("status")
							&& !gdv.getColomnName().equals("via_from") && !gdv.getColomnName().equals("feed_registerby")
							&& !gdv.getColomnName().equals("action_by") && !gdv.getColomnName().equals("resolve_date")
							&& !gdv.getColomnName().equals("resolve_time") && !gdv.getColomnName().equals("resolve_duration")
							&& !gdv.getColomnName().equals("resolve_remark") && !gdv.getColomnName().equals("resolve_by")
							&& !gdv.getColomnName().equals("spare_used") && !gdv.getColomnName().equals("hp_date")
							&& !gdv.getColomnName().equals("hp_time") && !gdv.getColomnName().equals("hp_reason")
							&& !gdv.getColomnName().equals("sn_reason") && !gdv.getColomnName().equals("sn_on_date")
							&& !gdv.getColomnName().equals("sn_on_time") && !gdv.getColomnName().equals("sn_upto_date")
							&& !gdv.getColomnName().equals("sn_upto_time") && !gdv.getColomnName().equals("sn_duration")
							&& !gdv.getColomnName().equals("ig_date") && !gdv.getColomnName().equals("ig_time")
							&& !gdv.getColomnName().equals("ig_reason") && !gdv.getColomnName().equals("transfer_date")
							&& !gdv.getColomnName().equals("transfer_time") && !gdv.getColomnName().equals("transfer_reason")
							&& !gdv.getColomnName().equals("sub_location") && !gdv.getColomnName().equals("vendorid")
							&& !gdv.getColomnName().equals("moduleName") && !gdv.getColomnName().equals("location")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType(gdv.getColType());
						if (gdv.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						assetFieldList.add(obj);
					}
				  }
				 else if (dataForValue!=null && !dataForValue.equals("") && dataForValue.equalsIgnoreCase("online"))
					 {
						if (!gdv.getColomnName().equals("deptHierarchy") && !gdv.getColomnName().equals("by_dept")
								&& !gdv.getColomnName().equals("uniqueid") && !gdv.getColomnName().equals("feed_by")
								&& !gdv.getColomnName().equals("feed_by_mobno") && !gdv.getColomnName().equals("feed_by_emailid")
								&& !gdv.getColomnName().equals("ticket_no") && !gdv.getColomnName().equals("allot_to")
								&& !gdv.getColomnName().equals("escalation_date") && !gdv.getColomnName().equals("escalation_time")
								&& !gdv.getColomnName().equals("open_date") && !gdv.getColomnName().equals("open_time")
								&& !gdv.getColomnName().equals("level") && !gdv.getColomnName().equals("status")
								&& !gdv.getColomnName().equals("via_from") && !gdv.getColomnName().equals("feed_registerby")
								&& !gdv.getColomnName().equals("action_by") && !gdv.getColomnName().equals("resolve_date")
								&& !gdv.getColomnName().equals("resolve_time") && !gdv.getColomnName().equals("resolve_duration")
								&& !gdv.getColomnName().equals("resolve_remark") && !gdv.getColomnName().equals("resolve_by")
								&& !gdv.getColomnName().equals("spare_used") && !gdv.getColomnName().equals("hp_date")
								&& !gdv.getColomnName().equals("hp_time") && !gdv.getColomnName().equals("hp_reason")
								&& !gdv.getColomnName().equals("sn_reason") && !gdv.getColomnName().equals("sn_on_date")
								&& !gdv.getColomnName().equals("sn_on_time") && !gdv.getColomnName().equals("sn_upto_date")
								&& !gdv.getColomnName().equals("sn_upto_time") && !gdv.getColomnName().equals("sn_duration")
								&& !gdv.getColomnName().equals("ig_date") && !gdv.getColomnName().equals("ig_time")
								&& !gdv.getColomnName().equals("ig_reason") && !gdv.getColomnName().equals("transfer_date")
								&& !gdv.getColomnName().equals("transfer_time") && !gdv.getColomnName().equals("transfer_reason")
								&& !gdv.getColomnName().equals("sub_location") && !gdv.getColomnName().equals("vendorid")
								&& !gdv.getColomnName().equals("moduleName")  && !gdv.getColomnName().equals("location")) {
							obj.setKey(gdv.getColomnName());
							obj.setValue(gdv.getHeadingName());
							obj.setValidationType(gdv.getValidationType());
							obj.setColType(gdv.getColType());
							if (gdv.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							assetFieldList.add(obj);
						}
					 }
			     }
		     }
	    }
	
	@SuppressWarnings("unchecked")
	public String getAssetDetailINJSONArray() {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append("select distinct asset.serialno,asset.assetname from createasset_type_master as asset_type");
				query.append(" inner join asset_detail as asset on asset_type.id=asset.assettype");
				query.append(" inner join asset_allotment_detail as asset_allot on asset.id=asset_allot.assetid");
				query.append(" inner join compliance_contacts as contact on asset_allot.employeeid=contact.id");
				query.append(" inner join employee_basic as employee on contact.emp_id=employee.id");
			    query.append(" where employee.empId='"+uniqueid+"'");
			    query.append(" ORDER BY asset_type.assetSubCat ASC");
			   // System.out.println("Query is "+query.toString());
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
			    if(list.size()>0)
				 {
			    	commonJSONArray=new JSONArray();
					for (Object c : list) 
					 {
						Object[] objVar = (Object[]) c;
						JSONObject listObject=new JSONObject();
						listObject.put("id",objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					 }
				 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAssetTypeDetail() {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append("select distinct feedType.id,feedType.fbType from feedback_type as  feedType");
				query.append(" inner join feedback_category as feed_catg on feedType.id = feed_catg.fbType");
				query.append(" inner join asset_detail as asset on feed_catg.categoryName = asset.assetname");
			    query.append(" where asset.id='"+uniqueid+"'");
			    query.append(" ORDER BY feedType.fbType ASC");
			    System.out.println("Query is "+query.toString());
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
			    if(list.size()>0)
				 {
			    	commonJSONArray=new JSONArray();
					for (Object c : list) 
					 {
						Object[] objVar = (Object[]) c;
						JSONObject listObject=new JSONObject();
						listObject.put("id",objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					 }
				 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getComplaintCategory() {
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try {
				query.append(" select distinct feedCatg.id from feedback_category as feedCatg");
				query.append(" inner join asset_detail as asset on feedCatg.categoryName = asset.assetname ");
			    query.append(" where asset.id='"+uniqueid+"'");
			    System.out.println("Query is "+query.toString());
			    list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
			    //System.out.println("List Size   "+list.size());
			    if (list!=null && list.size()==1) {
			    	//System.out.println("Inside If Block");
			    	String catgId=list.get(0).toString();
			    	query.setLength(0);
			    	list.clear();
			    	if (catgId!=null && !catgId.equals("")) {
			    		//System.out.println("Category Id is  "+catgId);
			    		query.append("select id,subCategoryName from feedback_subcategory where categoryName="+catgId+" order by subCategoryName");
			    		//System.out.println("Query for Sub category  "+query.toString());
			    		list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
					}
				}
			    if(list.size()>0)
				 {
			    	commonJSONArray=new JSONArray();
					for (Object c : list) 
					 {
						Object[] objVar = (Object[]) c;
						JSONObject listObject=new JSONObject();
						listObject.put("id",objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					 }
				 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	
	@SuppressWarnings("unchecked")
	public String getDataById() {
		List list = new ArrayList();
		try {
			//System.out.println("inisde method");
			    list = new AssetComplaintHelper().getAssetDetailById(uniqueid, dataFor, connectionSpace);
			   // System.out.println("Data size= "+list.size());
			    if(list!=null && list.size()>0)
				 {
			    	assetObj = new AssetPojo();
			    	for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0]!=null && !object[0].toString().equals(""))
						{
							assetObj.setAssetId(object[0].toString());
						}
						else {
							assetObj.setAssetId("NA");
						}
						if (object[1]!=null && !object[1].toString().equals(""))
						{
							assetObj.setAssetName(object[1].toString());
						}
						else {
							assetObj.setAssetName("NA");
						}
						
						if (object[2]!=null && !object[2].toString().equals(""))
						{
							assetObj.setSerialNo(object[2].toString());
						}
						else {
							assetObj.setSerialNo("NA");
						}
						if (object[3]!=null && !object[3].toString().equals(""))
						{
							assetObj.setEmpName(object[3].toString());
						}
						else {
							assetObj.setEmpName("NA");
						}
						if (object[4]!=null && !object[4].toString().equals(""))
						{
							assetObj.setMobOne(object[4].toString());
						}
						else {
							assetObj.setMobOne("NA");
						}
						if (object[5]!=null && !object[5].toString().equals(""))
						{
							assetObj.setEmailId(object[5].toString());
						}
						else {
							assetObj.setEmailId("NA");
						}
						if (object[6]!=null && !object[6].toString().equals(""))
						{
							assetObj.setEmpId(object[6].toString());
						}
						else {
							assetObj.setEmpId("NA");
						}
						if (object[7]!=null && !object[7].toString().equals(""))
						{
							assetObj.setDeptId(object[7].toString());
						}
						else {
							assetObj.setDeptId("NA");
						}
					}
				 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// Method for getting Employee data for ticket allocation
	@SuppressWarnings("unchecked")
	public String getEmp4Escalation() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				AssetComplaintHelper ACH = new AssetComplaintHelper();
				empData4Escalation = new ArrayList<Object>();
                String tolevel="";
				// Code for getting the Escalation Date and Escalation Time
				List subCategoryList = HUH.getAllData("feedback_subcategory", "id",subCatg, "subCategoryName", "ASC",connectionSpace);
				if (subCategoryList != null && subCategoryList.size() > 0) {
					for (Iterator iterator = subCategoryList.iterator(); iterator
							.hasNext();) {
						Object[] objectCol = (Object[]) iterator.next();
						
						if (objectCol[8] == null) {
							tolevel = "1";
						}
						else {
						tolevel = objectCol[8].toString().substring(5);
						}
					}
				}
				List fieldNames = new ArrayList();
				fieldNames.add("id");
				fieldNames.add("empName");
				fieldNames.add("mobOne");
				fieldNames.add("emailIdOne");

				List emp4Escalation = new ArrayList();
				if (deptLevel.equals("2"))
				 {
					String newToLevel ="";
						emp4Escalation = ACH.getEmp4TicketAllocation(todept,dataFor,tolevel, connectionSpace);
						if (emp4Escalation==null || emp4Escalation.size()==0) {
								newToLevel = ""+(Integer.parseInt(tolevel)+1);
								emp4Escalation = ACH.getEmp4TicketAllocation(todept,dataFor,newToLevel, connectionSpace);
								if (emp4Escalation==null || emp4Escalation.size()==0) {
									newToLevel = ""+(Integer.parseInt(newToLevel)+1);
									emp4Escalation = ACH.getEmp4TicketAllocation(todept,dataFor,newToLevel, connectionSpace);
									if (emp4Escalation==null || emp4Escalation.size()==0) {
										newToLevel = ""+(Integer.parseInt(newToLevel)+1);
										emp4Escalation = ACH.getEmp4TicketAllocation(todept,dataFor,newToLevel, connectionSpace);
									}
								}
					}
				
					if (emp4Escalation != null && emp4Escalation.size() > 0)
					 {
						setRecords(emp4Escalation.size());
						int to = (getRows() * getPage());
						@SuppressWarnings("unused")
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
		
						
							List<Object> Listhb = new ArrayList<Object>();
							for (Iterator it = emp4Escalation.iterator(); it.hasNext();) {
								Object[] obdata = (Object[]) it.next();
								Map<String, Object> one = new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if (obdata[k] != null) {
										if (k == 0) {
											one.put(fieldNames.get(k).toString(),
													(Integer) obdata[k]);
										} else {
											one.put(fieldNames.get(k).toString(),
													obdata[k].toString());
										}
									}
								}
								Listhb.add(one);
							}
							setEmpData4Escalation(Listhb);
							setTotal((int) Math.ceil((double) getRecords()
									/ (double) getRows()));
							returnResult = SUCCESS;
						}
				 }
			  }
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}
	

	
	public void printTicket(FeedbackPojo data) {
		String orgDetail = (String) session.get("orgDetail");
		//System.out.println("Org Detail    "+orgDetail);
		String[] orgData = null;
		String orgName = "", address = "", city = "", pincode = "";
		if (orgDetail != null && !orgDetail.equals("")) {
			orgData = orgDetail.split("#");
			orgName = orgData[0];
			address = DateUtil.makeTitle(orgData[1]);
			city = DateUtil.makeTitle(orgData[2]);
			pincode = orgData[3];
		}
		setOrgName(orgName);
		setAddress(address);
		setCity(city);
		setPincode(pincode);
		
		if (data != null) {
			setTicketno(data.getTicket_no());
			//System.out.println("Ticket no   "+getTicketno());
			setFeedbackBy(data.getFeed_registerby());
			setMobileno(data.getFeedback_by_mobno());
			setTodept(data.getFeedback_to_dept());
			setBydept(data.getFeedback_by_dept());
			setAllotto_name(data.getFeedback_allot_to());
			setAllotto_mobno(data.getMobOne());
			setComplainantResTime(data.getEscalation_time());
			
			setCatg(data.getFeedback_catg());
			setSubCatg(data.getFeedback_subcatg());
			setFeed_brief(data.getFeed_brief());
			//setLocation(data.getLocation());
			setOpen_date(DateUtil.convertDateToIndianFormat(data.getOpen_date()));
			setOpen_time(data.getOpen_time());
			setAsset(data.getAsset());
			setSerialno(data.getSerialno());
			
			if (data.getOpen_date() != null && !data.getOpen_date().equals("")
					&& data.getOpen_time() != null && !data.getOpen_time().equals("")
					&& data.getFeedaddressing_time() != null && !data.getFeedaddressing_time().equals("")) {
				String addr_date_time = DateUtil.newdate_AfterTime(data
						.getOpen_date(), data.getOpen_time(), data
						.getFeedaddressing_time());
				String[] add_date_time = addr_date_time.split("#");
				for (int i = 0; i < add_date_time.length; i++) {
					setAddrDate(DateUtil
							.convertDateToIndianFormat(add_date_time[0]));
					setAddrTime(add_date_time[1]);
				}
			} else {
				setAddrDate("NA");
				setAddrTime("NA");
			}
		}
	}
	
	
	
	public String geTicketDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				String orgName = "", address = "", city = "", pincode = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
					address = orgData[1];
					city = orgData[2];
					pincode = orgData[3];
				}
				setTicketno(ticketno);
				setFeedbackBy(feedbackBy);
				setMobileno(mobileno);
				setOpen_date(open_date);
				setOpen_time(open_time);
				setCatg(catg);
				setSubCatg(subCatg);
				setFeed_brief(feed_brief);
				setAllotto_name(allotto_name);
				setTodept(todept);
				setBydept(bydept);
				setSerialno(serialno);

				setOrgName(orgName);
				setAddress(address);
				setCity(city);
				setPincode(pincode);

				if (feedStatus.equalsIgnoreCase("Resolved"))
				{
					setResolveon(resolveon);
					setResolveat(resolveat);
					setActiontaken(actiontaken);
					setSpareused(spareused);
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getTicketDetails Method");
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		// System.out.println(returnResult+"sdvc nsd vdn");
		return returnResult;
	}
	
	
	
	
	public AssetPojo getAssetObj()
	{
		return assetObj;
	}
	public void setAssetObj(AssetPojo assetObj)
	{
		this.assetObj = assetObj;
	}


	public String getDataFor()
	{
		return dataFor;
	}
	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	@SuppressWarnings("unchecked")
	public List getAssetFieldList()
	{
		return assetFieldList;
	}
	@SuppressWarnings("unchecked")
	public void setAssetFieldList(List assetFieldList)
	{
		this.assetFieldList = assetFieldList;
	}


	public Map<Integer, String> getServiceDeptList()
	{
		return serviceDeptList;
	}


	public void setServiceDeptList(Map<Integer, String> serviceDeptList)
	{
		this.serviceDeptList = serviceDeptList;
	}
	
	public String getModuleName()
	{
		return moduleName;
	}


	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	
	public Map<String, String> getAssetDetailList()
	{
		return assetDetailList;
	}


	public void setAssetDetailList(Map<String, String> assetDetailList)
	{
		this.assetDetailList = assetDetailList;
	}


	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}


	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}


	public String getUniqueid()
	{
		return uniqueid;
	}


	public void setUniqueid(String uniqueid)
	{
		this.uniqueid = uniqueid;
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


	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getTodept()
	{
		return todept;
	}


	public void setTodept(String todept)
	{
		this.todept = todept;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public String getSubCatg()
	{
		return subCatg;
	}


	public void setSubCatg(String subCatg)
	{
		this.subCatg = subCatg;
	}


	public List getEmpData4Escalation()
	{
		return empData4Escalation;
	}


	public void setEmpData4Escalation(List empData4Escalation)
	{
		this.empData4Escalation = empData4Escalation;
	}


	public String getSelectedId()
	{
		return selectedId;
	}


	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}


	public String getFeedbackBy()
	{
		return feedbackBy;
	}


	public void setFeedbackBy(String feedbackBy)
	{
		this.feedbackBy = feedbackBy;
	}


	public String getMobileno()
	{
		return mobileno;
	}


	public void setMobileno(String mobileno)
	{
		this.mobileno = mobileno;
	}


	public String getEmailId()
	{
		return emailId;
	}


	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}


	public String getViaFrom()
	{
		return viaFrom;
	}


	public void setViaFrom(String viaFrom)
	{
		this.viaFrom = viaFrom;
	}


	public String getFeed_brief()
	{
		return feed_brief;
	}


	public void setFeed_brief(String feed_brief)
	{
		this.feed_brief = feed_brief;
	}


	public String getAsset_id()
	{
		return asset_id;
	}


	public void setAsset_id(String asset_id)
	{
		this.asset_id = asset_id;
	}


	public EmpBasicPojoBean getEmpObj()
	{
		return empObj;
	}


	public void setEmpObj(EmpBasicPojoBean empObj)
	{
		this.empObj = empObj;
	}


	public String getBydept()
	{
		return bydept;
	}


	public void setBydept(String bydept)
	{
		this.bydept = bydept;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getTicketno()
	{
		return ticketno;
	}

	public void setTicketno(String ticketno)
	{
		this.ticketno = ticketno;
	}

	public String getComplainantResTime()
	{
		return complainantResTime;
	}

	public void setComplainantResTime(String complainantResTime)
	{
		this.complainantResTime = complainantResTime;
	}

	public String getAllotto_name()
	{
		return allotto_name;
	}

	public void setAllotto_name(String allotto_name)
	{
		this.allotto_name = allotto_name;
	}

	public String getAllotto_mobno()
	{
		return allotto_mobno;
	}

	public void setAllotto_mobno(String allotto_mobno)
	{
		this.allotto_mobno = allotto_mobno;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPincode()
	{
		return pincode;
	}

	public void setPincode(String pincode)
	{
		this.pincode = pincode;
	}

	public String getOpen_date()
	{
		return open_date;
	}

	public void setOpen_date(String open_date)
	{
		this.open_date = open_date;
	}

	public String getOpen_time()
	{
		return open_time;
	}

	public void setOpen_time(String open_time)
	{
		this.open_time = open_time;
	}

	public String getAddrDate()
	{
		return addrDate;
	}

	public void setAddrDate(String addrDate)
	{
		this.addrDate = addrDate;
	}

	public String getAddrTime()
	{
		return addrTime;
	}

	public void setAddrTime(String addrTime)
	{
		this.addrTime = addrTime;
	}

	public String getAsset()
	{
		return asset;
	}

	public void setAsset(String asset)
	{
		this.asset = asset;
	}

	public String getSerialno()
	{
		return serialno;
	}

	public void setSerialno(String serialno)
	{
		this.serialno = serialno;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public List<GridDataPropertyView> getFeedbackColumnNames()
	{
		return feedbackColumnNames;
	}

	public void setFeedbackColumnNames(List<GridDataPropertyView> feedbackColumnNames)
	{
		this.feedbackColumnNames = feedbackColumnNames;
	}

	public List<AssetComplaintPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<AssetComplaintPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}

	public String getResolveon()
	{
		return resolveon;
	}

	public void setResolveon(String resolveon)
	{
		this.resolveon = resolveon;
	}

	public String getResolveat()
	{
		return resolveat;
	}

	public void setResolveat(String resolveat)
	{
		this.resolveat = resolveat;
	}

	public String getSpareused()
	{
		return spareused;
	}

	public void setSpareused(String spareused)
	{
		this.spareused = spareused;
	}

	public String getActiontaken()
	{
		return actiontaken;
	}

	public void setActiontaken(String actiontaken)
	{
		this.actiontaken = actiontaken;
	}


	public String getFeedType() {
		return feedType;
	}


	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}


	public String getRecvSMS() {
		return recvSMS;
	}


	public void setRecvSMS(String recvSMS) {
		this.recvSMS = recvSMS;
	}


	public String getRecvEmail() {
		return recvEmail;
	}


	public void setRecvEmail(String recvEmail) {
		this.recvEmail = recvEmail;
	}
	
	
}
