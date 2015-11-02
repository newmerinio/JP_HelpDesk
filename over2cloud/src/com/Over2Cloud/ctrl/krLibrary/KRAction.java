package com.Over2Cloud.ctrl.krLibrary;

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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.krLibrary.Download.DownloadConversionAction;
import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;

public class KRAction extends GridPropertyBean implements ServletRequestAware
{
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String grpID;
	private String deptID;
	private String flag;
	public InputStream excelStream;
	public String downloadFile;
	private Map<String, String> krActionTextBox = null;
	private Map<Integer, String> subGrpList = null;
	private List<ConfigurationUtilBean> krDirTextBox = null;
	private List<ConfigurationUtilBean> krActionFileField = null;
	private Map<String, String> krActionCheckBox = null;
	private Map<String, String> serchedTagMap = null;
	private Map<String, String> modifyTagMap = null;
	private String fromDeptId;
	private String group;
	private String file;
	private String subGroup;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private List<Object> masterViewList;
	private String tags;
	private String accessType;
	@SuppressWarnings("rawtypes")
	List krViaTags = null;
	private String krName;
	private InputStream fileInputStream;
	private long contentLength;
	private File krUpload;
	private String krUploadContentType;
	private String krUploadFileName;
	private String fileName;
	private String downloadPath;
	private String krRating;
	private String kr_ID;
	private String dept;
	private String empName;
	private String tag;
	private File browse;
	private String BrowseContentType;
	private Map<Integer, String> deptList = null;
	private Map<Integer, String> subDeptList = new HashMap<Integer, String>();
	private Map<String, String> subDept_DeptLevelName = null;
	private Map<Integer, String> empList = null;
	private String l1Share;
	private String l2Share;
	private String l3Share;
	private String l4Share;
	private String l5Share;
	private String l1Modify;
	private String l2Modify;
	private String l3Modify;
	private String l4Modify;
	private String l5Modify;
	private String notification;
	private String formater;
	private String filePath;
	private String lShare;
	private String lModify;
	private String brief;
	private String krComment;
	private String header;
	private List<Parent> parentTakeaction = null;
	JSONArray jsonArray = null;
	private String emailID=null;
	private String password=null;
	private String server=null;
	private String port=null;
	boolean ratingFlag=false;
	boolean commentFlag=false;
	private String frequency=null;
	private String rating=null;
	private String comments=null;
	private Map<String, String> docList;
	private String subgrpID;

	// execute method for navigation.
	public String execute()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				//System.out.println("hello execute");
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String beforeKRDirAdd()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");

				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();

				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();

				List<String> colname = new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");
				/* Get Department Data */
			/*	deptHierarchy = (String) session.get("userDeptLevel");
				System.out.println("deptHierarchy  " + deptHierarchy);
				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = true;
				}
				else if (deptHierarchy.equals("1"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = false;
				}*/
				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				// Getting Id, Dept Name for Non Service Department
				departmentlist = getDataFromTable("department", colmName,
						wherClause, order, connectionSpace);
				if (departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator
							.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
				}
				departmentlist.removeAll(departmentlist);
				setAddPageDataFields();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String beforeKRAdd()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String orgLevel = (String) session.get("orgnztnlevel");
				//System.out.println("orgLevel:" + orgLevel);
				String orgId = (String) session.get("mappedOrgnztnId");
				//System.out.println("orgId:" + orgId);
				SessionFactory connectionSpace = (SessionFactory) session
						.get("connectionSpace");

				deptList = new LinkedHashMap<Integer, String>();
				List departmentlist = new ArrayList();

				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();

