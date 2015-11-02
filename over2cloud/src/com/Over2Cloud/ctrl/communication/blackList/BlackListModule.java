package com.Over2Cloud.ctrl.communication.blackList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.hr.CommonContactCtrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BlackListModule extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(CommonContactCtrl.class);
	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	
	private List<GridDataPropertyView> ViewBlackListedNumGrid = new ArrayList<GridDataPropertyView>();
	private List<Object> viewblacklList = null;
	private List<ConfigurationUtilBean> blacklistNameee = null;
	private List<ConfigurationUtilBean> blackListFor = null;
	private String smsNotification = null;
	private String emailNotification = null;
	private String contact_name;
	private Integer rows = 0;
	private String subGroupId;
	private String department;
	private String blistfor;
	private String email;
	private String module;
	private JSONArray commonJSONArray = new JSONArray();
	Map<String, String> appDetails = null;
	private Map<Integer, String> deptMap;

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
	private String from_date = null;
	private String to_date = null;
	private String duration = null;
	private String reason = null;

	public String beforeBlackListAdd()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setBlackListAdd();
		}
		catch (Exception e)
		{
			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setBlackListAdd()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			blacklistNameee = new ArrayList<ConfigurationUtilBean>();
			blackListFor = new ArrayList<ConfigurationUtilBean>();
			appDetails = new LinkedHashMap<String,String>();
			appDetails = CommonWork.fetchAppAssignedUser(connectionSpace, userName);
			String query = " select id,contact_subtype_name from contact_sub_type ORDER BY contact_subtype_name";
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			deptMap = new HashMap<Integer, String>();
			if (data != null && data.size() > 0 && data.get(0) != null)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						deptMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
					}
				}
			}
			List<GridDataPropertyView> blacklist = Configuration.getConfigurationData("mapped_exclusion_configuration", accountID, connectionSpace, "", 0, "table_name", "exclusion_configuration");
			if (blacklist != null && blacklist.size()>0)
			{
				for (GridDataPropertyView gdp : blacklist)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T"))
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
						blacklistNameee.add(obj);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("D") )
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
						blackListFor.add(obj);
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getContact()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String query = "SELECT id,emp_name,mobile_no,email_id FROM primary_contact WHERE sub_type_id=" + subGroupId + " ORDER BY emp_name";
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null & dataList.size() > 0)
				{
					for (Object c : dataList)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						if(objVar[1]!=null)
						{
							listObject.put("id", objVar[0].toString());
							listObject.put("name", objVar[1].toString()+"-"+objVar[2].toString()+"-"+objVar[3].toString());
							commonJSONArray.add(listObject);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + "Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloudmethod getContBySubGroupId4User of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String insertDataBlackList()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		boolean status = false;
		if (sessionFlag)
		{
			InsertDataTable ob = null;
			TableColumes ob1 = null;
			try
			{
				String parmName = null;
				String paramValue = null;
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> blacklist = Configuration.getConfigurationData("mapped_exclusion_configuration", accountID, connectionSpace, "", 0, "table_name", "exclusion_configuration");

				if (blacklist != null && blacklist.size() > 0)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : blacklist)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("exclusion", tableColume, connectionSpace);
					String mob=null,email=null;	String Name1 = null;
					String query = "SELECT emp.emp_name,dept.contact_subtype_name,emp.mobile_no,emp.email_id FROM primary_contact AS emp INNER JOIN contact_sub_type AS dept ON dept.id=emp.sub_type_id AND	emp.id='" + getContact_name() + "'";
					List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
					if (dataList != null & dataList.size() > 0)
					{
						for (Object c : dataList)
						{
							Object[] objVar = (Object[]) c;
							Name1 = (objVar[0].toString());
							department = (objVar[1].toString());
							mob = (objVar[2].toString());
							email = (objVar[3].toString());
						}
					}
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							parmName = it.next().toString();
							paramValue = request.getParameter(parmName);

							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("__checkbox_emailNotification") && !parmName.equalsIgnoreCase("smsNotification")&& !parmName.equalsIgnoreCase("mobile_no") && !parmName.equalsIgnoreCase("__checkbox_smsNotification") && !parmName.equalsIgnoreCase("emailNotification"))
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
						ob = new InsertDataTable();
						ob.setColName("mobile_no");
						ob.setDataName(mob);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("sms_ack");
						ob.setDataName(smsNotification);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("email_ack");
						ob.setDataName(emailNotification);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("user_name");
						ob.setDataName(DateUtil.makeTitle(userName));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("created_date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("created_time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
						status = cbt.insertIntoTable("exclusion", insertData, connectionSpace);
					}
					if (status)
					{
						String OrganizationNo = null;
						String orgEmail = null;
						String orgName = null;
						String orgAddress = null;
						String que = "SELECT support_no,support_email,address,city,company_name FROM organization_level1";
						List data = cbt.executeAllSelectQuery(que, connectionSpace);
						if (data != null & data.size() > 0)
						{
							for (Object c : data)
							{
								Object[] objVar = (Object[]) c;
								OrganizationNo = (objVar[0].toString());
								orgEmail = (objVar[1].toString());
								orgAddress = (objVar[2].toString()) + "," + (objVar[3].toString());
								orgName = (objVar[4].toString());
							}
						}
						String UrlMail = "over2cloud.co.in";
						// Mail
						// Sending***************************************

						if (emailNotification.equals("true"))
						{
							if (getEmail() != null && getEmail() != "")
							{
								if (getDuration().equalsIgnoreCase("Forever"))
								{
									sendEmail(mob, Name1, OrganizationNo, getBlistfor(), UrlMail, email, "Forever", getModule(), getReason(), orgEmail, orgName, orgAddress);
								}
								else
								{
									sendEmail(mob, Name1, OrganizationNo, getBlistfor(), UrlMail, email, getFrom_date(), getModule(), getReason(), orgEmail, orgName, orgAddress, getTo_date());
								}
							}
						}
						// SMS
						// Sending***************************************
						if (smsNotification.equals("true"))
						{
							if (mob != null && mob != "")
							{
								if (getDuration().equalsIgnoreCase("Forever"))
								{
									sendSMS(mob, Name1, OrganizationNo, getBlistfor(), "Forever");
								}
								else
								{
									sendSMS(mob, Name1, OrganizationNo, getBlistfor(), getFrom_date(), getTo_date());
								}
							}
						}
						addActionMessage("Data Save Successfully!!!");
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!!!!");
						returnResult = ERROR;
					}
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	private boolean sendSMS(String... param)
	{
		boolean smsFlag = true;// true means problem in email sending
		try
		{
			String sms = null;
			if (param[4].equalsIgnoreCase("Forever"))
			{
				sms = "Dear " + param[1] + " as requested your " + param[3] + " has been bloocked Forever for Future Alerts" + " Kindaly Contact " + param[2] + " for any Support";
			}
			else
			{
				sms = "Dear " + param[1] + " as requested your " + param[3] + " has been bloocked " + param[4] + " to " + param[5] + " for Future Alerts" + " Kindaly Contact " + param[2] + "for any Support";
			}
			List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			ob = new InsertDataTable();
			ob.setColName("msisdn");
			ob.setDataName(param[0]);
			insertData1.add(ob);

			ob = new InsertDataTable();
			ob.setColName("msg");
			ob.setDataName(sms);
			insertData1.add(ob);

			ob = new InsertDataTable();
			ob.setColName("date");
			ob.setDataName(DateUtil.getCurrentDateUSFormat());
			insertData1.add(ob);

			ob = new InsertDataTable();
			ob.setColName("time");
			ob.setDataName(DateUtil.getCurrentTime());
			insertData1.add(ob);
			smsFlag = new MsgMailCommunication().addMessage(param[1], "Admin", param[0], sms, "", "Pending", "0", "Common");
		}
		catch (Exception e)
		{
			smsFlag = true;
			e.printStackTrace();
		}
		return smsFlag;
	}

	private boolean sendEmail(String... param)
	{
		boolean emailFlag = true;// true means problem in email sending
		try
		{
			if (param[0] != null)
			{
				/*
				 * if (param[6].equalsIgnoreCase("Forever")) { period =
				 * "Forever"; } else { period = param[6] + " to " + param[10]; }
				 */
				StringBuffer buffer = new StringBuffer("<html><body><p><strong>Dear " + param[1] + "," + "<br />As requested, your contact details has been excluded for future " + param[3] + " alerts</p>" + "<br/>that are automated generating from the application." + "<br/>Here are the details:" + "<p><center><table border='1'><tr>" + "<td width='200px'>Excluded For:</td width='200px'><td>" + param[3] + "");

				if (param[3] != null && param[3].equalsIgnoreCase("Mobile Number"))
				{
					buffer.append("<tr><td width='200px'>Mobile No.: </td><td width='200px'>" + param[0] + " </td></tr>");
				}
				else if (param[3] != null && param[3].equalsIgnoreCase("Email ID"))
				{
					buffer.append("<tr><td width='200px'>Email ID: </td><td width='200px'>" + param[5] + " </td></tr>");
				}
				else if (param[3] != null && param[3].equalsIgnoreCase("Mobile No & Email"))
				{
					buffer.append("<tr><td width='200px'>Mobile No.: </td><td width='200px'>" + param[0] + " </td></tr>");
					buffer.append("<tr><td width='200px'>Email ID: </td><td width='200px'>" + param[5] + " </td></tr>");
				}
				else if (param[7] != null)
				{
					buffer.append("<tr><td width='200px'>From Module:  </td><td width='200px'>" + param[7] + " </td></tr>");
				}
				else if (param[6] != null)
				{
					String period1 = null;
					if (param[6].equalsIgnoreCase("Forever"))
					{
						period1 = "Forever";
					}
					else
					{
						period1 = param[6] + " To " + param[12];
					}

					buffer.append("<tr><td width='200px'>Period : </td><td width='200px'>" + period1 + " </td></tr>");
				}
				else if (param[8] != null)
				{
					buffer.append("</td></tr><tr><td width='200px'>Reason: </td width='200px'><td>" + param[8] + "</td></tr></table></center></p><p>For any further assistance, please feel free to contact<strong>" + param[2] + "</strong> &amp; also mail with details to  " + param[2] + "," + param[9] + "+<strong>" + "</strong></p><p>&nbsp;</p><p><span style='font-size: smal;" + "color: #993366;'><strong><span style='font-family: 'times new roman', times;'>Thanks &amp; Regards,</span></strong></span><br /><span style='font-size: small; color: #993366;'><strong><span style='font-family: 'times new roman', times;'>Application Team.</span></strong></span></p>" + "<br /><span style='font-size: small; color: #993366;'><strong><span style='font-family: 'times new roman', times;'>" + param[10] + "</span></strong></span></p>" + "<br /><span style='font-size: small; color: #993366;'><strong><span style='font-family: 'times new roman', times;'>" + param[11] + "</span></strong></span></p>"

					+ "<p><span style='font-family: 'arial black', 'avant garde'; font-size: small; color: #ff9900;'>DreamSol TeleSolutions Pvt. Ltd.</span><br /><span style='color: #993366;'>An ISO 9001:2000 Certified Company...</span><br />India: C-52, Sector-2, Noida-201301<br /><span style='color: #3366ff;'><a name='www.dreamsol.biz'></a>www.dreamsol.biz | <a name='www.punchsms.com'></a>www.punchsms.com | <a name='www.mgov.in'></a>www.mgov.in | <a name='www.over2cloud.com'></a>www.over2cloud.com</span><br /><span style='color: #000080;'><strong>Innovating Business Processes via automated ICT solutions integrated with unified communications... offered over Public / Private Cloud &amp; Mobile Apps.</strong></span><br />---------------------------------------" + "------------------------------------------------------------------------------<br /><span style='color: #800080;'>This e-mail may contain proprietary and confidential information and is sent for the intended recipient(s) only. If by an addressing or transmission error this mail has been misdirected, you are requested to please notify us by reply e-mail and delete this mail immediately. You are also hereby notified that any use, any form of reproduction, dissemination, copying, disclosure, modification, distribution and / or publication of this e-mail message, contents or its attachment other than by its intended recipient(s) is strictly prohibited. \"Thank you.\"</span><br />\"<span style='color: #99cc00;'>Every 3000 sheets of paper cost us a tree. Let's consider our environmental responsibility before printing this e-mail -</span> <strong><span style='color: #ff0000;'>Save paper</span></strong>.\"<br /><br /></p></body></html>");

				}
				emailFlag = new MsgMailCommunication().addMail(param[1], "Admin", param[5], "Exclusion of your " + param[3] + " for Automated Alerts ", buffer.toString(), "", "Pending", "0", "", "FM");

			}

		}
		catch (Exception e)
		{
			emailFlag = true;
			e.printStackTrace();
		}
		return emailFlag;
	}

	public String beforeBlackListView()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setblackListView();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setblackListView()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			ViewBlackListedNumGrid.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_exclusion_configuration", accountID, connectionSpace, "", 0, "table_name", "exclusion_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					ViewBlackListedNumGrid.add(gpv);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewBlacKListDataGrid()
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
				query1.append("select count(*) from exclusion where user_name='" + userName + "'");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					StringBuilder tempQuery = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_exclusion_configuration", accountID, connectionSpace, "exclusion_configuration");
					if (fieldNames != null && fieldNames.size() > 0)
					{
						if (fieldNames != null && fieldNames.size() > 0)
						{
							for (Iterator it = fieldNames.iterator(); it.hasNext();)
							{
								Object obdata = (Object) it.next();
								if (obdata != null)
								{

									if (obdata.toString().equalsIgnoreCase("contact_name"))
									{
										tempQuery.append("emp.emp_name,");
									}
									else
									{
										tempQuery.append("black." + obdata.toString() + ",");
									}
								}
							}
						}
					}

					query.append(tempQuery.substring(0, tempQuery.length() - 1));
					query.append(" FROM exclusion AS black");
					query.append(" LEFT JOIN primary_contact AS emp ON emp.id = black.contact_name");

					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" and black." + getSearchField() + " = '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" and  black." + getSearchField() + " like'%" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" and  black." + getSearchField() + " like '" + getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" and  black." + getSearchField() + " <> '" + getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" and black." + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}
					// query.append(" limit " + from + "," + to);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						viewblacklList = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									else if (fieldNames.get(k).toString().equalsIgnoreCase("from_date"))
									{
										if (obdata[k].toString() != null)
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("to_date"))
									{
										if (obdata[k].toString() != null)
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("email_ack"))
									{
										if (obdata[k].toString() != null && obdata[k].toString().equalsIgnoreCase("true"))
										{
											one.put(fieldNames.get(k).toString(),"Yes");
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "No");
										}
									}
									else if (fieldNames.get(k).toString().equalsIgnoreCase("sms_ack"))
									{
										if (obdata[k].toString() != null && obdata[k].toString().equalsIgnoreCase("true"))
										{
											one.put(fieldNames.get(k).toString(),"Yes");
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "No");
										}
									}
									else
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
								else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setViewblacklList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String searchblackListview()
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
				query1.append("select count(*) from exclusion where user_name='" + userName + "'");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
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

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_exclusion_configuration", accountID, connectionSpace, "exclusion_configuration");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dynamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
									query.append(obdata.toString() + ",");
								else
									query.append(obdata.toString());
							}
							i++;
						}
					}
					query.append(" from exclusion");
				/*	if (getMobileNo() != null && getMobileNo() != "")
					{
						query.append(" where ");
						query.append(" mobile_no  like  " + "'" + getMobileNo() + "%'" + "and user='" + userName + "'");
					}
					else*/ if (from_date != null && from_date != "" && to_date != null && to_date != "")
					{
						query.append(" where ");
						query.append("from_date between'" + DateUtil.convertDateToUSFormat(getFrom_date()) + "' and  '" + DateUtil.convertDateToUSFormat(getTo_date()) + "'" + " and user_name='" + userName + "'");
					}
					// add search query based on the search operation

					else if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" where ");
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'" + " and user='" + userName + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" where ");
						query.append(" " + getSearchField() + " like'%" + getSearchString() + "%'" + " and user='" + userName + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" where ");
						query.append(" " + getSearchField() + " like'" + getSearchString() + "%'" + " and user='" + userName + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" where ");
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'" + " and user='" + userName + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" where ");
						query.append(" " + getSearchField() + " like'%" + getSearchString() + "'" + " and user='" + userName + "'");
					}
					query.append(" limit " + from + "," + to);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						viewblacklList = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									else
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							}
							Listhb.add(one);
						}
						setViewblacklList(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeBlackListExcelUpload()
	{

		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;

	}

	public String beforeBlackListGridDataDownload()
	{

		String returnString = ERROR;
		if (ValidateSession.checkSession())
		{
			returnString = SUCCESS;
		}
		else
		{
			returnString = LOGIN;
		}
		return returnString;
	}

	public String modifyBlackList()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("exclusion", wherClause, condtnBlock, connectionSpace);
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
				cbt.deleteAllRecordForId("exclusion", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{

			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		return SUCCESS;

	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<Object> getViewblacklList()
	{
		return viewblacklList;
	}

	public void setViewblacklList(List<Object> viewblacklList)
	{
		this.viewblacklList = viewblacklList;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	
	public List<GridDataPropertyView> getViewBlackListedNumGrid()
	{
		return ViewBlackListedNumGrid;
	}

	public void setViewBlackListedNumGrid(List<GridDataPropertyView> viewBlackListedNumGrid)
	{
		ViewBlackListedNumGrid = viewBlackListedNumGrid;
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<ConfigurationUtilBean> getBlacklistNameee()
	{
		return blacklistNameee;
	}

	public void setBlacklistNameee(List<ConfigurationUtilBean> blacklistNameee)
	{
		this.blacklistNameee = blacklistNameee;
	}

	
	public String getFrom_date()
	{
		return from_date;
	}

	public void setFrom_date(String from_date)
	{
		this.from_date = from_date;
	}

	public String getTo_date()
	{
		return to_date;
	}

	public void setTo_date(String to_date)
	{
		this.to_date = to_date;
	}

	public List<ConfigurationUtilBean> getBlackListFor()
	{
		return blackListFor;
	}

	public void setBlackListFor(List<ConfigurationUtilBean> blackListFor)
	{
		this.blackListFor = blackListFor;
	}
	public String getSmsNotification()
	{
		return smsNotification;
	}

	public void setSmsNotification(String smsNotification)
	{
		this.smsNotification = smsNotification;
	}

	public String getEmailNotification()
	{
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification)
	{
		this.emailNotification = emailNotification;
	}



	public String getContact_name()
	{
		return contact_name;
	}

	public void setContact_name(String contactName)
	{
		contact_name = contactName;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public String getBlistfor()
	{
		return blistfor;
	}

	public void setBlistfor(String blistfor)
	{
		this.blistfor = blistfor;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public String getSubGroupId()
	{
		return subGroupId;
	}

	public void setSubGroupId(String subGroupId)
	{
		this.subGroupId = subGroupId;
	}

	public Map<Integer, String> getDeptMap()
	{
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<String, String> getAppDetails()
	{
		return appDetails;
	}

	public void setAppDetails(Map<String, String> appDetails)
	{
		this.appDetails = appDetails;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}
	
}