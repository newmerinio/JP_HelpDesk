package com.Over2Cloud.ctrl.asset.helpdoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.FilesUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.VAM.master.CommonMethod;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddDocument extends ActionSupport implements ServletRequestAware  {
	static final Log log = LogFactory.getLog(AddDocument.class);
	private Map session = ActionContext.getContext().getSession();
	private String userName=(String)session.get("uName");
	private String accountID=(String)session.get("accountid");
	private SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	HttpServletRequest request;
	public String mainHeaderName;
	private Map<String, String> moduleNames=new HashMap<String, String>();
	private Map<String, String> linkNames=new HashMap<String, String>();
	
	private InputStream fileInputStream;
	
	private String filename;
	
	private String modulename;
	private String linkname;
	
	private String moduleId;
	
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
	private String id;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	private JSONArray jsonArray;
	
	private File[] docs = null;
	protected String[] docsFileName;
	private String docfilename;
	
	private List<GridDataPropertyView> viewidseriescolumn = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> idseriesfieldslist = null;
	private List<ConfigurationUtilBean> idseriesDropDown = null;
	private List<Object> idseriesData;
	
	private List<GridDataPropertyView> viewdocumentuploadcolumn = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> documentuploadfieldslist = null;
	private List<ConfigurationUtilBean> documentuploadDropDown = null;
	private List<Object> documentuploadData;
	
	
	
	public String beforeDocumentUploading()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setMainHeaderName("Uploading Documents");
			setUploadDocumnetViewProp("mapped_help_document_upload_configuration","help_document_upload_configuration");
			
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	
	
	public String beforeMappingLink()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setMainHeaderName("Help Document Link Mapping");
			setIdSeriesViewProp("mapped_help_document_configuration","help_document_configuration");
			
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	public void setUploadDocumnetViewProp(String table1, String table2)
	{

		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewdocumentuploadcolumn.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(table1, accountID, connectionSpace, "", 0,"table_name", table2);
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
			viewdocumentuploadcolumn.add(gpv);
		}
		System.out.println("size viewdocumentuploadcolumn  "+viewdocumentuploadcolumn.size());
		System.out.println("data viewdocumentuploadcolumn    "+viewdocumentuploadcolumn);
	
		
	}
	
	public void setIdSeriesViewProp(String table1, String table2)
	{
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		viewidseriescolumn.add(gpv);

		List<GridDataPropertyView> returnResult = Configuration.getConfigurationData(table1, accountID, connectionSpace, "", 0,"table_name", table2);
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
		System.out.println("size  "+viewidseriescolumn.size());
		System.out.println("data    "+viewidseriescolumn);
	}
	
	
	public String linkMappingAdd()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			
			
			moduleNames=getModuleNamesData();
			linkNames=getLinkNamesData();
			
			idseriesfieldslist = new ArrayList<ConfigurationUtilBean>();
			idseriesDropDown = new ArrayList<ConfigurationUtilBean>();
			
			
			String userType=(String)session.get("userTpe");
		
			List<GridDataPropertyView> group = Configuration.getConfigurationData("mapped_help_document_configuration", accountID, connectionSpace, "",0, "table_name", "help_document_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
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

						idseriesfieldslist.add(obj);
					}
					if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName().equalsIgnoreCase("userName")
					    )
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
					
				}
			}
			
			
			System.out.println("add form idseriesfieldslist     "+idseriesfieldslist.size());
			System.out.println("add  jjjjjjj  idseriesDropDown   "+idseriesDropDown.size());
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
	
	
	
	
public String insertMapping()
{

	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			
				String actionMsg = null;
				boolean checkstatus=false;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_help_document_configuration", accountID, connectionSpace, "", 0, "table_name", "help_document_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;					
					
					
					String modulename=request.getParameter("module_name");
					String linkname=request.getParameter("link_name");
					checkstatus=checkingexistfile4mapping(modulename,linkname);
					if(checkstatus)
					{
						addActionMessage("Already Mapped !!!");
						return SUCCESS;
					}
					else
					{
						ob = new InsertDataTable();
						ob.setColName("module_name");
						ob.setDataName(modulename);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("link_name");
						ob.setDataName(linkname);
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
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);
					}
					
					status = cbt.insertIntoTable("help_document", insertData, connectionSpace);
					

					if (status)
					{
						addActionMessage("Module Link Mapping added Successfully!!!");
						return SUCCESS;
					} else
					{
						addActionMessage(actionMsg);
						return SUCCESS;
					}
				} else
				{
					return ERROR;
				}
			
		} catch (Exception exp)
		{
			exp.printStackTrace();
			return ERROR;
		}
	} else
	{
		return LOGIN;
	}

	
}
	
