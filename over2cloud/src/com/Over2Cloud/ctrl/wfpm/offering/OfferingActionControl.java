package com.Over2Cloud.ctrl.wfpm.offering;

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

public class OfferingActionControl extends ActionSupport implements ServletRequestAware
{

	Map																	session								= ActionContext.getContext().getSession();
	String															userName							= (String) session.get("uName");
	String															accountID							= (String) session.get("accountid");
	SessionFactory											connectionSpace				= (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest					request;
	private int													levelOfOffering;
//	private Map<String, String>					offeringLevel1TextBox	= null;
	//private Map<String, String>					offeringLevel2TextBox	= null;
//	private Map<String, String>					offeringLevel3TextBox	= null;
	//private Map<String, String>					offeringLevel4TextBox	= null;
	private List<ConfigurationUtilBean>	offeringLevel1TextBox			= null;
	private List<ConfigurationUtilBean>	offeringLevel2TextBox			= null;
	private List<ConfigurationUtilBean> offeringLevel3TextBox			= null;
	private List<ConfigurationUtilBean> offeringLevel4TextBox			= null;
	private Map<String, String>					offeringLevel5TextBox	= null;
	private String											offeringLevel1Name,status;
	private String											offeringLevel2Name;
	private String											offeringLevel3Name;
	private String											offeringLevel4Name;
	private String											offeringLevel5Name;
	private Map<Integer, String>				offeringLevel1				= null;
	private Map<Integer, String>				offeringLevel2				= null;
	private Map<Integer, String>				offeringLevel3				= null;
	private Map<Integer, String>				offeringLevel4				= null;
	private Map<Integer, String>				offeringLevel5				= null;

	// view work here
	private List<Object>								level1DataObject;
	private String											modifyFlag;
	private String											deleteFlag;
	private String											mainHeaderName;
	private List<GridDataPropertyView>	level1ColmnNames			= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>	level2ColmnNames			= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>	level3ColmnNames			= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>	level4ColmnNames			= new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>	level5ColmnNames			= new ArrayList<GridDataPropertyView>();
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
	private String											lastLevelOffering			= null;
	private InputStream									offering;
	private String											offeringFileName;
	private String											offeringContentType;
	private Integer countVertical=0;
	private Integer countOffering=0;
	private Integer countSubOffering=0;

	

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String beforeOfferingAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			levelOfOffering = getOfferingLevelForOrganization();
			setOfferinglevelsFields();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getallLevel1Offering()
	{
		// //System.out.println("999999999999999999999999999999");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			colname.add("id");
			colname.add("verticalname");
			List offeringData = cbt.viewAllDataEitherSelectOrAll("offeringlevel1", colname, connectionSpace);
			// ////System.out.println("offeringData:"+offeringData.size());
			if (offeringData != null)
			{
				offeringLevel1 = new LinkedHashMap<Integer, String>();
				for (Object c : offeringData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && dataC[1] != null) offeringLevel1.put((Integer) dataC[0], dataC[1].toString());
				}
			}
			else
			{
				return ERROR;
			}
			List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_offering_level1", accountID, connectionSpace, "", 0, "table_name",
					"offering_level1");
			offeringLevel1TextBox = new ArrayList<ConfigurationUtilBean>();
			for (GridDataPropertyView gdp : offeringLevel1)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("verticalname"))
				{
					offeringLevel2Name = gdp.getHeadingName();
				}
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getallLevel2Offering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			colname.add("id");
			colname.add("offeringname");
			List offeringData = cbt.viewAllDataEitherSelectOrAll("offeringlevel2", colname, connectionSpace);
			if (offeringData != null)
			{
				offeringLevel2 = new LinkedHashMap<Integer, String>();
				for (Object c : offeringData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && dataC[1] != null) offeringLevel2.put((Integer) dataC[0], dataC[1].toString());
				}
			}
			else
			{
				return ERROR;
			}
			List<GridDataPropertyView> offeringLevel2 = Configuration.getConfigurationData("mapped_offering_level2", accountID, connectionSpace, "", 0, "table_name",
					"offering_level2");
			offeringLevel2TextBox = new ArrayList<ConfigurationUtilBean>();
			for (GridDataPropertyView gdp : offeringLevel2)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("offeringname"))
				{
					offeringLevel3Name = gdp.getHeadingName();
				}
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getallLevel3Offering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			colname.add("id");
			colname.add("subofferingname");
			List offeringData = cbt.viewAllDataEitherSelectOrAll("offeringlevel3", colname, connectionSpace);
			if (offeringData != null)
			{
				offeringLevel3 = new LinkedHashMap<Integer, String>();
				for (Object c : offeringData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && dataC[1] != null) offeringLevel3.put((Integer) dataC[0], dataC[1].toString());
				}
			}
			else
			{
				return ERROR;
			}
			List<GridDataPropertyView> offeringLevel3 = Configuration.getConfigurationData("mapped_offering_level3", accountID, connectionSpace, "", 0, "table_name",
					"offering_level3");
			offeringLevel3TextBox = new ArrayList<ConfigurationUtilBean>();
			for (GridDataPropertyView gdp : offeringLevel3)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("subofferingname"))
				{
					offeringLevel4Name = gdp.getHeadingName();
				}
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getallLevel4Offering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			colname.add("id");
			colname.add("variantname");
			List offeringData = cbt.viewAllDataEitherSelectOrAll("offeringlevel4", colname, connectionSpace);
			if (offeringData != null)
			{
				offeringLevel4 = new LinkedHashMap<Integer, String>();
				for (Object c : offeringData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && dataC[1] != null) offeringLevel4.put((Integer) dataC[0], dataC[1].toString());
				}
			}
			else
			{
				return ERROR;
			}
			List<GridDataPropertyView> offeringLevel4 = Configuration.getConfigurationData("mapped_offering_level4", accountID, connectionSpace, "", 0, "table_name",
					"offering_level4");
			offeringLevel4TextBox = new ArrayList<ConfigurationUtilBean>();
			for (GridDataPropertyView gdp : offeringLevel4)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("variantname"))
				{
					offeringLevel5Name = gdp.getHeadingName();
				}
			}

		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public String getallLevel5Offering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			colname.add("id");
			colname.add("subvariantname");
			List offeringData = cbt.viewAllDataEitherSelectOrAll("offeringlevel5", colname, connectionSpace);
			if (offeringData != null)
			{
				offeringLevel5 = new LinkedHashMap<Integer, String>();
				for (Object c : offeringData)
				{
					Object[] dataC = (Object[]) c;
					if (dataC[0] != null && dataC[1] != null) offeringLevel5.put((Integer) dataC[0], dataC[1].toString());
				}
			}
			else
			{
				return ERROR;
			}
			List<GridDataPropertyView> offeringLevel5 = Configuration.getConfigurationData("mapped_offering_level5", accountID, connectionSpace, "", 0, "table_name",
					"offering_level5");
			// offeringLevel4TextBox=new LinkedHashMap<String, String>();
			for (GridDataPropertyView gdp : offeringLevel5)
			{
				if (gdp.getColType().trim().equalsIgnoreCase("T") && gdp.getColomnName().equalsIgnoreCase("subvariantname"))
				{
					offeringLevel5Name = gdp.getHeadingName();
				}
			}

		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Adding offering from level 1 to level 5
	 */

	public String level1AddOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_offering_level1", accountID, connectionSpace, "", 0, "table_name",
					"offering_level1");
			if (org2 != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				cbt.createTable22("offeringlevel1", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}
				if (insertData.size() > 3) // when their is atleast one data
				status = cbt.insertIntoTable("offeringlevel1", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Offering Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
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

	public String level2AddOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_offering_level2", accountID, connectionSpace, "", 0, "table_name",
					"offering_level2");
			if (org2 != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				cbt.createTable22("offeringlevel2", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}
				if (insertData.size() > 3) // when their is atleast one data
				status = cbt.insertIntoTable("offeringlevel2", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Offering Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
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

	public String level3AddOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_offering_level3", accountID, connectionSpace, "", 0, "table_name",
					"offering_level3");
			if (org2 != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				cbt.createTable22("offeringlevel3", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}

				if (insertData.size() > 3) // when their is atleast one data
				status = cbt.insertIntoTable("offeringlevel3", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Offering Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
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

	public String level4AddOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_offering_level4", accountID, connectionSpace, "", 0, "table_name",
					"offering_level4");
			if (org2 != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				cbt.createTable22("offeringlevel4", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}

				if (insertData.size() > 3) // when their is atleast one data
				status = cbt.insertIntoTable("offeringlevel4", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Offering Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
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

	public String level5AddOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<GridDataPropertyView> org2 = Configuration.getConfigurationData("mapped_offering_level5", accountID, connectionSpace, "", 0, "table_name",
					"offering_level5");
			if (org2 != null)
			{
				// create table query based on configuration
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = false;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				boolean userTrue = false;
				boolean dateTrue = false;
				boolean timeTrue = false;
				for (GridDataPropertyView gdp : org2)
				{
					TableColumes ob1 = new TableColumes();
					ob1 = new TableColumes();
					ob1.setColumnname(gdp.getColomnName());
					ob1.setDatatype("varchar(255)");
					ob1.setConstraint("default NULL");
					Tablecolumesaaa.add(ob1);
					if (gdp.getColomnName().equalsIgnoreCase("userName")) userTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("date")) dateTrue = true;
					else if (gdp.getColomnName().equalsIgnoreCase("time")) timeTrue = true;
				}
				cbt.createTable22("offeringlevel5", Tablecolumesaaa, connectionSpace);
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null)
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
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
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);
				}

				if (insertData.size() > 3) // when their is atleast one data
				status = cbt.insertIntoTable("offeringlevel5", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Offering Registered Successfully!!!");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Oops There is some error in data!");
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

	/**
	 * Method for getting the offering levels and the names of the levels of
	 * offering as per configuration
	 */
	public int getOfferingLevelForOrganization()
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
			List offeringLevel = cbt.viewAllDataEitherSelectOrAll("mapped_offering_level", colname, connectionSpace);
			String levelsName = null;
			if (offeringLevel != null)
			{
				for (Object c : offeringLevel)
				{
					Object[] dataC = (Object[]) c;
					levelOfOffering = Integer.parseInt((String) dataC[0]);
					levelsName = (String) dataC[1];
				}
			}
			if (levelsName != null)
			{
				String tempName[] = levelsName.split("#");
				if (levelOfOffering > 0)
				{
					offeringLevel1Name = tempName[0];
					lastLevelOffering = tempName[0];
				}
				if (levelOfOffering > 1)
				{
					offeringLevel2Name = tempName[1];
					lastLevelOffering = tempName[1];
				}
				if (levelOfOffering > 2)
				{
					offeringLevel3Name = tempName[2];
					lastLevelOffering = tempName[2];
				}
				if (levelOfOffering > 3)
				{
					offeringLevel4Name = tempName[3];
					lastLevelOffering = tempName[3];
				}
				if (levelOfOffering > 4)
				{
					offeringLevel5Name = tempName[4];
					lastLevelOffering = tempName[4];
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return levelOfOffering;
	}

	/**
	 * setting the offering create page data as per organization configuration.
	 */
	public void setOfferinglevelsFields()
	{
		try
		{
			if (levelOfOffering > 0)
			{

				List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_offering_level1", accountID, connectionSpace, "", 0,
						"table_name", "offering_level1");
				if (offeringLevel1 != null)
				{
					offeringLevel1TextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : offeringLevel1)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (!gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status") && gdp.getColType().trim().equalsIgnoreCase("T"))
						{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if (gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
									System.out.println("getMandatory?>>");
								}
								else
								{
									obj.setMandatory(false);
								}
								offeringLevel1TextBox.add(obj);
						}
							
					}
				}

			}
			if (levelOfOffering > 1)
			{
				List<GridDataPropertyView> offeringLevel2 = Configuration.getConfigurationData("mapped_offering_level2", accountID, connectionSpace, "", 0,
						"table_name", "offering_level2");
				if (offeringLevel2 != null)
				{
					offeringLevel2TextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : offeringLevel2)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (!gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
						{
							if(gdp.getColType().trim().equalsIgnoreCase("D"))
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
								//offeringLevel2TextBox.add(obj);
							}
							if(gdp.getColType().trim().equalsIgnoreCase("T"))
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
							offeringLevel2TextBox.add(obj);
							}
						}
					}
				}
			}
			if (levelOfOffering > 2)
			{
				List<GridDataPropertyView> offeringLevel3 = Configuration.getConfigurationData("mapped_offering_level3", accountID, connectionSpace, "", 0,
						"table_name", "offering_level3");
				if (offeringLevel3 != null)
				{
					offeringLevel3TextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : offeringLevel3)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (!gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")  && !gdp.getColomnName().equalsIgnoreCase("status"))
						{
							if(gdp.getColType().trim().equalsIgnoreCase("D"))
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
								//offeringLevel3TextBox.add(obj);
							}
							if(gdp.getColType().trim().equalsIgnoreCase("T"))
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
							offeringLevel3TextBox.add(obj);
						  }
					   }
					}
				}
			}
			if (levelOfOffering > 3)
			{
				List<GridDataPropertyView> offeringLevel4 = Configuration.getConfigurationData("mapped_offering_level4", accountID, connectionSpace, "", 0,
						"table_name", "offering_level4");
				if (offeringLevel4 != null)
				{
					offeringLevel4TextBox = new ArrayList<ConfigurationUtilBean>();
					for (GridDataPropertyView gdp : offeringLevel4)
					{
						ConfigurationUtilBean obj = new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							if(gdp.getColType().trim().equalsIgnoreCase("D"))
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
								//offeringLevel4TextBox.add(obj);
							}
							if(gdp.getColType().trim().equalsIgnoreCase("T"))
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
							offeringLevel4TextBox.add(obj);
						  }}
					}
				}
			}
			if (levelOfOffering > 4)
			{
				List<GridDataPropertyView> offeringLevel5 = Configuration.getConfigurationData("mapped_offering_level5", accountID, connectionSpace, "", 0,
						"table_name", "offering_level5");
				if (offeringLevel5 != null)
				{
					offeringLevel5TextBox = new LinkedHashMap<String, String>();
					for (GridDataPropertyView gdp : offeringLevel5)
					{
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
								&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time"))
						{
							if (gdp.getMandatroy().toString().equals("1"))
							{
								gdp.setMandatroy("true");
							}
							else
							{
								gdp.setMandatroy("false");
							}
							offeringLevel5TextBox.put(gdp.getColomnName(), gdp.getHeadingName());
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

	/**
	 *Before View Of Offering
	 */

	public String beforeOfferingView()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			levelOfOffering = getOfferingLevelForOrganization();
			if (getModifyFlag().equalsIgnoreCase("0")) setModifyFlag("false");
			else setModifyFlag("true");
			if (getDeleteFlag().equalsIgnoreCase("0")) setDeleteFlag("false");
			else setDeleteFlag("true");

			if (levelOfOffering > 0) setOfferingLevel1Name(getOfferingLevel1Name());
			if (levelOfOffering > 1) setOfferingLevel2Name(getOfferingLevel2Name());
			if (levelOfOffering > 2) setOfferingLevel3Name(getOfferingLevel3Name());
			if (levelOfOffering > 3) setOfferingLevel4Name(getOfferingLevel4Name());
			if (levelOfOffering > 4) setOfferingLevel5Name(getOfferingLevel5Name());

			if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false")) setMainHeaderName("Offering >> View");
			else if (getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false")) setMainHeaderName("Offering >> Modify");
			else if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true")) setMainHeaderName("Offering >> Delete");

			setGridColmunsForOffering();
			
			//Count total record (by Azad 7July 2014)
			String query1="select count(*) from offeringlevel1";
			countVertical=countRecord(query1);
			String query2="select count(*) from offeringlevel2";
			countOffering=countRecord(query2);
			String query3="select count(*) from offeringlevel3";
			countSubOffering=countRecord(query3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>beforeOfferingView:"+
		// levelOfOffering);
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
	
	

	/*
	 * Anoop, 9-7-2013 get last level of data for offering
	 */

	@SuppressWarnings("unused")
	private void getLastLevelCostMapping(List<GridDataPropertyView> level1ColmnNames)
	{
		GridDataPropertyView gpv = null;
		gpv = new GridDataPropertyView();
		gpv.setColomnName("size");
		gpv.setHeadingName("Size");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("skuNo");
		gpv.setHeadingName("SKU No");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("barCode");
		gpv.setHeadingName("Bar Code");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("dap");
		gpv.setHeadingName("DAP");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("mrp");
		gpv.setHeadingName("MRP");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("cutOffPrice");
		gpv.setHeadingName("Cut Off Price");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("description");
		gpv.setHeadingName("Description");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);

		gpv = new GridDataPropertyView();
		gpv.setColomnName("code");
		gpv.setHeadingName("Other");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setAlign("left");
		level1ColmnNames.add(gpv);
	}

	public void setGridColmunsForOffering()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Registration Id");
		gpv.setHideOrShow("true");
		level1ColmnNames.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_offering_level1", accountID, connectionSpace, "", 0, "table_name",
				"offering_level1");
		if (returnResult != null)
		{
			for (GridDataPropertyView gdp : returnResult)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				gpv.setAlign(gdp.getAlign());
				level1ColmnNames.add(gpv);
			}
		}

		/**
		 * level 2 grid colomn view
		 */
		if (levelOfOffering > 1)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Registration Id");
			gpv.setHideOrShow("true");
			level2ColmnNames.add(gpv);
			returnResult = Configuration.getConfigurationData("mapped_offering_level2", accountID, connectionSpace, "", 0, "table_name", "offering_level2");
			if (returnResult != null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					if (gdp.getColomnName().equalsIgnoreCase("verticalname")) continue;
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

		if (levelOfOffering > 2)
		{
			/**
			 * level 3 configuration
			 */
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Registration Id");
			gpv.setHideOrShow("true");
			level3ColmnNames.add(gpv);
			returnResult = Configuration.getConfigurationData("mapped_offering_level3", accountID, connectionSpace, "", 0, "table_name", "offering_level3");

			if (returnResult != null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					if (gdp.getColomnName().equalsIgnoreCase("offeringname")) continue;
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					level3ColmnNames.add(gpv);
				}
			}

		}

		if (levelOfOffering > 3)
		{
			/**
			 * level 4 configuration
			 */
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Registration Id");
			gpv.setHideOrShow("true");
			level4ColmnNames.add(gpv);
			returnResult = Configuration.getConfigurationData("mapped_offering_level4", accountID, connectionSpace, "", 0, "table_name", "offering_level4");

			if (returnResult != null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					if (gdp.getColomnName().equalsIgnoreCase("subofferingname")) continue;
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					level4ColmnNames.add(gpv);
				}
			}
		}

		if (levelOfOffering > 4)
		{
			/**
			 * level 5 configuration
			 */
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Registration Id");
			gpv.setHideOrShow("true");
			level5ColmnNames.add(gpv);
			returnResult = Configuration.getConfigurationData("mapped_offering_level5", accountID, connectionSpace, "", 0, "table_name", "offering_level5");

			if (returnResult != null)
			{
				for (GridDataPropertyView gdp : returnResult)
				{
					if (gdp.getColomnName().equalsIgnoreCase("variantname")) continue;
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					level5ColmnNames.add(gpv);
				}
			}

		}

		// Set view of cost for last level
		if (levelOfOffering == 1) getLastLevelCostMapping(level1ColmnNames);
		else if (levelOfOffering == 2) getLastLevelCostMapping(level2ColmnNames);
		else if (levelOfOffering == 3) getLastLevelCostMapping(level3ColmnNames);
		else if (levelOfOffering == 4) getLastLevelCostMapping(level4ColmnNames);
		else if (levelOfOffering == 5) getLastLevelCostMapping(level5ColmnNames);

	}

	public String viewLevel1Offering()
	{
		// System.out.println(
		// "viewLevel1Offering()................................................");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from offeringlevel1");
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
				List fieldNames = Configuration.getColomnList("mapped_offering_level1", accountID, connectionSpace, "offering_level1");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (i < fieldNames.size() - 1) query.append(obdata.toString() + ",");
						else query.append(obdata.toString());
					}
					i++;

				}

				query.append(" from offeringlevel1 where id is not null ");
				if (this.getStatus() != null && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("undefined"))
				{
					query.append(" and status='" + this.getStatus() + "'");
				}
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
				//	query.append(" where ");
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

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "offeringlevel1", connectionSpace);

				// oferring taken by client in configuration
				String offeringLevel = session.get("offeringLevel").toString();
				String[] oLevels = null;

				if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);
				System.out.println("query.toString()"+query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null)
				{
					// level1Data
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						int offeringId = -1;
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (k == 0)
							{
								one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								offeringId = (Integer) obdata[k];
							}
							else if (CommonHelper.getFieldValue(fieldNames.get(k)).equalsIgnoreCase("date"))
							{
								one.put(CommonHelper.getFieldValue(fieldNames.get(k)), DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(obdata[k])));
							}
							else one.put(CommonHelper.getFieldValue(fieldNames.get(k)), CommonHelper.getFieldValue(obdata[k]));
						}
						if (level == 1 && offeringId != -1)
						{
							setLastLevelCost(one, offeringId, level);
						}
						Listhb.add(one);
					}
					setLevel1DataObject(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// System.out.println(
		// "viewLevel1Offering()................................................");
		return SUCCESS;
	}

	/*
	 * Anoop, 9-7-2013 put offering cost in last level of offering view
	 */
	@SuppressWarnings("unused")
	private void setLastLevelCost(Map<String, Object> one, int offeringId, int level)
	{
		CommonOperInterface coi = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder();
		query.append("select size, skuNo, barCode, dap, mrp, cutOffPrice, description, code from offering_level_cost_mapping where offeringId = ");
		query.append(offeringId);
		query.append(" and offeringLevelId = ");
		query.append(level);
		// ////System.out.println("QUERY: "+query);
		List data = coi.executeAllSelectQuery(query.toString(), connectionSpace);
		// ////System.out.println("data: "+data.size());
		if (data != null && data.size() > 0)
		{
			Object[] obj = (Object[]) data.get(0);
			if (obj != null)
			{
				if (obj[0] != null)
				{
					one.put("size", obj[0].toString());
				}
				else
				{
					one.put("size", "NA");
				}
				if (obj[1] != null)
				{
					one.put("skuNo", obj[1].toString());
				}
				else
				{
					one.put("skuNo", "NA");
				}
				if (obj[2] != null)
				{
					one.put("barCode", obj[2].toString());
				}
				else
				{
					one.put("barCode", "NA");
				}
				if (obj[3] != null)
				{
					one.put("dap", obj[3].toString());
				}
				else
				{
					one.put("dap", "NA");
				}
				if (obj[4] != null)
				{
					one.put("mrp", obj[4].toString());
				}
				else
				{
					one.put("mrp", "NA");
				}
				if (obj[5] != null)
				{
					one.put("cutOffPrice", obj[5].toString());
				}
				else
				{
					one.put("cutOffPrice", "NA");
				}
				if (obj[6] != null)
				{
					one.put("description", obj[6].toString());
				}
				else
				{
					one.put("description", "NA");
				}
				if (obj[7] != null)
				{
					one.put("code", obj[7].toString());
				}
				else
				{
					one.put("code", "NA");
				}
			}
		}
		else
		{
			one.put("size", "NA");
			one.put("skuNo", "NA");
			one.put("barCode", "NA");
			one.put("dap", "NA");
			one.put("mrp", "NA");
			one.put("cutOffPrice", "NA");
			one.put("description", "NA");
			one.put("code", "NA");
		}
	}

	public String viewLevel2Offering()
	{
		// System.out.println(
		// "viewLevel2Offering()................................................");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from offeringlevel2");
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
				List fieldNames = Configuration.getColomnList("mapped_offering_level2", accountID, connectionSpace, "offering_level2");
				fieldNames.remove("verticalname");

				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("verticalname"))
						{
							if (i < fieldNames.size() - 1) query.append("offeringlevel1.verticalname,");
							else query.append("offeringlevel1.verticalname");
						}
						else
						{
							if (i < fieldNames.size() - 1) query.append("offeringlevel2." + obdata.toString() + ",");
							else query.append("offeringlevel2." + obdata.toString());
						}
					}
					i++;

				}

				query.append(" from offeringlevel2 as " + "offeringlevel2 inner join offeringlevel1 as offeringlevel1 on offeringlevel2.verticalname=offeringlevel1.id"
						+ " where offeringlevel2.verticalname=" + getId());

				if (this.getStatus() != null && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("undefined"))
				{
					query.append(" and offeringlevel2.status='" + this.getStatus() + "'");
				}
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and offeringlevel2." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and offeringlevel2." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and offeringlevel2." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and offeringlevel2." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and offeringlevel2." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel2." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel2." + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "offeringlevel2", connectionSpace);
				// oferring taken by client in configuration
				String offeringLevel = session.get("offeringLevel").toString();
				String[] oLevels = null;

				if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						int offeringId = -1;
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									offeringId = (Integer) obdata[k];
								}
								else if (CommonHelper.getFieldValue(fieldNames.get(k)).equalsIgnoreCase("date"))
								{
									one.put(CommonHelper.getFieldValue(fieldNames.get(k)), DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(obdata[k])));
								}
								else one.put(CommonHelper.getFieldValue(fieldNames.get(k)), CommonHelper.getFieldValue(obdata[k]));
							}
						}
						if (level == 2 && offeringId != -1)
						{
							setLastLevelCost(one, offeringId, level);
						}
						Listhb.add(one);
					}
					setLevel1DataObject(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			e.printStackTrace();
		}
		// System.out.println(
		// "viewLevel2Offering()................................................");
		return SUCCESS;
	}

	public String viewLevel3Offering()
	{
		// System.out.println(
		// "viewLevel3Offering()................................................");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from offeringlevel3");
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
				List fieldNames = Configuration.getColomnList("mapped_offering_level3", accountID, connectionSpace, "offering_level3");
				fieldNames.remove("offeringname");

				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("offeringname"))
						{
							query.append(", ");
							it.remove();
							/*
							 * if (i < fieldNames.size() - 1)
							 * query.append("offeringlevel2.offeringname,"); else
							 * query.append("offeringlevel2.offeringname");
							 */
						}
						else
						{
							if (i < fieldNames.size() - 1) query.append("offeringlevel3." + obdata.toString() + ",");
							else query.append("offeringlevel3." + obdata.toString());
						}
					}
					i++;

				}

				query.append(" from offeringlevel3 as " + "offeringlevel3 inner join offeringlevel2 as offeringlevel2 on offeringlevel3.offeringname=offeringlevel2.id"
						+ " where offeringlevel3.offeringname=" + getId());
				
				if (this.getStatus() != null && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("undefined"))
				{
					query.append(" and offeringlevel3.status='" + this.getStatus() + "'");
				}
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and offeringlevel3." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and offeringlevel3." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and offeringlevel3." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and offeringlevel3." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and offeringlevel3." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel3." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel3." + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "offeringlevel3", connectionSpace);
				// oferring taken by client in configuration
				String offeringLevel = session.get("offeringLevel").toString();
				String[] oLevels = null;

				if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						int offeringId = -1;
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									offeringId = (Integer) obdata[k];
								}
								else if (CommonHelper.getFieldValue(fieldNames.get(k)).equalsIgnoreCase("date"))
								{
									one.put(CommonHelper.getFieldValue(fieldNames.get(k)), DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(obdata[k])));
								}
								else one.put(CommonHelper.getFieldValue(fieldNames.get(k)), CommonHelper.getFieldValue(obdata[k]));
							}
						}
						if (level == 3 && offeringId != -1)
						{
							setLastLevelCost(one, offeringId, level);
						}
						Listhb.add(one);
					}
					setLevel1DataObject(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			e.printStackTrace();
		}
		// System.out.println(
		// "viewLevel3Offering()................................................");
		return SUCCESS;
	}

	public String viewLevel4Offering()
	{
		// System.out.println(
		// "viewLevel4Offering()................................................");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from offeringlevel4");
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
				List fieldNames = Configuration.getColomnList("mapped_offering_level4", accountID, connectionSpace, "offering_level4");
				fieldNames.remove("subofferingname");
				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("subofferingname"))
						{
							query.append(", ");
							it.remove();
							/*
							 * if (i < fieldNames.size() - 1)
							 * query.append("offeringlevel3.subofferingname,"); else
							 * query.append("offeringlevel3.subofferingname");
							 */
						}
						else
						{
							if (i < fieldNames.size() - 1) query.append("offeringlevel4." + obdata.toString() + ",");
							else query.append("offeringlevel4." + obdata.toString());
						}
					}
					i++;

				}

				query.append(" from offeringlevel4 as "
						+ "offeringlevel4 inner join offeringlevel3 as offeringlevel3 on offeringlevel4.subofferingname=offeringlevel3.id"
						+ " where offeringlevel4.subofferingname=" + getId());

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and offeringlevel4." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and offeringlevel4." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and offeringlevel4." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and offeringlevel4." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and offeringlevel4." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel4." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel4." + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "offeringlevel4", connectionSpace);

				// oferring taken by client in configuration
				String offeringLevel = session.get("offeringLevel").toString();
				String[] oLevels = null;

				if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						int offeringId = -1;
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									offeringId = (Integer) obdata[k];
								}
								else if (CommonHelper.getFieldValue(fieldNames.get(k)).equalsIgnoreCase("date"))
								{
									one.put(CommonHelper.getFieldValue(fieldNames.get(k)), DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(obdata[k])));
								}
								else one.put(CommonHelper.getFieldValue(fieldNames.get(k)), CommonHelper.getFieldValue(obdata[k]));
							}
						}
						if (level == 4 && offeringId != -1)
						{
							setLastLevelCost(one, offeringId, level);
						}
						Listhb.add(one);
					}
					setLevel1DataObject(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			e.printStackTrace();
		}
		// System.out.println(
		// "viewLevel4Offering()................................................");
		return SUCCESS;
	}

	public String viewLevel5Offering()
	{
		// System.out.println(
		// "viewLevel5Offering()................................................");
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from offeringlevel5");
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
				List fieldNames = Configuration.getColomnList("mapped_offering_level5", accountID, connectionSpace, "offering_level5");
				fieldNames.remove("variantname");

				List<Object> Listhb = new ArrayList<Object>();
				int i = 0;
				for (Iterator it = fieldNames.iterator(); it.hasNext();)
				{
					// generating the dyanamic query based on selected fields
					Object obdata = (Object) it.next();
					if (obdata != null)
					{
						if (obdata.toString().equalsIgnoreCase("variantname"))
						{
							query.append(", ");
							it.remove();
							/*
							 * if (i < fieldNames.size() - 1)
							 * query.append("offeringlevel4.variantname,"); else
							 * query.append("offeringlevel4.variantname");
							 */
						}
						else
						{
							if (i < fieldNames.size() - 1) query.append("offeringlevel5." + obdata.toString() + ",");
							else query.append("offeringlevel5." + obdata.toString());
						}
					}
					i++;

				}

				query.append(" from offeringlevel5 as " + "offeringlevel5 inner join offeringlevel4 as offeringlevel4 on offeringlevel5.variantname=offeringlevel4.id"
						+ " where offeringlevel5.variantname=" + getId());

				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
				{
					// add search query based on the search operation
					if (getSearchOper().equalsIgnoreCase("eq"))
					{
						query.append(" and offeringlevel5." + getSearchField() + " = '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("cn"))
					{
						query.append(" and offeringlevel5." + getSearchField() + " like '%" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("bw"))
					{
						query.append(" and offeringlevel5." + getSearchField() + " like '" + getSearchString() + "%'");
					}
					else if (getSearchOper().equalsIgnoreCase("ne"))
					{
						query.append(" and offeringlevel5." + getSearchField() + " <> '" + getSearchString() + "'");
					}
					else if (getSearchOper().equalsIgnoreCase("ew"))
					{
						query.append(" and offeringlevel5." + getSearchField() + " like '%" + getSearchString() + "'");
					}

				}

				if (getSord() != null && !getSord().equalsIgnoreCase(""))
				{
					if (getSord().equals("asc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel5." + getSidx());
					}
					if (getSord().equals("desc") && getSidx() != null && !getSidx().equals(""))
					{
						query.append(" order by offeringlevel5." + getSidx() + " DESC");
					}
				}

				query.append(" limit " + from + "," + to);

				/**
				 * **************************checking for colomon change due to
				 * configuration if then alter table
				 */
				cbt.checkTableColmnAndAltertable(fieldNames, "offeringlevel5", connectionSpace);

				// oferring taken by client in configuration
				String offeringLevel = session.get("offeringLevel").toString();
				String[] oLevels = null;

				if (offeringLevel != null && !offeringLevel.equalsIgnoreCase("")) oLevels = offeringLevel.split("#");
				int level = Integer.parseInt(oLevels[0]);

				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						int offeringId = -1;
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
								{
									one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									offeringId = (Integer) obdata[k];
								}
								else if (CommonHelper.getFieldValue(fieldNames.get(k)).equalsIgnoreCase("date"))
								{
									one.put(CommonHelper.getFieldValue(fieldNames.get(k)), DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(obdata[k])));
								}
								else one.put(CommonHelper.getFieldValue(fieldNames.get(k)), CommonHelper.getFieldValue(obdata[k]));
							}
						}
						if (level == 5 && offeringId != -1)
						{
							setLastLevelCost(one, offeringId, level);
						}
						Listhb.add(one);
					}
					setLevel1DataObject(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}

			}
		}
		catch (Exception e)
		{
			addActionMessage("Ooops There Is Some Problem In Organization Registration!");
			e.printStackTrace();
		}
		// System.out.println(
		// "viewLevel5Offering()................................................");
		return SUCCESS;
	}

	public String viewModifyLevel1()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
			// //////System.out.println("getOper"+getOper());
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
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("offeringlevel1", wherClause, condtnBlock, connectionSpace);
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
				cbt.updateTableColomn("offeringlevel1", wherClause,condtnBlock, connectionSpace);
				//cbt.deleteAllRecordForId("offeringlevel1", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewModifyLevel2()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("offeringlevel2", wherClause, condtnBlock, connectionSpace);
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
				cbt.updateTableColomn("offeringlevel2", wherClause,condtnBlock, connectionSpace);
				//cbt.deleteAllRecordForId("offeringlevel2", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewModifyLevel3()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("offeringlevel3", wherClause, condtnBlock, connectionSpace);
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
				cbt.updateTableColomn("offeringlevel3", wherClause,condtnBlock, connectionSpace);
				//cbt.deleteAllRecordForId("offeringlevel3", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewModifyLevel4()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("offeringlevel4", wherClause, condtnBlock, connectionSpace);
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
				cbt.updateTableColomn("offeringlevel4", wherClause,condtnBlock, connectionSpace);
			//	cbt.deleteAllRecordForId("offeringlevel4", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewModifyLevel5()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;
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
							&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid")) wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("offeringlevel5", wherClause, condtnBlock, connectionSpace);
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
				cbt.updateTableColomn("offeringlevel5", wherClause,condtnBlock, connectionSpace);
		//		cbt.deleteAllRecordForId("offeringlevel5", "id", condtIds.toString(), connectionSpace);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 30-5-2013 It is to show tax mapping with offering page
	 */
	public String beforeTaxMapping()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			// Start: create table 'tax_offering_mapping'
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();

			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("tax_name");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("offeringLevelId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			cbt.createTable22("tax_offering_mapping", Tablecolumesaaa, connectionSpace);
			// End: create table 'tax_offering_mapping'

			// Start: Fetch Offering names and client name
			int level = 0;
			String offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			String tableName = "", colName = "";
			if (level == 1)
			{
				tableName = "offeringlevel1";
				colName = "verticalname";
			}
			if (level == 2)
			{
				tableName = "offeringlevel2";
				colName = "offeringname";
			}
			if (level == 3)
			{
				tableName = "offeringlevel3";
				colName = "subofferingname";
			}
			if (level == 4)
			{
				tableName = "offeringlevel4";
				colName = "variantname";
			}
			if (level == 5)
			{
				tableName = "offeringlevel5";
				colName = "subvariantname";
			}

			StringBuffer query = new StringBuffer();
			query.append("select id,");
			query.append(colName);
			query.append(" from ");
			query.append(tableName);

			// ////System.out.println("query:"+query);
			List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			if (data != null)
			{
				for (Iterator it = data.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					for (int k = 0; k <= 1; k++)
					{
						if (obdata != null)
						{
							offeringList.put(obdata[0].toString(), obdata[1].toString());
						}
					}
				}
			}
			// End: Fetch Offering names and client name

		}
		catch (Exception e)
		{
			/*
			 * log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			 * + DateUtil.getCurrentTime() + " " +
			 * "Problem in Over2Cloud  method: addClient of class " + getClass(), e);
			 */
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop, 30-5-2013 Map tax with offering
	 */
	public String addTaxMapping()
	{
		String returnString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			int level = 0;
			String offeringLevel = session.get("offeringLevel").toString();
			String[] oLevels = null;

			if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
			{
				oLevels = offeringLevel.split("#");
				level = Integer.parseInt(oLevels[0]);
			}

			String offeringId = request.getParameter("offeringId").toString();
			String[] taxId = request.getParameterValues("taxId");

			if (offeringId != null && !offeringId.equalsIgnoreCase("-1"))
			{
				// Start: Delete offering mapped with tax (tabel: 'tax_offering_mapped)
				// first in both case UPDATE and INSERT
				StringBuilder condtIds = new StringBuilder();
				condtIds.append(offeringId);
				cbt.deleteAllRecordForId("tax_offering_mapping", "offeringId", condtIds.toString(), connectionSpace);

				// End: Delete offering mapped with tax (tabel: 'tax_offering_mapped)
				// first in both case UPDATE and INSERT

				// Start: Insert new mapping of tax with offering everytime
				if (taxId != null && taxId.length > 0)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					int count = 0;

					ob = new InsertDataTable();
					ob.setColName("offeringId");
					ob.setDataName(offeringId);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("offeringLevelId");
					ob.setDataName(String.valueOf(level));
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

					ob = new InsertDataTable();
					ob.setColName("tax_name");
					insertData.add(ob);

					for (String s : taxId)
					{
						// set different value of tax Id only and store it to table
						ob.setDataName(s);
						boolean statusFlag = cbt.insertIntoTable("tax_offering_mapping", insertData, connectionSpace);
						if (statusFlag) count++;
					}
					// End: Insert new mapping of tax with offering everytime

					if (count > 0)
					{
						addActionMessage(count + " Type of taxes mapped successfully");
						returnString = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
						returnString = SUCCESS;
					}
				}
				else
				{
					addActionMessage("All mapping deleted successfully.");
					returnString = SUCCESS;
				}
			}

		}
		catch (Exception e)
		{
			/*
			 * log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			 * + DateUtil.getCurrentTime() + " " +
			 * "Problem in Over2Cloud  method: addClient of class " + getClass(), e);
			 */
			e.printStackTrace();
			return ERROR;
		}
		return returnString;
	}

	/*
	 * Anoop, 30-5-2013 Fetch tax list in div
	 */
	public String fetchTaxForOffering()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			// Start: Fetch all tax
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuffer query1 = new StringBuffer();
			query1.append("select ct.id, ct.tax_name, tom.id tomId, ct.tax_price  from createtax_table as ct ");
			query1.append(" left join tax_offering_mapping as tom on ct.id = tom.tax_name and tom.offeringId = ");
			query1.append(id); // 'id' is offeringId

			// //////System.out.println("query1: "+query1);
			List data1 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

			if (data1 != null)
			{
				taxList = new ArrayList<List<String>>();
				List<String> listItem = null;
				for (Iterator it = data1.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					if (obdata != null)
					{
						listItem = new ArrayList<String>();
						for (int k = 0; k < obdata.length; k++)
						{
							if (k == 2)
							{
								if (obdata[k] == null) obdata[k] = "false";
								else obdata[k] = "true";
							}
							listItem.add(obdata[k].toString());
						}
						taxList.add(listItem);
					}
				}
			}
			// //////System.out.println("taxList:"+taxList.size());
			// End: Fetch all tax

		}
		catch (Exception e)
		{
			/*
			 * log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			 * + DateUtil.getCurrentTime() + " " +
			 * "Problem in Over2Cloud  method: addClient of class " + getClass(), e);
			 */
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings( { "unchecked", "unused" })
	public String addOfferingLevelCostMapping()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase("")) return LOGIN;

			CommonOperInterface coi = new CommonConFactory().createInterface();
			// Start: create table 'offering_level_cost_mapping'
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();

			ob1.setColumnname("offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			// overwriting same tableColumes reference again and again
			ob1 = new TableColumes();
			ob1.setColumnname("offeringLevelId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("size");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("skuNo");
			ob1.setDatatype("varchar(255)");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("barCode");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("dap");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("mrp");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("cutOffPrice");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("description");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("code");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			coi.createTable22("offering_level_cost_mapping", Tablecolumesaaa, connectionSpace);
			// End: create table 'offering_level_cost_mapping'

			// Start: create table 'offering_level_cost_mapping_history'
			Tablecolumesaaa.clear();

			ob1 = new TableColumes();
			ob1.setColumnname("offering_level_cost_mapping_id");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("offeringId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("offeringLevelId");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("size");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("skuNo");
			ob1.setDatatype("varchar(255)");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("barCode");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("dap");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("mrp");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("cutOffPrice");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("description");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("code");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("userName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);

			coi.createTable22("offering_level_cost_mapping_history", Tablecolumesaaa, connectionSpace);
			// End: create table 'offering_level_cost_mapping_history'

			// Start: Read all parameters from jsp and create list
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			Collections.sort(requestParameterNames);
			Iterator it = requestParameterNames.iterator();
			InsertDataTable ob = null;
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			boolean status = false;
			String tempOfferingId = null;
			String tempOfferingLevelId = null;

			while (it.hasNext())
			{
				String Parmname = it.next().toString();
				String paramValue = request.getParameter(Parmname);
				if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null)
				{
					if (Parmname.equalsIgnoreCase("offeringId"))
					{
						tempOfferingId = paramValue;
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
					else if (Parmname.equalsIgnoreCase("variantname"))
					{
						tempOfferingId = paramValue;
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
					else if (Parmname.equalsIgnoreCase("subofferingname"))
					{
						tempOfferingId = paramValue;
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
					else if (Parmname.equalsIgnoreCase("offeringname"))
					{
						tempOfferingId = paramValue;
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
					else if (Parmname.equalsIgnoreCase("verticalname"))
					{
						tempOfferingId = paramValue;
						ob = new InsertDataTable();
						ob.setColName("offeringId");
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
					else
					{
						if (Parmname.equalsIgnoreCase("offeringLevelId")) tempOfferingLevelId = paramValue;

						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}

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
			// End: Read all parameters from jsp and create list

			// Start: Check whether record exists or not for same offeringId &
			// offeringLevelId, if yes then update it else insert a new record
			if (tempOfferingId != null && tempOfferingLevelId != null)
			{
				Map<String, Object> condList = new HashMap<String, Object>();
				condList.put("offeringId", tempOfferingId);
				condList.put("offeringLevelId", tempOfferingLevelId);

				List<String> colList = new ArrayList<String>();
				colList.add("id");
				colList.add("offeringId");
				colList.add("offeringLevelId");
				colList.add("size");
				colList.add("skuNo");
				colList.add("barCode");
				colList.add("dap");
				colList.add("mrp");
				colList.add("cutOffPrice");
				colList.add("description");
				colList.add("code");
				colList.add("userName");
				colList.add("date");
				colList.add("time");

				List data = coi.viewAllDataEitherSelectOrAll("offering_level_cost_mapping", colList, condList, connectionSpace);
				if (data != null && data.size() > 0)// offeringId & offeringLevelId
				// exists, update record in table
				// 'offering_level_cost_mapping'
				{
					Object[] obj = (Object[]) data.get(0);
					// Start: copy data from table 'offering_level_cost_mapping' and put
					// in table 'offering_level_cost_mapping_history'
					List<InsertDataTable> insertDataToHistoy = new ArrayList<InsertDataTable>();
					InsertDataTable obValue = null;

					obValue = new InsertDataTable();
					obValue.setColName("offering_level_cost_mapping_id");
					obValue.setDataName(obj[0].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("offeringId");
					obValue.setDataName(obj[1].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("offeringLevelId");
					obValue.setDataName(obj[2].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("size");
					obValue.setDataName(obj[3].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("skuNo");
					obValue.setDataName(obj[4].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("barCode");
					obValue.setDataName(obj[5].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("dap");
					obValue.setDataName(obj[6].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("mrp");
					obValue.setDataName(obj[7].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("cutOffPrice");
					obValue.setDataName(obj[8].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("description");
					obValue.setDataName(obj[9].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("code");
					obValue.setDataName(obj[10].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("userName");
					obValue.setDataName(obj[11].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("date");
					obValue.setDataName(obj[12].toString());
					insertDataToHistoy.add(obValue);

					obValue = new InsertDataTable();
					obValue.setColName("time");
					obValue.setDataName(obj[13].toString());
					insertDataToHistoy.add(obValue);

					status = coi.insertIntoTable("offering_level_cost_mapping_history", insertDataToHistoy, connectionSpace);
					if (status)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						for (InsertDataTable idt : insertData)
						{
							wherClause.put(idt.getColName(), idt.getDataName());
						}

						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						condtnBlock.put("id", obj[0].toString());
						status = coi.updateTableColomn("offering_level_cost_mapping", wherClause, condtnBlock, connectionSpace);
						if (status) addActionMessage("Offering updated successfully.");
						else addActionMessage("Error in updating mapping !");
					}
					else addActionMessage("Error in updating mapping !");
					// End: copy data from table 'offering_level_cost_mapping' and put in
					// table 'offering_level_cost_mapping_history'
				}
				else
				// insert a new record in table 'offering_level_cost_mapping'
				{
					status = coi.insertIntoTable("offering_level_cost_mapping", insertData, connectionSpace);
					if (status) addActionMessage("Offering mapped successfully.");
					else addActionMessage("Error in mapping !");
				}

			}
			// End: Check whether record exists or not for same offeringId &
			// offeringLevelId, if yes then update it else insert a new record

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * Anoop 19-8-2013 Show offering upload page
	 */
	public String beforeOfferingUpload()
	{
		String returnType = ERROR;
		if (ValidateSession.checkSession())
		{
			// //System.out.println("111111111111111111111111");
			levelOfOffering = getOfferingLevelForOrganization();

			boolean f1, f2, f3, f4, f5;
			f1 = getallLevel1Offering().equalsIgnoreCase("SUCCESS");
			f2 = getallLevel2Offering().equalsIgnoreCase("SUCCESS");
			f3 = getallLevel3Offering().equalsIgnoreCase("SUCCESS");
			f4 = getallLevel4Offering().equalsIgnoreCase("SUCCESS");
			f5 = getallLevel5Offering().equalsIgnoreCase("SUCCESS");
			if (levelOfOffering == 1 && f1)
			{
				// //System.out.println("--11111111111111111111111111");
				returnType = SUCCESS;
				getOfferingLevelForOrganization();
			}
			else if (levelOfOffering == 2 && f1 && f2)
			{
				// //System.out.println("--222222222222222222222222222");
				returnType = SUCCESS;
				getOfferingLevelForOrganization();
			}
			else if (levelOfOffering == 3 && f1 && f2 && f3)
			{
				// //System.out.println("--33333333333333333333333333333");
				returnType = SUCCESS;
				getOfferingLevelForOrganization();
			}
			else if (levelOfOffering == 4 && f1 && f2 && f3 && f4)
			{
				// //System.out.println("--444444444444444444444444444444");
				returnType = SUCCESS;
				getOfferingLevelForOrganization();
			}
			else if (levelOfOffering == 5 && f1 && f2 && f3 && f4 && f5)
			{
				// //System.out.println("--555555555555555555555555555555555");
				returnType = SUCCESS;
				getOfferingLevelForOrganization();
			}

			/*
			 * if(getallLevel1Offering().equalsIgnoreCase("SUCCESS")
			 * &&getallLevel2Offering().equalsIgnoreCase("SUCCESS") &&
			 * getallLevel3Offering().equalsIgnoreCase("SUCCESS") &&
			 * getallLevel4Offering().equalsIgnoreCase("SUCCESS") &&
			 * getallLevel5Offering().equalsIgnoreCase("SUCCESS")) {
			 * ////System.out.println("777777777777777777777777777777777"); returnType
			 * = SUCCESS; getOfferingLevelForOrganization(); }
			 */
			// //////System.out.println("22222222222222222222222222222:"+returnType);
		}
		else
		{
			returnType = LOGIN;
		}
		return returnType;
	}

	public String beforeOfferingUploadNew()
	{
		System.out.println("22222222222222222222222222222222222");
		if (!ValidateSession.checkSession()) return LOGIN;
		return SUCCESS;
	}
	
	public String uploadOfferingExcelNew()
	{
		System.out.println("8888888888888888888888888888888");
		try
		{
			if (!ValidateSession.checkSession()) return LOGIN;

			int level = Integer.parseInt(new CommonHelper().getOfferingName()[2]);
			ArrayList<ArrayList<String>> list = new OfferingHelper().readOfferingExcel(offering, offeringFileName, offeringContentType, level);
			records = new OfferingHelper().insertOffering(list, connectionSpace);
			addActionMessage(records+" Offering Uploaded Successfully !");
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

	public int getLevelOfOffering()
	{
		return levelOfOffering;
	}

	public void setLevelOfOffering(int levelOfOffering)
	{
		this.levelOfOffering = levelOfOffering;
	}
	public List<ConfigurationUtilBean> getOfferingLevel1TextBox()
	{
		return offeringLevel1TextBox;
	}

	public void setOfferingLevel1TextBox(List<ConfigurationUtilBean> offeringLevel1TextBox)
	{
		this.offeringLevel1TextBox = offeringLevel1TextBox;
	}

	public List<ConfigurationUtilBean> getOfferingLevel2TextBox()
	{
		return offeringLevel2TextBox;
	}

	public void setOfferingLevel2TextBox(List<ConfigurationUtilBean> offeringLevel2TextBox)
	{
		this.offeringLevel2TextBox = offeringLevel2TextBox;
	}

	public List<ConfigurationUtilBean> getOfferingLevel3TextBox()
	{
		return offeringLevel3TextBox;
	}

	public void setOfferingLevel3TextBox(List<ConfigurationUtilBean> offeringLevel3TextBox)
	{
		this.offeringLevel3TextBox = offeringLevel3TextBox;
	}

	public List<ConfigurationUtilBean> getOfferingLevel4TextBox()
	{
		return offeringLevel4TextBox;
	}

	public void setOfferingLevel4TextBox(List<ConfigurationUtilBean> offeringLevel4TextBox)
	{
		this.offeringLevel4TextBox = offeringLevel4TextBox;
	}

	public Map<String, String> getOfferingLevel5TextBox()
	{
		return offeringLevel5TextBox;
	}

	public void setOfferingLevel5TextBox(Map<String, String> offeringLevel5TextBox)
	{
		this.offeringLevel5TextBox = offeringLevel5TextBox;
	}

	public String getOfferingLevel1Name()
	{
		return offeringLevel1Name;
	}

	public void setOfferingLevel1Name(String offeringLevel1Name)
	{
		this.offeringLevel1Name = offeringLevel1Name;
	}

	public String getOfferingLevel2Name()
	{
		return offeringLevel2Name;
	}

	public void setOfferingLevel2Name(String offeringLevel2Name)
	{
		this.offeringLevel2Name = offeringLevel2Name;
	}

	public String getOfferingLevel3Name()
	{
		return offeringLevel3Name;
	}

	public void setOfferingLevel3Name(String offeringLevel3Name)
	{
		this.offeringLevel3Name = offeringLevel3Name;
	}

	public String getOfferingLevel4Name()
	{
		return offeringLevel4Name;
	}

	public void setOfferingLevel4Name(String offeringLevel4Name)
	{
		this.offeringLevel4Name = offeringLevel4Name;
	}

	public String getOfferingLevel5Name()
	{
		return offeringLevel5Name;
	}

	public void setOfferingLevel5Name(String offeringLevel5Name)
	{
		this.offeringLevel5Name = offeringLevel5Name;
	}

	public Map<Integer, String> getOfferingLevel1()
	{
		return offeringLevel1;
	}

	public void setOfferingLevel1(Map<Integer, String> offeringLevel1)
	{
		this.offeringLevel1 = offeringLevel1;
	}

	public Map<Integer, String> getOfferingLevel2()
	{
		return offeringLevel2;
	}

	public void setOfferingLevel2(Map<Integer, String> offeringLevel2)
	{
		this.offeringLevel2 = offeringLevel2;
	}

	public Map<Integer, String> getOfferingLevel3()
	{
		return offeringLevel3;
	}

	public void setOfferingLevel3(Map<Integer, String> offeringLevel3)
	{
		this.offeringLevel3 = offeringLevel3;
	}

	public Map<Integer, String> getOfferingLevel4()
	{
		return offeringLevel4;
	}

	public void setOfferingLevel4(Map<Integer, String> offeringLevel4)
	{
		this.offeringLevel4 = offeringLevel4;
	}

	public String getModifyFlag()
	{
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag)
	{
		this.modifyFlag = modifyFlag;
	}

	public String getDeleteFlag()
	{
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag)
	{
		this.deleteFlag = deleteFlag;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getLevel1ColmnNames()
	{
		return level1ColmnNames;
	}

	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames)
	{
		this.level1ColmnNames = level1ColmnNames;
	}

	public List<GridDataPropertyView> getLevel2ColmnNames()
	{
		return level2ColmnNames;
	}

	public void setLevel2ColmnNames(List<GridDataPropertyView> level2ColmnNames)
	{
		this.level2ColmnNames = level2ColmnNames;
	}

	public List<GridDataPropertyView> getLevel3ColmnNames()
	{
		return level3ColmnNames;
	}

	public void setLevel3ColmnNames(List<GridDataPropertyView> level3ColmnNames)
	{
		this.level3ColmnNames = level3ColmnNames;
	}

	public List<GridDataPropertyView> getLevel4ColmnNames()
	{
		return level4ColmnNames;
	}

	public void setLevel4ColmnNames(List<GridDataPropertyView> level4ColmnNames)
	{
		this.level4ColmnNames = level4ColmnNames;
	}

	public List<GridDataPropertyView> getLevel5ColmnNames()
	{
		return level5ColmnNames;
	}

	public void setLevel5ColmnNames(List<GridDataPropertyView> level5ColmnNames)
	{
		this.level5ColmnNames = level5ColmnNames;
	}

	public List<Object> getLevel1DataObject()
	{
		return level1DataObject;
	}

	public void setLevel1DataObject(List<Object> level1DataObject)
	{
		this.level1DataObject = level1DataObject;
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

	public Map<String, Object> getOfferingList()
	{
		return offeringList;
	}

	public void setOfferingList(Map<String, Object> offeringList)
	{
		this.offeringList = offeringList;
	}

	public List<List<String>> getTaxList()
	{
		return taxList;
	}

	public void setTaxList(List<List<String>> taxList)
	{
		this.taxList = taxList;
	}

	public String getLastLevelOffering()
	{
		return lastLevelOffering;
	}

	public void setLastLevelOffering(String lastLevelOffering)
	{
		this.lastLevelOffering = lastLevelOffering;
	}

	public Map<Integer, String> getOfferingLevel5()
	{
		return offeringLevel5;
	}

	public void setOfferingLevel5(Map<Integer, String> offeringLevel5)
	{
		this.offeringLevel5 = offeringLevel5;
	}

	public void setOfferingFileName(String offeringFileName)
	{
		this.offeringFileName = offeringFileName;
	}

	public String getOfferingFileName()
	{
		return offeringFileName;
	}

	public void setOfferingContentType(String offeringContentType)
	{
		this.offeringContentType = offeringContentType;
	}

	public String getOfferingContentType()
	{
		return offeringContentType;
	}

	public void setOffering(InputStream offering)
	{
		this.offering = offering;
	}

	public InputStream getOffering()
	{
		return offering;
	}
	public Integer getCountVertical() {
		return countVertical;
	}

	public void setCountVertical(Integer countVertical) {
		this.countVertical = countVertical;
	}

	public Integer getCountOffering() {
		return countOffering;
	}

	public void setCountOffering(Integer countOffering) {
		this.countOffering = countOffering;
	}

	public Integer getCountSubOffering() {
		return countSubOffering;
	}

	public void setCountSubOffering(Integer countSubOffering) {
		this.countSubOffering = countSubOffering;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
