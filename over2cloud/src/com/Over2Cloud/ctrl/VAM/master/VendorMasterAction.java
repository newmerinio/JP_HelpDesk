package com.Over2Cloud.ctrl.VAM.master;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VendorMasterAction extends ActionSupport implements ServletRequestAware
{
	/**
	 * For Vendor details.
	 */
  private static final long serialVersionUID = 1L;
  static final Log log = LogFactory.getLog(VAMMasterActionControl.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName = (String) session.get("uName");
	private String accountID = (String) session.get("accountid");
	private SessionFactory connectionSpace = (SessionFactory) session
	    .get("connectionSpace");
	HttpServletRequest request;
	public String mainHeaderName;
	private List<Object> vendorData;
	
	private LinkedList<ConfigurationUtilBean> vendorfields = null;
	private List<GridDataPropertyView> viewvendorgridcolumn = new ArrayList<GridDataPropertyView>();
	
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
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	
	public String beforeVendorTypeAdd()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
			{
				setMainHeaderName("Vendor Type");
				setGateMasterViewProp("mapped_vender_type_configuration", "vender_type_configuration");
				
			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <vender_type_configuration()> of class "
				    + getClass(), e);
				e.printStackTrace();
			}
			return SUCCESS;
	}
	public void setGateMasterViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewvendorgridcolumn.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewvendorgridcolumn.add(gpv);
		}
	}
	
	
	public String getVendorDetails()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from vendor_admin_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			    connectionSpace);
			if (dataCount != null)
			{
				/*BigInteger count = new BigInteger("3");
				for (Iterator it = dataCount.iterator(); it.hasNext();)
				{
					Object obdata = (Object) it.next();
					count = (BigInteger) obdata;

				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();*/
				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_vender_type_configuration", accountID, connectionSpace,
				    "vender_type_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{
						String object = iterator.next().toString();
						if (object != null)
						{
							if (i < fieldNames.size() - 1)
								query.append(object.toString() + ",");
							else
								query.append(object.toString());
						}
						i++;
					}
					query.append(" from vendor_admin_details");
					System.out.println("Querry>test>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
					    && !getSearchField().equalsIgnoreCase("")
					    && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '"
							    + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString()
							    + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%"
							    + getSearchString() + "'");
						}

					}

					if (getSord() != null && !getSord().equalsIgnoreCase(""))
					{
						if (getSord().equals("asc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx());
						}
						if (getSord().equals("desc") && getSidx() != null
						    && !getSidx().equals(""))
						{
							query.append(" order by " + getSidx() + " DESC");
						}
					}

					//query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "vendor_admin_details",
					    connectionSpace);

					System.out.println("Querry Vendor>>" + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(),
					    connectionSpace);
					List<Object> listhb = new ArrayList<Object>();
					if (data != null && !data.isEmpty())
					{

						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] obdata = (Object[]) iterator.next();

							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{

									if (fieldNames.get(k).toString()
									    .equalsIgnoreCase("orgnztnlevel"))
									{
										// need to work here for viewing the organizaion name
										// instead the id
										String orgName = null;
										if (obdata[k].toString().equalsIgnoreCase("2"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("3"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("4"))
										{
										} else if (obdata[k].toString().equalsIgnoreCase("5"))
										{
										}
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									} else
									{
										if (k == 0)
										{
											one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
										} else
										{
											if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("date"))
											{
												one.put(fieldNames.get(k).toString(), DateUtil
												    .convertDateToIndianFormat(obdata[k].toString()));
											} else if (fieldNames.get(k) != null
											    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("time"))
											{
												one.put(fieldNames.get(k).toString(),
												    (obdata[k].toString().substring(0, 5)));
											} else
											{
												one.put(fieldNames.get(k).toString(),
												    DateUtil.makeTitle(obdata[k].toString()));
											}
										}
									}

								}
							}
							listhb.add(one);
						}
						setVendorData(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getVendorDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	
	}
	public String modifyVendorGrid()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if (getOper().equalsIgnoreCase("edit"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				List<String> requestParameterNames = Collections
				    .list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("")
					    && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
						wherClause.put(Parmname, paramValue);
				}
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("vendor_admin_details", wherClause,
				    condtnBlock, connectionSpace);
			} else if (getOper().equalsIgnoreCase("del"))
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String tempIds[] = getId().split(",");
				StringBuilder condtIds = new StringBuilder();
				int i = 1;
				for (String H : tempIds)
				{
					if (i < tempIds.length)
						condtIds.append(H + " ,");
					else
						condtIds.append(H);
					i++;
				}
				cbt.deleteAllRecordForId("vendor_admin_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method modifyVendorGrid of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String addVendor()
	{
		String resString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			vendorfields = new LinkedList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_vender_type_configuration", accountID,
			    connectionSpace, "", 0, "table_name",
			    "vender_type_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("D")
					    && !gdp.getColomnName().equalsIgnoreCase("userName")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						//deptDropDown.add(obj);
					}
					if (!gdp.getColType().trim().equalsIgnoreCase("Date")
					    && !gdp.getColType().trim().equalsIgnoreCase("Time")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						// visitordatetimelist.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time"))
					{
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						} else
						{
							obj.setMandatory(false);
						}
						vendorfields.add(obj);
					}
				}
				resString = SUCCESS;
			}

		} catch (Exception e)
		{
			addActionError("There Is Problem to Add addVendor!");
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <addVendor> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	public String submitVendorDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> emplocgatemapData = Configuration
				    .getConfigurationData("mapped_vender_type_configuration",
				        accountID, connectionSpace, "", 0, "table_name",
				        "vender_type_configuration");

				if (emplocgatemapData != null && emplocgatemapData.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : emplocgatemapData)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						System.out.println("gdp.getColomnName()>>" + gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("vendor_admin_details", tableColume,
					    connectionSpace);
					List<String> requestParameterNames = Collections
					    .list((Enumeration<String>) request.getParameterNames());
					System.out.println("requestParameterNames:====="
					    + requestParameterNames.size());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (paramValue != null && !paramValue.equalsIgnoreCase("")
							    && parmName != null)
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
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

					System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("vendor_admin_details", insertData,
					    connectionSpace);
					if (status)
					{
						addActionMessage("Vendor Type Saved Successfully!!!");
						returnResult = SUCCESS;
					}
				} else
				{
					addActionMessage("There is some error in data insertion!!!!");
				}
			} catch (Exception e)
			{
				log.error(
				    "Date : "
				        + DateUtil.getCurrentDateIndianFormat()
				        + " Time: "
				        + DateUtil.getCurrentTime()
				        + " "
				        + "Problem in Over2Cloud  method <submitVendorDetail> of class "
				        + getClass(), e);
				returnResult = ERROR;
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	
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
	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;

	}
	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	public String getMainHeaderName()
	{
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}
	public List<Object> getVendorData()
	{
		return vendorData;
	}
	public void setVendorData(List<Object> vendorData)
	{
		this.vendorData = vendorData;
	}
	public List<GridDataPropertyView> getViewvendorgridcolumn()
	{
		return viewvendorgridcolumn;
	}
	public void setViewvendorgridcolumn(
	    List<GridDataPropertyView> viewvendorgridcolumn)
	{
		this.viewvendorgridcolumn = viewvendorgridcolumn;
	}
	public LinkedList<ConfigurationUtilBean> getVendorfields()
	{
		return vendorfields;
	}
	public void setVendorfields(LinkedList<ConfigurationUtilBean> vendorfields)
	{
		this.vendorfields = vendorfields;
	}
	
}
