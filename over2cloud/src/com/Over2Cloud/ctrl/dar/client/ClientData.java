package com.Over2Cloud.ctrl.dar.client;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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

  public class ClientData extends ActionSupport implements ServletRequestAware
	{
		static final Log log = LogFactory.getLog(ClientData.class);
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		Map session = ActionContext.getContext().getSession();
	    private HttpServletRequest request;
		//for DAR modules
		private String pageHeader;
		private List<ConfigurationUtilBean> clienttypeColumnMap=null;
		private List<Object> viewList;
		private String mainHeaderName;
		private List<GridDataPropertyView>clientViewProp=new ArrayList<GridDataPropertyView>();
		// Get the requested page. By default grid sets this to 1.
		private Integer rows = 0;
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
		//Grid colomn view
		private String oper;
		private String id;
		
		public String execute()
		{
			try
			{
				String userName=(String)session.get("uName");
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			return SUCCESS;
		}
		
		public String beforeAddClientType()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
				    String accountID=(String)session.get("accountid");
				    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					List<GridDataPropertyView> taskColumnList=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","client_data_configuration");
					clienttypeColumnMap=new ArrayList<ConfigurationUtilBean>();
					if(taskColumnList!=null&&taskColumnList.size()>0)
					{
						ConfigurationUtilBean obj=null;
						for(GridDataPropertyView  gdp:taskColumnList)
						{
							obj=new ConfigurationUtilBean();

							if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName"))
							{
								obj.setValue(gdp.getHeadingName());
								obj.setKey(gdp.getColomnName());
								obj.setValidationType(gdp.getValidationType());
								obj.setColType(gdp.getColType());
								if(gdp.getMandatroy().toString().equals("1"))
								{
									obj.setMandatory(true);
								}
								else
								{
									obj.setMandatory(false);
								}
								clienttypeColumnMap.add(obj);
							}
						}
						returnResult=SUCCESS;
					}
				}
				catch(Exception e)
				{
					 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in Over2Cloud  method createKRAKPI of class "+getClass(), e);
					e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String clientAdd()
		{
		try
		{
			String userName=(String)session.get("uName");
		    String accountID=(String)session.get("accountid");
		    SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			 CommonOperInterface cbt=new CommonConFactory().createInterface();
			 List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","client_data_configuration");
				if(org2!=null)
				{
					//create table query based on configuration
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					TableColumes ob1=null;
					for(GridDataPropertyView gdp:org2)
					{
						 ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 Tablecolumesaaa.add(ob1);
						 if(gdp.getColomnName().equalsIgnoreCase("userName"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("time"))
							 timeTrue=true;
						 
					}
					cbt.createTable22("client_type",Tablecolumesaaa,connectionSpace);
					
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					 Iterator it = requestParameterNames.iterator();
					 while (it.hasNext()) {
							String Parmname = it.next().toString();
							String paramValue=request.getParameter(Parmname);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& Parmname!=null)
								{
								 ob=new InsertDataTable();
								 ob.setColName(Parmname);
								 ob.setDataName(paramValue);
								 insertData.add(ob);
								}
					 }
					 if(userTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("userName");
						 ob.setDataName(userName);
						 insertData.add(ob);
					 }
					 if(dateTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("date");
						 ob.setDataName(DateUtil.getCurrentDateUSFormat());
						 insertData.add(ob);
					 }
					 if(timeTrue)
					 {
						 ob=new InsertDataTable();
						 ob.setColName("time");
						 ob.setDataName(DateUtil.getCurrentTime());
						 insertData.add(ob);
					 }
					 if(insertData.size()>2)//when their is atleast one data
					 {
						 status=cbt.insertIntoTable("client_type",insertData,connectionSpace);
					 }
					 if(status)
					 {
						 addActionMessage("Client Registered Successfully!!!");
						 return SUCCESS;
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!");
						 return SUCCESS;
					 }
				}
		}
		catch(Exception e)
		{
			 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in clientAdd method  of class "+getClass(), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
		}

		public void setClientViewProp()
		 {
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			clientViewProp.add(gpv);
			try
			{
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_dar_configuration",accountID,connectionSpace,"",0,"table_name","client_data_configuration");
			if(statusColName!=null&&statusColName.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName)
				{
					gpv=new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					clientViewProp.add(gpv);
				}
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public String beforeClientView()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					String userName=(String)session.get("uName");
					if(userName==null || userName.equalsIgnoreCase(""))
						return LOGIN;
			
					setMainHeaderName("Client Type Master >> View ");
					setClientViewProp();
					returnResult=SUCCESS;		
				}
				catch(Exception e)
				{
					returnResult=ERROR;
					 e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
	}
		
		@SuppressWarnings("rawtypes")
		public String viewClientOperation()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					String accountID=(String)session.get("accountid");
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					StringBuilder query1=new StringBuilder("");
					query1.append("select count(*) from client_type");
					List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
					if(dataCount!=null&&dataCount.size()>0)
					{
						BigInteger count=new BigInteger("1");
						Object obdata=null;
						for(Iterator it=dataCount.iterator(); it.hasNext();)
						{
							 obdata=(Object)it.next();
							 count=(BigInteger)obdata;
						}
						setRecords(count.intValue());
						int to = (getRows() * getPage());
						if (to > getRecords())
							to = getRecords();
						
						//getting the list of colmuns
						StringBuilder query=new StringBuilder("");
						query.append("select ");
						List fieldNames=Configuration.getColomnList("mapped_dar_configuration", accountID, connectionSpace, "client_data_configuration");
						int i=0;
						if(fieldNames!=null&&fieldNames.size()>0)
						{
							Object obdata1=null;
							for(Iterator it=fieldNames.iterator(); it.hasNext();)
							{
								//generating the dyanamic query based on selected fields
								    obdata1=(Object)it.next();
								    if(obdata1!=null)
								    {
									    if(i<fieldNames.size()-1)
									    	query.append(obdata1.toString()+",");
									    else
									    	query.append(obdata1.toString());
								    }
								    i++;
							}
						}
						query.append(" from client_type");
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" where ");
							//add search  query based on the search operation
							if(getSearchOper().equalsIgnoreCase("eq"))
							{
								query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("cn"))
							{
								query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("bw"))
							{
								query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
							}
							else if(getSearchOper().equalsIgnoreCase("ne"))
							{
								query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
							}
							else if(getSearchOper().equalsIgnoreCase("ew"))
							{
								query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
							}
						}
						else if(getSord()!=null && getSidx()!=null && !getSord().equalsIgnoreCase("") && !getSidx().equalsIgnoreCase(""))
						{
							query.append(" ORDER BY "+getSidx()+" "+getSord()+"");
						}
						//query.append(" limit "+from+","+to);
						
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				
						if(data.size()>0)
						{
							viewList=new ArrayList<Object>();
							List<Object> Listhb=new ArrayList<Object>();
							Object[] obdata1=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								obdata1=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata1[k]!=null)
									{
											if(k==0)
												one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
											else
												one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}
								}
								Listhb.add(one);
							}
							setViewList(Listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
					returnResult=SUCCESS;
				}
				catch(Exception e)
				{
					returnResult=ERROR;
					e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String modifyClient()
		{
			String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					if(getOper().equalsIgnoreCase("edit"))
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						Map<String, Object>wherClause=new HashMap<String, Object>();
						Map<String, Object>condtnBlock=new HashMap<String, Object>();
						List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
						Collections.sort(requestParameterNames);
						Iterator it = requestParameterNames.iterator();
						while (it.hasNext())
						{
							String parmName = it.next().toString();
							String paramValue=request.getParameter(parmName);
							if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
									&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
								wherClause.put(parmName, paramValue);
						}
						condtnBlock.put("id",getId());
						cbt.updateTableColomn("client_type", wherClause, condtnBlock,connectionSpace);
					}
					else if(getOper().equalsIgnoreCase("del"))
					{
						CommonOperInterface cbt=new CommonConFactory().createInterface();
						String tempIds[]=getId().split(",");
						StringBuilder condtIds=new StringBuilder();
							int i=1;
							for(String H:tempIds)
							{
								if(i<tempIds.length)
									condtIds.append(H+" ,");
								else
									condtIds.append(H);
								i++;
							}
							cbt.deleteAllRecordForId("client_type", "id", condtIds.toString(), connectionSpace);
					}
				}
				catch(Exception e)
				{
					returnResult=ERROR;
					 e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		}

		public String getPageHeader() {
			return pageHeader;
		}
		public void setPageHeader(String pageHeader) {
			this.pageHeader = pageHeader;
		}
		
		public List<ConfigurationUtilBean> getClienttypeColumnMap() {
			return clienttypeColumnMap;
		}
		public void setClienttypeColumnMap(
				List<ConfigurationUtilBean> clienttypeColumnMap) {
			this.clienttypeColumnMap = clienttypeColumnMap;
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
		public boolean isLoadonce() {
			return loadonce;
		}
		public void setLoadonce(boolean loadonce) {
			this.loadonce = loadonce;
		}
		public String getOper() {
			return oper;
		}
		public void setOper(String oper) {
			this.oper = oper;
		}
		public String getMainHeaderName() {
			return mainHeaderName;
		}
		public void setMainHeaderName(String mainHeaderName) {
			this.mainHeaderName = mainHeaderName;
		}
	
		public List<GridDataPropertyView> getClientViewProp() {
			return clientViewProp;
		}
		public void setClientViewProp(List<GridDataPropertyView> clientViewProp) {
			this.clientViewProp = clientViewProp;
		}
		
		public List<Object> getViewList() {
			return viewList;
		}
		public void setViewList(List<Object> viewList) {
			this.viewList = viewList;
		}
		@Override
		public void setServletRequest(HttpServletRequest request) {
			this.request = request;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
	
	}
