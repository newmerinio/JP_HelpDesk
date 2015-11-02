package com.Over2Cloud.ctrl.wfpm.lead;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

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

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

public class LeadCommonAction extends ActionSupport implements ServletRequestAware
{
	Map																	session					= ActionContext.getContext().getSession();
	String															userName				= (String) session.get("uName");
	private Map<String, String>					assetDetailMap	= null;
	private Map<String, String>					vendorDetailMap	= null;
	private double											unitCost;
	private int													quantity;
	private double											taxes;
	private String											totalAmount;
	private int													vendorId;
	private String											assetId;
	private String											assetWithProc		= null;
	private String											assetWithAllot	= null;
	private Map<Integer, String>				spareNameMap		= null;
	private Map<String, String>					checkSpareNo;
	private String											spareCount;
	private String											spareNameId;
	Map<Integer, String>								spareCatgMap		= null;
	String															accountID				= (String) session.get("accountid");
	private String[]										columnNames;
	private String											downloadType		= null;
	private String											download4				= null;
	private Map<String, String>					columnMap				= null;
	private Map<String, String>					columnMap1			= null;

	private String											tableName				= null;
	private String											tableAllis			= null;
	private String											excelName				= null;
	private String											mainHeaderName;
	private String											status,industry,subIndustry,location,starRating,sourceName,lead_name_wild,associate_Name,associateCategory,associateType,isExistingAssociate,associatestatus;
	private Integer											rows						= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer											page						= 0;
	// sorting order - asc or desc
	private String											sord						= "";
	// get index row - i.e. user click to sort.
	private String											sidx						= "";
	// Search Field
	private String											searchField			= "";
	// The Search String
	private String											searchString		= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String											searchOper			= "";
	// Your Total Pages
	private Integer											total						= 0;
	// All Record
	private Integer											records					= 0;
	// private boolean loadonce = false;
	// Grid colomn view
	private String											oper;
	private String											id;
	private List<Object>								masterViewList;
	private List<GridDataPropertyView>	masterViewProp	= new ArrayList<GridDataPropertyView>();
	private String											assetColCount, ast1stCol = null;
	private String											procColCount, proc1stCol = null;
	private String											suprtColCount, suprt1stCol = null;
	private String											allotColCount, allot1stCol = null;
	private String											barcode					= null;
	private HttpServletRequest					request;
	private Properties									configProp			= new Properties();

	private String											catgId					= null;
	SessionFactory											connectionSpace	= (SessionFactory) session.get("connectionSpace");

	private String isExistingClient = "";
	private String clientstatus;
	private String client_Name;
	private String acManager;
	private String forecastCategary;
	private String fromDateSearch;
	
