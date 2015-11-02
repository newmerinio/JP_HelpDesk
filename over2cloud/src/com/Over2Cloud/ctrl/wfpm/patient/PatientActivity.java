package com.Over2Cloud.ctrl.wfpm.patient;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.management.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class PatientActivity extends GridPropertyBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private File attachment;
	private String attachmentContentType;
	private String attachmentFileName;
	private JSONArray jsonArray;
	private String offering;
	private List dataList;
	private String pId;

	@SuppressWarnings("unchecked")
	public String fetchAllActivityDetails()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List list = null;
			dataList=new ArrayList<ArrayList<String>>();
			ComplianceUniversalAction CUA = new ComplianceUniversalAction();
			StringBuilder query = new StringBuilder();
			query.append("SELECT status,action_date,action_time,comment,esc_level,action_by,sn_upto_date,sn_upto_time,escalate_to FROM "
					+ "patient_visit_action_history WHERE vid = '" + id + "' ");
			list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			ArrayList<String> tempList = null;
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					tempList = new ArrayList<String>();
					//System.out.println(object[0]);
					
					tempList.add(CUA.getValueWithNullCheck(object[0]));
					
					tempList.add(DateUtil.convertDateToIndianFormat(CUA.getValueWithNullCheck(object[1]))+", "+CUA.getValueWithNullCheck(object[2]).substring(0, 5));
					tempList.add(CUA.getValueWithNullCheck(object[3]));
					if (object[4] != null)
					{
						if(object[8]!=null && !"".equalsIgnoreCase(object[4].toString()))
						{
							tempList.add(object[4].toString()+": "+object[8].toString());
						}
						else
						{
							tempList.add(object[4].toString());
						}
					}
					else
					{
						tempList.add("NA");
					}
					if (object[5] != null)
					{
						System.out.println(object[5]);
						tempList.add(getEmpNameMobByEmpId(object[5].toString(),connectionSpace,cbt).split("#")[0]);
					}
					else
					{
						tempList.add("Auto");
					}
					
					dataList.add(tempList);
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return "success";
	}
	
	public String getEmpNameMobByEmpId(String empId, SessionFactory connection, CommonOperInterface cbt)
	{
		String empDetails = null;
		try
		{
			List dataList = cbt.executeAllSelectQuery("SELECT emp_name, mobile_no FROM primary_contact WHERE  id="+empId, connection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + "#" + object[1].toString();
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empDetails;
	}
	
	
	public String fetchSubOfferingData()
	{
		try
		{
			jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			StringBuilder query = new StringBuilder();
			query.append("SELECT DISTINCT psd.id,act.act_name  " + " FROM patientcrm_status_data as psd  " + " "
					+ "LEFT JOIN activity_type as act on act.id=psd.status WHERE psd.offering_name='"+id+"'");
			if(status!=null && !status.equalsIgnoreCase(""))
			{
				query.append(" AND psd.rh_sub_type='"+status+"' ");
			}
			if(offering!=null && !offering.equalsIgnoreCase(""))
			{
				query.append(" AND psd.stage='"+offering+"' ");
			}
			query.append(" ORDER BY act.act_name ");
			System.out.println("sub offering que:::"+query);
			List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null)
			{
				for (Object c : dataList)
				{
					Object[] dataC = (Object[]) c;
					jsonObject = new JSONObject();
					jsonObject.put("ID",dataC[0].toString());
					jsonObject.put("name",dataC[1].toString());
					jsonArray.add(jsonObject);
				}
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}
	public String fetchVisitHistoryOffering()
	{
		try
		{
			jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			StringBuilder query = new StringBuilder();
			query.append("select distinct off.id,off.subofferingname  " + " FROM patient_visit_action as pva  " + " inner join offeringlevel3 as off on off.id=pva.offeringlevel3" 
			+ " where pva.patient_name=(select patient_name from patient_visit_action where id=" + id + ") order by pva.id desc ");
			//System.out.println(query);
			List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null)
			{
				for (Object c : dataList)
				{
					Object[] dataC = (Object[]) c;
					jsonObject = new JSONObject();
					jsonObject.put("ID",dataC[0]);
					jsonObject.put("name",dataC[1]);
					jsonArray.add(jsonObject);
				}
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String fetchAllDetailsOffering()
	{
		try
		{
			jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			StringBuilder sb = new StringBuilder("");
			System.out.println("pid::::"+pId);
			sb.append(" SELECT act.act_name AS activity, emp.emp_name,off.subofferingname,pva.maxDateTime,pva.status,pva.time,pva.comment2,pva.id,pva.stage,curr_outcome FROM patient_visit_action AS pva ");
			sb.append(" LEFT JOIN patientcrm_status_data AS psd ON psd.id = pva.next_activity ");
			sb.append(" LEFT JOIN activity_type as act on act.id=psd.status ");
			sb.append(" LEFT JOIN manage_contact AS cc ON cc.id = pva.relationship_mgr");
			sb.append(" LEFT JOIN primary_contact AS emp ON emp.id = cc.emp_id ");
			sb.append(" LEFT JOIN offeringlevel3 AS off ON off.id = pva.offeringlevel3 ");
			if(id!=null && !id.equalsIgnoreCase(""))
			{
				sb.append(" WHERE pva.patient_name=(SELECT patient_name FROM patient_visit_action WHERE id=" + id + ") ");
			}
			else
			{
				sb.append(" WHERE pva.patient_name='"+pId+"'  ");
			}
			sb.append(" AND pva.offeringlevel3='"+offering+"' ORDER BY pva.id DESC ");
			
			System.out.println("sb::::::::"+sb);
			List dataList = new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
			if (dataList != null)
			{
				for (Object c : dataList)
				{
					Object[] dataC = (Object[]) c;
					jsonObject = new JSONObject();
					jsonObject.put("activity",dataC[0]);
					jsonObject.put("name",dataC[1]);
					jsonObject.put("offering",dataC[2]);
					if(dataC[3]!=null && dataC[5]!=null)
					{
						jsonObject.put("scheduledate",DateUtil.convertDateToIndianFormat(dataC[3].toString())+", "+dataC[5]);
					}
					else
					{
						jsonObject.put("scheduledate","NA");
					}
					jsonObject.put("status",dataC[4]);
					jsonObject.put("comment",dataC[6]);
					jsonObject.put("id",dataC[7]);
					jsonObject.put("stage",dataC[8]);
					if(dataC[9]!=null)
					{
						jsonObject.put("outcome",dataC[9]);
					}
					else
					{
						jsonObject.put("outcome","NA");
					}
					jsonArray.add(jsonObject);
				}
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String fetchDetailsOffering()
	{
		try
		{
			jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			StringBuilder sb = new StringBuilder("");
			sb.append("select pbd.date,pbd.time,fcm.forcastName from patient_basic_data as pbd ");
			sb.append(" inner join forcastcategarymaster as fcm on fcm.id=pbd.type ");
			sb.append("  where pbd.id=(SELECT patient_name FROM patient_visit_action WHERE id="+id+" )" );
			System.out.println("pdata::::"+sb);
			List dataList = new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
			jsonObject = new JSONObject();
			if (dataList != null)
			{
				JSONArray jsonArray1 = new JSONArray();
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null)
					{
						jsonObject.put("visitDate",DateUtil.convertDateToIndianFormat(object[0].toString()));
					}
					jsonObject.put("stage",object[2]);
					jsonArray1.add(jsonObject);
				}
				jsonArray.add(jsonArray1);
				dataList.clear();
			}
			
			sb.setLength(0);
			sb.append("select pva.id,act.act_name,pva.maxDateTime,pva.time,pvh.action_date,pvh.action_time,pc.emp_name,pva.status,pva.curr_outcome,pva.comment2 from patient_visit_action as pva ");
			sb.append(" left join  patient_visit_action_history as pvh on pvh.vid=pva.id ");
			sb.append(" INNER JOIN patientcrm_status_data as pcsd on pcsd.id=pva.next_activity ");
			sb.append(" left join activity_type as act on act.id=pcsd.status ");
			sb.append(" left join manage_contact as cc on cc.id = pva.relationship_mgr ");
			sb.append(" left join primary_contact as pc on pc.id = cc.emp_id ");
			sb.append(" where pva.patient_name=(SELECT patient_name FROM patient_visit_action WHERE id=" + id + ") and pva.offeringlevel3='"+offering+"' group by pva.id order by pva.maxDateTime ");
			System.out.println("sb::::::::"+sb);
			dataList = new createTable().executeAllSelectQuery(sb.toString(), connectionSpace);
			if (dataList != null)
			{
				JSONArray jsonArray1 = new JSONArray();
				for (Object c : dataList)
				{
					Object[] dataC = (Object[]) c;
					jsonObject = new JSONObject();
					jsonObject.put("id",dataC[0]);
					jsonObject.put("activity",dataC[1]);
					jsonObject.put("date",DateUtil.convertDateToIndianFormat(dataC[2].toString()));
					if(dataC[4]!=null)
					{
						jsonObject.put("actionDate",DateUtil.convertDateToIndianFormat(dataC[4].toString())+", "+dataC[5].toString().substring(0, 5));
					}
					else
					{
						jsonObject.put("actionDate","NA");
					}
					jsonObject.put("owner",dataC[6]);
					jsonObject.put("status",dataC[7]);
					if(dataC[8]!=null)
					{
						jsonObject.put("outcome",dataC[8]);
					}
					else
					{
						jsonObject.put("outcome","NA");
					}
					if(dataC[9]!=null)
					{
						jsonObject.put("comment",dataC[9]);
					}
					else
					{
						jsonObject.put("comment","NA");
					}
					jsonArray1.add(jsonObject);
				}
				jsonArray.add(jsonArray1);
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	public String addFirstActivity()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String allotTo=null;
				List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_patient_visit_action_config", accountID, connectionSpace, "", 0, "table_name", "patient_visit_action_config");
				if (statusColName != null)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
					InsertDataTable ob = null;
					boolean status = false;
					List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
					TableColumes ob1 = null;
					for (GridDataPropertyView gdp : statusColName)
					{
						ob1 = new TableColumes();
						ob1.setColumnname(gdp.getColomnName());
						ob1.setDatatype("varchar(255)");
						Tablecolumesaaa.add(ob1);
					}
					cbt.createTable22("patient_visit_action", Tablecolumesaaa, connectionSpace);
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					String[] val = null;
					// System.out.println(requestParameterNames.size());
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						//System.out.println(Parmname + ":::::" + paramValue);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && !paramValue.equalsIgnoreCase("-1") && Parmname != null && 
								 (Parmname.equalsIgnoreCase("maxDateTime") || Parmname.equalsIgnoreCase("relationship_mgr") || Parmname.equalsIgnoreCase("dr")
								  ||Parmname.equalsIgnoreCase("next_activity") || Parmname.equalsIgnoreCase("comment2") || Parmname.equalsIgnoreCase("offeringlevel3")
								  || Parmname.equalsIgnoreCase("stage") || Parmname.equalsIgnoreCase("patient_name")))
						{
							if (Parmname.equalsIgnoreCase("maxDateTime"))
							{
								val = paramValue.split(" ");
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(DateUtil.convertDateToUSFormat(val[0]));
								insertData.add(ob);
									// sheduled time for next activity
								ob = new InsertDataTable();
								ob.setColName("time");
								ob.setDataName(val[1]);
								insertData.add(ob);

							} else
							{
								if (Parmname.equalsIgnoreCase("dr"))
								{
									Parmname = "relationship_mgr";
								}
								ob = new InsertDataTable();
								ob.setColName(Parmname);
								ob.setDataName(paramValue);
								insertData.add(ob);
								if (!Parmname.equalsIgnoreCase("next_activity") && !Parmname.equalsIgnoreCase("comment2") && !Parmname.equalsIgnoreCase("comment1") && !Parmname.equalsIgnoreCase("relationship_mgr") && !Parmname.equalsIgnoreCase("current_activity"))
								{
									insertData1.add(ob);
								}
							}
						}
					}
					/*
					 * if (request.getParameter("attachment") != null) { String
					 * date= DateUtil.mergeDateTime(); String renameFilePath =
					 * new CreateFolderOs().createUserDir("PatientDocs") + "//"
					 * +date + attachmentFileName; String storeFilePath = new
					 * CreateFolderOs().createUserDir("PatientDocs") + "//" +
					 * attachmentFileName; String str =
					 * renameFilePath.replace("//", "/");
					 * System.out.println("store"+storeFilePath); if
					 * (storeFilePath != null) { File theFile = new
					 * File(storeFilePath); File newFileName = new File(str); if
					 * (theFile != null) { try { FileUtils.copyFile(attachment,
					 * theFile); if (theFile.exists())
					 * theFile.renameTo(newFileName); } catch (IOException e) {
					 * e.printStackTrace(); } if (theFile != null) { ob = new
					 * InsertDataTable(); ob.setColName("attachment");
					 * ob.setDataName(date +getAttachmentFileName());
					 * insertData.add(ob); } } } }
					 */
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
					insertData.add(ob);
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("escalation");
					ob.setDataName("Level1");
					insertData.add(ob);
					insertData1.add(ob);

					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTime());
					insertData.add(ob);
					insertData1.add(ob);

					if (request.getParameter("current_activity") != null && request.getParameter("current_activity") != "-1" && this.status != null && !this.status.equalsIgnoreCase("followup"))
					{
						ob = new InsertDataTable();
						ob.setColName("next_activity");
						ob.setDataName(request.getParameter("current_activity"));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("comment2");
						ob.setDataName(request.getParameter("comment1"));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Completed");
						insertData1.add(ob);

						val = request.getParameter("curr_schedule_date").split(" ");
						ob = new InsertDataTable();
						ob.setColName("maxDateTime");
						ob.setDataName(DateUtil.convertDateToUSFormat(val[0]));
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("time");
						ob.setDataName(val[1]);
						insertData1.add(ob);

						ob = new InsertDataTable();
						ob.setColName("relationship_mgr");
						if (request.getParameter("ownerTypeCurr").equalsIgnoreCase("1"))
						{
							ob.setDataName(request.getParameter("dr1"));
						} else
						{
							ob.setDataName(request.getParameter("relationship_mgr1"));
						}
						insertData1.add(ob);

						status = cbt.insertIntoTable("patient_visit_action", insertData1, connectionSpace);
						if (status)
						{
							System.out.println("First row inserted");
						}
					}

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Open");
					insertData.add(ob);

					if (request.getParameter("maxDateTime").split(" ") != null)
					{
						String time = fetchTAT(request.getParameter("offeringlevel3"), request.getParameter("next_activity"), cbt, connectionSpace);
						if(time != null && !"".equalsIgnoreCase(time) && !"NA".equalsIgnoreCase(time))
						{
							String esctm = DateUtil.newdate_AfterEscTime(DateUtil.convertDateToUSFormat(request.getParameter("maxDateTime").split(" ")[0]), request.getParameter("maxDateTime").split(" ")[1], time, "Y");
							if (esctm != null && !"".equalsIgnoreCase(esctm) && !"NA".equalsIgnoreCase(esctm))
							{
								ob = new InsertDataTable();
								ob.setColName("escalate_date");
								ob.setDataName(esctm.split("#")[0]);
								insertData.add(ob);

								ob = new InsertDataTable();
								ob.setColName("escalate_time");
								ob.setDataName(esctm.split("#")[1]);
								insertData.add(ob);
							}
						}
					}
					if(request.getParameter("next_activity")!=null && !request.getParameter("next_activity").equalsIgnoreCase("-1"))
					{
						status = cbt.insertIntoTable("patient_visit_action", insertData, connectionSpace);
					}
					insertData.clear();
					//if (status)
					//{
						List<InsertDataTable> insertDta = new ArrayList<InsertDataTable>();
						// Update in data table
						if (this.status != null && this.status.equalsIgnoreCase("followup") && request.getParameter("statusActivity") != null && !request.getParameter("statusActivity").equalsIgnoreCase("-1"))
						{
							
							if(request.getParameter("statusActivity").equalsIgnoreCase("Completed"))
							{
								System.out.println("id::::"+id);
								cbt.executeAllUpdateQuery("UPDATE patient_visit_action SET status='Completed', curr_outcome='" + request.getParameter("curr_outcome") + "' WHERE id=" + id, connectionSpace);
								
								ob = new InsertDataTable();
								ob.setColName("comment");
								ob.setDataName(request.getParameter("curr_outcome"));
								insertDta.add(ob);
							}
							else if(request.getParameter("statusActivity").equalsIgnoreCase("Snooze"))
							{
								int i=cbt.executeAllUpdateQuery("UPDATE patient_visit_action SET status='Snooze' WHERE id=" + id, connectionSpace);
								if(i>0)
								{
									ob = new InsertDataTable();
									ob.setColName("sn_upto_date");
									ob.setDataName(DateUtil.convertDateToUSFormat(request.getParameter("sn_upto_date")));
									insertDta.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("sn_upto_time");
									ob.setDataName(request.getParameter("sn_upto_time"));
									insertDta.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("comment");
									ob.setDataName(request.getParameter("sn_reason"));
									insertDta.add(ob);
								}
							}
							else if(request.getParameter("statusActivity").equalsIgnoreCase("Re-assign"))
							{
								String owner=null;
								if(request.getParameter("re_owner").equalsIgnoreCase("1"))
								{
									owner=request.getParameter("re_dr");
								}
								else
								{
									owner=request.getParameter("re_relmgr");
								}
								List list=cbt.executeAllSelectQuery("SELECT relationship_mgr FROM patient_visit_action WHERE id=" + id, connectionSpace);
								String pre_allot_to=null;
								if(list!=null && list.size()>0)
								{
									pre_allot_to=(String) list.get(0);
								}
								int i=cbt.executeAllUpdateQuery("UPDATE patient_visit_action SET relationship_mgr='"+owner+"' WHERE id=" + id, connectionSpace);
								if(i>0)
								{
									ob = new InsertDataTable();
									ob.setColName("allot_to");
									ob.setDataName(pre_allot_to);
									insertDta.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("comment");
									ob.setDataName(request.getParameter("re_reason"));
									insertDta.add(ob);
								}
							}
							
							else if(request.getParameter("statusActivity").equalsIgnoreCase("Seek Approval"))
							{
								int i=cbt.executeAllUpdateQuery("UPDATE patient_visit_action SET status='Seek Approval' WHERE id=" + id, connectionSpace);
								if(i>0)
								{
									ob = new InsertDataTable();
									ob.setColName("allot_to");
									ob.setDataName(request.getParameter("seek_by"));
									insertData.add(ob);
									
									ob = new InsertDataTable();
									ob.setColName("comment");
									ob.setDataName(request.getParameter("se_reason"));
									insertDta.add(ob);
								}
							}
							
							ob = new InsertDataTable();
							ob.setColName("vid");
							ob.setDataName(id);
							insertDta.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("action_date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertDta.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("action_time");
							ob.setDataName(DateUtil.getCurrentTime());
							insertDta.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("action_by");
							ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
							insertDta.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("status");
							ob.setDataName(request.getParameter("statusActivity"));
							insertDta.add(ob);
							
							cbt.insertIntoTable("patient_visit_action_history", insertDta, connectionSpace);
						}
						else if(request.getParameter("stage").equalsIgnoreCase("3") && request.getParameter("lost_reason")!=null && !request.getParameter("lost_reason").equalsIgnoreCase("-1"))
						{

							cbt.executeAllUpdateQuery("UPDATE patient_visit_action SET status='Lost', stage='" + request.getParameter("stage") +
									"',rca='"+request.getParameter("rca")+"',capa='"+request.getParameter("capa")+"',lost_reason='"+request.getParameter("lost_reason")+"' "
									+ "WHERE id=" + id, connectionSpace);
						}
						
						else if(request.getParameter("stage").equalsIgnoreCase("4") && request.getParameter("uhid")!=null)
						{

							cbt.executeAllUpdateQuery("UPDATE patient_visit_action SET status='Closed', stage='" + request.getParameter("stage") + "'"
									+ ",un_doc='"+request.getParameter("un_doc")+"',admitted_on='"+request.getParameter("adm_date")+"',remarks='"+request.getParameter("cl_reason")+"' WHERE id=" + id, connectionSpace);
							
							cbt.executeAllUpdateQuery("UPDATE patient_basic_data SET uhid='"+request.getParameter("uhid")+"' WHERE id="+request.getParameter("patient_name")+"", connectionSpace);
						}
						
						//For Communication
						String escHolderDetails=null;
						String patientDetails=null;
						String activityName=null;
						if ((request.getParameter("sendSMS") != null && request.getParameter("sendSMS").toString().equalsIgnoreCase("true"))|| (request.getParameter("sendEmail") != null && request.getParameter("sendEmail").toString().equalsIgnoreCase("true") ))
						{
							String ids=null;
							if (request.getParameter("ownerType").equalsIgnoreCase("1"))
							{
								ids=request.getParameter("dr");
							} else
							{
								ids=request.getParameter("relationship_mgr");
							}
							
							escHolderDetails=escHolderNameMob(ids,"WFPM",connectionSpace);
							patientDetails=patientDetails(request.getParameter("patient_name"),connectionSpace);
							activityName=getActivityName(request.getParameter("next_activity"),connectionSpace);
						}
						
						if (request.getParameter("sendSMS") != null && request.getParameter("sendSMS").toString().equalsIgnoreCase("true"))
						{
							String empName=null;
							String empMob=null;
							if(escHolderDetails!=null)
							{
								empName = escHolderDetails.split("#")[0];
								empMob = escHolderDetails.split("#")[1];
								System.out.println(empName+":::emp::"+empMob);
							}
							//For Owner
							if (empMob != null && empMob != "" && empMob.trim().length() == 10)
							{
								String msg ="Activity Open: Patient Name: "+patientDetails.split("#")[0]+", Activity Name: "+activityName+", Schedule Date: "+request.getParameter("maxDateTime")+", Remarks: "+request.getParameter("comment2")+".";
								new CommunicationHelper().addMessage(empName, "", empMob, msg, "", "Pending", "0", "WFPM", connectionSpace);
							}
							String pName=null;
							String pMob=null;
							if(patientDetails!=null)
							{
								pName = patientDetails.split("#")[0];
								pMob = patientDetails.split("#")[1];
								System.out.println(pName+":::patient::"+pMob);
							}
							//For Patient
							if (pMob != null && pMob != "" && pMob.trim().length() == 10)
							{
								String msg ="Activity Open: Activity Name: "+activityName+", Schedule Date: "+request.getParameter("maxDateTime")+", Remarks: "+request.getParameter("comment2")+".";
								new CommunicationHelper().addMessage(pName, "", pMob, msg, "", "Pending", "0", "WFPM", connectionSpace);
							}
						}
						if (request.getParameter("sendEmail") != null && request.getParameter("sendEmail").toString().equalsIgnoreCase("true"))
						{
							if(escHolderDetails.split("#")[2]!=null)
							{
								String mailText=getConfigMessage(patientDetails,escHolderDetails,activityName,request.getParameter("maxDateTime"),request.getParameter("comment2"),true);
								new CommunicationHelper().addMail(escHolderDetails.split("#")[0],"",escHolderDetails.split("#")[2],"Open Activity Alert", mailText.toString(), "","Pending", "0",null,"WFPM", connectionSpace);
							}
							
							if(patientDetails.split("#")[2]!=null)
							{
								String mailText=getConfigMessage(patientDetails,escHolderDetails,activityName,request.getParameter("maxDateTime"),request.getParameter("comment2"),false);
								new CommunicationHelper().addMail(patientDetails.split("#")[0],"",patientDetails.split("#")[2],"Open Activity Alert", mailText.toString(), "","Pending", "0",null,"WFPM", connectionSpace);
							}
						}
						if (request.getParameter("sendQuestion") != null && request.getParameter("sendQuestion").toString().equalsIgnoreCase("true"))
						{
							/*
							 * String query =
							 * "select mobile,email,concat(first_name,' ',last_name) FROM patient_basic_data where id='"
							 * + request.getParameter("patient_name") + "'";
							 * List data4escalate =
							 * cbt.executeAllSelectQuery(query,
							 * connectionSpace); if (data4escalate.size() > 0) {
							 * CommunicationHelper COH = new
							 * CommunicationHelper(); Object[] contactdetails =
							 * (Object[]) data4escalate.get(0); boolean
							 * putsmsstatus = false, putmailstatus = false;
							 * String report_sms = "Dear " + (contactdetails[2]
							 * != null ? contactdetails[2] : "NA") +
							 * ", please visit http://115.112.236.225:9090/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?setNo=A&traceid="
							 * + request.getParameter("patient_name") +
							 * " for fill your Wellness Questionnaire."; // SMS
							 * 
							 * if(contactdetails[0]!=null) { String
							 * report_sms=null;
							 * 
							 * }
							 * 
							 * // MAil if (contactdetails[1] != null) { String
							 * mailtext = this.mail_text((contactdetails[2] !=
							 * null ? contactdetails[2].toString() : "NA"),
							 * request.getParameter("patient_name"));
							 * putmailstatus = COH.addMail((contactdetails[2] !=
							 * null ? contactdetails[2].toString() : "NA"), "",
							 * (contactdetails[1] != null ?
							 * contactdetails[1].toString() : "NA"),
							 * "Online Feedback", mailtext, "", "Pending", "0",
							 * "", "WFPM", connectionSpace); } }
							 */

						}
						addActionMessage("Patient Activity Done successfully!!!");
						return SUCCESS;
					/*} else
					{
						
						return SUCCESS;
					}*/
				}
			} catch (Exception exp)
			{
				addActionMessage("Oops There is some error in data!");
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}
	
	public String getConfigMessage(String patientDetails, String escHolderDetails, String activityName, String date, String comment, boolean flag)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Activity Management</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
		mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Activity Open</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
		if (flag)
		{
			mailtext.append("<b>Dear " + escHolderDetails.split("#")[0] + ",</b>");
		}
		else if (!flag)
		{
			mailtext.append("<b>Dear " + patientDetails.split("#")[0] + ",</b>");
		}

		mailtext.append("<br><br><b>Hello !!!</b><br>");
		if (flag)
		{
			mailtext.append("Here is a activity mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
		}
		else
		{
			mailtext.append("Here is a activity details for you:- <br>");
		}
		
		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
		if (flag)
		{
			mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Patient&nbsp;Name:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + patientDetails.split("#")[0] + "</td></tr>");
			mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Mobile&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + patientDetails.split("#")[1] + "</td></tr>");
		}
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Activity:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + activityName + "</td></tr>");
		mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Schedule&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + date + "</td></tr>");
		mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Remarks:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + comment + " </td></tr>");

		mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		mailtext
				.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}
	
	private String getActivityName(String id, SessionFactory connectionSpace)
	{
		StringBuilder query=new StringBuilder();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		query.append("SELECT DISTINCT act.act_name  " + " FROM patientcrm_status_data as psd  " + " "
				+ "LEFT JOIN activity_type as act on act.id=psd.status WHERE psd.id='"+id+"'");
		System.out.println("activity::::"+query);
		List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if(list!=null && list.size()>0)
		{
			return (String) list.get(0);
		}
		return null;
	}
	private String patientDetails(String id, SessionFactory connectionSpace)
	{

		String empDetails = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = cbt.executeAllSelectQuery("SELECT first_name,last_name,mobile,email FROM patient_basic_data WHERE id="+id+"", connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + " " + object[1].toString()+ "#" + object[2].toString()+ "#" + object[3].toString();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("patient details::::"+empDetails);
		return empDetails;
	
	}

	@SuppressWarnings("rawtypes")
	private String escHolderNameMob(String id, String module, SessionFactory connection)
	{
		String empDetails = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = cbt.executeAllSelectQuery("SELECT emp.emp_name, emp.mobile_no,email_id FROM primary_contact AS emp INNER JOIN manage_contact AS cc ON cc.emp_id=emp.id WHERE cc.id='" + id + "' AND cc.module_name='" + module + "'", connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					empDetails = object[0].toString() + "#" + object[1].toString()+ "#" + object[2].toString();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("emp details::::"+empDetails);
		return empDetails;
	}
	/*private List<InsertDataTable> setAddData(List<InsertDataTable> insertData, String id) 
	{
		
		
		return insertData;
	}*/
	private String fetchTAT(String offering, String activity, CommonOperInterface cbt, SessionFactory connectionSpace)
	{
		try
		{
			StringBuilder query = new StringBuilder();
			query.append(" SELECT tat2 FROM patientcrm_status_data WHERE id=" + activity);
			List list = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && list.size() > 0)
			{
				String time = (String) list.get(0);
				if (time != null && !time.equalsIgnoreCase("NA"))
				{
					return time;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "00:00";
	}

	public String mail_text(String name, String trace)
	{
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(name) + ",</b>");
		mailtext.append("<br><br> Please visit http://115.112.236.225:9090/over2cloud/view/Over2Cloud/questionairOver2Cloud/patientFeedback.action?setNo=A&traceid=" + trace + " to fill your Wellness Questionnaire.");
		mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><br Thanks !!!<br><br></FONT>");
		mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>--------------<br>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		return mailtext.toString();
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public File getAttachment()
	{
		return attachment;
	}

	public void setAttachment(File attachment)
	{
		this.attachment = attachment;
	}

	public String getAttachmentContentType()
	{
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType)
	{
		this.attachmentContentType = attachmentContentType;
	}

	public String getAttachmentFileName()
	{
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName)
	{
		this.attachmentFileName = attachmentFileName;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public String getOffering()
	{
		return offering;
	}

	public void setOffering(String offering)
	{
		this.offering = offering;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getpId()
	{
		return pId;
	}

	public void setpId(String pId)
	{
		this.pId = pId;
	}


}