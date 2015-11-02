package com.Over2Cloud.ctrl.wfpm.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.offeringComplaint.ComplaintLodgeHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class WFPMServiceHelper
{

	String id = "", mobno = "", msg_text = "", module = "", date = "", time = "", updateQry = "", mappingUpdateQry = "";
	String new_escalation_datetime = "0000-00-00#00:00", non_working_timing = "00:00";

	String PatientNmae = "";
	String offeringlevel3 = "";
	String currentActivity = "";
	String currentSheduleDate = "";
	String nextActivity = "";
	String escalate_date = "";
	String escalation = "";
	String relationship_mgr = "";
	String comment = "";

	// Method body for Escalating Ticket @ manab :06-06-2015
	@SuppressWarnings({ "rawtypes" })
	public void escalateToNextLevel(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			String dr=null,scheduleDate=null,scheduleTime=null;
			String query = "";
			query = "SELECT pva.id, pva.patient_name, pva.offeringlevel3, pva.current_activity, pva.curr_schedule_date, pva.next_activity,pva.escalate_date, pva.escalate_time, " + "pva.escalation, pva.relationship_mgr, pva.comment2,pva.dr,pva.maxDateTime,pva.time FROM patient_visit_action AS pva WHERE status='Open' AND escalate_date ='" + DateUtil.getCurrentDateUSFormat() + "'";
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						PatientNmae = object[1].toString();
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						offeringlevel3 = object[2].toString();
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						currentActivity = object[3].toString();
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						currentSheduleDate = object[4].toString();
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						nextActivity = object[5].toString();
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						escalate_date = object[6].toString();
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						time = object[7].toString();
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						escalation = object[8].toString();
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						relationship_mgr = object[9].toString();
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						comment = object[10].toString();
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						dr = object[11].toString();
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						scheduleDate = object[12].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						scheduleTime = object[13].toString();
					}
				}
				boolean escnextlvl = DateUtil.time_update(escalate_date, time);
				if (escnextlvl)
				{
					String calculationLevel = String.valueOf(Integer.parseInt(escalation.substring(5, 6)) + 1);
					StringBuilder sb=new StringBuilder();
					String data = fetchDataForEscalation(nextActivity, calculationLevel, connection);
					String empDetails=null;
					if (escalation != null && !escalation.equals("") && !escalation.equals("0") && data != null)
					{
						String Pname = "NA", PmobNo = "NA";
						List patientData = HUH.getData("select first_name, last_name, mobile from patient_basic_data where id =" + PatientNmae + "", connection);
						if (patientData != null && patientData.size() > 0)
						{
							for (Iterator itr = patientData.iterator(); itr.hasNext();)
							{
								Object[] object = (Object[]) itr.next();
								Pname = object[0].toString() + object[1].toString();
								PmobNo = object[2].toString();
							}
						}

						String offering = "NA";
						List offerName = HUH.getData("select subofferingname, subofferingdesc from offeringlevel3 where status='Active' and id=" + offeringlevel3 + "", connection);
						if (offerName != null && offerName.size() > 0)
						{
							for (Iterator itr = offerName.iterator(); itr.hasNext();)
							{
								Object[] object = (Object[]) itr.next();
								offering = object[1].toString();
							}
						}

						String rel_mag = "NA", rel_mag_mob = "NA";
						String query5 = null;
						if(relationship_mgr!=null && !relationship_mgr.equalsIgnoreCase(""))
						{
							query5 = "select emp.emp_name, emp.mobOne from primary_contact as emp inner join manage_contact as cc on cc.emp_id=emp.id inner join rel_mgr_data as rel on cc.id=rel.name where rel.name=" + relationship_mgr + "";
						}
						else if(dr!=null && !dr.equalsIgnoreCase(""))
						{
							query5 = "select emp.emp_name, emp.mobOne from primary_contact as emp inner join manage_contact as cc on cc.emp_id=emp.id inner join doctor_off_map_data as rel on cc.id=rel.doctor_name where rel.doctor_name=" + dr + "";
						}
						if(query5!=null)
						{
							List relData = HUH.getData(query5, connection);
							if (relData != null && relData.size() > 0)
							{
								for (Iterator itr = relData.iterator(); itr.hasNext();)
								{
									Object[] object = (Object[]) itr.next();
									rel_mag = object[0].toString();
									rel_mag_mob = object[1].toString();
								}
							}
						}
						
						String mailBody1 = getMailBody1("Level"+calculationLevel, Pname, PmobNo, offering, data.split("#")[2], DateUtil.convertDateToIndianFormat(scheduleDate) + ", " + scheduleTime, rel_mag, rel_mag_mob, comment);
						if (data.split("#")[0].contains(","))
						{
							String mapEmp[] = data.split("#")[0].trim().split(",");
							for (int i = 0; i < mapEmp.length; i++)
							{
								String[] escMappedData = getNameByccid(mapEmp[i], connection);
								new MsgMailCommunication().addMailHR("Un Mapped", "", escMappedData[1], "Escalated Activity of Patient " + "", mailBody1, "", "Pending", "0", "", "T2M", connection);
								empDetails=empDetails+escMappedData[0]+",";
							}
							empDetails=empDetails.substring(0, empDetails.length()-1);
						}
						else
						{
							String name[]=getNameByccid(data.split("#")[0], connection);
							new MsgMailCommunication().addMailHR("Un Mapped", "", name[1], "Escalated Activity of Patient ", mailBody1, "", "Pending", "0", "", "T2M", connection);
							empDetails=name[0];
						}
					}
					
					String nxtEsc = escTime("tat" + (Integer.parseInt(calculationLevel) + 1), nextActivity, connection);
					sb.append(" UPDATE patient_visit_action SET ");
					if (nxtEsc != null)
					{
						sb.append(" escalate_date='" + nxtEsc.split("#")[0] + "',");
						sb.append(" escalate_time='" + nxtEsc.split("#")[1] + "',");
					}
					sb.append(" escalation='Level" + calculationLevel + "'");
					sb.append(" WHERE id=" + id + "");
					//Update in data table
					int chkUpdate = new createTable().executeAllUpdateQuery(sb.toString(), connection);
					if (chkUpdate > 0)
					{
						Map<String, Object> wherClause = new HashMap<String, Object>();
						// update in history table
						wherClause.put("vid", id);
						wherClause.put("action_date", DateUtil.getCurrentDateUSFormat());
						wherClause.put("action_time", DateUtil.getCurrentTime());
						wherClause.put("esc_level", "Level" + calculationLevel);
						wherClause.put("status", "Escalate");
						wherClause.put("escalate_to", empDetails);

						if (wherClause != null && wherClause.size() > 0)
						{
							CommonOperInterface cbt = new CommonConFactory().createInterface();
							InsertDataTable ob = new InsertDataTable();
							List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
							for (Map.Entry<String, Object> entry : wherClause.entrySet())
							{
								ob = new InsertDataTable();
								ob.setColName(entry.getKey());
								ob.setDataName(entry.getValue().toString());
								insertData.add(ob);
							}
							cbt.insertIntoTable("patient_visit_action_history", insertData, connection);
							insertData.clear();
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String escTime(String tat, String nextActivity, SessionFactory connection)
	{
		String esctm = null;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List empData1 = cbt.executeAllSelectQuery("SELECT  " + tat + " FROM patientcrm_status_data WHERE id=" + nextActivity + "", connection);
			if (empData1 != null && empData1.size() > 0)
			{
				for (Iterator it = empData1.iterator(); it.hasNext();)
				{
					String time = (String) it.next();
					if (time != null && !"".equalsIgnoreCase(time)&& !"NA".equalsIgnoreCase(time))
					{
						esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(),time, "Y");
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return esctm;
	}

	private String fetchDataForEscalation(String nextActivity, String level, SessionFactory connection)
	{
		if (nextActivity != null)
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT l" + level + ",tat" + level + ",status FROM patientcrm_status_data WHERE id=" + nextActivity);
			List dataList = new createTable().executeAllSelectQuery(query.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object[] = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						return "" + object[0] + "#" + object[1]+"#"+object[2];
					}
				}
			}
		}
		return null;
	}

	// method for get emp details
	private String[] getNameByccid(String string, SessionFactory connectionSpace)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder qryString = new StringBuilder();
		List dataList = null;
		String[] values = null;

		try
		{
			qryString.append("select emp.empName, emp.emailIdOne, emp.mobOne from employee_basic as emp inner join compliance_contacts as cc on cc.emp_id=emp.id");
			qryString.append(" where cc.id=" + string + "");
			dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);

			if (dataList != null && dataList.size() > 0)
			{
				Object[] objectCol = null;
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					objectCol = (Object[]) iterator.next();
					values = new String[3];
					if (objectCol[0].toString() != null || !objectCol[1].toString().equals(""))
					{
						values[0] = objectCol[0].toString();
						values[1] = objectCol[1].toString();
						values[2] = objectCol[2].toString();
					}
				}
			}
		} catch (Exception exp)
		{
			exp.printStackTrace();
		}
		return values;
	}

	// method for null chk
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	// method for convert string to milis
	private long getMili(String string)
	{
		long millis;
		int hh = Integer.parseInt(string.split(":")[0]);
		int mm = Integer.parseInt(string.split(":")[1]);
		millis = (mm * 60 * 1000) + (hh * 60 * 1000);
		return millis;
	}

	// send mail body for level holder
	public String getMailBody1(String level, String Pname, String PmobNo, String offering, String activity, String scheduledAt, String rel_mag, String rel_mag_mob, String comment)
	{
		StringBuilder mailText = new StringBuilder("");

		mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>" + "<font color='#000000' size='2'>Hi,</font></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>" + "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
				+ "<font color='#000000' size='2'> Kindly find the following auto generated escalated patient information mapped for you via your E-mail ID as your are mapped with this at "
				+ level
				+ "</font></td></tr></table>"
				+ "<br>"
				+ "<font color='#808080' size='2'><center><h2><b> Escalated Information Details </b> </h2></center></font></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
				+ "<tr  bgcolor='#D0D0D0'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Patient Name:</font></td> "
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ Pname
				+ "&nbsp;</font></td>"

				+ "</tr><tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Patient Mobile No.:</font></td>"
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ PmobNo
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='#D0D0D0'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Offering:</font></td>"
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ offering
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Activity:</font></td>"
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ activity
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='#D0D0D0'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Scheduled At:</font></td>"
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ scheduledAt
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Mapped Person:</font></td>"
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ rel_mag
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#D0D0D0'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Mapped Person Mobile:</font></td>"
				+ "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;"
				+ rel_mag_mob
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Comment:</font></td>" + "<td  align='left' width='30%'><font style='color:#000000;'>&nbsp;" + comment + "&nbsp;</font></td></tr>"

				+ "   </table>");
		mailText.append("<tr><td><br></td></tr>");
		mailText.append("<tr><td><br></td></tr>");
		mailText.append("<tr><td>Hope this information was useful for you.</td></tr>");

		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
		mailText.append("<tr><td>IT Team.</td></tr>");

		mailText.append("<br><b>___________________________________</b>");
		mailText.append("<br>");
		mailText.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then. Please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to support@dreamsol.biz</FONT>");
		return mailText.toString();

	}

	/*
	 * public void escalateToNextLevel(SessionFactory connection,
	 * HelpdeskUniversalHelper HUH, CommunicationHelper CH) { try { String query
	 * = ""; query =
	 * "select id ,ticket_no ,feed_by ,feed_by_mobno ,feed_by_emailid ,deptHierarchy ,feed_brief ,to_dept_subdept ,escalation_date ,escalation_time,open_date,open_time,location,level,subCatg,moduleName from feedback_status where status='Pending' and escalation_date<='"
	 * + DateUtil.getCurrentDateUSFormat() + "'"; List data4escalate =
	 * HUH.getData(query, connection); if (data4escalate != null &&
	 * data4escalate.size() > 0) { //
	 * System.out.println("Inside Escalate Method"); // CommunicationHelper CH =
	 * new CommunicationHelper();
	 * 
	 * @SuppressWarnings("unused") String id = null, ticket_no = null, feed_by =
	 * null, feed_by_mobno = null, feed_by_emailid = null, deptHierarchy = null,
	 * feed_brief = null, to_dept_subdept = "", escalation_date = null,
	 * escalation_time = null, open_date = null, open_time = null, location =
	 * null, level = null, subCatg = null, catg_Dept = null, esc_duration =
	 * null, needesc = null, module = null;
	 * 
	 * String levelMsg = null, subject = null, ulevel = null, _2uLevel = null,
	 * _3uLevel = null; List esclevelData = null;
	 * 
	 * for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();) {
	 * Object[] object = (Object[]) iterator.next(); if (object[0] != null &&
	 * !object[0].toString().equals("")) { id = object[0].toString(); } if
	 * (object[1] != null && !object[1].toString().equals("")) { ticket_no =
	 * object[1].toString(); } else { ticket_no = "NA"; } if (object[2] != null
	 * && !object[2].toString().equals("")) { feed_by = object[2].toString(); }
	 * else { feed_by = "NA"; } if (object[3] != null &&
	 * !object[3].toString().equals("")) { feed_by_mobno = object[3].toString();
	 * } else { feed_by_mobno = "NA"; } if (object[4] != null &&
	 * !object[4].toString().equals("")) { feed_by_emailid =
	 * object[4].toString(); } else { feed_by_emailid = "NA"; } if (object[5] !=
	 * null && !object[5].toString().equals("")) { deptHierarchy =
	 * object[5].toString(); } else { deptHierarchy = "NA"; } if (object[6] !=
	 * null && !object[6].toString().equals("")) { feed_brief =
	 * object[6].toString(); } else { feed_brief = "NA"; } if (object[7] != null
	 * && !object[7].toString().equals("")) { to_dept_subdept =
	 * object[7].toString(); } else { to_dept_subdept = "NA"; } if (object[8] !=
	 * null && !object[8].toString().equals("")) { escalation_date =
	 * object[8].toString(); } else { escalation_date = "NA"; } if (object[9] !=
	 * null && !object[9].toString().equals("")) { escalation_time =
	 * object[9].toString(); } else { escalation_time = "NA"; } if (object[10]
	 * != null && !object[10].toString().equals("")) { open_date =
	 * object[10].toString(); } else { open_date = "NA"; } if (object[11] !=
	 * null && !object[11].toString().equals("")) { open_time =
	 * object[11].toString(); } else { open_time = "NA"; } if (object[12] !=
	 * null && !object[12].toString().equals("")) { location =
	 * object[12].toString(); } else { location = "NA"; } if (object[13] != null
	 * && !object[13].toString().equals("")) { level = object[13].toString(); }
	 * else { level = "NA"; } if (object[14] != null &&
	 * !object[14].toString().equals("")) { subCatg = object[14].toString(); }
	 * else { subCatg = "NA"; } if (object[15] != null &&
	 * !object[15].toString().equals("")) { module = object[15].toString(); }
	 * else { module = "NA"; } // System.out.println("Module Name  "+module); if
	 * (escalation_date != null && !escalation_date.equals("") &&
	 * !escalation_date.equals("NA") && escalation_time != null &&
	 * !escalation_time.equals("") && !escalation_time.equals("NA") && level !=
	 * null && !level.equals("") && !level.equals("NA") &&
	 * !level.equals("Level5")) { boolean escnextlvl =
	 * DateUtil.time_update(escalation_date, escalation_time); // If Escalating
	 * time passed away then go inside the If // block if (escnextlvl == true &&
	 * module != null && !module.equals("") && !module.equals("NA")) { //
	 * Getting Detail from Sub Category Table if (subCatg != null &&
	 * !subCatg.equals("") && !subCatg.equals("0")) { query =
	 * "select * from feedback_subcategory where id='" + subCatg + "'"; List
	 * subCategoryList = HUH.getData(query, connection); if (subCategoryList !=
	 * null && subCategoryList.size() > 0) { for (Iterator iterator5 =
	 * subCategoryList.iterator(); iterator5.hasNext();) { Object[] objectCol =
	 * (Object[]) iterator5.next(); if (objectCol[6] == null) { esc_duration =
	 * "24:00"; } else { esc_duration = objectCol[6].toString(); }
	 * 
	 * if (objectCol[9] == null) { needesc = "Y"; } else { needesc =
	 * objectCol[9].toString(); } } }
	 * 
	 * if (module.equals("HDM")) { query =
	 * "select deptid from subdepartment where id='" + to_dept_subdept + "'";
	 * List data = HUH.getData(query, connection); if (data != null &&
	 * data.size() > 0) { for (Iterator iterator2 = data.iterator();
	 * iterator2.hasNext();) { Object object2 = (Object) iterator2.next();
	 * catg_Dept = object2.toString(); } } } else { catg_Dept = to_dept_subdept;
	 * }
	 * 
	 * String updateLevel = ""; ulevel = "" +
	 * (Integer.parseInt(level.substring(5)) + 1); subject = "L" +
	 * level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
	 * levelMsg = "L" + level.substring(5) + " to L" + (ulevel) +
	 * " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " +
	 * DateUtil.convertDateToIndianFormat(escalation_date) + "," +
	 * escalation_time.substring(0, 5) + ", Reg. By: " +
	 * DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " +
	 * feed_brief + "."; esclevelData = HUH.getEmp4Escalation(to_dept_subdept,
	 * deptHierarchy, module, ulevel, "N", "", connection); updateLevel =
	 * "Level" + ulevel; if (esclevelData == null || esclevelData.size() == 0) {
	 * _2uLevel = "" + (Integer.parseInt(ulevel) + 1); subject = "L" +
	 * level.substring(5) + " to L" + (_2uLevel) + " Escalate Alert: " +
	 * ticket_no; levelMsg = "L" + level.substring(5) + " to L" + (_2uLevel) +
	 * " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " +
	 * DateUtil.convertDateToIndianFormat(escalation_date) + "," +
	 * escalation_time.substring(0, 5) + ", Reg. By: " +
	 * DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " +
	 * feed_brief + "."; esclevelData = HUH.getEmp4Escalation(to_dept_subdept,
	 * deptHierarchy, module, _2uLevel, "N", "", connection); updateLevel =
	 * "Level" + _2uLevel; if (esclevelData == null || esclevelData.size() == 0)
	 * { _3uLevel = "" + (Integer.parseInt(_2uLevel) + 1); subject = "L" +
	 * level.substring(5) + " to L" + (_3uLevel) + " Escalate Alert: " +
	 * ticket_no; levelMsg = "L" + level.substring(5) + " to L" + (_3uLevel) +
	 * " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " +
	 * DateUtil.convertDateToIndianFormat(escalation_date) + "," +
	 * escalation_time.substring(0, 5) + ", Reg. By: " +
	 * DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " +
	 * feed_brief + "."; esclevelData = HUH.getEmp4Escalation(to_dept_subdept,
	 * deptHierarchy, module, _3uLevel, "N", "", connection); updateLevel =
	 * "Level" + _3uLevel; } }
	 * 
	 * if (esclevelData != null && esclevelData.size() > 0) { for (Iterator
	 * iterator3 = esclevelData.iterator(); iterator3.hasNext();) { Object[]
	 * object3 = (Object[]) iterator3.next();
	 * 
	 * String new_date = null, new_time = null, datetime = null; String[]
	 * newdate_time = null; if (escalation_date != null && escalation_date != ""
	 * && escalation_time != null && escalation_time != "" && esc_duration !=
	 * null && esc_duration != "" && needesc != null && needesc != "") {
	 * datetime = DateUtil.newdate_AfterEscTime(escalation_date,
	 * escalation_time, esc_duration, needesc); if (datetime != null && datetime
	 * != "") { newdate_time = datetime.split("#"); if (newdate_time.length > 0)
	 * { new_date = newdate_time[0]; new_time = newdate_time[1]; updateQry =
	 * "update feedback_status set escalation_date='" + new_date +
	 * "',escalation_time='" + new_time + "',level='" + updateLevel +
	 * "' where id='" + id + "'"; boolean updatefeedback =
	 * HUH.updateData(updateQry, connection); mappingUpdateQry =
	 * "update escalation_mapping set nextEscLevel_" + updateLevel.substring(5)
	 * + "='" + updateLevel + "',EscLevel_" + updateLevel.substring(5) +
	 * "_DateTime='" + DateUtil.convertDateToIndianFormat(new_date) + "(" +
	 * new_time + ")'  where feed_status_id='" + id + "'";
	 * HUH.updateData(mappingUpdateQry, connection);
	 * 
	 * List data = HUH.getFeedbackDetail("", deptHierarchy, "Pending", id,
	 * connection); FeedbackPojo fbp = null; fbp =
	 * HUH.setFeedbackDataValues(data, "Pending", deptHierarchy); if
	 * (updatefeedback) { if (object3[1] != null &&
	 * !object3[1].toString().equalsIgnoreCase("")) {
	 * CH.addMessage(object3[1].toString(), object3[4].toString(),
	 * object3[2].toString(), levelMsg, "", "Pending", "0", module, connection);
	 * } if (fbp != null) { String mailText = HUH.configMessage4Escalation(fbp,
	 * subject, object3[1].toString(), deptHierarchy, true);
	 * CH.addMail(object3[1].toString(), object3[4].toString(),
	 * object3[3].toString(), subject, mailText, "", "Pending", "0", "", module,
	 * connection); } if (fbp != null && fbp.getFeed_registerby() != null &&
	 * !fbp.getFeed_registerby().equals("") && fbp.getFeedback_by_mobno() !=
	 * null && !fbp.getFeedback_by_mobno().equals("") &&
	 * !fbp.getFeedback_by_mobno().equals("NA")) { String msg = "Dear " +
	 * fbp.getFeed_registerby() + ", We apologies for delay of Ticket No: " +
	 * fbp.getTicket_no() + " that has now been escalated to " +
	 * DateUtil.makeTitle(object3[1].toString()) + ", Mob: " +
	 * object3[2].toString() + ". Thanks.";
	 * CH.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(),
	 * fbp.getFeedback_by_mobno(), msg, "", "Pending", "0", module, connection);
	 * } } } } } } } } } } } } } catch (Exception e) { e.printStackTrace(); } }
	 */
	/*
	 * public void escalationInAsset(SessionFactory connection,
	 * HelpdeskUniversalHelper HUH, CommunicationHelper CH) { try { String query
	 * = ""; query =
	 * "select id ,ticket_no ,feed_by ,feed_by_mobno ,feed_by_emailid ,feed_brief ,to_dept ,escalation_date ,escalation_time,open_date,open_time,location,level,subCatg,moduleName from asset_complaint_status where status='Pending' and escalation_date<='"
	 * + DateUtil.getCurrentDateUSFormat() + "'"; List data4escalate =
	 * HUH.getData(query, connection); if (data4escalate != null &&
	 * data4escalate.size() > 0) { System.out.println("Inside Escalate Method");
	 * // CommunicationHelper CH = new CommunicationHelper();
	 * 
	 * @SuppressWarnings("unused") String id = null, ticket_no = null, feed_by =
	 * null, feed_by_mobno = null, feed_by_emailid = null, deptHierarchy = null,
	 * feed_brief = null, to_dept_subdept = "", escalation_date = null,
	 * escalation_time = null, open_date = null, open_time = null, location =
	 * null, level = null, subCatg = null, catg_Dept = null, esc_duration =
	 * null, needesc = null,module=null;
	 * 
	 * String levelMsg = null, subject = null, ulevel = null, _2uLevel = null,
	 * _3uLevel = null; List esclevelData = null;
	 * 
	 * for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();) {
	 * Object[] object = (Object[]) iterator.next(); if (object[0] != null &&
	 * !object[0].toString().equals("")) { id = object[0].toString(); } if
	 * (object[1] != null && !object[1].toString().equals("")) { ticket_no =
	 * object[1].toString(); } else { ticket_no = "NA"; } if (object[2] != null
	 * && !object[2].toString().equals("")) { feed_by = object[2].toString(); }
	 * else { feed_by = "NA"; } if (object[3] != null &&
	 * !object[3].toString().equals("")) { feed_by_mobno = object[3].toString();
	 * } else { feed_by_mobno = "NA"; } if (object[4] != null &&
	 * !object[4].toString().equals("")) { feed_by_emailid =
	 * object[4].toString(); } else { feed_by_emailid = "NA"; }
	 * 
	 * if (object[5] != null && !object[5].toString().equals("")) { feed_brief =
	 * object[5].toString(); } else { feed_brief = "NA"; } if (object[6] != null
	 * && !object[6].toString().equals("")) { to_dept_subdept =
	 * object[6].toString(); } else { to_dept_subdept = "NA"; } if (object[7] !=
	 * null && !object[7].toString().equals("")) { escalation_date =
	 * object[7].toString(); } else { escalation_date = "NA"; } if (object[8] !=
	 * null && !object[8].toString().equals("")) { escalation_time =
	 * object[8].toString(); } else { escalation_time = "NA"; } if (object[9] !=
	 * null && !object[9].toString().equals("")) { open_date =
	 * object[9].toString(); } else { open_date = "NA"; } if (object[10] != null
	 * && !object[10].toString().equals("")) { open_time =
	 * object[10].toString(); } else { open_time = "NA"; } if (object[11] !=
	 * null && !object[11].toString().equals("")) { location =
	 * object[11].toString(); } else { location = "NA"; } if (object[12] != null
	 * && !object[12].toString().equals("")) { level = object[12].toString(); }
	 * else { level = "NA"; } if (object[13] != null &&
	 * !object[13].toString().equals("")) { subCatg = object[13].toString(); }
	 * else { subCatg = "NA"; } if (object[14] != null &&
	 * !object[14].toString().equals("")) { module = object[14].toString(); }
	 * else { module = "NA"; } // System.out.println("Module Name  "+module); if
	 * (escalation_date != null && !escalation_date.equals("") &&
	 * !escalation_date.equals("NA") && escalation_time != null &&
	 * !escalation_time.equals("") && !escalation_time.equals("NA") && level !=
	 * null && !level.equals("") && !level.equals("NA") &&
	 * !level.equals("Level5")) { boolean escnextlvl =
	 * DateUtil.time_update(escalation_date, escalation_time); // If Escalating
	 * time passed away then go inside the If // block
	 * System.out.println("Flag Value iss   "+escnextlvl); if (escnextlvl ==
	 * true && module!=null && !module.equals("") && !module.equals("NA")) { //
	 * Getting Detail from Sub Category Table if (subCatg != null &&
	 * !subCatg.equals("") && !subCatg.equals("0")) { query =
	 * "select * from feedback_subcategory where id='" + subCatg + "'"; List
	 * subCategoryList = HUH.getData(query, connection); if (subCategoryList !=
	 * null && subCategoryList.size() > 0) { for (Iterator iterator5 =
	 * subCategoryList.iterator(); iterator5.hasNext();) { Object[] objectCol =
	 * (Object[]) iterator5.next(); if (objectCol[6] == null) { esc_duration =
	 * "24:00"; } else { esc_duration = objectCol[6].toString(); }
	 * 
	 * if (objectCol[9] == null) { needesc = "Y"; } else { needesc =
	 * objectCol[9].toString(); } } }
	 * 
	 * if (module.equals("HDM")) { query =
	 * "select deptid from subdepartment where id='" + to_dept_subdept + "'";
	 * List data = HUH.getData(query, connection); if (data != null &&
	 * data.size() > 0) { for (Iterator iterator2 = data.iterator();
	 * iterator2.hasNext();) { Object object2 = (Object) iterator2.next();
	 * catg_Dept = object2.toString(); } } } else {
	 * 
	 * }
	 * 
	 * catg_Dept = to_dept_subdept;
	 * 
	 * String updateLevel = ""; ulevel = "" +
	 * (Integer.parseInt(level.substring(5)) + 1); subject = "L" +
	 * level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
	 * levelMsg = "L" + level.substring(5) + " to L" + (ulevel) +
	 * " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " +
	 * DateUtil.convertDateToIndianFormat(escalation_date) + "," +
	 * escalation_time.substring(0, 5) + ", Reg. By: " +
	 * DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " +
	 * feed_brief + "."; esclevelData = HUH.getEmp4Escalation(to_dept_subdept,
	 * deptHierarchy, module, ulevel, "N", "", connection); updateLevel =
	 * "Level" + ulevel; if (esclevelData == null || esclevelData.size() == 0) {
	 * _2uLevel = "" + (Integer.parseInt(ulevel) + 1); subject = "L" +
	 * level.substring(5) + " to L" + (_2uLevel) + " Escalate Alert: " +
	 * ticket_no; levelMsg = "L" + level.substring(5) + " to L" + (_2uLevel) +
	 * " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " +
	 * DateUtil.convertDateToIndianFormat(escalation_date) + "," +
	 * escalation_time.substring(0, 5) + ", Reg. By: " +
	 * DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " +
	 * feed_brief + "."; esclevelData = HUH.getEmp4Escalation(to_dept_subdept,
	 * deptHierarchy, module, _2uLevel, "N", "", connection); updateLevel =
	 * "Level" + _2uLevel; if (esclevelData == null || esclevelData.size() == 0)
	 * { _3uLevel = "" + (Integer.parseInt(_2uLevel) + 1); subject = "L" +
	 * level.substring(5) + " to L" + (_3uLevel) + " Escalate Alert: " +
	 * ticket_no; levelMsg = "L" + level.substring(5) + " to L" + (_3uLevel) +
	 * " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " +
	 * DateUtil.convertDateToIndianFormat(escalation_date) + "," +
	 * escalation_time.substring(0, 5) + ", Reg. By: " +
	 * DateUtil.makeTitle(feed_by) + ", Location: " + location + ", Brief: " +
	 * feed_brief + "."; esclevelData = HUH.getEmp4Escalation(to_dept_subdept,
	 * deptHierarchy, module, _3uLevel, "N", "", connection); updateLevel =
	 * "Level" + _3uLevel; } }
	 * 
	 * if (esclevelData != null && esclevelData.size() > 0) { for (Iterator
	 * iterator3 = esclevelData.iterator(); iterator3.hasNext();) { Object[]
	 * object3 = (Object[]) iterator3.next();
	 * 
	 * String new_date = null, new_time = null, datetime = null; String[]
	 * newdate_time = null; if (escalation_date != null && escalation_date != ""
	 * && escalation_time != null && escalation_time != "" && esc_duration !=
	 * null && esc_duration != "" && needesc != null && needesc != "") {
	 * datetime = DateUtil.newdate_AfterEscTime(escalation_date,
	 * escalation_time, esc_duration, needesc); if (datetime != null && datetime
	 * != "") { newdate_time = datetime.split("#"); if (newdate_time.length > 0)
	 * { new_date = newdate_time[0]; new_time = newdate_time[1]; updateQry =
	 * "update asset_complaint_status set escalation_date='" + new_date +
	 * "',escalation_time='" + new_time + "',level='" + updateLevel +
	 * "' where id='" + id + "'"; boolean updatefeedback =
	 * HUH.updateData(updateQry, connection);
	 * 
	 * List data = new AssetComplaintHelper().getFeedbackDetail("", "Pending",
	 * id, connection); FeedbackPojo fbp = null; fbp = new
	 * AssetComplaintHelper().setFeedbackDataValues(data, "Pending",
	 * deptHierarchy); if (updatefeedback) { if (object3[1] != null &&
	 * !object3[1].toString().equalsIgnoreCase("")) {
	 * CH.addMessage(object3[1].toString(), object3[4].toString(),
	 * object3[2].toString(), levelMsg, "", "Pending", "0", module, connection);
	 * } if (object3[3] != null && !object3[3].toString().equalsIgnoreCase(""))
	 * { String mailText = HUH.configMessage4Escalation(fbp, subject,
	 * object3[1].toString(),deptHierarchy, true);
	 * CH.addMail(object3[1].toString(), object3[4].toString(),
	 * object3[3].toString(), subject,mailText, "", "Pending", "0", "", module,
	 * connection); } if (fbp != null && fbp.getFeed_registerby() != null &&
	 * !fbp.getFeed_registerby().equals("") && fbp.getFeedback_by_mobno() !=
	 * null && !fbp.getFeedback_by_mobno().equals("") &&
	 * !fbp.getFeedback_by_mobno().equals("NA")) { String msg = "Dear " +
	 * fbp.getFeed_registerby() + ", We apologies for delay of Ticket No: " +
	 * fbp.getTicket_no() + " that has now been escalated to " +
	 * DateUtil.makeTitle(object3[1].toString()) + ", Mob: " +
	 * object3[2].toString() + ". Thanks.";
	 * CH.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp
	 * .getFeedback_by_mobno(), msg, "", "Pending", "0", module, connection); }
	 * } } } } } } } } } } } } catch (Exception e) { e.printStackTrace(); } }
	 */

	/*public void escalationInAsset(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			WorkingHourAction WHA = new WorkingHourAction();
			String query = "";
			query = "select id ,ticket_no ,feed_by ,feed_by_mobno ,feed_by_emailid ,feed_brief ,to_dept ,escalation_date ,escalation_time,open_date,open_time,location,level,subCatg,moduleName,by_dept from asset_complaint_status where status='Pending' and escalation_date<='" + DateUtil.getCurrentDateUSFormat() + "'";
			System.out.println(query);
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				// CommunicationHelper CH = new CommunicationHelper();
				@SuppressWarnings("unused")
				String id = null, ticket_no = null, feed_by = null, feed_by_mobno = null, feed_by_emailid = null, deptHierarchy = null, feed_brief = null, to_dept_subdept = "", escalation_date = null, escalation_time = null, open_date = null, open_time = null, location = null, level = null, subCatg = null, catg_Dept = null, esc_duration = null, needesc = null, module = null, by_dept = "";

				String levelMsg = null, subject = null, ulevel = null, _2uLevel = null, _3uLevel = null;
				List esclevelData = null;

				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticket_no = object[1].toString();
					} else
					{
						ticket_no = "NA";
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						feed_by = object[2].toString();
					} else
					{
						feed_by = "NA";
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						feed_by_mobno = object[3].toString();
					} else
					{
						feed_by_mobno = "NA";
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						feed_by_emailid = object[4].toString();
					} else
					{
						feed_by_emailid = "NA";
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						feed_brief = object[5].toString();
					} else
					{
						feed_brief = "NA";
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						to_dept_subdept = object[6].toString();
					} else
					{
						to_dept_subdept = "NA";
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						escalation_date = object[7].toString();
					} else
					{
						escalation_date = "NA";
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						escalation_time = object[8].toString();
					} else
					{
						escalation_time = "NA";
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						open_date = object[9].toString();
					} else
					{
						open_date = "NA";
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						open_time = object[10].toString();
					} else
					{
						open_time = "NA";
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						location = object[11].toString();
					} else
					{
						location = "NA";
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						level = object[12].toString();
					} else
					{
						level = "NA";
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						subCatg = object[13].toString();
					} else
					{
						subCatg = "NA";
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						module = object[14].toString();
					} else
					{
						module = "NA";
					}
					if (object[15] != null && !object[15].toString().equals(""))
					{
						by_dept = object[15].toString();
					} else
					{
						by_dept = "NA";
					}
					// System.out.println("Module Name  "+module);
					if (escalation_date != null && !escalation_date.equals("") && !escalation_date.equals("NA") && escalation_time != null && !escalation_time.equals("") && !escalation_time.equals("NA") && level != null && !level.equals("") && !level.equals("NA") && !level.equals("Level5"))
					{
						boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
						// If Escalating time passed away then go inside the If
						// block
						if (escnextlvl == true && module != null && !module.equals("") && !module.equals("NA"))
						{
							// Getting Detail from Sub Category Table
							if (subCatg != null && !subCatg.equals("") && !subCatg.equals("0"))
							{
								query = "select * from feedback_subcategory where id='" + subCatg + "'";
								// System.out.println(query);
								List subCategoryList = HUH.getData(query, connection);
								if (subCategoryList != null && subCategoryList.size() > 0)
								{
									for (Iterator iterator5 = subCategoryList.iterator(); iterator5.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator5.next();
										if (objectCol[6] == null)
										{
											esc_duration = "24:00";
										} else
										{
											esc_duration = objectCol[6].toString();
										}

										if (objectCol[9] == null)
										{
											needesc = "Y";
										} else
										{
											needesc = objectCol[9].toString();
										}
									}
								}

								catg_Dept = to_dept_subdept;
								String type = getAllotmentConfig(cbt, connection);
								String updateLevel = "";
								ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
								subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
								// ///////////
								esclevelData = HUH.getEmp4Escalation(location, ulevel, type, by_dept, connection);
								updateLevel = "Level" + ulevel;

								if (esclevelData != null && esclevelData.size() > 0)
								{
									for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
									{
										Object[] object3 = (Object[]) iterator3.next();

										String new_date = null, new_time = null;
										if (escalation_date != null && escalation_date != "" && escalation_time != null && escalation_time != "" && esc_duration != null && esc_duration != "" && needesc != null && needesc != "")
										{
											List<String> dateTime = WHA.getAddressingEscTime(connection, cbt, esc_duration, "05:00", needesc, escalation_date, escalation_time, "HASTM");
											if (dateTime != null && dateTime.size() > 0)
											{
												new_date = dateTime.get(0);
												new_time = dateTime.get(1);
												updateQry = "update asset_complaint_status set escalation_date='" + new_date + "',escalation_time='" + new_time + "',level='" + updateLevel + "' where id='" + id + "'";
												boolean updatefeedback = HUH.updateData(updateQry, connection);
												// ///////////
												List data = new AssetComplaintHelper().getFeedbackDetail("", "Pending", id, connection, type);
												FeedbackPojo fbp = null;
												fbp = new AssetComplaintHelper().setFeedbackDataValues(data, "Pending", deptHierarchy);
												if (updatefeedback)
												{
													if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
													{
														levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feed_by) + ", Location: " + fbp.getLocation() + ", Brief: " + feed_brief + ".";
														CH.addMessage(object3[1].toString(), object3[4].toString(), object3[2].toString(), levelMsg, "", "Pending", "0", module, connection);
													}
													if (object3[3] != null && !object3[3].toString().equalsIgnoreCase(""))
													{
														// For Mail
														String mailText = HUH.configMessage4EscalationAsset(fbp, subject, object3[1].toString(), "0", true);
														CH.addMail(object3[1].toString(), object3[4].toString(), object3[3].toString(), subject, mailText, "", "Pending", "0", "", module, connection);
													}
													if (fbp != null && fbp.getFeed_registerby() != null && !fbp.getFeed_registerby().equals("") && fbp.getFeedback_by_mobno() != null && !fbp.getFeedback_by_mobno().equals("") && !fbp.getFeedback_by_mobno().equals("NA"))
													{
														String msg = "Dear " + fbp.getFeed_registerby() + ", We apologies for delay of Ticket No: " + fbp.getTicket_no() + " that has now been escalated to " + DateUtil.makeTitle(object3[1].toString()) + ", Mob: " + object3[2].toString() + ". Thanks.";
														CH.addMessage(fbp.getFeed_registerby(), fbp.getFeedback_by_dept(), fbp.getFeedback_by_mobno(), msg, "", "Pending", "0", module, connection);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}*/

	public String getAllotmentConfig(CommonOperInterface cbt, SessionFactory connection)
	{
		String allot2Config = null;
		try
		{
			List dataList = cbt.executeAllSelectQuery("SELECT allotto FROM asset_allotment_config", connection);
			if (dataList != null && dataList.size() > 0 && dataList.get(0) != null)
			{
				allot2Config = dataList.get(0).toString();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return allot2Config;
	}

	// Method body for Snooze to Active ticket status 24-01-2014
	/*
	 * @SuppressWarnings("unchecked") public void
	 * SnoozetoPendingForAsset(SessionFactory connection,
	 * HelpdeskUniversalHelper HUH, CommunicationHelper CH) {
	 * 
	 * try { String query =
	 * "select id ,ticket_no ,feed_by ,deptHierarchy ,feed_brief ,allot_to ,location ,subCatg ,sn_upto_date ,sn_upto_time,moduleName from asset_complaint_status where status='Snooze' or status='Off Site' and sn_upto_date<='"
	 * + DateUtil.getCurrentDateUSFormat() + "'"; List data4escalate =
	 * HUH.getData(query, connection); if (data4escalate != null &&
	 * data4escalate.size() > 0) { String id = null, ticket_no = null, feed_by =
	 * null, deptHierarchy = null, feed_brief = null, snooze_upto_date = null,
	 * snooze_upto_time = null, location = null, subCatg = null, allot_to =
	 * null, adrr_time = null, res_time = null,module=null; List esclevelData =
	 * null; for (Iterator iterator = data4escalate.iterator();
	 * iterator.hasNext();) { Object[] object = (Object[]) iterator.next();
	 * 
	 * if (object[0] != null && !object[0].toString().equals("")) { id =
	 * object[0].toString(); }
	 * 
	 * if (object[1] != null && !object[1].toString().equals("")) { ticket_no =
	 * object[1].toString(); } else { ticket_no = "NA"; }
	 * 
	 * if (object[2] != null && !object[2].toString().equals("")) { feed_by =
	 * object[2].toString(); } else { feed_by = "NA"; }
	 * 
	 * if (object[3] != null && !object[3].toString().equals("")) {
	 * deptHierarchy = object[3].toString(); } else { deptHierarchy = "NA"; }
	 * 
	 * if (object[4] != null && !object[4].toString().equals("")) { feed_brief =
	 * object[4].toString(); } else { feed_brief = "NA"; }
	 * 
	 * if (object[5] != null && !object[5].toString().equals("")) { allot_to =
	 * object[5].toString(); } else { allot_to = "NA"; }
	 * 
	 * if (object[6] != null && !object[6].toString().equals("")) { location =
	 * object[6].toString(); } else { location = "NA"; }
	 * 
	 * if (object[7] != null && !object[7].toString().equals("")) { subCatg =
	 * object[7].toString(); } else { subCatg = "NA"; }
	 * 
	 * if (object[8] != null && !object[8].toString().equals("")) {
	 * snooze_upto_date = object[8].toString(); } else { snooze_upto_date =
	 * "NA"; }
	 * 
	 * if (object[9] != null && !object[9].toString().equals("")) {
	 * snooze_upto_time = object[9].toString(); } else { snooze_upto_time =
	 * "NA"; } if (object[10] != null && !object[10].toString().equals("")) {
	 * module = object[10].toString(); } else { module = "NA"; }
	 * 
	 * if (snooze_upto_date != null && !snooze_upto_date.equals("") &&
	 * snooze_upto_time != null && !snooze_upto_time.equals("") && module !=
	 * null && !module.equals("") && !module.equals("NA")) { boolean
	 * snoozetopending = DateUtil.time_update(snooze_upto_date,
	 * snooze_upto_time); if (snoozetopending == true) { // Getting Detail from
	 * Sub Category Table if (!subCatg.equals("0")) { String query2 =
	 * "select * from feedback_subcategory where id='" + subCatg + "'"; List
	 * subCategoryList = HUH.getData(query2, connection); if (subCategoryList !=
	 * null && subCategoryList.size() > 0) { for (Iterator iterator2 =
	 * subCategoryList.iterator(); iterator2.hasNext();) { Object[] objectCol =
	 * (Object[]) iterator2.next(); if (objectCol[4] == null) { adrr_time =
	 * "06:00"; } else { adrr_time = objectCol[4].toString(); } if (objectCol[5]
	 * == null) { res_time = "10:00"; } else { res_time =
	 * objectCol[5].toString(); } } }
	 * 
	 * esclevelData = HUH.getEmployeeData(allot_to.toString(), "2", connection);
	 * if (esclevelData != null && esclevelData.size() > 0) { for (Iterator
	 * iterator3 = esclevelData.iterator(); iterator3.hasNext();) { Object[]
	 * object3 = (Object[]) iterator3.next();
	 * 
	 * String new_date = null, new_time = null, datetime = null; String[]
	 * newdate_time = null; if (snooze_upto_date != null && snooze_upto_date !=
	 * "" && snooze_upto_time != null && snooze_upto_time != "" && adrr_time !=
	 * null && adrr_time != "" && res_time != null && res_time != "") { datetime
	 * = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(),
	 * DateUtil .getCurrentTime(), adrr_time, res_time); if (datetime != null &&
	 * datetime != "") { newdate_time = datetime.split("#"); if
	 * (newdate_time.length > 0) { new_date = newdate_time[0]; new_time =
	 * newdate_time[1];
	 * 
	 * updateQry = "update feedback_status set escalation_date='" + new_date +
	 * "',escalation_time='" + new_time + "',status='Pending' where id='" + id +
	 * "'"; boolean updatefeedback = HUH.updateData(updateQry, connection); List
	 * data = HUH.getFeedbackDetail("", deptHierarchy, "Pending", id,
	 * connection); FeedbackPojo fbp = null; fbp =
	 * HUH.setFeedbackDataValues(data, "Pending", ""); if (updatefeedback) {
	 * 
	 * @SuppressWarnings("unused") boolean putsmsstatus = false; // Code/block
	 * for // sending the message if (object3[1] != null &&
	 * !object3[1].toString().equalsIgnoreCase("")) { String msg =
	 * "By:Snooze to Active Alert: Ticket No: " + ticket_no + ", Resolved By: "
	 * + new_time + ", Reg. By: " + feed_by + ", Location: " + location +
	 * ", Brief: " + feed_brief + "."; putsmsstatus =
	 * CH.addMessage(object3[0].toString(), object3[5].toString(), object3[1]
	 * .toString(), msg, ticket_no, "Pending", "0", module, connection); } if
	 * (fbp != null) { String mailText = new
	 * HelpdeskUniversalHelper().getConfigMessage(fbp,
	 * "Snooze --> Pending Feedback Alert", "Pending", deptHierarchy, true);
	 * CH.addMail(object3[0].toString(), object3[5].toString(),
	 * object3[4].toString(), "Snooze --> Pending Feedback Alert", mailText,
	 * ticket_no, "Pending", "0", "", module, connection); } } } } } } } } } } }
	 * } } catch (Exception e) { e.printStackTrace(); } }
	 */

	@SuppressWarnings("unchecked")
	public void SnoozetoPendingForAsset(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{/*

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select id ,ticket_no ,feed_by ,feed_brief ,allot_to ,location ,subCatg ,sn_upto_date ,sn_upto_time,moduleName,retDate,retTime from asset_complaint_status where (status='Snooze' or  status='Off Site') and sn_upto_date<='" + DateUtil.getCurrentDateUSFormat() + "'";

			// System.out.println("QQQQQ   "+query);
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				String id = null, ticket_no = null, feed_by = null, deptHierarchy = null, feed_brief = null, snooze_upto_date = null, snooze_upto_time = null, location = null, subCatg = null, allot_to = null, adrr_time = null, res_time = null, module = null;
				List esclevelData = null;
				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}

					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticket_no = object[1].toString();
					} else
					{
						ticket_no = "NA";
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						feed_by = object[2].toString();
					} else
					{
						feed_by = "NA";
					}

					
					 * if (object[3] != null &&
					 * !object[3].toString().equals("")) { deptHierarchy =
					 * object[3].toString(); } else { deptHierarchy = "NA"; }
					 

					if (object[3] != null && !object[3].toString().equals(""))
					{
						feed_brief = object[3].toString();
					} else
					{
						feed_brief = "NA";
					}

					if (object[4] != null && !object[4].toString().equals(""))
					{
						allot_to = object[4].toString();
					} else
					{
						allot_to = "NA";
					}

					if (object[5] != null && !object[5].toString().equals(""))
					{
						location = object[6].toString();
					} else
					{
						location = "NA";
					}

					if (object[6] != null && !object[6].toString().equals(""))
					{
						subCatg = object[6].toString();
					} else
					{
						subCatg = "NA";
					}

					if (object[7] != null && !object[7].toString().equals(""))
					{
						snooze_upto_date = object[7].toString();
					} else
					{
						snooze_upto_date = "NA";
					}

					if (object[8] != null && !object[8].toString().equals(""))
					{
						snooze_upto_time = object[8].toString();
					} else
					{
						snooze_upto_time = "NA";
					}

					if (object[10] != null && !object[10].toString().equals(""))
					{
						snooze_upto_date = object[10].toString();
					} else
					{
						snooze_upto_date = "NA";
					}

					if (object[11] != null && !object[11].toString().equals(""))
					{
						snooze_upto_time = object[11].toString();
					} else
					{
						snooze_upto_time = "NA";
					}

					if (object[7] != null && !object[7].toString().equals("") && object[10] != null && !object[10].toString().equals(""))
					{
						snooze_upto_date = object[7].toString();
					} else
					{
						snooze_upto_date = "NA";
					}

					if (object[8] != null && !object[8].toString().equals("") && object[11] != null && !object[11].toString().equals(""))
					{
						snooze_upto_time = object[8].toString();
					} else
					{
						snooze_upto_time = "NA";
					}

					if (object[9] != null && !object[9].toString().equals(""))
					{
						module = object[9].toString();
					} else
					{
						module = "NA";
					}

					if (snooze_upto_date != null && !snooze_upto_date.equals("") && snooze_upto_time != null && !snooze_upto_time.equals("") && module != null && !module.equals("") && !module.equals("NA"))
					{
						boolean snoozetopending = DateUtil.time_update(snooze_upto_date, snooze_upto_time);
						if (snoozetopending == true)
						{
							// Getting Detail from Sub Category Table
							if (!subCatg.equals("0"))
							{
								String query2 = "select * from feedback_subcategory where id='" + subCatg + "'";
								List subCategoryList = HUH.getData(query2, connection);
								if (subCategoryList != null && subCategoryList.size() > 0)
								{
									for (Iterator iterator2 = subCategoryList.iterator(); iterator2.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator2.next();
										if (objectCol[4] == null)
										{
											adrr_time = "06:00";
										} else
										{
											adrr_time = objectCol[4].toString();
										}
										if (objectCol[5] == null)
										{
											res_time = "10:00";
										} else
										{
											res_time = objectCol[5].toString();
										}
									}
								}
								String type = getAllotmentConfig(cbt, connection);
								esclevelData = HUH.getEmployeeData(allot_to.toString(), "2", connection);
								if (esclevelData != null && esclevelData.size() > 0)
								{
									for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
									{
										Object[] object3 = (Object[]) iterator3.next();

										String new_date = null, new_time = null, datetime = null;
										String[] newdate_time = null;
										if (snooze_upto_date != null && snooze_upto_date != "" && snooze_upto_time != null && snooze_upto_time != "" && adrr_time != null && adrr_time != "" && res_time != null && res_time != "")
										{
											datetime = DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time, res_time);
											if (datetime != null && datetime != "")
											{
												newdate_time = datetime.split("#");
												if (newdate_time.length > 0)
												{
													new_date = newdate_time[0];
													new_time = newdate_time[1];

													updateQry = "update asset_complaint_status set escalation_date='" + new_date + "',escalation_time='" + new_time + "',status='Pending' where id='" + id + "'";
													boolean updatefeedback = HUH.updateData(updateQry, connection);
													List data = new AssetComplaintHelper().getFeedbackDetail("", "Pending", id, connection, type);
													FeedbackPojo fbp = null;
													fbp = HUH.setFeedbackDataValues(data, "Pending", "");
													if (updatefeedback)
													{
														@SuppressWarnings("unused")
														boolean putsmsstatus = false;
														// Code/block for
														// sending the message
														if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
														{
															String msg = "By:Snooze to Active Alert: Ticket No: " + ticket_no + ", Resolved By: " + new_time + ", Reg. By: " + feed_by + ", Location: " + location + ", Brief: " + feed_brief + ".";
															putsmsstatus = CH.addMessage(object3[0].toString(), object3[5].toString(), object3[1].toString(), msg, ticket_no, "Pending", "0", module, connection);
														}
														if (fbp != null)
														{
															String mailText = new HelpdeskUniversalHelper().getConfigMessage(fbp, "Snooze --> Pending Feedback Alert", "Pending", deptHierarchy, true);
															CH.addMail(object3[0].toString(), object3[5].toString(), object3[4].toString(), "Snooze --> Pending Feedback Alert", mailText, ticket_no, "Pending", "0", "", module, connection);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	*/}

	/*
	 * // Method body for sending SMS @Khushal 24-01-2014
	 * 
	 * @SuppressWarnings("unchecked") public void SendSMSMethod(SessionFactory
	 * connection, HelpdeskUniversalHelper HUH) { try { List listSMS = new
	 * ArrayList(); String query =
	 * "select id ,mobno ,msg_text,module,date,time from instant_msg where flag='0' and date<='"
	 * + DateUtil.getCurrentDateUSFormat() + "'"; listSMS = HUH.getData(query,
	 * connection); if (listSMS != null && listSMS.size() > 0) { for (Iterator
	 * iterator = listSMS.iterator(); iterator.hasNext();) { Object[] object =
	 * (Object[]) iterator.next(); id = object[0].toString(); if (object[1] !=
	 * null && !object[1].toString().equals("")) { mobno = object[1].toString();
	 * } else { mobno = "NA"; } if (object[2] != null &&
	 * !object[2].toString().equals("")) { msg_text = object[2].toString(); }
	 * else { msg_text = "NA"; } if (object[3] != null &&
	 * !object[3].toString().equals("")) { module = object[3].toString(); } else
	 * { module = "NA"; } if (object[4] != null &&
	 * !object[4].toString().equals("")) { date = object[4].toString(); } else {
	 * date = "NA"; } if (object[5] != null && !object[5].toString().equals(""))
	 * { time = object[5].toString(); } else { time = "NA"; }
	 * 
	 * if (date != null && !date.equals("") && !date.equals("NA") && time !=
	 * null && !time.equals("") && !time.equals("NA")) { boolean escnextlvl =
	 * DateUtil.time_update(date, time); // If Escalating time passed away then
	 * go inside the If // block if (escnextlvl) { updateQry =
	 * "update instant_msg set flag='3',status='Send',update_date='" +
	 * DateUtil.getCurrentDateUSFormat() + "',update_time='" +
	 * DateUtil.getCurrentTime() + "' where id='" + id + "'"; boolean update =
	 * HUH.updateData(updateQry, connection); if (update) { if (mobno != null &&
	 * !mobno.equals("") && !mobno.equals("NA") && msg_text != null &&
	 * !msg_text.equals("") && !msg_text.equals("NA")) { sendSMS(mobno,
	 * msg_text, module, id, connection, HUH); } } } } } Runtime rt =
	 * Runtime.getRuntime(); rt.gc(); } } catch (Exception e) {
	 * e.printStackTrace(); } finally { try { } catch (Exception e2) {
	 * e2.printStackTrace(); } }
	 * 
	 * }
	 */

	// Method body for sending SMS @Khushal 24-01-2014
	/*
	 * @SuppressWarnings("deprecation") public void sendSMS(String msisdn,
	 * String fmsg, String module, String id, SessionFactory connection,
	 * HelpdeskUniversalHelper HUH) { try { String clientid = "blkdst1"; //
	 * String clientid = "dmudst04"; String senderid = ""; String encodedMsg =
	 * URLEncoder.encode(fmsg); if (module != null && !module.equals("") &&
	 * (module.equals("HDM"))) { senderid = "BLKHDM"; } else if (module != null
	 * && !module.equals("") && (module.equals("HIS") || module.equals("T2M")))
	 * { senderid = "BLKSSH"; } else if (module != null && !module.equals("") &&
	 * (module.equals("FM"))) { senderid = "BLKCRM"; } else { senderid =
	 * "BLKHDM"; }
	 * 
	 * if ((msisdn != null && !msisdn.equals("")) && msisdn.length() == 10 &&
	 * ((msisdn.startsWith("9") || msisdn.startsWith("8") ||
	 * msisdn.startsWith("7")) && fmsg != null && !fmsg.equals(""))) { try {
	 * String URL = null; StringBuffer url = new StringBuffer(); // URL = //
	 * "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=blkhdm&password=blk31"
	 * // ; URL =
	 * "http://64.120.220.52:9080/urldreamclient/dreamurl?userName=demourl&password=demourl"
	 * ; URL = URL + "&clientid=" + clientid + "&to=";
	 * 
	 * url.append(URL); url.append(msisdn); url.append("&text=");
	 * url.append(encodedMsg); url.append("&Senderid="); url.append(senderid);
	 * 
	 * URL codedURL = new URL(url.toString()); HttpURLConnection HURLC =
	 * (HttpURLConnection) codedURL.openConnection(); HURLC.connect(); int
	 * revertCode = HURLC.getResponseCode(); HURLC.disconnect(); if (revertCode
	 * == 1201 || revertCode == 1202 || revertCode == 1204) { url = null; } else
	 * if (revertCode == 200 || revertCode == 505 || revertCode == 503) {
	 * 
	 * System.out.println(
	 * "SMS Not Send Successfully From First Route and Get Revert Code :" +
	 * revertCode+"::: Now trying to send from second Route" );
	 * 
	 * String URL2 = null; StringBuffer url2 = new StringBuffer(); URL2 =
	 * "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=blkhdm&password=blk31"
	 * ; URL2=URL2+"&clientid="+clientid+"&to=";
	 * 
	 * url2.append(URL2); url2.append(msisdn); url2.append("&text=");
	 * url2.append(encodedMsg); url2.append("&Senderid=");
	 * url2.append(senderid);
	 * 
	 * URL codedURL2 = new URL(url2.toString()); HttpURLConnection HURLC2
	 * =(HttpURLConnection)codedURL2.openConnection(); HURLC2.connect(); int
	 * revertCode2= HURLC2.getResponseCode(); HURLC.disconnect(); if
	 * (revertCode2==1201 || revertCode2==1202 || revertCode2==1204)
	 * {System.out.println(
	 * "SMS Send Successfully From Second Route and Get111 Revert Code :"
	 * +revertCode); url = null; } else { System.out.println(
	 * "SMS Not Send Successfully From Second Route and Get Revert Code :"
	 * +revertCode);updateQry=
	 * "update instant_msg set flag='err',status='Pending',update_date='"
	 * +DateUtil.getCurrentDateUSFormat()+"',update_time='"+
	 * DateUtil.getCurrentTime()+"' where id='"+id+"'"; new
	 * HelpdeskUniversalHelper().updateData(updateQry, connection); url = null;
	 * }
	 * 
	 * System.out.println(
	 * "SMS Not Send Successfully From Second Route and Get Revert Code :" +
	 * revertCode); updateQry =
	 * "update instant_msg set flag='err',status='Pending',update_date='" +
	 * DateUtil.getCurrentDateUSFormat() + "',update_time='" +
	 * DateUtil.getCurrentTime() + "' where id='" + id + "'";
	 * HUH.updateData(updateQry, connection); url = null; } else {
	 * System.out.println
	 * ("SMS Not Send Successfully From Second Route and Get Revert Code :" +
	 * revertCode); updateQry =
	 * "update instant_msg set flag='err',status='Pending',update_date='" +
	 * DateUtil.getCurrentDateUSFormat() + "',update_time='" +
	 * DateUtil.getCurrentTime() + "' where id='" + id + "'";
	 * HUH.updateData(updateQry, connection); url = null; } } catch (Exception
	 * E) { try { String URL = null; StringBuffer url = new StringBuffer(); //
	 * URL = //
	 * "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=blkhdm&password=blk31"
	 * // ; URL =
	 * "http://115.112.185.85:6060/urldreamclient/dreamurl?userName=demourl&password=demourl"
	 * ; URL = URL + "&clientid=" + clientid + "&to=";
	 * 
	 * url.append(URL); url.append(msisdn); url.append("&text=");
	 * url.append(encodedMsg); url.append("&Senderid="); url.append(senderid);
	 * 
	 * URL codedURL = new URL(url.toString()); HttpURLConnection HURLC =
	 * (HttpURLConnection) codedURL.openConnection(); HURLC.connect(); int
	 * revertCode = HURLC.getResponseCode(); HURLC.disconnect(); if (revertCode
	 * == 1201 || revertCode == 1202 || revertCode == 1204) {
	 * System.out.println(
	 * "SMS Send Successfully From Second Route and Get Revert Code :" +
	 * revertCode); url = null; } else if (revertCode == 200) {
	 * System.out.println
	 * ("SMS Not Send Successfully From Second Route and Get Revert Code :" +
	 * revertCode); updateQry =
	 * "update instant_msg set flag='ui',status='Pending',update_date='" +
	 * DateUtil.getCurrentDateUSFormat() + "',update_time='" +
	 * DateUtil.getCurrentTime() + "' where id='" + id + "'";
	 * HUH.updateData(updateQry, connection); url = null; } else if (revertCode
	 * == 505) {System.out.println(
	 * "SMS Not Send Successfully From Second Route and Get Revert Code :" +
	 * revertCode); updateQry =
	 * "update instant_msg set flag='vi',status='Pending',update_date='" +
	 * DateUtil.getCurrentDateUSFormat() + "',update_time='" +
	 * DateUtil.getCurrentTime() + "' where id='" + id + "'";
	 * HUH.updateData(updateQry, connection); url = null; } else {
	 * System.out.println
	 * ("SMS Not Send Successfully From Second Route and Get Revert Code :" +
	 * revertCode); updateQry =
	 * "update instant_msg set flag='err',status='Pending',update_date='" +
	 * DateUtil.getCurrentDateUSFormat() + "',update_time='" +
	 * DateUtil.getCurrentTime() + "' where id='" + id + "'";
	 * HUH.updateData(updateQry, connection); url = null; } } catch (Exception
	 * e2) { e2.printStackTrace(); } } } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	@SuppressWarnings({ "unchecked", "null", "rawtypes", "unused" })
	public void ReportGeneration(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH, FeedbackUniversalHelper FUH, String IPAddress)
	{/*

		try
		{
			List data4report = HUH.getReportToData(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), connection);
			if (data4report != null && data4report.size() > 0)
			{
				// System.out.println("Inside Service File Helper");
				// Local variable defined
				@SuppressWarnings("unused")
				String empname = null, mobno = null, emailid = null, subject = null, report_level = null, subdept_dept = null, deptLevel = null, sms_status = null, mail_status = null, type_report = null, id = null, deptname = null, module = null, department = null, empId = null;
				// Counts for Current Day
				int pc = 0, hc = 0, sc = 0, ic = 0, rc = 0, total = 0;
				int rAc = 0, cfrAc = 0;
				// Counts for Carry Forward
				@SuppressWarnings("unused")
				int cfpc = 0, cfhc = 0, cfsc = 0, cfic = 0, cfrc = 0, cftotal = 0, totalSnooze = 0, cfSeek = 0, seek = 0, cftotal1 = 0;
				List subDeptList = null;
				String deptComment = "NA";
				for (Iterator iterator = data4report.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null)
					{
						empname = object[0].toString();
					} else
					{
						empname = "NA";
					}

					if (object[1] != null)
					{
						mobno = object[1].toString();
					} else
					{
						mobno = "NA";
					}

					if (object[2] != null)
					{
						emailid = object[2].toString();
					} else
					{
						emailid = "NA";
					}

					if (object[3] != null)
					{
						subject = object[3].toString();
					} else
					{
						subject = "NA";
					}

					if (object[4] != null)
					{
						report_level = object[4].toString();
					}

					if (object[5] != null)
					{
						subdept_dept = object[5].toString();
					}

					if (object[6] != null)
					{
						deptLevel = object[6].toString();
					}

					if (object[7] != null)
					{
						sms_status = object[7].toString();
					}

					if (object[8] != null)
					{
						mail_status = object[8].toString();
					}

					if (object[9] != null)
					{
						type_report = object[9].toString();
					}

					if (object[10] != null)
					{
						id = object[10].toString();
					}

					if (object[11] != null)
					{
						module = object[11].toString();
					}

					if (object[12] != null && !object[12].toString().equals(""))
					{
						department = object[12].toString();
					} else
					{
						department = "NA";
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						empId = object[13].toString();
					} else
					{
						empId = "NA";
					}

					String newDate = null;
					if (!type_report.equals("") && !type_report.equals("NA"))
					{
						newDate = HUH.getNewDate(type_report);
					}
					String updateQry = "update report_configuration set report_date='" + newDate + "' where id='" + id + "'";
					boolean updateReport = HUH.updateData(updateQry, connection);

					
					 * if (module != null && !module.equals("") &&
					 * module.equalsIgnoreCase("HDM") && updateReport) { List
					 * currentdayCounter = new ArrayList(); List CFCounter = new
					 * ArrayList(); List<FeedbackPojo> currentDayResolvedData =
					 * null; List<FeedbackPojo> currentDayPendingData = null;
					 * List<FeedbackPojo> currentDaySnoozeData = null;
					 * List<FeedbackPojo> currentDayHPData = null;
					 * List<FeedbackPojo> currentDayIgData = null;
					 * List<FeedbackPojo> CFData = null;
					 * 
					 * // get Data when Dept Level is 2 in Organization if
					 * (deptLevel.equals("2")) { // getting the data for All the
					 * departments if (report_level.equals("2")) {
					 * currentdayCounter = HUH.getTicketCounters(report_level,
					 * type_report, true, subdept_dept, deptLevel, connection);
					 * CFCounter = HUH.getTicketCounters(report_level,
					 * type_report, false, subdept_dept, deptLevel, connection);
					 * currentDayResolvedData = HUH.getTicketData(report_level,
					 * type_report, true, "Resolved", subdept_dept,
					 * deptLevel,connection); currentDayPendingData =
					 * HUH.getTicketData(report_level, type_report, true,
					 * "Pending", subdept_dept, deptLevel,connection);
					 * currentDaySnoozeData = HUH.getTicketData(report_level,
					 * type_report, true, "Snooze", subdept_dept,
					 * deptLevel,connection); currentDayHPData =
					 * HUH.getTicketData(report_level, type_report, true,
					 * "High Priority", subdept_dept, deptLevel,connection);
					 * currentDayIgData = HUH.getTicketData(report_level,
					 * type_report, true, "Ignore", subdept_dept, deptLevel,
					 * connection); CFData = HUH.getTicketData(report_level,
					 * type_report, false, "", subdept_dept, deptLevel,
					 * connection); } // getting the data for specific
					 * department else if (report_level.equals("1")) {
					 * subDeptList = HUH.getSubDeptList(subdept_dept,
					 * connection); if (subDeptList != null) { currentdayCounter
					 * = HUH.getTicketCounters(report_level, type_report, true,
					 * subdept_dept, deptLevel, connection); CFCounter =
					 * HUH.getTicketCounters(report_level, type_report, false,
					 * subdept_dept, deptLevel, connection);
					 * currentDayResolvedData = HUH.getTicketData(report_level,
					 * type_report, true, "Resolved", subdept_dept,
					 * deptLevel,connection); currentDayPendingData =
					 * HUH.getTicketData(report_level, type_report, true,
					 * "Pending", subdept_dept, deptLevel,connection);
					 * currentDaySnoozeData = HUH.getTicketData(report_level,
					 * type_report, true, "Snooze", subdept_dept,
					 * deptLevel,connection); currentDayHPData =
					 * HUH.getTicketData(report_level, type_report, true,
					 * "High Priority", subdept_dept, deptLevel,connection);
					 * currentDayIgData = HUH.getTicketData(report_level,
					 * type_report, true, "Ignore", subdept_dept,
					 * deptLevel,connection); CFData =
					 * HUH.getTicketData(report_level, type_report, false, "",
					 * subdept_dept, deptLevel, connection); // } } } } // get
					 * Data when Dept Level is 1 in Organization
					 * 
					 * // Check Counter for Current day if (currentdayCounter !=
					 * null && currentdayCounter.size() > 0) { for (Iterator
					 * iterator2 = currentdayCounter.iterator();
					 * iterator2.hasNext();) { Object[] object2 = (Object[])
					 * iterator2.next(); if
					 * (object2[1].toString().equalsIgnoreCase("Pending")) { pc
					 * = Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("Snooze")) { sc =
					 * Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("High Priority"))
					 * { hc = Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("Ignore")) { ic =
					 * Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("Resolved")) { rc
					 * = Integer.parseInt(object2[0].toString()); } } total = pc
					 * + sc + hc + ic + rc; } // Check Counter for Carry Forward
					 * if (CFCounter != null && CFCounter.size() > 0) { for
					 * (Iterator iterator2 = CFCounter.iterator();
					 * iterator2.hasNext();) { Object[] object3 = (Object[])
					 * iterator2.next(); if
					 * (object3[1].toString().equalsIgnoreCase("Pending")) {
					 * cfpc = Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("Snooze")) { cfsc
					 * = Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("High Priority"))
					 * { cfhc = Integer.parseInt(object3[0].toString()); } }
					 * cftotal = cfpc + cfsc + cfhc; } String report_sms =
					 * "Dear " + empname + ", For All Ticket Status as on " +
					 * DateUtil.getCurrentDateIndianFormat() + ": Pending: " +
					 * pc + ", C/F Pending: " + cftotal + ", Snooze: " + sc +
					 * ", Ignore: " + ic + ", Resolved: " + rc + "."; if (mobno
					 * != null && mobno != "" && mobno != "NA" && mobno.length()
					 * == 10 && (mobno.startsWith("9") || mobno.startsWith("8")
					 * || mobno.startsWith("7"))) {
					 * 
					 * @SuppressWarnings("unused") boolean putsmsstatus =
					 * CH.addMessage(empname, department, mobno, report_sms,
					 * subject, "Pending", "0", "HDM", connection); }
					 * 
					 * String filepath = new
					 * DailyReportExcelWrite4Attach().writeDataInExcel
					 * (currentdayCounter, CFCounter, currentDayResolvedData,
					 * currentDayPendingData, currentDaySnoozeData,
					 * currentDayHPData, currentDayIgData, CFData, deptLevel,
					 * type_report); String mailtext =
					 * HUH.getConfigMailForReport(pc, hc, sc, ic, rc, total,
					 * cfpc, cfsc, cfhc, cftotal, empname,
					 * currentDayResolvedData, currentDayPendingData,
					 * currentDaySnoozeData, currentDayHPData, currentDayIgData,
					 * CFData); CH.addMail(empname, department, emailid,
					 * subject, mailtext, "Report", "Pending", "0", filepath,
					 * "HDM", connection); }
					 * 
					 * else if (module != null && !module.equals("") &&
					 * module.equalsIgnoreCase("FM") && updateReport) {
					 * 
					 * System.out.println("For Fedback "); // For Feedback
					 * FeedbackHelper FRH=new FeedbackHelper();
					 * 
					 * 
					 * List currentdayCounter = new ArrayList(); List CFCounter
					 * = new ArrayList(); List<FeedbackPojo>
					 * currentDayResolvedData = null; List<FeedbackPojo>
					 * currentDayPendingData = null; List<FeedbackPojo>
					 * currentDaySnoozeData = null; List<FeedbackPojo>
					 * currentDayHPData = null; List<FeedbackPojo>
					 * currentDayIgData = null; List<FeedbackPojo> CFData =
					 * null; List<FeedbackReportPojo> FRPList = null;
					 * FeedbackReportPojo FRP = null;
					 * 
					 * FeedbackReportPojo FRP4Counter = null; // get Data when
					 * Dept Level is 2 in Organization int
					 * cfp_Total=0,cd_Total=0
					 * ,cd_Pending=0,total_snooze=0,CDR_Total=0;
					 * 
					 * if (deptLevel.equals("2")) { // getting the data for All
					 * the departments if (report_level.equals("2")) {
					 * deptComment="All";
					 * subject="Patient Delight Score for All Dept. as on "
					 * +DateUtil
					 * .getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime
					 * ().substring(0, 5)+""; StringBuilder sb = new
					 * StringBuilder();sb.append(
					 * "select distinct dept.id,dept.deptName from feedback_type as feed"
					 * );sb.append(
					 * " inner join department as dept on feed.dept_subdept=dept.id"
					 * ); sb.append(" where feed.moduleName='FM'");
					 * 
					 * List deptList = new
					 * HelpdeskUniversalHelper().getData(sb.toString(),
					 * connection); if (deptList!=null && deptList.size()>0) {
					 * FRPList = new ArrayList<FeedbackReportPojo>(); for
					 * (Iterator iterator2 = deptList.iterator();
					 * iterator2.hasNext();) { Object[] object2 = (Object[])
					 * iterator2.next(); FRP = new FeedbackReportPojo();
					 * //System
					 * .out.println("O index value is   "+object2[0].toString
					 * ()); CFCounter.clear(); currentdayCounter.clear();
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * FRP.setCFP(0);FRP.setCDT(0);FRP.setCDP(0);FRP.setTS(0);FRP
					 * .setCDR(0); pc=0;hc = 0;sc = 0;ic = 0;rc = 0;total = 0;
					 * rAc = 0;cfrAc = 0; cfpc = 0; cfhc = 0; cfsc = 0; cfic =
					 * 0; cfrc = 0; cftotal = 0;totalSnooze = 0;
					 * 
					 * currentdayCounter = FRH.getTicketCounters(report_level,
					 * type_report, true, object2[0].toString(), deptLevel,
					 * connection); CFCounter =
					 * FRH.getTicketCounters(report_level, type_report, false,
					 * object2[0].toString(), deptLevel, connection); if
					 * (currentdayCounter != null && currentdayCounter.size() >
					 * 0) {
					 * //System.out.println("Current day Counter for "+object2
					 * [1].toString()); for (Iterator iterator3 =
					 * currentdayCounter.iterator(); iterator3.hasNext();) {
					 * Object[] object3 = (Object[]) iterator3.next(); if
					 * (object3[1].toString().equalsIgnoreCase("Pending")) { pc
					 * = Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("Snooze")) { sc =
					 * Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("High Priority"))
					 * { hc = Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("Ignore")) { ic =
					 * Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("Resolved")) { rc
					 * = Integer.parseInt(object3[0].toString()); } } total = pc
					 * + sc + hc +ic + rc; } // Check Counter for Carry Forward
					 * if (CFCounter != null && CFCounter.size() > 0) {
					 * //System.
					 * out.println("Carry Forward  Counter for "+object2
					 * [1].toString()); for (Iterator iterator4 =
					 * CFCounter.iterator(); iterator4.hasNext();) { Object[]
					 * object4 = (Object[]) iterator4.next(); if
					 * (object4[1].toString().equalsIgnoreCase("Pending")) {
					 * cfpc = Integer.parseInt(object4[0].toString()); } else if
					 * (object4[1].toString().equalsIgnoreCase("Snooze")) { cfsc
					 * = Integer.parseInt(object4[0].toString()); } else if
					 * (object4[1].toString().equalsIgnoreCase("High Priority"))
					 * { cfhc = Integer.parseInt(object4[0].toString()); } }
					 * cftotal = cfpc + cfhc; } totalSnooze = sc + cfsc; //int
					 * cfp_Total
					 * =0,cd_Total=0,cd_Pending,total_snooze=0,CDR_Total=0; if
					 * ((CFCounter!=null && CFCounter.size()>0) ||
					 * (currentdayCounter!=null && currentdayCounter.size()>0))
					 * {
					 * //System.out.println("Final Block "+object2[1].toString(
					 * )); FRP.setDeptName(object2[1].toString());
					 * FRP.setCFP(cftotal); FRP.setCDT(total); FRP.setCDP(pc);
					 * FRP.setTS(totalSnooze); FRP.setCDR(rc); FRPList.add(FRP);
					 * cfp_Total=cfp_Total+cftotal; cd_Total=cd_Total+total;
					 * cd_Pending = cd_Pending+pc;
					 * total_snooze=total_snooze+totalSnooze; CDR_Total
					 * =CDR_Total+rc; } } }
					 * 
					 * currentDayResolvedData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Resolved", subdept_dept, deptLevel,connection);
					 * currentDayPendingData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Pending", subdept_dept, deptLevel,connection);
					 * currentDaySnoozeData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Snooze", subdept_dept, deptLevel,connection);
					 * currentDayHPData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "High Priority", subdept_dept,
					 * deptLevel,connection); currentDayIgData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Ignore", subdept_dept, deptLevel, connection);
					 * CFData = FRH.getTicketDataforReport(report_level,
					 * type_report, false, "", subdept_dept, deptLevel,
					 * connection);
					 * 
					 * int pp=0,pn=0,All=0,NR=0,Neg=0;
					 * 
					 * // Code For First Block(Paper Mode) List paperData = new
					 * FeedbackHelper().getPaperData(type_report, connection);
					 * 
					 * if (paperData!=null && paperData.size()>0) { for
					 * (Iterator iterator2 = paperData.iterator();
					 * iterator2.hasNext();) { Object[] object3 = (Object[])
					 * iterator2.next(); if (object3[0]!=null &&
					 * !object3[0].toString().equals("") &&
					 * object3[0].toString().equalsIgnoreCase("No")) {
					 * pn=Integer.parseInt(object3[1].toString()); } else if
					 * (object3[0]!=null && !object3[0].toString().equals("") &&
					 * object3[0].toString().equalsIgnoreCase("Yes")) {
					 * pp=Integer.parseInt(object3[1].toString()); } } }
					 * 
					 * // SMS Mode // SMS Mode // For Total Feedback Seek
					 * Counters // All = new
					 * FeedbackHelper().getSMSData(type_report, "A",
					 * connection); All=new
					 * FeedbackHelper().getSMSDataForTotalSeek(type_report,
					 * connection); // NR = new
					 * FeedbackHelper().getSMSData(type_report, "NR",
					 * connection); NR=new
					 * FeedbackHelper().getRevertedSMSDetails(true, false,
					 * type_report, connection);
					 * System.out.println(true+"<<<"+false
					 * +">>>"+type_report+"<<>><<><><>"+connection); // Neg =
					 * new FeedbackHelper().getSMSData(type_report, "N",
					 * connection);
					 * System.out.println(false+"<<<"+true+">>>"+type_report
					 * +"<<>><<><><>"+connection); Neg=new
					 * FeedbackHelper().getRevertedSMSDetails(false, true,
					 * type_report, connection);
					 * 
					 * 
					 * FRP4Counter = new FeedbackReportPojo();
					 * FRP4Counter.setPt(pp+pn); FRP4Counter.setPp(pp);
					 * FRP4Counter.setPn(pn); FRP4Counter.setSt(All);
					 * FRP4Counter.setSnr(NR); FRP4Counter.setSn(Neg); } //
					 * getting the data for specific department else if
					 * (report_level.equals("1")) { deptComment=department;
					 * subject
					 * ="Patient Delight Score for "+deptComment+" as on "
					 * +DateUtil
					 * .getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime
					 * ().substring(0, 5)+""; currentdayCounter =
					 * FRH.getTicketCounters(report_level, type_report, true,
					 * subdept_dept, deptLevel, connection); CFCounter =
					 * FRH.getTicketCounters(report_level, type_report, false,
					 * subdept_dept, deptLevel, connection);
					 * currentDayResolvedData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Resolved", subdept_dept, deptLevel,connection);
					 * currentDayPendingData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Pending", subdept_dept, deptLevel,connection);
					 * currentDaySnoozeData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Snooze", subdept_dept, deptLevel,connection);
					 * currentDayHPData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "High Priority", subdept_dept,
					 * deptLevel,connection); currentDayIgData =
					 * FRH.getTicketDataforReport(report_level, type_report,
					 * true, "Ignore", subdept_dept, deptLevel, connection);
					 * CFData = FRH.getTicketDataforReport(report_level,
					 * type_report, false, "", subdept_dept, deptLevel,
					 * connection); } // Check Counter for Current day if
					 * (currentdayCounter != null && currentdayCounter.size() >
					 * 0) { for (Iterator iterator2 =
					 * currentdayCounter.iterator(); iterator2.hasNext();) {
					 * Object[] object2 = (Object[]) iterator2.next(); if
					 * (object2[1].toString().equalsIgnoreCase("Pending")) { pc
					 * = Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("Snooze")) { sc =
					 * Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("High Priority"))
					 * { hc = Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("Ignore")) { ic =
					 * Integer.parseInt(object2[0].toString()); } else if
					 * (object2[1].toString().equalsIgnoreCase("Resolved")) { rc
					 * = Integer.parseInt(object2[0].toString()); } } total = pc
					 * + sc + hc +ic + rc; } // Check Counter for Carry Forward
					 * if (CFCounter != null && CFCounter.size() > 0) { for
					 * (Iterator iterator2 = CFCounter.iterator();
					 * iterator2.hasNext();) { Object[] object3 = (Object[])
					 * iterator2.next(); if
					 * (object3[1].toString().equalsIgnoreCase("Pending")) {
					 * cfpc = Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("Snooze")) { cfsc
					 * = Integer.parseInt(object3[0].toString()); } else if
					 * (object3[1].toString().equalsIgnoreCase("High Priority"))
					 * { cfhc = Integer.parseInt(object3[0].toString()); } }
					 * cftotal = cfpc + cfhc; } totalSnooze = sc + cfsc; }
					 * 
					 * String report_sms = ""; if (report_level!=null &&
					 * !report_level.equals("") && report_level.equals("1")) {
					 * report_sms = "Dear " + empname +
					 * ", For "+deptComment+" Ticket Status as on " +
					 * DateUtil.getCurrentDateIndianFormat()+ ": Pending: " + pc
					 * + ", C/F Pending: " + cftotal + ", Snooze: " +
					 * totalSnooze + ", Ignore: " + ic + ", Resolved: " + rc+
					 * "."; } else if (report_level!=null &&
					 * !report_level.equals("") && report_level.equals("2")) {
					 * report_sms = "Dear " + empname +
					 * ", For "+deptComment+" Ticket Status as on " +
					 * DateUtil.getCurrentDateIndianFormat()+ ": Pending: " +
					 * cd_Pending + ", C/F Pending: " + cfp_Total + ", Snooze: "
					 * + total_snooze + ", Ignore: " + ic + ", Resolved: " +
					 * CDR_Total+ "."; }
					 * 
					 * if (mobno != null && mobno != "" && mobno != "NA" &&
					 * mobno.length() == 10 && (mobno.startsWith("9") ||
					 * mobno.startsWith("8") || mobno.startsWith("7"))) {
					 * CH.addMessage(empname, department, mobno, report_sms,
					 * subject, "Pending", "0", "HDM", connection); } String
					 * filepath = null; if ((currentDayResolvedData!=null &&
					 * currentDayResolvedData.size()>0) ||
					 * (currentDayPendingData!=null &&
					 * currentDayPendingData.size()>0) ||
					 * (currentDaySnoozeData!=null &&
					 * currentDaySnoozeData.size()>0) || (currentDayHPData!=null
					 * && currentDayHPData.size()>0) || (currentDayIgData!=null
					 * && currentDayIgData.size()>0) || (CFData!=null &&
					 * CFData.size()>0)) {
					 * //System.out.println("Inside If for write Excel File   "
					 * ); filepath = new
					 * WriteFeedbackData().writeDataInExcel(pc, hc, sc, ic, rc,
					 * total, cfpc, cfsc, cfhc,
					 * cftotal,totalSnooze,report_level,
					 * FRPList,deptComment,type_report,FRP4Counter,
					 * empname,currentDayResolvedData, currentDayPendingData,
					 * currentDaySnoozeData, currentDayHPData, currentDayIgData,
					 * CFData);
					 * //System.out.println("Generated File path   >>>>>>>>>   "
					 * +filepath); String mailtext =
					 * FRH.getConfigMailForReport(pc, hc, sc, ic, rc, total,
					 * cfpc, cfsc, cfhc,
					 * cftotal,totalSnooze,report_level,FRPList
					 * ,deptComment,type_report,FRP4Counter,
					 * empname,currentDayResolvedData, currentDayPendingData,
					 * currentDaySnoozeData, currentDayHPData, currentDayIgData,
					 * CFData
					 * ,IPAddress,cfp_Total,cd_Total,cd_Pending,total_snooze
					 * ,CDR_Total); CH.addMail(empname, department, emailid,
					 * subject, mailtext, "Report", "Pending", "0", filepath,
					 * "HDM", connection); }
					 * 
					 * }
					 
					if (module != null && !module.equals("") && module.equalsIgnoreCase("compliance") && updateReport)
					{

						ComplianceServiceHelper srvcHelper = new ComplianceServiceHelper();
						CommunicationHelper cmnHelper = new CommunicationHelper();
						CommonOperInterface cbt = new CommonConFactory().createInterface();
						StringBuilder mailtext = new StringBuilder("");
						List validData = null;
						WorkingHourHelper WHH = new WorkingHourHelper();
						List<String> workingDayList = WHH.getDayDate(DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()), connection, cbt, "COMPL");
						String workingDay = workingDayList.get(1).toString();
						if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.getNewDate("day", -1, workingDay)))
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// DateUtil.getCurrentDateUSFormat(),
							// "equalCondition");
							validData = srvcHelper.getReportData(connection, report_level, type_report, null, DateUtil.getCurrentDateUSFormat(), "equalCondition");
						} else
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// workingDayList.get(1).toString(),
							// "betweenCondition");
							validData = srvcHelper.getReportData(connection, report_level, type_report, null, workingDayList.get(1).toString(), "betweenCondition");
						}
						// List
						// validData=srvcHelper.getReportData(connection,report_level,type_report,null);
						List<ComplianceDashboardBean> listForActionDataMail = new ArrayList<ComplianceDashboardBean>();
						List<ComplianceDashboardBean> listForPendingDataMail = new ArrayList<ComplianceDashboardBean>();

						if (validData != null && validData.size() > 0)
						{
							ComplianceDashboardBean obj = null;
							String reminderFor = null;
							for (Iterator iterator2 = validData.iterator(); iterator2.hasNext();)
							{
								obj = new ComplianceDashboardBean();
								Object[] objects = (Object[]) iterator2.next();

								if (objects[0] != null)
								{
									List lastCompDeatil = srvcHelper.getLastCompActionData(objects[0].toString(), connection);
									if (lastCompDeatil != null && lastCompDeatil.size() > 0)
									{
										for (Iterator iterator1 = lastCompDeatil.iterator(); iterator1.hasNext();)
										{
											Object[] object1 = (Object[]) iterator1.next();
											if (object1[0] != null)
											{
												obj.setActionTakenOn(DateUtil.convertDateToIndianFormat(object1[0].toString()));
											} else
											{
												obj.setActionTakenOn("NA");
											}

											if (object1[1] != null)
											{
												obj.setActionBy(object1[1].toString());
											} else
											{
												obj.setActionBy("NA");
											}

											if (object1[2] != null)
											{
												obj.setLastActionComment(object1[2].toString());
											} else
											{
												obj.setLastActionComment("NA");
											}
										}
									}
								}
								if (objects[1] != null)
									obj.setFrequency(new ComplianceReminderHelper().getFrequencyName(objects[1].toString()));
								else
									obj.setFrequency("NA");

								if (objects[2] != null)
									obj.setTaskBrief(objects[2].toString());
								else
									obj.setTaskBrief("NA");

								if (objects[4] != null)
									obj.setDueDate(DateUtil.convertDateToIndianFormat(objects[4].toString()));
								else
									obj.setDueDate("NA");

								if (objects[5] != null)
									obj.setActionStatus(objects[5].toString());
								else
									obj.setActionStatus("NA");

								if (objects[6] != null)
									obj.setTaskName(objects[6].toString());
								else
									obj.setTaskName("NA");

								if (objects[7] != null)
									obj.setTaskType(objects[7].toString());
								else
									obj.setTaskType("NA");

								if (objects[8] != null)
									obj.setDepartName(objects[8].toString());
								else
									obj.setDepartName("NA");

								if (objects[3] != null)
									reminderFor = objects[3].toString();
								else
									reminderFor = "NA";

								String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));

								StringBuilder builder = new StringBuilder();
								builder.append("SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName FROM employee_basic AS emp ");
								builder.append("INNER JOIN compliance_contacts AS cont ON cont.emp_id=emp.id ");
								builder.append("INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cont.id IN(" + contactId + ")");
								List data1 = new createTable().executeAllSelectQuery(builder.toString(), connection);

								if (data1 != null && data1.size() > 0)
								{
									String mobileNo = null, emailId = null, empName = null;
									String mappedTeam = null;
									StringBuilder str = new StringBuilder();
									for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
									{
										Object object2[] = (Object[]) iterator3.next();
										if (object2[2] != null)
										{
											str.append(object2[2].toString() + ", ");
										}
									}
									if (str != null && str.toString().length() > 0)
									{
										mappedTeam = str.toString().substring(0, str.toString().length() - 2);
									}
									obj.setMappedTeam(mappedTeam);
								} else
									obj.setMappedTeam("NA");

								listForPendingDataMail.add(obj);
							}
						}

						List todayActionData = null;
						if (DateUtil.getCurrentDateUSFormat().equals(DateUtil.getNewDate("day", -1, workingDay)))
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// DateUtil.getCurrentDateUSFormat(),
							// "equalCondition");
							todayActionData = srvcHelper.getTodayActionData(connection, report_level, type_report, null, DateUtil.getCurrentDateUSFormat(), "equalCondition");
						} else
						{
							// data = srvcHelper.getTodayReminderFor(connection,
							// workingDayList.get(1).toString(),
							// "betweenCondition");
							todayActionData = srvcHelper.getTodayActionData(connection, report_level, type_report, null, workingDayList.get(1).toString(), "betweenCondition");
						}
						if (todayActionData != null && todayActionData.size() > 0)
						{
							ComplianceDashboardBean obj = null;
							String reminderFor = null;
							for (Iterator iterator5 = todayActionData.iterator(); iterator5.hasNext();)
							{
								obj = new ComplianceDashboardBean();
								Object[] object4 = (Object[]) iterator5.next();

								if (object4[1] != null)
									obj.setFrequency(new ComplianceReminderHelper().getFrequencyName(object4[1].toString()));
								else
									obj.setFrequency("NA");

								if (object4[2] != null)
									obj.setTaskBrief(object4[2].toString());
								else
									obj.setTaskBrief("NA");

								if (object4[3] != null)
									reminderFor = object4[3].toString();
								else
									reminderFor = "NA";

								if (object4[4] != null)
									obj.setDueDate(DateUtil.convertDateToIndianFormat(object4[4].toString()));
								else
									obj.setDueDate("NA");

								if (object4[5] != null)
								{
									if (object4[5].toString().equalsIgnoreCase("Recurring"))
									{
										obj.setActionStatus("Done & " + object4[5].toString());
									} else
									{
										obj.setActionStatus(object4[5].toString());
									}
								} else
								{
									obj.setActionStatus("NA");
								}

								if (object4[6] != null)
									obj.setTaskName(object4[6].toString());
								else
									obj.setTaskName("NA");

								if (object4[7] != null)
									obj.setTaskType(object4[7].toString());
								else
									obj.setTaskType("NA");

								if (object4[8] != null)
									obj.setDepartName(object4[8].toString());
								else
									obj.setDepartName("NA");

								if (object4[9] != null)
									obj.setEmpId(object4[9].toString());
								else
									obj.setEmpId("NA");

								if (object4[10] != null)
									obj.setComment(object4[10].toString());
								else
									obj.setComment("NA");

								String contactId = reminderFor.replace("#", ",").substring(0, (reminderFor.toString().length() - 1));

								StringBuilder builder = new StringBuilder();
								builder.append("SELECT emp.mobOne,emp.emailIdOne,emp.empName,emp.id,dept.deptName FROM employee_basic AS emp ");
								builder.append("INNER JOIN compliance_contacts AS cont ON cont.emp_id=emp.id ");
								builder.append("INNER JOIN department AS dept ON dept.id=emp.deptname WHERE cont.id IN(" + contactId + ")");
								List data1 = new createTable().executeAllSelectQuery(builder.toString(), connection);

								if (data1 != null && data1.size() > 0)
								{

									String mobileNo = null, emailId = null, empName = null;
									String mappedTeam = null;
									StringBuilder str = new StringBuilder();
									for (Iterator iterator3 = data1.iterator(); iterator3.hasNext();)
									{
										Object object2[] = (Object[]) iterator3.next();
										if (object2[2] != null)
										{
											str.append(object2[2].toString() + ", ");
										}
									}
									if (str != null && str.toString().length() > 0)
									{
										mappedTeam = str.toString().substring(0, str.toString().length() - 2);
									}
									obj.setMappedTeam(mappedTeam);

								} else
									obj.setMappedTeam("NA");

								listForActionDataMail.add(obj);
							}
						}
						if ((listForPendingDataMail != null || listForActionDataMail != null) && (listForActionDataMail.size() > 0 || listForPendingDataMail.size() > 0))
						{
							String filePath = new DailyCompReportExcelWrite4Attach().writeDataInExcel(listForActionDataMail, listForPendingDataMail, null, type_report);
							String mailSubject = "Today Operational Task Status";
							String mailText = new ComplianceReminderHelper().configReportComp(listForPendingDataMail, listForActionDataMail, empname, mailSubject);
							cmnHelper.addMail(empname, department, emailid, mailSubject, mailText, "", "Pending", "0", filePath, "Compliance", connection);
						} else
						{
							String mailSubject = "Today There Are No Operational Task";
							String mailText = new ComplianceReminderHelper().configReportComp(listForPendingDataMail, listForActionDataMail, empname, mailSubject);
							cmnHelper.addMail(empname, department, emailid, mailSubject, mailText, "", "Pending", "0", "", "Compliance", connection);
						}
					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("KR") && updateReport)
					{
						KRServiceHelper SRH = new KRServiceHelper();
						List<KRPojo> sharedSummaryData = SRH.getAllSharedSummary(connection);
						List<KRPojo> todayShareData = SRH.getAllTodaySharedSummary(connection);
						List<KRPojo> actionTakenData = SRH.getAllActionTakenData(connection);
						if ((sharedSummaryData != null && sharedSummaryData.size() > 0) || (todayShareData != null && todayShareData.size() > 0) || (actionTakenData != null && actionTakenData.size() > 0))
						{
							String mailtext = SRH.getConfigMailForReport(empname, sharedSummaryData, actionTakenData, todayShareData);
							// String
							// filepath=SRH.writeDataInExcel(sharedSummaryData,actionTakenData);
							if (mailtext != null && !mailtext.equals(""))
							{
								String mailSubject = "Consolidated KR Report for " + DateUtil.getCurrentDateIndianFormat();
								new CommunicationHelper().addMail(empname, department, emailid, mailSubject, mailtext, "", "Pending", "0", "", "KR", connection);
								// new
								// GenericMailSender().sendMail("karnikag@dreamsol.biz",
								// "Testing", mailtext, "", "smtp.gmail.com",
								// "465", "karnikag@dreamsol.biz",
								// "karnikagupta");
								// new
								// CommunicationHelper().addMail("Mr. Lalit","Management","lalit.kaushik@jbm.co.in","Evening Report",
								// mailtext,"","Pending",
								// "0",null,"DAR",connection);
							}
						}

					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("DAR") && updateReport)
					{
						String mailText = null;
						DARReportHelper DARR = new DARReportHelper();
						CopyOnWriteArrayList<DarSubmissionPojoBean> reportData = DARR.getAllData(connection);
						String holiday = "SELECT holidayname FROM holiday_list where (fdate = '" + DateUtil.getCurrentDateUSFormat() + "' OR tdate = '" + DateUtil.getCurrentDateUSFormat() + "')";
						List holidayData = new createTable().executeAllSelectQuery(holiday, connection);
						String day = DateUtil.findDayFromDate(DateUtil.getCurrentDateIndianFormat());
						if (holidayData != null && holidayData.size() > 0)
						{
							mailText = "There is no task update for today due to " + holidayData.get(0).toString() + " holiday";
						} else if (day != null && day.equalsIgnoreCase("Sunday"))
						{
							mailText = "There is no task update for today due to Sunday";
						} else if (reportData != null && reportData.size() > 0)
						{
							mailText = DARR.getConfigMailForReport(reportData, connection);
						}
						if (mailText != null && !mailText.equals(""))
						{
							String mailSubject = "Consolidated DAR Project Report for " + DateUtil.getCurrentDateIndianFormat();
							if (mailText != null && !mailText.equals(""))
							{
								new CommunicationHelper().addMail(empname, department, emailid, mailSubject, mailText, "", "Pending", "0", "", "DAR", connection);
							}
						}
					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("DAR1") && updateReport)
					{
						String searchdate = DateUtil.getCurrentDateUSFormat();
						DARReportHelper DARR = new DARReportHelper();
						List<DarSubmissionPojoBean> todayReview = DARR.getAllTodayReviewData(connection, searchdate);
						List<DarSubmissionPojoBean> tommorowReview = DARR.getAllTommorowReviewData(connection, searchdate);
						List<DarSubmissionPojoBean> dayAfterReview = DARR.getAllDayAfterReviewData(connection, searchdate);
						if (todayReview != null && todayReview.size() > 0)
						{
							String mailText = DARR.getConfigMailForReviewReport(todayReview, tommorowReview, dayAfterReview);
							if (mailText != null && !mailText.equals(""))
							{
								String mailSubject = "Functional & Technical Review Status for " + DateUtil.getCurrentDateIndianFormat();
								if (mailText != null && !mailText.equals(""))
								{
									new CommunicationHelper().addMail(empname, department, emailid, mailSubject, mailText, "", "Pending", "0", "", "DAR", connection);
								}
							}
						}
					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("DAR2") && updateReport)
					{
						DARReportHelper DARR = new DARReportHelper();
						String searchdate = DateUtil.getCurrentDateUSFormat();
						List<DarSubmissionPojoBean> alreadyFree = DARR.getAllAlreadyFreeData(connection);
						List<DarSubmissionPojoBean> todayFree = DARR.getAllTodayFreeData(connection, searchdate);
						List<DarSubmissionPojoBean> tommorowFree = DARR.getAllTommorowFreeData(connection, searchdate);
						String mailtext = null;
						String holiday = "SELECT holidayname FROM 1_clouddb.holiday_list where (fdate = '" + DateUtil.getCurrentDateUSFormat() + "' OR tdate = '" + DateUtil.getCurrentDateUSFormat() + "')";
						List holidayData = new createTable().executeAllSelectQuery(holiday, connection);
						String day = DateUtil.findDayFromDate(DateUtil.getCurrentDateIndianFormat());
						if (holidayData != null && holidayData.size() > 0)
						{
							mailtext = "There is no task update for today due to " + holidayData.get(0).toString() + " holiday";
						} else if (day != null && day.equalsIgnoreCase("Sunday"))
						{
							mailtext = "There is no task update for today due to Sunday";
						} else
						{
							mailtext = DARR.getConfigMailForFreeResourceReport(alreadyFree, todayFree, tommorowFree);
						}
						if (mailtext != null && !mailtext.equals(""))
						{
							String mailSubject = "Consolidated Project Allotment Status for " + DateUtil.getCurrentDateIndianFormat();
							if (mailtext != null && !mailtext.equals(""))
							{
								new CommunicationHelper().addMail(empname, department, emailid, mailSubject, mailtext, "", "Pending", "0", "", "DAR", connection);
							}
						}
					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("HR") && updateReport)
					{
						HRReportHelper HRR = new HRReportHelper();
						List<AttendancePojo> reportData = HRR.getAllData(connection);
						List<AttendancePojo> reportDataBeforeDate = HRR.getAllDataBeforeDate(connection);
						List<AttendancePojo> reportForApprovalStatus = HRR.getApprovalStatus(connection);
						List<AttendancePojo> reportForNewJoiners = HRR.getNewJoiners(connection);
						List<AttendancePojo> reportForEvents = HRR.getEvents(connection);
						List<AttendancePojo> reportForAbsentData = HRR.getAbsentDataBeforeDate(connection);
						if (reportForAbsentData != null || reportForAbsentData.size() > 0 || reportForEvents != null || reportForEvents.size() > 0 || reportForNewJoiners != null || reportForNewJoiners.size() > 0 || reportData != null || reportData.size() > 0 || reportForApprovalStatus != null || reportForApprovalStatus.size() > 0 || reportDataBeforeDate != null || reportDataBeforeDate.size() > 0)
						{
							String mailText = HRR.getConfigMailForReport(reportData, reportForApprovalStatus, reportForNewJoiners, reportDataBeforeDate, reportForEvents, reportForAbsentData);

							// String
							// filepath=HRR.writeDataInExcel(reportData,reportForApprovalStatus,reportForNewJoiners);
							if (mailText != null && !mailText.equals(""))
							{
								new CommunicationHelper().addMail(empname, department, emailid, "Human Resource Summary Report for " + DateUtil.getCurrentDateIndianFormat(), mailText, "", "Pending", "0", "", "HR", connection);
								// new
								// CommunicationHelper().addMail("Ms. Sumiti Bajpai","Technical","sumitib@dreamsol.biz","Human Resource Summary Report for "
								// +DateUtil.getCurrentDate(),
								// mailtext,"","Pending",
								// "0",filepath,"HR",connection);
							}
						}

					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("DREAM_HDM") && updateReport)
					{
						List currentdayCounter = new ArrayList();
						List CFCounter = new ArrayList();
						List<FeedbackPojo> currentDayResolvedData = null;
						List<FeedbackPojo> currentDayPendingData = null;
						List<FeedbackPojo> currentDaySnoozeData = null;
						List<FeedbackPojo> currentDayHPData = null;
						List<FeedbackPojo> currentDayIgData = null;
						List<FeedbackPojo> CFData = null;
						ComplaintReportHelper CRH = new ComplaintReportHelper();
						if (report_level.equals("2"))
						{
							currentdayCounter = CRH.getTicketCounters(report_level, type_report, true, "4", connection);
							CFCounter = CRH.getTicketCounters(report_level, type_report, false, "4", connection);
							currentDayResolvedData = CRH.getTicketData(report_level, type_report, true, "Resolved", "4", connection);
							currentDayPendingData = CRH.getTicketData(report_level, type_report, true, "Pending", "4", connection);
							currentDaySnoozeData = CRH.getTicketData(report_level, type_report, true, "Snooze", "4", connection);
							currentDayHPData = CRH.getTicketData(report_level, type_report, true, "High Priority", "4", connection);
							currentDayIgData = CRH.getTicketData(report_level, type_report, true, "Ignore", "4", connection);
							CFData = CRH.getTicketData(report_level, type_report, false, "", "4", connection);
						} else if (report_level.equals("1"))
						{
							currentdayCounter = CRH.getTicketCounters(report_level, type_report, true, "4", connection);
							CFCounter = CRH.getTicketCounters(report_level, type_report, false, "4", connection);
							currentDayResolvedData = CRH.getTicketData(report_level, type_report, true, "Resolved", "4", connection);
							currentDayPendingData = CRH.getTicketData(report_level, type_report, true, "Pending", "4", connection);
							currentDaySnoozeData = CRH.getTicketData(report_level, type_report, true, "Snooze", "4", connection);
							currentDayHPData = CRH.getTicketData(report_level, type_report, true, "High Priority", "4", connection);
							currentDayIgData = CRH.getTicketData(report_level, type_report, true, "Ignore", "4", connection);
							CFData = CRH.getTicketData(report_level, type_report, false, "", "4", connection);
						}
						// Check Counter for Current day
						if (currentdayCounter != null && currentdayCounter.size() > 0)
						{
							for (Iterator iterator2 = currentdayCounter.iterator(); iterator2.hasNext();)
							{
								Object[] object2 = (Object[]) iterator2.next();
								if (object2[1].toString().equalsIgnoreCase("Pending"))
								{
									pc = Integer.parseInt(object2[0].toString());
								} else if (object2[1].toString().equalsIgnoreCase("Snooze"))
								{
									sc = Integer.parseInt(object2[0].toString());
								} else if (object2[1].toString().equalsIgnoreCase("High Priority"))
								{
									hc = Integer.parseInt(object2[0].toString());
								} else if (object2[1].toString().equalsIgnoreCase("Ignore"))
								{
									ic = Integer.parseInt(object2[0].toString());
								} else if (object2[1].toString().equalsIgnoreCase("Resolved"))
								{
									rc = Integer.parseInt(object2[0].toString());
								}
							}
							total = pc + sc + hc + ic + rc;
						}
						// Check Counter for Carry Forward
						if (CFCounter != null && CFCounter.size() > 0)
						{
							for (Iterator iterator2 = CFCounter.iterator(); iterator2.hasNext();)
							{
								Object[] object3 = (Object[]) iterator2.next();
								if (object3[1].toString().equalsIgnoreCase("Pending"))
								{
									cfpc = Integer.parseInt(object3[0].toString());
								} else if (object3[1].toString().equalsIgnoreCase("Snooze"))
								{
									cfsc = Integer.parseInt(object3[0].toString());
								} else if (object3[1].toString().equalsIgnoreCase("High Priority"))
								{
									cfhc = Integer.parseInt(object3[0].toString());
								}
							}
							cftotal = cfpc + cfsc + cfhc;
						}
						String report_sms = "Dear " + empname + ", For All Ticket Status as on " + DateUtil.getCurrentDateIndianFormat() + ": Pending: " + pc + ", C/F " + ": " + cftotal + ", Snooze: " + sc + ", Ignore: " + ic + ", Resolved: " + rc + ".";
						if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
						{
							boolean putsmsstatus = CH.addMessage(empname, department, mobno, report_sms, "", "Pending", "0", "DREAM_HDM", connection);
						}
						String filepath = new DailyReportExcelWrite4Attach().writeDataInExcelForDream_HDM(currentdayCounter, CFCounter, currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, CFData, "", type_report);
						String mailtext = CRH.getConfigMailForReport(pc, hc, sc, ic, rc, total, cfpc, cfsc, cfhc, cftotal, empname, currentDayResolvedData, currentDayPendingData, currentDaySnoozeData, currentDayHPData, currentDayIgData, CFData);
						String mailSubject = "Ticket Summary for DreamSol Helpdesk as on " + DateUtil.getCurrentDateIndianFormat() + ", " + DateUtil.getCurrentTimeHourMin();

						new CommunicationHelper().addMail(empname, department, emailid, mailSubject, mailtext, "", "Pending", "0", filepath, "DREAM_HDM", connection);
					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("ASTM") && updateReport)
					{
						System.out.println("Inside If Block");
						String mailSubject = "Daily Summary Report for Asset As On " + DateUtil.getCurrentDateIndianFormat() + " & " + DateUtil.getCurrentTimeHourMin();
						List currentdayCounter = new ArrayList();
						List CFCounter = new ArrayList();
						List<FeedbackPojo> currentDayTrasferData = null;
						Map<String, List<FeedbackPojo>> currentDayResolvedDataMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						Map<String, List<FeedbackPojo>> currentDayPendingDataMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						Map<String, List<FeedbackPojo>> currentDaySnoozeDataMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						Map<String, List<FeedbackPojo>> currentDayHPDataMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						Map<String, List<FeedbackPojo>> currentDaySeekDataMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						Map<String, List<FeedbackPojo>> currentDayIgMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						Map<String, List<FeedbackPojo>> CFDataMap = new LinkedHashMap<String, List<FeedbackPojo>>();
						List<LoginPojo> loginReportData = new LinkedList<LoginPojo>();
						loginReportData = HUH.getLoginData(connection);
						String loginReportMailText = HUH.createMailLoginReport(loginReportData, empname);
						// List<FeedbackPojo> currentDayPendingData = null;
						// List<FeedbackPojo> currentDaySnoozeData = null;
						// List<FeedbackPojo> currentDayHPData = null;
						// List<FeedbackPojo> currentDayIgData = null;
						List<AssetPojo> finalHeaderList = null;

						// System.out.println("Report Level   " + report_level);
						// get Data when Dept Level is 2 in Organization
						// getting the data for All the departments
						if (report_level.equals("2"))
						{
							// System.out.println("Inside If when report level is 2");

							List dataList = HUH.getOutletDetail(empId, connection);
							// System.out.println("Number Of Outlets "+dataList.size());
							finalHeaderList = new ArrayList<AssetPojo>();
							AssetPojo assetPojoObject;
							if (dataList != null && dataList.size() > 0)
							{
								int pc1 = 0, hc1 = 0, sc1 = 0, ic1 = 0, rc1 = 0;
								for (Iterator iterator2 = dataList.iterator(); iterator2.hasNext();)
								{
									assetPojoObject = new AssetPojo();
									Object[] obj = (Object[]) iterator2.next();
									if (obj[0] != null && obj[1] != null)
									{
										assetPojoObject.setOutletName(obj[1].toString());
										StringBuilder outletName = new StringBuilder();
										currentdayCounter = HUH.getTicketCounters4AssetComplaint(report_level, type_report, true, subdept_dept, deptLevel, connection, obj[0].toString());
										CFCounter = HUH.getTicketCounters4AssetComplaint(report_level, type_report, false, subdept_dept, deptLevel, connection, obj[0].toString());
										List<FeedbackPojo> currentDayResolvedData = HUH.getTicketData4AssetComplaint(report_level, type_report, true, "Resolved", obj[0].toString(), deptLevel, connection);
										List<FeedbackPojo> currentDayPendingData = HUH.getTicketData4AssetComplaint(report_level, type_report, true, "Pending", obj[0].toString(), deptLevel, connection);
										List<FeedbackPojo> currentDaySnoozeData = HUH.getTicketData4AssetComplaint(report_level, type_report, true, "Snooze", obj[0].toString(), deptLevel, connection);
										// List<FeedbackPojo> currentDayHPData =
										// HUH.getTicketData4AssetComplaint(report_level,
										// type_report, true, "High Priority",
										// obj[0].toString(), deptLevel,
										// connection);
										List<FeedbackPojo> currentDaySeekData = HUH.getTicketData4AssetComplaint(report_level, type_report, true, "Hold", obj[0].toString(), deptLevel, connection);
										List<FeedbackPojo> currentDayIgData = HUH.getTicketData4AssetComplaint(report_level, type_report, true, "Ignore", obj[0].toString(), deptLevel, connection);
										List<FeedbackPojo> CFData = null;
										CFData = HUH.getTicketData4AssetComplaint(report_level, type_report, false, "", obj[0].toString(), deptLevel, connection);
										// System.out.println("currentdayCounter  >>>>>>>>>"+currentdayCounter.size());
										// System.out.println("CFCounter  >>>>>>>>>>>>> "+CFCounter.size());
										rc1 = rc1 + currentDayResolvedData.size();
										// System.out.println("currentDayResolvedData >>>>>"+rc1);
										pc1 = pc1 + currentDayPendingData.size();
										// System.out.println("currentDayPendingData >>>>>>"+pc1);
										sc1 = sc1 + currentDaySnoozeData.size();
										// System.out.println("currentDaySnoozeData >>>>>>>"+sc1);
										// System.out.println("currentDayHPData >>>>>>>>>>>"+currentDayHPData.size());
										ic1 = ic1 + currentDayIgData.size();
										// System.out.println("currentDayIgData >>>>>>>>>>>"+ic1);
										cftotal1 = cftotal1 + CFData.size();
										// System.out.println("CFData >>>>>>>>>>>>>>>>>>>>."+cftotal1);
										if (currentDayResolvedData != null && currentDayResolvedData.size() > 0)
										{
											outletName.append(obj[0].toString() + "#" + obj[1].toString() + "#");
											currentDayResolvedDataMap.put(outletName.toString(), currentDayResolvedData);
											outletName.setLength(0);
										}
										if (currentDayPendingData != null && currentDayPendingData.size() > 0)
										{
											outletName.append(obj[0].toString() + "#" + obj[1].toString() + "#");
											currentDayPendingDataMap.put(outletName.toString(), currentDayPendingData);
											outletName.setLength(0);
										}
										if (currentDaySnoozeData != null && currentDaySnoozeData.size() > 0)
										{
											outletName.append(obj[0].toString() + "#" + obj[1].toString() + "#");
											currentDaySnoozeDataMap.put(outletName.toString(), currentDaySnoozeData);
											outletName.setLength(0);
										}
										
										 * if(currentDayHPData!=null &&
										 * currentDayHPData.size()>0) {
										 * outletName
										 * .append(obj[0].toString()+"#"
										 * +obj[1].toString()+"#");
										 * currentDayHPDataMap
										 * .put(outletName.toString(),
										 * currentDayHPData);
										 * outletName.setLength(0); }
										 
										if (currentDaySeekData != null && currentDaySeekData.size() > 0)
										{
											outletName.append(obj[0].toString() + "#" + obj[1].toString() + "#");
											currentDaySeekDataMap.put(outletName.toString(), currentDaySeekData);
											outletName.setLength(0);
										}
										if (currentDayIgData != null && currentDayIgData.size() > 0)
										{
											outletName.append(obj[0].toString() + "#" + obj[1].toString() + "#");
											currentDayIgMap.put(outletName.toString(), currentDayIgData);
											outletName.setLength(0);
										}
										if (CFData != null && CFData.size() > 0)
										{
											outletName.append(obj[0].toString() + "#" + obj[1].toString() + "#");
											CFDataMap.put(outletName.toString(), CFData);
											outletName.setLength(0);
										}
										// System.out.println("Current Day Pending Map "+currentDayPendingDataMap.size());

										// Check Counter for Current day
										if (currentdayCounter != null && currentdayCounter.size() > 0)
										{
											for (Iterator iterator3 = currentdayCounter.iterator(); iterator3.hasNext();)
											{
												Object[] object2 = (Object[]) iterator3.next();
												if (object2[1].toString().equalsIgnoreCase("Pending"))
												{
													assetPojoObject.setPendingCounter(object2[0].toString());
													pc = Integer.parseInt(object2[0].toString());
												} else if (object2[1].toString().equalsIgnoreCase("Snooze"))
												{
													assetPojoObject.setSnoozeCounter(object2[0].toString());
													sc = Integer.parseInt(object2[0].toString());
												}
												
												 * else if
												 * (object2[1].toString()
												 * .equalsIgnoreCase
												 * ("High Priority")) {
												 * assetPojoObject
												 * .setHpCounter(object2
												 * [0].toString()); hc =
												 * Integer.
												 * parseInt(object2[0].toString
												 * ()); }
												 
												else if (object2[1].toString().equalsIgnoreCase("Hold"))
												{
													assetPojoObject.setSeekCounter(object2[0].toString());
													seek = Integer.parseInt(object2[0].toString());
												} else if (object2[1].toString().equalsIgnoreCase("Ignore"))
												{
													assetPojoObject.setIgnoreCounter(object2[0].toString());
													ic = Integer.parseInt(object2[0].toString());
												} else if (object2[1].toString().equalsIgnoreCase("Resolved"))
												{
													assetPojoObject.setResolveCounter(object2[0].toString());
													rc = Integer.parseInt(object2[0].toString());
													// System.out.println("Resolve Counter "+rc);
												}
											}
											total = pc + sc + seek + ic + rc;
											assetPojoObject.setTotalCounter(String.valueOf(total));
										}
										// Check Counter for Carry Forward
										if (CFCounter != null && CFCounter.size() > 0)
										{
											for (Iterator iterator3 = CFCounter.iterator(); iterator3.hasNext();)
											{
												Object[] object3 = (Object[]) iterator3.next();
												// System.out.println(object3[1].toString()+" >>>> "+object3[0].toString());
												if (object3[1].toString().equalsIgnoreCase("Pending"))
												{
													assetPojoObject.setCfPendingCounter(object3[0].toString());
													cfpc = Integer.parseInt(object3[0].toString());
												} else if (object3[1].toString().equalsIgnoreCase("Snooze"))
												{
													assetPojoObject.setCfSnoozeCounter(object3[0].toString());
													cfsc = Integer.parseInt(object3[0].toString());
												} else if (object3[1].toString().equalsIgnoreCase("Hold"))
												{
													assetPojoObject.setCfSeekCounter(object3[0].toString());
													cfSeek = Integer.parseInt(object3[0].toString());
												}
											}
											cftotal = cfpc + cfsc + cfSeek;
											System.out.println(cftotal);
											assetPojoObject.setCfTotalCounter(String.valueOf(cftotal));

										}

										
										 * pc1=pc1 +
										 * currentDayPendingData.size(); sc1=sc1
										 * + currentDaySnoozeData.size();
										 * ic1=ic1 + currentDayIgData.size();
										 * rc1=rc1 +
										 * currentDayResolvedData.size();
										 * cftotal1 = cftotal1 + CFData.size();
										 * System.out.println(
										 * " Total Pending Tickets are :"+pc1);
										 * System.out.println(
										 * " Total Snooze Tickets are :"+pc1);
										 * System.out.println(
										 * " Total Ignore Tickets are :"+pc1);
										 * System.out.println(
										 * " Total Resolve Tickets are :"+pc1);
										 * System.out.println(
										 * " Total Carry Forward Tickets are :"
										 * +pc1);
										 
									}
									finalHeaderList.add(assetPojoObject);
									// System.out.println("finalHeaderList >>> "+finalHeaderList.size());
									if (!iterator2.hasNext())
									{
										// System.out.println("HELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

										// currentDayTrasferData used for get
										// today transfer data
										currentDayTrasferData = HUH.getData4AssetTrasfer(connection);
										// System.out.println("  currentDayTrasferData  Size Is  >>>>>"+currentDayTrasferData.size());

										String report_sms = null;
										if (dataList.size() == 1 && obj[0] != null && obj[1] != null)
										{
											report_sms = "Dear " + empname + ", For " + obj[1] + " Outlet, Total Tickets Status as on " + DateUtil.getCurrentDateIndianFormat() + ": Pending: " + pc1 + ", C/F Pending: " + cftotal1 + ", Snooze: " + sc1 + ", Ignore: " + ic1 + ", Resolved: " + rc1 + ".";
										} else
										{
											report_sms = "Dear " + empname + ", For " + dataList.size() + " Outlets, Total Tickets Status as on " + DateUtil.getCurrentDateIndianFormat() + ": Pending: " + pc1 + ", C/F Pending: " + cftotal1 + ", Snooze: " + sc1 + ", Ignore: " + ic1 + ", Resolved: " + rc1 + ".";
										}
										if (mobno != null && mobno != "" && mobno != "NA" && mobno.length() == 10 && (mobno.startsWith("9") || mobno.startsWith("8") || mobno.startsWith("7")))
										{
											boolean putsmsstatus = CH.addMessage(empname, department, mobno, report_sms, mailSubject, "Pending", "0", "ASTM", connection);
										}
										cftotal1 = 0;
										pc1 = 0;
										sc1 = 0;
										ic1 = 0;
										rc1 = 0;
									}
								}
							}
							
							 * if(dataList.size()==0) { String report_sms =
							 * "Dear " + empname +", For "+dataList.size()+
							 * " Outlets, Total Ticket Status as on " +
							 * DateUtil.getCurrentDateIndianFormat() +
							 * ": Pending: " + pc + ", C/F Pending: " + cftotal
							 * + ", Snooze: " + sc + ", Ignore: " + ic +
							 * ", Resolved: " + rc + "."; if (mobno != null &&
							 * mobno != "" && mobno != "NA" && mobno.length() ==
							 * 10 && (mobno.startsWith("9") ||
							 * mobno.startsWith("8") || mobno.startsWith("7")))
							 * {
							 * 
							 * @SuppressWarnings("unused") boolean putsmsstatus
							 * = CH.addMessage(empname, department, mobno,
							 * report_sms, subject, "Pending", "0", "ASTM",
							 * connection); } }
							 
						}

						String filepath = new DailyReportExcelWrite4Attach().writeDataInExcel4AssetCompliant(currentdayCounter, CFCounter, currentDayResolvedDataMap, currentDayPendingDataMap, currentDaySnoozeDataMap, currentDayHPDataMap, currentDayIgMap, CFDataMap, deptLevel, type_report);
						String mailtext = HUH.getConfigMailForReport(finalHeaderList, empname, currentDayResolvedDataMap, currentDayPendingDataMap, currentDaySnoozeDataMap, currentDaySeekDataMap, currentDayIgMap, CFDataMap, currentDayTrasferData);

						CH.addMail(empname, department, emailid, mailSubject, mailtext, "Report", "Pending", "0", filepath, "ASTM", connection);
						CH.addMail(empname, department, emailid, "Login Details For VLCC Asset As On " + DateUtil.getCurrentDateIndianFormat() + " & " + DateUtil.getCurrentTimeHourMin(), loginReportMailText, "Report", "Pending", "0", "", "ASTM", connection);
					}

					// to send the daily activity report and reminder of cuurent
					// day activity.
					else if (module != null && !module.equals("") && module.equalsIgnoreCase("WFPM") && updateReport)
					{
						System.out.println("WFPM>>");
						new WFPMMailingReport().buildEmail(connection, empname, emailid, mobno, department, subject, module);
					} else if (module != null && !module.equals("") && module.equalsIgnoreCase("WFPMActivityRem") && updateReport)
					{
						System.out.println("WFPMActivityRem>>");
						String cID = new DailyActivityReminderMailHelper().getContactIdByEmpIdForMail(empId, subdept_dept, connection);
						// new
						// DailyActivityReminderMailText().buildEmail(connection,
						// empname, emailid, mobno, department, subject,
						// module);
						new DailyActivityReminderMailText().buildEmailSales(connection, empname, emailid, mobno, department, subject, module, cID);
					}
				}
			}
			Runtime rt = Runtime.getRuntime();
			rt.gc();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	*/}

	@SuppressWarnings("rawtypes")
	public void escalateToNextLevelInDREAM_HDM(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			DARReportHelper DRH = new DARReportHelper();
			ComplaintLodgeHelper CLH = new ComplaintLodgeHelper();
			String query = "";
			query = "select id ,ticket_no ,clientfor ,clientName ,offering ,feedbackTo,feedbackCC,feed_brief ,forDept  ,escalation_date ,escalation_time,open_date,open_time,location,level,subCategory,moduleName,non_working_time from complaint_status where status='Pending' and escalation_date<='" + DateUtil.getCurrentDateUSFormat() + "'";
			System.out.println(query);
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				@SuppressWarnings("unused")
				String id = null, ticket_no = null, clientFor = null, clientName = null, offering = null, feedTo = null, feed_cc = null, feed_brief = null, forDept = "", escalation_date = null, escalation_time = null, open_date = null, open_time = null, location = null, level = null, subCatg = null, catg_Dept = null, esc_duration = null, needesc = null, module = null, existing_non_working = "00:00";
				String adrr_time = "", res_time = "", non_working_time = "00:00";
				String levelMsg = null, subject = null, ulevel = null, _2uLevel = null, _3uLevel = null;
				List esclevelData = null;

				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticket_no = object[1].toString();
					} else
					{
						ticket_no = "NA";
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						if (object[2].toString().equalsIgnoreCase("EC"))
						{
							clientFor = "Existing Client";
						} else if (object[2].toString().equalsIgnoreCase("PC"))
						{
							clientFor = "Prospect Client";
						} else if (object[2].toString().equalsIgnoreCase("PA"))
						{
							clientFor = "Prospect Associate";
						} else if (object[2].toString().equalsIgnoreCase("EA"))
						{
							clientFor = "Existing Associate";
						} else if (object[2].toString().equalsIgnoreCase("IN"))
						{
							clientFor = "Internal";
						}
					} else
					{
						clientFor = "NA";
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						clientName = DRH.clientName(object[2].toString(), object[3].toString(), connection);
					} else
					{
						clientName = "NA";
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						offering = DRH.offeringName(object[2].toString(), object[3].toString(), object[4].toString(), connection);
					} else
					{
						offering = "NA";
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						String[] feed_to = CLH.getFeedbackToData(object[2].toString(), object[5].toString(), connection);
						feedTo = feed_to[0];
					} else
					{
						feedTo = "NA";
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						String[] feedCC = object[6].toString().split("#");
						StringBuilder feed_cc1 = new StringBuilder();
						for (String s : feedCC)
						{
							String[] feed_to = CLH.getFeedbackToData(object[2].toString(), s, connection);
							feed_cc1.append(feed_to[0] + ",");
						}
						feed_cc = feed_cc1.substring(0, feed_cc1.length() - 1);
					} else
					{
						feed_cc = "NA";
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						feed_brief = object[7].toString();
					} else
					{
						feed_brief = "NA";
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						forDept = object[8].toString();
					} else
					{
						forDept = "NA";
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						escalation_date = object[9].toString();
					} else
					{
						escalation_date = "NA";
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						escalation_time = object[10].toString();
					} else
					{
						escalation_time = "NA";
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						open_date = object[11].toString();
					} else
					{
						open_date = "NA";
					}
					if (object[12] != null && !object[12].toString().equals(""))
					{
						open_time = object[12].toString();
					} else
					{
						open_time = "NA";
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						location = object[13].toString();
					} else
					{
						location = "NA";
					}
					if (object[14] != null && !object[14].toString().equals(""))
					{
						level = object[14].toString();
					} else
					{
						level = "NA";
					}
					if (object[15] != null && !object[15].toString().equals(""))
					{
						subCatg = object[15].toString();
					} else
					{
						subCatg = "NA";
					}
					if (object[16] != null && !object[16].toString().equals(""))
					{
						module = object[16].toString();
					} else
					{
						module = "NA";
					}
					if (object[17] != null && !object[17].toString().equals(""))
					{
						existing_non_working = object[17].toString();
					}

					if (escalation_date != null && !escalation_date.equals("") && !escalation_date.equals("NA") && escalation_time != null && !escalation_time.equals("") && !escalation_time.equals("NA") && level != null && !level.equals("") && !level.equals("NA") && !level.equals("Level5"))
					{
						boolean escnextlvl = DateUtil.time_update(escalation_date, escalation_time);
						if (escnextlvl && module != null && !module.equals("") && !module.equals("NA"))
						{
							if (subCatg != null && !subCatg.equals("") && !subCatg.equals("0"))
							{
								query = "select * from feedback_subcategory where id='" + subCatg + "'";
								List subCategoryList = HUH.getData(query, connection);
								if (subCategoryList != null && subCategoryList.size() > 0)
								{
									for (Iterator iterator5 = subCategoryList.iterator(); iterator5.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator5.next();
										if (objectCol[4] == null)
										{
											adrr_time = "06:00";
										} else
										{
											adrr_time = objectCol[4].toString();
										}
										if (objectCol[5] == null)
										{
											res_time = "10:00";
										} else
										{
											res_time = objectCol[5].toString();
										}
										if (objectCol[9] == null)
										{
											needesc = "Y";
										} else
										{
											needesc = objectCol[9].toString();
										}
									}
								}
								if (module.equals("DREAM_HDM"))
								{
									catg_Dept = forDept;
								}
								String new_date = null, new_time = null, datetime = null;
								if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
								{
									String[] newdate_time = null;
									getNextEscalationDateTime(adrr_time, res_time, catg_Dept, "DREAM_HDM", connection);
									if (new_escalation_datetime != null && new_escalation_datetime != "")
									{
										newdate_time = new_escalation_datetime.split("#");
										if (newdate_time.length > 0)
										{
											new_date = newdate_time[0];
											new_time = newdate_time[1];
											non_working_time = DateUtil.addTwoTime(non_working_time, non_working_timing);
										}
									}
								}
								String updateLevel = "";
								ulevel = "" + (Integer.parseInt(level.substring(5)) + 1);
								subject = "L" + level.substring(5) + " to L" + ulevel + " Escalate Alert: " + ticket_no;
								levelMsg = "L" + level.substring(5) + " to L" + (ulevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feedTo) + ", Location: " + location + ", Brief: " + feed_brief + ".";
								/*
								 * esclevelData = HUH.getEmp4Escalation(
								 * to_dept_subdept, deptHierarchy, module,
								 * ulevel, "N", "", connection);
								 */
								esclevelData = CLH.getEmp4Escalation(forDept, module, ulevel, connection);
								updateLevel = "Level" + ulevel;
								if (esclevelData == null || esclevelData.size() == 0)
								{
									_2uLevel = "" + (Integer.parseInt(ulevel) + 1);
									subject = "L" + level.substring(5) + " to L" + (_2uLevel) + " Escalate Alert: " + ticket_no;
									levelMsg = "L" + level.substring(5) + " to L" + (_2uLevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feedTo) + ", Location: " + location + ", Brief: " + feed_brief + ".";
									/*
									 * esclevelData = HUH.getEmp4Escalation(
									 * to_dept_subdept, deptHierarchy, module,
									 * _2uLevel, "N", "", connection);
									 */
									esclevelData = CLH.getEmp4Escalation(forDept, module, _2uLevel, connection);
									updateLevel = "Level" + _2uLevel;
									if (esclevelData == null || esclevelData.size() == 0)
									{
										_3uLevel = "" + (Integer.parseInt(_2uLevel) + 1);
										subject = "L" + level.substring(5) + " to L" + (_3uLevel) + " Escalate Alert: " + ticket_no;
										levelMsg = "L" + level.substring(5) + " to L" + (_3uLevel) + " Escalate Alert: Tkt No: " + ticket_no + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(escalation_date) + "," + escalation_time.substring(0, 5) + ", Reg. By: " + DateUtil.makeTitle(feedTo) + ", Location: " + location + ", Brief: " + feed_brief + ".";
										/*
										 * esclevelData = HUH.getEmp4Escalation(
										 * to_dept_subdept, deptHierarchy,
										 * module, _3uLevel, "N", "",
										 * connection);
										 */
										esclevelData = CLH.getEmp4Escalation(forDept, module, _3uLevel, connection);
										updateLevel = "Level" + _3uLevel;
									}
								}
								if (esclevelData != null && esclevelData.size() > 0)
								{
									// System.out.println("Inside 4th If");
									for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
									{
										Object[] object3 = (Object[]) iterator3.next();

										String[] newdate_time = null;
										if (adrr_time != null && adrr_time != "" && res_time != null && res_time != "" && forDept != null && forDept != "")
										{
											getNextEscalationDateTime(adrr_time, res_time, forDept, module, connection);
											if (new_escalation_datetime != null && new_escalation_datetime != "")
											{

												String updated_non_working_time = DateUtil.addTwoTime(existing_non_working, non_working_timing);
												newdate_time = new_escalation_datetime.split("#");
												if (newdate_time.length > 0)
												{
													new_date = newdate_time[0];
													new_time = newdate_time[1];

													updateQry = "update complaint_status set escalation_date='" + new_date + "',escalation_time='" + new_time + "',level='" + updateLevel + "',non_working_time='" + updated_non_working_time + "' where id='" + id + "'";
													boolean updatefeedback = HUH.updateData(updateQry, connection);
													mappingUpdateQry = "update escalation_mapping set nextEscLevel_" + updateLevel.substring(5) + "='" + updateLevel + "',EscLevel_" + updateLevel.substring(5) + "_DateTime='" + DateUtil.convertDateToIndianFormat(new_date) + "(" + new_time + ")'  where feed_status_id='" + id + "'";
													HUH.updateData(mappingUpdateQry, connection);

													List data = CLH.getFeedbackDetail(id, "Pending", connection);
													FeedbackPojo fbp = null;
													fbp = CLH.setFeedbackDataValues(data, "Pending", connection);
													if (updatefeedback)
													{
														if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
														{
															CH.addMessage(object3[1].toString(), object3[4].toString(), object3[2].toString(), levelMsg, "", "Pending", "0", module, connection);
														}
														if (fbp != null)
														{
															String mailText = CLH.configMessage4Escalation(fbp, subject, object3[1].toString(), true);
															CH.addMail(object3[1].toString(), object3[4].toString(), object3[3].toString(), subject, mailText, "", "Pending", "0", "", module, connection);
														}
														if (fbp != null && fbp.getFeed_by() != null && !fbp.getFeed_by().equals("") && fbp.getFeedback_by_mobno() != null && !fbp.getFeedback_by_mobno().equals("") && !fbp.getFeedback_by_mobno().equals("NA"))
														{
															String msg = "Dear " + fbp.getFeed_by() + ", We apologies for delay of Ticket No: " + fbp.getTicket_no() + " that has now been escalated to " + DateUtil.makeTitle(object3[1].toString()) + ", Mob: " + object3[2].toString() + ". Thanks.";
															CH.addMessage(fbp.getFeed_by(), "", fbp.getFeedback_by_mobno(), msg, "", "Pending", "0", module, connection);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void SnoozetoPendingInDREAM_HDM(SessionFactory connection, HelpdeskUniversalHelper HUH, CommunicationHelper CH)
	{
		try
		{
			ComplaintLodgeHelper CLH = new ComplaintLodgeHelper();
			String query = "select id ,ticket_no ,feedbackTo ,feedbackCC ,feed_brief ,allot_to ,location ,subCategory ,sn_upto_date ,sn_upto_time,moduleName,forDept,clientfor,non_working_time from complaint_status where status='Snooze' and sn_upto_date<='" + DateUtil.getCurrentDateUSFormat() + "'";
			System.out.println("query    " + query);
			List data4escalate = HUH.getData(query, connection);
			if (data4escalate != null && data4escalate.size() > 0)
			{
				String id = null, ticket_no = null, feed_by = null, feed_cc = null, feed_brief = null, snooze_upto_date = null, snooze_upto_time = null, location = null, subCatg = null, allot_to = null, adrr_time = null, res_time = null, module = null, to_dept = null, existing_non_working = "00:00";
				List esclevelData = null;
				for (Iterator iterator = data4escalate.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();

					if (object[0] != null && !object[0].toString().equals(""))
					{
						id = object[0].toString();
					}
					if (object[1] != null && !object[1].toString().equals(""))
					{
						ticket_no = object[1].toString();
					} else
					{
						ticket_no = "NA";
					}
					if (object[2] != null && !object[2].toString().equals(""))
					{
						String[] feed_to = CLH.getFeedbackToData(object[12].toString(), object[2].toString(), connection);
						feed_by = feed_to[0];
					} else
					{
						feed_by = "NA";
					}
					if (object[3] != null && !object[3].toString().equals(""))
					{
						String[] feedCC = object[3].toString().split("#");
						StringBuilder feed_cc1 = new StringBuilder();
						for (String s : feedCC)
						{
							String[] feed_to = CLH.getFeedbackToData(object[12].toString(), s, connection);
							feed_cc1.append(feed_to[0] + ",");
						}
						feed_cc = feed_cc1.substring(0, feed_cc1.length() - 1);
					} else
					{
						feed_cc = "NA";
					}
					if (object[4] != null && !object[4].toString().equals(""))
					{
						feed_brief = object[4].toString();
					} else
					{
						feed_brief = "NA";
					}
					if (object[5] != null && !object[5].toString().equals(""))
					{
						allot_to = object[5].toString();
					} else
					{
						allot_to = "NA";
					}
					if (object[6] != null && !object[6].toString().equals(""))
					{
						location = object[6].toString();
					} else
					{
						location = "NA";
					}
					if (object[7] != null && !object[7].toString().equals(""))
					{
						subCatg = object[7].toString();
					} else
					{
						subCatg = "NA";
					}
					if (object[8] != null && !object[8].toString().equals(""))
					{
						snooze_upto_date = object[8].toString();
					} else
					{
						snooze_upto_date = "NA";
					}
					if (object[9] != null && !object[9].toString().equals(""))
					{
						snooze_upto_time = object[9].toString();
					} else
					{
						snooze_upto_time = "NA";
					}
					if (object[10] != null && !object[10].toString().equals(""))
					{
						module = object[10].toString();
					} else
					{
						module = "NA";
					}
					if (object[11] != null && !object[11].toString().equals(""))
					{
						to_dept = object[11].toString();
					}
					if (object[13] != null && !object[13].toString().equals(""))
					{
						existing_non_working = object[13].toString();
					}
					if (snooze_upto_date != null && !snooze_upto_date.equals("") && snooze_upto_time != null && !snooze_upto_time.equals("") && module != null && !module.equals("") && !module.equals("NA"))
					{
						boolean snoozetopending = DateUtil.time_update(snooze_upto_date, snooze_upto_time);
						if (snoozetopending)
						{
							if (!subCatg.equals("0"))
							{
								String query2 = "select * from feedback_subcategory where id='" + subCatg + "'";
								List subCategoryList = HUH.getData(query2, connection);
								if (subCategoryList != null && subCategoryList.size() > 0)
								{
									for (Iterator iterator2 = subCategoryList.iterator(); iterator2.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator2.next();
										if (objectCol[4] == null)
										{
											adrr_time = "06:00";
										} else
										{
											adrr_time = objectCol[4].toString();
										}
										if (objectCol[5] == null)
										{
											res_time = "10:00";
										} else
										{
											res_time = objectCol[5].toString();
										}
									}
								}
								esclevelData = CLH.getEmployeeData(allot_to.toString(), to_dept, "DREAM_HDM", connection);
								if (esclevelData != null && esclevelData.size() > 0)
								{
									for (Iterator iterator3 = esclevelData.iterator(); iterator3.hasNext();)
									{
										Object[] object3 = (Object[]) iterator3.next();

										String new_date = null, new_time = null, datetime = null;
										String[] newdate_time = null;
										if (snooze_upto_date != null && snooze_upto_date != "" && snooze_upto_time != null && snooze_upto_time != "" && adrr_time != null && adrr_time != "" && res_time != null && res_time != "")
										{
											getSnoozeToActiveDateTime(adrr_time, res_time, to_dept, module, connection);
											// datetime =
											// DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(),
											// DateUtil.getCurrentTime(),
											// adrr_time, res_time);

											if (new_escalation_datetime != null && new_escalation_datetime != "")
											{
												String updated_non_working_time = DateUtil.addTwoTime(existing_non_working, non_working_timing);
												newdate_time = new_escalation_datetime.split("#");
												if (newdate_time.length > 0)
												{
													new_date = newdate_time[0];
													new_time = newdate_time[1];

													updateQry = "update complaint_status set escalation_date='" + new_date + "',escalation_time='" + new_time + "',status='Pending',non_working_time='" + updated_non_working_time + "' where id='" + id + "'";
													boolean updatefeedback = HUH.updateData(updateQry, connection);

													// List data =
													// CLH.getFeedbackDetail("",deptHierarchy,"Pending",id,connection);
													List data = CLH.getFeedbackDetail(id, "Pending", connection);
													FeedbackPojo fbp = null;
													fbp = CLH.setFeedbackDataValues(data, "Pending", connection);
													if (updatefeedback)
													{
														@SuppressWarnings("unused")
														boolean putsmsstatus = false;
														// Code/block for
														// sending the message
														if (object3[1] != null && !object3[1].toString().equalsIgnoreCase(""))
														{
															String msg = "By:Snooze to Active Alert: Ticket No: " + ticket_no + ", Resolved By: " + new_time + ", Reg. By: " + feed_by + ", Location: " + location + ", Brief: " + feed_brief + ".";
															putsmsstatus = CH.addMessage(object3[1].toString(), object3[5].toString(), object3[2].toString(), msg, ticket_no, "Pending", "0", module, connection);
														}
														if (fbp != null)
														{
															String mailText = CLH.getConfigMessage(fbp, "Snooze --> Pending Feedback Alert", "Pending", true, fbp.getClientFor(), fbp.getClientName(), fbp.getOfferingName());
															CH.addMail(object3[1].toString(), object3[5].toString(), object3[3].toString(), "Snooze --> Pending Feedback Alert", mailText, ticket_no, "Pending", "0", "", module, connection);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
	{

		// System.out.println("Inside Important method");
		String startTime = "", endTime = "", callflag = "";
		String dept_id = todept;

		String working_date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
		String working_day = DateUtil.getDayofDate(working_date);
		// List of working timing data
		List workingTimingData = new EscalationHelper().getWorkingTimeData(module, working_day, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			startTime = workingTimingData.get(1).toString();
			endTime = workingTimingData.get(2).toString();

			// Here we know the complaint lodging time is inside the the start
			// and end time or not
			callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
			if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
			{
				// System.out.println("Inside before");

				// date_time = DateUtil.newdate_AfterEscInRoaster(date,
				// startTime, adrr_time, res_time);
				// new_escalation_datetime = DateUtil.newdate_AfterEscTime(date,
				// startTime, esc_duration, needesc);
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
				// System.out.println("After Calculation First escalation Date time is  "+new_escalation_datetime);
				// (Differrence between current date/time and date & starttime)
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					// System.out.println("Inside If when flag is true");
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					// System.out.println("Non Working time inside if  "+non_working_timing);
				} else
				{
					// System.out.println("Inside Else when flag is false");
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					// System.out.println("First Non Working time  "+non_working_timing);
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					// System.out.println("Final After calculation Second Non Working time  "+non_working_timing);
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, newdatetime[0], newdatetime[1]);
					// System.out.println("First diffrence "+timeDiff1);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, working_date, endTime);
					// System.out.println("Second diffrence "+timeDiff2);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					// System.out.println("Main diffrence "+main_difference);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						// System.out.println("Inside Second If   "+workingTimingData.toString());
						startTime = workingTimingData.get(1).toString();
						// System.out.println("Final Start Time :::::::::: "+startTime);
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						// System.out.println("Final Date ::::::::: "+final_date);
						// System.out.println("Final Left time :::::::: "+left_time);
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						// new_escalation_datetime =
						// DateUtil.newdate_AfterEscInRoaster(final_date,
						// startTime, adrr_time, res_time);
						non_working_timing = workingTimingData.get(3).toString();
						// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
			{

				// Escalation date time at the time of opening the ticket
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = "00:00";
				} else
				{
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						// new_escalation_datetime =
						// DateUtil.newdate_AfterEscInRoaster(final_date,
						// startTime, adrr_time, res_time);
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
			{
				// System.out.println("Inside After");

				// System.out.println("Inside After");

				non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, "24:00");

				workingTimingData.clear();
				String main_difference = DateUtil.addTwoTime(adrr_time, res_time);
				// System.out.println("Main Difference in After  "+main_difference);
				workingTimingData = new EscalationHelper().getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					// System.out.println("Inside Second If   "+workingTimingData.toString());
					startTime = workingTimingData.get(1).toString();
					// System.out.println("Final Start Time :::::::::: "+startTime);
					String left_time = workingTimingData.get(4).toString();
					String final_date = workingTimingData.get(5).toString();
					// System.out.println("Final Date ::::::::: "+final_date);
					// System.out.println("Final Left time :::::::: "+left_time);
					new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
					// new_escalation_datetime =
					// DateUtil.newdate_AfterEscInRoaster(final_date, startTime,
					// adrr_time, res_time);
					non_working_timing = workingTimingData.get(3).toString();
					// System.out.println("Non Non 222  :::::::::  "+non_working_timing);
				}
			}
		}

		// System.out.println("Final   ::::   Escalation Date  "+new_escalation_datetime);
		// System.out.println("Final Non Working Timing  "+non_working_timing);
	}

	public void getSnoozeToActiveDateTime(String adrr_time, String res_time, String tosubdept, String module, SessionFactory connectionSpace)
	{
		// System.out.println("Inside Important method");
		String date_time = "", level_date_time = "";
		String holiday_status = "", startTime = "", endTime = "", callflag = "";
		String dept_id = tosubdept;
		String date = DateUtil.getCurrentDateUSFormat();
		String currentday = DateUtil.getCurrentDayofWeek();
		// List of working timing data
		List workingTimingData = new EscalationHelper().getWorkingTimeData(module, currentday, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			holiday_status = workingTimingData.get(0).toString();
		}

		if (holiday_status.equals("Y") || holiday_status.equals("N"))
		{
			// Name should be different for date & current day
			if (holiday_status.equals("Y"))
			{
				date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
				currentday = DateUtil.getDayofDate(date);
			}
			workingTimingData.clear();
			workingTimingData = new EscalationHelper().getWorkingTimeData(module, currentday, dept_id, connectionSpace);
			if (workingTimingData != null && workingTimingData.size() > 0)
			{
				startTime = workingTimingData.get(1).toString();
				endTime = workingTimingData.get(2).toString();
				// System.out.println("Start Time  "+startTime);
				// System.out.println("End Time  "+endTime);
				// Here we know the complaint lodging time is inside the the
				// start and end time or not
				callflag = DateUtil.timeInBetween(date, startTime, endTime, DateUtil.getCurrentTime());
				// System.out.println("Call Flag Value is "+callflag);
				if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
				{
					// System.out.println("Inside before");
					// date_time = DateUtil.newdate_AfterEscInRoaster(date,
					// startTime, adrr_time, res_time);
					// new_escalation_datetime =
					// DateUtil.newdate_AfterEscTime(date, startTime,
					// esc_duration, needesc);
					new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);

					// (Differrence between current date/time and date &
					// starttime)
					String newdatetime[] = new_escalation_datetime.split("#");
					// Check the date time is lying inside the time block
					boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], date, endTime);
					if (flag)
					{
						non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
					} else
					{
						/*
						 * StringtimeDiff1=DateUtil.time_difference(DateUtil.
						 * getCurrentDateUSFormat(), DateUtil.getCurrentTime(),
						 * newdatetime[0], newdatetime[1]); String
						 * timeDiff2=DateUtil
						 * .time_difference(DateUtil.getCurrentDateUSFormat(),
						 * DateUtil.getCurrentTime(), date, endTime); String
						 * main_difference =
						 * DateUtil.getTimeDifference(timeDiff1, timeDiff2);
						 */workingTimingData.clear();

						date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date), 1), connectionSpace);
						currentday = DateUtil.getDayofDate(date);

						workingTimingData = new EscalationHelper().getWorkingTimeData(module, currentday, dept_id, connectionSpace);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							startTime = workingTimingData.get(1).toString();
							new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							// System.out.println("Non Non 222"+non_working_timing);
						}
					}
				} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
				{
					// Escalation date time at the time of opening the ticket
					// date_time =
					// DateUtil.newdate_AfterEscInRoaster(DateUtil.getCurrentDateUSFormat(),
					// DateUtil.getCurrentTime(), adrr_time, res_time);
					new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
					// System.out.println("New Escalation Date & Time  "+new_escalation_datetime);
					String newdatetime[] = new_escalation_datetime.split("#");
					// Check the date time is lying inside the time block
					boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], DateUtil.getCurrentDateUSFormat(), endTime);
					if (flag)
					{
						non_working_timing = "00:00";
					} else
					{
						/*
						 * StringtimeDiff1=DateUtil.time_difference(DateUtil.
						 * getCurrentDateUSFormat(), DateUtil.getCurrentTime(),
						 * newdatetime[0], newdatetime[1]); String
						 * timeDiff2=DateUtil
						 * .time_difference(DateUtil.getCurrentDateUSFormat(),
						 * DateUtil.getCurrentTime(),
						 * DateUtil.getCurrentDateUSFormat(), endTime); String
						 * main_difference =
						 * DateUtil.getTimeDifference(timeDiff1, timeDiff2);
						 */
						workingTimingData.clear();

						date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date), 1), connectionSpace);
						currentday = DateUtil.getDayofDate(date);

						workingTimingData = new EscalationHelper().getWorkingTimeData(module, currentday, dept_id, connectionSpace);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							startTime = workingTimingData.get(1).toString();
							new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							// System.out.println("Non Non 222"+non_working_timing);
						}
					}
				} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
				{
					System.out.println("Inside After");
					workingTimingData.clear();
					if (holiday_status.equalsIgnoreCase("Y"))
					{
						date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNextDateAfter(1), connectionSpace);
						currentday = DateUtil.getDayofDate(date);
					} else if (holiday_status.equalsIgnoreCase("N"))
					{
						date = DateUtil.getNextDateAfter(1);
						currentday = DateUtil.getDayofDate(date);
					}
					System.out.println("Now The Date  " + date);
					System.out.println("Now The Day  " + currentday);
					workingTimingData = new EscalationHelper().getWorkingTimeData(module, currentday, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						endTime = workingTimingData.get(2).toString();
						System.out.println("Start Time " + startTime);
						System.out.println("Start Time " + endTime);
						date_time = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
						new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
						System.out.println("New Escalation date Time " + new_escalation_datetime);
						String newdatetime[] = new_escalation_datetime.split("#");
						// Check the date time is lying inside the time block
						boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], newdatetime[0], endTime);
						System.out.println("Flag Value is  " + flag);
						if (flag)
						{
							non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
							System.out.println("Non Non " + non_working_timing);
						} else
						{
							System.out.println("Flag Value is  " + flag);
							workingTimingData.clear();
							date = new EscalationHelper().getNextOrPreviousWorkingDate("N", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(date), 1), connectionSpace);
							currentday = DateUtil.getDayofDate(date);

							workingTimingData = new EscalationHelper().getWorkingTimeData(module, currentday, dept_id, connectionSpace);
							if (workingTimingData != null && workingTimingData.size() > 0)
							{
								startTime = workingTimingData.get(1).toString();
								new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(date, startTime, adrr_time, res_time);
								System.out.println("Updated  New Escalation date Time " + new_escalation_datetime);
								non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), date, startTime);
								System.out.println("Non Non 222" + non_working_timing);
							}
						}
					}
				}
			}
		}

		String[] date_time_arr = date_time.split("#");
		if (date_time_arr.length > 0)
		{/*
		 * if (needesc!=null && !needesc.equals("") &&
		 * needesc.equalsIgnoreCase("Y")) { escalation_date = date_time_arr[0];
		 * escalation_time = date_time_arr[1]; resolution_date =
		 * date_time_arr[0]; resolution_time = date_time_arr[1]; } else {
		 * resolution_date = date_time_arr[0]; resolution_time =
		 * date_time_arr[1]; }
		 */
		}

		String[] level_date_time_arr = level_date_time.split("#");
		if (level_date_time_arr.length > 0)
		{/*
		 * level_resolution_date = level_date_time_arr[0]; level_resolution_time
		 * = level_date_time_arr[1];
		 */
		}

		// System.out.println("Non Working Time   "+non_working_timing);
	}

}
