package com.Over2Cloud.ctrl.VAM.master;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.asset.AssetCommonAction;
import com.Over2Cloud.ctrl.asset.AssetDashboardAction;
import com.Over2Cloud.ctrl.asset.AssetDashboardBean;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DashboardVAMAction extends ActionSupport{

	static final Log log = LogFactory.getLog(AssetDashboardAction.class);
	Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private HttpServletRequest request;
	private Map<Integer, String> deptList = null;
	private String datetoday;
	private int totalvisitor;
	private int visitorin;
	private int visitorout;
	private List<Object> deptname;
	private ArrayList<String> deptlist;
	private ArrayList<String> alertdeptlist;
	private List<Object> purposename;
	private ArrayList<String> purposelist;
	private List<Object> dateoneweek;
	private ArrayList<String> datesevenlist;
	ArrayList<ArrayList<String>> listOlists = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> purposeListOflists = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> stayAlertListOflists = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> sevendayslistoflist = new ArrayList<ArrayList<String>>();
	private List<GridDataPropertyView> dashboardVisitorDetail=new ArrayList<GridDataPropertyView>();
	private List<Object> dashboardvisitorData;
	private String deptName;
	private int visitorInForDept;
	private int visitorOutForDept;
	private int visitorPendingForDept;
	private int visitorInForPurpose;
	private int visitorOutForPurpose;
	private int visitorPendingForPurpose;
	private TreeMap<String, String> graphMap;
	
	private String visitorName;
	private String timeIn;
	private String timeAlloted;
	private String dataFor;
	private String type;
	private String deptId;
	private String assetid;
	int size=0;
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
	//private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private String id;

	private String mainHeaderName=null;
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private double randomNum; 
		
	public String dashboardVisitorView()
	{
		String strResult = ERROR;
		String val=null;
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
					
					for(Iterator itr = fieldNames.iterator(); itr.hasNext();)
					{
						val = itr.next().toString();
					
						if(val.equalsIgnoreCase("location") || val.equalsIgnoreCase("gate") || val.equalsIgnoreCase("visit_date") || val.equalsIgnoreCase("time_in") || val.equalsIgnoreCase("time_out") || val.equalsIgnoreCase("image") || val.equalsIgnoreCase("possession") || val.equalsIgnoreCase("alert_after") || val.equalsIgnoreCase("status") || val.equalsIgnoreCase("one_time_password") || val.equalsIgnoreCase("barcode") || val.equalsIgnoreCase("address") || val.equalsIgnoreCase("date") || val.equalsIgnoreCase("time") || val.equalsIgnoreCase("userName") || val.equalsIgnoreCase("empName") || val.equalsIgnoreCase("id") || val.equalsIgnoreCase("alert_time") || val.equalsIgnoreCase("accept") || val.equalsIgnoreCase("reject") || val.equalsIgnoreCase("db_transfer") || val.equalsIgnoreCase("ip") || val.equalsIgnoreCase("update_date") || val.equalsIgnoreCase("update_time") || val.equalsIgnoreCase("car_number") || val.equalsIgnoreCase("comments")) 
						{
							itr.remove();
						}
					}
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
								{
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}else
									{
										query.append("vid." + object.toString());
									}
									
								}
									
							}
							i++;
						}

						query.append(" " + queryEnd.toString());
						query.append("where vid.visit_date = '"+DateUtil.getCurrentDateUSFormat()+"' and vid.time_out is null ");
						//System.out.println("query>>>>RRRRRRR>>>>>>>>>>"+query.toString());
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

						System.out.println("Querry dash board visitor2>>" + query.toString());
						List data = cbt.executeAllSelectQuery(query.toString(),
						    connectionSpace);
						List<Object> listhb = new ArrayList<Object>();
						if (data != null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] obdata = (Object[]) iterator.next();

								Map<String, Object> one = new LinkedHashMap<String, Object>();
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
												one.put(fieldNames.get(k).toString(),obdata[k]);
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
							
							setDashboardvisitorData(listhb);
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
				    + "Problem in Over2Cloud  method <dashboardVisitorView> of class "
				    + getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
		return strResult;
	}
	public String dashboardVehicleView()
	{
		String strResult = ERROR;
		String val=null;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from vehicle_entry_details");
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
					queryEnd.append(" from vehicle_entry_details as vid ");
					List fieldNames = Configuration.getColomnList(
					    "mapped_vehicle_entry_configuration", accountID, connectionSpace,
					    "vehicle_entry_configuration");
					
					for(Iterator itr = fieldNames.iterator(); itr.hasNext();)
					{
						val = itr.next().toString();
						
						if( val.equalsIgnoreCase("id")
						   ||val.equalsIgnoreCase("alert_after")
						   ||val.equalsIgnoreCase("vehicle_model")
						   ||val.equalsIgnoreCase("barcode")
						   || val.equalsIgnoreCase("entry_date") 
						   || val.equalsIgnoreCase("entry_time") 
						   || val.equalsIgnoreCase("out_time") 
						   || val.equalsIgnoreCase("invoce_no") 
						   || val.equalsIgnoreCase("trip_no") 
						   || val.equalsIgnoreCase("no_of_bill") 
						   || val.equalsIgnoreCase("userName") 
						   || val.equalsIgnoreCase("date") 
						   || val.equalsIgnoreCase("time") 
						   || val.equalsIgnoreCase("alert_time") 
						   || val.equalsIgnoreCase("image")  
						   || val.equalsIgnoreCase("status") 
						   || val.equalsIgnoreCase("db_transfer") 
						   || val.equalsIgnoreCase("alert_time") 
						   || val.equalsIgnoreCase("reject")  
						   || val.equalsIgnoreCase("ip") 
						   || val.equalsIgnoreCase("update_date") 
						   || val.equalsIgnoreCase("update_time") 
						   || val.equalsIgnoreCase("vehicle_owner") 
						   || val.equalsIgnoreCase("vh_owner_mob"))
						{
							itr.remove();
						}
					}
					
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
								    else if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.jlocation_name , ");
										queryEnd
										    .append(" left join vh_location_jbm as dept on dept.id = vid.deptName ");
									}else
									{
										query.append("vid." + object.toString() + ",");
									}
									
								}
								else
								{   
								     if (object.toString().equalsIgnoreCase("purpose"))
								        {  
									       query.append(" pradmin.purpose ");
									       queryEnd
									                   .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
								        }
								     else
										query.append("vid." + object.toString());
								}
									
							}
							i++;
						}

						query.append(" " + queryEnd.toString());
						query.append("where vid.entry_date = '"+DateUtil.getCurrentDateUSFormat()+"' and vid.out_time is null ");
						query.append(" and pradmin.purpose_for = 'Vehicle' ");
	
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
						    "vehicle_entry_details", connectionSpace);

						System.out.println("Querry dash board vehicle>>>>>>>>>>>>>>>>>>>>>>>>>>" + query.toString());
						List data = cbt.executeAllSelectQuery(query.toString(),
						    connectionSpace);
						List<Object> listhb = new ArrayList<Object>();
						if (data != null && !data.isEmpty())
						{
							for (Iterator iterator = data.iterator(); iterator.hasNext();)
							{
								Object[] obdata = (Object[]) iterator.next();

								Map<String, Object> one = new LinkedHashMap<String, Object>();
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
												one.put(fieldNames.get(k).toString(),obdata[k]);
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
															if(obdata[k] != null &&  obdata[k] !=null)
															{
																one.put(fieldNames.get(k).toString(),
																    obdata[k].toString());
															}
														}
												else if (fieldNames.get(k) != null
													    && fieldNames.get(k).toString()
											             .equalsIgnoreCase("entry_date"))
														{
															if(obdata[k+1] != null &&  obdata[k] !=null)
															{
																one.put(fieldNames.get(k).toString(),
																   DateUtil.convertDateToIndianFormat(obdata[k].toString())+"   "+obdata[k+1].toString());
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
								listhb.add(one);
							}
							
							setDashboardvisitorData(listhb);
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
				    + "Problem in Over2Cloud  method <dashboardVisitorView> of class "
				    + getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
		return strResult;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String getDataForVAMDashboard()
	{
		String returnResult=ERROR;
		String fromdate = DateUtil.getDateAfterDays(-7); 
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String username=(String) session.get("uName");
				
				// Code for Visitor details of current date....
				datetoday = DateUtil.getCurrentDateUSFormat();
				deptname = CommonMethod.getVisitedDept(connectionSpace);
				purposename = CommonMethod.getVisitedPurpose(connectionSpace);
				dateoneweek = CommonMethod.getOneWeekDate(connectionSpace, fromdate, datetoday);
				Iterator dateitr = dateoneweek.iterator();
				while(dateitr.hasNext())
				{
					datesevenlist = new ArrayList<String>();
					Object dateres = (Object) dateitr.next();
					datesevenlist.add(DateUtil.convertDateToIndianFormat(dateres.toString()));
					totalvisitor = CommonMethod.getTotalVisitorToday(connectionSpace, dateres.toString());
					datesevenlist.add(Integer.toString(totalvisitor));
					visitorin = CommonMethod.getVisitorInToday(connectionSpace, dateres.toString());
					datesevenlist.add(Integer.toString(visitorin));
					visitorout = CommonMethod.getVisitorExitToday(connectionSpace, dateres.toString());
					datesevenlist.add(Integer.toString(visitorout));
					sevendayslistoflist.add(datesevenlist);
				}
				
				Iterator iter = deptname.iterator();
				while (iter.hasNext()) {
					deptlist = new ArrayList<String>();
			        Object result= (Object) iter.next();
			        deptlist.add(result.toString());
			        String deptId = CommonMethod.getDeptId(connectionSpace, result.toString());
			        visitorInForDept = CommonMethod.getVisitorInTodayForDept(connectionSpace, datetoday, deptId);
			        deptlist.add(Integer.toString(visitorInForDept));
			        visitorOutForDept = CommonMethod.getVisitorOutTodayForDept(connectionSpace, datetoday, deptId);
			        deptlist.add(Integer.toString(visitorOutForDept));
			        visitorPendingForDept = CommonMethod.getVisitorPendingTodayForDept(connectionSpace, datetoday, deptId);
			        deptlist.add(Integer.toString(visitorPendingForDept));
			        listOlists.add(deptlist);
				}
				Iterator iterpurpose = purposename.iterator();
				while (iterpurpose.hasNext()) {
					purposelist = new ArrayList<String>();
					Object purposeobject = (Object) iterpurpose.next();
					purposelist.add(purposeobject.toString());	
					String purposeId = CommonMethod.getPurposeId(connectionSpace, purposeobject.toString());
					visitorInForPurpose = CommonMethod.getVisitorInTodayForPurpose(connectionSpace, datetoday, purposeId);
					purposelist.add(Integer.toString(visitorInForPurpose));
					visitorOutForPurpose = CommonMethod.getVisitorOutTodayForPurpose(connectionSpace, datetoday, purposeId);
					purposelist.add(Integer.toString(visitorOutForPurpose));
					visitorPendingForPurpose = CommonMethod.getVisitorPendingTodayForPurpose(connectionSpace, datetoday, purposeId);
					purposelist.add(Integer.toString(visitorPendingForPurpose));
					purposeListOflists.add(purposelist);
					
				}
				Iterator iterstayalrt = deptname.iterator();
				while (iterstayalrt.hasNext()) {
					alertdeptlist = new ArrayList<String>();
					Object stayalrtobject = (Object) iterstayalrt.next();
					alertdeptlist.add(stayalrtobject.toString());
					String deptId = CommonMethod.getDeptId(connectionSpace, stayalrtobject.toString());
					visitorName = CommonMethod.getVisitorNameforDeptId(connectionSpace, datetoday, deptId);
					alertdeptlist.add(visitorName);
					timeIn = CommonMethod.getVisitorInTime(connectionSpace, datetoday, deptId);
					alertdeptlist.add(timeIn);
					timeAlloted = CommonMethod.getVisitPurposeTime(connectionSpace, datetoday, deptId);
					alertdeptlist.add(timeAlloted);
					stayAlertListOflists.add(alertdeptlist);
				}
				
				 returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getDataForVAMDashboard of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
		
	}
	public String dataForVehicleDashboard()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String username=(String) session.get("uName");
				//vehicle one week report
				oneweekVehicleReport();
				//Vehicle departmentwise report
				deptwiseVehicleReport();
               //vehicle purposewise report
				purposewiseVehicleReport();
				
				/*
				Iterator iterstayalrt = deptname.iterator();
				while (iterstayalrt.hasNext()) {
					alertdeptlist = new ArrayList<String>();
					Object stayalrtobject = (Object) iterstayalrt.next();
					alertdeptlist.add(stayalrtobject.toString());
					String deptId = CommonMethod.getDeptId(connectionSpace, stayalrtobject.toString());
					visitorName = CommonMethod.getVisitorNameforDeptId(connectionSpace, datetoday, deptId);
					alertdeptlist.add(visitorName);
					timeIn = CommonMethod.getVisitorInTime(connectionSpace, datetoday, deptId);
					alertdeptlist.add(timeIn);
					timeAlloted = CommonMethod.getVisitPurposeTime(connectionSpace, datetoday, deptId);
					alertdeptlist.add(timeAlloted);
					stayAlertListOflists.add(alertdeptlist);
				}*/
				
				 returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method getDataForVAMDashboard of class "+getClass(), e);
			}
		}
		else
		{
			returnResult=LOGIN;
		}
	return returnResult;	
}
	
	public String pieChartDeptwiseReport()
	{
		graphMap = new TreeMap<String, String>();
		graphMap = CommonMethod.getDeptwiseVisitorReport(connectionSpace);
		return SUCCESS;
	}
	public String pieChartVehicleDeptwiseReport()
	{
		graphMap = new TreeMap<String, String>();
		graphMap = CommonMethod.getDeptwiseVehicleReport(connectionSpace);
		return SUCCESS;
	}
	public String pieChartPurposewiseReport()
	{
		graphMap = new TreeMap<String, String>();
		graphMap = CommonMethod.getPurposewiseVisitorReport(connectionSpace);
		return SUCCESS;
	}
	public String pieChartVehiclePurposewiseReport()
	{
		graphMap = new TreeMap<String, String>();
		graphMap = CommonMethod.getPurposewiseVehicleReport(connectionSpace);
		return SUCCESS;
	}
	
	public String tableDeptwiseDashReport()
	{
		datetoday = DateUtil.getCurrentDateUSFormat();
		deptname = CommonMethod.getVisitedDept(connectionSpace);
		Iterator iter = deptname.iterator();
		while (iter.hasNext()) {
			deptlist = new ArrayList<String>();
	        Object result= (Object) iter.next();
	        deptlist.add(result.toString());
	        String deptId = CommonMethod.getDeptId(connectionSpace, result.toString());
	        visitorInForDept = CommonMethod.getVisitorInTodayForDept(connectionSpace, datetoday, deptId);
	        deptlist.add(Integer.toString(visitorInForDept));
	        visitorOutForDept = CommonMethod.getVisitorOutTodayForDept(connectionSpace, datetoday, deptId);
	        deptlist.add(Integer.toString(visitorOutForDept));
	        visitorPendingForDept = CommonMethod.getVisitorPendingTodayForDept(connectionSpace, datetoday, deptId);
	        deptlist.add(Integer.toString(visitorPendingForDept));
	        listOlists.add(deptlist);
		}
		return SUCCESS;
	}
	public String deptwiseVehicleReport()
	{
		datetoday = DateUtil.getCurrentDateUSFormat();
		deptname = CommonMethod.getVehicleDept(connectionSpace);
		Iterator iter = deptname.iterator();
		while (iter.hasNext()) {
			deptlist = new ArrayList<String>();
	        Object result= (Object) iter.next();
	        deptlist.add(result.toString());
	        String deptId = CommonMethod.getVehicleDeptId(connectionSpace, result.toString());
	        visitorInForDept = CommonMethod.getVehicleInTodayForDept(connectionSpace, datetoday, deptId);
	        deptlist.add(Integer.toString(visitorInForDept));
	        visitorOutForDept = CommonMethod.getVehicleOutTodayForDept(connectionSpace, datetoday, deptId);
	        deptlist.add(Integer.toString(visitorOutForDept));
	        visitorPendingForDept = CommonMethod.getVehiclePendingTodayForDept(connectionSpace, datetoday, deptId);
	        deptlist.add(Integer.toString(visitorPendingForDept));
	        listOlists.add(deptlist);
		}
		return SUCCESS;
	}

	public String tablePurposewiseDashReport()
	{
		datetoday = DateUtil.getCurrentDateUSFormat();
		purposename = CommonMethod.getVisitedPurpose(connectionSpace);
		Iterator iterpurpose = purposename.iterator();
		while (iterpurpose.hasNext()) {
			purposelist = new ArrayList<String>();
			Object purposeobject = (Object) iterpurpose.next();
			purposelist.add(purposeobject.toString());	
			String purposeId = CommonMethod.getPurposeId(connectionSpace, purposeobject.toString());
			visitorInForPurpose = CommonMethod.getVisitorInTodayForPurpose(connectionSpace, datetoday, purposeId);
			purposelist.add(Integer.toString(visitorInForPurpose));
			visitorOutForPurpose = CommonMethod.getVisitorOutTodayForPurpose(connectionSpace, datetoday, purposeId);
			purposelist.add(Integer.toString(visitorOutForPurpose));
			visitorPendingForPurpose = CommonMethod.getVisitorPendingTodayForPurpose(connectionSpace, datetoday, purposeId);
			purposelist.add(Integer.toString(visitorPendingForPurpose));
			purposeListOflists.add(purposelist);
		}
		return SUCCESS;
	}
	public String purposewiseVehicleReport()
	{
		datetoday = DateUtil.getCurrentDateUSFormat();
		purposename = CommonMethod.getVehiclePurpose(connectionSpace);
		Iterator iterpurpose = purposename.iterator();
		while (iterpurpose.hasNext()) {
			purposelist = new ArrayList<String>();
			Object purposeobject = (Object) iterpurpose.next();
			purposelist.add(purposeobject.toString());	
			String purposeId = CommonMethod.getVehiclePurposeId(connectionSpace, purposeobject.toString());
			visitorInForPurpose = CommonMethod.getVehicleInTodayForPurpose(connectionSpace, datetoday, purposeId);
			purposelist.add(Integer.toString(visitorInForPurpose));
			visitorOutForPurpose = CommonMethod.getVehicleOutTodayForPurpose(connectionSpace, datetoday, purposeId);
			purposelist.add(Integer.toString(visitorOutForPurpose));
			visitorPendingForPurpose = CommonMethod.getVehiclePendingTodayForPurpose(connectionSpace, datetoday, purposeId);
			purposelist.add(Integer.toString(visitorPendingForPurpose));
			purposeListOflists.add(purposelist);
			
		}
		return SUCCESS;
	}
	public void oneweekVehicleReport()
	{   // Code for Visitor details of current dat...
		String fromdate = DateUtil.getDateAfterDays(-7); 
	    datetoday = DateUtil.getCurrentDateUSFormat();
		dateoneweek = CommonMethod.getOneWeekDate(connectionSpace, fromdate, datetoday);
	    Iterator dateitr = dateoneweek.iterator();
	     while(dateitr.hasNext())
	          {
		       datesevenlist = new ArrayList<String>();
		       Object dateres = (Object) dateitr.next();
		       datesevenlist.add(DateUtil.convertDateToIndianFormat(dateres.toString()));
		       totalvisitor = CommonMethod.getTotalVehicleToday(connectionSpace, dateres.toString());
		       datesevenlist.add(Integer.toString(totalvisitor));
		       visitorin = CommonMethod.getVehicleInToday(connectionSpace, dateres.toString());
		       datesevenlist.add(Integer.toString(visitorin));
		       visitorout = CommonMethod.getVehicleExitToday(connectionSpace, dateres.toString());
		       datesevenlist.add(Integer.toString(visitorout));
		       sevendayslistoflist.add(datesevenlist);
	          }
	}
	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public String getDataFor() {
		return dataFor;
	}

	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public double getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(double randomNum) {
		this.randomNum = randomNum;
	}

	public String getAssetid() {
		return assetid;
	}
	public void setAssetid(String assetid) {
		this.assetid = assetid;
	}
	public String getDatetoday() {
		return datetoday;
	}
	public void setDatetoday(String datetoday) {
		this.datetoday = datetoday;
	}
	public int getTotalvisitor() {
		return totalvisitor;
	}
	public void setTotalvisitor(int totalvisitor) {
		this.totalvisitor = totalvisitor;
	}
	public int getVisitorin() {
		return visitorin;
	}
	public void setVisitorin(int visitorin) {
		this.visitorin = visitorin;
	}
	public int getVisitorout() {
		return visitorout;
	}
	public void setVisitorout(int visitorout) {
		this.visitorout = visitorout;
	}
	public List<Object> getDeptname() {
		return deptname;
	}
	public void setDeptname(List<Object> deptname) {
		this.deptname = deptname;
	}
	public int getVisitorInForDept() {
		return visitorInForDept;
	}
	public void setVisitorInForDept(int visitorInForDept) {
		this.visitorInForDept = visitorInForDept;
	}
	public int getVisitorOutForDept() {
		return visitorOutForDept;
	}
	public void setVisitorOutForDept(int visitorOutForDept) {
		this.visitorOutForDept = visitorOutForDept;
	}
	public int getVisitorPendingForDept() {
		return visitorPendingForDept;
	}
	public void setVisitorPendingForDept(int visitorPendingForDept) {
		this.visitorPendingForDept = visitorPendingForDept;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public ArrayList<String> getDeptlist() {
		return deptlist;
	}

	public void setDeptlist(ArrayList<String> deptlist) {
		this.deptlist = deptlist;
	}

	public ArrayList<ArrayList<String>> getListOlists() {
		return listOlists;
	}
	public void setListOlists(ArrayList<ArrayList<String>> listOlists) {
		this.listOlists = listOlists;
	}
	public List<Object> getPurposename() {
		return purposename;
	}
	public void setPurposename(List<Object> purposename) {
		this.purposename = purposename;
	}
	public ArrayList<String> getPurposelist() {
		return purposelist;
	}
	public void setPurposelist(ArrayList<String> purposelist) {
		this.purposelist = purposelist;
	}
	public int getVisitorInForPurpose() {
		return visitorInForPurpose;
	}
	public void setVisitorInForPurpose(int visitorInForPurpose) {
		this.visitorInForPurpose = visitorInForPurpose;
	}
	public int getVisitorOutForPurpose() {
		return visitorOutForPurpose;
	}
	public void setVisitorOutForPurpose(int visitorOutForPurpose) {
		this.visitorOutForPurpose = visitorOutForPurpose;
	}
	public int getVisitorPendingForPurpose() {
		return visitorPendingForPurpose;
	}
	public void setVisitorPendingForPurpose(int visitorPendingForPurpose) {
		this.visitorPendingForPurpose = visitorPendingForPurpose;
	}
	public ArrayList<ArrayList<String>> getPurposeListOflists() {
		return purposeListOflists;
	}
	public void setPurposeListOflists(
			ArrayList<ArrayList<String>> purposeListOflists) {
		this.purposeListOflists = purposeListOflists;
	}
	public ArrayList<String> getAlertdeptlist() {
		return alertdeptlist;
	}
	public void setAlertdeptlist(ArrayList<String> alertdeptlist) {
		this.alertdeptlist = alertdeptlist;
	}
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getTimeAlloted() {
		return timeAlloted;
	}
	public void setTimeAlloted(String timeAlloted) {
		this.timeAlloted = timeAlloted;
	}
	public ArrayList<ArrayList<String>> getStayAlertListOflists() {
		return stayAlertListOflists;
	}
	public void setStayAlertListOflists(
			ArrayList<ArrayList<String>> stayAlertListOflists) {
		this.stayAlertListOflists = stayAlertListOflists;
	}
	public List<Object> getDateoneweek() {
		return dateoneweek;
	}
	public void setDateoneweek(List<Object> dateoneweek) {
		this.dateoneweek = dateoneweek;
	}
	public ArrayList<String> getDatesevenlist() {
		return datesevenlist;
	}
	public void setDatesevenlist(ArrayList<String> datesevenlist) {
		this.datesevenlist = datesevenlist;
	}
	public ArrayList<ArrayList<String>> getSevendayslistoflist() {
		return sevendayslistoflist;
	}
	public void setSevendayslistoflist(
			ArrayList<ArrayList<String>> sevendayslistoflist) {
		this.sevendayslistoflist = sevendayslistoflist;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<GridDataPropertyView> getDashboardVisitorDetail() {
		return dashboardVisitorDetail;
	}

	public void setDashboardVisitorDetail(
			List<GridDataPropertyView> dashboardVisitorDetail) {
		this.dashboardVisitorDetail = dashboardVisitorDetail;
	}

	public List<Object> getDashboardvisitorData() {
		return dashboardvisitorData;
	}

	public void setDashboardvisitorData(List<Object> dashboardvisitorData) {
		this.dashboardvisitorData = dashboardvisitorData;
	}



	public TreeMap<String, String> getGraphMap() {
		return graphMap;
	}

	public void setGraphMap(TreeMap<String, String> graphMap) {
		this.graphMap = graphMap;
	}
	
}
