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

public class EscalationTypeConfig extends GridPropertyBean implements ServletRequestAware{

	
		private HttpServletRequest request;
		private List<Object> masterViewList;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		
		public String beforeConfigEscalation()
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
		
		public String addEscalationConfig()
		{
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try
				{
					String query="SELECT esc_type FROM feedback_escalation_type_config";
					List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							String type=(String) iterator.next();
							if(type.equalsIgnoreCase(request.getParameter("escType")))
							{
								addActionMessage("Opps. Escalation Type is already exist!!!");
								return SUCCESS;
							}
						}
					}	
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					List<TableColumes> tableColume = new ArrayList<TableColumes>();
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname("esc_type");
					ob1.setDatatype("varchar(20)");
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
					
					cbt.createTable22("feedback_escalation_type_config", tableColume, connectionSpace);
					
					ob = new InsertDataTable();
					ob.setColName("esc_type");
					ob.setDataName(request.getParameter("openType"));
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("user_name");
					ob.setDataName(userName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
					
					boolean status=cbt.insertIntoTable("feedback_escalation_type_config", insertData, connectionSpace);
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
		
		public String editEscalationViewConf()
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
						cbt.updateTableColomn("feedback_escalation_type_config", wherClause, condtnBlock,connectionSpace);
					}
					else if(getOper().equalsIgnoreCase("del"))
					{
						cbt.deleteAllRecordForId("feedback_escalation_type_config", "id", getId(), connectionSpace);
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
		
		
		public String beforeEscViewConf()
		{
			System.out.println("EscalationTypeConfig.beforeEscViewConf()");
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				System.out.println("indide esc type");
				return SUCCESS;
			}
			else
			{
				return LOGIN;
			}
		}
		
		public String escTypeViewConf()
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
					query.append("SELECT id,esc_type FROM feedback_escalation_type_config");
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
								one.put("esc_type", (object[1]));
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

