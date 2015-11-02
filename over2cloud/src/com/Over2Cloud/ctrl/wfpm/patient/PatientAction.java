package com.Over2Cloud.ctrl.wfpm.patient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.wfpm.client.ClientHelper3;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PatientAction extends ActionSupport implements ServletRequestAware
{

	/**
	 * Patient Status and add view
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	private String status;
	private String empId = session.get("empIdofuser").toString().split("-")[1];
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private String offeringLevel = session.get("offeringLevel").toString();
	String cId = new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, userName, connectionSpace)[0];
	static final Log log = LogFactory.getLog(PatientAction.class);
	private String isExistingClient;
	private String offeringName, first_name, status_view;
	private List<Object> masterViewList;
	private String offeringExist;
	private String offering, type;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> patientTextBox = null;
	private List<ConfigurationUtilBean> patientDropDown = null;
	private List<ConfigurationUtilBean> patientBasicTextBox = null;
	private boolean OLevel1;
	private boolean OLevel2;
	private boolean OLevel3;
	private boolean OLevel4;
	private boolean OLevel5;
	private LinkedHashMap<String, String> deptMap;
	private Map<String, String> escL2Emp = null;
	private Map<String, String> escL3Emp = null;
	private Map<String, String> escL4Emp = null;
	private Map<String, String> escL5Emp = null;
	private Map<String, String> escL5EmpNxtLevel = null;
	private String l1, l2, l3, l4, l5, div, sId, sName, nId, nName;

	private String OLevel1LevelName;
	private String OLevel2LevelName, traceid;
	private String OLevel3LevelName;
	private String OLevel4LevelName;
	private String OLevel5LevelName;
	private LinkedHashMap<String, String> verticalMap;
	private LinkedHashMap<String, String> rhTypeMap;
	private LinkedHashMap<String, String> rhSubTypeMap;
	private LinkedHashMap<String, String> stageMap;
	private LinkedHashMap<String, String> corporateMap;
	private LinkedHashMap<String, String> associateMap;
	private LinkedHashMap<String, String> sourceMap;
	private LinkedHashMap<String, String> countryMap;
	private Map<String, String> locationList;
	private String patien_id;
	private String mobile;
	private String dept_name;
	private boolean datavalue = true;
	private String totalmaleCount;
	private String totalFemaleCount;

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
	private String count;

	private JSONArray jsonArray;
	private String offeringId;
	private String Offeringlevel;
	private JSONObject jsonObject;
	private String rowid;

	private Map<String, Object> offeringMap;
	private Map<String, Object> relManagerMap;
	private Map<String, String> empMap;
	private String laststatus;
	private String pname;
	private String depName;
	private String sent_questionair;
	private String searchParameter;

	private Map<String, String> profilemap;
	private JSONArray dataArray = new JSONArray();

	private String gender, nationality, city, blood_group, patient_category, patient_type, corporate, referral, last_name, age, email, address;
	private String crm_id, createdBy, lastComment, nextComment, lastDate, date, relName, drName, stage;

	private String l2_99, l3_99, l4_99, l5_99, l2_100, l3_100, l4_100, l5_100, l2_101, l3_101, l4_101, l5_101, l2_102, l3_102, l4_102, l5_102, l2_103, l3_103, l4_103, l5_103;

	public String beforePatientStatus()
	{
		// System.out.println(" before PatientStatus");
		if (!ValidateSession.checkSession())
			return LOGIN;
		setPatientStatusViewProp();
		return SUCCESS;
	}

	public void setPatientStatusViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_patientcrm_status_data_master", accountID, connectionSpace, "", 0, "table_name", "patientcrm_status_data_master");

			for (GridDataPropertyView gdp : returnResult)
			{

				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setAlign(gdp.getAlign());
				gpv.setWidth(gdp.getWidth());
				if (gdp.getFormatter() != null)
					gpv.setFormatter(gdp.getFormatter());
				masterViewProp.add(gpv);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String viewPatientStatusGrid()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from patientcrm_status_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from patientcrm_status_data as psd ");
				queryEnd.append(" left join activity_type as act on psd.status = act.id ");
				List fieldNames = Configuration.getColomnList("mapped_patientcrm_status_data_master", accountID, connectionSpace, "patientcrm_status_data_master");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("status"))
						{
							queryTemp.append(" act.act_name, ");
						} else if (obdata.toString().equalsIgnoreCase("offering_name"))
						{
							queryTemp.append(" off.subofferingname, ");
							queryEnd.append(" left join offeringlevel3 as off on off.id = psd.offering_name ");
						} else
						{
							queryTemp.append("psd." + obdata.toString() + ",");
						}

					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" psd.user <> '0' ");
				if (offeringName != null && !offeringName.equals("-1"))
				{
					query.append("and psd.offering = '");
					query.append(offeringName);
					query.append("' ");
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceMaster"))
					{
						setSearchField(" sm.sourceName ");
					} else if (getSearchField().toString().equalsIgnoreCase("status"))
					{
						setSearchField(" cs.statusName ");
					} else if (getSearchField().toString().equalsIgnoreCase("location"))
					{
						setSearchField(" loc.name ");
					} else if (getSearchField().toString().equalsIgnoreCase("acManager"))
					{
						setSearchField(" cbd1.empName ");
					} else
					{
						setSearchField(" cbd." + getSearchField());
					}

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				query.append(" order by psd.status limit " + from + "," + to);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				System.out.println("query.toString()>>" + query.toString());
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{

							// System.out.println("obdata[k].toString().length()  "+
							// obdata[k].toString().length());
							if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("NA") && obdata[k].toString().length() > 0)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								} else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l2"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										} else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}

									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l3"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										} else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l4"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										} else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l5"))
									{
										if (!obdata[k].toString().contains(","))
										{
											obdata[k] = getNameByccid(obdata[k].toString()).toString();
										} else
										{
											String s = " ";
											String empArr[] = obdata[k].toString().trim().split(",");
											for (int i = 0; i < empArr.length; i++)
											{
												s = getNameByccid(empArr[i]).toString();
												s = s + ", " + s;
												obdata[k] = s;
											}
										}
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							} else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					// System.out.println(getMasterViewList());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String getNameByccid(String string)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		String emp = null;

		try
		{
			qryString.append("select emp.emp_name, cc.id from primary_contact as emp inner join manage_contact as cc on cc.emp_id=emp.id");
			qryString.append(" where cc.id=" + string + "");
			// System.out.println("qryString " + qryString);
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						emp = objectCol[0].toString();
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return emp;
	}

	public String beforeStatusAdd()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageDataForStatus();

			escL2Emp = new LinkedHashMap<String, String>();
			escL3Emp = new LinkedHashMap<String, String>();
			escL4Emp = new LinkedHashMap<String, String>();
			escL5Emp = new LinkedHashMap<String, String>();

			escL2Emp = getComplianceAllContacts("2", "WFPM");
			escL3Emp = getComplianceAllContacts("3", "WFPM");
			escL4Emp = getComplianceAllContacts("4", "WFPM");
			escL5Emp = getComplianceAllContacts("5", "WFPM");

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String fetchAllActivity()
	{
		try
		{
			System.out.println(id);
			System.out.println("SELECT id,act_name FROM activity_type WHERE act_type='Routine' AND act_rel_subtype IN ('" + id + "') AND act_stage='" + rowid + "'");
			List list = new createTable().executeAllSelectQuery("SELECT id,act_name FROM activity_type WHERE act_type='Routine' AND act_rel_subtype IN (" + id + ") AND act_stage='" + rowid + "'", connectionSpace);
			if (list != null && list.size() > 0)
			{
				jsonArray = new JSONArray();
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						jsonObject = new JSONObject();
						jsonObject.put("ID", object[0]);
						jsonObject.put("name", object[1]);
						jsonArray.add(jsonObject);
					}
				}
			}
			return SUCCESS;
		} catch (Exception e)
		{
			return ERROR;
		}
	}

	private Map<String, String> getComplianceAllContacts(String level, String module)
	{
		String deptId = "";

		String[] loggedUserData = new String[7];
		loggedUserData = getEmpDetailsByUserName("WFPM", userName);
		if (loggedUserData != null && loggedUserData.length > 0)
		{
			deptId = loggedUserData[4];
			// System.out.println("deptId >>>>>>>11111 " + deptId);
		}

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
			qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE for_contact_sub_type_id=" + deptId + " and module_name='" + module + "' AND work_status!=1 AND emp.status='0' order by emp_name asc");
			System.out.println("qryString " + qryString);
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return empMap;
	}

	public String[] getEmpDetailsByUserName(String moduleName, String userName)
	{
		String[] values = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

			StringBuilder query = new StringBuilder();
			query.append("select contact.id,emp.emp_name,emp.mobile_no,emp.email_id,emp.sub_type_id as deptid, dept.contact_subtype_name,emp.id as empid from primary_contact as emp ");
			query.append(" inner join manage_contact as contact on contact.emp_id=emp.id");
			query.append(" inner join contact_sub_type as dept on emp.sub_type_id=dept.id ");
			query.append(" inner join contact_type as gi on dept.contact_type_id=gi.id ");
			query.append(" inner join user_account as uac on emp.user_account_id=uac.id where contact.module_name='" + moduleName + "' and uac.user_name='");
			query.append(userName + "' and contact.for_contact_sub_type_id=dept.id");

			List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				values = new String[7];
				Object[] object = (Object[]) dataList.get(0);
				values[0] = getValueWithNullCheck(object[0]);
				values[1] = getValueWithNullCheck(object[1]);
				values[2] = getValueWithNullCheck(object[2]);
				values[3] = getValueWithNullCheck(object[3]);
				values[4] = getValueWithNullCheck(object[4]);
				values[5] = getValueWithNullCheck(object[5]);
				values[6] = getValueWithNullCheck(object[6]);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return values;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String nextEscMap4Emp()
	{
		/*
		 * System.out.println("l1123 " + l1); System.out.println("l2 " + l2);
		 * System.out.println("l3 " + l3); System.out.println("l4 " + l4);
		 * System.out.println("div " + div); System.out.println(".length()>1   "
		 * + l1.length()); System.out.println(".length()>1   " + l2.length());
		 * System.out.println(".length()>1   " + l3.length());
		 * System.out.println(".length()>1   " + l4.length());
		 */
		String empID = " ";

		if (!l1.equalsIgnoreCase("null") && !l1.equalsIgnoreCase("undefined"))
		{
			empID = l1;
			// System.out.println("get 2");
		}
		if (!l2.equalsIgnoreCase("null") && !l2.equalsIgnoreCase("undefined"))
		{
			empID = empID + ", " + l2;
			// System.out.println("get 3");
		}
		if (!l3.equalsIgnoreCase("null") && !l3.equalsIgnoreCase("undefined"))
		{
			empID = empID + ", " + l3;
			// System.out.println("get 4");
		}
		if (!l4.equalsIgnoreCase("null") && !l4.equalsIgnoreCase("undefined"))
		{
			empID = empID + ", " + l4;
			// System.out.println("get 5");
		}

		// System.out.println("empID " + empID);
		escL5EmpNxtLevel = new LinkedHashMap<String, String>();

		escL5EmpNxtLevel = getComplianceAllContacts4NxtExc(empID, "WFPM");

		return SUCCESS;

	}

	private Map<String, String> getComplianceAllContacts4NxtExc(String empID2, String module)
	{

		String deptId = "";

		String[] loggedUserData = new String[7];
		loggedUserData = getEmpDetailsByUserName("WFPM", userName);
		if (loggedUserData != null && loggedUserData.length > 0)
		{
			deptId = loggedUserData[4];
			// System.out.println("deptId    " + deptId);
		}

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		Map<String, String> empMap = new LinkedHashMap<String, String>();
		try
		{
			qryString.append("SELECT cc.id,emp.emp_name FROM primary_contact AS emp");
			qryString.append(" INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id");
			qryString.append(" WHERE  for_contact_sub_type_id=" + deptId + " and cc.id NOT IN(" + empID2 + ") and module_name='" + module + "' AND work_status!=1 AND emp.status='0' order by emp_name asc");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						empMap.put(objectCol[0].toString(), objectCol[1].toString());
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Compliance  method getComplianceContacts of class " + getClass(), exp);
		}
		return empMap;
	}

	public void setAddPageDataForStatus()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Build map for text box and drop down for employee basic
			// information
			ClientHelper3 ch3 = new ClientHelper3();
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_patientcrm_status_data_master", accountID, connectionSpace, "", 0, "table_name", "patientcrm_status_data_master");
			patientTextBox = new ArrayList<ConfigurationUtilBean>();
			patientDropDown = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("questionnaire") && !gdp.getColomnName().equalsIgnoreCase("escalation") && !gdp.getColomnName().equalsIgnoreCase("l2") && !gdp.getColomnName().equalsIgnoreCase("tat2") && !gdp.getColomnName().equalsIgnoreCase("l3") && !gdp.getColomnName().equalsIgnoreCase("tat3") && !gdp.getColomnName().equalsIgnoreCase("l4")
						&& !gdp.getColomnName().equalsIgnoreCase("tat4") && !gdp.getColomnName().equalsIgnoreCase("l5") && !gdp.getColomnName().equalsIgnoreCase("tat5"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
					patientTextBox.add(obj);
				}

				if (gdp.getColType().trim().equalsIgnoreCase("Date") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("questionnaire") && !gdp.getColomnName().equalsIgnoreCase("escalation") && !gdp.getColomnName().equalsIgnoreCase("l2") && !gdp.getColomnName().equalsIgnoreCase("tat2") && !gdp.getColomnName().equalsIgnoreCase("l3") && !gdp.getColomnName().equalsIgnoreCase("tat3") && !gdp.getColomnName().equalsIgnoreCase("l4")
						&& !gdp.getColomnName().equalsIgnoreCase("tat4") && !gdp.getColomnName().equalsIgnoreCase("l5") && !gdp.getColomnName().equalsIgnoreCase("tat5"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}

					if (gdp.getColomnName().equalsIgnoreCase("offering_name"))
					{
						offering = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							offeringExist = "true";
						else
							offeringExist = "false";
					}

				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("targetAchieved") && !gdp.getColomnName().equalsIgnoreCase("questionnaire") && !gdp.getColomnName().equalsIgnoreCase("escalation") && !gdp.getColomnName().equalsIgnoreCase("l2") && !gdp.getColomnName().equalsIgnoreCase("tat2")
						&& !gdp.getColomnName().equalsIgnoreCase("l3") && !gdp.getColomnName().equalsIgnoreCase("tat3") && !gdp.getColomnName().equalsIgnoreCase("l4") && !gdp.getColomnName().equalsIgnoreCase("tat4") && !gdp.getColomnName().equalsIgnoreCase("l5") && !gdp.getColomnName().equalsIgnoreCase("tat5"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
				}

			}

			// For offering details

			// oferring taken by client in configuration
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{

				oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);
				if (level > 0)
				{
					setOLevel1(true);
					OLevel1LevelName = oLevels[1];
				}
				if (level > 1)
				{
					OLevel2 = true;
					OLevel2LevelName = oLevels[2];
				}
				if (level > 2)
				{
					OLevel3 = true;
					OLevel3LevelName = oLevels[3];
				}
				if (level > 3)
				{
					OLevel4 = true;
					OLevel4LevelName = oLevels[4];
				}
				if (level > 4)
				{
					OLevel5 = true;
					OLevel5LevelName = oLevels[5];
				}

				// Get verticalname list
				verticalMap = new LinkedHashMap<String, String>();
				List verticalData = cbt.executeAllSelectQuery("select id, verticalname from " + "offeringlevel1 order by verticalname", connectionSpace);

				if (verticalData != null)
				{
					for (Object c : verticalData)
					{
						Object[] dataC = (Object[]) c;
						verticalMap.put(dataC[0].toString(), dataC[1].toString());
					}
					verticalData.clear();
				}
				// Get Relationship type list

				rhTypeMap = new LinkedHashMap<String, String>();
				verticalData = cbt.executeAllSelectQuery("SELECT id, name FROM relationship ORDER BY name", connectionSpace);

				if (verticalData != null)
				{
					for (Object c : verticalData)
					{
						Object[] dataC = (Object[]) c;
						rhTypeMap.put(dataC[0].toString(), dataC[1].toString());
					}
					verticalData.clear();
				}
				stageMap = new LinkedHashMap<String, String>();
				verticalData = cbt.executeAllSelectQuery("SELECT id, forcastName FROM forcastcategarymaster ORDER BY forcastName", connectionSpace);

				if (verticalData != null)
				{
					for (Object c : verticalData)
					{
						Object[] dataC = (Object[]) c;
						stageMap.put(dataC[0].toString(), dataC[1].toString());
					}
					verticalData.clear();
				}
			}

		} catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String fetchRhSubType()
	{
		try
		{
			rhSubTypeMap = new LinkedHashMap<String, String>();
			List verticalData = new createTable().executeAllSelectQuery("SELECT id, rel_subtype FROM relationship_sub_type WHERE rel_type IN (" + id + ") ORDER BY rel_subtype", connectionSpace);
			if (verticalData != null)
			{
				for (Object c : verticalData)
				{
					Object[] dataC = (Object[]) c;
					rhSubTypeMap.put(dataC[0].toString(), dataC[1].toString());
				}
				verticalData.clear();
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	String getValueOfArray(Object[] ob, int i)
	{
		String returnString ="NA";
		try
		{
			if (ob != null && ob.length != 0)
			{
				returnString = (ob.length > i && ob[i] != null ? ob[i].toString() : "NA");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnString;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addPatientStatus()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patientcrm_status_data_master", accountID, connectionSpace, "", 0, "table_name", "patientcrm_status_data_master");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					ob1 = new TableColumes();
					ob1.setColumnname("time_gap");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("activity_code");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("activityType");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("offeringname");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("seqOption");
					ob1.setDatatype("varchar(255)");
					Tablecolumesaaa.add(ob1);

					cbt.createTable22("patientcrm_status_data", Tablecolumesaaa, connectionSpace);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					String rhIds[] = new String[0];
					String[] activity = new String[0];
					String[] actCode = new String[0];
					String[] questionnaire = new String[0];
					String[] escalation = new String[0];
					String[] tat2 = new String[0];
					String[] tat3 = new String[0];
					String[] tat4 = new String[0];
					String[] tat5 = new String[0];
					String[] time_gap = new String[0];

					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && !Parmname.contains("l2") && !Parmname.contains("l3") && !Parmname.contains("l4") && !Parmname.contains("l5") && !Parmname.equalsIgnoreCase("verticalname") && !Parmname.equalsIgnoreCase("offeringname") && !Parmname.equalsIgnoreCase("relationship_type")
								&& !Parmname.equalsIgnoreCase("ofrWise"))
						{
							if (Parmname.equalsIgnoreCase("rh_sub_type"))
							{
								rhIds = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("activity_code"))
							{
								actCode = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("status"))
							{
								activity = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("questionnaire"))
							{
								questionnaire = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("escalation"))
							{
								escalation = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("time_gap"))
							{
								time_gap = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("tat2"))
							{
								tat2 = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("tat3"))
							{
								tat3 = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("tat4"))
							{
								tat4 = request.getParameterValues(Parmname);
							} else if (Parmname.equalsIgnoreCase("tat5"))
							{
								tat5 = request.getParameterValues(Parmname);
							} else
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

					ob = new InsertDataTable();
					ob.setColName("user");
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

					//System.out.println("rhIds :"+ Arrays.asList(rhIds));

					String[] l2 = new String[5];
					l2[0] = l2_99;
					l2[1] = l2_100;
					l2[2] = l2_101;
					l2[3] = l2_102;
					l2[4] = l2_103;

					String[] l3 = new String[5];
					l3[0] = l3_99;
					l3[1] = l3_100;
					l3[2] = l3_101;
					l3[3] = l2_102;
					l3[4] = l3_103;

					String[] l4 = new String[5];
					l4[0] = l4_99;
					l4[1] = l4_100;
					l4[2] = l4_101;
					l3[3] = l4_102;
					l4[4] = l4_103;

					String[] l5 = new String[5];
					l5[0] = l5_99;
					l4[1] = l5_100;
					l5[2] = l5_101;
					l5[3] = l5_102;
					l5[4] = l5_103;

					int count = 0;

					for (int i = 0; i < rhIds.length; i++)
					{
						ob = new InsertDataTable();
						ob.setColName("rh_sub_type");
						ob.setDataName(rhIds[i]);
						insertData.add(ob);

						for (int j = 0; j < activity.length && !(activity[j].toString().trim().equalsIgnoreCase("-1")) && (activity[j].toString().trim().length() > 0); j++)
						{
							System.out.println("0000");

							InsertDataTable idt1 = new InsertDataTable();
							idt1.setColName("status");
							idt1.setDataName(getValueWithNullCheck(activity[j]));
							insertData.add(idt1);

							InsertDataTable idt2 = new InsertDataTable();
							idt2.setColName("activity_code");
							idt2.setDataName(getValueWithNullCheck(actCode[j]));
							insertData.add(idt2);

							InsertDataTable idt3 = new InsertDataTable();
							idt3.setColName("questionnaire");
							idt3.setDataName(getValueWithNullCheck(questionnaire[j]));
							insertData.add(idt3);

							InsertDataTable idt4 = new InsertDataTable();
							idt4.setColName("escalation");
							idt4.setDataName(getValueWithNullCheck(escalation[j]));
							insertData.add(idt4);

							InsertDataTable idt5 = new InsertDataTable();
							idt5.setColName("time_gap");
							idt5.setDataName(getValueOfArray(time_gap, j));
							insertData.add(idt5);

							if (!(getValueWithNullCheck(escalation[i]).equalsIgnoreCase("NA")) && getValueWithNullCheck(escalation[i]).equalsIgnoreCase("Yes") && !escalation[i].toString().trim().equalsIgnoreCase(""))
							{

								InsertDataTable idt6 = new InsertDataTable();
								idt6.setColName("l2");
								idt6.setDataName(getValueOfArray(l2, j));
								insertData.add(idt6);

								InsertDataTable idt7 = new InsertDataTable();
								idt7.setColName("l3");
								idt7.setDataName(getValueOfArray(l3, j));
								insertData.add(idt7);

								InsertDataTable idt8 = new InsertDataTable();
								idt8.setColName("l4");
								idt8.setDataName(getValueOfArray(l4, j));
								insertData.add(idt8);

								InsertDataTable idt9 = new InsertDataTable();
								idt9.setColName("l5");
								idt9.setDataName(getValueOfArray(l5, j));
								insertData.add(idt9);

								InsertDataTable idt10 = new InsertDataTable();
								idt10.setColName("tat2");
								idt10.setDataName(getValueOfArray(tat2, j));
								insertData.add(idt10);

								InsertDataTable idt11 = new InsertDataTable();
								idt11.setColName("tat3");
								idt11.setDataName(getValueOfArray(tat3, j));
								insertData.add(idt11);

								InsertDataTable idt12 = new InsertDataTable();
								idt12.setColName("tat4");
								idt12.setDataName(getValueOfArray(tat4, j));
								insertData.add(idt12);

								InsertDataTable idt13 = new InsertDataTable();
								idt13.setColName("tat5");
								idt13.setDataName(getValueOfArray(tat5, j));
								insertData.add(idt13);

								status = cbt.insertIntoTable("patientcrm_status_data", insertData, connectionSpace);

								insertData.remove(idt6);
								insertData.remove(idt7);
								insertData.remove(idt8);
								insertData.remove(idt9);
								insertData.remove(idt10);
								insertData.remove(idt11);
								insertData.remove(idt12);
								insertData.remove(idt13);

							} else
							{
								status = cbt.insertIntoTable("patientcrm_status_data", insertData, connectionSpace);
							}

							if (status)
							{
								count++;
							}
							insertData.remove(idt1);
							insertData.remove(idt2);
							insertData.remove(idt3);
							insertData.remove(idt4);
							insertData.remove(idt5);
						}

						insertData.remove(ob);
					}

					if (status && count > 0)
					{
						addActionMessage("Patient Status Added Successfully!!!");
						returnResult = SUCCESS;
					} else
					{
						addActionMessage("Oops There is some error in data!");
						returnResult = SUCCESS;
					}
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeviewPatient()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		StringBuilder query2 = new StringBuilder("");
		query1.append(" select count(*) from patient_basic_data where gender = 'Male'");
		List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
		if (dataCount != null)
		{
			BigInteger count1 = new BigInteger("3");
			for (Iterator it = dataCount.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				count1 = (BigInteger) obdata;
			}
			totalmaleCount = String.valueOf(count1.intValue());
			// setTotalmaleCount(totalmaleCount);
		}
		query2.append(" select count(*) from patient_basic_data where gender = 'Female'");
		List dataCount2 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
		if (dataCount2 != null)
		{
			BigInteger count2 = new BigInteger("3");
			for (Iterator it = dataCount2.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				count2 = (BigInteger) obdata;
			}
			totalFemaleCount = String.valueOf(count2.intValue());

			// setTotalFemaleCount(totalFemaleCount);
		}

		setPatientBasicViewProp();
		return SUCCESS;
	}

	public String beforePatientBasicView()
	{
		if (!ValidateSession.checkSession())
			return LOGIN;

		return SUCCESS;
	}

	public void setPatientBasicViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("action");
			gpv.setHeadingName("Action");
			gpv.setFormatter("takeAction");
			gpv.setWidth(30);
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_patient_basic_configuration", accountID, connectionSpace, "", 0, "table_name", "patient_basic_configuration");

			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				if (gdp.getColomnName().equalsIgnoreCase("first_name"))
				{
					gpv.setHeadingName("Patient Name");
				} else
				{
					gpv.setHeadingName(gdp.getHeadingName());
				}
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setAlign(gdp.getAlign());
				gpv.setWidth(gdp.getWidth());
				if (gdp.getFormatter() != null)
					gpv.setFormatter(gdp.getFormatter());
				masterViewProp.add(gpv);
			}

			gpv = new GridDataPropertyView();
			gpv.setColomnName("pType");
			gpv.setHeadingName("Patient Rel Type");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			/*
			 * gpv = new GridDataPropertyView(); gpv.setColomnName("status");
			 * gpv.setHeadingName("Status"); gpv.setHideOrShow("false");
			 * gpv.setAlign("center"); gpv.setWidth(120);
			 * masterViewProp.add(gpv);
			 */
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String viewPatientBasicGrid()
	{
		// System.out.println("viewPatientBasicGrid>>>>>");
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from patient_basic_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select distinct ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from patient_basic_data as pbd ");

				List fieldNames = Configuration.getColomnList("mapped_patient_basic_configuration", accountID, connectionSpace, "patient_basic_configuration");
				fieldNames.add("status");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("offering_name"))
						{
							queryTemp.append(" off.subofferingname, ");
							queryEnd.append(" left join offeringlevel3 as off on off.id = pbd.offering_name ");
						} else if (obdata.toString().equalsIgnoreCase("nationality"))
						{
							queryTemp.append(" co.country_name as nationality , ");
							queryEnd.append("left join country as co on co.id=pbd.nationality ");
						} else if (obdata.toString().equalsIgnoreCase("city"))
						{
							queryTemp.append(" ct.city_name as city, ");
							queryEnd.append("left join city as ct on ct.id = pbd.city ");
						} else if (obdata.toString().equalsIgnoreCase("corporate"))
						{
							queryTemp.append(" cbd.corp_name as corporate, ");
							queryEnd.append(" left join  corporate_master as cbd on cbd.id = pbd.corporate ");
						} else if (obdata.toString().equalsIgnoreCase("status"))
						{
							queryTemp.append(" ffa.question_status, ");
							queryEnd.append(" left join feedback_form_answers as ffa on ffa.empId = pbd.id ");
						} else if (obdata.toString().equalsIgnoreCase("patient_type"))
						{
							// src_subtype from pcrm_source where src_type in (
							// select id from relationship where name='Patient'
							// ) order by src_subtype
							queryTemp.append(" pcrm.src_subtype as patient_type , ");
							queryEnd.append(" left join pcrm_source  as pcrm on pcrm.id = pbd.patient_type ");
						} else if (obdata.toString().equalsIgnoreCase("source"))
						{
							queryTemp.append(" psrc.src_subtype as source , ");
							queryEnd.append(" left join pcrm_source as psrc on psrc.id = pbd.source ");
						} else if (obdata.toString().equalsIgnoreCase("type"))
						{
							queryTemp.append(" relsub.rel_subtype as type, ");
							queryEnd.append(" left join relationship_sub_type as relsub on relsub.id=pbd.type ");
						} else if (obdata.toString().equalsIgnoreCase("state"))
						{
							queryTemp.append(" st.state_name as state, ");
							queryEnd.append(" left join state as st on st.id = pbd.state ");
						} else if (obdata.toString().equalsIgnoreCase("patient_category"))
						{
							queryTemp.append(" cat.cat_subtype as patient_category, ");
							queryEnd.append(" left join category_type as cat on cat.id = pbd.patient_category ");
						} else
						{
							queryTemp.append("pbd." + obdata.toString() + ",");
						}
					}
				}
				queryTemp.append(" pbd.type as pType,");
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" pbd.id <> '0' ");

				if (first_name != null && !first_name.equals(""))
				{
					query.append("and concat(pbd.first_name,' ', pbd.last_name) like '%");
					query.append(first_name);
					query.append("%'");
				}
				if (patien_id != null && !patien_id.equals(""))
				{
					query.append("and pbd.patien_id like '%");
					query.append(patien_id);
					query.append("%'");
				}
				if (mobile != null && !mobile.equals(""))
				{
					query.append("and pbd.mobile like '%");
					query.append(mobile);
					query.append("%'");
				}
				if (email != null && !email.equals(""))
				{
					query.append("and pbd.email like '%");
					query.append(email);
					query.append("%'");
				}
				if (city != null && !city.equals(""))
				{
					query.append("and loc.name like '%");
					query.append(city);
					query.append("%'");
				}

				if (address != null && !address.equals(""))
				{
					query.append("and pbd.address like '%");
					query.append(address);
					query.append("%'");
				}
				if (sent_questionair != null && !sent_questionair.equals("-1") && !sent_questionair.equals("undefined"))
				{
					query.append("and pbd.sent_questionair = '");
					query.append(sent_questionair);
					query.append("' ");
				}
				if (patient_category != null && !patient_category.equals("-1") && !patient_category.equals("undefined"))
				{
					query.append("and pbd.patient_category = '");
					query.append(patient_category);
					query.append("' ");
				}
				if (gender != null && !gender.equals("-1") && !gender.equals("undefined"))
				{
					query.append("and pbd.gender = '");
					query.append(gender);
					query.append("' ");
				}

				if (type != null && !type.equals("-1") && !type.equals("undefined"))
				{
					query.append("and pbd.type = '");
					query.append(type);
					query.append("' ");
				}

				if (patient_type != null && !patient_type.equals("-1") && !patient_type.equals("undefined"))
				{
					query.append("and pbd.patient_type = '");
					query.append(patient_type);
					query.append("' ");
				}
				if (searchParameter != null && !searchParameter.equalsIgnoreCase(""))
				{
					query.append(" and ");
					if (fieldNames != null && !fieldNames.isEmpty())
					{
						int k = 0;
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
						{
							String object = iterator.next().toString();
							if (object != null)
							{
								// System.out.println("EEE>>>>"+object.toString());
								if (object.toString().equalsIgnoreCase("first_name"))
								{
									query.append(" concat(pbd.first_name,' ', pbd.last_name) LIKE " + "'%" + searchParameter + "%" + "'");
								} else if (object.toString().equalsIgnoreCase("nationality"))
								{
									query.append(" cd.name LIKE " + "'%" + searchParameter + "%" + "'");
								} else if (object.toString().equalsIgnoreCase("offering_name"))
								{
									query.append(" off.subofferingname LIKE " + "'%" + searchParameter + "%" + "'");
								} else if (object.toString().equalsIgnoreCase("city"))
								{
									query.append(" loc.name LIKE " + "'%" + searchParameter + "%" + "'");
								} else if (object.toString().equalsIgnoreCase("corporate"))
								{
									query.append(" cbd.corp_name LIKE " + "'%" + searchParameter + "%" + "'");
								} else if (object.toString().equalsIgnoreCase("status"))
								{
									query.append(" ffa.question_status LIKE " + "'%" + searchParameter + "%" + "'");
								} else
								{
									query.append(" pbd." + object.toString() + " LIKE " + "'%" + searchParameter + "%" + "'");
								}
							}
							if (k < fieldNames.size() - 1)
							{
								/*
								 * if
								 * (!object.toString().equalsIgnoreCase("clientName"
								 * )) {
								 */
								query.append(" OR ");
								/* } */
							} else
								query.append(" ");
							k++;
						}
					}
				}

				if (status_view != null && !status_view.equals("") && !status_view.equals("-1"))
				{
					if (status_view.equalsIgnoreCase("0"))
					{
						query.append("and ffa.question_status = '");
						query.append(status_view);
						query.append("' ");
						query.append(" or ffa.question_status Is NULL");
					} else
					{
						query.append("and ffa.question_status = '");
						query.append(status_view);
						query.append("' ");
					}
				}
				// System.out.println("getSearchField() "+getSearchField()+"  getSearchString() "+getSearchString()+" getSearchOper() "+getSearchOper());
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// System.out.println(getSearchField());
					// add search query based on the search operation
					query.append(" and ");
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						if (getSearchField().equalsIgnoreCase(", first_name"))
						{
							setSearchField(" concat(pbd.first_name,' ', pbd.last_name) ");
						}
						if (getSearchField().equalsIgnoreCase("offering_name"))
						{
							setSearchField(" off.subofferingname ");
						}
						if (getSearchField().equalsIgnoreCase("nationality"))
						{
							setSearchField(" cd.name as nationality ");
						}
						if (getSearchField().equalsIgnoreCase("location"))
						{
							setSearchField(" loc.name as city ");
						}
						if (getSearchField().equalsIgnoreCase("status"))
						{
							setSearchField(" ffa.question_status ");
						} else
						{
							setSearchField(" pbd." + getSearchField());
						}
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						if (getSearchField().equalsIgnoreCase(", first_name"))
						{
							setSearchField(" concat(pbd.first_name,' ', pbd.last_name) ");
						}
						if (getSearchField().equalsIgnoreCase("offering_name"))
						{
							setSearchField(" off.subofferingname ");
						}
						if (getSearchField().equalsIgnoreCase("nationality"))
						{
							setSearchField(" cd.name as nationality ");
						}
						if (getSearchField().equalsIgnoreCase("location"))
						{
							setSearchField(" loc.name as city ");
						}
						if (getSearchField().equalsIgnoreCase("status"))
						{
							setSearchField(" ffa.question_status ");
						} else
						{
							setSearchField(" pbd." + getSearchField());
						}
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}
				query.append(" order by pbd.first_name limit " + from + "," + to);
				// System.out.println(query);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				// System.out.println("query.toString()>>" + query.toString());
				String firstname = null;
				fieldNames.add("pType");
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null && !obdata[k].equals("-1"))
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								} else
								{

									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									} else if (fieldNames.get(k).toString().equalsIgnoreCase("age"))
									{
										obdata[k] = DateUtil.calculateAge(obdata[k].toString());
									} else if (fieldNames.get(k).toString().equalsIgnoreCase("first_name"))
									{
										firstname = obdata[k].toString();

										if (obdata[k + 1] != null && !obdata[k + 1].toString().equals(""))
										{
											firstname = firstname + " " + obdata[k + 1].toString().substring(0, 1).toUpperCase() + obdata[k + 1].toString().substring(1);
											firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1);
											one.put(fieldNames.get(k).toString(), firstname);
										} else
										{
											firstname = firstname.substring(0, 1).toUpperCase() + firstname.substring(1);
											one.put(fieldNames.get(k).toString(), firstname);
										}
									}
									/*
									 * else if (fieldNames.get(k).toString()
									 * .equalsIgnoreCase("last_name")) {
									 * obdata[k] = firstname + " " +
									 * obdata[k].toString();
									 * one.put(fieldNames.get(k - 1).toString(),
									 * obdata[k].toString()); }
									 */else if (fieldNames.get(k).toString().equalsIgnoreCase("sent_questionair"))
									{
										if (obdata[k].toString().equalsIgnoreCase("false"))
										{
											obdata[k] = "Unsent";
										} else
										{
											obdata[k] = "Sent";
										}

									}

									if (!fieldNames.get(k).toString().equalsIgnoreCase("first_name") && !fieldNames.get(k).toString().equalsIgnoreCase("status"))
									{
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								}
							}

						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	void createFormList()
	{
		countryMap = new LinkedHashMap<String, String>();
		stageMap = new LinkedHashMap<String, String>();
		verticalMap = new LinkedHashMap<String, String>();
		rhTypeMap = new LinkedHashMap<String, String>();
		rhSubTypeMap = new LinkedHashMap<String, String>();
		offeringMap=new LinkedHashMap<String, Object>();
		// query="select code,country_name from country GROUP by country_name ORDER BY country_name ";
		// query="select city_code,state_name from state where city_code='IND' GROUP by state_name ";

		// get Country
		String query = " select distinct id, country_name from country order by country_name ";
		if (query != null)
		{
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						countryMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
			List dataList = new createTable().executeAllSelectQuery("select id, offeringname from offeringlevel2", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						offeringMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		System.out.println(countryMap);
		// get State
		query = "select distinct id ,state_name from state order by state_name ";
		if (query != null)
		{
			dataList.clear();
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						stageMap.put(object[0].toString(), object[0].toString());
					}
				}
			}
		}

		// get city
		query = "select distinct id,city_name from city order by city_name ";
		if (query != null)
		{
			dataList.clear();
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						verticalMap.put(object[0].toString(), object[0].toString());
					}
				}
			}
		}

		// get Patient Sub Type
		query = "select id,rel_subtype  from relationship_sub_type " + " where rel_type in ( select id from relationship where name='Patient' ) " + "order by rel_subtype ";
		if (query != null)
		{
			dataList.clear();
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						rhTypeMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}

		// get Patient Category Type
		query = "select id,cat_subtype  from category_type " + " where cat_type in ( select id from relationship where name='Patient' ) " + "order by cat_subtype ";
		if (query != null)
		{
			dataList.clear();
			dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						rhSubTypeMap.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}

	}

	public String patientBasicAdd()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			createFormList();
			setAddPageDataForBasic();
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForBasic()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Build map for text box and drop down for employee basic
			// information
			ClientHelper3 ch3 = new ClientHelper3();
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_patient_basic_configuration", accountID, connectionSpace, "", 0, "table_name", "patient_basic_configuration");
			patientBasicTextBox = new ArrayList<ConfigurationUtilBean>();
			patientDropDown = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("patien_id") && !gdp.getColomnName().equalsIgnoreCase("emp_id") && !gdp.getColomnName().equalsIgnoreCase("uh_id") && !gdp.getColomnName().equalsIgnoreCase("crm_id") && !gdp.getColomnName().equalsIgnoreCase("patient_status"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
					patientBasicTextBox.add(obj);
				}

				if (gdp.getColType().trim().equalsIgnoreCase("Date") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}

					if (gdp.getColomnName().equalsIgnoreCase("offering_name"))
					{
						offering = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							offeringExist = "true";
						else
							offeringExist = "false";
					}

				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
				}
			}
			// Get corporate list
			corporateMap = new LinkedHashMap<String, String>();
			// Get department list
			deptMap = new LinkedHashMap<String, String>();
			List deptData = cbt.executeAllSelectQuery("select id, contact_subtype_name from contact_sub_type order by contact_subtype_name", connectionSpace);

			if (deptData != null)
			{
				for (Object c : deptData)
				{
					Object[] dataC = (Object[]) c;
					deptMap.put(dataC[0].toString(), dataC[1].toString());
					// System.out.println("deptMap "+deptMap.size()+"  dataC[1].toString() "+dataC[1].toString());
				}
			}
			List corporateData = cbt.executeAllSelectQuery("select cm.id,cm.corp_name from corporate_master as cm ", connectionSpace);
			if (corporateData != null)
			{
				for (Object c : corporateData)
				{
					Object[] dataC = (Object[]) c;
					corporateMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}
			corporateMap.put("newAdd", "Add New");

			// Get associate
			associateMap = new LinkedHashMap<String, String>();

			List associateData = cbt.executeAllSelectQuery(" select abd.id,abd.associateName from associate_basic_data as abd " + " inner join associate_offering_mapping as aom on aom.associateName=abd.id  " + " where aom.isConverted='1' order by abd.associateName ", connectionSpace);
			if (associateData != null)
			{
				for (Object c : associateData)
				{
					Object[] dataAss = (Object[]) c;
					associateMap.put(dataAss[0].toString(), dataAss[1].toString());
				}
			}
			associateMap.put("newAdd", "Add New");

			// Get Country list
			/*
			 * countryMap = new LinkedHashMap<String, String>(); List
			 * countryData = cbt.executeAllSelectQuery(
			 * "select id, name from country_data  order by name ",
			 * connectionSpace); // System.out.println("countryMap  " +
			 * countryMap); if (countryData != null) { for (Object c :
			 * countryData) { Object[] dataC = (Object[]) c;
			 * countryMap.put(dataC[0].toString(), dataC[1].toString()); } }
			 */
			// Get Country list
			sourceMap = new LinkedHashMap<String, String>();
			List relationshipData = cbt.executeAllSelectQuery("select id, name from relationship order by name ", connectionSpace);
			// System.out.println("countryMap  " + countryMap);
			if (relationshipData != null)
			{
				for (Object c : relationshipData)
				{
					Object[] dataC = (Object[]) c;
					sourceMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}
			// Get City (Location) list
			/*
			 * locationList = new
			 * LeadHelper().fetchLocationList(connectionSpace);
			 */
		} catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String addPatientBasicForm()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		CommunicationHelper COH = new CommunicationHelper();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patient_basic_configuration", accountID, connectionSpace, "", 0, "table_name", "patient_basic_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<InsertDataTable> insertInEmpBasic = new ArrayList<InsertDataTable>();
					InsertDataTable empBasicObj = null;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("patient_basic_data", Tablecolumesaaa, connectionSpace);
					String name = null;
					String email = null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						System.out.println(Parmname + "    " + paramValue);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("verticalname") && !Parmname.equalsIgnoreCase("offeringname") && !Parmname.equalsIgnoreCase("__checkbox_sent_questionair"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							if (Parmname.equalsIgnoreCase("first_name"))
								name = paramValue;
							if (Parmname.equalsIgnoreCase("last_name"))
								name = name + " " + paramValue;
							if (Parmname.equalsIgnoreCase("email"))
								email = paramValue;
							if (Parmname.equalsIgnoreCase("mobile"))
								mobile = paramValue;

							if (Parmname.equalsIgnoreCase("dept_name"))
								dept_name = paramValue;
							if (Parmname.equalsIgnoreCase("patient_type"))
							{
								patient_type = paramValue;
								ob.setDataName(patient_type);
							} else if (Parmname.equalsIgnoreCase("age"))
							{
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							} else if (Parmname.equalsIgnoreCase("crm_id"))
							{
								System.out.println("true" + patient_type);
								if (patient_type != null && !patient_type.equalsIgnoreCase(""))
								{
									String crm_series = "CRMS";
									List<?> dataList = null;

									dataList = new createTable().executeAllSelectQuery("SELECT abb,series FROM relationship where id='" + patient_type + "'", connectionSpace);
									System.out.println("SELECT abb,series FROM relationship where id='" + patient_type + "'");
									if (dataList != null && dataList.size() > 0)
									{
										for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
										{
											Object[] object = (Object[]) iterator.next();
											if (object[0] != null)
											{
												crm_series = crm_series + "/" + object[0].toString() + "/";
											}
											if (object[1] != null)
											{
												crm_series = crm_series + "" + object[1].toString();
											}
										}
									}
									System.out.println(crm_series);
									dataList = null;
									dataList = new createTable().executeAllSelectQuery("SELECT crm_id FROM patient_basic_data ", connectionSpace);
									if (dataList != null && dataList.size() > 0)
									{
										for (int i = 0; i < dataList.size(); i++)
										{
											if (dataList.get(i) != null && dataList.get(i) != "NA")
											{
												String str = dataList.get(i).toString();
												if (str.split("/").length > 1)
												{
													String strtest = str.substring(0, str.lastIndexOf("/"));
													System.out.println("test" + strtest);
													if (str.equalsIgnoreCase(crm_series))
													{
														dataList = new createTable().executeAllSelectQuery("Select max(crm_id) from patient_basic_data where crm_id like '" + strtest + "/%';", connectionSpace);
														if (dataList != null && dataList.size() > 0)
														{
															String ser = dataList.get(0).toString();
															String arr[] = ser.split("/");
															Integer in = Integer.parseInt(arr[arr.length - 1]);
															in = in + 1;
															ob.setDataName(strtest + "/" + in);
														}
													} else
													{
														ob.setDataName(crm_series);
													}
												}
											} else
											{
												ob.setDataName(crm_series);
											}
										}
									} else
									{
										ob.setDataName(crm_series);
									}
								}
							} else
							{
								if (paramValue != null && !paramValue.equals(""))

									ob.setDataName(paramValue);
								else
									ob.setDataName("NA");
							}
							insertData.add(ob);
						}

					}
					ob = new InsertDataTable();
					ob.setColName("user");
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

					/*
					 * ob = new InsertDataTable();
					 * ob.setColName("patient_type");
					 * ob.setDataName(patient_type); insertData.add(ob);
					 */

					// status =
					// cbt.insertIntoTable("patient_basic_data",insertData,
					// connectionSpace);
					empBasicObj = new InsertDataTable();
					empBasicObj.setColName("groupId");
					empBasicObj.setDataName("1");
					insertInEmpBasic.add(empBasicObj);

					empBasicObj = new InsertDataTable();
					empBasicObj.setColName("empName");
					empBasicObj.setDataName(name);
					insertInEmpBasic.add(empBasicObj);

					empBasicObj = new InsertDataTable();
					empBasicObj.setColName("deptname");
					empBasicObj.setDataName(dept_name);
					insertInEmpBasic.add(empBasicObj);

					empBasicObj = new InsertDataTable();
					empBasicObj.setColName("mobOne");
					empBasicObj.setDataName(mobile);
					insertInEmpBasic.add(empBasicObj);

					empBasicObj = new InsertDataTable();
					empBasicObj.setColName("emailIdOne");
					empBasicObj.setDataName(email);
					insertInEmpBasic.add(empBasicObj);

					int empmaxid = cbt.insertDataReturnId("employee_basic", insertInEmpBasic, connectionSpace);

					if (empmaxid > 0)
					{
						ob = new InsertDataTable();
						ob.setColName("emp_id");
						ob.setDataName(empmaxid);
						insertData.add(ob);
					}

					int trace = cbt.insertDataReturnId("patient_basic_data", insertData, connectionSpace);
					insertData.clear();
					insertInEmpBasic.clear();

					String sent_questionair = request.getParameter("sent_questionair");
					if (trace > 0)
					{
						boolean putsmsstatus = false, putmailstatus = false;
						if (sent_questionair.equalsIgnoreCase("true"))
						{
							if (name != null && !name.equals("") && !name.equals("NA") && !email.equals("") && !email.equals("NA"))
							{
								String report_sms = null;
								report_sms = "Dear " + name + ", Please visit http://115.112.236.225:9090/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?setNo=A&traceid=" + Integer.toString(trace) + " to fill your Wellness Questionnaire.";
								if (mobile != null && mobile != "" && mobile != "NA" && mobile.length() == 10 && (mobile.startsWith("9") || mobile.startsWith("8") || mobile.startsWith("7")))
								{
									status = COH.addMessage(name, " ", mobile, report_sms, "Wellness Questionnaire Alert", "Pending", "0", "COM", connectionSpace);
								}

								String mailtext = this.mail_text(name, Integer.toString(trace));
								putmailstatus = COH.addMail(name, "", email, "Wellness Questionnaire Alert", mailtext, "", "Pending", "0", "", "WFPM", connectionSpace);
							}
						}
						/*
						 * if(sm.equalsIgnoreCase("True")) { if(name!=null &&
						 * !name.equals("") && !name.equals("NA") &&
						 * !mobile.equals("") && !mobile.equals("NA")) { String
						 * report_sms=null; report_sms = "Dear " + name +
						 * ", please visit 192.168.1.51:8080/over2cloud/patientFeedback.action?traceid="
						 * +trace+" for giving your valuable feedback";
						 * if(mobile != null && mobile != "" && mobile != "NA"
						 * && mobile.length() == 10 && (mobile.startsWith("9")
						 * || mobile.startsWith("8") || mobile.startsWith("7")))
						 * { putsmsstatus = COH.addMessage(name, " ", mobile,
						 * report_sms, "Online Feedback", "Pending", "0",
						 * "WFPM",connectionSpace); } } }
						 */
						addActionMessage("Patient Basic Data Added successfully!!!");
						return SUCCESS;
					} else
					{
						addActionMessage("There Is Some Error.");
						return SUCCESS;
					}
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String resendQuestions()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			CommunicationHelper COH = new CommunicationHelper();
			String ppname = null, ppmob = null, ppemail = null;
			// String query =
			// "SELECT concat(first_name,' ',last_name) as first_name,mobile,email FROM patient_basic_data  where id="+id+" and concat(first_name,' ',last_name)!='NA' and concat(first_name,' ',last_name) is not null ";
			String query = "SELECT first_name,last_name,mobile,email FROM patient_basic_data  where id=" + id + " and first_name!='NA' and first_name is not null ";
			boolean status = false;
			List data = cbt.executeAllSelectQuery(query, connectionSpace);
			if (data != null && data.size() > 0)
			{
				Object[] ob = (Object[]) data.get(0);
				if (ob[0] != null && !ob[0].toString().equals(""))
				{
					ppname = ob[0].toString();
					if (ob[0 + 1].toString() != null && !ob[0 + 1].toString().equals(""))
					{
						ppname = ppname + " " + ob[0 + 1].toString();
					}
				} else
				{
					ppname = "NA";
				}
				if (ob[2] == null)
				{
					ppmob = "NA";
				} else
				{
					ppmob = ob[2].toString();
				}
				if (ob[3] == null)
				{
					ppemail = "NA";
				} else
				{
					ppemail = ob[3].toString();
				}
			}

			String report_sms = null;
			report_sms = "Dear " + ppname + ", Please visit http://115.112.236.225:9090/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?setNo=A&traceid=" + id + "  for fill your Wellness Questionnaire.";
			if (ppmob != null && ppmob != "" && ppmob != "NA" && ppmob.length() == 10 && (ppmob.startsWith("9") || ppmob.startsWith("8") || ppmob.startsWith("7")))
			{
				status = COH.addMessage(ppname, " ", mobile, report_sms, "Wellness Questionnaire Alert", "Pending", "0", "COM", connectionSpace);
			}

			String mailtext = this.mail_text(ppname, id);
			status = COH.addMail(ppname, "", ppemail, "Wellness Questionnaire Alert", mailtext, "", "Pending", "0", "", "WFPM", connectionSpace);
			if (status)
			{
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				wherClause.put("sent_questionair", "true");
				condtnBlock.put("id", id);
				cbt.updateTableColomn("patient_basic_data", wherClause, condtnBlock, connectionSpace);
				return SUCCESS;
			} else
			{
				return ERROR;
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
	}

	public String addNewOffering()
	{
		try
		{
			System.out.println(first_name + ":::::" + mobile);
			setAddPageForVisit();
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	public String beforePatientActivity()
	{
		status = "success";
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageForVisit();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			System.out.println("id::::::" + id);
			List dataList = fetchPreviousData(cbt, offering);
			if (dataList != null && dataList.size() > 0 || offering!=null)
			{
				status = "followup";

				if (dataList != null && dataList.size() > 0)
				{
					Object[] obdata = (Object[]) dataList.get(0);
					offeringLevel = CUA.getValueWithNullCheck(obdata[0]);
					pname = CUA.getValueWithNullCheck(obdata[1]);
					relName = CUA.getValueWithNullCheck(obdata[2]);
					createdBy = CUA.getValueWithNullCheck(obdata[3]);
					status_view = CUA.getValueWithNullCheck(obdata[5]);
					if (obdata[6] != null)
					{
						date = DateUtil.convertDateToIndianFormat(obdata[6].toString());
					}
					nextComment = CUA.getValueWithNullCheck(obdata[7]);
					id = CUA.getValueWithNullCheck(obdata[8]);
					stage = CUA.getValueWithNullCheck(obdata[9]);
				}
				dataList.clear();
				StringBuilder query = new StringBuilder();
				query.append("select off.id,off.subofferingname  " + " FROM patient_visit_action as pva  " + " inner join offeringlevel3 as off on off.id=pva.offeringlevel3" + " where pva.patient_name='" + rowid + "' order by pva.id desc ");
				System.out.println(query);
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				offeringMap = new LinkedHashMap<String, Object>();
				if (dataList != null && dataList.size()>0)
				{
					for (Object c : dataList)
					{
						Object[] dataC = (Object[]) c;
						offeringMap.put((dataC == null ? "" : dataC[0].toString()), (dataC[1] == null ? "" : dataC[1].toString()));
					}
					offeringMap.put("Add New", "Add New");
				}
				else
				{
					//dataList.clear();
					System.out.println("inside else");
					offeringLevel=offering;
					query.setLength(0);
					query.append("select subofferingname from offeringlevel3 as off where id=" + offering);
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						offeringMap.put(offering, dataList.get(0));
						offeringMap.put("Add New", "Add New");
					}
				}
				System.out.println(offeringMap.size());
				dataList.clear();
				query.setLength(0);
				query.append("SELECT DISTINCT psd.id,act.act_name  " + " FROM patientcrm_status_data as psd  " + " " + "LEFT JOIN activity_type as act on act.id=psd.status WHERE psd.offering_name='" + offeringLevel + "'");
				if (patient_type != null && !patient_type.equalsIgnoreCase(""))
				{
					query.append(" AND psd.rh_sub_type='" + patient_type + "' ");
				}
				if (stage != null && !stage.equalsIgnoreCase(""))
				{
					query.append(" AND psd.stage='" + stage + "' ");
				}
				System.out.println("Query::::" + query);
				// query.append("select id,status FROM patientcrm_status_data where offering_name='"
				// + offeringLevel + "'");
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				rhTypeMap = new LinkedHashMap<String, String>();
				if (dataList != null)
				{
					for (Object c : dataList)
					{
						Object[] dataC = (Object[]) c;
						rhTypeMap.put((dataC == null ? "" : dataC[0].toString()), (dataC[1] == null ? "" : dataC[1].toString()));
					}
				}
				dataList.clear();
				query.setLength(0);
				List list = cbt.executeAllSelectQuery("SELECT offeringname FROM offeringlevel3 WHERE id=" + offeringLevel, connectionSpace);
				if (list != null && list.size() > 0 )
				{
					query.append("SELECT cc.id,emp.emp_name FROM doctor_off_map_data AS dmd ");
					query.append(" INNER JOIN manage_contact AS cc ON cc.id=dmd.doctor_name");
					query.append(" INNER JOIN primary_contact AS emp ON cc.emp_id=emp.id");
					query.append(" WHERE offlevel3=(SELECT offeringname FROM offeringlevel3 WHERE id=" + offeringLevel + ")");
					
					// System.out.println("doc query::::"+query);
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					deptMap = new LinkedHashMap<String, String>();
					if (dataList != null)
					{
						for (Object c : dataList)
						{
							Object[] dataC = (Object[]) c;
							deptMap.put((dataC == null ? "" : dataC[0].toString()), (dataC[1] == null ? "" : dataC[1].toString()));
						}
					}
				}

				dataList.clear();
				query.setLength(0);
				query.append("SELECT cc.id,emp.emp_name FROM manage_contact AS cc ");
				query.append(" INNER JOIN primary_contact AS emp ON cc.emp_id=emp.id");
				query.append(" WHERE level > '1' AND cc.work_status!='1' AND cc.module_name='WFPM' ");
				// System.out.println("doc query::::"+query);
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				empMap = new LinkedHashMap<String, String>();
				if (dataList != null)
				{
					for (Object c : dataList)
					{
						Object[] dataC = (Object[]) c;
						empMap.put((dataC == null ? "" : dataC[0].toString()), (dataC[1] == null ? "" : dataC[1].toString()));
					}
				}

				stageMap = new LinkedHashMap<String, String>();
				List relManagerData = new createTable().executeAllSelectQuery("SELECT id, forcastName FROM forcastcategarymaster", connectionSpace);
				if (relManagerData != null)
				{
					for (Object c : relManagerData)
					{
						Object[] dataC = (Object[]) c;
						stageMap.put(dataC[0].toString(), dataC[1].toString());
					}
					relManagerData.clear();
				}

				sourceMap = new LinkedHashMap<String, String>();
				relManagerData = new createTable().executeAllSelectQuery("SELECT id, lost_name FROM lost_opportunity WHERE rel_type='3'", connectionSpace);
				if (relManagerData != null)
				{
					for (Object c : relManagerData)
					{
						Object[] dataC = (Object[]) c;
						sourceMap.put(dataC[0].toString(), dataC[1].toString());
					}
				}

			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return status;
	}

	public List fetchPreviousData(CommonOperInterface cbt, String oferringId)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder(" select  distinct pva.offeringlevel3,concat(pbd.first_name,' ',pbd.last_name),eb.emp_name ,pva.userName,pva.date,act1.act_name as nextstatus,pva.maxDateTime,pva.comment2,pva.id,pva.stage from patient_visit_action as pva ");
			query.append(" LEFT JOIN patientcrm_status_data as pcs1 on pcs1.id=pva.next_activity ");
			query.append(" LEFT JOIN activity_type as act1 on act1.id=pcs1.status ");
			query.append(" LEFT JOIN patient_basic_data as pbd on pbd.id=pva.patient_name ");
			query.append(" LEFT JOIN manage_contact as cc on cc.id=pva.relationship_mgr ");
			query.append(" LEFT JOIN primary_contact as eb on eb.id=cc.emp_id ");
			if (id != null && !id.equalsIgnoreCase("") && !id.equalsIgnoreCase("undefined"))
			{
				query.append("where pva.id=" + id + " ");
			} else
			{
				query.append("where pva.patient_name='" + rowid + "'  ");
				if (oferringId != null && !"".equalsIgnoreCase(oferringId))
				{
					query.append(" AND pva.offeringlevel3='" + oferringId + "' ");
				}
				query.append(" order by pva.id desc limit 0,1");
			}

			System.out.println("all data query:::" + query);
			dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public String fetchAllOfferingDetails()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = fetchPreviousData(cbt, offeringLevel);
			jsonArray = new JSONArray();
			if (dataList != null && dataList.size() > 0)
			{
				Object[] obdata = (Object[]) dataList.get(0);
				jsonObject = new JSONObject();
				jsonObject.put("pname", obdata[1]);
				jsonObject.put("relName", obdata[2]);
				jsonObject.put("createdBy", obdata[3]);
				jsonObject.put("laststatus", obdata[4]);
				jsonObject.put("lastComment", obdata[5]);
				if (obdata[6] != null)
				{
					jsonObject.put("lastDate", DateUtil.convertDateToIndianFormat(obdata[6].toString().split(" ")[0]) + ", " + obdata[6].toString().split(" ")[1].substring(0, 5));
					jsonObject.put("lastDate", DateUtil.convertDateToIndianFormat(obdata[6].toString()));
				}
				jsonObject.put("status_view", obdata[7]);
				if (obdata[6] != null)
				{
					jsonObject.put("date", DateUtil.convertDateToIndianFormat(obdata[8].toString()));
				}
				jsonObject.put("nextComment", obdata[9]);
				jsonObject.put("currentActivity", obdata[10]);
				jsonArray.add(jsonObject);
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	public String beforePatientVisit()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageForVisit();
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageForVisit()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Build map for text box and drop down for employee basic
			// information
			ClientHelper3 ch3 = new ClientHelper3();
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_patient_visit_action_config", accountID, connectionSpace, "", 0, "table_name", "patient_visit_action_config");
			patientTextBox = new ArrayList<ConfigurationUtilBean>();
			patientDropDown = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
					patientTextBox.add(obj);
				}

				if (gdp.getColType().trim().equalsIgnoreCase("Date") && !gdp.getColomnName().equalsIgnoreCase("user") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}

					if (gdp.getColomnName().equalsIgnoreCase("offering_name"))
					{
						offering = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							offeringExist = "true";
						else
							offeringExist = "false";
					}

				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("targetAchieved"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
				}
			}
			// For offering details

			// oferring taken by client in configuration
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{

				oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);
				if (level > 0)
				{
					setOLevel1(true);
					OLevel1LevelName = oLevels[1];
				}
				if (level > 1)
				{
					OLevel2 = true;
					OLevel2LevelName = oLevels[2];
				}
				if (level > 2)
				{
					OLevel3 = true;
					OLevel3LevelName = oLevels[3];
				}
				if (level > 3)
				{
					OLevel4 = true;
					OLevel4LevelName = oLevels[4];
				}
				if (level > 4)
				{
					OLevel5 = true;
					OLevel5LevelName = oLevels[5];
				}

				// Get verticalname list
				verticalMap = new LinkedHashMap<String, String>();
				List verticalData = cbt.executeAllSelectQuery("select id, verticalname from " + "offeringlevel1 order by verticalname", connectionSpace);

				if (verticalData != null)
				{
					for (Object c : verticalData)
					{
						Object[] dataC = (Object[]) c;
						verticalMap.put(dataC[0].toString(), dataC[1].toString());
					}
				}
			}

			relManagerMap = new LinkedHashMap<String, Object>();
			List relManagerData = cbt.executeAllSelectQuery("select cc.id,eb.emp_name from rel_mgr_data as rmd  " + " inner join manage_contact as cc on cc.id=rmd.name" + " inner join primary_contact as eb on  eb.id=cc.emp_id ", connectionSpace);
			if (relManagerData != null)
			{
				for (Object c : relManagerData)
				{
					Object[] dataC = (Object[]) c;
					relManagerMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}

		} catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String followupActivity()
	{
		// System.out.println("rowid:: " + rowid);
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageForVisit();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<ConfigurationUtilBean> tempList = getPatientBasicTextBox();
			StringBuilder query = new StringBuilder(" select ");
			query.append(" distinct pva.offeringlevel3, pcs.status,concat(pbd.first_name,' ',pbd.last_name) from patient_visit_action as pva " + "inner join patientcrm_status_data as pcs on pcs.id=pva.current_activity" + " inner join patient_basic_data as pbd on pbd.id=pva.patient_name " + "where pva.patient_name='" + rowid + "' order by pva.id desc limit 0,1 ");
			System.out.println(query);

			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] obdata = (Object[]) dataList.get(0);
				offeringLevel = (obdata[0] == null ? "" : obdata[0].toString());
				laststatus = (obdata[1] == null ? "" : obdata[1].toString());
				pname = (obdata[2] == null ? "" : obdata[2].toString());
			}
			String queryMap = "select off.id,off.subofferingname  " + " FROM patient_visit_action as pva  " + " inner join offeringlevel3 as off on off.id=pva.offeringlevel3" + " where pva.patient_name='" + rowid + "' ";
			System.out.println(queryMap);
			List dataList2 = cbt.executeAllSelectQuery(queryMap, connectionSpace);
			offeringMap = new LinkedHashMap<String, Object>();
			if (dataList2 != null)
			{
				for (Object c : dataList2)
				{
					Object[] dataC = (Object[]) c;
					offeringMap.put((dataC == null ? "" : dataC[0].toString()), (dataC[1] == null ? "" : dataC[1].toString()));
				}

			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// check for patient FollowupActivity for further perform FollowupActivity
	public String checkFollowupActivity()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<ConfigurationUtilBean> tempList = getPatientBasicTextBox();
			StringBuilder query = new StringBuilder(" select ");
			query.append(" distinct pva.offeringlevel3, pcs.status,concat(pbd.first_name,' ',pbd.last_name) from patient_visit_action as pva " + "inner join patientcrm_status_data as pcs on pcs.id=pva.current_activity" + " inner join patient_basic_data as pbd on pbd.id=pva.patient_name " + "where pva.patient_name='" + rowid + "' order by pva.id desc limit 0,1 ");

			System.out.println(query.toString());

			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			jsonObject = new JSONObject();
			if (dataList.size() < 1)
			{
				jsonObject.put("status", "notexists");
			} else
			{
				jsonObject.put("status", "exists");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String fetchOfferingLevelData()
	{

		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			/*
			 * System.out.println("Offeringlevel  " + Offeringlevel);
			 * System.out.println("offeringId  " + offeringId);
			 */
			if (Offeringlevel.equalsIgnoreCase("1"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("offeringname");
				Map<String, Object> temp = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("offeringname", "ASC");
				temp.put("verticalname", offeringId);
				// empSelectParam =
				// cbt.viewAllDataEitherSelectOrAll("offeringlevel2",
				// empSelectParam, temp, connectionSpace);
				empSelectParam = cbt.viewAllDataEitherSelectOrAllByOrder("offeringlevel2", empSelectParam, temp, order, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}

				}

			} else if (Offeringlevel.equalsIgnoreCase("2"))
			{
				// System.out.println(" 2 in -----");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("subofferingname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("offeringname", offeringId);
				empSelectParam = cbt.viewAllDataEitherSelectOrAll("offeringlevel3", empSelectParam, temp, connectionSpace);
				// System.out.println("@ in CHKK ");
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());
						jsonArray.add(formDetailsJson);
					}

				}
			} else if (Offeringlevel.equalsIgnoreCase("3"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("variantname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("subofferingname", offeringId);
				empSelectParam = cbt.viewAllDataEitherSelectOrAll("offeringlevel4", empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}
			} else if (Offeringlevel.equalsIgnoreCase("4"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				empSelectParam.add("id");
				empSelectParam.add("subvariantname");
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("variantname", offeringId);
				empSelectParam = cbt.viewAllDataEitherSelectOrAll("offeringlevel5", empSelectParam, temp, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}
				}
			}
			// System.out.println("jsonArray" + jsonArray.toString());
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: fetchOfferingLevelData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addFirstStatus()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patient_visit_action_config", accountID, connectionSpace, "", 0, "table_name", "patient_visit_action_config");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("patient_visit_action", Tablecolumesaaa, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					String[] val = null;
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						// System.out.println(Parmname+"    "+paramValue);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("purpose_visit") && !Parmname.equalsIgnoreCase("__checkbox_sent_questionair") && !Parmname.equalsIgnoreCase("verticalname") && !Parmname.equalsIgnoreCase("offeringname"))
						{
							if (Parmname.equalsIgnoreCase("maxDateTime") || Parmname.equalsIgnoreCase("curr_schedule_date"))
							{
								val = paramValue.split(" ");
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(DateUtil.convertDateToUSFormat(val[0]));
								insertData.add(ob);
								if (Parmname.equalsIgnoreCase("maxDateTime"))
								{
									// sheduled time for next activity
									ob = new InsertDataTable();
									ob.setColName("time");
									ob.setDataName(val[1]);
									insertData.add(ob);
								}

							} else
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
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

					status = cbt.insertIntoTable("patient_visit_action", insertData, connectionSpace);
					insertData.clear();
					if (status)
					{
						if (request.getParameter("sent_questionair") != null && request.getParameter("sent_questionair").toString().equalsIgnoreCase("true"))
						{
							String query = "select mobile,email,concat(first_name,' ',last_name) FROM patient_basic_data where id='" + request.getParameter("patient_name") + "'";
							List data4escalate = cbt.executeAllSelectQuery(query, connectionSpace);
							if (data4escalate.size() > 0)
							{
								CommunicationHelper COH = new CommunicationHelper();
								Object[] contactdetails = (Object[]) data4escalate.get(0);
								boolean putsmsstatus = false, putmailstatus = false;
								String report_sms = "Dear " + (contactdetails[2] != null ? contactdetails[2] : "NA") + ", please visit http://115.112.236.225:9090/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?setNo=A&traceid=" + request.getParameter("patient_name") + " for fill your Wellness Questionnaire.";
								// SMS
								/*
								 * if(contactdetails[0]!=null) { String
								 * report_sms=null;
								 * 
								 * }
								 */
								// MAil
								if (contactdetails[1] != null)
								{
									String mailtext = this.mail_text((contactdetails[2] != null ? contactdetails[2].toString() : "NA"), request.getParameter("patient_name"));
									putmailstatus = COH.addMail((contactdetails[2] != null ? contactdetails[2].toString() : "NA"), "", (contactdetails[1] != null ? contactdetails[1].toString() : "NA"), "Online Feedback", mailtext, "", "Pending", "0", "", "WFPM", connectionSpace);
								}
							}
							addActionMessage("Patient Status Added successfully!!!");
						}
						return SUCCESS;
					} else
					{
						addActionMessage("Oops There is some error in data!");
						return SUCCESS;
					}
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String beforeviewManager()
	{
		if (!ValidateSession.checkSession())
			return LOGIN;
		setRelMgrViewProp();
		return SUCCESS;
	}

	public String mail_text(String name, String trace)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(name) + ",</b>");
		mailtext.append("<br><br> Please visit http://115.112.236.225:9090/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?setNo=A&traceid=" + trace + " to fill your Wellness Questionnaire.");
		mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><br Thanks !!!<br><br></FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>--------------<br>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public void setRelMgrViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_rel_mgr_data_config", accountID, connectionSpace, "", 0, "table_name", "rel_mgr_data_config");

			for (GridDataPropertyView gdp : returnResult)
			{

				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setAlign(gdp.getAlign());
				gpv.setWidth(gdp.getWidth());
				if (gdp.getFormatter() != null)
					gpv.setFormatter(gdp.getFormatter());
				masterViewProp.add(gpv);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String viewrelMgrGrid()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from rel_mgr_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from rel_mgr_data as psd ");

				List fieldNames = Configuration.getColomnList("mapped_rel_mgr_data_config", accountID, connectionSpace, "rel_mgr_data_config");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("name"))
						{
							queryTemp.append(" emp.emp_name, ");
							queryEnd.append(" left join manage_contact as cc on cc.id=psd.name ");
							queryEnd.append(" left join primary_contact as emp on emp.id = cc.emp_id ");
						} else if (obdata.toString().equalsIgnoreCase("department"))
						{
							queryTemp.append(" dept.contact_subtype_name, ");
							queryEnd.append(" left join contact_sub_type as dept on dept.id = psd.department ");

						} else
						{
							queryTemp.append("psd." + obdata.toString() + ",");
						}

					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" psd.id <> '0' ");
				if (offeringName != null && !offeringName.equals("-1"))
				{
					query.append("and psd.offering = '");
					query.append(offeringName);
					query.append("' ");
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceMaster"))
					{
						setSearchField(" sm.sourceName ");
					} else if (getSearchField().toString().equalsIgnoreCase("status"))
					{
						setSearchField(" cs.statusName ");
					} else if (getSearchField().toString().equalsIgnoreCase("location"))
					{
						setSearchField(" loc.name ");
					} else if (getSearchField().toString().equalsIgnoreCase("acManager"))
					{
						setSearchField(" cbd1.empName ");
					} else
					{
						setSearchField(" cbd." + getSearchField());
					}

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				// query.append(" order by psd.name limit " + from + "," + to);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				System.out.println("query.toString()>>" + query.toString());
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{

							if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("NA"))
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								} else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l2"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l3"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l4"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l5"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							} else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					// System.out.println(getMasterViewList());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addRelationshipMgr()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageDataForRelMgr();

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForRelMgr()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Build map for text box and drop down for relationship Manager.
			ClientHelper3 ch3 = new ClientHelper3();
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_rel_mgr_data_config", accountID, connectionSpace, "", 0, "table_name", "rel_mgr_data_config");
			patientTextBox = new ArrayList<ConfigurationUtilBean>();
			patientDropDown = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("status"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
					patientTextBox.add(obj);
				}

				if (gdp.getColType().trim().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}

					if (gdp.getColomnName().equalsIgnoreCase("offering_name"))
					{
						offering = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							offeringExist = "true";
						else
							offeringExist = "false";
					}

				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
				}

			}

			// Get verticalname list
			verticalMap = new LinkedHashMap<String, String>();
			List verticalData = cbt.executeAllSelectQuery("select id, contact_subtype_name from " + "contact_sub_type order by contact_subtype_name", connectionSpace);

			if (verticalData != null)
			{
				for (Object c : verticalData)
				{
					Object[] dataC = (Object[]) c;
					verticalMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}

		} catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForClient of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String relationshipMgrSave()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_rel_mgr_data_config", accountID, connectionSpace, "", 0, "table_name", "rel_mgr_data_config");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("rel_mgr_data", Tablecolumesaaa, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();

						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("verticalname") && !Parmname.equalsIgnoreCase("offeringname"))
						{
							if (Parmname.equalsIgnoreCase("name"))
							{
								String contactID = null;
								String query = "select id from manage_contact where emp_id='" + paramValue + "' and module_name='WFPM' order by id desc ";
								List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if (dataList != null)
								{

									contactID = dataList.get(0).toString();
								}
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(contactID);
								insertData.add(ob);
							} else
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
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

					status = cbt.insertIntoTable("rel_mgr_data", insertData, connectionSpace);
					insertData.clear();

					if (status)
					{
						addActionMessage("Manager Added successfully!!!");
						return SUCCESS;
					} else
					{
						addActionMessage("There is some error in data!");
						return SUCCESS;
					}
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeEditPatientForm()
	{
		// System.out.println("beforeEditPatientForm" + id);
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			patientBasicEdit();
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String patientBasicEdit()
	{
		/*
		 * System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
		 * + id);
		 */
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			setAddPageDataForBasic();
			List<ConfigurationUtilBean> tempList = getPatientBasicTextBox();
			StringBuilder query = new StringBuilder(" select ");
			for (ConfigurationUtilBean cub : tempList)
			{
				String field = cub.getKey();
				query.append(field + ",");
			}
			query.append(" gender , nationality , city , blood_group , patient_category , patient_type , corporate , referral from patient_basic_data where id='" + id + "'");
			System.out.println(query.toString());
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			List<ConfigurationUtilBean> finalPageFieldColumn = new ArrayList<ConfigurationUtilBean>();
			if (dataList != null && dataList.size() > 0)
			{
				Object[] obdata = (Object[]) dataList.get(0);

				for (int i = 0; i < tempList.size(); i++)
				{
					ConfigurationUtilBean cub = (ConfigurationUtilBean) tempList.get(i);
					cub.setDefault_value((obdata[i] == null ? "NA" : obdata[i].toString()));
					// System.out.println(cub.getKey() + "\t >>>>  "+
					// cub.getDefault_value());
					finalPageFieldColumn.add(cub);
				}
				setPatientBasicTextBox(finalPageFieldColumn);
				gender = (obdata[8] == null ? "NA" : obdata[8].toString());
				nationality = (obdata[9] == null ? "NA" : obdata[9].toString());
				city = (obdata[10] == null ? "NA" : obdata[10].toString());
				blood_group = (obdata[11] == null ? "NA" : obdata[11].toString());
				patient_category = (obdata[12] == null ? "NA" : obdata[12].toString());
				patient_type = (obdata[13] == null ? "NA" : obdata[13].toString());
				corporate = (obdata[14] == null ? "NA" : obdata[14].toString());
				referral = (obdata[15] == null ? "NA" : obdata[15].toString());

			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: beforeStatusAdd of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String updatePatientBasicForm()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patient_basic_configuration", accountID, connectionSpace, "", 0, "table_name", "patient_basic_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("patient_basic_data", Tablecolumesaaa, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						// System.out.println(Parmname+"    "+paramValue);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("verticalname") && !Parmname.equalsIgnoreCase("offeringname") && !Parmname.equalsIgnoreCase("__checkbox_sent_questionair"))
						{
							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
					ob = new InsertDataTable();
					ob.setColName("user");
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

					status = cbt.updateIntoTable("patient_basic_data", insertData, id, connectionSpace);

					insertData.clear();
					if (status)
					{
						addActionMessage("Patient Basic updated successfully!!!");
						return SUCCESS;
					} else
					{
						addActionMessage("There Is Some Error.");
						return SUCCESS;
					}
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String fetchEmployeeData()
	{

		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;

			if (depName != null && !depName.equalsIgnoreCase("-1") && !depName.equalsIgnoreCase("") && !depName.equalsIgnoreCase("undefined"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List empSelectParam = new ArrayList<String>();
				String query = "select id, emp_name from primary_contact where sub_type_id='" + depName + "'";
				System.out.println("query  " + query);
				empSelectParam = cbt.executeAllSelectQuery(query, connectionSpace);
				if (empSelectParam != null && empSelectParam.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Object c : empSelectParam)
					{
						Object[] dataC = (Object[]) c;

						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());

						jsonArray.add(formDetailsJson);
					}

				}

			}
			// System.out.println("jsonArray" + jsonArray.toString());
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: fetchOfferingLevelData of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeDoctorOfferingMap()
	{
		if (!ValidateSession.checkSession())
			return LOGIN;
		setDoctorOfferingViewProp();
		return SUCCESS;
	}

	public void setDoctorOfferingViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);
			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_doctor_off_map_conf", accountID, connectionSpace, "", 0, "table_name", "doctor_off_map_conf");

			for (GridDataPropertyView gdp : returnResult)
			{

				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setAlign(gdp.getAlign());
				gpv.setWidth(gdp.getWidth());
				if (gdp.getFormatter() != null)
					gpv.setFormatter(gdp.getFormatter());
				masterViewProp.add(gpv);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String viewrMapDoctorOffdataGrid()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append(" select count(*) from doctor_off_map_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
				StringBuilder queryTemp = new StringBuilder("");
				queryTemp.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from doctor_off_map_data as psd ");

				List fieldNames = Configuration.getColomnList("mapped_doctor_off_map_conf", accountID, connectionSpace, "doctor_off_map_conf");
				List<Object> Listhb = new ArrayList<Object>();
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("doctor_name"))
						{
							queryTemp.append(" emp.emp_name, ");
							queryEnd.append(" left join manage_contact as cc on cc.id=psd.doctor_name ");
							queryEnd.append(" left join primary_contact as emp on emp.id = cc.emp_id ");
						} else if (obdata.toString().equalsIgnoreCase("deptName"))
						{
							queryTemp.append(" dept.contact_subtype_name, ");
							queryEnd.append(" left join contact_sub_type as dept on dept.id = psd.deptName ");

						} else if (obdata.toString().equalsIgnoreCase("deptName"))
						{
							queryTemp.append(" dept.contact_subtype_name, ");
							queryEnd.append(" left join contact_sub_type as dept on dept.id = psd.deptName ");

						} else if (obdata.toString().equalsIgnoreCase("offlevel3"))
						{
							queryTemp.append(" off.offeringname, ");
							queryEnd.append(" left join offeringlevel2 as off on off.id =  psd.offlevel3 ");

						} else
						{
							queryTemp.append("psd." + obdata.toString() + ",");
						}

					}
				}
				query.append(queryTemp.toString().substring(0, queryTemp.toString().lastIndexOf(",")));
				query.append(" ");
				query.append(queryEnd.toString());
				query.append(" where ");
				query.append(" psd.id <> '0' ");
				if (offeringName != null && !offeringName.equals("-1"))
				{
					query.append("and psd.offering = '");
					query.append(offeringName);
					query.append("' ");
				}

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					query.append(" and ");

					if (getSearchField().toString().equalsIgnoreCase("sourceMaster"))
					{
						setSearchField(" sm.sourceName ");
					} else if (getSearchField().toString().equalsIgnoreCase("status"))
					{
						setSearchField(" cs.statusName ");
					} else if (getSearchField().toString().equalsIgnoreCase("location"))
					{
						setSearchField(" loc.name ");
					} else if (getSearchField().toString().equalsIgnoreCase("acManager"))
					{
						setSearchField(" cbd1.empName ");
					} else
					{
						setSearchField(" cbd." + getSearchField());
					}

					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
					} else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
					} else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				// query.append(" order by psd.name limit " + from + "," + to);
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				System.out.println("query.toString()>>" + query.toString());
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{

							if (obdata[k] != null && !obdata[k].toString().equalsIgnoreCase("NA"))
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								} else
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
									{
										obdata[k] = DateUtil.convertDateToIndianFormat(obdata[k].toString());
									}

									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l2"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l3"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l4"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());

									if (fieldNames.get(k).toString().equalsIgnoreCase("l5"))
									{
										obdata[k] = getNameByccid(obdata[k].toString()).toString();
									}
									one.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
							} else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					// System.out.println(getMasterViewList());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addDocOfferingSave()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_doctor_off_map_conf", accountID, connectionSpace, "", 0, "table_name", "doctor_off_map_conf");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("doctor_off_map_data", Tablecolumesaaa, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();

						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("verticalname") && !Parmname.equalsIgnoreCase("offeringname"))
						{
							if (Parmname.equalsIgnoreCase("doctor_name"))
							{
								String contactID = null;
								String query = "select id from manage_contact where emp_id='" + paramValue + "' and module_name='WFPM' order by id desc ";
								List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
								if (dataList != null)
								{

									contactID = dataList.get(0).toString();
								}
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(contactID);
								insertData.add(ob);
							} else
							{
								ob = new InsertDataTable();
								ob.setColName(Parmname);
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

					status = cbt.insertIntoTable("doctor_off_map_data", insertData, connectionSpace);
					insertData.clear();

					if (status)
					{
						addActionMessage("Offering Mapped successfully.");
						return SUCCESS;
					} else
					{
						addActionMessage("There is some error in data.");
						return SUCCESS;
					}
				}
			} catch (Exception exp)
			{
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String addDocOffering()
	{
		try
		{
			if (!ValidateSession.checkSession())
				return LOGIN;
			setAddPageDataForDocOffering();

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: addOffering of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAddPageDataForDocOffering()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			// Build map for text box and drop down for relationship Manager.
			ClientHelper3 ch3 = new ClientHelper3();
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_doctor_off_map_conf", accountID, connectionSpace, "", 0, "table_name", "doctor_off_map_conf");
			// System.out.println("offeringLevel1>"+offeringLevel1.size());
			patientTextBox = new ArrayList<ConfigurationUtilBean>();
			patientDropDown = new ArrayList<ConfigurationUtilBean>();

			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean obj = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("offlevel3") && !gdp.getColomnName().equalsIgnoreCase("deptName") && !gdp.getColomnName().equalsIgnoreCase("doctor_name") && !gdp.getColomnName().equalsIgnoreCase("status"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
					patientTextBox.add(obj);
				}

				if (gdp.getColType().trim().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}

				}

				if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
				{

					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
				}

			}

			// Get department list
			deptMap = new LinkedHashMap<String, String>();
			List deptData = cbt.executeAllSelectQuery("select id, contact_subtype_name from " + "contact_sub_type order by contact_subtype_name", connectionSpace);

			if (deptData != null)
			{
				for (Object c : deptData)
				{
					Object[] dataC = (Object[]) c;
					deptMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}

			// For offering details

			// oferring taken by client in configuration
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{

				oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);
				if (level > 0)
				{
					setOLevel1(true);
					OLevel1LevelName = oLevels[1];
				}
				if (level > 1)
				{
					OLevel2 = true;
					OLevel2LevelName = oLevels[2];
				}
				if (level > 2)
				{
					OLevel3 = true;
					OLevel3LevelName = oLevels[3];
				}
				if (level > 3)
				{
					OLevel4 = true;
					OLevel4LevelName = oLevels[4];
				}
				if (level > 4)
				{
					OLevel5 = true;
					OLevel5LevelName = oLevels[5];
				}

				// Get verticalname list
				verticalMap = new LinkedHashMap<String, String>();
				List verticalData = cbt.executeAllSelectQuery("select id, verticalname from " + "offeringlevel1 order by verticalname", connectionSpace);

				if (verticalData != null)
				{
					for (Object c : verticalData)
					{
						Object[] dataC = (Object[]) c;
						verticalMap.put(dataC[0].toString(), dataC[1].toString());
					}
				}
			}

		} catch (Exception ex)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method: setAddPageDataForDocOffering of class " + getClass(), ex);
			ex.printStackTrace();
		}
	}

	public String beforeprofileView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder("");
			// query.append(" Select first_name, last_name, age, gender,mobile, email, address from patient_basic_data where emp_id='"+empId+"'");
			query.append(" Select pbd.first_name, pbd.last_name, pbd.age, pbd.gender,pbd.mobile, pbd.email, pbd.address,cd.name as nationality,pbd.patient_type,pbd.patient_category ,pbd.blood_group ");
			query.append(" from patient_basic_data as pbd ");
			query.append(" inner join  country_data as cd on cd.id = pbd.nationality ");//
			query.append(" where emp_id='" + empId + "' ");
			List deptData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			System.out.println("query " + query.toString());
			if (deptData != null && deptData.size() > 0)
			{
				for (Object c : deptData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && !dataC[0].toString().equalsIgnoreCase(""))
					{
						setFirst_name(dataC[0].toString());
					} else
					{
						setFirst_name("NA");
					}

					if (dataC[1] != null && !dataC[1].toString().equalsIgnoreCase(""))
					{
						setLast_name(dataC[1].toString());
					} else
					{
						setLast_name("NA");
					}
					if (dataC[2] != null && !dataC[2].toString().equalsIgnoreCase(""))
					{
						String fdate = (String) dataC[2];
						dataC[2] = DateUtil.convertDateToIndianFormat(fdate);
						// System.out.println("dataC[2]   "+dataC[2]);
						setAge(dataC[2].toString());
					} else
					{
						setAge("NA");
					}
					if (dataC[3] != null && !dataC[3].toString().equalsIgnoreCase(""))
					{
						setGender(dataC[3].toString());
					} else
					{
						setGender("NA");
					}
					if (dataC[4] != null && !dataC[4].toString().equalsIgnoreCase(""))
					{
						setMobile(dataC[4].toString());
					} else
					{
						setMobile("NA");
					}
					if (dataC[5] != null && !dataC[5].toString().equalsIgnoreCase(""))
					{
						setEmail(dataC[5].toString());
					} else
					{
						setEmail("NA");
					}
					if (dataC[6] != null && !dataC[6].toString().equalsIgnoreCase(""))
					{
						setAddress(dataC[6].toString());
					} else
					{
						setAddress("NA");
					}
					if (dataC[7] != null && !dataC[7].toString().equalsIgnoreCase(""))
					{
						setNationality(dataC[7].toString());
					} else
					{
						setNationality("NA");
					}
					if (dataC[8] != null && !dataC[8].toString().equalsIgnoreCase(""))
					{
						setPatient_type(dataC[8].toString());
					} else
					{
						setPatient_type("NA");
					}
					if (dataC[9] != null && !dataC[9].toString().equalsIgnoreCase(""))
					{
						setPatient_category(dataC[9].toString());
					} else
					{
						setPatient_category("NA");
					}
					if (dataC[10] != null && !dataC[10].toString().equalsIgnoreCase(""))
					{
						setBlood_group(dataC[10].toString());
					} else
					{
						setBlood_group("NA");
					}

				}
			} else
			{
				setDatavalue(false);
			}
			returnResult = SUCCESS;
		}
		return returnResult;

	}

	public String beforeEditProfile()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			countryMap = new LinkedHashMap<String, String>();
			List countryData = cbt.executeAllSelectQuery("select id, name from country_data  order by name ", connectionSpace);
			// System.out.println("countryMap  " + countryMap);
			if (countryData != null)
			{
				for (Object c : countryData)
				{
					Object[] dataC = (Object[]) c;
					countryMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}

			// System.out.println("empId "+empId);
			StringBuilder query = new StringBuilder("");
			// query.append(" Select first_name, last_name, age, gender,mobile, email, address from patient_basic_data where emp_id='"+empId+"'");
			query.append(" Select pbd.first_name, pbd.last_name, pbd.age, pbd.gender,pbd.mobile, pbd.email, pbd.address,cd.name as nationality,pbd.patient_type,pbd.patient_category ,pbd.blood_group ");
			query.append(" from patient_basic_data as pbd ");
			query.append(" inner join  country_data as cd on cd.id = pbd.nationality ");//
			query.append(" where emp_id='" + empId + "' ");
			List deptData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			System.out.println("query " + query.toString());
			if (deptData != null && deptData.size() > 0)
			{
				for (Object c : deptData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && !dataC[0].toString().equalsIgnoreCase(""))
					{
						setFirst_name(dataC[0].toString());
					} else
					{
						setFirst_name("NA");
					}

					if (dataC[1] != null && !dataC[1].toString().equalsIgnoreCase(""))
					{
						setLast_name(dataC[1].toString());
					} else
					{
						setLast_name("NA");
					}
					if (dataC[2] != null && !dataC[2].toString().equalsIgnoreCase(""))
					{
						String fdate = (String) dataC[2];
						dataC[2] = DateUtil.convertDateToIndianFormat(fdate);
						// System.out.println("dataC[2]    "+dataC[2]);
						setAge(dataC[2].toString());
					} else
					{
						setAge("NA");
					}
					if (dataC[3] != null && !dataC[3].toString().equalsIgnoreCase(""))
					{
						setGender(dataC[3].toString());
					} else
					{
						setGender("NA");
					}
					if (dataC[4] != null && !dataC[4].toString().equalsIgnoreCase(""))
					{
						setMobile(dataC[4].toString());
					} else
					{
						setMobile("NA");
					}
					if (dataC[5] != null && !dataC[5].toString().equalsIgnoreCase(""))
					{
						setEmail(dataC[5].toString());
					} else
					{
						setEmail("NA");
					}
					if (dataC[6] != null && !dataC[6].toString().equalsIgnoreCase(""))
					{
						setAddress(dataC[6].toString());
					} else
					{
						setAddress("NA");
					}
					if (dataC[7] != null && !dataC[7].toString().equalsIgnoreCase(""))
					{
						setNationality(dataC[7].toString());
					} else
					{
						setNationality("NA");
					}
					if (dataC[8] != null && !dataC[8].toString().equalsIgnoreCase(""))
					{
						setPatient_type(dataC[8].toString());
					} else
					{
						setPatient_type("NA");
					}
					if (dataC[9] != null && !dataC[9].toString().equalsIgnoreCase(""))
					{
						setPatient_category(dataC[9].toString());
					} else
					{
						setPatient_category("NA");
					}
					if (dataC[10] != null && !dataC[10].toString().equalsIgnoreCase(""))
					{
						setBlood_group(dataC[10].toString());
					} else
					{
						setBlood_group("NA");
					}

				}
			}
			returnResult = SUCCESS;
		}
		return returnResult;

	}

	public String modifyPatientProfile()
	{

		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			boolean updated = false;

			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();

			condtnBlock.put("emp_id", empId);
			if (getFirst_name() != null)
			{
				wherClause.put("first_name", getFirst_name());
			}

			if (getLast_name() != null)
			{
				wherClause.put("last_name", getLast_name());
			}

			if (getAddress() != null)
			{
				wherClause.put("address", getAddress());
			}
			if (getAge() != null)
			{
				wherClause.put("age", getAge());
			}
			if (getGender() != null)
			{
				wherClause.put("gender", getGender());
			}

			if (getMobile() != null)
			{
				wherClause.put("mobile", getMobile());
			}
			if (getEmail() != null)
			{
				wherClause.put("email", getEmail());
			}

			if (getNationality() != null)
			{
				wherClause.put("nationality", getNationality());
			}
			if (getPatient_type() != null)
			{
				wherClause.put("patient_type", getPatient_type());
			}
			if (getPatient_category() != null)
			{
				wherClause.put("patient_category", getPatient_category());
			}
			if (getBlood_group() != null)
			{
				wherClause.put("blood_group", getBlood_group());
			}

			updated = new FeedbackUniversalHelper().updateTableColomn("patient_basic_data", wherClause, condtnBlock, connectionSpace);
			if (updated)
			{
				addActionMessage("Profile Updated Successfully !!!");
			} else
			{
				addActionMessage("There is some problem.");
			}
			return SUCCESS;
		} else
		{
			return LOGIN;
		}
	}

	public String patientBasicDetails()
	{
		try
		{
			// System.out.println(" Profile Data "+ id);
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			profilemap = new LinkedHashMap<String, String>();

			sb.append("select concat(pbd.first_name,' ',pbd.last_name),cd.country_name,pbd.mobile,pbd.email,pbd.address,relsub.rel_subtype as type,cat.cat_subtype as patient_category,pbd.blood_group,pbd.allergic_to,pbd.patien_id, pbd.gender,pbd.age, pbd.uh_id ");
			sb.append(" from patient_basic_data as pbd ");
			sb.append(" left join country as cd ON cd.id = pbd.nationality " + " left join relationship_sub_type as relsub on relsub.id=pbd.type " + " left join category_type as cat on cat.id = pbd.patient_category ");
			sb.append(" where pbd.id= '" + id + "'");

			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				profilemap.put("Patient Name", getValueWithNullCheck(data[0]));
				profilemap.put("Nationality", getValueWithNullCheck(data[1]));
				profilemap.put("Moblie No", getValueWithNullCheck(data[2]));
				profilemap.put("Email", getValueWithNullCheck(data[3]));
				profilemap.put("Address", getValueWithNullCheck(data[4]));
				profilemap.put("Patient Type", getValueWithNullCheck(data[5]));
				profilemap.put("Patient Category", getValueWithNullCheck(data[6]));
				profilemap.put("Blood Group", getValueWithNullCheck(data[7]));
				profilemap.put("Allergic To", getValueWithNullCheck(data[8]));
				profilemap.put("Patient ID", getValueWithNullCheck(data[9]));
				profilemap.put("Gender", getValueWithNullCheck(data[10]));
				profilemap.put("Age", DateUtil.convertDateToIndianFormat(getValueWithNullCheck(data[11])));
				profilemap.put("UHID", getValueWithNullCheck(data[12]));

			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String questionSentDetails()
	{
		try
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			JSONObject jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			profilemap = new LinkedHashMap<String, String>();

			sb.append("select pbd.date, pbd.time");
			sb.append(" from patient_basic_data as pbd ");
			sb.append(" inner join country_data as cd on cd.id= pbd.nationality");
			sb.append(" where pbd.id= '" + id + "'");

			List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);
			System.out.println(sb.toString());
			for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
			{
				Object obj = iterator.next();
				Object data[] = (Object[]) obj;
				profilemap.put("Sent Date", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[0].toString())));
				profilemap.put("Sent Time", getValueWithNullCheck(data[1]));
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String modifyPatientGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
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
					String time = null;
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if ((paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")))
					{
						wherClause.put(Parmname, paramValue);
					}
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("patient_basic_data", wherClause, condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del"))
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
				cbt.deleteAllRecordForId("patient_basic_data", "id", condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method editVisitorEntryGrid of class " + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String patientAddBasicDetails()
	{
		try
		{
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			jsonObject = new JSONObject();
			StringBuilder sb = new StringBuilder("");
			CommonOperInterface coi = new CommonConFactory().createInterface();
			profilemap = new LinkedHashMap<String, String>();

			if (crm_id != null && !crm_id.equalsIgnoreCase("undefined"))
			{
				sb.append("select pbd.first_name,pbd.last_name,pbd.nationality,pbd.mobile,pbd.email," + "pbd.address,pbd.patient_type,pbd.patient_category,pbd.blood_group,pbd.allergic_to," + "pbd.patien_id, pbd.gender,pbd.age, pbd.uh_id,pbd.type,pbd.city, pbd.state , pbd.territory ");
				sb.append(" from patient_basic_data as pbd ");
				sb.append(" where pbd.uh_id='" + crm_id + "'");
				System.out.println(sb.toString());
				List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
					{
						Object obj = iterator.next();
						Object data[] = (Object[]) obj;

						profilemap.put("First_Name", getValueWithNullCheck(data[0]));
						profilemap.put("Last_Name", getValueWithNullCheck(data[1]));
						profilemap.put("Nationality", getValueWithNullCheck(data[2]));
						profilemap.put("Moblie_No", getValueWithNullCheck(data[3]));
						profilemap.put("Email", getValueWithNullCheck(data[4]));
						profilemap.put("Address", getValueWithNullCheck(data[5]));
						profilemap.put("Patient_Type", getValueWithNullCheck(data[6]));
						profilemap.put("Patient_Category", getValueWithNullCheck(data[7]));
						profilemap.put("Blood_Group", getValueWithNullCheck(data[8]));
						profilemap.put("Allergic_To", getValueWithNullCheck(data[9]));
						profilemap.put("Patient_ID", getValueWithNullCheck(data[10]));
						profilemap.put("Gender", getValueWithNullCheck(data[11]));
						profilemap.put("Age", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[12].toString())));
						profilemap.put("UHID", getValueWithNullCheck(data[13]));
						profilemap.put("Type", getValueWithNullCheck(data[14]));
						profilemap.put("City", getValueWithNullCheck(data[15]));
						profilemap.put("State", getValueWithNullCheck(data[16]));
						profilemap.put("Territory", getValueWithNullCheck(data[17]));

						profilemap.put("No", "Record Already Exist For " + crm_id);

					}
				} else
				{
					profilemap.put("No", "No Record Found for " + crm_id);
				}

			}
			if (mobile != null && !mobile.equalsIgnoreCase("undefined"))
			{
				sb.append("select pbd.first_name,pbd.last_name,pbd.nationality,pbd.mobile," + "pbd.email,pbd.address,pbd.patient_type,pbd.patient_category,pbd.blood_group," + "pbd.allergic_to,pbd.patien_id, pbd.gender,pbd.age, pbd.uh_id,pbd.type,pbd.city" + " , pbd.state, pbd.territory ");
				sb.append(" from patient_basic_data as pbd ");
				sb.append(" where pbd.mobile='" + mobile + "'");
				System.out.println(sb.toString());
				List dataList = coi.executeAllSelectQuery(sb.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext(); dataArray.add(jsonObject))
					{
						Object obj = iterator.next();
						Object data[] = (Object[]) obj;
						profilemap.put("First_Name", getValueWithNullCheck(data[0]));
						profilemap.put("Last_Name", getValueWithNullCheck(data[1]));
						profilemap.put("Nationality", getValueWithNullCheck(data[2]));
						profilemap.put("Moblie_No", getValueWithNullCheck(data[3]));
						profilemap.put("Email", getValueWithNullCheck(data[4]));
						profilemap.put("Address", getValueWithNullCheck(data[5]));
						profilemap.put("Patient_Type", getValueWithNullCheck(data[6]));
						profilemap.put("Patient_Category", getValueWithNullCheck(data[7]));
						profilemap.put("Blood_Group", getValueWithNullCheck(data[8]));
						profilemap.put("Allergic_To", getValueWithNullCheck(data[9]));
						profilemap.put("Patient_ID", getValueWithNullCheck(data[10]));
						profilemap.put("Gender", getValueWithNullCheck(data[11]));
						profilemap.put("Age", getValueWithNullCheck(DateUtil.convertDateToIndianFormat(data[12].toString())));
						profilemap.put("UHID", getValueWithNullCheck(data[13]));
						profilemap.put("Type", getValueWithNullCheck(data[14]));
						profilemap.put("City", getValueWithNullCheck(data[15]));
						profilemap.put("State", getValueWithNullCheck(data[16]));
						profilemap.put("Territory", getValueWithNullCheck(data[17]));
						profilemap.put("No", "Record Already Exist For " + mobile);
					}
				} else
				{
					profilemap.put("No", "No Record Found for " + mobile);
				}

			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public String nxxMytLevelEscnext()
	{
		try
		{
			String empID = " ";

			if (!l1.equalsIgnoreCase("null") && !l1.equalsIgnoreCase("undefined"))
			{
				empID = l1;
				// System.out.println("get 2");
			}
			if (!l2.equalsIgnoreCase("null") && !l2.equalsIgnoreCase("undefined"))
			{
				empID = empID + ", " + l2;
				// System.out.println("get 3");
			}
			if (!l3.equalsIgnoreCase("null") && !l3.equalsIgnoreCase("undefined"))
			{
				empID = empID + ", " + l3;
				// System.out.println("get 4");
			}
			if (!l4.equalsIgnoreCase("null") && !l4.equalsIgnoreCase("undefined"))
			{
				empID = empID + ", " + l4;
				// System.out.println("get 5");
			}

			// System.out.println("empID " + empID);
			escL5EmpNxtLevel = new LinkedHashMap<String, String>();

			escL5EmpNxtLevel = getComplianceAllContacts4NxtExc(empID, "WFPM");
			if (escL5EmpNxtLevel != null && escL5EmpNxtLevel.size() > 0)
			{
				jsonArray = new JSONArray();
				for (Iterator iterator = escL5EmpNxtLevel.entrySet().iterator(); iterator.hasNext();)
				{
					Map.Entry object = (Map.Entry) iterator.next();
					if (object.getKey() != null && object.getValue() != null)
					{
						jsonObject = new JSONObject();
						jsonObject.put("ID", object.getKey());
						jsonObject.put("name", object.getValue());
						jsonArray.add(jsonObject);
					}
				}
			}
			return SUCCESS;
		} catch (Exception e)
		{
			return ERROR;
		}
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public String getOfferingId()
	{
		return offeringId;
	}

	public void setOfferingId(String offeringId)
	{
		this.offeringId = offeringId;
	}

	public String getOfferinglevel()
	{
		return Offeringlevel;
	}

	public void setOfferinglevel(String offeringlevel)
	{
		Offeringlevel = offeringlevel;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public String getRowid()
	{
		return rowid;
	}

	public void setRowid(String rowid)
	{
		this.rowid = rowid;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
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

	public void setIsExistingClient(String isExistingClient)
	{
		this.isExistingClient = isExistingClient;
	}

	public String getIsExistingClient()
	{
		return isExistingClient;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getOfferingName()
	{
		return offeringName;
	}

	public void setOfferingName(String offeringName)
	{
		this.offeringName = offeringName;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public String getOfferingExist()
	{
		return offeringExist;
	}

	public void setOfferingExist(String offeringExist)
	{
		this.offeringExist = offeringExist;
	}

	public String getOffering()
	{
		return offering;
	}

	public void setOffering(String offering)
	{
		this.offering = offering;
	}

	public List<ConfigurationUtilBean> getPatientTextBox()
	{
		return patientTextBox;
	}

	public void setPatientTextBox(List<ConfigurationUtilBean> patientTextBox)
	{
		this.patientTextBox = patientTextBox;
	}

	public List<ConfigurationUtilBean> getPatientDropDown()
	{
		return patientDropDown;
	}

	public void setPatientDropDown(List<ConfigurationUtilBean> patientDropDown)
	{
		this.patientDropDown = patientDropDown;
	}

	public void setOLevel1(boolean oLevel1)
	{
		OLevel1 = oLevel1;
	}

	public boolean isOLevel1()
	{
		return OLevel1;
	}

	public String getOfferingLevel()
	{
		return offeringLevel;
	}

	public void setOfferingLevel(String offeringLevel)
	{
		this.offeringLevel = offeringLevel;
	}

	public boolean isOLevel2()
	{
		return OLevel2;
	}

	public void setOLevel2(boolean level2)
	{
		OLevel2 = level2;
	}

	public boolean isOLevel3()
	{
		return OLevel3;
	}

	public void setOLevel3(boolean level3)
	{
		OLevel3 = level3;
	}

	public boolean isOLevel4()
	{
		return OLevel4;
	}

	public void setOLevel4(boolean level4)
	{
		OLevel4 = level4;
	}

	public boolean isOLevel5()
	{
		return OLevel5;
	}

	public void setOLevel5(boolean level5)
	{
		OLevel5 = level5;
	}

	public String getOLevel1LevelName()
	{
		return OLevel1LevelName;
	}

	public void setOLevel1LevelName(String level1LevelName)
	{
		OLevel1LevelName = level1LevelName;
	}

	public String getOLevel2LevelName()
	{
		return OLevel2LevelName;
	}

	public void setOLevel2LevelName(String level2LevelName)
	{
		OLevel2LevelName = level2LevelName;
	}

	public String getOLevel3LevelName()
	{
		return OLevel3LevelName;
	}

	public void setOLevel3LevelName(String level3LevelName)
	{
		OLevel3LevelName = level3LevelName;
	}

	public String getOLevel4LevelName()
	{
		return OLevel4LevelName;
	}

	public void setOLevel4LevelName(String level4LevelName)
	{
		OLevel4LevelName = level4LevelName;
	}

	public String getOLevel5LevelName()
	{
		return OLevel5LevelName;
	}

	public void setOLevel5LevelName(String level5LevelName)
	{
		OLevel5LevelName = level5LevelName;
	}

	public LinkedHashMap<String, String> getVerticalMap()
	{
		return verticalMap;
	}

	public void setVerticalMap(LinkedHashMap<String, String> verticalMap)
	{
		this.verticalMap = verticalMap;
	}

	public void setTraceid(String traceid)
	{
		this.traceid = traceid;
	}

	public String getTraceid()
	{
		return traceid;
	}

	public List<ConfigurationUtilBean> getPatientBasicTextBox()
	{
		return patientBasicTextBox;
	}

	public void setPatientBasicTextBox(List<ConfigurationUtilBean> patientBasicTextBox)
	{
		this.patientBasicTextBox = patientBasicTextBox;
	}

	public LinkedHashMap<String, String> getCorporateMap()
	{
		return corporateMap;
	}

	public void setCorporateMap(LinkedHashMap<String, String> corporateMap)
	{
		this.corporateMap = corporateMap;
	}

	public LinkedHashMap<String, String> getCountryMap()
	{
		return countryMap;
	}

	public void setCountryMap(LinkedHashMap<String, String> countryMap)
	{
		this.countryMap = countryMap;
	}

	public Map<String, String> getLocationList()
	{
		return locationList;
	}

	public void setLocationList(Map<String, String> locationList)
	{
		this.locationList = locationList;
	}

	public LinkedHashMap<String, String> getAssociateMap()
	{
		return associateMap;
	}

	public void setAssociateMap(LinkedHashMap<String, String> associateMap)
	{
		this.associateMap = associateMap;
	}

	public String getPatien_id()
	{
		return patien_id;
	}

	public void setPatien_id(String patien_id)
	{
		this.patien_id = patien_id;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public Map<String, String> getEscL2Emp()
	{
		return escL2Emp;
	}

	public void setEscL2Emp(Map<String, String> escL2Emp)
	{
		this.escL2Emp = escL2Emp;
	}

	public Map<String, String> getEscL3Emp()
	{
		return escL3Emp;
	}

	public void setEscL3Emp(Map<String, String> escL3Emp)
	{
		this.escL3Emp = escL3Emp;
	}

	public Map<String, String> getEscL4Emp()
	{
		return escL4Emp;
	}

	public void setEscL4Emp(Map<String, String> escL4Emp)
	{
		this.escL4Emp = escL4Emp;
	}

	public Map<String, String> getEscL5Emp()
	{
		return escL5Emp;
	}

	public void setEscL5Emp(Map<String, String> escL5Emp)
	{
		this.escL5Emp = escL5Emp;
	}

	public Map<String, String> getEscL5EmpNxtLevel()
	{
		return escL5EmpNxtLevel;
	}

	public void setEscL5EmpNxtLevel(Map<String, String> escL5EmpNxtLevel)
	{
		this.escL5EmpNxtLevel = escL5EmpNxtLevel;
	}

	public String getL1()
	{
		return l1;
	}

	public void setL1(String l1)
	{
		this.l1 = l1;
	}

	public String getL2()
	{
		return l2;
	}

	public void setL2(String l2)
	{
		this.l2 = l2;
	}

	public String getL3()
	{
		return l3;
	}

	public void setL3(String l3)
	{
		this.l3 = l3;
	}

	public String getL4()
	{
		return l4;
	}

	public void setL4(String l4)
	{
		this.l4 = l4;
	}

	public String getDiv()
	{
		return div;
	}

	public void setDiv(String div)
	{
		this.div = div;
	}

	public String getSId()
	{
		return sId;
	}

	public void setSId(String id)
	{
		sId = id;
	}

	public String getSName()
	{
		return sName;
	}

	public void setSName(String name)
	{
		sName = name;
	}

	public String getNId()
	{
		return nId;
	}

	public void setNId(String id)
	{
		nId = id;
	}

	public String getNName()
	{
		return nName;
	}

	public void setNName(String name)
	{
		nName = name;
	}

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public Map<String, Object> getOfferingMap()
	{
		return offeringMap;
	}

	public void setOfferingMap(Map<String, Object> offeringMap)
	{
		this.offeringMap = offeringMap;
	}

	public String getLaststatus()
	{
		return laststatus;
	}

	public void setLaststatus(String laststatus)
	{
		this.laststatus = laststatus;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getBlood_group()
	{
		return blood_group;
	}

	public void setBlood_group(String blood_group)
	{
		this.blood_group = blood_group;
	}

	public String getPatient_category()
	{
		return patient_category;
	}

	public void setPatient_category(String patient_category)
	{
		this.patient_category = patient_category;
	}

	public String getPatient_type()
	{
		return patient_type;
	}

	public void setPatient_type(String patient_type)
	{
		this.patient_type = patient_type;
	}

	public String getCorporate()
	{
		return corporate;
	}

	public void setCorporate(String corporate)
	{
		this.corporate = corporate;
	}

	public String getReferral()
	{
		return referral;
	}

	public void setReferral(String referral)
	{
		this.referral = referral;
	}

	public String getL5()
	{
		return l5;
	}

	public void setL5(String l5)
	{
		this.l5 = l5;
	}

	public Map<String, Object> getRelManagerMap()
	{
		return relManagerMap;
	}

	public void setRelManagerMap(Map<String, Object> relManagerMap)
	{
		this.relManagerMap = relManagerMap;
	}

	public String getDepName()
	{
		return depName;
	}

	public void setDepName(String depName)
	{
		this.depName = depName;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public void setDeptMap(LinkedHashMap<String, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public LinkedHashMap<String, String> getDeptMap()
	{
		return deptMap;
	}

	public void setStatus_view(String status_view)
	{
		this.status_view = status_view;
	}

	public String getStatus_view()
	{
		return status_view;
	}

	public void setSent_questionair(String sent_questionair)
	{
		this.sent_questionair = sent_questionair;
	}

	public String getSent_questionair()
	{
		return sent_questionair;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getAge()
	{
		return age;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getAddress()
	{
		return address;
	}

	public String getDept_name()
	{
		return dept_name;
	}

	public void setDept_name(String dept_name)
	{
		this.dept_name = dept_name;
	}

	public void setDatavalue(boolean datavalue)
	{
		this.datavalue = datavalue;
	}

	public boolean isDatavalue()
	{
		return datavalue;
	}

	public Map<String, String> getProfilemap()
	{
		return profilemap;
	}

	public void setProfilemap(Map<String, String> profilemap)
	{
		this.profilemap = profilemap;
	}

	public JSONArray getDataArray()
	{
		return dataArray;
	}

	public void setDataArray(JSONArray dataArray)
	{
		this.dataArray = dataArray;
	}

	public void setSearchParameter(String searchParameter)
	{
		this.searchParameter = searchParameter;
	}

	public String getSearchParameter()
	{
		return searchParameter;
	}

	public String getTotalmaleCount()
	{
		return totalmaleCount;
	}

	public void setTotalmaleCount(String totalmaleCount)
	{
		this.totalmaleCount = totalmaleCount;
	}

	public String getTotalFemaleCount()
	{
		return totalFemaleCount;
	}

	public void setTotalFemaleCount(String totalFemaleCount)
	{
		this.totalFemaleCount = totalFemaleCount;
	}

	public LinkedHashMap<String, String> getRhTypeMap()
	{
		return rhTypeMap;
	}

	public void setRhTypeMap(LinkedHashMap<String, String> rhTypeMap)
	{
		this.rhTypeMap = rhTypeMap;
	}

	public LinkedHashMap<String, String> getRhSubTypeMap()
	{
		return rhSubTypeMap;
	}

	public void setRhSubTypeMap(LinkedHashMap<String, String> rhSubTypeMap)
	{
		this.rhSubTypeMap = rhSubTypeMap;
	}

	public LinkedHashMap<String, String> getStageMap()
	{
		return stageMap;
	}

	public void setStageMap(LinkedHashMap<String, String> stageMap)
	{
		this.stageMap = stageMap;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getCrm_id()
	{
		return crm_id;
	}

	public void setCrm_id(String crm_id)
	{
		this.crm_id = crm_id;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public String getLastComment()
	{
		return lastComment;
	}

	public void setLastComment(String lastComment)
	{
		this.lastComment = lastComment;
	}

	public String getNextComment()
	{
		return nextComment;
	}

	public void setNextComment(String nextComment)
	{
		this.nextComment = nextComment;
	}

	public String getLastDate()
	{
		return lastDate;
	}

	public void setLastDate(String lastDate)
	{
		this.lastDate = lastDate;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDrName()
	{
		return drName;
	}

	public void setDrName(String drName)
	{
		this.drName = drName;
	}

	public String getRelName()
	{
		return relName;
	}

	public void setRelName(String relName)
	{
		this.relName = relName;
	}

	public LinkedHashMap<String, String> getSourceMap()
	{
		return sourceMap;
	}

	public void setSourceMap(LinkedHashMap<String, String> sourceMap)
	{
		this.sourceMap = sourceMap;
	}

	public String getStage()
	{
		return stage;
	}

	public void setStage(String stage)
	{
		this.stage = stage;
	}

	public Map<String, String> getEmpMap()
	{
		return empMap;
	}

	public void setEmpMap(Map<String, String> empMap)
	{
		this.empMap = empMap;
	}

	public String getsId()
	{
		return sId;
	}

	public void setsId(String sId)
	{
		this.sId = sId;
	}

	public String getsName()
	{
		return sName;
	}

	public void setsName(String sName)
	{
		this.sName = sName;
	}

	public String getnId()
	{
		return nId;
	}

	public void setnId(String nId)
	{
		this.nId = nId;
	}

	public String getnName()
	{
		return nName;
	}

	public void setnName(String nName)
	{
		this.nName = nName;
	}

	public String getL2_99()
	{
		return l2_99;
	}

	public void setL2_99(String l2_99)
	{
		this.l2_99 = l2_99;
	}

	public String getL3_99()
	{
		return l3_99;
	}

	public void setL3_99(String l3_99)
	{
		this.l3_99 = l3_99;
	}

	public String getL4_99()
	{
		return l4_99;
	}

	public void setL4_99(String l4_99)
	{
		this.l4_99 = l4_99;
	}

	public String getL5_99()
	{
		return l5_99;
	}

	public void setL5_99(String l5_99)
	{
		this.l5_99 = l5_99;
	}

	public String getL2_100()
	{
		return l2_100;
	}

	public void setL2_100(String l2_100)
	{
		this.l2_100 = l2_100;
	}

	public String getL3_100()
	{
		return l3_100;
	}

	public void setL3_100(String l3_100)
	{
		this.l3_100 = l3_100;
	}

	public String getL4_100()
	{
		return l4_100;
	}

	public void setL4_100(String l4_100)
	{
		this.l4_100 = l4_100;
	}

	public String getL5_100()
	{
		return l5_100;
	}

	public void setL5_100(String l5_100)
	{
		this.l5_100 = l5_100;
	}

	public String getL2_101()
	{
		return l2_101;
	}

	public void setL2_101(String l2_101)
	{
		this.l2_101 = l2_101;
	}

	public String getL3_101()
	{
		return l3_101;
	}

	public void setL3_101(String l3_101)
	{
		this.l3_101 = l3_101;
	}

	public String getL4_101()
	{
		return l4_101;
	}

	public void setL4_101(String l4_101)
	{
		this.l4_101 = l4_101;
	}

	public String getL5_101()
	{
		return l5_101;
	}

	public void setL5_101(String l5_101)
	{
		this.l5_101 = l5_101;
	}

	public String getL2_102()
	{
		return l2_102;
	}

	public void setL2_102(String l2_102)
	{
		this.l2_102 = l2_102;
	}

	public String getL3_102()
	{
		return l3_102;
	}

	public void setL3_102(String l3_102)
	{
		this.l3_102 = l3_102;
	}

	public String getL4_102()
	{
		return l4_102;
	}

	public void setL4_102(String l4_102)
	{
		this.l4_102 = l4_102;
	}

	public String getL5_102()
	{
		return l5_102;
	}

	public void setL5_102(String l5_102)
	{
		this.l5_102 = l5_102;
	}

	public String getL2_103()
	{
		return l2_103;
	}

	public void setL2_103(String l2_103)
	{
		this.l2_103 = l2_103;
	}

	public String getL3_103()
	{
		return l3_103;
	}

	public void setL3_103(String l3_103)
	{
		this.l3_103 = l3_103;
	}

	public String getL4_103()
	{
		return l4_103;
	}

	public void setL4_103(String l4_103)
	{
		this.l4_103 = l4_103;
	}

	public String getL5_103()
	{
		return l5_103;
	}

	public void setL5_103(String l5_103)
	{
		this.l5_103 = l5_103;
	}

}