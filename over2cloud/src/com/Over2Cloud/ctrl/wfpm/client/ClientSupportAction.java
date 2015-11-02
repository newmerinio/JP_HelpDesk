package com.Over2Cloud.ctrl.wfpm.client;

import java.io.InputStream;
import java.math.BigInteger;
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

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.Over2Cloud.ctrl.wfpm.common.EmployeeHelper;
import com.Over2Cloud.ctrl.wfpm.common.EntityType;
import com.Over2Cloud.ctrl.wfpm.target.TargetType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClientSupportAction extends ActionSupport implements
ServletRequestAware{
	private HttpServletRequest request;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1000000L;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	
	private String empId = session.get("empIdofuser").toString().split("-")[1];

	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
	.get("connectionSpace");
	String cId = new CommonHelper().getEmpDetailsByUserName(
	CommonHelper.moduleName, userName, connectionSpace)[0];
	static final Log log = LogFactory.getLog(ClientSupportAction.class);
	
	
	private List<ConfigurationUtilBean> clientTypeTextBox = null;
	private List<ConfigurationUtilBean> clientTypeDropDown = null;
	private List<GridDataPropertyView> supportTypeViewColum = new ArrayList<GridDataPropertyView>();
	private List<Object> supportTypeViewList;
	
	private List<GridDataPropertyView> supportViewColum = new ArrayList<GridDataPropertyView>();
	private List<Object> supportViewList;
	
	private InputStream fileInputStream;
	
	private String actionType;
	private Map<String, String> supportTypeMAP;
	private Map<String, String> clientNameMAP;
	private Map<String, String> offeringNameMAP;
	private Map<String, String> deptMap;
	Map<Integer, String> employeelist = new HashMap<Integer, String>();
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private String dept;
	
	private String title;
	private String clientName;
	private String offeringName;
	
	private String address;
	private String location;
	private String contactNumber;
	private String industry;
	private String subIndustry;
	private String email;
	private String targetSegment;
	private String website;
	private String starRating;
	
	private String offeringId;
	private String  verticalname;
	private String  offeringname;
	private String  subofferingname;
	
	private String contactName;
	private String birthday;
	private String anniversary;
	private String designation;
	private String degreeOfInfluence;
	private String ownership;
	private String offeringLevelId;
	
	private String delivery_date;
	private String department;
	private String empName;
	private String contact_person;
	private String details;
	
	private String ins_attachementFileName;
	private String ins_attachementContentType;
	private File ins_attachement;
	
	private String support_type;
	private String comments;
	private String support_from;
	private String support_to;
	private String amount;
	
	
	private String po_attachFileName;
	private String po_attachContentType;
	private File po_attach;
	
	private String agreement_attachFileName;
	private String agreement_attachContentType;
	private File agreement_attach;
	
	private String acManager;
	private String clientname;
	private String organization;
	private String convertedOn;
	private String downloadPO;
	private String ClientType;
	
	private String searchFor;
	private String fromDate;
	private String fromDateSearch;
	
	private String supportTill;
	private String poAttach;
	private String agreementAttach;
	
	private String extendedSupportType;
	private String extendComments;
	private String extendDateFrom;
	private String extendDateTo;
	private String extendAmount;
	int numberOfStar;
	List countStar;
	private String clientContact;
	private String close_flag;
	private String extpoAttach;
	private String extagreementAttach;
	private String communicationName;
	private Map<String, String> employee_basicMap;
	private File attachedDoc;
	protected String attachedDocFileName;
	protected String attachedDocContentType;
	private String attachement1;
	private String subject;
	private String mail_text;
	private String name;
	private String mobile_number;
	private String msg_txt;
	private String fileName;
	private String docType;
	List<ClientSupportBean> contactDetails;
	List<ClientSupportBean>contactNameToConfigure;
	
	
	
	private String id;
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

	public String beforeClientDetails()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			ClientHelper3 ch3 = new ClientHelper3();
			clientNameMAP = ch3.supportClientNameMAP(connectionSpace);
			offeringNameMAP =  ch3.supportOfferingNameMAP(connectionSpace);
			// Build support Type map 
			setSupportTypeMAP(ch3.fetchSupportType(connectionSpace));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String searchShowClientSupportData()
	{
		try {
			if (!ValidateSession.checkSession()) return LOGIN;
			//setMainHeaderName("Vendor");
			if(ClientType.equalsIgnoreCase("Existing Clients"))
			{
				setSupportViewProp("mapped_client_support_configuration","client_support_configuration");
			}
			if(ClientType.equalsIgnoreCase("Closed Clients"))
			{
				setClosedClientSupportViewProp("mapped_client_support_configuration","client_support_configuration");
			}
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setSupportViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		supportViewColum.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("client_id");
		gpv.setHeadingName("Client Id");
		gpv.setHideOrShow("true");
		supportViewColum.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
				.getConfigurationData(table1, accountID, connectionSpace, "",
						0, "table_name", table2);
		for (GridDataPropertyView gdp : returnResult) {
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			if(gdp.getColomnName().equalsIgnoreCase("delivery_date"))
			{
				gpv.setHideOrShow("true");
			}else if(gdp.getColomnName().equalsIgnoreCase("support_status"))
			{
				gpv.setHideOrShow("true");
			}
			else if(gdp.getColomnName().equalsIgnoreCase("support_type"))
			{
				gpv.setHideOrShow("true");
			}
			else if(gdp.getColomnName().equalsIgnoreCase("support_till"))
			{
				gpv.setHideOrShow("true");
			}
			else
			{
				gpv.setHideOrShow(gdp.getHideOrShow());
			}
			
			gpv.setFormatter(gdp.getFormatter());
			if(gdp.getColomnName().equalsIgnoreCase("clientName"))
			{
				gpv.setWidth(280);
			}else if(gdp.getColomnName().equalsIgnoreCase("offeringlevel3"))
			{
				gpv.setWidth(250);
			}
			else if(gdp.getColomnName().equalsIgnoreCase("contact_person"))
			{
				gpv.setWidth(220);
			}
			else
			{
				gpv.setWidth(gdp.getWidth());
			}
			
			supportViewColum.add(gpv);
		}
	}
	public void setClosedClientSupportViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		supportViewColum.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("client_id");
		gpv.setHeadingName("Client Id");
		gpv.setHideOrShow("true");
		supportViewColum.add(gpv);
		
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
			supportViewColum.add(gpv);
		}
	}
	
	public String viewClientSupportGrid()
	{
		try {
			if (!ValidateSession.checkSession()) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from client_support_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount != null && dataCount.size()>0)
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
				if (to > getRecords()) to = getRecords();
				System.out.println(" to  "+to+"    from   "+from);
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select distinct ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from client_support_details as csd ");

				List fieldNames = Configuration.getColomnList("mapped_client_support_configuration", accountID, connectionSpace, "client_support_configuration");
				fieldNames.add("client_id");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
					if (obdata.toString().equalsIgnoreCase("clientName"))
						{
							queryTemp.append(" cbd.clientName,");
							queryEnd.append(" left join opportunity_details as opd on opd.id = csd.opportunity_detail_id ");
							queryEnd.append(" left join client_basic_data as cbd on cbd.id = opd.clientName ");
						}
						else if (obdata.toString().equalsIgnoreCase("delivary_dept"))
						{
							queryTemp.append(" dept.deptName 'delivary_dept', ");
							queryEnd.append(" left join department as dept on dept.id = csd.delivary_dept ");
						}
						else if (obdata.toString().equalsIgnoreCase("delivary_emp"))
						{
							queryTemp.append(" emp.empName 'delivary_emp', ");
							queryEnd.append(" left join employee_basic as emp on emp.id = csd.delivary_emp ");
						}
						else if (obdata.toString().equalsIgnoreCase("offeringlevel3"))
						{
							queryTemp.append(" off.subofferingname 'offeringlevel3', ");
							queryEnd.append(" inner join offeringlevel3 as off on off.id = opd.offeringId ");
						}
						else if (obdata.toString().equalsIgnoreCase("support_type"))
						{
							queryTemp.append(" cstd.support_type 'support_type', ");
							queryEnd.append(" inner join clientsupport_type_data as cstd on cstd.id = csd.support_type ");
						}
						else if (obdata.toString().equalsIgnoreCase("contact_person"))
						{
							queryTemp.append(" ccd.personName 'contact_person',");
							queryEnd.append(" left join client_contact_data as ccd ");
							queryEnd.append(" on ccd.id IN (select id from client_contact_data where clientName = (select clientName from client_offering_mapping where clientName = opd.clientName and offeringId = opd.offeringId))");
						}
						else if (obdata.toString().equalsIgnoreCase("mobile_number"))
						{
							queryTemp.append(" ccd.contactNo 'mobile_number', ");
							//queryEnd.append(" left join client_basic_data as cbd on cbd.id = opd.clientName ");
						}
						else if (obdata.toString().equalsIgnoreCase("email_id"))
						{
							queryTemp.append(" ccd.emailOfficial 'email_id', ");
							//queryEnd.append(" left join client_basic_data as cbd on cbd.id = opd.clientName ");
						}
						else if (obdata.toString().equalsIgnoreCase("converted_on"))
						{
							queryTemp.append(" com.convertDate 'converted_on', ");
							queryEnd.append(" inner join client_offering_mapping as com ");
							queryEnd.append(" on (opd.clientName = com.clientName and opd.offeringId = com.offeringId) and  com.clientName = (select clientName from client_offering_mapping where clientName = opd.clientName and offeringId = opd.offeringId)");
						}
						else if(obdata.toString().equalsIgnoreCase("client_id"))
						{
							queryTemp.append(" ccd.clientName 'client_id' , ");
						}
						else
						{
							queryTemp.append("csd." + obdata.toString() + ",");
						}
					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				if(ClientType.equalsIgnoreCase("Existing Clients"))
				{
					query.append(" com.isConverted = '1' and close_flag IN (0,2)");
				}
				else
				{
					query.append(" com.isConverted = '1' and close_flag = '1'");
				}
				System.out.println("ClientType  "+ClientType);
				if(support_type != null && !support_type.equalsIgnoreCase("-1"))
				{
					query.append(" and csd.support_type = '");
					query.append(support_type);
					query.append("'");
				}
				if(clientName != null && !clientName.equalsIgnoreCase("-1"))
				{
					query.append(" and opd.clientName = '");
					query.append(clientName);
					query.append("'");
				}
				if(offeringName != null && !offeringName.equalsIgnoreCase("-1"))
				{
					query.append(" and opd.offeringId = '");
					query.append(offeringName);
					query.append("'");
				}
				if(fromDate != null && !fromDate.equalsIgnoreCase("") && fromDateSearch != null && !fromDateSearch.equalsIgnoreCase("") && searchFor != null && !searchFor.equalsIgnoreCase("-1"))
				{
					
					if(searchFor.equalsIgnoreCase("Support"))
					{
						query.append(" and csd.support_till between '");
						query.append(DateUtil.convertDateToUSFormat(fromDate));
						query.append("' and '");
						query.append(DateUtil.convertDateToUSFormat(fromDateSearch));
						query.append("'");
					}else if(searchFor.equalsIgnoreCase("Delivery"))
					{
						query.append(" and csd.delivery_date between '");
						query.append(DateUtil.convertDateToUSFormat(fromDate));
						query.append("' and '");
						query.append(DateUtil.convertDateToUSFormat(fromDateSearch));
						query.append("'");
					}
					else
					{
						query.append(" and com.convertDate between '");
						query.append(DateUtil.convertDateToUSFormat(fromDate));
						query.append("' and '");
						query.append(DateUtil.convertDateToUSFormat(fromDateSearch));
						query.append("'");
					}
					
				}
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceMaster"))
					{
						setSearchField(" sm.sourceName ");
					}
					
					else
					{
						setSearchField(" csd." + getSearchField());
					}

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}
				}
				//query.append(" order by ctd.support_type limit " + from + "," + to);
				query.append(" order by cbd.clientName");
				System.out.println("query>>>>" + query.toString());
				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "client_support_details", connectionSpace);

				// System.out.println("query:::" + query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
				if(data != null && data.size()>0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						String clientname = "";
						String offeringId = "";

						String clientId = "";
						double weightage = 0;
						String supportTo = null;
						String closeFlag = null;
						ClientHelper2 ch2 = new ClientHelper2();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] == null || obdata[k].toString().equalsIgnoreCase(""))
							{
								obdata[k] = fieldNames.get(k).toString().equalsIgnoreCase("weightage") ? "0.0" : "NA";
							}
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									clientId = obdata[k].toString();
								}
								else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("support_till"))
									{
										supportTo = obdata[k].toString();
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("converted_on"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("delivery_date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}
									
									else if (fieldNames.get(k).toString().equalsIgnoreCase("close_flag"))
									{
										closeFlag = obdata[k].toString();
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("support_status"))
									{
										String currDate = DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin();
										if(supportTo != null && !supportTo.equalsIgnoreCase("NA"))
										{
											boolean status=true;
											//boolean status = DateUtil.comparetwoDatesForClientSupport(currDate, supportTo);
											if(status)
											{
												obdata[k] = "Active";
											}else 
											{
												obdata[k] = "Expire";
											}
											if(closeFlag.equalsIgnoreCase("2"))
											{
												obdata[k] = "Hold";
											}
											System.out.println("supportTo "+supportTo+" currDate "+currDate+"  status "+status+"  obdata[k] "+obdata[k].toString());
										}
										
										/*obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
										closeFlag = obdata[k].toString();*/
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
						}

						
						Listhb.add(one);
					}
					
					setSupportViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeviewSupportType()
	{
		try {
			if (!ValidateSession.checkSession()) return LOGIN;
			//setMainHeaderName("Vendor");
			setSupportTypeViewProp("mapped_clientsupport_type_configuration",
					"clientsupport_type_configuration");
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setSupportTypeViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		supportTypeViewColum.add(gpv);

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
			supportTypeViewColum.add(gpv);
		}
	}
	
	public String searchshowClientSupportType()
	{
		try {
			if (!ValidateSession.checkSession()) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from clientsupport_type_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			if(dataCount != null && dataCount.size()>0)
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
				if (to > getRecords()) to = getRecords();
				System.out.println(" to  "+to+"    from   "+from);
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from clientsupport_type_data as ctd ");

				List fieldNames = Configuration.getColomnList("mapped_clientsupport_type_configuration", accountID, connectionSpace, "clientsupport_type_configuration");
				fieldNames.add("client_id");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{

						if (obdata.toString().equalsIgnoreCase("sourceMaster"))
						{
							queryTemp.append(" sm.sourceName 'sourceMaster', ");
							queryEnd.append(" left join sourcemaster as sm on sm.id = cbd.sourceMaster ");
						}
						
						else
						{
							queryTemp.append("ctd." + obdata.toString() + ",");
						}
					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" ctd.userName is not null ");
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceMaster"))
					{
						setSearchField(" sm.sourceName ");
					}
					
					else
					{
						setSearchField(" ctd." + getSearchField());
					}

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}
				//query.append(" order by ctd.support_type limit " + from + "," + to);
				query.append(" order by ctd.support_type");
				System.out.println("query>>>>" + query.toString());
				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "clientsupport_type_data", connectionSpace);

				// System.out.println("query:::" + query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				
				if(data != null && data.size()>0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						String clientname = "";
						String type = "";

						String clientId = "";
						double weightage = 0;
						ClientHelper2 ch2 = new ClientHelper2();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] == null || obdata[k].toString().equalsIgnoreCase(""))
							{
								obdata[k] = fieldNames.get(k).toString().equalsIgnoreCase("weightage") ? "0.0" : "NA";
							}
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									clientId = obdata[k].toString();
								}
								else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
						}

						
						Listhb.add(one);
					}
					
					setSupportTypeViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeAddClientSupportType()
	{
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;
			setAddPageDataForClientSupportType();

		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeAddClientSupportType of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void setAddPageDataForClientSupportType()
	{
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> list = Configuration.getConfigurationData("mapped_clientsupport_type_configuration",accountID, connectionSpace, "", 0, "table_name","clientsupport_type_configuration");
			clientTypeTextBox = new ArrayList<ConfigurationUtilBean>();
			clientTypeDropDown = new ArrayList<ConfigurationUtilBean>();
			
			if(list != null && list.size()>0)
			{
				for (GridDataPropertyView gdp : list)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
						clientTypeTextBox.add(obj);
					}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String addClientSupportType()
	{
		try {
			if (!ValidateSession.checkSession()) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> clientTypeColName = Configuration.getConfigurationData("mapped_clientsupport_type_configuration", accountID, connectionSpace, "", 0, "table_name", "clientsupport_type_configuration");
			if (clientTypeColName != null && clientTypeColName.size()>0)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumns = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : clientTypeColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumns.add(ob1);

				}
				cbt.createTable22("clientsupport_type_data", Tablecolumns, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
				if (requestParameterNames != null
						&& requestParameterNames.size() > 0) {

					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						//System.out.println("parmName  "+parmName);
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
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				status = cbt.insertIntoTable("clientsupport_type_data",insertData, connectionSpace);
				if (status) {
					addActionMessage("Data Saved Successfully!!!");
				}else
				{
					addActionMessage("There is some error");
				}
				
			}
		} catch (Exception e) {
			log.error("Date : "	+ DateUtil.getCurrentDateIndianFormat()	+ " Time: "+ DateUtil.getCurrentTime()
							+ " "
							+ "Problem in Over2Cloud  method addClientSupportType of class "+ getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	// view on client link click
	public String viewclientShow()
	{	
		if (!ValidateSession.checkSession()) return LOGIN;
		try
		{
			ClientSupportBean clsupportBean = null;
			 contactDetails =  new LinkedList<ClientSupportBean>();
			 countStar = new ArrayList();
			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			clientName = cNameOffId.split("#")[0];
			List clientdata = new ClientHelper3().fetchClientDetails(clientName);
			if(clientdata!=null && clientdata.size()>0)
			{
				for(Iterator it=clientdata.iterator(); it.hasNext();)
				{
					clsupportBean = new ClientSupportBean();
					 Object[] obdata=(Object[])it.next();
					 //offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
					 if(obdata[0] == null){obdata[0]="NA";}
					 if(obdata[1] == null){obdata[1]="NA";}
					 if(obdata[2] == null){obdata[2]="NA";}
					 if(obdata[3] == null){obdata[3]="NA";}
					 if(obdata[4] == null){obdata[4]="NA";}
					 if(obdata[5] == null){obdata[5]="NA";}
					 if(obdata[6] == null){obdata[6]="NA";}
					 if(obdata[7] == null){obdata[7]="NA";}
					 if(obdata[8] == null){obdata[8]="NA";}
					 if(obdata[9] == null){obdata[9]="NA";}
					 if(obdata[10] == null){obdata[10]="NA";}
					 if(obdata[11] == null){obdata[11]="NA";}
					 if(obdata[12] == null){obdata[12]="NA";}
					 if(obdata[13] == null){obdata[13]="NA";}
					 if(obdata[14] == null){obdata[14]="NA";}
					 if(obdata[15] == null){obdata[15]="NA";}
					 if(obdata[16] == null){obdata[16]="NA";}
					 if(obdata[17] == null){obdata[17]="NA";}
					 if(obdata[18] == null){obdata[18]="NA";}
					 if(obdata[19] == null){obdata[19]="NA";}
					 
					 clientName = obdata[0].toString();
					 starRating = obdata[1].toString();
					 setAddress(obdata[2].toString());
					 setLocation(obdata[3].toString());
					 //acManager = obdata[4].toString();
					 industry = obdata[5].toString();
					 subIndustry = obdata[6].toString();
					 contactNumber = obdata[19].toString();
					 email = obdata[18].toString();
					 website = obdata[16].toString();
					 targetSegment = obdata[17].toString();
					 
					 
					 clsupportBean.setContactName(obdata[9].toString());
					 clsupportBean.setContactNumber(obdata[10].toString());
					 clsupportBean.setBirthday(obdata[11].toString());
					 clsupportBean.setAnniversary(obdata[12].toString());
					 clsupportBean.setDegreeOfInfluence(obdata[13].toString());
					 clsupportBean.setDesignation(obdata[14].toString());
					 clsupportBean.setEmail(obdata[15].toString());
					 clsupportBean.setOwnership("Primary");
					 contactDetails.add(clsupportBean);
					 
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// view on offering link click.
	public String viewOfferingShow()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try
		{
			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			//clientName = cNameOffId.split("#")[0];
			offeringId = cNameOffId.split("#")[1];
			String offeringtree = new ClientHelper3().fetchOfferingTree(offeringId);
			verticalname = offeringtree.split("#")[0];
			offeringname = offeringtree.split("#")[1];
			subofferingname = offeringtree.split("#")[2];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// view on offering link click.
	public String viewcontactClickShow()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			ClientSupportBean clsupportBean = null;
			 contactDetails =  new LinkedList<ClientSupportBean>();
			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			clientName = cNameOffId.split("#")[0];
			offeringId = cNameOffId.split("#")[1];
			List contactdata = new ClientHelper3().fetchClientDetails(clientName);
			if(contactdata!=null && contactdata.size()>0)
			{
				for(Iterator it=contactdata.iterator(); it.hasNext();)
				{
					
					 clsupportBean = new ClientSupportBean();
					 Object[] obdata=(Object[])it.next();
					 
					 if(obdata[9] == null){obdata[9]="NA";}
					 if(obdata[10] == null){obdata[10]="NA";}
					 if(obdata[11] == null){obdata[11]="NA";}
					 if(obdata[12] == null){obdata[12]="NA";}
					 if(obdata[13] == null){obdata[13]="NA";}
					 if(obdata[14] == null){obdata[14]="NA";}
					 if(obdata[15] == null){obdata[15]="NA";}
					 
					 
					 /*contactName = obdata[9].toString();
					 contactNumber = obdata[10].toString();
					 birthday = obdata[11].toString();
					 anniversary = obdata[12].toString();
					 degreeOfInfluence = obdata[13].toString();
					 designation = obdata[14].toString();
					 email = obdata[15].toString();
					 setOwnership("Primary");*/
					 
					 clsupportBean.setContactName(obdata[9].toString());
					 clsupportBean.setContactNumber(obdata[10].toString());
					 clsupportBean.setBirthday(obdata[11].toString());
					 clsupportBean.setAnniversary(obdata[12].toString());
					 clsupportBean.setDegreeOfInfluence(obdata[13].toString());
					 clsupportBean.setDesignation(obdata[14].toString());
					 clsupportBean.setEmail(obdata[15].toString());
					 clsupportBean.setOwnership("Primary");
					 contactDetails.add(clsupportBean);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// view on support status link click.
	public String viewsupportStatusView()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			clientName = cNameOffId.split("#")[0];
			offeringId = cNameOffId.split("#")[1];
			String supportStatus = new ClientHelper3().fetchSupportStatus(id);
			support_type = supportStatus.split("#")[0];
			support_from = DateUtil.convertDateToIndianFormat(supportStatus.split("#")[1]);
			supportTill = DateUtil.convertDateToIndianFormat(supportStatus.split("#")[2]);
			poAttach = supportStatus.split("#")[3];
			request.setAttribute("poAttach",poAttach);
			agreementAttach = supportStatus.split("#")[4];
			request.setAttribute("agreeAttach",agreementAttach);
			comments = supportStatus.split("#")[5];
			
			// Records for support status view.
			//System.out.println("ID Of extendedSupportData "+id);
			String extendedSupportData = new ClientHelper3().fetchExtendedSupportData(id);
			request.setAttribute("Ext", extendedSupportData);
			if(extendedSupportData != "NA")
			{
				extendedSupportType = extendedSupportData.split("#")[0];
				extendComments = extendedSupportData.split("#")[1];
				extendDateFrom = DateUtil.convertDateToIndianFormat(extendedSupportData.split("#")[2]);
				extendDateTo = DateUtil.convertDateToIndianFormat(extendedSupportData.split("#")[3]);
				extpoAttach = extendedSupportData.split("#")[4];
				request.setAttribute("extpoAttach", extpoAttach);
				extagreementAttach = extendedSupportData.split("#")[5];
				request.setAttribute("extagreeAttach", extagreementAttach);
				extendAmount = extendedSupportData.split("#")[6];
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	// view on convert Date link click.
	public String conversionDetails()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			 ClientSupportBean clsupportBean = null;
			 contactDetails =  new LinkedList<ClientSupportBean>();
			//clientName = getClientName();
			offeringName = getOfferingName();

			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			clientName = cNameOffId.split("#")[0];
			offeringId = cNameOffId.split("#")[1];
			offeringname = new ClientHelper3().fetchOfferingName(offeringId);
			contactName = new ClientHelper3().fetchContactName(clientName);
			organization = new ClientHelper3().fetchClientName(clientName);
			//lastActivity = new ClientHelper3().fetchLastStatus(clientName, offeringId);
			//takeActionId = new ClientHelper3().fetchTakeActionId(clientName, offeringId);
			
			//forecastCategory = cNameOffId.split("#")[3];
			//salesStages = cNameOffId.split("#")[4];
			List clientdata = new ClientHelper3().fetchClientDetails(clientName);
			String clientOfferingdata = new ClientHelper3().fetchClMapData(clientName, offeringId);
			convertedOn = DateUtil.convertDateToIndianFormat(clientOfferingdata.split("#")[0]);
			downloadPO = clientOfferingdata.split("#")[1];
			request.setAttribute("download", downloadPO);
			comments = clientOfferingdata.split("#")[2];
			
			String offeringtree = new ClientHelper3().fetchOfferingTree(offeringId);
			verticalname = offeringtree.split("#")[0];
			offeringname = offeringtree.split("#")[1];
			subofferingname = offeringtree.split("#")[2];
			
			//List salesstageForcast = new ClientHelper3().fetch
			if(clientdata!=null && clientdata.size()>0)
			{
				for(Iterator it=clientdata.iterator(); it.hasNext();)
				{
					 clsupportBean = new ClientSupportBean();
					 Object[] obdata=(Object[])it.next();
					 //offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
					 if(obdata[0] == null){obdata[0]="NA";}
					 if(obdata[1] == null){obdata[1]="NA";}
					 if(obdata[2] == null){obdata[2]="NA";}
					 if(obdata[3] == null){obdata[3]="NA";}
					 if(obdata[4] == null){obdata[4]="NA";}
					 if(obdata[5] == null){obdata[5]="NA";}
					 if(obdata[6] == null){obdata[6]="NA";}
					 if(obdata[7] == null){obdata[7]="NA";}
					 if(obdata[8] == null){obdata[8]="NA";}
					 if(obdata[9] == null){obdata[9]="NA";}
					 if(obdata[10] == null){obdata[10]="NA";}
					 if(obdata[11] == null){obdata[11]="NA";}
					 if(obdata[12] == null){obdata[12]="NA";}
					 if(obdata[13] == null){obdata[13]="NA";}
					 if(obdata[14] == null){obdata[14]="NA";}
					 if(obdata[16] == null){obdata[16]="NA";}
					 if(obdata[17] == null){obdata[17]="NA";}
					 if(obdata[18] == null){obdata[18]="NA";}
					 if(obdata[19] == null){obdata[19]="NA";}
					 clientname = obdata[0].toString();
					 starRating = obdata[1].toString();
					 address = obdata[2].toString();
					 location = obdata[3].toString();
					 acManager = obdata[4].toString();
					 industry = obdata[5].toString();
					 subIndustry = obdata[6].toString();
					 website = obdata[16].toString();
					 targetSegment = obdata[17].toString();
					 
					 clsupportBean.setContactName(obdata[9].toString());
					 clsupportBean.setContactNumber(obdata[10].toString());
					 clsupportBean.setBirthday(obdata[11].toString());
					 clsupportBean.setAnniversary(obdata[12].toString());
					 clsupportBean.setDegreeOfInfluence(obdata[13].toString());
					 clsupportBean.setDesignation(obdata[14].toString());
					 clsupportBean.setEmail(obdata[15].toString());
					
					 contactDetails.add(clsupportBean);
					 
					 //forecastCategory = obdata[7].toString();
					// salesStages = obdata[8].toString();
					 
				}
			}
			//System.out.println("contactName>"+contactName+"   organization>"+organization+"  lastActivity>>"+lastActivity);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String docDownload() throws IOException
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		String result = null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List docName=null;
		try{
			System.out.println(this.getPo_attachFileName());
		    if(this.docType!="" && this.docType!=null)
			 {
		    	System.out.println(this.offeringId);
		    	System.out.println(this.clientName);
		    	System.out.println(id);
		    	if(docType.equalsIgnoreCase("poattach1") && this.offeringId!=null && this.offeringId!="" && this.clientName!=null && this.clientName!="")
		    	{	
		     	docName=cbt.executeAllSelectQuery("SELECT po_attachment FROM client_offering_mapping WHERE clientName="+clientName+" and  offeringId="+offeringId, connectionSpace);
		    	}
		    	if(docType.equalsIgnoreCase("poattach2") && this.id!=null && this.id!="" )
		    	{
		     	docName=cbt.executeAllSelectQuery("SELECT po_attach FROM client_support_details WHERE id="+id, connectionSpace);
		    	}
		    	if(docType.equalsIgnoreCase("agree") && this.id!=null && this.id!="" )
		    	{
		     	docName=cbt.executeAllSelectQuery("SELECT agreement_attach FROM client_support_details WHERE id="+id, connectionSpace);
		    	}
		    	if(docType.equalsIgnoreCase("extpoattach1") && this.id!=null && this.id!="" )
		    	{
		     	docName=cbt.executeAllSelectQuery("SELECT max(ces.po_attach) FROM client_extended_support as ces left join client_support_details as csd on ces.client_support_id = csd.id left join clientsupport_type_data as ctd on ctd.id = ces.support_type where ces.client_support_id="+id, connectionSpace);
		    	}
		    	if(docType.equalsIgnoreCase("extpoattach2") && this.id!=null && this.id!="" )
		    	{
		     	docName=cbt.executeAllSelectQuery("SELECT max(ces.agreement_attach) FROM client_extended_support as ces left join client_support_details as csd on ces.client_support_id = csd.id left join clientsupport_type_data as ctd on ctd.id = ces.support_type where ces.client_support_id=="+id, connectionSpace);
		    	}
		    	
		    	System.out.println(docName.size());
		    	if (docName.get(0)!=null && !docName.get(0).equals("")) 
				{
		    		fileName =(String) docName.get(0);
					File file = new File(fileName);
					System.out.println("File IS >>>>>>>>>>>>"+file);
					boolean res = file.exists();
					System.out.println("res "+res);
					if(res)
					{
						fileInputStream = new FileInputStream(file);
						System.out.println("Insput Stream ??????????????"+fileInputStream);
						fileName = file.getName();
						System.out.println(fileName);
						result = SUCCESS;
					}
					else{
						addActionError("Attachment Not Found !!!!!");
						System.out.println("in Error");
						result = ERROR;
					}
				}
				else 
				{
					addActionError("Attachment Not Found !!!!!");
					System.out.println("in Error");
					result = ERROR;
				} 
			 }
		}
		catch(FileNotFoundException file)
		{
			 addActionError("Attachment Not Found !!!!!");
	    	 result = ERROR;
	    	 file.printStackTrace();
		}
	     catch(Exception e)
	     {
	    	 
	    	 fileInputStream.close();
	    	 e.printStackTrace();
	    	 addActionError("Attachment Not Found !!!!!");
	    	 result = ERROR;
	  }
		return result;
	}
	
	/*
	public String downLoadPO()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			fileInputStream = new FileInputStream(new File("/opt//CRM_ImageData//Cancer Awareness Day Offer.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	    return SUCCESS;
	}*/
	public String beforeTakeActionSupport()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			//clientName = getClientName();
			offeringName = getOfferingName();

			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			clientName = cNameOffId.split("#")[0];
			offeringId = cNameOffId.split("#")[1];
			if(cNameOffId.split("#")[5].equalsIgnoreCase("1"))
			{
				setClose_flag("true");
			}
			
			offeringname = new ClientHelper3().fetchOfferingName(offeringId);
			contactName = new ClientHelper3().fetchContactName(clientName);
			organization = new ClientHelper3().fetchClientName(clientName);
			//lastActivity = new ClientHelper3().fetchLastStatus(clientName, offeringId);
			//takeActionId = new ClientHelper3().fetchTakeActionId(clientName, offeringId);
			//forecastCategory = cNameOffId.split("#")[3];
			//salesStages = cNameOffId.split("#")[4];
			String offeringtree = new ClientHelper3().fetchOfferingTree(offeringId);
			verticalname = offeringtree.split("#")[0];
			offeringname = offeringtree.split("#")[1];
			subofferingname = offeringtree.split("#")[2];
			List clientdata = new ClientHelper3().fetchClientDetails(clientName);
			String clientOfferingdata = new ClientHelper3().fetchClMapData(clientName, offeringId);
			convertedOn = DateUtil.convertDateToIndianFormat(clientOfferingdata.split("#")[0]);
			downloadPO = clientOfferingdata.split("#")[1];
			request.setAttribute("down", downloadPO);
			comments = clientOfferingdata.split("#")[2];
			
			//List salesstageForcast = new ClientHelper3().fetch
			if(clientdata!=null && clientdata.size()>0)
			{
				for(Iterator it=clientdata.iterator(); it.hasNext();)
				{
					 Object[] obdata=(Object[])it.next();
					 //offtreedata = obdata[0].toString()+"#"+obdata[1].toString()+"#"+obdata[2].toString();
					 if(obdata[0] == null){obdata[0]="NA";}
					 if(obdata[1] == null){obdata[1]="NA";}
					 if(obdata[2] == null){obdata[2]="NA";}
					 if(obdata[3] == null){obdata[3]="NA";}
					 if(obdata[4] == null){obdata[4]="NA";}
					 if(obdata[5] == null){obdata[5]="NA";}
					 if(obdata[6] == null){obdata[6]="NA";}
					 if(obdata[7] == null){obdata[7]="NA";}
					 if(obdata[8] == null){obdata[8]="NA";}
					 if(obdata[9] == null){obdata[9]="NA";}
					 if(obdata[10] == null){obdata[10]="NA";}
					 if(obdata[11] == null){obdata[11]="NA";}
					 if(obdata[12] == null){obdata[12]="NA";}
					 if(obdata[13] == null){obdata[13]="NA";}
					 if(obdata[14] == null){obdata[14]="NA";}
					 if(obdata[15] == null){obdata[15]="NA";}
					 if(obdata[16] == null){obdata[16]="NA";}
					 if(obdata[17] == null){obdata[17]="NA";}
					 if(obdata[18] == null){obdata[18]="NA";}
					 if(obdata[19] == null){obdata[19]="NA";}
					
					 clientname = obdata[0].toString();
					 starRating = obdata[1].toString();
					 address = obdata[2].toString();
					 location = obdata[3].toString();
					 acManager = obdata[4].toString();
					 industry = obdata[5].toString();
					 subIndustry = obdata[6].toString();
					 contactName = obdata[9].toString();
					 contactNumber = obdata[10].toString();
					 birthday = obdata[11].toString();
					 anniversary = obdata[12].toString();
					 degreeOfInfluence = obdata[13].toString();
					 designation = obdata[14].toString();
					 email = obdata[15].toString();
					 website = obdata[16].toString();
					 targetSegment = obdata[17].toString();
					 
					 setClientContact(obdata[19].toString());
					 degreeOfInfluence = obdata[13].toString();
					 
				}
			}
			//System.out.println("contactName>"+contactName+"   organization>"+organization+"  lastActivity>>"+lastActivity);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String actionOnSupport()
	{
		String responce = "error";
		if (!ValidateSession.checkSession()) return LOGIN;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			if(!actionType.equalsIgnoreCase("-1"))
			{
				if(actionType.equalsIgnoreCase("Configure Delivery"))
				{
					// Department
					ClientHelper3 ch3 = new ClientHelper3();
					deptMap = new LinkedHashMap<String, String>();
					deptMap = ch3.fetchAllDepartment();
					contactNameToConfigure = new LinkedList<ClientSupportBean>();
					contactNameToConfigure = new ClientHelper3().fetchContactNameToConfigure(clientName);
					responce = "configDelivery";
				}
				else if(actionType.equalsIgnoreCase("Configure Support"))
				{
					ClientHelper3 ch = new ClientHelper3();
					// Build support Type map
					setSupportTypeMAP(ch.fetchSupportType(connectionSpace));
					responce = "LostOpp";
					responce = "configSupport";
				}
				else if(actionType.equalsIgnoreCase("Extented Support"))
				{
					ClientHelper3 ch = new ClientHelper3();
					// Build support Type map 
					setSupportTypeMAP(ch.fetchSupportType(connectionSpace));
					responce = "extendedSupport";
				}
				else if(actionType.equalsIgnoreCase("Hold Support"))
				{
					responce = "holdSupport";
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return responce;
	}
	
	public String getEmployeeDetails()
	{
		ClientHelper3 obj = new ClientHelper3();
		String resultString = ERROR;
		try
		{
			employeelist = new HashMap<Integer, String>();
			employeelist = obj.alleEmployeeList(connectionSpace, dept);
				
			if (employeelist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : employeelist.entrySet())
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
			    + "Problem in Over2Cloud  method getEmployeeDetails of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}
	
	public String takeactionOnClientSupport()
	{
		String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
		clientName = cNameOffId.split("#")[0];
		offeringId = cNameOffId.split("#")[1];
		offeringLevelId = cNameOffId.split("#")[2];
		//System.out.println("clientName  "+clientName);
		List<ClientSupportBean> contactDetail = new LinkedList<ClientSupportBean>();
		contactDetail = new ClientHelper3().fetchContactDetails(clientName);
		String opportunityDetailId = new ClientHelper3().fetchOpportunityDetailId(clientName, offeringId);
		String fetchsupportDetailsId = new ClientHelper3().fetchsupportDetailsId(clientName, offeringId);
		String[] clientsupportId = fetchsupportDetailsId.split("#");
		if (!ValidateSession.checkSession()) return LOGIN;
		if(actionType.equalsIgnoreCase("Configure Delivery"))
		{
			try {
				String renameFilePath = null;
				File theFile = null;
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (delivery_date != null && !delivery_date.equalsIgnoreCase(""))
				{
					wherClause.put("delivery_date", DateUtil.convertDateToUSFormat(delivery_date));
				}
				if (department != null && !department.equalsIgnoreCase(""))
				{
					wherClause.put("delivary_dept", department);
				}
				if (empName != null && !empName.equalsIgnoreCase(""))
				{
					wherClause.put("delivary_emp", empName);
				}
				if (getIns_attachementFileName() != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support") + "//" + DateUtil.mergeDateTime() + getIns_attachementFileName();
					String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support") + "//" + getIns_attachementFileName();
					String str = renameFilePath.replace("//", "/");
					//System.out.println("renameFilePath "+renameFilePath);
					//System.out.println("storeFilePath "+storeFilePath);
					if (storeFilePath != null)
					{
						theFile = new File(storeFilePath);
						File newFileName = new File(str);
						if (theFile != null)
						{
							try
							{
								FileUtils.copyFile(getIns_attachement(), theFile);
								if (theFile.exists()) theFile.renameTo(newFileName);
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
				if (theFile != null)
				{
					wherClause.put("ins_attachement", renameFilePath);
				}
				
				if (contact_person != null && !contact_person.equalsIgnoreCase(""))
				{
					wherClause.put("contact_person", contact_person);
				}
				if (ownership != null && !ownership.equalsIgnoreCase(""))
				{
					wherClause.put("ownership", ownership);
				}
				if (details != null && !details.equalsIgnoreCase(""))
				{
					wherClause.put("details", details);
				}
				wherClause.put("close_flag", "1");
				
				condtnBlock.put("opportunity_detail_id", opportunityDetailId);
				boolean b = cbt.updateTableColomn("client_support_details", wherClause, condtnBlock, connectionSpace);
				
				
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_client_contact_ownership_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_ownership_configuration");
				String[] contactname = contact_person.split(",");
				String[] ownershipval = ownership.split(",");
				boolean res = false;
				if (statusColName != null && statusColName.size()>0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);

					}
					cbt.createTable22("client_contact_ownership_data", Tablecolumesaaa, connectionSpace);
					for (int i = 0; i<contactname.length;i++)
					{
						ob = new InsertDataTable();
						ob.setColName("client_support_detail_id");
						ob.setDataName(clientsupportId[0]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("contactName");
						ob.setDataName(contactDetail.get(i).getContactId());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("clientName");
						ob.setDataName(contactDetail.get(i).getClientname());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(offeringId);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("deptName");
						ob.setDataName(contactDetail.get(i).getContactDept());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("contactNo");
						ob.setDataName(contactDetail.get(i).getContactNumber());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("email");
						ob.setDataName(contactDetail.get(i).getEmail());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("degreeOfInfluence");
						ob.setDataName(contactDetail.get(i).getDegreeOfInfluence());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("commName");
						ob.setDataName(contactDetail.get(i).getContCommName());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("ownership");
						ob.setDataName(ownershipval[i]);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("designation");
						ob.setDataName(contactDetail.get(i).getDesignation());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("dob");
						ob.setDataName(contactDetail.get(i).getBirthday());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("doa");
						ob.setDataName(contactDetail.get(i).getAnniversary());
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
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
						res = cbt.insertIntoTable("client_contact_ownership_data", insertData, connectionSpace);
						insertData.clear();
					}
					if (res)
					{
						System.out.println("statusColName "+statusColName);
						addActionMessage("Data saved successfully.");
					}
					else
					{
						addActionMessage("There is some problem.");
					}
				}
					
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(actionType.equalsIgnoreCase("Configure Support"))
		{
			String renameFilePath = null;
			File theFile = null;
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			if (support_type != null && !support_type.equalsIgnoreCase(""))
			{
				wherClause.put("support_type", support_type);
			}
			if (comments != null && !comments.equalsIgnoreCase(""))
			{
				wherClause.put("comments", comments);
			}
			if (support_from != null && !support_from.equalsIgnoreCase(""))
			{
				wherClause.put("support_from", DateUtil.convertDateToUSFormat(support_from));
			}
			if (support_to != null && !support_to.equalsIgnoreCase(""))
			{
				wherClause.put("support_till", DateUtil.convertDateToUSFormat(support_to));
			}
			
			if (getPo_attachFileName() != null)
			{
				renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_PO") + "//" + DateUtil.mergeDateTime() + getPo_attachFileName();
				String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_PO") + "//" + getPo_attachFileName();
				String str = renameFilePath.replace("//", "/");
				//System.out.println("renameFilePath "+renameFilePath);
				//System.out.println("storeFilePath "+storeFilePath);
				if (storeFilePath != null)
				{
					theFile = new File(storeFilePath);
					File newFileName = new File(str);
					if (theFile != null)
					{
						try
						{
							FileUtils.copyFile(getPo_attach(), theFile);
							if (theFile.exists()) theFile.renameTo(newFileName);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			if (theFile != null)
			{
				//System.out.println("theFile in po_attach  "+theFile);
				wherClause.put("po_attach", po_attach);
			}
			
			if (getAgreement_attach() != null)
			{
				renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_Agreement") + "//" + DateUtil.mergeDateTime() + getAgreement_attach();
				String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_Agreement") + "//" + getAgreement_attach();
				String str = renameFilePath.replace("//", "/");
				//System.out.println("renameFilePath "+renameFilePath);
				//System.out.println("storeFilePath "+storeFilePath);
				if (storeFilePath != null)
				{
					theFile = new File(storeFilePath);
					File newFileName = new File(str);
					if (theFile != null)
					{
						try
						{
							FileUtils.copyFile(getAgreement_attach(), theFile);
							if (theFile.exists()) theFile.renameTo(newFileName);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			if (theFile != null)
			{
				//System.out.println("theFile in agreement_attach "+theFile);
				wherClause.put("agreement_attach", agreement_attach);
			}
			if (amount != null && !amount.equalsIgnoreCase(""))
			{
				wherClause.put("amount", amount);
			}
			wherClause.put("close_flag", "1");
			condtnBlock.put("opportunity_detail_id", opportunityDetailId);
			boolean b = cbt.updateTableColomn("client_support_details", wherClause, condtnBlock, connectionSpace);
			if (b)
			{
				addActionMessage("Data saved successfully.");
			}
			else
			{
				addActionMessage("There is some problem.");
			}
			
		}
		else if(actionType.equalsIgnoreCase("Extented Support"))
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> clientTypeColName = Configuration.getConfigurationData("mapped_client_extended_support_configuration", accountID, connectionSpace, "", 0, "table_name", "client_extended_support_configuration");
				if (clientTypeColName != null && clientTypeColName.size()>0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumns = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : clientTypeColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						Tablecolumns.add(ob1);

					}
					cbt.createTable22("client_extended_support", Tablecolumns, connectionSpace);
					String renameFilePath = null;
					File theFile = null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
					if (requestParameterNames != null
							&& requestParameterNames.size() > 0) {

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext()) {
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							//System.out.println("parmName "+parmName);
							if (paramValue != null
									&& !paramValue.equalsIgnoreCase("")
									&& parmName != null) {
								if(parmName.equalsIgnoreCase("support_from"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
								//	paramValue = DateUtil.convertDateToUSFormat("");
								}
								if(parmName.equalsIgnoreCase("support_till"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									insertData.add(ob);
								}
								if(!parmName.equalsIgnoreCase("actionType") && parmName.equalsIgnoreCase("support_till") &&!parmName.equalsIgnoreCase("support_from")&& !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("supportActionType") && !parmName.equalsIgnoreCase("clientName") && !parmName.equalsIgnoreCase("offeringId"))
								{
									ob = new InsertDataTable();
									ob.setColName(parmName);
									ob.setDataName(paramValue);
									insertData.add(ob);
								}
							}
						}
					}
					
					if (getPo_attachFileName() != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_PO") + "//" + DateUtil.mergeDateTime() + getPo_attachFileName();
						String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_PO") + "//" + getPo_attachFileName();
						String str = renameFilePath.replace("//", "/");
						//System.out.println("renameFilePath "+renameFilePath);
						//System.out.println("storeFilePath "+storeFilePath);
						if (storeFilePath != null)
						{
							theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(getPo_attach(), theFile);
									if (theFile.exists()) theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}
					}
					if (theFile != null)
					{
						System.out.println("theFile in po_attach  "+theFile);
						ob = new InsertDataTable();
						ob.setColName("po_attach");
						ob.setDataName(renameFilePath);
						insertData.add(ob);
					}
					
					if (getAgreement_attach() != null)
					{
						renameFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_Agreement") + "//" + DateUtil.mergeDateTime() + getAgreement_attach();
						String storeFilePath = new CreateFolderOs().createUserDir("WFPM_Client_Support_Agreement") + "//" + getAgreement_attach();
						String str = renameFilePath.replace("//", "/");
						//System.out.println("renameFilePath "+renameFilePath);
						//System.out.println("storeFilePath "+storeFilePath);
						if (storeFilePath != null)
						{
							theFile = new File(storeFilePath);
							File newFileName = new File(str);
							if (theFile != null)
							{
								try
								{
									FileUtils.copyFile(getAgreement_attach(), theFile);
									if (theFile.exists()) theFile.renameTo(newFileName);
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}
					}
					if (theFile != null)
					{
						//System.out.println("theFile in agreement_attach "+theFile);
						ob = new InsertDataTable();
						ob.setColName("agreement_attach");
						ob.setDataName(renameFilePath);
						insertData.add(ob);
					}
					
					ob = new InsertDataTable();
					ob.setColName("client_support_id");
					ob.setDataName(clientsupportId[0]);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("close_flag");
					ob.setDataName("1");
					insertData.add(ob);
					
					/*ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);*/
					
					status = cbt.insertIntoTable("client_extended_support",insertData, connectionSpace);
					if (status) {
						addActionMessage("Data Saved Successfully.");
					}else
					{
						addActionMessage("There is some error");
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		else if(actionType.equalsIgnoreCase("Hold Support"))
		{
			try {
				String renameFilePath = null;
				File theFile = null;
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				
				if (comments != null && !comments.equalsIgnoreCase(""))
				{
					wherClause.put("comments", comments);
				}
				wherClause.put("close_flag", "2");//  enter 2 for hold support.
				
				condtnBlock.put("opportunity_detail_id", opportunityDetailId);
				boolean b = cbt.updateTableColomn("client_support_details", wherClause, condtnBlock, connectionSpace);
				if (b)
				{
					addActionMessage("Data saved successfully.");
				}
				else
				{
					addActionMessage("There is some problem.");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return SUCCESS;
	}
	
	public String clientStar()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			String cNameOffId = new ClientHelper3().fetchClientAndOffIdSupport(id);
			clientName = cNameOffId.split("#")[0];
			numberOfStar = new ClientHelper3().fetchStarOfClient(cNameOffId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String degreeOfInfluence()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			numberOfStar = new ClientHelper3().fetchDegreeOfInfluence(contactName);
			System.out.println("numberOfStar "+numberOfStar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	// for SMS send..
	public String beforeSupportSMSSend()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			communicationName = new ClientHelper3().fetchContactCommName(clientname, contact_person);
			if(communicationName == null){communicationName = "NA";}
			// Sales department Employee
			employee_basicMap = new LinkedHashMap<String, String>();
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// for Mail send..
	public String beforeSupportMailSend()
	{
		if (!ValidateSession.checkSession()) return LOGIN;
		try {
			communicationName = new ClientHelper3().fetchContactCommName(clientname, contact_person);
			if(communicationName == null){communicationName = "NA";}
			// Sales department Employee
			employee_basicMap = new LinkedHashMap<String, String>();
			EmployeeHelper<Map<String, String>> eh = new EmployeeHelper<Map<String, String>>();
			employee_basicMap = eh.fetchEmployee(EmployeeReturnType.MAP, TargetType.OFFERING);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	// save email to send..
	public String sendEmailAction()
	{
		String resString = ERROR;
		if (!ValidateSession.checkSession()) return LOGIN;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String fileName = null;
		try {
			String ccmailid = new CommonHelper().fetchEmailIdOfEmp(name);
			if (attachedDoc != null)
			{
				String filePath = new CreateFolderOs().createUserDir("WFPM_Mail_Attachement");
				fileName = filePath + "/mail_" + DateUtil.getCurrentDateIndianFormat().replaceAll("-", "_") + "_"
						+ DateUtil.getCurrentTime().replaceAll(":", "_") + "_" + attachedDocFileName;
				// System.out.println("fileName:" + fileName);
				File fileToCreate = new File(fileName);
				// System.out.println("fileToCreate "+fileToCreate);
					FileUtils.copyFile(attachedDoc, fileToCreate);
			}
		//	boolean instantmailstatus = new MsgMailCommunication().addMailWithCC(communicationName, "",email, subject, mail_text, "","Pending", "0", fileName, "WFPM", ccmailid);
			if(true)
			{
				addActionMessage("Mail Data saved successfully.");
				resString = SUCCESS;
			}
			else
			{
				addActionMessage("There is some problem.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resString;
	}
	
	// to save support SMS
	public String sendSMSAction()
	{
		String resString = ERROR;
		if (!ValidateSession.checkSession()) return LOGIN;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			String ccCommNameMobileNo = new CommonHelper().fetchMobileOfEmp(name);
			String ccmobileNo = ccCommNameMobileNo.split("#")[0];
			String ccCommunicationName = ccCommNameMobileNo.split("#")[1];
			System.out.println("ccmobileNo "+ccmobileNo+"  ccCommunicationName "+ccCommunicationName);
			boolean instantsmsstatus = new MsgMailCommunication().addMessage(communicationName, "", mobile_number, msg_txt, "", "Pending", "0", "WFPM");
			
			if(instantsmsstatus)
			{
				if(ccmobileNo != null && !ccmobileNo.equalsIgnoreCase("NA") && !ccmobileNo.equalsIgnoreCase(""))
				{
					boolean instantCCsmsstatus = new MsgMailCommunication().addMessage(ccCommunicationName, "", ccmobileNo, msg_txt, "", "Pending", "0", "WFPM");
					System.out.println("instantCCsmsstatus  "+instantCCsmsstatus);
				}
				addActionMessage("SMS Data saved successfully.");
				resString = SUCCESS;
			}
			else
			{
				addActionMessage("There is some problem.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resString;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
	this.request = request;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	public List<ConfigurationUtilBean> getClientTypeTextBox() {
		return clientTypeTextBox;
	}
	public void setClientTypeTextBox(List<ConfigurationUtilBean> clientTypeTextBox) {
		this.clientTypeTextBox = clientTypeTextBox;
	}
	public List<ConfigurationUtilBean> getClientTypeDropDown() {
		return clientTypeDropDown;
	}
	public void setClientTypeDropDown(List<ConfigurationUtilBean> clientTypeDropDown) {
		this.clientTypeDropDown = clientTypeDropDown;
	}

	public List<GridDataPropertyView> getSupportTypeViewColum() {
		return supportTypeViewColum;
	}

	public void setSupportTypeViewColum(
			List<GridDataPropertyView> supportTypeViewColum) {
		this.supportTypeViewColum = supportTypeViewColum;
	}

	public List<Object> getSupportTypeViewList() {
		return supportTypeViewList;
	}

	public void setSupportTypeViewList(List<Object> supportTypeViewList) {
		this.supportTypeViewList = supportTypeViewList;
	}

	public List<GridDataPropertyView> getSupportViewColum() {
		return supportViewColum;
	}

	public void setSupportViewColum(List<GridDataPropertyView> supportViewColum) {
		this.supportViewColum = supportViewColum;
	}

	public List<Object> getSupportViewList() {
		return supportViewList;
	}

	public void setSupportViewList(List<Object> supportViewList) {
		this.supportViewList = supportViewList;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionType() {
		return actionType;
	}

	public Map<String, String> getSupportTypeMAP() {
		return supportTypeMAP;
	}

	public void setSupportTypeMAP(Map<String, String> supportTypeMAP) {
		this.supportTypeMAP = supportTypeMAP;
	}

	public void setDeptMap(Map<String, String> deptMap) {
		this.deptMap = deptMap;
	}

	public Map<String, String> getDeptMap() {
		return deptMap;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOfferingName() {
		return offeringName;
	}

	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSubIndustry() {
		return subIndustry;
	}

	public void setSubIndustry(String subIndustry) {
		this.subIndustry = subIndustry;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTargetSegment() {
		return targetSegment;
	}

	public void setTargetSegment(String targetSegment) {
		this.targetSegment = targetSegment;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public String getVerticalname() {
		return verticalname;
	}

	public void setVerticalname(String verticalname) {
		this.verticalname = verticalname;
	}

	public String getOfferingname() {
		return offeringname;
	}

	public void setOfferingname(String offeringname) {
		this.offeringname = offeringname;
	}

	public String getSubofferingname() {
		return subofferingname;
	}

	public void setSubofferingname(String subofferingname) {
		this.subofferingname = subofferingname;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(String anniversary) {
		this.anniversary = anniversary;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDegreeOfInfluence() {
		return degreeOfInfluence;
	}

	public void setDegreeOfInfluence(String degreeOfInfluence) {
		this.degreeOfInfluence = degreeOfInfluence;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getOwnership() {
		return ownership;
	}

	public String getOfferingLevelId() {
		return offeringLevelId;
	}

	public void setOfferingLevelId(String offeringLevelId) {
		this.offeringLevelId = offeringLevelId;
	}

	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public String getIns_attachementFileName() {
		return ins_attachementFileName;
	}

	public void setIns_attachementFileName(String ins_attachementFileName) {
		this.ins_attachementFileName = ins_attachementFileName;
	}

	public String getIns_attachementContentType() {
		return ins_attachementContentType;
	}

	public void setIns_attachementContentType(String ins_attachementContentType) {
		this.ins_attachementContentType = ins_attachementContentType;
	}

	public File getIns_attachement() {
		return ins_attachement;
	}

	public void setIns_attachement(File ins_attachement) {
		this.ins_attachement = ins_attachement;
	}

	public String getSupport_type() {
		return support_type;
	}

	public void setSupport_type(String support_type) {
		this.support_type = support_type;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSupport_from() {
		return support_from;
	}

	public void setSupport_from(String support_from) {
		this.support_from = support_from;
	}

	public String getSupport_to() {
		return support_to;
	}

	public void setSupport_to(String support_to) {
		this.support_to = support_to;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPo_attachFileName() {
		return po_attachFileName;
	}

	public void setPo_attachFileName(String po_attachFileName) {
		this.po_attachFileName = po_attachFileName;
	}

	public String getPo_attachContentType() {
		return po_attachContentType;
	}

	public void setPo_attachContentType(String po_attachContentType) {
		this.po_attachContentType = po_attachContentType;
	}

	public File getPo_attach() {
		return po_attach;
	}

	public void setPo_attach(File po_attach) {
		this.po_attach = po_attach;
	}

	public String getAgreement_attachFileName() {
		return agreement_attachFileName;
	}

	public void setAgreement_attachFileName(String agreement_attachFileName) {
		this.agreement_attachFileName = agreement_attachFileName;
	}

	public String getAgreement_attachContentType() {
		return agreement_attachContentType;
	}

	public void setAgreement_attachContentType(String agreement_attachContentType) {
		this.agreement_attachContentType = agreement_attachContentType;
	}

	public File getAgreement_attach() {
		return agreement_attach;
	}

	public void setAgreement_attach(File agreement_attach) {
		this.agreement_attach = agreement_attach;
	}

	public String getAcManager() {
		return acManager;
	}

	public void setAcManager(String acManager) {
		this.acManager = acManager;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getConvertedOn() {
		return convertedOn;
	}

	public void setConvertedOn(String convertedOn) {
		this.convertedOn = convertedOn;
	}

	public String getDownloadPO() {
		return downloadPO;
	}

	public void setDownloadPO(String downloadPO) {
		this.downloadPO = downloadPO;
	}

	public String getClientType() {
		return ClientType;
	}

	public void setClientType(String clientType) {
		ClientType = clientType;
	}

	public void setClientNameMAP(Map<String, String> clientNameMAP) {
		this.clientNameMAP = clientNameMAP;
	}

	public Map<String, String> getClientNameMAP() {
		return clientNameMAP;
	}

	public void setOfferingNameMAP(Map<String, String> offeringNameMAP) {
		this.offeringNameMAP = offeringNameMAP;
	}

	public Map<String, String> getOfferingNameMAP() {
		return offeringNameMAP;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromDateSearch() {
		return fromDateSearch;
	}

	public void setFromDateSearch(String fromDateSearch) {
		this.fromDateSearch = fromDateSearch;
	}

	public String getSupportTill() {
		return supportTill;
	}

	public void setSupportTill(String supportTill) {
		this.supportTill = supportTill;
	}

	public String getPoAttach() {
		return poAttach;
	}

	public void setPoAttach(String poAttach) {
		this.poAttach = poAttach;
	}

	public String getAgreementAttach() {
		return agreementAttach;
	}

	public void setAgreementAttach(String agreementAttach) {
		this.agreementAttach = agreementAttach;
	}

	public String getExtendedSupportType() {
		return extendedSupportType;
	}

	public void setExtendedSupportType(String extendedSupportType) {
		this.extendedSupportType = extendedSupportType;
	}

	public String getExtendComments() {
		return extendComments;
	}

	public void setExtendComments(String extendComments) {
		this.extendComments = extendComments;
	}

	public String getExtendDateFrom() {
		return extendDateFrom;
	}

	public void setExtendDateFrom(String extendDateFrom) {
		this.extendDateFrom = extendDateFrom;
	}

	public String getExtendDateTo() {
		return extendDateTo;
	}

	public void setExtendDateTo(String extendDateTo) {
		this.extendDateTo = extendDateTo;
	}

	public String getExtendAmount() {
		return extendAmount;
	}

	public void setExtendAmount(String extendAmount) {
		this.extendAmount = extendAmount;
	}

	public int getNumberOfStar() {
		return numberOfStar;
	}

	public void setNumberOfStar(int numberOfStar) {
		this.numberOfStar = numberOfStar;
	}

	public void setClientContact(String clientContact) {
		this.clientContact = clientContact;
	}

	public String getClientContact() {
		return clientContact;
	}

	public void setClose_flag(String close_flag) {
		this.close_flag = close_flag;
	}

	public String getClose_flag() {
		return close_flag;
	}

	public String getCommunicationName() {
		return communicationName;
	}

	public void setCommunicationName(String communicationName) {
		this.communicationName = communicationName;
	}

	public Map<String, String> getEmployee_basicMap() {
		return employee_basicMap;
	}

	public void setEmployee_basicMap(Map<String, String> employee_basicMap) {
		this.employee_basicMap = employee_basicMap;
	}

	public File getAttachedDoc() {
		return attachedDoc;
	}

	public void setAttachedDoc(File attachedDoc) {
		this.attachedDoc = attachedDoc;
	}

	public String getAttachedDocFileName() {
		return attachedDocFileName;
	}

	public void setAttachedDocFileName(String attachedDocFileName) {
		this.attachedDocFileName = attachedDocFileName;
	}

	public String getAttachedDocContentType() {
		return attachedDocContentType;
	}

	public void setAttachedDocContentType(String attachedDocContentType) {
		this.attachedDocContentType = attachedDocContentType;
	}

	public String getAttachement1() {
		return attachement1;
	}

	public void setAttachement1(String attachement1) {
		this.attachement1 = attachement1;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMail_text() {
		return mail_text;
	}

	public String getExtpoAttach() {
		return extpoAttach;
	}

	public void setExtpoAttach(String extpoAttach) {
		this.extpoAttach = extpoAttach;
	}

	public String getExtagreementAttach() {
		return extagreementAttach;
	}

	public void setExtagreementAttach(String extagreementAttach) {
		this.extagreementAttach = extagreementAttach;
	}

	public void setMail_text(String mail_text) {
		this.mail_text = mail_text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getMsg_txt() {
		return msg_txt;
	}

	public void setMsg_txt(String msg_txt) {
		this.msg_txt = msg_txt;
	}

	public List<ClientSupportBean> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ClientSupportBean> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List getCountStar() {
		return countStar;
	}

	public void setCountStar(List countStar) {
		this.countStar = countStar;
	}

	public List<ClientSupportBean> getContactNameToConfigure() {
		return contactNameToConfigure;
	}

	public void setContactNameToConfigure(
			List<ClientSupportBean> contactNameToConfigure) {
		this.contactNameToConfigure = contactNameToConfigure;
	}



}
