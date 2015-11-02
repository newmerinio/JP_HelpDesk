package com.Over2Cloud.ctrl.wfpm.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.asset.AssetCommonAction;
import com.Over2Cloud.ctrl.asset.common.GenericWriteExcel;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Excel_Upload_Download extends ActionSupport
{
	static final Log											log							= LogFactory.getLog(AssetCommonAction.class);
	Map																		session					= ActionContext.getContext().getSession();
	SessionFactory												connectionSpace	= (SessionFactory) session.get("connectionSpace");
	String																userName				= (String) session.get("uName");
	String																accountID				= (String) session.get("accountid");
	String cId = new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, userName, connectionSpace)[0];
	private String[]											columnNames;
	private String												downloadType		= null;
	private String												moduleName			= null;
	private String												tableName				= null;
	private String												tableAllis			= null;
	private String												excelName				= null;
	private String												mappedTableName	= null;
	private String												masterTableName	= null;
	private List<ConfigurationUtilBean>		columnMap				= new ArrayList<ConfigurationUtilBean>();
	private File													excel;
	public Map<String, Object>						columnNameMap		= null;
	private String												excelContentType;
	private String												excelFileName;
	private ArrayList<ArrayList<String>>	excelDataList		= new ArrayList<ArrayList<String>>();
	private String												level						= null;
	private String												productMappedId	= null;
	private InputStream	fileInputStream;

	/*
	 * Anoop, 20-8-2013 Get list of column names to show on confirmation page for
	 * downloading
	 */
	public String currentColumn()
	{
		//System.out.println("CHKKK true  currentColumn()");
		
		String returnResult = ERROR;
		//System.out.println(">>>>>>");
		if (ValidateSession.checkSession())
		{
			try
			{
				/*
				 * These ladder if/else blocks will determine that which module data is
				 * being uploaded
				 */
				////System.out.println("level:" + level);
				if (level.equalsIgnoreCase("associate") && !level.equalsIgnoreCase("undefined")) // In case of excel
				// upload/download for
				// associate
				{

					////////System.out.println("iffffffffffffffffffffffffffffffffffffffffffff"
					// );
					// Start: Read all configuration tables
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<GridDataPropertyView> columnList = new ArrayList<GridDataPropertyView>();
					//System.out.println(">>>>>>>>>>>  "+masterTableName);
					String[] masterTable = masterTableName.split("#");
					// //////System.out.println("masterTableName:"+masterTableName);
					// //////System.out.println("masterTable:"+masterTable.length);

					StringBuilder empuery = new StringBuilder("");
					empuery.append("select table_name from " + getMappedTableName());
					List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);

					for (Iterator it = empData.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (obdata.toString().equalsIgnoreCase("associate_basic_configuration"))
							{
								List<GridDataPropertyView> data = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name",
										masterTable[0].trim());
								if (data != null && data.size() > 0)
								{
									columnList.addAll(data);
								}
							}

							/*
							 * else if (obdata.toString().equalsIgnoreCase(
							 * "associate_offering_configuration")) {
							 * List<GridDataPropertyView> data = Configuration
							 * .getConfigurationData(mappedTableName, accountID,
							 * connectionSpace, "", 0, "table_name", masterTable[1] .trim());
							 * if (data != null && data.size() > 0) { columnList.addAll(data);
							 * } } else if (obdata.toString().equalsIgnoreCase(
							 * "associate_contact_configuration")) {
							 * List<GridDataPropertyView> data = Configuration
							 * .getConfigurationData(mappedTableName, accountID,
							 * connectionSpace, "", 0, "table_name", masterTable[2] .trim());
							 * int index = 0; for (GridDataPropertyView gv : data) { if
							 * (gv.getColomnName().equalsIgnoreCase("associateName")) { break;
							 * } index++; } data.remove(index);// remove field 'associateName'
							 * from excel so // as to avoid duplicate if (data != null &&
							 * data.size() > 0) { columnList.addAll(data); } }
							 */
						}
					}
					// End: Read all configuration tables

					// Start: iterate all data and build a list to show checkbox at
					// confirm page
					for (GridDataPropertyView gdp1 : columnList)
					{
						String firstValue, secondValue = null;
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						// //////System.out.println(">>>>>>>>:"+gdp1.getColomnName());
						if (false)// left for testing purpose
						{

						}
						else if (!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("time"))
						{
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							columnMap.add(obj);
						}
					}
					// End: iterate all data and build a list to show checkbox at confirm
					// page
					session.put("columnMap", columnMap);
					// //////System.out.println("===============================>>>>>>>>>:"+
					// columnMap.size());
					returnResult = SUCCESS;

				}
				else if (level.equalsIgnoreCase("client") && !level.equalsIgnoreCase("undefined")) // In case of excel
				// upload/download for
				// client
				{
					// //////System.out.println("inside else if");
					// Start: Read all configuration tables
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					List<GridDataPropertyView> columnList = new ArrayList<GridDataPropertyView>();
					//client_basic_configuration#client_offering_configuration#client_contact_configuration	
					String[] masterTable = masterTableName.split("#");
					// //////System.out.println("masterTableName:"+masterTableName);
					// //////System.out.println("masterTable:"+masterTable.length);
//mapped:::::: mapped_client_configuration
					StringBuilder empuery = new StringBuilder("");
					empuery.append("select table_name from " + getMappedTableName());
					List empData = cbt.executeAllSelectQuery(empuery.toString(), connectionSpace);

					for (Iterator it = empData.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (obdata.toString().equalsIgnoreCase("client_basic_configuration"))
							{
								List<GridDataPropertyView> data = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name",
										masterTable[0].trim());
								if (data != null && data.size() > 0)
								{
									columnList.addAll(data);
								}
							}
							/*
							 * else if
							 * (obdata.toString().equalsIgnoreCase("client_offering_configuration"
							 * )) { List<GridDataPropertyView> data =
							 * Configuration.getConfigurationData(mappedTableName, accountID,
							 * connectionSpace, "", 0, "table_name",masterTable[1].trim());
							 * if(data != null && data.size() > 0) { columnList.addAll(data);
							 * } } else if
							 * (obdata.toString().equalsIgnoreCase("client_contact_configuration"
							 * )) { List<GridDataPropertyView> data =
							 * Configuration.getConfigurationData(mappedTableName, accountID,
							 * connectionSpace, "", 0, "table_name",masterTable[2].trim());
							 * int index = 0; for(GridDataPropertyView gv:data) {
							 * if(gv.getColomnName().equalsIgnoreCase("clientName")) { break;
							 * } index++; } data.remove(index);//remove field 'clientName'
							 * from excel so as to avoid duplicate if(data != null &&
							 * data.size() > 0) { columnList.addAll(data); } }
							 */
						}
					}// end for loop
					// End: Read all configuration tables

					// Start: iterate all data and build a list to show checkbox at
					// confirm page
					for (GridDataPropertyView gdp1 : columnList)
					{
						String firstValue, secondValue = null;
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						//System.out.println(gdp1.getHeadingName()+ " >>>>>>>>:"+gdp1.getColomnName());
						if (false)// left for testing purpose
						{
									//System.out.println("false Test in Excel_upload_download");
						}
						else if (!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("time"))
						{	//System.out.println(gdp1.getHeadingName()+ " &&&&&&&&&:"+gdp1.getColomnName());
						
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey(gdp1.getColomnName());
							obj.setValue(gdp1.getHeadingName());
							columnMap.add(obj);
						}
					}
					// End: iterate all data and build a list to show checkbox at confirm
					// page
					session.put("columnMap", columnMap);
					//System.out.println("===============================>>>>>>>>>:"+ columnMap.size());
					returnResult = SUCCESS;

				}
				else if
				(!level.equalsIgnoreCase("undefined") && Integer.parseInt(level.trim()) >= 1 && Integer.parseInt(level.trim()) <= 5) // in
																																															// case
																																															// of
																																															// excel
				// upload/download for
				// offering
				{
					String accountID = (String) session.get("accountid");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					List<GridDataPropertyView> columnList = Configuration.getConfigurationData(getMappedTableName(), accountID, connectionSpace, "", 0, "table_name",
							getMasterTableName());
					////////System.out.println("22222222222222222222222222222:"+columnList.size
					// (
					// ));
					if (columnList != null && columnList.size() > 0)
					{
						if (downloadType != null && downloadType.equals("downloadExcel"))
						{

						}
						else
						// Excel format download
						{

							String offeringColumn = "";
							if (level.equalsIgnoreCase("1")) offeringColumn = "";
							if (level.equalsIgnoreCase("2")) offeringColumn = "verticalname";
							if (level.equalsIgnoreCase("3")) offeringColumn = "offeringname";
							if (level.equalsIgnoreCase("4")) offeringColumn = "subofferingname";
							if (level.equalsIgnoreCase("5")) offeringColumn = "variantname";

							for (GridDataPropertyView gdp1 : columnList)
							{
								String firstValue, secondValue = null;
								ConfigurationUtilBean obj = new ConfigurationUtilBean();
								// //////System.out.println(">>>>>>>>:"+gdp1.getColomnName());
								if (false)
								{
									firstValue = new AssetCommonAction().getFieldName("mapped_spare_master", "spare_master", "spare_name");
									if (gdp1.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey("spare_name");
									obj.setValue(firstValue);
									columnMap.add(obj);
								}
								else if (!gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("time")
										&& !gdp1.getColomnName().equals(offeringColumn))
								{
									if (gdp1.getMandatroy().equals("1"))
									{
										obj.setMandatory(true);
									}
									else
									{
										obj.setMandatory(false);
									}
									obj.setKey(gdp1.getColomnName());
									obj.setValue(gdp1.getHeadingName());
									columnMap.add(obj);
								}
							}
						}
						session.put("columnMap", columnMap);
					}
					returnResult = SUCCESS;

				}

			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
						+ "Problem in Over2Cloud  method currentColumn of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	/*
	 * Anoop, 22-8-2013 Create excel and download it for confirmation and data
	 * part
	 */
	public String downloadAction()
	{
		//System.out.println(" chk ::  downloadAction client");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				keyList = Arrays.asList(columnNames);
				List<ConfigurationUtilBean> tempMap = new ArrayList<ConfigurationUtilBean>();
				tempMap = (List<ConfigurationUtilBean>) session.get("columnMap");

				StringBuilder query2 = new StringBuilder("");
				StringBuilder query3 = new StringBuilder("");
				
				// Removing 'columnMap' from session
				if (session.get("columnMap") != null)
				{
					List dataList = null;

					for (ConfigurationUtilBean cub : tempMap)
					{
						if (keyList.contains(cub.getKey()))
						{
							//System.out.println(cub.getValue());
							titleList.add(cub.getValue());
						}
					}
					//System.out.println("Titles :::::::::::::; "+ titleList);

					if (downloadType.equals("downloadExcel"))
					{
						if (keyList != null && keyList.size() > 0)
						{
							query2.append("select ");
							int i = 0;
							for (Iterator it = keyList.iterator(); it.hasNext();)
							{
								Object obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < keyList.size() - 1)
									{
										if (obdata.toString().endsWith("state"))
										{
											query2.append(" stt.stateName, ");
											query3.append(" left join state_data as stt on stt.id = ");
											query3.append(tableAllis);
											query3.append(".state ");
										}
										else if (obdata.toString().endsWith("associateCategory"))
										{
											query2.append("asct.associate_category,");
											query3.append("left join associatecategory as asct on asct.id = ");
											query3.append(tableAllis);
											query3.append(".associateCategory ");
										}
										else if (obdata.toString().endsWith("associateType"))
										{
											query2.append("ast.associateType,");
											query3.append("left join associatetype as ast on ast.id = ");
											query3.append(tableAllis);
											query3.append(".associateType ");
										}
										else if (obdata.toString().endsWith("associateSubType"))
										{
											query2.append("asst.associateSubType,");
											query3.append("left join associatesubtype as asst on asst.id = ");
											query3.append(tableAllis);
											query3.append(".associateSubType ");
										}
										else if (obdata.toString().endsWith("sourceName") || obdata.toString().endsWith("sourceMaster"))
										{
											query2.append("src.sourceName,");
											query3.append("left join sourcemaster as src on src.id = ");
											query3.append(tableAllis);
											if (tableAllis.equalsIgnoreCase("cbd"))
											{
												query3.append(".sourceMaster ");
											}
											else if (tableAllis.equalsIgnoreCase("abd"))
											{
												query3.append(".sourceName ");
											}
											else
											{
												query3.append(".sourceName ");
											}
										}
										else if (obdata.toString().endsWith("industry"))
										{
											query2.append("ind.industry,");
											query3.append("left join industrydatalevel1 as ind on ind.id = ");
											query3.append(tableAllis);
											query3.append(".industry ");
										}
										else if (obdata.toString().endsWith("subIndustry"))
										{
											query2.append("sind.subIndustry,");
											query3.append("left join subindustrydatalevel2 as sind on sind.id = ");
											query3.append(tableAllis);
											query3.append(".subIndustry ");
										}
										else if (obdata.toString().endsWith("status"))
										{
											if (tableAllis.equalsIgnoreCase("cbd"))
											{
												query2.append("cstn.statusName,");
												query3.append("left join client_status as cstn on cstn.id = ");
												query3.append(tableAllis);
												query3.append(".status ");
											}
											else if (tableAllis.equalsIgnoreCase("abd"))
											{
												query2.append("astn.statusname,");
												query3.append("left join associatestatus as astn on astn.id = ");
												query3.append(tableAllis);
												query3.append(".status ");
											}
										}
										else if (obdata.toString().endsWith("name") || obdata.toString().endsWith("location"))
										{
											query2.append("loc.name,");
											query3.append("left join location as loc on loc.id = ");
											query3.append(tableAllis);
											if (tableAllis.equalsIgnoreCase("lbd"))
											{
												query3.append(".name ");
											}
											else
											{
												query3.append(".location ");
											}
										}
										else query2.append(obdata.toString() + ",");
									}
									else
									{
										if (obdata.toString().endsWith("state"))
										{
											query2.append(" stt.stateName ");
											query3.append(" left join state_data as stt on stt.id = ");
											query3.append(tableAllis);
											query3.append(".state ");
										}
										else if (obdata.toString().endsWith("associateCategory"))
										{
											query2.append("asct.associate_category ");
											query3.append("left join associatecategory as asct on asct.id = ");
											query3.append(tableAllis);
											query3.append(".associateCategory ");
										}
										else if (obdata.toString().endsWith("associateType"))
										{
											query2.append("ast.associateType ");
											query3.append("left join associatetype as ast on ast.id = ");
											query3.append(tableAllis);
											query3.append(".associateType ");
										}
										else if (obdata.toString().endsWith("associateSubType"))
										{
											query2.append("asst.associateSubType ");
											query3.append("left join associatesubtype as asst on asst.id = ");
											query3.append(tableAllis);
											query3.append(".associateSubType ");
										}
										else if (obdata.toString().endsWith("sourceName") || obdata.toString().endsWith("sourceMaster"))
										{
											query2.append("src.sourceName ");
											query3.append("left join sourcemaster as src on src.id = ");
											query3.append(tableAllis);
											if (tableAllis.equalsIgnoreCase("cbd"))
											{
												query3.append(".sourceMaster ");
											}
											else if (tableAllis.equalsIgnoreCase("abd"))
											{
												query3.append(".sourceName ");
											}
											else
											{
												query3.append(".sourceName ");
											}
										}
										else if (obdata.toString().endsWith("industry"))
										{
											query2.append("ind.industry ");
											query3.append("left join industrydatalevel1 as ind on ind.id = ");
											query3.append(tableAllis);
											query3.append(".industry ");
										}
										else if (obdata.toString().endsWith("subIndustry"))
										{
											query2.append("sind.subIndustry ");
											query3.append("left join subindustrydatalevel2 as sind on sind.id = ");
											query3.append(tableAllis);
											query3.append(".subIndustry ");
										}
										else if (obdata.toString().endsWith("status"))
										{
											if (tableAllis.equalsIgnoreCase("cbd"))
											{
												query2.append("cstn.statusName ");
												query3.append("left join client_status as cstn on cstn.id = ");
												query3.append(tableAllis);
												query3.append(".status ");
											}
											else if (tableAllis.equalsIgnoreCase("abd"))
											{
												query2.append("astn.statusname ");
												query3.append("left join associatestatus as astn on astn.id = ");
												query3.append(tableAllis);
												query3.append(".status ");
											}
										}
										else if (obdata.toString().endsWith("name") || obdata.toString().endsWith("location"))
										{
											if (tableAllis.equalsIgnoreCase("lbd"))
											{
												query2.append("loc.name ");
												query3.append("left join location as loc on loc.id = ");
												query3.append(tableAllis);
												query3.append(".name ");
											}
											else
											{
												query2.append("loc.name ");
												query3.append("left join location as loc on loc.id = ");
												query3.append(tableAllis);
												query3.append(".location ");
											}
										}
										else query2.append(obdata.toString());
									}
								}
								i++;
							}
							query2.append(" from " + tableName + " as " + tableAllis);
							query2.append(" ");
							query2.append(query3.toString());
							query2.append(" ");
							/*query2.append("where ");
							query2.append(tableAllis);
							query2.append(".userName = '");
							query2.append(userName);
							query2.append("' ");*/

							//System.out.println("query2.toString():" + query2.toString());
							dataList = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								returnResult = new GenericWriteExcel().createExcel("WFPM", excelName, dataList, titleList, null, excelName);
								if (session.containsKey("columnMap"))
								{
									session.remove("columnMap");
								}
							}

						}
					}
					else if (downloadType.equals("uploadExcel"))
					{
						//System.out.println("else part :; "+downloadType);
						returnResult = new GenericWriteExcel().createExcel("WFPM", excelName, dataList, titleList, "c:/", excelName);
					}
					//System.out.println("returnResult:"+returnResult);
					File file = new File(returnResult);
					//System.out.println("file>>>>>>>>>>>>>>>>>>>" + file);
					//System.out.println("file>>>>>>>>>>>>>>>>>>>" + file.getAbsolutePath());
					
					if (file.exists())
					{
						//System.out.println("existtttttttttttttttttttttttt");
						fileInputStream = new FileInputStream(file);
						//System.out.println(file.getName());
						excelFileName=file.getName();
					}
					else
					{
						addActionError("File does not exist");
					}
					session.remove("columnMap");
				}
				returnResult = SUCCESS;
				//System.out.println("in Success "+returnResult);
			}
			catch (Exception e)
			{
				//System.out.println("in catch block "+returnResult);
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
						+ "Problem in Over2Cloud  method downloadAction of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		//System.out.println("before return "+returnResult);
		return returnResult;
	}

	/*
	 * Anoop, 24-'8-2013 Create or update record
	 */
	private String insertRecordAndGetKey(String tableName, String fieldName, String fieldValue, String condField, String condValue, boolean needToInsert)
	{
		String id = null;
		String selectQuery = "select id from " + tableName + " where " + fieldName + "='" + fieldValue + "' ";
		if (condField != null && !condField.equalsIgnoreCase("")) selectQuery += " and " + condField + "='" + condValue + "' ";
		// //////System.out.println("selectQuery:"+selectQuery);
		String insertQuery = "insert into " + tableName + "(" + fieldName + ") values('" + fieldValue + "')";
		// //////System.out.println("insertQuery:"+insertQuery);

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connectionSpace.openSession();
			transaction = session.beginTransaction();
			Query query1 = session.createSQLQuery(selectQuery);
			List list = query1.list();
			transaction.commit();
			if (list != null && list.size() > 0)
			{
				id = list.get(0).toString();
			}
			else if (needToInsert)
			{
				// insert record
				session = connectionSpace.openSession();
				transaction = session.beginTransaction();
				query1 = session.createSQLQuery(insertQuery);
				id = String.valueOf(query1.executeUpdate());
				transaction.commit();

				// fetch record
				session = connectionSpace.openSession();
				transaction = session.beginTransaction();
				query1 = session.createSQLQuery(selectQuery);
				list = query1.list();
				transaction.commit();
				if (list != null && list.size() > 0)
				{
					id = list.get(0).toString();
				}
			}
			//			session.close();
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " "
					+ "Problem in Over2Cloud  method insertRecordAndGetKey of class " + getClass(), e);
			e.printStackTrace();
			e.printStackTrace();
		}
		return id;
	}

	public String uploadOffering()
	{

		String returnResult = ERROR;
		try
		{
			readExcel();// read data from excel
			int tableColumnSize = excelDataList.get(0).size();
			InsertDataTable ob = null;
			ArrayList<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			CommonOperInterface coi = new CommonConFactory().createInterface();

			/*
			 * list 'excelDataList' will hold data read from excel in a 2D list where
			 * first list in the containing list will be header values of table where
			 * data is to be inserted
			 */
			if (excelDataList.size() > 1) // in case when excel has minimum one row
			// data
			{
				if (level.equalsIgnoreCase("associate"))// In case of client excel
				// upload
				{
					ArrayList<String> tempList = excelDataList.get(0);// third row value
					// of excel sheet
					// i.e excel headers
					boolean flag = true;
					String id = null;

					// In this for loop iteration if fields with mandatory comment as at
					// the end of if block, has to be given otherwise entire record will
					// be discarded
					for (ArrayList<String> list : excelDataList)
					{
						if (flag) // to escape first list because these are excel headers
						{
							flag = false;
							continue;
						}
						boolean isFirst = true, isFirstForAssociateType = true;
						String associateId = null;

						for (int j = 0; j < tableColumnSize; j++)
						{
							// //////System.out.println("inner for");

							// state or location
							// **********************************************
							// *******************
							if ((tempList.get(j).equalsIgnoreCase("state") || tempList.get(j)
									.equalsIgnoreCase("location"))
									&& isFirst)
							{
								isFirst = false;
								if (tempList.contains("state")
										&& tempList.contains("location")
										&& !list.get(tempList.indexOf("state")).trim()
												.equalsIgnoreCase("NA")
										&& !list.get(tempList.indexOf("location")).trim()
												.equalsIgnoreCase("NA"))// mandatory
								{
									// //////System.out.println("state");
									String id1 = insertRecordAndGetKey("state_data", "stateName",
											list.get(j).trim(), "", "", true);

									id = insertRecordAndGetKey("location", "name", list.get(
											tempList.indexOf("location")).trim(), "stateName", id1,
											false);
									if (id == null)
									{
										int size = coi.executeAlter(
												"insert into location(name,stateName) values('"
														+ list.get(tempList.indexOf("location")).trim()
														+ "','" + id1 + "')", connectionSpace);
										if (size < 1) continue;
										id = insertRecordAndGetKey("location", "name", list.get(
												tempList.indexOf("location")).trim(), "stateName", id1,
												false);
									}

									if (id != null)
									{
										ob = new InsertDataTable();
										ob.setColName("state");
										ob.setDataName(id1);
										insertData.add(ob);

										ob = new InsertDataTable();
										ob.setColName("location");
										ob.setDataName(id);
										insertData.add(ob);
									}
								}
							}

							// associate type or associate sub type
							// ***********************************************
							else if ((tempList.get(j).equalsIgnoreCase("associateType") || tempList
									.get(j).equalsIgnoreCase("associateSubType"))
									&& isFirstForAssociateType)
							{
								isFirstForAssociateType = false;
								if (tempList.contains("associateType")
										&& !tempList.contains("associateSubType")
										&& !list.get(tempList.indexOf("associateType")).trim()
												.equalsIgnoreCase("NA"))
								// && !list.get(tempList.indexOf("associateSubType")).trim().
								// equalsIgnoreCase("NA"))//mandatory
								{
									// //////System.out.println("associateType");
									id = insertRecordAndGetKey("associatetype", "associateType",
											list.get(tempList.indexOf("associateType")).trim(), "",
											"", true);
									if (id != null)
									{
										ob = new InsertDataTable();
										ob.setColName("associateType");
										ob.setDataName(id);
										insertData.add(ob);
									}
								}
								else if (tempList.contains("associateType")
										&& tempList.contains("associateSubType")
										&& !list.get(tempList.indexOf("associateType")).trim()
												.equalsIgnoreCase("NA"))
								// && !list.get(tempList.indexOf("associateSubType")).trim().
								// equalsIgnoreCase("NA"))//mandatory
								{
									// //////System.out.println("associateType");
									String id1 = insertRecordAndGetKey("associatetype",
											"associateType", list.get(
													tempList.indexOf("associateType")).trim(), "", "",
											true);

									id = insertRecordAndGetKey("associatesubtype",
											"associateSubType", list.get(
													tempList.indexOf("associateSubType")).trim(),
											"associateType", id1, false);
									if (id == null)
									{
										coi.executeAlter(
												"insertinto associatesubtype(associateSubType,associateType) values('"
														+ list.get(tempList.indexOf("associateSubType"))
																.trim() + "','" + id1 + "')", connectionSpace);
										id = insertRecordAndGetKey("associatesubtype",
												"associateSubType", list.get(
														tempList.indexOf("associateSubType")).trim(),
												"associateType", id1, false);
									}

									if (id != null)
									{
										ob = new InsertDataTable();
										ob.setColName("associateType");
										ob.setDataName(id1);
										insertData.add(ob);

										ob = new InsertDataTable();
										ob.setColName("associateSubType");
										ob.setDataName(id);
										insertData.add(ob);
									}
								}
							}

							// associate category
							// ***************************************************************
							else if (tempList.get(j).equalsIgnoreCase("associateCategory"))
							{
								// //////System.out.println("associateCategory");
								id = insertRecordAndGetKey("associatecategory",
										"associate_category", list.get(j).trim(), "", "", true);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// source master
							// **************************************************
							// **********************
							else if (tempList.get(j).equalsIgnoreCase("sourceName"))
							{
								// //////System.out.println("sourceMaster");
								id = insertRecordAndGetKey("sourcemaster", "sourceName", list
										.get(j).trim(), "", "", true);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// associate status
							// ***********************************************
							// **********************
							else if (tempList.get(j).equalsIgnoreCase("status"))
							{
								// //////System.out.println("status");
								id = insertRecordAndGetKey("associatestatus", "statusname",
										list.get(j).trim(), "", "", true);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// associate acManager
							// ***************************************************************
							else if (tempList.get(j).equalsIgnoreCase("acManager")
									&& list.get(j).trim().contains("-"))// refname should be like
							// 'name-mobilenumber' e.g
							// 'Anoop-9136498345'
							{
								// //////System.out.println("acManager");
								String[] val = list.get(j).trim().split("-");
								id = insertRecordAndGetKey("employee_basic", "empName", val[0]
										.trim(), "mobOne", val[1].trim(), false);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// other data
							else if (!tempList.get(j).equalsIgnoreCase("associateSubType")
									&& !tempList.get(j).equalsIgnoreCase("associateType")
									&& !tempList.get(j).equalsIgnoreCase("state")
									&& !tempList.get(j).equalsIgnoreCase("location")
									&& !tempList.get(j).equalsIgnoreCase("offering"))
							{
								// //////System.out.println("elseeeeeeeeeeeeeeeeee");
								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(list.get(j));
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

						boolean statusContact = coi.insertIntoTable(getTableName().split(
								"#")[0], insertData, connectionSpace);
						// clear list
						insertData.clear();

					}
					addActionMessage(" Associate Added Successfully.");
					returnResult = SUCCESS;
					
					
				}
				else if (level.equalsIgnoreCase("client"))// In case of client excel upload
				{
					ArrayList<String> tempList = excelDataList.get(0);// third row value
					// of excel sheet
					// i.e excel headers
					boolean flag = true;
					String id = null;

					// In this for loop iteration if fields with mandatory comment as at
					// the end of if block, has to be given otherwise entire record will
					// be discarded
					for (ArrayList<String> list : excelDataList)
					{
						if (flag) // to escape first list because these are excel headers
						{
							flag = false;
							continue;
						}
						boolean isFirst = true;
						String clientId = null;

						for (int j = 0; j < tableColumnSize; j++)
						{
							// //////System.out.println("inner for");
							// source master
							// **************************************************
							// *********************
							if (tempList.get(j).equalsIgnoreCase("sourceMaster"))
							{
								// //////System.out.println("sourceMaster");
								id = insertRecordAndGetKey("sourcemaster", "sourceName", list
										.get(j).trim(), "", "", true);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// referred by and referred name
							// variable 'isFirst' is just to run this else if block only once
							// for 'referedBy' and 'refName'
							// ********************************************************
							else if ((tempList.get(j).equalsIgnoreCase("referedBy") || tempList
									.get(j).equalsIgnoreCase("refName"))
									&& isFirst)
							{
								isFirst = false;
								if (tempList.contains("referedBy")
										&& tempList.contains("refName")
										&& !list.get(tempList.indexOf("referedBy")).trim()
												.equalsIgnoreCase("NA")
										&& !list.get(tempList.indexOf("refName")).trim()
												.equalsIgnoreCase("NA")
										&& list.get(tempList.indexOf("refName")).trim().contains(
												"-"))// mandatory
								{
									// //////System.out.println("referedBy");
									String[] val = list.get(tempList.indexOf("refName")).trim()
											.split("-");// refname should be like 'name-mobilenumber'
									// e.g 'Anoop-9136498345'
									if (list.get(tempList.indexOf("referedBy")).trim()
											.equalsIgnoreCase("client")
											&& val[1].length() == 10)
									{
										id = insertRecordAndGetKey("client_basic_data",
												"clientName", val[0].trim(), "mobileNo", val[1].trim(),
												false);
										// //////System.out.println("iddd:"+id);
										if (id != null)
										{
											ob = new InsertDataTable();
											ob.setColName("referedBy");
											ob.setDataName(list.get(tempList.indexOf("referedBy"))
													.trim());
											insertData.add(ob);

											ob = new InsertDataTable();
											ob.setColName("refName");
											ob.setDataName(id);
											insertData.add(ob);
										}
									}
									else if (list.get(tempList.indexOf("referedBy")).trim()
											.equalsIgnoreCase("associate")
											&& val[1].length() == 10)
									{
										id = insertRecordAndGetKey("associate_basic_data",
												"associateName", val[0].trim(), "contactNo", val[1]
														.trim(), false);
										// //////System.out.println("iddd:"+id);
										if (id != null)
										{
											ob = new InsertDataTable();
											ob.setColName("referedBy");
											ob.setDataName(list.get(tempList.indexOf("referedBy"))
													.trim());
											insertData.add(ob);

											ob = new InsertDataTable();
											ob.setColName("refName");
											ob.setDataName(id);
											insertData.add(ob);
										}
									}
								}
							}

							// client status
							// **************************************************
							// ***********************
							else if (tempList.get(j).equalsIgnoreCase("status"))
							{
								// //////System.out.println("status");
								id = insertRecordAndGetKey("client_status", "statusName", list
										.get(j).trim(), "", "", true);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// client location
							// ************************************************
							// ***********************
							else if (tempList.get(j).equalsIgnoreCase("location"))
							{
								// //////System.out.println("location");
								id = insertRecordAndGetKey("location", "name", list.get(j)
										.trim(), "", "", true);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// client industry
							// ************************************************
							// ***********************
							else if (tempList.get(j).equalsIgnoreCase("industry"))
							{
								////System.out.println("industry");
								id = insertRecordAndGetKey("industrydatalevel1", "industry", list.get(j)
										.trim(), "", "", false);
								////System.out.println("id:"+id);
								
								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}
							
							// client sub industry
							// ************************************************
							// ***********************
							else if (tempList.get(j).equalsIgnoreCase("subIndustry"))
							{
								////System.out.println("subIndustry");
								id = insertRecordAndGetKey("subindustrydatalevel2", "subIndustry", list.get(j)
										.trim(), "", "", false);
								////System.out.println("id:"+id);
								
								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// client acManager
							// ***********************************************
							// *********************
							else if (tempList.get(j).equalsIgnoreCase("acManager")
									&& list.get(j).trim().contains("-"))// refname should be like
							// 'name-mobilenumber' e.g
							// 'Anoop-9136498345'
							{
								// //////System.out.println("acManager");
								String[] val = list.get(j).trim().split("-");
								id = insertRecordAndGetKey("employee_basic", "empName", val[0]
										.trim(), "mobOne", val[1].trim(), false);
								// //////System.out.println("id:"+id);

								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(id);
								insertData.add(ob);
							}

							// other data
							// *****************************************************
							// ***********************
							else if (!tempList.get(j).equalsIgnoreCase("referedBy")
									&& !tempList.get(j).equalsIgnoreCase("refName")
									&& !tempList.get(j).equalsIgnoreCase("offering")
									&& !tempList.get(j).equalsIgnoreCase("weightage")
									)
							{
								ob = new InsertDataTable();
								ob.setColName(tempList.get(j));
								ob.setDataName(list.get(j));
								insertData.add(ob);
							}
						}

						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(cId);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						boolean statusContact = coi.insertIntoTable(getTableName().split(
								"#")[0], insertData, connectionSpace);
						// clear list
						insertData.clear();
					}
					addActionMessage(" Clients Added Successfully.");
					returnResult = SUCCESS;
				}
				else if (Integer.parseInt(level.trim()) >= 1
						&& Integer.parseInt(level.trim()) <= 5) // in case of excel
				// upload/download for
				// offering
				{
					for (String str : excelDataList.get(0))
					{
						ob = new InsertDataTable();
						ob.setColName(str);
						ob.setDataName("");
						insertData.add(ob);
					}

					if (Integer.parseInt(level.trim()) != 1)
					{
						ob = new InsertDataTable();
						ob.setColName((level.equalsIgnoreCase("2") ? "verticalname"
								: (level.equalsIgnoreCase("3") ? "offeringname" : (level
										.equalsIgnoreCase("4") ? "subofferingname" : (level
										.equalsIgnoreCase("5") ? "variantname" : "")))));
						ob.setDataName(productMappedId);
						insertData.add(ob);
					}

					//////System.out.println("ob.getColName():" + ob.getColName());
					//////System.out.println("getTableName():" + getTableName());

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

					boolean flag = true;
					for (ArrayList<String> strList : excelDataList)
					{
						if (flag) // we dont want first row to be inserted in table because
						// it
						// is table columns, so we will escape it
						{
							flag = false;
							continue;
						}

						for (int j = 0; j < tableColumnSize; j++)
						{
							InsertDataTable idt = insertData.get(j);
							idt.setDataName(strList.get(j));// build 'insertData' to store it
							// in
							// table
							// //////System.out.println("1111111111:"+idt.getColName());
							// //////System.out.println("2222222222:"+idt.getDataName());

						}

						// Store data to table
						//////System.out.println("insertData.size():" + insertData.size());
						boolean status = coi.insertIntoTable(getTableName(), insertData,
								connectionSpace);
						if (status)
						{
							addActionMessage("Offering Added Successfully");
							returnResult = SUCCESS;
						}
						else
						{
							addActionMessage("Error! There is some error in data!");
							returnResult = SUCCESS;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	private void readExcel()
	{
		try
		{
			if (getExcel() != null)
			{
				columnNameMap = new LinkedHashMap<String, Object>();
				// List<ConfigurationUtilBean> columnMap=new
				// ArrayList<ConfigurationUtilBean>();
				InputStream inputStream = new FileInputStream(getExcel());

				// if(session.get("columnMap") != null)
				if (currentColumn().equalsIgnoreCase("SUCCESS"))
				{
					// columnMap=(List<ConfigurationUtilBean>) session.get("columnMap");
					try
					{
						/*
						 * list 'excelDataList' will hold data read from excel in a 2D list
						 * where first list in the containing list will be header values of
						 * table where data is to be inserted
						 */
						if (getExcelFileName().contains(".xlsx")) // Read data from .xlsx
						{
							GenericReadExcel7 GRE7 = new GenericReadExcel7();
							XSSFSheet excelSheet = GRE7.readExcel(inputStream);
							for (int rowIndex = 2; rowIndex < excelSheet
									.getPhysicalNumberOfRows(); rowIndex++)
							{
								XSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									if (rowIndex == 2)// for header parameters
									{
										ArrayList<String> paramList = new ArrayList<String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											for (ConfigurationUtilBean cub : columnMap)
											{
												if (cub.getValue().equalsIgnoreCase(
														GRE7.readExcelSheet(row, cellIndex).toString()
																.trim()))
												{
													paramList.add(cub.getKey());
												}
											}
										}
										excelDataList.add(paramList);
									}
									else
									// Excel data part
									{
										ArrayList<String> paramList = new ArrayList<String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											if (GRE7.readExcelSheet(row, cellIndex) != null
													&& !GRE7.readExcelSheet(row, cellIndex)
															.equalsIgnoreCase(""))
											{
												paramList.add(GRE7.readExcelSheet(row, cellIndex)
														.toString().trim());
											}
											else
											{
												paramList.add("NA");
											}
										}
										excelDataList.add(paramList);
									}
								}
							}
						}
						else
						// Read data from .xls
						{
							GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
							HSSFSheet excelSheet = GRBE.readExcel(inputStream);
							for (int rowIndex = 2; rowIndex < excelSheet
									.getPhysicalNumberOfRows(); rowIndex++)
							{
								HSSFRow row = excelSheet.getRow(rowIndex);
								int cellNo = row.getLastCellNum();
								if (cellNo > 0)
								{
									if (rowIndex == 2)
									{
										ArrayList<String> paramList = new ArrayList<String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											for (ConfigurationUtilBean cub : columnMap)
											{
												if (cub.getValue().equalsIgnoreCase(
														GRBE.readExcelSheet(row, cellIndex).toString()
																.trim()))
												{
													paramList.add(cub.getKey());
												}
											}
										}
										excelDataList.add(paramList);
									}
									else
									// Read excel data part
									{
										ArrayList<String> paramList = new ArrayList<String>();
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
										{
											if (GRBE.readExcelSheet(row, cellIndex) != null
													&& !GRBE.readExcelSheet(row, cellIndex)
															.equalsIgnoreCase(""))
											{
												paramList.add(GRBE.readExcelSheet(row, cellIndex)
														.toString().trim());
											}
											else
											{
												paramList.add("NA");
											}
										}
										excelDataList.add(paramList);
									}
								}
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				for (ArrayList<String> s : excelDataList)
				{
					for (String p : s)
					{
						//////System.out.println(">>>>:" + p);
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public String getDownloadType()
	{
		return downloadType;
	}

	public void setDownloadType(String downloadType)
	{
		this.downloadType = downloadType;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public List<ConfigurationUtilBean> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(List<ConfigurationUtilBean> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableAllis()
	{
		return tableAllis;
	}

	public void setTableAllis(String tableAllis)
	{
		this.tableAllis = tableAllis;
	}

	public String getExcelName()
	{
		return excelName;
	}

	public void setExcelName(String excelName)
	{
		this.excelName = excelName;
	}

	public String getMappedTableName()
	{
		return mappedTableName;
	}

	public void setMappedTableName(String mappedTableName)
	{
		this.mappedTableName = mappedTableName;
	}

	public String getMasterTableName()
	{
		return masterTableName;
	}

	public void setMasterTableName(String masterTableName)
	{
		this.masterTableName = masterTableName;
	}

	public File getExcel()
	{
		return excel;
	}

	public void setExcel(File excel)
	{
		this.excel = excel;
	}

	public Map<String, Object> getColumnNameMap()
	{
		return columnNameMap;
	}

	public void setColumnNameMap(Map<String, Object> columnNameMap)
	{
		this.columnNameMap = columnNameMap;
	}

	public String getExcelContentType()
	{
		return excelContentType;
	}

	public void setExcelContentType(String excelContentType)
	{
		this.excelContentType = excelContentType;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public String getProductMappedId()
	{
		return productMappedId;
	}

	public void setProductMappedId(String productMappedId)
	{
		this.productMappedId = productMappedId;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

}


/*



*/