public String getUploadDocumentData()
{

	String resString = ERROR;
	if (userName == null || userName.equalsIgnoreCase(""))
	{
		return LOGIN;
	}
	try
	{    CommonMethod VCM = new CommonMethod();
	     String userType=(String)session.get("userTpe");
        CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append("select count(*) from help_document_upload");
		List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		if (dataCount != null)
		   {
			// getting the list of colmuns
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from help_document_upload as hd inner join module_name_data as mnd on mnd.id=hd.module_name inner join asset_link_name as aln on aln.id=hd.link_name  ");
			List fieldNames = Configuration.getColomnList("mapped_help_document_upload_configuration", accountID, connectionSpace,"help_document_upload_configuration");
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
							
							if(object.toString().equalsIgnoreCase("module_name"))
							{
								query.append( "mnd."+object.toString() + ",");
							}
							else if(object.toString().equalsIgnoreCase("link_name"))
							{
								query.append("aln."+ object.toString() + ",");
							}
							
							else
							{
								query.append( "hd."+object.toString() + ",");
							}
							
						}
						else
						{
							query.append("hd."+object.toString());
						}
							
					}
					i++;
				}
				query.append(" " + queryEnd.toString());
			     
				
			/*	select hd.id,mnd.module_name,aln.link_name,userName,date,time  from help_document as hd
				inner join module_name_data as mnd on mnd.id=hd.moduleName
				inner join asset_link_name as aln on aln.id=hd.linkname */
				
				
				/*if (getSearchField() != null && getSearchString() != null
				    && !getSearchField().equalsIgnoreCase("")
				    && !getSearchString().equalsIgnoreCase(""))
				    {
					  if(userType.equalsIgnoreCase("H") || userType.equalsIgnoreCase("N"))
						  query.append(" and ");
					  else
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

				}*/

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
			/*	cbt.checkTableColmnAndAltertable(fieldNames, "vam_idseries_details",
				    connectionSpace);*/

				System.out.println("Query help_document     " + query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
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

									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} 
									else
									{
										if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										} 
										else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
										{
											one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0, 5)));
										} else
										{
											one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
										}
									}
								

							}
						}
						listhb.add(one);
					}
					setIdseriesData(listhb);
					System.out.println("size listhb    "+listhb.size());
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
	
	
	
	
	


