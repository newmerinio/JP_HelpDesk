package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
public class TicketConfigAction extends GridPropertyBean implements ServletRequestAware
{
	private HttpServletRequest request;
	private List<Object> masterViewList;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	public String beforeConfigTicket()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
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
	
	public String addTicketConfig()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String query="SELECT open_type FROM feedback_ticket_config_view";
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						String type=(String) iterator.next();
						if(type.equalsIgnoreCase(request.getParameter("openType")))
						{
							addActionMessage("Opps. Open Type is already exist!!!");
							return SUCCESS;
						}
					}
				}	
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<TableColumes> tableColume = new ArrayList<TableColumes>();
				TableColumes ob1 = new TableColumes();
				ob1.setColumnname("open_type");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("min_rating");
				ob1.setDatatype("varchar(2)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("neg_rating");
				ob1.setDatatype("varchar(3)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("recommend");
				ob1.setDatatype("varchar(3)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
			 
				ob1 = new TableColumes();
				ob1.setColumnname("user_name");
				ob1.setDatatype("varchar(30)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("date");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				tableColume.add(ob1);
				
				cbt.createTable22("feedback_ticket_config_view", tableColume, connectionSpace);
				
				ob = new InsertDataTable();
				ob.setColName("open_type");
				ob.setDataName(request.getParameter("openType"));
				insertData.add(ob);
				 
				ob = new InsertDataTable();
				ob.setColName("min_rating");
				ob.setDataName(request.getParameter("minRating"));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("neg_rating");
				ob.setDataName(request.getParameter("negRating"));
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("recommend");
				ob.setDataName(request.getParameter("recommend"));
				insertData.add(ob);
			
				ob = new InsertDataTable();
				ob.setColName("user_name");
				ob.setDataName(userName);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				boolean status=cbt.insertIntoTable("feedback_ticket_config_view", insertData, connectionSpace);
				if(status)
				{	
					addActionMessage("Data saved sucessfully.");
					return SUCCESS;
				}
				else
				{
					addActionMessage("Opps. There are some problem in data save!!!");
					return ERROR;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				addActionMessage("Opps. There are some problem in data save!!!");
				return ERROR;
				
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String editTicketViewConf()
	{
		System.out.println(">>>>>edit>>>>>");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if(getOper().equalsIgnoreCase("edit"))
				{
					System.out.println(">>>>>edit>>>>>");
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) 
					{
						String Parmname = it.next().toString();
						String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("feedback_ticket_config_view", wherClause, condtnBlock,connectionSpace);
				}
				else if(getOper().equalsIgnoreCase("del"))
				{
					cbt.deleteAllRecordForId("feedback_ticket_config_view", "id", getId(), connectionSpace);
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
	
	
	public String beforeTicketViewConf()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String ticketViewConf()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb=new ArrayList<Object>();
				List dataList = null;
				StringBuilder query = new StringBuilder();
				query.append("SELECT id,open_type,min_rating,neg_rating,recommend FROM feedback_ticket_config_view");
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", (object[0]));
						if(object[1]!=null)
						{
							one.put("open_type", (object[1]));
						}
						
						if(object[2]!=null && !object[2].equals("-1"))
						{
							one.put("min_rating", (object[2]));
						}
						else
						{
							one.put("min_rating", "NA");
						}
						if(object[3]!=null && !object[3].equals("-1"))
						{
							one.put("neg_rating", (object[3]));
						}
						else
						{
							one.put("neg_rating", "NA");
						}
						if(object[4]!=null && !object[4].equals("-1"))
						{
							one.put("recommend", (object[4]));
						}
						else
						{
							one.put("recommend", "NA");
						}
						
						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

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

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}

	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
}
