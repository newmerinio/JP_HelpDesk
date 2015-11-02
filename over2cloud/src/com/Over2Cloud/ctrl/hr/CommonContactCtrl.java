package com.Over2Cloud.ctrl.hr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.compliance.ComplianceExcelDownload;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.krLibrary.KRActionHelper;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;

@SuppressWarnings("serial")
public class CommonContactCtrl extends GridPropertyBean
{
	static final Log log = LogFactory.getLog(CommonContactCtrl.class);
	private boolean statusFlag = false;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> contactTextBox;
	private List<ConfigurationUtilBean> contactDateTimeBox;
	private List<ConfigurationUtilBean> contactFormDDBox;
	private List<ConfigurationUtilBean> contactFileBox;
	private List<ConfigurationUtilBean> contactDD;
	private Map<Integer, String> groupMap;
	private Map<Integer, String> subGroupMap;
	private Map<Integer, String> deptMap;
	private Map<Integer, String> desgMap;
	private Map<Integer, String> industryMap;
	private Map<Integer, String> sourceMap;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private String searchFields;
	private String SearchValue;
	// For File Upload and Image
	public File image;
	private String imageContentType;
	private String imageFileName;

	public File document;
	private String documentContentType;
	private String documentFileName;

	private String groupId;
	private String regLevel;
	private String subGroupId;
	private String industryId;
	private JSONArray commonJSONArray = new JSONArray();
	private HttpServletRequest request;
	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private Map<Integer, String> officeMap;
	private Map<String, String> checkEmp;
	private String excelFileName;
	private String mobile_no;
	private String empId;
	private FileInputStream excelStream;
	private String contentType;
	private File contactExcel;
	private String contactExcelContentType;
	private String contactExcelFileName;
	private String groupName;
	private String contId;
	private String documentName;
	private Map<String, String> docMap;;
	private InputStream fileInputStream;
	private Map<String, String> totalCount;
	private JSONArray jsonObjArray;
	private JSONArray userTypeMap = null;
	private String selectedId;
	private JSONArray jssonArr;
	private boolean hodStatus=false;
	private boolean mgtStatus=false;

