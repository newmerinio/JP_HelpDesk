package com.Over2Cloud.ctrl.asset;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jsoup.Connection.Request;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.asset.common.AssetUniversalHelper;
import com.Over2Cloud.ctrl.asset.common.GenericWriteExcel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class AssetCommonAction extends ActionSupport implements ServletRequestAware
{
	static final Log log = LogFactory.getLog(AssetCommonAction.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	private Map<String, String> assetDetailMap = null;
	private Map<String, String> vendorDetailMap = null;
	private double unitCost;
	private int quantity;
	private double taxes;
	private String totalAmount = "0";
	private int vendorId;
	private String assetId;
	private String assetWithSuprt = null;
	private String assetWithAllot = null;
	private Map<Integer, String> spareNameMap = null;
	private Map<String, String> checkSpareNo;
	private String spareCount;
	private String spareNameId;
	Map<Integer, String> spareCatgMap = null;
	String accountID = (String) session.get("accountid");
	private String[] columnNames;
	private String columnNames1;
	private String downloadType = null;
	private String download4 = null;
	private Map<String, String> columnMap = null;
	private Map<String, String> columnMap1 = null;
	private List<ConfigurationUtilBean> columnMap2 = null;

	private String tableName = null;
	private String tableAllis = null;
	private String excelName = null;
	private String mainHeaderName;
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
	// private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
	private String assetColCount, ast1stCol = null;
	private String PMColCount, PMc1stCol = null;
	private String suprtColCount, suprt1stCol = null;
	private String allotColCount, allot1stCol, serialNo = null;
	private String barcode = null;
	private HttpServletRequest request;
	private Properties configProp = new Properties();
	private String dialogId;
	private String catgId = null;
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private String excelFileName;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	

	@SuppressWarnings( { "unchecked", "static-access" })
	public String getAssetDetailById()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List columnList = new ArrayList<String>();
				assetDetailMap = new LinkedHashMap<String, String>();
				
				List<GridDataPropertyView> assetColumnList = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
				if (assetColumnList != null && assetColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : assetColumnList)
					{
						if (gdp.getColomnName().equalsIgnoreCase("assetname") || gdp.getColomnName().equalsIgnoreCase("assetbrand") || gdp.getColomnName().equalsIgnoreCase("assetmodel") || gdp.getColomnName().equalsIgnoreCase("serialno") || gdp.getColomnName().equalsIgnoreCase("mfgserialno") || gdp.getColomnName().equalsIgnoreCase("receivedOn"))
						{
							columnList.add(gdp.getColomnName());
						}
					}
				}
				if (assetWithSuprt != null)
				{
					// get support from column name
					String query = "Select supportfrom from asset_detail where id=" + getAssetId();
					List supportFromList = new ArrayList();
					supportFromList = cbt.executeAllSelectQuery(query, connectionSpace);

					// get support from data & support for data
					List supportList = new ArrayList();
					if (supportFromList != null && supportFromList.size() > 0)
					{
						String query1 = "Select " + supportFromList.get(0).toString() + ",supportfor from asset_detail where id=" + getAssetId();
						supportList = cbt.executeAllSelectQuery(query1, connectionSpace);
					}
					String supportfrom = null;
					String months = null;
					String supportto = null;
					if (supportList != null && supportList.size() > 0)
					{
						for (Object obj : supportList)
						{
							Object[] object = (Object[]) obj;
							if (object[0].toString() != null && object[0] != "")
							{
								supportfrom = object[0].toString();

							}
							if (object[1].toString() != null && object[1] != "")
							{
								months = object[1].toString();

							}
						}
						supportto = DateUtil.getNewDate("month", Integer.valueOf(months), supportfrom);
					}
					if (supportfrom != null && supportto != null)
					{
						assetDetailMap.put("supportfrom", DateUtil.convertDateToIndianFormat(supportfrom));
						assetDetailMap.put("supportto", DateUtil.convertDateToIndianFormat(supportto));
					}
				}
				else if (assetWithAllot != null)
				{
					StringBuilder query = new StringBuilder("");
					query.append("select emp.empName,emp.mobOne,dept.deptName");
					query.append(" from asset_allotment_detail as allot ");
					query.append(" inner join department as dept on dept.id=allot.dept_subdept ");
					query.append(" inner join compliance_contacts as cc on cc.id=allot.employeeid ");
					query.append(" inner join employee_basic as emp on emp.id=cc.emp_id ");
					query.append(" where allot.assetid="+ getAssetId());

					List tempList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (tempList != null && tempList.size() > 0)
					{
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[0] != "")
							{
								assetDetailMap.put("empid", object[0].toString());
							}
							if (object[1] != null && object[1] != "")
							{
								assetDetailMap.put("mobile", object[1].toString());
							}
							if (object[2] != null && object[2] != "")
							{
								assetDetailMap.put("dept", object[2].toString());
							}
						}
					}
				}
				String serialNo = null,dateOfPurchase=null,mfgSerialNo=null;
				
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", getAssetId());
				if (columnList != null && columnList.size() > 0)
				{
					List assetDetailList = cbt.viewAllDataEitherSelectOrAll("asset_detail", columnList, temp, connectionSpace);
					if (assetDetailList != null && assetDetailList.size() > 0)
					{
						for (Object obj : assetDetailList)
						{
							Object[] object = (Object[]) obj;
							for (int i = 0; i < columnList.size(); i++)
							{
								if (columnList.get(i).toString().equals("serialno"))
								{
									if(object[i]!=null)
									{
										serialNo = object[i].toString();
										assetDetailMap.put("assetserialno", serialNo);
									}
									else
									{
										serialNo = "NA";
										assetDetailMap.put("assetserialno", "NA");
									}
								}
								else if(columnList.get(i).toString().equals("receivedOn"))
								{
									if(object[i]!=null)
									{
										dateOfPurchase=DateUtil.convertDateToIndianFormat(object[i].toString());
									}
									else
									{
										dateOfPurchase="";
									}
								}
								else if(columnList.get(i).toString().equals("mfgserialno"))
								{
									if(object[i]!=null)
									{
										mfgSerialNo=object[i].toString();
									}
									else
									{
										mfgSerialNo="";
									}
								}
								if (object[i] != null && object[i] != "")
								{
									assetDetailMap.put(columnList.get(i).toString(), object[i].toString());
								}
								else
								{
									assetDetailMap.put(columnList.get(i).toString(), "NA");
								}
							}
						}
					}
				}
				if (barcode != null && !barcode.equalsIgnoreCase("") && serialNo != null)
				{
					String filePath = request.getSession().getServletContext().getRealPath("/");
					File TTFfilePath = new File(filePath + "/images/", "3of9.TTF");
					System.out.println(TTFfilePath.toString());
					if(TTFfilePath!=null)
					{
						String saveImgPath = request.getSession().getServletContext().getRealPath("/images/barCodeImage/");
						System.out.println("saveImgPath "+saveImgPath);
						if(saveImgPath!=null)
						{
							StringBuffer buffer = new StringBuffer();
							for (int i = 0; i < 10 - getAssetId().length(); i++)
							{
								buffer.append("0");
							}
							buffer.append(getAssetId());
							int width, height;
							String format = new String("gif");
							width = 250;
							height = 102;
							BufferedImage bufimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
							Graphics graphicsobj = bufimg.createGraphics();
							
							InputStream fin = new FileInputStream(TTFfilePath);
							Font font = Font.createFont(Font.TRUETYPE_FONT, fin);
							Font font1 = font.deriveFont(30f);
							graphicsobj.setFont(font1);
							graphicsobj.setFont(font.getFont("3 of 9 Barcode"));
							graphicsobj.setColor(Color.WHITE);

							graphicsobj.fillRect(1, 1, 248, 100);
							graphicsobj.setColor(Color.BLACK);
							((Graphics2D) graphicsobj).drawString(buffer.toString(), 30, 50);
							Font font2 = new Font("serif", Font.PLAIN, 12);
							graphicsobj.setFont(font2);
							graphicsobj.drawString("Org S/N : " + serialNo, 30, 60);
							graphicsobj.drawString("DOP : "+dateOfPurchase, 30, 70);
							graphicsobj.drawString("Mfg S/N : " + mfgSerialNo, 30, 80);
							File saveFile = new File(saveImgPath, getAssetId() + ".gif");
							
							ImageIO.write(bufimg, format, saveFile);
							//assetDetailMap.put("path", "/images/BarcodeImg/" + getAssetId() + ".gif");
						}
						
					}
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

	public String calculateTotalAmount()
	{
		String returnResult = SUCCESS;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			double total = 0.00;
			double baseValue;
			try
			{
				baseValue = (unitCost * quantity);
				total = baseValue + (baseValue * taxes) / 100;
				DecimalFormat df = new DecimalFormat("#.##");
				totalAmount = df.format(total);
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				totalAmount = "0";
				e.printStackTrace();
			}
		}
		else
		{
			totalAmount = "0";
			returnResult = LOGIN;
		}
		System.out.println(totalAmount);
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getVendorDetailById()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List columnList = new ArrayList<String>();
				vendorDetailMap = new LinkedHashMap<String, String>();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> vendorColumnList = Configuration.getConfigurationData("mapped_vendor_master", accountID, connectionSpace, "", 0, "table_name", "vendor_master");
				if (vendorColumnList != null && vendorColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : vendorColumnList)
					{
						columnList.add(gdp.getColomnName());
					}
				}
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", getVendorId());
				if (columnList != null && columnList.size() > 0)
				{
					List vendorDetailList = cbt.viewAllDataEitherSelectOrAll("createvendor_master", columnList, temp, connectionSpace);
					if (vendorDetailList != null && vendorDetailList.size() > 0)
					{
						for (Object obj : vendorDetailList)
						{
							Object[] object = (Object[]) obj;
							for (int i = 0; i < columnList.size(); i++)
							{
								vendorDetailMap.put(columnList.get(i).toString(), object[i].toString());
							}

						}
					}
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

	/*// calculate date after add month in date
	public String convertDateAfterAddMonth(String aDate, int month4Add)
	{
		String strIssueArr[] = aDate.split("-");
		String year = strIssueArr[0];
		int intYear = Integer.parseInt(year);
		int month = Integer.parseInt(strIssueArr[1]);
		int date = Integer.parseInt(strIssueArr[2]);
		int totalMonth = month + month4Add;
		int cosient = totalMonth / 12;
		int rem = totalMonth % 12;
		date = date - 1;
		if (cosient > 0 && rem != 0)
		{
			intYear = intYear + cosient;
		}
		else
		{
			intYear = intYear + cosient - 1;
		}

		if (rem < 10 && rem != 0)
		{
			aDate = intYear + "-0" + rem + "-" + date;
		}
		else if (rem == 0)
		{
			aDate = intYear + "-12-" + date;
		}
		else
		{
			aDate = intYear + "-" + rem + "-" + date;
		}
		return aDate;
	}*/

	@SuppressWarnings("unchecked")
	public String getSpareNameByCatgId()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (catgId != null && !catgId.equalsIgnoreCase(""))
				{
					spareNameMap = new LinkedHashMap<Integer, String>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String query = "Select id,spare_name from spare_detail where spare_category=" + catgId + " ORDER BY spare_name ASC";
					List temp = new ArrayList();
					temp = cbt.executeAllSelectQuery(query, connectionSpace);

					if (temp != null && temp.size() > 0)
					{
						for (Object obj : temp)
						{
							Object[] object = (Object[]) obj;
							spareNameMap.put((Integer) object[0], object[1].toString());
						}
					}
					returnResult = SUCCESS;
				}

			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSpareNameByCatgId of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getAvailSpareByCatgId()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (catgId != null && !catgId.equalsIgnoreCase(""))
				{
					spareNameMap = new LinkedHashMap<Integer, String>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String query = "Select id,spare_name from spare_detail where " + "spare_category=" + catgId + " AND id " + "in(select spare_name from nonconsume_spare_detail where total_nonconsume_spare>0) ORDER BY spare_name ASC";
					List temp = new ArrayList();
					temp = cbt.executeAllSelectQuery(query, connectionSpace);
					if (temp != null && temp.size() > 0)
					{
						for (Object obj : temp)
						{
							Object[] object = (Object[]) obj;
							spareNameMap.put((Integer) object[0], object[1].toString());
						}
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSpareNameByCatgId of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public String getTotalSpareAvailable()
	{
		String returnResult = ERROR;
		boolean validSession = new ValidateSession().checkSession();
		if (validSession)
		{
			try
			{
				if (spareCount != null && spareCount != "")
				{
					checkSpareNo = new HashMap<String, String>();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String query = "select total_nonconsume_spare from nonconsume_spare_detail where spare_name=" + spareNameId;
					List temp = new ArrayList();
					temp = cbt.executeAllSelectQuery(query, connectionSpace);
					int totalCount = 0;
					if (temp.get(0).toString() != null && !temp.get(0).toString().equalsIgnoreCase(""))
					{
						totalCount = Integer.parseInt(temp.get(0).toString());
					}
					if (totalCount >= Integer.parseInt(spareCount))
					{
						session.put("remainSpare", totalCount - Integer.parseInt(spareCount));
						checkSpareNo.put("msg", "Total " + totalCount + " Spare Exist you can issue");
						checkSpareNo.put("status", "true");
					}
					else
					{
						checkSpareNo.put("msg", "Sorry you can't issue , Total " + totalCount + " Spare Exist");
						checkSpareNo.put("status", "false");
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				log.error("@ERP::>> Problem in " + getClass() + " in method getTotalSpareAvailable()on" + DateUtil.getCurrentDateIndianFormat() + "At" + DateUtil.getCurrentTimeHourMin(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSpareCatgById()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			spareCatgMap = new LinkedHashMap<Integer, String>();
			List temp = new ArrayList<String>();
			temp.add("id");
			temp.add("spare_category");
			temp = cbt.viewAllDataEitherSelectOrAll("createspare_catg_master", temp, connectionSpace);
			if (temp != null && temp.size() > 0)
			{
				for (Object obj : temp)
				{
					Object[] object = (Object[]) obj;
					spareCatgMap.put((Integer) object[0], object[1].toString());
				}
			}
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSpareCatgById of class " + getClass(), e);
			e.printStackTrace();
		}
		return spareCatgMap;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSpare()
	{
		spareNameMap = new LinkedHashMap<Integer, String>();
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag)
		{
			try
			{
				String query = "Select id,spare_name from spare_detail ORDER BY spare_name ASC";
				List temp = new ArrayList();
				temp = cbt.executeAllSelectQuery(query, connectionSpace);
				if (temp != null && temp.size() > 0)
				{
					for (Object obj : temp)
					{
						Object[] object = (Object[]) obj;
						spareNameMap.put((Integer) object[0], object[1].toString());
					}
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getSpare of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return spareNameMap;
	}

	public String getFieldName(String mapTableName, String masterTableName, String columnName)
	{
		String returnResult = null;
		boolean sessionFlag = ValidateSession.checkSession();
		// CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag)
		{
			try
			{
				List<GridDataPropertyView> spareCatgColumnList = Configuration.getConfigurationData(mapTableName, accountID, connectionSpace, "", 0, "table_name", masterTableName);

				if (spareCatgColumnList != null && spareCatgColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : spareCatgColumnList)
					{
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							if (gdp.getColomnName().equalsIgnoreCase(columnName))
							{
								returnResult = gdp.getHeadingName();
							}
						}
					}
				}
				else
				{
					addActionMessage(mapTableName + " Table Is Not properly Configured !");
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getFieldName of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String getMasterFieldName(String masterTableName, String columnName)
	{
		String returnResult = null;
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag)
		{
			try
			{
				String query = "Select field_name from " + masterTableName + " where field_value='" + columnName + "'";
				List temp = new ArrayList();
				temp = cbt.executeAllSelectQuery(query, connectionSpace);
				if (temp != null && temp.size() > 0)
				{
					returnResult = temp.get(0).toString();
				}
				else
				{
					addActionMessage(masterTableName + " Table Is Not properly Configured !");
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getMasterFiledName of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	public String getColumn4Download()
	{
		System.out.println(">>>>>>>>> getColumn4Download  >>>>>>>>");
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (download4 != null && download4.equals("assetDetail"))
				{
					tableAllis = "ast";
					returnResult = getColumnName("mapped_asset_master", "asset_master", tableAllis);
					tableName = "asset_detail";
					excelName = "Asset detail";
				}
				if (download4 != null && download4.equals("assetSupport"))
				{
					tableAllis = "ast";
					returnResult = getColumnName("mapped_asset_reminder", "asset_reminder", tableAllis);
					tableName = "asset_reminder_details";
					excelName = "Asset Reminder details";
				}
				if (download4 != null && download4.equals("assetPreventive"))
				{
					tableAllis = "ast";
					returnResult = getColumnName("mapped_asset_reminder", "asset_reminder", tableAllis);
					tableName = "asset_reminder_details";
					excelName = "Asset Reminder details";
				}
				else if (download4 != null && download4.equals("procurementDetail"))
				{
					tableAllis = "astProc";
					returnResult = getColumnName("mapped_procurement_master", "procurement_master", tableAllis);
					tableName = "procurement_detail";
					excelName = "Procurement detail";
				}
				else if (download4 != null && download4.equals(""))
				{
					tableAllis = "astSupport";
					returnResult = getColumnName("mapped_asset_support", "asset_support", tableAllis);
					tableName = "asset_support_detail";
					excelName = "Support detail";
				}
				else if (download4 != null && download4.equals("totalAllotDetail"))
				{
					System.out.println("totalAllotDetail=======");
					tableAllis = "astTotalAllot";
					returnResult = getColumnName("mapped_allotment_master", "allotment_master", tableAllis);
					tableName = "asset_allotment_detail";
					//tableName = "asset_allotment_log";
					excelName = "Total Allotment detail";
				}
				else if (download4 != null && download4.equals("totalSoftwareDetail"))
				{
					tableAllis = "astTotalSoft";
					returnResult = getColumnName("mapped_network_monitor_config", "network_monitor_config", tableAllis);
					tableName = "network_monitor_details";
					excelName = "Total Network Monitor";
				}
				else if (download4 != null && download4.equals("totalHarwareDetail"))
				{
					System.out.println("totalHarwareDetail=======");
					tableAllis = "mds";
					returnResult = getColumnName(" ", " ", tableAllis);
					tableName = "machine_details";
					excelName = "Total Machine Details";
				}
				else if (download4 != null && download4.equals("totalRepairDetail"))
				{
					tableAllis = "astRepair";
					returnResult = getColumnName("mapped_asset_repair_master", "asset_repair_master", tableAllis);
					tableName = "asset_repair_detail";
					excelName = "Total Repair detail";
				}
				else if (download4 != null && download4.equals("totalSpareDetail"))
				{
					tableAllis = "spare";
					returnResult = getColumnName("mapped_spare_master", "spare_master", tableAllis);
					tableName = "spare_detail";
					excelName = "Total Spare Detail";
				}
				else if (download4 != null && download4.equals("totalRecvSpareDetail"))
				{
					tableAllis = "spareRecv";
					returnResult = getColumnName("mapped_spare_receive_master", "spare_receive_master", tableAllis);
					tableName = "receive_spare_detail";
					excelName = "Total Receive Spare Detail";
				}
				else if (download4 != null && download4.equals("venderDetail"))
				{
					tableAllis = "cvm";
					returnResult = getColumnName("mapped_vendor_master", "vendor_master", tableAllis);
					tableName = "createvendor_master";
					excelName = "Total Vendor Details Detail";
				}
				else if (download4 != null && download4.equals("totalIssueSpareDetail"))
				{
					tableAllis = "spareIssue";
					returnResult = getColumnName("mapped_spare_issue_master", "spare_issue_master", tableAllis);
					tableName = "spare_issue_detail";
					excelName = "Total Issue Spare Detail";
				}
				else if (download4 != null && download4.equals("totalDepreciationDetalis"))
				{
					tableAllis = "depse";
					returnResult = getColumnName(" ", " ", tableAllis);
					tableName = "asset_depreciation_detail";
					excelName = "Total Depreciation Detail";
				}
				else if (download4 != null && download4.equals("allModuleDetail"))
				{
					returnResult = getAllModuleColumnName();
					excelName = "Total All Asset Detail";
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getColumn4Download of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		System.out.println("returnResult "+returnResult);
		return returnResult;
	}



	@SuppressWarnings("unchecked")
	public String getColumnName(String mappedTableName, String basicTableName, String allias)
	{
		System.out.println(";;;;;;;;;;  getColumnName ;;;;;;basicTableName;;"+basicTableName);
		String returnResult = ERROR;
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name", basicTableName);
			columnMap = new LinkedHashMap<String, String>();
			System.out.println("columnMap" + columnMap);
			columnMap2 = new ArrayList<ConfigurationUtilBean>();
			System.out.println("columnMap2" + columnMap2);
			System.out.println("columnList" + columnList);
			if (columnList != null && columnList.size() > 0)
			{
				System.out.println("totalHarwareDetail~~~~~~~~~~~~");
				if (downloadType != null && downloadType.equals("downloadExcel"))
				{
					System.out.println("if blockkkkkkkk~~~~~~~~~~~~~");
					for (GridDataPropertyView gdp1 : columnList)
					{
						if (gdp1.getColomnName().equals("dept_subdept"))
						{
							columnMap.put("dept.deptName", new AssetCommonAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName"));
						}
						else if (gdp1.getColomnName().equals("employeeid"))
						{
							columnMap.put("emp.empName", new AssetCommonAction().getFieldName("mapped_employee_basic_configuration", "employee_basic_configuration", "empName"));
						}
						else if (gdp1.getColomnName().equals("assetid"))
						{
							columnMap.put("asset.assetname", new AssetCommonAction().getFieldName("mapped_asset_master", "asset_master", "assetname"));
						}
						else if (gdp1.getColomnName().equals("vendorname") || gdp1.getColomnName().equals("vendorid"))
						{
							columnMap.put("cvm.vendorname", new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname"));
						}
						else if(gdp1.getColomnName().equals("vendorfor"))
						{
							columnMap.put("vtd.vendor_for", new AssetCommonAction().getMasterFieldName("vendor_type_config", "vendor_for"));
						}
						/*else if (gdp1.getColomnName().equals("supporttype"))
						{
							columnMap.put("cscmstr.support_type", new AssetCommonAction().getMasterFieldName("asset_category_master", "support_type"));
						}*/
						else if (gdp1.getColomnName().equals("supporttype"))
						{
							columnMap.put("cscmstr.category", new AssetCommonAction().getMasterFieldName("asset_category", "asset_category"));
						}
						else if (gdp1.getColomnName().equals("spare_category"))
						{
							columnMap.put("spc.spare_category", new AssetCommonAction().getMasterFieldName("spare_catg_master", "spare_category"));
						}
						else if (gdp1.getColomnName().equals("spare_name") && !basicTableName.equalsIgnoreCase("spare_master"))
						{
							columnMap.put("spr.spare_name", new AssetCommonAction().getFieldName("mapped_spare_master", "spare_master", "spare_name"));
						}
						else if (!gdp1.getColomnName().equals("deptHierarchy") 
								&& !gdp1.getColomnName().equalsIgnoreCase("userName") 
								&& !gdp1.getColomnName().equalsIgnoreCase("date") 
								&& !gdp1.getColomnName().equalsIgnoreCase("time")
								&& !gdp1.getColomnName().equalsIgnoreCase("status")
								&& !gdp1.getColomnName().equalsIgnoreCase("escalation")
								&& !gdp1.getColomnName().equalsIgnoreCase("l1EscDuration")
								&& !gdp1.getColomnName().equalsIgnoreCase("l2EscDuration")
								&& !gdp1.getColomnName().equalsIgnoreCase("l3EscDuration")
								&& !gdp1.getColomnName().equalsIgnoreCase("l4EscDuration")
								&& !gdp1.getColomnName().equalsIgnoreCase("actionStatus")
								&& !gdp1.getColomnName().equalsIgnoreCase("actionTaken")
								&& !gdp1.getColomnName().equalsIgnoreCase("moduleName")
								&& !gdp1.getColomnName().equalsIgnoreCase("reminder1")
								&& !gdp1.getColomnName().equalsIgnoreCase("reminder2")
								&& !gdp1.getColomnName().equalsIgnoreCase("reminder3")
								)
						{
							if(download4.equals("assetSupport"))
							{
								columnMap.put("asset.serialno", "Asset Code");
								columnMap.put("asset.assetbrand", "Brand");
								columnMap.put("asset.assetmodel", "Model");
								columnMap.put("asset.supportfrom", "Support From");
								System.out.println("vaues of columnMap"+columnMap.values());
							}
							
							else if(download4.equals("assetPreventive"))
							{
								columnMap.put("asset.serialno", "Asset Code");
								columnMap.put("asset.assetbrand", "Brand");
								columnMap.put("asset.assetmodel", "Model");
								columnMap.put("asset.supportfrom", "Support From");
							}
							columnMap.put(allias + "." + gdp1.getColomnName(), gdp1.getHeadingName());
						}
					}	
					
				}
				else
				{
					
					for (GridDataPropertyView gdp1 : columnList)
					{
						String firstValue= null;
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp1.getColomnName().equals("dept_subdept"))
						{
							firstValue = new AssetCommonAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName");
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey("deptName");
							obj.setValue(firstValue);
							columnMap2.add(obj);
							columnMap.put("deptName", firstValue);
						}
						else if (gdp1.getColomnName().equals("employeeid"))
						{
							firstValue = new AssetCommonAction().getFieldName("mapped_employee_basic_configuration", "employee_basic_configuration", "mobOne");
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey("employeeid");
							obj.setValue(firstValue);
							columnMap2.add(obj);
							columnMap.put("employeeid", firstValue);
						}
						/*
						 * else if(gdp1.getColomnName().equals("assetid")) {
						 * columnMap.put("assetname", new
						 * AssetCommonAction().getFieldName
						 * ("mapped_asset_master", "asset_master",
						 * "assetname")); }
						 */
						else if (gdp1.getColomnName().equals("vendorname") || gdp1.getColomnName().equals("vendorid"))
						{
							if (gdp1.getColomnName().equals("vendorname"))
							{
								firstValue = new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname");
								if (gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey("vendorname");
								obj.setValue(firstValue);
								columnMap2.add(obj);
								columnMap.put("vendorname", firstValue);
							}
							else
							{
								firstValue = new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname");
								if (gdp1.getMandatroy().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								obj.setKey("vendorid");
								obj.setValue(firstValue);
								columnMap2.add(obj);
								columnMap.put("vendorid", firstValue);
							}
						}
						else if (gdp1.getColomnName().equals("supporttype"))
						{
							firstValue = new AssetCommonAction().getMasterFieldName("asset_category_master", "support_type");
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey("supporttype");
							obj.setValue(firstValue);
							columnMap2.add(obj);
							columnMap.put("supporttype", firstValue);
						}
						else if (gdp1.getColomnName().equals("spare_category"))
						{
							firstValue = new AssetCommonAction().getMasterFieldName("spare_catg_master", "spare_category");
							if (gdp1.getMandatroy().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							obj.setKey("spare_category");
							obj.setValue(firstValue);
							columnMap2.add(obj);
							columnMap.put("spare_category", firstValue);
						}
						else if (gdp1.getColomnName().equals("spare_name"))
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
							columnMap2.add(obj);
							columnMap.put("spare_name", firstValue);
						}
						else if (!gdp1.getColomnName().equals("deptHierarchy") 
								&& !gdp1.getColomnName().equals("userName") 
								&& !gdp1.getColomnName().equals("date") 
								&& !gdp1.getColomnName().equals("time"))
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
							columnMap2.add(obj);
							columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
						}
					}
				}
				System.out.println("columnMap ::::::::"+columnMap .size());
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
			}
			else 
			{
				if(download4.equals("totalDepreciationDetalis"))
				{
					System.out.println("+++++++++++++++++++++++");
					columnMap.put("ad.assetname", "Asset Name");
					columnMap.put("ad.serialno", "Asset Code");
					columnMap.put("ad.totalamount", "Total Amount");
					columnMap.put("depse.depreciation_rate", "Depreciation Charge");
					columnMap.put("depse.amount_deducted", "Deducted Amount");
					columnMap.put("depse.today_amount", "Current Amount");
					
				}
				else if(download4.equals("totalHarwareDetail"))
				{
				columnMap.put("machine_id", "Machine_id");
				columnMap.put("ip", "IP");
				columnMap.put("os_name", "OS Name");
				columnMap.put("systemManufacturer", "Manufacturer");
				columnMap.put("systemModel", "System Model");
				columnMap.put("BIOSVersion", "BIOS Version");
				columnMap.put("windowsDirectory", "Directory");
				columnMap.put("timeZone", "Time Zone");
				columnMap.put("OSInstallDate", "OS Install Date");
				columnMap.put("OSVersion", "OS Version");
				columnMap.put("domain", "Domain");
				columnMap.put("hd_serialno", "HD Serial");
				columnMap.put("hd_partitaion", "HD Partitaion");
				columnMap.put("ram_details", "RAM Details");
				columnMap.put("process_list", "Process List");
				columnMap.put("temp_file_size", "Temp Size");
				columnMap.put("software_inventory", "Software Inventory");
				}
				System.out.println("columnMap ::::::::"+columnMap .size());
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
			}
			
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method getColumnName of class " + getClass(), e);
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String downloadExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					tempMap.remove("status");
					tempMap.remove("flag");
				
					List dataList = null;
					StringBuilder query = new StringBuilder("");
					for (int index = 0; index < keyList.size(); index++)
					{
						if (tempMap.get(keyList.get(index))!=null) 
						{
							titleList.add(tempMap.get(keyList.get(index)));
						}
						//System.out.println("tempMap.get(keyList.get(index)      cccccccccc" +tempMap.get(keyList.get(index)));
					}
					System.out.println("DOWNLOAD TYPE ::::   "+downloadType);
					if (downloadType.equals("downloadExcel"))
					{
						String dept_subdept_id = "";
						List empData = new AssetUniversalHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel, connectionSpace);
						if (empData != null && empData.size() > 0)
						{
							for (Iterator iterator = empData.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								dept_subdept_id = object[3].toString();
							}
						}
						if (keyList != null && keyList.size() > 0)
						{
							query.append("select ");
							int i = 0;
							for (Iterator it = keyList.iterator(); it.hasNext();)
							{
								Object obdata = (Object) it.next();
								if (obdata != null)
								{
									if (i < keyList.size() - 1)
									{
										query.append(obdata.toString() + ",");
									}
									else
									{
										query.append(obdata.toString());
									}
								}
								i++;
							}
							// changes---------------
							query.append(" from " + tableName + " as " + tableAllis);
							/*if (keyList.contains("sdept.subdeptname") && !keyList.contains("dept.deptName"))
							{
								query.append(" left join subdepartment sdept on sdept.id=" + tableAllis + ".dept_subdept");
							}*/
							if (keyList.contains("dept.deptName") && session.get("userDeptLevel").toString().equals("2"))
							{
								query.append(" left join department dept on dept.id=" + tableAllis + ".dept_subdept ");
							}
							/*if (keyList.contains("dept.deptName") && session.get("userDeptLevel").toString().equals("1"))
							{
								query.append(" left join department dept on dept.id=" + tableAllis + ".dept_subdept");
							}*/
							if (keyList.contains("emp.empName"))
							{
								query.append(" left join employee_basic emp on emp.id=" + tableAllis + ".employeeid");
							}
							if (keyList.contains("asset.assetname"))
							{
								query.append(" left join asset_detail as asset on asset.id=" + tableAllis + ".assetid");
							}
							/*if (keyList.contains("vendor.vendorname"))
							{
								query.append(" left join createvendor_master vendor on vendor.id=" + tableAllis + ".vendorname");
							}*/
							if (keyList.contains("vtd.vendor_for"))
							{
								query.append(" left join  vendor_type_detail as vtd on vtd.id=" + tableAllis + ".vendorfor OR cvm.vendorname=cvm.id");
							}
							if (keyList.contains("cvm.vendorname")&& !tableName.equalsIgnoreCase("createvendor_master"))
							{
								query.append(" left join  createvendor_master as cvm on cvm.id=" + tableAllis + ".vendorname");
							}
							/*if (keyList.contains("cscmstr.support_type"))
							{
								query.append(" left join createasset_support_catg_master as cscmstr on cscmstr.id=" + tableAllis + ".supporttype");
							}*/
							if (keyList.contains("cscmstr.category"))
							{
								query.append(" left join createasset_support_catg_master as cscmstr on cscmstr.category=" + tableAllis + ".id where ast.moduleName='Support' ");
							}
							if (keyList.contains("spc.spare_category"))
							{
								query.append(" left join createspare_catg_master spc on spc.id=" + tableAllis + ".spare_category");
							}
							if (keyList.contains("spr.spare_name") && !tableName.equalsIgnoreCase("spare_detail"))
							{
								query.append(" left join spare_detail spr on spr.id=" + tableAllis + ".spare_name");
							}
							/*if (keyList.contains("dept.deptName") && session.get("userDeptLevel").toString().equals("2"))
							{
								query.append(" where " + tableAllis + ".dept_subdept=" + dept_subdept_id);
							}*/
							if (keyList.contains("ad.assetname") 
									|| keyList.contains("ad.serialno")
									|| keyList.contains("ad.totalamount")
									&& !tableName.equalsIgnoreCase("asset_detail"))
							{
								query.append(" inner join asset_detail as ad on ad.id=" + tableAllis + ".assetid");
							}
							System.out.println("query::"+query.toString());
							dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							if (dataList != null && dataList.size() > 0)
							{
								excelFileName = new GenericWriteExcel().createExcel("Dreamsol Telesolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
								System.out.println("ORGE NAMEeeeeee ===  "+session.get("orgname"));
								System.out.println("excelFileName :"+excelFileName);
								if (session.containsKey("columnMap"))
								{
									session.remove("columnMap");
								}
							}
						}
					}
					else if (downloadType != null && downloadType.equals("uploadExcel"))
					{
						excelFileName = new GenericWriteExcel().createExcel(session.get("orgname").toString(), excelName, dataList, titleList, null, excelName);
					}
					else if (downloadType != null && downloadType.equals("downloadAllData"))
					{
						System.out.println("If download type=  " + downloadType);
						List empData = new AssetUniversalHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel, connectionSpace);
						if (empData != null && empData.size() > 0)
						{
							for (Iterator iterator = empData.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
							}
						}
						query.append("select ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								System.out.println(">>>>>>>colname ====     "+obdata.toString());
								if (!obdata.toString().equalsIgnoreCase("flag")) 
								{
									if (i < keyList.size() - 1)
									{
										if(obdata.toString().equalsIgnoreCase("vendorname"))
										{
											query.append("vendor."+obdata.toString()+ ",");
										}
										else if(obdata.toString().equalsIgnoreCase("deptName"))
										{
											query.append("dept.deptName,");
										}
										else if(obdata.toString().equalsIgnoreCase("assettype"))
										{
											query.append("at.assetSubCat,");
										}
										else if(obdata.toString().equalsIgnoreCase("supportfor"))
										{
											query.append("concat(asd.supportfor,' Months'),");
										}
										else if(!obdata.toString().equalsIgnoreCase("deptHierarchy") && !obdata.toString().equalsIgnoreCase("date") && !obdata.toString().equalsIgnoreCase("time") && !obdata.toString().equalsIgnoreCase("userName") && !obdata.toString().equalsIgnoreCase("flag") && !obdata.toString().equalsIgnoreCase("status"))
										{
											query.append("asd."+obdata.toString() + ",");
										}
									}
									else
									{
										query.append("asd."+obdata.toString());
										System.out.println("query======="+query);
									}
								}
							}
							i++;
						}
						query.append(" from asset_detail as asd");
						query.append(" inner join createvendor_master as vendor on vendor.id=asd.vendorname");
						query.append(" inner join createasset_type_master as at on at.id=asd.assettype");
						query.append(" inner join department as dept on dept.id=asd.dept_subdept where asd.status='Active'");
						System.out.println("queryvvvvvvvvvv"  +query);

						if (keyList.contains("astdept.deptName") && session.get("userDeptLevel").toString().equals("2"))
						{
							query.append(" left join subdepartment astsdept on astsdept.id=ast.dept_subdept left join department astdept on astdept.id=astsdept.deptid");
						}
						if (keyList.contains("astdept.deptName") && session.get("userDeptLevel").toString().equals("1"))
						{
							query.append(" left join department astdept on astdept.id=ast.dept_subdept");
						}
						if (keyList.contains("astvendor.vendorname"))
						{
							query.append(" left join createvendor_master astvendor on astvendor.id=ast.vendorname");
						}
						if (keyList.contains("astProcvendor.vendorname"))
						{
							query.append(" left join createvendor_master astProcvendor on astProcvendor.id=astProc.vendorname");
						}
						if (keyList.contains("astSuprtsdept.subdeptname") && !keyList.contains("astSuprtdept.deptName"))
						{
							query.append(" left join subdepartment astSuprtsdept on astSuprtsdept.id=astSuprt.dept_subdept");
						}
						if (keyList.contains("astSuprtdept.deptName") && session.get("userDeptLevel").toString().equals("2"))
						{
							query.append(" left join subdepartment astSuprtsdept on astSuprtsdept.id=astSuprt.dept_subdept left join department astSuprtdept on astSuprtdept.id=astSuprtsdept.deptid");
						}
						if (keyList.contains("astSuprtdept.deptName") && session.get("userDeptLevel").toString().equals("1"))
						{
							query.append(" left join department astSuprtdept on astSuprtdept.id=astSuprt.dept_subdept");
						}
						if (keyList.contains("astSuprtvendor.vendorname"))
						{
							query.append(" left join createvendor_master astSuprtvendor on astSuprtvendor.id=astSuprt.vendorid");
						}
						if (keyList.contains("astSuprtemp.empName"))
						{
							query.append(" left join employee_basic astSuprtemp on astSuprtemp.id=astSuprt.employeeid");
						}
						if (keyList.contains("astSuprtsuprtcatg.category"))
						{
							query.append(" left join createasset_category_master astSuprtsuprtcatg on astSuprtsuprtcatg.id=astSuprt.supporttype");
						}
						if (keyList.contains("astAllotsdept.subdeptname") && !keyList.contains("astAllotdept.deptName"))
						{
							query.append(" left join subdepartment astAllotsdept on astAllotsdept.id=astAllot.dept_subdept");
						}
						if (keyList.contains("astAllotdept.deptName") && session.get("userDeptLevel").toString().equals("2"))
						{
							query.append(" left join subdepartment astAllotsdept on astAllotsdept.id=astAllot.dept_subdept left join department astAllotdept on astAllotdept.id=astAllotsdept.deptid");
						}
						if (keyList.contains("astAllotdept.deptName") && session.get("userDeptLevel").toString().equals("1"))
						{
							query.append(" left join department astAllotdept on astAllotdept.id=astAllot.dept_subdept");
						}
						if (keyList.contains("astAllotemp.empName"))
						{
							query.append(" left join employee_basic astAllotemp on astAllotemp.id=astAllot.employeeid");
						}
						// query.append(" where ast.dept_subdept=" +
						// dept_subdept_id);
						System.out.println("Ececute queryyyy"  +query.toString());
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

						if (dataList != null && dataList.size() > 0)
						{
							System.out.println("ORGE NAME ===  "+session.get("orgname"));
							excelFileName = new GenericWriteExcel().createExcel("Dreamsol Telesolutions Pvt. Ltd.", excelName, dataList, titleList, null, excelName);
							System.out.println(excelFileName);
							if (session.containsKey("columnMap"))
							{
								session.remove("columnMap");
							}
						}
					}
					if (excelFileName != null)
					{
						excelStream = new FileInputStream(excelFileName);
					}
					returnResult = SUCCESS;
				}
				else
				{
					returnResult = ERROR;
				}
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method downloadExcel of class " + getClass(), e);
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}


	public String beforeAllView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				getAllColumn4View();
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method beforeAllView of class " + getClass(), e);
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
	public void getAllColumn4View()
	{
		int colCount = 0;
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		List astColList = new ArrayList();
		String tableAllias = null;
		
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");

		if (statusColName != null && statusColName.size() > 0)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("astid");
			gpv.setHeadingName("Asset Id");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("true");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			astColList.add("astid");
			
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				if (gdp.getColType().toString().equalsIgnoreCase("D") && !gdp.getColomnName().equals("assettype") && !gdp.getColomnName().equals("astcategory"))
				{
					tableAllias = "ast";
					columnMap = addAliasInColumn(gdp.getColomnName(), tableAllias);
					if (columnMap != null && columnMap.size() > 0)
					{
						Iterator<Entry<String, String>> hiterator = columnMap.entrySet().iterator();
						while (hiterator.hasNext())
						{
							gpv = new GridDataPropertyView();
							Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
							gpv.setColomnName(paramPair.getKey().toString());
							gpv.setHeadingName(paramPair.getValue().toString());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							gpv.setWidth(gdp.getWidth());
							masterViewProp.add(gpv);
							astColList.add(paramPair.getKey().toString());
							colCount++;
						}
						columnMap.clear();
					}
				}
				else if(!gdp.getColomnName().equals("assetbrand") && !gdp.getColomnName().equals("assetmodel") && !gdp.getColomnName().equals("receivedOn") 
						&& !gdp.getColomnName().equals("specification") && !gdp.getColomnName().equals("quantity") && !gdp.getColomnName().equals("assettype")
						&& !gdp.getColomnName().equals("installon") && !gdp.getColomnName().equals("commssioningon") && !gdp.getColomnName().equals("pono")
						&& !gdp.getColomnName().equals("expectedlife") && !gdp.getColomnName().equals("mfgserialno") && !gdp.getColomnName().equals("totalamount")
						)
				{
					
					gpv = new GridDataPropertyView();
					gpv.setColomnName("ast." + gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
					astColList.add("ast." + gdp.getColomnName());
					colCount++;

				}
			}
			
			
			
			if (astColList != null && astColList.size() > 0)
			{
				ast1stCol = astColList.get(0).toString();
			}
			assetColCount = String.valueOf(colCount);
			colCount = 0;
			
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("supportDate");
			gpv.setHeadingName("Support ON");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("viewSupportDetails");
			gpv.setHeadingName("Support Details");
			gpv.setFormatter("viewSupportDetails");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			astColList.add("viewSupportDetails");
			
			suprt1stCol="supportDate";
			suprtColCount="2";
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("PMDate");
			gpv.setHeadingName("P.M On");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("viewPMDetails");
			gpv.setHeadingName("P.M Details");
			gpv.setFormatter("viewPMDetails");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			astColList.add("viewPMDetails");
			
			PMc1stCol="PMDate";
			PMColCount="2";
			
		}
		statusColName.clear();
		statusColName = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				if (gdp.getColType().toString().equalsIgnoreCase("D"))
				{
					tableAllias = "astAllot";
					columnMap = addAliasInColumn(gdp.getColomnName(), tableAllias);
					if (columnMap != null && columnMap.size() > 0)
					{
						Iterator<Entry<String, String>> hiterator = columnMap.entrySet().iterator();
						while (hiterator.hasNext())
						{
							gpv = new GridDataPropertyView();
							Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
							gpv.setColomnName(paramPair.getKey().toString());
							gpv.setHeadingName(paramPair.getValue().toString());
							gpv.setEditable(gdp.getEditable());
							gpv.setSearch(gdp.getSearch());
							gpv.setHideOrShow(gdp.getHideOrShow());
							gpv.setWidth(gdp.getWidth());
							masterViewProp.add(gpv);
							astColList.add(paramPair.getKey().toString());
							colCount++;
						}
						columnMap.clear();
					}
				}
				else if(!gdp.getColomnName().equals("user") && !gdp.getColomnName().equals("usermobno") && !gdp.getColomnName().equals("returnon"))
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("astAllot." + gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					masterViewProp.add(gpv);
					astColList.add("astAllot." + gdp.getColomnName());
					colCount++;
				}
			}
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalAllotment");
			gpv.setHeadingName("Total Allotment");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setFormatter("totalAllotment");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			astColList.add("totalAllotment");
			colCount++;
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("totalBreakdown");
			gpv.setHeadingName("Total Breakdown");
			gpv.setEditable("false");
			gpv.setSearch("false");
			gpv.setHideOrShow("false");
			gpv.setFormatter("totalBreakdown");
			gpv.setWidth(100);
			masterViewProp.add(gpv);
			astColList.add("totalBreakdown");
			colCount++;
			
			
			if (astColList != null && astColList.size() > 0)
			{
				allot1stCol = astColList.get(astColList.size() - colCount).toString();
			}
			allotColCount = String.valueOf(colCount);
			colCount = 0;
		}
		
		
		if (masterViewProp != null && masterViewProp.size() > 0)
		{
			session.put("assetMasterViewProp", masterViewProp);
		}
		if (astColList != null && astColList.size() > 0)
		{
			session.put("assetCol", astColList);
		}
	}

	public Map<String, String> addAliasInColumn(String columnName, String allias)
	{
		columnMap = new LinkedHashMap<String, String>();
		if (columnName.equals("assettype"))
		{
			columnMap.put(allias + ".assettype", new AssetCommonAction().getMasterFieldName("asset_type_master", "assetSubCat"));
		}
		else if (columnName.equals("dept_subdept"))
		{
			columnMap.put(allias + "dept.deptName", new AssetCommonAction().getFieldName("mapped_dept_config_param", "dept_config_param", "deptName"));
		}
		else if (columnName.equals("employeeid"))
		{
			columnMap.put(allias + "emp.empName", new AssetCommonAction().getFieldName("mapped_employee_basic_configuration", "employee_basic_configuration", "empName"));
		}
		else if (columnName.equals("vendorname") || columnName.equals("vendorid"))
		{
			columnMap.put(allias + "vendor.vendorname", new AssetCommonAction().getMasterFieldName("vendor_master", "vendorname"));
		}
		else if (columnName.equals("supporttype"))
		{
			columnMap.put(allias + "suprtcatg.category", new AssetCommonAction().getMasterFieldName("asset_category_master", "category"));
		}
		else if (columnName.equals("spare_category"))
		{
			columnMap.put(allias + "spc.spare_category", new AssetCommonAction().getMasterFieldName("spare_catg_master", "spare_category"));
		}
		else if (columnName.equals("spare_name"))
		{
			columnMap.put(allias + "spr.spare_name", new AssetCommonAction().getFieldName("mapped_spare_master", "spare_master", "spare_name"));
		}
		return columnMap;
	}

	@SuppressWarnings("unchecked")
	public String viewAllModuleData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String userName = (String) session.get("uName");
				String deptLevel = (String) session.get("userDeptLevel");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_detail");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				String dept_subdept_id = "";

				List empData = new AssetUniversalHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel, connectionSpace);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_subdept_id = object[3].toString();
					}
				}

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
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("assetMasterViewProp");
					List astColList = (List) session.get("assetCol");
					if (session.containsKey("assetMasterViewProp"))
					{
						session.remove("assetMasterViewProp");
					}
					if (session.containsKey("assetCol"))
					{
						session.remove("assetCol");
					}
					if (fieldNames != null && fieldNames.size() > 0)
					{
						int i = 0;
						for (GridDataPropertyView GPV : fieldNames)
						{
							if (i < fieldNames.size() - 3 && !GPV.getColomnName().equalsIgnoreCase("ast.astcategory"))
							{
								if(GPV.getColomnName().toString().equals("astid"))
								{
									query.append("ast.id as astid,");
								}
								else if(GPV.getColomnName().equalsIgnoreCase("viewSupportDetails"))
								{
									query.append("ast.supportfor AS suprtdetails,");
								}
								else if(GPV.getColomnName().equalsIgnoreCase("viewPMDetails"))
								{
									query.append("ast.supportfor AS pmdetails,");
								}
								else if(!GPV.getColomnName().equalsIgnoreCase("PMDate") && !GPV.getColomnName().equalsIgnoreCase("supportDate") && !GPV.getColomnName().equalsIgnoreCase("totalAllotment") && !GPV.getColomnName().equalsIgnoreCase("totalBreakdown"))
								{
									query.append(GPV.getColomnName().toString() + " as astCol" + i + ",");
								}
									
							}
							else if (GPV.getColomnName().equalsIgnoreCase("ast.astcategory"))
							{
								query.append("catm.assetSubCat" + " as astCol" + i + ",");
							}
							else if(!GPV.getColomnName().equalsIgnoreCase("PMDate") && !GPV.getColomnName().equalsIgnoreCase("supportDate") && !GPV.getColomnName().equalsIgnoreCase("totalAllotment") && !GPV.getColomnName().equalsIgnoreCase("totalBreakdown"))
							{
								query.append(GPV.getColomnName().toString());
							}

							i++;
						}
						query.append(" from asset_detail as ast");
						query.append(" left join createasset_type_master catm on catm.id=ast.assettype");
						query.append(" left join procurement_detail astProc on astProc.assetid=ast.id");
						query.append(" left join asset_support_detail astSuprt on astSuprt.assetid=ast.id");
						query.append(" left join asset_allotment_detail astAllot on astAllot.assetid=ast.id");

						if (astColList.contains("astdept.deptName"))
						{
							query.append(" left join department astdept on astdept.id=ast.dept_subdept");
						}
						if (astColList.contains("astvendor.vendorname"))
						{
							query.append(" left join createvendor_master astvendor on astvendor.id=ast.vendorname");
						}
						if (astColList.contains("astProcvendor.vendorname"))
						{
							query.append(" left join createvendor_master astProcvendor on astProcvendor.id=astProc.vendorname");
						}
						if (astColList.contains("astSuprtsdept.subdeptname") && !astColList.contains("astSuprtdept.deptName"))
						{
							query.append(" left join subdepartment astSuprtsdept on astSuprtsdept.id=astSuprt.dept_subdept");
						}
						if (astColList.contains("astSuprtdept.deptName"))
						{
							query.append(" left join department astSuprtdept on astSuprtdept.id=astSuprt.dept_subdept");
						}
						if (astColList.contains("astSuprtvendor.vendorname"))
						{
							query.append(" left join createvendor_master astSuprtvendor on astSuprtvendor.id=astSuprt.vendorid");
						}
						if (astColList.contains("astSuprtemp.empName"))
						{
							query.append(" left join employee_basic astSuprtemp on astSuprtemp.id=astSuprt.employeeid");
						}
						if (astColList.contains("astSuprtsuprtcatg.category"))
						{
							query.append(" left join createasset_support_catg_master astSuprtsuprtcatg on astSuprtsuprtcatg.id=astSuprt.supporttype");
						}
						if (astColList.contains("astAllotsdept.subdeptname") && !astColList.contains("astAllotdept.deptName"))
						{
							query.append(" left join subdepartment astAllotsdept on astAllotsdept.id=astAllot.dept_subdept");
						}
						if (astColList.contains("astAllotdept.deptName"))
						{
							query.append(" left join department astAllotdept on astAllotdept.id=astAllot.dept_subdept");
						}
						if (astColList.contains("astAllotemp.empName"))
						{
							query.append(" inner join compliance_contacts as astAllotCont on astAllotCont.id=astAllot.employeeid");
							query.append(" inner join employee_basic astAllotemp on astAllotCont.emp_id=astAllotemp.id");
						}
						
						if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{   
							query.append(" where ");
							// add search query based on the search operation
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
						// query.append(" ORDER BY ast.id DESC");
						System.out.println(">>>>>>>>>>> " + query.toString());
						List data1 = new ArrayList();
						Session session = null;
						Transaction transaction = null;
						try
						{
							session = connectionSpace.openSession();
							transaction = session.beginTransaction();
							data1 = session.createSQLQuery(query.toString()).list();
							transaction.commit();
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
							transaction.rollback();
							session.close();
						}
						if (data1 != null && data1.size() > 0)
						{
							List<Object> tempList = new ArrayList<Object>();
							for (Iterator it = data1.iterator(); it.hasNext();)
							{
								Object[] obdata = (Object[]) it.next();
								Map<String, Object> tempMap = new HashMap<String, Object>();
								System.out.println(" Column List is "+astColList.size());
								for (int k = 0; k < astColList.size()-2; k++)
								{
									if (obdata[k] != null)
									{
										if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											tempMap.put(astColList.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										}
										else if(astColList.get(k).toString().equalsIgnoreCase("astid"))
										{
											tempMap.put(astColList.get(k).toString(), obdata[k].toString());
											tempMap.put("totalAllotment", getCounter("totalAllotment",obdata[k].toString()));
											tempMap.put("totalBreakdown", getCounter("totalBreakdown",obdata[k].toString()));
											tempMap.put("PMDate", getCounter("Preventive",obdata[k].toString()));
											tempMap.put("supportDate", getCounter("Support",obdata[k].toString()));
										}
										else
										{
											tempMap.put(astColList.get(k).toString(), obdata[k].toString());
										}
									}
									else
									{
										tempMap.put(astColList.get(k).toString(), "NA");
									}
								}
								tempList.add(tempMap);
							}
							setMasterViewList(tempList);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}

				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAllModuleData of class " + getClass(), e);
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
	public String getAllModuleColumnName()
	{
		String returnString = null;
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			Map<String, String> tempMap = new LinkedHashMap<String, String>();
			String tableAllias = null;
			columnMap1 = new LinkedHashMap<String, String>();
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name", "asset_master");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (gdp.getColType().toString().equalsIgnoreCase("D"))
					{
						tableAllias = "ast";
						tempMap = addAliasInColumn(gdp.getColomnName(), tableAllias);
						if (tempMap != null && tempMap.size() > 0)
						{
							Iterator<Entry<String, String>> hiterator = tempMap.entrySet().iterator();
							while (hiterator.hasNext())
							{
								Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
								columnMap1.put(paramPair.getKey().toString(), paramPair.getValue().toString());
							}
							tempMap.clear();
						}
					}
					else
					{
						columnMap1.put("ast." + gdp.getColomnName(), gdp.getHeadingName());
					}

				}
			}
			statusColName.clear();
			statusColName = Configuration.getConfigurationData("mapped_procurement_master", accountID, connectionSpace, "", 0, "table_name", "procurement_master");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (gdp.getColType().toString().equalsIgnoreCase("D"))
					{
						tableAllias = "astProc";
						tempMap = addAliasInColumn(gdp.getColomnName(), tableAllias);
						if (tempMap != null && tempMap.size() > 0)
						{
							Iterator<Entry<String, String>> hiterator = tempMap.entrySet().iterator();
							while (hiterator.hasNext())
							{
								Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
								columnMap1.put(paramPair.getKey().toString(), paramPair.getValue().toString());
							}
							tempMap.clear();
						}
					}
					else
					{
						columnMap1.put("astProc." + gdp.getColomnName(), gdp.getHeadingName());
					}
				}
			}
			statusColName.clear();
			/*statusColName = Configuration.getConfigurationData("mapped_asset_support", accountID, connectionSpace, "", 0, "table_name", "asset_support");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (gdp.getColType().toString().equalsIgnoreCase("D"))
					{
						tableAllias = "astSuprt";
						tempMap = addAliasInColumn(gdp.getColomnName(), tableAllias);
						if (tempMap != null && tempMap.size() > 0)
						{
							Iterator<Entry<String, String>> hiterator = tempMap.entrySet().iterator();
							while (hiterator.hasNext())
							{
								Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
								columnMap1.put(paramPair.getKey().toString(), paramPair.getValue().toString());
							}
							tempMap.clear();
						}
					}
					else
					{
						columnMap1.put("astSuprt." + gdp.getColomnName(), gdp.getHeadingName());
					}
				}
			}*/
			statusColName.clear();
			statusColName = Configuration.getConfigurationData("mapped_allotment_master", accountID, connectionSpace, "", 0, "table_name", "allotment_master");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (gdp.getColType().toString().equalsIgnoreCase("D"))
					{
						tableAllias = "astAllot";
						tempMap = addAliasInColumn(gdp.getColomnName(), tableAllias);
						if (tempMap != null && tempMap.size() > 0)
						{
							Iterator<Entry<String, String>> hiterator = tempMap.entrySet().iterator();
							while (hiterator.hasNext())
							{
								Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
								columnMap1.put(paramPair.getKey().toString(), paramPair.getValue().toString());
							}
							tempMap.clear();
						}
					}
					else
					{
						columnMap1.put("astAllot." + gdp.getColomnName(), gdp.getHeadingName());
					}
				}
			}
			returnString = SUCCESS;
		}
		catch (Exception e)
		{
			returnString = ERROR;
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: " + DateUtil.getCurrentTime() + " " + "Problem in Over2Cloud  method viewAllModuleData of class " + getClass(), e);
			e.printStackTrace();
		}
		if (columnMap1 != null && columnMap1.size() > 0)
		{
			Iterator<Entry<String, String>> hiterator = columnMap1.entrySet().iterator();
			while (hiterator.hasNext())
			{
				Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				columnMap.put(paramPair.getKey().toString(), paramPair.getValue().toString());
			}
		}

		if (columnMap != null && columnMap.size() > 0)
		{
			session.put("columnMap", columnMap);
		}
		return returnString;
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public String isExistSLNo()
	{
		String returnResult = ERROR;
		boolean validSession = new ValidateSession().checkSession();
		if (validSession)
		{
			try
			{
				if (serialNo != null && serialNo != "")
				{
					String assetCode = getRendomAssetCode();
					checkSpareNo = new HashMap<String, String>();
					if (assetCode != null && !assetCode.equals(""))
					{
						checkSpareNo.put("serialno", assetCode);
					}
					returnResult = SUCCESS;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
				log.error("@ERP::>> Problem in " + getClass() + " in method getTotalSpareAvailable()on" + DateUtil.getCurrentDateIndianFormat() + "At" + DateUtil.getCurrentTimeHourMin(), e);
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String getRendomAssetCode()
	{
		SecureRandom random = new SecureRandom();
		String assetCode = new BigInteger(50, random).toString(32);
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query = "select id from asset_detail where serialno= '" + assetCode + "'";
		List temp = new ArrayList();
		temp = cbt.executeAllSelectQuery(query, connectionSpace);
		if (temp != null && temp.size() > 0)
		{
			getRendomAssetCode();
		}
		return assetCode;
	}

	@SuppressWarnings("unchecked")
	public String getCounter(String counterFor,String assetId)
	{
		String count="0";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String query=null;
		List temp = new ArrayList();
		if(counterFor!=null && counterFor.equalsIgnoreCase("totalAllotment"))
		{
			query="SELECT COUNT(id) FROM asset_allotment_log WHERE assetid="+assetId;
		}
		else if(counterFor!=null && counterFor.equalsIgnoreCase("totalBreakdown"))
		{
			query="SELECT COUNT(id) FROM asset_complaint_status WHERE asset_id="+assetId;
		}
		else if(counterFor!=null && counterFor.equalsIgnoreCase("Support"))
		{
			query="SELECT dueDate FROM asset_reminder_details WHERE moduleName='Support' AND assetid="+assetId;
		}
		else if(counterFor!=null && counterFor.equalsIgnoreCase("Preventive"))
		{
			query="SELECT dueDate FROM asset_reminder_details WHERE moduleName='Preventive' AND assetid="+assetId;
		}
		
		temp = cbt.executeAllSelectQuery(query, connectionSpace);
		if(temp!=null && temp.size()>0)
		{
			if(counterFor.equalsIgnoreCase("Support") || counterFor.equalsIgnoreCase("Preventive"))
			{
				count=DateUtil.convertDateToIndianFormat(temp.get(0).toString());
			}
			else
			{
				count=temp.get(0).toString();
			}
			
		}
		return count;
	}
	
	public String getAllotmentConfig()
	{
		String allot2Config = null;
		try
		{
			List dataList = cbt.executeAllSelectQuery("SELECT allotto FROM asset_allotment_config", connectionSpace);
			if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
			{
				allot2Config = dataList.get(0).toString();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return allot2Config;
	}
	
	
	@SuppressWarnings("static-access")
	public Map<String, String> getAssetDetailMap()
	{
		return assetDetailMap;
	}

	public void setAssetDetailMap(Map<String, String> assetDetailMap)
	{
		this.assetDetailMap = assetDetailMap;
	}

	public double getUnitCost()
	{
		return unitCost;
	}

	public void setUnitCost(double unitCost)
	{
		this.unitCost = unitCost;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public double getTaxes()
	{
		return taxes;
	}

	public void setTaxes(double taxes)
	{
		this.taxes = taxes;
	}

	public String getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public Map<String, String> getVendorDetailMap()
	{
		return vendorDetailMap;
	}

	public void setVendorDetailMap(Map<String, String> vendorDetailMap)
	{
		this.vendorDetailMap = vendorDetailMap;
	}

	public int getVendorId()
	{
		return vendorId;
	}

	public void setVendorId(int vendorId)
	{
		this.vendorId = vendorId;
	}

	public String getAssetId()
	{
		return assetId;
	}

	public void setAssetId(String assetId)
	{
		this.assetId = assetId;
	}

	public String getAssetWithAllot()
	{
		return assetWithAllot;
	}

	public void setAssetWithAllot(String assetWithAllot)
	{
		this.assetWithAllot = assetWithAllot;
	}

	public Map<Integer, String> getSpareNameMap()
	{
		return spareNameMap;
	}

	public void setSpareNameMap(Map<Integer, String> spareNameMap)
	{
		this.spareNameMap = spareNameMap;
	}

	public String getCatgId()
	{
		return catgId;
	}

	public void setCatgId(String catgId)
	{
		this.catgId = catgId;
	}

	public Map<String, String> getCheckSpareNo()
	{
		return checkSpareNo;
	}

	public void setCheckSpareNo(Map<String, String> checkSpareNo)
	{
		this.checkSpareNo = checkSpareNo;
	}

	public String getSpareCount()
	{
		return spareCount;
	}

	public void setSpareCount(String spareCount)
	{
		this.spareCount = spareCount;
	}

	public String getSpareNameId()
	{
		return spareNameId;
	}

	public void setSpareNameId(String spareNameId)
	{
		this.spareNameId = spareNameId;
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

	public String getDownload4()
	{
		return download4;
	}

	public void setDownload4(String download4)
	{
		this.download4 = download4;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
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

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
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

	public String getAssetColCount()
	{
		return assetColCount;
	}

	public void setAssetColCount(String assetColCount)
	{
		this.assetColCount = assetColCount;
	}


	public String getSuprtColCount()
	{
		return suprtColCount;
	}

	public void setSuprtColCount(String suprtColCount)
	{
		this.suprtColCount = suprtColCount;
	}

	public String getAllotColCount()
	{
		return allotColCount;
	}

	public void setAllotColCount(String allotColCount)
	{
		this.allotColCount = allotColCount;
	}

	public String getBarcode()
	{
		return barcode;
	}

	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public Map<String, String> getColumnMap1()
	{
		return columnMap1;
	}

	public void setColumnMap1(Map<String, String> columnMap1)
	{
		this.columnMap1 = columnMap1;
	}

	public String getAst1stCol()
	{
		return ast1stCol;
	}

	public void setAst1stCol(String ast1stCol)
	{
		this.ast1stCol = ast1stCol;
	}


	public String getSuprt1stCol()
	{
		return suprt1stCol;
	}

	public void setSuprt1stCol(String suprt1stCol)
	{
		this.suprt1stCol = suprt1stCol;
	}

	public String getAllot1stCol()
	{
		return allot1stCol;
	}

	public void setAllot1stCol(String allot1stCol)
	{
		this.allot1stCol = allot1stCol;
	}

	public String getSerialNo()
	{
		return serialNo;
	}

	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}

	public List<ConfigurationUtilBean> getColumnMap2()
	{
		return columnMap2;
	}

	public void setColumnMap2(List<ConfigurationUtilBean> columnMap2)
	{
		this.columnMap2 = columnMap2;
	}

	public String getColumnNames1()
	{
		return columnNames1;
	}

	public void setColumnNames1(String columnNames1)
	{
		this.columnNames1 = columnNames1;
	}

	public String getDialogId()
	{
		return dialogId;
	}

	public void setDialogId(String dialogId)
	{
		this.dialogId = dialogId;
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

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getAssetWithSuprt()
	{
		return assetWithSuprt;
	}

	public void setAssetWithSuprt(String assetWithSuprt)
	{
		this.assetWithSuprt = assetWithSuprt;
	}

	public String getPMColCount()
	{
		return PMColCount;
	}

	public void setPMColCount(String colCount)
	{
		PMColCount = colCount;
	}

	public String getPMc1stCol()
	{
		return PMc1stCol;
	}

	public void setPMc1stCol(String mc1stCol)
	{
		PMc1stCol = mc1stCol;
	}

}
