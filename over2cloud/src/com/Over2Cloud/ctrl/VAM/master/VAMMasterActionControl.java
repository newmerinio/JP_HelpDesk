package com.Over2Cloud.ctrl.VAM.master;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.description.java2wsdl.bytecode.ParamNameExtractor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.VAM.visitor.MailText;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VAMMasterActionControl extends ActionSupport implements
		ServletRequestAware {

	/**
	 * Class for all masters view.
	 */
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(VAMMasterActionControl.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName = (String) session.get("uName");
	private String accountID = (String) session.get("accountid");
	private SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private String dept = null;
	private String veh_status;
	private String gatename;
	private String vpassId = null;
	private String hidesrno;
	private String modifyFlag;
	private String deleteFlag;
	private String mainHeaderName;
	private String deptNameExist;
	private String deptName;
	private String alert_to;
	private String empNameExist;
	private String countTotal;
	private String countIn;
	private String countOut;
	private String empName;
	private String param = "";
	private String purpose = null;
	private String location;
	private String vehlocation;
	private String purposeExist;
	private String visitdateExist;
	private String visit_date;
	private String visittimeExist;
	private String time_in;
	private String datevalue;
	private String timevalue;
	private String purposeNameExist;
	private String locationNameExit;
	private String previsitdateExit;
	private String previsittimeExit;
	private String visitorCompanyExist;
	private String visitorNameExist;
	private String visitorMobileExist;
	private String gateNameExist;
	private String alert_after;
	private String visited_mobile;
	private String visitormobile;
	private String visitor_company;
	private String visitor_mobile;
	private String visitor_name;
	private String visitorname;
	private String one_time_pwd;
	private String sr_number;
	private String from_date;
	private String to_date;
	private Boolean sms;
	private String visitorcompany;
	private String destinationto;
	private Boolean mail;
	private String emplocgatemap;
	private String requestedvisitorName;
	private String visitorName;
	private String visitorMob;
	private String orgname;
	private String organizationName;
	private String entry_date;
	private String entry_time;
	private String driver_name;
	private String driver_mobile;
	private String vehicle_model;
	private String vehicle_number;
	private String selectedId;
	private String no_of_bill;
	private String quantity;
	private String material_details;
	private String vh_owner_mob;
	private String vehicle_owner;
	private String vehicleEntrydateExist;
	private String vehicleEntrytimeExist;
	private boolean dropdownformaterial = false;
	private boolean dropdownforpurpose = false;
	private String vehiclestatus;
	private String vendortype;
	private String vendorTypeExist;
	private String vendorcompNameExist;
	private String vendorCompName;
	private String purpose_for;
	private String purposeaddition;
	private String alertval;
	private String locname;
	private String trip_no;
	private String hidetripno;
	HttpServletRequest request;
	private List<Object> vehicledatalist;
	private String vehicleExitStatus;
	private List<GridDataPropertyView> viewPurpose = new ArrayList<GridDataPropertyView>();
	Map<Integer, String> vendorcompFrontlist = new HashMap<Integer, String>();
	private List<ConfigurationUtilBean> purposedata = null;
	private List<ConfigurationUtilBean> purposeDescriptionTextBox = null;
	private List<ConfigurationUtilBean> purposeDropDown = null;
	private List<GridDataPropertyView> viewFrontoffcDetail = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> frontoffice = null;
	private List<ConfigurationUtilBean> vendordropdownlist = null;
	private LinkedList<ConfigurationUtilBean> visitorfields = null;
	private LinkedList<ConfigurationUtilBean> vehiclefields = null;
	private List<ConfigurationUtilBean> visitordatetimelist = null;
	private List<ConfigurationUtilBean> vehicledatetimelist = null;
	private List<ConfigurationUtilBean> prerequestdatetimelist = null;
	private List<GridDataPropertyView> viewGateLocation = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> locationDropDown = null;
	private List<ConfigurationUtilBean> deptDropDown = null;
	private List<ConfigurationUtilBean> empDropDown = null;
	private List<ConfigurationUtilBean> dropDownList = null;
	private List<ConfigurationUtilBean> prerequestfields = null;
	Map<Integer, String> locationlist = new LinkedHashMap<Integer, String>();
	Map<Integer, String> gatenamelist = new HashMap<Integer, String>();
	Map<Integer, String> departmentlist = new HashMap<Integer, String>();
	Map<Integer, String> jlocationlist = new HashMap<Integer, String>();
	Map<Integer, String> employeelist = new HashMap<Integer, String>();
	Map<Integer, String> gatelistofloggedinUser = new HashMap<Integer, String>();
	String alerttime = null;
	Map<Integer, String> purposelist = new HashMap<Integer, String>();
	Map<Integer, String> purposeListPreReqest = new HashMap<Integer, String>();
	Map<Integer, String> ornizationList = new HashMap<Integer, String>();
	Map<Integer, String> reqvisitorList = new HashMap<Integer, String>();
	Map<Integer, String> reqvisitormobList = new HashMap<Integer, String>();
	Map<Integer, String> locationListPreReqest = new HashMap<Integer, String>();
	Map<Integer, String> vendortypelist = new HashMap<Integer, String>();
	Map<Integer, String> vendorcompNameList = new HashMap<Integer, String>();
	private List<ConfigurationUtilBean> gateData = null;
	private List<GridDataPropertyView> viewVisitorDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewVehicleDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewPrerequestDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewEmplocmappingDetail = new ArrayList<GridDataPropertyView>();
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
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<String> alertlist = null;
	private List<Object> purposeList;
	private List<Object> vender;
	private List<Object> gatelocation;
	private List<Object> visitorData;
	private List<Object> prerequestData;
	private List<Object> emplocgatemapdata;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private BufferedReader readfilename;

	public String execute() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			setMainHeaderName("Purpose");
			setPurposeMasterViewProp("mapped_purpose_configuration",
					"purpose_configuration");

		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <execute()> of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getPurposeRecords() {
		if (userName == null || userName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from purpose_admin");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					connectionSpace);
			if (dataCount != null) 
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

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from purpose_admin as pd ");
				List fieldNames = Configuration.getColomnList(
						"mapped_purpose_configuration", accountID,
						connectionSpace, "purpose_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty()) {
					for (Iterator iterator = fieldNames.iterator(); iterator
							.hasNext();) {

						String object = iterator.next().toString();
						if (object != null) {
							if (i <= fieldNames.size() - 1) {
								if (object.toString().equalsIgnoreCase(
										"alert_to")) {
									query.append(" dept.deptName , ");
									queryEnd
											.append(" left join department as dept on pd.alert_to = dept.id ");
								}
								if (object.toString().equalsIgnoreCase(
										"empName")) {
									query.append(" empb.empName ");
									queryEnd
											.append(" left join employee_basic as empb on pd.empName = empb.id ");
								} else {
									if (!object.toString().equalsIgnoreCase(
											"alert_to")
											&& !object
													.toString()
													.equalsIgnoreCase("empName")) {
										query.append("pd." + object.toString()
												+ ",");
									}
								}
							} else {
								query.append("pd." + object.toString());
							}
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) {
						query.append(" where ");
						// add search query based on the search operation
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

				//	query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due
					 * to configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames,
							"purpose_admin", connectionSpace);

					//System.out.println("Querry Purpose>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty()) {
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {

									if (fieldNames.get(k).toString()
											.equalsIgnoreCase("orgnztnlevel")) {
										// need to work here for viewing the
										// organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString()
												.equalsIgnoreCase("2")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("3")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("4")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("5")) {
										}
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									} else {
										if (k == 0) {
											one.put(fieldNames.get(k)
													.toString(),
													(Integer) obdata[k]);
										} else {
											if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"date")) {
												one
														.put(
																fieldNames
																		.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"time")) {
												one.put(fieldNames.get(k)
														.toString(), (obdata[k]
														.toString().substring(
														0, 5)));
											} else {
												one.put(fieldNames.get(k)
														.toString(), DateUtil
														.makeTitle(obdata[k]
																.toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setPurposeList(listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}
			}

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getPurposeRecords> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setPurposeMasterViewProp(String table1, String table2) {
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPurpose.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewPurpose.add(gpv);
		}
	}

	public String addPurpose() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			purposedata = new ArrayList<ConfigurationUtilBean>();
			purposeDropDown = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_purpose_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"purpose_configuration");
			if (userName != null
					&& !userName.equalsIgnoreCase("")
					&& (getPurpose_for() == null || getPurpose_for()
							.equalsIgnoreCase(""))) {
				dropdownforpurpose = true;
				return SUCCESS;
			} else {
				if (group != null) {
					for (GridDataPropertyView gdp : group) {
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"sr_number")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"email_id")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"purpose")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}

							purposedata.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("D")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							purposeDropDown.add(obj);
						}
						if (gdp.getColType().trim().equalsIgnoreCase("Time")
								&& !gdp.getColomnName().equalsIgnoreCase(
										"userName")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("date")
								&& !gdp.getColomnName()
										.equalsIgnoreCase("time")) {
							obj.setValue(gdp.getHeadingName());
							obj.setKey(gdp.getColomnName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							if (gdp.getMandatroy().toString().equals("1")) {
								obj.setMandatory(true);
							} else {
								obj.setMandatory(false);
							}
							purposedata.add(obj);
						}
						if (gdp.getColomnName().equalsIgnoreCase("alert_to")) {
							alert_to = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								deptNameExist = "true";
							else
								deptNameExist = "false";
						} else if (gdp.getColomnName().equalsIgnoreCase(
								"empName")) {
							empName = gdp.getHeadingName();
							if (gdp.getMandatroy().equalsIgnoreCase("1"))
								empNameExist = "true";
							else
								empNameExist = "false";
						}
					}
				}
			}
		} catch (Exception e) {
			addActionError("There Is Problem to Add Purpose.");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <addPurpose> of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return "alertaftersuccess";
	}

	@SuppressWarnings("unchecked")
	public String submitPurpose() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// SessionFactory connectionSpace = (SessionFactory)
				// session.get("connectionSpace");
				// String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> purposeData = Configuration
						.getConfigurationData("mapped_purpose_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "purpose_configuration");

				if (purposeData != null && purposeData.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : purposeData) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("purpose_admin", tableColume,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null
									&& !parmName.equalsIgnoreCase("alertval")) {
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

					//System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("purpose_admin", insertData,
							connectionSpace);
					if (status) {
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				} else {
					addActionMessage("Oops There is some error in data insertion!!!!");
				}
			} catch (Exception e) {
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeFrontOffcView() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			setMainHeaderName("Vendor");
			setFrontOfficeViewProp("mapped_frontoffice_configuration",
					"frontoffice_configuration");

		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <execute()> of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public void setFrontOfficeViewProp(String table1, String table2) {
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewFrontoffcDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewFrontoffcDetail.add(gpv);
		}
	}

	public String getFrontOfficeRecords() {

		if (userName == null || userName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from front_office_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					connectionSpace);
			if (dataCount != null) {
				
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

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from front_office_details as fod ");
				List fieldNames = Configuration.getColomnList(
						"mapped_frontoffice_configuration", accountID,
						connectionSpace, "frontoffice_configuration");
				int i = 0;

				if (fieldNames != null && !fieldNames.isEmpty()) {
					for (Iterator iterator = fieldNames.iterator(); iterator
							.hasNext();) {

						String object = iterator.next().toString();
						if (object != null) {
							if (i < fieldNames.size() - 1) {
								if (object.toString().equalsIgnoreCase(
										"vendor_type")) {
									query.append(" vad.vender_type , ");
									queryEnd
											.append(" left join vendor_admin_details as vad on fod.vendor_type = vad.id ");
								} else {
									query.append("fod." + object.toString()
											+ ",");
								}
							} else
								query.append("fod." + object.toString());
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) {
						query.append(" where ");
						// add search query based on the search operation
						
							if (getSearchOper().equalsIgnoreCase("eq")) {
	                            if(getSearchField().equals("vendor_type"))
	                            {
	                                query.append("vad.vender_type ='"
	                                        + getSearchString() + "'");
	                            }
	                            else if(getSearchField().equals("brief"))
	                            {
	                                query.append("fod."+getSearchField() + " = '"
	                                        + getSearchString() + "'");
	                            }
	                            else if(getSearchField().equals("empName"))
	                            {
	                                query.append("empb."+getSearchField() + " = '"
	                                        + getSearchString() + "'");
	                            }
	                           
	                            else{
	                            query.append(" " + getSearchField() + " = '"
	                                    + getSearchString() + "'");
	                            }
	                        
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

		//			query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due
					 * to configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames,
							"front_office_details", connectionSpace);

					//System.out.println("Querry front>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty()) {
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {

									if (fieldNames.get(k).toString()
											.equalsIgnoreCase("orgnztnlevel")) {
										// need to work here for viewing the
										// organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString()
												.equalsIgnoreCase("2")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("3")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("4")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("5")) {
										}
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									} else {
										if (k == 0) {
											one.put(fieldNames.get(k)
													.toString(),
													(Integer) obdata[k]);
										} else {
											if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"date")) {
												one
														.put(
																fieldNames
																		.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"time")) {
												one.put(fieldNames.get(k)
														.toString(), (obdata[k]
														.toString().substring(
														0, 5)));
											} else {
												one.put(fieldNames.get(k)
														.toString(), DateUtil
														.makeTitle(obdata[k]
																.toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setVender(listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}
			}

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getPurposeRecords> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String addVenderDetails() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			frontoffice = new ArrayList<ConfigurationUtilBean>();
			vendordropdownlist = new ArrayList<ConfigurationUtilBean>();
			purposeDescriptionTextBox = new ArrayList<ConfigurationUtilBean>();
			vendortypelist = new HashMap<Integer, String>();
			vendortypelist = CommonMethod.allVendor(connectionSpace);
			vendorcompNameList = new HashMap<Integer, String>();
			vendorcompNameList = CommonMethod.allVendorCompName(connectionSpace);
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_frontoffice_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"frontoffice_configuration");
			if (group != null) {
				for (GridDataPropertyView gdp : group) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"vndr_representv")
							&& !gdp.getColomnName().equalsIgnoreCase("dept")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"visit_purpose")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("emp_name")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						frontoffice.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						vendordropdownlist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("vendor_type")) {
						vendortype = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vendorTypeExist = "true";
						else
							vendorTypeExist = "false";
					}
					if (gdp.getColomnName().equalsIgnoreCase("brief")) {
						vendorCompName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vendorcompNameExist = "true";
						else
							vendorcompNameExist = "false";
					}
				}
			}
		} catch (Exception e) {
			addActionError("Ooops There Is Problem to Add Vender!");
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <addVenderDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getVendorCompNameFront()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			vendorcompFrontlist = new HashMap<Integer, String>();
			{
				vendorcompFrontlist = CommonMethod.vendorCompNameList(connectionSpace, id);
			} 
			if (vendorcompFrontlist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : vendorcompFrontlist.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to Load Employee!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getVendorCompNameDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	
	
	}
	
	public String submitVenderDetail() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// SessionFactory connectionSpace = (SessionFactory)
				// session.get("connectionSpace");
				// String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> purposeData = Configuration
						.getConfigurationData(
								"mapped_frontoffice_configuration", accountID,
								connectionSpace, "", 0, "table_name",
								"frontoffice_configuration");

				if (purposeData != null && purposeData.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : purposeData) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("front_office_details", tableColume,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null) {
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

					//System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("front_office_details",
							insertData, connectionSpace);
					if (status) {
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				} else {
					addActionMessage("Oops There is some error in data insertion!!!!");
				}
			} catch (Exception e) {
				log
						.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method <submitVender> of class "
										+ getClass(), e);
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String editFrontOfficeGrid() {
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit")) {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("front_office_details", wherClause,
						condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del")) {
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
				cbt.deleteAllRecordForId("front_office_details", "id", condtIds
						.toString(), connectionSpace);
			}
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method editFrontOfficeGrid of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String editPurposeGrid() {

		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit")) {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
						 if(Parmname.equalsIgnoreCase("alert_to") || Parmname.equalsIgnoreCase("empName"))
	                        { 
	                            
	                            String alert_to=request.getParameter("alert_to");
	                            String emp=request.getParameter("empName");
	                           
	                            String query = "SELECT dept.id,dept.deptName,empb.id 'empi',empb.empName FROM employee_basic AS empb  INNER JOIN department AS dept WHERE dept.flag=0 AND dept.deptName='"+alert_to+"' AND empName='"+emp+"' ORDER BY dept.deptName";
	                            System.out.println("Query Is::::::"+query.toString());
	                            List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
	                            if (dataList != null && dataList.size() > 0)
	                            {
	                                for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
	                                {
	                                    Object[] object = (Object[]) iterator.next();
	                                    if (Parmname.equalsIgnoreCase("alert_to"))
	                                        {
	                                        paramValue = object[0].toString();
	                                        wherClause.put(Parmname,paramValue);
	                                    }
	                                    else{
	                                        paramValue = object[2].toString();
	                                        wherClause.put(Parmname,paramValue);
	                                        }
	                                   }
	                            }                   
	                        }
	                    else
	                        {
	                            wherClause.put(Parmname, paramValue);
	                        }
	                }
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("purpose_admin", wherClause, condtnBlock,
						connectionSpace);
			} else if (getOper().equalsIgnoreCase("del")) {
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
				cbt.deleteAllRecordForId("purpose_admin", "id", condtIds
						.toString(), connectionSpace);
			}
		} catch (Exception e) {
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method editPurposeGrid of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String beforeGateLocationRecords() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			setMainHeaderName("Gate");
			setGateMasterViewProp("mapped_gate_configuration",
					"gate_configuration");

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <gate_configuration()> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setGateMasterViewProp(String table1, String table2) {
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewGateLocation.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewGateLocation.add(gpv);
		}
	}

	public String getGateLocationRecords() {
		if (userName == null || userName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from gate_location_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					connectionSpace);
			if (dataCount != null) 
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
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from gate_location_details as gld ");
				List fieldNames = Configuration.getColomnList(
						"mapped_gate_configuration", accountID,
						connectionSpace, "gate_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty()) {
					for (Iterator iterator = fieldNames.iterator(); iterator
							.hasNext();) {

						String object = iterator.next().toString();
						if (object != null) {
							if (i < fieldNames.size() - 1) {
								if (object.toString().equalsIgnoreCase(
										"location")) {
									query.append(" loc.name , ");
									queryEnd
											.append(" left join location as loc on loc.id = gld.location ");
								} else {
									query.append("gld." + object.toString()
											+ ",");
								}
							} else {
								if (object.toString().equalsIgnoreCase(
										"location")) {
									query.append(" loc.name ");
									queryEnd
											.append(" left join location as loc on loc.id = gld.location ");
								} else {
									query.append("gld." + object.toString());
								}
							}
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					//System.out.println("Querry>test>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) {
						query.append(" where ");
						// add search query based on the search operation
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
				//	query.append(" limit " + from + "," + to);
					cbt.checkTableColmnAndAltertable(fieldNames,
							"gate_location_details", connectionSpace);

					//System.out.println("Querry Gate>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty()) {
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {

									if (fieldNames.get(k).toString()
											.equalsIgnoreCase("orgnztnlevel")) {
										// need to work here for viewing the
										// organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString()
												.equalsIgnoreCase("2")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("3")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("4")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("5")) {
										}
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									} else {
										if (k == 0) {
											one.put(fieldNames.get(k)
													.toString(),
													(Integer) obdata[k]);
										} else {
											if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"date")) {
												one
														.put(
																fieldNames
																		.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"time")) {
												one.put(fieldNames.get(k)
														.toString(), (obdata[k]
														.toString().substring(
														0, 5)));
											} else {
												one.put(fieldNames.get(k)
														.toString(), DateUtil
														.makeTitle(obdata[k]
																.toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setGatelocation(listhb);
						//System.out.println((double) getRecords());
						//System.out.println((double) getRows());
						//System.out.println((double) getPage());
						//System.out.println("><><><><"+ (int) Math.ceil((double) getRecords()/ (double) getRows()));
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}
			}

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getGateLocationRecords> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addGateLocationName() {
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			List<GridDataPropertyView> list = Configuration
					.getConfigurationData("mapped_gate_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"gate_configuration");

			locationDropDown = new ArrayList<ConfigurationUtilBean>();
			gateData = new ArrayList<ConfigurationUtilBean>();
			locationlist = new LinkedHashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			if (list != null) {
				for (GridDataPropertyView gdp : list) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();

					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						locationDropDown.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						gateData.add(obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String submitGateForLocation() {
		String returnResult = ERROR;
    	boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// SessionFactory connectionSpace = (SessionFactory)
				// session.get("connectionSpace");
				// String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> purposeData = Configuration
						.getConfigurationData("mapped_gate_configuration",
								accountID, connectionSpace, "", 0,
								"table_name", "gate_configuration");

				if (purposeData != null && purposeData.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : purposeData) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("gate_location_details", tableColume,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) 
					  {

						  Collections.sort(requestParameterNames);
						  Iterator it = requestParameterNames.iterator();
						  while (it.hasNext()) 
						  { 
							  
					       String  parmName = it.next().toString(); 
						    if(!parmName.equalsIgnoreCase("userName") &&
						       !parmName.equalsIgnoreCase("date") &&
						       !parmName.equalsIgnoreCase("time")){
						   String  paramValue = request.getParameter(parmName);
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

					status = cbt.insertIntoTable("gate_location_details",
							insertData, connectionSpace);
					if (status) {
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
				} else {
					addActionMessage("Oops There is some error in data insertion!!!!");
				}
			} catch (Exception e) {
				log
						.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method <submitgateForLocation> of class "
										+ getClass(), e);
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String editGateLocationGrid() {

		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit")) {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("gate_location_details", wherClause,
						condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del")) {
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
				cbt.deleteAllRecordForId("gate_location_details", "id",
						condtIds.toString(), connectionSpace);
			}
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method editGateLocationGrid of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getEmployeeDetails() {
		String resultString = ERROR;
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				resultString = LOGIN;
			}
			employeelist = new HashMap<Integer, String>();
			if (getEmplocgatemap() != null
					&& getEmplocgatemap().equals("emplocgatemap")) {
				employeelist = CommonMethod.employeeList(connectionSpace, id);
			} else if (getEmplocgatemap() != null
					&& getEmplocgatemap().equals("loademp")) {
				employeelist = CommonMethod.alleEmployeeList(connectionSpace,
						id);
			} else if (getPurposeaddition() != null
					&& getPurposeaddition().equals("purposeaddition")) {
				employeelist = CommonMethod.alleEmployeeList(connectionSpace,
						id);
			}
			if (employeelist != null) {
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : employeelist.entrySet()) {

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e) {
			addActionError("There Is Problem to Load Employee!");
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getEmployeeDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}

	public String getAlertTime() {
		String resultString = ERROR;
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				resultString = LOGIN;
			}
			if (id != null) {
				alerttime = CommonMethod.alertTime(connectionSpace, id);
				resultString = SUCCESS;
			} else {
				resultString = ERROR;
			}

		} catch (Exception e) {
			addActionError("Ooops There Is Problem to Load getAlertTime!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat()
					+ " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method <getAlertTime> of class "
					+ getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}

	public String beforePreRequestRecords() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			purposeListPreReqest = new HashMap<Integer, String>();
			purposeListPreReqest = CommonMethod.allPurpose(connectionSpace);
			locationListPreReqest = new HashMap<Integer, String>();
			locationListPreReqest = CommonMethod
					.allLocationList(connectionSpace);
			setMainHeaderName("Visitor Pre-Request");
			setPrerequestViewProp("mapped_prerequest_configuration",
					"prerequest_configuration");

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <beforePreRequestRecords()> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setPrerequestViewProp(String table1, String table2) {
		GridDataPropertyView gpv = new GridDataPropertyView();
		String usrtype = CommonMethod.getUserType(connectionSpace, userName);
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewPrerequestDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if (gdp.getColomnName().equalsIgnoreCase("visit_date")) {
				gpv.setHeadingName("Scheduled Date & Time");
			} else if (gdp.getColomnName().equalsIgnoreCase("location")) {
				gpv.setHeadingName("Location & Gate");
			} else {
				gpv.setHeadingName(gdp.getHeadingName());
			}
			if (gdp.getColomnName().equals("time_in")) {
				gpv.setHideOrShow("true");
			} else if (gdp.getColomnName().equals("gate")) {
				gpv.setHideOrShow("true");
			} else if (usrtype.equals("M")
					&& gdp.getColomnName().equals("visited_person")) {
				gpv.setHideOrShow("false");
			} else {
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewPrerequestDetail.add(gpv);
		}
	}

	public String getPrerequestRecords() {
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from prerequest_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					connectionSpace);
			if (dataCount != null)
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

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from prerequest_details as prereq ");
				List fieldNames = Configuration.getColomnList(
						"mapped_prerequest_configuration", accountID,
						connectionSpace, "prerequest_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty()) {
					for (Iterator iterator = fieldNames.iterator(); iterator
							.hasNext();) {

						String object = iterator.next().toString();
						if (object != null) {
							if (i < fieldNames.size() - 1) {
								if (object.toString().equalsIgnoreCase(
										"location")) {
									query.append(" loc.name , ");
									queryEnd
											.append(" left join location as loc on loc.id = prereq.location ");
								}
								if (object.toString().equalsIgnoreCase(
										"purpose")) {
									query.append(" purposeadmin.purpose, ");
									queryEnd
											.append(" left join purpose_admin as purposeadmin on purposeadmin.id = prereq.purpose ");
								} else {
									if (!object.toString().equalsIgnoreCase(
											"location")) {
										query.append("prereq."
												+ object.toString() + ",");
									}
								}
							} else {
								if (object.toString().equalsIgnoreCase(
										"location")) {
									query.append(" loc.name ");
									queryEnd
											.append(" left join location as loc on loc.id = prereq.location ");
								} else {
									query.append("prereq." + object.toString());
								}
							}
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					if (getPurpose() != null && !getPurpose().equals("-1")) {
						query.append(" " + "where prereq.purpose="
								+ getPurpose());
					} else if (getPurpose() != null
							&& getPurpose().equals("-1")) {
					}
					if (getLocation() != null && !getLocation().equals("-1")) {
						query.append(" " + "where prereq.location="
								+ getLocation());
					} else if (getLocation() != null
							&& getLocation().equals("-1")) {
					}
					if (getFrom_date() != null && !getFrom_date().equals("")
							&& getTo_date() != null && !getTo_date().equals("")) {
						query.append(" where prereq.visit_date between '"
								+ getFrom_date() + "' and '" + getTo_date()
								+ "'");
					}
					//System.out.println("Querry>Prereqest>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) {
						query.append(" where ");
						// add search query based on the search operation
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

			       //query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due
					 * to configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames,
							"prerequest_details", connectionSpace);

					System.out.println("Querry Pre>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty()) {
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {

									if (fieldNames.get(k).toString()
											.equalsIgnoreCase("orgnztnlevel")) {
										// need to work here for viewing the
										// organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString()
												.equalsIgnoreCase("2")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("3")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("4")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("5")) {
										}
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									} else {
										if (k == 0) {
											one.put(fieldNames.get(k)
													.toString(),
													(Integer) obdata[k]);
										} else {
											if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"date")) {
												one
														.put(
																fieldNames
																		.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"time")) {
												one.put(fieldNames.get(k)
														.toString(), (obdata[k]
														.toString().substring(
														0, 5)));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"location")) {
												if (obdata[k + 9] != null
														&& obdata[k] != null) {
													one
															.put(
																	fieldNames
																			.get(
																					k)
																			.toString(),
																	obdata[k]
																			.toString()
																			+ "   "
																			+ obdata[k + 9]
																					.toString());
												}
											} else if (fieldNames.get(k) != null
													&& fieldNames
															.get(k)
															.toString()
															.equalsIgnoreCase(
																	"visit_date")) {
												if (obdata[k + 1] != null
														&& obdata[k] != null) {
													one
															.put(
																	fieldNames
																			.get(
																					k)
																			.toString(),
																	obdata[k]
																			.toString()
																			+ "   "
																			+ obdata[k + 1]
																					.toString());
												}
											} else {
												one.put(fieldNames.get(k)
														.toString(), DateUtil
														.makeTitle(obdata[k]
																.toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setPrerequestData(listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getPrerequestRecords> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}

	public String addPrerequestDetails() {
		String empNaMe = (String) session.get("empName");
		String prefixval = "VS";
		String subpre = null;
		CommonMethod srNoPreReqObj = new CommonMethod();
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			String visitorsrnumber = srNoPreReqObj
					.getPreReqVisitorSerialNumber(connectionSpace);
			int number = Integer.parseInt(visitorsrnumber);
			String visitorSrNum = srNoPreReqObj.getSeries(connectionSpace,
					prefixval);
			if (visitorSrNum != null && !visitorSrNum.equals("")) {
				String pre = visitorSrNum.substring(0, 2);
				subpre = visitorSrNum.substring(2, 5);
				String numberseries = visitorSrNum.substring(5);
			}
			if (number <= 1000) {
				number = 1001;
			} else {
				number = number + 1;
			}
			String strnumber = Integer.toString(number);
			String srNumber = prefixval + subpre + strnumber;
			String EmpMobile = CommonMethod.getMobileNo(connectionSpace,
					empNaMe);
			String Empgate = CommonMethod.getEmpGateName(connectionSpace,
					empNaMe);
			String dptname = CommonMethod
					.getDeptName(connectionSpace, userName);
			prerequestfields = new ArrayList<ConfigurationUtilBean>();
			dropDownList = new ArrayList<ConfigurationUtilBean>();
			purposeListPreReqest = new HashMap<Integer, String>();
			purposeListPreReqest = CommonMethod.allPurpose(connectionSpace);
			ornizationList = new HashMap<Integer, String>();
			ornizationList = CommonMethod
					.allVisitorOrganization(connectionSpace);
			locationListPreReqest = new HashMap<Integer, String>();
			locationListPreReqest = CommonMethod
					.allLocationList(connectionSpace);
			prerequestdatetimelist = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_prerequest_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"prerequest_configuration");
			if (group != null) {
				for (GridDataPropertyView gdp : group) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							// &&
							// !gdp.getColomnName().equalsIgnoreCase("sr_number")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"date_of_entry")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"time_of_entry")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("email_id")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"meet_status")
							&& !gdp.getColomnName().equalsIgnoreCase("empName")
							&& !gdp.getColomnName().equalsIgnoreCase("id_val")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"visit_date")
							&& !gdp.getColomnName().equalsIgnoreCase("time_in")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						if (gdp.getColomnName().equalsIgnoreCase(
								"visited_person")) {
							obj.setDefault_value(empNaMe);
						}
						if (gdp.getColomnName().equalsIgnoreCase(
								"visited_mobile")) {
							obj.setDefault_value(EmpMobile);
						}

						if (gdp.getColomnName().equalsIgnoreCase("deptName")) {
							obj.setDefault_value(dptname);
						}
						if (gdp.getColomnName().equalsIgnoreCase("sr_number")) {
							setSr_number(srNumber);
						}
						if (gdp.getColomnName().equalsIgnoreCase("gate")) {
							// setGatename(Empgate);
							obj.setDefault_value(Empgate);
						}
						prerequestfields.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						dropDownList.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("Date")
							|| gdp.getColType().trim().equalsIgnoreCase("Time")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						prerequestdatetimelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("purpose")) {
						purpose = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							purposeNameExist = "true";
						else
							purposeNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("location")) {
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							locationNameExit = "true";
						else
							locationNameExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"visit_date")) {
						visit_date = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							previsitdateExit = "true";
						else
							previsitdateExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"visitor_company")) {
						orgname = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							visitorCompanyExist = "true";
						else
							visitorCompanyExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"visitor_name")) {
						visitorName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							visitorNameExist = "true";
						else
							visitorNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"visitor_mobile")) {
						visitorMob = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							visitorMobileExist = "true";
						else
							visitorMobileExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("time_in")) {
						time_in = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							previsittimeExit = "true";
						else
							previsittimeExit = "false";
					}
				}
			}
		} catch (Exception e) {
			addActionError("Ooops There Is Problem to add PrerequestDetails!");
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <addPrerequestDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getReqVisitorDetails() {
		String resultString = ERROR;
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				resultString = LOGIN;
			}
			if (getOrganizationName() != null) {
				reqvisitorList = new HashMap<Integer, String>();
				reqvisitorList = CommonMethod.allReqVisitor(connectionSpace,
						getOrganizationName());
			}
			if (reqvisitorList != null) {
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : reqvisitorList.entrySet()) {

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e) {
			addActionError("There Is Problem to Load Visitor.");
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getReqVisitorDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resultString;

	}

	public String getReqVisitorMobDetails() {
		String resultString = ERROR;
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				resultString = LOGIN;
			}
			if (getRequestedvisitorName() != null) {
				reqvisitormobList = new HashMap<Integer, String>();
				reqvisitormobList = CommonMethod.allReqVisitorMob(
						connectionSpace, getRequestedvisitorName());
			}
			if (reqvisitormobList != null) {
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : reqvisitormobList
						.entrySet()) {

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e) {
			addActionError("There Is Problem to Load Visitor Mob.");
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getReqVisitorMobDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resultString;

	}

	public String submitPreRequestDetails() {
		String returnResult = ERROR;
		String visitedPerson = null;
		String emailid = null;
		String empMobileNo = null;
		String visitorName = null;
		String visitorCompany = null;
		String scheduledDate = null;
		String scheduledTime = null;
		String visitorMobileNo = null;
		String locationName = null;
		String OTP = null;
		String dept = null;
		CommonMethod srNumberObject = new CommonMethod();
		RandomNumberGenerator randamNum = new RandomNumberGenerator();
		List<String> Tablecolumename = new ArrayList<String>();
		List<String> Tablecolumevalue = new ArrayList<String>();
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// SessionFactory connectionSpace = (SessionFactory)
				// session.get("connectionSpace");
				/*
				 * int srnumber =
				 * srNumberObject.getSerialNumber(connectionSpace); if (srnumber <=
				 * 1000) { srnumber = 1001; } else { srnumber = srnumber + 1; }
				 * String sNumber = Integer.toString(srnumber);
				 */
				// String accountID = (String) session.get("accountid");
				int otpNumber = randamNum.getNDigitRandomNumber(6);
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> prerequestData = Configuration
						.getConfigurationData(
								"mapped_prerequest_configuration", accountID,
								connectionSpace, "", 0, "table_name",
								"prerequest_configuration");

				if (prerequestData != null && prerequestData.size() > 0) {
					// create table query based on configuration

					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					boolean status = false;
					boolean serverstatus = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					InsertDataTable ob = null;
					for (GridDataPropertyView gdp : prerequestData) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

					}
					cbt.createTable22("prerequest_details", tableColume,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (parmName.equalsIgnoreCase("visited_person")) {
								emailid = CommonMethod.getEmailId(
										connectionSpace, paramValue);
								visitedPerson = paramValue;
							}
							if (parmName.equalsIgnoreCase("visited_mobile")) {
								empMobileNo = paramValue;
							}
							if (parmName.equalsIgnoreCase("visitor_name")) {
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")) {
									visitorName = paramValue;
								}
							} else if (parmName.equalsIgnoreCase("visitorname")) {
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")) {
									visitorName = paramValue;
									parmName = "visitor_name";
									//Tablecolumename.add(parmName);
									//Tablecolumevalue.add(paramValue);
									/*
									 * ob = new InsertDataTable();
									 * ob.setColName(parmName);
									 * ob.setDataName(paramValue);
									 * insertData.add(ob);
									 */
								}
							}
							if (parmName.equalsIgnoreCase("visitor_mobile")) {
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")) {
									visitorMobileNo = paramValue;
								}
							} else if (parmName
									.equalsIgnoreCase("visitormobile")) {
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")) {
									visitorMobileNo = paramValue;
									parmName = "visitor_mobile";
									//Tablecolumename.add(parmName);
									//Tablecolumevalue.add(paramValue);
								}
							}
							if (parmName.equalsIgnoreCase("visit_date")) {
								scheduledDate = paramValue;
							}
							if (parmName.equalsIgnoreCase("time_in")) {
								scheduledTime = paramValue;

							}
							if (parmName.equalsIgnoreCase("deptName")) {
								dept = paramValue;

							}

							if (parmName.equalsIgnoreCase("location")) {
								locationName = CommonMethod.getLocationOnId(
										connectionSpace, paramValue);
							}
							if (parmName.equalsIgnoreCase("one_time_pwd")) {
								paramValue = Integer.toString(otpNumber);
								OTP = Integer.toString(otpNumber);
							}
							if (parmName.equalsIgnoreCase("visitor_company")) {
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")) {
									visitorCompany = paramValue;
								}
							} else if (parmName
									.equalsIgnoreCase("visitorcompany")) {
								if (paramValue != null
										&& !paramValue.equalsIgnoreCase("")) {
									visitorCompany = paramValue;
									parmName = "visitor_company";
									//Tablecolumename.add(parmName);
									//Tablecolumevalue.add(paramValue);
								}
							}
							//System.out.println("pname    pvalue>" + parmName+ "  " + paramValue);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.endsWith("sms")	&& !parmName.endsWith("mail") && parmName != null && !parmName.equalsIgnoreCase("visitorname") && !parmName.equalsIgnoreCase("visitormobile") && !parmName.equalsIgnoreCase("visitorcompany")/* * && (!parmName .equalsIgnoreCase("visitor_name") && * !paramValue .equalsIgnoreCase("New")) && * (!parmName .equalsIgnoreCase("visitor_mobile") && * !paramValue .equalsIgnoreCase("New")) && * (!parmName .equalsIgnoreCase("visitor_company") && * !paramValue .equalsIgnoreCase("New"))*/)
							{
								if(!(parmName.equalsIgnoreCase("visitor_company") && paramValue.equalsIgnoreCase("New")) && !(parmName.equalsIgnoreCase("visitor_name") && paramValue.equalsIgnoreCase("New")) && !(parmName.equalsIgnoreCase("visitor_mobile") && paramValue.equalsIgnoreCase("New")))
								{
									Tablecolumename.add(parmName);
									Tablecolumevalue.add(paramValue);
								}
								
							}
						}
					}

					/**
					 * Code to insert Pre-request data on server database.
					 */
					PreRequestserviceStub objstatus = new PreRequestserviceStub();
					InsertIntoTable obj = new InsertIntoTable();
					Tablecolumename.add("userName");
					Tablecolumevalue.add(userName);
					Tablecolumename.add("date");
					Tablecolumevalue.add(DateUtil.getCurrentDateUSFormat());
					Tablecolumename.add("time");
					Tablecolumevalue.add(DateUtil.getCurrentTime());
					Tablecolumename.add("date_of_entry");
					Tablecolumevalue.add(DateUtil.getCurrentDateIndianFormat());
					Tablecolumename.add("time_of_entry");
					Tablecolumevalue.add(DateUtil.getCurrentTime());
					StringBuilder createTableQuery = new StringBuilder(
							"INSERT INTO " + "prerequest_details" + " (");

					/*
					 * ob = new InsertDataTable(); ob.setColName("userName");
					 * ob.setDataName(userName); insertData.add(ob);
					 * 
					 * ob = new InsertDataTable(); ob.setColName("date");
					 * ob.setDataName(DateUtil.getCurrentDateUSFormat());
					 * insertData.add(ob);
					 * 
					 * ob = new InsertDataTable(); ob.setColName("time");
					 * ob.setDataName(DateUtil.getCurrentTime());
					 * insertData.add(ob);
					 * 
					 * ob = new InsertDataTable();
					 * ob.setColName("date_of_entry");
					 * ob.setDataName(DateUtil.getCurrentDateIndianFormat());
					 * insertData.add(ob);
					 * 
					 * ob = new InsertDataTable();
					 * ob.setColName("time_of_entry");
					 * ob.setDataName(DateUtil.getCurrentTime());
					 * insertData.add(ob);
					 * 
					 * System.out.println("insertData:" + insertData.size());
					 */
					int i = 1;
					// append Column
					for (String h : Tablecolumename) {
						if (i < Tablecolumename.size())
							createTableQuery.append(h + ", ");
						else
							createTableQuery.append(h + ")");
						i++;
					}

					createTableQuery.append(" VALUES (");
					i = 1;
					for (String h : Tablecolumevalue) {
						if (i < Tablecolumevalue.size())
							createTableQuery.append("'" + h + "', ");
						else
							createTableQuery.append("'" + h + "')");
						i++;
					}
					createTableQuery.append(" ;");
					// System.out.println("Prereq>"+createTableQuery);
					obj.setCreateTableQuery(createTableQuery.toString());
					// insert into local database.
					int maxId = cbt.insertIntoTable(createTableQuery.toString(), connectionSpace);
					
					/*
					 * status = cbt.insertIntoTable("prerequest_details",
					 * insertData, connectionSpace);
					 */
					if (maxId > 0) 
					{
						if (getSms() == true && getMail() == true) 
						{
							// SMS to visited person.
							if (empMobileNo != null
									&& empMobileNo != ""
									&& empMobileNo.trim().length() == 10
									&& !empMobileNo.startsWith("NA")
									&& (empMobileNo.startsWith("9")
											|| empMobileNo.startsWith("8") || empMobileNo
											.startsWith("7"))) {
								String empPreReqMsg = "Dear " + visitedPerson
										+ ", your meeting is scheduled with "
										+ visitorName + " on date "
										+ scheduledDate + " at time "
										+ scheduledTime + ".";
								new MsgMailCommunication()
										.addScheduledMessageClientServer(
												visitedPerson, dept,
												empMobileNo, empPreReqMsg, "",
												"Pending", "0", "VAM",
												scheduledDate, scheduledTime,
												connectionSpace);
								//System.out.println("empPreReqMsg>>>"+ empPreReqMsg);
							}
							// SMS to visitor.
							if (visitorMobileNo != null
									&& visitorMobileNo != ""
									&& visitorMobileNo.trim().length() == 10
									&& !visitorMobileNo.startsWith("NA")
									&& (visitorMobileNo.startsWith("9")
											|| visitorMobileNo.startsWith("8") || visitorMobileNo
											.startsWith("7"))) {
								String visitorPreReqMsg = "Dear "
										+ visitorName
										+ ", "
										+ visitedPerson
										+ " had scheduled a meeting at "
										+ locationName
										+ " on "
										+ scheduledTime
										+ ". Please reach on time on date "
										+ scheduledDate
										+ ". Show OTP "
										+ OTP
										+ " to security room. Thanks, Admin Team JBM.";
								new MsgMailCommunication()
										.addMessageClientServer(
												visitorMobileNo,
												visitorPreReqMsg, "",
												"Pending", "0", "VAM",
												connectionSpace);
								//System.out.println("visitorPreReqMsg>>>"+ visitorPreReqMsg);

							}
							// mail to visited person.
							if (emailid != null && !emailid.equals("")
									&& !emailid.equals("NA")) {
								MailText mailtextobj = new MailText();
								String mailText = mailtextobj
										.getPreReqMailText(visitedPerson,
												visitorName, visitorCompany,
												locationName, scheduledDate,
												scheduledTime);
								new MsgMailCommunication().addMail(emailid,
										"Visitor PreRequest Mail", mailText,
										"", "Pending", "0", "", "VAM",
										connectionSpace);
							}
						} else if (getSms() == true) {
							// SMS to visited person.
							if (empMobileNo != null
									&& empMobileNo != ""
									&& empMobileNo.trim().length() == 10
									&& !empMobileNo.startsWith("NA")
									&& (empMobileNo.startsWith("9")
											|| empMobileNo.startsWith("8") || empMobileNo
											.startsWith("7"))) {
								String empPreReqMsg = "Dear " + visitedPerson
										+ ", your meeting is scheduled with "
										+ visitorName + " on date "
										+ scheduledDate + " at time "
										+ scheduledTime + ".";
								new MsgMailCommunication()
										.addScheduledMessageClientServer(
												visitedPerson, dept,
												empMobileNo, empPreReqMsg, "",
												"Pending", "0", "VAM",
												scheduledDate, scheduledTime,
												connectionSpace);
								//System.out.println("empPreReqMsg>>>"+ empPreReqMsg);
							}
							// SMS to visitor.
							if (visitorMobileNo != null
									&& visitorMobileNo != ""
									&& visitorMobileNo.trim().length() == 10
									&& !visitorMobileNo.startsWith("NA")
									&& (visitorMobileNo.startsWith("9")
											|| visitorMobileNo.startsWith("8") || visitorMobileNo
											.startsWith("7"))) {
								String visitorPreReqMsg = "Dear "
										+ visitorName
										+ ", "
										+ visitedPerson
										+ " had scheduled a meeting at "
										+ locationName
										+ " on "
										+ scheduledTime
										+ ". Please reach on time on date "
										+ scheduledDate
										+ ". Show OTP "
										+ OTP
										+ " to security room. Thanks, Admin Team JBM.";
								new MsgMailCommunication()
										.addMessageClientServer(
												visitorMobileNo,
												visitorPreReqMsg, "",
												"Pending", "0", "VAM",
												connectionSpace);
								//System.out.println("visitorPreReqMsg>>>"+ visitorPreReqMsg);

							}
						} else if (getMail() == true) {
							// mail to visited person.
							if (emailid != null && !emailid.equals("")
									&& !emailid.equals("NA")) 
							{
								MailText mailtextobj = new MailText();
								String mailText = mailtextobj
										.getPreReqMailText(visitedPerson,
												visitorName, visitorCompany,
												locationName, scheduledDate,
												scheduledTime);
								new MsgMailCommunication().addMail(emailid,
										"Visitor PreRequest Mail", mailText,
										"", "Pending", "0", "", "VAM",
										connectionSpace);
							}
						}
						addActionMessage("Request Done Successfully.");
						// insert into Server database.
						status = objstatus.insertIntoTable(obj).get_return();
						returnResult = SUCCESS;
					}
					
				} else {
					addActionMessage("Oops There is some error in data insertion.");
				}
			} catch (Exception e) {
				log
						.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method <submitPreRequestDetails> of class "
										+ getClass(), e);
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String editPrerequestGrid() {

		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit")) {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("prerequest_details", wherClause,
						condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del")) {
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
			}
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method editPrerequestGrid of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String empLocationMapping() {
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			setMainHeaderName("Employee Location Map");
			setEmpLocMapViewProp("mapped_emp_location_mapping_configuration",
					"emp_location_mapping_configuration");

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <beforeVisitorEntryRecords()> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setEmpLocMapViewProp(String table1, String table2) {
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewEmplocmappingDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewEmplocmappingDetail.add(gpv);
		}
	}

	public String getEmpLocMappingDetails() {
		//System.out.println("emplocdatamapgate");
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase("")) {
			return LOGIN;
		}

		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from emp_location_map_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					connectionSpace);
			if (dataCount != null) {
				
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

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from emp_location_map_details as elmd ");
				List fieldNames = Configuration.getColomnList(
						"mapped_emp_location_mapping_configuration", accountID,
						connectionSpace, "emp_location_mapping_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty()) {
					for (Iterator iterator = fieldNames.iterator(); iterator
							.hasNext();) {

						String object = iterator.next().toString();
						if (object != null) {
							if (i < fieldNames.size() - 1) {
								if (object.toString().equalsIgnoreCase(
										"location")) {
									query.append(" loc.name , ");
									queryEnd
											.append(" left join location as loc on loc.id = elmd.location ");
								}

								if (object.toString().equalsIgnoreCase(
										"deptName")) {
									query.append(" dept.deptName , ");
									queryEnd
											.append(" left join department as dept on dept.id = elmd.deptName ");
								}
								if (object.toString().equalsIgnoreCase(
										"empName")) {
									query.append(" empb.empName , ");
									queryEnd
											.append(" left join employee_basic as empb on empb.id = elmd.empName ");
								}
								if (object.toString().equalsIgnoreCase("gate")) {
									query.append(" glocd.gate , ");
									queryEnd
											.append(" left join gate_location_details as glocd on glocd.id = elmd.gate ");
								} else {
									if (!object.toString().equalsIgnoreCase(
											"location")
											&& !object.toString()
													.equalsIgnoreCase(
															"deptName")
											&& !object
													.toString()
													.equalsIgnoreCase("empName")
											&& !object.toString()
													.equalsIgnoreCase("gate")) {
										query.append("elmd."
												+ object.toString() + ",");
									}
								}
							} else {
								if (object.toString().equalsIgnoreCase(
										"location")) {
									query.append(" loc.name ");
									queryEnd
											.append(" left join location as loc on loc.id = elmd.location ");
								} else {
									query.append("elmd." + object.toString());
								}
							}
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					//System.out.println("Querry>EmpLocMapping>>"+ query.toString());
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase("")) {
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq")) {
                            if(getSearchField().equals("empName"))
                            {
                                query.append("empb."+getSearchField()+ " = '"
                                        + getSearchString() + "'");
                            }
                            else if(getSearchField().equals("location"))
                            {
                            //    String locid=CommonMethod.getLocationId(connectionSpace, getSearchString());
                                query.append("loc.name='"    +getSearchString()+ "'");
                            }
                            else if(getSearchField().equals("gate"))
                            {
                                query.append("glocd."+getSearchField()+ " = '"
                                        + getSearchString() + "'");
                            }
                            else{
                            query.append(" " + getSearchField() + " = '"
                                    + getSearchString() + "'");
                            }
                        }  else if (getSearchOper().equalsIgnoreCase("cn")) {
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

			//		query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due
					 * to configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames,
							"emp_location_map_details", connectionSpace);

					//System.out.println("Querry EmpLocMap>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty()) {
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) {
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {

									if (fieldNames.get(k).toString()
											.equalsIgnoreCase("orgnztnlevel")) {
										// need to work here for viewing the
										// organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString()
												.equalsIgnoreCase("2")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("3")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("4")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("5")) {
										}
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									} else {
										if (k == 0) {
											one.put(fieldNames.get(k)
													.toString(),
													(Integer) obdata[k]);
										} else {
											if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"date")) {
												one
														.put(
																fieldNames
																		.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"time")) {
												one.put(fieldNames.get(k)
														.toString(), (obdata[k]
														.toString().substring(
														0, 5)));
											} else {
												one.put(fieldNames.get(k)
														.toString(), DateUtil
														.makeTitle(obdata[k]
																.toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setEmplocgatemapdata(listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getEmpLocMappingDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}

	public String empLocationGateMappingAdd() {
		String resString = ERROR;
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			// SessionFactory connectionSpace =
			// (SessionFactory)session.get("connectionSpace");
			deptDropDown = new ArrayList<ConfigurationUtilBean>();
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			locationListPreReqest = new HashMap<Integer, String>();
			locationListPreReqest = CommonMethod
					.allLocationList(connectionSpace);
			visitorfields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData(
							"mapped_emp_location_mapping_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"emp_location_mapping_configuration");
			if (group != null) {
				for (GridDataPropertyView gdp : group) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						deptDropDown.add(obj);
					}
					if (!gdp.getColType().trim().equalsIgnoreCase("Date")
							&& !gdp.getColType().trim()
									.equalsIgnoreCase("Time")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						// visitordatetimelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("deptName")) {
						deptName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExist = "true";
						else
							deptNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("empName")) {
						empName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							empNameExist = "true";
						else
							empNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("location")) {
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							locationNameExit = "true";
						else
							locationNameExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("gate")) {
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							gateNameExist = "true";
						else
							gateNameExist = "false";
					}
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase("ip")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_time")
							&& !gdp.getColomnName().equalsIgnoreCase("accept")
							&& !gdp.getColomnName().equalsIgnoreCase("reject")
							&& !gdp.getColomnName().equalsIgnoreCase("status")
							&& !gdp.getColomnName().equalsIgnoreCase("image")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"db_transfer")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_date")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_time")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("time_out")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_time")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_after")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						visitorfields.add(obj);
					}
				}
				resString = SUCCESS;
			}

		} catch (Exception e) {
			addActionError("There Is Problem to Add EmpLocationGateMappingAdd!");
			log
					.error(
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

	public String getGateDetails() {
		String resultString = ERROR;
		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				resultString = LOGIN;
			}
			gatenamelist = new HashMap<Integer, String>();
			gatenamelist = CommonMethod.allMappedGateList(connectionSpace, id);

			if (gatenamelist != null) {
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : gatenamelist.entrySet()) {

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e) {
			addActionError("There Is Problem to Load Gate Name!");
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getGateDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}

	public String submitEmpLocGateMapDetail() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				// SessionFactory connectionSpace = (SessionFactory)
				// session.get("connectionSpace");
				// String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<GridDataPropertyView> emplocgatemapData = Configuration
						.getConfigurationData(
								"mapped_emp_location_mapping_configuration",
								accountID, connectionSpace, "", 0,
								"table_name",
								"emp_location_mapping_configuration");

				if (emplocgatemapData != null && emplocgatemapData.size() > 0) {
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : emplocgatemapData) {
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						//System.out.println("gdp.getColomnName()>>"+ gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("emp_location_map_details", tableColume,
							connectionSpace);
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
							if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null) {
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

					//System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("emp_location_map_details",
							insertData, connectionSpace);
					if (status) {
						addActionMessage("Data Saved Successfully!!!");
						returnResult = SUCCESS;
					}
				} else {
					addActionMessage("There is some error in data insertion!!!!");
				}
			} catch (Exception e) {
				log
						.error(
								"Date : "
										+ DateUtil.getCurrentDateIndianFormat()
										+ " Time: "
										+ DateUtil.getCurrentTime()
										+ " "
										+ "Problem in Over2Cloud  method <submitEmpLocGateMapDetail> of class "
										+ getClass(), e);
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else {
			returnResult = LOGIN;
		}
		return returnResult;
	}

 public String modifyEmplocGateMapGrid() {
        try {
            if (userName == null || userName.equalsIgnoreCase(""))
                return LOGIN;
            if (getOper().equalsIgnoreCase("edit")) {
                CommonOperInterface cbt = new CommonConFactory()
                        .createInterface();
                Map<String, Object> wherClause = new HashMap<String, Object>();
                Map<String, Object> condtnBlock = new HashMap<String, Object>();
                List<String> requestParameterNames = Collections
                        .list((Enumeration<String>) request.getParameterNames());
                Collections.sort(requestParameterNames);
                Iterator it = requestParameterNames.iterator();
                while (it.hasNext()) {
                    String Parmname = it.next().toString();
                    String paramValue = request.getParameter(Parmname);
                    if (paramValue != null && !paramValue.equalsIgnoreCase("")
                            && Parmname != null
                            && !Parmname.equalsIgnoreCase("")
                            && !Parmname.equalsIgnoreCase("id")
                            && !Parmname.equalsIgnoreCase("oper")
                            && !Parmname.equalsIgnoreCase("rowid"))
                      //  System.out.println("Param Name Is>>>>"+Parmname+"  Param value Is"+paramValue );
                        if(Parmname.equalsIgnoreCase("location"))
                        {
                            String loc=request.getParameter("location");
                            String locationId= CommonMethod.getLocationId(connectionSpace, loc);
                           // System.out.println(locationId);
                            wherClause.put(Parmname,locationId);
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
                       
                        else if(Parmname.equalsIgnoreCase("deptName"))
                        {
                            String dep=request.getParameter("deptName");
                            String dept=CommonMethod.getDeptId(connectionSpace, dep);
                            wherClause.put(Parmname,dept);   
                        }

                        else if(Parmname.equalsIgnoreCase("gate"))
                        {
                            String gate=request.getParameter("gate");
                            String loc=request.getParameter("location");
                            String locationId= CommonMethod.getLocationId(connectionSpace, loc);
                            String str="Select id from gate_location_details where gate='"+gate+"' and location='"+locationId+"'";
                            System.out.println(">>>>>>>>"+str.toString());
                            String gateid=null;
                            List gateData =new createTable().executeAllSelectQuery(str.toString(), connectionSpace);
                            if(gateData!=null && gateData.size()>0)
                            {
                                gateid = gateData.get(0).toString();
                            }
                            wherClause.put(Parmname,gateid);   
                        }
                        else{
                            wherClause.put(Parmname, paramValue);
                        }
                }
                if(wherClause.containsKey("oper"))
                {
                    wherClause.remove("oper");
                }
                condtnBlock.put("id", getId());
                cbt.updateTableColomn("emp_location_map_details", wherClause,
                condtnBlock, connectionSpace);
            } else if (getOper().equalsIgnoreCase("del")) {
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
                cbt.deleteAllRecordForId("emp_location_map_details", "id",
                        condtIds.toString(), connectionSpace);
            }
        } catch (Exception e) {
            log
                    .error(
                            "Date : "
                                    + DateUtil.getCurrentDateIndianFormat()
                                    + " Time: "
                                    + DateUtil.getCurrentTime()
                                    + " "
                                    + "Problem in Over2Cloud  method modifyEmplocGateMapGrid of class "
                                    + getClass(), e);
            e.printStackTrace();
        }
        return SUCCESS;
    }

	public String beforeVehicleEntryRecords() {

		try {
			if (userName == null || userName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allVhDepartmentList(connectionSpace);
			purposelist = new HashMap<Integer, String>();
			purposelist = CommonMethod.allVehiclePurpose(connectionSpace);
			setMainHeaderName("Vehicle Activity");
			setVehicleViewProp("mapped_vehicle_entry_configuration",
					"vehicle_entry_configuration");
			String query=null;
			//count vehicle activity
			query="select count(*) from vehicle_entry_details";
		    countTotal=CommonMethod.countRecord(query,connectionSpace);
			query="select count(*) from vehicle_entry_details where status = 0";
			countIn=CommonMethod.countRecord(query,connectionSpace);
			query="select count(*) from vehicle_entry_details where status = 1";
			countOut=CommonMethod.countRecord(query,connectionSpace);
			
			/*Exit vehicle**************/
			if(vehicle_number != null && !vehicle_number.equalsIgnoreCase(""))
			{
			   ExitVehicle(vehicle_number);
			}

		  } catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <beforeVehicleEntryRecords()> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setVehicleViewProp(String table1, String table2) {
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVehicleDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewVehicleDetail.add(gpv);
		}
	}
/*************************************Vehicle exit**************************************************/
	  public void ExitVehicle(String vehicleNumber)
	  {
			String resString = ERROR;
			String query = null;
			try 
			{
			String updatetimeout = DateUtil.getCurrentTime();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
				if(vehicleNumber !=null && !vehicleNumber.equalsIgnoreCase(""))
				{
					 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where vehicle_number like '"+"%"+vehicleNumber+"'";
				}
				/*if(getDrivername()!=null && !getDrivername().equalsIgnoreCase(""))
				{
					 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where driver_name = '"+getDrivername()+"'";
				}
				if(getDrivermobile()!=null && !getDrivermobile().equalsIgnoreCase(""))
				{
					 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where driver_mobile = '"+getDrivermobile().trim()+"'";
				}*/
				//update on local.
				int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
				//cbt.executeAllSelectQuery(query, connectionSpace);
				PreRequestserviceStub updateTable=new PreRequestserviceStub();
				UpdateTable obj=new UpdateTable();
				obj.setTableQuery(query);
				
			/*if(localstatus>0)
			{
			addActionMessage("Vehicle Exited Successfully.");
			resString = SUCCESS;
			}else
			{
				addActionMessage("Some Problem In Exit.");
				resString = ERROR;
			}*/
			//update on Server.
			boolean status = updateTable.updateTable(obj).get_return();
			} catch (Exception e) {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method <ExitVehicle()> of class "+getClass(), e);
				e.printStackTrace();
			}
			
			//return resString;
	  }
	 public String getVehicleDetails() 
	 {
		 String resString = ERROR;
		 if (userName == null || userName.equalsIgnoreCase("")) {
			return LOGIN;
		 }
		 try {
			  CommonOperInterface cbt = new CommonConFactory().createInterface();
			  StringBuilder query1 = new StringBuilder("");
			  query1.append("select count(*) from vehicle_entry_details");
			  List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					                                                       connectionSpace);
			 if (dataCount != null) 
			    {
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from vehicle_entry_details as ved ");
				List fieldNames = Configuration.getColomnList(
						"mapped_vehicle_entry_configuration", accountID,
						connectionSpace, "vehicle_entry_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty()) 
				   {
					 for (Iterator iterator = fieldNames.iterator(); iterator
							                                                  .hasNext();) 
					     {
                           String object = iterator.next().toString();
					 	   if (object != null)
					 	   {
							   if (object.equalsIgnoreCase("deptName")) {
								    queryTemp.append(",dpt.jlocation_name");
								    queryEnd
										.append(" left join vh_location_jbm as dpt on ved.deptName = dpt.id ");
							      } else if (object.equalsIgnoreCase("purpose"))
							      {
								    queryTemp.append(",pd.purpose");
								    queryEnd
										.append(" left join purpose_admin as pd on ved.purpose = pd.id ");
							      } else 
							      {
								    queryTemp.append("," + "ved."
										         + object.toString());
							      }
						   }
					    }
                      	query.append(queryTemp.toString().substring(1));
					    query.append(" " + queryEnd.toString());
					    if (getDeptName() != null && !getDeptName().equals("-1"))
					       {
						        query.append(" " + "where ved.deptName="+ getDeptName());
						   } 
					   else if (getVehlocation() != null && !getVehlocation().equals("-1"))
						   {
						        query.append(" " + "where ved.location="+ getVehlocation());
						    	
						   } 
					   else if (getPurpose() != null && !getPurpose().equals("-1")) 
					       {
						        query.append(" " + "where ved.purpose=" + getPurpose());
					       }
					    else if (vehicle_number != null && !vehicle_number.equals("") && !vehicle_number.equals("-1")) 
				        {  query.append(" "+"where ");
					    	if (fieldNames != null && !fieldNames.isEmpty()) 
							   {  int k=0;
								 for (Iterator iterator = fieldNames.iterator(); iterator
										                                                  .hasNext();) 
								     {  i++;
			                           String object = iterator.next().toString();
								 	   if (object != null)
								 	   {
								 		 /* query.append("ved."+object+"=" + "'"+vehicle_number+"'");
								 		*/  query.append("ved."+object+" LIKE " + "'"+vehicle_number+"%"+"'");

								 	   } 
								 	   if(k <fieldNames.size()-1)
								 	   {
								 		  query.append(" OR ");
								 		  
								 	   }
								 	   else{
								 		   query.append(" ");
								 	   }k++;
								 	 }
							   }
					    } 
					    else if (driver_mobile != null && !driver_mobile.equals("-1") && !driver_mobile.equals("")) 
				        {
					        query.append(" " + "where ved.driver_mobile=" + driver_mobile);
				        } 
					    if (getFrom_date() != null && !getFrom_date().equals("")
							&& getTo_date() != null && !getTo_date().equals(""))
					      {
						    setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
						    setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
						    query.append(" where ved.entry_date >= '" + getFrom_date()
						                                                      + "' and ved.entry_date <= '" + getTo_date() + "'");
					      }
					    if(selectedId != null && !selectedId.equalsIgnoreCase("-1"))
					       {  if(!selectedId.equalsIgnoreCase("2"))
					            {
						         if (getFrom_date() != null && !getFrom_date().equals(""))
						    	    query.append(" and " + "ved.status=" +selectedId+" ORDER BY entry_date ASC,entry_time ASC");
						         else if(selectedId.equalsIgnoreCase("1"))	 
						    	    query.append(" where " + "ved.status=" +selectedId+" ORDER BY entry_date DESC,out_time DESC");
						         
					             else if(selectedId.equalsIgnoreCase("0"))	 
							    	    query.append(" where " + "ved.status=" +selectedId+" ORDER BY entry_date ASC,entry_time ASC");
					            }
					        }
					  
						 if (getSearchField() != null && getSearchString() != null
							                                                 && !getSearchField().equalsIgnoreCase("")
							                                                 && !getSearchString().equalsIgnoreCase("")) 
					      {
						   query.append(" where ");
						   // add search query based on the search operation
					 if (getSearchOper().equalsIgnoreCase("eq")) 
					      {
							query.append(" " + getSearchField() + " = '"
									                                    + getSearchString() + "'");
						  } 
					 else if (getSearchOper().equalsIgnoreCase("cn")) {
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
					/**
					 * **************************checking for colomn change due
					 * to configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames,
							                                   "vehicle_entry_details", connectionSpace);
					System.out.println("vehicle query***********************"+query.toString());
    				List data = cbt.executeAllSelectQuery(query.toString(),
							                                               connectionSpace);
    				
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty()) 
					{
						for (Iterator iterator = data.iterator(); iterator
								.hasNext();) 
						{
							Object[] obdata = (Object[]) iterator.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if (obdata[k] != null) {
                                        if (fieldNames.get(k).toString()
											.equalsIgnoreCase("orgnztnlevel")) {
										// need to work here for viewing the
										// organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString()
												.equalsIgnoreCase("2")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("3")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("4")) {
										} else if (obdata[k].toString()
												.equalsIgnoreCase("5")) {
										}
										one.put(fieldNames.get(k).toString(),
												obdata[k].toString());
									  } else {
										      if (k == 0) {
											   one.put(fieldNames.get(k)
													.toString(),
													(Integer) obdata[k]);
										} else {
											if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"date")) {
												one
														.put(
																fieldNames
																		.get(k)
																		.toString(),
																DateUtil
																		.convertDateToIndianFormat(obdata[k]
																				.toString()));
											} else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"time")) {
												one.put(fieldNames.get(k)
														.toString(), (obdata[k]
														.toString().substring(
														0, 5)));
											} 
											else if (fieldNames.get(k) != null
													&& fieldNames.get(k)
															.toString()
															.equalsIgnoreCase(
																	"entry_date")) {
												one.put(fieldNames.get(k)
														.toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											}
											else {
												if(fieldNames.get(k) != null
								                          && fieldNames.get(k).toString()
								                                    .equalsIgnoreCase("sr_number"))
								             {
										      one.put(fieldNames.get(k).toString(),
				                                    (obdata[k].toString().toUpperCase().substring(5, 10)));
								             }
									else{
									        one.put(fieldNames.get(k).toString(),
									                                    DateUtil.makeTitle(obdata[k].toString()));
									    }
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setVehicledatalist(listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getVehicleDetails> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return resString;

	}

	public String modifyVehicleEntryGrid() {
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit")) {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext()) {
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& Parmname != null
							&& !Parmname.equalsIgnoreCase("")
							&& !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper")
							&& !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("vehicle_entry_details", wherClause,
						condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del")) {
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
				cbt.deleteAllRecordForId("vehicle_entry_details", "id",
						condtIds.toString(), connectionSpace);
			}
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method modifyVehicleEntryGrid of class "
									+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * To get vehicle entry form fields.
	 */
	public String vehicleEntryAdd() 
	      {
		
		      String resString = ERROR;
		      try 
		      {
			   if (userName == null || userName.equalsIgnoreCase("")) 
			      {
				   return LOGIN;
			      }
			   if(vehicleExitStatus.equalsIgnoreCase("1"))
			   {
				   setMainHeaderName("Vehicle Exit"); 
			   } else
			   {
				   setMainHeaderName("Vehicle Entry"); 
			   }
			      
			      gatelistofloggedinUser = new HashMap<Integer, String>();
			      gatelistofloggedinUser = CommonMethod.allSelectedGateList(connectionSpace, userName);
				  Iterator iterator = gatelistofloggedinUser.entrySet().iterator();
	//comment by Azad 22oct		 
				  /*  while (iterator.hasNext()) 
			           {
				        Map.Entry mapEntry = (Map.Entry) iterator.next();
				        if (mapEntry.getValue().equals("G1") && mapEntry.getValue() != null && !mapEntry.getValue().equals(""))
				           {                         
						      getFormFieldsForIn();
					          resString = SUCCESS;
				           } 
				        else 
				           {
					          dropdownformaterial = true;
					          resString = SUCCESS;
				           }*/
				       if (getVehiclestatus() != null && getVehiclestatus().equalsIgnoreCase("1") && !getVehiclestatus().equals(""))
						  {
					         getFieldsForINOut();
					         dropdownformaterial = false;
					         resString = SUCCESS;
				          } 
				       else if (getVehiclestatus() != null
							                             && getVehiclestatus().equalsIgnoreCase("2")
							                             && !getVehiclestatus().equals("")) 
					     {
						         vehicleFieldsWithoutMaterial();
						         dropdownformaterial = false;
						         resString = SUCCESS;
					     }
				       else if(getVehiclestatus() != null
	                             && getVehiclestatus().equalsIgnoreCase("0")
	                             && !getVehiclestatus().equals("")) 
			           {
				          dropdownformaterial = true;
				          resString = SUCCESS;
			           }
				     
		} catch (Exception e) {

			e.printStackTrace();
		}
		return resString;
	}
	

	/**
	 * Method for Vehicle in Only.
	 */
	public void getFormFieldsForIn() {
		System.out.println("In G1 Gate>>");
		String prefixval = "VH";
		String subpre = null;
		CommonMethod srNumberObject = new CommonMethod();
		try {
			String vhtripnumber = srNumberObject
					.getVehicleTripNumber(connectionSpace);
			int tripn = Integer.parseInt(vhtripnumber);
			if (tripn <= 1000) {
				tripn = 1001;
			} else {
				tripn = tripn + 1;
			}
			String tripnumber = Integer.toString(tripn);

			String vehiclernumber = srNumberObject
					.getVehicleSerialNumber(connectionSpace);
			//System.out.println(">>vhtripnumber>>" + vhtripnumber);
			int number = Integer.parseInt(vehiclernumber);
			//System.out.println("vehiclernumber>" + vehiclernumber);
			String vehicleSrNum = srNumberObject.getSeries(connectionSpace,
					prefixval);
			if (vehicleSrNum != null && !vehicleSrNum.equals("")) {
				String pre = vehicleSrNum.substring(0, 2);
				subpre = vehicleSrNum.substring(2, 5);
				String numberseries = vehicleSrNum.substring(5);
				//System.out.println("pre>" + pre + "subpre>" + subpre+ "numberseries>>" + numberseries);
			}
			if (number <= 1000) {
				number = 1001;
			} else {
				number = number + 1;
			}
			String strnumber = Integer.toString(number);

			String srNumber = prefixval + subpre + strnumber;
			vehicledatetimelist = new ArrayList<ConfigurationUtilBean>();
			deptDropDown = new ArrayList<ConfigurationUtilBean>();
			// departmentlist = new HashMap<Integer, String>();
			jlocationlist = new HashMap<Integer, String>();
			jlocationlist = CommonMethod.allJLocationList(connectionSpace);
			// departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			locationListPreReqest = new HashMap<Integer, String>();
			locationListPreReqest = CommonMethod
					.allLocationList(connectionSpace);
			purposelist = CommonMethod.allVehiclePurpose(connectionSpace);
			vehiclefields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_vehicle_entry_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"vehicle_entry_configuration");
			if (group != null) {
				for (GridDataPropertyView gdp : group) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						deptDropDown.add(obj);
					}
					if (!gdp.getColType().trim().equalsIgnoreCase("Date")
							&& !gdp.getColType().trim()
									.equalsIgnoreCase("Time")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						vehicledatetimelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("deptName")) {
						deptName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExist = "true";
						else
							deptNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("location")) {
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							locationNameExit = "true";
						else
							locationNameExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"entry_date")) {
						entry_date = gdp.getHeadingName();
						datevalue = DateUtil.getCurrentDateIndianFormat();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vehicleEntrydateExist = "true";
						else
							vehicleEntrydateExist = "false";

					} else if (gdp.getColomnName().equalsIgnoreCase("purpose")) {
						purpose = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							purposeExist = "true";
						else
							purposeExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"entry_time")) {
						entry_time = gdp.getHeadingName();
						timevalue = DateUtil.getCurrentTime();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vehicleEntrytimeExist = "true";
						else
							vehicleEntrytimeExist = "false";
					}

					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("out_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_time")
							&& !gdp.getColomnName().equalsIgnoreCase("image")
							&& !gdp.getColomnName().equalsIgnoreCase("status")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"db_transfer")
							&& !gdp.getColomnName().equalsIgnoreCase("ip")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_date")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_after")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"entry_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"invoce_no")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"no_of_bill")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"destination")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"material_details")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("quantity")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_after")
							&& !gdp.getColomnName().equalsIgnoreCase("barcode")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						vehiclefields.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("sr_number")) {
						setSr_number(srNumber);
					}
					if (gdp.getColomnName().equalsIgnoreCase("trip_no")) {
						setTrip_no(tripnumber);
					}
				}
			}
			LinkedList<ConfigurationUtilBean> vehiclefieldsTemp = new LinkedList<ConfigurationUtilBean>();
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("trip_no")) {
						vehiclefieldsTemp.add(vd);
						setHidetripno("true");
					}
				}for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("sr_number")) {
						vehiclefieldsTemp.add(vd);
						setHidesrno("true");
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vehicle_number")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("driver_mobile")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("driver_name")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vehicle_model")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vehicle_owner")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vh_owner_mob")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("invoce_no")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("material_details")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("quantity")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("no_of_bill")) {
						vehiclefieldsTemp.add(vd);
					}
				}
			vehiclefields = vehiclefieldsTemp;

		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getFormFieldsForIn> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
	}
	

	public void vehicleFieldsWithoutMaterial() {
		String prefixval = "VH";
		String subpre = null;
		CommonMethod srNumberObject = new CommonMethod();
		//System.out.println("In vehicleFieldsWithoutMaterial>>>>>>.");
		try {
			String vhtripnumber = srNumberObject
					.getVehicleTripNumber(connectionSpace);
			int tripn = Integer.parseInt(vhtripnumber);
			if (tripn <= 1000) {
				tripn = 1001;
			} else {
				tripn = tripn + 1;
			}
			String tripnumber = Integer.toString(tripn);
			String vehiclernumber = srNumberObject
					.getVehicleSerialNumber(connectionSpace);
			int number = Integer.parseInt(vehiclernumber);
			//System.out.println("vehiclernumber>" + vehiclernumber);
			String vehicleSrNum = srNumberObject.getSeries(connectionSpace,
					prefixval);
			if (vehicleSrNum != null && !vehicleSrNum.equals("")) {
				String pre = vehicleSrNum.substring(0, 2);
				subpre = vehicleSrNum.substring(2, 5);
				String numberseries = vehicleSrNum.substring(5);
				//System.out.println("pre>" + pre + "subpre>" + subpre+ "numberseries>>" + numberseries);
			}
			if (number <= 1000) {
				number = 1001;
			} else {
				number = number + 1;
			}
			String strnumber = Integer.toString(number);

			String srNumber = prefixval + subpre + strnumber;
			vehicledatetimelist = new ArrayList<ConfigurationUtilBean>();
			deptDropDown = new ArrayList<ConfigurationUtilBean>();
			// departmentlist = new HashMap<Integer, String>();
			// departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			setMainHeaderName("Without Material");
			jlocationlist = new HashMap<Integer, String>();
			jlocationlist = CommonMethod.allJLocationList(connectionSpace);
			locationListPreReqest = new HashMap<Integer, String>();
			locationListPreReqest = CommonMethod
					.allLocationList(connectionSpace);
			purposelist = CommonMethod.allVehiclePurpose(connectionSpace);
			vehiclefields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_vehicle_entry_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"vehicle_entry_configuration");
			if (group != null) {
				for (GridDataPropertyView gdp : group) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						deptDropDown.add(obj);
					}
					if (!gdp.getColType().trim().equalsIgnoreCase("Date")
							&& !gdp.getColType().trim()
									.equalsIgnoreCase("Time")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						vehicledatetimelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("deptName")) {
						deptName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExist = "true";
						else
							deptNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("location")) {
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							locationNameExit = "true";
						else
							locationNameExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"entry_date")) {
						entry_date = gdp.getHeadingName();
						datevalue = DateUtil.getCurrentDateIndianFormat();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vehicleEntrydateExist = "true";
						else
							vehicleEntrydateExist = "false";

					} else if (gdp.getColomnName().equalsIgnoreCase("purpose")) {
						purpose = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							purposeExist = "true";
						else
							purposeExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"entry_time")) {
						entry_time = gdp.getHeadingName();
						timevalue = DateUtil.getCurrentTime();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vehicleEntrytimeExist = "true";
						else
							vehicleEntrytimeExist = "false";
					}

					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_time")
							&& !gdp.getColomnName().equalsIgnoreCase("image")
							&& !gdp.getColomnName().equalsIgnoreCase("status")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("out_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"db_transfer")
							&& !gdp.getColomnName().equalsIgnoreCase("ip")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_date")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_after")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"invoce_no")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"no_of_bill")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"destination")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"material_details")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("quantity")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_after")
							&& !gdp.getColomnName().equalsIgnoreCase("barcode")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						vehiclefields.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("sr_number")) {
						setSr_number(srNumber);
					}
					if (gdp.getColomnName().equalsIgnoreCase("trip_no")) {
						setTrip_no(tripnumber);
					}
				}
			}
			LinkedList<ConfigurationUtilBean> vehiclefieldsTemp = new LinkedList<ConfigurationUtilBean>();
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("sr_number")) {
						vehiclefieldsTemp.add(vd);
						setHidesrno("true");
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vehicle_number")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("driver_mobile")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("driver_name")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vehicle_model")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("trip_no")) {
						vehiclefieldsTemp.add(vd);
						setHidetripno("true");
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vehicle_owner")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("vh_owner_mob")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("invoce_no")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("material_details")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("quantity")) {
						vehiclefieldsTemp.add(vd);
					}
				}
				for (ConfigurationUtilBean vd : vehiclefields) {
					if (vd.getKey().equalsIgnoreCase("no_of_bill")) {
						vehiclefieldsTemp.add(vd);
					}
				}

			vehiclefields = vehiclefieldsTemp;
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <getFormFieldsForIn> of class "
									+ getClass(), e);
			e.printStackTrace();
		}

	}
	

	/**
	 * Method for Vehicle for in and out both.
	 */
	public void getFieldsForINOut() 
	{
		String prefixval = "VH";
		String subpre = null;
		CommonMethod srNumberObject = new CommonMethod();
		try {
			 String vhtripnumber = srNumberObject
					                           .getVehicleTripNumber(connectionSpace);
			 int tripn = Integer.parseInt(vhtripnumber);
			 if (tripn <= 1000) 
			   {
				   tripn = 1001;
			   } 
			 else 
			  {
				tripn = tripn + 1;
			  }
			 String tripnumber = Integer.toString(tripn);

			 String vehiclernumber = srNumberObject
					                              .getVehicleSerialNumber(connectionSpace);
			 int number = Integer.parseInt(vehiclernumber);
			 String vehicleSrNum = srNumberObject.getSeries(connectionSpace,
					                                                        prefixval);
			 if (vehicleSrNum != null && !vehicleSrNum.equals("")) 
			    {
				  String pre = vehicleSrNum.substring(0, 2);
				  subpre = vehicleSrNum.substring(2, 5);
				  String numberseries = vehicleSrNum.substring(5);
			    }
			 if (number <= 1000) 
			    {
				  number = 1001;
			    } else 
			    {
				number = number + 1;
			    }
			     String strnumber = Integer.toString(number);
              	 String srNumber = prefixval + subpre + strnumber;
			
              	 vehicledatetimelist = new ArrayList<ConfigurationUtilBean>();
			     deptDropDown = new ArrayList<ConfigurationUtilBean>();
			     setMainHeaderName("With Material");
			     // departmentlist = new HashMap<Integer, String>();
			     // departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			    jlocationlist = new HashMap<Integer, String>();
			    jlocationlist = CommonMethod.allJLocationList(connectionSpace);
			    locationListPreReqest = new HashMap<Integer, String>();
			    locationListPreReqest = CommonMethod
					                                .allLocationList(connectionSpace);
			    purposelist = CommonMethod.allVehiclePurpose(connectionSpace);
			    vehiclefields = new LinkedList<ConfigurationUtilBean>();
			    List<GridDataPropertyView> group = Configuration
					                                          .getConfigurationData("mapped_vehicle_entry_configuration",
							                                                                           accountID, connectionSpace, "", 0, "table_name",
							                                                                           "vehicle_entry_configuration");
			    if (group != null)
			       {
				    for (GridDataPropertyView gdp : group) 
				        {
					     ConfigurationUtilBean obj = new ConfigurationUtilBean();
					     if (gdp.getColType().trim().equalsIgnoreCase("D")
							              && !gdp.getColomnName()
									                            .equalsIgnoreCase("userName")
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
						         } 
						         else {
							      obj.setMandatory(false);
						              }
						     deptDropDown.add(obj);
					     }
					    if (!gdp.getColType().trim().equalsIgnoreCase("Date")
							         && !gdp.getColType().trim()
									                            .equalsIgnoreCase("Time")
							         && !gdp.getColomnName().equalsIgnoreCase("date")
							         && !gdp.getColomnName().equalsIgnoreCase("time"))
					    {
						      obj.setValue(gdp.getHeadingName());
						      obj.setKey(gdp.getColomnName());
						      obj.setValidationType(gdp.getValidationType());
						      obj.setColType(gdp.getColType());
						      if (gdp.getMandatroy().toString().equals("1")) {
							      obj.setMandatory(true);
						         } else {
							      obj.setMandatory(false);
						          }
						   vehicledatetimelist.add(obj);
					   }
					if (gdp.getColomnName().equalsIgnoreCase("deptName")) {
						deptName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExist = "true";
						else
							deptNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("location")) {
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							locationNameExit = "true";
						else
							locationNameExit = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"entry_date")) {
						entry_date = gdp.getHeadingName();
						datevalue = DateUtil.getCurrentDateIndianFormat();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vehicleEntrydateExist = "true";
						else
							vehicleEntrydateExist = "false";

					} else if (gdp.getColomnName().equalsIgnoreCase("purpose")) {
						purpose = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							purposeExist = "true";
						else
							purposeExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase(
							"entry_time")) {
						entry_time = gdp.getHeadingName();
						timevalue = DateUtil.getCurrentTime();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vehicleEntrytimeExist = "true";
						else
							vehicleEntrytimeExist = "false";
					}

					if (gdp.getColType().trim().equalsIgnoreCase("T")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("out_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_time")
							&& !gdp.getColomnName().equalsIgnoreCase("image")
							&& !gdp.getColomnName().equalsIgnoreCase("status")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"db_transfer")
							&& !gdp.getColomnName().equalsIgnoreCase("ip")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"update_date")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"alert_after")
							&& !gdp.getColomnName().equalsIgnoreCase("barcode")) 
					 {
						        obj.setValue(gdp.getHeadingName());
						        obj.setKey(gdp.getColomnName());
						        obj.setValidationType(gdp.getValidationType());
						        obj.setColType(gdp.getColType());
						        if (gdp.getMandatroy().toString().equals("1")) 
						        {
							     obj.setMandatory(true);
						        } else {
							     obj.setMandatory(false);
						        }
						   vehiclefields.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("sr_number")) {
						setSr_number(srNumber);
					}
					if (gdp.getColomnName().equalsIgnoreCase("trip_no")) {
						setTrip_no(tripnumber);
					}
				}
			}
			LinkedList<ConfigurationUtilBean> vehiclefieldsTemp = new LinkedList<ConfigurationUtilBean>();
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("sr_number")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("driver_mobile")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("vehicle_number")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("trip_no")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("driver_name")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("vehicle_model")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("vehicle_owner")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("vh_owner_mob")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("invoce_no")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("material_details")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("quantity")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("no_of_bill")) {
					vehiclefieldsTemp.add(vd);
				}
			}
			for (ConfigurationUtilBean vd : vehiclefields) {
				if (vd.getKey().equalsIgnoreCase("destination")) {
					//vehiclefieldsTemp.add(vd);
					setDestinationto(vd.getValue());
				}
			}

			vehiclefields = vehiclefieldsTemp;
		} catch (Exception e) {
			log
					.error(
							"Date : "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " Time: "
									+ DateUtil.getCurrentTime()
									+ " "
									+ "Problem in Over2Cloud  method <vehicleEntryAdd> of class "
									+ getClass(), e);
			e.printStackTrace();
		}
	}

	public String vehicleEntry()
	{

		
	      String resString = ERROR;
	      try 
	      {
		   if (userName == null || userName.equalsIgnoreCase("")) 
		      {
			   return LOGIN;
		      }
		      setMainHeaderName("Vehicle Entry");
		              dropdownformaterial = true;
			          resString = SUCCESS;
		           
		   } catch (Exception e) {
             e.printStackTrace();
	                             }
		    return resString;
}
	public String submitVehicleDetail() 
	{   
    String returnResult = ERROR;
    String adminmobNumber = "9540043749";
    String emailid = "Rajendra.singh@jbm.co.in";
    String photoname;
    String path = request.getSession().getServletContext().getRealPath("/");
    List<String> Tablecolumename = new ArrayList<String>();
    List<String> Tablecolumevalue = new ArrayList<String>();
    boolean status = false;
    boolean serverstatus = false;
    boolean sessionFlag = ValidateSession.checkSession();
   if (sessionFlag)
      {
       try {
            FileInputStream filename = new FileInputStream(path+ "//visitorimagepath.txt");
            DataInputStream in = new DataInputStream(filename);
            readfilename = new BufferedReader(new InputStreamReader(in));
            photoname = readfilename.readLine();
            CommonOperInterface cbt = new CommonConFactory()
                                                            .createInterface();
            List<GridDataPropertyView> emplocgatemapData = Configuration
                                 .getConfigurationData(
                                         "mapped_vehicle_entry_configuration",
                                           accountID, connectionSpace, "", 0,
                                               "table_name", "vehicle_entry_configuration");

           if (emplocgatemapData != null && emplocgatemapData.size() > 0) 
              {
                          // create table query based on configuration
                   List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
                          // InsertDataTable ob = null;
                   List<TableColumes> tableColume = new ArrayList<TableColumes>();
                   for (GridDataPropertyView gdp : emplocgatemapData)
                       {
                        TableColumes ob1 = new TableColumes();
                            ob1 = new TableColumes();
                            ob1.setColumnname(gdp.getColomnName());
                            ob1.setDatatype("varchar(255)");
                            ob1.setConstraint("default NULL");
                            tableColume.add(ob1);
                        }
                        cbt.createTable22("vehicle_entry_details", tableColume,
                                                                                connectionSpace);
                        List<String> requestParameterNames = Collections
                                                           .list((Enumeration<String>) request
                                                                                               .getParameterNames());
               
                        if (requestParameterNames != null
                                                          && requestParameterNames.size() > 0) 
                           {
                       	  
                              Collections.sort(requestParameterNames);
                              Iterator it = requestParameterNames.iterator();
                              while (it.hasNext()) 
                                    {
                                      String parmName = it.next().toString();
                                      String paramValue = request.getParameter(parmName);
                                      if (paramValue == null && paramValue.equals("") && paramValue.equalsIgnoreCase("-1")) 
                                         {
                                          paramValue = "NA";
                                         }
                                      if (parmName.equalsIgnoreCase("location")) {
                                             //   locname = CommonMethod.getLocationOnId(connectionSpace, paramValue);
                                                                                        
                                                locname = paramValue;
                                         }
                                      if (parmName.equalsIgnoreCase("deptName")) {
                                                dept = CommonMethod.getLocationJBMForId(
                                                                                        connectionSpace, paramValue);
                                          }
                                      if (parmName.equalsIgnoreCase("sr_number")) {
                                                sr_number = paramValue;
                                          }
                                      if (parmName.equalsIgnoreCase("driver_name")) {
                                                driver_name = paramValue;
                                          }
                                      if (parmName.equalsIgnoreCase("driver_mobile")) {
                                                driver_mobile = paramValue;
                                          }
                                      if (parmName.equalsIgnoreCase("vehicle_owner")) {
                                   	         vehicle_owner = paramValue;
                                          }
                                     if (parmName.equalsIgnoreCase("vh_owner_mob")) {
                                   	         vh_owner_mob = paramValue;
                                          }
                                     if (parmName.equalsIgnoreCase("material_details")) {
                                   	         material_details = paramValue;
                                         }
                                    if (parmName.equalsIgnoreCase("quantity")) {
                                   	         quantity = paramValue;
                                         }
                                    if (parmName.equalsIgnoreCase("no_of_bill")) {
                                   	         no_of_bill = paramValue;
                                   	        
                                         }
                                    
                                      
                                    if (parmName.equalsIgnoreCase("vehicle_model")) {
                                               vehicle_model = paramValue;
                                          }
                                    if (parmName.equalsIgnoreCase("vehicle_number")) {
                                               vehicle_number = paramValue;
                                               }
                                    if (parmName.equalsIgnoreCase("entry_date")) {
                                                              paramValue = DateUtil.convertDateToUSFormat(paramValue);
                                                                                                 entry_date = paramValue;
                                                          }
                                    if (parmName.equalsIgnoreCase("entry_time")) {
                                                          entry_time = paramValue;
                                                            }
                                    if (parmName.equalsIgnoreCase("purpose")) 
                                          {
                                    	    
                                               purpose = CommonMethod.fetchPurpose(paramValue, connectionSpace);
                                    	    if(purpose == null || purpose.equalsIgnoreCase(""))
                                    	       purpose="NA";
                           	              }
                                     if (paramValue != null
                                                              && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1")
                                                                                               && parmName != null && !parmName.equalsIgnoreCase("vehicleExitStatus"))
                                       	{
                                          Tablecolumename.add(parmName);
                                          Tablecolumevalue.add(paramValue);
                                         }
                                      	 
                                    }
                                     if(photoname != null)
                                    {  Tablecolumename.add("image");
                                       Tablecolumevalue.add(photoname); 
                                    }
                       }
/**
* Code to insert data on server database.
*/
PreRequestserviceStub objstatus = new PreRequestserviceStub();
InsertIntoTable obj = new InsertIntoTable();
Tablecolumename.add("userName");
Tablecolumevalue.add(userName);
Tablecolumename.add("date");
Tablecolumevalue.add(DateUtil.getCurrentDateUSFormat());
Tablecolumename.add("time");
Tablecolumevalue.add(DateUtil.getCurrentTime());
StringBuilder createTableQuery;

if(getVehicleExitStatus().equalsIgnoreCase("1"))
{ createTableQuery = new StringBuilder(
  "INSERT INTO " + "vehicle_exit_withmaterial" + " (");
}
else
{
createTableQuery = new StringBuilder(
			"INSERT INTO " + "vehicle_entry_details" + " (");	
}
int i = 1;
// append Column
for (String h : Tablecolumename) {
if (i < Tablecolumename.size())
createTableQuery.append(h + ", ");
else
createTableQuery.append(h + ")");
i++;
}

createTableQuery.append(" VALUES (");
i = 1;
for (String h : Tablecolumevalue) {
if (i < Tablecolumevalue.size())
createTableQuery.append("'" + h + "', ");
else
createTableQuery.append("'" + h + "')");
i++;
}
createTableQuery.append(" ;");
//System.out.println(">>>>Vehicle>>>" + createTableQuery);
obj.setCreateTableQuery(createTableQuery.toString());
// insert into local database.
boolean maxId = cbt.insertIntoTableFlag(createTableQuery
.toString(), connectionSpace);
// insert into Server database.
try
{
status = objstatus.insertIntoTable(obj).get_return();
} catch (Exception e) {
e.printStackTrace();
}finally
{
status = true;
}
/*Code For Bar Code*/
String filePath = request.getSession().getServletContext().getRealPath("/");
File TTFfilePath = new File(filePath + "/images/", "3of9.TTF");
  if(TTFfilePath!=null)
{
String saveImgPath = request.getSession().getServletContext().getRealPath("/images/barCodeImage/");
if(saveImgPath!=null)
{
StringBuffer buffer = new StringBuffer();
for (int m = 0; m < 10 - String.valueOf(maxId).length(); m++)
{
buffer.append("0");
}
buffer.append(String.valueOf(maxId));
int width, height;
String format = new String("gif");
width = 250;
height = 102;
BufferedImage bufimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics graphicsobj = bufimg.createGraphics();
InputStream fin = new FileInputStream(TTFfilePath);
Font font = Font.createFont(Font.TRUETYPE_FONT, fin);
Font font1 = font.deriveFont(30f);
graphicsobj.setFont(font1);
graphicsobj.setFont(font.getFont("3 of 9 Barcode"));
graphicsobj.setColor(Color.WHITE);

graphicsobj.fillRect(1, 1, 248, 150);
graphicsobj.setColor(Color.BLACK);
((Graphics2D) graphicsobj).drawString(buffer.toString(), 40, 60);
Font font2 = new Font("serif", Font.PLAIN, 20);
graphicsobj.setFont(font2);
graphicsobj.drawString("Vis S/N : " + sr_number, 30, 50);
File saveFile = new File(saveImgPath, String.valueOf(maxId) + ".gif");
System.out.println("Save Image Path"+saveImgPath);
ImageIO.write(bufimg, format, saveFile);
vpassId=String.valueOf(maxId);
}
}

/*
* System.out.println("insertData:" + insertData.size());
* status = cbt.insertIntoTable("vehicle_entry_details",
* insertData, connectionSpace);
*/
  // For image path.
String imgpath = request.getRealPath("/");
String photopath = path+photoname;
System.out.println("mailphoto>>"+photopath);
  
  //String imgpath = "E:/Workspace_08-03-2014/over2cloud/WebContent/visitorsnap/photoname";
   InetAddress ownIP=InetAddress.getLocalHost();
  //System.out.println("IP of my system is := "+ownIP.getHostAddress());
  StringBuilder url2 = new StringBuilder();
  url2.append("http://").append(ownIP.getHostAddress()).append(":8080/over2cloud/view/Over2Cloud/VAM/master/");
  String   url=url2.toString();
      if (maxId) 
         {
          if (adminmobNumber != null
               && adminmobNumber != ""
               && adminmobNumber.trim().length() == 10
               && !adminmobNumber.startsWith("NA")
               && (adminmobNumber.startsWith("9") ||adminmobNumber.startsWith("8") || adminmobNumber
                                                                                                     .startsWith("7")))
                 {
                 String empMsg = "Hi "
                                 + "Mr Rajendra"
                                 + ", "
                                 + vehicle_number
                                 + " from "
                                 + locname
                                 +" - "
                                 +"("
                                 +driver_name
                                 + " & "
                                 + driver_mobile
                                 + ") is in for "
                                 + purpose
                                 +".";
                   new MsgMailCommunication().addMessageClientServer(adminmobNumber, empMsg, "",
                                                                                               "Pending", "0", "VAM", connectionSpace);
                     System.out.println("empMsg>>>" + empMsg);
                 }
                 addActionMessage("Data Saved Successfully.");
                 returnResult = SUCCESS;
           }
           // mail to visited person.
           if (emailid != null && !emailid.equals("") && !emailid.equals("NA"))
              {
               MailText mailtextobj = new MailText();
               String mailText = mailtextobj.getVehicleMailText("Mr Rajendra",
                                                                 driver_name, "", driver_mobile, "", locname, url , "");
               //System.out.println(">mailText>>" + mailText);
               new MsgMailCommunication().addMail(emailid, "Vehicle Entry Mail",
               mailText, "", "Pending", "0", "", "VAM", connectionSpace);
               }
    } else {
addActionMessage("There is some error in data insertion.");
returnResult = ERROR;
}
} catch (Exception e) {
log
.error(
"Date : "
+ DateUtil.getCurrentDateIndianFormat()
+ " Time: "
+ DateUtil.getCurrentTime()
+ " "
+ "Problem in Over2Cloud  method <submitVehicleDetail> of class "
+ getClass(), e);
returnResult = ERROR;
e.printStackTrace();
}
} else {
returnResult = LOGIN;
}
return returnResult;}
	//for exit add
	public String vehicleExitAdd()
	{
		String returnString = vehicleEntryAdd();
		return returnString;
		
	}
	public String vehicleExitSubmit()
	{
	 
	    String resString = ERROR;
		String query = null;
		try 
		{
		submitVehicleDetail();
		String updatetimeout = DateUtil.getCurrentTime();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		   if(vehicle_number !=null && !vehicle_number.equalsIgnoreCase(""))
		     {
			 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where sr_number = '"+sr_number+"'";
		     }
			/*if(vehicle_number !=null && !vehicle_number.equalsIgnoreCase(""))
			{
				 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where vehicle_number = '"+vehicle_number+"'";
			}
			if(driver_mobile !=null && !driver_mobile.equalsIgnoreCase(""))
			{
				 query = "update vehicle_entry_details set status = 1, out_time = '"+updatetimeout+"' where driver_mobile = '"+driver_mobile.trim()+"'";
			}*/
			//update on local.
			int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
			
			//cbt.executeAllSelectQuery(query, connectionSpace);
			//comment by Azad 20 oct
			PreRequestserviceStub updateTable=new PreRequestserviceStub();
			UpdateTable obj=new UpdateTable();
			obj.setTableQuery(query);
			
		if(localstatus>0)
		{
		    addActionMessage("Vehicle Exited Successfully.");
		    resString = SUCCESS;
		}else
		{
			addActionMessage("Some Problem In Exit.");
			resString = ERROR;
		}
		//update on Server.
		//comment by Azad 20 oct
		boolean status = updateTable.updateTable(obj).get_return();
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <ExitVehicle()> of class "+getClass(), e);
			e.printStackTrace();
		}
		
		return resString;
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

	public List<GridDataPropertyView> getViewPurpose() {
		return viewPurpose;
	}

	public void setViewPurpose(List<GridDataPropertyView> viewPurpose) {
		this.viewPurpose = viewPurpose;
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

	public List<Object> getPurposeList() {
		return purposeList;
	}

	public void setPurposeList(List<Object> purposeList) {
		this.purposeList = purposeList;
	}

	public List<ConfigurationUtilBean> getPurposedata() {
		return purposedata;
	}

	public void setPurposedata(List<ConfigurationUtilBean> purposedata) {
		this.purposedata = purposedata;
	}

	public List<ConfigurationUtilBean> getPurposeDescriptionTextBox() {
		return purposeDescriptionTextBox;
	}

	public void setPurposeDescriptionTextBox(
			List<ConfigurationUtilBean> purposeDescriptionTextBox) {
		this.purposeDescriptionTextBox = purposeDescriptionTextBox;
	}

	public List<ConfigurationUtilBean> getPurposeDropDown() {
		return purposeDropDown;
	}

	public void setPurposeDropDown(List<ConfigurationUtilBean> purposeDropDown) {
		this.purposeDropDown = purposeDropDown;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;

	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<Object> getVender() {
		return vender;
	}

	public void setVender(List<Object> vender) {
		this.vender = vender;
	}

	public List<GridDataPropertyView> getViewFrontoffcDetail() {
		return viewFrontoffcDetail;
	}

	public void setViewFrontoffcDetail(
			List<GridDataPropertyView> viewFrontoffcDetail) {
		this.viewFrontoffcDetail = viewFrontoffcDetail;
	}

	public List<ConfigurationUtilBean> getFrontoffice() {
		return frontoffice;
	}

	public void setFrontoffice(List<ConfigurationUtilBean> frontoffice) {
		this.frontoffice = frontoffice;
	}

	public List<GridDataPropertyView> getViewGateLocation() {
		return viewGateLocation;
	}

	public void setViewGateLocation(List<GridDataPropertyView> viewGateLocation) {
		this.viewGateLocation = viewGateLocation;
	}

	public List<Object> getGatelocation() {
		return gatelocation;
	}

	public void setGatelocation(List<Object> gatelocation) {
		this.gatelocation = gatelocation;
	}

	public List<ConfigurationUtilBean> getGateData() {
		return gateData;
	}

	public void setGateData(List<ConfigurationUtilBean> gateData) {
		this.gateData = gateData;
	}

	public Map<Integer, String> getLocationlist() {
		return locationlist;
	}

	public void setLocationlist(Map<Integer, String> locationlist) {
		this.locationlist = locationlist;
	}

	public List<ConfigurationUtilBean> getLocationDropDown() {
		return locationDropDown;
	}

	public void setLocationDropDown(List<ConfigurationUtilBean> locationDropDown) {
		this.locationDropDown = locationDropDown;
	}

	public List<GridDataPropertyView> getViewVisitorDetail() {
		return viewVisitorDetail;
	}

	public void setViewVisitorDetail(
			List<GridDataPropertyView> viewVisitorDetail) {
		this.viewVisitorDetail = viewVisitorDetail;
	}

	public List<Object> getVisitorData() {
		return visitorData;
	}

	public void setVisitorData(List<Object> visitorData) {
		this.visitorData = visitorData;
	}

	public List<ConfigurationUtilBean> getVisitorfields() {
		return visitorfields;
	}

	public List<ConfigurationUtilBean> getDeptDropDown() {
		return deptDropDown;
	}

	public void setDeptDropDown(List<ConfigurationUtilBean> deptDropDown) {
		this.deptDropDown = deptDropDown;
	}

	public Map<Integer, String> getDepartmentlist() {
		return departmentlist;
	}

	public void setDepartmentlist(Map<Integer, String> departmentlist) {
		this.departmentlist = departmentlist;
	}

	public List<ConfigurationUtilBean> getEmpDropDown() {
		return empDropDown;
	}

	public void setEmpDropDown(List<ConfigurationUtilBean> empDropDown) {
		this.empDropDown = empDropDown;
	}

	public Map<Integer, String> getEmployeelist() {
		return employeelist;
	}

	public void setEmployeelist(Map<Integer, String> employeelist) {
		this.employeelist = employeelist;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getDeptNameExist() {
		return deptNameExist;
	}

	public void setDeptNameExist(String deptNameExist) {
		this.deptNameExist = deptNameExist;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmpNameExist() {
		return empNameExist;
	}

	public void setEmpNameExist(String empNameExist) {
		this.empNameExist = empNameExist;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getPurposeExist() {
		return purposeExist;
	}

	public void setPurposeExist(String purposeExist) {
		this.purposeExist = purposeExist;
	}

	public Map<Integer, String> getPurposelist() {
		return purposelist;
	}

	public void setPurposelist(Map<Integer, String> purposelist) {
		this.purposelist = purposelist;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public List<ConfigurationUtilBean> getVisitordatetimelist() {
		return visitordatetimelist;
	}

	public void setVisitordatetimelist(
			List<ConfigurationUtilBean> visitordatetimelist) {
		this.visitordatetimelist = visitordatetimelist;
	}

	public String getVisitdateExist() {
		return visitdateExist;
	}

	public void setVisitdateExist(String visitdateExist) {
		this.visitdateExist = visitdateExist;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public String getVisittimeExist() {
		return visittimeExist;
	}

	public void setVisittimeExist(String visittimeExist) {
		this.visittimeExist = visittimeExist;
	}

	public String getTime_in() {
		return time_in;
	}

	public void setTime_in(String time_in) {
		this.time_in = time_in;
	}

	public String getDatevalue() {
		return datevalue;
	}

	public void setDatevalue(String datevalue) {
		this.datevalue = datevalue;
	}

	public String getTimevalue() {
		return timevalue;
	}

	public void setTimevalue(String timevalue) {
		this.timevalue = timevalue;
	}

	public List<GridDataPropertyView> getViewPrerequestDetail() {
		return viewPrerequestDetail;
	}

	public void setViewPrerequestDetail(
			List<GridDataPropertyView> viewPrerequestDetail) {
		this.viewPrerequestDetail = viewPrerequestDetail;
	}

	public List<ConfigurationUtilBean> getPrerequestfields() {
		return prerequestfields;
	}

	public void setPrerequestfields(List<ConfigurationUtilBean> prerequestfields) {
		this.prerequestfields = prerequestfields;
	}

	public List<ConfigurationUtilBean> getDropDownList() {
		return dropDownList;
	}

	public void setDropDownList(List<ConfigurationUtilBean> dropDownList) {
		this.dropDownList = dropDownList;
	}

	public Map<Integer, String> getPurposeListPreReqest() {
		return purposeListPreReqest;
	}

	public void setPurposeListPreReqest(
			Map<Integer, String> purposeListPreReqest) {
		this.purposeListPreReqest = purposeListPreReqest;
	}

	public Map<Integer, String> getLocationListPreReqest() {
		return locationListPreReqest;
	}

	public void setLocationListPreReqest(
			Map<Integer, String> locationListPreReqest) {
		this.locationListPreReqest = locationListPreReqest;
	}

	public String getPusposeNameExist() {
		return purposeNameExist;
	}

	public void setPusposeNameExist(String purposeNameExist) {
		this.purposeNameExist = purposeNameExist;
	}

	public String getLocationNameExit() {
		return locationNameExit;
	}

	public void setLocationNameExit(String locationNameExit) {
		this.locationNameExit = locationNameExit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<ConfigurationUtilBean> getPrerequestdatetimelist() {
		return prerequestdatetimelist;
	}

	public void setPrerequestdatetimelist(
			List<ConfigurationUtilBean> prerequestdatetimelist) {
		this.prerequestdatetimelist = prerequestdatetimelist;
	}

	public String getPrevisitdateExit() {
		return previsitdateExit;
	}

	public void setPrevisitdateExit(String previsitdateExit) {
		this.previsitdateExit = previsitdateExit;
	}

	public String getPrevisittimeExit() {
		return previsittimeExit;
	}

	public void setPrevisittimeExit(String previsittimeExit) {
		this.previsittimeExit = previsittimeExit;
	}

	public String getPurposeNameExist() {
		return purposeNameExist;
	}

	public void setPurposeNameExist(String purposeNameExist) {
		this.purposeNameExist = purposeNameExist;
	}

	public List<Object> getPrerequestData() {
		return prerequestData;
	}

	public void setPrerequestData(List<Object> prerequestData) {
		this.prerequestData = prerequestData;
	}

	public List<String> getAlertlist() {
		return alertlist;
	}

	public void setAlertlist(List<String> alertlist) {
		this.alertlist = alertlist;
	}

	public String getAlert_after() {
		return alert_after;
	}

	public void setAlert_after(String alert_after) {
		this.alert_after = alert_after;
	}

	public String getAlerttime() {
		return alerttime;
	}

	public void setAlerttime(String alerttime) {
		this.alerttime = alerttime;
	}

	public String getVisited_mobile() {
		return visited_mobile;
	}

	public void setVisited_mobile(String visited_mobile) {
		this.visited_mobile = visited_mobile;
	}

	public String getOne_time_pwd() {
		return one_time_pwd;
	}

	public void setOne_time_pwd(String one_time_pwd) {
		this.one_time_pwd = one_time_pwd;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSr_number() {
		return sr_number;
	}

	public void setSr_number(String sr_number) {
		this.sr_number = sr_number;
	}

	public void setVisitorfields(LinkedList<ConfigurationUtilBean> visitorfields) {
		this.visitorfields = visitorfields;
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

	public Boolean getSms() {
		return sms;
	}

	public void setSms(Boolean sms) {
		this.sms = sms;
	}

	public Boolean getMail() {
		return mail;
	}

	public void setMail(Boolean mail) {
		this.mail = mail;
	}

	public List<GridDataPropertyView> getViewEmplocmappingDetail() {
		return viewEmplocmappingDetail;
	}

	public void setViewEmplocmappingDetail(
			List<GridDataPropertyView> viewEmplocmappingDetail) {
		this.viewEmplocmappingDetail = viewEmplocmappingDetail;
	}

	public List<Object> getEmplocgatemapdata() {
		return emplocgatemapdata;
	}

	public void setEmplocgatemapdata(List<Object> emplocgatemapdata) {
		this.emplocgatemapdata = emplocgatemapdata;
	}

	public String getGateNameExist() {
		return gateNameExist;
	}

	public void setGateNameExist(String gateNameExist) {
		this.gateNameExist = gateNameExist;
	}

	public Map<Integer, String> getGatenamelist() {
		return gatenamelist;
	}

	public void setGatenamelist(Map<Integer, String> gatenamelist) {
		this.gatenamelist = gatenamelist;
	}

	public String getEmplocgatemap() {
		return emplocgatemap;
	}

	public void setEmplocgatemap(String emplocgatemap) {
		this.emplocgatemap = emplocgatemap;
	}

	public String getVisitorCompanyExist() {
		return visitorCompanyExist;
	}

	public void setVisitorCompanyExist(String visitorCompanyExist) {
		this.visitorCompanyExist = visitorCompanyExist;
	}

	public String getVisitorNameExist() {
		return visitorNameExist;
	}

	public void setVisitorNameExist(String visitorNameExist) {
		this.visitorNameExist = visitorNameExist;
	}

	public String getVisitorMobileExist() {
		return visitorMobileExist;
	}

	public void setVisitorMobileExist(String visitorMobileExist) {
		this.visitorMobileExist = visitorMobileExist;
	}

	public Map<Integer, String> getOrnizationList() {
		return ornizationList;
	}

	public void setOrnizationList(Map<Integer, String> ornizationList) {
		this.ornizationList = ornizationList;
	}

	public Map<Integer, String> getReqvisitorList() {
		return reqvisitorList;
	}

	public void setReqvisitorList(Map<Integer, String> reqvisitorList) {
		this.reqvisitorList = reqvisitorList;
	}

	public Map<Integer, String> getReqvisitormobList() {
		return reqvisitormobList;
	}

	public void setReqvisitormobList(Map<Integer, String> reqvisitormobList) {
		this.reqvisitormobList = reqvisitormobList;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorMob() {
		return visitorMob;
	}

	public void setVisitorMob(String visitorMob) {
		this.visitorMob = visitorMob;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getRequestedvisitorName() {
		return requestedvisitorName;
	}

	public void setRequestedvisitorName(String requestedvisitorName) {
		this.requestedvisitorName = requestedvisitorName;
	}

	public List<GridDataPropertyView> getViewVehicleDetail() {
		return viewVehicleDetail;
	}

	public void setViewVehicleDetail(
			List<GridDataPropertyView> viewVehicleDetail) {
		this.viewVehicleDetail = viewVehicleDetail;
	}

	public LinkedList<ConfigurationUtilBean> getVehiclefields() {
		return vehiclefields;
	}

	public void setVehiclefields(LinkedList<ConfigurationUtilBean> vehiclefields) {
		this.vehiclefields = vehiclefields;
	}

	public Map<Integer, String> getGatelistofloggedinUser() {
		return gatelistofloggedinUser;
	}

	public void setGatelistofloggedinUser(
			Map<Integer, String> gatelistofloggedinUser) {
		this.gatelistofloggedinUser = gatelistofloggedinUser;
	}

	public boolean isDropdownformaterial() {
		return dropdownformaterial;
	}

	public void setDropdownformaterial(boolean dropdownformaterial) {
		this.dropdownformaterial = dropdownformaterial;
	}

	public String getVehiclestatus() {
		return vehiclestatus;
	}

	public void setVehiclestatus(String vehiclestatus) {
		this.vehiclestatus = vehiclestatus;
	}

	public List<ConfigurationUtilBean> getVehicledatetimelist() {
		return vehicledatetimelist;
	}

	public void setVehicledatetimelist(
			List<ConfigurationUtilBean> vehicledatetimelist) {
		this.vehicledatetimelist = vehicledatetimelist;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}

	public String getVehicleEntrydateExist() {
		return vehicleEntrydateExist;
	}

	public void setVehicleEntrydateExist(String vehicleEntrydateExist) {
		this.vehicleEntrydateExist = vehicleEntrydateExist;
	}

	public String getVehicleEntrytimeExist() {
		return vehicleEntrytimeExist;
	}

	public void setVehicleEntrytimeExist(String vehicleEntrytimeExist) {
		this.vehicleEntrytimeExist = vehicleEntrytimeExist;
	}

	public Map<Integer, String> getVendortypelist() {
		return vendortypelist;
	}

	public void setVendortypelist(Map<Integer, String> vendortypelist) {
		this.vendortypelist = vendortypelist;
	}

	public String getVendortype() {
		return vendortype;
	}

	public void setVendortype(String vendortype) {
		this.vendortype = vendortype;
	}

	public String getVendorTypeExist() {
		return vendorTypeExist;
	}

	public void setVendorTypeExist(String vendorTypeExist) {
		this.vendorTypeExist = vendorTypeExist;
	}

	public List<Object> getVehicledatalist() {
		return vehicledatalist;
	}

	public void setVehicledatalist(List<Object> vehicledatalist) {
		this.vehicledatalist = vehicledatalist;
	}

	public String getVisitor_company() {
		return visitor_company;
	}

	public void setVisitor_company(String visitor_company) {
		this.visitor_company = visitor_company;
	}

	public String getVisitor_mobile() {
		return visitor_mobile;
	}

	public void setVisitor_mobile(String visitor_mobile) {
		this.visitor_mobile = visitor_mobile;
	}

	public String getVisitor_name() {
		return visitor_name;
	}

	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}

	public String getVisitorname() {
		return visitorname;
	}

	public void setVisitorname(String visitorname) {
		this.visitorname = visitorname;
	}

	public String getVisitorcompany() {
		return visitorcompany;
	}

	public void setVisitorcompany(String visitorcompany) {
		this.visitorcompany = visitorcompany;
	}

	public String getVisitormobile() {
		return visitormobile;
	}

	public void setVisitormobile(String visitormobile) {
		this.visitormobile = visitormobile;
	}

	public boolean isDropdownforpurpose() {
		return dropdownforpurpose;
	}

	public void setDropdownforpurpose(boolean dropdownforpurpose) {
		this.dropdownforpurpose = dropdownforpurpose;
	}

	public String getPurpose_for() {
		return purpose_for;
	}

	public void setPurpose_for(String purpose_for) {
		this.purpose_for = purpose_for;
	}

	public String getAlertval() {
		return alertval;
	}

	public void setAlertval(String alertval) {
		this.alertval = alertval;
	}

	public String getAlert_to() {
		return alert_to;
	}

	public void setAlert_to(String alert_to) {
		this.alert_to = alert_to;
	}

	public String getPurposeaddition() {
		return purposeaddition;
	}

	public void setPurposeaddition(String purposeaddition) {
		this.purposeaddition = purposeaddition;
	}

	public List<ConfigurationUtilBean> getVendordropdownlist() {
		return vendordropdownlist;
	}

	public void setVendordropdownlist(
			List<ConfigurationUtilBean> vendordropdownlist) {
		this.vendordropdownlist = vendordropdownlist;
	}

	public String getGatename() {
		return gatename;
	}

	public void setGatename(String gatename) {
		this.gatename = gatename;
	}

	public String getLocname() {
		return locname;
	}

	public void setLocname(String locname) {
		this.locname = locname;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_mobile() {
		return driver_mobile;
	}

	public void setDriver_mobile(String driver_mobile) {
		this.driver_mobile = driver_mobile;
	}

	public String getVehicle_model() {
		return vehicle_model;
	}

	public void setVehicle_model(String vehicle_model) {
		this.vehicle_model = vehicle_model;
	}

	public String getVehicle_number() {
		return vehicle_number;
	}

	public void setVehicle_number(String vehicle_number) {
		this.vehicle_number = vehicle_number;
	}

	public Map<Integer, String> getJlocationlist() {
		return jlocationlist;
	}

	public void setJlocationlist(Map<Integer, String> jlocationlist) {
		this.jlocationlist = jlocationlist;
	}

	public String getTrip_no() {
		return trip_no;
	}

	public void setTrip_no(String trip_no) {
		this.trip_no = trip_no;
	}

	public String getVehlocation() {
		return vehlocation;
	}

	public void setVehlocation(String vehlocation) {
		this.vehlocation = vehlocation;
	}

	public String getDestinationto() {
		return destinationto;
	}

	public void setDestinationto(String destinationto) {
		this.destinationto = destinationto;
	}

	public String getHidesrno() {
		return hidesrno;
	}

	public void setHidesrno(String hidesrno) {
		this.hidesrno = hidesrno;
	}
	public String getHidetripno() {
		return hidetripno;
	}
	public void setHidetripno(String hidetripno) {
		this.hidetripno = hidetripno;
	}
	public String getVpassId() {
		return vpassId;
	}

	public void setVpassId(String vpassId) {
		this.vpassId = vpassId;
	}
	public Map<Integer, String> getVendorcompNameList() {
		return vendorcompNameList;
	}
	public void setVendorcompNameList(Map<Integer, String> vendorcompNameList) {
		this.vendorcompNameList = vendorcompNameList;
	}

	public String getVendorcompNameExist() {
		return vendorcompNameExist;
	}

	public void setVendorcompNameExist(String vendorcompNameExist) {
		this.vendorcompNameExist = vendorcompNameExist;
	}
	public String getVendorCompName() {
		return vendorCompName;
	}
	public void setVendorCompName(String vendorCompName) {
		this.vendorCompName = vendorCompName;
	}
	public Map<Integer, String> getVendorcompFrontlist() {
		return vendorcompFrontlist;
	}
	public void setVendorcompFrontlist(Map<Integer, String> vendorcompFrontlist) {
		this.vendorcompFrontlist = vendorcompFrontlist;
	}
	public String getVeh_status() {
		return veh_status;
	}
	public void setVeh_status(String veh_status) {
		this.veh_status = veh_status;
	}

	public String getNo_of_bill() {
		return no_of_bill;
	}

	public void setNo_of_bill(String no_of_bill) {
		this.no_of_bill = no_of_bill;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMaterial_details() {
		return material_details;
	}

	public void setMaterial_details(String material_details) {
		this.material_details = material_details;
	}

	public String getVh_owner_mob() {
		return vh_owner_mob;
	}

	public void setVh_owner_mob(String vh_owner_mob) {
		this.vh_owner_mob = vh_owner_mob;
	}

	public String getVehicle_owner() {
		return vehicle_owner;
	}

	public void setVehicle_owner(String vehicle_owner) {
		this.vehicle_owner = vehicle_owner;
	}

	public String getVehicleExitStatus() {
		return vehicleExitStatus;
	}

	public void setVehicleExitStatus(String vehicleExitStatus) {
		this.vehicleExitStatus = vehicleExitStatus;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getCountTotal() {
		return countTotal;
	}

	public void setCountTotal(String countTotal) {
		this.countTotal = countTotal;
	}

	public String getCountIn() {
		return countIn;
	}

	public void setCountIn(String countIn) {
		this.countIn = countIn;
	}

	public String getCountOut() {
		return countOut;
	}

	public void setCountOut(String countOut) {
		this.countOut = countOut;
	}
	
}
