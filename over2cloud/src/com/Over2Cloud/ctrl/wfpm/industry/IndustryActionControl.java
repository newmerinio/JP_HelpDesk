package com.Over2Cloud.ctrl.wfpm.industry;

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
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.wfpm.common.CommonHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IndustryActionControl extends ActionSupport implements ServletRequestAware
{
	Map																	session								= ActionContext.getContext().getSession();
	String															userName							= (String) session.get("uName");
	String															accountID							= (String) session.get("accountid");
	SessionFactory											connectionSpace				= (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest					request;
	private int													levelOfIndustry;
	// private Map<String, String> industryLevel1TextBox=null;
	private List<ConfigurationUtilBean>	industryLevel1TextBox	= null;
	private List<ConfigurationUtilBean>	industryLevel2TextBox	= null;
	// private Map<String, String> industryLevel2TextBox=null;
	private String											industryDDColName,status;
	private String											industryDDHeadingName;
	private String											industryLevel1Name;
	private String											industryLevel2Name;
	Map<Integer, String>								industryLevel1				= null;
	// view work here
	private List<Object>								level1DataObject;
	// For Grid View
	private List<GridDataPropertyView>	level1ColmnNames			= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>	level2ColmnNames			= new ArrayList<GridDataPropertyView>();
	private Integer											rows									= 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer											page									= 0;
	// sorting order - asc or desc
	private String											sord									= "";
	// get index row - i.e. user click to sort.
	private String											sidx									= "";
	// Search Field
	private String											searchField						= "";
	// The Search String
	private String											searchString					= "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String											searchOper						= "";
	// Your Total Pages
	private Integer											total									= 0;
	// All Record
	private Integer											records								= 0;
	private boolean											loadonce							= false;
	// Grid colomn view
	private String											oper;
	private String											id;

	private Map<String, Object>					offeringList					= new HashMap<String, Object>();
	private List<List<String>>					taxList								= null;
	private String											lastLevelIndustry			= null;
	
	private Integer countIndustries=0;
	private Integer countSubIndustries=0;

	public String beforeIndustryAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			levelOfIndustry = getIndustryLevelForOrganization();
			setIndustryAddFields();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public int getIndustryLevelForOrganization()
	{
		// //System.out.println("8888888888888888888888888888888");
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			// getting the offering levels for the offering as per configuration and
			// name of the offering levels
			colname.add("orgLevel");
			colname.add("levelName");
			List offeringLevel = cbt.viewAllDataEitherSelectOrAll("mapped_indusry_level_config", colname, connectionSpace);
			String levelsName = null;
			if (offeringLevel != null)
			{
				for (Object c : offeringLevel)
				{
					Object[] dataC = (Object[]) c;
					levelOfIndustry = Integer.parseInt((String) dataC[0]);
					levelsName = (String) dataC[1];
				}
			}
			if (levelsName != null)
			{
				String tempName[] = levelsName.split("#");
				if (levelOfIndustry > 0)
				{
					industryLevel1Name = tempName[0] + " >> Registration";
					lastLevelIndustry = tempName[0];
				}
				if (levelOfIndustry > 1)
				{
					industryLevel2Name = tempName[1] + " >> Registration";
					lastLevelIndustry = tempName[1];
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return levelOfIndustry;
	}

	/**
	 * setting the Industry create page data as per industry configuration.
	 */
	public void setIndustryAddFields()
	{
		try
		{
			if (levelOfIndustry > 0)
			{

				List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_industry_configuration", accountID, connectionSpace, "", 0,
						"table_name", "industry_configuration");
				if (offeringLevel1 != null)
				{
					// industryLevel1TextBox=new LinkedHashMap<String, String>();
					industryLevel1TextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : offeringLevel1)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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

							industryLevel1TextBox.add(obj);
							// industryLevel1TextBox.put(gdp.getColomnName(),
							// gdp.getHeadingName());

						}
					}
				}

			}
			if (levelOfIndustry > 1)
			{
				List<GridDataPropertyView> offeringLevel2 = Configuration.getConfigurationData("mapped_sub_industry_configuration", accountID, connectionSpace, "", 0,
						"table_name", "sub_industry_configuration");
				if (offeringLevel2 != null)
				{
					industryLevel2TextBox = new ArrayList<ConfigurationUtilBean>();

					// industryLevel2TextBox=new LinkedHashMap<String, String>();
					for (GridDataPropertyView gdp : offeringLevel2)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
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
							industryLevel2TextBox.add(obj);
							// industryLevel2TextBox.put(gdp.getColomnName(),
							// gdp.getHeadingName());
						}
						if (gdp.getColType().trim().equalsIgnoreCase("D"))
						{
							// industryDDColName=gdp.getColomnName();
							industryDDHeadingName = gdp.getHeadingName();
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

	// Grid column view Code
	public String beforeIndustryView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			levelOfIndustry = getIndustryLevelForOrganization();
			if (levelOfIndustry > 0) setIndustryLevel1Name(getIndustryLevel1Name().subSequence(0, getIndustryLevel1Name().indexOf(" >> Registration")).toString());
			if (levelOfIndustry > 1) setIndustryLevel2Name(getIndustryLevel2Name().subSequence(0, getIndustryLevel2Name().indexOf(" >> Registration")).toString());
			setGridColmunsForIndustry();
			
			//count Industry and subIndustry (by Azad 7July 2014)
			String query1="select count(*) from industrydatalevel1";
			countIndustries=countRecord(query1);
			String query2="select count(*) from subindustrydatalevel2";
			countSubIndustries=countRecord(query2);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	//function of count record (by Azad 7july 2014)
	public Integer countRecord(String query)
	{ 	  BigInteger totalRecord=new BigInteger("3");
	    List listData=null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		listData=cbt.executeAllSelectQuery(query,connectionSpace);
	          if(listData!=null)
	          {
	        	  for(Iterator iterator=listData.iterator(); iterator.hasNext();)
	        	  {
	        	
	        		  totalRecord=(BigInteger) iterator.next();
	        	  }
	          }
		
		
		return totalRecord.intValue();
	}

	public void setGridColmunsForIndustry()
	{

		GridDataPropertyView gpv = new GridDataPropertyView();
		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_industry_configuration", accountID, connectionSpace, "", 0,
				"table_name", "industry_configuration");
		{
			returnResult = Configuration.getConfigurationData("mapped_sub_industry_configuration", accountID, connectionSpace, "", 0, "table_name",
					"sub_industry_configuration");
			if (returnResult != null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					// //System.out.println("returnResult>>>>>>Sub Industry>>>>>" +
					// gdp.getColomnName());
					if (!gdp.getColType().trim().equalsIgnoreCase(""))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						gpv.setAlign(gdp.getAlign());
						level2ColmnNames.add(gpv);
					}
				}
			}
		}
	}

	/**
	 * Adding Industry from level 1 to level 2
	 */

	public String level1AddIndustry()
	{
		String returnValue = LOGIN;
		if (ValidateSession.checkSession())
		{

			boolean status = new IndustryHelper().addIndustry(connectionSpace, request, userName, accountID);
			if (status) addActionMessage("Industry Registered Successfully!!!");
			else addActionMessage("Oops There is some error in data!");

			returnValue = SUCCESS;
		}
		return returnValue;
	}

	public String getallLevel1Industry()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select id ,industry from industrydatalevel1 order by industry ";
			List offeringData = cbt.executeAllSelectQuery(query, connectionSpace);
			industryLevel1 = new LinkedHashMap<Integer, String>();
			if (offeringData != null)
			{
				for (Object c : offeringData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && dataC[1] != null) industryLevel1.put((Integer) dataC[0], dataC[1].toString());
				}
			}
			else
			{
				return ERROR;
			}
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_industry_configuration", accountID, connectionSpace, "", 0,
					"table_name", "industry_configuration");
			industryLevel1TextBox = new ArrayList<ConfigurationUtilBean>();
			for (GridDataPropertyView gdp : offeringLevel1)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("industry"))
				{
					industryDDHeadingName = gdp.getHeadingName();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String level2AddSubIndustry()
	{
		boolean flag = false;
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();

			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			boolean userTrue = false;
			boolean dateTrue = false;
			boolean timeTrue = false;
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_sub_industry_configuration", accountID, connectionSpace, "", 0,
					"table_name", "sub_industry_configuration");
			if (org2 != null)
			{
				// create table query based on configuration

				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = null;
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");

					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}

				cbt.createTable22("subindustrydatalevel2", Tablecolumesaaa, connectionSpace);

				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				String industryID = null;
				String[] subIndustry = null;
				String[] subIndustryBrief = null;

				InsertDataTable ob = null;
				int count = 0;
				while (it.hasNext())
				{
					String Parmname = it.next().toString();// control Name stored in
					// Paramname
					//String paramValue = request.getParameter(Parmname);

					if (Parmname != null && !Parmname.trim().equals(""))
					{
						if (Parmname.equalsIgnoreCase("industry"))
						{
							industryID = request.getParameter(Parmname);
							// //System.out.println("industryID====" + industryID);
						}
						else if (Parmname.equalsIgnoreCase("subIndustry"))//
						{
							subIndustry = request.getParameterValues(Parmname);
							// //System.out.println("subIndustry====" + subIndustry);
						}
						else if (Parmname.equalsIgnoreCase("brief"))//
						{
							subIndustryBrief = request.getParameterValues(Parmname);
							// //System.out.println("subIndustryBrief====" + subIndustryBrief);
						}
						else
						{

							ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(CommonHelper.getFieldValue(request.getParameter(Parmname)));
							insertData.add(ob);
						}
					}
				}

				ob = new InsertDataTable();
				ob.setColName("industry");
				ob.setDataName(industryID);
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
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("subIndustry");
				// ob1.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob1);

				InsertDataTable ob2 = new InsertDataTable();
				ob2.setColName("brief");
				// ob2.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob2);

				//System.out.println("subIndustryBrief>>"+subIndustryBrief);
				for (int i = 0; i < subIndustry.length; i++)
				{
					if (subIndustry[i] != null && !subIndustry[i].trim().equals(""))
					{
						ob1.setDataName(subIndustry[i]);
						//System.out.println("subIndustryBrief[i]=="+subIndustryBrief[i]);
						ob2.setDataName(CommonHelper.getFieldValue(subIndustryBrief[i]));

						flag = cbt.insertIntoTable("subindustrydatalevel2", insertData, connectionSpace);
						if (flag) count++;
					}
				}

				if (count > 0)
				{
					addActionMessage(count + " Sub Industry saved successfully");
					return SUCCESS;
				}
				else
				{
					addActionMessage("There is some error in data!");
					return SUCCESS;
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String viewLevel1Industry()
	{
		// //System.out.println("viewLevel1Industry>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from subindustrydatalevel2");
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
				if (to > getRecords()) to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List<String> fieldName = new ArrayList<String>();

				fieldName.add("id");
				fieldName.add("industry");
				fieldName.add("subIndustry");
				fieldName.add("date");
				fieldName.add("time");
				fieldName.add("userName");
				fieldName.add("brief");
				fieldName.add("status");

				query.append("sid.id" + ",");
				query.append("idd.industry" + ",");
				query.append("sid.subIndustry" + ",");
				query.append("sid.date" + ",");
				query.append("sid.time" + ",");
				query.append("sid.userName" + ",");
				query.append("sid.brief" + " , ");
				query.append("sid.status" + " ");
				query.append(" from subindustrydatalevel2 as sid " + "inner join industrydatalevel1 as idd on sid.industry=" + "idd.id ");
				// //System.out.println("query>>>>>>>>>>view>" + query);
				// for Searching start here
				
				if(this.getStatus()!=null && !this.getStatus().equals("-1") &&  !this.getStatus().equalsIgnoreCase("undefined"))
		    	{
				  query.append(" where sid.status='"+getStatus()+"'");
			    }
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
					}
				}
				// End Here
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				String[] arrayfieldName = fieldName.toArray(new String[fieldName.size()]);
				// //System.out.println(">>>>>>>>>>" + arrayfieldName.length);
				// //System.out.println(">>>>>>>>>>" + data.size());
				if (data != null && data.size() > 0)
				{
					List<Object> tempList = new ArrayList<Object>();
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> tempMap = new HashMap<String, Object>();
						for (int k = 0; k < arrayfieldName.length; k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									tempMap.put(arrayfieldName[k].toString(), (Integer) obdata[k]);
									// //System.out.println("tempMap>>>>when k=0>>>" + tempMap);
								}
								else
								{
									if (arrayfieldName[k].toString().equalsIgnoreCase("date")) tempMap.put(arrayfieldName[k].toString(), DateUtil
											.convertDateToIndianFormat(obdata[k].toString()));
									else tempMap.put(arrayfieldName[k].toString(), obdata[k]);

									// //System.out.println("tempMap>>>else?>>>>>>>>>>" + tempMap);
								}
							}

						}
						tempList.add(tempMap);
						setLevel1DataObject(tempList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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

	public String viewModifyLevel1()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			// //System.out.println("getOper" + getOper());
			Map<String, Object> wherClause = new HashMap<String, Object>();
			Map<String, Object> condtnBlock = new HashMap<String, Object>();
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid") && !Parmname.equalsIgnoreCase("date")
							&& !Parmname.equalsIgnoreCase("time") && !Parmname.equalsIgnoreCase("userName") && !Parmname.equalsIgnoreCase("industry"))

					wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("subindustrydatalevel2", wherClause, condtnBlock, connectionSpace);
			}
			else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length) condtIds.append(H + " ,");
					else condtIds.append(H);
					i++;
				}
				
				condtnBlock.put("id", condtIds.toString());
				wherClause.put("status","Inactive");
				cbt.updateTableColomn("subindustrydatalevel2", wherClause, condtnBlock, connectionSpace);
			//	cbt.deleteAllRecordForId("subindustrydatalevel2", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
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

	// add--------
	public int getLevelOfIndustry()
	{
		return levelOfIndustry;
	}

	public void setLevelOfIndustry(int levelOfIndustry)
	{
		this.levelOfIndustry = levelOfIndustry;
	}

	public String getIndustryLevel1Name()
	{
		return industryLevel1Name;
	}

	public void setIndustryLevel1Name(String industryLevel1Name)
	{
		this.industryLevel1Name = industryLevel1Name;
	}

	public String getIndustryLevel2Name()
	{
		return industryLevel2Name;
	}

	public void setIndustryLevel2Name(String industryLevel2Name)
	{
		this.industryLevel2Name = industryLevel2Name;
	}

	public List<GridDataPropertyView> getLevel1ColmnNames()
	{
		return level1ColmnNames;
	}

	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames)
	{
		this.level1ColmnNames = level1ColmnNames;
	}

	public String getLastLevelIndustry()
	{
		return lastLevelIndustry;
	}

	public void setLastLevelIndustry(String lastLevelIndustry)
	{
		this.lastLevelIndustry = lastLevelIndustry;
	}

	public String getIndustryDDColName()
	{
		return industryDDColName;
	}

	public void setIndustryDDColName(String industryDDColName)
	{
		this.industryDDColName = industryDDColName;
	}

	public String getIndustryDDHeadingName()
	{
		return industryDDHeadingName;
	}

	public void setIndustryDDHeadingName(String industryDDHeadingName)
	{
		this.industryDDHeadingName = industryDDHeadingName;
	}

	public List<GridDataPropertyView> getLevel2ColmnNames()
	{
		return level2ColmnNames;
	}

	public void setLevel2ColmnNames(List<GridDataPropertyView> level2ColmnNames)
	{
		this.level2ColmnNames = level2ColmnNames;
	}

	public Map<Integer, String> getIndustryLevel1()
	{
		return industryLevel1;
	}

	public void setIndustryLevel1(Map<Integer, String> industryLevel1)
	{
		this.industryLevel1 = industryLevel1;
	}

	public List<Object> getLevel1DataObject()
	{
		return level1DataObject;
	}

	public void setLevel1DataObject(List<Object> level1DataObject)
	{
		this.level1DataObject = level1DataObject;
	}

	public List<ConfigurationUtilBean> getIndustryLevel1TextBox()
	{
		return industryLevel1TextBox;
	}

	public void setIndustryLevel1TextBox(List<ConfigurationUtilBean> industryLevel1TextBox)
	{
		this.industryLevel1TextBox = industryLevel1TextBox;
	}

	public List<ConfigurationUtilBean> getIndustryLevel2TextBox()
	{
		return industryLevel2TextBox;
	}

	public void setIndustryLevel2TextBox(List<ConfigurationUtilBean> industryLevel2TextBox)
	{
		this.industryLevel2TextBox = industryLevel2TextBox;
	}

	public Integer getCountIndustries() {
		return countIndustries;
	}

	public void setCountIndustries(Integer countIndustries) {
		this.countIndustries = countIndustries;
	}

	public Integer getCountSubIndustries() {
		return countSubIndustries;
	}

	public void setCountSubIndustries(Integer countSubIndustries) {
		this.countSubIndustries = countSubIndustries;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
