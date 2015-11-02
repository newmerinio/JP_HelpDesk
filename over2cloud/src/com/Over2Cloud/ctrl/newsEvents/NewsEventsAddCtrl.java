package com.Over2Cloud.ctrl.newsEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
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

@SuppressWarnings("serial")
public class NewsEventsAddCtrl extends ActionSupport implements ServletRequestAware
{
	private String mainHeaderName;
	private String name;
	private String description;
	private String startDate;
	private String endDate;
	private String byDept;
	private String showFlag;
	private String severity;
	
	private List<ConfigurationUtilBean> columnText=null;
	private List<ConfigurationUtilBean> columnDate=null;
	private HttpServletRequest request;
	private Map<Integer,String> deptList;
	private Map session = ActionContext.getContext().getSession();
	
	private List<NewsAlertsPojo> newsAlertsList;
	public String beforeNewsEventsAdd()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				setMainHeaderName("News and Events >> Add");
				deptList=new NewsAlertsHelper().getAllDepts(connectionSpace);
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
				}
				
				
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
	
	public String addAlertsAndNews()
	{
		String returnResult=ERROR;
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=(String)session.get("uName");
				String accountID=(String)session.get("accountid");
				
				
				List<GridDataPropertyView> statusColName=Configuration.getConfigurationData("mapped_news_and_alert_configuration",accountID,connectionSpace,"",0,"table_name","news_and_alert_configuration");
				if(statusColName!=null && statusColName.size()>0)
				{
					//create table query based on configuration
					List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
					InsertDataTable ob=null;
					boolean status=false;
					List <TableColumes> tableColume=new ArrayList<TableColumes>();
					boolean userTrue=false;
					boolean dateTrue=false;
					boolean timeTrue=false;
					for(GridDataPropertyView gdp:statusColName)
					{
						 TableColumes ob1=new TableColumes();
				         ob1=new TableColumes();
						 ob1.setColumnname(gdp.getColomnName());
						 ob1.setDatatype("varchar(255)");
						 ob1.setConstraint("default NULL");
						 tableColume.add(ob1);
						 if(gdp.getColomnName().equalsIgnoreCase("user_name"))
							 userTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("created_date"))
							 dateTrue=true;
						 else if(gdp.getColomnName().equalsIgnoreCase("created_time"))
							 timeTrue=true;
					}
					 cbt.createTable22("news_and_alerts",tableColume,connectionSpace);
					 @SuppressWarnings("unchecked")
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					 if(requestParameterNames!=null && requestParameterNames.size()>0)
					 {
						 Collections.sort(requestParameterNames);
						 Iterator it = requestParameterNames.iterator();
						 while (it.hasNext()) {
								String parmName = it.next().toString();
								String paramValue=request.getParameter(parmName);
								
								/*System.out.println(" parmName "+parmName);
								System.out.println(" paramValue "+paramValue);*/
								if(paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null && (parmName.equalsIgnoreCase("start_date") || parmName.equalsIgnoreCase("end_date")))
									{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(DateUtil.convertDateToUSFormat(paramValue));
									 insertData.add(ob);
									 }
								else if (paramValue!=null && !paramValue.equalsIgnoreCase("")&& parmName!=null && parmName.equalsIgnoreCase("department") ){
									String deptName=new NewsAlertsHelper().getDeptNameFromId(connectionSpace, getByDept());
									ob=new InsertDataTable();
									ob.setColName(parmName);
									if(deptName!=null)
									{
										ob.setDataName(""+deptName+":   "+getDescription());
									}
									else
									{
										ob.setDataName(getDescription());
									}
									 insertData.add(ob);
								     }
								else
								{
									 ob=new InsertDataTable();
									 ob.setColName(parmName);
									 ob.setDataName(DateUtil.makeTitle(paramValue));
									 insertData.add(ob);
								}
									}
								   }
						 if(userTrue)
						 {
							 ob=new InsertDataTable();
							 ob.setColName("user_name");
							 ob.setDataName(DateUtil.makeTitle(userName));
							 insertData.add(ob);
						 }
						 if(dateTrue)
						 {
							 ob=new InsertDataTable();
							 ob.setColName("created_date");
							 ob.setDataName(DateUtil.getCurrentDateUSFormat());
							 insertData.add(ob);
						 }
						 if(timeTrue)
						 {
							 ob=new InsertDataTable();
							 ob.setColName("created_time");
							 ob.setDataName(DateUtil.getCurrentTimeHourMin());
							 insertData.add(ob);
						 }
						 ob=new InsertDataTable();
						 ob.setColName("status");
						 ob.setDataName("Active");
						 insertData.add(ob);
						 
						 
					 status=cbt.insertIntoTable("news_and_alerts",insertData,connectionSpace); 
					 if(status)
					 {
						 addActionMessage("Data Save Successfully!!!");
						 returnResult=SUCCESS;
					 } 
					 }
					 else
					 {
						 addActionMessage("Oops There is some error in data!!!!");
						 returnResult=ERROR;
					 }
			}
			catch (Exception e) {
				e.printStackTrace();
				returnResult= ERROR;
			}
		}
		return returnResult;
		
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<NewsAlertsPojo> getNewsAlertsList() {
		return newsAlertsList;
	}
	public void setNewsAlertsList(List<NewsAlertsPojo> newsAlertsList) {
		this.newsAlertsList = newsAlertsList;
	}
	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}
	public String getByDept() {
		return byDept;
	}
	public void setByDept(String byDept) {
		this.byDept = byDept;
	}
	public String getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
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

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}