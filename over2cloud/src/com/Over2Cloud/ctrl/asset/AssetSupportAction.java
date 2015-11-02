package com.Over2Cloud.ctrl.asset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.compliance.ComplianceCommonOperation;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalAction;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.asset.common.CreateLogTable;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.ctrl.compliance.ConfigureComplianceHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings(
{ "serial", "unused" })
public class AssetSupportAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(AssetSupportAction.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public File uploadDoc;
	private String uploadDocContentType;
	private String uploadDocFileName;
	public File uploadDoc1;
	private String uploadDoc1ContentType;
	private String uploadDoc1FileName;
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
	// private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private HttpServletRequest request;
	Map<String, String> supportCatgColumnMap = null;
	private String moduleName;
	private String frequency;
	private String dueDate;
	private String assetId;
	private Map<Integer, String> assetTypeList;
	List<ConfigurationUtilBean> assetDropMap = null;
	Map<String,ArrayList> escMatrixMap = null;

	@SuppressWarnings(
	{ "unchecked", "static-access", "rawtypes" })
	public String configureSupport()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ComplianceUniversalAction CUA=new ComplianceUniversalAction();
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				AssetUniversalAction AUH = new AssetUniversalAction();
				String docPath1 = null, docPath2 = null;
				String renameFilePath = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_reminder", accountID, connectionSpace, "", 0, "table_name", "asset_reminder");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertDataOBJ = new ArrayList<InsertDataTable>();
					StringBuilder strID = new StringBuilder();
					InsertDataTable ob = null;
					InsertDataTable insertObj = null;
					boolean status = false, otherMailFlag = false, otherSmsFlag = false, selfMailFlag = false, selfSmsFlag = false;
					String reminFor = null;
					List fileList = new ArrayList<String>();
					TableColumes ob1;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("reminder1") && !gdp.getColomnName().equalsIgnoreCase("reminder2") && !gdp.getColomnName().equalsIgnoreCase("reminder3"))
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype("varchar(255)");
							if (gdp.getColomnName().equalsIgnoreCase("l1EscDuration") || gdp.getColomnName().equalsIgnoreCase("l2EscDuration") || gdp.getColomnName().equalsIgnoreCase("l3EscDuration")
									|| gdp.getColomnName().equalsIgnoreCase("l4EscDuration"))
							{
								ob1.setConstraint("default '00:00'");
							}
							else
							{
								ob1.setConstraint("default NULL");
							}
							Tablecolumesaaa.add(ob1);
						}
					}
					ob1 = new TableColumes();
					ob1.setColumnname("escalate_date");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("escalate_time");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("current_esc_level");
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					cbt.createTable22("asset_reminder_details", Tablecolumesaaa, connectionSpace);

					List<GridDataPropertyView> statusColName1 = Configuration
							.getConfigurationData("mapped_asset_report_config", accountID, connectionSpace, "", 0, "table_name", "asset_report_config");
					if (statusColName1 != null && statusColName1.size() > 0)
					{
						TableColumes ob2;
						List<TableColumes> Tablecolumesaaa1 = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : statusColName1)
						{
							ob2 = new TableColumes();
							ob2.setColumnname(gdp.getColomnName());
							ob2.setDatatype("varchar(255)");
							if (gdp.getColomnName().equalsIgnoreCase("costForCompMiss"))
							{
								ob2.setConstraint("default '0'");
							}
							else
							{
								ob2.setConstraint("default NULL");
							}
							Tablecolumesaaa1.add(ob2);
						}
						cbt.createTable22("asset_report", Tablecolumesaaa1, connectionSpace);
					}

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					String dueDate = null;
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);

						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("assettype") && !parmName.equalsIgnoreCase("mfgserialno")
								&& !parmName.equalsIgnoreCase("assetname11") && !parmName.equalsIgnoreCase("serialno") && !parmName.equalsIgnoreCase("assetmodel")
								&& !parmName.equalsIgnoreCase("reminder1") && !parmName.equalsIgnoreCase("reminder2") && !parmName.equalsIgnoreCase("reminder3")
								&& !parmName.equalsIgnoreCase("assetbrand") && !parmName.equalsIgnoreCase("serialno1") && !parmName.equalsIgnoreCase("assetname1")
								&& !parmName.equalsIgnoreCase("search") && !parmName.equalsIgnoreCase("__checkbox_othermail") && !parmName.equalsIgnoreCase("__checkbox_selfmail")
								&& !parmName.equalsIgnoreCase("supportfrom") && !parmName.equalsIgnoreCase("__checkbox_othersms") && !parmName.equalsIgnoreCase("__checkbox_selfsms")
								&& !parmName.equalsIgnoreCase("othermail") && !parmName.equalsIgnoreCase("selfmail") && !parmName.equalsIgnoreCase("othersms") && !parmName.equalsIgnoreCase("selfsms"))
						{
							if (parmName.equals("dueDate"))
							{
								dueDate = DateUtil.convertDateToUSFormat(paramValue);
								if (dueDate != null)
								{
									status = false;
									status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), dueDate + " " + request.getParameter("dueTime"));
									if (!status)
									{
										if (request.getParameter("frequency").equalsIgnoreCase("W"))
											dueDate = DateUtil.getNewDate("day", 7, dueDate);
										else if (request.getParameter("frequency").equalsIgnoreCase("M"))
											dueDate = DateUtil.getNewDate("month", 1, dueDate);
										else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
											dueDate = DateUtil.getNewDate("day", 15, dueDate);
										else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
											dueDate = DateUtil.getNewDate("month", 3, dueDate);
										else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
											dueDate = DateUtil.getNewDate("month", 6, dueDate);
										else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
											dueDate = DateUtil.getNewDate("year", 1, dueDate);
										else if (request.getParameter("frequency").equalsIgnoreCase("D"))
											dueDate = DateUtil.getNewDate("day", 1, dueDate);
									}
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(dueDate);
									insertData.add(ob);
								}
							}
							else if(parmName.equals("supporttype") || parmName.equals("vendorid"))
							{
								if(request.getParameter("moduleName").equalsIgnoreCase("Support"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
							}
							// REminder for
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToSelf"))
							{
								reminFor = paramValue;
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("ASTM", userName)[0];
								strID.append(userContID.concat("#"));
							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToOther"))
							{
								reminFor = paramValue;
								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
							}
							else if (parmName.equals("reminder_for") && paramValue.equalsIgnoreCase("remToBoth"))
							{
								reminFor = paramValue;
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("ASTM", userName)[0];
								String[] emplID = request.getParameterValues("emp_id");
								for (int i = 0; i < emplID.length; i++)
								{
									strID.append(emplID[i]);
									strID.append("#");
								}
								strID.append(userContID);
								strID.append("#");
							}
							else if (parmName.equals("assetId"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
							else if (parmName.equals("moduleName"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
							else if (parmName.equals("l1EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase(""))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),
										paramValue, "Y").split("#");

								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_date");
								ob.setDataName(escDateTime[0]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_time");
								ob.setDataName(escDateTime[1]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("current_esc_level");
								ob.setDataName("1");
								insertData.add(ob);
							}
							else if (parmName.equals("l2EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("")
									&& (request.getParameter("l1EscDuration") == null || request.getParameter("l1EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),
										paramValue, "Y").split("#");
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_date");
								ob.setDataName(escDateTime[0]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_time");
								ob.setDataName(escDateTime[1]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("current_esc_level");
								ob.setDataName("2");
								insertData.add(ob);
							}
							else if (parmName.equals("l3EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("")
									&& (request.getParameter("l2EscDuration") == null || request.getParameter("l2EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),
										paramValue, "Y").split("#");
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_date");
								ob.setDataName(escDateTime[0]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_time");
								ob.setDataName(escDateTime[1]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("current_esc_level");
								ob.setDataName("3");
								insertData.add(ob);
							}
							else if (parmName.equals("l4EscDuration") && paramValue != null && !paramValue.equalsIgnoreCase("")
									&& (request.getParameter("l3EscDuration") == null || request.getParameter("l3EscDuration").equalsIgnoreCase("")))
							{
								String escDateTime[] = new DateUtil().newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("dueDate")), request.getParameter("dueTime"),
										paramValue, "Y").split("#");

								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_date");
								ob.setDataName(escDateTime[0]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_time");
								ob.setDataName(escDateTime[1]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("current_esc_level");
								ob.setDataName("4");
								insertData.add(ob);
							}
							else if (parmName.equals("escalation_level_1") || parmName.equals("escalation_level_2") || parmName.equals("escalation_level_3") || parmName.equals("escalation_level_4"))
							{
								String empId[] = request.getParameterValues(parmName);
								StringBuilder strIDD = new StringBuilder();
								for (int i = 0; i < empId.length; i++)
								{
									strIDD.append(empId[i]);
									strIDD.append("#");
								}
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(strIDD.toString());
								insertData.add(ob);
							}
							else if (!parmName.equalsIgnoreCase("emp_id"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
						if (parmName.equalsIgnoreCase("selfmail"))
						{
							selfMailFlag = true;
						}
						if (parmName.equalsIgnoreCase("selfsms"))
						{
							selfSmsFlag = true;
						}
						if (parmName.equalsIgnoreCase("othermail"))
						{
							otherMailFlag = true;
						}
						if (parmName.equalsIgnoreCase("othersms"))
						{
							otherSmsFlag = true;
						}
					}
					if (otherMailFlag || selfMailFlag)
					{
						ob = new InsertDataTable();
						ob.setColName("ack_dge");
						ob.setDataName("Yes");
						insertData.add(ob);
					}
					else if (otherSmsFlag || selfSmsFlag)
					{
						ob = new InsertDataTable();
						ob.setColName("ack_dge");
						ob.setDataName("Yes");
						insertData.add(ob);
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("ack_dge");
						ob.setDataName("No");
						insertData.add(ob);
					}
					// setting the values of file.
					if (uploadDocFileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + DateUtil.mergeDateTime() + getUploadDocFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + getUploadDocFileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath != null)
						{
							File theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(uploadDoc, theFile);
									if (theFile.exists())
										theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile != null)
								{
									docPath1 = renameFilePath;
									ob = new InsertDataTable();
									ob.setColName("uploadDoc");
									ob.setDataName(renameFilePath);
									fileList.add(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					if (uploadDoc1FileName != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("Asset_Data") + "//" + DateUtil.mergeDateTime() + getUploadDoc1FileName();
						String storeFilePath1 = new CreateFolderOs().createUserDir("Asset_Data") + "//" + getUploadDoc1FileName();
						String str = renameFilePath.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(uploadDoc1, theFile1);
									if (theFile1.exists())
										theFile1.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								if (theFile1 != null)
								{
									docPath2 = renameFilePath;
									ob = new InsertDataTable();
									ob.setColName("uploadDoc1");
									ob.setDataName(renameFilePath);
									fileList.add(renameFilePath);
									insertData.add(ob);
								}
							}
						}
					}
					ob = new InsertDataTable();
					ob.setColName("actionStatus");
					ob.setDataName("Pending");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("actionTaken");
					ob.setDataName("Pending");
					insertData.add(ob);

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

					/*
					 * String deptId=getUserDeptId(userName); String
					 * frq=request.getParameter("frequency"); List
					 * complSeries=getCompIdPrefix_Suffix(deptId,frq); String
					 * prefix=null; int suffix=0; if(complSeries!=null &&
					 * complSeries.size()>0) { if(complSeries.get(0)!=null)
					 * prefix=complSeries.get(0).toString();
					 * 
					 * if(complSeries.get(1)!=null)
					 * suffix=Integer.valueOf(complSeries.get(1).toString()); }
					 * 
					 * ob=new InsertDataTable(); ob.setColName("compid_prefix");
					 * ob.setDataName(prefix); insertData.add(ob);
					 */

					if (request.getParameter("action_type") != null && request.getParameter("action_type").equalsIgnoreCase("individual"))
					{

						if (strID != null && !strID.toString().equalsIgnoreCase(""))
						{
							String[] empIds = strID.toString().split("#");
							if (empIds != null && empIds.length > 0)
							{
								for (int count = 0; count < empIds.length; count++)
								{
									/*
									 * String
									 * complianceId=prefix+String.valueOf(
									 * suffix+count); ob=new InsertDataTable();
									 * ob.setColName("compid_suffix");
									 * ob.setDataName(suffix+count);
									 * insertData.add(ob);
									 */

									ob = new InsertDataTable();
									ob.setColName("reminder_for");
									ob.setDataName(empIds[count] + "#");
									insertData.add(ob);

									status = cbt.insertIntoTable("asset_reminder_details", insertData, connectionSpace);

									insertData.remove(insertData.size() - 1);
									insertData.remove(insertData.size() - 1);

									int maxAssetReminderID = AUH.getMaximumAssetReminderId();

									if (otherMailFlag || selfMailFlag)
									{
										if (maxAssetReminderID > 0)
										{
											String user = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));
											String userContID = null;
											userContID = CUA.getEmpDetailsByUserName("ASTM", userName)[0];
											// fileList is used for excel name
											// list
											mailSendAsset(maxAssetReminderID, reminFor, userContID, connectionSpace, selfMailFlag, otherMailFlag);
										}
									}
									if (otherSmsFlag || selfSmsFlag)
									{
										if (maxAssetReminderID > 0)
										{
											String userContID = null;
											userContID = CUA.getEmpDetailsByUserName("ASTM", userName)[0];
											smsSendAsset(maxAssetReminderID, userContID, connectionSpace, reminFor, otherSmsFlag, selfSmsFlag);
										}
									}
									// Insert Data Into Compliance Reminder
									// Table
									List<String> dateList = new ArrayList<String>();
									if (status)
									{
										List<String> dayCounterList = new ArrayList<String>();
										dateList.add(dueDate);
										dayCounterList.add("0");
										String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
										String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
										String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));
										if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
											dayCounterList.add(String.valueOf(dateDiff));
											// dateList.add(reminDate1);
											// reminDate1=DateUtil.getNewDate(
											// "day"
											// ,-Integer.parseInt(reminDate1
											// ),dueDate);

											status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												if (request.getParameter("frequency").equalsIgnoreCase("W"))
													dateList.add(DateUtil.getNewDate("day", 7, reminDate1));
												else if (request.getParameter("frequency").equalsIgnoreCase("M"))
													dateList.add(DateUtil.getNewDate("month", 1, reminDate1));
												else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
													dateList.add(DateUtil.getNewDate("day", 15, reminDate1));
												else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
													dateList.add(DateUtil.getNewDate("month", 3, reminDate1));
												else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
													dateList.add(DateUtil.getNewDate("month", 6, reminDate1));
												else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
													dateList.add(DateUtil.getNewDate("year", 1, reminDate1));
											}
											else
											{
												dateList.add(reminDate1);
											}
										}
										if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
											dayCounterList.add(String.valueOf(dateDiff));
											// dateList.add(reminDate2);
											// reminDate2=DateUtil.getNewDate(
											// "day"
											// ,-Integer.parseInt(reminDate2
											// ),dueDate);
											status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												if (request.getParameter("frequency").equalsIgnoreCase("W"))
													dateList.add(DateUtil.getNewDate("day", 7, reminDate2));
												else if (request.getParameter("frequency").equalsIgnoreCase("M"))
													dateList.add(DateUtil.getNewDate("month", 1, reminDate2));
												else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
													dateList.add(DateUtil.getNewDate("day", 15, reminDate2));
												else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
													dateList.add(DateUtil.getNewDate("month", 3, reminDate2));
												else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
													dateList.add(DateUtil.getNewDate("month", 6, reminDate2));
												else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
													dateList.add(DateUtil.getNewDate("year", 1, reminDate2));
											}
											else
											{
												dateList.add(reminDate2);
											}
										}
										if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
										{
											status = false;
											int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
											dayCounterList.add(String.valueOf(dateDiff));
											// dateList.add(reminDate3);
											// reminDate3=DateUtil.getNewDate(
											// "day"
											// ,-Integer.parseInt(reminDate3
											// ),dueDate);
											status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
											if (status)
											{
												if (request.getParameter("frequency").equalsIgnoreCase("W"))
													dateList.add(DateUtil.getNewDate("day", 7, reminDate3));
												else if (request.getParameter("frequency").equalsIgnoreCase("M"))
													dateList.add(DateUtil.getNewDate("month", 1, reminDate3));
												else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
													dateList.add(DateUtil.getNewDate("day", 15, reminDate3));
												else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
													dateList.add(DateUtil.getNewDate("month", 3, reminDate3));
												else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
													dateList.add(DateUtil.getNewDate("month", 6, reminDate3));
												else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
													dateList.add(DateUtil.getNewDate("year", 1, reminDate3));
											}
											else
											{
												dateList.add(reminDate3);
											}
										}
										if (dateList != null && dateList.size() > 0)
										{
											for (int i = 0; i < dateList.size(); i++)
											{
												Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
												dataWithColumnName.put("due_date", dueDate);
												dataWithColumnName.put("due_time", request.getParameter("dueTime"));
												dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
												dataWithColumnName.put("reminder_status", "0");
												dataWithColumnName.put("status", "Pending");
												dataWithColumnName.put("asseReminder_id", String.valueOf(maxAssetReminderID));
												if (i == 0)
												{
													dataWithColumnName.put("reminder_name", "Due Reminder");
													dataWithColumnName.put("reminder_code", "D");
													dataWithColumnName.put("remind_date", dueDate);
												}
												else
												{
													dataWithColumnName.put("reminder_name", "Reminder " + i);
													dataWithColumnName.put("reminder_code", "R");
													dataWithColumnName.put("remind_date", dateList.get(i));
													// dataWithColumnName.put(
													// "remind_interval",
													// request
													// .getParameter("reminder"
													// +i));
													dataWithColumnName.put("remind_interval", dayCounterList.get(i));
												}
												AUH.saveAssetReminder(dataWithColumnName);
											}
										}
									}
									insertDataOBJ = new ArrayList<InsertDataTable>();
									if (dateList != null && dateList.size() > 1)
									{
										for (int i = 1; i < dateList.size(); i++)
										{
											insertObj = new InsertDataTable();
											insertObj.setColName("reminder" + i);
											insertObj.setDataName(dateList.get(i).toString());
											insertDataOBJ.add(insertObj);
										}
									}
									/*
									 * insertObj=new InsertDataTable();
									 * insertObj.setColName("complainceId");
									 * insertObj.setDataName(complianceId);
									 * insertDataOBJ.add(insertObj);
									 */

									insertObj = new InsertDataTable();
									insertObj.setColName("dueDate");
									insertObj.setDataName(dueDate);
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("dueTime");
									insertObj.setDataName(request.getParameter("dueTime"));
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("userName");
									insertObj.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("actionTaken");
									insertObj.setDataName("Pending");
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("assetReminderID");
									insertObj.setDataName(maxAssetReminderID);
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("document_config_1");
									insertObj.setDataName(docPath1);
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("document_config_2");
									insertObj.setDataName(docPath2);
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("actionTakenDate");
									insertObj.setDataName(DateUtil.getCurrentDateUSFormat());
									insertDataOBJ.add(insertObj);

									insertObj = new InsertDataTable();
									insertObj.setColName("actionTakenTime");
									insertObj.setDataName(DateUtil.getCurrentTimeHourMin());
									insertDataOBJ.add(insertObj);

									if (request.getParameter("escalation").equalsIgnoreCase("Y"))
									{
										insertObj = new InsertDataTable();
										insertObj.setColName("current_esc_level");
										insertObj.setDataName("1");
										insertDataOBJ.add(insertObj);
									}
									cbt.insertIntoTable("asset_report", insertDataOBJ, connectionSpace);
								}
							}
							addActionMessage("Asset Support Added Successfully !!!");
							returnResult = SUCCESS;
						}
						else
						{
							addActionMessage("OOPs There is some error in employee !!!");
							returnResult = ERROR;
						}
					}
					else
					{
						// ob=new InsertDataTable();
						// ob.setColName("compid_suffix");
						// ob.setDataName(suffix);
						// insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("reminder_for");
						ob.setDataName(strID.toString());
						insertData.add(ob);

						status = cbt.insertIntoTable("asset_reminder_details", insertData, connectionSpace);
						int maxAssetReminderID = AUH.getMaximumAssetReminderId();

						if (otherMailFlag || selfMailFlag)
						{
							if (maxAssetReminderID > 0)
							{
								String user = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));
								/*String userEmpID = null;
								List loggedUserData = new AssetUniversalAction().getEmpDataByUserName(user, deptLevel, "ASTM");
								if (loggedUserData != null && loggedUserData.size() > 0)
								{
									Object[] object = null;
									for (Iterator iterator = loggedUserData.iterator(); iterator.hasNext();)
									{
										object = (Object[]) iterator.next();
										if (deptLevel.equalsIgnoreCase("2"))
										{
											userEmpID = object[9].toString();
										}
										else if (deptLevel.equalsIgnoreCase("1"))
										{
											userEmpID = object[7].toString();
										}
									}
								}*/
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("ASTM", userName)[0];
								// fileList is used for excel name list
								mailSendAsset(maxAssetReminderID, reminFor, userContID, connectionSpace, selfMailFlag, otherMailFlag);
							}
						}
						if (otherSmsFlag || selfSmsFlag)
						{
							if (maxAssetReminderID > 0)
							{
								String user = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));
								String userContID = null;
								userContID = CUA.getEmpDetailsByUserName("ASTM", userName)[0];
								smsSendAsset(maxAssetReminderID, userContID, connectionSpace, reminFor, otherSmsFlag, selfSmsFlag);
							}
						}
						// Insert Data Into Compliance Reminder Table
						List<String> dateList = new ArrayList<String>();
						if (status)
						{
							List<String> dayCounterList = new ArrayList<String>();
							dateList.add(dueDate);
							dayCounterList.add("0");
							String reminDate1 = DateUtil.convertDateToUSFormat(request.getParameter("reminder1"));
							String reminDate2 = DateUtil.convertDateToUSFormat(request.getParameter("reminder2"));
							String reminDate3 = DateUtil.convertDateToUSFormat(request.getParameter("reminder3"));

							if (reminDate1 != null && !reminDate1.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate1);
								dayCounterList.add(String.valueOf(dateDiff));
								// dateList.add(reminDate1);
								//reminDate1=DateUtil.getNewDate("day",-Integer.
								// parseInt(reminDate1),dueDate);
								status = DateUtil.comparetwoDates(reminDate1, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									if (request.getParameter("frequency").equalsIgnoreCase("W"))
										dateList.add(DateUtil.getNewDate("day", 7, reminDate1));
									else if (request.getParameter("frequency").equalsIgnoreCase("M"))
										dateList.add(DateUtil.getNewDate("month", 1, reminDate1));
									else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
										dateList.add(DateUtil.getNewDate("day", 15, reminDate1));
									else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
										dateList.add(DateUtil.getNewDate("month", 3, reminDate1));
									else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
										dateList.add(DateUtil.getNewDate("month", 6, reminDate1));
									else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
										dateList.add(DateUtil.getNewDate("year", 1, reminDate1));
								}
								else
								{
									dateList.add(reminDate1);
								}
							}
							if (reminDate2 != null && !reminDate2.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate2);
								dayCounterList.add(String.valueOf(dateDiff));
								// dateList.add(reminDate2);
								//reminDate2=DateUtil.getNewDate("day",-Integer.
								// parseInt(reminDate2),dueDate);
								status = DateUtil.comparetwoDates(reminDate2, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									if (request.getParameter("frequency").equalsIgnoreCase("W"))
										dateList.add(DateUtil.getNewDate("day", 7, reminDate2));
									else if (request.getParameter("frequency").equalsIgnoreCase("M"))
										dateList.add(DateUtil.getNewDate("month", 1, reminDate2));
									else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
										dateList.add(DateUtil.getNewDate("day", 15, reminDate2));
									else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
										dateList.add(DateUtil.getNewDate("month", 3, reminDate2));
									else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
										dateList.add(DateUtil.getNewDate("month", 6, reminDate2));
									else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
										dateList.add(DateUtil.getNewDate("year", 1, reminDate2));
								}
								else
								{
									dateList.add(reminDate2);
								}
							}
							if (reminDate3 != null && !reminDate3.equalsIgnoreCase("-1"))
							{
								status = false;
								int dateDiff = DateUtil.getNoOfDays(dueDate, reminDate3);
								dayCounterList.add(String.valueOf(dateDiff));
								// dateList.add(reminDate3);
								//reminDate3=DateUtil.getNewDate("day",-Integer.
								// parseInt(reminDate3),dueDate);
								status = DateUtil.comparetwoDates(reminDate3, DateUtil.getCurrentDateUSFormat());
								if (status)
								{
									if (request.getParameter("frequency").equalsIgnoreCase("W"))
										dateList.add(DateUtil.getNewDate("day", 7, reminDate3));
									else if (request.getParameter("frequency").equalsIgnoreCase("M"))
										dateList.add(DateUtil.getNewDate("month", 1, reminDate3));
									else if (request.getParameter("frequency").equalsIgnoreCase("BM"))
										dateList.add(DateUtil.getNewDate("day", 15, reminDate3));
									else if (request.getParameter("frequency").equalsIgnoreCase("Q"))
										dateList.add(DateUtil.getNewDate("month", 3, reminDate3));
									else if (request.getParameter("frequency").equalsIgnoreCase("HY"))
										dateList.add(DateUtil.getNewDate("month", 6, reminDate3));
									else if (request.getParameter("frequency").equalsIgnoreCase("Y"))
										dateList.add(DateUtil.getNewDate("year", 1, reminDate3));
								}
								else
								{
									dateList.add(reminDate3);
								}
							}
							if (dateList != null && dateList.size() > 0)
							{
								for (int i = 0; i < dateList.size(); i++)
								{
									Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
									dataWithColumnName.put("due_date", dueDate);
									dataWithColumnName.put("due_time", request.getParameter("dueTime"));
									dataWithColumnName.put("remind_time", request.getParameter("dueTime"));
									dataWithColumnName.put("reminder_status", "0");
									dataWithColumnName.put("status", "Pending");
									dataWithColumnName.put("asseReminder_id", String.valueOf(maxAssetReminderID));
									if (i == 0)
									{
										dataWithColumnName.put("reminder_name", "Due Reminder");
										dataWithColumnName.put("reminder_code", "D");
										dataWithColumnName.put("remind_date", dueDate);
									}
									else
									{
										dataWithColumnName.put("reminder_name", "Reminder " + i);
										dataWithColumnName.put("reminder_code", "R");
										dataWithColumnName.put("remind_date", dateList.get(i));
										// dataWithColumnName.put(
										// "remind_interval",
										// request.getParameter("reminder"+i));
										dataWithColumnName.put("remind_interval", dayCounterList.get(i));
									}
									AUH.saveAssetReminder(dataWithColumnName);
								}
							}
						}
						insertData = new ArrayList<InsertDataTable>();
						if (dateList != null && dateList.size() > 1)
						{
							for (int i = 1; i < dateList.size(); i++)
							{
								insertObj = new InsertDataTable();
								insertObj.setColName("reminder" + i);
								insertObj.setDataName(dateList.get(i).toString());
								insertData.add(insertObj);
							}
						}
						/*
						 * insertObj=new InsertDataTable();
						 * insertObj.setColName("complainceId");
						 * insertObj.setDataName(prefix+String.valueOf(suffix));
						 * insertData.add(insertObj);
						 */

						insertObj = new InsertDataTable();
						insertObj.setColName("dueDate");
						insertObj.setDataName(dueDate);
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("dueTime");
						insertObj.setDataName(request.getParameter("dueTime"));
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("userName");
						insertObj.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("actionTaken");
						insertObj.setDataName("Pending");
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("assetReminderID");
						insertObj.setDataName(maxAssetReminderID);
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("document_config_1");
						insertObj.setDataName(docPath1);
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("document_config_2");
						insertObj.setDataName(docPath2);
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("actionTakenDate");
						insertObj.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(insertObj);

						insertObj = new InsertDataTable();
						insertObj.setColName("actionTakenTime");
						insertObj.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(insertObj);

						if (request.getParameter("escalation").equalsIgnoreCase("Y"))
						{
							insertObj = new InsertDataTable();
							insertObj.setColName("current_esc_level");
							insertObj.setDataName("1");
							insertData.add(insertObj);
						}
						cbt.insertIntoTable("asset_report", insertData, connectionSpace);
						addActionMessage("Asset Support Added Successfully !!!");
						returnResult = SUCCESS;
					}
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method configureCompliance of class " + getClass(),
						exp);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	/*public void getColumn4View(String mappingTable, String masterTable)
	{
		System.out.println("inide method  ");
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("actionStatus");
		gpv.setHeadingName("Status");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("true");
		gpv.setWidth(150);
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappingTable, accountID, connectionSpace, "", 0, "table_name", masterTable);
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				if (gdp.getColomnName().equals("assetid"))
				{
					List<GridDataPropertyView> statusColName1 = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
					if (statusColName1 != null && statusColName1.size() > 0)
					{
						for (GridDataPropertyView gdp1 : statusColName1)
						{
							if (gdp1.getColomnName().equalsIgnoreCase("assetname") || gdp1.getColomnName().equalsIgnoreCase("serialno") || gdp1.getColomnName().equalsIgnoreCase("assetbrand")
									|| gdp1.getColomnName().equalsIgnoreCase("assetmodel") || gdp1.getColomnName().equalsIgnoreCase("supportfrom")
									|| gdp1.getColomnName().equalsIgnoreCase("receivedOn") || gdp1.getColomnName().equalsIgnoreCase("commssioningon")
									|| gdp1.getColomnName().equalsIgnoreCase("installon"))
							{
								gpv = new GridDataPropertyView();
								gpv.setColomnName(gdp1.getColomnName());
								gpv.setHeadingName(gdp1.getHeadingName());
								gpv.setEditable(gdp1.getEditable());
								gpv.setSearch(gdp1.getSearch());

								if ((moduleName != null && moduleName.equalsIgnoreCase("Preventive") && gdp1.getColomnName().toString().equalsIgnoreCase("supportfrom"))
										|| gdp1.getColomnName().equalsIgnoreCase("receivedOn") || gdp1.getColomnName().equalsIgnoreCase("commssioningon")
										|| gdp1.getColomnName().equalsIgnoreCase("installon"))
								{
									gpv.setHideOrShow("true");
								}
								else
								{
									gpv.setHideOrShow(gdp1.getHideOrShow());
								}
								gpv.setWidth(gdp1.getWidth());
								masterViewProp.add(gpv);
							}
						}
					}
				}
				else if (!gdp.getColomnName().equals("assetid") && !gdp.getColomnName().equals("actionStatus"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					if (gdp.getColomnName().equals("dueDate") || gdp.getColomnName().equals("snooze_date"))
					{
						gpv.setHeadingName(gdp.getHeadingName() + " & Time");
					}
					else
					{
						gpv.setHeadingName(gdp.getHeadingName());
					}
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
		}
		System.out.println("Master View List is " + masterViewProp.size());
	}*/
	
	
	public void getColumn4View(String mappingTable, String masterTable)
	{
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("actionStatus");
		gpv.setHeadingName("Status");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setFormatter("true");
		gpv.setWidth(150);
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData(mappingTable, accountID, connectionSpace, "", 0, "table_name", masterTable);
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				if (gdp.getColomnName().equals("assetid"))
				{
					List<GridDataPropertyView> statusColName1 = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
					if (statusColName1 != null && statusColName1.size() > 0)
					{
						for (GridDataPropertyView gdp1 : statusColName1)
						{
							if (gdp1.getColomnName().equalsIgnoreCase("assetname") )
							{
								gpv = new GridDataPropertyView();
								gpv.setColomnName(gdp1.getColomnName());
								gpv.setHeadingName(gdp1.getHeadingName());
								gpv.setEditable(gdp1.getEditable());
								gpv.setSearch(gdp1.getSearch());

								if ((moduleName != null && moduleName.equalsIgnoreCase("Preventive") && gdp1.getColomnName().toString().equalsIgnoreCase("supportfrom"))
										|| gdp1.getColomnName().equalsIgnoreCase("receivedOn") || gdp1.getColomnName().equalsIgnoreCase("commssioningon")
										|| gdp1.getColomnName().equalsIgnoreCase("installon"))
								{
									gpv.setHideOrShow("true");
								}
								else
								{
									gpv.setHideOrShow(gdp1.getHideOrShow());
								}
								gpv.setWidth(gdp1.getWidth());
								masterViewProp.add(gpv);
							}
						}
					}
				}
				else if (!gdp.getColomnName().equals("supporttype") && !gdp.getColomnName().equals("vendorid") && !gdp.getColomnName().equals("assetid") && !gdp.getColomnName().equals("actionStatus") && !gdp.getColomnName().equals("escalation_level_1") && !gdp.getColomnName().equals("escalation_level_2") && !gdp.getColomnName().equals("escalation_level_3") && !gdp.getColomnName().equals("escalation_level_4"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					if (gdp.getColomnName().equals("dueDate") || gdp.getColomnName().equals("snooze_date"))
					{
						gpv.setHeadingName(gdp.getHeadingName() + " & Time");
					}
					else
					{
						gpv.setHeadingName(gdp.getHeadingName());
					}
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
				else if((gdp.getColomnName().equals("supporttype") || gdp.getColomnName().equals("vendorid")) && moduleName.equalsIgnoreCase("Support") )
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
		}
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("escMatix");
		gpv.setHeadingName("Escalation Matrix");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setFormatter("testAbc");
		gpv.setWidth(2000);
		masterViewProp.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("download");
		gpv.setHeadingName("Document");
		gpv.setEditable("false");
		gpv.setSearch("false");
		gpv.setHideOrShow("false");
		gpv.setFormatter("downloadDoc");
		gpv.setWidth(2000);
		masterViewProp.add(gpv);
		
		System.out.println("Master View List is " + masterViewProp.size());
	}
	

	public String assetActionGridCol()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getColumn4View("mapped_asset_reminder", "asset_reminder");
				AssetUniversalAction AUA = new AssetUniversalAction();
				assetDropMap = new ArrayList<ConfigurationUtilBean>();
				assetTypeList = new LinkedHashMap<Integer, String>();

				assetDropMap = AUA.getAssetConfig();
				assetTypeList = AUA.assetTypeList();
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method assetActionGridCol of class " + getClass(),
						e);
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String viewSupportData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_reminder_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					StringBuilder columnName = new StringBuilder("");
					List fieldNames = Configuration.getColomnList("mapped_asset_reminder", accountID, connectionSpace, "asset_reminder");
					List finalFieldName = new ArrayList();
					int i = 0;
					int j = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						Object obdata = null;
						query.append("SELECT ");
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if (obdata.toString().equalsIgnoreCase("vendorid") && moduleName.equalsIgnoreCase("Support"))
									{
										columnName.append("vendor.vendorname,");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else if (obdata.toString().equalsIgnoreCase("assetid"))
									{
										List fieldNames1 = Configuration.getColomnList("mapped_asset_master", accountID, connectionSpace, "asset_master");
										if (fieldNames1 != null && fieldNames1.size() > 0)
										{
											Object obj = null;
											for (Iterator iterator = fieldNames1.iterator(); iterator.hasNext();)
											{
												obj = (Object) iterator.next();
												if (obj != null)
												{
													if (j < fieldNames1.size() - 1)
													{
														if (obj.toString().equalsIgnoreCase("assetname") || obj.toString().equalsIgnoreCase("serialno")
																|| obj.toString().equalsIgnoreCase("assetbrand") || obj.toString().equalsIgnoreCase("assetmodel")
																|| obj.toString().equalsIgnoreCase("supportfrom") || obj.toString().equalsIgnoreCase("commssioningon")
																|| obj.toString().equalsIgnoreCase("receivedOn") || obj.toString().equalsIgnoreCase("installon"))
														{
															columnName.append("asd." + obj.toString() + ",");
															finalFieldName.add(obj.toString());
															i++;
														}
													}
												}
											}
										}
									}
									else if (obdata.toString().equalsIgnoreCase("supporttype") && moduleName.equalsIgnoreCase("Support"))
									{
										columnName.append("suprtcatg.category,");
										finalFieldName.add("supporttype");
										i++;
									}
									else if (!obdata.toString().equalsIgnoreCase("assetid") && !obdata.toString().equalsIgnoreCase("reminder1") && !obdata.toString().equalsIgnoreCase("reminder2")
											&& !obdata.toString().equalsIgnoreCase("reminder3") && !obdata.toString().equals("escalation_level_1") && !obdata.toString().equals("escalation_level_2") && !obdata.toString().equals("escalation_level_3") && !obdata.toString().equals("escalation_level_4"))
									{
										columnName.append("suprt." + obdata.toString() + ",");
										finalFieldName.add(obdata.toString());
										i++;
									}
								}
							}
						}
					}
					query.append(columnName.substring(0, columnName.toString().length() - 1));
					System.out.println("queryVVVVVVVV ::::" + query);
					query.append(" FROM asset_reminder_details AS suprt");
					query.append(" LEFT JOIN createvendor_master AS vendor ON vendor.id=suprt.vendorid");
					query.append(" LEFT JOIN createasset_support_catg_master AS suprtcatg ON suprt.supporttype=suprtcatg.id ");
					query.append(" INNER JOIN asset_detail AS asd ON asd.id=suprt.assetid ");
					query.append(" WHERE suprt.moduleName='" + moduleName + "' ");
					query.append(" AND suprt.actionStatus='Pending' ");
					System.out.println("query 222" + query);
					if (frequency != null && !frequency.equalsIgnoreCase("") && !frequency.equalsIgnoreCase("undefined") && !frequency.equalsIgnoreCase("-1"))
					{
						query.append(" AND suprt.frequency='" + frequency + "' ");
					}
					if (dueDate != null && !dueDate.equalsIgnoreCase("") && !dueDate.equalsIgnoreCase("undefined") && frequency != null && !frequency.equalsIgnoreCase("")
							&& !frequency.equalsIgnoreCase("undefined") && frequency.equalsIgnoreCase("-1"))
					{
						query.append(" AND suprt.dueDate='" + DateUtil.convertDateToUSFormat(dueDate) + "' ");
					}
					if (assetId != null)
					{
						query.append(" AND suprt.assetid=" + assetId);
					}

					String app = "asd.";
					// changes-------
					System.out.println(" SEARCH FIELD ==== " + getSearchField());
					System.out.println("SEARCH  STRING     " + getSearchString());
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")
							&& getSearchField().equalsIgnoreCase("Search"))
					{
						query.append(" and asd.assetname='" + getSearchString() + "' OR asd.serialno='" + getSearchString() + "' OR asd.mfgserialno='" + getSearchString() + "'");
					}
					else if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(app + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(app + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(app + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(app + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(app + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + app + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + app + getSidx() + " DESC");
						}
					}
					else
					{
						query.append(" order by asd.assetname");
					}

					System.out.println("Query String " + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Object[] obdata = null;
						Map<String, Object> tempMap = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							for (int k = 0; k < finalFieldName.size(); k++)
							{
								if (obdata[k] != null && !obdata[k].equals(""))
								{
									if (finalFieldName.get(k).toString().equalsIgnoreCase("id"))
									{
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										String reminQry = "SELECT reminder_name,remind_date from asset_reminder_data WHERE asseReminder_id IN(" + obdata[k].toString() + ")";
										System.out.println("reminQry>>>>>>>>" + reminQry);
										List remData = cbt.executeAllSelectQuery(reminQry, connectionSpace);
										if (remData != null && remData.size() > 0)
										{
											boolean rem1 = false, rem2 = false, rem3 = false;
											for (Iterator iterator = remData.iterator(); iterator.hasNext();)
											{
												Object[] object = (Object[]) iterator.next();
												if (object[1] != null && object[0] != null)
												{
													if (object[0].toString().equalsIgnoreCase("Reminder 1"))
													{
														tempMap.put("reminder1", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem1 = true;
													}
													else if (object[0].toString().equalsIgnoreCase("Reminder 2"))
													{
														tempMap.put("reminder2", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem2 = true;
													}
													else if (object[0].toString().equalsIgnoreCase("Reminder 3"))
													{
														tempMap.put("reminder3", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem3 = true;
													}
													if (!rem1)
													{
														tempMap.put("reminder1", "NA");
													}
													if (!rem2)
													{
														tempMap.put("reminder2", "NA");
													}
													if (!rem3)
													{
														tempMap.put("reminder3", "NA");
													}
												}
											}
										}
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("dueDate") || finalFieldName.get(k).toString().equalsIgnoreCase("snooze_date"))
									{
										tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + " " + obdata[k + 1].toString());
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("supportfrom"))
									{
										if (obdata[k].toString().equalsIgnoreCase("receivedOn"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 3].toString()));
										}
										else if (obdata[k].toString().equalsIgnoreCase("installOn"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 2].toString()));
										}
										else if (obdata[k].toString().equalsIgnoreCase("commssioningon"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 1].toString()));
										}
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("reminder_for") || finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_1")
											|| finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_2") || finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_3")
											|| finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_4"))
									{
										StringBuilder empName = new StringBuilder();
										String empId = obdata[k].toString().replace("#", ",").substring(0, (obdata[k].toString().length() - 1));
										String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
										List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
										for (Iterator iterator = data1.iterator(); iterator.hasNext();)
										{
											Object object = (Object) iterator.next();
											empName.append(object.toString() + ", ");
										}
										if (empName != null && !empName.toString().equalsIgnoreCase(""))
											tempMap.put(finalFieldName.get(k).toString(), empName.toString().substring(0, empName.toString().length() - 2));
										else
											tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("action_type"))
									{
										tempMap.put(finalFieldName.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("frequency"))
									{
										tempMap.put(finalFieldName.get(k).toString(), new AssetUniversalHelper().getFrequencyName(obdata[k].toString()));
									}
									else if(finalFieldName.get(k).toString().equalsIgnoreCase("uploadDoc") || finalFieldName.get(k).toString().equalsIgnoreCase("uploadDoc1"))
									{
										String str=obdata[k].toString().substring(obdata[k].toString().lastIndexOf("//")+2, obdata[k].toString().length());
										String docName=str.substring(14,str.length());
										tempMap.put(finalFieldName.get(k).toString(),docName);
									}
									else if (obdata[k] == null)
									{
										tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
									else
									{
										tempMap.put(finalFieldName.get(k).toString(), obdata[k].toString());
									}
								}
								/*else
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}*/
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSupport of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	
	/*@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewSupportData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_reminder_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					StringBuilder query = new StringBuilder("");
					StringBuilder columnName = new StringBuilder("");
					List fieldNames = Configuration.getColomnList("mapped_asset_reminder", accountID, connectionSpace, "asset_reminder");
					List finalFieldName = new ArrayList();
					int i = 0;
					int j = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						Object obdata = null;
						query.append("SELECT ");
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if (obdata.toString().equalsIgnoreCase("vendorid"))
									{
										columnName.append("vendor.vendorname,");
										finalFieldName.add(obdata.toString());
										i++;
									}
									else if (obdata.toString().equalsIgnoreCase("assetid"))
									{
										List fieldNames1 = Configuration.getColomnList("mapped_asset_master", accountID, connectionSpace, "asset_master");
										if (fieldNames1 != null && fieldNames1.size() > 0)
										{
											Object obj = null;
											for (Iterator iterator = fieldNames1.iterator(); iterator.hasNext();)
											{
												obj = (Object) iterator.next();
												if (obj != null)
												{
													if (j < fieldNames1.size() - 1)
													{
														if (obj.toString().equalsIgnoreCase("assetname") || obj.toString().equalsIgnoreCase("serialno")
																|| obj.toString().equalsIgnoreCase("assetbrand") || obj.toString().equalsIgnoreCase("assetmodel")
																|| obj.toString().equalsIgnoreCase("supportfrom") || obj.toString().equalsIgnoreCase("commssioningon")
																|| obj.toString().equalsIgnoreCase("receivedOn") || obj.toString().equalsIgnoreCase("installon"))
														{
															columnName.append("asd." + obj.toString() + ",");
															finalFieldName.add(obj.toString());
															i++;
														}
													}
												}
											}
										}
									}
									else if (obdata.toString().equalsIgnoreCase("supporttype"))
									{
										columnName.append("suprtcatg.category,");
										finalFieldName.add("supporttype");
										i++;
									}
									else if (!obdata.toString().equalsIgnoreCase("assetid") && !obdata.toString().equalsIgnoreCase("reminder1") && !obdata.toString().equalsIgnoreCase("reminder2")
											&& !obdata.toString().equalsIgnoreCase("reminder3"))
									{
										columnName.append("suprt." + obdata.toString() + ",");
										finalFieldName.add(obdata.toString());
										i++;
									}
								}
							}
						}
					}
					query.append(columnName.substring(0, columnName.toString().length() - 1));
					System.out.println("queryVVVVVVVV ::::" + query);
					query.append(" FROM asset_reminder_details AS suprt");
					query.append(" INNER JOIN createvendor_master AS vendor ON vendor.id=suprt.vendorid");
					query.append(" INNER JOIN createasset_support_catg_master AS suprtcatg ON suprt.supporttype=suprtcatg.id ");
					query.append(" INNER JOIN asset_detail AS asd ON asd.id=suprt.assetid ");
					query.append(" WHERE suprt.moduleName='" + moduleName + "' ");
					query.append(" AND suprt.actionStatus='Pending' ");
					System.out.println("query 222" + query);
					if (frequency != null && !frequency.equalsIgnoreCase("") && !frequency.equalsIgnoreCase("undefined") && !frequency.equalsIgnoreCase("-1"))
					{
						query.append(" AND suprt.frequency='" + frequency + "' ");
					}
					if (dueDate != null && !dueDate.equalsIgnoreCase("") && !dueDate.equalsIgnoreCase("undefined") && frequency != null && !frequency.equalsIgnoreCase("")
							&& !frequency.equalsIgnoreCase("undefined") && frequency.equalsIgnoreCase("-1"))
					{
						query.append(" AND suprt.dueDate='" + DateUtil.convertDateToUSFormat(dueDate) + "' ");
					}
					if (assetId != null)
					{
						query.append(" AND suprt.assetid=" + assetId);
					}

					String app = "asd.";
					// changes-------
					System.out.println(" SEARCH FIELD ==== " + getSearchField());
					System.out.println("SEARCH  STRING     " + getSearchString());
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")
							&& getSearchField().equalsIgnoreCase("Search"))
					{
						query.append(" and asd.assetname='" + getSearchString() + "' OR asd.serialno='" + getSearchString() + "' OR asd.mfgserialno='" + getSearchString() + "'");
					}
					else if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" and ");
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(app + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(app + getSearchField() + " like '%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(app + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(app + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(app + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + app + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + app + getSidx() + " DESC");
						}
					}
					else
					{
						query.append(" order by asd.assetname");
					}

					System.out.println("Query String " + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						Object[] obdata = null;
						Map<String, Object> tempMap = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata = (Object[]) it.next();
							tempMap = new HashMap<String, Object>();
							for (int k = 0; k < finalFieldName.size(); k++)
							{
								if (obdata[k] != null && !obdata[k].equals(""))
								{
									if (finalFieldName.get(k).toString().equalsIgnoreCase("id"))
									{
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										String reminQry = "SELECT reminder_name,remind_date from asset_reminder_data WHERE asseReminder_id IN(" + obdata[k].toString() + ")";
										System.out.println("reminQry>>>>>>>>" + reminQry);
										List remData = cbt.executeAllSelectQuery(reminQry, connectionSpace);
										if (remData != null && remData.size() > 0)
										{
											boolean rem1 = false, rem2 = false, rem3 = false;
											for (Iterator iterator = remData.iterator(); iterator.hasNext();)
											{
												Object[] object = (Object[]) iterator.next();
												if (object[1] != null && object[0] != null)
												{
													System.out.println("@@@@@@@ "+object[0].toString());
													if (object[0].toString().equalsIgnoreCase("Reminder 1"))
													{
														System.out.println("Reminder 1 Date is "+DateUtil.convertDateToIndianFormat(object[1].toString()));
														
														
														if(object[1]!=null && !object[1].toString().equals(""))
														{
															System.out.println("Inside if Khushal");
															tempMap.put("reminder1",DateUtil.convertDateToIndianFormat(object[1].toString()));
															//rem1 = true;
														}
														else
														{
															System.out.println("Inside else Khushal");
															tempMap.put("reminder1","NA");
														}
													}
													else if (object[0].toString().equalsIgnoreCase("Reminder 2"))
													{
														tempMap.put("reminder2", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem2 = true;
													}
													else if (object[0].toString().equalsIgnoreCase("Reminder 3"))
													{
														tempMap.put("reminder3", DateUtil.convertDateToIndianFormat(object[1].toString()));
														rem3 = true;
													}
													
													System.out.println("Check rem1 value by Khushal "+rem1);
													if (!rem1)
													{
														System.out.println("Reminder 1 value "+!rem1);
														tempMap.put("reminder1", "NA");
													}
													System.out.println("Check rem2 value by Khushal "+rem2);
													if (!rem2)
													{
														System.out.println("Reminder 2 value "+!rem2);
														tempMap.put("reminder2", "NA");
													}
													System.out.println("Check rem3 value by Khushal "+rem3);
													if (!rem3)
													{
														System.out.println("Reminder 3 value "+!rem3);
														tempMap.put("reminder3", "NA");
													}
												}
											}
										}
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("dueDate") || finalFieldName.get(k).toString().equalsIgnoreCase("snooze_date"))
									{
										tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()) + " " + obdata[k + 1].toString());
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("supportfrom"))
									{
										if (obdata[k].toString().equalsIgnoreCase("receivedOn"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 3].toString()));
										}
										else if (obdata[k].toString().equalsIgnoreCase("installOn"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 2].toString()));
										}
										else if (obdata[k].toString().equalsIgnoreCase("commssioningon"))
										{
											tempMap.put(finalFieldName.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k - 1].toString()));
										}
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("reminder_for") || finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_1")
											|| finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_2") || finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_3")
											|| finalFieldName.get(k).toString().equalsIgnoreCase("escalation_level_4"))
									{
										StringBuilder empName = new StringBuilder();
										String empId = obdata[k].toString().replace("#", ",").substring(0, (obdata[k].toString().length() - 1));
										String query2 = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + empId + ")";
										List data1 = cbt.executeAllSelectQuery(query2, connectionSpace);
										for (Iterator iterator = data1.iterator(); iterator.hasNext();)
										{
											Object object = (Object) iterator.next();
											empName.append(object.toString() + ", ");
										}
										if (empName != null && !empName.toString().equalsIgnoreCase(""))
											tempMap.put(finalFieldName.get(k).toString(), empName.toString().substring(0, empName.toString().length() - 2));
										else
											tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("action_type"))
									{
										tempMap.put(finalFieldName.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
									}
									else if (finalFieldName.get(k).toString().equalsIgnoreCase("frequency"))
									{
										tempMap.put(finalFieldName.get(k).toString(), new AssetUniversalHelper().getFrequencyName(obdata[k].toString()));
									}
									else if (obdata[k] == null)
									{
										tempMap.put(finalFieldName.get(k).toString(), "NA");
									}
									else
									{
										tempMap.put(finalFieldName.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewSupport of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}*/

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String modifySupport()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					StringBuffer columnBuffer = new StringBuffer("");
					StringBuffer dataBuffer = new StringBuffer("");
					Map<String, String> logData = new LinkedHashMap<String, String>();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id")
								&& !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equalsIgnoreCase("userName"))
							{
								wherClause.put(parmName, userName);
								logData.put("userName", userName);
							}
							else if (parmName.equalsIgnoreCase("date"))
							{
								wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
								logData.put("updateon", DateUtil.getCurrentDateUSFormat());
							}
							else if (parmName.equalsIgnoreCase("time"))
							{
								wherClause.put(parmName, DateUtil.getCurrentTime());
								logData.put("updateat", DateUtil.getCurrentTime());
							}
							else if (parmName.equalsIgnoreCase("supportfrom") || parmName.equalsIgnoreCase("supportto"))
							{
								wherClause.put(parmName, DateUtil.convertDateToUSFormat(paramValue));
							}
							else
							{
								wherClause.put(parmName, paramValue);
								columnBuffer.append(parmName + "#");
								dataBuffer.append(paramValue + "#");
							}
						}

					}
					logData.put("column_name", columnBuffer.toString());
					logData.put("data", dataBuffer.toString());
					logData.put("update_row_id", getId());
					logData.put("table_name", "asset_support_detail");
					logData.put("update_type", "Modify");
					new CreateLogTable().createLogTable();
					new CreateLogTable().insertDataInLogTable(logData);

					condtnBlock.put("id", getId());
					ProcurementAction pa = new ProcurementAction();
					StringBuilder updateQuery = pa.updateQuery("asset_support_detail", wherClause, condtnBlock);
					cbt.updateTableColomn(connectionSpace, updateQuery);
				}
				else if (getOper().equalsIgnoreCase("del"))
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
					cbt.deleteAllRecordForId("asset_support_detail", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method modifySupport of class " + getClass(), e);
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
	public void smsSendAsset(int maxID, String contactId, SessionFactory connection, String reminFor, boolean otherSmsFlag, boolean selfSmsFlag)
	{
		try
		{
			CommunicationHelper cmnHelper = new CommunicationHelper();
			AssetUniversalAction AUA = new AssetUniversalAction();
			AssetUniversalHelper AUH = new AssetUniversalHelper();
			List<AssetDashboardBean> assetData = AUA.getAssetData(maxID, contactId);
			String remaindTo = null;
			String assetName = null;
			String dueDate = null;
			String escTime = null;
			String allotedBy = null;
			String mobNo = null;
			String loggedMob = null;
			String loggedName = null;
			String loggedDept = null;
			String msgBody = null;
			String empName = null;
			String otherDept = null;
			if (assetData != null && assetData.size() > 0)
			{
				for (AssetDashboardBean c : assetData)
				{
					remaindTo = c.getRemindTo();
					assetName = c.getAssetName();
					dueDate = c.getDueDate();
					escTime = c.getEscL1Duration();
				}
			}
			if (reminFor != null && reminFor.equalsIgnoreCase("remToSelf"))
			{
				if (contactId.equalsIgnoreCase(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1))))
				{
					if (selfSmsFlag)
					{
						List other = AUA.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
						for (Iterator iterator = other.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object != null)
							{
								mobNo = object[2].toString();
								if (object[5].toString().equalsIgnoreCase(contactId))
								{
									allotedBy = "You";
								}
								else
								{
									allotedBy = object[0].toString();
								}
								if (object[0] != null)
								{
									loggedName = object[0].toString();
								}
								if (object[4] != null)
								{
									loggedDept = object[4].toString();
								}
							}
							msgBody = AUH.getMsgBodyAsset(allotedBy, assetName, dueDate, escTime);
							cmnHelper.addMessage(loggedName, loggedDept, mobNo, msgBody, "", "Pending", "0", "Asset", connection);
						}
					}
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToOther"))
			{
				StringBuilder allotedTo = new StringBuilder();
				List loggedUSer = AUA.getEmployeeInfo(contactId);
				Object[] object = null;
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggedMob = object[2].toString();
						allotedBy = object[0].toString();
						if (object[4] != null)
						{
							loggedDept = object[4].toString();
						}
					}
				}
				List other = AUA.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							mobNo = object[2].toString();
							if (object[4] != null)
							{
								otherDept = object[4].toString();
							}
							allotedTo.append(empName + " ,");
							if (otherSmsFlag)
							{
								msgBody = AUH.getMsgBodyAsset(allotedBy, assetName, dueDate, escTime);
								cmnHelper.addMessage(empName, otherDept, mobNo, msgBody, "", "Pending", "0", "Asset", connection);
							}
						}
					}
				}
				if (selfSmsFlag)
				{
					msgBody = AUH.getMsgBodyAssetReg(allotedTo.toString(), assetName, dueDate, escTime);
					cmnHelper.addMessage(allotedBy, loggedDept, loggedMob, msgBody, "", "Pending", "0", "Asset", connection);
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToBoth"))
			{
				String mappedTeam = null;
				String alloteBy = null;
				boolean flag = false;
				StringBuilder allotedTo = new StringBuilder();
				Object[] object = null;
				List loggedUSer = AUA.getEmployeeInfo(contactId);
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggedMob = object[2].toString();
						allotedBy = object[0].toString();
						if (object[4] != null)
						{
							loggedDept = object[4].toString();
						}
					}
				}
				List other = AUA.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							mobNo = object[2].toString();
							if (!object[5].toString().equalsIgnoreCase(contactId))
							{
								allotedTo.append(empName + " ,");
							}
							else
							{
								flag = true;
							}
							if (object[5].toString().equalsIgnoreCase(contactId))
							{
								alloteBy = "You";
							}
							else
							{
								alloteBy = allotedBy;
							}
							if (object[4] != null)
							{
								otherDept = object[4].toString();
							}
							if (otherSmsFlag)
							{
								msgBody = AUH.getMsgBodyAsset(alloteBy, assetName, dueDate, escTime);
								cmnHelper.addMessage(empName, otherDept, mobNo, msgBody, "", "Pending", "0", "Asset", connection);
							}
						}
					}
				}
				if (allotedTo != null && allotedTo.toString().length() > 0)
				{
					mappedTeam = allotedTo.toString().substring(0, allotedTo.toString().length() - 2);
				}
				if (mappedTeam == null)
					mappedTeam = "You";
				else if (flag)
					mappedTeam = mappedTeam + " and You";

				if (selfSmsFlag)
				{
					msgBody = AUH.getMsgBodyAssetReg(mappedTeam, assetName, dueDate, escTime);
					cmnHelper.addMessage(allotedBy, loggedDept, loggedMob, msgBody, "", "Pending", "0", "Asset", connection);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method smsSendAsset of class " + getClass(), e);
		}
	}

	@SuppressWarnings(
	{ "rawtypes" })
	public void mailSendAsset(int maxId, String reminFor, String contactId, SessionFactory connection, boolean selfMailFlag, boolean otherMailFlag)
	{
		try
		{
			CommunicationHelper cmnHelper = new CommunicationHelper();
			AssetUniversalAction AUA = new AssetUniversalAction();
			AssetUniversalHelper AUH = new AssetUniversalHelper();
			String mailBody = null;
			String empName = null;
			String empEmail = null;
			String empDept = null;
			String loggeName = null;
			String loggeEmail = null;
			String loggeDept = null;
			List<AssetDashboardBean> assetData = AUA.getAssetData(maxId, contactId);
			String remaindTo = null;
			String assetName = null;
			if (assetData != null && assetData.size() > 0)
			{
				for (AssetDashboardBean c : assetData)
				{
					remaindTo = c.getRemindTo();
					assetName = c.getAssetName();
				}
			}
			Object[] object = null;
			if (reminFor != null && reminFor.equalsIgnoreCase("remToSelf"))
			{
				if (contactId.equalsIgnoreCase(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1))))
				{
					if (selfMailFlag)
					{
						List loggeddata = AUA.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
						for (Iterator iterator = loggeddata.iterator(); iterator.hasNext();)
						{
							object = (Object[]) iterator.next();
							if (object != null)
							{
								empName = object[0].toString();
								empEmail = object[1].toString();
								empDept = object[4].toString();
							}
						}
						mailBody = AUH.getMailBodyAsset(assetData, "allot", empName, reminFor, "");
						cmnHelper.addMail(empName, empDept, empEmail, "Asset Support Allotment Alert: " + assetName + "", mailBody, "", "Pending", "0", "", "Asset", connection);
					}
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToOther"))
			{
				StringBuilder allotedTo = new StringBuilder();
				List loggedUSer = AUA.getEmployeeInfo(contactId);
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggeName = object[0].toString();
						loggeEmail = object[1].toString();
						loggeDept = object[4].toString();
					}
				}
				List other = AUA.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							empEmail = object[1].toString();
							empDept = object[4].toString();
							allotedTo.append(empName + " ,");
							if (otherMailFlag)
							{
								mailBody = AUH.getMailBodyAsset(assetData, "allot", empName, reminFor, loggeName);
								cmnHelper.addMail(empName, empDept, empEmail, "Asset Support Allotment Alert: " + assetName + "", mailBody, "", "Pending", "0", "", "Asset", connection);
							}
						}
					}
				}
				if (selfMailFlag)
				{
					mailBody = AUH.getMailBodyAsset(assetData, "confi", loggeName, reminFor, allotedTo.substring(0, allotedTo.toString().length() - 1));
					cmnHelper.addMail(loggeName, loggeDept, loggeEmail, "Asset Support Registration Alert: " + assetName + "", mailBody, "", "Pending", "0", "", "Asset", connection);
				}
			}
			else if (reminFor != null && reminFor.equalsIgnoreCase("remToBoth"))
			{
				String mappedTeam = null;
				String allotedBy = null;
				boolean flag = false;
				StringBuilder allotedTo = new StringBuilder();
				List loggedUSer = AUA.getEmployeeInfo(contactId);
				for (Iterator iterator = loggedUSer.iterator(); iterator.hasNext();)
				{
					object = (Object[]) iterator.next();
					if (object != null)
					{
						loggeName = object[0].toString();
						loggeEmail = object[1].toString();
						loggeDept = object[4].toString();
					}
				}
				List other = AUA.getEmployeeInfo(remaindTo.replace("#", ",").substring(0, (remaindTo.toString().length() - 1)));
				if (other != null && other.size() > 0)
				{
					for (Iterator iterator = other.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						if (object != null)
						{
							empName = object[0].toString();
							empEmail = object[1].toString();
							empDept = object[4].toString();
							if (!object[5].toString().equalsIgnoreCase(contactId))
							{
								allotedTo.append(empName + " ,");
							}
							else
							{
								flag = true;
							}
							if (object[5].toString().equalsIgnoreCase(contactId))
							{
								allotedBy = "You";
							}
							else
							{
								allotedBy = loggeName;
							}
							if (otherMailFlag)
							{
								mailBody = AUH.getMailBodyAsset(assetData, "allot", empName, reminFor, allotedBy);
								cmnHelper.addMail(empName, empDept, empEmail, "Asset Support Allotment Alert: " + assetName + "", mailBody, "", "Pending", "0", "", "Asset", connection);
							}
						}
					}
				}
				if (allotedTo != null && allotedTo.toString().length() > 0)
				{
					mappedTeam = allotedTo.toString().substring(0, allotedTo.toString().length() - 2);
				}
				if (mappedTeam == null)
					mappedTeam = "You";
				else if (flag)
					mappedTeam = mappedTeam + " and You";

				if (selfMailFlag)
				{
					mailBody = AUH.getMailBodyAsset(assetData, "confi", loggeName, reminFor, mappedTeam);
					cmnHelper.addMail(loggeName, loggeDept, loggeEmail, "Asset Support Registration Alert: " + assetName + "", mailBody, "", "Pending", "0", "", "Asset", connection);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewEscMatrixAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String[] dataArr =null;
				List empNameList=null;
				ArrayList dataList=new ArrayList();
				ArrayList dataList1=new ArrayList();
				ArrayList dataList2=new ArrayList();
				ArrayList dataList3=new ArrayList();
				escMatrixMap=new LinkedHashMap<String, ArrayList>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query="SELECT escalation_level_1,escalation_level_2,escalation_level_3,escalation_level_4 FROM asset_reminder_details WHERE id="+id;
				List escMapDataList=cbt.executeAllSelectQuery(query, connectionSpace);
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				if(escMapDataList!=null && escMapDataList.size()>0)
				{
					dataArr= new String[4];
					Object[] obArr=(Object[]) escMapDataList.get(0);
					if(obArr[0]!=null)
						dataArr[0]=obArr[0].toString();
					
					if(obArr[1]!=null)
						dataArr[1]=obArr[1].toString();
					
					if(obArr[2]!=null)
						dataArr[2]=obArr[2].toString();
					
					if(obArr[3]!=null)
						dataArr[3]=obArr[3].toString();
				}
				if(dataArr[0]!=null)
				{
					empNameList=getEmpNameByContId(dataArr[0],cbt);
					for (Iterator iterator = empNameList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						dataList.add(object.toString());
					}
					escMatrixMap.put("escLevel1", dataList);
				}
				if(dataArr[1]!=null)
				{
					empNameList=getEmpNameByContId(dataArr[1],cbt);
					for (Iterator iterator = empNameList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						dataList1.add(object.toString());
					}
					escMatrixMap.put("escLevel2", dataList1);
				}
				if(dataArr[2]!=null)
				{
					empNameList=getEmpNameByContId(dataArr[2],cbt);
					for (Iterator iterator = empNameList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						dataList2.add(object.toString());
					}
					escMatrixMap.put("escLevel3", dataList2);
				}
				if(dataArr[3]!=null)
				{
					empNameList=getEmpNameByContId(dataArr[3],cbt);
					for (Iterator iterator = empNameList.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						dataList3.add(object.toString());
					}
					escMatrixMap.put("escLevel4", dataList3);
				}
				
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				returnResult=ERROR;
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	public List getEmpNameByContId(String contIds,CommonOperInterface cbt)
	{
		List dataList=null;
		try
		{
			String ids = contIds.replace("#", ",")+"0";
			String query = "SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN(" + ids + ")";
			System.out.println(query);
			dataList=cbt.executeAllSelectQuery(query, connectionSpace);
		}
		catch (Exception e) 
		{
		}
		return dataList;
	}
	
	
	public Map<String, String> getSupportCatgColumnMap()
	{
		return supportCatgColumnMap;
	}

	public void setSupportCatgColumnMap(Map<String, String> supportCatgColumnMap)
	{
		this.supportCatgColumnMap = supportCatgColumnMap;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
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

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public File getUploadDoc()
	{
		return uploadDoc;
	}

	public void setUploadDoc(File uploadDoc)
	{
		this.uploadDoc = uploadDoc;
	}

	public String getUploadDocContentType()
	{
		return uploadDocContentType;
	}

	public void setUploadDocContentType(String uploadDocContentType)
	{
		this.uploadDocContentType = uploadDocContentType;
	}

	public String getUploadDocFileName()
	{
		return uploadDocFileName;
	}

	public void setUploadDocFileName(String uploadDocFileName)
	{
		this.uploadDocFileName = uploadDocFileName;
	}

	public File getUploadDoc1()
	{
		return uploadDoc1;
	}

	public void setUploadDoc1(File uploadDoc1)
	{
		this.uploadDoc1 = uploadDoc1;
	}

	public String getUploadDoc1ContentType()
	{
		return uploadDoc1ContentType;
	}

	public void setUploadDoc1ContentType(String uploadDoc1ContentType)
	{
		this.uploadDoc1ContentType = uploadDoc1ContentType;
	}

	public String getUploadDoc1FileName()
	{
		return uploadDoc1FileName;
	}

	public void setUploadDoc1FileName(String uploadDoc1FileName)
	{
		this.uploadDoc1FileName = uploadDoc1FileName;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}

	public Map<Integer, String> getAssetTypeList()
	{
		return assetTypeList;
	}

	public void setAssetTypeList(Map<Integer, String> assetTypeList)
	{
		this.assetTypeList = assetTypeList;
	}

	public List<ConfigurationUtilBean> getAssetDropMap()
	{
		return assetDropMap;
	}

	public void setAssetDropMap(List<ConfigurationUtilBean> assetDropMap)
	{
		this.assetDropMap = assetDropMap;
	}

	public Map<String, ArrayList> getEscMatrixMap()
	{
		return escMatrixMap;
	}

	public void setEscMatrixMap(Map<String, ArrayList> escMatrixMap)
	{
		this.escMatrixMap = escMatrixMap;
	}

}
