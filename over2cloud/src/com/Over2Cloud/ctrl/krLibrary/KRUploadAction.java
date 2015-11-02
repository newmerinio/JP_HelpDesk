package com.Over2Cloud.ctrl.krLibrary;

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
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;

public class KRUploadAction extends GridPropertyBean implements ServletRequestAware
{
	private static final long serialVersionUID = -1448801624367373228L;
	private List<ConfigurationUtilBean> KRUploadTextBox = null;
	private List<ConfigurationUtilBean> KRUploadFile = null;
	private Map<String, String> deptName = null;
	private HttpServletRequest request;
	private int indexVal;
	private File upload1;
	private String upload1ContentType;
	private String upload1FileName;
	private List<GridDataPropertyView> masterViewProp;
	private List<Object> masterViewList;
	private String fromDate;
	private String shareId;
	private Map<String, String> fullViewMap;
	private List<KRPojo> contactDetails;
	private String dataFrom;
	private String toDate;
	private Map<String, String> createdBy;

	@SuppressWarnings({  "unchecked" })
	public String beforeKRUploadData()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				deptName = new LinkedHashMap<String, String>();
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
								deptName.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}

				List<GridDataPropertyView> fieldsNames = Configuration.getConfigurationData("mapped_kr_upload_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_upload_configuration");
				if (fieldsNames != null && fieldsNames.size() > 0)
				{
					KRUploadTextBox = new ArrayList<ConfigurationUtilBean>();
					KRUploadFile = new ArrayList<ConfigurationUtilBean>();
					ConfigurationUtilBean obj = null;
					ConfigurationUtilBean obj1 = null;
					for (GridDataPropertyView gdp : fieldsNames)
					{
						obj = new ConfigurationUtilBean();
						if (gdp.getColomnName().equalsIgnoreCase("kr_starting_id"))
						{
							List<GridDataPropertyView> fieldsNames1 = Configuration.getConfigurationData("mapped_kr_sub_group_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_sub_group_configuration");
							if (fieldsNames1 != null && fieldsNames1.size() > 0)
							{
								for (GridDataPropertyView gdp1 : fieldsNames1)
								{
									obj1 = new ConfigurationUtilBean();
									if (gdp1.getColomnName().equalsIgnoreCase("group_name") || gdp1.getColomnName().equalsIgnoreCase("sub_group_name") || gdp1.getColomnName().equalsIgnoreCase("kr_starting_id"))
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
										KRUploadTextBox.add(obj1);
									}
								}
							}
						}
						else if (gdp.getColType().equalsIgnoreCase("F"))
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
							KRUploadFile.add(obj);
						}
						else if (gdp.getColType().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("kr_starting_id") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("created_date") && !gdp.getColomnName().equalsIgnoreCase("created_time"))
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
							obj.setField_length(gdp.getLength());
							KRUploadTextBox.add(obj);
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

	@SuppressWarnings(
	{ "unchecked" })
	public String uploadKRdata()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_kr_upload_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_upload_configuration");
				if (statusColName != null && statusColName.size() > 0)
				{
					InsertDataTable ob = null;
					boolean status = false;
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
					cbt.createTable22("kr_upload_data", Tablecolumesaaa, connectionSpace);

					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();

					String subGroupName = null;
					List<String> accessTypeList = new ArrayList<String>();
					ArrayList<ArrayList> mainList = new ArrayList<ArrayList>();
					ArrayList<String> fieldNameList = new ArrayList<String>();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("module_name") && !Parmname.equalsIgnoreCase("indexVal") && !Parmname.equalsIgnoreCase("otherPlant"))
						{
							if (Parmname.equalsIgnoreCase("sub_group_name"))
							{
								subGroupName = paramValue;
							}
							else if (Parmname.equalsIgnoreCase("access_type") || Parmname.equalsIgnoreCase("access_type100") || Parmname.equalsIgnoreCase("access_type101") || Parmname.equalsIgnoreCase("access_type102") || Parmname.equalsIgnoreCase("access_type103"))
							{
								accessTypeList.add(paramValue);
							}
							else
							{
								fieldNameList.add(Parmname);
								ArrayList<String> list = new ArrayList<String>();
								String tempParamValueSize[] = request.getParameterValues(Parmname);
								for (int i = 0; i < indexVal; i++)
								{
									list.add(tempParamValueSize[i].trim());
								}
								mainList.add(list);
							}
						}
					}
					for (int i = 0; i < mainList.get(0).size(); i++)
					{
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						for (int j = 0; j < fieldNameList.size(); j++)
						{
							// System.out.print(fieldNameList.get(j).toString());
							String s = mainList.get(j).get(i).toString();
							// System.out.println(":"+s);
							if (!fieldNameList.get(j).toString().equalsIgnoreCase("indexVal") && !s.equalsIgnoreCase(""))
							{
								ob = new InsertDataTable();
								ob.setColName(fieldNameList.get(j).toString());
								ob.setDataName(s);
								insertData.add(ob);
							}
						}
						String storeFilePath1 = null;
						if (upload1FileName != null)
						{

							storeFilePath1 = new CreateFolderOs().createUserDir("krUploadDocs") + "//" + getUpload1FileName().split(", ")[i];
							String str = storeFilePath1.replace("//", "/");
							if (storeFilePath1 != null)
							{
								File theFile1 = new File(storeFilePath1);
								File newFileName = new File(str);
								if (theFile1 != null)
								{
									try
									{
										FileUtils.copyFile(upload1, theFile1);
										if (theFile1.exists())
											theFile1.renameTo(newFileName);
									}
									catch (IOException e)
									{
										e.printStackTrace();
									}
									if (theFile1 != null)
									{
										ob = new InsertDataTable();
										ob.setColName("upload1");
										ob.setDataName(getUpload1FileName().split(", ")[i]);
										insertData.add(ob);
									}
								}
							}
						}
						String krAbre = null;
						String krID = null;
						String query = "SELECT kr_starting_abbre,kr_starting_id FROM kr_upload_data WHERE sub_group_name='" + subGroupName + "' ORDER BY kr_starting_id DESC LIMIT 1 ";
						List data = cbt.executeAllSelectQuery(query, connectionSpace);
						if (data != null && data.size() > 0)
						{
							Object[] object = (Object[]) data.get(0);
							krAbre = object[0].toString();
							krID = object[0].toString() + "" + String.valueOf(Integer.parseInt(object[1].toString().substring(object[0].toString().length(), object[1].toString().length())) + 1);
						}
						else
						{
							query = "SELECT kr_starting_id,start_kr_id FROM kr_sub_group_data WHERE id='" + subGroupName + "'";
							data.clear();
							data = cbt.executeAllSelectQuery(query, connectionSpace);
							if (data != null && data.size() > 0)
							{
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0] != null && object[1] != null)
									{
										krAbre = object[0].toString();
										krID = object[0].toString() + "" + String.valueOf(Integer.parseInt(object[1].toString()));
									}
								}
							}
						}

						ob = new InsertDataTable();
						ob.setColName("access_type");
						ob.setDataName(accessTypeList.get(i));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("kr_starting_abbre");
						ob.setDataName(krAbre);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("kr_starting_id");
						ob.setDataName(krID);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("sub_group_name");
						ob.setDataName(subGroupName);
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
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);

						status = cbt.insertIntoTable("kr_upload_data", insertData, connectionSpace);
						// System.out.println("status :::::  "+status);
						// System.out.println("-----------------------------------------------------------------");
					}
					if (status)
					{
						addActionMessage("Data added successfully!!!");
						return SUCCESS;
					}
					else
					{
						addActionMessage("Oops there is some error in data!!!");
						return ERROR;
					}
				}
				else
				{
					return ERROR;
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String beforeKRUploadViewHeader()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				deptName = new LinkedHashMap<String, String>();
				KRActionHelper KRH = new KRActionHelper();
				List data = null;
				String dept[] = KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
				if (dept != null && !dept[4].equalsIgnoreCase(""))
				{
					data = KRH.getLibraryAssignedDepartment(connectionSpace, dept[4]);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null)
							{
								deptName.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				createdBy = new LinkedHashMap<String, String>();
				List created = KRH.getCreatedByDetails(connectionSpace);
				if (created != null && created.size() > 0)
				{
					for (Iterator iterator = created.iterator(); iterator.hasNext();)
					{
						Object object = (Object) iterator.next();
						if (object != null)
						{
							createdBy.put(object.toString(), object.toString());
						}
					}
				}
				fromDate = DateUtil.getNextDateAfter(-30);
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

	public String krUploadViewGrid()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				viewGridProp();
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
	private void viewGridProp()
	{
		try
		{
			List<GridDataPropertyView> masterView = new ArrayList<GridDataPropertyView>();
			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			gpv.setSearch("true");
			masterView.add(gpv);
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_kr_upload_configuration", accountID, connectionSpace, "", 0, "table_name", "kr_upload_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if (gdp.getColomnName().equalsIgnoreCase("sub_group_name"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterView.add(gpv);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("group_name");
					gpv.setHeadingName("Group Name");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterView.add(gpv);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("contact_subtype_name");
					gpv.setHeadingName("Contact Sub Type");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterView.add(gpv);
					masterViewProp.add(gpv);

			/*		gpv = new GridDataPropertyView();
					gpv.setColomnName("access_type");
					gpv.setHeadingName("Access Type");
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(60);
					masterView.add(gpv);
					masterViewProp.add(gpv);*/

					gpv = new GridDataPropertyView();
					gpv.setColomnName("file");
					gpv.setHeadingName("File");
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow("true");
					gpv.setWidth(60);
					masterView.add(gpv);
					masterViewProp.add(gpv);
				}
				else if (gdp.getColomnName().equalsIgnoreCase("kr_name"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setFormatter(gdp.getFormatter());
					masterView.add(gpv);
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("share_score");
					gpv.setHeadingName("Share");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					gpv.setFormatter("shareLink");
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("download_score");
					gpv.setHeadingName("Download");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					gpv.setFormatter("downLoadLink");
					masterViewProp.add(gpv);

					gpv = new GridDataPropertyView();
					gpv.setColomnName("edit_score");
					gpv.setHeadingName("Edit");
					gpv.setEditable("false");
					gpv.setSearch("false");
					gpv.setHideOrShow("false");
					gpv.setWidth(60);
					gpv.setFormatter("editLink");
					masterViewProp.add(gpv);

				}
				else
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					if (gdp.getColomnName().equalsIgnoreCase("upload1"))
					{
						gpv.setHeadingName("Download");
					}
					else
					{
						gpv.setHeadingName(gdp.getHeadingName());
					}

					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setFormatter(gdp.getFormatter());
					masterView.add(gpv);
					masterViewProp.add(gpv);
				}
			}
			session.put("fieldsname", masterView);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings(
	{ "unchecked" })
	public String krUploadViewData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List data = null;
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_upload_data");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("fieldsname");

					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (i < (fieldNames.size() - 1))
							{
								if (gpv.getColomnName().equalsIgnoreCase("sub_group_name"))
								{
									query.append("subGroup.sub_group_name , ");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("group_name"))
								{
									query.append("grp.group_name , ");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("contact_subtype_name"))
								{
									query.append("dept.contact_subtype_name , ");
								}
								else if (gpv.getColomnName().equalsIgnoreCase("file"))
								{
									query.append("krUpload.upload1 AS file , ");
								}
								else
								{
									query.append("krUpload." + gpv.getColomnName().toString() + ",");
								}
							}
							else
							{
								query.append("krUpload." + gpv.getColomnName().toString());
							}
							i++;
						}
						query.append(" from kr_upload_data AS krUpload");
						query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON subGroup.id =  krUpload.sub_group_name ");
						query.append(" LEFT JOIN kr_group_data AS grp ON subGroup.group_name =  grp.id ");
						query.append(" LEFT JOIN contact_sub_type AS dept ON grp.sub_type_id =  dept.id ");
						query.append(" WHERE  krUpload.status='Active'");
						if (searchField != null && searchString != null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase("") && !searchString.equalsIgnoreCase("-1"))
						{
							if (searchField.equalsIgnoreCase("toDate"))
							{
								query.append("AND krUpload.created_date BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(searchString) + "'");
							}
							else if (searchField.equalsIgnoreCase("fromDate"))
							{
								query.append("AND krUpload.created_date BETWEEN '" + DateUtil.convertDateToUSFormat(searchString) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "'");
							}
							else if (searchField.equalsIgnoreCase("wildSearch"))
							{
								query.append("AND dept.id = '" + getSearchString() + "' OR grp.id = '" + getSearchString() + "' OR subGroup.id = '" + getSearchString() + "' OR krUpload.kr_starting_id = '" + getSearchString() + "' OR krUpload.kr_name = '" + getSearchString() + "' OR krUpload.tag_search LIKE '%" + getSearchString() + "%'");
							}
							else
							{
								query.append("AND " + getSearchField() + " = '" + getSearchString() + "'   ");
								query.append("AND krUpload.created_date BETWEEN '" + DateUtil.convertDateToUSFormat(fromDate) + "' AND '" + DateUtil.convertDateToUSFormat(toDate) + "' ");
							}
						}
						else
						{
							query.append(" AND krUpload.created_date BETWEEN '" + DateUtil.getNextDateAfter(-30) + "' AND '" + DateUtil.getCurrentDateUSFormat() + "'");
						}
						data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						if (data != null && data.size() > 0)
						{
							KRActionHelper KH = new KRActionHelper();
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
										else if (gpv.getColomnName().equalsIgnoreCase("tag_search"))
										{
											one.put(gpv.getColomnName(), obdata[k].toString().replace(",", ", "));
										}
										else if (gpv.getColomnName().equalsIgnoreCase("id"))
										{
											one.put(gpv.getColomnName(), obdata[k].toString());
											one.put("download_score", KH.getValueWithNullCheck(KH.fetchDocumentHistory(obdata[k].toString(), "download", connectionSpace)));
											one.put("edit_score", KH.getValueWithNullCheck(KH.fetchDocumentHistory(obdata[k].toString(), "edit", connectionSpace)));
											one.put("share_score", KH.getValueWithNullCheck(KH.fetchDocumentHistory(obdata[k].toString(), "share", connectionSpace)));
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
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
					// session.remove("fieldsname");
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

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public String viewModifyKrUpload()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
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
						if (paramValue != null && Parmname != null && !Parmname.equalsIgnoreCase("tableName") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("rowid"))
						{
							wherClause.put(Parmname, paramValue);
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("kr_upload_data", wherClause, condtnBlock, connectionSpace);
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
					wherClause.put("flag", "1");
					condtnBlock.put("id", condtIds.toString());
					cbt.updateTableColomn("kr_upload_data", wherClause, condtnBlock, connectionSpace);
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

	@SuppressWarnings("rawtypes")
	public String fullViewKr()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				StringBuilder query = new StringBuilder();
				KRActionHelper KH = new KRActionHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				query.append("SELECT upload.kr_name,upload.kr_starting_id,upload.kr_brief,upload.tag_search,dept.contact_subtype_name,krGroup.group_name,subGroup.sub_group_name, ");
				query.append(" upload.access_type,upload.upload1 FROM kr_upload_data AS upload  ");
				query.append(" LEFT JOIN kr_sub_group_data AS subGroup ON upload.sub_group_name=subGroup.id ");
				query.append(" LEFT JOIN kr_group_data AS krGroup ON subGroup.group_name=krGroup.id ");
				query.append(" LEFT JOIN contact_sub_type AS dept ON krGroup.sub_type_id =  dept.id ");
				if (dataFrom != null && dataFrom.equalsIgnoreCase("share"))
				{
					query.append(" LEFT JOIN kr_share_data AS krShare ON krShare.doc_name=upload.id ");
					query.append("WHERE krShare.id= " + shareId);
				}
				else if (dataFrom != null && dataFrom.equalsIgnoreCase("upload"))
				{
					query.append("WHERE upload.id= " + shareId);
				}
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				fullViewMap = new LinkedHashMap<String, String>();
				if (data != null && !data.isEmpty())
				{
					Object[] object = (Object[]) data.get(0);
					// fullViewMap.put("KR Name",
					// KH.getValueWithNullCheck(object[0]));
					fullViewMap.put("KR ID", KH.getValueWithNullCheck(object[1]));
					fullViewMap.put("Document", KH.getValueWithNullCheck(object[8]));
					fullViewMap.put("KR Brief", KH.getValueWithNullCheck(object[2]));
					fullViewMap.put("Tags", KH.getValueWithNullCheck(object[3]));
					fullViewMap.put("Group", KH.getValueWithNullCheck(object[5]));
					fullViewMap.put("Sub Group", KH.getValueWithNullCheck(object[6]));
					fullViewMap.put("Department", KH.getValueWithNullCheck(object[4]));
					fullViewMap.put("Access Type", KH.getValueWithNullCheck(object[7]));
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

	@SuppressWarnings("rawtypes")
	public String showContactDetail()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				StringBuilder query = new StringBuilder();
				KRActionHelper KH = new KRActionHelper();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String qry = "SELECT emp_name FROM kr_share_data where id ='" + shareId + "'";
				List data = cbt.executeAllSelectQuery(qry, connectionSpace);
				contactDetails = new ArrayList<KRPojo>();
				if (data != null && data.size() > 0)
				{
					query.append(" SELECT emp.emp_name,emp.mobile_no,emp.email_id,dept.contact_subtype_name FROM primary_contact as emp  ");
					query.append(" RIGHT JOIN contact_sub_type AS dept on emp.sub_type_id=dept.id ");
					query.append(" RIGHT JOIN manage_contact AS cc ON emp.id=cc.emp_id ");
					query.append(" WHERE cc.id IN " + data.toString().replace("[", "(").replace("]", ")") + "");
					List data2 = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2 != null && !data2.isEmpty())
					{
						for (Iterator iterator = data2.iterator(); iterator.hasNext();)
						{
							KRPojo CDB = new KRPojo();
							Object[] obj = (Object[]) iterator.next();
							CDB.setRemindTo(KH.getValueWithNullCheck(obj[0]));
							CDB.setMobNo(KH.getValueWithNullCheck(obj[1]));
							CDB.setEmailId(KH.getValueWithNullCheck(obj[2]));
							CDB.setDepartName(KH.getValueWithNullCheck(obj[3]));
							contactDetails.add(CDB);
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

	public List<ConfigurationUtilBean> getKRUploadTextBox()
	{
		return KRUploadTextBox;
	}

	public void setKRUploadTextBox(List<ConfigurationUtilBean> kRUploadTextBox)
	{
		KRUploadTextBox = kRUploadTextBox;
	}

	public List<ConfigurationUtilBean> getKRUploadFile()
	{
		return KRUploadFile;
	}

	public void setKRUploadFile(List<ConfigurationUtilBean> kRUploadFile)
	{
		KRUploadFile = kRUploadFile;
	}

	public Map<String, String> getDeptName()
	{
		return deptName;
	}

	public void setDeptName(Map<String, String> deptName)
	{
		this.deptName = deptName;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public int getIndexVal()
	{
		return indexVal;
	}

	public void setIndexVal(int indexVal)
	{
		this.indexVal = indexVal;
	}

	public File getUpload1()
	{
		return upload1;
	}

	public void setUpload1(File upload1)
	{
		this.upload1 = upload1;
	}

	public String getUpload1ContentType()
	{
		return upload1ContentType;
	}

	public void setUpload1ContentType(String upload1ContentType)
	{
		this.upload1ContentType = upload1ContentType;
	}

	public String getUpload1FileName()
	{
		return upload1FileName;
	}

	public void setUpload1FileName(String upload1FileName)
	{
		this.upload1FileName = upload1FileName;
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

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getShareId()
	{
		return shareId;
	}

	public void setShareId(String shareId)
	{
		this.shareId = shareId;
	}

	public Map<String, String> getFullViewMap()
	{
		return fullViewMap;
	}

	public void setFullViewMap(Map<String, String> fullViewMap)
	{
		this.fullViewMap = fullViewMap;
	}

	public String getDataFrom()
	{
		return dataFrom;
	}

	public void setDataFrom(String dataFrom)
	{
		this.dataFrom = dataFrom;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public List<KRPojo> getContactDetails()
	{
		return contactDetails;
	}

	public void setContactDetails(List<KRPojo> contactDetails)
	{
		this.contactDetails = contactDetails;
	}

	public Map<String, String> getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(Map<String, String> createdBy)
	{
		this.createdBy = createdBy;
	}

}
