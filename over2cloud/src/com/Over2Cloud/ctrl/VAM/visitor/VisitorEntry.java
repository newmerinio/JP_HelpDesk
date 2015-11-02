package com.Over2Cloud.ctrl.VAM.visitor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.objectweb.asm.xwork.tree.TryCatchBlockNode;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.Over2Cloud.ctrl.VAM.master.VAMMasterActionControl;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTable;
import com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class VisitorEntry extends ActionSupport implements ServletRequestAware{
	static final Log log = LogFactory.getLog(VAMMasterActionControl.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	public String mainHeaderName;
	HttpServletRequest request;
	private String vendortype;
	private String vendorTypeExist;
	private String companyname;
	private String compNameExist;
	private String deptname;
	private String vndrname = null;
	private String deptNameExit;
	private String empNameExist;
	private String empName;
	private String emplocgatemap;
	private String emp_mail;
	private String emp_sms;
	private String vendor_sms;
	private String visitor_name;
	private String visitor_mobile; 
	private String vendor_mail;
	private String vender_name;
	private String vendorNameExist;
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private String seriesfor;
	private String seriesForExist;
	private String locationName;
	private String locationNameExit;
	private String statusParameter;
	private String gate;
	private String location;
	private String gateNameExist;
	private String prefixstring;
	private List<Object> acknowledgepostdata;
	private List<Object> idseriesData;
	private String venderdatalist = null;
	private Boolean mail;
	private Boolean sms;
	private String dept = null;
	private BufferedReader readfilename;
	String alerttime = null;
	Map<Integer, String> employeelist = new HashMap<Integer, String>();
	private String purposeaddition;
	private String sr_number;
	private LinkedList<ConfigurationUtilBean> visitorfields = null;
	private String purposeExist;
	private String visitdateExist;
	private String visit_date;
	private String visittimeExist;
	private String time_in;
	private String datevalue;
	private String search_parameter;
	private String timevalue;
	private String selectedId;
	private String purposeNameExist;
	private String purpose=null;
	private String deptNameExist;
	private String deptName;
	private List<ConfigurationUtilBean> visitordatetimelist = null;
	private List<ConfigurationUtilBean> deptDropDown = null;
	Map<Integer, String> purposelist = new HashMap<Integer, String>();
	private List<Object> visitorData;
	private String from_date;
	private String to_date;
	private List<GridDataPropertyView> viewVisitorDetail = new ArrayList<GridDataPropertyView>();
	Map<Integer, String> purposeListPreReqest = new HashMap<Integer, String>();
	private String vpassId = null;
    private List<ConfigurationUtilBean> frontoffice = null;
	private List<ConfigurationUtilBean> locationgatelist = null;
	private List<ConfigurationUtilBean> idseriesfieldslist = null;
	private List<ConfigurationUtilBean> idseriesDropDown = null;
	Map<Integer, String> vendortypelist = new HashMap<Integer, String>();
	Map<Integer, String> vendorcompnamelist = new HashMap<Integer, String>();
	Map<Integer, String> departmentlist = new HashMap<Integer, String>();
	Map<Integer, String> locationlist = new HashMap<Integer, String>(); 
	Map<Integer, String> vendordatalist = new HashMap<Integer, String>();
	Map<Integer, String> vendorcompdatalist = new HashMap<Integer, String>();
	Map<Integer, String> locationList = new HashMap<Integer, String>();
	Map<Integer, String> gatenamelist = new HashMap<Integer, String>();
	String numberseries = null;
	private List<GridDataPropertyView> viewacknowledgepostDetail = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewidseriescolumn = new ArrayList<GridDataPropertyView>();
	
	public Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	public Integer page = 0;
	// sorting order - asc or desc
	public String sord = "";
	// get index row - i.e. user click to sort.
	public String sidx = "";
	// Search Field
	public String searchField = "";
	// The Search String
	public String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	public String searchOper = "";
	// Your Total Pages
	public Integer total = 0;
	// All Record
	public Integer records = 0;
	private boolean loadonce = true;
	// Grid colomn view
	public String oper;
	public String id;
	private String countTotal=null;
	private String countIn=null;
	private String countOut=null;
	private String condition=null;
	private String column=null;
	
	
   public String execute()
	{
		return SUCCESS;
	}
	
	public String beforeacknowledgePostAdd()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
			{
				setMainHeaderName("Post");
				setAckowledgePostViewProp("mapped_acknowledge_post_configuration", "acknowledge_post_configuration");
				
			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <beforeacknowledgePostAdd()> of class "
				    + getClass(), e);
				e.printStackTrace();
			}
			return SUCCESS;
	}
	public void setAckowledgePostViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewacknowledgepostDetail.add(gpv);

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
			viewacknowledgepostDetail.add(gpv);
		}
	}
	
	public String getacknowledgePostData()
	{
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from acknowledge_post_details");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),
			    connectionSpace);
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
				if (to > getRecords())
					to = getRecords();

				// getting the list of colmuns
				StringBuilder query = new StringBuilder("");
				query.append("select ");
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from acknowledge_post_details as apd ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_acknowledge_post_configuration", accountID, connectionSpace,
				    "acknowledge_post_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{

						String object = iterator.next().toString();
						if (object != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (object.toString().equalsIgnoreCase("vender_type"))
								{
									query.append(" vad.vender_type, ");
									queryEnd
									    .append(" left join vendor_admin_details as vad on apd.vender_type = vad.id ");
								}else
								if (object.toString().equalsIgnoreCase("dept"))
								{
									query.append(" dpt.deptName, ");
									queryEnd
									    .append(" left join department as dpt on apd.dept = dpt.id ");
								}else
								if (object.toString().equalsIgnoreCase("empName"))
								{
									query.append(" empb.empName, ");
									queryEnd
									    .append(" left join employee_basic as empb on apd.empName = empb.id ");
								}
								else
								{
									query.append("apd." + object.toString() + ",");
								}
							}
							else
								query.append("apd."+object.toString());
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					//System.out.println("Querry>acknowledgePost>>" + query.toString());
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

					query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "acknowledge_post_details",
					    connectionSpace);
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
						setAcknowledgepostdata(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getacknowledgePostData> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	
	}
	
	public String modifyAcknowledgePostGrid()
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
				cbt.updateTableColomn("acknowledge_post_details", wherClause,
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
				cbt.deleteAllRecordForId("acknowledge_post_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method modifyAcknowledgePostGrid of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	
	}
	public String acknowledgePostAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			frontoffice = new ArrayList<ConfigurationUtilBean>();
			locationgatelist = new ArrayList<ConfigurationUtilBean>();
			vendortypelist = new HashMap<Integer, String>();
			vendortypelist = CommonMethod.allVendor(connectionSpace);
			vendorcompnamelist = new HashMap<Integer, String>();
			vendorcompnamelist = CommonMethod.allVendorCompany(connectionSpace);
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			//vedordropDownList = new ArrayList<ConfigurationUtilBean>();
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_acknowledge_post_configuration", accountID, connectionSpace, "",
			    0, "table_name", "acknowledge_post_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("purpose")
					    && !gdp.getColomnName().equalsIgnoreCase("vndr_representv")
					    && !gdp.getColomnName().equalsIgnoreCase("act_mangr"))
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

						frontoffice.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName().equalsIgnoreCase("userName"))
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
						locationgatelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("vender_type"))
					{
						vendortype = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vendorTypeExist = "true";
						else
							vendorTypeExist = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("vendor_comp_name"))
					{
						companyname = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							compNameExist = "true";
						else
							compNameExist = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("dept"))
					{
						deptname = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExit = "true";
						else
							deptNameExit = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("empName"))
					{
						empName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							empNameExist = "true";
						else
							empNameExist = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("vender_name"))
					{
						vender_name = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							vendorNameExist = "true";
						else
							vendorNameExist = "false";
					}
				}
			}
		} catch (Exception e)
		{
			addActionError("Ooops There Is Problem to Add Acknowledge Post!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <acknowledgePostAdd> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getVendorNameDetails()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			vendordatalist = new HashMap<Integer, String>();
			{
				vendordatalist = CommonMethod.vendorNameList(connectionSpace, id);
			} 
			if (vendordatalist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : vendordatalist.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to Load Employee!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getVendorNameDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	
	}
	public String getVendorCompNameDetails()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			vendorcompdatalist = new HashMap<Integer, String>();
			{
				vendorcompdatalist = CommonMethod.vendorCompNameList(connectionSpace, id);
			} 
			if (vendorcompdatalist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : vendorcompdatalist.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to Load Employee!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getVendorCompNameDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}
	public String getVendorData()
	{
		String resString = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				resString = LOGIN;
			}
			if(getVndrname()!=null && !getVndrname().equals("")){ 
				venderdatalist = CommonMethod.getVendorData(connectionSpace, getVndrname());
				resString = SUCCESS;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resString;
	}
	public String submitAcknowledgePostDetail()
	{
		String returnResult = ERROR;
		String[] loc_gate = null;
		String locatn = null;
		String gatename = null;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> purposeData = Configuration
				    .getConfigurationData("mapped_acknowledge_post_configuration",
				        accountID, connectionSpace, "", 0, "table_name",
				        "acknowledge_post_configuration");

				if (purposeData != null && purposeData.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : purposeData)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("acknowledge_post_details", tableColume,
					    connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if(parmName.equals("empName"))
							{
								String locgate = CommonMethod.locationGateForEmp(connectionSpace,paramValue);
								if(locgate != null)
								{
									loc_gate = locgate.split("#"); 
									locatn = loc_gate[0];
									gatename = loc_gate[1];
								}
							}
							if (paramValue != null && !paramValue.equalsIgnoreCase("")
							    && parmName != null && parmName != null && !parmName.endsWith("sms")
									&& !parmName.endsWith("mail"))
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
					ob.setColName("location");
					ob.setDataName(locatn);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("gate");
					ob.setDataName(gatename);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateIndianFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					//System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("acknowledge_post_details", insertData,
					    connectionSpace);
					if (status)
					{
						addActionMessage("Data Saved Successfully.");
						returnResult = SUCCESS;
					}
				} else
				{
					addActionMessage("There is some error in data Insertion.");
				}
			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <submitAcknowledgePostDetail> of class "
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
	
	// method for showing grid with column name of id series.
	public String beforeIdSeriesAdd()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
			{
				setMainHeaderName("ID Series");
				setIdSeriesViewProp("mapped_vam_idseries_configuration", "vam_idseries_configuration");
				
			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <beforeIdSeriesAdd()> of class "
				    + getClass(), e);
				e.printStackTrace();
			}
			return SUCCESS;
	
	} 
	public void setIdSeriesViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewidseriescolumn.add(gpv);

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
			viewidseriescolumn.add(gpv);
		}
	}
	// method for showing idseries data.
	public String getIDSeriesData()
	{
		String resString = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from vam_idseries_details");
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
				StringBuilder queryEnd = new StringBuilder("");
				queryEnd.append(" from vam_idseries_details as vid ");
				List fieldNames = Configuration.getColomnList(
				    "mapped_vam_idseries_configuration", accountID, connectionSpace,
				    "vam_idseries_configuration");
				int i = 0;
				if (fieldNames != null && !fieldNames.isEmpty())
				{
					for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
					{

						String object = iterator.next().toString();
						if (object != null)
						{
							if (i < fieldNames.size() - 1)
							{
								if (object.toString().equalsIgnoreCase("location"))
								{
									query.append(" loc.name, ");
									queryEnd
									    .append(" left join location as loc on vid.location = loc.id ");
								}else
								if (object.toString().equalsIgnoreCase("gate"))
								{
									query.append(" gld.gate, ");
									queryEnd
									    .append(" left join gate_location_details as gld on vid.gate = gld.id  ");
								}
								else
								{
									query.append("vid." + object.toString() + ",");
								}
							}
							else
								query.append("vid."+object.toString());
						}
						i++;
					}
					query.append(" " + queryEnd.toString());
					//System.out.println("Querry>getIDSeriesData>>" + query.toString());
					if (getSearchField() != null && getSearchString() != null
					    && !getSearchField().equalsIgnoreCase("")
					    && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
                        {
                            if(getSearchField().equals("location"))
                            {
                                query.append(" loc.name = '" + getSearchString()
                                        + "'");
                            }
                            else if(getSearchField().equals("series"))
                            {
                                query.append(" vid."+getSearchField() + " = '" + getSearchString()
                                        + "'");   
                            }
                            else{
                            query.append(" " + getSearchField() + " = '" + getSearchString()
                                + "'");
                            }
                        }else if (getSearchOper().equalsIgnoreCase("cn"))
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

				//	query.append(" limit " + from + "," + to);

					/**
					 * **************************checking for colomn change due to
					 * configuration if then alter table
					 */
					cbt.checkTableColmnAndAltertable(fieldNames, "vam_idseries_details",
					    connectionSpace);

					//System.out.println("Query getIDSeriesData>" + query.toString());
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
						setIdseriesData(listhb);
						setTotal((int) Math
						    .ceil((double) getRecords() / (double) getRows()));
						resString = SUCCESS;
					}
				}
			}

		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getIDSeriesData> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
		}
	//method idseries record.
	public String modifyIDSeriesGrid()
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
						  if(Parmname.equals("location"))
	                        {
	                            String locid=CommonMethod.getLocationId(connectionSpace, paramValue);
	                            wherClause.put(Parmname, locid);
	                        }
	                        else{
	                            wherClause.put(Parmname, paramValue);
	                        }	
				}
				 if(wherClause.containsKey("oper"));
                 {
                     wherClause.remove("oper");
                 }

				condtnBlock.put("id", getId());
				cbt.updateTableColomn("vam_idseries_details", wherClause,
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
				cbt.deleteAllRecordForId("vam_idseries_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method modifyIDSeriesGrid of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//Add idseries fields on form.
	public String idSeriesAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			idseriesfieldslist = new ArrayList<ConfigurationUtilBean>();
			idseriesDropDown = new ArrayList<ConfigurationUtilBean>();
			locationList = new HashMap<Integer, String>();
			locationList = CommonMethod.allLocationList(connectionSpace);
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_vam_idseries_configuration", accountID, connectionSpace, "",
			    0, "table_name", "vam_idseries_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("series"))
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

						idseriesfieldslist.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName().equalsIgnoreCase("userName")
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
						idseriesDropDown.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("series_for"))
					{
						seriesfor = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							seriesForExist = "true";
						else
							seriesForExist = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("location"))
					{
						location = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
						locationNameExit = "true";
						else
							locationNameExit = "false";
					}
					else if (gdp.getColomnName().equalsIgnoreCase("gate"))
					{
						gate = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							gateNameExist = "true";
						else
							gateNameExist = "false";
					}
				}
			}
		} catch (Exception e)
		{
			addActionError("Ooops There Is Problem to Add Acknowledge Post!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <idSeriesAdd> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getPrefixData()
	{
		String resString = ERROR;
		try {
			if(userName == null || userName.equalsIgnoreCase("")){
				resString = LOGIN;
			}
			if(getLocationName()!=null){
				prefixstring = CommonMethod.getPrefixData(connectionSpace, getLocationName());
				resString = SUCCESS;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resString;
	}
	/*
	 * To select gate name on the basis of location.
	 */
	public String getGateDetails()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			gatenamelist = new HashMap<Integer, String>();
			gatenamelist = CommonMethod.allMappedGateList(connectionSpace, id);

			if (gatenamelist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : gatenamelist.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to Load Gate Name!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getGateDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}
	//Submit idseries record in database.
	public String submitIDSeriesDetail()
	{
		String pre = null;
		String subpre = null;
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> purposeData = Configuration
				    .getConfigurationData("mapped_vam_idseries_configuration",
				        accountID, connectionSpace, "", 0, "table_name",
				        "vam_idseries_configuration");

				if (purposeData != null && purposeData.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : purposeData)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("vam_idseries_details", tableColume,
					    connectionSpace);
					List<String> requestParameterNames = Collections
					    .list((Enumeration<String>) request.getParameterNames());
					//System.out.println("requestParameterNames:====="+ requestParameterNames.size());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if(parmName.equalsIgnoreCase("prefix"))
							{
								pre = paramValue;
							}
							if(parmName.equalsIgnoreCase("sub_prefix"))
							{
								subpre = paramValue;
							}
							if(parmName.equalsIgnoreCase("number_series"))
							{
								numberseries = paramValue;
							}
							if (paramValue != null && !paramValue.equalsIgnoreCase("")
							    && parmName != null )
							{
								ob = new InsertDataTable();
								ob.setColName(parmName);
								ob.setDataName(paramValue);
								insertData.add(ob);
							}
						}
					}

					ob = new InsertDataTable();
					ob.setColName("series");
					ob.setDataName(pre+subpre+numberseries);
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

					//System.out.println("insertData:" + insertData.size());
					status = cbt.insertIntoTable("vam_idseries_details", insertData,
					    connectionSpace);
					if (status)
					{
						addActionMessage("Id Series Saved Successfully!!!");
						returnResult = SUCCESS;
					}
				} else
				{
					addActionMessage("There is some error in data Insertion!!!!");
				}
			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <submitIDSeriesDetail> of class "
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
	// for visitor entry view.
	public String beforeVisitorEntryRecords()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			locationlist = new HashMap<Integer, String>();
			locationlist = CommonMethod.allLocationList(connectionSpace);
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			purposeListPreReqest = new HashMap<Integer, String>();
			purposeListPreReqest = CommonMethod.allPurpose(connectionSpace);
			setMainHeaderName("Visitor Activity Board");
			setVisitorViewProp("mapped_visitor_configuration",
			    "visitor_configuration");
			String query=null;
		   //count total 
		    	    query="select count(*) from visitor_entry_details";
				    countTotal=CommonMethod.countRecord(query,connectionSpace);
					query="select count(*) from visitor_entry_details where time_in !='null' ";
					countIn=CommonMethod.countRecord(query,connectionSpace);
					query="select count(*) from visitor_entry_details where time_out !='null' ";
					countOut=CommonMethod.countRecord(query,connectionSpace);
			 if(column!=null)
		     {
		       if(column.equalsIgnoreCase("visit_date"))
		         {
		    	  query="select count(*) from visitor_entry_details where "+column+" >= '"+from_date+"' and  "+column+" <= '"+to_date+"' "; 
		    	  countTotal=CommonMethod.countRecord(query,connectionSpace);
			      query="select count(*) from visitor_entry_details where "+column+" >= '"+from_date+"' and  "+column+" <= '"+to_date+"' and time_in !='null' ";
				  countIn=CommonMethod.countRecord(query,connectionSpace);
				  query="select count(*) from visitor_entry_details where "+column+" >= '"+from_date+"' and  "+column+" <= '"+to_date+"' and time_out !='null' ";
				  countOut=CommonMethod.countRecord(query,connectionSpace);
				  }
		      }
		 //count on basic of department,purpose,location  	     
		  	       if(deptName!=null)
			    	   condition=deptName;
			       if(purpose!=null)
			    	   condition=purpose;
			       if(location!=null)
			    	   condition=location;
			       if(condition !=null)
			         {
			    	  query="select count(*) from visitor_entry_details where "+column+"="+condition+" ";
			    	  countTotal=CommonMethod.countRecord(query,connectionSpace);	 
			    	  query="select count(*) from visitor_entry_details where  "+column+"="+condition+" and time_in !='null' ";
					  countIn=CommonMethod.countRecord(query,connectionSpace);	
				      query="select count(*) from visitor_entry_details where "+column+"="+condition+" and time_out !='null' ";
				      countOut=CommonMethod.countRecord(query,connectionSpace);
					 }
	              //Exit visitor
			       if(sr_number != null && !sr_number.equalsIgnoreCase(""))
			       {
			         ExitVisitor(sr_number);
			       } 
			    
		} catch (Exception e)
		{
			log.error(
			    "Date : "
			        + DateUtil.getCurrentDateIndianFormat()
			        + " Time: "
			        + DateUtil.getCurrentTime()
			        + " "
			        + "Problem in Over2Cloud  method <beforeVisitorEntryRecords()> of class "
			        + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;

	}
	
	public void setVisitorViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewVisitorDetail.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration
		    .getConfigurationData(table1, accountID, connectionSpace, "", 0,
		        "table_name", table2);
		for (GridDataPropertyView gdp : returnResult)
		{
			gpv = new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			if (gdp.getColomnName().equalsIgnoreCase("location"))
		      {
			      gpv.setHeadingName("Location & Gate");
				  gpv.setHideOrShow("false");
		      }
			else if(gdp.getColomnName().equalsIgnoreCase("gate"))
			{
				gpv.setHideOrShow("true");
			}
			else if (gdp.getColomnName().equalsIgnoreCase("visit_date"))
		      {
			      gpv.setHeadingName("Visit Date & Time");
				  gpv.setHideOrShow("false");
		      }
			else if(gdp.getColomnName().equalsIgnoreCase("time_in"))
			{
				gpv.setHideOrShow("true");
			}
			else
		      {
		    	  gpv.setHeadingName(gdp.getHeadingName());
		    	  gpv.setHideOrShow(gdp.getHideOrShow());
		      }
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(gdp.getWidth());
			viewVisitorDetail.add(gpv);
		}
	}
	//Exit visitor function
	public void ExitVisitor(String visitor_id){
		//String resString = ERROR;
		String query = null;
		try 
		{
		 String updatetimeout = DateUtil.getCurrentTime();
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		 if(getSr_number() != null && !getSr_number().equalsIgnoreCase(""))
		 {
			query = "update visitor_entry_details set status = 1, time_out = '"+updatetimeout+"' where sr_number like '"+"%"+visitor_id+"'";
		 }
		 /*if(getBarcode() != null && !getBarcode().equalsIgnoreCase(""))
		 {
			query = "update visitor_entry_details set status = 1, time_out = '"+updatetimeout+"' where barcode = '"+getBarcode()+"'";
		 }*/
		 //update on local.
		 int localstatus = cbt.executeAllUpdateQuery(query, connectionSpace);
	     PreRequestserviceStub updateTable=new PreRequestserviceStub();
	     UpdateTable obj=new UpdateTable();
	     obj.setTableQuery(query);
		 if(localstatus>0)
		 {
			selectedId="1";
		 }
		 //update on Server.		
		/*if(localstatus>0)
		{
			addActionMessage("Visitor Exited Successfully.");
			resString = SUCCESS;
		}else
		{
			addActionMessage("There is Some Problem in Visitor Exit.");
			resString = ERROR;
		}*/
		boolean status = updateTable.updateTable(obj).get_return();
		
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <ExitVisitor()> of class "+getClass(), e);
			e.printStackTrace();
		}
		//return resString;
	}
	
	public String visitorDetails()
	{	String strResult = ERROR;
		if (userName == null || userName.equalsIgnoreCase(""))
		{
			return LOGIN;
		}
			try
			{   
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from visitor_entry_details");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
				    connectionSpace);
				if (dataCount != null)
				{
					
					// getting the list of colmuns
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					StringBuilder queryEnd = new StringBuilder("");
					queryEnd.append(" from visitor_entry_details as vid ");
					List fieldNames = Configuration.getColomnList(
					    "mapped_visitor_configuration", accountID, connectionSpace,
					    "visitor_configuration");
					int i = 0;
					if (fieldNames != null && !fieldNames.isEmpty())
					{
						for (Iterator iterator = fieldNames.iterator(); iterator.hasNext();)
						{

							String object = iterator.next().toString();
							if (object != null)
							{
								if (i < fieldNames.size() - 1)
								{
									if (object.toString().equalsIgnoreCase("purpose"))
									{
										query.append(" pradmin.purpose , ");
										queryEnd
										    .append(" left join purpose_admin as pradmin on pradmin.id = vid.purpose ");
									}
									if (object.toString().equalsIgnoreCase("deptName"))
									{
										query.append(" dept.deptName , ");
										queryEnd
										    .append(" left join department as dept on dept.id = vid.deptName ");
									}
									if (object.toString().equalsIgnoreCase("location"))
									{
										query.append(" loc.name , ");
										queryEnd
										    .append(" left join location as loc on loc.id = vid.location ");
									} else
									{
										if (!object.toString().equalsIgnoreCase("purpose")
										    && !object.toString().equalsIgnoreCase("deptName"))
										{
											query.append("vid." + object.toString() + ",");
										}
									}

								}
								// query.append(object.toString()+",");
								else
									query.append("vid." + object.toString());
							}
							i++;
						}

						query.append(" " + queryEnd.toString());

						if (getDeptName() != null && !getDeptName().equals("-1"))
						{
							query.append(" " + "where vid.deptName=" + getDeptName());
						}
						else
						if (getPurpose() != null && !getPurpose().equals("-1"))
						{
							query.append(" " + "where vid.purpose=" + getPurpose());
						}
						else 
						if (getLocation() != null && !getLocation().equals("-1"))
						{
							query.append(" " + "where vid.location=" + getLocation());
						}
						else if(visitor_name !=null && !visitor_name.equalsIgnoreCase(""))
						{
							query.append(" " + "where vid.visitor_name = '"+visitor_name);
							
						}
						else if(visitor_mobile !=null && !visitor_mobile.equalsIgnoreCase(""))
						{
							query.append(" " + "where vid.visitor_mobile = "+visitor_mobile);
							
						}
						else if(search_parameter != null && !search_parameter.equalsIgnoreCase(""))
						{      
							query.append(" where ");
				    	    if (fieldNames != null && !fieldNames.isEmpty()) 
						       {  
				    	    	  int k=0;
							      for (Iterator iterator = fieldNames.iterator(); iterator
									                                                  .hasNext();) 
							           {  
		                                 String object = iterator.next().toString();
							 	         if (object != null)
							 	            {
							 		         query.append("vid."+object+" LIKE " + "'"+search_parameter+"%"+"'");
							 	            } 
							 	         if(k <fieldNames.size()-1)
							 	        	query.append(" OR ");
							 	         else 
							 	        	query.append(" ");
					 	                 k++;
							 	      }
						        }
						}
						else if (getFrom_date() != null && !getFrom_date().equals("")
							    && getTo_date() != null && !getTo_date().equals(""))
						{	setFrom_date(DateUtil.convertDateToUSFormat(getFrom_date()));
							setTo_date(DateUtil.convertDateToUSFormat(getTo_date()));
							query.append(" where vid.visit_date >= '" + getFrom_date()
							    + "' and vid.visit_date <= '" + getTo_date() + "'");
						}
				        if(selectedId != null && !selectedId.equalsIgnoreCase("-1"))
						 {  if(!selectedId.equalsIgnoreCase("2"))
						            {
							          if (getFrom_date() != null && !getFrom_date().equals(""))
							    	       query.append(" and " + "vid.status=" + selectedId+" ORDER BY visit_date ASC,time_in ASC");
							          else if(selectedId.equalsIgnoreCase("1"))	 
							    	      query.append(" where " + "vid.status=" + selectedId+" ORDER BY visit_date DESC,time_out DESC");
							         
						              else if(selectedId.equalsIgnoreCase("0"))	 
								    	    query.append(" where " + "vid.status=" + selectedId+" ORDER BY visit_date ASC,time_in ASC");
						            }
						 }

						if (getSearchField() != null && getSearchString() != null
						    && !getSearchField().equalsIgnoreCase("")
						    && !getSearchString().equalsIgnoreCase(""))
						   {
							query.append(" where ");

							// add search query based on the search operation
							if (getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" vid." + getSearchField() + " = '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" vid." + getSearchField() + " like '%"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" vid." + getSearchField() + " like '"
								    + getSearchString() + "%'");
							} else if (getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" vid." + getSearchField() + " <> '"
								    + getSearchString() + "'");
							} else if (getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" vid." + getSearchField() + " like '%"
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

						/**s
						 * **************************checking for colomn change due to
						 * configuration if then alter table
						 */
						cbt.checkTableColmnAndAltertable(fieldNames,
						    "visitor_entry_details", connectionSpace);

						System.out.println("Querry visitor2>>>>>>>>>>>>>>>>>" + query.toString());
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
											one.put(fieldNames.get(k).toString(),
											                                    obdata[k].toString());
										} else
										{
											if (k == 0)
											{
												one.put(fieldNames.get(k).toString(),
												    (Integer) obdata[k]);
											} else
											{
												
												
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil
													    .convertDateToIndianFormat(obdata[k].toString()));
												}
												if (fieldNames.get(k) != null
												    && fieldNames.get(k).toString()
												        .equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),
													    (obdata[k].toString().substring(0, 5)));
												}
												else if (fieldNames.get(k) != null
													    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("location"))
														{
															if(obdata[k+9] != null &&  obdata[k] !=null && obdata[k+4] != null)
															{
																one.put(fieldNames.get(k).toString(),
																    obdata[k].toString()+"   "+obdata[k+4].toString());
															}
														}
													else if (fieldNames.get(k) != null
													    && fieldNames.get(k).toString()
											        .equalsIgnoreCase("visit_date"))
														{
															if(obdata[k+1] != null &&  obdata[k] !=null)
															{
																one.put(fieldNames.get(k).toString(),
																   DateUtil.convertDateToIndianFormat(obdata[k].toString())+","+obdata[k+1].toString().substring(0, 5));
															}
														}
												
												
												
												else
												{   
													
													if(fieldNames.get(k) != null
												                          && fieldNames.get(k).toString()
												                                    .equalsIgnoreCase("sr_number"))
												          {
														 one.put(fieldNames.get(k).toString(),
								                                    (obdata[k].toString().toUpperCase().substring(5, 10)));
												           }
													else{
													        one.put(fieldNames.get(k).toString(),
													                                    DateUtil.makeTitle(obdata[k].toString()));
													    }
												}

											}
										}

									}
								}
								listhb.add(one);
							}
							setVisitorData(listhb);
							setTotal((int) Math.ceil((double) getRecords()
							                                              / (double) getRows()));
							
						}
					}

				}
				strResult = SUCCESS;

			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <visitorDetails> of class "
				    + getClass(), e);
				e.printStackTrace();
				strResult = ERROR;
			}
		return strResult;
	}
	public String addVisitorDetails()
	{
		String prefixval = "VS";
		String subpre = null;
		String resString = ERROR;
		CommonMethod srNumberObject = new CommonMethod();
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			//SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			String visitorsrnumber = srNumberObject
			    .getVisitorSerialNumber(connectionSpace);
			int number = Integer.parseInt(visitorsrnumber);
			//System.out.println("visitorsrnumber>"+visitorsrnumber);
			String visitorSrNum = srNumberObject.getSeries(connectionSpace,prefixval);
			if(visitorSrNum != null && !visitorSrNum.equals(""))
			{
				String pre = visitorSrNum.substring(0, 2);
				subpre = visitorSrNum.substring(2, 5);
				String numberseries = visitorSrNum.substring(5);
				//System.out.println("pre>"+pre+"subpre>"+subpre+"numberseries>>"+numberseries);
			}
			if (number <=1000 )
			{
				number = 1001;
			} else
			{
				number = number + 1;
			}
			String strnumber = Integer.toString(number);
			
			String srNumber = prefixval+subpre+strnumber;
			deptDropDown = new ArrayList<ConfigurationUtilBean>();
			// visitorfields = new ArrayList<ConfigurationUtilBean>();
			visitorfields = new LinkedList<ConfigurationUtilBean>();
			visitordatetimelist = new ArrayList<ConfigurationUtilBean>();
			departmentlist = new HashMap<Integer, String>();
			departmentlist = CommonMethod.allDepartmentList(connectionSpace);
			purposelist = CommonMethod.allPurpose(connectionSpace);
			List<GridDataPropertyView> group = Configuration.getConfigurationData(
			    "mapped_visitor_configuration", accountID, connectionSpace, "", 0,
			    "table_name", "visitor_configuration");
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
						deptDropDown.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("Date")
					    || gdp.getColType().trim().equalsIgnoreCase("Time")
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
						visitordatetimelist.add(obj);
					}
					if (gdp.getColomnName().equalsIgnoreCase("deptName"))
					{
						deptName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							deptNameExist = "true";
						else
							deptNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("empName"))
					{
						empName = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							empNameExist = "true";
						else
							empNameExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("purpose"))
					{
						purpose = gdp.getHeadingName();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							purposeExist = "true";
						else
							purposeExist = "false";
					} else if (gdp.getColomnName().equalsIgnoreCase("visit_date"))
					{
						visit_date = gdp.getHeadingName();
						datevalue = DateUtil.getCurrentDateIndianFormat();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							visitdateExist = "true";
						else
							visitdateExist = "false";

					} else if (gdp.getColomnName().equalsIgnoreCase("time_in"))
					{
						time_in = gdp.getHeadingName();
						timevalue = DateUtil.getCurrentTime();
						if (gdp.getMandatroy().equalsIgnoreCase("1"))
							visittimeExist = "true";
						else
							visittimeExist = "false";
					}
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("ip")
					    && !gdp.getColomnName().equalsIgnoreCase("alert_time")
					    && !gdp.getColomnName().equalsIgnoreCase("accept")
					    && !gdp.getColomnName().equalsIgnoreCase("reject")
					    && !gdp.getColomnName().equalsIgnoreCase("status")
					    && !gdp.getColomnName().equalsIgnoreCase("image")
					    && !gdp.getColomnName().equalsIgnoreCase("db_transfer")
					    && !gdp.getColomnName().equalsIgnoreCase("update_date")
					    && !gdp.getColomnName().equalsIgnoreCase("update_time")
					    && !gdp.getColomnName().equalsIgnoreCase("time_out")
					    && !gdp.getColomnName().equalsIgnoreCase("update_time")
					    && !gdp.getColomnName().equalsIgnoreCase("userName")
					    && !gdp.getColomnName().equalsIgnoreCase("car_number")
					    && !gdp.getColomnName().equalsIgnoreCase("comments")
					    && !gdp.getColomnName().equalsIgnoreCase("alert_after"))
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
						if (gdp.getColomnName().equalsIgnoreCase("one_time_password") || gdp.getColomnName().equalsIgnoreCase("visitor_mobile"))
						{

							if (!visitorfields.isEmpty()
							    && visitorfields.get(0).getField_value() != null
							    && visitorfields.get(0).getField_value()
							        .equalsIgnoreCase("one_time_password"))
							{
								visitorfields.add(1, obj);
								
							} else
							{
								visitorfields.addFirst(obj);
							}
						} 
						else
						{
							visitorfields.add(obj);
						}
					}
					if (gdp.getColomnName().equalsIgnoreCase("sr_number"))
					{
						setSr_number(srNumber);
					}
				}
				resString = SUCCESS;
			}

		} catch (Exception e)
		{
			addActionError("Ooops There Is Problem to Add Vender!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <addVisitorDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resString;
	}
	public String getEmployeeDetails()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			employeelist = new HashMap<Integer, String>();
			if (getEmplocgatemap() != null && getEmplocgatemap().equals("emplocgatemap"))
			{
				employeelist = CommonMethod.employeeList(connectionSpace, id);
			} else if (getEmplocgatemap() != null && getEmplocgatemap().equals("loademp"))
			{
				employeelist = CommonMethod.alleEmployeeList(connectionSpace, id);
			}
			else if (getPurposeaddition() != null && getPurposeaddition().equals("purposeaddition"))
			{
				//employeelist = CommonMethod.alleEmployeeList(connectionSpace, id);
				employeelist = CommonMethod.allEmployeeOnly(connectionSpace, id);
			}
			if (employeelist != null)
			{
				jsonArray = new JSONArray();
				jsonObject = new JSONObject();
				for (Entry<Integer, String> entry : employeelist.entrySet())
				{

					jsonObject.put("ID", entry.getKey());
					jsonObject.put("NAME", entry.getValue());

					jsonArray.add(jsonObject);
				}
				resultString = SUCCESS;
			}
		} catch (Exception e)
		{
			addActionError("There Is Problem to Load Employee!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getEmployeeDetails> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}
	public String getAlertTime()
	{
		String resultString = ERROR;
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				resultString = LOGIN;
			}
			if (id != null)
			{
				alerttime = CommonMethod.alertTime(connectionSpace, id);
				resultString = SUCCESS;
			} else
			{
				resultString = ERROR;
			}

		} catch (Exception e)
		{
			addActionError("Ooops There Is Problem to Load getAlertTime!");
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method <getAlertTime> of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return resultString;
	}
	@SuppressWarnings("static-access")
  public String submitVisitorDetails()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		String photoname;
		String visitedPerson = null;
		String emailid = null;
		String empMobileNo = null;
		String visitorName = null;
		String visitorMobile = null;
		String visitorCompany = null;
		String srNumber = null;
		String locname = null;
		String gatename = null;
		List<String> Tablecolumename=new ArrayList<String>(); 
		List<String> Tablecolumevalue=new ArrayList<String>(); 
		if (sessionFlag)
		{
			try
			{
				String path = request.getSession().getServletContext().getRealPath("/");
				FileInputStream filename = new FileInputStream(path
				    + "//visitorimagepath.txt");
				DataInputStream in = new DataInputStream(filename);
				readfilename = new BufferedReader(new InputStreamReader(in));
				photoname = readfilename.readLine();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<GridDataPropertyView> purposeData = Configuration
				    .getConfigurationData("mapped_visitor_configuration", accountID,
				        connectionSpace, "", 0, "table_name", "visitor_configuration");

				if (purposeData != null && purposeData.size() > 0)
				{
					// create table query based on configuration
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					boolean status = false;
					boolean serverstatus = false;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					for (GridDataPropertyView gdp : purposeData)
					{
						TableColumes ob1 = new TableColumes();
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);
					}
					cbt.createTable22("visitor_entry_details", tableColume,
					    connectionSpace);
					List<String> requestParameterNames = Collections
					    .list((Enumeration<String>) request.getParameterNames());
					if (requestParameterNames != null && requestParameterNames.size() > 0)
					{

						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue = request.getParameter(parmName);
							if (parmName.equalsIgnoreCase("visited_person"))
							{
								emailid = CommonMethod.getVisitorEmailId(connectionSpace,
								    paramValue);
								visitedPerson = paramValue;
							}
							if (parmName.equalsIgnoreCase("visited_mobile"))
							{
								empMobileNo = paramValue;
							}
							if (parmName.equalsIgnoreCase("deptName"))
							{
								dept = CommonMethod.getDeptForId(connectionSpace, paramValue);
							}
							if (parmName.equalsIgnoreCase("visitor_name"))
							{
								visitorName = paramValue;
							}
							if (parmName.equalsIgnoreCase("visitor_mobile"))
							{
								visitorMobile = paramValue;
							}
							if (parmName.equalsIgnoreCase("visitor_company"))
							{
								visitorCompany = paramValue;
							} 
							if (parmName.equalsIgnoreCase("sr_number"))
							{
								srNumber = paramValue;
							}
							if (parmName.equalsIgnoreCase("location"))
							{
								locname = paramValue;
							}
							if (parmName.equalsIgnoreCase("gate"))
							{
								gatename = paramValue;
							}
							if (parmName.equalsIgnoreCase("visit_date"))
							{
								paramValue = DateUtil.convertDateToUSFormat(paramValue);
							}
							if (paramValue != null && !paramValue.equalsIgnoreCase("")
							    && parmName != null && !parmName.endsWith("sms")
							    && !parmName.endsWith("mail"))
							{
								Tablecolumename.add(parmName);
								Tablecolumevalue.add(paramValue);
							}
						}
					}

					/** 
					 * Code to insert data on server database.
					 * */
					PreRequestserviceStub objstatus=new PreRequestserviceStub();
					InsertIntoTable obj=new InsertIntoTable();
					Tablecolumename.add("userName");
					Tablecolumevalue.add(userName);
					Tablecolumename.add("date");
					Tablecolumevalue.add(DateUtil.getCurrentDateUSFormat());
					Tablecolumename.add("time");
					Tablecolumevalue.add(DateUtil.getCurrentTime());
					Tablecolumename.add("status");
					Tablecolumevalue.add("0");
					Tablecolumename.add("image");
					Tablecolumevalue.add(photoname);
					StringBuilder createTableQuery = new StringBuilder("INSERT INTO " +"visitor_entry_details"+" ("); 

					int i=1;
					// append Column 
					for(String h: Tablecolumename)
						{
							if(i<Tablecolumename.size())
								createTableQuery.append(h+", ");
							else
								createTableQuery.append(h+")");
							i++;
						}
					 
					createTableQuery.append(" VALUES (");
					i=1;
					for(String h:Tablecolumevalue)
					{
						if(i<Tablecolumevalue.size())
							createTableQuery.append("'"+h+"', ");
						else
							createTableQuery.append("'"+h+"')");
						i++;
					}
					createTableQuery.append(" ;");
					obj.setCreateTableQuery(createTableQuery.toString());
				   //insert into local database.
				    int maxId = cbt.insertIntoTable(createTableQuery.toString(),connectionSpace);
				  // insert into Server database.
				   try {
					   status= objstatus.insertIntoTable(obj).get_return();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally
				{
					 status = true;
				}
				    /*Code For Bar Code*/
					String filePath = request.getSession().getServletContext().getRealPath("/");
					File TTFfilePath = new File(filePath + "/images/", "3of9.TTF");
				    if(TTFfilePath!=null)
					{
						String saveImgPath = request.getSession().getServletContext().getRealPath("/images/barCodeImage/");
						if(saveImgPath!=null)
						{
							StringBuffer buffer = new StringBuffer();
							for (int m = 0; m < 10 - String.valueOf(maxId).length(); m++)
							{
								buffer.append("0");
							}
							buffer.append(String.valueOf(maxId));
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

							graphicsobj.fillRect(1, 1, 248, 150);
							graphicsobj.setColor(Color.BLACK);
							((Graphics2D) graphicsobj).drawString(buffer.toString(), 40, 60);
							Font font2 = new Font("serif", Font.PLAIN, 20);
							graphicsobj.setFont(font2);
							graphicsobj.drawString("Vis S/N : " + srNumber, 30, 50);
							File saveFile = new File(saveImgPath, String.valueOf(maxId) + ".gif");
							System.out.println("Save Image Path"+saveImgPath);
							ImageIO.write(bufimg, format, saveFile);
							vpassId=String.valueOf(maxId);
						}
					}
				    
				    
				    // For image path.
				   // String imgpath = request.getRealPath("/");
					String photopath = path+photoname;
					System.out.println("mailphoto>>"+photopath);
	    		    InetAddress ownIP=InetAddress.getLocalHost();
			        StringBuilder url2 = new StringBuilder();
				    url2.append("http://").append(ownIP.getHostAddress()).append(":8080/over2cloud/view/Over2Cloud/VAM/master/");
				    String   url=url2.toString();
					String locandgate = CommonMethod.getLocAndGateVal(connectionSpace,locname, gatename);
					String[] locngate = locandgate.split("#");
				    if(maxId > 0)
			        {
			            if(getSms().booleanValue() && getMail().booleanValue())
			            {
			                if(empMobileNo != null && empMobileNo != "" && empMobileNo.trim().length() == 10 && !empMobileNo.startsWith("NA") && (empMobileNo.startsWith("9") || empMobileNo.startsWith("8") || empMobileNo.startsWith("7")))
			                {
			                    String empMsg = (new StringBuilder("Hi ")).append(visitedPerson).append(", ").append(visitorName).append(" from ").append(visitorCompany).append(" - (").append(visitorMobile).append(") is here to meet you at ").append(locngate[0]).append(", and waiting for your approval.Kindly approve by sending sms JBM ").append(locngate[1]).append("-").append(srNumber).append(" Y/N at 9870845678.").toString();
			                    (new MsgMailCommunication()).addMessageClientServer(empMobileNo, empMsg, "", "Pending", "0", "VAM", connectionSpace);
			                }
			                if(emailid != null && !emailid.equals("") && !emailid.equals("NA"))
			                {
			                    MailText mailtextobj = new MailText();
			                    String mailText = mailtextobj.getMailText(visitedPerson, visitorName, visitorCompany, visitorMobile, photopath, locngate[0], url, srNumber);
			                    (new MsgMailCommunication()).addMail(emailid, "Visitor Entry Mail", mailText, "", "Pending", "0", "", "VAM", connectionSpace);
			                }
			            } else
			            if(getSms().booleanValue())
			            {
			                if(empMobileNo != null && empMobileNo != "" && empMobileNo.trim().length() == 10 && !empMobileNo.startsWith("NA") && (empMobileNo.startsWith("9") || empMobileNo.startsWith("8") || empMobileNo.startsWith("7")))
			                {
			                    String empMsg = (new StringBuilder("Hi ")).append(visitedPerson).append(", ").append(visitorName).append(" from ").append(visitorCompany).append(" - (").append(visitorMobile).append(") is here to meet you at ").append(locngate[0]).append(", and waiting for your approval.Kindly approve by sending sms JBM ").append(locngate[1]).append("-").append(srNumber).append(" Y/N at 9870845678.").toString();
			                    (new MsgMailCommunication()).addMessageClientServer(empMobileNo, empMsg, "", "Pending", "0", "VAM", connectionSpace);
			                }
			            } else
			            if(getMail().booleanValue() && emailid != null && !emailid.equals("") && !emailid.equals("NA"))
			            {
			                MailText mailtextobj = new MailText();
			                String mailText = mailtextobj.getMailText(visitedPerson, visitorName, visitorCompany, visitorMobile, photopath, locngate[0], url, srNumber);
			                (new MsgMailCommunication()).addMail(emailid, "Visitor Entry Mail", mailText, "", "Pending", "0", "", "VAM", connectionSpace);
			            }
			            addActionMessage("Data Save Successfully!!!");
			            returnResult = "success";
			        }
				} else
				{
					addActionMessage("Oops There is some error in data insertion!!!!");
				}
			} catch (Exception e)
			{
				log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
				    + DateUtil.getCurrentTime() + " "
				    + "Problem in Over2Cloud  method <submitVisitorDetails> of class "
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
	public String editVisitorEntryGrid()
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
						if(Parmname.equalsIgnoreCase("purpose"))
	                    {
	                    String pur=request.getParameter("purpose");
	                    String purpose=CommonMethod.getPurposeId(connectionSpace, pur);
	                    wherClause.put(Parmname, purpose);
	                    }    
	                    else if(Parmname.equalsIgnoreCase("deptName"))
	                    {
	                    String dept=request.getParameter("deptName");
	                    String depid=CommonMethod.getDeptId(connectionSpace, dept);
	                    wherClause.put(Parmname, depid);
	                    }
	                    
	                    else if(Parmname.equalsIgnoreCase("visit_date"))
	                    {
	                    String str=request.getParameter("visit_date");
	                    String st[]=str.split("  ");
	                    String date=DateUtil.convertDateToUSFormat(st[0].toString());
	                    //    String depid=CommonMethod.getDeptId(connectionSpace, dept);
	                    wherClause.put(Parmname, date);
	                    }
	                    else if(Parmname.equalsIgnoreCase("location")||Parmname.equalsIgnoreCase("gate"))
	                    {
	                        if(request.getParameter("location")!=null ||request.getParameter("gate")!=null){
	                        String st=request.getParameter("location");
	                        String loc=null,gate=null;
	                       
	                        if(st!=null || st!=" "){
	                        String str[]=st.split(" ");
	                        if(str.length == 3)
	                        {
	                             loc=str[0].toString()+""+str[1].toString();
	                             gate=str[2].toString();
	                        }
	                        else
	                        {
	                             loc=str[0].toString();
	                             gate=str[1].toString();
	                        }
	                        }
	                        String locationId= CommonMethod.getLocationId(connectionSpace,loc);
	                        
	                        if(Parmname.equalsIgnoreCase("gate"))
	                        {
	                            wherClause.put(Parmname,gate);
	                        }
	                        else{
	                            wherClause.put(Parmname,locationId);
	                        }
	                        }
	                    }
	                    else{
	                    wherClause.put(Parmname, paramValue);
	                    }
	            }
	            if(wherClause.containsKey("oper"))
	            {
	                wherClause.remove("oper");
	            }
				condtnBlock.put("id", getId());
				cbt.updateTableColomn("visitor_entry_details", wherClause, condtnBlock,
				    connectionSpace);
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
				cbt.deleteAllRecordForId("visitor_entry_details", "id",
				    condtIds.toString(), connectionSpace);
			}
		} catch (Exception e)
		{
			log.error("Date : " + DateUtil.getCurrentDateIndianFormat() + " Time: "
			    + DateUtil.getCurrentTime() + " "
			    + "Problem in Over2Cloud  method editVisitorEntryGrid of class "
			    + getClass(), e);
			e.printStackTrace();
		}
		return SUCCESS;
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

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getViewacknowledgepostDetail()
	{
		return viewacknowledgepostDetail;
	}

	public void setViewacknowledgepostDetail(
	    List<GridDataPropertyView> viewacknowledgepostDetail)
	{
		this.viewacknowledgepostDetail = viewacknowledgepostDetail;
	}
	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0)
	{
		this.request = arg0;

	}

	public List<Object> getAcknowledgepostdata() {
		return acknowledgepostdata;
	}

	public void setAcknowledgepostdata(List<Object> acknowledgepostdata) {
		this.acknowledgepostdata = acknowledgepostdata;
	}

	public List<ConfigurationUtilBean> getFrontoffice() {
		return frontoffice;
	}

	public void setFrontoffice(List<ConfigurationUtilBean> frontoffice) {
		this.frontoffice = frontoffice;
	}
	public Map<Integer, String> getVendortypelist() {
		return vendortypelist;
	}
	public void setVendortypelist(Map<Integer, String> vendortypelist) {
		this.vendortypelist = vendortypelist;
	}

	public String getVendortype() {
		return vendortype;
	}

	public void setVendortype(String vendortype) {
		this.vendortype = vendortype;
	}
	public String getVendorTypeExist() {
		return vendorTypeExist;
	}
	public void setVendorTypeExist(String vendorTypeExist) {
		this.vendorTypeExist = vendorTypeExist;
	}

	public String getDeptname()
	{
		return deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getDeptNameExit()
	{
		return deptNameExit;
	}

	public void setDeptNameExit(String deptNameExit)
	{
		this.deptNameExit = deptNameExit;
	}

	public Map<Integer, String> getDepartmentlist()
	{
		return departmentlist;
	}

	public void setDepartmentlist(Map<Integer, String> departmentlist)
	{
		this.departmentlist = departmentlist;
	}

	public String getEmpNameExist()
	{
		return empNameExist;
	}

	public void setEmpNameExist(String empNameExist)
	{
		this.empNameExist = empNameExist;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getEmplocgatemap()
	{
		return emplocgatemap;
	}

	public void setEmplocgatemap(String emplocgatemap)
	{
		this.emplocgatemap = emplocgatemap;
	}

	public String getEmp_mail()
	{
		return emp_mail;
	}

	public void setEmp_mail(String emp_mail)
	{
		this.emp_mail = emp_mail;
	}

	public String getEmp_sms()
	{
		return emp_sms;
	}

	public void setEmp_sms(String emp_sms)
	{
		this.emp_sms = emp_sms;
	}

	public String getVendor_sms()
	{
		return vendor_sms;
	}

	public void setVendor_sms(String vendor_sms)
	{
		this.vendor_sms = vendor_sms;
	}

	public String getVendor_mail()
	{
		return vendor_mail;
	}

	public void setVendor_mail(String vendor_mail)
	{
		this.vendor_mail = vendor_mail;
	}

	public String getVender_name()
	{
		return vender_name;
	}

	public void setVender_name(String vender_name)
	{
		this.vender_name = vender_name;
	}

	public String getVendorNameExist()
	{
		return vendorNameExist;
	}

	public void setVendorNameExist(String vendorNameExist)
	{
		this.vendorNameExist = vendorNameExist;
	}

	public Map<Integer, String> getVendordatalist()
	{
		return vendordatalist;
	}

	public void setVendordatalist(Map<Integer, String> vendordatalist)
	{
		this.vendordatalist = vendordatalist;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public List<GridDataPropertyView> getViewidseriescolumn()
	{
		return viewidseriescolumn;
	}

	public void setViewidseriescolumn(List<GridDataPropertyView> viewidseriescolumn)
	{
		this.viewidseriescolumn = viewidseriescolumn;
	}

	public List<ConfigurationUtilBean> getIdseriesfieldslist()
	{
		return idseriesfieldslist;
	}

	public void setIdseriesfieldslist(List<ConfigurationUtilBean> idseriesfieldslist)
	{
		this.idseriesfieldslist = idseriesfieldslist;
	}

	public String getSeriesfor()
	{
		return seriesfor;
	}

	public void setSeriesfor(String seriesfor)
	{
		this.seriesfor = seriesfor;
	}

	public String getSeriesForExist()
	{
		return seriesForExist;
	}

	public void setSeriesForExist(String seriesForExist)
	{
		this.seriesForExist = seriesForExist;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getLocationNameExit()
	{
		return locationNameExit;
	}

	public void setLocationNameExit(String locationNameExit)
	{
		this.locationNameExit = locationNameExit;
	}

	public String getGate()
	{
		return gate;
	}

	public void setGate(String gate)
	{
		this.gate = gate;
	}

	public String getGateNameExist()
	{
		return gateNameExist;
	}

	public void setGateNameExist(String gateNameExist)
	{
		this.gateNameExist = gateNameExist;
	}

	public List<ConfigurationUtilBean> getIdseriesDropDown()
	{
		return idseriesDropDown;
	}

	public void setIdseriesDropDown(List<ConfigurationUtilBean> idseriesDropDown)
	{
		this.idseriesDropDown = idseriesDropDown;
	}

	public Map<Integer, String> getLocationList()
	{
		return locationList;
	}

	public void setLocationList(Map<Integer, String> locationList)
	{
		this.locationList = locationList;
	}

	public Map<Integer, String> getGatenamelist()
	{
		return gatenamelist;
	}

	public void setGatenamelist(Map<Integer, String> gatenamelist)
	{
		this.gatenamelist = gatenamelist;
	}

	public List<Object> getIdseriesData()
	{
		return idseriesData;
	}

	public void setIdseriesData(List<Object> idseriesData)
	{
		this.idseriesData = idseriesData;
	}

	public String getPrefixstring()
	{
		return prefixstring;
	}

	public void setPrefixstring(String prefixstring)
	{
		this.prefixstring = prefixstring;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	public List<ConfigurationUtilBean> getLocationgatelist()
	{
		return locationgatelist;
	}

	public void setLocationgatelist(List<ConfigurationUtilBean> locationgatelist)
	{
		this.locationgatelist = locationgatelist;
	}

	public String getVenderdatalist()
	{
		return venderdatalist;
	}

	public void setVenderdatalist(String venderdatalist)
	{
		this.venderdatalist = venderdatalist;
	}

	public String getVndrname()
	{
		return vndrname;
	}

	public void setVndrname(String vndrname)
	{
		this.vndrname = vndrname;
	}

	public Boolean getMail()
	{
		return mail;
	}

	public void setMail(Boolean mail)
	{
		this.mail = mail;
	}

	public Boolean getSms()
	{
		return sms;
	}

	public void setSms(Boolean sms)
	{
		this.sms = sms;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getAlerttime()
	{
		return alerttime;
	}

	public void setAlerttime(String alerttime)
	{
		this.alerttime = alerttime;
	}

	public Map<Integer, String> getEmployeelist()
	{
		return employeelist;
	}

	public void setEmployeelist(Map<Integer, String> employeelist)
	{
		this.employeelist = employeelist;
	}

	public String getPurposeaddition()
	{
		return purposeaddition;
	}

	public void setPurposeaddition(String purposeaddition)
	{
		this.purposeaddition = purposeaddition;
	}

	public String getSr_number()
	{
		return sr_number;
	}

	public void setSr_number(String sr_number)
	{
		this.sr_number = sr_number;
	}

	public LinkedList<ConfigurationUtilBean> getVisitorfields()
	{
		return visitorfields;
	}

	public void setVisitorfields(LinkedList<ConfigurationUtilBean> visitorfields)
	{
		this.visitorfields = visitorfields;
	}

	public String getPurposeExist()
	{
		return purposeExist;
	}

	public void setPurposeExist(String purposeExist)
	{
		this.purposeExist = purposeExist;
	}

	public String getVisitdateExist()
	{
		return visitdateExist;
	}

	public void setVisitdateExist(String visitdateExist)
	{
		this.visitdateExist = visitdateExist;
	}

	public String getVisit_date()
	{
		return visit_date;
	}

	public void setVisit_date(String visit_date)
	{
		this.visit_date = visit_date;
	}

	public String getVisittimeExist()
	{
		return visittimeExist;
	}

	public void setVisittimeExist(String visittimeExist)
	{
		this.visittimeExist = visittimeExist;
	}

	public String getTime_in()
	{
		return time_in;
	}

	public void setTime_in(String time_in)
	{
		this.time_in = time_in;
	}

	public String getDatevalue()
	{
		return datevalue;
	}

	public void setDatevalue(String datevalue)
	{
		this.datevalue = datevalue;
	}

	public String getTimevalue()
	{
		return timevalue;
	}

	public void setTimevalue(String timevalue)
	{
		this.timevalue = timevalue;
	}

	public String getPurposeNameExist()
	{
		return purposeNameExist;
	}

	public void setPurposeNameExist(String purposeNameExist)
	{
		this.purposeNameExist = purposeNameExist;
	}

	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}

	public String getDeptNameExist()
	{
		return deptNameExist;
	}

	public void setDeptNameExist(String deptNameExist)
	{
		this.deptNameExist = deptNameExist;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public List<ConfigurationUtilBean> getVisitordatetimelist()
	{
		return visitordatetimelist;
	}

	public void setVisitordatetimelist(
	    List<ConfigurationUtilBean> visitordatetimelist)
	{
		this.visitordatetimelist = visitordatetimelist;
	}

	public List<ConfigurationUtilBean> getDeptDropDown()
	{
		return deptDropDown;
	}

	public void setDeptDropDown(List<ConfigurationUtilBean> deptDropDown)
	{
		this.deptDropDown = deptDropDown;
	}

	public Map<Integer, String> getPurposelist()
	{
		return purposelist;
	}

	public void setPurposelist(Map<Integer, String> purposelist)
	{
		this.purposelist = purposelist;
	}

	public List<Object> getVisitorData()
	{
		return visitorData;
	}

	public void setVisitorData(List<Object> visitorData)
	{
		this.visitorData = visitorData;
	}

	public String getFrom_date()
	{
		return from_date;
	}

	public void setFrom_date(String from_date)
	{
		this.from_date = from_date;
	}

	public String getTo_date()
	{
		return to_date;
	}

	public void setTo_date(String to_date)
	{
		this.to_date = to_date;
	}

	public List<GridDataPropertyView> getViewVisitorDetail()
	{
		return viewVisitorDetail;
	}

	public void setViewVisitorDetail(List<GridDataPropertyView> viewVisitorDetail)
	{
		this.viewVisitorDetail = viewVisitorDetail;
	}

	public Map<Integer, String> getPurposeListPreReqest()
	{
		return purposeListPreReqest;
	}

	public void setPurposeListPreReqest(Map<Integer, String> purposeListPreReqest)
	{
		this.purposeListPreReqest = purposeListPreReqest;
	}

	public String getVpassId()
	{
		return vpassId;
	}

	public void setVpassId(String vpassId)
	{
		this.vpassId = vpassId;
	}
	public Map<Integer, String> getLocationlist() {
		return locationlist;
	}
	public void setLocationlist(Map<Integer, String> locationlist) {
		this.locationlist = locationlist;
	}
	public Map<Integer, String> getVendorcompnamelist() {
		return vendorcompnamelist;
	}
	public void setVendorcompnamelist(Map<Integer, String> vendorcompnamelist) {
		this.vendorcompnamelist = vendorcompnamelist;
	}

	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompNameExist() {
		return compNameExist;
	}
	public void setCompNameExist(String compNameExist) {
		this.compNameExist = compNameExist;
	}
	public Map<Integer, String> getVendorcompdatalist() {
		return vendorcompdatalist;
	}
	public void setVendorcompdatalist(Map<Integer, String> vendorcompdatalist) {
		this.vendorcompdatalist = vendorcompdatalist;
	}
	
	public String getCountTotal() {
		return countTotal;
	}

	public void setCountTotal(String countTotal) {
		this.countTotal = countTotal;
	}

	public String getCountIn() {
		return countIn;
	}

	public void setCountIn(String countIn) {
		this.countIn = countIn;
	}

	public String getCountOut() {
		return countOut;
	}

	public void setCountOut(String countOut) {
		this.countOut = countOut;
	}
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getVisitor_name() {
		return visitor_name;
	}

	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}

	public String getVisitor_mobile() {
		return visitor_mobile;
	}

	public void setVisitor_mobile(String visitor_mobile) {
		this.visitor_mobile = visitor_mobile;
	}

	public String getStatusParameter() {
		return statusParameter;
	}

	public void setStatusParameter(String statusParameter) {
		this.statusParameter = statusParameter;
	}

	public String getSearch_parameter() {
		return search_parameter;
	}

	public void setSearch_parameter(String search_parameter) {
		this.search_parameter = search_parameter;
	}

	
}
