package com.Over2Cloud.ctrl.asset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VendorAction extends ActionSupport implements ServletRequestAware
{
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private HttpServletRequest request;
	private List<ConfigurationUtilBean> vendorColumnMap = null;
	private String pageFor;
	private Map<String,String> vendorType;

	public String execute()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
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

	// set data for create vendor add page
	@SuppressWarnings("unchecked")
	public String setAddPageDataFields()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("Page For "+pageFor);
				String accountID = (String) session.get("accountid");
				List<GridDataPropertyView> vendorColumnList=null;
				vendorType=new LinkedHashMap<String, String>();
				System.out.println(pageFor);
				if(pageFor!=null && pageFor.equalsIgnoreCase("Vendor"))
				{
					System.out.println("dsvndsnbndf");
					vendorColumnList = Configuration.getConfigurationData("mapped_vendor_master", accountID, connectionSpace, "", 0, "table_name", "vendor_master");
				}
				else if(pageFor!=null && pageFor.equalsIgnoreCase("VendorType"))
				{
					System.out.println("dfxbmx b,m cv");
					vendorColumnList = Configuration.getConfigurationData("mapped_vendor_type_config", accountID, connectionSpace, "", 0, "table_name", "vendor_type_config");
				}
				System.out.println("vendorColumnList "+vendorColumnList.size());
				vendorColumnMap = new ArrayList<ConfigurationUtilBean>();
				ConfigurationUtilBean obj;
				if (vendorColumnList != null && vendorColumnList.size() > 0)
				{
					for (GridDataPropertyView gdp : vendorColumnList)
					{
						obj=new ConfigurationUtilBean();
						if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("status"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							obj.setEditable(true);
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							vendorColumnMap.add(obj);
						}
						else if (gdp.getColType().trim().equalsIgnoreCase("D") && gdp.getColomnName().equalsIgnoreCase("vendorfor"))
						{
							obj.setKey(gdp.getColomnName());
							obj.setValue(gdp.getHeadingName());
							obj.setValidationType(gdp.getValidationType());
							obj.setColType(gdp.getColType());
							obj.setEditable(true);
							if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
							else
							{
								obj.setMandatory(false);
							}
							vendorColumnMap.add(obj);
						}
					}
					List dataList=new createTable().executeAllSelectQuery("SELECT id,vendor_for FROM vendor_type_detail WHERE status='Active' ORDER BY vendor_for", connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object!=null)
							{
								vendorType.put(object[0].toString(), object[1].toString());
							}
						}
					}
					if(pageFor!=null && pageFor.equalsIgnoreCase("Vendor"))
					{
						returnResult="Vendor";
					}
					else if(pageFor!=null && pageFor.equalsIgnoreCase("VendorType"))
					{
						returnResult="VendorType";
					}

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String vendorAddMaster()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_vendor_master", accountID, connectionSpace, "", 0, "table_name", "vendor_master");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
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
					}
					cbt.createTable22("createvendor_master", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null)
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
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
					
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
					
					
					status = cbt.insertIntoTable("createvendor_master", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("Vendor Registered Successfully!!!");
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
					}
				}
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String vendorTypeAddMaster()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_vendor_type_config", accountID, connectionSpace, "", 0, "table_name", "vendor_type_config");
				if (statusColName != null && statusColName.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					boolean userTrue = false;
					boolean dateTrue = false;
					boolean timeTrue = false;
					for (GridDataPropertyView gdp : statusColName)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
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
					}
					cbt.createTable22("vendor_type_detail", tableColume, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null)
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
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
					
					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Active");
					insertData.add(ob);
					
					
					status = cbt.insertIntoTable("vendor_type_detail", insertData, connectionSpace);
					if (status)
					{
						addActionMessage("Vendor Type Registered Successfully!!!");
						returnResult = SUCCESS;
					}
					else
					{
						addActionMessage("Oops There is some error in data!");
					}
				}
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
	

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public List<ConfigurationUtilBean> getVendorColumnMap()
	{
		return vendorColumnMap;
	}

	public void setVendorColumnMap(List<ConfigurationUtilBean> vendorColumnMap)
	{
		this.vendorColumnMap = vendorColumnMap;
	}

	public String getPageFor()
	{
		return pageFor;
	}

	public void setPageFor(String pageFor)
	{
		this.pageFor = pageFor;
	}

	public Map<String, String> getVendorType()
	{
		return vendorType;
	}

	public void setVendorType(Map<String, String> vendorType)
	{
		this.vendorType = vendorType;
	}

}
