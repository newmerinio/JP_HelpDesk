package com.Over2Cloud.ctrl.asset;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.compliance.complContacts.ComplianceEditGridAction;

public class AssetHistory extends GridPropertyBean implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(Contact2OutletMapping.class);
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private HttpServletRequest request;
	private String dataId;
	private String idFor;
	private Map<String,String> assetDetailMap;
	private Map<String,String> assetSupportDetailMap;
	private Map<String,String> assetPreventiveDetailMap;
	private String assetName;
	private String assetCode;
	private String slNo;
	private String frequency="Not Configure";
	private String supportStatus="Not Configure";
	private String nextOrPrevious;
	List<AssetDashboardBean> supportList=null;
	List<AssetDashboardBean> preventiveList=null;
	private boolean forwardStatus;
	private boolean backwardStatus;
	List<AssetDashboardBean> breakDownList=null;
	private String totalBreakDown="0";
	private String currentStatus="Working";
	private String complaintId;
	List<AssetDashboardBean> complainDetailList;
	private String doc1;
	private String doc2;
	private String lastActionDoc;
	
	private String pmDoc1;
	private String pmDoc2;
	private String pmLastActionDoc;
	
	private String reminderId;
	private String removeSession4;
	private String assetReminderID;
	
	@SuppressWarnings("unchecked")
	public String getAssetHistoryById()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				assetDetailMap = new LinkedHashMap<String, String>();
				assetSupportDetailMap = new LinkedHashMap<String, String>();
				assetPreventiveDetailMap = new LinkedHashMap<String, String>();
				supportList = new ArrayList<AssetDashboardBean>();
				preventiveList = new ArrayList<AssetDashboardBean>();
				breakDownList = new ArrayList<AssetDashboardBean>();
				String assetId =null,supportFrom=null,astReminderId=null;
				List dataList =null,idList=new ArrayList();
				String frequency=null;
				if(session.containsKey("assetIdd"))
				{
					dataId=(String) session.get("assetIdd");
					idList= (List) session.get("assetIddList");
					if(nextOrPrevious!=null && nextOrPrevious.equals("ForwardData"))
						dataId=getNext(dataId, idList);
					
					if(nextOrPrevious!=null && nextOrPrevious.equals("BackwardData"))
						dataId=getPrevious(dataId, idList);
					
				}
				else
				{
					idList=getAssetId();
					if(idList!=null)
						session.put("assetIddList", idList);
					
				}
				
				if(idList!=null && idList.size()>0)
				{
					String tempId=null;
					tempId=getNext(dataId, idList);
					
					if(tempId!=null && !tempId.equals(""))
					{
						backwardStatus=true;
						tempId=null;
					}
					
					tempId=getPrevious(dataId, idList);
					
					if(tempId!=null && !tempId.equals(""))
					{
						forwardStatus=true;
						tempId=null;
					}
					
				}
				
				if(dataId!=null && idFor!=null)
				{
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					StringBuilder query = new StringBuilder();
					
					if(idFor.equalsIgnoreCase("AssetDetail"))
					{
						//Block Asset Details
						query.append("SELECT ast.assetname,ast.serialno,ast.mfgserialno,");
						query.append("ast.assetbrand,ast.assetmodel,ast.specification,ast.pono,");
						query.append("vendor.vendorname,ast.totalamount,ast.receivedOn,");
						query.append("ast.installon,ast.commssioningon,astCatg.assetSubCat,");
						query.append("ast.expectedlife,astDep.today_amount,outlet.associateName,outlet.address,ast.id,");
						query.append("ast.supportfrom ");
						query.append(" FROM asset_detail AS ast ");
						query.append(" LEFT JOIN createvendor_master AS vendor ON vendor.id=ast.vendorname ");
						query.append(" LEFT JOIN createasset_type_master AS astCatg ON astCatg.id=ast.assettype ");
						query.append(" LEFT JOIN asset_depreciation_detail AS astDep ON astDep.assetid=ast.id ");
						query.append(" LEFT JOIN asset_allotment_detail AS allot ON allot.assetid=ast.id ");
						query.append(" LEFT JOIN associate_basic_data AS outlet ON outlet.id=allot.outletid ");
						query.append("WHERE ast.id="+dataId+" AND astDep.id=(SELECT id FROM asset_depreciation_detail WHERE assetid="+dataId+" ORDER BY id DESC LIMIT 1)");
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query.setLength(0);
						if(dataList!=null && dataList.size()>0)
						{
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								assetName=CUA.getValueWithNullCheck(object[0]);
								assetCode=CUA.getValueWithNullCheck(object[1]);
								slNo=CUA.getValueWithNullCheck(object[3]);
								
								assetDetailMap.put("Brand", CUA.getValueWithNullCheck(object[3]));
								assetDetailMap.put("Model", CUA.getValueWithNullCheck(object[4]));
								assetDetailMap.put("Specification", CUA.getValueWithNullCheck(object[5]));
								assetDetailMap.put("P.O. No.", CUA.getValueWithNullCheck(object[6]));
								assetDetailMap.put("Vendor", CUA.getValueWithNullCheck(object[7]));
								assetDetailMap.put("Asset Category", CUA.getValueWithNullCheck(object[12]));
								assetDetailMap.put("Current Value", CUA.getValueWithNullCheck(object[14]));
								assetDetailMap.put("Asset Type", "Shared");
								assetDetailMap.put("Allotted To", CUA.getValueWithNullCheck(object[15]));
								assetDetailMap.put("Location", CUA.getValueWithNullCheck(object[16]));
								
								if(object[8]!=null)
									assetDetailMap.put("At", object[8].toString());
								else
									assetDetailMap.put("At", "00:00");
								
								if(object[9]!=null)
									assetDetailMap.put("Purchased On", DateUtil.convertDateToIndianFormat(object[9].toString()));
								else
									assetDetailMap.put("Purchased On","NA");
								
								if(object[10]!=null)
									assetDetailMap.put("Install On", DateUtil.convertDateToIndianFormat(object[10].toString()));
								else
									assetDetailMap.put("Install On","NA");
								
								if(object[11]!=null)
									assetDetailMap.put("Commissioned On", DateUtil.convertDateToIndianFormat(object[11].toString()));
								else
									assetDetailMap.put("Commissioned On","NA");
								
								if(object[13]!=null)
									assetDetailMap.put("Expected Life", DateUtil.convertDateToIndianFormat(object[13].toString()));
								else
									assetDetailMap.put("Expected Life","NA");
								
								assetId=object[17].toString();
								
								session.put("assetIdd", assetId);
								
								supportFrom=CUA.getValueWithNullCheck(object[18]);
							}
							dataList.clear();
						}
					}
					// Block for asset support
					if(assetId!=null)
					{
						query.append("SELECT reminder.id,reminder.dueDate,reminder.dueTime,reminder.actionStatus,suprtType.category,vendor.vendorname,vendor.contactno,reminder.detail,reminder.frequency,reminder.reminder_for,reminder.uploadDoc,reminder.uploadDoc1 ");
						query.append(" FROM asset_reminder_details AS reminder ");
						query.append(" LEFT JOIN createasset_support_catg_master AS suprtType ON reminder.supporttype=suprtType.id ");
						query.append(" LEFT JOIN createvendor_master AS vendor ON vendor.id=reminder.vendorid ");
						query.append(" WHERE reminder.moduleName='Support' AND reminder.assetid="+assetId);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query.setLength(0);
						if(dataList!=null && dataList.size()>0)
						{
							
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								assetSupportDetailMap.put("Support Type", CUA.getValueWithNullCheck(object[4]));
								assetSupportDetailMap.put("From Vendor", CUA.getValueWithNullCheck(object[5]));
								assetSupportDetailMap.put("Contact No.", CUA.getValueWithNullCheck(object[6]));
								assetSupportDetailMap.put("Support Detail", CUA.getValueWithNullCheck(object[7]));
								
								if(supportFrom.equalsIgnoreCase("installon"))
									assetSupportDetailMap.put("Support From", "Install On");
								else if(supportFrom.equalsIgnoreCase("commssioningon"))
									assetSupportDetailMap.put("Support From", "Commssioning On");
								else if(supportFrom.equalsIgnoreCase("receivedOn"))
									assetSupportDetailMap.put("Support From", "Received On");
								else
									assetSupportDetailMap.put("Support From", supportFrom);
								
								assetSupportDetailMap.put("Support To", DateUtil.convertDateToIndianFormat(object[1].toString()));
								assetSupportDetailMap.put("Frequency", getFrequencyAbrivation(object[8].toString()));
								String arr[]=getEmpName(object[9].toString(),cbt,CUA).split("#");
								assetSupportDetailMap.put("Mapped To",arr[0]);
								assetSupportDetailMap.put("Contact No. ",arr[1]);
								
								String dataArr[]=getRemindersById(object[0].toString(),cbt);
								
								assetSupportDetailMap.put("Reminder-3", dataArr[0]);
								assetSupportDetailMap.put("Reminder-2", dataArr[1]);
								assetSupportDetailMap.put("Reminder-1", dataArr[2]);
								if(object[10]!=null)
								{
									String str=object[10].toString().substring(object[10].toString().lastIndexOf("//")+2, object[10].toString().length());
									String documentName=str.substring(14,str.length());
									assetSupportDetailMap.put("Document 1", documentName);
									doc1=object[10].toString();
								}
								else
								{
									assetSupportDetailMap.put("Document 1", "Not Available");
								}
								
								if(object[11]!=null)
								{
									String str=object[11].toString().substring(object[11].toString().lastIndexOf("//")+2, object[11].toString().length());
									String documentName=str.substring(14,str.length());
									assetSupportDetailMap.put("Document 2", documentName);
									doc2=object[11].toString();
								}
								else
								{
									assetSupportDetailMap.put("Document 2", "Not Available");
								}
								
								String docPath = getLastActionDoc(object[0].toString());
								if(docPath!=null)
								{
									String str=docPath.substring(docPath.lastIndexOf("//")+2, docPath.length());
									String documentName=str.substring(14,str.length());
									assetSupportDetailMap.put("Last Action Doc", documentName);
									lastActionDoc=docPath;
								}
								else
								{
									assetSupportDetailMap.put("Last Action Doc", "Not Available");
								}
								
								if(DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(),object[1].toString()))
								{
									supportStatus="Under AMC";
								}
								else if(!object[3].toString().equals("Done") || !object[3].toString().equals("Recurring"))
								{
									supportStatus="Expire";
								}
								astReminderId=object[0].toString();
								frequency=object[8].toString();
							}
							dataList.clear();
							
							// Get Last 3 Asset Support Action
							query.append("SELECT dueDate,actionTakenDate,userName FROM asset_report WHERE ");
							query.append(" (actionTaken='Done' OR actionTaken='Recurring') AND assetReminderID="+astReminderId+" ORDER BY id DESC LIMIT 0,3");
							dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							query.setLength(0);
							if(dataList!=null)
							{
								AssetDashboardBean ADB = new AssetDashboardBean();
								for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
								{
									ADB = new AssetDashboardBean();
									Object[] object = (Object[]) iterator.next();
									ADB.setActionTakenDate(DateUtil.convertDateToIndianFormat(object[1].toString()));
									String supportTill=DateUtil.convertDateToIndianFormat(getNextDueDate(frequency,object[0].toString()));
									ADB.setDueDate(supportTill);
									ADB.setEmpName(getEmpNameByUserId(object[2].toString(),cbt));
									supportList.add(ADB);
								}
								dataList.clear();
							}
						}
						else
						{
							assetSupportDetailMap.put("Support Type", "Not Available");
							assetSupportDetailMap.put("From Vendor", "Not Available");
							assetSupportDetailMap.put("Contact No. ", "Not Available");
							assetSupportDetailMap.put("Support Detail", "Not Available");
							assetSupportDetailMap.put("Support From", "Not Available");
							assetSupportDetailMap.put("Support To", "Not Available");
							assetSupportDetailMap.put("Frequency", "Not Available");
							assetSupportDetailMap.put("Mapped To", "Not Available");
							assetSupportDetailMap.put("Contact No.", "Not Available");
							assetSupportDetailMap.put("Reminder-3", "Not Available");
							assetSupportDetailMap.put("Reminder-2", "Not Available");
							assetSupportDetailMap.put("Reminder-1", "Not Available");
							assetSupportDetailMap.put("Document 1", "Not Available");
							assetSupportDetailMap.put("Document 2", "Not Available");
							assetSupportDetailMap.put("Last Action Doc", "Not Available");
							
						}
					}
					// Block for asset preventive
					if(assetId!=null)
					{
						query.append("SELECT reminder.id,reminder.dueDate,reminder.dueTime,reminder.actionStatus,reminder.detail,reminder.frequency,reminder.reminder_for,reminder.action_type ");
						query.append(" FROM asset_reminder_details AS reminder ");
						query.append(" WHERE reminder.moduleName='Preventive' AND reminder.assetid="+assetId);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query.setLength(0);
						if(dataList!=null && dataList.size()>0)
						{
							astReminderId=null;
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								assetPreventiveDetailMap.put("Detail", CUA.getValueWithNullCheck(object[4]));
								assetPreventiveDetailMap.put("Due", DateUtil.convertDateToIndianFormat(object[1].toString()));
								assetPreventiveDetailMap.put("Next Due", DateUtil.convertDateToIndianFormat(getNextDueDate(object[5].toString(),object[1].toString())));
								String arr[]=getEmpName(object[6].toString(),cbt,CUA).split("#");
								assetPreventiveDetailMap.put("Mapped To",arr[0]);
								assetPreventiveDetailMap.put("Contact No.",arr[1]);
								assetPreventiveDetailMap.put("Ownership",DateUtil.makeTitle(CUA.getValueWithNullCheck(object[7])));
								frequency=getFrequencyAbrivation(object[5].toString());
								String dataArr[]=getRemindersById(object[0].toString(),cbt);
								assetPreventiveDetailMap.put("Reminder-3", dataArr[0]);
								assetPreventiveDetailMap.put("Reminder-2", dataArr[1]);
								assetPreventiveDetailMap.put("Reminder-1", dataArr[2]);
								System.out.println(object[10].toString()+" >>>> ");
								if(object[10]!=null)
								{
									String str=object[10].toString().substring(object[10].toString().lastIndexOf("//")+2, object[10].toString().length());
									String documentName=str.substring(14,str.length());
									assetPreventiveDetailMap.put("Document 1", documentName);
									pmDoc1=object[10].toString();
								}
								else
								{
									assetPreventiveDetailMap.put("Document 1", "Not Available");
								}
								
								if(object[11]!=null)
								{
									String str=object[11].toString().substring(object[11].toString().lastIndexOf("//")+2, object[11].toString().length());
									String documentName=str.substring(14,str.length());
									assetPreventiveDetailMap.put("Document 2", documentName);
									pmDoc2=object[11].toString();
								}
								else
								{
									assetPreventiveDetailMap.put("Document 2", "Not Available");
								}
								
								String docPath = getLastActionDoc(object[0].toString());
								if(docPath!=null)
								{
									String str=docPath.substring(docPath.lastIndexOf("//")+2, docPath.length());
									String documentName=str.substring(14,str.length());
									assetPreventiveDetailMap.put("Last Action Doc", documentName);
									pmLastActionDoc=docPath;
								}
								else
								{
									assetPreventiveDetailMap.put("Last Action Doc", "Not Available");
								}
								
								
								
								astReminderId = object[0].toString();
							}
							dataList.clear();
							
							// Get Last 3 Asset Preventive Action
							query.append("SELECT dueDate,actionTakenDate,userName,actionTaken,comments FROM asset_report WHERE ");
							query.append(" (actionTaken='Done' OR actionTaken='Recurring') AND assetReminderID="+astReminderId+" ORDER BY id DESC LIMIT 0,3");
							dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							query.setLength(0);
							if(dataList!=null)
							{
								AssetDashboardBean ADB = new AssetDashboardBean();
								for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
								{
									ADB = new AssetDashboardBean();
									Object[] object = (Object[]) iterator.next();
									ADB.setActionTakenDate(DateUtil.convertDateToIndianFormat(object[1].toString()));
									ADB.setDueDate(DateUtil.convertDateToIndianFormat(object[0].toString()));
									ADB.setEmpName(getEmpNameByUserId(object[2].toString(),cbt));
									ADB.setActionStatus(object[3].toString());
									ADB.setComments(object[4].toString());
									preventiveList.add(ADB);
								}
								dataList.clear();
							}
							
						}
						else
						{
							assetPreventiveDetailMap.put("Detail", "Not Available");
							assetPreventiveDetailMap.put("Due", "Not Available");
							assetPreventiveDetailMap.put("Next Due", "Not Available");
							assetPreventiveDetailMap.put("Mapped To","Not Available");
							assetPreventiveDetailMap.put("Contact No.","Not Available");
							assetPreventiveDetailMap.put("Ownership ","Not Available");
							assetPreventiveDetailMap.put("Frequency", "Not Available");
							assetPreventiveDetailMap.put("Reminder-3", "Not Available");
							assetPreventiveDetailMap.put("Reminder-2", "Not Available");
							assetPreventiveDetailMap.put("Reminder-1", "Not Available");
							assetPreventiveDetailMap.put("Document 1", "Not Available");
							assetPreventiveDetailMap.put("Document 2", "Not Available");
							assetPreventiveDetailMap.put("Last Action Doc", "Not Available");
						}
					}
					// Block for asset break down
					if(assetId!=null)
					{
						query.append("SELECT feedback.ticket_no,feed_catg.categoryName,feedback.open_date,feedback.open_time,feedback.resolve_duration,rca.rcaBrief,feedback.spare_used,feedback.status,feedback.id ");
						query.append(" FROM asset_complaint_status AS feedback ");
						query.append(" LEFT JOIN feedback_subcategory AS feed_subcatg ON feedback.subCatg = feed_subcatg.id ");
						query.append(" LEFT JOIN feedback_category AS feed_catg ON feed_subcatg.categoryName = feed_catg.id ");
						query.append(" LEFT JOIN rca_master AS rca ON feedback.rca = rca.id ");
						query.append(" WHERE feedback.asset_id="+assetId+" ORDER BY feedback.id DESC");
						
						dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query.setLength(0);
						if(dataList!=null && dataList.size()>0)
						{
							totalBreakDown=String.valueOf(dataList.size());
							int counter=1;
							AssetDashboardBean ADB=null;
							for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								if(counter>3)
									break;
								
								
								ADB=new AssetDashboardBean();
								Object[] object = (Object[]) iterator.next();

								if(counter==1)
								{
									if(object[7]!=null && !object[7].toString().equalsIgnoreCase("Resolved"))
										currentStatus="Breakdown";
								}
								
								
								ADB.setTicket_no(CUA.getValueWithNullCheck(object[0]));
								ADB.setFeedback_catg(CUA.getValueWithNullCheck(object[1]));
								ADB.setOpen_date(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[2]))+" "+CUA.getValueWithNullCheck(object[3]));
								ADB.setOffTime(CUA.getValueWithNullCheck(object[4]));
								ADB.setComments(CUA.getValueWithNullCheck(object[5]));
								ADB.setOther(CUA.getValueWithNullCheck(object[6]));
								ADB.setId(Integer.parseInt(object[8].toString()));
								
								breakDownList.add(ADB);
								
								counter++;
							}
							dataList.clear();
						}
						
					}
				}
				
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getCompliantDetailById()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				complainDetailList = new ArrayList<AssetDashboardBean>();
				StringBuilder query = new StringBuilder();
				query.append("SELECT feedback.ticket_no,feed_catg.categoryName,feedback.feed_brief,");
				query.append("feedback.open_date,feedback.open_time,feedback.resolve_date,feedback.resolve_time,");
				query.append("feedback.resolve_duration,rca.rcaBrief,feedback.spare_used,emp.empName,");
				query.append("feedback.level,asset.assetname,asset.serialno,asset.mfgserialno"); 
				query.append(" FROM asset_complaint_status AS feedback");
				query.append(" LEFT join feedback_subcategory AS feed_subcatg ON feedback.subCatg = feed_subcatg.id");
				query.append(" LEFT join feedback_category AS feed_catg ON feed_subcatg.categoryName = feed_catg.id");
				query.append(" LEFT JOIN employee_basic AS emp ON emp.id=feedback.resolve_by");
				query.append(" LEFT JOIN asset_detail AS asset ON asset.id=feedback.asset_id");
				query.append(" LEFT join rca_master AS rca ON feedback.rca = rca.id");
				query.append(" WHERE feedback.id="+complaintId);
				List dataList=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					AssetDashboardBean ADB=null;
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ADB = new AssetDashboardBean();
						Object[] object = (Object[]) iterator.next();
						ADB.setTicket_no(CUA.getValueWithNullCheck(object[0]));
						ADB.setFeedback_catg(CUA.getValueWithNullCheck(object[1]));
						ADB.setComments(CUA.getValueWithNullCheck(object[2]));
						ADB.setOpen_date(DateUtil.convertDateToIndianFormat(object[3].toString())+" "+object[4].toString().substring(0, 5));
						if(object[5]!=null)
							ADB.setActionTakenDate(DateUtil.convertDateToIndianFormat(object[5].toString())+" "+object[6].toString().substring(0, 5));
						else
							ADB.setActionTakenDate("Not Available");
						
						
						ADB.setTotalBreakdown(CUA.getValueWithNullCheck(object[7]));
						ADB.setRCA(CUA.getValueWithNullCheck(object[8]));
						ADB.setOther(CUA.getValueWithNullCheck(object[9]));
						ADB.setEmpName(CUA.getValueWithNullCheck(object[10]));
						if(object[11]!=null)
						{
							int currentLevel=Integer.valueOf(object[11].toString().substring(5));
							if(currentLevel>1)
								ADB.setEscFlag("Yes");
							else
								ADB.setEscFlag("No");
						}
						else
							ADB.setEscFlag("No");
						
						ADB.setEscLevel(CUA.getValueWithNullCheck(object[11]));
						
						assetName=CUA.getValueWithNullCheck(object[12]);
						assetCode=CUA.getValueWithNullCheck(object[13]);
						slNo=CUA.getValueWithNullCheck(object[14]);
						
						complainDetailList.add(ADB);
					}
				}
				
				
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String showAllSupportDetailsWithA4Sheet()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				assetSupportDetailMap = new LinkedHashMap<String, String>();
				assetPreventiveDetailMap = new LinkedHashMap<String, String>();
				List dataList =null,idList=new ArrayList();
				if(session.containsKey("remindIdd"))
				{
					reminderId=(String) session.get("remindIdd");
					System.out.println("Session Value Is "+reminderId);
					idList= (List) session.get("remindIddList");
					if(nextOrPrevious!=null && nextOrPrevious.equals("ForwardData"))
						reminderId=getNext(reminderId, idList);
					
					if(nextOrPrevious!=null && nextOrPrevious.equals("BackwardData"))
						reminderId=getPrevious(reminderId, idList);
					
				}
				else
				{
					idList=getAssetReminderId();
					System.out.println(">>>>> "+idList.size());
					if(idList!=null)
						session.put("remindIddList", idList);
					
				}
				
				if(idList!=null && idList.size()>0)
				{
					String tempId=null;
					tempId=getNext(reminderId, idList);
					
					if(tempId!=null && !tempId.equals(""))
					{
						backwardStatus=true;
						tempId=null;
					}
					
					tempId=getPrevious(reminderId, idList);
					
					if(tempId!=null && !tempId.equals(""))
					{
						forwardStatus=true;
						tempId=null;
					}
					
				}
				
				System.out.println("EEEEEEE "+reminderId);
				
				if(reminderId!=null)
				{
					StringBuilder query = new StringBuilder();
					ComplianceUniversalAction CUA = new ComplianceUniversalAction();
					query.append("SELECT reminder.id,reminder.dueDate,reminder.dueTime,reminder.actionStatus,suprtType.category,vendor.vendorname,vendor.contactno,reminder.detail,reminder.frequency,reminder.reminder_for,reminder.uploadDoc,reminder.uploadDoc1,ast.assetname,ast.serialno,ast.mfgserialno,ast.supportfrom,reminder.moduleName,reminder.action_type ");
					query.append(" FROM asset_reminder_details AS reminder ");
					query.append(" LEFT JOIN asset_detail AS ast ON ast.id=reminder.assetid ");
					query.append(" LEFT JOIN createasset_support_catg_master AS suprtType ON reminder.supporttype=suprtType.id ");
					query.append(" LEFT JOIN createvendor_master AS vendor ON vendor.id=reminder.vendorid ");
					query.append(" WHERE reminder.id="+reminderId);
					
					System.out.println("Query String Is "+query.toString());
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					query.setLength(0);
					if(dataList!=null && dataList.size()>0)
					{
						
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							
							assetName = CUA.getValueWithNullCheck(object[12]);
							assetCode = CUA.getValueWithNullCheck(object[13]);
							slNo = CUA.getValueWithNullCheck(object[14]);
							session.put("remindIdd", object[0].toString());
							System.out.println(CUA.getValueWithNullCheck(object[16]));
							if(CUA.getValueWithNullCheck(object[16]).equalsIgnoreCase("Support"))
							{
								assetSupportDetailMap.put("Support Type", CUA.getValueWithNullCheck(object[4]));
								assetSupportDetailMap.put("From Vendor", CUA.getValueWithNullCheck(object[5]));
								assetSupportDetailMap.put("Contact No.", CUA.getValueWithNullCheck(object[6]));
								assetSupportDetailMap.put("Support Detail", CUA.getValueWithNullCheck(object[7]));
								String supportFrom = CUA.getValueWithNullCheck(object[15]);
								
								if(supportFrom.equalsIgnoreCase("installon"))
									assetSupportDetailMap.put("Support From", "Install On");
								else if(supportFrom.equalsIgnoreCase("commssioningon"))
									assetSupportDetailMap.put("Support From", "Commssioning On");
								else if(supportFrom.equalsIgnoreCase("receivedOn"))
									assetSupportDetailMap.put("Support From", "Received On");
								else
									assetSupportDetailMap.put("Support From", supportFrom);
								
								assetSupportDetailMap.put("Support To", DateUtil.convertDateToIndianFormat(object[1].toString()));
								assetSupportDetailMap.put("Frequency", getFrequencyAbrivation(object[8].toString()));
								String arr[]=getEmpName(object[9].toString(),cbt,CUA).split("#");
								assetSupportDetailMap.put("Mapped To",arr[0]);
								assetSupportDetailMap.put("Contact No. ",arr[1]);
								String dataArr[]=getRemindersById(object[0].toString(),cbt);
								assetSupportDetailMap.put("Reminder-3", dataArr[0]);
								assetSupportDetailMap.put("Reminder-2", dataArr[1]);
								assetSupportDetailMap.put("Reminder-1", dataArr[2]);
								if(object[10]!=null)
								{
									String str=object[10].toString().substring(object[10].toString().lastIndexOf("//")+2, object[10].toString().length());
									String documentName=str.substring(14,str.length());
									assetSupportDetailMap.put("Document 1", documentName);
									doc1=object[10].toString();
								}
								else
								{
									assetSupportDetailMap.put("Document 1", "Not Available");
								}
								
								if(object[11]!=null)
								{
									String str=object[11].toString().substring(object[11].toString().lastIndexOf("//")+2, object[11].toString().length());
									String documentName=str.substring(14,str.length());
									assetSupportDetailMap.put("Document 2", documentName);
									doc2=object[11].toString();
								}
								else
								{
									assetSupportDetailMap.put("Document 2", "Not Available");
								}
								
								String docPath = getLastActionDoc(reminderId);
								if(docPath!=null)
								{
									String str=docPath.substring(docPath.lastIndexOf("//")+2, docPath.length());
									String documentName=str.substring(14,str.length());
									assetSupportDetailMap.put("Last Action Doc", documentName);
									lastActionDoc=docPath;
								}
								else
								{
									assetSupportDetailMap.put("Last Action Doc", "Not Available");
								}
								
								if(DateUtil.comparetwoDates(DateUtil.getCurrentDateUSFormat(),object[1].toString()))
								{
									supportStatus="Under AMC";
								}
								else if(!object[3].toString().equals("Done") || !object[3].toString().equals("Recurring"))
								{
									supportStatus="Expire";
								}
								frequency=object[8].toString();
							}
							else if(CUA.getValueWithNullCheck(object[16]).equalsIgnoreCase("Preventive"))
							{
								assetPreventiveDetailMap.put("Detail", CUA.getValueWithNullCheck(object[7]));
								assetPreventiveDetailMap.put("Due Date", DateUtil.convertDateToIndianFormat(object[1].toString()));
								assetPreventiveDetailMap.put("Next Due", DateUtil.convertDateToIndianFormat(getNextDueDate(object[8].toString(),object[1].toString())));
								String arr[]=getEmpName(object[9].toString(),cbt,CUA).split("#");
								assetPreventiveDetailMap.put("Mapped To",arr[0]);
								assetPreventiveDetailMap.put("Contact No.",arr[1]);
								assetPreventiveDetailMap.put("Ownership",DateUtil.makeTitle(CUA.getValueWithNullCheck(object[17])));
								frequency=getFrequencyAbrivation(object[8].toString());
								String dataArr[]=getRemindersById(object[0].toString(),cbt);
								assetPreventiveDetailMap.put("Reminder-3", dataArr[0]);
								assetPreventiveDetailMap.put("Reminder-2", dataArr[1]);
								assetPreventiveDetailMap.put("Reminder-1", dataArr[2]);
								frequency = getFrequencyAbrivation(object[8].toString());
								
								if(object[10]!=null)
								{
									String str=object[10].toString().substring(object[10].toString().lastIndexOf("//")+2, object[10].toString().length());
									String documentName=str.substring(14,str.length());
									assetPreventiveDetailMap.put("Document 1", documentName);
									pmDoc1=object[10].toString();
								}
								else
								{
									assetPreventiveDetailMap.put("Document 1", "Not Available");
								}
								
								if(object[11]!=null)
								{
									String str=object[11].toString().substring(object[11].toString().lastIndexOf("//")+2, object[11].toString().length());
									String documentName=str.substring(14,str.length());
									assetPreventiveDetailMap.put("Document 2", documentName);
									pmDoc2=object[11].toString();
								}
								else
								{
									assetPreventiveDetailMap.put("Document 2", "Not Available");
								}
								
								String docPath = getLastActionDoc(object[0].toString());
								if(docPath!=null)
								{
									String str=docPath.substring(docPath.lastIndexOf("//")+2, docPath.length());
									String documentName=str.substring(14,str.length());
									assetPreventiveDetailMap.put("Last Action Doc", documentName);
									pmLastActionDoc=docPath;
								}
								else
								{
									assetPreventiveDetailMap.put("Last Action Doc", "Not Available");
								}
							}
						}
					}
					dataList.clear();
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return SUCCESS;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	
	public String getLastActionDoc(String reminderId)
	{
		String docName=null;
		String query="SELECT document_takeaction FROM asset_report WHERE assetReminderID="+reminderId+" AND (actionTaken='Done' OR actionTaken='Recurring') ORDER BY id DESC LIMIT 0,1";
		List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
		if(dataList!=null && dataList.size()>0)
			docName=(String) dataList.get(0);
		
		
		return docName;
	}
	
	
	public String removeSessionValue()
	{
		System.out.println("Remove Session For "+removeSession4);
		if(removeSession4 != null)
		{
			if(removeSession4.equalsIgnoreCase("Asset"))
			{
				if(session.containsKey("assetIdd") && session.containsKey("assetIddList"))
				{
					session.remove("assetIdd");
					session.remove("assetIddList");
				}
			}
			else if(removeSession4.equalsIgnoreCase("reminder"))
			{
				if(session.containsKey("remindIdd") && session.containsKey("remindIddList"))
				{
					session.remove("remindIdd");
					session.remove("remindIddList");
				}
			}
		}
		
		
		return SUCCESS;
	}
	
	public String getSuprtPMSessionValue()
	{
		if(session.containsKey("remindIdd"))
		{
			assetReminderID=(String) session.get("remindIdd");
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}
	}
	
	public String getEmpNameByUserId(String userId,CommonOperInterface cbt)
	{
		String empName="Not Available";
		String query="SELECT empName FROM employee_basic AS emp INNER JOIN useraccount AS ua ON ua.id=emp.useraccountid WHERE ua.userID='"+userId+"'";
		List tempList=cbt.executeAllSelectQuery(query, connectionSpace);
		if(tempList!=null && tempList.size()>0)
			empName=(String) tempList.get(0);
		
		return empName;
	}
	
	
	public String getNextDueDate(String frequency,String dueDate)
	{
		if(frequency!=null && dueDate!=null)
		{
			if (frequency.equalsIgnoreCase("W"))
				dueDate = DateUtil.getNewDate("day", 7, dueDate);
			else if (frequency.equalsIgnoreCase("M"))
				dueDate = DateUtil.getNewDate("month", 1, dueDate);
			else if (frequency.equalsIgnoreCase("BM"))
				dueDate = DateUtil.getNewDate("day", 15, dueDate);
			else if (frequency.equalsIgnoreCase("Q"))
				dueDate = DateUtil.getNewDate("month", 3, dueDate);
			else if (frequency.equalsIgnoreCase("HY"))
				dueDate = DateUtil.getNewDate("month", 6, dueDate);
			else if (frequency.equalsIgnoreCase("Y"))
				dueDate = DateUtil.getNewDate("year", 1, dueDate);
			else if (frequency.equalsIgnoreCase("D"))
				dueDate = DateUtil.getNewDate("day", 1, dueDate);
		}
		return dueDate;
	}
	
	public String getFrequencyAbrivation(String frequency)
	{
		if(frequency!=null)
		{
			if (frequency.equalsIgnoreCase("W"))
				frequency="Weekly";
			else if (frequency.equalsIgnoreCase("M"))
				frequency="Monthly";
			else if (frequency.equalsIgnoreCase("BM"))
				frequency="Bi-Monthly";
			else if (frequency.equalsIgnoreCase("Q"))
				frequency="Quartly";
			else if (frequency.equalsIgnoreCase("HY"))
				frequency="Half Yearly";
			else if (frequency.equalsIgnoreCase("Y"))
				frequency="Yearly";
			else if (frequency.equalsIgnoreCase("D"))
				frequency="Daily";
			else if (frequency.equalsIgnoreCase("O"))
				frequency="One Time";
		}
		return frequency;
	}
	
	public String getEmpName(String contId,CommonOperInterface cbt,ComplianceUniversalAction CUA)
	{
		StringBuilder empNames = new StringBuilder();
		StringBuilder mobileNo = new StringBuilder();
		String empName=null;
		if(contId!=null)
			contId = contId.replace("#", ",")+"0";
		
		StringBuilder query=new StringBuilder();
		query.append("SELECT emp.empName,emp.mobOne FROM employee_basic AS emp ");
		query.append(" INNER JOIN compliance_contacts AS conatct ON conatct.emp_id=emp.id ");
		query.append(" WHERE conatct.id IN("+contId+")");
		
		List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				empNames.append(object[0].toString()+", ");
				
				mobileNo.append(CUA.getValueWithNullCheck(object[1].toString())+", ");
			}
			if(empNames.length()>0)
				empName=empNames.substring(0, empNames.length()-2).toString();
			
			if(mobileNo.length()>0)
				empName=empName+"#"+mobileNo.substring(0, mobileNo.length()-2).toString()+"#";
		}
		return empName;
	}
	
	public String getOnOffTimeAction(String assetReminderID ,CommonOperInterface cbt)
	{
		int onCount=0,offCount=0;
		String query="SELECT dueDate,actionTakenDate FROM asset_report WHERE (actionTaken='Done' OR actionTaken='Recurring') AND assetReminderID="+assetReminderID;
		List dataList= cbt.executeAllSelectQuery(query, connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(DateUtil.comparetwoDates(object[1].toString(),object[0].toString()))
					onCount++;
				else
					offCount++;
				
			}
		}
		return String.valueOf(onCount)+","+String.valueOf(offCount)+",";
	}
	
	@SuppressWarnings("unchecked")
	public String[] getRemindersById(String assetRemindId,CommonOperInterface cbt)
	{
		String dataArr[]={"Not Available","Not Available","Not Available"};
		
		String query="SELECT reminder_name,remind_date FROM asset_reminder_data WHERE reminder_code!='D' AND asseReminder_id="+assetRemindId;
		List dataList=cbt.executeAllSelectQuery(query, connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null)
				{
					if(object[0].toString().equalsIgnoreCase("Reminder 1"))
					{
						dataArr[0]=DateUtil.convertDateToIndianFormat(object[1].toString());
					}
					else if(object[0].toString().equalsIgnoreCase("Reminder 2"))
					{
						dataArr[1]=DateUtil.convertDateToIndianFormat(object[1].toString());
					}
					else if(object[0].toString().equalsIgnoreCase("Reminder 3"))
					{
						dataArr[2]=DateUtil.convertDateToIndianFormat(object[1].toString());
					}
				}
			}
		}
		return dataArr;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getAssetId()
	{
		List dataList = new ArrayList();
		String query="select asd.id,asd.assetname,asd.serialno,asd.assetbrand,asd.assetmodel,asd.specification,dept.deptName,at.assetSubCat,asd.pono,asd.quantity,asd.totalamount,vendor.vendorname,asd.receivedOn,asd.installon,asd.commssioningon,asd.expectedlife,asd.supportfrom,concat(asd.supportfor,' Months'),asd.mfgserialno from asset_detail as asd inner join createvendor_master as vendor on vendor.id=asd.vendorname inner join createasset_type_master as at on at.id=asd.assettype inner join department as dept on dept.id=asd.dept_subdept where asd.status='Active' order by asd.assetname";
		List tempList=new createTable().executeAllSelectQuery(query, connectionSpace);
		if(tempList!=null && tempList.size()>0)
		{
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				dataList.add(object[0].toString());
			}
		}
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	public List getAssetReminderId()
	{	
		List dataList = new ArrayList();
	 	String userEmpID=null;
	 	String empIdofuser = (String) session.get("empIdofuser");
	 	if (empIdofuser != null)
	 		userEmpID = empIdofuser.split("-")[1].trim();
	 	
		String strQuery = "SELECT cc.emp_id FROM compliance_contacts AS cc INNER JOIN contact_sharing_detail AS csd ON csd.sharing_with=cc.id WHERE cc.work_status=0 AND cc.moduleName='ASTM' AND csd.contact_id IN (SELECT id FROM compliance_contacts WHERE emp_id="+userEmpID+")";
		List shareDataCount = cbt.executeAllSelectQuery(strQuery, connectionSpace);
		StringBuilder empId = new StringBuilder();
		if (shareDataCount != null && shareDataCount.size() > 0)
		{
			for (int i = 0; i < shareDataCount.size(); i++)
			{
				empId.append(shareDataCount.get(i).toString()+",");
				String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", shareDataCount.get(i).toString());
				if (mappedEmpId != null)
				{
					empId.append(mappedEmpId+",");
				}
			}
		}
		String mappedEmpId = new ComplianceEditGridAction().getLevelHierarchy("ASTM", userEmpID);
		if (mappedEmpId != null)
		{
			empId.append(mappedEmpId+",");
		}
		empId.append(userEmpID);
		List tempList = new AssetUniversalHelper().getOutletAssetDetailByEmpId(empId.toString(),connectionSpace);
		if(tempList!=null && tempList.size()>0)
		{
			for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				dataList.add(object[9].toString());
			}
		}
		return dataList;
	}
	
	
	public String getNext(String uid,List dataList) 
	{
	    int idx = dataList.indexOf(uid);
	    if (idx < 0 || idx+1 == dataList.size()) return "";
	    return (String) dataList.get(idx + 1);
	}

	public String getPrevious(String uid,List dataList)
	{
	    int idx = dataList.indexOf(uid);
	    if (idx <= 0) return "";
	    return (String) dataList.get(idx - 1);
	}
	
	
	
	public String getDataId()
	{
		return dataId;
	}

	public void setDataId(String dataId)
	{
		this.dataId = dataId;
	}

	public String getIdFor()
	{
		return idFor;
	}

	public void setIdFor(String idFor)
	{
		this.idFor = idFor;
	}

	public Map<String, String> getAssetDetailMap()
	{
		return assetDetailMap;
	}

	public void setAssetDetailMap(Map<String, String> assetDetailMap)
	{
		this.assetDetailMap = assetDetailMap;
	}

	public Map<String, String> getAssetSupportDetailMap()
	{
		return assetSupportDetailMap;
	}

	public void setAssetSupportDetailMap(Map<String, String> assetSupportDetailMap)
	{
		this.assetSupportDetailMap = assetSupportDetailMap;
	}

	public Map<String, String> getAssetPreventiveDetailMap()
	{
		return assetPreventiveDetailMap;
	}

	public void setAssetPreventiveDetailMap(Map<String, String> assetPreventiveDetailMap)
	{
		this.assetPreventiveDetailMap = assetPreventiveDetailMap;
	}

	public String getAssetName()
	{
		return assetName;
	}

	public void setAssetName(String assetName)
	{
		this.assetName = assetName;
	}

	public String getAssetCode()
	{
		return assetCode;
	}

	public void setAssetCode(String assetCode)
	{
		this.assetCode = assetCode;
	}

	public String getSlNo()
	{
		return slNo;
	}

	public void setSlNo(String slNo)
	{
		this.slNo = slNo;
	}

	public String getFrequency()
	{
		return frequency;
	}


	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}


	public String getSupportStatus()
	{
		return supportStatus;
	}


	public void setSupportStatus(String supportStatus)
	{
		this.supportStatus = supportStatus;
	}

	public String getNextOrPrevious()
	{
		return nextOrPrevious;
	}

	public void setNextOrPrevious(String nextOrPrevious)
	{
		this.nextOrPrevious = nextOrPrevious;
	}

	public List<AssetDashboardBean> getSupportList()
	{
		return supportList;
	}

	public void setSupportList(List<AssetDashboardBean> supportList)
	{
		this.supportList = supportList;
	}

	public List<AssetDashboardBean> getPreventiveList()
	{
		return preventiveList;
	}

	public void setPreventiveList(List<AssetDashboardBean> preventiveList)
	{
		this.preventiveList = preventiveList;
	}

	public boolean isForwardStatus()
	{
		return forwardStatus;
	}

	public void setForwardStatus(boolean forwardStatus)
	{
		this.forwardStatus = forwardStatus;
	}

	public boolean isBackwardStatus()
	{
		return backwardStatus;
	}

	public void setBackwardStatus(boolean backwardStatus)
	{
		this.backwardStatus = backwardStatus;
	}

	public List<AssetDashboardBean> getBreakDownList()
	{
		return breakDownList;
	}

	public void setBreakDownList(List<AssetDashboardBean> breakDownList)
	{
		this.breakDownList = breakDownList;
	}

	public String getTotalBreakDown()
	{
		return totalBreakDown;
	}

	public void setTotalBreakDown(String totalBreakDown)
	{
		this.totalBreakDown = totalBreakDown;
	}

	public String getCurrentStatus()
	{
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}
	public List<AssetDashboardBean> getComplainDetailList()
	{
		return complainDetailList;
	}

	public void setComplainDetailList(List<AssetDashboardBean> complainDetailList)
	{
		this.complainDetailList = complainDetailList;
	}

	public String getComplaintId()
	{
		return complaintId;
	}

	public void setComplaintId(String complaintId)
	{
		this.complaintId = complaintId;
	}

	public String getDoc1()
	{
		return doc1;
	}

	public void setDoc1(String doc1)
	{
		this.doc1 = doc1;
	}

	public String getDoc2()
	{
		return doc2;
	}

	public void setDoc2(String doc2)
	{
		this.doc2 = doc2;
	}

	public String getLastActionDoc()
	{
		return lastActionDoc;
	}

	public void setLastActionDoc(String lastActionDoc)
	{
		this.lastActionDoc = lastActionDoc;
	}

	public String getReminderId() {
		return reminderId;
	}

	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

	public String getPmDoc1() {
		return pmDoc1;
	}

	public void setPmDoc1(String pmDoc1) {
		this.pmDoc1 = pmDoc1;
	}

	public String getPmDoc2() {
		return pmDoc2;
	}

	public void setPmDoc2(String pmDoc2) {
		this.pmDoc2 = pmDoc2;
	}

	public String getPmLastActionDoc() {
		return pmLastActionDoc;
	}

	public void setPmLastActionDoc(String pmLastActionDoc) {
		this.pmLastActionDoc = pmLastActionDoc;
	}

	public String getRemoveSession4() {
		return removeSession4;
	}

	public void setRemoveSession4(String removeSession4) {
		this.removeSession4 = removeSession4;
	}

	public String getAssetReminderID() {
		return assetReminderID;
	}

	public void setAssetReminderID(String assetReminderID) {
		this.assetReminderID = assetReminderID;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}
}
