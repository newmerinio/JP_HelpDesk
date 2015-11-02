package com.Over2Cloud.ctrl.newsEvents;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NewsAlertsViewCtrl extends ActionSupport 
{
	private String oper;
	  // get how many rows we want to have into the grid - rowNum attribute in the
	  // grid
	  private Integer             rows             = 0;

	  // Get the requested page. By default grid sets this to 1.
	  private Integer             page             = 0;

	  // sorting order - asc or desc
	  private String              sord;

	  // get index row - i.e. user click to sort.
	  private String              sidx;

	  // Search Field
	  private String              searchField;

	  // The Search String
	  private String              searchString;

	  // Limit the result when using local data, value form attribute rowTotal
	  private Integer             totalrows;

	  // he Search Operation
	  // ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	  private String              searchOper;

	  // Your Total Pages
	  private Integer             total            = 0;

	  // All Records
	  private Integer             records          = 0;
	  private boolean             loadonce         = false;
	  private List<NewsAlertsPojo> newsAlertsList;
	  private String mainHeaderName;
	  private List<Parent> parentTakeaction = null;
		Map session = ActionContext.getContext().getSession();
		String userName = (String) session.get("uName");
		String encryptedID = (String) session.get("encryptedID");
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session .get("connectionSpace");
		 private List<GridDataPropertyView> gridColomns=new ArrayList<GridDataPropertyView>();
			private List<ConfigurationUtilBean> columnText=null;
			private List<ConfigurationUtilBean> columnDate=null;
			 private List<Object> viewNewsAlerts;
			
		public String getAllNewsAlerts()
		{
			System.out.println("getHolidayView ");
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
					query1.append("select count(*) from news_and_alerts ");
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
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						
						//getting the list of colmuns
						StringBuilder query=new StringBuilder("");
						query.append("SELECT ");
						List fieldNames=Configuration.getColomnList("mapped_news_and_alert_configuration", accountID, connectionSpace, "news_and_alert_configuration");
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
						query.append(" FROM news_and_alerts  order by start_date asc  ");
						if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
						{
							query.append(" WHERE ");
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
						//System.out.println(">>>>>>>" +query);
						query.append(" limit "+from+","+to);
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						if(data!=null && data.size()>0)
						{
							viewNewsAlerts=new ArrayList<Object>();
							List<Object> Listhb=new ArrayList<Object>();
							Object[] obdata1=null;
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								obdata1=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata1[k]!=null)
									{
										if(fieldNames.get(k).toString().equals("start_date") || fieldNames.get(k).toString().equals("end_date") || fieldNames.get(k).toString().equals("date"))
										{
												one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata1[k].toString()));
										}
										else{
											if(k==0)
												one.put(fieldNames.get(k).toString(), (Integer)obdata1[k]);
											else
												one.put(fieldNames.get(k).toString(), obdata1[k].toString());
										}	
									}
								}
								Listhb.add(one);
							}
							setViewNewsAlerts(Listhb);
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
			System.out.println("returnResult");
			return returnResult;
		
			
			
			
			/*
			System.out.println("NewsAlertsViewCtrl.getAllNewsAlerts()");
			boolean valid=ValidateSession.checkSession();
			if(valid)
			{
				try
				{
					Map session = ActionContext.getContext().getSession();
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					
					newsAlertsList=new ArrayList<NewsAlertsPojo>();
					
					StringBuffer buffer=new StringBuffer("select * from news_and_alerts order by id desc");
					List data=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
					if(data!=null && data.size()>0)
					{
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object!=null)
							{
								NewsAlertsPojo newsPojo=new NewsAlertsPojo();
								if(object[0]!=null)
								{
									newsPojo.setId(Integer.parseInt(object[0].toString()));
								}
								
								if(object[1]!=null)
								{
									newsPojo.setName(object[1].toString());
								}
								
								if(object[2]!=null)
								{
									newsPojo.setDesc(object[2].toString());
								}
								
								if(object[3]!=null)
								{
									newsPojo.setStartDate(DateUtil.convertDateToIndianFormat(object[3].toString()));
								}
								
								if(object[4]!=null)
								{
									newsPojo.setEndDate(DateUtil.convertDateToIndianFormat(object[4].toString()));
								}
								
								if(object[5]!=null)
								{
									newsPojo.setAddedBy(object[5].toString());
								}
								
								if(object[6]!=null)
								{
									newsPojo.setAddedOn(DateUtil.convertDateToIndianFormat(object[6].toString()));
								}
								
								if(object[7]!=null)
								{
									newsPojo.setAddedAt(object[7].toString());
								}
								
								newsAlertsList.add(newsPojo);
							}
						}
					}
					
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					setRecords(newsAlertsList.size());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					return SUCCESS;
				}
				catch (Exception e) {
					e.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		*/}
		
		
		 public String viewNewsAlertList()
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
						
						setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
						setGridColomnNames();
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
		
		
	 /* public String beforeNewsEventsAdd()
		{
		  System.out.println("NewsAlertsViewCtrl.beforeNewsEventsAdd()");
			boolean valid=ValidateSession.checkSession();
			if(valid)
			{
				try
				{
					setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
					return SUCCESS;
				}
				catch (Exception e) {
					e.printStackTrace();
					return ERROR;
				}
			}
			else
			{
				return LOGIN;
			}
		}
	  */
	  public String beforeNewsEventsAdd()
		{
	     String returnResult=ERROR;
			boolean sessionFlag=ValidateSession.checkSession();
			if(sessionFlag)
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				try
				{
					setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
					String accountID=(String)session.get("accountid");
					List<GridDataPropertyView> requestColumnList=Configuration.getConfigurationData("mapped_news_and_alert_configuration",accountID,connectionSpace,"",0,"table_name","news_and_alert_configuration");
					columnText=new ArrayList<ConfigurationUtilBean>();
					columnDate=new ArrayList<ConfigurationUtilBean>();
					if(requestColumnList!=null&&requestColumnList.size()>0)
					{
						for(GridDataPropertyView  gdp:requestColumnList)
						{
							ConfigurationUtilBean cub=new ConfigurationUtilBean();
							if(gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("userName")
									&& !gdp.getColomnName().equalsIgnoreCase("date")&& !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getHideOrShow().equalsIgnoreCase("true"))
							{
								cub.setKey(gdp.getColomnName());
								cub.setValue(gdp.getHeadingName());
								cub.setColType(gdp.getColType());
								cub.setValidationType(gdp.getValidationType());
								if(gdp.getMandatroy().equalsIgnoreCase("1")){
									cub.setMandatory(true);
								}else{
									cub.setMandatory(false);
								}
							columnText.add(cub);
							}
							else if(gdp.getColType().equalsIgnoreCase("Date")&& !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getHideOrShow().equalsIgnoreCase("true"))
							{
								cub.setKey(gdp.getColomnName());
								cub.setValue(gdp.getHeadingName());
								cub.setColType(gdp.getColType());
								cub.setValidationType(gdp.getValidationType());
								if(gdp.getMandatroy().equalsIgnoreCase("1")){
									cub.setMandatory(true);
								}else{
									cub.setMandatory(false);
								}
								columnDate.add(cub);
							}
						}
						returnResult=SUCCESS;
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
	  public String viewnews_and_alerts()
	  {
	        System.out.println("NewsAlertsViewCtrl.viewnews_and_alerts()");
			boolean sessionFlag = ValidateSession.checkSession();
			if(sessionFlag)
			{
				try
				{
					setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
				    newsAlertsList=new FeedbackDashboardDao().getDashboardAlertsList("News", connectionSpace);
				
			//	    System.out.println("News and Alerts Size is as :::"+getNewsAlertsList().size());
				    
				    return SUCCESS;
			} 
			catch (Exception e)
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
	  
	  public String editNewsndevents()
	  {
		  

			try
			{
				if(userName==null || userName.equalsIgnoreCase(""))
					return LOGIN;
				if(getOper().equalsIgnoreCase("edit"))
				{
				}
				

				if(getOper().equalsIgnoreCase("del"))
				{}
				
				
			}
			catch(Exception e)
			{
				 e.printStackTrace();
			}
			return SUCCESS;
		
		  
	  }
	  
	  public void setGridColomnNames()
		{
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			gridColomns.add(gpv);
			try
			{
			List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_news_and_alert_configuration",accountID,connectionSpace,"",0,"table_name","news_and_alert_configuration");
			
			if(statusColName!=null&&statusColName.size()>0)
			{
				for(GridDataPropertyView gdp:statusColName)
				{
					if(!gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("comment") && !gdp.getColomnName().equalsIgnoreCase("empid") && !gdp.getColomnName().equalsIgnoreCase("name") && !gdp.getColomnName().equalsIgnoreCase("email") && !gdp.getColomnName().equalsIgnoreCase("mobno")){
					gpv=new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gridColomns.add(gpv);
				}
				}
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("holidayGridColomns          "+gridColomns.size());
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
	public Integer getTotalrows() {
		return totalrows;
	}
	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
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
	public List<NewsAlertsPojo> getNewsAlertsList() {
		return newsAlertsList;
	}
	public void setNewsAlertsList(List<NewsAlertsPojo> newsAlertsList) {
		this.newsAlertsList = newsAlertsList;
	}



	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public List<Parent> getParentTakeaction() {
		return parentTakeaction;
	}
	public void setParentTakeaction(List<Parent> parentTakeaction) {
		this.parentTakeaction = parentTakeaction;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}



	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public List<GridDataPropertyView> getGridColomns() {
		return gridColomns;
	}
	public void setGridColomns(List<GridDataPropertyView> gridColomns) {
		this.gridColomns = gridColomns;
	}


	public List<ConfigurationUtilBean> getColumnText() {
		return columnText;
	}


	public void setColumnText(List<ConfigurationUtilBean> columnText) {
		this.columnText = columnText;
	}


	public List<ConfigurationUtilBean> getColumnDate() {
		return columnDate;
	}


	public void setColumnDate(List<ConfigurationUtilBean> columnDate) {
		this.columnDate = columnDate;
	}


	public List<Object> getViewNewsAlerts() {
		return viewNewsAlerts;
	}


	public void setViewNewsAlerts(List<Object> viewNewsAlerts) {
		this.viewNewsAlerts = viewNewsAlerts;
	}
	
	
}