package com.Over2Cloud.ctrl.offeringComplaint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class LodgeComplaintAction extends GridPropertyBean
{

	private static final long serialVersionUID = 1L;
	private List<Object> empData4Escalation;
	private String subCategory;
	private String offering;
	private String forDept;
	private String feedbackTo;
	private String feedbackCC;
	private String clientfor;
	private String clientName;
	private String feed_brief;
	private String recvSMS;
	private String recvEmail;
	private String viaFrom;
	private String rowId;
	
	
	
	
	
	
	
	// Method for getting Employee data for ticket allocation
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getEmployee4Escalation() 
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			try 
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String deptLevel = (String) session.get("userDeptLevel");
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				ComplaintLodgeHelper CLH=new ComplaintLodgeHelper();
				empData4Escalation = new ArrayList<Object>();
                String tolevel="";
				List subCategoryList = HUH.getAllData("feedback_subcategory", "id",subCategory, "subCategoryName", "ASC",connectionSpace);
				if (subCategoryList != null && subCategoryList.size() > 0)
				{
					for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();) 
					{
						Object[] objectCol = (Object[]) iterator.next();
						
						if (objectCol[8] == null) 
						{
							tolevel = "1";
						}
						else 
						{
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
					emp4Escalation = CLH.getEmp4Escalation("4","DREAM_HDM",tolevel, connectionSpace);
					if (emp4Escalation==null || emp4Escalation.size()==0) 
					{
						newToLevel = ""+(Integer.parseInt(tolevel)+1);
						emp4Escalation = CLH.getEmp4Escalation("4","DREAM_HDM",newToLevel, connectionSpace);
						if (emp4Escalation==null || emp4Escalation.size()==0) 
						{
							newToLevel = ""+(Integer.parseInt(newToLevel)+1);
							emp4Escalation = CLH.getEmp4Escalation("4","DREAM_HDM",newToLevel, connectionSpace);
							if (emp4Escalation==null || emp4Escalation.size()==0) 
							{
								newToLevel = ""+(Integer.parseInt(newToLevel)+1);
								emp4Escalation = CLH.getEmp4Escalation("4","DREAM_HDM",newToLevel, connectionSpace);
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
						for (Iterator it = emp4Escalation.iterator(); it.hasNext();) 
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if (obdata[k] != null) 
								{
									if (k == 0) 
									{
										one.put(fieldNames.get(k).toString(),(Integer) obdata[k]);
									} 
									else 
									{
										one.put(fieldNames.get(k).toString(),obdata[k].toString());
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
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else 
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	@SuppressWarnings("rawtypes")
	public synchronized String registerFeedbackViaCall() 
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		{
			Lock lock = new ReentrantLock();
			lock.lock();
			try 
			{
				System.out.println(" forDept    "+forDept);
				System.out.println(" feedbackTo    "+feedbackTo);
				System.out.println(" feedbackCC    "+feedbackCC);
				System.out.println(" clientfor    "+clientfor);
				System.out.println(" clientName    "+clientName);
				System.out.println(" offering    "+offering);
				System.out.println(" subCategory    "+subCategory);
				System.out.println(" feed_brief    "+feed_brief);
				System.out.println(" recvSMS    "+recvSMS);
				System.out.println(" recvEmail    "+recvEmail);
				System.out.println("selectedId    ::;   "+rowId);
				
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				ComplaintLodgeHelper CLH=new ComplaintLodgeHelper();
				MsgMailCommunication MMC = new MsgMailCommunication();
				CommonOperInterface cot = new CommonConFactory().createInterface();
				List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_complaint_lodge_configuration", "",connectionSpace, "", 0, "table_name","complaint_lodge_configuration");
	
				if (colName != null && colName.size() > 0) 
				{
					@SuppressWarnings("unused")
					boolean status = false;
					List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
					for (GridDataPropertyView tableColumes : colName) 
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(tableColumes.getColomnName());
						ob1.setDatatype("varchar("+ tableColumes.getWidth() + ")");
						ob1.setConstraint("default NULL");
						TableColumnName.add(ob1);
					}
					cot.createTable22("complaint_status", TableColumnName,connectionSpace);
					FeedbackPojo fbp = null;
					String  escalation_date = "NA", escalation_time = "NA", adrr_time = "",res_time = "", allottoId = "",  ticketno = "", mailText = "", tolevel = "", needesc = "";
					String resolution_date = "NA", resolution_time = "NA",level_resolution_date = "NA", level_resolution_time = "NA";
					List subCategoryList = HUH.getAllData("feedback_subcategory", "id",subCategory, "subCategoryName", "ASC",connectionSpace);
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
					
						String date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, res_time);
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
						
						String level_date_time = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, "00:00");
						String[] level_date_time_arr = level_date_time.split("#");
						if (level_date_time_arr.length > 0) {
							level_resolution_date = level_date_time_arr[0];
							level_resolution_time = level_date_time_arr[1];
						}
	
					if (viaFrom != null && viaFrom.equals("call")) 
					{
						allottoId=rowId.replace(",", "#");
					}
					String ticketLevel = "";
					if (allottoId!=null && !allottoId.equals("")) 
					{
						 ticketLevel = CLH.getTicketLevel(allottoId, connectionSpace);
					}
					
					ticketno = HelpdeskUniversalHelper.getTicketNo(forDept,"DREAM_HDM", connectionSpace);
					
					if (ticketno!=null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId!=null && !allottoId.equals("")) 
					{
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					ob.setColName("ticket_no");
					ob.setDataName(ticketno);
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("clientfor");
					ob.setDataName(clientfor);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("clientName");
					ob.setDataName(clientName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("offering");
					ob.setDataName(offering);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("feedbackTo");
					ob.setDataName(feedbackTo);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("feedbackCC");
					ob.setDataName(feedbackCC.replace(", ","#"));
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("subCategory");
					ob.setDataName(subCategory);
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("feed_brief");
					ob.setDataName(feed_brief);
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("forDept");
					ob.setDataName(forDept);
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("allot_to");
					ob.setDataName(allottoId);
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
					ob.setColName("open_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("open_time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
	
					ob = new InsertDataTable();
					ob.setColName("location");
					ob.setDataName("Noida");
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
					ob.setDataName("DREAM_HDM");
					insertData.add(ob);
	
					boolean status1 = cot.insertIntoTable("complaint_status",insertData, connectionSpace);
					insertData.clear();
	
					if (status1) 
					{
						List data = CLH.getFeedbackDetail("","Pending", connectionSpace);
						fbp = CLH.setFeedbackDataValues(data, "Pending",connectionSpace);
						if (fbp != null) 
						{
						/*	//mapEscalation(""+fbp.getId(),fbp.getLevel(),fbp.getEscalation_date(),fbp.getEscalation_time(), connectionSpace);
							// Block for sending sms for Level1 Engineer
							if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10  &&  !fbp.getMobOne().startsWith("NA") && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7"))) {
								String levelMsg = "Open Feedback Alert: Ticket No: "+ fbp.getTicket_no()+ ", To Be Closed By: "+ DateUtil.convertDateToIndianFormat(level_resolution_date)+ ","+ level_resolution_time.substring(0, 5)+ ", Reg. By: "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ", Location: "+ fbp.getLocation()+ ", Brief: "+ fbp.getFeed_brief()+ ".";
								MMC.addMessage(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getMobOne(), levelMsg, ticketno,"Pending", "0","HDM");
							}
							// Block for sending sms for Complainant
							if (getRecvSMS().equals("true") && fbp.getFeedback_by_mobno() != null && fbp.getFeedback_by_mobno() != "" && fbp.getFeedback_by_mobno().trim().length() == 10 && (fbp.getFeedback_by_mobno().startsWith("9") || fbp.getFeedback_by_mobno().startsWith("8") || fbp.getFeedback_by_mobno().startsWith("7"))) {
								String complainMsg = "Dear "+ DateUtil.makeTitle(fbp.getFeed_registerby())+ ","+ " Ticket No: "+ fbp.getTicket_no()+ ", Resolution Time: "+ DateUtil.convertDateToIndianFormat(resolution_date)+ ","+ resolution_time.substring(0, 5)+ ", Location: " + fbp.getLocation()+ ", Brief: " + fbp.getFeed_brief()+ " is open.";
								MMC.addMessage(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_mobno(), complainMsg,ticketno,"Pending", "0", "HDM");
							}*/
							// Block for creating mail for Level1 Engineer
							if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA")) 
							{
								mailText = CLH.getConfigMessage(fbp,"Open Feedback Alert","Pending",  true,fbp.getClientFor(),fbp.getClientName(),fbp.getOfferingName());
								MMC.addMail(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),fbp.getEmailIdOne(),"Open Feedback Alert:"+ticketno, mailText,ticketno, "Pending", "0","","DREAM_HDM");
								MMC.addMail(fbp.getFeedback_allot_to(),fbp.getFeedback_to_dept(),"support@dreamsol.biz","Open Feedback Alert : "+ticketno, mailText,ticketno, "Pending", "0","","DREAM_HDM");
							}
							// Block for creating mail for complainant
							if (getRecvEmail().equals("true") && fbp.getFeedback_by_emailid() != null && !fbp.getFeedback_by_emailid().equals("") && !fbp.getFeedback_by_emailid().equals("NA")) {
								mailText = CLH.getConfigMessage(fbp,"Open Feedback Alert","Pending", false,fbp.getClientFor(),fbp.getClientName(),fbp.getOfferingName());
								MMC.addMail(fbp.getFeed_registerby(),fbp.getFeedback_by_dept(),fbp.getFeedback_by_emailid(),"Feedback Acknowledgement : "+ticketno, mailText,ticketno,"Pending", "0","","DREAM_HDM");
							}
						}
						addActionMessage("Feedback Lodge Successfully!!!");
					}
					returnResult = SUCCESS;
					}
					else
					{
						if (ticketno==null || ticketno.equalsIgnoreCase("") || ticketno.equalsIgnoreCase("NA")) 
						{
							addActionMessage("Please Check Your Ticket Series!!!");
						}
						else if (allottoId==null || allottoId.equalsIgnoreCase("") || allottoId.equalsIgnoreCase("-1")) 
						{
							addActionMessage("May be Someone is not mapped for taking the complaint. Please contact to concern service department !!!");
						}
						returnResult = "TicketError";
					}
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
		return returnResult;
	}
	boolean mapEscalation(String id,String level,String date,String time,SessionFactory connection)
	 {
		boolean flag=false;
		
		try {
			  CommonOperInterface cot = new CommonConFactory().createInterface();
			  List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
		      
			  TableColumes tc = new TableColumes();
			  tc.setColumnname("feed_status_id");
			  tc.setDatatype("varchar(10)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("allot_to_level");
			  tc.setDatatype("varchar(10)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("nextEscDateTime");
			  tc.setDatatype("varchar(20)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("nextEscLevel_2");
			  tc.setDatatype("varchar(10)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("EscLevel_2_DateTime");
			  tc.setDatatype("varchar(20)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("nextEscLevel_3");
			  tc.setDatatype("varchar(10)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("EscLevel_3_DateTime");
			  tc.setDatatype("varchar(20)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("nextEscLevel_4");
			  tc.setDatatype("varchar(10)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("EscLevel_4_DateTime");
			  tc.setDatatype("varchar(20)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("nextEscLevel_5");
			  tc.setDatatype("varchar(10)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  tc = new TableColumes();
			  tc.setColumnname("EscLevel_5_DateTime");
			  tc.setDatatype("varchar(20)");
			  tc.setConstraint("default NULL");
			  TableColumnName.add(tc);
			  
			  
			  //Method calling for creating table on the basis of above
			  cot.createTable22("escalation_mapping", TableColumnName,connection);
			  
			  
			    InsertDataTable ob = new InsertDataTable();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				ob.setColName("feed_status_id");
				ob.setDataName(id);
				insertData.add(ob);
	
				ob = new InsertDataTable();
				ob.setColName("allot_to_level");
				ob.setDataName(level);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("nextEscDateTime");
				ob.setDataName(DateUtil.convertDateToIndianFormat(date)+"("+time+")");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("nextEscLevel_2");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("EscLevel_2_DateTime");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("nextEscLevel_3");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("EscLevel_3_DateTime");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("nextEscLevel_4");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("EscLevel_4_DateTime");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("nextEscLevel_5");
				ob.setDataName("NA");
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("EscLevel_5_DateTime");
				ob.setDataName("NA");
				insertData.add(ob);
				
				// Method calling for inserting the values in the
				// feedback_status table
				@SuppressWarnings("unused")
				boolean status1 = cot.insertIntoTable("escalation_mapping",insertData, connection);
				insertData.clear();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	public List<Object> getEmpData4Escalation() {
		return empData4Escalation;
	}
	public void setEmpData4Escalation(List<Object> empData4Escalation) {
		this.empData4Escalation = empData4Escalation;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getOffering() {
		return offering;
	}
	public void setOffering(String offering) {
		this.offering = offering;
	}

	public String getForDept() {
		return forDept;
	}

	public void setForDept(String forDept) {
		this.forDept = forDept;
	}

	public String getFeedbackTo() {
		return feedbackTo;
	}

	public void setFeedbackTo(String feedbackTo) {
		this.feedbackTo = feedbackTo;
	}

	public String getFeedbackCC() {
		return feedbackCC;
	}

	public void setFeedbackCC(String feedbackCC) {
		this.feedbackCC = feedbackCC;
	}

	public String getClientfor() {
		return clientfor;
	}

	public void setClientfor(String clientfor) {
		this.clientfor = clientfor;
	}

	public String getFeed_brief() {
		return feed_brief;
	}

	public void setFeed_brief(String feed_brief) {
		this.feed_brief = feed_brief;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getViaFrom() {
		return viaFrom;
	}

	public void setViaFrom(String viaFrom) {
		this.viaFrom = viaFrom;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	
}