				List<String> colname = new ArrayList<String>();
				colname.add("orgLevel");
				colname.add("levelName");
				/* Get Department Data */
				/*deptHierarchy = (String) session.get("userDeptLevel");
				System.out.println("deptHierarchy  " + deptHierarchy);
				if (deptHierarchy.equals("2"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = true;
				}
				else if (deptHierarchy.equals("1"))
				{
					setsubDept_DeptTags(deptHierarchy);
					checkdept = false;
				}*/
				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				// Getting Id, Dept Name for Non Service Department
				departmentlist = getDataFromTable("department", colmName,
						wherClause, order, connectionSpace);
				if (departmentlist.size() > 0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator
							.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						deptList.put((Integer) object[0], object[1].toString());
					}
				}
				departmentlist.removeAll(departmentlist);
				setAddPageDataFields();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// KR Search
	public String beforeKRSearch()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public void setAddPageDataFields()
	{
		try
		{
			List<GridDataPropertyView> offeringLevel1 = Configuration
					.getConfigurationData(
							"mapped_knowledge_resource_Configuration",
							accountID, connectionSpace, "", 0,
							"table_name",
							"knowledge_resource_Configuration");
			krActionTextBox = new LinkedHashMap<String, String>();
			krActionFileField = new ArrayList<ConfigurationUtilBean>();
			krActionCheckBox = new LinkedHashMap<String, String>();
			for (GridDataPropertyView gdp : offeringLevel1)
			{
				ConfigurationUtilBean configBean = new ConfigurationUtilBean();
				if (gdp.getColType().trim().equalsIgnoreCase("T")
						&& !gdp.getColomnName()
								.equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					krActionTextBox.put(gdp.getColomnName(), gdp
							.getHeadingName());
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("F")
						&& !gdp.getColomnName()
								.equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
				//	krActionFileField.put(gdp.getColomnName(), gdp
							//.getHeadingName());
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("C")
						&& !gdp.getColomnName()
								.equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					krActionCheckBox.put(gdp.getColomnName(), gdp
							.getHeadingName());
				}
				else if (gdp.getColType().trim().equalsIgnoreCase("C")
						&& !gdp.getColomnName()
								.equalsIgnoreCase("userName")
						&& !gdp.getColomnName().equalsIgnoreCase("date")
						&& !gdp.getColomnName().equalsIgnoreCase("time"))
				{
					configBean.setField_name(gdp.getHeadingName());
					configBean.setField_value(gdp.getColomnName());

					// configBean.setMandatory(mandatory);
					krActionCheckBox.put(gdp.getColomnName(), gdp
							.getHeadingName());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String modifyReportInGrid()
	{
		

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				if (getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections
							.list((Enumeration<String>) request
									.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null
								&& !paramValue.equalsIgnoreCase("")
								&& Parmname != null
								&& !Parmname.equalsIgnoreCase("")
								&& !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper")
								&& !Parmname.equalsIgnoreCase("rowid")) wherClause
								.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("kractiontaken", wherClause,
							condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory()
							.createInterface();
					String tempIds[] = getId().split(",");
					StringBuilder condtIds = new StringBuilder();
					int i = 1;
					for (String H : tempIds)
					{
						if (i < tempIds.length) condtIds.append(H + " ,");
						else condtIds.append(H);
						i++;
					}
					cbt.deleteAllRecordForId("kractiontaken", "id", condtIds
							.toString(), connectionSpace);
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
	
	
	
	
	
	
	
	
	
	
	
	
	

	// KR SEARCH VIA TAGS<><><><>
	@SuppressWarnings("rawtypes")
	public String viewSearchKR()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		List listData=null;
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String temp = "%" + tags + "%";
				//krViaTags = krSearchedDataByTags("", "");
				//System.out.println("krViaTags size>>>>>>> "+krViaTags.size());
				serchedTagMap = new HashMap<String, String>();
				
				
				
				/*String sql_query = "  select krid,krName,date,time, id from kr_add where tag like'"
						+ temp + "' and status='0' ";*/
				String sql_query = "  select krid,krName,date,time, id from kr_add where tag like'"
					+ temp + "'";
			//	System.out.println("sql_query  "+sql_query.toString());
				listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
				Collections.reverse(listData);
				parentTakeaction = new ArrayList<Parent>();

				if (listData != null)
				{
					for (Iterator it = listData.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Parent p1 = new Parent();
						if (obdata != null)
						{
							List<Child> child = new ArrayList<Child>();
							for (int j = 0; j < obdata.length; j++)
							{
								Child c1 = new Child();
								if (obdata[j] != null)
								{
									c1.setName(obdata[j].toString());
									// list.add(obdata[j].toString());
								}
								else
								{
									c1.setName("NA");
									// list.add("NA");
								}
								// System.out.println("###"+c1.getName());
								child.add(c1);
							}
							p1.setChildList(child);
						}
						parentTakeaction.add(p1);
					}
					
				}
				
				
				
				
				/*if (krViaTags != null && krViaTags.size() > 0)
				{
					for (Iterator iterator = krViaTags.iterator(); iterator
							.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							id=object[0].toString();
							serchedTagMap.put("id", object[0].toString());
							serchedTagMap.put("KR id", object[1].toString());
							serchedTagMap.put("kr Name", object[2].toString());
							serchedTagMap.put("date", object[3].toString());
							serchedTagMap.put("File Path", object[4].toString());
							serchedTagMap.put("time", object[5].toString());
									
									
						}
					}
				}*/
				setTotal(listData.size());
				return SUCCESS;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			return LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("rawtypes")
	public List krSearchedDataByTags(String tag, String loginUserLevel)
	{
		List listData = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			//String temp = "%" + tags + "%";
			String temp=tags;
			String sql_query = "  select id,krid,krName,date,filestoragepath,time from kr_add where tag='"
					+ temp + "' ";
			System.out.println("sql_query  "+sql_query.toString());
			listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
			
			parentTakeaction = new ArrayList<Parent>();

			if (listData != null)
			{
				for (Iterator it = listData.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
								// list.add(obdata[j].toString());
								System.out.println(obdata[j].toString());
							}
							else
							{
								c1.setName("NA");
								// list.add("NA");
							}
							// System.out.println("###"+c1.getName());
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}
				System.out.println("parentTakeaction:"
						+ parentTakeaction.size());
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return listData;
	}

	
	// code for take action.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String viewKRForTakingAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from kr_history");
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
					if (to > getRecords()) to = getRecords();
					/* COLUMNS FOR QUERY */
					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList(
							"mapped_knowledge_resource_configuration",
							accountID, connectionSpace,
							"knowledge_resource_configuration");
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					fieldNames.add("tags");
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1) query.append(obdata
									.toString()
									+ ",");
							else query.append(obdata.toString());
						}
						i++;
					}
					query.append(" from krcollection");
					System.out.println("QRY IS:::  " + query.toString());
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
					query.append(" limit " + from + "," + to);
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);

					if (data != null)
					{
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0) one.put(fieldNames.get(k)
											.toString(), (Integer) obdata[k]);
									else one.put(fieldNames.get(k).toString(),
											obdata[k].toString());
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
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
			return LOGIN;
		}
		return returnResult;
	}

	// find records for take action
	@SuppressWarnings("rawtypes")
	public List getRecordsForTakeAction(String krId)
	{
		List valueList = new ArrayList();
		if (krId != null)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				StringBuilder queryString = new StringBuilder("");
				queryString.append("select id,krName from kr_history");
				valueList = cbt.executeAllSelectQuery(queryString.toString(),
						connectionSpace);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return valueList;
	}

	public String krTakeActionForUpdate()
	{
	/*	try
		{
			HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
			Map<String, Object> setVariables = new HashMap<String, Object>();
			Map<String, Object> whereCondition = new HashMap<String, Object>();
			String takeADate = DateUtil.getCurrentDateUSFormat();
			String takeATime = DateUtil.getCurrentTime();
			String cmnts = getComments();
			String rating = getKrRating();
			String krID = getKr_ID();
			whereCondition.put("id", getKr_ID());
			setVariables.put("take_action", "1");
			setVariables.put("take_action_date", takeADate);
			setVariables.put("take_action_time", takeATime);
			setVariables.put("comments", cmnts);
			setVariables.put("kr_rating", rating);
			helpdeskHelper.updateTableColomn("kr_history", setVariables,
					whereCondition, connectionSpace);
			addActionMessage("Successfully done !!!!");
			return SUCCESS;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return ERROR;*/
		return SUCCESS;
	}

	// code for NOTIFICATION(CURRENTLY RAW)
	public String findNotification()
	{
		try
		{
			StringBuilder queryString = new StringBuilder("");
			queryString
					.append("select count(*) from kr_history where  take_action='1'");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	public void setsubDept_DeptTags(String level)
	{
		List<GridDataPropertyView> deptList = Configuration
				.getConfigurationData("mapped_dept_config_param", accountID,
						connectionSpace, "", 0, "table_name",
						"dept_config_param");
		subDept_DeptLevelName = new LinkedHashMap<String, String>();
		if (deptList != null && deptList.size() > 0)
		{
			for (GridDataPropertyView gdv : deptList)
			{
				if (gdv.getColomnName().equalsIgnoreCase("deptName"))
				{
					subDept_DeptLevelName.put(gdv.getColomnName(), gdv
							.getHeadingName());
				}
			}
		}
		if (level.equals("2"))
		{
			List<GridDataPropertyView> subdept_deptList = Configuration
					.getConfigurationData("mapped_subdeprtmentconf", accountID,
							connectionSpace, "", 0, "table_name",
							"subdeprtmentconf");
			if (subdept_deptList != null && subdept_deptList.size() > 0)
			{
				for (GridDataPropertyView gdv1 : subdept_deptList)
				{
					if (gdv1.getColomnName().equalsIgnoreCase("subdeptname"))
					{
						subDept_DeptLevelName.put(gdv1.getColomnName(), gdv1
								.getHeadingName());
					}

				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public List getDataFromTable(String tableName, List<String> colmName,
			Map<String, Object> wherClause, Map<String, Object> order,
			SessionFactory connection)
	{
		List Data = null;
		StringBuilder selectTableData = new StringBuilder("");
		selectTableData.append("select ");
		// Set the columns name of a table
		if (colmName != null && !colmName.equals("") && !colmName.isEmpty())
		{
			int i = 1;
			for (String H : colmName)
			{
				if (i < colmName.size()) selectTableData.append(H + " ,");
				else selectTableData.append(H + " from " + tableName);
				i++;
			}
		}
		// Here we get the whole data of a table
		else
		{
			selectTableData.append(" * from " + tableName);
		}
		// Set the values for where clause
		if (wherClause != null && !wherClause.isEmpty())
		{
			if (wherClause.size() > 0)
			{
				selectTableData.append(" where ");
			}
			int size = 1;
			Set set = wherClause.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext())
			{
				Map.Entry me = (Map.Entry) i.next();
				if (size < wherClause.size()) selectTableData
						.append((String) me.getKey() + " = " + me.getValue()
								+ " and ");
				else selectTableData.append((String) me.getKey() + " = '"
						+ me.getValue() + "'");
				size++;
			}
		}
		// Set the value for type of order for getting data in specific order
		if (order != null && !order.isEmpty())
		{
			Set set = order.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry) it.next();
				selectTableData.append(" ORDER BY " + me.getKey() + " "
						+ me.getValue() + "");
			}
		}
		selectTableData.append(";");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connection.openSession();
			transaction = session.beginTransaction();
			Data = session.createSQLQuery(selectTableData.toString()).list();
			transaction.commit();
		}
		catch (Exception ex)
		{
			transaction.rollback();
		}
		finally
		{
			session.close();
		}
		return Data;
	}
	/*
	 * Rahul 9-10-2013 Fetch group for Kr
	 */
	@SuppressWarnings("rawtypes")
	public String fetchGroup()
	{
		if (ValidateSession.checkSession())
		{
			try 
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				String query="select id, group_name from kr_group_data WHERE sub_type_id='"+getGrpID()+"' AND status='Active'";
				List dataList = coi.executeAllSelectQuery(query, connectionSpace);
				jsonArray = new JSONArray();
				if (dataList != null && dataList.size()>0)
				{
					for (Object c : dataList)
					{
						Object[] dataC = (Object[]) c;
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("ID", Integer.toString((Integer) dataC[0]));
						formDetailsJson.put("NAME", dataC[1].toString());
						jsonArray.add(formDetailsJson);
					}
				}
				return  SUCCESS;
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
	public String fetchDocument()
	{
		if (ValidateSession.checkSession())
		{
			try 
			{
				CommonOperInterface coi = new CommonConFactory().createInterface();
				KRActionHelper KRAH= new KRActionHelper();
				docList=new LinkedHashMap<String, String>();
				String loggedDept[]=KRAH.getEmpDetailsByUserName("KR", userName, connectionSpace);
			    if (loggedDept!=null) 
			    {
			    	String query="SELECT count(*) FROM kr_group_data AS krGroup LEFT JOIN kr_sub_group_data AS subGroup ON krGroup.id=subGroup.group_name WHERE subGroup.id='"+getGrpID()+"' AND krGroup.sub_type_id='"+loggedDept[4]+"'";
			    	List data=coi.executeAllSelectQuery(query, connectionSpace);
			    	if (data!=null && data.size()>0) 
			    	{
			    		query="select id, upload1 from kr_upload_data WHERE sub_group_name='"+getGrpID()+"'";
					}
			    	else
			    	{
			    		query="select id, upload1 from kr_upload_data WHERE sub_group_name='"+getGrpID()+"' AND access_type='Public'";
			    	}
			    	List dataList = coi.executeAllSelectQuery(query, connectionSpace);
					
					if (dataList != null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							if (object[0]!=null && object[1]!=null)
							{
								docList.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				return  SUCCESS;
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
	/*
	 * Rahul 10-9-2013 Add Starting Id or Number
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String addStartingId()
	{
		String returnType = ERROR;
		if (ValidateSession.checkSession())
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
System.out.println("zzzzzzzzzzzzzzzzzz inside the Starting Id method  ");
			
			// Read configurationn from table 'kr_starting_id_configuration'
			List<GridDataPropertyView> statusColName = Configuration
					.getConfigurationData("mapped_kr_group_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"kr_starting_id_configuration");

			// Create table 'kr_starting_id_data'
			if (statusColName != null)
			{
				// create table query based on configuration
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes ob1 = null;
				for (GridDataPropertyView gdp : statusColName)
				{
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
				}
				coi.createTable22("kr_starting_id_data", Tablecolumesaaa,
						connectionSpace);

				// Add values to table 'kr_starting_id_data'
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<String> requestParameterNames = Collections
						.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
							&& !paramValue.equalsIgnoreCase("-1")
							&& Parmname != null)
					{
						if (Parmname.equalsIgnoreCase("startingId")) {
						}

						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
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

				// Check existence of startingID
				List dataList = coi.executeAllSelectQuery(
						"select * from kr_starting_id_data ", connectionSpace);
				if (dataList.size() > 0) // Update startingId to table
				{
					boolean status = coi.insertIntoTable("kr_starting_id_data",
							insertData, connectionSpace);
					if (status)
					{
						addActionMessage("KR Starting ID Updated Successfully.");// we
						returnType = ERROR;
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
						returnType = ERROR;
					}
				}
				else
				// First time add startingID to table
				{
					boolean status = coi.insertIntoTable("kr_starting_id_data",
							insertData, connectionSpace);
					if (status)
					{
						addActionMessage("KR Starting ID Added Successfully.");
						returnType = SUCCESS;
					}
					else
					{
						addActionMessage("ERROR: There is some error !");
						returnType = ERROR;
					}
				}
			}
		}
		else
		{
			returnType = LOGIN;
		}
		return returnType;
	}

	@SuppressWarnings("unchecked")
	public String getSubGrp()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) 
				return LOGIN;
			subGrpList = new LinkedHashMap<Integer, String>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query ="SELECT id,sub_group_name FROM kr_sub_group_data WHERE status='Active' AND group_name="+getGrpID();  
			List lowerLevel3 = cbt.executeAllSelectQuery(query, connectionSpace);
			if (lowerLevel3 != null && lowerLevel3.size() > 0)
			{
				jsonArray = new JSONArray();
				for (Iterator it = lowerLevel3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					if (obdata != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("id", obdata[0].toString());
						formDetailsJson.put("groupName", obdata[1].toString());
						jsonArray.add(formDetailsJson);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@SuppressWarnings("rawtypes")
	public String fetchKRName()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			subGrpList = new LinkedHashMap<Integer, String>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query ="SELECT upload1,kr_name,id FROM kr_upload_data WHERE flag='0' AND subGroupName="+subgrpID;  
			List lowerLevel3 = cbt.executeAllSelectQuery(query, connectionSpace);
			if (lowerLevel3 != null && lowerLevel3.size() > 0)
			{
				jsonArray = new JSONArray();
				for (Iterator it = lowerLevel3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					if (obdata != null)
					{
						JSONObject formDetailsJson = new JSONObject();
						formDetailsJson.put("id", obdata[2].toString());
						formDetailsJson.put("subGroupName", obdata[1].toString()+"-"+obdata[0].toString());
						jsonArray.add(formDetailsJson);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	@SuppressWarnings({ "rawtypes" })
	public String getEmployeeName()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
				KRActionHelper KRH=new KRActionHelper();
				String dept[]=KRH.getEmpDetailsByUserName("KR", userName, connectionSpace);
				empList = new LinkedHashMap<Integer, String>();
				if (dept[4]!=null && !dept[4].equalsIgnoreCase(""))
				{
					List data=KRH.getContactFromDept(dept[4],fromDeptId,connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							if (obdata != null)
							{
								empList.put(Integer.parseInt(obdata[0].toString()), obdata[1].toString());
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

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String krUpload()
	{
		String gpName = null;
		String subgpName = null;
		String KRShowFor = new String();
		String KRModifyFor = new String();
		String employee=null;
		String mobOne=null;
		String emailIdOne=null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			StringBuilder query2 = new StringBuilder("");
			StringBuilder query4 = new StringBuilder("");
			
		     StringBuilder queryForEmail = new StringBuilder("");
			  queryForEmail.append("select*from email_configuration_data");
			  List dataCountForEmail = cbt.executeAllSelectQuery(queryForEmail.toString(),
					connectionSpace);
			if(dataCountForEmail!=null && dataCountForEmail.size()>0)
			{
				//emailID=(String) dataCount.get(0);
				for (Iterator it = dataCountForEmail.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					emailID=(String) obdata[3];
					password=(String) obdata[4];
					server=(String) obdata[1];
					port=(String) obdata[2];
				}	
			
			
			query1.append("Select groupName from kr_group_data where id='"
					+ group + "'");
			query2
					.append("Select subGroupName from kr_sub_group_data where groupName='"
							+ group + "'");
			//query3.append("select empName from employee_basic where subdept='"+dept+"' and dept='"+subDept+"'");
			query4.append("select empName,mobOne,emailIdOne from employee_basic where id='"+empName+"'");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
					connectionSpace);
			
			
			if (dataCount != null && dataCount.size() > 0)
			{
				gpName = dataCount.get(0).toString();

			}
			List dataCount2 = cbt.executeAllSelectQuery(query2.toString(),
					connectionSpace);

			if (dataCount2 != null && dataCount2.size() > 0)
			{
				subgpName = dataCount2.get(0).toString();

			}
			/*List dataCount3 = cbt.executeAllSelectQuery(query3.toString(),
					connectionSpace);

			if (dataCount3 != null && dataCount3.size() > 0)
			{
				employee = dataCount3.get(0).toString();

			}*/
			List dataCount4 = cbt.executeAllSelectQuery(query4.toString(),
					connectionSpace);
			if (dataCount4 != null && dataCount4.size() > 0)
			{
				for (Iterator iterator = dataCount4.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					employee =  object[0].toString();
					mobOne=object[1].toString();
					emailIdOne=object[2].toString();
					
				}
			}
			String KRID = gpName + "/" + subgpName + "/"
					+ new RandomNumberGenerator().getRandomNumber(1030, 9000);
			/*if(modifyPrivilege)
			{
				 mPrivilege="Yes";
			}else{
				 mPrivilege="No";
			}*/	
				
			if (getL1Share().equalsIgnoreCase("true")) KRShowFor = KRShowFor
					+ "1" + ",";
			if (getL2Share().equalsIgnoreCase("true")) KRShowFor = KRShowFor
					+ "2" + ",";
			if (getL3Share().equalsIgnoreCase("true")) KRShowFor = KRShowFor
					+ "3" + ",";
			if (getL4Share().equalsIgnoreCase("true")) KRShowFor = KRShowFor
					+ "4" + ",";
			if (getL5Share().equalsIgnoreCase("true")) KRShowFor = KRShowFor
					+ "5" + ",";

			if (getL1Modify() != null && getL1Modify().equalsIgnoreCase("true")) KRModifyFor = KRModifyFor
					+ "1" + ",";
			if (getL2Modify() != null && getL2Modify().equalsIgnoreCase("true")) KRModifyFor = KRModifyFor
					+ "2" + ",";
			if (getL3Modify() != null && getL3Modify().equalsIgnoreCase("true")) KRModifyFor = KRModifyFor
					+ "3" + ",";
			if (getL4Modify() != null && getL4Modify().equalsIgnoreCase("true")) KRModifyFor = KRModifyFor
					+ "4" + ",";
			if (getL5Modify() != null && getL5Modify().equalsIgnoreCase("true")) KRModifyFor = KRModifyFor
					+ "5" + ",";

			System.out.println("notification " + notification);
			String fileExtention = null;
			System.out.println("getBrowseContentType>>>>>>>>>>>  "+getBrowseContentType());
			fileExtention = new KRActionHelper()
					.getFileExtenstion(getBrowseContentType());
			System.out.println("file extension " + fileExtention);
			String fileName = browse.getName().substring(0,
					browse.getName().length() - 4)
					+ fileExtention;
			String storeFilePath = new CreateFolderOs().createUserDir("KRData")
					+ "//" + fileName;
			System.out.println("storeFilePath  " + storeFilePath);
			File theFile = new File(storeFilePath);
			try
			{
				FileUtils.copyFile(browse, theFile);
			}
			catch (IOException e)
			{

				e.printStackTrace();
			}

			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
			TableColumes ob1=new TableColumes();
			  ob1=new TableColumes();
			  ob1.setColumnname("krid");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("groups");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("subgroup");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("filestoragepath");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("tag");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("empName");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("modifyPrivilege");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("Lshare");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			
			  ob1=new TableColumes();
			  ob1.setColumnname("Lmodify");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			
			  ob1=new TableColumes();
			  ob1.setColumnname("notification");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("date");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("time");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("userName");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("lastModify");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("modifiedBy");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			
			  ob1=new TableColumes();
			  ob1.setColumnname("krName");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			  
			  ob1=new TableColumes();
			  ob1.setColumnname("brief");
			  ob1.setDatatype("varchar(255)");
			  ob1.setConstraint("default NULL");
			  Tablecolumesaaa.add(ob1);
			
	boolean status2= cbt.createTable22("kr_add",Tablecolumesaaa,connectionSpace);
			
			if(status2)
			{
			
			InsertDataTable ob4 = null;
			ob4 = new InsertDataTable();
			ob4.setColName("krid");
			ob4.setDataName(KRID);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("groups");
			ob4.setDataName(gpName);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("subgroup");
			ob4.setDataName(subgpName);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("filestoragepath");
			ob4.setDataName(storeFilePath);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("tag");
			ob4.setDataName(tag);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("empName");
			ob4.setDataName(employee);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("modifyPrivilege");
			ob4.setDataName("");
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("Lshare");
			ob4.setDataName(KRShowFor);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("Lmodify");
			ob4.setDataName(KRModifyFor);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("notification");
			ob4.setDataName(notification);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("date");
			ob4.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("time");
			ob4.setDataName(DateUtil.getCurrentTimeHourMin());
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("userName");
			ob4.setDataName(userName);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("lastModify");
			ob4.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("modifiedBy");
			ob4.setDataName(userName);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("krName");
			ob4.setDataName(krName);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("brief");
			ob4.setDataName(brief);
			insertHistory.add(ob4);
			// For SMS and Mail after uploading the KR  Starts
			System.out.println("notification"+notification);
			
		/*	if(KRShowFor!=null && !KRShowFor.equalsIgnoreCase(""))
			{
				String levelforShow[] = KRShowFor.split(",");
				// getting level1....level5 on the basis of selected level
				for (int i = 0; i < levelforShow.length; i++) 
				{
					if(levelforShow[i]!=null && !levelforShow[i].equalsIgnoreCase(""))
					{
						StringBuilder query11 = new StringBuilder("");
						query11.append("select empName,mobOne,emailIdOne from employee_basic where mapLevel='"+levelforShow[i]+"'");
						
						List dataCount11 = cbt.executeAllSelectQuery(query11.toString(),
								connectionSpace);
						Mailtest mt = new Mailtest();
						StringBuilder mailtext = new StringBuilder("");
						StringBuffer msgText = new StringBuffer();
						
						if (dataCount11 != null && dataCount11.size() > 0)
						{
							
							for (Iterator iterator = dataCount11.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								
								mailtext
								.append("Hi "
										+ object[0]
										+ ", Their is an update from Mr/Ms/Mrs."
										+ userName
										+ ",<br/>"
										+ "Kindly view your Account K.R view page for this update with tag: " 
										+ getTag()
										+".");
						
					
								boolean mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),object[2].toString(),"K.R Add Notification",mailtext.toString());
								mailtext.delete(0, mailtext.length());
								if(mailFlag)
					            {
						              System.out.println("**************Mail Sent Successfully**********************");
					            }
								
							
								 String SMSClient ="KR Library Update: By "+userName+","+"Title "+getKrName()+","+"You can search with the tags "+getTag().toString()+".";
								 System.out.println("SMSClient"+SMSClient);
									// SMS On DS mobile NO
									new SendHttpSMS().sendSMS(object[1].toString(), SMSClient);
									
							
							
							
							}

						
						  
					}
							
				}
			}}*/
			// For SMS and Mail after uploading the KR  End
			if(notification.equalsIgnoreCase("1"))
			{
				if (dataCount4 != null && dataCount4.size() > 0)
				{
					String SMSForEmp ="KR Library Update: By "+userName+","+"Title "+getKrName()+","+"You can search with the tags "+getTag().toString()+".";
					 System.out.println("SMSClient"+SMSForEmp);
						// SMS On DS mobile NO
						new SendHttpSMS().sendSMS(mobOne, SMSForEmp);
					
				}	

				String levelforShow[] = KRShowFor.split(",");
				// getting level1....level5 on the basis of selected level
				for (int i = 0; i < levelforShow.length; i++) 
				{
					if(levelforShow[i]!=null && !levelforShow[i].equalsIgnoreCase(""))
					{
						StringBuilder query11 = new StringBuilder("");
						query11.append("select empName,mobOne,emailIdOne from employee_basic where mapLevel='"+levelforShow[i]+"'");
						
						List dataCount11 = cbt.executeAllSelectQuery(query11.toString(),
								connectionSpace);
						StringBuilder mailtext = new StringBuilder("");
						if (dataCount11 != null && dataCount11.size() > 0)
						{
							
							for (Iterator iterator = dataCount11.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								
								mailtext
								.append("Hi "
										+ object[0]
										+ ", Their is an update from Mr/Ms/Mrs."
										+ userName
										+ ",<br/>"
										+ "Kindly view your Account K.R view page for this update with tag: " 
										+ getTag()
										+".");
						
					
								/*boolean mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),object[2].toString(),"K.R Add Notification",mailtext.toString());
								mailtext.delete(0, mailtext.length());
								if(mailFlag)
					            {
						              System.out.println("**************Mail Sent Successfully**********************");
					            }*/
								
							
								 String SMSClient ="KR Library Update: By "+userName+","+"Title "+getKrName()+","+"You can search with the tags "+getTag().toString()+".";
								 System.out.println("SMSClient"+SMSClient);
									// SMS On DS mobile NO
									new SendHttpSMS().sendSMS(object[1].toString(), SMSClient);
							}
					}
				}
			}
				
			}
			else if(notification.equalsIgnoreCase("2"))
			{
				
				if(dataCount4!=null && dataCount4.size()>0)
				{
					Mailtest mt22 = new Mailtest();
					StringBuilder mailtext22 = new StringBuilder("");	
					
					
					mailtext22
					.append("Hi "
							+ employee
							+ ", Their is an update from Mr/Ms/Mrs."
							+ userName
							+ ",<br/>"
							+ "Kindly view your Account K.R view page for this update with tag: " 
							+ getTag()
							+".");
			
		
					boolean mailFlag=mt22.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),emailIdOne,"K.R Add Notification",mailtext22.toString());
					mailtext22.delete(0, mailtext22.length());
					if(mailFlag)
		            {
		            }
				}	
				String levelforShow[] = KRShowFor.split(",");
				// getting level1....level5 on the basis of selected level
				for (int i = 0; i < levelforShow.length; i++) 
				{
					if(levelforShow[i]!=null && !levelforShow[i].equalsIgnoreCase(""))
					{
						StringBuilder query11 = new StringBuilder("");
						query11.append("select empName,mobOne,emailIdOne from employee_basic where mapLevel='"+levelforShow[i]+"'");
						
						List dataCount11 = cbt.executeAllSelectQuery(query11.toString(),
								connectionSpace);
						Mailtest mt = new Mailtest();
						StringBuilder mailtext = new StringBuilder("");
						if (dataCount11 != null && dataCount11.size() > 0)
						{
							
							for (Iterator iterator = dataCount11.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								
								mailtext
								.append("Hi "
										+ object[0]
										+ ", Their is an update from Mr/Ms/Mrs."
										+ userName
										+ ",<br/>"
										+ "Kindly view your Account K.R view page for this update with tag: " 
										+ getTag()
										+".");
						
					
								boolean mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),object[2].toString(),"K.R Add Notification",mailtext.toString());
								mailtext.delete(0, mailtext.length());
								if(mailFlag)
					            {
						              System.out.println("**************Mail Sent Successfully**********************");
					            }
								 String SMSClient ="KR Library Update: By "+userName+","+"Title "+getKrName()+","+"You can search with the tags "+getTag().toString()+".";
								 System.out.println("SMSClient"+SMSClient);
									// SMS On DS mobile NO
									//new SendHttpSMS().sendSMS(object[1].toString(), SMSClient);
							}
					}
							
				}
			}
			}	
			else if(notification.equalsIgnoreCase("3"))
			{
				if (dataCount4 != null && dataCount4.size() > 0)
				{
					String SMSForEmp ="KR Library Update: By "+userName+","+"Title "+getKrName()+","+"You can search with the tags "+getTag().toString()+".";
					 System.out.println("SMSClient"+SMSForEmp);
						// SMS On DS mobile NO
						new SendHttpSMS().sendSMS(mobOne, SMSForEmp);
						
						Mailtest mt22 = new Mailtest();
						StringBuilder mailtext22 = new StringBuilder("");	
						
						
						mailtext22
						.append("Hi "
								+ employee
								+ ", Their is an update from Mr/Ms/Mrs."
								+ userName
								+ ",<br/>"
								+ "Kindly view your Account K.R view page for this update with tag: " 
								+ getTag()
								+".");
				
			
						boolean mailFlag=mt22.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),emailIdOne,"K.R Add Notification",mailtext22.toString());
						mailtext22.delete(0, mailtext22.length());
						if(mailFlag)
			            {
				              System.out.println("   Mail Sent Successfully   ");
			            }
					
				}	
				String levelforShow[] = KRShowFor.split(",");
				// getting level1....level5 on the basis of selected level
				for (int i = 0; i < levelforShow.length; i++) 
				{
					if(levelforShow[i]!=null && !levelforShow[i].equalsIgnoreCase(""))
					{
						StringBuilder query11 = new StringBuilder("");
						query11.append("select empName,mobOne,emailIdOne from employee_basic where mapLevel='"+levelforShow[i]+"'");
						
						List dataCount11 = cbt.executeAllSelectQuery(query11.toString(),
								connectionSpace);
						Mailtest mt = new Mailtest();
						StringBuilder mailtext = new StringBuilder("");
						
						if (dataCount11 != null && dataCount11.size() > 0)
						{
							for (Iterator iterator = dataCount11.iterator(); iterator
									.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								
								mailtext
								.append("Hi "
										+ object[0]
										+ ", Their is an update from Mr/Ms/Mrs."
										+ userName
										+ ",<br/>"
										+ "Kindly view your Account K.R view page for this update with tag: " 
										+ getTag()
										+".");
						
					
								boolean mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),object[2].toString(),"K.R Add Notification",mailtext.toString());
								mailtext.delete(0, mailtext.length());
								if(mailFlag)
					            {
						              System.out.println("**************Mail Sent Successfully**********************");
					            }
								
							
								 String SMSClient ="KR Library Update: By "+userName+","+"Title "+getKrName()+","+"You can search with the tags "+getTag().toString()+".";
								 System.out.println("SMSClient"+SMSClient);
									// SMS On DS mobile NO
									new SendHttpSMS().sendSMS(object[1].toString(), SMSClient);
									
							
							
							
							}

						
						  
					}
							
				}
			}
				
			}	

			boolean status = cbt.insertIntoTable("kr_add", insertHistory,
					connectionSpace);
			if (status)
			{
				addActionMessage("Uploaded  Successfully");
			}
			else
			{
				addActionMessage("OOps there is an error");
			}
			
		}}}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String deleteGridOper() 
	{
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
				cbt.updateTableColomn("kr_add", wherClause,
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
				cbt.deleteAllRecordForId("kr_add", "id", condtIds
						.toString(), connectionSpace);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Oops There is some error in data!");
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String download()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			List listData=null;
			try
			{
				
			    CommonOperInterface cbt = new CommonConFactory().createInterface();
		        String sql_query = " SELECT upload.upload1 FROM kr_upload_data AS upload INNER JOIN kr_share_data AS sh ON upload.id=sh.doc_name where sh.id='"+getFilePath()+"'";
		        listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
				if (listData!=null && listData.size()>0) 
				{
					String files = listData.get(0).toString();
					DownloadConversionAction DA=new DownloadConversionAction();
					if (accessType!=null && accessType.equalsIgnoreCase("Readable")) 
					{
						String receiveFile=DA.documentToPDF(files);
						if (receiveFile!=null && !receiveFile.equalsIgnoreCase("")) 
						{
							fileName=DA.documentToPDF(files);
						}
						else
						{
							fileName = new CreateFolderOs().createUserDir("krUploadDocs")+ "//"+files;
						}
						
					}
					else if (accessType!=null && accessType.equalsIgnoreCase("Editable")) 
					{
						fileName = new CreateFolderOs().createUserDir("krUploadDocs")+ "//"+files;
					}
					
					File file = new File(fileName);

					if (file!=null && file.exists())
					{
						fileInputStream = new FileInputStream(file);
						contentLength = file.length();
						fileName = file.getName();
						boolean tableCreateStatus=DA.createTableFields(connectionSpace);
						if (tableCreateStatus)
						{
							List<InsertDataTable> insertData=null;
							insertData = new ArrayList<InsertDataTable>();
							InsertDataTable ob = null;
							
					    	ob = new InsertDataTable();
							ob.setColName("shareId");
							ob.setDataName(getFilePath());
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
							
							cbt.insertIntoTable("kr_share_download_history",insertData, connectionSpace);
						}
						return "download";
					}
					else
					{
						addActionError("File dose not exist");
						return ERROR;
					}
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String downloadLiabrary()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			List listData=null;
			try
			{
				
				    CommonOperInterface cbt = new CommonConFactory().createInterface();
			        String sql_query = " SELECT upload1 FROM kr_upload_data where id='"+getFilePath()+"'";
			        System.out.println(">>>>>>>>   "+sql_query);
			        listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
					String files = listData.get(0).toString();
					fileName = new CreateFolderOs().createUserDir("krUploadDocs")+ "//"+files;
					File file = new File(fileName);

					if (file.exists())
					{
						fileInputStream = new FileInputStream(file);
						contentLength = file.length();
						fileName = file.getName();
						DownloadConversionAction DA=new DownloadConversionAction();
						boolean tableCreateStatus=DA.createTableFields(connectionSpace);
						if (tableCreateStatus)
						{
							List<InsertDataTable> insertData=null;
							insertData = new ArrayList<InsertDataTable>();
							InsertDataTable ob = null;
							
					    	ob = new InsertDataTable();
							ob.setColName("shareId");
							ob.setDataName(getFilePath());
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
							
							cbt.insertIntoTable("kr_share_download_history",insertData, connectionSpace);
						}
						return "download";
					}
					else
					{
						addActionError("File dose not exist");
						return ERROR;
					}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public String modifyPrvlg()
	{
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append("Select Lshare, Lmodify from kr_add where id='"+id+"'");
		List dataCount = cbt.executeAllSelectQuery(query1.toString(),
				connectionSpace);
		if (dataCount != null && dataCount.size() > 0)
		{
			for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				lShare=(String) object[0];
				lModify=(String) object[1];
			}

		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String beforeTakeAction()
	{
		if (ValidateSession.checkSession())
		{
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder("");
				query.append("SELECT action_req,rating_required,comments_required,frequency,access_type FROM kr_share_data WHERE id ='"+id+"'");
				List dataCountquery = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				System.out.println("Basic Information#  ::::::: "+empName);
				
				String contactName=empName.substring(empName.indexOf("=")+2,empName.indexOf("src")-2);
				empName=contactName;
				if(dataCountquery!=null && dataCountquery.size()>0)
				{
					Object[] ob = (Object[]) dataCountquery.get(0);
					if (ob[0].toString().equalsIgnoreCase("Yes"))
					{
						if (ob[1]!=null && ob[1].toString().equalsIgnoreCase("Yes"))
						{
							ratingFlag=true;
						}
						else
						{
							ratingFlag=false;
						}
						if (ob[2]!=null && ob[2].toString().equalsIgnoreCase("Yes"))
						{
							commentFlag=true;
						}
						else
						{
							commentFlag=false;
						}
						frequency=ob[3].toString();
						accessType=ob[4].toString();
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
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public String krTakeAction()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				boolean doneRemindFlag = false;
				String dueDate = null;
				String actionDate = null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_kr_actiontaken_report", accountID, connectionSpace, "", 0, "table_name",
				        "kr_actionreport_configuration");
				
				if (statusColName != null)
				{
					TableColumes ob1;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype(gdp.getData_type());
						ob1.setConstraint("default NULL");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("kr_share_report_data", Tablecolumesaaa, connectionSpace);
				}
				if (rating != null || comments!=null)
				{
					List data = cbt.executeAllSelectQuery("SELECT due_share_date,due_date FROM kr_share_data WHERE id=" + id, connectionSpace);
					if (data != null && data.size() > 0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0]!=null )
							{
								dueDate = object[0].toString();
							}
							if (object[1]!=null )
							{
								actionDate=object[1].toString();
							}
						}
					}
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					HelpdeskUniversalHelper helpdeskHelper = new HelpdeskUniversalHelper();
					Map<String, Object> setVariables;
					Map<String, Object> whereCondition;
		            
					setVariables = new HashMap<String, Object>();
					whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", id);
					if (frequency!=null && frequency.equalsIgnoreCase("OT"))
					{
						setVariables.put("action_taken", "Done");
					}
					else
					{
						setVariables.put("action_taken", "Recurring");
					}
					if (dueDate!=null && !dueDate.equalsIgnoreCase(""))
					{
						setVariables.put("due_share_date",getUpdateDueDate(frequency,dueDate));
					}
					if (actionDate!=null && !actionDate.equalsIgnoreCase(""))
					{
						setVariables.put("due_date",getUpdateDueDate(frequency,actionDate));
					}
					setVariables.put("action_status", "Done");
					helpdeskHelper.updateTableColomn("kr_share_data", setVariables, whereCondition, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && parmName != null && !paramValue.equalsIgnoreCase("") && !parmName.equals("id") && !parmName.equals("frequency"))
						{
							ob = new InsertDataTable();
							ob.setColName(parmName);
							ob.setDataName(paramValue);
							insertData.add(ob);
						}
					}
					String storeFilePath1 = null;
					if (krUploadFileName != null)
					{
						storeFilePath1 = new CreateFolderOs().createUserDir("krUploadDocs")+ "//"+ getKrUploadFileName();
						String str = storeFilePath1.replace("//", "/");
						if (storeFilePath1 != null)
						{
							File theFile1 = new File(storeFilePath1);
							File newFileName = new File(str);
							if (theFile1 != null)
							{
								try
								{
									FileUtils.copyFile(krUpload, theFile1);
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
									ob.setColName("kr_upload");
									ob.setDataName(getKrUploadFileName());
									insertData.add(ob);
								}
							}
						}
					}
					
					String query = "SELECT kr_starting_id FROM kr_upload_data AS upload INNER JOIN kr_share_data AS krShare ON krShare.doc_name=upload.id WHERE krShare.id="+id;
					List codeData=cbt.executeAllSelectQuery(query, connectionSpace);
					if (codeData!=null && !codeData.isEmpty())
					{
						query="SELECT kr_id FROM kr_share_report_data WHERE kr_sharing_id ="+id+" ORDER BY id DESC LIMIT 0,1";
						List value=cbt.executeAllSelectQuery(query, connectionSpace);
						if (value!=null && !value.isEmpty() && value.get(0)!=null )
						{
							String finalCode=null;
							if (value.get(0).toString().equalsIgnoreCase(codeData.get(0).toString()))
							{
								finalCode=value.get(0).toString()+".1";
							}
							else
							{
								   String a=value.get(0).toString();
								   String b=codeData.get(0).toString();
								   String c=a.substring(0, b.length()+1);
								   String d=a.substring(b.length()+1, a.length());
								   finalCode=c+""+String.valueOf(Integer.parseInt(d)+1);
							}
							ob = new InsertDataTable();
							ob.setColName("kr_id");
							ob.setDataName(finalCode);
							insertData.add(ob);
						}
					}
				
					ob = new InsertDataTable();
					ob.setColName("kr_sharing_id");
					ob.setDataName(id);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("share_date");
					ob.setDataName(getUpdateDueDate(frequency,dueDate));
					insertData.add(ob);
					
					if(actionDate!=null && !actionDate.equalsIgnoreCase(""))
					{
						ob = new InsertDataTable();
						ob.setColName("action_date");
						ob.setDataName(getUpdateDueDate(frequency,actionDate));
						insertData.add(ob);
					}
					
					ob = new InsertDataTable();
					ob.setColName("action_taken");
					ob.setDataName("Done");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_taken_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_by");
					ob.setDataName(new Cryptography().encrypt(userName, DES_ENCRYPTION_KEY));
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("action_taken_time");
					ob.setDataName(DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);

					doneRemindFlag = cbt.insertIntoTable("kr_share_report_data", insertData, connectionSpace);
					//boolean status = beforeMailConfiguration(complID, renameFilePath, actionTaken, connectionSpace);
					if (doneRemindFlag)
					{
						addActionMessage("Successfully done !!!!");
						return SUCCESS;
					}
					else
					{
						return ERROR;
					}
				}
				else
				{
					addActionMessage("No Action Required");
					return SUCCESS;
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
	public String getUpdateDueDate(String frequency,String dueDate)
	{
		if (frequency.equalsIgnoreCase("W"))
			dueDate = DateUtil.getNewDate("day", 7, dueDate);
		else if (frequency.equalsIgnoreCase("M"))
			dueDate = DateUtil.getNewDate("month", 1, dueDate);
		else if (frequency.equalsIgnoreCase("BM"))
			dueDate = DateUtil.getNewDate("day", 15, dueDate);
		else if (frequency.equalsIgnoreCase("Q"))
			dueDate = DateUtil.getNewDate("month", 3, dueDate);
		else if (frequency.equalsIgnoreCase("HY"))
			dueDate = DateUtil.getNewDate("month", 6, dueDate);
		else if (frequency.equalsIgnoreCase("Y"))
			dueDate = DateUtil.getNewDate("year", 1, dueDate);
		else if (frequency.equalsIgnoreCase("D"))
			dueDate = DateUtil.getNewDate("day", 1, dueDate);
		
		return dueDate;
	}
	@SuppressWarnings("unchecked")
	public String beforekrModify()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			List listData=null;
			List listData2=null;
			modifyTagMap = new HashMap<String, String>();
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String sql_query = " select krid from kr_add where id='"+id+"'   ";
			  //  System.out.println("sql_query  "+sql_query.toString());
			    listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
			    String Sql_query2=" select * from kr_add where krid like'"+listData.get(0)+"' ";
			   // System.out.println("sql_query2  "+Sql_query2.toString());
			    listData2 = cbt.executeAllSelectQuery(Sql_query2, connectionSpace);
			    for (Iterator iterator = listData2.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					modifyTagMap.put("KR ID", object[1].toString());
					modifyTagMap.put("Document", object[16].toString());
					modifyTagMap.put("Created On", object[13].toString());
					modifyTagMap.put("Created At", object[12].toString());
					modifyTagMap.put("Last Modified", object[14].toString());
					
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	public String beforeModifyUpload()
	{
	//	System.out.println("inside method"+id);
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String afterModifyUploadKR()
	{    List listData=null;
	     String groups=null;
	     String subgroup=null;
	     String tag=null;
	     String empName=null;
	     String modifyPrivilege=null;
	     String Lshare=null;
	     String Lmodify=null;
	     String username=null;
	     String notification=null;      
	     String krName=null;
	     String brief=null;
	     String kID=null;
		try {
			//System.out.println("browse>>>>>>>>>>>>>>>>>"+browse);
		//	System.out.println("userName>>>>>>>>>>>>>>>> "+userName);
			String kr_id="ddd";
			String[] krid=kr_id.split("/");
			//String kID=krid[0]/krid[1]/krid[2]/krid[3]+
		//	System.out.println("size of the kr id"+krid.length);
			if(krid.length>3)
			{
				kID=krid[0]+"/"+krid[1]+"/"+krid[2]+"/"+(Integer.parseInt(krid[3])+1);
			}	
			else{kID=krid[0]+"/"+krid[1]+"/"+krid[2]+"/"+1;
				}
		//	System.out.println("New kr id >>>>>>>>>>>>>>>> "+kID);
			
			String fileExtention = null;
			fileExtention = new KRActionHelper()
					.getFileExtenstion(getBrowseContentType());
			//System.out.println("file extension " + fileExtention);
			String fileName = browse.getName().substring(0,
					browse.getName().length() - 4)
					+ fileExtention;
			String storeFilePath = new CreateFolderOs().createUserDir("UpdatedKRData")
					+ "//" + fileName;
			//System.out.println("storeFilePath  " + storeFilePath);
			File theFile = new File(storeFilePath);
			try
			{
				FileUtils.copyFile(browse, theFile);
			}
			catch (IOException e)
			{

				e.printStackTrace();
			}
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<InsertDataTable> insertHistory = new ArrayList<InsertDataTable>();
			String sql_query = " select * from kr_add where krid='1'   ";
		//	System.out.println("sql_query  "+sql_query.toString());
			listData = cbt.executeAllSelectQuery(sql_query, connectionSpace);
			for (Iterator iterator = listData.iterator(); iterator
					.hasNext();) {
				Object[] object = (Object[]) iterator.next();
			
			  groups=object[2].toString();
	          subgroup=object[3].toString();
		      tag=object[5].toString();
		      empName=object[6].toString();
		      modifyPrivilege=object[7].toString();
		      Lshare=object[8].toString();
		      Lmodify=object[9].toString();
		      notification=object[10].toString();      
		      username=object[13].toString();
		      krName=object[16].toString();
		      brief=object[17].toString();
			}
			
			InsertDataTable ob4 = null;
			ob4 = new InsertDataTable();
			ob4.setColName("krid");
			ob4.setDataName(kID);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("groups");
			ob4.setDataName(groups);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("subgroup");
			ob4.setDataName(subgroup);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("filestoragepath");
			ob4.setDataName(storeFilePath);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("tag");
			ob4.setDataName(tag);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("empName");
			ob4.setDataName(empName);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("modifyPrivilege");
			ob4.setDataName(modifyPrivilege);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("Lshare");
			ob4.setDataName(Lshare);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("Lmodify");
			ob4.setDataName(Lmodify);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("notification");
			ob4.setDataName(notification);
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("date");
			ob4.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("time");
			ob4.setDataName(DateUtil.getCurrentTimeHourMin());
			insertHistory.add(ob4);

			ob4 = new InsertDataTable();
			ob4.setColName("userName");
			ob4.setDataName(username);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("lastModify");
			ob4.setDataName(DateUtil.getCurrentDateIndianFormat());
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("modifiedBy");
			ob4.setDataName(userName);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("krName");
			ob4.setDataName(krName);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("brief");
			ob4.setDataName(brief);
			insertHistory.add(ob4);
			
			ob4 = new InsertDataTable();
			ob4.setColName("status");
			ob4.setDataName("0");
			insertHistory.add(ob4);
			
			CommonOperInterface cbt2 = new CommonConFactory().createInterface();
			 cbt2.executeAllUpdateQuery("UPDATE kr_add SET status='1' WHERE krid='1'", connectionSpace);
		
		

			boolean status = cbt.insertIntoTable("kr_add", insertHistory,
					connectionSpace);
			if (status)
			{
				addActionMessage("Added Successfully");
			}
			else
			{
				addActionMessage("OOps there is an error");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeKrActionTakenReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				setViewProp2();
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return LOGIN;
			}
			
			
		}
		else
		{
			return LOGIN;
		}
	}

	public void setViewProp2()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				masterViewProp.add(gpv);
				List<GridDataPropertyView> returnResult = Configuration
						.getConfigurationData(
								"mapped_KR_actionTaken_Report",
								accountID, connectionSpace, "", 0,
								"table_name",
								"kr_actionreport_configuration");
				for (GridDataPropertyView gdp : returnResult)
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
						
					masterViewProp.add(gpv);
				}
				
				/*gpv = new GridDataPropertyView();
				gpv.setColomnName("tags");
				gpv.setHeadingName("Searching Tags");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				masterViewProp.add(gpv);*/
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{

		}
	}

	
	@SuppressWarnings("rawtypes")
	public String viewKRReportInGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
			    StringBuilder query1 = new StringBuilder("");
			    StringBuilder query2 = new StringBuilder("");
				query1.append("select count(*) from dummy_kr_share");
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
					if (to > getRecords()) to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select id,");
					List fieldNames = Configuration.getColomnList(
							"mapped_KR_actionTaken_Report",
							accountID, connectionSpace,
							"kr_actionreport_configuration");
					
					
					List<Object> Listhb = new ArrayList<Object>();
					int i = 0;
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null  && !obdata.toString().equals("comment") && !obdata.toString().equals("rating")&& !obdata.toString().equals("id"))
						{
							if (i < fieldNames.size() - 1) 
								query.append(obdata
									.toString()
									+ ",");
							
							else query.append(obdata.toString());
						}
						i++;
					}
					query.append(" FROM dummy_kr_share " );
					/*query2.append(" SELECT kr_add.krid,kractiontaken.Rating,kractiontaken.Comments,kractiontaken.action_date,kractiontaken.action_time,kractiontaken.actionby FROM kractiontaken LEFT JOIN kr_add ON kractiontaken.krid=kr_add.id");
					query2.append(" where actionby");
					query2.append("=");
					query2.append("'");
					query2.append(userName);
					query2.append("'");*/
		
					if (getSearchField() != null && getSearchString() != null
							&& !getSearchField().equalsIgnoreCase("")
							&& !getSearchString().equalsIgnoreCase(""))
					{
						query2.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query2.append(" " + getSearchField() + " = '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query2.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query2.append(" " + getSearchField() + " like '"
									+ getSearchString() + "%'");
						}
						else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query2.append(" " + getSearchField() + " <> '"
									+ getSearchString() + "'");
						}
						else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query2.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "'");
						}
					}
					query2.append(" limit " + from + "," + to);
				//	System.out.println("query2.toString()  "+query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					if (data != null)
					{
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < obdata.length; k++)
							{
								if (obdata[k] != null)
								{
									if (fieldNames.get(k).toString().equalsIgnoreCase("comment")) 
									{
										one.put(fieldNames.get(k).toString(),"Need to modify");
									} 
									else if(fieldNames.get(k).toString().equalsIgnoreCase("rating"))
									{
										one.put(fieldNames.get(k).toString(),"2");
									}
									else if(fieldNames.get(k).toString().equalsIgnoreCase("lastModify"))
									{
										one.put(fieldNames.get(k).toString(),"Download");
									}
									else
									{
										one.put(fieldNames.get(k).toString(),obdata[k].toString());
									}
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
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
			return LOGIN;
		}
		return returnResult;
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}



	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
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

	public Map<String, String> getKrActionTextBox()
	{
		return krActionTextBox;
	}

	public void setKrActionTextBox(Map<String, String> krActionTextBox)
	{
		this.krActionTextBox = krActionTextBox;
	}

	
	public Map<String, String> getKrActionCheckBox()
	{
		return krActionCheckBox;
	}

	public void setKrActionCheckBox(Map<String, String> krActionCheckBox)
	{
		this.krActionCheckBox = krActionCheckBox;
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}

	public File getKrUpload()
	{
		return krUpload;
	}

	public void setKrUpload(File krUpload)
	{
		this.krUpload = krUpload;
	}

	public String getKrUploadContentType()
	{
		return krUploadContentType;
	}

	public void setKrUploadContentType(String krUploadContentType)
	{
		this.krUploadContentType = krUploadContentType;
	}

	public String getKrUploadFileName()
	{
		return krUploadFileName;
	}

	public void setKrUploadFileName(String krUploadFileName)
	{
		this.krUploadFileName = krUploadFileName;
	}

	@SuppressWarnings("rawtypes")
	public List getKrViaTags()
	{
		return krViaTags;
	}

	@SuppressWarnings("rawtypes")
	public void setKrViaTags(List krViaTags)
	{
		this.krViaTags = krViaTags;
	}

	public Map<String, String> getSerchedTagMap()
	{
		return serchedTagMap;
	}

	public void setSerchedTagMap(Map<String, String> serchedTagMap)
	{
		this.serchedTagMap = serchedTagMap;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDownloadPath()
	{
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath)
	{
		this.downloadPath = downloadPath;
	}

	public String getKrName()
	{
		return krName;
	}

	public void setKrName(String krName)
	{
		this.krName = krName;
	}

	public String getKrRating()
	{
		return krRating;
	}

	public void setKrRating(String krRating)
	{
		this.krRating = krRating;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public String getKr_ID()
	{
		return kr_ID;
	}

	public void setKr_ID(String kr_ID)
	{
		this.kr_ID = kr_ID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}



	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}

	public Map<Integer, String> getSubDeptList()
	{
		return subDeptList;
	}

	public void setSubDeptList(Map<Integer, String> subDeptList)
	{
		this.subDeptList = subDeptList;
	}

	public Map<String, String> getSubDept_DeptLevelName()
	{
		return subDept_DeptLevelName;
	}

	public void setSubDept_DeptLevelName(
			Map<String, String> subDept_DeptLevelName)
	{
		this.subDept_DeptLevelName = subDept_DeptLevelName;
	}

	public Map<Integer, String> getEmpList()
	{
		return empList;
	}

	public void setEmpList(Map<Integer, String> empList)
	{
		this.empList = empList;
	}

	public List<ConfigurationUtilBean> getKrDirTextBox()
	{
		return krDirTextBox;
	}

	public void setKrDirTextBox(List<ConfigurationUtilBean> krDirTextBox)
	{
		this.krDirTextBox = krDirTextBox;
	}

	public InputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public String getDownloadFile()
	{
		return downloadFile;
	}

	public void setDownloadFile(String downloadFile)
	{
		this.downloadFile = downloadFile;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}
	


	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}



	public String getSubGroup()
	{
		return subGroup;
	}

	public void setSubGroup(String subGroup)
	{
		this.subGroup = subGroup;
	}

	public Map<Integer, String> getSubGrpList()
	{
		return subGrpList;
	}

	public void setSubGrpList(Map<Integer, String> subGrpList)
	{
		this.subGrpList = subGrpList;
	}

	public String getGrpID()
	{
		return grpID;
	}

	public void setGrpID(String grpID)
	{
		this.grpID = grpID;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	
	public String getDeptID()
	{
		return deptID;
	}

	public void setDeptID(String deptID)
	{
		this.deptID = deptID;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}
	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public File getBrowse()
	{
		return browse;
	}

	public void setBrowse(File browse)
	{
		this.browse = browse;
	}

	public String getBrowseContentType()
	{
		return BrowseContentType;
	}

	public void setBrowseContentType(String browseContentType)
	{
		BrowseContentType = browseContentType;
	}
	public String getL1Share()
	{
		return l1Share;
	}

	public void setL1Share(String share)
	{
		l1Share = share;
	}

	public String getL2Share()
	{
		return l2Share;
	}

	public void setL2Share(String share)
	{
		l2Share = share;
	}

	public String getL3Share()
	{
		return l3Share;
	}

	public void setL3Share(String share)
	{
		l3Share = share;
	}

	public String getL4Share()
	{
		return l4Share;
	}

	public void setL4Share(String share)
	{
		l4Share = share;
	}

	public String getL5Share()
	{
		return l5Share;
	}

	public void setL5Share(String share)
	{
		l5Share = share;
	}

	public String getL1Modify()
	{
		return l1Modify;
	}

	public void setL1Modify(String modify)
	{
		l1Modify = modify;
	}

	public String getL2Modify()
	{
		return l2Modify;
	}

	public void setL2Modify(String modify)
	{
		l2Modify = modify;
	}

	public String getL3Modify()
	{
		return l3Modify;
	}

	public void setL3Modify(String modify)
	{
		l3Modify = modify;
	}

	public String getL4Modify()
	{
		return l4Modify;
	}

	public void setL4Modify(String modify)
	{
		l4Modify = modify;
	}

	public String getL5Modify()
	{
		return l5Modify;
	}

	public void setL5Modify(String modify)
	{
		l5Modify = modify;
	}

	public String getNotification()
	{
		return notification;
	}

	public void setNotification(String notification)
	{
		this.notification = notification;
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public List<ConfigurationUtilBean> getKrActionFileField()
	{
		return krActionFileField;
	}

	public void setKrActionFileField(List<ConfigurationUtilBean> krActionFileField)
	{
		this.krActionFileField = krActionFileField;
	}
	public String getAccessType()
	{
		return accessType;
	}

	public void setAccessType(String accessType)
	{
		this.accessType = accessType;
	}
	public String getFormater()
	{
		return formater;
	}

	public void setFormater(String formater)
	{
		this.formater = formater;
	}

	

	public String getLShare()
	{
		return lShare;
	}

	public void setLShare(String share)
	{
		lShare = share;
	}

	public String getLModify()
	{
		return lModify;
	}

	public void setLModify(String modify)
	{
		lModify = modify;
	}

	public String getBrief()
	{
		return brief;
	}

	public void setBrief(String brief)
	{
		this.brief = brief;
	}

	public String getKrComment() {
		return krComment;
	}

	public void setKrComment(String krComment) {
		this.krComment = krComment;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Map<String, String> getModifyTagMap() {
		return modifyTagMap;
	}

	public void setModifyTagMap(Map<String, String> modifyTagMap) {
		this.modifyTagMap = modifyTagMap;
	}

	public List<Parent> getParentTakeaction() {
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction) {
		this.parentTakeaction = parentTakeaction;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFromDeptId()
	{
		return fromDeptId;
	}
	public void setFromDeptId(String fromDeptId)
	{
		this.fromDeptId = fromDeptId;
	}

	public Map<String, String> getDocList()
	{
		return docList;
	}

	public void setDocList(Map<String, String> docList)
	{
		this.docList = docList;
	}

	public boolean isRatingFlag()
	{
		return ratingFlag;
	}

	public void setRatingFlag(boolean ratingFlag)
	{
		this.ratingFlag = ratingFlag;
	}

	public boolean isCommentFlag()
	{
		return commentFlag;
	}

	public void setCommentFlag(boolean commentFlag)
	{
		this.commentFlag = commentFlag;
	}

	public String getRating()
	{
		return rating;
	}

	public void setRating(String rating)
	{
		this.rating = rating;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public String getSubgrpID() {
		return subgrpID;
	}

	public void setSubgrpID(String subgrpID) {
		this.subgrpID = subgrpID;
	}

}
