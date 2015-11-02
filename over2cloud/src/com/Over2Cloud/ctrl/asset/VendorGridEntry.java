package com.Over2Cloud.ctrl.asset;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VendorGridEntry extends ActionSupport implements ServletRequestAware
{
	Map session = ActionContext.getContext().getSession();

	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String userName = (String) session.get("uName");
	
	private String mainHeaderName;
	private String modifyFlag;
	private String deleteFlag;
	private List<GridDataPropertyView> masterViewProp = new ArrayList<GridDataPropertyView>();
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
	private HttpServletRequest request;

	public String beforeVendorView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if (getModifyFlag().equalsIgnoreCase("0"))
					setModifyFlag("false");
				else
					setModifyFlag("true");
				if (getDeleteFlag().equalsIgnoreCase("0"))
					setDeleteFlag("false");
				else
					setDeleteFlag("true");

				if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("false"))
					setMainHeaderName("Vendor >> View");
				else if (getModifyFlag().equalsIgnoreCase("true") && getDeleteFlag().equalsIgnoreCase("false"))
					setMainHeaderName("Vendor>> Modify");
				else if (getModifyFlag().equalsIgnoreCase("false") && getDeleteFlag().equalsIgnoreCase("true"))
					setMainHeaderName("Vendor >> Delete");

				setVendorMasterViewProp();
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

	public void setVendorMasterViewProp()
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);

		List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_vendor_master", accountID, connectionSpace, "", 0, "table_name", "vendor_master");
		if (statusColName != null && statusColName.size() > 0)
		{
			for (GridDataPropertyView gdp : statusColName)
			{
				gpv = new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				masterViewProp.add(gpv);
			}
		}
	}

	public String viewVendor()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from createvendor_master where status='Active'");
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
					if (to > getRecords())
						to = getRecords();

					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("SELECT ");
					List fieldNames = Configuration.getColomnList("mapped_vendor_master", accountID, connectionSpace, "vendor_master");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dyanamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if(obdata.toString().equalsIgnoreCase("vendorfor"))
									{
										query.append("vendortype.vendor_for,");
									}
									else
									{
										query.append("vendor."+obdata.toString() + ",");
									}
								}
								else
								{
									query.append("vendor."+obdata.toString());
								}
							}
							i++;

						}
					}
					query.append(" FROM createvendor_master AS vendor ");
					query.append(" INNER JOIN vendor_type_detail AS vendortype ON vendortype.id=vendor.vendorfor");
					query.append(" WHERE vendor.status='Active'");
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						// add search query based on the search operation
						query.append(" and ");
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
					query.append(" ORDER BY vendor.vendorname ");
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						List<Object> tempList = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> tempMap = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									if (k == 0)
										tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									else
										tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
								}
								else
								{
									tempMap.put(fieldNames.get(k).toString(), "NA");
								}
							}
							tempList.add(tempMap);
						}
						setMasterViewList(tempList);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}

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

	@SuppressWarnings("unchecked")
	public String modifyVendor()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
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
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equalsIgnoreCase("userName"))
							{
								wherClause.put(parmName, userName);
							}
							else if (parmName.equalsIgnoreCase("date"))
							{
								wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
							}
							else if (parmName.equalsIgnoreCase("time"))
							{
								wherClause.put(parmName, DateUtil.getCurrentTime());
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("createvendor_master", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					for (String H : tempIds)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						wherClause.put("status", "DActive");
						condtnBlock.put("id", H);
						cbt.updateTableColomn("createvendor_master", wherClause, condtnBlock, connectionSpace);
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

	public String beforeVendorTypeView()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				GridDataPropertyView gpv = new GridDataPropertyView();
				masterViewProp = new ArrayList<GridDataPropertyView>();
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_vendor_type_config", accountID, connectionSpace, "", 0, "table_name", "vendor_type_config");
				if (statusColName != null && statusColName.size() > 0)
				{
					for (GridDataPropertyView gdp : statusColName)
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
				else
				{
					addActionMessage("Vendor Type Table Is Not properly Configured !");
				}
				return SUCCESS;
			}
			catch(Exception e)
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
	public String viewVendorType()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList("mapped_vendor_type_config", accountID, connectionSpace, "vendor_type_config");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (Iterator it = fieldNames.iterator(); it.hasNext();)
					{
						// generating the dyanamic query based on selected
						// fields
						Object obdata = (Object) it.next();
						if (obdata != null)
						{
							if (i < fieldNames.size() - 1)
								query.append(obdata.toString() + ",");
							else
								query.append(obdata.toString());
						}
						i++;

					}
				}
				query.append(" from vendor_type_detail where status='Active'");
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
				query.append(" ORDER BY vendor_for ");
				System.out.println(query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (data != null && data.size() > 0)
				{
					/*BigInteger count = new BigInteger("3");
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();*/
					
					List<Object> tempList = new ArrayList<Object>();
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						Map<String, Object> tempMap = new HashMap<String, Object>();
						for (int k = 0; k < fieldNames.size(); k++)
						{
							if (obdata[k] != null)
							{
								if (k == 0)
									tempMap.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
								else
									tempMap.put(fieldNames.get(k).toString(), obdata[k].toString());
							}
							else
							{
								tempMap.put(fieldNames.get(k).toString(), "NA");
							}
						}
						tempList.add(tempMap);
					}
					setMasterViewList(tempList);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	public String modifyVendorTypeGrid()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
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
						String parmName = it.next().toString();
						String paramValue = request.getParameter(parmName);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && parmName != null && !parmName.equalsIgnoreCase("") && !parmName.equalsIgnoreCase("id") && !parmName.equalsIgnoreCase("oper") && !parmName.equalsIgnoreCase("rowid"))
						{
							if (parmName.equalsIgnoreCase("userName"))
							{
								wherClause.put(parmName, userName);
							}
							else if (parmName.equalsIgnoreCase("date"))
							{
								wherClause.put(parmName, DateUtil.getCurrentDateUSFormat());
							}
							else if (parmName.equalsIgnoreCase("time"))
							{
								wherClause.put(parmName, DateUtil.getCurrentTime());
							}
							else
							{
								wherClause.put(parmName, paramValue);
							}
						}
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("vendor_type_detail", wherClause, condtnBlock, connectionSpace);
				}
				else if (getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					String tempIds[] = getId().split(",");
					for (String H : tempIds)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						Map<String, Object> condtnBlock = new HashMap<String, Object>();
						wherClause.put("status", "DActive");
						condtnBlock.put("id", H);
						cbt.updateTableColomn("vendor_type_detail", wherClause, condtnBlock, connectionSpace);
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

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

}
