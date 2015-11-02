package com.Over2Cloud.ctrl.krLibrary;

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

import org.apache.struts2.interceptor.ServletRequestAware;

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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.compliance.serviceFiles.ComplianceReminderHelper;

@SuppressWarnings("serial")
public class KRShareAction extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> shareDropDown = null;
	private List<ConfigurationUtilBean> shareRadioButton = null;
	private List<ConfigurationUtilBean> shareDate = null;
	private Map<String, String> contactTypeList;
	private Map<Integer, String> deptList;
	private Map<Integer, String> groupMap;
	private String doc_name;
	private String emp_name;
	private String shareStatus;
	private List<GridDataPropertyView> masterViewProp = null;
	private List<Object> masterViewList = null;
	private String searchTags;
	private String fromDate;
	private String toDate;
	private String searchField;
	private String searchValue;
	private String flag;
	private String docName;
	private Map<String, String> fullViewMap;
	private String status;
	private String dataFor;
	private String krId;

	@SuppressWarnings("unchecked")
	public String beforeKRLibraryAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setKRAddPageData();
			StringBuilder query = new StringBuilder();
			KRActionHelper KH = new KRActionHelper();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			query.append("SELECT upload.kr_name,upload.kr_starting_id,upload.kr_brief,upload.tag_search,dept.contact_subtype_name,krGroup.group_name,subGroup.sub_group_name, ");
			query.append(" upload.access_type,upload.upload1 FROM kr_share_data AS krShare  ");
			query.append(" RIGHT JOIN kr_upload_data AS upload ON krShare.doc_name=upload.id ");
			query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.sub_group_name=subGroup.id ");
			query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.group_name=krGroup.id ");
			query.append(" LEFT JOIN contact_sub_type AS dept ON krGroup.sub_type_id =  dept.id ");
			query.append("WHERE upload.id= " + docName);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			fullViewMap = new LinkedHashMap<String, String>();
			if (data != null && !data.isEmpty())
			{
				Object[] object = (Object[]) data.get(0);
				fullViewMap.put("Department", KH.getValueWithNullCheck(object[4]));
				fullViewMap.put("KR ID", KH.getValueWithNullCheck(object[1]));
				fullViewMap.put("Group", KH.getValueWithNullCheck(object[5]));
				fullViewMap.put("Sub Group", KH.getValueWithNullCheck(object[6]));
				fullViewMap.put("KR Name", KH.getValueWithNullCheck(object[0]));
				fullViewMap.put("KR Brief", KH.getValueWithNullCheck(object[2]));
				fullViewMap.put("Tags", KH.getValueWithNullCheck(object[3]));
				fullViewMap.put("Access Type", KH.getValueWithNullCheck(object[7]));
				fullViewMap.put("Document", KH.getValueWithNullCheck(object[8]));
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public void setKRAddPageData()
	{
		try
		{
			contactTypeList = new LinkedHashMap<String, String>();
			KRActionHelper KRH = new KRActionHelper();
			List data = null;
			String dept[] = KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
			if (dept != null && !dept[4].equalsIgnoreCase(""))
			{
				data = KRH.getAssignedDepartment(connectionSpace, dept[4]);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							contactTypeList.put(object[0].toString(), object[1].toString());
						}
					}
				}
			}

			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_kr_add_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_add_configuration");
			shareDropDown = new ArrayList<ConfigurationUtilBean>();
			shareRadioButton = new ArrayList<ConfigurationUtilBean>();
			shareDate = new ArrayList<ConfigurationUtilBean>();
			setShareDate(new ArrayList<ConfigurationUtilBean>());
			if (offeringLevel1 != null && offeringLevel1.size() > 0)
			{
				ConfigurationUtilBean obj = null;
				ConfigurationUtilBean obj1 = null;
				for (GridDataPropertyView gdp : offeringLevel1)
				{
					obj = new ConfigurationUtilBean();
					if (gdp.getColomnName().equalsIgnoreCase("doc_name"))
					{
						List<GridDataPropertyView> fieldsNames1 = Configuration.getConfigurationData("mapped_kr_sub_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_sub_group_configuration");
						if (fieldsNames1 != null && fieldsNames1.size() > 0)
						{
							for (GridDataPropertyView gdp1 : fieldsNames1)
							{
								obj1 = new ConfigurationUtilBean();
								if (gdp1.getColomnName().equalsIgnoreCase("group_name") || gdp1.getColomnName().equalsIgnoreCase("sub_group_name"))
								{
									obj1.setValue(gdp1.getHeadingName());
									obj1.setKey(gdp1.getColomnName());
									obj1.setValidationType(gdp1.getValidationType());
									obj1.setColType(gdp1.getColType());
									if (gdp.getMandatroy().toString().equals("1"))
									{
										obj1.setMandatory(true);
									}
									else
									{
										obj1.setMandatory(false);
									}
									shareDropDown.add(obj1);
								}
							}
						}
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
						shareDropDown.add(obj);
					}
					else if (gdp.getColType().equalsIgnoreCase("D"))
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
						shareDropDown.add(obj);
					}
					else if (gdp.getColType().equalsIgnoreCase("R"))
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
						shareRadioButton.add(obj);
					}
					else if (gdp.getColType().equalsIgnoreCase("date"))
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
						shareDate.add(obj);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings(
	{ "unchecked" })
	public String krShareAdd()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				boolean status = false, otherMailFlag = false, otherSmsFlag = false, selfMailFlag = false, selfSmsFlag = false;
				;
				int maxId = 0;
				CommonOperInterface coi = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_kr_add_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_add_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}

					ob1 = new TableColumes();
					ob1.setColumnname("ack_dge");
					ob1.setDatatype("varchar(20)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);

					ob1 = new TableColumes();
					ob1.setColumnname("reminder_alert");
					ob1.setDatatype("varchar(15)");
					ob1.setConstraint("default '0'");
					Tablecolumesaaa.add(ob1);

					coi.createTable22("kr_share_data", Tablecolumesaaa, connectionSpace);
					statusColName.clear();
					statusColName = Configuration.getConfigurationData("mapped_kr_actiontaken_report", accountID, connectionSpace, "", 0, "table_name", "kr_actionreport_configuration");
					if (statusColName != null && statusColName.size() > 0)
					{
						Tablecolumesaaa = new ArrayList<TableColumes>();
						for (GridDataPropertyView gdp : statusColName)
						{
							ob1 = new TableColumes();
							ob1.setColumnname(gdp.getColomnName());
							ob1.setDatatype(gdp.getData_type());
							ob1.setConstraint("default NULL");
							Tablecolumesaaa.add(ob1);
						}

						coi.createTable22("kr_share_report_data", Tablecolumesaaa, connectionSpace);
					}
					InsertDataTable ob = null;
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					ArrayList<ArrayList> mainList = new ArrayList<ArrayList>();
					ArrayList<String> fieldNameList = new ArrayList<String>();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && !Parmname.equalsIgnoreCase("doc_name") && !Parmname.equalsIgnoreCase("emp_name") && !Parmname.equalsIgnoreCase("__checkbox_othersms") && !Parmname.equalsIgnoreCase("__checkbox_selfmail") && !Parmname.equalsIgnoreCase("__checkbox_selfsms") && !Parmname.equalsIgnoreCase("__checkbox_othermail"))
						{
							if (Parmname.equalsIgnoreCase("selfmail"))
							{
								selfMailFlag = true;
							}
							else if (Parmname.equalsIgnoreCase("selfsms"))
							{
								selfSmsFlag = true;
							}
							else if (Parmname.equalsIgnoreCase("othermail"))
							{
								otherMailFlag = true;
							}
							else if (Parmname.equalsIgnoreCase("othersms"))
							{
								otherSmsFlag = true;
							}
							else
							{
								fieldNameList.add(Parmname);
								ArrayList<String> list = new ArrayList<String>();
								String tempParamValueSize[] = request.getParameterValues(Parmname);
								list.add(tempParamValueSize[0].trim());
								mainList.add(list);
							}
						}
					}
					List<InsertDataTable> insertData = null;
					List<InsertDataTable> insertData1 = null;
					for (int i = 0; i < doc_name.split(",").length; i++)
					{
						String shareDate = null;
						String actionDate = null;
						insertData = new ArrayList<InsertDataTable>();
						for (int j = 0; j < fieldNameList.size(); j++)
						{
							// System.out.print(fieldNameList.get(j).toString());
							String s = mainList.get(j).get(0).toString();
							// System.out.println(":"+s);
							if (!s.equalsIgnoreCase(""))
							{
								if (fieldNameList.get(j).toString().equalsIgnoreCase("due_date") || fieldNameList.get(j).toString().equalsIgnoreCase("due_share_date"))
								{
									if (fieldNameList.get(j).toString().equalsIgnoreCase("due_date"))
									{
										actionDate = DateUtil.convertDateToUSFormat(s);
									}
									if (fieldNameList.get(j).toString().equalsIgnoreCase("due_share_date"))
									{
										shareDate = DateUtil.convertDateToUSFormat(s);
									}
									ob = new InsertDataTable();
									ob.setColName(fieldNameList.get(j).toString());
									ob.setDataName(DateUtil.convertDateToUSFormat(s));
									insertData.add(ob);
								}
								else
								{
									ob = new InsertDataTable();
									ob.setColName(fieldNameList.get(j).toString());
									ob.setDataName(s);
									insertData.add(ob);
								}
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
						ob = new InsertDataTable();
						ob.setColName("doc_name");
						ob.setDataName(doc_name.split(",")[i].trim());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_status");
						ob.setDataName("Pending");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_taken");
						ob.setDataName("Pending");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("user_name");
						ob.setDataName(userName);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("created_date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("created_time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
						if (actionDate != null && !actionDate.equalsIgnoreCase(""))
						{
							ob = new InsertDataTable();
							ob.setColName("reminder_alert");
							ob.setDataName(actionDate);
							insertData.add(ob);
						}

						ob = new InsertDataTable();
						ob.setColName("emp_name");
						ob.setDataName(emp_name.trim());
						insertData.add(ob);
						
						maxId = coi.insertDataReturnId("kr_share_data", insertData, connectionSpace);
						insertData.clear();
						if (maxId > 0)
						{
							status = true;
							insertData1 = new ArrayList<InsertDataTable>();

							String query = "SELECT kr_starting_id,upload1 FROM kr_upload_data WHERE id=" + doc_name.split(",")[i].trim();
							List idData = coi.executeAllSelectQuery(query, connectionSpace);
							if (idData != null && !idData.isEmpty())
							{
								Object[] object = (Object[]) idData.get(0);

								ob = new InsertDataTable();
								ob.setColName("kr_id");
								ob.setDataName(object[0].toString());
								insertData1.add(ob);

								ob = new InsertDataTable();
								ob.setColName("kr_upload");
								ob.setDataName(object[1].toString());
								insertData1.add(ob);
							}

							ob = new InsertDataTable();
							ob.setColName("kr_sharing_id");
							ob.setDataName(maxId);
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("share_date");
							ob.setDataName(shareDate);
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_date");
							ob.setDataName(actionDate);
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("rating");
							ob.setDataName("NA");
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("comments");
							ob.setDataName("NA");
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_taken");
							ob.setDataName("Pending");
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_by");
							ob.setDataName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_taken_date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData1.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_taken_time");
							ob.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData1.add(ob);

							coi.insertIntoTable("kr_share_report_data", insertData1, connectionSpace);
							insertData1.clear();
						}

						KRPojo krp = null;
						CommunicationHelper CH = new CommunicationHelper();
						KRActionHelper KH = new KRActionHelper();
						List data = KH.fetchAllKRDetails(maxId, connectionSpace);
						krp = KH.krDetailsSet(data);
						String loggedDetails[] = KH.getEmpDetailsByUserName("KR", userName, connectionSpace);
						String mailBody = null;
						String subject = null;
						StringBuilder msg = new StringBuilder();
						if (otherSmsFlag || selfSmsFlag)
						{
							if (maxId > 0)
							{
								if (selfSmsFlag)
								{
									msg.append("Dear " + loggedDetails[1] + ", Your KR " + krp.getKrName() + " has been successfully shared.");
									CH.addMessage(loggedDetails[1], loggedDetails[5], loggedDetails[2], msg.toString(), "", "Pending", "0", "KR", connectionSpace);
									msg.setLength(0);
								}
								if (otherSmsFlag)
								{
									StringBuilder query = new StringBuilder();
									String shareTo[] = krp.getKrShareTo().split(",");
									for (String str : shareTo)
									{
										query.append("SELECT emp.emp_name,emp.email_id,emp.mobile_no FROM contact_sub_type AS dept   ");
										query.append("INNER JOIN primary_contact AS emp ON emp.sub_type_id =dept.id ");
										query.append("INNER JOIN manage_contact AS cc ON cc.emp_id =emp.id ");
										query.append(" WHERE cc.id IN(" + str + ") ");
										List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
										query.setLength(0);
										if (dataList != null && dataList.size() > 0)
										{
											Object[] obj = (Object[]) dataList.get(0);
											msg.append("Dear " + obj[0].toString() + ", a new KR " + krp.getKrName() + " has been shared for you by " + loggedDetails[1] + " in " + krp.getKrAccessType() + " mode. ");
											if (krp.getKrActionReq() != null && !krp.getKrActionReq().equalsIgnoreCase("NA") && krp.getKrActionReq().equalsIgnoreCase("Yes"))
											{
												msg.append("Your action is required ");
												if (krp.getKrDueDate() != null && !krp.getKrDueDate().equalsIgnoreCase(""))
												{
													msg.append("by " + krp.getKrDueDate() + ".");
												}
											}
											CH.addMessage(obj[0].toString(), "", obj[2].toString(), msg.toString(), "", "Pending", "0", "KR", connectionSpace);
											msg.setLength(0);
										}
									}
								}
							}
						}
						if (otherMailFlag || selfMailFlag)
						{
							if (maxId > 0)
							{
								CreateFolderOs cos = new CreateFolderOs();

								if (selfMailFlag)
								{
									StringBuilder query = new StringBuilder();
									query.append("SELECT emp.emp_name FROM contact_sub_type AS dept   ");
									query.append("INNER JOIN primary_contact AS emp ON emp.sub_type_id =dept.id ");
									query.append("INNER JOIN manage_contact AS cc ON cc.emp_id =emp.id ");
									query.append(" WHERE cc.id IN(" + krp.getKrShareTo() + ") ");
									List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
									if (dataList != null && !dataList.isEmpty())
									{
										mailBody = getMailTextForShare(krp, true, loggedDetails[1], dataList.toString().replace("[", "").replace("]", ""));
										subject = "KR Notification Alert";
										// new
										// GenericMailSender().sendMail("karnikag@dreamsol.biz",
										// subject, mailBody, "",
										// "smtp.gmail.com", "465",
										// "karnikag@dreamsol.biz",
										// "karnikagupta");
										CH.addMail(loggedDetails[1], loggedDetails[5], loggedDetails[3], subject, mailBody, "", "Pending", "0", cos.createUserDir("krUploadDocs") + "//" + krp.getKrAttach(), "KR", connectionSpace);
									}
								}
								if (otherMailFlag)
								{
									subject = "KR Acknowledgement Alert";
									StringBuilder query = new StringBuilder();
									String shareTo[] = krp.getKrShareTo().split(",");
									for (String str : shareTo)
									{
										query.append("SELECT emp.emp_name,emp.email_id,emp.mobile_no FROM contact_sub_type AS dept   ");
										query.append("INNER JOIN primary_contact AS emp ON emp.sub_type_id =dept.id ");
										query.append("INNER JOIN manage_contact AS cc ON cc.emp_id =emp.id ");
										query.append(" WHERE cc.id IN(" + str + ") ");
										List dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
										query.setLength(0);
										if (dataList != null && dataList.size() > 0)
										{
											Object[] obj = (Object[]) dataList.get(0);
											mailBody = getMailTextForShare(krp, false, loggedDetails[1], obj[0].toString());
											CH.addMail(obj[0].toString(), "", obj[1].toString(), subject, mailBody, "", "Pending", "0", cos.createUserDir("krUploadDocs") + "//" + krp.getKrAttach(), "KR", connectionSpace);
											// new
											// GenericMailSender().sendMail("karnikag@dreamsol.biz",
											// subject, mailBody, "",
											// "smtp.gmail.com", "465",
											// "karnikag@dreamsol.biz",
											// "karnikagupta");
										}
									}
								}
							}
						}
					}
					if (status)
					{
						addActionMessage("KR Share Successfully");
						return SUCCESS;
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
						return ERROR;
					}
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

	private String getMailTextForShare(KRPojo kp, boolean flag, String loggedName, String shareTo)
	{
		StringBuilder mailText = new StringBuilder();
		try
		{
			if (flag)
			{
				mailText.append("<br><div align='justify'><font face ='ARIAL' size='2'><h3>Dear " + loggedName + ", </h3></FONT></div>");
			}
			else
			{
				mailText.append("<br><div align='justify'><font face ='ARIAL' size='2'><h3>Dear " + shareTo + ", </h3></FONT></div>");
			}
			mailText.append("<div align='justify'><font face ='ARIAL' size='2'><h3>Hello!!!</h3></FONT></div>");
			if (flag)
			{
				mailText.append("<div align='justify'><font face ='ARIAL' size='2'>This is an acknowledgement alert for following KR done by you. </FONT></div>");
			}
			else
			{
				String URL = "<a href=http://www.over2cloud.co.in>Login</a>";
				mailText.append("<div align='justify'><font face ='ARIAL' size='2'>Your kind attention is required for following KR Shared for your analysis. You may find all the related documents for this activity attached with the mail. We request, you to take relevant actions (if any) by ");
				mailText.append(URL);
				mailText.append(" with your respective credentials. </FONT></div>");
			}
			mailText.append("<br>");
			mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b><center>KR Share</center></b></font></div><br>");
			mailText.append("<table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>KR Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrName() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>KR Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrBrief() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Tags:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrTags() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Department:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getDepartment() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Group:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getGroup() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Sub Group:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getSubGroup() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Access Type:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrAccessType() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Due Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrDueDateReq() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Action Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrDueDate() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Frequency:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrFreq() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Rating Required:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrRating() + "</td></tr>");
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Comments Required:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrComments() + "</td></tr>");

			if (flag)
			{
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Share To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + shareTo + "</td></tr>");
			}
			else
			{
				mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Share By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + loggedName + "</td></tr>");
			}
			mailText.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:ARIAL;'><b>Attachment:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:ARIAL;'>" + kp.getKrAttach() + "</td></tr>");
			mailText.append("</table>");
			mailText.append("<br>");
			mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b> Thanks & Regards </b></font></div><br>");
			mailText.append("<div align='justify'><font face ='ARIAL' size='2'><b> Support Team </b></font></div>");
			mailText.append("<br>");
			mailText.append("-----------------------------------------------------------------------");
			mailText.append("<br><div align='justify'><font face ='ARIAL' size='1'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");

			return mailText.toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mailText.toString();
	}

	@SuppressWarnings("unchecked")
	public String beforeKrViewHeaderPage()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				fromDate = DateUtil.getNextDateAfter(-30);
				deptList = new LinkedHashMap<Integer, String>();
				KRActionHelper KRH = new KRActionHelper();
				List data = null;
				String dept[] = KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
				if (dept != null && !dept[4].equalsIgnoreCase(""))
				{
					data = KRH.getSharedAssignedDepartment(connectionSpace, dept[4]);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								deptList.put(Integer.parseInt(object[0].toString()), object[1].toString());
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

	public String beforeKRView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp();
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
	public void setViewProp()
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("empSubType");
			gpv.setHeadingName("Department");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("group_name");
			gpv.setHeadingName("Group Name");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("sub_group_name");
			gpv.setHeadingName("Sub Group Name");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("tag_search");
			gpv.setHeadingName("Tags");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("kr_id");
			gpv.setHeadingName("KR ID");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("kr_name");
			gpv.setHeadingName("KR Name");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setFormatter("fullKrDetails");
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("document");
			gpv.setHeadingName("Doc");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			gpv.setWidth(40);
			masterViewProp.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("primary_author");
			gpv.setHeadingName("Primary Author");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setWidth(80);
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_kr_add_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_add_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				if (gdp.getColomnName().equalsIgnoreCase("doc_name"))
				{
					gpv.setHeadingName("Download");
				}
				else
				{
					gpv.setHeadingName(gdp.getHeadingName());
				}
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setWidth(gdp.getWidth());
				gpv.setFormatter(gdp.getFormatter());
				if (shareStatus != null && shareStatus.equalsIgnoreCase("byMe"))
				{
					if (gdp.getColomnName().equalsIgnoreCase("user_name"))
					{
						gpv.setHideOrShow("true");
					}
					else
					{
						gpv.setHideOrShow(gdp.getHideOrShow());
					}
				}
				else if (shareStatus != null && shareStatus.equalsIgnoreCase("2"))
				{
					gpv.setHideOrShow(gdp.getHideOrShow());
				}
				masterViewProp.add(gpv);
			}
			session.put("fieldsname", masterViewProp);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@SuppressWarnings(
	{  "unchecked" })
	public String viewKRInGrid()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_share_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<Object> Listhb = new ArrayList<Object>();
					BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					// int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("fieldsname");
					if (fieldNames == null)
					{
						session.remove("fieldsname");
					}

					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName().equalsIgnoreCase("sub_type_id"))
								{
									query.append("dept.contact_subtype_name AS groupdept,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("group_name"))
								{
									query.append("krGroup.group_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("empSubType"))
								{
									query.append("share.emp_name as dept,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("sub_group_name"))
								{
									query.append("subGroup.sub_group_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("doc_name"))
								{
									query.append("upload.upload1,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("tag_search"))
								{
									query.append("upload.tag_search,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("kr_name"))
								{
									query.append("upload.kr_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("kr_id"))
								{
									query.append("upload.kr_starting_id,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("document"))
								{
									query.append("share.doc_name,");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("primary_author"))
								{
									query.append("upload.user_name AS primaryAuthor,");
								}
								else
								{
									query.append("share." + gpv.getColomnName().toString() + ",");
								}
							}
							else
							{
								query.append("share." + gpv.getColomnName().toString());
							}
							i++;
						}
					}
					query.append(" from kr_share_data As share ");
					query.append(" LEFT JOIN kr_upload_data AS upload ON share.doc_name=upload.id ");
					query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.sub_group_name=subGroup.id ");
					query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.group_name=krGroup.id ");
					query.append(" LEFT JOIN contact_sub_type AS dept ON krGroup.sub_type_id =  dept.id ");
					query.append(" WHERE share.status='Active' AND share.user_name='" + userName + "' ");
					
					if (searchTags != null && !searchTags.equalsIgnoreCase(""))
					{
						query.append(" AND upload.tag_search LIKE '%" + searchTags + "%' OR upload.upload1 LIKE '%" + searchTags + "%'");
					}
					else if (fromDate != null && !fromDate.equalsIgnoreCase("") && toDate != null && !toDate.equalsIgnoreCase(""))
					{
						query.append(" AND share.created_date BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
					}
					else if (searchField != null && !searchField.equalsIgnoreCase("-1") && searchValue != null && !searchValue.equalsIgnoreCase("-1"))
					{
						query.append(" AND " + searchField + " = '" + searchValue + "'");
					}
					else
					{
						query.append(" AND share.created_date BETWEEN '" + DateUtil.getNextDateAfter(-30) + "' AND '" + DateUtil.getCurrentDateUSFormat() + "'");
					}
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");

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
					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data != null && data.size() > 0)
					{
						KRActionHelper KH = new KRActionHelper();
						ComplianceReminderHelper RH = new ComplianceReminderHelper();
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
										one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
									}
									else if (gpv.getColomnName().equalsIgnoreCase("empSubType"))
									{
										one.put(gpv.getColomnName(), KH.getValueWithNullCheck(KH.getMultipleDeptName(obdata[k].toString(), connectionSpace)));
									}
									else if (gpv.getColomnName().equalsIgnoreCase("emp_name"))
									{
										one.put(gpv.getColomnName(), KH.getValueWithNullCheck(KH.getMultipleEmpName(obdata[k].toString(), connectionSpace)));
									}
									else if (gpv.getColomnName().equalsIgnoreCase("frequency"))
									{
										one.put(gpv.getColomnName(), KH.getValueWithNullCheck(RH.getFrequencyName(obdata[k].toString())));
									}
									else
									{
										one.put(gpv.getColomnName(), obdata[k].toString());
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

	@SuppressWarnings("unchecked")
	public String viewKrInGridDownload()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_share_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<Object> Listhb = new ArrayList<Object>();
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

					StringBuilder query = new StringBuilder("");
					query.append("select download.user_name,download.created_date,download.created_time ");
					query.append(" from kr_share_download_history AS download LEFT JOIN kr_share_data AS krShare ON krShare.id=download.share_id WHERE krShare.doc_name=" + docName);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							one.put("downloadBy", obdata[0].toString());
							one.put("date", DateUtil.convertDateToIndianFormat(obdata[1].toString()) + " " + obdata[2].toString());
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

	@SuppressWarnings("unchecked")
	public String viewKrInGridShare()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_share_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<Object> Listhb = new ArrayList<Object>();
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

					StringBuilder query = new StringBuilder("");
					query.append("SELECT emp_name,due_share_date,due_date FROM kr_share_data WHERE doc_name =" + docName);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						KRActionHelper KH = new KRActionHelper();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							one.put("department", KH.getValueWithNullCheck(KH.getMultipleDeptName(obdata[0].toString(), connectionSpace)));
							one.put("contactPerson", KH.getValueWithNullCheck(KH.getMultipleEmpName(obdata[0].toString(), connectionSpace)));

							if (obdata[1] != null)
							{
								one.put("date", DateUtil.convertDateToIndianFormat(obdata[1].toString()));
							}
							else
							{
								one.put("date", "NA");
							}
							if(obdata[2]!=null)
							{
								one.put("actiondate", DateUtil.convertDateToIndianFormat(obdata[2].toString()));
							}
							else
							{
								one.put("actiondate","NA");
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

	@SuppressWarnings("unchecked")
	public String viewKrInGridEdit()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_share_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<Object> Listhb = new ArrayList<Object>();
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

					StringBuilder query = new StringBuilder("");
					query.append("SELECT rating,comments,action_taken_date,action_taken_time,action_by,kr_upload,kr_id ");
					query.append("  FROM kr_share_report_data  AS report LEFT JOIN kr_share_data AS krshare on krshare.id=report.kr_sharing_id WHERE krshare.doc_name=" + docName);
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						KRActionHelper KH = new KRActionHelper();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							one.put("rating", KH.getValueWithNullCheck(obdata[0]));
							one.put("comment", KH.getValueWithNullCheck(obdata[1]));
							if (obdata[2] != null)
							{
								one.put("actiondate", DateUtil.convertDateToIndianFormat(obdata[2].toString()) + " " + obdata[3].toString());
							}
							else
							{
								one.put("actiondate", "NA");
							}
							if (obdata[4] != null)
							{
								one.put("actionBy", Cryptography.decrypt(obdata[4].toString(), DES_ENCRYPTION_KEY));
							}
							else
							{
								one.put("actionBy", "NA");
							}

							one.put("actionDoc", KH.getValueWithNullCheck(obdata[5]));
							one.put("krId", KH.getValueWithNullCheck(obdata[6]));
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
	{ "unchecked" })
	public String modifyKRInGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
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
					cbt.updateTableColomn("kr_share_data", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length)
							condtIds.append(H + " ,");
						else
							condtIds.append(H);
						i++;
					}
					wherClause.put("status", "In active");
					condtnBlock.put("id", condtIds.toString());
					cbt.updateTableColomn("kr_share_data", wherClause, condtnBlock, connectionSpace);
				}
				return SUCCESS;
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

	@SuppressWarnings("unchecked")
	public String scoreDownloadReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List data = new KRActionHelper().fetchAllKRUploadDetails(Integer.parseInt(docName), connectionSpace);
				fullViewMap = new LinkedHashMap<String, String>();
				KRActionHelper KH = new KRActionHelper();
				if (data != null && !data.isEmpty())
				{
					Object[] object = (Object[]) data.get(0);
					fullViewMap.put("Primary Author", KH.getValueWithNullCheck(object[9]));
					fullViewMap.put("Department", KH.getValueWithNullCheck(object[5]));
					fullViewMap.put("Date/Time", DateUtil.convertDateToIndianFormat(KH.getValueWithNullCheck(object[10])) + " " + KH.getValueWithNullCheck(object[11]));
					fullViewMap.put("Group", KH.getValueWithNullCheck(object[6]));
					fullViewMap.put("Sub Group", KH.getValueWithNullCheck(object[7]));
					fullViewMap.put("KR Brief", KH.getValueWithNullCheck(object[2]));
					fullViewMap.put("Tags", KH.getValueWithNullCheck(object[3]));
					fullViewMap.put("Document", KH.getValueWithNullCheck(object[4]));
					fullViewMap.put("Access Type", KH.getValueWithNullCheck(object[8]));
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

	public List<ConfigurationUtilBean> getShareDropDown()
	{
		return shareDropDown;
	}

	public void setShareDropDown(List<ConfigurationUtilBean> shareDropDown)
	{
		this.shareDropDown = shareDropDown;
	}

	public List<ConfigurationUtilBean> getShareRadioButton()
	{
		return shareRadioButton;
	}

	public void setShareRadioButton(List<ConfigurationUtilBean> shareRadioButton)
	{
		this.shareRadioButton = shareRadioButton;
	}

	public List<ConfigurationUtilBean> getShareDate()
	{
		return shareDate;
	}

	public void setShareDate(List<ConfigurationUtilBean> shareDate)
	{
		this.shareDate = shareDate;
	}

	public Map<String, String> getContactTypeList()
	{
		return contactTypeList;
	}

	public void setContactTypeList(Map<String, String> contactTypeList)
	{
		this.contactTypeList = contactTypeList;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public String getDoc_name()
	{
		return doc_name;
	}

	public void setDoc_name(String doc_name)
	{
		this.doc_name = doc_name;
	}

	public String getEmp_name()
	{
		return emp_name;
	}

	public void setEmp_name(String empName)
	{
		emp_name = empName;
	}

	public String getShareStatus()
	{
		return shareStatus;
	}

	public void setShareStatus(String shareStatus)
	{
		this.shareStatus = shareStatus;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getSearchTags()
	{
		return searchTags;
	}

	public void setSearchTags(String searchTags)
	{
		this.searchTags = searchTags;
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

	public Map<Integer, String> getGroupMap()
	{
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap)
	{
		this.groupMap = groupMap;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public Map<String, String> getFullViewMap()
	{
		return fullViewMap;
	}

	public void setFullViewMap(Map<String, String> fullViewMap)
	{
		this.fullViewMap = fullViewMap;
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

	public String getKrId()
	{
		return krId;
	}

	public void setKrId(String krId)
	{
		this.krId = krId;
	}
}
