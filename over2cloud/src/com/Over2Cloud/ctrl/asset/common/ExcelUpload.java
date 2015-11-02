package com.Over2Cloud.ctrl.asset.common;

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
import com.Over2Cloud.ctrl.asset.AssetCommonAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.hr.EmpExcelUploadHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ExcelUpload extends ActionSupport
{

	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private File excel;
	private String excelContentType;
	private String excelFileName;
	private Integer rows = 0;
	private Integer total = 0;
	private Integer records = 0;
	private Integer page = 0;
	private String selectedId = null;
	public Map<String, Object> columnNameMap = null;
	public String upload4;
	public List<Object> gridDataList;

	@SuppressWarnings("unchecked")
	public String readExcelData()
	{
		String returnResult = ERROR;
		try
		{
			if (excel != null)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				columnNameMap = new LinkedHashMap<String, Object>();
				Map<String, String> columnMap = new LinkedHashMap<String, String>();
				List<String> columnKeyList = new ArrayList<String>();
				Map<String, Object> dataKeyMap = new LinkedHashMap<String, Object>();
				List<String> gridListData = new ArrayList<String>();
				InputStream inputStream = new FileInputStream(excel);
				Map<String, String> deptMap = new LinkedHashMap<String, String>();
				Map<String, String> tempMap = new LinkedHashMap<String, String>();
				Map<String, String> vendorMap = new LinkedHashMap<String, String>();
				Map<String, String> assetMap = new LinkedHashMap<String, String>();
				Map<String, String> employeeMap = new LinkedHashMap<String, String>();
				Map<String, String> suprtTypeMap = new LinkedHashMap<String, String>();
				Map<String, String> assetTypeMap = new LinkedHashMap<String, String>();
				List<String> dropDownId = new ArrayList<String>();
				List<String> dropDownValue = new ArrayList<String>();
				List dataList = null;
				int deptCellIndex = 0,  vendorCellIndex = 0,assetTypeCellIndex=0;
				dataList = new ArrayList();
				dataList = cbt.executeAllSelectQuery("SELECT id,deptName FROM department", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[1].toString() != "")
						{
							deptMap.put(object[1].toString(), object[0].toString());
						}
					}
				}
				if(dataList!=null)
					dataList.clear();
				
				dataList = cbt.executeAllSelectQuery("Select id,mobOne from employee_basic", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[1].toString() != "")
						{
							employeeMap.put(object[1].toString(), object[0].toString());
						}
					}
				}

				if(dataList!=null)
					dataList.clear();
				
				dataList = cbt.executeAllSelectQuery("Select id,serialno from asset_detail", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[1].toString() != "")
						{
							assetMap.put(object[1].toString(), object[0].toString());
						}
					}
				}

				if(dataList!=null)
					dataList.clear();
				
				dataList = cbt.executeAllSelectQuery("Select id,vendorname from createvendor_master where status='Active'", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[1].toString() != "")
						{
							vendorMap.put(object[1].toString(), object[0].toString());
						}
					}
				}

				if(dataList!=null)
					dataList.clear();
				
				dataList = cbt.executeAllSelectQuery("Select id,support_type from createasset_category_master", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[1].toString() != "")
						{
							suprtTypeMap.put(object[1].toString(), object[0].toString());
						}
					}
				}
				if(dataList!=null)
					dataList.clear();
				
				dataList = cbt.executeAllSelectQuery("SELECT id,assetSubCat FROM createasset_type_master WHERE status='Active'", connectionSpace);
				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null && object[1].toString() != "")
						{
							assetTypeMap.put(object[1].toString(), object[0].toString());
						}
					}
				}
				
				
				if (upload4.equals("assetDetail"))
				{
					if (new AssetCommonAction().getColumnName("mapped_asset_master", "asset_master", "") != null)
					{
						returnResult = "astConfirm";
						columnMap = (Map<String, String>) session.get("columnMap");
					}
				}
				else if (upload4.equals("assetSupport"))
				{
					if (new AssetCommonAction().getColumnName("mapped_asset_support", "asset_support", "") != null)
					{
						returnResult = "suprtConfirm";
						columnMap = (Map<String, String>) session.get("columnMap");
					}
				}
				else if (upload4.equals("allotmentDetail"))
				{
					if (new AssetCommonAction().getColumnName("mapped_allotment_master", "allotment_master", "") != null)
					{
						returnResult = "allotConfirm";
						columnMap = (Map<String, String>) session.get("columnMap");
					}
				}

				try
				{
					if (excelFileName.contains(".xlsx"))
					{
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
												 *header field matches from
												 * header Put Into List and Map
												 * Object
												 * 
												 */
												if (paramPair.getKey().toString().equals("deptName"))
												{
													deptCellIndex = cellIndex;
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("vendorname") || paramPair.getKey().toString().equals("vendorid"))
												{
													vendorCellIndex = cellIndex;
													System.out.println(String.valueOf(cellIndex)+" >>> "+ paramPair.getValue().toString());
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("assettype"))
												{
													assetTypeCellIndex = cellIndex;
													System.out.println(String.valueOf(cellIndex)+" >>> "+ paramPair.getValue().toString());
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("assetid") || paramPair.getKey().toString().equals("employeeid") || paramPair.getKey().toString().equals("supporttype"))
												{
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												columnKeyList.add(paramPair.getKey());
												dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
											}
										}
									}
									// Asset Mandatory Field
									if (dataKeyMap.containsKey("assetname") || dataKeyMap.containsKey("deptName") || dataKeyMap.containsKey("vendorname") || dataKeyMap.containsKey("assettype"))
									{
										if (dataKeyMap.containsKey("deptName"))
										{
											setColumnNameMap(dataKeyMap);
										}
										else
										{
											setColumnNameMap(dataKeyMap);
										}
									}
									// Asset Support Mandatory Field
									else if (dataKeyMap.containsKey("employeeid") && dataKeyMap.containsKey("supporttype") && dataKeyMap.containsKey("deptName"))
									{
										if (dataKeyMap.containsKey("deptName"))
										{
											setColumnNameMap(dataKeyMap);
										}
										else
										{
											setColumnNameMap(dataKeyMap);
										}
									}
									// Asset Allotment Mandatory Field
									else if (dataKeyMap.containsKey("deptName"))
									{
										if (dataKeyMap.containsKey("deptName"))
										{
											setColumnNameMap(dataKeyMap);
										}
										else
										{
											setColumnNameMap(dataKeyMap);
										}
									}

								}
								else
								{
									int deptMaxId = 0;
									int astTypeMaxId = 0;
									int vendorMaxId = 0;
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										if (GRE7.readExcelSheet(row, cellIndex) != null && !GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
										{
											/**
											 * add source code at here and check
											 * what column value you have get
											 * 
											 * if you have drop type value just
											 * check it and pick id of that
											 * value and and add in List Object
											 * !
											 * 
											 */
											if (tempMap.containsKey(String.valueOf(cellIndex)))
											{
												/*
												 * if(deptMap.containsKey(GRBE.readExcelSheet
												 * (row, cellIndex).toString()))
												 * {
												 * dropDownId.add(deptMap.get(GRBE
												 * .readExcelSheet(row,
												 * cellIndex).toString()));
												 * dropDownValue
												 * .add(GRBE.readExcelSheet(row,
												 * cellIndex).toString()); }
												 * if(subDeptMap
												 * .containsKey(GRBE
												 * .readExcelSheet(row,
												 * cellIndex).toString())) {
												 * dropDownId
												 * .add(subDeptMap.get(
												 * GRBE.readExcelSheet(row,
												 * cellIndex).toString()));
												 * dropDownValue
												 * .add(GRBE.readExcelSheet(row,
												 * cellIndex).toString()); }
												 */

												if (deptMap.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(deptMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
													deptMaxId = Integer.valueOf(deptMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
												}
												else if (deptCellIndex == cellIndex)
												{
													String id = null, value = null;
													Iterator<Entry<String, String>> hiterator = deptMap.entrySet().iterator();
													while (hiterator.hasNext())
													{
														Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
														if (paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
														{
															String query = "SELECT id FROM department WHERE deptName='" + GRE7.readExcelSheet(row, cellIndex).toString() + "'";
															dataList = cbt.executeAllSelectQuery(query, connectionSpace);
															if (dataList != null && dataList.size() > 0)
																id = dataList.get(0).toString();

															value = GRE7.readExcelSheet(row, cellIndex).toString();
														}
													}

													if (id != null && value != null && id != "" && value != "")
													{
														dropDownId.add(id);
														dropDownValue.add(value);
														deptMaxId = Integer.valueOf(id);
													}
													else
													{
														String levelOforganization = null, level2 = null;
														List level2Tenp = new ArrayList<String>();
														level2Tenp.add("id");
														level2Tenp.add("locCompanyName");
														List<String> colname = new ArrayList<String>();
														colname.add("orgLevel");
														colname.add("levelName");
														level2Tenp = cbt.viewAllDataEitherSelectOrAll("organization_level2", level2Tenp, connectionSpace);
														List orgntnlevel = cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level", colname, connectionSpace);

														// getting the current
														// logined user
														// organization level
														if (orgntnlevel != null)
														{
															for (Object c : orgntnlevel)
															{
																Object[] dataC = (Object[]) c;
																levelOforganization = dataC[0].toString();
															}
														}
														else
														{
															levelOforganization = "2";
														}

														if (level2Tenp != null)
														{
															for (Object c : level2Tenp)
															{
																Object[] dataC = (Object[]) c;
																level2 = dataC[0].toString();
															}
														}
														Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
														dataWithColumnName.put("deptName", GRE7.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("mappedOrgnztnId", level2);
														dataWithColumnName.put("orgnztnlevel", levelOforganization);

														deptMaxId = new EmpExcelUploadHelper().saveData("mapped_dept_config_param", "dept_config_param", "department", dataWithColumnName);
														if (deptMaxId > 0)
														{
															dropDownId.add(String.valueOf(deptMaxId));
															dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
															deptMap.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(deptMaxId));
														}
													}
												}
												else if (vendorCellIndex == cellIndex)
												{
													String id = null, value = null;
													Iterator<Entry<String, String>> hiterator = vendorMap.entrySet().iterator();
													while (hiterator.hasNext())
													{
														Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
														if (paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
														{
															String query = "SELECT id FROM createvendor_master WHERE vendorname='" + GRE7.readExcelSheet(row, cellIndex).toString() + "'";
															dataList = cbt.executeAllSelectQuery(query, connectionSpace);
															if (dataList != null && dataList.size() > 0)
																id = dataList.get(0).toString();

															value = GRE7.readExcelSheet(row, cellIndex).toString();
														}
													}

													if (id != null && value != null && id != "" && value != "")
													{
														dropDownId.add(id);
														dropDownValue.add(value);
														vendorMaxId = Integer.valueOf(id);
													}
													else
													{
														Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
														dataWithColumnName.put("vendorname", GRE7.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("vendorfor", GRE7.readExcelSheet(row, cellIndex).toString());
														vendorMaxId = new EmpExcelUploadHelper().saveData("mapped_vendor_master", "vendor_master", "createvendor_master", dataWithColumnName);
														if (vendorMaxId > 0)
														{
															dropDownId.add(String.valueOf(vendorMaxId));
															dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
															vendorMap.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(vendorMaxId));
														}
													}
												}
												
												else if (assetTypeCellIndex == cellIndex)
												{
													String id = null, value = null;
													Iterator<Entry<String, String>> hiterator = vendorMap.entrySet().iterator();
													while (hiterator.hasNext())
													{
														Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
														if (paramPair.getKey().toString().equalsIgnoreCase(GRE7.readExcelSheet(row, cellIndex).toString()))
														{
															String query = "SELECT id FROM createasset_type_master WHERE assetSubCat='" + GRE7.readExcelSheet(row, cellIndex).toString() + "'";
															dataList = cbt.executeAllSelectQuery(query, connectionSpace);
															if (dataList != null && dataList.size() > 0)
																id = dataList.get(0).toString();

															value = GRE7.readExcelSheet(row, cellIndex).toString();
														}
													}

													if (id != null && value != null && id != "" && value != "")
													{
														dropDownId.add(id);
														dropDownValue.add(value);
														astTypeMaxId = Integer.valueOf(id);
													}
													else
													{
														Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
														dataWithColumnName.put("assetCategory", GRE7.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("assetSubCat", GRE7.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("brief", GRE7.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("status", "Active");
														astTypeMaxId = new EmpExcelUploadHelper().saveData("mapped_asset_type_master", "asset_type_master", "createasset_type_master", dataWithColumnName);
														if (astTypeMaxId > 0)
														{
															dropDownId.add(String.valueOf(astTypeMaxId));
															dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
															assetTypeMap.put(GRE7.readExcelSheet(row, cellIndex).toString(), String.valueOf(astTypeMaxId));
														}
													}
												}

												if (assetMap.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(assetMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
												}
												/*
												 * if(vendorMap.containsKey(GRBE.
												 * readExcelSheet(row,
												 * cellIndex).toString())) {
												 * dropDownId
												 * .add(vendorMap.get(GRBE
												 * .readExcelSheet(row,
												 * cellIndex).toString()));
												 * dropDownValue
												 * .add(GRBE.readExcelSheet(row,
												 * cellIndex).toString()); }
												 */
												if (employeeMap.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(employeeMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
												}
												if (suprtTypeMap.containsKey(GRE7.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(suprtTypeMap.get(GRE7.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRE7.readExcelSheet(row, cellIndex).toString());
												}
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
												 *header field matches from
												 * header Put Into List and Map
												 * Object
												 * 
												 */
												System.out.println(paramPair.getKey().toString());
												if (paramPair.getKey().toString().equals("deptName"))
												{
													deptCellIndex = cellIndex;
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("vendorname") || paramPair.getKey().toString().equals("vendorid"))
												{
													System.out.println(String.valueOf(cellIndex)+" >>> "+ paramPair.getValue().toString());
													vendorCellIndex = cellIndex;
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("assettype"))
												{
													assetTypeCellIndex = cellIndex;
													System.out.println(String.valueOf(cellIndex)+" >>> "+ paramPair.getValue().toString());
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												else if (paramPair.getKey().toString().equals("assetid") || paramPair.getKey().toString().equals("employeeid") || paramPair.getKey().toString().equals("supporttype"))
												{
													tempMap.put(String.valueOf(cellIndex), paramPair.getValue().toString());
												}
												columnKeyList.add(paramPair.getKey());
												dataKeyMap.put(paramPair.getKey(), paramPair.getValue().toString());
											}
										}
									}
									// Asset Mandatory Field
									if (dataKeyMap.containsKey("assetname") && dataKeyMap.containsKey("deptName"))
									{
										if (dataKeyMap.containsKey("deptName"))
										{
											setColumnNameMap(dataKeyMap);
										}
										else
										{
											setColumnNameMap(dataKeyMap);
										}
									}
									// Procurement Mandatory Field
									else if (dataKeyMap.containsKey("assetid") && dataKeyMap.containsKey("vendorname") || dataKeyMap.containsKey("vendorid"))
									{
										setColumnNameMap(dataKeyMap);
									}
									// Asset Support Mandatory Field
									else if (dataKeyMap.containsKey("employeeid") && dataKeyMap.containsKey("supporttype") && dataKeyMap.containsKey("deptName"))
									{
										if (dataKeyMap.containsKey("deptName"))
										{
											setColumnNameMap(dataKeyMap);
										}
										else
										{
											setColumnNameMap(dataKeyMap);
										}
									}
									// Asset Allotment Mandatory Field
									else if (dataKeyMap.containsKey("deptName"))
									{
										if (dataKeyMap.containsKey("deptName"))
										{
											setColumnNameMap(dataKeyMap);
										}
										else
										{
											setColumnNameMap(dataKeyMap);
										}
									}

								}
								else
								{
									int deptMaxId = 0;
									int vendorMaxId = 0;
									int astTypeMaxId = 0;
									for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)
									{
										if (GRBE.readExcelSheet(row, cellIndex) != null && !GRBE.readExcelSheet(row, cellIndex).equalsIgnoreCase(""))
										{
											/**
											 * add source code at here and check
											 * what column value you have get
											 * 
											 * if you have drop type value just
											 * check it and pick id of that
											 * value and and add in List Object
											 * !
											 * 
											 */
											if (tempMap.containsKey(String.valueOf(cellIndex)))
											{
												/*
												 * if(deptMap.containsKey(GRBE.readExcelSheet
												 * (row, cellIndex).toString()))
												 * {
												 * dropDownId.add(deptMap.get(GRBE
												 * .readExcelSheet(row,
												 * cellIndex).toString()));
												 * dropDownValue
												 * .add(GRBE.readExcelSheet(row,
												 * cellIndex).toString()); }
												 * if(subDeptMap
												 * .containsKey(GRBE
												 * .readExcelSheet(row,
												 * cellIndex).toString())) {
												 * dropDownId
												 * .add(subDeptMap.get(
												 * GRBE.readExcelSheet(row,
												 * cellIndex).toString()));
												 * dropDownValue
												 * .add(GRBE.readExcelSheet(row,
												 * cellIndex).toString()); }
												 */

												if (deptMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(deptMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
													deptMaxId = Integer.valueOf(deptMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
												}
												else if (deptCellIndex == cellIndex)
												{
													String id = null, value = null;
													Iterator<Entry<String, String>> hiterator = deptMap.entrySet().iterator();
													while (hiterator.hasNext())
													{
														Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
														System.out.println(paramPair.getKey().toString()+" >>>> "+(GRBE.readExcelSheet(row, cellIndex).toString().trim()));
														if (paramPair.getKey().toString().trim().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
														{
															String query = "SELECT id FROM department WHERE deptName='" + GRBE.readExcelSheet(row, cellIndex).toString() + "'";
															System.out.println(query);
															dataList = cbt.executeAllSelectQuery(query, connectionSpace);
															if (dataList != null && dataList.size() > 0)
																id = dataList.get(0).toString();

															value = GRBE.readExcelSheet(row, cellIndex).toString();
														}
													}

													if (id != null && value != null && id != "" && value != "")
													{
														dropDownId.add(id);
														dropDownValue.add(value);
														deptMaxId = Integer.valueOf(id);
													}
													else
													{
														String levelOforganization = null, level2 = null;
														List level2Tenp = new ArrayList<String>();
														level2Tenp.add("id");
														level2Tenp.add("locCompanyName");
														List<String> colname = new ArrayList<String>();
														colname.add("orgLevel");
														colname.add("levelName");
														level2Tenp = cbt.viewAllDataEitherSelectOrAll("organization_level2", level2Tenp, connectionSpace);
														List orgntnlevel = cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level", colname, connectionSpace);

														// getting the current
														// logined user
														// organization level
														if (orgntnlevel != null)
														{
															for (Object c : orgntnlevel)
															{
																Object[] dataC = (Object[]) c;
																levelOforganization = dataC[0].toString();
															}
														}
														else
														{
															levelOforganization = "2";
														}

														if (level2Tenp != null)
														{
															for (Object c : level2Tenp)
															{
																Object[] dataC = (Object[]) c;
																level2 = dataC[0].toString();
															}
														}
														Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
														dataWithColumnName.put("deptName", GRBE.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("mappedOrgnztnId", level2);
														dataWithColumnName.put("orgnztnlevel", levelOforganization);

														deptMaxId = new EmpExcelUploadHelper().saveData("mapped_dept_config_param", "dept_config_param", "department", dataWithColumnName);
														if (deptMaxId > 0)
														{
															dropDownId.add(String.valueOf(deptMaxId));
															dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
															deptMap.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(deptMaxId));
														}
													}
												}

												else if (vendorCellIndex == cellIndex)
												{
													String id = null, value = null;
													Iterator<Entry<String, String>> hiterator = vendorMap.entrySet().iterator();
													while (hiterator.hasNext())
													{
														Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
														if (paramPair.getKey().toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
														{
															String query = "SELECT id FROM createvendor_master WHERE vendorname='" + GRBE.readExcelSheet(row, cellIndex).toString() + "'";
															dataList = cbt.executeAllSelectQuery(query, connectionSpace);
															if (dataList != null && dataList.size() > 0)
																id = dataList.get(0).toString();

															value = GRBE.readExcelSheet(row, cellIndex).toString();
														}
													}
													if (id != null && value != null && id != "" && value != "")
													{
														dropDownId.add(id);
														dropDownValue.add(value);
														vendorMaxId = Integer.valueOf(id);
													}
													else
													{
														Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
														dataWithColumnName.put("vendorname", GRBE.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("vendorfor", GRBE.readExcelSheet(row, cellIndex).toString());
														vendorMaxId = new EmpExcelUploadHelper().saveData("mapped_vendor_master", "vendor_master", "createvendor_master", dataWithColumnName);
														if (vendorMaxId > 0)
														{
															dropDownId.add(String.valueOf(vendorMaxId));
															dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
															vendorMap.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(vendorMaxId));
														}
													}
												}
												
												else if (assetTypeCellIndex == cellIndex)
												{
													String id = null, value = null;
													Iterator<Entry<String, String>> hiterator = assetTypeMap.entrySet().iterator();
													while (hiterator.hasNext())
													{
														Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
														if (paramPair.getKey().toString().equalsIgnoreCase(GRBE.readExcelSheet(row, cellIndex).toString()))
														{
															String query = "SELECT id FROM createasset_type_master WHERE assetSubCat='" + GRBE.readExcelSheet(row, cellIndex).toString() + "'";
															dataList = cbt.executeAllSelectQuery(query, connectionSpace);
															if (dataList != null && dataList.size() > 0)
																id = dataList.get(0).toString();

															value = GRBE.readExcelSheet(row, cellIndex).toString();
														}
													}

													if (id != null && value != null && id != "" && value != "")
													{
														dropDownId.add(id);
														dropDownValue.add(value);
														astTypeMaxId = Integer.valueOf(id);
													}
													else
													{
														Map<String, String> dataWithColumnName = new LinkedHashMap<String, String>();
														dataWithColumnName.put("assetCategory", GRBE.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("assetSubCat", GRBE.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("brief", GRBE.readExcelSheet(row, cellIndex).toString());
														dataWithColumnName.put("status", "Active");
														astTypeMaxId = new EmpExcelUploadHelper().saveData("mapped_asset_type_master", "asset_type_master", "createasset_type_master", dataWithColumnName);
														if (astTypeMaxId > 0)
														{
															dropDownId.add(String.valueOf(astTypeMaxId));
															dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
															assetTypeMap.put(GRBE.readExcelSheet(row, cellIndex).toString(), String.valueOf(astTypeMaxId));
														}
													}
												}

												if (assetMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(assetMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
												}
												/*
												 * if(vendorMap.containsKey(GRBE.
												 * readExcelSheet(row,
												 * cellIndex).toString())) {
												 * dropDownId
												 * .add(vendorMap.get(GRBE
												 * .readExcelSheet(row,
												 * cellIndex).toString()));
												 * dropDownValue
												 * .add(GRBE.readExcelSheet(row,
												 * cellIndex).toString()); }
												 */
												if (employeeMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(employeeMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
												}
												if (suprtTypeMap.containsKey(GRBE.readExcelSheet(row, cellIndex).toString()))
												{
													dropDownId.add(suprtTypeMap.get(GRBE.readExcelSheet(row, cellIndex).toString()));
													dropDownValue.add(GRBE.readExcelSheet(row, cellIndex).toString());
												}
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
					System.out.println(gridListData.size()+">>>>"+columnKeyList.size());
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
			List<String> gridListData = new ArrayList<String>();
			if (gridListData.size() > 0)
			{
				gridListData.clear();
			}
			gridListData = (List<String>) session.get("gridListData");
			List<String> columnKeyList = (List<String>) session.get("columnKeyList");
			List<Object> tempList = new ArrayList<Object>();
			if (gridListData != null && gridListData.size() > 0)
			{
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
				setRecords(gridDataList.size());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
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

	@SuppressWarnings({ "unchecked", "static-access" })
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
				String receivedOn =null;
				if (upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
				{
					statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				}
				else if (upload4 != null && !upload4.equals("") && upload4.equals("assetSupport"))
				{
					statusColName = Configuration.getConfigurationData("mapped_asset_support", accountID, connectionSpace, "", 0, "table_name", "asset_support");
				}
				else if (upload4 != null && !upload4.equals("") && upload4.equals("allotmentDetail"))
				{
					statusColName = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
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
						if (gdp.getColomnName().equalsIgnoreCase("userName"))
							userTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("date"))
							dateTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("time"))
							timeTrue = true;
						else if (gdp.getColomnName().equalsIgnoreCase("flag"))
							workStatus = true;
					}
					if (upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
					{
						cbt.createTable22("asset_detail", tableColume, connectionSpace);
						
						tableColume.clear();
						TableColumes ob1 = new TableColumes();
						ob1.setColumnname("assetid");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("today_amount");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("depreciation_rate");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("amount_deducted");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("next_update_on");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("next_update_at");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						cbt.createTable22("asset_depreciation_detail", tableColume, connectionSpace);
						
						tableColume.clear();
						ob1 = new TableColumes();
						ob1.setColumnname("asset_depreciation_id");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("witten_down_value");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("amount_deducted");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						ob1 = new TableColumes();
						ob1.setColumnname("date");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
						
						cbt.createTable22("asset_depreciation_yearly_log", tableColume, connectionSpace);
						/*
						if (status1)
						{
							new AssetUniversalHelper().altTabSetUnique("asset_detail", "serialno", connectionSpace);
						}*/
					}
					else if (upload4 != null && !upload4.equals("") && upload4.equals("assetSupport"))
					{
						boolean status1 = cbt.createTable22("asset_support_detail", tableColume, connectionSpace);
						if (status1)
						{
							new AssetUniversalHelper().altTabSetUnique("asset_support_detail", "assetid", connectionSpace);
						}
					}
					else if (upload4 != null && !upload4.equals("") && upload4.equals("allotmentDetail"))
					{
						boolean status1 = cbt.createTable22("asset_allotment_detail", tableColume, connectionSpace);
						if (status1)
						{
							new AssetUniversalHelper().altTabSetUnique("asset_allotment_detail", "assetid", connectionSpace);
						}
					}

				}

				List<String> gridListData = (List<String>) session.get("gridListData");
				List<String> columnKeyList = (List<String>) session.get("columnKeyList");
				List<String> dropDownValue = (List<String>) session.get("dropDownValue");
				List<String> dropDownId = (List<String>) session.get("dropDownId");
				ArrayList<InsertDataTable> insertData = null;
				List<InsertDataTable> insertDataForDepreciation=null;
				InsertDataTable ob = null;
				session.remove("gridListData");
				session.remove("columnKeyList");
				session.remove("dropDownValue");
				session.remove("dropDownId");
				int tempIndex = 0;
				for (int index = 0; index < gridListData.size() / columnKeyList.size(); index++)
				{
					insertData = new ArrayList<InsertDataTable>();
					insertDataForDepreciation = new ArrayList<InsertDataTable>();
					int qty = 0;
					float totalamount = 0.0f;
					String deptId = null;
					for (int index4Col = 0; index4Col < columnKeyList.size(); index4Col++)
					{
						if (columnKeyList.get(index4Col).toString().equals("deptName"))
						{

							for (int i = 0; i < dropDownValue.size(); i++)
							{
								if (gridListData.get(tempIndex).toString() != null && gridListData.get(tempIndex).toString() != "" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
								{
									ob = new InsertDataTable();
									ob.setColName("dept_subdept");
									ob.setDataName(dropDownId.get(i).toString());
									deptId = dropDownId.get(i).toString();
									System.out.println(dropDownId.get(i).toString()+" >>> "+dropDownValue.get(i).toString());
									insertData.add(ob);
									dropDownId.remove(i);
									dropDownValue.remove(i);
									break;
								}
							}
							ob = new InsertDataTable();
							ob.setColName("deptHierarchy");
							ob.setDataName("1");
							insertData.add(ob);
						}
						/*else if (columnKeyList.get(index4Col).toString().equals("subdeptname") && session.get("userDeptLevel").toString().equals("2"))
						{
							for (int i = 0; i < dropDownValue.size(); i++)
							{
								if (gridListData.get(tempIndex).toString() != null && gridListData.get(tempIndex).toString() != "" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
								{
									ob = new InsertDataTable();
									ob.setColName("dept_subdept");
									ob.setDataName(dropDownId.get(i).toString());
									deptId = dropDownId.get(i).toString();
									insertData.add(ob);
									dropDownId.remove(i);
									dropDownValue.remove(i);
									break;
								}
							}

							ob = new InsertDataTable();
							ob.setColName("deptHierarchy");
							ob.setDataName("2");
							insertData.add(ob);
						}*/
						else if (columnKeyList.get(index4Col).toString().equals("assetid") || columnKeyList.get(index4Col).toString().equals("vendorname") || columnKeyList.get(index4Col).toString().equals("vendorid") || columnKeyList.get(index4Col).toString().equals("employeeid") || columnKeyList.get(index4Col).toString().equals("supporttype")  || columnKeyList.get(index4Col).toString().equals("assettype"))
						{
							if (gridListData.get(tempIndex).toString() != null && gridListData.get(tempIndex).toString() != "")
							{
								for (int i = 0; i < dropDownValue.size(); i++)
								{
									if (gridListData.get(tempIndex).toString() != null && gridListData.get(tempIndex).toString() != "" && dropDownValue.get(i).toString().equalsIgnoreCase(gridListData.get(tempIndex).toString()))
									{
										ob = new InsertDataTable();
										ob.setColName(columnKeyList.get(index4Col));
										ob.setDataName(dropDownId.get(i).toString());
										insertData.add(ob);
										dropDownId.remove(i);
										dropDownValue.remove(i);
										break;
									}
								}

							}
						}
						else if (gridListData.get(tempIndex).toString() != null && gridListData.get(tempIndex).toString() != "" && !columnKeyList.get(index4Col).toString().equals("deptName") && !columnKeyList.get(index4Col).toString().equals("subdeptname") && !columnKeyList.get(index4Col).toString().equals("astcategory") && !columnKeyList.get(index4Col).toString().equals("flag")
								&& !columnKeyList.get(index4Col).toString().equals("serialno") && !columnKeyList.get(index4Col).toString().equals("assettype") && upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
						{
							if (columnKeyList.get(index4Col).toString().equalsIgnoreCase("quantity"))
							{
								qty = Integer.valueOf(gridListData.get(tempIndex).toString());
							}
							else if (columnKeyList.get(index4Col).toString().equalsIgnoreCase("totalamount"))
							{
								totalamount = Float.valueOf(gridListData.get(tempIndex).toString());
							}
							else
							{
								ob = new InsertDataTable();
								ob.setColName(columnKeyList.get(index4Col).toString());
								// for Date Check With (YYYY-DD-MM OR YY-D-M)
								if (gridListData.get(tempIndex).toString().matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
								{
									if(columnKeyList.get(index4Col).toString().equalsIgnoreCase("receivedOn"))
									{
										receivedOn=DateUtil.convertHyphenDateToUSFormat(gridListData.get(tempIndex).toString());
										ob.setDataName(DateUtil.convertHyphenDateToUSFormat(gridListData.get(tempIndex).toString()));
									}
									else
									{
										ob.setDataName(DateUtil.convertHyphenDateToUSFormat(gridListData.get(tempIndex).toString()));
									}
									
								}
								// for Date Check With (DD/MM/YYYY OR D/M/YY)
								else if (gridListData.get(tempIndex).toString().matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
								{
									if(columnKeyList.get(index4Col).toString().equalsIgnoreCase("receivedOn"))
									{
										receivedOn=DateUtil.convertSlashDateToUSFormat(gridListData.get(tempIndex).toString());
										ob.setDataName(DateUtil.convertSlashDateToUSFormat(gridListData.get(tempIndex).toString()));
									}
									else
									{
										ob.setDataName(DateUtil.convertSlashDateToUSFormat(gridListData.get(tempIndex).toString()));
									}
								}
								else
								{
									ob.setDataName(gridListData.get(tempIndex).toString());
								}
								insertData.add(ob);
							}
						}
						else if (upload4 != null && !upload4.equals("") && upload4.equals("assetSupport") && !columnKeyList.get(index4Col).toString().equals("deptName") && !columnKeyList.get(index4Col).toString().equals("subdeptname"))
						{
							ob = new InsertDataTable();
							ob.setColName(columnKeyList.get(index4Col).toString());
							// for Date Check With (YYYY-DD-MM OR YY-D-M)
							if (gridListData.get(tempIndex).toString().matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
							{
								ob.setDataName(DateUtil.convertHyphenDateToUSFormat(gridListData.get(tempIndex).toString()));
							}
							// for Date Check With (DD/MM/YYYY OR D/M/YY)
							else if (gridListData.get(tempIndex).toString().matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
							{
								ob.setDataName(DateUtil.convertSlashDateToUSFormat(gridListData.get(tempIndex).toString()));
							}
							else
							{
								ob.setDataName(gridListData.get(tempIndex).toString());
							}
							insertData.add(ob);
						}
						else if (upload4 != null && !upload4.equals("") && upload4.equals("allotmentDetail") && !columnKeyList.get(index4Col).toString().equals("deptName") && !columnKeyList.get(index4Col).toString().equals("subdeptname") && !columnKeyList.get(index4Col).toString().equals("flag"))
						{
							ob = new InsertDataTable();
							ob.setColName(columnKeyList.get(index4Col).toString());
							// for Date Check With (YYYY-DD-MM OR YY-D-M)
							if (gridListData.get(tempIndex).toString().matches("^(?:[0-9]{2})?[0-9]{2}-(3[01]|[12][0-9]|0?[1-9])-(1[0-2]|0?[1-9])$"))
							{
								ob.setDataName(DateUtil.convertHyphenDateToUSFormat(gridListData.get(tempIndex).toString()));
							}
							// for Date Check With (DD/MM/YYYY OR D/M/YY)
							else if (gridListData.get(tempIndex).toString().matches("^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$"))
							{
								ob.setDataName(DateUtil.convertSlashDateToUSFormat(gridListData.get(tempIndex).toString()));
							}
							else
							{
								ob.setDataName(gridListData.get(tempIndex).toString());
							}
							insertData.add(ob);
						}
						tempIndex++;
					}
					if (userTrue)
					{
						ob = new InsertDataTable();
						ob.setColName("userName");
						ob.setDataName(userName);
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
						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
					}
					if (workStatus)
					{
						ob = new InsertDataTable();
						ob.setColName("flag");
						ob.setDataName("1");
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Active");
						insertData.add(ob);
						
						/*
						 * if(upload4!=null && !upload4.equals("") &&
						 * upload4.equals("assetDetail")) { ob=new
						 * InsertDataTable(); ob.setColName("assettype");
						 * ob.setDataName("0"); insertData.add(ob); }
						 */
					}
					if (upload4 != null && !upload4.equals("") && upload4.equals("assetDetail"))
					{

						ob = new InsertDataTable();
						ob.setColName("quantity");
						ob.setDataName("1");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("totalamount");
						ob.setDataName(String.valueOf(totalamount / qty));
						insertData.add(ob);
						
						// data insert for asset Depreciation
						ob = new InsertDataTable();
						ob.setColName("today_amount");
						ob.setDataName(String.valueOf(totalamount / qty));
						insertDataForDepreciation.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("depreciation_rate");
						ob.setDataName("0.0707");
						insertDataForDepreciation.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("next_update_on");
						ob.setDataName(receivedOn);
						insertDataForDepreciation.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("next_update_at");
						ob.setDataName("23:30");
						insertDataForDepreciation.add(ob);
						
						HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
						String serialNo =HUH.getTicketNo(deptId, "ASTM", connectionSpace);
						
						String ticket_type=null,prefix=null;
						int sufix=0;
						
						if(serialNo!=null && !serialNo.equalsIgnoreCase(""))
						{
							List ticketSeries = new HelpdeskUniversalHelper().getAllData("ticket_series_conf", "moduleName", "ASTM", "", "", connectionSpace);
							if (ticketSeries != null && ticketSeries.size() == 1)
							{
								for (Iterator iterator = ticketSeries.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									ticket_type = object[1].toString();
								}
							}
						}
						if(ticket_type!=null && !ticket_type.equalsIgnoreCase("") && ticket_type.equalsIgnoreCase("D"))
						{
							System.out.println(serialNo);
							prefix = serialNo.substring(0, 11);
							sufix = Integer.valueOf(serialNo.substring(11, serialNo.length()));
						}
						

						for (int i = 0; i < qty; i++)
						{
							ob = new InsertDataTable();
							ob.setColName("serialno");
							if(ticket_type!=null && !ticket_type.equalsIgnoreCase("") && ticket_type.equalsIgnoreCase("D"))
							{
								ob.setDataName(prefix+(sufix+i));
							}
							else if(ticket_type!=null && !ticket_type.equalsIgnoreCase("") && ticket_type.equalsIgnoreCase("N"))
							{
								ob.setDataName(Integer.valueOf(serialNo)+i);
							}
							insertData.add(ob);
							int maxId = cbt.insertDataReturnId("asset_detail", insertData, connectionSpace);
							insertData.remove(insertData.size() - 1);
							
							ob = new InsertDataTable();
							ob.setColName("assetid");
							ob.setDataName(maxId);
							insertDataForDepreciation.add(ob);
							cbt.insertIntoTable("asset_depreciation_detail", insertDataForDepreciation, connectionSpace);
							insertDataForDepreciation.remove(insertDataForDepreciation.size() - 1);
						}
					}
					else if (upload4 != null && !upload4.equals("") && upload4.equals("assetSupport"))
					{
						new AssetUniversalHelper().insertIntoTable("asset_support_detail", insertData, connectionSpace);
					}
					else if (upload4 != null && !upload4.equals("") && upload4.equals("allotmentDetail"))
					{
						new AssetUniversalHelper().insertIntoTable("asset_allotment_detail", insertData, connectionSpace);
						cbt.insertIntoTable("asset_allotment_log", insertData, connectionSpace);
					}
				}
				addActionMessage("Upload Data Successfully Registered!!!");
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