	public String docDownload()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getContId() != null)
				{
					List docList = cbt.executeAllSelectQuery("select document from primary_contact where id='" + getContId() + "'", connectionSpace);
					if (docList != null && docList.size() > 0)
					{
						docMap = new LinkedHashMap<String, String>();
						String str = null;
						Object object = null;
						for (Iterator iterator = docList.iterator(); iterator.hasNext();)
						{
							object = (Object) iterator.next();
							if (object != null)
							{
								str = object.toString().substring(object.toString().lastIndexOf("//") + 2, object.toString().length());
								documentName = str.substring(14, str.length());
								docMap.put(object.toString(), documentName);
							}
						}

						File file = new File(str);
						documentName = str.substring(14, str.length());
						if (file.exists())
						{
							fileInputStream = new FileInputStream(file);
							return SUCCESS;
						}
						else
						{
							addActionError("File dose not exist");
							return ERROR;
						}
					}

				}
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

	public String getAvailability()
	{
		try
		{
			if (mobile_no != null)
			{
				checkEmp = new HashMap<String, String>();
				// check user mobile number
				boolean status = false;
				if (mobile_no.matches(".*\\d.*") && mobile_no.length() == 10)
				{

					List lowerLevel3 = new ArrayList<String>();
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put("mobile_no", mobile_no);
					if (getGroupId() != null)
					{
						temp.put("sub_type_id", getSubGroupId());
					}
					lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("primary_contact", lowerLevel3, temp, connectionSpace);
					if (lowerLevel3 != null && lowerLevel3.size() > 0)
					{
						status = true;
					}
					if (!status)
					{
						checkEmp.put("msg", "You Can Create.");
						checkEmp.put("status", "false");
					}
					else
					{
						checkEmp.put("msg", "Already exist.");
						checkEmp.put("status", "true");
					}

				}
				else
				{
					checkEmp.put("msg", "Please Enter Valid details");
					checkEmp.put("status", "true");
				}
			}
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getAvailability of class " + getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
	}

	public String beforeAdd()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				setContactAddPageDataFields();
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAdd of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String contactImageView()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List dataList = cbt.executeAllSelectQuery("select image from primary_contact where id='" + getId() + "'", connectionSpace);
				if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
				{
					setImageFileName(dataList.get(0).toString());
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAdd of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	@SuppressWarnings(
	{ "unchecked" })
	public String contactViewInGrid()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				String userName = (String) session.get("uName");
				List dataCount = cbt.executeAllSelectQuery("select count(*) from primary_contact where status!=1", connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
				}
				StringBuilder query = new StringBuilder(" select ");
				List fieldNames = Configuration.getColomnList("mapped_common_contact_configuration", accountID, connectionSpace, "common_contact_configuration");
				fieldNames.add("contact_name");
				List fieldNamesEmp = Configuration.getColomnList("mapped_primary_contact_configuration", accountID, connectionSpace, "primary_contact_configuration");
				List fieldNamesLead = new ArrayList();
				List clientExists = cbt.executeAllSelectQuery("select * from contact_type where contact_name='Client'", connectionSpace);
				if (clientExists != null && clientExists.size() > 0)
				{
					fieldNamesLead = Configuration.getColomnList("mapped_client_contact_configuration", accountID, connectionSpace, "client_contact_configuration");
				}
				List<Object> listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (i < fieldNames.size() - 1)
					{
						if (obdata.toString().equalsIgnoreCase("sub_type_id"))
						{
							query.append("dpt.contact_subtype_name AS dept1,");
						}
						else if(obdata.toString().equalsIgnoreCase("contact_name"))
						{
							query.append("grp.contact_name AS contactName,");
						}
						else
						{
							query.append("contct." + obdata.toString() + ",");
						}
					}
					else
					{
						if (obdata.toString().equalsIgnoreCase("sub_type_id"))
						{
							query.append("dpt.contact_subtype_name AS dept1");
						}
						else if(obdata.toString().equalsIgnoreCase("contact_name"))
						{
							query.append("grp.contact_name AS contactName");
						}
						else
						{
							query.append("contct." + obdata.toString());
						}
					}
					i++;
				}
				
				for (Iterator it = fieldNamesEmp.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					if (obdata != null && !obdata.toString().equalsIgnoreCase("id"))
					{
						query.append(",contct." + obdata.toString());
						fieldNames.add(obdata.toString());
					}
					i++;
				}
				if (clientExists != null && clientExists.size() > 0)
				{
					for (Iterator it = fieldNamesLead.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null && !obdata.toString().equalsIgnoreCase("id") && obdata != null && !obdata.toString().equalsIgnoreCase("contactName") && !obdata.toString().equalsIgnoreCase("dateOfBirth") && !obdata.toString().equalsIgnoreCase("dateOfJoining") && !obdata.toString().equalsIgnoreCase("dateOfAnnvrsry") && !obdata.toString().equalsIgnoreCase("mobileNo") && !obdata.toString().equalsIgnoreCase("contactEmailId") && !obdata.toString().equalsIgnoreCase("orgAddress"))
						{
							if (obdata.toString().equalsIgnoreCase("source"))
							{
								query.append(",sm.sourceName ");
							}
							else if (obdata.toString().equalsIgnoreCase("department"))
							{
								query.append(",dept1.deptName As clientDept ");
							}
							else if (obdata.toString().equalsIgnoreCase("industry"))
							{
								query.append(",idus.industry As indu ");
							}
							else if (obdata.toString().equalsIgnoreCase("subIndustry"))
							{
								query.append(",subIndus.subIndustry As subIndus ");
							}
							else
							{
								query.append(",contct." + obdata.toString());
							}

							fieldNames.add(obdata.toString());
						}
						i++;
					}
				}
				query.append("  from primary_contact as contct LEFT join contact_sub_type as dpt on dpt.id=contct.sub_type_id  LEFT join contact_type as grp on grp.id= dpt.contact_type_id  ");
				if (clientExists != null && clientExists.size() > 0)
				{
					query.append("  LEFT join contact_sub_type as dept1 on dept1.id=contct.department  LEFT JOIN sourcemaster AS sm ON sm.id=contct.source " + " LEFT JOIN industrydatalevel1 AS idus ON idus.id=contct.industry " + " LEFT JOIN subindustrydatalevel2 AS subIndus ON subIndus.id=contct.subIndustry ");
				}
				query.append("  where  contct.id!=0  ");
				String userType = (String) session.get("userTpe");
				if (getSearchFields() != null && !getSearchFields().equalsIgnoreCase("-1") && getSearchValue() != null && !getSearchValue().equalsIgnoreCase("-1"))
				{
					if (!getSearchFields().equalsIgnoreCase("contct.status"))
					{
						query.append("  and " + getSearchFields() + "='" + getSearchValue() + "' AND contct.status!=1 ");
					}
					else
					{
						query.append("  and " + getSearchFields() + "='" + getSearchValue() + "' ");
					}
				}

				if (userType != null && userType.toString().equalsIgnoreCase("H"))
				{
					String deptId[] = new ComplianceUniversalAction().getEmpDataByUserName(userName);
					if (deptId != null && !deptId.toString().equalsIgnoreCase(""))
					{
						query.append("  and contct.sub_type_id='" + deptId[3] + "'  ");
					}
				}
				else if (userType != null && userType.toString().equalsIgnoreCase("N"))
				{
					query.append(" and contct.user_name='" + userName + "'");

				}
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					query.append(" and  ");
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
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								}
								else
								{
									if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("dob") || fieldNames.get(k).toString().equalsIgnoreCase("doa") || fieldNames.get(k).toString().equalsIgnoreCase("doj") || fieldNames.get(k).toString().equalsIgnoreCase("createdDate"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("image"))
									{
										one.put(fieldNames.get(k).toString(), "View Image");
									}
									else if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("status"))
									{
										if (obdata[k].toString() != null && obdata[k].toString().equalsIgnoreCase("0"))
										{
											one.put(fieldNames.get(k).toString(), "Active");
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "Inactive");
										}
									}
									else
									{
										if (obdata[k].toString() != null)
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
										else
										{
											one.put(fieldNames.get(k).toString(), "NA");
										}
									}
								}
							}
							else
							{
								one.put(fieldNames.get(k).toString(), "NA");
							}
						}
						listhb.add(one);
					}
					setMasterViewList(listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method contactViewInGrid of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	@SuppressWarnings("unchecked")
	public String addContact()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				String userName = (String) session.get("uName");
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "common_contact_configuration");
				boolean userTrue = true;
				boolean dateTrue = true;
				boolean timeTrue = true;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				for (GridDataPropertyView gdp : statusColName)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype(gdp.getData_type());
					if (gdp.getColomnName().equalsIgnoreCase("status"))
					{
						ob1.setConstraint("default 0");
					}
					else
					{
						ob1.setConstraint("default NULL");
					}
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("user_name"))
						userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("created_date"))
						dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("created_time"))
						timeTrue = true;
				}
				statusColName.clear();
				statusColName = Configuration.getConfigurationData("mapped_primary_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "primary_contact_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}
				}
				statusColName.clear();
				statusColName = Configuration.getConfigurationData("mapped_client_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						if (!gdp.getColomnName().equalsIgnoreCase("orgAddress") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("mobileNo") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("contactName") && !gdp.getColomnName().equalsIgnoreCase("dateOfJoining") && !gdp.getColomnName().equalsIgnoreCase("dateOfAnnvrsry") && !gdp.getColomnName().equalsIgnoreCase("designation"))
						{
							TableColumes ob1 = new TableColumes();
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype(gdp.getData_type());
							ob1.setConstraint("default NULL");
							Tablecolumesaaa.add(ob1);
						}
					}
				}
				cbt.createTable22("primary_contact", Tablecolumesaaa, connectionSpace);
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				String orgName = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
				}

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				String contactMobNo = null;
				boolean status = false;
				String contactId = null;
				List lowerLevel3 = new ArrayList<String>();
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("mobile_no", mobile_no);
				if (getSubGroupId() != null)
				{
					temp.put("sub_type_id", getSubGroupId());
				}
				lowerLevel3 = cbt.viewAllDataEitherSelectOrAll("primary_contact", lowerLevel3, temp, connectionSpace);
				if (lowerLevel3 != null && lowerLevel3.size() > 0)
				{
					status = true;
					addActionMessage("Contact Already Exists. !!!");
					return "success";
				}
				while (it.hasNext())
				{
					String parmname = it.next().toString();
					String paramValue = request.getParameter(parmname);

					if (parmname.equalsIgnoreCase("contactName"))
					{
						parmname = "emp_name";
					}
					else if (parmname.equalsIgnoreCase("mobileNo"))
					{
						parmname = "mobile_no";
					}
					else if (parmname.equalsIgnoreCase("contactEmailId"))
					{
						parmname = "email_id";
					}
					else if (parmname.equalsIgnoreCase("dateOfBirth"))
					{
						parmname = "dob";
					}
					else if (parmname.equalsIgnoreCase("dateOfAnnvrsry"))
					{
						parmname = "doa";
					}
					else if (parmname.equalsIgnoreCase("dateOfJoining"))
					{
						parmname = "doj";
					}
					else if (parmname.equalsIgnoreCase("orgAddress"))
					{
						parmname = "address";
					}
					else if (parmname != null && parmname.equalsIgnoreCase("mobile_no"))
					{
						contactMobNo = paramValue;
					}
					else if (parmname != null && parmname.equalsIgnoreCase("emp_id"))
					{
						contactId = paramValue;
						if (contactId != null && !contactId.equalsIgnoreCase(""))
						{
							boolean exists = new HelpdeskUniversalHelper().isExist("primary_contact", "emp_id", contactId, connectionSpace);
							if (exists)
							{
								addActionMessage("Contact Already Exists !!!");
								return SUCCESS;
							}
						}
					}
					if (!status)
					{
						if (parmname != null && !parmname.equalsIgnoreCase("status") && !parmname.equalsIgnoreCase("image") && !parmname.equalsIgnoreCase("document"))
						{
							ob = new InsertDataTable();
							ob.setColName(parmname);
							if (parmname.equalsIgnoreCase("doj") || parmname.equalsIgnoreCase("doa") || parmname.equalsIgnoreCase("dob"))
							{
								ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
							}
							else if (parmname.equalsIgnoreCase("orgName"))
							{
								if (paramValue != null && paramValue.equalsIgnoreCase(""))
								{
									ob.setDataName(orgName);
								}
								else
								{
									ob.setDataName(paramValue);
								}
							}
							else
							{
								ob.setDataName(paramValue);
							}
							insertData.add(ob);
						}
					}
				}
			
				if (userTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertData.add(ob);
				}
				if (dateTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("created_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);
				}
				if (timeTrue)
				{
					ob = new InsertDataTable();
					ob.setColName("created_time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
				}
				String renameFilePath = null;
				String docPath1 = null;

				if (getDocumentFileName() != null)
				{
					renameFilePath = new CreateFolderOs().createUserDir("Common_Data") + "//" + DateUtil.mergeDateTime() + getDocumentFileName();
					String storeFilePath = new CreateFolderOs().createUserDir("Common_Data") + "//" + getDocumentFileName();
					String str = renameFilePath.replace("//", "/");
					if (storeFilePath != null)
					{
						File theFile = new File(storeFilePath);
						File newFileName = new File(str);
						if (theFile != null)
						{
							try
							{
								FileUtils.copyFile(document, theFile);
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
								ob.setColName("document");
								ob.setDataName(docPath1);
								insertData.add(ob);

							}
						}
					}
				}

				if (getImage() != null)
				{
					String filePath = request.getSession().getServletContext().getRealPath("/images/contact/");
					String profilePicName = contactMobNo + "" + new KRActionHelper().getFileExtenstion(getImageContentType());
					File fileToCreate = new File(filePath, profilePicName);
					FileUtils.copyFile(this.image, fileToCreate);
					profilePicName = "images/contact/" + profilePicName;

					if (profilePicName != null)
					{
						ob = new InsertDataTable();
						ob.setColName("image");
						ob.setDataName(profilePicName);
						insertData.add(ob);
					}
				}
				int maxId = cbt.insertDataReturnId("primary_contact", insertData, connectionSpace);
				if (maxId > 0)
				{
					StringBuilder fieldsNames = new StringBuilder();
					StringBuilder fieldsValue = new StringBuilder();
					if (insertData != null && !insertData.isEmpty())
					{
						int i = 1;
						for (InsertDataTable h : insertData)
						{
							for (GridDataPropertyView gdp : statusColName)
							{
								if (h.getColName().equalsIgnoreCase(gdp.getColomnName()))
								{
									if (i < insertData.size())
									{
										fieldsNames.append(gdp.getHeadingName() + ", ");
										if (h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()) + ", ");
										}
										else
										{
											fieldsValue.append(h.getDataName() + ", ");
										}

									}
									else
									{
										fieldsNames.append(gdp.getHeadingName());
										if (h.getDataName().toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											fieldsValue.append(DateUtil.convertDateToIndianFormat(h.getDataName().toString()));
										}
										else
										{
											fieldsValue.append(h.getDataName());
										}
									}
								}
							}
							i++;
						}
					}
					String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
					new UserHistoryAction().userHistoryAdd(empIdofuser, "Add", "Admin", "Primary Contact", fieldsValue.toString(), fieldsNames.toString(), maxId, connectionSpace);
				}
				addActionMessage("Contact Added Successfully !!!");
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method addContact of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String getMappedContacts()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List data = cbt.executeAllSelectQuery(" select id,contact_name from contact_type where mapped_level='" + getRegLevel() + "'", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Object c : data)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getMappedContacts of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getMappedGroup()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List data = cbt.executeAllSelectQuery(" select id,contact_name from contact_type where mapped_level='" + getRegLevel() + "' AND status='Active' ORDER BY contact_name ASC", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Object c : data)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getMappedGroup of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getSubGroup()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getGroupId() != null)
				{
					List dataList = cbt.executeAllSelectQuery("select id,contact_subtype_name from contact_sub_type where contact_type_id='" + getGroupId() + "' ORDER BY contact_subtype_name ASC", connectionSpace);
					if (dataList != null & dataList.size() > 0)
					{
						for (Object c : dataList)
						{
							Object[] objVar = (Object[]) c;
							JSONObject listObject = new JSONObject();
							listObject.put("id", objVar[0].toString());
							listObject.put("name", objVar[1].toString());
							commonJSONArray.add(listObject);
						}
					}
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSubGroup of class " + getClass(), e);
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}

	public String getSubIndustry()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				if (getIndustryId() != null)
				{
					List dataList = cbt.executeAllSelectQuery("select id,subIndustry from subindustrydatalevel2 where industry='" + getIndustryId() + "'", connectionSpace);
					if (dataList != null & dataList.size() > 0)
					{
						for (Object c : dataList)
						{
							Object[] objVar = (Object[]) c;
							JSONObject listObject = new JSONObject();
							listObject.put("id", objVar[0].toString());
							listObject.put("name", objVar[1].toString());
							commonJSONArray.add(listObject);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSubIndustry of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}



	public String getContactName(SessionFactory connection)
	{
		String contactName = null;
		if (getGroupId() != null)
		{
			List data = cbt.executeAllSelectQuery("select contact_name from contact_type where id='" + getGroupId() + "'", connection);
			if (data != null && data.size() > 0 && data.get(0) != null)
			{
				contactName = data.get(0).toString();
			}
		}
		return contactName;
	}

	public String getContactForm()
	{
		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				if (getGroupId() != null)
				{
					String contactName = getContactName(connectionSpace);
					if (contactName != null && !contactName.equalsIgnoreCase(""))
					{
						if (contactName.equalsIgnoreCase("Employee"))
						{
							setContactDataFieldsToAdd("mapped_primary_contact_configuration", "primary_contact_configuration", contactName);
						}
						else if (contactName.equalsIgnoreCase("Associate"))
						{
							setContactDataFieldsToAdd("mapped_associate_basic_configuration", "associate_basic_configuration", contactName);
						}
						else if (contactName.equalsIgnoreCase("Client"))
						{
							setContactDataFieldsToAdd("mapped_client_contact_configuration", "client_contact_configuration", contactName);

							/*
							 * String
							 * subContact=getSubContactName(connectionSpace);
							 * System
							 * .out.println("Sub Contact Name is as "+subContact
							 * ); if(subContact!=null &&
							 * subContact.equalsIgnoreCase("Lead Management")) {
							 * setContactDataFieldsToAdd
							 * ("mapped_client_contact_configuration"
							 * ,"client_contact_configuration"); }
							 */
						}
						else
						{
							setContactDataFieldsToAdd("mapped_primary_contact_configuration", "primary_contact_configuration", contactName);
						}
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getContactForm of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setContactDataFieldsToAdd(String mappedTable, String configTable, String contactName)
	{
		try
		{
			List<GridDataPropertyView> gridFields = Configuration.getConfigurationData(mappedTable, accountID, connectionSpace, "", 0, "table_name", configTable);

			if (gridFields != null)
			{
				deptMap = new LinkedHashMap<Integer, String>();
				contactFormDDBox = new ArrayList<ConfigurationUtilBean>();
				contactTextBox = new ArrayList<ConfigurationUtilBean>();
				industryMap = new LinkedHashMap<Integer, String>();
				sourceMap = new LinkedHashMap<Integer, String>();
				contactFileBox = new ArrayList<ConfigurationUtilBean>();
				contactDateTimeBox = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("created_time") && !gdp.getColomnName().equalsIgnoreCase("user_account_id") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactTextBox.add(objdata);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactFileBox.add(objdata);

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("date"))
					{
						if (contactName != null && contactName.equalsIgnoreCase("Associate"))
						{
							if (!gdp.getColomnName().equalsIgnoreCase("doj"))
							{
								objdata.setKey(gdp.getColomnName());
								objdata.setValue(gdp.getHeadingName());
								objdata.setColType(gdp.getColType());
								objdata.setValidationType(gdp.getValidationType());
								if (gdp.getMandatroy() == null)
									objdata.setMandatory(false);
								else if (gdp.getMandatroy().equalsIgnoreCase("0"))
									objdata.setMandatory(false);
								else if (gdp.getMandatroy().equalsIgnoreCase("1"))
									objdata.setMandatory(true);
								contactDateTimeBox.add(objdata);
							}
						}
						else
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							contactDateTimeBox.add(objdata);

						}

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("clientName"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactFormDDBox.add(objdata);

						if (gdp.getColomnName().equalsIgnoreCase("industry"))
						{
							List data = cbt.executeAllSelectQuery("select id,industry from industrydatalevel1", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										industryMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}

							}
						}
						else if (gdp.getColomnName().equalsIgnoreCase("source"))
						{
							List data = cbt.executeAllSelectQuery("select id,sourceName from sourcemaster", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										sourceMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}

							}
						}
						else if (getRegLevel() != null)
						{
							List data = cbt.executeAllSelectQuery("select id,contact_subtype_name from contact_sub_type where contact_type_id='" + getRegLevel() + "'", connectionSpace);
							if (data != null && data.size() > 0)
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
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactDataFieldsToAdd of class " + getClass(), e);
		}
	}

	public void setContactAddPageDataFields()
	{
		try
		{
			groupMap = new HashMap<Integer, String>();
			contactTextBox = new ArrayList<ConfigurationUtilBean>();
			contactDateTimeBox = new ArrayList<ConfigurationUtilBean>();
			contactDD = new ArrayList<ConfigurationUtilBean>();
			contactFileBox = new ArrayList<ConfigurationUtilBean>();
			String userType = (String)session.get("userTpe");
        	officeMap=new LinkedHashMap<Integer, String>();
        	String query =null;
        	
        	if (userType!=null && userType.equalsIgnoreCase("H")) 
        	{
        		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
        		hodStatus=true;
        		query = "SELECT id,branch_name FROM branch_office WHERE head_id = '"+s[2]+"' ORDER BY branch_name";
			} 
        	else if(userType!=null &&( userType.equalsIgnoreCase("M")||userType.equalsIgnoreCase("o")))
        	{
        	    mgtStatus = true;
        	    hodStatus = true;
        		query = "SELECT id,country_name FROM country_office ORDER BY country_name";
        	}
        	else 
        	{
        		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
        		query = "SELECT id,contact_name FROM contact_type WHERE mapped_level = '"+s[1]+"' ORDER BY contact_name";
			}
			if (query!=null)
			{
				List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							officeMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
						}
					}
				}
			}
	  
			List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "common_contact_configuration");
			if (gridFields != null)
			{
				
				for (GridDataPropertyView gdp : gridFields)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactDD.add(objdata);
					}
					else if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time") && !gdp.getColomnName().equalsIgnoreCase("user_account_id") && !gdp.getColomnName().equalsIgnoreCase("status"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactTextBox.add(objdata);

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("F") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactFileBox.add(objdata);

					}
					else if (gdp.getColType().trim().equalsIgnoreCase("date"))
					{

						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
						contactDateTimeBox.add(objdata);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactAddPageDataFields of class " + getClass(), e);
		}
	}

	public void setContactUploadPageDataFields()
	{
		try
		{
			List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "common_contact_configuration");
			if (gridFields != null)
			{
				contactDD = new ArrayList<ConfigurationUtilBean>();
				for (GridDataPropertyView gdp : gridFields)
				{
					ConfigurationUtilBean objdata = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D"))
					{
						objdata.setKey(gdp.getColomnName());
						objdata.setValue(gdp.getHeadingName());
						objdata.setColType(gdp.getColType());
						objdata.setValidationType(gdp.getValidationType());
						if (gdp.getMandatroy() == null)
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if (gdp.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);

						if (gdp.getColomnName().equalsIgnoreCase("groupId"))
						{
							contactDD.add(objdata);
							groupMap = new HashMap<Integer, String>();
							List data = cbt.executeAllSelectQuery(" select id,groupName from groupinfo ", connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
									}
								}
							}
						}
						else if (gdp.getColomnName().equalsIgnoreCase("subGroupId"))
						{
							contactDD.add(objdata);
							subGroupMap = new HashMap<Integer, String>();
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactUploadPageDataFields of class " + getClass(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	public String beforeContactViewHeader()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				groupMap = new LinkedHashMap<Integer, String>();
				deptMap = new LinkedHashMap<Integer, String>();
				String userType = (String)session.get("userTpe");
            	String query =null;
            	
            	if (userType!=null && userType.equalsIgnoreCase("H")) 
            	{
            		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
            		hodStatus=true;
            		query = "SELECT id,branch_name FROM branch_office WHERE head_id = '"+s[2]+"' ORDER BY branch_name";
				} 
            	else if(userType!=null && (userType.equalsIgnoreCase("M")||userType.equalsIgnoreCase("o")))
            	{
            	    mgtStatus = true;
            	    hodStatus = true;
            		query = "SELECT id,country_name FROM country_office ORDER BY country_name";
            	}
            	else 
            	{
            		String s[]=CommonWork.fetchCommonDetails(userName,connectionSpace);
            		List data=cbt.executeAllSelectQuery("SELECT id ,contact_name FROM contact_type  WHERE status='Active' AND mapped_level="+s[1]+" ORDER BY contact_name ASC", connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}
						}
					}
				}
				if (query!=null)
				{
					List<?> dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
    				if (dataList != null && dataList.size() > 0)
    				{
    					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
    					{
    						Object[] object = (Object[]) iterator.next();
    						if (object[0] != null && object[1] != null)
    						{
    							deptMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
    						}
    					}
    				}
				}
				String typeUser = (String) session.get("userTpe");
				String userName = (String) session.get("uName");
				totalCount = new LinkedHashMap<String, String>();
				totalCount = getTotalCounters(typeUser, userName, connectionSpace);
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public Map<String, String> getTotalCounters(String userType, String username, SessionFactory connectionSpace)
	{
		Map<String, String> counter = new LinkedHashMap<String, String>();

		StringBuilder builder = new StringBuilder(" Select count(*),status from primary_contact  AS contct where contct.id!=0  ");
		
		if (userType != null && userType.equalsIgnoreCase("N"))
		{
			builder.append("  and contct.user_name='" + userName + "'  ");
		}
		else if (userType != null && userType.equalsIgnoreCase("H"))
		{
			String deptId[] = new ComplianceUniversalAction().getEmpDataByUserName(userName);
			if (deptId != null && !deptId.toString().equalsIgnoreCase(""))
			{
				builder.append(" and  contct.sub_type_id='" + deptId[3] + "'  ");
			}
		}
		builder.append(" GROUP BY status  ");
		// System.out.println("builder.toString()  "+builder.toString());
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null && object[1] != null)
				{
					total = total + Integer.parseInt(object[0].toString());
					if (object[1].toString().equalsIgnoreCase("0"))
					{
						counter.put("Active", object[0].toString());
					}
					else
					{
						counter.put("Inactive", object[0].toString());
					}
				}
			}
			counter.put("Total Records", String.valueOf(total));
		}
		return counter;
	}
	public String beforeContactView()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				setContactViewProp();
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeContactView of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public void setContactViewProp()
	{
		try
		{
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> emp = null;
			List<GridDataPropertyView> returnResult = null;
			List<GridDataPropertyView> lead = null;
			if (searchFields != null && searchFields.equalsIgnoreCase("contact_type"))
			{
				String groupName = fetchGroupNameById(SearchValue);
				if (groupName != null && !groupName.equalsIgnoreCase(""))
				{
					returnResult = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "common_contact_configuration");
					emp = Configuration.getConfigurationData("mapped_primary_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "primary_contact_configuration");
					if (groupName.equalsIgnoreCase("Client"))
					{
						lead = Configuration.getConfigurationData("mapped_client_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
					}
				}
			}
			else
			{
				emp = Configuration.getConfigurationData("mapped_primary_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "primary_contact_configuration");
				returnResult = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "common_contact_configuration");
			//	lead = Configuration.getConfigurationData("mapped_client_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "client_contact_configuration");
			}
			if (emp != null)
			{
				for (GridDataPropertyView gdp : emp)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setFormatter(gdp.getFormatter());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
				}
			}
			if (returnResult != null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					if(gdp.getColomnName().equalsIgnoreCase("sub_type_id"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName("contact_name");
						gpv.setHeadingName("Contact Type");
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						gpv.setFormatter(gdp.getFormatter());
						masterViewProp.add(gpv);
						
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						gpv.setFormatter(gdp.getFormatter());
						masterViewProp.add(gpv);
					}
					else
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						gpv.setFormatter(gdp.getFormatter());
						masterViewProp.add(gpv);
					}
				}
			}
			if (lead != null)
			{
				for (GridDataPropertyView gdp : lead)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("orgAddress") && !gdp.getColomnName().equalsIgnoreCase("contactName") && !gdp.getColomnName().equalsIgnoreCase("mobileNo") && !gdp.getColomnName().equalsIgnoreCase("contactEmailId") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("dateOfBirth") && !gdp.getColomnName().equalsIgnoreCase("dateOfJoining") && !gdp.getColomnName().equalsIgnoreCase("dateOfAnnvrsry"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable("false");
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setFormatter(gdp.getFormatter());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method setContactViewProp of class " + getClass(), e);
		}
	}

	private String fetchGroupNameById(String searchValue2)
	{
		String result = null;
		try
		{
			List data = new createTable().executeAllSelectQuery("SELECT contact_name FROM contact_type WHERE id=" + searchValue2, connectionSpace);
			if (data != null && !data.isEmpty())
			{
				result = data.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public String getContBySubGroupId4User()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				String query = "SELECT id,emp_name FROM primary_contact WHERE sub_type_id=" + subGroupId + " AND user_account_id IS NULL ORDER BY emp_name";
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null & dataList.size() > 0)
				{
					for (Object c : dataList)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getContBySubGroupId4User of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	public String getEmpDetails()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				checkEmp = new LinkedHashMap<String, String>();
				String query = "SELECT emp_name,mobile_no,email_id FROM primary_contact WHERE id=" + empId;
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					Object[] objArr = (Object[]) dataList.get(0);

					checkEmp.put("name", CUA.getValueWithNullCheck(objArr[0]));
					checkEmp.put("mobileNo", CUA.getValueWithNullCheck(objArr[1]));
					checkEmp.put("email", CUA.getValueWithNullCheck(objArr[2]));
				}
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

	@SuppressWarnings("unchecked")
	public String beforeUpload()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{

				List<GridDataPropertyView> gridFields = Configuration.getConfigurationData("mapped_common_contact_configuration", accountID, connectionSpace, "", 0, "table_name", "common_contact_configuration");
				if (gridFields != null)
				{
					contactDD = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : gridFields)
					{
						ConfigurationUtilBean objdata = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							objdata.setKey(gdp.getColomnName());
							objdata.setValue(gdp.getHeadingName());
							objdata.setColType(gdp.getColType());
							objdata.setValidationType(gdp.getValidationType());
							if (gdp.getMandatroy() == null)
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if (gdp.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
							contactDD.add(objdata);
						}
					}
				}

				officeMap = new LinkedHashMap<Integer, String>();
				groupMap = new LinkedHashMap<Integer, String>();
				String user = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				query.append("SELECT emp.id,emp.sub_type_id FROM primary_contact AS emp ");
				query.append(" INNER JOIN useraccount AS uac ON uac.id=emp.user_account_id ");
				query.append(" WHERE uac.userID='" + user + "'");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList == null || dataList.size() == 0)
				{
					List data = cbt.executeAllSelectQuery(" select levelName from mapped_orgleinfo_level ", connectionSpace);

					if (data != null && data.size() > 0 && data.get(0) != null)
					{
						String arr[] = data.get(0).toString().split("#");
						for (int i = 0; i < arr.length; i++)
						{
							officeMap.put(i + 1, arr[i].toString());
						}
					}
					statusFlag = true;

				}
				else
				{
					Object[] obj = (Object[]) dataList.get(0);
					String regLevel = obj[1].toString();
					List groupDataList = cbt.executeAllSelectQuery("SELECT id,contact_name FROM contact_type WHERE mapped_level=" + regLevel + " AND status='Active' ORDER BY contact_name", connectionSpace);
					if (groupDataList != null && groupDataList.size() > 0)
					{
						for (Iterator iterator = groupDataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								groupMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
							}

						}
					}

				}
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

	@SuppressWarnings("unchecked")
	public String downloadFormat()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				String orgDetail = (String) session.get("orgDetail");
				String[] orgData = null;
				String orgName = "";
				if (orgDetail != null && !orgDetail.equals(""))
				{
					orgData = orgDetail.split("#");
					orgName = orgData[0];
				}
				List titleList = new ArrayList();

				if (groupName != null && groupName.equalsIgnoreCase("Employee"))
				{
					titleList.add("Emp Id");
					titleList.add("Emp Name");
					titleList.add("Communication Name");
					titleList.add("Mobile No");
					titleList.add("Email Id");
					titleList.add("Dept Name");
					titleList.add("Designation");
					titleList.add("Address");
					titleList.add("City");
					titleList.add("DOB");
					titleList.add("DOA");
					titleList.add("DOJ");
					titleList.add("Alternate Mobile No");
					titleList.add("Alternate Email Id");
				}
				else if (groupName != null)
				{
					titleList.add("Organisation Name");
					titleList.add("Address");
					titleList.add("Phone No.");
					titleList.add("Website");
					titleList.add("Location");
					titleList.add("Industry");
					titleList.add("Sub-Industry");
					titleList.add("Source");
					titleList.add("Official Email ID");
					titleList.add("Star");
					titleList.add("Potential / Size");
					titleList.add("Input");
					titleList.add("Contact Person");
					titleList.add("Communication Name");
					titleList.add("Influence");
					titleList.add("Mobile No");
					titleList.add("Email Id");
					titleList.add("Dept Name");
					titleList.add("Designation");
					titleList.add("Alternate Mobile No");
					titleList.add("Alternate Email Id");
					titleList.add("DOB");
					titleList.add("DOA");
					titleList.add("Acc Mgr No");
				}

				if (titleList != null && titleList.size() > 0)
				{
					excelFileName = new ComplianceExcelDownload().writeDataInExcel(null, titleList, null, "Excel Format for " + groupName, orgName, false, connectionSpace);
					if (excelFileName != null)
					{
						excelStream = new FileInputStream(excelFileName);
					}
					return SUCCESS;
				}
				else
				{
					return ERROR;
				}
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

	@SuppressWarnings("unchecked")
	public String uploadContact()
	{
		int rowNo = 0;
		String result = null;
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				if (groupId != null)
				{
					StringBuilder getRegLevelQuery = new StringBuilder();
					getRegLevelQuery.append("SELECT regLevel FROM groupinfo WHERE id=" + groupId);
					List dataList = cbt.executeAllSelectQuery(getRegLevelQuery.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						regLevel = dataList.get(0).toString();
					}
				}

				Map<String, String> empDataMap = null;
				Map<String, String> leadDataMap = null;
				Map<String, String> leadContDataMap = null;
				Map<String, String> clientDataMap = null;
				Map<String, String> clientContDataMap = null;
				Map<String, String> associateDataMap = null;
				Map<String, String> associateContDataMap = null;
				int maxId = 0;
				int leadMaxId = 0;
				int deptMaxId = 0;
				int clientMaxId = 0;
				int associateMaxId = 0;
				Map<String, String> columnDataMap = null;
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String contactId = null;
				if (groupName != null && !groupName.equalsIgnoreCase("Employee"))
				{
					if (groupName.equalsIgnoreCase("Lead"))
					{
						contactId = new ComplianceUniversalAction().getEmpDetailsByUserName("WFPM", userName)[0];
					}

					InputStream inputStream = new FileInputStream(contactExcel);
					if (contactExcelFileName.contains(".xlsx"))
					{
						GenericReadExcel7 GRBE = new GenericReadExcel7();
						XSSFSheet excelSheet = GRBE.readExcel(inputStream);
						rowNo = excelSheet.getPhysicalNumberOfRows() - 3;
						if (rowNo < 1)
						{
							addActionMessage("Excel Sheet is Blank ");
							result = "error";
						}
						else
						{

							for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								XSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									if (rowIndex >= 3)
									{
										empDataMap = new LinkedHashMap<String, String>();
										leadDataMap = new LinkedHashMap<String, String>();
										columnDataMap = new LinkedHashMap<String, String>();
										leadContDataMap = new LinkedHashMap<String, String>();
										clientDataMap = new LinkedHashMap<String, String>();
										clientContDataMap = new LinkedHashMap<String, String>();
										associateDataMap = new LinkedHashMap<String, String>();
										associateContDataMap = new LinkedHashMap<String, String>();

										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											switch (cellIndex)
											{
											case 0:
											{
												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													columnDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													leadMaxId = getMaxId("leadgeneration", columnDataMap);
													if (leadMaxId == 0)
													{
														leadDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													columnDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													associateMaxId = getMaxId("associate_basic_data", columnDataMap);
													if (associateMaxId == 0)
													{
														associateDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													columnDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													clientMaxId = getMaxId("client_basic_data", columnDataMap);
													if (clientMaxId == 0)
													{
														clientDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													}
												}
												break;
											}
											case 1:
											{
												leadDataMap.put("leadAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 2:
											{
												leadDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}

											case 3:
											{
												leadDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 4:
											{
												columnDataMap.clear();
												columnDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("location", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("location", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("location", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("location", String.valueOf(maxId));
													}
												}
												break;
											}
											case 5:
											{
												columnDataMap.clear();
												columnDataMap.put("industry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("industrydatalevel1", columnDataMap);
													if (maxId > 0)
													{
														leadDataMap.put("industry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("industrydatalevel1", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("industry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("industrydatalevel1", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("industry", String.valueOf(maxId));
													}
												}

												break;
											}
											case 6:
											{
												columnDataMap.clear();
												columnDataMap.put("industry", String.valueOf(maxId));
												columnDataMap.put("subIndustry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("subindustrydatalevel2", columnDataMap);
													if (maxId > 0)
													{
														leadDataMap.put("subIndustry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("subindustrydatalevel2", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("subIndustry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("subindustrydatalevel2", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("subIndustry", String.valueOf(maxId));
													}
												}

												break;
											}
											case 7:
											{
												columnDataMap.clear();
												columnDataMap.put("sourceName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("sourcemaster", columnDataMap);
													if (maxId > 0)
													{
														leadDataMap.put("sourceName", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("sourcemaster", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("sourceName", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("sourcemaster", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("sourceMaster", String.valueOf(maxId));
													}
												}

												break;
											}
											case 8:
											{
												leadDataMap.put("email", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("associateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("companyEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 9:
											{
												leadDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("associateRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 10:
											{
												leadDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("size", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 11:
											{
												leadDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 12:
											{
												empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 13:
											{
												empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("communicationName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 14:
											{
												leadContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 15:
											{
												empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("contactNum", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 16:
											{
												empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("emailOfficialContact", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 17:
											{
												columnDataMap.clear();
												columnDataMap.put("deptName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("department", columnDataMap);
													if (maxId > 0)
													{
														leadContDataMap.put("department", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("department", columnDataMap);
													if (maxId > 0)
													{
														associateContDataMap.put("department", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("department", columnDataMap);
													if (maxId > 0)
													{
														clientContDataMap.put("department", String.valueOf(maxId));
													}
												}
											}
											case 18:
											{
												empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 19:
											{
												leadContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 20:
											{
												leadContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 21:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
												leadContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
												associateContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
												clientContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 22:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
												leadContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
												associateContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
												clientContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 23:
											{
												if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													associateDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
													associateContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{

													clientDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
													clientContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));

												}
											}
											}
										}
									}
								}
							}
							addActionMessage("Excel Sheet Uploaded With " + rowNo + " Rows Successfully");
							result = "success";
						}
					}
					else
					{
						GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						rowNo = excelSheet.getPhysicalNumberOfRows() - 3;
						if (rowNo < 1)
						{
							addActionMessage("Excel Sheet is Blank");
							result = "error";
						}
						else
						{
							for (int rowIndex = 3; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								HSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									if (rowIndex >= 3)
									{
										empDataMap = new LinkedHashMap<String, String>();
										leadDataMap = new LinkedHashMap<String, String>();
										columnDataMap = new LinkedHashMap<String, String>();
										leadContDataMap = new LinkedHashMap<String, String>();
										clientDataMap = new LinkedHashMap<String, String>();
										clientContDataMap = new LinkedHashMap<String, String>();
										associateDataMap = new LinkedHashMap<String, String>();
										associateContDataMap = new LinkedHashMap<String, String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											switch (cellIndex)
											{
											case 0:
											{
												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													columnDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													leadMaxId = getMaxId("leadgeneration", columnDataMap);
													if (leadMaxId == 0)
													{
														leadDataMap.put("leadName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													columnDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													associateMaxId = getMaxId("associate_basic_data", columnDataMap);
													if (associateMaxId == 0)
													{
														associateDataMap.put("associateName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													columnDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													clientMaxId = getMaxId("client_basic_data", columnDataMap);
													if (clientMaxId == 0)
													{
														clientDataMap.put("clientName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
													}
												}
												break;
											}
											case 1:
											{
												leadDataMap.put("leadAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 2:
											{
												leadDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("phoneNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}

											case 3:
											{
												leadDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("webAddress", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 4:
											{
												columnDataMap.clear();
												columnDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("location", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("location", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("location", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("location", String.valueOf(maxId));
													}
												}
												break;
											}
											case 5:
											{
												columnDataMap.clear();
												columnDataMap.put("industry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("industrydatalevel1", columnDataMap);
													if (maxId > 0)
													{
														leadDataMap.put("industry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("industrydatalevel1", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("industry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("industrydatalevel1", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("industry", String.valueOf(maxId));
													}
												}

												break;
											}
											case 6:
											{
												columnDataMap.clear();
												columnDataMap.put("industry", String.valueOf(maxId));
												columnDataMap.put("subIndustry", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("subindustrydatalevel2", columnDataMap);
													if (maxId > 0)
													{
														leadDataMap.put("subIndustry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("subindustrydatalevel2", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("subIndustry", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("subindustrydatalevel2", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("subIndustry", String.valueOf(maxId));
													}
												}

												break;
											}
											case 7:
											{
												columnDataMap.clear();
												columnDataMap.put("sourceName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("sourcemaster", columnDataMap);
													if (maxId > 0)
													{
														leadDataMap.put("sourceName", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("sourcemaster", columnDataMap);
													if (maxId > 0)
													{
														associateDataMap.put("sourceName", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("sourcemaster", columnDataMap);
													if (maxId > 0)
													{
														clientDataMap.put("sourceMaster", String.valueOf(maxId));
													}
												}

												break;
											}
											case 8:
											{
												leadDataMap.put("email", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("associateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("companyEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 9:
											{
												leadDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("associateRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("starRating", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 10:
											{
												leadDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("size", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("comment", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 11:
											{
												leadDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientDataMap.put("input", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 12:
											{
												empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("name", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("personName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 13:
											{
												empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("communicationName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 14:
											{
												leadContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("degreeOfInfluence", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 15:
											{
												empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("contactNum", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("contactNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 16:
											{
												empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("emailOfficialContact", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("emailOfficial", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 17:
											{
												columnDataMap.clear();
												columnDataMap.put("deptName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));

												if (groupName != null && groupName.equalsIgnoreCase("Lead"))
												{
													maxId = getMaxId("department", columnDataMap);
													if (maxId > 0)
													{
														leadContDataMap.put("department", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													maxId = getMaxId("department", columnDataMap);
													if (maxId > 0)
													{
														associateContDataMap.put("department", String.valueOf(maxId));
													}
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{
													maxId = getMaxId("department", columnDataMap);
													if (maxId > 0)
													{
														clientContDataMap.put("department", String.valueOf(maxId));
													}
												}
											}
											case 18:
											{
												empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												leadContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 19:
											{
												leadContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("alternateContNo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 20:
											{
												leadContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												associateContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												clientContDataMap.put("alternateEmail", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 21:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
												leadContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
												associateContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
												clientContDataMap.put("birthday", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 22:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
												leadContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
												associateContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
												clientContDataMap.put("anniversary", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 23:
											{
												if (groupName != null && groupName.equalsIgnoreCase("Associate"))
												{
													associateDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
													associateContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
												}
												else if (groupName != null && groupName.equalsIgnoreCase("Client"))
												{

													clientDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));
													clientContDataMap.put("userName", getContIdByMobileNo(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)), "WFPM"));

												}
											}
											}
										}
									}
									if (empDataMap != null && empDataMap.size() > 0)
									{
										String empName = null, mobileNo = null;
										List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
										for (Map.Entry entry : empDataMap.entrySet())
										{

											InsertDataTable object = new InsertDataTable();
											object.setColName(entry.getKey().toString());
											object.setDataName(entry.getValue().toString());
											insertData.add(object);
											if (entry.getKey().toString().equalsIgnoreCase("empName"))
												empName = entry.getValue().toString();

											if (entry.getKey().toString().equalsIgnoreCase("mobOne"))
												mobileNo = entry.getValue().toString();
										}

										InsertDataTable object = new InsertDataTable();
										object.setColName("regLevel");
										object.setDataName(regLevel);
										insertData.add(object);

										object = new InsertDataTable();
										object.setColName("groupId");
										object.setDataName(groupId);
										insertData.add(object);

										object = new InsertDataTable();
										object.setColName("flag");
										object.setDataName("0");
										insertData.add(object);

										object = new InsertDataTable();
										object.setColName("deptname");
										object.setDataName(subGroupId);
										insertData.add(object);
										if (empName != null && mobileNo != null && !empName.equalsIgnoreCase("NA") && !mobileNo.equalsIgnoreCase("NA") && groupName != null)
										{
											cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
										}
									}
									if (groupName != null && groupName.equalsIgnoreCase("Lead"))
									{
										if (leadMaxId == 0 && leadDataMap != null && leadDataMap.size() > 0)
										{
											InsertDataTable object = null;
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											for (Map.Entry entry : leadDataMap.entrySet())
											{
												object = new InsertDataTable();
												object.setColName(entry.getKey().toString());
												object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
												insertData.add(object);
											}

											object = new InsertDataTable();
											object.setColName("userName");
											object.setDataName(contactId);
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("date");
											object.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("time");
											object.setDataName(DateUtil.getCurrentTime());
											insertData.add(object);

											leadMaxId = cbt.insertDataReturnId("leadgeneration", insertData, connectionSpace);
										}
										if (leadMaxId > 0 && leadContDataMap != null && leadContDataMap.size() > 0)
										{
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											InsertDataTable object = null;
											for (Map.Entry entry : leadContDataMap.entrySet())
											{
												object = new InsertDataTable();
												object.setColName(entry.getKey().toString());
												object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
												insertData.add(object);
											}

											object = new InsertDataTable();
											object.setColName("userName");
											object.setDataName(contactId);
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("leadName");
											object.setDataName(leadMaxId);
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("date");
											object.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("time");
											object.setDataName(DateUtil.getCurrentTime());
											insertData.add(object);

											cbt.insertIntoTable("lead_contact_data", insertData, connectionSpace);
										}
									}
									else if (groupName != null && groupName.equalsIgnoreCase("Associate"))
									{
										if (associateMaxId == 0 && associateDataMap != null && associateDataMap.size() > 0)
										{
											InsertDataTable object = null;
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											for (Map.Entry entry : associateDataMap.entrySet())
											{
												object = new InsertDataTable();
												object.setColName(entry.getKey().toString());
												object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
												insertData.add(object);
											}

											object = new InsertDataTable();
											object.setColName("date");
											object.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("time");
											object.setDataName(DateUtil.getCurrentTime());
											insertData.add(object);

											associateMaxId = cbt.insertDataReturnId("associate_basic_data", insertData, connectionSpace);
										}
										if (associateMaxId > 0 && associateContDataMap != null && associateContDataMap.size() > 0)
										{
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											InsertDataTable object = null;
											for (Map.Entry entry : associateContDataMap.entrySet())
											{
												object = new InsertDataTable();
												object.setColName(entry.getKey().toString());
												object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
												insertData.add(object);
											}

											object = new InsertDataTable();
											object.setColName("associateName");
											object.setDataName(associateMaxId);
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("date");
											object.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("time");
											object.setDataName(DateUtil.getCurrentTime());
											insertData.add(object);

											cbt.insertIntoTable("associate_contact_data", insertData, connectionSpace);
										}

									}
									else if (groupName != null && groupName.equalsIgnoreCase("Client"))
									{

										if (clientMaxId == 0 && clientDataMap != null && clientDataMap.size() > 0)
										{
											InsertDataTable object = null;
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											for (Map.Entry entry : clientDataMap.entrySet())
											{
												object = new InsertDataTable();
												object.setColName(entry.getKey().toString());
												object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
												insertData.add(object);
											}

											object = new InsertDataTable();
											object.setColName("date");
											object.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("time");
											object.setDataName(DateUtil.getCurrentTime());
											insertData.add(object);

											clientMaxId = cbt.insertDataReturnId("client_basic_data", insertData, connectionSpace);
										}
										if (clientMaxId > 0 && clientContDataMap != null && clientContDataMap.size() > 0)
										{
											List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
											InsertDataTable object = null;
											for (Map.Entry entry : clientContDataMap.entrySet())
											{

												object = new InsertDataTable();
												object.setColName(entry.getKey().toString());
												object.setDataName(CommonHelper.getFieldValue(entry.getValue()));
												insertData.add(object);
											}

											object = new InsertDataTable();
											object.setColName("clientName");
											object.setDataName(clientMaxId);
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("date");
											object.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData.add(object);

											object = new InsertDataTable();
											object.setColName("time");
											object.setDataName(DateUtil.getCurrentTime());
											insertData.add(object);

											cbt.insertIntoTable("client_contact_data", insertData, connectionSpace);
										}

									}
								}
							}
							addActionMessage("Excel Uploaded with " + rowNo + " Rows Successfully");
							result = "success";
						}

					}
				}
				else if (groupName != null && groupName.equalsIgnoreCase("Employee"))
				{
					InputStream inputStream = new FileInputStream(contactExcel);
					if (contactExcelFileName.contains(".xlsx"))
					{
						GenericReadExcel7 GRBE = new GenericReadExcel7();
						XSSFSheet excelSheet = GRBE.readExcel(inputStream);
						rowNo = excelSheet.getPhysicalNumberOfRows() - 3;
						if (rowNo < 1)
						{
							addActionMessage("Excel Sheet is Blank");
							result = "error";
						}
						else
						{
							for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								XSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									if (rowIndex >= 3)
									{
										empDataMap = new LinkedHashMap<String, String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											switch (cellIndex)
											{
											case 0:
											{
												empDataMap.put("empId", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 1:
											{
												empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 2:
											{
												empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 3:
											{
												empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 4:
											{
												empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 5:
											{
												deptMaxId = getDeptIdByGroupId(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												if (deptMaxId > 0)
												{
													empDataMap.put("deptname", String.valueOf(deptMaxId));
												}
											}
											case 6:
											{
												empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 7:
											{
												empDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 8:
											{
												empDataMap.put("city", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 9:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 10:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 11:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("doj", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 12:
											{
												empDataMap.put("mobTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 13:
											{
												empDataMap.put("emailIdTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											}
										}

									}
									if (empDataMap != null && empDataMap.size() > 0)
									{
										String empName = null, mobileNo = null;
										List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
										for (Map.Entry entry : empDataMap.entrySet())
										{

											InsertDataTable object = new InsertDataTable();
											object.setColName(entry.getKey().toString());
											object.setDataName(entry.getValue().toString());
											insertData.add(object);
											if (entry.getKey().toString().equalsIgnoreCase("empName"))
												empName = entry.getValue().toString();

											if (entry.getKey().toString().equalsIgnoreCase("mobOne"))
												mobileNo = entry.getValue().toString();
										}

										InsertDataTable object = new InsertDataTable();
										object.setColName("regLevel");
										object.setDataName(regLevel);
										insertData.add(object);

										object = new InsertDataTable();
										object.setColName("groupId");
										object.setDataName(groupId);
										insertData.add(object);

										if (empName != null && mobileNo != null && !empName.equalsIgnoreCase("NA") && !mobileNo.equalsIgnoreCase("NA") && groupName != null)
										{
											cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
										}
									}

								}
							}
							addActionMessage("Excel Uploaded with " + rowNo + " Rows Successfully");
							result = "success";
						}
					}
					else
					{
						GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						rowNo = excelSheet.getPhysicalNumberOfRows() - 3;
						if (rowNo < 1)
						{
							addActionMessage("Excel Sheet is Blank");
							result = "error";
						}
						else
						{
							for (int rowIndex = 3; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
							{
								HSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									if (rowIndex >= 3)
									{
										empDataMap = new LinkedHashMap<String, String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											switch (cellIndex)
											{
											case 0:
											{
												empDataMap.put("empId", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 1:
											{
												empDataMap.put("empName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 2:
											{
												empDataMap.put("comName", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 3:
											{
												empDataMap.put("mobOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 4:
											{
												empDataMap.put("emailIdOne", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 5:
											{
												deptMaxId = getDeptIdByGroupId(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												if (deptMaxId > 0)
												{
													empDataMap.put("deptname", String.valueOf(deptMaxId));
												}
											}
											case 6:
											{
												empDataMap.put("designation", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 7:
											{
												empDataMap.put("address", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 8:
											{
												empDataMap.put("city", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 9:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("dob", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 10:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("doa", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 11:
											{
												String newDate = changeExcelDateFormat(CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												empDataMap.put("doj", CUA.getValueWithNullCheck(newDate));
												break;
											}
											case 12:
											{
												empDataMap.put("mobTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											case 13:
											{
												empDataMap.put("emailIdTwo", CUA.getValueWithNullCheck(GRBE.readExcelSheet(row, cellIndex)));
												break;
											}
											}
										}
									}
									if (empDataMap != null && empDataMap.size() > 0)
									{
										String empName = null, mobileNo = null;
										List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
										for (Map.Entry entry : empDataMap.entrySet())
										{

											InsertDataTable object = new InsertDataTable();
											object.setColName(entry.getKey().toString());
											object.setDataName(entry.getValue().toString());
											insertData.add(object);
											if (entry.getKey().toString().equalsIgnoreCase("empName"))
												empName = entry.getValue().toString();

											if (entry.getKey().toString().equalsIgnoreCase("mobOne"))
												mobileNo = entry.getValue().toString();
										}

										InsertDataTable object = new InsertDataTable();
										object.setColName("regLevel");
										object.setDataName(regLevel);
										insertData.add(object);

										object = new InsertDataTable();
										object.setColName("groupId");
										object.setDataName(groupId);
										insertData.add(object);

										if (empName != null && mobileNo != null && !empName.equalsIgnoreCase("NA") && !mobileNo.equalsIgnoreCase("NA") && groupName != null)
										{

											cbt.insertIntoTable("employee_basic", insertData, connectionSpace);
										}
									}
								}
							}
							addActionMessage("Excel Uploaded with " + rowNo + " Rows Successfully");
							result = "success";
						}
					}

				}

				return result;
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

	public int getDeptIdByGroupId(String deptName)
	{
		int id = 0;
		StringBuilder str = new StringBuilder();
		str.append("SELECT dept.id FROM contact_sub_type AS dept ");
		str.append(" INNER JOIN contact_type AS grinfo ON grinfo.id=dept.contact_type_id ");
		str.append(" WHERE grinfo.contact_name='Employee' AND dept.contact_subtype_name='" + deptName + "'");
		List dataList = cbt.executeAllSelectQuery(str.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			id = Integer.valueOf(dataList.get(0).toString());
		}
		return id;
	}

	public int getMaxId(String tableName, Map<String, String> condtnValue)
	{
		int id = 0;
		StringBuilder str = new StringBuilder();
		str.append("SELECT id FROM " + tableName + " WHERE ");
		int i = 0;
		for (Map.Entry entry : condtnValue.entrySet())
		{
			if (i == 0)
			{
				str.append(entry.getKey().toString() + "='" + entry.getValue().toString() + "' ");
			}
			else
			{
				str.append(" AND " + entry.getKey().toString() + "='" + entry.getValue().toString() + "' ");
			}
			i++;
		}
		List dataList = cbt.executeAllSelectQuery(str.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			id = Integer.valueOf(dataList.get(0).toString());
		}
		return id;
	}

	public String changeExcelDateFormat(String date)
	{
		String newDate = null;

		// for Date Check With (YYYY-DD-MM OR YY-D-M)
		if (date.matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
		{
			newDate = DateUtil.convertHyphenDateToUSFormat(date);
		}
		// for Date Check With (DD/MM/YYYY OR D/M/YY)
		else if (date.matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
		{
			newDate = DateUtil.convertSlashDateToUSFormat(date);
		}
		return newDate;
	}

	public String getContBySubGroupId4UserReset()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
				String query = "SELECT id,emp_name FROM primary_contact WHERE sub_type_id=" + subGroupId + " AND user_account_id is not null ORDER BY emp_name";

				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null & dataList.size() > 0)
				{
					for (Object c : dataList)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[1].toString());
						commonJSONArray.add(listObject);
					}
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getContBySubGroupId4User of class " + getClass(), e);
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	public String getContIdByMobileNo(String mobileNo, String moduleName)
	{
		String contId = null;
		try
		{
			StringBuilder str = new StringBuilder();
			str.append("select contact.id from compliance_contacts as contact ");
			str.append(" inner join  employee_basic as emp on emp.id=contact.emp_id AND emp.sub_type_id=contact.forDept_id ");
			str.append(" where contact.moduleName='" + moduleName + "' AND emp.mobile_no='" + mobileNo + "'");

			List dataList = cbt.executeAllSelectQuery(str.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				contId = dataList.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return contId;
	}

	public String editCommonContact()
	{
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag)
		{
			try
			{
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
					cbt.updateTableColomn("employee_basic", wherClause, condtnBlock, connectionSpace);
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
					StringBuilder query = new StringBuilder("UPDATE employee_basic SET flag='1' WHERE id IN(" + condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
				}
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

	public String getDeptBySubGroup()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				jssonArr = new JSONArray();
				JSONObject ob;
				StringBuilder query = new StringBuilder();
				query.append("SELECT dept.id,dept.contact_subtype_name FROM contact_sub_type AS dept ");
				query.append(" INNER JOIN contact_type AS gi ON dept.contact_type_id=gi.id ");
				query.append(" WHERE gi.id=" + selectedId + " GROUP BY dept.contact_subtype_name ");
				List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						ob = new JSONObject();
						Object[] object = (Object[]) iterator.next();
						ob.put("id", object[0].toString());
						ob.put("name", object[1].toString());
						jssonArr.add(ob);
					}
				}

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

	public JSONArray getJssonArr()
	{
		return jssonArr;
	}

	public void setJssonArr(JSONArray jssonArr)
	{
		this.jssonArr = jssonArr;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<ConfigurationUtilBean> getContactTextBox()
	{
		return contactTextBox;
	}

	public void setContactTextBox(List<ConfigurationUtilBean> contactTextBox)
	{
		this.contactTextBox = contactTextBox;
	}

	public Map<Integer, String> getGroupMap()
	{
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap)
	{
		this.groupMap = groupMap;
	}

	public Map<Integer, String> getSubGroupMap()
	{
		return subGroupMap;
	}

	public void setSubGroupMap(Map<Integer, String> subGroupMap)
	{
		this.subGroupMap = subGroupMap;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
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

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public List<ConfigurationUtilBean> getContactDateTimeBox()
	{
		return contactDateTimeBox;
	}

	public void setContactDateTimeBox(List<ConfigurationUtilBean> contactDateTimeBox)
	{
		this.contactDateTimeBox = contactDateTimeBox;
	}

	public List<ConfigurationUtilBean> getContactDD()
	{
		return contactDD;
	}

	public void setContactDD(List<ConfigurationUtilBean> contactDD)
	{
		this.contactDD = contactDD;
	}

	public Map<Integer, String> getOfficeMap()
	{
		return officeMap;
	}

	public void setOfficeMap(Map<Integer, String> officeMap)
	{
		this.officeMap = officeMap;
	}

	public Map<Integer, String> getDeptMap()
	{
		return deptMap;
	}

	public void setDeptMap(Map<Integer, String> deptMap)
	{
		this.deptMap = deptMap;
	}

	public Map<Integer, String> getDesgMap()
	{
		return desgMap;
	}

	public void setDesgMap(Map<Integer, String> desgMap)
	{
		this.desgMap = desgMap;
	}

	public String getSubGroupId()
	{
		return subGroupId;
	}

	public void setSubGroupId(String subGroupId)
	{
		this.subGroupId = subGroupId;
	}

	public List<ConfigurationUtilBean> getContactFormDDBox()
	{
		return contactFormDDBox;
	}

	public void setContactFormDDBox(List<ConfigurationUtilBean> contactFormDDBox)
	{
		this.contactFormDDBox = contactFormDDBox;
	}

	public List<ConfigurationUtilBean> getContactFileBox()
	{
		return contactFileBox;
	}

	public void setContactFileBox(List<ConfigurationUtilBean> contactFileBox)
	{
		this.contactFileBox = contactFileBox;
	}

	public Map<Integer, String> getIndustryMap()
	{
		return industryMap;
	}

	public void setIndustryMap(Map<Integer, String> industryMap)
	{
		this.industryMap = industryMap;
	}

	public String getIndustryId()
	{
		return industryId;
	}

	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	public String getRegLevel()
	{
		return regLevel;
	}

	public void setRegLevel(String regLevel)
	{
		this.regLevel = regLevel;
	}

	public Map<String, String> getCheckEmp()
	{
		return checkEmp;
	}

	public void setCheckEmp(Map<String, String> checkEmp)
	{
		this.checkEmp = checkEmp;
	}

	public String getMobile_no()
	{
		return mobile_no;
	}

	public void setMobile_no(String mobile_no)
	{
		this.mobile_no = mobile_no;
	}

	

	public String getEmpId()
	{
		return empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	/*
	 * public InputStream getInputStream() { return inputStream; }
	 * 
	 * public void setInputStream(InputStream inputStream) { this.inputStream =
	 * inputStream; }
	 */

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public boolean isStatusFlag()
	{
		return statusFlag;
	}

	public void setStatusFlag(boolean statusFlag)
	{
		this.statusFlag = statusFlag;
	}

	public File getContactExcel()
	{
		return contactExcel;
	}

	public void setContactExcel(File contactExcel)
	{
		this.contactExcel = contactExcel;
	}

	public String getContactExcelContentType()
	{
		return contactExcelContentType;
	}

	public void setContactExcelContentType(String contactExcelContentType)
	{
		this.contactExcelContentType = contactExcelContentType;
	}

	public String getContactExcelFileName()
	{
		return contactExcelFileName;
	}

	public void setContactExcelFileName(String contactExcelFileName)
	{
		this.contactExcelFileName = contactExcelFileName;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getContId()
	{
		return contId;
	}

	public void setContId(String contId)
	{
		this.contId = contId;
	}

	public Map<String, String> getDocMap()
	{
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap)
	{
		this.docMap = docMap;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public Map<String, String> getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(Map<String, String> totalCount)
	{
		this.totalCount = totalCount;
	}

	public String getSearchFields()
	{
		return searchFields;
	}

	public void setSearchFields(String searchFields)
	{
		this.searchFields = searchFields;
	}

	public String getSearchValue()
	{
		return SearchValue;
	}

	public void setSearchValue(String searchValue)
	{
		SearchValue = searchValue;
	}

	public JSONArray getJsonObjArray()
	{
		return jsonObjArray;
	}

	public void setJsonObjArray(JSONArray jsonObjArray)
	{
		this.jsonObjArray = jsonObjArray;
	}

	public JSONArray getUserTypeMap()
	{
		return userTypeMap;
	}

	public void setUserTypeMap(JSONArray userTypeMap)
	{
		this.userTypeMap = userTypeMap;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public Map<Integer, String> getSourceMap()
	{
		return sourceMap;
	}

	public void setSourceMap(Map<Integer, String> sourceMap)
	{
		this.sourceMap = sourceMap;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;

	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public File getDocument() {
		return document;
	}

	public void setDocument(File document) {
		this.document = document;
	}

	public String getDocumentContentType() {
		return documentContentType;
	}

	public void setDocumentContentType(String documentContentType) {
		this.documentContentType = documentContentType;
	}

	public String getDocumentFileName() {
		return documentFileName;
	}

	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}

	public void setHodStatus(boolean hodStatus) {
		this.hodStatus = hodStatus;
	}

	public boolean isHodStatus() {
		return hodStatus;
	}

	public void setMgtStatus(boolean mgtStatus) {
		this.mgtStatus = mgtStatus;
	}

	public boolean isMgtStatus() {
		return mgtStatus;
	}
	
}
