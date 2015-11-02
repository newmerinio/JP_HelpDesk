package com.Over2Cloud.ctrl.wfpm.lead;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
public class ExcelUpload extends ActionSupport
{
	

	Map													session					= ActionContext.getContext().getSession();
	String											userName				= (String) session.get("uName");
	String											accountID				= (String) session.get("accountid");
	SessionFactory							connectionSpace	= (SessionFactory) session.get("connectionSpace");
	String cId = new CommonHelper().getEmpDetailsByUserName(CommonHelper.moduleName, userName,connectionSpace)[0];
	private File								excel;
	private String							excelContentType;
	private String							excelFileName;
	private Integer							rows						= 0;
	private Integer							total						= 0;
	private Integer							records					= 0;
	private Integer							page						= 0;
	private String							selectedId			= null;
	public Map<String, Object>	columnNameMap		= null;
	public String								upload4;
	public List<Object>					gridDataList;

	@SuppressWarnings("unchecked")
	public String readExcelData()
	{
		String returnResult = ERROR;
		try
		{
			if (excel != null)
			{
				// create table for lead
				new LeadActionControlDao().createLeadGenerationTable(accountID, connectionSpace);

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				columnNameMap = new LinkedHashMap<String, Object>();
				Map<String, String> columnMap = new LinkedHashMap<String, String>();
				List<String> columnKeyList = new ArrayList<String>();
				Map<String, Object> dataKeyMap = new LinkedHashMap<String, Object>();
				List<String> gridListData = new ArrayList<String>();
				InputStream inputStream = new FileInputStream(excel);

				Map<String, String> tempMap = new LinkedHashMap<String, String>();
				Map<String, String> sourceMap = new LinkedHashMap<String, String>();
				List<String> dropDownId = new ArrayList<String>();
				List<String> dropDownValue = new ArrayList<String>();
				//refName
				Map<String, String> refTempMap = new LinkedHashMap<String, String>();
				Map<String, String> refMap = new LinkedHashMap<String, String>();
				List<String> dropDownIdIrefName = new ArrayList<String>();
				List<String> dropDownValuerefName = new ArrayList<String>();
				// Industry
				Map<String, String> industryTempMap = new LinkedHashMap<String, String>();
				Map<String, String> industryMap = new LinkedHashMap<String, String>();
				List<String> dropDownIdIndustry = new ArrayList<String>();
				List<String> dropDownValueIndustry = new ArrayList<String>();
				// Sub Industry
				Map<String, String> subIndustryTempMap = new LinkedHashMap<String, String>();
				Map<String, String> subIndustryMap = new LinkedHashMap<String, String>();
				List<String> dropDownIdSubIndustry = new ArrayList<String>();
				List<String> dropDownValueSubIndustry = new ArrayList<String>();
				// Location
				Map<String, String> nameTempMap = new LinkedHashMap<String, String>();
				Map<String, String> nameMap = new LinkedHashMap<String, String>();
				List<String> dropDownIdname = new ArrayList<String>();
				List<String> dropDownValuename = new ArrayList<String>();

				
				List dataList = null;
				dataList = new ArrayList();
				dataList = cbt.executeAllSelectQuery("Select id,sourceName from sourcemaster", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && !object[1].toString().equals(""))
						{
							sourceMap.put(object[1].toString(), object[0].toString());
						}
					}
				}
				//refName
				dataList = cbt.executeAllSelectQuery("select id,clientName from client_basic_data", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					//System.out.println("-----refName   ");
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && !object[1].toString().equals(""))
						{
							//System.out.println("-----refName   ");
							refMap.put(object[1].toString(), object[0].toString());
						}
					}
				}
				//industry
				dataList = cbt.executeAllSelectQuery("select id, industry from industrydatalevel1", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && !object[1].toString().equals(""))
						{
							industryMap.put(object[1].toString(), object[0].toString());
						}
					}
				}

				// sub industry
				dataList = cbt.executeAllSelectQuery("select id, subIndustry from subindustrydatalevel2", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && !object[1].toString().equals(""))
						{
							subIndustryMap.put(object[1].toString(), object[0].toString());
						}
					}
				}

				// sub industry
				dataList = cbt.executeAllSelectQuery("select id, name from location", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && !object[1].toString().equals(""))
						{
							nameMap.put(object[1].toString(), object[0].toString());
						}
					}
				}
				
				
				if (upload4.equals("assetDetail"))
				{
					//System.out.println("upload4---");
					if (new LeadCommonAction().getColumnName("mapped_lead_generation", "lead_generation", "") != null)
					{
						returnResult = "astConfirm";
						columnMap = (Map<String, String>) session.get("columnMap");
					}
				}

				try
				{
					if (excelFileName.contains(".xlsx"))
					{
						//System.out.println("---excelFileName---");
						GenericReadExcel7 GRE7 = new GenericReadExcel7();
						XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						{
							XSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0)
							{
								if (rowIndex == 2)
								{
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										Iterator<Entry<String, String>> hiterator = columnMap.entrySet().iterator();
										while (hiterator.hasNext())
										{
											Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
											if (GRE7.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim()))
											{
												/**
												 *header field matches from header Put Into List and
												 * Map Object
												 */
												if (paramPair.getKey().toString().equals("sourceName"))
												{
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("industry"))
												{
													industryTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												
												else if (paramPair.getKey().toString().equals("subIndustry"))
												{
													subIndustryTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("name"))//location
												{
													nameTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("refName"))
												{
													refTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												columnKeyList.add(paramPair.getKey());
												dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
											}
										}
									}
									setColumnNameMap(dataKeyMap);
								}
								else
								{
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
										{
											/**
											 * add source code at here and check what column value you
											 * have get if you have drop type value just check it and
											 * pick id of that value and and add in List Object !
											 */
											if (tempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownId.add(sourceMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
											}
											else if (industryTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdIndustry.add(industryMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												dropDownValueIndustry.add(GRE7.readExcelSheet(row, cellIndex).toString());
											}
											else if (refTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdIrefName.add(refMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												dropDownValuerefName.add(GRE7.readExcelSheet(row, cellIndex).toString());
											}
											else if (subIndustryTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdSubIndustry.add(subIndustryMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												dropDownValueSubIndustry.add(GRE7.readExcelSheet(row, cellIndex).toString());
											}
											else if (nameTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdname.add(nameMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												dropDownValuename.add(GRE7.readExcelSheet(row, cellIndex).toString());
											}
											
											gridListData.add(GRE7.readExcelSheet(row, cellIndex).toString());
										}
										else
										{
											gridListData.add("NA");
										}
									}
								}
							}
						}
					}
					else
					{
						GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						for (int rowIndex = 2; rowIndex < excelSheet.getPhysicalNumberOfRows(); rowIndex++)
						{
							HSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0)
							{
								if (rowIndex == 2)
								{
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										Iterator<Entry<String, String>> hiterator = columnMap.entrySet().iterator();
										while (hiterator.hasNext())
										{
											Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
											if (GRBE.readExcelSheet(row, cellIndex).toString().trim().equals(paramPair.getValue().toString().trim()))
											{
												/**
												 *header field matches from header Put Into List and
												 * Map Object
												 */
												if (paramPair.getKey().toString().equals("sourceName"))
												{
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("industry"))
												{
													industryTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("subIndustry"))
												{
													subIndustryTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("name"))//location
												{
													nameTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("refName"))
												{
													refTempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												columnKeyList.add(paramPair.getKey());
												dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
											}
										}
									}
									setColumnNameMap(dataKeyMap);
								}
								else
								{
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
										{
											/**
											 * add source code at here and check what column value you
											 * have get if you have drop type value just check it and
											 * pick id of that value and and add in List Object !
											 */
											if (tempMap.containsKey(String.valueOf(cellIndex)))
											{
												if (sourceMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(sourceMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
												}
											}
											else if (industryTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdIndustry.add(industryMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
												dropDownValueIndustry.add(GRBE.readExcelSheet(row, cellIndex).toString());
											}
											/*else if (refTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdIrefName.add(refMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												dropDownValuerefName.add(GRE7.readExcelSheet(row, cellIndex).toString());
											}*/
											
											else if (refTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdIrefName.add(refMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
												dropDownValuerefName.add(GRBE.readExcelSheet(row, cellIndex).toString());
											}
											
											else if (subIndustryTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdSubIndustry.add(subIndustryMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
												dropDownValueSubIndustry.add(GRBE.readExcelSheet(row, cellIndex).toString());
											}
											else if (nameTempMap.containsKey(String.valueOf(cellIndex)))
											{
												dropDownIdname.add(nameMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
												dropDownValuename.add(GRBE.readExcelSheet(row, cellIndex).toString());
											}
											
											gridListData.add(GRBE.readExcelSheet(row, cellIndex).toString());
										}
										else
										{
											gridListData.add("NA");
										}
									}
								}
							}
						}
					}

					if (gridListData != null && gridListData.size() > 0)
					{
						//System.out.println("27July2013>>>>>>>>>>" + gridListData.size());
						session.put("gridListData", gridListData);
						session.put("columnKeyList", columnKeyList);
					}
					if (session.containsKey("columnMap"))
					{
						session.remove("columnMap");
					}
					if (dropDownId != null && dropDownId.size() > 0)
					{
						session.put("dropDownId", dropDownId);
						session.put("dropDownValue", dropDownValue);
					}
					if (dropDownIdIndustry != null && dropDownIdIndustry.size() > 0)
					{
						session.put("dropDownIdIndustry", dropDownIdIndustry);
						session.put("dropDownValueIndustry", dropDownValueIndustry);
					}
					if (dropDownIdIrefName != null && dropDownIdIrefName.size() > 0)
					{
						session.put("dropDownIdIrefName", dropDownIdIrefName);
						session.put("dropDownValuerefName", dropDownValuerefName);
					}
					if (dropDownIdSubIndustry != null && dropDownIdSubIndustry.size() > 0)
					{
						session.put("dropDownIdSubIndustry", dropDownIdSubIndustry);
						session.put("dropDownValueSubIndustry", dropDownValueSubIndustry);
					}
					if (dropDownIdname != null && dropDownIdname.size() > 0)
					{
						session.put("dropDownIdname", dropDownIdname);
						session.put("dropDownValuename", dropDownValuename);
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String viewConfirmationData()
	{
		String returnResult = ERROR;
		try
		{
			List<String> gridListData = (List<String>) session.get("gridListData");
			List<String> columnKeyList = (List<String>) session.get("columnKeyList");
			List<Object> tempList = new ArrayList<Object>();
			if (gridListData != null && gridListData.size() > 0)
			{
				setRecords(gridListData.size());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords()) to = getRecords();
				if (gridListData.size() > 0)
				{
					gridDataList = new ArrayList<Object>();
					int i = 0;
					Map<String, Object> one = new HashMap<String, Object>();
					String[] fieldsArray = columnKeyList.toArray(new String[columnKeyList.size()]);
					String[] fieldsArrayss = gridListData.toArray(new String[gridListData.size()]);
					if (fieldsArrayss != null && fieldsArray != null)
					{
						for (int k = 0; k < fieldsArrayss.length; k++)
						{
							one.put(fieldsArray[i].toString().trim(), fieldsArrayss[k].toString());
							i++;
							if (i == fieldsArray.length)
							{
								i = 0;
								tempList.add(one);
								one = new HashMap<String, Object>();
							}
						}
					}
					setGridDataList(tempList);
				}
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public String saveSelectData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = null;
				if (upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
				{
					statusColName = Configuration.getConfigurationData("mapped_lead_generation", accountID, connectionSpace, "", 0, "table_name", "lead_generation");
				}

				boolean workStatus = false;
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<TableColumes> tableColume = new ArrayList<TableColumes>();

					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("flag")) workStatus = true;
					}
					if (upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
					{
						boolean status1 = cbt.createTable22("leadgeneration", tableColume, connectionSpace);

					}

				}

				List<String> gridListData = (List<String>) session.get("gridListData");
				List<String> columnKeyList = (List<String>) session.get("columnKeyList");
				List<String> dropDownId = (List<String>) session.get("dropDownId");
				List<String> dropDownValue = (List<String>) session.get("dropDownValue");
				
				List<String> dropDownIdIndustry = (List<String>) session.get("dropDownIdIndustry");
				List<String> dropDownValueIndustry = (List<String>) session.get("dropDownValueIndustry");
				
				List<String> dropDownIdIrefName = (List<String>) session.get("dropDownIdIrefName");
				List<String> dropDownValuerefName = (List<String>) session.get("dropDownValuerefName");
				
				List<String> dropDownIdSubIndustry = (List<String>) session.get("dropDownIdSubIndustry");
				List<String> dropDownValueSubIndustry = (List<String>) session.get("dropDownValueSubIndustry");
				
				List<String> dropDownIdname = (List<String>) session.get("dropDownIdname");
				List<String> dropDownValuename = (List<String>) session.get("dropDownValuename");
				
				//System.out.println("FROM Session dropDownValuerefName>>>>" + dropDownValuerefName);
				//System.out.println("FROM Session dropDownValue>>>>" + dropDownValue);
				ArrayList<InsertDataTable> insertData = null;
				InsertDataTable ob = null;
				
				session.remove("gridListData");
				session.remove("columnKeyList");
				session.remove("dropDownId");
				session.remove("dropDownValue");
				session.remove("dropDownIdIndustry");
				session.remove("dropDownIdIrefName");
				session.remove("dropDownValueIndustry");
				session.remove("dropDownIdSubIndustry");
				session.remove("dropDownValueSubIndustry");
				session.remove("dropDownIdname");
				session.remove("dropDownValuename");
				
				int tempIndex = 0;
				boolean flagmasg = false;
				for (int index = 0; index < gridListData.size() / columnKeyList.size(); index++)
				{
					insertData = new ArrayList<InsertDataTable>();
					for (int index4Col = 0; index4Col < columnKeyList.size(); index4Col++)
					{
						// source name
						if (columnKeyList.get(index4Col).toString().equals("sourceName"))
						{
							if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals(""))
							{
								if (dropDownValue != null)
								{
									for (int i = 0; i < dropDownValue.size(); i++)
									{
										if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals("")
												&& dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob = new InsertDataTable();
											ob.setColName(columnKeyList.get(index4Col));
											ob.setDataName(dropDownId.get(i).toString());
											insertData.add(ob);
											break;
										}
									}
								}
								else
								{
									flagmasg = true;
								}
							}
						}

						// Industry
						else if (columnKeyList.get(index4Col).toString().equals("industry"))
						{
							if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals(""))
							{
								if (dropDownValueIndustry != null)
								{
									for (int i = 0; i < dropDownValueIndustry.size(); i++)
									{
										if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals("")
												&& dropDownValueIndustry.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob = new InsertDataTable();
											ob.setColName(columnKeyList.get(index4Col));
											ob.setDataName(dropDownIdIndustry.get(i).toString());
											insertData.add(ob);
											break;
										}
									}
								}
								else
								{
									flagmasg = true;
								}
							}
						}
						// RefName
						else if (columnKeyList.get(index4Col).toString().equals("refName"))
						{
							if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals(""))
							{
								if (dropDownValuerefName != null)
								{
									for (int i = 0; i < dropDownValuerefName.size(); i++)
									{
										if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals("")
												&& dropDownValuerefName.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob = new InsertDataTable();
											ob.setColName(columnKeyList.get(index4Col));
											ob.setDataName(dropDownIdIrefName.get(i).toString());
											insertData.add(ob);
											break;
										}
									}
								}
								else
								{
									flagmasg = true;
								}
							}
						}
					// Sub Industry
						else if (columnKeyList.get(index4Col).toString().equals("subIndustry"))
						{
							if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals(""))
							{
								if (dropDownValueSubIndustry != null)
								{
									for (int i = 0; i < dropDownValueSubIndustry.size(); i++)
									{
										if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals("")
												&& dropDownValueSubIndustry.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob = new InsertDataTable();
											ob.setColName(columnKeyList.get(index4Col));
											ob.setDataName(dropDownIdSubIndustry.get(i).toString());
											insertData.add(ob);
											break;
										}
									}
								}
								else
								{
									flagmasg = true;
								}
							}
						}
					// Location
						else if (columnKeyList.get(index4Col).toString().equals("name"))
						{
							if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals(""))
							{
								if (dropDownValuename != null)
								{
									for (int i = 0; i < dropDownValuename.size(); i++)
									{
										if (gridListData.get(tempIndex).toString() != null && !gridListData.get(tempIndex).toString().equals("")
												&& dropDownValuename.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
										{
											ob = new InsertDataTable();
											ob.setColName(columnKeyList.get(index4Col));
											ob.setDataName(dropDownIdname.get(i).toString());
											insertData.add(ob);
											break;
										}
									}
								}
								else
								{
									flagmasg = true;
								}
							}
						}
						
						else
						{
							ob = new InsertDataTable();
							ob.setColName(columnKeyList.get(index4Col).toString());
							ob.setDataName(gridListData.get(tempIndex).toString());
							insertData.add(ob);
						}
						tempIndex++;
					}
					if (userTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(cId);
						insertData.add(ob);
					}
					if (dateTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);
					}
					if (timeTrue)
					{
						/*ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);*/
						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);
					}

					if (upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
					{
						cbt.insertIntoTable("leadgeneration", insertData, connectionSpace);
					}

				}
				if (flagmasg)
				{
					addActionMessage("Please Insert your Source Data First ");

					addActionMessage("Upload Data Successfully Registered!!!");
				}
				else
				{
					addActionMessage("Upload Data Successfully Registered!!!");
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

	public File getExcel()
	{
		return excel;
	}

	public void setExcel(File excel)
	{
		this.excel = excel;
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

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public Map<String, Object> getColumnNameMap()
	{
		return columnNameMap;
	}

	public void setColumnNameMap(Map<String, Object> columnNameMap)
	{
		this.columnNameMap = columnNameMap;
	}

	public String getUpload4()
	{
		return upload4;
	}

	public void setUpload4(String upload4)
	{
		this.upload4 = upload4;
	}

	public List<Object> getGridDataList()
	{
		return gridDataList;
	}

	public void setGridDataList(List<Object> gridDataList)
	{
		this.gridDataList = gridDataList;
	}

}
