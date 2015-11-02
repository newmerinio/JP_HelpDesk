package com.Over2Cloud.ctrl.compliance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.emailSetting.EmailSetting;

public class MorningReportAction extends GridPropertyBean implements ServletRequestAware
{

	private HttpServletRequest request;
	private Map<String, String>	appDetails;
	private List<Object> masterViewList;
	private String report_date;
	private String report_time;
	
	public String beforeCreateMorningReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				appDetails = new LinkedHashMap<String, String>();
				appDetails=CommonWork.fetchAppAssignedUser(connectionSpace,userName);
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
	
	
	public String configMorningReport()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);

				ob1 = new TableColumes();
				ob1.setColumnname("time");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("moduleName");
				ob1.setDatatype("varchar(255)");
				Tablecolumesaaa.add(ob1);
				new createTable().createTable22("compliance_todayTask_config", Tablecolumesaaa, connectionSpace);
				
				
				
				String[] moduleName = request.getParameterValues("moduleName");
				if(moduleName!=null && moduleName.length>0)
				{
					for (int j = 0; j < moduleName.length; j++)
					{
						ob = new InsertDataTable();
						ob.setColName("date");
						ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("date")));
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(request.getParameter("time"));
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("moduleName");
						ob.setDataName(moduleName[j].toString());
						insertData.add(ob);
						
						new createTable().executeAllUpdateQuery("DELETE FROM compliance_todayTask_config WHERE moduleName='"+moduleName[j].toString()+"'", connectionSpace);
						new createTable().insertIntoTable("compliance_todayTask_config", insertData, connectionSpace);
						
						insertData.clear();
					}
				}
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				addActionMessage("Opps. problem in data save");
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String viewMorningReportData()
	{
		System.out.println("efsfvbsdjbvjsdfj");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				query.append("SELECT id,date,time,moduleName FROM compliance_todayTask_config");
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] object = null;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						object = (Object[]) it.next();
						Map<String, Object> one=new LinkedHashMap<String, Object>();
						
						if(object[0]!=null)
							one.put("id",object[0].toString());
						else 
							one.put("id","NA");
						
						if(object[1]!=null)
							one.put("date",DateUtil.convertDateToIndianFormat(object[1].toString()));
						else 
							one.put("date","NA");
						
						if(object[2]!=null)
							one.put("time",object[2].toString());
						else 
							one.put("time","NA");
						
						if(object[3]!=null)
							one.put("moduleName",new EmailSetting().getModulename(object[3].toString()));
						else 
							one.put("moduleName","NA");
						
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	public String modifyMorningReportData()
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
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id")
								&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							if(Parmname.equals("date"))
								wherClause.put(Parmname, DateUtil.convertDateToUSFormat(paramValue));
							else
								wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("compliance_todayTask_config", wherClause, condtnBlock, connectionSpace);
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
					StringBuilder query = new StringBuilder("DELETE FROM compliance_todayTask_config  WHERE id IN(" + condtIds + ")");
					cbt.updateTableColomn(connectionSpace, query);
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
				returnResult = ERROR;
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
		this.request= request;
		
	}

	public Map<String, String> getAppDetails() {
		return appDetails;
	}

	public void setAppDetails(Map<String, String> appDetails) {
		this.appDetails = appDetails;
	}


	public List<Object> getMasterViewList() {
		return masterViewList;
	}


	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}


	public String getReport_date() {
		return report_date;
	}


	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}


	public String getReport_time() {
		return report_time;
	}


	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}
	

}