public String getModuleLinkMappingData()
{
	String resString = ERROR;
	if (userName == null || userName.equalsIgnoreCase(""))
	{
		return LOGIN;
	}
	try
	{    CommonMethod VCM = new CommonMethod();
	     String userType=(String)session.get("userTpe");
        CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query1 = new StringBuilder("");
		query1.append("select count(*) from help_document");
		List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		if (dataCount != null)
		   {
			// getting the list of colmuns
			StringBuilder query = new StringBuilder("");
			query.append("select ");
			StringBuilder queryEnd = new StringBuilder("");
			queryEnd.append(" from help_document as hd inner join module_name_data as mnd on mnd.id=hd.module_name inner join asset_link_name as aln on aln.id=hd.link_name  ");
			List fieldNames = Configuration.getColomnList("mapped_help_document_configuration", accountID, connectionSpace,"help_document_configuration");
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
							System.out.println("fieldNames   "+fieldNames);
							if(object.toString().equalsIgnoreCase("module_name"))
							{
								query.append( "mnd."+object.toString() + ",");
							}
							else if(object.toString().equalsIgnoreCase("link_name"))
							{
								query.append("aln."+ object.toString() + ",");
							}
							
							else
							{
								query.append( "hd."+object.toString() + ",");
							}
							
						}
						else
						{
							query.append("hd."+object.toString());
						}
							
					}
					i++;
				}
				query.append(" " + queryEnd.toString());
			     
				
			
				
				
				if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("")
				    && !getSearchString().equalsIgnoreCase(""))
				    {
					  		
					System.out.println("getSearchField   "+getSearchField());
					System.out.println("getSearchString   "+getSearchString());
					System.out.println("getSearchOper  "+getSearchOper());
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
			/*	cbt.checkTableColmnAndAltertable(fieldNames, "vam_idseries_details",
				    connectionSpace);*/

				System.out.println("Query help_document     " + query.toString());
				List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
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

									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} 
									else
									{
										if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
										{
											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										} 
										else if (fieldNames.get(k) != null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
										{
											one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0, 5)));
										} else
										{
											one.put(fieldNames.get(k).toString(),DateUtil.makeTitle(obdata[k].toString()));
										}
									}
								

							}
						}
						listhb.add(one);
					}
					setIdseriesData(listhb);
					System.out.println("size listhb    "+listhb.size());
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














	
	
	
	
	
	
	
	
	public String beforeAddingDoc()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			
			
			moduleNames=getModuleNamesData();
			linkNames=getLinkNamesData();
			
			idseriesfieldslist = new ArrayList<ConfigurationUtilBean>();
			idseriesDropDown = new ArrayList<ConfigurationUtilBean>();
			
			
			String userType=(String)session.get("userTpe");
		
			List<GridDataPropertyView> group = Configuration.getConfigurationData("mapped_help_document_upload_configuration", accountID, connectionSpace, "",0, "table_name", "help_document_upload_configuration");
			if (group != null)
			{
				for (GridDataPropertyView gdp : group)
				{
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
					    && !gdp.getColomnName().equalsIgnoreCase("date")
					    && !gdp.getColomnName().equalsIgnoreCase("time")
					    && !gdp.getColomnName().equalsIgnoreCase("userName")
					    && !gdp.getColomnName().equalsIgnoreCase("file_name"))
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
					    )
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
					
				}
			}
			
			
			System.out.println("add form idseriesfieldslist     "+idseriesfieldslist.size());
			System.out.println("add  jjjjjjj  idseriesDropDown   "+idseriesDropDown.size());
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
	
	
	
	
	
	
	
	
	
	
	public String dowloaddocument(){
		String retunrString =ERROR;
		try{
			ServletContext servletContext=ServletActionContext.getServletContext();
			System.out.println("module name   "+modulename);
			System.out.println("linkname   "+linkname);
			if(modulename !="null" && linkname !="null")
			{
				System.out.println("ddd  ");
				String moduleId=getModuleIds(modulename);
				String linkid=getLinkIds(linkname);
				String path=getpath(moduleId,linkid);
				if(path != null)
				{
					File file=new File(path);
					setFilename(file.getName());
					System.out.println("filename  "+filename);
					if(file.exists()){
					/*	PrintWriter writer = new PrintWriter(file.getName(), "UTF-8");
						writer.println("The first line");
						writer.println("The second line");
						writer.close();
						fileInputStream = new FileInputStream(file);*/
						//retunrString= ERROR;
						fileInputStream = new FileInputStream(file);
						retunrString=SUCCESS;
						
					}else{
						retunrString= ERROR;
					}
					
					//File f=new File();
				/*	try{
						fileInputStream = new FileInputStream(file);
					}
					catch(FileNotFoundException e)
					{
						File blank=new File(filename);
						e.printStackTrace();
					}*/
					
					
				}
				
			}
			//fileInputStream = servletContext.getResourceAsStream("/opt/projectdocument/file.txt");
			
			
			
		}catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
		}
		return retunrString;
		
	}
	
	
	public String getModuleIds(String modulename)
	{
		String ids=null;
		List modules = cbt.executeAllSelectQuery("select id from module_name_data where module_name='"+modulename+"'", connectionSpace);
		if (modules != null && modules.size() > 0)
		{
			for (Iterator iterator = modules.iterator(); iterator.hasNext();)
			{
				Object object =  iterator.next();
				if (object != null)
				{
					ids=object.toString();
				}
			}
		}
		System.out.println("ids "+ids);
		return ids;
	}
	public String getLinkIds(String linkname)
	{
		String ids=null;
		List modules = cbt.executeAllSelectQuery("select id from asset_link_name where link_name='"+linkname+"'", connectionSpace);
		if (modules != null && modules.size() > 0)
		{
			for (Iterator iterator = modules.iterator(); iterator.hasNext();)
			{
				Object object =  iterator.next();
				if (object != null)
				{
					ids=object.toString();
				}
			}
		}
		System.out.println("ids "+ids);
		return ids;
	}
	public String getpath(String moduleid,String linkid)
	{
		String ids=null;
		String query = "select help_file from help_document_upload where module_name='"+moduleid+"' and link_name='"+linkid+"'";
		System.out.println("query  "+query);
		List modules = cbt.executeAllSelectQuery(query, connectionSpace);
		
		if (modules != null && modules.size() > 0)
		{
			for (Iterator iterator = modules.iterator(); iterator.hasNext();)
			{
				Object object =  iterator.next();
				if (object != null)
				{
					ids=object.toString();
				}
			}
		}
		System.out.println("path  "+ids);
		return ids;
	}
	
	
	
	
	public String getModuleLinkList()
	{
		String returnresult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				System.out.println("moduleId  "+moduleId);
				String query = "SELECT distinct aln.id,aln.link_name From help_document as hd inner join asset_link_name aln on aln.id=hd.link_name where module_name='" + moduleId + "' ORDER BY link_name";
				System.out.println("query  "+query);
				List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					jsonArray = new JSONArray();
					for (Iterator<?> iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							JSONObject obj = new JSONObject();
							obj.put("ID", object[0].toString());
							obj.put("NAME", object[1].toString());
							jsonArray.add(obj);
						}
					}
				}

				returnresult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnresult = ERROR;
			}
		} else
		{
			return LOGIN;
		}
		return returnresult;
	}
	
	
	public String addHelpData()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;		
			 CommonOperInterface cbt = new CommonConFactory().createInterface();
			 String actionMsg = null;
			 List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_help_document_upload_configuration", accountID, connectionSpace, "", 0, "table_name", "help_document_upload_configuration");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					
					boolean status = false;
					boolean checkstatus=false;
					String modulename=request.getParameter("module_name");
					String linkname=request.getParameter("link_name");
					System.out.println("modulename  "+modulename+"  linkname  "+linkname);
					
		
					
					if (docs != null && docsFileName != null)
					{
						
						String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
						System.out.println("timeStamp   "+timeStamp);
						
						String filePath = new CreateFolderOs().createUserDir("projectdocument");
						System.out.println("path  "+filePath);
						StringBuffer str = new StringBuffer();
						int k = 1;
						for (int i = 0; i < docs.length; i++)
						{
							if (k < docs.length)
							{
								File fileToCreate = new File(filePath + "//"+ docsFileName[i]);
								FileUtils.copyFile(docs[i], fileToCreate);
								//FilesUtil.saveFile(docs[i], docsFileName[i],filePath + File.separator);
								str.append(docsFileName[i]+ ",");
								System.out.println("inside if");
							}
							else
							{
								File fileToCreate = new File(filePath + "//"+"_"+timeStamp+ docsFileName[i]);
								FileUtils.copyFile(docs[i], fileToCreate);
								//FilesUtil.saveFile(docs[i], docsFileName[i],filePath + File.separator);
								str.append(filePath+"/"+"_"+timeStamp+docsFileName[i]);
								k++;
								System.out.println("inside else");
							}
						}
						
						setDocfilename(str.toString().replace("//", "/"));
						System.out.println("docfilename  "+docfilename);
					}
					
					
					checkstatus=checkingexistfile(modulename,linkname);
					System.out.println("checkstatus   "+checkstatus);
					if(checkstatus)
					{
						String query="select module_name,link_name,help_file from help_document_upload where module_name='"+modulename+"' and link_name='"+linkname+"'";
						System.out.println("objArray  query  "+query);
						List data=cbt.executeAllSelectQuery(query, connectionSpace);
						
						if(data !=null && data.size()>0)
						{
							for(Iterator iterator=data.iterator(); iterator.hasNext();)
							{
								Object[] object=(Object[])iterator.next();
								if(object !=null)
								{
									if(object[0] !=null && object[1]!=null && object[2]!=null)
									{
										ob = new InsertDataTable();
										ob.setColName("module_name");
										ob.setDataName(object[0]);
										insertData.add(ob);
										
										ob = new InsertDataTable();
										ob.setColName("link_name");
										ob.setDataName(object[1]);
										insertData.add(ob);
										
										ob = new InsertDataTable();
										ob.setColName("help_file");
										ob.setDataName(object[2]);
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
							ob.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData.add(ob);
							
							
						}
						status = cbt.insertIntoTable("privious_doc_data", insertData, connectionSpace);
						System.out.println("status  "+status);
						
						String updatequery="update help_document_upload set help_file='"+docfilename+"' where module_name='"+modulename+"' and link_name='"+linkname+"'";
						int update=cbt.executeAllUpdateQuery(updatequery, connectionSpace);
						if(update>0)
						{
							addActionMessage("Module Link Mapping added Successfully!!!");
							return SUCCESS;
						}
						else
						{
							addActionMessage(actionMsg);
							return SUCCESS;
						}
						
					}
					else
					{
						System.out.println("false");
						
						ob = new InsertDataTable();
						ob.setColName("module_name");
						ob.setDataName(modulename);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("link_name");
						ob.setDataName(linkname);
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("help_file");
						ob.setDataName(docfilename);
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
						ob.setDataName(DateUtil.getCurrentTimeHourMin());
						insertData.add(ob);

					}	
					
					status = cbt.insertIntoTable("help_document_upload", insertData, connectionSpace);
					

					if (status)
					{
						addActionMessage("Module Link Mapping added Successfully!!!");
						return SUCCESS;
					} else
					{
						addActionMessage(actionMsg);
						return SUCCESS;
					}
				} else
				{
					return ERROR;
				}
			
			
			
		}
		catch (Exception e)
		{

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	
	
	
	public Boolean checkingexistfile(String modulename,String linkname)
	{
		boolean exist=false;
		String query="select * from help_document_upload where module_name='"+modulename+"' and link_name='"+linkname+"'";
		System.out.println("query  "+query);
		List check=cbt.executeAllSelectQuery(query, connectionSpace);
		if(check !=null && check.size()>0)
		{
			exist=true;
		}
		else
		{
			exist=false;
		}
		return exist;
	}
	
	
	public Boolean checkingexistfile4mapping(String modulename,String linkname)
	{
		boolean exist=false;
		String query="select * from help_document where module_name='"+modulename+"' and link_name='"+linkname+"'";
		System.out.println("query  "+query);
		List check=cbt.executeAllSelectQuery(query, connectionSpace);
		if(check !=null && check.size()>0)
		{
			exist=true;
		}
		else
		{
			exist=false;
		}
		return exist;
	}
	
	
	
	public Map<String, String> getLinkNamesData()
	{
		Map<String, String> linklintdata=new HashMap<String, String>();
		List modules = cbt.executeAllSelectQuery("select id, link_name from asset_link_name", connectionSpace);
		if (modules != null && modules.size() > 0)
		{
			for (Iterator iterator = modules.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null && object[1] != null)
					{
						linklintdata.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		System.out.println("sizelinklintdata >>>>>>>>>>>  "+linklintdata.size());
		return linklintdata;
	}
	
	
	
	public Map<String, String> getModuleNamesData()
	{
		Map<String, String> modulelist=new HashMap<String, String>();
		List modules = cbt.executeAllSelectQuery("select id, module_name from module_name_data", connectionSpace);
		if (modules != null && modules.size() > 0)
		{
			for (Iterator iterator = modules.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null && object[1] != null)
					{
						modulelist.put(object[0].toString(), object[1].toString());
					}
				}
			}
		}
		System.out.println("size >>>>>>>>>>>  "+modulelist.size());
		return modulelist;
	}


	
	
	public String modifyHelpDocument()
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
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					
					System.out.println("Parmname   "+Parmname);
					System.out.println("paramValue   "+paramValue);
					
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
					{
						 wherClause.put(Parmname, paramValue);
					}        	
				}
				 if(wherClause.containsKey("oper"));
                 {
                     wherClause.remove("oper");
                 }
                 System.out.println("wherClause   "+wherClause);
				condtnBlock.put("id", getId());
				System.out.println("condtnBlock   "+condtnBlock);
				cbt.updateTableColomn("help_document", wherClause,condtnBlock, connectionSpace);
			} 
			else if (getOper().equalsIgnoreCase("del"))
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
				cbt.deleteAllRecordForId("help_document", "id",condtIds.toString(), connectionSpace);
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
	

	public String modifyuploadgrid()
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
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					
					System.out.println("Parmname   "+Parmname);
					System.out.println("paramValue   "+paramValue);
					
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("")
					    && !Parmname.equalsIgnoreCase("id")
					    && !Parmname.equalsIgnoreCase("oper")
					    && !Parmname.equalsIgnoreCase("rowid"))
					{
						 wherClause.put(Parmname, paramValue);
					}        	
				}
				 if(wherClause.containsKey("oper"));
                 {
                     wherClause.remove("oper");
                 }
                 System.out.println("wherClause   "+wherClause);
				condtnBlock.put("id", getId());
				System.out.println("condtnBlock   "+condtnBlock);
				cbt.updateTableColomn("help_document_upload", wherClause,condtnBlock, connectionSpace);
			} 
			else if (getOper().equalsIgnoreCase("del"))
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
				cbt.deleteAllRecordForId("help_document_upload", "id",condtIds.toString(), connectionSpace);
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
	


	
	
	
	
	
	
	
	
	
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Map<String, String> getModuleNames() {
		return moduleNames;
	}


	public void setModuleNames(Map<String, String> moduleNames) {
		this.moduleNames = moduleNames;
	}

	public File[] getDocs() {
		return docs;
	}

	public void setDocs(File[] docs) {
		this.docs = docs;
	}

	public String[] getDocsFileName() {
		return docsFileName;
	}

	public void setDocsFileName(String[] docsFileName) {
		this.docsFileName = docsFileName;
	}

	public String getDocfilename() {
		return docfilename;
	}

	public void setDocfilename(String docfilename) {
		this.docfilename = docfilename;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	public List<GridDataPropertyView> getViewidseriescolumn() {
		return viewidseriescolumn;
	}
	public void setViewidseriescolumn(List<GridDataPropertyView> viewidseriescolumn) {
		this.viewidseriescolumn = viewidseriescolumn;
	}
	public List<ConfigurationUtilBean> getIdseriesfieldslist() {
		return idseriesfieldslist;
	}
	public void setIdseriesfieldslist(List<ConfigurationUtilBean> idseriesfieldslist) {
		this.idseriesfieldslist = idseriesfieldslist;
	}
	public List<ConfigurationUtilBean> getIdseriesDropDown() {
		return idseriesDropDown;
	}
	public void setIdseriesDropDown(List<ConfigurationUtilBean> idseriesDropDown) {
		this.idseriesDropDown = idseriesDropDown;
	}
	public Map<String, String> getLinkNames() {
		return linkNames;
	}
	public void setLinkNames(Map<String, String> linkNames) {
		this.linkNames = linkNames;
	}
	public List<Object> getIdseriesData() {
		return idseriesData;
	}
	public void setIdseriesData(List<Object> idseriesData) {
		this.idseriesData = idseriesData;
	}



	public List<GridDataPropertyView> getViewdocumentuploadcolumn() {
		return viewdocumentuploadcolumn;
	}



	public void setViewdocumentuploadcolumn(
			List<GridDataPropertyView> viewdocumentuploadcolumn) {
		this.viewdocumentuploadcolumn = viewdocumentuploadcolumn;
	}



	public List<ConfigurationUtilBean> getDocumentuploadfieldslist() {
		return documentuploadfieldslist;
	}



	public void setDocumentuploadfieldslist(
			List<ConfigurationUtilBean> documentuploadfieldslist) {
		this.documentuploadfieldslist = documentuploadfieldslist;
	}



	public List<ConfigurationUtilBean> getDocumentuploadDropDown() {
		return documentuploadDropDown;
	}



	public void setDocumentuploadDropDown(
			List<ConfigurationUtilBean> documentuploadDropDown) {
		this.documentuploadDropDown = documentuploadDropDown;
	}



	public List<Object> getDocumentuploadData() {
		return documentuploadData;
	}



	public void setDocumentuploadData(List<Object> documentuploadData) {
		this.documentuploadData = documentuploadData;
	}



	public JSONArray getJsonArray() {
		return jsonArray;
	}



	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}



	public String getModuleId() {
		return moduleId;
	}



	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}



	public InputStream getFileInputStream() {
		return fileInputStream;
	}



	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}



	public String getModulename() {
		return modulename;
	}



	public void setModulename(String modulename) {
		this.modulename = modulename;
	}



	public String getLinkname() {
		return linkname;
	}



	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}


	
	
	
	
	
	
}
