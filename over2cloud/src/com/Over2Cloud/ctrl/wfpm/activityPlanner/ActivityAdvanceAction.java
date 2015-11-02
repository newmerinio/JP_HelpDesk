package com.Over2Cloud.ctrl.wfpm.activityPlanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
@SuppressWarnings("serial")
public class ActivityAdvanceAction extends GridPropertyBean
{
   private List<Object> masterViewList;
   private List<ActivityPojo> commonList;
   private List<ActivityPojo> commonList2;
   private int sum =0;
   private int sum2  =0;
private String amt;
private double finalAmt;
   
   @SuppressWarnings("unchecked")
   public String advanceView()
   {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("sc.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("country"))
							{
								query.append("co.country_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("state"))
							{
								query.append("s.state_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("city"))
							{
								query.append("c.city_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("territory"))
							{
								query.append("tt.trr_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("rel_type"))
							{
								query.append("type.name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("schedule_contact_id"))
							{
								query.append("pc.emp_name,");
							}
							else
							{
								query.append("sc."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							query.append("sc."+gpv.getColomnName().toString());
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_plan as sc");
				query.append(" LEFT JOIN territory as tt on tt.id=sc.territory ");
				query.append(" LEFT JOIN city as c on c.id=tt.trr_city ");
				query.append(" LEFT JOIN state as s on s.id=c.name_state ");
				query.append(" LEFT JOIN country as co on co.id=s.country_code ");
				query.append(" LEFT JOIN relationship as type on type.id=sc.rel_type ");
				query.append(" LEFT JOIN manage_contact as mc on mc.id=sc.schedule_contact_id ");
				query.append(" LEFT JOIN primary_contact as pc on pc.id=mc.emp_id ");
				query.append(" LEFT JOIN patientcrm_status_data as escData on escData.id=sc.activity_type ");
				query.append(" LEFT JOIN activity_type as at on at.id=escData.status WHERE ");
				
				query.append("  sc.activity_flag = '2' AND sc.advance_required='Yes' ");
				query.append(" ORDER BY sc.for_month ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					ActivityPlannerAction AA=new ActivityPlannerAction();
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("rel_sub_type"))
								{
									if( obdata[k].toString().equalsIgnoreCase("referral_contact_data"))
									{
										one.put(gpv.getColomnName(), "Individual");
									}
									else
									{
										one.put(gpv.getColomnName(), "Institutional");
									}
								}
								else if(gpv.getColomnName().equalsIgnoreCase("sch_from"))
								{
									one.put(gpv.getColomnName(),  obdata[k].toString()+" To "+ obdata[k+1].toString());
								}
								else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
								}
							}
							else
							{
								one.put(gpv.getColomnName().toString(), "NA");
							}
							k++;
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
   @SuppressWarnings("unchecked")
   public String advanceViewSettlement()
   {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query = new StringBuilder("");
				List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				query.append("SELECT ");

				int i = 0;
				if (fieldNames != null && fieldNames.size() > 0)
				{
					for (GridDataPropertyView gpv : fieldNames)
					{
						if (i < (fieldNames.size() - 1))
						{
							if (gpv.getColomnName().equalsIgnoreCase("id"))
							{
								query.append("sc.id,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("country"))
							{
								query.append("co.country_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("state"))
							{
								query.append("s.state_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("city"))
							{
								query.append("c.city_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("territory"))
							{
								query.append("tt.trr_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("rel_type"))
							{
								query.append("type.name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("activity_type"))
							{
								query.append("at.act_name,");
							}
							else if (gpv.getColomnName().equalsIgnoreCase("schedule_contact_id"))
							{
								query.append("pc.emp_name,");
							}
							else
							{
								query.append("sc."+gpv.getColomnName().toString() + ",");
							}
						}
						else
						{
							query.append("sc."+gpv.getColomnName().toString());
						}
						i++;
					}
				}
				query.append(" FROM activity_schedule_plan as sc ");
				query.append(" LEFT JOIN activity_schedule_dsr_fill as dsc on dsc.activityId = sc.id ");
				query.append(" LEFT JOIN territory as tt on tt.id=sc.territory ");
				query.append(" LEFT JOIN city as c on c.id=tt.trr_city ");
				query.append(" LEFT JOIN state as s on s.id=c.name_state ");
				query.append(" LEFT JOIN country as co on co.id=s.country_code ");
				query.append(" LEFT JOIN relationship as type on type.id=sc.rel_type ");
				query.append(" LEFT JOIN manage_contact as mc on mc.id=sc.schedule_contact_id ");
				query.append(" LEFT JOIN primary_contact as pc on pc.id=mc.emp_id ");
				query.append(" LEFT JOIN patientcrm_status_data as escData on escData.id=sc.activity_type ");
				query.append(" LEFT JOIN activity_type as at on at.id=escData.status WHERE ");
				query.append("  dsc.dsr_flag = '1'  AND dsc.manager_status = 'Approved' ");
				query.append(" ORDER BY sc.for_month ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					ActivityPlannerAction AA=new ActivityPlannerAction();
					setRecords(data.size());
					int to = (getRows() * getPage());
					if (to > getRecords())
						to = getRecords();
					Object[] obdata = null;
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						int k = 0;
						obdata = (Object[]) it.next();
						Map<String, Object> one = new HashMap<String, Object>();
						for (GridDataPropertyView gpv : fieldNames)
						{
							if (obdata[k] != null)
							{
								if(gpv.getColomnName().equalsIgnoreCase("for_month"))
								{
									one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("activity_for"))
								{
									one.put(gpv.getColomnName(), AA.fetchActivityFor(obdata[k-1].toString(), obdata[k].toString()));
								}
								else if(gpv.getColomnName().equalsIgnoreCase("rel_sub_type"))
								{
									if( obdata[k].toString().equalsIgnoreCase("referral_contact_data"))
									{
										one.put(gpv.getColomnName(), "Individual");
									}
									else
									{
										one.put(gpv.getColomnName(), "Institutional");
									}
								}
								else if(gpv.getColomnName().equalsIgnoreCase("sch_from"))
								{
									one.put(gpv.getColomnName(),  obdata[k].toString()+" To "+ obdata[k+1].toString());
								}
								else
								{
									one.put(gpv.getColomnName(), obdata[k].toString());
								}
							}
							else
							{
								one.put(gpv.getColomnName().toString(), "NA");
							}
							k++;
						}
						Listhb.add(one);
					}
					setMasterViewList(Listhb);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	
	}
   @SuppressWarnings("unchecked")
	public String addAdvanceAmount()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
	
				TableColumes ob1 = null;
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				
				ob1 = new TableColumes();
				ob1.setColumnname("account_ticket_no");
				ob1.setDatatype("varchar(150)");
			    ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("activity_id");
				ob1.setDatatype("varchar(20)");
			    ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("emp_amt");
				ob1.setDatatype("varchar(150)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("emp_reason");
				ob1.setDatatype("varchar(250)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("accountant_id");
				ob1.setDatatype("varchar(20)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("created_date");
				ob1.setDatatype("varchar(50)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
				
				ob1 = new TableColumes();
				ob1.setColumnname("created_time");
				ob1.setDatatype("varchar(10)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
	
				cbt.createTable22("activity_advance_accounts", Tablecolumesaaa, connectionSpace);
	
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
				Collections.sort(requestParameterNames);
				Iterator it = requestParameterNames.iterator();
				while (it.hasNext())
				{
					String Parmname = it.next().toString();
					String paramValue = request.getParameter(Parmname);
					if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("ticket_no"))
					{
						ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
				}
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				String loggedDetail[] = CUA.getEmpDetailsByUserName("WFPM", userName);
				ob = new InsertDataTable();
				ob.setColName("accountant_id");
				ob.setDataName(loggedDetail[0]);
				insertData.add(ob);
	
				ob = new InsertDataTable();
				ob.setColName("created_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);
	
				ob = new InsertDataTable();
				ob.setColName("created_time");
				ob.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				
				String ticket = request.getParameter("ticket_no");
				String query= "SELECT account_ticket_no FROM activity_advance_accounts WHERE activity_id ='"+ request.getParameter("activity_id")+"' ORDER BY id DESC LIMIT 1";
				List ticket_data = cbt.executeAllSelectQuery(query, connectionSpace);
				if(ticket_data!=null && !ticket_data.isEmpty() && ticket_data.size()>0 && ticket_data.get(0)!=null)
				{
					String s = ticket_data.get(0).toString();
				    int ab = s.indexOf(".");
					String x = s.substring(ab+1, s.length());
					String y = s.substring(0,ab);
					String a = String.valueOf((Integer.parseInt(x)+1));
					
					ob = new InsertDataTable();
					ob.setColName("account_ticket_no");
					ob.setDataName(y+"."+a);
					insertData.add(ob);
				}
				else
				{
					ob = new InsertDataTable();
					ob.setColName("account_ticket_no");
					ob.setDataName(ticket+".1");
					insertData.add(ob);
				}
				
				boolean status = false;
				
				status = cbt.insertIntoTable("activity_advance_accounts", insertData, connectionSpace);
				if (status)
				{
					addActionMessage("Action Successfully Done !!!");
				}
				else
				{
					addActionMessage("Oop's there is some problem !!!");
				}
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addActionError("Oop's there is some problem in adding Group ");
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
   @SuppressWarnings("unchecked")
public String beforeAdvanceSettlementAdd()
   {
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				ActivityPojo aa = null;
				commonList = new ArrayList<ActivityPojo>();
				commonList2 = new ArrayList<ActivityPojo>();
				StringBuilder query = new StringBuilder();
				query.append(" SELECT aaa.account_ticket_no,aaa.emp_amt,aaa.emp_reason,pc.emp_name, ");
				query.append(" aaa.created_date,aaa.created_time FROM activity_advance_accounts as aaa ");
				query.append(" LEFT JOIN manage_contact as mc on mc.id=aaa.accountant_id ");
				query.append(" LEFT JOIN primary_contact as pc on pc.id= mc.emp_id ");
				query.append(" WHERE aaa.activity_id = '"+id+"' ");
				List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(data!=null && data.size()>0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						aa = new ActivityPojo();
						if(object[0]!=null)
						{
							aa.setTicket_no(object[0].toString());
						}
						else
						{
							aa.setTicket_no("NA");
						}
						if(object[1]!=null)
						{
							sum = sum + Integer.parseInt(object[1].toString());
							aa.setAmount(object[1].toString());
						}
						else
						{
							aa.setAmount("NA");
						}
						if(object[2]!=null)
						{
							aa.setReason(object[2].toString());
						}
						else
						{
							aa.setReason("NA");
						}
						if(object[3]!=null)
						{
							aa.setManager_name(object[3].toString());
						}
						else
						{
							aa.setManager_name("NA");
						}
						if(object[4]!=null && object[5]!=null)
						{
							aa.setAction_date(DateUtil.convertDateToIndianFormat(object[4].toString()) +", "+object[5].toString());
						}
						else
						{
							aa.setAction_date("NA");
						}
						commonList.add(aa);
					}
				}
				query.setLength(0);
				amt = null;
				query.append(" SELECT exp.exp_parameter, dex.value_text,dex.document,dex.value_text2,dex.value_text2_reason,pc.emp_name,sp.amount ");
				query.append(" FROM activity_schedule_dsr_fill_expenses as dex ");
				query.append(" LEFT JOIN expense_parameter as exp on exp.id=dex.expenses ");
				query.append(" LEFT JOIN activity_schedule_dsr_fill as dsr on dsr.id = dex.dsr_id ");
				query.append(" LEFT JOIN activity_schedule_plan as sp on sp.id=dsr.activityId ");
				query.append(" LEFT JOIN manage_contact as mc on mc.id = dex.manager_id ");
				query.append(" LEFT JOIN primary_contact as pc on pc.id =mc .emp_id ");
				query.append(" WHERE sp.id='"+id+"' ");
				List expList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if(expList!=null && expList.size()>0)
				{
					for (Iterator iterator = expList.iterator(); iterator.hasNext();)
					{
						Object[] object2 = (Object[]) iterator.next();
						aa = new ActivityPojo();
						if(object2[0]!=null)
						{
							aa.setExp(object2[0].toString());
						}
						else
						{
							aa.setExp("NA");
						}
						if(object2[1]!=null)
						{
							sum2 = sum2 + Integer.parseInt(object2[1].toString());
							aa.setExp_val(object2[1].toString());
						}
						else
						{
							aa.setExp_val("NA");
						}
						
						if(object2[2]!=null)
						{
							aa.setDoc(object2[2].toString());
						}
						else
						{
							aa.setDoc("NA");
						}
						if(object2[3]!=null)
						{
							aa.setMan_exp_val(object2[3].toString());
						}
						else
						{
							aa.setMan_exp_val("NA");
						}
						if(object2[4]!=null)
						{
							aa.setMan_exp_reason(object2[4].toString());
						}
						else
						{
							aa.setMan_exp_reason("NA");
						}
						if(object2[5]!=null)
						{
							aa.setMan_name(object2[5].toString());
						}
						else
						{
							aa.setMan_name("NA");
						}
						if(object2[6]!=null)
						{
							amt =object2[6].toString();
						}
						
						commonList2.add(aa);
					}
				}
				
				finalAmt = Integer.parseInt(amt)-sum2;
				System.out.println("Final Amt :::  "+finalAmt);
				return "success";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addActionError("Oop's there is some problem in adding Group ");
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
public void setMasterViewList(List<Object> masterViewList)
{
	this.masterViewList = masterViewList;
}
public List<Object> getMasterViewList()
{
	return masterViewList;
}
public void setCommonList(List<ActivityPojo> commonList)
{
	this.commonList = commonList;
}
public List<ActivityPojo> getCommonList()
{
	return commonList;
}
public void setCommonList2(List<ActivityPojo> commonList2)
{
	this.commonList2 = commonList2;
}
public List<ActivityPojo> getCommonList2()
{
	return commonList2;
}
public void setSum(int sum)
{
	this.sum = sum;
}
public int getSum()
{
	return sum;
}
public void setSum2(int sum2)
{
	this.sum2 = sum2;
}
public int getSum2()
{
	return sum2;
}
public void setAmt(String amt)
{
	this.amt = amt;
}
public String getAmt()
{
	return amt;
}
public double getFinalAmt()
{
	return finalAmt;
}
public void setFinalAmt(double finalAmt)
{
	this.finalAmt = finalAmt;
}

}