	private String salesstages;
	private String closure_date;
	private String opportunity_name;
	
	
	@SuppressWarnings( { "unchecked", "static-access" })
	// calculate date after add month in date
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
	}

	public String getColumn4Download()
	{
		//System.out.println(downloadType +"getColumn4Download():"+download4+ downloadType);
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (download4 != null && download4.equals("leadDetail"))
				{
					//System.out.println("inside the getColumn4Download() method");
					tableAllis = "lbd";
					returnResult = getColumnName("mapped_lead_generation", "lead_generation", tableAllis);
					//System.out.println("-----"+returnResult);
					tableName = "leadgeneration";
					excelName = "Lead";
				}
				if (download4 != null && download4.equals("clientDetail"))
				{

					tableAllis = "cbd";
					returnResult = getColumnName("mapped_client_configuration", "client_basic_configuration", tableAllis);
					tableName = "client_basic_data";
					excelName = "Prospective_Client";
				}
				if (download4 != null && download4.equals("associateDetail"))
				{

					tableAllis = "abd";
					returnResult = getColumnName("mapped_associate_configuration", "associate_basic_configuration", tableAllis);
					tableName = "associate_basic_data";
					excelName = "Prospective_Associate";
				}

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
	public String getColumnName(String mappedTableName, String basicTableName, String allias)
	{
		String returnResult = ERROR;
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name",
					basicTableName);
			columnMap = new LinkedHashMap<String, String>();
			if (columnList != null && columnList.size() > 0)
			{
				if (downloadType != null && downloadType.equals("downloadExcel"))
				{
					for (GridDataPropertyView gdp1 : columnList)
					{

						if (!gdp1.getColomnName().equals("deptHierarchy") && !gdp1.getColomnName().equals("weightage") && !gdp1.getColomnName().equals("refName"))
						{
							columnMap.put(allias + "." + gdp1.getColomnName(), gdp1.getHeadingName());
						}
					}
				}
				else
				{
					//System.out.println("esle part into for uplaod  getColumnName  ");
					for (GridDataPropertyView gdp1 : columnList)
					{
						if (!gdp1.getColomnName().equals("deptHierarchy") && !gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date")
								&& !gdp1.getColomnName().equals("time"))
						{
							columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
						}
					}
				}
				//System.out.println("firest colmap>>>>> "  +columnMap);
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
			}
			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String downloadExcel()
	{
		//System.out.println("in downloadExcel() @@@@@@@@@@@@@@@@@@@@@@@@@@@");
		//System.out.println(download4+"\t"+downloadType);		
		System.out.println("----associateName ---"+associate_Name);//associateCategory,associateType
		System.out.println("--associateCategory   "+associateCategory);
		System.out.println("associateType   "+associateType);
		System.out.println("isExistingAssociate "+isExistingAssociate);//associatestatus
		System.out.println("associatestatus "+associatestatus);
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
					System.out.println("keyList >>>>>>>>>>  "+keyList);
				Map<String, String> tempMap = new LinkedHashMap<String, String>();
				tempMap = (Map<String, String>) session.get("columnMap");
				System.out.println("columnMAP>>>>>>>>>>>>>>>>>>>>>"+ tempMap);
				List dataList = null;
				StringBuilder query = new StringBuilder("");
				StringBuilder query2 = new StringBuilder("");
				StringBuilder query3 = new StringBuilder("");

				for (int index = 0; index < keyList.size(); index++)
				{
					titleList.add(tempMap.get(keyList.get(index)));
				}

				query.append(" from " + tableName + " as " + tableAllis);
				//if (download4 != null && download4.equals("associateDetail")) 
				if (downloadType.equals("downloadExcel") )
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
									else if (obdata.toString().endsWith("location"))
									{
										query2.append("loc.name ,");
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
						query2.append(" where ");
						//query2.append(tableAllis);
						if (status != null && !status.equals("-1") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
                        {
                        query2.append(" lbd.status = '");
                        query2.append(status);
                        query2.append("' ");//&& !download4.equalsIgnoreCase("associateDetail")
                        }
						else if(download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
							query2.append("cbd.userName <> '0' ");
						}
						else if (status != null && !status.equals("-1") && download4.equalsIgnoreCase("associateDetail"))
                        {
                        query2.append(" abd.status = '");
                        query2.append(status);
                        query2.append("' ");//&& !download4.equalsIgnoreCase("associateDetail")
                        }
						if (industry != null && !industry.equals("-1") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
                        {
                        query2.append("and lbd.industry = '");
                        query2.append(industry);
                        query2.append("' ");
                        } 
                        else if (industry != null && !industry.equals("-1")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.industry = '");
						query2.append(industry);
						query2.append("' ");
						}
                        else if (industry != null && !industry.equals("-1"))
						{
						query2.append("and abd.industry = '");
						query2.append(industry);
						query2.append("' ");
						System.out.println("---"+query2);
						}
						if (subIndustry != null && !subIndustry.equals("-1") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
                        {
                        query2.append("and lbd.subIndustry = '");
                        query2.append(subIndustry);
                        query2.append("' ");
                        } else if (subIndustry != null && !subIndustry.equals("-1")&& download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.subIndustry = '");
						query2.append(subIndustry);
						query2.append("' ");
						}
				
                        else if (subIndustry != null && !subIndustry.equals("-1")&& download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and abd.subIndustry = '");
						query2.append(subIndustry);
						query2.append("' ");
						System.out.println("----subiN--"+query2);
						}
						if (location != null && !location.equals("-1") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
                        {
                        query2.append("and lbd.name = '");
                        query2.append(location);
                        query2.append("' ");//&& !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
                        } 
						else if (location != null && !location.equals("-1")&& download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.location = '");
						query2.append(location);
						query2.append("' ");
						}
						else if (location != null && !location.equals("-1")&& download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and abd.location = '");
						query2.append(location);
						query2.append("' ");
						System.out.println("----"+query2);
						}
						
						if (starRating != null && !starRating.equals("-1") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
                        {
                        query2.append("and lbd.starRating = '");
                        query2.append(starRating);
                        query2.append("' ");
                        } else if (starRating != null && !starRating.equals("-1") && download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.starRating = '");
						query2.append(starRating);
						query2.append("' ");
						}
							
                        else if (starRating != null && !starRating.equals("-1") && download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and abd.associateRating = '");
						query2.append(starRating);
						query2.append("' ");
						System.out.println("---Star  "+query2);
						}
						if (sourceName != null && !sourceName.equals("-1") && !download4.equalsIgnoreCase("clientDetail") && !download4.equalsIgnoreCase("undefined")&& !download4.equalsIgnoreCase("associateDetail")) 
						{
	                         query2.append("and lbd.sourceName = '");
	                         query2.append(sourceName); 
	                         query2.append("' "); 
	                    } 
						else if (sourceName != null && !sourceName.equals("-1")&& !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("undefined")&& download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and abd.sourceName = '");
						query2.append(sourceName);
						query2.append("' ");
						System.out.println("----source  "+query2);
						}
						else if (sourceName != null && !sourceName.equals("-1")&& !download4.equalsIgnoreCase("undefined") && download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.sourceMaster = '");
						query2.append(sourceName);
						query2.append("' ");
						}
						
						if (lead_name_wild != null && !lead_name_wild.equals("") && !lead_name_wild.equals("undefined") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("associateDetail"))
	                	{
							//System.out.println("lead_name_wild");
	                	query2.append("and lbd.leadName like '");
	                	query2.append(lead_name_wild);
	                	query2.append("%' ");
	                	//System.out.println("  lead_name_wild   "+query2);
	                	}
						if (associate_Name != null && !associate_Name.equals("-1") && !associate_Name.equals("") && !associate_Name.equals("undefined") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("leadDetail"))
	                	{query2.append("and abd.associateName like '");
						query2.append(associate_Name);
						query2.append("%' ");
	                	System.out.println("  associate_Name   "+query2);//associateCategory,associateType
	                	}
						if (associateType != null && !associateType.equals("-1") && !associateType.equals("") && !associateType.equals("undefined") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("leadDetail"))
						{
							query2.append("and abd.associateType = '");
							query2.append(associateType);
							query2.append("' ");
							System.out.println("associateType   "+query2);
						}
						if (associateCategory != null && !associateCategory.equals("-1") && !associateCategory.equals("") && !associateCategory.equals("undefined") && !download4.equalsIgnoreCase("clientDetail")&& !download4.equalsIgnoreCase("leadDetail"))
						{
							query2.append("and abd.associateCategory = '");
							query2.append(associateCategory);
							query2.append("' ");
							System.out.println("associateCategory   "+query2);
						}
						if (acManager != null && !acManager.equals("-1") && !acManager.equals("undefined")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.acManager ='");
						query2.append(acManager);
						query2.append("' ");
						}
						if (client_Name != null && !client_Name.equals("") && !client_Name.equalsIgnoreCase("undefined")&& !download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.clientName like '");
						query2.append(client_Name);
						query2.append("%' ");
						}
						if (salesstages != null && !salesstages.equals("-1") && !salesstages.equalsIgnoreCase("undefined")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.sales_stages like '");
						query2.append(salesstages);
						query2.append("%' ");
						}
						if (forecastCategary != null && !forecastCategary.equals("-1") && !forecastCategary.equalsIgnoreCase("undefined")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.forecast_category = '");
						query2.append(forecastCategary);
						query2.append("' ");
						}
						if (fromDateSearch != null && !fromDateSearch.equals("") && !fromDateSearch.equalsIgnoreCase("undefined")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
						query2.append("and cbd.closure_date = '");
						query2.append(fromDateSearch);
						query2.append("' ");
						}
						if (associatestatus != null && !associatestatus.equalsIgnoreCase("-1") && !associatestatus.equalsIgnoreCase("") && !clientstatus.equalsIgnoreCase("undefined"))
						{
							query2.append(" and ");
							query2.append(" abd.id in (select distinct(ccd.associateName) from associate_contact_data as ccd, "
									+ "associate_takeaction as cta ,associate_offering_mapping as com where ccd.id = cta.contacId "
									+ "and ccd.associateName = com.associateName and cta.statusId = '" + associatestatus + "' " + "and com.isConverted = 0) ");
							System.out.println("Status    22   2   "+query2);
						}
						else
						{
							
							if (isExistingAssociate.equalsIgnoreCase("1")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("clientDetail"))
							{
								query2.append(" and ");
								query2.append(" abd.id in (select distinct(associateName) from associate_offering_mapping where isConverted = '1')");
								System.out.println("isExistingAssociate    1   "+query2);
							}
							else if (isExistingAssociate.equalsIgnoreCase("2")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("clientDetail"))
							{
								query2.append(" and ");
								query2.append(" abd.id in (select distinct(associateName) from associate_offering_mapping where isConverted = '2')");
								
								System.out.println("isExistingAssociate    2   "+query2);
							}
							else if (isExistingAssociate.equalsIgnoreCase("0")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("clientDetail"))
							{
								query2.append(" and ");
								query2.append(" abd.id in (select distinct(associateName) from associate_offering_mapping where isConverted = '0')");
								System.out.println("isExistingAssociate    0   "+query2);
							}
						}
						
						if (clientstatus != null && !clientstatus.equalsIgnoreCase("-1") && !clientstatus.equalsIgnoreCase("") && !clientstatus.equalsIgnoreCase("undefined")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{
							query2.append(" and ");
						query2.append(" cbd.id in (select distinct(ccd.clientName) from client_contact_data as ccd, " + "client_takeaction as cta ,client_offering_mapping as com where ccd.id = cta.contacId "
						+ "and ccd.clientName = com.clientName and cta.statusId = '" + clientstatus + "' " + "and com.isConverted = 0) ");
						}
						else
						{
							
						if (isExistingClient.equalsIgnoreCase("0")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{query2.append(" and ");
							// Prospective
						// client
						query2.append(" cbd.id in (select distinct(clientName) from client_offering_mapping where isConverted = '0')");
						}
						else if (isExistingClient.equalsIgnoreCase("1")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{query2.append(" and ");
							// Existing
						// client
						query2.append(" cbd.id in (select distinct(clientName) from client_offering_mapping where isConverted = '1')");
						}
						else if (isExistingClient.equalsIgnoreCase("2")&&!download4.equalsIgnoreCase("leadDetail")&& !download4.equalsIgnoreCase("associateDetail"))
						{query2.append(" and ");
							// Lost
						// client
						query2.append(" cbd.id in (select distinct(clientName) from client_offering_mapping where isConverted = '2')");
						}
						
						}
						
						
						
						
						//System.out.println("query2====" + query2);
						System.out.println("query2.toString():" + query2.toString());
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

				else if (downloadType != null && downloadType.equals("uploadExcel"))
				{
					returnResult = new GenericWriteExcel().createExcel("WFPM", excelName, dataList, titleList, null, excelName);
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						// //System.out.println(" dataList.size() " + dataList.size());
						returnResult = new GenericWriteExcel().createExcel("WFPM", excelName, dataList, titleList, null, excelName);
						if (session.containsKey("columnMap"))
						{
							session.remove("columnMap");
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

	public String beforeAllView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

				setMainHeaderName("All Asset Details >> View");

				getAllColumn4View();
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

	@SuppressWarnings("unchecked")
	public void getAllColumn4View()
	{
		int colCount = 0;
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		GridDataPropertyView gpv = new GridDataPropertyView();
		List astColList = new ArrayList();
		String tableAllias = null;
		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name",
				"asset_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				if (gdp.getColType().toString().equalsIgnoreCase("D"))
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
							masterViewProp.add(gpv);
							astColList.add(paramPair.getKey().toString());
							colCount++;
						}
						columnMap.clear();
					}
				}
				else
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("ast." + gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
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
		}
		statusColName.clear();
		statusColName = Configuration.getConfigurationData("mapped_procurement_master", accountID, connectionSpace, "", 0, "table_name", "procurement_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				if (gdp.getColType().toString().equalsIgnoreCase("D"))
				{
					tableAllias = "astProc";
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
							masterViewProp.add(gpv);
							astColList.add(paramPair.getKey().toString());
							colCount++;
						}
						columnMap.clear();
					}
				}
				else
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("astProc." + gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewProp.add(gpv);
					astColList.add("astProc." + gdp.getColomnName());
					colCount++;
				}
			}
			if (astColList != null && astColList.size() > 0)
			{
				proc1stCol = astColList.get(astColList.size() - colCount).toString();
			}
			procColCount = String.valueOf(colCount);
			colCount = 0;
		}
		statusColName.clear();
		statusColName = Configuration.getConfigurationData("mapped_asset_support", accountID, connectionSpace, "", 0, "table_name", "asset_support");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				if (gdp.getColType().toString().equalsIgnoreCase("D"))
				{
					tableAllias = "astSuprt";
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
							masterViewProp.add(gpv);
							astColList.add(paramPair.getKey().toString());
							colCount++;
						}
						columnMap.clear();
					}
				}
				else
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("astSuprt." + gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewProp.add(gpv);
					astColList.add("astSuprt." + gdp.getColomnName());
					colCount++;
				}
			}
			if (astColList != null && astColList.size() > 0)
			{
				suprt1stCol = astColList.get(astColList.size() - colCount).toString();
			}
			suprtColCount = String.valueOf(colCount);
			colCount = 0;
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
							masterViewProp.add(gpv);
							astColList.add(paramPair.getKey().toString());
							colCount++;
						}
						columnMap.clear();
					}
				}
				else
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName("astAllot." + gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					masterViewProp.add(gpv);
					astColList.add("astAllot." + gdp.getColomnName());
					colCount++;
				}
			}
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
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from asset_detail");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
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
					if (to > getRecords()) to = getRecords();

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
							if (i < fieldNames.size() - 1) query.append(GPV.getColomnName().toString() + " as astCol" + i + ",");
							else query.append(GPV.getColomnName().toString());

							i++;
						}
						query.append(" from asset_detail as ast");
						query.append(" left join procurement_detail astProc on astProc.assetid=ast.id");
						query.append(" left join asset_support_detail astSuprt on astSuprt.assetid=ast.id");
						query.append(" left join asset_allotment_detail astAllot on astAllot.assetid=ast.id");

						if (astColList.contains("astdept.deptName") && session.get("userDeptLevel").toString().equals("2"))
						{
							query.append(" left join subdepartment astsdept on astsdept.id=ast.dept_subdept left join department astdept on astdept.id=astsdept.deptid");
						}
						if (astColList.contains("astdept.deptName") && session.get("userDeptLevel").toString().equals("1"))
						{
							query.append(" left join department astdept on astdept.id=ast.dept_subdept");
						}
						if (astColList.contains("astProcvendor.vendorname"))
						{
							query.append(" left join createvendor_master astProcvendor on astProcvendor.id=astProc.vendorname");
						}
						if (astColList.contains("astSuprtsdept.subdeptname") && !astColList.contains("astSuprtdept.deptName"))
						{
							query.append(" left join subdepartment astSuprtsdept on astSuprtsdept.id=astSuprt.dept_subdept");
						}
						if (astColList.contains("astSuprtdept.deptName") && session.get("userDeptLevel").toString().equals("2"))
						{
							query
									.append(" left join subdepartment astSuprtsdept on astSuprtsdept.id=astSuprt.dept_subdept left join department astSuprtdept on astSuprtdept.id=astSuprtsdept.deptid");
						}
						if (astColList.contains("astSuprtdept.deptName") && session.get("userDeptLevel").toString().equals("1"))
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
						if (astColList.contains("astSuprtsuprtcatg.support_type"))
						{
							query.append(" left join createasset_category_master astSuprtsuprtcatg on astSuprtsuprtcatg.id=astSuprt.supporttype");
						}
						if (astColList.contains("astAllotsdept.subdeptname") && !astColList.contains("astAllotdept.deptName"))
						{
							query.append(" left join subdepartment astAllotsdept on astAllotsdept.id=astAllot.dept_subdept");
						}
						if (astColList.contains("astAllotdept.deptName") && session.get("userDeptLevel").toString().equals("2"))
						{
							query
									.append(" left join subdepartment astAllotsdept on astAllotsdept.id=astAllot.dept_subdept left join department astAllotdept on astAllotdept.id=astAllotsdept.deptid");
						}
						if (astColList.contains("astAllotdept.deptName") && session.get("userDeptLevel").toString().equals("1"))
						{
							query.append(" left join department astAllotdept on astAllotdept.id=astAllot.dept_subdept");
						}
						if (astColList.contains("astAllotemp.empName"))
						{
							query.append(" left join employee_basic astAllotemp on astAllotemp.id=astAllot.employeeid");
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
						query.append(" limit " + from + "," + to);
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
								for (int k = 0; k < astColList.size(); k++)
								{
									if (obdata[k] != null)
									{
										if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											tempMap.put(astColList.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
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
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_asset_master", accountID, connectionSpace, "", 0, "table_name",
					"asset_master");
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
			statusColName = Configuration.getConfigurationData("mapped_asset_support", accountID, connectionSpace, "", 0, "table_name", "asset_support");
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
			}
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

	public String getAssetWithProc()
	{
		return assetWithProc;
	}

	public void setAssetWithProc(String assetWithProc)
	{
		this.assetWithProc = assetWithProc;
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

	public String getProcColCount()
	{
		return procColCount;
	}

	public void setProcColCount(String procColCount)
	{
		this.procColCount = procColCount;
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

	public String getProc1stCol()
	{
		return proc1stCol;
	}

	public void setProc1stCol(String proc1stCol)
	{
		this.proc1stCol = proc1stCol;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSubIndustry() {
		return subIndustry;
	}

	public void setSubIndustry(String subIndustry) {
		this.subIndustry = subIndustry;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStarRating() {
		return starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getLead_name_wild() {
		return lead_name_wild;
	}

	public void setLead_name_wild(String leadNameWild) {
		lead_name_wild = leadNameWild;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<Integer, String> getSpareCatgMap() {
		return spareCatgMap;
	}

	public void setSpareCatgMap(Map<Integer, String> spareCatgMap) {
		this.spareCatgMap = spareCatgMap;
	}

	public String getIsExistingClient() {
		return isExistingClient;
	}

	public void setIsExistingClient(String isExistingClient) {
		this.isExistingClient = isExistingClient;
	}

	public String getClientstatus() {
		return clientstatus;
	}

	public void setClientstatus(String clientstatus) {
		this.clientstatus = clientstatus;
	}

	public String getClient_Name() {
		return client_Name;
	}

	public void setClient_Name(String client_Name) {
		this.client_Name = client_Name;
	}

	public String getAcManager() {
		return acManager;
	}

	public void setAcManager(String acManager) {
		this.acManager = acManager;
	}

	public String getForecastCategary() {
		return forecastCategary;
	}

	public void setForecastCategary(String forecastCategary) {
		this.forecastCategary = forecastCategary;
	}

	public String getFromDateSearch() {
		return fromDateSearch;
	}

	public void setFromDateSearch(String fromDateSearch) {
		this.fromDateSearch = fromDateSearch;
	}

	public String getSalesstages() {
		return salesstages;
	}

	public void setSalesstages(String salesstages) {
		this.salesstages = salesstages;
	}

	public String getClosure_date() {
		return closure_date;
	}

	public void setClosure_date(String closure_date) {
		this.closure_date = closure_date;
	}

	public String getOpportunity_name() {
		return opportunity_name;
	}

	public void setOpportunity_name(String opportunity_name) {
		this.opportunity_name = opportunity_name;
	}

	public String getAssociate_Name() {
		return associate_Name;
	}

	public void setAssociate_Name(String associateName) {
		associate_Name = associateName;
	}

	public String getAssociateCategory() {
		return associateCategory;
	}

	public void setAssociateCategory(String associateCategory) {
		this.associateCategory = associateCategory;
	}

	public String getAssociateType() {
		return associateType;
	}

	public void setAssociateType(String associateType) {
		this.associateType = associateType;
	}

	public String getIsExistingAssociate() {
		return isExistingAssociate;
	}

	public void setIsExistingAssociate(String isExistingAssociate) {
		this.isExistingAssociate = isExistingAssociate;
	}

	public String getAssociatestatus() {
		return associatestatus;
	}

	public void setAssociatestatus(String associatestatus) {
		this.associatestatus = associatestatus;
	}
	
}