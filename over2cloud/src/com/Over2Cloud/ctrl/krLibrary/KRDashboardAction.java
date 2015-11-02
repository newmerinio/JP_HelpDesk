package com.Over2Cloud.ctrl.krLibrary;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

public class KRDashboardAction extends GridPropertyBean
{
	private static final long serialVersionUID = -4053313260137617311L;
	private String fromDate;
	private String toDate;
	private String userType;
	private List<KRDashPojo> ksStatusList;
	private List<KRDashPojo> scoreKRList;
	private List<KRDashPojo> ageingKRList;
	private List<KRDashPojo> totalScoreKRList;
	private String pending;
	private String done;
	private String nonaction;
	private String lastMonthPending;
	private String lastMonthDone;
	private String lastMonthNonAction;
	private String lastWeekPending;
	private String lastWeekDone;
	private String lastWeekNonAction;
	private String yesterdayPending;
	private String yesterdayDone;
	private String yesterdayNonAction;
	private String todayPending;
	private String todayDone;
	private String todayNonAction;
	private List<Object> masterViewList=null;
	private String status;
	private String dataFor;
	
	@SuppressWarnings("rawtypes")
	public String beforeDashboard()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				KRDashboardHelper DH=new KRDashboardHelper();
				fromDate=DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-30));
				toDate=DateUtil.getCurrentDateIndianFormat();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String dataName=null;
				if (userType!=null && userType.equalsIgnoreCase("N"))
				{
					StringBuilder shareId=new StringBuilder();
					KRActionHelper KRH=new KRActionHelper();
					String[] loggedContactId=KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
					if (loggedContactId!=null && !loggedContactId.toString().equalsIgnoreCase(""))
					{
					    String contactId=null;
					    contactId=", "+loggedContactId[0]+", ";
					    String query="SELECT id,empName FROM kr_share_data";
					    List empData=cbt.executeAllSelectQuery(query, connectionSpace);
					    if (empData!=null && empData.size()>0)
						{
							for (Iterator iterator = empData.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[1]!=null)
								{
									String employeeName = ", "+object[1].toString()+", ";
									if (employeeName.contains(contactId))
									{
										shareId.append(object[0].toString()+",");
									}
								}
							}
							if (shareId.toString()!=null && !shareId.toString().equalsIgnoreCase(""))
							{
								dataName = shareId.toString().substring(0, shareId.toString().length()-1);
							}
						}
					}
				}
				// second Quadrant.......
				ksStatusList = new ArrayList<KRDashPojo>();
				ksStatusList=DH.getKRStatusData(connectionSpace,dataName,cbt);
				
				// First Quadrant........
				scoreKRList=new ArrayList<KRDashPojo>();
				scoreKRList=DH.getKRScoreData(connectionSpace,dataName,cbt,userName);
				
				return SUCCESS;
				
			}
			catch (Exception e)
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

	@SuppressWarnings("rawtypes")
	public String beforeDashboardModified()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				KRDashboardHelper DH=new KRDashboardHelper();
				System.out.println("INSIDE METHODD beforeDashboardModified");
				fromDate=DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-30));
				toDate=DateUtil.getCurrentDateIndianFormat();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String dataName=null;
				if (userType!=null && userType.equalsIgnoreCase("N"))
				{
					String employeeName=null;
					StringBuilder shareId=new StringBuilder();
					KRActionHelper KRH=new KRActionHelper();
					String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
					String loggedContactId=KRH.contactIdLoggenedMulti(empIdofuser, "KR", connectionSpace);
				    if (loggedContactId!=null && !loggedContactId.toString().equalsIgnoreCase(""))
					{
				    	String contactId[]=null;
					    String query="SELECT id,empName FROM kr_share_data";
					    List empData=cbt.executeAllSelectQuery(query, connectionSpace);
					    if (empData!=null && empData.size()>0)
						{
					    	contactId=loggedContactId.split(",");
							for (Iterator iterator = empData.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[1]!=null)
								{
									employeeName=", "+object[1].toString()+", ";
									for (String s : contactId) 
					    			{
					    				if (employeeName.contains(", "+s+","))
										{
					    					shareId.append(object[0].toString()+",");
										}
									}
								}
							}
							if (shareId.toString()!=null && !shareId.toString().equalsIgnoreCase(""))
							{
								dataName=shareId.toString().substring(0, shareId.toString().length()-1);
							}
						}
					}
				}
				// First Quadrant........
				scoreKRList=new ArrayList<KRDashPojo>();
				scoreKRList=DH.getKRScoreData(connectionSpace,dataName,cbt,userName);
				if (scoreKRList!=null && !scoreKRList.isEmpty())
				{
					int temp=0,temp2=0,temp4=0,temp5=0,temp6=0,temp7=0,temp8=0,temp9=0,temp1=0,temp3=0,temp10=0,temp11=0,temp12=0,temp13=0,temp14=0;
					for(KRDashPojo obj:scoreKRList)
					{
						temp=temp+Integer.valueOf(obj.getPending());
						temp2=temp2+Integer.valueOf(obj.getActionTaken());
						temp4=temp4+Integer.valueOf(obj.getActionable());
						temp5=temp5+Integer.valueOf(obj.getLastMonthPending());
						temp6=temp6+Integer.valueOf(obj.getLastMonthActionTaken());
						temp7=temp7+Integer.valueOf(obj.getLastMonthActionable());
						temp8=temp8+Integer.valueOf(obj.getLastWeekPending());
						temp9=temp9+Integer.valueOf(obj.getLastWeekActionTaken());
						temp1=temp1+Integer.valueOf(obj.getLastWeekActionable());
						temp3=temp3+Integer.valueOf(obj.getYesterPending());
						temp10=temp10+Integer.valueOf(obj.getYesterActionTaken());
						temp11=temp11+Integer.valueOf(obj.getYesterActionable());
						temp12=temp12+Integer.valueOf(obj.getTodayPending());
						temp13=temp13+Integer.valueOf(obj.getTodayActionTaken());
						temp14=temp14+Integer.valueOf(obj.getTodayActionable());
					}
					pending=String.valueOf(temp);
					done=String.valueOf(temp2);
					nonaction=String.valueOf(temp4);
					lastMonthPending=String.valueOf(temp5);
					lastMonthDone=String.valueOf(temp6);
					lastMonthNonAction=String.valueOf(temp7);
					lastWeekPending=String.valueOf(temp8);
					lastWeekDone=String.valueOf(temp9);
					lastWeekNonAction=String.valueOf(temp1);
					yesterdayPending=String.valueOf(temp2);
					yesterdayDone=String.valueOf(temp10);
					yesterdayNonAction=String.valueOf(temp11);
					todayPending=String.valueOf(temp12);
					todayDone=String.valueOf(temp13);
					todayNonAction=String.valueOf(temp14);
				}
				
				// second Quadrant.......
				ksStatusList = new ArrayList<KRDashPojo>();
				ksStatusList=DH.getKRStatusData(connectionSpace,dataName,cbt);
				
				// Third Quadrant........
				ageingKRList=new ArrayList<KRDashPojo>();
				ageingKRList=DH.getKRAgeingData(connectionSpace,dataName,cbt,userName);
				
				// Fourth Quadrant........
				totalScoreKRList =new ArrayList<KRDashPojo>();
				totalScoreKRList=DH.fetchTotalScoreDetails(connectionSpace,dataName,cbt,userName);
				
				return SUCCESS;
				
			}
			catch (Exception e)
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
	
	@SuppressWarnings("rawtypes")
	public String viewKrInGridDashboard()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_share_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
				if (dataCount != null && dataCount.size()>0)
				{
					System.out.println("Status For :::::::::"+status);
					String dataName=null;
					String employeeName=null;
					StringBuilder shareId=new StringBuilder();
					List<Object> Listhb=new ArrayList<Object>();
					if (status!=null && status.equalsIgnoreCase("Share With Me"))
					{
						String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
						KRActionHelper KRH=new KRActionHelper();
						String loggedContactId=KRH.contactIdLoggenedMulti(empIdofuser, "KR", connectionSpace);
					    if (loggedContactId!=null && !loggedContactId.toString().equalsIgnoreCase(""))
						{
					    	String contactId[]=null;
						    String query="SELECT id,empName FROM kr_share_data";
						    List empData=cbt.executeAllSelectQuery(query, connectionSpace);
						    if (empData!=null && empData.size()>0)
							{
						    	contactId=loggedContactId.split(",");
								for (Iterator iterator = empData.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[1]!=null)
									{
										employeeName=", "+object[1].toString()+", ";
										for (String s : contactId) 
						    			{
						    				if (employeeName.contains(", "+s+","))
											{
						    					shareId.append(object[0].toString()+",");
											}
										}
									}
								}
								if (shareId.toString()!=null && !shareId.toString().equalsIgnoreCase(""))
								{
									dataName=shareId.toString().substring(0, shareId.toString().length()-1);
								}
							}
						}
					}
					else if(status!=null && status.equalsIgnoreCase("Share By Me"))
					{
						dataName=userName;
					}
					else if(status!=null && status.equalsIgnoreCase("KRStatus"))
					{
						dataName=dataFor;
					}
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

					@SuppressWarnings("unchecked")
					List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("fieldsname");
					session.remove("fieldsname");
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					
					int i=0;
					if(fieldNames!=null && fieldNames.size()>0)
					{
						for(GridDataPropertyView gpv:fieldNames)
						{
							if(i<(fieldNames.size()-1))
							{
								if (gpv.getColomnName().equalsIgnoreCase("dept")) 
								{
									query.append("dept.deptName AS groupdept,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("groupName"))
								{
									query.append("grp.groupName,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("subGroupName"))
								{
									query.append("subGroup.subGroupName,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("doc_name"))
								{
									query.append("upload.upload1,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("tag_search"))
								{
									query.append("upload.tag_search,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("krName"))
								{
									query.append("upload.kr_name,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("krID"))
								{
									query.append("upload.kr_starting_id,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("document"))
								{
									query.append("share.doc_name,");
								}
								else if(gpv.getColomnName().equalsIgnoreCase("primaryAuthor"))
								{
									query.append("upload.userName AS primaryAuthor,");
								}
								else
								{
									query.append("share."+gpv.getColomnName().toString()+",");
								}
							}
							else
							{
								query.append("share."+gpv.getColomnName().toString());
							}
							i++;
						}
					}
					query.append(" from kr_share_data As share ");
					query.append(" LEFT JOIN kr_upload_data AS upload ON share.doc_name=upload.id ");
					query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.subGroupName=subGroup.id ");
					query.append(" LEFT JOIN kr_group_data AS grp ON subGroup.groupName=grp.id ");
					query.append(" LEFT JOIN department AS dept ON grp.dept=dept.id ");
					if (dataFor!=null && !dataFor.equalsIgnoreCase("") && dataFor.equalsIgnoreCase("download"))
					{
						query.append(" LEFT JOIN kr_share_download_history AS history ON share.id=history.shareId ");
					}
					else if(dataFor!=null && !dataFor.equalsIgnoreCase("") && dataFor.equalsIgnoreCase("edit"))
					{
						query.append(" LEFT JOIN kr_share_report_data AS report ON share.id=report.krSharingId ");
					}
					query.append(" WHERE ");
					query.append(" share.status='0' ");
					if (status!=null && status.equalsIgnoreCase("Share With Me"))
					{
						query.append(" AND share.id IN ("+dataName+") ");
					}
					else if(status!=null && status.equalsIgnoreCase("Share By Me"))
					{
						query.append(" AND share.userName = '"+dataName+"' ");
					}
					else if(status!=null && status.equalsIgnoreCase("KRStatus"))
					{
						query.append(" AND share.id = '"+dataName+"' ");
					}
					if (dataFor!=null && !dataFor.equalsIgnoreCase(""))
					{
						System.out.println("Dataa For :::::::::"+dataFor);
						if (dataFor.equalsIgnoreCase("TotalP"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Pending' ");
						}
						else if(dataFor.equalsIgnoreCase("TotalD"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Done' ");
						}
						else if(dataFor.equalsIgnoreCase("TotalN"))
						{
							query.append(" AND actionReq ='No' ");
						}
						else if(dataFor.equalsIgnoreCase("LastMP"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Pending' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-30)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("LastMD"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Done' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-30)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("LastMN"))
						{
							query.append(" AND actionReq ='No' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-30)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'  ");
						}
						else if(dataFor.equalsIgnoreCase("LastWP"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Pending' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-7)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("LastWD"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Done' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-7)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("LastWN"))
						{
							query.append(" AND actionReq ='No' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-30)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("YesterdayP"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Pending' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-1)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
						}
						else if(dataFor.equalsIgnoreCase("YesterdayD"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Done' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-1)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
						}
						else if(dataFor.equalsIgnoreCase("YesterdayN"))
						{
							query.append(" AND actionReq ='No' AND dueShareDate BETWEEN '"+DateUtil.getNextDateAfter(-1)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
						}
						else if(dataFor.equalsIgnoreCase("TodayP"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Pending' AND dueShareDate = '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("TodayD"))
						{
							query.append(" AND actionReq='Yes' AND actionStatus ='Done' AND dueShareDate = '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("TodayN"))
						{
							query.append(" AND actionReq ='No' AND dueShareDate = '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						else if(dataFor.equalsIgnoreCase("week"))
						{
							query.append("  AND dueShareDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNextDateAfter(7)+"' ");
						}
						else if(dataFor.equalsIgnoreCase("month"))
						{
							query.append("  AND dueShareDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNextDateAfter(30)+"' ");
						}
						else if(dataFor.equalsIgnoreCase("year"))
						{
							query.append("  AND dueShareDate BETWEEN '"+DateUtil.getCurrentDateUSFormat()+"' AND '"+DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat())+"' ");
						}
						else if(dataFor.equalsIgnoreCase("download"))
						{
							query.append(" GROUP BY history.shareId ");
						}
					}
					
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");

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
					System.out.println("query::::" + query);
					List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);

					if(data!=null && data.size()>0)
					{
						KRActionHelper KH=new KRActionHelper();
						ComplianceReminderHelper RH=new ComplianceReminderHelper();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							int k=0;
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for(GridDataPropertyView gpv:fieldNames)
							{
								if(obdata[k]!=null)
								{
									if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if(gpv.getColomnName().equalsIgnoreCase("deptName"))
									{
									    one.put(gpv.getColomnName(),KH.getValueWithNullCheck(KH.getMultipleDeptName(obdata[k+1].toString(), connectionSpace)));
									}
									else if(gpv.getColomnName().equalsIgnoreCase("empName"))
									{
									    one.put(gpv.getColomnName(),KH.getValueWithNullCheck(KH.getMultipleEmpName(obdata[k].toString(), connectionSpace)));
									}
									else if(gpv.getColomnName().equalsIgnoreCase("frequency"))
									{
									    one.put(gpv.getColomnName(),KH.getValueWithNullCheck(RH.getFrequencyName(obdata[k].toString())));
									}
									else
									{
										one.put(gpv.getColomnName(),obdata[k].toString());
									}
								}
								else
								{
									one.put(gpv.getColomnName().toString(), "NA");
								}
								k++;
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setRecords(masterViewList.size());
						if (to > getRecords())
							to = getRecords();
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	@SuppressWarnings(
			{
					"rawtypes", "unchecked"
			})
			public String viewKrUploadDash()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
				{
					try
					{
						CommonOperInterface cbt = new CommonConFactory()
								.createInterface();
						List data = null;
						List<Object> Listhb = new ArrayList<Object>();
						StringBuilder query1 = new StringBuilder("");
						query1.append("select count(*) from kr_upload_data");
						List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);

						if (dataCount != null && dataCount.size() > 0)
						{
							List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("fieldsname");
							session.remove("fieldsname");
							StringBuilder query = new StringBuilder("");
							query.append("select ");

							int i = 0;
							if (fieldNames != null && fieldNames.size() > 0)
							{
								for (GridDataPropertyView gpv : fieldNames)
								{
									if (i < (fieldNames.size() - 1))
									{
										if (gpv.getColomnName().equalsIgnoreCase("subGroupName"))
										{
											query.append("subGroup.subGroupName , ");
										}
										else if (gpv.getColomnName().equalsIgnoreCase("groupName"))
										{
											query.append("grp.groupName , ");
										}
										else if (gpv.getColomnName().equalsIgnoreCase("deptName"))
										{
											query.append("dept.deptName , ");
										}
										else if (gpv.getColomnName().equalsIgnoreCase("file"))
										{
											query.append("krUpload.upload1 AS file , ");
										}
										else
										{
											query.append("krUpload."+ gpv.getColomnName().toString()+ ",");
										}
									}
									else
									{
										query.append("krUpload."+ gpv.getColomnName().toString());
									}
									i++;
								}
								query.append(" from kr_upload_data AS krUpload");
								query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON subGroup.id =  krUpload.subGroupName ");
								query.append(" LEFT JOIN kr_group_data AS grp ON subGroup.groupName =  grp.id ");
								query.append(" LEFT JOIN department AS dept ON grp.dept =  dept.id ");
								query.append(" WHERE  krUpload.flag='0' ");
								if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("")
										&& !searchString.equalsIgnoreCase("") && !searchString.equalsIgnoreCase("-1"))
								{
									if (searchField.equalsIgnoreCase("toDate"))
									{
										query.append("AND krUpload.date BETWEEN '"
												+ DateUtil.convertDateToUSFormat(fromDate)
												+ "' AND '"+ DateUtil.convertDateToUSFormat(searchString)
												+ "'");
									}
									else if (searchField.equalsIgnoreCase("wildSearch"))
									{
										query.append("AND dept.id = '" + getSearchString()
												+ "' OR grp.id = '" + getSearchString()
												+ "' OR subGroup.id = '"
												+ getSearchString()
												+ "' OR krUpload.kr_starting_id = '"
												+ getSearchString()
												+ "' OR krUpload.kr_name = '"
												+ getSearchString()
												+ "' OR krUpload.tag_search LIKE '%"
												+ getSearchString() + "%'");
									}
									else
									{
										query.append("AND " + getSearchField() + " = '"
												+ getSearchString() + "'");
									}
								}
								else
								{
									query.append("AND krUpload.username= '"+userName+"'");
								}
								System.out.println("QUERY IS AS :::  "+ query.toString());
								data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);

								if (data != null && data.size() > 0)
								{
									KRActionHelper KH=new KRActionHelper();
									for (Iterator it = data.iterator(); it.hasNext();)
									{
										int k = 0;
										Object[] obdata = (Object[]) it.next();
										Map<String, Object> one = new HashMap<String, Object>();
										for (GridDataPropertyView gpv : fieldNames)
										{
											if (obdata[k] != null)
											{
												if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
												{
													one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if (gpv.getColomnName().equalsIgnoreCase("tag_search"))
												{
													one.put(gpv.getColomnName(),obdata[k].toString().replace(",", ", "));
												}
												else
												{
													one.put(gpv.getColomnName(),obdata[k].toString());
												}
											}
											else
											{
												one.put(gpv.getColomnName().toString(),
														"NA");
											}
											k++;
										}
										Listhb.add(one);
									}
									setMasterViewList(Listhb);
									setRecords(masterViewList.size());
									int to = (getRows() * getPage());
									if (to > getRecords())
										to = getRecords();
									setTotal((int) Math.ceil((double) getRecords()
											/ (double) getRows()));
								}
							}
						}
						returnResult = SUCCESS;
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
	public String getUserType()
	{
		return userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public List<KRDashPojo> getKsStatusList()
	{
		return ksStatusList;
	}
	public void setKsStatusList(List<KRDashPojo> ksStatusList)
	{
		this.ksStatusList = ksStatusList;
	}
	public List<KRDashPojo> getScoreKRList()
	{
		return scoreKRList;
	}
	public void setScoreKRList(List<KRDashPojo> scoreKRList)
	{
		this.scoreKRList = scoreKRList;
	}

	public List<KRDashPojo> getAgeingKRList()
	{
		return ageingKRList;
	}

	public void setAgeingKRList(List<KRDashPojo> ageingKRList)
	{
		this.ageingKRList = ageingKRList;
	}

	public List<KRDashPojo> getTotalScoreKRList()
	{
		return totalScoreKRList;
	}

	public void setTotalScoreKRList(List<KRDashPojo> totalScoreKRList)
	{
		this.totalScoreKRList = totalScoreKRList;
	}

	public String getPending()
	{
		return pending;
	}

	public void setPending(String pending)
	{
		this.pending = pending;
	}

	public String getDone()
	{
		return done;
	}

	public void setDone(String done)
	{
		this.done = done;
	}

	public String getNonaction()
	{
		return nonaction;
	}

	public void setNonaction(String nonaction)
	{
		this.nonaction = nonaction;
	}

	public String getLastMonthPending()
	{
		return lastMonthPending;
	}

	public void setLastMonthPending(String lastMonthPending)
	{
		this.lastMonthPending = lastMonthPending;
	}

	public String getLastMonthDone()
	{
		return lastMonthDone;
	}

	public void setLastMonthDone(String lastMonthDone)
	{
		this.lastMonthDone = lastMonthDone;
	}

	public String getLastMonthNonAction()
	{
		return lastMonthNonAction;
	}

	public void setLastMonthNonAction(String lastMonthNonAction)
	{
		this.lastMonthNonAction = lastMonthNonAction;
	}

	public String getLastWeekPending()
	{
		return lastWeekPending;
	}

	public void setLastWeekPending(String lastWeekPending)
	{
		this.lastWeekPending = lastWeekPending;
	}

	public String getLastWeekDone()
	{
		return lastWeekDone;
	}

	public void setLastWeekDone(String lastWeekDone)
	{
		this.lastWeekDone = lastWeekDone;
	}

	public String getLastWeekNonAction()
	{
		return lastWeekNonAction;
	}

	public void setLastWeekNonAction(String lastWeekNonAction)
	{
		this.lastWeekNonAction = lastWeekNonAction;
	}

	public String getYesterdayPending()
	{
		return yesterdayPending;
	}

	public void setYesterdayPending(String yesterdayPending)
	{
		this.yesterdayPending = yesterdayPending;
	}

	public String getYesterdayDone()
	{
		return yesterdayDone;
	}

	public void setYesterdayDone(String yesterdayDone)
	{
		this.yesterdayDone = yesterdayDone;
	}

	public String getYesterdayNonAction()
	{
		return yesterdayNonAction;
	}

	public void setYesterdayNonAction(String yesterdayNonAction)
	{
		this.yesterdayNonAction = yesterdayNonAction;
	}

	public String getTodayPending()
	{
		return todayPending;
	}

	public void setTodayPending(String todayPending)
	{
		this.todayPending = todayPending;
	}

	public String getTodayDone()
	{
		return todayDone;
	}

	public void setTodayDone(String todayDone)
	{
		this.todayDone = todayDone;
	}

	public String getTodayNonAction()
	{
		return todayNonAction;
	}

	public void setTodayNonAction(String todayNonAction)
	{
		this.todayNonAction = todayNonAction;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}
	
}
